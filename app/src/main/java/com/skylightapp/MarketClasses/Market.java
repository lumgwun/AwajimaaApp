package com.skylightapp.MarketClasses;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Markets.MarketTranx;

import java.io.Serializable;
import java.util.ArrayList;

import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class Market implements Serializable, Parcelable {

    public static final String MARKET_TABLE = "market_table";
    public static final String MARKET_TOWN = "market_town";
    public static final String MARKET_LGA = "market_LGA";
    public static final String MARKET_NAME = "market_name";
    public static final String MARKET_STATE = "market_state";
    public static final String MARKET_TYPE = "market_type";
    public static final String MARKET_ID = "market_ID33";
    public static final String MARKET_COUNTRY = "market_Country";
    public static final String MARKET_COMMODITY_COUNT = "market_com_count";
    public static final String MARKET_USER_COUNT = "market_user_count";
    public static final String MARKET_LOGO = "market_Logo";
    public static final String MARKET_STATUS = "market_Status";
    public static final String MARKET_ACCOUNT_ID = "market_Acct_ID";
    public static final String MARKET_PROF_ID = "market_Prof_ID";
    public static final String MARKET_REVENUE = "market_Revenue";
    public static final String MARKET_ADDRESS = "market_Address";
    public static final String MARKET_LAT = "market_Lat";
    public static final String MARKET_LNG = "market_Lng";


    public static final String CREATE_MARKET_TABLE = "CREATE TABLE IF NOT EXISTS " + MARKET_TABLE + " (" + MARKET_ID + " INTEGER , " + MARKET_NAME + "TEXT," + MARKET_ACCOUNT_ID + " INTEGER , " +
            MARKET_PROF_ID + " INTEGER , " + MARKET_TYPE + " TEXT , " +
            MARKET_TOWN + " TEXT , " + MARKET_LGA + " TEXT , " + MARKET_STATE + " TEXT , " + MARKET_COUNTRY + " TEXT, " + MARKET_LOGO + " BLOB, " +
            MARKET_COMMODITY_COUNT + " REAL, " + MARKET_USER_COUNT + " REAL, " + MARKET_STATUS + "TEXT, "+ MARKET_REVENUE + "TEXT, "+ MARKET_ADDRESS + "TEXT, "+ MARKET_LAT + "REAL, "+ MARKET_LNG + "REAL, "+"FOREIGN KEY(" + MARKET_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"  + "FOREIGN KEY(" + MARKET_ACCOUNT_ID + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +
            "PRIMARY KEY(" + MARKET_ID + "))";
   private Market marketInstance;
    private int marketID;
    private int marketProfID;
    private String marketName;
    private  String marketAddress;
    private String marketType;
    private String marketState;
    private String marketLGA;
    private String marketTown;
    private String marketCountry;
    private  double marketLng;
    private  double marketLat;
    private int commodityCount;
    private int marketUserCount;
    private int marketLogo;
    private Uri marketLogoURI;
    private double marketRevenue;
    private String marketStatus;
    private Account marketAccount;
    private ArrayList<Commodity> commodityArrayList;
    private ArrayList<MarketDays> marketMDaysArrayList;
    private ArrayList<BusinessOthers> marketBizArrayList;
    private ArrayList<BusinessDeal> marketBizDealList;
    private ArrayList<BizDealAccount> marketBizDealAcctList;
    private ArrayList<MarketCustomer> marketCustomers;
    private ArrayList<MarketAdmin> marketAdminArrayList;
    private ArrayList<MarketTranx> marketTranxArrayList;

    private ArrayList<Long> marketBizIDList;
    private Profile marketProf;

    public Market () {
        super();

    }

    public Market(int logo, String name, String marketType, String marketLGA, String marketState) {
        this.marketLogo = logo;
        this.marketName = name;
        this.marketType = marketType;
        this.marketLGA = marketLGA;
        this.marketState = marketState;
    }

    public Market(Parcel in) {
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
        marketBizArrayList = in.createTypedArrayList(BusinessOthers.CREATOR);
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
    public void addMarketBizID(long businessID) {
        marketBizIDList = new ArrayList<>();
        marketBizIDList.add(businessID);
    }

    public Market(int id, String marketName, String marketType, String marketLGA, String marketState, int marketLogo, int marketUserCount, int marketComCount, double marketRevenue, String marketStatus) {
        this.marketID=id;
        this.marketName=marketName;
        this.marketType=marketType;
        this.marketLGA=marketLGA;
        this.marketState=marketState;
        this.marketLogo=marketLogo;
        this.marketUserCount=marketUserCount;
        this.commodityCount=marketComCount;
        this.marketRevenue=marketRevenue;
        this.marketStatus=marketStatus;
    }
    public Market(int marketID, String name, String address, String marketType, String town, String lga, String state, String country, Uri logo, String status, double lat, double lng, double marketRev) {
        this.marketID=marketID;
        this.marketName=name;
        this.marketType=marketType;
        this.marketLGA=lga;
        this.marketState=state;
        this.marketLogoURI=logo;
        this.marketTown=town;
        this.marketLat=lat;
        this.marketLng=lng;
        this.marketAddress=address;
        this.marketCountry=country;
        this.marketRevenue=marketRev;
        this.marketStatus=status;
    }

    public Market(Market loan_market) {
        this.marketInstance=loan_market;

    }



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

    public ArrayList<BusinessOthers> getMarketBizArrayList() {
        return marketBizArrayList;
    }

    public void setMarketBizArrayList(ArrayList<BusinessOthers> marketBizArrayList) {
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

    public double getMarketRevenue() {
        return marketRevenue;
    }

    public void setMarketRevenue(double marketRevenue) {
        this.marketRevenue = marketRevenue;
    }

    public String getMarketCountry() {
        return marketCountry;
    }

    public void setMarketCountry(String marketCountry) {
        this.marketCountry = marketCountry;
    }

    public Account getMarketAccount() {
        return marketAccount;
    }

    public void setMarketAccount(Account marketAccount) {
        this.marketAccount = marketAccount;
    }

    public int getMarketUserCount() {
        return marketUserCount;
    }

    public void setMarketUserCount(int marketUserCount) {
        this.marketUserCount = marketUserCount;
    }

    public String getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(String marketStatus) {
        this.marketStatus = marketStatus;
    }

    public Profile getMarketProf() {
        return marketProf;
    }

    public void setMarketProf(Profile marketProf) {
        this.marketProf = marketProf;
    }

    public Uri getMarketLogoURI() {
        return marketLogoURI;
    }

    public void setMarketLogoURI(Uri marketLogoURI) {
        this.marketLogoURI = marketLogoURI;
    }

    public ArrayList<Long> getMarketBizIDList() {
        return marketBizIDList;
    }

    public void setMarketBizIDList(ArrayList<Long> marketBizIDList) {
        this.marketBizIDList = marketBizIDList;
    }
}
