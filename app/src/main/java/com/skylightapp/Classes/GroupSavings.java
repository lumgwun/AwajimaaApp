package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static com.skylightapp.Classes.GroupAccount.GRPA_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class GroupSavings implements Parcelable, Serializable {
    public static final String GROUP_SAVINGS_TABLE = "Grp_savings_Table";
    public static final String GS_ID = "GS_id";

    public static final String GROUP_NAME = "GS_Name";
    public static final String GROUP_PURPOSE = "Purpose_amount";
    public static final String GROUP_AMOUNT = "GS_amount";
    public static final String GROUP_DATE = "GS_Date";
    public static final String GS_STATUS = "GS_Status";
    public static final String GS_COUNTRY = "GS_Country";
    public static final String GS_ACOUNT = "GS_Account";
    public static final String GS_ACCOUNT_ID = "GS_AccountID";

    public static final String GS_WHO_SHOULDBE_PAID = "Who_should_";
    public static final String GS_END_DATE = "GS_End_Date";
    public static final String GS_AMOUNT_CONTRIBUTED = "GS_Amount_So_Far";

    public static final String GS_GRPA_ID = "gs_gaID";
    public static final String GS_PROF_ID = "gs_prof_id";


    public static final String CREATE_GROUP_SAVINGS_TABLE = "CREATE TABLE IF NOT EXISTS " + GROUP_SAVINGS_TABLE + " (" + GS_PROF_ID + " INTEGER ," + GS_GRPA_ID + " INTEGER ,"+
            GS_ID + " INTEGER , " + GROUP_NAME + " TEXT, " + GROUP_PURPOSE + " TEXT, " + GROUP_AMOUNT + " NUMERIC, " + GROUP_DATE +" TEXT, "+ GS_STATUS + " TEXT, "  +
            "PRIMARY KEY(" + GS_ID + "), " +"FOREIGN KEY(" + GS_GRPA_ID  + ") REFERENCES " + GRP_ACCT_TABLE + "(" + GRPA_ID + ")," +"FOREIGN KEY(" + GS_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    private Date savingsDate;
    private long gProfileID;
    private Profile gProfile;
    private Account gAccount;
    private String gStatus;
    private double gAmount;
    private int grpSavingsID;
    String groupName;
    private ArrayList<Transaction> transactions;

    public GroupSavings (int grpSavingsID, long profileID, double amount, Date savingsDate, String status) {
        this.grpSavingsID = grpSavingsID;
        this.gProfileID = profileID;
        this.gAmount = amount;
        this.savingsDate = savingsDate;
        this.gStatus = status;
        gAccount = new Account();
        gProfile = new Profile();

    }
    public GroupSavings (int grpSavingsID,String groupName,double amount, Date savingsDate, String status) {
        this.grpSavingsID = grpSavingsID;
        this.gAmount = amount;
        this.savingsDate = savingsDate;
        this.gStatus = status;
        gAccount = new Account();
        gProfile = new Profile();

    }

    public GroupSavings() {

    }

    public GroupSavings(long profileID, int savingsID, double totalAmount, Date dateOfSavings,  String status) {
        this.gProfileID = profileID;
        this.grpSavingsID = savingsID;
        this.gAmount = totalAmount;
        this.savingsDate = dateOfSavings;
        this.gStatus = status;

    }

    protected GroupSavings(Parcel in) {
        gProfileID = in.readLong();
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



    public Date getSavingsDate() { return savingsDate; }
    public void setSavingsDate(Date savingsDate) { this.savingsDate = savingsDate; }

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
}
