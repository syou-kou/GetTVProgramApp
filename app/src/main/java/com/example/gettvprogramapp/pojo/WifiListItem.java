package com.example.gettvprogramapp.pojo;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class WifiListItem {

    public static final int SIGNAL_LEVEL_MAX = 5;

    private String ssid;
    private int level;
    private int signalLevel;
    private String venueName;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public WifiListItem(ScanResult result) {
        this.ssid = result.SSID;
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

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public int getSignalLevel() { return signalLevel; }

    public void setSignalLevel(int signalLevel) { this.signalLevel = signalLevel; }

    public String getVenueName() { return venueName; }

    public void setVenueName(String venueName) { this.venueName = venueName; }
}
