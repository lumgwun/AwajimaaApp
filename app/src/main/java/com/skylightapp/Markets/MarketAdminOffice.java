package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Admins.AdminDrawerActivity;
import com.skylightapp.Admins.AdminStocksTab;
import com.skylightapp.Admins.AdminTransActivity;
import com.skylightapp.Admins.SkylightUsersActivity;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.PackListTab;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.R;

public class MarketAdminOffice extends TabActivity {
    private ChipNavigationBar chipNavigationBar;
    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;
    private com.melnykov.fab.FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_admin_office);
        chipNavigationBar = findViewById(R.id.mao_nav_bar);
        fab = findViewById(R.id.fab_mao);
        mFab = findViewById(R.id.ic_ma_fab);
        bottomAppBar = findViewById(R.id.invAppBar);
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

        Intent intentDeposit= new Intent().setClass(this, BizDepositTab.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabDeposit = tabhost
                .newTabSpec("Deposits")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentDeposit);

        tabhost.addTab(tabUserSupport);
        tabhost.addTab(tabTransaction);
        tabhost.addTab(tabDeposit);

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

                            case R.id.ma_topStat:
                                Intent chat = new Intent(MarketAdminOffice.this, TopStatsAct.class);
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
}