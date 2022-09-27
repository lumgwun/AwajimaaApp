package com.skylightapp.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_COUNTRY;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_ID33;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_LOCATION;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_LOGO;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_MARKET_ID;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_NAME;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_PROF_ID;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_REG_NO;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_STATUS;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_TABLE22;
import static com.skylightapp.MarketClasses.BusinessOthers.BUSINESS_TYPE;
import static java.lang.String.valueOf;

public class MarketTranXDAO extends DBHelperDAO{
    private static final String WHERE_ID_EQUALS = CUSTOMER_ID
            + " =?";
    private SQLiteDatabase readableDatabase;
    private ContentResolver myCR;
    public static final String MARKET_TX_TABLE = "m_trans_Table";
    public static final String MARKET_TXM_ID = "m_trans_id";
    public static final String MARKT_TX_SENDN_ACCT = "m_trans_sending_acct";
    public static final String MARKET_TX_DEST_ACCT = "m_trans_dest_acct";
    public static final String MARKET_TX_PAYEE = "m_trans_payee";
    public static final String MARKET_TRANX_PAYER = "m_trans_payer";
    public static final String MARKET_TRANX_STATUS = "m_trans_status";
    public static final String MARKET_TRANX_TYPE = "m_transType";
    public static final String MARKET_TRANX_AMOUNT = "m_trans_amount";
    public static final String MARKET_TRANX_DATE = "m_trans_date";

    public static final String MARKET_TX_SENDER_ID = "m_trans_sender_id";
    public static final String MARKET_TRANX_PAYMENT_METHOD = "m_trans_payment_id";
    public static final String MARKET_TX_MARKET_ID = "m_trans_market_id";
    public static final String MARKET_TRANX_APPROVER = "m_trans_approver";
    public static final String MARKET_TRANX_APPROVAL_DATE = "m_trans_approval_Date";
    public static final String MARKET_TX_PROF_ID = "m_trans_Prof_ID";
    public static final String MARKET_TX_BUS_ID = "m_trans_Biz_ID";
    public static final String MARKET_TX_CODE = "m_trans_Code";
    public static final String MARKET_TRANX_CURRENCY = "m_trans_Currency";
    public static final String MARKET_TX_RECEIVER_ID = "m_trans_receiver_ID";
    public static final String MARKET_TX_CUS_ID = "m_trans_Cus_ID";


    public static final String CREATE_MARKET_TX_TABLE_TABLE = "CREATE TABLE IF NOT EXISTS " + MARKET_TX_TABLE + " (" + MARKET_TXM_ID + " INTEGER, " + MARKET_TX_PROF_ID + " INTEGER , " +
            MARKET_TX_BUS_ID + " INTEGER , " + MARKET_TX_MARKET_ID + " INTEGER , " + MARKET_TX_SENDER_ID + " INTEGER, " + MARKET_TX_RECEIVER_ID + " INTEGER, " +
            MARKET_TRANX_PAYER + " TEXT, " + MARKET_TX_PAYEE + " TEXT, " + MARKET_TRANX_AMOUNT + " REAL, " + MARKET_TRANX_TYPE + " TEXT, " +
            MARKET_TRANX_DATE + " TEXT, " + MARKET_TRANX_PAYMENT_METHOD + " TEXT, " + MARKT_TX_SENDN_ACCT + " INTEGER, "+ MARKET_TX_DEST_ACCT +" INTEGER, " + MARKET_TRANX_CURRENCY + " TEXT, "+  MARKET_TX_CODE + " TEXT, "+ MARKET_TRANX_APPROVER + " TEXT, "+ MARKET_TRANX_APPROVAL_DATE + " TEXT, "+ MARKET_TRANX_STATUS + " TEXT, "+ MARKET_TX_CUS_ID + " INTEGER, " + "PRIMARY KEY(" +MARKET_TXM_ID + "), "+"FOREIGN KEY(" + MARKET_TX_CUS_ID  + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+"FOREIGN KEY(" + MARKET_TX_RECEIVER_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + MARKT_TX_SENDN_ACCT  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + MARKET_TX_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + MARKET_TX_DEST_ACCT  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + MARKET_TX_BUS_ID + ") REFERENCES " + BUSINESS_TABLE22 + "(" + BUSINESS_ID33 + "))";


