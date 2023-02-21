package com.skylightapp.MapAndLoc;

import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_DWELL;
import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_ENTER;
import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_EXIT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
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
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AwajimaReportAct extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener , View.OnClickListener{
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
    private static final String TAG = AwajimaReportAct.class.getSimpleName();
    private CameraPosition cameraPosition;
    private int index=0;
    private PendingIntent pendingIntent;
    private Bundle reportBundle;
    private int safeIndex=0;
    private boolean doIt;
    private AppCompatSpinner spnCategory;
    ArrayList<Fence> fences;
    private ArrayList<EmergencyReport> emergencyReports;

    private static final String CLASSTAG =
            " " + AwajimaReportAct.class.getSimpleName () + " ";
    private final LatLng defaultLocation = new LatLng(4.8359, 7.0139);
    private int catIndex=0;
    private String placeLatLngStrng, category,userPhoneNO,userEmailAddress;
    private  boolean isBundle=false;
    private GroundOverlay groundOverlay;
    private Handler handler = new Handler();
    private long startTime = SystemClock.uptimeMillis();
    boolean boolean_permission;
    private GeofencingClient geofencingClient;
    CopyOnWriteArrayList<FenceEvent> events;
    private boolean hasBackgroundLocationPermission;
    private boolean hasCoarseLocationPermission;
    private boolean hasFineLocationPermission;
    private MapView mMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private AppController.Status status;
    Bundle mapViewBundle = null;

    private AlertDialog addingFenceFailedDialog;
    boolean addGeoF=false;

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
                                    placeLoc = PlacePicker.getPlace(AwajimaReportAct.this, data);

                                }
                                if (placeLoc != null) {
                                    placeLatLng = placeLoc.getLatLng();
                                    placeID = placeLoc.getId();
                                    address = String.valueOf(placeLoc.getAddress());
                                }
                                if(placeLatLng !=null){
                                    placeLatLngStrng= String.valueOf(placeLatLng);
                                }

                                Toast.makeText(AwajimaReportAct.this, "Location registration successful", Toast.LENGTH_SHORT).show();

                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(AwajimaReportAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + result.getResultCode());
                    }
                }

            });

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_awajima_report);
        checkInternetConnection();
        if (!hasPermissions(AwajimaReportAct.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(AwajimaReportAct.this, PERMISSIONS, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
        awajima= new Awajima();

        emergencyReports= new ArrayList<>();
        reportID = ThreadLocalRandom.current().nextInt(1020, 103210);
        fences = new ArrayList<> ();
        reportBundle= new Bundle();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //chipNavigationBar = findViewById(R.id.emerg_Bar);
        spnCategory = findViewById(R.id.re_spn_Cat);
        spnCategory.setOnItemSelectedListener(cat_listener);
        spnTypeOfEmerg = findViewById(R.id.re_type_spn_);
        spnSafe = findViewById(R.id.safe_spn);

        txtLocAddress = findViewById(R.id.your_Address);
        txtLat = findViewById(R.id.your_lat);
        txtLng = findViewById(R.id.your_lng);
        txtLocTittle = findViewById(R.id.t_my_Loc);
        fabPlacePicker = findViewById(R.id.fab_report_fab);
        chipNavigationBar = findViewById(R.id.emerg_Bar);
        //fabEmerg = findViewById(R.id.fab_Emergency);
        btnSubmitReport = findViewById(R.id.buttonSubLoc);
        fabEmerg = findViewById(R.id.fab_Emergency);
        bundle = new Bundle();
        gson1 = new Gson();
        gson2 = new Gson();
        today = new Date();
        dbHelper = new DBHelper(this);
        emergReportDAO = new EmergReportDAO(AwajimaReportAct.this);
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

        reportID = ThreadLocalRandom.current().nextInt(1020, 103210);

        fabPlacePicker.setOnClickListener(this);
        //fabEmerg.setOnClickListener(this);

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
                                Intent myIntent = new Intent(AwajimaReportAct.this, MyEmergReportAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.emerg_report_rewards:

                                Intent chat = new Intent(AwajimaReportAct.this, MyERRewardAct.class);
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

        if(category !=null){
            if(category.equalsIgnoreCase("Environmental Issues")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(AwajimaReportAct.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.EnvironmentalReport));

                myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnTypeOfEmerg.setAdapter(myAdaptor);

            }
            if(category.equalsIgnoreCase("Crimes")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(AwajimaReportAct.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.CrimesReport));

                myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnTypeOfEmerg.setAdapter(myAdaptor);

            }
            if(category.equalsIgnoreCase("Emergencies")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(AwajimaReportAct.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.EmergencyReport));

                myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnTypeOfEmerg.setAdapter(myAdaptor);

            }
            if(category.equalsIgnoreCase("Climate Change")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(AwajimaReportAct.this,
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
                Intent myIntent = new Intent(AwajimaReportAct.this, NewOSReportAct.class);
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
                    fabPlacePicker.setEnabled(false);
                    return;
                }else{
                    fabPlacePicker.setEnabled(true);
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


        String title = "Awajima Reports, Response";
        String body = "Emergency Reports received, and Tracking in Progress";
        if(placeLatLng !=null){
            try {
                placeLatLngStrng= String.valueOf(placeLatLng);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        fabEmerg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bundle !=null){
                    doIt=true;
                    bundle.putParcelable("Profile", userProfile);
                    bundle.putInt("EMERG_REPORT_ID", reportID);
                    bundle.putString("selectedType", selectedType);
                    bundle.putParcelable("EmergencyReport",emergencyReport);
                    Intent intent = new Intent(AwajimaReportAct.this, GoogleMapAct.class);
                    intent.putExtras(bundle);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //mGetReportLoc.launch(intent);

                }

            }
        });
        fabPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putParcelable("Profile", userProfile);
                bundle.putInt("EMERGENCY_LOCID", reportID);
                Intent intent = new Intent(AwajimaReportAct.this, GoogleMapAct.class);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //mGetReportLoc.launch(intent);

            }
        });
        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < emergencyReports.size(); i++) {
                    try {
                        if (emergencyReports.get(i).getEmergRType().equalsIgnoreCase(selectedType) && emergencyReports.get(i).getEmergRGroup().equalsIgnoreCase(category)&& emergencyReports.get(i).getEmergRTime().equalsIgnoreCase(dateOfToday)) {
                            Toast.makeText(AwajimaReportAct.this, "A similar Emergency Report was submitted before" , Toast.LENGTH_LONG).show();
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
                                        Toast.makeText(AwajimaReportAct.this, "Service is already running", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(AwajimaReportAct.this, "Please enable the gps", Toast.LENGTH_SHORT).show();
                                }

                                Toast.makeText(AwajimaReportAct.this, "Your Emergency Report has been received ", Toast.LENGTH_SHORT).show();



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
                                showSnackbar(R.string.permission_terms, R.string.tracking,
                                        new View.OnClickListener() {
                                            @SuppressLint("UnspecifiedImmutableFlag")
                                            @Override
                                            public void onClick(View view) {
                                                registerReport= false;
                                                try {
                                                    Intent intent = new Intent();
                                                    intent = new Intent(AwajimaReportAct.this, LocUpdatesBReceiver.class);
                                                    intent.setAction(LocUpdatesBReceiver.ACTION_PROCESS_UPDATES);
                                                    PendingIntent.getBroadcast(AwajimaReportAct.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        });

                                if(reportIDF>0){
                                    //addGeofence(selectedType, latitude,longitude,emergencyReport);
                                    registerReport= false;
                                    bundle.putParcelable("Profile", userProfile);
                                    Intent intent = new Intent(AwajimaReportAct.this, LoginDirAct.class);
                                    intent.putExtras(bundle);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                }


                            }

                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }

                }

                if(addGeoF){
                    addGeofence(selectedType,latitude,longitude,emergencyReport);

                }






            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (placeLatLng == null) {
                            bundle.putParcelable("Profile", userProfile);
                            bundle.putInt("EMERGENCY_LOCID", reportID);
                            Intent intent = new Intent(AwajimaReportAct.this, GoogleMapAct.class);
                            intent.putExtras(bundle);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mGetReportLoc.launch(intent);

                        }
                    }
                });

            }
        }).start();




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
    public void enableLocUpdates(int reportID, long reportIDF) {
        EmergReportNext emergReportNext = new EmergReportNext();
        woosmap = Woosmap.getInstance().initializeWoosmap(this);
        EmergReportNextDAO emergReportNextDAO= new EmergReportNextDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        reportTrackingID = ThreadLocalRandom.current().nextInt(110, 1213);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:SS", Locale.getDefault());

        dateOfToday = dateFormat.format(calendar.getTime());


        FallbackLocTracker tracker = new FallbackLocTracker(AwajimaReportAct.this);
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
    public void removeLocationUpdates(View view) {
        Log.i(TAG, "Removing location updates");
        Utils.setRequestingLocationUpdates(this, false);
        fusedLocationClient.removeLocationUpdates(getPendingIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {

            if (googleApiClient.isConnected()) {
                googleApiClient.disconnect();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //mMapView.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //mMapView.onStop();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        /*if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        fusedLocationClient.removeLocationUpdates(getPendingIntent());*/

        /*if (fusedLocationClient != null)
            fusedLocationClient.removeLocationUpdates(mLocationCallback);*/
    }


    @Override
    public void onPause() {
        //mMapView.onPause();
        super.onPause();
        //unregisterReceiver(broadcastReceiver);
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
        fusedLocationClient.removeLocationUpdates(getPendingIntent());
        //handler.removeCallbacks(rotateThingsRunnable);
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
            //showCurrentPlace();
        }
        return true;
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
                .addOnSuccessListener (this, new OnSuccessListener<Void>()
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
        //status = AppController.Status.FENCES_ADDED;
        //DisplayLocationActivity.getHandler ().handleMessage (new Message());
    }
    private void handleCloseAddingFenceFailedDialog () {
        //Log.v (Constants.LOGTAG, CLASSTAG + "handleCloseAddingFenceFailedDialog called");
        addingFenceFailedDialog = null;
        Activity activity = this;
        //dialogActivity = null;
        //status = AppController.Status.FENCES_FAILED;
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
        Intent intent = new Intent(AwajimaReportAct.this, GoogleMapAct.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
        finish();

    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
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
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
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

    private PendingIntent getPendingIntent() {
        if (pendingIntent != null) {
            return pendingIntent;
        }else{
            bundle= new Bundle();
            /*Intent intent = new Intent(this, LocUpdatesBReceiver.class);
            intent.setAction(LocUpdatesBReceiver.ACTION_PROCESS_UPDATES);
            bundle.putParcelable("Profile", this.userProfile);
            bundle.putInt("EMERG_REPORT_ID", this.reportID);
            intent.putExtras(bundle);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);*/
            return pendingIntent;

        }



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
                                    AwajimaReportAct.this,
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult()", Integer.toString(resultCode));

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK: {

                    setInitialLocation();

                    Toast.makeText(AwajimaReportAct.this, "Location enabled", Toast.LENGTH_LONG).show();
                    mRequestingLocationUpdates = true;
                    break;
                }
                case Activity.RESULT_CANCELED: {
                    Toast.makeText(AwajimaReportAct.this, "Location not enabled", Toast.LENGTH_LONG).show();
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
        txtLat = findViewById(R.id.your_lat);
        txtLng = findViewById(R.id.your_lng);
        txtLocTittle = findViewById(R.id.t_my_Loc);
        txtLocAddress = findViewById(R.id.your_Address);


        if (ActivityCompat.checkSelfPermission( AwajimaReportAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( AwajimaReportAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                double lat=location.getLatitude();
                double lng=location.getLongitude();

                AwajimaReportAct.this.latitude=lat;
                AwajimaReportAct.this.longitude=lng;
                stringLatLng=String.valueOf(lat + "," + lng);

                try {
                    if(now !=null){
                        now.remove();
                    }


                    //myCurrentloc.setText( ""+latitude );


                } catch (Exception ex) {

                    ex.printStackTrace();
                    Log.e( "MapException", ex.getMessage() );

                }

                try {
                    geocoder = new Geocoder(AwajimaReportAct.this, Locale.ENGLISH);
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
                        Toast.makeText(AwajimaReportAct.this, str,
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
    public void onClick(View view) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
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

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}