package com.t3h.scmovie.model;

import com.t3h.scmovie.base.BaseModel;

public class SearchBy extends BaseModel {
    private String name;

    public SearchBy(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
