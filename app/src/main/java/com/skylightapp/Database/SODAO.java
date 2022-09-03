package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Classes.Utils;

import java.math.BigInteger;
import java.util.ArrayList;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Profile.PROFILE_FIRSTNAME;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.Profile.PROFILE_SURNAME;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_CUSTOMER_ID_FOREIGN;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TABLE;
import static com.skylightapp.Classes.StandingOrder.SO_ACCT_NO;
import static com.skylightapp.Classes.StandingOrder.SO_AMOUNT_DIFF;
import static com.skylightapp.Classes.StandingOrder.SO_CUS_ID;
import static com.skylightapp.Classes.StandingOrder.SO_DAILY_AMOUNT;
import static com.skylightapp.Classes.StandingOrder.SO_DAYS_REMAINING;
import static com.skylightapp.Classes.StandingOrder.SO_END_DATE;
import static com.skylightapp.Classes.StandingOrder.SO_EXPECTED_AMOUNT;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static com.skylightapp.Classes.StandingOrder.SO_PROF_ID;
import static com.skylightapp.Classes.StandingOrder.SO_RECEIVED_AMOUNT;
import static com.skylightapp.Classes.StandingOrder.SO_START_DATE;
import static com.skylightapp.Classes.StandingOrder.SO_STATUS;
import static com.skylightapp.Classes.StandingOrder.STANDING_ORDER_TABLE;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCOUNT_BALANCE;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCOUNT_NAME;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCOUNT_NO;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCT_TABLE;
import static com.skylightapp.Classes.TransactionGranting.TANSACTION_EXTRA_CUSTOMER_ID;
import static java.lang.String.valueOf;

