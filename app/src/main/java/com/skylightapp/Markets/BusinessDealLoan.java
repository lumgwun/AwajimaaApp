package com.skylightapp.Markets;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BusinessDealLoan implements Serializable, Parcelable {
    private int bizDLID;
    private int bizDLBizDealID;
    private int bizDLProfID;
    private int bizDLAcctID;
    private int bizDLMarketID;
    private String bizDLStatus;
    private double bizDLAmount;
    private double bizDLInterest;

    public BusinessDealLoan () {
        super();

    }

    protected BusinessDealLoan(Parcel in) {
        bizDLID = in.readInt();
        bizDLBizDealID = in.readInt();
        bizDLProfID = in.readInt();
        bizDLAcctID = in.readInt();
        bizDLMarketID = in.readInt();
        bizDLStatus = in.readString();
        bizDLAmount = in.readDouble();
        bizDLInterest = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bizDLID);
        dest.writeInt(bizDLBizDealID);
        dest.writeInt(bizDLProfID);
        dest.writeInt(bizDLAcctID);
        dest.writeInt(bizDLMarketID);
        dest.writeString(bizDLStatus);
        dest.writeDouble(bizDLAmount);
        dest.writeDouble(bizDLInterest);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusinessDealLoan> CREATOR = new Creator<BusinessDealLoan>() {
        @Override
        public BusinessDealLoan createFromParcel(Parcel in) {
            return new BusinessDealLoan(in);
        }

        @Override
        public BusinessDealLoan[] newArray(int size) {
            return new BusinessDealLoan[size];
        }
    };

    public int getBizDLID() {
        return bizDLID;
    }

    public void setBizDLID(int bizDLID) {
        this.bizDLID = bizDLID;
    }

    public int getBizDLBizDealID() {
        return bizDLBizDealID;
    }

    public void setBizDLBizDealID(int bizDLBizDealID) {
        this.bizDLBizDealID = bizDLBizDealID;
    }

    public int getBizDLProfID() {
        return bizDLProfID;
    }

    public void setBizDLProfID(int bizDLProfID) {
        this.bizDLProfID = bizDLProfID;
    }

    public int getBizDLAcctID() {
        return bizDLAcctID;
    }

    public void setBizDLAcctID(int bizDLAcctID) {
        this.bizDLAcctID = bizDLAcctID;
    }

    public String getBizDLStatus() {
        return bizDLStatus;
    }

    public void setBizDLStatus(String bizDLStatus) {
        this.bizDLStatus = bizDLStatus;
    }

    public double getBizDLAmount() {
        return bizDLAmount;
    }

    public void setBizDLAmount(double bizDLAmount) {
        this.bizDLAmount = bizDLAmount;
    }

    public double getBizDLInterest() {
        return bizDLInterest;
    }

    public void setBizDLInterest(double bizDLInterest) {
        this.bizDLInterest = bizDLInterest;
    }

    public int getBizDLMarketID() {
        return bizDLMarketID;
    }

    public void setBizDLMarketID(int bizDLMarketID) {
        this.bizDLMarketID = bizDLMarketID;
    }
}
