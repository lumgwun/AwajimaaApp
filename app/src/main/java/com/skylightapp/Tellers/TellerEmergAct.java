package com.skylightapp.Tellers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.EmergencyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LocationUpdateAct;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import static com.skylightapp.Classes.ImageUtil.TAG;


public class TellerEmergAct extends AppCompatActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    Location location;
    StreetViewPanorama streetViewPanorama;
//OnStreetViewPanoramaReadyCallback
    private SharedPreferences userPreferences;
    private static final int REQUEST_CHECK_SETTINGS = 1000;
    private String s, address;
    private FusedLocationProviderClient fusedLocationClient;
    private static final String GOOGLE_API_KEY = "AIzaSyD-wXTX8DBPhyESIeVjaGTLCclr8eculzM";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    double latitude = 0;
    double longitude = 0;
    private double latitute1, longitute1;
    private int PROXIMITY_RADIUS = 5000;
    private LocationRequest request;
    Marker now;
    private boolean mRequestingLocationUpdates;
    CameraUpdate cLocation;
    List<Address> addresses;
    Geocoder geocoder;
    private AppCompatSpinner spnPurpose;
    Profile tellerProfile;
    private CustomerManager customerManager;
    Location customerLoc;

    LatLng cusLatLng;
    SharedPreferences.Editor editor;
    Gson gson, gson1;
    String json, json1, userName, userPassword, userMachine, dateOfToday, selectedPurpose;
    Profile userProfile, customerProfile;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private int profileID;
    private int customerID;
    private int reportID;
    private Customer customer;
    private DBHelper dbHelper;
    Date today;

    AppCompatButton btnSubmitReport;

    AppCompatTextView txtLocAddress, txtLat, txtLng;
    private static final String PREF_NAME = "skylight";
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    String SharedPrefProfileID,stringLatLng;
    Task<Void> myProfile;
    private int locationRequestCode = 1000;
    Task<Void> myProfileDatabaseRef;
    EmergencyReport emergencyReport;
    private CancellationTokenSource cancellationTokenSource;
    PlacesClient placesClient;
    private boolean locationPermissionGranted;
    private View mapView,mapView2;
    LatLng userLocation,dest;
    GoogleMap googleMapView,map,mMap;

    private GoogleApiClient googleApiClient;
    SupportStreetViewPanoramaFragment streetViewPanoramaFragment;
    SupportMapFragment mapFrag;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_emerg);
        if (!hasPermissions(TellerEmergAct.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(TellerEmergAct.this, PERMISSIONS, PERMISSION_ALL);
        }
        checkInternetConnection();
        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder( this )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API )
                    .build();
        }
        googleApiClient.connect();
        //createLocationRequest();
        //setInitialLocation();
        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //getDeviceLocation(dest, latitute1, longitute1);
        gson1 = new Gson();
        gson = new Gson();
        today = new Date();
        dbHelper = new DBHelper(this);
        tellerProfile = new Profile();
        customer = new Customer();
        addresses = new ArrayList<>();
        emergencyReport = new EmergencyReport();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID= String.valueOf(userPreferences.getLong("PROFILE_ID", 0));
        json1 = userPreferences.getString("LastProfileUsed", "");
        tellerProfile = gson1.fromJson(json1, Profile.class);
        userName=userPreferences.getString("PROFILE_USERNAME", "");
        userPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        userMachine=userPreferences.getString("PROFILE_ROLE", "");
        profileID=userPreferences.getInt("PROFILE_ID", 0);
        //getLocationPermission();
        //geocoder = new Geocoder(this, Locale.getDefault());
        //cancellationTokenSource = new CancellationTokenSource();

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map34R);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            //mapView = mapFrag.getView();
        }


        reportID = ThreadLocalRandom.current().nextInt(1020, 10010);
        spnPurpose = findViewById(R.id.teller_pur_spn_);
        txtLocAddress = findViewById(R.id.loAddress);
        txtLat = findViewById(R.id.telle_latT);
        txtLng = findViewById(R.id.teller_lng);
        btnSubmitReport = findViewById(R.id.buttonTellerLoc);
        btnSubmitReport.setOnClickListener(this::sendEmergReport);

        spnPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPurpose = (String) parent.getSelectedItem();
                Toast.makeText(TellerEmergAct.this, "Purpose: "+ selectedPurpose,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());

        dateOfToday =dateFormat.format(calendar.getTime());

        if(tellerProfile !=null){
            customer= tellerProfile.getProfileCus();
        }
        if(customer !=null){
            customerID=customer.getCusUID();
        }

        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cusLatLng != null) {
                    emergencyReport = new EmergencyReport(reportID, profileID, selectedPurpose, dateOfToday, stringLatLng);
                    dbHelper.insertUserEmergencyReport(profileID, customerID, dateOfToday, selectedPurpose, "", stringLatLng);
                }

            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }

    }
    @Override
    public void onPause() {
        super.onPause();

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //mMap.getUiSettings().setZoomControlsEnabled(true);
            //mMap.getUiSettings().setZoomGesturesEnabled(true);
            //mMap.getUiSettings().setCompassEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(4.824167, 6.9746), 20.0f));

            CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
            googleMap.moveCamera(zoom);
            googleMap.animateCamera(zoom);


        }
        else {
            //buildGoogleApiClient();
            checkLocationPermission();
            //googleMap.setMyLocationEnabled(false);
            requestPermissions();
        }
        if(latitude==0.0){
            latitude=4.8359;

        }
        if(longitude==0.0){
            longitude=7.0139;

        }
        mMap=googleMap;
        cusLatLng = new LatLng(latitude, longitude);
        /*mMap.addMarker(new MarkerOptions().position(cusLatLng).title("Default Location"));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20.0f));

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
        mMap.moveCamera(zoom);
        mMap.animateCamera(zoom);
        updateLocationUI();
        getDeviceLocation();*/


    }
    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        request = new LocationRequest();
        request.setInterval(1000);
        request.setFastestInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);
        }
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(TellerEmergAct.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }
    private void requestPermissions() {

        boolean permissionAccessFineLocationApproved =
                ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;

        boolean backgroundLocationPermissionApproved =
                false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            backgroundLocationPermissionApproved = ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
        }

        boolean shouldProvideRationale =
                permissionAccessFineLocationApproved && backgroundLocationPermissionApproved;

        if (shouldProvideRationale) {
            Snackbar.make(
                    findViewById(R.id.DolocUpDate),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.Q)
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(TellerEmergAct.this,
                                    new String[] {
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(TellerEmergAct.this,
                        new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION )
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this,
                            Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
                        //setupLocationManager();
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }

                        this.mMap.setMyLocationEnabled(true);

                    }
                } else {

                    Toast.makeText( TellerEmergAct.this, "Permission Denied", Toast.LENGTH_SHORT ).show();
                    //finish();
                }

            }
            break;
            case REQUEST_PERMISSIONS_REQUEST_CODE:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                    fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location2) {
                                    if (location2 != null) {

                                        latitude = location2.getLatitude();
                                        longitude = location2.getLongitude();
                                        userLocation = new LatLng(latitude, longitude);
                                        stringLatLng=String.valueOf(latitude + "," + longitude);

                                    }
                                    if(googleMapView !=null){
                                        googleMapView.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                        //googleMapView.getUiSettings().setZoomControlsEnabled(true);
                                        //googleMapView.getUiSettings().setZoomGesturesEnabled(true);
                                        //googleMapView.getUiSettings().setCompassEnabled(true);
                                        googleMapView.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20.0f));
                                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
                                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(userLocation, 20);
                                        now = googleMapView.addMarker(new MarkerOptions().position(userLocation)
                                                .title("Your Location"));
                                        googleMapView.animateCamera(update);
                                        googleMapView.moveCamera(zoom);
                                        googleMapView.animateCamera(zoom);

                                    }

                                    cusLatLng = new LatLng(latitude, longitude);
                                    txtLat.setText("Your Lat:"+latitude);
                                    txtLng.setText("Your Lng:"+longitude);

                                }
                            });

                    geocoder = new Geocoder(this, Locale.getDefault());

                    if (location != null) {
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {

                            address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getSubLocality();


                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }

                    txtLocAddress.setText("My Loc:"+address);



                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }


        //updateLocationUI();

        cusLatLng = new LatLng(latitude, longitude);
        txtLat.setText("Your Lat:"+latitude);
        txtLng.setText("Your Lng:"+longitude);

    }
    protected void createLocationRequest() {

        request = new LocationRequest();
        request.setSmallestDisplacement( 10 );
        request.setFastestInterval( 50000 );
        request.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
        request.setNumUpdates( 3 );

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest( request );
        builder.setAlwaysShow( true );

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings( googleApiClient,
                        builder.build() );


        result.setResultCallback( new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {

                    case LocationSettingsStatusCodes.SUCCESS:
                        setInitialLocation();
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {

                            status.startResolutionForResult(
                                    TellerEmergAct.this,
                                    REQUEST_CHECK_SETTINGS );
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        } );


    }
    @SuppressLint("MissingPermission")
    private void getDeviceLocation(LatLng dest, double latitute1, double longitute1) {
        try {
            if (locationPermissionGranted) {
                location = null;
                double finalLatitute = latitute1;
                double finalLongitute = longitute1;
                fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location mCurrentLocation) {
                                if (mCurrentLocation != null) {
                                    latitude = mCurrentLocation.getLatitude();
                                    longitude = mCurrentLocation.getLongitude();
                                    cusLatLng = new LatLng(latitude, longitude);
                                    stringLatLng=String.valueOf(latitude + "," + longitude);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(mCurrentLocation.getLatitude(),
                                                    mCurrentLocation.getLongitude()), 20));

                                } else {
                                    Log.d(TAG, "Current location is null. Using Last Location.");
                                    getPreviousLoc(finalLatitute, finalLongitute);
                                }

                            }

                        });
                dest = cusLatLng;
                latitute1=latitude;
                longitute1=longitude;
                setResult(Activity.RESULT_OK, new Intent());
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))).setTitle("Where you are");
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(dest);
                markerOptions.title("You are here");
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20));
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getPreviousLoc(double latitute1, double longitute1) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationResult = fusedLocationClient.getLastLocation();
        locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    location = task.getResult();
                    double finalLatitute = latitute1;
                    double finalLongitute = longitute1;
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        cusLatLng = new LatLng(latitude, longitude);
                        stringLatLng=String.valueOf(latitude + "," + longitude);
                        cusLatLng = new LatLng(latitude, longitude);
                        dest = cusLatLng;
                        finalLatitute=latitude;
                        finalLongitute=longitude;
                        txtLat.setText("Your Lat:"+latitude);
                        txtLng.setText("Your Lng:"+longitude);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))).setTitle("Where you were");
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(cusLatLng);
                        markerOptions.title("You are here");
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20));
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                } else {

                    cusLatLng = new LatLng(4.52871, 7.44507);
                    Log.d(TAG, "Current location is null. Using defaults.");
                    mMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(cusLatLng, 17));
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                }
            }
        });

    }
    private void setupLocationManager() {
        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder( this )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API )
                    .build();
        }
        googleApiClient.connect();
        createLocationRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        //geocoder = new Geocoder(this, Locale.getDefault());
        createLocationRequest();
        /*locationTracker=new LocationTracker("my.action")
                .setInterval(50000)
                .setGps(true)
                .setNetWork(false)

                .currentLocation(new CurrentLocationReceiver(new CurrentLocationListener() {

                    @Override
                    public void onCurrentLocation(Location location) {
                        Log.d("callback", ":onCurrentLocation" + location.getLongitude());
                        locationTracker.stopLocationService(getBaseContext());
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {

                            address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getSubLocality();


                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        latitude=location.getLatitude();
                        longitude=location.getLongitude();
                        cusLatLng = new LatLng(latitude, longitude);
                    }

                    @Override
                    public void onPermissionDiened() {
                        Log.d("callback", ":onPermissionDiened");
                        locationTracker.stopLocationService(getBaseContext());
                    }
                }))

                .start(getBaseContext(), this);*/
        //cusLatLng = new LatLng(latitude, longitude);
        /*googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20.0f));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
        googleMap.moveCamera(zoom);
        googleMap.animateCamera(zoom);*/


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult()", Integer.toString(resultCode));

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK: {

                    setInitialLocation();

                    Toast.makeText(TellerEmergAct.this, "Location enabled", Toast.LENGTH_LONG).show();
                    mRequestingLocationUpdates = true;
                    break;
                }
                case Activity.RESULT_CANCELED: {
                    Toast.makeText(TellerEmergAct.this, "Location not enabled", Toast.LENGTH_LONG).show();
                    mRequestingLocationUpdates = false;
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }
    private void setInitialLocation() {


        if (ActivityCompat.checkSelfPermission( TellerEmergAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( TellerEmergAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                double lat=location.getLatitude();
                double lng=location.getLongitude();

                TellerEmergAct.this.latitude=lat;
                TellerEmergAct.this.longitude=lng;
                stringLatLng=String.valueOf(lat + "," + lng);

                try {
                    if(now !=null){
                        now.remove();
                    }

                    LatLng positionUpdate = new LatLng( TellerEmergAct.this.latitude,TellerEmergAct.this.longitude );
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom( positionUpdate, 15 );
                    now=mMap.addMarker(new MarkerOptions().position(positionUpdate)
                            .title("Your Location"));

                    mMap.animateCamera( update );
                    LatLng userLocation = new LatLng(latitude, longitude);
                    map.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(userLocation, 17));
                    map.getUiSettings().setMyLocationButtonEnabled(false);

                    //myCurrentloc.setText( ""+latitude );


                } catch (Exception ex) {

                    ex.printStackTrace();
                    Log.e( "MapException", ex.getMessage() );

                }

                try {
                    geocoder = new Geocoder(TellerEmergAct.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {

                        android.location.Address returnAddress = addresses.get(0);

                        String localityString = returnAddress.getAddressLine (0);

                        str.append( localityString ).append( "" );
                        // str.append( city ).append( "" ).append( region_code ).append( "" );
                        // str.append( zipcode ).append( "" );
                        txtLat.setText("Lat:"+latitude);
                        txtLng.setText("Lng:"+longitude);

                        txtLocAddress.setText("Your Address"+str);
                        Toast.makeText(TellerEmergAct.this, str,
                                Toast.LENGTH_SHORT).show();

                    } else {
                    /*    Toast.makeText(getApplicationContext(),
                                "geocoder not present", Toast.LENGTH_SHORT).show();*/
                    }


                } catch (IOException e) {

                    Log.e("tag", e.getMessage());
                }



            }

        } );
    }

    private void updateCamera(){

    }

    private void CheckMapPermission() {


        if (ActivityCompat.checkSelfPermission( TellerEmergAct.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( TellerEmergAct.this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( TellerEmergAct.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1002 );
        } else {

            setupLocationManager();
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        if(googleApiClient.isConnected()){
            setInitialLocation();

        }

        LocationManager service = (LocationManager) getSystemService( LOCATION_SERVICE );
        boolean enabled = service.isProviderEnabled( LocationManager.GPS_PROVIDER );

        if (!enabled) {
            buildAlertMessageNoGps();
        }
        if(enabled){
            //mMap.animateCamera( update );
/*
            Toast.makeText( MainActivity.this, "OnResume:"+latitude+","+longitude, Toast.LENGTH_SHORT ).show();
*/



        }


    }


    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        Bundle bundle=new Bundle();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = map.addMarker(markerOptions);
        if(this.emergencyReport !=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bundle.putParcelable("EmergencyReport",emergencyReport);
                            Intent intent= new Intent(TellerEmergAct.this, LocationUpdateAct.class);
                            intent.putExtras(bundle);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                    });

                }
            }).start();
        }

        //move map camera
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.animateCamera(CameraUpdateFactory.zoomTo(20));

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("Internet connection failed");
        }

        return hasInternetConnection;
    }
    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setTitle( "GPS Not Enabled" )
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }




    public void sendEmergReport(View view) {
    }
    /*@Override
    public void onStreetViewPanoramaReady(@NotNull StreetViewPanorama streetViewPanorama) {
        //Techcreek= 4.8359,7.0139----4.7774,7.0134
        //Unyeada>4.52871,7.44507----4.526685, 7.4462486===4.5287100,7.4450700
        if (location != null) {
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            LatLng userLocation = new LatLng(latitude, longitude);
            streetViewPanorama.setPosition(userLocation);
            streetViewPanorama.setPosition(userLocation, 20);
            streetViewPanorama.setPosition(userLocation, StreetViewSource.OUTDOOR);

            streetViewPanorama.setPosition(userLocation, 20, StreetViewSource.OUTDOOR);
            long duration = 1000;

            StreetViewPanoramaCamera camera =
                    new StreetViewPanoramaCamera.Builder()
                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                            .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                            //.bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
                            .build();
            streetViewPanorama.animateTo(camera, duration);
        }
        else {
            latitude=4.8359;
            longitude=7.0139;
            LatLng TechCreek = new LatLng(latitude, longitude);
            streetViewPanorama.setPosition(TechCreek);
            streetViewPanorama.setPosition(TechCreek, 20);
            streetViewPanorama.setPosition(TechCreek, StreetViewSource.OUTDOOR);

            streetViewPanorama.setPosition(TechCreek, 20, StreetViewSource.OUTDOOR);
            long duration = 1000;
            StreetViewPanoramaCamera camera =
                    new StreetViewPanoramaCamera.Builder()
                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                            .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                            //.bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
                            .build();
            streetViewPanorama.animateTo(camera, duration);



        }
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAccuracy(100);


    }*/


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }
}