package com.skylightapp.MarketClasses;

import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;

public class MarketBizSub {
    private int mbiz_Sub_ID;
    private long mbiz_Sub_Biz_ID;
    private int mbiz_Sub_Market_ID;
    private int mbiz_Sub_Prof_ID;
    private double mb_Sub_Amount;
    private String mb_Sub_Date;
    private  String mb_Sub_Currency;
    private  String mb_Sub_Country;
    private  String mb_Sub_Status;
    private  String mb_Sub_Type;
    private  String mb_Sub_StartTime;
    private  String mb_Sub_EndTime;
    private int mbiz_Sub_NoOfMonths;
    private int mbiz_Sub_Cus_ID;
    private  String mb_Sub_Mode_Of_Payment;
    private  String mb_Sub_State;
    private  String mb_Sub_Name;
    private  String mb_Sub_Office;
    public static final String MARKET_BIZ_SUB_TABLE = "mbiz_Sub_Table";
    public static final String MARKET_BIZ_SUB_ID = "mbiz_Sub_ID";
    public static final String MARKET_BIZ_SUB_BIZ_ID = "mbiz_Sub_Biz_ID";
    public static final String MARKET_BIZ_SUB_MARKET_ID22 = "mbiz_Sub_Market_ID";
    public static final String MARKET_BIZ_SUB_DATE = "mbiz_Sub_Date";
    public static final String MARKET_BIZ_SUB_AMOUNT1 = "mbiz_Sub_Amount";
    public static final String MARKET_BIZ_SUB_CURRENCY = "mbiz_Sub_Currency";
    public static final String MARKET_BIZ_SUB_STATUS = "mbiz_Sub_Status";
    public static final String MARKET_BIZ_SUB_TYPE = "mbiz_Sub_Type";
    public static final String MARKET_BIZ_SUB_START_T = "mbiz_Sub_StartTime";
    public static final String MARKET_BIZ_SUB_END_T = "mbiz_Sub_EndTime";
    public static final String MARKET_BIZ_SUB_NO_OF_MONTHS = "mbiz_Sub_NO_Of_Months";
    public static final String MARKET_BIZ_SUB_MODE_OF_P = "mbiz_Sub_Mode_Of_Payment";
    public static final String MARKET_BIZ_SUB_PROF_ID = "mbiz_Sub_Prof_ID";

    public static final String MARKET_BIZ_SUB_CUS_ID = "mbiz_Sub_Cus_ID";
    public static final String MARKET_BIZ_SUB_OFFICE = "mbiz_Sub_Office";
    public static final String MARKET_BIZ_SUB_STATE = "mbiz_Sub_State";
    public static final String MARKET_BIZ_SUB_COUNTRY = "mbiz_Sub_Country";
    public static final String MARKET_BIZ_SUB_NAME = "mbiz_Sub_Name";

    public MarketBizSub() {
        super();

    }

    public static final String CREATE_MARKET_BIZ_SUB_TABLE = "CREATE TABLE IF NOT EXISTS " + MARKET_BIZ_SUB_TABLE + " (" + MARKET_BIZ_SUB_ID + " INTEGER , " +
            MARKET_BIZ_SUB_BIZ_ID + " INTEGER , " + MARKET_BIZ_SUB_MARKET_ID22 + " TEXT , " + MARKET_BIZ_SUB_DATE + " TEXT , " + MARKET_BIZ_SUB_TYPE + " TEXT , " + MARKET_BIZ_SUB_AMOUNT1 + " REAL , " + MARKET_BIZ_SUB_CURRENCY + " TEXT , " + MARKET_BIZ_SUB_STATUS + " TEXT , "+ MARKET_BIZ_SUB_START_T + " TEXT , "+ MARKET_BIZ_SUB_END_T + " TEXT , "+ MARKET_BIZ_SUB_MODE_OF_P + " TEXT , "+ MARKET_BIZ_SUB_NO_OF_MONTHS + " INTEGER , "+ MARKET_BIZ_SUB_PROF_ID + " INTEGER , " + MARKET_BIZ_SUB_CUS_ID + " TEXT , "+ MARKET_BIZ_SUB_NAME + " TEXT , "+ MARKET_BIZ_SUB_OFFICE + " TEXT , "+ MARKET_BIZ_SUB_STATE + " TEXT , "+ MARKET_BIZ_SUB_COUNTRY + " TEXT , "+  "PRIMARY KEY(" + MARKET_BIZ_SUB_ID + "), " +
            "FOREIGN KEY(" + MARKET_BIZ_SUB_BIZ_ID + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "))";

    public MarketBizSub(long bizID, long amount, int profileID, int noOfMonths, String subDate, String subEndDate, String modeOfPayment, String status) {
        this.mbiz_Sub_Biz_ID =bizID;
        this.mb_Sub_Amount =amount;
        this.mbiz_Sub_Prof_ID =profileID;
        this.mbiz_Sub_NoOfMonths=noOfMonths;
        this.mb_Sub_StartTime=subDate;
        this.mb_Sub_EndTime=subEndDate;
        this.mb_Sub_Mode_Of_Payment=modeOfPayment;
        this.mb_Sub_Status=status;
    }

