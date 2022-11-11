package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.MapAndLoc.EmergencyReportAdapter;

import java.util.ArrayList;

public class ClimateCRepAct extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<EmergencyReport> emergencyReports;
    private EmergencyReportAdapter reportAdapter;
    private EmergReportDAO emergReportDAO;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_climate_rep);
        emergReportDAO=new EmergReportDAO(this);
        recyclerView = findViewById(R.id.report_c_change);
        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getReadableDatabase();
            emergencyReports=emergReportDAO.getAllEmergencyReports();


        }

    }
}