package com.skylightapp.Wearable;

import static com.skylightapp.Bookings.BookingConstant.NO_REQUEST;
import static com.skylightapp.Bookings.BookingConstant.URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.wear.ambient.AmbientModeSupport;
import androidx.wear.widget.SwipeDismissFrameLayout;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.skylightapp.Bookings.BookingConstant;
import com.skylightapp.Bookings.BookingHomeAct;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.Classes.AsyncListener;
import com.skylightapp.Classes.ParseContent;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MapAndLoc.ApiUtil;
import com.skylightapp.MapAndLoc.MyMapCallBack;
import com.skylightapp.R;

import java.util.HashMap;

public class WearMainAct extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnCameraMoveListener, AmbientModeSupport.AmbientCallbackProvider, Response.ErrorListener, AsyncListener {
    private SupportMapFragment mapFragment;
    private SwipeDismissFrameLayout mapFrameLayout;
    SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    DBHelper dbHelper;
    Gson gson, gson1, gson2;
    String json, json1, json2, userName;
    Profile userProfile;
    private AQuery aQuery;
    private LocationManager manager;
    private ImageOptions imageOptions;
    private RequestQueue requestQueue;
    private MapView mGoogleMapView;

    private GoogleMap googlemap;
    private float zoom = 10;
    private double latitude = 23.10485;
    private double longitude = 113.388975;
    private boolean mIsAmapDisplay = true;
    private boolean mIsAuto = true;
    private String formatAddress = "";
    private AlphaAnimation anappear;
    private AlphaAnimation andisappear;
    private PrefManager pHelper;
    private boolean isDataRecieved = false, isRecieverRegistered = false,
            isNetDialogShowing = false, isGpsDialogShowing = false;
    private AlertDialog internetDialog, gpsAlertDialog, locationAlertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wear_main);
        userProfile = new Profile();
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        userName = userPreferences.getString("PROFILE_USERNAME", "");
        AmbientModeSupport.AmbientController controller = AmbientModeSupport.attach(this);
        Log.d(WearMainAct.class.getSimpleName(), "Is ambient enabled: " + controller.isAmbient());

        mapFrameLayout = (SwipeDismissFrameLayout) findViewById(R.id.map_container);
        requestQueue = Volley.newRequestQueue(this);
        mapFrameLayout.addCallback(new SwipeDismissFrameLayout.Callback() {
            @Override
            public void onDismissed(SwipeDismissFrameLayout layout) {
                onBackPressed();
            }
        });

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        imageOptions = new ImageOptions();
        imageOptions.memCache = true;
        imageOptions.fileCache = true;
        imageOptions.targetWidth = 200;
        imageOptions.fallback = R.drawable.ic_admin_user;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (mAmapView != null) {
            mAmapView.onPause();
        }*/
        if (mGoogleMapView != null) {
            try {
                mGoogleMapView.onPause();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*if (mAmapView != null) {
            mAmapView.onSaveInstanceState(outState);
        }*/
        if (mGoogleMapView != null) {
            try {
                mGoogleMapView.onSaveInstanceState(outState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public BroadcastReceiver GpsChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final LocationManager manager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // do something
                removeGpsDialog();
            } else {
                // do something else
                if (isGpsDialogShowing) {
                    return;
                }
                ShowGpsDialog();
            }
        }
    };
    public BroadcastReceiver internetConnectionReciever = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo activeWIFIInfo = connectivityManager
                    .getNetworkInfo(connectivityManager.TYPE_WIFI);

            if (activeWIFIInfo.isConnected() || activeNetInfo.isConnected()) {
                removeInternetDialog();
            } else {
                if (isNetDialogShowing) {
                    return;
                }
                showInternetDialog();
            }
        }
    };

    private void ShowGpsDialog() {
        UtilsExtra.removeCustomProgressDialog();
        isGpsDialogShowing = true;
        AlertDialog.Builder gpsBuilder = new AlertDialog.Builder(
                WearMainAct.this);
        gpsBuilder.setCancelable(false);
        gpsBuilder
                .setTitle(getString(R.string.need_gps))
                .setMessage(getString(R.string.no_gps))
                .setPositiveButton(getString(R.string.enable_gps),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                Intent intent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                                removeGpsDialog();
                            }
                        })

                .setNegativeButton(getString(R.string.exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                removeGpsDialog();
                                finish();
                            }
                        });
        gpsAlertDialog = gpsBuilder.create();
        gpsAlertDialog.show();
    }
    private void removeGpsDialog() {
        if (gpsAlertDialog != null && gpsAlertDialog.isShowing()) {
            gpsAlertDialog.dismiss();
            isGpsDialogShowing = false;
            gpsAlertDialog = null;

        }
    }

    private void removeInternetDialog() {
        if (internetDialog != null && internetDialog.isShowing()) {
            internetDialog.dismiss();
            isNetDialogShowing = false;
            internetDialog = null;

        }
    }

    private void showInternetDialog() {
        UtilsExtra.removeCustomProgressDialog();
        isNetDialogShowing = true;
        AlertDialog.Builder internetBuilder = new AlertDialog.Builder(
                WearMainAct.this);
        internetBuilder.setCancelable(false);
        internetBuilder
                .setTitle(getString(R.string.no_internet_connection))
                .setMessage(getString(R.string.no_internet_connection))
                .setPositiveButton(getString(R.string.dialog_enable),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                Intent intent = new Intent(
                                        android.provider.Settings.ACTION_SETTINGS);
                                startActivity(intent);
                                removeInternetDialog();
                            }
                        })
                .setNeutralButton(getString(R.string.dialog_enable_wifi),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // User pressed Cancel button. Write
                                // Logic Here
                                startActivity(new Intent(
                                        Settings.ACTION_WIFI_SETTINGS));
                                removeInternetDialog();

                            }
                        })
                .setNegativeButton(getString(R.string.exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                removeInternetDialog();
                                finish();
                            }
                        });
        internetDialog = internetBuilder.create();
        internetDialog.show();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        //Mint.closeSession(this);
        if (isRecieverRegistered) {
            unregisterReceiver(internetConnectionReciever);
            unregisterReceiver(GpsChangeReceiver);
        }

        if (mGoogleMapView != null) {
            try {
                mGoogleMapView.onDestroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private boolean isInArea(double latitude, double longtitude) {
        if ((latitude > 3.837031) && (latitude < 53.563624)
                && (longtitude < 135.095670) && (longtitude > 73.502355)) {
            return true;
        }
        return false;
    }
    public void showLocationOffDialog() {

        AlertDialog.Builder gpsBuilder = new AlertDialog.Builder(
                WearMainAct.this);
        gpsBuilder.setCancelable(false);
        gpsBuilder
                .setTitle(getString(R.string.loc_serv_imp))
                .setMessage(getString(R.string.no_loc_service))
                .setPositiveButton(
                        getString(R.string.enable_loc_service),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // continue with delete
                                dialog.dismiss();
                                Intent viewIntent = new Intent(
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(viewIntent);

                            }
                        })

                .setNegativeButton(getString(R.string.exit),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                                dialog.dismiss();
                                finish();
                            }
                        });
        locationAlertDialog = gpsBuilder.create();
        locationAlertDialog.show();
    }


    @Override
    protected void onResume() {
        pHelper = new PrefManager(this);

        super.onResume();
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ShowGpsDialog();
            showLocationOffDialog();
        } else {
            removeGpsDialog();
        }
        registerReceiver(internetConnectionReciever, new IntentFilter(
                "android.net.conn.CONNECTIVITY_CHANGE"));
        registerReceiver(GpsChangeReceiver, new IntentFilter(
                LocationManager.PROVIDERS_CHANGED_ACTION));
        isRecieverRegistered = true;
        if (UtilsExtra.isNetworkAvailable(this)
                && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (!isDataRecieved) {
                isDataRecieved = true;
                checkStatus();
            }
        }
        gson = new Gson();
        userProfile = new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        /*if (mAmapView != null) {
            mAmapView.onResume();
        }*/
        if (mGoogleMapView != null) {
            try {
                mGoogleMapView.onResume();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    private void setCurrentPositionInfo() {
        String position = "：" + longitude + " ：" + latitude + "\n" + formatAddress;

    }
    private void getRequestInProgress() {
        HashMap<String, String> map = new HashMap<String, String>();
        AppLog.Log(
                "BookingDrawerAct",
                BookingConstant.ServiceType.REQUEST_IN_PROGRESS + BookingConstant.Params.ID + "="
                        + new PrefManager(this).getUserId() + "&"
                        + BookingConstant.Params.TOKEN + "="
                        + new PrefManager(this).getSessionToken());
        map.put(URL,
                BookingConstant.ServiceType.REQUEST_IN_PROGRESS + BookingConstant.Params.ID + "="
                        + new PrefManager(this).getUserId() + "&"
                        + BookingConstant.Params.TOKEN + "="
                        + new PrefManager(this).getSessionToken());
        // new HttpRequester(this, map,
        // Const.ServiceCode.GET_REQUEST_IN_PROGRESS,
        // true, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                BookingConstant.ServiceCode.GET_REQUEST_IN_PROGRESS, this, this));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // drawerToggel.onConfigurationChanged(newConfig);
    }

    private void checkStatus() {
        //pHelper = new PrefManager(this);
        // AndyUtils.showCustomProgressDialog(this,
        // getString(R.string.text_gettting_request_stat), false, null);
        try {
            if(pHelper !=null){
                if (pHelper.getRequestId() == NO_REQUEST) {
                    getRequestInProgress();
                } else {
                    getRequestStatus(String.valueOf(pHelper.getRequestId()));
                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }
    private void getRequestStatus(String requestId) {
        pHelper = new PrefManager(this);

        HashMap<String, String> map = new HashMap<String, String>();
        try {
            if(pHelper !=null){
                map.put(URL, BookingConstant.ServiceType.GET_REQUEST_STATUS
                        + BookingConstant.Params.ID + "=" + pHelper.getUserId() + "&"
                        + BookingConstant.Params.TOKEN + "=" + pHelper.getSessionToken() + "&"
                        + BookingConstant.Params.REQUEST_ID + "=" + requestId);


            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        // new HttpRequester(this, map, Const.ServiceCode.GET_REQUEST_STATUS,
        // true, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                BookingConstant.ServiceCode.GET_REQUEST_STATUS, this, this));
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }


    @Override
    public AmbientModeSupport.AmbientCallback getAmbientCallback() {
        return new AmbientModeSupport.AmbientCallback() {

            @Override
            public void onEnterAmbient(Bundle ambientDetails) {
                super.onEnterAmbient(ambientDetails);
                mapFragment.onEnterAmbient(ambientDetails);
            }

            @Override
            public void onExitAmbient() {
                super.onExitAmbient();
                mapFragment.onExitAmbient();
            }
        };
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googlemap = googleMap;
        if (googlemap != null) {
            googlemap.setOnCameraMoveListener(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            googlemap.setMyLocationEnabled(true);
            //googlemap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            googlemap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        addGoogleMarker();
        googlemap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(com.google.android.gms.maps.model.LatLng latLng) {
                longitude = latLng.longitude;
                latitude = latLng.latitude;
                getGooglePosition(latLng);
            }
        });
        UiSettings uiSettings = googlemap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setCompassEnabled(true);
        uiSettings.setRotateGesturesEnabled(true);

    }
    private void getGooglePosition(com.google.android.gms.maps.model.LatLng latLng) {
        ApiUtil.init().getGooglePostion(latLng, new MyMapCallBack() {
            public void onSuccess(String result) {
                formatAddress = result;
                //LogUtils.debug(TAG,"Google formatAddress ="+formatAddress);
                setCurrentPositionInfo();
                addGoogleMarker();
            }
        });
    }
    public void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void addGoogleMarker() {
        com.google.android.gms.maps.model.LatLng location = new com.google.android.gms.maps.model.LatLng(latitude, longitude);
        googlemap.addMarker(new com.google.android.gms.maps.model.MarkerOptions().position(location).title("My Loc："+longitude+","+latitude));
        googlemap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLng(location));
        googlemap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),19));
    }


    @Override
    public void onCameraMove() {
        CameraPosition cameraPosition=googlemap.getCameraPosition();
        longitude = cameraPosition.target.longitude;
        latitude = cameraPosition.target.latitude;
        zoom = cameraPosition.zoom;

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {

    }
}