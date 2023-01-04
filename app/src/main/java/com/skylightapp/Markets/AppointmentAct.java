package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;

import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;
import com.skylightapp.ConfirmationActivity;
import com.skylightapp.MapAndLoc.EmergReportMapA;
import com.skylightapp.R;

import java.util.Calendar;

import static com.hannesdorfmann.mosby3.PresenterManager.DEBUG_TAG;
import static com.skylightapp.Classes.ImageUtil.TAG;

public class AppointmentAct extends AppCompatActivity {
    private Calendar calendar;
    private long eventID;
    CalendarContract.Events events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_appointment);
        calendar=Calendar.getInstance();
        CalenderEvent calenderEvent = findViewById(R.id.calender_event);
        Event event = new Event(calendar.getTimeInMillis(), "Subscription", Color.RED);
        calenderEvent.addEvent(event);


        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                Log.d(TAG, dayContainerModel.getDate());
            }
        });
        if(calenderEvent !=null){
            //eventID

        }
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.MINUTES, 15);
        values.put(CalendarContract.Reminders.EVENT_ID, eventID);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = cr.insert(CalendarContract.Reminders.CONTENT_URI, values);



        long calID = 3;
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime22 = Calendar.getInstance();
        beginTime22.set(2012, 9, 14, 7, 30);
        startMillis = beginTime22.getTimeInMillis();
        Calendar endTime33 = Calendar.getInstance();
        endTime33.set(2012, 9, 14, 8, 45);
        endMillis = endTime33.getTimeInMillis();

        ContentResolver cr33 = getContentResolver();
        ContentValues values33 = new ContentValues();
        values33.put(CalendarContract.Events.DTSTART, startMillis);
        values33.put(CalendarContract.Events.DTEND, endMillis);
        values33.put(CalendarContract.Events.TITLE, "Jazzercise");
        values33.put(CalendarContract.Events.DESCRIPTION, "Group workout");
        values33.put(CalendarContract.Events.CALENDAR_ID, calID);
        values33.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Los_Angeles");
        Uri uri33 = cr33.insert(CalendarContract.Events.CONTENT_URI, values);

// get the event ID that is the last element in the Uri
        long eventID = Long.parseLong(uri.getLastPathSegment());





        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2012, 0, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2012, 0, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Awajima Appointment")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Uchechi Dappa")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Awajima Office")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        startActivity(intent);
    }
    private void addEventAttendee(long eventID) {
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Attendees.ATTENDEE_NAME, "Trevor");
        values.put(CalendarContract.Attendees.ATTENDEE_EMAIL, "trevor@example.com");
        values.put(CalendarContract.Attendees.ATTENDEE_RELATIONSHIP, CalendarContract.Attendees.RELATIONSHIP_ATTENDEE);
        values.put(CalendarContract.Attendees.ATTENDEE_TYPE, CalendarContract.Attendees.TYPE_OPTIONAL);
        values.put(CalendarContract.Attendees.ATTENDEE_STATUS, CalendarContract.Attendees.ATTENDEE_STATUS_INVITED);
        values.put(CalendarContract.Attendees.EVENT_ID, eventID);
        Uri uri = cr.insert(CalendarContract.Attendees.CONTENT_URI, values);

    }

    private void deleteEvent(long eventID) {
        ContentResolver cr = getContentResolver();
        Uri deleteUri = null;
        deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        int rows = cr.delete(deleteUri, null, null);
        Log.i(DEBUG_TAG, "Rows deleted: " + rows);


    }
    private void updateEvent(long eventID) {
        ContentResolver cr = getContentResolver();
        Uri deleteUri = null;
        ContentValues values = new ContentValues();
        long calID = 2;
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Trevor's Calendar");
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, eventID);
        int rows = getContentResolver().update(updateUri, values, null, null);
        Log.i(DEBUG_TAG, "Rows updated: " + rows);

    }
}