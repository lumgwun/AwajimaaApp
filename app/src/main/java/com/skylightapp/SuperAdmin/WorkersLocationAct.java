package com.skylightapp.SuperAdmin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LocationUpdateAct;
import com.skylightapp.MapAndLoc.GetAddressIntentService;
import com.skylightapp.MapAndLoc.ProfileLocSourceAct;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class WorkersLocationAct extends FragmentActivity implements LocationListener, OnMapReadyCallback {
    private GoogleMap mMap;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson, gson1;
    GoogleMap googleMap;
    private String json, json1, SharedPrefSuperUser;

    private AppCompatTextView textLat, txtAddress;
    private AppCompatTextView textLng;
    String latString, lngString;
    DBHelper dbHelper;
    private Date today;
    Date currentDate;
    private Profile userProfile;
    String SharedPrefUserPassword;
    long reportTime;
    String timeOfReport;
    String SharedPrefCusID;
    Random ran;
    SecureRandom random;
    String SharedPrefUserMachine;
    String SharedPrefUserName, selectedPurpose;
    long SharedPrefProfileID;
    String longitude;
    String address;
    private int profileID;
    private int reportID;
    private Customer customer;
    private LatLng latlong;
    private Bundle bundle;
    private AppCompatButton btnSubmit;
    AppCompatSpinner spnPurpose;
    String latitude,stringLatLng;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private CancellationTokenSource cancellationTokenSource;
    Location location;
    LatLng latLng, lastLatLng, currentLatlng;
    private int PROXIMITY_RADIUS = 5000;
    List<Address> addresses;
    double lastLat, lastLng, latitute, longitute, altitute;
    double altitude, lastAltitude;
    float bearingAccuracyDegree, bearing, bearingDegree, bearing1;
    Geocoder geocoder;
    double latitude1 = 0;
    double longitude1 = 0;
    Bundle userLocBundle;
    private LocationAddressResultReceiver addressResultReceiver;

    private static final String GOOGLE_API_KEY = "AIzaSyDS7bVTjP-gEVTPJrz9bZdtImFHzRGBP7w";
    int PERMISSION_ALL = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;
    private static final String PREF_NAME = "skylight";

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
                            if (!hasPermissions(WorkersLocationAct.this, PERMISSIONS)) {
                                ActivityCompat.requestPermissions(WorkersLocationAct.this, PERMISSIONS, PERMISSION_ALL);
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
                        resolvable.startResolutionForResult(WorkersLocationAct.this, 1);
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
        private static final String TAG = ProfileLocSourceAct.class.getSimpleName();
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_workers_loc);
        bundle = new Bundle();
        gson1 = new Gson();
        gson = new Gson();
        userProfile= new Profile();
        addresses = new ArrayList<>();
        userLocBundle = new Bundle();
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        reportTime = SystemClock.uptimeMillis();
        customer = new Customer();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HH:mm:ss", Locale.getDefault());

        try {
            timeOfReport =dateFormat.format(reportTime);
            today = dateFormat.parse(timeOfReport);


        } catch (ParseException ignored) {
        }
        geocoder = new Geocoder(this, Locale.getDefault());
        addressResultReceiver = new LocationAddressResultReceiver(new Handler());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            if (!hasPermissions(WorkersLocationAct.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(WorkersLocationAct.this, PERMISSIONS, PERMISSION_ALL);
            }
        }
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                location = locationResult.getLocations().get(0);
                getAddress();
            };
        };
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            timeOfReport = dateFormat.format(currentDate);
            today = dateFormat.parse(timeOfReport);


        } catch (ParseException ignored) {
        }
        //startLocationUpdates();
        dbHelper = new DBHelper(this);

        ran = new Random();
        random = new SecureRandom();
        userPreferences= getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword = userPreferences.getString(PROFILE_PASSWORD, "");
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefSuperUser = userPreferences.getString("Teller", "");
        SharedPrefProfileID = userPreferences.getLong(PROFILE_ID, 0);
        json1 = userPreferences.getString("LastProfileUsed", "");
        json = userPreferences.getString("LastTellerProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);

        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        textLat = findViewById(R.id.latText);
        textLng = findViewById(R.id.editText2);
        txtAddress = findViewById(R.id.locUserAddress);
        btnSubmit = findViewById(R.id.buttonLoc);
        spnPurpose = findViewById(R.id.purpose_spn_);
        spnPurpose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reportID = random.nextInt((int) (Math.random() * 900) + 1110);
                selectedPurpose = (String) parent.getSelectedItem();
                Toast.makeText(WorkersLocationAct.this, "Purpose: " + selectedPurpose, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        googleMap.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);

        btnSubmit.setOnClickListener(this::updateButtonOnclick);
        mLocationSource = new LongPressLocationSource();
        userLocBundle.putParcelable("Location",latLng);
        cancellationTokenSource = new CancellationTokenSource();
        userLocBundle.putLong("PROFILE_ID",SharedPrefProfileID);
        userLocBundle.putString("timeOfReport",timeOfReport);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dbHelper.insertUserLocation(profileID,customerID, String.valueOf(reportTime),latlong);
                dbHelper.insertUserEmergencyReport(reportID, profileID, timeOfReport, selectedPurpose,"", stringLatLng);
                Intent intent = new Intent(WorkersLocationAct.this, LocationUpdateAct.class);
                intent.putExtras(userLocBundle);
                startService(intent);

            }
        });
        /*fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitute = location.getLatitude();
                            longitute = location.getLongitude();
                            userLocBundle = new Bundle();
                            LatLng userLocation = new LatLng(latitute, longitute);
                            userLocBundle.putParcelable("Location", latLng);
                            cancellationTokenSource = new CancellationTokenSource();
                            userLocBundle.putDouble("LastLatitude", latitute);
                            userLocBundle.putDouble("LastLongitude", longitute);
                            userLocBundle.putParcelable("LastLocation", userLocation);
                            setResult(Activity.RESULT_OK, new Intent());
                        }
                    }
                });*/
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


        try {

            /*String[] stringLat = latString.split(", ");
            Arrays.sort(stringLat);
            latitude = stringLat[stringLat.length-1].split("=")[1];

            String[] stringLong = lngString.split(", ");
            Arrays.sort(stringLong);
            longitude = stringLong[stringLong.length-1].split("=")[1];


            LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));*/

            mMap.addMarker(new MarkerOptions().position(latLng).title(latitude + " , " + longitude));
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
        textLat.setText("Lat:" + latitude);
        textLng.setText("Lng:" + longitude);
        txtAddress.setText("Address:" + address);

    }

    @SuppressWarnings("MissingPermission")
    private void getAddress() {

        if (!Geocoder.isPresent()) {
            Toast.makeText(WorkersLocationAct.this,
                    "Can't find current address, ",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, GetAddressIntentService.class);
        intent.putExtra("add_receiver", addressResultReceiver);
        intent.putExtra("add_location", location);
        startService(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    finish();
                    //startLocationUpdates();
                } else {
                    Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
                return;
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
                Toast.makeText(WorkersLocationAct.this,
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();
            }

            String currentAdd = resultData.getString("address_result");

            showResults(currentAdd);
        }
    }

    private void showResults(String currentAdd){
        txtAddress.setText(currentAdd);
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
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }
        LatLng ourPlace = new LatLng(4.824167, 6.9746);
        mMap.addMarker(new MarkerOptions().position(ourPlace).title("Marker in LS"));
       mMap.moveCamera(CameraUpdateFactory.newLatLng(ourPlace));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(4.824167, 6.9746), 20.0f));

        locationListener = new LocationListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onLocationChanged(@NotNull Location location) {
                try {
                    textLat.setText(Double.toString(location.getLatitude()));
                    textLng.setText(Double.toString(location.getLongitude()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        };

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, (android.location.LocationListener) locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, (android.location.LocationListener) locationListener);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void updateButtonOnclick(View view){
        dbHelper.insertUserEmergencyReport(reportID,profileID, timeOfReport, selectedPurpose,"", stringLatLng);

    }
}