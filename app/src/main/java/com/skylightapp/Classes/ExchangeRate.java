package com.skylightapp.Classes;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ExchangeRate implements Serializable, Parcelable {

    public static final String EXCHANGER_TABLE = "exchanger_table";
    public static final String EXCHANGER_ID = "exchanger_ID";
    public static final String EXCHANGER_DB_ID = "exchanger_DB_Id";
    public static final String EXCHANGER_DATE = "exchanger_Date";
    public static final String EXCHANGER_OUR_RATE = "exchanger_ourR";
    public static final String EXCHANGER_FROM = "exchanger_From";
    public static final String EXCHANGER_TO = "exchanger_To";
    public static final String EXCHANGER_OFFICIAL_RATE = "exchanger_officialR";
    public static final String EXCHANGER_ADMIN_NAME = "exchanger_Admin_Name";
    public static final String EXCHANGER_INST = "exchanger_Institution";
    public static final String EXCHANGER_STATUS = "exchanger_Status";

    public static final String CREATE_EXCHANGER_TABLE = "CREATE TABLE IF NOT EXISTS " + EXCHANGER_TABLE + " (" + EXCHANGER_DB_ID + " INTEGER , " + EXCHANGER_ID + "INTEGER," + EXCHANGER_OUR_RATE + " REAL , " +
            EXCHANGER_OFFICIAL_RATE + " REAL , " + EXCHANGER_DATE + " TEXT , " + EXCHANGER_FROM + " TEXT , " + EXCHANGER_TO + " TEXT , " + EXCHANGER_INST + " TEXT, " + EXCHANGER_ADMIN_NAME + " REAL, " +
            EXCHANGER_STATUS + " TEXT, "  + "PRIMARY KEY(" + EXCHANGER_DB_ID + "))";
    private int exchangeRID;
    private String exchangeRDate;
    private String fromCurrency;
    private String toCurrency;
    private double ourRate;
    private String adminName;
    private String status;
    private double officialRate;
    private double officialInst;

    public ExchangeRate(){
        super();

    }

    protected ExchangeRate(Parcel in) {
        exchangeRID = in.readInt();
        exchangeRDate = in.readString();
        fromCurrency = in.readString();
        toCurrency = in.readString();
        ourRate = in.readDouble();
        adminName = in.readString();
        status = in.readString();
        officialRate = in.readDouble();
        officialInst = in.readDouble();
    }

    public static final Creator<ExchangeRate> CREATOR = new Creator<ExchangeRate>() {
        @Override
        public ExchangeRate createFromParcel(Parcel in) {
            return new ExchangeRate(in);
        }

        @Override
        public ExchangeRate[] newArray(int size) {
            return new ExchangeRate[size];
        }
    };

    public int getExchangeRID() {
        return exchangeRID;
    }

    public void setExchangeRID(int exchangeRID) {
        this.exchangeRID = exchangeRID;
    }

    public String getExchangeRDate() {
        return exchangeRDate;
    }

    public void setExchangeRDate(String exchangeRDate) {
        this.exchangeRDate = exchangeRDate;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }

    public double getOurRate() {
        return ourRate;
    }

    public void setOurRate(double ourRate) {
        this.ourRate = ourRate;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getOfficialRate() {
        return officialRate;
    }

    public void setOfficialRate(double officialRate) {
        this.officialRate = officialRate;
    }

    public double getOfficialInst() {
        return officialInst;
    }

    public void setOfficialInst(double officialInst) {
        this.officialInst = officialInst;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(exchangeRID);
        parcel.writeString(exchangeRDate);
        parcel.writeString(fromCurrency);
        parcel.writeString(toCurrency);
        parcel.writeDouble(ourRate);
        parcel.writeString(adminName);
        parcel.writeString(status);
        parcel.writeDouble(officialRate);
        parcel.writeDouble(officialInst);
    }
}
