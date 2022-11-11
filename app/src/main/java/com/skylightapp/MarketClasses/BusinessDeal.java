package com.skylightapp.MarketClasses;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.blongho.country_data.Currency;
import com.quickblox.chat.model.QBChatDialog;
import com.skylightapp.Classes.Account;

import java.io.Serializable;
import java.util.ArrayList;

import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class BusinessDeal implements Serializable, Parcelable {
    private int dealID;
    private  String dealTittle;
    private long dealAcctNumber;
    private int dealFromProfileID;
    private int dealToProfileID;
    private MarketBusiness toBiz;
    private MarketBusiness fromBiz;
    private long dealToBizID;
    private long dealFromBizID;
    private int dealQty;
    private int dealNoOfMileStone;
    private Account dealSellerAcct;
    private Account dealBuyerAcct;
    private long dealToAcctNoID;
    private long dealFromAcctNoID;
    private double dealCostOfProduct;
    private double dealCostOfInsurance;
    private double dealBalance;
    private double dealShippingCost;
    private double dealTotalAmount;
    private  String dealCurrency;
    private LogisticEntity dealLogisticEntity;
    private InsurancePolicy dealInsuranceP;
    private int dealCode;
    private  String dealStartDate;
    private  String dealEndDate;
    private  String dealDateCreated;
    private  String dealStatus;
    private  String dealType;
    private boolean isHost;
    private boolean isPartner;
    private boolean isLogistics;
    private Currency bizDealCurrency;
    private  String bizDealDispute;
    private boolean isCallAllowed;
    private boolean isRequestAccepted;
    private BizDealAccount bizDealBDAccount;
    private ArrayList<MarketBusiness> bizDealBusS;
    private ArrayList<Market> bizDealMarkets;
    private ArrayList<MarketAdmin> bizDealMarketAdmins;
    private ArrayList<MarketTranx> bizDealMarketTranxS;
    private ArrayList<BizDealMileStone> bizDealMileStones;
    private ArrayList<BusinessDealLoan> bizDealLoans;
    private ArrayList<BusinessDealSub> bizDealSubs;
    private ArrayList<BusinessDealDoc> bizDealDocs;
    private ArrayList<BizDealTimeLine> bizDealBDTimeLines;
    private ArrayList<BizDealPartner> bizDealPartners;
    private ArrayList<QBChatDialog> bizDealChatDialogs;

    private ArrayList<BizDealRemittance> dealBizDealRemittances;
    private ArrayList<BizDealAccount> bizDealAccts;

    public BusinessDeal(int bizDealID, long bizDealAcctNo, int bizDealToProfID, int bizDealFromProfID, int bizDealFromAcctID, int bizDealToAcctID, String tittle, double productAmt, double shippingAmt, int qty, double insuranceAmt, String dealType, String startDate, String status, int milestones, int code, long fromBizID, long toBizID, String isHost, String isPartner, String isLogistics, String dispute, String dateCreated, String dateEnded, double total,String currency) {
        this.dealID = bizDealID;
        this.dealAcctNumber = bizDealAcctNo;
        this.dealTotalAmount = total;
        this.dealCurrency = currency;
        this.dealToProfileID = bizDealToProfID;
        this.dealFromProfileID = bizDealFromProfID;
        this.dealFromAcctNoID = bizDealFromAcctID;
        this.dealToAcctNoID = bizDealToAcctID;
        this.dealTittle = tittle;
        this.dealCostOfProduct = productAmt;
        this.dealShippingCost = shippingAmt;
        this.dealQty = qty;
        this.dealCostOfInsurance = insuranceAmt;
        this.dealStatus = status;
        this.dealType = dealType;
        this.dealStartDate = startDate;
        this.dealNoOfMileStone = milestones;
        this.dealCode = code;
        this.dealFromBizID = fromBizID;
        this.dealToBizID = toBizID;
        this.isHost = Boolean.parseBoolean(isHost);
        this.isPartner = Boolean.parseBoolean(isPartner);
        this.isLogistics = Boolean.parseBoolean(isLogistics);
        this.bizDealDispute = dispute;
        this.dealDateCreated = dateCreated;
        this.dealEndDate = dateEnded;
    }

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
    public static final String BIZ_DEAL_CREATED_DATE = "bizDeal_Created_Date";
    public static final String BIZ_DEAL_TYPE = "bizDeal_Type";
    public static final String BIZ_DEAL_START_DATE = "bizDeal_Start_Date";
    public static final String BIZ_DEAL_FROM_ACCT_ID = "bizDeal_From_Acct_ID";
    public static final String BIZ_DEAL_TO_ACCT_ID = "bizDeal_To_Acct_ID";
    public static final String BIZ_DEAL_FROM_B_ID = "bizDeal_From_Biz_ID";
    public static final String BIZ_DEAL_TO_BIZ_ID = "bizDeal_To_Biz_ID";
    public static final String BIZ_DEAL_IS_CALL_ALLOWED = "bizDeal_is_Call_Allowed";
    public static final String BIZ_DEAL_IS_REQUEST_ACCEPTED = "bizDeal_Is_Request_A";

    public static final String BIZ_DEAL_QTY = "bizDeal_QTY";
    public static final String BIZ_DEAL_NO_OF_MILESTONE = "bizDeal_No_Of_MS";
    public static final String BIZ_DEAL_CODE = "bizDeal_Code";
    public static final String BIZ_DEAL_END_DATE = "bizDeal_End_Date";
    public static final String BIZ_DEAL_STATUS = "bizDeal_Status";
    public static final String BIZ_DEAL_ACCT_NO = "bizDeal_Acct_No";

    public static final String BIZ_DEAL_IS_HOST = "bizDeal_is_host";
    public static final String BIZ_DEAL_IS_PARTNER = "bizDeal_is_Partner";
    public static final String BIZ_DEAL_IS_LOGISTICS = "bizDeal_is_logistics";
    public static final String BIZ_DEAL_DISPUTE = "bizDeal_Dispute";
    public static final String BIZ_DEAL_DB_ID = "bizDeal_DB_Id";
    public static final String BIZ_DEAL_TOTAL = "bizDeal_Total";
    public static final String BIZ_DEAL_CURRENCY = "bizDeal_Currency";

    public static final String BIZ_DEAL_CODE_TABLE = "bizDeal_Code_Table";

    public static final String BIZ_DEAL_CODE_DEAL_ID = "bizDeal_Code_Deal_Forgn_ID";
    public static final String BIZ_DEAL_NEW_CODE = "bizDeal_New_Code";
    public static final String BIZ_DEAL_NEW_CODE_ID = "bizDeal_New_Code_ID";





    public static final String BIZ_DEAL_CHAT_TABLE = "deal_Chat_Table";
    public static final String BIZ_DEAL_CHAT_ID = "deal_Chat_id";
    public static final String BIZ_DEAL_QBCHAT_D_ID = "d_Chat_Dialog_ID";
    public static final String BIZ_DEAL_CHAT_TITTLE = "deal_Chat_Tittle";
    public static final String BIZ_DEAL_CHAT_DBID = "deal_Chat_DB_ID";
    public static final String BIZ_DEAL_CHAT_DEAL_ID = "deal_Chat_Deal_ID";
    public static final String BIZ_DEAL_CHAT_AMOUNT = "deal_Chat_Amount";
    public static final String BIZ_DEAL_CHAT_AMT_CU = "deal_Chat_Currency";
    public static final String BIZ_DEAL_CHAT_PARTNER_ID = "deal_Chat_PartnerID";
    public static final String BIZ_DEAL_CHAT_HOST_ID = "deal_Chat_Host_ID";
    public static final String BIZ_DEAL_CHAT_TYPE = "deal_Chat_Type";
    public static final String BIZ_DEAL_CHAT_CREATED_DATE = "deal_Chat__Created_Date";
    public static final String BIZ_DEAL_CHAT_STATUS = "deal_Chat_Status";
    public static final String BIZ_DEAL_CHAT_FROM_BIZID = "deal_Chat_FRomBizID";
    public static final String BIZ_DEAL_CHAT_TO_BIZID = "deal_Chat_ToBizID";

    public static final String CREATE_DEAL_CHAT_TABLE = "CREATE TABLE " + BIZ_DEAL_CHAT_TABLE + " (" + BIZ_DEAL_CHAT_ID + " INTEGER, " +
            BIZ_DEAL_CHAT_DBID + " INTEGER, " + BIZ_DEAL_CHAT_DEAL_ID + " INTEGER , " + BIZ_DEAL_CHAT_TYPE + " TEXT, " + BIZ_DEAL_CHAT_STATUS + " TEXT, " + BIZ_DEAL_QBCHAT_D_ID + " TEXT, " + BIZ_DEAL_CHAT_PARTNER_ID + " TEXT, "+ BIZ_DEAL_CHAT_HOST_ID + " TEXT, " + BIZ_DEAL_CHAT_AMOUNT + " REAL, " + BIZ_DEAL_CHAT_AMT_CU + " TEXT, " + BIZ_DEAL_CHAT_FROM_BIZID + " LONG , " + BIZ_DEAL_CHAT_TO_BIZID + " LONG, "+ BIZ_DEAL_CHAT_TITTLE + " TEXT, " + BIZ_DEAL_CHAT_CREATED_DATE + " TEXT, " +
            "PRIMARY KEY(" + BIZ_DEAL_CHAT_DBID + "), " + "FOREIGN KEY(" + BIZ_DEAL_CHAT_DEAL_ID + ") REFERENCES " + BIZ_DEAL_TABLE + "(" + BIZ_DEAL_ID + "))";


    public static final String CREATE_BIZ_DEAL_CODE_TABLE = "CREATE TABLE " + BIZ_DEAL_CODE_TABLE + " (" + BIZ_DEAL_NEW_CODE_ID + " INTEGER, " +
            BIZ_DEAL_CODE_DEAL_ID + " INTEGER, " + BIZ_DEAL_NEW_CODE + " INTEGER , " +
            "PRIMARY KEY(" + BIZ_DEAL_NEW_CODE_ID + "), " + "FOREIGN KEY(" + BIZ_DEAL_CODE_DEAL_ID + ") REFERENCES " + BIZ_DEAL_TABLE + "(" + BIZ_DEAL_ID + "))";

    public static final String CREATE_BIZ_DEAL_TABLE = "CREATE TABLE IF NOT EXISTS " + BIZ_DEAL_TABLE + " (" + BIZ_DEAL_ID + " INTEGER, " + BIZ_DEAL_ACCT_NO + " INTEGER , " +
            BIZ_DEAL_TO_PROF_ID + " INTEGER , " + BIZ_DEAL_FROM_PROF_ID + " INTEGER , " + BIZ_DEAL_FROM_ACCT_ID + " INTEGER, " + BIZ_DEAL_TO_ACCT_ID + " INTEGER, " +
            BIZ_DEAL_TITTLE + " TEXT, " + BIZ_DEAL_PROD_AMOUNT + " REAL, " + BIZ_DEAL_SHIPPING_FEE + " REAL, " + BIZ_DEAL_QTY + " REAL, " +
            BIZ_DEAL_INS_FEE + " REAL, " + BIZ_DEAL_TYPE + " TEXT, " + BIZ_DEAL_START_DATE + " TEXT, "+ BIZ_DEAL_NO_OF_MILESTONE + " TEXT, "+ BIZ_DEAL_CODE + " TEXT, "+  BIZ_DEAL_STATUS + " TEXT, "+ BIZ_DEAL_FROM_B_ID + " INTEGER, "+ BIZ_DEAL_TO_BIZ_ID + " INTEGER, "+ BIZ_DEAL_IS_HOST + " TEXT, "+ BIZ_DEAL_IS_PARTNER + " TEXT, "+ BIZ_DEAL_IS_LOGISTICS + " TEXT, "+ BIZ_DEAL_DISPUTE + " TEXT, " + BIZ_DEAL_DB_ID + " INTEGER, "+ BIZ_DEAL_CREATED_DATE + " TEXT, "+ BIZ_DEAL_END_DATE + " TEXT, "+ BIZ_DEAL_TOTAL + " REAL, " + BIZ_DEAL_CURRENCY + " TEXT, "+ BIZ_DEAL_IS_CALL_ALLOWED + " TEXT, "+ BIZ_DEAL_IS_REQUEST_ACCEPTED + " TEXT, "+ "PRIMARY KEY(" +BIZ_DEAL_DB_ID + "), "+"FOREIGN KEY(" + BIZ_DEAL_FROM_ACCT_ID  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + BIZ_DEAL_TO_ACCT_ID  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + BIZ_DEAL_TO_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + BIZ_DEAL_ACCT_NO  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + BIZ_DEAL_FROM_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

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
    public void addBizDealPartner(BizDealPartner friendUser) {
        bizDealPartners = new ArrayList<>();
        bizDealPartners.add(friendUser);

    }
    public void addQBChatDialog(QBChatDialog createdChatDialog) {
        bizDealChatDialogs = new ArrayList<>();
        bizDealChatDialogs.add(createdChatDialog);
    }
    protected BusinessDeal(Parcel in) {
        dealID = in.readInt();
        dealTittle = in.readString();
        dealFromProfileID = in.readInt();
        dealToProfileID = in.readInt();
        toBiz = in.readParcelable(MarketBusiness.class.getClassLoader());
        fromBiz = in.readParcelable(MarketBusiness.class.getClassLoader());
        dealQty = in.readInt();
        dealSellerAcct = in.readParcelable(Account.class.getClassLoader());
        dealBuyerAcct = in.readParcelable(Account.class.getClassLoader());
        dealCostOfProduct = in.readDouble();
        dealCostOfInsurance = in.readDouble();
        dealShippingCost = in.readDouble();
        dealCode = in.readInt();
        dealStartDate = in.readString();
        dealEndDate = in.readString();
        bizDealBusS = in.createTypedArrayList(MarketBusiness.CREATOR);
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

    public MarketBusiness getToBiz() {
        return toBiz;
    }

    public void setToBiz(MarketBusiness toBiz) {
        this.toBiz = toBiz;
    }

    public MarketBusiness getFromBiz() {
        return fromBiz;
    }

    public void setFromBiz(MarketBusiness fromBiz) {
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

    public LogisticEntity getDealLogisticManager() {
        return dealLogisticEntity;
    }

    public void setDealLogisticManager(LogisticEntity dealLogisticEntity) {
        this.dealLogisticEntity = dealLogisticEntity;
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

    public ArrayList<MarketBusiness> getBizDealBusS() {
        return bizDealBusS;
    }

    public void setBizDealBusS(ArrayList<MarketBusiness> bizDealBusS) {
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

    public long getDealFromBizID() {
        return dealFromBizID;
    }

    public void setDealFromBizID(int dealFromBizID) {
        this.dealFromBizID = dealFromBizID;
    }

    public long getDealToBizID() {
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

    public long getDealAcctNumber() {
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

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public boolean isPartner() {
        return isPartner;
    }

    public void setPartner(boolean partner) {
        isPartner = partner;
    }

    public boolean isLogistics() {
        return isLogistics;
    }

    public void setLogistics(boolean logistics) {
        isLogistics = logistics;
    }

    public String getBizDealDispute() {
        return bizDealDispute;
    }

    public void setBizDealDispute(String bizDealDispute) {
        this.bizDealDispute = bizDealDispute;
    }

    public ArrayList<BizDealPartner> getBizDealPartners() {
        return bizDealPartners;
    }

    public void setBizDealPartners(ArrayList<BizDealPartner> bizDealPartners) {
        this.bizDealPartners = bizDealPartners;
    }


    public ArrayList<QBChatDialog> getBizDealChatDialogs() {
        return bizDealChatDialogs;
    }

    public void setBizDealChatDialogs(ArrayList<QBChatDialog> bizDealChatDialogs) {
        this.bizDealChatDialogs = bizDealChatDialogs;
    }

    public double getDealTotalAmount() {
        return dealTotalAmount;
    }

    public void setDealTotalAmount(double dealTotalAmount) {
        this.dealTotalAmount = dealTotalAmount;
    }

    public String getDealCurrency() {
        return dealCurrency;
    }

    public void setDealCurrency(String dealCurrency) {
        this.dealCurrency = dealCurrency;
    }

    public String getDealDateCreated() {
        return dealDateCreated;
    }

    public void setDealDateCreated(String dealDateCreated) {
        this.dealDateCreated = dealDateCreated;
    }

    public long getDealToAcctNoID() {
        return dealToAcctNoID;
    }

    public void setDealToAcctNoID(long dealToAcctNoID) {
        this.dealToAcctNoID = dealToAcctNoID;
    }

    public long getDealFromAcctNoID() {
        return dealFromAcctNoID;
    }

    public void setDealFromAcctNoID(long dealFromAcctNoID) {
        this.dealFromAcctNoID = dealFromAcctNoID;
    }

    public boolean isCallAllowed() {
        return isCallAllowed;
    }

    public void setCallAllowed(boolean callAllowed) {
        isCallAllowed = callAllowed;
    }

    public boolean isRequestAccepted() {
        return isRequestAccepted;
    }

    public void setRequestAccepted(boolean requestAccepted) {
        isRequestAccepted = requestAccepted;
    }
}
