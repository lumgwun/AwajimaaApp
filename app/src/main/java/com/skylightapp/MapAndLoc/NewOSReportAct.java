package com.skylightapp.MapAndLoc;

import static com.skylightapp.Classes.AppController.TAG;
import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.widget.ContentLoadingProgressBar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.ImageUtil;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.skylightapp.SignUpAct;
import com.skylightapp.SuperAdmin.Awajima;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NewOSReportAct extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemSelectedListener {
    String apiKey = getString(R.string.google_maps_key);
    private EditText queryText;
    private AppCompatButton mSearchButton;
    private AppCompatTextView mSearchResult;
    private StringBuilder mResult;
    private String moreInfo;
    SharedPreferences userPreferences;
    protected DatePickerDialog datePickerDialog;
    Random ran;
    SecureRandom random;
    AppCompatTextView spillageDateText;
    int customerID;
    int profileID1;
    DBHelper dbHelper;
    Location mCurrentLocation = null;
    SQLiteDatabase sqLiteDatabase;
    Gson gson, gson1,gson3,gson6;
    String json, json1, json3,json6nIN,name;
    Profile userProfile, customerProfile, lastProfileUsed;
    Date date;
    ArrayList<EmergencyReport> emergencyReports;
    Location location;
    AppCompatTextView txtLoc, locTxt,otpTxt;
    private LocationRequest request;
    private CancellationTokenSource cancellationTokenSource;
    private Calendar calendar;
    private static final String PREF_NAME = "awajima";
    private LocationManager locationManager;
    private AutoCompleteTextView mAutocompleteView;
    private  Intent autoPlaceIntent;
    String address;
    LatLng userLocation;
    LatLng cusLatLng;
    double latitude = 0;
    double longitude = 0;
    Geocoder geocoder;
    ContentLoadingProgressBar progressBar;
    List<Address> addresses;
    private PrefManager prefManager;

    private Calendar cal;
    String joinedDate, reporterNameName,reportDateReversed,reportDate,state;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    protected GoogleApiClient mGoogleApiClient;
    private int sReportID;
    int  day, month, year, newMonth;
    private boolean locationPermissionGranted;
    int numMessages;
    public static final String NOTIFICATION_CHANNEL_ID = "101" ;
    private final static String default_notification_channel_id = "default" ;
    String selectedOilCompany, selectedLGA,strngLatLng, SharedPrefUserMachine,iamAvailable,localityString,selectedState,lgaEdtStrng, subUrb,selectedLga;
    private ProgressDialog progressDialog;
    AppCompatSpinner spnState, spnOilCom, spnLGA, spnAvalaibility;
    private AppCompatButton btnPlaceOnMap,btnSendReport;
    private AppCompatEditText edtObservation,edtOtherLGA;
    private CardView cardViewOtherLGA;
    private String[] lgaList;
    private ArrayAdapter<String>  arrayAdapter;
    private Awajima awajima;
    private EmergReportDAO emergReportDAO;
    private MarketBusiness marketBusiness;
    private Customer customer;
    private LatLng latlonNew;
    private long bizID;
    private  String newReportMessage,userPhoneNO, userEmailAddress;
    private EmergencyReport emergencyReport;
    RectangularBounds stateBounds;
    boolean reportIsOld = false;
    private int spnIndex=0;

    int PERMISSION_ALL33 = 2;
    String[] PERMISSIONS33 = {
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.SEND_SMS
    };

    private ActivityResultLauncher<Intent> auToPlaceStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            if (result.getData() != null) {
                                autoPlaceIntent = result.getData();
                            }
                            Place place = null;
                            if (autoPlaceIntent != null) {
                                place = Autocomplete.getPlaceFromIntent(autoPlaceIntent);
                                subUrb=place.getName();
                            }
                            if (place != null) {
                                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                                Toast.makeText(NewOSReportAct.this, "ID: " + place.getId() + "address:" + place.getAddress() + "Name:" + place.getName() + " latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();
                                address = place.getAddress();
                            }

                            Toast.makeText(NewOSReportAct.this, "Activity returned ok", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(NewOSReportAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + result.getResultCode());
                    }
                }
            });
    private final android.location.LocationListener locationListenerNetwork = new android.location.LocationListener() {
        public void onLocationChanged(Location location) {
            mCurrentLocation=location;
            if(mCurrentLocation !=null){
                longitude = mCurrentLocation.getLongitude();
                latitude = mCurrentLocation.getLatitude();

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(NewOSReportAct.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });
            try {
                latlonNew = getLocation(longitude,latitude,100);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if(latlonNew !=null){
                latitude= latlonNew.latitude;
                longitude=latlonNew.longitude;
            }


            try {
                Geocoder newGeocoder = new Geocoder(NewOSReportAct.this, Locale.ENGLISH);
                List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder street = new StringBuilder();
                if (Geocoder.isPresent()) {

                    locTxt.setVisibility(View.VISIBLE);

                    Address newAddress = newAddresses.get(0);
                    if(newAddress !=null){
                        localityString = newAddress.getLocality();
                        subUrb=newAddress.getSubLocality();

                    }

                    street.append(localityString).append("");

                    locTxt.setText(MessageFormat.format("Where you are:  {0},{1}/{2}", latitude, longitude, street));
                    Toast.makeText(NewOSReportAct.this, street,
                            Toast.LENGTH_SHORT).show();

                } else {
                    locTxt.setVisibility(View.GONE);
                    //go
                }


            } catch (IndexOutOfBoundsException | IOException e) {

                Log.e("tag", e.getMessage());
            }

        }

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_osreport);
        gson = new Gson();
        gson1 = new Gson();
        gson3= new Gson();
        userProfile= new Profile();
        marketBusiness= new MarketBusiness();
        emergencyReports= new ArrayList<>();
        customer= new Customer();
        emergencyReport= new EmergencyReport();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(com.google.android.gms.location.places.Places.GEO_DATA_API)
                .build();
        if (!Places.isInitialized()) {
            try {

                Places.initialize(NewOSReportAct.this, apiKey);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        userPhoneNO = userPreferences.getString("PROFILE_PHONE", "");
        userEmailAddress = userPreferences.getString("PROFILE_EMAIL", "");
        profileID1=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        json3 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson3.fromJson(json3, MarketBusiness.class);
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }
            });
        }
        if(marketBusiness !=null){
            bizID=marketBusiness.getBusinessID();
        }
        if(userProfile !=null){
            reporterNameName =userProfile.getProfileLastName()+""+userProfile.getProfileFirstName();
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        setInitialLocation();
        txtLoc = findViewById(R.id.you_loc22);
        locTxt = findViewById(R.id.where_spillage_you);
        String provider = locationManager.getBestProvider(criteria, true);
        spnState = findViewById(R.id.os_state);
        spnLGA = findViewById(R.id.os_LGA);
        btnPlaceOnMap = findViewById(R.id.place_onMap);
        spnOilCom = findViewById(R.id.os_oilCom);
        spillageDateText = findViewById(R.id.report_os_dp);
        edtObservation = findViewById(R.id.os_observation);
        spnAvalaibility = findViewById(R.id.reporter_avail);
        btnSendReport = findViewById(R.id.send_os_report);
        edtOtherLGA = findViewById(R.id.enter_report_lga);
        cardViewOtherLGA = findViewById(R.id.card_otherLGA);
        emergReportDAO= new EmergReportDAO(this);
        awajima= new Awajima();

        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, locationListenerNetwork);
        }
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    mCurrentLocation=location;
                    longitude = mCurrentLocation.getLongitude();
                    latitude = mCurrentLocation.getLatitude();
                    locTxt.setVisibility(View.VISIBLE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewOSReportAct.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                        }
                    });
                    try {
                        latlonNew = getLocation(longitude,latitude,100);

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    if(latlonNew !=null){
                        latitude= latlonNew.latitude;
                        longitude=latlonNew.longitude;
                    }
                    userLocation = new LatLng(latitude, longitude);
                    cusLatLng = userLocation;
                    strngLatLng= String.valueOf(cusLatLng);


                    try {
                        Geocoder newGeocoder = new Geocoder(NewOSReportAct.this, Locale.ENGLISH);
                        List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                        StringBuilder street = new StringBuilder();
                        if (Geocoder.isPresent()) {

                            Address newAddress = newAddresses.get(0);
                            if(newAddress !=null){
                                localityString = newAddress.getLocality();
                                subUrb=newAddress.getSubLocality();

                            }



                            street.append(localityString).append("");

                            locTxt.setText(MessageFormat.format("Where you are:  {0},{1}/{2}", latitude, "" + longitude, street));
                            Toast.makeText(NewOSReportAct.this, street,
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            locTxt.setVisibility(View.GONE);
                            //go
                        }


                    } catch (IndexOutOfBoundsException | IOException e) {

                        Log.e("tag", e.getMessage());
                    }

                }
            }
        };
            spnState.setOnItemSelectedListener(this);
            spnOilCom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    btnSendReport.setEnabled(false);
                    return;
                }else {
                    selectedOilCompany = spnOilCom.getSelectedItem().toString();
                    btnSendReport.setEnabled(true);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnAvalaibility.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    btnSendReport.setEnabled(false);
                    return;
                }else {
                    btnSendReport.setEnabled(true);
                    iamAvailable = spnAvalaibility.getSelectedItem().toString();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spnIndex==position){
                    selectedLGA = edtOtherLGA.getText().toString().trim();
                    if(selectedLGA.isEmpty()){
                        btnSendReport.setEnabled(false);

                    }else {
                        btnSendReport.setEnabled(true);
                    }
                    //return;
                }else {
                    btnSendReport.setEnabled(true);
                    selectedLGA = spnLGA.getSelectedItem().toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        getDeviceLocation();
        sReportID = ThreadLocalRandom.current().nextInt(122, 1631);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        cancellationTokenSource = new CancellationTokenSource();
        cal = Calendar.getInstance();

            spillageDateText.setOnClickListener(this::reportDatePicker);

            spillageDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = (cal.get(Calendar.MONTH)+1);
                //newMonth = month + 1;
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(NewOSReportAct.this, R.style.DatePickerDialogStyle, mDateSetListener, day, month, year);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
                dialog.show();
                reportDateReversed = year + "-" + month + "-" + day;
                reportDate = day + "-" + month + "-" + year;
                spillageDateText.setText("Your date of Spillage:" + reportDateReversed);

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                Log.d(ImageUtil.TAG, "onDateSet: date of Spillage: " + day + "-" + month + "-" + year);
                reportDateReversed = year + "-" + month + "-" + day;
                reportDate = day + "-" + month + "-" + year;
                spillageDateText.setText("Your date of Spillage:" + reportDateReversed);


            }


        };
            reportDate = year + "-" + month + "-" + day;



        mSearchButton.setOnClickListener(v -> {
            Toast.makeText(NewOSReportAct.this, queryText.getText().toString(), Toast.LENGTH_SHORT).show();
            AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

            RectangularBounds bounds = stateBounds;

            FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                    .setLocationBias(bounds)
                    .setCountry("ng")
                    .setTypeFilter(TypeFilter.ADDRESS)
                    .setSessionToken(token)
                    .setQuery(queryText.getText().toString())
                    .build();


            placesClient.findAutocompletePredictions(request).addOnSuccessListener(response -> {
                mResult = new StringBuilder();
                for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                    mResult.append(" ").append(prediction.getFullText(null) + "\n");
                    Log.i(TAG, prediction.getPlaceId());
                    Log.i(TAG, prediction.getPrimaryText(null).toString());
                    Toast.makeText(NewOSReportAct.this, prediction.getPrimaryText(null) + "-" + prediction.getSecondaryText(null), Toast.LENGTH_SHORT).show();
                }
                mSearchResult.setText(String.valueOf(mResult));
            }).addOnFailureListener((exception) -> {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                }
            });
        });
            calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            reportDate = mdformat.format(calendar.getTime());

        Animation translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        Animation translater44 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        newReportMessage="The Governments have received your Spillage Report, and we would respond immediately";

        //btnSendReport.setOnClickListener(this::sendReportToTeam);
        emergencyReport= new EmergencyReport();
        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(translater44);
                lgaEdtStrng = edtOtherLGA.getText().toString().trim();
                moreInfo = edtObservation.getText().toString().trim();



                for (int i = 0; i < emergencyReports.size(); i++) {
                    try {
                        if (emergencyReports.get(i).getEmergRTime().equalsIgnoreCase(reportDate) && emergencyReports.get(i).getEmergRTown().equalsIgnoreCase(subUrb)) {
                            reportIsOld = false;
                            Toast.makeText(NewOSReportAct.this, "A similar Spillage Report, exist, here", Toast.LENGTH_LONG).show();
                            return;

                        } else {
                            reportIsOld=true;
                            doNotification(newReportMessage,userPhoneNO,userEmailAddress);
                            doSyNotification(newReportMessage,userPhoneNO,userEmailAddress);

                            if(reportIsOld){
                                try {

                                    if(sqLiteDatabase !=null){
                                        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                                    }
                                } catch (SQLiteException e) {
                                    e.printStackTrace();
                                }

                                if(emergReportDAO !=null){
                                    try {

                                        emergReportDAO.insertUserEmergencyReport(sReportID, profileID1,bizID,reportDate,"Oil Spillage Report",localityString,subUrb,selectedLGA,selectedOilCompany,address,strngLatLng,moreInfo,iamAvailable );

                                    } catch (SQLiteException e) {
                                        e.printStackTrace();
                                    }

                                }
                                if(awajima !=null){
                                    try {

                                        awajima.addEmergReport(sReportID, profileID1,bizID,reportDate,"Oil Spillage Report",localityString,subUrb,selectedLGA,selectedOilCompany,address,strngLatLng,moreInfo,iamAvailable);
                                    } catch (SQLiteException e) {
                                        e.printStackTrace();
                                    }
                                }


                            }
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }

                }

            }
        });


    }
    public void doSyNotification(String newReportMessage,String userPhoneNO,String userEmailAddress) {
        Bundle smsBundle = new Bundle();
        smsBundle.putString("PROFILE_PHONE", userPhoneNO);
        smsBundle.putString("USER_PHONE", userPhoneNO);
        smsBundle.putString("smsMessage", newReportMessage);
        //smsBundle.putString("from", "Awajima");
        smsBundle.putString("to", userPhoneNO);
        smsBundle.putString("PROFILE_EMAIL", userEmailAddress);
        Intent otpIntent = new Intent(NewOSReportAct.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(spnIndex==position){
            return;
        }else {

            state = (String) spnState.getItemAtPosition(position);
            if(state !=null){
                if(state.equalsIgnoreCase("Rivers State")){
                    try {
                        lgaList=getResources().getStringArray(R.array.rivers_lga);
                        stateBounds = RectangularBounds.newInstance(
                                new LatLng(4.8396, 6.9112),
                                new LatLng(4.7719, 6.0699));


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    arrayAdapter = new ArrayAdapter<String>(NewOSReportAct.this, android.R.layout.simple_spinner_item, lgaList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cardViewOtherLGA.setVisibility(View.GONE);
                    spnLGA.setAdapter(arrayAdapter);

                    spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spnIndex==position){
                                selectedLGA = edtOtherLGA.getText().toString().trim();
                                if(selectedLGA.isEmpty()){
                                    btnSendReport.setEnabled(false);

                                }else {
                                    btnSendReport.setEnabled(true);
                                }
                            }else {
                                selectedLGA = spnLGA.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                if(state.equalsIgnoreCase("Bayelsa State")){
                    try {
                        lgaList=getResources().getStringArray(R.array.bayelsa_lga);
                        stateBounds = RectangularBounds.newInstance(
                                new LatLng(4.7719, 6.0699),
                                new LatLng(5.532462, 5.898714));


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    arrayAdapter = new ArrayAdapter<String>(NewOSReportAct.this, android.R.layout.simple_spinner_item, lgaList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cardViewOtherLGA.setVisibility(View.GONE);
                    spnLGA.setAdapter(arrayAdapter);
                    spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spnIndex==position){
                                selectedLGA = edtOtherLGA.getText().toString().trim();
                                if(selectedLGA.isEmpty()){
                                    btnSendReport.setEnabled(false);

                                }else {
                                    btnSendReport.setEnabled(true);
                                }
                            }else {
                                selectedLGA = spnLGA.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                if(state.equalsIgnoreCase("Delta State")){
                    try {
                        lgaList=getResources().getStringArray(R.array.delta_lga);
                        stateBounds = RectangularBounds.newInstance(
                                new LatLng(5.532462, 5.898714),
                                new LatLng(5.879698, 5.700531));


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    arrayAdapter = new ArrayAdapter<String>(NewOSReportAct.this, android.R.layout.simple_spinner_item, lgaList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cardViewOtherLGA.setVisibility(View.GONE);
                    spnLGA.setAdapter(arrayAdapter);
                    spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spnIndex==position){
                                selectedLGA = edtOtherLGA.getText().toString().trim();
                                if(selectedLGA.isEmpty()){
                                    btnSendReport.setEnabled(false);

                                }else {
                                    btnSendReport.setEnabled(true);
                                }
                            }else {
                                selectedLGA = spnLGA.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                if(state.equalsIgnoreCase("Akwa-Ibom State")){
                    try {
                        lgaList=getResources().getStringArray(R.array.akwaibom_lga);
                        stateBounds = RectangularBounds.newInstance(
                                new LatLng(4.9057, 7.8537),
                                new LatLng(5.8702, 8.5988));


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    arrayAdapter = new ArrayAdapter<String>(NewOSReportAct.this, android.R.layout.simple_spinner_item, lgaList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cardViewOtherLGA.setVisibility(View.GONE);
                    spnLGA.setAdapter(arrayAdapter);
                    spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spnIndex==position){
                                selectedLGA = edtOtherLGA.getText().toString().trim();
                                if(selectedLGA.isEmpty()){
                                    btnSendReport.setEnabled(false);

                                }else {
                                    btnSendReport.setEnabled(true);
                                }
                            }else {
                                selectedLGA = spnLGA.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                if(state.equalsIgnoreCase("Imo State")){
                    try {
                        lgaList=getResources().getStringArray(R.array.imo_lga);
                        stateBounds = RectangularBounds.newInstance(
                                new LatLng(5.5720, 7.0588),
                                new LatLng(4.8396, 6.9112));


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    arrayAdapter = new ArrayAdapter<String>(NewOSReportAct.this, android.R.layout.simple_spinner_item, lgaList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cardViewOtherLGA.setVisibility(View.GONE);
                    spnLGA.setAdapter(arrayAdapter);
                    spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spnIndex==position){
                                selectedLGA = edtOtherLGA.getText().toString().trim();
                                if(selectedLGA.isEmpty()){
                                    btnSendReport.setEnabled(false);

                                }else {
                                    btnSendReport.setEnabled(true);
                                }
                            }else {
                                selectedLGA = spnLGA.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                if(state.equalsIgnoreCase("Abia State")){
                    try {
                        lgaList=getResources().getStringArray(R.array.abia_lga);
                        stateBounds = RectangularBounds.newInstance(
                                new LatLng(5.4527, 7.5248),
                                new LatLng(4.8396, 6.9112));


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    arrayAdapter = new ArrayAdapter<String>(NewOSReportAct.this, android.R.layout.simple_spinner_item, lgaList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cardViewOtherLGA.setVisibility(View.GONE);
                    spnLGA.setAdapter(arrayAdapter);
                    spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spnIndex==position){
                                selectedLGA = edtOtherLGA.getText().toString().trim();
                                if(selectedLGA.isEmpty()){
                                    btnSendReport.setEnabled(false);

                                }else {
                                    btnSendReport.setEnabled(true);
                                }
                            }else {
                                selectedLGA = spnLGA.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                if(state.equalsIgnoreCase("Ondo State")){
                    try {
                        lgaList=getResources().getStringArray(R.array.ondo_lga);
                        stateBounds = RectangularBounds.newInstance(
                                new LatLng(6.9149, 5.1478),
                                new LatLng(7.5629, 4.5200));


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    arrayAdapter = new ArrayAdapter<String>(NewOSReportAct.this, android.R.layout.simple_spinner_item, lgaList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cardViewOtherLGA.setVisibility(View.GONE);
                    spnLGA.setAdapter(arrayAdapter);
                    spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spnIndex==position){
                                selectedLGA = edtOtherLGA.getText().toString().trim();
                                if(selectedLGA.isEmpty()){
                                    btnSendReport.setEnabled(false);

                                }else {
                                    btnSendReport.setEnabled(true);
                                }
                            }else {
                                selectedLGA = spnLGA.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                if(state.equalsIgnoreCase("Cross River State")){
                    try {
                        lgaList=getResources().getStringArray(R.array.cr_lga);
                        stateBounds = RectangularBounds.newInstance(
                                new LatLng(5.8702, 8.5988),
                                new LatLng(4.9057, 7.8537));


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    arrayAdapter = new ArrayAdapter<String>(NewOSReportAct.this, android.R.layout.simple_spinner_item, lgaList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cardViewOtherLGA.setVisibility(View.GONE);
                    spnLGA.setAdapter(arrayAdapter);
                    spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spnIndex==position){
                                selectedLGA = edtOtherLGA.getText().toString().trim();
                                if(selectedLGA.isEmpty()){
                                    btnSendReport.setEnabled(false);

                                }else {
                                    btnSendReport.setEnabled(true);
                                }
                            }else {
                                selectedLGA = spnLGA.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                if(state.equalsIgnoreCase("Edo State")){
                    try {
                        lgaList=getResources().getStringArray(R.array.edo_lga);
                        stateBounds = RectangularBounds.newInstance(
                                new LatLng(6.6342, 5.9304),
                                new LatLng(5.532462, 5.898714));


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    arrayAdapter = new ArrayAdapter<String>(NewOSReportAct.this, android.R.layout.simple_spinner_item, lgaList);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cardViewOtherLGA.setVisibility(View.GONE);
                    spnLGA.setAdapter(arrayAdapter);
                    spnLGA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spnIndex==position){
                                selectedLGA = edtOtherLGA.getText().toString().trim();
                                if(selectedLGA.isEmpty()){
                                    btnSendReport.setEnabled(false);

                                }else {
                                    btnSendReport.setEnabled(true);
                                }
                            }else {
                                selectedLGA = spnLGA.getSelectedItem().toString();

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });

                }
                if(state.equalsIgnoreCase("Others")){
                    cardViewOtherLGA = findViewById(R.id.card_otherLGA);
                    cardViewOtherLGA.setVisibility(View.VISIBLE);

                }
            }

        }


    }
    private void doNotification(String newReportMessage, String userPhoneNO, String otpMessage) {

        try {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(NewOSReportAct.this, sound);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Uri sound = Uri. parse (ContentResolver. SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/quite_impressed.mp3" ) ;
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.logo_awa_round)
                        .setContentTitle("Your Awajima OTP Message")
                        //.setSound(sound)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setContentText(otpMessage);

        Intent notificationIntent = new Intent(this, ERMediaUAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SignUpAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        builder.setNumber(++numMessages);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "OIL Spillage Channel" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            //notificationChannel.setSound(sound , audioAttributes) ;
            builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis (), builder.build()) ;
        mNotificationManager.notify(20, builder.build());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                onSearchCalled();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }
    public static LatLng getLocation(double lon, double lat, int radius)
    {
        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = radius / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(lat);

        double foundLongitude = new_x + lon;
        double foundLatitude = y + lat;
        System.out.println("Longitude: " + foundLongitude + "  Latitude: "
                + foundLatitude);

        return new LatLng(foundLatitude, foundLongitude);

    }
    public void onSearchCalled() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields).setCountry("NG") //NIGERIA
                .build(this);
        auToPlaceStartForResult.launch(new Intent(intent));
    }
    public static boolean isOnline(ConnectivityManager cm) {
        @SuppressLint("MissingPermission") NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
    protected void createLocationRequest() {
        if (!hasPermissions(NewOSReportAct.this, PERMISSIONS33)) {
            ActivityCompat.requestPermissions(NewOSReportAct.this, PERMISSIONS33, PERMISSION_ALL33);

        }


        request = new LocationRequest();
        request.setSmallestDisplacement(10);
        request.setFastestInterval(50000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(3);



    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void setInitialLocation() {
        txtLoc = findViewById(R.id.you_loc22);
        locTxt = findViewById(R.id.where_spillage_you);
        final double[] newLat = {0.00};
        final double[] newLng = {0.00};

        if (ActivityCompat.checkSelfPermission(NewOSReportAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NewOSReportAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (locationPermissionGranted) {
            @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        mCurrentLocation = task.getResult();
                        newLat[0] = mCurrentLocation.getLatitude();
                        newLng[0] = mCurrentLocation.getLongitude();

                    } else {
                        Log.d(ImageUtil.TAG, "Current location is null. Using defaults.");
                        Log.e(ImageUtil.TAG, "Exception: %s", task.getException());

                    }
                }
            });
        }
        if(mCurrentLocation !=null){
            newLat[0] = mCurrentLocation.getLatitude();
            newLng[0] = mCurrentLocation.getLongitude();

        }


        try {
            Geocoder newGeocoder = new Geocoder(NewOSReportAct.this, Locale.ENGLISH);
            List<Address> newAddresses = newGeocoder.getFromLocation(newLat[0], newLng[0], 1);
            StringBuilder street = new StringBuilder();
            if (Geocoder.isPresent()) {

                //txtLoc.setVisibility(View.VISIBLE);

                android.location.Address newAddress = newAddresses.get(0);

                String localityString = newAddress.getLocality();

                street.append(localityString).append("");

                //txtLoc.setText(MessageFormat.format("Last Loc:  {0},{1}/{2}", newLat[0], newLng[0], street));
                Toast.makeText(NewOSReportAct.this, street,
                        Toast.LENGTH_SHORT).show();

            } else {
                txtLoc.setVisibility(View.GONE);
                //go
            }


        } catch (IndexOutOfBoundsException | IOException e) {

            Log.e("tag", e.getMessage());
        }

    }


    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {

                fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location2) {
                                if (location2 != null) {
                                    latitude = location2.getLatitude();
                                    longitude = location2.getLongitude();
                                    cusLatLng = new LatLng(latitude, longitude);


                                    setResult(Activity.RESULT_OK, new Intent());


                                } else {
                                    cusLatLng = new LatLng(4.52871, 7.44507);
                                    Log.d(ImageUtil.TAG, "Current location is null. Using defaults.");
                                }

                            }

                        });
                txtLoc = findViewById(R.id.you_loc22);
                locTxt = findViewById(R.id.where_spillage_you);
                try {
                    geocoder = new Geocoder(NewOSReportAct.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {
                        //txtLoc.setVisibility(View.VISIBLE);

                        android.location.Address returnAddress = addresses.get(1);

                        String localityString = returnAddress.getAdminArea();

                        str.append(localityString).append("");
                        //txtLoc.setText(MessageFormat.format("Your are here:  {0},{1}/{2}", latitude, longitude, str));


                        Toast.makeText(NewOSReportAct.this, str,
                                Toast.LENGTH_SHORT).show();

                    } else {
                        //go
                    }


                } catch (IllegalStateException | IOException e) {

                    Log.e("tag", e.getMessage());
                }
                /*fusedLocationClient.requestLocationUpdates(LocationRequest.create()
                                .setInterval(36000).setFastestInterval(36000)
                                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                                .setMaxWaitTime(1000),
                        mLocationCallBack(), Looper.myLooper());*/



                //txtLoc.setText("My Loc:" + latitude + "," + longitude + "/" + city);


            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
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


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //locationTracker.onRequestPermission(requestCode, permissions, grantResults);
        locationPermissionGranted = false;
        txtLoc = findViewById(R.id.you_loc22);
        locTxt = findViewById(R.id.where_spillage_you);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1002: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        //setupLocationManager();

                    }
                } else {

                    Toast.makeText(NewOSReportAct.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("Awajima App",
                            BuildConfig.APPLICATION_ID, null);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
            break;
            case 1000: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                    fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location2) {
                                    if (location2 != null) {

                                        latitude = location2.getLatitude();
                                        longitude = location2.getLongitude();
                                        userLocation = new LatLng(latitude, longitude);
                                        long duration = 1000;


                                    }
                                    cusLatLng = new LatLng(latitude, longitude);


                                }
                            });
                    NewOSReportAct.this.cusLatLng = userLocation;

                    geocoder = new Geocoder(this, Locale.getDefault());

                    if (location != null) {
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {

                            address = addresses.get(0).getAddressLine(0);
                            //txtLoc.setVisibility(View.VISIBLE);



                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                    String city = addresses.get(0).getAdminArea();

                    //txtLoc.setText(MessageFormat.format("My Loc:{0},{1}/{2}", latitude, longitude, city));


                } else {
                    Intent intent = new Intent();
                    intent.setAction(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("Awajima App",
                            BuildConfig.APPLICATION_ID, null);
                    intent.setData(uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }

        }


        cusLatLng = new LatLng(latitude, longitude);



    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);//you can cancel it by pressing back button
        progressDialog.setMessage("signing up wait ...");
        progressBar.show();//displays the progress bar
    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
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
    public void reportDatePicker(View view) {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = (cal.get(Calendar.MONTH)+1);
        //newMonth = month + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(NewOSReportAct.this, R.style.DatePickerDialogStyle, mDateSetListener, day, month, year);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
        dialog.show();
        reportDate = year + "-" + month + "-" + day;
        reportDateReversed = year + "-" + month + "-" + day;
        spillageDateText.setText("Your date of Birth:" + reportDate);


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


    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {

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
    protected void onStart() {
        super.onStart();
        geocoder = new Geocoder(this, Locale.getDefault());
        getDeviceLocation();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}