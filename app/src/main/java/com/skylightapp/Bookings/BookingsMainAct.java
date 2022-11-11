package com.skylightapp.Bookings;


import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.blongho.country_data.Country;
import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rom4ek.arcnavigationview.ArcNavigationView;

import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CustomerHelpActTab;

import com.skylightapp.MapAndLoc.EmergReportMapA;

import com.skylightapp.Markets.MarketChatTab;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;

import java.util.ArrayList;
import java.util.List;

public class BookingsMainAct extends TabActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "BookingsMainAct.KEY";
    ChipNavigationBar chipNavigationBar;

    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    private int profileID;

    private List<Country> countries;
    private List<Currency> currencies;
    private World world;
    private Customer customer;
    private Account account;
    private DrawerLayout mDrawerLayout;
    private ArcNavigationView  arcNavigationView;
    private AppCompatTextView txtSub,txtUserName;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;
    private FloatingActionButton fab,fabHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_bookings);
        setTitle("Booking Arena");
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        chipNavigationBar = findViewById(R.id.bottom_nav_barC);
        mDrawerLayout = findViewById(R.id.booking_drawer);
        arcNavigationView = findViewById(R.id.nav_view_bookingR);
        arcNavigationView.setNavigationItemSelectedListener(this);
        toolbar = (Toolbar) findViewById(R.id.booking_tBar);
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        mDrawerLayout.addDrawerListener(toggle);
        mDrawerLayout.addDrawerListener(toggle);

        arcNavigationView.bringToFront();
        toggle.syncState();


        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerLayout.bringToFront();
                mDrawerLayout.requestLayout();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };


        txtSub = findViewById(R.id.subscrip_booking);
        txtUserName = findViewById(R.id.booking_username);
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
                Intent myIntent = new Intent(BookingsMainAct.this, EmergReportMapA.class);
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
                Intent myIntent = new Intent(BookingsMainAct.this, CustomerHelpActTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
            }
        });
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
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

                                Intent chat = new Intent(BookingsMainAct.this, MyBoatAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.my_taxiB:

                                Intent shop = new Intent(BookingsMainAct.this, MyTaxiAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.my_trainB:

                                Intent pIntent = new Intent(BookingsMainAct.this, MyTrainAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);

                        }

                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    public void showDrawerButton() {
        mDrawerLayout = findViewById(R.id.booking_drawer);
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.syncState();
    }

    public void showUpButton() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void setupDrawerListener() {
        mDrawerLayout = findViewById(R.id.booking_drawer);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }

    private void setupHeader() {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_view_super);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout = findViewById(R.id.super_drawer);
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        txtSub = headerView.findViewById(R.id.subscrip_booking);
        txtUserName = headerView.findViewById(R.id.booking_username);
        if(SharedPrefUserName !=null){
            txtUserName.setText(SharedPrefUserName);

        }
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.taxi_nav_tab:

                Intent intent = new Intent(BookingsMainAct.this, TaxiDriverRideAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case R.id.nav_train_Tab:
                Intent tIntent = new Intent(this, TrainTripListAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tIntent);
                break;


            case R.id.nav_Boat_Booking:
                Intent loanIntent = new Intent(this, BoatTripListAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);

                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loanIntent);
                break;



            case R.id.nav_live_tab:
                Intent emergencyIntentSper = new Intent(BookingsMainAct.this, MarketChatTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                emergencyIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(emergencyIntentSper);
                break;

            case R.id.nav_tour:
                Intent tourIntent = new Intent(BookingsMainAct.this, TourBookingAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                tourIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(tourIntent);
                break;


            case R.id.n_logSettings:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(BookingsMainAct.this, SignTabMainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }


    public void subsBooking(View view) {
    }
}