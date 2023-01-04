package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class ZOI implements Parcelable, Serializable, BaseColumns {
    private int id;
    private String uuid;
    private ArrayList<String> idVisits = new ArrayList<>();
    private double lngMean;
    private double latMean;
    private double age;
    private double accumulator;
    private double covariance_det;
    private double prior_probability;
    private double x00Covariance_matrix_inverse;
    private double x01Covariance_matrix_inverse;
    private double x10Covariance_matrix_inverse;
    private double x11Covariance_matrix_inverse;
    private String wktPolygon;
    private long startTime;
    private long endTime;
    private long duration;
    private String period;
    private ArrayList<String> weekly_density;
    public ZOI() {
        super();

    }

    protected ZOI(Parcel in) {
        id = in.readInt();
        uuid = in.readString();
        idVisits = in.createStringArrayList();
        lngMean = in.readDouble();
        latMean = in.readDouble();
        age = in.readDouble();
        accumulator = in.readDouble();
        covariance_det = in.readDouble();
        prior_probability = in.readDouble();
        x00Covariance_matrix_inverse = in.readDouble();
        x01Covariance_matrix_inverse = in.readDouble();
        x10Covariance_matrix_inverse = in.readDouble();
        x11Covariance_matrix_inverse = in.readDouble();
        wktPolygon = in.readString();
        startTime = in.readLong();
        endTime = in.readLong();
        duration = in.readLong();
        period = in.readString();
        weekly_density = in.createStringArrayList();
    }

    public static final Creator<ZOI> CREATOR = new Creator<ZOI>() {
        @Override
        public ZOI createFromParcel(Parcel in) {
            return new ZOI(in);
        }

        @Override
        public ZOI[] newArray(int size) {
            return new ZOI[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getLngMean() {
        return lngMean;
    }

    public void setLngMean(double lngMean) {
        this.lngMean = lngMean;
    }

    public double getLatMean() {
        return latMean;
    }

    public void setLatMean(double latMean) {
        this.latMean = latMean;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getAccumulator() {
        return accumulator;
    }

    public void setAccumulator(double accumulator) {
        this.accumulator = accumulator;
    }

    public double getCovariance_det() {
        return covariance_det;
    }

    public void setCovariance_det(double covariance_det) {
        this.covariance_det = covariance_det;
    }

    public double getPrior_probability() {
        return prior_probability;
    }

    public void setPrior_probability(double prior_probability) {
        this.prior_probability = prior_probability;
    }

    public double getX00Covariance_matrix_inverse() {
        return x00Covariance_matrix_inverse;
    }

    public void setX00Covariance_matrix_inverse(double x00Covariance_matrix_inverse) {
        this.x00Covariance_matrix_inverse = x00Covariance_matrix_inverse;
    }

    public double getX01Covariance_matrix_inverse() {
        return x01Covariance_matrix_inverse;
    }

    public void setX01Covariance_matrix_inverse(double x01Covariance_matrix_inverse) {
        this.x01Covariance_matrix_inverse = x01Covariance_matrix_inverse;
    }

    public double getX10Covariance_matrix_inverse() {
        return x10Covariance_matrix_inverse;
    }

    public void setX10Covariance_matrix_inverse(double x10Covariance_matrix_inverse) {
        this.x10Covariance_matrix_inverse = x10Covariance_matrix_inverse;
    }

    public double getX11Covariance_matrix_inverse() {
        return x11Covariance_matrix_inverse;
    }

    public void setX11Covariance_matrix_inverse(double x11Covariance_matrix_inverse) {
        this.x11Covariance_matrix_inverse = x11Covariance_matrix_inverse;
    }

    public String getWktPolygon() {
        return wktPolygon;
    }

    public void setWktPolygon(String wktPolygon) {
        this.wktPolygon = wktPolygon;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public ArrayList<String> getWeekly_density() {
        return weekly_density;
    }

    public void setWeekly_density(ArrayList<String> weekly_density) {
        this.weekly_density = weekly_density;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(uuid);
        parcel.writeStringList(idVisits);
        parcel.writeDouble(lngMean);
        parcel.writeDouble(latMean);
        parcel.writeDouble(age);
        parcel.writeDouble(accumulator);
        parcel.writeDouble(covariance_det);
        parcel.writeDouble(prior_probability);
        parcel.writeDouble(x00Covariance_matrix_inverse);
        parcel.writeDouble(x01Covariance_matrix_inverse);
        parcel.writeDouble(x10Covariance_matrix_inverse);
        parcel.writeDouble(x11Covariance_matrix_inverse);
        parcel.writeString(wktPolygon);
        parcel.writeLong(startTime);
        parcel.writeLong(endTime);
        parcel.writeLong(duration);
        parcel.writeString(period);
        parcel.writeStringList(weekly_density);
    }
}
