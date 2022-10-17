package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Classes.Account;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Classes.Profile;

import java.util.ArrayList;

import static com.skylightapp.Classes.AppCash.APP_CASH_DATE;
import static com.skylightapp.Classes.AppCash.APP_CASH_TO;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ADDRESS;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_BRANDNAME;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_CONTINENT;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_COUNTRY;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_EMAIL;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_NAME;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_PHONE_NO;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_PIX;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_PROF_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_REG_NO;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_STATE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_STATUS;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TYPE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static java.lang.String.valueOf;

public class MarketBizDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = MARKET_BIZ_ID
            + " =?";
    public MarketBizDAO(Context context) {
        super(context);
    }
    public void deleteBusiness(long businessID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(MARKET_BIZ_TABLE,
                    "BIZ_ID = ? ",
                    new String[]{String.valueOf((businessID))});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    public long saveBiz(MarketBusiness marketBusiness) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Profile profile= null;
            if(marketBusiness !=null){
                Values.put(MARKET_BIZ_ID, marketBusiness.getBusinessID());
                Values.put(MARKET_BIZ_NAME, valueOf(marketBusiness.getBizBrandname()));
                Values.put(MARKET_BIZ_BRANDNAME, marketBusiness.getBizBrandname());
                Values.put(MARKET_BIZ_TYPE, marketBusiness.getBizType());
                Values.put(MARKET_BIZ_REG_NO, marketBusiness.getBizRegNo());
                Values.put(MARKET_BIZ_EMAIL, marketBusiness.getBizEmail());
                Values.put(MARKET_BIZ_PHONE_NO, marketBusiness.getBizPhoneNo());
                Values.put(MARKET_BIZ_ADDRESS, marketBusiness.getBizAddress());
                Values.put(MARKET_BIZ_STATE, marketBusiness.getBizState());
                Values.put(MARKET_BIZ_PIX, String.valueOf(marketBusiness.getBizPicture()));
                Values.put(MARKET_BIZ_STATUS, marketBusiness.getBizStatus());
                profile = marketBusiness.getmBusOwner();

            }

            if (profile != null) {
                Values.put(MARKET_BIZ_PROF_ID, profile.getPID());
            }

            db.insert(MARKET_BIZ_TABLE,null,Values);
            //long id = db.insert(BIZ_TABLE, null, codeValues);
            //paymentCode.setUID(String.valueOf(id));
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<MarketBusiness> getBusinessesFromProfile(long profileID) {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(MARKET_BIZ_TABLE, null, MARKET_BIZ_PROF_ID + "=?",
                    new String[]{String.valueOf(profileID)}, null, null,
                    null, null);
            getBiZsFromCursor(marketBusinesses, cursor);
            cursor.close();
            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllBizNames() {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.query(MARKET_BIZ_TABLE,null,null,null,null,null,null);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(7));
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





    public ArrayList<MarketBusiness> getAllBusinesses() {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(MARKET_BIZ_TABLE, null, null, null, MARKET_BIZ_TYPE,
                    null, null);

            getBiZsFromCursor(marketBusinesses, cursor);

            cursor.close();
            //db.close();

            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<MarketBusiness> getAllBusinessesForProfile(int profID) {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MARKET_BIZ_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(profID)};
            Cursor cursor = db.query(MARKET_BIZ_TABLE, null, selection, selectionArgs, MARKET_BIZ_TYPE,
                    null, null);

            getBiZsFromCursor(marketBusinesses, cursor);

            cursor.close();
            //db.close();

            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<MarketBusiness> getAllBusinessesForState(String state) {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MARKET_BIZ_STATE + "=?";
            String[] selectionArgs = new String[]{state};
            Cursor cursor = db.query(MARKET_BIZ_TABLE, null, selection, selectionArgs, MARKET_BIZ_TYPE,
                    null, null);

            getBiZsFromCursor(marketBusinesses, cursor);

            cursor.close();
            //db.close();

            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<MarketBusiness> getAllBusinessesForCountry(String country) {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MARKET_BIZ_COUNTRY + "=?";
            String[] selectionArgs = new String[]{country};
            Cursor cursor = db.query(MARKET_BIZ_TABLE, null, selection, selectionArgs, MARKET_BIZ_TYPE,
                    null, null);

            getBiZsFromCursor(marketBusinesses, cursor);

            cursor.close();
            //db.close();

            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<MarketBusiness> getAllBusinessesForContinent(String continent) {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MARKET_BIZ_CONTINENT + "=?";
            String[] selectionArgs = new String[]{continent};
            Cursor cursor = db.query(MARKET_BIZ_TABLE, null, selection, selectionArgs, MARKET_BIZ_TYPE,
                    null, null);

            getBiZsFromCursor(marketBusinesses, cursor);

            cursor.close();
            //db.close();

            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<MarketBusiness> getAllBusFromStateAndStatus(String state,String status) {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MARKET_BIZ_STATE + "=? AND " + MARKET_BIZ_STATUS + "=?";
            String[] selectionArgs = new String[]{valueOf(state), valueOf(status)};
            Cursor cursor = db.query(MARKET_BIZ_TABLE, null, selection, selectionArgs, MARKET_BIZ_TYPE,
                    null, null);

            getBiZsFromCursor(marketBusinesses, cursor);

            cursor.close();
            //db.close();

            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<MarketBusiness> getAllBusFromStateAndType(String state,String type) {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = MARKET_BIZ_STATE + "=? AND " + MARKET_BIZ_TYPE + "=?";
            String[] selectionArgs = new String[]{valueOf(state), valueOf(type)};
            Cursor cursor = db.query(MARKET_BIZ_TABLE, null, selection, selectionArgs, MARKET_BIZ_TYPE,
                    null, null);

            getBiZsFromCursor(marketBusinesses, cursor);

            cursor.close();
            //db.close();

            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private void getBiZsFromCursor(ArrayList<MarketBusiness> marketBusinesses, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                long bizID = (cursor.getLong(0));
                String name = cursor.getString(1);
                String brandName = cursor.getString(2);
                String type = cursor.getString(3);
                String regNo = cursor.getString(4);
                String email = cursor.getString(5);
                String phoneNo = cursor.getString(6);
                String address = cursor.getString(7);
                String state = cursor.getString(8);
                String status = cursor.getString(9);
                Uri logo = Uri.parse(cursor.getString(10));
                marketBusinesses.add(new MarketBusiness(bizID, name,brandName,type, regNo, email, phoneNo,address,state,status,logo));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void updateBusiness(long biZID,long profileID, String name,String brandName,String typeBiz,String bizEmail,String bizAddress, String ph_no,String state,String regNo) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(MARKET_BIZ_PROF_ID, profileID);
            cv.put(MARKET_BIZ_ID, biZID);
            cv.put(MARKET_BIZ_NAME, name);
            cv.put(MARKET_BIZ_BRANDNAME, brandName);
            cv.put(MARKET_BIZ_TYPE, typeBiz);
            cv.put(MARKET_BIZ_EMAIL, bizEmail);
            cv.put(MARKET_BIZ_ADDRESS, bizAddress);
            cv.put(MARKET_BIZ_PHONE_NO, ph_no);
            cv.put(MARKET_BIZ_STATE, state);
            cv.put(MARKET_BIZ_REG_NO, regNo);
            db.update(MARKET_BIZ_TABLE, cv, MARKET_BIZ_PROF_ID + "=?", new String[]{valueOf(profileID)});
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }



}
