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

public class BoatTrip implements Parcelable, Serializable {
    public static final String BOAT_TRIP_TABLE = "boat_trip_Table";
    public static final String BOAT_TRIP_ID = "boatTrip_id";
    public static final String BOAT_TRIP_DBID = "boatTrip_DB_ID";
    public static final String BOAT_TRIP_PROF_ID = "boatTrip_Prof_id";
    public static final String BOAT_TRIP_STATE = "boatTrip_State";
    public static final String BOAT_TRIP_AMOUNT_ADULT = "boatTrip_Amount_Adult";
    public static final String BOAT_TRIP_AMT_CHILDREN = "boatTrip_Amt_Children";
    public static final String BOAT_TRIP_STATUS = "boatTrip_Status";
    public static final String BOAT_TRIP_NO_OF_SIT = "boatTrip_Sits_No";
    public static final String BOAT_TRIP_STATION = "boatTrip_Station";
    public static final String BOAT_TRIP_DEST_NAME = "boatTrip_Dest_Name";
    public static final String BOAT_TRIP_TOTAL_AMT = "boatTrip_TotalA";
    public static final String BOAT_TRIP_START_TIME = "boatTrip_StartT";
    public static final String BOAT_TRIP_ENDT = "boatTrip_EndT";
    public static final String BOAT_TRIP_DATE = "boatTrip_Date";
    public static final String BOAT_TRIP_TAKE_OFF_POINT = "boatTrip_Take_off_point";
    public static final String BOAT_TRIP_TAKE_OFF_LATLNG = "boatTrip_Take_off_LatLng";
    public static final String BOAT_TRIP_NO_OF_BOATS = "boatTrip_No_Of_Boat";
    public static final String BOAT_TRIP_TYPE_OF_BOAT = "boatTrip_Type_Of_Boat";
    public static final String BOAT_TRIP_TYPE = "boatTrip_Type";

    public static final String CREATE_BOAT_TRIP_TABLE = "CREATE TABLE " + BOAT_TRIP_TABLE + " (" + BOAT_TRIP_DBID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BOAT_TRIP_ID + " INTEGER, " + BOAT_TRIP_PROF_ID + " INTEGER , " +
            BOAT_TRIP_STATE + " TEXT ,"+ BOAT_TRIP_STATION + " TEXT, "+ BOAT_TRIP_DEST_NAME + " TEXT, "+ BOAT_TRIP_TOTAL_AMT + " TEXT, "+ BOAT_TRIP_START_TIME + " TEXT, "+ BOAT_TRIP_ENDT + " TEXT, "+ BOAT_TRIP_DATE + " TEXT, "+ BOAT_TRIP_AMOUNT_ADULT + " INTEGER, "+ BOAT_TRIP_AMT_CHILDREN + " INTEGER, "+ BOAT_TRIP_STATUS + " TEXT, "+ BOAT_TRIP_NO_OF_SIT + " INTEGER, "+ BOAT_TRIP_TAKE_OFF_POINT + " TEXT, "+ BOAT_TRIP_TAKE_OFF_LATLNG + " TEXT, "+ BOAT_TRIP_NO_OF_BOATS + " INTEGER, "+ BOAT_TRIP_TYPE_OF_BOAT + " TEXT, "+ BOAT_TRIP_TYPE + " TEXT, "+"FOREIGN KEY(" + BOAT_TRIP_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    private int boatTripID;
    private int boatTripProfID;
    private int boatTripNoOfSits;
    private String btLoadingPoint;
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
    private String btTakeOffLatLng;
    private int boatTripDuration;
    private ArrayList<Profile>btUsers;
    private ArrayList<BoatTripRoute>btRoutes;
    private ArrayList<Transaction>btTransactions;
    private ArrayList<EmergencyReport>btEmergReports;
    private String btTakeOffPoint;
    private ArrayList<TripStopPoint>btStopPoints;
    private int btNoOfBoats;
    private String btTripType;
    private String btTypeOfBoat;
    private boolean isCharterTrip;
    private int btNoOfSitRem;
    private int btNoOfBookedSits;

