package com.t3h.scmovie.data.model;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.base.BaseModel;

public class Movie extends BaseModel {
    @SerializedName("id")
    private int mId;

    @SerializedName("backdrop_patch")
    private String mBackdropPatch;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("overview")
    private String mOverView;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("vote_average")
    private double mVoteAverage;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmBackdropPatch() {
        return mBackdropPatch;
    }

    public void setmBackdropPatch(String mBackdropPatch) {
      
    public int getmBackdropPatch() {
        return mBackdropPatch;
    }

    public void setmBackdropPatch(int mBackdropPatch) {

        this.mBackdropPatch = mBackdropPatch;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmOverView() {
        return mOverView;
    }

    public void setmOverView(String mOverView) {
        this.mOverView = mOverView;
    }

    public String getmPosterPath() {
        return mPosterPath;
    }

    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public double getmVoteAverage() {
        return mVoteAverage;
    }

    public void setmVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }
}
