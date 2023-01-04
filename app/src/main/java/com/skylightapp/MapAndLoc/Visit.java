package com.skylightapp.MapAndLoc;

import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_REPORT_ID;
import static com.skylightapp.MapAndLoc.Region.REGION_ID;
import static com.skylightapp.MapAndLoc.Region.REGION_TABLE;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;

import com.skylightapp.Classes.Transaction;

import java.io.Serializable;

public class Visit implements Parcelable, Serializable, BaseColumns {
    public static final String VISIT_TABLE = "visit_Table";
    public static final String VISIT_ID = "visit_ID";
    public static final String VISIT_UUID = "visit_uuid";
    public static final String VISIT_DBID = "visit_DBID";
    public static final String VISIT_LAT = "visit_Lat";
    public static final String VISIT_LNG = "visit_Lng";
    public static final String VISIT_ACCURACY = "visit_accuracy";
    public static final String VISIT_START_TIME = "visit_Start_Time";
    public static final String VISIT_END_TIME = "visit_End_Time";
    public static final String VISIT_NB_POINT = "visit_nb_Point";
    public static final String VISIT_DURATION = "visit_Duration";
    public static final String VISIT_IS_UPLOAD = "visit_Is_Upload";
    public static final String VISIT_STATUS = "visit_Status";
    public static final String VISIT_REPORT_ID = "visit_Report_ID";
    public static final String VISIT_REGION_ID = "visit_Region_ID";
    public static final String VISIT_DATE_TIME = "visit_Date_Time";
    public static final String VISIT_STATE = "visit_State";

    public static final String CREATE_VISIT_TABLE = "CREATE TABLE IF NOT EXISTS " + VISIT_TABLE + " (" + VISIT_DBID + " INTEGER, "+ VISIT_ID + " INTEGER, " +
            VISIT_UUID + " TEXT , " + VISIT_LAT + " REAL , " + VISIT_LNG + " REAL, " + VISIT_START_TIME + " LONG, " +
            VISIT_END_TIME + " TEXT, " + VISIT_NB_POINT + " REAL, " + VISIT_DURATION + " TEXT, " + VISIT_IS_UPLOAD + " TEXT, " +
            VISIT_ACCURACY  + " FLOAT, "+ VISIT_STATUS + " TEXT, " + VISIT_DATE_TIME + " TEXT, " + VISIT_STATE + " TEXT, "+ VISIT_REPORT_ID + " INTEGER, "+ VISIT_REGION_ID + " INTEGER, "+ "PRIMARY KEY(" +VISIT_DBID + "), " +"FOREIGN KEY(" + VISIT_REPORT_ID  + ") REFERENCES " + EMERGENCY_REPORT_TABLE + "(" + EMERG_REPORT_ID + ")," +"FOREIGN KEY(" + VISIT_REGION_ID + ") REFERENCES " + REGION_TABLE + "(" + REGION_ID + "))";

    private int id;
    String uuid;
    public double lat;
    public double lng;
    float accuracy;
    public long startTime;
    protected long endTime;
    private int nbPoint;
    long duration;
    private String visitStatus;
    private int reportID;
    private int regionID;
    private int isUpload = 0; //0 -> not uploaded; 1 -> not finished but uploaded; 2 -> finished and uploaded
    private String dateTimeString;
    private String visitState;

    public Visit() {
        super();

    }

    protected Visit(Parcel in) {
        id = in.readInt();
        uuid = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        accuracy = in.readFloat();
        startTime = in.readLong();
        endTime = in.readLong();
        nbPoint = in.readInt();
        duration = in.readLong();
        isUpload = in.readInt();
    }

    public static final Creator<Visit> CREATOR = new Creator<Visit>() {
        @Override
        public Visit createFromParcel(Parcel in) {
            return new Visit(in);
        }

        @Override
        public Visit[] newArray(int size) {
            return new Visit[size];
        }
    };

    public Visit(int visitId, int reportID, int regionID, long duration, double lat, double lng, String uuid, String dateTime,long startTime,long endTime, int nbPoint, float accuracy, int isUpload, String state, String status) {
        this.id = visitId;
        this.reportID = reportID;
        this.regionID = regionID;
        this.duration = duration;
        this.lat = lat;
        this.lng = lng;
        this.uuid = uuid;
        this.dateTimeString = dateTime;
        this.nbPoint = nbPoint;
        this.accuracy = accuracy;
        this.isUpload = isUpload;
        this.visitState = state;
        this.startTime = startTime;
        this.endTime = endTime;
        this.visitStatus = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getNbPoint() {
        return nbPoint;
    }

    public void setNbPoint(int nbPoint) {
        this.nbPoint = nbPoint;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(int isUpload) {
        this.isUpload = isUpload;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(uuid);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeFloat(accuracy);
        parcel.writeLong(startTime);
        parcel.writeLong(endTime);
        parcel.writeInt(nbPoint);
        parcel.writeLong(duration);
        parcel.writeInt(isUpload);
    }

    public String getVisitStatus() {
        return visitStatus;
    }

    public void setVisitStatus(String visitStatus) {
        this.visitStatus = visitStatus;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

    public String getDateTimeString() {
        return dateTimeString;
    }

    public void setDateTimeString(String dateTimeString) {
        this.dateTimeString = dateTimeString;
    }

    public String getVisitState() {
        return visitState;
    }

    public void setVisitState(String visitState) {
        this.visitState = visitState;
    }
}
