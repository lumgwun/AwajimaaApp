package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Referral implements Parcelable, Serializable {
    private String referralCode;
    private String totalReferrals;
    private double amountEarned;
    private double amountSpent;
    private double balanceAmount;

    protected Referral(Parcel in) {
        referralCode = in.readString();
        totalReferrals = in.readString();
        amountEarned = in.readDouble();
        amountSpent = in.readDouble();
        balanceAmount = in.readDouble();
    }

    public Referral() {
        super();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(referralCode);
        dest.writeString(totalReferrals);
        dest.writeDouble(amountEarned);
        dest.writeDouble(amountSpent);
        dest.writeDouble(balanceAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Referral> CREATOR = new Creator<Referral>() {
        @Override
        public Referral createFromParcel(Parcel in) {
            return new Referral(in);
        }

        @Override
        public Referral[] newArray(int size) {
            return new Referral[size];
        }
    };

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getTotalReferrals() {
        return totalReferrals;
    }

    public void setTotalReferrals(String totalReferrals) {
        this.totalReferrals = totalReferrals;
    }

    public double getAmountEarned() {
        return amountEarned;
    }

    public void setAmountEarned(double amountEarned) {
        this.amountEarned = amountEarned;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(double amountSpent) {
        this.amountSpent = amountSpent;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
