package com.t3h.scmovie.service.response;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.model.Movie;

import java.util.List;

public class MovieResponse {

    @SerializedName("total_results")
    private int mTotalResults;

    @SerializedName("results")
    private List<Movie> mMovies;

    public int getmTotalResults() {
        return mTotalResults;
    }

    public void setmTotalResults(int mTotalResults) {
        this.mTotalResults = mTotalResults;
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    public void setmMovies(List<Movie> mMovies) {
        this.mMovies = mMovies;
    }
}
