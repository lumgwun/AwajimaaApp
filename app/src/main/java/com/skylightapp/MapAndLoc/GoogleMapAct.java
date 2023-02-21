package com.skylightapp.MapAndLoc;

import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_DWELL;
import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_ENTER;
import static com.google.android.gms.location.GeofencingRequest.INITIAL_TRIGGER_EXIT;
import static com.skylightapp.MapAndLoc.PlaceData.COLUMN_PLACE_ID;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_ADDRESS;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_LATLNG;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_LATLNGBOUNDS;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_NAME;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_PHONE;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_PROF_ID;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_REPORTID;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_TIMESTAMP;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.Gson;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.Awajima;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public class GoogleMapAct extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Constants
    public static final String TAG = GoogleMapAct.class.getSimpleName();
    private static final String CLASSTAG =
            " " + GoogleMapAct.class.getSimpleName () + " ";
    private static final String PREF_NAME = "awajima";
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private static final int PLACE_PICKER_REQUEST = 1;

    private PlaceListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private boolean mIsEnabled;
    private GoogleApiClient mClient;
    private Geofencing mGeofencing;
    private SharedPreferences userPreferences;
    private Bundle bundle;
    private int reportID;
    private String placeID;
    private CharSequence name;
    private CharSequence phoneNo;
    private Switch onOffSwitch;
    private LatLng placeLatLng;
    private LatLngBounds latLngBounds;
    private CharSequence address;
    private MarketBusiness marketBusiness;
    private long bizID;
    private DBHelper dbHelper;
    Gson gson, gson1,gson2;
    String json1, json2,userName, userPassword,selectedType, userMachine;
    Profile userProfile;
    private int profileID;
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    private Woosmap woosmap;
    String SharedPrefProfileID,dateOfToday;
    Location location;
    double latitude = 0;
    double longitude = 0;
    private int PROXIMITY_RADIUS = 5000;
    private LocationRequest request;
    List<Address> addresses;
    Geocoder geocoder;
    private CancellationTokenSource cancellationTokenSource;
    PlacesClient placesClient;
    private boolean locationPermissionGranted;
    LatLng userLocation, dest,latLng,center;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Awajima awajima;
    private FusedLocationProviderClient fusedLocationClient;
    boolean boolean_permission;
    ArrayList<Fence> fences;
    private int catIndex =0;
    private GeofencingClient geofencingClient;
    private PendingIntent pendingIntent;
    private GeofencingRequest.Builder builder;
    private EmergencyReport emergencyReport;

    CopyOnWriteArrayList<FenceEvent> events;
    private AppController.Status status;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 14;
    @SuppressLint("InlinedApi")
    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };
    private boolean permissionApproved,isChecked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_google_map);
        woosmap = Woosmap.getInstance().initializeWoosmap(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionApproved = ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;
        }
        initializeStuff();
        dbHelper = new DBHelper(this);
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
        userProfile = new Profile();
        marketBusiness= new MarketBusiness();
        emergencyReport= new EmergencyReport();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        bundle= new Bundle();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID= String.valueOf(userPreferences.getLong("PROFILE_ID", 0));
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        userName=userPreferences.getString("PROFILE_USERNAME", "");
        userPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        userMachine=userPreferences.getString("PROFILE_ROLE", "");
        profileID=userPreferences.getInt("PROFILE_ID", 0);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        refreshPlacesData(selectedType, latitude, longitude, emergencyReport);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            reportID=bundle.getInt("EMERG_REPORT_ID",0);
            emergencyReport=bundle.getParcelable("EmergencyReport");
            selectedType=bundle.getString("selectedType");

        }
        onOffSwitch = findViewById(R.id.enable_switch);
        mIsEnabled = getPreferences(MODE_PRIVATE).getBoolean(getString(R.string.setting_enabled), false);
        onOffSwitch.setChecked(mIsEnabled);
        CheckBox locationPermissions = (CheckBox) findViewById(R.id.location_permission_checkbox);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionApproved = ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;

            locationPermissions.setChecked(true);
            locationPermissions.setEnabled(false);
        }
        if(mIsEnabled=true){
            isChecked= mIsEnabled;

        }


        CheckBox ringerPermissions = (CheckBox) findViewById(R.id.ringer_permissions_checkbox);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        ringerPermissions.setChecked(true);
        ringerPermissions.setEnabled(false);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putBoolean(getString(R.string.setting_enabled), isChecked);

        editor.apply();
        try {
            if (isChecked) mGeofencing.registerAllGeofences(reportID);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        /*onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = userPreferences.edit();
                editor.putBoolean(getString(R.string.setting_enabled), isChecked);
                mIsEnabled = isChecked;
                editor.apply();
                if (isChecked) mGeofencing.registerAllGeofences(reportID);
                else mGeofencing.unRegisterAllGeofences();
            }

        });*/


        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(GoogleMapAct.this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();

        mGeofencing = new Geofencing(this, mClient);

    }
    private void initializeStuff() {
        //createLocationRequest();
        com.google.android.libraries.places.api.Places.initialize(this, BuildConfig.MAP_API);
        placesClient = com.google.android.libraries.places.api.Places.createClient(this);
        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
        locationRequest = LocationRequest.create();

    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        refreshPlacesData(selectedType, latitude, longitude, emergencyReport);
        Log.i(TAG, "API Client Connection Successful!");
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "API Client Connection Suspended!");
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.e(TAG, "API Client Connection Failed!");
    }

    @SuppressLint("Range")
    public void refreshPlacesData(String selectedType, double latitude, double longitude, EmergencyReport emergencyReport) {
        bundle= new Bundle();
        bundle=getIntent().getExtras();
        if(bundle !=null){
            reportID=bundle.getInt("EMERG_REPORT_ID",0);
            this.emergencyReport =bundle.getParcelable("EmergencyReport");
            this.selectedType =bundle.getString("selectedType");

        }
        Uri uri = PlaceContract.PlaceEntry.CONTENT_URI;
        Cursor data = getContentResolver().query(uri, null, null, null, null);

        if (data == null || data.getCount() == 0) return;
        List<String> guids = new ArrayList<String>();
        while (data.moveToNext()) {
            guids.add(data.getString(data.getColumnIndex(COLUMN_PLACE_ID)));
        }
        PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mClient,
                guids.toArray(new String[guids.size()]));
        placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer places) {
                mAdapter.swapPlaces(places);
                mGeofencing.updateGeofencesList(places);
                mRecyclerView = (RecyclerView) findViewById(R.id.places_list_recycler_view);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(GoogleMapAct.this));
                mAdapter = new PlaceListAdapter(GoogleMapAct.this, places);
                mRecyclerView.setAdapter(mAdapter);
                if (mIsEnabled) mGeofencing.registerAllGeofences();
            }
        });
    }


    public void onAddPlaceButtonClicked(View view) throws GooglePlayServicesNotAvailableException {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, getString(R.string.need_location_permission_message), Toast.LENGTH_LONG).show();
            return;
        }
        try {

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent i = builder.build(this);
            startActivityForResult(i, PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (Exception e) {
            Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:SS", Locale.getDefault());
        bundle= new Bundle();
        bundle=getIntent().getExtras();
        if(bundle !=null){
            reportID=bundle.getInt("EMERG_REPORT_ID",0);
            emergencyReport=bundle.getParcelable("EmergencyReport");
            selectedType=bundle.getString("selectedType");

        }
        dateOfToday =dateFormat.format(calendar.getTime());
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);
            if (place == null) {
                Log.i(TAG, "No place selected");
                return;
            }
            setResult(RESULT_OK);

            if(place !=null){
                placeID = place.getId();
                phoneNo=place.getPhoneNumber();
                placeLatLng=place.getLatLng();
                address=place.getAddress();
                latLngBounds=place.getViewport();
                name=place.getName();

            }

            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PLACE_ID, placeID);
            contentValues.put(PLACE_ENTRY_PROF_ID, placeID);
            contentValues.put(PLACE_ENTRY_REPORTID, reportID);
            contentValues.put(PLACE_ENTRY_NAME, String.valueOf(name));
            contentValues.put(PLACE_ENTRY_ADDRESS, String.valueOf(address));
            contentValues.put(PLACE_ENTRY_PHONE, String.valueOf(phoneNo));
            contentValues.put(PLACE_ENTRY_LATLNGBOUNDS, String.valueOf(latLngBounds));
            contentValues.put(PLACE_ENTRY_LATLNG, String.valueOf(placeLatLng));
            contentValues.put(PLACE_ENTRY_TIMESTAMP, dateOfToday);
            getContentResolver().insert(PlaceContract.PlaceEntry.CONTENT_URI, contentValues);
            addGeofence(selectedType,latitude,longitude,emergencyReport);
            //enableLocUpdates(reportID,reportIDF);

            // Get live data information
            refreshPlacesData(selectedType,latitude,longitude,emergencyReport);
        }
    }
    @SuppressLint("MissingPermission")
    private void addGeofence(String selectedType, double latitude, double longitude, EmergencyReport emergencyReport) {
        Fence fence2 = Fence.buildCircularFence(selectedType, new LatLng(latitude, longitude), 100f);

        CircularFence circularFence = (CircularFence) CircularFence.buildCircularFence(selectedType, placeLatLng,100f);

        emergencyReport.addFence(fence2);
        emergencyReport.addCircularFence(circularFence);
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient (this);
        }

        if (isHasBackgroundLocationPermission ()) {
            if (! isGeofencingInitialised ()) {
                geofencingClient = LocationServices.getGeofencingClient (this);
                Intent intent = new Intent (this, GeofenceBroadcastReceiver.class);
                pendingIntent =
                        PendingIntent.getBroadcast (this, 0, intent,
                                PendingIntent.FLAG_CANCEL_CURRENT);
            }
        }
         builder = new GeofencingRequest.Builder ()
                .setInitialTrigger (INITIAL_TRIGGER_DWELL | INITIAL_TRIGGER_ENTER |
                        INITIAL_TRIGGER_EXIT);
        builder.addGeofence (fence2.createGeofence ());

        for (Fence fence : fences) {
            builder.addGeofence (fence2.createGeofence ());
        }
        geofencingClient.addGeofences (builder.build (), pendingIntent)
                .addOnSuccessListener (this, new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess (Void aVoid)
                    {
                        handleAddingFenceSucceeded ();
                    }
                })
                .addOnFailureListener (this, new OnFailureListener()
                {
                    @Override
                    public void onFailure (@NotNull Exception e)
                    {
                        handleAddingFenceFailed (e);
                    }
                });
    }
    public boolean isGeofencingInitialised () { return geofencingClient != null; }
    private void handleAddingFenceFailed (Exception e)
    {
        String logMsg = CLASSTAG + "Adding fence failed: " + e.getLocalizedMessage ();
        //Log.w (Constants.LOGTAG, logMsg, e);
        String msg;
        Resources res = getResources ();
        if (e instanceof ApiException)
        {
            int code = ((ApiException) e).getStatusCode ();
            switch (code)
            {
                case 1004:  // GeofenceStatusCodes.GEOFENCE_INSUFFICIENT_LOCATION_PERMISSION
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_insufficient_location_permission) );
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_not_available) );
                    break;
                case 1005: //GeofenceStatusCodes.GEOFENCE_REQUEST_TOO_FREQUENT
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_request_too_frequent) );
                    break;
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_too_many_geofences) );
                    break;
                case GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            res.getString (R.string.geofence_too_many_pending_intents) );
                    break;
                default:
                    msg = res.getString ( R.string.failed_with_error_code, code,
                            e.getLocalizedMessage () );
            }
        } else
        {
            msg = res.getString ( R.string.failed_with_unexpected_error,
                    e.getLocalizedMessage () );
        }
        createAddingFenceFailedDialog (msg);
        //addingFenceFailedDialog.show ();
    }

    private void handleAddingFenceSucceeded () {
        //Log.i (Constants.LOGTAG, CLASSTAG + "Fence added.");
        status = AppController.Status.FENCES_ADDED;
        //DisplayLocationActivity.getHandler ().handleMessage (new Message());
    }
    private void handleCloseAddingFenceFailedDialog () {
        //Log.v (Constants.LOGTAG, CLASSTAG + "handleCloseAddingFenceFailedDialog called");
        //addingFenceFailedDialog = null;
        Activity activity = this;
        //dialogActivity = null;
        status = AppController.Status.FENCES_FAILED;
        // Force a screen refresh on DisplayLocationActivity
        //DisplayLocationActivity.getHandler ().handleMessage (new Message ());
    }
    private void createAddingFenceFailedDialog (String msg) {
        //Log.v (Constants.LOGTAG, CLASSTAG + ">createAddingFenceFailedDialog");
        /*addingFenceFailedDialog = new AlertDialog.Builder (this)
                .setTitle (R.string.adding_fence_failed_title)
                .setMessage (msg)
                .setPositiveButton (R.string.ok, new DialogInterface.OnClickListener ()
                {
                    @Override
                    public void onClick (DialogInterface dialog, int which)
                    {
                        handleCloseAddingFenceFailedDialog ();
                    }
                })
                .create ();*/
    }


    private boolean isHasBackgroundLocationPermission() {
        return false;
    }



    @Override
    public void onResume() {
        super.onResume();


        CheckBox locationPermissions = (CheckBox) findViewById(R.id.location_permission_checkbox);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionApproved = ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                    == PackageManager.PERMISSION_GRANTED;

            locationPermissions.setChecked(true);
            locationPermissions.setEnabled(false);
        }

        CheckBox ringerPermissions = (CheckBox) findViewById(R.id.ringer_permissions_checkbox);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        ringerPermissions.setChecked(true);
        ringerPermissions.setEnabled(false);
    }

    public void onRingerPermissionsClicked(View view) {
        Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
        startActivity(intent);
    }

    public void onLocationPermissionClicked(View view) {
        ActivityCompat.requestPermissions(GoogleMapAct.this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_FINE_LOCATION);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
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
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}