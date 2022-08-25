package com.skylightapp.Markets;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BizDealMileStone implements Serializable, Parcelable {
    private int mileStoneID;
    private int mileStoneBizDealID;
    private double mileStoneAmt;
    private String mileStoneCode;
    private String mileStoneDate;

    public BizDealMileStone () {
        super();

    }


    protected BizDealMileStone(Parcel in) {
        mileStoneID = in.readInt();
        mileStoneBizDealID = in.readInt();
        mileStoneAmt = in.readDouble();
        mileStoneCode = in.readString();
        mileStoneDate = in.readString();
    }

    public static final Creator<BizDealMileStone> CREATOR = new Creator<BizDealMileStone>() {
        @Override
        public BizDealMileStone createFromParcel(Parcel in) {
            return new BizDealMileStone(in);
        }

        @Override
        public BizDealMileStone[] newArray(int size) {
            return new BizDealMileStone[size];
        }
    };

    public int getMileStoneID() {
        return mileStoneID;
    }

    public void setMileStoneID(int mileStoneID) {
        this.mileStoneID = mileStoneID;
    }

    public double getMileStoneAmt() {
        return mileStoneAmt;
    }

    public void setMileStoneAmt(double mileStoneAmt) {
        this.mileStoneAmt = mileStoneAmt;
    }

    public String getMileStoneCode() {
        return mileStoneCode;
    }

    public void setMileStoneCode(String mileStoneCode) {
        this.mileStoneCode = mileStoneCode;
    }

    public String getMileStoneDate() {
        return mileStoneDate;
    }

    public void setMileStoneDate(String mileStoneDate) {
        this.mileStoneDate = mileStoneDate;
    }

    public int getMileStoneBizDealID() {
        return mileStoneBizDealID;
    }

    public void setMileStoneBizDealID(int mileStoneBizDealID) {
        this.mileStoneBizDealID = mileStoneBizDealID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mileStoneID);
        parcel.writeInt(mileStoneBizDealID);
        parcel.writeDouble(mileStoneAmt);
        parcel.writeString(mileStoneCode);
        parcel.writeString(mileStoneDate);
    }
}
