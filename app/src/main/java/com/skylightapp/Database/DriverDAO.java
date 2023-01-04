package com.skylightapp.Database;



import static com.skylightapp.Bookings.Driver.DRIVER_ID;
import static com.skylightapp.Bookings.Driver.DRIVER_JOINED_D;
import static com.skylightapp.Bookings.Driver.DRIVER_NAME;
import static com.skylightapp.Bookings.Driver.DRIVER_PICTURE;
import static com.skylightapp.Bookings.Driver.DRIVER_POSITION;
import static com.skylightapp.Bookings.Driver.DRIVER_PROF_ID;
import static com.skylightapp.Bookings.Driver.DRIVER_STATUS;
import static com.skylightapp.Bookings.Driver.DRIVER_TABLE;
import static com.skylightapp.Bookings.Driver.DRIVER_VEHICLE;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Bookings.Driver;
import com.skylightapp.Bookings.TaxiDriver;

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
    public long insertDriver(String driverID, int profID,String driverName, String driverVehicle,String dateJoined, Uri picture,String status,LatLng position) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DRIVER_ID, driverID);
        contentValues.put(DRIVER_PROF_ID, profID);
        contentValues.put(DRIVER_NAME, driverName);
        contentValues.put(DRIVER_VEHICLE, driverVehicle);
        contentValues.put(DRIVER_JOINED_D, dateJoined);
        contentValues.put(DRIVER_POSITION, String.valueOf(position));
        contentValues.put(DRIVER_PICTURE, String.valueOf(picture));
        contentValues.put(DRIVER_STATUS, status);
        return sqLiteDatabase.insert(DRIVER_TABLE, null, contentValues);

    }

    public Driver getDriverByPosition(TaxiDriver latLng) {
        SQLiteDatabase db = this.getReadableDatabase();

        Driver driver= new Driver();
        try (Cursor cursor = db.query(DRIVER_TABLE, new String[]{
                        DRIVER_ID,
                        DRIVER_PROF_ID,
                        DRIVER_NAME,
                        DRIVER_VEHICLE,
                        DRIVER_PICTURE,
                        DRIVER_STATUS,
                }, DRIVER_POSITION + "=?",

                new String[]{String.valueOf(latLng)}, null, null, null, null)) {

            if (cursor != null)
                cursor.moveToFirst();

            driver = null;
            if (cursor != null) {
                driver = new Driver(Integer.parseInt(cursor.getString(1)),Integer.parseInt(cursor.getString(2)), cursor.getString(3),Uri.parse(cursor.getString(12)),
                        cursor.getString(4), cursor.getString(7), cursor.getString(9));
            }
        }

        return driver;

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
    public ArrayList<Driver> getDriversByDateJoined(String date) {
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
