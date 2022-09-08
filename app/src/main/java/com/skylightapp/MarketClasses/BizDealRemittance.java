package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BizDealRemittance implements Serializable, Parcelable {
    private int bdRID;
    private int bdRBizDealID;
    private int bdRProfID;
    private  String bdRDate;
    private double bdRAmount;
    private String bdRStatus;
    private int bdRCode;
    private String bdRApprovalDate;

    public BizDealRemittance () {
        super();

    }

    protected BizDealRemittance(Parcel in) {
        bdRID = in.readInt();
        bdRBizDealID = in.readInt();
        bdRProfID = in.readInt();
        bdRDate = in.readString();
        bdRAmount = in.readDouble();
        bdRStatus = in.readString();
        bdRCode = in.readInt();
    }

    public static final Creator<BizDealRemittance> CREATOR = new Creator<BizDealRemittance>() {
        @Override
        public BizDealRemittance createFromParcel(Parcel in) {
            return new BizDealRemittance(in);
        }

        @Override
        public BizDealRemittance[] newArray(int size) {
            return new BizDealRemittance[size];
        }
    };

    public int getBdRID() {
        return bdRID;
    }

    public void setBdRID(int bdRID) {
        this.bdRID = bdRID;
    }

    public String getBdRDate() {
        return bdRDate;
    }

    public void setBdRDate(String bdRDate) {
        this.bdRDate = bdRDate;
    }

    public double getBdRAmount() {
        return bdRAmount;
    }

    public void setBdRAmount(double bdRAmount) {
        this.bdRAmount = bdRAmount;
    }

    public String getBdRStatus() {
        return bdRStatus;
    }

    public void setBdRStatus(String bdRStatus) {
        this.bdRStatus = bdRStatus;
    }

    public int getBdRCode() {
        return bdRCode;
    }

    public void setBdRCode(int bdRCode) {
        this.bdRCode = bdRCode;
    }

    public int getBdRBizDealID() {
        return bdRBizDealID;
    }

    public void setBdRBizDealID(int bdRBizDealID) {
        this.bdRBizDealID = bdRBizDealID;
    }

    public int getBdRProfID() {
        return bdRProfID;
    }

    public void setBdRProfID(int bdRProfID) {
        this.bdRProfID = bdRProfID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bdRID);
        parcel.writeInt(bdRBizDealID);
        parcel.writeInt(bdRProfID);
        parcel.writeString(bdRDate);
        parcel.writeDouble(bdRAmount);
        parcel.writeString(bdRStatus);
        parcel.writeInt(bdRCode);
    }

    public String getBdRApprovalDate() {
        return bdRApprovalDate;
    }

    public void setBdRApprovalDate(String bdRApprovalDate) {
        this.bdRApprovalDate = bdRApprovalDate;
    }
}
