package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.location.places.Place;
import com.skylightapp.Classes.Profile;

@Entity(tableName = EmergencyReport.EMERGENCY_REPORT_TABLE)
public class EmergencyReport implements Parcelable, Serializable {
    public static final String EMERGENCY_LOCID = "emerg_id";
    public static final String EMERGENCY_LOCTIME = "Emeg_locTime";
    public static final String EMERGENCY_REPORT = "emerg_report";
    public static final String EMERGENCY_REPORT_ADDRESS = "emerg_address";
    public static final String EMERGENCY_REPORT_LAT = "emerg_lat";
    public static final String EMERGENCY_REPORT_LNG = "emerg_lng";
    public static final String EMERGENCY_REPORT_LATLNG = "emerg_LatLng";
    public static final String EMERGENCY_REPORT_TABLE = "emerg_loc_table";
    public static final String EMERGENCY_REPORT_TYPE = "emerg_lOC_report_type";
    public static final String EMERGENCY_REPORT_PROF_ID = "emerg_lOC_report_Prof_id";
    public static final String EMERGENCY_REPORT_STATUS = "emerg_lOC_report_status";
    public static final String EMERGENCY_REPORT_SUBLOCALE = "emerg_lOC_SunLoc";
    public static final String EMERGENCY_REPORT_DB_ID = "emerg_lOC_DB_Id";
    public static final String EMERGENCY_REPORT_BG_ADDRESS = "emerg_lOC_BG_Address";
    public static final String EMERGENCY_REPORT_TOWN = "emerg_lOC_Town";
    public static final String EMERGENCY_REPORT_STATE = "emerg_lOC_STATE";
    public static final String EMERGENCY_REPORT_BIZ_ID = "emerg_lOC_Biz_ID";
    public static final String EMERGENCY_REPORT_MARKET_ID = "emerg_lOC_Market_ID";
    public static final String EMERGENCY_REPORT_COUNTRY = "emerg_lOC_Country";
    public static final String EMERGENCY_REPORT_GROUP = "emerg_lOC_report_Group";
    public static final String EMERGENCY_REPORT_QUESTION = "emerg_report_Question";


    public static final String CREATE_EMERGENCY_REPORT_TABLE = "CREATE TABLE IF NOT EXISTS " + EMERGENCY_REPORT_TABLE + " (" + EMERGENCY_LOCID + " INTEGER , " + EMERGENCY_REPORT_PROF_ID + " INTEGER , " +
            EMERGENCY_LOCTIME + " TEXT , " + EMERGENCY_REPORT_TYPE + " TEXT , " + EMERGENCY_REPORT_LAT + " REAL , "+ EMERGENCY_REPORT_LNG + " REAL , " + EMERGENCY_REPORT_STATUS + " TEXT , " + EMERGENCY_REPORT + " TEXT , " + EMERGENCY_REPORT_ADDRESS + " TEXT , " + EMERGENCY_REPORT_LATLNG + " REAL , "+ EMERGENCY_REPORT_SUBLOCALE + " TEXT , " + EMERGENCY_REPORT_DB_ID + " INTEGER , "+ EMERGENCY_REPORT_BG_ADDRESS + " TEXT , "+ EMERGENCY_REPORT_TOWN + " TEXT , "+ EMERGENCY_REPORT_STATE + " TEXT , "+ EMERGENCY_REPORT_BIZ_ID + " INTEGER , "+ EMERGENCY_REPORT_MARKET_ID + " INTEGER , "+ EMERGENCY_REPORT_COUNTRY + " TEXT , "+ EMERGENCY_REPORT_GROUP + " TEXT , "+ EMERGENCY_REPORT_QUESTION + " TEXT , "+ "PRIMARY KEY(" + EMERGENCY_REPORT_DB_ID + "), " +
            "FOREIGN KEY(" + EMERGENCY_REPORT_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";
    private Place emerGPlace;
    private boolean selected;


    public EmergencyReport() {
        super();

    }
    int profileID;
    @PrimaryKey(autoGenerate = true)
    private int emergReportDBID;
    Profile userProfile;
    private int emergReportID;
    //private double latLng;
    private String emergRTime;
    private String emergRType;
    private String emergRLatLng;
    private String emergReport;
    private String emergRStatus;
    private double emergRLat;
    private double emergRLng;
    private String emergRLocAddress;
    private String emergR_SubLocality;
    private String emergR_BackgAddress;
    private ArrayList<EmergReportNext> emergReportNexts;
    private ArrayList<SlideModel> emergRPictures;
    private ArrayList<EmergResponse> emergResponses;
    private long emergRBizID;
    private int emergRMarketID;
    private String emergRState;
    private String emergRCountry;
    private String emergRTown;
    private String emergRGroup;
    private PlaceData emerGPlaceDate;
    private ArrayList<EmergencyReport> emergReportMoreReports;
    private ArrayList<ERGeofenceResponse> ERGeofenceResponses;
    private String emergRQuestion;
    private ArrayList<FenceEvent> emergReportFenceEs;


