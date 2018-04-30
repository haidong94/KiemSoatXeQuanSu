package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
@Entity
public class SignLicensePlates {
    @Id( assignable = true )
    @SerializedName("Id_License_Plates")
    @Expose
    private long idLicensePlates;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Sign")
    @Expose
    private String sign;

    public long getIdLicensePlates() {
        return idLicensePlates;
    }

    public void setIdLicensePlates(long idLicensePlates) {
        this.idLicensePlates = idLicensePlates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
