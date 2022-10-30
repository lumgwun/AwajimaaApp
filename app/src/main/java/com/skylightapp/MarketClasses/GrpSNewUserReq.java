package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.GroupAccount;

public class GrpSNewUserReq implements Parcelable {
    private int gsAReqID;
    private int gsAReqAcctID;
    private GroupAccount groupSReqAcct;
    private String gsAReqName;
    private String gsAReqCurrency;
    private double gsAReqAmount;
    private double gsAReqAmtSoFar;
    private int duration;
    private String gsAReqStartDate;
    private String gsAReqEndDate;
    private String gsAReqStatus;
    private int gsAReqNoOfPersons;
    private int gsAReqDurationRem;

    protected GrpSNewUserReq(Parcel in) {
        gsAReqID = in.readInt();
        gsAReqAcctID = in.readInt();
        groupSReqAcct = in.readParcelable(GroupAccount.class.getClassLoader());
        gsAReqName = in.readString();
        gsAReqCurrency = in.readString();
        gsAReqAmount = in.readDouble();
        gsAReqAmtSoFar = in.readDouble();
        duration = in.readInt();
        gsAReqStartDate = in.readString();
        gsAReqEndDate = in.readString();
        gsAReqStatus = in.readString();
        gsAReqNoOfPersons = in.readInt();
    }

    public static final Creator<GrpSNewUserReq> CREATOR = new Creator<GrpSNewUserReq>() {
        @Override
        public GrpSNewUserReq createFromParcel(Parcel in) {
            return new GrpSNewUserReq(in);
        }

        @Override
        public GrpSNewUserReq[] newArray(int size) {
            return new GrpSNewUserReq[size];
        }
    };

    public int getGsAReqID() {
        return gsAReqID;
    }

    public void setGsAReqID(int gsAReqID) {
        this.gsAReqID = gsAReqID;
    }

    public String getGsAReqName() {
        return gsAReqName;
    }

    public void setGsAReqName(String gsAReqName) {
        this.gsAReqName = gsAReqName;
    }

    public String getGsAReqCurrency() {
        return gsAReqCurrency;
    }

    public void setGsAReqCurrency(String gsAReqCurrency) {
        this.gsAReqCurrency = gsAReqCurrency;
    }

    public double getGsAReqAmount() {
        return gsAReqAmount;
    }

    public void setGsAReqAmount(double gsAReqAmount) {
        this.gsAReqAmount = gsAReqAmount;
    }

    public double getGsAReqAmtSoFar() {
        return gsAReqAmtSoFar;
    }

    public void setGsAReqAmtSoFar(double gsAReqAmtSoFar) {
        this.gsAReqAmtSoFar = gsAReqAmtSoFar;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getGsAReqStartDate() {
        return gsAReqStartDate;
    }

    public void setGsAReqStartDate(String gsAReqStartDate) {
        this.gsAReqStartDate = gsAReqStartDate;
    }

    public String getGsAReqEndDate() {
        return gsAReqEndDate;
    }

    public void setGsAReqEndDate(String gsAReqEndDate) {
        this.gsAReqEndDate = gsAReqEndDate;
    }

    public String getGsAReqStatus() {
        return gsAReqStatus;
    }

    public void setGsAReqStatus(String gsAReqStatus) {
        this.gsAReqStatus = gsAReqStatus;
    }

    public int getGsAReqNoOfPersons() {
        return gsAReqNoOfPersons;
    }

    public void setGsAReqNoOfPersons(int gsAReqNoOfPersons) {
        this.gsAReqNoOfPersons = gsAReqNoOfPersons;
    }

    public int getGsAReqAcctID() {
        return gsAReqAcctID;
    }

    public void setGsAReqAcctID(int gsAReqAcctID) {
        this.gsAReqAcctID = gsAReqAcctID;
    }

    public GroupAccount getGroupSReqAcct() {
        return groupSReqAcct;
    }

    public void setGroupSReqAcct(GroupAccount groupSReqAcct) {
        this.groupSReqAcct = groupSReqAcct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(gsAReqID);
        parcel.writeInt(gsAReqAcctID);
        parcel.writeParcelable(groupSReqAcct, i);
        parcel.writeString(gsAReqName);
        parcel.writeString(gsAReqCurrency);
        parcel.writeDouble(gsAReqAmount);
        parcel.writeDouble(gsAReqAmtSoFar);
        parcel.writeInt(duration);
        parcel.writeString(gsAReqStartDate);
        parcel.writeString(gsAReqEndDate);
        parcel.writeString(gsAReqStatus);
        parcel.writeInt(gsAReqNoOfPersons);
    }

    public int getGsAReqDurationRem() {
        return gsAReqDurationRem;
    }

    public void setGsAReqDurationRem(int gsAReqDurationRem) {
        this.gsAReqDurationRem = gsAReqDurationRem;
    }
}
