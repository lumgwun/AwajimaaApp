package com.skylightapp.Database;



import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_COUNTRY;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_FROM;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_ID;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_STATUS;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_TABLE;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_THROUGH;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_TO;
import static com.skylightapp.Bookings.Driver.DRIVER_ID;
import static com.skylightapp.Bookings.Driver.DRIVER_JOINED_D;
import static com.skylightapp.Bookings.Driver.DRIVER_NAME;
import static com.skylightapp.Bookings.Driver.DRIVER_PICTURE;
import static com.skylightapp.Bookings.Driver.DRIVER_PROF_ID;
import static com.skylightapp.Bookings.Driver.DRIVER_STATUS;
import static com.skylightapp.Bookings.Driver.DRIVER_TABLE;
import static com.skylightapp.Bookings.Driver.DRIVER_VEHICLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Bookings.BoatTripRoute;
import com.skylightapp.Bookings.Driver;
import com.skylightapp.MapAndLoc.EmergencyReport;

import java.util.ArrayList;

public class DriverDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = DRIVER_ID
            + " =?";
    public DriverDAO(Context context) {
        super(context);
    }
    public int getBoatTripRouteCount() {
        String countQuery = "SELECT * FROM " + DRIVER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertDriver(String driverID, int profID,String from, String to,String through, Uri picture,String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DRIVER_ID, driverID);
        contentValues.put(DRIVER_PROF_ID, profID);
        contentValues.put(DRIVER_NAME, from);
        contentValues.put(DRIVER_VEHICLE, to);
        contentValues.put(DRIVER_JOINED_D, through);
        contentValues.put(DRIVER_PICTURE, String.valueOf(picture));
        contentValues.put(DRIVER_STATUS, status);
        return sqLiteDatabase.insert(DRIVER_TABLE, null, contentValues);

    }
    public void deleteDriver(String driverID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DRIVER_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(driverID)};
        db.delete(DRIVER_TABLE, selection, selectionArgs);

    }
    private void getDriverFromCursor(ArrayList<Driver> driverArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            String driverId = cursor.getString(1);
            String name = cursor.getString(3);
            String vehicle = cursor.getString(4);
            int profID = cursor.getInt(2);
            String status = cursor.getString(9);
            String dateJoined = cursor.getString(7);
            Uri picture = Uri.parse(cursor.getString(12));
            driverArrayList.add(new Driver(driverId,profID, name,picture,vehicle,dateJoined, status));
        }

    }
    public ArrayList<Driver> getDriverByDateJoined(String date) {
        ArrayList<Driver> driverArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DRIVER_JOINED_D + "=?";
        String[] selectionArgs = new String[]{date};

        Cursor cursor = db.query(DRIVER_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDriverFromCursor(driverArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return driverArrayList;
    }
    public ArrayList<Driver> getAllDrivers() {
        ArrayList<Driver> driverArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DRIVER_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getDriverFromCursor(driverArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return driverArrayList;
    }
}
