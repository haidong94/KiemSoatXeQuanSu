
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
public class MatterChild {
    @Id( assignable = true )
    @SerializedName("Id_Child_Matter")
    @Expose
    private long idChildMatter;
    @SerializedName("Name_Child_Matter")
    @Expose
    private String nameChildMatter;


    public long getIdChildMatter() {
        return idChildMatter;
    }

    public void setIdChildMatter(long idChildMatter) {
        this.idChildMatter = idChildMatter;
    }

    public String getNameChildMatter() {
        return nameChildMatter;
    }

    public void setNameChildMatter(String nameChildMatter) {
        this.nameChildMatter = nameChildMatter;
    }


}
