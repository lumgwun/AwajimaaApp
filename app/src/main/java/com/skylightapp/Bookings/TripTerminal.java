package com.skylightapp.Bookings;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class TripTerminal {
    private int stationID;
    private LatLng stationLatLng;
    private String stationName;
    private String stationAddress;
    private String stationManager;
    private String stationLGA;
    private String stationState;
    private String stationPhone;
    private String stationEmail;
    private ArrayList<TripBooking> bookingArrayList;

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public LatLng getStationLatLng() {
        return stationLatLng;
    }

    public void setStationLatLng(LatLng stationLatLng) {
        this.stationLatLng = stationLatLng;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationAddress() {
        return stationAddress;
    }

    public void setStationAddress(String stationAddress) {
        this.stationAddress = stationAddress;
    }

    public String getStationManager() {
        return stationManager;
    }

    public void setStationManager(String stationManager) {
        this.stationManager = stationManager;
    }

    public String getStationLGA() {
        return stationLGA;
    }

    public void setStationLGA(String stationLGA) {
        this.stationLGA = stationLGA;
    }

    public String getStationState() {
        return stationState;
    }

    public void setStationState(String stationState) {
        this.stationState = stationState;
    }

    public String getStationPhone() {
        return stationPhone;
    }

    public void setStationPhone(String stationPhone) {
        this.stationPhone = stationPhone;
    }

    public String getStationEmail() {
        return stationEmail;
    }

    public void setStationEmail(String stationEmail) {
        this.stationEmail = stationEmail;
    }

    public ArrayList<TripBooking> getBookingArrayList() {
        return bookingArrayList;
    }

    public void setBookingArrayList(ArrayList<TripBooking> bookingArrayList) {
        this.bookingArrayList = bookingArrayList;
    }
}
