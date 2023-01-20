package com.skylightapp.Admins;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewSource;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.ui.IconGenerator;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.BuildConfig;
import com.skylightapp.MapAndLoc.LocUpdatesBReceiver;
import com.skylightapp.Classes.Utils;
import com.skylightapp.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("deprecation")

public class TrackWorkersAct extends FragmentActivity implements OnStreetViewPanoramaReadyCallback, RoutingListener, SharedPreferences.OnSharedPreferenceChangeListener, OnMapReadyCallback,
        com.google.android.gms.location.LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 60 * 1; //1 minute
    // 1 minute

    private static final float SMALLEST_DISPLACEMENT = 0.25F; //quarter of a meter
    private static final String REQUESTING_LOCATION_UPDATES_KEY = "RequestLocUpdate";
    Button btnFusedLocation;
    ;
    LocationRequest mLocationRequest;
    private LongPressLocationSource mLocationSource;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation,adminLocation;
    String mLastUpdateTime;
    GoogleMap googleMap;
    private  boolean requestingLocationUpdates=false;

    private double adminLng;
    private double adminLat;
    private ArrayList<LatLng> points; //added
    Polyline line; //added
    Bundle lastLocBundle,previousLocBundle;
    double lastLat,lastLng,previousLat,previousLng;
    float lastBearing,lastBearingDegree,previousBearing,previousBearingDegree;
    double lastAltitude,previousAltitude;
    float bearingAccuracyDegree,bearing;
    private SharedPreferences sharedPreferences;
    private String s;
    LatLng previousLatLng,lastLatLng;
    private FusedLocationProviderClient fusedLocationClient;
    private CancellationTokenSource cancellationTokenSource;
    private LocationSource.OnLocationChangedListener mListener;
    long atTime,workerLocTime,workerPreviousLocTime;
    LatLng adminPosition;
    //private LongPressLocationSource mLocationSource;
    AppCompatTextView txtLocUpdatesResultView, txtDistance,txtPDistance,tvLocation,tvPLocation,txtPDuration, txtDuration;
    AppCompatButton mShareLocButton;
    AppCompatButton mRequestUpdatesButton;
    AppCompatButton mRemoveUpdatesButton;
    View view;
    long currentLocTime;

    private boolean mPaused;
    StreetViewPanorama streetViewPanorama;
    GoogleMap map;
    Uri sharedLocMap;
    private FusedLocationProviderClient mFusedLocationClient;
    Location location;
    LatLng latLng,currentLatlng;
    double altitude,latitute,longitute;
    float bearingDegree,bearing1;
    FloatingActionButton fabRouting;
    AppCompatImageButton mBtnWalk, pBtnWalk,mBtnCar,pBtnCar,mBtnBicycle,PBtnBycicle;

    private static class LongPressLocationSource implements LocationSource, GoogleMap.OnMapLongClickListener {

        private LocationSource.OnLocationChangedListener mListener;

        private boolean mPaused;
        private static final String TAG = TrackWorkersAct.class.getSimpleName();
        private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

        private static final long UPDATE_INTERVAL = 60000; // Every 60 seconds.


        private static final long FASTEST_UPDATE_INTERVAL = 30000; // Every 30 seconds


        private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 5; // Every 5 minutes.




        @Override
        public void activate(OnLocationChangedListener listener) {
            mListener = listener;
        }

        @Override
        public void deactivate() {
            mListener = null;
        }

        @Override
        public void onMapLongClick(LatLng point) {
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


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_track_workers);
        mBtnWalk = findViewById(R.id.mBtnWalk);
        //pBtnWalk = findViewById(R.id.pBtnWalk);
        mBtnCar = findViewById(R.id.mBtnCar);
        //pBtnCar = findViewById(R.id.pBtnCar);
        mBtnBicycle = findViewById(R.id.mBtnBicycle);
        //PBtnBycicle = findViewById(R.id.PBtnBycicle);
        mBtnWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRoutingPathWalking();

            }
        });
        mBtnBicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRoutingPathBicycle();

            }
        });
        mBtnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRoutingPath();

            }
        });

        points=new ArrayList<LatLng>();
        lastLocBundle=getIntent().getExtras();
        txtLocUpdatesResultView = findViewById(R.id.locUpDate2);
        txtDistance = findViewById(R.id.locDistance8);
        txtDuration = findViewById(R.id.locDuration34);
        txtPDuration = findViewById(R.id.locDuration340);
        tvLocation = findViewById(R.id.locAddress);
        fabRouting = (FloatingActionButton)findViewById(R.id.fabRouting);
        fabRouting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackWorkersAct.this.getRoutingPath();
                Snackbar.make(v, "Fetching Route", Snackbar.LENGTH_SHORT).show();
            }
        });
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map22);
        if (fm != null) {
            fm.getMapAsync(TrackWorkersAct.this);
        }

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map20);
        //googleMap = fm.getMapAsync();
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        else {
            if (!checkPermissions()) {
                requestPermissions();
            }
            map.setMyLocationEnabled(true);



        }

        fusedLocationClient.getCurrentLocation(2,cancellationTokenSource.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {


                            //userLocBundle.putString("Location Map", String.valueOf(sharedLocMap));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                bearingAccuracyDegree=location.getBearingAccuracyDegrees();
                            }
                            latitute=location.getLatitude();
                            longitute=location.getLongitude();
                            altitude=location.getAltitude();
                            bearing=location.getBearing();
                            currentLocTime=location.getTime();
                            LatLng lastLocation = new LatLng(latitute, longitute);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                bearingDegree=location.getBearingAccuracyDegrees();
                            }
                            streetViewPanorama.setPosition(lastLocation);
                            streetViewPanorama.setPosition(lastLocation, 20);
                            streetViewPanorama.setPosition(lastLocation, StreetViewSource.OUTDOOR);

                            streetViewPanorama.setPosition(lastLocation, 20, StreetViewSource.OUTDOOR);
                            long duration = 1000;
                            StreetViewPanoramaCamera camera =
                                    new StreetViewPanoramaCamera.Builder()
                                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                                            .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                                            .bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
                                            .build();
                            streetViewPanorama.animateTo(camera, duration);
                        }
                        else {

                            adminLat =4.52871;
                            adminLng =7.44507;
                            LatLng unyeada = new LatLng(adminLat, adminLng);
                            map.addMarker(new MarkerOptions().position(unyeada).title("Marker in Unyeada"));
                            map.moveCamera(CameraUpdateFactory.newLatLng(unyeada));
                            map.setLocationSource(mLocationSource);
                            map.setOnMapLongClickListener(mLocationSource);

                            LatLng userLocation = new LatLng(adminLat, adminLng);
                            streetViewPanorama.setPosition(userLocation);
                            streetViewPanorama.setPosition(userLocation, 20);
                            streetViewPanorama.setPosition(userLocation, StreetViewSource.OUTDOOR);

                            streetViewPanorama.setPosition(userLocation, 20, StreetViewSource.OUTDOOR);
                            long duration = 1000;

                            StreetViewPanoramaCamera camera =
                                    new StreetViewPanoramaCamera.Builder()
                                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                                            .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                                            .bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
                                            .build();
                            streetViewPanorama.animateTo(camera, duration);

                        }

                    }
                });


        lastLocBundle=getIntent().getExtras();
        if(lastLocBundle !=null){
            previousAltitude=lastLocBundle.getDouble("PreviousAltitude");
            previousLng=lastLocBundle.getDouble("PreviousLongitude");
            previousLat=lastLocBundle.getDouble("PreviousLatitude");
            previousBearing=lastLocBundle.getFloat("PreviousBearing");
            previousBearingDegree=lastLocBundle.getFloat("PreviousBearingDegree");
            previousLatLng=lastLocBundle.getParcelable("PreviousLocation");
            workerPreviousLocTime=lastLocBundle.getLong("PreviousTime");

            lastLat=lastLocBundle.getDouble("LastLatitude");
            lastLng=lastLocBundle.getDouble("LastLongitude");
            lastAltitude=lastLocBundle.getDouble("LastAltitude");
            lastBearing=lastLocBundle.getFloat("LastBearing");
            lastBearingDegree=lastLocBundle.getFloat("LastBearingDegree");
            lastLatLng=lastLocBundle.getParcelable("LastLocation");
            workerLocTime=lastLocBundle.getLong("LastTime");

            MarkerOptions options = new MarkerOptions();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(lastLat,
                        lastLng, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);

            options.snippet(cityName + "," + stateName);
            tvLocation.setText(MessageFormat.format("Address:{0},{1}", cityName, stateName));



            IconGenerator iconFactory = new IconGenerator(this);
            iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
            mLastUpdateTime="last updated:";
            options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mLastUpdateTime)));
            options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
            LatLng workerLatLng = new LatLng(lastLat, lastLng);

            options.position(workerLatLng);
            Marker mapMarker = googleMap.addMarker(options);

            mapMarker.setTitle("Worker @:"+workerLocTime);
            Log.d(TAG, "Worker @.............................");
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(workerLatLng,
                    13));

        }

        Log.d(TAG, "onCreate ...............................");


        if (mListener != null) {
            //Location location = new Location("LongPressLocationProvider");
            points = new ArrayList<LatLng>();

            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);

            line = googleMap.addPolyline(options);

            long atTime = mCurrentLocation.getTime();
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
            adminLocation.setLatitude(adminPosition.latitude);
            adminLocation.setLongitude(adminPosition.longitude);
            adminLocation.setAccuracy(100);
            mListener.onLocationChanged(adminLocation);

            adminLng = adminLocation.getLongitude();
            adminLat = adminLocation.getLatitude();
            LatLng latLng = new LatLng(adminLat, adminLng);

            points.add(latLng);

            CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(adminPosition);

            CameraUpdate updateZoom = CameraUpdateFactory.zoomTo(6);

            googleMap.moveCamera(updatePosition);

            googleMap.animateCamera(updateZoom);
        }






        /*if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        createLocationRequest();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();*/

    }
    @Override
    public void onRoutingFailure(RouteException e)
    {
        Toast.makeText(TrackWorkersAct.this, "Routing Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRoutingStart() { }

    @Override
    public void onRoutingSuccess(ArrayList<Route> list, int i)
    {
        try
        {
            //Get all points and plot the polyLine route.
            lastLocBundle=getIntent().getExtras();
            if(lastLocBundle !=null){
                previousAltitude=lastLocBundle.getDouble("PreviousAltitude");
                previousLng=lastLocBundle.getDouble("PreviousLongitude");
                previousLat=lastLocBundle.getDouble("PreviousLatitude");
                previousBearing=lastLocBundle.getFloat("PreviousBearing");
                previousBearingDegree=lastLocBundle.getFloat("PreviousBearingDegree");
                previousLatLng=lastLocBundle.getParcelable("PreviousLocation");
                workerPreviousLocTime=lastLocBundle.getLong("PreviousTime");

                lastLat=lastLocBundle.getDouble("LastLatitude");
                lastLng=lastLocBundle.getDouble("LastLongitude");
                lastAltitude=lastLocBundle.getDouble("LastAltitude");
                lastBearing=lastLocBundle.getFloat("LastBearing");
                lastBearingDegree=lastLocBundle.getFloat("LastBearingDegree");
                lastLatLng=lastLocBundle.getParcelable("LastLocation");
                workerLocTime=lastLocBundle.getLong("LastTime");

                MarkerOptions options = new MarkerOptions();

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(lastLat,
                            lastLng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);

                options.snippet(cityName + "," + stateName);



                IconGenerator iconFactory = new IconGenerator(this);
                iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
                mLastUpdateTime="last updated:";
                options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mLastUpdateTime)));
                options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
                LatLng workerLatLng = new LatLng(lastLat, lastLng);

                options.position(workerLatLng);
                Marker mapMarker = googleMap.addMarker(options);

                mapMarker.setTitle("Worker @:"+workerLocTime);
                Log.d(TAG, "Worker @.............................");
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(workerLatLng,
                        13));

            }

            List<LatLng> listPoints = list.get(0).getPoints();
            PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
            Iterator<LatLng> iterator = listPoints.iterator();
            while(iterator.hasNext())
            {
                LatLng data = iterator.next();
                options.add(data);
            }

            if(line != null)
            {
                line.remove();
            }
            line = map.addPolyline(options);

            if (mListener != null) {


                long atTime = adminLocation.getTime();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
                adminLocation.setLatitude(adminPosition.latitude);
                adminLocation.setLongitude(adminPosition.longitude);
                adminLocation.setAccuracy(100);
                mListener.onLocationChanged(adminLocation);

                adminLng = adminLocation.getLongitude();
                adminLat = adminLocation.getLatitude();
                LatLng latLng = new LatLng(adminLat, adminLng);

                CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(adminPosition);

                CameraUpdate updateZoom = CameraUpdateFactory.zoomTo(6);

                googleMap.moveCamera(updatePosition);

                googleMap.animateCamera(updateZoom);
                LatLng workerLatLng = new LatLng(lastLat, lastLng);
                map.moveCamera(CameraUpdateFactory.newLatLng(list.get(0).getLatLgnBounds().getCenter()));
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latLng);
                builder.include(workerLatLng);
                LatLngBounds bounds = builder.build();
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
            }



            txtDistance.setText(MessageFormat.format("Distance: {0}", list.get(0).getDistanceText()));
            txtDuration.setText(MessageFormat.format("Duration: {0}", list.get(0).getDurationText()));
            //txtTime.setText("Duration: " + list.get(0).getDurationText());


        }
        catch (Exception e)
        {
            Toast.makeText(TrackWorkersAct.this, "EXCEPTION: Cannot parse routing response", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRoutingCancelled()
    {
        Toast.makeText(TrackWorkersAct.this, "Routing Cancelled", Toast.LENGTH_SHORT).show();
    }

    private void getRoutingPath()
    {
        try
        {
            lastLocBundle=getIntent().getExtras();
            if(lastLocBundle !=null){
                previousAltitude=lastLocBundle.getDouble("PreviousAltitude");
                previousLng=lastLocBundle.getDouble("PreviousLongitude");
                previousLat=lastLocBundle.getDouble("PreviousLatitude");
                previousBearing=lastLocBundle.getFloat("PreviousBearing");
                previousBearingDegree=lastLocBundle.getFloat("PreviousBearingDegree");
                previousLatLng=lastLocBundle.getParcelable("PreviousLocation");
                workerPreviousLocTime=lastLocBundle.getLong("PreviousTime");

                lastLat=lastLocBundle.getDouble("LastLatitude");
                lastLng=lastLocBundle.getDouble("LastLongitude");
                lastAltitude=lastLocBundle.getDouble("LastAltitude");
                lastBearing=lastLocBundle.getFloat("LastBearing");
                lastBearingDegree=lastLocBundle.getFloat("LastBearingDegree");
                lastLatLng=lastLocBundle.getParcelable("LastLocation");
                workerLocTime=lastLocBundle.getLong("LastTime");

                MarkerOptions options = new MarkerOptions();

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(lastLat,
                            lastLng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);

                options.snippet(cityName + "," + stateName);



                IconGenerator iconFactory = new IconGenerator(this);
                iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
                mLastUpdateTime="last updated:";
                options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mLastUpdateTime)));
                options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
                LatLng workerLatLng = new LatLng(lastLat, lastLng);

                options.position(workerLatLng);
                Marker mapMarker = googleMap.addMarker(options);

                mapMarker.setTitle("Worker @:"+workerLocTime);
                Log.d(TAG, "Worker @.............................");
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(workerLatLng,
                        13));

            }


            if (mListener != null) {


                long atTime = mCurrentLocation.getTime();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
                adminLocation.setLatitude(adminPosition.latitude);
                adminLocation.setLongitude(adminPosition.longitude);
                adminLocation.setAccuracy(100);
                mListener.onLocationChanged(adminLocation);

                adminLng = adminLocation.getLongitude();
                adminLat = adminLocation.getLatitude();
                LatLng latLng = new LatLng(adminLat, adminLng);

                CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(adminPosition);

                CameraUpdate updateZoom = CameraUpdateFactory.zoomTo(6);

                googleMap.moveCamera(updatePosition);

                googleMap.animateCamera(updateZoom);
                LatLng workerLatLng = new LatLng(lastLat, lastLng);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latLng);
                builder.include(workerLatLng);
                LatLngBounds bounds = builder.build();
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                Routing routing = new Routing.Builder()
                        .travelMode(Routing.TravelMode.DRIVING)
                        .withListener(this)
                        .waypoints(latLng, workerLatLng)
                        .build();
                routing.execute();
            }

        }
        catch (Exception e)
        {
            Toast.makeText(TrackWorkersAct.this, "Unable to Route", Toast.LENGTH_SHORT).show();
        }
    }

    private void getRoutingPathWalking()
    {
        try
        {
            lastLocBundle=getIntent().getExtras();
            if(lastLocBundle !=null){
                previousAltitude=lastLocBundle.getDouble("PreviousAltitude");
                previousLng=lastLocBundle.getDouble("PreviousLongitude");
                previousLat=lastLocBundle.getDouble("PreviousLatitude");
                previousBearing=lastLocBundle.getFloat("PreviousBearing");
                previousBearingDegree=lastLocBundle.getFloat("PreviousBearingDegree");
                previousLatLng=lastLocBundle.getParcelable("PreviousLocation");
                workerPreviousLocTime=lastLocBundle.getLong("PreviousTime");

                lastLat=lastLocBundle.getDouble("LastLatitude");
                lastLng=lastLocBundle.getDouble("LastLongitude");
                lastAltitude=lastLocBundle.getDouble("LastAltitude");
                lastBearing=lastLocBundle.getFloat("LastBearing");
                lastBearingDegree=lastLocBundle.getFloat("LastBearingDegree");
                lastLatLng=lastLocBundle.getParcelable("LastLocation");
                workerLocTime=lastLocBundle.getLong("LastTime");

                MarkerOptions options = new MarkerOptions();

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(lastLat,
                            lastLng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);

                options.snippet(cityName + "," + stateName);



                IconGenerator iconFactory = new IconGenerator(this);
                iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
                mLastUpdateTime="last updated:";
                options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mLastUpdateTime)));
                options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
                LatLng workerLatLng = new LatLng(lastLat, lastLng);

                options.position(workerLatLng);
                Marker mapMarker = googleMap.addMarker(options);

                mapMarker.setTitle("Worker @:"+workerLocTime);
                Log.d(TAG, "Worker @.............................");
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(workerLatLng,
                        13));

            }


            if (mListener != null) {


                long atTime = mCurrentLocation.getTime();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
                adminLocation.setLatitude(adminPosition.latitude);
                adminLocation.setLongitude(adminPosition.longitude);
                adminLocation.setAccuracy(100);
                mListener.onLocationChanged(adminLocation);

                adminLng = adminLocation.getLongitude();
                adminLat = adminLocation.getLatitude();
                LatLng latLng = new LatLng(adminLat, adminLng);

                CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(adminPosition);

                CameraUpdate updateZoom = CameraUpdateFactory.zoomTo(6);

                googleMap.moveCamera(updatePosition);

                googleMap.animateCamera(updateZoom);
                LatLng workerLatLng = new LatLng(lastLat, lastLng);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latLng);
                builder.include(workerLatLng);
                LatLngBounds bounds = builder.build();
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                Routing routing = new Routing.Builder()
                        .travelMode(Routing.TravelMode.WALKING)
                        .withListener(this)
                        .waypoints(latLng, workerLatLng)
                        .build();
                routing.execute();
            }

        }
        catch (Exception e)
        {
            Toast.makeText(TrackWorkersAct.this, "Unable to Route", Toast.LENGTH_SHORT).show();
        }
    }
    private void getRoutingPathBicycle()
    {
        try
        {
            lastLocBundle=getIntent().getExtras();
            if(lastLocBundle !=null){
                previousAltitude=lastLocBundle.getDouble("PreviousAltitude");
                previousLng=lastLocBundle.getDouble("PreviousLongitude");
                previousLat=lastLocBundle.getDouble("PreviousLatitude");
                previousBearing=lastLocBundle.getFloat("PreviousBearing");
                previousBearingDegree=lastLocBundle.getFloat("PreviousBearingDegree");
                previousLatLng=lastLocBundle.getParcelable("PreviousLocation");
                workerPreviousLocTime=lastLocBundle.getLong("PreviousTime");

                lastLat=lastLocBundle.getDouble("LastLatitude");
                lastLng=lastLocBundle.getDouble("LastLongitude");
                lastAltitude=lastLocBundle.getDouble("LastAltitude");
                lastBearing=lastLocBundle.getFloat("LastBearing");
                lastBearingDegree=lastLocBundle.getFloat("LastBearingDegree");
                lastLatLng=lastLocBundle.getParcelable("LastLocation");
                workerLocTime=lastLocBundle.getLong("LastTime");

                MarkerOptions options = new MarkerOptions();

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(lastLat,
                            lastLng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);

                options.snippet(cityName + "," + stateName);



                IconGenerator iconFactory = new IconGenerator(this);
                iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
                mLastUpdateTime="last updated:";
                options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mLastUpdateTime)));
                options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());
                LatLng workerLatLng = new LatLng(lastLat, lastLng);

                options.position(workerLatLng);
                Marker mapMarker = googleMap.addMarker(options);

                mapMarker.setTitle("Worker @:"+workerLocTime);
                Log.d(TAG, "Worker @.............................");
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(workerLatLng,
                        13));

            }


            if (mListener != null) {


                long atTime = mCurrentLocation.getTime();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
                adminLocation.setLatitude(adminPosition.latitude);
                adminLocation.setLongitude(adminPosition.longitude);
                adminLocation.setAccuracy(100);
                mListener.onLocationChanged(adminLocation);

                adminLng = adminLocation.getLongitude();
                adminLat = adminLocation.getLatitude();
                LatLng latLng = new LatLng(adminLat, adminLng);

                CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(adminPosition);

                CameraUpdate updateZoom = CameraUpdateFactory.zoomTo(6);

                googleMap.moveCamera(updatePosition);

                googleMap.animateCamera(updateZoom);
                LatLng workerLatLng = new LatLng(lastLat, lastLng);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(latLng);
                builder.include(workerLatLng);
                LatLngBounds bounds = builder.build();
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
                Routing routing = new Routing.Builder()
                        .travelMode(Routing.TravelMode.BIKING)
                        .withListener(this)
                        .waypoints(latLng, workerLatLng)
                        .build();
                routing.execute();
            }

        }
        catch (Exception e)
        {
            Toast.makeText(TrackWorkersAct.this, "Unable to Route", Toast.LENGTH_SHORT).show();
        }
    }





    @SuppressLint("MissingPermission")
    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        //Techcreek= 4.8359,7.0139----4.7774,7.0134
        //Unyeada>4.52871,7.44507----4.526685, 7.4462486===4.5287100,7.4450700
        fusedLocationClient.getCurrentLocation(2,cancellationTokenSource.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitute=location.getLatitude();
                            longitute=location.getLongitude();
                            altitude=location.getAltitude();
                            bearing=location.getBearing();
                            currentLocTime=location.getTime();
                            LatLng lastLocation = new LatLng(latitute, longitute);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                bearingDegree=location.getBearingAccuracyDegrees();
                            }
                            streetViewPanorama.setPosition(lastLocation);
                            streetViewPanorama.setPosition(lastLocation, 20);
                            streetViewPanorama.setPosition(lastLocation, StreetViewSource.OUTDOOR);

                            streetViewPanorama.setPosition(lastLocation, 20, StreetViewSource.OUTDOOR);
                            long duration = 1000;
                            StreetViewPanoramaCamera camera =
                                    new StreetViewPanoramaCamera.Builder()
                                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                                            .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                                            .bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
                                            .build();
                            streetViewPanorama.animateTo(camera, duration);
                        }
                        else {

                            adminLat =4.52871;
                            adminLng =7.44507;
                            LatLng unyeada = new LatLng(adminLat, adminLng);
                            map.addMarker(new MarkerOptions().position(unyeada).title("Marker in Unyeada"));
                            map.moveCamera(CameraUpdateFactory.newLatLng(unyeada));
                            map.setLocationSource(mLocationSource);
                            map.setOnMapLongClickListener(mLocationSource);

                            LatLng userLocation = new LatLng(adminLat, adminLng);
                            streetViewPanorama.setPosition(userLocation);
                            streetViewPanorama.setPosition(userLocation, 20);
                            streetViewPanorama.setPosition(userLocation, StreetViewSource.OUTDOOR);

                            streetViewPanorama.setPosition(userLocation, 20, StreetViewSource.OUTDOOR);
                            long duration = 1000;

                            StreetViewPanoramaCamera camera =
                                    new StreetViewPanoramaCamera.Builder()
                                            .zoom(streetViewPanorama.getPanoramaCamera().zoom)
                                            .tilt(streetViewPanorama.getPanoramaCamera().tilt)
                                            .bearing(streetViewPanorama.getPanoramaCamera().bearing - 60)
                                            .build();
                            streetViewPanorama.animateTo(camera, duration);

                        }

                    }
                });

    }

    @Override
    protected void onStop() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(LongPressLocationSource.UPDATE_INTERVAL);

        mLocationRequest.setFastestInterval(LongPressLocationSource.FASTEST_UPDATE_INTERVAL);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        mLocationRequest.setMaxWaitTime(LongPressLocationSource.MAX_WAIT_TIME);
    }

    private PendingIntent getPendingIntent() {

        Intent intent = new Intent(this, LocUpdatesBReceiver.class);
        intent.setAction(LocUpdatesBReceiver.ACTION_PROCESS_UPDATES);
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //return null;
    }

    private boolean checkPermissions() {
        int fineLocationPermissionState = ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION);

        int backgroundLocationPermissionState = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            backgroundLocationPermissionState = ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }

        return (fineLocationPermissionState == PackageManager.PERMISSION_GRANTED) &&
                (backgroundLocationPermissionState == PackageManager.PERMISSION_GRANTED);
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

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
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
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                ActivityCompat.requestPermissions(TrackWorkersAct.this,
                                        new String[] {
                                                Manifest.permission.ACCESS_FINE_LOCATION,
                                                Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                                        LongPressLocationSource.REQUEST_PERMISSIONS_REQUEST_CODE);
                            }
                        }
                    })
                    .show();
        } else {
            Log.i(TAG, "Requesting permission");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(TrackWorkersAct.this,
                        new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION },
                        LongPressLocationSource.REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == LongPressLocationSource.REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");

            } else if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                    (grantResults[1] == PackageManager.PERMISSION_GRANTED)
            ) {
                // Permission was granted.
                requestLocationUpdates(null);

            } else {
                view.setVisibility(View.VISIBLE);
                Snackbar snackbar = Snackbar
                        .make(view, R.string.permission_denied_explanation, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("Our Coop. App",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });

                snackbar.show();

            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        this.sharedPreferences = sharedPreferences;
        this.s = s;
        if (s.equals(Utils.KEY_LOCATION_UPDATES_RESULT)) {
            txtLocUpdatesResultView.setText(Utils.getLocationUpdatesResult(this));
        } else if (s.equals(Utils.KEY_LOCATION_UPDATES_REQUESTED)) {
            updateButtonsState(Utils.getRequestingLocationUpdates(this));
        }
    }


    @SuppressLint("MissingPermission")
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

    public void removeLocationUpdates(View view) {
        Log.i(TAG, "Removing location updates");
        Utils.setRequestingLocationUpdates(this, false);
        mFusedLocationClient.removeLocationUpdates(getPendingIntent());
    }


    private void updateButtonsState(boolean requestingLocationUpdates) {
        if (requestingLocationUpdates) {
            mRequestUpdatesButton.setEnabled(false);
            mRemoveUpdatesButton.setEnabled(true);
        } else {
            mRequestUpdatesButton.setEnabled(true);
            mRemoveUpdatesButton.setEnabled(false);
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        @SuppressLint("MissingPermission") PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        adminLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        addMarker();

        adminLng = location.getLongitude();
        adminLat = location.getLatitude();
        LatLng latLng = new LatLng(adminLat, adminLng); //you already have this
        points.add(latLng); //added
        redrawLine();
        getRoutingPath();
    }

    private void addMarker() {
        MarkerOptions options = new MarkerOptions();
        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
        options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mLastUpdateTime)));
        options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        LatLng currentLatLng = new LatLng(adminLocation.getLatitude(), adminLocation.getLongitude());
        options.position(currentLatLng);
        Marker mapMarker = googleMap.addMarker(options);
        long atTime = adminLocation.getTime();
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
        mapMarker.setTitle("You @"+mLastUpdateTime);
        Log.d(TAG, "Marker added.............................");
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,
                13));
        Log.d(TAG, "Zoom done.............................");
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                requestingLocationUpdates);
        super.onSaveInstanceState(outState);
    }


    private void redrawLine(){

        googleMap.clear();  //clears all Markers and Polylines
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        addMarker(); //add Marker in current position
        line = googleMap.addPolyline(options); //add Polyline

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        map.setLocationSource(mLocationSource);
        map.setOnMapLongClickListener(mLocationSource);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        addMarker();
    }

}