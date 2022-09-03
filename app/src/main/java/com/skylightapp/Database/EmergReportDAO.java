package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.EmergencyReport;

import java.util.ArrayList;

import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_LOCID;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_LOCTIME;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_ADDRESS;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_LATLNG;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_PROF_ID;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.Classes.EmergencyReport.EMERGENCY_REPORT_TYPE;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static java.lang.String.valueOf;

public class EmergReportDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = EMERGENCY_LOCID
            + " =?";
    public EmergReportDAO(Context context) {
        super(context);
    }


    public int getEmergencyReportCount() {
        String countQuery = "SELECT * FROM " + EMERGENCY_REPORT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertUserEmergencyReport(int locID, int profileID, String time, String type,String report, String latLng) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMERGENCY_REPORT_PROF_ID, profileID);
        contentValues.put(EMERGENCY_LOCID, locID);
        contentValues.put(EMERGENCY_LOCTIME, time);
        contentValues.put(EMERGENCY_REPORT_TYPE, type);
        contentValues.put(EMERGENCY_REPORT, report);
        contentValues.put(EMERGENCY_REPORT_LATLNG, latLng);
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);

    }
    public long insertUserEmergencyReport(int reportID, int profileID, String reportTime, String type,String report,String address, String latlong) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMERGENCY_REPORT_PROF_ID, profileID);
        contentValues.put(EMERGENCY_LOCID, reportID);
        contentValues.put(EMERGENCY_LOCTIME, reportTime);
        contentValues.put(EMERGENCY_REPORT_TYPE, type);
        contentValues.put(EMERGENCY_REPORT, report);
        contentValues.put(EMERGENCY_REPORT_ADDRESS, address);
        contentValues.put(EMERGENCY_REPORT_LATLNG, latlong);
        sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);
        return reportID;
    }
    public void deleteLocationReport(int reportID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERGENCY_REPORT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(reportID)};
        db.delete(EMERGENCY_REPORT_TABLE, selection, selectionArgs);

    }

    private void getLocReportFromCursor(ArrayList<EmergencyReport> reportArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int reportId = cursor.getInt(0);
            int profileID = cursor.getInt(1);
            String reportTime = cursor.getString(2);
            String type = cursor.getString(3);
            double lat = cursor.getDouble(4);
            double lng = cursor.getDouble(5);
            String status = cursor.getString(6);

            reportArrayList.add(new EmergencyReport(reportId, profileID,reportTime, type, lat,lng, status));
        }


    }
    public ArrayList<EmergencyReport> getEmergencyReportFromProfile(int profileID) {
        ArrayList<EmergencyReport> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERGENCY_REPORT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(EMERGENCY_REPORT_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLocReportFromCursor(emergencyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return emergencyReports;
    }
    public ArrayList<EmergencyReport> getAllEmergencyReports() {
        ArrayList<EmergencyReport> reportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(EMERGENCY_REPORT_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getLocReportFromCursor(reportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return reportArrayList;
    }

}
