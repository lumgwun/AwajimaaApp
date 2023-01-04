package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.MySavingsCodeAdapter;
import com.skylightapp.Adapters.SkyLightPackageAdapter;
import com.skylightapp.Adapters.SuperSavingsAdapter;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ImportDateTab extends AppCompatActivity implements SkyLightPackageAdapter.OnItemsClickListener{
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson;
    private String json;

    private Profile userProfile;
    private String profileID;
    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");
    private List<CustomerDailyReport> customerDailyReports;
    private MySavingsCodeAdapter codeAdapter;
    private SuperSavingsAdapter savingsAdapter;
    SkyLightPackageAdapter packageAdapter;
    SkyLightPackageAdapter packageAdapter3;
    SkyLightPackageAdapter packageAdapter7;
    SkyLightPackageAdapter packageAdapterCustom;
    DBHelper dbHelper;
    private ArrayList<CustomerDailyReport> customerDailyReports3;
    private ArrayList<MarketBizPackage> marketBizPackages;
    private ArrayList<MarketBizPackage> marketBizPackages3;
    private ArrayList<MarketBizPackage> marketBizPackages7;
    private ArrayList<MarketBizPackage> marketBizPackagesC;
    private TextView txtPackages, txtPackages3, txtPackages7,txtSO, txtPackagesC;
    String currentDate;
    Date tomorrowDate;
    Date twoDaysDate;
    Date sevenDaysDate;
    Date customDayDate;
    String dateOf7Days,dateOf3Days,dateOfCustomDays, dateOfPackageDue;
    DatePicker picker;
    double transactionTotal;
    protected DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    Calendar calendar,calendar7;
    private  int packageTodayCount,packageDay3Count, packageDay7Count,packageDayCCount;
    RecyclerView recyclerToday, recyclerDay3,recyclerDay7,rcyclerCustomDate;
    AppCompatButton btnSearch;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_impot_date_tab);
        recyclerToday = findViewById(R.id.recyclerViewPack0);
        recyclerDay3 = findViewById(R.id.recyclerViewPack3);
        recyclerDay7 = findViewById(R.id.recyclerViewPack7);
        rcyclerCustomDate = findViewById(R.id.recyclerCustom);
        dbHelper= new DBHelper(this);
        txtPackages = findViewById(R.id.txtViewP);
        txtPackages3 = findViewById(R.id.txtViewP3);
        txtPackages7 = findViewById(R.id.txtViewP7);
        txtPackagesC = findViewById(R.id.txtViewPC);
        btnSearch = findViewById(R.id.buttonSearchPDB);
        marketBizPackages =new ArrayList<MarketBizPackage>();
        marketBizPackages3 =new ArrayList<MarketBizPackage>();
        marketBizPackages7 =new ArrayList<MarketBizPackage>();
        marketBizPackagesC =new ArrayList<MarketBizPackage>();
        customerDailyReports3=new ArrayList<CustomerDailyReport>();
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date newDate = calendar.getTime();
        dateOf3Days = sdf.format(newDate);

        calendar7 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar7.add(Calendar.DAY_OF_YEAR, 7);
        Date newDate7 = calendar7.getTime();
        dateOf7Days = sdf.format(newDate7);

        customDayDate = calendar.getTime();
        if(customDayDate !=null){
            try {
                currentDate = sdf.format(customDayDate);

            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }

        }

        picker=(DatePicker)findViewById(R.id.p_date_);
        dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy");

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfPackageDue = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();
        currentDate = dateFormat.format(customDayDate);

        try {
            marketBizPackages =dbHelper.getPackageEndingToday1(currentDate);
            marketBizPackages3 =dbHelper.getPackageEndingIn3Days(dateOf3Days);
            marketBizPackages7 =dbHelper.getPackageEnding7Days(dateOf7Days);
            marketBizPackagesC =dbHelper.getPackEndingCustomDay(dateOfPackageDue);

        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        packageAdapter = new SkyLightPackageAdapter(ImportDateTab.this, marketBizPackages);
        packageAdapter3 = new SkyLightPackageAdapter(ImportDateTab.this, marketBizPackages3);
        packageAdapter7 = new SkyLightPackageAdapter(ImportDateTab.this, marketBizPackages7);
        packageAdapterCustom = new SkyLightPackageAdapter(ImportDateTab.this, marketBizPackagesC);



        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerToday.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerToday.getContext(),
                layoutManager.getOrientation());
        recyclerToday.addItemDecoration(dividerItemDecoration);
        recyclerToday.setItemAnimator(new DefaultItemAnimator());
        recyclerToday.setAdapter(packageAdapter);
        //recyclerToday.setHasFixedSize(true);
        SnapHelper snapHelper1 = new PagerSnapHelper();
        snapHelper1.attachToRecyclerView(recyclerToday);

        try {
            packageTodayCount= marketBizPackages.size();
            packageDay3Count= marketBizPackages3.size();
            packageDay7Count= marketBizPackages7.size();
            packageDayCCount= marketBizPackagesC.size();


        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(packageTodayCount>0){
            txtPackages.setText("Today's Count"+packageTodayCount);
        }else {
            txtPackages.setText("Sorry! no any packages, today");

        }

        if(packageTodayCount>0){
            txtPackages3.setText("3 days ahead Count"+packageDay3Count);
        }else {
            txtPackages3.setText("No any packages, for the 3rd day from today");

        }

        if(packageTodayCount>0){
            txtPackages7.setText("7 days ahead Count"+packageDay7Count);
        }else {
            txtPackages7.setText("No any packages, for the 7th day from today");

        }
        if(packageTodayCount>0){
            txtPackages.setText("Selected days ahead Count"+packageDayCCount);
        }else {
            txtPackages.setText("Sorry! no any packages, for the date");

        }




        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDay3.setLayoutManager(layoutManager2);
        //recyclerDay3.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerDay3.getContext(),
                layoutManager.getOrientation());
        recyclerDay3.addItemDecoration(dividerItemDecoration2);
        recyclerDay3.setItemAnimator(new DefaultItemAnimator());
        recyclerDay3.setAdapter(packageAdapter3);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerDay3);




        LinearLayoutManager layoutManager7
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDay7.setLayoutManager(layoutManager7);
        //recyclerDay7.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerDay7.getContext(),
                layoutManager.getOrientation());
        recyclerDay7.addItemDecoration(dividerItemDecoration7);
        recyclerDay7.setItemAnimator(new DefaultItemAnimator());
        recyclerDay7.setAdapter(packageAdapter7);
        SnapHelper snapHelper7 = new PagerSnapHelper();
        snapHelper7.attachToRecyclerView(recyclerDay7);


        LinearLayoutManager layoutManagerCustom
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelperC = new PagerSnapHelper();

        btnSearch.setOnClickListener(this::runPackageSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcyclerCustomDate.setLayoutManager(layoutManagerCustom);
                //rcyclerCustomDate.setHasFixedSize(true);
                DividerItemDecoration dividerItemDecorationC = new DividerItemDecoration(rcyclerCustomDate.getContext(),
                        layoutManager.getOrientation());
                rcyclerCustomDate.addItemDecoration(dividerItemDecorationC);
                rcyclerCustomDate.setItemAnimator(new DefaultItemAnimator());
                rcyclerCustomDate.setAdapter(packageAdapterCustom);
                snapHelperC.attachToRecyclerView(rcyclerCustomDate);

            }
        });
        btnSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                rcyclerCustomDate.setVisibility(View.GONE);
                return false;
            }
        });
    }
    private void chooseDate() {
        dateOfPackageDue = picker.getDayOfMonth()+"/"+ (picker.getMonth() + 1)+"/"+picker.getYear();

    }

    @Override
    public void onItemClick(MarketBizPackage lightPackage) {

    }

    public void runPackageSearch(View view) {
    }
}