package com.example.dong.kiemsoatxequansu.data.importer;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.model.DetailMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.Matter;
import com.example.dong.kiemsoatxequansu.data.model.MatterChild;
import com.example.dong.kiemsoatxequansu.data.model.Specification;
import com.example.dong.kiemsoatxequansu.data.model.SubMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.Vehicle;
import com.example.dong.kiemsoatxequansu.utils.TransactionTime;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Created by DONG on 31-Oct-17.
 */

public class ObjectBoxImporter {
    private Resources resources;
    private TransactionTime transactionTime;
    private static BoxStore boxStore;
    private Box<Vehicle> vihicleBox;
    private Box<Matter> matterBox;
    private Box<MatterChild> matterChildBox;
    private Box<Specification> specificationBox;
    private Box<DetailMatterChild> detailMatterChildBox;
    private Box<SubMatterChild> subMatterChildBox;
    private Activity activity;

    public static BoxStore getInstance(){
        return boxStore;

    }
    public ObjectBoxImporter(Resources resources, Activity activity) {
        this.resources = resources;
        this.activity = activity;
        boxStore = ((App) activity.getApplication()).getBoxStore();
        vihicleBox = boxStore.boxFor(Vehicle.class);
        matterBox = boxStore.boxFor(Matter.class);
        matterChildBox = boxStore.boxFor(MatterChild.class);
        specificationBox = boxStore.boxFor(Specification.class);
        detailMatterChildBox = boxStore.boxFor(DetailMatterChild.class);
        subMatterChildBox = boxStore.boxFor(SubMatterChild.class);
    }

    public void importFromJson() {
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            // transaction timer
            transactionTime = new TransactionTime(System.currentTimeMillis());

            //file vehicle
            File fileVehicle = new File(sdcard, "vehicle.txt");
            if (fileVehicle.exists()) {
                String getVehicleFromFile = readTextFromFile(fileVehicle);
                List<Vehicle> vihicleList = convertStringToObject(getVehicleFromFile);
                vihicleBox.put(vihicleList);
            }


            //file matter
            File fileMatter = new File(sdcard, "matter.txt");
            if (fileMatter.exists()) {
                String getMatterFromFile = readTextFromFile(fileMatter);
                List<Matter> matterList = convertStringToObjectMatter(getMatterFromFile);
                matterBox.put(matterList);
            }

            //file matter_child
            File fileMatterChild = new File(sdcard, "matter_child.txt");
            if (fileMatterChild.exists()) {
                String getMatterChildFromFile = readTextFromFile(fileMatterChild);
                List<MatterChild> matterChildList = convertStringToObjectMatterChild(getMatterChildFromFile);
                matterChildBox.put(matterChildList);
            }

            //file specification
            File fileSpecification = new File(sdcard, "specification.txt");
            if (fileSpecification.exists()) {
                String getSpecificationFromFile = readTextFromFile(fileSpecification);
                List<Specification> specificationList = convertStringToObjectSpeccification(getSpecificationFromFile);
                specificationBox.put(specificationList);
            }


            //file detail_matter_child
            File fileDetailMatterChild = new File(sdcard, "detail_matter_child.txt");
            if (fileDetailMatterChild.exists()) {
                String getDetailMatterChild = readTextFromFile(fileDetailMatterChild);
                List<DetailMatterChild> detailMatterChildList = convertStringToObjectDetailMatterChild(getDetailMatterChild);
                detailMatterChildBox.put(detailMatterChildList);
            }

            //file sub_matter_child
            File fileSubMatterChild = new File(sdcard, "sub_matter_child.txt");
            if (fileMatterChild.exists()) {
                String getSubMatterChild = readTextFromFile(fileSubMatterChild);
                List<SubMatterChild> subMatterChildList = convertStringToObjectSubMatterChild(getSubMatterChild);
                subMatterChildBox.put(subMatterChildList);
            }

            transactionTime.setEnd(System.currentTimeMillis());
            Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Lấy danh sách các chi tiết con từ file
     * Created_by hhdong 05/02/2018
     * @param getSubMatterChild tên file cần lấy
     * @return danh sách các chi tiết
     */
    private List<SubMatterChild> convertStringToObjectSubMatterChild(String getSubMatterChild) {
        SubMatterChild[] gsonObj = new Gson().fromJson(getSubMatterChild, SubMatterChild[].class);
        return Arrays.asList(gsonObj);
    }

    /**
     * Lấy danh sách các chi tiết con từ file
     * Created_by hhdong 05/02/2018
     * @param getDetailMatterChild tên file cần lấy
     * @return danh sách các chi tiết
     */
    private List<DetailMatterChild> convertStringToObjectDetailMatterChild(String getDetailMatterChild) {
        DetailMatterChild[] gsonObj = new Gson().fromJson(getDetailMatterChild, DetailMatterChild[].class);
        return Arrays.asList(gsonObj);
    }

    /**
     * Lấy danh sách các chi tiết từ file
     * Created_by hhdong 05/02/2018
     * @param getTextFromFile tên file cần lấy
     * @return danh sách các chi tiết
     */
    private List<Specification> convertStringToObjectSpeccification(String getTextFromFile) {
        Specification[] gsonObj = new Gson().fromJson(getTextFromFile, Specification[].class);
        return Arrays.asList(gsonObj);
    }

    /**
     * Lấy danh sách các vật liệu con từ file
     * Created_by hhdong 05/02/2018
     * @param getTextFromFile tên file cần lấy
     * @return danh sách các vật liệu con
     */
    private List<MatterChild> convertStringToObjectMatterChild(String getTextFromFile) {
        MatterChild[] gsonObj = new Gson().fromJson(getTextFromFile, MatterChild[].class);
        return Arrays.asList(gsonObj);
    }

    /**
     * Lấy danh sách các Vật liệu từ file
     * Created_by hhdong 05/02/2018
     * @param getTextFromFile tên file cần lấy
     * @return danh sách các vật liệu
     */
    private List<Matter> convertStringToObjectMatter(String getTextFromFile) {
        Matter[] gsonObj = new Gson().fromJson(getTextFromFile, Matter[].class);
        return Arrays.asList(gsonObj);
    }


    /**
     * Lấy danh sách các xe từ file
     * Created_by hhdong 05/04/2018
     * @param getTextFromFile tên file cần lấy
     * @return danh sách các xe
     */
    private List<Vehicle> convertStringToObject(String getTextFromFile) {
        Vehicle[] gsonObj = new Gson().fromJson(getTextFromFile, Vehicle[].class);
        return Arrays.asList(gsonObj);
    }

    /**
     * Đọc file từ điện thoại sang string
     *
     * @param file file cần đọc
     * @return string
     */
    private String readTextFromFile(File file) {
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                text.append(line);
                line = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            //You'll need to add proper error handling here
        }
        return text.toString();
    }
}
