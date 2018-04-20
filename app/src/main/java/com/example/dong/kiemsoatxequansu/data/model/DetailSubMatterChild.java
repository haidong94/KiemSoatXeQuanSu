package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class DetailSubMatterChild {
    @Id(assignable = true)
    @SerializedName("Id_DetailSubMatterChild")
    @Expose
    private long idDetailSubMatterChild;
    @SerializedName("Id_Vehicle")
    @Expose
    private Integer idVehicle;
    @SerializedName("Id_SubMatterChild")
    @Expose
    private Integer idSubMatterChild;


    public long getIdDetailSubMatterChild() {
        return idDetailSubMatterChild;
    }

    public void setIdDetailSubMatterChild(long idDetailSubMatterChild) {
        this.idDetailSubMatterChild = idDetailSubMatterChild;
    }


    public Integer getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(Integer idVehicle) {
        this.idVehicle = idVehicle;
    }

    public Integer getIdSubMatterChild() {
        return idSubMatterChild;
    }

    public void setIdSubMatterChild(Integer idSubMatterChild) {
        this.idSubMatterChild = idSubMatterChild;
    }

}
