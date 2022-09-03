package com.skylightapp.Markets;

import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Account;

import java.io.Serializable;

public class FarmTranx implements Serializable, Parcelable {
    private int farmTranxID;
    private int farmTranxProfID;
    private int farmTranxFarmID;
    private String farmTranxStatus;
    private String farmTranxCurrency;
    private double farmTranxAmount;
    private double farmTranxCommission;
    private  int farmTranxCode;
    private Account farmTranxSenderAcct;
    private Account farmTranxReceiverAcct;

    public FarmTranx () {
        super();

    }

    protected FarmTranx(Parcel in) {
        farmTranxID = in.readInt();
        farmTranxProfID = in.readInt();
        farmTranxFarmID = in.readInt();
        farmTranxStatus = in.readString();
        farmTranxCurrency = in.readString();
        farmTranxAmount = in.readDouble();
        farmTranxCommission = in.readDouble();
        farmTranxCode = in.readInt();
        farmTranxSenderAcct = in.readParcelable(Account.class.getClassLoader());
        farmTranxReceiverAcct = in.readParcelable(Account.class.getClassLoader());
    }

    public static final Creator<FarmTranx> CREATOR = new Creator<FarmTranx>() {
        @Override
        public FarmTranx createFromParcel(Parcel in) {
            return new FarmTranx(in);
        }

        @Override
        public FarmTranx[] newArray(int size) {
            return new FarmTranx[size];
        }
    };

    public int getFarmTranxID() {
        return farmTranxID;
    }

    public void setFarmTranxID(int farmTranxID) {
        this.farmTranxID = farmTranxID;
    }

    public int getFarmTranxProfID() {
        return farmTranxProfID;
    }

    public void setFarmTranxProfID(int farmTranxProfID) {
        this.farmTranxProfID = farmTranxProfID;
    }

    public int getFarmTranxFarmID() {
        return farmTranxFarmID;
    }

    public void setFarmTranxFarmID(int farmTranxFarmID) {
        this.farmTranxFarmID = farmTranxFarmID;
    }

    public String getFarmTranxStatus() {
        return farmTranxStatus;
    }

    public void setFarmTranxStatus(String farmTranxStatus) {
        this.farmTranxStatus = farmTranxStatus;
    }

    public String getFarmTranxCurrency() {
        return farmTranxCurrency;
    }

    public void setFarmTranxCurrency(String farmTranxCurrency) {
        this.farmTranxCurrency = farmTranxCurrency;
    }

    public double getFarmTranxAmount() {
        return farmTranxAmount;
    }

    public void setFarmTranxAmount(double farmTranxAmount) {
        this.farmTranxAmount = farmTranxAmount;
    }

    public double getFarmTranxCommission() {
        return farmTranxCommission;
    }

    public void setFarmTranxCommission(double farmTranxCommission) {
        this.farmTranxCommission = farmTranxCommission;
    }

    public int getFarmTranxCode() {
        return farmTranxCode;
    }

    public void setFarmTranxCode(int farmTranxCode) {
        this.farmTranxCode = farmTranxCode;
    }

    public Account getFarmTranxSenderAcct() {
        return farmTranxSenderAcct;
    }

    public void setFarmTranxSenderAcct(Account farmTranxSenderAcct) {
        this.farmTranxSenderAcct = farmTranxSenderAcct;
    }

    public Account getFarmTranxReceiverAcct() {
        return farmTranxReceiverAcct;
    }

    public void setFarmTranxReceiverAcct(Account farmTranxReceiverAcct) {
        this.farmTranxReceiverAcct = farmTranxReceiverAcct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(farmTranxID);
        parcel.writeInt(farmTranxProfID);
        parcel.writeInt(farmTranxFarmID);
        parcel.writeString(farmTranxStatus);
        parcel.writeString(farmTranxCurrency);
        parcel.writeDouble(farmTranxAmount);
        parcel.writeDouble(farmTranxCommission);
        parcel.writeInt(farmTranxCode);
        parcel.writeParcelable(farmTranxSenderAcct, i);
        parcel.writeParcelable(farmTranxReceiverAcct, i);
    }
}
