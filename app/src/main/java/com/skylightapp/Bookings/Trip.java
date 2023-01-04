package com.skylightapp.Bookings;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.MapAndLoc.EmergencyReport;

import java.io.Serializable;
import java.util.ArrayList;

public class Trip implements Parcelable, Serializable {
    public static final String A_TRIP_TABLE = "awajima_trip_Table";
    public static final String A_TRIP_ID = "aTrip_id";
    public static final String A_TRIP_DBID = "aTrip_DB_ID";
    public static final String A_TRIP_PROF_ID = "aTrip_Prof_id";
    public static final String A_TRIP_STATE = "aTrip_State";
    public static final String A_TRIP_AMOUNT_ADULT = "aTrip_Amount_Adult";
    public static final String A_TRIP_AMT_CHILDREN = "aTrip_Amt_Children";
    public static final String A_TRIP_STATUS = "aTrip_Status";
    public static final String A_TRIP_NO_OF_SIT = "aTrip_Sits_No";
    public static final String A_TRIP_STATION = "aTrip_Station";
    public static final String A_TRIP_DEST_NAME = "aTrip_Dest_Name";
    public static final String A_TRIP_TOTAL_AMT = "aTrip_TotalA";
    public static final String A_TRIP_START_TIME = "aTrip_StartT";
    public static final String A_TRIP_ENDT = "aTrip_EndT";
    public static final String A_TRIP_DATE = "aTrip_Date";
    public static final String A_TRIP_TAKE_OFF_POINT = "aTrip_Take_off_point";
    public static final String A_TRIP_TAKE_OFF_LATLNG = "aTrip_Take_off_LatLng";
    public static final String A_TRIP_NO_OF_BOATS = "aTrip_No_Of_Boat";
    public static final String A_TRIP_TYPE_OF_BOAT = "aTrip_Type_Of_Boat";
    public static final String A_TRIP_TYPE = "aTrip_Type";
    public static final String A_TRIP_CURRENCY = "aTrip_Currency";
    public static final String A_TRIP_BIZ_ID = "aTrip_Biz_ID";
    public static final String A_TRIP_COUNTRY = "aTrip_Country";
    public static final String A_TRIP_DRIVER_ID = "aTrip_Driver_ID";

    public static final String CREATE_BOAT_TRIP_TABLE = "CREATE TABLE " + A_TRIP_TABLE + " (" + A_TRIP_DBID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + A_TRIP_ID + " INTEGER, " + A_TRIP_PROF_ID + " INTEGER , " +
            A_TRIP_STATE + " TEXT ,"+ A_TRIP_STATION + " TEXT, "+ A_TRIP_DEST_NAME + " TEXT, "+ A_TRIP_TOTAL_AMT + " TEXT, "+ A_TRIP_START_TIME + " TEXT, "+ A_TRIP_ENDT + " TEXT, "+ A_TRIP_DATE + " TEXT, "+ A_TRIP_AMOUNT_ADULT + " INTEGER, "+ A_TRIP_AMT_CHILDREN + " INTEGER, "+ A_TRIP_STATUS + " TEXT, "+ A_TRIP_NO_OF_SIT + " INTEGER, "+ A_TRIP_TAKE_OFF_POINT + " TEXT, "+ A_TRIP_TAKE_OFF_LATLNG + " TEXT, "+ A_TRIP_NO_OF_BOATS + " INTEGER, "+ A_TRIP_TYPE_OF_BOAT + " TEXT, "+ A_TRIP_TYPE + " TEXT, "+ A_TRIP_CURRENCY + " TEXT, "+ A_TRIP_BIZ_ID + " Long, "+ A_TRIP_COUNTRY + " TEXT, "+ A_TRIP_DRIVER_ID + " TEXT, "+"FOREIGN KEY(" + A_TRIP_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    private int aTripID;
    private int aTripProfID;
    private int aTripNoOfSits;
    private String btLoadingPointLatLngStrng;
    private String btLoadingPointLat;
    private String btLoadingPointLng;
    private String btDate;
    private String btStartTime;
    private String btEndTime;
    private ArrayList<LatLng>btPickUpPoints;
    private ArrayList<LatLng>btDischargePoints;
    private ArrayList<TripBooking>btBookings;
    private String btStatus;
    private double btAmountForAdult;
    private double btAmountForChildren;
    private double btTotalAmount;
    private String btState;
    private String btFinalDestination;
    private LatLng btFinalDestLatLng;
    private LatLng btTakeOffLatLng;
    private int boatTripDuration;
    private ArrayList<Profile>btUsers;
    private ArrayList<TripRoute>btRoutes;
    private ArrayList<Transaction>btTransactions;
    private ArrayList<EmergencyReport>btEmergReports;
    private String btTakeOffPoint;
    private ArrayList<TripStopPoint>btStopPoints;
    private int btNoOfBoats;
    private String tripType;
    private String btTypeOfBoat;
    private boolean isCharterTrip;
    private int btNoOfSitRem;
    private int btNoOfBookedSits;
    private String btTripCurrency;
    private long btBizID;
    private String btCountry;
    private Driver tripDriver;
    private TaxiDriver tripTaxiDriver;
    private int tripDriverID;
    private int tripTaxiDriverID;

