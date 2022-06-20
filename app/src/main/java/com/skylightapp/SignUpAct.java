package com.skylightapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.preference.PreferenceManager;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.auth.ui.phone.SpacedEditText;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountInvestment;
import com.skylightapp.Classes.AccountItemPurchase;
import com.skylightapp.Classes.AccountPromo;
import com.skylightapp.Classes.AccountSavings;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.PinEntryView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.RoomController;
import com.skylightapp.Classes.RoomRepo;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Classes.User;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Interfaces.ProfileDao;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_LATLONG;
import static com.skylightapp.Classes.ImageUtil.TAG;
import static com.skylightapp.Classes.Profile.PICTURE_URI;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_NIN;
import static com.skylightapp.Classes.Profile.PROFILE_USERNAME;
import static com.skylightapp.Classes.Profile.PROFILE_ADDRESS;
import static com.skylightapp.Classes.Profile.PROFILE_COUNTRY;
import static com.skylightapp.Classes.Profile.PROFILE_DATE_JOINED;
import static com.skylightapp.Classes.Profile.PROFILE_DOB;
import static com.skylightapp.Classes.Profile.PROFILE_EMAIL;
import static com.skylightapp.Classes.Profile.PROFILE_FIRSTNAME;
import static com.skylightapp.Classes.Profile.PROFILE_GENDER;
import static com.skylightapp.Classes.Profile.PROFILE_NEXT_OF_KIN;
import static com.skylightapp.Classes.Profile.PROFILE_OFFICE;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.Profile.PROFILE_ROLE;
import static com.skylightapp.Classes.Profile.PROFILE_SPONSOR_ID;
import static com.skylightapp.Classes.Profile.PROFILE_STATE;
import static com.skylightapp.Classes.Profile.PROFILE_STATUS;
import static com.skylightapp.Classes.Profile.PROFILE_SURNAME;
import static com.skylightapp.Database.DBHelper.DATABASE_NAME;
import static com.skylightapp.Database.DBHelper.DATABASE_NEW_VERSION;
import static com.skylightapp.Database.DBHelper.DATABASE_VERSION;
import static com.skylightapp.Transactions.OurConfig.TWILIO_ACCOUNT_SID;
import static com.skylightapp.Transactions.OurConfig.TWILIO_AUTH_TOKEN;

public class SignUpAct extends AppCompatActivity implements LocationListener {
    public static final int PICTURE_REQUEST_CODE = 505;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    SharedPreferences userPreferences;
    AppCompatEditText phone_number;
    AppCompatEditText email_address;
    AppCompatEditText edtNIN;
    String stringNIN, timeLineTime;
    GoogleMap googleMap, mMap;
    AppCompatEditText firstName;
    AppCompatEditText surname1;
    AppCompatEditText userName;
    AppCompatEditText password, edtSponsorID;
    AppCompatEditText address_2;
    Bitmap thumbnail;
    //private GoogleApiClient googleApiClient;
    protected DatePickerDialog datePickerDialog;
    Random ran;
    private double accountBalance;
    SecureRandom random;

    Context context;
    String dateOfBirth;
    PreferenceManager preferenceManager;
    private Bundle bundle;
    AppCompatTextView dobText;
    String uPhoneNumber, accountName, skylightMFb;
    private ProgressDialog progressDialog;
    int profileID, birthdayID, messageID;

    private ProgressBar loadingPB;
    String ManagerSurname;
    String managerFirstName, uPassword;
    String managerPhoneNumber1;
    String managerEmail, managerGender;
    String managerAddress;
    private boolean locationPermissionGranted;
    String managerUserName;
    String mLastUpdateTime, selectedGender, selectedOffice, selectedState;
    Birthday birthday;

    AppCompatRadioButton rbTeller, rbCustomer;
    String uFirstName, uEmail, uSurname, uAddress, uUserName;

    int virtualAccountNumber, soAccountNumber;
    int customerID;
    int profileID1;

    AppCompatSpinner state, office, spnGender;
    DBHelper dbHelper;
    Profile managerProfile;

    Location mCurrentLocation = null;
    String daysRemaining;
    int daysBTWN;

    String acct;
    SQLiteDatabase sqLiteDatabase;
    Date date;

    AccountTypes accountTypeStr;

    Gson gson, gson1;
    String json, json1, nIN;
    Profile userProfile, customerProfile, lastProfileUsed;
    String selectedCountry, selectedBank, bankName, bankNumber, officePref, userNamePref;

    private String picturePath;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String REQUIRED = "Required";
    private static final int RESULT_CAMERA_CODE = 22;
    int investmentAcctID, savingsAcctID, itemPurchaseAcctID, promoAcctID, packageAcctID;
    int comAcctID;
    int profileFId;
    int birthdayFID;
    int acctFID;
    SQLiteDatabase sqLiteDatabaseObj;
    //daysRemaining = birthday.g(currentDate, dateOfBirth);
    //daysBTWN = birthday.getDaysInBetween(currentDate, dateOfBirth);
    SharedPreferences.Editor editor;
    RadioGroup userTypes;
    ByteArrayOutputStream bytes;
    DBHelper applicationDb;
    DatePicker picker;
    String userProfileType;
    Customer customer;
    //Uri selectedImage;
    int selectedId, day, month, year, newMonth;
    String userType;
    String userRole, sponsorIDString;
    String selectedUser;
    int sponsorID;
    String address;
    Spinner spnUsers;
    Location customerLoc;
    LatLng userLocation;
    int daysInBetween;
    File destination;
    LatLng cusLatLng;
    double latitude = 0;
    double longitude = 0;
    Geocoder geocoder;
    Bundle userLocBundle;
    ArrayList<Profile> profiles;
    ArrayList<Customer> customers;
    CircleImageView profilePix;
    AppCompatImageView imgGreetings;
    private CustomerManager customerManager, customerManager2;
    ArrayList<CustomerManager> tellers;
    ArrayList<AdminUser> adminUserArrayList;
    ArrayList<UserSuperAdmin> superAdminArrayList;

    private Uri mImageUri;
    //private ProgressBar uploadProgressBar;
    ContentLoadingProgressBar progressBar;

    private int managerProfileID;

    List<Address> addresses;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private Calendar cal;
    AppController appController;
    private static boolean isPersistenceEnabled = false;
    private Account account;
    private StandingOrderAcct standingOrderAcct;
    private FusedLocationProviderClient fusedLocationClient;
    private TimeLine timeLine;
    AccountInvestment accountInvestment;
    AccountItemPurchase accountItemPurchase;
    AccountSavings accountSavings;
    AccountPromo accountPromo;
    String joinedDate, customerName;
    String code;
    Profile userProfile1, userProfile2, userProfile3;
    private static final int REQUEST_CHECK_SETTINGS = 1000;
    private String mVerificationId;
    double lat, lng;

    String dob;
    String SharedPrefState, otpMessage, smsMessage;
    private User user;
    //private OtpTextView otpTextView;
    private AppCompatButton btnVerifyOTPAndSignUp;
    private int otpDigit;
    String otpPhoneNumber;
    PinEntryView pinEntry;
    SpacedEditText edtOtp1, edtOtp2, edtOtp3, edtOtp4;
    private LinearLayoutCompat layoutOTP, layoutPreOTP;
    private EditText[] editTexts;
    private Button btnSendOTP;
    Location location;
    private View mapView;
    AppCompatTextView txtLoc, locTxt;
    private LocationRequest request;

    Marker now;

    private CancellationTokenSource cancellationTokenSource;
    CameraUpdate cLocation;
    TextView otpTxt;
    private Calendar calendar;
    private PinEntryView pinEntryView;
    InstallReferrerClient referrerClient;
    CountryCodePicker ccp;
    String referrer = "";
    private LocationCallback locationCallback;
    public static final Boolean USINGROOM = true;
    RoomController mRoomDB;
    RoomRepo roomRepo;
    ProfileDao roomTableDao;
    SupportSQLiteDatabase supportSQLiteDatabase;


    private static final String PREF_NAME = "skylight";
    private LocationManager locationManager;
    String regEx =
            "^(([w-]+.)+[w-]+|([a-zA-Z]{1}|[w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[w-]+.)+[a-zA-Z]{2,4})$";

