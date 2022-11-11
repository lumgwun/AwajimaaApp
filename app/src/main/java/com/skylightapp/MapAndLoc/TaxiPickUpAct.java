package com.skylightapp.MapAndLoc;

import static com.skylightapp.MapAndLoc.GoogleMapActivity.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;

import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Bookings.HomeViewModel;
import com.skylightapp.Bookings.MyTaxiBAct;
import com.skylightapp.Bookings.Status;
import com.skylightapp.Bookings.TaxiBoookingSheetListener;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.PackListTab;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Markets.MarketBizOffice;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.Markets.MyMarketTranxAct;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.R;
import com.teliver.sdk.core.TripListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TaxiPickUpAct extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, OnMapReadyCallback {
    private Marker calledMarker = null;
    private Marker marker = null;
    private LatLng taxiFinalLocation = null;
    private Marker userLocationTripMarker = null;

    private TripBottomSheetDialog tripBottomSheetDialog;
    private CustomDialog spaciousLocationDialog;
    private LocationUtil locationUtil;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private HomeBottomSheetDialog homeBottomSheetDialog;
    Bundle mapViewBundle = null;
    private final int widthPixels = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int heightPixels = Resources.getSystem().getDisplayMetrics().heightPixels;

    private BottomSheetButtonTypes bottomSheetButtonTypes;
    private TripListener tripListener;

    int numberOfTaxiNearby = 6;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    SupportMapFragment mapFrag;
    private View mapView, mapView2;
    GoogleMap mMap;
    private String tripDate;
    private TaxiDriver driver;
    private SharedPreferences userPreferences;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 980;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 129;
    SharedPreferences.Editor editor;
    Gson gson, gson1, gson2;
    String json, json1, json2, userName, userPassword, userMachine, dateOfToday, selectedType;
    Profile userProfile, customerProfile;
    private int profileID;
    private int customerID;
    private int reportID;
    private Customer customer;
    private DBHelper dbHelper;
    Date today;
    private static final String PREF_NAME = "awajima";
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    String SharedPrefProfileID, stringLatLng;
    private MarketBusiness marketBusiness;
    List<Address> addresses;
    private ChipNavigationBar chipNavigationBar;
    private HomeViewModel homeViewModel;
    //https://maps.googleapis.com/maps/api/directions/json?


    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Log.i("LocationCallback", "Location : $locationResult.getLastLocation()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_taxi_pick_up);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Taxi Pick up");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.lo_awaj);
        checkInternetConnection();
        fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        driver= new TaxiDriver();
        chipNavigationBar = findViewById(R.id.taxify_Bar);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle("MapViewBundleKey");
        }
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Taxi_Pick);

        if (mapFrag != null) {
            mapFrag.getMapAsync(this);
            mapView = mapFrag.getView();
        }
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        gson1 = new Gson();
        gson2 = new Gson();
        today = new Date();
        dbHelper = new DBHelper(this);
        userProfile = new Profile();
        customer = new Customer();
        addresses = new ArrayList<>();
        marketBusiness = new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefProfileID = String.valueOf(userPreferences.getLong("PROFILE_ID", 0));
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        userName = userPreferences.getString("PROFILE_USERNAME", "");
        userPassword = userPreferences.getString("PROFILE_PASSWORD", "");
        userMachine = userPreferences.getString("PROFILE_ROLE", "");
        profileID = userPreferences.getInt("PROFILE_ID", 0);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);

        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i) {
                            case R.id.taxi_bookings:
                                Intent myIntent = new Intent(TaxiPickUpAct.this, MyTaxiBAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.taxi_tranx:

                                Intent chat = new Intent(TaxiPickUpAct.this, MyMarketTranxAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.taxi_support_com:

                                Intent shop = new Intent(TaxiPickUpAct.this, CustomerHelpActTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);


                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });

    }

    private void getRoute() {


    }

    private void addRouteOnScreen() {
        Route route = homeViewModel.getTripRoute().getValue();
        if (route != null) {
            Path path = route.paths.get(0);
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.add(Utils.toLatLng(path.startLocation));
            for (TaxiStep step : path.steps) {
                for (Coordinate coordinate : step.polyline) {
                    polylineOptions.add(Utils.toLatLng(coordinate));
                }
            }
            polylineOptions
                    .add(Utils.toLatLng(path.endLocation))
                    .color(Color.GREEN)
                    .width(6F);
            mMap.clear();
            mMap.addPolyline(polylineOptions);
            mMap.setPadding(0, ((int) -(heightPixels * 0.3)), 0, ((int) (heightPixels * 0.3)));
            /*mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(new LatLngBounds(
                                    Utils.toLatLng(route.bounds.southwest),
                                    Utils.toLatLng(route.bounds.northeast)
                            ),
                            widthPixels,
                            heightPixels - bottomSheet.getMeasuredHeight(),
                            0
                    )
            );*/
            mMap.addMarker(
                    new MarkerOptions()
                            .position(Utils.toLatLng(path.startLocation))
                            .draggable(false)
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                            Utils.toBitmap(ContextCompat.getDrawable(this,
                                                    R.drawable.location_pin
                                                    )
                                            )
                                    )
                            )
                            .title(path.startAddress)
            );
            mMap.addMarker(
                    new MarkerOptions()
                            .position(Utils.toLatLng(path.endLocation))
                            .draggable(false)
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                            Utils.toBitmap(ContextCompat.getDrawable(
                                                    this,
                                                    R.drawable.location__end
                                                    )
                                            )
                                    )
                            )
                            .title(path.endAddress)
            );
            LatLng latLng = new LatLng(path.startLocation.lat, path.startLocation.lng);
            addTaxi(latLng);
            taxiFinalLocation = latLng;
            homeBottomSheetDialog.setTaxiArrivedTime(path.durationInTrafficText);
            homeBottomSheetDialog.setTaxiDistance(path.distanceText);
            homeBottomSheetDialog.setTaxiPrice(getCost(path.distance).toString());
            //firstLocationImageView.setVisibility(View.GONE);
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

    private void addTaxi(LatLng latLng) {
        if (marker != null) {
            marker.remove();
        }
        double startLat = latLng.latitude - 0.001;
        double endLat = latLng.latitude + 0.001;
        double startLng = latLng.longitude - 0.001;
        double endLng = latLng.longitude + 0.001;

        for (int i = 0; i < 5; i++) {
            LatLng taxiDriver = new LatLng(latLng.latitude, latLng.longitude);
            mMap.addMarker(new MarkerOptions().position(taxiDriver).title("Driver"));
           /* marker = mMap.addMarker(new MarkerOptions().position(new LatLng(Random.Default.nextDouble(startLat, endLat),
                                            Random.Default.nextDouble(startLng, endLng)))
                            .anchor(0.5f, 0.9f)
                            .title("HiTaxi")
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                            Utils.toBitmap(
                                                    ContextCompat.getDrawable(
                                                            this,
                                                            R.drawable.ic_taxi
                                                    )
                                            )
                                    )
                            )
            );*/
        }

        /*calledMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Random.Default.nextDouble(startLat, endLat),
                                        Random.Default.nextDouble(startLng, endLng)
                                )
                        )
                        .anchor(0.5f, 0.9f)
                        .title("Awajima Taxi")
                        .icon(
                                BitmapDescriptorFactory.fromBitmap(
                                        Utils.toBitmap(
                                                ContextCompat.getDrawable(
                                                        this,
                                                        R.drawable.ic_taxi
                                                )
                                        )
                                )
                        )
        );*/
    }

    private Double getCost(Double distance) {
        return Double.parseDouble(String.format(
                Locale.ENGLISH,
                "%.2f",
                homeViewModel.getSelectedCarType().getValue().getCar().getCostForKm() * (distance / 1000)
        ));
    }

    private void moveTaxi() {
        LatLng startPosition = calledMarker.getPosition();
        LatLng finalPosition = taxiFinalLocation;
        Handler handler = new Handler();
        Long start = SystemClock.uptimeMillis();
        AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        float durationInMs = 3000.0F;
        /*boolean hideMarker = false;
        if (homeViewModel.driverData(1).getStatus() == Status.SUCCESSFUL) {
            homeViewModel.driver = homeViewModel.driverData(1).getData();
            handler.post(new Runnable() {
                long elapsed = 0;
                float t = 0.0f;
                float v = 0.0f;

                @Override
                public void run() {
                    elapsed = SystemClock.uptimeMillis() - start;
                    t = elapsed / durationInMs;
                    v = interpolator.getInterpolation(t);
                    LatLng currentPosition = new LatLng(
                            startPosition.latitude * (1 - t) + finalPosition.latitude * t,
                            startPosition.longitude * (1 - t) + finalPosition.longitude * t
                    );
                    calledMarker.setPosition(currentPosition);
                    if (t < 1) {
                        handler.postDelayed(this, 16);
                    } else {
                        if (hideMarker) {
                            calledMarker.setVisible(false);
                        } else {
                            calledMarker.setVisible(true);
                            boolean isTripStarted = homeViewModel.getIsTripFinished().getValue();
                            if (!isTripStarted) {
                                showAlertDialog(
                                        getString(R.string.taxi_arrived_title),
                                        getString(R.string.taxi_arrived_message),
                                        R.drawable.ic_taxi_dialog
                                );
                            }
                        }
                    }

            });
        }*/
    }

    private void setupBookingBottomSheet() {
        homeBottomSheetDialog = new HomeBottomSheetDialog(new TaxiBoookingSheetListener() {
            @Override
            public Object invoke(Object o) {
                this.invoke((BottomSheetButtonTypes) o);
                return 6;
            }

            public final void invoke(@NotNull BottomSheetButtonTypes it) {
                bottomSheetButtonTypes = it;
                switch (bottomSheetButtonTypes) {
                    case HOME: {
                        navigateToDestinationFragment(false);
                        break;
                    }
                    case DESTINATION:
                    case FIRST_DESTINATION: {
                        navigateToDestinationFragment(true);
                        break;
                    }
                    case CALL_TAXI: {
                        if (homeViewModel.getDestinationLocation().getValue() == null) {
                            Toast.makeText(TaxiPickUpAct.this, "getString(R.string.warning_empty_destination)", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (homeViewModel.getStartingLocation().getValue() == null) {
                            Toast.makeText(TaxiPickUpAct.this, getString(R.string.warning_empty_starting_location), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        moveTaxi();
                        break;
                    }
                    case CAR_TYPE_SELECTED: {
                        homeViewModel.setSelectedCarType(homeBottomSheetDialog.selectedCar);
                        if (homeViewModel.getTripRoute().getValue() != null) {
                            homeBottomSheetDialog.setTaxiPrice(
                                    getCost(
                                            homeViewModel.getTripRoute().getValue().paths.get(0).distance
                                    ).toString()
                            );
                        }
                        break;
                    }
                    default:
                }
                changeBottomSheet(homeBottomSheetDialog);
            }
        });
        changeBottomSheet(homeBottomSheetDialog);
    }

    private void showAlertDialog(String title, String message, int icon) {
        CustomDialog customDialog = new CustomDialog(this)
                .setTitle(title)
                .setMessage(message)
                .setIcon(ContextCompat.getDrawable(this, icon))
                .setPositiveButton(
                        getString(R.string.start_trip),
                        () -> {
                            if (Boolean.TRUE.equals(homeViewModel.getIsDestinationSelectedBefore().getValue()) &&
                                    homeViewModel.getTripRoute().getValue() != null) {
                                //hidePetalMapsButton();
                                setTripBottomSheet(homeViewModel.getTripRoute().getValue());
                                homeViewModel.setIsTripStarted(true);
                            }
                        })
                .setCancelButton(
                        getString(R.string.answer_cancel)
                )
                .createDialog();
        customDialog.showDialog();
    }

    private void setTripBottomSheet(Route route) {
        tripBottomSheetDialog = new TripBottomSheetDialog(createTrip(route), new TaxiBoookingSheetListener() {
            @Override
            public Object invoke(Object o) {
                this.invoke((TripListener) o);
                //return Unit.INSTANCE;
                return o;
            }

            @Override
            public void invoke(BottomSheetButtonTypes it) {

            }

            public final void invoke(@NotNull TripListener it) {
                tripListener = it;
                /*if (tripListener == TripListener.START_TRIP) {
                    startLocationUpdates();
                    tripStartedOnMap();
                    moveTaxiWithRoute(route);
                }*/
            }
        });
        changeBottomSheet(tripBottomSheetDialog);
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    protected void startLocationUpdates() {

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                //.setInterval(UPDATE_INTERVAL)
                //.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }


    private void moveTaxiWithRoute(Route route) {
        List<TaxiStep> steps = route.paths.get(0).steps;
        drawTripRoute();
        animateMarker(steps, Utils.secondToMillisecond(route.paths.get(0).duration.longValue()));
    }

    private void tripStartedOnMap() {
        //myLocationButton.setVisibility(View.INVISIBLE);
        mMap.setOnCameraMoveStartedListener(i -> {

        });
        mMap.setOnCameraIdleListener(() -> {

        });
        observeAndAddTaxi();
    }

    private void drawTripRoute() {
        Route route = homeViewModel.getTripRoute().getValue();
        if (route != null) {
            Path path = route.paths.get(0);
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.add(Utils.toLatLng(path.startLocation));
            for (TaxiStep step : path.steps) {
                for (Coordinate coordinate : step.polyline) {
                    polylineOptions.add(Utils.toLatLng(coordinate));
                }
            }

            polylineOptions
                    .add(Utils.toLatLng(path.endLocation))
                    .color(Color.BLUE)
                    .width(6F);
            mMap.clear();
            mMap.addPolyline(polylineOptions);
            mMap.setPadding(0, ((int) -(heightPixels * 0.3)), 0, ((int) (heightPixels * 0.3)));
            /*mMap.addMarker(
                    new MarkerOptions()
                            .position(Utils.toLatLng(path.endLocation))
                            .anchorMarker(0.5F, 0.5F)
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                            Utils.toBitmap(ContextCompat.getDrawable(
                                                    this,
                                                    R.drawable.ic_finish_flag
                                                    )
                                            )
                                    )
                            )
            );*/
        }
    }

    private void animateMarker(List<TaxiStep> steps, Long duration) {
        Handler handler = new Handler();
        Long start = SystemClock.uptimeMillis();
        final TaxiStep[] nextStep = {steps.get(0)};
        LinearInterpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            int index = 0;

            @Override
            public void run() {
                Long elapsed = SystemClock.uptimeMillis() - start;
                float time = interpolator.getInterpolation(
                        elapsed.floatValue() / duration
                );
                if (index < steps.size()) {
                    nextStep[0] = steps.get(index);
                    animateMarkerInStep(nextStep[0]);
                    tripBottomSheetDialog.setStepData(index);
                }
                index++;
                if (time < 1.0) {
                    handler.postDelayed(this, Utils.secondToMillisecond(nextStep[0].duration.longValue()));
                } else {
                    tripHasFinished();
                }
            }
        });
    }

    private void animateMarkerInStep(TaxiStep currentStep) {
        LatLng startPosition = Utils.toLatLng(currentStep.startLocation);
        LatLng finalPosition = Utils.toLatLng(currentStep.endLocation);
        Handler handler = new Handler();
        Long start = SystemClock.uptimeMillis();
        float durationInMs = Utils.secondToMillisecond(currentStep.duration.longValue()).floatValue();
        handler.post(new Runnable() {
            long elapsed = 0;
            float time = 0.0f;

            @Override
            public void run() {
                elapsed = SystemClock.uptimeMillis() - start;
                time = elapsed / durationInMs;
                LatLng currentPosition = new LatLng(
                        startPosition.latitude * (1 - time) + finalPosition.latitude * time,
                        startPosition.longitude * (1 - time) + finalPosition.longitude * time
                );
                setupUserLocationTripMarker(
                        currentPosition,
                        getBearingBetweenTwoPoints(
                                Utils.toLatLng(currentStep.startLocation),
                                Utils.toLatLng(currentStep.endLocation)
                        )
                );
                if (time < 1) {
                    handler.postDelayed(this, 1);
                }
            }
        });
    }

    private void setupUserLocationTripMarker(LatLng userLatLng, double rotation) {
        if (userLocationTripMarker != null) {
            userLocationTripMarker.setPosition(userLatLng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 17F));
            userLocationTripMarker.setRotation(((float) rotation));
        } else {
            userLocationTripMarker = mMap.addMarker(
                    new MarkerOptions()
                            .position(userLatLng)
                            .icon(
                                    BitmapDescriptorFactory.fromBitmap(
                                            Utils.toBitmap(
                                                    ContextCompat.getDrawable(
                                                            this,
                                                            R.drawable.ic_taxi
                                                    )
                                            )
                                    )
                            )
                            .rotation(((float) rotation))
            );
            mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(userLatLng, 17F)
            );
        }
    }

    private double getBearingBetweenTwoPoints(LatLng locationOne, LatLng locationTwo) {
        double latitude1 = degreesToRadians(locationOne.latitude);
        double longitude1 = degreesToRadians(locationOne.longitude);
        double latitude2 = degreesToRadians(locationTwo.latitude);
        double longitude2 = degreesToRadians(locationTwo.longitude);
        double longitudeDifference = longitude2 - longitude1;
        double yCoordinate = Math.sin(longitudeDifference) * Math.cos(latitude2);
        double xCoordinate =
                Math.cos(latitude1) * Math.sin(latitude2) - (Math.sin(latitude1)
                        * Math.cos(latitude2) * Math.cos(longitudeDifference));
        double radiansBearing = Math.atan2(yCoordinate, xCoordinate);
        return radiansToDegrees(radiansBearing);
    }

    private double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180.0;
    }

    private double radiansToDegrees(double radians) {
        return radians * 180.0 / Math.PI;
    }

    private void tripHasFinished() {
        stopLocationUpdates();
        tripNotStartedOnMap();
        homeViewModel.setIsTripFinished(true);
    }

    private void changeBottomSheet(Fragment fragment) {
        /*FragmentTransaction fragmentTransaction = this.getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.bottomSheet, fragment);
        fragmentTransaction.commit();*/
    }

    private void showBottomSheet() {
        /*bottomSheet.animate()
                .translationY(0.0F)
                .setDuration(500);*/
    }

    private void hideBottomSheet() {
        /*bottomSheet.animate()
                .translationY((float) (getFragmentViewBinding().bottomSheet.getHeight()))
                .setDuration(500);*/
    }

    private void tripNotStartedOnMap() {
        checkLocationPermission();
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        Handler handler = new Handler();
        /*handler.postDelayed(() -> {
            mMap.setOnCameraMoveStartedListener(i -> {
                myLocationButton.setVisibility(View.INVISIBLE);
                hideBottomSheet();
            });
            mMap.setOnCameraIdleListener(() -> {
                myLocationButton.setVisibility(View.VISIBLE);
                showBottomSheet();
            });
        }, 2000);
        observeAndAddTaxi();*/
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
                                ActivityCompat.requestPermissions(TaxiPickUpAct.this,
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void findLocation() {
        getLastKnownLocation();

        new CountDownTimer(30_000, 1_000) {
            @Override
            public void onFinish() {
                //locationDialog.dismissDialog();
                moveSpaciousLocationDialog();
            }

            @Override
            public void onTick(long l) {
                if (homeViewModel.getLastKnownLocation().getValue() != null) {
                    cancel();
                }
            }
        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void moveSpaciousLocationDialog() {
        if (spaciousLocationDialog == null) {
            spaciousLocationDialog = new CustomDialog(this)
                    .setTitle(getString(R.string.app_name))
                    .setIcon(ContextCompat.getDrawable(this, R.drawable.ic_error))
                    .setMessage(getString(R.string.loc_place_error_message))
                    .setPositiveButton(
                            getString(R.string.try_again),
                            () -> {
                                //locationDialog.showDialog();
                                findLocation();
                            }).createDialog();
        }
        spaciousLocationDialog.showDialog();
        homeViewModel.getLastKnownLocation().getValue();
    }

    private TaxiTrip createTrip(Route route) {
        Path path = route.paths.get(0);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:SS", Locale.getDefault());

        tripDate = dateFormat.format(calendar.getTime());
        //return new TaxiTrip(path.startLocation, path.startAddress, path.endLocation, path.endAddress, getCost(path.distance), route, driver);
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getLastKnownLocation() {

        FallbackLocTracker tracker = new FallbackLocTracker(TaxiPickUpAct.this);
        tracker.start(new LocationTracker.LocationUpdateListener() {
            @Override
            public void onUpdate(Location oldLoc, long oldTime, Location newLoc, long newTime) {
                if(newLoc==null) {
                    if(oldLoc !=null){
                       // location = oldLoc;

                    }else {
                        //location = newLoc;
                    }
                }
                //LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            }
        });

    }

    private void navigateToDestinationFragment(boolean isFromDestination) {
        //NavDirections action = (NavDirections) HomeFragmentDirections.actionHomeFragmentToDestinationFragment().setIsFromDestination(isFromDestination);
        //NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

    }


    private void showRouteOnPetalMaps(Coordinate startLocation, Coordinate endLocation) {
        Uri contentUri = Uri.parse(
                "mapapp://navigation?saddr=" + startLocation.lat
                        + "," + startLocation.lng + "&daddr=" + endLocation.lat + ","
                        + endLocation.lng + "&language=en&type=drive"
        );
        Intent petalMapsIntent = new Intent(Intent.ACTION_VIEW, contentUri);
        if (petalMapsIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(petalMapsIntent);
        }
    }

    private void observeAndAddTaxi() {
        /*homeViewModel.getLastKnownLocation().observe(getViewLifecycleOwner(), latLng -> {
                    if (latLng != null && Boolean.FALSE.equals(homeViewModel.getIsDestinationSelectedBefore().getValue())) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f));
                        mMap.clear();
                        addTaxi(latLng);
                        taxiFinalLocation = latLng;
                    }
                }
        );*/
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        checkLocationPermission();
        mMap = googleMap;
        tripNotStartedOnMap();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        } startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {
            double latitude = mLocation.getLatitude();
            double longitude = mLocation.getLongitude();
        } else {
            // Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}