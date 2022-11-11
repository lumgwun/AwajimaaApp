package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.BizDealArrayAd;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.R;

import java.util.ArrayList;

public class MyBizDealAct extends AppCompatActivity {
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,machine;
    int profileUID;
    Bundle bundle;
    PrefManager prefManager;
    private static final String PREF_NAME = "awajima";
    private ChipNavigationBar chipNavigationBar;
    private RecyclerView recycler,recyclerReal;
    private ArrayList<BusinessDeal> fenceEventArrayList;
    private BizDealArrayAd bizDealArrayAd;
    String SharedPrefUserPassword,SharedPrefBankAcctNo,SharedPrefBankName,SharedPrefCusID,SharedPrefNirsal,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_biz_deal);

        chipNavigationBar = findViewById(R.id.bottom_nav_Market);


        /*bizDealArrayAd = new BizDealArrayAd(MyBizDealAct.this,fenceEventArrayList);
        LinearLayoutManager layout = new LinearLayoutManager (this);
        recyclerReal.setLayoutManager (layout);
        recyclerReal.setAdapter (bizDealArrayAd);
        recyclerReal.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerReal);
        recyclerReal.setNestedScrollingEnabled(false);
        recyclerReal.invalidate ();*/
        chipNavigationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.mHome:
                                Intent myIntent = new Intent(MyBizDealAct.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.market_biz_deals:

                                Intent myDealIntent = new Intent(MyBizDealAct.this, BusinessDealSubAct.class);
                                startActivity(myDealIntent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myDealIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myDealIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            case R.id.my_market_Book:

                                Intent myCashIntent = new Intent(MyBizDealAct.this, BizDealTimeLineAct.class);
                                startActivity(myCashIntent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myCashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myCashIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            case R.id.my_market_inventory:

                                Intent myStocksIntent = new Intent(MyBizDealAct.this, BusDealDocAct.class);
                                startActivity(myStocksIntent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myStocksIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myStocksIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            case R.id.market_prod:

                                Intent myProdIntent = new Intent(MyBizDealAct.this, BizDealMileStoneAct.class);
                                startActivity(myProdIntent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myProdIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myProdIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            case R.id.my_marketFunding:

                                Intent fundingIntent = new Intent(MyBizDealAct.this, BizDealComAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                fundingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                fundingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(fundingIntent);

                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });


    }
}