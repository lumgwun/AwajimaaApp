package com.skylightapp.Awards;

import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.Classes.Profile;

import java.io.Serializable;

import static com.skylightapp.Awards.Award.AWARD_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.Profile.PROFILE_STATE;

public class Votes extends  Nomination implements Serializable, Parcelable {
    public static final String VOTE_TABLE = "VoteTable";
    public static final String VOTE_ID = "votes_id";
    public static final String VOTING_TITTLE = "VTittle";
    public static final String VOTE_DATE = "voteDate";
    public static final String VOTEE_ = "Votee";
    public static final String VOTE_AMOUNT = "VoteAmount";
    public static final String VOTE_CATEGORY = "VCategory";
    public static final String VOTE_STATUS = "VStatus";
    public static final String VOTER_IDENTITY = "VoterIdentity";
    public static final String VOTER_COUNT = "VoterCount";

    public static final String CREATE_VOTE_TABLE = "CREATE TABLE " + VOTE_TABLE + " (" + VOTE_ID + " LONG PRIMARY KEY, " + AWARD_ID + " LONG , " + NOMINATION_ID + " LONG , " + PROFILE_ID + " LONG , " + MARKET_BIZ_ID + " LONG , " +
            VOTING_TITTLE + " TEXT, " + VOTE_CATEGORY + " TEXT, " +  VOTEE_ + " TEXT, " + PROFILE_PHONE + " TEXT, " + PROFILE_STATE + " TEXT, " + VOTER_IDENTITY + " TEXT, " + VOTE_AMOUNT + " DOUBLE, " +
            VOTE_DATE + " TEXT, " + VOTER_COUNT + " TEXT, " +  VOTE_STATUS +  " TEXT,"+ "FOREIGN KEY(" + PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";
    public static final int SUCCESS = 1;
    public static final int FAILED = 2;

    private long voteID;

    private String voteTittle;
    private String voteDate;
    private String voteCategory;
    private String votee;
    private Double voteAmount;
    private String voteStatus;
    private String voteMeansOfID;
    private int voteCount;
    private String voteState;
    private String phoneNumber;
    private long profileID;
    private long nominationID;
    private Profile profile;
    public Votes() {

    }

    public Votes(long voteID, String voteTittle, String voteCategory, String votee, Double voteAmount, String voteDate, String voteMeansOfID, String voteState, int voteCount, String status) {
        this.voteID = voteID;
        this.voteTittle = voteTittle;
        this.voteCategory = voteCategory;
        this.votee = votee;
        this.voteAmount = voteAmount;
        this.voteDate = voteDate;
        this.voteMeansOfID = voteMeansOfID;
        this.voteState = voteState;
        this.voteCount = voteCount;
        this.voteStatus = status;

    }
    public Votes(long voteID, long nominationID,long profileID, String voteTittle, String voteCategory, String votee, Double voteAmount, String voteDate, String voteMeansOfID, String voteState, int voteCount, String status) {
        this.voteID = voteID;
        this.nominationID = nominationID;
        this.profileID = profileID;
        this.voteTittle = voteTittle;
        this.voteCategory = voteCategory;
        this.votee = votee;
        this.voteAmount = voteAmount;
        this.voteDate = voteDate;
        this.voteMeansOfID = voteMeansOfID;
        this.voteState = voteState;
        this.voteCount = voteCount;
        this.voteStatus = status;

    }
    public Votes(long voteID,String voteCategory, String votee, Double voteAmount, String voteDate, String voteMeansOfID, int voteCount, String status) {
        this.voteID = voteID;
        this.nominationID = nominationID;
        this.profileID = profileID;
        this.voteTittle = voteTittle;
        this.voteCategory = voteCategory;
        this.votee = votee;
        this.voteAmount = voteAmount;
        this.voteDate = voteDate;
        this.voteMeansOfID = voteMeansOfID;
        this.voteState = voteState;
        this.voteCount = voteCount;
        this.voteStatus = status;

    }


    public Votes(long voteID, long profileID, String voteTittle, String voteCategory, String votee, Double voteAmount, String voteDate, String voteMeansOfID, String voteState, int voteCount, String status) {
        this.voteID = voteID;
        this.profileID = profileID;
        this.voteTittle = voteTittle;
        this.voteCategory = voteCategory;
        this.votee = votee;
        this.voteAmount = voteAmount;
        this.voteDate = voteDate;
        this.voteMeansOfID = voteMeansOfID;
        this.voteState = voteState;
        this.voteCount = voteCount;
        this.voteStatus = status;

    }

    public Votes(long id, String nominationTittle, String nominationDescription, String nominationCategory, String nominationName, String nominationDate, String nominationStatus) {
        super(id, nominationTittle, nominationDescription, nominationCategory, nominationName, nominationDate, nominationStatus);
    }


    protected Votes(Parcel in) {
        voteID = in.readLong();
        voteTittle = in.readString();
        voteDate = in.readString();
        voteCategory = in.readString();
        votee = in.readString();
        if (in.readByte() == 0) {
            voteAmount = null;
        } else {
            voteAmount = in.readDouble();
        }
        voteStatus = in.readString();
        voteMeansOfID = in.readString();
        voteCount = in.readInt();
        voteState = in.readString();
        phoneNumber = in.readString();
        profileID = in.readLong();
        nominationID = in.readLong();
        profile = in.readParcelable(Profile.class.getClassLoader());
    }

    public static final Creator<Votes> CREATOR = new Creator<Votes>() {
        @Override
        public Votes createFromParcel(Parcel in) {
            return new Votes(in);
        }

        @Override
        public Votes[] newArray(int size) {
            return new Votes[size];
        }
    };

    public Profile getNominerProfile() { return profile; }
    public void setProfile(Profile voteStatus) { this.profile = profile; }

    public String getVoteStatus() { return voteStatus; }
    public void setVoteStatus(String voteStatus) { this.voteStatus = voteStatus; }

    public void setVoteMeansOfID(String voteMeansOfID) { this.voteMeansOfID = voteMeansOfID; }
    public String getVoteMeansOfID() {
        return voteMeansOfID;
    }
    public void setVoterCount(int voteCount) { this.voteCount = voteCount; }
    public int getVoterCount() {
        return voteCount;
    }
    public void setVoteState(String voteState) { this.voteState = voteState; }
    public String getVoteState() {
        return voteState;
    }

    public String getVotingTittle() { return voteTittle; }
    public void setVotingTittle(String voteTittle) { this.voteTittle = voteTittle; }

    public void setVoteDate(String nominationDescription) { this.voteDate = voteDate; }
    public String getVoteDate() {
        return voteDate;
    }
    public void setVoteCategory(String voteCategory) { this.voteCategory = voteCategory; }
    public String getVoteCategory() {
        return voteCategory;
    }
    public void setVotee(String votee) { this.votee = votee; }
    public String getVotee() {
        return votee;
    }

    public void setVoteAmount(double voteAmount) { this.voteAmount = voteAmount; }
    public Double getVoteAmount() { return voteAmount; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(voteID);
        parcel.writeString(voteTittle);
        parcel.writeString(voteDate);
        parcel.writeString(voteCategory);
        parcel.writeString(votee);
        if (voteAmount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAmount);
        }
        parcel.writeString(voteStatus);
        parcel.writeString(voteMeansOfID);
        parcel.writeInt(voteCount);
        parcel.writeString(voteState);
        parcel.writeString(phoneNumber);
        parcel.writeLong(profileID);
        parcel.writeLong(nominationID);
        parcel.writeParcelable(profile, i);
    }

    public enum SUCCESS {}

    public enum FAILED {}
}
