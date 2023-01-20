package com.skylightapp.Classes;

import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_ID;
import static com.skylightapp.Classes.OfficeBranch.OFFICE_BRANCH_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.skylightapp.MarketClasses.MarketBusiness;

import java.io.Serializable;
import java.util.ArrayList;

public class Worker implements Parcelable, Serializable {

    public static final String WORKER_TABLE = "workers_table";
    //@Ignore
    public static final String WORKER_NAME = "workerName";
    //@Ignore
    public static final String WORKER_ID = "worker_id";
    public static final String WORKER_DB_ID = "worker_DB_id";
    public static final String WORKER_BIZ_ID = "worker_Biz_id";
    public static final String WORKER_BIZ_BRANCH_ID = "worker_Branch_id";
    public static final String WORKER_PROFILE_ID = "worker_Prof_ID";
    public static final String WORKER_ROLE = "worker_Role";
    public static final String WORKER_SIGNED_UP_DATE = "worker_Sign_Up_Date";
    public static final String WORKER_OFFICE_NAME = "worker_Office_Name";
    public static final String WORKER_STATUS = "worker_Status";
    public static final String WORKER_BLOCK_STATUS = "worker_Block_Status";


    public static final String CREATE_WORKERS_TABLE = "CREATE TABLE IF NOT EXISTS " + WORKER_TABLE + " ( " + WORKER_ID + " INTEGER ,  " + WORKER_NAME + " TEXT  , "+ WORKER_DB_ID + " INTEGER, "  + " INTEGER, " + WORKER_BIZ_ID + " INTEGER,"+ WORKER_BIZ_BRANCH_ID + " INTEGER," + WORKER_PROFILE_ID + " INTEGER,"+ WORKER_SIGNED_UP_DATE + " TEXT,"+ WORKER_OFFICE_NAME + " TEXT,"+ WORKER_STATUS + " TEXT,"+ WORKER_BLOCK_STATUS + " TEXT," + WORKER_ROLE + " TEXT  , "+ "FOREIGN KEY(" + WORKER_BIZ_ID + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "),"+ "FOREIGN KEY(" + WORKER_BIZ_BRANCH_ID + ") REFERENCES " + OFFICE_BRANCH_TABLE + "(" + OFFICE_BRANCH_ID + "),"+  "FOREIGN KEY(" + WORKER_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"PRIMARY KEY(" + WORKER_DB_ID  + "))";

    private  int workerID;
    private int workerProfID;
    private int workerOfficeID;
    private Profile workerProfile;
    private String signUpDate;
    private String workerStatus;
    private String workerBlockStatus;
    private String workerOfficeName;
    private MarketBusiness workerEmployer;
    private ArrayList<MarketBusiness> workerBizs;
    private String workerRole;
    private long workerEmployerID;
    private String workerName;

    public Worker(Parcel in) {
        workerID = in.readInt();
        workerProfID = in.readInt();
        workerOfficeID = in.readInt();
        workerProfile = in.readParcelable(Profile.class.getClassLoader());
        signUpDate = in.readString();
        workerStatus = in.readString();
        workerBlockStatus = in.readString();
        workerOfficeName = in.readString();
        workerEmployer = in.readParcelable(MarketBusiness.class.getClassLoader());
        workerBizs = in.createTypedArrayList(MarketBusiness.CREATOR);
    }

    public static final Creator<Worker> CREATOR = new Creator<Worker>() {
        @Override
        public Worker createFromParcel(Parcel in) {
            return new Worker(in);
        }

        @Override
        public Worker[] newArray(int size) {
            return new Worker[size];
        }
    };
    public Worker() {
        super();

    }

    public Worker(int workerID, int profileID1, long bizID, String selectedOffice, String name, String selectedUserType, String timeLineTime) {
        this.workerID = workerID;
        this.workerProfID = profileID1;
        this.workerEmployerID = bizID;
        this.workerOfficeName = selectedOffice;
        this.workerName = name;
        this.workerRole = selectedUserType;
        this.signUpDate = timeLineTime;

    }

    public int getWorkerID() {
        return workerID;
    }

    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public int getWorkerProfID() {
        return workerProfID;
    }

    public void setWorkerProfID(int workerProfID) {
        this.workerProfID = workerProfID;
    }

    public Profile getWorkerProfile() {
        return workerProfile;
    }

    public void setWorkerProfile(Profile workerProfile) {
        this.workerProfile = workerProfile;
    }

    public ArrayList<MarketBusiness> getWorkerBizs() {
        return workerBizs;
    }

    public void setWorkerBizs(ArrayList<MarketBusiness> workerBizs) {
        this.workerBizs = workerBizs;
    }

    public String getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signUpDate = signUpDate;
    }

    public int getWorkerOfficeID() {
        return workerOfficeID;
    }

    public void setWorkerOfficeID(int workerOfficeID) {
        this.workerOfficeID = workerOfficeID;
    }

    public MarketBusiness getWorkerEmployer() {
        return workerEmployer;
    }

    public void setWorkerEmployer(MarketBusiness workerEmployer) {
        this.workerEmployer = workerEmployer;
    }

    public String getWorkerStatus() {
        return workerStatus;
    }

    public void setWorkerStatus(String workerStatus) {
        this.workerStatus = workerStatus;
    }

    public String getWorkerBlockStatus() {
        return workerBlockStatus;
    }

    public void setWorkerBlockStatus(String workerBlockStatus) {
        this.workerBlockStatus = workerBlockStatus;
    }

    public String getWorkerOfficeName() {
        return workerOfficeName;
    }

    public void setWorkerOfficeName(String workerOfficeName) {
        this.workerOfficeName = workerOfficeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(workerID);
        parcel.writeInt(workerProfID);
        parcel.writeInt(workerOfficeID);
        parcel.writeParcelable(workerProfile, i);
        parcel.writeString(signUpDate);
        parcel.writeString(workerStatus);
        parcel.writeString(workerBlockStatus);
        parcel.writeString(workerOfficeName);
        parcel.writeParcelable(workerEmployer, i);
        parcel.writeTypedList(workerBizs);
    }

    public String getWorkerRole() {
        return workerRole;
    }

    public void setWorkerRole(String workerRole) {
        this.workerRole = workerRole;
    }

    public long getWorkerEmployerID() {
        return workerEmployerID;
    }

    public void setWorkerEmployerID(long workerEmployerID) {
        this.workerEmployerID = workerEmployerID;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
}
