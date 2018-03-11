package com.example.dong.kiemsoatxequansu.data.model;
import java.util.List;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by Dong on 05-Mar-18.
 */
@Entity
public class Matter {
    @Id( assignable = true )
    long Id;

    String name;
    List<DetailMatter> phuTungList;

    public Matter() {
    }

    public Matter(long id, String name, List<DetailMatter> phuTungList) {
        Id = id;
        this.name = name;
        this.phuTungList = phuTungList;
    }

    public List<DetailMatter> getPhuTungList() {
        return phuTungList;
    }

    public void setPhuTungList(List<DetailMatter> phuTungList) {
        this.phuTungList = phuTungList;
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
