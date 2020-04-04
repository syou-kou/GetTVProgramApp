package com.example.get_tvprogram_app.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.get_tvprogram_app.R;
import com.example.get_tvprogram_app.adapters.TVProgramListAdapter;
import com.example.get_tvprogram_app.pojo.JsonStrParser;
import com.example.get_tvprogram_app.pojo.TVProgramItem;

import java.util.ArrayList;
import java.util.List;

public class ShowTVProgramActivity extends AppCompatActivity {

    private ListView listView;
    private TVProgramListAdapter tvProgramListAdapter;
    private List<TVProgramItem> tvProgramItems = new ArrayList<>();

    private static final String KEY_TV_PROGRAM_STR = "key_tv_program_str";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        Toolbar toolbar = findViewById(R.id.show_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.tvprogram_title);

        String jsonStr = getIntent().getStringExtra(KEY_TV_PROGRAM_STR);
        if (null != jsonStr && !("".equals(jsonStr))) {
            JsonStrParser jsonStrParser = new JsonStrParser(getIntent().getStringExtra(KEY_TV_PROGRAM_STR));
            tvProgramItems.add(jsonStrParser.parsePresentProgram());
            tvProgramItems.add(jsonStrParser.parseFollowingProgram());
            tvProgramListAdapter = new TVProgramListAdapter(this, tvProgramItems);
            listView = findViewById(R.id.tv_program_list);
            listView.setAdapter(tvProgramListAdapter);
        } else {
            Toast.makeText(
                    ShowTVProgramActivity.this,
                    "番組表の取得に失敗しました",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public static Intent createIntent(Context context, String tvProgramStr) {
        Intent intent = new Intent(context, ShowTVProgramActivity.class);
        intent.putExtra(KEY_TV_PROGRAM_STR, tvProgramStr);
        return intent;
    }

}
