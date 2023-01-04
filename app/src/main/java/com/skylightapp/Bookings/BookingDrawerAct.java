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

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.google.gson.Gson;
import com.skylightapp.Classes.AppLog;
import com.skylightapp.Classes.HttpRequester;
import com.skylightapp.Classes.ParseContent;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Referral;
import com.skylightapp.Classes.UtilsExtra;
import com.skylightapp.Classes.VolleyHttpRequest;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Markets.ProfileActivity;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingDrawerAct extends ActionBarBaseAct {
    private DrawerAdapter adapter;
    // MenuDrawer mMenuDrawer;
    DrawerLayout drawerLayout;
    private ListView listDrawer;
    private ActionBarDrawerToggle drawerToggel;
    public PrefManager pHelper;
    public ParseContent pContent;
    private ArrayList<AppPages> listMenu;
    private boolean isDataRecieved = false, isRecieverRegistered = false,
            isNetDialogShowing = false, isGpsDialogShowing = false;
    private AlertDialog internetDialog, gpsAlertDialog, locationAlertDialog;
    private DBHelper dbHelper;
    private AQuery aQuery;
    private LocationManager manager;
    private AppCompatImageView ivMenuProfile;
    private AppCompatTextView tvMenuName;
    private ImageOptions imageOptions;
    private Profile profile;
    private boolean isLogoutCheck = true;
    private View headerView;
    private RequestQueue requestQueue;
    private ProfDAO profDAO;
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    private Gson gson;
    private String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_booking_drawer);
        //Mint.initAndStartSession(this, "6f0d48b8");
        dbHelper = new DBHelper(this);
        aQuery = new AQuery(this);
        gson = new Gson();
        profile= new Profile();
        profDAO= new ProfDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        profile = gson.fromJson(json, Profile.class);
        // mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.MENU_DRAG_WINDOW);
        // mMenuDrawer.setContentView(R.layout.activity_map);
        // mMenuDrawer.setMenuView(R.layout.menu_drawer);
        // mMenuDrawer.setDropShadowEnabled(false);
        //btnActionMenu = findViewById(R.id.btnActionMenu);

        requestQueue = Volley.newRequestQueue(this);
        try {
            moveDrawerToTop();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            initActionBar();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        /*try {
            btnActionMenu.setVisibility(View.VISIBLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }*/




        setIcon(R.drawable.ic_notification_icon);

        imageOptions = new ImageOptions();
        imageOptions.memCache = true;
        imageOptions.fileCache = true;
        imageOptions.targetWidth = 200;
        imageOptions.fallback = R.drawable.ic_admin_user;

        pHelper = new PrefManager(this);
        pContent = new ParseContent(this);
        try {
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            listDrawer = (ListView) findViewById(R.id.left_drawer);
            listMenu = new ArrayList<AppPages>();
            adapter = new DrawerAdapter(this, listMenu);
            headerView = getLayoutInflater().inflate(R.layout.booking_drawer, null);
            listDrawer.addHeaderView(headerView);
            listDrawer.setAdapter(adapter);


            ivMenuProfile =  headerView.findViewById(R.id.ivMenuProfile);

            tvMenuName = headerView
                    .findViewById(R.id.tvMenuName);
            // tvMenuName.setText(user.getFname() + " " + user.getLname());
            getMenuItems();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // drawerToggel = new ActionBarDrawerToggle(this, drawerLayout,
        // R.drawable.slide_btn, 0, 0);
        // drawerLayout.setDrawerListener(drawerToggel);
        // actionBar.setDisplayHomeAsUpEnabled(true);
        // actionBar.setHomeButtonEnabled(true);
        // actionBar.setHomeAsUpIndicator(R.drawable.slide_btn);
        if(listDrawer !=null){

            listDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View v,
                                        final int position, long id) {
                    if (position == 0)
                        return;
                    drawerLayout.closeDrawer(listDrawer);
                    // mMenuDrawer.closeMenu();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            if (position == 1) {
                                startActivity(new Intent(BookingDrawerAct.this,
                                        ProfileActivity.class));
                            } else if (position == 2) {
                                startActivity(new Intent(BookingDrawerAct.this,
                                        ViewPaymentAct.class));
                            } else if (position == 3) {
                                startActivity(new Intent(BookingDrawerAct.this,
                                        HistoryAct.class));
                            } else if (position == 4) {
                                getReffrelaCode();
                            } else if (position == (listMenu.size())) {
                                if (isLogoutCheck) {
                                    openLogoutDialog();
                                    isLogoutCheck = false;
                                    return;
                                }
                            } else {
                                Intent intent = new Intent(BookingDrawerAct.this,
                                        MenuDescAct.class);
                                intent.putExtra(BookingConstant.Params.TITLE,
                                        listMenu.get(position - 1).getTitle());
                                intent.putExtra(BookingConstant.Params.CONTENT,
                                        listMenu.get(position - 1).getData());
                                startActivity(intent);
                            }
                        }
                    }, 350);

                }
            });

        }


    }
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.app.FragmentActivity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
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
        profile= new Profile();
        profDAO= new ProfDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        profile = gson.fromJson(json, Profile.class);
        if (profile != null) {
            aQuery.id(ivMenuProfile).progress(R.id.pBar)
                    .image(String.valueOf(profile.getProfilePicture()), imageOptions);

            tvMenuName.setText(profile.getProfileFirstName() + " " + profile.getProfileLastName());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyboard();
        // if (drawerToggel.onOptionsItemSelected(item)) {
        // return true;
        // }
        switch (item.getItemId()) {
            case android.R.id.home:
                // mMenuDrawer.toggleMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // drawerToggel.onConfigurationChanged(newConfig);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.btnActionMenu:
            case R.id.tvTitle:
                // mMenuDrawer.toggleMenu();
                drawerLayout.openDrawer(listDrawer);
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        openExitDialog();
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
                getMenuItems();

                break;
            case BookingConstant.ServiceCode.LOGIN:
                if (pContent.isSuccessWithStoreId(response)) {
                    checkStatus();
                }
                break;
            case BookingConstant.ServiceCode.GET_PAGES:
                AppLog.Log(TAG, "Pages::::" + response);
                listMenu.clear();
                listMenu = pContent.parsePages(listMenu, response);
                AppPages applicationPages = new AppPages();
                applicationPages.setData("");
                applicationPages.setId(listMenu.size());
                applicationPages.setTitle(getString(R.string.logout));
                listMenu.add(applicationPages);
                adapter.notifyDataSetChanged();
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


    @Override
    protected boolean isValidate() {
        return false;
    }

    private void loginSocial(String id, String loginType) {
        if (!UtilsExtra.isNetworkAvailable(this)) {
            UtilsExtra.showToast(getResources().getString(R.string.no_internet_connection),
                    this);
            return;
        }
        UtilsExtra.showCustomProgressDialog(this,
                getResources().getString(R.string.dlg_sign_in), false, null);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.LOGIN);
        map.put(BookingConstant.Params.SOCIAL_UNIQUE_ID, id);
        map.put(BookingConstant.Params.DEVICE_TYPE, DEVICE_TYPE_ANDROID);
        map.put(BookingConstant.Params.DEVICE_TOKEN,
                new PrefManager(this).getDeviceToken());
        map.put(BookingConstant.Params.LOGIN_BY, loginType);
        // new HttpRequester(this, map, Const.ServiceCode.LOGIN, this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.POST, map,
                BookingConstant.ServiceCode.LOGIN, this, this));

    }

    private void getMenuItems() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.GET_PAGES + "?user_type=0");
        AppLog.Log(TAG, URL);

        // new HttpRequester(this, map, Const.ServiceCode.GET_PAGES, true,
        // this);
        requestQueue.add(new VolleyHttpRequest(Request.Method.GET, map,
                BookingConstant.ServiceCode.GET_PAGES, this, this));

    }

    private void getMenuItemsDetail(String id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(URL, BookingConstant.ServiceType.GET_PAGES_DETAIL);
        new HttpRequester(this, map, BookingConstant.ServiceCode.GET_PAGES_DETAILS, true,
                this);
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
                BookingDrawerAct.this);
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

    public void showLocationOffDialog() {

        AlertDialog.Builder gpsBuilder = new AlertDialog.Builder(
                BookingDrawerAct.this);
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
                BookingDrawerAct.this);
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

    public void openLogoutDialog() {
        final Dialog mDialog = new Dialog(this,
                android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        mDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mDialog.setContentView(R.layout.booking_logout);
        mDialog.findViewById(R.id.tvLogoutOk).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                        logout();
                        isLogoutCheck = false;
                    }
                });
        mDialog.findViewById(R.id.tvLogoutCancel).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                        isLogoutCheck = true;
                    }
                });
        mDialog.show();
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

    private void moveDrawerToTop() {
        try {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            DrawerLayout drawer = (DrawerLayout) inflater.inflate(
                    R.layout.drawer_booking, null); // "null" is important.

            // HACK: "steal" the first child of decor view
            ViewGroup decor = (ViewGroup) getWindow().getDecorView();
            View child = decor.getChildAt(0);
            decor.removeView(child);
            LinearLayout container = (LinearLayout) drawer
                    .findViewById(R.id.booking_Content); // This is the container we
            // defined just now.
            container.addView(child, 0);
            drawer.findViewById(R.id.left_drawer).setPadding(0,
                    (actionBar.getHeight() + getStatusBarHeight()), 0, 0);

            // Make the drawer replace the first child
            decor.addView(drawer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

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
}