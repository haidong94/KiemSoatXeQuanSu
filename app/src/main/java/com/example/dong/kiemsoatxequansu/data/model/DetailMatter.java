package com.example.dong.kiemsoatxequansu.data.model;

import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Dong on 05-Mar-18.
 */
@Entity
public class DetailMatter {
    @Id(assignable = true)
    long Id;

    String name;
    List<Specification> chiTietPhuTungList;

    public DetailMatter() {
    }


    public DetailMatter(long id, String name, List<Specification> chiTietPhuTungList) {

        Id = id;
        this.name = name;
        this.chiTietPhuTungList = chiTietPhuTungList;
    }

    public List<Specification> getChiTietPhuTungList() {
        return chiTietPhuTungList;
    }

    public void setChiTietPhuTungList(List<Specification> chiTietPhuTungList) {
        this.chiTietPhuTungList = chiTietPhuTungList;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
