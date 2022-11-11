package com.skylightapp.MapAndLoc;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.functions.Consumer;

public class LocationService {
    private Context context;
    private final String tag = "LocationService";

    @Inject
    public LocationService
            (
                    @ApplicationContext Context context

            ) {
        this.context = context;

    }


    public Boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return androidx.core.content.ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
                    || androidx.core.content.ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getLastLocation(Consumer<LatLng> function) {
        FusedLocationProviderClient mFusedLocationProviderClient;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);


        try {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            Task<Location> lastLocation = mFusedLocationProviderClient.getLastLocation();
            lastLocation.addOnSuccessListener(it -> {
                if (it == null) {
                    try {
                        function.accept(new LatLng(0.0, 0.0));
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                } else {
                    try {
                        function.accept(new LatLng(it.getLatitude(), it.getLongitude()));
                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }
            }).addOnFailureListener(it -> Log.e(tag, "getLastLocation exception:" + it.getMessage()));
        } catch (Exception e) {
            Log.e(tag, "getLastLocation exception:" + e.getMessage());
        }
    }
}
