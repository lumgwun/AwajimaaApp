package com.skylightapp.Markets;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class FarmCommodity implements Serializable, Parcelable {
    private int farmComID;
    private String farmComName;
    private int farmComImg;
    private double farmComPrice;
    private String farmComPriceUnit;
    private String farmComStatus;
    public FarmCommodity () {
        super();

    }

    protected FarmCommodity(Parcel in) {
        farmComID = in.readInt();
        farmComName = in.readString();
        farmComImg = in.readInt();
        farmComPrice = in.readDouble();
        farmComPriceUnit = in.readString();
        farmComStatus = in.readString();
    }

    public static final Creator<FarmCommodity> CREATOR = new Creator<FarmCommodity>() {
        @Override
        public FarmCommodity createFromParcel(Parcel in) {
            return new FarmCommodity(in);
        }

        @Override
        public FarmCommodity[] newArray(int size) {
            return new FarmCommodity[size];
        }
    };

    public int getFarmComID() {
        return farmComID;
    }

    public void setFarmComID(int farmComID) {
        this.farmComID = farmComID;
    }

    public String getFarmComName() {
        return farmComName;
    }

    public void setFarmComName(String farmComName) {
        this.farmComName = farmComName;
    }

    public int getFarmComImg() {
        return farmComImg;
    }

    public void setFarmComImg(int farmComImg) {
        this.farmComImg = farmComImg;
    }

    public String getFarmComStatus() {
        return farmComStatus;
    }

    public void setFarmComStatus(String farmComStatus) {
        this.farmComStatus = farmComStatus;
    }

    public double getFarmComPrice() {
        return farmComPrice;
    }

    public void setFarmComPrice(double farmComPrice) {
        this.farmComPrice = farmComPrice;
    }

    public String getFarmComPriceUnit() {
        return farmComPriceUnit;
    }

    public void setFarmComPriceUnit(String farmComPriceUnit) {
        this.farmComPriceUnit = farmComPriceUnit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(farmComID);
        parcel.writeString(farmComName);
        parcel.writeInt(farmComImg);
        parcel.writeDouble(farmComPrice);
        parcel.writeString(farmComPriceUnit);
        parcel.writeString(farmComStatus);
    }
}
