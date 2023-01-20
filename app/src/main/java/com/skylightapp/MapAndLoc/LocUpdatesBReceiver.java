package com.skylightapp.MapAndLoc;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.skylightapp.Classes.Profile;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

public class LocUpdatesBReceiver extends BroadcastReceiver {
    public static final String ACTION_PROCESS_UPDATES = "ActionProcessUpdate";
    public static final String TAG = LocUpdatesBReceiver.class.getSimpleName();
    private Context mContext;
    private int reportID;
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    Intent intent;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 10000;
    List<Address> addresses;
    Geocoder geocoder;
    private String placeLatLngStrng;
    private FusedLocationProviderClient fusedLocationClient;
    String stringLatLng;
    LatLng placeLatLng;

    private CancellationTokenSource cancellationTokenSource;
    private Bundle bundle, newBundle;
    private Profile profile;

    @Override
    public void onReceive(Context context, Intent intent) {
        mTimer = new Timer();
        bundle= new Bundle();
        profile= new Profile();
        newBundle= new Bundle();

        bundle=intent.getExtras();
        if(bundle !=null){
            profile=bundle.getParcelable("Profile");
            reportID= bundle.getInt("EMERG_REPORT_ID");

        }
        newBundle.putInt("EMERG_REPORT_ID",reportID);
        newBundle.putParcelable("Profile",profile);
        intent = new Intent(ACTION_PROCESS_UPDATES);
        intent.putExtras(bundle);
        mTimer.schedule(new TimerTaskToGetLocation(), 5, notify_interval);

    }
    private void fn_update(Location location){

        intent.putExtra("latitude",location.getLatitude()+"");
        intent.putExtra("longitude",location.getLongitude()+"");
        sendBroadcast(intent);
    }

    private void sendBroadcast(Intent intent) {
        latitude = Double.parseDouble(intent.getStringExtra("latitude"));
        longitude = Double.parseDouble(intent.getStringExtra("longitude"));

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private class TimerTaskToGetLocation extends TimerTask {
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                }
            });

        }
    }
    private void fn_getlocation() {
        if (null != intent && intent.getAction().equals(ACTION_PROCESS_UPDATES)) {
            locationManager = (LocationManager) mContext.getApplicationContext().getSystemService(LOCATION_SERVICE);
            isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!isGPSEnable && !isNetworkEnable) {

            } else {

                if (isNetworkEnable) {
                    location = null;
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 36000, 0, (LocationListener) this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {

                            Log.e("latitude", location.getLatitude() + "");
                            Log.e("longitude", location.getLongitude() + "");

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            fn_update(location);
                        }
                    }

                }

                //Log.d("Location: ", "Latitude: " + locationData.getLatitude() + "Longitude:" + locationData.getLongitude());
            }
            fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                    .addOnSuccessListener((Executor) this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location mCurrentLocation) {
                            if (mCurrentLocation != null) {
                                latitude = mCurrentLocation.getLatitude();
                                longitude = mCurrentLocation.getLongitude();
                                placeLatLng = new LatLng(latitude, longitude);
                                stringLatLng = String.valueOf(latitude + "," + longitude);

                            } else {
                                Log.d(TAG, "Current location is null. Using Last Location.");
                            }

                        }

                    });
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }


}