package com.skylightapp.MapAndLoc;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Distance implements Parcelable, Serializable, BaseColumns {

    public static final String DISTANCE_TABLE = "distance_Table";
    public static final String DISTANCE_TIME = "distance_Time";
    public static final String DISTANCE_ID = "distance_ID";
    public static final String DISTANCE_ORIGIN_LAT = "distance_Origin_Lat";
    public static final String DISTANCE_ORIGIN_LNG = "distance_Origin_Lng";
    public static final String DISTANCE_DEST_LAT = "distance_Dest_Lat";
    public static final String DISTANCE_DEST_LNG = "distance_Dest_Lng";
    public static final String DISTANCE_VALUE = "distance_Value";
    public static final String DISTANCE_DURATION = "distance_Duration";
    public static final String DISTANCE_TEXT = "distance_Text";
    public static final String DISTANCE_MODE = "distance_Mode";
    public static final String DISTANCE_UNIT = "distance_Unit";
    public static final String DISTANCE_ROUTING = "distance_Routing";
    public static final String DISTANCE_LANGUAGE = "distance_Language";
    public static final String DISTANCE_STATUS = "distance_Status";
    public static final String DISTANCE_DBID = "distance_DBID";
    public static final String DISTANCE_STATE = "distance_State";

    public static final String CREATE_DISTANCE_TABLE = "CREATE TABLE IF NOT EXISTS " + DISTANCE_TABLE + " (" + DISTANCE_DBID + " INTEGER, "+ DISTANCE_ID + " INTEGER, " +
            DISTANCE_TIME + " TEXT , " + DISTANCE_ORIGIN_LAT + " REAL , " + DISTANCE_ORIGIN_LNG + " REAL, " +
            DISTANCE_DEST_LAT + " REAL, " + DISTANCE_DEST_LNG + " REAL, " + DISTANCE_VALUE + " TEXT, " + DISTANCE_DURATION + " TEXT, " +
            DISTANCE_TEXT  + " INTEGER, "+ DISTANCE_MODE + " TEXT, " + DISTANCE_UNIT + " TEXT, "+ DISTANCE_ROUTING + " TEXT, "+ DISTANCE_LANGUAGE + " TEXT, "+ DISTANCE_STATUS + " TEXT, "+ DISTANCE_STATE + " TEXT , " +"PRIMARY KEY(" +DISTANCE_DBID + "))";

    private int id;
    private long dateTime;
    private double originLatitude;
    private double originLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
    private int distance;
    private String distanceText;
    private int duration;
    private String durationText;
    private String mode;
    private String units;
    private String routing;
    private String language;
    private int locationId;
    private String distanceState;
    private EmergencyReport distanceReport;
    private String distanceStatus;
    private String distanceType;

    public Distance() {
        super();

    }
    public Distance(int distanceID, String type, int distance, String unit, long time, double startLat, double startLng, double endLat, double endLng, String state, String mode, String text, int duration, String routing, String language, String status) {
        super();
        this.id = distanceID;
        this.distanceType = type;
        this.distance = distance;
        this.units = unit;
        this.dateTime = time;
        this.originLatitude = startLat;
        this.originLongitude = startLng;
        this.destinationLatitude = endLat;
        this.destinationLongitude = endLng;
        this.distanceState = state;
        this.mode = mode;
        this.distanceText = text;
        this.duration = duration;
        this.routing = routing;
        this.language = language;
        this.distanceStatus = status;
    }

    protected Distance(Parcel in) {
        id = in.readInt();
        dateTime = in.readLong();
        originLatitude = in.readDouble();
        originLongitude = in.readDouble();
        destinationLatitude = in.readDouble();
        destinationLongitude = in.readDouble();
        distance = in.readInt();
        distanceText = in.readString();
        duration = in.readInt();
        durationText = in.readString();
        mode = in.readString();
        units = in.readString();
        routing = in.readString();
        language = in.readString();
        locationId = in.readInt();
    }

    public static final Creator<Distance> CREATOR = new Creator<Distance>() {
        @Override
        public Distance createFromParcel(Parcel in) {
            return new Distance(in);
        }

        @Override
        public Distance[] newArray(int size) {
            return new Distance[size];
        }
    };



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public double getOriginLatitude() {
        return originLatitude;
    }

    public void setOriginLatitude(double originLatitude) {
        this.originLatitude = originLatitude;
    }

    public double getOriginLongitude() {
        return originLongitude;
    }

    public void setOriginLongitude(double originLongitude) {
        this.originLongitude = originLongitude;
    }

    public double getDestinationLatitude() {
        return destinationLatitude;
    }

    public void setDestinationLatitude(double destinationLatitude) {
        this.destinationLatitude = destinationLatitude;
    }

    public double getDestinationLongitude() {
        return destinationLongitude;
    }

    public void setDestinationLongitude(double destinationLongitude) {
        this.destinationLongitude = destinationLongitude;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getDistanceText() {
        return distanceText;
    }

    public void setDistanceText(String distanceText) {
        this.distanceText = distanceText;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationText() {
        return durationText;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeLong(dateTime);
        parcel.writeDouble(originLatitude);
        parcel.writeDouble(originLongitude);
        parcel.writeDouble(destinationLatitude);
        parcel.writeDouble(destinationLongitude);
        parcel.writeInt(distance);
        parcel.writeString(distanceText);
        parcel.writeInt(duration);
        parcel.writeString(durationText);
        parcel.writeString(mode);
        parcel.writeString(units);
        parcel.writeString(routing);
        parcel.writeString(language);
        parcel.writeInt(locationId);
    }


    public EmergencyReport getDistanceReport() {
        return distanceReport;
    }

    public void setDistanceReport(EmergencyReport distanceReport) {
        this.distanceReport = distanceReport;
    }

    public String getDistanceState() {
        return distanceState;
    }

    public void setDistanceState(String distanceState) {
        this.distanceState = distanceState;
    }

    public String getDistanceStatus() {
        return distanceStatus;
    }

    public void setDistanceStatus(String distanceStatus) {
        this.distanceStatus = distanceStatus;
    }

    public String getDistanceType() {
        return distanceType;
    }

    public void setDistanceType(String distanceType) {
        this.distanceType = distanceType;
    }
}
