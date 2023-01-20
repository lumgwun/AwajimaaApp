package com.skylightapp.MapAndLoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.POIDAO;
import com.skylightapp.Database.POIVisitDAO;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class GeoFAct extends AppCompatActivity implements OnMapReadyCallback {
    public static final boolean AIRSHIP = false;
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat displayDateFormatAirship = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private LocationFragment locationFragment;
    private MapFragment mapFragment;
    private VisitFrag visitFragment;

    private POI[] POIData = new POI[0];

    private boolean isMenuOpen = false;

    private BottomNavigationView bottomNav;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private SQLiteDatabase sqLiteDatabase;
    private Calendar calendar;
    protected DBHelper dbHelper;
    Gson gson;
    private int reportID;
    private Profile trackedUser;
    private Place place;
    String json,tittle,purpose,firstName,surname,phoneNo,emailAddress,machine,bundleID,bundleMachine;

    private Woosmap woosmap;
    private Bundle trackingIntent;
    String name = "my_package_channel";
    String id = "my_package_channel_1"; // The user-visible name of the channel.
    String description = "my_package_first_channel"; // The user-visible description of the channel.

    Intent intent;
    PendingIntent pendingIntent;
    PendingIntent geofencePendingIntent;
    NotificationCompat.Builder builder;
    private GeofencingClient geofencingClient;
    private EmergencyReport report;
    private EmergReportNext emergReportNext;
    private EmergResponse emergResponse;
    private Bundle bundle;
    private ArrayList<POI> emrPOIList;
    private ArrayList<POIVisit> emrPOIVisitList;
    private ArrayList<Region> regions;
    private ArrayList<ZOI> zois;
    private ArrayList<RegionLog> regionLogs;
    private ArrayList<MovingPosition> movingPositionList;
    private POI poi;
    private POIDAO poidao;
    private POIVisitDAO poiVisitDAO;
    MapView mapView;
    GoogleMap map;
    public class WoosLocationReadyListener implements Woosmap.LocationReadyListener {
        public void LocationReadyCallback(Location location) {
            onLocationCallback(location);
        }
    }

    private void onLocationCallback(Location currentLocation) {
    }


    public class WoosAirshipSearchAPIReadyListener implements Woosmap.AirshipSearchAPIReadyListener {
        public void AirshipSearchAPIReadyCallback(HashMap<String, Object> dataEvent) {
            //sendAirshipEvent( dataEvent );
        }
    }


    public class WoosAirshipVisitReadyListener implements Woosmap.AirshipVisitReadyListener {
        public void AirshipVisitReadyCallback(HashMap<String, Object> dataEvent) {
            //sendAirshipEvent( dataEvent );
        }
    }

    public class WoosAirshipRegionLogReadyListener implements Woosmap.AirshipRegionLogReadyListener {
        public void AirshipRegionLogReadyCallback(HashMap<String, Object> dataEvent) {
            //sendAirshipEvent( dataEvent );
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_geo_fact);
        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        locationFragment = new LocationFragment();
        mapFragment = new MapFragment();
        visitFragment = new VisitFrag();
        trackingIntent= new Bundle();
        trackedUser= new Profile();
        bundle= new Bundle();
        report = new EmergencyReport();
        emergReportNext= new EmergReportNext();
        emrPOIList= new ArrayList<com.skylightapp.MapAndLoc.POI>();
        emrPOIVisitList= new ArrayList<>();
        regions= new ArrayList<>();
        zois= new ArrayList<>();
        regionLogs= new ArrayList<>();
        movingPositionList= new ArrayList<>();

        poi= new POI();
        poidao= new POIDAO(this);
        poiVisitDAO= new POIVisitDAO(this);

        POIData = new POI[0];

        setFragment(mapFragment);
        setFragment(visitFragment);
        setFragment(locationFragment);

        geofencingClient = LocationServices.getGeofencingClient(this);

        if(trackingIntent !=null){
            reportID=trackingIntent.getInt("EMERGENCY_LOCID");
            trackedUser=trackingIntent.getParcelable("Profile");
            place=trackingIntent.getParcelable("Place");
            report=trackingIntent.getParcelable("EmergencyReport");


        }
        //loadData(reportID);
        //loadPOI(reportID);
        //loadVisit(reportID);
        //loadZOI(reportID);
        //loadRegion(reportID);
        //loadRegionLogs(reportID);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_map:
                        setFragment(mapFragment);
                        return true;
                    case R.id.navigation_location:
                        setFragment(locationFragment);
                        return true;
                    case R.id.navigation_visit:
                        setFragment(visitFragment);
                        return true;
                    default:
                        return false;
                }
            }

        });

        dbHelper=new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        boolean trackingEnable = userPreferences.getBoolean("trackingEnable",true);
        boolean searchAPIEnable = userPreferences.getBoolean("searchAPIEnable",true);
        boolean distanceAPIEnable = userPreferences.getBoolean("distanceAPIEnable",true);

        boolean modeHighFrequencyLocationEnable = userPreferences.getBoolean("modeHighFrequencyLocationEnable",false);
        OtherPref.trackingEnable = trackingEnable;
        OtherPref.searchAPIEnable = searchAPIEnable;
        OtherPref.distanceAPIEnable = distanceAPIEnable;
        OtherPref.modeHighFrequencyLocation = modeHighFrequencyLocationEnable;

        OtherPref.numberOfDayDataDuration = 30;

        // Set Keys
        OtherPref.privateKeyWoosmapAPI = "";
        OtherPref.privateKeyGMPStatic = "";

        OtherPref.foregroundLocationServiceEnable = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.woosmap.createWoosmapNotifChannel();
        }

        //this.InitializeOptionsPanel();

        this.woosmap.setLocationReadyListener(new WoosLocationReadyListener());
        //this.woosmap.setSearchAPIReadyListener(new WoosSearchAPIReadyListener());
        //this.woosmap.setDistanceReadyListener(new WoosDistanceReadyListener());
        //this.woosmap.setVisitReadyListener(new WoosVisitReadyListener());
        //this.woosmap.setRegionReadyListener( new WoosRegionReadyListener() );
        //this.woosmap.setRegionLogReadyListener( new WoosRegionLogReadyListener() );
        this.woosmap.startTracking( Woosmap.ConfigurationProfile.passiveTracking );

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(43.1, -87.9)));

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private boolean checkPermissions() {

        int finePermissionState = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        int coarsePermissionState = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        return finePermissionState == PackageManager.PERMISSION_GRANTED || coarsePermissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i("Awajima App", "Displaying permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(GeoFAct.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        } else {
            Log.i("Awajima App", "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(GeoFAct.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        Log.i( "Awajima App", "onRequestPermissionResult" );
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i( "Awajima App", "User interaction was cancelled." );
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i( "Awajima App", "Permission granted, updates requested, starting location updates" );
            } else {
                // Permission denied.

                showSnackbar( R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
                                Uri uri = Uri.fromParts( "package", getPackageName(), null );
                                intent.setData( uri );
                                intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
                                startActivity( intent );
                            }
                        } );
            }
        }
    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

}