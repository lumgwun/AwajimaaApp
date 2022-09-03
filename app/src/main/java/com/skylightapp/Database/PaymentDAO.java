package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.Payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.skylightapp.Classes.Payment.PAYMENTS_TABLE;
import static com.skylightapp.Classes.Payment.PAYMENTTYPE;
import static com.skylightapp.Classes.Payment.PAYMENT_ACCOUNT;
import static com.skylightapp.Classes.Payment.PAYMENT_ACCOUNT_TYPE;
import static com.skylightapp.Classes.Payment.PAYMENT_ADMIN_ID;
import static com.skylightapp.Classes.Payment.PAYMENT_AMOUNT;
import static com.skylightapp.Classes.Payment.PAYMENT_APPROVAL_DATE;
import static com.skylightapp.Classes.Payment.PAYMENT_APPROVER;
import static com.skylightapp.Classes.Payment.PAYMENT_CODE;
import static com.skylightapp.Classes.Payment.PAYMENT_CUS_ID;
import static com.skylightapp.Classes.Payment.PAYMENT_DATE;
import static com.skylightapp.Classes.Payment.PAYMENT_ID;
import static com.skylightapp.Classes.Payment.PAYMENT_OFFICE;
import static com.skylightapp.Classes.Payment.PAYMENT_PROF_ID;
import static com.skylightapp.Classes.Payment.PAYMENT_STATUS;
import static com.skylightapp.Classes.Profile.PASSWORD;
import static com.skylightapp.Classes.Profile.PASSWORD_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROF_ID_FOREIGN_KEY_PASSWORD;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static java.lang.String.valueOf;

