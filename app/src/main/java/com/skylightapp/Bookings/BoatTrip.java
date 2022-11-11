package com.skylightapp.Bookings;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.Profile;

import java.io.Serializable;
import java.util.ArrayList;

public class BoatTrip implements Parcelable, Serializable {
    public static final String BOAT_TRIP_TABLE = "boat_trip_Table";
    public static final String BOAT_TRIP_ID = "boatTrip_id";
    public static final String BOAT_TRIP_PROF_ID = "boatTrip_Prof_id";
    public static final String BOAT_TRIP_STATE = "boatTrip_State";
    public static final String BOAT_TRIP_AMOUNT = "boatTrip_Amount";
    public static final String BOAT_TRIP_STATUS = "boatTrip_Status";
    public static final String BOAT_TRIP_NO_OF_SIT = "boatTrip_Sits_No";
    public static final String BOAT_TRIP_STATION = "boatTrip_Station";
    public static final String BOAT_TRIP_DEST_NAME = "boatTrip_Dest_Name";
    public static final String BOAT_TRIP_TOTAL_AMT = "boatTrip_TotalA";
    public static final String BOAT_TRIP_START_TIME = "boatTrip_StartT";
    public static final String BOAT_TRIP_ENDT = "boatTrip_EndT";
    public static final String BOAT_TRIP_DATE = "boatTrip_Date";
    private int boatTripID;
    private int boatTripProfID;
    private int boatTripNoOfSits;
    private String btLoadingPoint;
    private String btLoadingPointLat;
    private String btLoadingPointLng;
    private String btDate;
    private String btStartTime;
    private String btEndTime;
    private ArrayList<LatLng>btPickUpPoints;
    private ArrayList<LatLng>btDischargePoints;
    private ArrayList<TripBooking>btBookings;
    private String btStatus;
    private double btAmount;
    private double btTotalAmount;
    private String btState;
    private String btFinalDestination;
    private LatLng btFinalDestLatLng;
    private LatLng btTakeOffLatLng;
    private int boatTripDuration;
    private ArrayList<Profile>btUsers;

    public BoatTrip() {
        super();
    }

    protected BoatTrip(Parcel in) {
        boatTripID = in.readInt();
        boatTripProfID = in.readInt();
        boatTripNoOfSits = in.readInt();
        btLoadingPoint = in.readString();
        btLoadingPointLat = in.readString();
        btLoadingPointLng = in.readString();
        btDate = in.readString();
        btStartTime = in.readString();
        btEndTime = in.readString();
        btPickUpPoints = in.createTypedArrayList(LatLng.CREATOR);
        btDischargePoints = in.createTypedArrayList(LatLng.CREATOR);
        btStatus = in.readString();
        btAmount = in.readDouble();
        btTotalAmount = in.readDouble();
        btState = in.readString();
        btFinalDestination = in.readString();
        btFinalDestLatLng = in.readParcelable(LatLng.class.getClassLoader());
        btTakeOffLatLng = in.readParcelable(LatLng.class.getClassLoader());
        boatTripDuration = in.readInt();
        btUsers = in.createTypedArrayList(Profile.CREATOR);
    }

    public static final Creator<BoatTrip> CREATOR = new Creator<BoatTrip>() {
        @Override
        public BoatTrip createFromParcel(Parcel in) {
            return new BoatTrip(in);
        }

        @Override
        public BoatTrip[] newArray(int size) {
            return new BoatTrip[size];
        }
    };

    public int getBoatTripID() {
        return boatTripID;
    }

    public void setBoatTripID(int boatTripID) {
        this.boatTripID = boatTripID;
    }

    public String getBtLoadingPoint() {
        return btLoadingPoint;
    }

    public void setBtLoadingPoint(String btLoadingPoint) {
        this.btLoadingPoint = btLoadingPoint;
    }

    public String getBtLoadingPointLat() {
        return btLoadingPointLat;
    }

    public void setBtLoadingPointLat(String btLoadingPointLat) {
        this.btLoadingPointLat = btLoadingPointLat;
    }

    public String getBtLoadingPointLng() {
        return btLoadingPointLng;
    }

    public void setBtLoadingPointLng(String btLoadingPointLng) {
        this.btLoadingPointLng = btLoadingPointLng;
    }

