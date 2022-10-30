package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
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

import com.google.gson.Gson;
import com.skylightapp.Adapters.TranxAdminA;
import com.skylightapp.Adapters.TranxSimpleAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketTranXDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.MarketClasses.InsuranceCompany;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MarketClasses.MarketTranxAdapter;
import com.skylightapp.MarketClasses.TranxAdapter;
import com.skylightapp.MarketClasses.MarketTranx;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class BusinessTransXAct extends AppCompatActivity implements  MarketTranxAdapter.OnMarketTxItemsClickListener,MarketTranxAdapter.Callback,TranxSimpleAdapter.OnItemsClickListener{
    private ArrayList<MarketTranx> marketTranxArrayList;
    private ArrayList<MarketTranx> marketTranxListAll;
    private ArrayList<MarketTranx> marketTranxListDate;
    private ArrayList<Transaction> genTranxListDate;
    private ArrayList<Transaction> genTranxListAll;
    private ArrayList<Transaction> genTranxListToday;
    private Customer customer;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1,gson2,gson3;
    private String json,json1,json2,json3;
    private Profile userProfile;
    private int profileID,count,countTx;
    CircleImageView profileImage;
    private Profile profile;
    private Intent data;
    private InsuranceCompany insuranceCompany;
    private static final String PREF_NAME = "awajima";
    private RecyclerView recyclerToday, recyclerDateTx,recyclerViewAll,recyclerMarketAll,recyclerMarketTx;
    private AppCompatTextView txtCount;
    private TranxAdapter transactionAdapter;

    private MarketTranxAdapter marketTranxAdapter;
    private MarketTranxAdapter marketTranxAdapAll;
    private TranxAdminA transaAdapter;
    TranxSimpleAdapter tranxSimpleAdapter;
    TranxSimpleAdapter tranxSimpleAdapAll;
    private long marketBizID;
    private TranXDAO transactionDao;
    private MarketTranXDAO marketTranXDAO;
    private MarketBusiness marketBusiness;
    private AppCompatButton btnByDate;
    private DatePicker datePicker;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private int totalMTForToday,totalMT,totalMTForDate,insuranceID;
    AppCompatTextView txtTotal,txtToday,txtByDate;
    String SharedPrefUserPassword,dateOfTranx,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_trans);
        setTitle("Business Transactions");
        recyclerToday = findViewById(R.id.recyclerInsToday);
        btnByDate = findViewById(R.id.button_DateInsTxs);
        datePicker = findViewById(R.id.ins_datePicker);
        recyclerMarketTx = findViewById(R.id.recycler_other_Ins_TXs);
        recyclerViewAll = findViewById(R.id.recycler_All_Ins_TXs);
        recyclerDateTx = findViewById(R.id.recyclerInsTxD);
        recyclerMarketAll = findViewById(R.id.recyclerMT_All);
        txtTotal = findViewById(R.id.tranx_Total);
        txtToday = findViewById(R.id.tx_Today_ins);
        txtByDate = findViewById(R.id.ins_tx_sDate);
        gson = new Gson();
        dbHelper= new DBHelper(this);
        transactionDao = new TranXDAO(this);
        marketTranXDAO= new MarketTranXDAO(this);
        marketTranxArrayList = new ArrayList<>();
        genTranxListToday = new ArrayList<>();
        genTranxListDate = new ArrayList<>();
        marketTranxListDate = new ArrayList<>();
        marketTranxListAll = new ArrayList<>();
        genTranxListDate = new ArrayList<>();
        genTranxListAll = new ArrayList<>();



        gson1 = new Gson();
        gson2 = new Gson();
        gson3 = new Gson();
        userProfile=new Profile();
        customer=new Customer();
        marketBusiness= new MarketBusiness();
        insuranceCompany=new InsuranceCompany();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        json2 = userPreferences.getString("LastInsuranceCompanyUsed", "");
        insuranceCompany = gson2.fromJson(json2, InsuranceCompany.class);

        json3 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson3.fromJson(json3, MarketBusiness.class);

        txtCount = findViewById(R.id.counts_otherT_X);
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
        if(marketBusiness !=null){
            marketBizID=marketBusiness.getBusinessID();

        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();

            marketTranxListDate =marketTranXDAO.getMarketTranxForBizForDate(marketBizID,dateOfTranx);


        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();

            genTranxListAll =transactionDao.getAllTranxForBiz(marketBizID);


        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();

            marketTranxArrayList=marketTranXDAO.getMarketTranxForBiz(marketBizID);


        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();

            genTranxListDate=transactionDao.getTranxForBizForDate(marketBizID,dateOfTranx);


        }

        LinearLayoutManager linearLayoutManagerAll
                = new LinearLayoutManager(BusinessTransXAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAll.setLayoutManager(linearLayoutManagerAll);
        tranxSimpleAdapAll = new TranxSimpleAdapter(BusinessTransXAct.this, genTranxListAll);
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(tranxSimpleAdapAll);
        recyclerViewAll.setNestedScrollingEnabled(false);
        SnapHelper snapHelperT = new PagerSnapHelper();
        snapHelperT.attachToRecyclerView(recyclerViewAll);
        recyclerViewAll.setClickable(true);





        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerMarketAll.setLayoutManager(layoutManager);
        recyclerMarketAll.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationL = new DividerItemDecoration(recyclerMarketAll.getContext(),
                layoutManager.getOrientation());
        recyclerMarketAll.addItemDecoration(dividerItemDecorationL);
        marketTranxAdapAll = new MarketTranxAdapter(BusinessTransXAct.this, marketTranxArrayList);
        recyclerMarketAll.setAdapter(marketTranxAdapAll);


        if(marketTranxArrayList !=null){
            count= marketTranxArrayList.size();
            txtCount.setText(count+""+"Market Transactions");
        }else {
            txtCount.setText("No"+""+"Market Transactions yet");

        }



        if(genTranxListDate !=null){
            countTx= genTranxListDate.size();
            txtCount.setText(countTx+""+"Transactions");
        }else {
            txtCount.setText("No"+""+"Transactions yet");

        }
        marketTranxAdapter = new MarketTranxAdapter(BusinessTransXAct.this, marketTranxArrayList);



        LinearLayoutManager layoutMTransaction
                = new LinearLayoutManager(BusinessTransXAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerDateTx.setLayoutManager(layoutMTransaction);
        recyclerDateTx.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDec = new DividerItemDecoration(recyclerDateTx.getContext(),
                layoutMTransaction.getOrientation());
        recyclerDateTx.addItemDecoration(dividerItemDec);
        tranxSimpleAdapter = new TranxSimpleAdapter(BusinessTransXAct.this, genTranxListDate);
        recyclerDateTx.setAdapter(tranxSimpleAdapter);




        LinearLayoutManager layoutM
                = new LinearLayoutManager(BusinessTransXAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerToday.setLayoutManager(layoutM);
        recyclerToday.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDeco = new DividerItemDecoration(recyclerToday.getContext(),
                layoutM.getOrientation());
        recyclerToday.addItemDecoration(dividerItemDeco);
        marketTranxAdapter = new MarketTranxAdapter(BusinessTransXAct.this, marketTranxListDate);
        //recyclerAccounts.setHasFixedSize(true);
        recyclerToday.setAdapter(marketTranxAdapter);

        btnByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayoutManager layoutMTransaction
                        = new LinearLayoutManager(BusinessTransXAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerDateTx.setLayoutManager(layoutMTransaction);
                recyclerDateTx.setItemAnimator(new DefaultItemAnimator());
                DividerItemDecoration dividerItemDec = new DividerItemDecoration(recyclerDateTx.getContext(),
                        layoutMTransaction.getOrientation());
                recyclerDateTx.addItemDecoration(dividerItemDec);
                tranxSimpleAdapter = new TranxSimpleAdapter(BusinessTransXAct.this, genTranxListDate);
                recyclerDateTx.setAdapter(tranxSimpleAdapter);




                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(BusinessTransXAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerToday.setLayoutManager(layoutManager);
                recyclerToday.setItemAnimator(new DefaultItemAnimator());
                DividerItemDecoration dividerItemDecorationL = new DividerItemDecoration(recyclerToday.getContext(),
                        layoutManager.getOrientation());
                recyclerToday.addItemDecoration(dividerItemDecorationL);
                marketTranxAdapter = new MarketTranxAdapter(BusinessTransXAct.this, marketTranxListDate);
                //recyclerAccounts.setHasFixedSize(true);
                recyclerToday.setAdapter(marketTranxAdapter);
            }
        });
        if(totalMT >0){
            txtTotal.setText("Total Items:"+ totalMT);

        }else{
            if(totalMT ==0){
                txtTotal.setText("Oops! no Transaction for this profile");

            }

        }
        if(totalMTForToday >0){
            txtToday.setText("Today  N:"+ totalMTForToday);

        }else{
            if(totalMTForToday ==0){
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
    private void chooseDate(String dateOfTranx) {
        dateOfTranx = datePicker.getYear()+"-"+ (datePicker.getMonth() + 1)+"-"+datePicker.getDayOfMonth();


    }

    public void getInsuTxByDate(View view) {
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }

    @Override
    public void onMarketTranxClick(long tcId, View view) {

    }

    @Override
    public void onMtItemClick(MarketTranx marketTranx) {

    }

    @Override
    public void onPosItemClick(int adapterPosition) {

    }

    @Override
    public void onItemClick(Transaction transaction) {

    }
}