package com.skylightapp.MarketClasses;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;

import android.location.Address;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Inventory.Stocks;

import java.io.Serializable;
import java.util.ArrayList;

public class MarketBizSupplier implements Parcelable, Serializable {
    public static final String SUPPLIER_TABLE = "Supplier_Table";
    public static final String SUPPLIER_ID = "Supplier_ID";
    public static final String SUPPLIER_NAME = "Supplier_Name";
    public static final String SUPPLIER_REG_NO = "Supplier_BizRegNo";
    public static final String SUPPLIER_PHONE_NO = "Supplier_PhoneNo";
    public static final String SUPPLIER_EMAIL = "Supplier_Email";
    public static final String SUPPLIERZ_ADDRESS = "Supplier_Address";
    public static final String SUPPLIER_TYPE = "Supplier_Type";
    public static final String SUPPLIER_BRANDNAME = "Supplier_Brand";
    public static final String SUPPLIER_STATE = "Supplier_State";
    public static final String SUPPLIER_PIX = "Supplier_Pix";
    public static final String SUPPLIER_STATUS = "Supplier_Status";
    public static final String SUPPLIER_PROF_ID = "Supplier_Prof_ID";
    public static final String SUPPLIER_COUNTRY = "Supplier_Country";
    public static final String SUPPLIER_MARKET_ID = "Supplier_Market_ID";
    public static final String SUPPLIER_CUSTOMER_ID = "Supplier_Customer_ID";
    public static final String SUPPLIER_BIZ_ID = "Supplier_Biz_ID";
    public static final String SUPPLIER_DB_ID = "Supplier_DB_ID";

    public static final String CREATE_SUPPLIER_TABLE = "CREATE TABLE IF NOT EXISTS " + SUPPLIER_TABLE + " (" + SUPPLIER_ID + " INTEGER , " +
            SUPPLIER_BIZ_ID + " INTEGER , " + SUPPLIER_NAME + " TEXT , " + SUPPLIER_REG_NO + " TEXT , " + SUPPLIER_PHONE_NO + " TEXT , " + SUPPLIER_EMAIL + " TEXT , " + SUPPLIERZ_ADDRESS + " TEXT , " + SUPPLIER_TYPE + " TEXT , " + SUPPLIER_BRANDNAME + " TEXT , " + SUPPLIER_STATE + " TEXT , " + SUPPLIER_PIX + " BLOB , " + SUPPLIER_STATUS + " TEXT , "+ SUPPLIER_COUNTRY + " TEXT , "+ SUPPLIER_MARKET_ID + " INTEGER , "+ SUPPLIER_CUSTOMER_ID + " INTEGER , " + SUPPLIER_DB_ID + " INTEGER , " +  "PRIMARY KEY(" + SUPPLIER_DB_ID + "), "+"FOREIGN KEY(" + SUPPLIER_BIZ_ID  + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "),"+"FOREIGN KEY(" + SUPPLIER_CUSTOMER_ID  + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," +"FOREIGN KEY(" + SUPPLIER_MARKET_ID  + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + ")," +
            "FOREIGN KEY(" + SUPPLIER_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";
    private int mBSupplierID;
    private int mBSupplierBizID;
    private String mBSupplierEmail;
    private String mBSupplierName;
    private String mBSupplierState;
    private String mBSupplierCountry;
    private String mBSupplierStatus;
    private Uri mBSupplierPix;
    private String mBSupplierAddress;
    private String mBSupplierPhoneNo;
    private String mBSupplierType;
    private String mBSupplierRegNo;
    private Profile mBSupplierProfile;

    private ArrayList<Account> mBSupplierAccounts;
    private ArrayList<Address> mBSupplierAddresses;
    private ArrayList<MarketBusiness> mBSupplierMarketBiz;
    private ArrayList<Loan> mBSupplierLoans;
    private ArrayList<MarketCommodity> mBSupplierCommodities;
    private ArrayList<MarketAnnouncement> mBSupplierMarketAnns;
    private ArrayList<MarketBizSub> mBSupplierBizSubs;
    private ArrayList<MarketInventory> mBSupplierInvTs;
    private ArrayList<MarketStock> mBSupplierStocks;
    private ArrayList<BizDealAccount> mBSupplierBizDealAccts;
    private ArrayList<Stocks> mBSupplierStockList;

