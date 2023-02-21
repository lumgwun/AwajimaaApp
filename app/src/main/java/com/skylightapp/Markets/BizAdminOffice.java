package com.skylightapp.Markets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Admins.AdminStocksTab;
import com.skylightapp.Admins.BranchSuppAct;
import com.skylightapp.Admins.AdminTransActivity;
import com.skylightapp.Admins.BirthdayTab;
import com.skylightapp.Admins.BranchReportTab;
import com.skylightapp.Admins.ProfilePackHistoryAct;
import com.skylightapp.Admins.SendSingleUserMAct;
import com.skylightapp.Bookings.BoatBookingTab;
import com.skylightapp.Bookings.SessionTab;
import com.skylightapp.Bookings.TaxiBookingTab;
import com.skylightapp.Bookings.TrainBookingTab;
import com.skylightapp.Bookings.TripBookingAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.MyAcctOverViewAct;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MapAndLoc.AwajimaReportAct;
import com.skylightapp.MapAndLoc.OurCleanUpAct;
import com.skylightapp.MapAndLoc.OurJIVsAct;
import com.skylightapp.MapAndLoc.RespTeamPrefAct;
import com.skylightapp.MapAndLoc.TreatedReportAct;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MyGrpSavingsTab;
import com.skylightapp.MyInsTab;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.Tellers.MyCusList;
import com.skylightapp.UserPrefActivity;
import com.skylightapp.UserTimeLineAct;

import java.util.Date;

