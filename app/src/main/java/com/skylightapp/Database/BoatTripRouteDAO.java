package com.skylightapp.Database;

import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_COUNTRY;
import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_FROM;
import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_ID;
import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_STATE;
import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_STATUS;
import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_TABLE;
import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_THROUGH;
import static com.skylightapp.Bookings.TripRoute.BOAT_TRIP_ROUTE_TO;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skylightapp.Bookings.TripRoute;

import java.util.ArrayList;

public class BoatTripRouteDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = BOAT_TRIP_ROUTE_ID
            + " =?";
    public BoatTripRouteDAO(Context context) {
        super(context);
    }
    public int getBoatTripRouteCount() {
        String countQuery = "SELECT * FROM " + BOAT_TRIP_ROUTE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertBoatTripRoute(int routeID, String from, String to,String through, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOAT_TRIP_ROUTE_ID, routeID);
        contentValues.put(BOAT_TRIP_ROUTE_FROM, from);
        contentValues.put(BOAT_TRIP_ROUTE_TO, to);
        contentValues.put(BOAT_TRIP_ROUTE_THROUGH, through);
        contentValues.put(BOAT_TRIP_ROUTE_STATUS, status);
        return sqLiteDatabase.insert(BOAT_TRIP_ROUTE_TABLE, null, contentValues);

    }
    public void deleteBoatTripRoute(int routeID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = BOAT_TRIP_ROUTE_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(routeID)};
        db.delete(BOAT_TRIP_ROUTE_TABLE, selection, selectionArgs);

    }
    private void getBoatTripRouteFromCursor(ArrayList<TripRoute> tripRoutes, Cursor cursor) {
        while (cursor.moveToNext()) {
            int routeId = cursor.getInt(1);
            String to = cursor.getString(3);
            String from = cursor.getString(2);
            String through = cursor.getString(4);
            String status = cursor.getString(5);
            tripRoutes.add(new TripRoute(routeId, to,from,through, status));
        }

    }
    private void getBoatTripRouteFromCursorState(ArrayList<TripRoute> tripRoutes, Cursor cursor) {
        while (cursor.moveToNext()) {
            int routeId = cursor.getInt(1);
            String to = cursor.getString(3);
            String from = cursor.getString(2);
            String through = cursor.getString(4);
            String status = cursor.getString(5);
            String state = cursor.getString(8);
            tripRoutes.add(new TripRoute(routeId, to,from,through, state,status));
        }

    }
    private void getBoatTripRouteFromCursorCountry(ArrayList<TripRoute> tripRoutes, Cursor cursor) {
        while (cursor.moveToNext()) {
            int routeId = cursor.getInt(1);
            String to = cursor.getString(3);
            String from = cursor.getString(2);
            String through = cursor.getString(4);
            String status = cursor.getString(5);
            String state = cursor.getString(8);
            String country = cursor.getString(8);
            tripRoutes.add(new TripRoute(routeId, to,from,through,state,country, status));
        }

    }
    public ArrayList<TripRoute> getBoatTripRouteForCountry(String country) {
        ArrayList<TripRoute> tripRoutes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BOAT_TRIP_ROUTE_COUNTRY + "=?";
        String[] selectionArgs = new String[]{country};

        Cursor cursor = db.query(BOAT_TRIP_ROUTE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getBoatTripRouteFromCursorCountry(tripRoutes, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripRoutes;
    }
    public ArrayList<TripRoute> getAllCurrentBoatTripRoute(String status) {
        ArrayList<TripRoute> tripRoutes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BOAT_TRIP_ROUTE_STATUS + "=?";
        String[] selectionArgs = new String[]{status};

        Cursor cursor = db.query(BOAT_TRIP_ROUTE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getBoatTripRouteFromCursorCountry(tripRoutes, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripRoutes;
    }
    public ArrayList<TripRoute> getBoatTripRouteForState(String state) {
        ArrayList<TripRoute> tripRoutes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BOAT_TRIP_ROUTE_STATE + "=?";
        String[] selectionArgs = new String[]{state};
        Cursor cursor = db.query(BOAT_TRIP_ROUTE_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getBoatTripRouteFromCursorState(tripRoutes, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripRoutes;
    }

    public ArrayList<TripRoute> getAllBoatTripRoutes() {
        ArrayList<TripRoute> reportArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(BOAT_TRIP_ROUTE_TABLE, null, null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getBoatTripRouteFromCursor(reportArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return reportArrayList;
    }

}
