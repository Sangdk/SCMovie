package com.t3h.scmovie.model;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.base.BaseModel;

public class Movie extends BaseModel {
    @SerializedName("id")
    private int id;

    @SerializedName("backdrop_patch")
    private int backdropPatch;

    @SerializedName("title")
    private String title;

    @SerializedName("overview")
    private String overView;

    @SerializedName("poster_path")
    private String posterPatch;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBackdropPatch() {
        return backdropPatch;
    }

    public void setBackdropPatch(int backdropPatch) {
        this.backdropPatch = backdropPatch;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getPosterPatch() {
        return posterPatch;
    }

    public void setPosterPatch(String posterPatch) {
        this.posterPatch = posterPatch;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
