package com.skylightapp.Database;


import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_DATE;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_OFFICE;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_DISTANCE;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_ID;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_LAT;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_LATLNG;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_LNG;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_REPORT_ID;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_STATE;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_STATUS;
import static com.skylightapp.MapAndLoc.POI.EMER_POI_TABLE;


import static java.lang.String.valueOf;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.MapAndLoc.POI;
import com.skylightapp.MarketClasses.MarketBizSub;

import java.util.ArrayList;

public class POIDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = EMERG_POI_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public POIDAO(Context context) {
        super(context);
    }
    public long insertPOI(int reportID,int poiID,double lat,double lng,String latLngStrng,int distance,String state,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMERG_POI_REPORT_ID, reportID);
        values.put(EMERG_POI_ID, poiID);
        values.put(EMERG_POI_LAT, lat);
        values.put(EMERG_POI_LNG, lng);
        values.put(EMERG_POI_LATLNG, latLngStrng);
        values.put(EMERG_POI_STATUS, status);
        values.put(EMERG_POI_DISTANCE, distance);
        values.put(EMERG_POI_STATE, state);
        return db.insert(EMER_POI_TABLE, null, values);
    }
    private void getPOICursor(ArrayList<POI> poiArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int poiID = cursor.getInt(2);
            int reportID = cursor.getInt(1);
            double poiLat = cursor.getDouble(3);
            double poiLng = cursor.getDouble(4);
            int distance = cursor.getInt(5);
            String status = cursor.getString(6);
            String poiLatLng = cursor.getString(7);
            String poiState = cursor.getString(8);
            poiArrayList.add(new POI(poiID,reportID,poiLat, poiLng,poiLatLng,status,distance,status,poiState));
        }
    }
    public ArrayList<POI> getPOIForReport(int reportID) {
        ArrayList<POI> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERG_POI_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(reportID)};

        Cursor cursor = db.query(EMER_POI_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOICursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }
    public ArrayList<POI> getPOILiveReports(String live) {
        ArrayList<POI> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERG_POI_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(live)};

        Cursor cursor = db.query(EMER_POI_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOICursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }
    public ArrayList<POI> getPOIForReportLive(int reportID, String live) {
        ArrayList<POI> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERG_POI_ID + "=? AND " + EMERG_POI_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(reportID), valueOf(live)};

        Cursor cursor = db.query(EMER_POI_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOICursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }

    public ArrayList<POI> getPOIForState(String state) {
        ArrayList<POI> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERG_POI_STATE + "=?";
        String[] selectionArgs = new String[]{valueOf(state)};

        Cursor cursor = db.query(EMER_POI_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOICursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }
    public ArrayList<POI> getPOIForStateLive(String state, String live) {
        ArrayList<POI> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = EMERG_POI_STATE + "=? AND " + EMERG_POI_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(state), valueOf(live)};

        Cursor cursor = db.query(EMER_POI_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOICursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }

}
