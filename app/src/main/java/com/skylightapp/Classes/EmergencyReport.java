package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = EmergencyReport.EMERGENCY_REPORT_TABLE)
public class EmergencyReport implements Parcelable, Serializable {
    public static final String EMERGENCY_LOCID = "emerg_id";
    public static final String EMERGENCY_LOCTIME = "locTime";
    public static final String EMERGENCY_REPORT = "report";
    public static final String EMERGENCY_REPORT_ADDRESS = "address";
    public static final String EMERGENCY_REPORT_LAT = "emerg_lat";
    public static final String EMERGENCY_REPORT_LNG = "emerg_lng";
    public static final String EMERGENCY_REPORT_LATLNG = "emerg_LatLng";
    public static final String EMERGENCY_REPORT_TABLE = "loc_table";
    public static final String EMERGENCY_REPORT_TYPE = "lOC_report_type";
    public static final String EMERGENCY_REPORT_STATUS = "lOC_report_status";




    public static final String CREATE_EMERGENCY_REPORT_TABLE = "CREATE TABLE IF NOT EXISTS " + EMERGENCY_REPORT_TABLE + " (" + EMERGENCY_LOCID + " INTEGER , " + PROFILE_ID + " INTEGER , " +
            EMERGENCY_LOCTIME + " TEXT , " + EMERGENCY_REPORT_TYPE + " TEXT , " + EMERGENCY_REPORT_LAT + " TEXT , "+ EMERGENCY_REPORT_LNG + " TEXT , " + EMERGENCY_REPORT_STATUS + " TEXT , " + EMERGENCY_REPORT + " TEXT , " + EMERGENCY_REPORT_ADDRESS + " TEXT , " + EMERGENCY_REPORT_LATLNG + " TEXT , " + "PRIMARY KEY(" + EMERGENCY_LOCID + "), " +
            "FOREIGN KEY(" + PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";




    public EmergencyReport() {

    }
    int profileID;
    @PrimaryKey(autoGenerate = true)
    private int emergReportID=107;
    Profile userProfile;
    //private double latLng;
    private String reportTime;
    private String type;
    private String latLng;
    private String report;
    private String status;
    private double lat;
    private double lng;
    private String locationAddress;
    private ArrayList<EmergReportNext> emergReportNexts;



    public EmergencyReport(int emergReportID, int profileID, String type, String reportTime, double lat, double lng, String status) {
        this.profileID = profileID;
        this.emergReportID = emergReportID;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
        this.reportTime = reportTime;
        this.type = type;
    }

    protected EmergencyReport(Parcel in) {
        profileID = in.readInt();
        emergReportID = in.readInt();
        lat=in.readDouble();
        lng=in.readDouble();
        userProfile = in.readParcelable(Profile.class.getClassLoader());
        reportTime = in.readString();
        type = in.readString();
        status = in.readString();
    }



    public EmergencyReport(int emergReportID, int profileID, String selectedPurpose, String dateOfToday, String stringLatLng) {
        this.profileID = profileID;
        this.emergReportID = emergReportID;
        this.latLng = stringLatLng;
        this.lng = lng;
        this.reportTime = dateOfToday;
        this.type = selectedPurpose;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(profileID);
        dest.writeLong(emergReportID);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeParcelable(userProfile, flags);
        dest.writeString(reportTime);
        dest.writeString(type);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EmergencyReport> CREATOR = new Creator<EmergencyReport>() {
        @Override
        public EmergencyReport createFromParcel(Parcel in) {
            return new EmergencyReport(in);
        }

        @Override
        public EmergencyReport[] newArray(int size) {
            return new EmergencyReport[size];
        }
    };

    public String getType() {
        return type;
    }
    public void setType(String type) { this.type = type; }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) { this.status = status; }



    public int getProfileID() { return profileID; }
    public void setProfileID(int profileID) { this.profileID = profileID; }
    public int getEmergReportID() { return emergReportID; }
    public void setEmergReportID(int emergReportID) { this.emergReportID = emergReportID; }

    public Profile getUnit() {
        return userProfile;
    }
    public void setUserProfile(Profile userProfile) { this.userProfile = userProfile; }
    public String getReportTime() {
        return reportTime;
    }
    public void setReportTime(String reportTime) { this.reportTime = reportTime; }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }


    public ArrayList<EmergReportNext> getEmergReportNexts() {
        return emergReportNexts;
    }

    public void setEmergReportNexts(ArrayList<EmergReportNext> emergReportNexts) {
        this.emergReportNexts = emergReportNexts;
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
