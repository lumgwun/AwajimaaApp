package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import com.skylightapp.Classes.AlarmReceiver;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.BirthdayViewAdapter;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TodayBirthDays extends AppCompatActivity {
    public static final String IMPORTANT_LIST_ID = "IMP_Id";
    private RecyclerView recyclerView;
    private List<Birthday> birthdayList;
    private ArrayList<Birthday> birthdayArrayList;

    private BirthdayViewAdapter birthdayViewAdapter;

    private Toolbar mToolbar;
    Context context;

    private AlarmReceiver mAlarmReceiver;

    DBHelper dbHelper;
    String todayDate;
    private BirthdayDAO birthdayDAO;
    Date newDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_today_birth_days);
        recyclerView = findViewById(R.id._todayBirthDays);
        dbHelper = new DBHelper(this);
        birthdayDAO= new BirthdayDAO(this);
        birthdayArrayList = new ArrayList<>();
        newDate= new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        newDate = calendar.getTime();
        todayDate = sdf.format(newDate);
        try {

            birthdayArrayList = birthdayDAO.getBirthdayFromTodayDate(todayDate);
            birthdayViewAdapter = new BirthdayViewAdapter(TodayBirthDays.this, birthdayArrayList);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        //birthdayArrayList = dbHelper.getBirthdayFromTodayDate(todayDate);
        birthdayViewAdapter = new BirthdayViewAdapter(TodayBirthDays.this, birthdayArrayList);
        recyclerView.setAdapter(birthdayViewAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


    }
}