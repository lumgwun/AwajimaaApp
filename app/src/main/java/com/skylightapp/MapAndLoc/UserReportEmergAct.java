package com.skylightapp.MapAndLoc;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
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
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
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
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.CustomerPayAct;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Customers.PackListTab;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.Database.EmergReportNextDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Markets.MarketBizOffice;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.R;
import com.webgeoservices.woosmapgeofencing.Woosmap;
import com.webgeoservices.woosmapgeofencing.WoosmapSettings;

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


public class UserReportEmergAct extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {
    Location location;
    StreetViewPanorama streetViewPanorama;
    //OnStreetViewPanoramaReadyCallback
    private SharedPreferences userPreferences;
    private static final int REQUEST_CHECK_SETTINGS = 1000;
    private String s, bgAddress;

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
    private AppCompatSpinner spnTypeOfEmerg,spnSafe;
    Profile tellerProfile;
    private CustomerManager customerManager;
    Location customerLoc;

    LatLng cusLatLng;
    SharedPreferences.Editor editor;
    Gson gson, gson1, gson2;
    String json, json1, json2, userName, userPassword, userMachine, dateOfToday, selectedType;
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

    AppCompatTextView txtLocAddress, txtLat, txtLocTittle, txtLng;
    private static final String PREF_NAME = "awajima";
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    String SharedPrefProfileID, stringLatLng;
    EmergencyReport emergencyReport;
    private CancellationTokenSource cancellationTokenSource;
    PlacesClient placesClient;
    private boolean locationPermissionGranted;
    private View mapView, mapView2;
    LatLng userLocation, dest;
    private Woosmap woosmap;
    GoogleMap googleMapView, map, mMap;

    private GoogleApiClient googleApiClient;
    SupportMapFragment mapFrag;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private FloatingActionButton homeFab;
    private long reportIDF = 0;
    private String placeID, type, address, country;
    private Bundle bundle;
    private String locality,safeOption;
    private MarketBusiness marketBusiness;
    private long bizID;
    Place place;
    int PERMISSION_ALL = 1;
    private ChipNavigationBar chipNavigationBar;
    private SQLiteDatabase sqLiteDatabase;
    boolean firstTimeFlag;
    private LocationRequest locationRequest;
    private boolean iSReportingForSelf;
    private int reportTrackingID;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fabEmerg;

