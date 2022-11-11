package com.skylightapp.Database;

import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_STATUS;
import static com.skylightapp.Bookings.TripStopPoint.TSP_AMOUNT;
import static com.skylightapp.Bookings.TripStopPoint.TRIP_STOP_POINT_ID;
import static com.skylightapp.Bookings.TripStopPoint.TSP_LATLNG;
import static com.skylightapp.Bookings.TripStopPoint.TSP_NAME;
import static com.skylightapp.Bookings.TripStopPoint.TRIP_STOP_POINT_TABLE;
import static com.skylightapp.Bookings.TripStopPoint.TSP_TRIP_ID;
import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Bookings.TripStopPoint;

import java.util.ArrayList;

public class BoatTripSPDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = TRIP_STOP_POINT_ID
            + " =?";
    public BoatTripSPDAO(Context context) {
        super(context);
    }
    public int getBoatTripSPCount() {
        String countQuery = "SELECT * FROM " + TRIP_STOP_POINT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertBoatTripSP(int tripSpID, int tripID, String nameOfPlace, double amt, String latlngStrng,String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TSP_TRIP_ID, tripID);
        contentValues.put(TRIP_STOP_POINT_ID, tripSpID);
        contentValues.put(TSP_AMOUNT, amt);
        contentValues.put(TSP_NAME, nameOfPlace);
        contentValues.put(TSP_LATLNG, latlngStrng);
        contentValues.put(BOAT_TRIP_STATUS, status);
        return sqLiteDatabase.insert(TRIP_STOP_POINT_TABLE, null, contentValues);

    }
    private void getBoatTripSPFromCursor(ArrayList<TripStopPoint> tripStopPoints, Cursor cursor) {
        while (cursor.moveToNext()) {
            int tripSPId = cursor.getInt(1);
            String name = cursor.getString(3);
            double amount = cursor.getDouble(2);
            String latLngStrng = cursor.getString(4);
            int tripID = cursor.getInt(5);
            String status = cursor.getString(8);
            tripStopPoints.add(new TripStopPoint(tripSPId, name,amount,latLngStrng,tripID, status));
        }

    }
    public ArrayList<TripStopPoint> getBoatTripSPForTrip(int tripID) {
        ArrayList<TripStopPoint> tripStopPoints = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_STOP_POINT_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(tripID)};
        Cursor cursor = db.query(TRIP_STOP_POINT_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getBoatTripSPFromCursor(tripStopPoints, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripStopPoints;
    }
    public ArrayList<TripStopPoint> getAllBoatTripStopPoints() {
        ArrayList<TripStopPoint> stopPointArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TRIP_STOP_POINT_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getBoatTripSPFromCursor(stopPointArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return stopPointArrayList;
    }
    public void deleteBoatTrip(int tripPointID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TRIP_STOP_POINT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tripPointID)};
        db.delete(TRIP_STOP_POINT_TABLE, selection, selectionArgs);

    }
}
