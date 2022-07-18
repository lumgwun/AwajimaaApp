package com.skylightapp.Inventory;

import androidx.appcompat.app.AppCompatActivity;
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
import com.skylightapp.Admins.AdminHomeChoices;
import com.skylightapp.Admins.SkylightHistoryAct;
import com.skylightapp.Admins.TwilloConsole;
import com.skylightapp.Admins.WebHostingAct;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.OfficialEmailAct;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.ADepositList;
import com.skylightapp.SuperAdmin.SuperAdminOffice;
import com.skylightapp.Tellers.StoreManagerWeb;
import com.skylightapp.Tellers.WPAdminActivity;

public class SuperInvTab extends TabActivity {
    public static final String USER_ID_EXTRA_KEY = "SuperInvTab.USER_ID_EXTRA_KEY";
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
        setContentView(R.layout.act_super_inv_tab);
        floatingActionButton = findViewById(R.id.fab_Superdmin_Home);
        floatingActionButton2 = findViewById(R.id.fab_super_s);
        Resources resources = getResources();
        TabWidget tabs = findViewById(android.R.id.tabs);
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup();
        Toolbar toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        Intent webEmail = new Intent().setClass(this, StocksOverview.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec email = tabhost
                .newTabSpec("Stocks Overview")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(webEmail);

        Intent intentWebHosting = new Intent().setClass(this, StocksTransferAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecHosting = tabhost
                .newTabSpec("Item Collection")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentWebHosting);

        Intent intentWebShop = new Intent().setClass(this, InStocksAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabWebStore = tabhost
                .newTabSpec("New Stocks")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentWebShop);

        Intent intentLast = new Intent().setClass(this, MyStocksList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabLast = tabhost
                .newTabSpec("My Stocks")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentLast);

        Intent intentA = new Intent().setClass(this, ADepositList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabADMIN = tabhost
                .newTabSpec("Deposits")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentA);


        tabhost.addTab(email);
        tabhost.addTab(tabSpecHosting);
        tabhost.addTab(tabWebStore);
        tabhost.addTab(tabLast);

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

        Intent intent = new Intent(this, SuperAdminOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, "Taking you to the Home Act", Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

    public void adminSkyPackage() {

        Intent intent = new Intent(this, SkylightHistoryAct.class);
        Toast.makeText(this, "History", Toast.LENGTH_LONG).show();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("PROFILE_ID", profileID);
        startActivity(intent);



    }
}