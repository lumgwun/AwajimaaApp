package com.skylightapp.Bookings;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TripBooking implements Parcelable, Serializable {
    private int tripBID;
    private String tripBDest;
    private String tripBTime;
    private String tripBModeOfPayment;
    private double tripBAmount;
    private int tripBNoOfAdult;
    private int tripBNoOfChildren;
    private int tripBProfID;
    private int tripBLuggageSize;
    private String tripBStatus;
    private String tripBName;
    private String tripBNIN;
    private double tripBLuggageAmt;
    public TripBooking() {
        super();
    }

    protected TripBooking(Parcel in) {
        tripBID = in.readInt();
        tripBDest = in.readString();
        tripBTime = in.readString();
        tripBModeOfPayment = in.readString();
        tripBAmount = in.readDouble();
        tripBNoOfAdult = in.readInt();
        tripBNoOfChildren = in.readInt();
        tripBProfID = in.readInt();
        tripBLuggageSize = in.readInt();
        tripBStatus = in.readString();
        tripBName = in.readString();
        tripBNIN = in.readString();
        tripBLuggageAmt = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tripBID);
        dest.writeString(tripBDest);
        dest.writeString(tripBTime);
        dest.writeString(tripBModeOfPayment);
        dest.writeDouble(tripBAmount);
        dest.writeInt(tripBNoOfAdult);
        dest.writeInt(tripBNoOfChildren);
        dest.writeInt(tripBProfID);
        dest.writeInt(tripBLuggageSize);
        dest.writeString(tripBStatus);
        dest.writeString(tripBName);
        dest.writeString(tripBNIN);
        dest.writeDouble(tripBLuggageAmt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TripBooking> CREATOR = new Creator<TripBooking>() {
        @Override
        public TripBooking createFromParcel(Parcel in) {
            return new TripBooking(in);
        }

        @Override
        public TripBooking[] newArray(int size) {
            return new TripBooking[size];
        }
    };

    public int getTripBNoOfAdult() {
        return tripBNoOfAdult;
    }

    public void setTripBNoOfAdult(int tripBNoOfAdult) {
        this.tripBNoOfAdult = tripBNoOfAdult;
    }

    public int getTripBID() {
        return tripBID;
    }

    public void setTripBID(int tripBID) {
        this.tripBID = tripBID;
    }

    public String getTripBDest() {
        return tripBDest;
    }

    public void setTripBDest(String tripBDest) {
        this.tripBDest = tripBDest;
    }

    public String getTripBTime() {
        return tripBTime;
    }

    public void setTripBTime(String tripBTime) {
        this.tripBTime = tripBTime;
    }

    public String getTripBModeOfPayment() {
        return tripBModeOfPayment;
    }

    public void setTripBModeOfPayment(String tripBModeOfPayment) {
        this.tripBModeOfPayment = tripBModeOfPayment;
    }

    public double getTripBAmount() {
        return tripBAmount;
    }

    public void setTripBAmount(double tripBAmount) {
        this.tripBAmount = tripBAmount;
    }

    public int getTripBNoOfChildren() {
        return tripBNoOfChildren;
    }

    public void setTripBNoOfChildren(int tripBNoOfChildren) {
        this.tripBNoOfChildren = tripBNoOfChildren;
    }

    public int getTripBProfID() {
        return tripBProfID;
    }

    public void setTripBProfID(int tripBProfID) {
        this.tripBProfID = tripBProfID;
    }

    public int getTripBLuggageSize() {
        return tripBLuggageSize;
    }

    public void setTripBLuggageSize(int tripBLuggageSize) {
        this.tripBLuggageSize = tripBLuggageSize;
    }

    public String getTripBStatus() {
        return tripBStatus;
    }

    public void setTripBStatus(String tripBStatus) {
        this.tripBStatus = tripBStatus;
    }

    public String getTripBName() {
        return tripBName;
    }

    public void setTripBName(String tripBName) {
        this.tripBName = tripBName;
    }

    public String getTripBNIN() {
        return tripBNIN;
    }

    public void setTripBNIN(String tripBNIN) {
        this.tripBNIN = tripBNIN;
    }

    public double getTripBLuggageAmt() {
        return tripBLuggageAmt;
    }

    public void setTripBLuggageAmt(double tripBLuggageAmt) {
        this.tripBLuggageAmt = tripBLuggageAmt;
    }
}
