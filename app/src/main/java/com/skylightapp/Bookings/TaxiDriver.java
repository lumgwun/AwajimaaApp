package com.skylightapp.Bookings;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketBusiness;

import java.io.Serializable;
import java.util.ArrayList;

public class TaxiDriver implements Parcelable, Serializable {
    public static final String TAXI_DRIVER_TABLE = "taxi_Driver_table";
    public static final String TAXI_DRIVER_ID = "taxi_Driver_ID";
    public static final String TAXI_DRIVER_DBID = "taxi_Driver_DB_ID";
    public static final String TAXI_DRIVER_AGE = "taxi_Driver_Age";
    public static final String TAXI_DRIVER_PROF_ID = "taxi_Driver_Prof_ID";
    public static final String TAXI_DRIVER_GENDER = "taxi_Driver_Gender";
    public static final String TAXI_DRIVER_REVENUE = "taxi_Driver_Revenue";
    public static final String TAXI_DRIVER_CAR_TYPE = "taxi_Driver_Car_Type";
    public static final String TAXI_DRIVER_STATUS = "taxi_Driver_Status";
    public static final String TAXI_DRIVER_PIX = "taxi_Driver_Picture";
    public static final String TAXI_DRIVER_NIN = "taxi_Driver_NIN";
    public static final String TAXI_DRIVER_LICENSE = "taxi_Driver_License";
    public static final String TAXI_DRIVER_RATE = "taxi_Driver_Rate";
    public static final String TAXI_DRIVER_JOINED_D = "taxi_Driver_JDate";
    public static final String TAXI_DRIVER_PHONE_NO = "taxi_Driver_Phone_No";
    public static final String TAXI_DRIVER_CURRENT_LATLNG = "taxi_Driver_LatLng";
    public static final String TAXI_DRIVER_CURRENT_TOWN = "taxi_Driver_Town";
    public static final String TAXI_DRIVER_NAME = "taxi_Driver_Name";
    public static final String TAXI_DRIVER_SURNAME = "taxi_Driver_SN";

    public static final String CREATE_TAXI_DRIVER_TABLE = "CREATE TABLE IF NOT EXISTS " + TAXI_DRIVER_TABLE + " (" + TAXI_DRIVER_DBID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TAXI_DRIVER_ID + " INTEGER, "+
            TAXI_DRIVER_AGE + " INTEGER , " + TAXI_DRIVER_PROF_ID + " INTEGER , " + TAXI_DRIVER_GENDER + " TEXT, " + TAXI_DRIVER_REVENUE + " TEXT, " + TAXI_DRIVER_CAR_TYPE + " TEXT, " + TAXI_DRIVER_STATUS + " TEXT, " + TAXI_DRIVER_PIX + " BLOB, " + TAXI_DRIVER_NIN + " TEXT, " + TAXI_DRIVER_LICENSE + " TEXT, " + TAXI_DRIVER_RATE + " TEXT, " + TAXI_DRIVER_JOINED_D + " TEXT,"+ TAXI_DRIVER_PHONE_NO + " TEXT," + TAXI_DRIVER_CURRENT_LATLNG + " FLOAT, "+ TAXI_DRIVER_CURRENT_TOWN + " TEXT, "+ TAXI_DRIVER_NAME + " INTEGER , " + TAXI_DRIVER_SURNAME + " TEXT, "+
            "FOREIGN KEY(" + TAXI_DRIVER_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";
    int driverID;
    private int driverProfID;
    String driverName;
    String driverSurname;
    String driverPicture;
    Integer driverAge;
    String driverGender;
    float driverRate;
    int driverRatingCount;
    String driverCarType;
    String driverCarPlate;
    float driverCarRate;
    int carVoteCount;
    String driverCarModel;
    private String driverStatus;
    private Profile driverProfile;
    private String driverLicense;
    private String driverJoinedDate;
    private LatLng driverLatLng;
    private String driverTown;
    private String driverPhoneNo;
    private String driverLatLngStng;
    private MarketBusiness driverMarketBiz;
    private ArrayList<MarketBusiness> driverMarketBizS;
    private ArrayList<Market> driverMarkets;
    private ArrayList<Transaction> driverTranxs;
    private ArrayList<Customer> driverCustomers;
    private ArrayList<Profile> driverProfiles;
    private ArrayList<EmergencyReport> driverEmergReports;

