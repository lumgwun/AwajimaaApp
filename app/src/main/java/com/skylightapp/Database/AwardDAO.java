package com.skylightapp.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static com.skylightapp.Awards.Award.AWARD_ID;
import static com.skylightapp.Awards.Award.AWARD_TABLE;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static java.lang.String.valueOf;

public class AwardDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = SO_ID
            + " =?";
    public AwardDAO(Context context) {
        super(context);
    }
    public void deleteAward(long awardID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String selection = AWARD_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(awardID)};

            db.delete(AWARD_TABLE, selection, selectionArgs);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
}
