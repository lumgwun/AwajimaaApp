package com.skylightapp.Bookings;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

public class TaxiBookingTab extends TabActivity {
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    com.github.clans.fab.FloatingActionButton fab;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,machine;
    int profileUID;
    Bundle bundle;
    private static final String PREF_NAME = "awajima";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_taxi_booking_tab);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        setTitle("Taxi Booking Tab");
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile=new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        bundle = new Bundle();
        if(userProfile !=null){
            machine=userProfile.getProfileMachine();
            profileUID=userProfile.getPID();

        }
        floatingActionButton = findViewById(R.id.fab_taxi_book);
        Intent intentSignUp = new Intent().setClass(this, MyTaxiAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("My Taxi B")
                .setIndicator("", resources.getDrawable(R.drawable.train))
                .setContent(intentSignUp);

        Intent intentSignIn = new Intent().setClass(this, MyTaxiBAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("My Taxisi")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentSignIn);

        Intent intentTD = new Intent().setClass(this, TaxiDriverRideAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpec = tabhost
                .newTabSpec("My Driver Deals")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentTD);

        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);
        tabhost.addTab(tabSpec);

        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recoverAccount(profileUID,machine);
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(TaxiBookingTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}