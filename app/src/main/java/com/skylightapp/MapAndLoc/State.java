package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;

import com.blongho.country_data.Country;

import java.io.Serializable;

public class State implements Comparable<State> , Serializable, Parcelable {

    private int stateID;
    private Country country;
    private String stateName;

    public State(int stateID, Country country, String stateName) {
        this.stateID = stateID;
        this.country = country;
        this.stateName = stateName;
    }

    public State() {
        super();
    }

    protected State(Parcel in) {
        stateID = in.readInt();
        country = in.readParcelable(Country.class.getClassLoader());
        stateName = in.readString();
    }

    public static final Creator<State> CREATOR = new Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) {
            return new State[size];
        }
    };

    public int getStateID() {
        return stateID;
    }

    public Country getCountry() {
        return country;
    }

    public String getStateName() {
        return stateName;
    }

    @Override
    public String toString() {
        return stateName;
    }

    @Override
    public int compareTo(State another) {
        return this.getStateID() - another.getStateID();//ascending order
//            return another.getStateID()-this.getStateID();//descending order
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(stateID);
        parcel.writeParcelable(country, i);
        parcel.writeString(stateName);
    }
}
