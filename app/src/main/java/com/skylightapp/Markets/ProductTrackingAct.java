package com.skylightapp.Markets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.teliver.sdk.core.Teliver;
import com.teliver.sdk.core.TrackingListener;
import com.teliver.sdk.models.MarkerOption;
import com.teliver.sdk.models.TLocation;
import com.teliver.sdk.models.TrackingBuilder;
import com.teliver.sdk.models.UserBuilder;

import net.qiujuer.genius.ui.widget.ImageView;

import java.util.Objects;

public class ProductTrackingAct extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks{
    private AppCompatTextView txtPayment, txtInKitchen, txtOnRoute, txtDeliverHint, txtDelivered;

    private ImageView imgOne, imgTwo, imgThree, imgFour, imgViewOne, imgViewtwo, imgViewThree;

    private GoogleMap googleMap;

    private GoogleApiClient googleApiClient;

    private LinearLayout layoutDelivered;

    private RelativeLayout layoutTracking;

    private AlertDialog dialog;

    private String track = "Awajima_101";
    private PrefManager prefManager;
    private Profile userProfile;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
    private MarketBusiness marketBusiness;
    private Customer customer;
    String SharedPrefUserPassword,SharedPrefUserMachine,senderFullNames,phoneNo,SharedPrefUserName,adminName,sender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_stocks_tracking);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        customer=new Customer();
        marketBusiness=new MarketBusiness();
        userProfile =new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);

        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("message"));
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarT);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Teliver.identifyUser(new UserBuilder(SharedPrefUserName).
                setUserType(UserBuilder.USER_TYPE.CONSUMER).registerPush().build());

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.tracking_steps, null);
        view.setPadding(8, 0, 8, 0);

        txtPayment = view.findViewById(R.id.txtpayment);
        txtInKitchen = view.findViewById(R.id.txtInKitchen);
        txtOnRoute = view.findViewById(R.id.initTeliver);
        txtOnRoute.setOnClickListener(this);
        txtDeliverHint = view.findViewById(R.id.txtDeliveryHint);
        txtDelivered = view.findViewById(R.id.txtDelivered);
        imgOne = view.findViewById(R.id.imgOne);
        imgTwo = view.findViewById(R.id.imgTwo);
        imgThree = view.findViewById(R.id.imgThree);
        imgFour = view.findViewById(R.id.imgFour);
        imgViewOne = view.findViewById(R.id.viewOne);
        imgViewtwo = view.findViewById(R.id.viewTwo);
        imgViewThree = view.findViewById(R.id.viewThree);
        layoutDelivered = view.findViewById(R.id.layoutdelivered);
        layoutTracking = view.findViewById(R.id.layoutTracker);

        alert.setView(view);

        prefManager = new PrefManager(this);
        alert.setCancelable(false);
        dialog = alert.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                    finish();
                return false;
            }
        });

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.BOTTOM;
        params.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(params);


        dialog.show();

        final Intent intent = getIntent();

        if (intent != null) {
            String msg = getIntent().getStringExtra("msg");
            setStates(msg);
        }
        txtOnRoute.setEnabled(true);
    }

    private void maintainState() {
        if (prefManager.getBooleanInPef("STEP_ONE")) {
            txtPayment.setTextColor(ContextCompat.getColor(this, R.color.green_dark));
            setCompletedIcon(imgOne, imgViewOne);
        }
        if (prefManager.getBooleanInPef("STEP_TWO")) {
            setCompletedText(txtPayment, txtInKitchen, imgTwo, imgViewtwo);
            setCompletedText(txtPayment, txtDeliverHint, imgTwo, imgViewtwo);
            setCompletedIcon(imgTwo, imgViewtwo);
            txtOnRoute.setEnabled(true);
        }
        if (prefManager.getBooleanInPef("STEP_THREE")) {
            setCompletedText(txtInKitchen, txtOnRoute, imgThree, imgViewThree);
            setCompletedText(txtDeliverHint, txtOnRoute, imgThree, imgViewThree);
            setCompletedIcon(imgThree, imgViewThree);
        }
        if (prefManager.getBooleanInPef("STEP_FOUR")) {
            storeStepCompletion("STEP_FOUR");
            layoutTracking.setVisibility(View.GONE);
            layoutDelivered.setVisibility(View.VISIBLE);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.CENTER;
            window.setAttributes(params);
        }
        if (prefManager.getBooleanInPef("STEP_ONE_RUNNING")) {
            txtPayment.setTextColor(ContextCompat.getColor(this, R.color.orange));
            changeTintMode(imgOne, imgViewOne);
        }
        if (prefManager.getBooleanInPef("STEP_TWO_RUNNING")) {
            txtInKitchen.setTextColor(ContextCompat.getColor(this, R.color.orange));
            txtDeliverHint.setTextColor(ContextCompat.getColor(this, R.color.orange));
            changeTintMode(imgTwo, imgViewtwo);
        }
        if (prefManager.getBooleanInPef("STEP_THREE_RUNNING")) {
            changeTintMode(imgThree, imgViewThree);
            txtOnRoute.setTextColor(ContextCompat.getColor(this, R.color.orange));
        }
    }

    private void setCompletedText(AppCompatTextView txtNow, AppCompatTextView txtNext, ImageView img, ImageView imgViewLine) {
        txtNow.setTextColor(ContextCompat.getColor(this, R.color.green));
        txtNext.setTextColor(ContextCompat.getColor(this, R.color.green));
        img.setColorFilter(ContextCompat.getColor(this, R.color.green));
        imgViewLine.setColorFilter(ContextCompat.getColor(this, R.color.green));
    }

    private void setCompletedIcon(ImageView img, ImageView imgViewLine) {
        img.setColorFilter(ContextCompat.getColor(this, R.color.green));
        imgViewLine.setColorFilter(ContextCompat.getColor(this, R.color.green));
    }

    private void changeTintMode(ImageView imgStatus, ImageView imgView) {
        imgStatus.setColorFilter(ContextCompat.getColor(this, R.color.orange));
        imgView.setColorFilter(ContextCompat.getColor(this, R.color.orange));
    }

    public void setStates(String message) {
        if (message != null) {
            switch (message) {
                case "1":
                    txtPayment.setTextColor(ContextCompat.getColor(this, R.color.green));
                    setCompletedIcon(imgOne, imgViewOne);
                    prefManager.storeBooleanInPref("STEP_ONE_RUNNING", false);
                    changeColors(txtPayment, txtInKitchen, imgTwo, imgViewtwo);
                    changeColors(txtPayment, txtDeliverHint, imgTwo, imgViewtwo);
                    changeTintMode(imgTwo, imgViewtwo);
                    storeStepCompletion("STEP_ONE");
                    storeStepCompletion("STEP_TWO_RUNNING");
                    setCompletedIcon(imgOne, imgViewOne);
                    break;
                case "2":
                    txtOnRoute.setEnabled(true);
                    changeColors(txtInKitchen, txtOnRoute, imgThree, imgViewThree);
                    changeColors(txtDeliverHint, txtOnRoute, imgThree, imgViewThree);
                    changeTintMode(imgThree, imgViewThree);
                    storeStepCompletion("STEP_TWO");
                    storeStepCompletion("STEP_THREE_RUNNING");
                    prefManager.storeBooleanInPref("STEP_TWO_RUNNING", false);
                    setCompletedIcon(imgTwo, imgViewtwo);
                    break;
                case "3":
                    setCompletedIcon(imgThree, imgViewThree);
                    storeStepCompletion("STEP_THREE");
                    prefManager.storeBooleanInPref("STEP_THREE_RUNNING", false);
                    storeStepCompletion("STEP_FOUR");
                    layoutTracking.setVisibility(View.GONE);
                    layoutDelivered.setVisibility(View.VISIBLE);
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams params = window.getAttributes();
                    params.gravity = Gravity.CENTER;
                    window.setAttributes(params);
                    break;
                default:
                    break;
            }
        }
    }


    private void changeColors(AppCompatTextView txtView, AppCompatTextView txtNext, ImageView img, ImageView imgView) {
        txtView.setTextColor(ContextCompat.getColor(this, R.color.green));
        txtNext.setTextColor(ContextCompat.getColor(this, R.color.orange));
        img.setColorFilter(ContextCompat.getColor(this, R.color.orange));
        imgView.setColorFilter(ContextCompat.getColor(this, R.color.orange));

    }

    private void storeStepCompletion(String step) {
        prefManager.storeBooleanInPref(step, true);
    }

    public void startTracking(String track) {

        TrackingBuilder builder = new TrackingBuilder(new MarkerOption(track)).withListener(new TrackingListener() {
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
        });
        Teliver.startTracking(builder.build());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.initTeliver:
                startTracking(track);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 19));
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    protected void onResume() {
        maintainState();
        if (prefManager.getBooleanInPef("STEP_FOUR"))
            prefManager.deletePreference();
        if (dialog != null)
            dialog.show();
        super.onResume();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setStates(intent.getStringExtra("msg"));
        }
    };

    @Override
    protected void onPause() {
        if (dialog != null)
            dialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (dialog != null)
            dialog.dismiss();
        super.onDestroy();
    }
}