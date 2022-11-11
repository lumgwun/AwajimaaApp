package com.skylightapp.Database;

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
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_DEVICE_ID;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_EMERG_ID;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_EMERG_TYPE;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_ID;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_PLACE_ID;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_PROF_NAME;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_TABLE;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_TIME;
import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.MapAndLoc.FenceEvent;

import java.util.ArrayList;

public class FenceEventDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = FENCE_EVENT_ID
            + " =?";
    public FenceEventDAO(Context context) {
        super(context);
    }


    public int getFenceEventCount() {
        String countQuery = "SELECT * FROM " + FENCE_EVENT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertFenceEvent(int fenceEventID, int emergReportID, String emergType, String time,int deviceID, String placeID,String profName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FENCE_EVENT_ID, fenceEventID);
        contentValues.put(FENCE_EVENT_EMERG_ID, emergReportID);
        contentValues.put(FENCE_EVENT_EMERG_TYPE, emergType);
        contentValues.put(FENCE_EVENT_TIME, time);
        contentValues.put(FENCE_EVENT_DEVICE_ID, deviceID);
        contentValues.put(FENCE_EVENT_PLACE_ID, placeID);
        contentValues.put(FENCE_EVENT_PROF_NAME, profName);
        return sqLiteDatabase.insert(FENCE_EVENT_TABLE, null, contentValues);

    }

    public void deleteFenceEvent(int eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = FENCE_EVENT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(eventID)};
        db.delete(FENCE_EVENT_TABLE, selection, selectionArgs);

    }

    private void getFenceEventFromCursor(ArrayList<FenceEvent> fenceEventArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int fenceEventId = cursor.getInt(1);
            int emergID = cursor.getInt(2);
            String type = cursor.getString(3);
            String reportTime = cursor.getString(4);
            int deviceID = cursor.getInt(5);
            String status = cursor.getString(7);
            String profName = cursor.getString(8);

            fenceEventArrayList.add(new FenceEvent(fenceEventId, emergID,deviceID,reportTime, type, profName,status));
        }


    }

    public ArrayList<FenceEvent> getFenceEventsForEmerg(int emergID) {
        ArrayList<FenceEvent> eventArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = FENCE_EVENT_EMERG_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(emergID)};

        Cursor cursor = db.query(FENCE_EVENT_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getFenceEventFromCursor(eventArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return eventArrayList;
    }
    public ArrayList<FenceEvent> getAllFenceEvents() {
        ArrayList<FenceEvent> reportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(FENCE_EVENT_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getFenceEventFromCursor(reportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return reportArrayList;
    }


}
