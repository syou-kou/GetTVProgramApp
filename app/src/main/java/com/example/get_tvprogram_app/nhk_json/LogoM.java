package com.example.get_tvprogram_app.nhk_json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogoM {

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("width")
    @Expose
    private String width;

    @SerializedName("height")
    @Expose
    private String height;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

}
