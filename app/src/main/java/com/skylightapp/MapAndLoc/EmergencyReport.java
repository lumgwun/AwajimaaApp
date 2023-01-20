package com.skylightapp.MapAndLoc;

import android.os.Parcel;
import android.os.Parcelable;



import java.io.Serializable;
import java.util.ArrayList;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.location.places.Place;
import com.skylightapp.Classes.Profile;


//@Entity(tableName = EmergencyReport.EMERGENCY_REPORT_TABLE)
public class EmergencyReport implements Parcelable, Serializable {
    public static final String EMERG_REPORT_ID = "emerg_id";
    public static final String EMERG_R_TIME = "Emeg_locTime";
    public static final String EMERG_REPORT = "emerg_report";
    public static final String EMERG_R_ADDRESS = "emerg_address";
    public static final String EMERG_R_LAT = "emerg_lat";
    public static final String EMERG_R_LNG = "emerg_lng";
    public static final String EMERG_R_LATLNG = "emerg_LatLng";
    public static final String EMERGENCY_REPORT_TABLE = "emerg_loc_table";
    public static final String EMERG_R_TYPE = "emerg_lOC_report_type";
    public static final String EMERG_R_PROF_ID = "emerg_lOC_report_Prof_id";
    public static final String EMERG_R_STATUS = "emerg_lOC_report_status";
    public static final String EMERG_R_SUBLOCALE = "emerg_lOC_SunLoc";
    public static final String EMERG_R_DB_ID = "emerg_lOC_DB_Id";
    public static final String EMERG_R_BG_ADDRESS = "emerg_lOC_BG_Address";
    public static final String EMERG_R_TOWN = "emerg_lOC_Town";
    public static final String EMERG_R_STATE = "emerg_lOC_STATE";
    public static final String EMERG_R_BIZ_ID = "emerg_lOC_Biz_ID";
    public static final String EMERG_R_MARKET_ID = "emerg_lOC_Market_ID";
    public static final String EMERG_R_COUNTRY = "emerg_lOC_Country";
    public static final String EMERG_R_GROUP = "emerg_lOC_report_Group";
    public static final String EMERG_R_QUESTION = "emerg_report_Question";
    public static final String EMERG_R_COMPANY = "emerg_report_Company";
    public static final String EMERG_R_LGA = "emerg_report_LGA";
    public static final String EMERG_R_MORE_INFO = "emerg_report_More";
    public static final String EMERG_R_PLACE_LATLNG = "emerg_Place_LatLng";


    public static final String CREATE_EMERGENCY_REPORT_TABLE = "CREATE TABLE IF NOT EXISTS " + EMERGENCY_REPORT_TABLE + " (" + EMERG_REPORT_ID + " INTEGER , " + EMERG_R_PROF_ID + " INTEGER , " +
            EMERG_R_TIME + " TEXT , " + EMERG_R_TYPE + " TEXT , " + EMERG_R_LAT + " REAL , "+ EMERG_R_LNG + " REAL , " + EMERG_R_STATUS + " TEXT , " + EMERG_REPORT + " TEXT , " + EMERG_R_ADDRESS + " TEXT , " + EMERG_R_LATLNG + " REAL , "+ EMERG_R_SUBLOCALE + " TEXT , " + EMERG_R_DB_ID + " INTEGER , "+ EMERG_R_BG_ADDRESS + " TEXT , "+ EMERG_R_TOWN + " TEXT , "+ EMERG_R_STATE + " TEXT , "+ EMERG_R_BIZ_ID + " INTEGER , "+ EMERG_R_MARKET_ID + " INTEGER , "+ EMERG_R_COUNTRY + " TEXT , "+ EMERG_R_GROUP + " TEXT , "+ EMERG_R_QUESTION + " TEXT , "+ EMERG_R_LGA + " TEXT , "+ EMERG_R_COMPANY + " TEXT , "+ EMERG_R_MORE_INFO + " TEXT , "+ EMERG_R_PLACE_LATLNG + " TEXT , "+ "PRIMARY KEY(" + EMERG_R_DB_ID + "), " +
            "FOREIGN KEY(" + EMERG_R_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";



    private Place emerGPlace;
    private boolean selected;
    private ArrayList<Region> emrRegions;
    private ArrayList<ZOI> emrZOIs;
    private ArrayList<RegionLog> emrRegionLog;
    private ArrayList<MovingPosition> emrMovingPosition;


    public EmergencyReport() {
        super();

    }
    int profileID;
    //@PrimaryKey(autoGenerate = true)
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
    private String emergRLGA;
    private String emergRCompanyResp;
    private String emergRMoreInfo;
    private String emergRGroup;
    private PlaceData emerGPlaceDate;
    private ArrayList<EmergencyReport> emergReportMoreReports;
    private ArrayList<ERGeofenceResponse> ERGeofenceResponses;
    private String emergRQuestion;
    private ArrayList<FenceEvent> emergReportFenceEs;
    private ArrayList<Fence> emergReportFences;
    private ArrayList<CircularFence> emrCircularFences;
    private String emrPlaceLatLng;
    private ArrayList<POI> emrPOIList;
    private POI emrPoi;
    private Visit emrVisit;
    private ArrayList<Visit> emrVisitList;
    private String emergRCategory;




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
    public EmergencyReport(int sReportID, int profileID1, long bizID, String reportDate, String oil_spillage_report, String localityString, String subUrb, String selectedLGA, String selectedOilCompany, String address, String strngLatLng, String moreInfo, String iamAvailable) {
        this.emergReportID = sReportID;
        this.profileID = profileID1;
        this.emergRBizID = bizID;
        this.emergRTime = reportDate;
        this.emergRMoreInfo = moreInfo;
        this.emergRType = oil_spillage_report;
        this.emergRLatLng = strngLatLng;
        this.emergRTown = localityString;
        this.emergR_SubLocality = subUrb;
        this.emergRLGA = selectedLGA;
        this.emergRLocAddress = address;
        this.emergRCompanyResp = selectedOilCompany;
    }

