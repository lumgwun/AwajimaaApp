package com.skylightapp.Classes;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;

@Entity(tableName = StandingOrder.STANDING_ORDER_TABLE)
public class StandingOrder extends Account {
    public static final String STANDING_ORDER_TABLE = "standingOrderTable";
    public static final String SO_ID = "sO_id";
    public static final String SO_DAILY_AMOUNT = "So_Daily_Amount";
    public static final String SO_EXPECTED_AMOUNT = "_AmountExp";
    public static final String SO_RECEIVED_AMOUNT = "AmountReceived";
    public static final String SO_TOTAL_DAYS = "TotalDays";
    public static final String SO_DAYS_REMAINING = "DaysRem";
    public static final String SO_AMOUNT_DIFF = "Amount_Diff";
    public static final String SO_STATUS = "So_status";
    public static final String SO_ACCT_NO = "SO_Acct_no";
    public static final String SO_START_DATE = "SO_Start_Date";
    public static final String SO_END_DATE = "SO_End_Date";

    public static final String CREATE_SO_TABLE = "CREATE TABLE IF NOT EXISTS " + STANDING_ORDER_TABLE + " (" + SO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SO_DAILY_AMOUNT + " DOUBLE, " +
            CUSTOMER_ID + " INTEGER NOT NULL, " + SO_EXPECTED_AMOUNT + " DOUBLE , " + SO_RECEIVED_AMOUNT + " DOUBLE , " + SO_TOTAL_DAYS + " DOUBLE , " + SO_AMOUNT_DIFF + " DOUBLE , " +
            SO_DAYS_REMAINING + " DOUBLE , " + SO_STATUS + " TEXT , " + SO_ACCT_NO + " INTEGER , " + SO_START_DATE + " TEXT, " + SO_END_DATE + " TEXT, " + "FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";


    @PrimaryKey(autoGenerate = true)
    private int soID=10;
    double expectedAmount;
    double receivedAmount;
    double amountDiff;
    double soDailyAmount;
    String soStatus;
    int so_Acct_No;
    int totalDays;
    int daysRemaining;
    String so_start_date;
    String names;
    String so_end_date;
    double balance;
    Customer customer;
    Profile profile;
    Account account;

    public StandingOrder(long soNo, double expectedAmount) {

    }

    public StandingOrder (int soID, int so_Acct_No, double soDailyAmount, String so_start_date, double expectedAmount, double receivedAmount, double amountDiff, String soStatus, String so_end_date) {
        this.soID = soID;
        this.so_Acct_No = so_Acct_No;
        this.soDailyAmount = soDailyAmount;
        this.so_start_date = so_start_date;
        this.expectedAmount = expectedAmount;
        this.so_start_date = so_start_date;
        this.receivedAmount = receivedAmount;
        this.amountDiff = amountDiff;
        this.soStatus = soStatus;
        this.so_end_date = so_end_date;
    }
    public StandingOrder(int soID, String names, double balance) {
        this.soID = soID;
        this.names = names;
        this.balance = balance;
    }

    public StandingOrder(int profileID, int customerID, int soAcctNo, double amountCarriedForward, String currentDate, double expectedAmount, double sONowAmount, double amtDiff, double amtDiff1, int totalDays, int daysRemaining, String so_end_date, String inProgress) {
        this.soID = profileID;
        this.so_Acct_No = soAcctNo;
        this.totalDays = totalDays;
        this.daysRemaining = daysRemaining;
        this.soDailyAmount = amountCarriedForward;
        this.so_start_date = currentDate;
        this.expectedAmount = expectedAmount;
        this.so_start_date = currentDate;
        this.receivedAmount = sONowAmount;
        this.amountDiff = amtDiff;
        this.soStatus = inProgress;
        this.so_end_date = so_end_date;
    }

    public StandingOrder(int soNo, double expectedAmount) {
        this.expectedAmount = expectedAmount;
        this.so_Acct_No = soNo;

    }

    public StandingOrder(int soAcctNo, double expectedAmount, double sONowAmount, double amountCarriedForward, String currentDate, int months, int daysRemaining, String endDate) {
        this.so_Acct_No = soAcctNo;
        this.expectedAmount = expectedAmount;
        this.receivedAmount = sONowAmount;
        this.amountDiff = amountCarriedForward;
        this.so_end_date = endDate;
        this.totalDays = months*31;
        this.daysRemaining = daysRemaining;
        this.so_start_date = currentDate;
    }

    public StandingOrder() {
        super();
    }

    public int getSo_Acct_No() {
        return so_Acct_No;
    }
    public void setSoAcctNo(int so_Acct_No) {
        this.so_Acct_No = so_Acct_No;
    }
    public int getUID() {
        return soID;
    }
    public void setSoID(int soID) {
        this.soID = soID;
    }
    public String getSoEndDate() {
        return so_end_date;
    }
    public void setSoEndDate(String so_end_date) {
        this.so_end_date = so_end_date;
    }
    public double getExpectedAmount() {
        return expectedAmount;
    }
    public void setSoExpectedAmount(double expectedAmount) {
        this.expectedAmount = expectedAmount;
    }
    public double getSoDailyAmount() {
        return soDailyAmount;
    }
    public void setSoDailyAmount(double soDailyAmount) {
        this.soDailyAmount = soDailyAmount;
    }

    public String getSoStartDate() {
        return so_start_date;
    }
    public void setSoStartDate(String so_start_date) {
        this.so_start_date = so_start_date;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public int getTotalDays() {
        return totalDays;
    }
    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }
    public double getDaysRemaing() {
        return daysRemaining;
    }
    public void setDaysRemaining(int daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public double getReceivedAmount() {
        return receivedAmount;
    }
    public void setSoReceivedAmount(double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }
    public String getSoStatus() {
        return soStatus;
    }
    public void setSoStatus(String soStatus) {
        this.soStatus = soStatus;
    }
    public double getAmountDiff() {
        return amountDiff;
    }
    public void setSoAmountDiff(double amountDiff) {
        this.amountDiff = amountDiff;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
