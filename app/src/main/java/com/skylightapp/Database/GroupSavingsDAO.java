package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.AppCash;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.GroupSavings;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.ProjectSavingsGroup;
import com.skylightapp.Classes.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.skylightapp.Classes.CustomerDailyReport.DAILY_REPORT_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DATE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_OFFICE_BRANCH;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_AMOUNT;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_CURRENCY;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_DATE;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_TABLE;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_ID;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_PROF_ID;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_STATUS;
import static com.skylightapp.Classes.GroupSavings.GS_GRP_ACCT_ID;
import static com.skylightapp.Classes.Payment.PAYMENTS_TABLE;
import static com.skylightapp.Classes.Payment.PAYMENT_AMOUNT;
import static com.skylightapp.Classes.Payment.PAYMENT_DATE;
import static com.skylightapp.Classes.Payment.PAYMENT_PROF_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static java.lang.String.valueOf;

public class GroupSavingsDAO extends DBHelperDAO{
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    private static final String WHERE_ID_EQUALS = GROUP_SAVINGS_ID
            + " =?";
    public GroupSavingsDAO(Context context) {
        super(context);
    }
    public ArrayList<GroupSavings> getGrpSavingsForCurrentProfile(int profileID) {
        try {
            ArrayList<GroupSavings> groupSavings = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery("SELECT * FROM " + GROUP_SAVINGS_TABLE, new String[]{" WHERE GROUP_SAVINGS_PROF_ID=?",String.valueOf(profileID)})){

                if (cursor.moveToFirst()) {

                    do {
                        GroupSavings savings = new GroupSavings();
                        savings.setGrpSavingsID(cursor.getColumnIndex(GROUP_SAVINGS_PROF_ID));
                        savings.setGrpSavingsAmount(cursor.getColumnIndex(GROUP_SAVINGS_AMOUNT));
                        savings.setGrpSavingsCurrency(cursor.getString(4));
                        savings.seStatus(cursor.getString(7));
                        savings.setSavingsDateStr(cursor.getString(6));
                        groupSavings.add(savings);
                    } while (cursor.moveToNext());


                }
                return groupSavings;
            }

            //return profiles;
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private void getGrpSavingCursorAll(ArrayList<GroupSavings> groupSavings, Cursor cursor) {
        while (cursor.moveToNext()) {
            int profID = cursor.getInt(0);
            int acctID = cursor.getInt(1);
            int grpSavingsID = cursor.getInt(2);
            String currency = cursor.getString(4);
            double amount = cursor.getDouble(5);
            String grpSavingsDate = cursor.getString(6);
            String status = cursor.getString(7);
            groupSavings.add(new GroupSavings(grpSavingsID, profID,acctID,grpSavingsDate, amount,currency,status));
        }


    }
    public ArrayList<GroupSavings> getAllSavingsGroups() {
        try {
            ArrayList<GroupSavings> groupSavingsArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(GROUP_SAVINGS_TABLE, null, null, null, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getGrpSavingCursorAll(groupSavingsArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }


            return groupSavingsArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<GroupSavings> getGrpSavingsForGrpAcct(int grpAcctID, int profID) {
        ArrayList<GroupSavings> customerDailyReportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GS_GRP_ACCT_ID + "=? AND " + GROUP_SAVINGS_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(grpAcctID), valueOf(profID)};

        Cursor cursor = db.query(GROUP_SAVINGS_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getGrpSavingCursorAll(customerDailyReportArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return customerDailyReportArrayList;
    }
    public Cursor getGroupSavingsCursor(int groupSavingsID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            return db.rawQuery("select * from GROUP_SAVINGS_TABLE where GROUP_SAVINGS_ID=" + groupSavingsID + "", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public long insertGroupSavings(int grpSavingsID, int grpSavingsAcctID, int profileID, double amount,String currencySymbol, String savingsDate, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GROUP_SAVINGS_PROF_ID, profileID);
        contentValues.put(GS_GRP_ACCT_ID, grpSavingsAcctID);
        contentValues.put(GROUP_SAVINGS_ID, grpSavingsID);
        contentValues.put(GROUP_SAVINGS_AMOUNT, amount);
        contentValues.put(GROUP_SAVINGS_CURRENCY, currencySymbol);
        contentValues.put(GROUP_SAVINGS_DATE, savingsDate);
        contentValues.put(GROUP_SAVINGS_STATUS, status);
        return sqLiteDatabase.insert(GROUP_SAVINGS_TABLE, null, contentValues);

    }
    public void updateGrpSavingsStatus(int grpSavingsAcctID,int grpSavingsID,double amount,String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues savingsUpdateValues = new ContentValues();
            CustomerDailyReport customerDailyReport = new CustomerDailyReport();
            savingsUpdateValues.put(GROUP_SAVINGS_STATUS, status);
            savingsUpdateValues.put(GROUP_SAVINGS_AMOUNT, amount);
            db.update(GROUP_SAVINGS_TABLE, savingsUpdateValues, GROUP_SAVINGS_ID + " = ?", new String[]{valueOf(grpSavingsID)});
            db.update(GRP_ACCT_TABLE, savingsUpdateValues, GRP_ACCT_ID + " = ?", new String[]{valueOf(grpSavingsAcctID)});
            db.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public double getGrpSavingsForProfile(int profileID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + GROUP_SAVINGS_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE, new String[]{" WHERE GROUP_SAVINGS_PROF_ID=?",String.valueOf(profileID)})){

                if (cursor.moveToFirst()) {

                    return cursor.getDouble(5);


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public double getGrpSavingsTotalForAcctMonth(int grpSavingsAcctID,String todayMonth) {
        double Total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String substrgDate = "substr(" + GROUP_SAVINGS_DATE + ",7,2)||substr(" + GROUP_SAVINGS_DATE + ",4,2)";
        String subToday = "substr(" + todayMonth + ",7,2)||substr(" + todayMonth + ",4,2)";

        String selection = GS_GRP_ACCT_ID + "=? AND " + substrgDate + "=?";

        String[] selectionArgs = new String[] {valueOf(grpSavingsAcctID),valueOf(subToday)};

        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        //String[] columns = new String[]{"sum(" + GROUP_SAVINGS_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + GROUP_SAVINGS_DATE + ",4) AS " + tmpcol_month_year};



        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + GROUP_SAVINGS_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Total=cursor.getDouble(5);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return Total;
    }
    public double getGrpSavingsTotalForAcctForProfMonth(int grpSavingsAcctID,int profID,String todayMonth) {
        double Total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String substrgDate = "substr(" + GROUP_SAVINGS_DATE + ",7,2)||substr(" + GROUP_SAVINGS_DATE + ",4,2)";
        String subToday = "substr(" + todayMonth + ",7,2)||substr(" + todayMonth + ",4,2)";

        String selection = GS_GRP_ACCT_ID + "=? AND " + GROUP_SAVINGS_PROF_ID + "=? AND "+ substrgDate + "=?";

        String[] selectionArgs = new String[] {valueOf(grpSavingsAcctID),valueOf(profID),valueOf(subToday)};

        String tmpcol_monthly_total = "Monthly_Total";
        String tmpcol_month_year = "Month_and_Year";

        //String[] columns = new String[]{"sum(" + GROUP_SAVINGS_AMOUNT + ") AS " + tmpcol_monthly_total, "substr(" + GROUP_SAVINGS_DATE + ",4) AS " + tmpcol_month_year};



        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + GROUP_SAVINGS_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Total=cursor.getDouble(5);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return Total;
    }
    public double getGrpSavingsTotalForAcctForProf(int grpSavingsAcctID,int profID) {
        double Total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = GS_GRP_ACCT_ID + "=? AND " + GROUP_SAVINGS_PROF_ID + "=?";

        String[] selectionArgs = new String[] {valueOf(grpSavingsAcctID),valueOf(profID)};

        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + GROUP_SAVINGS_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Total=cursor.getDouble(5);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return Total;
    }
    public double getGrpSavingsTotalForAcct(int grpSavingsAcctID) {
        double Total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = GS_GRP_ACCT_ID + "=?";

        String[] selectionArgs = new String[] {valueOf(grpSavingsAcctID)};

        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + GROUP_SAVINGS_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Total=cursor.getDouble(5);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return Total;
    }

    public double getGrpSavingsTotalForProf(int profID) {
        double Total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = GROUP_SAVINGS_PROF_ID + "=?";
        String[] selectionArgs = new String[] {valueOf(profID)};
        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + GROUP_SAVINGS_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Total=cursor.getDouble(5);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return Total;
    }
    public double getGrpSavingsTotalForProfToday(int profID,String today) {
        double Total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = GROUP_SAVINGS_PROF_ID + "=? AND " + GROUP_SAVINGS_DATE + "=?";
        String[] selectionArgs = new String[] {valueOf(profID),valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + GROUP_SAVINGS_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    Total=cursor.getDouble(5);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return Total;
    }
    public int getGrpSavingsCountToday(int grpSavingsAcctID,String today) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = GS_GRP_ACCT_ID + "=? AND " + GROUP_SAVINGS_DATE + "=?";
        String[] selectionArgs = new String[] {valueOf(grpSavingsAcctID),valueOf(today)};
        Cursor cursor = db.rawQuery(
                "SELECT COUNT (*) FROM " + GROUP_SAVINGS_TABLE + " WHERE " +  selection,
                selectionArgs
        );

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count=cursor.getColumnIndex(GROUP_SAVINGS_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count;
    }


}
