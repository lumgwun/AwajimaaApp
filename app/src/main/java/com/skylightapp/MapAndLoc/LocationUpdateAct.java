package com.skylightapp.MapAndLoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.skylightapp.Classes.AppLocService;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportNextDAO;
import com.skylightapp.R;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static com.skylightapp.Classes.ImageUtil.TAG;

@SuppressWarnings("ALL")
public class LocationUpdateAct extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
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
    Date currentDate;
    private Profile userProfile;
    String SharedPrefUserPassword;
    long reportTime;
    int emergencyNextReportID;
    String dateOfToday;
    String SharedPrefCusID;
    Random ran;
    SecureRandom random;
    String SharedPrefUserMachine;
    String SharedPrefUserName, selectedPurpose;
    String SharedPrefProfileID, longitude, address;
    private long profileID, customerID, reportID;
    private Customer customer;
    private LatLng latlong;
    private Bundle bundle;
    private AppCompatButton btnSubmit;
    AppCompatSpinner spnPurpose;
    String latitude, currentAdd,stringLatLng;
    private boolean locationPermissionGranted;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private CancellationTokenSource cancellationTokenSource;
    Location location, location1;
    private int emergencyReportID;
    LatLng latLng;
    LatLng lastLatLng;
    String reportLatlng;
    LatLng mostRecentLatLng;
    private int PROXIMITY_RADIUS = 5000;
    List<Address> addresses;
    double lastLat, lastLng, latitute, longitute, altitute;
    double altitude, lastAltitude;
    float bearingAccuracyDegree, bearing, bearingDegree, bearing1;
    Geocoder geocoder;
    double latitude1 = 0;
    Location customerLoc;
    LatLng userLocation, cusLatLng;
    private static final int REQUEST_CHECK_SETTINGS = 1000;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    double longitude1 = 0,distanceCovered;
    Bundle userLocBundle;
    String  eMergReportTime;
    private LocationRequest request;

    private LocationAddressResultReceiver addressResultReceiver;
    //private LongPressLocationSource mLocationSource;
    private LocationCallback locationCallback;
    AppLocService appLocationService;
    private GoogleApiClient googleApiClient;
    private static final String PREF_NAME = "skylight";
    Marker now;
    private EmergencyReport emergencyReport;
    SupportMapFragment mapFragment;
    private static final String GOOGLE_API_KEY = "AIzaSyDS7bVTjP-gEVTPJrz9bZdtImFHzRGBP7w";
    int PERMISSION_ALL = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 2;

    String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,

    };

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
                        resolvable.startResolutionForResult(LocationUpdateAct.this, 1);
                    } catch (IntentSender.SendIntentException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loc_update);
        if (!hasPermissions(LocationUpdateAct.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(LocationUpdateAct.this, PERMISSIONS, PERMISSION_ALL);
        }
        emergencyReport=new EmergencyReport();
        userProfile = new Profile();
        ran = new Random();
        random = new SecureRandom();
        gson = new Gson();
        gson1 = new Gson();
        userLocBundle = getIntent().getExtras();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkGPS();
        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefProfileID = userPreferences.getString("PROFILE_ID", "");
        json1 = userPreferences.getString("LastProfileUsed", "");
        json = userPreferences.getString("LastTellerProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        reportTime = SystemClock.uptimeMillis();
        reportID = random.nextInt((int) (Math.random() * 100) + 111);
        if (userProfile != null) {
            customer = userProfile.getProfileCus();
        }
        if(userLocBundle !=null){
            emergencyReport=userLocBundle.getParcelable("EmergencyReport");

        }
        if(emergencyReport !=null){
            emergencyReportID=emergencyReport.getEmergReportID();
            reportLatlng=emergencyReport.getEmergRLatLng();
            eMergReportTime=emergencyReport.getEmergRTime();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dateOfToday = dateFormat.format(calendar.getTime());

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NotNull LocationResult locationResult) {
                location = locationResult.getLocations().get(0);
                getAddress(location);
                getCurrentLocation();
            }

            ;
        };
        googleApiClient.connect();
        createLocationRequest();
        getDeviceLocation();

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


    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();

        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            location = task.getResult();
                            if (location != null) {
                                latitude1 = location.getLatitude();
                                longitude1 = location.getLongitude();
                                cusLatLng = new LatLng(latitude1, longitude1);
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(location.getLatitude(),
                                                location.getLongitude()), 17));
                            }
                        } else {

                            cusLatLng = new LatLng(4.52871, 7.44507);
                            Log.d(TAG, "Current location is null. Using defaults.");
                            googleMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(cusLatLng, 17));
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });

                fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location2) {
                                if (location2 != null) {
                                    latitude1 = location2.getLatitude();
                                    longitude1 = location2.getLongitude();
                                    cusLatLng = new LatLng(latitude1, longitude1);


                                    setResult(Activity.RESULT_OK, new Intent());


                                } else {
                                    cusLatLng = new LatLng(4.52871, 7.44507);
                                    Log.d(TAG, "Current location is null. Using defaults.");
                                }

                            }

                        });

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


            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
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
                                    LocationUpdateAct.this,
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

    private void setInitialLocation() {

        if (ActivityCompat.checkSelfPermission(LocationUpdateAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationUpdateAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                double lat = location.getLatitude();
                double lng = location.getLongitude();

                LocationUpdateAct.this.latitude1 = lat;
                LocationUpdateAct.this.longitude1 = lng;

                try {
                    if (now != null) {
                        now.remove();
                    }

                    userLocation = new LatLng(LocationUpdateAct.this.latitude1, LocationUpdateAct.this.longitude1);
                    cusLatLng = userLocation;


                } catch (Exception ex) {

                    ex.printStackTrace();
                    Log.e("MapException", ex.getMessage());

                }


            }

        });
    }

    public void getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
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
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());

            }
        });

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


    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(2000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    null);

            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, (android.location.LocationListener) locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, (android.location.LocationListener) locationListener);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @SuppressWarnings("MissingPermission")
    private void getAddress(Location location) {

        if (!Geocoder.isPresent()) {
            Toast.makeText(LocationUpdateAct.this,
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
                    startLocationUpdates();
                } else {
                    Toast.makeText(this, "Location permission not granted, " +
                                    "restart the app if you want the feature",
                            Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {
        latitude1 = location.getLatitude();
        longitude1 = location.getLongitude();
        LatLng latLng = new LatLng(latitude1, longitude1);
        dbHelper=new DBHelper(this);
        location1 = location;
        emergencyNextReportID = ThreadLocalRandom.current().nextInt(1129, 11001);
        cancellationTokenSource = new CancellationTokenSource();
        if(this.userLocBundle !=null){
            emergencyReport=userLocBundle.getParcelable("EmergencyReport");

        }
        if(emergencyReport !=null){
            emergencyReportID=emergencyReport.getEmergReportID();
            reportLatlng=emergencyReport.getEmergRLatLng();
            eMergReportTime=emergencyReport.getEmergRTime();

        }
        if(reportLatlng !=null){
            String[] latlong = reportLatlng.split(",");
            lastLat = Double.parseDouble(latlong[0]);
            lastLng = Double.parseDouble(latlong[1]);

        }
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DIST, (android.location.LocationListener) locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, (android.location.LocationListener) locationListener);
        } catch (Exception e) {
            e.printStackTrace();
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
        }).addOnSuccessListener(this, location1 -> {
            if (location1 != null) {
                latlong = new LatLng(location1.getLatitude(), location1.getLongitude());

                latitude1 = location1.getLatitude();
                longitude1 = location1.getLongitude();
                altitude = location1.getAltitude();
                bearing = location1.getBearing();
                mostRecentLatLng = new LatLng(latitude1, longitude1);
                stringLatLng=String.valueOf(latitude1 + "," + longitude1);
                userLocBundle = new Bundle();
                userLocBundle.putParcelable("Location", mostRecentLatLng);
                userLocBundle.putDouble("PreviousAltitude", altitude);
                userLocBundle.putDouble("PreviousLongitude", longitude1);
                userLocBundle.putDouble("PreviousLatitude", latitude1);
                userLocBundle.putDouble("PreviousBearing", bearing);
                userLocBundle.putParcelable("PreviousLocation", mostRecentLatLng);


            }else {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        bearingAccuracyDegree = location.getBearingAccuracyDegrees();
                                    }
                                    latitude1 = location.getLatitude();
                                    longitude1 = location.getLongitude();
                                    altitude = location.getAltitude();
                                    bearing = location.getBearing();
                                    mostRecentLatLng = new LatLng(latitude1, longitude1);
                                    stringLatLng=String.valueOf(latitude1 + "," + longitude1);
                                    userLocBundle = new Bundle();
                                    userLocBundle.putParcelable("Location", latLng);
                                    userLocBundle.putDouble("PreviousAltitude", altitude);
                                    userLocBundle.putDouble("PreviousLongitude", longitude1);
                                    userLocBundle.putDouble("PreviousLatitude", latitude1);
                                    userLocBundle.putDouble("PreviousBearing", bearing);
                                    userLocBundle.putFloat("PreviousBearingDegree", bearingAccuracyDegree);
                                    userLocBundle.putParcelable("PreviousLocation", mostRecentLatLng);
                                    //userLocBundle.putString("Location Map", String.valueOf(sharedLocMap));




                                }
                            }
                        });
            }
        });


        String result_in_kms = "";
        String url = "http://maps.google.com/maps/api/directions/xml?origin="
                + lastLat + "," + lastLng + "&destination=" + latitude1
                + "," + longitude1 + "&sensor=false&units=metric";
        String tag[] = { "text" };
        HttpResponse response = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            response = httpClient.execute(httpPost, (org.apache.http.protocol.HttpContext) localContext);
            InputStream is = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = builder.parse(is);
            if (doc != null) {
                NodeList nl;
                ArrayList args = new ArrayList();
                for (String s : tag) {
                    nl = doc.getElementsByTagName(s);
                    if (nl.getLength() > 0) {
                        Node node = nl.item(nl.getLength() - 1);
                        args.add(node.getTextContent());
                    } else {
                        args.add(" - ");
                    }
                }
                result_in_kms = String.format("%s", args.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if(result_in_kms !=null){
                distanceCovered= Double.parseDouble(result_in_kms);

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        stringLatLng=String.valueOf(latitude1 + "," + longitude1);

        EmergReportNextDAO nextDAO= new EmergReportNextDAO(this);

        if(distanceCovered>1){
            nextDAO.insertNewEmergNextLoc(emergencyNextReportID,emergencyReportID,dateOfToday,stringLatLng);

        }


    }

    @Override
    public void onConnected(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(
                        this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

            }
            catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }
        else {

            Log.i(TAG, "Location services connection failed with code " +
                    connectionResult.getErrorCode());
        }

    }

    private class LocationAddressResultReceiver extends ResultReceiver {
        LocationAddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            if (resultCode == 0) {
                Log.d("Address", "Location null retrying");
                getAddress(location);
            }

            if (resultCode == 1) {
                Toast.makeText(LocationUpdateAct.this,
                        "Address not found, " ,
                        Toast.LENGTH_SHORT).show();
            }

            currentAdd = resultData.getString("address_result");

            showResults(currentAdd);
        }
    }

    private void showResults(String currentAdd){
        txtAddress.setText(currentAdd);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}