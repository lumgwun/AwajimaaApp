package com.skylightapp.Markets;

import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Account;

import java.io.Serializable;
import java.util.ArrayList;

public class BusinessDeal implements Serializable, Parcelable {
    private int dealID;
    private  String dealTittle;
    private int dealFromProfileID;
    private int dealToProfileID;
    private Business toBiz;
    private Business fromBiz;
    private int dealQty;
    private Account dealSellerAcct;
    private Account dealBuyerAcct;
    private double dealCostOfProduct;
    private double dealCostOfInsurance;
    private double dealShippingCost;
    private  LogisticManager dealLogisticManager;
    private InsurancePolicy dealInsuranceP;
    private int dealCode;
    private  String dealStartDate;
    private  String dealEndDate;
    private BizDealAccount bizDealBDAccount;
    private ArrayList<Business> bizDealBusS;
    private ArrayList<Market> bizDealMarkets;
    private ArrayList<MarketAdmin> bizDealMarketAdmins;
    private ArrayList<MarketTranx> bizDealMarketTranxS;
    private ArrayList<BizDealMileStone> bizDealMileStones;
    private ArrayList<BusinessDealLoan> bizDealLoans;
    private ArrayList<BizDealRemittance> dealBizDealRemittances;

    public BusinessDeal () {
        super();

    }

