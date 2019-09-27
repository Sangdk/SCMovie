package com.t3h.scmovie.data.model;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.base.BaseModel;

public class Genre extends BaseModel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private int name;

    public Genre(int id, int name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    //    public Genre(int mId, int mName) {
//        this.mId = mId;
//        this.mName = mName;
//    }
//
//    public int getmId() {
//        return mId;
//    }
//
//    public void setmId(int mId) {
//        this.mId = mId;
//    }
//
//    public int getmName() {
//        return mName;
//    }
//
//    public void setmName(int mName) {
//        this.mName = mName;
//    }
}
