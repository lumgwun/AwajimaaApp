package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.EmergencyReportAdapter;
import com.skylightapp.Admins.TrackWorkersAct;
import com.skylightapp.Classes.EmergencyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

public class SuperEmergList extends AppCompatActivity implements EmergencyReportAdapter.OnItemsClickListener{
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private AppCompatButton transactions, savings, supportMessage;

    private RecyclerView recyclerView;

    private ArrayList<EmergencyReport> emergencyReports;
    private EmergencyReportAdapter mAdapter;

    DBHelper dbHelper;
    int emergencyCount;
    String json;
    private TextView txtEmergencyCount;
    String LastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_emerg_list);
        recyclerView = findViewById(R.id._EmergencyList);
        txtEmergencyCount = findViewById(R.id.actEmerg);
        emergencyReports = new ArrayList<EmergencyReport>();
        userProfile=new Profile();
        dbHelper = new DBHelper(this);
        //txtEmergencyCount.setText("Emergency:0");
        emergencyReports = dbHelper.getAllEmergencyReports();

        emergencyCount =dbHelper.getEmergencyReportCount();
        if(emergencyCount >0){
            txtEmergencyCount.setText("Emergency:"+ emergencyCount);

        }else if(emergencyCount ==0){
            txtEmergencyCount.setText("Emergency:0");

        }
        mAdapter = new EmergencyReportAdapter(this, R.layout.emergency_row, emergencyReports);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setClickable(true);


    }

    @Override
    public void onItemClick(EmergencyReport emergencyReport) {
        Bundle bundle=new Bundle();
        LastLocation=emergencyReport.getLatLng();
        Intent intent = new Intent(this, TrackWorkersAct.class);
        bundle.putParcelable("EmergencyReport",emergencyReport);
        bundle.putString("LastLocation",LastLocation);
        startActivity(intent);

    }
}