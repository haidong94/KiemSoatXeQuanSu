package com.example.dong.kiemsoatxequansu.data.model;

import java.util.List;

/**
 * Created by Dong on 08-Mar-18.
 */

public class Vehicle {
    private int id;
    private String name;
    private List<Matter> matterList;

    public Vehicle(int id, String name, List<Matter> matterList) {
        this.id = id;
        this.name = name;
        this.matterList = matterList;
    }

    public Vehicle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Matter> getMatterList() {
        return matterList;
    }

    public void setMatterList(List<Matter> matterList) {
        this.matterList = matterList;
    }
}
