package com.skylightapp.Accountant;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.SavingsAdapter;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.UpdateSavingsAct;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class OfficeBranchSavings extends AppCompatActivity implements SavingsAdapter.OnItemsClickListener{
    private SavingsAdapter transactionAdapter;

    private ArrayList<CustomerDailyReport> customerDailyReports;
    private ArrayList<CustomerDailyReport> customerDailyReportArrayList;
    int branchSavingsCount;
    private DBHelper dbHelper;
    private Date today;
    Date currentDate,tomorrowDate,twoDaysDate,sevenDaysDate,customDayDate;
    private Profile userProfile;
    String SharedPrefUserPassword;
    long reportTime;
    int noOfDay;
    String officeBranch;

    TextView txtSavingsCount4theDay, txtSavingsTotal4theDay;
    private AppCompatButton btnSearchDB;
    String dateOfToday,dateOfCustomDays,dateOfTransaction;
    DatePicker picker;
    double savingsTotal;
    protected DatePickerDialog datePickerDialog;
    private SQLiteDatabase sqLiteDatabase;
    private Profile marketBizProfile,senderProfile;
    private static final String PREF_NAME = "awajima";
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    private static final String AUTH_KEY = QUICKBLOX_AUTH_KEY;
    private static final String AUTH_SECRET = QUICKBLOX_SECRET_KEY;
    private static final String ACCOUNT_KEY = QUICKBLOX_ACCT_KEY;
    private static final String SERVER_URL = "";

    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private String json,json1,json2,SharedPrefUserName,SharedPrefUserMachine;
    MarketBusiness marketBusiness;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wimpey_packs);
        setTitle("Elelenwo Transactions");
        gson= new Gson();
        gson1= new Gson();
        marketBizProfile= new Profile();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        senderProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        officeBranch=userPreferences.getString("OFFICE_BRANCH_NAME", "");
        customerDailyReports = new ArrayList<CustomerDailyReport>();
        customerDailyReportArrayList = new ArrayList<CustomerDailyReport>();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewSavingsWimpey);
        RecyclerView recyclerViewTXToday = findViewById(R.id.recyclerViewWimpeySavingsToday);
        picker=(DatePicker)findViewById(R.id.Savings_date_Wimpey);
        txtSavingsCount4theDay =findViewById(R.id.SavingsCountWimpey);
        txtSavingsTotal4theDay =findViewById(R.id.SavingsAmountWIMPEY);
        dbHelper=new DBHelper(this);


        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfTransaction = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

        SnapHelper snapHelper = new PagerSnapHelper();
        btnSearchDB = findViewById(R.id.btnSearchSavingsDBWimpey);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        customDayDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        dateOfToday = dateFormat.format(calendar.getTime());

        try {
            today = dateFormat.parse(dateOfToday);

        } catch (ParseException ignored) {
        }
        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getWritableDatabase();
            try {
                customerDailyReports = dbHelper.getSavingsForBranchAtDate(officeBranch,dateOfToday);
            } catch (SQLException e) {
                e.printStackTrace();
            }



        }



        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getWritableDatabase();
            try {
                branchSavingsCount =dbHelper.getSavingsCountForBranchAtDate(officeBranch,dateOfToday);
            } catch (SQLException e) {
                e.printStackTrace();
            }



        }



        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getWritableDatabase();
            try {
                savingsTotal =dbHelper.getTotalSavingsForBranchAtDate(officeBranch,dateOfToday);
            } catch (SQLException e) {
                e.printStackTrace();
            }



        }


        LinearLayoutManager layoutManagerC
                = new LinearLayoutManager(OfficeBranchSavings.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTXToday.setLayoutManager(layoutManagerC);
        //recyclerViewTXToday.setHasFixedSize(true);
        transactionAdapter = new SavingsAdapter(OfficeBranchSavings.this, customerDailyReports);
        recyclerViewTXToday.setAdapter(transactionAdapter);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewTXToday.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewTXToday.addItemDecoration(dividerItemDecoration7);

        if(savingsTotal >0){
            txtSavingsTotal4theDay.setText("Savings Total:"+ savingsTotal);

        }else if(savingsTotal ==0){
            txtSavingsTotal4theDay.setText("Savings Total:N0");

        }
        if(branchSavingsCount >0){
            txtSavingsCount4theDay.setText("Savings:"+ branchSavingsCount);

        }else if(branchSavingsCount ==0){
            txtSavingsCount4theDay.setText("Savings:0");

        }
        dateOfTransaction = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                recyclerViewTXToday.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                if(dbHelper !=null){
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    try {
                        customerDailyReports = dbHelper.getSavingsForBranchAtDate(officeBranch,dateOfTransaction);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                }



                if(dbHelper !=null){
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    try {
                        branchSavingsCount =dbHelper.getSavingsCountForBranchAtDate(officeBranch,dateOfTransaction);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                }



                if(dbHelper !=null){
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    try {
                        savingsTotal =dbHelper.getTotalSavingsForBranchAtDate(officeBranch,dateOfTransaction);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                }


                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(OfficeBranchSavings.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManagerC);
                //recyclerView.setHasFixedSize(true);
                transactionAdapter = new SavingsAdapter(OfficeBranchSavings.this, customerDailyReportArrayList);
                recyclerView.setAdapter(transactionAdapter);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerView.getContext(),
                        layoutManagerC.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration7);
                snapHelper.attachToRecyclerView(recyclerView);

                if(branchSavingsCount >0){
                    txtSavingsCount4theDay.setText(MessageFormat.format("Savings:{0}", branchSavingsCount));

                }else if(branchSavingsCount ==0){
                    txtSavingsCount4theDay.setText("Savings:0");

                }
                if(savingsTotal >0){
                    txtSavingsTotal4theDay.setText("Savings Total:"+ savingsTotal);

                }else if(savingsTotal ==0){
                    txtSavingsTotal4theDay.setText("Savings Total:N0");

                }

            }
        });
        btnSearchDB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerViewTXToday.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                return false;
            }
        });


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    private void chooseDate() {
        dateOfTransaction = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

    }

    @Override
    public void onItemClick(CustomerDailyReport customerDailyReport) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("CustomerDailyReport", customerDailyReport);
        Intent intent = new Intent(this, UpdateSavingsAct.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}