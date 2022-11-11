package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;

import com.blongho.country_data.Country;

import java.io.Serializable;

public class Town implements Comparable<Town> , Serializable, Parcelable {

    private int cityID;
    private Country country;
    private State state;
    private String cityName;
    public Town() {
        super();
    }

    public Town(int cityID, Country country, State state, String cityName) {
        this.cityID = cityID;
        this.country = country;
        this.state = state;
        this.cityName = cityName;
    }

    protected Town(Parcel in) {
        cityID = in.readInt();
        country = in.readParcelable(Country.class.getClassLoader());
        state = in.readParcelable(State.class.getClassLoader());
        cityName = in.readString();
    }

    public static final Creator<Town> CREATOR = new Creator<Town>() {
        @Override
        public Town createFromParcel(Parcel in) {
            return new Town(in);
        }

        @Override
        public Town[] newArray(int size) {
            return new Town[size];
        }
    };

    public int getCityID() {
        return cityID;
    }

    public Country getCountry() {
        return country;
    }

    public State getState() {
        return state;
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public String toString() {
        return cityName;
    }

    @Override
    public int compareTo(Town another) {
        return this.cityID - another.getCityID();//ascending order
//            return another.getCityID() - this.cityID;//descending order
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(cityID);
        parcel.writeParcelable(country, i);
        parcel.writeParcelable(state, i);
        parcel.writeString(cityName);
    }
}
