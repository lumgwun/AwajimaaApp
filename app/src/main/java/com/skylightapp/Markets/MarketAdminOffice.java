package com.skylightapp.Markets;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Admins.AdminStocksTab;
import com.skylightapp.Admins.AdminTransActivity;
import com.skylightapp.Admins.ProfilePackHistoryAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.Date;

public class MarketAdminOffice extends TabActivity {
    private ChipNavigationBar chipNavigationBar;
    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;
    private com.melnykov.fab.FloatingActionButton mFab;
    private static final String PREF_NAME = "awajima";
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    Gson gson,gson1;
    private Profile userProfile;
    private Date date;
    private Customer customer;
    private String json,json1;
    private Bundle userBundle;
    private int customerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_admin_office);
        setTitle("Market Admin Office");
        chipNavigationBar = findViewById(R.id.mao_nav_bar);
        fab = findViewById(R.id.fab_mao);
        userBundle= new Bundle();
        mFab = findViewById(R.id.ic_ma_fab);
        bottomAppBar = findViewById(R.id.invAppBar);
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userProfile= new Profile();
        gson = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
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
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarketAdminOffice.this, BizDepositTab.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //Toast.makeText(MarketAdminOffice.this, "Taking you to the Dashboard", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarketAdminOffice.this, BizDealCreatorAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra("PROFILE_ID", profileID);
                Toast.makeText(MarketAdminOffice.this, "Taking you to the Dashboard", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        Resources resources = getResources();
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
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

        Intent intentDeposit= new Intent().setClass(this, TopStatsAct.class);
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
                            case R.id.ma_Home:
                                Intent myIntent = new Intent(MarketAdminOffice.this, MarketAdminOffice.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.m_dTabs:
                                Intent chat = new Intent(MarketAdminOffice.this, BizDepositTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.ma_G_Market:

                                Intent shop = new Intent(MarketAdminOffice.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.ma_sendMT:

                                Intent pIntent = new Intent(MarketAdminOffice.this, MarketMessagingTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.ma_SupportO:
                                Intent helpIntent = new Intent(MarketAdminOffice.this, CustomerHelpActTab.class);
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
}