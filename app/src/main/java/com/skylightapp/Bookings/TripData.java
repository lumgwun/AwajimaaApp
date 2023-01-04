package com.skylightapp.Bookings;

import com.google.auto.value.AutoValue;

import java.util.List;

public abstract class TripData {
    public abstract String tripName();

    public abstract int tripStatus();

    public abstract List<WaypointResponse> waypoints();

    public abstract String vehicleId();

    public abstract String tripId();

    public static Builder newBuilder() {
        //return new AutoValue_TripData.Builder();
        return null;
    }

    /** Builder for TripData. */
    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setTripName(String tripName);

        public abstract Builder setTripStatus(int tripStatus);

        public abstract Builder setWaypoints(List<WaypointResponse> waypoints);

        public abstract Builder setVehicleId(String vehicleId);

        public abstract Builder setTripId(String tripId);

        public abstract TripData build();
    }
}
