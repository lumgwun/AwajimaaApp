package com.skylightapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.skylightapp.MarketClasses.BDealChatExtra;
import com.skylightapp.MarketClasses.BusinessDeal;

import java.util.ArrayList;

import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_AMOUNT;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_AMT_CU;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_CREATED_DATE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_DEAL_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_FROM_BIZID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_HOST_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_PARTNER_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_TABLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_TITTLE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_TO_BIZID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CHAT_TYPE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_CREATED_DATE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_IS_HOST;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_NO_OF_MILESTONE;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_QBCHAT_D_ID;
import static com.skylightapp.MarketClasses.BusinessDeal.BIZ_DEAL_TO_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ADDRESS;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_BRANDNAME;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_EMAIL;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_NAME;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_PHONE_NO;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_REG_NO;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_STATE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TYPE;
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
    public long saveBizDeal(int dealAcctNo, int creatorProfileID, long bizID, String tittle, String dateOfCreation, String type , String status,boolean isHost) {
        long dealID=0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues Values = new ContentValues();
            Values.put(BIZ_DEAL_FROM_PROF_ID, creatorProfileID);
            Values.put(BIZ_DEAL_TITTLE, tittle);
            Values.put(BIZ_DEAL_CREATED_DATE, dateOfCreation);
            Values.put(BIZ_DEAL_ACCT_NO, dealAcctNo);
            Values.put(BIZ_DEAL_FROM_B_ID, bizID);
            Values.put(BIZ_DEAL_TYPE, type);
            Values.put(BIZ_DEAL_IS_HOST, isHost);
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
    public long saveBizDealChat(QBChatDialog chatDialog, int hostQBID, int bizDealID, double amount,String amtCurrency,long fromBiz,long toBiz) {
        long dealID=0;
        String dialogID=null;
        String name=null;
        String createdDate=null;
        int partnerID=0;
        QBDialogType type = null;
        SQLiteDatabase db = this.getWritableDatabase();
        if(chatDialog !=null){
            dialogID= chatDialog.getDialogId();
            name = chatDialog.getName();
            createdDate=chatDialog.getFCreatedAt();
            type= chatDialog.getType();
            partnerID=chatDialog.getUserId();

        }

        try {

            ContentValues Values = new ContentValues();
            Values.put(BIZ_DEAL_QBCHAT_D_ID, dialogID);
            Values.put(BIZ_DEAL_CHAT_TITTLE, name);
            Values.put(BIZ_DEAL_CHAT_CREATED_DATE, createdDate);
            Values.put(BIZ_DEAL_CHAT_TYPE, String.valueOf(type));
            Values.put(BIZ_DEAL_CHAT_PARTNER_ID, partnerID);
            Values.put(BIZ_DEAL_CHAT_HOST_ID, hostQBID);
            Values.put(BIZ_DEAL_CHAT_DEAL_ID, bizDealID);
            Values.put(BIZ_DEAL_CHAT_AMOUNT, amount);
            Values.put(BIZ_DEAL_CHAT_AMT_CU, amtCurrency);
            Values.put(BIZ_DEAL_CHAT_FROM_BIZID, fromBiz);
            Values.put(BIZ_DEAL_CHAT_TO_BIZID, toBiz);

            db.insert(BIZ_DEAL_CHAT_TABLE,null,Values);

            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return dealID;
    }


    public ArrayList<BusinessDeal> getBizDealFromFromBizID(long bizID) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_FROM_B_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(bizID)};
            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();



            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getBizDealFromToBizID(long bizID) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_TO_BIZ_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(bizID)};
            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();



            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getBiZDealsChatFromCursor(ArrayList<BDealChatExtra> dealChatExtras, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int chatID = cursor.getInt(0);
                int dealID = cursor.getInt(2);
                String chatType = cursor.getString(3);
                String status = cursor.getString(4);
                String chatQBID = cursor.getString(5);
                int partnerChatID = cursor.getInt(6);
                int hostChatID = cursor.getInt(7);
                double chatAmt = cursor.getDouble(8);
                String currency = cursor.getString(9);
                long fromBizID = cursor.getLong(10);
                long toBizID = cursor.getLong(11);
                String title = cursor.getString(12);
                String dateCreated = cursor.getString(13);

                dealChatExtras.add(new BDealChatExtra(chatID, dealID,title,hostChatID,partnerChatID,chatQBID, chatType, toBizID, fromBizID,dateCreated,chatAmt,status,currency));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<BDealChatExtra> getBizDealChatFromToBizID(long bizID) {
        try {
            ArrayList<BDealChatExtra> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_CHAT_TO_BIZID + "=?";
            String[] selectionArgs = new String[]{valueOf(bizID)};
            Cursor cursor = db.query(BIZ_DEAL_CHAT_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsChatFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();



            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BDealChatExtra> getBizDealChatFromFROMBizID(long bizID) {
        try {
            ArrayList<BDealChatExtra> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_CHAT_FROM_BIZID + "=?";
            String[] selectionArgs = new String[]{valueOf(bizID)};
            Cursor cursor = db.query(BIZ_DEAL_CHAT_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsChatFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();



            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BDealChatExtra> getBizDealChatFromBizDealID(int bizDealID) {
        try {
            ArrayList<BDealChatExtra> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_CHAT_DEAL_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(bizDealID)};
            Cursor cursor = db.query(BIZ_DEAL_CHAT_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsChatFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();

            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getAllBizDealChats() {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(BIZ_DEAL_CHAT_TABLE, null, null, null, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();


            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }



    public ArrayList<BusinessDeal> getBizDealFromType(String type) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_TYPE + "=?";
            String[] selectionArgs = new String[]{valueOf(type)};
            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();



            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }



    public ArrayList<BusinessDeal> getBizDealFromTittle(String tittle) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_TITTLE + "=?";
            String[] selectionArgs = new String[]{valueOf(tittle)};
            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();

            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<BusinessDeal> getBizDealByNoOfMilestone(int noOfMileStones) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_NO_OF_MILESTONE + "=?";
            String[] selectionArgs = new String[]{valueOf(noOfMileStones)};
            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();

            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getBizDealToBizID(long toBizID) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_TO_BIZ_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(toBizID)};
            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();

            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getBizDealToBizAtCreatedToday(long toBizID, String date) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = BIZ_DEAL_TO_BIZ_ID + "=? AND " + BIZ_DEAL_CHAT_CREATED_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(toBizID), valueOf(date)};

            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }else {
                if (cursor != null) {
                    cursor.close();
                }

            }
            db.close();
            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getBizDealFromUsToday(long toBizID, String today) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = BIZ_DEAL_FROM_B_ID + "=? AND " + BIZ_DEAL_CHAT_CREATED_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(toBizID), valueOf(today)};

            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }else {
                if (cursor != null) {
                    cursor.close();
                }

            }
            db.close();
            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getBizDealFromBizTypeAndDate(long toBizID, String today) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = BIZ_DEAL_TYPE + "=? AND " + BIZ_DEAL_CHAT_CREATED_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(toBizID), valueOf(today)};

            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }else {
                if (cursor != null) {
                    cursor.close();
                }

            }
            db.close();
            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getBizDealFromBizID(long toBizID) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();

            String selection = BIZ_DEAL_FROM_B_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(toBizID)};
            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();

            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getBizDealFromBizAtCreatedDate(long toBizID,String date) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = BIZ_DEAL_FROM_B_ID + "=? AND " + BIZ_DEAL_CHAT_CREATED_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(toBizID), valueOf(date)};

            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }else {
                if (cursor != null) {
                    cursor.close();
                }

            }
            db.close();
            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getBizDealToBizAndStartDate(long toBizID,String date) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = BIZ_DEAL_TO_BIZ_ID + "=? AND " + BIZ_DEAL_START_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(toBizID), valueOf(date)};

            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null,
                    null, null);

            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);
                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }else {
                if (cursor != null) {
                    cursor.close();
                }

            }
            db.close();
            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public ArrayList<BusinessDeal> getAllBizDeals() {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, null, null, null,
                    null, null);
            if(cursor!=null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        getBiZDealsFromCursor(dealArrayList, cursor);

                    } while (cursor.moveToNext());
                    cursor.close();
                }

            }
            if (cursor != null) {
                cursor.close();
            }

            db.close();


            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public ArrayList<BusinessDeal> getAllBizDealsByCreatedDate(String date) {
        try {
            ArrayList<BusinessDeal> dealArrayList = new ArrayList<>();
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = BIZ_DEAL_CREATED_DATE + "=?";
            String[] selectionArgs = new String[]{valueOf(date)};
            Cursor cursor = db.query(BIZ_DEAL_TABLE, null, selection, selectionArgs, null, null,
                    null, null);
            getBiZDealsFromCursor(dealArrayList, cursor);
            cursor.close();
            //db.close();
            return dealArrayList;

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private void getBiZDealsFromCursor(ArrayList<BusinessDeal> businessDeals, Cursor cursor) {
        try {
            while (cursor.moveToNext()) {
                int bizDealID = cursor.getInt(0);
                long bizDealAcctNo = cursor.getLong(1);
                int bizDealToProfID = cursor.getInt(2);
                int bizDealFromProfID = cursor.getInt(3);
                int bizDealFromAcctID = cursor.getInt(4);
                int bizDealToAcctID = cursor.getInt(5);
                String tittle = cursor.getString(6);
                double productAmt = cursor.getDouble(7);
                double shippingAmt = cursor.getDouble(8);
                int qty = cursor.getInt(9);
                double insuranceAmt = cursor.getDouble(10);
                String dealType = cursor.getString(11);
                String startDate = cursor.getString(12);
                int milestones = cursor.getInt(13);
                int code = cursor.getInt(14);
                String status = cursor.getString(15);
                long fromBizID = cursor.getLong(16);
                long toBizID = cursor.getLong(17);
                String isHost = cursor.getString(18);
                String isPartner = cursor.getString(19);
                String isLogistics = cursor.getString(20);
                String dispute = cursor.getString(21);
                String dateCreated = cursor.getString(23);
                String dateEnded = cursor.getString(24);
                double total = cursor.getDouble(25);
                String currency = cursor.getString(26);
                businessDeals.add(new BusinessDeal(bizDealID,bizDealAcctNo,bizDealToProfID,bizDealFromProfID,bizDealFromAcctID, bizDealToAcctID,tittle,productAmt, shippingAmt, qty, insuranceAmt,dealType,startDate,status,milestones,code,fromBizID,toBizID,isHost,isPartner,isLogistics,dispute,dateCreated,dateEnded,total,currency));
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void updateBusiness(long biZID,int profileID, String name,String brandName,String typeBiz,String bizEmail,String bizAddress, String ph_no,String state,String regNo) {

        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(MARKET_BIZ_NAME, name);
            cv.put(MARKET_BIZ_BRANDNAME, brandName);
            cv.put(MARKET_BIZ_TYPE, typeBiz);
            cv.put(MARKET_BIZ_EMAIL, bizEmail);
            cv.put(MARKET_BIZ_ADDRESS, bizAddress);
            cv.put(MARKET_BIZ_PHONE_NO, ph_no);
            cv.put(MARKET_BIZ_STATE, state);
            cv.put(MARKET_BIZ_REG_NO, regNo);
            String selection = MARKET_BIZ_ID + "=?";
            String[] selectionArgs = new String[]{valueOf(biZID)};
            db.update(BIZ_DEAL_TABLE, cv, selection, selectionArgs);
            db.close();

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

}
