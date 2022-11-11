package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_ID;
import static com.skylightapp.Bookings.BoatTrip.BOAT_TRIP_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;

import android.os.Parcel;
import android.os.Parcelable;

import com.skylightapp.MarketClasses.MarketBusiness;

import java.io.Serializable;
import java.util.ArrayList;

public class TripBooking implements Parcelable, Serializable {
    public static final String TRIP_BOOKING_TABLE = "trip_Booking_Table";
    public static final String TRIP_BOOKING_ID = "trip_Booking_id";
    public static final String TRIP_BOOKING_DBID = "trip_Booking_DB_id";
    public static final String TRIP_BOOKING_PROF_ID = "trip_Booking_Prof_id";
    public static final String TRIP_BOOKING_TRIP_ID = "trip_Booking_TRIP_id";
    public static final String TRIP_BOOKING_DEST = "trip_Booking_Dest";
    public static final String TRIP_BOOKING_DATE = "trip_Booking_Date";
    public static final String TRIP_BOOKING_MOP = "trip_Booking_Payment";
    public static final String TRIP_BOOKING_AMT = "trip_Booking_Amt";
    public static final String TRIP_BOOKING_CHILDREN = "trip_Booking_C";
    public static final String TRIP_BOOKING_ADULT = "trip_Booking_Adult";
    public static final String TRIP_BOOKING_LUGGAGE = "trip_Booking_LUG";
    public static final String TRIP_BOOKING_NAME = "trip_Booking_Name";
    public static final String TRIP_BOOKING_NIN = "trip_Booking_NIN";
    public static final String TRIP_BOOKING_SLOTS = "trip_Booking_Slots";
    public static final String TRIP_BOOKING_LAMT = "trip_Booking_LAmt";
    public static final String TRIP_BOOKING_TYPEOS = "trip_Booking_Service";
    public static final String TRIP_BOOKING_STATUS = "trip_Booking_Status";
    public static final String TRIP_BOOKING_TX_ID = "trip_Booking_TX_id";



    public static final String CREATE_TRIP_BOOKING_TABLE = "CREATE TABLE IF NOT EXISTS " + TRIP_BOOKING_TABLE + " (" + TRIP_BOOKING_DBID + " INTEGER, " + TRIP_BOOKING_ID + " INTEGER , " +
            TRIP_BOOKING_PROF_ID + " INTEGER , " + TRIP_BOOKING_TRIP_ID + " INTEGER , " + TRIP_BOOKING_DATE + " TEXT, " + TRIP_BOOKING_DEST + " TEXT, " +
            TRIP_BOOKING_NAME + " TEXT, " + TRIP_BOOKING_SLOTS + " INTEGER, " + TRIP_BOOKING_AMT + " REAL, " + TRIP_BOOKING_MOP + " TEXT, " +
            TRIP_BOOKING_TYPEOS + " TEXT, " + TRIP_BOOKING_ADULT + " INTEGER, " + TRIP_BOOKING_CHILDREN + " INTEGER, "+ TRIP_BOOKING_NIN + " TEXT, " +TRIP_BOOKING_LUGGAGE + " TEXT, "+  TRIP_BOOKING_LAMT + " TEXT, " + TRIP_BOOKING_STATUS + " INTEGER, " + "PRIMARY KEY(" +TRIP_BOOKING_DBID + "), " +"FOREIGN KEY(" + TRIP_BOOKING_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + TRIP_BOOKING_TRIP_ID  + ") REFERENCES " + BOAT_TRIP_TABLE + "(" + BOAT_TRIP_ID + ")," +"FOREIGN KEY(" + TRIP_BOOKING_TX_ID + ") REFERENCES " + TRANSACTIONS_TABLE + "(" + TRANSACTION_ID + "))";




    private int tripBID;
    private int tripBTripID;
    private String tripBDest;
    private String tripBTime;
    private String tripBModeOfPayment;
    private long tripBAmount;
    private int tripBNoOfAdult;
    private int tripBNoOfChildren;
    private int tripBProfID;
    private int tripBLuggageSize;
    private String tripBStatus;
    private String tripBName;
    private String tripBNIN;
    private long tripBLuggageAmt;
    private int tripBSlots;
    private String tripBTypeOfService;
    private ArrayList<String> ninList;
    public TripBooking() {
        super();
    }

    protected TripBooking(Parcel in) {
        tripBID = in.readInt();
        tripBDest = in.readString();
        tripBTime = in.readString();
        tripBModeOfPayment = in.readString();
        tripBAmount = in.readLong();
        tripBNoOfAdult = in.readInt();
        tripBNoOfChildren = in.readInt();
        tripBProfID = in.readInt();
        tripBLuggageSize = in.readInt();
        tripBStatus = in.readString();
        tripBName = in.readString();
        tripBNIN = in.readString();
        tripBLuggageAmt = in.readLong();
    }

