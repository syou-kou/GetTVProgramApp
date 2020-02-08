package com.example.gettvprogramapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.example.gettvprogramapp.R;
import com.example.gettvprogramapp.pojo.WifiListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WifiManager wifiManager;
    private ListView mListView;
    private WifiListAdapter mAdapter;
    private List<WifiListItem> wifiListItems = new ArrayList<>();

    private BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(
                    WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (success) {
                scanSuccess();
            } else {
                // scan failure handling
                scanFailure();
            }
        }
    };

    private class WifiListAdapter extends ArrayAdapter<WifiListItem> {

        private LayoutInflater mLayoutInflater;
        private List<WifiListItem> mDataList;

        public WifiListAdapter(@NonNull Context context, @NonNull List<WifiListItem> dataList) {
            super(context, R.layout.layout_wifilist_item, dataList);
            mLayoutInflater = LayoutInflater.from(context);
            mDataList = dataList;
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Nullable
        @Override
        public WifiListItem getItem(int position) { return mDataList.get(position); }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ListItemHolder holder;

            if (convertView == null) {
                // convertViewがnullの場合は、新規にViewを作成する
                convertView = mLayoutInflater.inflate(R.layout.layout_wifilist_item, parent, false);
                TextView ssidView = convertView.findViewById(R.id.ssid);

                holder = new ListItemHolder();
                holder.setSsidView(ssidView);
                convertView.setTag(holder);
            } else {
                // convertViewがnullでない場合は、Viewを再利用する
                holder = (ListItemHolder)convertView.getTag();
            }
            WifiListItem item = mDataList.get(position);
            // タイトルを設定
            holder.getSsidView().setText(item.getSsid());

            return convertView;
        }
    }

    private class ListItemHolder {
        private TextView ssidView;

        TextView getSsidView() {
            return ssidView;
        }

        void setSsidView(TextView ssidView) {
            this.ssidView = ssidView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mListView = findViewById(R.id.wifi_list);
        mAdapter = new WifiListAdapter(this, wifiListItems);
        mListView.setAdapter(mAdapter);

        wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(wifiScanReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiScanReceiver, intentFilter);

        boolean success = wifiManager.startScan();
        if (!success) {
            // scan failure handling
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
        mAdapter.clear();
        // 作成した表示データをAdapterに追加する
        mAdapter.addAll(dataList);
        // Adapterにデータが更新されたことを通知する
        mAdapter.notifyDataSetChanged();
    }

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
        List<ScanResult> results = wifiManager.getScanResults();
    }
}
