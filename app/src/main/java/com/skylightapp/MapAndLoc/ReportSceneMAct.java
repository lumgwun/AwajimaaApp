package com.skylightapp.MapAndLoc;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportNextDAO;
import com.skylightapp.R;
import com.teliver.sdk.core.Teliver;
import com.teliver.sdk.core.TrackingListener;
import com.teliver.sdk.models.MarkerOption;
import com.teliver.sdk.models.TLocation;
import com.teliver.sdk.models.TrackingBuilder;
import com.teliver.sdk.models.UserBuilder;
import com.twilio.Twilio;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportSceneMAct extends FragmentActivity implements MapListener,OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    long accountNo, acctID;
    long customerID;
    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;
    private Customer lastCustomerProfileUsed;
    private final int MY_LOCATION_REQUEST_CODE = 100;

    public final static int SENDING = 1;
    public final static int CONNECTING = 2;
    public final static int ERROR = 3;
    public final static int SENT = 4;
    public final static int SHUTDOWN = 5;

    private static final String TAG = "ReportSceneMAct";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Button btnFusedLocation, btn_backTo_dashboard;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    LatLng cusLatLng;
    Location mCurrentLocation;
    String customerPhone;
    private Location previousLocation;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    double lastLat, lastLng, latitute1, longitute1, altitute1;


    private final long MIN_TIME = 1000;
    private final long MIN_DIST = 5;

    private LatLng latLng, profileLatLng;
    protected DBHelper dbHelper;
    SharedPreferences sharedpreferences;
    Gson gson;
    String json;
    long customerId;
    private boolean locationPermissionGranted;
    Customer customer;
    Bundle locDetailsBundle, locBundle, profileBundle, typeBundle;

    SecureRandom random;
    Profile userProfile;
    private static boolean isPersistenceEnabled = false;
    private String stringLatitude = "0.0";
    private String stringLongitude = "0.0";
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest request;

    String myLatitude;
    String myLongitude, CustomerName, displayName,profileName, locDate, profilePhoneNo, profileEmail, address, CustomerPhone;
    double latitude;
    double longitude;
    private static final int REQUEST_CHECK_SETTINGS = 1000;
    long PackageID, ProfileID;
    Geocoder geocoder;
    List<Address> addresses;
    SupportMapFragment mapFragment,bottomSheetMap;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private int emergReportID;


    private static final String PREF_NAME = "awajima";
    private String type;
    LatLng userLocation;
    private GoogleApiClient googleApiClient;
    private Marker officeMaker;
    private Marker sceneMaker;
    private Marker responseTeamMaker;
    private Marker mCurrLocationMarker;
    private ArrayList<Marker> nearByResponseDevices;
    private Polyline greyPolyline;
    private Polyline blackPolyline;
    private LatLng sceneLatLng;
    DBHelper applicationDb;
    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;



    private CancellationTokenSource cancellationTokenSource;
    Bundle userLocBundle;
    int PERMISSION_ALL33 = 2;
    private int profileID;
    private Gson gson1;
    private String json1,SharedPrefSuperUser;
    private Uri pictureLink;
    private static Handler handlerN;
    private AppCompatTextView txtMyLoc,txtStatus;
    private EmergencyReport emergencyReport;
    private EmergReportNext emergReportNext;
    private ArrayList<String> emergReportNextLatLngStng;
    private ArrayList<LatLng> emergReportNextLatLng;
    private EmergReportNextDAO emergReportNextDAO;
    String latlong;
    String[] latlongs;
    private LatLng emergNextLatLng;
    //AppCompatTextView txtLoc;
    String machineUser, office,state,role,dbRole,joinedDate,password,firstName, email,phoneNO, dob,gender,surname;

    String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private final android.location.LocationListener locationListenerNetwork = new android.location.LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ReportSceneMAct.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });


            try {
                Geocoder newGeocoder = new Geocoder(ReportSceneMAct.this, Locale.ENGLISH);
                List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder street = new StringBuilder();
                if (Geocoder.isPresent()) {
                    txtMyLoc = findViewById(R.id.locTextV);

                    Address newAddress = newAddresses.get(0);

                    String localityString = newAddress.getSubLocality();

                    street.append(localityString).append("");

                    txtMyLoc.setText(MessageFormat.format("Where you are:  {0},{1}/{2}", latitude, longitude, street));
                    Toast.makeText(ReportSceneMAct.this, street,
                            Toast.LENGTH_SHORT).show();

                } else {
                    txtMyLoc.setVisibility(View.GONE);
                    //go
                }


            } catch (IndexOutOfBoundsException | IOException e) {

                Log.e("tag", e.getMessage());
            }

        }

    };
    private SimpleDateFormat timeFormatter;

    public static Handler getHandler() {
        return handlerN;
    }

    public static void setHandler(Handler handler) {

        handlerN = handler;
    }

    private int getNumEvents ()
    {
        AppController app = (AppController) getApplication ();
        return app.getEvents ().size ();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_report_scene);
        applicationDb= new DBHelper(this);
        if (!hasPermissions(ReportSceneMAct.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(ReportSceneMAct.this, PERMISSIONS, PERMISSION_ALL33);
        }
        checkInternetConnection();
        customer = new Customer();
        userProfile = new Profile();
        emergencyReport= new EmergencyReport();
        emergReportNextLatLngStng = new ArrayList<>();
        gson = new Gson();
        dbHelper = new DBHelper(this);
        txtStatus = findViewById(R.id.statusTextView);
        txtMyLoc = findViewById(R.id.locTextV);
        emergReportNextDAO= new EmergReportNextDAO(this);

        locBundle = new Bundle();
        profileBundle = new Bundle();
        locDetailsBundle = new Bundle();
        typeBundle = new Bundle();
        customer = new Customer();
        userLocBundle = new Bundle();
        locDetailsBundle = getIntent().getExtras();
        typeBundle = getIntent().getExtras();
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapScene);
        geocoder = new Geocoder(ReportSceneMAct.this, Locale.getDefault());
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        joinedDate = sharedpreferences.getString("PROFILE_DATE_JOINED", "");
        surname = sharedpreferences.getString("PROFILE_SURNAME", "");
        email = sharedpreferences.getString("PROFILE_EMAIL", "");
        phoneNO = sharedpreferences.getString("PROFILE_PHONE", "");
        firstName = sharedpreferences.getString("PROFILE_FIRSTNAME", "");
        dob = sharedpreferences.getString("PROFILE_DOB", "");
        gender = sharedpreferences.getString("PROFILE_GENDER", "");
        address = sharedpreferences.getString("PROFILE_ADDRESS", "");
        pictureLink = Uri.parse(sharedpreferences.getString("PICTURE_URI", ""));
        json = sharedpreferences.getString("LastSuperAdminProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        Teliver.identifyUser(new UserBuilder(email).setUserType(UserBuilder.USER_TYPE.CONSUMER).registerPush().build());

        if (typeBundle != null) {
            type = typeBundle.getString("Type");
        }
        if (type.equalsIgnoreCase("Teller")) {
            if (profileBundle != null) {
                ProfileID = profileBundle.getLong("ProfileID");
                profileName = profileBundle.getString("ProfileName");
                profilePhoneNo = profileBundle.getString("ProfilePhone");
                profileEmail = profileBundle.getString("ProfileEmail");
                profileLatLng = profileBundle.getParcelable("LatLng");
                latLng = profileLatLng;
            }

        }
        if (type.equalsIgnoreCase("Admin")) {
            if (profileBundle != null) {
                ProfileID = profileBundle.getLong("ProfileID");
                profileName = profileBundle.getString("ProfileName");
                profilePhoneNo = profileBundle.getString("ProfilePhone");
                profileEmail = profileBundle.getString("ProfileEmail");
                profileLatLng = profileBundle.getParcelable("LatLng");
                latLng = profileLatLng;
                latitute1=latLng.latitude;
                longitute1=latLng.longitude;
            }

        } else {
            if (locDetailsBundle != null) {
                customerId = locDetailsBundle.getLong("CustomerID");
                CustomerName = locDetailsBundle.getString("CustomerName");
                CustomerPhone = locDetailsBundle.getString("CustomerPhone");
                PackageID = locDetailsBundle.getLong("PackageID");
                cusLatLng = locDetailsBundle.getParcelable("LatLng");
                latLng = cusLatLng;
                latitute1=latLng.latitude;
                longitute1=latLng.longitude;
            }

        }

        if(locDetailsBundle !=null){
            latLng = locDetailsBundle.getParcelable("LatLng");
            emergReportID = locDetailsBundle.getInt("PackageID");
            emergencyReport=locDetailsBundle.getParcelable("EmergencyReport");


        }
        if(emergencyReport !=null){
            emergReportID=emergencyReport.getEmergReportID();
        }
        if(emergReportID>0){
            emergReportNextLatLngStng = emergReportNextDAO.getLatLngStrngForEmergR(emergReportID);

        }

        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        googleApiClient.connect();
        createLocationRequest();

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getMyLocation();
        locationRequest = LocationRequest.create();
        //cancellationTokenSource = new CancellationTokenSource();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getDeviceLocation(latLng,latitute1,longitute1);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);

        if (bestProvider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            locationManager.requestLocationUpdates(bestProvider, 2 * 60 * 1000, 10, locationListenerNetwork);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        try {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {
                    userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //doGetLocation();
        //getLocationDetails();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPause () {
        super.onPause ();
        //Log.v (Constants.LOGTAG, CLASSTAG + "onPause called");
        AppController app = (AppController) getApplication ();
        if (app.getStatus () == AppController.Status.FENCES_FAILED)
        {
            app.setStatus (AppController.Status.DEFAULT);
        }
    }
    @Override
    public void onResume ()
    {
        super.onResume ();
        //Log.v (Constants.LOGTAG, CLASSTAG + "onResume called");

        // Add fences if required
        AppController app = (AppController) getApplication ();
        if ( app.isGeofencingInitialised () &&
                (app.getStatus () == AppController.Status.DEFAULT) )
        {
            app.addFences (this);
        }

        // Replace the time formatter in case the Locale has changed.
        timeFormatter =
                new SimpleDateFormat ("dd-MMM HH:mm:ss", Locale.getDefault ());
        refreshScreen ();
    }
    public void refreshScreen ()
    {
        AppController app = (AppController) getApplication ();


        app.getLocationAvailability ()
                .addOnSuccessListener ( new OnSuccessListener<LocationAvailability> ()
                {
                    @Override
                    public void onSuccess (LocationAvailability availability)
                    {
                        handleLocationAvailable (availability);
                    }
                } )
                .addOnFailureListener ( new OnFailureListener()
                {
                    @Override
                    public void onFailure (@NonNull Exception e)
                    {
                        /*Log.w ( Constants.LOGTAG,
                                CLASSTAG + "Location availability failure: " +
                                        e.getLocalizedMessage () );*/
                        handleLocationNotAvailable ();
                    }
                } );

        // Force the menu to be updated
        invalidateOptionsMenu ();
    }
    private String locationToString (double d)
    { return Location.convert (d, Location.FORMAT_DEGREES); }

    /**
     * Convert a UTC long time to a string using default Locale
     */
    private String timeToString (long t)
    { return timeFormatter.format (new Date(t)); }

    private void handleDisplayLocationRequest ()
    {
        //Log.v (Constants.LOGTAG, CLASSTAG + "Location requested");
        AppController app = (AppController) getApplication ();
        app.requestSingleLocation (new LocationCallback()
        {
            @Override
            public void onLocationAvailability (LocationAvailability la)
            {
                /*String msg = CLASSTAG +
                        "onLocationAvailability called within handleDisplayLocationRequest";
                Log.v (Constants.LOGTAG, msg);*/
            }

            @Override
            public void onLocationResult (LocationResult result)
            { handleLocationResultReceived (result); }
        })
                .addOnSuccessListener ( new OnSuccessListener <Void> ()
                {
                    @Override public void onSuccess (Void aVoid)
                    { handleLocationRequestSucceeded (); }
                } )
                .addOnFailureListener (new OnFailureListener ()
                {
                    @Override public void onFailure (@NonNull Exception e)
                    { handleLocationRequestFailed (e); }
                } );
    }
    private void handleLocationNotAvailable ()
    {
        //btnDisplayLocation.setEnabled (true);
    }

    private void handleLocationRequestFailed (Exception e) {

    }

    private void handleLocationRequestSucceeded () {
        //Log.v (Constants.LOGTAG, CLASSTAG + "Location request succeeded");
    }
    private void initialiseGeofencing ()
    {
        AppController app = (AppController) getApplication ();
        app.initGeofencing (this);
    }

    private void handleLocationResultReceived (LocationResult result)
    {

        /*currentLocation = result.getLastLocation ();
        String text = locationToString (currentLocation.getLatitude ());
        float accuracy = currentLocation.getAccuracy ();
        if (accuracy != 0.0f)
        {
            text += " (\u00B1" + // ± - \u00B1
                    String.format (Locale.getDefault (), "%.2f m", accuracy) + ")";
        }
        clLatitude.setText (text);
        clLongitude.setText (locationToString (currentLocation.getLongitude ()));
        clTime.setText (timeToString (currentLocation.getTime ()));
        clFrame.setVisibility (VISIBLE);*/
    }

    private void handleLocationAvailable (LocationAvailability availability)
    {
        if (availability.isLocationAvailable ())
        {
            AppController app = (AppController) getApplication ();
            app.getLastLocation ()
                    .addOnSuccessListener ( new OnSuccessListener<Location>()
                    {
                        @Override
                        public void onSuccess (Location location)
                        {
                            handleLastLocationFound (location);
                        }
                    } )
                    .addOnFailureListener ( new OnFailureListener() {
                        @Override
                        public void onFailure (@NonNull Exception e)
                        {
                            handleLastLocationFailed (e);
                        }
                    } );
        } else
        {

            handleLocationNotAvailable ();
        }
    }
    private void handleLastLocationFailed (Exception e)
    {

        //btnDisplayLocation.setEnabled (true);
    }
    private void handleLastLocationFound (Location location) {
        /*lastLocationFound = location;
        String text = locationToString (location.getLatitude ());
        float accuracy = location.getAccuracy ();
        if (accuracy != 0.0f)
        {
            text += " (\u00B1" + // ± - \u00B1
                    String.format (Locale.getDefault (), "%.2f m", accuracy) + ")";
        }
        llfLatitude.setText (text);
        llfLongitude.setText (locationToString (location.getLongitude ()));
        llfTime.setText (timeToString (location.getTime ()));
        llfFrame.setVisibility (VISIBLE);*/

    }


    private  void openBottomSheet(View v) {
        //View view = activity.getLayoutInflater ().inflate (R.layout.bottom_sheet, null);
        // View view = inflater.inflate( R.layout.bottom_sheet, null );
        if(emergencyReport !=null){
            emergReportID=emergencyReport.getEmergReportID();
        }
        if(emergReportID>0){
            emergReportNextLatLngStng = emergReportNextDAO.getLatLngStrngForEmergR(emergReportID);
        }

        for (int i = 0; i < emergReportNextLatLngStng.size(); i++) {
            latlong= emergReportNextLatLngStng.get(i);


        }

        latlongs =  latlong.split(",");
        double latitude = Double.parseDouble(latlongs[0]);
        double longitude = Double.parseDouble(latlongs[1]);

        emergNextLatLng=new LatLng(latitude,longitude);
        emergReportNextLatLng.add(emergNextLatLng);
        //emergReportNextLatLng=mMarkerPoints;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addPolyline(new PolylineOptions().addAll(emergReportNextLatLng).
                width(5).color(Color.BLUE));


        //mMap.getMyLocation();
        if (handlerN == null)
        {
            handlerN = new Handler (getMainLooper ())
            {
                @Override
                public void handleMessage (@NotNull Message msg)
                {
                    //Log.v (Constants.LOGTAG, CLASSTAG + "Handler receives Event");
                    refreshScreen ();
                }
            };
        } else
        {

        }
        Context context=v.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate (R.layout.act_display_loc, null);
        bottomSheetMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_Details);
        if (bottomSheetMap != null) {
            bottomSheetMap.getMapAsync(this);
        }

        AppCompatTextView lastUpdateTime = findViewById (R.id.time_cp_value);
        AppCompatTextView responseCount             = findViewById (R.id.response_Counts);
        AppCompatTextView updateCount = view.findViewById(R.id.report_loc_update);
        AppCompatTextView txtStatus = view.findViewById(R.id.status_label);
        AppCompatButton btnMore = view.findViewById( R.id.next_More_Reports);
        ImageView closeImg = view.findViewById(R.id.awa_close_Img);


        final Dialog mBottomSheetDialog = new Dialog (context, R.style.Theme_Material3_DayNight_BottomSheetDialog);
        mBottomSheetDialog.setContentView (view);
        mBottomSheetDialog.setCancelable (true);
        mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);
        mBottomSheetDialog.show ();

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.addPolyline(new PolylineOptions().addAll(emergReportNextLatLng).
                width(5).color(Color.BLUE));


        closeImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Closing More Details",Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Closing More Details",Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });



    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    @Override
    public void showNearbyResponseTeams(List<LatLng> latLngList) {
        nearByResponseDevices.clear();
        for (LatLng latLng :latLngList) {
            responseTeamMaker = addResponseTeamMarkerAndGet(latLng);
            nearByResponseDevices.add(responseTeamMaker);
        }

    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           /* if (intent.getStringExtra("payload").equals("0"))
                Teliver.stopTracking(intent.getStringExtra(Application.TRACKING_ID));
            else if (intent.getStringExtra("payload").equals("1")) {
                trackingId.add(intent.getStringExtra(Application.TRACKING_ID));
                startTracking(trackingId);
            }*/
        }
    };
    private void startTracking(List<String> trackingId) {
        List<MarkerOption> markerOptionList = new ArrayList<>();
        String[] ids = trackingId.toArray(new String[trackingId.size()]);
        for (String id : ids) {
            MarkerOption option = new MarkerOption(id);
            option.setMarkerTitle("Hi There");
            markerOptionList.add(option);
        }

        Teliver.startTracking(new TrackingBuilder(markerOptionList).withYourMap(mMap).withListener(new TrackingListener() {
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

    @Override
    public void informReporterRescued() {
        nearByResponseDevices.forEach(Marker::remove);
        nearByResponseDevices.clear();
        Toast.makeText(this, "Response Operation Completed", Toast.LENGTH_LONG).show();
        //requestCabButton.visibility = View.GONE
        //statusTextView.text = getString(R.string.your_cab_is_booked)

    }

    @Override
    public void showPath(List<LatLng> latLngList) {

    }

    @Override
    public void updateResponseTeamLocation(LatLng latLng) {

    }

    @Override
    public void informResponseTeamIsArriving() {

    }

    @Override
    public void informResponseTeamArrived() {

    }

    @Override
    public void informTripStart() {

    }

    @Override
    public void informTripEnd() {
        greyPolyline.remove();
        blackPolyline.remove();
        sceneMaker.remove();
        responseTeamMaker.remove();
        Toast.makeText(this, "Response Operation has Ended", Toast.LENGTH_LONG).show();

    }

    @Override
    public void showRoutesNotAvailableError() {

    }

    @Override
    public void showDirectionApiFailedError(String error) {

    }
    public void moveCamera(LatLng latLng){
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
    public void animateCamera(LatLng latLng){
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15.5f).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 17));
    }
    private Marker addResponseTeamMarkerAndGet(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("He is here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        return mMap.addMarker(markerOptions);

    }

    private Marker addOriginDestinationMarkerAndGet(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("You are here");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        return mMap.addMarker(markerOptions);
    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        latLng = origin;

        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key);

        String parameters = str_origin + "&amp;" + str_dest + "&amp;" + key;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation(LatLng dest, double latitute1, double longitute1) {
        try {
            if (locationPermissionGranted) {
                mCurrentLocation = null;
                fusedLocationProviderClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location mCurrentLocation) {
                                if (mCurrentLocation != null) {
                                    latitude = mCurrentLocation.getLatitude();
                                    longitude = mCurrentLocation.getLongitude();
                                    cusLatLng = new LatLng(latitude, longitude);
                                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                            new LatLng(mCurrentLocation.getLatitude(),
                                                    mCurrentLocation.getLongitude()), 17));

                                } else {
                                    //cusLatLng = new LatLng(4.52871, 7.44507);
                                    Log.d(TAG, "Current location is null. Using Last Location.");
                                    getPreviousLoc(latitute1,longitute1);
                                }

                            }

                        });
                dest = cusLatLng;
                setResult(Activity.RESULT_OK, new Intent());
                txtMyLoc = findViewById(R.id.locTextV);

                txtMyLoc.setText("My Loc:" + latitude + "," + longitude + "/");

                StringBuilder sourceToDestination = new StringBuilder();
                sourceToDestination.append("https://maps.googleapis.com/maps/api/directions/json?");
                sourceToDestination.append("origin=").append(latitude).append(",").append(longitude);
                sourceToDestination.append("&destination=").append(latitute1).append(",").append(longitute1);
                sourceToDestination.append("&waypoints=via:").append(latitude).append(",").append(longitude);
                sourceToDestination.append("&key=").append(getResources().getString(R.string.google_api_key));
                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))).setTitle("Where you are");
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(dest);
                markerOptions.title("You are here");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));


            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void getPreviousLoc(double latitute1, double longitute1) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    mCurrentLocation = task.getResult();
                    if (mCurrentLocation != null) {
                        latitude = mCurrentLocation.getLatitude();
                        longitude = mCurrentLocation.getLongitude();
                        cusLatLng = new LatLng(latitude, longitude);

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 20));
                        StringBuilder sourceToDestination = new StringBuilder();
                        sourceToDestination.append("https://maps.googleapis.com/maps/api/directions/json?");
                        sourceToDestination.append("origin=").append(latitude).append(",").append(longitude);
                        sourceToDestination.append("&destination=" + latitute1 + "," + longitute1);
                        sourceToDestination.append("&waypoints=via:").append(latitude).append(",").append(latitude);
                        sourceToDestination.append("&key=").append(getResources().getString(R.string.google_api_key));
                        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))).setTitle("Where you were");
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(cusLatLng);
                        markerOptions.title("You are here");
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }
                } else {

                    cusLatLng = new LatLng(4.52871, 7.44507);
                    Log.d(TAG, "Current location is null. Using defaults.");
                    mMap.moveCamera(CameraUpdateFactory
                            .newLatLngZoom(cusLatLng, 20));
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                }
            }
        });

    }

    private final LocationCallback mLocationCallBack = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                if (location != null) {
                    latitute1 = location.getLatitude();
                    longitute1 = location.getLongitude();
                    userLocation = new LatLng(latitute1, longitute1);
                    userLocBundle = new Bundle();
                    userLocBundle.putParcelable("Location", userLocation);
                    cancellationTokenSource = new CancellationTokenSource();
                    userLocBundle.putDouble("LastLatitude", latitute1);
                    userLocBundle.putDouble("LastLongitude", longitute1);
                    userLocBundle.putParcelable("LastLocation", userLocation);
                    setResult(Activity.RESULT_OK, new Intent());
                    userLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    StringBuilder sourceToDestination = new StringBuilder();
                    sourceToDestination.append("https://maps.googleapis.com/maps/api/directions/json?");
                    sourceToDestination.append("origin=").append(userLocation.latitude).append(",").append(userLocation.longitude);
                    sourceToDestination.append("&destination=" + 4.8156 + "," + 7.0498);
                    sourceToDestination.append("&waypoints=via:").append(latitute1).append(",").append(longitute1);
                    sourceToDestination.append("&key=").append(getResources().getString(R.string.google_api_key));
                    //mMap.addMarker(new MarkerOptions().position(new LatLng(startPoint.getLatitude(), startPoint.getLongitude()))).setTitle("Source");
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(userLocation);
                    markerOptions.title("You are here");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    if(mCurrLocationMarker!=null){
                        mCurrLocationMarker.remove();
                    }
                    mCurrLocationMarker = mMap.addMarker(markerOptions);
                    //mMap.addMarker(new MarkerOptions().position().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setTitle("You are here");
                    //getDirectionData.execute(data);
                }
            }
        }
    };
    protected void createLocationRequest() {

        request = new LocationRequest();
        request.setSmallestDisplacement( 10 );
        request.setFastestInterval( 50000 );
        request.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );
        request.setNumUpdates( 3 );

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest( request );
        builder.setAlwaysShow( true );

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings( googleApiClient,
                        builder.build() );


        result.setResultCallback( new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {

                    case LocationSettingsStatusCodes.SUCCESS:
                        setInitialLocation();
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {

                            status.startResolutionForResult(
                                    ReportSceneMAct.this,
                                    REQUEST_CHECK_SETTINGS );
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        } );


    }
    private void setInitialLocation() {

        txtMyLoc = findViewById(R.id.locTextV);

        if (ActivityCompat.checkSelfPermission( ReportSceneMAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( ReportSceneMAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                double lat=location.getLatitude();
                double lng=location.getLongitude();

                ReportSceneMAct.this.latitude=lat;
                ReportSceneMAct.this.longitude=lng;

                try {
                    if(mCurrLocationMarker !=null){
                        mCurrLocationMarker.remove();
                    }

                    userLocation = new LatLng( ReportSceneMAct.this.latitude, ReportSceneMAct.this.longitude );
                    cusLatLng=userLocation;


                } catch (Exception ex) {

                    ex.printStackTrace();
                    Log.e( "MapException", ex.getMessage() );

                }

                try {
                    geocoder = new Geocoder(ReportSceneMAct.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {
                        txtMyLoc.setVisibility(View.VISIBLE);

                        android.location.Address returnAddress = addresses.get(0);

                        String localityString = returnAddress.getSubLocality ();

                        str.append( localityString ).append( "" );

                        txtMyLoc.setText(MessageFormat.format("Your are here:  {0},{1}/{2}", latitude, longitude , str));
                        Toast.makeText(ReportSceneMAct.this, str,
                                Toast.LENGTH_SHORT).show();

                    } else {
                        //go
                    }


                } catch (IOException e) {

                    Log.e("tag", e.getMessage());
                }



            }

        } );
    }
    public void doGetLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(@NotNull Location location) {
                    getLocationDetails();
                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                    locDate = mdformat.format(calendar.getTime());

                    if (location != null) {
                        stringLatitude = Double.toString(location.getLatitude());
                        stringLongitude = Double.toString(location.getLongitude());
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        latLng = new LatLng(latitude, longitude);
                        txtMyLoc = findViewById(R.id.locTextV);

                        try {
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            locBundle.putString("Address", String.valueOf(addresses));
                            locBundle.putParcelable("LatLong", latLng);

                            txtMyLoc.setText("Address:"+addresses+"("+"Lat: " + stringLatitude + "," +" Long: " + stringLongitude+")");

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        locDetailsBundle = getIntent().getExtras();
                        if (locDetailsBundle !=null) {
                            customerId= locDetailsBundle.getLong("CustomerID");
                            CustomerName= locDetailsBundle.getString("CustomerName");
                            CustomerPhone= locDetailsBundle.getString("CustomerPhone");
                            PackageID= locDetailsBundle.getLong("PackageID");
                            ProfileID= locDetailsBundle.getLong("ProfileID");
                        }


                        setResult(Activity.RESULT_OK);
                        //setResult(Activity.RESULT_OK, new Intent());


                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locDetailsBundle = getIntent().getExtras();
        if(locDetailsBundle !=null){
            latLng = locDetailsBundle.getParcelable("LatLng");
            emergReportID = locDetailsBundle.getInt("PackageID");
            previousLocation=locDetailsBundle.getParcelable("Location");
            displayName= locDetailsBundle.getString("Report Tittle");


        }
        LatLng portHarcourt = new LatLng(4.8156, 7.0498);
        mMap.addMarker(new MarkerOptions().position(portHarcourt).title("Port Harcourt"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(portHarcourt));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        locationRequest = LocationRequest.create();
        locationRequest.setFastestInterval(12000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(" Position"));

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    myLatitude = String.valueOf(location.getLatitude());
                    myLongitude = String.valueOf(location.getLongitude());
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

        };

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,MIN_DIST, (android.location.LocationListener) locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME,MIN_DIST, (android.location.LocationListener) locationListener);
        }
        catch (SecurityException e){
            e.printStackTrace();
        }

        List<Fence> fences =
                ((AppController) getApplication ()).getFences ();
        new FenceMap (mMap).showLocation (displayName, previousLocation).showFences (fences);

        LatLng location = new LatLng ( previousLocation.getLatitude (), previousLocation.getLongitude () );
        mMap.moveCamera (CameraUpdateFactory.newLatLngZoom (location, 15f));
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void getLocationDetails(){
        latitude = latLng.latitude;
        longitude = latLng.longitude;
        latLng = new LatLng(latitude, longitude);
        /*geoFire.setLocation("CustomerLoc", new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
            @Override
            public void onComplete(String key, DatabaseError error) {
                if (error != null) {
                    System.err.println("There was an error saving the location to Cloud: " + error);
                } else {
                    System.out.println("Location saved on server successfully!");
                }

            }

        });*/
        //Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        String address = null;
        String city = null;
        String state = null;
        String country = null;
        String postalCode = null;
        String knonName = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            knonName = addresses.get(0).getFeatureName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtMyLoc = findViewById(R.id.locTextV);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Customer in:" + address + city + state  + knonName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        txtMyLoc.setText(MessageFormat.format("{0}{1}{2}{3}", address, city, state, knonName));
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