    private FirebaseAuth.AuthStateListener mAuthListener;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    SupportMapFragment mapFrag;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    int PERMISSION_ALL33 = 2;
    String[] PERMISSIONS33 = {
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS, Manifest.permission.SEND_SMS
    };

    // private static final String BASE_URL="https://firebasestorage.googleapis.com/";

    ActivityResultLauncher<Intent> mGetPixContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            if (result.getData() != null) {
                                //logo = findViewById(R.id.imageLogo);
                                Intent data = result.getData();
                                mImageUri = data.getData();
                                dbHelper.insertProfilePicture(profileID1, customerID, mImageUri);
                                /*if (selectedImage != null) {
                                    logo.setImageBitmap(getScaledBitmap(selectedImage));
                                } else {
                                    Toast.makeText(NewSignUpActivity.this, "Error getting Image",
                                            Toast.LENGTH_SHORT).show();
                                }*/

                            }

                            Toast.makeText(SignUpAct.this, "Image picking returned successful", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(SignUpAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            //finish();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + result.getResultCode());
                    }
                }
                /*@Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        finish();
                    }
                }*/
            });
    ActivityResultLauncher<Intent> mStartLocForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            cusLatLng = intent.getParcelableExtra("CusLatLng");
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
                    Toast.makeText(SignUpAct.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });


            try {
                Geocoder newGeocoder = new Geocoder(SignUpAct.this, Locale.ENGLISH);
                List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                StringBuilder street = new StringBuilder();
                if (Geocoder.isPresent()) {

                    locTxt.setVisibility(View.VISIBLE);

                    android.location.Address newAddress = newAddresses.get(0);

                    String localityString = newAddress.getLocality();

                    street.append(localityString).append("");

                    locTxt.setText(MessageFormat.format("Where you are:  {0},{1}/{2}",latitude, longitude, street));
                    Toast.makeText(SignUpAct.this, street,
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
        setContentView(R.layout.act_sign_up);
        dbHelper = new DBHelper(this);
        createLocationRequest();
        referrerClient = InstallReferrerClient.newBuilder(this).build();
        locationManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        setInitialLocation();
        txtLoc = findViewById(R.id.hereYou333);
        locTxt = findViewById(R.id.whereYou222);

        String provider = locationManager.getBestProvider(criteria, true);
        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            }
            locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, locationListenerNetwork);
        }
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    locTxt.setVisibility(View.GONE);
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignUpAct.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                        }
                    });
                    userLocation = new LatLng(latitude,longitude );
                    cusLatLng=userLocation;


                    try {
                        Geocoder newGeocoder = new Geocoder(SignUpAct.this, Locale.ENGLISH);
                        List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                        StringBuilder street = new StringBuilder();
                        if (Geocoder.isPresent()) {

                            locTxt.setVisibility(View.VISIBLE);

                            android.location.Address newAddress = newAddresses.get(0);

                            String localityString = newAddress.getLocality();

                            street.append(localityString).append("");

                            locTxt.setText(MessageFormat.format("Where you are:  {0},{1}/{2}",latitude, ""+longitude, street));
                            Toast.makeText(SignUpAct.this, street,
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
        RoomController db = Room.databaseBuilder(getApplicationContext(),

                RoomController.class, DATABASE_NAME).build();



        getSkylightRefferer(referrerClient);
        btnSendOTP = findViewById(R.id.sendOTPCode);
        progressBar = findViewById(R.id.progressBar);
        accountTypeStr = null;
        spnGender = findViewById(R.id.gender);
        imgGreetings = findViewById(R.id.Greetings);
        profilePix = findViewById(R.id.profile_image_j);
        edtSponsorID = findViewById(R.id.user_sponsor);
        btnVerifyOTPAndSignUp = findViewById(R.id.idBtnVerify);
        layoutOTP = findViewById(R.id.layoutOtp);
        otpTxt = findViewById(R.id.textOTP);

        customers = null;
        ran = new Random();
        random = new SecureRandom();
        SQLiteDataBaseBuild();
        sqLiteDatabase = dbHelper.getWritableDatabase();

        getDeviceLocation();
        getUserLocation();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        layoutPreOTP = findViewById(R.id.layoutSign);

        cancellationTokenSource = new CancellationTokenSource();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (cusLatLng == null) {
                            mStartLocForResult.launch(new Intent(SignUpAct.this, NewLocAct.class));

                        }
                    }
                });

            }
        }).start();

        userProfile = new Profile();
        account = new Account();
        userProfile1 = new Profile();
        userProfile2 = new Profile();
        userProfile3 = new Profile();
        customerManager = new CustomerManager();
        customerManager2 = new CustomerManager();
        gson1 = new Gson();
        gson = new Gson();
        customer = new Customer();
        managerProfile = new Profile();
        standingOrderAcct = new StandingOrderAcct();
        account = new Account();
        customerProfile = new Profile();
        birthday = new Birthday();
        //dbHelper.onUpgrade(sqLiteDatabase, dbHelper.getDatabaseVersion(), DBHelper.DATABASE_NEW_VERSION);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson1.fromJson(json, CustomerManager.class);
        loadProfiles(dbHelper);
        otpDigit = ThreadLocalRandom.current().nextInt(122, 1631);
        messageID = ThreadLocalRandom.current().nextInt(1125, 10400);
        otpMessage = "&message=" + "Hello Skylight, Your OTP Code is " + otpDigit;

        profileID1 = random.nextInt((int) (Math.random() * 1400) + 1115);
        virtualAccountNumber = random.nextInt((int) (Math.random() * 123045) + 100123);
        soAccountNumber = random.nextInt((int) (Math.random() * 133044) + 100125);
        customerID = random.nextInt((int) (Math.random() * 1203) + 1101);
        //profileID2 = random.nextInt((int) (Math.random() * 1001) + 101);

        investmentAcctID = random.nextInt((int) (Math.random() * 1011) + 1010);
        savingsAcctID = random.nextInt((int) (Math.random() * 1101) + 1010);
        itemPurchaseAcctID = random.nextInt((int) (Math.random() * 1010) + 1010);
        promoAcctID = random.nextInt((int) (Math.random() * 1111) + 1010);
        packageAcctID = random.nextInt((int) (Math.random() * 1000) + 1010);
        profiles = null;

        accountInvestment = new AccountInvestment();
        accountItemPurchase = new AccountItemPurchase();
        accountPromo = new AccountPromo();
        accountSavings = new AccountSavings();
        tellers = null;
        adminUserArrayList = null;
        superAdminArrayList = null;
        cal = Calendar.getInstance();
        dobText = findViewById(R.id.dob_text_signUp);
        dobText.setOnClickListener(this::dobPicker);

        dobText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                newMonth = month + 1;
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SignUpAct.this, R.style.DatePickerDialogStyle, mDateSetListener, day, month, year);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
                dialog.show();
                dob = year + "-" + newMonth + "-" + day;
                dateOfBirth = day + "-" + newMonth + "-" + year;
                dobText.setText("Your date of Birth:" + dob);

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                Log.d(TAG, "onDateSet: date of Birth: " + day + "-" + month + "-" + year);
                dob = year + "-" + newMonth + "-" + day;
                dateOfBirth = day + "-" + newMonth + "-" + year;
                dobText.setText("Your date of Birth:" + dob);


            }


        };
        dateOfBirth = day + "-" + newMonth + "-" + year;

        //token= getIntent().getStringExtra("TOKEN");
        birthdayID = random.nextInt((int) (Math.random() * 1001) + 1010);
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        joinedDate = mdformat.format(calendar.getTime());

        //dobText.setText("Your date of Birth:"+dateOfBirth);

        birthday = new Birthday();



        if (managerProfile != null) {
            ManagerSurname = managerProfile.getProfileLastName();
            managerFirstName = managerProfile.getProfileFirstName();
            profileID = managerProfile.getPID();
            //profileID1=profileID;
            managerPhoneNumber1 = managerProfile.getProfilePhoneNumber();
            managerEmail = managerProfile.getProfileEmail();
            managerUserName = managerProfile.getProfileUserName();
            userType = managerProfile.getProfileMachine();
            userRole = managerProfile.getProfileRole();

        } else {
            profileID = profileID1;

        }

        profiles = new ArrayList<>();
        customers = new ArrayList<>();
        tellers = new ArrayList<>();
        adminUserArrayList = new ArrayList<>();
        superAdminArrayList = new ArrayList<>();

        nIN = null;

        profilePix.setOnClickListener(this::doSelectPix);
        managerProfile = new Profile();

        StringBuilder welcomeString = new StringBuilder();

        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 5 && timeOfDay < 12) {
            //welcomeString.append(getString(R.string.good_morning));
            imgGreetings.setImageResource(R.drawable.good_morn3);
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            welcomeString.append(getString(R.string.good_afternoon));
            imgGreetings.setImageResource(R.drawable.good_after1);
        } else {
            welcomeString.append(getString(R.string.good_evening));
            imgGreetings.setImageResource(R.drawable.good_even2);
        }
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String[] days = getResources().getStringArray(R.array.days);
        String dow = "";

        switch (day) {
            case Calendar.SUNDAY:
                dow = days[0];
                break;
            case Calendar.MONDAY:
                dow = days[1];
                break;
            case Calendar.TUESDAY:
                dow = days[2];
                break;
            case Calendar.WEDNESDAY:
                dow = days[3];
                break;
            case Calendar.THURSDAY:
                dow = days[4];
                break;
            case Calendar.FRIDAY:
                dow = days[5];
                break;
            case Calendar.SATURDAY:
                dow = days[6];
                break;
            default:
                break;
        }


        welcomeString.append("Welcome" + "")
                .append(managerFirstName + "")
                .append("How are you today? ")
                .append(getString(R.string.happy))
                .append(dow);

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = spnGender.getSelectedItem().toString();
                selectedGender = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        office = findViewById(R.id.office5);
        office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOffice = office.getSelectedItem().toString();
                selectedOffice = (String) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        state = findViewById(R.id.state1);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = state.getSelectedItem().toString();
                selectedState = (String) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        phone_number = findViewById(R.id.phone_number);
        email_address = findViewById(R.id.email_address);
        address_2 = findViewById(R.id.address_all);
        firstName = findViewById(R.id.first_Name_00);
        userName = findViewById(R.id.user_name_1);
        surname1 = findViewById(R.id.surname1);
        password = findViewById(R.id.user_password_sig);
        edtNIN = findViewById(R.id.NIN);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        String email = email_address.getText().toString().trim();

        ccp.registerCarrierNumberEditText(phone_number);
        ccp.getFullNumberWithPlus();

        ccp.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                // your code
            }
        });

        email_address.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (email.matches(emailPattern) && s.length() > 0) {
                    Toast.makeText(SignUpAct.this, "valid email address", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(SignUpAct.this, "Invalid email address", Toast.LENGTH_SHORT).show();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        profilePix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(SignUpAct.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(SignUpAct.this, PERMISSIONS, PERMISSION_ALL);
                }

                final PopupMenu popup = new PopupMenu(SignUpAct.this, profilePix);
                popup.getMenuInflater().inflate(R.menu.profile, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.Camera) {
                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, RESULT_CAMERA_CODE);

                        }

                        if (item.getItemId() == R.id.Gallery) {
                            Intent i = new Intent(
                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }
                        if (item.getItemId() == R.id.cancel_action) {
                            // finish();
                        }

                        return true;
                    }
                });
                popup.show();//showing popup menu


            }

            protected void onActivityResult(int requestCode, int resultCode, Intent data) {

                if ((data != null) && requestCode == RESULT_CAMERA_CODE) {
                    mImageUri = data.getData();
                    //dbHelper= new DBHelper(SignUpAct.this);
                    //dbHelper.insertProfilePicture(profileID1,customerID,photoUri);


                }
                if ((data != null) && requestCode == RESULT_LOAD_IMAGE) {
                    mImageUri = data.getData();
                    //dbHelper= new DBHelper(SignUpAct.this);
                    //dbHelper.insertProfilePicture(profileID1,customerID,photoUri);


                }

            }
        });

        if (managerProfile != null) {
            ManagerSurname = managerProfile.getProfileLastName();
            managerFirstName = managerProfile.getProfileFirstName();
            profileID = managerProfile.getPID();
            //profileID1=profileID;
            managerPhoneNumber1 = managerProfile.getProfilePhoneNumber();
            managerEmail = managerProfile.getProfileEmail();
            managerUserName = managerProfile.getProfileUserName();
            userType = managerProfile.getProfileMachine();
            userRole = managerProfile.getProfileRole();

        } else {
            profileID = profileID1;

        }


        btnVerifyOTPAndSignUp.setOnClickListener(this::verifyOTP);
        pinEntryView = (PinEntryView) findViewById(R.id.txt_pin_entry);
        customers = dbHelper.getAllCustomers11();
        Animation translater = AnimationUtils.loadAnimation(this, R.anim.bounce);

        btnSendOTP.setOnClickListener(this::sendOTPToCus);
        btnSendOTP.startAnimation(translater);
       /*btnSendOTP.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Button view = (Button) v;
                        view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL: {
                        Button view = (Button) v;
                        view.getBackground().clearColorFilter();
                        view.invalidate();
                        break;
                    }
                }
                return true;
            }
        });*/
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(translater);
                uFirstName = firstName.getText().toString().trim();
                uSurname = surname1.getText().toString().trim();
                uEmail = email_address.getText().toString();
                sponsorIDString = edtSponsorID.getText().toString().trim();
                uAddress = Objects.requireNonNull(address_2.getText()).toString();
                uPassword = password.getText().toString().trim();
                uPhoneNumber = Objects.requireNonNull(phone_number.getText()).toString();
                uUserName = userName.getText().toString();
                boolean usernameTaken = false;
                nIN = null;
                customerName = uSurname + "," + uFirstName;
                try {
                    sponsorID = Integer.parseInt(sponsorIDString);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                try {

                    if (TextUtils.isEmpty(uFirstName)) {
                        firstName.setError("Please enter Your First Name");
                    } else if (TextUtils.isEmpty(uSurname)) {
                        surname1.setError("Please enter your Last/SurName");
                    } else if (TextUtils.isEmpty(uPassword)) {
                        password.setError("Please enter your Password");
                    } else if (uPhoneNumber.isEmpty() || uPhoneNumber.length() < 11) {
                        phone_number.setError("Enter a valid mobile Number");
                        phone_number.requestFocus();
                    } else if (TextUtils.isEmpty(uUserName)) {
                        userName.setError("Please enter your userName");
                    } else if (TextUtils.isEmpty(uAddress)) {
                        address_2.setError("Please enter Address");
                    } else {

                        layoutOTP.setVisibility(View.VISIBLE);
                        layoutPreOTP.setVisibility(View.GONE);
                        otpTxt.setText("Your OTP is:" + otpDigit);
                        otpPhoneNumber = "+234" + uPhoneNumber;
                        //doOtpNotification();
                        //sendOTPMessage(otpPhoneNumber,otpMessage);
                        //sendOTPVerCode(otpPhoneNumber,mAuth,sponsorID,account,standingOrderAcct,customer,joinedDate,uFirstName,uSurname,uPhoneNumber,uAddress,uUserName,uPassword,customer,customerProfile,nIN,managerProfile,dateOfBirth,selectedGender,selectedOffice,selectedState,birthday,customerManager,dateOfBirth,profileID1,virtualAccountNumber,soAccountNumber, customerID,profileID2,birthdayID, investmentAcctID,itemPurchaseAcctID,promoAcctID,packageAcctID,profiles,customers,tellers,adminUserArrayList,superAdminArrayList);

                        for (int i = 0; i < customers.size(); i++) {
                            try {
                                if (customers.get(i).getCusPhoneNumber().equalsIgnoreCase(uPhoneNumber)) {
                                    Toast.makeText(SignUpAct.this, "This Phone Number is already in use, here", Toast.LENGTH_LONG).show();
                                    return;

                                } else {
                                    layoutOTP.setVisibility(View.VISIBLE);
                                    layoutPreOTP.setVisibility(View.GONE);
                                    otpPhoneNumber = "+234" + uPhoneNumber;
                                    doOtpNotification();
                                    sendOTPMessage(otpPhoneNumber, otpMessage);

                                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                        //dbHelper = new DBHelper(this);
                                        sqLiteDatabase = dbHelper.getWritableDatabase();


                                    }



                                    dbHelper.insertMessage(profileID, customerID, messageID, otpMessage, "Skylight", customerName, selectedOffice, joinedDate);

                                    //sendOTPVerCode(otpPhoneNumber,mAuth,sponsorID,account,standingOrderAcct,customer,joinedDate,uFirstName,uSurname,uPhoneNumber,uAddress,uUserName,uPassword,customer,customerProfile,nIN,managerProfile,dateOfBirth,selectedGender,selectedOffice,selectedState,birthday,customerManager,dateOfBirth,profileID1,virtualAccountNumber,soAccountNumber, customerID,profileID2,birthdayID, investmentAcctID,itemPurchaseAcctID,promoAcctID,packageAcctID,profiles,customers,tellers,adminUserArrayList,superAdminArrayList);
                                }
                            } catch (NullPointerException e) {
                                System.out.println("Oops!");
                            }

                        }
                        //long accountNumber = Long.parseLong(String.valueOf((long) (Math.random() * 10501) + 10010));


                    }

                } catch (Exception e) {
                    System.out.println("Oops!");
                }

            }
        });
        btnVerifyOTPAndSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(translater);
                boolean usernameTaken = false;
                nIN = null;
                accountTypeStr = AccountTypes.EWALLET;
                //long accountNumber = Long.parseLong(String.valueOf((long) (Math.random() * 10501) + 10010));

                otpPhoneNumber = "+234" + uPhoneNumber;
                code = pinEntryView.getText().toString().trim();

                if(code !=null){
                    if (code.equals(String.valueOf(otpDigit))) {
                        Toast.makeText(SignUpAct.this, "OTP verification, a Success", Toast.LENGTH_SHORT).show();
                        startProfileActivity(sponsorID, cusLatLng, account, standingOrderAcct, joinedDate, uFirstName, uSurname, uPhoneNumber, uAddress, uUserName, uPassword, customer, customerProfile, nIN, managerProfile, dateOfBirth, selectedGender, selectedOffice, selectedState, birthday, customerManager, dateOfBirth, profileID1, virtualAccountNumber, soAccountNumber, customerID, birthdayID, investmentAcctID, itemPurchaseAcctID, promoAcctID, packageAcctID, customers);

                    } else {
                        Toast.makeText(SignUpAct.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        pinEntryView.setText("Wrong OTP...");
                        pinEntryView.requestFocus();
                    }
                    progressBar.setVisibility(View.VISIBLE);

                }


            }
        });


    }
    public void loadProfiles(DBHelper dbHelper) {
        mRoomDB = Room.databaseBuilder(this,RoomController.class,DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        roomTableDao = mRoomDB.roomProfileTableDao();

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            //dbHelper = new DBHelper(this);
            sqLiteDatabase = dbHelper.getWritableDatabase();

            dbHelper.insertCustomer11(0, 10, "Ezekiel", "Gwun-orene", "07038843102", "lumgwun1@gmail.com", "1983-04-25", "male", "Ilabuchi", "Rivers", "", "2022-04-19", "Lumgwun", "@Awajima1", Uri.parse(""), "Customer");

            dbHelper.insertTimeLine("Sign up", "", "2022-04-19", mCurrentLocation);

            //dbHelper.saveNewProfile(userProfile);

            dbHelper.insertUser112(0, 102, "Ezekiel", "Gwun-orene", "07038843102", "lumgwun1@gmail.com", "1983-04-25", "male", "Ilabuchi", "Rivers", "", "2022-04-19", "Lumgwun", "@Awajima1", "", "", "SuperAdmin");

            userProfile1 = new Profile(0, "Emmanuel", "Becky", "08069524599", "urskylight@gmail.com", "1980-04-19", "female", "Skylight", "", "Rivers", "Elelenwo", "2022-04-19", "SuperAdmin", "Skylight4ever", "@Awajima2", "Confirmed", "");
            userProfile2= new Profile(0,"Benedict", "Benedict", "08059250176", "bener@gmail.com", "25/04/1989", "male", "PH", "","Rivers", "Elelenwo", "19/04/2022","Customer", "Lumgwun", "@Awajima3","Confirmed","");

            dbHelper.saveNewProfile(userProfile1);
            dbHelper.saveNewProfile(userProfile2);


            /*List<Profile> profileArrayList = new ArrayList<Profile>();
            for (Profile profile : profileArrayList) {
                profileArrayList.add(userProfile1);
                profileArrayList.add(userProfile2);
                dbHelper.saveNewProfile(profile);
            }*/
        }


    }
    private void createRoomTableIfNeeded() {
        SQLiteDatabase db = SQLiteDatabase.openDatabase(this.getDatabasePath(DATABASE_NAME).getPath(),null,SQLiteDatabase.OPEN_READWRITE);
        Cursor csr = db.query("sqlite_master",null,"name=?  AND type=?",new String[]{"RoomProfileTable","table"},null,null,null);
        int rowcount = csr.getCount();
        csr.close();
        //String P_first_name ,String p_role,int cus_id ,String p_username , String P_surname , String p_state, String p_password, String P_email, String p_sponsor , String P_gender , String P_street , String p_office, String p_phone , String p_join_date , String P_dob ,String p_next_of_kin , String picture_uri , String profile_NIN , String p_status , int profile_id

        if (rowcount == 0) {
            db.execSQL("CREATE TABLE IF NOT EXISTS `RoomProfileTable` (`profile_id` INTEGER, `P_surname` TEXT, `P_first_name` TEXT,`p_phone` TEXT,`P_email` TEXT,`P_dob` TEXT,`P_gender` TEXT,`P_street` TEXT,`profile_NIN` TEXT,`p_office` TEXT,`p_join_date` TEXT,`p_state` TEXT,`p_role` TEXT,`p_next_of_kin` TEXT,`p_username` TEXT,`p_password` TEXT,`p_sponsor` INTEGER,`cus_id` INTEGER,`p_status` TEXT,PRIMARY KEY(`_id`))");
        }
        db.close();
    }

    public boolean isPhoneNumberValid(String phoneNumber, String countryCode) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        try {
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber, countryCode);
            return phoneUtil.isValidNumber(numberProto);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }

        return false;
    }

    private void getSkylightRefferer(InstallReferrerClient referrerClient) {
        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        ReferrerDetails response = null;
                        try {
                            response = referrerClient.getInstallReferrer();

                            String referrerUrl = response.getInstallReferrer();

                            long referrerClickTime = response.getReferrerClickTimestampSeconds();

                            long appInstallTime = response.getInstallBeginTimestampSeconds();

                            boolean instantExperienceLaunched = response.getGooglePlayInstantParam();

                            referrer = response.getInstallReferrer();

                            //refrerTV.setText("Referrer is : \n" + referrerUrl + "\n" + "Referrer Click Time is : " + referrerClickTime + "\nApp Install Time : " + appInstallTime);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        Toast.makeText(SignUpAct.this, "Feature not supported..", Toast.LENGTH_SHORT).show();
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        Toast.makeText(SignUpAct.this, "Fail to establish connection", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                Toast.makeText(SignUpAct.this, "Service disconnected..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendOTPMessage(String otpPhoneNumber, String otpMessage) {
        Bundle smsBundle = new Bundle();
        smsBundle.putString("PROFILE_PHONE", otpPhoneNumber);
        smsBundle.putString("USER_PHONE", otpPhoneNumber);
        smsBundle.putString("smsMessage", otpMessage);
        //smsBundle.putString("from", "Skylight");
        smsBundle.putString("to", otpPhoneNumber);
        Intent otpIntent = new Intent(SignUpAct.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }
    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void startProfileActivity(int sponsorID, LatLng cusLatLng, Account account, StandingOrderAcct standingOrderAcct, String joinedDate, String uFirstName, String uSurname, String uPhoneNumber, String uAddress, String uUserName, String uPassword, Customer customer, Profile customerProfile, String nIN, Profile managerProfile, String dateOfBirth, String selectedGender, String selectedOffice, String selectedState, Birthday birthday, CustomerManager customerManager, String ofBirth, int profileID1, int virtualAccountNumber, int soAccountNumber, int customerID, int birthdayID, int investmentAcctID, int itemPurchaseAcctID, int promoAcctID, int packageAcctID, ArrayList<Customer> customers) {
        if (!USINGROOM) {
            PreRoomCode();
        } else {
            PostRoomCode();
        }
        Toast.makeText(SignUpAct.this, "Gender: " + selectedGender + "," + "Office:" + selectedOffice + "" + "State:" + selectedState, Toast.LENGTH_SHORT).show();
        showProgressDialog();
        ran = new Random();
        SQLiteDataBaseBuild();
        calendar = Calendar.getInstance();
        sqLiteDatabase = dbHelper.getWritableDatabase();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        timeLineTime = mdformat.format(calendar.getTime());
        profiles = dbHelper.getAllProfiles();
        //customers = dbHelper.getAllCustomers();
        smsMessage = "Welcome to the Skylight  App, may you have the best experience";
        String names = uSurname + uFirstName;
        //long customerId = customer.getId();
        //Customer c = new Customer();
        String name = uSurname + "," + uFirstName;
        String managerFullNamesT = ManagerSurname + "," + managerFirstName;
        String namesT = uSurname + "," + uFirstName;

        String timelineDetailsTD = uSurname + "," + uFirstName + "was added as a Customer" + "by" + managerFullNamesT + "@" + timeLineTime;
        String tittleT1 = "Customer Sign Up Alert!";
        String timelineDetailsT11 = "You added" + uSurname + "," + uFirstName + "as a Customer" + "on" + timeLineTime;

        random = new SecureRandom();
        gson = new Gson();
        String accountName = uSurname + "," + uFirstName;

        Date date = new Date();


        //birthdayID = random.nextInt((int) (Math.random() * 1001) + 1010);
        String skylightMFb = "E-Wallet";
        double accountBalance = 0.00;
        accountTypeStr = AccountTypes.EWALLET;
        String interestRate = "0.0";
        dbHelper = new DBHelper(this);

        if (managerProfile != null) {
            ManagerSurname = managerProfile.getProfileLastName();
            managerFirstName = managerProfile.getProfileFirstName();
            profileID = managerProfile.getPID();
            managerPhoneNumber1 = managerProfile.getProfilePhoneNumber();
            managerEmail = managerProfile.getProfileEmail();
            managerUserName = managerProfile.getProfileUserName();
            officePref = managerProfile.getProfileOffice();
            customerManager = managerProfile.getCustomerManager();
            managerAddress = managerProfile.getProfileAddress();
            managerGender = managerProfile.getProfileGender();
            managerProfile.addTimeLine(tittleT1, timelineDetailsT11);
            managerProfile.addCustomer(customerID, uSurname, uFirstName, uPhoneNumber, uEmail, uAddress, selectedGender, selectedOffice, selectedState, mImageUri, joinedDate, uUserName, uPassword);

        }
        int finalProfileID = profileID1;
        lastProfileUsed = new Profile();
        account = new Account("EWallet Account", accountName, virtualAccountNumber, accountBalance, accountTypeStr);
        standingOrderAcct = new StandingOrderAcct(virtualAccountNumber + 12, accountName, 0.00);
        customer = new Customer(customerID, uSurname, uFirstName, uPhoneNumber, uEmail, nIN, dateOfBirth, selectedGender, uAddress, uUserName, uPassword, selectedOffice, joinedDate);
        birthday = new Birthday(birthdayID, profileID1, uSurname + "," + uFirstName, uPhoneNumber, uEmail, selectedGender, uAddress, dateOfBirth, 0, "", "Not celebrated");
        customerProfile = new Profile(profileID1, uSurname, uFirstName, uPhoneNumber, uEmail, dateOfBirth, selectedGender, uAddress, "", selectedState, selectedOffice, joinedDate, "Customer", uUserName, uPassword, "pending", "");

        lastProfileUsed = customerProfile;
        json = gson.toJson(lastProfileUsed);

        sendSMSMessage22(uPhoneNumber, smsMessage);

        Bundle userBundle = new Bundle();

        userBundle.putString(PROFILE_DOB, dateOfBirth);
        userBundle.putString(PROFILE_EMAIL, uEmail);
        userBundle.putString(PROFILE_OFFICE, selectedOffice);
        userBundle.putString(PROFILE_FIRSTNAME, uFirstName);
        userBundle.putString(PROFILE_GENDER, selectedGender);
        userBundle.putString(PROFILE_COUNTRY, "");
        userBundle.putString(PROFILE_NEXT_OF_KIN, "");
        userBundle.putString(PROFILE_PHONE, uPhoneNumber);
        userBundle.putString(PROFILE_SURNAME, uSurname);
        userBundle.putString(PICTURE_URI, String.valueOf(mImageUri));
        userBundle.putString(CUSTOMER_LATLONG, String.valueOf(cusLatLng));
        userBundle.putString(PROFILE_PASSWORD, uPassword);
        userBundle.putString(PROFILE_NIN, nIN);
        userBundle.putString(PROFILE_STATE, selectedState);
        userBundle.putString(PROFILE_ROLE, "Customer");
        userBundle.putString(PROFILE_STATUS, "Pending Approval");
        userBundle.putString(PROFILE_USERNAME, uUserName);
        userBundle.putInt(PROFILE_ID, profileID1);
        userBundle.putString(PROFILE_DATE_JOINED, joinedDate);
        userBundle.putString("machine", "Customer");

        userBundle.putString("USER_DOB", dateOfBirth);
        userBundle.putString("USER_EMAIL", uEmail);
        userBundle.putString("USER_OFFICE", selectedOffice);
        userBundle.putString("USER_FIRSTNAME", uFirstName);
        userBundle.putString("USER_GENDER", selectedGender);
        userBundle.putString("USER_COUNTRY", "");
        userBundle.putString("USER_NEXT_OF_KIN", "");
        userBundle.putString("USER_PHONE", uPhoneNumber);
        userBundle.putString("USER_SURNAME", "");
        userBundle.putString("PICTURE_URI", String.valueOf(mImageUri));
        userBundle.putString("USER_DATE_JOINED", "");
        userBundle.putString("PROFILE_NIN", nIN);
        userBundle.putString("USER_STATE", selectedState);
        userBundle.putString("USER_ROLE", "Customer");
        userBundle.putString("USER_STATUS", "Pending Approval");
        userBundle.putString("USERNAME", uUserName);
        userBundle.putLong("PROFILE_ID", profileID1);
        userBundle.putString("USER_PASSWORD", uPassword);
        userBundle.putString("USER_LOCATION", String.valueOf(cusLatLng));
        userBundle.putString("EMAIL_ADDRESS", uEmail);
        userBundle.putString("CHOSEN_OFFICE", selectedOffice);
        userBundle.putString("USER_NAME", uUserName);
        userBundle.putString("USER_DATE_JOINED", joinedDate);

        userBundle.putInt("CUSTOMER_ID", customerID);
        userBundle.putString("PROFILE_NIN", nIN);
        userBundle.putString("EMAIL_ADDRESS", uEmail);
        userBundle.putString("DATE_OF_BIRTH_KEY", dateOfBirth);
        userBundle.putString("GENDER_KEY", selectedGender);
        userBundle.putString("USER_NEXT_OF_KIN", "");
        userBundle.putString(CUSTOMER_LATLONG, "");
        userBundle.putString("machine", "Customer");
        userBundle.putInt(PROFILE_SPONSOR_ID, sponsorID);
        userBundle.putInt("USER_SPONSOR_ID", sponsorID);
        sendTextMessage(uPhoneNumber, smsMessage);
        Intent intent = new Intent(SignUpAct.this, NewCustomerDrawer.class);
        intent.putExtras(userBundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        Toast.makeText(SignUpAct.this, "Thank you" + "" +
                "for Signing up " + "" + uFirstName + "" + "on the Skylight. App", Toast.LENGTH_LONG).show();
        setResult(Activity.RESULT_OK, new Intent());



        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("PROFILE_DOB", dateOfBirth);
        editor.putString("PROFILE_EMAIL", uEmail);
        editor.putString("PROFILE_OFFICE", selectedOffice);
        editor.putString("PROFILE_FIRSTNAME", uFirstName);
        editor.putString("PROFILE_GENDER", selectedGender);
        editor.putString("PROFILE_COUNTRY", "");
        editor.putString("PROFILE_NEXT_OF_KIN", "");
        editor.putString("PROFILE_PHONE", uPhoneNumber);
        editor.putString("PROFILE_SURNAME", uSurname);
        editor.putString("PICTURE_URI", String.valueOf(mImageUri));
        editor.putString("CUSTOMER_LATLONG", String.valueOf(cusLatLng));
        editor.putString("PROFILE_PASSWORD", uPassword);
        editor.putString("PROFILE_NIN", nIN);
        editor.putString("PROFILE_STATE", selectedState);
        editor.putString("PROFILE_ROLE", "Customer");
        editor.putString("PROFILE_STATUS", "Pending Approval");
        editor.putInt("PROFILE_ID", profileID1);
        editor.putString("PROFILE_DATE_JOINED", joinedDate);

        editor.putString("PROFILE_USERNAME", uUserName);
        editor.putString("PROFILE_PASSWORD", uPassword);
        editor.putInt("CUSTOMER_ID", customerID);
        editor.putString("PROFILE_SURNAME", uSurname);
        editor.putString("PROFILE_FIRSTNAME", uFirstName);
        editor.putString("PROFILE_PHONE", uPhoneNumber);
        editor.putString("PROFILE_EMAIL", uEmail);
        editor.putString("PROFILE_DOB", dateOfBirth);
        editor.putString("PROFILE_ADDRESS", uAddress);
        editor.putString("PROFILE_GENDER", selectedGender);
        editor.putString("PROFILE_STATE", selectedState);
        editor.putString(PROFILE_DOB, dateOfBirth);
        editor.putString(PROFILE_EMAIL, uEmail);
        editor.putString(PROFILE_OFFICE, selectedOffice);
        editor.putString(PROFILE_FIRSTNAME, uFirstName);
        editor.putString(PROFILE_GENDER, selectedGender);
        editor.putString(PROFILE_COUNTRY, "");
        editor.putString(PROFILE_NEXT_OF_KIN, "");
        editor.putString(PROFILE_PHONE, uPhoneNumber);
        editor.putString(PROFILE_SURNAME, uSurname);
        editor.putString("PICTURE_URI", String.valueOf(mImageUri));
        editor.putString(PICTURE_URI, String.valueOf(mImageUri));
        editor.putString(CUSTOMER_LATLONG, String.valueOf(cusLatLng));
        editor.putString(PROFILE_PASSWORD, uPassword);
        editor.putString(PROFILE_NIN, nIN);
        editor.putString(PROFILE_STATE, selectedState);
        editor.putString(PROFILE_ROLE, "Customer");
        editor.putString(PROFILE_STATUS, "Pending Approval");
        editor.putInt(PROFILE_ID, profileID1);
        editor.putString(PROFILE_DATE_JOINED, joinedDate);

        editor.putString(PROFILE_USERNAME, uUserName);
        editor.putString(PROFILE_PASSWORD, uPassword);
        editor.putInt(CUSTOMER_ID, customerID);
        editor.putString(PROFILE_SURNAME, uSurname);
        editor.putString(PROFILE_FIRSTNAME, uFirstName);
        editor.putString(PROFILE_PHONE, uPhoneNumber);
        editor.putString(PROFILE_EMAIL, uEmail);
        editor.putString(PROFILE_DOB, dateOfBirth);
        editor.putString(PROFILE_ADDRESS, uAddress);
        editor.putString(PROFILE_GENDER, selectedGender);
        editor.putString(PROFILE_STATE, selectedState);
        editor.putString("machine", "Customer");
        editor.putString("Machine", "Customer");
        editor.putLong("EWalletID", virtualAccountNumber);
        editor.putLong("StandingOrderAcct", soAccountNumber);
        //editor.putLong("TransactionAcctID", transactionAcctID);
        editor.putInt("InvestmentAcctID", investmentAcctID);
        editor.putInt("PromoAcctID", promoAcctID);
        editor.putInt("ItemsPurchaseAcctID", itemPurchaseAcctID);
        editor.putInt("SavingsAcctID", savingsAcctID);
        //editor.putLong("MessageAcctID", finalProfileID1);

        editor.putString("USER_DOB", dateOfBirth);
        editor.putString("USER_EMAIL", uEmail);
        editor.putString("USER_OFFICE", selectedOffice);
        editor.putString("USER_FIRSTNAME", uFirstName);
        editor.putString("USER_GENDER", selectedGender);
        editor.putString("USER_COUNTRY", "");
        editor.putString("USER_NEXT_OF_KIN", "");
        editor.putString("USER_PHONE", uPhoneNumber);
        editor.putString("USER_SURNAME", uSurname);
        editor.putString("PICTURE_URI", String.valueOf(mImageUri));
        editor.putString("USER_LOCATION", "");
        editor.putString("USER_DATE_JOINED", "");
        editor.putString("USER_PASSWORD", uPassword);
        editor.putString("PROFILE_NIN", nIN);
        editor.putString("USER_STATE", selectedState);
        editor.putString("USER_ROLE", "Customer");
        editor.putString("USER_STATUS", "Pending Approval");
        editor.putString("USERNAME", uUserName);
        editor.putInt("PROFILE_ID", profileID1);
        editor.putString("USER_PASSWORD", uPassword);
        editor.putString("EMAIL_ADDRESS", uEmail);
        editor.putString("CHOSEN_OFFICE", selectedOffice);
        editor.putString("USER_NAME", uUserName);
        editor.putString("USER_DATE_JOINED", joinedDate);
        editor.putString("Machine", "Customer");
        editor.putString(PROFILE_ROLE, "Customer");
        editor.putString("LastProfileUsed", json).apply();

        customer.setCusFirstName(uFirstName);
        customer.setCusSurname(uSurname);
        customer.setCusAddress(uAddress);
        customer.setCusDob(dateOfBirth);
        customer.setCusUID(customerID);
        customer.setCusDateJoined(joinedDate);
        customer.setCusEmail(uEmail);
        customer.setCusRole("customer");
        customer.setCusPhoneNumber(uPhoneNumber);
        customer.setCusGender(selectedGender);
        customer.setCustomerLocation(this.cusLatLng);
        customer.setCusUserName(uUserName);
        customer.setCusState(selectedState);
        customer.setCustomerLocation(this.cusLatLng);
        customer.setCusSponsorID(sponsorID);
        customer.setCusOffice(selectedOffice);
        customer.setCusAccount(account);
        customer.setCusStandingOrderAcct(standingOrderAcct);

        customerProfile.setProfileFirstName(uFirstName);
        customerProfile.setProfileLastName(uSurname);
        customerProfile.setProfileAddress(uAddress);
        customerProfile.setProfileDob(dateOfBirth);
        customerProfile.setProfileDateJoined(joinedDate);
        customerProfile.setProfileEmail(uEmail);
        customerProfile.setProfileRole("customer");
        customerProfile.setProfilePhoneNumber(uPhoneNumber);
        customerProfile.setProfileGender(selectedGender);
        customerProfile.setProfileIdentity(null);
        customerProfile.setProfileLastKnownLocation(this.cusLatLng);
        customerProfile.setProfileMachine("customer");
        customerProfile.setProfileUserName(uUserName);
        customerProfile.setProfileState(selectedState);
        customerProfile.setProfileSponsorID(profileID);
        customerProfile.setProfileOffice(selectedOffice);
        customerProfile.setCustomerManager(customerManager);


        try {

            customer.setCusAccount(account);
            customer.setCusStandingOrderAcct(standingOrderAcct);
            customer.addCusAccountManager(managerProfileID, ManagerSurname, managerFirstName, managerGender, officePref);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            //dbHelper = new DBHelper(this);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            dbHelper.insertAccount(profileID1, customerID, skylightMFb, accountName, virtualAccountNumber, accountBalance, accountTypeStr);
            dbHelper.insertRole(profileID1, "Customer", uUserName, uPassword, uPhoneNumber);
            dbHelper.insertCustomer11(profileID1, customerID, uSurname, uFirstName, uPhoneNumber, uEmail, dateOfBirth, selectedGender, uAddress, selectedState, "", joinedDate, uUserName, uPassword, mImageUri, "Customer");


            dbHelper.insertUser112(profileID1, customerID, uSurname, uFirstName, uPhoneNumber, uEmail, dateOfBirth, selectedGender, uAddress, selectedState, "", joinedDate, uUserName, uPassword, "", "New", "Customer");

            dbHelper.insertBirthDay3(birthday, "1983-04-25");
            dbHelper.saveNewProfile(customerProfile);
            dbHelper.insertTimeLine(tittleT1, timelineDetailsTD, timeLineTime, mCurrentLocation);
            dbHelper.insertStandingOrderAcct(profileID1, customerID, virtualAccountNumber, accountName, 0.00);


            if (cusLatLng != null) {
                try {
                    //dbHelper.openDataBase();
                    dbHelper.insertCustomerLocation(customerID, this.cusLatLng);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            EmptyEditTextAfterDataInsert();







        }



    }
    private void PreRoomCode() {
        dbHelper = new DBHelper(this);
        userProfile1 = new Profile(0, "Emmanuel", "Becky", "08069524599", "urskylight@gmail.com", "1980-04-19", "female", "Skylight", "", "Rivers", "Elelenwo", "2022-04-19", "SuperAdmin", "Skylight4ever", "@Awajima2", "Confirmed", "");
        userProfile2= new Profile(0,"Benedict", "Benedict", "08059250176", "bener@gmail.com", "25/04/1989", "male", "PH", "","Rivers", "Elelenwo", "19/04/2022","Customer", "Lumgwun", "@Awajima3","Confirmed","");

        if (DatabaseUtils.queryNumEntries(dbHelper.getWritableDatabase(),DATABASE_NAME) < 1) {
            dbHelper.insert(userProfile1);
            dbHelper.insert(userProfile2);
        }
    }


    private void PostRoomCode() {
        createRoomTableIfNeeded();
        mRoomDB = Room.databaseBuilder(this,RoomController.class,DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
        roomTableDao = mRoomDB.roomProfileTableDao();
        supportSQLiteDatabase = mRoomDB.getOpenHelper().getWritableDatabase();
        userProfile1 = new Profile(0, "Emmanuel", "Becky", "08069524599", "urskylight@gmail.com", "1980-04-19", "female", "Skylight", "", "Rivers", "Elelenwo", "2022-04-19", "SuperAdmin", "Skylight4ever", "@Awajima2", "Confirmed", "");
        userProfile2= new Profile(0,"Benedict", "Benedict", "08059250176", "bener@gmail.com", "25/04/1989", "male", "PH", "","Rivers", "Elelenwo", "19/04/2022","Customer", "Lumgwun", "@Awajima3","Confirmed","");

        if (roomTableDao.getProfileCount() < 1) {
            //roomTableDao.insertProfile(userProfile1);
            roomTableDao.insertProfile(userProfile2);
            DBHelper.insertPostRoom(supportSQLiteDatabase,userProfile2);

        }


    }

    public void EmptyEditTextAfterDataInsert() {

        phone_number.getText().clear();

        email_address.getText().clear();

        address_2.getText().clear();
        firstName.getText().clear();

        userName.getText().clear();

        surname1.getText().clear();
        password.getText().clear();

    }

    public void SQLiteDataBaseBuild() {

        dbHelper = new DBHelper(this);

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (dbHelper != null) {
            dbHelper.onUpgrade(sqLiteDatabase, DATABASE_VERSION, DATABASE_NEW_VERSION);

        } else {
            sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);


        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.insertNewPackage(1000000,1234,92345678,4567,"Test Package","Savings",31,5000,"2022-17-06",5000000,"","Confirmed");
            dbHelper.insertTellerCash(109,193,193,"TestItem",500.00,"2022-05-23","Uche","Elelenwo",8903,"new");
            dbHelper.addProfile(345,"Ezekeil","","","","","","","","","","","password","",0);




        }


    }

    public void sendTextMessage(String uPhoneNumber, String smsMessage) {
        Bundle smsBundle = new Bundle();
        smsBundle.putString(PROFILE_PHONE, uPhoneNumber);
        smsBundle.putString("USER_PHONE", uPhoneNumber);
        smsBundle.putString("smsMessage", smsMessage);
        //smsBundle.putString("from", "Skylight Coop.");
        smsBundle.putString("to", uPhoneNumber);
        Intent otpIntent = new Intent(SignUpAct.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, otpIntent);
        //startActivity(itemPurchaseIntent);

    }

    protected void sendSMSMessage22(String uPhoneNumber, String smsMessage) {
        smsMessage = "Welcome to Skylight, your best Cooperative from Africa";
        phone_number = findViewById(R.id.phone_number);
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(uPhoneNumber),
                new com.twilio.type.PhoneNumber("+15703251701"),
                smsMessage)
                .create();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public interface OnPinEnteredListener {
        void onPinEntered(String pin);
    }

    private void doOtpNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Your OTP Message")
                        .setContentText(otpMessage);

        //Intent notificationIntent = new Intent(this, NewCustomerDrawer.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //stackBuilder.addParentStack(LoginDirectorActivity.class);
        //stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }



    /*private void sendOTPVerCode(String otpPhoneNumber, FirebaseAuth mAuth, String sponsorID, Account account, StandingOrderAcct standingOrderAcct, Customer customer, String joinedDate, String uFirstName, String uSurname, String uPhoneNumber, String uAddress, String uUserName, String uPassword, Customer customer1, Profile customerProfile, String nIN, Profile managerProfile, String dateOfBirth, String selectedGender, String selectedOffice, String selectedState, Birthday birthday, CustomerManager customerManager, String ofBirth, long profileID1, int virtualAccountNumber, int soAccountNumber, long customerID, Long profileID2, long birthdayID, Long investmentAcctID, Long itemPurchaseAcctID, Long promoAcctID, Long packageAcctID, ArrayList<Profile> profiles, ArrayList<Customer> customers, ArrayList<CustomerManager> tellers, ArrayList<AdminUser> adminUserArrayList, ArrayList<UserSuperAdmin> superAdminArrayList) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(otpPhoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(SignUpAct.this)
                .setCallbacks(codeCallBack)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        //firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(otpPhoneNumber, String.valueOf(itemPurchaseAcctID));


    }*/

    private void setupLocationManager() {

        createLocationRequest();
    }

    protected void createLocationRequest() {
        if (!hasPermissions(SignUpAct.this, PERMISSIONS33)) {
            ActivityCompat.requestPermissions(SignUpAct.this, PERMISSIONS33, PERMISSION_ALL33);

        }


        request = new LocationRequest();
        request.setSmallestDisplacement(10);
        request.setFastestInterval(50000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(3);

        /*LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());


        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
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
                                    SignUpAct.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });*/



    }
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")

                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }
    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void setInitialLocation() {
        txtLoc = findViewById(R.id.hereYou333);
        locTxt = findViewById(R.id.whereYou222);
        final double[] newLat = {0.00};
        final double[] newLng = {0.00};

        if (ActivityCompat.checkSelfPermission(SignUpAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SignUpAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if (locationPermissionGranted) {
            Task<Location> locationResult = fusedLocationClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        mCurrentLocation = task.getResult();
                        newLat[0] = mCurrentLocation.getLatitude();
                        newLng[0] = mCurrentLocation.getLongitude();

                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());

                    }
                }
            });
        }
        if(mCurrentLocation !=null){
            newLat[0] = mCurrentLocation.getLatitude();
            newLng[0] = mCurrentLocation.getLongitude();

        }


        try {
            Geocoder newGeocoder = new Geocoder(SignUpAct.this, Locale.ENGLISH);
            List<Address> newAddresses = newGeocoder.getFromLocation(newLat[0], newLng[0], 1);
            StringBuilder street = new StringBuilder();
            if (Geocoder.isPresent()) {

                //txtLoc.setVisibility(View.VISIBLE);

                android.location.Address newAddress = newAddresses.get(0);

                String localityString = newAddress.getLocality();

                street.append(localityString).append("");

                //txtLoc.setText(MessageFormat.format("Last Loc:  {0},{1}/{2}", newLat[0], newLng[0], street));
                Toast.makeText(SignUpAct.this, street,
                        Toast.LENGTH_SHORT).show();

            } else {
                txtLoc.setVisibility(View.GONE);
                //go
            }


        } catch (IndexOutOfBoundsException | IOException e) {

            Log.e("tag", e.getMessage());
        }


        /*LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                //double lat=location.getLatitude();
                //double lng=location.getLongitude();

                lat = location.getLatitude()/1E6;
                lng = location.getLongitude()/1E6;
                //locationPoint = new GeoPoint(latitude.intValue(),longitude.intValue());

                SignUpAct.this.latitude=lat;
                SignUpAct.this.longitude=lng;

                try {
                    if(now !=null){
                        now.remove();
                    }

                    userLocation = new LatLng( SignUpAct.this.latitude,SignUpAct.this.longitude );
                    cusLatLng=userLocation;


                } catch (Exception ex) {

                    ex.printStackTrace();
                    Log.e( "MapException", ex.getMessage() );

                }

                try {
                    geocoder = new Geocoder(SignUpAct.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {
                        txtLoc.setVisibility(View.VISIBLE);

                        android.location.Address returnAddress = addresses.get(0);

                        String localityString = returnAddress.getAddressLine (0);

                        str.append( localityString ).append( "" );

                        txtLoc.setText(MessageFormat.format("Your are here:  {0},{1}/{2}", latitude, longitude , str));
                        Toast.makeText(SignUpAct.this, str,
                                Toast.LENGTH_SHORT).show();

                    } else {
                          //go
                    }


                } catch (IndexOutOfBoundsException | IOException e) {

                    Log.e("tag", e.getMessage());
                }



            }

        } );*/
    }

    private void getUserLocation() {
        txtLoc = findViewById(R.id.hereYou333);

        /*if (ActivityCompat.checkSelfPermission(SignUpAct.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SignUpAct.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        try {

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {


                    //double lat=location.getLatitude();
                    //double lng=location.getLongitude();

                    lat = location.getLatitude() / 1E6;
                    lng = location.getLongitude() / 1E6;
                    //locationPoint = new GeoPoint(latitude.intValue(),longitude.intValue());

                    SignUpAct.this.latitude = lat;
                    SignUpAct.this.longitude = lng;

                    try {
                        if (now != null) {
                            now.remove();
                        }

                        userLocation = new LatLng(SignUpAct.this.latitude, SignUpAct.this.longitude);
                        cusLatLng = userLocation;


                    } catch (Exception ex) {

                        ex.printStackTrace();
                        Log.e("MapException", ex.getMessage());

                    }

                    try {
                        geocoder = new Geocoder(SignUpAct.this, Locale.ENGLISH);
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        StringBuilder str = new StringBuilder();
                        if (Geocoder.isPresent()) {
                            //txtLoc.setVisibility(View.VISIBLE);

                            android.location.Address returnAddress = addresses.get(0);

                            String localityString = returnAddress.getAddressLine(0);

                            str.append(localityString).append("");
                            //txtLoc.setText(MessageFormat.format("Your are here:  {0},{1}/{2}", latitude, longitude, str));


                            Toast.makeText(SignUpAct.this, str,
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            //go
                        }


                    } catch (IllegalStateException | IOException e) {

                        Log.e("tag", e.getMessage());
                    }


                }

            });



        } catch (Exception ex) {

            ex.printStackTrace();
            Log.e( "MapException", ex.getMessage() );

        }*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        geocoder = new Geocoder(this, Locale.getDefault());
        getDeviceLocation();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

    }
    @Override
    protected void onStop() {
        super.onStop();



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
                txtLoc = findViewById(R.id.hereYou333);
                try {
                    geocoder = new Geocoder(SignUpAct.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {
                        //txtLoc.setVisibility(View.VISIBLE);

                        android.location.Address returnAddress = addresses.get(1);

                        String localityString = returnAddress.getAdminArea();

                        str.append(localityString).append("");
                        //txtLoc.setText(MessageFormat.format("Your are here:  {0},{1}/{2}", latitude, longitude, str));


                        Toast.makeText(SignUpAct.this, str,
                                Toast.LENGTH_SHORT).show();

                    } else {
                        //go
                    }


                } catch (IllegalStateException | IOException e) {

                    Log.e("tag", e.getMessage());
                }


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
        txtLoc = findViewById(R.id.hereYou333);
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

                    Toast.makeText(SignUpAct.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setAction(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("Skylight App",
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
                    SignUpAct.this.cusLatLng = userLocation;

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
                    Uri uri = Uri.fromParts("Skylight App",
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


    public void verifyOTP(View view) {
        startProfileActivity(sponsorID, cusLatLng, account, standingOrderAcct, joinedDate, uFirstName, uSurname, uPhoneNumber, uAddress, uUserName, uPassword, customer, customerProfile, nIN, managerProfile, dateOfBirth, selectedGender, selectedOffice, selectedState, birthday, customerManager, dateOfBirth, profileID1, virtualAccountNumber, soAccountNumber, customerID, birthdayID, investmentAcctID, itemPurchaseAcctID, promoAcctID, packageAcctID, customers);

    }


    public void sendOTPToCus(View view) {
    }

    public void doSelectPix(View view) {

        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, PICTURE_REQUEST_CODE);
        Toast.makeText(SignUpAct.this, "Picture selection in Progress",
                Toast.LENGTH_SHORT).show();

        if (intent.resolveActivity(getPackageManager()) != null) {
            mGetPixContent.launch(intent);
        } else {
            Toast.makeText(SignUpAct.this, "There is no app that support this action",
                    Toast.LENGTH_SHORT).show();
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

    private void onCaptureImageResult(Intent data) throws IOException {
        FileOutputStream fo = null;
        destination = null;
        if (data != null) {
            thumbnail = (Bitmap) data.getExtras().get("data");
        }
        if (thumbnail != null) {
            bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
            destination = new File(Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis() + ".jpg");


        }

        try {
            if (destination != null) {
                destination.createNewFile();
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (destination != null) {
            picturePath = destination.getAbsolutePath();
        }

        Glide.with(SignUpAct.this)
                .asBitmap()
                .load(destination)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_alert)
                //.listener(listener)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(profilePix);
    }

    protected void sendSMSMessage() {
        Bundle smsBundle = new Bundle();
        String smsMessage = uFirstName + "" + "Welcome to the Skylight  App, may you have the best experience";
        smsBundle.putString(PROFILE_PHONE, otpPhoneNumber);
        smsBundle.putString("USER_PHONE", otpPhoneNumber);
        smsBundle.putString("smsMessage", smsMessage);
        smsBundle.putString("from", "Skylight");
        smsBundle.putString("to", otpPhoneNumber);
        Intent itemPurchaseIntent = new Intent(SignUpAct.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //System.out.println(message2.getSid());;

    }


    public Bitmap getBitmap(String path) {
        Bitmap myBitmap = null;
        File imgFile = new File(path);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }

    private void setResultOk(Uri mImageUri) {
        Intent intent = new Intent();
        profilePix.setImageBitmap(getScaledBitmap(mImageUri));
        setResult(AppCompatActivity.RESULT_OK, intent);
        finish();
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(AppCompatActivity.RESULT_CANCELED, intent);
        finish();
    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        startActivity(intent);

    }

    public String getPath(Uri photoUri) {

        String filePath = "";
        if (photoUri != null) {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(photoUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            filePath = cursor.getString(columnIndex);
            cursor.close();
        }
        return filePath;
    }

    private File getDir() {
        File sdDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(sdDir, "LS Attachments");
    }

    private Bitmap getScaledBitmap(Uri uri) {
        Bitmap thumb = null;
        try {
            ContentResolver cr = getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            thumb = BitmapFactory.decodeStream(in, null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(SignUpAct.this, "File not found", Toast.LENGTH_SHORT).show();
        }
        return thumb;
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

    public void dobPicker(View view) {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        newMonth = month + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(SignUpAct.this, R.style.DatePickerDialogStyle, mDateSetListener, day, month, year);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
        dialog.show();
        dob = year + "-" + newMonth + "-" + day;
        dateOfBirth = day + "-" + newMonth + "-" + year;
        dobText.setText("Your date of Birth:" + dob);


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
}