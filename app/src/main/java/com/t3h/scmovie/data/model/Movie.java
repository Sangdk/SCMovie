package com.t3h.scmovie.data.model;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.base.BaseModel;

public class Movie extends BaseModel {
    @SerializedName("id")
    private int mId;

    @SerializedName("backdrop_patch")
    private int mBackdropPatch;

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

}
