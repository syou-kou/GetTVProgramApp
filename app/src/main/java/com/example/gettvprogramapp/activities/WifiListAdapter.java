package com.example.gettvprogramapp.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gettvprogramapp.R;
import com.example.gettvprogramapp.pojo.WifiListItem;

import java.util.List;

public class WifiListAdapter extends ArrayAdapter<WifiListItem> {

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
            ImageView wifiLevelView = convertView.findViewById(R.id.wifi_level);
            TextView ssidView = convertView.findViewById(R.id.ssid);
            TextView venueNameView = convertView.findViewById(R.id.venue_name);

            holder = new ListItemHolder();
            holder.setWifiLevelView(wifiLevelView);
            holder.setSsidView(ssidView);
            holder.setVenueNameView(venueNameView);
            convertView.setTag(holder);
        } else {
            // convertViewがnullでない場合は、Viewを再利用する
            holder = (ListItemHolder)convertView.getTag();
        }
        WifiListItem item = mDataList.get(position);

        // wifiレベルの画像ファイルを設定
        int drawableId;
        switch (item.getSignalLevel()) {
            case 0: drawableId = R.drawable.signal_level_0;
                    break;
            case 1: drawableId = R.drawable.signal_level_1;
                    break;
            case 2: drawableId = R.drawable.signal_level_2;
                    break;
            case 3: drawableId = R.drawable.signal_level_3;
                    break;
            default: drawableId = R.drawable.signal_level_4;
                     break;
        }
        holder.getWifiLevelView().setImageResource(drawableId);

        // SSIDを設定
        holder.getSsidView().setText(item.getSsid());

        // 会場名を設定
        holder.getVenueNameView().setText(item.getVenueName());
        holder.getVenueNameView().setText("テスト");

        return convertView;
    }

    private class ListItemHolder {

        private ImageView wifiLevelView;
        private TextView ssidView;
        private TextView venueNameView;

        public ImageView getWifiLevelView() { return wifiLevelView; }

        public void setWifiLevelView(ImageView wifiLevelView) { this.wifiLevelView = wifiLevelView; }

        TextView getSsidView() {
            return ssidView;
        }

        void setSsidView(TextView ssidView) {
            this.ssidView = ssidView;
        }

        public TextView getVenueNameView() { return venueNameView; }

        public void setVenueNameView(TextView venueNameView) { this.venueNameView = venueNameView; }

    }

}
