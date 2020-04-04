package com.example.get_tvprogram_app.nhk_json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NowonairList {

    @SerializedName("g1")
    @Expose
    private G1 g1;

    public G1 getG1() {
        return g1;
    }

    public void setG1(G1 g1) {
        this.g1 = g1;
    }

}
