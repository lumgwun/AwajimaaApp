package com.skylightapp.Bookings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MapAndLoc.GPSTracker;
import com.skylightapp.R;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import io.teliver.sdk.core.TLog;
import io.teliver.sdk.core.Teliver;
import io.teliver.sdk.core.TrackingListener;
import io.teliver.sdk.models.MarkerOption;
import io.teliver.sdk.models.TLocation;
import io.teliver.sdk.models.TrackingBuilder;
import io.teliver.sdk.models.UserBuilder;

public class TrackUserAct extends AppCompatActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Object>, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private GoogleMap mMap;
    private MapFragment mapFragment;
    private GoogleApiClient googleApiClient;
    private Location mLastLocation;
    private LocationRequest request;
    View mapView;
    private boolean mRequestingLocationUpdates;
    CameraUpdate cLocation;
    double latitude,longitude;
    Marker now;

    Geocoder geocoder;
    List<Address> addresses;
    private FloatingActionButton locFab;
    //private String userName = "LS";
    SharedPreferences userPreferences;
    Gson gson, gson1;
    Profile userProfile, lastProfileUsed;
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
    private Calendar calendar;
    int PERMISSION_ALL33 = 345;
    private boolean locationPermissionGranted;
    private GoogleMap mGoogleMap;
    private  Bundle userBundle;

    private MapApp application;

    private GoogleMap googleMap;

    private RelativeLayout relativeLayout;
    private int sharedPProfileID;

    private List<String> trackingId = new ArrayList<>();
    String json, sharedPUserName, sharedPRole,sharedPMachine,sharedPSurName,sharedPFirstName;
    private GPSTracker mGpsTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_find_driver);
        setTitle("Get A Driver");
        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById( R.id.map );
        mapView = mapFragment.getView();
        mapFragment.getMapAsync( this );
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        sharedPMachine=userPreferences.getString("Machine","");
        sharedPSurName=userPreferences.getString("PROFILE_SURNAME","");
        sharedPFirstName=userPreferences.getString("PROFILE_FIRSTNAME","");
        sharedPRole=userPreferences.getString("PROFILE_ROLE","");
        sharedPUserName=userPreferences.getString("PROFILE_USERNAME","");
        sharedPProfileID=userPreferences.getInt("PROFILE_ID",0);

        TLog.setVisible(true);
        Teliver.identifyUser(new UserBuilder(sharedPUserName).setUserType(UserBuilder.USER_TYPE.CONSUMER).registerPush().build());


    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        application = (MapApp) getApplication();
        relativeLayout = (RelativeLayout) findViewById(R.id.rel_Find_D);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("tripId"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("payload").equals("0"))
                Teliver.stopTracking(intent.getStringExtra(MapApp.TRACKING_ID));
            else if (intent.getStringExtra("payload").equals("1")) {
                trackingId.add(intent.getStringExtra(MapApp.TRACKING_ID));
                startTracking(trackingId);
            }
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    private void startTracking(List<String> trackingId) {
        List<MarkerOption> markerOptionList = new ArrayList<>();
        String[] ids = trackingId.toArray(new String[trackingId.size()]);
        for (String id : ids) {
            MarkerOption option = new MarkerOption(id);
            option.setMarkerTitle("Hi There");
            markerOptionList.add(option);
        }

        Teliver.startTracking(new TrackingBuilder(markerOptionList).withYourMap(googleMap).withListener(new TrackingListener() {
            @Override
            public void onTrackingStarted(String trackingId) {

            }

            @Override
            public void onLocationUpdate(String trackingId, TLocation location) {

            }

            @Override
            public void onTrackingEnded(String trackingId) {

            }

            @Override
            public void onTrackingError(String reason) {

            }
        }).build());
    }

    public void showToast(View view) {
        Snackbar.make(relativeLayout,"Driver LiveTracking", Snackbar.LENGTH_LONG).show();
    }

    @NonNull
    @NotNull
    @Override
    public Loader<Object> onCreateLoader(int id, @Nullable @org.jetbrains.annotations.Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull @NotNull Loader<Object> loader, Object data) {

    }

    @Override
    public void onLoaderReset(@NonNull @NotNull Loader<Object> loader) {

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