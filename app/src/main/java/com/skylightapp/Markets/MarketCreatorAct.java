package com.skylightapp.Markets;

import static com.skylightapp.Classes.ImageUtil.TAG;
import static com.skylightapp.Database.DBHelper.DATABASE_NAME;
import static com.skylightapp.MarketClasses.Market.MARKET_LAT;
import static com.skylightapp.MarketClasses.Market.MARKET_LNG;
import static com.skylightapp.MarketClasses.Market.MARKET_MAP_ZOOM;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.blongho.country_data.Country;
import com.blongho.country_data.World;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketDAO;
import com.skylightapp.Database.UserContentProvider;
import com.skylightapp.LoginDirAct;
import com.skylightapp.MapAndLoc.Town;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.R;
import com.skylightapp.StateDir.State;
import com.skylightapp.SuperAdmin.Awajima;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MarketCreatorAct extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, LocationListener {
    SharedPreferences userPreferences;
    AppCompatEditText edtMarketName, edtMarketAddress, edtMarketLGA, edtTown, edtStateOutsideNig;
    String country, lga, marketName, marketAddress, marketType, marketState, stateOutSideNig;
    Random ran;
    SecureRandom random;
    int profileID, newMarketID;
    Gson gson, gson1;
    String json, json1, nIN;
    Profile userProfile, lastProfileUsed;
    Customer customer;
    List<Address> addresses;

    Location location;
    AppCompatTextView txtLoc22, txtLocTxt;
    private LocationRequest request;
    private CancellationTokenSource cancellationTokenSource;
    private Calendar calendar;
    private static boolean isPersistenceEnabled = false;
    private Account account;
    private FusedLocationProviderClient fusedLocationClient;
    CountryCodePicker ccp;
    private static final String PREF_NAME = "awajima";
    private LocationManager locationManager;
    private AutoCompleteTextView mAutocompleteView;
    protected GoogleApiClient mGoogleApiClient;
    private MarketDAO marketDAO;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private LatLng latLng;
    private AppCompatTextView txtSelectState;
    private AppCompatSpinner spnCountry, spnState, spnMarketType;
    private AppCompatButton btnSubmit;
    private List<Country> countryArrayList;
    private ArrayList<State> stateArrayList;
    private ArrayAdapter<Country> countryArrayAdapter;
    private ArrayAdapter<State> stateArrayAdapter;
    private ArrayAdapter<Town> townArrayAdapter;
    private Country worldCountry;
    private State state;
    private String marketCountry;
    private LinearLayoutCompat layoutCompatState;
    Location mCurrentLocation = null;
    String address, dateOFReg, town;
    LatLng userLocation;
    LatLng cusLatLng;
    double latitude = 0;
    double longitude = 0;
    Geocoder geocoder;
    private long dbID;
    ContentLoadingProgressBar progressBar;
    private ProgressDialog progressDialog;
    private LocationCallback locationCallback;
    int PERMISSION_ALL33 = 345;
    private boolean locationPermissionGranted;
    private CardView cardViewSelectSpn, cardViewEnterState;
    private Market market;
    private GoogleMap mGoogleMap;
    private  Bundle userBundle;
    String localityString,city,autoAddress,selectedAddress;
    int mapZoom=19;
    LocationInsertTask insertTask;
    private AutoCompleteTextView  autoComPlaces;
    private static int AUTOCOMPLETE_REQUEST_CODE = 19998;
    private ArrayList<Market>marketArrayList;
    private Awajima awajima;
    String provider;
    private boolean isActivatedEnabled = false;


    String[] PERMISSIONS33 = {
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.SEND_SMS
    };
    ActivityResultLauncher<Intent> mStartLocNowForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            latLng = intent.getParcelableExtra("CusLatLng");
                        }
                        // Handle the Intent
                    }
                }
            });

    private final android.location.LocationListener locationListenerNetwork = new android.location.LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MarketCreatorAct.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });


            try {
                Geocoder newGeocoder = new Geocoder(MarketCreatorAct.this, Locale.ENGLISH);
                List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder street = new StringBuilder();
                if (Geocoder.isPresent()) {

                    txtLoc22.setVisibility(View.VISIBLE);

                    Address newAddress = newAddresses.get(0);

                    String localityString = newAddress.getLocality();
                    country = newAddress.getCountryName();

                    street.append(localityString).append("");

                    txtLoc22.setText(MessageFormat.format("Where you are:  {0},{1}/{2}", latitude, longitude, street));
                    Toast.makeText(MarketCreatorAct.this, street,
                            Toast.LENGTH_SHORT).show();

                } else {
                    txtLoc22.setVisibility(View.GONE);
                    //go
                }


            } catch (IndexOutOfBoundsException | IOException e) {

                Log.e("tag", e.getMessage());
            }

        }

    };
    private AdapterView.OnItemSelectedListener country_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                marketCountry = spnCountry.getSelectedItem().toString();
                //worldCountry = (Country) spnCountry.getItemAtPosition(position);
                spnState = findViewById(R.id.market_state_new);
                edtStateOutsideNig = findViewById(R.id.market_state_addition);
                layoutCompatState = findViewById(R.id.stateLayout);
                txtSelectState = findViewById(R.id.stateOfM);
                cardViewSelectSpn = findViewById(R.id.cardSelectState);
                cardViewEnterState = findViewById(R.id.cardSelectSOutside);
                if (marketCountry != null) {
                    layoutCompatState.setVisibility(View.VISIBLE);
                    if (marketCountry.equalsIgnoreCase("Nigeria")) {
                        isActivatedEnabled=true;
                        cardViewSelectSpn.setVisibility(View.VISIBLE);
                        spnState.setVisibility(View.VISIBLE);
                        txtSelectState.setVisibility(View.VISIBLE);

                        edtStateOutsideNig.setVisibility(View.GONE);
                        cardViewEnterState.setVisibility(View.GONE);

                    } else {
                        isActivatedEnabled=true;
                        txtSelectState.setVisibility(View.VISIBLE);
                        edtStateOutsideNig.setVisibility(View.VISIBLE);
                        cardViewEnterState.setVisibility(View.VISIBLE);

                    }
                } else {
                    layoutCompatState.setVisibility(View.GONE);
                }
                /*ArrayList<State> tempStates = new ArrayList<>();

                for (State singleState : stateArrayList) {
                    if (singleState.getCountry().getId() == worldCountry.getId()) {
                        tempStates.add(singleState);
                        stateArrayList=tempStates;
                    }
                }*/


            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            spnState.setVisibility(View.VISIBLE);
            layoutCompatState.setVisibility(View.GONE);

        }
    };
    private AdapterView.OnItemSelectedListener state_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                marketState = spnState.getSelectedItem().toString();
                //state = (State) spnState.getItemAtPosition(position);

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, locationListenerNetwork);
        }
        setContentView(R.layout.act_market_creator);
        setTitle("Market OnBoarding");
        checkInternetConnection();
        dbHelper = new DBHelper(this);
        marketDAO = new MarketDAO(this);
        World.init(this);
        account = new Account();
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        state= new State();
        market= new Market();
        awajima= new Awajima();
        marketArrayList= new ArrayList<>();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        createLocationRequest();
        getDeviceLocation();
        setInitialLocation();

        userBundle= new Bundle();
        mGoogleMap.setMyLocationEnabled(true);

        // Invoke LoaderCallbacks to retrieve and draw already saved locations in map
        getSupportLoaderManager().initLoader(0, null, this);
        edtMarketName = findViewById(R.id.market_Name_Tittle);
        edtMarketAddress = findViewById(R.id.address_market);
        edtMarketLGA = findViewById(R.id.province_lga);
        txtLoc22 = findViewById(R.id.whereMarketIs);
        txtLocTxt = findViewById(R.id.here_market);
        edtTown = findViewById(R.id.town_market);
        spnCountry = findViewById(R.id.market_country);
        spnState = findViewById(R.id.market_state_new);
        edtStateOutsideNig = findViewById(R.id.market_state_addition);
        spnMarketType = findViewById(R.id.market_type);
        btnSubmit = findViewById(R.id.create_market_new);
        layoutCompatState = findViewById(R.id.stateLayout);
        txtSelectState = findViewById(R.id.stateOfM);
        cardViewSelectSpn = findViewById(R.id.cardSelectState);
        cardViewEnterState = findViewById(R.id.cardSelectSOutside);
        //autoComPlaces= findViewById(R.id.place_auto);

        stateArrayList= new ArrayList<>();
        countryArrayList=World.getAllCountries();
        //autoComPlaces.setAdapter(new PlacesAutoCAdapter(this,R.layout.list_layout));
        List<com.google.android.libraries.places.api.model.Place.Field> fields = Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME);


        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autoM_frag);


        if (autocompleteFragment != null) {
            autocompleteFragment.setCountries("NG", "NG");
        }




        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                //.setTypeFilter(Arrays.asList(TypeFilter.ADDRESS.toString()))
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        if (autocompleteFragment != null) {
            autocompleteFragment.setPlaceFields(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID, com.google.android.libraries.places.api.model.Place.Field.NAME));
        }

        // Set up a PlaceSelectionListener to handle the response.
        if (autocompleteFragment != null) {
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {


                @Override
                public void onError(@NonNull Status status) {
                    // TODO: Handle the error.
                    Log.i(TAG, "An error occurred: " + status);
                }

                @Override
                public void onPlaceSelected(@NonNull com.google.android.libraries.places.api.model.Place place) {
                    Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                    if(place !=null){
                        autoAddress=place.getAddress();

                    }



                }
            });
        }


        spnCountry.setOnItemSelectedListener(country_listener);
        spnState.setOnItemSelectedListener(state_listener);
        newMarketID = ThreadLocalRandom.current().nextInt(1125, 10400);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        /*countryArrayAdapter = new ArrayAdapter<Country>(MarketCreatorAct.this, R.layout.support_simple_spinner_dropdown_item, countryArrayList);
        countryArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);*/
        //spnState.setAdapter(countryArrayAdapter);
        cancellationTokenSource = new CancellationTokenSource();
        /*mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                drawMarker(point);

                ContentValues contentValues = new ContentValues();

                contentValues.put(MARKET_LAT, point.latitude );

                contentValues.put(MARKET_LNG, point.longitude);

                contentValues.put(MARKET_MAP_ZOOM, mGoogleMap.getCameraPosition().zoom);
                //LocationInsertTask insertTask = new LocationInsertTask();

                //insertTask.execute(contentValues);

                Toast.makeText(MarketCreatorAct.this, "Marker is added to the Map", Toast.LENGTH_SHORT).show();
            }
        });

        mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {

                // Removing all markers from the Google Map
                mGoogleMap.clear();

                // Creating an instance of LocationDeleteTask
                LocationDeleteTask deleteTask = new LocationDeleteTask();

                // Deleting all the rows from SQLite database table
                deleteTask.execute();

            }
        });*/
        spnMarketType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    marketType = spnMarketType.getSelectedItem().toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //setInitialLocation();
        txtLoc22 = findViewById(R.id.whereMarketIs);
        txtLocTxt = findViewById(R.id.here_market);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    txtLocTxt.setVisibility(View.GONE);
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MarketCreatorAct.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                        }
                    });
                    userLocation = new LatLng(latitude, longitude);
                    cusLatLng = userLocation;


                    try {
                        Geocoder newGeocoder = new Geocoder(MarketCreatorAct.this, Locale.ENGLISH);
                        List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                        StringBuilder street = new StringBuilder();
                        if (Geocoder.isPresent()) {

                            txtLocTxt.setVisibility(View.VISIBLE);

                            Address newAddress = newAddresses.get(0);

                            String localityString = newAddress.getLocality();
                            country=newAddress.getCountryName();

                            street.append(localityString).append("");

                            txtLocTxt.setText(MessageFormat.format("Where you are:  {0},{1}/{2}", latitude, "" + longitude, street));
                            Toast.makeText(MarketCreatorAct.this, street,
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            txtLocTxt.setVisibility(View.GONE);
                            //go
                        }


                    } catch (IndexOutOfBoundsException | IOException e) {

                        Log.e("tag", e.getMessage());
                    }

                }
            }
        };
        ran = new Random();
        random = new SecureRandom();
        calendar = Calendar.getInstance();
        Animation translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateOFReg = mdformat.format(calendar.getTime());
        btnSubmit.startAnimation(translater);
        insertTask = new LocationInsertTask();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(translER);
                marketName = edtMarketName.getText().toString().trim();
                address = edtMarketAddress.getText().toString().trim();
                lga = edtMarketLGA.getText().toString();
                town = edtTown.getText().toString().trim();
                //autoAddress=autoComPlaces.getText().toString();
                if (address.isEmpty()) {
                    address = autoAddress;

                }
                @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_error_black_24dp);
                customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());
                if (TextUtils.isEmpty(edtMarketName.getText().toString())) {
                    edtMarketName.setError("Please Enter Market Name", customErrorIcon);
                    edtMarketName.setFocusable(true);
                    edtMarketName.requestFocus();
                }
                if (TextUtils.isEmpty(edtMarketAddress.getText().toString())) {
                    address = autoAddress;
                }
                if (TextUtils.isEmpty(edtMarketLGA.getText().toString())) {
                    edtMarketLGA.setError("Please Enter Market LGA", customErrorIcon);
                    edtMarketLGA.requestFocus();
                }
                if (TextUtils.isEmpty(edtTown.getText().toString())) {
                    edtTown.setError("Please Enter Market Town", customErrorIcon);
                    edtTown.requestFocus();
                } else {
                    checkInternetConnection();
                    market = new Market(newMarketID, profileID, marketName, marketType, address, town, lga, marketState, country, latitude, longitude, dateOFReg);
                    for (int i = 0; i < marketArrayList.size(); i++) {
                        try {
                            if (marketArrayList.get(i).getMarketName().contains(marketName) || marketArrayList.get(i).getMarketState().contains(marketState)) {
                                Toast.makeText(MarketCreatorAct.this, "This Market is already in use, here", Toast.LENGTH_LONG).show();
                                return;

                            } else {
                                isActivatedEnabled=true;
                                try {

                                    if (sqLiteDatabase != null) {
                                        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                                    }
                                } catch (SQLiteException e) {
                                    e.printStackTrace();
                                }

                                if (insertTask != null) {
                                    try {
                                        insertTask.execute(market);
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }

                                }
                                try {
                                    awajima.addMarket(market);
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }

                                if (dbHelper != null) {

                                    if (marketDAO != null) {
                                        try {
                                            dbID = marketDAO.saveMarket(market);
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        }

                                    }


                                }
                                if(userProfile !=null){
                                    userProfile.addMarket(market);
                                }

                                userBundle.putParcelable("Profile", userProfile);

                                Toast.makeText(MarketCreatorAct.this, "New Market has been successfully saved", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MarketCreatorAct.this, NewCustomerDrawer.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                intent.putExtras(userBundle);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                if (dbID > 0) {
                                    Toast.makeText(MarketCreatorAct.this, "New Market has been successfully saved", Toast.LENGTH_LONG).show();
                                    Intent intent44 = new Intent(MarketCreatorAct.this, NewCustomerDrawer.class);
                                    overridePendingTransition(R.anim.slide_in_right,
                                            R.anim.slide_out_left);
                                    intent44.putExtras(userBundle);
                                    intent44.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent44);


                                }
                                //sendOTPVerCode(otpPhoneNumber,mAuth,sponsorID,account,standingOrderAcct,customer,joinedDate,uFirstName,uSurname,uPhoneNumber,uAddress,uUserName,uPassword,customer,customerProfile,nIN,managerProfile,dateOfBirth,selectedGender,selectedOffice,selectedState,birthday,customerManager,dateOfBirth,profileID1,virtualAccountNumber,soAccountNumber, customerID,profileID2,birthdayID, investmentAcctID,itemPurchaseAcctID,promoAcctID,packageAcctID,profiles,customers,tellers,adminUserArrayList,superAdminArrayList);
                            }
                        } catch (NullPointerException e) {
                            System.out.println("Oops!");
                        }

                    }
                }


            }

        });

        btnSubmit.setOnClickListener(this::registerANewMarket);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                if(place !=null){
                    autoAddress=place.getAddress();
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    private boolean validateMarketName() {
        marketName = edtMarketName.getText().toString().trim();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_error_black_24dp);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());
        if(marketName.trim().isEmpty()) {
            edtMarketName.setError("Please Enter Market Name", customErrorIcon);
            edtMarketName.setFocusable(true);
            //edtMarketName.requestFocus();
            requestFocus(edtMarketName);
            return false;
        } else {
            btnSubmit.setEnabled(false);
        }
        return true;
    }

    private boolean validateMarketAddress() {
        address = edtMarketAddress.getText().toString().trim();
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_error_black_24dp);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());
        if(address.trim().isEmpty()) {
            edtMarketAddress.setError("Please Enter Market Address", customErrorIcon);
            //edtMarketAddress.requestFocus();
            requestFocus(edtMarketAddress);
            return false;
        } else {
            btnSubmit.setEnabled(false);
        }
        return true;
    }

    private boolean validateMarketLGA() {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_error_black_24dp);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());
        lga = edtMarketLGA.getText().toString();
        if(lga.trim().isEmpty()){
            edtMarketLGA.setError("Please Enter Market LGA", customErrorIcon);
            //edtMarketLGA.requestFocus();
            requestFocus(edtMarketLGA);
            return false;
        }else{
            btnSubmit.setEnabled(false);
        }
        return true;
    }

    private boolean validateMarketTown() {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_error_black_24dp);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());
        town = Objects.requireNonNull(edtTown.getText()).toString().trim();
        if(town.trim().isEmpty()) {
            edtTown.setError("Please Enter Market Town", customErrorIcon);
            edtTown.requestFocus();
            requestFocus(edtTown);
            return false;
        } else {
            btnSubmit.setEnabled(false);
        }
        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;
        private MyTextWatcher(View view) { this.view = view; }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.market_Name_Tittle:
                    validateMarketName();
                    break;
                case R.id.address_market:
                    validateMarketAddress();
                    break;
                case R.id.province_lga:
                    validateMarketLGA();
                    break;
                case R.id.town_market:
                    validateMarketTown();
                    break;
            }
        }
    }





    private void drawMarker(LatLng point){
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(point);

        mGoogleMap.addMarker(markerOptions);
    }
    private void addCircle(LatLng latLng, float radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0));
        circleOptions.fillColor(Color.argb(64, 255, 0, 0));
        circleOptions.strokeWidth(4);
        mGoogleMap.addCircle(circleOptions);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mGoogleMap=googleMap;
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            cusLatLng = new LatLng(latitude, longitude);


            setResult(Activity.RESULT_OK, new Intent());


        } else {
            cusLatLng = new LatLng(4.52871, 7.44507);
            Log.d(TAG, "Current location is null. Using defaults.");
        }
        MarkerOptions markerOptions = new MarkerOptions()
                .position(cusLatLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                .title("Where you are");
        if (mGoogleMap != null) {

            Marker mMarker = mGoogleMap.addMarker(markerOptions);
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(19));
        }

    }
    private void doOtpNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Your Market Registration Message")
                        .setContentText("We have received your new Market Registration,wait for verification");

        Intent notificationIntent = new Intent(this, MarketCreatorAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    private void setInitialLocation() {
        createLocationRequest();
        txtLoc22 = findViewById(R.id.whereMarketIs);
        txtLocTxt = findViewById(R.id.here_market);
        final double[] newLat = {0.00};
        final double[] newLng = {0.00};
        geocoder = new Geocoder(this, Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(MarketCreatorAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MarketCreatorAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
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
                                Log.d(TAG, "Current location is null. Using defaults.");
                            }
                            mCurrentLocation =location2;

                        }

                    });



        }

        if(mCurrentLocation !=null){
            newLat[0] = mCurrentLocation.getLatitude();
            newLng[0] = mCurrentLocation.getLongitude();

            try {
                if (Geocoder.isPresent()) {
                    try {
                        addresses = geocoder.getFromLocation(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {

                        address = addresses.get(0).getAddressLine(0);
                        localityString = addresses.get(0).getSubLocality();
                        city = addresses.get(0).getAdminArea();
                        //txtLoc.setVisibility(View.VISIBLE);



                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    StringBuilder street = new StringBuilder();



                    street.append(localityString).append("");



                    Toast.makeText(MarketCreatorAct.this, street,
                            Toast.LENGTH_SHORT).show();

                } else {

                    txtLocTxt.setVisibility(View.GONE);
                    txtLoc22.setVisibility(View.GONE);
                    //go
                }

                Geocoder newGeocoder = new Geocoder(MarketCreatorAct.this, Locale.ENGLISH);



            } catch (IndexOutOfBoundsException e) {

                Log.e("tag", e.getMessage());
            }

        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri uri = UserContentProvider.BASE_CONTENT_URI;
        return new CursorLoader(this, uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        int locationCount = 0;
        double lat=0;
        double lng=0;
        float zoom=0;

        /*locationCount = arg1.getCount();

        arg1.moveToFirst();

        for(int i=0;i<locationCount;i++){

            lat = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LAT));

            lng = arg1.getDouble(arg1.getColumnIndex(LocationsDB.FIELD_LNG));

            zoom = arg1.getFloat(arg1.getColumnIndex(LocationsDB.FIELD_ZOOM));

            LatLng location = new LatLng(lat, lng);

            drawMarker(location);

            arg1.moveToNext();
        }*/

        if(locationCount>0){
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,lng)));

            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void>{
        @Override
        protected Void doInBackground(ContentValues... contentValues) {

            /** Setting up values to insert the clicked location into SQLite database */
            getContentResolver().insert(UserContentProvider.BASE_CONTENT_URI, contentValues[0]);
            return null;
        }

        public void execute(Market market) {

        }
    }

    private class LocationDeleteTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            /** Deleting all the locations stored in SQLite database */
            getContentResolver().delete(UserContentProvider.BASE_CONTENT_URI, null, null);
            return null;
        }
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    protected void createLocationRequest() {
        if (!hasPermissions(MarketCreatorAct.this, PERMISSIONS33)) {
            ActivityCompat.requestPermissions(MarketCreatorAct.this, PERMISSIONS33, PERMISSION_ALL33);

        }


        request = new LocationRequest();
        request.setSmallestDisplacement(10);
        request.setFastestInterval(50000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(3);



    }
    @Override
    protected void onStart() {
        super.onStart();

        geocoder = new Geocoder(this, Locale.getDefault());
        getDeviceLocation();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

    }


    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            location = task.getResult();
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                cusLatLng = new LatLng(latitude, longitude);

                            }
                        } else {

                            cusLatLng = new LatLng(4.52871, 7.44507);
                            Log.d(TAG, "Current location is null. Using defaults.");
                            /*googleMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(cusLatLng, 17));
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);*/
                        }
                    }
                });
                txtLoc22 = findViewById(R.id.whereMarketIs);
                txtLocTxt = findViewById(R.id.here_market);
                try {
                    geocoder = new Geocoder(MarketCreatorAct.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {
                        //txtLoc.setVisibility(View.VISIBLE);

                        android.location.Address returnAddress = addresses.get(1);

                        String localityString = returnAddress.getAdminArea();
                        country=returnAddress.getCountryName();

                        str.append(localityString).append("");
                        //txtLoc.setText(MessageFormat.format("Your are here:  {0},{1}/{2}", latitude, longitude, str));


                        Toast.makeText(MarketCreatorAct.this, str,
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
                                    Log.d(TAG, "Current location is null. Using defaults.");
                                }
                                location=location2;
                            }

                        });


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


                //txtLoc.setText("My Loc:" + latitude + "," + longitude + "/" + city);


            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
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

    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
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

                    Toast.makeText(MarketCreatorAct.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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
                                    location=location2;


                                }
                            });
                    MarketCreatorAct.this.cusLatLng = userLocation;

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


    public void registerANewMarket(View view) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

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

}