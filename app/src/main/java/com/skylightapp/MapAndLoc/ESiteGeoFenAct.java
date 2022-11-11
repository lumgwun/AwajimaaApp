package com.skylightapp.MapAndLoc;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.skylightapp.Classes.AppConstants;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.webgeoservices.woosmapgeofencingcore.GeofenceHelper;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.MapAndLoc.GeofenceBroadcastReceiver.TAG;
import static com.skylightapp.NotificationAct.CHANNEL_ID;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class ESiteGeoFenAct extends FragmentActivity implements LocationListener, OnMapReadyCallback, OnCompleteListener<Void> {
    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private SharedPreferences userPreferences;
    private Gson gson, gson1;
    GoogleMap googleMap;
    private String json, json1;

    private AppCompatTextView textLat, txtEmergID;
    private AppCompatTextView textLng;
    DBHelper dbHelper;
    private Date today;
    Date currentDate;
    private Profile userProfile;
    String SharedPrefUserPassword;
    long reportTime;
    String timeOfReport;
    Random ran;
    SecureRandom random;
    String SharedPrefUserMachine;
    long SharedPrefProfileID;
    String longitude;
    String address;
    private int emergReportID;
    private Bundle bundle;

    String latitude,stringLatLng;
    private FusedLocationProviderClient fusedLocationClient;
    private CancellationTokenSource cancellationTokenSource;
    Location location,deviceLocation;
    LatLng latLng, lastLatLng, currentLatlng;
    private int PROXIMITY_RADIUS = 5000;
    List<Address> addresses;
    Geocoder geocoder;
    double latitude1 = 0;
    double longitude1 = 0;
    Bundle userLocBundle;
    private LocationAddressResultReceiver addressResultReceiver;

    private static final String GOOGLE_API_KEY = "AIzaSyDS7bVTjP-gEVTPJrz9bZdtImFHzRGBP7w";
    int PERMISSION_ALL = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private static final String PREF_NAME = "awajima";
    private GeofenceHelper geofenceHelper;
    private float GEOFENCE_RADIUS = 20;
    private String GEOFENCE_ID = "SOME_GEOFENCE_ID";

    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;

    private List<LatLng> latLngList;
    private GeofencingClient geofencingClient;
    private int geofenceID;
    private String geofenceStrg;

    String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,

    };
    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        Boolean fineLocationGranted = result.get(
                                Manifest.permission.ACCESS_FINE_LOCATION);
                        Boolean coarseLocationGranted = result.get(
                                Manifest.permission.ACCESS_COARSE_LOCATION);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            // Precise location access granted.
                            if (coarseLocationGranted != null && coarseLocationGranted) {
                                checkGPS();
                            }
                        } else {
                            if (!hasPermissions(ESiteGeoFenAct.this, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(ESiteGeoFenAct.this, PERMISSIONS, PERMISSION_ALL);
                            }
                        }
                    }
            );

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
                        resolvable.startResolutionForResult(ESiteGeoFenAct.this, 1);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    private static class LongPressLocationSource implements LocationSource, GoogleMap.OnMapLongClickListener {

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
    private LocationCallback locationCallback;
    private EmergencyReport emergencyReport;
    String city;
    private enum PendingGeofenceTask {
        ADD, REMOVE, NONE
    }

    private GeofencingClient mGeofencingClient;

    private ArrayList<Geofence> mGeofenceList;


    private PendingIntent mGeofencePendingIntent;
    private PendingGeofenceTask mPendingGeofenceTask = PendingGeofenceTask.NONE;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private Button mAddGeofencesButton;
    private Button mRemoveGeofencesButton;
    private Geofence geofence;
    private double geofenceLat;
    private double geofenceLng;
    private float geofenceRadius;
    private String geofenceReqID;
    private long geofenceExpTime;
    private  int gefTransitionType;
    private  int gefNotRes;
    private  int gefLoiteringDealay;
    private ArrayList<ERGeofenceResponse> ERGeofenceResponseArrayList;
    private ERGeofenceResponse ERGeofenceResponse;

    private GeofencingRequest getGeofencingRequest() {
        mGeofenceList = new ArrayList<>();
        geofenceID = random.nextInt((int) (Math.random() * 1000) + 1010);
        geofenceStrg="Awa"+geofenceID;
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            timeOfReport =dateFormat.format(reportTime);
            today = dateFormat.parse(timeOfReport);


        } catch (ParseException ignored) {
        }
        for(LatLng coordinate: latLngList){
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(geofenceStrg) // A string to identify this geofence
                    .setCircularRegion(coordinate.latitude, coordinate.longitude, AppConstants.GEOFENCE_RADIUS_IN_METERS)
                    .setExpirationDuration(AppConstants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build()
            );
        }

        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        builder.addGeofences(mGeofenceList);

        return builder.build();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_resp_device_loc);
        geofencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeofenceHelper(this);
        bundle = new Bundle();
        gson1 = new Gson();
        random= new SecureRandom();
        ran = new Random();
        gson = new Gson();
        userProfile= new Profile();
        ERGeofenceResponseArrayList = new ArrayList<>();
        geofenceID = random.nextInt((int) (Math.random() * 1000) + 1010);
        geofenceStrg="Awajima"+geofenceID;
        addresses = new ArrayList<>();
        userLocBundle = new Bundle();
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        mGeofenceList = new ArrayList<>();
        emergencyReport= new EmergencyReport();
        reportTime = SystemClock.uptimeMillis();
        mAddGeofencesButton = (Button) findViewById(R.id.add_geofences_);
        mRemoveGeofencesButton = (Button) findViewById(R.id.remove_geof_b);
        mGeofencePendingIntent = null;
        txtEmergID = findViewById(R.id.locRepID);

        setButtonsEnabledState();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            timeOfReport =dateFormat.format(reportTime);
            today = dateFormat.parse(timeOfReport);


        } catch (ParseException ignored) {
        }

        ERGeofenceResponse = new ERGeofenceResponse();
        if(emergencyReport !=null){
            emergReportID=emergencyReport.getEmergReportID();
            ERGeofenceResponseArrayList =emergencyReport.getaLocGeoFences();
        }
        txtEmergID.setText("Emerg. Report ID:"+emergReportID);
        geocoder = new Geocoder(this, Locale.getDefault());
        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        populateGeofenceList();

        mGeofencingClient = LocationServices.getGeofencingClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapGeo);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            if (!hasPermissions(ESiteGeoFenAct.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(ESiteGeoFenAct.this, PERMISSIONS, PERMISSION_ALL);
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            timeOfReport = dateFormat.format(currentDate);
            today = dateFormat.parse(timeOfReport);


        } catch (ParseException ignored) {
        }
        dbHelper = new DBHelper(this);


        userPreferences= getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserPassword = userPreferences.getString(PROFILE_PASSWORD, "");
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefProfileID = userPreferences.getLong(PROFILE_ID, 0);
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        if(userLocBundle !=null){
            deviceLocation=userLocBundle.getParcelable("Location");

        }

        try {


            latLng = new LatLng(deviceLocation.getLatitude(), deviceLocation.getLongitude());

            mMap.addMarker(new MarkerOptions().position(latLng).title("Device Loc."));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)), 20.0f));


        } catch (Exception e) {
            e.printStackTrace();
        }


        googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        mLocationSource = new LongPressLocationSource();
        userLocBundle.putParcelable("Location",latLng);
        cancellationTokenSource = new CancellationTokenSource();
        userLocBundle.putLong("PROFILE_ID",SharedPrefProfileID);
        userLocBundle.putString("timeOfReport",timeOfReport);




    }

    private void populateGeofenceList() {

    }
    public void removeGeofencesButtonHandler(View view) {
        if (!checkPermissions()) {
            mPendingGeofenceTask = PendingGeofenceTask.REMOVE;
            requestPermissions();
            return;
        }
        removeGeofences();
    }
    public void startGeofenceAdditionOnclick(View view){
        mGeofenceList = new ArrayList<>();
        geofenceID = random.nextInt((int) (Math.random() * 1000) + 1010);
        geofenceStrg="Awa"+geofenceID;
        String notDetails="New Geofencing Entrance Alert";
        for(LatLng coordinate: latLngList){
            mGeofenceList.add(new Geofence.Builder()
                    .setRequestId(geofenceStrg) // A string to identify this geofence
                    .setCircularRegion(coordinate.latitude, coordinate.longitude, AppConstants.GEOFENCE_RADIUS_IN_METERS)
                    .setExpirationDuration(AppConstants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                    .build()
            );
            sendNotification(notDetails);
        }

    }
    @SuppressWarnings("MissingPermission")
    private void removeGeofences() {
        ERGeofenceResponse = new ERGeofenceResponse();
        if(emergencyReport !=null){
            emergReportID=emergencyReport.getEmergReportID();
            ERGeofenceResponseArrayList =emergencyReport.getaLocGeoFences();
        }
        if (!checkPermissions()) {
            showSnackbar(getString(R.string.insufficient_permissions));
            return;
        }
        //emergencyReport.removeGeofence();

        mGeofencingClient.removeGeofences(getGeofencePendingIntent()).addOnCompleteListener(this);
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        mPendingGeofenceTask = PendingGeofenceTask.NONE;
        if (task.isSuccessful()) {
            updateGeofencesAdded(!getGeofencesAdded());
            setButtonsEnabledState();

            int messageId = getGeofencesAdded() ? R.string.geofences_added :
                    R.string.geofences_removed;

            Toast.makeText(this, getString(messageId), Toast.LENGTH_SHORT).show();
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorString(this, task.getException());
            Log.w(TAG, errorMessage);
        }
    }
    private void setButtonsEnabledState() {
        if (getGeofencesAdded()) {
            mAddGeofencesButton.setEnabled(false);
            mRemoveGeofencesButton.setEnabled(true);
        } else {
            mAddGeofencesButton.setEnabled(true);
            mRemoveGeofencesButton.setEnabled(false);
        }
    }
    private boolean getGeofencesAdded() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                AppConstants.GEOFENCES_ADDED_KEY, false);
    }

    private void updateGeofencesAdded(boolean added) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(AppConstants.GEOFENCES_ADDED_KEY, added)
                .apply();
    }
    private void performPendingGeofenceTask() {

        if (mPendingGeofenceTask == PendingGeofenceTask.ADD) {
            addGeofences();
        } else if (mPendingGeofenceTask == PendingGeofenceTask.REMOVE) {
            removeGeofences();
        }
    }

    private String getGeofenceTransitionDetails(
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);

        return geofenceTransitionString + ": " + triggeringGeofencesIdsString;
    }
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }
    private void sendNotification(String notificationDetails) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            mNotificationManager.createNotificationChannel(mChannel);
        }

        Intent notificationIntent = new Intent(this, ESiteGeoFenAct.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(ESiteGeoFenAct.class);

        stackBuilder.addNextIntent(notificationIntent);


        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_notification_icon)

                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_notification_icon))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText(getString(R.string.geofence_transition_notification_text))
                .setContentIntent(notificationPendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }

        builder.setAutoCancel(true);

        mNotificationManager.notify(0, builder.build());
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        }
    }
    private void addGeofences() {
        PendingIntent pendingIntent = getGeofencePendingIntent();
        ERGeofenceResponse = new ERGeofenceResponse();
        if(emergencyReport !=null){
            emergReportID=emergencyReport.getEmergReportID();
            ERGeofenceResponseArrayList =emergencyReport.getaLocGeoFences();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        geofencingClient.addGeofences(getGeofencingRequest(), pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Geofence Added...");
                       // emergencyReport.addEmergLocGeofence();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geofenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure: " + errorMessage);
                    }
                });
    }



    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        mGeofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return mGeofencePendingIntent;
    }


    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(ESiteGeoFenAct.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");

            ActivityCompat.requestPermissions(ESiteGeoFenAct.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(ESiteGeoFenAct.this,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", location);
        startService(intent);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                finish();
                //startLocationUpdates();
            } else {
                Toast.makeText(this, "Location permission not granted, " +
                                "restart the app if you want the feature",
                        Toast.LENGTH_SHORT).show();
            }

        }
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                //We do not have the permission..

            }
        }

        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //We have the permission
                Toast.makeText(this, "You can add geofences...", Toast.LENGTH_SHORT).show();
            } else {
                //We do not have the permission..
                Toast.makeText(this, "Background location access is neccessary for geofences to trigger...", Toast.LENGTH_SHORT).show();
            }
        }

    }




    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {
        latitude1 = location.getLatitude();
        longitude1 = location.getLongitude();
        LatLng latLng = new LatLng(latitude1, longitude1);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

    }
    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                Log.d("Address", "Location null retrying");
                getAddress();
            }

            if (resultCode == 1) {
                Toast.makeText(ESiteGeoFenAct.this,
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");

            showResults(currentAdd);
        }
    }

    private void showResults(String currentAdd){
        txtEmergID.setText(currentAdd);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //fusedLocationClient.removeLocationUpdates(locationCallback);
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

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //call request
            locationPermissionRequest.launch(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});

        } else {
            fusedLocationClient.requestLocationUpdates(LocationRequest.create()
                            .setInterval(36000).setFastestInterval(36000)
                            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                            .setMaxWaitTime(1000),
                    getLocationCallback(), Looper.myLooper());
            getCurrentLocation();
        }
    }

    private LocationCallback getLocationCallback() {
        return new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    Location location = locationList.get(locationList.size() - 1);

                }
            }


        };
    }


    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, new CancellationToken() {
            @Override
            public boolean isCancellationRequested() {
                return false;
            }

            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                return null;
            }
        }).addOnSuccessListener(this, location -> {
            if (location != null) {
                latitude1 = location.getLatitude();
                longitude1 = location.getLongitude();
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                stringLatLng=String.valueOf(latitude1 + "," + longitude1);

            }
        });

    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        latLngList = new ArrayList<>();

        addGeofences();

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        for (LatLng i : latLngList) {
            addMarker(i);
            addCircle(i, GEOFENCE_RADIUS);
            addGeofence(i, GEOFENCE_RADIUS);
        }

    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }
    private void addGeofence(LatLng latLng, float radius) {

        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = getGeofencePendingIntent();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: Geofence Added...");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geofenceHelper.getErrorString(e);
                        Log.d(TAG, "onFailure: " + errorMessage);
                    }
                });
    }


    private void addCircle(LatLng latLng, float radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0));
        circleOptions.fillColor(Color.argb(64, 255, 0, 0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }



}