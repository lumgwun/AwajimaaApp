package com.skylightapp.Customers;

import androidx.preference.PreferenceManager;

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
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.UserPrefActivity;

public class CustUtilTab extends TabActivity {
    FloatingActionButton fab;
    Profile userProfile;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,machine;
    long profileID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_utility);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        // setting up the tab host
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        fab = findViewById(R.id.fab_Util);
        Intent intentSignUp = new Intent().setClass(this, PrivacyPolicy_Web.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Privacy Policy")
                .setIndicator("", resources.getDrawable(R.drawable.birthday))
                .setContent(intentSignUp);

        Intent intentSignIn = new Intent().setClass(this, UserPrefActivity.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("My Pref")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentSignIn);


        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);

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
                Toast.makeText(CustUtilTab.this, tabId, Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}