package com.skylightapp.Markets;

import static com.skylightapp.Classes.Profile.PROFILE_ID;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Bookings.BookingDrawerAct;
import com.skylightapp.Bookings.LogisticsComLAct;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Bookings.LogisticsMatchAct;
import com.skylightapp.R;
import com.webgeoservices.woosmapgeofencing.Woosmap;

public class LogisticsTab extends TabActivity {
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private MarketBusiness marketBusiness;
    private long bizID;
    private DBHelper dbHelper;
    Gson gson, gson1,gson2;
    String json1, json2,userName, userPassword, userMachine;
    Profile userProfile;
    private int profileID;
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    private Woosmap woosmap;
    String SharedPrefProfileID,dateOfToday;
    com.melnykov.fab.FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_logistics_tab);
        dbHelper = new DBHelper(this);
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
        userProfile = new Profile();
        marketBusiness= new MarketBusiness();

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID= String.valueOf(userPreferences.getLong("PROFILE_ID", 0));
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        userName=userPreferences.getString("PROFILE_USERNAME", "");
        userPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        userMachine=userPreferences.getString("PROFILE_ROLE", "");
        profileID=userPreferences.getInt("PROFILE_ID", 0);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        // setting up the tab host
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        floatingActionButton = findViewById(R.id.fab_tc);
        Intent intentCom = new Intent().setClass(this, LogisticsComLAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecCom = tabhost
                .newTabSpec("Transport Companies")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentCom);

        Intent intentTrips = new Intent().setClass(this, BookingDrawerAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecMy = tabhost
                .newTabSpec("My Bookings")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon1))
                .setContent(intentTrips);


        tabhost.addTab(tabSpecCom);
        tabhost.addTab(tabSpecMy);

        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverAccount();
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(LogisticsTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void recoverAccount() {
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", (Parcelable) userProfile);
        intent.putExtra("Machine", SharedPrefUserMachine);
        intent.putExtra("ProfileID", SharedPrefProfileID);
        intent.putExtra(PROFILE_ID, SharedPrefProfileID);
        startActivity(intent);



    }
}