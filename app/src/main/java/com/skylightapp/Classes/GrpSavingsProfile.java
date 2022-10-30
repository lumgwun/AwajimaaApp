package com.skylightapp.Classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GrpSavingsProfile implements Parcelable, Serializable {
    private int gsPID;
    private int gsPOwnerID;
    private int gsPGrpAcctID;
    private String gsPFirstName;
    private String gsPSurName;
    private String gsPDateJoined;
    private String gsPCountry;
    private String gsPStatus;
    private Uri gsPPhoto;
    private List<Integer> grpAcctIDs;

    public GrpSavingsProfile(int newGrpSavingsProfID, int userID, String firstName, String lastName, String joinedDate) {
        super();
    }

    protected GrpSavingsProfile(Parcel in) {
        gsPID = in.readInt();
        gsPOwnerID = in.readInt();
        gsPGrpAcctID = in.readInt();
        gsPFirstName = in.readString();
        gsPSurName = in.readString();
        gsPDateJoined = in.readString();
        gsPCountry = in.readString();
        gsPStatus = in.readString();
        gsPPhoto = in.readParcelable(Uri.class.getClassLoader());
    }

    public GrpSavingsProfile(int newGrpSavingsProfID, int userID, String firstName, String lastName, String joinedDate,String gsPStatus) {
        this.gsPID = newGrpSavingsProfID;
        this.gsPOwnerID = userID;
        this.gsPFirstName = firstName;
        this.gsPSurName = lastName;
        this.gsPDateJoined = joinedDate;
        this.gsPStatus = gsPStatus;
    }

    public GrpSavingsProfile() {
        super();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(gsPID);
        dest.writeInt(gsPOwnerID);
        dest.writeInt(gsPGrpAcctID);
        dest.writeString(gsPFirstName);
        dest.writeString(gsPSurName);
        dest.writeString(gsPDateJoined);
        dest.writeString(gsPCountry);
        dest.writeString(gsPStatus);
        dest.writeParcelable(gsPPhoto, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GrpSavingsProfile> CREATOR = new Creator<GrpSavingsProfile>() {
        @Override
        public GrpSavingsProfile createFromParcel(Parcel in) {
            return new GrpSavingsProfile(in);
        }

        @Override
        public GrpSavingsProfile[] newArray(int size) {
            return new GrpSavingsProfile[size];
        }
    };

    public int getGsPID() {
        return gsPID;
    }

    public void setGsPID(int gsPID) {
        this.gsPID = gsPID;
    }

    public int getGsPOwnerID() {
        return gsPOwnerID;
    }

    public void setGsPOwnerID(int gsPOwnerID) {
        this.gsPOwnerID = gsPOwnerID;
    }

    public int getGsPGrpAcctID() {
        return gsPGrpAcctID;
    }

    public void setGsPGrpAcctID(int gsPGrpAcctID) {
        this.gsPGrpAcctID = gsPGrpAcctID;
    }

    public String getGsPFirstName() {
        return gsPFirstName;
    }

    public void setGsPFirstName(String gsPFirstName) {
        this.gsPFirstName = gsPFirstName;
    }

    public String getGsPSurName() {
        return gsPSurName;
    }

    public void setGsPSurName(String gsPSurName) {
        this.gsPSurName = gsPSurName;
    }

    public String getGsPDateJoined() {
        return gsPDateJoined;
    }

    public void setGsPDateJoined(String gsPDateJoined) {
        this.gsPDateJoined = gsPDateJoined;
    }

    public String getGsPCountry() {
        return gsPCountry;
    }

    public void setGsPCountry(String gsPCountry) {
        this.gsPCountry = gsPCountry;
    }

    public Uri getGsPPhoto() {
        return gsPPhoto;
    }

    public void setGsPPhoto(Uri gsPPhoto) {
        this.gsPPhoto = gsPPhoto;
    }

    public String getGsPStatus() {
        return gsPStatus;
    }

    public void setGsPStatus(String gsPStatus) {
        this.gsPStatus = gsPStatus;
    }

    public List<Integer> getGrpAcctIDs() {
        return grpAcctIDs;
    }

    public void setGrpAcctIDs(List<Integer> grpAcctIDs) {
        this.grpAcctIDs = grpAcctIDs;
    }
    public void addGrpAcctID(Integer grpAcctID) {
        this.grpAcctIDs = new ArrayList<>();
        grpAcctIDs.add(grpAcctID);
    }

}
