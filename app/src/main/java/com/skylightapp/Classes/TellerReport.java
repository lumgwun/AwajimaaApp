package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import static com.skylightapp.Classes.AdminUser.ADMIN_ID;
import static com.skylightapp.Classes.AdminUser.ADMIN_TABLE;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_ID;
import static com.skylightapp.Classes.CustomerManager.CUSTOMER_TELLER_TABLE;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;

@Entity(tableName = TellerReport.TELLER_REPORT_TABLE)
public class TellerReport  implements Serializable, Parcelable {
    public static final String TELLER_REPORT_TABLE = "TellerReportTable";
    public static final String TELLER_REPORT_ID = "teller_report_id";
    public static final String TELLER_REPORT_DATE = "T_R_Date";
    public static final String TELLER_REPORT_AMOUNT_SUBMITTED = "T_R_Amt_Submitted";
    public static final String TELLER_REPORT_NO_OF_CUS = "T_R_No_Of_Savings";
    public static final String TELLER_REPORT_BALANCE = "T_R_Balance";
    public static final String TELLER_REPORT_AMT_PAID = "T_R_Amt_Paid";
    public static final String TELLER_REPORT_EXPECTED_AMT = "T_R_Expected_Amt";

    public static final String TELLER_REPORT_STATUS = "T_R_Status";
    public static final String TELLER_REPORT_BRANCH = "T_R__Branch";
    public static final String TELLER_REPORT_ADMIN = "T_R__Manager";
    public static final String TELLER_REPORT_MARKETER = "T_R__Marketer";
    public static final String TELLER_REPORT_COMMENT = "T_R__Comment";
    public static final String TELLER_REPORT_PROF_ID = "T_R__ProfID";
    public static final String TELLER_REPORT_APPROVAL_DATE = "T_R_ApprovalDate";
    public static final String TELLER_REPORT_ADMIN_ID = "T_R_AdminID";
    public static final String TELLER_REPORT_OFFICE_ID = "T_R_OfficeID";
    public static final String TELLER_REPORT_TELLER_ID = "T_R_TellerCID";


    public static final String CREATE_TELLER_REPORT_TABLE = "CREATE TABLE IF NOT EXISTS " + TELLER_REPORT_TABLE + " (" + TELLER_REPORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TELLER_REPORT_OFFICE_ID + " INTEGER , " + TELLER_REPORT_ADMIN_ID + " INTEGER , " + CUSTOMER_TELLER_ID + " INTEGER , " +
            TELLER_REPORT_DATE + " TEXT, " + TELLER_REPORT_AMOUNT_SUBMITTED + " DOUBLE , " + TELLER_REPORT_NO_OF_CUS + " TEXT, " + TELLER_REPORT_BALANCE + " DOUBLE, " +
            TELLER_REPORT_AMT_PAID + " DOUBLE, " + TELLER_REPORT_EXPECTED_AMT + " DOUBLE, " + TELLER_REPORT_BRANCH + " DOUBLE, " +
            TELLER_REPORT_ADMIN + " TEXT, " + TELLER_REPORT_MARKETER + " TEXT, " + TELLER_REPORT_APPROVAL_DATE + " DATE," + TELLER_REPORT_STATUS + " TEXT," + "FOREIGN KEY(" + TELLER_REPORT_OFFICE_ID  + ") REFERENCES " + OFFICE_BRANCH_TABLE + "(" + OFFICE_BRANCH_ID + ")," +"FOREIGN KEY(" + TELLER_REPORT_TELLER_ID  + ") REFERENCES " + CUSTOMER_TELLER_TABLE + "(" + CUSTOMER_TELLER_ID + ")," +"FOREIGN KEY(" + TELLER_REPORT_ADMIN_ID + ") REFERENCES " + ADMIN_TABLE + "(" + ADMIN_ID + "))";



    @PrimaryKey(autoGenerate = true)
    private int tellerReportID=1022;
    private int dbaseID;
    private String tellerReportDate;
    private double amtSubmitted;
    private double amtPaid;
    private double amtExpected;
    private double balance;
    private String ReportStatus;
    private String report_Office_Branch;
    private String reportManager;
    private String reportMarketer;
    private String reportComments;
    private int noOfSavings;
    private Date approvalDate;
    private String adminName;
    private long superCode;

    public TellerReport(int reportID, int dbaseID, String date, String status) {
        this.tellerReportID = reportID;
        this.dbaseID = dbaseID;
        this.tellerReportDate = date;
        this.ReportStatus = status;
    }

