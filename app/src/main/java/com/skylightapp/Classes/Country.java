package com.skylightapp.Classes;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Locale;

public class Country implements Serializable, Parcelable {
    private final String mCode;

    private final String mName;

    private final int mDialCode;
    private String mCurrency;

    public Country(String code, String name, int dialCode) {
        mCode = code;
        mName = name;
        mDialCode = dialCode;
    }
    public Country(String code, String name, int dialCode,String mCurrency) {
        mCode = code;
        mName = name;
        mDialCode = dialCode;
        mCurrency = mCurrency;
    }

    protected Country(Parcel in) {
        mCode = in.readString();
        mName = in.readString();
        mDialCode = in.readInt();
        mCurrency = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public String getCode() {
        return mCode;
    }

    public String getName() {
        return mName;
    }
    public void setmCurrency(String mCurrency) {
        this.mCurrency=mCurrency;
    }


    public String getmCurrency() {
        return mCurrency;
    }

    public int getDialCode() {
        return mDialCode;
    }

    public String getDisplayName() {

        return new Locale("", mCode).getDisplayCountry(Locale.US);
    }

    public int getResId(Context context) {
        String name = String.format("country_flag_%s",mCode.toLowerCase());
        final Resources resources = context.getResources();
        return resources.getIdentifier(name, "drawable", context.getPackageName());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mCode);
        parcel.writeString(mName);
        parcel.writeInt(mDialCode);
        parcel.writeString(mCurrency);
    }
}
