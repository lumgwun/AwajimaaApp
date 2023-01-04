package com.skylightapp.Bookings;

import static com.skylightapp.Bookings.BookingConstant.DEVICE_TYPE_ANDROID;
import static com.skylightapp.Bookings.BookingConstant.DRIVER;
import static com.skylightapp.Bookings.BookingConstant.FRAGMENT_FEEDBACK;
import static com.skylightapp.Bookings.BookingConstant.FRAGMENT_MAP;
import static com.skylightapp.Bookings.BookingConstant.FRAGMENT_TRIP;
import static com.skylightapp.Bookings.BookingConstant.INVALID_TOKEN;
import static com.skylightapp.Bookings.BookingConstant.IS_COMPLETED;
import static com.skylightapp.Bookings.BookingConstant.IS_WALKER_ARRIVED;
import static com.skylightapp.Bookings.BookingConstant.IS_WALKER_RATED;
import static com.skylightapp.Bookings.BookingConstant.IS_WALKER_STARTED;
import static com.skylightapp.Bookings.BookingConstant.IS_WALK_STARTED;
import static com.skylightapp.Bookings.BookingConstant.MANUAL;
import static com.skylightapp.Bookings.BookingConstant.NO_REQUEST;
import static com.skylightapp.Bookings.BookingConstant.REQUEST_ID_NOT_FOUND;
import static com.skylightapp.Bookings.BookingConstant.TAG;
import static com.skylightapp.Bookings.BookingConstant.URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.Classes.ParseContent;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Referral;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Markets.ProfileActivity;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BookingHomeAct extends ActionBarBaseAct implements NavigationView.OnNavigationItemSelectedListener{
    SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    DBHelper dbHelper;
    Gson gson, gson1,gson2;
    String json, json1,json2, userName;
    Profile userProfile;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar,toolbar22;
    private FrameLayout frameLayout;
    public ParseContent pContent;
    private AQuery aQuery;
    private LocationManager manager;
    private ImageOptions imageOptions;
    private boolean isLogoutCheck = true;
    private View headerView;
    private RequestQueue requestQueue;
    private ArrayList<AppPages> listMenu;
    private PrefManager pHelper;
    private boolean isDataRecieved = false, isRecieverRegistered = false,
            isNetDialogShowing = false, isGpsDialogShowing = false;
    private AlertDialog internetDialog, gpsAlertDialog, locationAlertDialog;
    com.google.android.material.floatingactionbutton.FloatingActionButton fabHome;

    @Override
    protected boolean isValidate() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_booking_home);
        setTitle("Agric Market Shop");
        userProfile= new Profile();
        gson= new Gson();
        gson1= new Gson();
        gson2= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        userName=userPreferences.getString("PROFILE_USERNAME", "");
        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.booking_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.m_Layout_Booking);
        toolbar = findViewById(R.id.toolbar_booking);

        frameLayout = findViewById(R.id.frameC_booking);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        requestQueue = Volley.newRequestQueue(this);
        setIcon(R.drawable.ic_notification_icon);

        imageOptions = new ImageOptions();
        imageOptions.memCache = true;
        imageOptions.fileCache = true;
        imageOptions.targetWidth = 200;
        imageOptions.fallback = R.drawable.ic_admin_user;
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        pHelper = new PrefManager(this);
        pContent = new ParseContent(this);
        try {
            initActionBar();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

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
                BookingHomeAct.this);
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
    private void initActionBar() {
        actionBar = getSupportActionBar();
        // actionBar.setDisplayHomeAsUpEnabled(true);
        // actionBar.setHomeButtonEnabled(true);
    }
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TODO Auto-generated method stub
        AppLog.Log(TAG, error.getMessage());
    }
    public boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        try {
            locationMode = Settings.Secure.getInt(
                    context.getContentResolver(),
                    Settings.Secure.LOCATION_MODE);

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF;

    }

    public void showLocationOffDialog() {

        AlertDialog.Builder gpsBuilder = new AlertDialog.Builder(
                BookingHomeAct.this);
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

    private void removeLocationoffDialog() {
        if (locationAlertDialog != null && locationAlertDialog.isShowing()) {
            locationAlertDialog.dismiss();
            locationAlertDialog = null;

        }
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
                BookingHomeAct.this);
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

    }
    @Override
    protected void onResume() {
        pHelper = new PrefManager(this);

        super.onResume();
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            ShowGpsDialog();
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
        userProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_my_Home) {
            invalidateOptionsMenu();

        } else if (id == R.id.nav_my_history) {
            Intent intent = new Intent(BookingHomeAct.this, HistoryAct.class);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (id == R.id.nav_booking_payment) {
            Intent intent = new Intent(BookingHomeAct.this, ViewPaymentAct.class);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_my_profile) {
            Intent intent = new Intent(BookingHomeAct.this, ProfileActivity.class);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }else if (id == R.id.nav_booking_Tab) {
            Intent intent = new Intent(BookingHomeAct.this, BookingsMainTab.class);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);


        }  else if (id == R.id.nav_booking_Home) {
            Intent intent = new Intent(BookingHomeAct.this, NewCustomerDrawer.class);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.b_sign_out) {
            logout();

        }

        drawer = findViewById(R.id.m_Layout_Booking);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void showDrawerButton() {

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.syncState();
    }

    public void showUpButton() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void setupDrawerListener() {
        drawer = findViewById(R.id.m_Layout_Booking);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
    }
    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.m_Layout_Booking);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } navigationView.getMenu().getItem(0).setChecked(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.booking_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {

    }
    private void logout() {
        if (!UtilsExtra.isNetworkAvailable(this)) {
            UtilsExtra.showToast(getResources().getString(R.string.no_internet_connection),
                    this);
            return;
        }
        UtilsExtra.showCustomProgressDialog(this,
                getString(R.string.loading1), false, null);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.LOGOUT);
        try {
            map.put(BookingConstant.Params.ID, String.valueOf(pHelper.getUserId()));
            map.put(BookingConstant.Params.TOKEN, pHelper.getSessionToken());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // new HttpRequester(this, map, Const.ServiceCode.LOGOUT, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.LOGOUT, this, this));
    }
    private void getReffrelaCode() {
        pHelper = new PrefManager(this);

        UtilsExtra.showCustomProgressDialog(this,
                getString(R.string.getting_ref_code), false, null);
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            if(pHelper !=null){
                map.put(URL, BookingConstant.ServiceType.GET_REFERRAL + BookingConstant.Params.ID
                        + "=" + pHelper.getUserId() + "&" + BookingConstant.Params.TOKEN + "="
                        + pHelper.getSessionToken());

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        // new HttpRequester(this, map, Const.ServiceCode.GET_REFERREL, true,
        // this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                BookingConstant.ServiceCode.GET_REFERREL, this, this));
    }
    public void gotoMapFragment() {
        MapFragment frag = MapFragment.newInstance();
        addFragment(frag, false, FRAGMENT_MAP);
    }

    public void gotoTripFragment(Driver driver) {
        TripFragment tripFrag = new TripFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(DRIVER, driver);
        tripFrag.setArguments(bundle);
        addFragment(tripFrag, false, FRAGMENT_TRIP);
    }

    public void gotoRateFragment(Driver driver) {
        try {
            if (TextUtils.isEmpty(driver.getLastTime()))
                driver.setLastTime(0 + " " + getString(R.string.mins));
            if (TextUtils.isEmpty(driver.getLastDistance()))
                driver.setLastDistance(0.0 + " "
                        + getString(R.string.miles));
            FeedbackFrag feedBack = new FeedbackFrag();
            Bundle bundle = new Bundle();
            bundle.putParcelable(DRIVER, driver);
            feedBack.setArguments(bundle);
            addFragmentWithStateLoss(feedBack, false, FRAGMENT_FEEDBACK);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onTaskCompleted(String response, int serviceCode) {
        // TODO Auto-generated method stub
        super.onTaskCompleted(response, serviceCode);
        pHelper = new PrefManager(this);


        switch (serviceCode) {
            case BookingConstant.ServiceCode.GET_REQUEST_IN_PROGRESS:
                if (pContent.isSuccess(response)) {
                    pContent.parseCardAndPriceDetails(response);
                    if (pContent.getRequestInProgress(response) == NO_REQUEST) {
                        UtilsExtra.removeCustomProgressDialog();
                        gotoMapFragment();
                    } else {
                        try {
                            if(pHelper !=null){

                                pHelper.putRequestId(pContent.getRequestId(response));
                                getRequestStatus(String.valueOf(pHelper.getRequestId()));

                            }
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }


                    }
                } else if (pContent.getErrorCode(response) == INVALID_TOKEN) {
                    try {
                        if(pHelper !=null){

                            if (pHelper.getLoginBy().equalsIgnoreCase(MANUAL))
                                login();

                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }



                } else if (pContent.getErrorCode(response) == REQUEST_ID_NOT_FOUND) {
                    UtilsExtra.removeCustomProgressDialog();
                    try {
                        if(pHelper !=null){
                            pHelper.clearRequestData();



                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }


                    gotoMapFragment();
                }
                break;
            case BookingConstant.ServiceCode.GET_REQUEST_STATUS:
                UtilsExtra.removeCustomProgressDialog();
                if (pContent.isSuccess(response)) {
                    pContent.parseCardAndPriceDetails(response);
                    switch (pContent.checkRequestStatus(response)) {
                        case IS_WALK_STARTED:
                        case IS_WALKER_ARRIVED:
                        case IS_COMPLETED:
                        case IS_WALKER_STARTED:
                            Driver driver = pContent.getDriverDetail(response);
                            gotoTripFragment(driver);
                            break;

                        case IS_WALKER_RATED:
                            gotoRateFragment(pContent.getDriverDetail(response));
                            break;
                        default:
                            gotoMapFragment();
                            break;
                    }

                } else if (pContent.getErrorCode(response) == INVALID_TOKEN) {
                    login();
                } else if (pContent.getErrorCode(response) == REQUEST_ID_NOT_FOUND) {
                    try {
                        if(pHelper !=null){

                            pHelper.clearRequestData();

                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }


                    gotoMapFragment();
                }


                break;
            case BookingConstant.ServiceCode.LOGIN:
                if (pContent.isSuccessWithStoreId(response)) {
                    checkStatus();
                }
                break;

            case BookingConstant.ServiceCode.GET_REFERREL:
                if (pContent.isSuccess(response)) {
                    Referral ref = pContent.parseReffrelCode(response);
                    if (ref != null) {
                        showRefferelDialog(ref);
                    }
                }
                UtilsExtra.removeCustomProgressDialog();

                break;
            case BookingConstant.ServiceCode.LOGOUT:
                UtilsExtra.removeCustomProgressDialog();
                if (pContent.isSuccess(response)) {
                    try {
                        if(pHelper !=null){

                            pHelper.Logout();

                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }


                    goToMainActivity();
                }
                break;
        }

    }
    private void showRefferelDialog(final Referral ref) {
        pHelper = new PrefManager(this);

        final Dialog mDialog = new Dialog(this, R.style.AlertDialogStyle);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.ref_code);
        AppCompatTextView tvTitle =  mDialog.findViewById(R.id.tvTitle);
        tvTitle.setText(getString(R.string.text_ref_code)
                + ref.getReferralCode());

        AppCompatTextView tvReferralEarn =  mDialog
                .findViewById(R.id.tvReferralEarn);
        tvReferralEarn.setText(getString(R.string.payment_unit)
                + ref.getBalanceAmount());

        AppCompatButton btnCancel =  mDialog.findViewById(R.id.btnCancel);
        AppCompatButton btnShare =  mDialog.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                sharingIntent
                        .putExtra(
                                android.content.Intent.EXTRA_TEXT,
                                "Take a look at the world famous Awajima App- "
                                        + "https://play.google.com/store/apps/details?id="
                                        + getPackageName()
                                        + "\nPlease take a note of your Referral code -"
                                        + ref.getReferralCode());
                startActivity(Intent.createChooser(sharingIntent,
                        "Share Referral Code"));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mDialog.dismiss();
            }
        });

        // final AnimationDrawable frameAnimation = (AnimationDrawable)
        // imageView
        // .getBackground();
        // mDialog.setCancelable(false);
        // imageView.post(new Runnable() {
        //
        // @Override
        // public void run() {
        // frameAnimation.start();
        // frameAnimation.setOneShot(false);
        // }
        // });
        mDialog.show();

    }


    private void login() {
        if (!UtilsExtra.isNetworkAvailable(this)) {
            UtilsExtra.showToast(getResources().getString(R.string.no_internet_connection),
                    this);
            return;
        }
        pHelper = new PrefManager(this);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.LOGIN);
        map.put(BookingConstant.Params.EMAIL, pHelper.getEmail());
        map.put(BookingConstant.Params.PASSWORD, pHelper.getPassword());
        map.put(BookingConstant.Params.DEVICE_TYPE, DEVICE_TYPE_ANDROID);
        map.put(BookingConstant.Params.DEVICE_TOKEN, pHelper.getDeviceToken());
        map.put(BookingConstant.Params.LOGIN_BY, MANUAL);
        // new HttpRequester(this, map, Const.ServiceCode.LOGIN, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.LOGIN, this, this));
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



}