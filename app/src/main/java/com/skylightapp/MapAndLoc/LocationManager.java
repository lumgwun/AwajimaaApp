package com.skylightapp.MapAndLoc;

import static com.skylightapp.MapAndLoc.OtherPref.Tags.WoosmapSdkTag;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.skylightapp.Bookings.Driver;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.DistancesDAO;
import com.skylightapp.Database.MovingPositionsDAO;
import com.skylightapp.Database.RegionDAO;
import com.skylightapp.Database.VisitDAO;
import com.webgeoservices.woosmapgeofencing.GeofenceTransitionsIntentService;
import com.webgeoservices.woosmapgeofencingcore.GeofenceHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocationManager {
    private LocationRequest mLocationRequest;
    private final FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private PendingIntent mLocationIntent;

    private final GeofencingClient mGeofencingClient;
    private PendingIntent mGeofencePendingIntent;
    private final GeofenceHelper geofenceHelper;

    private final Woosmap woos;
    private final Context context;
    private DBHelper db = null;
    VisitDAO visitDAO;
    RegionDAO regionDAO;
    MovingPositionsDAO movingPositionsDAO;
    //private PositionsManager positionsManager = null;

    public LocationManager(Context context, Woosmap woos) {
        this.woos = woos;
        this.context = context;
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        db = new DBHelper(context);
        visitDAO = new VisitDAO(context);
        regionDAO = new RegionDAO(context);
        movingPositionsDAO = new MovingPositionsDAO(context);
        //PositionsManager positionsManager = new PositionsManager(context,visitDAO,movingPositionsDAO,regionDAO, db);

        mGeofencingClient = LocationServices.getGeofencingClient(context);
        geofenceHelper = new GeofenceHelper(context);

        createLocationCallback();
        createLocationPendingIntent();
    }

    public void removeGeofences(int regionID,String dateTime) {
        RegionDAO regionDAO = new RegionDAO(context);
        try {
            mGeofencingClient.removeGeofences(getGeofencePendingIntent());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            regionDAO.deleteRegion(regionID,dateTime);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    public void removeGeofences(String id) {
        mGeofencingClient.removeGeofences( Collections.singletonList( id ) );
        //positionsManager.removeGeofence(id);
    }

    public void replaceGeofence(String oldId, String newId, final LatLng latLng, final float radius, final String type) {
        if(type.equals( "circle" )) {
            mGeofencingClient.removeGeofences( Collections.singletonList( oldId ) );
            Geofence geofence = geofenceHelper.getGeofence( newId, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT );
            GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest( geofence );
            //positionsManager.replaceGeofenceCircle( oldId, geofenceHelper, geofencingRequest, getGeofencePendingIntent(), mGeofencingClient, newId, radius, latLng.latitude, latLng.longitude);
        } else {
            //positionsManager.replaceGeofence( oldId, newId, radius, latLng.latitude, latLng.longitude, type );
        }
    }


    public void addGeofence(final String id, final LatLng latLng, final float radius, final String idStore, String type) {
        if(type.equals( "circle" )) {
            Geofence geofence = geofenceHelper.getGeofence( id, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT );
            GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest( geofence );
            //positionsManager.addGeofence( geofenceHelper, geofencingRequest, getGeofencePendingIntent(), mGeofencingClient, id, radius, latLng.latitude, latLng.longitude, idStore );
        } else {
            //positionsManager.createRegion( id,radius,latLng.latitude,latLng.longitude,idStore,type );
        }
    }


    private PendingIntent getGeofencePendingIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Intent intent = new Intent(context, GeofenceBroadcastReceiver.class);
            mGeofencePendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.
                    FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(context, GeofenceBroadcastReceiver.class);
            mGeofencePendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.
                    FLAG_UPDATE_CURRENT);
        } else {
            Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
            mGeofencePendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return mGeofencePendingIntent;
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location currentLocation = locationResult.getLastLocation();
                Log.d("AwajimaSdk", currentLocation.toString());

                List<Location> listLocations = new ArrayList<Location>();
                listLocations.add(currentLocation);
                if (woos.locationReadyListener != null) {
                    woos.locationReadyListener.LocationReadyCallback(currentLocation);
                }
                //positionsManager.asyncManageLocation(listLocations);
            }
        };
    }

    private void createLocationPendingIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Intent intent = new Intent(this.context, LocationUpdatesBroadcastReceiver.class);
            intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
            int flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
            mLocationIntent = PendingIntent.getBroadcast(this.context, 0, intent, flags);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(this.context, LocationUpdatesBroadcastReceiver.class);
            intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
            mLocationIntent = PendingIntent.getBroadcast(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            Intent intent = new Intent(this.context, LocationUpdatesIntentService.class);
            intent.setAction(LocationUpdatesIntentService.ACTION_PROCESS_UPDATES);
            mLocationIntent = PendingIntent.getService(this.context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    void setmLocationRequest() {
        mLocationRequest = new LocationRequest();
    }

    void updateLocationForeground() {
        if(mLocationRequest == null) {
            this.setmLocationRequest();
        }
        if(OtherPref.modeHighFrequencyLocation) {
            mLocationRequest.setInterval( 1000 );
            mLocationRequest.setFastestInterval( 1000 );
        } else {
            mLocationRequest.setInterval( 10000 );
            mLocationRequest.setFastestInterval( 5000 );
        }
        mLocationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationIntent);
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, null);//Looper.myLooper());
        } catch (SecurityException e) {
            Log.e(WoosmapSdkTag, "security exception");
        }
    }

    void updateLocationBackground() {
        if(OtherPref.foregroundLocationServiceEnable) {
            mFusedLocationClient.removeLocationUpdates(mLocationIntent);
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            return;
        }

        if(OtherPref.modeHighFrequencyLocation) {
            mLocationRequest.setInterval( 1000 );
            mLocationRequest.setFastestInterval( 1000 );
            mLocationRequest.setMaxWaitTime( 5000 );
            mLocationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
        } else {
            mLocationRequest.setInterval( 240000 );
            mLocationRequest.setFastestInterval( 60000 );
            mLocationRequest.setMaxWaitTime( 480000 );
            mLocationRequest.setPriority( LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY );
        }
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationIntent);
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationIntent);
        } catch (SecurityException e) {
            Log.e(WoosmapSdkTag, "security exception");
        }
    }

    void removeLocationUpdates() {
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationIntent);
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        } catch (SecurityException e) {
            Log.e(WoosmapSdkTag, "security exception");
        }
    }

    void removeLocationCallback() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    boolean checkPermissions() {
        int finePermissionState = ActivityCompat.checkSelfPermission(this.context,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        int coarsePermissionState = ActivityCompat.checkSelfPermission(this.context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        return finePermissionState == PackageManager.PERMISSION_GRANTED || coarsePermissionState == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    public void setMonitoringRegions(int reportID) {
        Log.d(WoosmapSdkTag,"Geofence Add on Reboot");
        mGeofencingClient.removeGeofences(getGeofencePendingIntent());
        //Region[] regionsTrip= new Region[].clone();
        RegionDAO regionDAO= new RegionDAO(context);
        ArrayList<Region> regionsReport = regionDAO.getReportRegions(reportID);
        for (Region regionToAdd : regionsReport) {
            Geofence geofence = geofenceHelper.getGeofence(regionToAdd.getIdentifier(), new LatLng( regionToAdd.getLng(), regionToAdd.getLat() ), (float) regionToAdd.getRadius(), Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
            GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
            mGeofencingClient.addGeofences(geofencingRequest, getGeofencePendingIntent())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(WoosmapSdkTag,"onSuccess: Geofence Added...");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String errorMessage=geofenceHelper.getErrorString(e);
                            Log.d(WoosmapSdkTag,"onFailure "+errorMessage);
                        }
                    });
        }
    }
}