    public MarketTranXDAO(Context context) {
        super(context);
    }
    public Cursor get_user_details(int user_id){
        String Query = "SELECT "+MARKET_TRANX_PAYER+","+MARKET_TX_SENDER_ID+","+MARKET_TRANX_AMOUNT+","+MARKET_TRANX_CURRENCY+","+MARKET_TRANX_DATE+" FROM "+MARKET_TX_TABLE+" WHERE "+MARKET_TX_PROF_ID+"="+user_id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(Query,null);
        return  cursor;
    }
    public boolean storeNewDebitMarketTranx(int businessID, int cus_id, String transaction_balance, String transaction_reason, String transaction_date){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(MARKET_TX_BUS_ID,businessID);
        cv.put(MARKET_TX_RECEIVER_ID,cus_id);
        cv.put(MARKET_TX_CUS_ID,cus_id);
        cv.put(MARKET_TRANX_AMOUNT,transaction_balance);
        cv.put(MARKET_TRANX_STATUS,1);
        cv.put(MARKET_TRANX_PAYMENT_METHOD,transaction_reason);
        cv.put(MARKET_TRANX_DATE,transaction_date);

        long result = db.insert(MARKET_TX_TABLE,null,cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean storeNewCreditTransaction(int businessID,int cus_id,String amount,String transaction_reason,String transaction_date){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(MARKET_TX_BUS_ID,businessID);
        cv.put(MARKET_TX_SENDER_ID,cus_id);
        cv.put(MARKET_TX_CUS_ID,cus_id);
        cv.put(MARKET_TRANX_AMOUNT,amount);
        cv.put(MARKET_TRANX_STATUS,1);
        cv.put(MARKET_TRANX_PAYMENT_METHOD,transaction_reason);
        cv.put(MARKET_TRANX_DATE,transaction_date);

        long result = db.insert(MARKET_TX_TABLE,null,cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public Cursor user_friend_transaction(int businessID, int cus_id){
        String Query = "SELECT "+MARKET_TRANX_PAYER+","+MARKET_TX_SENDER_ID+","+MARKET_TRANX_AMOUNT+","+MARKET_TRANX_DATE+","+MARKET_TX_CUS_ID+" FROM "+MARKET_TX_TABLE+" WHERE "+MARKET_TX_CUS_ID+"!= 0 AND ( ("+ MARKET_TX_BUS_ID+ "=" + businessID+ " AND " +MARKET_TX_SENDER_ID+"=" + cus_id+ ")OR("+ MARKET_TX_CUS_ID+ "=" + cus_id+ " AND " +MARKET_TX_SENDER_ID+"=" + cus_id+ "))";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(Query,null);
        }
        return  cursor;
    }

    public double debit_transaction_amount(int businessID,String debit){
        double amount=0.00;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = MARKET_TX_BUS_ID + "=? AND " + MARKET_TRANX_TYPE + "=?";
        String[] selectionArgs = new String[]{valueOf(businessID), valueOf(debit)};

        Cursor cursor = db.rawQuery(
                "select sum ("+ MARKET_TRANX_AMOUNT +") from " + MARKET_TX_TABLE + " WHERE " + selection,
                selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    amount=cursor.getColumnIndex(MARKET_TXM_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return amount;
    }
    public double credit_transaction_amount(int businessID,String credit){
        double amount=0.00;
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = MARKET_TX_BUS_ID + "=? AND " + MARKET_TRANX_TYPE + "=?";
        String[] selectionArgs = new String[]{valueOf(businessID), valueOf(credit)};

        Cursor cursor = db.rawQuery(
                "select sum ("+ MARKET_TRANX_AMOUNT +") from " + MARKET_TX_TABLE + " WHERE " + selection,
                selectionArgs);

        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    amount=cursor.getColumnIndex(MARKET_TXM_ID);
                } while (cursor.moveToNext());
                cursor.close();
            }

        }

        return amount;
    }


    Cursor all_transaction(int businessID,int status){
        //int status = 2;
        String Query = "SELECT "+MARKET_TX_TABLE+"."+MARKET_TX_PAYEE+","+MARKET_TX_TABLE+"."+MARKET_TRANX_PAYER+","+MARKET_TX_TABLE+"."+MARKET_TRANX_AMOUNT+","+MARKET_TX_TABLE+"."+MARKET_TRANX_CURRENCY+","+BUSINESS_TABLE22+"."+BUSINESS_NAME+","+BUSINESS_TABLE22+"."+BUSINESS_REG_NO+","+BUSINESS_TABLE22+"."+BUSINESS_LOCATION+","+BUSINESS_TABLE22+"."+BUSINESS_MARKET_ID+ ","+BUSINESS_TABLE22+"."+BUSINESS_LOGO+","+BUSINESS_TABLE22+"."+BUSINESS_COUNTRY+" FROM "+MARKET_TX_TABLE+","+BUSINESS_TABLE22+" WHERE "+BUSINESS_TABLE22+"."+BUSINESS_STATUS+"!="+status+" AND ("+MARKET_TX_TABLE+"."+MARKET_TX_SENDER_ID+"="+businessID+" OR "+MARKET_TX_TABLE+"."+MARKET_TX_RECEIVER_ID+"="+businessID+") AND ("+MARKET_TX_TABLE+"."+MARKET_TX_MARKET_ID+"="+BUSINESS_TABLE22+"."+BUSINESS_MARKET_ID+" OR "+MARKET_TX_TABLE+"."+MARKET_TX_PROF_ID+" = "+BUSINESS_TABLE22+"."+BUSINESS_PROF_ID+") AND "+MARKET_TX_TABLE+"."+MARKET_TX_BUS_ID+" != "+businessID+" ORDER BY "+BUSINESS_TABLE22+"."+BUSINESS_STATUS+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(Query,null);
        }
        return  cursor;
    }

    Cursor credit_transaction(int businessID){
        int status = 2;
        String Query = "SELECT "+MARKET_TX_TABLE+"."+MARKET_TRANX_PAYER+","+MARKET_TX_TABLE+"."+MARKET_TRANX_AMOUNT+","+MARKET_TX_TABLE+"."+MARKET_TRANX_CURRENCY+","+MARKET_TX_TABLE+"."+MARKET_TRANX_PAYMENT_METHOD+","+MARKET_TX_TABLE+"."+MARKET_TRANX_DATE+","+BUSINESS_TABLE22+"."+BUSINESS_NAME+","+BUSINESS_TABLE22+"."+BUSINESS_LOCATION+","+BUSINESS_TABLE22+"."+BUSINESS_MARKET_ID+","+BUSINESS_TABLE22+"."+BUSINESS_STATUS+ ","+BUSINESS_TABLE22+"."+BUSINESS_LOGO+","+BUSINESS_TABLE22+"."+BUSINESS_COUNTRY+" FROM "+MARKET_TX_TABLE+","+BUSINESS_TABLE22+" WHERE "+BUSINESS_TABLE22+"."+BUSINESS_STATUS+"!="+status+" AND  ("+BUSINESS_TABLE22+"."+BUSINESS_ID33+"="+businessID+") AND ("+MARKET_TX_TABLE+"."+MARKET_TX_PROF_ID+"="+BUSINESS_TABLE22 +"."+BUSINESS_PROF_ID+")"+" ORDER BY "+BUSINESS_TABLE22+"."+BUSINESS_LOCATION+" DESC" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(Query,null);
        }
        return  cursor;
    }

