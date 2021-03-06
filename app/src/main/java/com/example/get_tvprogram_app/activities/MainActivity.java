package com.example.get_tvprogram_app.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import com.example.get_tvprogram_app.R;
import com.example.get_tvprogram_app.adapters.WifiListAdapter;
import com.example.get_tvprogram_app.pojo.WifiListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private WifiManager wifiManager;
    private WifiListAdapter wifiListAdapter;
    private List<WifiListItem> wifiListItems = new ArrayList<>();
    private TVProgramSearchTask tvProgramSearchTask;
    private static final String URL_NHK =
            "http://api.nhk.or.jp/v2/pg/now/130/g1.json?key=Igp36pGY5gdtnMUglypxoIDDGWJmIW6f";

    @RequiresApi(api = Build.VERSION_CODES.P)
    private BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(
                    WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (success) {
                scanSuccess();
            } else {
                scanFailure();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.wifi_title);

        listView = findViewById(R.id.wifi_list);
        wifiListAdapter = new WifiListAdapter(this, wifiListItems);
        listView.setAdapter(wifiListAdapter);
        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                tvProgramSearchTask = new TVProgramSearchTask(MainActivity.this);
                tvProgramSearchTask.setWifiListItem(wifiListAdapter.getItem(position));
                tvProgramSearchTask.setWifiManager(wifiManager);
                tvProgramSearchTask.setTvProgramSearchListener(
                        new TVProgramSearchTask.TVProgramSearchListener() {
                            @Override
                            public void onSuccess(String result) {
                                Intent intent = ShowTVProgramActivity.createIntent(
                                        MainActivity.this, result);
                                startActivity(intent);
                            }
                        }
                );
                try {
                    tvProgramSearchTask.execute(new URL(URL_NHK));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                onResume();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onPause() {
        unregisterReceiver(wifiScanReceiver);
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiScanReceiver, intentFilter);

        boolean success = wifiManager.startScan();
        if (!success) {
            scanFailure();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displayWifiList(List<WifiListItem> dataList) {
        // Adapter内のデータをリセットする
        wifiListAdapter.clear();
        // 作成した表示データをAdapterに追加する
        wifiListAdapter.addAll(dataList);
        // Adapterにデータが更新されたことを通知する
        wifiListAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void scanSuccess() {
        Log.d(MainActivity.class.getSimpleName(), "成功しました");

        List<ScanResult> results = wifiManager.getScanResults();
        List<WifiListItem> dataList = new ArrayList<>();

        for (ScanResult result : results) {
            if (result.SSID.isEmpty()) { continue; }
            Log.d(MainActivity.class.getSimpleName(), "結果:SSID/" + result.SSID);
            WifiListItem wifiListItem = new WifiListItem(result);
            dataList.add(wifiListItem);
        }
        displayWifiList(dataList);
    }

    private void scanFailure() {
        Log.d(MainActivity.class.getSimpleName(), "失敗しました");
    }
}
