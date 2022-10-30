package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_LOCID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;

@Entity(tableName = EmergReportNext.EMERGENCY_NEXT_REPORT_TABLE)
public class EmergReportNext implements Parcelable, Serializable {
    public static final String EMERGENCY_NEXT_LOCID = "emerg_Next_id";
    public static final String EMERGENCY_NEXT_LOCTIME = "emerg_next_locTime";
    public static final String EMERGENCY_NEXT_LAT = "emerg_next_lat";
    public static final String EMERGENCY_NEXT_LNG = "emerg_next_lng";
    public static final String EMERGENCY_NEXT_LATLNG = "emerg_next_LatLng";
    public static final String EMERGENCY_NEXT_REPORT_ID = "emerg_next_RID";
    public static final String EMERGENCY_NEXT_REPORT_TABLE = "emeg_next_table";

    public static final String CREATE_EMERGENCY_NEXT_REPORT_TABLE = "CREATE TABLE IF NOT EXISTS " + EMERGENCY_NEXT_REPORT_TABLE + " (" + EMERGENCY_NEXT_LOCID + " INTERGER , " + EMERGENCY_NEXT_REPORT_ID + " INTEGER , " +
            EMERGENCY_NEXT_LOCTIME + " TEXT , " + EMERGENCY_NEXT_LAT + " REAL , " + EMERGENCY_NEXT_LNG + " REAL , " + EMERGENCY_NEXT_LATLNG + " REAL , "  + "PRIMARY KEY(" + EMERGENCY_NEXT_LOCID + "), " +
            "FOREIGN KEY(" + EMERGENCY_NEXT_REPORT_ID + ") REFERENCES " + EMERGENCY_REPORT_TABLE + "(" + EMERGENCY_LOCID + "))";


    @PrimaryKey(autoGenerate = true)
    private int reportNextID=12;
    private String reportNextTime;
    private String latLng;
    private double lat;
    private double lng;
    private int reportID;
    private  EmergencyReport emergencyReport;


    public EmergReportNext(Parcel in) {
        reportNextID = in.readInt();
        reportNextTime = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        reportID = in.readInt();
        emergencyReport = in.readParcelable(EmergencyReport.class.getClassLoader());

    }

    public static final Creator<EmergReportNext> CREATOR = new Creator<EmergReportNext>() {
        @Override
        public EmergReportNext createFromParcel(Parcel in) {
            return new EmergReportNext(in);
        }

        @Override
        public EmergReportNext[] newArray(int size) {
            return new EmergReportNext[size];
        }
    };

    public EmergReportNext(int emergNextID, int emergReportID, String time, String latLng) {
        this.reportNextID = emergNextID;
        this.reportID = emergReportID;
        this.reportNextTime = time;
        this.latLng = latLng;
    }

    public EmergReportNext() {

    }


    public long getReportNextID() {
        return reportNextID;
    }

    public void setReportNextID(int reportNextID) {
        this.reportNextID = reportNextID;
    }

    public String getReportNextTime() {
        return reportNextTime;
    }

    public void setReportNextTime(String reportNextTime) {
        this.reportNextTime = reportNextTime;
    }

    public long getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public EmergencyReport getEmergencyReport() {
        return emergencyReport;
    }

    public void setEmergencyReport(EmergencyReport emergencyReport) {
        this.emergencyReport = emergencyReport;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(reportNextID);
        parcel.writeString(reportNextTime);
        parcel.writeLong(reportID);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeParcelable(emergencyReport, i);

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

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }
}
