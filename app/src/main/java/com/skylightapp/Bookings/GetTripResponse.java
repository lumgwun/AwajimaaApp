package com.skylightapp.Bookings;

import androidx.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

public class GetTripResponse {
    @SerializedName("trip")
    private TripResponse trip;

    public TripResponse getTrip() {
        return trip;
    }

    @VisibleForTesting
    public void setTrip(TripResponse trip) {
        this.trip = trip;
    }
}
