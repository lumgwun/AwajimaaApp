package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.TellerCountData;
import com.skylightapp.Classes.TransactionGranting;

import java.util.ArrayList;

import static com.skylightapp.Classes.Customer.CUSTOMER_DATE_JOINED;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_PHONE_NUMBER;
import static com.skylightapp.Classes.Customer.CUSTOMER_PROF_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DATE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_AMOUNT;
import static com.skylightapp.Classes.Transaction.TRANSACTION_DATE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_ACCTNAME;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_ACCTNO;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_AMOUNT;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_AUTHORIZER;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_BANK;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_CUSTOMER_ID;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_C_NAME;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_DATE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_ID;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_PAYMENT_METHOD;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_PROFILE_ID;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_PURPOSE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_STATUS;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_TABLE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_TYPE;
import static java.lang.String.valueOf;

public class TransactionGrantingDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = TANSACTION_EXTRA_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;

    public TransactionGrantingDAO(Context context) {
        super(context);
    }

    public long insertTransaction_Granting(int te_id, int profileID, int cusID, String cusName, double teAmount, String date, String bank, String acctName, String acctNo, String purpose, String method, String authorizer, String teType, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TANSACTION_EXTRA_ID, te_id);
        values.put(PROFILE_ID, profileID);
        values.put(CUSTOMER_ID, cusID);
        values.put(TANSACTION_EXTRA_C_NAME, cusName);
        values.put(TANSACTION_EXTRA_BANK, bank);
        values.put(TANSACTION_EXTRA_ACCTNAME, acctName);
        values.put(TANSACTION_EXTRA_ACCTNO, acctNo);
        values.put(TANSACTION_EXTRA_AMOUNT, teAmount);
        values.put(TANSACTION_EXTRA_TYPE, teType);
        values.put(TANSACTION_EXTRA_PURPOSE, purpose);
        values.put(TANSACTION_EXTRA_PAYMENT_METHOD, method);
        values.put(TANSACTION_EXTRA_DATE, date);
        values.put(TANSACTION_EXTRA_AUTHORIZER, authorizer);
        values.put(TANSACTION_EXTRA_STATUS, status);
        return db.insert(TANSACTION_EXTRA_TABLE, null, values);
    }

    public void updateTranxGrantingStatus(int teID,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues stocksUpdateValues = new ContentValues();
        String selection = TANSACTION_EXTRA_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(teID)};
        stocksUpdateValues.put(TANSACTION_EXTRA_STATUS, status);
        db.update(TANSACTION_EXTRA_TABLE, stocksUpdateValues, selection, selectionArgs);


    }
    public void updateTranxGranting(int teID,String authorizer,String methodOfPayment,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues teUpdateValues = new ContentValues();
        String selection = TANSACTION_EXTRA_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(teID)};
        teUpdateValues.put(TANSACTION_EXTRA_PAYMENT_METHOD, methodOfPayment);
        teUpdateValues.put(TANSACTION_EXTRA_AUTHORIZER, authorizer);
        teUpdateValues.put(TANSACTION_EXTRA_STATUS, status);
        db.update(TANSACTION_EXTRA_TABLE, teUpdateValues, selection, selectionArgs);
        db.close();


    }
    public String getTransaction_GrantingType(int teID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor cursor=null;
            String loanType=null;
            String selection = TANSACTION_EXTRA_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(teID)};
            cursor = sqLiteDatabase.query(TANSACTION_EXTRA_TABLE, null,  selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        loanType=cursor.getString(16);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }

            return loanType;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public int getAPPROVER_TE_MonthCount(int profileID, String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String queryString="SELECT COUNT (*) FROM " + TANSACTION_EXTRA_TABLE ;

        String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + CUSTOMER_ID + "=?";
        String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + PROFILE_ID + "=?";

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(profileID)};

        @SuppressLint("Recycle") Cursor cursor = db.query(queryString,
                null,
                selection, selectionArgs,
                null, null, null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }

    public int getTE_MonthCount1(String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String query="SELECT COUNT (*) from "+TANSACTION_EXTRA_TABLE +" WHERE "+ TANSACTION_EXTRA_DATE +" >= date('now','localtime', '-31 day')";
        String queryString="SELECT COUNT (*) FROM " + TANSACTION_EXTRA_TABLE ;

        String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=?";
        String selection22 = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + CUSTOMER_ID + "=?";
        String selection1 = "substr(" + TANSACTION_EXTRA_DATE + ",4)" + "=? AND " + PROFILE_ID + "=?";

        String[] selectionArgs = new String[]{valueOf(monthYear)};

        @SuppressLint("Recycle") Cursor cursor = db.query(queryString,
                null,
                selection, selectionArgs,
                null, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }

    private void getTECursor(ArrayList<TransactionGranting> transactionGrantings, Cursor cursor) {
        while (cursor.moveToNext()) {

            int teID = cursor.getInt(0);
            int profileID = cursor.getInt(1);
            int customerID = cursor.getInt(2);
            String teCusName = cursor.getString(3);
            String te_Bank = cursor.getString(4);
            String teBank_AcctName = cursor.getString(5);
            String te_Bank_AcctNo = cursor.getString(6);
            double te_Amount = cursor.getDouble(7);
            String te_Purpose = cursor.getString(8);
            String te_method = cursor.getString(9);
            String date = cursor.getString(10);
            String te_Authorizer = cursor.getString(11);
            String status = cursor.getString(12);
            transactionGrantings.add(new TransactionGranting(teID, profileID, customerID, teCusName, te_Bank, teBank_AcctName, te_Bank_AcctNo, te_Amount, te_Purpose, date, te_method, te_Authorizer, status));
        }
    }

    public ArrayList<TransactionGranting> getAllTransactionGranting() {
        ArrayList<TransactionGranting> transactionGrantings = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {TANSACTION_EXTRA_ID, TANSACTION_EXTRA_C_NAME, TANSACTION_EXTRA_BANK, TANSACTION_EXTRA_ACCTNO, TANSACTION_EXTRA_ACCTNAME, TANSACTION_EXTRA_AMOUNT, TANSACTION_EXTRA_PURPOSE, TANSACTION_EXTRA_DATE, TANSACTION_EXTRA_PAYMENT_METHOD, TANSACTION_EXTRA_AUTHORIZER, TANSACTION_EXTRA_STATUS};
        Cursor cursor = db.query(TANSACTION_EXTRA_TABLE, columns, null, null, TANSACTION_EXTRA_C_NAME,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTECursor(transactionGrantings, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return transactionGrantings;
    }

    public ArrayList<TransactionGranting> getTranxRequestAtDate(String currentDate) {
        ArrayList<TransactionGranting> grantingArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TANSACTION_EXTRA_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(currentDate)};

        Cursor cursor = db.query(TANSACTION_EXTRA_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTECursor(grantingArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return grantingArrayList;
    }

    public int getTranxRequestCountAtDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = TANSACTION_EXTRA_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TANSACTION_EXTRA_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }


    public double getTotalTranxExtraForTheMonth1(String monthYear) {
        String monthTotal2=null;

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "= monthYear";

        String query="SELECT COUNT (*) from "+TANSACTION_EXTRA_TABLE +" WHERE "+ TANSACTION_EXTRA_DATE +" >= date('now','localtime', '-31 day')";
        String queryString="select sum ("+ TANSACTION_EXTRA_AMOUNT +") from " + TANSACTION_EXTRA_TABLE + " WHERE " + selection;


        String selection22 = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + CUSTOMER_ID + "=?";
        String selection1 = "substr(" + TANSACTION_EXTRA_DATE + ",4)" + "=? AND " + PROFILE_ID + "=?";

        String[] selectionArgs = new String[]{valueOf(monthYear)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,null,

                null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    public double getTotalTranxRequestAtDate(String date) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;

        String selection = TANSACTION_EXTRA_DATE + "=?";

        String[] selectionArgs = new String[]{valueOf(date)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ TANSACTION_EXTRA_AMOUNT +") from " + TANSACTION_EXTRA_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }



    public double getTotalTEForCusTheMonth1(int customerID,String monthYear) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;

        String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";


        String query="SELECT COUNT (*) from "+TANSACTION_EXTRA_TABLE +" WHERE "+ TANSACTION_EXTRA_DATE +" >= date('now','localtime', '-31 day')";
        String queryString="select sum ("+ TANSACTION_EXTRA_AMOUNT +") from " + TANSACTION_EXTRA_TABLE + " WHERE " + selection;


        String selection22 = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";
        String selection1 = "substr(" + TANSACTION_EXTRA_DATE + ",4)" + "=? AND " + TANSACTION_EXTRA_PROFILE_ID + "=?";

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    public double getTotalTEForProfileTheMonth(int profileID,String monthYear) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;


        String selection = "STRFTIME('%Y-%m',TANSACTION_EXTRA_DATE)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";

        String queryString="select sum ("+ TANSACTION_EXTRA_AMOUNT +") from " + TANSACTION_EXTRA_TABLE + " WHERE " + selection;


        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(profileID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);



        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TANSACTION_EXTRA_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;

    }




    public int getCusMonthTransactionCount1(int customerID,String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = "substr(" + TRANSACTION_DATE + ",4)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";

        String queryString="select COUNT ("+ TRANSACTION_AMOUNT +") from " + TRANSACTIONS_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TRANSACTION_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }





}
