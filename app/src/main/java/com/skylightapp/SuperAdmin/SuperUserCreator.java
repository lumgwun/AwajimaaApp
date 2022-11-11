package com.skylightapp.SuperAdmin;

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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.widget.ContentLoadingProgressBar;

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
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.klinker.android.send_message.BroadcastUtils;
import com.skylightapp.BuildConfig;
import com.skylightapp.CameraActivity;
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
import com.skylightapp.Classes.ImportantDates;
import com.skylightapp.Classes.PinEntryView;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminUserDAO;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.Customer_TellerDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.MapAndLoc.MapPanoramaStVAct;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.NewLocAct;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
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

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Database.DBHelper.DATABASE_NEW_VERSION;
import static com.skylightapp.Database.DBHelper.DATABASE_VERSION;
import static com.skylightapp.Transactions.OurConfig.TWILIO_ACCOUNT_SID;
import static com.skylightapp.Transactions.OurConfig.TWILIO_AUTH_TOKEN;

public class SuperUserCreator extends AppCompatActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = SuperUserCreator.class.getSimpleName();
    public static final int PICTURE_REQUEST_CODE = 505;
    SharedPreferences userPreferences;
    AppCompatEditText phone_number;
    AppCompatEditText email_address,edtSponsorID;
    AppCompatEditText edtNIN;
    String stringNIN;
    AppCompatEditText firstName;
    AppCompatEditText surname1;
    AppCompatEditText userName;
    AppCompatEditText password;
    AppCompatEditText address_2;
    String formattedDaysRem;
    Random ran;
    SecureRandom random;
    String dateOfBirth;
    String uPhoneNumber;
    private ProgressDialog progressDialog;
    int superID,tellerID;

    String superSurname;
    String superFirstName, uPassword;
    String superPhoneNumber;
    String superEmailAddress;
    String superUserName;
    String  selectedGender, selectedOffice, selectedState;
    Birthday birthday;
    String userType;
    String userRole,sponsorIDString;
    int sponsorID;
    String address;
    LatLng userLocation;
    int daysInBetween;
    LatLng cusLatLng;
    double latitude = 0;
    double longitude = 0;
    Geocoder geocoder;
    ArrayList<Profile> profiles;
    ArrayList<Customer> customers;
    CircleImageView profilePix;
    AppCompatImageView imgGreetings;
    private CustomerManager customerManager2;
    ArrayList<CustomerManager> tellers;
    ArrayList<AdminUser> adminUserArrayList;
    ArrayList<UserSuperAdmin> superAdminArrayList;

    private Uri mImageUri;
    ContentLoadingProgressBar progressBar;

    List<Address> addresses;
    String otpMessage,smsMessage;
    private AppCompatButton btnVerifyOTPAndSignUp;
    private int otpDigit;
    String otpPhoneNumber;
    private LinearLayoutCompat layoutOTP,layoutPreOTP;

    private Button btnSendOTP;
    Location location;
    AppCompatTextView txtLoc;
    private LocationRequest request;
    Marker now;
    private CancellationTokenSource cancellationTokenSource;

    TextView otpTxt;
    String uFirstName, uEmail, uSurname, uAddress, uUserName;

    int virtualAccountNumber ;
    int customerID1 ;
    int profileID1 ;
    private boolean locationPermissionGranted;
    SQLiteDatabase sqLiteDatabase;
    Date date;

    AccountTypes accountTypeStr;
    private FusedLocationProviderClient fusedLocationClient;

    Location mCurrentLocation = null;
    AccountInvestment accountInvestment;
    AccountItemPurchase accountItemPurchase;
    AccountSavings accountSavings;
    AccountPromo accountPromo;
    GoogleMap googleMap;
    String joinedDate,customerName;
    String code;
    Profile userProfile1,userProfile2;
    private static final int REQUEST_CHECK_SETTINGS = 1000;

    AppCompatSpinner state, office, spnGender;
    DBHelper dbHelper;

    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");

    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
    String timeLineTime = mdformat.format(calendar.getTime());
    Profile superAdminProfile;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String REQUIRED = "Required";
    private static final int RESULT_CAMERA_CODE=22;
    String daysRemaining;
    int daysBTWN;

    String acct;
    Gson gson,gson1,gson2;
    String json,json1,json2,nIN;
    Profile userProfile;
    String pix;

    int profileID2;
    int comAcctID;
    int investmentAcctID,savingsAcctID,itemPurchaseAcctID,promoAcctID,packageAcctID;

    int  birthdayID,managerProfileID,marketID;
    private static boolean isPersistenceEnabled = false;
    Customer customer;
    Uri selectedImage;
    AppCompatTextView dobText;
    int selectedId,day, month, year,newMonth;
    protected DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    int messageID,soAccountNumber,customerID;
    String selectedUserType, selectedAdminType;
    private UserSuperAdmin userSuperAdmin;
    private CustomerManager teller;
    private StandingOrderAcct standingOrderAcct;
    private  AdminUser adminUser2;
    private  Spinner spnUserType;
    Profile newUserProfileL;
    private  UserSuperAdmin superAdmin;
    Bitmap thumbnail;
    private GoogleApiClient googleApiClient;
    private PinEntryView pinEntryView;
    private Spinner spnAdminType;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private AdminUser.ADMIN_TYPE admin_type;
    private long bizID;


    LinearLayoutCompat layoutAdminType;
    private MarketBusiness marketBusiness;
    private int marketBizID;
    private Account account;
    private Market market;

    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    int PERMISSION_ALL33 = 2;
    String[] PERMISSIONS33 = {
            Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,Manifest.permission.SEND_SMS
    };
    private static final String PREF_NAME = "awajima";
    ProfDAO profDAO= new ProfDAO(this);


    ActivityResultLauncher<Intent> mGetUserPixContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK :
                            if (result.getData() != null) {
                                //logo = findViewById(R.id.imageLogo);
                                Intent data = result.getData();
                                selectedImage = data.getData();
                                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                    dbHelper.openDataBase();
                                    sqLiteDatabase = dbHelper.getReadableDatabase();
                                    profDAO.insertProfilePicture(profileID2,customerID1,selectedImage);

                                }
                                /*if (selectedImage != null) {
                                    logo.setImageBitmap(getScaledBitmap(selectedImage));
                                } else {
                                    Toast.makeText(NewSignUpActivity.this, "Error getting Image",
                                            Toast.LENGTH_SHORT).show();
                                }*/

                            }

                            Toast.makeText(SuperUserCreator.this, "Image picking returned successful", Toast.LENGTH_SHORT).show();
                            //doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(SuperUserCreator.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            finish();
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
                            cusLatLng=intent.getParcelableExtra("CusLatLng");
                        }
                        // Handle the Intent
                    }
                }
            });

    ActivityResultLauncher<Intent> mRegUserLoc = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK :
                            if (result.getData() != null) {
                                Calendar calendar = Calendar.getInstance();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                String locDate = mdformat.format(calendar.getTime());
                                Intent data = result.getData();
                                Bundle bundle = data.getBundleExtra("userLocBundle");
                                cusLatLng = bundle.getParcelable("Location");
                                dbHelper.insertCustomerLocation(customerID1,cusLatLng);
                                //dbHelper.insertUserEmergencyReport(0,profileID2,locDate, selectedPurpose, cusLatLng);
                                Toast.makeText(SuperUserCreator.this, "Location registration successful", Toast.LENGTH_SHORT).show();

                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(SuperUserCreator.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_su_creator);
        checkInternetConnection();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (!hasPermissions(SuperUserCreator.this, PERMISSIONS33)) {
            ActivityCompat.requestPermissions(SuperUserCreator.this, PERMISSIONS33, PERMISSION_ALL33);
        }
        if (googleApiClient == null) {

            googleApiClient = new GoogleApiClient.Builder( this )
                    .addConnectionCallbacks( this )
                    .addOnConnectionFailedListener( this )
                    .addApi( LocationServices.API )
                    .build();
        }
        googleApiClient.connect();
        createLocationRequest();
        getDeviceLocation();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        cancellationTokenSource = new CancellationTokenSource();
        //dbHelper.onUpgrade(sqLiteDatabase,dbHelper.getDatabaseVersion(), DATABASE_NEW_VERSION);
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        imgGreetings = findViewById(R.id.GreetSuper);
        profilePix = findViewById(R.id.super_image);
        surname1 = findViewById(R.id.super_LastName);
        profilePix.setOnClickListener(this::doSelectPix);
        firstName = findViewById(R.id.super_FirstName);
        email_address = findViewById(R.id.super_email_);
        phone_number = findViewById(R.id.super_phone_);
        address_2 = findViewById(R.id.super_Address);
        userName = findViewById(R.id.superUserN);
        spnGender = findViewById(R.id.super_gender);
        password = findViewById(R.id.super_password_s);
        edtSponsorID = findViewById(R.id.super_sponsor);
        btnSendOTP = findViewById(R.id.sendOTPSuper);
        layoutOTP = findViewById(R.id.layoutSuperOtp);
        layoutPreOTP = findViewById(R.id.layoutPreO);
        otpTxt = findViewById(R.id.super_textOTP);
        layoutAdminType = findViewById(R.id.admin_layout_type);
        teller= new CustomerManager();
        standingOrderAcct= new StandingOrderAcct();
        account= new Account();
        market= new Market();

        pinEntryView = (PinEntryView) findViewById(R.id.super_pin_entry);
        btnVerifyOTPAndSignUp = findViewById(R.id.BtnVerifySuper);

        String email = email_address.getText().toString().trim();

        email_address .addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (email.matches(emailPattern) && s.length() > 0)
                {
                    Toast.makeText(SuperUserCreator.this,"valid email address",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(SuperUserCreator.this,"Invalid email address",Toast.LENGTH_SHORT).show();

                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });



        txtLoc = findViewById(R.id.whereSuperYou);

        layoutPreOTP = findViewById(R.id.layoutSign);
        edtNIN = findViewById(R.id.NIN_No);
        dobText = findViewById(R.id.dob_super);
        dobText.setOnClickListener(this::superDatePicker);
        random = new SecureRandom();

        dobText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                newMonth = month;
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog( SuperUserCreator.this, R.style.DatePickerDialogStyle,mDateSetListener,day,month,year);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //make transparent background.
                dialog.show();
                dateOfBirth = year+"/"+ newMonth+"/"+day;
                dobText.setText("Your date of Birth:"+dateOfBirth);

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                Log.d(TAG, "onDateSet: date of Birth: "+ day + "/" +month + "/" +year);
                //dateOfBirth = day+"/"+ month+"/"+year;
                dateOfBirth = year+"/"+ newMonth+"/"+day;
                dobText.setText("Your date of Birth:"+dateOfBirth);

            }


        };
        dateOfBirth = year+"/"+ newMonth+"/"+day;



        otpDigit = ThreadLocalRandom.current().nextInt(1022, 16300);
        messageID = ThreadLocalRandom.current().nextInt(1125, 10400);
        otpMessage = "&message=" + "Hello Awajima User, Your OTP Code is " +otpDigit;

        profileID1 = random.nextInt((int) (Math.random() * 1400) + 1115);
        profileID2 = random.nextInt((int) (Math.random() * 1400) + 1115);
        customerID1 = random.nextInt((int) (Math.random() * 1203) + 1101);
        virtualAccountNumber = random.nextInt((int) (Math.random() * 123045) + 100123);
        soAccountNumber = random.nextInt((int) (Math.random() * 133044) + 100125);
        customerID = random.nextInt((int) (Math.random() * 1203) + 1101);
        investmentAcctID =  random.nextInt((int) (Math.random() * 1011) + 1010);
        savingsAcctID = random.nextInt((int) (Math.random() * 1101) + 1010);
        itemPurchaseAcctID =  random.nextInt((int) (Math.random() * 1010) + 1010);
        promoAcctID = random.nextInt((int) (Math.random() * 1111) + 1010);
        packageAcctID = random.nextInt((int) (Math.random() * 1000) + 1010);



        accountInvestment= new AccountInvestment();
        accountItemPurchase= new AccountItemPurchase();
        accountPromo= new AccountPromo();
        accountSavings= new AccountSavings();
        tellers=null;
        adminUserArrayList=null;
        superAdminArrayList=null;


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
                        if(cusLatLng ==null){
                            mStartLocForResult.launch(new Intent(SuperUserCreator.this, NewLocAct.class));

                        }
                    }
                });

            }
        }).start();
        btnVerifyOTPAndSignUp = findViewById(R.id.BtnVerifySuper);
        progressBar = findViewById(R.id.progressBar);
        customer=new Customer();
        adminUser2=new AdminUser();
        superAdminProfile =new Profile();
        birthday= new Birthday();
        gson1 = new Gson();
        gson = new Gson();
        gson2= new Gson();
        newUserProfileL = new Profile();
        userSuperAdmin =new UserSuperAdmin();
        teller =new CustomerManager();
        superAdmin =new UserSuperAdmin();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        superAdminProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastSuperProfileUsed", "");
        userSuperAdmin = gson1.fromJson(json1, UserSuperAdmin.class);
        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        ran = new Random();
        if(marketBusiness !=null){
            bizID=marketBusiness.getBusinessID();
            marketBizID=marketBusiness.getBizMarketID();
            market=marketBusiness.getBizMarket();

        }
        if(market !=null){
            marketID=market.getMarketID();

        }

        if(superAdminProfile !=null){
            managerProfileID= superAdminProfile.getPID();

        }

        birthday= new Birthday();
        dbHelper = new DBHelper(this);
        profiles=new ArrayList<Profile>();
        customers=new ArrayList<Customer>();
        calendar = Calendar.getInstance();
        birthday= new Birthday();
        sqLiteDatabase = dbHelper.getWritableDatabase();
        if(dbHelper !=null){
            dbHelper.onUpgrade(sqLiteDatabase,DATABASE_VERSION,DATABASE_NEW_VERSION);

        }else{
            try {
                dbHelper.openDataBase();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
        spnUserType = findViewById(R.id.super_spinner_User);
        spnUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedUserType = spnUserType.getSelectedItem().toString();
                selectedUserType = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("Admin User")){
                layoutAdminType.setVisibility(View.VISIBLE);
                spnAdminType.setVisibility(View.VISIBLE);

            }else {
                layoutAdminType.setVisibility(View.GONE);

            }

        }

        spnAdminType = findViewById(R.id.admin_spinner_type);

        spnAdminType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedSelectedType = spnUserType.getSelectedItem().toString();
                selectedAdminType = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(selectedAdminType !=null){
            admin_type=AdminUser.ADMIN_TYPE.valueOf(selectedAdminType);
        }
        StringBuilder welcomeString = new StringBuilder();
        if(userSuperAdmin !=null){
            superSurname = userSuperAdmin.getSSurname();
            superFirstName = userSuperAdmin.getSFirstName();
            superID = userSuperAdmin.getSuperID();
            //profileID1=profileID;
            superPhoneNumber = userSuperAdmin.getsPhoneNumber();
            superEmailAddress = userSuperAdmin.getSEmailAddress();
            superUserName = userSuperAdmin.getSuperUserName();
            userType = userSuperAdmin.getSAdminRole();
            userRole = userSuperAdmin.getSAdminRole();

        }else {
            if(superAdminProfile !=null){
                superSurname = superAdminProfile.getProfileLastName();
                superFirstName = superAdminProfile.getProfileFirstName();
                superID = superAdminProfile.getPID();
                //profileID1=profileID;
                superPhoneNumber = superAdminProfile.getProfilePhoneNumber();
                superEmailAddress = superAdminProfile.getProfileEmail();
                superUserName = superAdminProfile.getProfileUserName();
                userType = superAdminProfile.getProfileMachine();
                userRole = superAdminProfile.getProfileRole();

            }

        }

        if(superAdminProfile !=null){
            superSurname = superAdminProfile.getProfileLastName();
            superFirstName = superAdminProfile.getProfileFirstName();
            superID = superAdminProfile.getPID();
            //profileID1=profileID;
            superPhoneNumber = superAdminProfile.getProfilePhoneNumber();
            superEmailAddress = superAdminProfile.getProfileEmail();
            superUserName = superAdminProfile.getProfileUserName();
            userType = superAdminProfile.getProfileMachine();
            userRole = superAdminProfile.getProfileRole();

        }

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


        welcomeString.append("Welcome"+"")
                .append(superFirstName +"")
                .append("How are you today? ")
                .append(getString(R.string.happy))
                .append(dow);


        profiles=null;

        accountTypeStr= null;

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
        profilePix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(SuperUserCreator.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(SuperUserCreator.this, PERMISSIONS, PERMISSION_ALL);
                }

                final PopupMenu popup = new PopupMenu(SuperUserCreator.this, profilePix);
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

        });



        office = findViewById(R.id.super_office);
        state = findViewById(R.id.super_state1);
        office.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOffice = office.getSelectedItem().toString();
                //selectedOffice = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //selectedState = state.getSelectedItem().toString();
                selectedState = (String) parent.getSelectedItem().toString();
                Toast.makeText(SuperUserCreator.this, "State: "+ selectedState,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //sign_up.setMovementMethod(LinkMovementMethod.getInstance());
        btnVerifyOTPAndSignUp.setOnClickListener(this::verifySuperOTP);
        CusDAO cusDAO = new CusDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            customers=cusDAO.getAllCustomers11();

        }

        btnSendOTP.setOnClickListener(this::sendOTPSuper);
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uFirstName = firstName.getText().toString().trim();
                uSurname = surname1.getText().toString().trim();
                uEmail = email_address.getText().toString();
                sponsorIDString = edtSponsorID.getText().toString().trim();
                uAddress = Objects.requireNonNull(address_2.getText()).toString();
                uPassword = password.getText().toString().trim();
                uPhoneNumber = Objects.requireNonNull(phone_number.getText()).toString();
                uUserName = userName.getText().toString();
                boolean usernameTaken = false;
                nIN=edtNIN.getText().toString().trim();
                customerName=uSurname+","+uFirstName;
                try {
                    sponsorID= Integer.parseInt(sponsorIDString);
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                try {

                    if (TextUtils.isEmpty(uFirstName)) {
                        firstName.setError("Please enter Your First Name");
                    } else if (TextUtils.isEmpty(nIN)) {
                        edtNIN.setError("Please enter your National ID No.");

                    } else if (TextUtils.isEmpty(uSurname)) {
                        surname1.setError("Please enter your Last/SurName");
                    }else if (TextUtils.isEmpty(uPassword)) {
                        password.setError("Please enter your Password");
                    }else if(uPhoneNumber.isEmpty() || uPhoneNumber.length() < 11){
                        phone_number.setError("Enter a valid mobile Number");
                        phone_number.requestFocus();
                    }else if (TextUtils.isEmpty(uUserName)) {
                        userName.setError("Please enter your userName");
                    } else if (TextUtils.isEmpty(uAddress)) {
                        address_2.setError("Please enter Address");
                    } else {

                        layoutOTP.setVisibility(View.VISIBLE);
                        layoutPreOTP.setVisibility(View.GONE);
                        otpTxt.setText("Your OTP is:"+otpDigit);
                        otpPhoneNumber = "+234" + uPhoneNumber;

                        for (int i = 0; i < customers.size(); i++) {
                            try {
                                if (customers.get(i).getCusPhoneNumber().equalsIgnoreCase(uPhoneNumber)) {
                                    Toast.makeText(SuperUserCreator.this, "This Phone Number is already in use, here" , Toast.LENGTH_LONG).show();
                                    return;

                                }else {
                                    layoutOTP.setVisibility(View.VISIBLE);
                                    layoutPreOTP.setVisibility(View.GONE);
                                    otpPhoneNumber = "+234" + uPhoneNumber;
                                    doOtpNotification();
                                    sendOTPMessage(otpPhoneNumber,otpMessage);
                                    MessageDAO messageDAO= new MessageDAO(SuperUserCreator.this);

                                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                        dbHelper.openDataBase();
                                        sqLiteDatabase = dbHelper.getReadableDatabase();
                                        messageDAO.insertMessage(profileID1,customerID,messageID,bizID,otpMessage,"Awajima App",customerName,selectedOffice,joinedDate);
                                    }

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
                boolean usernameTaken = false;
                accountTypeStr = AccountTypes.EWALLET;
                //long accountNumber = Long.parseLong(String.valueOf((long) (Math.random() * 10501) + 10010));

                otpPhoneNumber = "+234" + uPhoneNumber;
                code = pinEntryView.getText().toString().trim();

                if (code.equals(String.valueOf(otpDigit))) {
                    Toast.makeText(SuperUserCreator.this, "OTP verification, a Success", Toast.LENGTH_SHORT).show();
                    startNewProfileActivity(sponsorID,cusLatLng,account,standingOrderAcct,joinedDate,uFirstName,uSurname,uPhoneNumber,uAddress,uUserName,uPassword,customer,newUserProfileL,nIN, superAdminProfile,dateOfBirth,selectedGender,selectedOffice,selectedState,birthday,teller,dateOfBirth,profileID1,virtualAccountNumber,soAccountNumber, customerID,birthdayID, investmentAcctID,itemPurchaseAcctID,promoAcctID,packageAcctID,customers,selectedUserType,admin_type,bizID);

                } else {
                    Toast.makeText(SuperUserCreator.this, "FAIL", Toast.LENGTH_SHORT).show();
                    pinEntryView.setText("Wrong OTP...");
                    pinEntryView.requestFocus();
                }
                progressBar.setVisibility(View.VISIBLE);




            }
        });


    }


    public void sendOTPMessage(String otpPhoneNumber, String otpMessage) {
        Bundle smsBundle= new Bundle();
        smsBundle.putString(PROFILE_PHONE, otpPhoneNumber);
        smsBundle.putString("USER_PHONE", otpPhoneNumber);
        smsBundle.putString("smsMessage", otpMessage);
        smsBundle.putString("from","Awajima");
        smsBundle.putString("to", otpPhoneNumber);
        Intent otpIntent = new Intent(SuperUserCreator.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);//you can cancel it by pressing back button
        progressDialog.setMessage("singing up wait ...");
        progressBar.show();//displays the progress bar
    }

    public void sendTextMessage(String uPhoneNumber, String smsMessage) {
        Bundle smsBundle= new Bundle();
        smsBundle.putString(PROFILE_PHONE, uPhoneNumber);
        smsBundle.putString("USER_PHONE", uPhoneNumber);
        smsBundle.putString("smsMessage", smsMessage);
        smsBundle.putString("from","Awajima Coop.");
        smsBundle.putString("to", uPhoneNumber);
        Intent otpIntent = new Intent(SuperUserCreator.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }
    public interface OnPinEnteredListener {
        void onPinEntered(String pin);
    }
    private void doOtpNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Awajima App OTP Message")
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
                                    SuperUserCreator.this,
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


    private void setInitialLocation() {
        txtLoc = findViewById(R.id.whereYou);

        if (ActivityCompat.checkSelfPermission( SuperUserCreator.this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( SuperUserCreator.this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        /*if (!hasPermissions(SignUpAct.this, PERMISSIONS33)) {
            ActivityCompat.requestPermissions(SignUpAct.this, PERMISSIONS33, PERMISSION_ALL33);
        }*/
        LocationServices.FusedLocationApi.requestLocationUpdates( googleApiClient, request, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                double lat=location.getLatitude();
                double lng=location.getLongitude();

                SuperUserCreator.this.latitude=lat;
                SuperUserCreator.this.longitude=lng;

                try {
                    if(now !=null){
                        now.remove();
                    }

                    userLocation = new LatLng( SuperUserCreator.this.latitude,SuperUserCreator.this.longitude );
                    cusLatLng=userLocation;


                } catch (Exception ex) {

                    ex.printStackTrace();
                    Log.e( "MapException", ex.getMessage() );

                }

                try {
                    geocoder = new Geocoder(SuperUserCreator.this, Locale.ENGLISH);
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    StringBuilder str = new StringBuilder();
                    if (Geocoder.isPresent()) {
                        //txtLoc.setVisibility(View.VISIBLE);

                        android.location.Address returnAddress = addresses.get(0);

                        String localityString = returnAddress.getAddressLine (0);

                        str.append( localityString ).append( "" );

                        /*txtLoc.setText(MessageFormat.format("Your are here:  {0},{1}/{2}", latitude, longitude , str));
                        Toast.makeText(SuperUserCreator.this, str,
                                Toast.LENGTH_SHORT).show();*/

                    } else {
                        //go
                    }


                } catch (IOException e) {

                    Log.e("tag", e.getMessage());
                }



            }

        } );
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        geocoder = new Geocoder(this, Locale.getDefault());
        otpTxt = findViewById(R.id.textViewOTP);
        createLocationRequest();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

    }
    protected void sendSMSMessage() {
        String welcomeMessage="Welcome to Awajima, your best Business Community from Africa";
        phone_number = findViewById(R.id.super_phone_);
        uPhoneNumber = Objects.requireNonNull(phone_number.getText()).toString();
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(uPhoneNumber),
                new com.twilio.type.PhoneNumber("234"+uPhoneNumber),
                welcomeMessage)
                .create();
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
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(location.getLatitude(),
                                                location.getLongitude()), 17));
                            }
                        } else {

                            cusLatLng = new LatLng(4.52871, 7.44507);
                            Log.d(TAG, "Current location is null. Using defaults.");
                            googleMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(cusLatLng, 17));
                            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });

                fusedLocationClient.getCurrentLocation(2,cancellationTokenSource.getToken())
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location2) {
                                if (location2 != null) {
                                    latitude = location2.getLatitude();
                                    longitude = location2.getLongitude();
                                    cusLatLng = new LatLng(latitude, longitude);


                                    setResult(Activity.RESULT_OK, new Intent());


                                }
                                else {
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
                        String city = addresses.get(0).getSubLocality();


                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                txtLoc.setText("My Loc:"+latitude+","+longitude+"/"+address);


            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
    private void startNewProfileActivity(int sponsorID, LatLng cusLatLng, Account account, StandingOrderAcct standingOrderAcct, String joinedDate, String uFirstName, String uSurname, String uPhoneNumber, String uAddress, String uUserName, String uPassword, Customer customer, Profile newUserProfileL, String nIN, Profile superAdminProfile, String dateOfBirth, String selectedGender, String selectedOffice, String selectedState, Birthday birthday, CustomerManager customerManager, String dateOfBirth1, int profileID1, int virtualAccountNumber, int soAccountNumber, int customerID, int birthdayID, int investmentAcctID, int itemPurchaseAcctID, int promoAcctID, int packageAcctID, ArrayList<Customer> customers, String selectedUserType, AdminUser.ADMIN_TYPE admin_type, long bizID) {
        Toast.makeText(SuperUserCreator.this, "Gender: "+ selectedGender,Toast.LENGTH_SHORT).show();

        Toast.makeText(SuperUserCreator.this, "Office Branch Selected: "+ selectedOffice,Toast.LENGTH_SHORT).show();
        Toast.makeText(SuperUserCreator.this, "State: "+ selectedState,Toast.LENGTH_SHORT).show();
        showProgressDialog();
        ran = new Random();
        dbHelper = new DBHelper(this);
        random = new SecureRandom();
        //user= new User(profileID1,"Ezekiel", "Gwun-orene", "07038843102", "lumgwun1@gmail.com", "25/04/1983", "male", "Ilabuchi", "","Rivers", "Elelenwo", "19/04/2022","SuperAdmin", "Lumgwun", "@Awajima1","Confirmed","");
        userProfile1= new Profile(profileID1,"Emma", "Uja", "08069524599", "urskylight@gmail.com", "25/04/1983", "female", "Awajima", "","Rivers", "Elelenwo", "19/04/2022","SuperAdmin", "Skylight4ever", "@Awajima2","Confirmed","");
        //user=new User(profileID1, "Ezekiel", "Gwun-orene", "07038843102", "lumgwun1@gmail.com", "25/04/1983", "male", "Ilabuchi", "", "Rivers", "Elelenwo", "19/04/2022", "SuperAdmin", "Lumgwun", "@Awajima1", "Confirmed", "");


        try {
            dbHelper.openDataBase();
            dbHelper.insertCustomer11(savingsAcctID, savingsAcctID, "Ezekiel", "Gwun-orene", "07038843102", "lumgwun1@gmail.com", "25/04/1983", "male", "Ilabuchi", "Rivers", "", "19/04/2022","Lumgwun", "@Awajima1", Uri.parse(""), "Customer");

        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        BirthdayDAO birthdayDAO = new BirthdayDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            try {
                birthdayDAO.insertBirthDay3(birthday, "25/04/1983");

            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        MessageDAO messageDAO = new MessageDAO(this);
        TimeLineClassDAO timeLineClassDAO1 = new TimeLineClassDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            messageDAO.insertMessage(profileID1,customerID,messageID, bizID, otpMessage,"Awajima App",customerName,selectedOffice,joinedDate);
        }
        try {
            timeLineClassDAO1.insertTimeLine("Sign up", "", "23/09/2022", mCurrentLocation);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            dbHelper.saveNewProfile(userProfile);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }




        ArrayList<Profile> profiles = new ArrayList<>();
        ArrayList<CustomerManager> tellers = new ArrayList<>();
        ArrayList<AdminUser> adminUserArrayList = new ArrayList<>();
        ArrayList<UserSuperAdmin> superAdminArrayList = new ArrayList<>();
        tellerID = random.nextInt((int) (Math.random() * 10319) + 1113);
        virtualAccountNumber = random.nextInt((int) (Math.random() * 123046) + 100122);
        customerID1 = random.nextInt((int) (Math.random() * 1201) + 1102);
        profileID1 = random.nextInt((int) (Math.random() * 1200) + 1113);
        profileID2 = random.nextInt((int) (Math.random() * 1302) + 8113);
        comAcctID =  random.nextInt((int) (Math.random() * 13013) + 1012);
        tellerID = random.nextInt((int) (Math.random() * 35021) + 12122);
        accountTypeStr = null;
        dbHelper = new DBHelper(this);
        ProfDAO profDAO1 =new ProfDAO(this);
        CusDAO cusDAO =new CusDAO(this);
        Customer_TellerDAO customer_tellerDAO =new Customer_TellerDAO(this);
        AdminUserDAO adminUserDAO =new AdminUserDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            profiles = profDAO1.getAllProfiles();
        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            customers = cusDAO.getAllCustomers();        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            tellers = customer_tellerDAO.getAllCustomersManagers();        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            adminUserArrayList = adminUserDAO.getAllAdmin();        }




        if(userSuperAdmin !=null){
            superSurname = userSuperAdmin.getSSurname();
            superFirstName = userSuperAdmin.getSFirstName();
            superID = userSuperAdmin.getSuperID();
            //profileID1=profileID;
            superPhoneNumber = userSuperAdmin.getsPhoneNumber();
            superEmailAddress = userSuperAdmin.getSEmailAddress();
            superUserName = userSuperAdmin.getSuperUserName();
            userType = userSuperAdmin.getSAdminRole();
            userRole = userSuperAdmin.getSAdminRole();

        }else {
            if(superAdminProfile !=null){
                superSurname = superAdminProfile.getProfileLastName();
                superFirstName = superAdminProfile.getProfileFirstName();
                superID = superAdminProfile.getPID();
                //profileID1=profileID;
                superPhoneNumber = superAdminProfile.getProfilePhoneNumber();
                superEmailAddress = superAdminProfile.getProfileEmail();
                superUserName = superAdminProfile.getProfileUserName();
                userType = superAdminProfile.getProfileMachine();
                userRole = superAdminProfile.getProfileRole();
                String managerFullNamesT = superSurname + "," + superFirstName;
                String namesT = uSurname +","+ uFirstName;

                String timelineDetailsTD = uSurname + "," + uFirstName + "was added as a Customer" + "by" + managerFullNamesT + "@" + timeLineTime;
                String tittleT1 = "Customer Sign Up Alert!";
                String timelineDetailsT11 = "You added" + uSurname + "," + uFirstName + "as a Customer" + "on" + timeLineTime;

                superAdminProfile.addPTimeLine(tittleT1, timelineDetailsT11);
                superAdminProfile.addPCustomer(customerID, uSurname, uFirstName, uPhoneNumber, uEmail, uAddress, selectedGender, selectedOffice, selectedState, mImageUri, joinedDate, uUserName, uPassword);


            }

        }
        if(superAdminProfile !=null){
            superSurname = superAdminProfile.getProfileLastName();
            superFirstName = superAdminProfile.getProfileFirstName();
            superID = superAdminProfile.getPID();
            //profileID1=profileID;
            superPhoneNumber = superAdminProfile.getProfilePhoneNumber();
            superEmailAddress = superAdminProfile.getProfileEmail();
            superUserName = superAdminProfile.getProfileUserName();
            userType = superAdminProfile.getProfileMachine();
            userRole = superAdminProfile.getProfileRole();

        }



        Date date = new Date();
        uFirstName = firstName.getText().toString();
        uEmail = email_address.getText().toString();
        boolean usernameTaken = false;
        try {
            uSurname = surname1.getText().toString().trim();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            stringNIN = edtNIN.getText().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            uFirstName = Objects.requireNonNull(firstName.getText()).toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            uEmail = email_address.getText().toString();
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        try {
            uPhoneNumber = Objects.requireNonNull(phone_number.getText()).toString();
        } catch (Exception e) {
            System.out.println("Oops!");
            phone_number.requestFocus();
        }
        try {
            uAddress = Objects.requireNonNull(address_2.getText()).toString();
        } catch (Exception e) {
            System.out.println("Oops!");
            address_2.requestFocus();
        }
        try {
            uUserName = Objects.requireNonNull(userName.getText()).toString();
        } catch (Exception e) {
            System.out.println("Oops!");
            userName.requestFocus();
        }
        try {
            uPassword = Objects.requireNonNull(password.getText()).toString();
        } catch (Exception e) {
            System.out.println("Oops!");
            password.requestFocus();
        }
        ran = new Random();
        calendar = Calendar.getInstance();


        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        timeLineTime = mdformat.format(calendar.getTime());
        profiles = profDAO1.getAllProfiles();
        //customers = dbHelper.getAllCustomers();
        smsMessage="Welcome to the Awajima  App, may you have the best experience";
        String names = uSurname + uFirstName;
        //long customerId = customer.getId();
        //Customer c = new Customer();
        String name = uSurname + "," + uFirstName;

        random = new SecureRandom();
        gson = new Gson();
        accountTypeStr = AccountTypes.EWALLET;

        String timelineDetailsTD = uSurname + "," + uFirstName + "was added as a User" + "by" + "Awajima App" + "@" + timeLineTime;
        String tittleT1 = "NewUser Sign Up Alert!";
        String timelineDetailsT11 = "You added" + uSurname + "," + uFirstName + "as a User" + "on" + timeLineTime;

        String accountName = uSurname + "," + uFirstName;


        //birthdayID = random.nextInt((int) (Math.random() * 1001) + 1010);
        String skylightMFb = "E-Wallet";
        double accountBalance = 0.00;
        String interestRate = "0.0";
        int finalProfileID = profileID1;
        superAdminProfile=new Profile();
        account = new Account("EWallet Account", accountName, virtualAccountNumber, accountBalance, accountTypeStr);
        standingOrderAcct = new StandingOrderAcct(virtualAccountNumber+12, accountName, 0.00);
        customer = new Customer(customerID, uSurname, uFirstName, uPhoneNumber, uEmail, nIN, dateOfBirth, selectedGender, uAddress, uUserName, uPassword, selectedOffice, joinedDate);
        birthday = new Birthday(birthdayID, profileID1, uSurname+","+uFirstName, uPhoneNumber, uEmail, selectedGender, uAddress, dateOfBirth,0,"","Not celebrated");
        newUserProfileL= new Profile(profileID1, uSurname, uFirstName, uPhoneNumber, uEmail, dateOfBirth, selectedGender, uAddress, "",selectedState, selectedOffice, joinedDate,selectedUserType, uUserName, uPassword,"New","");

        userProfile2= new Profile(profileID2,"Benedict", "Benedict", "08059250176", "bener@gmail.com", "25/04/1989", "male", "PH", "","Rivers", "Elelenwo", "19/04/2022","Customer", "Lumgwun", "@Awajima3","Confirmed","");
        Calendar calendar = Calendar.getInstance();
        //String joinedDate = mdformat.format(calendar.getTime());

        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = dateFormatWithZone.format(date);
        birthday = new Birthday(profileID2, customerID1, names, uPhoneNumber, uEmail, selectedGender, uAddress, dateOfBirth, 0, formattedDaysRem, "");

        BroadcastUtils.sendExplicitBroadcast(this, new Intent(), "Awajima Account Sign up action");



        ImportantDates importantDates2 = new ImportantDates();

        importantDates2 = new ImportantDates(names, dateOfBirth, "", "", "", "");
        if(birthday !=null){
            try {
                formattedDaysRem=birthday.getFormattedDaysRemainingString(joinedDate,dateOfBirth);
                daysInBetween=birthday.getDaysInBetween(joinedDate,dateOfBirth);
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }

        }
        newUserProfileL = new Profile(uSurname, uFirstName, uPhoneNumber, uEmail, dateOfBirth, selectedGender, uAddress,
                selectedState, selectedOffice, currentDate, uUserName, selectedUserType, "Pending Approval", selectedImage,admin_type);
        //newUserProfileL.addAccount("Our Cooperative", names, virtualAccountNumber, 0.00, accountTypeStr);
        customer = new Customer(customerID1, bizID, uSurname, uFirstName, uPhoneNumber, uEmail, nIN, dateOfBirth, selectedGender, uAddress, userName.getText().toString(), password.getText().toString(), selectedOffice, joinedDate);

        /*for (int i = 0; i < tellers.size(); i++) {
            try {
                if (tellers.get(i).getPhoneNumber().equalsIgnoreCase(uPhoneNumber)&& tellers.get(i).getUFirstName().equalsIgnoreCase(uFirstName)||tellers.get(i).getUSurname().equalsIgnoreCase(uFirstName)||tellers.get(i).getUSurname().equalsIgnoreCase(uSurname)) {
                    Toast.makeText(SuperUserCreator.this, "Those details are already in use, here" , Toast.LENGTH_LONG).show();
                    return;

                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }


        }


        for (int i = 0; i < customers.size(); i++) {
            try {
                if (customers.get(i).getCusPhoneNumber().equalsIgnoreCase(uPhoneNumber)&& customers.get(i).getCusFirstName().equalsIgnoreCase(uFirstName)||customers.get(i).getCusSurname().equalsIgnoreCase(uFirstName)||customers.get(i).getCusSurname().equalsIgnoreCase(uSurname)) {
                    Toast.makeText(SuperUserCreator.this, "Those details are already in use, here" , Toast.LENGTH_LONG).show();
                    return;

                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }


        }


        for (int i = 0; i < adminUserArrayList.size(); i++) {
            try {
                if (adminUserArrayList.get(i).getPhoneNo().equalsIgnoreCase(uPhoneNumber)&& adminUserArrayList.get(i).getUFirstName().equalsIgnoreCase(uFirstName)||adminUserArrayList.get(i).getUSurname().equalsIgnoreCase(uFirstName)||adminUserArrayList.get(i).getUSurname().equalsIgnoreCase(uSurname)) {
                    Toast.makeText(SuperUserCreator.this, "Those details are already in use, here" , Toast.LENGTH_LONG).show();
                    return;

                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }


        }*/
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("Teller")){

            }

        }
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("Customer")){


            }

        }
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("Admin User")){

            }

        }
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("Accountant")){

            }

        }
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("REGULATOR")){

            }

        }
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("RECORD_KEEPER")){

            }

        }
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("PARTNER")){

            }

        }
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("WORKER")){

            }

        }
        if(selectedUserType !=null){
            if(selectedUserType.equalsIgnoreCase("INVESTOR")){

            }

        }


        WorkersDAO workersDAO= new WorkersDAO(this);

        for (int i = 0; i < profiles.size(); i++) {

            try {
                if (profiles.get(i).getProfilePhoneNumber().equalsIgnoreCase(uPhoneNumber) && profiles.get(i).getProfileFirstName().equalsIgnoreCase(uFirstName) || profiles.get(i).getProfileLastName().equalsIgnoreCase(uSurname)) {
                    Toast.makeText(SuperUserCreator.this, "Those details are already in use, here", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    customer.setCusAccount(account);
                    customer.setCusStandingOrderAcct(standingOrderAcct);
                    customer.addCusAccount1(virtualAccountNumber,accountName,  accountBalance, accountTypeStr);

                    customer.setCusFirstName(uFirstName);
                    customer.setCusSurname(uSurname);
                    customer.setCusAddress(uAddress);
                    customer.setCusDob(dateOfBirth);
                    customer.setCusUID(customerID);
                    customer.setCusDateJoined(joinedDate);
                    customer.setCusEmail(uEmail);
                    customer.setCusRole(selectedUserType);
                    customer.setCusPhoneNumber(uPhoneNumber);
                    customer.setCusGender(selectedGender);
                    customer.setCustomerLocation(this.cusLatLng);
                    customer.setCusUserName(uUserName);
                    customer.setCusState(selectedState);
                    customer.setCusSponsorID(sponsorID);
                    customer.setCusOffice(selectedOffice);
                    customer.setCusAccount(account);
                    customer.setCusStandingOrderAcct(standingOrderAcct);

                    newUserProfileL.setProfileFirstName(uFirstName);
                    newUserProfileL.setProfileLastName(uSurname);
                    newUserProfileL.setProfileAddress(uAddress);
                    newUserProfileL.setProfileDob("NGN");
                    newUserProfileL.setProfileDateJoined(joinedDate);
                    newUserProfileL.setProfileEmail(uEmail);
                    newUserProfileL.setProfileRole(selectedUserType);
                    newUserProfileL.setProfilePhoneNumber(uPhoneNumber);
                    newUserProfileL.setProfileGender(selectedGender);
                    newUserProfileL.setProfileIdentity(null);
                    newUserProfileL.setProfileLastKnownLocation(this.cusLatLng);
                    newUserProfileL.setProfileMachine(selectedUserType);
                    newUserProfileL.setProfileUserName(uUserName);
                    newUserProfileL.setProfileState(selectedState);
                    newUserProfileL.setProfileSponsorID(profileID1);
                    newUserProfileL.setProfOfficeName(selectedOffice);
                    newUserProfileL.setProfileBusinessID(Math.toIntExact(bizID));
                    newUserProfileL.setProfile_CustomerManager(customerManager);

                    customer.addCusAccountManager(managerProfileID, superSurname, superFirstName, "", selectedOffice);
                    dbHelper.openDataBase();

                    if(selectedUserType !=null){
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            dbHelper.saveNewProfile(newUserProfileL);


                        }

                    }



                    /*if (cusLatLng != null) {
                        try {
                            dbHelper.openDataBase();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }*/
                    SODAO sodao = new SODAO(this);
                    AcctDAO acctDAO = new AcctDAO(this);
                    TimeLineClassDAO timeLineClassDAO = new TimeLineClassDAO(this);
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        sodao.insertStandingOrderAcct(profileID2, customerID1, soAccountNumber, accountName, 0.00);

                    }

                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        dbHelper.addReminder(importantDates2);


                    }


                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        birthdayDAO.insertBirthDay3(birthday, dateOfBirth);


                    }



                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        if (cusLatLng != null) {
                            try {
                                dbHelper.openDataBase();
                                dbHelper.insertCustomerLocation(customerID, this.cusLatLng);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    try {

                        dbHelper.openDataBase();
                        sodao.insertStandingOrderAcct(profileID1, customerID, virtualAccountNumber, accountName, 0.00);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        acctDAO.insertAccount(profileID1, customerID, skylightMFb, accountName, virtualAccountNumber, accountBalance, accountTypeStr);

                    }


                    if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                        dbHelper.openDataBase();
                        dbHelper.saveNewProfile(newUserProfileL);


                    }
                    AdminUserDAO adminUserDAO1 = new AdminUserDAO(this);


                    if (selectedUserType != null) {
                        if (selectedUserType.equalsIgnoreCase("Customer")) {
                            String timelineDetailCus = uSurname + "," + uFirstName + "was added as a Customer" + "by" + "Awajima Alpha" + "@" + timeLineTime;
                            String tittleTCus = "Customer Sign Up Alert!";
                            String timelineDetailsTMyCus = "You added" + uSurname + "," + uFirstName + "as a Customer" + "on" + timeLineTime;

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                timeLineClassDAO.insertTimeLine(tittleTCus, timelineDetailCus, timeLineTime, mCurrentLocation);



                            }


                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertRole(profileID1, "Customer",  uPhoneNumber);

                            }



                        }
                        if (selectedUserType.equalsIgnoreCase("Teller")) {
                            String managerFullNamesT = superSurname + "," + superFirstName;
                            String namesT = uSurname + "," + uFirstName;

                            String tittleT = "Teller Sign Up Alert!";

                            String managerFullNames = superSurname + "," + superFirstName;
                            String timelineDetailsT = uSurname + "," + uFirstName + "was added as a Teller" + "by" + managerFullNames + "@" + timeLineTime;
                            String tittleTeller = "Teller Sign Up Alert!";
                            String timelineMySelf = "You added" + uSurname + "," + uFirstName + "as a Teller" + "on" + timeLineTime;
                            teller = new CustomerManager(profileID2, uSurname, uFirstName, uPhoneNumber, dateOfBirth, uEmail, uAddress, selectedGender, selectedOffice, selectedState, selectedImage, joinedDate, uUserName, uPassword);

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                workersDAO.saveNewWorker(profileID1, namesT);
                            }


                            customerManager2 = new CustomerManager(profileID1, namesT);
                            newUserProfileL.setProfile_CustomerManager(customerManager2);
                            superAdminProfile.addPTimeLine(tittleTeller, timelineMySelf);

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertTellerReport(profileID2, uSurname, uFirstName, uPhoneNumber, dateOfBirth, uEmail, uAddress, selectedGender, selectedOffice, selectedState, selectedImage, joinedDate, nIN, uUserName, uPassword);

                            }

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                timeLineClassDAO.insertTimeLine(tittleTeller, timelineDetailsT, timeLineTime, mCurrentLocation);

                            }

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertRole(profileID1, "Teller",  uPhoneNumber);

                            }

                        }
                        if (selectedUserType.equalsIgnoreCase("Admin User")) {
                            String managerFullNamesT = superSurname + "," + superFirstName;
                            String namesT = uSurname + "," + uFirstName;

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                workersDAO.saveNewWorker(profileID1, namesT);
                            }


                            adminUser2 = new AdminUser(profileID1, namesT);
                            newUserProfileL.setProfile_AdminUser(adminUser2);

                            String superAdminNames = superSurname + "," + superFirstName;
                            String timelineDAd = uSurname + "," + uFirstName + "was added as an Admin User" + "by" + superAdminNames + "@" + timeLineTime;
                            String tittleAdmin = "Admin User Sign Up Alert!";
                            String timelineDetMe = "You added" + uSurname + "," + uFirstName + "as an Admin User" + "on" + timeLineTime;
                            superAdminProfile.addPTimeLine(tittleAdmin, timelineDetMe);
                            adminUser2 = new AdminUser(profileID2, uSurname, uFirstName, uPhoneNumber, dateOfBirth, uEmail, uAddress, selectedGender, selectedOffice, selectedState, selectedImage, joinedDate, uUserName, uPassword);


                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                timeLineClassDAO.insertTimeLine(tittleAdmin, timelineDAd, timeLineTime, mCurrentLocation);

                            }

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                workersDAO.saveNewWorker(profileID1, namesT);
                            }

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                adminUserDAO1.insertAdminUser(profileID2, uSurname, uFirstName, uPhoneNumber, uEmail, dateOfBirth, selectedGender, uAddress, selectedOffice, joinedDate, uUserName, uPassword, nIN, selectedState, selectedImage, "new");

                            }

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertRole(profileID1, "AdminUser",  uPhoneNumber);

                            }


                        }
                        if (selectedUserType.equalsIgnoreCase("Accountant")) {
                            String timelineDAd = uSurname + "," + uFirstName + "was added as an Accountant" + "by" + "Awajima Alpha" + "@" + timeLineTime;
                            String tittleAcct = "Account Sign Up Alert!";
                            String superAcctantNames = superSurname + "," + superFirstName;

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                workersDAO.saveNewWorker(profileID1, superAcctantNames);
                            }




                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertRole(profileID1, "Accountant",  uPhoneNumber);

                            }


                            String timelineDetAcct = "You added" + uSurname + "," + uFirstName + "as an Admin User" + "on" + timeLineTime;
                            superAdminProfile.addPTimeLine(tittleAcct, timelineDetAcct);
                        }

                        if (selectedUserType.equalsIgnoreCase("Super Admin")) {
                            String managerFullNamesT = superSurname + "," + superFirstName;
                            String namesT = uSurname + "," + uFirstName;

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                workersDAO.saveNewWorker(profileID1, namesT);
                            }

                            String tittleT = "Super Admin Sign Up Alert!";
                            superAdmin = new UserSuperAdmin(namesT);
                            newUserProfileL.setProfile_SuperAdmin(superAdmin);

                            String managerFullNames = superSurname + "," + superFirstName;
                            String timelineDetailSuper = uSurname + "," + uFirstName + "was added as a Super Admin" + "by" + managerFullNames + "@" + timeLineTime;
                            String tittleSuper = "Super Admin Sign Up Alert!";
                            String timelineDetailsTMe = "You added" + uSurname + "," + uFirstName + "as a Super Admin" + "on" + timeLineTime;
                            if (superAdmin != null) {
                                superAdmin.addSTimeLine(tittleT1, timelineDetailsT11);
                            }

                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertSuperAdmin(profileID2, uSurname, uFirstName, uPhoneNumber, uEmail, dateOfBirth, selectedGender, uAddress, selectedOffice, uUserName, uPassword, selectedImage,marketBizID);

                            }


                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertRole(profileID1, "SuperAdmin", uPhoneNumber);

                            }


                            superAdminProfile.addPTimeLine(tittleSuper, timelineDetailsTMe);

                        }


                        if (selectedUserType.equalsIgnoreCase("Customer")) {
                            String managerFullNamesT = superSurname + "," + superFirstName;
                            String namesT = uSurname + "," + uFirstName;
                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertCustomer11(profileID1, customerID, bizID, marketID, uSurname, uFirstName, uPhoneNumber, uEmail, dateOfBirth, selectedGender, uAddress, selectedState, "", joinedDate, uUserName, uPassword, mImageUri, "Customer");

                            }



                            String tittleT = "Support Manager Sign Up Alert!";

                            String managerFullNames = superSurname + "," + superFirstName;
                            String timelineDetailsSuport = uSurname + "," + uFirstName + "was added as a Support Manager" + "by" + managerFullNames + "@" + timeLineTime;
                            String tittleTSupport = "Support Manager Sign Up Alert!";
                            String timelineDetailsTme = "You added" + uSurname + "," + uFirstName + "as a Support Manager" + "on" + timeLineTime;


                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertRole(profileID1, "Customer", uPhoneNumber);

                            }


                            if (superAdminProfile != null) {
                                superAdminProfile.addPTimeLine(tittleTSupport, timelineDetailsTme);
                            }


                        }
                        if (selectedUserType.equalsIgnoreCase("Support Manager")) {
                            String managerFullNamesT = superSurname + "," + superFirstName;
                            String namesT = uSurname + "," + uFirstName;
                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                workersDAO.saveNewWorker(profileID1, namesT);
                            }



                            String tittleT = "Support Manager Sign Up Alert!";

                            String managerFullNames = superSurname + "," + superFirstName;
                            String timelineDetailsSuport = uSurname + "," + uFirstName + "was added as a Support Manager" + "by" + managerFullNames + "@" + timeLineTime;
                            String tittleTSupport = "Support Manager Sign Up Alert!";
                            String timelineDetailsTme = "You added" + uSurname + "," + uFirstName + "as a Support Manager" + "on" + timeLineTime;


                            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                                dbHelper.openDataBase();
                                dbHelper.insertRole(profileID1, "SupportManager", uPhoneNumber);

                            }


                            if (superAdminProfile != null) {
                                superAdminProfile.addPTimeLine(tittleTSupport, timelineDetailsTme);
                            }


                        }


                    }Toast.makeText(SuperUserCreator.this, "Thank you" +
                            "for Signing up " + uFirstName + "on the Awajima App", Toast.LENGTH_LONG).show();
                    setResult(Activity.RESULT_OK, new Intent());
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


        }


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

                    if (ActivityCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION )
                            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( this,
                            Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {

                        //setupLocationManager();

                    }
                } else {

                    Toast.makeText( SuperUserCreator.this, "Permission Denied", Toast.LENGTH_SHORT ).show();
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
            case 1000:{
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
                    SuperUserCreator.this.cusLatLng=userLocation;

                    geocoder = new Geocoder(this, Locale.getDefault());

                    if (location != null) {
                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {

                            address = addresses.get(0).getAddressLine(0);
                            String city = addresses.get(0).getSubLocality();


                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }

                    txtLoc.setText(MessageFormat.format("Your Address{0},{1}/{2}", latitude, longitude, address));



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


        //updateLocationUI();

        cusLatLng = new LatLng(latitude, longitude);

    }

    public Bitmap getBitmap(String path) {
        Bitmap myBitmap = null;
        File imgFile = new File(path);
        if (imgFile.exists()) {
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
        }
        return myBitmap;
    }
    public void afterDialog(Bundle userBundle){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Next option");
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"Register the Location", "Finish"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                Toast.makeText(SuperUserCreator.this, "Getting this location details", Toast.LENGTH_SHORT).show();
                                mRegUserLoc.launch(new Intent(SuperUserCreator.this, MapPanoramaStVAct.class));

                                break;
                            case 1:
                                Toast.makeText(SuperUserCreator.this, "Back to Dashboard", Toast.LENGTH_SHORT).show();
                                Intent itemPurchaseIntent = new Intent(SuperUserCreator.this, SuperAdminOffice.class);
                                itemPurchaseIntent.putExtras(userBundle);
                                itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                break;

                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null) && requestCode == RESULT_CAMERA_CODE) {
            mImageUri = data.getData();


        }
        if ((data != null) && requestCode == RESULT_LOAD_IMAGE) {
            mImageUri = data.getData();



        }

        if (requestCode == PICTURE_REQUEST_CODE && data != null) {
            selectedImage = null;
            if (resultCode == RESULT_OK) {
                selectedImage = data.getData();
                ProfDAO dbHelper = new ProfDAO(this);
                dbHelper.insertProfilePicture(superID,customerID1,selectedImage);
                if (selectedImage != null) {
                    profilePix.setImageBitmap(getScaledBitmap(selectedImage));

                }

                Toast.makeText(this, "SUCCESS " , Toast.LENGTH_SHORT).show();
                setResultOk(selectedImage);
            }


            else if (resultCode == RESULT_CANCELED) {
                setResultCancelled();
                Toast.makeText(this, "CANCELLED " , Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    private void setResultOk(Uri selectedImage) {
        Intent intent = new Intent();
        profilePix.setImageBitmap(getScaledBitmap(selectedImage));
        setResult(AppCompatActivity.RESULT_OK, intent);
        finish();
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(AppCompatActivity.RESULT_CANCELED, intent);
        finish();
    }


    public String getPath(Uri photoUri) {

        String filePath = "";
        if (photoUri != null) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
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
    private Bitmap getScaledBitmap(Uri uri){
        Bitmap thumb = null ;
        try {
            ContentResolver cr = getContentResolver();
            InputStream in = cr.openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=8;
            thumb = BitmapFactory.decodeStream(in,null,options);
        } catch (FileNotFoundException e) {
            Toast.makeText(SuperUserCreator.this , "File not found" , Toast.LENGTH_SHORT).show();
        }
        return thumb ;
    }
    public void doSelectPix(View view) {

        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, PICTURE_REQUEST_CODE);
        Toast.makeText(SuperUserCreator.this, "Picture selection in Progress",
                Toast.LENGTH_SHORT).show();

        /*if (intent.resolveActivity(getPackageManager()) != null) {
            mGetUserPixContent.launch(intent);
        } else {
            Toast.makeText(SuperUserCreator.this, "There is no app that support this action",
                    Toast.LENGTH_SHORT).show();
        }*/


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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));


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

    public void superDatePicker(View view) {
        dateOfBirth = year+"/"+ newMonth+"/"+day;
    }

    public void sendOTPSuper(View view) {
    }

    public void verifySuperOTP(View view) {
        //startProfileActivity(sponsorID,cusLatLng,account,standingOrderAcct,joinedDate,uFirstName,uSurname,uPhoneNumber,uAddress,uUserName,uPassword,customer,newUserProfileL,nIN, superAdminProfile,dateOfBirth,selectedGender,selectedOffice,selectedState,birthday,customerManager,dateOfBirth,profileID1,virtualAccountNumber,soAccountNumber, customerID,birthdayID, investmentAcctID,itemPurchaseAcctID,promoAcctID,packageAcctID,customers,selectedUserType);

    }
}