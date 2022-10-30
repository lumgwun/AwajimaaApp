package com.skylightapp.Classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;


public class ProjectSavingsGroup implements Parcelable, Serializable {
    public static final String PROJECT_SAVINGS_GROUP_TABLE = "savings_GrpTable";
    public static final String PROJECT_SG_ID = "sgt_id";

    public static final String PROJECT_SG_NAME = "st_Name";
    public static final String PROJECT_SG_PURPOSE = "stPurpose";
    public static final String PROJECT_SG_AMOUNT = "st_amount";
    public static final String PROJECT_SG_DATE = "st_Date";
    public static final String PROJECT_SG_CURRENCY = "st_Currency";
    public static final String PROJECT_SG_COUNTRY = "st_Country";
    public static final String PROJECT_SG_ACCOUNT = "Gst_Account";
    public static final String PSG_WHO_SHOULDBE_PAID = "sWho_should_";
    public static final String PSG_END_DATE = "st_End_Date";
    public static final String PSG_AMOUNT_CONTRIBUTED = "st_Amount_So_Far";

    public static final String PSG_DOC = "st_Doc";
    public static final String PSG_PROFID = "st_Pid";
    public static final String PSG_CUSID = "st_Cid";


    public static final String CREATE_PROJECT_SAVINGS_GROUP_TABLE = "CREATE TABLE IF NOT EXISTS " + PROJECT_SAVINGS_GROUP_TABLE + " (" + PSG_PROFID + " INTEGER , " + PSG_CUSID + " INTEGER , " +
            PROJECT_SG_NAME + " TEXT, " + PROJECT_SG_PURPOSE + " TEXT, " + PROJECT_SG_AMOUNT + " REAL, " + PROJECT_SG_CURRENCY +" TEXT, "+ PROJECT_SG_ACCOUNT + " REAL, "  + PROJECT_SG_COUNTRY + " TEXT, " + PSG_AMOUNT_CONTRIBUTED + " REAL,  " + PSG_WHO_SHOULDBE_PAID + " TEXT," + PSG_DOC + " TEXT, " + PSG_END_DATE + " TEXT, " + PROJECT_SG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +"FOREIGN KEY(" + PSG_PROFID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";


    private int projectSavingsGrpID;
    private String projectSavingsGrpName;
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

    public ProjectSavingsGroup(int projectSavingsGrpID, String projectSavingsGrpName, String adminName, String purpose, double amount, Date startDate, Date endDate, String status) {
        this.projectSavingsGrpID = projectSavingsGrpID;
        this.projectSavingsGrpName = projectSavingsGrpName;
        this.adminName = adminName;
        this.groupPurpose = purpose;
        this.amountOfGS = amount;
        this.dateOfGS = startDate;
        this.gsEndDate = endDate;
        this.status = status;
    }

    public static final Creator<ProjectSavingsGroup> CREATOR = new Creator<ProjectSavingsGroup>() {
        @Override
        public ProjectSavingsGroup createFromParcel(Parcel in) {
            return new ProjectSavingsGroup(in);
        }

        @Override
        public ProjectSavingsGroup[] newArray(int size) {
            return new ProjectSavingsGroup[size];
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


    public ProjectSavingsGroup(Parcel in) {
        projectSavingsGrpID = in.readInt();
        projectSavingsGrpName = in.readString();
        amountOfGS = in.readDouble();
        groupCurrency = in.readString();
        paymentDoc = in.readParcelable(Uri.class.getClassLoader());
        groupPurpose = in.readString();
        gsAmountContributed = in.readDouble();
        grpCountry = in.readString();
    }



    public ProjectSavingsGroup() {

    }

    public void setProjectSavingsGrpID(int projectSavingsGrpID) {
        this.projectSavingsGrpID = projectSavingsGrpID;
    }


    public int getProjectSavingsGrpID() {
        return projectSavingsGrpID;
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


    public void setProjectSavingsGrpName(String projectSavingsGrpName) {
        this.projectSavingsGrpName = projectSavingsGrpName;
    }


    public String getProjectSavingsGrpName() {
        return projectSavingsGrpName;
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
        parcel.writeInt(projectSavingsGrpID);
        parcel.writeString(projectSavingsGrpName);
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
