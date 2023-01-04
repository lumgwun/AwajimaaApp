package com.skylightapp.MapAndLoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.POIDAO;
import com.skylightapp.Database.POIVisitDAO;
import com.skylightapp.R;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GeofenceAct extends AppCompatActivity {
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
    private ArrayList<com.skylightapp.MapAndLoc.POI> emrPOIList;
    private ArrayList<POIVisit> emrPOIVisitList;
    private ArrayList<Region> regions;
    private ArrayList<ZOI> zois;
    private ArrayList<RegionLog> regionLogs;
    private ArrayList<MovingPosition> movingPositionList;
    private POI poi;
    private POIDAO poidao;
    private POIVisitDAO poiVisitDAO;
    public class WoosLocationReadyListener implements Woosmap.LocationReadyListener {
        public void LocationReadyCallback(Location location) {
            onLocationCallback(location);
        }
    }

    private void onLocationCallback(Location currentLocation) {
    }


    public class WoosAirshipSearchAPIReadyListener implements Woosmap.AirshipSearchAPIReadyListener {
        public void AirshipSearchAPIReadyCallback(HashMap<String, Object> dataEvent) {
            sendAirshipEvent( dataEvent );
        }
    }


    public class WoosAirshipVisitReadyListener implements Woosmap.AirshipVisitReadyListener {
        public void AirshipVisitReadyCallback(HashMap<String, Object> dataEvent) {
            sendAirshipEvent( dataEvent );
        }
    }

    public class WoosAirshipRegionLogReadyListener implements Woosmap.AirshipRegionLogReadyListener {
        public void AirshipRegionLogReadyCallback(HashMap<String, Object> dataEvent) {
            sendAirshipEvent( dataEvent );
        }
    }



    void sendAirshipEvent(HashMap<String, Object> dataEvent){
        if(AIRSHIP) {
            // Create and name an event
            //CustomEvent.Builder eventBuilder = new CustomEvent.Builder( (String) dataEvent.get( "event" ) );

            // Set custom event properties on the builder
           /* for (Map.Entry<String, Object> entry : dataEvent.entrySet()) {
                eventBuilder.addProperty(entry.getKey(),  entry.getValue().toString() );
            }

            // Then record it
            CustomEvent event = eventBuilder.build();

            event.track();*/
        }
    }

    public class WoosMarketingCloudSearchAPIReadyListener implements Woosmap.MarketingCloudSearchAPIReadyListener {
        public void MarketingCloudSearchAPIReadyCallback(HashMap<String, Object> dataEvent) {
            sendMarketingCloudEvent( dataEvent );
        }
    }


    public class WoosMarketingCloudVisitReadyListener implements Woosmap.MarketingCloudVisitReadyListener {
        public void MarketingCloudVisitReadyCallback(HashMap<String, Object> dataEvent) {
            sendMarketingCloudEvent( dataEvent );
        }
    }

    public class WoosMarketingCloudRegionLogReadyListener implements Woosmap.MarketingCloudRegionLogReadyListener {
        public void MarketingCloudRegionLogReadyCallback(HashMap<String, Object> dataEvent) {
            sendMarketingCloudEvent( dataEvent );
        }
    }

    void sendMarketingCloudEvent(HashMap<String, Object> dataEvent){
        // Call Marketing Cloud API here
    }

    public class WoosSearchAPIReadyListener implements Woosmap.SearchAPIReadyListener {
        public void SearchAPIReadyCallback(POI poi) {
            onPOICallback(poi);
        }
    }

    private void onPOICallback(POI poi) {
    }

    public class WoosDistanceReadyListener implements Woosmap.DistanceReadyListener {
        public void DistanceReadyCallback(Distance[] distances) {
            onDistanceCallback(distances);
        }
    }

    private void onDistanceCallback(Distance[] distances) {
    }

    public class WoosVisitReadyListener implements Woosmap.VisitReadyListener {
        public void VisitReadyCallback(Visit visit) {
            onVisitCallback(visit);
        }
    }

    private void onVisitCallback(Visit visit) {
    }

    public class WoosRegionReadyListener implements Woosmap.RegionReadyListener {
        public void RegionReadyCallback(Region region) {
            onRegionCallback(region);
        }
    }

    private void onRegionCallback(Region region) {
    }

    public class WoosRegionLogReadyListener implements Woosmap.RegionLogReadyListener {
        public void RegionLogReadyCallback(RegionLog regionLog) {
            onRegionLogCallback(regionLog);
        }
    }

    private void onRegionLogCallback(RegionLog regionLog) {
        createNotification("Region update from geofence detection","Region : " + regionLog.getIdentifier() + "\n" + "didenter : " + regionLog.didEnter +
                "\n" + "isCurrentPositionInside : " + regionLog.isCurrentPositionInside +
                "\n" + "Date : " + displayDateFormatAirship.format(regionLog.getDateTime()));
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            Log.d("Awajima App", "Permission OK");
            this.woosmap.onResume();
        } else {
            Log.d("Awajima App", "Permission NOK");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Awajima App", "BackGround");
        if (checkPermissions()) {
            this.woosmap.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        woosmap.onDestroy();
        super.onDestroy();
    };


    public void createNotification(String title, String body) {
        final int NOTIFY_ID = 19002;

        // There are hardcoding only for show it's just strings


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


            intent = new Intent(this, GeofenceAct.class);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_geofence);
        locationFragment = new LocationFragment();
        woosmap = Woosmap.getInstance().initializeWoosmap(this);
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
        loadData(reportID);
        loadPOI(reportID);
        loadVisit(reportID);
        loadZOI(reportID);
        loadRegion(reportID);
        loadRegionLogs(reportID);

        if(AIRSHIP) {
            String channelId = "452";
            System.out.println( "Awajima App Channel ID: " + channelId );
        }

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

        // Set Filter on user Location
        //WoosmapSettings.currentLocationTimeFilter = 30;

        // Set Filter on search API
        //WoosmapSettings.searchAPITimeFilter = 30;
        //WoosmapSettings.searchAPIDistanceFilter = 50;

        // Set Filter on Accuracy of the location
        //WoosmapSettings.accuracyFilter = 10;

        // Instanciate woosmap object


        // Set the Delay of Duration data
        OtherPref.numberOfDayDataDuration = 30;

        // Set Keys
        OtherPref.privateKeyWoosmapAPI = "";
        OtherPref.privateKeyGMPStatic = "";

        OtherPref.foregroundLocationServiceEnable = true;

        this.woosmap.setLocationReadyListener(new WoosLocationReadyListener());
        this.woosmap.setSearchAPIReadyListener(new WoosSearchAPIReadyListener());
        this.woosmap.setDistanceReadyListener(new WoosDistanceReadyListener());
        this.woosmap.setVisitReadyListener(new WoosVisitReadyListener());
        this.woosmap.setRegionReadyListener( new WoosRegionReadyListener() );
        this.woosmap.setRegionLogReadyListener( new WoosRegionLogReadyListener() );

        // Airship Listener
        //this.woosmap.setAirshipSearchAPIReadyListener( new WoosAirshipSearchAPIReadyListener() );
        //this.woosmap.setAirshipVisitReadyListener( new WoosAirshipVisitReadyListener() );
        //this.woosmap.setAirhshipRegionLogReadyListener( new WoosAirshipRegionLogReadyListener() );

        // Marketing CLoud Listener
        //this.woosmap.setMarketingCloudSearchAPIReadyListener( new WoosMarketingCloudSearchAPIReadyListener() );
        //this.woosmap.setMarketingCloudVisitReadyListener( new WoosMarketingCloudVisitReadyListener() );
        //this.woosmap.setMarketingCloudRegionLogReadyListener( new WoosMarketingCloudRegionLogReadyListener() );

        // Search API parameters
        //OtherPref.searchAPIParameters.put("radius","5000");
        //OtherPref.searchAPIParameters.put("stores_by_page","100");

        // User properties filter
        //OtherPref.userPropertiesFilter.add( "creation_year" );

        // Fix the radius of geofence POI
        //OtherPref.poiRadiusNameFromResponse = "near_radius";
        //OtherPref.poiRadius = 500;

        this.woosmap.startTracking( Woosmap.ConfigurationProfile.passiveTracking );

        //WoosmapSettings.foregroundLocationServiceEnable = true;

        // For android version >= 8 you have to create a channel or use the woosmap's channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.woosmap.createWoosmapNotifChannel();
        }

        this.InitializeOptionsPanel();
        //geofencePendingIntent = LocationServices.getGeofencingClient(this);


    }
    private String getGeofenceTransitionDetails(GeofenceBroadcastReceiver geofenceBroadcastReceiver, int geofenceTransition, List<Geofence> triggeringGeofences) {


        geofencingClient.removeGeofences(getGeofencePendingIntent())
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Geofences removed
                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to remove geofences
                        // ...
                    }
                });
        return null;
    }

    private void InitializeOptionsPanel() {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        final SharedPreferences.Editor editor = userPreferences.edit();
        final FloatingActionButton enableLocationUpdateBtn = findViewById(R.id.UpdateLocation);
        enableLocationUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherPref.modeHighFrequencyLocation = !OtherPref.modeHighFrequencyLocation;
                String msg = "";
                if(OtherPref.modeHighFrequencyLocation) {
                    msg = "Mode High Frequency Location Enable";
                    editor.putBoolean( "modeHighFrequencyLocationEnable",true);
                    editor.apply();
                    enableLocationUpdateBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                } else {
                    msg = "Mode High Frequency Location disable";
                    editor.putBoolean( "modeHighFrequencyLocationEnable",false);
                    editor.apply();
                    enableLocationUpdateBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                }
                woosmap.enableModeHighFrequencyLocation(OtherPref.modeHighFrequencyLocation );
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });

        final FloatingActionButton clearDBBtn = findViewById(R.id.clearDB);
        clearDBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Clear Database", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                new clearDBTask(GeofenceAct.this, GeofenceAct.this).execute();
            }
        });

        final FloatingActionButton testZOIBtn = findViewById(R.id.TestZOI);
        testZOIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Create ZOI", 8000)
                        .setAction("Action", null).show();
                new testZOITask(GeofenceAct.this, GeofenceAct.this).execute();
                //new testDataImportTask(getApplicationContext(), MainActivity.this).execute();
            }
        });

        final FloatingActionButton enableLocationBtn = findViewById(R.id.EnableLocation);
        enableLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherPref.trackingEnable = !OtherPref.trackingEnable;
                String msg = "";
                if(OtherPref.trackingEnable) {
                    msg = "Tracking Enable";
                    editor.putBoolean( "trackingEnable",true);
                    editor.apply();
                    enableLocationBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                } else {
                    msg = "Tracking Disable";
                    editor.putBoolean( "trackingEnable",false);
                    editor.apply();
                    enableLocationBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                }
                woosmap.enableTracking(OtherPref.trackingEnable);
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final FloatingActionButton enableSearchAPIBtn = findViewById(R.id.EnableSearchAPI);
        enableSearchAPIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherPref.searchAPIEnable = !OtherPref.searchAPIEnable;
                String msg = "";
                if(OtherPref.searchAPIEnable) {
                    msg = "SearchAPI Enable";
                    editor.putBoolean( "searchAPIEnable",true);
                    editor.apply();
                    enableSearchAPIBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                }else {
                    msg = "SearchAPI Disable";
                    editor.putBoolean( "searchAPIEnable",false);
                    editor.apply();
                    enableSearchAPIBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                }
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final FloatingActionButton enableDistanceAPIBtn = findViewById(R.id.EnableDistanceAPI);
        enableDistanceAPIBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherPref.distanceAPIEnable = !OtherPref.distanceAPIEnable;
                String msg = "";
                if(OtherPref.distanceAPIEnable) {
                    msg = "DistanceAPI Enable";
                    editor.putBoolean( "distanceAPIEnable",true);
                    editor.apply();
                    enableDistanceAPIBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                }else {
                    msg = "DistanceAPI Disable";
                    editor.putBoolean( "distanceAPIEnable",false);
                    editor.apply();
                    enableDistanceAPIBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
                }
                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if(OtherPref.trackingEnable) {
            enableLocationBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        } else {
            enableLocationBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        }

        if(OtherPref.modeHighFrequencyLocation) {
            enableLocationUpdateBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        } else {
            enableLocationUpdateBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        }

        if(OtherPref.distanceAPIEnable) {
            enableDistanceAPIBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        }else {
            enableDistanceAPIBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        }

        if(OtherPref.searchAPIEnable) {
            enableSearchAPIBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        }else {
            enableSearchAPIBtn.setBackgroundTintList(getResources().getColorStateList(R.color.colorAccent));
        }

        final FloatingActionButton menuSettings = findViewById(R.id.Menu);
        menuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMenuOpen) {
                    isMenuOpen = true;
                    clearDBBtn.animate().translationY(-200);
                    testZOIBtn.animate().translationY(-400);
                    enableDistanceAPIBtn.animate().translationY(-600);
                    enableSearchAPIBtn.animate().translationY(-800);
                    enableLocationBtn.animate().translationY(-1000);
                    enableLocationUpdateBtn.animate().translationY(-1200);
                } else {
                    isMenuOpen = false;
                    clearDBBtn.animate().translationY(0);
                    testZOIBtn.animate().translationY(0);
                    enableDistanceAPIBtn.animate().translationY(0);
                    enableSearchAPIBtn.animate().translationY(0);
                    enableLocationBtn.animate().translationY(0);
                    enableLocationUpdateBtn.animate().translationY(0);
                }
            }
        });

    }

    private void loadPOI(int reportID) {
        poidao= new POIDAO(this);
        emrPOIList= new ArrayList<com.skylightapp.MapAndLoc.POI>();
        emrPOIList=poidao.getPOILiveReports("live");
        //POIData = emrPOIList;
        loadData(this.reportID);
    }

    private void loadVisit(int reportID) {
        poiVisitDAO= new POIVisitDAO(this);
        emrPOIVisitList= new ArrayList<>();
        emrPOIVisitList=poiVisitDAO.getAllPOIVisitsForReport(reportID);
        final ArrayList<PlaceData> arrayOfPlaceData = new ArrayList<>();
        for (POIVisit visitToShow : emrPOIVisitList) {
            PlaceData place = new PlaceData();
            place.setType( PlaceData.dataType.visit );
            place.setLatitude( visitToShow.getLat() );
            place.setLongitude( visitToShow.getLng() );
            place.setArrivalDate( visitToShow.getStartTime() );
            place.setDepartureDate( visitToShow.getEndTime() );
            place.setDuration( visitToShow.getDuration() );
            arrayOfPlaceData.add( place );

            @SuppressLint("SimpleDateFormat") SimpleDateFormat displayDateFormat = new SimpleDateFormat("HH:mm:ss");
            if(visitToShow.getDuration() >= OtherPref.durationVisitFilter) {
                LatLng latLng = new LatLng( visitToShow.getLat(), visitToShow.getLng() );
                String startFormatedDate = displayDateFormat.format( visitToShow.getStartTime() );
                String endFormatedDate = "";
                if (visitToShow.getEndTime() == 0) {
                    //POIVisit in progress
                    endFormatedDate = "ongoing";
                } else {
                    endFormatedDate = displayDateFormat.format( visitToShow.getEndTime() );
                }
                String infoVisites = " --> start: " + startFormatedDate + " / end: " + endFormatedDate + " NbPt : " + visitToShow.getPoint();
                MarkerOptions markerOptions = new MarkerOptions().position( latLng ).title( infoVisites );
                boolean markerToUpdate = false;
                for (MarkerOptions marker : GeofenceAct.this.mapFragment.markersVisit) {
                    if (marker.getPosition().equals( markerOptions.getPosition() )) {
                        //Update marker
                        markerToUpdate = true;
                        marker.title( markerOptions.getTitle() );
                    }
                }
                if (!markerToUpdate) {
                    GeofenceAct.this.mapFragment.markersVisit.add( markerOptions );
                    if (GeofenceAct.this.mapFragment.mGoolgeMap != null) {
                        GeofenceAct.this.mapFragment.visitMarkerList.add( GeofenceAct.this.mapFragment.mGoolgeMap.addMarker( markerOptions.icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_ORANGE ) ) ) );
                        if (!GeofenceAct.this.mapFragment.visitEnableCheckbox.isChecked()) {
                            for (Marker marker : GeofenceAct.this.mapFragment.visitMarkerList) {
                                marker.setVisible( false );
                            }
                        }
                    }
                }
            }
        }
        GeofenceAct.this.visitFragment.loadDataFromVisit( arrayOfPlaceData );
    }

    private void loadRegion(int reportID) {
        regions= new ArrayList<>();
        if(trackingIntent !=null){
            reportID=trackingIntent.getInt("EMERGENCY_LOCID");
            trackedUser=trackingIntent.getParcelable("Profile");
            place=trackingIntent.getParcelable("Place");
            report=trackingIntent.getParcelable("EmergencyReport");


        }
        if(report !=null){
            regions =report.getEmrRegions();

        }

        GeofenceAct.this.mapFragment.regions.clear();
        GeofenceAct.this.mapFragment.regions.addAll(regions);

        if (GeofenceAct.this.mapFragment.mGoolgeMap != null) {
            GeofenceAct.this.mapFragment.clearCircleGeofence();
            GeofenceAct.this.mapFragment.drawCircleGeofence();
        }
    }

    private void loadZOI(int reportID) {
        zois= new ArrayList<>();

        GeofenceAct.this.mapFragment.zois.clear();
        GeofenceAct.this.mapFragment.clearPolygon();
        if(trackingIntent !=null){
            reportID=trackingIntent.getInt("EMERGENCY_LOCID");
            trackedUser=trackingIntent.getParcelable("Profile");
            place=trackingIntent.getParcelable("Place");
            report=trackingIntent.getParcelable("EmergencyReport");


        }
        if(report !=null){
            zois =report.getEmrZOIs();

        }

        GeofenceAct.this.mapFragment.zois.addAll(zois);

        if (GeofenceAct.this.mapFragment.mGoolgeMap != null) {
            GeofenceAct.this.mapFragment.drawPolygon();
        }
    }

    private void loadRegionLogs(int reportID) {
        regionLogs= new ArrayList<>();
        final ArrayList<PlaceData> arrayOfPlaceData = new ArrayList<>();
        if(trackingIntent !=null){
            reportID=trackingIntent.getInt("EMERGENCY_LOCID");
            trackedUser=trackingIntent.getParcelable("Profile");
            place=trackingIntent.getParcelable("Place");
            report=trackingIntent.getParcelable("EmergencyReport");


        }
        if(report !=null){
            regionLogs =report.getEmrRegionLog();

        }
        for (RegionLog regionLogToShow : regionLogs) {
            PlaceData place = new PlaceData( regionLogToShow );
            arrayOfPlaceData.add( place );
        }
        GeofenceAct.this.visitFragment.loadDataFromRegionLog( arrayOfPlaceData );
    }

    public void loadData(int reportID) {
        movingPositionList= new ArrayList<>();
        final ArrayList<PlaceData> arrayOfPlaceData = new ArrayList<>();
        if(trackingIntent !=null){
            reportID=trackingIntent.getInt("EMERGENCY_LOCID");
            trackedUser=trackingIntent.getParcelable("Profile");
            place=trackingIntent.getParcelable("Place");
            report=trackingIntent.getParcelable("EmergencyReport");


        }
        if(report !=null){
            movingPositionList =report.getEmrMovingPosition();

        }
        for (MovingPosition locationToShow : movingPositionList) {
            final PlaceData place = new PlaceData();
            place.setType( PlaceData.dataType.location );
            place.setLatitude( locationToShow.getLat() );
            place.setLongitude( locationToShow.getLng() );
            place.setDate(locationToShow.getDateTime());
            place.setLocationId( locationToShow.getId() );

            for (POI poi : POIData) {
                if (poi.getPoiID() == locationToShow.getId()) {
                    place.setPOILatitude( poi.getLat() );
                    place.setPOILongitude( poi.getLng() );
                    place.setZipCode( poi.getZipCode() );
                    place.setCity( poi.getCity() );
                    place.setDistance((double) poi.getDistance());
                    place.setTravelingDistance( poi.getTravelingDistance() );
                    place.setType( PlaceData.dataType.POI );
                    place.setMovingDuration( poi.getDuration() );
                    break;
                }
            }
            arrayOfPlaceData.add( place );
            if(place.getType() == PlaceData.dataType.location) {
                LatLng latLng = new LatLng( place.getLatitude(), place.getLongitude() );
                boolean markerToAdd = true;
                MarkerOptions markerOptions = new MarkerOptions().position( latLng ).icon( BitmapDescriptorFactory.defaultMarker( BitmapDescriptorFactory.HUE_MAGENTA ) );
                if (!GeofenceAct.this.mapFragment.markersLocations.isEmpty()) {
                    for (MarkerOptions marker : GeofenceAct.this.mapFragment.markersLocations) {
                        if (marker.getPosition().equals( markerOptions.getPosition() )) {
                            markerToAdd = false;
                        }
                    }
                }
                if (markerToAdd) {
                    GeofenceAct.this.mapFragment.markersLocations.add( markerOptions );
                    if (GeofenceAct.this.mapFragment.mGoolgeMap != null && GeofenceAct.this.mapFragment.isVisible()) {
                        GeofenceAct.this.mapFragment.locationMarkerList.add( GeofenceAct.this.mapFragment.mGoolgeMap.addMarker( markerOptions ) );
                        if (!GeofenceAct.this.mapFragment.locationEnableCheckbox.isChecked()) {
                            for (Marker marker : GeofenceAct.this.mapFragment.locationMarkerList) {
                                marker.setVisible( false );
                            }
                        }
                    }
                }
            }

            if (GeofenceAct.this.mapFragment.mGoolgeMap != null && GeofenceAct.this.mapFragment.isVisible()) {

                if(place.getType() == PlaceData.dataType.POI) {
                    for (POI poi : POIData) {
                        if (poi.getPoiID() == locationToShow.getId()) {
                            place.setPOILatitude( poi.getLat() );
                            place.setPOILongitude( poi.getLng() );
                            place.setZipCode( poi.getZipCode() );
                            place.setCity( poi.getCity() );
                            place.setDistance((double) poi.getDistance());
                            place.setTravelingDistance( poi.getTravelingDistance() );
                            place.setType( PlaceData.dataType.POI );
                            place.setMovingDuration( poi.getDuration() );
                            LatLng latLng = new LatLng( place.getPOILatitude(), place.getPOILongitude() );
                            boolean markerToAdd = true;
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(place.getCity()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            if (!GeofenceAct.this.mapFragment.markersPOI.isEmpty()) {
                                for (MarkerOptions marker : GeofenceAct.this.mapFragment.markersPOI) {
                                    if (marker.getPosition().equals(markerOptions.getPosition())) {
                                        markerToAdd =false;
                                    }
                                }
                            }
                            if(markerToAdd) {
                                GeofenceAct.this.mapFragment.markersPOI.add(markerOptions);
                                GeofenceAct.this.mapFragment.poiMarkerList.add(GeofenceAct.this.mapFragment.mGoolgeMap.addMarker(markerOptions));
                                if(!GeofenceAct.this.mapFragment.POIEnableCheckbox.isChecked()){
                                    for (Marker marker : GeofenceAct.this.mapFragment.poiMarkerList) {
                                        marker.setVisible(false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        GeofenceAct.this.locationFragment.loadData( arrayOfPlaceData );
    }
    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);

        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
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
            ActivityCompat.requestPermissions(GeofenceAct.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        } else {
            Log.i("Awajima App", "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(GeofenceAct.this,
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


    public static class clearDBTask extends AsyncTask<Void, Void, Void> {
        private final Context mContext;
        public GeofenceAct mActivity;

        clearDBTask(Context context, GeofenceAct activity) {
            mContext = context;
            mActivity = activity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //WoosmapDb.getInstance(mContext).clearAllTables();
            Woosmap.getInstance().removeGeofence();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (mActivity.locationFragment.adapter != null)
                mActivity.locationFragment.clearData();
            if (mActivity.visitFragment.adapter != null)
                mActivity.visitFragment.clearData();
            mActivity.mapFragment.clearMarkers();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class testZOITask extends AsyncTask<Void, Void, Void> {
        private final Context mContext;
        public GeofenceAct mActivity;

        testZOITask(Context context, GeofenceAct activity) {
            mContext = context;
            mActivity = activity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream in = getResources().openRawResource(R.raw.location);
            // if you want less visits and ZOI you can load the file location.csv like that :
            //InputStream in = getResources().openRawResource(R.raw.location);
            POIVisitDAO poiVisitDAO1= new POIVisitDAO(GeofenceAct.this);
            @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss+SS");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            VisitsCreator figmmForVisitsCreator = new VisitsCreator(GeofenceAct.this);
            try {
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    String[] separated = line.split(",");
                    String id = separated[0];
                    double accuracy = Double.parseDouble(separated[1]);
                    String[] valLatLng = separated[2].replace("POINT(","").replace(")","").split(" ");

                    double x = Double.parseDouble(valLatLng[0]);
                    double y = Double.parseDouble(valLatLng[1]);

                    long startime = formatter.parse(separated[3]).getTime();
                    long endtime = formatter.parse(separated[4]).getTime();

                    com.skylightapp.MapAndLoc.Visit visit = new Visit();
                    visit.lat = y;
                    visit.lng = x;
                    visit.startTime = startime;
                    visit.endTime = endtime;
                    visit.accuracy = (float) accuracy;
                    visit.uuid = id;
                    visit.duration = visit.endTime - visit.startTime;


                    //WoosmapDb.getInstance(mContext).getVisitsDao().createStaticPosition(visit);

                    figmmForVisitsCreator.figmmForVisitTest(visit);
                    i++;
                }
                figmmForVisitsCreator.update_db();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }

    public class testDataImportTask extends AsyncTask<Void, Void, Void> {
        private final Context mContext;
        public GeofenceAct mActivity;

        testDataImportTask(Context context, GeofenceAct activity) {
            mContext = context;
            mActivity = activity;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            InputStream in = getResources().openRawResource(R.raw.dataimport);

            /*String dir=Environment.getExternalStorageDirectory().getAbsolutePath();
            File sd = new File(dir);
            File data = Environment.getDataDirectory();
            FileChannel source = null;
            FileChannel destination = null;
            String backupDBPath = "/data/com.synnlabz.sycryptr/databases/Sycrypter.db";
            String currentDBPath = "Sycrypter.db";
            File currentDB = new File(sd, currentDBPath);
            File backupDB = new File(data, backupDBPath);

            try {
                source = new FileInputStream(currentDB).getChannel();
                destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            URLConnection ucon = imageUrl.openConnection();

            InputStream is = ucon.getInputStream();*/


            @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss+SS");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            //To be fixed

            /*PositionsManager mPositionsManager = new PositionsManager(mContext,WoosmapDb.getInstance( mContext ));
            try {
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    String[] separated = line.split(",");
                    String id = separated[0];
                    double accuracy = Double.parseDouble(separated[3]);

                    Location loc = new Location("test");
                    loc.setTime( Long.parseLong( separated[5] ) );
                    loc.setAccuracy( (float) accuracy );
                    loc.setLatitude( Double.parseDouble(separated[1]));
                    loc.setLongitude(  Double.parseDouble(separated[2]));

                    List<Location> listLocations = new ArrayList<Location>();
                    listLocations.add(loc);
                    Thread.sleep(200);
                    mPositionsManager.asyncManageLocation(listLocations);

                    i++;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }

    }
}