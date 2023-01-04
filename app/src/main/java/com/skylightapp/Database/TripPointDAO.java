package com.skylightapp.Database;

import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_ID;
import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_TABLE;
import static com.skylightapp.Bookings.TripStopPoint.TRIP_STOP_POINT_ID;
import static com.skylightapp.Bookings.TripStopPoint.TRIP_STOP_POINT_TABLE;
import static com.skylightapp.Bookings.TripStopPoint.TSP_BIZ_ID;
import static com.skylightapp.Bookings.TripStopPoint.TSP_LATLNG;
import static com.skylightapp.Bookings.TripStopPoint.TSP_NAME;
import static com.skylightapp.Bookings.TripStopPoint.TSP_STATE;
import static com.skylightapp.Bookings.TripStopPoint.TSP_STATUS;
import static com.skylightapp.Classes.Customer.CUSTOMER_STATUS;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Customer.CUS_BIZ_ID1;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Bookings.TripRoute;
import com.skylightapp.Bookings.TripStopPoint;
import com.skylightapp.Classes.Customer;

import java.util.ArrayList;

public class TripPointDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = TRIP_STOP_POINT_ID
            + " =?";
    public TripPointDAO(Context context) {
        super(context);
    }
    SQLiteDatabase db = this.getWritableDatabase();
    public long addTripStopPoint(TripStopPoint stopPoint) {
        ContentValues values = new ContentValues();
        values.put(TRIP_STOP_POINT_ID, stopPoint.getbTSPId());
        values.put(TSP_NAME, stopPoint.getbTSPName());
        values.put(TSP_STATE, stopPoint.getbTSPState());
        values.put(TSP_LATLNG, stopPoint.getBtSPLatLngStrng());
        values.put(TSP_STATUS, stopPoint.getbTSPStatus());
        return db.insert(TRIP_STOP_POINT_TABLE, null, values);
    }

    public long addTripStopPoints(ArrayList<TripStopPoint> tripStopPoints) {
        int count = 0;
        for (int i = 0; i < tripStopPoints.size(); i++) {
            TripStopPoint stopPoint = tripStopPoints.get(i);
            ContentValues values = new ContentValues();
            values.put(TRIP_STOP_POINT_ID, stopPoint.getbTSPId());
            values.put(TSP_NAME, stopPoint.getbTSPName());
            values.put(TSP_STATE, stopPoint.getbTSPState());
            values.put(TSP_LATLNG, stopPoint.getBtSPLatLngStrng());
            values.put(TSP_STATUS, stopPoint.getbTSPStatus());
            long id = db.insert(TRIP_STOP_POINT_TABLE, null, values);
            if (id != -1)
                count += 1;
        }
        return count;

    }

    public ArrayList<LatLng> getTripStopPointsLatLng() {
        ArrayList<LatLng> points = new ArrayList<LatLng>();
        String selectQuery = "SELECT  * FROM " + TRIP_STOP_POINT_TABLE;
        try {

            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {

                    LatLng latLng = new LatLng(Double.parseDouble(cursor
                            .getString(9)), Double.parseDouble(cursor
                            .getString(10)));
                    points.add(latLng);
                } while (cursor.moveToNext());
            }
            cursor.close();
            return points;
        } catch (Exception e) {
            Log.d("Error in getting users from DB", e.getMessage());
            return null;
        }
    }



    public boolean isTripStopPointExists() {
        String selectQuery = "SELECT * from " + TRIP_STOP_POINT_TABLE;
        boolean isExists = false;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            isExists = true;
            cursor.close();
        }
        return isExists;
    }
    public void deleteTripStopPoint(int tspID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = TRIP_STOP_POINT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(tspID)};
        db.delete(TRIP_STOP_POINT_TABLE, selection, selectionArgs);

    }
    private void getTripStopPointFromCursor(ArrayList<TripStopPoint> tripStopPoints, Cursor cursor) {
        while (cursor.moveToNext()) {
            int tspId = cursor.getInt(1);
            String placeName = cursor.getString(3);
            String latLng = cursor.getString(4);
            double amount = cursor.getDouble(5);
            String status = cursor.getString(6);
            String state = cursor.getString(8);
            tripStopPoints.add(new TripStopPoint(tspId, placeName,amount,latLng,state, status));
        }

    }
    public ArrayList<TripStopPoint> getStopPointForBizState(long bizID, String state) {
        ArrayList<TripStopPoint> tripStopPoints = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = TSP_BIZ_ID + "=? AND " + TSP_STATE + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID), valueOf(state)};

        Cursor cursor = db.query(TRIP_STOP_POINT_TABLE, null,  selection, selectionArgs, null, null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getTripStopPointFromCursor(tripStopPoints, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }
        db.close();

        cursor.close();

        return tripStopPoints;
    }

}
