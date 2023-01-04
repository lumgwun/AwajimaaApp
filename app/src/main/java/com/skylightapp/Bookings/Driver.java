package com.skylightapp.Bookings;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.Bill;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.MarketClasses.MarketBizSub;

import java.io.Serializable;
import java.util.ArrayList;

public class Driver implements Parcelable, Serializable {
    public static final String DRIVER_TABLE = "driver_Table";
    public static final String DRIVER_ID = "driver_id";
    public static final String DRIVER_DBID = "driver_DBid";
    public static final String DRIVER_NAME = "driver_Name";
    public static final String DRIVER_PROF_ID = "driver_Prof_id";
    public static final String DRIVER_VEHICLE = "driver_Vehicle";
    public static final String DRIVER_TRIPS = "driver_Trips";
    public static final String DRIVER_REVENUE = "driver_Revenue";
    public static final String DRIVER_STATUS = "driver_Status";
    public static final String DRIVER_POSITION = "driver_Position";
    public static final String DRIVER_JOINED_D = "driver_DateJoined";
    public static final String DRIVER_COMPLAINS = "driver_Complains";
    public static final String DRIVER_REPORTS = "driver_Reports";
    public static final String DRIVER_PICTURE = "driver_Picture";
    public static final String DRIVER_TYPE = "driver_Type";
    public static final String DRIVER_RATING = "driver_Rating";
    public static final String DRIVER_LAST_SEEN = "driver_Seen";

    public static final String CREATE_DRIVER_TABLE = "CREATE TABLE " + DRIVER_TABLE + " (" + DRIVER_DBID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DRIVER_ID + " TEXT, " + DRIVER_PROF_ID + " INTEGER , " +
            DRIVER_NAME + " TEXT ,"+ DRIVER_VEHICLE + " TEXT, "+ DRIVER_TRIPS + " INTEGER, "+ DRIVER_REVENUE + " FLOAT, "+ DRIVER_JOINED_D + " TEXT, "+ DRIVER_POSITION + " TEXT, "+ DRIVER_STATUS + " TEXT, "+ DRIVER_COMPLAINS + " TEXT, "+ DRIVER_REPORTS + " TEXT, "+ DRIVER_PICTURE + " BLOB, "+ DRIVER_TYPE + " TEXT, "+ DRIVER_RATING + " TEXT, "+ DRIVER_LAST_SEEN + " TEXT, "+"FOREIGN KEY(" + DRIVER_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    private String driverID;
    private String driverName;
    private String driverVehicle;
    private Uri driverVehiclePix;
    private ArrayList<Uri> vehiclePixS;
    private ArrayList<Uri> drivingLicenceS;
    private String driverJoinedDate;
    private int driverNoOfTrips;
    private double driverRevenue;
    private String driverStatus;
    private LatLng driverPosition;
    private int driverComplainTrips;
    private int driverReports;
    private int driverProfID;
    private ArrayList<EmergencyReport> driverEmergRs;
    private ArrayList<String> driverTypeOfTrips;
    private ArrayList<LatLng> driverLatLngs;
    private String driverType;
    private double rating;
    private String lastTime;
    private String lastDistance;
    private Bill bill;
    private ArrayList<MarketBizSub> driverSubS;
    private double latitude;
    private double longitude;
    private double bearing;
    private int vehicleTypeId;
    private String DVehicleModel;
    private Uri driverPicture;
    private String driverPhone;
    private String driverLastName;
    private String driverFirstName;
    private String driverGender;

    public Driver() {
        super();
    }

    public Driver(String driverId, int profID, String name, Uri picture, String vehicle, String dateJoined, String status) {
        super();
        this.driverID = driverId;
        this.driverProfID = profID;
        this.driverName = name;
        this.driverVehiclePix = picture;
        this.driverVehicle = vehicle;
        this.driverJoinedDate = dateJoined;
        this.driverStatus = status;
    }
    public Driver(int driverId, int profID, String name, Uri picture, String vehicle, String dateJoined, String status) {
        super();
        this.driverID = String.valueOf(driverId);
        this.driverProfID = profID;
        this.driverName = name;
        this.driverVehiclePix = picture;
        this.driverVehicle = vehicle;
        this.driverJoinedDate = dateJoined;
        this.driverStatus = status;
    }

    public Driver(int driverId, String john, String adams, String picture, int i, int i1, float v, int i2, int i3, String s, float v1, int i4, String toyota_corolla) {
        this.driverID = String.valueOf(driverId);
        this.driverName = adams;

    }
    public void addDriverSub(MarketBizSub sub) {
        driverEmergRs = new ArrayList<>();
        driverSubS.add(sub);
    }

    public void addDriverEmergReport(EmergencyReport emergencyReport) {
        driverEmergRs = new ArrayList<>();
        driverEmergRs.add(emergencyReport);
    }
    public void addDriverLatLng(LatLng latLng) {
        driverLatLngs = new ArrayList<>();
        driverLatLngs.add(latLng);
    }

