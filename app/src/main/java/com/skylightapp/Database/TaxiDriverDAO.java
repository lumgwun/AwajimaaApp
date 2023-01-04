package com.skylightapp.Database;

import static com.skylightapp.Bookings.Driver.DRIVER_ID;
import static com.skylightapp.Bookings.Driver.DRIVER_NAME;
import static com.skylightapp.Bookings.Driver.DRIVER_PICTURE;
import static com.skylightapp.Bookings.Driver.DRIVER_POSITION;
import static com.skylightapp.Bookings.Driver.DRIVER_PROF_ID;
import static com.skylightapp.Bookings.Driver.DRIVER_STATUS;
import static com.skylightapp.Bookings.Driver.DRIVER_TABLE;
import static com.skylightapp.Bookings.Driver.DRIVER_VEHICLE;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_AGE;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_CAR_TYPE;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_CURRENT_LATLNG;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_CURRENT_TOWN;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_GENDER;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_ID;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_JOINED_D;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_LICENSE;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_NAME;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_NIN;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_PIX;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_PROF_ID;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_STATUS;
import static com.skylightapp.Bookings.TaxiDriver.TAXI_DRIVER_TABLE;
import static com.skylightapp.Bookings.Trip.A_TRIP_STATE;
import static com.skylightapp.Bookings.Trip.A_TRIP_TYPE;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Bookings.Driver;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Bookings.TaxiDriver;

import java.util.ArrayList;