    public EmergencyReport(int reportID, int profileID, long bizID, String dateOfToday, String category, String selectedType, String stringLatLng, String locality, String bgAddress, String address, String country) {
        this.emergReportID = reportID;
        this.profileID = profileID;
        this.emergRBizID = bizID;
        this.emergRTime = dateOfToday;
        this.emergRCategory = category;
        this.emergRType = selectedType;
        this.emergRLatLng = stringLatLng;
        this.emergR_SubLocality = locality;
        this.emergR_BackgAddress = bgAddress;
        this.emergRLocAddress = address;
        this.emergRCountry = country;
    }



    public void addERGeofenceResponse(ERGeofenceResponse ERGeofenceResponse) {
        ERGeofenceResponses = new ArrayList<>();
        ERGeofenceResponses.add(ERGeofenceResponse);

    }
    public void addCircularFence(CircularFence circularFence) {
        emrCircularFences = new ArrayList<>();
        emrCircularFences.add(circularFence);

    }
    public void addEmergFenceEvents(FenceEvent fenceEvent) {
        emergReportFenceEs= new ArrayList<>();
        emergReportFenceEs.add(fenceEvent);

    }
    public void addEmergPOI(POI emrPoi) {
        emrPOIList= new ArrayList<>();
        emrPOIList.add(emrPoi);

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
    public void addFence(Fence fence) {
        emergReportFences= new ArrayList<>();
        emergReportFences.add(fence);



    }
    public void addEmergVisit(Visit emrVisit) {
        emrVisitList= new ArrayList<>();
        emrVisitList.add(emrVisit);



    }


    public void addEmergReportRegions(Region region) {
        emrRegions= new ArrayList<>();
        emrRegions.add(region);



    }

    public void addZOI(ZOI zoi) {
        emrZOIs= new ArrayList<>();
        emrZOIs.add(zoi);



    }
    public void addMoreEmergReports(EmergencyReport emergencyReport) {
        emergReportMoreReports= new ArrayList<>();
        emergReportMoreReports.add(emergencyReport);



    }
    public void addEmergRegionLog(RegionLog regionLog) {
        emrRegionLog= new ArrayList<>();
        emrRegionLog.add(regionLog);



    }
    public void addMovingPosition(MovingPosition movingPosition) {
        emrMovingPosition= new ArrayList<>();
        emrMovingPosition.add(movingPosition);



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

    public String getEmergRLGA() {
        return emergRLGA;
    }

    public void setEmergRLGA(String emergRLGA) {
        this.emergRLGA = emergRLGA;
    }

    public String getEmergRCompanyResp() {
        return emergRCompanyResp;
    }

    public void setEmergRCompanyResp(String emergRCompanyResp) {
        this.emergRCompanyResp = emergRCompanyResp;
    }

    public String getEmergRMoreInfo() {
        return emergRMoreInfo;
    }

    public void setEmergRMoreInfo(String emergRMoreInfo) {
        this.emergRMoreInfo = emergRMoreInfo;
    }

    public ArrayList<Fence> getEmergReportFences() {
        return emergReportFences;
    }

    public void setEmergReportFences(ArrayList<Fence> emergReportFences) {
        this.emergReportFences = emergReportFences;
    }

    public String getEmrPlaceLatLng() {
        return emrPlaceLatLng;
    }

    public void setEmrPlaceLatLng(String emrPlaceLatLng) {
        this.emrPlaceLatLng = emrPlaceLatLng;
    }

    public ArrayList<POI> getEmrPOIList() {
        return emrPOIList;
    }

    public void setEmrPOIList(ArrayList<POI> emrPOIList) {
        this.emrPOIList = emrPOIList;
    }

    public POI getEmrPoi() {
        return emrPoi;
    }

    public void setEmrPoi(POI emrPoi) {
        this.emrPoi = emrPoi;
    }

    public Visit getEmrVisit() {
        return emrVisit;
    }

    public void setEmrVisit(Visit emrVisit) {
        this.emrVisit = emrVisit;
    }

    public ArrayList<Visit> getEmrVisitList() {
        return emrVisitList;
    }

    public void setEmrVisitList(ArrayList<Visit> emrVisitList) {
        this.emrVisitList = emrVisitList;
    }

    public ArrayList<Region> getEmrRegions() {
        return emrRegions;
    }

    public void setEmrRegions(ArrayList<Region> emrRegions) {
        this.emrRegions = emrRegions;
    }

    public ArrayList<com.skylightapp.MapAndLoc.ZOI> getEmrZOIs() {
        return emrZOIs;
    }

    public void setEmrZOIs(ArrayList<ZOI> emrZOIs) {
        this.emrZOIs = emrZOIs;
    }

    public ArrayList<com.skylightapp.MapAndLoc.RegionLog> getEmrRegionLog() {
        return emrRegionLog;
    }

    public void setEmrRegionLog(ArrayList<RegionLog> emrRegionLog) {
        this.emrRegionLog = emrRegionLog;
    }

    public ArrayList<com.skylightapp.MapAndLoc.MovingPosition> getEmrMovingPosition() {
        return emrMovingPosition;
    }

    public void setEmrMovingPosition(ArrayList<MovingPosition> emrMovingPosition) {
        this.emrMovingPosition = emrMovingPosition;
    }

    public String getEmergRCategory() {
        return emergRCategory;
    }

    public void setEmergRCategory(String emergRCategory) {
        this.emergRCategory = emergRCategory;
    }
}
