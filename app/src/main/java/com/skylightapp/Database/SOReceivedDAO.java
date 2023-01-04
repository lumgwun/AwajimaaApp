package com.skylightapp.Database;


import static com.skylightapp.Classes.SOReceived.SOR_AMOUNT;
import static com.skylightapp.Classes.SOReceived.SOR_COMMENT;
import static com.skylightapp.Classes.SOReceived.SOR_CUS_ID;
import static com.skylightapp.Classes.SOReceived.SOR_CUS_NAME;
import static com.skylightapp.Classes.SOReceived.SOR_DATE;
import static com.skylightapp.Classes.SOReceived.SOR_ID;
import static com.skylightapp.Classes.SOReceived.SOR_MANAGER_NAME;
import static com.skylightapp.Classes.SOReceived.SOR_ManagerID;
import static com.skylightapp.Classes.SOReceived.SOR_OFFICE;
import static com.skylightapp.Classes.SOReceived.SOR_PLATFORM;
import static com.skylightapp.Classes.SOReceived.SOR_PROF_ID;
import static com.skylightapp.Classes.SOReceived.SOR_SOACCT_NO;
import static com.skylightapp.Classes.SOReceived.SOR_SO_ID;
import static com.skylightapp.Classes.SOReceived.SOR_STATUS;
import static com.skylightapp.Classes.SOReceived.SOR_TX_REF;
import static com.skylightapp.Classes.SOReceived.SO_RECEIVED_TABLE;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.SOReceived;

import java.util.ArrayList;

public class SOReceivedDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = SOR_ID
            + " =?";
    public SOReceivedDAO(Context context) {
        super(context);
    }
    public void updateSOR(int soRID,String txRef,String dateReceived,String comment,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SOR_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(soRID)};
        ContentValues contentValues = new ContentValues();
        contentValues.put(SOR_TX_REF, txRef);
        contentValues.put(SOR_DATE, dateReceived);
        contentValues.put(SOR_COMMENT, comment);
        contentValues.put(SOR_STATUS, status);
        db.update(SO_RECEIVED_TABLE, contentValues, selection, selectionArgs);
        db.close();

    }
    public long insertSOReceived(int profileID, int customerID, int soID, long soAcctNo,int soReceivedID,double amount, String date,  String officebranch, int managerID, String txRef,String managerName,String comment, String status,String cusName,String paymentGateway) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SOR_PROF_ID, profileID);
        contentValues.put(SOR_CUS_ID, customerID);
        contentValues.put(SOR_SO_ID, soID);
        contentValues.put(SOR_ID, soReceivedID);
        contentValues.put(SOR_AMOUNT, amount);
        contentValues.put(SOR_DATE, date);
        contentValues.put(SOR_OFFICE, officebranch);
        contentValues.put(SOR_SOACCT_NO, soAcctNo);
        contentValues.put(SOR_STATUS, status);
        contentValues.put(SOR_ManagerID, managerID);
        contentValues.put(SOR_MANAGER_NAME, managerName);
        contentValues.put(SOR_COMMENT, comment);
        contentValues.put(SOR_TX_REF, txRef);
        contentValues.put(SOR_CUS_NAME, cusName);
        contentValues.put(SOR_PLATFORM, paymentGateway);
        return sqLiteDatabase.insert(SO_RECEIVED_TABLE, null, contentValues);

    }
    public int getSORForSOAndStatus(int soID, String status) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = SOR_SO_ID + "=? AND " + SOR_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(soID), valueOf(status)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + SO_RECEIVED_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return count;
    }
    private void getSORFromCursor(ArrayList<SOReceived> receivedArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int soRID = cursor.getInt(1);
            int soRSoID = cursor.getInt(2);
            int soRManagerID = cursor.getInt(3);
            int profID = cursor.getInt(4);
            int customerID = cursor.getInt(5);
            int sorSoAcctNo = cursor.getInt(6);
            double sorAmount = Double.parseDouble(cursor.getString(7));
            String sor_TxRef = cursor.getString(8);
            String date = cursor.getString(9);
            String managerName = cursor.getString(10);
            String sorOfficeID = cursor.getString(11);
            String soStatus = cursor.getString(12);
            String soComment = cursor.getString(13);
            String soCusName = cursor.getString(14);
            String soPlatform = cursor.getString(15);

            receivedArrayList.add(new SOReceived(soRID,profID,customerID,soCusName,soRSoID,soRManagerID,sorSoAcctNo,sorOfficeID,sorAmount, sor_TxRef,date,managerName,soPlatform, soComment,soStatus));
        }

    }

    public ArrayList<SOReceived> getAllSORForCustomer(int customerID) {
        ArrayList<SOReceived> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SOR_CUS_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf((customerID))};

        Cursor cursor = db.query(SO_RECEIVED_TABLE, null, selection, selectionArgs, null,
                null, null);
        getSORFromCursor(standingOrders, cursor);

        cursor.close();
        db.close();

        return standingOrders;
    }

    public ArrayList<SOReceived> getAllSORForOffice(int officeID) {
        ArrayList<SOReceived> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SOR_OFFICE + "=?";
        String[] selectionArgs = new String[]{String.valueOf((officeID))};

        Cursor cursor = db.query(SO_RECEIVED_TABLE, null, selection, selectionArgs, null,
                null, null);
        getSORFromCursor(standingOrders, cursor);

        cursor.close();
        db.close();

        return standingOrders;
    }

    public ArrayList<SOReceived> getAllSORForManager(int managerID) {
        ArrayList<SOReceived> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SOR_ManagerID + "=?";
        String[] selectionArgs = new String[]{String.valueOf((managerID))};

        Cursor cursor = db.query(SO_RECEIVED_TABLE, null, selection, selectionArgs, null,
                null, null);
        getSORFromCursor(standingOrders, cursor);

        cursor.close();
        db.close();

        return standingOrders;
    }
    public ArrayList<SOReceived> getAllSORForSOAcct(int soAcctID) {
        ArrayList<SOReceived> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SOR_SOACCT_NO + "=?";
        String[] selectionArgs = new String[]{String.valueOf((soAcctID))};

        Cursor cursor = db.query(SO_RECEIVED_TABLE, null, selection, selectionArgs, null,
                null, null);
        getSORFromCursor(standingOrders, cursor);

        cursor.close();
        db.close();

        return standingOrders;
    }
}
