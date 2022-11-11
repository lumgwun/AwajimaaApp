package com.skylightapp.Database;

import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_AMOUNT_ADULT;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_AMT_CHILDREN;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_DATE;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_DEST_NAME;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_ENDT;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_ID;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_NO_OF_BOATS;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_NO_OF_SIT;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_PROF_ID;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_START_TIME;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_STATE;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_STATUS;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TABLE;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TAKE_OFF_LATLNG;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TAKE_OFF_POINT;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TOTAL_AMT;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TYPE;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TYPE_OF_BOAT;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_COUNTRY;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_FROM;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_ID;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_STATE;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_STATUS;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_TABLE;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_THROUGH;
import static com.skylightapp.Bookings.BoatTripRoute.BOAT_TRIP_ROUTE_TO;
import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Bookings.BoatTrip;
import com.skylightapp.Bookings.BoatTripRoute;

import java.util.ArrayList;

public class BoatTripDAO  extends DBHelperDAO{

    private static final String WHERE_ID_EQUALS = BOAT_TRIP_ID
            + " =?";
    public BoatTripDAO(Context context) {
        super(context);
    }
    public int getBoatTripCount() {
        String countQuery = "SELECT * FROM " + BOAT_TRIP_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertBoatTrip(int tripID, int profID, String state, double amtForAdult, double amtForChildren, int noOfSits, String takeOffPoint, String destination, String date, String startTime, String endTime, LatLng takeOffLatLng, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOAT_TRIP_ID, tripID);
        contentValues.put(BOAT_TRIP_PROF_ID, profID);
        contentValues.put(BOAT_TRIP_STATE, state);
        contentValues.put(BOAT_TRIP_AMOUNT_ADULT, amtForAdult);
        contentValues.put(BOAT_TRIP_AMT_CHILDREN, amtForChildren);
        contentValues.put(BOAT_TRIP_NO_OF_SIT, noOfSits);
        contentValues.put(BOAT_TRIP_DEST_NAME, destination);
        contentValues.put(BOAT_TRIP_STATUS, status);
        contentValues.put(BOAT_TRIP_START_TIME, startTime);
        contentValues.put(BOAT_TRIP_ENDT, endTime);
        contentValues.put(BOAT_TRIP_TAKE_OFF_POINT, takeOffPoint);
        contentValues.put(BOAT_TRIP_DATE, date);
        contentValues.put(BOAT_TRIP_TAKE_OFF_LATLNG, String.valueOf(takeOffLatLng));
        return sqLiteDatabase.insert(BOAT_TRIP_TABLE, null, contentValues);

    }
    public long insertBoatTrip(int tripID, int profID,int noOfBoatInt,String selectedBoatType, String takeOffPoint,String  destination,String state,String date,String selectedTripType) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOAT_TRIP_ID, tripID);
        contentValues.put(BOAT_TRIP_PROF_ID, profID);
        contentValues.put(BOAT_TRIP_STATE, state);
        contentValues.put(BOAT_TRIP_NO_OF_BOATS, noOfBoatInt);
        contentValues.put(BOAT_TRIP_TYPE_OF_BOAT, selectedBoatType);
        contentValues.put(BOAT_TRIP_DEST_NAME, destination);
        contentValues.put(BOAT_TRIP_TYPE, selectedTripType);
        contentValues.put(BOAT_TRIP_TAKE_OFF_POINT, takeOffPoint);
        contentValues.put(BOAT_TRIP_DATE, date);
        return sqLiteDatabase.insert(BOAT_TRIP_TABLE, null, contentValues);

    }
    public void deleteBoatTrip(int tripID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = BOAT_TRIP_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tripID)};
        db.delete(BOAT_TRIP_TABLE, selection, selectionArgs);

    }
    private void getBoatTripFromCursor(ArrayList<BoatTrip> boatTripArrayList, Cursor cursor) {
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
            boatTripArrayList.add(new BoatTrip(tripId, profId,tripNoOfSits,takeOffPoint,destPoint,takeOffLatLng, tripState,tripDate,tripAdultAmt,tripChildrenAmt,status));
        }

    }

    public ArrayList<BoatTrip> getBoatTripForState(String state) {
        ArrayList<BoatTrip> boatTripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BOAT_TRIP_STATE + "=?";
        String[] selectionArgs = new String[]{state};
        Cursor cursor = db.query(BOAT_TRIP_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getBoatTripFromCursor(boatTripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return boatTripArrayList;
    }

    public ArrayList<BoatTrip> getAllBoatTrips() {
        ArrayList<BoatTrip> reportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BOAT_TRIP_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getBoatTripFromCursor(reportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return reportArrayList;
    }

}
