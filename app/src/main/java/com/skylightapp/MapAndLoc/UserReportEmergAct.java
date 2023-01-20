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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.libraries.places.api.model.Place;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;

import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.Database.EmergReportNextDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.Awajima;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_DWELL;
import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_ENTER;
import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_EXIT;


public class UserReportEmergAct extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,View.OnClickListener{
    Location location;
    private SharedPreferences userPreferences;
    private static final int REQUEST_CHECK_SETTINGS = 190;
    private String s, bgAddress;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    double latitude = 0;
    double longitude = 0;
    private double latitute1, longitute1;
    private int PROXIMITY_RADIUS = 5000;
    private LocationRequest request;
    Marker now;
    private boolean mRequestingLocationUpdates;
    List<Address> addresses;
    Geocoder geocoder;
    private AppCompatSpinner spnTypeOfEmerg,spnSafe;
    LatLng placeLatLng;
    SharedPreferences.Editor editor;
    Gson gson, gson1, gson2;
    String json, json1, json2, userName, userPassword, userMachine, dateOfToday, selectedType;
    Profile userProfile, customerProfile;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 3212;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 9932;
    private int profileID;
    private int reportID;
    private Customer customer;
    private DBHelper dbHelper;
    private boolean registerReport=false;
    Date today;

    AppCompatButton btnSubmitReport;

    AppCompatTextView txtLocAddress, txtLat, txtLocTittle, txtLng;
    private static final String PREF_NAME = "awajima";
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    int SharedPrefProfileID;
    String stringLatLng;
    EmergencyReport emergencyReport;
    private CancellationTokenSource cancellationTokenSource;
    PlacesClient placesClient;
    private boolean locationPermissionGranted;
    private View mapView, mapView2;
    LatLng userLocation, dest,latLng,center;
    private Woosmap woosmap;
    GoogleMap googleMapView, map, mMap;

    private GoogleApiClient googleApiClient;
    SupportMapFragment mapFrag;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private FloatingActionButton fabPlacePicker;
    private long reportIDF = 0;
    private String placeID, type, address, country;
    private Bundle bundle;
    private String locality,safeOption;
    private MarketBusiness marketBusiness;
    private long bizID;
    Place place;
    int PERMISSION_ALL = 18765;
    private ChipNavigationBar chipNavigationBar;
    private SQLiteDatabase sqLiteDatabase;
    boolean firstTimeFlag;
    private LocationRequest locationRequest;
    private boolean iSReportingForSelf;
    private int reportTrackingID;
    EmergReportDAO emergReportDAO;
    private Awajima awajima;
    private String placeLatLngStrng, category;
    private static final double ONE_THOUSAND_KM = 1000000.0;
    private Location lastKnownLocation;

    private Circle circle;

    private Polygon polygon;

    private Polyline polyline;
    SharedPreferences.Editor medit;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fabEmerg;

    private FusedLocationProviderClient fusedLocationClient;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private static final int M_MAX_ENTRIES = 5;
    private String[] likelyPlaceNames;
    private String[] likelyPlaceAddresses;
    private List[] likelyPlaceAttributions;
    private LatLng[] likelyPlaceLatLngs;
    private static final String TAG = UserReportEmergAct.class.getSimpleName();
    private CameraPosition cameraPosition;
    private int index=0;
    private PendingIntent pendingIntent;
    private Bundle reportBundle;
    private int safeIndex=0;
    private boolean doIt;
    private AppCompatSpinner spnCategory;
    private static final String CLASSTAG =
            " " + UserReportEmergAct.class.getSimpleName () + " ";
    private final LatLng defaultLocation = new LatLng(4.8359, 7.0139);



