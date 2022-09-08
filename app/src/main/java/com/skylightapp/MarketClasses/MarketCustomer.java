package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Markets.BizDealAccount;
import com.skylightapp.Markets.MarketTranx;

import java.io.Serializable;
import java.util.ArrayList;

public class MarketCustomer implements Serializable, Parcelable {
    private int marketCusID;
    private Profile marketCusProf;
    private String marketCusStatus;
    private ArrayList<Market> marketCusMarkets;
    private ArrayList<Business> marketCusBizS;
    private ArrayList<BusinessDeal> marketCusBizDeals;
    private ArrayList<BusinessDealLoan> marketCusBizDealLoans;
    private ArrayList<InsurancePolicy> marketCusInsurances;
    private ArrayList<BizDealRemittance> marketCusRemitances;
    private ArrayList<BizDealAccount> marketCusBizDealAccts;
    private ArrayList<MarketTranx> marketCusMarketTranxS;

    public MarketCustomer () {
        super();

    }


    protected MarketCustomer(Parcel in) {
        marketCusID = in.readInt();
        marketCusProf = in.readParcelable(Profile.class.getClassLoader());
        marketCusStatus = in.readString();
        marketCusMarkets = in.createTypedArrayList(Market.CREATOR);
        marketCusBizS = in.createTypedArrayList(Business.CREATOR);
        marketCusBizDeals = in.createTypedArrayList(BusinessDeal.CREATOR);
        marketCusBizDealLoans = in.createTypedArrayList(BusinessDealLoan.CREATOR);
        marketCusRemitances = in.createTypedArrayList(BizDealRemittance.CREATOR);
        marketCusBizDealAccts = in.createTypedArrayList(BizDealAccount.CREATOR);
    }

    public static final Creator<MarketCustomer> CREATOR = new Creator<MarketCustomer>() {
        @Override
        public MarketCustomer createFromParcel(Parcel in) {
            return new MarketCustomer(in);
        }

        @Override
        public MarketCustomer[] newArray(int size) {
            return new MarketCustomer[size];
        }
    };

    public int getMarketCusID() {
        return marketCusID;
    }

    public void setMarketCusID(int marketCusID) {
        this.marketCusID = marketCusID;
    }

    public Profile getMarketCusProf() {
        return marketCusProf;
    }

    public void setMarketCusProf(Profile marketCusProf) {
        this.marketCusProf = marketCusProf;
    }

    public ArrayList<Market> getMarketCusMarkets() {
        return marketCusMarkets;
    }

    public void setMarketCusMarkets(ArrayList<Market> marketCusMarkets) {
        this.marketCusMarkets = marketCusMarkets;
    }

    public ArrayList<MarketTranx> getMarketCusMarketTranxS() {
        return marketCusMarketTranxS;
    }

    public void setMarketCusMarketTranxS(ArrayList<MarketTranx> marketCusMarketTranxS) {
        this.marketCusMarketTranxS = marketCusMarketTranxS;
    }

    public ArrayList<BusinessDeal> getMarketCusBizDeals() {
        return marketCusBizDeals;
    }

    public void setMarketCusBizDeals(ArrayList<BusinessDeal> marketCusBizDeals) {
        this.marketCusBizDeals = marketCusBizDeals;
    }

    public ArrayList<Business> getMarketCusBizS() {
        return marketCusBizS;
    }

    public void setMarketCusBizS(ArrayList<Business> marketCusBizS) {
        this.marketCusBizS = marketCusBizS;
    }

    public String getMarketCusStatus() {
        return marketCusStatus;
    }

    public void setMarketCusStatus(String marketCusStatus) {
        this.marketCusStatus = marketCusStatus;
    }

    public ArrayList<BusinessDealLoan> getMarketCusBizDealLoans() {
        return marketCusBizDealLoans;
    }

    public void setMarketCusBizDealLoans(ArrayList<BusinessDealLoan> marketCusBizDealLoans) {
        this.marketCusBizDealLoans = marketCusBizDealLoans;
    }

    public ArrayList<InsurancePolicy> getMarketCusInsurances() {
        return marketCusInsurances;
    }

    public void setMarketCusInsurances(ArrayList<InsurancePolicy> marketCusInsurances) {
        this.marketCusInsurances = marketCusInsurances;
    }

    public ArrayList<BizDealRemittance> getMarketCusRemitances() {
        return marketCusRemitances;
    }

    public void setMarketCusRemitances(ArrayList<BizDealRemittance> marketCusRemitances) {
        this.marketCusRemitances = marketCusRemitances;
    }

    public ArrayList<BizDealAccount> getMarketCusBizDealAccts() {
        return marketCusBizDealAccts;
    }

    public void setMarketCusBizDealAccts(ArrayList<BizDealAccount> marketCusBizDealAccts) {
        this.marketCusBizDealAccts = marketCusBizDealAccts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(marketCusID);
        parcel.writeParcelable(marketCusProf, i);
        parcel.writeString(marketCusStatus);
        parcel.writeTypedList(marketCusMarkets);
        parcel.writeTypedList(marketCusBizS);
        parcel.writeTypedList(marketCusBizDeals);
        parcel.writeTypedList(marketCusBizDealLoans);
        parcel.writeTypedList(marketCusRemitances);
        parcel.writeTypedList(marketCusBizDealAccts);
    }
}
