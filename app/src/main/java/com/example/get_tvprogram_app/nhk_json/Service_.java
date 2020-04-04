package com.example.get_tvprogram_app.nhk_json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service_ {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("logo_s")
    @Expose
    private LogoS_ logoS;

    @SerializedName("logo_m")
    @Expose
    private LogoM_ logoM;

    @SerializedName("logo_l")
    @Expose
    private LogoL_ logoL;

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

    public LogoS_ getLogoS() {
        return logoS;
    }

    public void setLogoS(LogoS_ logoS) {
        this.logoS = logoS;
    }

    public LogoM_ getLogoM() {
        return logoM;
    }

    public void setLogoM(LogoM_ logoM) {
        this.logoM = logoM;
    }

    public LogoL_ getLogoL() {
        return logoL;
    }

    public void setLogoL(LogoL_ logoL) {
        this.logoL = logoL;
    }

}
