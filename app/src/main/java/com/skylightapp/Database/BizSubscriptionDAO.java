package com.skylightapp.Database;

import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ADDRESS;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_APPROVER;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_BIZ_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_DATE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_NAME;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_STATUS;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_SUPERADMIN_ID;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_AMOUNT1;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_DATE;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_END_T;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_MODE_OF_P;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_NO_OF_MONTHS;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_PROF_ID;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_START_T;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_STATUS;
import static com.skylightapp.MarketClasses.MarketBizSubScription.MARKET_BIZ_SUB_TABLE;

import static java.lang.String.valueOf;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.MarketClasses.MarketBizSubScription;

import java.util.ArrayList;

public class BizSubscriptionDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = OFFICE_BRANCH_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public BizSubscriptionDAO(Context context) {
        super(context);
    }

    public long insertSubscription(long bizID,double amount,int profileID,int noOfMonths,String subDate,String subEndDate,String modeOfPayment,String status) {
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
    private void getSubscriptionCursor(ArrayList<MarketBizSubScription> bizSubScriptions, Cursor cursor) {
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

            bizSubScriptions.add(new MarketBizSubScription(subID,bizID,marketID, profID,type,amount,currency,noOfMonths,date,startTime,endTime,modeOfPayment,status));
        }
    }
    public ArrayList<MarketBizSubScription> getAllSubscriptions() {
        try {
            ArrayList<MarketBizSubScription> subScriptions = new ArrayList<>();
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

    public ArrayList<MarketBizSubScription> getSubForBusiness(long bizID) {
        ArrayList<MarketBizSubScription> subScriptionArrayList = new ArrayList<>();
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
    public ArrayList<MarketBizSubScription> getSubForProfile(int profID) {
        ArrayList<MarketBizSubScription> subScriptionArrayList = new ArrayList<>();
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
