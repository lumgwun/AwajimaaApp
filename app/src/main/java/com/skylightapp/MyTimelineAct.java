package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.TimeLineAdapterOthers;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.SuperAdmin.Awajima;

import java.util.ArrayList;

public class MyTimelineAct extends AppCompatActivity {
    private ListView btnSupport;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private AppCompatButton transactions, savings, supportMessage;

    private RecyclerView recyclerView;

    private ArrayList<TimeLine> timeLines;
    private TimeLineAdapterOthers mAdapter;

    DBHelper dbHelper;
    String json;
    int profileID;
    TextView txtTIMELINE;
    int timelineCount;
    private static final String PREF_NAME = "awajima";
    Gson  gson1,gson3,gson6;
    String  json1, json3,json6nIN,name;
    Profile customerProfile, lastProfileUsed;
    private Awajima awajima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_timeline);
        gson = new Gson();
        userProfile= new Profile();
        recyclerView = findViewById(R.id.user_timelines44);
        txtTIMELINE = findViewById(R.id.fragmentTimeline354);
        timeLines = new ArrayList<TimeLine>();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        dbHelper = new DBHelper(this);
        if(userProfile !=null){
            profileID =userProfile.getPID();
            timeLines = userProfile.getProfile_TimeLines();
            if(timeLines !=null){
                timelineCount=timeLines.size();

            }

            if(timelineCount>0){
                txtTIMELINE.setText("Timelines:"+timelineCount);

            }else if(timelineCount==0){
                txtTIMELINE.setText("Timelines:0");

            }
            mAdapter = new TimeLineAdapterOthers(this, R.layout.timeline_row_user, timeLines);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setClickable(true);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);

        }

    }
}