    private FusedLocationProviderClient fusedLocationClient;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    ActivityResultLauncher<Intent> mGetReportLoc = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            if (result.getData() != null) {
                                Intent data = result.getData();
                                //resultUri = data.getData();
                                place = PlacePicker.getPlace(UserReportEmergAct.this, data);
                                cusLatLng = place.getLatLng();
                                if (place != null) {
                                    placeID = place.getId();
                                    address = String.valueOf(place.getAddress());
                                }
                                //Bundle bundle = data.getBundleExtra("userLocBundle");
                                //placeID = bundle.getParcelable("placeID");
                                Calendar calendar = Calendar.getInstance();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy HH:SS", Locale.getDefault());
                                String locDate = mdformat.format(calendar.getTime());
                                //dbHelper.insertCustomerLocation(customerID,cusLatLng);
                                //dbHelper.insertUserEmergencyReport(0,profileID2,locDate, selectedPurpose, cusLatLng);
                                Toast.makeText(UserReportEmergAct.this, "Location registration successful", Toast.LENGTH_SHORT).show();

                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(UserReportEmergAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + result.getResultCode());
                    }
                }

            });
    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null)
                return;
            location = locationResult.getLastLocation();

            if (firstTimeFlag && map != null) {
                animateCamera(location);
                firstTimeFlag = false;
            }
            showMarker(location);
        }
    };


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_emerg);
        checkInternetConnection();
        woosmap = Woosmap.getInstance().initializeWoosmap(this);
        chipNavigationBar = findViewById(R.id.gsavings_nav_bar);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        chipNavigationBar = findViewById(R.id.emerg_Bar);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map34R);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            mapView = mapFrag.getView();
        }
        bundle = new Bundle();
        if (!hasPermissions(UserReportEmergAct.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(UserReportEmergAct.this, PERMISSIONS, PERMISSION_ALL);
        }
        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    //.addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
        locationRequest = LocationRequest.create();
        //createLocationRequest();
        //setInitialLocation();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //getDeviceLocation(dest, latitute1, longitute1);
        gson1 = new Gson();
        gson2 = new Gson();
        today = new Date();
        dbHelper = new DBHelper(this);
        userProfile = new Profile();
        customer = new Customer();
        addresses = new ArrayList<>();
        marketBusiness = new MarketBusiness();
        emergencyReport = new EmergencyReport();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefProfileID = String.valueOf(userPreferences.getLong("PROFILE_ID", 0));
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        userName = userPreferences.getString("PROFILE_USERNAME", "");
        userPassword = userPreferences.getString("PROFILE_PASSWORD", "");
        userMachine = userPreferences.getString("PROFILE_ROLE", "");
        profileID = userPreferences.getInt("PROFILE_ID", 0);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        //getLocationPermission();
        //geocoder = new Geocoder(this, Locale.getDefault());
        //cancellationTokenSource = new CancellationTokenSource();


        reportID = ThreadLocalRandom.current().nextInt(1020, 103210);
        spnTypeOfEmerg = findViewById(R.id.teller_pur_spn_);
        spnSafe = findViewById(R.id.safe_reporter_spn);

        txtLocAddress = findViewById(R.id.loAddress);
        txtLat = findViewById(R.id.telle_latT);
        txtLng = findViewById(R.id.teller_lng);
        txtLocTittle = findViewById(R.id.tittleLocU);
        homeFab = findViewById(R.id.ic_emerg_fab);
        fabEmerg = findViewById(R.id.fab_Emergency);

        if (marketBusiness != null) {
            bizID = marketBusiness.getBusinessID();
        }
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i) {
                            case R.id.emerg_reports:
                                Intent myIntent = new Intent(UserReportEmergAct.this, MyEmergReportAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.emerg_report_rewards:

                                Intent chat = new Intent(UserReportEmergAct.this, MyERRewardAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.emerg_report_com:

                                Intent shop = new Intent(UserReportEmergAct.this, CustomerHelpActTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);


                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });


        btnSubmitReport = findViewById(R.id.buttonTellerLoc);
        btnSubmitReport.setOnClickListener(this::sendEmergReport);
        homeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("Profile", userProfile);
                bundle.putInt("EMERGENCY_LOCID", reportID);
                Intent intent = new Intent(UserReportEmergAct.this, GoogleMapActivity.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mGetReportLoc.launch(intent);

            }
        });


        spnTypeOfEmerg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnSafe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                safeOption = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:SS", Locale.getDefault());

        dateOfToday = dateFormat.format(calendar.getTime());

        /*if(userProfile !=null){
            customer= userProfile.getProfileCus();
        }
        if(customer !=null){
            customerID=customer.getCusUID();
        }*/
        checkInternetConnection();
        String title = "Awajima Reports, Response";
        String body = "Emergency Reports received, and Tracking in Progress";

        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cusLatLng != null) {
                    EmergReportDAO emergReportDAO = new EmergReportDAO(UserReportEmergAct.this);
                    emergencyReport = new EmergencyReport(reportID, profileID, bizID, dateOfToday, selectedType, stringLatLng, locality, bgAddress, address, country);
                    if(dbHelper !=null){
                        sqLiteDatabase = dbHelper.getWritableDatabase();
                        reportIDF = emergReportDAO.insertUserEmergencyReport(reportID, profileID, bizID, dateOfToday, selectedType, stringLatLng, locality, bgAddress, address, country,safeOption);

                    }


                }
                if (reportIDF > 0) {
                    emergencyReport.setEmerGPlace(place);
                    createNotification(title, body);
                    enableLocUpdates(reportID,reportIDF);

                    Toast.makeText(UserReportEmergAct.this, "Your Emergency Report has been received ", Toast.LENGTH_SHORT).show();

                    showSnackbar(R.string.permission_terms, R.string.tracking,
                            new View.OnClickListener() {
                                @SuppressLint("UnspecifiedImmutableFlag")
                                @Override
                                public void onClick(View view) {

                                    Intent intent = new Intent();
                                    intent = new Intent(UserReportEmergAct.this, LocationUpdatesBroadcastReceiver.class);
                                    intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
                                    PendingIntent.getBroadcast(UserReportEmergAct.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            });

                    bundle.putParcelable("Profile", userProfile);
                    Intent intent = new Intent(UserReportEmergAct.this, LoginDirAct.class);
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }

            }
        });


    }

    private void animateCamera(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    private void showMarker(@NonNull Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        if (mCurrLocationMarker == null)
            mCurrLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker()).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(mCurrLocationMarker, latLng, new LatLngInterpolator.Spherical());
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }



    public void enableLocUpdates(int reportID, long reportIDF) {
        EmergReportNext emergReportNext = new EmergReportNext();
        EmergReportNextDAO emergReportNextDAO= new EmergReportNextDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        reportTrackingID = ThreadLocalRandom.current().nextInt(110, 1213);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:SS", Locale.getDefault());

        dateOfToday = dateFormat.format(calendar.getTime());

        FallbackLocTracker tracker = new FallbackLocTracker(UserReportEmergAct.this);
        tracker.start(new LocationTracker.LocationUpdateListener() {
            @Override
            public void onUpdate(Location oldLoc, long oldTime, Location newLoc, long newTime) {
                if(newLoc==null) {
                    if(oldLoc !=null){
                        location = oldLoc;

                    }else {
                        location = newLoc;
                    }
                }
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                if(latLng !=null){
                    stringLatLng = String.valueOf(latLng);

                }
                emergReportNextDAO.insertNewEmergNextLoc(reportTrackingID,reportID,dateOfToday,stringLatLng,reportIDF);
            }
        });

        WoosmapSettings.modeHighFrequencyLocation = !WoosmapSettings.modeHighFrequencyLocation;
        String msg = "";
        if (WoosmapSettings.modeHighFrequencyLocation) {
            msg = "Mode High Frequency Location Enable";
            editor.putBoolean("modeHighFrequencyLocationEnable", true);
            editor.apply();
        } else {
            msg = "Mode High Frequency Location disable";
            editor.putBoolean("modeHighFrequencyLocationEnable", false);
            editor.apply();
        }
        woosmap.enableModeHighFrequencyLocation(WoosmapSettings.modeHighFrequencyLocation);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(this, LocationUpdatesBroadcastReceiver.class);
        intent.setAction(LocationUpdatesBroadcastReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //return null;
    }

    public void removeLocationUpdates(View view) {
        Log.i(TAG, "Removing location updates");
        Utils.setRequestingLocationUpdates(this, false);
        fusedLocationClient.removeLocationUpdates(getPendingIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        fusedLocationClient.removeLocationUpdates(getPendingIntent());

        if (fusedLocationClient != null)
            fusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        fusedLocationClient.removeLocationUpdates(getPendingIntent());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map34R);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            mapView = mapFrag.getView();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(4.824167, 6.9746), 20.0f));

            CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
            googleMap.moveCamera(zoom);
            googleMap.animateCamera(zoom);


        } else {
            //buildGoogleApiClient();
            checkLocationPermission();
            //googleMap.setMyLocationEnabled(false);
            requestPermissions();
        }
        if (latitude == 0.0) {
            latitude = 4.8359;

        }
        if (longitude == 0.0) {
            longitude = 7.0139;

        }
        mMap = googleMap;
        cusLatLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(cusLatLng).title("Default Location"));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 16.0f));

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
        mMap.moveCamera(zoom);
        mMap.animateCamera(zoom);


    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map34R);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            mapView = mapFrag.getView();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map34R);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
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
                                ActivityCompat.requestPermissions(UserReportEmergAct.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
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
                            ActivityCompat.requestPermissions(UserReportEmergAct.this,
                                    new String[]{
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    })
                    .show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(UserReportEmergAct.this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map34R);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //setupLocationManager();
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }

                        this.mMap.setMyLocationEnabled(true);

                    }
                } else {

                    Toast.makeText(UserReportEmergAct.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    //finish();
                }

            }
            break;
            case REQUEST_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                    txtLocAddress = findViewById(R.id.loAddress);
                    txtLat = findViewById(R.id.telle_latT);
                    txtLng = findViewById(R.id.teller_lng);
                    txtLocTittle = findViewById(R.id.tittleLocU);
                    mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map34R);

                    if (mapFrag != null) {
                        mapFrag.getMapAsync(this);
                        mapView = mapFrag.getView();
                    }
                    fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location2) {
                                    if (location2 != null) {
                                        txtLocAddress.setVisibility(View.VISIBLE);
                                        txtLat.setVisibility(View.VISIBLE);
                                        txtLng.setVisibility(View.VISIBLE);
                                        txtLocTittle.setVisibility(View.VISIBLE);

                                        latitude = location2.getLatitude();
                                        longitude = location2.getLongitude();
                                        userLocation = new LatLng(latitude, longitude);
                                        stringLatLng = String.valueOf(latitude + "," + longitude);

                                    }
                                    if (googleMapView != null) {
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
                                    txtLat.setText("Your Lat:" + latitude);
                                    txtLng.setText("Your Lng:" + longitude);

                                }
                            });

                    geocoder = new Geocoder(this, Locale.getDefault());

                    if (location != null) {
                        txtLocAddress.setVisibility(View.VISIBLE);
                        txtLat.setVisibility(View.VISIBLE);
                        txtLng.setVisibility(View.VISIBLE);
                        txtLocTittle.setVisibility(View.VISIBLE);
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {

                            bgAddress = addresses.get(0).getAddressLine(0);
                            locality = addresses.get(0).getSubLocality();

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }

                    txtLocAddress.setText("My Loc:" + bgAddress);


                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        stringLatLng = String.valueOf(latitude + "," + longitude);


        //updateLocationUI();

        cusLatLng = new LatLng(latitude, longitude);
        txtLat.setText("Your Lat:" + latitude);
        txtLng.setText("Your Lng:" + longitude);

    }

    protected void createLocationRequest() {

        request = new LocationRequest();
        request.setSmallestDisplacement(10);
        request.setFastestInterval(50000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(3);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());


        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
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
                                    UserReportEmergAct.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });


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
                                    stringLatLng = String.valueOf(latitude + "," + longitude);
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
                latitute1 = latitude;
                longitute1 = longitude;
                setResult(Activity.RESULT_OK, new Intent());
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitute1, longitute1))).setTitle("Where you are");
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(dest);
                markerOptions.title("You are here");
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitute1, longitute1), 20));
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
        txtLocAddress = findViewById(R.id.loAddress);
        txtLat = findViewById(R.id.telle_latT);
        txtLng = findViewById(R.id.teller_lng);
        txtLocTittle = findViewById(R.id.tittleLocU);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map34R);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            mapView = mapFrag.getView();
        }
        locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    location = task.getResult();
                    double finalLatitute = latitute1;
                    double finalLongitute = longitute1;
                    if (location != null) {
                        txtLocAddress.setVisibility(View.VISIBLE);
                        txtLat.setVisibility(View.VISIBLE);
                        txtLng.setVisibility(View.VISIBLE);
                        txtLocTittle.setVisibility(View.VISIBLE);
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        cusLatLng = new LatLng(latitude, longitude);
                        stringLatLng = String.valueOf(latitude + "," + longitude);
                        cusLatLng = new LatLng(latitude, longitude);
                        dest = cusLatLng;
                        finalLatitute = latitude;
                        finalLongitute = longitude;
                        txtLat.setText("Your Lat:" + latitude);
                        txtLng.setText("Your Lng:" + longitude);
                        mMap.addMarker(new MarkerOptions().position(new LatLng(finalLatitute, finalLongitute))).setTitle("Where you were");
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(cusLatLng);
                        markerOptions.title("You are here");
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(finalLatitute, finalLongitute), 20));
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

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
        createLocationRequest();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        geocoder = new Geocoder(this, Locale.getDefault());
        createLocationRequest();
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map34R);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            mapView = mapFrag.getView();
        }
        cusLatLng = new LatLng(4.52871, 7.44507);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(4.52871, 7.44507), 20.0f));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
        mMap.moveCamera(zoom);
        mMap.animateCamera(zoom);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());


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

                    Toast.makeText(UserReportEmergAct.this, "Location enabled", Toast.LENGTH_LONG).show();
                    mRequestingLocationUpdates = true;
                    break;
                }
                case Activity.RESULT_CANCELED: {
                    Toast.makeText(UserReportEmergAct.this, "Location not enabled", Toast.LENGTH_LONG).show();
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


        if (ActivityCompat.checkSelfPermission( UserReportEmergAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( UserReportEmergAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                double lat=location.getLatitude();
                double lng=location.getLongitude();

                UserReportEmergAct.this.latitude=lat;
                UserReportEmergAct.this.longitude=lng;
                stringLatLng=String.valueOf(lat + "," + lng);

                try {
                    if(now !=null){
                        now.remove();
                    }

                    LatLng positionUpdate = new LatLng( UserReportEmergAct.this.latitude, UserReportEmergAct.this.longitude );
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
                    geocoder = new Geocoder(UserReportEmergAct.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {

                        android.location.Address returnAddress = addresses.get(0);

                        bgAddress = returnAddress.getAddressLine (0);

                        str.append(bgAddress).append( "" );
                        // str.append( city ).append( "" ).append( region_code ).append( "" );
                        // str.append( zipcode ).append( "" );
                        txtLat.setText("Lat:"+latitude);
                        txtLng.setText("Lng:"+longitude);

                        txtLocAddress.setText("Your Address"+str);
                        Toast.makeText(UserReportEmergAct.this, str,
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


        if (ActivityCompat.checkSelfPermission( UserReportEmergAct.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission( UserReportEmergAct.this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( UserReportEmergAct.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1002 );
        } else {

            setupLocationManager();
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        if (fusedLocationClient != null)
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(googleApiClient.isConnected()){
            enableLocUpdates(reportID, reportIDF);

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


    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {
        mLastLocation = location;
        txtLocAddress = findViewById(R.id.loAddress);
        txtLat = findViewById(R.id.telle_latT);
        txtLng = findViewById(R.id.teller_lng);
        txtLocTittle = findViewById(R.id.tittleLocU);
        /*if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }*/
        fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location2) {
                        if (location2 != null) {
                            txtLocAddress.setVisibility(View.VISIBLE);
                            txtLat.setVisibility(View.VISIBLE);
                            txtLng.setVisibility(View.VISIBLE);
                            txtLocTittle.setVisibility(View.VISIBLE);

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

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        Bundle bundle=new Bundle();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        emergencyReport = new EmergencyReport(reportID, profileID,bizID, dateOfToday, selectedType,stringLatLng,locality, bgAddress,address,country);

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
                            bundle.putParcelable("Place", (Parcelable) place);
                            Intent intent= new Intent(UserReportEmergAct.this, LocationUpdateAct.class);
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



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }
    public void createNotification(String title, String body) {
        final int NOTIFY_ID = 1002;

        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setLightColor( Color.GREEN);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);

            intent = new Intent(this, GeofenceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
            }
            pendingIntent = PendingIntent.getActivity(this, 0, intent, flags);

            builder.setContentTitle(title)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentText(body)
                    .setDefaults( Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(this);


            intent = new Intent(this, LoginDirAct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(title)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentText(body)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}