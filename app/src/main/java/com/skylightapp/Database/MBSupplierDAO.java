package com.skylightapp.Database;

import static com.skylightapp.Classes.GroupAccount.GRPA_AMT;
import static com.skylightapp.Classes.GroupAccount.GRPA_BALANCE;
import static com.skylightapp.Classes.GroupAccount.GRPA_CURRENCY_CODE;
import static com.skylightapp.Classes.GroupAccount.GRPA_DURATION;
import static com.skylightapp.Classes.GroupAccount.GRPA_EMAIL;
import static com.skylightapp.Classes.GroupAccount.GRPA_END_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPA_FIRSTNAME;
import static com.skylightapp.Classes.GroupAccount.GRPA_FREQ;
import static com.skylightapp.Classes.GroupAccount.GRPA_LINK;
import static com.skylightapp.Classes.GroupAccount.GRPA_PHONE;
import static com.skylightapp.Classes.GroupAccount.GRPA_PROFILE_ID;
import static com.skylightapp.Classes.GroupAccount.GRPA_PURPOSE;
import static com.skylightapp.Classes.GroupAccount.GRPA_SCORE;
import static com.skylightapp.Classes.GroupAccount.GRPA_START_DATE;
import static com.skylightapp.Classes.GroupAccount.GRPA_STATUS;
import static com.skylightapp.Classes.GroupAccount.GRPA_SURNAME;
import static com.skylightapp.Classes.GroupAccount.GRPA_TITTLE;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRP_ASSIGNED_ID;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIERZ_ADDRESS;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_BRANDNAME;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_COUNTRY;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_ID;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_MARKET_ID;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_NAME;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_PHONE_NO;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_PIX;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_PROF_ID;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_STATE;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_STATUS;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_TABLE;
import static com.skylightapp.MarketClasses.MarketBizSupplier.SUPPLIER_TYPE;
import static java.lang.String.valueOf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBizSupplier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MBSupplierDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = SUPPLIER_ID
            + " =?";
    public MBSupplierDAO(Context context) {
        super(context);
    }
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    public void updateSupplier(int bizID, int supplierID, String name, String address, String phoneNo,String type,String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues savingsUpdateValues = new ContentValues();
        savingsUpdateValues.put(SUPPLIER_NAME, name);
        savingsUpdateValues.put(SUPPLIERZ_ADDRESS, address);
        savingsUpdateValues.put(SUPPLIER_TYPE, type);
        savingsUpdateValues.put(SUPPLIER_STATUS, status);
        savingsUpdateValues.put(SUPPLIER_PHONE_NO, phoneNo);
        db.update(SUPPLIER_TABLE, savingsUpdateValues, SUPPLIER_ID + " = ?", new String[]{valueOf(supplierID)});
        db.close();

    }


    public long insertSupplier(long bizID,int supplierID, int marketID,int profileID, String name, String type, String brandName, String state, Uri pix, String country, String status) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUPPLIER_BIZ_ID, bizID);
        contentValues.put(SUPPLIER_PROF_ID, profileID);
        contentValues.put(SUPPLIER_ID, supplierID);
        contentValues.put(SUPPLIER_NAME, name);
        contentValues.put(SUPPLIER_TYPE, type);
        contentValues.put(SUPPLIER_BRANDNAME, brandName);
        contentValues.put(SUPPLIER_STATE, state);
        contentValues.put(SUPPLIER_PIX, String.valueOf(pix));
        contentValues.put(SUPPLIER_COUNTRY, country);
        contentValues.put(SUPPLIER_MARKET_ID, marketID);
        contentValues.put(SUPPLIER_STATUS, status);
        return sqLiteDatabase.insert(SUPPLIER_TABLE, null, contentValues);
    }

    private void getSupplierCursor(ArrayList<MarketBizSupplier> supplierArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            int supplierID = cursor.getInt(0);
            String name = cursor.getString(2);
            String type = cursor.getString(7);
            String state = cursor.getString(9);
            String country = cursor.getString(12);
            String status = cursor.getString(11);
            Uri pix = Uri.parse(cursor.getString(10));
            supplierArrayList.add(new MarketBizSupplier(supplierID,name,type,state,country, pix,status));
        }


    }

    public ArrayList<MarketBizSupplier> getSuppliersForBiz(int bizID) {
        ArrayList<MarketBizSupplier> suppliers = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = SUPPLIER_BIZ_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(bizID)};

        Cursor cursor = db.query(SUPPLIER_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSupplierCursor(suppliers,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return suppliers;
    }
    public ArrayList<MarketBizSupplier> getSuppliersForStateAndBiz(String state, int bizID) {
        ArrayList<MarketBizSupplier> suppliers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = SUPPLIER_STATE + "=? AND " + SUPPLIER_BIZ_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(state), valueOf(bizID)};
        Cursor cursor = db.query(SUPPLIER_TABLE, null, selection, selectionArgs, null,
                null, null);
        if (null != cursor)
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                getSupplierCursor(suppliers,cursor);
            }
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return suppliers;
    }




    public ArrayList<MarketBizSupplier> getSuppliersForBiz22(int bizID) {
        try {
            ArrayList<MarketBizSupplier> suppliers = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            try (Cursor cursor = db.rawQuery("SELECT * FROM " + SUPPLIER_TABLE, new String[]{" WHERE SUPPLIER_BIZ_ID=?",String.valueOf(bizID)})){

                if (cursor.moveToFirst()) {

                    do {
                        MarketBizSupplier bizSupplier = new MarketBizSupplier();
                        bizSupplier.setmBSupplierID(cursor.getInt(0));
                        bizSupplier.setmBSupplierName(cursor.getString(2));
                        bizSupplier.setmBSupplierType(cursor.getString(7));
                        bizSupplier.setmBSupplierPix(Uri.parse(cursor.getString(10)));
                        bizSupplier.setmBSupplierState(cursor.getString(9));
                        bizSupplier.setmBSupplierCountry(cursor.getString(12));
                        bizSupplier.setmBSupplierStatus(cursor.getString(11));

                        suppliers.add(bizSupplier);
                    } while (cursor.moveToNext());


                }
                return suppliers;
            }

            //return profiles;
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<MarketBizSupplier> getAllSuppliers() {
        ArrayList<MarketBizSupplier> suppliers = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT * FROM " + SUPPLIER_TABLE, null)){

            if (cursor.moveToFirst()) {

                do {
                    MarketBizSupplier bizSupplier = new MarketBizSupplier();
                    bizSupplier.setmBSupplierID(cursor.getInt(0));
                    bizSupplier.setmBSupplierName(cursor.getString(2));
                    bizSupplier.setmBSupplierType(cursor.getString(7));
                    bizSupplier.setmBSupplierPix(Uri.parse(cursor.getString(10)));
                    bizSupplier.setmBSupplierState(cursor.getString(9));
                    bizSupplier.setmBSupplierCountry(cursor.getString(12));
                    bizSupplier.setmBSupplierStatus(cursor.getString(11));

                    suppliers.add(bizSupplier);
                } while (cursor.moveToNext());


            }
            return suppliers;
        }


    }

    public void deleteSupplier(int supplierID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(SUPPLIER_TABLE, SUPPLIER_ID + "=?",
                    new String[]{String.valueOf(supplierID)});
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }
    public MarketBizSupplier getSupplier(int bizID,int supplierID) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = SUPPLIER_BIZ_ID + "=? AND " + SUPPLIER_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(bizID), valueOf(supplierID)};
            try (Cursor cursor = db.query(SUPPLIER_TABLE, null, selection, selectionArgs, null, null, null, null)) {

                if (cursor != null) {
                    MarketBizSupplier bizSupplier = new MarketBizSupplier();
                    bizSupplier.setmBSupplierID(cursor.getInt(0));
                    bizSupplier.setmBSupplierName(cursor.getString(2));
                    bizSupplier.setmBSupplierType(cursor.getString(7));
                    bizSupplier.setmBSupplierPix(Uri.parse(cursor.getString(10)));
                    bizSupplier.setmBSupplierState(cursor.getString(9));
                    bizSupplier.setmBSupplierCountry(cursor.getString(12));
                    bizSupplier.setmBSupplierStatus(cursor.getString(11));
                }
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return null;
    }
    public void updateSupplierStatus(int supplierID,String status) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues savingsUpdateValues = new ContentValues();
            savingsUpdateValues.put(SUPPLIER_STATUS, status);
            db.update(SUPPLIER_TABLE, savingsUpdateValues, SUPPLIER_ID + " = ?", new String[]{valueOf(supplierID)});
            db.close();


        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
}