    public void addDriverTypeOfTrip(String typeOfTrip) {
        driverTypeOfTrips = new ArrayList<>();
        driverTypeOfTrips.add(typeOfTrip);
    }

    protected Driver(Parcel in) {
        driverID = in.readString();
        driverName = in.readString();
        driverVehicle = in.readString();
        driverVehiclePix = in.readParcelable(Uri.class.getClassLoader());
        vehiclePixS = in.createTypedArrayList(Uri.CREATOR);
        drivingLicenceS = in.createTypedArrayList(Uri.CREATOR);
        driverJoinedDate = in.readString();
        driverNoOfTrips = in.readInt();
        driverRevenue = in.readDouble();
        driverStatus = in.readString();
        driverPosition = in.readParcelable(LatLng.class.getClassLoader());
        driverComplainTrips = in.readInt();
        driverReports = in.readInt();
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverVehicle() {
        return driverVehicle;
    }

    public void setDriverVehicle(String driverVehicle) {
        this.driverVehicle = driverVehicle;
    }

    public Uri getDriverVehiclePix() {
        return driverVehiclePix;
    }

    public void setDriverVehiclePix(Uri driverVehiclePix) {
        this.driverVehiclePix = driverVehiclePix;
    }

    public ArrayList<Uri> getVehiclePixS() {
        return vehiclePixS;
    }

    public void setVehiclePixS(ArrayList<Uri> vehiclePixS) {
        this.vehiclePixS = vehiclePixS;
    }

    public ArrayList<Uri> getDrivingLicenceS() {
        return drivingLicenceS;
    }

    public void setDrivingLicenceS(ArrayList<Uri> drivingLicenceS) {
        this.drivingLicenceS = drivingLicenceS;
    }

    public String getDriverJoinedDate() {
        return driverJoinedDate;
    }

    public void setDriverJoinedDate(String driverJoinedDate) {
        this.driverJoinedDate = driverJoinedDate;
    }

    public int getDriverNoOfTrips() {
        return driverNoOfTrips;
    }

    public void setDriverNoOfTrips(int driverNoOfTrips) {
        this.driverNoOfTrips = driverNoOfTrips;
    }

    public double getDriverRevenue() {
        return driverRevenue;
    }

    public void setDriverRevenue(double driverRevenue) {
        this.driverRevenue = driverRevenue;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public LatLng getDriverPosition() {
        return driverPosition;
    }

    public void setDriverPosition(LatLng driverPosition) {
        this.driverPosition = driverPosition;
    }

    public int getDriverComplainTrips() {
        return driverComplainTrips;
    }

    public void setDriverComplainTrips(int driverComplainTrips) {
        this.driverComplainTrips = driverComplainTrips;
    }

    public int getDriverReports() {
        return driverReports;
    }

    public void setDriverReports(int driverReports) {
        this.driverReports = driverReports;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(driverID);
        parcel.writeString(driverName);
        parcel.writeString(driverVehicle);
        parcel.writeParcelable(driverVehiclePix, i);
        parcel.writeTypedList(vehiclePixS);
        parcel.writeTypedList(drivingLicenceS);
        parcel.writeString(driverJoinedDate);
        parcel.writeInt(driverNoOfTrips);
        parcel.writeDouble(driverRevenue);
        parcel.writeString(driverStatus);
        parcel.writeParcelable(driverPosition, i);
        parcel.writeInt(driverComplainTrips);
        parcel.writeInt(driverReports);
    }

    public int getDriverProfID() {
        return driverProfID;
    }

    public void setDriverProfID(int driverProfID) {
        this.driverProfID = driverProfID;
    }

    public ArrayList<String> getDriverTypeOfTrips() {
        return driverTypeOfTrips;
    }

    public void setDriverTypeOfTrips(ArrayList<String> driverTypeOfTrips) {
        this.driverTypeOfTrips = driverTypeOfTrips;
    }

    public ArrayList<LatLng> getDriverLatLngs() {
        return driverLatLngs;
    }

    public void setDriverLatLngs(ArrayList<LatLng> driverLatLngs) {
        this.driverLatLngs = driverLatLngs;
    }

    public String getDriverType() {
        return driverType;
    }

    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastDistance() {
        return lastDistance;
    }

    public void setLastDistance(String lastDistance) {
        this.lastDistance = lastDistance;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public ArrayList<MarketBizSub> getDriverSubS() {
        return driverSubS;
    }

    public void setDriverSubS(ArrayList<MarketBizSub> driverSubS) {
        this.driverSubS = driverSubS;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }

    public double getBearing() {
        return bearing;
    }

    public void setVehicleTypeId(int vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public int getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setDVehicleModel(String dVehicleModel) {
        this.DVehicleModel = dVehicleModel;
    }

    public String getDVehicleModel() {
        return DVehicleModel;
    }

    public void setDriverPicture(Uri driverPicture) {
        this.driverPicture = driverPicture;
    }

    public Uri getDriverPicture() {
        return driverPicture;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public void setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public void setDriverGender(String driverGender) {
        this.driverGender = driverGender;
    }

    public String getDriverGender() {
        return driverGender;
    }
}
