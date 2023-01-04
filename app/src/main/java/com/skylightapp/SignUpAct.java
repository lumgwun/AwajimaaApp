package com.skylightapp;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;

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
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.blongho.country_data.Country;
import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountInvestment;
import com.skylightapp.Classes.AccountItemPurchase;
import com.skylightapp.Classes.AccountPromo;
import com.skylightapp.Classes.AccountSavings;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Message;
import com.skylightapp.Classes.PinEntryView;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Customers.MyAcctOverViewAct;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.MarketClasses.AppInstallRefReceiver;
import com.skylightapp.MarketClasses.RandomAcctNo;
import com.skylightapp.MarketClasses.ResourceUtils;
import com.skylightapp.SuperAdmin.Awajima;
import com.teliver.sdk.models.PushData;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.skylightapp.BuildConfig.QUICKBLOX_ACCT_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_APP_ID;
import static com.skylightapp.BuildConfig.QUICKBLOX_AUTH_KEY;
import static com.skylightapp.BuildConfig.QUICKBLOX_SECRET_KEY;
import static com.skylightapp.Classes.Account.BANK_ACCT_NO;
import static com.skylightapp.Classes.AppConstants.AWAJIMA_PRIVACY_POLICIES;
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

public class SignUpAct extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final int PICTURE_REQUEST_CODE = 505;
    SharedPreferences userPreferences;
    AppCompatEditText edtPhone_number;
    AppCompatEditText email_address;
    AppCompatEditText edtNIN;
    String stringNIN, timeLineTime;
    AppCompatEditText edtFirstName;
    AppCompatEditText edtSurname;
    AppCompatEditText userName;
    AppCompatEditText edtPassword, edtSponsorID;
    AppCompatEditText edtAddress_2;
    Bitmap thumbnail;
    protected DatePickerDialog datePickerDialog;
    Random ran;
    SecureRandom random;
    String dateOfBirth,json4;

    AppCompatTextView dobText;
    String uPhoneNumber;
    int profileID, birthdayID, messageID;
    private ProgressDialog progressDialog;
    String ManagerSurname;
    String managerFirstName, uPassword;
    String managerPhoneNumber1;
    String managerEmail, managerGender;
    String managerAddress;
    int numMessages;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    private boolean locationPermissionGranted;
    String managerUserName;
    String  selectedGender, selectedOffice, selectedState;
    Birthday birthday;
    String uFirstName, uEmail, uSurname, uAddress, uUserName;
    int virtualAccountNumber, soAccountNumber;
    int customerID;
    int profileID1;
    AppCompatSpinner state, office, spnGender;
    DBHelper dbHelper;
    Profile managerProfile;

    Location mCurrentLocation = null;
    SQLiteDatabase sqLiteDatabase;
    Date date;
    AccountTypes accountTypeStr;

    Gson gson, gson1,gson3,gson6,gson7,gson5,gson8;
    String json, json1, json3,json6,json7,json8,json9,nIN,name;
    Profile userProfile, customerProfile, lastProfileUsed;
    String  officePref,usdSymbol,gbpSymbol;

    private String picturePath;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String REQUIRED = "Required";
    private static final String SIGNUP_STATE_KEY = "signup_Key";
    private static final int RESULT_CAMERA_CODE = 22;
    private java.util.Currency currency22;
    int investmentAcctID, savingsAcctID, itemPurchaseAcctID, promoAcctID, packageAcctID;
    ByteArrayOutputStream bytes;
    Customer customer;
    int usdSymbolNo,gbpSymbolNo;
    int  day, month, year, newMonth;
    String userType;
    String userRole, sponsorIDString;
    int sponsorID;
    String address,signUpK;
    LatLng userLocation;
    File destination;
    LatLng cusLatLng;
    double latitude = 0;
    double longitude = 0;
    Geocoder geocoder;
    Uri sound;
    ArrayList<Profile> profiles;
    ArrayList<Customer> customers;
    ArrayList<Account> accountArrayList;
    CircleImageView profilePix;
    AppCompatImageView imgGreetings;
    private CustomerManager customerManager, customerManager2;
    ArrayList<CustomerManager> tellers;
    ArrayList<AdminUser> adminUserArrayList;
    ArrayList<UserSuperAdmin> superAdminArrayList;
    private Uri mImageUri;
    ContentLoadingProgressBar progressBar;
    List<Address> addresses;
    List<Currency> currencies;
    private PrefManager prefManager;
    private Calendar cal;
    private static boolean isPersistenceEnabled = false;
    private Account account;
    private StandingOrderAcct standingOrderAcct;
    private FusedLocationProviderClient fusedLocationClient;
    AccountInvestment accountInvestment;
    AccountItemPurchase accountItemPurchase;
    AccountSavings accountSavings;
    AccountPromo accountPromo;
    String joinedDate, customerName;
    String code;
    Profile userProfile1, userProfile2, userProfile3;

    private AppInstallRefReceiver appInstallRefReceiver;
    String dob;
    String otpMessage;
    String smsMessage;
    private AppCompatButton btnVerifyOTPAndSignUp,btnSendOTP;
    private int otpDigit;
    String otpPhoneNumber;
    PinEntryView pinEntry;
    private LinearLayoutCompat layoutOTP, layoutPreOTP;
    private EditText[] editTexts;

    Location location;
    AppCompatTextView txtLoc, locTxt,otpTxt;
    private LocationRequest request;
    private CancellationTokenSource cancellationTokenSource;
    private Calendar calendar;
    private PinEntryView pinEntryView;
    InstallReferrerClient referrerClient;
    CountryCodePicker ccp;
    String referrer = "";
    private LocationCallback locationCallback;
    private String bankAcctNumber;

    private static final String PREF_NAME = "awajima";
    private LocationManager locationManager;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private AcctDAO acctDAO;

    private CusDAO cusDAO;
    private ProfDAO profileDao;
    private QBUser qbUser,lastQBUserUsed;
    private Profile profile, tellerProfile;
    private Account indianAccount;
    private Account lastAccountUsed,usdAccount,gbpAccount,cadAccount,ghaAccount,kenAccount,philAccount,saAccount,pakistanAccount;
    private Awajima awajima;
    private Currency currency;
    String usd,gbp , cad, cedi,pak,keny,sa,phil,ccString,plink;
    private long cadNo, audNo,pakNo,saNo,ghaNo,kenNo,philNo,indianNo;
    private BirthdayDAO birthdayDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private Customer lastCustomerUsed;
    private static final String APPLICATION_ID = QUICKBLOX_APP_ID;   //QUICKBLOX_APP_ID
    public static final String USER_DEFAULT_PASSWORD = "quickblox";
    private static final int EXISTING_PROFILE_LOADER = 0;
    private BottomSheetBehavior mBottomSheetBehavior;
    String regEx =
            "^(([w-]+.)+[w-]+|([a-zA-Z]{1}|[w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9]).([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[w-]+.)+[a-zA-Z]{2,4})$";


    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Bundle bizNameBundle;
    private int bizID,marketID,managerProfileID;
    private LinearLayout layoutPrivacyAndTerms;
    private WebView webView;
    private AppCompatImageView btnClose;

    //private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocompleteView;
    protected GoogleApiClient mGoogleApiClient;
    private Intent data;
    private Country countryUS,countryUK;
    //private AppController appController;
    private Currency currency2;
    int genderIndex =0;
    int stateIndex =0;
    int officeIndex=0;
    private CoordinatorLayout coordinatorLayout;

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
                            data= new Intent();
                            if (result.getData() != null) {
                                if(result !=null){
                                    data = result.getData();

                                }
                                if(data !=null){
                                    mImageUri = data.getData();

                                }

                                if (mImageUri != null) {
                                    profilePix.setImageBitmap(getScaledBitmap(mImageUri));
                                } else {
                                    Toast.makeText(SignUpAct.this, "Error getting Photo",
                                            Toast.LENGTH_SHORT).show();
                                }


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
    ActivityResultLauncher<Intent> startBankAcctCreationForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            bankAcctNumber = intent.getStringExtra("BankAcct");
                        }

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

                    Address newAddress = newAddresses.get(0);

                    String localityString = newAddress.getLocality();

                    street.append(localityString).append("");

                    locTxt.setText(MessageFormat.format("Where you are:  {0},{1}/{2}", latitude, longitude, street));
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
    private String deviceMobileNo;
    private Uri currentProfileUri;
    private StandingOrderAcct lastStandingOrderAcctUsed;



    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            signUpK = savedInstanceState.getString(SIGNUP_STATE_KEY);
        }
        currencies= new ArrayList<>();
        accountArrayList= new ArrayList<>();
        setContentView(R.layout.act_sign_up);
        checkUser();
        dbHelper = new DBHelper(this);
        awajima= new Awajima();

        //appController= new AppController();
        gson3 = new Gson();
        gson6 = new Gson();
        gson5 = new Gson();
        gson7 = new Gson();

        prefManager= new PrefManager();
        lastQBUserUsed= qbUser;
        bizNameBundle= new Bundle();
        qbUser= new QBUser();
        profile= new Profile();
        lastAccountUsed = new Account();
        tellerProfile = new Profile();
        lastCustomerUsed=new Customer();
        usdAccount= new Account();
        gbpAccount= new Account();
        indianAccount= new Account();
        cadAccount= new Account();
        ghaAccount= new Account();
        kenAccount= new Account();
        philAccount= new Account();
        saAccount= new Account();
        pakistanAccount= new Account();
        lastStandingOrderAcctUsed= new StandingOrderAcct();
        //appController.onCreate();
        sqLiteDatabase = dbHelper.getWritableDatabase();

        appInstallRefReceiver= new AppInstallRefReceiver();
        //QBSettings.getInstance().init(this, APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        //QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        setTitle("OnBoarding Arena");
        bizNameBundle=getIntent().getExtras();
        createLocationRequest();
        cusDAO= new CusDAO(this);

        profileDao= new ProfDAO(this);
        birthdayDAO= new BirthdayDAO(this);

        timeLineClassDAO= new TimeLineClassDAO(this);
        if(bizNameBundle !=null){
            bizID=bizNameBundle.getInt("MARKET_BIZ_ID");
            marketID=bizNameBundle.getInt("MARKET_ID");
        }

        tranXDAO= new TranXDAO(this);
        messageDAO= new MessageDAO(this);

        acctDAO= new AcctDAO(this);
        doLocationStuff();

        setInitialLocation();
        txtLoc = findViewById(R.id.hereYou333);
        locTxt = findViewById(R.id.whereYou222);
        coordinatorLayout = findViewById(R.id.bottomSheet_Coord);
        mBottomSheetBehavior = BottomSheetBehavior.from(coordinatorLayout);

        btnClose = findViewById(R.id.btn_closeT);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


            }
        });

        layoutPrivacyAndTerms = findViewById(R.id.pp_and_terms_layout);
        layoutPrivacyAndTerms.setOnClickListener(this::showPrivacyPolicy);

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
        //SQLiteDataBaseBuild();

        getDeviceLocation();


        /*new Thread(new Runnable() {
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
        }).start();*/
        layoutPreOTP = findViewById(R.id.layoutSign);

        userProfile = new Profile();
        account = new Account();
        awajima= new Awajima();
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
        customerProfile = new Profile();
        birthday = new Birthday();
        try {

            if (dbHelper != null) {
                PrePopulateDB();
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //dbHelper.onUpgrade(sqLiteDatabase, dbHelper.getDatabaseVersion(), DBHelper.DATABASE_NEW_VERSION);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        managerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerManagerUsed", "");
        customerManager = gson1.fromJson(json1, CustomerManager.class);
        try {

            loadProfiles(dbHelper);

        } catch (Exception e) {
            e.printStackTrace();
        }

        otpDigit = ThreadLocalRandom.current().nextInt(122, 1631);
        messageID = ThreadLocalRandom.current().nextInt(1125, 10400);
        usdSymbolNo = ThreadLocalRandom.current().nextInt(18225, 1040012);
        gbpSymbolNo = ThreadLocalRandom.current().nextInt(10235, 10721400);
        audNo = ThreadLocalRandom.current().nextInt(10235, 10301400);
        cadNo = ThreadLocalRandom.current().nextInt(10235, 10021000);
        pakNo = ThreadLocalRandom.current().nextInt(1120, 10400);
        saNo = ThreadLocalRandom.current().nextInt(1121, 10400);
        ghaNo = ThreadLocalRandom.current().nextInt(1022, 10200);
        kenNo = ThreadLocalRandom.current().nextInt(1103, 10500);
        philNo = ThreadLocalRandom.current().nextInt(1314, 10700);
        indianNo = ThreadLocalRandom.current().nextInt(1014, 10301);


        otpMessage = "&message=" + "Hello Awajima New User, Your OTP Code is " + otpDigit;

        profileID1 = random.nextInt((int) (Math.random() * 1400) + 1115);
        try {
            virtualAccountNumber = Integer.parseInt(RandomAcctNo.getAcctNumeric(10));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //virtualAccountNumber = random.nextInt((int) (Math.random() * 123045) + 100123);
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
                month = cal.get(Calendar.MONTH+1);
                newMonth = month;
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SignUpAct.this, R.style.DatePickerDialogStyle, mDateSetListener, day, month, year);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
                dialog.show();
                dob = year + "-" + newMonth + "-" + day;
                dateOfBirth = dob;
                dobText.setText("Your date of Birth:" + dob);

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                Log.d(TAG, "onDateSet: date of Birth: " + day + "-" + month + "-" + year);
                dob = year + "-" + newMonth + "-" + day;
                dateOfBirth = dob;
                dobText.setText("Your date of Birth:" + dob);


            }


        };
        dateOfBirth = dob;

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
            profileID = 0;

        }
        edtSponsorID.setText("Your Sponsor's ID:" + profileID);

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
                if(genderIndex==position){
                    return;
                }else{
                    selectedGender = spnGender.getSelectedItem().toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        office = findViewById(R.id.office5);

        office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(officeIndex==position){
                    return;
                }else{
                    selectedOffice = office.getSelectedItem().toString();

                }


                //selectedOffice = (String) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        state = findViewById(R.id.state1);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(stateIndex==position){
                    return;
                }else{
                    selectedState = state.getSelectedItem().toString();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        edtPhone_number = findViewById(R.id.phone_number);
        email_address = findViewById(R.id.email_address);
        edtAddress_2 = findViewById(R.id.address_all);
        edtFirstName = findViewById(R.id.first_Name_00);
        userName = findViewById(R.id.user_name_1);
        edtSurname = findViewById(R.id.surname1);
        edtPassword = findViewById(R.id.user_password_sig);
        edtNIN = findViewById(R.id.profile_NIN);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);


        String email = email_address.getText().toString().trim();

        ccp.registerCarrierNumberEditText(edtPhone_number);


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
                setTitle("Photo selection in Progress...");

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
                            setTitle("Awajima onBoarding");
                        }

                        return true;
                    }
                });
                popup.show();//showing popup menu


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
            profileID = 0;

        }
        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            if(cusDAO !=null){
                try {
                    customers = cusDAO.getAllCustomers11();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }


        }

        //dbHelper.open();
        btnVerifyOTPAndSignUp.setOnClickListener(this::DoAwajimaSignUp);
        pinEntryView = (PinEntryView) findViewById(R.id.txt_pin_entry);

        Animation translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);

        btnSendOTP.setOnClickListener(this::sendOTPToCus);

        Animation translater44 = AnimationUtils.loadAnimation(this, R.anim.bounce);
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(translater44);
                uFirstName = edtFirstName.getText().toString().trim();
                uSurname = edtSurname.getText().toString().trim();
                uEmail = email_address.getText().toString();
                sponsorIDString = edtSponsorID.getText().toString().trim();
                uAddress = Objects.requireNonNull(edtAddress_2.getText()).toString();
                uPassword = edtPassword.getText().toString().trim();
                ccString=ccp.getFullNumberWithPlus();
                //uPhoneNumber = Objects.requireNonNull(phone_number.getText()).toString();
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
                        edtFirstName.setError("Please enter Your First Name");
                    } else if (TextUtils.isEmpty(uSurname)) {
                        edtSurname.setError("Please enter your Last/SurName");
                    } else if (TextUtils.isEmpty(uPassword)) {
                        edtPassword.setError("Please enter your Password");
                    } else if (uPhoneNumber.isEmpty() || uPhoneNumber.length() < 11) {
                        //edtPhone_number.setError("Enter a valid mobile Number");
                        //edtPhone_number.requestFocus();
                    } else if (TextUtils.isEmpty(uUserName)) {
                        userName.setError("Please enter your userName");
                    } else if (TextUtils.isEmpty(uAddress)) {
                        edtAddress_2.setError("Please enter Address");
                    } else {
                        //sendOTPVerCode(otpPhoneNumber,mAuth,sponsorID,account,standingOrderAcct,customer,joinedDate,uFirstName,uSurname,uPhoneNumber,uAddress,uUserName,uPassword,customer,customerProfile,nIN,managerProfile,dateOfBirth,selectedGender,selectedOffice,selectedState,birthday,customerManager,dateOfBirth,profileID1,virtualAccountNumber,soAccountNumber, customerID,profileID2,birthdayID, investmentAcctID,itemPurchaseAcctID,promoAcctID,packageAcctID,profiles,customers,tellers,adminUserArrayList,superAdminArrayList);

                        for (int i = 0; i < customers.size(); i++) {
                            try {
                                if (customers.get(i).getCusPhoneNumber().equalsIgnoreCase(uPhoneNumber)) {
                                    Toast.makeText(SignUpAct.this, "This Phone Number is already in use, here", Toast.LENGTH_LONG).show();
                                    return;

                                } else {
                                    doOtpNotification(otpMessage);

                                    layoutOTP.setVisibility(View.VISIBLE);
                                    layoutPreOTP.setVisibility(View.GONE);
                                    otpTxt.setText("Check your Message for your OTP:");
                                    otpPhoneNumber = uPhoneNumber;
                                    doOtpNotification(otpMessage);
                                    sendOTPMessage(otpPhoneNumber, otpMessage);
                                    doTeliverNoti(otpMessage);

                                    if(dbHelper !=null){
                                        sqLiteDatabase = dbHelper.getReadableDatabase();
                                        if(messageDAO !=null){
                                            try {

                                                if(sqLiteDatabase !=null){
                                                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                                                }
                                            } catch (SQLiteException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                messageDAO.insertMessage(profileID, customerID, messageID, bizID, otpMessage, "Awajima App", customerName, selectedOffice, joinedDate);

                                            } catch (NullPointerException e) {
                                                e.printStackTrace();
                                            }

                                        }



                                    }

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

        /*btnVerifyOTPAndSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(translater);
                boolean usernameTaken = false;
                nIN = null;
                accountTypeStr = AccountTypes.EWALLET;
                //long accountNumber = Long.parseLong(String.valueOf((long) (Math.random() * 10501) + 10010));

                otpPhoneNumber = "+234" + uPhoneNumber;
                code = pinEntryView.getText().toString().trim();

                if (code != null) {
                    if (code.equals(String.valueOf(otpDigit))) {
                        Toast.makeText(SignUpAct.this, "OTP verification, a Success", Toast.LENGTH_SHORT).show();
                        if (checkInputs()) {

                            //saveMyPreferences(sponsorID, cusLatLng, account, standingOrderAcct, joinedDate, uFirstName, uSurname, uPhoneNumber, uAddress, uUserName, uPassword,  customer, customerProfile, nIN, managerProfile, dateOfBirth, selectedGender,  selectedOffice, selectedState, birthday,  customerManager, dateOfBirth, profileID1, virtualAccountNumber, soAccountNumber, customerID, birthdayID, investmentAcctID, itemPurchaseAcctID,promoAcctID, packageAcctID,  customers);
                            saveInDatabase(sponsorID, cusLatLng, account, standingOrderAcct, joinedDate, uFirstName, uSurname, uPhoneNumber, uAddress, uUserName, uPassword, customer, customerProfile, nIN, managerProfile, dateOfBirth, selectedGender, selectedOffice, selectedState, birthday, customerManager, dateOfBirth, profileID1, virtualAccountNumber, soAccountNumber, customerID, birthdayID, investmentAcctID, itemPurchaseAcctID, promoAcctID, packageAcctID, customers);
                            saveNewCustomer(sponsorID, cusLatLng, account, standingOrderAcct, joinedDate, uFirstName, uSurname, uPhoneNumber, uAddress, uUserName, uPassword,  customer, customerProfile, nIN, managerProfile, dateOfBirth, selectedGender,  selectedOffice, selectedState, birthday,  customerManager, dateOfBirth, profileID1, virtualAccountNumber, soAccountNumber, customerID, birthdayID, investmentAcctID, itemPurchaseAcctID,promoAcctID, packageAcctID,  customers);

                        }


                    } else {
                        Toast.makeText(SignUpAct.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        pinEntryView.setText("Wrong OTP...");
                        pinEntryView.requestFocus();
                    }
                    progressBar.setVisibility(View.VISIBLE);

                }


            }
        });*/


    }
    public void DoAwajimaSignUp(View view) {
        otpDigit = ThreadLocalRandom.current().nextInt(122, 1631);
        messageID = ThreadLocalRandom.current().nextInt(1125, 10400);
        usdSymbolNo = ThreadLocalRandom.current().nextInt(18225, 1040012);
        gbpSymbolNo = ThreadLocalRandom.current().nextInt(10235, 10721400);
        audNo = ThreadLocalRandom.current().nextInt(10235, 10301400);
        cadNo = ThreadLocalRandom.current().nextInt(10235, 10021000);
        pakNo = ThreadLocalRandom.current().nextInt(1120, 10400);
        saNo = ThreadLocalRandom.current().nextInt(1121, 10400);
        ghaNo = ThreadLocalRandom.current().nextInt(1022, 10200);
        kenNo = ThreadLocalRandom.current().nextInt(1103, 10500);
        philNo = ThreadLocalRandom.current().nextInt(1314, 10700);
        indianNo = ThreadLocalRandom.current().nextInt(1014, 10301);
        userProfile = new Profile();
        account = new Account();
        awajima= new Awajima();
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
        customerProfile = new Profile();
        birthday = new Birthday();


        otpMessage = "&message=" + "Hello Awajima New User, Your OTP Code is " + otpDigit;

        profileID1 = random.nextInt((int) (Math.random() * 1400) + 1115);
        try {
            virtualAccountNumber = Integer.parseInt(RandomAcctNo.getAcctNumeric(10));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //virtualAccountNumber = random.nextInt((int) (Math.random() * 123045) + 100123);
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
        state = findViewById(R.id.state1);
        office = findViewById(R.id.office5);
        spnGender = findViewById(R.id.gender);
        calendar = Calendar.getInstance();
        profiles = new ArrayList<>();
        customers = new ArrayList<>();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        joinedDate = mdformat.format(calendar.getTime());
        prefManager= new PrefManager();
        lastQBUserUsed= qbUser;
        bizNameBundle= new Bundle();
        qbUser= new QBUser();
        profile= new Profile();
        lastAccountUsed = new Account();
        tellerProfile = new Profile();
        lastCustomerUsed=new Customer();
        usdAccount= new Account();
        gbpAccount= new Account();
        indianAccount= new Account();
        cadAccount= new Account();
        ghaAccount= new Account();
        kenAccount= new Account();
        philAccount= new Account();
        saAccount= new Account();
        pakistanAccount= new Account();
        lastStandingOrderAcctUsed= new StandingOrderAcct();

        edtPhone_number = findViewById(R.id.phone_number);
        email_address = findViewById(R.id.email_address);
        edtAddress_2 = findViewById(R.id.address_all);
        edtFirstName = findViewById(R.id.first_Name_00);
        userName = findViewById(R.id.user_name_1);
        edtSurname = findViewById(R.id.surname1);
        edtPassword = findViewById(R.id.user_password_sig);
        edtNIN = findViewById(R.id.profile_NIN);
        selectedState = state.getSelectedItem().toString();
        selectedOffice = office.getSelectedItem().toString();
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        cusDAO= new CusDAO(this);
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
            profileID = 0;

        }
        if(cusDAO !=null){
            try {
                customers = cusDAO.getAllCustomers11();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }

        Animation translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        selectedGender = spnGender.getSelectedItem().toString();
        edtSponsorID = findViewById(R.id.user_sponsor);
        uFirstName = edtFirstName.getText().toString().trim();
        uSurname = edtSurname.getText().toString().trim();
        uEmail = email_address.getText().toString();
        sponsorIDString = edtSponsorID.getText().toString().trim();
        uAddress = Objects.requireNonNull(edtAddress_2.getText()).toString();
        uPassword = edtPassword.getText().toString().trim();
        //uPhoneNumber=ccp.getFullNumberWithPlus();
        uPhoneNumber = edtPhone_number.getText().toString();
        uUserName = userName.getText().toString();
        boolean usernameTaken = false;
        nIN = null;
        customerName = uSurname + "," + uFirstName;
        ccString=ccp.getFullNumberWithPlus();
        try {
            sponsorID = Integer.parseInt(sponsorIDString);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        view.startAnimation(translater);
        nIN = null;
        accountTypeStr = AccountTypes.EWALLET;
        //long accountNumber = Long.parseLong(String.valueOf((long) (Math.random() * 10501) + 10010));

        //ccp.registerCarrierNumberEditText(edtPhone_number);


        try {

            if (TextUtils.isEmpty(uFirstName)) {
                edtFirstName.setError("Please enter Your First Name");
            } else if (TextUtils.isEmpty(uSurname)) {
                edtSurname.setError("Please enter your Last/SurName");
            } else if (TextUtils.isEmpty(uPassword)) {
                edtPassword.setError("Please enter your Password");
            } else if (uPhoneNumber.isEmpty()) {
                edtPhone_number.setError("Enter a valid mobile Number");
                //edtPhone_number.requestFocus();
            } else if (TextUtils.isEmpty(uUserName)) {
                userName.setError("Please enter your userName");
            } else if (TextUtils.isEmpty(uAddress)) {
                edtAddress_2.setError("Please enter Address");
            } else {
                //sendOTPVerCode(otpPhoneNumber,mAuth,sponsorID,account,standingOrderAcct,customer,joinedDate,uFirstName,uSurname,uPhoneNumber,uAddress,uUserName,uPassword,customer,customerProfile,nIN,managerProfile,dateOfBirth,selectedGender,selectedOffice,selectedState,birthday,customerManager,dateOfBirth,profileID1,virtualAccountNumber,soAccountNumber, customerID,profileID2,birthdayID, investmentAcctID,itemPurchaseAcctID,promoAcctID,packageAcctID,profiles,customers,tellers,adminUserArrayList,superAdminArrayList);

                for (int i = 0; i < customers.size(); i++) {
                    try {
                        if (customers.get(i).getCusPhoneNumber().equalsIgnoreCase(ccString)) {
                            Toast.makeText(SignUpAct.this, "This Phone Number is already in use, here", Toast.LENGTH_LONG).show();
                            return;

                        } else {
                            if (checkInputs()) {
                                //progressBar.setVisibility(View.VISIBLE);

                                //saveMyPreferences(sponsorID, cusLatLng, account, standingOrderAcct, joinedDate, uFirstName, uSurname, uPhoneNumber, uAddress, uUserName, uPassword,  customer, customerProfile, nIN, managerProfile, dateOfBirth, selectedGender,  selectedOffice, selectedState, birthday,  customerManager, dateOfBirth, profileID1, virtualAccountNumber, soAccountNumber, customerID, birthdayID, investmentAcctID, itemPurchaseAcctID,promoAcctID, packageAcctID,  customers);
                                saveInDatabase(sponsorID, this.cusLatLng, account, standingOrderAcct, joinedDate, uFirstName, uSurname, ccString, uAddress, uUserName, uPassword, customer, customerProfile, nIN, managerProfile, dateOfBirth, selectedGender, selectedOffice, selectedState, birthday, customerManager, this.dateOfBirth, profileID1, virtualAccountNumber, soAccountNumber, customerID, birthdayID, investmentAcctID, itemPurchaseAcctID, promoAcctID, packageAcctID, customers);
                                saveNewCustomer(sponsorID, this.cusLatLng, account, standingOrderAcct, joinedDate, uFirstName, uSurname, ccString, uAddress, uUserName, uPassword,  customer, customerProfile, nIN, managerProfile, dateOfBirth, selectedGender,  selectedOffice, selectedState, birthday,  customerManager, this.dateOfBirth, profileID1, virtualAccountNumber, soAccountNumber, customerID, birthdayID, investmentAcctID, itemPurchaseAcctID,promoAcctID, packageAcctID,  customers);
                                openDashboard(sponsorID, this.cusLatLng, account, standingOrderAcct, joinedDate, uFirstName, uSurname, ccString, uAddress, uUserName, uPassword,  customer, customerProfile, nIN, managerProfile, dateOfBirth, selectedGender,  selectedOffice, selectedState, birthday,  customerManager, this.dateOfBirth, profileID1, virtualAccountNumber, soAccountNumber, customerID, birthdayID, investmentAcctID, itemPurchaseAcctID,promoAcctID, packageAcctID,  customers);

                            }


                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }

                }


            }

        } catch (Exception e) {
            System.out.println("Oops!");
        }


    }

    private void openDashboard(int sponsorID, LatLng cusLatLng, Account account, StandingOrderAcct standingOrderAcct, String joinedDate, String uFirstName, String uSurname, String ccString, String uAddress, String uUserName, String uPassword, Customer customer, Profile customerProfile, String nIN, Profile managerProfile, String dateOfBirth, String selectedGender, String selectedOffice, String selectedState, Birthday birthday, CustomerManager customerManager, String dateOfBirth1, int profileID1, int virtualAccountNumber, int soAccountNumber, int customerID, int birthdayID, int investmentAcctID, int itemPurchaseAcctID, int promoAcctID, int packageAcctID, ArrayList<Customer> customers) {
        Bundle userBundle = new Bundle();
        userBundle.putString(PROFILE_DOB, dateOfBirth);
        userBundle.putString(BANK_ACCT_NO, bankAcctNumber);
        userBundle.putString("BANK_ACCT_NO", bankAcctNumber);
        userBundle.putString(PROFILE_EMAIL, uEmail);
        userBundle.putString(PROFILE_OFFICE, selectedOffice);
        userBundle.putString(PROFILE_FIRSTNAME, uFirstName);
        userBundle.putString(PROFILE_GENDER, selectedGender);
        userBundle.putString(PROFILE_COUNTRY, "");
        userBundle.putString(PROFILE_NEXT_OF_KIN, "");
        userBundle.putString(PROFILE_PHONE, ccString);
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
        userBundle.putInt(PROFILE_SPONSOR_ID, sponsorID);
        userBundle.putString("machine", "Customer");

        userBundle.putString("PICTURE_URI", String.valueOf(mImageUri));
        userBundle.putString("PROFILE_NIN", nIN);
        userBundle.putLong("PROFILE_ID", profileID1);

        userBundle.putString("CHOSEN_OFFICE", selectedOffice);
        userBundle.putInt("CUSTOMER_ID", customerID);
        userBundle.putString("EMAIL_ADDRESS", uEmail);
        userBundle.putString("DATE_OF_BIRTH_KEY", dateOfBirth);
        userBundle.putString("GENDER_KEY", selectedGender);
        userBundle.putInt("USER_SPONSOR_ID", sponsorID);
        userBundle.putString("PROFILE_DOB", dateOfBirth);
        userBundle.putString("BANK_ACCT_NO", bankAcctNumber);
        userBundle.putString("BANK_ACCT_NO", bankAcctNumber);
        userBundle.putString("PROFILE_EMAIL", uEmail);
        userBundle.putString("PROFILE_OFFICE", selectedOffice);
        userBundle.putString("PROFILE_FIRSTNAME", uFirstName);
        userBundle.putString("PROFILE_GENDER", selectedGender);
        userBundle.putString("PROFILE_COUNTRY", "");
        userBundle.putString("PROFILE_PHONE", ccString);
        userBundle.putString("PROFILE_SURNAME", uSurname);
        userBundle.putString("PICTURE_URI", String.valueOf(mImageUri));
        userBundle.putString("CUSTOMER_LATLONG", String.valueOf(cusLatLng));
        userBundle.putString("PROFILE_PASSWORD", uPassword);
        userBundle.putString("PROFILE_NIN", nIN);
        userBundle.putString("PROFILE_STATE", selectedState);
        userBundle.putString("PROFILE_ROLE", "Customer");
        userBundle.putString("PROFILE_STATUS", "Pending Approval");
        userBundle.putString("PROFILE_USERNAME", uUserName);
        userBundle.putInt("PROFILE_ID", profileID1);
        userBundle.putString("PROFILE_DATE_JOINED", joinedDate);
        userBundle.putInt("PROFILE_SPONSOR_ID", sponsorID);
        Intent intent = new Intent(SignUpAct.this, NewCustomerDrawer.class);
        intent.putExtras(userBundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        Toast.makeText(SignUpAct.this, "Thank you" + "" +
                "for Signing up " + "" + uFirstName + "" + "on the Awajima. App", Toast.LENGTH_LONG).show();

    }


    private void checkUser() {
        if(signUpK !=null){
            userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            String machine = userPreferences.getString("machine","");
            Bundle bundle = new Bundle();
            bundle.putInt("ProfileID", profileID);
            bundle.putString(machine, machine);
            bundle.putParcelable("Profile", profile);
            bundle.putParcelable("Customer", customer);
            //bundle.putString("FirstName", profileFirstName);
            Intent intent = new Intent(this, NewCustomerDrawer.class);
            intent.putExtras(bundle);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void doLocationStuff() {
        //getSupportLoaderManager().initLoader(EXISTING_PROFILE_LOADER, null, this);

        try {

            sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();


        referrerClient = InstallReferrerClient.newBuilder(this).build();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);


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
                    userLocation = new LatLng(latitude, longitude);
                    cusLatLng = userLocation;


                    try {
                        Geocoder newGeocoder = new Geocoder(SignUpAct.this, Locale.ENGLISH);
                        List<Address> newAddresses = newGeocoder.getFromLocation(latitude, longitude, 1);
                        StringBuilder street = new StringBuilder();
                        if (Geocoder.isPresent()) {

                            locTxt.setVisibility(View.VISIBLE);

                            Address newAddress = newAddresses.get(0);

                            String localityString = newAddress.getLocality();

                            street.append(localityString).append("");

                            locTxt.setText(MessageFormat.format("Where you are:  {0},{1}/{2}", latitude, "" + longitude, street));
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


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        cancellationTokenSource = new CancellationTokenSource();

    }



    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }



    private void doTeliverNoti(String otpMessage) {
        PushData pushData = new PushData("Unne");
        //pushData.setMessage(otpMessage+ ", " + trackingId);
        //Teliver.sendEventPush(trackingId, pushData, "taxi");


    }

    private void doOtpNotification(String otpMessage) {

        try {
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(SignUpAct.this, sound);
            ringtone.play();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent notificationIntent = new Intent(this, SignUpAct.class);
        notificationIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        notificationIntent.putExtra("pdus", 0);
        //notificationIntent.setAction(ACTION_SNOOZE);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SignUpAct.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        //Uri sound = Uri. parse (ContentResolver. SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/quite_impressed.mp3" ) ;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.logo_awa_round)
                        .setContentTitle("Your Awajima OTP Message")
                        .setSound(sound)
                        .setChannelId( NOTIFICATION_CHANNEL_ID )
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setAutoCancel(true)
                        .setNumber(++numMessages)
                         .setContentIntent(resultPendingIntent)
                        .setContentText(otpMessage);


        layoutOTP.setVisibility(View.VISIBLE);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            notificationChannel.setSound(sound , audioAttributes) ;

            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis (), builder.build()) ;
        mNotificationManager.notify(20, builder.build());
    }

    private boolean checkInputs() {
        @SuppressLint("UseCompatLoadingForDrawables") Drawable customErrorIcon = getResources().getDrawable(R.drawable.ic_error_black_24dp);
        customErrorIcon.setBounds(0, 0, customErrorIcon.getIntrinsicWidth(), customErrorIcon.getIntrinsicHeight());

        if (TextUtils.isEmpty(edtFirstName.getText().toString())) {
            edtFirstName.setError("Enter First Name", customErrorIcon);
            btnVerifyOTPAndSignUp.setEnabled(false);
            btnVerifyOTPAndSignUp.setTextColor(Color.argb(50, 0, 0, 0));
            //edtFirstName.requestFocus();
        } else if (TextUtils.isEmpty(edtSurname.getText().toString())) {
            edtSurname.setError("Enter Surname", customErrorIcon);
            btnVerifyOTPAndSignUp.setEnabled(false);
            btnVerifyOTPAndSignUp.setTextColor(Color.argb(50, 0, 0, 0));
            //edtSurname.requestFocus();
        } else if (TextUtils.isEmpty(userName.getText().toString())) {
            userName.setError("Enter UserName", customErrorIcon);
            btnVerifyOTPAndSignUp.setEnabled(false);
            //edtPassword.requestFocus();
        }
        else if (edtPassword.getText().toString().length() < 4) {
            edtPassword.setError("Password cannot be less than 4", customErrorIcon);
            btnVerifyOTPAndSignUp.setEnabled(false);
            btnVerifyOTPAndSignUp.setTextColor(Color.argb(50, 0, 0, 0));
            //edtPassword.requestFocus();
        }
        else if (TextUtils.isEmpty(edtPassword.getText().toString())) {
            edtPassword.setError("Enter Password", customErrorIcon);
            btnVerifyOTPAndSignUp.setEnabled(false);
            btnVerifyOTPAndSignUp.setTextColor(Color.argb(50, 0, 0, 0));
            //edtPassword.requestFocus();
        }
        else if (TextUtils.isEmpty(edtNIN.getText().toString())) {
            edtNIN.setError("Enter your National ID. Number", customErrorIcon);
            btnVerifyOTPAndSignUp.setEnabled(false);
            btnVerifyOTPAndSignUp.setTextColor(Color.argb(50, 0, 0, 0));
            edtNIN.requestFocus();
        }else if (TextUtils.isEmpty(edtAddress_2.getText().toString())) {
            edtAddress_2.setError("Enter Your Address", customErrorIcon);
            btnVerifyOTPAndSignUp.setEnabled(false);
            btnVerifyOTPAndSignUp.setTextColor(Color.argb(50, 0, 0, 0));
            //edtPhone_number.requestFocus();
        }
        else {
            btnVerifyOTPAndSignUp.setEnabled(true);
            btnVerifyOTPAndSignUp.setTextColor(Color.rgb(0, 0, 0));
        }


        return false;
    }

    private void saveNewCustomer(int sponsorID, LatLng cusLatLng, Account account, StandingOrderAcct standingOrderAcct, String joinedDate, String uFirstName, String uSurname, String ccString, String uAddress, String uUserName, String uPassword, Customer customer, Profile customerProfile, String nIN, Profile managerProfile, String dateOfBirth, String selectedGender, String selectedOffice, String selectedState, Birthday birthday, CustomerManager customerManager, String dateOfBirth1, int profileID1, int virtualAccountNumber, int soAccountNumber, int customerID, int birthdayID, int investmentAcctID, int itemPurchaseAcctID, int promoAcctID, int packageAcctID, ArrayList<Customer> customers) {
        profile= new Profile();
        profile=customerProfile;
        tellerProfile = new Profile();
        name =uSurname+","+uFirstName;
        accountArrayList= new ArrayList<>();
        //countryUS= new Country("USA",);
        //countryUK= new Country();
        AcctDAO acctDAO1= new AcctDAO(this);

        Bundle userBundle = new Bundle();
        prefManager= new PrefManager();
        awajima= new Awajima();
        gson6 = new Gson();
        gson5 = new Gson();
        gson7 = new Gson();
        //currency= new Currency();
        try {
            usdSymbolNo = Integer.parseInt(RandomAcctNo.getAcctNumeric(10));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        currencies=World.getAllCurrencies();
        currency2 = World.getAllCurrencies().get(7);
        //usd,gbp , cad, cedi,pak,keny,sa,phil
        //CAD,KES
        pakNo = ThreadLocalRandom.current().nextInt(1120, 10400);
        saNo = ThreadLocalRandom.current().nextInt(1121, 10400);
        ghaNo = ThreadLocalRandom.current().nextInt(1022, 10200);
        kenNo = ThreadLocalRandom.current().nextInt(1103, 10500);
        philNo = ThreadLocalRandom.current().nextInt(1314, 10700);

        //int awajimaAcctNo, int cusID, String accountName, String currencyCode,long accountNo, double accountBalance, AccountTypes accountType,String status

        gbpSymbolNo = ThreadLocalRandom.current().nextInt(10235, 10721400);
        usdAccount= new Account(virtualAccountNumber,profileID1,customerID,name,"USD",usdSymbolNo,0.00,AccountTypes.BANK_ACCT, "new");
        cadAccount= new Account(virtualAccountNumber,profileID1,customerID,name,"CAD",cadNo,0.00,AccountTypes.BANK_ACCT, "new");
        saAccount= new Account(virtualAccountNumber,profileID1,customerID,name,"ZAR",saNo,0.00,AccountTypes.BANK_ACCT, "new");
        philAccount= new Account(virtualAccountNumber,profileID1,customerID,name,"PHP",philNo,0.00,AccountTypes.BANK_ACCT, "new");
        ghaAccount= new Account(virtualAccountNumber,profileID1,customerID,name,"GHS",ghaNo,0.00,AccountTypes.BANK_ACCT, "new");
        kenAccount= new Account(virtualAccountNumber,profileID1,customerID,name,"KES",kenNo,0.00,AccountTypes.BANK_ACCT, "new");
        pakistanAccount= new Account(virtualAccountNumber,profileID1,customerID,name,"PKR",pakNo,0.00,AccountTypes.BANK_ACCT, "new");
        gbpAccount= new Account(virtualAccountNumber,profileID1,customerID,name,"GBP",gbpSymbolNo,0.00,AccountTypes.BANK_ACCT, "new");
        indianAccount= new Account(virtualAccountNumber,profileID1,customerID,name,"INR",indianNo,0.00,AccountTypes.BANK_ACCT, "new");


        profile = new Profile(profileID1, uSurname, uFirstName, ccString, uEmail, dateOfBirth, selectedGender, uAddress, "", selectedState, selectedOffice, joinedDate, "Customer", uUserName, uPassword, "pending", "");

        qbUser= new QBUser();
        qbUser.setCustomData(selectedOffice);
        qbUser.setEmail(uEmail);
        qbUser.setPassword(uPassword);
        qbUser.setCustomDataClass(profile.getClass());
        qbUser.setFullName(name);
        qbUser.setExternalId(String.valueOf(profileID));
        qbUser.setPhone(ccString);
        qbUser.setId(profileID1);
        //qbUser.setExternalId(String.valueOf(customerID));
        //usd=currency.toString();
        accountArrayList.add(gbpAccount);
        accountArrayList.add(usdAccount);
        accountArrayList.add(cadAccount);
        accountArrayList.add(saAccount);
        accountArrayList.add(philAccount);
        accountArrayList.add(pakistanAccount);
        accountArrayList.add(kenAccount);
        accountArrayList.add(ghaAccount);

        if(acctDAO1 !=null){
            acctDAO1.insertAccounts(accountArrayList);
        }


        if (userPreferences !=null){
            userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            gson3 = new Gson();
            lastQBUserUsed= qbUser;
            json3 = gson3.toJson(lastQBUserUsed);

        }


        SharedPreferences.Editor editor = userPreferences.edit();
        editor.putString("PROFILE_DOB", dateOfBirth);
        editor.putString("PROFILE_EMAIL", uEmail);
        editor.putString("PROFILE_OFFICE", selectedOffice);
        editor.putString("PROFILE_FIRSTNAME", uFirstName);
        editor.putString("PROFILE_GENDER", selectedGender);
        editor.putString("PROFILE_COUNTRY", "");
        editor.putString("PROFILE_NEXT_OF_KIN", "");
        editor.putString("PROFILE_PHONE", ccString);
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
        editor.putString("signUpK", signUpK);
        editor.putString("PROFILE_USERNAME", uUserName);
        editor.putString("PROFILE_PASSWORD", uPassword);
        editor.putInt("CUSTOMER_ID", customerID);
        editor.putString("PROFILE_SURNAME", uSurname);
        editor.putString("PROFILE_FIRSTNAME", uFirstName);
        editor.putString("PROFILE_PHONE", ccString);
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
        editor.putString(PROFILE_PHONE, ccString);
        editor.putString(PROFILE_SURNAME, uSurname);
        editor.putString("PICTURE_URI", String.valueOf(mImageUri));
        editor.putString(PICTURE_URI, String.valueOf(mImageUri));
        editor.putString(CUSTOMER_LATLONG, String.valueOf(cusLatLng));
        editor.putString(PROFILE_PASSWORD, uPassword);
        editor.putString(PROFILE_NIN, nIN);
        editor.putString(PROFILE_STATE, selectedState);
        editor.putString(PROFILE_ROLE, "Customer");
        editor.putString(PROFILE_STATUS, "New");
        editor.putInt(PROFILE_ID, profileID1);
        editor.putString(PROFILE_DATE_JOINED, joinedDate);
        editor.putString(PROFILE_USERNAME, uUserName);
        editor.putString(PROFILE_PASSWORD, uPassword);
        editor.putInt(CUSTOMER_ID, customerID);
        editor.putString(PROFILE_SURNAME, uSurname);
        editor.putString(PROFILE_FIRSTNAME, uFirstName);
        editor.putString(PROFILE_PHONE, ccString);
        editor.putString(PROFILE_EMAIL, uEmail);
        editor.putString(PROFILE_DOB, dateOfBirth);
        editor.putString(PROFILE_ADDRESS, uAddress);
        editor.putString(PROFILE_GENDER, selectedGender);
        editor.putString(PROFILE_STATE, selectedState);
        editor.putString("machine", "Customer");
        editor.putString("Machine", "Customer");
        editor.putLong("EWalletID", virtualAccountNumber);

        editor.putLong("LastStandingOrderAcctUsed", soAccountNumber);
        //editor.putLong("TransactionAcctID", transactionAcctID);
        editor.putInt("InvestmentAcctID", investmentAcctID);
        editor.putInt("PromoAcctID", promoAcctID);
        editor.putInt("ItemsPurchaseAcctID", itemPurchaseAcctID);
        editor.putInt("SavingsAcctID", savingsAcctID);
        //editor.putLong("MessageAcctID", finalProfileID1);


        editor.putString("PICTURE_URI", String.valueOf(mImageUri));
        editor.putInt("PROFILE_ID", profileID1);
        editor.putString("EMAIL_ADDRESS", uEmail);
        editor.putString("Machine", "Customer");
        editor.putString("LastQBUserUsed", json3).apply();
        prefManager.saveLoginDetails(uEmail,uPassword);



        tellerProfile=managerProfile;
        if (customerManager != null) {
            ManagerSurname = customerManager.getTSurname();
            managerFirstName = customerManager.getTFirstName();
            profileID = customerManager.getTellerProfileID();
            managerPhoneNumber1 = customerManager.getTPhoneNumber();
            managerEmail = customerManager.getTEmailAddress();
            managerUserName = customerManager.getTUserName();
            officePref = customerManager.getTOffice();
            managerAddress = customerManager.getTAddress();
            managerGender = customerManager.getTGender();
            customerManager.addCustomer(0,customerID, name, ccString,  joinedDate,"New");

        }

        if(customer !=null){
            customer.setCusProfile(customerProfile);
            customer.setCusFirstName(uFirstName);
            customer.setCusSurname(uSurname);
            customer.setCusPin(uPassword);
            customer.setCusAddress(uAddress);
            customer.setCusDob(dateOfBirth);
            customer.setCusUID(customerID);
            customer.setCusDateJoined(joinedDate);
            customer.setCusEmail(uEmail);
            customer.setCusRole("customer");
            customer.setCusPhoneNumber(ccString);
            customer.setCusGender(selectedGender);
            customer.setCustomerLocation(cusLatLng);
            customer.setCusUserName(uUserName);
            customer.setCusState(selectedState);
            customer.setCusSponsorID(sponsorID);
            customer.setCusOffice(selectedOffice);
            customer.setCusAccount(account);
            customer.setCusStandingOrderAcct(standingOrderAcct);
            customer.setCusProfilePicture(String.valueOf(mImageUri));
            customer.setCusAccount(account);
            customer.setCusAccount(usdAccount);
            customer.setCusAccount(gbpAccount);
            customer.setCusStandingOrderAcct(standingOrderAcct);
            customer.setCusProfile(profile);
            customer.setCusAccounts(accountArrayList);
            customer.addCusAccountManager(managerProfileID, ManagerSurname, managerFirstName, managerGender, officePref);

            profile.setProfileFirstName(uFirstName);
            profile.setProfileLastName(uSurname);
            profile.setProfileAddress(uAddress);
            profile.setProfilePassword(uPassword);
            profile.setProfileDob(dateOfBirth);
            profile.setProfileDateJoined(joinedDate);
            profile.setProfileEmail(uEmail);
            profile.setProfileRole("customer");
            profile.setProfilePhoneNumber(ccString);
            profile.setProfileGender(selectedGender);
            profile.setProfileIdentity(null);
            profile.setProfileLastKnownLocation(this.cusLatLng);
            profile.setProfileMachine("customer");
            profile.setProfileUserName(uUserName);
            profile.setProfileState(selectedState);
            profile.setProfileSponsorID(profileID);
            profile.setProfOfficeName(selectedOffice);
            profile.setProfile_CustomerManager(customerManager);
            profile.setProfilePicture(mImageUri);
            profile.setProfile_Accounts(accountArrayList);
            int countC=0;
            profile.setProfQbUser(qbUser);
            try {
                awajima.addNewCustomer(customer);
                awajima.addProfile(profile);
                awajima.addQBUsers(qbUser);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            SignUpUser(qbUser,profile);



        }


    }
    private void SignUpUser(final QBUser newUser, final Profile lastProfileUsed) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        newUser.setCustomDataClass(lastProfileUsed.getClass());
        QBUsers.signUp(newUser).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                signinUser(qbUser,lastProfileUsed);

            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }
    private void signinUser(final QBUser newUser,Profile lastProfileUsed) {
        QBSettings.getInstance().init(this, APPLICATION_ID, QUICKBLOX_AUTH_KEY, QUICKBLOX_SECRET_KEY);
        QBSettings.getInstance().setAccountKey(QUICKBLOX_ACCT_KEY);
        QBUsers.signIn(newUser).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                Toast.makeText(SignUpAct.this, "Login successfully", Toast.LENGTH_SHORT).show();
                QBChatService.setDebugEnabled(true); // enable chat logging
                QBChatService.setDefaultPacketReplyTimeout(10000);
                Bundle newBundle= new Bundle();

                QBChatService.ConfigurationBuilder chatServiceConfigurationBuilder = new QBChatService.ConfigurationBuilder();
                chatServiceConfigurationBuilder.setSocketTimeout(60);
                chatServiceConfigurationBuilder.setKeepAlive(true);
                chatServiceConfigurationBuilder.setUseTls(true);
                QBChatService.setConfigurationBuilder(chatServiceConfigurationBuilder);
                newBundle=bundle;
                newBundle.putParcelable("QBUser", (Parcelable) qbUser);
                newBundle.putParcelable("Profile",lastProfileUsed);

                Intent intent = new Intent(SignUpAct.this, NewCustomerDrawer.class);
                intent.putExtras(newBundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);


            }

            @Override
            public void onError(QBResponseException e) {
                Toast.makeText(SignUpAct.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void saveInDatabase(int sponsorID, LatLng cusLatLng, Account account, StandingOrderAcct standingOrderAcct, String joinedDate, String uFirstName, String uSurname, String ccString, String uAddress, String uUserName, String uPassword, Customer customer, Profile customerProfile, String nIN, Profile managerProfile, String dateOfBirth, String selectedGender, String selectedOffice, String selectedState, Birthday birthday, CustomerManager customerManager, String ofBirth, int profileID1, int virtualAccountNumber, int soAccountNumber, int customerID, int birthdayID, int investmentAcctID, int itemPurchaseAcctID, int promoAcctID, int packageAcctID, ArrayList<Customer> customers) {
        Toast.makeText(SignUpAct.this, "Gender: " + selectedGender + "," + "Office:" + selectedOffice + "" + "State:" + selectedState, Toast.LENGTH_SHORT).show();
        showProgressDialog();
        dbHelper = new DBHelper(this);
        ran = new Random();
        SQLiteDataBaseBuild();
        calendar = Calendar.getInstance();
        sqLiteDatabase = dbHelper.getWritableDatabase();
        startBankAcctCreationForResult.launch(new Intent(SignUpAct.this, MyAcctOverViewAct.class));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        timeLineTime = mdformat.format(calendar.getTime());



        //customers = dbHelper.getAllCustomers();

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
        String accountName = uSurname + "," + uFirstName;


        birthdayID = random.nextInt((int) (Math.random() * 1001) + 1010);
        String skylightMFb = "E-Wallet";
        double accountBalance = 0.00;
        accountTypeStr = AccountTypes.EWALLET;
        String interestRate = "0.0";
        Bundle userBundle= new Bundle();

        if (managerProfile != null) {
            ManagerSurname = managerProfile.getProfileLastName();
            managerFirstName = managerProfile.getProfileFirstName();
            profileID = managerProfile.getPID();
            managerPhoneNumber1 = managerProfile.getProfilePhoneNumber();
            managerEmail = managerProfile.getProfileEmail();
            managerUserName = managerProfile.getProfileUserName();
            officePref = managerProfile.getProfOfficeName();
            customerManager = managerProfile.getProfile_CustomerManager();
            managerAddress = managerProfile.getProfileAddress();
            managerGender = managerProfile.getProfileGender();
            managerProfile.addPTimeLine(tittleT1, timelineDetailsT11);
            managerProfile.addPCustomer(customerID, uSurname, uFirstName, ccString, uEmail, uAddress, selectedGender, selectedOffice, selectedState, mImageUri, joinedDate, uUserName, uPassword);

        }
        int finalProfileID = profileID1;

        account = new Account(virtualAccountNumber,"", accountName,bankAcctNumber, accountBalance, accountTypeStr);
        standingOrderAcct = new StandingOrderAcct(virtualAccountNumber + 12, accountName, 0.00);
        customer = new Customer(customerID, uSurname, uFirstName, ccString, uEmail, nIN, dateOfBirth, selectedGender, uAddress, uUserName, uPassword, selectedOffice, joinedDate);
        birthday = new Birthday(birthdayID, profileID1, uSurname + "," + uFirstName, ccString, uEmail, selectedGender, uAddress, dateOfBirth, 0, "", "Not celebrated");
        customerProfile = new Profile(profileID1, uSurname, uFirstName, ccString, uEmail, dateOfBirth, selectedGender, uAddress, "", selectedState, selectedOffice, joinedDate, "Customer", uUserName, uPassword, "pending", "");

        lastProfileUsed = customerProfile;
        SODAO sodao= new SODAO(this);

        saveMyPreferences(sponsorID,cusLatLng,account,standingOrderAcct,joinedDate,uFirstName,uSurname,ccString,uAddress,uUserName,uPassword,customer,customerProfile,nIN,managerProfile, dateOfBirth, selectedGender, selectedOffice, selectedState, birthday,  customerManager, ofBirth, profileID1,  virtualAccountNumber, soAccountNumber,  customerID, birthdayID, investmentAcctID,itemPurchaseAcctID,  promoAcctID, packageAcctID,  customers);

        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            if(acctDAO !=null){
                try {
                    acctDAO.insertAccount(profileID1, customerID, skylightMFb, accountName, virtualAccountNumber, accountBalance, accountTypeStr);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }


        }

        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }

            try {
                dbHelper.insertRole(profileID1, "Customer",  ccString);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }

        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                dbHelper.insertCustomer11(profileID1, customerID, uSurname, uFirstName, ccString, uEmail, dateOfBirth, selectedGender, uAddress, selectedState, "", joinedDate, uUserName, uPassword, mImageUri, "Customer");

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }

        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            if(birthdayDAO !=null){
                try {
                    birthdayDAO.insertBirthDay3(birthday, dateOfBirth);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }


        }

        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            if(timeLineClassDAO !=null){
                try {
                    timeLineClassDAO.insertTimeLine(tittleT1, timelineDetailsTD, timeLineTime, mCurrentLocation);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }



        }
        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                dbHelper.saveNewProfile(customerProfile);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }


        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            if(sodao !=null){
                try {
                    sodao.insertStandingOrderAcct(profileID1, customerID, virtualAccountNumber, accountName, 0.00);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }



        }


        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            if (cusLatLng != null) {
                try {
                    dbHelper.openDataBase();
                    try {
                        dbHelper.insertCustomerLocation(customerID, this.cusLatLng);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            EmptyEditTextAfterDataInsert();

        }





    }

    private void saveMyPreferences(int sponsorID, LatLng cusLatLng, Account account, StandingOrderAcct standingOrderAcct, String joinedDate, String uFirstName, String uSurname, String ccString, String uAddress, String uUserName, String uPassword, Customer customer, Profile customerProfile, String nIN, Profile managerProfile, String dateOfBirth, String selectedGender, String selectedOffice, String selectedState, Birthday birthday, CustomerManager customerManager, String ofBirth, int profileID1, int virtualAccountNumber, int soAccountNumber, int customerID, int birthdayID, int investmentAcctID, int itemPurchaseAcctID, int promoAcctID, int packageAcctID, ArrayList<Customer> customers) {
        Bundle userBundle = new Bundle();
        lastAccountUsed = new Account();
        smsMessage = "Welcome to the Awajima  App, may you have the best experience";

        sendSMSMessage22(ccString, smsMessage);
        sendTextMessage(ccString, smsMessage);
        lastAccountUsed =account;
        gson = new Gson();
        Gson gson4 = new Gson();
        Gson gson5 = new Gson();
        lastProfileUsed = customerProfile;
        lastCustomerUsed= customer;
        lastStandingOrderAcctUsed= standingOrderAcct;

        if (userPreferences !=null){
            userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            json = gson.toJson(lastProfileUsed);
            json1 = gson1.toJson(lastCustomerUsed);
            json4 = gson4.toJson(lastAccountUsed);
            json7 = gson7.toJson(lastStandingOrderAcctUsed);
            //json6 = gson6.toJson(lastAccountUsed);

        }


        SharedPreferences.Editor editor = userPreferences.edit();
        /*editor.putString("PROFILE_DOB", dateOfBirth);
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
        editor.putString("Machine", "Customer");*/
        editor.putLong("EWalletID", virtualAccountNumber);
        editor.putLong("StandingOrderAcct", soAccountNumber);
        //editor.putLong("TransactionAcctID", transactionAcctID);
        editor.putInt("InvestmentAcctID", investmentAcctID);
        editor.putInt("PromoAcctID", promoAcctID);
        editor.putInt("ItemsPurchaseAcctID", itemPurchaseAcctID);
        editor.putInt("SavingsAcctID", savingsAcctID);
        //editor.putLong("MessageAcctID", finalProfileID1);

        /*editor.putString("USER_DOB", dateOfBirth);
        editor.putString("USER_EMAIL", uEmail);
        editor.putString("USER_OFFICE", selectedOffice);
        editor.putString("USER_FIRSTNAME", uFirstName);
        editor.putString("USER_GENDER", selectedGender);
        editor.putString("USER_COUNTRY", "");
        editor.putString("USER_NEXT_OF_KIN", "");
        editor.putString("USER_PHONE", ccString);
        editor.putString("USER_SURNAME", uSurname);
        //editor.putString("PICTURE_URI", String.valueOf(mImageUri));
        editor.putString("USER_LOCATION", "");
        editor.putString("USER_DATE_JOINED", "");
        editor.putString("USER_PASSWORD", uPassword);
        //editor.putString("PROFILE_NIN", nIN);
        editor.putString("USER_STATE", selectedState);
        editor.putString("USER_ROLE", "Customer");
        editor.putString("USER_STATUS", "Pending Approval");
        editor.putString("USERNAME", uUserName);
        //editor.putInt("PROFILE_ID", profileID1);
        editor.putString("USER_PASSWORD", uPassword);
        editor.putString("EMAIL_ADDRESS", uEmail);
        editor.putString("CHOSEN_OFFICE", selectedOffice);
        editor.putString("USER_NAME", uUserName);
        editor.putString("USER_DATE_JOINED", joinedDate);*/
        //editor.putString("Machine", "Customer");
        //editor.putString(PROFILE_ROLE, "Customer");
        editor.putString("LastCustomerUsed", json1);
        editor.putString("lastAccountUsed", json4);
        editor.putString("lastStandingOrderAcctUsed", json7);
        editor.putString("LastProfileUsed", json).apply();

        /*userBundle.putString(PROFILE_DOB, dateOfBirth);
        userBundle.putString(BANK_ACCT_NO, bankAcctNumber);
        userBundle.putString("BANK_ACCT_NO", bankAcctNumber);
        userBundle.putString(PROFILE_EMAIL, uEmail);
        userBundle.putString(PROFILE_OFFICE, selectedOffice);
        userBundle.putString(PROFILE_FIRSTNAME, uFirstName);
        userBundle.putString(PROFILE_GENDER, selectedGender);
        userBundle.putString(PROFILE_COUNTRY, "");
        userBundle.putString(PROFILE_NEXT_OF_KIN, "");
        userBundle.putString(PROFILE_PHONE, ccString);
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
        userBundle.putString("USER_PHONE", ccString);
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
        userBundle.putInt("USER_SPONSOR_ID", sponsorID);*/
        Intent intent = new Intent(SignUpAct.this, NewCustomerDrawer.class);
        intent.putExtras(userBundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

        Toast.makeText(SignUpAct.this, "Thank you" + "" +
                "for Signing up " + "" + uFirstName + "" + "on the Awajima. App", Toast.LENGTH_LONG).show();
        setResult(Activity.RESULT_OK, new Intent());

        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null) && requestCode == RESULT_CAMERA_CODE) {
            this.mImageUri = data.getData();
            Bitmap logoBitmap = drawableToBitmap(this.profilePix.getDrawable());
            Glide.with(SignUpAct.this)
                    .asBitmap()
                    .load(this.mImageUri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_alert)
                    //.listener(listener)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .centerCrop()
                    .into(this.profilePix);
            try {
                profilePix.setImageBitmap(addGradient(logoBitmap));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
        if ((data != null) && requestCode == RESULT_LOAD_IMAGE) {
            this.mImageUri = data.getData();
            Bitmap logoBitmap = drawableToBitmap(this.profilePix.getDrawable());
            Glide.with(SignUpAct.this)
                    .asBitmap()
                    .load(this.mImageUri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_alert)
                    //.listener(listener)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .centerCrop()
                    .into(this.profilePix);
            try {
                profilePix.setImageBitmap(addGradient(logoBitmap));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }



        }

    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public Bitmap addGradient(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        Bitmap updatedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(updatedBitmap);
        canvas.drawBitmap(originalBitmap, 0, 0, null);
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, 0, width/2, height, ResourceUtils.getColor(R.color.primary_blue), ResourceUtils.getColor(R.color.primary_purple), Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setFilterBitmap(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0, 0, width, height, paint);

        return updatedBitmap;
    }




    public String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getDeviceId(Context context) {
        @SuppressLint({"HardwareIds", "MissingPermission"}) final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (deviceId != null) {
            return deviceId;
        } else {
            return Build.SERIAL;
        }
    }


    @SuppressLint({"HardwareIds", "MissingPermission"})
    public String getDeviceUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = MessageFormat.format("{0}", tm.getDeviceId());
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = MessageFormat.format("{0}", Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

        }
        deviceMobileNo = tm.getLine1Number();

        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();

    }
    public static boolean isEmailValid(EditText email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email.getText().toString();

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
    public static boolean isOnline(ConnectivityManager cm) {
        @SuppressLint("MissingPermission") NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    public void loadProfiles(DBHelper dbHelper) {

        userProfile1 = new Profile(10000, "Emmanuel", "Uju", "08069524599", "lumgwuntesting1@gmail.com", "1980-04-19", "female", "Awajima", "", "Rivers", "Elelenwo", "2022-04-19", "SuperAdmin", "Skylight4ever", "@Awajima2", "Confirmed", "");
        userProfile2= new Profile(1000,"Bartha", "Ene", "08059250176", "lumgwuntesting2@gmail.com", "25/04/1989", "male", "PH", "","Rivers", "Elelenwo", "19/04/2022","Customer", "Lumgwun", "@Awajima3","Confirmed","");

        Message supportMessage = new Message(uPhoneNumber,otpDigit);
        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                try {
                    dbHelper.saveNewProfile(userProfile2);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }



            } catch (SQLiteException e) {
                e.printStackTrace();
            }




        }


        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                dbHelper.openDataBase();
                try {
                    messageDAO.saveNewMessage(supportMessage);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }



            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        }

        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                dbHelper.openDataBase();
                try {
                    timeLineClassDAO.insertTimeLine("Sign up", "", "2022-04-19", mCurrentLocation);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }



            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        }

        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                dbHelper.openDataBase();
                try {
                    dbHelper.insertCustomer11(1000002, 10, "Ezekiel", "Gwun-orene", "07038843102", "lumgwun1@gmail.com", "1983-04-25", "male", "Ilabuchi", "Rivers", "", "2022-04-19", "Lumgwun", "@Awajima1", Uri.parse(""), "Customer");

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }



            } catch (SQLiteException e) {
                e.printStackTrace();
            }



        }



        if(dbHelper !=null){
            try {

                if(sqLiteDatabase !=null){
                    sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            try {
                dbHelper.openDataBase();
                try {
                    dbHelper.saveNewProfile(userProfile1);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }



            } catch (SQLiteException e) {
                e.printStackTrace();
            }


        }




    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(SIGNUP_STATE_KEY, signUpK);
        super.onSaveInstanceState(outState);
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
                        appInstallRefReceiver= new AppInstallRefReceiver();
                        profileDao= new ProfDAO(SignUpAct.this);
                        try {
                            response = referrerClient.getInstallReferrer();

                            long referrerClickTime = response.getReferrerClickTimestampSeconds();

                            long appInstallTime = response.getInstallBeginTimestampSeconds();

                            boolean instantExperienceLaunched = response.getGooglePlayInstantParam();

                            referrer = response.getInstallReferrer();

                            //profileDao.addProfRefLink(profileID,referrer);

                            //IntentFilter filter = new IntentFilter();
                            Intent intent = new Intent();
                            intent.setAction("com.example.broadcast.MY_NOTIFICATION");
                            intent.putExtra("data", "Nothing to see here, move along.");
                            sendBroadcast(intent);

                            IntentFilter filter = new IntentFilter("com.android.vending.INSTALL_REFERRER");
                            filter.addAction(Intent.ACTION_MAIN);
                            registerReceiver(appInstallRefReceiver, filter, "android.permission.INSTALL_PACKAGES", null );
                            registerReceiver(appInstallRefReceiver, filter );


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
        //smsBundle.putString("from", "Awajima");
        smsBundle.putString("to", otpPhoneNumber);
        Intent otpIntent = new Intent(SignUpAct.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }



    private void PrePopulateDB() {
        dbHelper = new DBHelper(this);
        userProfile1 = new Profile(234, "Emmanuel", "Becky", "08069524599", "urskylight@gmail.com", "1980-04-19", "female", "Awajima", "", "Rivers", "Elelenwo", "2022-04-19", "SuperAdmin", "Skylight4ever", "@Awajima2", "Confirmed", "");
        userProfile2= new Profile(432,"Benedict", "Benedict", "08059250176", "bener@gmail.com", "25/04/1989", "male", "PH", "","Rivers", "Elelenwo", "19/04/2022","Customer", "Lumgwun", "@Awajima3","Confirmed","");

        sqLiteDatabase = dbHelper.getWritableDatabase();
        try {

            if(sqLiteDatabase !=null){
                sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        try {

            if (dbHelper != null) {
                profileDao.insertProfile(userProfile2);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {

            if (dbHelper != null) {
                //dbHelper.openDataBase();
                profileDao.insertProfile(userProfile1);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }





        /*if (DatabaseUtils.queryNumEntries(dbHelper.getWritableDatabase(),DATABASE_NAME) < 1) {


        }*/
    }


    public void EmptyEditTextAfterDataInsert() {

        edtPhone_number.getText().clear();

        email_address.getText().clear();

        edtAddress_2.getText().clear();
        edtFirstName.getText().clear();

        userName.getText().clear();

        edtSurname.getText().clear();
        edtPassword.getText().clear();

    }

    public void SQLiteDataBaseBuild() {

        dbHelper = new DBHelper(this);
        Profile profile33= new Profile();
        profile33 = new Profile(12903, "TestBoy", "Emeka", "07034123456", "leaveme@yahoo.com", "1987-02-09", "male", "no address", "", "Rivers", "Head Office", joinedDate, "Customer", "seeme", "finish", "pending", "");



        if (dbHelper != null) {
            //dbHelper.openDataBase();
            dbHelper.insertNewPackage(1000000,1234,92345678,4567,"Test Package","Savings",31,5000,"2022-17-06",5000000,"","Confirmed");


        }

        if (dbHelper !=null) {
            //dbHelper.openDataBase();
            profileDao.insertProfile(profile33);


        }


    }


    public void sendTextMessage(String uPhoneNumber, String smsMessage) {
        Bundle smsBundle = new Bundle();
        smsBundle.putString(PROFILE_PHONE, uPhoneNumber);
        smsBundle.putString("USER_PHONE", uPhoneNumber);
        smsBundle.putString("smsMessage", smsMessage);
        //smsBundle.putString("from", "Awajima Coop.");
        smsBundle.putString("to", uPhoneNumber);
        Intent otpIntent = new Intent(SignUpAct.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, otpIntent);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        //startActivity(itemPurchaseIntent);

    }
    public void sendSMSMessage22(String uPhoneNumber, String smsMessage) {
        Bundle smsBundle = new Bundle();
        smsBundle.putString(PROFILE_PHONE, uPhoneNumber);
        smsBundle.putString("USER_PHONE", uPhoneNumber);
        smsBundle.putString("smsMessage", smsMessage);
        //smsBundle.putString("from", "Awajima Coop.");
        smsBundle.putString("to", uPhoneNumber);
        Intent otpIntent = new Intent(SignUpAct.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setResult(RESULT_OK, otpIntent);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        //startActivity(itemPurchaseIntent);

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void showPrivacyPolicy(View view) {
        webView = findViewById(R.id.webViewT);
        coordinatorLayout = findViewById(R.id.bottomSheet_Coord);
        mBottomSheetBehavior = BottomSheetBehavior.from(coordinatorLayout);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl(AWAJIMA_PRIVACY_POLICIES);
        webView.setWebViewClient(new SWebViewClient());
        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webViewSettings.setPluginState(WebSettings.PluginState.ON);

    }
    private class SWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if(plink !=null){
                webView.loadUrl(AWAJIMA_PRIVACY_POLICIES);

                if (plink.equals(request.getUrl().getHost())) {
                    // This is my website, so do not override; let my WebView load the page
                    return false;
                }


            }

            Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
            startActivity(intent);
            return true;
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void goPP(View view) {
    }


   /* @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {PROFILE_ID,PROFILE_SURNAME,PROFILE_FIRSTNAME, PROFILE_EMAIL, PROFILE_DOB
                , PROFILE_ADDRESS, PROFILE_GENDER, PROFILE_PHONE,PROFILE_ROLE,PROFILE_DATE_JOINED,PROFILE_PASSWORD,PROFILE_COUNTRY,PICTURE_URI,PROFILE_USERNAME,PROFILE_NIN,PROFILE_STATE,PROF_ROLE_TYPE};
        return new CursorLoader(this, currentProfileUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }*/



    public interface OnPinEnteredListener {
        void onPinEntered(String pin);
    }



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
            @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationClient.getLastLocation();
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
                                    Log.d(TAG, "Current location is null. Using defaults.");
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
                .error(R.drawable.ic_notification_icon)
                //.listener(listener)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(profilePix);
    }

    protected void sendSMSMessage() {
        Bundle smsBundle = new Bundle();
        String smsMessage = uFirstName + "" + "Welcome to the Awajima  App, may you have the best experience";
        smsBundle.putString(PROFILE_PHONE, otpPhoneNumber);
        smsBundle.putString("USER_PHONE", otpPhoneNumber);
        smsBundle.putString("smsMessage", smsMessage);
        smsBundle.putString("from", "Awajima");
        smsBundle.putString("to", otpPhoneNumber);
        Intent itemPurchaseIntent = new Intent(SignUpAct.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
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
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);

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

    public void dobPicker(View view) {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH+1);
        newMonth = month;
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        if(webView.canGoBack())
        {
            webView.goBack();
        }

        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    protected void onStart() {
        super.onStart();
        geocoder = new Geocoder(this, Locale.getDefault());
        getDeviceLocation();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }

}