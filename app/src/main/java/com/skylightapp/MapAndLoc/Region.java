package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Region implements Parcelable, Serializable, BaseColumns {

    public static final String REGION_TABLE = "region_Table";
    public static final String REGION_DATE_TIME = "region_DTime";
    public static final String REGION_ID = "region_ID";
    public static final String REGION_DBID = "region_DBID";
    public static final String REGION_LAT = "region_Lat";
    public static final String REGION_LNG = "region_Lng";
    public static final String REGION_DID_ENTER = "region_did_enter";
    public static final String REGION_RADIUS = "region_radius";
    public static final String REGION_IS_STORE = "region_Is_Store";
    public static final String REGION_IS_CURRENTLY_INSIDE = "region_Is_Inside";
    public static final String REGION_DISTANCE = "region_distance";
    public static final String REGION_DISTANCE_TEXT = "region_distance_Text";
    public static final String REGION_DURATION = "region_duration";
    public static final String REGION_DURATION_TEXT = "region_duration_Text";
    public static final String REGION_TYPE = "region_Type";
    public static final String REGION_TYPE_EXP_SPEED = "region_Exp_Speed";
    public static final String REGION_STATUS = "region_Status";
    public static final String REGION_PARAM_TYPE = "region_Param_Type";
    public static final String REGION_STATE = "region_State";

    public static final String CREATE_REGION_TABLE = "CREATE TABLE IF NOT EXISTS " + REGION_TABLE + " (" + REGION_DBID + " INTEGER, "+ REGION_ID + " INTEGER, " +
            REGION_LAT + " TEXT , " + REGION_LNG + " REAL , " + REGION_DATE_TIME + " LONG, " +
            REGION_DID_ENTER + " TEXT, " + REGION_RADIUS + " REAL, " + REGION_IS_STORE + " TEXT, " + REGION_IS_CURRENTLY_INSIDE + " TEXT, " +
            REGION_DISTANCE  + " INTEGER, "+ REGION_DISTANCE_TEXT + " TEXT, " + REGION_DURATION + " INTEGER, "+ REGION_DURATION_TEXT + " TEXT, "+ REGION_TYPE + " TEXT, "+ REGION_TYPE_EXP_SPEED + " REAL, "+ REGION_STATUS + " TEXT , "+ REGION_PARAM_TYPE + " TEXT , "+ REGION_STATE + " TEXT, " +"PRIMARY KEY(" +REGION_DBID + "))";

    public int id;
    public int locationId;
    public String identifier;
    public double lat;
    public double lng;
    public double radius;
    public boolean didEnter = false;
    public String idStore = "";
    public long dateTime;
    public boolean isCurrentPositionInside = false;
    public int distance = 0;
    public String distanceText = "";
    public int duration = 0;
    public String durationText = "";
    public String type = "circle";
    public float expectedAverageSpeed=-1f;
    public String status ;
    public String paramType ;
    public EmergencyReport emergencyReport;

    public Region() {
        super();

    }
    public Region(int regionID, double lat, double lng, long time, boolean isEnter, double radius, boolean isCurrentlyInside, int distance, String distanceText, int duration, String durationText, String type, float speed, String paramType, String status) {
        super();
        this.id = regionID;
        this.lat = lat;
        this.lng = lng;
        this.dateTime = time;
        this.didEnter = isEnter;
        this.radius = radius;
        this.isCurrentPositionInside = isCurrentlyInside;
        this.distance = distance;
        this.durationText = distanceText;
        this.duration = duration;
        this.durationText = durationText;
        this.type = type;
        this.expectedAverageSpeed = speed;
        this.paramType = paramType;
        this.status = status;
    }

    protected Region(Parcel in) {
        id = in.readInt();
        locationId = in.readInt();
        identifier = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        radius = in.readDouble();
        didEnter = in.readByte() != 0;
        idStore = in.readString();
        dateTime = in.readLong();
        isCurrentPositionInside = in.readByte() != 0;
        distance = in.readInt();
        distanceText = in.readString();
        duration = in.readInt();
        durationText = in.readString();
        type = in.readString();
        expectedAverageSpeed = in.readFloat();
    }

    public static final Creator<Region> CREATOR = new Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel in) {
            return new Region(in);
        }

        @Override
        public Region[] newArray(int size) {
            return new Region[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isDidEnter() {
        return didEnter;
    }

    public void setDidEnter(boolean didEnter) {
        this.didEnter = didEnter;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public boolean isCurrentPositionInside() {
        return isCurrentPositionInside;
    }

    public void setCurrentPositionInside(boolean currentPositionInside) {
        isCurrentPositionInside = currentPositionInside;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getExpectedAverageSpeed() {
        return expectedAverageSpeed;
    }

    public void setExpectedAverageSpeed(float expectedAverageSpeed) {
        this.expectedAverageSpeed = expectedAverageSpeed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(locationId);
        parcel.writeString(identifier);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeDouble(radius);
        parcel.writeByte((byte) (didEnter ? 1 : 0));
        parcel.writeString(idStore);
        parcel.writeLong(dateTime);
        parcel.writeByte((byte) (isCurrentPositionInside ? 1 : 0));
        parcel.writeInt(distance);
        parcel.writeString(distanceText);
        parcel.writeInt(duration);
        parcel.writeString(durationText);
        parcel.writeString(type);
        parcel.writeFloat(expectedAverageSpeed);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public EmergencyReport getEmergencyReport() {
        return emergencyReport;
    }

    public void setEmergencyReport(EmergencyReport emergencyReport) {
        this.emergencyReport = emergencyReport;
    }
}
