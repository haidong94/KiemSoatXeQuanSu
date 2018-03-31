
package com.example.dong.kiemsoatxequansu.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class Specification {
    @Id( assignable = true )
    @SerializedName("Id")
    @Expose
    private long id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Unit")
    @Expose
    private String unit;
    @SerializedName("Quantity")
    @Expose
    private float quantity;
    @SerializedName("Price")
    @Expose
    private Integer price;
    @SerializedName("Id_Vehicle")
    @Expose
    private Integer idVehicle;
    @SerializedName("Id_Matter")
    @Expose
    private Integer idMatter;
    @SerializedName("Id_Child_Matter")
    @Expose
    private Integer idChildMatter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(Integer idVehicle) {
        this.idVehicle = idVehicle;
    }

    public Integer getIdMatter() {
        return idMatter;
    }

    public void setIdMatter(Integer idMatter) {
        this.idMatter = idMatter;
    }

    public Integer getIdChildMatter() {
        return idChildMatter;
    }

    public void setIdChildMatter(Integer idChildMatter) {
        this.idChildMatter = idChildMatter;
    }


}
