package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

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
import com.skylightapp.Admins.AdminPackageActivity;
import com.skylightapp.Admins.AdminStocksTab;
import com.skylightapp.Admins.AdminTransActivity;
import com.skylightapp.Admins.CusPackHistoryAct;
import com.skylightapp.Admins.ProfilePackHistoryAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Markets.BizDealCreatorAct;
import com.skylightapp.Markets.BizDepositTab;
import com.skylightapp.Markets.MarketAdminOffice;
import com.skylightapp.Markets.MarketMessagingTab;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.Markets.TopStatsAct;
import com.skylightapp.SuperAdmin.StocksTab;

import java.util.Calendar;
import java.util.Date;

public class ProfHistoryTab extends TabActivity {
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    Gson gson;
    private Profile userProfile;
    private Date date;
    private  Profile teller,teller2;
    private int tellerID;
    private long tellerID1;
    private int customerID;
    private long tellerID2;
    private Customer customer;
    private String json;
    private static final String PREF_NAME = "awajima";
    static final int DATE_DIALOG_ID = 1;
    private ChipNavigationBar chipNavigationBar;
    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;
    private com.melnykov.fab.FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_prof_history_tab);
        setTitle("History Tab");
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        final Calendar c = Calendar.getInstance();
        userProfile= new Profile();
        gson = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        chipNavigationBar = findViewById(R.id.nav_bar_history);
        fab = findViewById(R.id.fab_history);
        mFab = findViewById(R.id.ic_history_fab);
        bottomAppBar = findViewById(R.id.history_AppBar);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfHistoryTab.this, BizDepositTab.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //Toast.makeText(MarketAdminOffice.this, "Taking you to the Dashboard", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfHistoryTab.this, BizDealCreatorAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra("PROFILE_ID", profileID);
                Toast.makeText(ProfHistoryTab.this, "Taking you to the Dashboard", Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });
        Resources resources = getResources();
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(getLocalActivityManager());

        Intent intentsupport = new Intent().setClass(this, AdminPackageActivity.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabUserSupport = tabhost
                .newTabSpec("System Packs")
                .setIndicator("", resources.getDrawable(R.drawable.user3))
                .setContent(intentsupport);

        Intent intentTransactions= new Intent().setClass(this, CusPackHistoryAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabTransaction = tabhost
                .newTabSpec("My Cus Pack")
                .setIndicator("", resources.getDrawable(R.drawable.withdrawal))
                .setContent(intentTransactions);

        Intent intentDeposit= new Intent().setClass(this, TopStatsAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabDeposit = tabhost
                .newTabSpec("Top Stats")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentDeposit);

        Intent intentHistory= new Intent().setClass(this, ProfilePackHistoryAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabHistory = tabhost
                .newTabSpec("My Prof Packs")
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
                            case R.id.mHome_history:
                                Intent myIntent = new Intent(ProfHistoryTab.this, MarketAdminOffice.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.mDeposit_Tab:
                                Intent chat = new Intent(ProfHistoryTab.this, BizDepositTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.market_history:

                                Intent shop = new Intent(ProfHistoryTab.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.ma_stocks_H:

                                Intent pIntent = new Intent(ProfHistoryTab.this, StocksTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.m_history_M:
                                Intent helpIntent = new Intent(ProfHistoryTab.this, MarketMessagingTab.class);
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
}