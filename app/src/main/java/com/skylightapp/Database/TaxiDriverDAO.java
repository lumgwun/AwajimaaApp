package com.skylightapp.Database;

import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_AGE;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_CAR_TYPE;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_CURRENT_LATLNG;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_CURRENT_TOWN;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_GENDER;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_ID;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_JOINED_D;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_LICENSE;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_NIN;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_PHONE_NO;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_PIX;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_PROF_ID;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_RATE;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_STATUS;
import static com.skylightapp.MapAndLoc.TaxiDriver.TAXI_DRIVER_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_ACCT_NO;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_FROM_B_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_FROM_PROF_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_START_DATE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_STATUS;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_TITTLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_TYPE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ADDRESS;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_BRANDNAME;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_EMAIL;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_NAME;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_PHONE_NO;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_REG_NO;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_STATE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TYPE;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.ChartData;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.MapAndLoc.TaxiDriver;
import com.skylightapp.MarketClasses.MarketBusiness;

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

    public ArrayList<LatLng> getTaxiDriverFromDistanceM(LatLng bookerLatLng) {
        LatLng taxiLatLng = null;
        double distanceNew=0;
        String unit="M";
        try {
            ArrayList<LatLng> latLngs = new ArrayList<>();
            ArrayList<Float> floatArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            for (int i = 0; i < latLngs.size(); i++) {
                taxiLatLng= latLngs.get(i);


            }
            distanceNew= Utils.taxiDistance(bookerLatLng,taxiLatLng,unit);
            if(distanceNew !=0){
                if(distanceNew<1000){
                    latLngs.add(taxiLatLng);

                }
            }

            Cursor cursor = db.query(TAXI_DRIVER_TABLE, null, TAXI_DRIVER_CURRENT_LATLNG + "=?",
                    new String[]{String.valueOf(bookerLatLng)}, null, null,
                    null, null);
            if (cursor.moveToFirst()) {
                do {
                    floatArrayList.add(cursor.getFloat(14));
                } while (cursor.moveToNext());
            }
            cursor.close();

            latLngs= (ArrayList<LatLng>) floatArrayList.clone();
            return latLngs;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

}
