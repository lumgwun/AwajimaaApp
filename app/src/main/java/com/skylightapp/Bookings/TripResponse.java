package com.skylightapp.Bookings;

import com.google.gson.annotations.SerializedName;

public class TripResponse {
    @SerializedName("name")
    private String tripName;

    @SerializedName("tripStatus")
    private String tripStatus;

    @SerializedName("waypoints")
    private WaypointResponse[] waypoints;

    @SerializedName("vehicleId")
    private String vehicleId;

    public WaypointResponse[] getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(WaypointResponse[] waypoints) {
        this.waypoints = waypoints;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
