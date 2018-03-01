package com.example.dong.kiemsoatxequansu.data.importer;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Environment;
import android.util.JsonReader;
import android.util.Log;

import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.model.ChiTietPhuTung;
import com.example.dong.kiemsoatxequansu.utils.TransactionTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by DONG on 31-Oct-17.
 */

public class ObjectBoxImporter {
    Resources resources;
    TransactionTime transactionTime;
    BoxStore boxStore;
    Box<ChiTietPhuTung> congTacBox;
    Activity activity;
    public ObjectBoxImporter(Resources resources, Activity activity) {
        this.resources = resources;
        this.activity=activity;
        boxStore = ((App) activity.getApplication()).getBoxStore();
        congTacBox = boxStore.boxFor(ChiTietPhuTung.class);
    }

    public void importFromJson() throws FileNotFoundException {
        File sdcard = Environment.getExternalStorageDirectory();

//Get the text file
        File file = new File(sdcard,"chitiet.txt");
        // transaction timer

        transactionTime = new TransactionTime(System.currentTimeMillis());
        //  InputStream inputStream = resources.openRawResource(R.raw.people);
        InputStream inputStream =new FileInputStream(file);
        try {
            readJsonStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        transactionTime.setEnd(System.currentTimeMillis());
        Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");
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

    public ChiTietPhuTung readMessage(JsonReader reader) throws IOException {
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

            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ChiTietPhuTung( id,  Ten,  QuyCach,  DonViTinh,  SoLuong,  GiaTien);
    }

}
