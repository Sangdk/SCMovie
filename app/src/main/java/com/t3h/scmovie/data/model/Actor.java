package com.t3h.scmovie.data.model;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.base.BaseModel;

public class Actor extends BaseModel {
    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("profile_path")
    private String mProfilePath;

    @SerializedName("character")
    private String mCharacter;

    @SerializedName("birthday")
    private String mBirthday;

    @SerializedName("biography")
    private String mBiography;

    @SerializedName("place_of_birth")
    private String mPlace;

    @SerializedName("popularity")
    private String mPopularity;

    @SerializedName("gender")
    private String mGender;

    @SerializedName("known_for_department")
    private String mDepartment;

    public Actor() {
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String biography) {
        mBiography = biography;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public String getPopularity() {
        return mPopularity;
    }

    public void setPopularity(String popularity) {
        mPopularity = popularity;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }
}