    @SuppressLint("InlinedApi")
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };
    private com.google.android.gms.location.places.Place placeLoc;
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
                                if(data !=null){
                                    placeLoc = PlacePicker.getPlace(UserReportEmergAct.this, data);

                                }
                                if (placeLoc != null) {
                                    placeLatLng = placeLoc.getLatLng();
                                    placeID = placeLoc.getId();
                                    address = String.valueOf(placeLoc.getAddress());
                                }
                                if(placeLatLng !=null){
                                    placeLatLngStrng= String.valueOf(placeLatLng);
                                }

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
    private GroundOverlay groundOverlay;
    private Handler handler = new Handler();
    private long startTime = SystemClock.uptimeMillis();
    boolean boolean_permission;
    ArrayList<Fence> fences;
    private int catIndex =0;
    private GeofencingClient geofencingClient;
    CopyOnWriteArrayList<FenceEvent> events;
    private boolean hasBackgroundLocationPermission;
    private boolean hasCoarseLocationPermission;
    private boolean hasFineLocationPermission;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private AppController.Status status;
    Bundle mapViewBundle = null;
    private boolean isBundle=true;
    private ArrayList<EmergencyReport> emergencyReports;
    private String userPhoneNO,userEmailAddress;

    private AlertDialog addingFenceFailedDialog;
    private Runnable rotateThingsRunnable = new Runnable() {

        @Override
        public void run() {
            long currentTime = SystemClock.uptimeMillis();
            double diff = (currentTime - startTime) / 2222.2222;
            List<LatLng> points = new ArrayList<LatLng>();
            if(latitude==0){
                if(longitude==0){
                    latitude = 4.8359;
                    longitude = 7.0139;
                }

            }else{
                if(latLng !=null){
                    latitude=latLng.latitude;
                    longitude=latLng.longitude;
                }

            }
            points.add(new LatLng(latitude, longitude));
            double lat = latitude * Math.sin(diff);
            double lng = longitude * Math.cos(diff);
            points.add(new LatLng(latitude + lat, longitude + lng));
            polyline.setPoints(points);

            groundOverlay.setBearing(groundOverlay.getBearing() + 1.0f);

            handler.postDelayed(this, 50);
        }
    };
    private AdapterView.OnItemSelectedListener cat_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(catIndex == position){
                return; //do nothing
            }
            else {
                if(reportBundle !=null){
                    category=reportBundle.getString("ReportType");

                }else {
                    category = spnCategory.getSelectedItem().toString();

                }

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_emerg);
        checkInternetConnection();
        if (!hasPermissions(UserReportEmergAct.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(UserReportEmergAct.this, PERMISSIONS, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
        awajima= new Awajima();
        //status = AppController.Status.DEFAULT;
        //events = new CopyOnWriteArrayList<> ();
        emergencyReports= new ArrayList<>();
        fences = new ArrayList<> ();
        reportBundle= new Bundle();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        chipNavigationBar = findViewById(R.id.emerg_Bar);
        spnCategory = findViewById(R.id.emerg_spn_Cat);
        spnCategory.setOnItemSelectedListener(cat_listener);

        /*mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Emerg);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            //mapView = mapFrag.getView();
        }*/

        bundle = new Bundle();

        //initializeAllMapStuff(savedInstanceState);

        //setInitialLocation();
        //getDeviceLocation(dest, latitute1, longitute1);
        gson1 = new Gson();
        gson2 = new Gson();
        today = new Date();
        dbHelper = new DBHelper(this);
        emergReportDAO = new EmergReportDAO(UserReportEmergAct.this);
        userProfile = new Profile();
        customer = new Customer();
        addresses = new ArrayList<>();
        marketBusiness = new MarketBusiness();
        emergencyReport = new EmergencyReport();

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefProfileID = userPreferences.getInt("PROFILE_ID", 0);
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        userName = userPreferences.getString("PROFILE_USERNAME", "");
        userPassword = userPreferences.getString("PROFILE_PASSWORD", "");
        userMachine = userPreferences.getString("PROFILE_ROLE", "");
        userPhoneNO = userPreferences.getString("PROFILE_PHONE", "");
        userEmailAddress = userPreferences.getString("PROFILE_EMAIL", "");
        profileID = userPreferences.getInt("PROFILE_ID", 0);
        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        if(isBundle){
            reportBundle=getIntent().getExtras();

        }
        //getLocationPermission();
        if(reportBundle !=null){
            category=reportBundle.getString("ReportType");

        }
        geocoder = new Geocoder(this, Locale.getDefault());
        cancellationTokenSource = new CancellationTokenSource();
        /*if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }*/
        //mMapView = findViewById(R.id.map);
        //mMapView.onCreate(mapViewBundle);
        //mMapView.getMapAsync(this);
        reportID = ThreadLocalRandom.current().nextInt(1020, 103210);
        spnTypeOfEmerg = findViewById(R.id.emerg_type_spn_);
        spnSafe = findViewById(R.id.safe_reporter_spn);

        txtLocAddress = findViewById(R.id.loAddress);
        txtLat = findViewById(R.id.telle_latT);
        txtLng = findViewById(R.id.teller_lng);
        txtLocTittle = findViewById(R.id.tittleLocU);
        fabPlacePicker = findViewById(R.id.ic_emerg_fab);
        fabEmerg = findViewById(R.id.fab_Emergency);
        btnSubmitReport = findViewById(R.id.buttonTellerLoc);
        fabPlacePicker.setOnClickListener(this);
        fabEmerg.setOnClickListener(this);

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

                                /*Intent shop = new Intent(UserReportEmergAct.this, CustomerHelpActTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);*/


                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });



        btnSubmitReport.setOnClickListener(this);
        //homeFab.setEnabled(false);
        fabPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("Profile", userProfile);
                bundle.putInt("EMERGENCY_LOCID", reportID);
                Intent intent = new Intent(UserReportEmergAct.this, GoogleMapAct.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mGetReportLoc.launch(intent);

            }
        });
        if(category !=null){
            if(category.equalsIgnoreCase("Environmental Issues")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(UserReportEmergAct.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.EnvironmentalReport));

                myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnTypeOfEmerg.setAdapter(myAdaptor);

            }
            if(category.equalsIgnoreCase("Crimes")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(UserReportEmergAct.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.CrimesReport));

                myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnTypeOfEmerg.setAdapter(myAdaptor);

            }
            if(category.equalsIgnoreCase("Emergencies")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(UserReportEmergAct.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.EmergencyReport));

                myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnTypeOfEmerg.setAdapter(myAdaptor);

            }
            if(category.equalsIgnoreCase("Climate Change")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(UserReportEmergAct.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.Climate_change));

                myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnTypeOfEmerg.setAdapter(myAdaptor);

            }

        }


        spnTypeOfEmerg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(index==position){
                    fabPlacePicker.setEnabled(false);
                    return;
                }else {
                    fabPlacePicker.setEnabled(true);
                    selectedType = spnTypeOfEmerg.getSelectedItem().toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(selectedType !=null){
            if(selectedType.equalsIgnoreCase("Oil Spillage")){
                Intent myIntent = new Intent(UserReportEmergAct.this, NewOSReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);

            }

        }
        spnSafe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(safeIndex==position){
                    return;
                }else{
                    safeOption = spnSafe.getSelectedItem().toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:SS", Locale.getDefault());

        dateOfToday = dateFormat.format(calendar.getTime());

        checkInternetConnection();
        String title = "Awajima Reports, Response";
        String body = "Emergency Reports received, and Tracking in Progress";
        if(placeLatLng !=null){
            try {
                placeLatLngStrng= String.valueOf(placeLatLng);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //btnSubmitReport.setEnabled(false);

        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < emergencyReports.size(); i++) {
                    try {
                        if (emergencyReports.get(i).getEmergRType().equalsIgnoreCase(selectedType) && emergencyReports.get(i).getEmergRGroup().equalsIgnoreCase(category)&& emergencyReports.get(i).getEmergRTime().equalsIgnoreCase(dateOfToday)) {
                            Toast.makeText(UserReportEmergAct.this, "A similar Emergency Report was submitted before" , Toast.LENGTH_LONG).show();
                            return;

                        }else {
                            if (placeLatLng != null) {
                                doIt=true;
                                emergencyReport = new EmergencyReport(reportID, profileID, bizID, dateOfToday, category,selectedType, stringLatLng, locality, bgAddress, address, country);


                            }

                            if(latLng !=null){
                                latitude=latLng.latitude;
                                longitude=latLng.longitude;
                            }
                            if (emergencyReport !=null) {
                                emergencyReport.setEmerGPlace(placeLoc);
                                if(userProfile !=null){
                                    userProfile.addNewEmergReport(emergencyReport);
                                }
                                //createNotification(title, body);
                                //enableLocUpdates(reportID,reportIDF);


                                try {
                                    awajima.addEmergReport(emergencyReport);

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                                if (boolean_permission) {

                                    if (userPreferences.getString("service", "").matches("")) {
                                        medit.putString("service", "service").commit();

                                        try {
                                            //Intent intent = new Intent(UserReportEmergAct.this, TrackerService.class);
                                             //startService(intent);

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }



                                    } else {
                                        Toast.makeText(UserReportEmergAct.this, "Service is already running", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(UserReportEmergAct.this, "Please enable the gps", Toast.LENGTH_SHORT).show();
                                }

                                Toast.makeText(UserReportEmergAct.this, "Your Emergency Report has been received ", Toast.LENGTH_SHORT).show();

                                showSnackbar(R.string.permission_terms, R.string.tracking,
                                        new View.OnClickListener() {
                                            @SuppressLint("UnspecifiedImmutableFlag")
                                            @Override
                                            public void onClick(View view) {
                                                try {
                                                    Intent intent = new Intent();
                                    //intent = new Intent(UserReportEmergAct.this, LocUpdatesBReceiver.class);
                                    //intent.setAction(LocUpdatesBReceiver.ACTION_PROCESS_UPDATES);
                                    //PendingIntent.getBroadcast(UserReportEmergAct.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    //startActivity(intent);

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        });

                                if(emergReportDAO !=null){
                                    if(registerReport){
                                        try {
                                            try {
                                                reportIDF = emergReportDAO.insertUserEmergencyReport(reportID, profileID, bizID, dateOfToday, selectedType, stringLatLng,placeLatLngStrng, locality, bgAddress, address, country,safeOption);



                                            } catch (SQLiteException e) {
                                                e.printStackTrace();
                                            }


                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        }


                                    }

                                }
                                if(reportIDF>0){
                                    //addGeofence(selectedType, latitude,longitude,emergencyReport);
                                    registerReport= false;
                                    bundle.putParcelable("Profile", userProfile);
                                    Intent intent = new Intent(UserReportEmergAct.this, LoginDirAct.class);
                                    intent.putExtras(bundle);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                }


                            }

                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }

                }


                //addGeofence(selectedType,latitude,longitude,emergencyReport);



            }
        });


    }

    public boolean hasForegroundLocationPermission () {
        return hasCoarseLocationPermission || hasFineLocationPermission; }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_emerg_fab:
                doMapPick();


                break;
            case R.id.fab_Emergency:
                doHotEmergReport ();

                break;

            /*case R.id.buttonTellerLoc:
                doSubmitReport ();*/
        }


    }
    private void doMapPick () {
        if(bundle !=null){
            doIt=true;
            bundle.putParcelable("Profile", userProfile);
            bundle.putInt("EMERG_REPORT_ID", this.reportID);
            bundle.putString("selectedType", this.selectedType);
            bundle.putParcelable("EmergencyReport",this.emergencyReport);
            Intent intent = new Intent(UserReportEmergAct.this, GoogleMapAct.class);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mGetReportLoc.launch(intent);

        }


    }


    private void doHotEmergReport () {
        gson1= new Gson();
        userProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefProfileID = userPreferences.getInt("PROFILE_ID", 0);
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);

        bundle= new Bundle();
        bundle.putParcelable("Profile", userProfile);
        bundle.putInt("EMERG_REPORT_ID", this.reportID);
        doIt=true;
        Intent intent = new Intent(UserReportEmergAct.this, GoogleMapAct.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
        finish();

    }
    public boolean isGeofencingInitialised () { return geofencingClient != null; }


    @SuppressLint("MissingPermission")
    private void addGeofence(String selectedType, double latitude, double longitude, EmergencyReport emergencyReport) {
        Fence fence2 = Fence.buildCircularFence(selectedType, new LatLng(latitude, longitude), 100f);

        CircularFence circularFence = (CircularFence) CircularFence.buildCircularFence(selectedType, placeLatLng,100f);

        emergencyReport.addFence(fence2);
        emergencyReport.addCircularFence(circularFence);
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient (this);
        }

        if (isHasBackgroundLocationPermission ()) {
            if (! isGeofencingInitialised ()) {
                geofencingClient = LocationServices.getGeofencingClient (this);
                Intent intent = new Intent (this, GeofenceBroadcastReceiver.class);
                pendingIntent =
                        PendingIntent.getBroadcast (this, 0, intent,
                                PendingIntent.FLAG_CANCEL_CURRENT);
            }
        }
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder ()
                .setInitialTrigger (INITIAL_TRIGGER_DWELL | INITIAL_TRIGGER_ENTER |
                        INITIAL_TRIGGER_EXIT);
        if(builder !=null){
            builder.addGeofence (fence2.createGeofence ());
        }

        for (Fence fence : fences) {
            builder.addGeofence (fence2.createGeofence ());
        }
        geofencingClient.addGeofences (builder.build (), pendingIntent)
                .addOnSuccessListener (this, new OnSuccessListener<Void> ()
                {
                    @Override
                    public void onSuccess (Void aVoid)
                    {
                        handleAddingFenceSucceeded ();
                    }
                })
                .addOnFailureListener (this, new OnFailureListener()
                {
                    @Override
                    public void onFailure (@NotNull Exception e)
                    {
                        handleAddingFenceFailed (e);
                    }
                });
    }

    private boolean isHasBackgroundLocationPermission() {
        return false;
    }

    @SuppressLint("MissingPermission")
    public Task<Void> requestSingleLocation (LocationCallback callback)
    {
        LocationRequest request = LocationRequest.create ()
                .setNumUpdates (4)
                .setExpirationDuration (50000)   // Give up if no location in 5 seconds
                .setPriority (LocationRequest.PRIORITY_HIGH_ACCURACY);
        // return fusedLocationClient.requestLocationUpdates (request, pendingIntent);
        return fusedLocationClient.requestLocationUpdates (request, callback, getMainLooper ());
    }
    String getStatusText ()
    {
        //Log.v (Constants.LOGTAG, CLASSTAG + "Requesting status text for status: " + status);
        int resId;
        switch (status)
        {
            case DEFAULT:
                resId = R.string.status_default;
                break;
            case FENCES_ADDED:
                resId = R.string.status_fences_added;
                break;
            case FENCES_FAILED:
                resId = R.string.status_fences_failed;
                break;
            case FENCES_REMOVED:
                resId = R.string.status_fences_removed;
                break;
            default:
                resId = R.string.status_unknown;
        }
        String result = getResources ().getString (resId);
        //Log.v (Constants.LOGTAG, CLASSTAG + "Returning status text: " + result);
        return result;
    }

    private void handleAddingFenceFailed (Exception e)
    {
        String logMsg = CLASSTAG + "Adding fence failed: " + e.getLocalizedMessage ();
        //Log.w (Constants.LOGTAG, logMsg, e);
        String msg;
        Resources res = getResources ();
        if (e instanceof ApiException)
        {
            int code = ((ApiException) e).getStatusCode ();
            switch (code)
            {
                case 1004:  // GeofenceStatusCodes.GEOFENCE_INSUFFICIENT_LOCATION_PERMISSION
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_insufficient_location_permission) );
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_not_available) );
                    break;
                case 1005: //GeofenceStatusCodes.GEOFENCE_REQUEST_TOO_FREQUENT
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_request_too_frequent) );
                    break;
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_too_many_geofences) );
                    break;
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_too_many_pending_intents) );
                    break;
                default:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            e.getLocalizedMessage () );
            }
        } else
        {
            msg = res.getString ( R.string.failed_with_unexpected_error,
                    e.getLocalizedMessage () );
        }
        //createAddingFenceFailedDialog (msg);
        //addingFenceFailedDialog.show ();
    }

    private void handleAddingFenceSucceeded () {
        //Log.i (Constants.LOGTAG, CLASSTAG + "Fence added.");
        status = AppController.Status.FENCES_ADDED;
        //DisplayLocationActivity.getHandler ().handleMessage (new Message());
    }
    private void handleCloseAddingFenceFailedDialog () {
        //Log.v (Constants.LOGTAG, CLASSTAG + "handleCloseAddingFenceFailedDialog called");
        addingFenceFailedDialog = null;
        Activity activity = this;
        //dialogActivity = null;
        status = AppController.Status.FENCES_FAILED;
        // Force a screen refresh on DisplayLocationActivity
        //DisplayLocationActivity.getHandler ().handleMessage (new Message ());
    }
    private void createAddingFenceFailedDialog (String msg) {
        //Log.v (Constants.LOGTAG, CLASSTAG + ">createAddingFenceFailedDialog");
        addingFenceFailedDialog = new AlertDialog.Builder (this)
                .setTitle (R.string.adding_fence_failed_title)
                .setMessage (msg)
                .setPositiveButton (R.string.ok, new DialogInterface.OnClickListener ()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                        handleCloseAddingFenceFailedDialog ();
                    }
                })
                .create ();
    }

    private void initializeAllMapStuff(Bundle savedInstanceState) {
        createLocationRequest();
        Places.initialize(this, BuildConfig.MAP_API);
        placesClient = Places.createClient(this);
        if (savedInstanceState != null) {
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
        locationRequest = LocationRequest.create();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        /*if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastLocation);
        }
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }*/

        //mMapView.onSaveInstanceState(mapViewBundle);
        super.onSaveInstanceState(outState);
    }

    private void addCircle(GoogleMap map,double latitude,double longitude) {
        if(latitude==0){
            if(longitude==0){
                latitude = 4.8359;
                longitude = 7.0139;
                center = new LatLng(latitude, longitude);

            }

        }else{
            if(latLng !=null){
                latitude=latLng.latitude;
                longitude=latLng.longitude;
            }
            center = new LatLng(latitude, longitude);

        }

        double radius = ONE_THOUSAND_KM;
        int fillColor = 0x6600ff99;
        int strokeColor = 0xaa00ff99;
        float strokeWidth = getResources().getDimension(R.dimen.circle_stroke_width);
        CircleOptions options = new CircleOptions().center(center).radius(radius).fillColor(fillColor).strokeColor(strokeColor).strokeWidth(strokeWidth)
                .zIndex(1.0f);

        circle = map.addCircle(options);
    }

    private void addPolygon(GoogleMap map, double latitude, double longitude) {
        int fillColor = 0x44ffffff;
        int strokeColor = 0x88ffffff;
        float strokeWidth = getResources().getDimension(R.dimen.polygon_stroke_width);
        PolygonOptions options = new PolygonOptions().fillColor(fillColor).strokeColor(strokeColor).strokeWidth(strokeWidth).zIndex(0.0f);

        //options.add(new LatLng(72.0245, 34.4512));
        //options.add(new LatLng(68.3408, 44.1491));
        //options.add(new LatLng(48.6029, 45.2666));
        //options.add(new LatLng(43.4643, 34.3700));
        //options.add(new LatLng(39.4653, 25.6528));
        if(latitude==0){
            if(longitude==0){
                latitude = 4.8359;
                longitude = 7.0139;
                center = new LatLng(latitude, longitude);

            }

        }else{
            if(latLng !=null){
                latitude=latLng.latitude;
                longitude=latLng.longitude;
            }
            center = new LatLng(latitude, longitude);

        }

        options.add(new LatLng(latitude, longitude));
        options.add(new LatLng(4.8403502, 7.0371499));
        options.add(new LatLng(4.8065452, 7.0478073));
        options.add(new LatLng(4.526685, 7.4462486));

        List<LatLng> hole = new ArrayList<LatLng>();
        //hole.add(new LatLng(54.3062, 13.1940));
        //hole.add(new LatLng(55.5966, 19.3098));
        //hole.add(new LatLng(54.8401, 24.0624));
        //hole.add(new LatLng(52.3033, 24.9097));
        //hole.add(new LatLng(50.2762, 25.2413));
        //hole.add(new LatLng(48.5262, 23.2150));
        //hole.add(new LatLng(48.5750, 18.5729));
        //hole.add(new LatLng(50.7447, 13.5256));
        options.addHole(hole);

        polygon = map.addPolygon(options);
    }

    private void addPolyline(GoogleMap map) {
        int color = 0xaa0000ff;
        float width = getResources().getDimension(R.dimen.polyline_width);
        PolylineOptions options = new PolylineOptions().color(color).width(width).zIndex(2.0f);

        polyline = map.addPolyline(options);
    }

    private void addGroundOverlay(GoogleMap map, double latitude, double longitude) {
        if(latitude==0){
            if(longitude==0){
                latitude = 4.8359;
                longitude = 7.0139;
                center = new LatLng(latitude, longitude);

            }

        }else{
            if(latLng !=null){
                latitude=latLng.latitude;
                longitude=latLng.longitude;
            }
            center = new LatLng(latitude, longitude);

        }
        LatLng position = new LatLng(latitude, longitude);
        int width = 1000000;
        BitmapDescriptor image = BitmapDescriptorFactory.fromResource(R.drawable.ic_admin_panel);
        GroundOverlayOptions options = new GroundOverlayOptions().position(position, width).image(image).zIndex(3.0f);

        groundOverlay = map.addGroundOverlay(options);

        //position = new LatLng(27.6035, 18.1508);
        width = 1500000;
        options = new GroundOverlayOptions().position(position, width).image(image).bearing(180.0f).transparency(0.5f).zIndex(3.0f);

        map.addGroundOverlay(options);
    }


    public void onExpandCircleClick(View view) {
        if (circle.getRadius() < 7 * ONE_THOUSAND_KM) {
            circle.setRadius(circle.getRadius() + ONE_THOUSAND_KM);
        }
    }

    public void onRemoveHoleClick(View view) {
        polygon.setHoles(new ArrayList<List<LatLng>>());
        view.setEnabled(false);
    }

    public void onGeodesicClick(View view) {
        polyline.setGeodesic(!polyline.isGeodesic());
    }

    private void animateCamera(@NonNull Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    private void showMarker(@NonNull Location currentLocation) {
        latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
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
        woosmap = Woosmap.getInstance().initializeWoosmap(this);
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
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                if(latLng !=null){
                    stringLatLng = String.valueOf(latLng);

                }
                try {

                    if(emergReportNextDAO !=null){
                        emergReportNextDAO.insertNewEmergNextLoc(reportTrackingID,reportID,dateOfToday,stringLatLng,reportIDF);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        });

        OtherPref.modeHighFrequencyLocation = !OtherPref.modeHighFrequencyLocation;
        String msg = "";
        if (OtherPref.modeHighFrequencyLocation) {
            msg = "Mode High Frequency Location Enable";
            editor.putBoolean("modeHighFrequencyLocationEnable", true);
            editor.apply();
        } else {
            msg = "Mode High Frequency Location disable";
            editor.putBoolean("modeHighFrequencyLocationEnable", false);
            editor.apply();
        }
        woosmap.enableModeHighFrequencyLocation(OtherPref.modeHighFrequencyLocation);
    }

    private PendingIntent getPendingIntent() {
        if (pendingIntent != null) {
            return pendingIntent;
        }else{
            bundle= new Bundle();
            Intent intent = new Intent(this, LocUpdatesBReceiver.class);
            intent.setAction(LocUpdatesBReceiver.ACTION_PROCESS_UPDATES);
            bundle.putParcelable("Profile", this.userProfile);
            bundle.putInt("EMERG_REPORT_ID", this.reportID);
            intent.putExtras(bundle);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            return pendingIntent;

        }



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
        //mMapView.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //mMapView.onStop();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        fusedLocationClient.removeLocationUpdates(getPendingIntent());

        if (fusedLocationClient != null)
            fusedLocationClient.removeLocationUpdates(mLocationCallback);
    }


    @Override
    public void onPause() {
        //mMapView.onPause();
        super.onPause();
        unregisterReceiver(broadcastReceiver);
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        fusedLocationClient.removeLocationUpdates(getPendingIntent());
        handler.removeCallbacks(rotateThingsRunnable);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //mMapView.onLowMemory();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_get_place) {
            showCurrentPlace();
        }
        return true;
    }
    private void showCurrentPlace() {
        /*if (mMap == null) {
            return;
        }*/

        if (locationPermissionGranted) {
            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                    Place.Field.LAT_LNG);

            FindCurrentPlaceRequest request =
                    FindCurrentPlaceRequest.newInstance(placeFields);

            @SuppressWarnings("MissingPermission") final
            Task<FindCurrentPlaceResponse> placeResult =
                    placesClient.findCurrentPlace(request);
            placeResult.addOnCompleteListener (new OnCompleteListener<FindCurrentPlaceResponse>() {
                @Override
                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        FindCurrentPlaceResponse likelyPlaces = task.getResult();

                        // Set the count, handling cases where less than 5 entries are returned.
                        int count;
                        if (likelyPlaces.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
                            count = likelyPlaces.getPlaceLikelihoods().size();
                        } else {
                            count = M_MAX_ENTRIES;
                        }

                        int i = 0;
                        likelyPlaceNames = new String[count];
                        likelyPlaceAddresses = new String[count];
                        likelyPlaceAttributions = new List[count];
                        likelyPlaceLatLngs = new LatLng[count];

                        for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
                            likelyPlaceNames[i] = placeLikelihood.getPlace().getName();
                            likelyPlaceAddresses[i] = placeLikelihood.getPlace().getAddress();
                            likelyPlaceAttributions[i] = placeLikelihood.getPlace()
                                    .getAttributions();
                            likelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

                            i++;
                            if (i > (count - 1)) {
                                break;
                            }
                        }

                        //openPlacesDialog();
                    }
                    else {
                        Log.e(TAG, "Exception: %s", task.getException());
                    }
                }
            });
        } else {
            // The user has not granted permission.
            Log.i(TAG, "The user did not grant location permission.");

            /*mMap.addMarker(new MarkerOptions()
                    .title("My Location")
                    .position(defaultLocation)
                    .snippet(getString(R.string.default_info_snippet)));*/

            requestPermissions();
        }
    }

    private void openPlacesDialog() {
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LatLng markerLatLng = likelyPlaceLatLngs[which];
                String markerSnippet = likelyPlaceAddresses[which];
                if (likelyPlaceAttributions[which] != null) {
                    markerSnippet = markerSnippet + "\n" + likelyPlaceAttributions[which];
                }
                /*if(mMap !=null){
                    mMap.addMarker(new MarkerOptions()
                            .title(likelyPlaceNames[which])
                            .position(markerLatLng)
                            .snippet(markerSnippet));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
                            19));

                }*/

            }
        };


        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.pick_place)
                .setItems(likelyPlaceNames, listener)
                .show();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        /*mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Emerg);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            mapView = mapFrag.getView();
        }*/
    }

    @Override
    public void onConnected(Bundle bundle) {
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_Emerg);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }*/
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
        /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_Emerg);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }*/

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        locationPermissionGranted = false;

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        //setupLocationManager();
                        locationPermissionGranted = true;
                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }

                        this.mMap.setMyLocationEnabled(true);

                    }
                } else {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

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
                    /*mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Emerg);

                    if (mapFrag != null) {
                        mapFrag.getMapAsync(this);
                        mapView = mapFrag.getView();
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
                                        stringLatLng = String.valueOf(latitude + "," + longitude);

                                    }
                                    if (mMap != null) {
                                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                        //googleMapView.getUiSettings().setZoomControlsEnabled(true);
                                        //googleMapView.getUiSettings().setZoomGesturesEnabled(true);
                                        //googleMapView.getUiSettings().setCompassEnabled(true);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20.0f));
                                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
                                        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(userLocation, 20);
                                        now = mMap.addMarker(new MarkerOptions().position(userLocation)
                                                .title("Your Location"));
                                        mMap.animateCamera(update);
                                        mMap.moveCamera(zoom);
                                        mMap.animateCamera(zoom);

                                    }

                                    placeLatLng = new LatLng(latitude, longitude);
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
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
        updateLocationUI();
        stringLatLng = String.valueOf(latitude + "," + longitude);


        //updateLocationUI();

        placeLatLng = new LatLng(latitude, longitude);
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
                                    placeLatLng = new LatLng(latitude, longitude);
                                    stringLatLng = String.valueOf(latitude + "," + longitude);
                                    if(mMap !=null){
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                                new LatLng(mCurrentLocation.getLatitude(),
                                                        mCurrentLocation.getLongitude()), 20));

                                    }


                                } else {
                                    Log.d(TAG, "Current location is null. Using Last Location.");
                                    getPreviousLoc(finalLatitute, finalLongitute);
                                }

                            }

                        });
                dest = placeLatLng;
                latitute1 = latitude;
                longitute1 = longitude;
                setResult(Activity.RESULT_OK, new Intent());
                if(mMap !=null){
                    mMap.addMarker(new MarkerOptions().position(new LatLng(latitute1, longitute1))).setTitle("Where you are");
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(dest);
                    markerOptions.title("You are here");
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitute1, longitute1), 20));
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                }




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
        /*mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Emerg);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            mapView = mapFrag.getView();
        }*/
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
                        placeLatLng = new LatLng(latitude, longitude);
                        stringLatLng = String.valueOf(latitude + "," + longitude);
                        placeLatLng = new LatLng(latitude, longitude);
                        dest = placeLatLng;
                        finalLatitute = latitude;
                        finalLongitute = longitude;
                        txtLat.setText("Your Lat:" + latitude);
                        txtLng.setText("Your Lng:" + longitude);
                        if(mMap !=null){
                            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            mMap.getUiSettings().setZoomControlsEnabled(true);
                            mMap.getUiSettings().setZoomGesturesEnabled(true);
                            mMap.getUiSettings().setCompassEnabled(true);
                            mMap.addMarker(new MarkerOptions().position(new LatLng(finalLatitute, finalLongitute))).setTitle("Where you were");

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(finalLatitute, finalLongitute), 20));
                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
                            mMap.moveCamera(zoom);
                            mMap.animateCamera(zoom);
                        }

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(placeLatLng);
                        markerOptions.title("You are here");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                } else {
                    latitude = 4.8359;
                    longitude = 7.0139;

                    placeLatLng = new LatLng(4.52871, 7.44507);
                    Log.d(TAG, "Current location is null. Using defaults.");

                    if(mMap !=null){
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        mMap.getUiSettings().setZoomGesturesEnabled(true);
                        mMap.getUiSettings().setCompassEnabled(true);
                        mMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(placeLatLng, 17));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(19);
                        mMap.moveCamera(zoom);
                        mMap.animateCamera(zoom);
                    }

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
        getDeviceLocation();
        geocoder = new Geocoder(this, Locale.getDefault());
        googleApiClient.connect();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        geocoder = new Geocoder(this, Locale.getDefault());
        createLocationRequest();
        /*mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Emerg);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            mapView = mapFrag.getView();
        }*/
        placeLatLng = new LatLng(4.52871, 7.44507);
        if(mMap !=null){
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(4.52871, 7.44507), 20.0f));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(17);
            mMap.moveCamera(zoom);
            mMap.animateCamera(zoom);

        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());


    }

    private void getDeviceLocation() {

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
                    mMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(userLocation, 17));
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);

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
        handler.post(rotateThingsRunnable);

        LocationManager service = (LocationManager) getSystemService( LOCATION_SERVICE );
        boolean enabled = service.isProviderEnabled( LocationManager.GPS_PROVIDER );

        if (!enabled) {
            buildAlertMessageNoGps();
        }
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        if(enabled){
            //mMap.animateCamera( update );
/*
            Toast.makeText( MainActivity.this, "OnResume:"+latitude+","+longitude, Toast.LENGTH_SHORT ).show();
*/



        }
        registerReceiver(broadcastReceiver, new IntentFilter(TrackerService.str_receiver));


    }


    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {
        mLastLocation = location;
        txtLocAddress = findViewById(R.id.loAddress);
        txtLat = findViewById(R.id.telle_latT);
        txtLng = findViewById(R.id.teller_lng);
        txtLocTittle = findViewById(R.id.tittleLocU);
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
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
                            stringLatLng=String.valueOf(latitude + "," + longitude);

                        }
                        if(mMap !=null){
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            //googleMapView.getUiSettings().setZoomControlsEnabled(true);
                            //googleMapView.getUiSettings().setZoomGesturesEnabled(true);
                            //googleMapView.getUiSettings().setCompassEnabled(true);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20.0f));
                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(20);
                            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(userLocation, 20);
                            now = mMap.addMarker(new MarkerOptions().position(userLocation)
                                    .title("Your Location"));
                            mMap.animateCamera(update);
                            mMap.moveCamera(zoom);
                            mMap.animateCamera(zoom);

                        }

                        placeLatLng = new LatLng(latitude, longitude);
                        txtLat.setText("Your Lat:"+latitude);
                        txtLng.setText("Your Lng:"+longitude);

                    }
                });

        /*LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        Bundle bundle=new Bundle();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));*/
        emergencyReport = new EmergencyReport(reportID, profileID,bizID, dateOfToday, selectedType,stringLatLng,locality, bgAddress,address,country);

        //mCurrLocationMarker = map.addMarker(markerOptions);
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
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(20));

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

            intent = new Intent(this, GeofenceAct.class);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            latitude = Double.parseDouble(intent.getStringExtra("latitude"));
            longitude = Double.parseDouble(intent.getStringExtra("longitude"));

            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);

                //tv_area.setText(addresses.get(0).getAdminArea());
                //tv_locality.setText(stateName);
                //tv_address.setText(countryName);



            } catch (IOException e1) {
                e1.printStackTrace();
            }


            //tv_latitude.setText(latitude+"");
            //tv_longitude.setText(longitude+"");
            //tv_address.getText();


        }
    };


    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
    private void updateLocationUI() {

        try {
            if (locationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
        /*if (mMap == null) {
            return;
        }
        */
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}