package com.t3h.scmovie.service.response;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.model.TV;

import java.util.List;

public class TVResponse {
    @SerializedName("total_results")
    private int mTotalResults;

    @SerializedName("results")
    private List<TV> mTVs;

    @SerializedName("total_pages")
    private int mTotalPages;

    public int getmTotalResults() {
        return mTotalResults;
    }

    public List<TV> getListTV() {
        return mTVs;
    }

    public int getmTotalPages() {
        return mTotalPages;
    }
}
