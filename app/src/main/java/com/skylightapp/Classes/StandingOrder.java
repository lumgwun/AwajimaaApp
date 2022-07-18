package com.skylightapp.Classes;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCOUNT_NO;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCT_TABLE;

@Entity(tableName = StandingOrder.STANDING_ORDER_TABLE)
public class StandingOrder implements Serializable, Parcelable {
    public static final String STANDING_ORDER_TABLE = "standingOrderTable";
    public static final String SO_ID = "sO_id";
    public static final String SO_DAILY_AMOUNT = "So_Daily_Amount";
    public static final String SO_EXPECTED_AMOUNT = "so_AmountExp";
    public static final String SO_RECEIVED_AMOUNT = "so_AmountReceived";
    public static final String SO_TOTAL_DAYS = "so_TotalDays";
    public static final String SO_DAYS_REMAINING = "so_DaysRem";
    public static final String SO_AMOUNT_DIFF = "so_Amount_Diff";
    public static final String SO_STATUS = "So_status";
    public static final String SO_ACCT_NO = "SO_Acct_no";
    public static final String SO_START_DATE = "SO_Start_Date";
    public static final String SO_END_DATE = "SO_End_Date";
    public static final String SO_CUS_ID = "SO_Cus_ID";
    public static final String SO_PROF_ID = "SO_Prof_ID";
    public static final String SO_APPROF_DATE = "SO_APProval_Date";

    public static final String CREATE_SO_TABLE = "CREATE TABLE IF NOT EXISTS " + STANDING_ORDER_TABLE + " (" + SO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SO_DAILY_AMOUNT + " REAL, " +
            SO_CUS_ID + " INTEGER , " + SO_EXPECTED_AMOUNT + " REAL , " + SO_RECEIVED_AMOUNT + " REAL , " + SO_TOTAL_DAYS + " REAL , " + SO_AMOUNT_DIFF + " REAL , " +
            SO_DAYS_REMAINING + " REAL , " + SO_STATUS + " TEXT , " + SO_ACCT_NO + " INTEGER , " + SO_START_DATE + " TEXT, " + SO_END_DATE + " TEXT, "+ SO_APPROF_DATE + " TEXT, " + SO_PROF_ID + " INTEGER, "+"FOREIGN KEY(" + SO_ACCT_NO + ") REFERENCES " + SO_ACCT_TABLE + "(" + SO_ACCOUNT_NO + ")," +"FOREIGN KEY(" + SO_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "FOREIGN KEY(" + SO_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    @PrimaryKey(autoGenerate = true)
    private int soID=10;
    double so_ExpectedAmount;
    double so_ReceivedAmount;
    double so_AmountDiff;
    double soDailyAmount;
    String soStatus;
    int so_Acct_No;
    int so_TotalDays;
    int so_DaysRemaining;
    String so_start_date;
    String so_Names;
    String so_end_date;
    double so_Balance;
    Customer so_Customer;
    Profile so_Profile;
    Account so_Account;
    private Transaction so_Tranx;

    public StandingOrder(long soNo, double so_ExpectedAmount) {

    }

    public StandingOrder (int soID, int so_Acct_No, double soDailyAmount, String so_start_date, double so_ExpectedAmount, double so_ReceivedAmount, double so_AmountDiff, String soStatus, String so_end_date) {
        this.soID = soID;
        this.so_Acct_No = so_Acct_No;
        this.soDailyAmount = soDailyAmount;
        this.so_start_date = so_start_date;
        this.so_ExpectedAmount = so_ExpectedAmount;
        this.so_start_date = so_start_date;
        this.so_ReceivedAmount = so_ReceivedAmount;
        this.so_AmountDiff = so_AmountDiff;
        this.soStatus = soStatus;
        this.so_end_date = so_end_date;
    }
    public StandingOrder(int soID, String so_Names, double so_Balance) {
        this.soID = soID;
        this.so_Names = so_Names;
        this.so_Balance = so_Balance;
    }

    public StandingOrder(int profileID, int customerID, int soAcctNo, double amountCarriedForward, String currentDate, double so_ExpectedAmount, double sONowAmount, double amtDiff, double amtDiff1, int so_TotalDays, int so_DaysRemaining, String so_end_date, String inProgress) {
        this.soID = profileID;
        this.so_Acct_No = soAcctNo;
        this.so_TotalDays = so_TotalDays;
        this.so_DaysRemaining = so_DaysRemaining;
        this.soDailyAmount = amountCarriedForward;
        this.so_start_date = currentDate;
        this.so_ExpectedAmount = so_ExpectedAmount;
        this.so_start_date = currentDate;
        this.so_ReceivedAmount = sONowAmount;
        this.so_AmountDiff = amtDiff;
        this.soStatus = inProgress;
        this.so_end_date = so_end_date;
    }

