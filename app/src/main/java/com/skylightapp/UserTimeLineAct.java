package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.TimeLineAdapterOthers;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Database.DBHelper;

import java.util.ArrayList;

public class UserTimeLineAct extends AppCompatActivity {
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private AppCompatButton transactions, savings, supportMessage;

    private RecyclerView recyclerView;

    private ArrayList<TimeLine> timeLines;
    private TimeLineAdapterOthers mAdapter;

    DBHelper dbHelper;
    int timelineCount;
    String json;
    private TextView txtNoTm;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_time_line);
        setTitle("My TimeLine");
        recyclerView = findViewById(R.id._timelines);
        txtNoTm = findViewById(R.id.actTimeline);
        timeLines = new ArrayList<TimeLine>();
        userProfile=new Profile();
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        txtNoTm.setText("Timelines:0");


        dbHelper = new DBHelper(this);
        if(userProfile !=null){
            long profileID =userProfile.getPID();
            timeLines = userProfile.getTimeLines();
            timelineCount=timeLines.size();
            if(timelineCount>0){
                txtNoTm.setText("Timelines:"+timelineCount);

            }else if(timelineCount==0){
                txtNoTm.setText("Timelines:0");

            }
            mAdapter = new TimeLineAdapterOthers(this, R.layout.timeline_row_user, timeLines);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            recyclerView.setClickable(true);

        }


    }
}