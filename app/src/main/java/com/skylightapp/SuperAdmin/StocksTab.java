package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
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
import com.skylightapp.Customers.CustomerPackageActivity;
import com.skylightapp.R;
import com.skylightapp.Tellers.MyCustPackTab;
import com.skylightapp.Tellers.TellerDrawerAct;
import com.skylightapp.Tellers.TellerHomeChoices;

import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class StocksTab extends TabActivity {
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
    private static final String PREF_NAME = "skylight";


    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,
            SharedPrefProfileID
                    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_stocks_tab);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        // setting up the tab host
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        Toolbar toolbar = (Toolbar) findViewById(R.id.stocks_toolbar);
        floatingActionButton = findViewById(R.id.ic_stocksTab);
        userProfile=new Profile();
        sharedPreferences= getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefUserName=sharedPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=sharedPreferences.getString("USER_PASSWORD", "");
        SharedPrefCusID=sharedPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=sharedPreferences.getString("machine", "");
        SharedPrefProfileID=sharedPreferences.getString("PROFILE_ID", "");
        Intent intentSignUp = new Intent().setClass(this, SuperAllSTList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Stocks List")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignUp);
        Intent intentSignIn = new Intent().setClass(this, SuperStockTrAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("Stocks Transfer")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignIn);

        Intent intentTellerCl = new Intent().setClass(this, TellerCashList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecCL = tabhost
                .newTabSpec("Cash List")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentTellerCl);

        Intent intentSCL = new Intent().setClass(this, SkylightCashList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSCL = tabhost
                .newTabSpec("Skylight Cash")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSCL);


        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);
        tabhost.addTab(tabSpecCL);
        tabhost.addTab(tabSpecSCL);


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
                Toast.makeText(StocksTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void packageTab() {
        Intent intent = new Intent(this, SuperAdminOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", (Parcelable) userProfile);
        intent.putExtra("Machine", SharedPrefUserMachine);
        intent.putExtra("ProfileID", SharedPrefProfileID);
        intent.putExtra(PROFILE_ID, SharedPrefProfileID);
        startActivity(intent);




    }
}