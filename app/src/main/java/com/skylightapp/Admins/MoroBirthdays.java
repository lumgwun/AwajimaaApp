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
import android.os.Bundle;

import com.skylightapp.Classes.AlarmReceiver;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.BirthdayViewAdapter;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MoroBirthdays extends AppCompatActivity {
    public static final String IMPORTANT_LIST_ID = "IMP_Id";
    private RecyclerView recyclerView;
    private List<Birthday> birthdayList;
    private ArrayList<Birthday> birthdayArrayList;

    private BirthdayViewAdapter birthdayViewAdapter;

    private Toolbar mToolbar;
    Context context;

    private AlarmReceiver mAlarmReceiver;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_moro_birthdays);
        recyclerView = findViewById(R.id._MoroBirthDays);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dbHelper = new DBHelper(this);
        birthdayArrayList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date newDate = calendar.getTime();
        String tomorrowDaysDate = sdf.format(newDate);
        try {

            birthdayArrayList = dbHelper.getBirthdayFromTodayDate(tomorrowDaysDate);
            birthdayViewAdapter = new BirthdayViewAdapter(MoroBirthdays.this, birthdayArrayList);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        birthdayViewAdapter = new BirthdayViewAdapter(MoroBirthdays.this, birthdayArrayList);
        recyclerView.setAdapter(birthdayViewAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }
}