    public TellerReport(int reportID, int dbaseID, String date, double balance, String status) {
        this.tellerReportID = reportID;
        this.dbaseID = dbaseID;
        this.tellerReportDate = date;
        this.balance = balance;
        this.ReportStatus = status;

    }

    public TellerReport(int keyExtraReportId, String officeBranch, double amountEntered, int noOfSavings, String reportDate) {
        this.tellerReportID = keyExtraReportId;
        this.amtSubmitted = amountEntered;
        this.tellerReportDate = reportDate;
        this.report_Office_Branch = officeBranch;
        this.noOfSavings = noOfSavings;
    }

    public TellerReport(int tellerReportId, String date, double amount, int noOfSaving, double balance, double amountPaid, double expectedAmount, String marketer, String status) {
        this.tellerReportID = tellerReportId;
        this.amtSubmitted = amount;
        this.tellerReportDate = date;
        this.balance = balance;
        this.ReportStatus = status;
        this.noOfSavings = noOfSaving;
        this.amtPaid = amountPaid;
        this.amtExpected = expectedAmount;
        this.reportMarketer = marketer;
    }
    public TellerReport(int tellerReportId,  double amountPaid, String marketer) {
        this.tellerReportID = tellerReportId;
        this.amtPaid = amountPaid;
        this.reportMarketer = marketer;
    }


    protected TellerReport(Parcel in) {
        tellerReportID = in.readInt();
        dbaseID = in.readInt();
        amtSubmitted = in.readDouble();
        amtPaid = in.readDouble();
        amtExpected = in.readDouble();
        balance = in.readDouble();
        ReportStatus = in.readString();
        report_Office_Branch = in.readString();
        reportManager = in.readString();
        reportMarketer = in.readString();
        reportComments = in.readString();
        noOfSavings = in.readInt();
        adminName = in.readString();
        superCode = in.readLong();
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

    public int getDbaseID() { return dbaseID; }
    public void setDbaseID(int dbaseID) {
        this.dbaseID = dbaseID;
    }

    public int getNoOfSavings() { return noOfSavings; }
    public void setNoOfSavings(int noOfSavings) {
        this.noOfSavings = noOfSavings;
    }


    public String getTellerReportDate() { return tellerReportDate; }
    public void setTellerReportDate(String tellerReportDate) {
        this.tellerReportDate = tellerReportDate;
    }

    public double getAmtSubmitted() {
        return amtSubmitted;
    }
    public void setAmtSubmitted(double amtSubmitted) {
        this.amtSubmitted = amtSubmitted;
    }


    public double getAmtPaid() {
        return amtPaid;
    }
    public void setAmtPaid(double amtPaid) {
        this.amtPaid = amtPaid;
    }


    public double getAmtExpected() { return amtExpected; }
    public void setAmtExpected(double amtExpected) {
        this.amtExpected = amtExpected;
    }



    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }


    public String getReportComments() {
        return reportComments;
    }
    public void setReportComments(String reportComments) {
        this.reportComments = reportComments;
    }




    public String getReportStatus() { return ReportStatus; }
    public void setReportStatus(String reportStatus) {
        this.ReportStatus = reportStatus;
    }


    public String getReport_Office_Branch() {
        return report_Office_Branch;
    }
    public void setReport_Office_Branch(String report_Office_Branch) {
        this.report_Office_Branch = report_Office_Branch;
    }


    public String getReportManager() {
        return reportManager;
    }
    public void setReportManager(String reportManager) {
        this.reportManager = reportManager;
    }



    public String getReportMarketer() { return reportMarketer; }
    public void setReportMarketer(String reportMarketer) {
        this.reportMarketer = reportMarketer;
    }




    public Date getTxApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }


    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }



    public void setSuperCode(long superCode) {
        this.superCode = superCode;
    }

    public long getSuperCode() {
        return superCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(tellerReportID);
        parcel.writeInt(dbaseID);
        parcel.writeDouble(amtSubmitted);
        parcel.writeDouble(amtPaid);
        parcel.writeDouble(amtExpected);
        parcel.writeDouble(balance);
        parcel.writeString(ReportStatus);
        parcel.writeString(report_Office_Branch);
        parcel.writeString(reportManager);
        parcel.writeString(reportMarketer);
        parcel.writeString(reportComments);
        parcel.writeInt(noOfSavings);
        parcel.writeString(adminName);
        parcel.writeLong(superCode);
    }
}
