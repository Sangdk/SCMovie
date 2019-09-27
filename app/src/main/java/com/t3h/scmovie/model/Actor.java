package com.t3h.scmovie.model;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.base.BaseModel;

import java.util.ArrayList;

public class Actor extends BaseModel {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("profile_path")
    private String profilePatch;

    @SerializedName("popularity")
    private String popularity;

    @SerializedName("gender")
    private int gender;

    @SerializedName("known_for")
    private ArrayList<Movie> knowFor;

    @SerializedName("known_for_department")
    private String knowForDepartment;

    public String getKnowForDepartment() {
        return knowForDepartment;
    }

    public void setKnowForDepartment(String knowForDepartment) {
        this.knowForDepartment = knowForDepartment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePatch() {
        return profilePatch;
    }

    public void setProfilePatch(String profilePatch) {
        this.profilePatch = profilePatch;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public ArrayList<Movie> getKnowFor() {
        return knowFor;
    }

    public void setKnowFor(ArrayList<Movie> knowFor) {
        this.knowFor = knowFor;
    }
}
