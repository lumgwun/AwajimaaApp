package com.skylightapp.MapAndLoc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.skylightapp.Classes.Message;

import java.util.Timer;
import java.util.TimerTask;


public class LocationResolver {
    private Timer timer;

    private LocationManager locationManager;
    private LocationResult locationResult;
    private boolean gpsEnabled = false;
    private boolean networkEnabled = false;
    private Handler locationTimeoutHandler;


    private final Handler.Callback locationTimeoutCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull android.os.Message message) {
            return false;
        }

        public boolean handleMessage(Message msg) {
            locationTimeoutFunc();
            return true;
        }

        @SuppressLint("MissingPermission")
        private void locationTimeoutFunc() {
            locationManager.removeUpdates(locationListenerGps);
            locationManager.removeUpdates(locationListenerNetwork);

            Location networkLocation = null, gpsLocation = null;
            if (gpsEnabled)
                gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (networkEnabled)
                networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            // if there are both values use the latest one
            if (gpsLocation != null && networkLocation != null) {
                if (gpsLocation.getTime() > networkLocation.getTime())
                    locationResult.gotLocation(gpsLocation);
                else
                    locationResult.gotLocation(networkLocation);
                return;
            }

            if (gpsLocation != null) {
                locationResult.gotLocation(gpsLocation);
                return;
            }
            if (networkLocation != null) {
                locationResult.gotLocation(networkLocation);
                return;
            }
            locationResult.gotLocation(null);
        }
    };
    private final LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            locationResult.gotLocation(location);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    public void prepare() {
        locationTimeoutHandler = new Handler(locationTimeoutCallback);
    }

    @SuppressLint("MissingPermission")
    public synchronized boolean getLocation(Context context, LocationResult result, int maxMillisToWait) {
        locationResult = result;
        if (locationManager == null)
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // exceptions will be thrown if provider is not permitted.
        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        // don't start listeners if no provider is enabled
        if (!gpsEnabled && !networkEnabled)
            return false;

        if (gpsEnabled)
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListenerGps, Looper.myLooper());
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        if (networkEnabled)
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, locationListenerNetwork, Looper.myLooper());
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

        timer = new Timer();
        timer.schedule(new GetLastLocationTask(), maxMillisToWait);
        return true;
    }

    private class GetLastLocationTask extends TimerTask {
        @Override
        public void run() {
            locationTimeoutHandler.sendEmptyMessage(0);
        }
    }

    public static abstract class LocationResult {
        public abstract void gotLocation(Location location);
    }
}
