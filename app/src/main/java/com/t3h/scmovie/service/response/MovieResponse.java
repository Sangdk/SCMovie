package com.t3h.scmovie.service.response;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.model.Movie;

import java.util.List;

public class MovieResponse {

    @SerializedName("total_results")
    private int mTotalResults;

    @SerializedName("results")
    private List<Movie> mMovies;

    @SerializedName("total_pages")
    private int mTotalPages;

    public int getTotalPages() {
        return mTotalPages;
    }

    public int getTotalResults() {
        return mTotalResults;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

}
