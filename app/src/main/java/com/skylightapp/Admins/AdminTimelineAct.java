package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.TimeLineAdapter;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;

import java.util.ArrayList;

public class AdminTimelineAct extends AppCompatActivity {
    private FloatingActionButton fab;
    private ListView payNow;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private Button home, settings;

    private RecyclerView recyclerView;

    private ArrayList<TimeLine> timeLines;
    private TimeLineAdapter mAdapter;

    DBHelper dbHelper;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;
    private CusDAO cusDAO;
    private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private PaymentDAO paymentDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private AdminBalanceDAO adminBalanceDAO;
    String json;
    private static final String PREF_NAME = "awajima";
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_timeline);
        setTitle("Users Timelines");
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id.notification_fab2);
        //long profileID =userProfile.getuID();
        recyclerView = findViewById(R.id.recycler_view_timeline25);
        timeLines = new ArrayList<TimeLine>();
        dbHelper = new DBHelper(this);
        timeLineClassDAO= new TimeLineClassDAO(this);


        mAdapter = new TimeLineAdapter(this, R.layout.timeline_list_row_admin, timeLines);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            //dbHelper = new DBHelper(this);
            sqLiteDatabase = dbHelper.getWritableDatabase();

            timeLines = timeLineClassDAO.getAllTimeLinesAdmin();


        }



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }
}