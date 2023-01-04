package com.skylightapp.Database;

import static com.skylightapp.Bookings.Trip.A_TRIP_AMOUNT_ADULT;
import static com.skylightapp.Bookings.Trip.A_TRIP_AMT_CHILDREN;
import static com.skylightapp.Bookings.Trip.A_TRIP_DATE;
import static com.skylightapp.Bookings.Trip.A_TRIP_DEST_NAME;
import static com.skylightapp.Bookings.Trip.A_TRIP_ENDT;
import static com.skylightapp.Bookings.Trip.A_TRIP_ID;
import static com.skylightapp.Bookings.Trip.A_TRIP_NO_OF_BOATS;
import static com.skylightapp.Bookings.Trip.A_TRIP_NO_OF_SIT;
import static com.skylightapp.Bookings.Trip.A_TRIP_PROF_ID;
import static com.skylightapp.Bookings.Trip.A_TRIP_START_TIME;
import static com.skylightapp.Bookings.Trip.A_TRIP_STATE;
import static com.skylightapp.Bookings.Trip.A_TRIP_STATUS;
import static com.skylightapp.Bookings.Trip.A_TRIP_TABLE;
import static com.skylightapp.Bookings.Trip.A_TRIP_TAKE_OFF_LATLNG;
import static com.skylightapp.Bookings.Trip.A_TRIP_TAKE_OFF_POINT;
import static com.skylightapp.Bookings.Trip.A_TRIP_TYPE;
import static com.skylightapp.Bookings.Trip.A_TRIP_TYPE_OF_BOAT;
import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Bookings.Trip;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TripDAO extends DBHelperDAO{

    private static final String WHERE_ID_EQUALS = A_TRIP_ID
            + " =?";
    private Calendar calendar;
    private String tripDate;

    public TripDAO(Context context) {
        super(context);
    }
    public int getTripCount() { String countQuery = "SELECT * FROM " + A_TRIP_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertNewTrip(int tripID, int profID, String state, double amtForAdult, double amtForChildren, int noOfSits, String takeOffPoint, String destination, String date, String startTime, String endTime, LatLng takeOffLatLng,String type, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(A_TRIP_ID, tripID);
        contentValues.put(A_TRIP_PROF_ID, profID);
        contentValues.put(A_TRIP_STATE, state);
        contentValues.put(A_TRIP_AMOUNT_ADULT, amtForAdult);
        contentValues.put(A_TRIP_AMT_CHILDREN, amtForChildren);
        contentValues.put(A_TRIP_NO_OF_SIT, noOfSits);
        contentValues.put(A_TRIP_DEST_NAME, destination);
        contentValues.put(A_TRIP_STATUS, status);
        contentValues.put(A_TRIP_START_TIME, startTime);
        contentValues.put(A_TRIP_ENDT, endTime);
        contentValues.put(A_TRIP_TAKE_OFF_POINT, takeOffPoint);
        contentValues.put(A_TRIP_DATE, date);
        contentValues.put(A_TRIP_TYPE, type);
        contentValues.put(A_TRIP_TAKE_OFF_LATLNG, String.valueOf(takeOffLatLng));
        return sqLiteDatabase.insert(A_TRIP_TABLE, null, contentValues);

    }
    public long insertNewTrip(int tripID, int profID, int noOfBoatInt, String selectedBoatType, String takeOffPoint, String  destination, String state, String date, String selectedTripType) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(A_TRIP_ID, tripID);
        contentValues.put(A_TRIP_PROF_ID, profID);
        contentValues.put(A_TRIP_STATE, state);
        contentValues.put(A_TRIP_NO_OF_BOATS, noOfBoatInt);
        contentValues.put(A_TRIP_TYPE_OF_BOAT, selectedBoatType);
        contentValues.put(A_TRIP_DEST_NAME, destination);
        contentValues.put(A_TRIP_TYPE, selectedTripType);
        contentValues.put(A_TRIP_TAKE_OFF_POINT, takeOffPoint);
        contentValues.put(A_TRIP_DATE, date);
        return sqLiteDatabase.insert(A_TRIP_TABLE, null, contentValues);

    }

    public void deleteTrip(int tripID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = A_TRIP_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tripID)};
        db.delete(A_TRIP_TABLE, selection, selectionArgs);

    }
    private void getTripFromCursor(ArrayList<Trip> tripArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int tripId = cursor.getInt(1);
            int profId = cursor.getInt(2);
            String takeOffPoint = cursor.getString(4);
            String destPoint = cursor.getString(5);
            String takeOffLatLng = cursor.getString(15);
            String status = cursor.getString(12);
            String tripState = cursor.getString(3);
            String tripDate = cursor.getString(9);
            double tripAdultAmt = cursor.getDouble(10);
            double tripChildrenAmt = cursor.getDouble(11);
            int tripNoOfSits = cursor.getInt(13);
            tripArrayList.add(new Trip(tripId, profId,tripNoOfSits,takeOffPoint,destPoint,takeOffLatLng, tripState,tripDate,tripAdultAmt,tripChildrenAmt,status));
        }

    }
    public ArrayList<Trip> getAllTripsByType(String tripType) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = A_TRIP_TYPE + "=?";
        String[] selectionArgs = new String[]{tripType};
        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Trip> getAllTripsByTypeAndState(String tripType,String state) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = A_TRIP_TYPE + "=?AND " + A_TRIP_STATE + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + A_TRIP_DATE + ",4)";

        String[] selectionArgs = new String[]{valueOf(tripType),valueOf(state)};
        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, grpBy,
                null, A_TRIP_DATE);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Trip> getAllTripsByTypeAndDate(String tripType,String date) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = A_TRIP_TYPE + "=?AND " + A_TRIP_TYPE + "=?AND " + A_TRIP_STATE + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + A_TRIP_DATE + ",4)";

        String[] selectionArgs = new String[]{valueOf(tripType),valueOf(date)};
        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, grpBy,
                null, A_TRIP_DATE);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Trip> getAllTripsByProf(int profID) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = A_TRIP_PROF_ID + "=?";

        String[] selectionArgs = new String[]{valueOf(profID)};
        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, A_TRIP_DATE,
                null, A_TRIP_DATE);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Trip> getAllTripsByTypeAndFrom(String from,String tripType) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        tripDate = mdformat.format(calendar.getTime());
        String selection = "STRFTIME('%Y-%m',A_TRIP_DATE)" + "=? AND " + A_TRIP_TAKE_OFF_POINT + "=? AND " + A_TRIP_TYPE + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + A_TRIP_DATE + ",4)";

        String[] selectionArgs = new String[]{valueOf(tripDate),valueOf(from), valueOf(tripType)};

        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, grpBy,
                null, grpBy);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Trip> getAllTripsByTypeAndTo(String to,String tripType) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        tripDate = mdformat.format(calendar.getTime());
        String selection = "STRFTIME('%Y-%m',A_TRIP_DATE)" + "=? AND " + A_TRIP_DEST_NAME + "=? AND " + A_TRIP_TYPE + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + A_TRIP_DATE + ",4)";

        String[] selectionArgs = new String[]{valueOf(tripDate),valueOf(to), valueOf(tripType)};

        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, grpBy,
                null, grpBy);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Trip> getTripsByTypeAndFromAndTo(String tripType,String from,String to) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        tripDate = mdformat.format(calendar.getTime());
        String selection = "STRFTIME('%Y-%m',A_TRIP_DATE)" + "=? AND " + A_TRIP_TAKE_OFF_POINT + "=? AND "+ A_TRIP_DEST_NAME + "=? AND " + A_TRIP_TYPE + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + A_TRIP_DATE + ",4)";

        String[] selectionArgs = new String[]{tripDate,from,to, tripType};

        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, grpBy,
                null, grpBy);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }

    public ArrayList<Trip> getAllTripsByDate(String date) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "STRFTIME('%Y-%m-%d',A_TRIP_DATE)" + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + A_TRIP_DATE + ",4)";

        String[] selectionArgs = new String[]{valueOf(date)};

        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, grpBy,
                null, grpBy);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }

    public ArrayList<Trip> getAllTripsByTypeAndStateAndDate(String date,String tripType,String state) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "STRFTIME('%Y-%m-%d',A_TRIP_DATE)" + "=? AND " + A_TRIP_TYPE + "=?AND " + A_TRIP_STATE + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + A_TRIP_DATE + ",4)";

        String[] selectionArgs = new String[]{valueOf(date), valueOf(tripType),valueOf(state)};

        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, grpBy,
                null, grpBy);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }


    public ArrayList<Trip> getBoatTripForState(String state) {
        ArrayList<Trip> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = A_TRIP_STATE + "=?";
        String[] selectionArgs = new String[]{state};
        Cursor cursor = db.query(A_TRIP_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }

    public ArrayList<Trip> getAllTripsByType() {
        ArrayList<Trip> reportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(A_TRIP_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripFromCursor(reportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return reportArrayList;
    }



}
