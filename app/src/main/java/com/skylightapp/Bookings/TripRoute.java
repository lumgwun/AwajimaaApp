package com.skylightapp.Bookings;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;

public class TripRoute {
    public static final String BOAT_TRIP_ROUTE_TABLE = "boat_trip_Route_Table";
    public static final String BOAT_TRIP_ROUTE_ID = "boatT_Route_id";
    public static final String BOAT_TRIP_ROUTE_FROM = "boatT_Route_From";
    public static final String BOAT_TRIP_ROUTE_TO = "boatT_Route_To";
    public static final String BOAT_TRIP_ROUTE_THROUGH = "boatT_Route_Through";
    public static final String BOAT_TRIP_ROUTE_STATUS = "boatT_Route_Status";
    public static final String BOAT_TRIP_ROUTE_DBID = "boatT_Route_DBId";
    public static final String BOAT_TRIP_ROUTE_PROF_ID = "boat_Route_PId";
    public static final String BOAT_TRIP_ROUTE_STATE = "boat_Route_State";
    public static final String BOAT_TRIP_ROUTE_COUNTRY = "boat_Route_Country";

    public static final String CREATE_BOAT_TRIP_ROUTE_TABLE = "CREATE TABLE " + BOAT_TRIP_ROUTE_TABLE + " (" + BOAT_TRIP_ROUTE_DBID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BOAT_TRIP_ROUTE_ID + " INTEGER, " + BOAT_TRIP_ROUTE_FROM + " TEXT , " +
            BOAT_TRIP_ROUTE_TO + " TEXT ,"+ BOAT_TRIP_ROUTE_THROUGH + " TEXT, "+ BOAT_TRIP_ROUTE_STATUS + " TEXT, "+ BOAT_TRIP_ROUTE_STATE + " TEXT, "+ BOAT_TRIP_ROUTE_COUNTRY + " TEXT, "+"FOREIGN KEY(" + BOAT_TRIP_ROUTE_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";
    private int btRouteID;
    private String btRouteFrom;
    private String btRouteTo;
    private String btRouteThrough;
    private String btRouteStatus;
    private String btRouteState;
    private String btRouteCountry;
    public TripRoute(int routeID, String to, String from, String through, String status) {
        super();
        this.btRouteID = routeID;
        this.btRouteTo = to;
        this.btRouteFrom = from;
        this.btRouteThrough = through;
        this.btRouteStatus = status;
    }

    public TripRoute(int routeId, String to, String from, String through, String state, String status) {
        this.btRouteID = routeId;
        this.btRouteTo = to;
        this.btRouteFrom = from;
        this.btRouteThrough = through;
        this.btRouteStatus = status;
        this.btRouteState = state;
    }

    public TripRoute(int routeId, String to, String from, String through, String state, String country, String status) {
        this.btRouteID = routeId;
        this.btRouteTo = to;
        this.btRouteFrom = from;
        this.btRouteThrough = through;
        this.btRouteStatus = status;
        this.btRouteState = state;
        this.btRouteCountry = country;
    }

    public TripRoute() {
        super();
    }

    public int getBtRouteID() {
        return btRouteID;
    }

    public void setBtRouteID(int btRouteID) {
        this.btRouteID = btRouteID;
    }

    public String getBtRouteFrom() {
        return btRouteFrom;
    }

    public void setBtRouteFrom(String btRouteFrom) {
        this.btRouteFrom = btRouteFrom;
    }

    public String getBtRouteTo() {
        return btRouteTo;
    }

    public void setBtRouteTo(String btRouteTo) {
        this.btRouteTo = btRouteTo;
    }

    public String getBtRouteThrough() {
        return btRouteThrough;
    }

    public void setBtRouteThrough(String btRouteThrough) {
        this.btRouteThrough = btRouteThrough;
    }

    public String getBtRouteStatus() {
        return btRouteStatus;
    }

    public void setBtRouteStatus(String btRouteStatus) {
        this.btRouteStatus = btRouteStatus;
    }


    public String getBtRouteState() {
        return btRouteState;
    }

    public void setBtRouteState(String btRouteState) {
        this.btRouteState = btRouteState;
    }

    public String getBtRouteCountry() {
        return btRouteCountry;
    }

    public void setBtRouteCountry(String btRouteCountry) {
        this.btRouteCountry = btRouteCountry;
    }
}
