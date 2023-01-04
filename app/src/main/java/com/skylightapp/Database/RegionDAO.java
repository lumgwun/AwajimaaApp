package com.skylightapp.Database;

import static com.skylightapp.Bookings.Driver.DRIVER_ID;
import static com.skylightapp.Bookings.Driver.DRIVER_JOINED_D;
import static com.skylightapp.Bookings.Driver.DRIVER_NAME;
import static com.skylightapp.Bookings.Driver.DRIVER_PICTURE;
import static com.skylightapp.Bookings.Driver.DRIVER_POSITION;
import static com.skylightapp.Bookings.Driver.DRIVER_PROF_ID;
import static com.skylightapp.Bookings.Driver.DRIVER_STATUS;
import static com.skylightapp.Bookings.Driver.DRIVER_TABLE;
import static com.skylightapp.Bookings.Driver.DRIVER_VEHICLE;
import static com.skylightapp.Classes.Customer.CUSTOMER_BIZ_STATUS;
import static com.skylightapp.Classes.Customer.CUSTOMER_ROLE;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_DEST_LAT;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_DEST_LNG;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_DURATION;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_ID;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_MODE;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_ORIGIN_LAT;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_ORIGIN_LNG;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_ROUTING;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_STATE;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_TABLE;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_TEXT;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_TIME;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_UNIT;
import static com.skylightapp.MapAndLoc.Distance.DISTANCE_VALUE;
import static com.skylightapp.MapAndLoc.Region.REGION_DATE_TIME;
import static com.skylightapp.MapAndLoc.Region.REGION_DID_ENTER;
import static com.skylightapp.MapAndLoc.Region.REGION_DISTANCE;
import static com.skylightapp.MapAndLoc.Region.REGION_DISTANCE_TEXT;
import static com.skylightapp.MapAndLoc.Region.REGION_DURATION;
import static com.skylightapp.MapAndLoc.Region.REGION_DURATION_TEXT;
import static com.skylightapp.MapAndLoc.Region.REGION_ID;
import static com.skylightapp.MapAndLoc.Region.REGION_IS_CURRENTLY_INSIDE;
import static com.skylightapp.MapAndLoc.Region.REGION_LAT;
import static com.skylightapp.MapAndLoc.Region.REGION_LNG;
import static com.skylightapp.MapAndLoc.Region.REGION_PARAM_TYPE;
import static com.skylightapp.MapAndLoc.Region.REGION_RADIUS;
import static com.skylightapp.MapAndLoc.Region.REGION_STATE;
import static com.skylightapp.MapAndLoc.Region.REGION_STATUS;
import static com.skylightapp.MapAndLoc.Region.REGION_TABLE;
import static com.skylightapp.MapAndLoc.Region.REGION_TYPE;
import static com.skylightapp.MapAndLoc.Region.REGION_TYPE_EXP_SPEED;
import static com.skylightapp.MapAndLoc.Visit.VISIT_ID;
import static com.skylightapp.MapAndLoc.Visit.VISIT_STATUS;
import static com.skylightapp.MapAndLoc.Visit.VISIT_TABLE;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.MapAndLoc.Distance;
import com.skylightapp.MapAndLoc.Region;
import com.skylightapp.MapAndLoc.Visit;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RegionDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = REGION_ID
            + " =?";
    @NotNull
    public ArrayList<Region> regionIsochrone;

    public RegionDAO(Context context) {
        super(context);
    }
    public int getRegionCount() {
        String countQuery = "SELECT * FROM " + REGION_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }
    public long insertRegion(int regionID, double lat,double lng,String type,String paramType,double radius,long dateTime,String didEnter,String isCurrentlyInside,int distance, String distanceText,int duration,String durationText,float speed,String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REGION_ID, regionID);
        contentValues.put(REGION_LAT, lat);
        contentValues.put(REGION_LNG, lng);
        contentValues.put(REGION_TYPE, type);
        contentValues.put(REGION_PARAM_TYPE, paramType);
        contentValues.put(REGION_RADIUS, radius);
        contentValues.put(REGION_DID_ENTER, didEnter);
        contentValues.put(REGION_IS_CURRENTLY_INSIDE, isCurrentlyInside);
        contentValues.put(REGION_DISTANCE, distance);
        contentValues.put(REGION_DISTANCE_TEXT, distanceText);
        contentValues.put(REGION_DURATION, duration);
        contentValues.put(REGION_DURATION_TEXT, durationText);
        contentValues.put(REGION_TYPE_EXP_SPEED, speed);
        contentValues.put(REGION_DATE_TIME, dateTime);
        contentValues.put(REGION_STATUS, status);

        return sqLiteDatabase.insert(REGION_TABLE, null, contentValues);

    }
    public void createRegion(@NotNull Region region) {

    }
    @NotNull
    public Region getRegionFromId(@NotNull String newId) {
        return null;
    }
    public void deleteRegion(String regionID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REGION_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(regionID)};
        db.delete(REGION_TABLE, selection, selectionArgs);

    }
    public void deleteRegion(int regionID,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = REGION_ID + "=? AND " + REGION_DATE_TIME + "=?";
        String[] selectionArgs = new String[]{valueOf(regionID), valueOf(date)};
        db.delete(REGION_TABLE, selection, selectionArgs);

    }
    public void updateRegion(@NotNull Region region) {
        SQLiteDatabase db = this.getWritableDatabase();
        int regiontID=0;
        String status="null";
        ContentValues visitUpdateValues = new ContentValues();
        if(region !=null){
            regiontID=region.getId();
            status=region.getStatus();
        }
        String selection = REGION_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(regiontID)};
        visitUpdateValues.put(REGION_STATUS, status);
        db.update(REGION_TABLE, visitUpdateValues, selection, selectionArgs);


    }


    private void getRegionFromCursor(ArrayList<Region> arrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int regionID = cursor.getInt(2);
            double Lat = cursor.getDouble(2);
            double Lng = cursor.getDouble(3);
            long time = cursor.getLong(4);
            boolean isEnter = Boolean.parseBoolean(cursor.getString(5));
            double radius = cursor.getInt(6);
            boolean isCurrentlyInside = Boolean.parseBoolean(cursor.getString(8));
            int distance = cursor.getInt(9);
            String distanceText = cursor.getString(10);
            int duration = cursor.getInt(11);
            String durationText = cursor.getString(12);
            String type = cursor.getString(13);
            float speed = cursor.getFloat(14);
            String status = cursor.getString(15);
            String paramType = cursor.getString(16);
            String state = cursor.getString(17);

            arrayList.add(new Region(regionID,Lat,Lng,time,isEnter,radius,isCurrentlyInside ,distance,distanceText,duration,durationText,type,speed,paramType,status));
        }

    }

    public ArrayList<Region> getReportRegions(int regionID) {
        ArrayList<Region> driverArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = REGION_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(regionID)};

        Cursor cursor = db.query(REGION_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getRegionFromCursor(driverArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return driverArrayList;

    }
    public ArrayList<Region> getAllReportRegions() {
        ArrayList<Region> driverArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(REGION_TABLE, null,  null, null, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getRegionFromCursor(driverArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return driverArrayList;

    }

    public ArrayList<Region> getAllStateRegions(String state) {
        ArrayList<Region> driverArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = REGION_STATE + "=?";
        String[] selectionArgs = new String[]{String.valueOf(state)};

        Cursor cursor = db.query(REGION_TABLE, null,  selection, selectionArgs, null,
                null, null);
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getRegionFromCursor(driverArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();

        return driverArrayList;
    }


    @NotNull
    public Object getRegionCircle() {
        return null;
    }
}
