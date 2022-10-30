package com.skylightapp.Classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import com.skylightapp.MarketClasses.GrpSNewUserReq;

public class GroupAccount implements Parcelable, Serializable {
    public static final String GRP_ACCT_TABLE = "GrpAcctTable";
    public static final String GRP_PROFILE_TABLE = "GrpProfileTable";
    public static final String GRP_ACCT_ID = "grpAcct_id";
    public static final String GRPA_SURNAME = "surnameG";
    public static final String GRPA_FIRSTNAME = "first_nameG";
    public static final String GRPA_EMAIL = "emailG";
    public static final String GRPA_PHONE = "phoneG";
    public static final String GRPA_TITTLE = "TittleG";
    public static final String GRPA_PURPOSE = "PurposeG";
    public static final String GRPA_START_DATE = "StartDateG";
    public static final String GRPA_END_DATE = "EndDateG";
    public static final String GRPA_STATUS = "StatusG";
    public static final String GROUP_NEW_USER_ID = "GRP_Prof_ID";
    public static final String GRPA_PROFILE_ID = "GRP_Acct_Prof_ID";
    public static final String GRPA_LINK = "GRP_Link";

    public static final String GRPA_BALANCE = "Balance";
    public static final Boolean ISCOMPLETE = false;
    public static final Boolean HASCARRIED = true;

    public static final String GRP_ASSIGNED_ID = "grpp_id";
    public static final String GRP_PROFILE_PIX = "GRPPPix";

    public static final String GRP_PROFILE_SURNAME = "GRPPSurname";
    public static final String GRP_PROFILE_FIRSTNAME = "GRPPFirstName";
    public static final String GRP_PROFILE_JOINED_DATE = "GRPPJoinedDate";
    public static final String GRP_PROFILE_JOINED_STATUS = "GRPPJoinedStatus";
    public static final String GRP_CREATOR_PROFILE_ID = "GRPP_Prof_ID";

    public static final String GRPA_DBID = "GRPP_Acct_DB_ID";
    public static final String GRP_PROF_ACCT_ID = "GRP_Prof_AcctID";
    public static final String GRP_PROF_DBID = "GRP_Prof_DB_ID";

    public static final String GRPA_FREQ = "grp_freq";
    public static final String GRPA_DURATION = "grpa_duration";
    public static final String GRPA_REM_DURATION = "grpa_Rem_duration";
    public static final String GRPA_AMT = "grpa_Amt";
    public static final String GRPA_CURRENCY_CODE = "grpa_Currency_Code";
    public static final String GRPA_SCORE = "grpa_Score";


    public static final String CREATE_GRP_ACCT_TABLE = "CREATE TABLE IF NOT EXISTS " + GRP_ACCT_TABLE + " (" + GRP_ACCT_ID + " INTEGER, " + GRPA_PROFILE_ID + " INTEGER , " +
            GRPA_TITTLE + " TEXT, " + GRPA_PURPOSE + " TEXT, "+ GRPA_SURNAME + " TEXT, " + GRPA_FIRSTNAME + " TEXT, " + GRPA_EMAIL + " TEXT, " + GRPA_PHONE + " TEXT, " +
            GRPA_START_DATE + " TEXT, " + GRPA_END_DATE + " TEXT, " + GRPA_BALANCE + " REAL, "+ GRPA_STATUS + " TEXT, " +
            ISCOMPLETE + " REAL, " + HASCARRIED + " REAL, "  + GRPA_DBID + " INTEGER, "+ GRPA_LINK + " TEXT, "+ GRPA_FREQ + " INTEGER, "+ GRPA_DURATION + " INTEGER, "+ GRPA_AMT + " REAL, "+ GRPA_CURRENCY_CODE + " TEXT, "+ GRPA_REM_DURATION + " INTEGER, "+ GRPA_SCORE + " INTEGER, "+ "PRIMARY KEY(" +GRPA_DBID + "), " +"FOREIGN KEY(" + GRPA_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";


