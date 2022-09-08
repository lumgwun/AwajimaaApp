package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MarketAnnouncement implements Serializable, Parcelable {
    private int mAID;
    private int mAProfID;
    private int mAMarketID;
    private String mAMessage;
    private String mADate;
    private String mAAnouncer;
    private String mAStatus;

    public MarketAnnouncement () {
        super();

    }

    protected MarketAnnouncement(Parcel in) {
        mAID = in.readInt();
        mAProfID = in.readInt();
        mAMarketID = in.readInt();
        mAMessage = in.readString();
        mADate = in.readString();
        mAAnouncer = in.readString();
        mAStatus = in.readString();
    }

    public static final Creator<MarketAnnouncement> CREATOR = new Creator<MarketAnnouncement>() {
        @Override
        public MarketAnnouncement createFromParcel(Parcel in) {
            return new MarketAnnouncement(in);
        }

        @Override
        public MarketAnnouncement[] newArray(int size) {
            return new MarketAnnouncement[size];
        }
    };

    public int getmAID() {
        return mAID;
    }

    public void setmAID(int mAID) {
        this.mAID = mAID;
    }

    public int getmAProfID() {
        return mAProfID;
    }

    public void setmAProfID(int mAProfID) {
        this.mAProfID = mAProfID;
    }

    public int getmAMarketID() {
        return mAMarketID;
    }

    public void setmAMarketID(int mAMarketID) {
        this.mAMarketID = mAMarketID;
    }

    public String getmAMessage() {
        return mAMessage;
    }

    public void setmAMessage(String mAMessage) {
        this.mAMessage = mAMessage;
    }

    public String getmADate() {
        return mADate;
    }

    public void setmADate(String mADate) {
        this.mADate = mADate;
    }

    public String getmAAnouncer() {
        return mAAnouncer;
    }

    public void setmAAnouncer(String mAAnouncer) {
        this.mAAnouncer = mAAnouncer;
    }

    public String getmAStatus() {
        return mAStatus;
    }

    public void setmAStatus(String mAStatus) {
        this.mAStatus = mAStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mAID);
        parcel.writeInt(mAProfID);
        parcel.writeInt(mAMarketID);
        parcel.writeString(mAMessage);
        parcel.writeString(mADate);
        parcel.writeString(mAAnouncer);
        parcel.writeString(mAStatus);
    }
}