public class BizAdminOffice extends TabActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ChipNavigationBar chipNavigationBar;
    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;
    private com.melnykov.fab.FloatingActionButton homeFab;
    private static final String PREF_NAME = "awajima";
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    Gson gson,gson1,gson2;
    private Profile userProfile;
    private Date date;
    private Customer customer;
    private String json,json1,json2;
    private Bundle userBundle;
    private int customerID,profID,marketID;
    private MarketBusiness marketBusiness;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_admin_office);
        setTitle("Biz Admin Office");
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        chipNavigationBar = findViewById(R.id.nav_bar_biz_admin);
        fab = findViewById(R.id.fab_mao);
        userBundle= new Bundle();
        homeFab = findViewById(R.id.fab_biz_admin);
        bottomAppBar = findViewById(R.id.biz_Admin_AppBar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_biz_admin);
        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.navi_biz_admin);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.biz_admin_drawerL);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        //getSupportActionBar().setHomeAsUpIndicator(R.mipmap.lo_awaj);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //getSupportActionBar().setElevation(90);
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userProfile= new Profile();
        customer= new Customer();
        marketBusiness= new MarketBusiness();
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        json = sharedpreferences.getString("LastMarketUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);

        json = sharedpreferences.getString("LastCustomerUsed", "");
        customer = gson2.fromJson(json2, Customer.class);




        if(userBundle !=null){
            userProfile=userBundle.getParcelable("Profile");
            customer=userBundle.getParcelable("Customer");

        }else {
            json = sharedpreferences.getString("LastProfileUsed", "");
            userProfile = gson.fromJson(json, Profile.class);
            json1 = sharedpreferences.getString("LastCustomerUsed", "");
            customer = gson1.fromJson(json1, Customer.class);
        }

        dbHelper= new DBHelper(this);
        if(customer !=null){
            customerID=customer.getCusUID();
        }
        homeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BizAdminOffice.this, BizDepositTab.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //Toast.makeText(MarketAdminOffice.this, "Taking you to the Dashboard", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BizAdminOffice.this, BizDealTab.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra("PROFILE_ID", profileID);
                Toast.makeText(BizAdminOffice.this, "Taking you to the Biz Deal area", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        Resources resources = getResources();

        tabhost.setup(getLocalActivityManager());

        Intent intentsupport = new Intent().setClass(this, AdminStocksTab.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabUserSupport = tabhost
                .newTabSpec("Stocks")
                .setIndicator("", resources.getDrawable(R.drawable.user3))
                .setContent(intentsupport);

        Intent intentTransactions= new Intent().setClass(this, AdminTransActivity.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabTransaction = tabhost
                .newTabSpec("Tx")
                .setIndicator("", resources.getDrawable(R.drawable.withdrawal))
                .setContent(intentTransactions);

        Intent intentDeposit= new Intent().setClass(this, MyMStocksListAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabDeposit = tabhost
                .newTabSpec("Top Stats")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentDeposit);

        Intent intentHistory= new Intent().setClass(this, ProfilePackHistoryAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabHistory = tabhost
                .newTabSpec("History")
                .setIndicator("", resources.getDrawable(R.drawable.transfer3))
                .setContent(intentHistory);

        tabhost.addTab(tabUserSupport);
        tabhost.addTab(tabTransaction);
        tabhost.addTab(tabDeposit);
        tabhost.addTab(tabHistory);

        tabhost.setCurrentTab(2);
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.biz_adminHome:
                                Intent myIntent = new Intent(BizAdminOffice.this, BizAdminOffice.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.biz_Admin_Stocks:
                                Intent chat = new Intent(BizAdminOffice.this, BizDepositTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.ma_G_Market:

                                Intent shop = new Intent(BizAdminOffice.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.support_bizAdmin:

                                Intent pIntent = new Intent(BizAdminOffice.this, MarketMessagingTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.ma_SupportO:
                                Intent helpIntent = new Intent(BizAdminOffice.this, CustomerHelpActTab.class);
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


        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.syncState();
    }

    public void showUpButton() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_Nosdra);
        //Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_dash_biz_admin:

                Intent intent = new Intent(BizAdminOffice.this, BizAdminOffice.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.nav_bCus_biz:
                Intent myIntent = new Intent(BizAdminOffice.this, MyCusList.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
                break;

            case R.id.all_Cus_biz:

                Intent chat = new Intent(BizAdminOffice.this, OurJIVsAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chat);
                break;

            case R.id.biz_admin_workers:

                Intent shop = new Intent(BizAdminOffice.this, OurCleanUpAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(shop);
                break;

            case R.id.biz_admin_BTe:

                Intent pIntent = new Intent(BizAdminOffice.this, TreatedReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pIntent);
                break;

            case R.id.biz_admin_ATe:
                Intent profile = new Intent(BizAdminOffice.this, MarketTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(profile);
                break;

            case R.id.biz_Messenger:

                Intent messengerIntent = new Intent(BizAdminOffice.this, MarketMessagingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                messengerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                messengerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(messengerIntent);

            case R.id.reportB_emerg:
                Intent history = new Intent(BizAdminOffice.this, AwajimaReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                history.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                history.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(history);
                break;

            case R.id.biz_admin_VA:
                Intent supportInt = new Intent(BizAdminOffice.this, BizOfficeAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(supportInt);
                break;



            case R.id.biz_taxi:
                Intent intPref = new Intent(BizAdminOffice.this, TaxiBookingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intPref.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intPref.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intPref);
                break;
            case R.id.biz_trainB:
                Intent intSO = new Intent(BizAdminOffice.this, TrainBookingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intSO.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intSO.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intSO);
                break;
            case R.id.boat_Biz_bookings:
                Intent boatIntent = new Intent(BizAdminOffice.this, BoatBookingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                boatIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                boatIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(boatIntent);
                break;

            case R.id.session_bookingB:
                Intent lIntent = new Intent(BizAdminOffice.this, SessionTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lIntent);
                break;

            case R.id.tour_trip_Biz:
                Intent tripIntent = new Intent(BizAdminOffice.this, TripBookingAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                tripIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                tripIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tripIntent);
                break;
            case R.id.nav_Stocks_biz:
                Intent loanIntent = new Intent(BizAdminOffice.this, AdminStocksTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loanIntent);
                break;
            case R.id.nav_accounts_Branch:
                Intent uIntent = new Intent(BizAdminOffice.this, MyAcctOverViewAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uIntent);
                break;


            case R.id.nav_supBiz:
                Intent emergencyIntentSper = new Intent(BizAdminOffice.this, RespTeamPrefAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                emergencyIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(emergencyIntentSper);
                break;



            case R.id.nav_timelines_Biz:

                Intent intentT = new Intent(BizAdminOffice.this, UserTimeLineAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intentT.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentT);
                break;

            case R.id.biz_supportM:
                Intent helpIntent = new Intent(BizAdminOffice.this, BranchSuppAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(helpIntent);
                break;

            case R.id.nav_tx_Biz:
                Intent txIntent = new Intent(BizAdminOffice.this, AdminTransActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                txIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(txIntent);
                break;

            case R.id.birthdays_Biz:
                Intent birthdayIntent = new Intent(BizAdminOffice.this, BirthdayTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                birthdayIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(birthdayIntent);
                break;

            case R.id.pp_BIz:
                Intent privacyIntent = new Intent(BizAdminOffice.this, PrivacyPolicy_Web.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                privacyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(privacyIntent);
                break;

            case R.id.biz_ins:
                Intent insIntent = new Intent(BizAdminOffice.this, MyInsTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                insIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(insIntent);
                break;
            case R.id.nav_GrpSavings_Biz:
                Intent gsIntent = new Intent(BizAdminOffice.this, MyGrpSavingsTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                gsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gsIntent);
                break;

            case R.id.nav_pref_Biz:
                Intent compIntent = new Intent(BizAdminOffice.this, UserPrefActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                compIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(compIntent);
                break;
            case R.id.biz_sendM:
                Intent sendIntent = new Intent(BizAdminOffice.this, SendSingleUserMAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(sendIntent);
                break;
            case R.id.nav_DR_Biz:
                Intent dailyReportIntent = new Intent(BizAdminOffice.this, BranchReportTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                dailyReportIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(dailyReportIntent);
                break;

            case R.id.biz_Alogout:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(BizAdminOffice.this, SignTabMainActivity.class);
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