    public Trip() {
        super();
    }

    protected Trip(Parcel in) {
        aTripID = in.readInt();
        aTripProfID = in.readInt();
        aTripNoOfSits = in.readInt();
        btLoadingPointLatLngStrng = in.readString();
        btLoadingPointLat = in.readString();
        btLoadingPointLng = in.readString();
        btDate = in.readString();
        btStartTime = in.readString();
        btEndTime = in.readString();
        btPickUpPoints = in.createTypedArrayList(LatLng.CREATOR);
        btDischargePoints = in.createTypedArrayList(LatLng.CREATOR);
        btStatus = in.readString();
        btAmountForAdult = in.readDouble();
        btTotalAmount = in.readDouble();
        btState = in.readString();
        btFinalDestination = in.readString();
        btFinalDestLatLng = in.readParcelable(LatLng.class.getClassLoader());
        btTakeOffLatLng = in.readParcelable(LatLng.class.getClassLoader());
        boatTripDuration = in.readInt();
        btUsers = in.createTypedArrayList(Profile.CREATOR);
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public Trip(int tripId, int profID, int tripNoOfSits, String takeOffPoint, String destPoint, String takeOffLatLng, String tripState, String tripDate, double tripAdultAmt, double tripChildrenAmt, String status) {
        this.aTripID = tripId;
        this.aTripProfID = profID;
        this.aTripNoOfSits = tripNoOfSits;
        this.btLoadingPointLatLngStrng = takeOffPoint;
        this.btFinalDestination = destPoint;
        this.btStatus = status;
        this.btLoadingPointLatLngStrng = takeOffLatLng;
        this.btState = tripState;
        this.btDate = tripDate;
        this.btAmountForAdult = tripAdultAmt;
        this.btAmountForChildren = tripChildrenAmt;

    }
    public Trip(int tripId, int profID, int tripNoOfBoats, String btTypeOfBoat, String takeOffPoint, String destPoint, String tripState, String tripDate, String typeOfTrip, String status) {
        this.aTripID = tripId;
        this.aTripProfID = profID;
        this.btNoOfBoats = tripNoOfBoats;
        this.btLoadingPointLatLngStrng = takeOffPoint;
        this.btFinalDestination = destPoint;
        this.btStatus = status;
        this.tripType = typeOfTrip;
        this.btState = tripState;
        this.btDate = tripDate;
        this.btTypeOfBoat = btTypeOfBoat;


    }

    public int getaTripID() {
        return aTripID;
    }

    public void setaTripID(int aTripID) {
        this.aTripID = aTripID;
    }

    public String getBtLoadingPointLatLngStrng() {
        return btLoadingPointLatLngStrng;
    }

    public void setBtLoadingPointLatLngStrng(String btLoadingPointLatLngStrng) {
        this.btLoadingPointLatLngStrng = btLoadingPointLatLngStrng;
    }

    public String getBtLoadingPointLat() {
        return btLoadingPointLat;
    }

    public void setBtLoadingPointLat(String btLoadingPointLat) {
        this.btLoadingPointLat = btLoadingPointLat;
    }

    public String getBtLoadingPointLng() {
        return btLoadingPointLng;
    }

    public void setBtLoadingPointLng(String btLoadingPointLng) {
        this.btLoadingPointLng = btLoadingPointLng;
    }

    public String getBtDate() {
        return btDate;
    }

    public void setBtDate(String btDate) {
        this.btDate = btDate;
    }

    public String getBtStartTime() {
        return btStartTime;
    }

    public void setBtStartTime(String btStartTime) {
        this.btStartTime = btStartTime;
    }

    public String getBtEndTime() {
        return btEndTime;
    }

    public void setBtEndTime(String btEndTime) {
        this.btEndTime = btEndTime;
    }

    public ArrayList<LatLng> getBtPickUpPoints() {
        return btPickUpPoints;
    }

    public void setBtPickUpPoints(ArrayList<LatLng> btPickUpPoints) {
        this.btPickUpPoints = btPickUpPoints;
    }

    public ArrayList<LatLng> getBtDischargePoints() {
        return btDischargePoints;
    }

    public void setBtDischargePoints(ArrayList<LatLng> btDischargePoints) {
        this.btDischargePoints = btDischargePoints;
    }

    public String getBtStatus() {
        return btStatus;
    }

    public void setBtStatus(String btStatus) {
        this.btStatus = btStatus;
    }

    public double getBtAmountForAdult() {
        return btAmountForAdult;
    }

    public void setBtAmountForAdult(double btAmountForAdult) {
        this.btAmountForAdult = btAmountForAdult;
    }

    public double getBtTotalAmount() {
        return btTotalAmount;
    }

    public void setBtTotalAmount(double btTotalAmount) {
        this.btTotalAmount = btTotalAmount;
    }

    public String getBtState() {
        return btState;
    }

    public void setBtState(String btState) {
        this.btState = btState;
    }

    public int getaTripProfID() {
        return aTripProfID;
    }

    public void setaTripProfID(int aTripProfID) {
        this.aTripProfID = aTripProfID;
    }

    public int getaTripNoOfSits() {
        return aTripNoOfSits;
    }

    public void setaTripNoOfSits(int aTripNoOfSits) {
        this.aTripNoOfSits = aTripNoOfSits;
    }

    public String getBtFinalDestination() {
        return btFinalDestination;
    }

    public void setBtFinalDestination(String btFinalDestination) {
        this.btFinalDestination = btFinalDestination;
    }

    public LatLng getBtFinalDestLatLng() {
        return btFinalDestLatLng;
    }

    public void setBtFinalDestLatLng(LatLng btFinalDestLatLng) {
        this.btFinalDestLatLng = btFinalDestLatLng;
    }

    public LatLng getBtTakeOffLatLng() {
        return btTakeOffLatLng;
    }

    public void setBtTakeOffLatLng(LatLng btTakeOffLatLng) {
        this.btTakeOffLatLng = btTakeOffLatLng;
    }

    public int getBoatTripDuration() {
        return boatTripDuration;
    }

    public void setBoatTripDuration(int boatTripDuration) {
        this.boatTripDuration = boatTripDuration;
    }

    public ArrayList<Profile> getBtUsers() {
        return btUsers;
    }

    public void setBtUsers(ArrayList<Profile> btUsers) {
        this.btUsers = btUsers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(aTripID);
        parcel.writeInt(aTripProfID);
        parcel.writeInt(aTripNoOfSits);
        parcel.writeString(btLoadingPointLatLngStrng);
        parcel.writeString(btLoadingPointLat);
        parcel.writeString(btLoadingPointLng);
        parcel.writeString(btDate);
        parcel.writeString(btStartTime);
        parcel.writeString(btEndTime);
        parcel.writeTypedList(btPickUpPoints);
        parcel.writeTypedList(btDischargePoints);
        parcel.writeString(btStatus);
        parcel.writeDouble(btAmountForAdult);
        parcel.writeDouble(btTotalAmount);
        parcel.writeString(btState);
        parcel.writeString(btFinalDestination);
        parcel.writeParcelable(btFinalDestLatLng, i);
        parcel.writeString(String.valueOf(btTakeOffLatLng));
        parcel.writeInt(boatTripDuration);
        parcel.writeTypedList(btUsers);
    }

    public ArrayList<TripBooking> getBtBookings() {
        return btBookings;
    }

    public void setBtBookings(ArrayList<TripBooking> btBookings) {
        this.btBookings = btBookings;
    }

    public double getBtAmountForChildren() {
        return btAmountForChildren;
    }

    public void setBtAmountForChildren(double btAmountForChildren) {
        this.btAmountForChildren = btAmountForChildren;
    }

    public ArrayList<TripRoute> getBtRoutes() {
        return btRoutes;
    }

    public void setBtRoutes(ArrayList<TripRoute> btRoutes) {
        this.btRoutes = btRoutes;
    }

    public ArrayList<Transaction> getBtTransactions() {
        return btTransactions;
    }

    public void setBtTransactions(ArrayList<Transaction> btTransactions) {
        this.btTransactions = btTransactions;
    }

    public ArrayList<EmergencyReport> getBtEmergReports() {
        return btEmergReports;
    }

    public void setBtEmergReports(ArrayList<EmergencyReport> btEmergReports) {
        this.btEmergReports = btEmergReports;
    }
    public void addBoatTripBooking(TripBooking tripBooking) {
        btBookings = new ArrayList<>();

        btBookings.add(tripBooking);
    }
    public void addBoatTripRoute(TripRoute tripRoute) {
        btRoutes = new ArrayList<>();
        btRoutes.add(tripRoute);
    }
    public void addBoatTripTransaction(Transaction transaction) {
        btTransactions = new ArrayList<>();
        btTransactions.add(transaction);
    }
    public void addBoatTripEmergReport(EmergencyReport emergencyReport) {
        btEmergReports = new ArrayList<>();
        btEmergReports.add(emergencyReport);
    }
    public void addBoatTripStopPoint(TripStopPoint tripStopPoint) {
        btStopPoints = new ArrayList<>();
        btStopPoints.add(tripStopPoint);
    }


    public String getBtTakeOffPoint() {
        return btTakeOffPoint;
    }

    public void setBtTakeOffPoint(String btTakeOffPoint) {
        this.btTakeOffPoint = btTakeOffPoint;
    }

    public ArrayList<TripStopPoint> getBtStopPoints() {
        return btStopPoints;
    }

    public void setBtStopPoints(ArrayList<TripStopPoint> btStopPoints) {
        this.btStopPoints = btStopPoints;
    }

    public int getBtNoOfBoats() {
        return btNoOfBoats;
    }

    public void setBtNoOfBoats(int btNoOfBoats) {
        this.btNoOfBoats = btNoOfBoats;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public boolean isCharterTrip() {
        return isCharterTrip;
    }

    public void setCharterTrip(boolean charterTrip) {
        isCharterTrip = charterTrip;
    }

    public String getBtTypeOfBoat() {
        return btTypeOfBoat;
    }

    public void setBtTypeOfBoat(String btTypeOfBoat) {
        this.btTypeOfBoat = btTypeOfBoat;
    }


    public void setBtNoOfSitRem(int btNoOfSitRem) {
        this.btNoOfSitRem = btNoOfSitRem;
    }

    public int getBtNoOfBookedSits() {
        return btNoOfBookedSits;
    }

    public void setBtNoOfBookedSits(int btNoOfBookedSits) {
        this.btNoOfBookedSits = btNoOfBookedSits;
    }
    public int getBtNoOfSitRem() {
        return getaTripNoOfSits()-getBtNoOfBookedSits();
    }

    public String getBtTripCurrency() {
        return btTripCurrency;
    }

    public void setBtTripCurrency(String btTripCurrency) {
        this.btTripCurrency = btTripCurrency;
    }

    public long getBtBizID() {
        return btBizID;
    }

    public void setBtBizID(long btBizID) {
        this.btBizID = btBizID;
    }

    public String getBtCountry() {
        return btCountry;
    }

    public void setBtCountry(String btCountry) {
        this.btCountry = btCountry;
    }

    public Driver getTripDriver() {
        return tripDriver;
    }

    public void setTripDriver(Driver tripDriver) {
        this.tripDriver = tripDriver;
    }

    public TaxiDriver getTripTaxiDriver() {
        return tripTaxiDriver;
    }

    public void setTripTaxiDriver(TaxiDriver tripTaxiDriver) {
        this.tripTaxiDriver = tripTaxiDriver;
    }

    public int getTripDriverID() {
        return tripDriverID;
    }

    public void setTripDriverID(int tripDriverID) {
        this.tripDriverID = tripDriverID;
    }

    public int getTripTaxiDriverID() {
        return tripTaxiDriverID;
    }

    public void setTripTaxiDriverID(int tripTaxiDriverID) {
        this.tripTaxiDriverID = tripTaxiDriverID;
    }
}