public class SODAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = SO_ID
            + " =?";
    public SODAO(Context context) {
        super(context);
    }
    public ArrayList<StandingOrder> getSOEndingCustomDay(String customEndDate) {
        ArrayList<StandingOrder> standingOrderArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SO_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customEndDate)};

        Cursor cursor = db.query(STANDING_ORDER_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStandingOrdersFromCursor(standingOrderArrayList,cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return standingOrderArrayList;
    }
    public void updateSOAcctBalance(int soAcctID,double newBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SO_ACCT_NO + "=?";
        String[] selectionArgs = new String[]{valueOf(soAcctID)};
        ContentValues documentUpdateValues = new ContentValues();
        documentUpdateValues.put(SO_ACCOUNT_BALANCE, newBalance);
        db.update(SO_ACCT_TABLE, documentUpdateValues, selection, selectionArgs);
        db.close();

    }
    public int getCusMonthSOCount1(int customerID,String monthYear) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String selection = "substr(" + SO_START_DATE + ",4)" + "=? AND " + TANSACTION_EXTRA_CUSTOMER_ID + "=?";

        String queryString="select COUNT ("+ SO_ID +") from " + STANDING_ORDER_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(monthYear), valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(SO_START_DATE);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;


    }
    public int getStandingOrderCountToday(String today) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = SO_START_DATE + "=?";
        String[] selectionArgs = new String[]{today};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                count = cursor.getInt(0);
            }
        if (cursor != null) {
            cursor.close();
        }
        return count;
    }
    public ArrayList<StandingOrder> getAllStandingOrdersWithStatus(String completedStatus) {
        ArrayList<StandingOrder> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {SO_ID, SO_ACCOUNT_NAME, SO_START_DATE,SO_DAILY_AMOUNT,SO_EXPECTED_AMOUNT,SO_AMOUNT_DIFF,SO_ACCOUNT_BALANCE,SO_END_DATE};


        String selection = SO_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(completedStatus)};

        Cursor cursor = db.query(SO_ACCT_TABLE, columns,  selection, selectionArgs, null, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStandingOrdersFromCursor(standingOrders, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return standingOrders;

    }
    public ArrayList<StandingOrder> getAllStandingOrdersForCustomerDate(int customerID, String date) {
        ArrayList<StandingOrder> standingOrders = new ArrayList<StandingOrder>();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {SO_DAILY_AMOUNT, SO_EXPECTED_AMOUNT,SO_RECEIVED_AMOUNT,SO_DAYS_REMAINING,SO_END_DATE,SO_STATUS};


        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(date)};

        Cursor cursor = db.query(
                STANDING_ORDER_TABLE + " , " + PACKAGE_TABLE,
                Utils.concat(new String[]{STANDING_ORDER_TABLE, PACKAGE_TABLE}),
                SO_CUS_ID + " = " + PACKAGE_CUSTOMER_ID_FOREIGN + " AND " + SO_START_DATE + " = " +  date,
                null, null, null, null);

        while (cursor.moveToNext()) {
            StandingOrder standingOrder = new StandingOrder();
            Transaction transaction = standingOrder.getSo_Tranx();
            standingOrder.setSoDailyAmount(cursor.getDouble(1));
            standingOrder.setSoExpectedAmount(cursor.getDouble(3));
            standingOrder.setSoReceivedAmount(cursor.getInt(4));
            standingOrder.setSo_TotalDays(cursor.getInt(5));
            standingOrder.setSo_DaysRemaining(cursor.getInt(7));
            standingOrder.setSoStatus(cursor.getString(8));
            standingOrder.setSoStartDate(cursor.getString(10));
            standingOrder.setSoEndDate(cursor.getString(11));
            transaction.setTranxApprovalDate(cursor.getString(12));
            transaction.setTransactionStatus(cursor.getString(13));
            standingOrders.add(standingOrder);
        }
        return standingOrders;
    }
    public ArrayList<StandingOrder> getStandingOrdersToday(String todayDate) {

        ArrayList<StandingOrder> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {SO_CUS_ID,SO_DAILY_AMOUNT, SO_EXPECTED_AMOUNT,SO_RECEIVED_AMOUNT,SO_DAYS_REMAINING};
        String selection = SO_START_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate)};
        Cursor cursor = db.query(STANDING_ORDER_TABLE, columns, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStandingOrdersFromCursor(standingOrders, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return standingOrders;
    }
    public int getAllStandingOrdersWithStatusCount(String Completed) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String selection = SO_STATUS + "=?";
        String[] selectionArgs = new String[]{valueOf(Completed)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;

    }
    public int getDueSOTodayCount(String todayDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;


        String selection = SO_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;

    }
    public int getDueSOCustomCount(String customDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;


        String selection = SO_END_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customDate)};

        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getInt(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;

    }
    public BigInteger getSoValue(int customerID){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",SO_RECEIVED_AMOUNT,STANDING_ORDER_TABLE);
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",LOAN_AMOUNT,LOAN_TABLE);

        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ SO_DAILY_AMOUNT +")) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    sum = cursor.getColumnIndex(SO_DAILY_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return BigInteger.valueOf(sum);

    }
    public BigInteger getSOReceivedValue(int customerID){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",SO_RECEIVED_AMOUNT,STANDING_ORDER_TABLE);
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",LOAN_AMOUNT,LOAN_TABLE);

        String[] columns = {PROFILE_SURNAME, PROFILE_FIRSTNAME, PROFILE_PHONE};

        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ SO_RECEIVED_AMOUNT +")) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    sum = cursor.getColumnIndex(SO_RECEIVED_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return BigInteger.valueOf(sum);

    }
    private void getStandingOrdersFromCursor(ArrayList<StandingOrder> standingOrders, Cursor cursor) {
        while (cursor.moveToNext()) {
            int uID = cursor.getInt(0);
            int customerID = cursor.getInt(2);
            int so_Acct_No = cursor.getInt(9);
            double soDailyAmount = Double.parseDouble(cursor.getString(1));
            double expectedAmount = Double.parseDouble(cursor.getString(3));
            double amountDiff = Double.parseDouble(cursor.getString(6));
            double receivedAmount = Double.parseDouble(cursor.getString(4));
            String soStatus = cursor.getString(8);
            String so_start_date = cursor.getString(10);
            String so_end_date = cursor.getString(11);

            standingOrders.add(new StandingOrder(uID, so_Acct_No, soDailyAmount,so_start_date,expectedAmount, receivedAmount,amountDiff,soStatus,so_end_date));
        }

    }

    public ArrayList<StandingOrder> getAllSOEndingToday(String soEndDate) {
        ArrayList<StandingOrder> orderArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SO_END_DATE + "=?";
        String[] selectionArgs = new String[]{(soEndDate)};
        String[] column = {SO_ID, CUSTOMER_ID, SO_DAILY_AMOUNT,SO_RECEIVED_AMOUNT,SO_AMOUNT_DIFF,SO_ACCT_NO,SO_STATUS,SO_START_DATE,SO_END_DATE};

        Cursor cursor = db.query(STANDING_ORDER_TABLE, column, selection, selectionArgs, null, null, null);

        getStandingOrdersFromCursor(orderArrayList, cursor);
        cursor.close();
        db.close();

        return orderArrayList;
    }



    public ArrayList<StandingOrder> getAllStandingOrders11() {
        ArrayList<StandingOrder> orderArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        //String selection = "SO_END_DATE=?";
        //String[] selectionArgs = {SO_ID, CUSTOMER_ID, SO_DAILY_AMOUNT,SO_RECEIVED_AMOUNT,SO_AMOUNT_DIFF,SO_ACCT_NO,SO_STATUS,SO_START_DATE,SO_END_DATE};

        Cursor cursor = db.query(STANDING_ORDER_TABLE, null, null, null, null,null , null);

        getStandingOrdersFromCursor(orderArrayList, cursor);
        cursor.close();
        db.close();

        return orderArrayList;
    }
    public ArrayList<StandingOrder> getAllStandingOrdersForCustomer(int customerID) {
        ArrayList<StandingOrder> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = customerID + "=?";
        String[] selectionArgs = new String[]{String.valueOf((customerID))};

        Cursor cursor = db.query(STANDING_ORDER_TABLE, null, selection, selectionArgs, null,
                null, null);
        getStandingOrdersFromCursor(standingOrders, cursor);

        cursor.close();
        db.close();

        return standingOrders;
    }


    public void updateStandingOrder(int uID, String soStatus, double receivedAmount, double amountDiff) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SO_ID, uID);
        contentValues.put(SO_RECEIVED_AMOUNT, receivedAmount);
        contentValues.put(SO_AMOUNT_DIFF, amountDiff);
        contentValues.put(SO_STATUS, soStatus);
        String selection = SO_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(uID)};
        db.update(STANDING_ORDER_TABLE, contentValues, selection, selectionArgs);

    }


    public long insertStandingOrder(StandingOrder standingOrder, int customerID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Customer customer = new Customer();
        customerID=customer.getCusUID();
        int uID=standingOrder.getUID();
        long so_Acct_No=standingOrder.getSo_Acct_No();
        String so_start_date =standingOrder.getSoStartDate();
        String soStatus =standingOrder.getSoStatus();
        double expectedAmount =standingOrder.getSo_ExpectedAmount();
        double receivedAmount =standingOrder.getSo_ReceivedAmount();
        double amountDiff =standingOrder.getSo_AmountDiff();
        double soDailyAmount =standingOrder.getSoDailyAmount();
        String so_end_date =standingOrder.getSoEndDate();

        ContentValues contentValues = new ContentValues();
        contentValues.put(SO_ID, uID);
        contentValues.put(SO_CUS_ID, customerID);
        contentValues.put(SO_DAILY_AMOUNT, soDailyAmount);
        contentValues.put(SO_EXPECTED_AMOUNT, expectedAmount);
        contentValues.put(SO_RECEIVED_AMOUNT, receivedAmount);
        contentValues.put(SO_AMOUNT_DIFF, amountDiff);
        contentValues.put(SO_ACCT_NO, so_Acct_No);
        contentValues.put(SO_STATUS, soStatus);
        contentValues.put(SO_START_DATE, so_start_date);
        contentValues.put(SO_END_DATE, so_end_date);
        return sqLiteDatabase.insert(STANDING_ORDER_TABLE, null, contentValues);

    }

    public long insertStandingOrder(int profileID, int customerID, int soID, long soAcctNo,double amountCarriedForward, String currentDate, double expectedAmount, double sONowAmount, double amtDiff, int totalDays, int daysRemaining, String endDate, String inProgress) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        StandingOrderAcct standingOrderAcct = new StandingOrderAcct();
        contentValues.put(SO_PROF_ID, profileID);
        contentValues.put(SO_CUS_ID, customerID);
        contentValues.put(SO_ID, soID);
        contentValues.put(SO_DAILY_AMOUNT, amountCarriedForward);
        contentValues.put(SO_EXPECTED_AMOUNT, expectedAmount);
        contentValues.put(SO_RECEIVED_AMOUNT, sONowAmount);
        contentValues.put(SO_AMOUNT_DIFF, amtDiff);
        contentValues.put(SO_ACCT_NO, soAcctNo);
        contentValues.put(SO_STATUS, inProgress);
        contentValues.put(SO_START_DATE, currentDate);
        contentValues.put(SO_END_DATE, endDate);
        return sqLiteDatabase.insert(STANDING_ORDER_TABLE, null, contentValues);

    }
    public long insertStandingOrderAcct(int profileID,int customerID ,int soAcctID, String soAcctName, Double soAcctBalance) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SO_PROF_ID, profileID);
        contentValues.put(SO_CUS_ID, customerID);
        contentValues.put(SO_ACCOUNT_NO, soAcctID);
        contentValues.put(SO_ACCOUNT_NAME, soAcctName);
        contentValues.put(SO_ACCOUNT_BALANCE, soAcctBalance);
        return sqLiteDatabase.insert(SO_ACCT_TABLE, null, contentValues);


    }
    @SuppressLint("Recycle")
    public int getSOCountAdmin() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=null;

        String countQuery = "SELECT  * FROM " + STANDING_ORDER_TABLE;

        try {
            cursor = db.rawQuery(countQuery, null);
            return cursor.getCount();

        } catch (RuntimeException e) {
            e.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }


        return 0;
    }
    public int getCustomerSOCountForDate(int customerID, String date) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = SO_CUS_ID + "=? AND " + SO_START_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection,
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
    public int getSOCountCustomer(int customerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;

        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection,
                selectionArgs
        );



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
    public int getCustomerSOCount(int customerId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerId)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(SO_START_DATE);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    public ArrayList<StandingOrder> getSOFromCurrentCustomer(int customerID) {
        ArrayList<StandingOrder> standingOrders = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(STANDING_ORDER_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getStandingOrdersFromCursor(standingOrders,cursor);

                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return standingOrders;
    }

    public BigInteger getSOExpectedValue(int customerID){
        int sum=0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",SO_RECEIVED_AMOUNT,STANDING_ORDER_TABLE);
        //String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s",LOAN_AMOUNT,LOAN_TABLE);

        String selection = SO_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT SUM (("+ SO_EXPECTED_AMOUNT +")) FROM " + STANDING_ORDER_TABLE + " WHERE " + selection, selectionArgs);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    sum = cursor.getColumnIndex(SO_EXPECTED_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        sqLiteDatabase.close();

        return BigInteger.valueOf(sum);

    }




}
