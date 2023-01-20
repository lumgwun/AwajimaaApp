package com.skylightapp.Bookings;

import androidx.multidex.MultiDexApplication;

import io.teliver.sdk.core.Teliver;


public class MapApp extends MultiDexApplication {

    public static final String TRACKING_ID = "tracking_id";


    @Override
    public void onCreate() {
        super.onCreate();
        Teliver.init(this, "bedbd2cbcd809ea707087942597a3096");
    }
}