    public BoatTrip() {
        super();
    }

    protected BoatTrip(Parcel in) {
        boatTripID = in.readInt();
        boatTripProfID = in.readInt();
        boatTripNoOfSits = in.readInt();
        btLoadingPoint = in.readString();
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

    public static final Creator<BoatTrip> CREATOR = new Creator<BoatTrip>() {
        @Override
        public BoatTrip createFromParcel(Parcel in) {
            return new BoatTrip(in);
        }

        @Override
        public BoatTrip[] newArray(int size) {
            return new BoatTrip[size];
        }
    };

    public BoatTrip(int tripId, int profID, int tripNoOfSits, String takeOffPoint, String destPoint, String takeOffLatLng, String tripState, String tripDate, double tripAdultAmt, double tripChildrenAmt, String status) {
        this.boatTripID = tripId;
        this.boatTripProfID = profID;
        this.boatTripNoOfSits = tripNoOfSits;
        this.btLoadingPoint = takeOffPoint;
        this.btFinalDestination = destPoint;
        this.btStatus = status;
        this.btTakeOffLatLng = takeOffLatLng;
        this.btState = tripState;
        this.btDate = tripDate;
        this.btAmountForAdult = tripAdultAmt;
        this.btAmountForChildren = tripChildrenAmt;

    }
    public BoatTrip(int tripId, int profID, int tripNoOfBoats,String btTypeOfBoat, String takeOffPoint, String destPoint, String tripState, String tripDate,String typeOfTrip, String status) {
        this.boatTripID = tripId;
        this.boatTripProfID = profID;
        this.btNoOfBoats = tripNoOfBoats;
        this.btLoadingPoint = takeOffPoint;
        this.btFinalDestination = destPoint;
        this.btStatus = status;
        this.btTripType = typeOfTrip;
        this.btState = tripState;
        this.btDate = tripDate;
        this.btTypeOfBoat = btTypeOfBoat;


    }

    public int getBoatTripID() {
        return boatTripID;
    }

    public void setBoatTripID(int boatTripID) {
        this.boatTripID = boatTripID;
    }

    public String getBtLoadingPoint() {
        return btLoadingPoint;
    }

    public void setBtLoadingPoint(String btLoadingPoint) {
        this.btLoadingPoint = btLoadingPoint;
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

    public int getBoatTripProfID() {
        return boatTripProfID;
    }

    public void setBoatTripProfID(int boatTripProfID) {
        this.boatTripProfID = boatTripProfID;
    }

    public int getBoatTripNoOfSits() {
        return boatTripNoOfSits;
    }

    public void setBoatTripNoOfSits(int boatTripNoOfSits) {
        this.boatTripNoOfSits = boatTripNoOfSits;
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

    public String getBtTakeOffLatLng() {
        return btTakeOffLatLng;
    }

    public void setBtTakeOffLatLng(String btTakeOffLatLng) {
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
        parcel.writeInt(boatTripID);
        parcel.writeInt(boatTripProfID);
        parcel.writeInt(boatTripNoOfSits);
        parcel.writeString(btLoadingPoint);
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
        parcel.writeString(btTakeOffLatLng);
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

    public ArrayList<BoatTripRoute> getBtRoutes() {
        return btRoutes;
    }

    public void setBtRoutes(ArrayList<BoatTripRoute> btRoutes) {
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
    public void addBoatTripRoute(BoatTripRoute boatTripRoute) {
        btRoutes = new ArrayList<>();
        btRoutes.add(boatTripRoute);
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

    public String getBtTripType() {
        return btTripType;
    }

    public void setBtTripType(String btTripType) {
        this.btTripType = btTripType;
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
        return getBoatTripNoOfSits()-getBtNoOfBookedSits();
    }

}
