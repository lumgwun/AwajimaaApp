package com.skylightapp.Classes;

import com.skylightapp.MarketClasses.TimeUtils;

import java.io.Serializable;

import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class DailyAccount implements Serializable, Comparable<DailyAccount>{

    private int dailyAcctID;
    private String dAcctContent;
    private double dAcctAmount;
    private String dAcctCurrType;
    private String dAcctCreateTime;
    private boolean dAcctIsIncome;
    private int dAcctProfID;
    private int dAcctAcctID;
    private int dAcctCusID;
    private String dAcctStatus;
    public static final String DAILY_ACCOUNTING_TABLE = "daily_account_table";
    public static final String ID_DA = "id_DA";
    public static final String CREATE_TIME_DA = "create_timeDA";
    public static final String AMOUNT_DA = "amountDA";
    public static final String IS_INCOME_DA = "is_incomeDA";
    public static final String CONTENT_DA = "contentDA";
    public static final String CURRENCY_TYPE_DA = "currency_typeDA";
    public static final String STATUS_DA = "status_DA";
    public static final String DA_PROF_ID = "id_DA_Prof";
    public static final String DA_CUS_ID = "id_DA";
    public static final String DA_ACCT_ID = "id_DA_Acct_ID";

    public static final String CREATE_DAILY_ACCOUNTING_TABLE = "CREATE TABLE " + DAILY_ACCOUNTING_TABLE + "("
            + ID_DA + " INTEGER ,"
            + CREATE_TIME_DA + " TEXT NOT NULL,"
            + CURRENCY_TYPE_DA + " TEXT NOT NULL,"
            + AMOUNT_DA + " REAL NOT NULL,"
            + IS_INCOME_DA + " BOOLEAN,"
            + CONTENT_DA + " TEXT,"+STATUS_DA+ " TEXT, "+DA_PROF_ID+ " TEXT, "+DA_CUS_ID+ " TEXT, "+DA_ACCT_ID+ " TEXT, "+"FOREIGN KEY(" + DA_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + DA_CUS_ID  + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," + "FOREIGN KEY(" + DA_ACCT_ID + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +
            "PRIMARY KEY AUTOINCREMENT UNIQUE(" + ID_DA + "))";






    public DailyAccount(){
        this.dAcctContent = "";
        this.dAcctAmount = 0.0;
        this.dAcctCurrType = "";
        this.dAcctCreateTime = TimeUtils.now();
        this.dAcctIsIncome = false;
    }

    public int getDailyAcctID() {
        return dailyAcctID;
    }

    public void setDailyAcctID(int dailyAcctID) {
        this.dailyAcctID = dailyAcctID;
    }

    public String getdAcctContent() {
        return dAcctContent;
    }

    public void setdAcctContent(String dAcctContent) {
        this.dAcctContent = dAcctContent;
    }

    public double getdAcctAmount() {
        return dAcctAmount;
    }

    public void setdAcctAmount(double dAcctAmount) {
        this.dAcctAmount = dAcctAmount;
    }

    public String getdAcctCurrType() {
        return dAcctCurrType;
    }

    public void setdAcctCurrType(String dAcctCurrType) {
        this.dAcctCurrType = dAcctCurrType;
    }

    public String getdAcctCreateTime() {
        return dAcctCreateTime;
    }

    public void setdAcctCreateTime(String dAcctCreateTime) {
        this.dAcctCreateTime = dAcctCreateTime;
    }

    public boolean isdAcctIsIncome(){
        return this.dAcctIsIncome;
    }

    public void setdAcctIsIncome(boolean isIncome){
        this.dAcctIsIncome = isIncome;
    }

    @Override
    public String toString() {
        return "Time: " + this.dAcctCreateTime + "\n" +
                "Content: " + this.dAcctContent + "\n" +
                "Amount: " + this.dAcctAmount + "\n" +
                "Type: " + this.dAcctCurrType;
    }

    public String getSimpleString() {
        return this.dAcctCreateTime + this.dAcctContent + this.dAcctAmount + this.dAcctCurrType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DailyAccount){
            DailyAccount dailyAccount = (DailyAccount)obj;
            return this.dAcctCreateTime.equals(dailyAccount.getdAcctCreateTime());
        }
        return false;
    }

    @Override
    public int compareTo(DailyAccount dailyAccount) {
        return this.dAcctCreateTime.compareTo(dailyAccount.dAcctCreateTime);
    }


    public int getdAcctProfID() {
        return dAcctProfID;
    }

    public void setdAcctProfID(int dAcctProfID) {
        this.dAcctProfID = dAcctProfID;
    }

    public int getdAcctAcctID() {
        return dAcctAcctID;
    }

    public void setdAcctAcctID(int dAcctAcctID) {
        this.dAcctAcctID = dAcctAcctID;
    }

    public int getdAcctCusID() {
        return dAcctCusID;
    }

    public void setdAcctCusID(int dAcctCusID) {
        this.dAcctCusID = dAcctCusID;
    }


    public String getdAcctStatus() {
        return dAcctStatus;
    }

    public void setdAcctStatus(String dAcctStatus) {
        this.dAcctStatus = dAcctStatus;
    }
}
