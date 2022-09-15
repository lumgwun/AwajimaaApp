package com.skylightapp.MarketClasses;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Markets.MarketTranx;

import java.io.Serializable;
import java.util.ArrayList;

public class Business implements Serializable, Parcelable {
    private int busIdentity;
    private int busStatus;
    private String busCACRegStatus;
    private Location busLocation;
    private  String busName;
    private  String busEmail;
    private  String busPhoneNo;
    private String busType;
    private String busRegNo;
    private Profile busOwner;
    private Profile busManager;
    private ArrayList<Market> busMarkets;
    private ArrayList<Location> busLocS;
    private ArrayList<MarketCustomer> busCusS;
    private ArrayList<MarketAdmin> busMarketAdminS;
    private ArrayList<BusinessDeal> busBizDeals;
    private ArrayList<MarketTranx> busMarketTranxS;

    public static final String BUSINESS_NAME = "business_name";
    public static final String BUSINESS_TABLE22 = "business_Table";
    public static final String BUSINESS_EMAIL = "business_Email";
    public static final String BUSINESS_PHONE_NO = "business_Phone";
    public static final String BUSINESS_REG_NO = "business_cac_no";
    public static final String BUSINESS_TYPE = "business-type";
    public static final String BUSINESS_OWNER22 = "business_owner_name";
    public static final String BUSINESS_MANAGER = "business_manager_name";
    public static final String BUSINESS_PROF_ID = "business_prof_Id";
    public static final String BUSINESS_MANAGER_ID22 = "business_manager_prof_Id";
    public static final String BUSINESS_LOCATION = "business_location";
    public static final String BUSINESS_MARKET_ID = "business_market_Id";
    public static final String BUSINESS_STATUS = "business_status";
    public static final String BUSINESS_ID33 = "business_ID";
    public static final String BUSINESS_LOGO = "business_Logo";
    public static final String BUSINESS_COUNTRY = "business_Country";


    public Business(Parcel in) {
        busIdentity = in.readInt();
        busStatus = in.readInt();
        busCACRegStatus = in.readString();
        busLocation = in.readParcelable(Location.class.getClassLoader());
        busName = in.readString();
        busEmail = in.readString();
        busPhoneNo = in.readString();
        busType = in.readString();
        busRegNo = in.readString();
        busOwner = in.readParcelable(Profile.class.getClassLoader());
        busManager = in.readParcelable(Profile.class.getClassLoader());
        busLocS = in.createTypedArrayList(Location.CREATOR);
    }

    public static final Creator<Business> CREATOR = new Creator<Business>() {
        @Override
        public Business createFromParcel(Parcel in) {
            return new Business(in);
        }

        @Override
        public Business[] newArray(int size) {
            return new Business[size];
        }
    };

    public Business() {
        super();
    }

    public int getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(int busStatus) {
        this.busStatus = busStatus;
    }

    public String getBusCACRegStatus() {
        return busCACRegStatus;
    }

    public void setBusCACRegStatus(String busCACRegStatus) {
        this.busCACRegStatus = busCACRegStatus;
    }

    public Location getBusLocation() {
        return busLocation;
    }

    public void setBusLocation(Location busLocation) {
        this.busLocation = busLocation;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public Profile getBusOwner() {
        return busOwner;
    }

    public void setBusOwner(Profile busOwner) {
        this.busOwner = busOwner;
    }

    public Profile getBusManager() {
        return busManager;
    }

    public void setBusManager(Profile busManager) {
        this.busManager = busManager;
    }

    public int getBusIdentity() {
        return busIdentity;
    }

    public void setBusIdentity(int busIdentity) {
        this.busIdentity = busIdentity;
    }

    public String getBusEmail() {
        return busEmail;
    }

    public void setBusEmail(String busEmail) {
        this.busEmail = busEmail;
    }

    public String getBusPhoneNo() {
        return busPhoneNo;
    }

    public void setBusPhoneNo(String busPhoneNo) {
        this.busPhoneNo = busPhoneNo;
    }

    public String getBusRegNo() {
        return busRegNo;
    }

    public void setBusRegNo(String busRegNo) {
        this.busRegNo = busRegNo;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public ArrayList<Market> getBusMarkets() {
        return busMarkets;
    }

    public void setBusMarkets(ArrayList<Market> busMarkets) {
        this.busMarkets = busMarkets;
    }

    public ArrayList<MarketCustomer> getBusCusS() {
        return busCusS;
    }

    public void setBusCusS(ArrayList<MarketCustomer> busCusS) {
        this.busCusS = busCusS;
    }

    public ArrayList<MarketAdmin> getBusMarketAdminS() {
        return busMarketAdminS;
    }

    public void setBusMarketAdminS(ArrayList<MarketAdmin> busMarketAdminS) {
        this.busMarketAdminS = busMarketAdminS;
    }

    public ArrayList<BusinessDeal> getBusBizDeals() {
        return busBizDeals;
    }

    public void setBusBizDeals(ArrayList<BusinessDeal> busBizDeals) {
        this.busBizDeals = busBizDeals;
    }

    public ArrayList<MarketTranx> getBusMarketTranxS() {
        return busMarketTranxS;
    }

    public void setBusMarketTranxS(ArrayList<MarketTranx> busMarketTranxS) {
        this.busMarketTranxS = busMarketTranxS;
    }

    public ArrayList<Location> getBusLocS() {
        return busLocS;
    }

    public void setBusLocS(ArrayList<Location> busLocS) {
        this.busLocS = busLocS;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(busIdentity);
        parcel.writeInt(busStatus);
        parcel.writeString(busCACRegStatus);
        parcel.writeParcelable(busLocation, i);
        parcel.writeString(busName);
        parcel.writeString(busEmail);
        parcel.writeString(busPhoneNo);
        parcel.writeString(busType);
        parcel.writeString(busRegNo);
        parcel.writeParcelable(busOwner, i);
        parcel.writeParcelable(busManager, i);
        parcel.writeTypedList(busLocS);
    }
}
