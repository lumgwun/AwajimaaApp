package com.skylightapp;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TabHost;

import com.blongho.country_data.Country;
import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Adapters.CurrAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBizSub;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubScriptionTab extends TabActivity {
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "SubScriptionTab.KEY";
    ChipNavigationBar chipNavigationBar;
    GridLayout maingrid;
    Button btnUtility, btnOurPrivacyPolicy,btnInvestment;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    private int profileID;
    CircleImageView profileImage;
    private Profile profile;
    private Currency currency;
    private String currencySymbol;
    private List<Country> countries;
    private List<Currency> currencies;
    private World world;
    private CurrAdapter currencyAdapter;
    private Customer customer;
    private Account account;
    private ArrayList<Account> accountArrayList;
    private ArrayList<MarketBizSub> marketBizSubs;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sub_scription_tab);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //chipNavigationBar = findViewById(R.id.bottom_nav_barC);
        fab = findViewById(R.id.subScription_Tab);
        gson = new Gson();
        countries=new ArrayList<>();
        gson1 = new Gson();
        userProfile=new Profile();
        customer=new Customer();
        account=new Account();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        Resources resources = getResources();
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(getLocalActivityManager());

        Intent intentsupport = new Intent().setClass(this, BizSubQTOptionAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabRespTeam = tabhost
                .newTabSpec("Subscribe")
                .setIndicator("", resources.getDrawable(R.drawable.user3))
                .setContent(intentsupport);

        Intent intentJIVs= new Intent().setClass(this, SubHistoryAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabJIV = tabhost
                .newTabSpec("History")
                .setIndicator("", resources.getDrawable(R.drawable.withdrawal))
                .setContent(intentJIVs);


        tabhost.addTab(tabRespTeam);
        tabhost.addTab(tabJIV);

        tabhost.setCurrentTab(2);
    }
}