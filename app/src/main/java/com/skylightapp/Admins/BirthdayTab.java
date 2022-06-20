package com.skylightapp.Admins;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.LoginDirectorActivity;
import com.skylightapp.R;

@SuppressWarnings("deprecation")
public class BirthdayTab extends TabActivity {
    FloatingActionButton fab;
    Profile userProfile;
    long profileID;
    long id;
    String userName;
    String password,machine;
    Bundle getBundle;
    String name;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_birthday_tab);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        // setting up the tab host
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        fab = findViewById(R.id.fab_BirthTab);
        Intent intentSignUp = new Intent().setClass(this, TodayBirthDays.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Today BD")
                .setIndicator("", resources.getDrawable(R.drawable.birthday))
                .setContent(intentSignUp);

        Intent intentSignIn = new Intent().setClass(this, MoroBirthdays.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("Moro BD")
                .setIndicator("", resources.getDrawable(R.drawable.birthday))
                .setContent(intentSignIn);

        Intent intent7 = new Intent().setClass(this, SevenDayBD.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpec7 = tabhost
                .newTabSpec("7 days BD")
                .setIndicator("", resources.getDrawable(R.drawable.birthday))
                .setContent(intent7);


        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);
        tabhost.addTab(tabSpec7);

        tabhost.setCurrentTab(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBD();
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(BirthdayTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void homeBD() {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putString(machine, machine);
        bundle.putParcelable("User",userProfile);
        if(userProfile !=null){
            profileID=userProfile.getPID();

        }
        bundle.putLong("ProfileID", profileID);
        Intent intent = new Intent(this, LoginDirectorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}