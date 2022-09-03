package com.skylightapp.Markets;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Farm implements Serializable, Parcelable {
    private int farmID;
    private int farmProfID;
    private int farmMarketID;
    private int farmBusinessID;
    private int farmLogo;
    private String farmName;
    private String farmType;
    private Location farmLocation;
    private String farmLGA;
    private String farmState;
    private String farmCountry;
    private  String farmStatus;
    private  String farmStartDate;
    private  FarmCommodity farmCommodity;
    private double farmRevenue;
    private ArrayList<FarmCommodity> farmCommodityList;
    private ArrayList<FarmTranx> farmTranxList;

    public Farm () {
        super();

    }

    protected Farm(Parcel in) {
        farmID = in.readInt();
        farmProfID = in.readInt();
        farmMarketID = in.readInt();
        farmBusinessID = in.readInt();
        farmName = in.readString();
        farmLocation = in.readParcelable(Location.class.getClassLoader());
        farmLGA = in.readString();
        farmState = in.readString();
        farmCountry = in.readString();
        farmStatus = in.readString();
        farmStartDate = in.readString();
        farmCommodity = in.readParcelable(Commodity.class.getClassLoader());
        farmRevenue = in.readDouble();
    }

    public static final Creator<Farm> CREATOR = new Creator<Farm>() {
        @Override
        public Farm createFromParcel(Parcel in) {
            return new Farm(in);
        }

        @Override
        public Farm[] newArray(int size) {
            return new Farm[size];
        }
    };

    public int getFarmID() {
        return farmID;
    }

    public void setFarmID(int farmID) {
        this.farmID = farmID;
    }

    public int getFarmProfID() {
        return farmProfID;
    }

    public void setFarmProfID(int farmProfID) {
        this.farmProfID = farmProfID;
    }

    public int getFarmMarketID() {
        return farmMarketID;
    }

    public void setFarmMarketID(int farmMarketID) {
        this.farmMarketID = farmMarketID;
    }

    public int getFarmBusinessID() {
        return farmBusinessID;
    }

    public void setFarmBusinessID(int farmBusinessID) {
        this.farmBusinessID = farmBusinessID;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public Location getFarmLocation() {
        return farmLocation;
    }

    public void setFarmLocation(Location farmLocation) {
        this.farmLocation = farmLocation;
    }

    public String getFarmLGA() {
        return farmLGA;
    }

    public void setFarmLGA(String farmLGA) {
        this.farmLGA = farmLGA;
    }

    public String getFarmState() {
        return farmState;
    }

    public void setFarmState(String farmState) {
        this.farmState = farmState;
    }

    public String getFarmCountry() {
        return farmCountry;
    }

    public void setFarmCountry(String farmCountry) {
        this.farmCountry = farmCountry;
    }

    public String getFarmStatus() {
        return farmStatus;
    }

    public void setFarmStatus(String farmStatus) {
        this.farmStatus = farmStatus;
    }

    public FarmCommodity getFarmCommodity() {
        return farmCommodity;
    }

    public void setFarmCommodity(FarmCommodity farmCommodity) {
        this.farmCommodity = farmCommodity;
    }

    public double getFarmRevenue() {
        return farmRevenue;
    }

    public void setFarmRevenue(double farmRevenue) {
        this.farmRevenue = farmRevenue;
    }

    public ArrayList<FarmCommodity> getFarmCommodityList() {
        return farmCommodityList;
    }

    public void setFarmCommodityList(ArrayList<FarmCommodity> farmCommodityList) {
        this.farmCommodityList = farmCommodityList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(farmID);
        parcel.writeInt(farmProfID);
        parcel.writeInt(farmMarketID);
        parcel.writeInt(farmBusinessID);
        parcel.writeString(farmName);
        parcel.writeParcelable(farmLocation, i);
        parcel.writeString(farmLGA);
        parcel.writeString(farmState);
        parcel.writeString(farmCountry);
        parcel.writeString(farmStatus);
        parcel.writeString(farmStartDate);
        parcel.writeParcelable(farmCommodity, i);
        parcel.writeDouble(farmRevenue);
    }

    public String getFarmStartDate() {
        return farmStartDate;
    }

    public void setFarmStartDate(String farmStartDate) {
        this.farmStartDate = farmStartDate;
    }

    public int getFarmLogo() {
        return farmLogo;
    }

    public void setFarmLogo(int farmLogo) {
        this.farmLogo = farmLogo;
    }

    public String getFarmType() {
        return farmType;
    }

    public void setFarmType(String farmType) {
        this.farmType = farmType;
    }

    public ArrayList<FarmTranx> getFarmTranxList() {
        return farmTranxList;
    }

    public void setFarmTranxList(ArrayList<FarmTranx> farmTranxList) {
        this.farmTranxList = farmTranxList;
    }
}
