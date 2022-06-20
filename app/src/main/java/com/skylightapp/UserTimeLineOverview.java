package com.skylightapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.TimeLineAdapterOthers;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Database.DBHelper;

import java.util.ArrayList;

public class UserTimeLineOverview extends Fragment {
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
    int timelineCount;
    String json;
    private  TextView txtNoTm;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_user_time_line, container, false);

        recyclerView = rootView.findViewById(R.id.user_timelines);
        txtNoTm = rootView.findViewById(R.id.fragmentTimeline3);
        timeLines = new ArrayList<TimeLine>();
        userProfile=new Profile();
        userPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);


        dbHelper = new DBHelper(getContext());
        if(userProfile !=null){
            long profileID =userProfile.getPID();
            timeLines = userProfile.getTimeLines();
            timelineCount=timeLines.size();
            if(timelineCount>0){
                txtNoTm.setText("Timelines:"+timelineCount);

            }else if(timelineCount==0){
                txtNoTm.setText("Timelines:0");

            }
            mAdapter = new TimeLineAdapterOthers(getContext(), R.layout.timeline_row_user, timeLines);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setClickable(true);
            getActivity().setTitle("Your TimeLine");

        }


        return rootView;
    }


}
