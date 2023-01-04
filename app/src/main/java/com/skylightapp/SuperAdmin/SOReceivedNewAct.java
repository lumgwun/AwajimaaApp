package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.skylightapp.Adapters.SOReceivedAd;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.SOReceivedDAO;
import com.skylightapp.R;

public class SOReceivedNewAct extends AppCompatActivity {
    SOReceivedAd soReceivedAd;
    private SOReceivedDAO soReceivedDAO;
    private Bundle bundle;
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    private Gson gson;
    private String json;
    private Profile profile;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_soreceived);
        soReceivedDAO= new SOReceivedDAO(this);
        dbHelper = new DBHelper(this);
        bundle= new Bundle();
        gson = new Gson();
        profile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        profile = gson.fromJson(json, Profile.class);
    }
}