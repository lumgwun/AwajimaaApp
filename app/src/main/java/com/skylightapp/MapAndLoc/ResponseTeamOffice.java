package com.skylightapp.MapAndLoc;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
import com.google.android.gms.location.places.Place;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.Database.EmergResonseDAO;
import com.skylightapp.GroupSavingsTab;
import com.skylightapp.LoginDirAct;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.MyTimelineAct;
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

public class ResponseTeamOffice extends FragmentActivity implements OnMapReadyCallback, com.google.android.gms.location.LocationListener, GoogleMap.OnMarkerClickListener, SharedPreferences.OnSharedPreferenceChangeListener, EmergencyReportAdapter.OnEmergClickListener, EmergResAdapter.OnResponseClickListener {
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
    String SharedPrefUserName;
    String SharedPrefProfileID;
    private long profileID, customerID, reportID;
    private Customer customer;
    private LatLng latlong;
    private Bundle bundle;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson, gson1;
    private String json, json1, SharedPrefSuperUser;
    private MapPanoramaStVAct.LongPressLocationSource mLocationSource;
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
    AppCompatTextView txtCity, txtLat, txtLng, txtCountResponse, txtReportC;
    private Marker mCurrLocationMarker;
    private LocationRequest locationRequest;

    private FusedLocationProviderClient mFusedLocationClient;
    private CancellationTokenSource cancellationTokenSource;
    private static final String PREF_NAME = "awajima";
    private ChipNavigationBar chipNavigationBar;
    private FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    CarouselLayoutManager layoutManager, layoutResponse;
    private EmergResAdapter emergResp;
    private EmergencyReportAdapter emergReportA;
    private ArrayList<EmergencyReport> emergReportList;
    private ArrayList<EmergResponse> emergResponseList;
    private RecyclerView recyclerReport;
    private SQLiteDatabase sqLiteDatabase;
    GoogleMap googleMapView;
    Marker now;
    private String state,cateogory;
    private EmergReportDAO emergDAO;
    private EmergResonseDAO emergResDAO;
    SharedPreferences.Editor medit;
    Bundle bundleIntent;
    private String signature;
    private PlaceData placeData;
    private Place place;
    private ArrayList<EmergencyReport> moreEmergReports;
    private EmergencyReportAdapter moreEmergReportA;

    public void doLocationSettings(View view) {
        requestPermissions();
    }

    public void shareLocation(View view) {
    }
    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            if(intent !=null){
                                bundleIntent = intent.getExtras();

                            }
                            if(bundleIntent !=null){
                                cateogory=bundleIntent.getString("EMERGENCY_REPORT_GROUP");
                                state=bundleIntent.getString("EMERGENCY_REPORT_STATE");
                                signature=bundleIntent.getString("signature");

                            }

