package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UnitOrganization {
    @Id( assignable = true )
    @SerializedName("Id_Unit")
    @Expose
    private long idUnit;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Short_name")
    @Expose
    private String shortName;
    @SerializedName("Parent_id")
    @Expose
    private Integer parentId;

    public long getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(long idUnit) {
        this.idUnit = idUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