    private ArrayList<Integer> mBSupplier_acctIDs;
    private ArrayList<Integer> mBSupplier_BizIDs;
    private ArrayList<OfficeBranch> mBSupplierOBranches;
    private Account mBSupplierAcct;
    private ArrayList<Market> mBSupplierMarkets;

    public MarketBizSupplier() {
        super();

    }
    public MarketBizSupplier(int supplierID,String name,String type,String state,String country, Uri pix,String status) {

        super();
        this.mBSupplierID = supplierID;
        this.mBSupplierName = name;
        this.mBSupplierType = type;
        this.mBSupplierState = state;
        this.mBSupplierCountry = country;
        this.mBSupplierPix =pix;
        this.mBSupplierStatus = status;
    }

    protected MarketBizSupplier(Parcel in) {
        mBSupplierID = in.readInt();
        mBSupplierEmail = in.readString();
        mBSupplierName = in.readString();
        mBSupplierState = in.readString();
        mBSupplierCountry = in.readString();
        mBSupplierStatus = in.readString();
        mBSupplierPix = in.readParcelable(Uri.class.getClassLoader());
        mBSupplierAddress = in.readString();
        mBSupplierPhoneNo = in.readString();
        mBSupplierType = in.readString();
        mBSupplierRegNo = in.readString();
        mBSupplierProfile = in.readParcelable(Profile.class.getClassLoader());
        mBSupplierAccounts = in.createTypedArrayList(Account.CREATOR);
        mBSupplierAddresses = in.createTypedArrayList(Address.CREATOR);
        mBSupplierMarketBiz = in.createTypedArrayList(MarketBusiness.CREATOR);
        mBSupplierLoans = in.createTypedArrayList(Loan.CREATOR);
        mBSupplierCommodities = in.createTypedArrayList(MarketCommodity.CREATOR);
        mBSupplierMarketAnns = in.createTypedArrayList(MarketAnnouncement.CREATOR);
        mBSupplierBizDealAccts = in.createTypedArrayList(BizDealAccount.CREATOR);
        mBSupplierStockList = in.createTypedArrayList(Stocks.CREATOR);
        mBSupplierOBranches = in.createTypedArrayList(OfficeBranch.CREATOR);
        mBSupplierAcct = in.readParcelable(Account.class.getClassLoader());
        mBSupplierMarkets = in.createTypedArrayList(Market.CREATOR);
    }

    public static final Creator<MarketBizSupplier> CREATOR = new Creator<MarketBizSupplier>() {
        @Override
        public MarketBizSupplier createFromParcel(Parcel in) {
            return new MarketBizSupplier(in);
        }

        @Override
        public MarketBizSupplier[] newArray(int size) {
            return new MarketBizSupplier[size];
        }
    };

    public int getmBSupplierID() {
        return mBSupplierID;
    }
    public void addMarket(Market market) {
        mBSupplierMarkets = new ArrayList<>();
        mBSupplierMarkets.add(market);
    }
    public void addOfficeBranch(OfficeBranch officeBranch) {
        mBSupplierOBranches = new ArrayList<>();
        mBSupplierOBranches.add(officeBranch);
    }

    public void addAcctID(Integer acctId) {
        mBSupplier_acctIDs = new ArrayList<>();
        mBSupplier_acctIDs.add(acctId);
    }
    public void addBizID(Integer bizId) {
        mBSupplier_BizIDs = new ArrayList<>();
        mBSupplier_BizIDs.add(bizId);
    }
    public void addAcct(Account account) {
        mBSupplierAccounts = new ArrayList<>();
        mBSupplierAccounts.add(account);
    }
    public void addMarketBiz(MarketBusiness marketBusiness) {
        mBSupplierMarketBiz = new ArrayList<>();
        mBSupplierMarketBiz.add(marketBusiness);
    }
    public void addLoan(Loan loan) {
        mBSupplierLoans = new ArrayList<>();
        mBSupplierLoans.add(loan);
    }
    public void addCommodity(MarketCommodity commodity) {
        mBSupplierCommodities = new ArrayList<>();
        mBSupplierCommodities.add(commodity);
    }
    public void addInventory(MarketInventory inventory) {
        mBSupplierInvTs = new ArrayList<>();
        mBSupplierInvTs.add(inventory);
    }
    public void addBizDealAcct(BizDealAccount bizDealAccount) {
        mBSupplierBizDealAccts = new ArrayList<>();
        mBSupplierBizDealAccts.add(bizDealAccount);
    }
    public void addMarketStock(MarketStock marketStock) {
        mBSupplierStocks = new ArrayList<>();
        mBSupplierStocks.add(marketStock);
    }
    public void addSubscription(MarketBizSub subScription) {
        mBSupplierBizSubs = new ArrayList<>();
        mBSupplierBizSubs.add(subScription);
    }