    public TripBooking(int bookingID, int tripID, int profileID, String tripBTypeOfService, int sitCount, String stopPointName, long bookingAmt, String modeOfPayment, String subDate, String paid) {
        this.tripBID = bookingID;
        this.tripBTripID = tripID;
        this.tripBProfID = profileID;
        this.tripBTypeOfService = tripBTypeOfService;
        this.tripBSlots = sitCount;
        this.tripBDest = stopPointName;
        this.tripBAmount = bookingAmt;
        this.tripBModeOfPayment = modeOfPayment;
        this.tripBTime = subDate;
        this.tripBStatus = paid;
    }
    public void addBookerNIN(String bookerNin) {
        ninList = new ArrayList<>();
        ninList.add(bookerNin);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tripBID);
        dest.writeString(tripBDest);
        dest.writeString(tripBTime);
        dest.writeString(tripBModeOfPayment);
        dest.writeDouble(tripBAmount);
        dest.writeInt(tripBNoOfAdult);
        dest.writeInt(tripBNoOfChildren);
        dest.writeInt(tripBProfID);
        dest.writeInt(tripBLuggageSize);
        dest.writeString(tripBStatus);
        dest.writeString(tripBName);
        dest.writeString(tripBNIN);
        dest.writeDouble(tripBLuggageAmt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TripBooking> CREATOR = new Creator<TripBooking>() {
        @Override
        public TripBooking createFromParcel(Parcel in) {
            return new TripBooking(in);
        }

        @Override
        public TripBooking[] newArray(int size) {
            return new TripBooking[size];
        }
    };

    public int getTripBNoOfAdult() {
        return tripBNoOfAdult;
    }

    public void setTripBNoOfAdult(int tripBNoOfAdult) {
        this.tripBNoOfAdult = tripBNoOfAdult;
    }

    public int getTripBID() {
        return tripBID;
    }

    public void setTripBID(int tripBID) {
        this.tripBID = tripBID;
    }

    public String getTripBDest() {
        return tripBDest;
    }

    public void setTripBDest(String tripBDest) {
        this.tripBDest = tripBDest;
    }

    public String getTripBTime() {
        return tripBTime;
    }

    public void setTripBTime(String tripBTime) {
        this.tripBTime = tripBTime;
    }

    public String getTripBModeOfPayment() {
        return tripBModeOfPayment;
    }

    public void setTripBModeOfPayment(String tripBModeOfPayment) {
        this.tripBModeOfPayment = tripBModeOfPayment;
    }

    public long getTripBAmount() {
        return tripBAmount;
    }

    public void setTripBAmount(long tripBAmount) {
        this.tripBAmount = tripBAmount;
    }

    public int getTripBNoOfChildren() {
        return tripBNoOfChildren;
    }

    public void setTripBNoOfChildren(int tripBNoOfChildren) {
        this.tripBNoOfChildren = tripBNoOfChildren;
    }

    public int getTripBProfID() {
        return tripBProfID;
    }

    public void setTripBProfID(int tripBProfID) {
        this.tripBProfID = tripBProfID;
    }

    public int getTripBLuggageSize() {
        return tripBLuggageSize;
    }

    public void setTripBLuggageSize(int tripBLuggageSize) {
        this.tripBLuggageSize = tripBLuggageSize;
    }

    public String getTripBStatus() {
        return tripBStatus;
    }

    public void setTripBStatus(String tripBStatus) {
        this.tripBStatus = tripBStatus;
    }

    public String getTripBName() {
        return tripBName;
    }

    public void setTripBName(String tripBName) {
        this.tripBName = tripBName;
    }

    public String getTripBNIN() {
        return tripBNIN;
    }

    public void setTripBNIN(String tripBNIN) {
        this.tripBNIN = tripBNIN;
    }

    public long getTripBLuggageAmt() {
        return tripBLuggageAmt;
    }

    public void setTripBLuggageAmt(long tripBLuggageAmt) {
        this.tripBLuggageAmt = tripBLuggageAmt;
    }

    public int getTripBSlots() {
        return tripBSlots;
    }

    public void setTripBSlots(int tripBSlots) {
        this.tripBSlots = tripBSlots;
    }

    public String getTripBTypeOfService() {
        return tripBTypeOfService;
    }

    public void setTripBTypeOfService(String tripBTypeOfService) {
        this.tripBTypeOfService = tripBTypeOfService;
    }

    public int getTripBTripID() {
        return tripBTripID;
    }

    public void setTripBTripID(int tripBTripID) {
        this.tripBTripID = tripBTripID;
    }

    public ArrayList<String> getNinList() {
        return ninList;
    }

    public void setNinList(ArrayList<String> ninList) {
        this.ninList = ninList;
    }
}
