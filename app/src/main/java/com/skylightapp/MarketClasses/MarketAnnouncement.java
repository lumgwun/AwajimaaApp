package com.skylightapp.MarketClasses;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.denzcoskun.imageslider.models.SlideModel;

import java.io.Serializable;
import java.util.ArrayList;

public class MarketAnnouncement implements Serializable, Parcelable {
    private int mAID;
    private int mAProfID;
    private int mAMarketID;
    private String mATittle;
    private String mAMessage;
    private String mADate;
    private String mAAnouncer;
    private String mAStatus;
    private ArrayList<Uri> mAImages;
    private ArrayList<String> mAStates;
    private ArrayList<Integer> mABizIDs;
    private ArrayList<Integer> mACustomerIDs;
    private ArrayList<Integer> mAProfIDs;
    private ArrayList<Integer> mAMarketIDs;
    private ArrayList<String> mACountries;
    private ArrayList<String> mATowns;
    private ArrayList<String> mABizByNames;
    private ArrayList<String> mABizTypes;
    private boolean mASelected;
    private ArrayList<SlideModel> slideModels;

    public MarketAnnouncement () {
        super();

    }
    public void addPhotos(Uri mAPhotos) {
        mAImages = new ArrayList<>();
        mAImages.add(mAPhotos);
    }
    public void addStates(String mAState) {
        mAStates = new ArrayList<>();
        mAStates.add(mAState);
    }
    public void addBizID(Integer mABizID) {
        mABizIDs = new ArrayList<>();
        mABizIDs.add(mABizID);
    }
    public void addCusID(Integer mACusID) {
        mACustomerIDs = new ArrayList<>();
        mACustomerIDs.add(mACusID);
    }

    public void addBizName(String mABizName) {
        mABizByNames = new ArrayList<>();
        mABizByNames.add(mABizName);
    }
    public void addTown(String mATown) {
        mATowns = new ArrayList<>();
        mATowns.add(mATown);
    }
    public void addCountry(String mACountry) {
        mACountries = new ArrayList<>();
        mACountries.add(mACountry);
    }
    public void addMarketID(Integer mAMarketID) {
        mAMarketIDs = new ArrayList<>();
        mAMarketIDs.add(mAMarketID);
    }
    public void addProfID(Integer mAProfID) {
        mAProfIDs = new ArrayList<>();
        mAProfIDs.add(mAProfID);
    }
    public void addBizType(String mABizType) {
        mABizTypes = new ArrayList<>();
        mABizTypes.add(mABizType);
    }

    protected MarketAnnouncement(Parcel in) {
        mAID = in.readInt();
        mAProfID = in.readInt();
        mAMarketID = in.readInt();
        mAMessage = in.readString();
        mADate = in.readString();
        mAAnouncer = in.readString();
        mAStatus = in.readString();
    }

    public static final Creator<MarketAnnouncement> CREATOR = new Creator<MarketAnnouncement>() {
        @Override
        public MarketAnnouncement createFromParcel(Parcel in) {
            return new MarketAnnouncement(in);
        }

        @Override
        public MarketAnnouncement[] newArray(int size) {
            return new MarketAnnouncement[size];
        }
    };

    public int getmAID() {
        return mAID;
    }

    public void setmAID(int mAID) {
        this.mAID = mAID;
    }

    public int getmAProfID() {
        return mAProfID;
    }

    public void setmAProfID(int mAProfID) {
        this.mAProfID = mAProfID;
    }

    public int getmAMarketID() {
        return mAMarketID;
    }

    public void setmAMarketID(int mAMarketID) {
        this.mAMarketID = mAMarketID;
    }

    public String getmAMessage() {
        return mAMessage;
    }

    public void setmAMessage(String mAMessage) {
        this.mAMessage = mAMessage;
    }

    public String getmADate() {
        return mADate;
    }

    public void setmADate(String mADate) {
        this.mADate = mADate;
    }

    public String getmAAnouncer() {
        return mAAnouncer;
    }

    public void setmAAnouncer(String mAAnouncer) {
        this.mAAnouncer = mAAnouncer;
    }

    public String getmAStatus() {
        return mAStatus;
    }

    public void setmAStatus(String mAStatus) {
        this.mAStatus = mAStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mAID);
        parcel.writeInt(mAProfID);
        parcel.writeInt(mAMarketID);
        parcel.writeString(mAMessage);
        parcel.writeString(mADate);
        parcel.writeString(mAAnouncer);
        parcel.writeString(mAStatus);
    }

    public ArrayList<Uri> getmAImages() {
        return mAImages;
    }

    public void setmAImages(ArrayList<Uri> mAImages) {
        this.mAImages = mAImages;
    }

    public String getmATittle() {
        return mATittle;
    }

    public void setmATittle(String mATittle) {
        this.mATittle = mATittle;
    }

    public ArrayList<String> getmAStates() {
        return mAStates;
    }

    public void setmAStates(ArrayList<String> mAStates) {
        this.mAStates = mAStates;
    }

    public ArrayList<Integer> getmABizIDs() {
        return mABizIDs;
    }

    public void setmABizIDs(ArrayList<Integer> mABizIDs) {
        this.mABizIDs = mABizIDs;
    }

    public ArrayList<Integer> getmACustomerIDs() {
        return mACustomerIDs;
    }

    public void setmACustomerIDs(ArrayList<Integer> mACustomerIDs) {
        this.mACustomerIDs = mACustomerIDs;
    }

    public ArrayList<Integer> getmAProfIDs() {
        return mAProfIDs;
    }

    public void setmAProfIDs(ArrayList<Integer> mAProfIDs) {
        this.mAProfIDs = mAProfIDs;
    }

    public ArrayList<Integer> getmAMarketIDs() {
        return mAMarketIDs;
    }

    public void setmAMarketIDs(ArrayList<Integer> mAMarketIDs) {
        this.mAMarketIDs = mAMarketIDs;
    }

    public ArrayList<String> getmACountries() {
        return mACountries;
    }

    public void setmACountries(ArrayList<String> mACountries) {
        this.mACountries = mACountries;
    }

    public ArrayList<String> getmATowns() {
        return mATowns;
    }

    public void setmATowns(ArrayList<String> mATowns) {
        this.mATowns = mATowns;
    }

    public ArrayList<String> getmABizByNames() {
        return mABizByNames;
    }

    public void setmABizByNames(ArrayList<String> mABizByNames) {
        this.mABizByNames = mABizByNames;
    }

    public boolean ismASelected() {
        return mASelected;
    }

    public void setmASelected(boolean mASelected) {
        this.mASelected = mASelected;
    }

    public ArrayList<String> getmABizTypes() {
        return mABizTypes;
    }

    public void setmABizTypes(ArrayList<String> mABizTypes) {
        this.mABizTypes = mABizTypes;
    }

    public ArrayList<SlideModel> getSlideModels() {
        return slideModels;
    }

    public void setSlideModels(ArrayList<SlideModel> slideModels) {
        this.slideModels = slideModels;
    }
}
