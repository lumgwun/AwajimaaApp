package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.skylightapp.Database.DBHelperDAO;

import java.io.File;
import java.util.ArrayList;

import static com.skylightapp.Classes.Loan.LOAN_ID;
import static com.skylightapp.Classes.Profile.CUS_ID_PIX_KEY;
import static com.skylightapp.Classes.Profile.PICTURE_TABLE;
import static com.skylightapp.Classes.Profile.PICTURE_URI;
import static com.skylightapp.Classes.Profile.PROFID_FOREIGN_KEY_PIX;
import static com.skylightapp.Classes.Profile.PROFILE_PIC_ID;
import static java.lang.String.valueOf;

public class PictureDAO extends DBHelperDAO {
    private static final String WHERE_ID_EQUALS = PROFILE_PIC_ID
            + " =?";
    public PictureDAO(Context context) {
        super(context);
    }

    public ArrayList<String> getPictures() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try (Cursor res = db.rawQuery("select * from PICTURE_TABLE", null)) {
            res.moveToFirst();

            while (!res.isAfterLast()) {
                array_list.add(res.getString(3));
                res.moveToNext();
            }
        }
        return array_list;
    }




    public Bitmap getProfilePicture(int userId) {
        String picturePath=null;
        Bitmap profilePicture=null;

        picturePath = getPicturePath(userId);
        if (picturePath == null || picturePath.length() == 0){
            return null;
        }else {
            profilePicture = BitmapFactory.decodeFile(picturePath);
        }



        return (profilePicture);
    }

    private String getPicturePath(int profileId) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = PROFID_FOREIGN_KEY_PIX + "=?";
        String[] selectionArgs = new String[]{valueOf(profileId)};

        @SuppressLint("Recycle") Cursor pictureCursor = db.query(PICTURE_TABLE,
                null,
                selection, selectionArgs,
                null,
                null,
                null);
        pictureCursor.moveToNext();

        String picturePath = pictureCursor.getString(0);

        return (picturePath);
    }
    public void deletePicture(int userId) {
        String picturePath = getPicturePath(userId); // See above
        if (picturePath != null && picturePath.length() != 0) {
            File reportFilePath = new File(picturePath);
            reportFilePath.delete();
        }

        SQLiteDatabase db = getWritableDatabase();
        String selection = PROFID_FOREIGN_KEY_PIX + "=?";
        String[] selectionArgs = new String[]{valueOf(userId)};

        db.delete(PICTURE_TABLE,
                selection, selectionArgs);

    }
    public long insertProfilePicture(int profileID, int customerID, Uri profilePicture) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PROFID_FOREIGN_KEY_PIX, profileID);
        contentValues.put(CUS_ID_PIX_KEY, customerID);
        contentValues.put(PICTURE_URI, valueOf(profilePicture));
        return sqLiteDatabase.insert(PICTURE_TABLE, null, contentValues);
    }


}
