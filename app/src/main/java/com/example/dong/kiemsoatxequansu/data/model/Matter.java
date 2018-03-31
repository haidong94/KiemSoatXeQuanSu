
package com.example.dong.kiemsoatxequansu.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class Matter {

    @Id( assignable = true )
    @SerializedName("Id_Matter")
    @Expose
    private long idMatter;
    @SerializedName("Name_Matter")
    @Expose
    private String nameMatter;

    public long getIdMatter() {
        return idMatter;
    }

    public void setIdMatter(long idMatter) {
        this.idMatter = idMatter;
    }

    public String getNameMatter() {
        return nameMatter;
    }

    public void setNameMatter(String nameMatter) {
        this.nameMatter = nameMatter;
    }
}
