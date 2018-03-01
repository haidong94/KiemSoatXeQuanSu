package com.example.dong.kiemsoatxequansu.data.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by DONG on 31-Oct-17.
 */
@Entity
public class ChiTietPhuTung {
    @Id( assignable = true )
    long Id;

    String Ten;
    String QuyCach;
    String DVT;
    String SoLuong;
    String DonGia;

    public ChiTietPhuTung() {
    }

    public ChiTietPhuTung(long id, String ten, String quyCach, String DVT, String soLuong, String donGia) {
        Id = id;
        Ten = ten;
        QuyCach = quyCach;
        this.DVT = DVT;
        SoLuong = soLuong;
        DonGia = donGia;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getQuyCach() {
        return QuyCach;
    }

    public void setQuyCach(String quyCach) {
        QuyCach = quyCach;
    }

    public String getDVT() {
        return DVT;
    }

    public void setDVT(String DVT) {
        this.DVT = DVT;
    }

    public String getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(String soLuong) {
        SoLuong = soLuong;
    }

    public String getDonGia() {
        return DonGia;
    }

    public void setDonGia(String donGia) {
        DonGia = donGia;
    }
}
