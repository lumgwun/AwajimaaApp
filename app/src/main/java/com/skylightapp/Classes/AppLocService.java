package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.location.LocationListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AppLocService extends Service implements LocationListener {

    protected LocationManager locationManager;
    Location location;

    private static final long MIN_DISTANCE_FOR_UPDATE = 100;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;
    String datetime;
    long differenceInMillis;
    long differenceInHours;

    public AppLocService(Context context) {
        locationManager = (LocationManager) context
                .getSystemService(LOCATION_SERVICE);
        Calendar date = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        try {
            date.setTime(Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH).parse(datetime))); // Parse into Date object
        } catch (ParseException e) {
            e.printStackTrace();
        }
        differenceInMillis = now.getTimeInMillis() - date.getTimeInMillis();
        differenceInHours = (differenceInMillis);
    }

    @SuppressLint("MissingPermission")
    public Location getLocation(String provider) {
        if (locationManager.isProviderEnabled(provider)) {
            locationManager.requestLocationUpdates(provider,
                    MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, (android.location.LocationListener) this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(provider);
                return location;
            }
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
