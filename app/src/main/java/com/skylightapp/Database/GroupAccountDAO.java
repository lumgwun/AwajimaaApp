package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Profile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static com.skylightapp.Classes.Bookings.BOOKING_ID;
import static com.skylightapp.Classes.GroupAccount.GRPA_BALANCE;
import static com.skylightapp.Classes.GroupAccount.GRPA_EMAIL;
import static com.skylightapp.Classes.GroupAccount.GRPA_END_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPA_FIRSTNAME;
import static com.skylightapp.Classes.GroupAccount.GRPA_ID;
import static com.skylightapp.Classes.GroupAccount.GRPA_PHONE;
import static com.skylightapp.Classes.GroupAccount.GRPA_PURPOSE;
import static com.skylightapp.Classes.GroupAccount.GRPA_START_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPA_STATUS;
import static com.skylightapp.Classes.GroupAccount.GRPA_SURNAME;
import static com.skylightapp.Classes.GroupAccount.GRPA_TITTLE;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupSavings.GROUP_AMOUNT;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PIC_ID;
import static java.lang.String.valueOf;

public class GroupAccountDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = GRPA_ID
            + " =?";
    public GroupAccountDAO(Context context) {
        super(context);
    }

    public void updateGrpAcct(long grpAcctID, String tittle, String purpose, String surname, String firstName, String phoneNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        savingsUpdateValues.put(GRPA_TITTLE, tittle);
        savingsUpdateValues.put(GRPA_PURPOSE, purpose);
        savingsUpdateValues.put(GRPA_SURNAME, surname);
        savingsUpdateValues.put(GRPA_FIRSTNAME, firstName);
        savingsUpdateValues.put(GRPA_PHONE, phoneNo);
        //db.update(GROUP_SAVINGS_TABLE, savingsUpdateValues, GS_ID + " = ?", new String[]{valueOf(grpSavingsID)});
        db.update(GRP_ACCT_TABLE, savingsUpdateValues, GRPA_ID + " = ?", new String[]{valueOf(grpAcctID)});
        db.close();

    }


    public long insertGroupAccount(long grpAccountNo, long profileID, String tittle, String purpose, String firstName, String lastName, String phoneNo, String emailAddress, Date startDate, double accountBalance, Date endDate, String status) {
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(GRPA_ID, grpAccountNo);
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

    public Cursor getAllGroupProfiles(long grpAcctID) {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM GRP_PROFILE_TABLE WHERE GRPA_ID = ?",
                    new String[]{valueOf(grpAcctID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Cursor getAllGroupSavingsForProfile(long profileID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM GROUP_SAVINGS_TABLE WHERE PROFILE_ID = ?",
                new String[]{valueOf(profileID)});
    }
    public double getGrpSavingsTotal(long grpAcctID) {
        try {
            GroupAccount groupAccount = new GroupAccount();
            grpAcctID=groupAccount.getGrpAcctNo();
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + GROUP_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE, new String[]{" WHERE GRPA_ID=?",String.valueOf(grpAcctID)})){

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
    public double getGrpSavingsForProfile(long profileID) {
        try {
            Profile profile = new Profile();
            profileID=profile.getPID();
            SQLiteDatabase db = this.getWritableDatabase();

            try (Cursor cursor = db.rawQuery("SELECT SUM(" + GROUP_AMOUNT + ") as Total FROM " + GROUP_SAVINGS_TABLE, new String[]{" WHERE PROFILE_ID=?",String.valueOf(profileID)})){

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
    public ArrayList<GroupAccount> getGrpAcctsForCurrentProfile(long profileID) {
        try {
            ArrayList<GroupAccount> groupAccounts = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery("SELECT * FROM " + GRP_ACCT_TABLE, new String[]{" WHERE PROFILE_ID=?",String.valueOf(profileID)})){

                if (cursor.moveToFirst()) {

                    do {
                        GroupAccount groupAccount = new GroupAccount();
                        groupAccount.setGrpAcctNo(Long.parseLong(cursor.getString(0)));
                        groupAccount.setGrpTittle(cursor.getString(1));
                        groupAccount.setGrpPurpose(cursor.getString(2));
                        groupAccount.setGrpLastName(cursor.getString(3));
                        groupAccount.setGrpFirstName(cursor.getString(4));
                        groupAccount.seGrpPhoneNo(cursor.getString(5));


                        try {
                            groupAccount.setGrpStartDate(formatter.parse(cursor.getString(6)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        groupAccount.setGrpEndDate(formatter.parse(cursor.getString(7)));
                        groupAccounts.add(groupAccount);
                    } while (cursor.moveToNext());


                }
                return groupAccounts;
            } catch (ParseException e) {
                e.printStackTrace();
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
        try {
            ArrayList<GroupAccount> groupAccountsList = new ArrayList<>();

            // Select all Query
            String selectQuery = "SELECT * FROM " + GRP_ACCT_TABLE;

            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {

                if (cursor.moveToFirst()) {
                    do {
                        GroupAccount groupAccount = new GroupAccount();
                        groupAccount.setGrpAcctNo(Long.parseLong(cursor.getString(0)));
                        groupAccount.setGrpProfileID(Long.parseLong(cursor.getString(1)));
                        groupAccount.setGrpPurpose(cursor.getString(3));
                        groupAccount.setGrpAcctBalance(Double.parseDouble(cursor.getString(10)));
                        try {
                            groupAccount.setGrpStartDate(formatter.parse(cursor.getString(8)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        try {
                            groupAccount.setGrpEndDate(formatter.parse(cursor.getString(9)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        groupAccount.setIsComplete(Boolean.parseBoolean(cursor.getString(11)));


                        groupAccountsList.add(groupAccount);
                    } while (cursor.moveToNext());
                }
            }
            return groupAccountsList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteGroupAcct(long grpAcctID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(GRP_ACCT_TABLE, GRPA_ID + "=?",
                    new String[]{String.valueOf(grpAcctID)});
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public GroupAccount getGrpAcct(String grpID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            GroupAccount groupAccount;
            try (Cursor cursor = db.query(GRP_ACCT_TABLE, new String[]
                            {
                                    GRPA_ID,
                                    PROFILE_ID,
                                    GRPA_TITTLE,
                                    GRPA_PURPOSE,
                                    GRPA_FIRSTNAME,
                                    GRPA_START_DATE,GRPA_BALANCE,GRPA_STATUS
                            }, BOOKING_ID + "=?",

                    new String[]{String.valueOf(grpID)}, null, null, null, null)) {

                if (cursor != null)
                    cursor.moveToFirst();

                groupAccount = null;
                if (cursor != null) {
                    try {
                        groupAccount = new GroupAccount(Integer.parseInt(cursor.getString(0)),Long.parseLong(cursor.getString(1)), cursor.getString(2),
                                cursor.getString(3), cursor.getString(4),Double.parseDouble(cursor.getString(5)),formatter.parse(cursor.getString(6)),
                                cursor.getString(7));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            return groupAccount;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
}
