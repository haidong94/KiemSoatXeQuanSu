package com.example.dong.kiemsoatxequansu.data.importer;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.model.Matter;
import com.example.dong.kiemsoatxequansu.data.model.MatterChild;
import com.example.dong.kiemsoatxequansu.data.model.Specification;
import com.example.dong.kiemsoatxequansu.data.model.Vehicle;
import com.example.dong.kiemsoatxequansu.utils.TransactionTime;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    Box<Vehicle> vihicleBox;
    Box<Matter> matterBox;
    Box<MatterChild> matterChildBox;
    Box<Specification> specificationBox;
    Activity activity;

    public ObjectBoxImporter(Resources resources, Activity activity) {
        this.resources = resources;
        this.activity = activity;
        boxStore = ((App) activity.getApplication()).getBoxStore();
        vihicleBox = boxStore.boxFor(Vehicle.class);
        matterBox = boxStore.boxFor(Matter.class);
        matterChildBox = boxStore.boxFor(MatterChild.class);
        specificationBox = boxStore.boxFor(Specification.class);
    }

    public void importFromJson() throws FileNotFoundException {
        File sdcard = Environment.getExternalStorageDirectory();


        // transaction timer

        transactionTime = new TransactionTime(System.currentTimeMillis());

        //file vehicle
        File fileVehicle = new File(sdcard, "vehicle.txt");
        String getVehicleFromFile = readTextFromFile(fileVehicle);
        List<Vehicle> vihicleList = convertStringToObject(getVehicleFromFile);
        vihicleBox.put(vihicleList);


        //file matter
        File fileMatter = new File(sdcard, "matter.txt");
        String getMatterFromFile = readTextFromFile(fileMatter);
        List<Matter> matterList = convertStringToObjectMatter(getMatterFromFile);
        matterBox.put(matterList);

        //file matter_child
        File fileMatterChild = new File(sdcard, "matter_child.txt");
        String getMatterChildFromFile = readTextFromFile(fileMatterChild);
        List<MatterChild> matterChildList = convertStringToObjectMatterChild(getMatterChildFromFile);
        matterChildBox.put(matterChildList);

        //file specification
        File fileSpecification = new File(sdcard, "specification.txt");
        String getSpecificationFromFile = readTextFromFile(fileSpecification);
        List<Specification> specificationList = convertStringToObjectSpeccification(getSpecificationFromFile);
        specificationBox.put(specificationList);

        transactionTime.setEnd(System.currentTimeMillis());
        Log.d("ObjectBox", "createAllFromJson Task completed in " + transactionTime.getDuration() + "ms");
    }

    /**
     * Lấy danh sách các chi tiết từ file
     * @param getTextFromFile tên file cần lấy
     * @return
     */
    private List<Specification> convertStringToObjectSpeccification(String getTextFromFile) {
        Specification[] gsonObj = new Gson().fromJson(getTextFromFile, Specification[].class);
        List<Specification> list = Arrays.asList(gsonObj);
        return list;
    }

    private List<MatterChild> convertStringToObjectMatterChild(String getTextFromFile) {
        MatterChild[] gsonObj = new Gson().fromJson(getTextFromFile, MatterChild[].class);
        List<MatterChild> list = Arrays.asList(gsonObj);
        return list;
    }
    private List<Matter> convertStringToObjectMatter(String getTextFromFile) {
        Matter[] gsonObj = new Gson().fromJson(getTextFromFile, Matter[].class);
        List<Matter> list = Arrays.asList(gsonObj);
        return list;
    }

    private List<Vehicle> convertStringToObject(String getTextFromFile) {
        Vehicle[] gsonObj = new Gson().fromJson(getTextFromFile, Vehicle[].class);
        List<Vehicle> list = Arrays.asList(gsonObj);
        return list;
    }

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