    public String getBtDate() {
        return btDate;
    }

    public void setBtDate(String btDate) {
        this.btDate = btDate;
    }

    public String getBtStartTime() {
        return btStartTime;
    }

    public void setBtStartTime(String btStartTime) {
        this.btStartTime = btStartTime;
    }

    public String getBtEndTime() {
        return btEndTime;
    }

    public void setBtEndTime(String btEndTime) {
        this.btEndTime = btEndTime;
    }

    public ArrayList<LatLng> getBtPickUpPoints() {
        return btPickUpPoints;
    }

    public void setBtPickUpPoints(ArrayList<LatLng> btPickUpPoints) {
        this.btPickUpPoints = btPickUpPoints;
    }

    public ArrayList<LatLng> getBtDischargePoints() {
        return btDischargePoints;
    }

    public void setBtDischargePoints(ArrayList<LatLng> btDischargePoints) {
        this.btDischargePoints = btDischargePoints;
    }

    public String getBtStatus() {
        return btStatus;
    }

    public void setBtStatus(String btStatus) {
        this.btStatus = btStatus;
    }

    public double getBtAmount() {
        return btAmount;
    }

    public void setBtAmount(double btAmount) {
        this.btAmount = btAmount;
    }

    public double getBtTotalAmount() {
        return btTotalAmount;
    }

    public void setBtTotalAmount(double btTotalAmount) {
        this.btTotalAmount = btTotalAmount;
    }

    public String getBtState() {
        return btState;
    }

    public void setBtState(String btState) {
        this.btState = btState;
    }

    public int getBoatTripProfID() {
        return boatTripProfID;
    }

    public void setBoatTripProfID(int boatTripProfID) {
        this.boatTripProfID = boatTripProfID;
    }

    public int getBoatTripNoOfSits() {
        return boatTripNoOfSits;
    }

    public void setBoatTripNoOfSits(int boatTripNoOfSits) {
        this.boatTripNoOfSits = boatTripNoOfSits;
    }

    public String getBtFinalDestination() {
        return btFinalDestination;
    }

    public void setBtFinalDestination(String btFinalDestination) {
        this.btFinalDestination = btFinalDestination;
    }

    public LatLng getBtFinalDestLatLng() {
        return btFinalDestLatLng;
    }

    public void setBtFinalDestLatLng(LatLng btFinalDestLatLng) {
        this.btFinalDestLatLng = btFinalDestLatLng;
    }

    public LatLng getBtTakeOffLatLng() {
        return btTakeOffLatLng;
    }

    public void setBtTakeOffLatLng(LatLng btTakeOffLatLng) {
        this.btTakeOffLatLng = btTakeOffLatLng;
    }

    public int getBoatTripDuration() {
        return boatTripDuration;
    }

    public void setBoatTripDuration(int boatTripDuration) {
        this.boatTripDuration = boatTripDuration;
    }

    public ArrayList<Profile> getBtUsers() {
        return btUsers;
    }

    public void setBtUsers(ArrayList<Profile> btUsers) {
        this.btUsers = btUsers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(boatTripID);
        parcel.writeInt(boatTripProfID);
        parcel.writeInt(boatTripNoOfSits);
        parcel.writeString(btLoadingPoint);
        parcel.writeString(btLoadingPointLat);
        parcel.writeString(btLoadingPointLng);
        parcel.writeString(btDate);
        parcel.writeString(btStartTime);
        parcel.writeString(btEndTime);
        parcel.writeTypedList(btPickUpPoints);
        parcel.writeTypedList(btDischargePoints);
        parcel.writeString(btStatus);
        parcel.writeDouble(btAmount);
        parcel.writeDouble(btTotalAmount);
        parcel.writeString(btState);
        parcel.writeString(btFinalDestination);
        parcel.writeParcelable(btFinalDestLatLng, i);
        parcel.writeParcelable(btTakeOffLatLng, i);
        parcel.writeInt(boatTripDuration);
        parcel.writeTypedList(btUsers);
    }

    public ArrayList<TripBooking> getBtBookings() {
        return btBookings;
    }

    public void setBtBookings(ArrayList<TripBooking> btBookings) {
        this.btBookings = btBookings;
    }
}
