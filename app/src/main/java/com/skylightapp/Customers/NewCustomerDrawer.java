package com.skylightapp.Customers;

import static com.skylightapp.Classes.AppController.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;


import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.blongho.country_data.Country;
import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skyfishjy.library.RippleBackground;
import com.skylightapp.Adapters.AccountAdapter;
import com.skylightapp.Adapters.AccountRecylerAdap;
import com.skylightapp.Adapters.CurrAdapter;
import com.skylightapp.Bookings.BoatBookingTab;
import com.skylightapp.Bookings.BookingHomeAct;
import com.skylightapp.Bookings.JetCharterAct;
import com.skylightapp.Bookings.SessionTab;
import com.skylightapp.Bookings.TaxiBookingTab;
import com.skylightapp.Bookings.TrainBookingTab;
import com.skylightapp.CameraActivity;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.LoginAct;
import com.skylightapp.MapAndLoc.AwajimaReportAct;
import com.skylightapp.MapAndLoc.ClimateCOffice;
import com.skylightapp.MapAndLoc.NewOSReportAct;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.Markets.BizOfficeAct;
import com.skylightapp.Markets.BizRegAct;
import com.skylightapp.Markets.LogisticsTab;
import com.skylightapp.Markets.MarketChatTab;
import com.skylightapp.Markets.MarketCreatorAct;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.MyGrpSavingsTab;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.NinIDAct;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.SecureAppAct;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.AwajimaSliderAct;
import com.skylightapp.SignUpAct;
import com.skylightapp.SubHistoryAct;
import com.skylightapp.SuperAdmin.StocksTab;
import com.skylightapp.Tellers.TellerHomeChoices;
import com.skylightapp.UserPrefActivity;
import com.skylightapp.StateDir.WasteRequestAct;
import com.skylightapp.VerifiAct;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewCustomerDrawer extends SecureAppAct implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener , OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    public static final String KEY = "NewCustomerDrawer.KEY";
    GridLayout maingrid;
    private SharedPreferences userPreferences;
    private Gson gson,gson1,gson2,gson3;
    private String json,json1,json2,json3,nBankN,marketName;
    private LinearLayout linearLayout;

    private AppCompatImageView imgTime;

    private Profile userProfile;
    private int profileID;
    float yValues[]={10,20,30,0,40,60};
    CardView cardViewPackges,cardViewGrpSavings,cardViewHistory, cardViewStandingOrders, cardViewOrders, cardViewSupport;

    AppCompatTextView extName, textID,textAcctNo,textBalance;
    CircleImageView profileImage;
    private Profile profile;

    private Customer customer;
    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    ContentLoadingProgressBar progressBar;
    private Account account;
    private ActionBarDrawerToggle toggle;
    private String surname,firstName,names,cusUserName;
    ImageButton hideLayouts;
    private int accountN, savings,skPackages;
    int acctIndex=0;
    private double accountBalance;
    CircleImageView imgProfilePic;
    private  int SOCount,customerID,loanCount,txCount;
    private StandingOrderAcct standingOrderAcct;
    private static final String PREF_NAME = "awajima";
    AppCompatButton btnToPacks;
    private Bundle homeBundle;
    private Spinner spnAccounts;
    AccountAdapter accountArrayAdapter;
    private ArrayList<Account> accountArrayList;
    private AppCompatTextView txtBankAcctBalance;
    ChipNavigationBar chipNavigationBar;
    private Currency currency;
    private String currencySymbol,userState,userCountry,SharedPrefUserMachine,userOfficeV,ward,town,ninValue,genderValue,reportType,reportCategory,nBank;
    private List<Country> countries;
    private List<Currency> currencies;
    private World world;
    private Bundle bundle;
    private CurrAdapter currencyAdapter;
    //private Bitmap bitmap;
    private DBHelper applicationDb;
    private Uri userPicture;
    private ProgressDialog progressDialog;
    private double nBankB;
    private AppCompatTextView txtBankName, balance, sPackages, accountNo,txtSO,  txtUserName;
    int PERMISSION_ALL = 17;
    private Market market;
    String SharedPrefUserPassword;
    int SharedPrefCusID;
    String SharedPrefUserRole;
    String SharedPrefUserName;
    int SharedPrefProfileID;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    private RecyclerView recyclerView;
    private AccountRecylerAdap accountRecylerAdap;
    private ArrayList<Account> accounts;
    private Bundle chooserBundle,reportBundle;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 201;
    private MaterialCardView cardViewE,cardViewA,cardViewM,cardViewI,cardViewP;
    ActivityResultLauncher<Intent> startCusPictureActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = result.getData();
                            if (data != null) {
                                Uri pictureUri = data.getData();
                                Picasso.get().load(pictureUri).into(profileImage);
                            }
                            Bundle bundle = null;
                            if (data != null) {
                                bundle = result.getData().getExtras();
                            }

                            Bitmap thumbnail = null;
                            if (bundle != null) {
                                thumbnail = (Bitmap) bundle.get("data");
                            }
                            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                            if (thumbnail != null) {
                                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                            }

                            File destination = new File(Environment.getExternalStorageDirectory(),
                                    System.currentTimeMillis() + ".jpg");
                            FileOutputStream fo;
                            try {
                                //destination.createNewFile();
                                fo = new FileOutputStream(destination);
                                try {
                                    fo.write(bytes.toByteArray());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    fo.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                            //Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                            /*if (data != null) {
                                pictureUri = data.getData();
                                Picasso.get().load(pictureUri).into(photoPrOP);
                            }*/
                            Toast.makeText(NewCustomerDrawer.this, "successful ", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(NewCustomerDrawer.this, "cancelled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_cus_drawer);
        checkInternetConnection();
        toolbar = (Toolbar) findViewById(R.id.toolbar_cus);
        applicationDb= new DBHelper(this);
        market= new Market();
        accounts= new ArrayList<>();
        reportBundle= new Bundle();
        recyclerView = findViewById(R.id.recycl_accts);
        cardViewE = findViewById(R.id.bd_card_E);
        cardViewA = findViewById(R.id.bd_card_A);
        cardViewM = findViewById(R.id.bd_card_M);
        cardViewI = findViewById(R.id.bd_card_I);
        cardViewP = findViewById(R.id.bd_card_P);
        chooserBundle= new Bundle();
        cardViewE.setOnClickListener(this::verifyEmailAddress);
        cardViewA.setOnClickListener(this::verifyAddress);
        cardViewM.setOnClickListener(this::checkMembership);
        cardViewI.setOnClickListener(this::verifyIDCard);
        cardViewP.setOnClickListener(this::verifyPhoneNo);

        /*SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }*/
        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_view_cus2);
        navigationView.setNavigationItemSelectedListener(this);
        bundle= new Bundle();
        mDrawerLayout = findViewById(R.id.cus_drawer2);
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.dark_green));
        toggle.setHomeAsUpIndicator(R.drawable.ic_home_black_24dp);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Customer BackOffice");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_notification_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mDrawerLayout.bringToFront();
                mDrawerLayout.requestLayout();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        gson = new Gson();
        World.init(this);
        homeBundle= new Bundle();
        countries=new ArrayList<>();
        accountArrayList= new ArrayList<>();
        gson1 = new Gson();
        gson3= new Gson();
        gson2= new Gson();
        countries=World.getAllCountries();
        currencies= new ArrayList<>();
        //currency= new Currency();
        userProfile=new Profile();
        customer=new Customer();
        account=new Account();
        homeBundle=getIntent().getExtras();
        standingOrderAcct= new StandingOrderAcct();
        btnToPacks = findViewById(R.id.cust_PackBtn);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        json2 = userPreferences.getString("LastMarketUsed", "");
        market = gson2.fromJson(json2, Market.class);
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        txtUserName = findViewById(R.id.cus_username);
        txtBankAcctBalance = findViewById(R.id._BankBalance4);
        spnAccounts = findViewById(R.id.spnA);
        txtSO = findViewById(R.id.savingsToday);
        extName = findViewById(R.id.cus_name3);
        textID = findViewById(R.id.cus_id2);
        textAcctNo = findViewById(R.id.allCus_Super);
        textBalance = findViewById(R.id.balance_normalCus);
        txtBankName = findViewById(R.id.cus_BankN);
        balance = findViewById(R.id.cus_BankBalance4444);
        accountNo = findViewById(R.id.cus_BankNo33);
        imgTime = findViewById(R.id.cusGreetings);
        imgProfilePic = findViewById(R.id.profile_image_cus);
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        imgTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rippleBackground.startRippleAnimation();
            }
        });
        getFirebaseToken();
        cardViewPackges=findViewById(R.id.cardCusPackages);
        cardViewHistory=findViewById(R.id.cardHistoryCus);
        cardViewGrpSavings=findViewById(R.id.cardGrpSavings);
        cardViewStandingOrders =findViewById(R.id.cardStandingOrdersCus);
        cardViewOrders =findViewById(R.id.cardOrdersCus);
        cardViewSupport =findViewById(R.id.cardCusHelp);
        linearLayout=findViewById(R.id.linearGrid);
        maingrid=(GridLayout) findViewById(R.id.ViewCus);


        Glide.with(NewCustomerDrawer.this)
                .asBitmap()
                .load(userPicture)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_alert)
                //.listener(listener)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(imgProfilePic);

        //currencies=World.getAllCurrencies();

        if(userProfile ==null){
            if(homeBundle !=null){
                userProfile=homeBundle.getParcelable("Profile");

            }
            if (userProfile != null) {
                accountArrayList=userProfile.getProfileAccounts();
                accounts=userProfile.getProfileAccounts();
            }
            accountArrayAdapter = new AccountAdapter(NewCustomerDrawer.this, android.R.layout.simple_spinner_item, accountArrayList);
            accountArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnAccounts.setAdapter(accountArrayAdapter);
            spnAccounts.setSelection(accountArrayAdapter.getPosition(account));

        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        accountRecylerAdap = new AccountRecylerAdap(NewCustomerDrawer.this,accounts);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(accountRecylerAdap);


        spnAccounts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(acctIndex==position){
                    return;
                }else {
                    account = (Account) parent.getSelectedItem();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(account !=null){
            currency=account.getCurrency();
            currencySymbol=account.getAccountCurrSymbol();

        }

        if(account !=null){
            txtBankAcctBalance.setText(""+currencySymbol+account.getAccountBalance());

        }



        ProfDAO applicationDb = new ProfDAO(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();

            names=surname+","+ firstName;



        }


        //imgProfilePic.setImageBitmap(bitmap);
        extName.setText("Welcome"+""+names);

        if(applicationDb !=null){
            if(profileID>0){
               // bitmap = applicationDb.getProfilePicture(profileID);

            }

        }
        imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCusPictureActivityForResult.launch(new Intent(NewCustomerDrawer.this, CameraActivity.class));

            }
        });
        if(customer !=null){
            surname= customer.getCusSurname();
            firstName= customer.getCusFirstName();
            nBank=customer.getCusBank();
            nBankB=customer.getCusBankBalance();
            nBankN=customer.getCusBankAcctNo();
            savings = customer.getCusDailyReports().size();
            skPackages = customer.getCusSkyLightPackages().size();
            SOCount= customer.getCusStandingOrders().size();

            account=customer.getCusAccount();
            standingOrderAcct=customer.getCusStandingOrderAcct();
            if(account !=null){
                accountBalance = account.getAccountBalance();
                accountN = account.getAwajimaAcctNo();

            }

            txtBankName.setText(MessageFormat.format("Bank :{0}", nBank));
            balance.setText(MessageFormat.format("{0}{1}", currencySymbol, nBankB));
            accountNo.setText(MessageFormat.format("Acct :{0}", nBankN));


            if(SOCount>0){
                txtSO.setText("Standing Orders:"+SOCount);

            }else if(SOCount==0){
                txtSO.setText("No Standing Orders, yet");

            }

            if(accountBalance>0){
                balance.setText(MessageFormat.format("{0}{1}", currencySymbol, accountBalance));

            }if (accountBalance <0) {
                balance.setText(MessageFormat.format("{0}{1}", currencySymbol, -nBankB));

            }else if(accountBalance==0){
                balance.setText(MessageFormat.format("{0}{1}", currencySymbol, 0.00));

            }

            accountNo.setText(MessageFormat.format("E-wallet No{0}", accountN));
            if(skPackages>0){
                sPackages.setText(MessageFormat.format("No of Packages{0}", skPackages));
            }
            if (skPackages == 0) {
                sPackages.setText("You do not have any packs yet");

            }


        }

        setSingleEvent(maingrid);
        StringBuilder welcomeString = new StringBuilder();


        Calendar calendar = Calendar.getInstance();

        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 5 && timeOfDay < 12) {
            //welcomeString.append(getString(R.string.good_morning));
            imgTime.setImageResource(R.drawable.good_morn3);
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            welcomeString.append(getString(R.string.good_afternoon));
            imgTime.setImageResource(R.drawable.good_after1);
        } else {
            welcomeString.append(getString(R.string.good_evening));
            imgTime.setImageResource(R.drawable.good_even2);
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
        welcomeString.append(", ")
                .append("Awajima Customer")
                .append("How are you? ")
                .append(getString(R.string.happy))
                .append(dow);


        setupHeader();


    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    protected synchronized void buildGoogleApiClient() {
        /*mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();*/
    }

    private void getFirebaseToken() {
        market= new Market();
        gson2= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json2 = userPreferences.getString("LastMarketUsed", "");
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        market = gson2.fromJson(json2, Market.class);
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        if(market !=null){
            marketName=market.getMarketName();
        }
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        /* String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();*/
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(SharedPrefUserName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(town)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        FirebaseMessaging.getInstance().subscribeToTopic(userOfficeV)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        FirebaseMessaging.getInstance().subscribeToTopic(genderValue)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(ward)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(SharedPrefUserRole)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(userCountry)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        FirebaseMessaging.getInstance().subscribeToTopic(marketName)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        FirebaseMessaging.getInstance().subscribeToTopic(userState)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(NewCustomerDrawer.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


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
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }
    public void onResume(){
        super.onResume();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);
    }


    public void showDrawerButton() {
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.red_brown_dark));
        mDrawerLayout = findViewById(R.id.cus_drawer2);
        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.syncState();
    }


    private void setupDrawerListener() {
        mDrawerLayout = findViewById(R.id.cus_drawer2);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
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






    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        reportBundle= new Bundle();
        reportBundle.putParcelable("Profile",userProfile);
        reportBundle.putString("ReportType",reportCategory);
        switch (id) {
            case R.id.nav_dashboard44:

                Intent intent = new Intent(NewCustomerDrawer.this, NewCustomerDrawer.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.nav_cus_market:
                Intent profile = new Intent(NewCustomerDrawer.this, MarketTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(profile);
                break;

            case R.id.market_N:
                    Intent marketIntent = new Intent(NewCustomerDrawer.this, MarketCreatorAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                marketIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(marketIntent);
                break;

            case R.id.nav_so_cus:
                Intent soIntent = new Intent(NewCustomerDrawer.this, SOTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                soIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                soIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(soIntent);
                break;

            case R.id.nav_New_BizC:
                Intent bizIntent = new Intent(NewCustomerDrawer.this, BizRegAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                bizIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                bizIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(bizIntent);
                break;



            case R.id.nav_cus_GrpSavings:
                Intent grpSavings = new Intent(NewCustomerDrawer.this, MyGrpSavingsTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                grpSavings.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                grpSavings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(grpSavings);
                break;
            case R.id.nav_cus_Env:
                reportCategory="Environmental Issues";
                Intent envIntent = new Intent(NewCustomerDrawer.this, AwajimaReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                envIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                envIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                envIntent.putExtras(reportBundle);
                startActivity(envIntent);
                break;
            case R.id.nav_Crime_Cus:
                reportCategory="Crimes";
                Intent intentCrimes = new Intent(NewCustomerDrawer.this, AwajimaReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intentCrimes.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentCrimes.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentCrimes.putExtras(reportBundle);
                startActivity(intentCrimes);
                break;

            case R.id.jet_charter_cus:
                Intent jetIntent = new Intent(NewCustomerDrawer.this, JetCharterAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                jetIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                jetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(jetIntent);
                break;

            case R.id.nav_climate_change:
                Intent climateCIntent = new Intent(NewCustomerDrawer.this, ClimateCOffice.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                climateCIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                climateCIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(climateCIntent);
                break;

            case R.id.nav_Cus_Emerg:
                reportCategory="Emergencies";
                Intent emergIntent = new Intent(NewCustomerDrawer.this, AwajimaReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                emergIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                emergIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emergIntent.putExtras(reportBundle);
                startActivity(emergIntent);
                break;
            case R.id.cusClimate_menu:
                reportCategory="Climate Change";
                Intent cIntent = new Intent(NewCustomerDrawer.this, AwajimaReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                cIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                cIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cIntent.putExtras(reportBundle);
                startActivity(cIntent);
                break;
            case R.id.nav_OilS:
                Intent oilSRIntent = new Intent(NewCustomerDrawer.this, NewOSReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                oilSRIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                oilSRIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(oilSRIntent);
                break;

            case R.id.nav_cus_Biz:
                Intent supportInt = new Intent(NewCustomerDrawer.this, BizOfficeAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(supportInt);
                break;
            case R.id.booking_nav_taxi:
                Intent intPref = new Intent(NewCustomerDrawer.this, TaxiBookingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intPref.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intPref.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intPref);
                break;
            case R.id.nav_train_bookings:
                Intent intSO = new Intent(NewCustomerDrawer.this, TrainBookingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intSO.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intSO.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intSO);
                break;
            case R.id.boat_cus_bookings:
                Intent pIntent = new Intent(NewCustomerDrawer.this, BoatBookingTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pIntent);
                break;

            case R.id.nav_cus_motor:
                Intent motorIntent = new Intent(NewCustomerDrawer.this, LogisticsTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                motorIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                motorIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(motorIntent);
                break;

            case R.id.session_booking_cus:
                Intent lIntent = new Intent(NewCustomerDrawer.this, SessionTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lIntent);
                break;
            case R.id.my_Loan_Tab:
                Intent loanIntent = new Intent(NewCustomerDrawer.this, CusLoanTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loanIntent);
                break;
            case R.id.nav_cus_Stocks:
                Intent stIntent = new Intent(NewCustomerDrawer.this, StocksTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                stIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                stIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(stIntent);
                break;
            case R.id.nav_order:
                Intent orderIntent = new Intent(NewCustomerDrawer.this, CusOrderTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                orderIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                orderIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(orderIntent);
                break;
            case R.id.nav_savings_2:
                Intent savingsIntent = new Intent(NewCustomerDrawer.this, MySavingsListAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                savingsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                savingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(savingsIntent);
                break;

            case R.id.nav_cus_accounts:
                Intent uIntent = new Intent(NewCustomerDrawer.this, MyAcctOverViewAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uIntent);
                break;

            case R.id.nav_new_wasteReq:
                Intent wasteIntent = new Intent(NewCustomerDrawer.this, WasteRequestAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                wasteIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                wasteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(wasteIntent);
                break;



            case R.id.nav_live_tab:
                Intent emergencyIntentSper = new Intent(NewCustomerDrawer.this, MarketChatTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                emergencyIntentSper.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(emergencyIntentSper);
                break;


            case R.id.nav_cus_timelines:

                Intent chathh = new Intent(NewCustomerDrawer.this, MyTimelineAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                chathh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(chathh);


            case R.id.nav_cus_Insurance:

                Intent shop = new Intent(NewCustomerDrawer.this, MarketTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(shop);

            case R.id.nav_cus_packs:

                Intent prtIntent = new Intent(NewCustomerDrawer.this, PackListTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                prtIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(prtIntent);


            case R.id.cSupport:
                Intent helpIntent = new Intent(NewCustomerDrawer.this, CustomerHelpActTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(helpIntent);

            case R.id.nav_New_market:
                Intent newMarketIntent = new Intent(NewCustomerDrawer.this, MarketCreatorAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                newMarketIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newMarketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newMarketIntent);
                break;

            case R.id.nav_New_virtualT:
                Intent tellerIntent = new Intent(NewCustomerDrawer.this, TellerHomeChoices.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                tellerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                tellerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tellerIntent);
                break;

            case R.id.nav_New_User:
                Intent newIntent = new Intent(NewCustomerDrawer.this, SignUpAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
                break;


            case R.id.nav_logout4:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(NewCustomerDrawer.this, SignTabMainActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_bottom_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home_customer:
                Intent mainIntent = new Intent(this, NewCustomerDrawer.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(mainIntent);
                return true;
            case R.id.action_profile_customer:
                Intent pIntent = new Intent(this, UserPrefActivity.class);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(pIntent);
                return true;

            case R.id.action_notification_customer:
                Intent p1Intent = new Intent(this, CustomerHelpActTab.class);
                p1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(p1Intent);
                return true;
            case R.id.my_subscriptions:
                Intent so1Intent = new Intent(this, SubHistoryAct.class);
                so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(so1Intent);
                return true;

            case R.id.utility:
                Intent cIntent = new Intent(this, CustUtilTab.class);
                cIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(cIntent);
                return true;

            case R.id.orderTab:
                Intent uIntent = new Intent(this, CusOrderTab.class);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(uIntent);
                return true;
            case R.id.loanTab:
                Intent loanIntent = new Intent(this, CusLoanTab.class);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(loanIntent);
                return true;

            case R.id.action_help_customer:
                Intent payNowIntent = new Intent(this, CustomerHelpActTab.class);
                payNowIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(payNowIntent);
                return true;



            case R.id.so_menu:
                Intent grpIntent = new Intent(this, SOTab.class);
                grpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(grpIntent);
                return true;


            case R.id.package_slider:
                Intent tIntent = new Intent(this, AwajimaSliderAct.class);
                tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(tIntent);
                return true;



            case R.id.privacy_policy_customer:
                Intent w1Intent = new Intent(this, PrivacyPolicy_Web.class);
                w1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(w1Intent);
                return true;


            case R.id.action_customer_logout:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(this, LoginAct.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(loginIntent);
                return true;
            default:
                //return super.onOptionsItemSelected(item);
        }
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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

    private void setupHeader() {

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_view_cus2);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout = findViewById(R.id.cus_drawer2);

        toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("Customer BackOffice");

        View headerView = navigationView.getHeaderView(0);
        btnToPacks = headerView.findViewById(R.id.cust_PackBtn);
        btnToPacks.setOnClickListener(this::getMyPacks);
        ProfDAO applicationDb = new ProfDAO(this);
        btnToPacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent so1Intent = new Intent(NewCustomerDrawer.this, PackageTab.class);
                so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(so1Intent);

            }
        });
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);


        if(userProfile !=null){
            profileID=userProfile.getPID();
            if(applicationDb !=null){
                Bitmap bitmap = applicationDb.getProfilePicture(profileID);
                CircleImageView imgProfilePic = headerView.findViewById(R.id.profile_image_cus);
                imgProfilePic.setImageBitmap(bitmap);

            }

        }
        Currency currency=null;
        String acctCurrency=null;
        if(customer !=null){
            surname= customer.getCusSurname();
            firstName= customer.getCusFirstName();
            customerID=customer.getCusUID();
            cusUserName=customer.getCusUserName();

            account=customer.getCusAccount();
            standingOrderAcct=customer.getCusStandingOrderAcct();
            if(account !=null){
                accountBalance = account.getAccountBalance();
                accountN = account.getAwajimaAcctNo();
                currency=account.getCurrency();


            }
            if(currency !=null){
                acctCurrency=currency.getSymbol();

            }else {
                acctCurrency="NGN";
            }
            if (accountBalance == 0.00) {
                balance.setText(acctCurrency + 0.00);

            }
            if(accountBalance>0){
                balance.setText(acctCurrency + Utils.awajimaAmountFormat(accountBalance));
            }
            if(accountN>0){
                accountNo.setText("E-wallet No" + accountN);

            }


            savings = customer.getCusDailyReports().size();
            skPackages = customer.getCusSkyLightPackages().size();
            SOCount= customer.getCusStandingOrders().size();
            txCount = customer.getCusTransactions().size();
            loanCount = customer.getCusLoans().size();
            if(SOCount>0){
                txtSO.setText("Standing Orders:"+SOCount);

            }else if(SOCount==0){
                txtSO.setText("No Standing Orders, yet");

            }


            if (accountBalance <0) {
                balance.setText(MessageFormat.format(acctCurrency, Utils.awajimaAmountFormat(accountBalance)));

            }else if(accountBalance==0){
                balance.setText(acctCurrency + "0.00");

            }

            accountNo.setText(MessageFormat.format("E-wallet No{0}", accountN));
            if(skPackages>0){
                sPackages.setText(MessageFormat.format("No of Packages{0}", skPackages));
            }
            if (skPackages == 0) {
                sPackages.setText("You do not have any packages yet");

            }


            //name = customerProfile.getFirstName() + " " + customerProfile.getSurname();

            textID.setText(MessageFormat.format("User Id:{0}", customerID));
            txtUserName.setText(MessageFormat.format("{0}{1}", cusUserName));


        }


    }
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void setSingleEvent(GridLayout maingrid)
    {
        for(int i=0;i<maingrid.getChildCount();i++)
        {
            CardView cardView=(CardView) maingrid.getChildAt(i);
            final int finalI=i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent intent=new Intent(MainActivity.this,places.class);
                    String tosend="";
                    if(finalI==0)
                    {
                        tosend="SO";
                        Intent intent=new Intent(NewCustomerDrawer.this, SOTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==1) {
                        tosend="Markets";
                        Intent intent=new Intent(NewCustomerDrawer.this, MarketTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }



                    else if(finalI==2) {
                        tosend = "Group Savings";
                        Intent intent=new Intent(NewCustomerDrawer.this, MyGrpSavingsTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==3) {
                        tosend = "Loans";
                        Intent intent=new Intent(NewCustomerDrawer.this, CusLoanTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==4) {
                        tosend = "Orders";
                        Intent intent=new Intent(NewCustomerDrawer.this, CusOrderTab.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }
                    else if(finalI==5) {
                        tosend = "My Sub. Packs";
                        Intent intent=new Intent(NewCustomerDrawer.this, BookingHomeAct.class);
                        intent.putExtra("info",tosend);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        startActivity(intent);
                    }


                }
            });
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnStatementMy:
                Intent active = new Intent(NewCustomerDrawer.this, CusStatementAct.class);
                active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(active);
                break;
            case R.id.btnMyHistory:
                Intent loanTab = new Intent(NewCustomerDrawer.this, CusDocCodeSavingsAct.class);
                loanTab.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loanTab);
                break;
            case R.id.buttonSupprt:
                Intent helpTab = new Intent(NewCustomerDrawer.this, CustomerHelpActTab.class);
                helpTab.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(helpTab);
                break;

        }

    }

    public void getAcctStatement(View view) {
        gson = new Gson();
        gson1= new Gson();
        chooserBundle= new Bundle();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        chooserBundle.putParcelable("Profile",userProfile);
        chooserBundle.putParcelable("Customer",customer);
        chooserBundle.putInt("CUSTOMER_ID",SharedPrefCusID);
        chooserBundle.putInt("PROFILE_ID",SharedPrefProfileID);
        Intent active = new Intent(NewCustomerDrawer.this, CusStatementAct.class);
        active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        active.putExtras(chooserBundle);
        startActivity(active);
    }
    public void verifyAddress(View view) {
        chooserBundle= new Bundle();
        gson = new Gson();
        gson1= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        chooserBundle=getIntent().getExtras();
        chooserBundle.putString("chooser","Address");
        chooserBundle.putParcelable("Profile",userProfile);
        chooserBundle.putParcelable("Customer",customer);
        chooserBundle.putInt("CUSTOMER_ID",SharedPrefCusID);
        chooserBundle.putInt("PROFILE_ID",SharedPrefProfileID);
        Intent active = new Intent(NewCustomerDrawer.this, VerifiAct.class);
        active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        active.putExtras(chooserBundle);
        startActivity(active);
    }
    public void verifyEmailAddress(View view) {
        chooserBundle= new Bundle();
        gson = new Gson();
        gson1= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        chooserBundle=getIntent().getExtras();
        chooserBundle.putString("chooser","EmailAddress");
        chooserBundle.putParcelable("Profile",userProfile);
        chooserBundle.putParcelable("Customer",customer);
        chooserBundle.putInt("CUSTOMER_ID",SharedPrefCusID);
        chooserBundle.putInt("PROFILE_ID",SharedPrefProfileID);
        Intent active = new Intent(NewCustomerDrawer.this, VerifiAct.class);
        active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        active.putExtras(chooserBundle);
        startActivity(active);
    }
    public void verifyPhoneNo(View view) {
        chooserBundle= new Bundle();
        gson = new Gson();
        gson1= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        chooserBundle=getIntent().getExtras();
        chooserBundle.putString("chooser","PhoneNo");
        chooserBundle.putParcelable("Profile",userProfile);
        chooserBundle.putParcelable("Customer",customer);
        chooserBundle.putInt("CUSTOMER_ID",SharedPrefCusID);
        chooserBundle.putInt("PROFILE_ID",SharedPrefProfileID);
        Intent active = new Intent(NewCustomerDrawer.this, VerifiAct.class);
        active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        active.putExtras(chooserBundle);
        startActivity(active);
    }
    public void verifyIDCard(View view) {
        gson = new Gson();
        gson1= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        chooserBundle.putParcelable("Profile",userProfile);
        chooserBundle.putParcelable("Customer",customer);
        chooserBundle.putInt("CUSTOMER_ID",SharedPrefCusID);
        chooserBundle.putInt("PROFILE_ID",SharedPrefProfileID);
        Intent active = new Intent(NewCustomerDrawer.this, NinIDAct.class);
        active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        active.putExtras(chooserBundle);
        startActivity(active);
    }
    public void checkMembership(View view) {
        chooserBundle= new Bundle();
        gson = new Gson();
        gson1= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        chooserBundle=getIntent().getExtras();
        chooserBundle.putString("chooser","PhoneNo");
        chooserBundle.putParcelable("Profile",userProfile);
        chooserBundle.putParcelable("Customer",customer);
        chooserBundle.putInt("CUSTOMER_ID",SharedPrefCusID);
        chooserBundle.putInt("PROFILE_ID",SharedPrefProfileID);
        Intent active = new Intent(NewCustomerDrawer.this, CusStatementAct.class);
        active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        active.putExtras(chooserBundle);
        startActivity(active);
    }

    public void getMyLoansCus(View view) {
        chooserBundle= new Bundle();
        gson = new Gson();
        gson1= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        chooserBundle=getIntent().getExtras();
        chooserBundle.putString("chooser","PhoneNo");
        chooserBundle.putParcelable("Profile",userProfile);
        chooserBundle.putParcelable("Customer",customer);
        chooserBundle.putInt("CUSTOMER_ID",SharedPrefCusID);
        chooserBundle.putInt("PROFILE_ID",SharedPrefProfileID);
        Intent loanI = new Intent(NewCustomerDrawer.this, CusLoanTab.class);
        loanI.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        loanI.putExtras(chooserBundle);
        startActivity(loanI);
    }

    public void getMyMessages(View view) {
        chooserBundle= new Bundle();
        gson = new Gson();
        gson1= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        chooserBundle=getIntent().getExtras();
        chooserBundle.putString("chooser","PhoneNo");
        chooserBundle.putParcelable("Profile",userProfile);
        chooserBundle.putParcelable("Customer",customer);
        chooserBundle.putInt("CUSTOMER_ID",SharedPrefCusID);
        chooserBundle.putInt("PROFILE_ID",SharedPrefProfileID);
        Intent helpIntent = new Intent(NewCustomerDrawer.this, CustomerHelpActTab.class);
        helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        helpIntent.putExtras(chooserBundle);
        startActivity(helpIntent);
    }

    public void getMyPacks(View view) {
        chooserBundle= new Bundle();
        gson = new Gson();
        gson1= new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine =userPreferences.getString("machine", "");
        SharedPrefUserRole = userPreferences.getString("PROFILE_ROLE", "");
        userState = userPreferences.getString("PROFILE_STATE", "");
        userCountry = userPreferences.getString("PROFILE_COUNTRY", "");
        ward = userPreferences.getString("PROFILE_WARD", "");
        town = userPreferences.getString("PROFILE_TOWN", "");
        userOfficeV = userPreferences.getString("PROFILE_OFFICE", "");
        genderValue = userPreferences.getString("PROFILE_GENDER", "");
        ninValue = userPreferences.getString("PROFILE_NIN", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        customer = gson1.fromJson(json1, Customer.class);
        chooserBundle=getIntent().getExtras();
        chooserBundle.putString("chooser","PhoneNo");
        chooserBundle.putParcelable("Profile",userProfile);
        chooserBundle.putParcelable("Customer",customer);
        chooserBundle.putInt("CUSTOMER_ID",SharedPrefCusID);
        chooserBundle.putInt("PROFILE_ID",SharedPrefProfileID);
        Intent so1Intent = new Intent(NewCustomerDrawer.this, PackageTab.class);
        so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        so1Intent.putExtras(chooserBundle);
        startActivity(so1Intent);
    }


    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
//Showing Current Location Marker on Map
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location locations = locationManager.getLastKnownLocation(provider);
        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.getLongitude();
            double latitude = locations.getLatitude();
            Geocoder geocoder = new Geocoder(getApplicationContext(),
                    Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude,
                        longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String state = listAddresses.get(0).getAdminArea();
                    String country = listAddresses.get(0).getCountryName();
                    String subLocality = listAddresses.get(0).getSubLocality();
                    markerOptions.title("" + latLng + "," + subLocality + "," + state
                            + "," + country);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(com.google.android.gms.maps.CameraUpdateFactory.zoomTo(11));
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied",
                            Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        //Initialize Google Play Services
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }
}