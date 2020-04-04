package com.example.get_tvprogram_app.activities;

import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

    //    @RequiresApi(api = Build.VERSION_CODES.P)
//    @Override
//    protected String doInBackground(URL... urls) {
//        WifiNetworkSpecifier.Builder builder = new WifiNetworkSpecifier.Builder();
//        builder.setSsidPattern(new PatternMatcher(this.wifiListItem.getSsid(), PatternMatcher.PATTERN_PREFIX));
//        builder.setBssidPattern(MacAddress.fromString(this.wifiListItem.getBssid()), MacAddress.fromString("ff:ff:ff:00:00:00"));
//        WifiNetworkSpecifier wifiNetworkSpecifier = builder.build();
//
//        final NetworkRequest networkRequest =
//                new NetworkRequest.Builder()
//                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//                .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
//                .addCapability(NetworkCapabilities.NET_CAPABILITY_TRUSTED)
//                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//                .setNetworkSpecifier(wifiNetworkSpecifier)
//                .build();
//
//        final ConnectivityManager connectivityManager =
//                (ConnectivityManager)activity.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
//            @Override
//            public void onAvailable(@NonNull Network network) {
//                // 成功時
//                Log.d(MainActivity.class.getSimpleName(), "成功しました");
//                super.onAvailable(network);
//            }
//            @Override
//            public void onUnavailable() {
//                // 失敗時
//                Log.d(MainActivity.class.getSimpleName(), "失敗しました");
//            }
//        };
//
//        connectivityManager.requestNetwork(networkRequest, networkCallback);
//
//        connectivityManager.unregisterNetworkCallback(networkCallback);
//
//        return null;
//    }

//    @Override
//    protected String doInBackground(URL... urls) {
//        WifiNetworkSuggestion wifiNetworkSuggestion =
//                new WifiNetworkSuggestion.Builder()
//                .setSsid(this.wifiListItem.getSsid())
//                .build();
//        List<WifiNetworkSuggestion> wifiNetworkSuggestionList = new ArrayList<>();
//        wifiNetworkSuggestionList.add(wifiNetworkSuggestion);
//
//        Log.d(MainActivity.class.getSimpleName(), this.wifiListItem.getSsid() + "に接続を試みます");
//        if (wifiManager.addNetworkSuggestions(wifiNetworkSuggestionList) == WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
//            Log.d(MainActivity.class.getSimpleName(), "成功しました");
//        } else {
//            Log.d(MainActivity.class.getSimpleName(), "失敗しました");
//        }
//
//        return null;
//    }

//    @Override
//    protected String doInBackground(URL... urls) {
//        WifiConfiguration wifiConfiguration = new WifiConfiguration();
//        wifiConfiguration.SSID = this.wifiListItem.getSsid();
//        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
//        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
//        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
//        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
//        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
//        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
//        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
//        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
//        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
//        wifiConfiguration.preSharedKey = "\"itpm1107\"";
//        Log.d(MainActivity.class.getSimpleName(), wifiConfiguration.SSID + "に接続を試みます");
//        if (wifiManager.addNetwork(wifiConfiguration) != -1) {
//            Log.d(MainActivity.class.getSimpleName(), "成功しました");
//        } else {
//            Log.d(MainActivity.class.getSimpleName(), "失敗しました");
//        }
//
//        wifiManager.saveConfiguration();
//        wifiManager.updateNetwork(wifiConfiguration);
////        if (wifiManager.enableNetwork(wifiConfiguration.networkId, true)) {
////            Log.d(MainActivity.class.getSimpleName(), "強制接続に成功しました");
////        } else {
////            Log.d(MainActivity.class.getSimpleName(), "強制接続に失敗しました");
////        }
//
//        return null;
//    }

//    @Override
//    protected String doInBackground(URL... urls) {
//
//        final URL url = urls[0];
//        HttpURLConnection con = null;
//        StringBuilder response = new StringBuilder();
//
//        try {
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.setInstanceFollowRedirects(true);
//            con.connect();
//
//            final int statusCode = con.getResponseCode();
//            if (statusCode != HttpURLConnection.HTTP_OK) {
//                Toast.makeText(
//                        this.activity,
//                        "接続に失敗しました statusCode:" + statusCode,
//                        Toast.LENGTH_LONG
//                ).show();
//                return null;
//            }
//
//            final InputStream in = con.getInputStream();
//            String encoding = con.getContentEncoding();
//            if (encoding == null) {
//                encoding = "UTF-8";
//            }
//            final InputStreamReader inReader = new InputStreamReader(in, encoding);
//            final BufferedReader bufReader = new BufferedReader(inReader);
//
//            String line = null;
//            while ((line = bufReader.readLine()) != null) {
//                response.append(line);
//            }
//            bufReader.close();
//            inReader.close();
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (con != null) {
//                con.disconnect();
//            }
//        }
//        return response.toString();
//    }

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
}

