package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.Trip.A_TRIP_ID;
import static com.skylightapp.Bookings.Trip.A_TRIP_TABLE;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class TripStopPoint implements Parcelable, Serializable {

    public static final String TRIP_STOP_POINT_TABLE = "trip_stop_point_Table";
    public static final String TRIP_STOP_POINT_ID = "trip_stop_point_id";
    public static final String TRIP_STOP_POINT_DBID = "trip_stop_point_DB_ID";
    public static final String TSP_TRIP_ID = "trip_stop_point_Trip_id";
    public static final String TSP_AMOUNT = "trip_stop_point_Amount";
    public static final String TSP_NAME = "trip_stop_point_Name";
    public static final String TSP_LATLNG = "trip_stop_point_LatLng";
    public static final String TSP_STATUS = "trip_stop_point_Status";
    public static final String TSP_BIZ_ID = "trip_stop_point_Biz_id";
    public static final String TSP_STATE = "trip_stop_point_State";
    public static final String TSP_LAT = "trip_stop_point_Lat";
    public static final String TSP_LNG = "trip_stop_point_Lng";

    public static final String CREATE_TRIP_STOP_POINT_TABLE = "CREATE TABLE " + TRIP_STOP_POINT_TABLE + " (" + TRIP_STOP_POINT_DBID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TRIP_STOP_POINT_ID + " INTEGER, " + TSP_TRIP_ID + " INTEGER , " +
            TSP_NAME + " TEXT ,"+ TSP_LATLNG + " TEXT, "+ TSP_AMOUNT + " REAL, "+ TSP_STATUS + " TEXT, "+ TSP_BIZ_ID + " LONG, "+ TSP_STATE + " TEXT, "+ TSP_LAT + " TEXT, "+ TSP_LNG + " TEXT, "+"FOREIGN KEY(" + TSP_TRIP_ID + ") REFERENCES " + A_TRIP_TABLE + "(" + A_TRIP_ID + "))";


    private  int bTSPId;
    private  int bTSPTripID;
    private String bTSPName;
    private double bTSPAmount;
    private String bTSPStatus;
    private LatLng btSPLatLng;
    private String btSPLatLngStrng;
    private String bTSPState;
    private double bTSPLat;
    private double bTSPLng;

    public TripStopPoint() {
        super();
    }

    protected TripStopPoint(Parcel in) {
        bTSPId = in.readInt();
        bTSPName = in.readString();
        bTSPAmount = in.readDouble();
        bTSPStatus = in.readString();
        btSPLatLng = in.readParcelable(LatLng.class.getClassLoader());
    }

    public static final Creator<TripStopPoint> CREATOR = new Creator<TripStopPoint>() {
        @Override
        public TripStopPoint createFromParcel(Parcel in) {
            return new TripStopPoint(in);
        }

        @Override
        public TripStopPoint[] newArray(int size) {
            return new TripStopPoint[size];
        }
    };

    public TripStopPoint(int tripSPId, String name, double amount, String latLngStrng, int tripID, String status) {
        this.bTSPId = tripSPId;
        this.bTSPTripID = tripID;
        this.bTSPName = name;
        this.bTSPAmount = amount;
        this.btSPLatLngStrng = latLngStrng;
        this.bTSPStatus = status;

    }

    public TripStopPoint(int tspId, String placeName, double amount, String latLng, String state, String status) {
        this.bTSPId = tspId;
        this.bTSPState = state;
        this.bTSPName = placeName;
        this.bTSPAmount = amount;
        this.btSPLatLngStrng = latLng;
        this.bTSPStatus = status;
    }

    public int getbTSPId() {
        return bTSPId;
    }

    public void setbTSPId(int bTSPId) {
        this.bTSPId = bTSPId;
    }

    public String getbTSPName() {
        return bTSPName;
    }

    public void setbTSPName(String bTSPName) {
        this.bTSPName = bTSPName;
    }

    public double getbTSPAmount() {
        return bTSPAmount;
    }

    public void setbTSPAmount(double bTSPAmount) {
        this.bTSPAmount = bTSPAmount;
    }

    public String getbTSPStatus() {
        return bTSPStatus;
    }

    public void setbTSPStatus(String bTSPStatus) {
        this.bTSPStatus = bTSPStatus;
    }

    public LatLng getBtSPLatLng() {
        return btSPLatLng;
    }

    public void setBtSPLatLng(LatLng btSPLatLng) {
        this.btSPLatLng = btSPLatLng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bTSPId);
        parcel.writeString(bTSPName);
        parcel.writeDouble(bTSPAmount);
        parcel.writeString(bTSPStatus);
        parcel.writeParcelable(btSPLatLng, i);
    }

    public int getbTSPTripID() {
        return bTSPTripID;
    }

    public void setbTSPTripID(int bTSPTripID) {
        this.bTSPTripID = bTSPTripID;
    }

    public String getBtSPLatLngStrng() {
        return btSPLatLngStrng;
    }

    public void setBtSPLatLngStrng(String btSPLatLngStrng) {
        this.btSPLatLngStrng = btSPLatLngStrng;
    }

    public String getbTSPState() {
        return bTSPState;
    }

    public void setbTSPState(String bTSPState) {
        this.bTSPState = bTSPState;
    }

    public double getbTSPLat() {
        return bTSPLat;
    }

    public void setbTSPLat(double bTSPLat) {
        this.bTSPLat = bTSPLat;
    }

    public double getbTSPLng() {
        return bTSPLng;
    }

    public void setbTSPLng(double bTSPLng) {
        this.bTSPLng = bTSPLng;
    }
}
