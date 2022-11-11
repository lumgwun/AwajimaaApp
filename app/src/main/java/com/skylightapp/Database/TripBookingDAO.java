package com.skylightapp.Database;

import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_AMOUNT_ADULT;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_AMT_CHILDREN;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_DATE;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_DEST_NAME;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_ENDT;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_ID;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_NO_OF_SIT;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_PROF_ID;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_START_TIME;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_STATE;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_STATUS;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TABLE;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TAKE_OFF_LATLNG;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TAKE_OFF_POINT;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_AMT;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_DATE;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_DEST;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_MOP;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_NIN;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_PROF_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_SLOTS;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_STATUS;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_TABLE;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_TRIP_ID;
import static com.skylightapp.Bookings.TripBooking.TRIP_BOOKING_TYPEOS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;

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
        contentValues.put(TRIP_BOOKING_NIN, nin);
        contentValues.put(TRIP_BOOKING_SLOTS, noOfSits);
        contentValues.put(TRIP_BOOKING_TYPEOS, tripType);
        contentValues.put(TRIP_BOOKING_STATUS, status);
        contentValues.put(TRIP_BOOKING_AMT, amt);
        contentValues.put(TRIP_BOOKING_MOP, modeOfPayment);
        contentValues.put(TRIP_BOOKING_DATE, date);
        return sqLiteDatabase.insert(BOAT_TRIP_TABLE, null, contentValues);

    }
}
