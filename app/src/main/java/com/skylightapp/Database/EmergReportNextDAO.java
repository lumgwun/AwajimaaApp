package com.skylightapp.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.EmergReportNext;

import java.util.ArrayList;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LAT;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LATLNG;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LNG;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LOCID;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_LOCTIME;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_REPORT_ID;
import static com.skylightapp.Classes.EmergReportNext.EMERGENCY_NEXT_REPORT_TABLE;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_LOCID;
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
        db.insert(EMERGENCY_NEXT_REPORT_TABLE, null, values);
        //emergencyNextReportID = db.insert(EMERGENCY_NEXT_REPORT_TABLE, EMERGENCY_NEXT_LOCID, values);

        return emergencyNextReportID;
    }
    public ArrayList<String> getAllLatLngForEmergReport(int emegReportID) {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERGENCY_LOCID + "=?";
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
    public ArrayList<EmergReportNext> getAllEmergNextReportFprEmergReport(int reportID) {
        ArrayList<EmergReportNext> emergReportNexts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERGENCY_NEXT_REPORT_ID + "=? ";
        String[] selectionArgs = new String[]{valueOf(reportID)};

        String[] columns = {EMERGENCY_NEXT_LOCID,EMERGENCY_LOCID,EMERGENCY_NEXT_LOCTIME,EMERGENCY_NEXT_LAT,EMERGENCY_NEXT_LNG};
        Cursor cursor = db.query(EMERGENCY_NEXT_REPORT_TABLE, columns, selection, selectionArgs, null,
                null, null);
        getEmergNextCursor(emergReportNexts, cursor);
        cursor.close();
        db.close();
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
}
