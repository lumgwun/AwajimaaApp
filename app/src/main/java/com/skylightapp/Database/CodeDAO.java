package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.PaymentCode;

import java.util.ArrayList;

import static com.skylightapp.Classes.PaymentCode.CODE_CUS_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_DATE;
import static com.skylightapp.Classes.PaymentCode.CODE_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_MANAGER;
import static com.skylightapp.Classes.PaymentCode.CODE_PIN;
import static com.skylightapp.Classes.PaymentCode.CODE_PROFILE_ID;
import static com.skylightapp.Classes.PaymentCode.CODE_REPORT_NO;
import static com.skylightapp.Classes.PaymentCode.CODE_STATUS;
import static com.skylightapp.Classes.PaymentCode.CODE_TABLE;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static java.lang.String.valueOf;

public class CodeDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = CODE_ID
            + " =?";
    public CodeDAO(Context context) {
        super(context);
    }
    public long insertSavingsCode(PaymentCode paymentCode) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CODE_CUS_ID, paymentCode.getCod_customer_ID());
        contentValues.put(CODE_REPORT_NO, paymentCode.getCode_savingsID());
        contentValues.put(CODE_PIN, paymentCode.getCode());
        contentValues.put(CODE_DATE, paymentCode.getCodeDate());
        contentValues.put(CODE_STATUS, paymentCode.getCodeStatus());
        return sqLiteDatabase.insert(CODE_TABLE, null, contentValues);
    }



    public int updateSavingsCode(int savingsID) {
        SQLiteDatabase db = this.getWritableDatabase();
        PaymentCode paymentCode= new PaymentCode();
        ContentValues values = new ContentValues();
        values.put(CODE_PIN, paymentCode.getCode());
        String selection = CODE_REPORT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(savingsID)};
        return db.update(CODE_TABLE, values, selection, selectionArgs);
    }
    public void updateSavingsCodeStatus(int savingsId,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        PaymentCode paymentCode = new PaymentCode();
        status = paymentCode.getCodeStatus();
        int codeID = paymentCode.getCodeID();
        contentValues.put(CODE_PIN, codeID);
        contentValues.put(CODE_STATUS, status);
        String selection = CODE_REPORT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(savingsId)};
        db.update(CODE_TABLE, contentValues, selection, selectionArgs);
        db.close();


    }

    public void deleteSavingsCode(int codeID) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = CODE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(codeID)};
        db.delete(CODE_TABLE, selection, selectionArgs);


    }


    private void getPaymentCodeFromCursor(ArrayList<PaymentCode> codeArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int ownerProfileId = cursor.getInt(8);
            String phoneNumber = cursor.getString(2);
            long code = cursor.getLong(3);
            String pinGenerationDate = cursor.getString(4);
            String status = cursor.getString(5);
            String approver = cursor.getString(6);

            codeArrayList.add(new PaymentCode(ownerProfileId, phoneNumber, code, pinGenerationDate, status, approver));
        }


    }
    public ArrayList<PaymentCode> getSavingsCodesCustomer(int customerID) {
        ArrayList<PaymentCode> codes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = CODE_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(CODE_TABLE, null, selection, selectionArgs, null,
                null, null);
            /*Cursor cursor = db.query(CODE_TABLE, null, CUSTOMER_ID
                            + " = " + customerID, new String[]{CODE_DATE,CODE_MANAGER,CODE_PIN}, null,
                    null, null);*/
        getAllSavingsCodes();

        cursor.close();
        //db.close();

        return codes;

    }
    public ArrayList<PaymentCode> getSavingsCodesProfile(int profileID) {
        ArrayList<PaymentCode> codes = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = CODE_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(CODE_TABLE, null, selection, selectionArgs, null,
                null, null);

        getAllSavingsCodes();

        cursor.close();
        //db.close();

        return codes;

    }


    public ArrayList<PaymentCode> getCodesFromCurrentCustomer(int customerID) {
        ArrayList<PaymentCode> codeArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = CODE_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};

        Cursor cursor = db.query(CODE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPaymentCodeFromCursor(codeArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return codeArrayList;
    }




    public ArrayList<PaymentCode> getSavingsCodeForDate(String date) {
        ArrayList<PaymentCode> codeArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = CODE_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.query(CODE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPaymentCodeFromCursor(codeArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return codeArrayList;
    }
    public ArrayList<PaymentCode> getCodesFromCurrentSavings(int savingsID) {
        ArrayList<PaymentCode> codeArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = CODE_REPORT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(savingsID)};

        Cursor cursor = db.query(CODE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPaymentCodeFromCursor(codeArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();


        return codeArrayList;
    }
    public ArrayList<PaymentCode> getCodesFromCurrentTeller(String teller) {
        ArrayList<PaymentCode> codeArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = CODE_MANAGER + "=?";
        String[] selectionArgs = new String[]{valueOf(teller)};

        Cursor cursor = db.query(CODE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPaymentCodeFromCursor(codeArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return codeArrayList;
    }
    public ArrayList<PaymentCode> getAllSavingsCodes() {
        ArrayList<PaymentCode> paymentCodeArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(CODE_TABLE, null, null, null, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getPaymentCodeFromCursor(paymentCodeArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return paymentCodeArrayList;
    }

    public int saveNewSavingsCode(PaymentCode paymentCode) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues codeValues = new ContentValues();
        Customer customer = new Customer();
        CustomerDailyReport customerDailyReport= new CustomerDailyReport();
        //customerDailyReport=paymentCode.getCustomerDailyReport();
        int codeID= paymentCode.getCodeID();
        codeValues.put(CODE_REPORT_NO, paymentCode.getCodeID());
        codeValues.put(CODE_CUS_ID, valueOf(customer.getCusUID()));
        codeValues.put(CODE_ID, codeID);
        codeValues.put(CODE_PIN, paymentCode.getCode());
        codeValues.put(CODE_DATE, paymentCode.getCodeDate());
        codeValues.put(CODE_STATUS, paymentCode.getCodeStatus());
        codeValues.put(CODE_MANAGER, paymentCode.getCodeManager());
        db.insert(CODE_TABLE, null, codeValues);
        db.close();
        return codeID;
    }
}
