package com.skylightapp.Awards;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Profile;

import java.io.Serializable;
import java.util.ArrayList;

import static com.skylightapp.Classes.Business.BIZ_ID;
import static com.skylightapp.Classes.Business.BIZ_TABLE;

public class Nomination implements Serializable, Parcelable {
    public static final String NOMINATION_TABLE = "NTable";
    public static final String NOMINATION_ID = "nom_id";
    public static final String NOMINATION_TITTLE = "NTittle";
    public static final String NOMINATION_DATE = "NDate";
    public static final String NOMINATED_NAME = "NName";
    public static final String NOMINATED_CATEGORY = "NCategory";
    public static final String NOMINATED_STATUS = "NStatus";
    public static final String NOMINATION_DESCRIPTION = "NDescription";
    public static final String NOMINATION_BANNER = "NBanner";

    public static final String CREATE_NOMINATION_TABLE = "CREATE TABLE " + NOMINATION_TABLE + " (" + NOMINATION_ID + " LONG PRIMARY KEY, " + BIZ_ID + " LONG , " +
            NOMINATION_TITTLE + " TEXT, " +  NOMINATED_CATEGORY + " TEXT, " +  NOMINATED_NAME + " TEXT, " + NOMINATION_DESCRIPTION + " TEXT, " +NOMINATION_DATE + " TEXT,"+  NOMINATED_STATUS + " TEXT, " +  NOMINATION_BANNER + " BLOB, " +
            "FOREIGN KEY(" + BIZ_ID + ") REFERENCES " + BIZ_TABLE + "(" + BIZ_ID + "))";



    private long nominationID;
    private String nominationTittle;
    private String nominationDescription;
    private String nominationCategory;
    private String nominationName;
    private Uri nominationBanner;
    private String nominationDate;
    private String nominationStatus;
    private Profile profile;
    private ArrayList<Votes> votes;
    private ArrayList<NominationCategory> nominationCategories;
    private ArrayList<Profile> profiles;

    public Nomination() {

    }

    public Nomination(long nominationID, String nominationTittle, String nominationDescription, String nominationCategory, String nominationName, String nominationDate, String nominationStatus) {
        this.nominationID = nominationID;
        this.nominationTittle = nominationTittle;
        this.nominationDescription = nominationDescription;
        this.nominationCategory = nominationCategory;
        this.nominationName = nominationName;
        this.nominationDate = nominationDate;
        this.nominationStatus = nominationStatus;

    }

    public Nomination(long nominationID, String nominationTittle, String nominationCategory, String nominationName, String nominationDescription, String nominationDate, String nominationStatus, Uri nominationLink) {
        this.nominationID = nominationID;
        this.nominationTittle = nominationTittle;
        this.nominationDescription = nominationDescription;
        this.nominationCategory = nominationCategory;
        this.nominationName = nominationName;
        this.nominationDate = nominationDate;
        this.nominationStatus = nominationStatus;
        this.nominationBanner = nominationLink;
    }

    public Nomination(long businessID) {

    }

    protected Nomination(Parcel in) {
        nominationID = in.readLong();
        nominationTittle = in.readString();
        nominationDescription = in.readString();
        nominationCategory = in.readString();
        nominationName = in.readString();
        nominationBanner = in.readParcelable(Uri.class.getClassLoader());
        nominationDate = in.readString();
        nominationStatus = in.readString();
        profile = in.readParcelable(Profile.class.getClassLoader());
        votes = in.createTypedArrayList(Votes.CREATOR);
        nominationCategories = in.createTypedArrayList(NominationCategory.CREATOR);
        //profiles = in.createTypedArrayList(Profile.CREATOR);
    }

    public static final Creator<Nomination> CREATOR = new Creator<Nomination>() {
        @Override
        public Nomination createFromParcel(Parcel in) {
            return new Nomination(in);
        }

        @Override
        public Nomination[] newArray(int size) {
            return new Nomination[size];
        }
    };

    public Profile getNominerProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public String getNominationStatus() { return nominationStatus; }
    public void setNominationStatus(String nominationStatus) {
        this.nominationStatus = nominationStatus;
    }
    public long getNominationID() { return nominationID; }
    public void setNominationID(long nominationID) { this.nominationID = nominationID; }

    public String getVotingTittle() { return nominationTittle; }
    public void setVotingTittle(String voteTittle) { this.nominationTittle = voteTittle; }

    public Uri getNominationBanner() { return nominationBanner; }
    public void setNominationBanner(Uri nominationBanner) { this.nominationBanner = nominationBanner; }



    public void setNominationDescription(String nominationDescription) { this.nominationDescription = nominationDescription; }
    public String getNominationDescription() {
        return nominationDescription;
    }
    public void setNominationCategory(String nominationCategory) { this.nominationCategory = nominationCategory; }
    public String getNominationCategory() {
        return nominationCategory;
    }
    public void setNominationName(String nominationName) { this.nominationName = nominationName; }
    public String getNominationName() {
        return nominationName;
    }

    public String getNominationDate() { return nominationDate; }
    public void setNominationDate(String nominationDate) { this.nominationDate = nominationDate; }

    public void addVote(long voteID,long nominationID, long profileID,String voteTittle, String voteCategory, String votee, Double voteAmount, String voteDate,String voteMeansOfID,String voteState,int voteCount,String status) {
        votes= new ArrayList<>();
        String voteNo = "Vote:" + (votes.size() + 1);
        Votes vote = new Votes(voteID,nominationID,profileID,voteTittle, voteCategory, votee,voteAmount,voteDate,voteMeansOfID,voteState,voteCount,status);
        votes.add(vote);
    }
    public void addProfile(int profileID, String firstName, String lastName,String phoneNumber, String email, String gender,String state,String username,String identity, String company) {
        votes= new ArrayList<>();
        String profileNo = "Profile:" + (profiles.size() + 1);
        Profile profile = new Profile(profileID,firstName,lastName,phoneNumber, email, gender,state,username,identity,company);
        profiles.add(profile);
    }
    public void addNominationCat(long ID, String nomCategory) {
        nominationCategories= new ArrayList<>();
        String noCatNo = "NoCat:" + (nominationCategories.size() + 1);
        NominationCategory nominationCategory = new NominationCategory(ID,nomCategory);
        nominationCategories.add(nominationCategory);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(nominationID);
        parcel.writeString(nominationTittle);
        parcel.writeString(nominationDescription);
        parcel.writeString(nominationCategory);
        parcel.writeString(nominationName);
        parcel.writeParcelable(nominationBanner, i);
        parcel.writeString(nominationDate);
        parcel.writeString(nominationStatus);
        parcel.writeParcelable(profile, i);
        parcel.writeTypedList(votes);
        parcel.writeTypedList(nominationCategories);
        parcel.writeTypedList(profiles);
    }
}
