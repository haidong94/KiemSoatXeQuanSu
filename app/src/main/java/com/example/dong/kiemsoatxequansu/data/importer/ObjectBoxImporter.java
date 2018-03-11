package com.example.dong.kiemsoatxequansu.data.importer;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;

import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.model.Specification;
import com.example.dong.kiemsoatxequansu.data.model.Matter;
import com.example.dong.kiemsoatxequansu.data.model.DetailMatter;
import com.example.dong.kiemsoatxequansu.utils.TransactionTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by DONG on 31-Oct-17.
 */

public class ObjectBoxImporter {
    Resources resources;
    TransactionTime transactionTime;
    BoxStore boxStore;
    Box<Matter> congTacBox;
    Activity activity;

    public ObjectBoxImporter(Resources resources, Activity activity) {
        this.resources = resources;
        this.activity = activity;
        boxStore = ((App) activity.getApplication()).getBoxStore();
        congTacBox = boxStore.boxFor(Matter.class);
    }

    public void importFromJson() throws FileNotFoundException {
        File sdcard = Environment.getExternalStorageDirectory();

//Get the text file
        File file = new File(sdcard, "chitiet2.txt");
        // transaction timer

        transactionTime = new TransactionTime(System.currentTimeMillis());
        //  InputStream inputStream = resources.openRawResource(R.raw.people);
//        InputStream inputStream = new FileInputStream(file);
//        try {
//            readJsonStream(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String getTextFromFile=readTextFromFile(file);
        List<Matter> nguyenVatLieuList=convertStringToObject(getTextFromFile);

        congTacBox.put(nguyenVatLieuList);


        transactionTime.setEnd(System.currentTimeMillis());
        Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");
    }

    private List<Matter> convertStringToObject(String getTextFromFile) {
        JSONArray dulieus = null;
        List<Matter> nguyenVatLieuList=new ArrayList<>();
        try {
            dulieus = new JSONArray(getTextFromFile);

            for (int i = 0; i < dulieus.length(); i++) {

                JSONObject c = dulieus.getJSONObject(i);
                Matter nguyenVatLieu=new Matter();
                nguyenVatLieu.setId(c.getInt("Id"));
                nguyenVatLieu.setName(c.getString("Name"));

                String jsonDuLieu= c.getString("Data");
                ArrayList<DetailMatter> phuTungArrayList=new ArrayList<>();
                JSONArray data1 = new JSONArray(jsonDuLieu);
                for (int j = 0; j < data1.length(); j++) {
                    JSONObject dataPhuTung = data1.getJSONObject(j);
                    DetailMatter phuTung=new DetailMatter();
                    phuTung.setId(dataPhuTung.getInt("Id"));
                    phuTung.setName(dataPhuTung.getString("Name"));

                    String jsonDuLieuChiTietPhuTung= dataPhuTung.getString("Data");
                    ArrayList<Specification> chiTietPhuTungArrayList=new ArrayList<>();
                    JSONArray data2 = new JSONArray(jsonDuLieuChiTietPhuTung);
                    for (int k = 0; k < data2.length(); k++) {
                        JSONObject dataChiTiet = data2.getJSONObject(k);
                        Specification chiTietPhuTung = new Specification();
                        chiTietPhuTung.setId(dataChiTiet.getInt("Id"));
                        chiTietPhuTung.setTen(dataChiTiet.getString("Ten"));
                        if(!dataChiTiet.isNull("QuyCach")) {
                            chiTietPhuTung.setQuyCach(dataChiTiet.getString("QuyCach"));
                        }
                        chiTietPhuTung.setDVT(dataChiTiet.getString("DonViTinh"));
                        chiTietPhuTung.setSoLuong(dataChiTiet.getString("SoLuong"));
                        chiTietPhuTung.setDonGia(dataChiTiet.getString("GiaTien"));
                        chiTietPhuTungArrayList.add(chiTietPhuTung);
                    }
                    phuTung.setChiTietPhuTungList(chiTietPhuTungArrayList);
                }
                nguyenVatLieu.setPhuTungList(phuTungArrayList);
                nguyenVatLieuList.add(nguyenVatLieu);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  nguyenVatLieuList;
    }

    private String readTextFromFile(File file) {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line=br.readLine();
            while (line!=null)
            {
                text.append(line);
                line=br.readLine();
            }

            br.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return text.toString();
    }

    public void readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                congTacBox.put(readMessage(reader));
            }
            reader.endArray();
        } finally {
            reader.close();
        }
    }

    public Matter readMessage(JsonReader reader) throws IOException {
        long id = -1;
        String Ten = null;
        List<DetailMatter> phuTungList = new ArrayList<>();


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Id")) {
                id = reader.nextLong();
            } else if (name.equals("Name")) {
                Ten = reader.nextString();
            } else if (name.equals("Data")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    phuTungList.add(readMessagePhuTung(reader));
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Matter(id, Ten, phuTungList);
    }

    public DetailMatter readMessagePhuTung(JsonReader reader) throws IOException {
        long id = -1;
        String Ten = null;
        List<Specification> chiTietPhuTungs = new ArrayList<>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Id")) {
                id = reader.nextLong();
            } else if (name.equals("Name")) {
                Ten = reader.nextString();
            } else if (name.equals("Data")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    chiTietPhuTungs.add(readChitiePhutung(reader));
                }
                reader.endArray();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new DetailMatter(id, Ten, chiTietPhuTungs);
    }

    public Specification readChitiePhutung(JsonReader reader) throws IOException {
        long id = -1;
        String Ten = null;
        String QuyCach = null;
        String DonViTinh = null;
        String SoLuong = null;
        String GiaTien = null;


        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Id")) {
                id = reader.nextLong();
            } else if (name.equals("Ten")) {
                Ten = reader.nextString();
            } else if (name.equals("QuyCach")) {
                QuyCach = reader.nextString();

            } else if (name.equals("DonViTinh")) {
                DonViTinh = reader.nextString();

            } else if (name.equals("SoLuong")) {
                SoLuong = reader.nextString();

            } else if (name.equals("GiaTien")) {
                GiaTien = reader.nextString();

            } else if (name.equals("")) {
                reader.skipValue();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Specification(id, Ten, QuyCach, DonViTinh, SoLuong, GiaTien);
    }

}
