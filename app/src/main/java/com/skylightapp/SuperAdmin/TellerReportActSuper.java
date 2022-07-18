package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.TellerReportAdapterSuper;
import com.skylightapp.Admins.TellerReportUpdateAct;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
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
    private TellerReportAdapterSuper mAdapter;

    DBHelper dbHelper;
    String json;
    String currentDate;
    Gson gson1,gson2;
    String json1,officeBranch;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_report_super);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        gson1 = new Gson();
        userProfile = new Profile();
        superAdmin = new UserSuperAdmin();
        json1 = userPreferences.getString("LastSuperAdminUserUsed", "");
        json = userPreferences.getString("LastProfileUsed", "");
        superAdmin = gson.fromJson(json, UserSuperAdmin.class);
        userProfile = gson1.fromJson(json1, Profile.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id._fabSuper);
        if(userProfile !=null){
            long profileID =userProfile.getPID();

        }


        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = mdformat.format(calendar.getTime());

        recyclerView = findViewById(R.id.recycler_view_Super);
        tellerReportArrayList = new ArrayList<TellerReport>();
        mAdapter = new TellerReportAdapterSuper(this, R.layout.teller_report_row_super, tellerReportArrayList);
        dbHelper = new DBHelper(this);
        tellerReportArrayList = dbHelper.getTellerReportsAll();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(TellerReport tellerReport, CustomerManager customerManager, AdminUser adminUser, Profile profile,String officeBranch) {
        Bundle extras = new Bundle();
        extras.putParcelable("TellerReport",tellerReport);
        extras.putParcelable("CustomerManager",customerManager);
        extras.putParcelable("AdminUser",adminUser);
        extras.putParcelable("Profile",profile);
        extras.putString("USER_OFFICE",officeBranch);
        Intent intent = new Intent(this, TellerReportUpdateAct.class);
        intent.putExtras(extras);
        startActivity(intent);


    }
}