    protected BusinessDeal(Parcel in) {
        dealID = in.readInt();
        dealTittle = in.readString();
        dealFromProfileID = in.readInt();
        dealToProfileID = in.readInt();
        toBiz = in.readParcelable(Business.class.getClassLoader());
        fromBiz = in.readParcelable(Business.class.getClassLoader());
        dealQty = in.readInt();
        dealSellerAcct = in.readParcelable(Account.class.getClassLoader());
        dealBuyerAcct = in.readParcelable(Account.class.getClassLoader());
        dealCostOfProduct = in.readDouble();
        dealCostOfInsurance = in.readDouble();
        dealShippingCost = in.readDouble();
        dealCode = in.readInt();
        dealStartDate = in.readString();
        dealEndDate = in.readString();
        bizDealBusS = in.createTypedArrayList(Business.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dealID);
        dest.writeString(dealTittle);
        dest.writeInt(dealFromProfileID);
        dest.writeInt(dealToProfileID);
        dest.writeParcelable(toBiz, flags);
        dest.writeParcelable(fromBiz, flags);
        dest.writeInt(dealQty);
        dest.writeParcelable(dealSellerAcct, flags);
        dest.writeParcelable(dealBuyerAcct, flags);
        dest.writeDouble(dealCostOfProduct);
        dest.writeDouble(dealCostOfInsurance);
        dest.writeDouble(dealShippingCost);
        dest.writeInt(dealCode);
        dest.writeString(dealStartDate);
        dest.writeString(dealEndDate);
        dest.writeTypedList(bizDealBusS);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusinessDeal> CREATOR = new Creator<BusinessDeal>() {
        @Override
        public BusinessDeal createFromParcel(Parcel in) {
            return new BusinessDeal(in);
        }

        @Override
        public BusinessDeal[] newArray(int size) {
            return new BusinessDeal[size];
        }
    };

    public int getDealID() {
        return dealID;
    }

    public void setDealID(int dealID) {
        this.dealID = dealID;
    }

    public String getDealTittle() {
        return dealTittle;
    }

    public void setDealTittle(String dealTittle) {
        this.dealTittle = dealTittle;
    }

    public int getDealFromProfileID() {
        return dealFromProfileID;
    }

    public void setDealFromProfileID(int dealFromProfileID) {
        this.dealFromProfileID = dealFromProfileID;
    }

    public int getDealToProfileID() {
        return dealToProfileID;
    }

    public void setDealToProfileID(int dealToProfileID) {
        this.dealToProfileID = dealToProfileID;
    }

    public Business getToBiz() {
        return toBiz;
    }

    public void setToBiz(Business toBiz) {
        this.toBiz = toBiz;
    }

    public Business getFromBiz() {
        return fromBiz;
    }

    public void setFromBiz(Business fromBiz) {
        this.fromBiz = fromBiz;
    }

    public int getDealQty() {
        return dealQty;
    }

    public void setDealQty(int dealQty) {
        this.dealQty = dealQty;
    }

    public Account getDealSellerAcct() {
        return dealSellerAcct;
    }

    public void setDealSellerAcct(Account dealSellerAcct) {
        this.dealSellerAcct = dealSellerAcct;
    }

    public Account getDealBuyerAcct() {
        return dealBuyerAcct;
    }

    public void setDealBuyerAcct(Account dealBuyerAcct) {
        this.dealBuyerAcct = dealBuyerAcct;
    }

    public double getDealCostOfProduct() {
        return dealCostOfProduct;
    }

    public void setDealCostOfProduct(double dealCostOfProduct) {
        this.dealCostOfProduct = dealCostOfProduct;
    }

    public double getDealCostOfInsurance() {
        return dealCostOfInsurance;
    }

    public void setDealCostOfInsurance(double dealCostOfInsurance) {
        this.dealCostOfInsurance = dealCostOfInsurance;
    }

    public double getDealShippingCost() {
        return dealShippingCost;
    }

    public void setDealShippingCost(double dealShippingCost) {
        this.dealShippingCost = dealShippingCost;
    }

    public LogisticManager getDealLogisticManager() {
        return dealLogisticManager;
    }

    public void setDealLogisticManager(LogisticManager dealLogisticManager) {
        this.dealLogisticManager = dealLogisticManager;
    }

    public InsurancePolicy getDealInsuranceP() {
        return dealInsuranceP;
    }

    public void setDealInsuranceP(InsurancePolicy dealInsuranceP) {
        this.dealInsuranceP = dealInsuranceP;
    }

    public int getDealCode() {
        return dealCode;
    }

    public void setDealCode(int dealCode) {
        this.dealCode = dealCode;
    }

    public String getDealStartDate() {
        return dealStartDate;
    }

    public void setDealStartDate(String dealStartDate) {
        this.dealStartDate = dealStartDate;
    }

    public String getDealEndDate() {
        return dealEndDate;
    }

    public void setDealEndDate(String dealEndDate) {
        this.dealEndDate = dealEndDate;
    }

    public ArrayList<Business> getBizDealBusS() {
        return bizDealBusS;
    }

    public void setBizDealBusS(ArrayList<Business> bizDealBusS) {
        this.bizDealBusS = bizDealBusS;
    }

    public ArrayList<Market> getBizDealMarkets() {
        return bizDealMarkets;
    }

    public void setBizDealMarkets(ArrayList<Market> bizDealMarkets) {
        this.bizDealMarkets = bizDealMarkets;
    }

    public ArrayList<MarketAdmin> getBizDealMarketAdmins() {
        return bizDealMarketAdmins;
    }

    public void setBizDealMarketAdmins(ArrayList<MarketAdmin> bizDealMarketAdmins) {
        this.bizDealMarketAdmins = bizDealMarketAdmins;
    }

    public ArrayList<MarketTranx> getBizDealMarketTranxS() {
        return bizDealMarketTranxS;
    }

    public void setBizDealMarketTranxS(ArrayList<MarketTranx> bizDealMarketTranxS) {
        this.bizDealMarketTranxS = bizDealMarketTranxS;
    }

    public ArrayList<BizDealMileStone> getBizDealMileStones() {
        return bizDealMileStones;
    }

    public void setBizDealMileStones(ArrayList<BizDealMileStone> bizDealMileStones) {
        this.bizDealMileStones = bizDealMileStones;
    }

    public ArrayList<BusinessDealLoan> getBizDealLoans() {
        return bizDealLoans;
    }

    public void setBizDealLoans(ArrayList<BusinessDealLoan> bizDealLoans) {
        this.bizDealLoans = bizDealLoans;
    }

    public BizDealAccount getBizDealBDAccount() {
        return bizDealBDAccount;
    }

    public void setBizDealBDAccount(BizDealAccount bizDealBDAccount) {
        this.bizDealBDAccount = bizDealBDAccount;
    }

    public ArrayList<BizDealRemittance> getDealBizDealRemittances() {
        return dealBizDealRemittances;
    }

    public void setDealBizDealRemittances(ArrayList<BizDealRemittance> dealBizDealRemittances) {
        this.dealBizDealRemittances = dealBizDealRemittances;
    }
}
