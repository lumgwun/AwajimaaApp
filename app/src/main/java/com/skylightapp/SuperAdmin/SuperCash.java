package com.skylightapp.SuperAdmin;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class SuperCash implements Serializable, Parcelable {
    public static final String SUPER_CASH_TABLE = "superc_Table";
    public static final String SUPER_CASH_ID = "super_cash_id";
    public static final String SUPER_CASH_AMOUNT = "superc_Amount";
    public static final String SUPER_CASH_DATE = "superc_Date";
    public static final String SUPER_CASH_CODE = "superc_Code";
    public static final String SUPER_CASH_COLLECTOR_TYPE = "superc_Type";
    public static final String SUPER_CASH_TRANX_STATUS = "Super_Cash_status";
    public static final String SUPER_CASH_BRANCH = "superc_Branch";
    public static final String SUPER_CASH_APPROVER = "superc_Approver";
    public static final String SUPER_CASH_COLLECTOR = "superc_Collector";
    public static final String SUPER_CASH_PROFILE_ID = "superc_Profile-ID";
    public static final String SUPER_CASH_CONFIRMATION_DATE = "superc_Conf_Date";

    /*public static final String CREATE_SUPER_CASH_TABLE = "CREATE TABLE IF NOT EXISTS " + SUPER_CASH_TABLE + " (" + SUPER_CASH_ID + " INTEGER PRIMARY KEY, " + SUPER_CASH_PROFILE_ID + " INTEGER , " + SUPER_CASH_AMOUNT + " DOUBLE , " +
            SUPER_CASH_BRANCH + " TEXT, " + SUPER_CASH_COLLECTOR_TYPE + " TEXT , " + SUPER_CASH_COLLECTOR + " TEXT, " + SUPER_CASH_CODE + " TEXT, " + SUPER_CASH_DATE + " TEXT, " + SUPER_CASH_APPROVER + " TEXT, " +
            SUPER_CASH_CONFIRMATION_DATE + " TEXT, " + SUPER_CASH_TRANX_STATUS + " TEXT, " +"FOREIGN KEY(" + SUPER_CASH_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";*/



    int superCashID;
    int profileID;
    String superCashDate;
    String superCashStatus;
    double superCashAmount;
    String sCCollectorType;
    String scBranchOffice;
    String scApprovalDate;
    String superCashApprover;
    long superCashCode;
    String superCashCollector;


    protected SuperCash(Parcel in) {
        superCashID = in.readInt();
        profileID = in.readInt();
        superCashDate = in.readString();
        superCashStatus = in.readString();
        superCashAmount = in.readDouble();
        sCCollectorType = in.readString();
        scBranchOffice = in.readString();
        scApprovalDate = in.readString();
        superCashApprover = in.readString();
        superCashCode = in.readLong();
        superCashCollector = in.readString();
    }

    public static final Creator<SuperCash> CREATOR = new Creator<SuperCash>() {
        @Override
        public SuperCash createFromParcel(Parcel in) {
            return new SuperCash(in);
        }

        @Override
        public SuperCash[] newArray(int size) {
            return new SuperCash[size];
        }
    };

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public String getsCCollectorType() {
        return sCCollectorType;
    }

    public void setsCCollectorType(String sCCollectorType) {
        this.sCCollectorType = sCCollectorType;
    }

    public String getSuperCashOfficeBranch() {
        return scBranchOffice;
    }

    public void setSuperCashOfficeBranch(String depositBranchOffice) {
        this.scBranchOffice = depositBranchOffice;
    }
    public String getSuperCashApprover() {
        return superCashApprover;
    }

    public void setSuperCashApprover(String superCashApprover) {
        this.superCashApprover = superCashApprover;
    }


    public String getScApprovalDate() {
        return scApprovalDate;
    }

    public void setScApprovalDate(String scApprovalDate) {
        this.scApprovalDate = scApprovalDate;
    }


    public void setSuperCashCollector(String superCashCollector) {
        this.superCashCollector = superCashCollector;
    }
    public String getSuperCashCollector() {
        return superCashCollector;
    }


    public String getSuperCashDate() {
        return superCashDate;
    }

    public void setSuperCashDate(String superCashDate) {
        this.superCashDate = superCashDate;
    }

    public String getSuperCashStatus() {
        return superCashStatus;
    }

    public void setSuperCashStatus(String superCashStatus) {
        this.superCashStatus = superCashStatus;
    }

    public long getSuperCashID() {
        return superCashID;
    }

    public void setSuperCashID(int superCashID) {
        this.superCashID = superCashID;
    }

    public Double getSuperCashAmount() {
        return superCashAmount;
    }

    public void setSuperCashAmount(double superCashAmount) {
        this.superCashAmount = superCashAmount;
    }

    public long getSuperCashCode() {
        return superCashCode;
    }

    public void setSuperCashCode(long superCashCode) {
        this.superCashCode = superCashCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(superCashID);
        parcel.writeLong(profileID);
        parcel.writeString(superCashDate);
        parcel.writeString(superCashStatus);
        parcel.writeDouble(superCashAmount);
        parcel.writeString(sCCollectorType);
        parcel.writeString(scBranchOffice);
        parcel.writeString(scApprovalDate);
        parcel.writeString(superCashApprover);
        parcel.writeLong(superCashCode);
        parcel.writeString(superCashCollector);
    }
}
