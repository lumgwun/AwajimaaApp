package com.skylightapp.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.skylightapp.MapAndLoc.EmergReportNext;

import java.util.ArrayList;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_LAT;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_LATLNG;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_LNG;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_LOCID;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_LOCTIME;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_REPORT_ID;
import static com.skylightapp.MapAndLoc.EmergReportNext.EMERGENCY_NEXT_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_REPORT_ID;
import static java.lang.String.valueOf;

public class EmergReportNextDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = CUSTOMER_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public EmergReportNextDAO(Context context) {
        super(context);
    }
    public long insertNewEmergNextLoc(int emergencyNextReportID,int emergencyReportID, String dateOfToday, String latLng) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMERGENCY_NEXT_LOCID, emergencyNextReportID);
        values.put(EMERGENCY_NEXT_REPORT_ID, emergencyReportID);
        values.put(EMERGENCY_NEXT_LOCTIME, dateOfToday);
        values.put(EMERGENCY_NEXT_LATLNG, latLng);
        return db.insert(EMERGENCY_NEXT_REPORT_TABLE, null, values);

    }
    public ArrayList<String> getLatLngStrngForEmergR(int emegReportID) {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERG_REPORT_ID + "=?";
        String[] columns = {EMERGENCY_NEXT_LATLNG};
        String[] selectionArgs = new String[]{valueOf(emegReportID)};
        Cursor res = db.query(EMERGENCY_NEXT_REPORT_TABLE,columns,selection,selectionArgs,null,null,null);


        if(res !=null && res.getCount()>0){

            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(5));
                    res.moveToNext();
                }
                res.close();
            }
        }


        return array_list;

    }

    public ArrayList<EmergReportNext> getAllEmergNextReportForEmergReport(int reportID) {
        ArrayList<EmergReportNext> emergReportNexts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERGENCY_NEXT_REPORT_ID + "=? ";
        String[] selectionArgs = new String[]{valueOf(reportID)};

        String[] columns = {EMERGENCY_NEXT_LOCID, EMERG_REPORT_ID,EMERGENCY_NEXT_LOCTIME,EMERGENCY_NEXT_LAT,EMERGENCY_NEXT_LNG};
        Cursor cursor = db.query(EMERGENCY_NEXT_REPORT_TABLE, columns, selection, selectionArgs, null,
                null, null);
        getEmergNextCursor(emergReportNexts, cursor);
        cursor.close();
        db.close();
        return emergReportNexts;
    }
    public ArrayList<EmergReportNext> getAllEmergNextReportEmergReport(int reportID,String current) {
        ArrayList<EmergReportNext> emergReportNexts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {EMERGENCY_NEXT_LOCID, EMERG_REPORT_ID,EMERGENCY_NEXT_LOCTIME,EMERGENCY_NEXT_LAT,EMERGENCY_NEXT_LNG};

        try {
            Cursor cursor = db.rawQuery("Select columns from EMERGENCY_NEXT_REPORT_TABLE INNER JOIN EMERGENCY_REPORT_TABLE ON EMERGENCY_NEXT_REPORT_ID=EMERGENCY_LOCID WHERE EMERGENCY_REPORT_STATUS='" + current + "'", null);

            if (cursor == null) return null;

            if (cursor.moveToFirst()) {
                getEmergNextCursor(emergReportNexts, cursor);
            }


            cursor.close();
        } catch (Exception e) {
            Log.e("tle99", e.getMessage());
        }

        return emergReportNexts;
    }
    private void getEmergNextCursor(ArrayList<EmergReportNext> emergReportNexts, Cursor cursor) {
        while (cursor.moveToNext()) {

            int emergNextID = cursor.getInt(0);
            int emergReportID = cursor.getInt(1);
            String time = cursor.getString(2);
            String latlng = cursor.getString(5);
            emergReportNexts.add(new EmergReportNext(emergNextID, emergReportID, time, latlng));
        }
    }

    public long insertNewEmergNextLoc(int reportTrackingID, int reportID, String dateOfToday, String latLng, long reportIDF) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMERGENCY_NEXT_LOCID, reportTrackingID);
        values.put(EMERGENCY_NEXT_REPORT_ID, reportID);
        values.put(EMERGENCY_NEXT_LOCTIME, dateOfToday);
        values.put(EMERGENCY_NEXT_LATLNG, latLng);
        return db.insert(EMERGENCY_NEXT_REPORT_TABLE, null, values);
    }
}
