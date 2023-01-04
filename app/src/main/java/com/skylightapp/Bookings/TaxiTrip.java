package com.skylightapp.Bookings;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

import com.directions.route.Route;
import com.google.android.gms.maps.model.LatLng;
import com.google.common.collect.ImmutableList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MapAndLoc.Coordinate;

import java.io.Serializable;
import java.util.ArrayList;

public class TaxiTrip implements Serializable {
    public static final String TAXI_TRIP_TABLE = "taxi_Trip_table";
    public static final String TAXI_TRIP_ID = "taxi_Trip_ID";
    public static final String TAXI_TRIP_DBID = "taxi_Trip_DB_ID";
    public static final String TAXI_TRIP_BOOKER_ID = "taxi_Trip_Booker_ID";
    public static final String TAXI_TRIP_DRIVER_ID = "taxi_Trip_Driver_ID";
    public static final String TAXI_TRIP_START_POINT = "taxi_Trip_S_LatLng";
    public static final String TAXI_TRIP_END_POINT = "taxi_Trip_End_LatLng";
    public static final String TAXI_TRIP_COST = "taxi_Trip_Cost";
    public static final String TAXI_TRIP_DISTANCE = "taxi_Trip_Distance";
    public static final String TAXI_TRIP_ADDRESS = "taxi_Trip_Address";
    public static final String TAXI_TRIP_COORDINATE = "taxi_Trip_Coordinate";
    public static final String TAXI_TRIP_START_TIME = "taxi_Trip_Start_Time";
    public static final String TAXI_TRIP_END_TIME = "taxi_Trip_End_Time";
    public static final String TAXI_TRIP_END_STATUS = "taxi_Trip_End_Status";
    public static final String TAXI_TRIP_PROMO_REWARD = "taxi_Trip_Promo_Reward";

    public static final String CREATE_TAXI_TRIP_TABLE = "CREATE TABLE IF NOT EXISTS " + TAXI_TRIP_TABLE + " (" + TAXI_TRIP_DBID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TAXI_TRIP_ID + " INTEGER, "+
            TAXI_TRIP_BOOKER_ID + " INTEGER , " + TAXI_TRIP_DRIVER_ID + " INTEGER , " + TAXI_TRIP_START_POINT + " TEXT, " + TAXI_TRIP_END_POINT + " TEXT, " + TAXI_TRIP_COST + " TEXT, " + TAXI_TRIP_DISTANCE + " TEXT, " + TAXI_TRIP_ADDRESS + " TEXT, " + TAXI_TRIP_COORDINATE + " TEXT, " + TAXI_TRIP_START_TIME + " TEXT, " + TAXI_TRIP_END_TIME + " TEXT, " + TAXI_TRIP_END_STATUS + " TEXT,"+ TAXI_TRIP_PROMO_REWARD + " TEXT,"+"FOREIGN KEY(" + TAXI_TRIP_DRIVER_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +
            "FOREIGN KEY(" + TAXI_TRIP_BOOKER_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    Coordinate startingLocation;
    String startingAddress;
    Coordinate destinationLocation;
    String destinationAddress;
    Double cost;
    Route route;
    private LatLng startingLatLng;
    private LatLng destinationLatLng;
    TaxiDriver driver;
    private ArrayList<Profile> tripProfiles;
    private Double tripPromoReward;

    @SerializedName("pickup")
    @Expose
    private LatLng pickup;

    @SerializedName("dropoff")
    @Expose
    private LatLng dropoff;

    @SerializedName("intermediateDestinations")
    @Expose
    private ImmutableList<LatLng> intermediateDestinations;

    @SerializedName("tripType")
    @Expose
    private String tripType;

    public TaxiTrip() {
        super();
    }

    public void addPassengerProfile(Profile pProfile) {
        tripProfiles = new ArrayList<>();
        tripProfiles.add(pProfile);
    }

    public TaxiTrip(Coordinate startingLocation, String startingAddress, Coordinate destinationLocation, String destinationAddress, Double cost, Route route, TaxiDriver driver) {
        this.startingLocation = startingLocation;
        this.startingAddress = startingAddress;
        this.destinationLocation = destinationLocation;
        this.destinationAddress = destinationAddress;
        this.cost = cost;
        this.route = route;
        this.driver = driver;
    }

    public Route getRoute() {
        return route;
    }

    public TaxiDriver getDriver() {
        return driver;
    }

    public LatLng getDestinationLatLng() {
        return destinationLatLng;
    }

    public void setDestinationLatLng(LatLng destinationLatLng) {
        this.destinationLatLng = destinationLatLng;
    }

    public LatLng getStartingLatLng() {
        return startingLatLng;
    }

    public void setStartingLatLng(LatLng startingLatLng) {
        this.startingLatLng = startingLatLng;
    }

    public ArrayList<Profile> getTripProfiles() {
        return tripProfiles;
    }

    public void setTripProfiles(ArrayList<Profile> tripProfiles) {
        this.tripProfiles = tripProfiles;
    }

    public Double getTripPromoReward() {
        return tripPromoReward;
    }

    public void setTripPromoReward(Double tripPromoReward) {
        this.tripPromoReward = tripPromoReward;
    }

    public LatLng getPickup() {
        return pickup;
    }

    public void setPickup(LatLng pickup) {
        this.pickup = pickup;
    }

    public LatLng getDropoff() {
        return dropoff;
    }

    public void setDropoff(LatLng dropoff) {
        this.dropoff = dropoff;
    }

    public ImmutableList<LatLng> getIntermediateDestinations() {
        return intermediateDestinations;
    }

    public void setIntermediateDestinations(ImmutableList<LatLng> intermediateDestinations) {
        this.intermediateDestinations = intermediateDestinations;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }
}
