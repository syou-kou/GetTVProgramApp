package com.example.get_tvprogram_app.nhk_json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Itemtop {

    @SerializedName("nowonair_list")
    @Expose
    private NowonairList nowonairList;

    public NowonairList getNowonairList() {
        return nowonairList;
    }

    public void setNowonairList(NowonairList nowonairList) {
        this.nowonairList = nowonairList;
    }

}
