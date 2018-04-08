package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class DetailMatterChild {
    @Id( assignable = true )
    @SerializedName("Id_Detail_Matter_Child")
    @Expose
    private long idDetailMatterChild;
    @SerializedName("Id_Vehicle")
    @Expose
    private Integer idVehicle;
    @SerializedName("Id_Child_Matter")
    @Expose
    private Integer idChildMatter;
    @SerializedName("Id_Matter")
    @Expose
    private Integer idMatter;

    public Integer getIdMatter() {
        return idMatter;
    }

    public void setIdMatter(Integer idMatter) {
        this.idMatter = idMatter;
    }

    public long getIdDetailMatterChild() {
        return idDetailMatterChild;
    }

    public void setIdDetailMatterChild(long idDetailMatterChild) {
        this.idDetailMatterChild = idDetailMatterChild;
    }

    public Integer getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(Integer idVehicle) {
        this.idVehicle = idVehicle;
    }

    public Integer getIdChildMatter() {
        return idChildMatter;
    }

    public void setIdChildMatter(Integer idChildMatter) {
        this.idChildMatter = idChildMatter;
    }
}
