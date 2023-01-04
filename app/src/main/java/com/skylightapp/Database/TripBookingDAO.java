package com.skylightapp.Database;

import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_AMT;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_CHILDREN;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_COUNTRY;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_CURRENCY;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_CUS_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_DATE;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_DEST;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_FROM;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_MOP;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_NAME;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_NIN_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_OFFICE;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_PROF_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_SLOTS;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_STATE;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_STATUS;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_TABLE;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_TRIP_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_TYPEOS;
import static com.skylightapp.Bookings.TripBooking.TRIP_B_NIN;
import static com.skylightapp.Bookings.TripBooking.TRIP_B_NIN_EXPIRYD;
import static com.skylightapp.Bookings.TripBooking.TRIP_B_NIN_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_B_NIN_STATUS;
import static com.skylightapp.Bookings.TripBooking.TRIP_B_NIN_TABLE;
import static com.skylightapp.Bookings.TripBooking.TRIP_B_NIN_TYPE;
import static com.skylightapp.Bookings.TripBooking.TRIP_B_TB_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_B_TRIP_ID;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Bookings.TripBooking;

import java.util.ArrayList;

public class TripBookingDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = TRIP_BOOKING_ID
            + " =?";
    public TripBookingDAO(Context context) {
        super(context);
    }
    public int getBoatTripCount() {
        String countQuery = "SELECT * FROM " + TRIP_BOOKING_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public long insertTripBooking(int bookingID,int tripID, int profID, String nin,String tripType, int noOfSits, String stopPointName,long amt,String modeOfPayment,String date, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRIP_BOOKING_ID, bookingID);
        contentValues.put(TRIP_BOOKING_PROF_ID, profID);
        contentValues.put(TRIP_BOOKING_TRIP_ID, tripID);
        contentValues.put(TRIP_BOOKING_DEST, stopPointName);
        contentValues.put(TRIP_BOOKING_NIN_ID, nin);
        contentValues.put(TRIP_BOOKING_SLOTS, noOfSits);
        contentValues.put(TRIP_BOOKING_TYPEOS, tripType);
        contentValues.put(TRIP_BOOKING_STATUS, status);
        contentValues.put(TRIP_BOOKING_AMT, amt);
        contentValues.put(TRIP_BOOKING_MOP, modeOfPayment);
        contentValues.put(TRIP_BOOKING_DATE, date);
        return sqLiteDatabase.insert(TRIP_BOOKING_TABLE, null, contentValues);

    }
    public long insertTripBooking(int bookingID, int tripID, int bundleProfID, int bundleCusID, String nin, String paymentFor, int sitCount, String stopPointName, long bookingAmt, String modeOfPayment, String subDate, String paid, String nin1, int noOfMinors, String state, String office, String country, String bookingName, String currency, String takeOffPoint) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRIP_BOOKING_ID, bookingID);
        contentValues.put(TRIP_BOOKING_PROF_ID, bundleProfID);
        contentValues.put(TRIP_BOOKING_CUS_ID, bundleCusID);
        contentValues.put(TRIP_BOOKING_TRIP_ID, tripID);
        contentValues.put(TRIP_BOOKING_DEST, stopPointName);
        contentValues.put(TRIP_BOOKING_NIN_ID, nin);
        contentValues.put(TRIP_BOOKING_SLOTS, sitCount);
        contentValues.put(TRIP_BOOKING_TYPEOS, paymentFor);
        contentValues.put(TRIP_BOOKING_STATUS, paid);
        contentValues.put(TRIP_BOOKING_AMT, bookingAmt);
        contentValues.put(TRIP_BOOKING_MOP, modeOfPayment);
        contentValues.put(TRIP_BOOKING_DATE, subDate);
        contentValues.put(TRIP_BOOKING_CHILDREN, noOfMinors);
        contentValues.put(TRIP_BOOKING_STATE, state);
        contentValues.put(TRIP_BOOKING_OFFICE, office);
        contentValues.put(TRIP_BOOKING_COUNTRY, country);
        contentValues.put(TRIP_BOOKING_NAME, bookingName);
        contentValues.put(TRIP_BOOKING_CURRENCY, currency);
        contentValues.put(TRIP_BOOKING_FROM, takeOffPoint);

        return sqLiteDatabase.insert(TRIP_BOOKING_TABLE, null, contentValues);
    }
    public long insertTripBooking(int bookingID, int tripID, int profID, String nin, String tripType, long amt, String date,LatLng startPoint, LatLng destination,String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRIP_BOOKING_ID, bookingID);
        contentValues.put(TRIP_BOOKING_PROF_ID, profID);
        contentValues.put(TRIP_BOOKING_TRIP_ID, tripID);
        contentValues.put(TRIP_BOOKING_DEST, String.valueOf(destination));
        contentValues.put(TRIP_BOOKING_FROM, String.valueOf(startPoint));
        contentValues.put(TRIP_BOOKING_NIN_ID, nin);
        contentValues.put(TRIP_BOOKING_TYPEOS, tripType);
        contentValues.put(TRIP_BOOKING_STATUS, status);
        contentValues.put(TRIP_BOOKING_AMT, amt);
        contentValues.put(TRIP_BOOKING_DATE, date);
        return sqLiteDatabase.insert(TRIP_BOOKING_TABLE, null, contentValues);

    }
    public long insertTripBookingNin(int bookingID, int tripID,int ninID, String ninType, Uri ninPix, String expDate, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TRIP_B_TB_ID, bookingID);
        contentValues.put(TRIP_B_NIN, String.valueOf(ninPix));
        contentValues.put(TRIP_B_TRIP_ID, tripID);
        contentValues.put(TRIP_B_NIN_ID, ninID);
        contentValues.put(TRIP_B_NIN_TYPE, ninType);
        contentValues.put(TRIP_B_NIN_EXPIRYD, expDate);
        contentValues.put(TRIP_B_NIN_STATUS, status);
        return sqLiteDatabase.insert(TRIP_B_NIN_TABLE, null, contentValues);

    }
    public void transferTripBooking(int profileId, int newProfileId, int newNINId,int bookingID,int tripId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues packageValues = new ContentValues();
        String selection = TRIP_BOOKING_PROF_ID + "=? AND " + TRIP_BOOKING_ID + "=?AND " + TRIP_BOOKING_TRIP_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profileId), valueOf(bookingID),valueOf(tripId)};
        packageValues.put(TRIP_BOOKING_PROF_ID, newProfileId);
        packageValues.put(TRIP_BOOKING_NIN_ID, newNINId);
        packageValues.put(TRIP_BOOKING_STATUS, status);
        db.update(TRIP_BOOKING_TABLE, packageValues, selection, selectionArgs);
        db.close();


    }
    private void getTriBookingFromCursor(ArrayList<TripBooking> tripBookings, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int bookingID = cursor.getInt(1);
                int profID = cursor.getInt(2);
                int tripID = cursor.getInt(3);
                String date = cursor.getString(4);
                String destination = cursor.getString(5);
                String bookingName = cursor.getString(6);
                int slots = cursor.getInt(7);
                long amount = cursor.getLong(8);
                String modeOfPayment = cursor.getString(9);
                String bookingType = cursor.getString(10);
                int minors = cursor.getInt(12);
                String ninID = cursor.getString(13);
                long logAmount = cursor.getLong(15);
                String status = cursor.getString(16);
                String currency = cursor.getString(17);
                String state = cursor.getString(18);
                String country = cursor.getString(19);
                String takeOffPoint = cursor.getString(20);
                String office = cursor.getString(21);
                tripBookings.add(new TripBooking(bookingID, profID,tripID, ninID,bookingName, amount, logAmount,currency,takeOffPoint,destination, slots,date, modeOfPayment,bookingType,minors,office,state,country, status));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public ArrayList<TripBooking> getAllTripBookingByDate(String todayDate) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getAllTripBookingToday(String todayDate) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(todayDate)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getTripBookingForOfficeByDate(String office,String date) {
        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_OFFICE + "=? AND " + TRIP_BOOKING_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(office), valueOf(date)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getTripBookingForOffice(String officeName) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_OFFICE + "=?";
        String[] selectionArgs = new String[]{valueOf(officeName)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getTripBookingForProf(int profID) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profID)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getTripBookingForProfByDate(int profID,String date) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_PROF_ID + "=? AND " + TRIP_BOOKING_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(profID), valueOf(date)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getTripBookingForStateToday(String state,String date) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_STATE + "=? AND " + TRIP_BOOKING_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(state), valueOf(date)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getAllTripBookingsForState(String state) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_STATE + "=?";
        String[] selectionArgs = new String[]{valueOf(state)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getTripBookingForCountryDate(String country,String date) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_COUNTRY + "=? AND " + TRIP_BOOKING_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(country), valueOf(date)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getAllTripBookingForNINAndDate(String nin,String date) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_NIN_ID + "=? AND " + TRIP_BOOKING_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(nin), valueOf(date)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getAllTripBookingForNIN(String nin) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_NIN_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(nin)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getAllTripBookingsForTypeAndDate(String type, String date) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_TYPEOS + "=? AND " + TRIP_BOOKING_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(type), valueOf(date)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getAllTripBookingForType(String type) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_TYPEOS + "=?";
        String[] selectionArgs = new String[]{valueOf(type)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getAllTripBookingForCurrencyAndDate(String currency,String date) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_CURRENCY + "=? AND " + TRIP_BOOKING_DATE + "=?";
        String[] selectionArgs = new String[]{valueOf(currency), valueOf(date)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }
    public ArrayList<TripBooking> getAllTripBookingForCurrency(String currency) {

        ArrayList<TripBooking> dailyReports = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = TRIP_BOOKING_CURRENCY + "=?";
        String[] selectionArgs = new String[]{valueOf(currency)};
        Cursor cursor = db.query(TRIP_BOOKING_TABLE, null, selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTriBookingFromCursor(dailyReports, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return dailyReports;
    }


}
