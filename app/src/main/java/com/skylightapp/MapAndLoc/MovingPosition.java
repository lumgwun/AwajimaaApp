package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class MovingPosition implements Parcelable, Serializable, BaseColumns {
    private int id;
    private double lat;
    private double lng;
    private float accuracy;
    private float speed = 0;
    private long dateTime;
    private int isUpload = 0;
    private String status;

    public MovingPosition() {
        super();

    }

    protected MovingPosition(Parcel in) {
        id = in.readInt();
        lat = in.readDouble();
        lng = in.readDouble();
        accuracy = in.readFloat();
        speed = in.readFloat();
        dateTime = in.readLong();
        isUpload = in.readInt();
        status = in.readString();
    }

    public static final Creator<MovingPosition> CREATOR = new Creator<MovingPosition>() {
        @Override
        public MovingPosition createFromParcel(Parcel in) {
            return new MovingPosition(in);
        }

        @Override
        public MovingPosition[] newArray(int size) {
            return new MovingPosition[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(int isUpload) {
        this.isUpload = isUpload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeFloat(accuracy);
        parcel.writeFloat(speed);
        parcel.writeLong(dateTime);
        parcel.writeInt(isUpload);
        parcel.writeString(status);
    }
}
