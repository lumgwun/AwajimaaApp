package com.skylightapp.Database;

import static com.skylightapp.Bookings.Trip.A_TRIP_AMOUNT_ADULT;
import static com.skylightapp.Bookings.Trip.A_TRIP_AMT_CHILDREN;
import static com.skylightapp.Bookings.Trip.A_TRIP_DATE;
import static com.skylightapp.Bookings.Trip.A_TRIP_DEST_NAME;
import static com.skylightapp.Bookings.Trip.A_TRIP_ENDT;
import static com.skylightapp.Bookings.Trip.A_TRIP_ID;
import static com.skylightapp.Bookings.Trip.A_TRIP_NO_OF_SIT;
import static com.skylightapp.Bookings.Trip.A_TRIP_PROF_ID;
import static com.skylightapp.Bookings.Trip.A_TRIP_START_TIME;
import static com.skylightapp.Bookings.Trip.A_TRIP_STATE;
import static com.skylightapp.Bookings.Trip.A_TRIP_STATUS;
import static com.skylightapp.Bookings.Trip.A_TRIP_TABLE;
import static com.skylightapp.Bookings.Trip.A_TRIP_TAKE_OFF_LATLNG;
import static com.skylightapp.Bookings.Trip.A_TRIP_TAKE_OFF_POINT;
import static com.skylightapp.Bookings.Trip.A_TRIP_TYPE;
import static com.skylightapp.Inventory.Stocks.STOCKS_TABLE;
import static com.skylightapp.Inventory.Stocks.STOCK_ID;
import static com.skylightapp.Inventory.Stocks.STOCK_QTY;
import static com.skylightapp.MapAndLoc.Visit.VISIT_ACCURACY;
import static com.skylightapp.MapAndLoc.Visit.VISIT_DATE_TIME;
import static com.skylightapp.MapAndLoc.Visit.VISIT_DURATION;
import static com.skylightapp.MapAndLoc.Visit.VISIT_END_TIME;
import static com.skylightapp.MapAndLoc.Visit.VISIT_ID;
import static com.skylightapp.MapAndLoc.Visit.VISIT_LAT;
import static com.skylightapp.MapAndLoc.Visit.VISIT_LNG;
import static com.skylightapp.MapAndLoc.Visit.VISIT_NB_POINT;
import static com.skylightapp.MapAndLoc.Visit.VISIT_REGION_ID;
import static com.skylightapp.MapAndLoc.Visit.VISIT_REPORT_ID;
import static com.skylightapp.MapAndLoc.Visit.VISIT_START_TIME;
import static com.skylightapp.MapAndLoc.Visit.VISIT_STATE;
import static com.skylightapp.MapAndLoc.Visit.VISIT_STATUS;
import static com.skylightapp.MapAndLoc.Visit.VISIT_TABLE;
import static com.skylightapp.MapAndLoc.Visit.VISIT_UUID;

