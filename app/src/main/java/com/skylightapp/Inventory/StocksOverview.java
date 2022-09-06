package com.skylightapp.Inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class StocksOverview extends AppCompatActivity {
    DBHelper dbHelper;
    private RecyclerView recyclerViewToday,recyclerViewCustomDate,recyclerViewAll;
    private MyInventAdapter adapterAll;
    private ArrayList<Stocks> stocksArrayListToday;
    private ArrayList<Stocks> stocksListDate;
    private ArrayList<Stocks> stocksArrayAll;
    private AppCompatButton btnByDate;
    TextView txtTotalStocksForDate, txtTotalStocksForToday, txtTotalStocksTotal;
    double totalStocksForDate, totalStocksForToday, totalDBStocks;
    DatePicker picker;
    private static final String PREF_NAME = "skylight";
    private SQLiteDatabase sqLiteDatabase;
    Gson gson,gson1;
    String json,json1, dateOfStocks;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_stocks_overview);
        setTitle("My Stocks List");
        stocksListDate =new ArrayList<>();
        stocksArrayListToday =new ArrayList<>();
        stocksArrayAll =new ArrayList<>();
        txtTotalStocksForDate = findViewById(R.id.Stocks44Date);
        txtTotalStocksTotal = findViewById(R.id.txtTotalStocks);
        txtTotalStocksForToday = findViewById(R.id.Stocks44Today);
        recyclerViewCustomDate = findViewById(R.id.reCyStocksD);
        recyclerViewToday = findViewById(R.id.rVStocksToday);
        recyclerViewAll = findViewById(R.id.recyAll_Stocks);
        picker = findViewById(R.id.stocksPicker);
        btnByDate = findViewById(R.id.buttonDateStocks);
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        dateOfStocks = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(dateOfStocks);
            }
        });

        dateOfStocks = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(calendar.getTime());
        if(dateOfStocks ==null){
            dateOfStocks =todayDate;
        }
        StocksDAO stocksDAO= new StocksDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            stocksArrayListToday =stocksDAO.getAllStocksForToday(todayDate);
        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            stocksListDate =stocksDAO.getAllStocksForDate( dateOfStocks);
        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            stocksArrayAll =stocksDAO.getALLStocksSuper();
        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalStocksForDate =stocksDAO.getTotalStocksForDate( dateOfStocks);

        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalStocksForToday =stocksDAO.getTotalStocksForToday(todayDate);
        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalDBStocks =stocksDAO.getTotalStocks();
        }


        if(totalDBStocks >0){
            txtTotalStocksTotal.setText("Total Stocks:"+ totalDBStocks);

        }else{
            if(totalDBStocks ==0){
                txtTotalStocksTotal.setText("Oops! no Skylight Stocks");

            }

        }
        if(totalStocksForToday >0){
            txtTotalStocksForToday.setText("Today Stocks:"+ totalStocksForToday);

        }else{
            if(totalStocksForToday ==0){
                txtTotalStocksForToday.setText("Oops! no Skylight Stocks for Today");

            }

        }
        if(totalStocksForDate >0){
            txtTotalStocksForDate.setText("Selected Date Stocks :"+ totalStocksForDate);

        }else{
            if(totalStocksForDate ==0){
                txtTotalStocksForDate.setText("Oops! no Skylight Stocks for the Date");

            }

        }

    }
    private void chooseDate(String dateOfCash) {
        dateOfCash = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


    }

    public void getStocksByDate(View view) {
    }
}