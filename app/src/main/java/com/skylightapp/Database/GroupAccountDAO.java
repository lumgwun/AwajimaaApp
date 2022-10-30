package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.GroupSavings;
import com.skylightapp.Classes.Profile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.skylightapp.Classes.GroupAccount.GRPA_LINK;
import static com.skylightapp.Classes.GroupAccount.GRPA_AMT;
import static com.skylightapp.Classes.GroupAccount.GRPA_BALANCE;
import static com.skylightapp.Classes.GroupAccount.GRPA_CURRENCY_CODE;
import static com.skylightapp.Classes.GroupAccount.GRPA_DURATION;
import static com.skylightapp.Classes.GroupAccount.GRPA_EMAIL;
import static com.skylightapp.Classes.GroupAccount.GRPA_END_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPA_FIRSTNAME;

import static com.skylightapp.Classes.GroupAccount.GRPA_FREQ;
import static com.skylightapp.Classes.GroupAccount.GRPA_PHONE;
import static com.skylightapp.Classes.GroupAccount.GRPA_PROFILE_ID;
import static com.skylightapp.Classes.GroupAccount.GRPA_PURPOSE;
import static com.skylightapp.Classes.GroupAccount.GRPA_SCORE;
import static com.skylightapp.Classes.GroupAccount.GRPA_START_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPA_STATUS;
import static com.skylightapp.Classes.GroupAccount.GRPA_SURNAME;
import static com.skylightapp.Classes.GroupAccount.GRPA_TITTLE;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRP_ASSIGNED_ID;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_AMOUNT;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_PROF_ID;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_TABLE;
import static com.skylightapp.Classes.GroupSavings.GS_GRP_ACCT_ID;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static java.lang.String.valueOf;

