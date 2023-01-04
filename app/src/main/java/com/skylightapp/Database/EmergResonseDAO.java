package com.skylightapp.Database;

import static com.skylightapp.MapAndLoc.EmergResponse.RESPONDER_DEVICE_ID;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONDER_EMERG_ID;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONDER_ORIGIN;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONDER_PROF_ID;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_CATEGORY;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_DATE;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_END_TIME;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_ID;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_START_TIME;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_STATE;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_STATUS_A;
import static com.skylightapp.MapAndLoc.EmergResponse.RESPONSE_TABLE;
import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.MapAndLoc.EmergResponse;

import java.util.ArrayList;

public class EmergResonseDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = RESPONSE_ID
            + " =?";
    public EmergResonseDAO(Context context) {
        super(context);
    }

    public int getEmergResponseCount() {
        String countQuery = "SELECT * FROM " + RESPONSE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertEmergResponse(int responseID, int emergID, int profID,int awajimaDeviceID,String time, String origin,String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESPONSE_ID, responseID);
        contentValues.put(RESPONDER_EMERG_ID, emergID);
        contentValues.put(RESPONDER_PROF_ID, profID);
        contentValues.put(RESPONSE_START_TIME, time);
        contentValues.put(RESPONDER_DEVICE_ID, awajimaDeviceID);
        contentValues.put(RESPONDER_ORIGIN, origin);
        contentValues.put(RESPONSE_STATUS_A, status);
        return sqLiteDatabase.insert(RESPONSE_TABLE, null, contentValues);

    }

    public void deleteEmergResponse(int responseID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = RESPONSE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(responseID)};
        db.delete(RESPONSE_TABLE, selection, selectionArgs);

    }

    private void getResponseFromCursor(ArrayList<EmergResponse> emergResponses, Cursor cursor) {
        while (cursor.moveToNext()) {

            int responseID = cursor.getInt(9);
            int profileID = cursor.getInt(2);
            int emergID = cursor.getInt(1);
            int deviceID = cursor.getInt(3);
            String responseTime = cursor.getString(4);
            String origin = cursor.getString(6);
            int count = cursor.getInt(7);
            String endTime = cursor.getString(5);
            String status = cursor.getString(6);
            String date = cursor.getString(10);
            String state = cursor.getString(11);
            String category = cursor.getString(12);
            emergResponses.add(new EmergResponse(responseID, emergID,deviceID,profileID,origin,date,count,responseTime,state,category,endTime,status));
        }


    }
    public ArrayList<EmergResponse> getEmergResponseForEmerg(int emergID) {
        ArrayList<EmergResponse> emergResponses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = RESPONDER_EMERG_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(emergID)};
        Cursor cursor = db.query(RESPONSE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getResponseFromCursor(emergResponses, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return emergResponses;
    }
    public ArrayList<EmergResponse> getEmergResponseFromDevice(int deviceID) {
        ArrayList<EmergResponse> emergResponses = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = RESPONDER_DEVICE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(deviceID)};
        Cursor cursor = db.query(RESPONSE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getResponseFromCursor(emergResponses, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return emergResponses;
    }
    public ArrayList<EmergResponse> getEmergResponseForState(String state) {
        ArrayList<EmergResponse> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = RESPONSE_STATE + "=?";
        String[] selectionArgs = new String[]{state};
        Cursor cursor = db.query(RESPONSE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getResponseFromCursor(emergencyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return emergencyReports;
    }
    public ArrayList<EmergResponse> getEmergResForStateForDate(String state, String date) {
        ArrayList<EmergResponse> emergResponseArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = RESPONSE_STATE + "=? AND " + RESPONSE_DATE + "=?";
        String[] selectionArgs = new String[]{state, date};

        Cursor cursor = db.query(RESPONSE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getResponseFromCursor(emergResponseArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return emergResponseArrayList;
    }
    public ArrayList<EmergResponse> getEmergResForCat(String category) {
        ArrayList<EmergResponse> emergResponseArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = RESPONSE_CATEGORY + "=?";
        String[] selectionArgs = new String[]{category};

        Cursor cursor = db.query(RESPONSE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getResponseFromCursor(emergResponseArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return emergResponseArrayList;
    }
    public ArrayList<EmergResponse> getAllEmergResponses() {
        ArrayList<EmergResponse> emergResponseArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(RESPONSE_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getResponseFromCursor(emergResponseArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return emergResponseArrayList;
    }
    public ArrayList<EmergResponse> getEmergResForCatForDate(String category, String date) {
        ArrayList<EmergResponse> emergResponseArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = RESPONSE_CATEGORY + "=? AND " + RESPONSE_DATE + "=?";
        String[] selectionArgs = new String[]{category, date};

        Cursor cursor = db.query(RESPONSE_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getResponseFromCursor(emergResponseArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return emergResponseArrayList;
    }

    public ArrayList<String> getEmergResponseTimes(int responseID) {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colums = {RESPONSE_END_TIME};
        String selection = RESPONSE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(responseID)};
        try (Cursor res = db.query(RESPONSE_TABLE, colums, null, null, null,
                null, null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(16));
                res.moveToNext();
            }
        }
        return array_list;
    }

}
