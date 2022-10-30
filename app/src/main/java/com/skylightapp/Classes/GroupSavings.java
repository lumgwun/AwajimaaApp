package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class GroupSavings implements Parcelable, Serializable {
    public static final String GROUP_SAVINGS_TABLE = "grp_savings_Table";
    public static final String GROUP_SAVINGS_ID = "gS_id";
    public static final String GROUP_SAVINGS_CURRENCY = "gs_currency";
    public static final String GROUP_SAVINGS_AMOUNT = "gs_amount";
    public static final String GROUP_SAVINGS_DATE = "gS_Date";
    public static final String GROUP_SAVINGS_STATUS = "gS_Status";
    public static final String GS_GRP_ACCT_ID = "gs_grp_acctID";
    public static final String GROUP_SAVINGS_PROF_ID = "gs_prof_id";
    public static final String GROUP_SAVINGS_DB_ID = "gs_DB_id";
    public static final String GS_GRP_ID = "gs_grp_ID";
    public static final String GS_FINE = "Fine_GS";
    public static final String GS_CODE = "grp_savings_code";



    public static final String CREATE_GROUP_SAVINGS_TABLE = "CREATE TABLE IF NOT EXISTS " + GROUP_SAVINGS_TABLE + " (" + GROUP_SAVINGS_PROF_ID + " INTEGER ," + GS_GRP_ACCT_ID + " INTEGER ,"+
            GROUP_SAVINGS_ID + " INTEGER , " + GS_GRP_ID + " INTEGER, " + GROUP_SAVINGS_CURRENCY + " TEXT, " + GROUP_SAVINGS_AMOUNT + " REAL, " + GROUP_SAVINGS_DATE +" TEXT, "+ GROUP_SAVINGS_STATUS + " TEXT, " + GROUP_SAVINGS_DB_ID + " INTEGER, " +
            "PRIMARY KEY(" + GROUP_SAVINGS_DB_ID + "), " +"FOREIGN KEY(" + GS_GRP_ACCT_ID + ") REFERENCES " + GRP_ACCT_TABLE + "(" + GRP_ACCT_ID + ")," +"FOREIGN KEY(" + GROUP_SAVINGS_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    private String savingsDate;
    private int gProfileID;
    private Profile gProfile;
    private Account gAccount;
    private String gStatus;
    private double gAmount;
    private int grpSavingsID;
    private int grpSavingsAcctID;
    private String grpSavingsCurrency;
    private String savingsDateStr;
    private ArrayList<Transaction> transactions;

    public GroupSavings (int grpSavingsID,String groupName,double amount, String savingsDate, String status) {
        this.grpSavingsID = grpSavingsID;
        this.gAmount = amount;
        this.savingsDate = savingsDate;
        this.gStatus = status;
        gAccount = new Account();
        gProfile = new Profile();

    }

    public GroupSavings() {

    }

    public GroupSavings(int profileID, int savingsID, double totalAmount, String dateOfSavings,  String status) {
        this.gProfileID = profileID;
        this.grpSavingsID = savingsID;
        this.gAmount = totalAmount;
        this.savingsDateStr = dateOfSavings;
        this.gStatus = status;

    }
    public GroupSavings(int profileID, int savingsID, double totalAmount, String grpSavingsCurrency,String dateOfSavings,  String status) {
        this.gProfileID = profileID;
        this.grpSavingsID = savingsID;
        this.gAmount = totalAmount;
        this.savingsDateStr = dateOfSavings;
        this.gStatus = status;
        this.grpSavingsCurrency = grpSavingsCurrency;

    }
    public GroupSavings(int grpSavingsID, int profID, int acctID, String grpSavingsDate, double amount, String currency, String status) {
        this.gProfileID = profID;
        this.grpSavingsID = grpSavingsID;
        this.gAmount = amount;
        this.savingsDateStr = grpSavingsDate;
        this.gStatus = status;
        this.grpSavingsCurrency = currency;
        this.grpSavingsAcctID = acctID;

    }

    protected GroupSavings(Parcel in) {
        gProfileID = in.readInt();
        gProfile = in.readParcelable(Profile.class.getClassLoader());
        gAccount = in.readParcelable(Account.class.getClassLoader());
        gStatus = in.readString();
        gAmount = in.readDouble();
        grpSavingsID = in.readInt();
        transactions = in.createTypedArrayList(Transaction.CREATOR);
    }


    public static final Creator<GroupSavings> CREATOR = new Creator<GroupSavings>() {
        @Override
        public GroupSavings createFromParcel(Parcel in) {
            return new GroupSavings(in);
        }

        @Override
        public GroupSavings[] newArray(int size) {
            return new GroupSavings[size];
        }
    };



    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public Profile getProfile() {
        return gProfile;
    }
    public void setProfile(Profile profile1) {
        this.gProfile = profile1;
    }
    public void setAccount(Account account) {
        this.gAccount = account;
    }

    public Account getAccount() {
        return gAccount;
    }



    public String getSavingsDate() { return savingsDate; }
    public void setSavingsDate(String savingsDate) { this.savingsDate = savingsDate; }

    public int getGrpSavingsID() {
        return grpSavingsID;
    }
    public void setGrpSavingsID(int grpSavingsID) { this.grpSavingsID = grpSavingsID; }

    public String getStatus() {
        return gStatus;
    }
    public void seStatus(String status) { this.gStatus = status; }
    public double getGrpSavingsAmount() {
        return gAmount;
    }
    public void setGrpSavingsAmount(double amount) { this.gAmount = amount; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(gProfileID);
        parcel.writeParcelable(gProfile, i);
        parcel.writeParcelable(gAccount, i);
        parcel.writeString(gStatus);
        parcel.writeDouble(gAmount);
        parcel.writeInt(grpSavingsID);
        parcel.writeTypedList(transactions);
    }

    public String getGrpSavingsCurrency() {
        return grpSavingsCurrency;
    }

    public void setGrpSavingsCurrency(String grpSavingsCurrency) {
        this.grpSavingsCurrency = grpSavingsCurrency;
    }

    public String getSavingsDateStr() {
        return savingsDateStr;
    }

    public void setSavingsDateStr(String savingsDateStr) {
        this.savingsDateStr = savingsDateStr;
    }

    public int getGrpSavingsAcctID() {
        return grpSavingsAcctID;
    }

    public void setGrpSavingsAcctID(int grpSavingsAcctID) {
        this.grpSavingsAcctID = grpSavingsAcctID;
    }
}
