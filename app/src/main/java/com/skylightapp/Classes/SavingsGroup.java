package com.skylightapp.Classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;


public class SavingsGroup implements Parcelable, Serializable {
    public static final String SAVINGS_GROUP_TABLE = "savings_GrpTable";
    public static final String SGT_ID = "sgt_id";

    public static final String SGT__NAME = "st_Name";
    public static final String SGT_PURPOSE = "stPurpose";
    public static final String ST_AMOUNT = "st_amount";
    public static final String ST_DATE = "st_Date";
    public static final String ST_CURRENCY = "st_Currency";
    public static final String ST_COUNTRY = "st_Country";
    public static final String GST_ACCOUNT = "Gst_Account";

    public static final String SGT_WHO_SHOULDBE_PAID = "sWho_should_";
    public static final String ST_END_DATE = "st_End_Date";
    public static final String SGT_AMOUNT_CONTRIBUTED = "st_Amount_So_Far";

    //public static final String GS_FINE = "Fine";
    public static final String ST_DOC = "st_Doc";
    public static final String ST_PROFID = "st_Pid";
    public static final String ST_CUSID = "st_Cid";


    public static final String CREATE_SAVINGS_GROUP_TABLE = "CREATE TABLE IF NOT EXISTS " + SAVINGS_GROUP_TABLE + " (" + ST_PROFID + " INTEGER , " + ST_CUSID + " INTEGER , " +
             SGT__NAME + " TEXT, " + SGT_PURPOSE + " TEXT, " + ST_AMOUNT + " REAL, " + ST_CURRENCY +" TEXT, "+ GST_ACCOUNT + " REAL, "  + ST_COUNTRY + " TEXT, " + SGT_AMOUNT_CONTRIBUTED + " REAL,  " + SGT_WHO_SHOULDBE_PAID + " TEXT," + ST_DOC + " TEXT, " + ST_END_DATE + " TEXT, " + SGT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +"FOREIGN KEY(" + ST_PROFID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";


    private int gsID;
    private String groupName;
    private Date dateOfGS;
    private double amountOfGS;
    private int gsDuration;
    private String groupCurrency;
    private String grpCountry;
    private Uri paymentDoc;
    private String groupPurpose;
    private Date gsEndDate;
    private String adminName;
    private String status;
    private double gsAmountContributed;
    private String whoshouldBePaid;
    private ArrayList<Profile> profiles;
    private UserSuperAdmin superAdmin;
    private ArrayList<Transaction> transactions;
    private GroupAccount groupAccount;
    private ArrayList<TimeLine> timeLines;
    private Payee payee;
    private ArrayList<Payee> payees;
    private double fine;

    public SavingsGroup(int gsID, String groupName, String adminName, String purpose, double amount, Date startDate, Date endDate,String status) {
        this.gsID = gsID;
        this.groupName = groupName;
        this.adminName = adminName;
        this.groupPurpose = purpose;
        this.amountOfGS = amount;
        this.dateOfGS = startDate;
        this.gsEndDate = endDate;
        this.status = status;
    }

    public static final Creator<SavingsGroup> CREATOR = new Creator<SavingsGroup>() {
        @Override
        public SavingsGroup createFromParcel(Parcel in) {
            return new SavingsGroup(in);
        }

        @Override
        public SavingsGroup[] newArray(int size) {
            return new SavingsGroup[size];
        }
    };

    public void addProfile(int profileID, String name, String phoneNo, String email, String identity, String userType ) {
        String profileNo = "A" + (profiles.size() + 1);
        Profile profile = new Profile(profileID,name,phoneNo, email, identity,userType);
        profiles.add(profile);
    }

    /*public void addTransaction(long transactionID, Double amountOfGS, String names, String type, String methodOfPay,String date,String status) {
        String TxNo = "A" + (transactions.size() + 1);
        Transaction transaction = new Transaction(transactionID,amountOfGS,names, type, methodOfPay,date,status);
        transactions.add(transaction);
    }*/
    public void addTimeline(long timelineID, String tittle, String details,String timestamp) {
        String timelineNo = "A" + (timeLines.size() + 1);
        //TimeLine timeLine = new TimeLine(timelineID,tittle, details,timestamp);
        //timeLines.add(timeLine);
    }
    public void addPayee(long payeeID,String phoneNo, String email,String bankName,String name,long bankAcctNo,String need,String status) {
        String payeeNo = "Payee" + (payees.size() + 1);
        //Payee payee = new Payee(payeeID,phoneNo,email,bankName,name, bankAcctNo, need,status);
        //payees.add(payee);
    }


    public SavingsGroup(Parcel in) {
        gsID = in.readInt();
        groupName = in.readString();
        amountOfGS = in.readDouble();
        groupCurrency = in.readString();
        paymentDoc = in.readParcelable(Uri.class.getClassLoader());
        groupPurpose = in.readString();
        gsAmountContributed = in.readDouble();
        grpCountry = in.readString();
    }



    public SavingsGroup() {

    }

    public void setGsID(int gsID) {
        this.gsID = gsID;
    }


    public int getGsID() {
        return gsID;
    }
    public Date getGsEndDate() {
        return gsEndDate;
    }

    public void setGsEndDate(Date gsEndDate) {
        this.gsEndDate = gsEndDate;
    }

    public double getGsAmountContributed() {
        return gsAmountContributed;
    }

    public void setGsAmountContributed(double gsAmountContributed) {
        this.gsAmountContributed = gsAmountContributed;
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setDateOfGS(Date dateOfGS) {
        this.dateOfGS = dateOfGS;
    }


    public Date getDateOfGS() {
        return dateOfGS;
    }

    public void setGroupCurrency(String groupCurrency) {
        this.groupCurrency = groupCurrency;
    }


    public String getGroupCurrency() {
        return groupCurrency;
    }

    public void setPaymentDoc(Uri paymentDoc) {
        this.paymentDoc = paymentDoc;
    }


    public Uri getPaymentDoc() {
        return paymentDoc;
    }

    public void setGroupPurpose(String groupPurpose) {
        this.groupPurpose = groupPurpose;
    }


    public String getGroupPurpose() {
        return groupPurpose;
    }


    public Double getFine() {
        return fine;
    }



    public void setFine(Double fine) {
        this.fine = fine;
    }
    public Double getAmountOfGS() {
        return amountOfGS;
    }



    public void setAmountOfGS(Double amountOfGS) {
        this.amountOfGS = amountOfGS;
    }


    public String getWhoshouldBePaid() {
        return whoshouldBePaid;
    }


    public void setWhoshouldBePaid(String whoshouldBePaid) {
        this.whoshouldBePaid = whoshouldBePaid;
    }
    public double getGSDuration() {
        return gsDuration;
    }


    public void setGsDuration(int gsDuration) {
        this.gsDuration = gsDuration;
    }


    public String getGrpCountry() {
        return grpCountry;
    }


    public void setGrpCountry(String Country) {
        this.grpCountry = Country;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(gsID);
        parcel.writeString(groupName);
        parcel.writeDouble(amountOfGS);
        parcel.writeString(groupCurrency);
        parcel.writeParcelable(paymentDoc, i);
        parcel.writeString(groupPurpose);
        parcel.writeDouble(gsAmountContributed);
        parcel.writeString(whoshouldBePaid);
        parcel.writeString(grpCountry);
    }

    public enum INVESTMENT_TYPE {Gold, Diamond, SILVER}
}
