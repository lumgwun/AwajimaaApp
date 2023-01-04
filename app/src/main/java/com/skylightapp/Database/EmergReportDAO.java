package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.MapAndLoc.EmergencyReport;

import java.util.ArrayList;

import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_REPORT_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_PLACE_LATLNG;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_TIME;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_REPORT;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_ADDRESS;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_BG_ADDRESS;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_BIZ_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_COMPANY;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_COUNTRY;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_LATLNG;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_LGA;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_MARKET_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_MORE_INFO;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_PROF_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_QUESTION;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_STATE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_SUBLOCALE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_TOWN;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_R_TYPE;
import static java.lang.String.valueOf;

public class EmergReportDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = EMERG_REPORT_ID
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
        contentValues.put(EMERG_R_PROF_ID, profileID);
        contentValues.put(EMERG_REPORT_ID, locID);
        contentValues.put(EMERG_R_TIME, time);
        contentValues.put(EMERG_R_TYPE, type);
        contentValues.put(EMERG_REPORT, report);
        contentValues.put(EMERG_R_LATLNG, latLng);
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);

    }
    public long insertUserEmergencyReport(int reportID, int profileID, String reportTime, String type,String report,String address, String latlong) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMERG_R_PROF_ID, profileID);
        contentValues.put(EMERG_REPORT_ID, reportID);
        contentValues.put(EMERG_R_TIME, reportTime);
        contentValues.put(EMERG_R_TYPE, type);
        contentValues.put(EMERG_REPORT, report);
        contentValues.put(EMERG_R_ADDRESS, address);
        contentValues.put(EMERG_R_LATLNG, latlong);
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);

    }
    public long insertUserEmergencyReport(int reportID, int profileID, long bizID, String dateOfToday, String selectedType, String stringLatLng, String locality, String bgAddress, String address, String country,String safeOption) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMERG_R_PROF_ID, profileID);
        contentValues.put(EMERG_REPORT_ID, reportID);
        contentValues.put(EMERG_R_BIZ_ID, bizID);
        contentValues.put(EMERG_R_TIME, dateOfToday);
        contentValues.put(EMERG_R_SUBLOCALE, locality);
        contentValues.put(EMERG_R_BG_ADDRESS, bgAddress);
        contentValues.put(EMERG_R_TYPE, selectedType);
        contentValues.put(EMERG_R_COUNTRY, country);
        contentValues.put(EMERG_R_ADDRESS, address);
        contentValues.put(EMERG_R_LATLNG, stringLatLng);
        contentValues.put(EMERG_R_QUESTION, safeOption);
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);


    }
    public long insertHotEmergReport(int reportID, int profileID, long bizID, String dateOfToday, String hot, String stringLatLng, String valueOf, String s, String valueOf1, String s1) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMERG_R_PROF_ID, profileID);
        contentValues.put(EMERG_REPORT_ID, reportID);
        contentValues.put(EMERG_R_BIZ_ID, bizID);
        contentValues.put(EMERG_R_TIME, dateOfToday);
        contentValues.put(EMERG_R_TYPE, hot);
        contentValues.put(EMERG_R_ADDRESS, valueOf1+","+valueOf);
        contentValues.put(EMERG_R_LATLNG, stringLatLng);
        contentValues.put(EMERG_R_QUESTION, "Can't Say");
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);
    }
    public long insertUserEmergencyReport(int sReportID, int profileID1, long bizID, String reportDate, String oil_spillage_report, String localityString, String subUrb, String selectedLGA, String selectedOilCompany, String address, String strngLatLng, String moreInfo, String iamAvailable) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMERG_R_PROF_ID, profileID1);
        contentValues.put(EMERG_REPORT_ID, sReportID);
        contentValues.put(EMERG_R_BIZ_ID, bizID);
        contentValues.put(EMERG_R_TIME, reportDate);
        contentValues.put(EMERG_R_TYPE, "Oil Spillage Report");
        contentValues.put(EMERG_R_ADDRESS, address);
        contentValues.put(EMERG_R_TOWN, localityString);
        contentValues.put(EMERG_R_QUESTION, iamAvailable);
        contentValues.put(EMERG_R_LGA, selectedLGA);
        contentValues.put(EMERG_R_MORE_INFO, moreInfo);
        contentValues.put(EMERG_R_COMPANY, selectedOilCompany);
        contentValues.put(EMERG_R_SUBLOCALE, subUrb);
        contentValues.put(EMERG_R_LATLNG, strngLatLng);
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);

    }
    public long insertUserEmergencyReport(int reportID, int profileID, long bizID, String dateOfToday, String selectedType, String stringLatLng, String placeLatLngStrng, String locality, String bgAddress, String address, String country, String safeOption) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMERG_R_PROF_ID, profileID);
        contentValues.put(EMERG_REPORT_ID, reportID);
        contentValues.put(EMERG_R_BIZ_ID, bizID);
        contentValues.put(EMERG_R_TIME, dateOfToday);
        contentValues.put(EMERG_R_SUBLOCALE, locality);
        contentValues.put(EMERG_R_BG_ADDRESS, bgAddress);
        contentValues.put(EMERG_R_TYPE, selectedType);
        contentValues.put(EMERG_R_COUNTRY, country);
        contentValues.put(EMERG_R_ADDRESS, address);
        contentValues.put(EMERG_R_LATLNG, stringLatLng);
        contentValues.put(EMERG_R_QUESTION, safeOption);
        contentValues.put(EMERG_R_PLACE_LATLNG, placeLatLngStrng);
        return sqLiteDatabase.insert(EMERGENCY_REPORT_TABLE, null, contentValues);
    }
    public void deleteLocationReport(int reportID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERG_R_PROF_ID + "=?";
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
        String selection = EMERG_R_COUNTRY + "=?";
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
        String selection = EMERG_R_BIZ_ID + "=?";
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
        String selection = EMERG_R_STATE + "=?";
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
        String selection = EMERG_R_TOWN + "=?";
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
        String selection = EMERG_R_TYPE + "=?";
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
        String selection = EMERG_R_MARKET_ID + "=?";
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
        String selection = EMERG_R_PROF_ID + "=?";
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
        String[] colums = {EMERG_R_STATE};

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

        String selection = EMERG_R_STATE + "=? AND " + EMERG_R_TIME + "=?";
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

        String selection = EMERG_R_STATE + "=? AND " + EMERG_R_TIME + "=?";
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

        String selection = EMERG_R_STATE + "=? AND " + EMERG_R_TIME + "=?";
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
