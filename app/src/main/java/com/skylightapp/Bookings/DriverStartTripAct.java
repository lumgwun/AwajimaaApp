package com.skylightapp.Bookings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.skylightapp.R;


import java.util.Calendar;

import io.teliver.sdk.core.TLog;
import io.teliver.sdk.core.Teliver;
import io.teliver.sdk.core.TripListener;
import io.teliver.sdk.models.PushData;
import io.teliver.sdk.models.TripBuilder;
import io.teliver.sdk.models.UserBuilder;

public class DriverStartTripAct extends AppCompatActivity implements OnSuccessListener<Location>{
    private static final int GPS_REQ = 124;
    private boolean inCurrentTrip;

    private String driverName = "driver_xolo";

    private GoogleApiClient googleApiClient;
    @Override
    public void onSuccess(Location location) {
        Log.e("onSuccess::",location.getLatitude()+"");
    }

    private enum type {
        trip,
        push
    }

    private DriverApp application;

    private SwitchCompat availabilitySwitch;

    private String trackingId;

    private TextView txtSendPush;

    private EditText edtPushMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_driver);
        Teliver.identifyUser(new UserBuilder(driverName).setUserType(UserBuilder.USER_TYPE.OPERATOR).registerPush().build());
        TLog.setVisible(true);
        Calendar calendar = Calendar.getInstance();
        int month = (calendar.get(Calendar.MONTH) + 1);
        int date = calendar.get(Calendar.DATE);
        trackingId = driverName + date + "/" + month;
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable drawable = toolBar.getNavigationIcon();
        drawable.setColorFilter(ContextCompat.getColor(this, R.color.white_good), PorterDuff.Mode.SRC_ATOP);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        application = (DriverApp) getApplication();
        txtSendPush = (TextView) findViewById(R.id.txtSendPush);
        edtPushMessage = (EditText) findViewById(R.id.edtPushMessage);
        availabilitySwitch = (SwitchCompat) findViewById(R.id.availabilitySwitch);
        availabilitySwitch.setChecked(application.getBooleanInPef("switch_state"));

        availabilitySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PushData pushData = new PushData("Ataisi Nte");
                    pushData.setMessage("trip started");
                    pushData.setPayload("1");
                    TripBuilder tripBuilder = new TripBuilder(trackingId).
                            withUserPushObject(pushData);
                    Teliver.startTrip(tripBuilder.build());
                    Teliver.setTripListener(new TripListener() {

                        @Override
                        public void onTripStarted(io.teliver.sdk.models.Trip tripDetails) {
                            application.storeBooleanInPref("switch_state", true);
                            Log.d("Awajima Trip::", "onTripStarted: " + tripDetails.getTrackingId());
                            txtSendPush.setTextColor(ContextCompat.getColor(DriverStartTripAct.this, R.color.black));
                            edtPushMessage.setEnabled(true);
                            edtPushMessage.setHintTextColor(ContextCompat.getColor(DriverStartTripAct.this, R.color.white_good));

                        }

                        @Override
                        public void onLocationUpdate(Location location) {
                            Log.d("TELIVER::", "onLocationUpdate: LATLNG VALUES == " + location.getLatitude() + location.getLongitude());
                            toYourServer();
                        }

                        @Override
                        public void onTripEnded(String trackingID) {

                        }

                        @Override
                        public void onTripError(String reason) {

                        }
                    });

                } else if (!isChecked) {
                    application.storeBooleanInPref("switch_state", false);
                    PushData pushData = new PushData("Ataisi Nte");
                    pushData.setMessage("trip_stopped");
                    pushData.setPayload("0");
                    Teliver.sendEventPush(trackingId, pushData, "trip stopped");
                    Teliver.stopTrip(trackingId);
                    Teliver.setTripListener(new TripListener() {

                        @Override
                        public void onTripStarted(io.teliver.sdk.models.Trip tripDetails) {

                        }

                        @Override
                        public void onLocationUpdate(Location location) {

                        }

                        @Override
                        public void onTripEnded(String trackingID) {
                            Log.d(DriverApp.TAG, "onTripEnded:  ON TRIP ENDED == " + trackingID);
                            txtSendPush.setTextColor(ContextCompat.getColor(DriverStartTripAct.this, R.color.White));
                            edtPushMessage.setText("");
                            edtPushMessage.setHintTextColor(ContextCompat.getColor(DriverStartTripAct.this, R.color.White));
                            edtPushMessage.setEnabled(false);
                        }

                        @Override
                        public void onTripError(String reason) {

                        }
                    });
                }
            }
        });

        if (DriverApp.checkLPermission(this))
            enableGPS(this);

        txtSendPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushData pushData = new PushData("Unne");
                pushData.setMessage(edtPushMessage.getText().toString().trim() + ", " + trackingId);
                Teliver.sendEventPush(trackingId, pushData, "taxi");
                edtPushMessage.setText("");
            }
        });
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

    private void toYourServer() {
        //In the setTripListener callback onLocationUpdate will give you the location values of driver
        // in a certain interval handle  it on your wish..
        // you can get the customer location and show the nearby taxi to your
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK)
            Toast.makeText(DriverStartTripAct.this, "Gps is turned on", Toast.LENGTH_SHORT).show();
        else if (requestCode == 3 && resultCode == RESULT_CANCELED)
            finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==115){
            if (!DriverApp.isPermissionOk(grantResults)){
                Toast.makeText(this,"Location permission denied",Toast.LENGTH_SHORT).show();
                finish();
            }
            else enableGPS(this);
        }
    }

    @Override
    protected void onResume() {
        if (application.getBooleanInPef("switch_state")) {
            edtPushMessage.setHintTextColor(ContextCompat.getColor(this, R.color.white_good));
            edtPushMessage.setEnabled(true);
            txtSendPush.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
        super.onResume();
    }

    private void fromYourServer() {
        //  Based on the location values show the cabs near to the customer, you can do that by handpicking those trackingId's
// near to the customer and starting the tracking by multiple trackingId's, so that the user can view and track his nearby cabs......
    }


    private void enableGPS(OnSuccessListener<Location> listener) {
        try {
            final FusedLocationProviderClient client = LocationServices
                    .getFusedLocationProviderClient(this);
            final LocationRequest locationRequest = getLocationReq();
            LocationSettingsRequest request = new LocationSettingsRequest
                    .Builder().addLocationRequest(locationRequest)
                    .setAlwaysShow(true).build();
            Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(
                    this).checkLocationSettings(request);
            task.addOnSuccessListener(locationSettingsResponse ->
                    getMyLocation(client, locationRequest, listener));
            task.addOnFailureListener(e -> {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(this, GPS_REQ);
                    } catch (IntentSender.SendIntentException sendEx) {
                        listener.onSuccess(null);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private static void getMyLocation(final FusedLocationProviderClient client
            , LocationRequest locationRequest, final OnSuccessListener<Location> listener) {
        try {
            client.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null)
                        Log.d("TaxiDriver::","result null");
                    else {
                        Location location = locationResult.getLastLocation();
                        client.removeLocationUpdates(this);
                        listener.onSuccess(location);
                    }
                }
            }, Looper.myLooper());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private LocationRequest getLocationReq() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(2000);
        return locationRequest;

    }
    protected synchronized void buildGoogleApiClient() {
        /*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();*/
    }
}