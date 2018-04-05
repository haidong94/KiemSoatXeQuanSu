package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
@Entity
public class SubMatterChild {
    @Id( assignable = true )
    @SerializedName("Id_SubMatterChild")
    @Expose
    private long idSubMatterChild;
    @SerializedName("Name_SubMatterChild")
    @Expose
    private String nameSubMatterChild;
    @SerializedName("Id_MatterChild")
    @Expose
    private Integer idMatterChild;

    public long getIdSubMatterChild() {
        return idSubMatterChild;
    }

    public void setIdSubMatterChild(long idSubMatterChild) {
        this.idSubMatterChild = idSubMatterChild;
    }

    public String getNameSubMatterChild() {
        return nameSubMatterChild;
    }

    public void setNameSubMatterChild(String nameSubMatterChild) {
        this.nameSubMatterChild = nameSubMatterChild;
    }

    public Integer getIdMatterChild() {
        return idMatterChild;
    }

    public void setIdMatterChild(Integer idMatterChild) {
        this.idMatterChild = idMatterChild;
    }

}
