package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.skylightapp.Adapters.MessageAdapter;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

public class TellerMessages extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ArrayList<Message> messageArrayList;


    private MessageAdapter mAdapter;
    private static final String PREF_NAME = "skylight";


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    private Profile userProfile;
    private int profileID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_messages);
        setTitle("Teller Messages");
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        recyclerView = findViewById(R.id.recycler_Mymessage);
        userProfile=new Profile();
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            profileID =userProfile.getPID();
        }

        messageArrayList = new ArrayList<Message>();
        mAdapter = new MessageAdapter(this, messageArrayList);
        dbHelper = new DBHelper(this);

        messageArrayList = dbHelper.getMessagesFromCurrentProfile(profileID);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }
}