    public EmergencyReport(int reportID, int profileID, long bizID, String dateOfToday, String selectedType, String stringLatLng, String locality, String bgAddress, String address, String country) {
        this.emergReportID = reportID;
        this.profileID = profileID;
        this.emergRBizID = bizID;
        this.emergRTime = dateOfToday;
        this.emergRType = selectedType;
        this.emergRLatLng = stringLatLng;
        this.emergR_SubLocality = locality;
        this.emergR_BackgAddress = bgAddress;
        this.emergRLocAddress = address;
        this.emergRCountry = country;

    }

    public EmergencyReport(int reportId, int profileID, long bizID, String reportTime, String type, double lat, double lng, String locality, String bgAddress, String address, String country, String strLatLng, String status) {
        this.emergReportID = reportId;
        this.profileID = profileID;
        this.emergRBizID = bizID;
        this.emergRTime = reportTime;
        this.emergRType = type;
        this.emergRLatLng = strLatLng;
        this.emergR_SubLocality = locality;
        this.emergR_BackgAddress = bgAddress;
        this.emergRLocAddress = address;
        this.emergRStatus = status;
        this.emergRLat = lat;
        this.emergRLng = lng;
        this.emergRCountry = country;


    }
    public void addEmergLocFence(ERGeofenceResponse ERGeofenceResponse) {
        ERGeofenceResponses = new ArrayList<>();
        ERGeofenceResponses.add(ERGeofenceResponse);

    }
    public void addEmergFence(FenceEvent fenceEvent) {
        emergReportFenceEs= new ArrayList<>();
        emergReportFenceEs.add(fenceEvent);

    }

    public void removeEmergLocFence(ArrayList<ERGeofenceResponse> ERGeofenceResponses, ERGeofenceResponse ERGeofenceResponse) {
        ERGeofenceResponses.remove(ERGeofenceResponse);

    }

    public EmergencyReport(int reportId, double lat, double lng) {
        this.emergRLat = lat;
        this.emergRLng = lng;
        this.emergReportID = reportId;
    }

    public void addEmergReportNext(EmergReportNext emergReportNext) {
        emergReportNexts= new ArrayList<>();
        emergReportNexts.add(emergReportNext);

    }



    public void addReportPicture(SlideModel picture) {
        emergRPictures= new ArrayList<>();
        emergRPictures.add(picture);



    }
    public void addMoreEmergReports(EmergencyReport emergencyReport) {
        emergReportMoreReports= new ArrayList<>();
        emergReportMoreReports.add(emergencyReport);



    }



    public EmergencyReport(int emergReportID, int profileID, String emergRType, String emergRTime, double emergRLat, double emergRLng, String emergRStatus) {
        this.profileID = profileID;
        this.emergReportID = emergReportID;
        this.emergRLat = emergRLat;
        this.emergRLng = emergRLng;
        this.emergRStatus = emergRStatus;
        this.emergRTime = emergRTime;
        this.emergRType = emergRType;
    }
    public EmergencyReport(int emergReportID, int profileID, String emergRType, String emergRTime, double emergRLat, double emergRLng,String strgLatLng, String locality,String selectedAddress) {
        this.profileID = profileID;
        this.emergReportID = emergReportID;
        this.emergRLat = emergRLat;
        this.emergRLng = emergRLng;
        this.emergRStatus = emergRStatus;
        this.emergRTime = emergRTime;
        this.emergRType = emergRType;
    }

    protected EmergencyReport(Parcel in) {
        profileID = in.readInt();
        emergReportID = in.readInt();
        emergRLat =in.readDouble();
        emergRLng =in.readDouble();
        userProfile = in.readParcelable(Profile.class.getClassLoader());
        emergRTime = in.readString();
        emergRType = in.readString();
        emergRStatus = in.readString();
    }



