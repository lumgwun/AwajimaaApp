package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BusinessDealSub  implements Serializable, Parcelable {
    private int bizDSubID;
    private int bizDSubBizDealID;
    private int bizDSubProfID;
    private int bizDSubBizID;
    private int bizDSubMarketID;
    private String bizDSubTittle;
    private  String bizDSubDesc;
    private double bizDSubAmount;
    private String bizDSubCurrency;
    private  int bizDSubMilestoneCount;
    private  int bizDSubCode;
    private String bizDSubStartTime;
    private String bizDSubEndTime;
    private String bizDSubStatus;

    public BusinessDealSub () {
        super();

    }

    public BusinessDealSub(int id, int code,String title, String desc, double amount, String currency, int milestones, String startTime, String endTime, String status) {
        this.bizDSubID=id;
        this.bizDSubTittle=title;
        this.bizDSubDesc=desc;
        this.bizDSubAmount=amount;
        this.bizDSubCode=code;
        this.bizDSubCurrency=currency;
        this.bizDSubMilestoneCount=milestones;
        this.bizDSubStartTime=startTime;
        this.bizDSubEndTime=endTime;
        this.bizDSubStatus=status;
    }

    protected BusinessDealSub(Parcel in) {
        bizDSubID = in.readInt();
        bizDSubBizDealID = in.readInt();
        bizDSubProfID = in.readInt();
        bizDSubBizID = in.readInt();
        bizDSubMarketID = in.readInt();
        bizDSubTittle = in.readString();
        bizDSubDesc = in.readString();
        bizDSubAmount = in.readDouble();
        bizDSubCurrency = in.readString();
        bizDSubMilestoneCount = in.readInt();
        bizDSubStartTime = in.readString();
        bizDSubEndTime = in.readString();
        bizDSubStatus = in.readString();
    }

    public static final Creator<BusinessDealSub> CREATOR = new Creator<BusinessDealSub>() {
        @Override
        public BusinessDealSub createFromParcel(Parcel in) {
            return new BusinessDealSub(in);
        }

        @Override
        public BusinessDealSub[] newArray(int size) {
            return new BusinessDealSub[size];
        }
    };

    public int getBizDSubID() {
        return bizDSubID;
    }

    public void setBizDSubID(int bizDSubID) {
        this.bizDSubID = bizDSubID;
    }

    public int getBizDSubBizDealID() {
        return bizDSubBizDealID;
    }

    public void setBizDSubBizDealID(int bizDSubBizDealID) {
        this.bizDSubBizDealID = bizDSubBizDealID;
    }

    public int getBizDSubProfID() {
        return bizDSubProfID;
    }

    public void setBizDSubProfID(int bizDSubProfID) {
        this.bizDSubProfID = bizDSubProfID;
    }

    public String getBizDSubTittle() {
        return bizDSubTittle;
    }

    public void setBizDSubTittle(String bizDSubTittle) {
        this.bizDSubTittle = bizDSubTittle;
    }

    public String getBizDSubDesc() {
        return bizDSubDesc;
    }

    public void setBizDSubDesc(String bizDSubDesc) {
        this.bizDSubDesc = bizDSubDesc;
    }

    public double getBizDSubAmount() {
        return bizDSubAmount;
    }

    public void setBizDSubAmount(double bizDSubAmount) {
        this.bizDSubAmount = bizDSubAmount;
    }

    public String getBizDSubCurrency() {
        return bizDSubCurrency;
    }

    public void setBizDSubCurrency(String bizDSubCurrency) {
        this.bizDSubCurrency = bizDSubCurrency;
    }

    public int getBizDSubMilestoneCount() {
        return bizDSubMilestoneCount;
    }

    public void setBizDSubMilestoneCount(int bizDSubMilestoneCount) {
        this.bizDSubMilestoneCount = bizDSubMilestoneCount;
    }

    public String getBizDSubStartTime() {
        return bizDSubStartTime;
    }

    public void setBizDSubStartTime(String bizDSubStartTime) {
        this.bizDSubStartTime = bizDSubStartTime;
    }

    public String getBizDSubEndTime() {
        return bizDSubEndTime;
    }

    public void setBizDSubEndTime(String bizDSubEndTime) {
        this.bizDSubEndTime = bizDSubEndTime;
    }

    public String getBizDSubStatus() {
        return bizDSubStatus;
    }

    public void setBizDSubStatus(String bizDSubStatus) {
        this.bizDSubStatus = bizDSubStatus;
    }

    public int getBizDSubBizID() {
        return bizDSubBizID;
    }

    public void setBizDSubBizID(int bizDSubBizID) {
        this.bizDSubBizID = bizDSubBizID;
    }

    public int getBizDSubMarketID() {
        return bizDSubMarketID;
    }

    public void setBizDSubMarketID(int bizDSubMarketID) {
        this.bizDSubMarketID = bizDSubMarketID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bizDSubID);
        parcel.writeInt(bizDSubBizDealID);
        parcel.writeInt(bizDSubProfID);
        parcel.writeInt(bizDSubBizID);
        parcel.writeInt(bizDSubMarketID);
        parcel.writeString(bizDSubTittle);
        parcel.writeString(bizDSubDesc);
        parcel.writeDouble(bizDSubAmount);
        parcel.writeString(bizDSubCurrency);
        parcel.writeInt(bizDSubMilestoneCount);
        parcel.writeString(bizDSubStartTime);
        parcel.writeString(bizDSubEndTime);
        parcel.writeString(bizDSubStatus);
    }

    public int getBizDSubCode() {
        return bizDSubCode;
    }

    public void setBizDSubCode(int bizDSubCode) {
        this.bizDSubCode = bizDSubCode;
    }
}