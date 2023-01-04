package com.skylightapp.MapAndLoc;

import static com.skylightapp.MapAndLoc.POI.EMERG_POI_ID;
import static com.skylightapp.MapAndLoc.POI.EMER_POI_TABLE;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.webgeoservices.woosmapgeofencingcore.database.Visit;

import java.io.Serializable;
import java.util.ArrayList;

public class POIVisit implements Parcelable, Serializable {

    private  int visitID;
    private int reportID;
    private int poiID;
    private int duration;
    private long startTime;
    private String status;
    private long endTime;
    private double lng;
    private double lat;
    private Visit visit;
    private EmergencyReport emergencyReport;
    private ArrayList<EmergResponse> emergResponses;
    private EmergResponse emergResponse;
    private EmergReportNext reportNext;
    private ArrayList<Visit> VisitList;
    private String latLngStrng;
    private String state;

    public static final String POI_VISIT_TABLE = "pOI_Visit_table";
    public static final String POI_VISIT_ID = "pOI_Visit_id";
    public static final String POI_VISIT_POIID = "pOI_Visit_POI_id";
    public static final String POI_VISIT_REPORT_ID = "pOI_Visit_Report_id";
    public static final String POI_VISIT_DBID = "pOI_Visit_DBid";
    public static final String POI_VISIT_START_TIME = "pOI_Visit_Start_T";
    public static final String POI_VISIT_END_TIME = "pOI_Visit_End_T";
    public static final String POI_VISIT_LNG = "pOI_Visit_Lng";
    public static final String POI_VISIT_LAT = "pOI_Visit_Lat";
    public static final String POI_VISIT_DURATION = "pOI_Visit_Duration";
    public static final String POI_VISIT_STATUS = "pOI_Visit_Status";
    public static final String POI_VISIT_LATLNG = "pOI_Visit_LatLng";
    public static final String POI_VISIT_STATE = "pOI_Visit_State";

    public static final String CREATE_POI_VISIT_TABLE = "CREATE TABLE IF NOT EXISTS " + POI_VISIT_TABLE + " (" + POI_VISIT_DBID + " INTEGER , " + POI_VISIT_ID + " INTEGER , " +
            POI_VISIT_POIID + " INTEGER , " + POI_VISIT_REPORT_ID + " INTEGER , " + POI_VISIT_START_TIME + " LONG , "+ POI_VISIT_END_TIME + " LONG , " + POI_VISIT_LAT + " TEXT , " + POI_VISIT_LNG + " TEXT , "+ POI_VISIT_DURATION + " TEXT , "+ POI_VISIT_LATLNG + " TEXT , " + POI_VISIT_STATE + " TEXT , "+ POI_VISIT_STATUS + " TEXT , "+ "PRIMARY KEY(" + POI_VISIT_DBID + "), " +
            "FOREIGN KEY(" + POI_VISIT_POIID + ") REFERENCES " + EMER_POI_TABLE + "(" + EMERG_POI_ID + "))";
    private String point;


    public POIVisit() {
        super();

    }


    public POIVisit(Parcel in) {
        visitID = in.readInt();
        reportID = in.readInt();
        poiID = in.readInt();
        duration = in.readInt();
        startTime = in.readLong();
        status = in.readString();
        endTime = in.readLong();
        lng = in.readDouble();
        lat = in.readDouble();
        emergencyReport = in.readParcelable(EmergencyReport.class.getClassLoader());
        emergResponses = in.createTypedArrayList(EmergResponse.CREATOR);
        emergResponse = in.readParcelable(EmergResponse.class.getClassLoader());
        reportNext = in.readParcelable(EmergReportNext.class.getClassLoader());
    }

    public static final Creator<POIVisit> CREATOR = new Creator<POIVisit>() {
        @Override
        public POIVisit createFromParcel(Parcel in) {
            return new POIVisit(in);
        }

        @Override
        public POIVisit[] newArray(int size) {
            return new POIVisit[size];
        }
    };

    public POIVisit(int visitID, int poiID, int reportID, long startTime, long endTime, int duration, double poiVisitLat, double poiVisitLng, String poiVisitLatLng, String poiVisitState, String status) {
        this.visitID = visitID;
        this.poiID = poiID;
        this.reportID = reportID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.duration = duration;
        this.lat = poiVisitLat;
        this.lng = poiVisitLng;
        this.latLngStrng = poiVisitLatLng;
        this.state = poiVisitState;
    }

    public int getVisitID() {
        return visitID;
    }

    public void setVisitID(int visitID) {
        this.visitID = visitID;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getPoiID() {
        return poiID;
    }

    public void setPoiID(int poiID) {
        this.poiID = poiID;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public ArrayList<Visit> getVisitList() {
        return VisitList;
    }

    public void setVisitList(ArrayList<Visit> visitList) {
        VisitList = visitList;
    }

    public EmergencyReport getEmergencyReport() {
        return emergencyReport;
    }

    public void setEmergencyReport(EmergencyReport emergencyReport) {
        this.emergencyReport = emergencyReport;
    }

    public ArrayList<EmergResponse> getEmergResponses() {
        return emergResponses;
    }

    public void setEmergResponses(ArrayList<EmergResponse> emergResponses) {
        this.emergResponses = emergResponses;
    }

    public EmergResponse getEmergResponse() {
        return emergResponse;
    }

    public void setEmergResponse(EmergResponse emergResponse) {
        this.emergResponse = emergResponse;
    }

    public EmergReportNext getReportNext() {
        return reportNext;
    }

    public void setReportNext(EmergReportNext reportNext) {
        this.reportNext = reportNext;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(visitID);
        parcel.writeInt(reportID);
        parcel.writeInt(poiID);
        parcel.writeInt(duration);
        parcel.writeLong(startTime);
        parcel.writeString(status);
        parcel.writeLong(endTime);
        parcel.writeDouble(lng);
        parcel.writeDouble(lat);
        parcel.writeParcelable(emergencyReport, i);
        parcel.writeTypedList(emergResponses);
        parcel.writeParcelable(emergResponse, i);
        parcel.writeParcelable(reportNext, i);
    }

    public String getLatLngStrng() {
        return latLngStrng;
    }

    public void setLatLngStrng(String latLngStrng) {
        this.latLngStrng = latLngStrng;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
