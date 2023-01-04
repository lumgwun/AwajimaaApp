package com.skylightapp.MapAndLoc;

import static com.skylightapp.MapAndLoc.PlaceData.COLUMN_PLACE_ID;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_ADDRESS;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_LATLNG;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_LATLNGBOUNDS;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_NAME;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_PHONE;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_PROF_ID;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_TIMESTAMP;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.webgeoservices.woosmapgeofencing.Woosmap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class GoogleMapAct extends AppCompatActivity implements ConnectionCallbacks, OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    // Constants
    public static final String TAG = GoogleMapAct.class.getSimpleName();
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
    String json1, json2,userName, userPassword, userMachine;
    Profile userProfile;
    private int profileID;
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    private Woosmap woosmap;
    String SharedPrefProfileID,dateOfToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_google_map);
        woosmap = Woosmap.getInstance().initializeWoosmap(this);
        dbHelper = new DBHelper(this);
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
        userProfile = new Profile();
        marketBusiness= new MarketBusiness();

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
        mRecyclerView = (RecyclerView) findViewById(R.id.places_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PlaceListAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            reportID=bundle.getInt("EMERGENCY_LOCID",0);

        }

        onOffSwitch = findViewById(R.id.enable_switch);
        mIsEnabled = getPreferences(MODE_PRIVATE).getBoolean(getString(R.string.setting_enabled), false);
        onOffSwitch.setChecked(mIsEnabled);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = userPreferences.edit();
                editor.putBoolean(getString(R.string.setting_enabled), isChecked);
                mIsEnabled = isChecked;
                editor.apply();
                if (isChecked) mGeofencing.registerAllGeofences();
                else mGeofencing.unRegisterAllGeofences();
            }

        });


        mClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(GoogleMapAct.this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, this)
                .build();

        mGeofencing = new Geofencing(this, mClient);

    }

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        refreshPlacesData();
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
    public void refreshPlacesData() {
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
            contentValues.put(PLACE_ENTRY_PROF_ID, profileID);
            contentValues.put(PLACE_ENTRY_NAME, String.valueOf(name));
            contentValues.put(PLACE_ENTRY_ADDRESS, String.valueOf(address));
            contentValues.put(PLACE_ENTRY_PHONE, String.valueOf(phoneNo));
            contentValues.put(PLACE_ENTRY_LATLNGBOUNDS, String.valueOf(latLngBounds));
            contentValues.put(PLACE_ENTRY_LATLNG, String.valueOf(placeLatLng));
            contentValues.put(PLACE_ENTRY_TIMESTAMP, dateOfToday);
            getContentResolver().insert(PlaceContract.PlaceEntry.CONTENT_URI, contentValues);

            // Get live data information
            refreshPlacesData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Initialize location permissions checkbox
        CheckBox locationPermissions = (CheckBox) findViewById(R.id.location_permission_checkbox);
        if (ActivityCompat.checkSelfPermission(GoogleMapAct.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissions.setChecked(false);
        } else {
            locationPermissions.setChecked(true);
            locationPermissions.setEnabled(false);
        }

        // Initialize ringer permissions checkbox
        CheckBox ringerPermissions = (CheckBox) findViewById(R.id.ringer_permissions_checkbox);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Check if the API supports such permission change and check if permission is granted
        if (!nm.isNotificationPolicyAccessGranted())
            ringerPermissions.setChecked(false);
        else {
            ringerPermissions.setChecked(true);
            ringerPermissions.setEnabled(false);
        }
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
}