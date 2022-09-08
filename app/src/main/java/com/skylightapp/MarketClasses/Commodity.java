package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Commodity implements Serializable, Parcelable {
    private int commodityID;
    private int commodityProfID;
    private String commodityName;
    private String commodityType;
    private String commodityDes;
    private int commodityQty;
    private int commodityImage;
    private String commodityStatus;
    private double commodityRevenue;
    private ArrayList<Market> commodityMarkets;

    public Commodity () {
        super();

    }
    public Commodity (String commodityName, String commodityType, String commodityDes,int img) {
        this.commodityName = commodityName;
        this.commodityType = commodityType;
        this.commodityDes = commodityDes;
        this.commodityImage = img;
    }

    protected Commodity(Parcel in) {
        commodityID = in.readInt();
        commodityProfID = in.readInt();
        commodityName = in.readString();
        commodityType = in.readString();
        commodityDes = in.readString();
        commodityQty = in.readInt();
        commodityImage = in.readInt();
        //commodityImage = in.readParcelable(Uri.class.getClassLoader());
        commodityStatus = in.readString();
        commodityMarkets = in.createTypedArrayList(Market.CREATOR);
    }

    public static final Creator<Commodity> CREATOR = new Creator<Commodity>() {
        @Override
        public Commodity createFromParcel(Parcel in) {
            return new Commodity(in);
        }

        @Override
        public Commodity[] newArray(int size) {
            return new Commodity[size];
        }
    };

    public int getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(int commodityID) {
        this.commodityID = commodityID;
    }

    public int getCommodityProfID() {
        return commodityProfID;
    }

    public void setCommodityProfID(int commodityProfID) {
        this.commodityProfID = commodityProfID;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(String commodityType) {
        this.commodityType = commodityType;
    }

    public String getCommodityDes() {
        return commodityDes;
    }

    public void setCommodityDes(String commodityDes) {
        this.commodityDes = commodityDes;
    }

    public int getCommodityQty() {
        return commodityQty;
    }

    public void setCommodityQty(int commodityQty) {
        this.commodityQty = commodityQty;
    }

    public String getCommodityStatus() {
        return commodityStatus;
    }

    public void setCommodityStatus(String commodityStatus) {
        this.commodityStatus = commodityStatus;
    }

    public int getCommodityImage() {
        return commodityImage;
    }

    public void setCommodityImage(int commodityImage) {
        this.commodityImage = commodityImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(commodityID);
        parcel.writeInt(commodityProfID);
        parcel.writeString(commodityName);
        parcel.writeString(commodityType);
        parcel.writeString(commodityDes);
        parcel.writeInt(commodityQty);
        parcel.writeInt(commodityImage);
        parcel.writeString(commodityStatus);
        parcel.writeTypedList(commodityMarkets);
    }

    public double getCommodityRevenue() {
        return commodityRevenue;
    }

    public void setCommodityRevenue(double commodityRevenue) {
        this.commodityRevenue = commodityRevenue;
    }
}