    public EmergencyReport(int emergReportID, int profileID, String selectedPurpose, String dateOfToday, String stringLatLng) {
        this.profileID = profileID;
        this.emergReportID = emergReportID;
        this.emergRLatLng = stringLatLng;
        this.emergRLng = emergRLng;
        this.emergRTime = dateOfToday;
        this.emergRType = selectedPurpose;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(profileID);
        dest.writeLong(emergReportID);
        dest.writeDouble(emergRLat);
        dest.writeDouble(emergRLng);
        dest.writeParcelable(userProfile, flags);
        dest.writeString(emergRTime);
        dest.writeString(emergRType);
        dest.writeString(emergRStatus);
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

    public String getEmergRType() {
        return emergRType;
    }
    public void setEmergRType(String emergRType) { this.emergRType = emergRType; }
    public String getEmergRStatus() {
        return emergRStatus;
    }
    public void setEmergRStatus(String emergRStatus) { this.emergRStatus = emergRStatus; }



    public int getProfileID() { return profileID; }
    public void setProfileID(int profileID) { this.profileID = profileID; }
    public int getEmergReportID() { return emergReportID; }
    public void setEmergReportID(int emergReportID) { this.emergReportID = emergReportID; }

    public Profile getUnit() {
        return userProfile;
    }
    public void setUserProfile(Profile userProfile) { this.userProfile = userProfile; }
    public String getEmergRTime() {
        return emergRTime;
    }
    public void setEmergRTime(String emergRTime) { this.emergRTime = emergRTime; }

    public String getEmergReport() {
        return emergReport;
    }

    public void setEmergReport(String emergReport) {
        this.emergReport = emergReport;
    }

    public String getEmergRLocAddress() {
        return emergRLocAddress;
    }

    public void setEmergRLocAddress(String emergRLocAddress) {
        this.emergRLocAddress = emergRLocAddress;
    }


    public ArrayList<EmergReportNext> getEmergReportNexts() {
        return emergReportNexts;
    }

    public void setEmergReportNexts(ArrayList<EmergReportNext> emergReportNexts) {
        this.emergReportNexts = emergReportNexts;
    }

    public double getEmergRLat() {
        return emergRLat;
    }

    public void setEmergRLat(double emergRLat) {
        this.emergRLat = emergRLat;
    }

    public double getEmergRLng() {
        return emergRLng;
    }

    public void setEmergRLng(double emergRLng) {
        this.emergRLng = emergRLng;
    }

    public String getEmergRLatLng() {
        return emergRLatLng;
    }

    public void setEmergRLatLng(String emergRLatLng) {
        this.emergRLatLng = emergRLatLng;
    }

    public String getEmergR_SubLocality() {
        return emergR_SubLocality;
    }

    public void setEmergR_SubLocality(String emergR_SubLocality) {
        this.emergR_SubLocality = emergR_SubLocality;
    }

    public String getEmergR_BackgAddress() {
        return emergR_BackgAddress;
    }

    public void setEmergR_BackgAddress(String emergR_BackgAddress) {
        this.emergR_BackgAddress = emergR_BackgAddress;
    }

    public ArrayList<SlideModel> getEmergRPictures() {
        return emergRPictures;
    }

    public void setEmergRPictures(ArrayList<SlideModel> emergRPictures) {
        this.emergRPictures = emergRPictures;
    }

    public long getEmergRBizID() {
        return emergRBizID;
    }

    public void setEmergRBizID(long emergRBizID) {
        this.emergRBizID = emergRBizID;
    }

    public int getEmergRMarketID() {
        return emergRMarketID;
    }

    public void setEmergRMarketID(int emergRMarketID) {
        this.emergRMarketID = emergRMarketID;
    }

    public String getEmergRState() {
        return emergRState;
    }

    public void setEmergRState(String emergRState) {
        this.emergRState = emergRState;
    }

    public String getEmergRCountry() {
        return emergRCountry;
    }

    public void setEmergRCountry(String emergRCountry) {
        this.emergRCountry = emergRCountry;
    }

    public String getEmergRTown() {
        return emergRTown;
    }

    public void setEmergRTown(String emergRTown) {
        this.emergRTown = emergRTown;
    }

    public PlaceData getEmerGPlaceData() {
        return emerGPlaceDate;
    }

    public void setEmerGPlaceDate(PlaceData emerGPlaceDate) {
        this.emerGPlaceDate = emerGPlaceDate;
    }

    public void setEmerGPlace(Place emerGPlace) {
        this.emerGPlace = emerGPlace;
    }

    public Place getEmerGPlace() {
        return emerGPlace;
    }

    public String getEmergRGroup() {
        return emergRGroup;
    }

    public void setEmergRGroup(String emergRGroup) {
        this.emergRGroup = emergRGroup;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ArrayList<EmergResponse> getEmergResponses() {
        return emergResponses;
    }

    public void setEmergResponses(ArrayList<EmergResponse> emergResponses) {
        this.emergResponses = emergResponses;
    }

    public ArrayList<EmergencyReport> getEmergReportMoreReports() {
        return emergReportMoreReports;
    }

    public void setEmergReportMoreReports(ArrayList<EmergencyReport> emergReportMoreReports) {
        this.emergReportMoreReports = emergReportMoreReports;
    }


    public ArrayList<ERGeofenceResponse> getaLocGeoFences() {
        return ERGeofenceResponses;
    }

    public void setaLocGeoFences(ArrayList<ERGeofenceResponse> ERGeofenceResponses) {
        this.ERGeofenceResponses = ERGeofenceResponses;
    }

    public int getEmergReportDBID() {
        return emergReportDBID;
    }

    public void setEmergReportDBID(int emergReportDBID) {
        this.emergReportDBID = emergReportDBID;
    }

    public String getEmergRQuestion() {
        return emergRQuestion;
    }

    public void setEmergRQuestion(String emergRQuestion) {
        this.emergRQuestion = emergRQuestion;
    }

    public ArrayList<FenceEvent> getEmergReportFenceEs() {
        return emergReportFenceEs;
    }

    public void setEmergReportFenceEs(ArrayList<FenceEvent> emergReportFenceEs) {
        this.emergReportFenceEs = emergReportFenceEs;
    }
}
