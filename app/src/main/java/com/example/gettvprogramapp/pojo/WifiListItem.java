package com.example.gettvprogramapp.pojo;

import android.net.wifi.ScanResult;

public class WifiListItem {

    private String ssid;

    public WifiListItem(ScanResult result) {
        this.ssid = result.SSID;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

}
