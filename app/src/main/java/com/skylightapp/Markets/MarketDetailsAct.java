package com.skylightapp.Markets;

import static com.skylightapp.Database.DBHelper.DATABASE_NAME;
import static com.skylightapp.Markets.MarketHub.MANAGE_MARKET;
import static com.skylightapp.Markets.MarketHub.SELECT_MARKET;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.skylightapp.MapAndLoc.EmergencyReportAdapter;
import com.skylightapp.Adapters.TranxSimpleAdapter;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketDAO;
import com.skylightapp.MarketClasses.CommodityAdater;
import com.skylightapp.MarketClasses.Farm;
import com.skylightapp.MarketClasses.FarmAdapter;
import com.skylightapp.MarketClasses.InsuranceCompany;
import com.skylightapp.MarketClasses.LogisticEntity;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketAdapter;
import com.skylightapp.MarketClasses.MarketAnnounceAdapt;
import com.skylightapp.MarketClasses.MarketBizRecyAdapter;
import com.skylightapp.MarketClasses.MarketBizSub;
import com.skylightapp.MarketClasses.MarketCommodity;
import com.skylightapp.MarketClasses.MarketAnnouncement;
import com.skylightapp.MarketClasses.MarketBizDonor;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.MarketTranxAdapter;
import com.skylightapp.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

public class MarketDetailsAct extends AppCompatActivity implements MarketAdapter.OnMarketClickListener,FarmAdapter.OnFarmClickListener, MarketAnnounceAdapt.OnAItemsClickListener,CommodityAdater.OnCommodityClickListener{
    SwipeRefreshLayout swipeRefreshLayout;
    private SQLiteDatabase sqLiteDatabase;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,dateOfTranx,marketName;
    int profileUID;
    Bundle bundle;
    private static final String PREF_NAME = "awajima";
    private Profile userProfile;
    private ArrayList<MarketBizDonor> marketBizDonors;
    private ArrayList<MarketAnnouncement> marketAnnouncements;
    private ArrayList<MarketBusiness> marketBusinesses;
    private ArrayList<MarketBusiness> marketBusinesseAll;
    private ArrayList<MarketCommodity> marketCommodities;
    private ArrayList<EmergencyReport> emergencyReports;
    private ArrayList<MarketBizSub> marketBizSubs;
    private ArrayList<InsuranceCompany> insuranceCompanies;
    private ArrayList<LogisticEntity> logisticEntities;
    private ArrayList<Farm> farmArrayList;
    private ArrayList<String> marketDays;
    private BigDecimal marketRevenue;
    private RecyclerView recyclerViewBiz,rVCommodity, rvFarms,rvEmerg,rvAnnouncement,recyclerMoreMarkets;
    private AppCompatTextView txtRevenue,txtMarketFollowing,txtMarketDays;
    //private String []  listDays = new char[];
    private BarChart marketChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntriesArrayList;
    private Market market;
    private  int marketID;
    private MarketBizRecyAdapter marketBizRecyAdapter;
    private MarketBizRecyAdapter marketBizRecyAdAll;
    private CommodityAdater commodityAdater;
    private FarmAdapter farmAdapter;
    private EmergencyReportAdapter emergencyReportAdapter;
    private MarketTranxAdapter marketTranxAdapter;
    private TranxSimpleAdapter tranxSimpleAdapter;
    private MarketAnnounceAdapt announceAdapt;
    private AppCompatButton followBtn;
    private int SharedPrefProfileID,mode,manageState;
    private  MarketAdapter mAdapter;
    ArrayList<Market> marketArrayList;
    private MarketDAO marketDAO;
    public static final int SELECT_ANNOUNCEMENT = 1105;
    public static final int MANAGE_ANNOUNCEMENT = 105;

    public static final int SELECT_COMMODITY = 159;
    public static final int MANAGE_COMMODITY = 1557;

