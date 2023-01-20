package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;

import com.blongho.country_data.Country;
import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.StateDir.State;

import java.io.Serializable;
import java.util.ArrayList;

public class Town implements Comparable<Town> , Serializable, Parcelable {

    private int townID;
    private Country country;
    private State state;
    private LatLng townLatLng;
    private ArrayList<LatLng> townLatLngs;
    private String cityName;
    public Town() {
        super();
    }

    public Town(int townID, Country country, State state, String cityName) {
        this.townID = townID;
        this.country = country;
        this.state = state;
        this.cityName = cityName;
    }

    protected Town(Parcel in) {
        townID = in.readInt();
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

    public int getTownID() {
        return townID;
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
        return this.townID - another.getTownID();//ascending order
//            return another.getCityID() - this.cityID;//descending order
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(townID);
        parcel.writeParcelable(country, i);
        parcel.writeParcelable(state, i);
        parcel.writeString(cityName);
    }

    public LatLng getTownLatLng() {
        return townLatLng;
    }

    public void setTownLatLng(LatLng townLatLng) {
        this.townLatLng = townLatLng;
    }

    public ArrayList<LatLng> getTownLatLngs() {
        return townLatLngs;
    }

    public void setTownLatLngs(ArrayList<LatLng> townLatLngs) {
        this.townLatLngs = townLatLngs;
    }
}
