package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static com.skylightapp.Classes.GroupAccount.GRPA_ID;
import static com.skylightapp.Classes.GroupAccount.GRPP_ID;
import static com.skylightapp.Classes.GroupAccount.GRPP_JOINED_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPP_JOINED_STATUS;
import static com.skylightapp.Classes.GroupAccount.GRP_PROFILE_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.StandingOrder.SO_ID;

public class GrpProfileDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = GRPA_ID
            + " =?";
    public GrpProfileDAO(Context context) {
        super(context);
    }
    public long insertGroupProfile(long grpAccountNo, long grpAccountProfileNo, long profileID, String phoneNo, String dateJoined) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(GRPP_ID, grpAccountProfileNo);
        contentValues.put(GRPA_ID, grpAccountNo);
        contentValues.put(PROFILE_ID, profileID);
        contentValues.put(GRPP_JOINED_DATE, dateJoined);
        contentValues.put(GRPP_JOINED_STATUS, "New");
        return sqLiteDatabase.insert(GRP_PROFILE_TABLE, null, contentValues);
    }
}
