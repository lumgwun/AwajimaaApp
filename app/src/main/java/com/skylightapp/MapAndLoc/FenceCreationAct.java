package com.skylightapp.MapAndLoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.skylightapp.Classes.AwajimaLog;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.FenceEventDAO;
import com.skylightapp.R;

import java.util.ArrayList;

public class FenceCreationAct extends AppCompatActivity {
    private static final String CLASSTAG =
            " " + FenceCreationAct.class.getSimpleName () + " ";

    private RecyclerView recycler,recyclerReal;
    private ArrayList<FenceEvent> fenceEventArrayList;
    private FenceAdapterDummy adapter;
    private FenceEventsAdapter fenceEventsAdapter;
    private FenceEventDAO fenceEventDAO;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Bundle bundle;
    private EmergencyReport emergencyReport;
    private int emergReportID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_fence_creation);
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "Fence Creation called");
        dbHelper=new DBHelper(this);
        fenceEventDAO=new FenceEventDAO(this);

        bundle=getIntent().getExtras();
        if(bundle !=null){
            emergencyReport=bundle.getParcelable("EmergencyReport");
        }
        if(emergencyReport !=null){
            emergReportID=emergencyReport.getEmergReportID();
        }
    }
}