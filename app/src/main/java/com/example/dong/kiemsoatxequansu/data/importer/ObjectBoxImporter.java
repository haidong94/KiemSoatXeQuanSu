package com.example.dong.kiemsoatxequansu.data.importer;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import com.example.dong.kiemsoatxequansu.app.App;
import com.example.dong.kiemsoatxequansu.data.model.CategoryVehicle;
import com.example.dong.kiemsoatxequansu.data.model.DetailMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.DetailSubMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.DetailVehicle;
import com.example.dong.kiemsoatxequansu.data.model.DrivingLicense;
import com.example.dong.kiemsoatxequansu.data.model.DrivingLicenseCatalog;
import com.example.dong.kiemsoatxequansu.data.model.Matter;
import com.example.dong.kiemsoatxequansu.data.model.MatterChild;
import com.example.dong.kiemsoatxequansu.data.model.SignLicensePlates;
import com.example.dong.kiemsoatxequansu.data.model.Specification;
import com.example.dong.kiemsoatxequansu.data.model.SubMatterChild;
import com.example.dong.kiemsoatxequansu.data.model.UnitOrganization;
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

    //Tra cứu phụ tùng xe
    private Box<Vehicle> vehicleBox;
    private Box<Matter> matterBox;
    private Box<MatterChild> matterChildBox;
    private Box<Specification> specificationBox;
    private Box<DetailSubMatterChild> detailSubMatterChildBox;
    private Box<DetailMatterChild> detailMatterChildBox;
    private Box<SubMatterChild> subMatterChildBox;

    //Tra giấy phép lái xe
    private Box<DrivingLicenseCatalog> drivingLicenseCatalogBox;
    private Box<DrivingLicense> drivingLicenseBox;

    //Tra giấy biển lái xe
    private Box<UnitOrganization> unitOrganizationBox;
    private Box<CategoryVehicle> categoryVehicleBox;
    private Box<SignLicensePlates> signLicensePlatesBox;
    private Box<DetailVehicle> detailVehicleBox;

    private Activity activity;

    //Các file dữ liệu tra cứu biến số xe
    private static final String FILE_UNIT_ORGANIZATION="unit_organization.txt";
    private static final String FILE_CATEGORY_VEHICLE="category_vehicle.txt";
    private static final String FILE_SIGN_LICENSE_PLATES="sign_license_plates.txt";
    private static final String FILE_DETAIL_VEHICLE="detail_vehicle.txt";

    //Các file dữ liệu tra cứu giấy đăng kí xe
    private static final String FILE_CATALOG="driving_license_catalog.txt";
    private static final String FILE_DRIVING_LICENSE="driving_license.txt";

    //các file dữ liệu tra cứu phụ tùng xe
    private static final String FILE_VEHICLE="vehicle.txt";
    private static final String FILE_MATTER="matter.txt";
    private static final String FILE_MATTER_CHILD="matter_child.txt";
    private static final String FILE_SPECIFICATION="specification.txt";
    private static final String FILE_DETAIL_SUBMATTER_CHILD="detail_submatter_child.txt";
    private static final String FILE_DETAIL_MATTER_CHILD="detail_matter_child.txt";
    private static final String FILE_SUBMATTER_CHILD="sub_matter_child.txt";


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

        unitOrganizationBox=boxStore.boxFor(UnitOrganization.class);
        categoryVehicleBox=boxStore.boxFor(CategoryVehicle.class);
        signLicensePlatesBox=boxStore.boxFor(SignLicensePlates.class);
        detailVehicleBox=boxStore.boxFor(DetailVehicle.class);

        drivingLicenseCatalogBox = boxStore.boxFor(DrivingLicenseCatalog.class);
        drivingLicenseBox = boxStore.boxFor(DrivingLicense.class);
    }
    /**
     * Import dữ liệu biển số xe: gồm 4 bảng (bảng nhóm xe, bảng đơn vị, bảng kí hiệu biển số và bảng chi tiết xe)
     * Created by Dong on 24-Apr-18
     */
    public void importDrivingLicensePlatesFromJson() {
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            // transaction timer
            transactionTime = new TransactionTime(System.currentTimeMillis());

            //FILE_UNIT_ORGANIZATION
            File fileUnitOrganization = new File(sdcard, FILE_UNIT_ORGANIZATION);
            if (fileUnitOrganization.exists()) {
                String getCatalogDrivingFromFile = readTextFromFile(fileUnitOrganization);
                List<UnitOrganization> unitOrganizationList = convertStringToObjectUnitOrganization(getCatalogDrivingFromFile);
                List<UnitOrganization> unitOrganizationListEncode = new ArrayList<>();
                for (UnitOrganization unitOrganization : unitOrganizationList) {
                    //Tạo object chứa dữ liệu mã hóa
                    UnitOrganization unitOrganizationEncode = new UnitOrganization();
                    unitOrganizationEncode.setIdUnit(unitOrganization.getIdUnit());
                    unitOrganizationEncode.setParentId(unitOrganization.getParentId());
                    unitOrganizationEncode.setName(Commons.encodeString(unitOrganization.getName()));
                    unitOrganizationEncode.setShortName(Commons.encodeString(unitOrganization.getShortName()));

                    unitOrganizationListEncode.add(unitOrganizationEncode);
                }
                unitOrganizationBox.put(unitOrganizationListEncode);
            }

            //FILE_CATEGORY_VEHICLE
            File fileCategoryVehicle = new File(sdcard, FILE_CATEGORY_VEHICLE);
            if (fileCategoryVehicle.exists()) {
                String getCatalogDrivingFromFile = readTextFromFile(fileCategoryVehicle);
                List<CategoryVehicle> categoryVehicleList = convertStringToObjectCategoryVehicle(getCatalogDrivingFromFile);
                List<CategoryVehicle> categoryVehicleListEncode = new ArrayList<>();
                for (CategoryVehicle categoryVehicle : categoryVehicleList) {
                    //Tạo object chứa dữ liệu mã hóa
                    CategoryVehicle categoryVehicleEncode = new CategoryVehicle();
                    categoryVehicleEncode.setIdCategoryVehicle(categoryVehicle.getIdCategoryVehicle());
                    categoryVehicleEncode.setParentId(categoryVehicle.getParentId());
                    categoryVehicleEncode.setName(Commons.encodeString(categoryVehicle.getName()));

                    categoryVehicleListEncode.add(categoryVehicleEncode);
                }
                categoryVehicleBox.put(categoryVehicleListEncode);
            }

            //FILE_SIGN_LICENSE_PLATES
            File fileSignLicensePlates = new File(sdcard, FILE_SIGN_LICENSE_PLATES);
            if (fileSignLicensePlates.exists()) {
                String getCatalogDrivingFromFile = readTextFromFile(fileSignLicensePlates);
                List<SignLicensePlates> signLicensePlatesList = convertStringToObjectSignLicensePlates(getCatalogDrivingFromFile);
                List<SignLicensePlates> signLicensePlatesListEncode = new ArrayList<>();
                for (SignLicensePlates signLicensePlates : signLicensePlatesList) {
                    //Tạo object chứa dữ liệu mã hóa
                    SignLicensePlates signLicensePlatesEncode = new SignLicensePlates();
                    signLicensePlatesEncode.setIdLicensePlates(signLicensePlates.getIdLicensePlates());
                    signLicensePlatesEncode.setName(Commons.encodeString(signLicensePlates.getName()));
                    signLicensePlatesEncode.setSign(Commons.encodeString(signLicensePlates.getSign()));

                    signLicensePlatesListEncode.add(signLicensePlatesEncode);
                }
                signLicensePlatesBox.put(signLicensePlatesListEncode);
            }

            //FILE_DETAIL_VEHICLE
            File fileDetailVehicle = new File(sdcard, FILE_DETAIL_VEHICLE);
            if (fileDetailVehicle.exists()) {
                String getCatalogDrivingFromFile = readTextFromFile(fileDetailVehicle);
                List<DetailVehicle> detailVehicleList = convertStringToObjectDetailVehicle(getCatalogDrivingFromFile);
                List<DetailVehicle> detailVehicleListEncode = new ArrayList<>();
                for (DetailVehicle detailVehicle : detailVehicleList) {
                    //Tạo object chứa dữ liệu mã hóa
                    DetailVehicle detailVehicleEncode = new DetailVehicle();
                    detailVehicleEncode.setIdVehicle(detailVehicle.getIdVehicle());
                    detailVehicleEncode.setIdCategoryVehicle(detailVehicle.getIdCategoryVehicle());
                    detailVehicleEncode.setIdLicensePlates(detailVehicle.getIdLicensePlates());
                    detailVehicleEncode.setIdUnit(detailVehicle.getIdUnit());
                    detailVehicleEncode.setDateIncrease(Commons.encodeString(detailVehicle.getDateIncrease()));
                    detailVehicleEncode.setDateRegister(Commons.encodeString(detailVehicle.getDateRegister()));
                    detailVehicleEncode.setFrameNumber(Commons.encodeString(detailVehicle.getFrameNumber()));
                    detailVehicleEncode.setMachineNumber(Commons.encodeString(detailVehicle.getMachineNumber()));
                    detailVehicleEncode.setDateUsing(Commons.encodeString(detailVehicle.getDateUsing()));
                    detailVehicleEncode.setRankQuanlity(Commons.encodeString(detailVehicle.getRankQuanlity()));
                    detailVehicleEncode.setNumberRegister(Commons.encodeString(detailVehicle.getNumberRegister()));
                    detailVehicleEncode.setSoure(Commons.encodeString(detailVehicle.getSoure()));
                    detailVehicleEncode.setLicensePlate(Commons.encodeString(detailVehicle.getLicensePlate()));
                    detailVehicleEncode.setRankQuanlity(Commons.encodeString(detailVehicle.getRankQuanlity()));
                    if (detailVehicle.getNotes() != null && !detailVehicle.getNotes().isEmpty()) {
                        detailVehicleEncode.setNotes(Commons.encodeString(detailVehicle.getNotes()));
                    }
                    if (detailVehicle.getImageVehicle() != null && !detailVehicle.getImageVehicle().isEmpty()) {
                        detailVehicleEncode.setImageVehicle(Commons.encodeString(detailVehicle.getImageVehicle()));
                    }


                    detailVehicleListEncode.add(detailVehicleEncode);
                }
                detailVehicleBox.put(detailVehicleListEncode);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * Import dữ liệu giấy phép lái xe: gồm 2 bảng (bảng danh mục giấy phép lái xe và bảng giấy phép lái xe)
     * Created by Dong on 24-Apr-18
     */
    public void importDrivingLicenseFromJson() {

        try {
            File sdcard = Environment.getExternalStorageDirectory();
            // transaction timer
            transactionTime = new TransactionTime(System.currentTimeMillis());

            //file driving_license_catalog
            File fileCatalog = new File(sdcard, FILE_CATALOG);
            if (fileCatalog.exists()) {
                String getCatalogDrivingFromFile = readTextFromFile(fileCatalog);
                List<DrivingLicenseCatalog> catalogList = convertStringToObjectCatalog(getCatalogDrivingFromFile);
                List<DrivingLicenseCatalog> catalogListEncode = new ArrayList<>();
                for (DrivingLicenseCatalog catalog : catalogList) {
                    //Tạo object chứa dữ liệu mã hóa
                    DrivingLicenseCatalog drivingLicenseCatalog = new DrivingLicenseCatalog();
                    drivingLicenseCatalog.setIdCatalog(catalog.getIdCatalog());
                    drivingLicenseCatalog.setClassCatalog(Commons.encodeString(catalog.getClassCatalog()));
                    drivingLicenseCatalog.setNotesCatalog(Commons.encodeString(catalog.getNotesCatalog()));
                    if (catalog.getExpiryDate() != null) {
                        drivingLicenseCatalog.setExpiryDate(Commons.encodeString(catalog.getExpiryDate()));
                    }
                    catalogListEncode.add(drivingLicenseCatalog);
                }
                drivingLicenseCatalogBox.put(catalogListEncode);
            }


            //file driving_license
            File fileDrivingLicense = new File(sdcard, FILE_DRIVING_LICENSE);
            if (fileDrivingLicense.exists()) {
                String getDrivingLicenseFromFile = readTextFromFile(fileDrivingLicense);
                List<DrivingLicense> drivingLicenseList = convertStringToObjectDrivingLicense(getDrivingLicenseFromFile);
                List<DrivingLicense> drivingLicenseListEncode = new ArrayList<>();
                for (DrivingLicense drivingLicense : drivingLicenseList) {
                    //Tạo object chứa dữ liệu mã hóa
                    DrivingLicense drivingLicenseEncode = new DrivingLicense();
                    drivingLicenseEncode.setIdDrivingLicense(drivingLicense.getIdDrivingLicense());

                    drivingLicenseEncode.setNumberDrivingLicense(Commons.encodeString(drivingLicense.getNumberDrivingLicense()));
                    drivingLicenseEncode.setNameDriver(Commons.encodeString(drivingLicense.getNameDriver()));
                    drivingLicenseEncode.setDateRegister(Commons.encodeString(drivingLicense.getDateRegister()));
                    drivingLicenseEncode.setBirthDay(Commons.encodeString(drivingLicense.getBirthDay()));
                    drivingLicenseEncode.setCodePerson(Commons.encodeString(drivingLicense.getCodePerson()));
                    drivingLicenseEncode.setCodeDateRanger(Commons.encodeString(drivingLicense.getCodeDateRanger()));
                    drivingLicenseEncode.setCodeIssuedBy(Commons.encodeString(drivingLicense.getCodeIssuedBy()));
                    drivingLicenseEncode.setDateOfEnlistment(Commons.encodeString(drivingLicense.getDateOfEnlistment()));
                    drivingLicenseEncode.setDateOfRecruitment(Commons.encodeString(drivingLicense.getDateOfRecruitment()));
                    drivingLicenseEncode.setKilometersDriven(Commons.encodeString(drivingLicense.getKilometersDriven()));
                    drivingLicenseEncode.setTimeHasDrove(Commons.encodeString(drivingLicense.getTimeHasDrove()));
                    drivingLicenseEncode.setVillage(Commons.encodeString(drivingLicense.getVillage()));
                    drivingLicenseEncode.setTown(Commons.encodeString(drivingLicense.getTown()));
                    drivingLicenseEncode.setDistrict(Commons.encodeString(drivingLicense.getDistrict()));
                    drivingLicenseEncode.setProvinces(Commons.encodeString(drivingLicense.getProvinces()));
                    drivingLicenseEncode.setNumberOfficers(Commons.encodeString(drivingLicense.getNumberOfficers()));
                    drivingLicenseEncode.setNameUnit(Commons.encodeString(drivingLicense.getNameUnit()));
                    drivingLicenseEncode.setIdCatalog(drivingLicense.getIdCatalog());
                    if (!drivingLicense.getAvatar().isEmpty() && drivingLicense.getAvatar() != null) {
                        drivingLicenseEncode.setAvatar(Commons.encodeString(drivingLicense.getAvatar()));
                    }

                    drivingLicenseListEncode.add(drivingLicenseEncode);
                }
                drivingLicenseBox.put(drivingLicenseListEncode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Import dữ liệu tra cứu phụ tùng xe
     * Created by Dong on 24-Apr-18
     */
    public void importFromJson() {

        try {

            File sdcard = Environment.getExternalStorageDirectory();
            // transaction timer
            transactionTime = new TransactionTime(System.currentTimeMillis());

            //file vehicle
            File fileVehicle = new File(sdcard, FILE_VEHICLE);
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
            File fileMatter = new File(sdcard, FILE_MATTER);
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
            File fileMatterChild = new File(sdcard, FILE_MATTER_CHILD);
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
            File fileSpecification = new File(sdcard, FILE_SPECIFICATION);
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
            File fileDetailSubMatterChild = new File(sdcard, FILE_DETAIL_SUBMATTER_CHILD);
            if (fileDetailSubMatterChild.exists()) {
                String getDetailSubMatterChild = readTextFromFile(fileDetailSubMatterChild);
                List<DetailSubMatterChild> detailSubMatterChildList = convertStringToObjectDetailSubMatterChild(getDetailSubMatterChild);
                detailSubMatterChildBox.put(detailSubMatterChildList);
            }

            //file detail_matter_child
            File fileDetailMatterChild = new File(sdcard, FILE_DETAIL_MATTER_CHILD);
            if (fileDetailMatterChild.exists()) {
                String getDetailMatterChild = readTextFromFile(fileDetailMatterChild);
                List<DetailMatterChild> detailMatterChildList = convertStringToObjectDetailMatterChild(getDetailMatterChild);
                detailMatterChildBox.put(detailMatterChildList);
            }

            //file sub_matter_child
            File fileSubMatterChild = new File(sdcard, FILE_SUBMATTER_CHILD);
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
     * Lấy danh sách các Vật liệu từ file
     * Created_by hhdong 05/02/2018
     *
     * @param getTextFromFile tên file cần lấy
     * @return danh sách các vật liệu
     */
    private List<UnitOrganization> convertStringToObjectUnitOrganization(String getTextFromFile) {
        try {
            UnitOrganization[] gsonObj = new Gson().fromJson(getTextFromFile, UnitOrganization[].class);
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
    private List<DetailVehicle> convertStringToObjectDetailVehicle(String getTextFromFile) {
        try {
            DetailVehicle[] gsonObj = new Gson().fromJson(getTextFromFile, DetailVehicle[].class);
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
    private List<SignLicensePlates> convertStringToObjectSignLicensePlates(String getTextFromFile) {
        try {
            SignLicensePlates[] gsonObj = new Gson().fromJson(getTextFromFile, SignLicensePlates[].class);
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
    private List<CategoryVehicle> convertStringToObjectCategoryVehicle(String getTextFromFile) {
        try {
            CategoryVehicle[] gsonObj = new Gson().fromJson(getTextFromFile, CategoryVehicle[].class);
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
    private List<DrivingLicenseCatalog> convertStringToObjectCatalog(String getTextFromFile) {
        try {
            DrivingLicenseCatalog[] gsonObj = new Gson().fromJson(getTextFromFile, DrivingLicenseCatalog[].class);
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
    private List<DrivingLicense> convertStringToObjectDrivingLicense(String getTextFromFile) {
        try {
            DrivingLicense[] gsonObj = new Gson().fromJson(getTextFromFile, DrivingLicense[].class);
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
