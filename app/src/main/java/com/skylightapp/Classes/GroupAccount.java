package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
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

    public static final String CREATE_GRP_ACCT_TABLE = "CREATE TABLE IF NOT EXISTS " + GRP_ACCT_TABLE + " (" + GRPA_ID + " INTEGER, " + PROFILE_ID + " INTEGER , " +
            GRPA_TITTLE + " TEXT, " + GRPA_PURPOSE + " TEXT, "+ GRPA_SURNAME + " TEXT, " + GRPA_FIRSTNAME + " TEXT, " + GRPA_EMAIL + " TEXT, " + GRPA_PHONE + " TEXT, " +
            GRPA_START_DATE + " DATE, " + GRPA_END_DATE + " DATE, " + GRPA_BALANCE + " DOUBLE, "+ GRPA_STATUS + " TEXT, " +
            ISCOMPLETE + " BOOLEAN, " + HASCARRIED + " BOOLEAN, " + "PRIMARY KEY(" +GRPA_ID + "), " +"FOREIGN KEY(" + PROFILE_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";


    public static final String CREATE_GRP_ACCT_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS " + GRP_PROFILE_TABLE + " (" + GRPP_ID + " INTEGER, " + PROFILE_ID + " INTEGER , " +
            GRPA_ID + " INTEGER, " + GRPP_PIX + " TEXT, "+ GRPP_SURNAME + " TEXT, " + GRPP_FIRSTNAME + " TEXT, " + GRPP_JOINED_DATE + " TEXT, "+ GRPP_JOINED_STATUS + " TEXT, " + "PRIMARY KEY(" +GRPP_ID + "), " +"FOREIGN KEY(" + PROFILE_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + GRPA_ID  + ") REFERENCES " + GRP_ACCT_TABLE + "(" + GRPA_ID + "))";

    private ArrayList<Account> accounts;
    private ArrayList<Profile> profiles;
    private ArrayList<GroupSavings> groupSavings;
    private ArrayList<Transaction> transactions;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String emailAddress;
    private String country;
    private String status;
    private long grpAccountNo;
    private long profileID;
    private Date endDate;
    private Date startDate;
    private double accountBalance;
    private String purpose;
    private String tittle;
    private boolean hasCarried=false;
    private boolean isComplete=false;



    public GroupAccount (long grpAccountNo, String tittle, String purpose, String firstName, String lastName,String phoneNo,String emailAddress,Date startDate,double accountBalance,Date endDate,String status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.purpose = purpose;
        this.phoneNo = lastName;
        this.emailAddress = purpose;
        this.tittle = tittle;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
        this.accountBalance = accountBalance;
        this.grpAccountNo = grpAccountNo;
        accounts = new ArrayList<>();
        profiles = new ArrayList<>();

    }
    public GroupAccount (long grpAccountNo, boolean hasCarried, boolean isComplete) {
        this.grpAccountNo = grpAccountNo;
        this.hasCarried = hasCarried;
        this.isComplete = isComplete;
        accounts = new ArrayList<>();
        profiles = new ArrayList<>();

    }

    public GroupAccount() {

    }

    protected GroupAccount(Parcel in) {
        accounts = in.createTypedArrayList(Account.CREATOR);
        //profiles = in.createTypedArrayList(Profile.CREATOR);
        groupSavings = in.createTypedArrayList(GroupSavings.CREATOR);
        transactions = in.createTypedArrayList(Transaction.CREATOR);
        firstName = in.readString();
        lastName = in.readString();
        phoneNo = in.readString();
        emailAddress = in.readString();
        country = in.readString();
        status = in.readString();
        grpAccountNo = in.readLong();
        accountBalance = in.readDouble();
        purpose = in.readString();
        tittle = in.readString();
        hasCarried = in.readByte() != 0;
        isComplete = in.readByte() != 0;
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

    public GroupAccount(int grpSavingsNo, long profileID, String tittle, String purpose, String firstName,double totalAmount, Date dateOfSavings,  String status) {
        this.profileID = profileID;
        this.firstName = firstName;
        this.purpose = purpose;
        this.purpose = purpose;
        this.tittle = tittle;
        this.startDate = dateOfSavings;
        this.status = status;
        this.accountBalance = totalAmount;
        this.grpAccountNo = grpSavingsNo;
    }


    public void addGrpSavings(int grpSavingsNo, long profileID, double totalAmount, Date dateOfSavings, String status) {
        grpSavingsNo = groupSavings.size() + 1;
        GroupSavings groupSavings1 = new GroupSavings(grpSavingsNo,profileID, totalAmount, dateOfSavings, status);
        groupSavings.add(groupSavings1);

    }
    public void addGrpProfile(int profileNo, long profileID,  String status) {
        profileNo = profiles.size() + 1;
        //Profile profile = new Profile(profileNo,profileID,status);
        //profiles.add(profile);

    }
    public void addGrpSkyLightAcct(int acctNo, int acctID) {
        acctNo = accounts.size() + 1;
        Account account = new Account(acctNo,acctID);
        accounts.add(account);

    }
    public void addGrpTransactions(int grpSavingsNo, long profileID, double totalAmount, Date dateOfSavings, String status) {
        grpSavingsNo = groupSavings.size() + 1;
        GroupSavings groupSavings1 = new GroupSavings(grpSavingsNo,profileID, totalAmount, dateOfSavings, status);
        groupSavings.add(groupSavings1);

    }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate;
    }

    public String getTittle() {
        return tittle;
    }
    public void setTittle(String tittle) { this.tittle = tittle;
    }
    public String getGrpPhoneNo() {
        return phoneNo;
    }
    public void seGrpPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    public String getGrpEmailAddress() {
        return emailAddress;
    }
    public void setGrpEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) { this.firstName = firstName;
    }
    public long getProfileID() {
        return profileID;
    }
    public void setProfileID(long profileID) { this.profileID = profileID;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setCountry(String country) { this.country = country; }
    public String getCountry() {
        return country;
    }
    public void setGrpAccountNo(long grpAccountNo) { this.grpAccountNo = grpAccountNo; }
    public long getGrpAccountNo() {
        return grpAccountNo;
    }
    public double getGrpAcctBalance() {
        return accountBalance;
    }
    public void setGrpAcctBalance(double accountBalance) { this.accountBalance = accountBalance; }
    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    public boolean getHasCarried() {
        return hasCarried;
    }
    public void setHasCarried(boolean hasCarried) { this.hasCarried = hasCarried; }
    public boolean getIsComplete() {
        return isComplete;
    }
    public void setIsComplete(boolean isComplete) { this.isComplete = isComplete; }

    public ArrayList<Account> getAccounts() { return accounts; }
    public ArrayList<Profile> getProfiles() { return profiles; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(accounts);
        parcel.writeTypedList(profiles);
        parcel.writeTypedList(groupSavings);
        parcel.writeTypedList(transactions);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(phoneNo);
        parcel.writeString(emailAddress);
        parcel.writeString(country);
        parcel.writeString(status);
        parcel.writeLong(grpAccountNo);
        parcel.writeDouble(accountBalance);
        parcel.writeString(purpose);
        parcel.writeString(tittle);
        parcel.writeByte((byte) (hasCarried ? 1 : 0));
        parcel.writeByte((byte) (isComplete ? 1 : 0));
    }
}
