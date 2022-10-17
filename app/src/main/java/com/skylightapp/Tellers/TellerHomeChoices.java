package com.skylightapp.Tellers;

import androidx.appcompat.widget.Toolbar;

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

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CusPacksAct;
import com.skylightapp.R;

public class TellerHomeChoices extends TabActivity {

    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    long profileUID2;
    long id;
    String userName;
    String password;
    Customer customer;
    Bundle getBundle;
    String name;
    com.github.clans.fab.FloatingActionButton fab;
    String SharedPrefUserName;
    SharedPreferences sharedPreferences;
    private Gson gson;
    private String json;
    private static final String PREF_NAME = "skylight";


    String SharedPrefUserPassword;
    int SharedPrefCusID;
    String SharedPrefUserMachine;
    int SharedPrefProfileID
                    ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_home_choices);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        // setting up the tab host
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        Toolbar toolbar = (Toolbar) findViewById(R.id.My_toolbar45);
        floatingActionButton = findViewById(R.id.fab_teller24);
        userProfile=new Profile();
        sharedPreferences= getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefUserName=sharedPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=sharedPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=sharedPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine=sharedPreferences.getString("machine", "");
        SharedPrefProfileID=sharedPreferences.getInt("PROFILE_ID", 0);

        Intent intentSignUp = new Intent().setClass(this, TellerDrawerAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Home")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignUp);
        Intent intentSignIn = new Intent().setClass(this, CusPacksAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("History")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignIn);


        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);

        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageTab(userProfile,SharedPrefUserMachine,SharedPrefProfileID);
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(TellerHomeChoices.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void packageTab(Profile userProfile, String sharedPrefUserMachine, int sharedPrefProfileID) {
        Intent intent = new Intent(this, MyCustPackTab.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Profile", (Parcelable) userProfile);
        intent.putExtra("Machine", SharedPrefUserMachine);
        intent.putExtra("ProfileID", SharedPrefProfileID);
        intent.putExtra("PROFILE_ID", SharedPrefProfileID);
        startActivity(intent);




    }

    public void showDrawerButton() {

    }
}