package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class CategoryVehicle {
    @Id( assignable = true )
    @SerializedName("Id_Category_Vehicle")
    @Expose
    private long idCategoryVehicle;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Parent_Id")
    @Expose
    private Integer parentId;

    public long getIdCategoryVehicle() {
        return idCategoryVehicle;
    }

    public void setIdCategoryVehicle(long idCategoryVehicle) {
        this.idCategoryVehicle = idCategoryVehicle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
