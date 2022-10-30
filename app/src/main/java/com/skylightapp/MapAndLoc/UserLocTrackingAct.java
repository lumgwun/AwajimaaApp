package com.skylightapp.MapAndLoc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.twilio.Twilio;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UserLocTrackingAct extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    long accountNo, acctID;
    long customerID;
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;
    private Customer lastCustomerProfileUsed;
    private final int MY_LOCATION_REQUEST_CODE = 100;

    public final static int SENDING = 1;
    public final static int CONNECTING = 2;
    public final static int ERROR = 3;
    public final static int SENT = 4;
    public final static int SHUTDOWN = 5;

    private static final String TAG = "CustomerAndProfileLocAct";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Button btnFusedLocation, btn_backTo_dashboard;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng cusLatLng;
    Location mCurrentLocation;
    String customerPhone;
    private Location previousLocation;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    double lastLat, lastLng, latitute1, longitute1, altitute1;


    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private LatLng latLng, profileLatLng;
    protected DBHelper dbHelper;
    SharedPreferences sharedpreferences;
    Gson gson;
    String json;
    long customerId;
    private boolean locationPermissionGranted;
    Customer customer;
    Bundle customerBundle, locBundle, profileBundle, typeBundle;
    private Marker m;
    SecureRandom random;
    Profile userProfile;
    private static boolean isPersistenceEnabled = false;
    private String stringLatitude = "0.0";
    private String stringLongitude = "0.0";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest request;
    AppCompatTextView txtLoc;
    String myLatitude;
    String myLongitude, CustomerName, profileName, locDate, profilePhoneNo, profileEmail, address, CustomerPhone;
    double latitude;
    double longitude;
    private static final int REQUEST_CHECK_SETTINGS = 1000;
    long PackageID, ProfileID;
    Geocoder geocoder;
    List<Address> addresses;
    SupportMapFragment mapFragment;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private Marker mCurrLocationMarker;

    private static final String PREF_NAME = "awajima";
    private String type;
    LatLng userLocation;
    private GoogleApiClient googleApiClient;

    private CancellationTokenSource cancellationTokenSource;
    Bundle userLocBundle;
    int PERMISSION_ALL33 = 2;
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_and_prof_loc);
        if (!hasPermissions(UserLocTrackingAct.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(UserLocTrackingAct.this, PERMISSIONS, PERMISSION_ALL33);
        }
        customer = new Customer();
        userProfile = new Profile();
        gson = new Gson();
        dbHelper = new DBHelper(this);
        txtLoc = findViewById(R.id.locTxt);
        locBundle = new Bundle();
        profileBundle = new Bundle();
        customerBundle = new Bundle();
        typeBundle = new Bundle();
        customer = new Customer();
        userLocBundle = new Bundle();
        customerBundle = getIntent().getExtras();
        typeBundle = getIntent().getExtras();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map98);
        geocoder = new Geocoder(UserLocTrackingAct.this, Locale.getDefault());
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if (typeBundle != null) {
            type = typeBundle.getString("Type");
        }
        if (type.equalsIgnoreCase("Teller")) {
            if (profileBundle != null) {
                ProfileID = profileBundle.getLong("ProfileID");
                profileName = profileBundle.getString("ProfileName");
                profilePhoneNo = profileBundle.getString("ProfilePhone");
                profileEmail = profileBundle.getString("ProfileEmail");
                profileLatLng = profileBundle.getParcelable("LatLng");
                latLng = profileLatLng;
            }

        }
        if (type.equalsIgnoreCase("Admin")) {
            if (profileBundle != null) {
                ProfileID = profileBundle.getLong("ProfileID");
                profileName = profileBundle.getString("ProfileName");
                profilePhoneNo = profileBundle.getString("ProfilePhone");
                profileEmail = profileBundle.getString("ProfileEmail");
                profileLatLng = profileBundle.getParcelable("LatLng");
                latLng = profileLatLng;
                latitute1=latLng.latitude;
                longitute1=latLng.longitude;
            }

        } else {
            if (customerBundle != null) {
                customerId = customerBundle.getLong("CustomerID");
                CustomerName = customerBundle.getString("CustomerName");
                CustomerPhone = customerBundle.getString("CustomerPhone");
                PackageID = customerBundle.getLong("PackageID");
                cusLatLng = customerBundle.getParcelable("LatLng");
                latLng = cusLatLng;
                latitute1=latLng.latitude;
                longitute1=latLng.longitude;
            }

        }

        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
        createLocationRequest();
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getMyLocation();
        locationRequest = LocationRequest.create();
        cancellationTokenSource = new CancellationTokenSource();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getDeviceLocation(latLng,latitute1,longitute1);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
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


        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //doGetLocation();
        //getLocationDetails();
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        latLng = origin;

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        String parameters = str_origin + "&amp;" + str_dest + "&amp;" + key;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation(LatLng dest, double latitute1, double longitute1) {
        try {
            if (locationPermissionGranted) {
                mCurrentLocation = null;
                fusedLocationProviderClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location mCurrentLocation) {
                                if (mCurrentLocation != null) {
                                    latitude = mCurrentLocation.getLatitude();
                                    longitude = mCurrentLocation.getLongitude();
                                    cusLatLng = new LatLng(latitude, longitude);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(mCurrentLocation.getLatitude(),
                                                    mCurrentLocation.getLongitude()), 17));

                                } else {
                                    //cusLatLng = new LatLng(4.52871, 7.44507);
                                    Log.d(TAG, "Current location is null. Using Last Location.");
                                    getPreviousLoc(latitute1,longitute1);
                                }

                            }

                        });
                dest = cusLatLng;
                setResult(Activity.RESULT_OK, new Intent());

                txtLoc.setText("My Loc:" + latitude + "," + longitude + "/");

                StringBuilder sourceToDestination = new StringBuilder();
                sourceToDestination.append("https://maps.googleapis.com/maps/api/directions/json?");
                sourceToDestination.append("origin=").append(latitude).append(",").append(longitude);
                sourceToDestination.append("&destination=").append(latitute1).append(",").append(longitute1);
                sourceToDestination.append("&waypoints=via:").append(latitude).append(",").append(longitude);
                sourceToDestination.append("&key=").append(getResources().getString(R.string.google_api_key));
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))).setTitle("Where you are");
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(dest);
                markerOptions.title("You are here");
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
        Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    mCurrentLocation = task.getResult();
                    if (mCurrentLocation != null) {
                        latitude = mCurrentLocation.getLatitude();
                        longitude = mCurrentLocation.getLongitude();
                        cusLatLng = new LatLng(latitude, longitude);

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20));
                        StringBuilder sourceToDestination = new StringBuilder();
                        sourceToDestination.append("https://maps.googleapis.com/maps/api/directions/json?");
                        sourceToDestination.append("origin=").append(latitude).append(",").append(longitude);
                        sourceToDestination.append("&destination=" + latitute1 + "," + longitute1);
                        sourceToDestination.append("&waypoints=via:").append(latitude).append(",").append(latitude);
                        sourceToDestination.append("&key=").append(getResources().getString(R.string.google_api_key));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))).setTitle("Where you were");
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(cusLatLng);
                        markerOptions.title("You are here");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                } else {

                    cusLatLng = new LatLng(4.52871, 7.44507);
                    Log.d(TAG, "Current location is null. Using defaults.");
                    mMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(cusLatLng, 20));
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                }
            }
        });

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
    protected void createLocationRequest() {

        request = new LocationRequest();
        request.setSmallestDisplacement( 10 );
        request.setFastestInterval( 50000 );
        request.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
        request.setNumUpdates( 3 );

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest( request );
        builder.setAlwaysShow( true );

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings( googleApiClient,
                        builder.build() );


        result.setResultCallback( new ResultCallback<LocationSettingsResult>() {
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
                                    UserLocTrackingAct.this,
                                    REQUEST_CHECK_SETTINGS );
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        } );


    }
    private void setInitialLocation() {
        txtLoc = findViewById(R.id.whereYou);

        if (ActivityCompat.checkSelfPermission( UserLocTrackingAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( UserLocTrackingAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                double lat=location.getLatitude();
                double lng=location.getLongitude();

                UserLocTrackingAct.this.latitude=lat;
                UserLocTrackingAct.this.longitude=lng;

                try {
                    if(mCurrLocationMarker !=null){
                        mCurrLocationMarker.remove();
                    }

                    userLocation = new LatLng( UserLocTrackingAct.this.latitude,UserLocTrackingAct.this.longitude );
                    cusLatLng=userLocation;


                } catch (Exception ex) {

                    ex.printStackTrace();
                    Log.e( "MapException", ex.getMessage() );

                }

                try {
                    geocoder = new Geocoder(UserLocTrackingAct.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {
                        txtLoc.setVisibility(View.VISIBLE);

                        android.location.Address returnAddress = addresses.get(0);

                        String localityString = returnAddress.getAddressLine (0);

                        str.append( localityString ).append( "" );

                        txtLoc.setText(MessageFormat.format("Your are here:  {0},{1}/{2}", latitude, longitude , str));
                        Toast.makeText(UserLocTrackingAct.this, str,
                                Toast.LENGTH_SHORT).show();

                    } else {
                        //go
                    }


                } catch (IOException e) {

                    Log.e("tag", e.getMessage());
                }



            }

        } );
    }
    public void doGetLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(@NotNull Location location) {
                    getLocationDetails();
                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                    locDate = mdformat.format(calendar.getTime());

                    if (location != null) {
                        stringLatitude = Double.toString(location.getLatitude());
                        stringLongitude = Double.toString(location.getLongitude());
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        latLng = new LatLng(latitude, longitude);

                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            locBundle.putString("Address", String.valueOf(addresses));
                            locBundle.putParcelable("LatLong", latLng);

                            txtLoc.setText("Address:"+addresses+"("+"Lat: " + stringLatitude + "," +" Long: " + stringLongitude+")");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        customerBundle = getIntent().getExtras();
                        if (customerBundle !=null) {
                            customerId= customerBundle.getLong("CustomerID");
                            CustomerName= customerBundle.getString("CustomerName");
                            CustomerPhone= customerBundle.getString("CustomerPhone");
                            PackageID= customerBundle.getLong("PackageID");
                            ProfileID= customerBundle.getLong("ProfileID");
                        }


                        setResult(Activity.RESULT_OK);
                        //setResult(Activity.RESULT_OK, new Intent());


                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng portHarcourt = new LatLng(4.8156, 7.0498);
        mMap.addMarker(new MarkerOptions().position(portHarcourt).title("Port Harcourt"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(portHarcourt));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        locationRequest = LocationRequest.create();
        locationRequest.setFastestInterval(12000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(" Position"));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    myLatitude = String.valueOf(location.getLatitude());
                    myLongitude = String.valueOf(location.getLongitude());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,MIN_DIST, (android.location.LocationListener) locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST, (android.location.LocationListener) locationListener);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }
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

    public void getLocationDetails(){
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        latLng = new LatLng(latitude, longitude);
        /*geoFire.setLocation("CustomerLoc", new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null) {
                    System.err.println("There was an error saving the location to Cloud: " + error);
                } else {
                    System.out.println("Location saved on server successfully!");
                }

            }

        });*/
        //Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        String address = null;
        String city = null;
        String state = null;
        String country = null;
        String postalCode = null;
        String knonName = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            knonName = addresses.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title("Customer in:" + address + city + state  + knonName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        txtLoc.setText(MessageFormat.format("{0}{1}{2}{3}", address, city, state, knonName));
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

    @Override
    public void onConnected(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {

    }
}