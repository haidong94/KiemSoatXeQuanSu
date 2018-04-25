package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class DrivingLicenseCatalog {
    @Id(assignable = true)
    @SerializedName("Id_Catalog")
    @Expose
    private long idCatalog;
    @SerializedName("Class_Catalog")
    @Expose
    private String classCatalog;
    @SerializedName("Notes_Catalog")
    @Expose
    private String notesCatalog;
    @SerializedName("Expiry_Date")
    @Expose
    private String expiryDate;

    public long getIdCatalog() {
        return idCatalog;
    }

    public void setIdCatalog(long idCatalog) {
        this.idCatalog = idCatalog;
    }

    public String getClassCatalog() {
        return classCatalog;
    }

    public void setClassCatalog(String classCatalog) {
        this.classCatalog = classCatalog;
    }

    public String getNotesCatalog() {
        return notesCatalog;
    }

    public void setNotesCatalog(String notesCatalog) {
        this.notesCatalog = notesCatalog;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
