package com.skylightapp.MapAndLoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import com.blongho.country_data.Country;
import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Adapters.CurrAdapter;
import com.skylightapp.Bookings.BoatBookingTab;
import com.skylightapp.Bookings.CusFirebaseMessaging;
import com.skylightapp.Bookings.SessionTab;
import com.skylightapp.Bookings.TaxiBookingTab;
import com.skylightapp.Bookings.TrainBookingTab;
import com.skylightapp.Bookings.TripBookingAct;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.HomeFragment;
import com.skylightapp.Classes.MyAccountFragment;
import com.skylightapp.Classes.MyCartFragment;
import com.skylightapp.Classes.MyOrdersFragment;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.MyAcctOverViewAct;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Customers.PackListTab;
import com.skylightapp.MarketClasses.MarketBizRegulator;
import com.skylightapp.MarketClasses.MarketBizSubScription;
import com.skylightapp.Markets.BizOfficeAct;
import com.skylightapp.Markets.MarketChatTab;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.MyGrpSavingsTab;
import com.skylightapp.MyInsTab;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.SuperAdmin.StocksTab;
import com.skylightapp.Tellers.UserTxAct;
import com.skylightapp.UserPrefActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class NOSDRAOffice extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "NOSDRAOffice.KEY";
    ChipNavigationBar chipNavigationBar;
    GridLayout maingrid;
    Button btnUtility, btnOurPrivacyPolicy,btnInvestment;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
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
    CardView cardViewPackges,cardViewGrpSavings,cardViewHistory, cardViewStandingOrders, cardViewOrders, cardViewSupport;
    private FloatingActionButton fab;
    private MarketBizRegulator marketBizRegulator;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nosdra_drawer);
        Objects.requireNonNull(getSupportActionBar()).setTitle("NOSDRA BACK OFFICE");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.lo_awaj);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        chipNavigationBar = findViewById(R.id.nos_nav_bar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_Nosdra);

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_Nosdra);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.nosdra_drawerL);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.green_dark));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setElevation(90);
        fab = findViewById(R.id.fab_nos);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        userProfile= new Profile();
        customer= new Customer();
        marketBizRegulator= new MarketBizRegulator();
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
        json2 = userPreferences.getString("LastMarketBizRegulatorUsed", "");
        marketBizRegulator = gson2.fromJson(json2, MarketBizRegulator.class);
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.nos_n_Home:
                                Intent myIntent = new Intent(NOSDRAOffice.this, ResponseTeamOffice.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.nos_jiv:

                                Intent chat = new Intent(NOSDRAOffice.this, OurJIVsAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.nos_cu:

                                Intent shop = new Intent(NOSDRAOffice.this, OurCleanUpAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.nos_pcu:

                                Intent pIntent = new Intent(NOSDRAOffice.this, TreatedReportAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.nos_support:
                                Intent helpIntent = new Intent(NOSDRAOffice.this, CusFirebaseMessaging.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(helpIntent);
                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });
    }
    public void showDrawerButton() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_Nosdra);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.syncState();
    }

    public void showUpButton() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_Nosdra);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void setupDrawerListener() {
        drawer = findViewById(R.id.nosdra_drawerL);
        toolbar = (Toolbar) findViewById(R.id.toolbar_Nosdra);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
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
    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.nosdra_drawerL);
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_dash_NOSDRA:

                Intent intent = new Intent(NOSDRAOffice.this, NOSDRAOffice.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.nos_n_Home:
                Intent myIntent = new Intent(NOSDRAOffice.this, ResponseTeamOffice.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
                break;

            case R.id.nav_jiv_N:

                Intent chat = new Intent(NOSDRAOffice.this, OurJIVsAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chat);
                break;

            case R.id.nav_NOSDRA_CU:

                Intent shop = new Intent(NOSDRAOffice.this, OurCleanUpAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(shop);
                break;

            case R.id.nav_N_PCI:

                Intent pIntent = new Intent(NOSDRAOffice.this, TreatedReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pIntent);
                break;

            case R.id.nav_nosdra_Markets:
                Intent profile = new Intent(NOSDRAOffice.this, MarketTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(profile);
                break;

            case R.id.report_new_emerg:
                Intent history = new Intent(NOSDRAOffice.this, UserReportEmergAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                history.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                history.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(history);
                break;

            case R.id.nav_nosdra_Biz:
                Intent supportInt = new Intent(NOSDRAOffice.this, BizOfficeAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(supportInt);
                break;



            case R.id.nosdra_nav_taxi:
                Intent intPref = new Intent(NOSDRAOffice.this, TaxiBookingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intPref.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intPref.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intPref);
                break;
            case R.id.nosdra_train_bookings:
                Intent intSO = new Intent(NOSDRAOffice.this, TrainBookingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intSO.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intSO.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intSO);
                break;
            case R.id.boat_bookings_nosdra:
                Intent boatIntent = new Intent(NOSDRAOffice.this, BoatBookingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                boatIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                boatIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(boatIntent);
                break;

            case R.id.session_nosdra_booking:
                Intent lIntent = new Intent(NOSDRAOffice.this, SessionTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lIntent);
                break;

            case R.id.nosdra_tour_trip:
                Intent tripIntent = new Intent(NOSDRAOffice.this, TripBookingAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                tripIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                tripIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tripIntent);
                break;
            case R.id.nav_Stocks_nosdra:
                Intent loanIntent = new Intent(NOSDRAOffice.this, StocksTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loanIntent);
                break;
            case R.id.nav_nosdra_accounts:
                Intent uIntent = new Intent(NOSDRAOffice.this, MyAcctOverViewAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uIntent);
                break;


            case R.id.nav_nosdra_pref:
                Intent emergencyIntentSper = new Intent(NOSDRAOffice.this, RespTeamPrefAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                emergencyIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(emergencyIntentSper);
                break;


            case R.id.nav_timelines_Nosdra:

                Intent chathh = new Intent(NOSDRAOffice.this, MyTimelineAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                chathh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(chathh);
                break;

            case R.id.nosdra_navsupport:
                Intent helpIntent = new Intent(NOSDRAOffice.this, CustomerHelpActTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(helpIntent);
                break;

            case R.id.nav_tx_nosdra:
                Intent txIntent = new Intent(NOSDRAOffice.this, UserTxAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                txIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(txIntent);
                break;

            case R.id.nosdra_pp:
                Intent privacyIntent = new Intent(NOSDRAOffice.this, PrivacyPolicy_Web.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                privacyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(privacyIntent);
                break;
            case R.id.nos_ins:
                Intent insIntent = new Intent(NOSDRAOffice.this, MyInsTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                insIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(insIntent);
                break;
            case R.id.nav_GrpSavings_nosdra:
                Intent gsIntent = new Intent(NOSDRAOffice.this, MyGrpSavingsTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                gsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gsIntent);
                break;

            case R.id.nav_Nosdra_comp:
                Intent compIntent = new Intent(NOSDRAOffice.this, CompensationAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                compIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(compIntent);
                break;

            case R.id.nosdra_logout:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(NOSDRAOffice.this, SignTabMainActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
                break;
        }
        drawer = findViewById(R.id.nosdra_drawerL);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


}