                            medit.putString("EMERGENCY_REPORT_GROUP", cateogory);
                            medit.putString("signature", signature);
                            medit.putString("EMERGENCY_REPORT_STATE", state).commit();
                            Toast.makeText(ResponseTeamOffice.this, "Preference returned ok", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(ResponseTeamOffice.this, "Preference canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

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
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {

            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        Log.d(TAG, "location update " + location);
                    }
                }
            }, null);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_map_test);
        setTitle("Emergency,Crimes & Env. Issues");
        dbHelper = new DBHelper(this);
        bundleIntent= new Bundle();
        placeData= new PlaceData();
        chipNavigationBar = findViewById(R.id.gsavings_nav_bar);
        floatingActionButton = findViewById(R.id.resp_fab_ty);
        txtCountResponse = findViewById(R.id.txt_responseD);
        txtReportC = findViewById(R.id.txt_emerg_RT);
        txtLat = findViewById(R.id.latT_emergLat);
        txtLng = findViewById(R.id.lngText_emerg);
        txtCity = findViewById(R.id.locUserAddress22);
        emergReportList = new ArrayList<>();
        emergResponseList = new ArrayList<>();
        moreEmergReports= new ArrayList<>();
        emergDAO = new EmergReportDAO(this);
        emergResDAO = new EmergResonseDAO(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_ResposeA);
        recyclerReport = (RecyclerView) findViewById(R.id.re_Reports_Emer);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        ran = new Random();
        gson1 = new Gson();
        random = new SecureRandom();
        userProfile = new Profile();
        addresses = new ArrayList<>();
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefProfileID = userPreferences.getString("PROFILE_ID", "");
        json1 = userPreferences.getString("LastProfileUsed", "");
        cateogory = userPreferences.getString("EMERGENCY_REPORT_GROUP", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        state = userPreferences.getString("EMERGENCY_REPORT_STATE", "");
        signature = userPreferences.getString("signature", "");

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map234);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (userPreferences.getString("EMERGENCY_REPORT_STATE", "").matches("")) {

            Intent intent = new Intent(ResponseTeamOffice.this, RespTeamPrefAct.class);
            startForResult.launch(intent);

        }if (userPreferences.getString("EMERGENCY_REPORT_GROUP", "").matches("")) {

            Intent intent = new Intent(ResponseTeamOffice.this, RespTeamPrefAct.class);
            startForResult.launch(intent);

        } else {
            medit.putString("EMERGENCY_REPORT_GROUP", cateogory);
            medit.putString("EMERGENCY_REPORT_STATE", state).commit();
            Toast.makeText(ResponseTeamOffice.this, "Service is already running", Toast.LENGTH_SHORT).show();
        }
        if(cateogory !=null){
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                sqLiteDatabase = dbHelper.getReadableDatabase();
                emergReportList = emergDAO.getEmergencyReportByType(cateogory);
            }
            if(cateogory.contains("Awajima")){
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    emergReportList = emergDAO.getAllEmergencyReports();
                }
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    emergResponseList = emergResDAO.getAllEmergResponses();
                }

            }
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                sqLiteDatabase = dbHelper.getReadableDatabase();
                emergReportList = emergDAO.getEmergencyReportByType(cateogory);
            }

        }
        if(state !=null){
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                sqLiteDatabase = dbHelper.getReadableDatabase();
                emergResponseList = emergResDAO.getEmergResponseForState(state);
            }

            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                sqLiteDatabase = dbHelper.getReadableDatabase();
                emergReportList = emergDAO.getEmergencyReportForState(state);
            }
            if(state.equalsIgnoreCase("All")){
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    emergReportList = emergDAO.getAllEmergencyReports();
                }
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    emergResponseList = emergResDAO.getAllEmergResponses();
                }

            }

        }
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show();
            finish();
        }

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startTrackerService();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }




        locationRequest = LocationRequest.create();
        userLocation = new LatLng(latitude, longitude);
        cancellationTokenSource = new CancellationTokenSource();
        geocoder = new Geocoder(this, Locale.getDefault());
        emergReportA = new EmergencyReportAdapter(ResponseTeamOffice.this, emergReportList);


        emergResp = new EmergResAdapter(ResponseTeamOffice.this, emergResponseList);


        LinearLayoutManager layoutManagerC
                = new LinearLayoutManager(ResponseTeamOffice.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerReport.setLayoutManager(layoutManagerC);

        recyclerReport.setAdapter(emergReportA);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerReport.getContext(),
                layoutManagerC.getOrientation());
        recyclerReport.addItemDecoration(dividerItemDecoration7);


        layoutResponse = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        recyclerView.setLayoutManager(layoutResponse);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutResponse.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.setAdapter(emergResp);
        recyclerView.setClickable(true);


        recyclerView.addOnItemTouchListener(new MyTouchListener(this,
                recyclerView,
                new MyTouchListener.OnTouchActionListener() {
                    @Override
                    public void onLeftSwipe(View view, int position) {//code as per your need
                    }

                    @Override
                    public void onRightSwipe(View view, int position) {//code as per your need
                    }

                    @Override
                    public void onClick(View view, int position) {//code as per your need
                    }
                }));

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
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                responseHome();

            }
        });

        customer = new Customer();
        userLocBundle = new Bundle();
        customerBundle = getIntent().getExtras();
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        if (customerBundle != null) {
            customer = customerBundle.getParcelable("Customer");

        } else {
            if (userProfile != null) {
                customer = userProfile.getProfileCus();
            }

        }
        reportTime = SystemClock.uptimeMillis();
        if (userProfile != null) {
            customer = userProfile.getProfileCus();
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


        //createLocationRequest();
        //mLocationSource = new MapPanoramaStVAct.LongPressLocationSource();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            ArrayList<Marker> markers = new ArrayList<Marker>();
            markers.add(mMap.addMarker(new MarkerOptions().position(userLocation).title("Your Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            int padding = 30;
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i) {
                            case R.id.new_EmA:
                                Intent myIntent = new Intent(ResponseTeamOffice.this, AwajimaReportAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.resp_Grp_Savings:
                                Intent pIntent = new Intent(ResponseTeamOffice.this, GroupSavingsTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);

                            case R.id.resp_Timelines:

                                Intent chat = new Intent(ResponseTeamOffice.this, MyTimelineAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.resp_marketT:

                                Intent shop = new Intent(ResponseTeamOffice.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);


                            case R.id.response_SupportT:
                                Intent helpIntent = new Intent(ResponseTeamOffice.this, CustomerHelpActTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(helpIntent);
                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });


        if (userLocation != null) {
            latitute1 = location.getLatitude();
            longitute1 = location.getLongitude();
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
        txtLat = findViewById(R.id.latT_emergLat);
        txtLng = findViewById(R.id.lngText_emerg);
        txtCity = findViewById(R.id.locUserAddress22);

        txtLat = findViewById(R.id.latT_emergLat);
        txtLng = findViewById(R.id.lngText_emerg);
        txtCity = findViewById(R.id.locUserAddress22);
        txtLat.setVisibility(View.VISIBLE);
        txtLng.setVisibility(View.VISIBLE);
        txtCity.setVisibility(View.VISIBLE);


        txtLat.setText(MessageFormat.format("Lat:{0}", latitute1));
        txtLng.setText(MessageFormat.format("Lng:{0}", longitute1));
        txtCity.setText(MessageFormat.format("Address:{0}", city));

    }
    private void startTrackerService() {
        startService(new Intent(this, GPSTracker.class));
        finish();
    }

    private void responseHome() {
        json1 = userPreferences.getString("LastProfileUsed", "");
        bundle = new Bundle();
        userProfile = gson1.fromJson(json1, Profile.class);
        bundle.putParcelable("Profile", userProfile);
        Intent helpIntent = new Intent(ResponseTeamOffice.this, LoginDirAct.class);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        helpIntent.putExtras(bundle);
        startActivity(helpIntent);
    }

    private final LocationCallback mLocationCallBack = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                if (location != null) {
                    latitute1 = location.getLatitude();
                    longitute1 = location.getLongitude();
                    txtLat = findViewById(R.id.latT_emergLat);
                    txtLng = findViewById(R.id.lngText_emerg);
                    txtCity = findViewById(R.id.locUserAddress22);
                    userLocation = new LatLng(latitute1, longitute1);
                    txtLat.setVisibility(View.VISIBLE);
                    txtLng.setVisibility(View.VISIBLE);
                    txtCity.setVisibility(View.VISIBLE);
                    userLocBundle = new Bundle();
                    userLocBundle.putParcelable("Location", userLocation);
                    cancellationTokenSource = new CancellationTokenSource();
                    userLocBundle.putDouble("LastLatitude", latitute1);
                    userLocBundle.putDouble("LastLongitude", longitute1);
                    userLocBundle.putParcelable("LastLocation", userLocation);
                    setResult(Activity.RESULT_OK, new Intent());
                    userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    /*StringBuilder sourceToDestination = new StringBuilder();
                    sourceToDestination.append("https://maps.googleapis.com/maps/api/directions/json?");
                    sourceToDestination.append("origin=").append(userLocation.latitude).append(",").append(userLocation.longitude);
                    sourceToDestination.append("&destination=" + 4.8156 + "," + 7.0498);
                    sourceToDestination.append("&waypoints=via:").append(latitute1).append(",").append(longitute1);
                    sourceToDestination.append("&key=").append(getResources().getString(R.string.google_api_key));*/
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(startPoint.getLatitude(), startPoint.getLongitude()))).setTitle("Source");
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(userLocation);
                    markerOptions.title("You are here");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    if (mCurrLocationMarker != null) {
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
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            altitude = location.getAltitude();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bearingAccuracyDegree = location.getBearingAccuracyDegrees();
            }

        } else {
            latitude = 4.824167;
            longitude = 6.9746;
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
                                ActivityCompat.requestPermissions(ResponseTeamOffice.this,
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


    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);

        } else {
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

        Intent intent = new Intent(this, LocUpdatesBReceiver.class);
        intent.setAction(LocUpdatesBReceiver.ACTION_PROCESS_UPDATES);
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
                            ActivityCompat.requestPermissions(ResponseTeamOffice.this,
                                    new String[]{
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(ResponseTeamOffice.this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION},
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
                Uri uri = Uri.fromParts("Awajima App",
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location2) {
                        if (location2 != null) {
                            txtLat.setVisibility(View.VISIBLE);
                            txtLng.setVisibility(View.VISIBLE);

                            latitude = location2.getLatitude();
                            longitude = location2.getLongitude();
                            userLocation = new LatLng(latitude, longitude);
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

                        txtLat.setText("Your Lat:" + latitude);
                        txtLng.setText("Your Lng:" + longitude);

                    }
                });

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        Bundle bundle=new Bundle();
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

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
                        resolvable.startResolutionForResult(ResponseTeamOffice.this, 1);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(EmergencyReport emergencyReport) {
        moreEmergReports= new ArrayList<>();
        RecyclerView moreRecyler = findViewById(R.id.recy_moreEmerg);
        if(emergencyReport !=null){
            latitude=emergencyReport.getEmergRLat();
            longitude=emergencyReport.getEmergRLng();
            placeData= emergencyReport.getEmerGPlaceData();
            moreEmergReports=emergencyReport.getEmergReportMoreReports();

        }
        moreEmergReportA = new EmergencyReportAdapter(ResponseTeamOffice.this, moreEmergReports);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate (R.layout.report_more_recyler, null);
        ImageButton closeBottomLayout = view.findViewById(R.id.reportClose);
        final Dialog mBottomSheetDialog = new Dialog(this, R.style.Theme_Design_BottomSheetDialog);
        mBottomSheetDialog.setContentView (view);
        mBottomSheetDialog.setCancelable (true);
        mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
        mBottomSheetDialog.show ();

        closeBottomLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Clicked Close",Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(ResponseTeamOffice.this);
        builder.setTitle("Action For Emergency Report");
        builder.setIcon(R.drawable.marker_);
        builder.setItems(new CharSequence[]
                        {"View More Similar Reports", "Get map Direction to the Report Scene","Navigate to the Reporter's Address"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                LinearLayoutManager layoutManagerC
                                        = new LinearLayoutManager(ResponseTeamOffice.this, LinearLayoutManager.VERTICAL, false);
                                moreRecyler.setLayoutManager(layoutManagerC);

                                moreRecyler.setAdapter(moreEmergReportA);
                                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(moreRecyler.getContext(),
                                        layoutManagerC.getOrientation());
                                moreRecyler.addItemDecoration(dividerItemDecoration7);

                                break;
                            case 1:

                                Toast.makeText(ResponseTeamOffice.this, "You have selected, get Direction", Toast.LENGTH_SHORT).show();
                                String url1 = "https://www.google.com/maps/dir/?api=1&destination=" + latitude + "," + longitude + "&travelmode=driving";
                                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                                startActivity(intent1);

                                break;


                            case 2:

                                Toast.makeText(ResponseTeamOffice.this, " Navigate to the Repoerter, selected", Toast.LENGTH_SHORT).show();
                                Uri gmmIntentUri = Uri.parse("google.navigation:q=position.latitude,position.longitude");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");

                                /*String url = "http://maps.google.com/maps?daddr=" + latitude + "," + longitude;
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                startActivity(intent);*/


                                startActivity(mapIntent);

                                break;

                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();


    }

    @Override
    public void onResponseClick(EmergResponse emergResponse) {

    }

    @Override
    public void onResponsePositionClick(int responsePosition) {

    }
}