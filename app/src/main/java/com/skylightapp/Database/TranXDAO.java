package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.ChartData;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.MarketClasses.MarketTranx;

import java.util.ArrayList;

import static com.skylightapp.Classes.GroupAccount.GRPA_AMT;
import static com.skylightapp.Classes.GroupAccount.GRPA_DURATION;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_DATE;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_PROF_ID;
import static com.skylightapp.Classes.GroupSavings.GS_GRP_ACCT_ID;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static com.skylightapp.Classes.Transaction.GRP_TRANX_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TYPE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ACCT_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_AMOUNT;
import static com.skylightapp.Classes.Transaction.TRANSACTION_BIZ_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_CUS_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_DATE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_DEST_ACCT;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_MARKET_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_METHOD_OF_PAYMENT;
import static com.skylightapp.Classes.Transaction.TRANSACTION_OFFICE_BRANCH;
import static com.skylightapp.Classes.Transaction.TRANSACTION_PAYEE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_PAYER;
import static com.skylightapp.Classes.Transaction.TRANSACTION_PROF_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTION_SENDING_ACCT;
import static com.skylightapp.Classes.Transaction.TRANSACTION_STATUS;
import static com.skylightapp.Classes.Transaction.TRANS_TYPE;
import static java.lang.String.valueOf;

