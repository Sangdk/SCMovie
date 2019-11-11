package com.t3h.scmovie.model;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.base.BaseModel;

import java.util.ArrayList;

public class People extends BaseModel {
    @SerializedName("id")
    private int id;

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

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("place_of_birth")
    private String placeOfBirth;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getKnowForDepartment() {
        return knowForDepartment;
    }

    public void setKnowForDepartment(String knowForDepartment) {
        this.knowForDepartment = knowForDepartment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
