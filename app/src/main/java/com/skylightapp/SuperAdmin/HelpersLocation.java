package com.skylightapp.SuperAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.LocationUpdatesBroadcastReceiver;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MapAndLoc.ProfileLocSourceAct;
import com.skylightapp.R;

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

public class HelpersLocation extends FragmentActivity implements OnMapReadyCallback, com.google.android.gms.location.LocationListener, GoogleMap.OnMarkerClickListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private GoogleMap mMap;
    LocationListener locationListener;
    LocationManager locationManager;
    Button button;
    boolean request_active = false;
    Handler handler = new Handler();
    TextView textView;
    Boolean driver_active = false;
    private SharedPreferences sharedPreferences;
    private String s, address;
    private FusedLocationProviderClient fusedLocationClient;
    private static final String GOOGLE_API_KEY = "AIzaSyD-wXTX8DBPhyESIeVjaGTLCclr8eculzM";
    GoogleMap googleMap;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private static final long UPDATE_INTERVAL = 60000; // Every 60 seconds.


    private static final long FASTEST_UPDATE_INTERVAL = 30000; // Every 30 seconds


    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 5; // Every 5 minutes.
    double latitude = 0.00;
    double longitude = 0.00;
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
    String SharedPrefUserName, selectedPurpose;
    String SharedPrefProfileID;
    private long profileID, customerID, reportID;
    private Customer customer;
    private LatLng latlong;
    private Bundle bundle;
    private AppCompatButton btnSubmit;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson, gson1;
    private String json, json1, SharedPrefSuperUser;
    private ProfileLocSourceAct.LongPressLocationSource mLocationSource;
    AppCompatTextView mLocationUpdatesResultView;
    AppCompatButton mShareLocButton;
    AppCompatButton mRequestUpdatesButton;
    AppCompatButton mRemoveUpdatesButton, btnLocSettings;
    private LocationRequest mLocationRequest;

    Bundle userLocBundle, customerBundle;
    Location location;
    String city;
    LatLng latLng, lastLatLng, currentLatlng;
    double lastLat, lastLng, latitute1, longitute1, altitute1;
    double altitude, lastAltitude;
    float bearingAccuracyDegree, bearing, bearingDegree, bearing1;
    StreetViewPanorama streetViewPanorama;
    GoogleMap map;
    Uri sharedLocMap;
    LatLng userLocation;
    AppCompatTextView txtAddress, txtLat, txtLng;
    AppCompatSpinner spnPurpose;
    private Marker mCurrLocationMarker;
    private LocationRequest locationRequest;

    private FusedLocationProviderClient mFusedLocationClient;
    private CancellationTokenSource cancellationTokenSource;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map_test);
        dbHelper = new DBHelper(this);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map234);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        locationRequest = LocationRequest.create();
        userLocation = new LatLng(latitude, longitude);
        cancellationTokenSource = new CancellationTokenSource();
        geocoder = new Geocoder(this, Locale.getDefault());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        customer = new Customer();
        userLocBundle = new Bundle();
        customerBundle = getIntent().getExtras();
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ran = new Random();
        gson1 = new Gson();
        random = new SecureRandom();
        userProfile = new Profile();
        addresses = new ArrayList<>();
        SharedPrefUserName = userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword = userPreferences.getString("USER_PASSWORD", "");
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefProfileID = userPreferences.getString("PROFILE_ID", "");
        json1 = userPreferences.getString("LastProfileUsed", "");
        json = userPreferences.getString("LastTellerProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        if (customerBundle != null) {
            customer = customerBundle.getParcelable("Customer");

        } else {
            if (userProfile != null) {
                customer = userProfile.getTimelineCustomer();
            }

        }
        reportTime = SystemClock.uptimeMillis();
        if (userProfile != null) {
            customer = userProfile.getTimelineCustomer();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());

        try {
            dateOfToday = dateFormat.format(currentDate);
            today = dateFormat.parse(dateOfToday);


        } catch (ParseException ignored) {
        }

        if (customer != null) {
            customerID = customer.getCusUID();
        }

        txtLat = findViewById(R.id.latText222);
        txtLng = findViewById(R.id.lngText22345);
        txtAddress = findViewById(R.id.locUserAddress22);
        btnSubmit = findViewById(R.id.buttonLoc);
        spnPurpose = findViewById(R.id.purpose_spn_);

        //createLocationRequest();
        //mLocationSource = new ProfileLocSourceAct.LongPressLocationSource();


        spnPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reportID = random.nextInt((int) (Math.random() * 900) + 1110);
                selectedPurpose = (String) parent.getSelectedItem();
                Toast.makeText(HelpersLocation.this, "Purpose: " + selectedPurpose, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            ArrayList<Marker> markers=new ArrayList<Marker>();
            markers.add(mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))));
            LatLngBounds.Builder builder=new LatLngBounds.Builder();

            for(Marker marker: markers){
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds=builder.build();
            int padding=30;
            CameraUpdate cu= CameraUpdateFactory.newLatLngBounds(bounds,padding);
            mMap.animateCamera(cu);
        }
        /*fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location2) {
                        if (location2 != null) {
                            latitute1 = location2.getLatitude();
                            longitute1 = location2.getLongitude();
                            userLocation = new LatLng(latitute1, longitute1);
                            userLocBundle = new Bundle();
                            userLocBundle.putParcelable("Location", userLocation);
                            cancellationTokenSource = new CancellationTokenSource();
                            userLocBundle.putDouble("LastLatitude", latitute1);
                            userLocBundle.putDouble("LastLongitude", longitute1);
                            userLocBundle.putParcelable("LastLocation", userLocation);
                            setResult(Activity.RESULT_OK, new Intent());

                        }

                    }
                });*/


        if (userLocation != null) {
            latitute1 = location.getLatitude();
            longitute1 = location.getLongitude();
            userLocation = new LatLng(latitute1, longitute1);
            userLocation = new LatLng(latitute1, longitute1);
            userLocBundle = new Bundle();
            userLocBundle.putParcelable("Location", userLocation);
            cancellationTokenSource = new CancellationTokenSource();
            userLocBundle.putDouble("LastLatitude", latitute1);
            userLocBundle.putDouble("LastLongitude", longitute1);
            userLocBundle.putParcelable("LastLocation", userLocation);
            setResult(Activity.RESULT_OK, new Intent());
            try {
                addresses = geocoder.getFromLocation(latitute1, longitute1, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getSubLocality();


            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        txtLat.setText(MessageFormat.format("Lat:{0}", latitute1));
        txtLng.setText(MessageFormat.format("Lng:{0}", longitute1));
        txtAddress.setText(MessageFormat.format("Address:{0}", address+"/"+city));

    }
    private final LocationCallback mLocationCallBack = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                if (location != null) {
                    latitute1 = location.getLatitude();
                    longitute1 = location.getLongitude();
                    userLocation = new LatLng(latitute1, longitute1);
                    userLocBundle = new Bundle();
                    userLocBundle.putParcelable("Location", userLocation);
                    cancellationTokenSource = new CancellationTokenSource();
                    userLocBundle.putDouble("LastLatitude", latitute1);
                    userLocBundle.putDouble("LastLongitude", longitute1);
                    userLocBundle.putParcelable("LastLocation", userLocation);
                    setResult(Activity.RESULT_OK, new Intent());
                    userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    StringBuilder sourceToDestination = new StringBuilder();
                    sourceToDestination.append("https://maps.googleapis.com/maps/api/directions/json?");
                    sourceToDestination.append("origin=").append(userLocation.latitude).append(",").append(userLocation.longitude);
                    sourceToDestination.append("&destination=" + 4.8156 + "," + 7.0498);
                    sourceToDestination.append("&waypoints=via:").append(latitute1).append(",").append(longitute1);
                    sourceToDestination.append("&key=").append(getResources().getString(R.string.google_api_key));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(startPoint.getLatitude(), startPoint.getLongitude()))).setTitle("Source");
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(userLocation);
                    markerOptions.title("You are here");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    if(mCurrLocationMarker!=null){
                        mCurrLocationMarker.remove();
                    }
                    mCurrLocationMarker = mMap.addMarker(markerOptions);
                    //mMap.addMarker(new MarkerOptions().position().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTitle("You are here");
                    //getDirectionData.execute(data);
                }
            }
        }
    };


    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap mMap) {
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        }
        else {
            mMap.setMyLocationEnabled(false);
            requestPermissions();
        }*/
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallBack, Looper.myLooper());
            mMap.setMyLocationEnabled(true);
        } else {
            checkLocationPermission();
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        locationRequest = LocationRequest.create();
        locationRequest.setFastestInterval(12000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);



        if (location != null) {
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            altitude=location.getAltitude();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bearingAccuracyDegree=location.getBearingAccuracyDegrees();
            }

        }
        else {
            latitude=4.824167;
            longitude=6.9746;
            LatLng unyeada = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(unyeada).title("Marker in Unyeada"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(unyeada));
            mMap.setLocationSource(mLocationSource);
            mMap.setOnMapLongClickListener(mLocationSource);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(4.824167, 6.9746), 20.0f));

            CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
            mMap.moveCamera(zoom);
            mMap.animateCamera(zoom);



        }

    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(HelpersLocation.this,
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

        mLocationRequest.setInterval(UPDATE_INTERVAL);

        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
    }

    private PendingIntent getPendingIntent() {

        Intent intent = new Intent(this, LocationUpdatesBroadcastReceiver.class);
        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //return null;
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                ActivityCompat.requestPermissions(HelpersLocation.this,
                                        new String[] {
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                                        REQUEST_PERMISSIONS_REQUEST_CODE);
                            }
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(HelpersLocation.this,
                        new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                        REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NotNull String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {

                    mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallBack, Looper.myLooper());
                    mMap.setMyLocationEnabled(true);
                }

            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
            }
        }
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");

            } else if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                    (grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                requestLocationUpdates(null);

            } else {
                Intent intent = new Intent();
                intent.setAction(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("Skylight App",
                        BuildConfig.APPLICATION_ID, null);
                intent.setData(uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        }
    }



    public void requestLocationUpdates(View view) {
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
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {

    }

    @Override
    public boolean onMarkerClick(@NonNull @NotNull Marker marker) {
        return false;
    }

    public void updateButtonOnclick(View view) {
    }
    private void checkGPS() {
        LocationRequest locationRequest = LocationRequest.create();

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, locationSettingsResponse -> {
            Log.d("GPS_main", "OnSuccess");
            // GPS is ON
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull final Exception e) {
                Log.d("GPS_main", "GPS off");

                // GPS off
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    try {
                        resolvable.startResolutionForResult(HelpersLocation.this, 1);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}