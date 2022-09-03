package com.skylightapp.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.CustomerDailyReport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.skylightapp.Classes.Birthday.BIRTHDAY_DAYS_BTWN;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_DAYS_REMAINING;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_ID;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_STATUS;
import static com.skylightapp.Classes.Birthday.BIRTHDAY_TABLE;
import static com.skylightapp.Classes.Birthday.B_DOB;
import static com.skylightapp.Classes.Birthday.B_EMAIL;
import static com.skylightapp.Classes.Birthday.B_FIRSTNAME;
import static com.skylightapp.Classes.Birthday.B_PHONE;
import static com.skylightapp.Classes.Birthday.B_PROF_ID;
import static com.skylightapp.Classes.Birthday.B_SURNAME;
import static com.skylightapp.Classes.CustomerDailyReport.DAILY_REPORT_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_AMOUNT_REMAINING;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_CUS_ID_FK;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DATE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_DAYS_REMAINING;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_NUMBER_OF_DAYS;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_TOTAL;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static java.lang.String.valueOf;

public class BirthdayDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = SO_ID
            + " =?";
    public BirthdayDAO(Context context) {
        super(context);
    }
    public List<Birthday> getAllBirthdayReminder() {
        List<Birthday> birthdayReminderList = new ArrayList<>();

        // Select all Query
        String selectQuery = "SELECT * FROM " + BIRTHDAY_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery(selectQuery, null)) {

            if (cursor.moveToFirst()) {
                do {
                    Birthday birthday = new Birthday();

                    birthday.setBirthdayID(Integer.parseInt(cursor.getString(0)));
                    birthday.setbSurName(cursor.getString(3));
                    birthday.setBFirstName(cursor.getString(2));
                    birthday.setbPhoneNumber(cursor.getString(5));
                    birthday.setBEmail(cursor.getString(4));
                    birthdayReminderList.add(birthday);
                } while (cursor.moveToNext());
            }
        }
        return birthdayReminderList;
    }
    public long insertBirthDay3(Birthday birthday1, String birthdayD) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        birthdayD = birthday1.getBirthDay();
        String surName =birthday1.getbSurName();
        String firstName =birthday1.getbFirstName();
        String phone =birthday1.getbPhoneNumber();
        String Status =birthday1.getbStatus();
        int birthdayId =birthday1.getBirthdayID();
        birthdayD=birthday1.getBirthDay();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdformat.format(calendar.getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(BIRTHDAY_ID, birthdayId);
        contentValues.put(B_PROF_ID, birthday1.getbProfileID());
        contentValues.put(BIRTHDAY_STATUS, Status);
        contentValues.put(B_SURNAME, surName);
        contentValues.put(B_FIRSTNAME, firstName);
        contentValues.put(B_PHONE, phone);
        contentValues.put(BIRTHDAY_DAYS_BTWN, birthday1.getDaysInBetween(currentDate,birthdayD));
        contentValues.put(BIRTHDAY_DAYS_REMAINING, birthday1.getFormattedDaysRemainingString(currentDate,birthdayD));
        return sqLiteDatabase.insert(BIRTHDAY_TABLE, null, contentValues);

    }


    public ArrayList<Birthday> getBirthdayFromTodayDate(String myDate) {
        ArrayList<Birthday> birthdayArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = B_DOB + "=?";
        String[] selectionArgs = new String[]{myDate};
        Cursor cursor = db.query(BIRTHDAY_TABLE, null, selection, selectionArgs, null, null, null);

        getBirthdaysFromCursorAdmin(birthdayArrayList, cursor);

        cursor.close();
        db.close();

        return birthdayArrayList;
    }

    public ArrayList<Birthday> getAllBirthDays() {
        ArrayList<Birthday> birthdays = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(BIRTHDAY_TABLE, null, null, null, null,
                null, null);
        getBirthdaysFromCursorAdmin(birthdays, cursor);

        cursor.close();
        db.close();

        return birthdays;
    }

    private void getBirthdaysFromCursorAdmin(ArrayList<Birthday> birthdays, Cursor cursor) {
        while (cursor.moveToNext()) {

            int profileID = cursor.getInt(0);
            int birthdayID = cursor.getInt(1);
            String name = cursor.getString(3) + ","+ cursor.getString(2);
            String phoneNumber = cursor.getString(5);
            String email = cursor.getString(4);
            String address = null;
            String gender = null;
            String date = cursor.getString(6);
            int daysBTWN = cursor.getInt(8);
            String daysRemaining = cursor.getString(7);
            String status = cursor.getString(9);

            birthdays.add(new Birthday(profileID, birthdayID, name, phoneNumber, email, gender, address, date,daysBTWN,daysRemaining, status));
        }

    }

    public boolean checkBirthdayExist(String birthday) {
        int count=0;

        SQLiteDatabase db = this.getReadableDatabase();
        String selection = B_DOB + "=?";
        String[] selectionArgs = new String[]{String.valueOf((birthday))};

        Cursor cursor = db.query(BIRTHDAY_TABLE, null, selection, selectionArgs, null, null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getCount();
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return count > 0;
    }




    public Birthday getBirthdayReminder(String birthdayReceivedID) {

        SQLiteDatabase db = this.getReadableDatabase();

        Birthday birthday = null;
        String selection = BIRTHDAY_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(birthdayReceivedID)};

        try (Cursor cursor = db.query(BIRTHDAY_TABLE, new String[]{B_PROF_ID,BIRTHDAY_ID, B_DOB, BIRTHDAY_STATUS, B_SURNAME,
                B_FIRSTNAME,
                B_PHONE,
                B_EMAIL
        }, selection, selectionArgs, null, null, null, null)) {

            if (cursor != null)
                cursor.moveToFirst();

            if (cursor != null) {
                birthday = new Birthday(cursor.getInt(0),cursor.getInt(1),
                        cursor.getString(2), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getInt(8));
            }
        }

        return birthday;
    }
    public long insertBirthDay3(Birthday birthday1) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String birthdayD = birthday1.getBirthDay();
        String surName =birthday1.getbSurName();
        String firstName =birthday1.getbFirstName();
        String phone =birthday1.getbPhoneNumber();
        String email =birthday1.getBEmail();
        String Status =birthday1.getbStatus();
        int birthdayId =birthday1.getBirthdayID();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = mdformat.format(calendar.getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(BIRTHDAY_ID, birthdayId);
        contentValues.put(B_DOB, birthdayD);
        contentValues.put(BIRTHDAY_STATUS, Status);
        contentValues.put(B_SURNAME, surName);
        contentValues.put(B_FIRSTNAME, firstName);
        contentValues.put(B_PHONE, phone);
        contentValues.put(B_EMAIL, email);
        contentValues.put(BIRTHDAY_DAYS_BTWN, birthday1.getDaysInBetween(currentDate,birthdayD));
        contentValues.put(BIRTHDAY_DAYS_REMAINING, birthday1.getFormattedDaysRemainingString(currentDate,birthdayD));
        sqLiteDatabase.insert(BIRTHDAY_TABLE,null,contentValues);
        sqLiteDatabase.close();

        return birthdayId;
    }




}