public class TaxiDriverDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = TAXI_DRIVER_ID
            + " =?";
    public TaxiDriverDAO(Context context) {
        super(context);
    }
    public void deleteDriver(int driverID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TAXI_DRIVER_TABLE,
                    "TAXI_DRIVER_ID = ? ",
                    new String[]{String.valueOf((driverID))});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public long InsertNewDriver(int driverID, int driverProfileID, int age, String gender, String nIN,String license,String carType, Uri pix ,String dateJoined, String status) {
        int driverDBIDID=0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Values.put(TAXI_DRIVER_ID, driverID);
            Values.put(TAXI_DRIVER_PROF_ID, driverProfileID);
            Values.put(TAXI_DRIVER_AGE, age);
            Values.put(TAXI_DRIVER_GENDER, gender);
            Values.put(TAXI_DRIVER_CAR_TYPE, carType);
            Values.put(TAXI_DRIVER_PIX, String.valueOf(pix));
            Values.put(TAXI_DRIVER_NIN, nIN);
            Values.put(TAXI_DRIVER_LICENSE, license);
            Values.put(TAXI_DRIVER_JOINED_D, dateJoined);
            Values.put(TAXI_DRIVER_STATUS, status);
            db.insert(TAXI_DRIVER_TABLE,null,Values);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return driverDBIDID;
    }
    public TaxiDriver getDriverByPosition(LatLng latLng) {
        SQLiteDatabase db = this.getReadableDatabase();

        TaxiDriver driver= new TaxiDriver();
        try (Cursor cursor = db.query(TAXI_DRIVER_TABLE, new String[]{
                        TAXI_DRIVER_ID,
                        TAXI_DRIVER_PROF_ID,
                        TAXI_DRIVER_NAME,
                        TAXI_DRIVER_CAR_TYPE,
                        TAXI_DRIVER_PIX,
                        TAXI_DRIVER_STATUS,
                }, TAXI_DRIVER_CURRENT_LATLNG + "=?", new String[]{String.valueOf(latLng)}, null, null, null, null)) {

            if (cursor != null)
                cursor.moveToFirst();

            driver = null;
            if (cursor != null) {
                driver = new TaxiDriver (Integer.parseInt(cursor.getString(1)),Integer.parseInt(cursor.getString(2)), cursor.getString(3),Uri.parse(cursor.getString(12)),
                        cursor.getString(4), cursor.getString(7), cursor.getString(9));
            }
        }

        return driver;

    }
    private void getDriversCursor(ArrayList<TaxiDriver> taxiDrivers, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int driverID = (cursor.getInt(1));
                int age = cursor.getInt(2);
                int profID = cursor.getInt(3);
                String gender = cursor.getString(4);
                String name = cursor.getString(4);
                String surName = cursor.getString(4);
                String carType = cursor.getString(6);
                String town = cursor.getString(16);
                String latlng = cursor.getString(15);
                String phoneNo = cursor.getString(14);
                String dateJoined = cursor.getString(13);
                String status = cursor.getString(7);
                Uri pix = Uri.parse(cursor.getString(10));
                taxiDrivers.add(new TaxiDriver(driverID, profID,name,surName,age,gender, carType, town, phoneNo,latlng,dateJoined,status,pix));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public ArrayList<TaxiDriver> getDriversFromLatLng(String latLng) {
        try {
            ArrayList<TaxiDriver> driverArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TAXI_DRIVER_TABLE, null, TAXI_DRIVER_CURRENT_LATLNG + "=?",
                    new String[]{latLng}, null, null,
                    null, null);
            getDriversCursor(driverArrayList, cursor);
            cursor.close();
            return driverArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<TaxiDriver> getDriversFromCarType(String carType) {
        try {
            ArrayList<TaxiDriver> driverArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TAXI_DRIVER_TABLE, null, TAXI_DRIVER_CAR_TYPE + "=?",
                    new String[]{carType}, null, null,
                    null, null);
            getDriversCursor(driverArrayList, cursor);
            cursor.close();
            return driverArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllDriversLicenses() {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = TAXI_DRIVER_LICENSE + "=?";
            Cursor res = db.query(TAXI_DRIVER_TABLE,null,selection,null,null,null,null);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(10));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

    }
    public void updateDriverLicense(int driverID,String license) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TAXI_DRIVER_LICENSE, license);
            db.update(TAXI_DRIVER_TABLE, cv, TAXI_DRIVER_ID + "=?", new String[]{valueOf(driverID)});
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void updateDriverLoc(int driverID, String presentTown, LatLng latLngStrng, String status) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TAXI_DRIVER_CURRENT_TOWN, presentTown);
            cv.put(TAXI_DRIVER_STATUS, status);
            cv.put(TAXI_DRIVER_CURRENT_LATLNG, String.valueOf(latLngStrng));
            db.update(TAXI_DRIVER_TABLE, cv, TAXI_DRIVER_ID + "=?", new String[]{valueOf(driverID)});
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public ArrayList<TaxiDriver> getAllTaxiDrivers() {
        try {
            ArrayList<TaxiDriver> taxiDrivers = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TAXI_DRIVER_TABLE, null, null, null, TAXI_DRIVER_CURRENT_TOWN,
                    null, null);

            getDriversCursor(taxiDrivers, cursor);

            cursor.close();
            //db.close();

            return taxiDrivers;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<TaxiDriver> getTaxiDriverFromTown(String town) {
        try {
            ArrayList<TaxiDriver> taxiDrivers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(TAXI_DRIVER_TABLE, null, TAXI_DRIVER_CURRENT_TOWN + "=?",
                    new String[]{String.valueOf(town)}, null, null,
                    null, null);
            getDriversCursor(taxiDrivers, cursor);
            cursor.close();
            return taxiDrivers;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<TaxiDriver> getTaxiDriverFromDistanceM(LatLng bookerLatLng,String online) {
        TaxiDriver taxiDriver = null;
        double distanceNew=0;
        String unit="M";
        ArrayList<LatLng> latLngs = new ArrayList<>();
        LatLng taxiLatLng=null;
        try {
            ArrayList<TaxiDriver> taxiDrivers = new ArrayList<>();
            ArrayList<Float> floatArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = TAXI_DRIVER_CURRENT_LATLNG + "=?AND " + TAXI_DRIVER_STATUS + "=?";
            String[] selectionArgs = new String[]{String.valueOf(bookerLatLng),online};
            for (int i = 0; i < taxiDrivers.size(); i++) {
                taxiDriver= taxiDrivers.get(i);

            }
            if(taxiDriver !=null){
                taxiLatLng=taxiDriver.getDriverLatLng();
            }
            distanceNew= Utils.taxiDistance(bookerLatLng,taxiLatLng,unit);
            if(distanceNew<1000){
                taxiDrivers.add(taxiDriver);

            }

            Cursor cursor = db.query(TAXI_DRIVER_TABLE, null, selection, selectionArgs, null, null,
                    null, null);
            if (cursor.moveToFirst()) {
                do {
                    floatArrayList.add(cursor.getFloat(14));
                } while (cursor.moveToNext());
            }
            cursor.close();

            taxiDrivers= (ArrayList<TaxiDriver>) floatArrayList.clone();
            return taxiDrivers;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
