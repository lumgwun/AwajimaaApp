package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.skylightapp.Classes.JourneyAccount;

import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.ImageUtil.TAG;
import static com.skylightapp.Classes.Journey.JOURNEY_ID;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCOUNT_TABLE;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCT_AMOUNT;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCT_CONTENT;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCT_CREATED_TIME;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCT_CURRENCY;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCT_CUS_ID;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCT_DACCT_ID;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCT_ID;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCT_PERSON;
import static com.skylightapp.Classes.JourneyAccount.JOURNEY_ACCT_PROF_ID;

public class JourneyAcctDAO extends DBHelperDAO{

    private static final String WHERE_ID_EQUALS = JOURNEY_ACCT_ID
            + " =?";
    public JourneyAcctDAO(Context context) {
        super(context);
    }

    public long addJourneyAccount(JourneyAccount journeyAccount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(JOURNEY_ACCT_CREATED_TIME, journeyAccount.getdAcctCreateTime());
        values.put(JOURNEY_ACCT_CURRENCY, journeyAccount.getdAcctCurrType());
        values.put(JOURNEY_ACCT_AMOUNT, journeyAccount.getdAcctAmount());
        values.put(JOURNEY_ACCT_CONTENT, journeyAccount.getdAcctContent());
        values.put(JOURNEY_ID, journeyAccount.getJourneyId());
        values.put(JOURNEY_ACCT_PERSON, journeyAccount.getJourneyPerson());
        values.put(JOURNEY_ACCT_PROF_ID, journeyAccount.getjAProfID());
        values.put(JOURNEY_ACCT_CUS_ID, journeyAccount.getjACusID());
        values.put(JOURNEY_ACCT_DACCT_ID, journeyAccount.getjAAcctID());
         db.insert(JOURNEY_ACCOUNT_TABLE, null, values);
        Log.e(TAG, "addJourneyAccount: " + journeyAccount.getJourneyId());
        db.close();
        return journeyAccount.getJourneyId();
    }
    

    public List<JourneyAccount> querySpecificJourneyAccountByID(int journeyID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(JOURNEY_ACCOUNT_TABLE, null, JOURNEY_ID, new String[journeyID], null, null, null);

        if (cursor.getCount() > 0) {
            List<JourneyAccount> journeyAccounts = new ArrayList<JourneyAccount>(cursor.getCount());
            while (cursor.moveToNext()) {
                JourneyAccount journeyAccount = parseJourneyAccount(cursor);
                journeyAccounts.add(journeyAccount);
            }
            return journeyAccounts;
        }

        return null;
    }
    public List<JourneyAccount> queryJourneyAccountByProfID(int profID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(JOURNEY_ACCOUNT_TABLE, null, JOURNEY_ACCT_PROF_ID, new String[profID], null, null, null);

        if (cursor.getCount() > 0) {
            List<JourneyAccount> journeyAccounts = new ArrayList<JourneyAccount>(cursor.getCount());
            while (cursor.moveToNext()) {
                JourneyAccount journeyAccount = parseJourneyAccount(cursor);
                journeyAccounts.add(journeyAccount);
            }
            return journeyAccounts;
        }

        return null;
    }
    private JourneyAccount parseJourneyAccount(Cursor cursor){
        JourneyAccount journeyAccount = new JourneyAccount();
        journeyAccount.setJourneyId(cursor.getInt(0));
        journeyAccount.setjAAcctID(cursor.getInt(6));
        journeyAccount.setjACusID(cursor.getInt(8));
        journeyAccount.setjAProfID(cursor.getInt(7));
        journeyAccount.setdAcctCreateTime(cursor.getString(1));
        journeyAccount.setdAcctCurrType(cursor.getString(2));
        journeyAccount.setdAcctAmount(cursor.getDouble(3));
        journeyAccount.setdAcctContent(cursor.getString(4));
        journeyAccount.setJourneyPerson(cursor.getString(5));
        journeyAccount.setjAStatus(cursor.getString(5));
        return journeyAccount;
    }



}
