package com.t3h.scmovie.service.response;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.model.People;

import java.util.List;

public class PeopleResponse {
    @SerializedName("results")
    private List<People> people;

    @SerializedName("cast")
    private List<People> casts;

    @SerializedName("crew")
    private List<People> crews;

    @SerializedName("total_pages")
    private int mTotalPages;

    public List<People> getCasts() {
        return casts;
    }

    public List<People> getCrews() {
        return crews;
    }

    public List<People> getPeople() {
        return people;
    }

    public int getTotalPages() {
        return mTotalPages;
    }

}
