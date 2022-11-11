package com.skylightapp.MapAndLoc;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_LOCID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_ID;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_TABLE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.libraries.places.api.model.Place;

import java.io.Serializable;
import java.util.Date;

public class FenceEvent implements Parcelable, Serializable {
    public static final String FENCE_EVENT_ID = "fence_Event_id";
    public static final String FENCE_EVENT_DBID = "fence_Event_db_id";
    public static final String FENCE_EVENT_EMERG_ID = "fence_Event_emerg_id";
    public static final String FENCE_EVENT_EMERG_TYPE = "fence_Event_emerg_type";
    public static final String FENCE_EVENT_TIME = "fence_Event_time";
    public static final String FENCE_EVENT_STATUS = "fence_Event_status";
    public static final String FENCE_EVENT_DEVICE_ID = "fence_Event_device_id";
    public static final String FENCE_EVENT_TABLE = "fence_Event_table";
    public static final String FENCE_EVENT_PLACE_ID = "fence_Event_PlaceID";
    public static final String FENCE_EVENT_PROF_NAME = "fence_Event_Prof_Name";

    public static final String CREATE_FENCE_EVENT_TABLE = "CREATE TABLE IF NOT EXISTS " + FENCE_EVENT_TABLE + " (" + FENCE_EVENT_DBID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FENCE_EVENT_ID + " INTEGER, "+
            FENCE_EVENT_EMERG_ID + " INTEGER , " + FENCE_EVENT_EMERG_TYPE + " TEXT , "+ FENCE_EVENT_TIME + " TEXT, " + FENCE_EVENT_DEVICE_ID + " TEXT, " + FENCE_EVENT_PLACE_ID + " TEXT, " + FENCE_EVENT_STATUS + " TEXT, " + FENCE_EVENT_PROF_NAME + " TEXT , " +"FOREIGN KEY(" + FENCE_EVENT_PLACE_ID  + ") REFERENCES " + PLACE_ENTRY_TABLE + "(" + PLACE_ENTRY_ID + ")," +
            "FOREIGN KEY(" + FENCE_EVENT_EMERG_ID + ") REFERENCES " + EMERGENCY_REPORT_TABLE + "(" + EMERGENCY_LOCID + "))";

    private  Date timestamp;
    private  String fence;
    private int eventID;
    private String placeID;
    private int fenceEventEmergID;
    private  String fenceEmergType;
    private  String fenceEventTimeStrg;
    private  String fenceEventEmergStatus;
    private  String fenceEventProfName;
    private  int fenceEventDeviceID;

    protected FenceEvent(Parcel in) {
        fence = in.readString();
        eventID = in.readInt();
        placeID = in.readString();
        fenceEventEmergID = in.readInt();
        fenceEmergType = in.readString();
        fenceEventTimeStrg = in.readString();
        fenceEventEmergStatus = in.readString();
        fenceEventDeviceID = in.readInt();
    }

    public static final Creator<FenceEvent> CREATOR = new Creator<FenceEvent>() {
        @Override
        public FenceEvent createFromParcel(Parcel in) {
            return new FenceEvent(in);
        }

        @Override
        public FenceEvent[] newArray(int size) {
            return new FenceEvent[size];
        }
    };

    public FenceEvent(int fenceEventId, int emergID, int deviceID, String reportTime, String type, String profName, String status) {
        this.fenceEventTimeStrg = reportTime;
        this.fenceEventEmergStatus = status;
        this.fenceEmergType = type;
        this.fenceEventEmergID = emergID;
        this.fenceEventDeviceID = deviceID;
        this.eventID = fenceEventId;
        this.fenceEventProfName = profName;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getFence() {
        return fence;
    }
    public void setFence(String fence) {
        this.fence = fence;
    }

    public int getEventID() {
        return eventID;
    }
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
    public FenceEvent() {
        super();

    }

    public FenceEvent (@NonNull Date t, @NonNull String f, int i)
    {
        timestamp = t;
        fence = f;
        eventID = i;
    }
    public FenceEvent (String fenceEventTimeStrg,String fenceEventEmergStatus, String fenceEmergType,int fenceEventEmergID, int eventID,int fenceEventDeviceID) {
        this.fenceEventTimeStrg = fenceEventTimeStrg;
        this.fenceEventEmergStatus = fenceEventEmergStatus;
        this.fenceEmergType = fenceEmergType;
        this.fenceEventEmergID = fenceEventEmergID;
        this.fenceEventDeviceID = fenceEventDeviceID;
        this.eventID = eventID;
    }

    public @NonNull
    String toString ()
    {
        return "{Time: " + timestamp + ", Fence: " + fence + ", Event: " + eventID +
                "}";
    }

    public int getFenceEventEmergID() {
        return fenceEventEmergID;
    }

    public void setFenceEventEmergID(int fenceEventEmergID) {
        this.fenceEventEmergID = fenceEventEmergID;
    }

    public String getFenceEmergType() {
        return fenceEmergType;
    }

    public void setFenceEmergType(String fenceEmergType) {
        this.fenceEmergType = fenceEmergType;
    }

    public String getFenceEventTimeStrg() {
        return fenceEventTimeStrg;
    }

    public void setFenceEventTimeStrg(String fenceEventTimeStrg) {
        this.fenceEventTimeStrg = fenceEventTimeStrg;
    }

    public String getFenceEventEmergStatus() {
        return fenceEventEmergStatus;
    }

    public void setFenceEventEmergStatus(String fenceEventEmergStatus) {
        this.fenceEventEmergStatus = fenceEventEmergStatus;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fence);
        parcel.writeInt(eventID);
        parcel.writeString(placeID);
        parcel.writeInt(fenceEventEmergID);
        parcel.writeString(fenceEmergType);
        parcel.writeString(fenceEventTimeStrg);
        parcel.writeString(fenceEventEmergStatus);
        parcel.writeInt(fenceEventDeviceID);
    }

    public String getFenceEventProfName() {
        return fenceEventProfName;
    }

    public void setFenceEventProfName(String fenceEventProfName) {
        this.fenceEventProfName = fenceEventProfName;
    }
}
