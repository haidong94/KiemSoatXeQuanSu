package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
@Entity
public class DrivingLicense {
    @Id(assignable = true)
    @SerializedName("Id_Driving_License")
    @Expose
    private long idDrivingLicense;
    @SerializedName("Number_Driving_License")
    @Expose
    private String numberDrivingLicense;
    @SerializedName("Date_Register")
    @Expose
    private String dateRegister;
    @SerializedName("Name_Driver")
    @Expose
    private String nameDriver;
    @SerializedName("BirthDay")
    @Expose
    private String birthDay;
    @SerializedName("Code_Person")
    @Expose
    private String codePerson;
    @SerializedName("Code_Date_Ranger")
    @Expose
    private String codeDateRanger;
    @SerializedName("Code_Issued_By")
    @Expose
    private String codeIssuedBy;
    @SerializedName("Date_Of_Enlistment")
    @Expose
    private String dateOfEnlistment;
    @SerializedName("Date_Of_Recruitment")
    @Expose
    private String dateOfRecruitment;
    @SerializedName("Kilometers_Driven")
    @Expose
    private String kilometersDriven;
    @SerializedName("Time_Has_Drove")
    @Expose
    private String timeHasDrove;
    @SerializedName("Village")
    @Expose
    private String village;
    @SerializedName("Town")
    @Expose
    private String town;
    @SerializedName("District")
    @Expose
    private String district;
    @SerializedName("Provinces")
    @Expose
    private String provinces;
    @SerializedName("Number_Officers")
    @Expose
    private String numberOfficers;
    @SerializedName("Name_Unit")
    @Expose
    private String nameUnit;
    @SerializedName("Id_Catalog")
    @Expose
    private Integer idCatalog;
    @SerializedName("Avatar")
    @Expose
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getIdDrivingLicense() {
        return idDrivingLicense;
    }

    public void setIdDrivingLicense(long idDrivingLicense) {
        this.idDrivingLicense = idDrivingLicense;
    }

    public String getNumberDrivingLicense() {
        return numberDrivingLicense;
    }

    public void setNumberDrivingLicense(String numberDrivingLicense) {
        this.numberDrivingLicense = numberDrivingLicense;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getNameDriver() {
        return nameDriver;
    }

    public void setNameDriver(String nameDriver) {
        this.nameDriver = nameDriver;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getCodePerson() {
        return codePerson;
    }

    public void setCodePerson(String codePerson) {
        this.codePerson = codePerson;
    }

    public String getCodeDateRanger() {
        return codeDateRanger;
    }

    public void setCodeDateRanger(String codeDateRanger) {
        this.codeDateRanger = codeDateRanger;
    }

    public String getCodeIssuedBy() {
        return codeIssuedBy;
    }

    public void setCodeIssuedBy(String codeIssuedBy) {
        this.codeIssuedBy = codeIssuedBy;
    }

    public String getDateOfEnlistment() {
        return dateOfEnlistment;
    }

    public void setDateOfEnlistment(String dateOfEnlistment) {
        this.dateOfEnlistment = dateOfEnlistment;
    }

    public String getDateOfRecruitment() {
        return dateOfRecruitment;
    }

    public void setDateOfRecruitment(String dateOfRecruitment) {
        this.dateOfRecruitment = dateOfRecruitment;
    }

    public String getKilometersDriven() {
        return kilometersDriven;
    }

    public void setKilometersDriven(String kilometersDriven) {
        this.kilometersDriven = kilometersDriven;
    }

    public String getTimeHasDrove() {
        return timeHasDrove;
    }

    public void setTimeHasDrove(String timeHasDrove) {
        this.timeHasDrove = timeHasDrove;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getNumberOfficers() {
        return numberOfficers;
    }

    public void setNumberOfficers(String numberOfficers) {
        this.numberOfficers = numberOfficers;
    }

    public String getNameUnit() {
        return nameUnit;
    }

    public void setNameUnit(String nameUnit) {
        this.nameUnit = nameUnit;
    }

    public Integer getIdCatalog() {
        return idCatalog;
    }

    public void setIdCatalog(Integer idCatalog) {
        this.idCatalog = idCatalog;
    }
}