import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Bookings.Trip;
import com.skylightapp.MapAndLoc.Visit;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class VisitDAO extends DBHelperDAO{

    private static final String WHERE_ID_EQUALS = VISIT_ID
            + " =?";
    private Calendar calendar;
    private String tripDate;

    public VisitDAO(Context context) {
        super(context);
    }
    public int getVisitCount() { String countQuery = "SELECT * FROM " + VISIT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertNewVisit(int visitID, int reportID, int regionID, String uuid,  String dateTime, long startTime, long endTime, double lat,double lng,float accuracy,int nbPoint,long duration,String state,String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VISIT_ID, visitID);
        contentValues.put(VISIT_UUID, uuid);
        contentValues.put(VISIT_LAT, lat);
        contentValues.put(VISIT_LNG, lng);
        contentValues.put(VISIT_ACCURACY, accuracy);
        contentValues.put(VISIT_START_TIME, startTime);
        contentValues.put(VISIT_END_TIME, endTime);
        contentValues.put(VISIT_NB_POINT, nbPoint);
        contentValues.put(VISIT_DURATION, duration);
        contentValues.put(VISIT_STATUS, status);
        contentValues.put(VISIT_REPORT_ID, reportID);
        contentValues.put(VISIT_REGION_ID, regionID);
        contentValues.put(VISIT_DATE_TIME, dateTime);
        contentValues.put(VISIT_STATE, state);
        return sqLiteDatabase.insert(VISIT_TABLE, null, contentValues);

    }
    public void deleteVisit(int visitID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = VISIT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(visitID)};
        db.delete(VISIT_TABLE, selection, selectionArgs);

    }
    private void getVisitFromCursor(ArrayList<Visit> visitArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int visitId = cursor.getInt(1);
            String uuid = cursor.getString(2);
            double lat = cursor.getDouble(3);
            double lng = cursor.getDouble(4);
            long startTime = cursor.getLong(5);
            long endTime = cursor.getLong(6);
            int nbPoint = cursor.getInt(7);
            long duration = cursor.getLong(8);
            int isUpload = cursor.getInt(9);
            float accuracy = cursor.getFloat(10);
            String status = cursor.getString(11);
            String dateTime = cursor.getString(12);
            String state = cursor.getString(13);
            int reportID = cursor.getInt(14);
            int regionID = cursor.getInt(15);
            visitArrayList.add(new Visit(visitId,reportID,regionID,duration,lat,lng, uuid,dateTime,startTime,endTime,nbPoint,accuracy,isUpload,state,status));
        }

    }
    public ArrayList<Visit> getAllVisitsByState(String state) {
        ArrayList<Visit> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = VISIT_STATE + "=?";
        String[] selectionArgs = new String[]{state};
        Cursor cursor = db.query(VISIT_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getVisitFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Visit> getAllVisitsByStateAndDate(String state,String date) {
        ArrayList<Visit> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = VISIT_STATE + "=?AND " + VISIT_DATE_TIME + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + VISIT_DATE_TIME + ",4)";

        String[] selectionArgs = new String[]{valueOf(state),valueOf(date)};
        Cursor cursor = db.query(VISIT_TABLE, null,  selection, selectionArgs, grpBy,
                null, VISIT_DATE_TIME);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getVisitFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Visit> getAllVisitsByStateAndStatus(String state,String status) {
        ArrayList<Visit> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = VISIT_STATE + "=?AND " + VISIT_STATUS + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + VISIT_DATE_TIME + ",4)";

        String[] selectionArgs = new String[]{valueOf(state),valueOf(status)};
        Cursor cursor = db.query(VISIT_TABLE, null,  selection, selectionArgs, grpBy,
                null, VISIT_DATE_TIME);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getVisitFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Visit> getVisitsByStateAndFromAndTo(String from,String to,String state) {
        ArrayList<Visit> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        tripDate = mdformat.format(calendar.getTime());
        String selection = "STRFTIME('%Y-%m',VISIT_DATE_TIME)" + "=? AND " + "STRFTIME('%Y-%m',VISIT_DATE_TIME)" + "=? AND "+ "STRFTIME('%Y-%m',VISIT_DATE_TIME)" + "=? AND " + VISIT_STATE + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + VISIT_DATE_TIME + ",4)";

        String[] selectionArgs = new String[]{tripDate,from,to, state};

        Cursor cursor = db.query(VISIT_TABLE, null,  selection, selectionArgs, grpBy,
                null, grpBy);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getVisitFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }

    public ArrayList<Visit> getAllVisitsByStatusAndDate(String date,String status) {
        ArrayList<Visit> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //String selection = "STRFTIME('%Y-%m-%d',VISIT_DATE_TIME)" + "=?";
        String selection1 = "substr(" + VISIT_DATE_TIME + ",4)" + "=? AND " + VISIT_STATUS + "=?";
        String grpBy = "substr(" + VISIT_DATE_TIME + ",4)";
        String[] selectionArgs = new String[]{valueOf(date), status};

        Cursor cursor = db.query(VISIT_TABLE, null,  selection1, selectionArgs, grpBy,
                null, grpBy);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getVisitFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }

    public ArrayList<Visit> getAllVisitsByReportAndStateAndDate(String date,int reportID,String state) {
        ArrayList<Visit> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "STRFTIME('%Y-%m-%d',BOAT_TRIP_DATE)" + "=? AND " + VISIT_REPORT_ID + "=?AND " + VISIT_STATE + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + VISIT_DATE_TIME + ",4)";

        String[] selectionArgs = new String[]{valueOf(date), valueOf(reportID),valueOf(state)};

        Cursor cursor = db.query(VISIT_TABLE, null,  selection, selectionArgs, grpBy,
                null, grpBy);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getVisitFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public ArrayList<Visit> getAllVisitsByStatusAndStateAndDate(String date,String status,String state) {
        ArrayList<Visit> tripArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = "STRFTIME('%Y-%m-%d',BOAT_TRIP_DATE)" + "=? AND " + VISIT_STATUS + "=?AND " + VISIT_STATE + "=?";
        //String selection1 = "substr(" + REPORT_DATE + ",4)" + "=? AND " + CUSTOMER_ID + "=?";
        String grpBy = "substr(" + VISIT_DATE_TIME + ",4)";

        String[] selectionArgs = new String[]{valueOf(date), status,valueOf(state)};

        Cursor cursor = db.query(VISIT_TABLE, null,  selection, selectionArgs, grpBy,
                null, grpBy);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getVisitFromCursor(tripArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return tripArrayList;
    }
    public void updateStaticPosition(@NotNull Visit lastVisit) {
        SQLiteDatabase db = this.getWritableDatabase();
        int visitID=0;
        String status="null";
        ContentValues visitUpdateValues = new ContentValues();
        if(lastVisit !=null){
            visitID=lastVisit.getId();
            status=lastVisit.getVisitStatus();
        }
        String selection = VISIT_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(visitID)};
        visitUpdateValues.put(VISIT_STATUS, status);
        db.update(VISIT_TABLE, visitUpdateValues, selection, selectionArgs);


    }


}
