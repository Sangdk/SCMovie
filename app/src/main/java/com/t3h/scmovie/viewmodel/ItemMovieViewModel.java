package com.t3h.scmovie.viewmodel;

import androidx.lifecycle.ViewModel;

import com.t3h.scmovie.data.model.Movie;

public class ItemMovieViewModel extends ViewModel {
    private Movie mMovie;

    public ItemMovieViewModel() {
    }

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        this.mMovie = movie;
    }

    public String getTitle(){
        return mMovie.getmTitle();
    }

    public String getBackdropPath() {
        return mMovie.getmBackdropPatch();
    }

    public String getReleaseDate() {
        return mMovie.getmReleaseDate();
    }

    public double getVoteAverage() {
        return mMovie.getmVoteAverage();
    }
}
