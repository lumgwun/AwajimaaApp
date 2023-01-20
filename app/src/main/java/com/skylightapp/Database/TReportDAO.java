package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.TellerCountData;
import com.skylightapp.Classes.TellerReport;

import java.util.ArrayList;
import java.util.Date;

import static com.skylightapp.Classes.CustomerDailyReport.REPORT_PROF_ID_FK;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_ADMIN;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_ADMIN_ID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_AMOUNT_SUBMITTED;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_AMT_PAID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_APPROVAL_DATE;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_BALANCE;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_BIZ_NAME;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_BRANCH;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_DATE;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_EXPECTED_AMT;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_ID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_MARKETER;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_NO_OF_SAVINGS;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_OFFICE_ID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_PROF_ID;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_STATUS;
import static com.skylightapp.Classes.TellerReport.TELLER_REPORT_TABLE;
import static java.lang.String.valueOf;

public class TReportDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = TELLER_REPORT_ID
            + " =?";
    private SQLiteDatabase readableDatabase;

    public TReportDAO(Context context) {
        super(context);
    }

    public int getTellerReportCountForDate(int profileID,String date) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = REPORT_PROF_ID_FK + "=? AND " + TELLER_REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TELLER_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TELLER_REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    public int getTellerReportCountAll(int profileID) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = REPORT_PROF_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TELLER_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        int count = 0;
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TELLER_REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }
    @SuppressLint("Range")
    public ArrayList<TellerCountData> getTellerMonthTReport(int tellerID){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<TellerCountData> countData = new ArrayList<TellerCountData>();
        TellerCountData tellerCountData=null;
        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        String[] columns = new String[]{"sum(" + TELLER_REPORT_AMT_PAID + ") AS " + tmpcol_monthly_total, "substr(" + TELLER_REPORT_DATE + ",4) AS " + tmpcol_month_year};
        String groupbyclause = "substr(" + TELLER_REPORT_DATE + ",4)";
        String orderbyclause = "substr(" + TELLER_REPORT_DATE + ",7,2)||substr(" + TELLER_REPORT_DATE + ",4,2)";

        String selection = TELLER_REPORT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID)};

        Cursor cursor = db.query(TELLER_REPORT_TABLE, columns, selection, selectionArgs, groupbyclause,
                null, orderbyclause);

        if(cursor.moveToFirst()) {
            do{
                tellerCountData=new TellerCountData();
                tellerCountData.setTellerID(cursor.getColumnIndex(PROFILE_ID));
                tellerCountData.setTellerName(cursor.getColumnName(12));
                tellerCountData.setCountData(cursor.getColumnIndex(tmpcol_monthly_total));
                tellerCountData.setCountDuration(String.valueOf(cursor.getColumnIndex(tmpcol_month_year)));
                countData.add(tellerCountData);
            }
            while(cursor.moveToNext());
        }


        return countData;

    }
    public void updateTellerReport(int tellerReportID, int tellerID,String admin, int noOfCustomers, double amountExpected, double amountEntered,String updateDate, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        double balance=amountExpected-amountEntered;
        savingsUpdateValues.put(TELLER_REPORT_AMOUNT_SUBMITTED, amountEntered);
        savingsUpdateValues.put(TELLER_REPORT_NO_OF_SAVINGS, noOfCustomers);
        savingsUpdateValues.put(TELLER_REPORT_EXPECTED_AMT, amountExpected);
        savingsUpdateValues.put(TELLER_REPORT_APPROVAL_DATE, updateDate);
        savingsUpdateValues.put(TELLER_REPORT_ADMIN, admin);
        savingsUpdateValues.put(TELLER_REPORT_BALANCE, balance);
        savingsUpdateValues.put(TELLER_REPORT_STATUS, status);
        String selection = TELLER_REPORT_ID + "=? AND " + TELLER_REPORT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerReportID), valueOf(tellerID)};
        db.update(TELLER_REPORT_TABLE,
                savingsUpdateValues, selection, selectionArgs);
        //db.update(TELLER_REPORT_TABLE, savingsUpdateValues, TELLER_REPORT_ID + " = ?", new String[]{valueOf(tellerReportID)});
        db.close();



    }
    public long insertTellerReport2(int tellerID,int officeBranchID, String dateOfReport, String officeBranch, double amountEntered, int noOfCustomers,String cmName,long bizID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long tellerReportID=0;
        values.put(TELLER_REPORT_OFFICE_ID, officeBranchID);
        values.put(TELLER_REPORT_ID, tellerReportID);
        values.put(TELLER_REPORT_ADMIN_ID, "");
        values.put(TELLER_REPORT_PROF_ID, tellerID);
        values.put(TELLER_REPORT_DATE, dateOfReport);
        values.put(TELLER_REPORT_AMOUNT_SUBMITTED, amountEntered);
        values.put(TELLER_REPORT_NO_OF_SAVINGS, noOfCustomers);
        values.put(TELLER_REPORT_BALANCE, "");
        values.put(TELLER_REPORT_AMT_PAID, "");
        values.put(TELLER_REPORT_EXPECTED_AMT, "");
        values.put(TELLER_REPORT_BRANCH, officeBranch);
        values.put(TELLER_REPORT_ADMIN, cmName);
        values.put(TELLER_REPORT_MARKETER, cmName);
        values.put(TELLER_REPORT_BIZ_NAME, bizID);
        values.put(TELLER_REPORT_STATUS, "");
        db.insert(TELLER_REPORT_TABLE,null,values);

        return tellerReportID;
    }

    public long insertTellerReport1(int officeBranchID,int adminID, int tellerID, String reportDate,double reportAmount,int noOfSavings,double reportBalance, double amtPaid,double expectedAmt,String branchName,String manager,String reportStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int tellerReportID=0;
        values.put(TELLER_REPORT_OFFICE_ID, officeBranchID);
        values.put(TELLER_REPORT_ID, tellerReportID);
        values.put(TELLER_REPORT_ADMIN_ID, adminID);
        values.put(TELLER_REPORT_PROF_ID, tellerID);
        values.put(TELLER_REPORT_DATE, String.valueOf(reportDate));
        values.put(TELLER_REPORT_AMOUNT_SUBMITTED, reportAmount);
        values.put(TELLER_REPORT_NO_OF_SAVINGS, noOfSavings);
        values.put(TELLER_REPORT_BALANCE, reportBalance);
        values.put(TELLER_REPORT_AMT_PAID, amtPaid);
        values.put(TELLER_REPORT_EXPECTED_AMT, expectedAmt);
        values.put(TELLER_REPORT_BRANCH, branchName);
        values.put(TELLER_REPORT_ADMIN, manager);
        values.put(TELLER_REPORT_MARKETER, manager);
        values.put(TELLER_REPORT_STATUS, reportStatus);
        db.insert(TELLER_REPORT_TABLE,null,values);

        return tellerReportID;
    }
    public int getTellerReportCountTodayForBranch(String branchName,String today) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TELLER_REPORT_BRANCH + "=? AND " + TELLER_REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + TELLER_REPORT_TABLE + " WHERE " + selection,
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
        db.close();
        return count;

    }

    public Cursor readTellerReport() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                TELLER_REPORT_DATE,
                TELLER_REPORT_AMOUNT_SUBMITTED,
                TELLER_REPORT_NO_OF_SAVINGS,
                TELLER_REPORT_BALANCE,
                TELLER_REPORT_AMT_PAID,
                TELLER_REPORT_EXPECTED_AMT,
                TELLER_REPORT_BRANCH,
                TELLER_REPORT_ADMIN,
                TELLER_REPORT_MARKETER,
                TELLER_REPORT_STATUS
        };
        Cursor cursor = db.query(
                TELLER_REPORT_TABLE,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }
    public void updateAParticularTellerReport(int tellerID, int currentReportID, TellerReport tellerReport,double amtSubmitted,String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        double expectedAmt=tellerReport.getTr_AmtExpected();
        double amtPaid=tellerReport.getTr_AmtPaid();
        double balance=expectedAmt-amtPaid-amtSubmitted;
        values.put(TELLER_REPORT_AMOUNT_SUBMITTED, amtSubmitted);
        values.put(TELLER_REPORT_BALANCE, balance);
        values.put(TELLER_REPORT_EXPECTED_AMT, expectedAmt);
        values.put(TELLER_REPORT_AMT_PAID, amtPaid);
        values.put(TELLER_REPORT_STATUS, status);
        String selection = TELLER_REPORT_ID + "=? AND " + TELLER_REPORT_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(currentReportID), valueOf(tellerID)};
        db.update(TELLER_REPORT_TABLE,
                values, selection, selectionArgs);
    }











    public double getTotalTellerReportAmountSubmittedTodayForBranch(String branchName, Date today) {

        SQLiteDatabase db = this.getReadableDatabase();
        double count = 0.00;
        String selection = TELLER_REPORT_BRANCH + "=? AND " + TELLER_REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName), valueOf(today)};
        Cursor cursor = db.rawQuery(
                "select sum ("+ TELLER_REPORT_AMOUNT_SUBMITTED +") from " + TELLER_REPORT_TABLE + " WHERE " + selection,
                selectionArgs
        );
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(TELLER_REPORT_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return count;
    }

    public Cursor readAParticularTellerReport(int tellerReportID,int tellerID) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = TELLER_REPORT_ID + "=? AND " + REPORT_PROF_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerReportID), valueOf(tellerID)};

        try {
            String[] projection = {
                    TELLER_REPORT_DATE,
                    TELLER_REPORT_AMOUNT_SUBMITTED,
                    TELLER_REPORT_NO_OF_SAVINGS,
                    TELLER_REPORT_BALANCE,
                    TELLER_REPORT_AMT_PAID,
                    TELLER_REPORT_EXPECTED_AMT,
                    TELLER_REPORT_BRANCH,
                    TELLER_REPORT_ADMIN,
                    TELLER_REPORT_MARKETER,
                    TELLER_REPORT_STATUS
            };

            Cursor cursor = db.query(
                    TELLER_REPORT_TABLE,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            return cursor;
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<TellerReport> getTellerReportBiz(long bizID) {
        ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_REPORT_BIZ_NAME + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID)};
        Cursor cursor = db.query(TELLER_REPORT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerReportFromCursor(tellerReportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tellerReportArrayList;
    }

    public ArrayList<TellerReport> getTellerReportsAll() {
        ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TELLER_REPORT_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerReportFromCursor(tellerReportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        db.close();

        return tellerReportArrayList;
    }
    private void getTellerReportFromCursor(ArrayList<TellerReport> tellerReportArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int tellerReportId = cursor.getInt(0);
            String date=cursor.getString(4);
            double amount = cursor.getDouble(5);
            int noOfSaving = cursor.getInt(6);
            double balance = cursor.getDouble(7);
            double amountPaid = cursor.getDouble(8);
            double expectedAmount = cursor.getDouble(9);
            String marketer = cursor.getString(11);
            String status = cursor.getString(12);
            tellerReportArrayList.add(new TellerReport(tellerReportId, date, amount,noOfSaving,balance,amountPaid,expectedAmount,marketer, status));
        }


    }

    public ArrayList<TellerReport> getTellerReportForABranch(String branchName) {
        ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_REPORT_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(branchName)};
        Cursor cursor = db.query(TELLER_REPORT_TABLE, null, selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerReportFromCursor(tellerReportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tellerReportArrayList;
    }


    public void deleteTellerReport(int tellerReportID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_REPORT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerReportID)};
        db.delete(TELLER_REPORT_TABLE, selection, selectionArgs);

    }
    private void getTellerReportFromCursorSuper(ArrayList<TellerReport> tellerReportArrayList, Cursor cursor) {
        try {

            while (cursor.moveToNext()) {

                int tellerReportId = cursor.getInt(0);
                String date=cursor.getString(4);
                double amount = cursor.getDouble(5);
                int noOfSaving = cursor.getInt(6);
                double balance = cursor.getDouble(7);
                double amountPaid = cursor.getDouble(8);
                double expectedAmount = cursor.getDouble(9);
                String marketer = cursor.getString(11);
                String status = cursor.getString(12);
                tellerReportArrayList.add(new TellerReport(tellerReportId, date, amount,noOfSaving,balance,amountPaid,expectedAmount,marketer, status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public ArrayList<TellerReport> getTellerReportsForBranch(String todayDate,String officeBranch) {
        ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TELLER_REPORT_DATE + "=? AND " + TELLER_REPORT_BRANCH + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate), valueOf(officeBranch)};
        Cursor cursor = db.query(TELLER_REPORT_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerReportFromCursor(tellerReportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tellerReportArrayList;

    }
    public ArrayList<TellerReport> getTellerReportsForDate(int profileID,String date) {
        ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REPORT_PROF_ID_FK + "=? AND " + TELLER_REPORT_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profileID), valueOf(date)};
        Cursor cursor = db.query(TELLER_REPORT_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerReportFromCursor(tellerReportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        db.close();

        return tellerReportArrayList;
    }
    public ArrayList<TellerReport> getTellerReportForTeller(int tellerID) {
        ArrayList<TellerReport> tellerReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REPORT_PROF_ID_FK + "=?";
        String[] selectionArgs = new String[]{valueOf(tellerID)};

        Cursor cursor = db.query(TELLER_REPORT_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTellerReportFromCursor(tellerReportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tellerReportArrayList;
    }
    public long insertTellerReport(TellerReport tellerReport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TELLER_REPORT_ID, tellerReport.getTellerReportID());
        values.put(TELLER_REPORT_AMOUNT_SUBMITTED, tellerReport.getTr_AmtSubmitted());
        values.put(TELLER_REPORT_BRANCH, tellerReport.getTr_Office_Branch());
        values.put(TELLER_REPORT_ADMIN, tellerReport.getTr_AdminName());
        values.put(TELLER_REPORT_DATE, tellerReport.getTellerReportDate());
        values.put(TELLER_REPORT_NO_OF_SAVINGS, tellerReport.getTrNoOfSavings());
        values.put(TELLER_REPORT_OFFICE_ID, tellerReport.getTr_Office_Branch());
        values.put(TELLER_REPORT_PROF_ID, tellerReport.getTellerReportID());
        values.put(TELLER_REPORT_BALANCE, tellerReport.getTr_Balance());
        values.put(TELLER_REPORT_MARKETER, tellerReport.getTrMarketer());
        values.put(TELLER_REPORT_AMT_PAID, tellerReport.getTr_AmtPaid());
        values.put(TELLER_REPORT_EXPECTED_AMT, tellerReport.getTr_AmtExpected());
        values.put(TELLER_REPORT_STATUS, tellerReport.getTrStatus());
        return db.insert(TELLER_REPORT_TABLE,null,values);
    }


}
