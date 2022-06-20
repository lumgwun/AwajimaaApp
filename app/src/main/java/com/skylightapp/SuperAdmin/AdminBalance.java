package com.skylightapp.SuperAdmin;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.Account;
import com.skylightapp.Database.DBHelper;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;
import static java.lang.String.valueOf;

public class AdminBalance extends Account {
    public static final String ADMIN_BALANCE_TABLE = "Balance_Table";
    public static final String ADMIN_BALANCE_NO = "ab_id";
    public static final String ADMIN_EXPECTED_BALANCE = "Ab_exp_mount";
    public static final String ADMIN_RECEIVED_AMOUNT = "ab_received_mount";
    public static final String ADMIN_BALANCE_DATE = "ab_Date";
    public static final String ADMIN_BALANCE_STATUS = "admin_balance_status";


    public static final String CREATE_ADMIN_BALANCE_TABLE = "CREATE TABLE " + ADMIN_BALANCE_TABLE + " (" + ADMIN_BALANCE_NO + " INTEGER, " + PROFILE_ID + " INTEGER, " + CUSTOMER_ID + " INTEGER, " + PACKAGE_ID + " LONG, " + ADMIN_EXPECTED_BALANCE + " DOUBLE, " +
            ADMIN_RECEIVED_AMOUNT + " DOUBLE, " + ADMIN_BALANCE_DATE + " TEXT , " + ADMIN_BALANCE_STATUS + " DOUBLE, " +
            "PRIMARY KEY(" + ADMIN_BALANCE_NO  + "))";


    double adminExpectedBalance;
    double adminReceivedBalance;
    String adminBalanceDate;
    int adminBalanceID;
    DBHelper dbHelper;

    public AdminBalance() {

    }

    public AdminBalance(int adminBalanceID, double adminExpectedBalance, double adminReceivedBalance, String adminBalanceDate) {
        this.adminExpectedBalance = adminExpectedBalance;
        this.adminReceivedBalance = adminReceivedBalance;
        this.adminBalanceDate = adminBalanceDate;
        this.adminBalanceID = adminBalanceID;

    }
    public double getAdminExpectedBalance() {
        AdminBalance adminBalance = new AdminBalance();
        String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //String[] whereArgs = new String[]{searchString};
        try (Cursor cursor = db.rawQuery("SELECT SUM(" + ADMIN_EXPECTED_BALANCE + ") as Total FROM " + ADMIN_BALANCE_TABLE, null)) {

            if (cursor.moveToFirst()) {

                //int total = cursor.getInt(cursor.getColumnIndex(ADMIN_EXPECTED_BALANCE));
                return Double.parseDouble(String.valueOf(cursor.getCount()));


            }
        
        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public double getAdminReceivedBalance() {


        String selectQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            try (Cursor cursor = db.rawQuery("SELECT SUM(" + ADMIN_RECEIVED_AMOUNT + ") as Total FROM " + ADMIN_BALANCE_TABLE, null)) {

                if (cursor.moveToFirst()) {

                    //int total = cursor.getInt(cursor.getColumnIndex(ADMIN_EXPECTED_BALANCE));
                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;


    }
    public double getTotalReceivedAmount() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            try (Cursor cursor = db.rawQuery(new StringBuilder().append("SELECT SUM(").append(ADMIN_RECEIVED_AMOUNT).append(") as Total FROM").append(ADMIN_BALANCE_TABLE).toString(),
                    null)) {

                if (cursor.moveToFirst()){
                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return -1;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;
    }
    public double getTotalExpectedAmount() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            try (Cursor cursor = db.rawQuery(new StringBuilder().append("SELECT SUM(").append(ADMIN_EXPECTED_BALANCE).append(") as Total FROM").append(ADMIN_BALANCE_TABLE).toString(),
                    null)) {

                if (cursor.moveToFirst()){
                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return -1;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;

    }


   /* public int getAdminBalanceCount() {
        String countQuery = "SELECT * FROM " + ADMIN_BALANCE_TABLE;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            try (Cursor cursor = db.rawQuery(new StringBuilder().append("SELECT SUM(").append(ADMIN_EXPECTED_BALANCE).append(") as Total FROM").append(ADMIN_BALANCE_TABLE).toString(),
                    null)) {

                if (cursor.moveToFirst()){
                    return cursor.getCount();


                }
            }

            return -1;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }*/

    public void setAdminExpectedBalance(double adminExpectedBalance) {
        this.adminExpectedBalance = adminExpectedBalance;
    }

    public void setAdminReceivedBalance(double adminReceivedBalance) {
        this.adminReceivedBalance = adminReceivedBalance;
    }
    public String getAdminBalanceDate() {
        return this.adminBalanceDate;
    }

    public void setAdminBalanceDate(String adminBalanceDate) {
        this.adminBalanceDate = adminBalanceDate;
    }

    public int getAdminBalanceID() {
        return this.adminBalanceID;
    }

    public void setAdminBalanceID(int adminBalanceID) {
        this.adminBalanceID = adminBalanceID;
    }


}
