package com.example.dong.kiemsoatxequansu.data.importer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.model.DetailMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.DetailSubMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.Matter;
import com.example.dong.kiemsoatxequansu.data.model.MatterChild;
import com.example.dong.kiemsoatxequansu.data.model.Specification;
import com.example.dong.kiemsoatxequansu.data.model.SubMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.Vehicle;
import com.example.dong.kiemsoatxequansu.utils.Commons;
import com.example.dong.kiemsoatxequansu.utils.TransactionTime;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.File;
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
    private Resources resources;
    private TransactionTime transactionTime;
    private static BoxStore boxStore;
    private Box<Vehicle> vehicleBox;
    private Box<Matter> matterBox;
    private Box<MatterChild> matterChildBox;
    private Box<Specification> specificationBox;
    private Box<DetailSubMatterChild> detailSubMatterChildBox;
    private Box<DetailMatterChild> detailMatterChildBox;
    private Box<SubMatterChild> subMatterChildBox;
    private Activity activity;


    public ObjectBoxImporter(Resources resources, Activity activity) {
        this.resources = resources;
        this.activity = activity;
        boxStore = ((App) activity.getApplication()).getBoxStore();
        vehicleBox = boxStore.boxFor(Vehicle.class);
        matterBox = boxStore.boxFor(Matter.class);
        matterChildBox = boxStore.boxFor(MatterChild.class);
        specificationBox = boxStore.boxFor(Specification.class);
        detailSubMatterChildBox = boxStore.boxFor(DetailSubMatterChild.class);
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
                List<Vehicle> vehicleList = convertStringToObject(getVehicleFromFile);
                List<Vehicle> vehicleListEncode = new ArrayList<>();//danh sách xe sau khi mã hóa
                for (Vehicle vehicle : vehicleList) {
                    //Tạo object chứa dữ liệu mã hóa
                    Vehicle vehicleEncode = new Vehicle();
                    vehicleEncode.setIdVehicle(vehicle.getIdVehicle());
                    if (vehicle.getNameVehicle() != null && !vehicle.getNameVehicle().isEmpty()) {
                        vehicleEncode.setNameVehicle(Commons.encodeString(vehicle.getNameVehicle())); //mã hóa name vehicle
                    }
                    vehicleListEncode.add(vehicleEncode);
                }
                vehicleBox.put(vehicleListEncode);
            }


            //file matter
            File fileMatter = new File(sdcard, "matter.txt");
            if (fileMatter.exists()) {
                String getMatterFromFile = readTextFromFile(fileMatter);
                List<Matter> matterList = convertStringToObjectMatter(getMatterFromFile);
                List<Matter> matterListEncode = new ArrayList<>();//danh sách nguyên liệu sau khi mã hóa
                for (Matter matter : matterList) {
                    //Tạo object chứa dữ liệu mã hóa
                    Matter matterEncode = new Matter();
                    matterEncode.setIdMatter(matter.getIdMatter());
                    if (matter.getNameMatter() != null && !matter.getNameMatter().isEmpty()) {
                        matterEncode.setNameMatter(Commons.encodeString(matter.getNameMatter())); //mã hóa name matter
                    }
                    matterListEncode.add(matterEncode);
                }
                matterBox.put(matterListEncode);
            }

            //file matter_child
            File fileMatterChild = new File(sdcard, "matter_child.txt");
            if (fileMatterChild.exists()) {
                String getMatterChildFromFile = readTextFromFile(fileMatterChild);
                List<MatterChild> matterChildList = convertStringToObjectMatterChild(getMatterChildFromFile);
                List<MatterChild> matterChildListEncode = new ArrayList<>();//danh sách nguyên liệu con sau khi mã hóa
                for (MatterChild matterChild : matterChildList) {
                    //Tạo object chứa dữ liệu mã hóa
                    MatterChild matterChildEncode = new MatterChild();
                    matterChildEncode.setIdChildMatter(matterChild.getIdChildMatter());
                    if (matterChild.getNameChildMatter() != null && !matterChild.getNameChildMatter().isEmpty()) {
                        matterChildEncode.setNameChildMatter(Commons.encodeString(matterChild.getNameChildMatter())); //mã hóa name matter child
                    }
                    matterChildListEncode.add(matterChildEncode);
                }
                matterChildBox.put(matterChildListEncode);
            }

            //file specification
            File fileSpecification = new File(sdcard, "specification.txt");
            if (fileSpecification.exists()) {
                String getSpecificationFromFile = readTextFromFile(fileSpecification);
                List<Specification> specificationList = convertStringToObjectSpeccification(getSpecificationFromFile);
                List<Specification> specificationListEncode = new ArrayList<>();//danh sách chi tiết phụ tùng sau khi mã hóa
                for (Specification specification : specificationList) {
                    //Tạo object chứa dữ liệu mã hóa
                    Specification specificationEncode = new Specification();
                    specificationEncode.setId(specification.getId());
                    specificationEncode.setIdVehicle(specification.getIdVehicle());
                    specificationEncode.setIdMatter(specification.getIdMatter());
                    specificationEncode.setIdChildMatter(specification.getIdChildMatter());
                    specificationEncode.setIdSubMatterChild(specification.getIdSubMatterChild());
                    if (specification.getName() != null && !specification.getName().isEmpty()) {
                        specificationEncode.setName(Commons.encodeString(specification.getName())); //mã hóa tên chi tiết phụ tùng
                    }
                    if (specification.getStyle() != null && !specification.getStyle().isEmpty()) {
                        specificationEncode.setStyle(Commons.encodeString(specification.getStyle()));//mã hóa Quy cách
                    }
                    if (specification.getUnit() != null && !specification.getUnit().isEmpty()) {
                        specificationEncode.setUnit(Commons.encodeString(specification.getUnit()));//mã hóa đơn vị tính
                    }
                    specificationEncode.setPrice(specification.getPrice());
                    specificationEncode.setQuantity(specification.getQuantity());
                    specificationListEncode.add(specificationEncode);
                }
                specificationBox.put(specificationListEncode);
            }


            //file detail_submatter_child
            File fileDetailSubMatterChild = new File(sdcard, "detail_submatter_child.txt");
            if (fileDetailSubMatterChild.exists()) {
                String getDetailSubMatterChild = readTextFromFile(fileDetailSubMatterChild);
                List<DetailSubMatterChild> detailSubMatterChildList = convertStringToObjectDetailSubMatterChild(getDetailSubMatterChild);
                detailSubMatterChildBox.put(detailSubMatterChildList);
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
                List<SubMatterChild> subMatterChildListEncode = new ArrayList<>();//danh sách nguyên liệu con sau khi mã hóa
                for (SubMatterChild subMatterChild : subMatterChildList) {
                    //Tạo object chứa dữ liệu mã hóa
                    SubMatterChild subMatterChildEncode = new SubMatterChild();
                    subMatterChildEncode.setIdMatterChild(subMatterChild.getIdMatterChild());
                    subMatterChildEncode.setNameSubMatterChild(Commons.encodeString(subMatterChild.getNameSubMatterChild())); //mã hóa name subMatterChild
                    subMatterChildEncode.setIdSubMatterChild(subMatterChild.getIdSubMatterChild());
                    subMatterChildListEncode.add(subMatterChildEncode);
                }
                subMatterChildBox.put(subMatterChildListEncode);
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
     *
     * @param getSubMatterChild tên file cần lấy
     * @return danh sách các chi tiết
     */
    private List<SubMatterChild> convertStringToObjectSubMatterChild(String getSubMatterChild) {
        try {
            SubMatterChild[] gsonObj = new Gson().fromJson(getSubMatterChild, SubMatterChild[].class);
            return Arrays.asList(gsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Lấy danh sách các chi tiết con từ file
     * Created_by hhdong 05/02/2018
     *
     * @param getDetailSubMatterChild tên file cần lấy
     * @return danh sách các chi tiết
     */
    private List<DetailSubMatterChild> convertStringToObjectDetailSubMatterChild(String getDetailSubMatterChild) {
        try {
            DetailSubMatterChild[] gsonObj = new Gson().fromJson(getDetailSubMatterChild, DetailSubMatterChild[].class);
            return Arrays.asList(gsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private List<DetailMatterChild> convertStringToObjectDetailMatterChild(String getDetailMatterChild) {
        try {
            DetailMatterChild[] gsonObj = new Gson().fromJson(getDetailMatterChild, DetailMatterChild[].class);
            return Arrays.asList(gsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Lấy danh sách các chi tiết từ file
     * Created_by hhdong 05/02/2018
     *
     * @param getTextFromFile tên file cần lấy
     * @return danh sách các chi tiết
     */
    private List<Specification> convertStringToObjectSpeccification(String getTextFromFile) {
        try {
            Specification[] gsonObj = new Gson().fromJson(getTextFromFile, Specification[].class);
            return Arrays.asList(gsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Lấy danh sách các vật liệu con từ file
     * Created_by hhdong 05/02/2018
     *
     * @param getTextFromFile tên file cần lấy
     * @return danh sách các vật liệu con
     */
    private List<MatterChild> convertStringToObjectMatterChild(String getTextFromFile) {
        try {
            MatterChild[] gsonObj = new Gson().fromJson(getTextFromFile, MatterChild[].class);
            return Arrays.asList(gsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Lấy danh sách các Vật liệu từ file
     * Created_by hhdong 05/02/2018
     *
     * @param getTextFromFile tên file cần lấy
     * @return danh sách các vật liệu
     */
    private List<Matter> convertStringToObjectMatter(String getTextFromFile) {
        try {
            Matter[] gsonObj = new Gson().fromJson(getTextFromFile, Matter[].class);
            return Arrays.asList(gsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    /**
     * Lấy danh sách các xe từ file
     * Created_by hhdong 05/04/2018
     *
     * @param getTextFromFile tên file cần lấy
     * @return danh sách các xe
     */
    private List<Vehicle> convertStringToObject(String getTextFromFile) {
        try {
            Vehicle[] gsonObj = new Gson().fromJson(getTextFromFile, Vehicle[].class);
            return Arrays.asList(gsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Đọc file từ điện thoại sang string
     * Created by Dong on 09-Apr-18
     *
     * @param file file cần đọc
     * @return string chuỗi trả về
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
