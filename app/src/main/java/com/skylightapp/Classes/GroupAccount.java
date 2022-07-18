package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class GroupAccount implements Parcelable, Serializable {
    public static final String GRP_ACCT_TABLE = "GrpAcctTable";
    public static final String GRP_PROFILE_TABLE = "GrpProfileTable";
    public static final String GRPA_ID = "grpAcct_id";
    public static final String GRPA_SURNAME = "surnameG";
    public static final String GRPA_FIRSTNAME = "first_nameG";
    public static final String GRPA_EMAIL = "emailG";
    public static final String GRPA_PHONE = "phoneG";
    public static final String GRPA_TITTLE = "TittleG";
    public static final String GRPA_PURPOSE = "PurposeG";
    public static final String GRPA_START_DATE = "StartDateG";
    public static final String GRPA_END_DATE = "EndDateG";
    public static final String GRPA_STATUS = "StatusG";

    public static final String GRPA_BALANCE = "Balance";
    public static final Boolean ISCOMPLETE = false;
    public static final Boolean HASCARRIED = true;
    public static final String GRPP_ID = "grpp_id";
    public static final String GRPP_PIX = "GRPPPix";
    public static final String GRPP_SURNAME = "GRPPSurname";
    public static final String GRPP_FIRSTNAME = "GRPPFirstName";
    public static final String GRPP_JOINED_DATE = "GRPPJoinedDate";
    public static final String GRPP_JOINED_STATUS = "GRPPJoinedStatus";
    public static final String GRPP_PROFILE_ID = "GRPP_Prof_ID";
    public static final String GRPA_PROFID = "GRPP_Acct_Prof_ID";

    public static final String GRPP_ACCT_PROFILE_ID = "GRPP_Acct_Prof_ID";


    public static final String CREATE_GRP_ACCT_TABLE = "CREATE TABLE IF NOT EXISTS " + GRP_ACCT_TABLE + " (" + GRPA_ID + " INTEGER, " + GRPP_ACCT_PROFILE_ID + " INTEGER , " +
            GRPA_TITTLE + " TEXT, " + GRPA_PURPOSE + " TEXT, "+ GRPA_SURNAME + " TEXT, " + GRPA_FIRSTNAME + " TEXT, " + GRPA_EMAIL + " TEXT, " + GRPA_PHONE + " TEXT, " +
            GRPA_START_DATE + " TEXT, " + GRPA_END_DATE + " TEXT, " + GRPA_BALANCE + " REAL, "+ GRPA_STATUS + " TEXT, " +
            ISCOMPLETE + " REAL, " + HASCARRIED + " REAL, " + "PRIMARY KEY(" +GRPA_ID + "), " +"FOREIGN KEY(" + GRPP_ACCT_PROFILE_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";


    public static final String CREATE_GRP_ACCT_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS " + GRP_PROFILE_TABLE + " (" + GRPP_ID + " INTEGER, " + GRPP_PROFILE_ID + " INTEGER , " +
            GRPA_PROFID + " INTEGER, " + GRPP_PIX + " TEXT, "+ GRPP_SURNAME + " TEXT, " + GRPP_FIRSTNAME + " TEXT, " + GRPP_JOINED_DATE + " TEXT, "+ GRPP_JOINED_STATUS + " TEXT, " + "PRIMARY KEY(" +GRPP_ID + "), " +"FOREIGN KEY(" + GRPP_PROFILE_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + GRPA_PROFID  + ") REFERENCES " + GRP_ACCT_TABLE + "(" + GRPA_ID + "))";

    private ArrayList<Account> grpAccounts;
    private ArrayList<Profile> grpProfiles;
    private ArrayList<GroupSavings> grpSSavings;
    private ArrayList<Transaction> grptranX;
    private String grpFirstName;
    private String grpLastName;
    private String grpPhoneNo;
    private String grpEmailAddress;
    private String grpCountry;
    private String grpStatus;
    private long grpAcctNo;
    private long grpProfileID;
    private Date grpEndDate;
    private Date grpStartDate;
    private double grpAcctBalance;
    private String grpPurpose;
    private String grpTittle;
    private boolean grpHasCarried =false;
    private boolean grpIsComplete =false;



    public GroupAccount (long grpAcctNo, String grpTittle, String grpPurpose, String grpFirstName, String grpLastName, String grpPhoneNo, String grpEmailAddress, Date grpStartDate, double grpAcctBalance, Date grpEndDate, String grpStatus) {
        this.grpFirstName = grpFirstName;
        this.grpLastName = grpLastName;
        this.grpPurpose = grpPurpose;
        this.grpPhoneNo = grpLastName;
        this.grpEmailAddress = grpPurpose;
        this.grpTittle = grpTittle;
        this.grpEndDate = grpEndDate;
        this.grpStartDate = grpStartDate;
        this.grpStatus = grpStatus;
        this.grpAcctBalance = grpAcctBalance;
        this.grpAcctNo = grpAcctNo;
        grpAccounts = new ArrayList<>();
        grpProfiles = new ArrayList<>();

    }
    public GroupAccount (long grpAcctNo, boolean grpHasCarried, boolean grpIsComplete) {
        this.grpAcctNo = grpAcctNo;
        this.grpHasCarried = grpHasCarried;
        this.grpIsComplete = grpIsComplete;
        grpAccounts = new ArrayList<>();
        grpProfiles = new ArrayList<>();

    }

    public GroupAccount() {

    }

    protected GroupAccount(Parcel in) {
        grpAccounts = in.createTypedArrayList(Account.CREATOR);
        //profiles = in.createTypedArrayList(Profile.CREATOR);
        grpSSavings = in.createTypedArrayList(GroupSavings.CREATOR);
        grptranX = in.createTypedArrayList(Transaction.CREATOR);
        grpFirstName = in.readString();
        grpLastName = in.readString();
        grpPhoneNo = in.readString();
        grpEmailAddress = in.readString();
        grpCountry = in.readString();
        grpStatus = in.readString();
        grpAcctNo = in.readLong();
        grpAcctBalance = in.readDouble();
        grpPurpose = in.readString();
        grpTittle = in.readString();
        grpHasCarried = in.readByte() != 0;
        grpIsComplete = in.readByte() != 0;
    }

    public static final Creator<GroupAccount> CREATOR = new Creator<GroupAccount>() {
        @Override
        public GroupAccount createFromParcel(Parcel in) {
            return new GroupAccount(in);
        }

        @Override
        public GroupAccount[] newArray(int size) {
            return new GroupAccount[size];
        }
    };

    public GroupAccount(int grpSavingsNo, long grpProfileID, String grpTittle, String grpPurpose, String grpFirstName, double totalAmount, Date dateOfSavings, String grpStatus) {
        this.grpProfileID = grpProfileID;
        this.grpFirstName = grpFirstName;
        this.grpPurpose = grpPurpose;
        this.grpPurpose = grpPurpose;
        this.grpTittle = grpTittle;
        this.grpStartDate = dateOfSavings;
        this.grpStatus = grpStatus;
        this.grpAcctBalance = totalAmount;
        this.grpAcctNo = grpSavingsNo;
    }


    public void addGrpSavings(int grpSavingsNo, long profileID, double totalAmount, Date dateOfSavings, String status) {
        grpSavingsNo = grpSSavings.size() + 1;
        GroupSavings groupSavings1 = new GroupSavings(grpSavingsNo,profileID, totalAmount, dateOfSavings, status);
        grpSSavings.add(groupSavings1);

    }
    public void addGrpProfile(int profileNo, long profileID,  String status) {
        profileNo = grpProfiles.size() + 1;
        //Profile profile = new Profile(profileNo,profileID,status);
        //profiles.add(profile);

    }
    public void addGrpSkyLightAcct(int acctNo, int acctID) {
        acctNo = grpAccounts.size() + 1;
        Account account = new Account(acctNo,acctID);
        grpAccounts.add(account);

    }
    public void addGrpTransactions(int grpSavingsNo, long profileID, double totalAmount, Date dateOfSavings, String status) {
        grpSavingsNo = grpSSavings.size() + 1;
        GroupSavings groupSavings1 = new GroupSavings(grpSavingsNo,profileID, totalAmount, dateOfSavings, status);
        grpSSavings.add(groupSavings1);

    }

    public Date getGrpStartDate() { return grpStartDate; }
    public void setGrpStartDate(Date grpStartDate) { this.grpStartDate = grpStartDate; }

    public Date getGrpEndDate() { return grpEndDate; }
    public void setGrpEndDate(Date grpEndDate) { this.grpEndDate = grpEndDate;
    }

    public String getGrpTittle() {
        return grpTittle;
    }
    public void setGrpTittle(String grpTittle) { this.grpTittle = grpTittle;
    }
    public String getGrpPhoneNo() {
        return grpPhoneNo;
    }
    public void seGrpPhoneNo(String phoneNo) { this.grpPhoneNo = phoneNo; }
    public String getGrpEmailAddress() {
        return grpEmailAddress;
    }
    public void setGrpEmailAddress(String emailAddress) { this.grpEmailAddress = emailAddress; }

    public String getGrpFirstName() {
        return grpFirstName;
    }
    public void setGrpFirstName(String grpFirstName) { this.grpFirstName = grpFirstName;
    }
    public long getGrpProfileID() {
        return grpProfileID;
    }
    public void setGrpProfileID(long grpProfileID) { this.grpProfileID = grpProfileID;
    }
    public String getGrpLastName() {
        return grpLastName;
    }
    public void setGrpLastName(String grpLastName) { this.grpLastName = grpLastName; }
    public void setGrpCountry(String grpCountry) { this.grpCountry = grpCountry; }
    public String getGrpCountry() {
        return grpCountry;
    }
    public void setGrpAcctNo(long grpAcctNo) { this.grpAcctNo = grpAcctNo; }
    public long getGrpAcctNo() {
        return grpAcctNo;
    }
    public double getGrpAcctBalance() {
        return grpAcctBalance;
    }
    public void setGrpAcctBalance(double accountBalance) { this.grpAcctBalance = accountBalance; }
    public String getGrpPurpose() {
        return grpPurpose;
    }
    public void setGrpPurpose(String grpPurpose) { this.grpPurpose = grpPurpose; }
    public boolean getGrpHasCarried() {
        return grpHasCarried;
    }
    public void setGrpHasCarried(boolean grpHasCarried) { this.grpHasCarried = grpHasCarried; }
    public boolean getIsComplete() {
        return grpIsComplete;
    }
    public void setIsComplete(boolean isComplete) { this.grpIsComplete = isComplete; }

    public ArrayList<Account> getGrpAccounts() { return grpAccounts; }
    public ArrayList<Profile> getGrpProfiles() { return grpProfiles; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(grpAccounts);
        parcel.writeTypedList(grpProfiles);
        parcel.writeTypedList(grpSSavings);
        parcel.writeTypedList(grptranX);
        parcel.writeString(grpFirstName);
        parcel.writeString(grpLastName);
        parcel.writeString(grpPhoneNo);
        parcel.writeString(grpEmailAddress);
        parcel.writeString(grpCountry);
        parcel.writeString(grpStatus);
        parcel.writeLong(grpAcctNo);
        parcel.writeDouble(grpAcctBalance);
        parcel.writeString(grpPurpose);
        parcel.writeString(grpTittle);
        parcel.writeByte((byte) (grpHasCarried ? 1 : 0));
        parcel.writeByte((byte) (grpIsComplete ? 1 : 0));
    }
}
