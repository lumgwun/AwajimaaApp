package com.skylightapp.Inventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.firebase.ui.auth.ui.phone.SpacedEditText;
import com.google.gson.Gson;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkylightCash;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MyStocksList extends AppCompatActivity implements MyInventAdapter.OnItemsClickListener{
    Bundle bundle;
    SkylightCash skylightCash;
    private AppCompatButton btnRunUpdate;
    DatePicker picker;
    String selectedStatus,dateOfApproval,superAdminName,tellerConfirmationCode, officeBranch;
    int selectedDepositIndex;
    SpacedEditText edtCode;
    DBHelper dbHelper;
    long tellerCashID,code,tellerCashCode,profileID;
    TextView txtDepositID;
    Bundle userBundle;
    PreferenceManager preferenceManager;
    SharedPreferences userPreferences;
    Profile managerProfile;
    Gson gson,gson1;
    String json,json1,dateOfCash;
    Profile userProfile;
    String machine;
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
    TextView txtTotalSCForDate,txtTotalSCForToday,txtTotalSCTotal;
    double totalSCForDate,totalSCForToday,totalSC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_stocks_list);
        skylightCash = new SkylightCash();
        stocksListDate =new ArrayList<>();
        stocksArrayListToday =new ArrayList<>();
        stocksArrayAll =new ArrayList<>();
        gson = new Gson();
        gson1 = new Gson();
        txtTotalSCForDate = findViewById(R.id.StocksD);
        txtTotalSCTotal = findViewById(R.id.txtTotalStocks);
        txtTotalSCForToday = findViewById(R.id.StocksToday);
        recyclerViewCustomDate = findViewById(R.id.recyclerStocksD);
        recyclerViewToday = findViewById(R.id.recyclerStocksToday);
        recyclerViewAll = findViewById(R.id.recycler_All_Stocks);
        picker = findViewById(R.id.stocks_datePicker);
        btnByDate = findViewById(R.id.buttonDateStocks);
        Calendar calendar1 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        managerProfile= new Profile();
        customerManager=new CustomerManager();
        dbHelper= new DBHelper(this);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson1.fromJson(json, CustomerManager.class);
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

        dateOfCash = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = sdf.format(calendar1.getTime());
        if(dateOfCash ==null){
            dateOfCash=todayDate;
        }
        stocksArrayListToday =dbHelper.getStocksForProfileAtDate(profileID,todayDate);
        stocksListDate =dbHelper.getStocksForProfileAtDate(profileID,dateOfCash);
        stocksArrayAll =dbHelper.getAllStocksForProfile(profileID);
        totalSCForDate =dbHelper.getTotalStocksTodayForProfile(profileID,dateOfCash);
        totalSCForToday =dbHelper.getTotalStocksTodayForProfile(profileID,todayDate);
        totalSC=dbHelper.getStocksTotalForProfile(profileID);

        if(totalSC >0){
            txtTotalSCTotal.setText("Total Items:"+ totalSC);

        }else{
            if(totalSC ==0){
                txtTotalSCTotal.setText("Oops! no Skylight Stocks for this profile");

            }

        }
        if(totalSCForToday >0){
            txtTotalSCForToday.setText("Today Cash  N:"+ totalSCForToday);

        }else{
            if(totalSCForToday ==0){
                txtTotalSCForToday.setText("Oops! no Skylight Stocks for Today");

            }

        }
        if(totalSCForDate >0){
            txtTotalSCForDate.setText("Selected Date Stocks :"+ totalSCForDate);

        }else{
            if(totalSCForDate ==0){
                txtTotalSCForDate.setText("Oops! no Skylight Stocks for the Date");

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
        LinearLayoutManager linearLayoutManagerAll = new LinearLayoutManager(this);
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
        dateOfCash = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();


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