public class TranXDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = SO_ID
            + " =?";
    public TranXDAO(Context context) {
        super(context);
    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getTranxChartByCusID(int cusID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = TRANSACTION_CUS_ID + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + TRANSACTION_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + TRANSACTION_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(cusID)};
        String groupbyclause = "substr(" + TRANSACTION_DATE + ",4)";
        String orderbyclause = "substr(" + TRANSACTION_DATE + ",7,2)||substr(" + TRANSACTION_DATE + ",4,2)";
        Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    private Cursor getTransactionsFromCursorSimple( ArrayList<Transaction> transactions, Cursor cursor) {
        while (cursor.moveToNext()) {

            int transactionID = cursor.getInt(0);
            int profileID = cursor.getInt(1);
            int customerID = cursor.getInt(2);
            int accountNo=cursor.getInt(3);
            String timestamp = cursor.getString(4);
            String sendingAccount = cursor.getString(5);
            String destinationAccount = cursor.getString(6);
            String payee = cursor.getString(7);
            String payer = cursor.getString(8);
            double amount = cursor.getDouble(9);
            Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE.valueOf(cursor.getString(10));
            String method = cursor.getString(11);
            String officeBranch = cursor.getString(12);
            String approver = cursor.getString(13);
            String approvalDate = cursor.getString(14);
            String status = cursor.getString(15);
            transactions.add(new Transaction(transactionID, timestamp,payee,payer, amount,transactionType, method,officeBranch,status));
        }return  cursor;
    }




    private Cursor getTransactionsFromCursor( ArrayList<Transaction> transactions, Cursor cursor) {
        while (cursor.moveToNext()) {

            int transactionID = cursor.getInt(0);
            int profileID = cursor.getInt(1);
            int customerID = cursor.getInt(2);
            int accountNo=cursor.getInt(3);
            String timestamp = cursor.getString(4);
            int sendingAccount = cursor.getInt(5);
            int destinationAccount = cursor.getInt(6);
            String payee = cursor.getString(7);
            String payer = cursor.getString(8);
            double amount = cursor.getDouble(9);
            Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE.valueOf(cursor.getString(10));
            String method = cursor.getString(11);
            String officeBranch = cursor.getString(12);
            String approver = cursor.getString(13);
            String approvalDate = cursor.getString(14);
            String status = cursor.getString(15);

            transactions.add(new Transaction(transactionID, profileID,customerID,accountNo,timestamp,sendingAccount, destinationAccount,payee,payer, amount,transactionType, method,officeBranch,approver,approvalDate,status));
        }return  cursor;
    }

    @SuppressLint("Range")
    public ArrayList<ChartData> getTranxChartByTellerID(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = TRANSACTION_PROF_ID + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + TRANSACTION_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + TRANSACTION_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(tellerID)};
        String groupbyclause = "substr(" + TRANSACTION_DATE + ",4)";
        String orderbyclause = "substr(" + TRANSACTION_DATE + ",7,2)||substr(" + TRANSACTION_DATE + ",4,2)";
        Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getTranxChartByBranch(String branch){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String selection = TRANSACTION_OFFICE_BRANCH + "=?";
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] columns = new String[]{"sum(" + TRANSACTION_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + TRANSACTION_DATE + ",4) AS " + tmpcol_month_year};
        String[] selectionArgs = new String[]{valueOf(branch)};
        String groupbyclause = "substr(" + TRANSACTION_DATE + ",4)";
        String orderbyclause = "substr(" + TRANSACTION_DATE + ",7,2)||substr(" + TRANSACTION_DATE + ",4,2)";
        Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, selection,
                selectionArgs, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    @SuppressLint("Range")
    public ArrayList<ChartData> getTranxChartAll(String yearMonth){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ChartData> dataList = new ArrayList<ChartData>();
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";
        String[] selectionArgs = new String[]{valueOf(yearMonth)};
        String[] columns = new String[]{"sum(" + TRANSACTION_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + TRANSACTION_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + TRANSACTION_DATE + ",4)";
        String orderbyclause = "substr(" + TRANSACTION_DATE + ",7,2)||substr(" + TRANSACTION_DATE + ",4,2)";
        Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, groupbyclause,
                selectionArgs, groupbyclause, null, orderbyclause, null);
        if (cursor.moveToFirst()) {
            do {
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_monthly_total))));
                dataList.add(new ChartData(cursor.getString(cursor.getColumnIndex(tmpcol_month_year))));
            } while (cursor.moveToNext());
        }
        return dataList;

    }
    public double getMonthTotalTransactionForCus1(int customerID,String yearMonth) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;


        String selection = "substr(" + TRANSACTION_DATE + ",4)" + "=? AND " + TRANSACTION_CUS_ID + "=?";

        String queryString="select sum ("+ TRANSACTION_AMOUNT +") from " + TRANSACTIONS_TABLE + " WHERE " + selection;

        String[] selectionArgs = new String[]{valueOf(yearMonth), valueOf(customerID)};

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString,selectionArgs,

                null);


        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getColumnIndex(TRANSACTION_AMOUNT);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;

    }
    public double getTotalTransactionForBranchAtDate(String branchName, String theDate) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = TRANSACTION_OFFICE_BRANCH + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(theDate)};

        Cursor cursor = db.rawQuery(
                "select sum ("+ TRANSACTION_AMOUNT +") from " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs);

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
    public int getTransactionCountForCustomer(int customerID,String date) {
        int count = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = TRANSACTION_CUS_ID + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
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
    public double getTotalTransactionForCustomer(int customerID, String theDate) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = TRANSACTION_CUS_ID + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(theDate)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ TRANSACTION_AMOUNT +") from " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getDouble(0);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();
        return count;
    }
    public ArrayList<Transaction> getAllTranxWithTypeForCustomer(int customerID, String typeOfTranX) {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = TRANSACTION_CUS_ID + "=? AND " + TRANSACTIONS_TYPE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(typeOfTranX)};

        Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTransactionsFromCursor(transactionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return transactionArrayList;
    }
    public ArrayList<Transaction> getTransactionsForBranchAtDate(String officeBranch, String givenDate) {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {TRANSACTION_ID, TRANSACTIONS_TYPE,TRANSACTION_AMOUNT,TRANSACTION_PAYEE,TRANSACTION_DATE,TRANSACTION_STATUS};

        String[] selectionArgs = new String[]{valueOf(officeBranch), valueOf(givenDate)};
        String selection = TRANSACTION_OFFICE_BRANCH + "=? AND " + TRANSACTION_DATE + "=?";

        Cursor cursor = db.query(TRANSACTIONS_TABLE, columns, selection, selectionArgs, null, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTransactionsFromCursor(transactionArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();


        return transactionArrayList;

    }
    public ArrayList<Transaction> getTransactionsFromCurrentAccount(String accountNo) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TRANSACTION_ACCT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(accountNo)};
        Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTransactionsFromCursor(transactions, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return transactions;
    }
    public ArrayList<Transaction> getTransactionsToday(String today) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(today)};
        Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTransactionsFromCursor(transactions, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        return transactions;
    }

    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TRANSACTIONS_TABLE, null, null, null, null,
                null, null);

        getTransactionsFromCursor( transactions, cursor);

        cursor.close();
        //db.close();

        return transactions;
    }

    public ArrayList<Transaction> getAllTransactionsSimple() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TRANSACTIONS_TABLE, new String[]{TRANSACTION_ID, TRANSACTION_DATE, TRANSACTION_PAYEE, TRANSACTION_PAYER,
                        TRANSACTION_AMOUNT,TRANSACTIONS_TYPE,TRANSACTION_METHOD_OF_PAYMENT,TRANSACTION_OFFICE_BRANCH,TRANSACTION_STATUS}, null, null, TRANSACTIONS_TYPE,
                null, null);

        getTransactionsFromCursorSimple( transactions, cursor);

        cursor.close();
        //db.close();

        return transactions;

    }

    public ArrayList<Transaction> getBizTranxForDate(int bizID, String date) {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TRANSACTION_BIZ_ID + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID), valueOf(date)};

        String substrgDate = "substr(" + TRANSACTION_DATE + ",7,2)||substr(" + TRANSACTION_DATE + ",4,2)";
        String subToday = "substr(" + date + ",7,2)||substr(" + date + ",4,2)";

        String select = TRANSACTION_BIZ_ID + "=? AND " + substrgDate + "=?";

        String[] selArgs = new String[] {valueOf(bizID),subToday};

        Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getTransactionsFromCursor(transactionArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return transactionArrayList;
    }
    public int getTellerTxForToday(int tellerID,String txDate) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRANSACTION_PROF_ID + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID), valueOf(txDate)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TRANSACTION_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;

    }
    public int getAllTxCountForToday(String txDate) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(txDate)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TRANSACTION_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;


    }


    public ArrayList<Transaction> getAllTransactionCustomer(int customerID) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TRANSACTION_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTransactionsFromCursorAdmin(transactions, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return transactions;
    }

    private void getTransactionsFromCursorGrp(ArrayList<Transaction> transactions, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String type = cursor.getString(3);
                double amount = cursor.getDouble(4);
                String endDate = cursor.getString(5);
                String methodOfPayment = cursor.getString(6);
                int sendingAcct = cursor.getInt(7);
                int receivingAcct = cursor.getInt(8);
                String status = cursor.getString(9);
                transactions.add(new Transaction(id,type,amount,endDate,methodOfPayment,sendingAcct,receivingAcct,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        // returns true if pointed to a record

    }
    public ArrayList<Transaction> getAllGrpAcctTranxs(long grpAcctID) {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(GRP_TRANX_TABLE, null,  GRP_ACCT_ID
                            + " = " + grpAcctID, null, null,
                    null, null);
            if (cursor != null)
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    getTransactionsFromCursorGrp(transactions, cursor);
                    cursor.close();
                }

            db.close();

            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public ArrayList<Transaction> getAllTransactionProfile(int profileID) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TRANSACTION_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};

        Cursor cursor = db.query(TRANSACTIONS_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTransactionsFromCursorAdmin(transactions, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return transactions;
    }
    public ArrayList<Transaction> getAllTranxForMarket(int marketID) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TRANSACTION_MARKET_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(marketID)};

        Cursor cursor = db.query(TRANSACTIONS_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTransactionsFromCursorAdmin(transactions, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return transactions;
    }
    public ArrayList<Transaction> getAllTranxForBiz(long marketBizID) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TRANSACTION_BIZ_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(marketBizID)};

        Cursor cursor = db.query(TRANSACTIONS_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTransactionsFromCursorAdmin(transactions, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return transactions;
    }
    public ArrayList<Transaction> getTranxForBizForDate(long marketBizID,String dateOfTranx) {
        String selection = TRANSACTION_BIZ_ID + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(marketBizID), valueOf(dateOfTranx)};
        ArrayList<Transaction> transactionArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TRANSACTIONS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getTransactionsFromCursorAdmin(transactionArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return transactionArrayList;
    }
    public ArrayList<Transaction> getAllTransactionAdmin() {
        try {
            ArrayList<Transaction> transactions = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(TRANSACTIONS_TABLE, null, null, null, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getTransactionsFromCursorAdmin(transactions, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }


            return transactions;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public int getTransactionCountForBranchAtDate(String branch,String date) {
        int count = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = TRANSACTION_OFFICE_BRANCH + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branch), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
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


    private void getTransactionsFromCursorAdmin(ArrayList<Transaction> transactions, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String date = cursor.getString(4);
                int sendingAcct = cursor.getInt(5);
                int destination = cursor.getInt(6);
                String payee = cursor.getString(7);
                String payer = cursor.getString(8);
                double amount = cursor.getDouble(9);
                String type = cursor.getString(10);
                String method = cursor.getString(15);
                String status = cursor.getString(15);
                transactions.add(new Transaction(id,type,method,date,sendingAcct,destination,amount,payee,payer,status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public int getCustomerTxForToday(int customerID,String txDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRANSACTION_CUS_ID + "=? AND " + TRANSACTION_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID), valueOf(txDate)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TRANSACTION_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public int getTxCountCustomer(int customerID) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRANSACTION_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TRANSACTION_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }


    private Cursor getTransactionsFromCustomerCursor(int customerID, ArrayList<Transaction> transactions, Cursor cursor) {
        while (cursor.moveToNext()) {

            if (customerID == cursor.getInt(2)) {
                int transactionID = cursor.getInt(0);
                int accountNo = cursor.getInt(3);
                String timestamp = cursor.getString(4);
                int sendingAccount = cursor.getInt(5);
                int destinationAccount = cursor.getInt(6);
                double amount = cursor.getDouble(9);
                Transaction.TRANSACTION_TYPE transactionType = Transaction.TRANSACTION_TYPE.valueOf(cursor.getString(10));

                if (transactionType == Transaction.TRANSACTION_TYPE.PAYMENT) {
                    transactions.add(new Transaction(transactionID, timestamp, amount));
                } else if (transactionType == Transaction.TRANSACTION_TYPE.TRANSFER) {
                    transactions.add(new Transaction(transactionID, timestamp, sendingAccount, destinationAccount, amount));
                } else if (transactionType == Transaction.TRANSACTION_TYPE.DEPOSIT) {
                    transactions.add(new Transaction(transactionID, timestamp, amount));
                }

            }
        }return  cursor;
    }
    public int getCustomerTotalTXCount(int customerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRANSACTION_CUS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(customerID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;

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

    public int getTxCountTeller(int profileID) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String selection = TRANSACTION_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " + selection,
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
    public void updateTransactionStatus(String status, Transaction transaction) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int transactionID = transaction.getTransactionID();
        contentValues.put(TRANSACTION_STATUS, status);
        String selection = TRANSACTION_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(transactionID)};
        sqLiteDatabase.update(TRANSACTIONS_TABLE, contentValues, selection,
                selectionArgs);

    }
    public void updateTransactionStatus(String status, int transactionID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRANSACTION_STATUS, status);
        String selection = TRANSACTION_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(transactionID)};
        sqLiteDatabase.update(TRANSACTIONS_TABLE, contentValues, selection,
                selectionArgs);


    }



    public ArrayList<String> getTransactions() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor res = db.rawQuery("select * from TRANSACTIONS_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(0));
                res.moveToNext();
            }
        }
        return array_list;

    }

    public long insertBillsTX(int profileID, String label, String billerName, double amount, String itemCode, String selectedCountry,String reference, String subDate) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            String details= label + ""+ billerName +","+itemCode+","+ selectedCountry;
            ContentValues contentValues = new ContentValues();
            contentValues.put(TRANSACTION_ID, reference);
            contentValues.put(PROFILE_ID, profileID);
            //contentValues.put(TRANSACTIONS_TYPE, String.valueOf(Transaction.TRANSACTION_TYPE.BILLSUBCRIPTION));
            contentValues.put(TRANSACTION_AMOUNT, amount);
            contentValues.put(TRANSACTION_STATUS, details);
            contentValues.put(TRANSACTION_DATE, subDate);
            sqLiteDatabase.insert(TRANSACTIONS_TABLE, null, contentValues);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return Long.parseLong(reference);
    }

    public long saveNewTransaction(int profileID, int customerId,Transaction transaction, int accountId,String payee,String payer,Transaction.TRANSACTION_TYPE type, double amount, long transactionId,  String officeBranch,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        co.paystack.android.Transaction transaction2 = new co.paystack.android.Transaction();
        ContentValues cv = new ContentValues();
        cv.put(TRANSACTION_PROF_ID, profileID);
        cv.put(TRANSACTION_CUS_ID, customerId);
        cv.put(TRANSACTION_ACCT_ID, accountId);
        cv.put(TRANSACTION_AMOUNT, amount);
        cv.put(TRANSACTION_ID, transactionId);
        cv.put(TRANSACTIONS_TYPE, String.valueOf(type));
        cv.put(TRANSACTION_PAYEE, payee);
        cv.put(TRANSACTION_PAYER, payer);
        cv.put(TRANSACTION_OFFICE_BRANCH, officeBranch);
        cv.put(TRANSACTION_DATE, date);

        if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.MANUAL_WITHDRAWAL) {
            cv.put(TRANSACTION_SENDING_ACCT, "Awajima");
            cv.put(TRANSACTION_DEST_ACCT, transaction.getTranxDestAcct());
        } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.LOAN) {
            cv.put(TRANSACTION_SENDING_ACCT, "Awajima");
            cv.put(TRANSACTION_DEST_ACCT, transaction.getTranxDestAcct());
            cv.put(TRANSACTION_PAYEE, transaction.getTranxPayee());
        } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.GROUP_SAVINGS_WITHDRAWAL) {
            cv.put(TRANSACTION_SENDING_ACCT, transaction.getTranxSendingAcct());
            cv.put(TRANSACTION_DEST_ACCT, transaction.getTranxDestAcct());
        } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.STANDING_ORDER) {
            cv.put(TRANSACTION_PAYEE, transaction.getTranxPayee());
        } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.GROUP_SAVINGS_DEPOSIT) {
            cv.put(TRANSACTION_PAYEE, transaction.getTranxPayee());
        } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
            //cv.putNull(SENDING_ACCOUNT);
            //cv.putNull(DESTINATION_ACCOUNT);
            cv.put(TRANSACTION_PAYEE, transaction.getTranxPayee());
        } else if (transaction.getTranXType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
            cv.put(TRANSACTION_DEST_ACCT, transaction.getTranxDestAcct());
            //cv.putNull(SENDING_ACCOUNT);
            //cv.putNull(DESTINATION_ACCOUNT);
            //cv.putNull(TRANSACTION_PAYEE);
        }
        cv.put(TRANSACTION_AMOUNT, transaction.getTranxAmount());
        cv.put(TRANS_TYPE, transaction.getTranXType().toString());

        db.insert(TRANSACTIONS_TABLE, null, cv);

        //db.close();

        return profileID;
    }
    public long savePaymentTransaction(String response) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TRANSACTION_STATUS, response);
        return  db.insert(TRANSACTIONS_TABLE, null, cv);

    }
    public long savePaymentTransactionP(int profileID4, int customerID, Transaction.TRANSACTION_TYPE type, String response) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Customer customer = new Customer();
        Profile profile = new Profile();
        Transaction transaction = new Transaction();
        cv.put(TRANSACTION_PROF_ID, profile.getPID());
        cv.put(TRANSACTION_CUS_ID, customer.getCusUID());
        cv.put(TRANSACTIONS_TYPE, String.valueOf(transaction.getTranXType()));
        cv.put(TRANSACTION_STATUS, response);
        return db.insert(TRANSACTIONS_TABLE, null, cv);
    }
    public int getCusMonthTransactionCount1(int customerID, String stringDate) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String sel = "substr(" + stringDate + ",4)";
        String seler = "substr(" + TRANSACTION_DATE + ",4)";
        String selection = TRANSACTION_CUS_ID + "=? AND " + seler + "=?";
        String[] selectionArgs = new String[] {valueOf(customerID),valueOf(sel)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TRANSACTIONS_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TRANSACTION_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }




}
