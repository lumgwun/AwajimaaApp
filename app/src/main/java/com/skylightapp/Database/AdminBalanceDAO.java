package com.skylightapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.SuperAdmin.AdminBalance;

import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.Bookings.BOOKING_ID;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_BALANCE_TABLE;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_EXPECTED_BALANCE;
import static com.skylightapp.SuperAdmin.AdminBalance.ADMIN_RECEIVED_AMOUNT;

public class AdminBalanceDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = BOOKING_ID
            + " =?";
    public AdminBalanceDAO(Context context) {
        super(context);
    }
    public List<AdminBalance> getAllAdminBalances() {
        List<AdminBalance> adminBalances = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {

            if (cursor.moveToFirst()) {
                do {
                    AdminBalance adminBalance = new AdminBalance();
                    adminBalance.setAdminBalanceID(Integer.parseInt(cursor.getString(0)));
                    adminBalance.setAdminExpectedBalance(Double.parseDouble(cursor.getString(1)));
                    adminBalance.setAdminExpectedBalance(Double.parseDouble(cursor.getString(1)));
                    adminBalance.setAdminReceivedBalance(Double.parseDouble(cursor.getString(2)));
                    adminBalance.setAdminBalanceDate(cursor.getString(4));

                    adminBalances.add(adminBalance);
                } while (cursor.moveToNext());
            }
        }
        return adminBalances;

    }
    public double getAdminExpectedBalance() {
        AdminBalance adminBalance = new AdminBalance();
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
