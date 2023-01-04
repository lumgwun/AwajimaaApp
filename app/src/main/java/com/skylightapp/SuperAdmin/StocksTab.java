package com.skylightapp.SuperAdmin;

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
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

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
    private static final String PREF_NAME = "awajima";
    String SharedPrefUserPassword;
    int SharedPrefCusID;
    String SharedPrefUserMachine;
    int SharedPrefProfileID;
    private MarketBusiness marketBusiness;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_stocks_tab);
        setTitle("Stocks Tab");
        bundle= new Bundle();
        marketBusiness= new MarketBusiness();
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        floatingActionButton = findViewById(R.id.ic_stocksTab);
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
        json = sharedPreferences.getString("LastProfileUsed", "");
        marketBusiness = gson.fromJson(json, MarketBusiness.class);
        bundle.putParcelable("MarketBusiness",marketBusiness);

        Intent intentAll = new Intent().setClass(this, SuperAllSTList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Stocks List")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentAll);
        intentAll.putExtras(bundle);
        Intent intentStockT = new Intent().setClass(this, SuperStockTrAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("Stocks Transfer")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentStockT);
        intentStockT.putExtras(bundle);

        Intent intentTellerCl = new Intent().setClass(this, TellerCashList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecCL = tabhost
                .newTabSpec("Cash List")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentTellerCl);
        intentTellerCl.putExtras(bundle);

        Intent intentSCL = new Intent().setClass(this, AppCashList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSCL = tabhost
                .newTabSpec("Awajima Cash")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSCL);
        intentSCL.putExtras(bundle);


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
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

}