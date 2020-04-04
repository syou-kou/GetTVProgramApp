package com.example.get_tvprogram_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.get_tvprogram_app.R;
import com.example.get_tvprogram_app.pojo.TVProgramItem;

import java.util.List;

public class TVProgramListAdapter extends ArrayAdapter<TVProgramItem> {

    private LayoutInflater layoutInflater;
    private List<TVProgramItem> dataList;

    public TVProgramListAdapter(@NonNull Context context, @NonNull List<TVProgramItem> dataList) {
        super(context, R.layout.layout_tvprogramlist_item, dataList);
        layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Nullable
    @Override
    public TVProgramItem getItem(int position) { return dataList.get(position); }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TVProgramListAdapter.ListItemHolder holder;

        if (convertView == null) {
            // convertViewがnullの場合は、新規にViewを作成する
            convertView = layoutInflater.inflate(R.layout.layout_tvprogramlist_item, parent, false);

            TextView titleView = convertView.findViewById(R.id.title);
            TextView subtitleView = convertView.findViewById(R.id.subtitle);
            TextView timeView = convertView.findViewById(R.id.time);

            holder = new TVProgramListAdapter.ListItemHolder();
            holder.setTitleView(titleView);
            holder.setSubtitleView(subtitleView);
            holder.setTimeView(timeView);
            convertView.setTag(holder);
        } else {
            // convertViewがnullでない場合は、Viewを再利用する
            holder = (TVProgramListAdapter.ListItemHolder)convertView.getTag();
        }
        TVProgramItem item = dataList.get(position);

        holder.getTitleView().setText(item.getTitle());
        holder.getSubtitleView().setText(item.getSubtitle());
        holder.getTimeView().setText(item.getStartTime() + " ～ " + item.getEndTime());

        return convertView;
    }

    private class ListItemHolder {
        private TextView titleView;
        private TextView subtitleView;
        private TextView timeView;

        TextView getTitleView() {
            return titleView;
        }
        void setTitleView(TextView titleView) {
            this.titleView = titleView;
        }

        TextView getSubtitleView() {
            return subtitleView;
        }
        void setSubtitleView(TextView subtitleView) {
            this.subtitleView = subtitleView;
        }

        TextView getTimeView() {
            return timeView;
        }
        void setTimeView(TextView timeView) {
            this.timeView = timeView;
        }
    }

}
