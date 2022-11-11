package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.MapAndLoc.EmergencyReportAdapter;

import java.util.ArrayList;

public class ClimateCRespAct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<EmergencyReport> emergencyReports;
    private EmergencyReportAdapter reportAdapter;
    private EmergReportDAO emergReportDAO;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private RecyclerView recycler,recyclerReal;
    private ArrayList<EmergencyReport> reportArrayList;
    private EmergencyReportAdapter adapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_climate_cresp);
        emergReportDAO=new EmergReportDAO(this);
        emergencyReports= new ArrayList<>();
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        dbHelper=new DBHelper(this);
        emergencyReport= new EmergencyReport();
        reportArrayList= new ArrayList<>();
        recyclerReal = findViewById (R.id.emerg_recy_report);
        recyclerView = findViewById(R.id.report_c_change);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getReadableDatabase();
            emergencyReports=emergReportDAO.getAllEmergencyReports();


        }
    }
}