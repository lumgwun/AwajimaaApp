package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.skylightapp.Classes.Account;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Classes.Profile;

import java.util.ArrayList;

import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ADDRESS;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_BRANDNAME;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_EMAIL;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_NAME;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_PHONE_NO;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_REG_NO;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_STATE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TYPE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_ACCT_NO;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_FROM_B_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_FROM_PROF_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_START_DATE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_STATUS;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_TITTLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_TYPE;
import static java.lang.String.valueOf;

public class BizDealDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = BIZ_DEAL_ID
            + " =?";
    public BizDealDAO(Context context) {
        super(context);
    }
    public void deleteBizDeal(long bizDealID) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(BIZ_DEAL_TABLE,
                    "BIZ_DEAL_ID = ? ",
                    new String[]{String.valueOf((bizDealID))});

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
    public long saveBizDeal(int dealAcctNo, int creatorProfileID,int bizID,String tittle,String dateOfCreation,String type ,String status) {
        int dealID=0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Values.put(BIZ_DEAL_FROM_PROF_ID, creatorProfileID);
            Values.put(BIZ_DEAL_TITTLE, tittle);
            Values.put(BIZ_DEAL_START_DATE, dateOfCreation);
            Values.put(BIZ_DEAL_ACCT_NO, dealAcctNo);
            Values.put(BIZ_DEAL_FROM_B_ID, bizID);
            Values.put(BIZ_DEAL_TYPE, type);
            Values.put(BIZ_DEAL_STATUS, status);
            db.insert(BIZ_DEAL_TABLE,null,Values);
            //long id = db.insert(BIZ_TABLE, null, codeValues);
            //paymentCode.setUID(String.valueOf(id));
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return dealID;
    }



















    public ArrayList<MarketBusiness> getBusinessesFromProfile(long profileID) {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Profile profile = new Profile();
            if(profile !=null) {
                profileID = profile.getPID();


                Cursor cursor = db.query(MARKET_BIZ_TABLE, null, PROFILE_ID + "=?",
                        new String[]{String.valueOf(profileID)}, null, null,
                        null, null);
                getBiZsFromCursor(marketBusinesses, cursor);
                cursor.close();
                //db.close();

                return marketBusinesses;
            }
            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<String> getAllBizNames() {

        try {
            ArrayList<String> array_list = new ArrayList<String>();

            SQLiteDatabase db = this.getReadableDatabase();
            String selection = MARKET_BIZ_BRANDNAME + "=?";
            //String[] selectionArgs = new String[]{valueOf(town)};
            Cursor res = db.query(MARKET_BIZ_TABLE,null,selection,null,null,null,null);
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





    public ArrayList<MarketBusiness> getAllBusinesses() {
        try {
            ArrayList<MarketBusiness> marketBusinesses = new ArrayList<>();
            SQLiteDatabase db = this.getWritableDatabase();
            Profile profile = new Profile();
            long profileID = profile.getPID();
            Account account = new Account();
            //String accountNo = String.valueOf(account.getAcctID());
            Cursor cursor = db.query(MARKET_BIZ_TABLE, null, null, null, MARKET_BIZ_TYPE,
                    null, null);

            getBiZsFromCursor(marketBusinesses, cursor);

            cursor.close();
            //db.close();

            return marketBusinesses;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getBiZsFromCursor(ArrayList<MarketBusiness> marketBusinesses, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                long bizID = (cursor.getLong(0));
                String name = cursor.getString(1);
                String brandName = cursor.getString(2);
                String type = cursor.getString(3);
                String regNo = cursor.getString(4);
                String email = cursor.getString(5);
                String phoneNo = cursor.getString(6);
                String address = cursor.getString(7);
                String state = cursor.getString(8);
                String status = cursor.getString(9);
                Uri logo = Uri.parse(cursor.getString(10));
                marketBusinesses.add(new MarketBusiness(bizID, name,brandName,type, regNo, email, phoneNo,address,state,status,logo));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void updateBusiness(long biZID,long profileID, String name,String brandName,String typeBiz,String bizEmail,String bizAddress, String ph_no,String state,String regNo) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(PROFILE_ID, profileID);
            cv.put(MARKET_BIZ_ID, biZID);
            cv.put(MARKET_BIZ_NAME, name);
            cv.put(MARKET_BIZ_BRANDNAME, brandName);
            cv.put(MARKET_BIZ_TYPE, typeBiz);
            cv.put(MARKET_BIZ_EMAIL, bizEmail);
            cv.put(MARKET_BIZ_ADDRESS, bizAddress);
            cv.put(MARKET_BIZ_PHONE_NO, ph_no);
            cv.put(MARKET_BIZ_STATE, state);
            cv.put(MARKET_BIZ_REG_NO, regNo);
            db.update(MARKET_BIZ_TABLE, cv, PROFILE_ID + "=?", new String[]{valueOf(profileID)});
            //db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
