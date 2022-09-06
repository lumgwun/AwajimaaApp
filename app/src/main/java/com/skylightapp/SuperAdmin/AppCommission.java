package com.skylightapp.SuperAdmin;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Account;
import com.skylightapp.Database.DBHelper;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static java.lang.String.valueOf;

public class AppCommission implements Parcelable, Serializable {
    public static final String ADMIN_BALANCE_TABLE = "ab_balance_Table";
    public static final String ADMIN_BALANCE_ID = "ab_id";
    public static final String ADMIN_EXPECTED_BALANCE = "ab_exp_mount";
    public static final String ADMIN_RECEIVED_AMOUNT = "ab_received_mount";
    public static final String ADMIN_BALANCE_DATE = "ab_Date";
    public static final String ADMIN_BALANCE_PROFILE_ID = "ab_Prof_ID";
    public static final String ADMIN_BALANCE_CUS_ID = "ab_Cus_ID";
    public static final String ADMIN_BALANCE_PACKID = "ab_PackID";
    public static final String ADMIN_BALANCE_STATUS = "admin_balance_status";
    public static final String ADMIN_BALANCE_ACCT_ID = "ab_AcctID";
    public static final String ADMIN_BALANCE_TRANX_ID = "ab_TranxID";



    public static final String CREATE_ADMIN_BALANCE_TABLE = "CREATE TABLE IF NOT EXISTS " + ADMIN_BALANCE_TABLE + " (" + ADMIN_BALANCE_ID + " INTEGER, " + ADMIN_BALANCE_PROFILE_ID + " TEXT, " + ADMIN_BALANCE_CUS_ID + " TEXT, " + ADMIN_BALANCE_PACKID + " TEXT, " + ADMIN_EXPECTED_BALANCE + " TEXT, " + ADMIN_RECEIVED_AMOUNT + " TEXT, " + ADMIN_BALANCE_DATE + " TEXT, " +
            ADMIN_BALANCE_STATUS + " TEXT, " + ADMIN_BALANCE_ACCT_ID + " INTEGER, " + ADMIN_BALANCE_TRANX_ID + " TEXT,"+ "FOREIGN KEY(" + ADMIN_BALANCE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+ "FOREIGN KEY(" + ADMIN_BALANCE_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"PRIMARY KEY(" + ADMIN_BALANCE_ID  + "))";



    double adminExpectedBalance;
    double adminReceivedBalance;
    String adminBalanceDate;
    int adminBalanceID;
    private int adminBalanceAcctID;
    private int adminBalanceTranxID;
    DBHelper dbHelper;

    public AppCommission() {

    }

    public AppCommission(int adminBalanceID, double adminExpectedBalance, double adminReceivedBalance, String adminBalanceDate) {
        this.adminExpectedBalance = adminExpectedBalance;
        this.adminReceivedBalance = adminReceivedBalance;
        this.adminBalanceDate = adminBalanceDate;
        this.adminBalanceID = adminBalanceID;

    }

    protected AppCommission(Parcel in) {
        adminExpectedBalance = in.readDouble();
        adminReceivedBalance = in.readDouble();
        adminBalanceDate = in.readString();
        adminBalanceID = in.readInt();
        adminBalanceAcctID = in.readInt();
        adminBalanceTranxID = in.readInt();
    }

    public static final Creator<AppCommission> CREATOR = new Creator<AppCommission>() {
        @Override
        public AppCommission createFromParcel(Parcel in) {
            return new AppCommission(in);
        }

        @Override
        public AppCommission[] newArray(int size) {
            return new AppCommission[size];
        }
    };

    public double getAdminExpectedBalance() {
        AppCommission appCommission = new AppCommission();
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


    public int getAdminBalanceAcctID() {
        return adminBalanceAcctID;
    }

    public void setAdminBalanceAcctID(int adminBalanceAcctID) {
        this.adminBalanceAcctID = adminBalanceAcctID;
    }

    public int getAdminBalanceTranxID() {
        return adminBalanceTranxID;
    }

    public void setAdminBalanceTranxID(int adminBalanceTranxID) {
        this.adminBalanceTranxID = adminBalanceTranxID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(adminExpectedBalance);
        parcel.writeDouble(adminReceivedBalance);
        parcel.writeString(adminBalanceDate);
        parcel.writeInt(adminBalanceID);
        parcel.writeInt(adminBalanceAcctID);
        parcel.writeInt(adminBalanceTranxID);
    }
}
