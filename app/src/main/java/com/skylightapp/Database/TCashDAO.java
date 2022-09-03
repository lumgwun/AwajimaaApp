package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Tellers.TellerCash;

import java.util.ArrayList;

import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_AMOUNT;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_BRANCH;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_CODE;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_DATE;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_ID;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_ITEM_NAME;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_PACKAGE_ID;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_PROFILE_ID;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_STATUS;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_TABLE;
import static com.skylightapp.Tellers.TellerCash.TELLER_CASH_TELLER_NAME;
import static java.lang.String.valueOf;

public class TCashDAO extends DBHelperDAO{

    private static final String WHERE_ID_EQUALS = TELLER_CASH_ID
            + " =?";
    public TCashDAO(Context context) {
        super(context);
    }
    public double getTellerCashForTellerToday(String tellerName,String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = TELLER_CASH_TELLER_NAME + "=? AND " + TELLER_CASH_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerName), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ TELLER_CASH_AMOUNT +") from " + TELLER_CASH_TABLE + " WHERE " + selection,
                selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TELLER_CASH_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    private void getTellerCashTellerCursor(ArrayList<String> tellerCashes, Cursor cursor) {
        String tellerName=null;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    tellerName = cursor.getString(6);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        tellerCashes.add(tellerName);

    }
    private void getTellerCashBranchCursor(ArrayList<String> tellerCashes, Cursor cursor) {
        while (cursor.moveToNext()) {
            String branch = cursor.getString(7);
            tellerCashes.add(branch);
        }
    }
    public ArrayList<String> getAllTellerCashBranchNames() {
        ArrayList<String> tellerCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {TELLER_CASH_BRANCH};
        Cursor cursor = db.query(TELLER_CASH_TABLE, columns, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerCashBranchCursor(tellerCashArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return tellerCashArrayList;
    }
    public ArrayList<String> getAllTellerCashTellerNames() {
        ArrayList<String> tellerCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {TELLER_CASH_TELLER_NAME};
        Cursor cursor = db.query(TELLER_CASH_TABLE, columns, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerCashBranchCursor(tellerCashArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return tellerCashArrayList;
    }
    public ArrayList<TellerCash> getTellerCashForBranch(String branch) {
        ArrayList<TellerCash> tellerCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_CASH_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(branch)};
        Cursor cursor = db.query(TELLER_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getTellerCashCursor(tellerCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return tellerCashArrayList;
    }
    public ArrayList<TellerCash> getTellerCashByTellerName(String tellerName) {
        ArrayList<TellerCash> tellerCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_CASH_TELLER_NAME + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerName)};
        Cursor cursor = db.query(TELLER_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getTellerCashCursor(tellerCashArrayList, cursor);
                cursor.close();
            }

        db.close();

        return tellerCashArrayList;
    }
    public ArrayList<TellerCash> getTellerCashForTeller(int profileID) {
        ArrayList<TellerCash> tellerCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_CASH_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(TELLER_CASH_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getTellerCashCursor(tellerCashArrayList, cursor);
                cursor.close();
            }

        db.close();
        return tellerCashArrayList;
    }
    public long insertTellerCash(int _id, int profileID, int packageID, String itemName, double packageAmount, String date, String teller, String branch, long code, String branchStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TELLER_CASH_ID, _id);
        values.put(TELLER_CASH_PROFILE_ID, profileID);
        values.put(TELLER_CASH_ITEM_NAME, itemName);
        values.put(TELLER_CASH_AMOUNT, packageAmount);
        values.put(TELLER_CASH_DATE, date);
        values.put(TELLER_CASH_PACKAGE_ID, packageID);
        values.put(TELLER_CASH_TELLER_NAME, teller);
        values.put(TELLER_CASH_BRANCH, branch);
        values.put(TELLER_CASH_CODE, code);
        values.put(TELLER_CASH_STATUS, branchStatus);
        return db.insert(OFFICE_BRANCH_TABLE,null,values);
    }
    private void getTellerCashCursor(ArrayList<TellerCash> tellerCashes, Cursor cursor) {
        while (cursor.moveToNext()) {
            int tellerCashID = cursor.getInt(0);
            int profileID = cursor.getInt(1);
            String tellerItemName = cursor.getString(2);
            double amount = cursor.getDouble(3);
            String date = cursor.getString(4);
            int packageID = cursor.getInt(5);
            String tellerName = cursor.getString(6);
            String branch = cursor.getString(7);
            long code = Long.parseLong(cursor.getString(8));
            String status = cursor.getString(9);
            tellerCashes.add(new TellerCash(tellerCashID, profileID,packageID,tellerItemName,amount,tellerName,branch,date,code,status));
        }
    }
    public ArrayList<TellerCash> getAllTellerCash() {
        ArrayList<TellerCash> tellerCashArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {TELLER_CASH_ITEM_NAME,TELLER_CASH_AMOUNT,TELLER_CASH_DATE,TELLER_CASH_PACKAGE_ID,TELLER_CASH_TELLER_NAME,TELLER_CASH_BRANCH,TELLER_CASH_CODE,TELLER_CASH_STATUS};
        Cursor cursor = db.query(TELLER_CASH_TABLE, columns, null, null, TELLER_CASH_TELLER_NAME,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerCashCursor(tellerCashArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return tellerCashArrayList;
    }



    public double getTellerTotalTellerCashForTheMonth(String tellerName,String monthYear) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;


        String selection = "substr(" + TELLER_CASH_DATE + ",4)" + "=? AND " + TELLER_CASH_PROFILE_ID + "=?";

        String queryString="select sum ("+ TELLER_CASH_AMOUNT +") from " + TELLER_CASH_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(tellerName)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TELLER_CASH_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;

    }


    public void deleteTellerCash(int tellerCashID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_CASH_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerCashID)};
        db.delete(TELLER_CASH_TABLE,
                selection, selectionArgs);

    }


    public void updateTellerCashCode(int tcID,long code) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues stocksUpdateValues = new ContentValues();
        String selection = TELLER_CASH_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tcID)};
        stocksUpdateValues.put(TELLER_CASH_CODE, code);
        db.update(TELLER_CASH_TABLE, stocksUpdateValues, selection, selectionArgs);

    }


}
