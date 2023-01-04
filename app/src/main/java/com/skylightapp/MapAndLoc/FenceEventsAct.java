package com.skylightapp.MapAndLoc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.AwajimaLog;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.FenceEventDAO;
import com.skylightapp.R;

import java.util.ArrayList;

public class FenceEventsAct extends AppCompatActivity implements  FenceAd.OnFenceEventClickListener {
    private static final String CLASSTAG =
            " " + FenceEventsAct.class.getSimpleName () + " ";

    private RecyclerView recycler,recyclerReal;
    private ArrayList<FenceEvent> fenceEventArrayList;
    private FenceEventAdapter adapter;
    private FenceAd fenceAd;
    private FenceEventDAO fenceEventDAO;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Bundle bundle;
    private EmergencyReport emergencyReport;
    private int emergReportID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onCreate called");
        setContentView(R.layout.act_fence_events);
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onResume called");
        AppController app = (AppController) getApplication ();

        Toolbar toolbar = findViewById (R.id.toolbar);
        fenceEventDAO=new FenceEventDAO(this);
        dbHelper=new DBHelper(this);
        emergencyReport= new EmergencyReport();
        fenceEventArrayList= new ArrayList<>();
        bundle= new Bundle();
        recycler = findViewById (R.id.fence_events_recycler);
        recyclerReal = findViewById (R.id.fence_events_Cy);

        setSupportActionBar (toolbar);
        ActionBar actionBar = getSupportActionBar ();
        if (actionBar == null)
        {
            Log.w (AwajimaLog.LOGTAG, CLASSTAG + "Connot access the action bar");
        } else
        {
            actionBar.setDisplayHomeAsUpEnabled (true);
            actionBar.setDisplayShowHomeEnabled (true);
        }
        bundle=getIntent().getExtras();
        if(bundle !=null){
            emergencyReport=bundle.getParcelable("EmergencyReport");
        }
        if(emergencyReport !=null){
            emergReportID=emergencyReport.getEmergReportID();
        }
        if (fenceEventDAO != null) {

            try {
                if(emergReportID>0){
                    fenceEventArrayList=fenceEventDAO.getFenceEventsForEmerg(emergReportID);
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }

         adapter = new FenceEventAdapter(app);
        LinearLayoutManager layoutMgr = new LinearLayoutManager (this);
        recycler.setLayoutManager (layoutMgr);
        recycler.setAdapter (adapter);
        recycler.invalidate ();

        fenceAd = new FenceAd(FenceEventsAct.this,fenceEventArrayList);
        LinearLayoutManager layout = new LinearLayoutManager (this);
        recyclerReal.setLayoutManager (layout);
        recyclerReal.setAdapter (fenceAd);
        recyclerReal.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerReal);
        recyclerReal.setNestedScrollingEnabled(false);
        recyclerReal.invalidate ();

    }

    @Override
    public void onPause ()
    {
        super.onPause ();
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onPause called");
    }

    @Override
    public void onResume ()
    {
        super.onResume ();
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onResume called");
        AppController app = (AppController) getApplication ();
        FenceAd adapter = new FenceAd(app);
        recycler.setAdapter (adapter);
        recycler.invalidate ();

        fenceAd = new FenceAd(FenceEventsAct.this,fenceEventArrayList);
        LinearLayoutManager layout = new LinearLayoutManager (this);
        recyclerReal.setLayoutManager (layout);
        recyclerReal.setAdapter (fenceAd);
        recyclerReal.invalidate ();
    }

    @Override
    public void onStart ()
    {
        super.onStart ();
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onStart called");
    }

    @Override
    public void onStop ()
    {
        super.onStop ();
        Log.v (AwajimaLog.LOGTAG, CLASSTAG + "onPause called");
    }

    @Override
    public void onEventClicked(FenceEvent fenceEvent) {

    }

    @Override
    public void onListItemClick(int index) {

    }
}
