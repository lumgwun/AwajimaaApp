package com.skylightapp.Database;

import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_BRAND_NAME;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_COUNTRY;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_GENDER;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_ID;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_MARKET_NAME;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_NAME;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_PIX;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_PROF_ID;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_QUSER_ID;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_RATING;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_REG_NO;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_STATUS;
import static com.skylightapp.MarketClasses.BizDealPartner.BDEAL_PARTNER_TABLE;
import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


import com.skylightapp.MarketClasses.BizDealPartner;

import java.util.ArrayList;

public class BizDealPartnerDAO extends DBHelperDAO{
    public static String  password;
    private static final String WHERE_ID_EQUALS = BDEAL_PARTNER_ID
            + " =?";
    public BizDealPartnerDAO(Context context) {
        super(context);
    }
    public String getPartnerBrandName(int profID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BDEAL_PARTNER_PROF_ID + "=?";
        String brandName=null;
        String[] selectionArgs = new String[]{valueOf(profID)};
        Cursor cursor = db.query(BDEAL_PARTNER_TABLE, null, selection, selectionArgs, null, null, null);

        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    brandName=cursor.getColumnName(4);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return brandName;
    }
    public long addDealPartners(ArrayList<BizDealPartner> bizDealPartners) {
        int count = 0;
        int profID = 0;
        int qUserID = 0;
        int rating = 0;
        int partnerID = 0;
        String marketName =null;
        String partnerStatus =null;
        String partnerGender =null;
        String partnerRegNo=null;
        String partnerBrandName=null;
        String partnerBrandCountry=null;
        String partnerName=null;
        Uri partnerPix=null;
        SQLiteDatabase db = this.getReadableDatabase();
        for (int i = 0; i < bizDealPartners.size(); i++) {
            BizDealPartner bizDealPartner = bizDealPartners.get(i);
            ContentValues values = new ContentValues();
            if(bizDealPartner !=null){
                profID=bizDealPartner.getPartnerProfID();
                qUserID=bizDealPartner.getPartnerQUserID();
                rating=bizDealPartner.getPartnerRating();
                partnerID=bizDealPartner.getPartnerID();
                marketName=bizDealPartner.getPartnerMarketName();
                partnerStatus=bizDealPartner.getPartnerMarketName();
                partnerGender=bizDealPartner.getPartnerMarketName();
                partnerRegNo=bizDealPartner.getPartnerMarketName();
                partnerBrandName=bizDealPartner.getPartnerMarketName();
                partnerBrandCountry=bizDealPartner.getPartnerCountry();
                partnerName=bizDealPartner.getPartnerName();
                partnerPix=bizDealPartner.getPartnerPix();

            }
            values.put(BDEAL_PARTNER_PROF_ID, profID);
            values.put(BDEAL_PARTNER_QUSER_ID, qUserID);
            values.put(BDEAL_PARTNER_NAME, partnerName);
            values.put(BDEAL_PARTNER_BRAND_NAME, partnerBrandName);
            values.put(BDEAL_PARTNER_REG_NO, partnerRegNo);
            values.put(BDEAL_PARTNER_MARKET_NAME, marketName);
            values.put(BDEAL_PARTNER_GENDER, partnerGender);
            values.put(BDEAL_PARTNER_COUNTRY, partnerBrandCountry);
            values.put(BDEAL_PARTNER_PIX, String.valueOf(partnerPix));
            values.put(BDEAL_PARTNER_STATUS, partnerStatus);
            values.put(BDEAL_PARTNER_RATING, rating);
            values.put(BDEAL_PARTNER_ID, partnerID);
            long id = db.insert(BDEAL_PARTNER_TABLE, null, values);
            if (id != -1)
                count += 1000;
        }
        return count;

    }
    public String getPartnerRating(int partnerID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BDEAL_PARTNER_ID + "=?";
        String ratings=null;
        String[] selectionArgs = new String[]{valueOf(partnerID)};
        Cursor cursor = db.query(BDEAL_PARTNER_TABLE, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() < 1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    ratings=cursor.getColumnName(12);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        db.close();
        return ratings;
    }
    public boolean checkPartnerExist(int profID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = BDEAL_PARTNER_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profID)};

