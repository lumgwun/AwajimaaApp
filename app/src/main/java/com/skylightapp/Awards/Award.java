package com.skylightapp.Awards;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Classes.Profile;

import java.io.Serializable;
import java.util.ArrayList;

import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class Award implements Serializable, Parcelable {
    public static final String AWARD_TABLE = "ATable";
    public static final String AWARD_ID = "award_id";
    public static final String AWARD_TITTLE = "AwardTittle";
    public static final String AWARD_DESCRIPTION = "AwardDescription";
    public static final String AWARD_DATE = "AwardDate";
    public static final String AWARD_START_DATE = "AwardStartDate";
    public static final String AWARD_STATUS = "AwardStatus";
    public static final String AWARD_END_DATE = "AwardEndDate";
    public static final String AWARD_LOGO = "Award_Logo";
    public static final String AWARD_COUNTRY = "Award_Country";

    public static final String CREATE_AWARD_TABLE = "CREATE TABLE " + AWARD_TABLE + " (" + AWARD_ID + " LONG PRIMARY KEY, " + MARKET_BIZ_ID + " LONG , " + PROFILE_ID + " LONG , " +
            AWARD_TITTLE + " TEXT, " +  AWARD_DESCRIPTION + " TEXT, " +  AWARD_DATE + " TEXT, " + AWARD_START_DATE + " TEXT, " +AWARD_END_DATE + " TEXT,"+  AWARD_STATUS + " TEXT, " +  AWARD_LOGO + " BLOB, " + AWARD_COUNTRY + " TEXT,"+"FOREIGN KEY(" + PROFILE_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +
            "FOREIGN KEY(" + MARKET_BIZ_ID + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "))";



    private long awardID;
    private String awardTittle;
    private String awardDesc;
    private String awardStartDate;
    private String awardEndDate;
    private Uri awardLogo;
    private String awardDate;
    private String awardCountry;
    private String awardStatus;
    private boolean allowOnePersonMultipleVote=false;
    private boolean allowNomineeSelfVote=false;
    private Profile profile;
    private MarketBusiness marketBusiness;
    private ArrayList<Votes> votes;
    private ArrayList<NominationCategory> nominationCategories;
    private ArrayList<Profile> profiles;

    public Award() {

    }

    public Award(long awardID, String awardTittle, String description, String awardDate, String awardStartDate, String awardEndDate, String awardStatus, Uri awardLogo) {
        this.awardID=awardID;
        this.awardTittle=awardTittle;
        this.awardDesc=description;
        this.awardDate=awardDate;
        this.awardStartDate=awardStartDate;
        this.awardEndDate=awardEndDate;
        this.awardStatus=awardStatus;
        this.awardLogo=awardLogo;
    }

    public Award(long profileID,long awardID, String awardTittle, String description, String awardDate, String awardStartDate, String awardEndDate, String awardStatus, Uri awardLogo) {
        this.awardID=awardID;
        this.awardTittle=awardTittle;
        this.awardDesc=description;
        this.awardDate=awardDate;
        this.awardStartDate=awardStartDate;
        this.awardEndDate=awardEndDate;
        this.awardStatus=awardStatus;
        this.awardLogo=awardLogo;
    }


    protected Award(Parcel in) {
        awardID = in.readLong();
        awardTittle = in.readString();
        awardDesc = in.readString();
        awardStartDate = in.readString();
        awardEndDate = in.readString();
        awardLogo = in.readParcelable(Uri.class.getClassLoader());
        awardDate = in.readString();
        awardStatus = in.readString();
        allowOnePersonMultipleVote = in.readByte() != 0;
        allowNomineeSelfVote = in.readByte() != 0;
        profile = in.readParcelable(Profile.class.getClassLoader());
        marketBusiness = in.readParcelable(MarketBusiness.class.getClassLoader());
        votes = in.createTypedArrayList(Votes.CREATOR);
        nominationCategories = in.createTypedArrayList(NominationCategory.CREATOR);
        //profiles = in.createTypedArrayList(Profile.CREATOR);
    }

    public static final Creator<Award> CREATOR = new Creator<Award>() {
        @Override
        public Award createFromParcel(Parcel in) {
            return new Award(in);
        }

        @Override
        public Award[] newArray(int size) {
            return new Award[size];
        }
    };

    public MarketBusiness getBusiness() { return marketBusiness; }
    public void setBusiness(MarketBusiness marketBusiness) { this.marketBusiness = marketBusiness; }

    public Profile getAwardGiverProfile() { return profile; }
    public void setAwardGiverProfile(Profile profile) { this.profile = profile; }


    public String getAwardCountry() { return awardCountry; }
    public void setAwardCountry(String awardCountry) {
        this.awardCountry = awardCountry;
    }

    public String getAwardStatus() { return awardStatus; }
    public void setAwardStatus(String awardStatus) {
        this.awardStatus = awardStatus;
    }

    public long getAwardID() { return awardID; }
    public void setAwardID(long awardID) { this.awardID = awardID; }

    public String getAwardTittle() { return awardTittle; }
    public void setAwardTittle(String voteTittle) { this.awardTittle = voteTittle; }
    public Uri getAwardLogo() { return awardLogo; }
    public void setAwardLogo(Uri awardLogo) { this.awardLogo = awardLogo; }



    public void setAwardDesc(String awardDesc) { this.awardDesc = awardDesc; }
    public String getAwardDesc() {
        return awardDesc;
    }
    public void setAwardStartDate(String awardStartDate) { this.awardStartDate = awardStartDate; }
    public String getAwardStartDate() {
        return awardStartDate;
    }
    public void setAwardEndDate(String awardEndDate) { this.awardEndDate = awardEndDate; }
    public String getAwardEndDate() {
        return awardEndDate;
    }

    public String getAwardDate() { return awardDate; }
    public void setAwardDate(String awardDate) { this.awardDate = awardDate; }

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
        parcel.writeLong(awardID);
        parcel.writeString(awardTittle);
        parcel.writeString(awardDesc);
        parcel.writeString(awardStartDate);
        parcel.writeString(awardEndDate);
        parcel.writeParcelable(awardLogo, i);
        parcel.writeString(awardDate);
        parcel.writeString(awardStatus);
        parcel.writeByte((byte) (allowOnePersonMultipleVote ? 1 : 0));
        parcel.writeByte((byte) (allowNomineeSelfVote ? 1 : 0));
        parcel.writeParcelable(profile, i);
        parcel.writeParcelable(marketBusiness, i);
        parcel.writeTypedList(votes);
        parcel.writeTypedList(nominationCategories);
        parcel.writeTypedList(profiles);
    }
}
