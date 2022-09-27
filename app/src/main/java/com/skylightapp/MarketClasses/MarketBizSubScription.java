package com.skylightapp.MarketClasses;

import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;

public class MarketBizSubScription {
    private int mbiz_Sub_ID;
    private int mbiz_Sub_Biz_ID;
    private int mbiz_Sub_Market_ID;
    private double mb_Sub_Amount;
    private String mb_Sub_Date;
    private  String mb_Sub_Currency;
    private  String mb_Sub_Country;
    private  String mb_Sub_Status;
    private  String mb_Sub_Type;
    public static final String MARKET_BIZ_SUB_TABLE = "mbiz_Sub_Table";
    public static final String MARKET_BIZ_SUB_ID = "mbiz_Sub_ID";
    public static final String MARKET_BIZ_SUB_BIZ_ID = "mbiz_Sub_Biz_ID";
    public static final String MARKET_BIZ_SUB_MARKET_ID22 = "mbiz_Sub_Market_ID";
    public static final String MARKET_BIZ_SUB_DATE = "mbiz_Sub_Date";
    public static final String MARKET_BIZ_SUB_AMOUNT1 = "mbiz_Sub_Amount";
    public static final String MARKET_BIZ_SUB_CURRENCY = "mbiz_Sub_Currency";
    public static final String MARKET_BIZ_SUB_STATUS = "mbiz_Sub_Status";
    public static final String MARKET_BIZ_SUB_TYPE = "mbiz_Sub_Type";

    public static final String CREATE_MARKET_BIZ_SUB_TABLE = "CREATE TABLE IF NOT EXISTS " + MARKET_BIZ_SUB_TABLE + " (" + MARKET_BIZ_SUB_ID + " INTEGER , " +
            MARKET_BIZ_SUB_BIZ_ID + " INTEGER , " + MARKET_BIZ_SUB_MARKET_ID22 + " TEXT , " + MARKET_BIZ_SUB_DATE + " TEXT , " + MARKET_BIZ_SUB_TYPE + " TEXT , " + MARKET_BIZ_SUB_AMOUNT1 + " REAL , " + MARKET_BIZ_SUB_CURRENCY + " TEXT , " + MARKET_BIZ_SUB_STATUS + " TEXT , " +  "PRIMARY KEY(" + MARKET_BIZ_SUB_ID + "), " +
            "FOREIGN KEY(" + MARKET_BIZ_SUB_BIZ_ID + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "))";




    public int getMbiz_Sub_ID() {
        return mbiz_Sub_ID;
    }

    public void setMbiz_Sub_ID(int mbiz_Sub_ID) {
        this.mbiz_Sub_ID = mbiz_Sub_ID;
    }

    public int getMbiz_Sub_Biz_ID() {
        return mbiz_Sub_Biz_ID;
    }

    public void setMbiz_Sub_Biz_ID(int mbiz_Sub_Biz_ID) {
        this.mbiz_Sub_Biz_ID = mbiz_Sub_Biz_ID;
    }

    public int getMbiz_Sub_Market_ID() {
        return mbiz_Sub_Market_ID;
    }

    public void setMbiz_Sub_Market_ID(int mbiz_Sub_Market_ID) {
        this.mbiz_Sub_Market_ID = mbiz_Sub_Market_ID;
    }

    public double getMb_Sub_Amount() {
        return mb_Sub_Amount;
    }

    public void setMb_Sub_Amount(double mb_Sub_Amount) {
        this.mb_Sub_Amount = mb_Sub_Amount;
    }

    public String getMb_Sub_Date() {
        return mb_Sub_Date;
    }

    public void setMb_Sub_Date(String mb_Sub_Date) {
        this.mb_Sub_Date = mb_Sub_Date;
    }

    public String getMb_Sub_Currency() {
        return mb_Sub_Currency;
    }

    public void setMb_Sub_Currency(String mb_Sub_Currency) {
        this.mb_Sub_Currency = mb_Sub_Currency;
    }

    public String getMb_Sub_Country() {
        return mb_Sub_Country;
    }

    public void setMb_Sub_Country(String mb_Sub_Country) {
        this.mb_Sub_Country = mb_Sub_Country;
    }

    public String getMb_Sub_Status() {
        return mb_Sub_Status;
    }

    public void setMb_Sub_Status(String mb_Sub_Status) {
        this.mb_Sub_Status = mb_Sub_Status;
    }

    public String getMb_Sub_Type() {
        return mb_Sub_Type;
    }

    public void setMb_Sub_Type(String mb_Sub_Type) {
        this.mb_Sub_Type = mb_Sub_Type;
    }
}