    public static final String CREATE_GRP_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS " + GRP_PROFILE_TABLE + " (" + GRP_ASSIGNED_ID + " INTEGER, " + GRP_CREATOR_PROFILE_ID + " INTEGER , " +
            GROUP_NEW_USER_ID + " INTEGER, " + GRP_PROFILE_PIX + " TEXT, "+ GRP_PROFILE_SURNAME + " TEXT, " + GRP_PROFILE_FIRSTNAME + " TEXT, " + GRP_PROFILE_JOINED_DATE + " TEXT, "+ GRP_PROFILE_JOINED_STATUS + " TEXT, "+ GRP_PROF_ACCT_ID + " TEXT, " + GRP_PROF_DBID + " INTEGER, "+ "PRIMARY KEY(" + GRP_PROF_DBID + "), " +"FOREIGN KEY(" + GRP_CREATOR_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + GRP_PROF_ACCT_ID + ") REFERENCES " + GRP_ACCT_TABLE + "(" + GRP_ACCT_ID + "))";

    private ArrayList<Account> grpAccounts;
    private ArrayList<GrpSavingsProfile> grpSavingsProfiles;
    private ArrayList<GroupSavings> grpSSavings;
    private ArrayList<Transaction> grptranX;
    private String grpFirstName;
    private String grpLastName;
    private String grpPhoneNo;
    private String grpEmailAddress;
    private String grpCountry;
    private String grpStatus;
    private int grpAcctNo;
    private int grpProfileID;
    private String grpEndDate;
    private String grpStartDate;
    private double grpAcctBalance;
    private String grpPurpose;
    private String grpTittle;
    private String grpLink;
    private boolean grpHasCarried =false;
    private boolean grpIsComplete =false;
    private int grpFreqNo;
    private int grpDuration;
    private String grpCurrencyCode;
    private double grpAmt;
    private int grpRemDuration;
    private int grpScore;
    private GrpSNewUserReq grpSNewUserReq;

    public GroupAccount (int grpAcctNo, String grpTittle, String grpPurpose, String grpFirstName, String grpLastName, String grpPhoneNo, String grpEmailAddress, String grpStartDate, double grpAcctBalance, String grpEndDate, String grpStatus) {
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
        grpSavingsProfiles = new ArrayList<>();

    }
    public GroupAccount (int grpAcctNo, boolean grpHasCarried, boolean grpIsComplete) {
        this.grpAcctNo = grpAcctNo;
        this.grpHasCarried = grpHasCarried;
        this.grpIsComplete = grpIsComplete;
        grpAccounts = new ArrayList<>();
        grpSavingsProfiles = new ArrayList<>();

    }

    public GroupAccount() {
        super();
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
        grpAcctNo = in.readInt();
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

    public GroupAccount(int grpSavingsNo, int grpProfileID, String grpTittle, String grpPurpose, String grpFirstName, double totalAmount, String dateOfSavings, String grpStatus) {
        this.grpProfileID = grpProfileID;
        this.grpFirstName = grpFirstName;
        this.grpPurpose = grpPurpose;
        this.grpTittle = grpTittle;
        this.grpStartDate = dateOfSavings;
        this.grpStatus = grpStatus;
        this.grpAcctBalance = totalAmount;
        this.grpAcctNo = grpSavingsNo;
    }


    public GroupAccount(int profID, int acctID, String tittle, String purpose, String name, String grpSavingsDate, double  amount, String currencyCode, int selectedFreq, int selectedDuration, int remDuration, String endDate, String link, String status) {
        this.grpAcctNo = acctID;
        this.grpTittle = tittle;
        this.grpProfileID = profID;
        this.grpFirstName = name;
        this.grpPurpose = purpose;
        this.grpStartDate = grpSavingsDate;
        this.grpAmt = amount;
        this.grpCurrencyCode = currencyCode;
        this.grpFreqNo = selectedFreq;
        this.grpDuration = selectedDuration;
        this.grpRemDuration = remDuration;
        this.grpLink= link;
        this.grpStatus = status;
        this.grpEndDate = endDate;

    }
    public GroupAccount(int grpAcctNo, int profileUID, String tittle, String purpose, String managerFirstName, String managerSurname, String managerPhoneNumber1, String managerEmail, String currentDate, double amount, String currencyCode, int selectedFreq, int selectedDuration, double acctBalance) {
        this.grpAcctNo = grpAcctNo;
        this.grpProfileID = profileUID;
        this.grpFirstName = managerFirstName;
        this.grpPurpose = purpose;
        this.grpLastName = managerSurname;
        this.grpTittle = tittle;
        this.grpPhoneNo = managerPhoneNumber1;
        this.grpStartDate = currentDate;
        this.grpAmt = amount;
        this.grpCurrencyCode = currencyCode;
        this.grpFreqNo = selectedFreq;
        this.grpEmailAddress = managerEmail;
        this.grpDuration = selectedDuration;
        this.grpAcctBalance = acctBalance;

    }


    public void addGrpSavings(int grpSavingsNo, int profileID, double totalAmount, String dateOfSavings, String status) {
        grpSavingsNo = grpSSavings.size() + 1;
        GroupSavings groupSavings1 = new GroupSavings(grpSavingsNo,profileID, totalAmount, dateOfSavings, status);
        grpSSavings.add(groupSavings1);

    }
    public void addGrpSavingsProfile(GrpSavingsProfile grpSavingsProfile) {
        grpSavingsProfiles=new ArrayList<>();
        grpSavingsProfiles.add(grpSavingsProfile);

    }
    public void addGrpSkyLightAcct(int acctNo, int acctID) {
        acctNo = grpAccounts.size() + 1;
        Account account = new Account(acctNo,acctID);
        grpAccounts.add(account);

    }
    public void addGrpTransactions(Transaction transaction) {
        grptranX=new ArrayList<>();
        grptranX.add(transaction);

    }

    public String getGrpStartDate() { return grpStartDate; }
    public void setGrpStartDate(String grpStartDate) { this.grpStartDate = grpStartDate; }

    public String getGrpEndDate() { return grpEndDate; }
    public void setGrpEndDate(String grpEndDate) { this.grpEndDate = grpEndDate;
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
    public void setGrpProfileID(int grpProfileID) { this.grpProfileID = grpProfileID;
    }
    public String getGrpLastName() {
        return grpLastName;
    }
    public void setGrpLastName(String grpLastName) { this.grpLastName = grpLastName; }
    public void setGrpCountry(String grpCountry) { this.grpCountry = grpCountry; }
    public String getGrpCountry() {
        return grpCountry;
    }
    public void setGrpAcctNo(int grpAcctNo) { this.grpAcctNo = grpAcctNo; }
    public int getGrpAcctNo() {
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
    public ArrayList<GrpSavingsProfile> getGrpProfiles() { return grpSavingsProfiles; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(grpAccounts);
        parcel.writeTypedList(grpSavingsProfiles);
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

    public String getGrpLink() {
        return grpLink;
    }

    public void setGrpLink(String grpLink) {
        this.grpLink = grpLink;
    }

    public int getGrpFreqNo() {
        return grpFreqNo;
    }

    public void setGrpFreqNo(int grpFreqNo) {
        this.grpFreqNo = grpFreqNo;
    }

    public int getGrpDuration() {
        return grpDuration;
    }

    public void setGrpDuration(int grpDuration) {
        this.grpDuration = grpDuration;
    }

    public String getGrpCurrencyCode() {
        return grpCurrencyCode;
    }

    public void setGrpCurrencyCode(String grpCurrencyCode) {
        this.grpCurrencyCode = grpCurrencyCode;
    }

    public double getGrpAmt() {
        return grpAmt;
    }

    public void setGrpAmt(double grpAmt) {
        this.grpAmt = grpAmt;
    }

    public int getGrpRemDuration() {
        return grpRemDuration;
    }

    public void setGrpRemDuration(int grpRemDuration) {
        this.grpRemDuration = grpRemDuration;
    }

    public int getGrpScore() {
        return grpScore;
    }

    public void setGrpScore(int grpScore) {
        this.grpScore = grpScore;
    }

    public GrpSNewUserReq getGrpSNewUserReq() {
        return grpSNewUserReq;
    }

    public void setGrpSNewUserReq(GrpSNewUserReq grpSNewUserReq) {
        this.grpSNewUserReq = grpSNewUserReq;
    }
}
