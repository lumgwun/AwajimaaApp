package com.skylightapp.Bookings;

import android.location.Location;

import com.skylightapp.MapAndLoc.BottomSheetButtonTypes;
import com.skylightapp.MapAndLoc.LocationTracker;

public interface TaxiBoookingSheetListener {
    public Object invoke(Object o);
    public void invoke(BottomSheetButtonTypes it);
}
