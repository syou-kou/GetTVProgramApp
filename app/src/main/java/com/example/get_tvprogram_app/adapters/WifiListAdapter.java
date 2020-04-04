package com.example.get_tvprogram_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.get_tvprogram_app.R;
import com.example.get_tvprogram_app.pojo.WifiListItem;

import java.util.List;

public class WifiListAdapter extends ArrayAdapter<WifiListItem> {

    private LayoutInflater layoutInflater;
    private List<WifiListItem> dataList;

    public WifiListAdapter(@NonNull Context context, @NonNull List<WifiListItem> dataList) {
        super(context, R.layout.layout_wifilist_item, dataList);
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Nullable
    @Override
    public WifiListItem getItem(int position) { return dataList.get(position); }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListItemHolder holder;

        if (convertView == null) {
            // convertViewがnullの場合は、新規にViewを作成する
            convertView = layoutInflater.inflate(R.layout.layout_wifilist_item, parent, false);
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
        WifiListItem item = dataList.get(position);

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

        return convertView;
    }

    private class ListItemHolder {
        private ImageView wifiLevelView;
        private TextView ssidView;
        private TextView venueNameView;

        ImageView getWifiLevelView() { return wifiLevelView; }
        void setWifiLevelView(ImageView wifiLevelView) { this.wifiLevelView = wifiLevelView; }

        TextView getSsidView() {
            return ssidView;
        }
        void setSsidView(TextView ssidView) {
            this.ssidView = ssidView;
        }

        TextView getVenueNameView() { return venueNameView; }
        void setVenueNameView(TextView venueNameView) { this.venueNameView = venueNameView; }
    }

}
