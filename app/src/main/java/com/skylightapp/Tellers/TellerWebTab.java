package com.skylightapp.Tellers;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

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
import com.skylightapp.OfficialEmailAct;
import com.skylightapp.R;

import static com.skylightapp.Classes.Profile.PROFILE_ID;


public class TellerWebTab extends TabActivity {
    private AppBarLayout appBarLayout;
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
    private static final String PREF_NAME = "awajima";

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,
           SharedPrefProfileID
            ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_web_tab);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        // setting up the tab host
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        Toolbar toolbar = (Toolbar) findViewById(R.id.My_toolbar);
        floatingActionButton = findViewById(R.id.fab_web);
        sharedPreferences= getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefCusID=sharedPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=sharedPreferences.getString("machine", "");
        SharedPrefProfileID=sharedPreferences.getString("PROFILE_ID", "");
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_My);
        Intent intentSignUp = new Intent().setClass(this, WPAdminActivity.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Backend")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignUp);

        Intent intentSignIn = new Intent().setClass(this, StoreManagerWeb.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("Web Store")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon1))
                .setContent(intentSignIn);

        Intent intentM = new Intent().setClass(this, OfficialEmailAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecM = tabhost
                .newTabSpec("Email")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentM);

        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);
        tabhost.addTab(tabSpecM);

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
                Toast.makeText(TellerWebTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void recoverAccount() {
        Intent intent = new Intent(this, TellerHomeChoices.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", (Parcelable) userProfile);
        intent.putExtra("Machine", SharedPrefUserMachine);
        intent.putExtra("ProfileID", SharedPrefProfileID);
        intent.putExtra(PROFILE_ID, SharedPrefProfileID);
        startActivity(intent);



    }
}