package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;


import java.io.Serializable;

import static com.skylightapp.Classes.AdminUser.ADMIN_ID;
import static com.skylightapp.Classes.AdminUser.ADMIN_TABLE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

//@Entity(tableName = TellerReport.TELLER_REPORT_TABLE)
public class TellerReport  implements Serializable, Parcelable {
    public static final String TELLER_REPORT_TABLE = "tellerReportTable";
    public static final String TELLER_REPORT_ID = "teller_report_id";
    public static final String TELLER_REPORT_DATE = "t_R_Date";
    public static final String TELLER_REPORT_AMOUNT_SUBMITTED = "t_R_Amt_Submitted";
    public static final String TELLER_REPORT_NO_OF_SAVINGS = "t_R_No_Of_Savings";
    public static final String TELLER_REPORT_BALANCE = "t_R_Balance";
    public static final String TELLER_REPORT_AMT_PAID = "t_R_Amt_Paid";
    public static final String TELLER_REPORT_EXPECTED_AMT = "t_R_Expected_Amt";

    public static final String TELLER_REPORT_STATUS = "t_R_Status";
    public static final String TELLER_REPORT_BRANCH = "t_R__Branch";
    public static final String TELLER_REPORT_ADMIN = "t_R__Manager";
    public static final String TELLER_REPORT_MARKETER = "t_R__Marketer";
    public static final String TELLER_REPORT_COMMENT = "t_R__Comment";
    public static final String TELLER_REPORT_PROF_ID = "t_R__ProfID";
    public static final String TELLER_REPORT_APPROVAL_DATE = "t_R_ApprovalDate";
    public static final String TELLER_REPORT_ADMIN_ID = "t_R_AdminID";
    public static final String TELLER_REPORT_OFFICE_ID = "t_R_OfficeID";
    public static final String TELLER_REPORT_NO_OF_CUS = "t_R_No_Of_Cus";
    public static final String TELLER_REPORT_BIZ_NAME = "t_R_Biz_Name";


    public static final String CREATE_TELLER_REPORT_TABLE = "CREATE TABLE IF NOT EXISTS " + TELLER_REPORT_TABLE + " (" + TELLER_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TELLER_REPORT_OFFICE_ID + " INTEGER , " + TELLER_REPORT_ADMIN_ID + " INTEGER , " + TELLER_REPORT_PROF_ID + " INTEGER , " +
            TELLER_REPORT_DATE + " TEXT, " + TELLER_REPORT_AMOUNT_SUBMITTED + " REAL , " + TELLER_REPORT_NO_OF_SAVINGS + " TEXT, " + TELLER_REPORT_BALANCE + " REAL, " +
            TELLER_REPORT_AMT_PAID + " REAL, " + TELLER_REPORT_EXPECTED_AMT + " REAL, " + TELLER_REPORT_BRANCH + " REAL, " +
            TELLER_REPORT_ADMIN + " TEXT, " + TELLER_REPORT_MARKETER + " TEXT, " + TELLER_REPORT_APPROVAL_DATE + " TEXT," + TELLER_REPORT_STATUS + " TEXT,"+ TELLER_REPORT_NO_OF_CUS + " TEXT,"+ TELLER_REPORT_BIZ_NAME + " TEXT," + "FOREIGN KEY(" + TELLER_REPORT_OFFICE_ID  + ") REFERENCES " + OFFICE_BRANCH_TABLE + "(" + OFFICE_BRANCH_ID + ")," +"FOREIGN KEY(" + TELLER_REPORT_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + TELLER_REPORT_ADMIN_ID + ") REFERENCES " + ADMIN_TABLE + "(" + ADMIN_ID + "))";



    //@PrimaryKey(autoGenerate = true)
    private int tellerReportID=1022;
    private int tr_DbaseID;
    private String tellerReportDate;
    private double tr_AmtSubmitted;
    private double tr_AmtPaid;
    private double tr_AmtExpected;
    private double tr_Balance;
    private String trStatus;
    private String tr_Office_Branch;
    private String trManager;
    private String trMarketer;
    private String trComments;
    private int trNoOfSavings;
    private int trNoOfCus;
    private String tr_ApprovalDate;
    private String tr_AdminName;
    private String tr_BizName;
    private long tr_SuperCode;

    public TellerReport(int reportID, int tr_DbaseID, String date, String status) {
        this.tellerReportID = reportID;
        this.tr_DbaseID = tr_DbaseID;
        this.tellerReportDate = date;
        this.trStatus = status;
    }

    public TellerReport(int reportID, int tr_DbaseID, String date, double tr_Balance, String status) {
        this.tellerReportID = reportID;
        this.tr_DbaseID = tr_DbaseID;
        this.tellerReportDate = date;
        this.tr_Balance = tr_Balance;
        this.trStatus = status;

    }

    public TellerReport(int keyExtraReportId, long bizName, String officeBranch, double amountEntered, int trNoOfSavings, String reportDate) {
        this.tellerReportID = keyExtraReportId;
        this.tr_AmtSubmitted = amountEntered;
        this.tellerReportDate = reportDate;
        this.tr_Office_Branch = officeBranch;
        this.trNoOfSavings = trNoOfSavings;
    }

