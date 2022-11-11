package com.skylightapp.Database;

import static com.skylightapp.Classes.AppCash.APP_CASH_DATE;
import static com.skylightapp.Classes.AppCash.APP_CASH_TO;
import static com.skylightapp.Classes.CustomerDailyReport.DAILY_REPORT_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.MarketClasses.Market.MARKET_ADDRESS;
import static com.skylightapp.MarketClasses.Market.MARKET_COUNTRY;
import static com.skylightapp.MarketClasses.Market.MARKET_DATEJOINED;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_LAT;
import static com.skylightapp.MarketClasses.Market.MARKET_LGA;
import static com.skylightapp.MarketClasses.Market.MARKET_LNG;
import static com.skylightapp.MarketClasses.Market.MARKET_LOGO;
import static com.skylightapp.MarketClasses.Market.MARKET_NAME;
import static com.skylightapp.MarketClasses.Market.MARKET_PROF_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_REVENUE;
import static com.skylightapp.MarketClasses.Market.MARKET_STATE;
import static com.skylightapp.MarketClasses.Market.MARKET_STATUS;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.Market.MARKET_TOWN;
import static com.skylightapp.MarketClasses.Market.MARKET_TYPE;

import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.Market;

import java.util.ArrayList;

public class MarketDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = MARKET_ID
            + " =?";
    public MarketDAO(Context context) {
        super(context);
    }

    public void deleteMarket(long marketID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(MARKET_TABLE,
                    "MARKET_ID = ? ",
                    new String[]{String.valueOf((marketID))});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public long saveMarket(Market market) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Profile profile= null;
            if(market !=null){
                Values.put(MARKET_ID, market.getMarketID());
                Values.put(MARKET_NAME, market.getMarketName());
                Values.put(MARKET_TYPE, market.getMarketType());
                Values.put(MARKET_STATE, market.getMarketState());
                Values.put(MARKET_TOWN, market.getMarketTown());
                Values.put(MARKET_LGA, market.getMarketLGA());
                Values.put(MARKET_COUNTRY, market.getMarketCountry());
                Values.put(MARKET_STATUS, market.getMarketStatus());
                Values.put(MARKET_DATEJOINED, market.getMarketDateOfReg());
                Values.put(MARKET_PROF_ID, String.valueOf(market.getMarketProfID()));
                Values.put(MARKET_ADDRESS, market.getMarketAddress());
                Values.put(MARKET_LAT, market.getMarketLat());
                Values.put(MARKET_LNG, market.getMarketLng());
                profile = market.getMarketProf();

            }

            if (profile != null) {
                Values.put(MARKET_PROF_ID, profile.getPID());
            }

            db.insert(MARKET_TABLE,null,Values);
            //long id = db.insert(BIZ_TABLE, null, codeValues);
            //paymentCode.setUID(String.valueOf(id));
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public void SaveMarketArrayList(ArrayList<Market> marketArrayList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        String marketName=null;
        String marketType=null;
        String marketLGA=null;
        String marketState=null;
        int marketLogo=0;
        if(marketArrayList !=null){
            for (int i = 0; i < marketArrayList.size(); i++) {
                marketName=marketArrayList.get(i).getMarketName();
                marketLogo=marketArrayList.get(i).getMarketLogo();
                marketType=marketArrayList.get(i).getMarketType();
                marketLGA=marketArrayList.get(i).getMarketLGA();
                marketState=marketArrayList.get(i).getMarketState();
            }

            Values.put(MARKET_NAME, marketName);
            Values.put(MARKET_TYPE, marketType);
            Values.put(MARKET_STATE, marketState);
            Values.put(MARKET_LGA, marketLGA);
            Values.put(MARKET_COUNTRY, "Nigeria");
            Values.put(MARKET_LOGO, marketLogo);

            db.insert(MARKET_TABLE,null,Values);



        }
        db.close();




    }

    public ArrayList<Market> getMarketsFromProfile(int profileID) {
        try {
            ArrayList<Market> marketArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(MARKET_TABLE, null, MARKET_PROF_ID + "=?",
                    new String[]{String.valueOf(profileID)}, null, null,
                    null, null);
            getMarketsFromCursor(marketArrayList, cursor);
            cursor.close();
            return marketArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Market> getMarketsFromCustomer(int customerID) {
        try {
            ArrayList<Market> marketArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(MARKET_TABLE, null, MARKET_PROF_ID + "=?",
                    new String[]{String.valueOf(customerID)}, null, null,
                    null, null);
            getMarketsFromCursor(marketArrayList, cursor);
            cursor.close();
            return marketArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Market> getMarketsFromMarketID(int marketID) {
        try {
            ArrayList<Market> marketArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(MARKET_TABLE, null, MARKET_ID + "=?",
                    new String[]{String.valueOf(marketID)}, null, null,
                    null, null);
            getMarketsFromCursor(marketArrayList, cursor);
            cursor.close();
            return marketArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Market> getMarkets() {
        try {
            ArrayList<Market> marketArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(MARKET_TABLE, null, null, null, null,
                    null, null);
            getMarketsFromCursor(marketArrayList, cursor);
            cursor.close();
            return marketArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllMarketNames() {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MARKET_NAME + "=?";
            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(MARKET_TABLE,null,selection,null,null,null,null);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(7));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

    }
    public ArrayList<String> getAllMarketNamesForState(String state) {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MARKET_STATE + "=?";
            String[] selectionArgs = new String[]{state};
            Cursor res = db.query(MARKET_TABLE,null,selection,selectionArgs,null,null,null);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(1));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

    }
    public ArrayList<String> getAllMarketNamesForLGA(String lga) {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MARKET_LGA + "=?";
            String[] selectionArgs = new String[]{lga};
            Cursor res = db.query(MARKET_TABLE,null,selection,selectionArgs,null,null,null);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(1));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

    }
    public ArrayList<String> getAllMarketNamesForCountry(String country) {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MARKET_COUNTRY + "=?";
            String[] selectionArgs = new String[]{country};
            Cursor res = db.query(MARKET_TABLE,null,selection,selectionArgs,null,null,null);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(1));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

    }

    public ArrayList<String> getAllMarketNamesForStatus(String status) {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MARKET_STATUS + "=?";
            String[] selectionArgs = new String[]{status};
            Cursor res = db.query(MARKET_TABLE,null,selection,selectionArgs,null,null,null);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(1));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

    }
    public ArrayList<String> getAllMarketNamesForTown(String town) {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MARKET_TOWN + "=?";
            String[] selectionArgs = new String[]{town};
            Cursor res = db.query(MARKET_TABLE,null,selection,selectionArgs,null,null,null);
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    array_list.add(res.getString(1));
                    res.moveToNext();
                }
            }
            res.close();

            return array_list;


        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;

    }






    public ArrayList<Market> getAllMarketsForState(String state) {
        try {
            ArrayList<Market> market = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MARKET_STATE + "=?";
            String[] selectionArgs = new String[]{valueOf(state)};
            Cursor cursor = db.query(MARKET_TABLE, null, selection, selectionArgs, null,
                    null, null);

            getMarketsFromCursor(market, cursor);

            cursor.close();
            //db.close();

            return market;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<Market> getAllMarketsForCountry(String country) {
        try {
            ArrayList<Market> market = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MARKET_COUNTRY + "=?";
            String[] selectionArgs = new String[]{valueOf(country)};
            Cursor cursor = db.query(MARKET_TABLE, null, selection, selectionArgs, null,
                    null, null);
            getMarketsFromCursor(market, cursor);

            cursor.close();
            //db.close();

            return market;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getMarketsFromCursor(ArrayList<Market> marketArrayList, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int marketID = cursor.getColumnIndex(MARKET_ID);
                String name = cursor.getColumnName(1);
                String marketType = cursor.getColumnName(4);
                String address = cursor.getString(14);
                String town = cursor.getString(5);
                String lga = cursor.getString(6);
                String state = cursor.getString(7);
                String country = cursor.getString(8);
                String status = cursor.getString(12);
                Uri logo = Uri.parse(cursor.getString(9));
                double lat = cursor.getColumnIndex(MARKET_LAT);
                double lng = cursor.getColumnIndex(MARKET_LNG);
                double marketRev = cursor.getColumnIndex(MARKET_REVENUE);
                String dateOfJoined = cursor.getString(19);
                marketArrayList.add(new Market(marketID, name,address,marketType,town, lga, state, country,logo,status,lat,lng,marketRev,dateOfJoined));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void updateMarketLocation(int marketID,int profileID,double lat, double lng) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(MARKET_LAT, lat);
            cv.put(MARKET_LNG, lng);
            String selection = MARKET_ID + "=? AND " + MARKET_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(marketID), valueOf(profileID)};
            db.update(MARKET_TABLE,
                    cv, selection, selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public void updateMarketName(int marketID,int profileID,String marketName) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(MARKET_NAME, marketName);
            String selection = MARKET_ID + "=? AND " + MARKET_PROF_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(marketID), valueOf(profileID)};
            db.update(MARKET_TABLE,
                    cv, selection, selectionArgs);
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
