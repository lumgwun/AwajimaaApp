package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.CustomerManager;

import java.util.ArrayList;

import static com.skylightapp.Classes.AdminUser.ADMIN_ADDRESS;
import static com.skylightapp.Classes.AdminUser.ADMIN_BUSINESS_ID;
import static com.skylightapp.Classes.AdminUser.ADMIN_DATE_JOINED;
import static com.skylightapp.Classes.AdminUser.ADMIN_DOB;
import static com.skylightapp.Classes.AdminUser.ADMIN_EMAIL_ADDRESS;
import static com.skylightapp.Classes.AdminUser.ADMIN_FIRST_NAME;
import static com.skylightapp.Classes.AdminUser.ADMIN_GENDER;
import static com.skylightapp.Classes.AdminUser.ADMIN_ID;
import static com.skylightapp.Classes.AdminUser.ADMIN_NIN;
import static com.skylightapp.Classes.AdminUser.ADMIN_OFFICE;
import static com.skylightapp.Classes.AdminUser.ADMIN_PASSWORD;
import static com.skylightapp.Classes.AdminUser.ADMIN_PHONE_NUMBER;
import static com.skylightapp.Classes.AdminUser.ADMIN_PIX;
import static com.skylightapp.Classes.AdminUser.ADMIN_PROFILE_ID;
import static com.skylightapp.Classes.AdminUser.ADMIN_STATUS;
import static com.skylightapp.Classes.AdminUser.ADMIN_SURNAME;
import static com.skylightapp.Classes.AdminUser.ADMIN_TABLE;
import static com.skylightapp.Classes.AdminUser.ADMIN_USER_NAME;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_BIZ_ID;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_TABLE;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static java.lang.String.valueOf;

public class AdminUserDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = SO_ID
            + " =?";
    public AdminUserDAO(Context context) {
        super(context);
    }
    public long insertAdminUser(int profileID, String surname, String firstName, String phoneNO, String emailAddress, String dob, String gender, String address, String officeBranch, String dateJoined, String userName, String password, String nin, String selectedState, Uri selectedImage, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ADMIN_PROFILE_ID, profileID);
        values.put(ADMIN_SURNAME, surname);
        values.put(ADMIN_FIRST_NAME, firstName);
        values.put(ADMIN_PHONE_NUMBER, phoneNO);
        values.put(ADMIN_EMAIL_ADDRESS, emailAddress);
        values.put(ADMIN_DOB, dob);
        values.put(ADMIN_GENDER, gender);
        values.put(ADMIN_ADDRESS, address);
        values.put(ADMIN_OFFICE, officeBranch);
        values.put(ADMIN_DATE_JOINED, dateJoined);
        values.put(ADMIN_USER_NAME, userName);
        values.put(ADMIN_PASSWORD, password);
        values.put(ADMIN_NIN, nin);
        values.put(ADMIN_PIX, String.valueOf(selectedImage));
        values.put(ADMIN_STATUS, status);
        return db.insert(ADMIN_TABLE,null,values);
    }
    public Cursor getAllAdminFromCursor(ArrayList<AdminUser> adminUsers, Cursor cursor) {
        while (cursor.moveToNext()) {
            int adminID = cursor.getInt(0);
            String surname = cursor.getString(2);
            String firstName = cursor.getString(3);
            String phoneNumber = cursor.getString(4);
            String emailAddress = cursor.getString(5);
            String dob = cursor.getString(6);
            String gender = cursor.getString(7);
            String address = cursor.getString(8);
            String office = cursor.getString(9);
            String dateJoined = cursor.getString(10);
            String userName = cursor.getString(11);
            String password = cursor.getString(12);
            String nin = cursor.getString(13);
            Uri pix = Uri.parse(cursor.getString(14));
            String status = cursor.getString(15);
            adminUsers.add(new AdminUser(adminID, surname, firstName, phoneNumber, emailAddress, nin,dob, gender, address, userName, password, office, dateJoined,pix,status));
        }
        return cursor;
    }

    public ArrayList<AdminUser> getAllAdmin() {
        ArrayList<AdminUser> adminUserArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(ADMIN_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getAllAdminFromCursor(adminUserArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return adminUserArrayList;
    }
    public Cursor getSpinnerAdminFromCursor(ArrayList<AdminUser> adminUserArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {

            int adminID = cursor.getInt(0);
            String surname = cursor.getString(2);
            String firstName = cursor.getString(3);
            adminUserArrayList.add(new AdminUser(adminID, surname, firstName));

        }
        return cursor;
    }
    public ArrayList<AdminUser> getAdminUsersForBiz(long bizID) {
        ArrayList<AdminUser> adminUserArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = ADMIN_BUSINESS_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID)};

        @SuppressLint("Recycle") Cursor cursor = db.query(ADMIN_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSpinnerAdminFromCursor(adminUserArrayList, cursor);
                cursor.close();
            }

        db.close();

        return adminUserArrayList;
    }

    public void repostAdmin(int adminID, String newOffice) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ADMIN_OFFICE, newOffice);
        String selection = ADMIN_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(adminID)};
        sqLiteDatabase.update(ADMIN_TABLE, contentValues, selection, selectionArgs);

    }
    public Cursor getAdminUserDetails(int adminID) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM ADMIN_TABLE WHERE ADMIN_ID = ?",
                new String[]{valueOf(adminID)});

    }

    public ArrayList<AdminUser> getAllAdminSpinner() {
        ArrayList<AdminUser> adminUserArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(ADMIN_TABLE, null, null, null, null,
                null, null);
        if (cursor != null)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSpinnerAdminFromCursor(adminUserArrayList, cursor);
                cursor.close();
            }

        db.close();


        return adminUserArrayList;
    }


}
