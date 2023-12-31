package com.skylightapp.Inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.AppCash;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class BranchStockOv extends AppCompatActivity implements MyInventAdapter.OnItemsClickListener{
    Bundle bundle;
    AppCash appCash;
    private AppCompatButton btnRunUpdate;
    DatePicker picker;
    String selectedStatus,dateOfApproval,superAdminName,tellerConfirmationCode, officeBranch;
    int selectedDepositIndex;
    DBHelper dbHelper;
    int branchID,stocksBranchCount;
    Bundle userBundle;
    SharedPreferences userPreferences;
    String branch;
    Gson gson,gson1;
    String json,json1, dateOfStocks;
    private RecyclerView recyclerViewToday,recyclerViewCustomDate,recyclerViewAll;
    private MyInventAdapter adapterToday;
    private MyInventAdapter adapterDate;
    private MyInventAdapter adapterAll;
    private ArrayList<Stocks> stocksArrayListToday;
    private ArrayList<Stocks> stocksListDate;
    private ArrayList<Stocks> stocksArrayAll;
    private AppCompatButton btnByDate;
    TextView txtTotalSCForDate,txtTotalSCForToday,txtTotalSCTotal;
    double totalSCForDate,totalSCForToday,totalSC;
    private static final String PREF_NAME = "awajima";
    private SQLiteDatabase sqLiteDatabase;
    private Profile userProfile;
    private  OfficeBranch office;
    private MarketBusiness marketBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_branch_stock_ov);
        setTitle("Branch Stocks Listing");
        stocksListDate =new ArrayList<>();
        stocksArrayListToday =new ArrayList<>();
        stocksArrayAll =new ArrayList<>();
        gson = new Gson();
        gson1 = new Gson();
        marketBusiness= new MarketBusiness();
        userProfile= new Profile();
        sqLiteDatabase = dbHelper.getWritableDatabase();
        txtTotalSCForDate = findViewById(R.id.StocksBranchD);
        txtTotalSCTotal = findViewById(R.id.txtBranchTotalStocks);
        txtTotalSCForToday = findViewById(R.id.StocksBranchToday);
        recyclerViewCustomDate = findViewById(R.id.recyclerBranchStocksD);
        recyclerViewToday = findViewById(R.id.recyclerBranchStocksToday);
        recyclerViewAll = findViewById(R.id.recycler_All_BranchStocks);
        picker = findViewById(R.id.stocks_BranchPicker);
        btnByDate = findViewById(R.id.buttonDateBranchStocks);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        office= new OfficeBranch();
        dbHelper= new DBHelper(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json, MarketBusiness.class);

        userBundle= new Bundle();
        if(userProfile !=null){
            branch=userProfile.getProfOfficeName();
            office=userProfile.getProfileOfficeBranch();
        }

        if(office !=null){
            branchID =office.getOfficeBranchID();
        }

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfStocks);
            }
        });


        dateOfStocks = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(calendar1.getTime());
        if(dateOfStocks ==null){
            dateOfStocks =todayDate;
        }
        StocksDAO stocksDAO= new StocksDAO(this);
        if (dbHelper !=null) {
            //dbHelper.openDataBase();
            stocksArrayListToday =stocksDAO.getStocksForBranchAtDate(branchID,dateOfStocks);
        }
        if (dbHelper !=null) {
            //dbHelper.openDataBase();
            stocksBranchCount =stocksDAO.getStockCountAtDateForBranch(branch,dateOfStocks);
        }

        if (dbHelper !=null) {
            //dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            stocksListDate =stocksDAO.getStocksForBranchAtDate(branchID, dateOfStocks);
        }

        if (dbHelper !=null) {
            //dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            stocksArrayAll =stocksDAO.getAllStocksForBranch(branchID);
        }

        if (dbHelper !=null) {
            //dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalSCForDate =stocksDAO.getTotalStocksTodayForBranch1(branchID, dateOfStocks);
        }

        if (dbHelper !=null) {
            //dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalSCForToday =stocksDAO.getTotalStocksTodayForBranch1(branchID,todayDate);
        }

        if (dbHelper !=null) {
            //dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalSC=stocksDAO.getStocksTotalForBranch(branchID);
        }


        if(totalSC >0){
            txtTotalSCTotal.setText("Total Items:"+ totalSC);

        }else{
            if(totalSC ==0){
                txtTotalSCTotal.setText("Oops! no Awajima Stocks for this Branch");

            }

        }
        if(totalSCForToday >0){
            txtTotalSCForToday.setText("Today Cash  N:"+ totalSCForToday);

        }else{
            if(totalSCForToday ==0){
                txtTotalSCForToday.setText("Oops! no Awajima Stocks for Today");

            }

        }
        if(totalSCForDate >0){
            txtTotalSCForDate.setText("Selected Date Stocks :"+ totalSCForDate);

        }else{
            if(totalSCForDate ==0){
                txtTotalSCForDate.setText("Oops! no Awajima Stocks for the Date");

            }

        }


        adapterToday = new MyInventAdapter(this, stocksArrayListToday);
        LinearLayoutManager linearLayoutManagerT = new LinearLayoutManager(this);
        recyclerViewToday.setLayoutManager(linearLayoutManagerT);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(adapterToday);
        recyclerViewToday.setNestedScrollingEnabled(false);
        recyclerViewToday.setClickable(true);



        adapterAll = new MyInventAdapter(this, stocksArrayAll);
        recyclerViewAll.setLayoutManager(new LinearLayoutManager(BranchStockOv.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(adapterAll);
        recyclerViewAll.setNestedScrollingEnabled(false);
        SnapHelper snapHelperT = new PagerSnapHelper();
        snapHelperT.attachToRecyclerView(recyclerViewAll);
        recyclerViewAll.setClickable(true);
        recyclerViewAll.addItemDecoration(new DividerItemDecoration(BranchStockOv.this,DividerItemDecoration.VERTICAL));
        btnByDate.setOnClickListener(this::getTCByDate);

        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterDate = new MyInventAdapter(BranchStockOv.this, stocksListDate);
                recyclerViewCustomDate.setLayoutManager(new LinearLayoutManager(BranchStockOv.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerViewCustomDate.setItemAnimator(new DefaultItemAnimator());
                recyclerViewCustomDate.setAdapter(adapterDate);
                recyclerViewCustomDate.addItemDecoration(new DividerItemDecoration(BranchStockOv.this,DividerItemDecoration.VERTICAL));
                recyclerViewCustomDate.setNestedScrollingEnabled(false);
                recyclerViewCustomDate.setClickable(true);

            }
        });
    }
    private void chooseDate(String dateOfStock) {
        dateOfStock = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


    }

    public void getTCByDate(View view) {

    }

    @Override
    public void onItemClick(Stocks stocks) {
        userBundle.putParcelable("Stocks", stocks);
        Intent intent8 = new Intent(this, UpDateStocksCode.class);
        intent8.putExtras(userBundle);
        startActivity(intent8);

    }

    @Override
    public void onItemClick(int adapterPosition) {
        userBundle.putInt("int", adapterPosition);
        Intent intent8 = new Intent(this, UpDateStocksCode.class);
        intent8.putExtras(userBundle);
        startActivity(intent8);


    }
}