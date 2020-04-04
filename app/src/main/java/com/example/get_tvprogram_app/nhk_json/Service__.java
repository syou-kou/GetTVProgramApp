package com.example.get_tvprogram_app.nhk_json;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Service__ {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("logo_s")
    @Expose
    private LogoS__ logoS;

    @SerializedName("logo_m")
    @Expose
    private LogoM__ logoM;

    @SerializedName("logo_l")
    @Expose
    private LogoL__ logoL;

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

    public LogoS__ getLogoS() {
        return logoS;
    }

    public void setLogoS(LogoS__ logoS) {
        this.logoS = logoS;
    }

    public LogoM__ getLogoM() {
        return logoM;
    }

    public void setLogoM(LogoM__ logoM) {
        this.logoM = logoM;
    }

    public LogoL__ getLogoL() {
        return logoL;
    }

    public void setLogoL(LogoL__ logoL) {
        this.logoL = logoL;
    }

}