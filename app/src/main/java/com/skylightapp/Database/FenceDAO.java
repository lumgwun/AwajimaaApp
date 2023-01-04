package com.skylightapp.Database;

import static com.skylightapp.Classes.CustomerDailyReport.DAILY_REPORT_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT_REMAINING;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_CUS_ID_FK;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DATE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DAYS_REMAINING;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_NUMBER_OF_DAYS;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_TOTAL;
import static com.skylightapp.MapAndLoc.Fence.FENCE_CENTER;
import static com.skylightapp.MapAndLoc.Fence.FENCE_DATE_CREATED;
import static com.skylightapp.MapAndLoc.Fence.FENCE_EMERG_ID;
import static com.skylightapp.MapAndLoc.Fence.FENCE_ID;
import static com.skylightapp.MapAndLoc.Fence.FENCE_NAME;
import static com.skylightapp.MapAndLoc.Fence.FENCE_RADIUS;
import static com.skylightapp.MapAndLoc.Fence.FENCE_STATE;
import static com.skylightapp.MapAndLoc.Fence.FENCE_STATUS;
import static com.skylightapp.MapAndLoc.Fence.FENCE_TABLE;
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

import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.MapAndLoc.Fence;
import com.skylightapp.MapAndLoc.FenceEvent;

import java.util.ArrayList;

public class FenceDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = FENCE_ID
            + " =?";
    public FenceDAO(Context context) {
        super(context);
    }


    public int getFenceCount() {
        String countQuery = "SELECT * FROM " + FENCE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertFence(int emergReportID, String fenceName, String centerLatLng,float radius, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FENCE_EMERG_ID, emergReportID);
        contentValues.put(FENCE_NAME, fenceName);
        contentValues.put(FENCE_CENTER, centerLatLng);
        contentValues.put(FENCE_RADIUS, radius);
        contentValues.put(FENCE_STATUS, status);
        return sqLiteDatabase.insert(FENCE_TABLE, null, contentValues);

    }

    public void deleteFence(int fenceID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = FENCE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(fenceID)};
        db.delete(FENCE_TABLE, selection, selectionArgs);

    }

    private void getFenceFromCursor(ArrayList<Fence> fences, Cursor cursor) {
        while (cursor.moveToNext()) {

            int fenceId = cursor.getInt(0);
            int emergID = cursor.getInt(1);
            String name = cursor.getString(2);
            String center = cursor.getString(3);
            float radius = cursor.getFloat(4);
            String status = cursor.getString(5);
            String dateCreated = cursor.getString(6);

            //fences.add(new Fence(fenceId, emergID,name,center, radius, status,dateCreated));
        }


    }
    public ArrayList<Fence> getFencesCreatedTodayForState(String state, String todayDate) {

        ArrayList<Fence> fenceArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = FENCE_STATE + "=? AND " + FENCE_DATE_CREATED + "=?";
        String[] selectionArgs = new String[]{valueOf(state), valueOf(todayDate)};

        Cursor cursor = db.query(FENCE_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getFenceFromCursor(fenceArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return fenceArrayList;
    }

    public ArrayList<Fence> getFenceForEmerg(int emergID) {
        ArrayList<Fence> eventArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = FENCE_EMERG_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(emergID)};

        Cursor cursor = db.query(FENCE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getFenceFromCursor(eventArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return eventArrayList;
    }
    public ArrayList<Fence> getAllFences() {
        ArrayList<Fence> reportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(FENCE_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getFenceFromCursor(reportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return reportArrayList;
    }

}
