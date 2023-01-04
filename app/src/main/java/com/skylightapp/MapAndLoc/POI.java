package com.skylightapp.MapAndLoc;

import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_REPORT_ID;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.webgeoservices.woosmapgeofencingcore.database.Visit;

import java.io.Serializable;
import java.util.ArrayList;

public class POI  implements Parcelable, Serializable {
    private Visit poiVisit;
    private  int poiID;
    private int reportID;
    private int distance;
    private String status;
    private String latLngStrng;
    private double lng;
    private double lat;
    private ArrayList<Visit> pOIVisitList;
    private String poiState;

    private int locationId;

    private String name;
    private String idStore;
    private String city;
    private String zipCode;
    private String travelingDistance;
    private String duration;
    private long dateTime;
    private String data;
    private int radius;
    private String contact;
    private String tags;
    private String types;
    private String countryCode;
    private String address;
    private boolean openNow;




    public static final String EMER_POI_TABLE = "emerg_POI_table";
    public static final String EMERG_POI_ID = "emerg_POI_id";
    public static final String EMERG_POI_DBID = "emerg_POI_DBid";
    public static final String EMERG_POI_LAT = "emerg_POI_Lat";
    public static final String EMERG_POI_REPORT_ID = "emerg_POI_Report_id";
    public static final String EMERG_POI_LNG = "emerg_POI_Lng";
    public static final String EMERG_POI_DISTANCE = "emerg_POI_Distance";
    public static final String EMERG_POI_STATUS = "emerg_POI_Status";
    public static final String EMERG_POI_LATLNG = "emerg_POI_LatLng";
    public static final String EMERG_POI_STATE = "emerg_POI_State";

    public static final String CREATE_POI_REPORT_TABLE = "CREATE TABLE IF NOT EXISTS " + EMER_POI_TABLE + " (" + EMERG_POI_DBID + " INTEGER , " + EMERG_POI_REPORT_ID + " INTEGER , " +
            EMERG_POI_ID + " INTEGER , " + EMERG_POI_LAT + " TEXT , " + EMERG_POI_LNG + " TEXT , "+ EMERG_POI_DISTANCE + " INTEGER , " + EMERG_POI_STATUS + " TEXT , " + EMERG_POI_LATLNG + " TEXT , "+ EMERG_POI_STATE + " TEXT , "+ "PRIMARY KEY(" + EMERG_POI_DBID + "), " +
            "FOREIGN KEY(" + EMERG_POI_REPORT_ID + ") REFERENCES " + EMERGENCY_REPORT_TABLE + "(" + EMERG_REPORT_ID + "))";


    public POI() {
        super();

    }

    public POI(int poiID, int reportID, double poiLat, double poiLng, String poiLatLng, String status, int distance, String status1,String poiState) {

        this.reportID = reportID;
        this.poiID = poiID;
        this.lat = poiLat;
        this.lng = poiLng;
        this.latLngStrng = poiLatLng;
        this.status = status;
        this.distance = distance;
        this.status = status1;
        this.poiState = poiState;

    }


    protected POI(Parcel in) {
        poiID = in.readInt();
        reportID = in.readInt();
        distance = in.readInt();
        status = in.readString();
        latLngStrng = in.readString();
        lng = in.readDouble();
        lat = in.readDouble();
        poiState = in.readString();
    }

    public static final Creator<POI> CREATOR = new Creator<POI>() {
        @Override
        public POI createFromParcel(Parcel in) {
            return new POI(in);
        }

        @Override
        public POI[] newArray(int size) {
            return new POI[size];
        }
    };

    public ArrayList<Visit> getpOIVisitList() {
        return pOIVisitList;
    }

    public void setpOIVisitList(ArrayList<Visit> pOIVisitList) {
        this.pOIVisitList = pOIVisitList;
    }

    public Visit getPoiVisit() {
        return poiVisit;
    }

    public void setPoiVisit(Visit poiVisit) {
        this.poiVisit = poiVisit;
    }

    public int getPoiID() {
        return poiID;
    }

    public void setPoiID(int poiID) {
        this.poiID = poiID;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLatLngStrng() {
        return latLngStrng;
    }

    public void setLatLngStrng(String latLngStrng) {
        this.latLngStrng = latLngStrng;
    }

    public String getPoiState() {
        return poiState;
    }

    public void setPoiState(String poiState) {
        this.poiState = poiState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(poiID);
        parcel.writeInt(reportID);
        parcel.writeInt(distance);
        parcel.writeString(status);
        parcel.writeString(latLngStrng);
        parcel.writeDouble(lng);
        parcel.writeDouble(lat);
        parcel.writeString(poiState);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTravelingDistance() {
        return travelingDistance;
    }

    public void setTravelingDistance(String travelingDistance) {
        this.travelingDistance = travelingDistance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
