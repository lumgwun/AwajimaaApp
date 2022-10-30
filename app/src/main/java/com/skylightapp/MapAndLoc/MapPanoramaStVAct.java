package com.skylightapp.MapAndLoc;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewSource;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.LocationUpdatesBroadcastReceiver;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.webgeoservices.woosmapgeofencing.Woosmap;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.skylightapp.Classes.ImageUtil.TAG;

public class MapPanoramaStVAct extends FragmentActivity implements LocationListener ,GoogleMap.OnMarkerClickListener,OnStreetViewPanoramaReadyCallback, SharedPreferences.OnSharedPreferenceChangeListener{
    private SharedPreferences sharedPreferences;
    private String s,address;
    private FusedLocationProviderClient fusedLocationClient;
    private static final String GOOGLE_API_KEY = "AIzaSyD-wXTX8DBPhyESIeVjaGTLCclr8eculzM";
    GoogleMap googleMap;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    double latitude = 0;
    double longitude = 0;
    private int PROXIMITY_RADIUS = 5000;
    List<Address> addresses;
    Geocoder geocoder;
    String latString, lngString;
    DBHelper dbHelper;
    private Date today;
    Date currentDate;
    private Profile userProfile;
    String SharedPrefUserPassword;
    long reportTime;
    String dateOfToday;
    String SharedPrefCusID;
    Random ran;
    SecureRandom random;
    String SharedPrefUserMachine;
    String SharedPrefUserName,selectedPurpose;
    String SharedPrefProfileID;
    private long profileID;
    private int customerID;
    private long reportID;
    private Customer reporter;
    private  LatLng latlong;
    private Bundle bundle;
    private AppCompatButton btnSubmit;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private StreetViewPanorama streetPanoView;
    private String json,json1,SharedPrefSuperUser;

    public void doLocationSettings(View view) {
        requestPermissions();
    }

    public void shareLocation(View view) {
    }

    public void cancelLocUpdate(View view) {
        Log.i(TAG, "Removing location updates");
        Utils.setRequestingLocationUpdates(this, false);
        mFusedLocationClient.removeLocationUpdates(getPendingIntent());
    }

