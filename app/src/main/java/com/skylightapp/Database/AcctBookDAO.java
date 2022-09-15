package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.skylightapp.Classes.DailyAccount;

import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.DailyAccount.AMOUNT_DA;
import static com.skylightapp.Classes.DailyAccount.CONTENT_DA;
import static com.skylightapp.Classes.DailyAccount.CREATE_TIME_DA;
import static com.skylightapp.Classes.DailyAccount.CURRENCY_TYPE_DA;
import static com.skylightapp.Classes.DailyAccount.DAILY_ACCOUNTING_TABLE;
import static com.skylightapp.Classes.DailyAccount.ID_DA;
import static com.skylightapp.Classes.DailyAccount.IS_INCOME_DA;
import static com.skylightapp.Classes.ImageUtil.TAG;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCOUNT_TABLE;
import static com.skylightapp.Classes.PaymentDoc.DOCUMENT_ID;

public class AcctBookDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = DOCUMENT_ID
            + " =?";
    public AcctBookDAO(Context context) {
        super(context);
    }
    public void addDailyAccount(DailyAccount dailyAccount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CREATE_TIME_DA, dailyAccount.getdAcctCreateTime());
        values.put(CURRENCY_TYPE_DA, dailyAccount.getdAcctCurrType());
        values.put(AMOUNT_DA, dailyAccount.getdAcctAmount());
        values.put(CONTENT_DA, dailyAccount.getdAcctContent());
        values.put(IS_INCOME_DA, dailyAccount.isdAcctIsIncome());
        long id = db.insert(DAILY_ACCOUNTING_TABLE, null, values);
        Log.e(TAG, "addDailyAccount: " + id);
        db.close();
    }
    public void deleteAccount(int accountID, boolean isDailyAccount){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(isDailyAccount ? DAILY_ACCOUNTING_TABLE : JOURNEY_ACCOUNT_TABLE, ID_DA + "=?", new String[]{String.valueOf(accountID)});
        Log.e(TAG, isDailyAccount ? "deleteDailyAccount: " : "deleteJourneyAccount: " + accountID);
        db.close();
    }

    public List<DailyAccount> queryDailyAccount(int year, int month){
        SQLiteDatabase db = this.getReadableDatabase();

        String whereClause = CREATE_TIME_DA + " >= ? and " + CREATE_TIME_DA + " <= ?";
        String arg1;
        String arg2;
        if (month < 10){
            arg1 = year + "-0" + month + "-01 00:00";
            arg2 = year + "-0" + month + "-31 23:59";
        }else {
            arg1 = year + "-" + month + "-01 00:00";
            arg2 = year + "-" + month + "-31 23:59";
        }
        Cursor cursor = db.query(DAILY_ACCOUNTING_TABLE, null, whereClause, new String[]{arg1, arg2}, null, null, null);

        if (cursor.getCount() > 0) {
            List<DailyAccount> dailyAccounts = new ArrayList<DailyAccount>(cursor.getCount());
            while (cursor.moveToNext()) {
                DailyAccount dailyAccount = parseDailyAccount(cursor);
                dailyAccounts.add(dailyAccount);
            }
            return dailyAccounts;
        }

        return null;
    }

    public List<DailyAccount> queryAllDailyAccount(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DAILY_ACCOUNTING_TABLE, null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            List<DailyAccount> dailyAccounts = new ArrayList<DailyAccount>(cursor.getCount());
            while (cursor.moveToNext()) {
                DailyAccount dailyAccount = parseDailyAccount(cursor);
                dailyAccounts.add(dailyAccount);
            }
            return dailyAccounts;
        }

        return null;
    }



    private DailyAccount parseDailyAccount(Cursor cursor){
        DailyAccount dailyAccount = new DailyAccount();
        dailyAccount.setDailyAcctID(cursor.getInt(0));
        dailyAccount.setdAcctCreateTime(cursor.getString(1));
        dailyAccount.setdAcctCurrType(String.valueOf(cursor.getColumnIndex(CURRENCY_TYPE_DA)));
        dailyAccount.setdAcctAmount(cursor.getDouble(3));
        dailyAccount.setdAcctIsIncome(Boolean.parseBoolean(cursor.getString(4)));
        dailyAccount.setdAcctContent(cursor.getString(5));
        dailyAccount.setdAcctCusID(cursor.getInt(8));
        dailyAccount.setdAcctProfID(cursor.getInt(7));
        dailyAccount.setdAcctStatus(cursor.getString(6));
        dailyAccount.setdAcctAcctID(cursor.getInt(5));
        // in SQL, boolean is stored in 0 & 1
        //dailyAccount.setdAcctIsIncome(cursor.getString(cursor.getColumnIndex(6)).equals("1"));
        return dailyAccount;
    }




}
