package com.skylightapp.Database;

import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_AMOUNT1;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_COUNTRY;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_CURRENCY;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_CUS_ID;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_DATE;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_END_T;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_ID;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_MARKET_ID22;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_MODE_OF_P;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_NAME;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_NO_OF_MONTHS;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_OFFICE;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_PROF_ID;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_START_T;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_STATE;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_STATUS;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_TABLE;
import static com.skylightapp.MarketClasses.MarketBizSub.MARKET_BIZ_SUB_TYPE;

import static java.lang.String.valueOf;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.MarketClasses.MarketBizSub;

import java.util.ArrayList;

public class BizSubscriptionDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = OFFICE_BRANCH_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public BizSubscriptionDAO(Context context) {
        super(context);
    }

    public long insertSubscription(long bizID,double amount,int profileID,int noOfMonths,String subDate,String subEndDate,String modeOfPayment,String subType,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MARKET_BIZ_SUB_BIZ_ID, bizID);
        values.put(MARKET_BIZ_SUB_DATE, subDate);
        values.put(MARKET_BIZ_SUB_AMOUNT1, amount);
        values.put(MARKET_BIZ_SUB_START_T, subDate);
        values.put(MARKET_BIZ_SUB_END_T, subEndDate);
        values.put(MARKET_BIZ_SUB_NO_OF_MONTHS, noOfMonths);
        values.put(MARKET_BIZ_SUB_STATUS, status);
        values.put(MARKET_BIZ_SUB_PROF_ID, profileID);
        values.put(MARKET_BIZ_SUB_MODE_OF_P, modeOfPayment);
        return db.insert(MARKET_BIZ_SUB_TABLE, null, values);
    }
    public long insertSubscription(int i, long amount, int bundleProfID, int bundleCusID, int noOfMonths, String subDate, String subEndDate, String modeOfPayment, String paymentFor, String status,String currency,String state,String country,String name,String office) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MARKET_BIZ_SUB_CUS_ID, bundleCusID);
        values.put(MARKET_BIZ_SUB_DATE, subDate);
        values.put(MARKET_BIZ_SUB_TYPE, paymentFor);
        values.put(MARKET_BIZ_SUB_CURRENCY, currency);
        values.put(MARKET_BIZ_SUB_STATE, state);
        values.put(MARKET_BIZ_SUB_COUNTRY, country);
        values.put(MARKET_BIZ_SUB_NAME, name);
        values.put(MARKET_BIZ_SUB_OFFICE, office);

        values.put(MARKET_BIZ_SUB_AMOUNT1, amount);
        values.put(MARKET_BIZ_SUB_START_T, subDate);
        values.put(MARKET_BIZ_SUB_END_T, subEndDate);
        values.put(MARKET_BIZ_SUB_NO_OF_MONTHS, noOfMonths);
        values.put(MARKET_BIZ_SUB_STATUS, status);
        values.put(MARKET_BIZ_SUB_PROF_ID, bundleProfID);
        values.put(MARKET_BIZ_SUB_MODE_OF_P, modeOfPayment);
        return db.insert(MARKET_BIZ_SUB_TABLE, null, values);
    }

    public long insertSubscription(int subID,long bizID, int marketID, long amount, int bundleProfID, int bundleCusID, int noOfMonths, String subDate, String subEndDate, String modeOfPayment, String paymentFor, String status, String state, String office, String country, String bookingName, String currency) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MARKET_BIZ_SUB_ID, subID);
        values.put(MARKET_BIZ_SUB_BIZ_ID, bizID);
        //values.put(MARKET_BIZ_SUB_BIZ_ID, bizID);
        values.put(MARKET_BIZ_SUB_MARKET_ID22, marketID);

        values.put(MARKET_BIZ_SUB_DATE, subDate);
        values.put(MARKET_BIZ_SUB_AMOUNT1, amount);
        values.put(MARKET_BIZ_SUB_START_T, subDate);
        values.put(MARKET_BIZ_SUB_END_T, subEndDate);
        values.put(MARKET_BIZ_SUB_NO_OF_MONTHS, noOfMonths);
        values.put(MARKET_BIZ_SUB_STATUS, status);
        values.put(MARKET_BIZ_SUB_PROF_ID, bundleProfID);
        values.put(MARKET_BIZ_SUB_MODE_OF_P, modeOfPayment);
        values.put(MARKET_BIZ_SUB_CUS_ID, bundleCusID);
        values.put(MARKET_BIZ_SUB_TYPE, paymentFor);
        values.put(MARKET_BIZ_SUB_STATE, state);
        values.put(MARKET_BIZ_SUB_OFFICE, office);
        values.put(MARKET_BIZ_SUB_COUNTRY, country);
        values.put(MARKET_BIZ_SUB_NAME, bookingName);
        values.put(MARKET_BIZ_SUB_CURRENCY, currency);
        return db.insert(MARKET_BIZ_SUB_TABLE, null, values);
    }
    private void getSubscriptionCursor(ArrayList<MarketBizSub> bizSubScriptions, Cursor cursor) {
        while (cursor.moveToNext()) {

            int subID = cursor.getInt(0);
            long bizID = cursor.getInt(1);
            int marketID = cursor.getInt(2);
            String date = cursor.getString(3);
            String type = cursor.getString(4);
            double amount = cursor.getDouble(5);
            String currency = cursor.getString(6);
            String status = cursor.getString(7);
            String startTime = cursor.getString(8);
            String endTime = cursor.getString(9);
            String modeOfPayment = cursor.getString(10);
            int noOfMonths = cursor.getInt(11);
            int profID = cursor.getInt(12);

            bizSubScriptions.add(new MarketBizSub(subID,bizID,marketID, profID,type,amount,currency,noOfMonths,date,startTime,endTime,modeOfPayment,status));
        }
    }
    public ArrayList<MarketBizSub> getAllSubscriptions() {
        try {
            ArrayList<MarketBizSub> subScriptions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(MARKET_BIZ_SUB_TABLE, null, null, null, null,
                    null, null);
            if (null != cursor)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getSubscriptionCursor(subScriptions, cursor);

                }
            if (cursor != null) {
                cursor.close();
            }
            db.close();
            return subScriptions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<MarketBizSub> getSubForBusiness(long bizID) {
        ArrayList<MarketBizSub> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = MARKET_BIZ_SUB_BIZ_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID)};

        Cursor cursor = db.query(MARKET_BIZ_SUB_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getSubscriptionCursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }
    public ArrayList<MarketBizSub> getSubForProfile(int profID) {
        ArrayList<MarketBizSub> subScriptionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = MARKET_BIZ_SUB_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profID)};

        Cursor cursor = db.query(MARKET_BIZ_SUB_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
                do {
                    getSubscriptionCursor(subScriptionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return subScriptionArrayList;
    }



}
