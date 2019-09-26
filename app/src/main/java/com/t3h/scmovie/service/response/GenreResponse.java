package com.t3h.scmovie.service.response;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.data.model.Genre;

import java.util.List;

public class GenreResponse {
    @SerializedName("genres")
    private List<Genre> mGenres;

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        this.mGenres = genres;
    }
}
