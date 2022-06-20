package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class TellerCountData {
    private int tellerID;
    private String tellerName;
    private int countData;
    private String countDuration;


    public TellerCountData() {
        super();

    }
    public TellerCountData(int tellerID,String tellerName,int countData,String countDuration) {
        super();
        this.tellerID=tellerID;
        this.tellerName=tellerName;
        this.countData=countData;
        this.countDuration=countDuration;

    }
    public TellerCountData(String tellerName,int countData,String countDuration) {
        super();
        this.tellerName=tellerName;
        this.countData=countData;
        this.countDuration=countDuration;

    }


    public int getTellerID() {
        return tellerID;
    }

    public void setTellerID(int tellerID) {
        this.tellerID = tellerID;
    }

    public String getTellerName() {
        return tellerName;
    }

    public void setTellerName(String tellerName) {
        this.tellerName = tellerName;
    }

    public int getCountData() {
        return countData;
    }

    public void setCountData(int countData) {
        this.countData = countData;
    }

    public String getCountDuration() {
        return countDuration;
    }

    public void setCountDuration(String countDuration) {
        this.countDuration = countDuration;
    }


}