    public TellerReport(int tellerReportId, String date, double amount, int noOfSaving, double tr_Balance, double amountPaid, double expectedAmount, String marketer, String status) {
        this.tellerReportID = tellerReportId;
        this.tr_AmtSubmitted = amount;
        this.tellerReportDate = date;
        this.tr_Balance = tr_Balance;
        this.trStatus = status;
        this.trNoOfSavings = noOfSaving;
        this.tr_AmtPaid = amountPaid;
        this.tr_AmtExpected = expectedAmount;
        this.trMarketer = marketer;
    }
    public TellerReport(int keyExtraReportId, String bizName, String officeBranch, double amountEntered, int noOfCustomers, String dateOfReport) {
        this.tellerReportID = keyExtraReportId;
        this.tr_BizName = bizName;
        this.tr_Office_Branch = officeBranch;
        this.tr_AmtSubmitted = amountEntered;
        this.trNoOfSavings = noOfCustomers;
        this.tellerReportDate = dateOfReport;

    }
    public TellerReport(int tellerReportId,  double amountPaid, String marketer) {
        this.tellerReportID = tellerReportId;
        this.tr_AmtPaid = amountPaid;
        this.trMarketer = marketer;
    }


    protected TellerReport(Parcel in) {
        tellerReportID = in.readInt();
        tr_DbaseID = in.readInt();
        tr_AmtSubmitted = in.readDouble();
        tr_AmtPaid = in.readDouble();
        tr_AmtExpected = in.readDouble();
        tr_Balance = in.readDouble();
        trStatus = in.readString();
        tr_Office_Branch = in.readString();
        trManager = in.readString();
        trMarketer = in.readString();
        trComments = in.readString();
        trNoOfSavings = in.readInt();
        tr_AdminName = in.readString();
        tr_SuperCode = in.readLong();
    }

    public static final Creator<TellerReport> CREATOR = new Creator<TellerReport>() {
        @Override
        public TellerReport createFromParcel(Parcel in) {
            return new TellerReport(in);
        }

        @Override
        public TellerReport[] newArray(int size) {
            return new TellerReport[size];
        }
    };

    public TellerReport() {
        super();
    }



    public int getTellerReportID() { return tellerReportID; }
    public void setTellerReportID(int tellerReportID) {
        this.tellerReportID = tellerReportID;
    }

    public int getTr_DbaseID() { return tr_DbaseID; }
    public void setTr_DbaseID(int tr_DbaseID) {
        this.tr_DbaseID = tr_DbaseID;
    }

    public int getTrNoOfSavings() { return trNoOfSavings; }
    public void setTrNoOfSavings(int trNoOfSavings) {
        this.trNoOfSavings = trNoOfSavings;
    }


    public String getTellerReportDate() { return tellerReportDate; }
    public void setTellerReportDate(String tellerReportDate) {
        this.tellerReportDate = tellerReportDate;
    }

    public double getTr_AmtSubmitted() {
        return tr_AmtSubmitted;
    }
    public void setTr_AmtSubmitted(double tr_AmtSubmitted) {
        this.tr_AmtSubmitted = tr_AmtSubmitted;
    }


    public double getTr_AmtPaid() {
        return tr_AmtPaid;
    }
    public void setTr_AmtPaid(double tr_AmtPaid) {
        this.tr_AmtPaid = tr_AmtPaid;
    }


    public double getTr_AmtExpected() { return tr_AmtExpected; }
    public void setTr_AmtExpected(double tr_AmtExpected) {
        this.tr_AmtExpected = tr_AmtExpected;
    }



    public double getTr_Balance() {
        return tr_Balance;
    }
    public void setTr_Balance(double tr_Balance) {
        this.tr_Balance = tr_Balance;
    }


    public String getTrComments() {
        return trComments;
    }
    public void setTrComments(String trComments) {
        this.trComments = trComments;
    }




    public String getTrStatus() { return trStatus; }
    public void setTrStatus(String trStatus) {
        this.trStatus = trStatus;
    }


    public String getTr_Office_Branch() {
        return tr_Office_Branch;
    }
    public void setTr_Office_Branch(String tr_Office_Branch) {
        this.tr_Office_Branch = tr_Office_Branch;
    }


    public String getTrManager() {
        return trManager;
    }
    public void setTrManager(String trManager) {
        this.trManager = trManager;
    }



    public String getTrMarketer() { return trMarketer; }
    public void setTrMarketer(String trMarketer) {
        this.trMarketer = trMarketer;
    }




    public String getTxApprovalDate() {
        return tr_ApprovalDate;
    }

    public void setTr_ApprovalDate(String tr_ApprovalDate) {
        this.tr_ApprovalDate = tr_ApprovalDate;
    }

    public String getTr_AdminName() {
        return tr_AdminName;
    }

    public void setTr_AdminName(String tr_AdminName) {
        this.tr_AdminName = tr_AdminName;
    }



    public void setTr_SuperCode(long tr_SuperCode) {
        this.tr_SuperCode = tr_SuperCode;
    }

    public long getTr_SuperCode() {
        return tr_SuperCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(tellerReportID);
        parcel.writeInt(tr_DbaseID);
        parcel.writeDouble(tr_AmtSubmitted);
        parcel.writeDouble(tr_AmtPaid);
        parcel.writeDouble(tr_AmtExpected);
        parcel.writeDouble(tr_Balance);
        parcel.writeString(trStatus);
        parcel.writeString(tr_Office_Branch);
        parcel.writeString(trManager);
        parcel.writeString(trMarketer);
        parcel.writeString(trComments);
        parcel.writeInt(trNoOfSavings);
        parcel.writeString(tr_AdminName);
        parcel.writeLong(tr_SuperCode);
    }

    public int getTrNoOfCus() {
        return trNoOfCus;
    }

    public void setTrNoOfCus(int trNoOfCus) {
        this.trNoOfCus = trNoOfCus;
    }

    public String getTr_BizName() {
        return tr_BizName;
    }

    public void setTr_BizName(String tr_BizName) {
        this.tr_BizName = tr_BizName;
    }
}
