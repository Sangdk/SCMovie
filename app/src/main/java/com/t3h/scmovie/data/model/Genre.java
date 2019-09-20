package com.t3h.scmovie.data.model;

import com.google.gson.annotations.SerializedName;
import com.t3h.scmovie.base.BaseModel;

public class Genre extends BaseModel {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private int mName;

    public Genre(int mId, int mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public int getmName() {
        return mName;
    }

    public void setmName(int mName) {
        this.mName = mName;
    }
}
