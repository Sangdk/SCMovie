package com.t3h.scmovie.model.tv;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.model.Genre;

import java.util.List;

public class TV {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("origin_country")
    private String country;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("created_by")
    private List<Genre> mGenres;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public List<Genre> getmGenres() {
        return mGenres;
    }

    public void setmGenres(List<Genre> mGenres) {
        this.mGenres = mGenres;
    }
}
