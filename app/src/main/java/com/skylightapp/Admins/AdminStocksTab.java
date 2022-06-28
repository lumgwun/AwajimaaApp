package com.skylightapp.Admins;

import androidx.appcompat.widget.Toolbar;

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

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Inventory.BranchStockOv;
import com.skylightapp.Inventory.MyStocksList;
import com.skylightapp.Inventory.StocksTransferAct;
import com.skylightapp.R;

public class AdminStocksTab extends TabActivity {
    public static final String USER_ID_EXTRA_KEY = "AdminStocksTab.USER_ID_EXTRA_KEY";
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson;
    private String json;

    private Profile userProfile;
    private String profileID;
    Intent data;
    private AppBarLayout appBarLayout;
    FloatingActionButton floatingActionButton,floatingActionButton2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_stocks_tab);
        setTitle("Stocks Tab");
        floatingActionButton = findViewById(R.id.fab_admin_Home_in);
        floatingActionButton2 = findViewById(R.id.fab_admin_home);
        Resources resources = getResources();
        TabWidget tabs = findViewById(android.R.id.tabs);
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup();
        Toolbar toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_admin_inv);
        Intent stocksTransferIntent = new Intent().setClass(this, StocksTransferAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec itemCollection = tabhost
                .newTabSpec("Item Collection")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(stocksTransferIntent);


        Intent intentB = new Intent().setClass(this, BranchStockOv.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec stocksOverview = tabhost
                .newTabSpec("Stocks Overview")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentB);

        Intent intentList = new Intent().setClass(this, MyStocksList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec newStocks = tabhost
                .newTabSpec("New Stocks")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentList);

        tabhost.addTab(itemCollection);
        tabhost.addTab(stocksOverview);
        tabhost.addTab(newStocks);
        tabhost.setCurrentTab(0);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminHomeHistory();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminSkyPackage();
            }
        });


    }


    public void adminHomeHistory() {

        Intent intent = new Intent(this, AdminDrawerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("PROFILE_ID", profileID);
        Toast.makeText(this, "Taking you to the Dashboard", Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

    public void adminSkyPackage() {

        Intent intent = new Intent(this, SkylightHistoryAct.class);
        Toast.makeText(this, "Taking you to the History area", Toast.LENGTH_LONG).show();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("PROFILE_ID", profileID);
        startActivity(intent);


    }
}