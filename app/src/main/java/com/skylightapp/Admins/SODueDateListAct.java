package com.skylightapp.Admins;

import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.skylightapp.Adapters.StandingOrderAdapter;
import com.skylightapp.Classes.AlarmReceiver;
import com.skylightapp.Classes.ImportantDates;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.SODAO;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.UpDateSO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SODueDateListAct extends AppCompatActivity implements  StandingOrderAdapter.OnItemsClickListener{
    private RecyclerView RecyclerViewCustomDate;
    public static final String IMPORTANT_LIST_ID = "IMP_Id";
    private RecyclerView RVTwo, recyclerViewToday,RV4;
    private ArrayList<ImportantDates> packageList2;
    private ArrayList<StandingOrder> standingOrders;
    private ArrayList<StandingOrder> standingOrderArrayList;

    private StandingOrderAdapter orderAdapter;
    private StandingOrderAdapter standingOrderAdapter;

    private Toolbar mToolbar;
    Context context;

    private AlarmReceiver mAlarmReceiver;

    DBHelper dbHelper;
    StandingOrder standingOrder;
    private String stringSpnDay,dateOfCustomDays;
    private String[] mDateSplit;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private Date mBirthDate;
    private TextView txtDueSOCount;
    int dueSOCount;
    private  Date customDayDate;
    int noOfDay,customDaySOCount;
    AppCompatSpinner spnDaysAhead;
    private AppCompatButton btnSearchDB;
    private  Bundle bundle;
    private SODAO sodao;
    String soEndDate;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_so_due_date_list);
        RecyclerViewCustomDate = findViewById(R.id.recyclerViewCustomSO);
        recyclerViewToday = findViewById(R.id.recyclerViewToday);
        txtDueSOCount = findViewById(R.id.actSODueCount);
        btnSearchDB = findViewById(R.id.buttonSearchSODB);
        spnDaysAhead = findViewById(R.id.days_SOspn_);
        bundle= new Bundle();
        sodao= new SODAO(this);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        spnDaysAhead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringSpnDay = (String) parent.getSelectedItem();
                try {
                    noOfDay= Integer.parseInt(stringSpnDay);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                Toast.makeText(SODueDateListAct.this, "No. of Days Ahead: "+ stringSpnDay,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        calendar.add(Calendar.DAY_OF_YEAR, noOfDay);
        customDayDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        dateOfCustomDays =dateFormat.format(customDayDate);



        RecyclerViewCustomDate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        dbHelper = new DBHelper(this);
        standingOrder= new StandingOrder();
        standingOrders = new ArrayList<StandingOrder>();

        Calendar calendar1 = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayDate = sdf.format(calendar1.getTime());

        try {

            if(sqLiteDatabase !=null){
                sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            }
            if(sodao !=null){
                try {
                    dueSOCount=sodao.getDueSOTodayCount(todayDate);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }



        if(dueSOCount >0){
            txtDueSOCount.setText("Due SO Today:"+ dueSOCount);

        }else if(dueSOCount ==0){
            txtDueSOCount.setText("Due SO Today:0");

        }
        if(standingOrder !=null){
            soEndDate = standingOrder.getSoEndDate();

        }

        try {

            if(sqLiteDatabase !=null){
                sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            }

            if(sodao !=null){

                try {
                    standingOrders = sodao.getAllSOEndingToday(soEndDate);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        recyclerViewToday.setLayoutManager(new LinearLayoutManager(SODueDateListAct.this, LinearLayoutManager.HORIZONTAL, false));
        standingOrderAdapter = new StandingOrderAdapter(SODueDateListAct.this, standingOrders);
        recyclerViewToday.setAdapter(standingOrderAdapter);
        recyclerViewToday.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));


        orderAdapter = new StandingOrderAdapter(SODueDateListAct.this, standingOrderArrayList);
        RecyclerViewCustomDate.setAdapter(orderAdapter);
        RecyclerViewCustomDate.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewToday.setVisibility(View.GONE);
                try {

                    if(sqLiteDatabase !=null){
                        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                    }
                    if(sodao !=null){
                        try {
                            standingOrderArrayList = sodao.getSOEndingCustomDay(dateOfCustomDays);
                            customDaySOCount =sodao.getDueSOCustomCount(dateOfCustomDays);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }




                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(SODueDateListAct.this, LinearLayoutManager.HORIZONTAL, false);
                RecyclerViewCustomDate.setLayoutManager(layoutManagerC);
                //RecyclerViewCustomDate.setHasFixedSize(true);
                orderAdapter = new StandingOrderAdapter(SODueDateListAct.this,standingOrderArrayList);
                RecyclerViewCustomDate.setAdapter(orderAdapter);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(RecyclerViewCustomDate.getContext(),
                        layoutManagerC.getOrientation());
                RecyclerViewCustomDate.addItemDecoration(dividerItemDecoration7);
                //snapHelper.attachToRecyclerView(customPackages);

                if(customDaySOCount >0){
                    txtDueSOCount.setText("Due Standing Orders:"+ customDaySOCount);

                }else if(customDaySOCount ==0){
                    txtDueSOCount.setText("Due Standing Orders:0");

                }

            }
        });

    }
    public static String stringToDayAndMonth(String birthdayString,String[] mDateSplit) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int mMonth, mDay;
        mDateSplit=null;
        if (birthdayString.startsWith("/")) {
            //birthdayString = birthdayString.replaceFirst("/", "1990");
            mDateSplit = birthdayString.split("-");
            mDay = Integer.parseInt(mDateSplit[2]);
            mMonth = Integer.parseInt(mDateSplit[1]);
            birthdayString=mMonth+"-"+mDay;
        }
        return birthdayString;
    }

    @Override
    public void onItemClick(StandingOrder standingOrder) {
        bundle= new Bundle();
        bundle.putParcelable("StandingOrder",standingOrder);
        Intent intent = new Intent(SODueDateListAct.this, UpDateSO.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}