    public void setmBSupplierID(int mBSupplierID) {
        this.mBSupplierID = mBSupplierID;
    }

    public String getmBSupplierState() {
        return mBSupplierState;
    }

    public void setmBSupplierState(String mBSupplierState) {
        this.mBSupplierState = mBSupplierState;
    }

    public String getmBSupplierName() {
        return mBSupplierName;
    }

    public void setmBSupplierName(String mBSupplierName) {
        this.mBSupplierName = mBSupplierName;
    }

    public String getmBSupplierEmail() {
        return mBSupplierEmail;
    }

    public void setmBSupplierEmail(String mBSupplierEmail) {
        this.mBSupplierEmail = mBSupplierEmail;
    }

    public String getmBSupplierCountry() {
        return mBSupplierCountry;
    }

    public void setmBSupplierCountry(String mBSupplierCountry) {
        this.mBSupplierCountry = mBSupplierCountry;
    }

    public String getmBSupplierStatus() {
        return mBSupplierStatus;
    }

    public void setmBSupplierStatus(String mBSupplierStatus) {
        this.mBSupplierStatus = mBSupplierStatus;
    }

    public Uri getmBSupplierPix() {
        return mBSupplierPix;
    }

    public void setmBSupplierPix(Uri mBSupplierPix) {
        this.mBSupplierPix = mBSupplierPix;
    }

    public String getmBSupplierPhoneNo() {
        return mBSupplierPhoneNo;
    }

    public void setmBSupplierPhoneNo(String mBSupplierPhoneNo) {
        this.mBSupplierPhoneNo = mBSupplierPhoneNo;
    }

    public String getmBSupplierType() {
        return mBSupplierType;
    }

    public void setmBSupplierType(String mBSupplierType) {
        this.mBSupplierType = mBSupplierType;
    }

    public String getmBSupplierRegNo() {
        return mBSupplierRegNo;
    }

    public void setmBSupplierRegNo(String mBSupplierRegNo) {
        this.mBSupplierRegNo = mBSupplierRegNo;
    }

    public Profile getmBSupplierProfile() {
        return mBSupplierProfile;
    }

    public void setmBSupplierProfile(Profile mBSupplierProfile) {
        this.mBSupplierProfile = mBSupplierProfile;
    }

    public ArrayList<Account> getmBSupplierAccounts() {
        return mBSupplierAccounts;
    }

    public void setmBSupplierAccounts(ArrayList<Account> mBSupplierAccounts) {
        this.mBSupplierAccounts = mBSupplierAccounts;
    }

    public ArrayList<Address> getmBSupplierAddresses() {
        return mBSupplierAddresses;
    }

    public void setmBSupplierAddresses(ArrayList<Address> mBSupplierAddresses) {
        this.mBSupplierAddresses = mBSupplierAddresses;
    }

    public ArrayList<MarketBusiness> getmBSupplierMarketBiz() {
        return mBSupplierMarketBiz;
    }

    public void setmBSupplierMarketBiz(ArrayList<MarketBusiness> mBSupplierMarketBiz) {
        this.mBSupplierMarketBiz = mBSupplierMarketBiz;
    }

    public ArrayList<Loan> getmBSupplierLoans() {
        return mBSupplierLoans;
    }

    public void setmBSupplierLoans(ArrayList<Loan> mBSupplierLoans) {
        this.mBSupplierLoans = mBSupplierLoans;
    }

    public ArrayList<MarketCommodity> getmBSupplierCommodities() {
        return mBSupplierCommodities;
    }

    public void setmBSupplierCommodities(ArrayList<MarketCommodity> mBSupplierCommodities) {
        this.mBSupplierCommodities = mBSupplierCommodities;
    }

    public ArrayList<MarketAnnouncement> getmBSupplierMarketAnns() {
        return mBSupplierMarketAnns;
    }

    public void setmBSupplierMarketAnns(ArrayList<MarketAnnouncement> mBSupplierMarketAnns) {
        this.mBSupplierMarketAnns = mBSupplierMarketAnns;
    }

    public ArrayList<MarketBizSub> getmBSupplierBizSubs() {
        return mBSupplierBizSubs;
    }

