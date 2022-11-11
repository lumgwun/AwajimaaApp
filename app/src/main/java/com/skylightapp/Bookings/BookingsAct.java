package com.skylightapp.Bookings;


import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
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
import com.skylightapp.Customers.CustomerHelpActTab;

import com.skylightapp.MapAndLoc.EmergReportMapA;
import com.skylightapp.MarketClasses.MarketBizSubScription;

import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BookingsAct extends TabActivity {
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "BookingsAct.KEY";
    ChipNavigationBar chipNavigationBar;

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
    private ArrayList<MarketBizSubScription> marketBizSubScriptions;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;
    private FloatingActionButton fab,fabHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bookings);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        chipNavigationBar = findViewById(R.id.bottom_nav_barC);
        fab = findViewById(R.id.booking_Fab);
        fabHome = findViewById(R.id.fab_BBB);
        gson = new Gson();
        World.init(this);
        countries=new ArrayList<>();
        gson1 = new Gson();
        countries=World.getAllCountries();
        currencies= new ArrayList<>();
        //currency= new Currency();
        userProfile=new Profile();
        customer=new Customer();
        account=new Account();
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(BookingsAct.this, EmergReportMapA.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(BookingsAct.this, CustomerHelpActTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
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

        Intent intentBoatB = new Intent().setClass(this, BoatTripListAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec boatTrips = tabhost
                .newTabSpec("Boat Trips")
                .setIndicator("", resources.getDrawable(R.drawable.boat33))
                .setContent(intentBoatB);

        Intent intentTransactions= new Intent().setClass(this, TaxiDriverRideAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec taxiTrips = tabhost
                .newTabSpec("Taxi Trips")
                .setIndicator("", resources.getDrawable(R.drawable.ic_taxi33))
                .setContent(intentTransactions);

        Intent intentTrain= new Intent().setClass(this, TrainTripListAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabTrain = tabhost
                .newTabSpec("Train Trips")
                .setIndicator("", resources.getDrawable(R.drawable.train))
                .setContent(intentTrain);

        tabhost.addTab(boatTrips);
        tabhost.addTab(taxiTrips);
        tabhost.addTab(tabTrain);

        tabhost.setCurrentTab(2);
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){

                            case R.id.my_boatB:

                                Intent chat = new Intent(BookingsAct.this, MyBoatAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.my_taxiB:

                                Intent shop = new Intent(BookingsAct.this, MyTaxiAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.my_trainB:

                                Intent pIntent = new Intent(BookingsAct.this, MyTrainAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);

                        }

                    }
                });
    }
}