    public static final int SELECT_FARM = 219;
    public static final int MANAGE_FARM = 437;
    private static MarketAdapter marketAdapter;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_details);
        Toolbar toolbar = findViewById(R.id.toolbar_Market);
        setSupportActionBar(toolbar);
        marketDAO= new MarketDAO(this);
        dbHelper= new DBHelper(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        gson = new Gson();
        userProfile=new Profile();
        bundle= new Bundle();
        market= new Market();
        marketBizDonors= new ArrayList<>();
        marketAnnouncements= new ArrayList<>();
        marketBusinesses= new ArrayList<>();
        marketBusinesseAll= new ArrayList<>();

        marketCommodities= new ArrayList<>();
        marketDays= new ArrayList<>();
        farmArrayList= new ArrayList<>();
        emergencyReports= new ArrayList<>();
        insuranceCompanies= new ArrayList<>();
        logisticEntities= new ArrayList<>();
        marketArrayList= new ArrayList<>();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        recyclerViewBiz = findViewById(R.id.biz_recycler_mBiz);
        recyclerMoreMarkets = findViewById(R.id.more_recylerM);
        marketChart = findViewById(R.id.market_charty);
        rvAnnouncement = findViewById(R.id.market_recyc_ann);

        rVCommodity = findViewById(R.id.commodity_recyler);
        rvEmerg = findViewById(R.id.emerg_recycler67);
        rvFarms = findViewById(R.id.farm_recycler56);
        txtRevenue = findViewById(R.id.market_Revenue);
        txtMarketFollowing = findViewById(R.id.market_following);
        txtMarketDays = findViewById(R.id.days_of_the_market);
        followBtn = findViewById(R.id.btn_follow);

        try {
            getBarEntries();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        if(userProfile !=null){
            profileUID=userProfile.getPID();
        }
        bundle=getIntent().getExtras();
        if(bundle !=null){
            market=bundle.getParcelable("Market");
            mode = bundle.getInt("MODE",-1);
            manageState = bundle.getInt("Manage",-1);
        }
        //bundle.putInt("Manage",MANAGE_MARKET);


        if(market !=null){
            marketID=market.getMarketID();
            marketBizDonors=market.getMarketBizDonors();
            marketAnnouncements=market.getMarketAnnouncements();
            marketBusinesses=market.getMarketBizArrayList();
            marketCommodities=market.getCommodityArrayList();
            marketDays=market.getMarketDayStrings();
            emergencyReports=market.getMarketEmergReports();
            farmArrayList= market.getMarketFarms();
            insuranceCompanies=market.getMarketInsurances();
            logisticEntities=market.getMarketLogisticE();
            marketName=market.getMarketName();
        }
        if(marketName !=null){
            getSupportActionBar().setTitle(marketName);
        }else {
            getSupportActionBar().setTitle("Market Details");
        }


        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userProfile !=null){
                    userProfile.addMarket(market);
                    userProfile.addMarketID(marketID);

                }


            }
        });
        if(dbHelper !=null){
            try {
                dbHelper.openDataBase();
                try {
                    if(marketDAO !=null){
                        marketArrayList=marketDAO.getMarkets();

                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        }


        followBtn.setOnClickListener(this::folowMarket);


        if(mode==SELECT_MARKET){
            followBtn.setVisibility(View.VISIBLE);
        }else{
            followBtn.setVisibility(View.GONE);
        }




        barDataSet = new BarDataSet(barEntriesArrayList, "Market Performance Chart in Billion Naira");
        barData = new BarData(barDataSet);
        marketChart.setData(barData);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        marketChart.getDescription().setEnabled(false);
        marketChart.setPinchZoom(true);
        marketChart.setDrawBarShadow(false);
        marketChart.setDrawGridBackground(false);


        LinearLayoutManager layoutMAnn
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvAnnouncement.setLayoutManager(layoutMAnn);
        rvAnnouncement.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationL = new DividerItemDecoration(rvAnnouncement.getContext(),
                layoutMAnn.getOrientation());
        rvAnnouncement.addItemDecoration(dividerItemDecorationL);
        announceAdapt = new MarketAnnounceAdapt(MarketDetailsAct.this, marketAnnouncements);
        rvAnnouncement.setAdapter(announceAdapt);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBiz.setLayoutManager(layoutManager);
        recyclerViewBiz.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDB = new DividerItemDecoration(recyclerViewBiz.getContext(),
                layoutManager.getOrientation());
        recyclerViewBiz.addItemDecoration(dividerItemDB);
        marketBizRecyAdapter = new MarketBizRecyAdapter(MarketDetailsAct.this, marketBusinesses);
        recyclerViewBiz.setAdapter(marketBizRecyAdapter);



        LinearLayoutManager layoutMMarket
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewBiz.setLayoutManager(layoutMMarket);
        recyclerViewBiz.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerMarket = new DividerItemDecoration(recyclerViewBiz.getContext(),
                layoutMMarket.getOrientation());
        recyclerViewBiz.addItemDecoration(dividerMarket);
        mAdapter = new MarketAdapter(MarketDetailsAct.this,marketArrayList,mode);
        recyclerMoreMarkets.setAdapter(mAdapter);
        ((SimpleItemAnimator) Objects.requireNonNull(recyclerMoreMarkets.getItemAnimator())).setSupportsChangeAnimations(false);
        mAdapter.notifyDataSetChanged();





        LinearLayoutManager layoutMC
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rVCommodity.setLayoutManager(layoutMC);
        rVCommodity.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDC = new DividerItemDecoration(rVCommodity.getContext(),
                layoutMC.getOrientation());
        rVCommodity.addItemDecoration(dividerItemDC);
        commodityAdater = new CommodityAdater(MarketDetailsAct.this, marketCommodities);
        rVCommodity.setAdapter(commodityAdater);




        LinearLayoutManager layoutMEmerg
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvEmerg.setLayoutManager(layoutMEmerg);
        rvEmerg.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDEmerg = new DividerItemDecoration(rvEmerg.getContext(),
                layoutMEmerg.getOrientation());
        rvEmerg.addItemDecoration(dividerItemDEmerg);
        emergencyReportAdapter = new EmergencyReportAdapter(MarketDetailsAct.this, emergencyReports);
        rvEmerg.setAdapter(emergencyReportAdapter);


        LinearLayoutManager layoutMFarm
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvFarms.setLayoutManager(layoutMFarm);
        rvFarms.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecF = new DividerItemDecoration(rvFarms.getContext(),
                layoutMFarm.getOrientation());
        rvFarms.addItemDecoration(dividerItemDecF);
        farmAdapter = new FarmAdapter(MarketDetailsAct.this, farmArrayList);
        rvFarms.setAdapter(farmAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void getBarEntries() {
        // creating a new array list
        barEntriesArrayList = new ArrayList<>();

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList.add(new BarEntry(1f, 4));
        barEntriesArrayList.add(new BarEntry(2f, 6));
        barEntriesArrayList.add(new BarEntry(3f, 8));
        barEntriesArrayList.add(new BarEntry(4f, 2));
        barEntriesArrayList.add(new BarEntry(5f, 4));
        barEntriesArrayList.add(new BarEntry(6f, 6));
        barEntriesArrayList.add(new BarEntry(7f, 7));
        barEntriesArrayList.add(new BarEntry(8f, 10));
        barEntriesArrayList.add(new BarEntry(9f, 14));
        barEntriesArrayList.add(new BarEntry(10f, 1));
    }
    public  void refreshAnnouncement(int deSelect, int select){
        announceAdapt.notifyItemChanged(deSelect);
        announceAdapt.notifyItemChanged(select);
    }

    public void folowMarket(View view) {
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle= new Bundle();
        bundle.putParcelable("Market",market);
        bundle.putInt("MODE",SELECT_MARKET);
        bundle.putInt("Manage",MANAGE_MARKET);
        Intent intent=new Intent(MarketDetailsAct.this, MarketDetailsAct.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);

    }


    @Override
    public void onTakeItemClick(MarketAnnouncement announcement) {
        Bundle bundle= new Bundle();
        bundle.putParcelable("MarketAnnouncement",announcement);
        bundle.putInt("MODE",SELECT_ANNOUNCEMENT);
        bundle.putInt("Manage",MANAGE_ANNOUNCEMENT);
        Intent intent=new Intent(MarketDetailsAct.this, MarketDetailsAct.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);

    }
    @Override
    public void onCommodityClick(MarketCommodity marketCommodity) {
        Bundle bundle= new Bundle();
        bundle.putParcelable("MarketCommodity",marketCommodity);
        bundle.putInt("MODE",SELECT_COMMODITY);
        bundle.putInt("Manage",MANAGE_COMMODITY);
        Intent intent=new Intent();
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);

    }
    @Override
    public void onFarmClick(Farm farm) {
        Bundle bundle= new Bundle();
        bundle.putParcelable("Farm",farm);
        bundle.putInt("MODE",SELECT_FARM);
        bundle.putInt("Manage",MANAGE_FARM);
        Intent intent=new Intent();
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);

    }
}