package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.GroupSavings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.skylightapp.Classes.Bookings.BOOKING_ID;
import static com.skylightapp.Classes.GroupAccount.GRPA_ID;
import static com.skylightapp.Classes.GroupSavings.GROUP_AMOUNT;
import static com.skylightapp.Classes.GroupSavings.GROUP_DATE;
import static com.skylightapp.Classes.GroupSavings.GROUP_SAVINGS_TABLE;
import static com.skylightapp.Classes.GroupSavings.GS_ID;
import static com.skylightapp.Classes.GroupSavings.GS_PROF_ID;
import static com.skylightapp.Classes.GroupSavings.GS_STATUS;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class GroupSavingsDAO extends DBHelperDAO{
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    private static final String WHERE_ID_EQUALS = BOOKING_ID
            + " =?";
    public GroupSavingsDAO(Context context) {
        super(context);
    }
    public ArrayList<GroupSavings> getGrpSavingsForCurrentProfile(long profileID) {
        try {
            ArrayList<GroupSavings> groupSavings = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery("SELECT * FROM " + GROUP_SAVINGS_TABLE, new String[]{" WHERE PROFILE_ID=?",String.valueOf(profileID)})){

                if (cursor.moveToFirst()) {

                    do {
                        GroupSavings savings = new GroupSavings();
                        savings.setGrpSavingsID(Integer.parseInt(cursor.getString(0)));
                        savings.setGrpSavingsAmount(Double.parseDouble(cursor.getString(1)));
                        savings.seStatus(cursor.getString(3));
                        try {
                            savings.setSavingsDate(formatter.parse(cursor.getString(2)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
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
    public Cursor getGroupSavingsCursor(int groupSavingsID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            return db.rawQuery("select * from GROUP_SAVINGS_TABLE where GS_ID=" + groupSavingsID + "", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public long insertGroupSavings(int grpSavingsID, int grpSavingsAcctID, long profileID, double amount, Date savingsDate, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GS_PROF_ID, profileID);
        contentValues.put(GRPA_ID, grpSavingsAcctID);
        contentValues.put(GS_ID, grpSavingsID);
        contentValues.put(GROUP_AMOUNT, amount);
        contentValues.put(GROUP_DATE, String.valueOf(savingsDate));
        contentValues.put(GS_STATUS, status);
        return sqLiteDatabase.insert(GROUP_SAVINGS_TABLE, null, contentValues);

    }

}
