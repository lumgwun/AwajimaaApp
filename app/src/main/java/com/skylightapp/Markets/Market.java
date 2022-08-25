package com.skylightapp.Markets;

import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Commodity;

import java.io.Serializable;
import java.util.ArrayList;

public class Market implements Serializable, Parcelable {
    private int marketID;
    private int marketProfID;
    private String marketName;
    private  String marketAddress;
    private String marketType;
    private String marketState;
    private String marketLGA;
    private String marketTown;
    private  double marketLng;
    private  double marketLat;
    private int commodityCount;
    private int marketLogo;
    private ArrayList<Commodity> commodityArrayList;
    private ArrayList<MarketDays> marketMDaysArrayList;
    private ArrayList<Business> marketBizArrayList;
    private ArrayList<BusinessDeal> marketBizDealList;
    private ArrayList<BizDealAccount> marketBizDealAcctList;
    private ArrayList<MarketCustomer> marketCustomers;
    private ArrayList<MarketAdmin> marketAdminArrayList;
    private ArrayList<MarketTranx> marketTranxArrayList;

    public Market () {
        super();

    }

    public Market(int logo, String creek_road_market, String fish_and_sea_food_market, String phalga, String rivers_state) {

    }

    protected Market(Parcel in) {
        marketID = in.readInt();
        marketProfID = in.readInt();
        marketName = in.readString();
        marketAddress = in.readString();
        marketType = in.readString();
        marketState = in.readString();
        marketLGA = in.readString();
        marketTown = in.readString();
        marketLng = in.readDouble();
        marketLat = in.readDouble();
        commodityCount = in.readInt();
        marketLogo = in.readInt();
        marketBizArrayList = in.createTypedArrayList(Business.CREATOR);
        marketBizDealList = in.createTypedArrayList(BusinessDeal.CREATOR);
        marketBizDealAcctList = in.createTypedArrayList(BizDealAccount.CREATOR);
    }

    public static final Creator<Market> CREATOR = new Creator<Market>() {
        @Override
        public Market createFromParcel(Parcel in) {
            return new Market(in);
        }

        @Override
        public Market[] newArray(int size) {
            return new Market[size];
        }
    };

    public ArrayList<Commodity> getCommodityArrayList() {
        return commodityArrayList;
    }

    public void setCommodityArrayList(ArrayList<Commodity> commodityArrayList) {
        this.commodityArrayList = commodityArrayList;
    }

    public ArrayList<MarketDays> getMarketMDaysArrayList() {
        return marketMDaysArrayList;
    }

    public void setMarketMDaysArrayList(ArrayList<MarketDays> marketMDaysArrayList) {
        this.marketMDaysArrayList = marketMDaysArrayList;
    }

    public ArrayList<MarketCustomer> getMarketCustomers() {
        return marketCustomers;
    }

    public void setMarketCustomers(ArrayList<MarketCustomer> marketCustomers) {
        this.marketCustomers = marketCustomers;
    }

    public ArrayList<MarketAdmin> getMarketAdminArrayList() {
        return marketAdminArrayList;
    }

    public void setMarketAdminArrayList(ArrayList<MarketAdmin> marketAdminArrayList) {
        this.marketAdminArrayList = marketAdminArrayList;
    }

    public int getMarketLogo() {
        return marketLogo;
    }

    public void setMarketLogo(int marketLogo) {
        this.marketLogo = marketLogo;
    }

    public double getMarketLat() {
        return marketLat;
    }

    public void setMarketLat(double marketLat) {
        this.marketLat = marketLat;
    }

    public double getMarketLng() {
        return marketLng;
    }

    public void setMarketLng(double marketLng) {
        this.marketLng = marketLng;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getMarketAddress() {
        return marketAddress;
    }

    public void setMarketAddress(String marketAddress) {
        this.marketAddress = marketAddress;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketTown() {
        return marketTown;
    }

    public void setMarketTown(String marketTown) {
        this.marketTown = marketTown;
    }

    public String getMarketLGA() {
        return marketLGA;
    }

    public void setMarketLGA(String marketLGA) {
        this.marketLGA = marketLGA;
    }

    public String getMarketState() {
        return marketState;
    }

    public void setMarketState(String marketState) {
        this.marketState = marketState;
    }

    public int getCommodityCount() {
        return commodityCount;
    }

    public void setCommodityCount(int commodityCount) {
        this.commodityCount = commodityCount;
    }

    public int getMarketID() {
        return marketID;
    }

    public void setMarketID(int marketID) {
        this.marketID = marketID;
    }

    public ArrayList<MarketTranx> getMarketTranxArrayList() {
        return marketTranxArrayList;
    }

    public void setMarketTranxArrayList(ArrayList<MarketTranx> marketTranxArrayList) {
        this.marketTranxArrayList = marketTranxArrayList;
    }

    public int getMarketProfID() {
        return marketProfID;
    }

    public void setMarketProfID(int marketProfID) {
        this.marketProfID = marketProfID;
    }

    public ArrayList<BusinessDeal> getMarketBizDealList() {
        return marketBizDealList;
    }

    public void setMarketBizDealList(ArrayList<BusinessDeal> marketBizDealList) {
        this.marketBizDealList = marketBizDealList;
    }

    public ArrayList<Business> getMarketBizArrayList() {
        return marketBizArrayList;
    }

    public void setMarketBizArrayList(ArrayList<Business> marketBizArrayList) {
        this.marketBizArrayList = marketBizArrayList;
    }

    public ArrayList<BizDealAccount> getMarketBizDealAcctList() {
        return marketBizDealAcctList;
    }

    public void setMarketBizDealAcctList(ArrayList<BizDealAccount> marketBizDealAcctList) {
        this.marketBizDealAcctList = marketBizDealAcctList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(marketID);
        parcel.writeInt(marketProfID);
        parcel.writeString(marketName);
        parcel.writeString(marketAddress);
        parcel.writeString(marketType);
        parcel.writeString(marketState);
        parcel.writeString(marketLGA);
        parcel.writeString(marketTown);
        parcel.writeDouble(marketLng);
        parcel.writeDouble(marketLat);
        parcel.writeInt(commodityCount);
        parcel.writeInt(marketLogo);
        parcel.writeTypedList(marketBizArrayList);
        parcel.writeTypedList(marketBizDealList);
        parcel.writeTypedList(marketBizDealAcctList);
    }
}
