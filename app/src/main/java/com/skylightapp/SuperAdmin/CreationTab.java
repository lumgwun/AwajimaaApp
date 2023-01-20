package com.skylightapp.SuperAdmin;

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
import com.skylightapp.R;
import com.skylightapp.SignUpAct;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;

public class CreationTab extends TabActivity {
    private AppBarLayout appBarLayout;
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    long profileUID2;
    long id;
    String userName;
    String password;
    Customer customer;
    private static final String PREF_NAME = "skylight";
    Bundle getBundle;
    String name;
    com.github.clans.fab.FloatingActionButton fab;
    String SharedPrefUserName;
    SharedPreferences sharedPreferences;
    private Gson gson;
    private String json;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,
            SharedPrefProfileID
                    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_creation_tab);

        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        // setting up the tab host
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        Toolbar toolbar = (Toolbar) findViewById(R.id.My_toolSuper);
        floatingActionButton = findViewById(R.id.fab_super);
        userProfile=new Profile();
        sharedPreferences= getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefUserName=sharedPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=sharedPreferences.getString(PROFILE_PASSWORD, "");
        SharedPrefCusID=sharedPreferences.getString(CUSTOMER_ID, "");
        SharedPrefUserMachine=sharedPreferences.getString("machine", "");
        SharedPrefProfileID=sharedPreferences.getString(PROFILE_ID, "");
        Intent intentSignUp = new Intent().setClass(this, SignUpAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("New User")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignUp);
        Intent intentSignIn = new Intent().setClass(this, OfficeCreatorAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("Create Office Branch")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignIn);


        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);

        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageTab();
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(CreationTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void packageTab() {
        Intent intent = new Intent(this, SuperAdminOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", (Parcelable) userProfile);
        intent.putExtra("Profile", (Parcelable) userProfile);
        intent.putExtra("Machine", SharedPrefUserMachine);
        intent.putExtra("ProfileID", SharedPrefProfileID);
        intent.putExtra("PROFILE_ID", SharedPrefProfileID);
        startActivity(intent);




    }
}