    public TaxiDriver(int driverID, String driverName, String driverSurname, String driverPicture, Integer driverAge, String driverGender, float driverRate, int driverRatingCount, String driverCarType, String driverCarPlate, float driverCarRate, int carVoteCount, String driverCarModel) {
        this.driverID = driverID;
        this.driverName = driverName;
        this.driverSurname = driverSurname;
        this.driverPicture = driverPicture;
        this.driverAge = driverAge;
        this.driverGender = driverGender;
        this.driverRate = driverRate;
        this.driverRatingCount = driverRatingCount;
        this.driverCarType = driverCarType;
        this.driverCarPlate = driverCarPlate;
        this.driverCarRate = driverCarRate;
        this.carVoteCount = carVoteCount;
        this.driverCarModel = driverCarModel;
    }
    public TaxiDriver(int driverId, int profID, String name, Uri picture, String vehicle, String dateJoined, String status) {
        super();
        this.driverID = driverId;
        this.driverProfID = profID;
        this.driverName = name;
        this.driverPicture = String.valueOf(picture);
        this.driverCarType = vehicle;
        this.driverJoinedDate = dateJoined;
        this.driverStatus = status;
    }
    public TaxiDriver(int driverID, int profID,String name,String surName, int age, String gender, String carType, String town, String phoneNo, String latlng, String dateJoined, String status, Uri pix) {
        this.driverID = driverID;
        this.driverProfID = profID;
        this.driverName = name;
        this.driverSurname = surName;
        this.driverGender = gender;
        this.driverCarType = carType;
        this.driverTown = town;
        this.driverAge = age;
        this.driverPhoneNo = phoneNo;
        this.driverJoinedDate = dateJoined;
        this.driverLatLngStng = latlng;
        this.driverStatus = status;
        this.driverPicture = String.valueOf(pix);

    }

    public TaxiDriver() {
        super();
    }

    protected TaxiDriver(Parcel in) {
        driverID = in.readInt();
        driverName = in.readString();
        driverSurname = in.readString();
        driverPicture = in.readString();
        if (in.readByte() == 0) {
            driverAge = null;
        } else {
            driverAge = in.readInt();
        }
        driverGender = in.readString();
        driverRate = in.readFloat();
        driverRatingCount = in.readInt();
        driverCarType = in.readString();
        driverCarPlate = in.readString();
        driverCarRate = in.readFloat();
        carVoteCount = in.readInt();
        driverCarModel = in.readString();
        driverProfile = in.readParcelable(Profile.class.getClassLoader());
        driverMarketBiz = in.readParcelable(MarketBusiness.class.getClassLoader());
        driverMarketBizS = in.createTypedArrayList(MarketBusiness.CREATOR);
        driverMarkets = in.createTypedArrayList(Market.CREATOR);
        driverTranxs = in.createTypedArrayList(Transaction.CREATOR);
        driverCustomers = in.createTypedArrayList(Customer.CREATOR);
        driverProfiles = in.createTypedArrayList(Profile.CREATOR);
        driverEmergReports = in.createTypedArrayList(EmergencyReport.CREATOR);
    }