    public void setmBSupplierBizSubs(ArrayList<MarketBizSub> mBSupplierBizSubs) {
        this.mBSupplierBizSubs = mBSupplierBizSubs;
    }

    public ArrayList<MarketInventory> getmBSupplierInvTs() {
        return mBSupplierInvTs;
    }

    public void setmBSupplierInvTs(ArrayList<MarketInventory> mBSupplierInvTs) {
        this.mBSupplierInvTs = mBSupplierInvTs;
    }

    public ArrayList<MarketStock> getmBSupplierStocks() {
        return mBSupplierStocks;
    }

    public void setmBSupplierStocks(ArrayList<MarketStock> mBSupplierStocks) {
        this.mBSupplierStocks = mBSupplierStocks;
    }

    public ArrayList<BizDealAccount> getmBSupplierBizDealAccts() {
        return mBSupplierBizDealAccts;
    }

    public void setmBSupplierBizDealAccts(ArrayList<BizDealAccount> mBSupplierBizDealAccts) {
        this.mBSupplierBizDealAccts = mBSupplierBizDealAccts;
    }

    public ArrayList<Stocks> getmBSupplierStockList() {
        return mBSupplierStockList;
    }

    public void setmBSupplierStockList(ArrayList<Stocks> mBSupplierStockList) {
        this.mBSupplierStockList = mBSupplierStockList;
    }

    public ArrayList<Integer> getmBSupplier_acctIDs() {
        return mBSupplier_acctIDs;
    }

    public void setmBSupplier_acctIDs(ArrayList<Integer> mBSupplier_acctIDs) {
        this.mBSupplier_acctIDs = mBSupplier_acctIDs;
    }

    public ArrayList<OfficeBranch> getmBSupplierOBranches() {
        return mBSupplierOBranches;
    }

    public void setmBSupplierOBranches(ArrayList<OfficeBranch> mBSupplierOBranches) {
        this.mBSupplierOBranches = mBSupplierOBranches;
    }

    public Account getmBSupplierAcct() {
        return mBSupplierAcct;
    }

    public void setmBSupplierAcct(Account mBSupplierAcct) {
        this.mBSupplierAcct = mBSupplierAcct;
    }

    public ArrayList<Market> getmBSupplierMarkets() {
        return mBSupplierMarkets;
    }

    public void setmBSupplierMarkets(ArrayList<Market> mBSupplierMarkets) {
        this.mBSupplierMarkets = mBSupplierMarkets;
    }

    public ArrayList<Integer> getmBSupplier_BizIDs() {
        return mBSupplier_BizIDs;
    }

    public void setmBSupplier_BizIDs(ArrayList<Integer> mBSupplier_BizIDs) {
        this.mBSupplier_BizIDs = mBSupplier_BizIDs;
    }

    public String getmBSupplierAddress() {
        return mBSupplierAddress;
    }

    public void setmBSupplierAddress(String mBSupplierAddress) {
        this.mBSupplierAddress = mBSupplierAddress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mBSupplierID);
        parcel.writeString(mBSupplierEmail);
        parcel.writeString(mBSupplierName);
        parcel.writeString(mBSupplierState);
        parcel.writeString(mBSupplierCountry);
        parcel.writeString(mBSupplierStatus);
        parcel.writeParcelable(mBSupplierPix, i);
        parcel.writeString(mBSupplierAddress);
        parcel.writeString(mBSupplierPhoneNo);
        parcel.writeString(mBSupplierType);
        parcel.writeString(mBSupplierRegNo);
        parcel.writeParcelable(mBSupplierProfile, i);
        parcel.writeTypedList(mBSupplierAccounts);
        parcel.writeTypedList(mBSupplierAddresses);
        parcel.writeTypedList(mBSupplierMarketBiz);
        parcel.writeTypedList(mBSupplierLoans);
        parcel.writeTypedList(mBSupplierCommodities);
        parcel.writeTypedList(mBSupplierMarketAnns);
        parcel.writeTypedList(mBSupplierBizDealAccts);
        parcel.writeTypedList(mBSupplierStockList);
        parcel.writeTypedList(mBSupplierOBranches);
        parcel.writeParcelable(mBSupplierAcct, i);
        parcel.writeTypedList(mBSupplierMarkets);
    }

    public int getmBSupplierBizID() {
        return mBSupplierBizID;
    }

    public void setmBSupplierBizID(int mBSupplierBizID) {
        this.mBSupplierBizID = mBSupplierBizID;
    }
}
