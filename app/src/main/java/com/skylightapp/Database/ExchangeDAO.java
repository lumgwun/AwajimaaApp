package com.skylightapp.Database;


import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_ADMIN_NAME;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_DATE;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_FROM;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_ID;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_INST;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_OFFICIAL_RATE;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_OUR_RATE;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_STATUS;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_TABLE;
import static com.skylightapp.Classes.ExchangeRate.EXCHANGER_TO;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.ExchangeRate;
import com.skylightapp.Classes.Profile;

import java.util.ArrayList;


public class ExchangeDAO  extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = EXCHANGER_ID
            + " =?";
    public ExchangeDAO (Context context) {
        super(context);
    }
    public long insertExchangeRate(int exchangeRID, String date, String fromC,String toC,double ourRate,double officialRate,String adminName,String refInsti) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EXCHANGER_ID, exchangeRID);
        contentValues.put(EXCHANGER_DATE, date);
        contentValues.put(EXCHANGER_FROM, fromC);
        contentValues.put(EXCHANGER_TO, toC);
        contentValues.put(EXCHANGER_OUR_RATE, ourRate);
        contentValues.put(EXCHANGER_OFFICIAL_RATE, officialRate);
        contentValues.put(EXCHANGER_ADMIN_NAME, adminName);
        contentValues.put(EXCHANGER_INST, refInsti);
        return sqLiteDatabase.insert(EXCHANGER_TABLE, null, contentValues);
    }
    protected Cursor getExchangeRate(String fromC, String toC,String date) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String selection = EXCHANGER_FROM + "=? AND " + EXCHANGER_TO + "=? AND " + EXCHANGER_DATE + "=?";
        String[] selectionArgs = new String[]{fromC, toC,date};
        return sqLiteDatabase.rawQuery("SELECT * FROM " + EXCHANGER_TABLE + " WHERE " + selection, selectionArgs);
    }

    public void overwriteExchangeRate(String from,String to,double ourRate,double officialRate,String date,String refInsti,String adminName,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EXCHANGER_FROM, from);
        cv.put(EXCHANGER_TO, to);
        cv.put(EXCHANGER_DATE, date);
        cv.put(EXCHANGER_OUR_RATE, ourRate);
        cv.put(EXCHANGER_OFFICIAL_RATE, officialRate);
        cv.put(EXCHANGER_INST, refInsti);
        cv.put(EXCHANGER_ADMIN_NAME, adminName);
        cv.put(EXCHANGER_STATUS, status);
        String selection = EXCHANGER_FROM + "=? AND " + EXCHANGER_TO + "=? AND " + EXCHANGER_INST + "=?";
        String[] selectionArgs = new String[]{from, to,refInsti};
        db.update(EXCHANGER_TABLE, cv, selection,
                selectionArgs);
        db.close();

    }
   /* private void getExchangeRateFromCursor(ArrayList<ExchangeRate> exchangeRates, Cursor cursor) {
        while (cursor.moveToNext()) {
            long acctID = cursor.getInt(0);
            String bankAccountNo = cursor.getString(1);
            String accountType = cursor.getString(5);
            String bank = cursor.getString(6);
            String accountName = cursor.getString(7);
            double accountBalance = cursor.getDouble(8);
            String currency = cursor.getString(13);
            String status = cursor.getString(15);
            exchangeRates.add(new ExchangeRate(acctID, bank, accountName, bankAccountNo, accountBalance, AccountTypes.valueOf(accountType),currency,status));
        }


    }
    public ArrayList<ExchangeRate> getAllAccounts() {
        ArrayList<ExchangeRate> accounts = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(EXCHANGER_TABLE, null, null, null, null,
                null, null);
        getExchangeRateFromCursor(accounts, cursor);
        cursor.close();
        //db.close();

        return accounts;
    }*/
}
