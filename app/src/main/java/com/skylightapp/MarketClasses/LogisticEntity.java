package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class LogisticEntity implements Serializable, Parcelable {
    private int logisticsID;
    private String logisticsName;
    private String logisticsAddress;
    private String logisticsEmailAddress;
    private String logisticsPhoneNO;
    private String logisticsRegYear;
    private String logisticsDateJoined;
    private String logisticsStatus;

    public LogisticEntity () {
        super();

    }

    protected LogisticEntity(Parcel in) {
        logisticsID = in.readInt();
        logisticsName = in.readString();
        logisticsAddress = in.readString();
        logisticsEmailAddress = in.readString();
        logisticsPhoneNO = in.readString();
        logisticsRegYear = in.readString();
        logisticsDateJoined = in.readString();
        logisticsStatus = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(logisticsID);
        dest.writeString(logisticsName);
        dest.writeString(logisticsAddress);
        dest.writeString(logisticsEmailAddress);
        dest.writeString(logisticsPhoneNO);
        dest.writeString(logisticsRegYear);
        dest.writeString(logisticsDateJoined);
        dest.writeString(logisticsStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LogisticEntity> CREATOR = new Creator<LogisticEntity>() {
        @Override
        public LogisticEntity createFromParcel(Parcel in) {
            return new LogisticEntity(in);
        }

        @Override
        public LogisticEntity[] newArray(int size) {
            return new LogisticEntity[size];
        }
    };

    public int getLogisticsID() {
        return logisticsID;
    }

    public void setLogisticsID(int logisticsID) {
        this.logisticsID = logisticsID;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public String getLogisticsAddress() {
        return logisticsAddress;
    }

    public void setLogisticsAddress(String logisticsAddress) {
        this.logisticsAddress = logisticsAddress;
    }

    public String getLogisticsEmailAddress() {
        return logisticsEmailAddress;
    }

    public void setLogisticsEmailAddress(String logisticsEmailAddress) {
        this.logisticsEmailAddress = logisticsEmailAddress;
    }

    public String getLogisticsPhoneNO() {
        return logisticsPhoneNO;
    }

    public void setLogisticsPhoneNO(String logisticsPhoneNO) {
        this.logisticsPhoneNO = logisticsPhoneNO;
    }

    public String getLogisticsRegYear() {
        return logisticsRegYear;
    }

    public void setLogisticsRegYear(String logisticsRegYear) {
        this.logisticsRegYear = logisticsRegYear;
    }

    public String getLogisticsDateJoined() {
        return logisticsDateJoined;
    }

    public void setLogisticsDateJoined(String logisticsDateJoined) {
        this.logisticsDateJoined = logisticsDateJoined;
    }

    public String getLogisticsStatus() {
        return logisticsStatus;
    }

    public void setLogisticsStatus(String logisticsStatus) {
        this.logisticsStatus = logisticsStatus;
    }
}
