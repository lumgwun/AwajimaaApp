package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.MapAndLoc.EmergencyReport;

import java.util.ArrayList;

import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_LOCID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_LOCTIME;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_ADDRESS;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_BG_ADDRESS;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_BIZ_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_COUNTRY;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_LATLNG;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_MARKET_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_PROF_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_QUESTION;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_STATE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_SUBLOCALE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TOWN;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TYPE;
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
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);

    }
    public long insertUserEmergencyReport(int reportID, int profileID, long bizID, String dateOfToday, String selectedType, String stringLatLng, String locality, String bgAddress, String address, String country,String safeOption) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMERGENCY_REPORT_PROF_ID, profileID);
        contentValues.put(EMERGENCY_LOCID, reportID);
        contentValues.put(EMERGENCY_REPORT_BIZ_ID, bizID);
        contentValues.put(EMERGENCY_LOCTIME, dateOfToday);
        contentValues.put(EMERGENCY_REPORT_SUBLOCALE, locality);
        contentValues.put(EMERGENCY_REPORT_BG_ADDRESS, bgAddress);
        contentValues.put(EMERGENCY_REPORT_TYPE, selectedType);
        contentValues.put(EMERGENCY_REPORT_COUNTRY, country);
        contentValues.put(EMERGENCY_REPORT_ADDRESS, address);
        contentValues.put(EMERGENCY_REPORT_LATLNG, stringLatLng);
        contentValues.put(EMERGENCY_REPORT_QUESTION, safeOption);
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);


    }
    public long insertHotEmergReport(int reportID, int profileID, long bizID, String dateOfToday, String hot, String stringLatLng, String valueOf, String s, String valueOf1, String s1) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMERGENCY_REPORT_PROF_ID, profileID);
        contentValues.put(EMERGENCY_LOCID, reportID);
        contentValues.put(EMERGENCY_REPORT_BIZ_ID, bizID);
        contentValues.put(EMERGENCY_LOCTIME, dateOfToday);
        contentValues.put(EMERGENCY_REPORT_TYPE, hot);
        contentValues.put(EMERGENCY_REPORT_ADDRESS, valueOf1+","+valueOf);
        contentValues.put(EMERGENCY_REPORT_LATLNG, stringLatLng);
        contentValues.put(EMERGENCY_REPORT_QUESTION, "Can't Say");
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);
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
            String locality = cursor.getString(10);
            String bgAddress = cursor.getString(12);
            String address = cursor.getString(8);
            String country = cursor.getString(17);
            long bizID = cursor.getLong(15);
            String status = cursor.getString(6);
            String strLatLng = cursor.getString(9);

            reportArrayList.add(new EmergencyReport(reportId, profileID,bizID,reportTime, type, lat,lng,locality, bgAddress,address,country,strLatLng,status));
        }


    }
    public ArrayList<EmergencyReport> getEmergencyReportForCountry(String country) {
        ArrayList<EmergencyReport> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERGENCY_REPORT_COUNTRY + "=?";
        String[] selectionArgs = new String[]{country};

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
    public ArrayList<EmergencyReport> getEmergencyReportForBiz(int bizID) {
        ArrayList<EmergencyReport> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERGENCY_REPORT_BIZ_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID)};

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
    public ArrayList<EmergencyReport> getEmergencyReportForState(String state) {
        ArrayList<EmergencyReport> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERGENCY_REPORT_STATE + "=?";
        String[] selectionArgs = new String[]{state};

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
    public ArrayList<EmergencyReport> getEmergencyReportForTown(String town) {
        ArrayList<EmergencyReport> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERGENCY_REPORT_TOWN + "=?";
        String[] selectionArgs = new String[]{town};

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
    public ArrayList<EmergencyReport> getEmergencyReportByType(String type) {
        ArrayList<EmergencyReport> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERGENCY_REPORT_TYPE + "=?";
        String[] selectionArgs = new String[]{type};

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
    public ArrayList<EmergencyReport> getEmergencyReportForMarket(int marketID) {
        ArrayList<EmergencyReport> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = EMERGENCY_REPORT_MARKET_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(marketID)};

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
    public ArrayList<EmergencyReport> getEmergencyReportFromProfile(int profileID) {
        ArrayList<EmergencyReport> emergencyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
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
        SQLiteDatabase db = this.getReadableDatabase();
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
    public ArrayList<String> getEmergencyStates() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] colums = {EMERGENCY_REPORT_STATE};

        try (Cursor res = db.query(EMERGENCY_REPORT_TABLE, colums, null, null, null,
                null, null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(16));
                res.moveToNext();
            }
        }
        return array_list;
    }
    public ArrayList<EmergencyReport> getStateEmergForDate(String state, String date) {
        ArrayList<EmergencyReport> reportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = EMERGENCY_REPORT_STATE + "=? AND " + EMERGENCY_LOCTIME + "=?";
        String[] selectionArgs = new String[]{state, date};

        Cursor cursor = db.query(EMERGENCY_REPORT_TABLE, null, selection, selectionArgs, null,
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
    public ArrayList<EmergencyReport> getStateEmergForToday(String state, String date) {
        ArrayList<EmergencyReport> reportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = EMERGENCY_REPORT_STATE + "=? AND " + EMERGENCY_LOCTIME + "=?";
        String[] selectionArgs = new String[]{state, date};

        Cursor cursor = db.query(EMERGENCY_REPORT_TABLE, null, selection, selectionArgs, null,
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
    private void getMapReportCursor(ArrayList<EmergencyReport> reportArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int reportId = cursor.getInt(0);
            double lat = cursor.getDouble(4);
            double lng = cursor.getDouble(5);

            reportArrayList.add(new EmergencyReport(reportId, lat,lng));
        }


    }
    public ArrayList<EmergencyReport> getStateMapEmergForDate(String state, String date) {
        ArrayList<EmergencyReport> reportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = EMERGENCY_REPORT_STATE + "=? AND " + EMERGENCY_LOCTIME + "=?";
        String[] selectionArgs = new String[]{state, date};

        Cursor cursor = db.query(EMERGENCY_REPORT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getMapReportCursor(reportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return reportArrayList;
    }


}
