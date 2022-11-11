package com.skylightapp.MapAndLoc;

import android.Manifest;
import android.app.Activity;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;
import java.util.function.Consumer;

public class LocationUtil {
    private final Activity context;

    public LocationUtil(Activity contex) {
        this.context = contex;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkLocationSettings(Consumer<LatLng> function) {
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        FusedLocationProviderClient fusedLocationProviderClient;
        LocationRequest mLocationRequest;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        LocationCallback mLocationCallback;
        mLocationCallback = new LocationCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                function.accept(new LatLng(Objects.requireNonNull(locationResult.getLastLocation()).getLatitude(), locationResult.getLastLocation().getLongitude()
                ));
            }
        };

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        mLocationRequest = new LocationRequest();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(it ->
                        fusedLocationProviderClient
                                .requestLocationUpdates(
                                        mLocationRequest,
                                        mLocationCallback,
                                        Looper.getMainLooper()
                                ))
                .addOnFailureListener(e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    if (statusCode == 890) {
                        try {
                            ResolvableApiException rae = ((ResolvableApiException) e);
                            rae.startResolutionForResult(context, 0);
                            function.accept(new LatLng(0.0, 0.0));
                        } catch (IntentSender.SendIntentException ise) {
                            function.accept(new LatLng(0.0, 0.0));
                        }
                    }
                });
    }
}
