package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.TellerReportAdapterSuper;
import com.skylightapp.Admins.TellerReportUpdateAct;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TellerReportActSuper extends AppCompatActivity implements TellerReportAdapterSuper.OnItemsClickListener{
    private static final String TAG = TellerReportActSuper.class.getSimpleName();

    private FloatingActionButton fab;
    private ListView payNow;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private UserSuperAdmin superAdmin;

    private RecyclerView recyclerView;

    private ArrayList<TellerReport> tellerReportArrayList;
    private ArrayList<TellerReport> tellerReportSBiz;
    private TellerReportAdapterSuper mAdapter;

    DBHelper dbHelper;
    String json;
    String currentDate;
    Gson gson1,gson2,gson3;
    String json1,json2,json3,officeBranch;
    private static final String PREF_NAME = "awajima";
    private Awajima awajima;
    private int profileID;
    private long bizID;
    private MarketBusiness business;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_report_super);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        userProfile = new Profile();
        awajima= new Awajima();
        superAdmin = new UserSuperAdmin();
        business= new MarketBusiness();
        tellerReportSBiz= new ArrayList<>();
        tellerReportArrayList = new ArrayList<TellerReport>();
        json1 = userPreferences.getString("LastUserSuperAdminUsed", "");
        superAdmin = gson1.fromJson(json1, UserSuperAdmin.class);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json2 = userPreferences.getString("LastAwajimaUsed", "");
        awajima = gson2.fromJson(json2, Awajima.class);
        json3 = userPreferences.getString("LastMarketBusinessUsed", "");
        business = gson3.fromJson(json3, MarketBusiness.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id._fabSuper);
        if(userProfile !=null){
            profileID =userProfile.getPID();

        }
        if(business !=null){
            bizID = business.getBusinessID();
        }
        TReportDAO tReportDAO = new TReportDAO(this);


        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = mdformat.format(calendar.getTime());

        recyclerView = findViewById(R.id.recycler_view_Super);



        if(business !=null){
            try {
                try {
                    tellerReportArrayList = tReportDAO.getTellerReportBiz(bizID);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }else {
            try {
                try {
                    tellerReportArrayList = tReportDAO.getTellerReportsAll();

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        mAdapter = new TellerReportAdapterSuper(this, R.layout.teller_report_row_super, tellerReportArrayList);
        dbHelper = new DBHelper(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(TellerReport tellerReport) {
        Bundle extras = new Bundle();
        extras.putParcelable("TellerReport",tellerReport);
        Intent intent = new Intent(this, TellerReportUpdateAct.class);
        intent.putExtras(extras);
        startActivity(intent);


    }
}