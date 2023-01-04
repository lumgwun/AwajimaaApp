package com.skylightapp.Bookings;

import android.net.Uri;

import java.io.Serializable;

public class PTour implements Serializable {
    private int tourID;
    double pTourPrice;
    int pTourType, pTourPerson;
    private String tourStatus;
    private String tourState;
    private String tourCountry;
    private String tourDuration;
    private Uri tourBanner;

    public double getpTourPrice() {
        return pTourPrice;
    }

    public void setpTourPrice(double pTourPrice) {
        this.pTourPrice = pTourPrice;
    }

    public int getpTourPerson() {
        return pTourPerson;
    }

    public void setpTourPerson(int pTourPerson) {
        this.pTourPerson = pTourPerson;
    }

    public int getpTourType() {
        return pTourType;
    }

    public void setpTourType(int pTourType) {
        this.pTourType = pTourType;
    }

    public int getTourID() {
        return tourID;
    }

    public void setTourID(int tourID) {
        this.tourID = tourID;
    }

    public String getTourStatus() {
        return tourStatus;
    }

    public void setTourStatus(String tourStatus) {
        this.tourStatus = tourStatus;
    }

    public String getTourState() {
        return tourState;
    }

    public void setTourState(String tourState) {
        this.tourState = tourState;
    }

    public String getTourCountry() {
        return tourCountry;
    }

    public void setTourCountry(String tourCountry) {
        this.tourCountry = tourCountry;
    }

    public String getTourDuration() {
        return tourDuration;
    }

    public void setTourDuration(String tourDuration) {
        this.tourDuration = tourDuration;
    }

    public Uri getTourBanner() {
        return tourBanner;
    }

    public void setTourBanner(Uri tourBanner) {
        this.tourBanner = tourBanner;
    }
}
