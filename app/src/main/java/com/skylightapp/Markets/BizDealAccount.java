package com.skylightapp.Markets;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BizDealAccount implements Serializable, Parcelable {
    private int bdaBizDealID;
    private int bdAcctID;
    private String bdaAcctName;
    private double bdaTotalAmt;
    private double bdaLogisticAmt;
    private double bdaSeeAmt;
    private String bdaStatus;

    protected BizDealAccount(Parcel in) {
        bdaBizDealID = in.readInt();
        bdAcctID = in.readInt();
        bdaAcctName = in.readString();
        bdaTotalAmt = in.readDouble();
        bdaLogisticAmt = in.readDouble();
        bdaSeeAmt = in.readDouble();
        bdaStatus = in.readString();
    }

    public static final Creator<BizDealAccount> CREATOR = new Creator<BizDealAccount>() {
        @Override
        public BizDealAccount createFromParcel(Parcel in) {
            return new BizDealAccount(in);
        }

        @Override
        public BizDealAccount[] newArray(int size) {
            return new BizDealAccount[size];
        }
    };

    public int getBdaBizDealID() {
        return bdaBizDealID;
    }

    public void setBdaBizDealID(int bdaBizDealID) {
        this.bdaBizDealID = bdaBizDealID;
    }

    public int getBdAcctID() {
        return bdAcctID;
    }

    public void setBdAcctID(int bdAcctID) {
        this.bdAcctID = bdAcctID;
    }

    public double getBdaTotalAmt() {
        return bdaTotalAmt;
    }

    public void setBdaTotalAmt(double bdaTotalAmt) {
        this.bdaTotalAmt = bdaTotalAmt;
    }

    public double getBdaLogisticAmt() {
        return bdaLogisticAmt;
    }

    public void setBdaLogisticAmt(double bdaLogisticAmt) {
        this.bdaLogisticAmt = bdaLogisticAmt;
    }

    public double getBdaSeeAmt() {
        return bdaSeeAmt;
    }

    public void setBdaSeeAmt(double bdaSeeAmt) {
        this.bdaSeeAmt = bdaSeeAmt;
    }

    public String getBdaStatus() {
        return bdaStatus;
    }

    public void setBdaStatus(String bdaStatus) {
        this.bdaStatus = bdaStatus;
    }

    public String getBdaAcctName() {
        return bdaAcctName;
    }

    public void setBdaAcctName(String bdaAcctName) {
        this.bdaAcctName = bdaAcctName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bdaBizDealID);
        parcel.writeInt(bdAcctID);
        parcel.writeString(bdaAcctName);
        parcel.writeDouble(bdaTotalAmt);
        parcel.writeDouble(bdaLogisticAmt);
        parcel.writeDouble(bdaSeeAmt);
        parcel.writeString(bdaStatus);
    }
}
