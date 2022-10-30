package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.skylightapp.Classes.GroupAccount.GROUP_NEW_USER_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_CREATOR_PROFILE_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_FIRSTNAME;
import static com.skylightapp.Classes.GroupAccount.GRP_ASSIGNED_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_JOINED_DATE;
import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_JOINED_STATUS;
import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_SURNAME;
import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRP_PROF_ACCT_ID;

import static java.lang.String.valueOf;

import com.skylightapp.Classes.GrpSavingsProfile;

import java.util.ArrayList;

public class GrpProfileDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = GRP_ASSIGNED_ID
            + " =?";
    public GrpProfileDAO(Context context) {
        super(context);
    }
    public long insertGroupProfile(int grpAccountNo, int grpAccountProfileNo, int profileID, String firstName,String surname, String dateJoined) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GRP_ASSIGNED_ID, grpAccountProfileNo);
        contentValues.put(GRP_ACCT_ID, grpAccountNo);
        contentValues.put(GROUP_NEW_USER_ID, profileID);
        contentValues.put(GRP_PROFILE_JOINED_DATE, dateJoined);
        contentValues.put(GRP_PROFILE_FIRSTNAME, firstName);
        contentValues.put(GRP_PROFILE_SURNAME, surname);
        contentValues.put(GRP_PROFILE_JOINED_STATUS, "New");
        return sqLiteDatabase.insert(GRP_PROFILE_TABLE, null, contentValues);
    }
    private void getGrpProfileCursorAll(ArrayList<GrpSavingsProfile> profileArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int profID = cursor.getInt(2);
            int grpSavingsID = cursor.getInt(8);
            String lastName = cursor.getString(4);
            String firstName = cursor.getString(5);
            String dateJoined = cursor.getString(6);
            String status = cursor.getString(7);

            profileArrayList.add(new GrpSavingsProfile(grpSavingsID,profID,firstName, lastName,dateJoined,status));
        }


    }
    public ArrayList<GrpSavingsProfile> getAllProfilesForGroups() {
        try {
            ArrayList<GrpSavingsProfile> profiles = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.query(GRP_PROFILE_TABLE, null, null, null, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getGrpProfileCursorAll(profiles, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }


            return profiles;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public ArrayList<GrpSavingsProfile> getGrpProfilesForGrpAcct(int grpAcctID, int ownerID) {
        ArrayList<GrpSavingsProfile> profileArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = GRP_PROF_ACCT_ID + "=? AND " + GRP_CREATOR_PROFILE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(grpAcctID), valueOf(ownerID)};

        Cursor cursor = db.query(GRP_PROFILE_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getGrpProfileCursorAll(profileArrayList,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return profileArrayList;
    }
    public Cursor getGroupProfileCursor(int groupProfileID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            return db.rawQuery("select * from GRP_PROFILE_TABLE where GROUP_NEW_USER_ID=" + groupProfileID + "", null);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
