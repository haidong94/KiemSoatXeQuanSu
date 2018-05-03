package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class DetailVehicle implements Serializable {
    @Id( assignable = true )
    @SerializedName("Id_Vehicle")
    @Expose
    private long idVehicle;
    @SerializedName("Id_License_Plates")
    @Expose
    private Integer idLicensePlates;
    @SerializedName("Id_Category_Vehicle")
    @Expose
    private Integer idCategoryVehicle;
    @SerializedName("Id_Unit")
    @Expose
    private Integer idUnit;
    @SerializedName("License_Plate")
    @Expose
    private String licensePlate;
    @SerializedName("Number_Register")
    @Expose
    private String numberRegister;
    @SerializedName("Date_Register")
    @Expose
    private String dateRegister;
    @SerializedName("Frame_Number")
    @Expose
    private String frameNumber;
    @SerializedName("Machine_Number")
    @Expose
    private String machineNumber;
    @SerializedName("Soure")
    @Expose
    private String soure;
    @SerializedName("Rank_Quanlity")
    @Expose
    private String rankQuanlity;
    @SerializedName("Date_Increase")
    @Expose
    private String dateIncrease;
    @SerializedName("Date_Using")
    @Expose
    private String dateUsing;
    @SerializedName("Notes")
    @Expose
    private String notes;
    @SerializedName("Image_Vehicle")
    @Expose
    private String imageVehicle;

    public long getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(long idVehicle) {
        this.idVehicle = idVehicle;
    }

    public Integer getIdLicensePlates() {
        return idLicensePlates;
    }

    public void setIdLicensePlates(Integer idLicensePlates) {
        this.idLicensePlates = idLicensePlates;
    }

    public Integer getIdCategoryVehicle() {
        return idCategoryVehicle;
    }

    public void setIdCategoryVehicle(Integer idCategoryVehicle) {
        this.idCategoryVehicle = idCategoryVehicle;
    }

    public Integer getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(Integer idUnit) {
        this.idUnit = idUnit;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getNumberRegister() {
        return numberRegister;
    }

    public void setNumberRegister(String numberRegister) {
        this.numberRegister = numberRegister;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getSoure() {
        return soure;
    }

    public void setSoure(String soure) {
        this.soure = soure;
    }

    public String getRankQuanlity() {
        return rankQuanlity;
    }

    public void setRankQuanlity(String rankQuanlity) {
        this.rankQuanlity = rankQuanlity;
    }

    public String getDateIncrease() {
        return dateIncrease;
    }

    public void setDateIncrease(String dateIncrease) {
        this.dateIncrease = dateIncrease;
    }

    public String getDateUsing() {
        return dateUsing;
    }

    public void setDateUsing(String dateUsing) {
        this.dateUsing = dateUsing;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getImageVehicle() {
        return imageVehicle;
    }

    public void setImageVehicle(String imageVehicle) {
        this.imageVehicle = imageVehicle;
    }
}
