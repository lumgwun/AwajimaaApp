package com.skylightapp.Tellers;

import android.os.Parcel;
import android.os.Parcelable;



import com.skylightapp.Classes.Profile;

import java.io.Serializable;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

//@Entity(tableName = TellerCash.TELLER_CASH_TABLE)
public class TellerCash implements Serializable, Parcelable {
    public static final String TELLER_CASH_TABLE = "tc_Table";
    public static final String TELLER_CASH_ID = "teller_cash_id";
    public static final String TELLER_CASH_AMOUNT = "tc_Amount";
    public static final String TELLER_CASH_DATE = "tc_Date";
    public static final String TELLER_CASH_CODE = "tc_Code";
    public static final String TELLER_CASH_PACKAGE_ID = "tc_package_id";
    public static final String TELLER_CASH_PROFILE_ID = "tc_profile_id";
    public static final String TELLER_CASH_STATUS = "tc_status";
    public static final String TELLER_CASH_BRANCH = "tc_Branch";
    public static final String TELLER_CASH_ITEM_NAME = "tc_item";
    public static final String TELLER_CASH_TELLER_NAME = "tc_teller";

    public static final String CREATE_TELLER_CASH_TABLE = "CREATE TABLE IF NOT EXISTS " + TELLER_CASH_TABLE + " (" + TELLER_CASH_ID + " INTEGER PRIMARY KEY, " + TELLER_CASH_PROFILE_ID + " INTEGER , " + TELLER_CASH_ITEM_NAME + " TEXT, " + TELLER_CASH_AMOUNT + " REAL , " +
            TELLER_CASH_DATE + " TEXT, " + TELLER_CASH_PACKAGE_ID + " INTEGER , " + TELLER_CASH_TELLER_NAME + " TEXT, " + TELLER_CASH_BRANCH + " TEXT, " +
            TELLER_CASH_CODE + " REAL, " + TELLER_CASH_STATUS + " TEXT, " +"FOREIGN KEY(" + TELLER_CASH_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";



    //@PrimaryKey(autoGenerate = true)
    private int tellerCashID=130;
    private int tellerProfileID;
    private String tellerCashDate;
    private String tellerCashStatus;
    private String tellerItemName;
    private String tellerCashTellerName;
    private double tellerCashAmount;
    private long tellerCashCode;
    private long tellerCashPackageID;
    private String tellerBranchOffice;

    public TellerCash(Parcel in) {
        tellerCashID = in.readInt();
        tellerProfileID = in.readInt();
        tellerCashDate = in.readString();
        tellerCashStatus = in.readString();
        tellerCashAmount = in.readDouble();
        tellerCashCode = in.readLong();
        tellerBranchOffice = in.readString();
    }

    public static final Creator<TellerCash> CREATOR = new Creator<TellerCash>() {
        @Override
        public TellerCash createFromParcel(Parcel in) {
            return new TellerCash(in);
        }

        @Override
        public TellerCash[] newArray(int size) {
            return new TellerCash[size];
        }
    };

    public TellerCash(int tellerCashID, int profileID, int packageID, String tellerItemName, double amount,String tellerName, String branch, String date, long code, String status) {
        this.tellerCashID = tellerCashID;
        this.tellerProfileID = profileID;
        this.tellerCashPackageID = packageID;
        this.tellerItemName = tellerItemName;
        this.tellerCashTellerName = tellerName;
        this.tellerBranchOffice = branch;
        this.tellerCashDate = date;
        this.tellerCashCode = code;
        this.tellerCashAmount = amount;
        this.tellerCashStatus = status;
    }

    public TellerCash() {
        super();
    }

    public TellerCash(int reportID,int packageID, String finalItemType, double tellerAmount, String tellerName, String officeBranch, String packageStartDate) {
        this.tellerCashPackageID = packageID;
        this.tellerCashID = reportID;
        this.tellerItemName = finalItemType;
        this.tellerCashTellerName = tellerName;
        this.tellerBranchOffice = officeBranch;
        this.tellerCashDate = packageStartDate;
        this.tellerCashAmount = tellerAmount;
    }

    public int getTellerCashID() {
        return tellerCashID;
    }

    public void setTellerCashID(int tellerCashID) {
        this.tellerCashID = tellerCashID;
    }

    public long getTellerProfileID() {
        return tellerProfileID;
    }

    public void setTellerProfileID(int tellerProfileID) {
        this.tellerProfileID = tellerProfileID;
    }

    public String getTellerCashDate() {
        return tellerCashDate;
    }

    public void setTellerCashDate(String tellerCashDate) {
        this.tellerCashDate = tellerCashDate;
    }

    public String getTellerCashStatus() {
        return tellerCashStatus;
    }

    public void setTellerCashStatus(String tellerCashStatus) {
        this.tellerCashStatus = tellerCashStatus;
    }

    public double getTellerCashAmount() {
        return tellerCashAmount;
    }

    public void setTellerCashAmount(double tellerCashAmount) {
        this.tellerCashAmount = tellerCashAmount;
    }

    public long getTellerCashCode() {
        return tellerCashCode;
    }

    public void setTellerCashCode(long tellerCashCode) {
        this.tellerCashCode = tellerCashCode;
    }

    public String getTellerBranchOffice() {
        return tellerBranchOffice;
    }

    public void setTellerBranchOffice(String tellerBranchOffice) {
        this.tellerBranchOffice = tellerBranchOffice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(tellerCashID);
        parcel.writeLong(tellerProfileID);
        parcel.writeString(tellerCashDate);
        parcel.writeString(tellerCashStatus);
        parcel.writeDouble(tellerCashAmount);
        parcel.writeLong(tellerCashCode);
        parcel.writeString(tellerBranchOffice);
    }

    public String getTellerCashItemName() {
        return tellerItemName;
    }

    public void setTellerItemName(String tellerItemName) {
        this.tellerItemName = tellerItemName;
    }

    public long getTellerCashPackageID() {
        return tellerCashPackageID;
    }

    public void setTellerCashPackageID(long tellerCashPackageID) {
        this.tellerCashPackageID = tellerCashPackageID;
    }

    public String getTellerCashTellerName() {
        return tellerCashTellerName;
    }

    public void setTellerCashTellerName(String tellerCashTellerName) {
        this.tellerCashTellerName = tellerCashTellerName;
    }
}
