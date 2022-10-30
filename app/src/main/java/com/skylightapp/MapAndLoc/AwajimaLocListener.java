package com.skylightapp.MapAndLoc;

import android.location.Address;
import android.location.Location;

public interface AwajimaLocListener {
    public void onLocationUpdate(Location location, Address address);
}