    public MarketBizSub(int subID, long bizID, int marketID, int profID, String type, double amount, String currency, int noOfMonths, String date, String startTime, String endTime, String modeOfPayment, String status) {
        this.mbiz_Sub_Biz_ID =bizID;
        this.mbiz_Sub_ID =subID;
        this.mbiz_Sub_Market_ID =marketID;
        this.mbiz_Sub_Prof_ID =profID;
        this.mb_Sub_Amount =amount;
        this.mb_Sub_Type =type;
        this.mbiz_Sub_NoOfMonths=noOfMonths;
        this.mb_Sub_Currency=currency;
        this.mb_Sub_Date=date;
        this.mb_Sub_StartTime=startTime;
        this.mb_Sub_EndTime=endTime;
        this.mb_Sub_Mode_Of_Payment=modeOfPayment;
        this.mb_Sub_Status=status;

    }


    public MarketBizSub(int i, long amount, int bundleProfID, int bundleCusID, int noOfMonths, String subDate, String subEndDate, String modeOfPayment, String paymentFor, String status, String currency, String state, String country, String bookingName, String office) {
        this.mbiz_Sub_ID =i;
        this.mbiz_Sub_Cus_ID =bundleCusID;
        this.mbiz_Sub_Prof_ID =bundleProfID;
        this.mb_Sub_Amount =amount;
        this.mb_Sub_Type =paymentFor;
        this.mbiz_Sub_NoOfMonths=noOfMonths;
        this.mb_Sub_Currency=currency;
        this.mb_Sub_Date=subDate;
        this.mb_Sub_StartTime=subDate;
        this.mb_Sub_EndTime=subEndDate;
        this.mb_Sub_Mode_Of_Payment=modeOfPayment;
        this.mb_Sub_Status=status;
        this.mb_Sub_Country=country;
        this.mb_Sub_State=state;
        this.mb_Sub_Name=bookingName;
        this.mb_Sub_Office=office;
    }

    public MarketBizSub(int bookingID, long bizID, int marketID, long amount, int bundleProfID, int bundleCusID, int noOfMonths, String subDate, String subEndDate, String modeOfPayment, String paymentFor, String status, String state, String office, String country, String currency,String bookingName) {
        this.mbiz_Sub_ID =bookingID;
        this.mbiz_Sub_Cus_ID =bundleCusID;
        this.mbiz_Sub_Prof_ID =bundleProfID;
        this.mb_Sub_Amount =amount;
        this.mb_Sub_Type =paymentFor;
        this.mbiz_Sub_NoOfMonths=noOfMonths;
        this.mb_Sub_Currency=currency;
        this.mb_Sub_Date=subDate;
        this.mb_Sub_StartTime=subDate;
        this.mb_Sub_EndTime=subEndDate;
        this.mb_Sub_Mode_Of_Payment=modeOfPayment;
        this.mb_Sub_Status=status;
        this.mb_Sub_Country=country;
        this.mb_Sub_State=state;
        this.mb_Sub_Name=bookingName;
        this.mb_Sub_Office=office;
    }


    public int getMbiz_Sub_ID() {
        return mbiz_Sub_ID;
    }

    public void setMbiz_Sub_ID(int mbiz_Sub_ID) {
        this.mbiz_Sub_ID = mbiz_Sub_ID;
    }

    public long getMbiz_Sub_Biz_ID() {
        return mbiz_Sub_Biz_ID;
    }

    public void setMbiz_Sub_Biz_ID(long mbiz_Sub_Biz_ID) {
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

    public String getMb_Sub_StartTime() {
        return mb_Sub_StartTime;
    }

    public void setMb_Sub_StartTime(String mb_Sub_StartTime) {
        this.mb_Sub_StartTime = mb_Sub_StartTime;
    }

    public String getMb_Sub_EndTime() {
        return mb_Sub_EndTime;
    }

    public void setMb_Sub_EndTime(String mb_Sub_EndTime) {
        this.mb_Sub_EndTime = mb_Sub_EndTime;
    }

    public int getMbiz_Sub_NoOfMonths() {
        return mbiz_Sub_NoOfMonths;
    }

    public void setMbiz_Sub_NoOfMonths(int mbiz_Sub_NoOfMonths) {
        this.mbiz_Sub_NoOfMonths = mbiz_Sub_NoOfMonths;
    }

    public String getMb_Sub_Mode_Of_Payment() {
        return mb_Sub_Mode_Of_Payment;
    }

    public void setMb_Sub_Mode_Of_Payment(String mb_Sub_Mode_Of_Payment) {
        this.mb_Sub_Mode_Of_Payment = mb_Sub_Mode_Of_Payment;
    }

    public int getMbiz_Sub_Prof_ID() {
        return mbiz_Sub_Prof_ID;
    }

    public void setMbiz_Sub_Prof_ID(int mbiz_Sub_Prof_ID) {
        this.mbiz_Sub_Prof_ID = mbiz_Sub_Prof_ID;
    }

    public int getMbiz_Sub_Cus_ID() {
        return mbiz_Sub_Cus_ID;
    }

    public void setMbiz_Sub_Cus_ID(int mbiz_Sub_Cus_ID) {
        this.mbiz_Sub_Cus_ID = mbiz_Sub_Cus_ID;
    }

    public String getMb_Sub_State() {
        return mb_Sub_State;
    }

    public void setMb_Sub_State(String mb_Sub_State) {
        this.mb_Sub_State = mb_Sub_State;
    }

    public String getMb_Sub_Name() {
        return mb_Sub_Name;
    }

    public void setMb_Sub_Name(String mb_Sub_Name) {
        this.mb_Sub_Name = mb_Sub_Name;
    }

    public String getMb_Sub_Office() {
        return mb_Sub_Office;
    }

    public void setMb_Sub_Office(String mb_Sub_Office) {
        this.mb_Sub_Office = mb_Sub_Office;
    }
}