public class PaymentDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = PAYMENT_ID
            + " =?";
    public PaymentDAO(Context context) {
        super(context);
    }
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public int getPaymentCountToday(String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = new String[]{today};
        String selection = PAYMENT_DATE + "=?";
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PAYMENTS_TABLE + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }


    public int getPaymentCountTodayForTeller(int profileID,String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = PAYMENT_PROF_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

    }
    public int getPaymentCountTodayForCustomer(int customerID,String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = PAYMENT_CUS_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }*/


    }
    public int getPaymentCountTodayForBranch(String branchName,String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = PAYMENT_OFFICE + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }
    public double getTotalPaymentTodayForTeller1(int profileID, String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        Cursor cursor=null;
        String selection = PAYMENT_PROF_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
        cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }


    public double getTotalPaymentTodayForCustomer(int customerID, String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_CUS_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(today)};
        //Cursor cursor = db.rawQuery("select sum("+ REPORT_TOTAL + ") from " + DAILY_REPORT_TABLE, null);
        Cursor cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;


        /*try {

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }

    public double getTotalPaymentTodayForBranch1(String branchName, String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_OFFICE + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;


        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }
    public double getTotalPaymentForBranch(String branchName) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_OFFICE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName)};
        //Cursor cursor = db.rawQuery("select sum("+ PAYMENT_AMOUNT + ") from " + PAYMENTS_TABLE, null);

        Cursor cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;

        /*try {



        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }


    public double getTotalPaymentForTeller(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ PAYMENT_AMOUNT +") from " + PAYMENTS_TABLE + " WHERE " + selection, selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;



        /*try {


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return 0;*/
    }
    public double getTotalPaymentToday1(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        @SuppressLint("Recycle") Cursor cursor = db.query(true, PAYMENTS_TABLE, null, PAYMENT_DATE+"="+ today, null, null, null, null, null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(PAYMENT_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return count;
    }






    public long insertPayment(int paymentID, int  profileID, int customerID, String office, String paymentDate, Payment.PAYMENT_TYPE type, double paymentAmount, long paymentCode, long paymentAcct, String acctType, String approver, String approvalDate, String paymentStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PAYMENT_ID, paymentID);
        values.put(PAYMENT_PROF_ID, profileID);
        values.put(PAYMENT_CUS_ID, customerID);
        values.put(PAYMENT_DATE, paymentDate);
        values.put(PAYMENTTYPE, String.valueOf(type));
        values.put(PAYMENT_AMOUNT, paymentAmount);
        values.put(PAYMENT_CODE, paymentCode);
        values.put(PAYMENT_ACCOUNT, paymentAcct);
        values.put(PAYMENT_ACCOUNT_TYPE, acctType);
        values.put(PAYMENT_APPROVER, approver);
        values.put(PAYMENT_APPROVAL_DATE, approvalDate);
        values.put(PAYMENT_ADMIN_ID, "");
        values.put(PAYMENT_OFFICE, office);
        values.put(PAYMENT_STATUS, paymentStatus);
        db.insert(PAYMENTS_TABLE,null,values);


        return paymentID;
    }
    public void updatePayment(int paymentID, long paymentCode, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues paymentUpdateValues = new ContentValues();
        paymentUpdateValues.put(PAYMENT_CODE, paymentCode);
        paymentUpdateValues.put(PAYMENT_STATUS, status);
        String selection = PAYMENT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(paymentID)};
        db.update(PAYMENTS_TABLE,
                paymentUpdateValues, selection, selectionArgs);
        db.close();


    }
    public void updatePaymentAmount(int paymentID,double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues paymentUpdateValues = new ContentValues();
        paymentUpdateValues.put(PAYMENT_AMOUNT, amount);
        String selection = PAYMENT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(paymentID)};
        db.update(PAYMENTS_TABLE,
                paymentUpdateValues, selection, selectionArgs);
        db.close();

    }

    public ArrayList<Payment> getALLPaymentsSuper() {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(PAYMENTS_TABLE, null, null, null, null,
                null, null);
        getPaymentFromCursorAdmin(paymentArrayList, cursor);
        cursor.close();
        //db.close();
        return paymentArrayList;
    }
    public ArrayList<Payment> getALLPaymentsSuperToday(String today) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorAdmin(paymentArrayList, cursor);
        cursor.close();
        //db.close();

        return paymentArrayList;
    }
    public ArrayList<Payment> getALLPaymentsBranchToday(String branch,String today) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = PAYMENT_OFFICE + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branch), valueOf(today)};

        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorAdmin(paymentArrayList, cursor);
        cursor.close();
        //db.close();

        return paymentArrayList;
    }
    public ArrayList<Payment> getALLPaymentsTeller(int profileID) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PAYMENT_PROF_ID + "=? ";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorTeller(paymentArrayList, cursor);
        cursor.close();
        //db.close();

        return paymentArrayList;
    }


    public ArrayList<Payment> getALLPaymentsTellerToday(int profileID,String today) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = PAYMENT_PROF_ID + "=? AND " + PAYMENT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(today)};
        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorTeller(paymentArrayList, cursor);
        cursor.close();
        db.close();

        return paymentArrayList;

    }
    public ArrayList<Payment> getALLPaymentsBranch(String branch) {
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = PAYMENT_OFFICE + "=? ";
        String[] selectionArgs = new String[]{valueOf(branch)};

        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorAdmin(paymentArrayList, cursor);
        cursor.close();
        db.close();
        return paymentArrayList;
    }


    private void getPaymentFromCursorAdmin(ArrayList<Payment> paymentArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int profileID = cursor.getInt(1);
            int customerID = cursor.getInt(2);
            String typeOfPayment = cursor.getString(4);
            double amountPaid = cursor.getDouble(5);
            Date date= null;
            try {
                date = formatter.parse(cursor.getString(6));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String approver = cursor.getString(8);
            long code = cursor.getLong(9);
            String acctType = cursor.getString(11);
            String office = cursor.getString(12);
            String status = cursor.getString(13);
            paymentArrayList.add(new Payment(profileID, customerID, typeOfPayment,amountPaid,date,approver,code,acctType,office, status));
        }


    }
    private void getPaymentFromCursorTeller(ArrayList<Payment> paymentArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int customerID = cursor.getInt(2);
            String typeOfPayment = cursor.getString(4);
            double amountPaid = cursor.getDouble(5);
            Date date= null;
            try {
                date = formatter.parse(cursor.getString(6));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String status = cursor.getString(13);
            paymentArrayList.add(new Payment(customerID, typeOfPayment,amountPaid,date, status));
        }


    }
    public ArrayList<Payment> getTellerPayments(int profileID) {
        ArrayList<Payment> payments = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = PAYMENT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.query(PAYMENTS_TABLE, null, selection, selectionArgs, null,
                null, null);
        getPaymentFromCursorTeller(payments,cursor);

        cursor.close();
        db.close();

        return payments;
    }
    public void updateUserPassword(int userID,String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        ContentValues savingsUpdateValues2 = new ContentValues();
        savingsUpdateValues.put(PROFILE_PASSWORD, userPassword);
        savingsUpdateValues2.put(PASSWORD, userPassword);
        String selection = PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(userID)};
        String selection1 = PROF_ID_FOREIGN_KEY_PASSWORD + "=?";
        String[] selectionArgs2 = new String[]{valueOf(userID)};
        db.update(PROFILES_TABLE,
                savingsUpdateValues, selection, selectionArgs);

        db.update(PASSWORD_TABLE,
                savingsUpdateValues, selection1, selectionArgs2);
        db.close();


    }
}
