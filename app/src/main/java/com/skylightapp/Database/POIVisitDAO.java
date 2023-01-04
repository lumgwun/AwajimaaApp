package com.skylightapp.Database;

import static com.skylightapp.MapAndLoc.POI.EMERG_POI_DISTANCE;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_ID;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_LAT;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_LATLNG;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_LNG;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_REPORT_ID;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_STATE;
import static com.skylightapp.MapAndLoc.POI.EMERG_POI_STATUS;
import static com.skylightapp.MapAndLoc.POI.EMER_POI_TABLE;
import static com.skylightapp.MapAndLoc.POIVisit.POI_VISIT_ID;
import static com.skylightapp.MapAndLoc.POIVisit.POI_VISIT_POIID;
import static com.skylightapp.MapAndLoc.POIVisit.POI_VISIT_REPORT_ID;
import static com.skylightapp.MapAndLoc.POIVisit.POI_VISIT_STATE;
import static com.skylightapp.MapAndLoc.POIVisit.POI_VISIT_STATUS;
import static com.skylightapp.MapAndLoc.POIVisit.POI_VISIT_TABLE;
import static java.lang.String.valueOf;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.MapAndLoc.POI;
import com.skylightapp.MapAndLoc.POIVisit;

import java.util.ArrayList;

public class POIVisitDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = POI_VISIT_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public POIVisitDAO(Context context) {
        super(context);
    }
    /*public long insertPOIVisit(int visitID,int poiID,int reportID,double lat,double lng,String latLngStrng,int duration,String state,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMERG_POI_REPORT_ID, visitID);
        values.put(EMERG_POI_REPORT_ID, reportID);
        values.put(EMERG_POI_REPORT_ID, reportID);
        values.put(EMERG_POI_ID, poiID);
        values.put(EMERG_POI_LAT, lat);
        values.put(EMERG_POI_LNG, lng);
        values.put(EMERG_POI_LATLNG, latLngStrng);
        values.put(EMERG_POI_STATUS, status);
        values.put(EMERG_POI_DISTANCE, duration);
        values.put(EMERG_POI_STATE, state);
        return db.insert(POI_VISIT_TABLE, null, values);
    }*/
    private void getPOIVisitCursor(ArrayList<POIVisit> poiVisits, Cursor cursor) {
        while (cursor.moveToNext()) {
            int visitID = cursor.getInt(1);
            int poiID = cursor.getInt(2);
            int reportID = cursor.getInt(3);
            long startTime = cursor.getLong(4);
            long endTime = cursor.getLong(5);
            double poiVisitLat = cursor.getDouble(6);
            double poiVisitLng = cursor.getDouble(7);
            int duration = cursor.getInt(8);
            String poiVisitLatLng = cursor.getString(9);
            String poiVisitState = cursor.getString(10);
            String status = cursor.getString(11);
            poiVisits.add(new POIVisit(visitID,poiID,reportID,startTime, endTime,duration,poiVisitLat,poiVisitLng,poiVisitLatLng,poiVisitState,status));
        }
    }
    public ArrayList<POIVisit> getPOIVisitsForPOI(int poiID) {
        ArrayList<POIVisit> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = POI_VISIT_POIID + "=?";
        String[] selectionArgs = new String[]{valueOf(poiID)};

        Cursor cursor = db.query(POI_VISIT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOIVisitCursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }
    public ArrayList<POIVisit> getPOIVisitsForState(String state) {
        ArrayList<POIVisit> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = POI_VISIT_STATE + "=?";
        String[] selectionArgs = new String[]{valueOf(state)};

        Cursor cursor = db.query(POI_VISIT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOIVisitCursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }


    public ArrayList<POIVisit> getAllPOIVisitsLive(String live) {
        ArrayList<POIVisit> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = POI_VISIT_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(live)};

        Cursor cursor = db.query(POI_VISIT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOIVisitCursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }
    public ArrayList<POIVisit> getAllPOIVisitsForReport(int reportID) {
        ArrayList<POIVisit> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = POI_VISIT_REPORT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(reportID)};

        Cursor cursor = db.query(POI_VISIT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOIVisitCursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }
    public ArrayList<POIVisit> getAllPOIVisits() {
        ArrayList<POIVisit> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(POI_VISIT_TABLE, null, null, null, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getPOIVisitCursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }


}