    public static final Creator<TaxiDriver> CREATOR = new Creator<TaxiDriver>() {
        @Override
        public TaxiDriver createFromParcel(Parcel in) {
            return new TaxiDriver(in);
        }

        @Override
        public TaxiDriver[] newArray(int size) {
            return new TaxiDriver[size];
        }
    };



    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }

    public String getDriverPicture() {
        return driverPicture;
    }

    public void setDriverPicture(String driverPicture) {
        this.driverPicture = driverPicture;
    }

    public Integer getDriverAge() {
        return driverAge;
    }

    public void setDriverAge(Integer driverAge) {
        this.driverAge = driverAge;
    }

    public String getDriverGender() {
        return driverGender;
    }

    public String getDriverCarType() {
        return driverCarType;
    }

    public String getDriverCarPlate() {
        return driverCarPlate;
    }

    public String getDriverCarModel() {
        return driverCarModel;
    }

    public Profile getDriverProfile() {
        return driverProfile;
    }

    public void setDriverProfile(Profile driverProfile) {
        this.driverProfile = driverProfile;
    }

    public MarketBusiness getDriverMarketBiz() {
        return driverMarketBiz;
    }

    public void setDriverMarketBiz(MarketBusiness driverMarketBiz) {
        this.driverMarketBiz = driverMarketBiz;
    }

    public ArrayList<MarketBusiness> getDriverMarketBizS() {
        return driverMarketBizS;
    }

    public void setDriverMarketBizS(ArrayList<MarketBusiness> driverMarketBizS) {
        this.driverMarketBizS = driverMarketBizS;
    }

    public ArrayList<Market> getDriverMarkets() {
        return driverMarkets;
    }

    public void setDriverMarkets(ArrayList<Market> driverMarkets) {
        this.driverMarkets = driverMarkets;
    }

    public ArrayList<Transaction> getDriverTranxs() {
        return driverTranxs;
    }

    public void setDriverTranxs(ArrayList<Transaction> driverTranxs) {
        this.driverTranxs = driverTranxs;
    }

    public ArrayList<Customer> getDriverCustomers() {
        return driverCustomers;
    }

    public void setDriverCustomers(ArrayList<Customer> driverCustomers) {
        this.driverCustomers = driverCustomers;
    }

    public ArrayList<EmergencyReport> getDriverEmergReports() {
        return driverEmergReports;
    }

    public void setDriverEmergReports(ArrayList<EmergencyReport> driverEmergReports) {
        this.driverEmergReports = driverEmergReports;
    }

    public ArrayList<Profile> getDriverProfiles() {
        return driverProfiles;
    }

    public void setDriverProfiles(ArrayList<Profile> driverProfiles) {
        this.driverProfiles = driverProfiles;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(driverID);
        parcel.writeString(driverName);
        parcel.writeString(driverSurname);
        parcel.writeString(driverPicture);
        if (driverAge == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(driverAge);
        }
        parcel.writeString(driverGender);
        parcel.writeFloat(driverRate);
        parcel.writeInt(driverRatingCount);
        parcel.writeString(driverCarType);
        parcel.writeString(driverCarPlate);
        parcel.writeFloat(driverCarRate);
        parcel.writeInt(carVoteCount);
        parcel.writeString(driverCarModel);
        parcel.writeParcelable(driverProfile, i);
        parcel.writeParcelable(driverMarketBiz, i);
        parcel.writeTypedList(driverMarketBizS);
        parcel.writeTypedList(driverMarkets);
        parcel.writeTypedList(driverTranxs);
        parcel.writeTypedList(driverCustomers);
        parcel.writeTypedList(driverProfiles);
        parcel.writeTypedList(driverEmergReports);
    }

    public int getDriverProfID() {
        return driverProfID;
    }

    public void setDriverProfID(int driverProfID) {
        this.driverProfID = driverProfID;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getDriverJoinedDate() {
        return driverJoinedDate;
    }

    public void setDriverJoinedDate(String driverJoinedDate) {
        this.driverJoinedDate = driverJoinedDate;
    }

    public LatLng getDriverLatLng() {
        return driverLatLng;
    }

    public void setDriverLatLng(LatLng driverLatLng) {
        this.driverLatLng = driverLatLng;
    }

    public String getDriverTown() {
        return driverTown;
    }

    public void setDriverTown(String driverTown) {
        this.driverTown = driverTown;
    }

    public String getDriverPhoneNo() {
        return driverPhoneNo;
    }

    public void setDriverPhoneNo(String driverPhoneNo) {
        this.driverPhoneNo = driverPhoneNo;
    }

    public String getDriverLatLngStng() {
        return driverLatLngStng;
    }

    public void setDriverLatLngStng(String driverLatLngStng) {
        this.driverLatLngStng = driverLatLngStng;
    }
}
