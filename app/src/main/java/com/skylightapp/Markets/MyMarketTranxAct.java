package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Adapters.TranxSimpleAdapter;
import com.skylightapp.BusinessTransXAct;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketTranXDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.MarketTranx;
import com.skylightapp.MarketClasses.MarketTranxAdapter;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class MyMarketTranxAct extends AppCompatActivity implements MarketTranxAdapter.OnMarketTxItemsClickListener{
    com.github.clans.fab.FloatingActionButton fab;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,dateOfTranx;
    int profileUID;
    Bundle bundle;
    private static final String PREF_NAME = "skylight";
    private ChipNavigationBar chipNavigationBar;
    private Profile userProfile;
    private ArrayList<MarketTranx> marketTranxListAll;
    private ArrayList<MarketTranx> marketTranxListDate;
    private MarketTranXDAO marketTranXDAO;
    private MarketBusiness marketBusiness;
    private AppCompatButton btnByDate;
    private MarketTranxAdapter marketTranxAdapter;
    private MarketTranxAdapter marketTranxAdapAll;
    private DatePicker datePicker;
    private RecyclerView recyclerDateTx, recyclerMarketTx;

    private SQLiteDatabase sqLiteDatabase;
    AppCompatTextView txtTotal,txtToday,txtByDate;
    private int countN,totalMTForDate;
    SwipeRefreshLayout swipeRefreshLayout;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_market_tranx);
        setTitle("Our Biz Transactions");
        btnByDate = findViewById(R.id.my_button_Date);
        datePicker = findViewById(R.id.my_datePic);
        recyclerMarketTx = findViewById(R.id.recycler_my_Tx);
        recyclerDateTx = findViewById(R.id.recy_myToday);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.recy_swipe);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile=new Profile();
        txtTotal = findViewById(R.id.my_tranx_tal);
        txtToday = findViewById(R.id.my_tx_Today_k);
        dbHelper= new DBHelper(this);
        marketTranXDAO= new MarketTranXDAO(this);
        marketTranxListDate = new ArrayList<>();
        marketTranxListAll = new ArrayList<>();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfTranx);
            }
        });
        dateOfTranx = datePicker.getYear()+"-"+ (datePicker.getMonth() + 1)+"-"+datePicker.getDayOfMonth();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(calendar.getTime());
        if(dateOfTranx ==null){
            dateOfTranx=todayDate;
        }
        if(userProfile !=null){
            profileUID=userProfile.getPID();

        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            marketTranxListAll =marketTranXDAO.getMarketTranxForProf(profileUID);
            marketTranxListDate =marketTranXDAO.getMarketTranxForProfForDate(profileUID,dateOfTranx);

        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();


        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerMarketTx.setLayoutManager(layoutManager);
        recyclerMarketTx.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationL = new DividerItemDecoration(recyclerMarketTx.getContext(),
                layoutManager.getOrientation());
        recyclerMarketTx.addItemDecoration(dividerItemDecorationL);
        marketTranxAdapAll = new MarketTranxAdapter(MyMarketTranxAct.this, marketTranxListAll);
        recyclerMarketTx.setAdapter(marketTranxAdapAll);



        LinearLayoutManager layoutM
                = new LinearLayoutManager(MyMarketTranxAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDateTx.setLayoutManager(layoutM);
        recyclerDateTx.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDeco = new DividerItemDecoration(recyclerDateTx.getContext(),
                layoutM.getOrientation());
        recyclerDateTx.addItemDecoration(dividerItemDeco);
        marketTranxAdapter = new MarketTranxAdapter(MyMarketTranxAct.this, marketTranxListDate);
        //recyclerAccounts.setHasFixedSize(true);
        recyclerDateTx.setAdapter(marketTranxAdapter);


        if(marketTranxListDate !=null){
            countN= marketTranxListDate.size();
            txtToday.setText(countN+""+"Market Transactions");
        }else {
            txtTotal.setText("No"+""+"Market Transactions yet");

        }



        if(marketTranxListAll !=null){
            totalMTForDate= marketTranxListAll.size();
            txtTotal.setText(totalMTForDate+""+"Transactions");
        }else {
            txtTotal.setText("No"+""+"Transactions yet");

        }

        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        // cancel the Visual indication of a refresh
                        swipeRefreshLayout.setRefreshing(false);
                        shuffleItems();
                    }
                });


            }
        });
        btnByDate.setOnClickListener(this::getMyTxByDate);
        if(countN >0){
            txtTotal.setText("Total Items:"+ countN);

        }else{
            if(countN ==0){
                txtTotal.setText("Oops! no Transaction for this profile");

            }

        }
        if(countN >0){
            txtToday.setText("Today  N:"+ countN);

        }else{
            if(totalMTForDate ==0){
                txtToday.setText("Oops! no Transaction for Today");

            }

        }
        if(totalMTForDate >0){
            txtByDate.setText("Selected Date Transaction :"+ totalMTForDate);

        }else{
            if(totalMTForDate ==0){
                txtByDate.setText("Oops! no Transaction for the Date");

            }

        }

    }
    public void shuffleItems() {
        recyclerDateTx = findViewById(R.id.recy_myToday);
        datePicker = findViewById(R.id.my_datePic);
        if(datePicker !=null){
            dateOfTranx = datePicker.getYear()+"-"+ (datePicker.getMonth() + 1)+"-"+datePicker.getDayOfMonth();

        }

        marketTranxListDate =marketTranXDAO.getMarketTranxForProfForDate(profileUID,dateOfTranx);
        Collections.shuffle(marketTranxListDate, new Random(System.currentTimeMillis()));
        LinearLayoutManager layoutM
                = new LinearLayoutManager(MyMarketTranxAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDateTx.setLayoutManager(layoutM);
        recyclerDateTx.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDeco = new DividerItemDecoration(recyclerDateTx.getContext(),
                layoutM.getOrientation());
        recyclerDateTx.addItemDecoration(dividerItemDeco);
        marketTranxAdapter = new MarketTranxAdapter(MyMarketTranxAct.this, marketTranxListDate);
        //recyclerAccounts.setHasFixedSize(true);
        recyclerDateTx.setAdapter(marketTranxAdapter);
    }
    private void chooseDate(String dateOfTranx) {
        dateOfTranx = datePicker.getYear()+"-"+ (datePicker.getMonth() + 1)+"-"+datePicker.getDayOfMonth();


    }

    public void getMyTxByDate(View view) {
    }

    @Override
    public void onMtItemClick(MarketTranx marketTranx) {

    }

    @Override
    public void onPosItemClick(int adapterPosition) {

    }
}