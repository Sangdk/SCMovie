package com.t3h.scmovie.model;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.base.BaseModel;

public class TV extends BaseModel {
    @SerializedName("id")
    private int id;

    @SerializedName("poster_path")
    private String posterPatch;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPatch() {
        return posterPatch;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.posterPatch = backdrop_path;
    }
}
