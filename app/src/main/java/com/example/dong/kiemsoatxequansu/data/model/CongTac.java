package com.example.dong.kiemsoatxequansu.data.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
/**
 * Created by DONG on 31-Oct-17.
 */
@Entity
public class CongTac {
    @Id( assignable = true )
    long Id;

    String Name;

    public CongTac(long id, String name) {
        Id = id;
        Name = name;
    }

    public CongTac() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