    public StandingOrder(int soNo, double so_ExpectedAmount) {
        this.so_ExpectedAmount = so_ExpectedAmount;
        this.so_Acct_No = soNo;

    }

    public StandingOrder(int soAcctNo, double so_ExpectedAmount, double sONowAmount, double amountCarriedForward, String currentDate, int months, int so_DaysRemaining, String endDate) {
        this.so_Acct_No = soAcctNo;
        this.so_ExpectedAmount = so_ExpectedAmount;
        this.so_ReceivedAmount = sONowAmount;
        this.so_AmountDiff = amountCarriedForward;
        this.so_end_date = endDate;
        this.so_TotalDays = months*31;
        this.so_DaysRemaining = so_DaysRemaining;
        this.so_start_date = currentDate;
    }

    public StandingOrder() {
        super();
    }

    protected StandingOrder(Parcel in) {
        soID = in.readInt();
        so_ExpectedAmount = in.readDouble();
        so_ReceivedAmount = in.readDouble();
        so_AmountDiff = in.readDouble();
        soDailyAmount = in.readDouble();
        soStatus = in.readString();
        so_Acct_No = in.readInt();
        so_TotalDays = in.readInt();
        so_DaysRemaining = in.readInt();
        so_start_date = in.readString();
        so_Names = in.readString();
        so_end_date = in.readString();
        so_Balance = in.readDouble();
        so_Customer = in.readParcelable(Customer.class.getClassLoader());
        so_Profile = in.readParcelable(Profile.class.getClassLoader());
        so_Account = in.readParcelable(Account.class.getClassLoader());
    }

    public static final Creator<StandingOrder> CREATOR = new Creator<StandingOrder>() {
        @Override
        public StandingOrder createFromParcel(Parcel in) {
            return new StandingOrder(in);
        }

        @Override
        public StandingOrder[] newArray(int size) {
            return new StandingOrder[size];
        }
    };

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
    public double getSo_ExpectedAmount() {
        return so_ExpectedAmount;
    }
    public void setSoExpectedAmount(double expectedAmount) {
        this.so_ExpectedAmount = expectedAmount;
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
    public Customer getSo_Customer() {
        return so_Customer;
    }
    public void setSo_Customer(Customer so_Customer) {
        this.so_Customer = so_Customer;
    }
    public Account getSo_Account() {
        return so_Account;
    }
    public void setSo_Account(Account so_Account) {
        this.so_Account = so_Account;
    }

    public int getSo_TotalDays() {
        return so_TotalDays;
    }
    public void setSo_TotalDays(int so_TotalDays) {
        this.so_TotalDays = so_TotalDays;
    }
    public double getDaysRemaing() {
        return so_DaysRemaining;
    }
    public void setSo_DaysRemaining(int so_DaysRemaining) {
        this.so_DaysRemaining = so_DaysRemaining;
    }

    public double getSo_ReceivedAmount() {
        return so_ReceivedAmount;
    }
    public void setSoReceivedAmount(double receivedAmount) {
        this.so_ReceivedAmount = receivedAmount;
    }
    public String getSoStatus() {
        return soStatus;
    }
    public void setSoStatus(String soStatus) {
        this.soStatus = soStatus;
    }
    public double getSo_AmountDiff() {
        return so_AmountDiff;
    }
    public void setSoAmountDiff(double amountDiff) {
        this.so_AmountDiff = amountDiff;
    }

    public Profile getSo_Profile() {
        return so_Profile;
    }

    public void setSo_Profile(Profile so_Profile) {
        this.so_Profile = so_Profile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(soID);
        parcel.writeDouble(so_ExpectedAmount);
        parcel.writeDouble(so_ReceivedAmount);
        parcel.writeDouble(so_AmountDiff);
        parcel.writeDouble(soDailyAmount);
        parcel.writeString(soStatus);
        parcel.writeInt(so_Acct_No);
        parcel.writeInt(so_TotalDays);
        parcel.writeInt(so_DaysRemaining);
        parcel.writeString(so_start_date);
        parcel.writeString(so_Names);
        parcel.writeString(so_end_date);
        parcel.writeDouble(so_Balance);
        parcel.writeParcelable(so_Customer, i);
        parcel.writeParcelable(so_Profile, i);
        parcel.writeParcelable(so_Account, i);
    }

    public Transaction getSo_Tranx() {
        return so_Tranx;
    }

    public void setSo_Tranx(Transaction so_Tranx) {
        this.so_Tranx = so_Tranx;
    }
}
