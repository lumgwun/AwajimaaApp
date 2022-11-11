package com.skylightapp.Bookings;

import com.google.android.gms.maps.model.LatLng;

public class StationDischarge {
    private int dischargeID;
    private String dischargeStartTime;
    private String dischargeEndTime;
    private int noOfLuggageDischarged;
    private int noOfLuggageIntake;
    private int noOfPassengersDischarged;
    private int noOfPassengerIntake;
    private String dischargeReason;
    private LatLng dischargeLatLng;

    public int getDischargeID() {
        return dischargeID;
    }

    public void setDischargeID(int dischargeID) {
        this.dischargeID = dischargeID;
    }

    public String getDischargeStartTime() {
        return dischargeStartTime;
    }

    public void setDischargeStartTime(String dischargeStartTime) {
        this.dischargeStartTime = dischargeStartTime;
    }

    public String getDischargeEndTime() {
        return dischargeEndTime;
    }

    public void setDischargeEndTime(String dischargeEndTime) {
        this.dischargeEndTime = dischargeEndTime;
    }

    public int getNoOfLuggageDischarged() {
        return noOfLuggageDischarged;
    }

    public void setNoOfLuggageDischarged(int noOfLuggageDischarged) {
        this.noOfLuggageDischarged = noOfLuggageDischarged;
    }

    public int getNoOfLuggageIntake() {
        return noOfLuggageIntake;
    }

    public void setNoOfLuggageIntake(int noOfLuggageIntake) {
        this.noOfLuggageIntake = noOfLuggageIntake;
    }

    public int getNoOfPassengersDischarged() {
        return noOfPassengersDischarged;
    }

    public void setNoOfPassengersDischarged(int noOfPassengersDischarged) {
        this.noOfPassengersDischarged = noOfPassengersDischarged;
    }

    public int getNoOfPassengerIntake() {
        return noOfPassengerIntake;
    }

    public void setNoOfPassengerIntake(int noOfPassengerIntake) {
        this.noOfPassengerIntake = noOfPassengerIntake;
    }

    public String getDischargeReason() {
        return dischargeReason;
    }

    public void setDischargeReason(String dischargeReason) {
        this.dischargeReason = dischargeReason;
    }

    public LatLng getDischargeLatLng() {
        return dischargeLatLng;
    }

    public void setDischargeLatLng(LatLng dischargeLatLng) {
        this.dischargeLatLng = dischargeLatLng;
    }
}
