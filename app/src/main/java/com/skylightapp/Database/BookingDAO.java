package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Bookings.Bookings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.skylightapp.Bookings.Bookings.BOOKING_CLIENT_NAME;
import static com.skylightapp.Bookings.Bookings.BOOKING_CUS_ID;
import static com.skylightapp.Bookings.Bookings.BOOKING_DATE;
import static com.skylightapp.Bookings.Bookings.BOOKING_ID;
import static com.skylightapp.Bookings.Bookings.BOOKING_LOCATION;
import static com.skylightapp.Bookings.Bookings.BOOKING_OCCURENCE_NO;
import static com.skylightapp.Bookings.Bookings.BOOKING_PROF_ID;
import static com.skylightapp.Bookings.Bookings.BOOKING_STATUS;
import static com.skylightapp.Bookings.Bookings.BOOKING_TABLE;
import static com.skylightapp.Bookings.Bookings.BOOKING_TITTLE;
import static com.skylightapp.Bookings.Bookings.ITISRECCURRING;
import static java.lang.String.valueOf;

public class BookingDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = BOOKING_ID
            + " =?";
    public BookingDAO(Context context) {
        super(context);
    }
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);
    public long insertBooking(Bookings bookings) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BOOKING_ID, bookings.getBookingID());
            values.put(BOOKING_TITTLE, bookings.getTittle());
            values.put(BOOKING_CLIENT_NAME, bookings.getClientName());
            values.put(BOOKING_DATE, valueOf(bookings.getDateOfBooking()));
            values.put(BOOKING_LOCATION, bookings.getLocation());
            values.put(BOOKING_OCCURENCE_NO, bookings.getOccurrenceNo());
            values.put(String.valueOf(ITISRECCURRING), bookings.getIsRecurring());
            db.insert(BOOKING_TABLE, null, values);

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public List<Bookings> getAllBookingsAdmin() {
        try {
            List<Bookings> bookings = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + BOOKING_TABLE;

            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {

                if (cursor.moveToFirst()) {
                    do {
                        Bookings bookings1 = new Bookings();
                        bookings1.setBookingID(2);
                        bookings1.setTittle(BOOKING_TITTLE);
                        bookings1.setClientName(BOOKING_CLIENT_NAME);
                        try {
                            bookings1.setBookingDate(formatter.parse(String.valueOf(5)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        bookings1.setLocation(BOOKING_LOCATION);
                        bookings1.setStatus(BOOKING_STATUS);
                        bookings1.isRecurring(cursor.getString(6));
                        bookings1.setProfileID(Long.parseLong(cursor.getString(7)));
                        bookings1.setCustomerID(Long.parseLong(cursor.getString(8)));
                        bookings.add(bookings1);
                    } while (cursor.moveToNext());
                }
            }
            return bookings;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public Bookings getBooking(String bookingID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Bookings bookings;
            try (Cursor cursor = db.query(BOOKING_TABLE, new String[]
                            {
                                    BOOKING_ID,
                                    BOOKING_TITTLE,
                                    BOOKING_CLIENT_NAME,
                                    BOOKING_DATE,
                                    BOOKING_LOCATION,
                                    BOOKING_OCCURENCE_NO,
                                    BOOKING_STATUS
                            }, BOOKING_ID + "=?",

                    new String[]{String.valueOf(bookingID)}, null, null, null, null)) {

                if (cursor != null)
                    cursor.moveToFirst();

                bookings = null;
                if (cursor != null) {
                    try {
                        bookings = new Bookings(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                                cursor.getString(2), formatter.parse(cursor.getString(3)), cursor.getString(4),
                                Boolean.parseBoolean(cursor.getString(5)), cursor.getString(6));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            return bookings;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }


    public int getBookingCount() {
        try {
            String countQuery = "SELECT * FROM " + BOOKING_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();
            return cursor.getCount();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }


    public long insertBooking(int booking_ID, long PROFILE_ID, long CUSTOMER_ID, String tittle, String ClientName, Date bookingDate, String location, Boolean isRecurring, String status) {

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            //double packageCount =customerDailyReport.getTotalSavingsForPackage(investment_ID)% 31;
            contentValues.put(String.valueOf(BOOKING_ID), booking_ID);
            contentValues.put(BOOKING_PROF_ID, PROFILE_ID);
            contentValues.put(BOOKING_CUS_ID, CUSTOMER_ID);
            contentValues.put(BOOKING_TITTLE, tittle);
            contentValues.put(BOOKING_CLIENT_NAME, ClientName);
            contentValues.put(BOOKING_DATE, String.valueOf(bookingDate));
            contentValues.put(BOOKING_LOCATION, location);
            contentValues.put(String.valueOf(ITISRECCURRING), String.valueOf(isRecurring));
            contentValues.put(String.valueOf(BOOKING_STATUS), status);
            sqLiteDatabase.insertWithOnConflict(BOOKING_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
            sqLiteDatabase.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return PROFILE_ID;
    }
    public void updateBooking(long bookingID) {
        try {
            Bookings bookings = new Bookings();
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BOOKING_DATE, String.valueOf(bookings.getDateOfBooking()));
            values.put(BOOKING_LOCATION, bookings.getLocation());
            values.put(BOOKING_OCCURENCE_NO, bookings.getIsRecurring());
            values.put(BOOKING_STATUS, bookings.getStatus());

            // Updating row
            db.update(BOOKING_TABLE, values, BOOKING_ID + "=?",
                    new String[]{String.valueOf(bookingID)});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public void deleteBooking(long bookingID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOKING_TABLE, BOOKING_ID + "=?",
                new String[]{String.valueOf(bookingID)});
        db.close();

    }
    public Cursor getBookingCursor(String tittle, Date bookingDate) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        if (bookingDate !=null ) {
            cursor = db.rawQuery("SELECT * FROM " + BOOKING_TABLE + " WHERE BOOKING_DATE = '" + bookingDate + "' AND NBRBEDROOMS = '" + tittle + "'  ORDER BY BOOKING_ID;", null);
        } else {
            cursor = db.rawQuery("SELECT * FROM " + BOOKING_TABLE + " WHERE BOOKING_DATE = '" + bookingDate + "'  ORDER BY BOOKING_ID;", null);
        }
        return cursor;
    }
}
