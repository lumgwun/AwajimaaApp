package com.skylightapp.Inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;
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
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.AppCash;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MyStocksList extends AppCompatActivity implements MyInventAdapter.OnItemsClickListener{

    DatePicker picker;
    String selectedStatus,dateOfApproval,superAdminName,tellerConfirmationCode, officeBranch;
    DBHelper dbHelper;
    int profileID;
    Bundle userBundle;
    SharedPreferences userPreferences;
    Profile managerProfile;
    Gson gson,gson1;
    String json,json1,dateOfCash;
    private CustomerManager customerManager;
    private UserSuperAdmin superAdmin;
    private AdminUser adminUser;
    private RecyclerView recyclerViewToday,recyclerViewCustomDate,recyclerViewAll;
    private MyInventAdapter adapterToday;
    private MyInventAdapter adapterDate;
    private MyInventAdapter adapterAll;
    private ArrayList<Stocks> stocksArrayListToday;
    private ArrayList<Stocks> stocksListDate;
    private ArrayList<Stocks> stocksArrayAll;
    private AppCompatButton btnByDate;
    AppCompatTextView txtTotalStocksForDate, txtTotalStocksForToday, txtTotalStocksTotal;
    double totalSCForDate,totalSCForToday,totalSC;
    private static final String PREF_NAME = "skylight";
    SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_stocks_list);
        setTitle("My Stocks List");
        stocksListDate =new ArrayList<>();
        stocksArrayListToday =new ArrayList<>();
        stocksArrayAll =new ArrayList<>();
        gson = new Gson();
        gson1 = new Gson();
        txtTotalStocksForDate = findViewById(R.id.StocksD);
        txtTotalStocksTotal = findViewById(R.id.txtTotalStocks);
        txtTotalStocksForToday = findViewById(R.id.StocksToday);
        recyclerViewCustomDate = findViewById(R.id.recyclerStocksD);
        recyclerViewToday = findViewById(R.id.recyclerStocksToday);
        recyclerViewAll = findViewById(R.id.recycler_All_Stocks);
        picker = findViewById(R.id.stocks_datePicker);
        btnByDate = findViewById(R.id.buttonDateStocks);
        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        managerProfile= new Profile();
        customerManager=new CustomerManager();
        dbHelper= new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerManagerUsed", "");
        customerManager = gson1.fromJson(json1, CustomerManager.class);
        dateOfCash = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();
        userBundle= new Bundle();

        if(managerProfile !=null){
            profileID=managerProfile.getPID();
        }

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfCash);
            }
        });

        dateOfCash = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(calendar1.getTime());
        if(dateOfCash ==null){
            dateOfCash=todayDate;
        }
        StocksDAO stocksDAO= new StocksDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            stocksArrayListToday =stocksDAO.getStocksForProfileAtDate(profileID,todayDate);
        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            stocksListDate =stocksDAO.getStocksForProfileAtDate(profileID,dateOfCash);
        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            stocksArrayAll =stocksDAO.getAllStocksForProfile(profileID);
        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalSCForDate =stocksDAO.getTotalStocksTodayForProfile(profileID,dateOfCash);

        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalSCForToday =stocksDAO.getTotalStocksTodayForProfile(profileID,todayDate);
        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalSC=stocksDAO.getStocksTotalForProfile(profileID);
        }


        if(totalSC >0){
            txtTotalStocksTotal.setText("Total Items:"+ totalSC);

        }else{
            if(totalSC ==0){
                txtTotalStocksTotal.setText("Oops! no Awajima Stocks for this profile");

            }

        }
        if(totalSCForToday >0){
            txtTotalStocksForToday.setText("Today Cash  N:"+ totalSCForToday);

        }else{
            if(totalSCForToday ==0){
                txtTotalStocksForToday.setText("Oops! no Awajima Stocks for Today");

            }

        }
        if(totalSCForDate >0){
            txtTotalStocksForDate.setText("Selected Date Stocks :"+ totalSCForDate);

        }else{
            if(totalSCForDate ==0){
                txtTotalStocksForDate.setText("Oops! no Awajima Stocks for the Date");

            }

        }


        adapterToday = new MyInventAdapter(this, stocksArrayListToday);
        LinearLayoutManager linearLayoutManagerT
                = new LinearLayoutManager(MyStocksList.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewToday.setLayoutManager(linearLayoutManagerT);
        recyclerViewToday.setItemAnimator(new DefaultItemAnimator());
        recyclerViewToday.setAdapter(adapterToday);
        recyclerViewToday.setNestedScrollingEnabled(false);
        recyclerViewToday.setClickable(true);



        adapterAll = new MyInventAdapter(this, stocksArrayAll);
        LinearLayoutManager linearLayoutManagerAll
                = new LinearLayoutManager(MyStocksList.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAll.setLayoutManager(linearLayoutManagerAll);
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(adapterAll);
        recyclerViewAll.setNestedScrollingEnabled(false);
        SnapHelper snapHelperT = new PagerSnapHelper();
        snapHelperT.attachToRecyclerView(recyclerViewAll);
        recyclerViewAll.setClickable(true);
        btnByDate.setOnClickListener(this::getTCByDate);

        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterDate = new MyInventAdapter(MyStocksList.this, stocksListDate);
                recyclerViewCustomDate.setLayoutManager(new LinearLayoutManager(MyStocksList.this, LinearLayoutManager.HORIZONTAL, false));
                recyclerViewCustomDate.setItemAnimator(new DefaultItemAnimator());
                recyclerViewCustomDate.setAdapter(adapterDate);
                recyclerViewCustomDate.addItemDecoration(new DividerItemDecoration(MyStocksList.this,DividerItemDecoration.VERTICAL));
                recyclerViewCustomDate.setNestedScrollingEnabled(false);
                recyclerViewCustomDate.setClickable(true);

            }
        });
    }
    private void chooseDate(String dateOfCash) {
        dateOfCash = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


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