public class GroupAccountDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = GRP_ACCT_ID
            + " =?";
    public GroupAccountDAO(Context context) {
        super(context);
    }
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public void updateGrpAcct(int grpAcctID, String tittle, String purpose, String surname, String firstName, String phoneNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        savingsUpdateValues.put(GRPA_TITTLE, tittle);
        savingsUpdateValues.put(GRPA_PURPOSE, purpose);
        savingsUpdateValues.put(GRPA_SURNAME, surname);
        savingsUpdateValues.put(GRPA_FIRSTNAME, firstName);
        savingsUpdateValues.put(GRPA_PHONE, phoneNo);
        //db.update(GROUP_SAVINGS_TABLE, savingsUpdateValues, GS_ID + " = ?", new String[]{valueOf(grpSavingsID)});
        db.update(GRP_ACCT_TABLE, savingsUpdateValues, GRP_ACCT_ID + " = ?", new String[]{valueOf(grpAcctID)});
        db.close();

    }


    public long insertGroupAccount(int grpAccountNo, int profileID, String tittle, String purpose, String firstName, String lastName, String phoneNo, String emailAddress, Date startDate, double accountBalance, Date endDate, String status) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GRP_ACCT_ID, grpAccountNo);
            contentValues.put(PROFILE_ID, profileID);
            contentValues.put(GRPA_TITTLE, tittle);
            contentValues.put(GRPA_PURPOSE, purpose);
            contentValues.put(GRPA_SURNAME, lastName);
            contentValues.put(GRPA_FIRSTNAME, firstName);
            contentValues.put(GRPA_PHONE, phoneNo);
            contentValues.put(GRPA_EMAIL, emailAddress);
            contentValues.put(GRPA_START_DATE, String.valueOf(startDate));
            contentValues.put(GRPA_BALANCE, accountBalance);
            contentValues.put(GRPA_END_DATE, String.valueOf(endDate));
            contentValues.put(GRPA_STATUS, status);
            sqLiteDatabase.insert(GRP_ACCT_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return grpAccountNo;
    }
    public long insertGroupAccount(int grpAccountNo, int profileID, String tittle, String purpose, String firstName, String lastName,String managerPhoneNumber,String managerEmail,  String startDate,double amount, String currencyCode, int selectedFreq, int selectedDuration, double accountBalance, Uri grpLink,String endDate, String status) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GRP_ACCT_ID, grpAccountNo);
            contentValues.put(GRPA_PROFILE_ID, profileID);
            contentValues.put(GRPA_TITTLE, tittle);
            contentValues.put(GRPA_PURPOSE, purpose);
            contentValues.put(GRPA_SURNAME, lastName);
            contentValues.put(GRPA_FIRSTNAME, firstName);
            contentValues.put(GRPA_PHONE, managerPhoneNumber);
            contentValues.put(GRPA_EMAIL, managerEmail);
            contentValues.put(GRPA_AMT, amount);
            contentValues.put(GRPA_CURRENCY_CODE, currencyCode);
            contentValues.put(GRPA_FREQ, selectedFreq);
            contentValues.put(GRPA_DURATION, selectedDuration);
            contentValues.put(GRPA_LINK, String.valueOf(grpLink));
            contentValues.put(GRPA_START_DATE, startDate);
            contentValues.put(GRPA_BALANCE, accountBalance);
            contentValues.put(GRPA_END_DATE, endDate);
            contentValues.put(GRPA_STATUS, status);
            sqLiteDatabase.insert(GRP_ACCT_TABLE, null, contentValues);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return grpAccountNo;
    }
    private void getGrpAcctCursorAll(ArrayList<GroupAccount> groupAccounts, Cursor cursor) {
        while (cursor.moveToNext()) {
            int acctID = cursor.getInt(0);
            int profID = cursor.getInt(1);
            String tittle = cursor.getString(2);
            String purpose = cursor.getString(3);
            String name = cursor.getString(4)+""+cursor.getString(5);
            String grpSavingsDate = cursor.getString(8);
            String endDate = cursor.getString(9);
            String status = cursor.getString(11);
            String link = cursor.getString(15);
            int selectedFreq = cursor.getInt(16);
            int selectedDuration = cursor.getInt(17);
            double amount = cursor.getDouble(18);

            String currencyCode = cursor.getString(19);
            int remDuration = cursor.getInt(20);
            groupAccounts.add(new GroupAccount(profID,acctID,tittle,purpose,name,grpSavingsDate, amount,currencyCode,  selectedFreq,selectedDuration,remDuration,endDate,link,status));
        }


    }
    public ArrayList<GroupAccount> getGrpAccountForProf(int profID) {
        ArrayList<GroupAccount> groupAccountArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GRPA_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profID)};

        Cursor cursor = db.query(GRP_ACCT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getGrpAcctCursorAll(groupAccountArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return groupAccountArrayList;
    }

    public ArrayList<GroupAccount> getGrpAccountForAmount(double amount) {
        ArrayList<GroupAccount> groupAccountArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GRPA_AMT + "=?";
        String[] selectionArgs = new String[]{valueOf(amount)};

        Cursor cursor = db.query(GRP_ACCT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getGrpAcctCursorAll(groupAccountArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return groupAccountArrayList;
    }
    public ArrayList<GroupAccount> getGrpAccountForAmtRange(long minAmount,long maxAmount) {
        ArrayList<GroupAccount> groupAccountArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = GRPA_AMT + " BETWEEN " + minAmount + " AND " + maxAmount;
        Cursor cursor = database.query(GRP_ACCT_TABLE, null, whereClause, null, null, null, GRPA_FREQ + " ASC");
        while (cursor.moveToNext()) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getGrpAcctCursorAll(groupAccountArrayList,cursor);
            }

        }
        db.close();
        cursor.close();

        return groupAccountArrayList;
    }
    public ArrayList<GroupAccount> getGrpAccountDurationRange(long minDuration,long maxDuration) {
        ArrayList<GroupAccount> groupAccountArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = GRPA_DURATION + " BETWEEN " + minDuration + " AND " + maxDuration;
        Cursor cursor = database.query(GRP_ACCT_TABLE, null, whereClause, null, null, null, GRPA_FREQ + " ASC");
        while (cursor.moveToNext()) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getGrpAcctCursorAll(groupAccountArrayList,cursor);
            }

        }
        db.close();
        cursor.close();

        return groupAccountArrayList;
    }
    public ArrayList<GroupAccount> getGrpAccountForDuration(int duration) {
        ArrayList<GroupAccount> groupAccountArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GRPA_DURATION + "=?";
        String[] selectionArgs = new String[]{valueOf(duration)};

        Cursor cursor = db.query(GRP_ACCT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getGrpAcctCursorAll(groupAccountArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return groupAccountArrayList;
    }
    public ArrayList<GroupAccount> getGrpAccountForScore(int score) {
        ArrayList<GroupAccount> groupAccountArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GRPA_SCORE + "=?";
        String[] selectionArgs = new String[]{valueOf(score)};

        Cursor cursor = db.query(GRP_ACCT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getGrpAcctCursorAll(groupAccountArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return groupAccountArrayList;
    }
    public ArrayList<GroupAccount> getGrpAccountForAmountAndDuration(double amount, int duration) {
        ArrayList<GroupAccount> groupAccountArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GRPA_AMT + "=? AND " + GRPA_DURATION + "=?";
        String[] selectionArgs = new String[]{valueOf(amount), valueOf(duration)};
        Cursor cursor = db.query(GRP_ACCT_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getGrpAcctCursorAll(groupAccountArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return groupAccountArrayList;
    }



    public Cursor getAllGroupProfiles(int grpAcctID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM GRP_ACCT_TABLE WHERE GRP_ACCT_ID = ?",
                    new String[]{valueOf(grpAcctID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getAllGroupSavingsForAcct(int grpAcctID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM GRP_ACCT_TABLE WHERE GRP_ACCT_ID = ?",
                new String[]{valueOf(grpAcctID)});
    }
    public double getGrpAccountTotal(int grpAcctID) {
        try {
            GroupAccount groupAccount = new GroupAccount();
            grpAcctID=groupAccount.getGrpAcctNo();
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + GRPA_AMT + ") as Total FROM " + GRP_ACCT_TABLE, new String[]{" WHERE GRP_ACCT_ID=?",String.valueOf(grpAcctID)})){

                if (cursor.moveToFirst()) {

                    return Double.parseDouble(String.valueOf(cursor.getCount()));


                }
            }

            return 0;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<GroupAccount> getGrpAcctsForCurrentProfile(int profileID) {
        try {
            ArrayList<GroupAccount> groupAccounts = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery("SELECT * FROM " + GRP_ACCT_TABLE, new String[]{" WHERE GRPA_PROFILE_ID=?",String.valueOf(profileID)})){

                if (cursor.moveToFirst()) {

                    do {
                        GroupAccount groupAccount = new GroupAccount();
                        groupAccount.setGrpAcctNo(cursor.getInt(0));
                        groupAccount.setGrpTittle(cursor.getString(2));
                        groupAccount.setGrpPurpose(cursor.getString(3));
                        groupAccount.setGrpLastName(cursor.getString(4));
                        groupAccount.setGrpFirstName(cursor.getString(5));
                        groupAccount.setGrpStartDate(cursor.getString(8));
                        groupAccount.setGrpEndDate(cursor.getString(9));
                        groupAccount.setGrpLink(cursor.getString(15));
                        groupAccount.setGrpFreqNo(cursor.getInt(16));
                        groupAccount.setGrpDuration(cursor.getInt(17));
                        groupAccount.setGrpAmt(cursor.getDouble(18));
                        groupAccount.setGrpCurrencyCode(cursor.getString(19));
                        groupAccount.setGrpCountry(cursor.getString(20));

                        groupAccounts.add(groupAccount);
                    } while (cursor.moveToNext());


                }
                return groupAccounts;
            }

            //return profiles;
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Profile> getProfilesForCurrentGrpAccount(long grpAcctID) {
        try {
            ArrayList<Profile> profiles = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery("SELECT * FROM " + GRP_ACCT_TABLE, new String[]{" WHERE GRPA_ID=?",String.valueOf(grpAcctID)})){

                if (cursor.moveToFirst()) {

                    do {
                        Profile userProfile = new Profile();
                        userProfile.setPID(Integer.parseInt((cursor.getString(0))));
                        userProfile.setProfileLastName(cursor.getString(1));
                        userProfile.setProfileFirstName(cursor.getString(2));
                        userProfile.setProfileIdentity(cursor.getString(3));
                        userProfile.setProfilePicture(Uri.parse(cursor.getString(4)));
                        userProfile.setProfilePhoneNumber(cursor.getString(5));
                        profiles.add(userProfile);
                    } while (cursor.moveToNext());


                }
                return profiles;
            }

            //return profiles;
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<GroupAccount> getAllGroupAcctList() {
        ArrayList<GroupAccount> groupAccountsList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + GRP_ACCT_TABLE, null)){

            if (cursor.moveToFirst()) {

                do {
                    GroupAccount groupAccount = new GroupAccount();
                    groupAccount.setGrpAcctNo(cursor.getInt(0));
                    groupAccount.setGrpTittle(cursor.getString(2));
                    groupAccount.setGrpPurpose(cursor.getString(3));
                    groupAccount.setGrpLastName(cursor.getString(4));
                    groupAccount.setGrpFirstName(cursor.getString(5));
                    groupAccount.setGrpStartDate(cursor.getString(8));
                    groupAccount.setGrpEndDate(cursor.getString(9));
                    groupAccount.setGrpLink(cursor.getString(15));
                    groupAccount.setGrpFreqNo(cursor.getInt(16));
                    groupAccount.setGrpDuration(cursor.getInt(17));
                    groupAccount.setGrpAmt(cursor.getDouble(18));
                    groupAccount.setGrpCurrencyCode(cursor.getString(19));
                    groupAccount.setGrpCountry(cursor.getString(20));

                    groupAccountsList.add(groupAccount);
                } while (cursor.moveToNext());


            }
            return groupAccountsList;
        }


    }

    public void deleteGroupAcct(long grpAcctID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(GRP_ACCT_TABLE, GRP_ACCT_ID + "=?",
                    new String[]{String.valueOf(grpAcctID)});
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public GroupAccount getGrpAcct(int grpAcctID,int grpProfID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = GRP_ACCT_ID + "=? AND " + GRP_ASSIGNED_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(grpAcctID), valueOf(grpProfID)};
            try (Cursor cursor = db.query(GRP_ACCT_TABLE, null, selection, selectionArgs, null, null, null, null)) {

                if (cursor != null) {
                    GroupAccount groupAccount = new GroupAccount();
                    groupAccount.setGrpAcctNo(cursor.getInt(0));
                    groupAccount.setGrpTittle(cursor.getString(2));
                    groupAccount.setGrpPurpose(cursor.getString(3));
                    groupAccount.setGrpLastName(cursor.getString(4));
                    groupAccount.setGrpFirstName(cursor.getString(5));
                    groupAccount.setGrpStartDate(cursor.getString(8));
                    groupAccount.setGrpEndDate(cursor.getString(9));
                    groupAccount.setGrpLink(cursor.getString(15));
                    groupAccount.setGrpFreqNo(cursor.getInt(16));
                    groupAccount.setGrpDuration(cursor.getInt(17));
                    groupAccount.setGrpAmt(cursor.getDouble(18));
                    groupAccount.setGrpCurrencyCode(cursor.getString(19));
                    groupAccount.setGrpCountry(cursor.getString(20));
                }
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public void updateGrpAcctStatus(int grpAccountNo,int profileID,double accountBalance,String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues savingsUpdateValues = new ContentValues();
            savingsUpdateValues.put(GRPA_BALANCE, accountBalance);
            savingsUpdateValues.put(GRPA_STATUS, status);
            db.update(PROFILES_TABLE, savingsUpdateValues, PROFILE_ID + " = ?", new String[]{valueOf(profileID)});
            db.update(GRP_ACCT_TABLE, savingsUpdateValues, GRP_ACCT_ID + " = ?", new String[]{valueOf(grpAccountNo)});
            db.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
}
