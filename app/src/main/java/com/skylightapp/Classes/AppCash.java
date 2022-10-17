package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;

@Entity(tableName = AppCash.APP_CASH_TABLE)
public class AppCash implements Serializable, Parcelable {
    public static final String APP_CASH_TABLE = "s_cash_table";
    public static final String APP_CASH_ID = "sc_id";
    public static final String APP_CASH_DATE = "s_cash_date";
    public static final String APP_CASH_AMOUNT = "s_cash_amount";
    public static final String APP_CASH_PAYER = "s_cash_Payer";
    public static final String APP_CASH_PAYEE = "s_cash_Payee";
    public static final String S_CASH_ADMIN = "s_cash_admin";
    public static final String APP_CASH_STATUS = "s_cash_status";
    public static final String APP_CASH_CODE = "s_cash_code";
    public static final String APP_CASH_FROM = "s_cash_from";
    public static final String APP_CASH_TO = "s_cash_to";
    public static final String S_CASH_OFFICE_ID = "s_cash_office_ID";
    public static final String APP_CASH_ADMIN_ID = "s_cash_Admin_ID";
    public static final String APP_CASH_PROFILE_ID = "s_cash_Profile_ID";
    public static final String APP_CASH_DB_ID = "s_cash_DB_ID";



    public static final String CREATE_SKYLIGHT_CASH_TABLE = "CREATE TABLE " + APP_CASH_TABLE + " (" + APP_CASH_ID + " INTEGER , " + S_CASH_OFFICE_ID + " TEXT, " + APP_CASH_ADMIN_ID + " TEXT, " + APP_CASH_PROFILE_ID + " TEXT, " + APP_CASH_DATE + " TEXT, " + APP_CASH_AMOUNT + " REAL, " + APP_CASH_PAYER + " TEXT, " +
            APP_CASH_PAYEE + " TEXT, " + S_CASH_ADMIN + " TEXT, " + APP_CASH_CODE + " REAL, " + APP_CASH_STATUS + " TEXT, " + APP_CASH_FROM + " TEXT, " + APP_CASH_TO + " TEXT, "+ APP_CASH_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "  + "FOREIGN KEY(" + S_CASH_OFFICE_ID + ") REFERENCES " + OFFICE_BRANCH_TABLE + "(" + OFFICE_BRANCH_ID + "))";



   /* public static final String CREATE_TELLER_CASH_TABLE = "CREATE TABLE IF NOT EXISTS " + S_CASH_TABLE + " (" + S_CASH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + S_CASH_OFFICE_ID + " INTEGER , " + S_CASH_ADMIN_ID + " INTEGER , " + S_CASH_PROFILE_ID + " INTEGER , " + S_CASH_DATE + " TEXT, " + S_CASH_AMOUNT + " FLOAT, " + S_CASH_PAYER + " TEXT, " + S_CASH_PAYEE + " TEXT, " +
            S_CASH_ADMIN + " TEXT, "  + S_CASH_CODE + " TEXT," + S_CASH_STATUS + " TEXT, " + S_CASH_FROM + " TEXT, " + S_CASH_TO + " TEXT," +"FOREIGN KEY(" + S_CASH_OFFICE_ID + ") REFERENCES " + OFFICE_BRANCH_TABLE + "(" + OFFICE_BRANCH_ID + ")," +"FOREIGN KEY(" + S_CASH_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + S_CASH_ADMIN_ID + ") REFERENCES " + ADMIN_TABLE + "(" + ADMIN_ID + "))";*/

    //private long skylightCashID;
    @PrimaryKey(autoGenerate = true)
    private int skylightCashID=1012;
    private String skylightCashDate;
    private double skylightCashAmount;
    private String skylightCashStatus;
    private long skylightCashCode;
    private String sCAdminName;
    private String sCPayer;
    private String sC_Payee;
    private String sC_From;
    private String sC_To;
    private int sc_AdminID;
    private int sc_ProfileID;
    private ArrayList<TellerReport> tellerReportArrayList;
    private ArrayList<Payment> paymentArrayList= null;

    public AppCash(int skylightCashID, String date, double amount, String payer, String payee, String from, String to, String status) {
        this.skylightCashID = skylightCashID;
        this.sCPayer = payer;
        this.sC_Payee = payee;
        this.skylightCashDate = date;
        this.skylightCashAmount = amount;
        this.sC_From = from;
        this.sC_To = to;
        this.skylightCashStatus = status;
    }

    public AppCash(int skylightCashID, String date, double amount, String payer, String payee, long code, String from, String to, String status) {
        this.skylightCashID = skylightCashID;
        this.sCPayer = payer;
        this.sC_Payee = payee;
        this.skylightCashDate = date;
        this.skylightCashAmount = amount;
        this.sC_From = from;
        this.sC_To = to;
        this.skylightCashCode = code;
        this.skylightCashStatus = status;
    }


    public void addSC_TellerManualPayment(String type, double totalToWithdraw, Date date, long paymentCode, String acctType, String office, String status) {
        if (paymentArrayList != null) {
            String paymentNo = "Payment:" + (paymentArrayList.size() + 1);
        }
        Payment payment = new Payment(type,totalToWithdraw, date,paymentCode,acctType,office,status);
        paymentArrayList.add(payment);

    }

    /*public void addTellerReport(int count,long customerID,String customerName,long packageID, long reportID, double savingsAmount, int numberOfDays, double totalAmountSum, int daysRemaining, double amountRemaining, String reportDate, String status) {
        count = tellerReportArrayList.size() + 1;
        CustomerDailyReport dailyReport = new CustomerDailyReport( count,customerID,customerName,packageID, reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining,  reportDate, status);
        tellerReportArrayList.add(dailyReport);
    }*/

    public AppCash() {

    }

    public AppCash(int tellerCashID, int adminID, int profileID, String date, double amount, String tellerName, String office, String adminName, long code, String status) {
        this.skylightCashID = tellerCashID;
        this.sc_AdminID = adminID;
        this.sc_ProfileID = profileID;
        this.skylightCashDate = date;
        this.skylightCashAmount = amount;
        this.sC_Payee = office;
        this.sCPayer = tellerName;
        this.sCAdminName = adminName;
        this.skylightCashCode = code;
        this.skylightCashStatus = status;

    }
    public AppCash(int tellerCashID, String date, double amount, String tellerName, String office, String adminName, long code, String status) {
        this.skylightCashID = tellerCashID;
        this.skylightCashDate = date;
        this.skylightCashAmount = amount;
        this.sCPayer = tellerName;
        this.sC_Payee = office;
        this.sCAdminName = adminName;
        this.skylightCashCode = code;
        this.skylightCashStatus = status;

    }


    protected AppCash(Parcel in) {
        skylightCashID = in.readInt();
        skylightCashAmount = in.readDouble();
        skylightCashStatus = in.readString();
        skylightCashCode = in.readLong();
        sCAdminName = in.readString();
        sCPayer = in.readString();
        sC_Payee = in.readString();
        sc_AdminID = in.readInt();
        sc_ProfileID = in.readInt();
    }

    public static final Creator<AppCash> CREATOR = new Creator<AppCash>() {
        @Override
        public AppCash createFromParcel(Parcel in) {
            return new AppCash(in);
        }

        @Override
        public AppCash[] newArray(int size) {
            return new AppCash[size];
        }
    };
    public String getSkylightCashFrom() { return sC_From; }
    public void setSkylightCashFrom(String sC_From) {
        this.sC_From = sC_From;
    }
    public String getSkylightCashTo() { return sC_To; }
    public void setSkylightCashTo(String sC_To) {
        this.sC_To = sC_To;
    }

    public int getSkylightCashID() { return skylightCashID; }
    public String getSkylightCashDate() { return skylightCashDate; }
    public double getSkylightCashAmount() {
        return skylightCashAmount;
    }
    public int getSCash_AdminID() {
        return sc_AdminID;
    }
    public int getSCash_ProfileID() { return sc_ProfileID; }
    public String getSkylightCashStatus() { return skylightCashStatus; }
    public String getSCPayee() {
        return sC_Payee;
    }
    public String getSCPayer() { return sCPayer; }
    public void setSkylightCashStatus(String skylightCashStatus) {
        this.skylightCashStatus = skylightCashStatus;
    }
    public void setSCPayee(String sC_Payee) {
        this.sC_Payee = sC_Payee;
    }
    public void setSCPayer(String sCPayer) {
        this.sCPayer = sCPayer;
    }

    public void setSkylightCashID(int skylightCashID) {
        this.skylightCashID = skylightCashID;
    }
    public void setSkylightCashDate(String skylightCashDate) {
        this.skylightCashDate = skylightCashDate;
    }
    public void setSkylightCashAmount(double skylightCashAmount) {
        this.skylightCashAmount = skylightCashAmount;
    }
    public void setSCash_AdminID(int tC_AdminID) {
        this.sc_AdminID = tC_AdminID;
    }
    public void setSCash_ProfileID(int tc_ProfileID) {
        this.sc_ProfileID = tc_ProfileID;
    }

    public String getSCashAdminName() {
        return sCAdminName;
    }

    public void setSCashAdminName(String tCAdminName) {
        this.sCAdminName = tCAdminName;
    }

    public void setSkylightCashCode(long skylightCashCode) {
        this.skylightCashCode = skylightCashCode;
    }

    public long getSkylightCashCode() {
        return skylightCashCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(skylightCashID);
        parcel.writeDouble(skylightCashAmount);
        parcel.writeString(skylightCashStatus);
        parcel.writeLong(skylightCashCode);
        parcel.writeString(sCAdminName);
        parcel.writeString(sCPayer);
        parcel.writeString(sC_Payee);
        parcel.writeLong(sc_AdminID);
        parcel.writeLong(sc_ProfileID);
    }
}