        Cursor cursor = db.query(BDEAL_PARTNER_TABLE, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        //close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<BizDealPartner> getAllBizDealPartners() {
        ArrayList<BizDealPartner> dealPartners = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + BDEAL_PARTNER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                BizDealPartner profile = new BizDealPartner();
                profile.setPartnerProfID(c.getInt(1));
                profile.setPartnerQUserID(c.getInt(2));
                profile.setPartnerName(c.getString(3));
                profile.setPartnerBrandName(c.getString(4));
                profile.setPartnerCACNo(c.getString(5));
                profile.setPartnerMarketName(c.getString(6));
                profile.setPartnerGender(c.getString(7));
                profile.setPartnerCountry(c.getString(8));
                profile.setPartnerPix(Uri.parse(c.getString(9)));
                profile.setPartnerStatus(c.getString(10));
                profile.setPartnerRating(c.getInt(11));
                profile.setPartnerID(c.getInt(12));

                dealPartners.add(profile);
            } while (c.moveToNext());
        }
        return dealPartners;
    }
    public ArrayList<BizDealPartner> getAllBizDealPartnerFromGender(String gender) {
        ArrayList<BizDealPartner> profiles = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BDEAL_PARTNER_GENDER + "=?";
        String[] selectionArgs = new String[]{String.valueOf(gender)};
        Cursor cursor = db.query(BDEAL_PARTNER_TABLE, null, selection, selectionArgs, null,
                null, null);
        getProfilesFromCursor(profiles, cursor);

        cursor.close();
        //db.close();

        return profiles;
    }
    public ArrayList<BizDealPartner> getAllBizDealPartnerBlocked(String blocked) {
        ArrayList<BizDealPartner> profileArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = BDEAL_PARTNER_STATUS + "=?";
        String[] selectionArgs = new String[]{String.valueOf(blocked)};
        Cursor cursor = db.query(BDEAL_PARTNER_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSimpleProfileFromCursor(profileArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }


        return profileArrayList;

    }
    public void blockProfile(int profID, String blocked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BDEAL_PARTNER_STATUS, blocked);
        String selection = BDEAL_PARTNER_PROF_ID + "=?";
        String[] selectionArgs = new String[]{valueOf(profID)};
        db.update(BDEAL_PARTNER_TABLE, values, selection,
                selectionArgs);

    }
    public ArrayList<BizDealPartner> getAllBizDealPartnersForMarket(String marketName) {
        ArrayList<BizDealPartner> profileArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = BDEAL_PARTNER_MARKET_NAME + "=?";
        String[] selectionArgs = new String[]{marketName};
        Cursor cursor = db.query(BDEAL_PARTNER_TABLE, null,  selection, selectionArgs, null,
                null, null);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    getSimpleProfileFromCursor(profileArrayList, cursor);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return profileArrayList;




    }
    public Cursor getSimpleProfileFromCursor(ArrayList<BizDealPartner> profileArrayList, Cursor cursor) {
        while (cursor.moveToNext()) {
            String partnerName =cursor.getString(3);
            String brandName =cursor.getString(4);
            Uri picture =Uri.parse(cursor.getString(9));
            int partnerID =cursor.getInt(12);
            profileArrayList.add(new BizDealPartner(partnerID,partnerName,brandName,picture));
        }
        return cursor;
    }
    private Cursor getProfilesFromCursor(ArrayList<BizDealPartner> profiles, Cursor cursor) {
        while (cursor.moveToNext()) {
            int profID =cursor.getInt(1);
            int qUserID =cursor.getInt(2);
            String partnerName =cursor.getString(3);
            String brandName =cursor.getString(4);
            String regNo =cursor.getString(5);
            String marketName =cursor.getString(6);
            String gender =cursor.getString(7);
            String country =cursor.getString(8);
            Uri picture =Uri.parse(cursor.getString(9));
            String status =cursor.getString(10);
            int rating =cursor.getInt(11);
            int partnerID =cursor.getInt(12);
            profiles.add(new BizDealPartner(partnerID,profID, qUserID,rating,partnerName,gender, brandName,regNo,marketName,country,picture, status ));
        }return  cursor;
    }
}
