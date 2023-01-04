package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Bill implements Parcelable, Serializable {
    private String distance;
    private String time;
    private String basePrice;
    private String distanceCost;
    private String timeCost;
    private String total;
    private String isPaid;
    private String unit;
    private String promoBouns;
    private String referralBouns;

    protected Bill(Parcel in) {
        distance = in.readString();
        time = in.readString();
        basePrice = in.readString();
        distanceCost = in.readString();
        timeCost = in.readString();
        total = in.readString();
        isPaid = in.readString();
        unit = in.readString();
        promoBouns = in.readString();
        referralBouns = in.readString();
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public Bill() {
        super();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getDistanceCost() {
        return distanceCost;
    }

    public void setDistanceCost(String distanceCost) {
        this.distanceCost = distanceCost;
    }

    public String getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(String timeCost) {
        this.timeCost = timeCost;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getPromoBouns() {
        return promoBouns;
    }

    public void setPromoBouns(String promoBouns) {
        this.promoBouns = promoBouns;
    }

    public String getReferralBouns() {
        return referralBouns;
    }

    public void setReferralBouns(String referralBouns) {
        this.referralBouns = referralBouns;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(distance);
        parcel.writeString(time);
        parcel.writeString(basePrice);
        parcel.writeString(distanceCost);
        parcel.writeString(timeCost);
        parcel.writeString(total);
        parcel.writeString(isPaid);
        parcel.writeString(unit);
        parcel.writeString(promoBouns);
        parcel.writeString(referralBouns);
    }
}
