package com.skylightapp.MapAndLoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.Database.FenceEventDAO;
import com.skylightapp.R;

import java.util.ArrayList;

public class MyEmergReportAct extends AppCompatActivity implements  EmergencyReportAdapter.OnEmergClickListener {
    private RecyclerView recycler,recyclerReal;
    private ArrayList<EmergencyReport> reportArrayList;
    private EmergencyReportAdapter adapter;
    private EmergReportDAO emergReportDAO;
    private DBHelper dbHelper;
    private EmergencyReport emergencyReport;
    private SharedPreferences userPreferences;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    private int profileID;
    private static final String PREF_NAME = "awajima";
    String SharedPrefUserPassword;
    String SharedPrefCusID;
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    int SharedPrefProfileID;
    private SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_emerg_report);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();

        emergReportDAO=new EmergReportDAO(this);
        dbHelper=new DBHelper(this);
        emergencyReport= new EmergencyReport();
        reportArrayList= new ArrayList<>();
        recyclerReal = findViewById (R.id.emerg_recy_report);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        if (emergReportDAO != null) {
            sqLiteDatabase = dbHelper.getReadableDatabase();
            try {
                reportArrayList = emergReportDAO.getEmergencyReportFromProfile(SharedPrefProfileID);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


        adapter = new EmergencyReportAdapter(MyEmergReportAct.this,reportArrayList);
        LinearLayoutManager layout = new LinearLayoutManager (this);
        recyclerReal.setLayoutManager (layout);
        recyclerReal.setAdapter (adapter);
        recyclerReal.invalidate ();
    }

    @Override
    public void onItemClick(EmergencyReport emergencyReport) {

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

}