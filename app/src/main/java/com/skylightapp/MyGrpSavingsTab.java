package com.skylightapp;

import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;

import java.security.SecureRandom;
import java.util.Random;

public class MyGrpSavingsTab extends TabActivity {
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
    private SharedPreferences userPreferences;
    Gson gson;
    String json,tittle,purpose,firstName,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    Random ran ;
    DBHelper dbHelper;
    long profileID,userID;
    private GroupAccount groupAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_grp_savings_tab);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        // setting up the tab host
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        userProfile=new Profile();
        random= new SecureRandom();
        json = userPreferences.getString("LastProfileUsed", "");
        //Toolbar toolbar = (Toolbar) findViewById(R.id.sign_toolbar);
        floatingActionButton = findViewById(R.id.fab_grpSTab);
        Bundle bundle = getIntent().getExtras() ;
        if(bundle !=null){
            groupAccount = bundle.getParcelable("GroupAccount");

        }
        Intent intentSignUp = new Intent().setClass(this, MyGrpSavings.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("My Grp Users")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentSignUp);

        Intent intentS = new Intent().setClass(this, MyGrpSavingsList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("My Grp Savings")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentS);


        Intent intentSignIn = new Intent().setClass(this, MyNewGrpSavings.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecNew = tabhost
                .newTabSpec("Sign in")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentSignIn);

        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);
        tabhost.addTab(tabSpecNew);

        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                home();
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(MyGrpSavingsTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void home() {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, LoginDirectorActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}