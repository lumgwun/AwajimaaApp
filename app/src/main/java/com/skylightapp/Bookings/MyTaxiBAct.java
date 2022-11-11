package com.skylightapp.Bookings;

import static com.skylightapp.Classes.ImageUtil.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TaxiDriverDAO;
import com.skylightapp.MapAndLoc.TaxiDriver;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.Markets.MarketCreatorAct;
import com.skylightapp.R;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MyTaxiBAct extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, LocationListener {
    private TaxiDriver taxiDriver;
    private ArrayList<TaxiDriver> taxiDrivers;
    private TaxiDriverDAO taxiDriverDAO;
    SharedPreferences userPreferences;
    Random ran;
    SecureRandom random;
    int profileID, newMarketID;
    Gson gson, gson1;
    String json, json1, localityString,address;
    Profile userProfile, lastProfileUsed;
    ArrayList<Market> marketArrayList;
    List<Address> addresses;

    Location location;
    //AppCompatTextView txtLoc22, txtLocTxt;
    private LocationRequest request;
    private FusedLocationProviderClient fusedLocationClient;
    private CancellationTokenSource cancellationTokenSource;
    private static final String PREF_NAME = "awajima";
    private LocationManager locationManager;
    protected GoogleApiClient mGoogleApiClient;
    private SQLiteDatabase sqLiteDatabase;
    private LatLng latLng;
    Location mCurrentLocation = null;
    LatLng userLocation;
    LatLng cusLatLng;
    double latitude = 0;
    double longitude = 0;
    Geocoder geocoder;
    private Calendar calendar;
    int PERMISSION_ALL33 = 345;
    private boolean locationPermissionGranted;
    private GoogleMap mGoogleMap;
    private  Bundle userBundle;
    String dateOFBookingR,city,autoAddress,selectedAddress;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1298;
    private LocationCallback locationCallback;
    private ArrayList<LatLng> taxiDriversLatLng;
    private DBHelper dbHelper;


    String[] PERMISSIONS33 = {
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.SEND_SMS
    };
    ActivityResultLauncher<Intent> mStartLocNowForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            latLng = intent.getParcelableExtra("CusLatLng");
                        }
                        // Handle the Intent
                    }
                }
            });

    private final android.location.LocationListener locationListenerNetwork = new android.location.LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyTaxiBAct.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });


            try {
                Geocoder newGeocoder = new Geocoder(MyTaxiBAct.this, Locale.ENGLISH);
                List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder street = new StringBuilder();
                if (Geocoder.isPresent()) {

                    Address newAddress = newAddresses.get(0);

                    String localityString = newAddress.getLocality();

                    street.append(localityString).append("");
                    Toast.makeText(MyTaxiBAct.this, street,
                            Toast.LENGTH_SHORT).show();

                } else {
                    //txtLoc22.setVisibility(View.GONE);
                    //go
                }


            } catch (IndexOutOfBoundsException | IOException e) {

                Log.e("tag", e.getMessage());
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_taxi_bact);
        taxiDrivers= new ArrayList<>();
        taxiDriversLatLng= new ArrayList<>();
        taxiDriverDAO= new TaxiDriverDAO(this);
        userProfile = new Profile();
        dbHelper= new DBHelper(this);
        gson1 = new Gson();
        gson = new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        cancellationTokenSource = new CancellationTokenSource();
        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getReadableDatabase();
            taxiDriversLatLng=taxiDriverDAO.getTaxiDriverFromDistanceM(cusLatLng);


        }



        List<Place.Field> fields = Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autoc_taxi);


        if (autocompleteFragment != null) {
            autocompleteFragment.setCountries("NG", "NG");
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        ran = new Random();
        random = new SecureRandom();
        calendar = Calendar.getInstance();
        Animation translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateOFBookingR = mdformat.format(calendar.getTime());
        getDeviceLocation();
        setInitialLocation();
        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, locationListenerNetwork);
        }
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyTaxiBAct.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                        }
                    });
                    userLocation = new LatLng(latitude, longitude);
                    cusLatLng = userLocation;


                    try {
                        Geocoder newGeocoder = new Geocoder(MyTaxiBAct.this, Locale.ENGLISH);
                        List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                        StringBuilder street = new StringBuilder();
                        if (Geocoder.isPresent()) {

                            Address newAddress = newAddresses.get(0);

                            String localityString = newAddress.getLocality();

                            street.append(localityString).append("");


                        } else {
                            //txtLocTxt.setVisibility(View.GONE);
                            //go
                        }


                    } catch (IndexOutOfBoundsException | IOException e) {

                        Log.e("tag", e.getMessage());
                    }

                }
            }
        };
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapM_Taxi);
        if (fm != null) {
            fm.getMapAsync(this);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        userBundle= new Bundle();
        mGoogleMap.setMyLocationEnabled(true);




        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                //.setTypeFilter(Arrays.asList(TypeFilter.ADDRESS.toString()))
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME));
        }

        // Set up a PlaceSelectionListener to handle the response.
        if (autocompleteFragment != null) {
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {


                @Override
                public void onError(@NonNull Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }

                @Override
                public void onPlaceSelected(@NonNull com.google.android.libraries.places.api.model.Place place) {
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                    if(place !=null){
                        autoAddress=place.getAddress();

                    }



                }
            });
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
    @Override
    protected void onStart() {
        super.onStart();

        geocoder = new Geocoder(this, Locale.getDefault());
        getDeviceLocation();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

    }
    @Override
    protected void onStop() {
        super.onStop();



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
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                cusLatLng = new LatLng(latitude, longitude);

                            }
                        } else {

                            cusLatLng = new LatLng(4.52871, 7.44507);
                            Log.d(TAG, "Current location is null. Using defaults.");
                            /*googleMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(cusLatLng, 17));
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);*/
                        }
                    }
                });

                try {
                    geocoder = new Geocoder(MyTaxiBAct.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {
                        //txtLoc.setVisibility(View.VISIBLE);

                        android.location.Address returnAddress = addresses.get(1);

                        String localityString = returnAddress.getAdminArea();

                        str.append(localityString).append("");

                    } else {
                        //go
                    }


                } catch (IllegalStateException | IOException e) {

                    Log.e("tag", e.getMessage());
                }


                fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location2) {
                                if (location2 != null) {
                                    latitude = location2.getLatitude();
                                    longitude = location2.getLongitude();
                                    cusLatLng = new LatLng(latitude, longitude);


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
                        //txtLoc.setVisibility(View.VISIBLE);



                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
                String city = addresses.get(0).getAdminArea();


                //txtLoc.setText("My Loc:" + latitude + "," + longitude + "/" + city);


            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    private void setInitialLocation() {
        final double[] newLat = {0.00};
        final double[] newLng = {0.00};
        geocoder = new Geocoder(this, Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(MyTaxiBAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MyTaxiBAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (locationPermissionGranted) {
            fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location2) {
                            if (location2 != null) {
                                latitude = location2.getLatitude();
                                longitude = location2.getLongitude();
                                cusLatLng = new LatLng(latitude, longitude);

                                setResult(Activity.RESULT_OK, new Intent());


                            } else {
                                cusLatLng = new LatLng(4.52871, 7.44507);
                                Log.d(TAG, "Current location is null. Using defaults.");
                            }
                            mCurrentLocation =location2;

                        }

                    });



        }

        if(mCurrentLocation !=null){
            newLat[0] = mCurrentLocation.getLatitude();
            newLng[0] = mCurrentLocation.getLongitude();

        }


        try {
            if (Geocoder.isPresent()) {
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {

                    address = addresses.get(0).getAddressLine(0);
                    localityString = addresses.get(0).getSubLocality();
                    city = addresses.get(0).getAdminArea();
                    //txtLoc.setVisibility(View.VISIBLE);



                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                StringBuilder street = new StringBuilder();



                street.append(localityString).append("");



                Toast.makeText(MyTaxiBAct.this, street,
                        Toast.LENGTH_SHORT).show();

            }

            Geocoder newGeocoder = new Geocoder(MyTaxiBAct.this, Locale.ENGLISH);



        } catch (IndexOutOfBoundsException e) {

            Log.e("tag", e.getMessage());
        }

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        taxiDriversLatLng= new ArrayList<>();
        taxiDriverDAO= new TaxiDriverDAO(this);
        dbHelper= new DBHelper(this);

        mGoogleMap=googleMap;
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            cusLatLng = new LatLng(latitude, longitude);


            setResult(Activity.RESULT_OK, new Intent());


        } else {
            cusLatLng = new LatLng(4.52871, 7.44507);
            Log.d(TAG, "Current location is null. Using defaults.");
        }
        MarkerOptions markerOptions = new MarkerOptions()
                .position(cusLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title("Where you are");
        if (mGoogleMap != null) {

            Marker mMarker = mGoogleMap.addMarker(markerOptions);
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(19));
        }
        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getReadableDatabase();
            taxiDriversLatLng=taxiDriverDAO.getTaxiDriverFromDistanceM(cusLatLng);


        }

        if (mGoogleMap != null) {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (mGoogleMap != null) {
            mGoogleMap.addPolyline(new PolylineOptions().addAll(taxiDriversLatLng).
                    width(5).color(Color.BLUE));
        }

    }

}