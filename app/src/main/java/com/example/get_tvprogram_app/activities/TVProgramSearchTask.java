package com.example.get_tvprogram_app.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.MacAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PatternMatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.get_tvprogram_app.pojo.WifiListItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TVProgramSearchTask extends AsyncTask<URL, Void, String> {

    private Activity activity;          // MainActivity
    private WifiListItem wifiListItem;
    private WifiManager wifiManager;
    private TVProgramSearchListener tvProgramSearchListener;

    public TVProgramSearchTask(Activity activity) {
        this.activity = activity;
    }

    public void setWifiListItem(WifiListItem wifiListItem) {
        this.wifiListItem = wifiListItem;
    }

    public WifiManager getWifiManager() {
        return this.wifiManager;
    }
    public void setWifiManager(WifiManager wifiManager) {
        this.wifiManager = wifiManager;
    }

    public void setTvProgramSearchListener(TVProgramSearchListener tvProgramSearchListener) {
        this.tvProgramSearchListener = tvProgramSearchListener;
    }

    @Override
    protected String doInBackground(URL... urls) {
        return this.doWithFreeWifi(urls);
//        return this.doWithHttpURLConnection(urls);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        if (this.tvProgramSearchListener != null) {
            this.tvProgramSearchListener.onSuccess(values[0].toString());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.tvProgramSearchListener != null) {
            this.tvProgramSearchListener.onSuccess(result);
        }
    }

    interface TVProgramSearchListener {
        void onSuccess(String result);
    }

    private String doWithFreeWifi(URL... urls) {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();

        wifiConfiguration.SSID = "\"" + wifiListItem.getSsid() + "\"";
        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);

        int networkId = wifiManager.addNetwork(wifiConfiguration);
        if (networkId == -1) {
            Log.d(MainActivity.class.getSimpleName(), "失敗しました");
        }

        Log.d(MainActivity.class.getSimpleName(), "成功しました");
        wifiManager.saveConfiguration();
        wifiManager.updateNetwork(wifiConfiguration);
        wifiManager.enableNetwork(networkId, true);

        final URL url = urls[0];
        HttpURLConnection con = null;
        StringBuilder response = new StringBuilder();

        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setInstanceFollowRedirects(true);
            con.connect();

            final int statusCode = con.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                Toast.makeText(
                        this.activity,
                        "接続に失敗しました statusCode:" + statusCode,
                        Toast.LENGTH_LONG
                ).show();
                return null;
            }

            final InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            if (encoding == null) {
                encoding = "UTF-8";
            }
            final InputStreamReader inReader = new InputStreamReader(in, encoding);
            final BufferedReader bufReader = new BufferedReader(inReader);
            String line = null;

            while ((line = bufReader.readLine()) != null) {
                response.append(line);
            }
            bufReader.close();
            inReader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        // wifiManager.disableNetwork(networkId);
        return response.toString();
    }

    private String doWithHttpURLConnection(URL... urls) {
        final URL url = urls[0];
        HttpURLConnection con = null;
        StringBuilder response = new StringBuilder();

        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setInstanceFollowRedirects(true);
            con.connect();

            final int statusCode = con.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                Toast.makeText(
                        this.activity,
                        "接続に失敗しました statusCode:" + statusCode,
                        Toast.LENGTH_LONG
                ).show();
                return null;
            }

            final InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            if (encoding == null) {
                encoding = "UTF-8";
            }
            final InputStreamReader inReader = new InputStreamReader(in, encoding);
            final BufferedReader bufReader = new BufferedReader(inReader);

            String line = null;
            while ((line = bufReader.readLine()) != null) {
                response.append(line);
            }
            bufReader.close();
            inReader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return response.toString();
    }

}

