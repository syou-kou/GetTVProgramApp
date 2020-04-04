package com.example.get_tvprogram_app.pojo;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class WifiListItem {

    private static final int SIGNAL_LEVEL_MAX = 5;

    private String ssid;
    private String bssid;
    private int level;
    private int signalLevel;
    private String venueName;

    @RequiresApi(api = Build.VERSION_CODES.P)
    public WifiListItem(ScanResult result) {
        this.ssid = result.SSID;
        this.bssid = result.BSSID;
        this.level = result.level;
        this.signalLevel = WifiManager.calculateSignalLevel(this.level, WifiListItem.SIGNAL_LEVEL_MAX);
        this.venueName = result.venueName.toString();
    }

    public String getSsid() {
        return ssid;
    }
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getBssid() {
        return bssid;
    }
    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getSignalLevel() { return signalLevel; }
    public void setSignalLevel(int signalLevel) { this.signalLevel = signalLevel; }

    public String getVenueName() { return venueName; }
    public void setVenueName(String venueName) { this.venueName = venueName; }
}
