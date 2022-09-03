package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Classes.Transaction;

import java.util.ArrayList;

import static com.skylightapp.Classes.Loan.LOAN_ID;
import static com.skylightapp.Classes.TimeLine.TIMELINE_AMOUNT;
import static com.skylightapp.Classes.TimeLine.TIMELINE_CLIENT_NAME;
import static com.skylightapp.Classes.TimeLine.TIMELINE_CUS_ID;
import static com.skylightapp.Classes.TimeLine.TIMELINE_DETAILS;
import static com.skylightapp.Classes.TimeLine.TIMELINE_GETTING_ACCOUNT;
import static com.skylightapp.Classes.TimeLine.TIMELINE_ID;
import static com.skylightapp.Classes.TimeLine.TIMELINE_LOCATION;
import static com.skylightapp.Classes.TimeLine.TIMELINE_PROF_ID;
import static com.skylightapp.Classes.TimeLine.TIMELINE_SENDING_ACCOUNT;
import static com.skylightapp.Classes.TimeLine.TIMELINE_TABLE;
import static com.skylightapp.Classes.TimeLine.TIMELINE_TIME;
import static com.skylightapp.Classes.TimeLine.TIMELINE_TITTLE;
import static com.skylightapp.Classes.TimeLine.TIMELINE_WORKER_NAME;
import static java.lang.String.valueOf;

public class TimeLineClassDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = TIMELINE_ID
            + " =?";
    public TimeLineClassDAO(Context context) {
        super(context);
    }
    public ArrayList<TimeLine> getTimeLinesFromCurrentProfile(int profileID) {
        ArrayList<TimeLine> timeLines = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TIMELINE_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(TIMELINE_TABLE, null,  selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTimeLinesFromCursorAdmin(timeLines, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return timeLines;
    }
    public long insertTimeLineNew(String timelineTittle, int profileID,int cusID, String details, String date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMELINE_PROF_ID, profileID);
        contentValues.put(TIMELINE_CUS_ID, cusID);
        contentValues.put(TIMELINE_TITTLE, timelineTittle);
        contentValues.put(TIMELINE_DETAILS, details);
        contentValues.put(TIMELINE_TIME, date);
        return sqLiteDatabase.insert(TIMELINE_TABLE, null, contentValues);

    }
    public long insertTimeLine(String tittle, String timelineDetails, String timeLineTime, Location mCurrentLocation) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMELINE_ID, tittle);
        contentValues.put(TIMELINE_DETAILS, timelineDetails);
        contentValues.put(TIMELINE_LOCATION, String.valueOf(mCurrentLocation));
        contentValues.put(TIMELINE_TIME, timeLineTime);
        return sqLiteDatabase.insert(TIMELINE_TABLE, null, contentValues);


    }

    public long insertTimeLine(Profile profile, Customer customer, TimeLine timeLine, Transaction transaction) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIMELINE_PROF_ID, profile.getPID());
        contentValues.put(TIMELINE_CUS_ID, customer.getCusUID());
        contentValues.put(TIMELINE_ID, timeLine.getTimelineID());
        contentValues.put(TIMELINE_DETAILS, timeLine.getTimelineDetails());
        contentValues.put(TIMELINE_TIME, timeLine.getTimestamp());
        contentValues.put(TIMELINE_AMOUNT, timeLine.getTimeline_Amount());
        contentValues.put(TIMELINE_SENDING_ACCOUNT, transaction.getTranxSendingAcct());
        contentValues.put(TIMELINE_GETTING_ACCOUNT, transaction.getTranxDestAcct());
        contentValues.put(TIMELINE_WORKER_NAME, profile.getProfileLastName() + "," + profile.getProfileFirstName());
        contentValues.put(TIMELINE_CLIENT_NAME, customer.getCusSurname() + "," + customer.getCusFirstName());
        return sqLiteDatabase.insert(TIMELINE_TABLE, null, contentValues);

    }

    public ArrayList<TimeLine> getAllTimeLinesCustomer(int customerID) {
        ArrayList<TimeLine> timeLines = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TIMELINE_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(TIMELINE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTimeLinesFromCursorAdmin(timeLines, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        return timeLines;
    }
    public ArrayList<TimeLine> getAllTimeLinesProfile(int profileID) {
        ArrayList<TimeLine> timeLines = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TIMELINE_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(TIMELINE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTimeLinesFromCursorAdmin(timeLines, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return timeLines;
    }
    public ArrayList<TimeLine> getAllTimeLinesAdmin() {
        ArrayList<TimeLine> timeLines = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TIMELINE_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTimeLinesFromCursorAdmin(timeLines, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return timeLines;
    }


    private void getTimeLinesFromCursorAdmin(ArrayList<TimeLine> timeLines, Cursor cursor) {
        while (cursor.moveToNext()) {

            int id = cursor.getInt(0);
            String tittle = cursor.getString(3);
            String details = cursor.getString(4);

            timeLines.add(new TimeLine(id,tittle, details));
        }

    }
}
