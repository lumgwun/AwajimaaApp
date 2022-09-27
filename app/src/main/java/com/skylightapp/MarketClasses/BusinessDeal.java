package com.skylightapp.MarketClasses;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.blongho.country_data.Currency;
import com.skylightapp.Classes.Account;
import com.skylightapp.Markets.MarketTranx;

import java.io.Serializable;
import java.util.ArrayList;

import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class BusinessDeal implements Serializable, Parcelable {
    private int dealID;
    private  String dealTittle;
    private int dealAcctNumber;
    private int dealFromProfileID;
    private int dealToProfileID;
    private BusinessOthers toBiz;
    private BusinessOthers fromBiz;
    private int dealToBizID;
    private int dealFromBizID;
    private int dealQty;
    private int dealNoOfMileStone;
    private Account dealSellerAcct;
    private Account dealBuyerAcct;
    private double dealCostOfProduct;
    private double dealCostOfInsurance;
    private double dealBalance;
    private double dealShippingCost;
    private LogisticManager dealLogisticManager;
    private InsurancePolicy dealInsuranceP;
    private int dealCode;
    private  String dealStartDate;
    private  String dealEndDate;
    private  String dealStatus;
    private  String dealType;
    private Currency bizDealCurrency;
    private BizDealAccount bizDealBDAccount;
    private ArrayList<BusinessOthers> bizDealBusS;
    private ArrayList<Market> bizDealMarkets;
    private ArrayList<MarketAdmin> bizDealMarketAdmins;
    private ArrayList<MarketTranx> bizDealMarketTranxS;
    private ArrayList<BizDealMileStone> bizDealMileStones;
    private ArrayList<BusinessDealLoan> bizDealLoans;
    private ArrayList<BusinessDealSub> bizDealSubs;
    private ArrayList<BusinessDealDoc> bizDealDocs;
    private ArrayList<BizDealTimeLine> bizDealBDTimeLines;

    private ArrayList<BizDealRemittance> dealBizDealRemittances;
    private ArrayList<BizDealAccount> bizDealAccts;

    public BusinessDeal () {
        super();

    }

    public static final String BIZ_DEAL_TABLE = "bizDeal_Table";
    public static final String BIZ_DEAL_ID = "bizDeal_id";
    public static final String BIZ_DEAL_TITTLE = "bizDeal_Tittle";
    public static final String BIZ_DEAL_TO_PROF_ID = "bizDeal_To_Prof_ID";
    public static final String BIZ_DEAL_FROM_PROF_ID = "bizDeal_From_Prof_ID";
    public static final String BIZ_DEAL_PROD_AMOUNT = "bizDeal_Amount";
    public static final String BIZ_DEAL_SHIPPING_FEE = "bizDeal_shipping_Fee";
    public static final String BIZ_DEAL_INS_FEE = "bizDeal_Ins_Fee";
    public static final String BIZ_DEAL_TYPE = "bizDeal_Type";
    public static final String BIZ_DEAL_START_DATE = "bizDeal_Start_Date";
    public static final String BIZ_DEAL_FROM_ACCT_ID = "bizDeal_From_Acct_ID";
    public static final String BIZ_DEAL_TO_ACCT_ID = "bizDeal_To_Acct_ID";
    public static final String BIZ_DEAL_FROM_B_ID = "bizDeal_From_Biz_ID";
    public static final String BIZ_DEAL_TO_BIZ_ID = "bizDeal_To_Biz_ID";

    public static final String BIZ_DEAL_QTY = "bizDeal_QTY";
    public static final String BIZ_DEAL_NO_OF_MILESTONE = "bizDeal_No_Of_MS";
    public static final String BIZ_DEAL_CODE = "bizDeal_Code";
    public static final String BIZ_DEAL_END_DATE = "bizDeal_End_Date";
    public static final String BIZ_DEAL_STATUS = "bizDeal_Status";
    public static final String BIZ_DEAL_ACCT_NO = "bizDeal_Acct_No";

    public static final String BIZ_DEAL_CODE_TABLE = "bizDeal_Code_Table";

    public static final String BIZ_DEAL_CODE_DEAL_ID = "bizDeal_Code_Deal_Forgn_ID";
    public static final String BIZ_DEAL_NEW_CODE = "bizDeal_New_Code";
    public static final String BIZ_DEAL_NEW_CODE_ID = "bizDeal_New_Code_ID";

    public static final String CREATE_BIZ_DEAL_CODE_TABLE = "CREATE TABLE " + BIZ_DEAL_CODE_TABLE + " (" + BIZ_DEAL_NEW_CODE_ID + " INTEGER, " +
            BIZ_DEAL_CODE_DEAL_ID + " INTEGER, " + BIZ_DEAL_NEW_CODE + " INTEGER , " +
            "PRIMARY KEY(" + BIZ_DEAL_NEW_CODE_ID + "), " + "FOREIGN KEY(" + BIZ_DEAL_CODE_DEAL_ID + ") REFERENCES " + BIZ_DEAL_TABLE + "(" + BIZ_DEAL_ID + "))";



    public static final String CREATE_BIZ_DEAL_TABLE = "CREATE TABLE IF NOT EXISTS " + BIZ_DEAL_TABLE + " (" + BIZ_DEAL_ID + " INTEGER, " + BIZ_DEAL_ACCT_NO + " INTEGER , " +
            BIZ_DEAL_TO_PROF_ID + " INTEGER , " + BIZ_DEAL_FROM_PROF_ID + " INTEGER , " + BIZ_DEAL_FROM_ACCT_ID + " INTEGER, " + BIZ_DEAL_TO_ACCT_ID + " INTEGER, " +
            BIZ_DEAL_TITTLE + " TEXT, " + BIZ_DEAL_PROD_AMOUNT + " REAL, " + BIZ_DEAL_SHIPPING_FEE + " REAL, " + BIZ_DEAL_QTY + " REAL, " +
            BIZ_DEAL_INS_FEE + " TEXT, " + BIZ_DEAL_TYPE + " TEXT, " + BIZ_DEAL_START_DATE + " TEXT, "+ BIZ_DEAL_NO_OF_MILESTONE + BIZ_DEAL_CODE + " TEXT, "+  BIZ_DEAL_STATUS + " TEXT, "+ BIZ_DEAL_FROM_B_ID + " INTEGER, "+ BIZ_DEAL_TO_BIZ_ID + " INTEGER, " + "PRIMARY KEY(" +BIZ_DEAL_ID + "), "+"FOREIGN KEY(" + BIZ_DEAL_FROM_ACCT_ID  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + BIZ_DEAL_TO_ACCT_ID  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + BIZ_DEAL_TO_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + BIZ_DEAL_ACCT_NO  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + BIZ_DEAL_FROM_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";


    public void addBizDealBDTimeline(int id, int bdTimelineProfID,String title,String details, String time,String status) {
        bizDealBDTimeLines = new ArrayList<>();
        BizDealTimeLine bizDealTimeLine = new BizDealTimeLine(id,bdTimelineProfID,title, details, time,status);
        bizDealBDTimeLines.add(bizDealTimeLine);
    }
    public void addBizDealSubDeal(int id, int suBDealCode,String title,String desc, double amount, String Currency,int milestones,String startTime,String endTime,String status) {
        bizDealSubs = new ArrayList<>();
        BusinessDealSub businessDealSub = new BusinessDealSub(id,suBDealCode,title, desc, amount,Currency,milestones,startTime,endTime,status);
        bizDealSubs.add(businessDealSub);
    }
    public void addBizDealMarketTranx(int id, String title,double amount, String from, String to,int code,String date,String status) {
        bizDealMarketTranxS = new ArrayList<>();
        MarketTranx marketTranx = new MarketTranx(id,title, amount, from,to,code,date,status);
        bizDealMarketTranxS.add(marketTranx);
    }
    public void addBizDealDoc(int id, String title,String desc, Uri documentLink, String status) {
        bizDealDocs = new ArrayList<>();
        BusinessDealDoc businessDealDoc = new BusinessDealDoc(id,title, desc, documentLink,status);
        bizDealDocs.add(businessDealDoc);
    }

    protected BusinessDeal(Parcel in) {
        dealID = in.readInt();
        dealTittle = in.readString();
        dealFromProfileID = in.readInt();
        dealToProfileID = in.readInt();
        toBiz = in.readParcelable(BusinessOthers.class.getClassLoader());
        fromBiz = in.readParcelable(BusinessOthers.class.getClassLoader());
        dealQty = in.readInt();
        dealSellerAcct = in.readParcelable(Account.class.getClassLoader());
        dealBuyerAcct = in.readParcelable(Account.class.getClassLoader());
        dealCostOfProduct = in.readDouble();
        dealCostOfInsurance = in.readDouble();
        dealShippingCost = in.readDouble();
        dealCode = in.readInt();
        dealStartDate = in.readString();
        dealEndDate = in.readString();
        bizDealBusS = in.createTypedArrayList(BusinessOthers.CREATOR);
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

    public BusinessOthers getToBiz() {
        return toBiz;
    }

    public void setToBiz(BusinessOthers toBiz) {
        this.toBiz = toBiz;
    }

    public BusinessOthers getFromBiz() {
        return fromBiz;
    }

    public void setFromBiz(BusinessOthers fromBiz) {
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

    public ArrayList<BusinessOthers> getBizDealBusS() {
        return bizDealBusS;
    }

    public void setBizDealBusS(ArrayList<BusinessOthers> bizDealBusS) {
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

    public ArrayList<BusinessDealSub> getBizDealSubs() {
        return bizDealSubs;
    }

    public void setBizDealSubs(ArrayList<BusinessDealSub> bizDealSubs) {
        this.bizDealSubs = bizDealSubs;
    }

    public ArrayList<BusinessDealDoc> getBizDealDocs() {
        return bizDealDocs;
    }

    public void setBizDealDocs(ArrayList<BusinessDealDoc> bizDealDocs) {
        this.bizDealDocs = bizDealDocs;
    }

    public ArrayList<BizDealTimeLine> getBizDealBDTimeLines() {
        return bizDealBDTimeLines;
    }

    public void setBizDealBDTimeLines(ArrayList<BizDealTimeLine> bizDealBDTimeLines) {
        this.bizDealBDTimeLines = bizDealBDTimeLines;
    }

    public int getDealFromBizID() {
        return dealFromBizID;
    }

    public void setDealFromBizID(int dealFromBizID) {
        this.dealFromBizID = dealFromBizID;
    }

    public int getDealToBizID() {
        return dealToBizID;
    }

    public void setDealToBizID(int dealToBizID) {
        this.dealToBizID = dealToBizID;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public int getDealNoOfMileStone() {
        return dealNoOfMileStone;
    }

    public void setDealNoOfMileStone(int dealNoOfMileStone) {
        this.dealNoOfMileStone = dealNoOfMileStone;
    }

    public int getDealAcctNumber() {
        return dealAcctNumber;
    }

    public void setDealAcctNumber(int dealAcctNumber) {
        this.dealAcctNumber = dealAcctNumber;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public Currency getBizDealCurrency() {
        return bizDealCurrency;
    }

    public void setBizDealCurrency(Currency bizDealCurrency) {
        this.bizDealCurrency = bizDealCurrency;
    }

    public double getDealBalance() {
        return dealBalance;
    }

    public void setDealBalance(double dealBalance) {
        this.dealBalance = dealBalance;
    }

    public ArrayList<BizDealAccount> getBizDealAccts() {
        return bizDealAccts;
    }

    public void setBizDealAccts(ArrayList<BizDealAccount> bizDealAccts) {
        this.bizDealAccts = bizDealAccts;
    }
}
