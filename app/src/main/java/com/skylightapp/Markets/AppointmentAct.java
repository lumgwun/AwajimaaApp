package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;
import com.skylightapp.R;

import java.util.Calendar;

import static com.skylightapp.Classes.ImageUtil.TAG;

public class AppointmentAct extends AppCompatActivity {
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_appointment);
        CalenderEvent calenderEvent = findViewById(R.id.calender_event);
        Event event = new Event(calendar.getTimeInMillis(), "Test", Color.RED);
        calenderEvent.addEvent(event);


        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                Log.d(TAG, dayContainerModel.getDate());
            }
        });
    }
}