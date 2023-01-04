package com.skylightapp.MapAndLoc;

import static com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_DWELL;
import static com.google.android.gms.location.Geofence.NEVER_EXPIRE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERGENCY_REPORT_TABLE;
import static com.skylightapp.MapAndLoc.EmergencyReport.EMERG_REPORT_ID;

import androidx.annotation.NonNull;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;

public abstract class Fence {
    public static final String FENCE_TABLE = "fence_table";
    public static final String FENCE_ID = "fence_id";
    public static final String FENCE_EMERG_ID = "fence_Emerg_id";
    public static final String FENCE_RADIUS = "fence_Radius";
    public static final String FENCE_NAME = "fence_Name";
    public static final String FENCE_CENTER = "fence_Centre";
    public static final String FENCE_STATUS = "fence_Status";
    public static final String FENCE_DATE_CREATED = "fence_Date_Created";
    public static final String FENCE_STATE = "fence_State";
    public static final String FENCE_TOWN = "fence_Stat";
    public static final String FENCE_COUNTRY = "fence_Country";
    public static final String CREATE_FENCE_TABLE = "CREATE TABLE IF NOT EXISTS " + FENCE_TABLE + " (" + FENCE_ID + " INTEGER , " + FENCE_EMERG_ID + " INTEGER , "+ FENCE_NAME + " TEXT , " +
            FENCE_CENTER + " TEXT , " + FENCE_RADIUS + " FLOAT , " + FENCE_STATUS + " TEXT , "+ FENCE_TOWN + " TEXT , "+ FENCE_STATE + " TEXT , "+ FENCE_COUNTRY + " TEXT , "+ "PRIMARY KEY(" + FENCE_ID + "), " + FENCE_DATE_CREATED + " TEXT , "+
            "FOREIGN KEY(" + FENCE_EMERG_ID + ") REFERENCES " + EMERGENCY_REPORT_TABLE + "(" + EMERG_REPORT_ID + "))";
    public static Fence
    buildCircularFence (String name, LatLng centre, float radius) {
        return new CircularFence (name, centre, radius); }

    private int fenceID;
    private int fenceEmergRID;
    private String name;
    private String centerLatLng;
    private float radius;
    private String status;
    private String fenceDateCreated;
    private String fenceState;
    private String fenceTown;
    private String fenceCountry;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    protected Fence (@NonNull String fencename) { name = fencename; }

    public Geofence createGeofence ()
    {
        return createBuilder ().build ();
    }


    protected Geofence.Builder createBuilder ()
    {
        return new Geofence.Builder ()
                .setRequestId (name)
                .setExpirationDuration (NEVER_EXPIRE)
                .setTransitionTypes ( Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT | GEOFENCE_TRANSITION_DWELL )
                .setLoiteringDelay (5 * 60 * 1000);  // DWELL fires after 5 minutes
    }

    abstract void showOnMap (FenceMap map);

    public int getFenceID() {
        return fenceID;
    }

    public void setFenceID(int fenceID) {
        this.fenceID = fenceID;
    }

    public String getCenterLatLng() {
        return centerLatLng;
    }

    public void setCenterLatLng(String centerLatLng) {
        this.centerLatLng = centerLatLng;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getFenceEmergRID() {
        return fenceEmergRID;
    }

    public void setFenceEmergRID(int fenceEmergRID) {
        this.fenceEmergRID = fenceEmergRID;
    }

    public String getFenceDateCreated() {
        return fenceDateCreated;
    }

    public void setFenceDateCreated(String fenceDateCreated) {
        this.fenceDateCreated = fenceDateCreated;
    }

    public String getFenceCountry() {
        return fenceCountry;
    }

    public void setFenceCountry(String fenceCountry) {
        this.fenceCountry = fenceCountry;
    }

    public String getFenceTown() {
        return fenceTown;
    }

    public void setFenceTown(String fenceTown) {
        this.fenceTown = fenceTown;
    }

    public String getFenceState() {
        return fenceState;
    }

    public void setFenceState(String fenceState) {
        this.fenceState = fenceState;
    }
}
