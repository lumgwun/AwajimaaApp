package com.skylightapp.Bookings;

import android.content.Context;
import android.location.Location;



public interface TripListener {

    void onTripStarted(Trip tripDetails);

    void onLocationUpdate(Location location);

    void onTripEnded(String trackingId);

    void onTripError(String reason);
}
