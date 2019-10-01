package com.t3h.scmovie.service.response;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.model.Actor;
import com.t3h.scmovie.model.Movie;

import java.util.List;

public class ActorResponse {
    @SerializedName("results")
    private List<Actor> actors;

    public List<Actor> getActors() {
        return actors;
    }
}