    Cursor debit_transaction(int businessID){
        int status = 2;
        String Query = "SELECT "+MARKET_TX_TABLE+"."+MARKET_TRANX_PAYER+","+MARKET_TX_TABLE+"."+MARKET_TRANX_AMOUNT+","+MARKET_TX_TABLE+"."+MARKET_TRANX_CURRENCY+","+MARKET_TX_TABLE+"."+MARKET_TRANX_PAYMENT_METHOD+","+BUSINESS_TABLE22+"."+BUSINESS_NAME+","+BUSINESS_TABLE22+"."+BUSINESS_LOCATION+","+BUSINESS_TABLE22+"."+BUSINESS_COUNTRY+","+BUSINESS_TABLE22+"."+BUSINESS_LOGO+ ","+BUSINESS_TABLE22+"."+BUSINESS_MARKET_ID+","+BUSINESS_TABLE22+"."+BUSINESS_TYPE+" FROM "+MARKET_TX_TABLE+","+BUSINESS_TABLE22+" WHERE "+BUSINESS_TABLE22+"."+BUSINESS_STATUS+"!="+status+" AND ("+BUSINESS_TABLE22+"."+BUSINESS_ID33+"="+businessID+") AND ("+MARKET_TX_TABLE+"."+MARKET_TX_PROF_ID+" = "+BUSINESS_TABLE22+"."+BUSINESS_PROF_ID+")"+" ORDER BY "+BUSINESS_TABLE22+"."+BUSINESS_LOCATION+" DESC" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(Query,null);
        }
        return  cursor;
    }

    /*Cursor customer_list_credit_transaction(String Sender_id){
        String Query = "SELECT "+TABLE_NAME_1+"."+COL_1_3+" , "+TABLE_NAME_1+"."+COL_3_1+" , "+TABLE_NAME_1+"."+COL_2_1+", "+TABLE_NAME_1+"."+COL_9_1+" , SUM("+TABLE_NAME_2+"."+COL_4_2+") FROM "+TABLE_NAME_1+" , "+TABLE_NAME_2+ " , " +TABLE_NAME_3+ " WHERE "+TABLE_NAME_3+"."+COL_2_3+" = "+Sender_id +" AND "+TABLE_NAME_1+"."+COL_1_3+" = "+TABLE_NAME_3+"."+COL_3_3+" AND ("+TABLE_NAME_2+"."+COL_2_2+" = "+TABLE_NAME_3+"."+COL_3_3+" AND "+TABLE_NAME_2+"."+COL_3_2+" = "+Sender_id+") GROUP BY "+TABLE_NAME_1+"."+COL_1_3+" ORDER BY "+TABLE_NAME_1+"."+COL_1_1+" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(Query,null);
        }
        return  cursor;
    }

    Cursor customer_list_debit_transaction(String Sender_id){
        String Query = "SELECT "+TABLE_NAME_1+"."+COL_1_3+" , "+TABLE_NAME_1+"."+COL_3_1+" , "+TABLE_NAME_1+"."+COL_2_1+", "+TABLE_NAME_1+"."+COL_9_1+" , SUM("+TABLE_NAME_2+"."+COL_4_2+") FROM "+TABLE_NAME_1+" , "+TABLE_NAME_2+ " , " +TABLE_NAME_3+ " WHERE "+TABLE_NAME_3+"."+COL_2_3+" = "+Sender_id +" AND "+TABLE_NAME_1+"."+COL_1_3+" = "+TABLE_NAME_3+"."+COL_3_3+" AND ("+TABLE_NAME_2+"."+COL_2_2+" = "+Sender_id+" AND "+TABLE_NAME_2+"."+COL_3_2+" = "+TABLE_NAME_3+"."+COL_3_3+") GROUP BY "+TABLE_NAME_1+"."+COL_1_3+" ORDER BY "+TABLE_NAME_1+"."+COL_1_1+" DESC" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(Query,null);
        }
        return  cursor;
    }

    Cursor user_friend_transaction(String user_id,String friend_id){
        String Query = "SELECT "+COL_2_2+","+COL_4_2+","+COL_6_2+","+COL_7_2+","+COL_1_2+" FROM "+TABLE_NAME_2+" WHERE "+COL_4_2+"!= 0 AND ( ("+ COL_2_2+ "=" + user_id+ " AND " +COL_3_2+"=" + friend_id+ ")OR("+ COL_2_2+ "=" + friend_id+ " AND " +COL_3_2+"=" + user_id+ "))";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db!= null){
            cursor = db.rawQuery(Query,null);
        }
        return  cursor;
    }
    public boolean storeNewDebitTransaction(String user_id,String friend_id,String transaction_balance,String transaction_reason,String transaction_date){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_2_2,user_id);
        cv.put(COL_3_2,friend_id);
        cv.put(COL_4_2,transaction_balance);
        cv.put(COL_5_2,1);
        cv.put(COL_6_2,transaction_reason);
        cv.put(COL_8_2,transaction_date);

        long result = db.insert(TABLE_NAME_2,null,cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean storeNewCreditTransaction(String user_id,String friend_id,String transaction_balance,String transaction_reason,String transaction_date){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_2_2,friend_id);
        cv.put(COL_3_2,user_id);
        cv.put(COL_4_2,transaction_balance);
        cv.put(COL_5_2,1);
        cv.put(COL_6_2,transaction_reason);
        cv.put(COL_8_2,transaction_date);

        long result = db.insert(TABLE_NAME_2,null,cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }*/
    public Cursor get_transaction_details(int transaction_id){
        String Query = "SELECT * FROM "+MARKET_TX_TABLE+" WHERE "+MARKET_TX_SENDER_ID+"="+transaction_id ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(Query,null);
        return  cursor;
    }
}
