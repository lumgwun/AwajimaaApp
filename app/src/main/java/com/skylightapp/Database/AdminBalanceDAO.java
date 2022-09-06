package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.SuperAdmin.AppCommission;

import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.Bookings.BOOKING_ID;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_ACCT_ID;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_CUS_ID;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_DATE;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_PACKID;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_PROFILE_ID;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_STATUS;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_BALANCE_TABLE;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_EXPECTED_BALANCE;
import static com.skylightapp.SuperAdmin.AppCommission.ADMIN_RECEIVED_AMOUNT;

public class AdminBalanceDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = BOOKING_ID
            + " =?";
    public AdminBalanceDAO(Context context) {
        super(context);
    }
    public List<AppCommission> getAllAdminBalances() {
        List<AppCommission> appCharges = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {

            if (cursor.moveToFirst()) {
                do {
                    AppCommission appCommission = new AppCommission();
                    appCommission.setAdminBalanceID(Integer.parseInt(cursor.getString(0)));
                    appCommission.setAdminExpectedBalance(Double.parseDouble(cursor.getString(1)));
                    appCommission.setAdminExpectedBalance(Double.parseDouble(cursor.getString(1)));
                    appCommission.setAdminReceivedBalance(Double.parseDouble(cursor.getString(2)));
                    appCommission.setAdminBalanceDate(cursor.getString(4));

                    appCharges.add(appCommission);
                } while (cursor.moveToNext());
            }
        }
        return appCharges;

    }
    public long saveNewAdminBalance(int acctID, int profileID, int customerID, int packageID, double savingsAmount, String reportDate, String unconfirmed) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADMIN_RECEIVED_AMOUNT, savingsAmount);
        contentValues.put(ADMIN_BALANCE_DATE, reportDate);
        contentValues.put(ADMIN_BALANCE_PROFILE_ID, profileID);
        contentValues.put(ADMIN_BALANCE_CUS_ID, customerID);
        contentValues.put(ADMIN_BALANCE_PACKID, packageID);
        contentValues.put(ADMIN_BALANCE_ACCT_ID, acctID);
        contentValues.put(ADMIN_BALANCE_STATUS, unconfirmed);
        return sqLiteDatabase.insert(ADMIN_BALANCE_TABLE, null, contentValues);
    }
    public double getAdminExpectedBalance() {
        AppCommission appCommission = new AppCommission();
        String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        //String[] whereArgs = new String[]{searchString};


        try (Cursor cursor = db.rawQuery("SELECT SUM(" + ADMIN_EXPECTED_BALANCE + ") as Total FROM " + ADMIN_BALANCE_TABLE, null)) {

            if (cursor.moveToFirst()) {

                //int total = cursor.getInt(cursor.getColumnIndex(ADMIN_EXPECTED_BALANCE));
                return Double.parseDouble(String.valueOf(cursor.getCount()));


            }
        }

        return 0;

    }

    public double getAdminReceivedBalance() {
        String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT SUM(" + ADMIN_RECEIVED_AMOUNT + ") as Total FROM " + ADMIN_BALANCE_TABLE, null)) {

            if (cursor.moveToFirst()) {

                //int total = cursor.getInt(cursor.getColumnIndex(ADMIN_EXPECTED_BALANCE));
                return Double.parseDouble(String.valueOf(cursor.getCount()));


            }
        }

        return 0;

    }

    public double getAdminBalanceCount() {
        String countQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }


}