    public void doLocationUpdate(View view) {
        try {
            Log.i(TAG, "Starting location updates");
            Utils.setRequestingLocationUpdates(this, true);
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, getPendingIntent());
        } catch (SecurityException e) {
            Utils.setRequestingLocationUpdates(this, false);
            e.printStackTrace();
        }


    }

    @Override
    public boolean onMarkerClick(@NonNull @NotNull Marker marker) {
        return false;
    }


    public static class LongPressLocationSource implements LocationSource, GoogleMap.OnMapLongClickListener {

        private LocationSource.OnLocationChangedListener mListener;

        private boolean mPaused;
        private static final String TAG = MapPanoramaStVAct.class.getSimpleName();
        private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

        private static final long UPDATE_INTERVAL = 60000; // Every 60 seconds.


        private static final long FASTEST_UPDATE_INTERVAL = 30000; // Every 30 seconds


        private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 5; // Every 5 minutes.


        @Override
        public void activate(@NotNull OnLocationChangedListener listener) {
            mListener = listener;
        }

        @Override
        public void deactivate() {
            mListener = null;
        }

        @Override
        public void onMapLongClick(@NotNull LatLng point) {
            if (mListener != null && !mPaused) {
                Location location = new Location("LongPressLocationProvider");
                location.setLatitude(point.latitude);
                location.setLongitude(point.longitude);
                location.setAccuracy(100);
                mListener.onLocationChanged(location);
            }
        }

        public void onPause() {
            mPaused = true;
        }

        public void onResume() {
            mPaused = false;
        }
    }

    private LongPressLocationSource mLocationSource;
    AppCompatTextView mLocationUpdatesResultView;
    AppCompatButton mShareLocButton;
    AppCompatButton mRequestUpdatesButton;
    AppCompatButton mRemoveUpdatesButton,btnLocSettings;
    private LocationRequest mLocationRequest;

    Bundle userLocBundle, receivedReportBundle;
    Location location;
    LatLng latLng,lastLatLng,currentLatlng;
    double lastLat,lastLng,latitute1,longitute1,altitute1;
    double altitude,lastAltitude;
    float bearingAccuracyDegree,bearing,bearingDegree,bearing1;
    StreetViewPanorama streetViewPanorama;
    GoogleMap map;
    Uri sharedLocMap;
    AppCompatTextView txtAddress, txtLat,txtLng;

    private FusedLocationProviderClient mFusedLocationClient;
    private CancellationTokenSource cancellationTokenSource;
    private static final String PREF_NAME = "skylight";
    private String receivedLatLng;
    private Woosmap woosmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_profile_loc_source);
        woosmap = Woosmap.getInstance().initializeWoosmap(this);
        dbHelper= new DBHelper(this);
        reporter = new Customer();
        userLocBundle= new Bundle();
        receivedReportBundle =getIntent().getExtras();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        ran = new Random();
        gson1= new Gson();
        random = new SecureRandom();
        userProfile= new Profile();
        addresses=new ArrayList<>();
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        if(receivedReportBundle !=null){
            reporter = receivedReportBundle.getParcelable("Profile");
            receivedLatLng= receivedReportBundle.getParcelable("LatLng");
            lastLat=receivedReportBundle.getDouble("Lat");
            lastLng=receivedReportBundle.getDouble("Lng");

        }
        /*else {
            reporter =userProfile.getProfileCus();
        }
        reportTime = SystemClock.uptimeMillis();
        if(userProfile !=null){
            reporter =userProfile.getProfileCus();
        }*/
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());

        try {
            dateOfToday =dateFormat.format(currentDate);
            today = dateFormat.parse(dateOfToday);


        } catch (ParseException ignored) {
        }

        if(reporter !=null){
            customerID= reporter.getCusUID();
        }

        txtLat = findViewById(R.id.latText222);
        txtLng = findViewById(R.id.lngText22345);
        txtAddress = findViewById(R.id.locUserAddress22);
        btnSubmit = findViewById(R.id.buttonLoc);


        SupportStreetViewPanoramaFragment panoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.streetviewpanorama);
        panoramaFragment.getStreetViewPanoramaAsync(this);
        latitude=4.8359;
        longitude=7.0139;
        LatLng TechCreek = new LatLng(latitude, longitude);



        panoramaFragment.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

                if (savedInstanceState == null) {
                    StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera.Builder()
                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                            // .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                            .bearing(200)
                            .build();

                    streetViewPanorama.animateTo(camera, 500);
                    streetViewPanorama.setPosition(TechCreek, 20);

                }

            }
        });


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        else {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }

        //googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, (android.location.LocationListener) this);
        mRemoveUpdatesButton = (AppCompatButton) findViewById(R.id.CancellocUpDate);
        mRemoveUpdatesButton.setOnClickListener(this::removeLocationUpdates);
        mRequestUpdatesButton = (AppCompatButton) findViewById(R.id.DolocUpDate);
        mRequestUpdatesButton.setOnClickListener(this::requestLocationUpdates);
        mShareLocButton = (AppCompatButton) findViewById(R.id.SharelocUpDate);
        mLocationUpdatesResultView = (AppCompatTextView) findViewById(R.id.locUpDate);
        btnLocSettings = findViewById(R.id.setPermissionLoc);

        /*mRequestUpdatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GooglePlacesReadTask googlePlacesReadTask = new GooglePlacesReadTask();
                Object[] toPass = new Object[2];
                toPass[0] = googleMap;
                String googlePlacesUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" + "location=" + latitude + "," + longitude +
                        "&radius=" + PROXIMITY_RADIUS +
                        //googlePlacesUrl.append("&types=" + type);
                        "&sensor=true" +
                        "&key=" + GOOGLE_API_KEY;
                toPass[1] = googlePlacesUrl;
                googlePlacesReadTask.execute(toPass);
            }
        });*/
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


        /*btnSubmit.setOnClickListener(this::updateButtonOnclick);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dbHelper.insertUserEmergencyReport(profileID,customerID, String.valueOf(reportTime),latlong);
                if(latlong !=null){
                    //dbHelper.insertUserEmergencyReport(profileID,customerID, String.valueOf(reportTime),selectedPurpose,"",latlong);
                    dbHelper.insertCustomerLocation(customerID,latlong);
                }

            }
        });*/

        createLocationRequest();
        mLocationSource = new LongPressLocationSource();

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                bearingAccuracyDegree=location.getBearingAccuracyDegrees();
                            }
                            latitude=location.getLatitude();
                            longitude=location.getLongitude();
                            altitude=location.getAltitude();
                            bearing=location.getBearing();

                            userLocBundle=new Bundle();
                            userLocBundle.putParcelable("Location",latLng);
                            cancellationTokenSource = new CancellationTokenSource();
                            userLocBundle.putDouble("PreviousAltitude",lastAltitude);
                            userLocBundle.putDouble("PreviousLongitude",longitude);
                            userLocBundle.putDouble("PreviousLatitude",latitude);
                            userLocBundle.putDouble("PreviousBearing",bearing);
                            userLocBundle.putFloat("PreviousBearingDegree",bearingAccuracyDegree);
                            userLocBundle.putParcelable("PreviousLocation",lastLatLng);
                            //userLocBundle.putString("Location Map", String.valueOf(sharedLocMap));

                            LatLng lastLocation = new LatLng(latitude, longitude);
                            if (streetViewPanoramaFragment != null) {
                                streetViewPanoramaFragment.getStreetViewPanoramaAsync(MapPanoramaStVAct.this);
                                streetViewPanorama.setPosition(lastLocation);
                                streetViewPanorama.setPosition(lastLocation, 20);
                                streetViewPanorama.setPosition(lastLocation, StreetViewSource.OUTDOOR);

                                streetViewPanorama.setPosition(lastLocation, 20, StreetViewSource.OUTDOOR);
                                long duration = 1000;

                            }

                            streetViewPanorama.setPosition(lastLocation);
                            streetViewPanorama.setPosition(lastLocation, 20);
                            streetViewPanorama.setPosition(lastLocation, StreetViewSource.OUTDOOR);

                            streetViewPanorama.setPosition(lastLocation, 20, StreetViewSource.OUTDOOR);
                            long duration = 1000;
                            StreetViewPanoramaCamera camera =
                                    new StreetViewPanoramaCamera.Builder()
                                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                                            .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                                            .bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
                                            .build();
                            streetViewPanorama.animateTo(camera, duration);
                        }
                    }
                });


        fusedLocationClient.getCurrentLocation(2,cancellationTokenSource.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location2) {
                        if (location2 != null) {


                            //userLocBundle.putString("Location Map", String.valueOf(sharedLocMap));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                bearingAccuracyDegree=location2.getBearingAccuracyDegrees();
                            }
                            LatLng userLocation = new LatLng(latitude, longitude);
                            if (streetViewPanoramaFragment != null) {
                                streetViewPanoramaFragment.getStreetViewPanoramaAsync(MapPanoramaStVAct.this);
                                streetViewPanorama.setPosition(userLocation);
                                streetViewPanorama.setPosition(userLocation, 20);
                                streetViewPanorama.setPosition(userLocation, StreetViewSource.OUTDOOR);

                                streetViewPanorama.setPosition(userLocation, 20, StreetViewSource.OUTDOOR);

                            }
                            latitute1=location2.getLatitude();
                            longitute1=location2.getLongitude();
                            altitute1=location2.getAltitude();
                            bearing1=location2.getBearing();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                bearingDegree=location2.getBearingAccuracyDegrees();
                            }
                            userLocBundle=new Bundle();
                            userLocBundle.putParcelable("Location",latLng);
                            cancellationTokenSource = new CancellationTokenSource();
                            userLocBundle.putDouble("LastAltitude",altitute1);
                            userLocBundle.putDouble("LastLatitude",latitute1);
                            userLocBundle.putDouble("LastLongitude",longitute1);
                            userLocBundle.putFloat("LastBearing",bearing1);
                            userLocBundle.putFloat("LastBearingDegree",bearingDegree);
                            userLocBundle.putParcelable("LastLocation",userLocation);
                            setResult(Activity.RESULT_OK, new Intent());

                            streetViewPanorama.setPosition(userLocation);
                            streetViewPanorama.setPosition(userLocation, 20);
                            streetViewPanorama.setPosition(userLocation, StreetViewSource.OUTDOOR);

                            streetViewPanorama.setPosition(userLocation, 20, StreetViewSource.OUTDOOR);
                            long duration = 1000;
                            StreetViewPanoramaCamera camera =
                                    new StreetViewPanoramaCamera.Builder()
                                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                                            .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                                            .bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
                                            .build();
                            streetViewPanorama.animateTo(camera, duration);
                        }

                    }
                });
        txtLat.setText(MessageFormat.format("Lat:{0}", latitude));
        txtLng.setText(MessageFormat.format("Lng:{0}", longitude));
        txtAddress.setText(MessageFormat.format("Address:{0}", address));


    }

    public void updateButtonOnclick(View view){


    }
    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(20));

    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationSource.onPause();
    }
    @Override
    public void onStreetViewPanoramaReady(@NotNull StreetViewPanorama streetViewPanorama) {
        //Techcreek= 4.8359,7.0139----4.7774,7.0134
        //Unyeada>4.52871,7.44507----4.526685, 7.4462486===4.5287100,7.4450700
        //latitude=4.824167;
        //            longitude=6.9746;

        if(receivedReportBundle !=null){
            reporter = receivedReportBundle.getParcelable("Profile");
            receivedLatLng= receivedReportBundle.getParcelable("LatLng");
            lastLat=receivedReportBundle.getDouble("Lat");
            lastLng=receivedReportBundle.getDouble("Lng");

        }
        if (location != null) {
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            altitude=location.getAltitude();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bearingAccuracyDegree=location.getBearingAccuracyDegrees();
            }
            LatLng userLocation = new LatLng(latitude, longitude);
            streetViewPanorama.setPosition(userLocation);
            streetViewPanorama.setPosition(userLocation, 20);
            streetViewPanorama.setPosition(userLocation, StreetViewSource.OUTDOOR);

            streetViewPanorama.setPosition(userLocation, 20, StreetViewSource.OUTDOOR);

            streetPanoView = streetViewPanorama;
            streetPanoView.setPosition(userLocation);
            /** you can control the inputs into street view */
            streetPanoView.setUserNavigationEnabled(true);
            streetPanoView.setPanningGesturesEnabled(true);
            streetPanoView.setZoomGesturesEnabled(true);

            long duration = 1000;

            StreetViewPanoramaCamera camera =
                    new StreetViewPanoramaCamera.Builder()
                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                            .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                            .bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
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
                            .bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
                            .build();
            streetViewPanorama.animateTo(camera, duration);



        }
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAccuracy(100);

    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);

        }
        else {
            map.setMyLocationEnabled(false);
            requestPermissions();
        }
        mLocationSource.onResume();
        //updateButtonsState(Utils.getRequestingLocationUpdates(this));
        //mLocationUpdatesResultView.setText(Utils.getLocationUpdatesResult(this));
    }

    @Override
    protected void onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(LongPressLocationSource.UPDATE_INTERVAL);

        mLocationRequest.setFastestInterval(LongPressLocationSource.FASTEST_UPDATE_INTERVAL);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        mLocationRequest.setMaxWaitTime(LongPressLocationSource.MAX_WAIT_TIME);
    }

    private PendingIntent getPendingIntent() {

        Intent intent = new Intent(this, LocationUpdatesBroadcastReceiver.class);
        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //return null;
    }

    private boolean checkPermissions() {
        int fineLocationPermissionState = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);

        int backgroundLocationPermissionState = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            backgroundLocationPermissionState = ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }

        return (fineLocationPermissionState == PackageManager.PERMISSION_GRANTED) &&
                (backgroundLocationPermissionState == PackageManager.PERMISSION_GRANTED);
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
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.DolocUpDate),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MapPanoramaStVAct.this,
                                    new String[] {
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                                    LongPressLocationSource.REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(MapPanoramaStVAct.this,
                        new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                        LongPressLocationSource.REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == LongPressLocationSource.REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");

            } else if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                    (grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                // Permission was granted.
                requestLocationUpdates(null);

            } else {
                btnLocSettings.setVisibility(View.VISIBLE);
                Snackbar snackbar = Snackbar
                        .make(btnLocSettings, R.string.permission_denied_explanation, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("Awajima App",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });

                snackbar.show();

            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        this.sharedPreferences = sharedPreferences;
        this.s = s;
        if (s.equals(Utils.KEY_LOCATION_UPDATES_RESULT)) {
            mLocationUpdatesResultView.setText(Utils.getLocationUpdatesResult(this));
        } else if (s.equals(Utils.KEY_LOCATION_UPDATES_REQUESTED)) {
            updateButtonsState(Utils.getRequestingLocationUpdates(this));
        }
    }


    public void requestLocationUpdates(View view) {
        try {
            Log.i(TAG, "Starting location updates");
            Utils.setRequestingLocationUpdates(this, true);
            fusedLocationClient.requestLocationUpdates(mLocationRequest, getPendingIntent());
        } catch (SecurityException e) {
            Utils.setRequestingLocationUpdates(this, false);
            e.printStackTrace();
        }
    }

    public void removeLocationUpdates(View view) {
        Log.i(TAG, "Removing location updates");
        Utils.setRequestingLocationUpdates(this, false);
        fusedLocationClient.removeLocationUpdates(getPendingIntent());
    }


    private void updateButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            mRequestUpdatesButton.setEnabled(false);
            mRemoveUpdatesButton.setEnabled(true);
        } else {
            mRequestUpdatesButton.setEnabled(true);
            mRemoveUpdatesButton.setEnabled(false);
        }
    }


}