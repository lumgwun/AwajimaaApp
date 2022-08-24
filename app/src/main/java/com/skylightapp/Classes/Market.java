package com.skylightapp.Classes;

import android.net.Uri;

import com.skylightapp.Markets.MarketAdmin;
import com.skylightapp.Markets.MarketTransactions;

import java.util.ArrayList;

public class Market {
    private int marketID;
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
    private ArrayList<MarketDays> marketDaysArrayList;
    private ArrayList<MarketCustomer> marketCustomers;
    private ArrayList<MarketAdmin> marketAdminArrayList;
    private ArrayList<MarketTransactions> marketTransactionsArrayList;

    public Market(int logo, String creek_road_market, String fish_and_sea_food_market, String phalga, String rivers_state) {

    }

    public ArrayList<Commodity> getCommodityArrayList() {
        return commodityArrayList;
    }

    public void setCommodityArrayList(ArrayList<Commodity> commodityArrayList) {
        this.commodityArrayList = commodityArrayList;
    }

    public ArrayList<MarketDays> getMarketDaysArrayList() {
        return marketDaysArrayList;
    }

    public void setMarketDaysArrayList(ArrayList<MarketDays> marketDaysArrayList) {
        this.marketDaysArrayList = marketDaysArrayList;
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
}
