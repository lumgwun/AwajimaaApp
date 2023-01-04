package com.skylightapp.MapAndLoc;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_REPORT_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_ID;
import static com.skylightapp.MapAndLoc.FenceEvent.FENCE_EVENT_TABLE;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.location.Geofence;

import java.io.Serializable;

public class ERGeofenceResponse implements Parcelable, Serializable {

    public static final String GEOF_RESPONSE_TABLE = "geof_Table";
    public static final String GEOF__DB_ID = "geof_DB_ID";
    public static final String GEOF__PROF_ID = "geof_Prof_ID";
    public static final String GEOF__EMERG_ID = "geof_Emerg_ID";
    public static final String GEOF_EVENT_ID = "geof_Event_ID";
    public static final String GEOF__DEVICE_ID = "geof_Device_ID";
    public static final String GEOF__START_TIME = "geof_Start_Time";
    public static final String GEOF__END_TIME = "geof_End_Time";
    public static final String GEOF_STATE = "geof_State";
    public static final String GEOF__A_COUNT = "geof_appear_Count";
    public static final String GEOF__STATUS_A = "geof_Status";
    public static final String GEOF_ID = "geof_ID";
    public static final String GEOF__DATE = "geof_Date";
    public static final String GEOF_PROF_NAME = "geof_Prof_Name";

    public static final String CREATE_GEOF_RESPONSE_TABLE = "CREATE TABLE " + GEOF_RESPONSE_TABLE + " (" + GEOF__DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GEOF_ID + " INTEGER, " + GEOF__EMERG_ID + " INTEGER, " + GEOF_EVENT_ID + " TEXT, " + GEOF__PROF_ID + " TEXT, " + GEOF__DEVICE_ID + " TEXT, " + GEOF_PROF_NAME + " TEXT, " +
            GEOF_STATE + " TEXT, " + GEOF__DATE + " TEXT, " + GEOF__START_TIME + " TEXT, "+ GEOF__END_TIME + " TEXT, "+ GEOF__STATUS_A + " TEXT, "+ GEOF__A_COUNT + " TEXT, "+ "FOREIGN KEY(" + GEOF_EVENT_ID + ") REFERENCES " + FENCE_EVENT_TABLE + "(" + FENCE_EVENT_ID + "),"+ "FOREIGN KEY(" + GEOF__EMERG_ID + ") REFERENCES " + EMERGENCY_REPORT_TABLE + "(" + EMERG_REPORT_ID + "),"+ "FOREIGN KEY(" + GEOF__PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";




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
    private int aLGFEventID;

    public ERGeofenceResponse() {
        super();

    }
    protected ERGeofenceResponse(Parcel in) {
        aLGFCount = in.readInt();
        aLGFID = in.readInt();
        aLGFEmergID = in.readInt();
        aLGFDeviceID = in.readInt();
        aLGFEntranceTime = in.readString();
        aLGFExitTime = in.readString();
        aLGFStatus = in.readString();
        aLGFDate = in.readString();
    }

    public static final Creator<ERGeofenceResponse> CREATOR = new Creator<ERGeofenceResponse>() {
        @Override
        public ERGeofenceResponse createFromParcel(Parcel in) {
            return new ERGeofenceResponse(in);
        }

        @Override
        public ERGeofenceResponse[] newArray(int size) {
            return new ERGeofenceResponse[size];
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

    public int getaLGFEventID() {
        return aLGFEventID;
    }

    public void setaLGFEventID(int aLGFEventID) {
        this.aLGFEventID = aLGFEventID;
    }
}
