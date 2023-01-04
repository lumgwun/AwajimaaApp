package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class RegionLog implements Parcelable, Serializable, BaseColumns {
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

    public RegionLog() {
        super();

    }

    protected RegionLog(Parcel in) {
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

    public static final Creator<RegionLog> CREATOR = new Creator<RegionLog>() {
        @Override
        public RegionLog createFromParcel(Parcel in) {
            return new RegionLog(in);
        }

        @Override
        public RegionLog[] newArray(int size) {
            return new RegionLog[size];
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

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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
}
