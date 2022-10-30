package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.location.Geofence;

import java.io.Serializable;
import java.util.ArrayList;

public class ALocGeoFence implements Parcelable, Serializable {
    private int aLGFCount;
    private int aLGFID;
    private int aLGFEmergID;
    private int aLGFDeviceID;
    private int aLGFProfID;
    private Geofence aLGFGeofence;
    private String aLGFEntranceTime;
    private String aLGFExitTime;
    private String aLGFStatus;
    private String aLGFDate;
    private String aLGFProfName;
    private String aLGFCity;
    private String aLGFState;

    public ALocGeoFence() {
        super();

    }
    protected ALocGeoFence(Parcel in) {
        aLGFCount = in.readInt();
        aLGFID = in.readInt();
        aLGFEmergID = in.readInt();
        aLGFDeviceID = in.readInt();
        aLGFEntranceTime = in.readString();
        aLGFExitTime = in.readString();
        aLGFStatus = in.readString();
        aLGFDate = in.readString();
    }

    public static final Creator<ALocGeoFence> CREATOR = new Creator<ALocGeoFence>() {
        @Override
        public ALocGeoFence createFromParcel(Parcel in) {
            return new ALocGeoFence(in);
        }

        @Override
        public ALocGeoFence[] newArray(int size) {
            return new ALocGeoFence[size];
        }
    };

    public int getaLGFCount() {
        return aLGFCount;
    }

    public void setaLGFCount(int aLGFCount) {
        this.aLGFCount = aLGFCount;
    }

    public int getaLGFID() {
        return aLGFID;
    }

    public void setaLGFID(int aLGFID) {
        this.aLGFID = aLGFID;
    }

    public int getaLGFEmergID() {
        return aLGFEmergID;
    }

    public void setaLGFEmergID(int aLGFEmergID) {
        this.aLGFEmergID = aLGFEmergID;
    }

    public Geofence getaLGFGeofence() {
        return aLGFGeofence;
    }

    public void setaLGFGeofence(Geofence aLGFGeofence) {
        this.aLGFGeofence = aLGFGeofence;
    }

    public String getaLGFEntranceTime() {
        return aLGFEntranceTime;
    }

    public void setaLGFEntranceTime(String aLGFEntranceTime) {
        this.aLGFEntranceTime = aLGFEntranceTime;
    }

    public String getaLGFExitTime() {
        return aLGFExitTime;
    }

    public void setaLGFExitTime(String aLGFExitTime) {
        this.aLGFExitTime = aLGFExitTime;
    }

    public String getaLGFStatus() {
        return aLGFStatus;
    }

    public void setaLGFStatus(String aLGFStatus) {
        this.aLGFStatus = aLGFStatus;
    }

    public String getaLGFDate() {
        return aLGFDate;
    }

    public void setaLGFDate(String aLGFDate) {
        this.aLGFDate = aLGFDate;
    }

    public int getaLGFDeviceID() {
        return aLGFDeviceID;
    }

    public void setaLGFDeviceID(int aLGFDeviceID) {
        this.aLGFDeviceID = aLGFDeviceID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(aLGFCount);
        parcel.writeInt(aLGFID);
        parcel.writeInt(aLGFEmergID);
        parcel.writeInt(aLGFDeviceID);
        parcel.writeString(aLGFEntranceTime);
        parcel.writeString(aLGFExitTime);
        parcel.writeString(aLGFStatus);
        parcel.writeString(aLGFDate);
    }

    public String getaLGFState() {
        return aLGFState;
    }

    public void setaLGFState(String aLGFState) {
        this.aLGFState = aLGFState;
    }

    public String getaLGFProfName() {
        return aLGFProfName;
    }

    public void setaLGFProfName(String aLGFProfName) {
        this.aLGFProfName = aLGFProfName;
    }

    public String getaLGFCity() {
        return aLGFCity;
    }

    public void setaLGFCity(String aLGFCity) {
        this.aLGFCity = aLGFCity;
    }

    public int getaLGFProfID() {
        return aLGFProfID;
    }

    public void setaLGFProfID(int aLGFProfID) {
        this.aLGFProfID = aLGFProfID;
    }
}
