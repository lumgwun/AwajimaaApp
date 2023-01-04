package com.skylightapp.Database;

import static com.skylightapp.Bookings.Driver.DRIVER_ID;
import static com.skylightapp.Bookings.Driver.DRIVER_JOINED_D;
import static com.skylightapp.Bookings.Driver.DRIVER_STATUS;
import static com.skylightapp.Bookings.Driver.DRIVER_TABLE;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_DEST_LAT;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_DEST_LNG;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_DURATION;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_ID;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_MODE;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_ORIGIN_LAT;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_ORIGIN_LNG;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_ROUTING;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_STATE;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_TABLE;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_TEXT;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_TIME;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_UNIT;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_VALUE;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Bookings.Driver;
import com.skylightapp.MapAndLoc.Distance;
import com.skylightapp.MapAndLoc.Region;

import java.util.ArrayList;

public class DistancesDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = DRIVER_ID
            + " =?";
    public DistancesDAO(Context context) {
        super(context);
    }
    public long insertDistance(int distanceID, int time,  int distance,int duration,String text,String mode,String routing, double takeOffLat, double takeOffLng,String unit,double desLat,double destLng, String state,String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DISTANCE_ID, distanceID);
        contentValues.put(DISTANCE_TIME, time);
        contentValues.put(DISTANCE_ORIGIN_LAT, takeOffLat);
        contentValues.put(DISTANCE_ORIGIN_LNG, takeOffLng);
        contentValues.put(DISTANCE_UNIT, unit);
        contentValues.put(DISTANCE_DEST_LAT, desLat);
        contentValues.put(DISTANCE_DEST_LNG, destLng);
        contentValues.put(DISTANCE_VALUE, distance);
        contentValues.put(DISTANCE_DURATION, duration);
        contentValues.put(DISTANCE_STATE, state);
        contentValues.put(DISTANCE_TEXT, text);
        contentValues.put(DISTANCE_ROUTING, routing);
        contentValues.put(DISTANCE_MODE, mode);
        contentValues.put(DRIVER_STATUS, status);
        return sqLiteDatabase.insert(DISTANCE_TABLE, null, contentValues);

    }
    public void deleteDistance(String distanceID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DRIVER_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(distanceID)};
        db.delete(DRIVER_TABLE, selection, selectionArgs);

    }
    private void getDistanceFromCursor(ArrayList<Distance> arrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int distanceID = cursor.getInt(2);
            long time = cursor.getLong(2);
            double startLat = cursor.getDouble(3);
            double startLng = cursor.getDouble(4);
            double endLat = cursor.getDouble(5);
            double endLng = cursor.getDouble(6);
            int distance = cursor.getInt(7);
            int duration = cursor.getInt(8);
            String text = cursor.getString(9);
            String mode = cursor.getString(10);
            String unit = cursor.getString(11);
            String routing = cursor.getString(12);
            String language = cursor.getString(13);
            String status = cursor.getString(14);
            String type = cursor.getString(15);
            String state = cursor.getString(16);

            arrayList.add(new Distance(distanceID,type,distance,unit,time,startLat,startLng ,endLat,endLng,state,mode,text,duration,routing,language,status));
        }

    }

    public ArrayList<Distance> getReportRegions(int distanceID) {
        ArrayList<Distance> driverArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DISTANCE_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(distanceID)};

        Cursor cursor = db.query(DRIVER_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDistanceFromCursor(driverArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return driverArrayList;

    }


    public ArrayList<Distance> getAllStateDistances(String state) {
        ArrayList<Distance> driverArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DISTANCE_STATE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(state)};

        Cursor cursor = db.query(DRIVER_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDistanceFromCursor(driverArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return driverArrayList;
    }
}
