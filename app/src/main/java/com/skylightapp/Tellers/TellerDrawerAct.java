package com.skylightapp.Tellers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.skylightapp.Admins.BirthdayTab;
import com.skylightapp.AllCusPackTab;
import com.skylightapp.AllNewAct;
import com.skylightapp.CameraActivity;
import com.skylightapp.CheckMailActivity;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkylightCash;
import com.skylightapp.Customers.TellerForCusLoanAct;
import com.skylightapp.Customers.CustomerPackageActivity;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.StocksTransferAct;
import com.skylightapp.MyInvAct;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.SkylightSliderAct;
import com.skylightapp.SuperAdmin.WorkersLocationAct;
import com.skylightapp.UserPrefActivity;
import com.skylightapp.UserTimeLineAct;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class TellerDrawerAct extends AppCompatActivity {
    private static final String TAG = TellerDrawerAct.class.getSimpleName();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle xToggle;
    //private FirebaseAuth xAuth;
    //private NavigationView navView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    private SharedPreferences userPreferences;
    private Gson gson,gson1;
    private String json,json1;


    private Profile userProfile;
    FragmentManager fragmentManager;
    private Fragment fragment = null;
    private AppCompatImageView imgTime;
    CircleImageView profilePix;
    int soS,noOfSavings,noOfCustomers,noOfPackages;
    private AppCompatTextView txtWelcomeName, txtTellerBalance,standingOrders, txtTellerID,txtGrpSavings,packages,customers,txtSavings;

    long packageCount;
    int profileId;
    ViewPager viewPager;
    CardView cardViewMyCusPackges,cardViewGrpSavings, cardViewAllCusPacks, cardViewTellerWeb, cardViewOrders, cardViewSupport;

    Bundle bundle;

    public static final int REQUEST_IMAGE = 100;
    public static final String INTENT_IMAGE_PICKER_OPTION = "image_picker_option";

    DBHelper applicationDb;
    Customer customer;
    Account acct;
    double acctBalance;
    GridLayout maingrid;
    Button btnUtility, btnOurPrivacyPolicy,btnInvestment;

    String SharedPrefUserName;
    SharedPreferences sharedPreferences;
    String SharedPrefUserPassword;
    int SharedPrefCusID;
    String SharedPrefUserMachine;
    String SharedPrefState;
    String SharedPrefOffice;
    String SharedPrefAddress;
    String SharedPrefJoinedDate;
    String SharedPrefGender;
    String name;
    String SharedPrefRole;
    String SharedPrefDOB;
    String SharedPrefPhone;
    String SharedPrefEmail;
    int SharedPrefProfileID;
    String SharedPrefSurName;
    String SharedPrefFirstName;
    String SharedPrefAcctNo;
    int customerId;
    String SharedPrefBankNo;
    String SharedPrefAcctBalance;
    String SharedPrefAcctName;
    String SharedPrefType;
    String SharedPrefBank
            ;

    DBHelper dbHelper;
    DrawerLayout drawer;
    AppCompatImageButton profileImage;
    //private SectionsPagerAdapter mSectionsPagerAdapter;
    long tellerID;

    //ArcNavigationView navigationView;
    FrameLayout frameLayout;
    FloatingActionButton fab;
    private Button btnUtils,btnSupport, btnLogOut;
    private double tellerTodayBalance,tellerTotalCash,tellerTotalPayment,skylightTotalCashForToday;
    private TellerCash tellerCash;
    private Payment payment;
    SkylightCash skylightCash;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static boolean isPersistenceEnabled = false;
    private Date dateToday;
    private String tellerName;
    private Account tellerAccount;
    private static final String PREF_NAME = "skylight";
    AppCompatTextView txtTellerTodaySavings,txtTellerSkylightCashToday,txtTellerPaymentToday,txtTellerProfileBalance;

    ActivityResultLauncher<Intent> startTellerPictureActivityForResult = registerForActivityResult(
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
                            Toast.makeText(TellerDrawerAct.this, "successful ", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(TellerDrawerAct.this, "cancelled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_teller3);
        dbHelper= new DBHelper(this);
        dateToday= new Date();
        gson = new Gson();
        gson1 = new Gson();
        userProfile= new Profile();
        tellerAccount= new Account();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=sharedPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=sharedPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=sharedPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine=sharedPreferences.getString("machine", "");
        SharedPrefProfileID=sharedPreferences.getInt("PROFILE_ID", 0);
        SharedPrefSurName=sharedPreferences.getString("PROFILE_SURNAME", "");
        SharedPrefFirstName=sharedPreferences.getString("PROFILE_FIRSTNAME", "");
        SharedPrefEmail=sharedPreferences.getString("PROFILE_EMAIL", "");
        SharedPrefPhone=sharedPreferences.getString("PROFILE_PHONE", "");
        SharedPrefAddress=sharedPreferences.getString("PROFILE_ADDRESS", "");
        SharedPrefDOB=sharedPreferences.getString("PROFILE_DOB", "");
        SharedPrefRole=sharedPreferences.getString("PROFILE_ROLE", "");
        SharedPrefGender=sharedPreferences.getString("PROFILE_GENDER", "");
        SharedPrefJoinedDate=sharedPreferences.getString("PROFILE_DATE_JOINED", "");
        SharedPrefOffice=sharedPreferences.getString("PROFILE_OFFICE", "");
        SharedPrefState=sharedPreferences.getString("PROFILE_STATE", "");
        customerId=sharedPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefAcctNo=sharedPreferences.getString("ACCOUNT_NO", "");
        SharedPrefBankNo=sharedPreferences.getString("BANK_ACCT_NO", "");
        SharedPrefAcctBalance=sharedPreferences.getString("ACCOUNT_BALANCE", "");
        SharedPrefAcctName=sharedPreferences.getString("ACCOUNT_NAME", "");
        SharedPrefType=sharedPreferences.getString("ACCOUNT_TYPE", "");
        SharedPrefBank=sharedPreferences.getString("ACCOUNT_BANK", "");
        json1 = sharedPreferences.getString("LastAccountUsed", "");
        acct = gson.fromJson(json1, Account.class);
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        applicationDb = new DBHelper(this);
        if(userProfile!=null){
            profileId=userProfile.getPID();
            tellerName=userProfile.getProfileLastName()+","+userProfile.getProfileFirstName();
            tellerAccount=userProfile.getProfileAccount();
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.add(Calendar.DAY_OF_YEAR, 31);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        try {
            dateToday=sdf.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(tellerAccount !=null){
            acctBalance=tellerAccount.getAccountBalance();
        }
        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mAuth = FirebaseAuth.getInstance();
        mauthListener();

        //NavigationView navigationView = findViewById(R.id.tellerNav);
        //View header = navigationView.getHeaderView(0);
        //navigationView.setNavigationItemSelectedListener(this);
        tellerCash= new TellerCash();
        payment= new Payment();
        btnLogOut = findViewById(R.id.btnLogout);
        btnSupport = findViewById(R.id.buttonTellerSupport);
        btnUtils = findViewById(R.id.btnUtilsTeller);
        toolbar = findViewById(R.id.toolbar_teller3);

        btnUtils.setOnClickListener(this::goToPrefs);
        btnSupport.setOnClickListener(this::GoToOurHelpSupport);
        btnLogOut.setOnClickListener(this::doLogOut);
        profilePix = findViewById(R.id.profile_image_teller);
        txtWelcomeName = findViewById(R.id.welcome_teller019);
        txtGrpSavings = findViewById(R.id.teller_grpSavings);
        txtSavings = findViewById(R.id.savingsTeller);
        txtTellerID = findViewById(R.id.teller_id);
        customers = findViewById(R.id.cus90);
        packages = findViewById(R.id.packages90);
        imgTime = findViewById(R.id.greetingsTellerr);
        fab = findViewById(R.id.fab_teller2);
        customers.setOnClickListener(this::getMyCustomer);
        packages.setOnClickListener(this::getMyPackages);
        standingOrders = findViewById(R.id.No_of_SO090);
        standingOrders.setOnClickListener(this::getMyStandingOrder);
        //TabLayout tabLayout = findViewById(R.id.Home_tabs_Teller);
        //ViewPager viewPager = findViewById(R.id.teller_Page_View);
        cardViewMyCusPackges =findViewById(R.id.cardMyCusPackages);
        cardViewAllCusPacks =findViewById(R.id.cardAllCusPacks);

        txtTellerTodaySavings = findViewById(R.id.teller_Savings_Received);
        txtTellerSkylightCashToday = findViewById(R.id.teller_Sky_Cash);
        txtTellerPaymentToday = findViewById(R.id.teller_PaymentToday);
        txtTellerBalance = findViewById(R.id.teller_balance_Today);
        txtTellerProfileBalance = findViewById(R.id.teller_balancer);

        double totalSavingsForTellerToday=dbHelper.getTotalSavingsTodayForTeller(profileId,dateToday);
        double totalPaymentForTellerToday=dbHelper.getTotalPaymentTodayForTeller(profileId,dateToday);
        tellerTotalCash=dbHelper.getTellerCashForTellerToday(tellerName,todayDate);
        //tellerTotalCash=dbHelper.getTellerCashForTellerTheMonth(tellerName,todayDate);
        skylightTotalCashForToday=dbHelper.getSkylightCashTotalForProfileAndDate(profileId,todayDate);


        tellerTodayBalance =skylightTotalCashForToday+totalSavingsForTellerToday-totalPaymentForTellerToday;

        if(acctBalance>0){
            txtTellerProfileBalance.setText("My Profile Balance:N"+acctBalance);


        }if(acctBalance==0){
            txtTellerProfileBalance.setText("My Profile Balance:N"+0);


        }else {
            txtTellerProfileBalance.setText("My Profile Balance:N -"+acctBalance);

        }
        if(tellerTodayBalance>0){
            txtTellerBalance.setText("Today Net Balance:N"+tellerTodayBalance);


        }if(tellerTodayBalance==0){
            txtTellerBalance.setText("Today Net Balance:N"+0);


        }else {
            txtTellerBalance.setText("Today Net Balance:N -"+tellerTodayBalance);


        }

        if(totalSavingsForTellerToday>0){
            txtTellerTodaySavings.setText("Customer Collections:N"+totalSavingsForTellerToday);


        }else {
            txtTellerTodaySavings.setText("Customer Collections:N"+0);


        }
        if(totalPaymentForTellerToday>0){
            txtTellerPaymentToday.setText("Field Payments:N"+totalPaymentForTellerToday);


        }else {
            txtTellerPaymentToday.setText("Field Payments:N"+0);


        }
        if(skylightTotalCashForToday>0){
            txtTellerSkylightCashToday.setText("Cash from Skylight:N"+skylightTotalCashForToday);


        }else {
            txtTellerSkylightCashToday.setText("Cash from Skylight:N"+0);


        }

        frameLayout = findViewById(R.id.frameCont_Teller);
        cardViewGrpSavings=findViewById(R.id.cardTellerGS);
        cardViewTellerWeb =findViewById(R.id.cardTellerWT);
        btnUtility=findViewById(R.id.btnUtilsCus);
        cardViewOrders =findViewById(R.id.cardOrdersCus);
        cardViewSupport =findViewById(R.id.cardCusHelp);
        btnOurPrivacyPolicy =findViewById(R.id.buttonOurPrivacyPolicy);
        //linearLayout=findViewById(R.id.linearGrid);
        maingrid=(GridLayout) findViewById(R.id.ViewTeller333);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setSingleEvent(maingrid);

        if (toolbar != null) {
            //toolbar.setLogo(R.drawable.skylight_2);
            toolbar.setTitle("Teller Back Office");
        }
        profilePix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerOptions();
            }
        });



        if(userProfile !=null){
            String name = userProfile.getProfileLastName();
            txtWelcomeName.setText(MessageFormat.format("Welcome{0}", name));
            profilePix.setImageBitmap(applicationDb.getProfilePicture(SharedPrefProfileID));
            String names = userProfile.getProfileFirstName();
            txtWelcomeName.setText("Welcome"+""+names);
            try {


                soS = userProfile.getStandingOrders().size();
                noOfSavings = userProfile.getProfileDailyReports().size();
                noOfCustomers = userProfile.getProfileCustomers().size();
                noOfPackages = userProfile.getProfileSkylightPackages().size();

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }
            standingOrders.setText(MessageFormat.format("{0}your SO:{1}", soS));

            txtSavings.setText("My Savings:"+noOfSavings);

            profileId = userProfile.getPID();

            if(noOfCustomers==0){

                customers.setText("You don not have a Customer yet");

            }
            if(noOfSavings==0){

                txtSavings.setText("You don not have any Savings yet");

            }
            if(noOfPackages==0){
                packages.setText("You don not have any Package yet");



            }
            customers.setText(MessageFormat.format("My Customers :{0}", noOfCustomers));
            packages.setText(MessageFormat.format("My Packages :{0}", noOfPackages));



        }
        try {
            int soS = userProfile.getStandingOrders().size();
            standingOrders.setText(MessageFormat.format("{0}your SO:{1}", soS));
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }



        calendar = Calendar.getInstance();

        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 5 && timeOfDay < 12) {
            //welcomeString.append(getString(R.string.good_morning));
            imgTime.setImageResource(R.drawable.good_morn3);
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            //welcomeString.append(getString(R.string.good_afternoon));
            imgTime.setImageResource(R.drawable.good_after1);
        } else {
            //welcomeString.append(getString(R.string.good_evening));
            imgTime.setImageResource(R.drawable.good_even2);
        }
        StringBuilder welcomeString = new StringBuilder();



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

        welcomeString.append(dow)
                .append(".");

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();

            }
        });

        //txtWelcomeName.setText(welcomeString.toString());


        //setupDrawerListener();
        //setupHeader();


    }
    private void mauthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.homeT_eller:
                    //toolbar.setTitle("Teller Home");
                    Intent tIntent = new Intent(TellerDrawerAct.this, TellerDrawerAct.class);
                    tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(tIntent);
                    return true;
                case R.id.Mycus_overview:
                    //toolbar.setTitle("My Customer Packs");
                    Intent myIntent = new Intent(TellerDrawerAct.this, MyCustPackTab.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myIntent);
                    return true;
                case R.id.teller_allCus:
                    //toolbar.setTitle("All Customer Packs");
                    Intent allIntent = new Intent(TellerDrawerAct.this, AllCusPackTab.class);
                    allIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(allIntent);
                    return true;

            }

            return false;
        }
    };
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
                        tosend="My Customer Packs";
                        Intent intent=new Intent(TellerDrawerAct.this, MyCustPackTab.class);
                        intent.putExtra("My Customer Packs","My Customer Packs");
                        startActivity(intent);
                    }
                    else if(finalI==1)
                    {
                        tosend="All Customer Packs";
                        Intent intent=new Intent(TellerDrawerAct.this, AllCusPackTab.class);
                        intent.putExtra("All Customer Packs",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==2) {
                        tosend = "Skylight Packages";
                        Intent intent=new Intent(TellerDrawerAct.this, SkylightSliderAct.class);
                        intent.putExtra("Skylight Packages",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==3) {
                        tosend = "New Sign Up";
                        Intent intent=new Intent(TellerDrawerAct.this, AllNewAct.class);
                        intent.putExtra("New Sign Up",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==4) {
                        tosend = "Loan Tab'";
                        Intent intent=new Intent(TellerDrawerAct.this, LoanRepaymentTab.class);
                        intent.putExtra("Loan Tab",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==5)
                    {
                        tosend="Borrow";
                        Intent intent=new Intent(TellerDrawerAct.this, TellerForCusLoanAct.class);
                        intent.putExtra("Borrow",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==6) {
                        tosend = "Check Email";
                        Intent intent=new Intent(TellerDrawerAct.this, CheckMailActivity.class);
                        intent.putExtra("Check Email",tosend);
                        startActivity(intent);
                    }


                    else if(finalI==7) {
                        tosend = "web App";
                        Intent intent=new Intent(TellerDrawerAct.this, TellerWebTab.class);
                        intent.putExtra("web App",tosend);
                        startActivity(intent);
                    }

                    else if(finalI==8) {
                        tosend = "Timeline";
                        Intent intent=new Intent(TellerDrawerAct.this, UserTimeLineAct.class);
                        intent.putExtra("Timeline",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==9) {
                        tosend = "Birthdays";
                        Intent intent=new Intent(TellerDrawerAct.this, BirthdayTab.class);
                        intent.putExtra("Birthdays",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==10) {
                        tosend = "Teller Reports";
                        Intent intent=new Intent(TellerDrawerAct.this, DailyReportAct.class);
                        intent.putExtra("Teller Reports",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==11) {
                        tosend = "Teller Reports List";
                        Intent intent=new Intent(TellerDrawerAct.this, DailyReportListAct.class);
                        intent.putExtra("Teller Reports List",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==12) {
                        tosend = "Unconfirmed Savings";
                        Intent intent=new Intent(TellerDrawerAct.this, UnConfirmedSavingsList.class);
                        intent.putExtra("Teller Reports List",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==13) {
                        tosend = "Manual Payment";
                        Intent intent=new Intent(TellerDrawerAct.this, ManualPaymentAct.class);
                        intent.putExtra("Teller Reports List",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==14) {
                        tosend = "Manual List";
                        Intent intent=new Intent(TellerDrawerAct.this, ManualPaymentList.class);
                        intent.putExtra("Teller Reports List",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==15) {
                        tosend = "Preferences";
                        Intent intent=new Intent(TellerDrawerAct.this, UserPrefActivity.class);
                        intent.putExtra("Teller Preference",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==16) {
                        tosend = "Emergency";
                        Intent intent=new Intent(TellerDrawerAct.this, WorkersLocationAct.class);
                        intent.putExtra("Emergency",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==17) {
                        tosend = "Customer More";
                        Intent intent=new Intent(TellerDrawerAct.this, CustomerPackageActivity.class);
                        intent.putExtra("Customer More",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==18) {
                        tosend = "Socks Transfer";
                        Intent intent=new Intent(TellerDrawerAct.this, StocksTransferAct.class);
                        intent.putExtra("Socks Transfer",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==19) {
                        tosend = "Inventory List";
                        Intent intent=new Intent(TellerDrawerAct.this, MyInvAct.class);
                        intent.putExtra("Inventory List",tosend);
                        startActivity(intent);
                    }

                }
            });
        }
    }


    /*public void showDrawerButton() {

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(
                this, drawer,  R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    public void removeUpButton() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    }


    public void openDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.teller5_drawer);
        drawer.openDrawer(GravityCompat.START);
    }*/
    private void showImagePickerOptions() {
        Intent intent = new Intent(TellerDrawerAct.this, CameraActivity.class);
        startTellerPictureActivityForResult.launch(intent);

    }


    private void signOut() {
        SharedPreferences preferences =getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SignTabMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.teller_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_app_teller2) {
            /*if (drawer.isOpen()) {
                drawer.closeDrawers();
                return true;
            }
        }

        if (id == R.id.nav_app_teller2) {
            Intent mainIntent = new Intent(this, TellerDrawerAct.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        if (id == R.id.teller_pack2) {

            Intent mainIntent = new Intent(this, SkylightSliderAct.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }

        if (id == R.id.my_cusP) {
            Intent mainIntent = new Intent(this, MyCustPackTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        if (id == R.id.allCusP) {
            Intent mainIntent = new Intent(this, AllCusPackTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        if (id == R.id.payNow) {
            Intent mainIntent = new Intent(this, PayForMyCusAct.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }

        if (id == R.id.grpSavings) {
            Intent mainIntent = new Intent(this, GroupSavingsTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        if (id == R.id.myTimelines) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameCont_Teller, new UserTimeLineOverview())
                    .commit();

        }
        if (id == R.id.pp) {
            Intent mainIntent = new Intent(this, PrivacyPolicy_Web.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        if (id == R.id.newCust) {
            Intent mainIntent = new Intent(this, NewSignUpActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        if (id == R.id.standingOrders) {
            Intent mainIntent = new Intent(this, StandingOrderTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }

        if (id == R.id.teller_all_web3) {
            Intent mainIntent = new Intent(this, TellerWebTab.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }


        if (id == R.id.teller_pref) {
            Intent mainIntent = new Intent(this, UserPrefActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        if (id == R.id.change_pswd) {
            Intent mainIntent = new Intent(this, PasswordRecoveryActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        if (id == R.id.log11) {
            Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }*/


    /*@SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        String title = item.getTitle().toString();
        switch (item.getItemId()) {
            case R.id.nav_app_teller2:
                Intent mainIntent = new Intent(this, TellerDrawerAct.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;

            case R.id.teller_pack2:
                Intent pIntent = new Intent(this, SkylightSliderAct.class);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.my_cusP:
                Intent mCIntent = new Intent(this, MyCustPackTab.class);
                mCIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;

            case R.id.allCusP:
                Intent aCIntent = new Intent(this, AllCusPackTab.class);
                aCIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.payNow:
                Intent payIntent = new Intent(this, PayNowActivity.class);
                payIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.grpSavings:
                Intent grpIntent = new Intent(this, GroupSavingsTab.class);
                grpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.pp:
                Intent pPIntent = new Intent(this, PrivacyPolicy_Web.class);
                pPIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.newCust:
                Intent signUpIntent = new Intent(this, NewSignUpActivity.class);
                signUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.standingOrders:
                Intent soIntent = new Intent(this, StandingOrderTab.class);
                soIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.teller_all_web3:
                Intent webIntent = new Intent(this, TellerWebTab.class);
                webIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;
            case R.id.teller_pref:
                Intent prefIntent = new Intent(this, UserPrefActivity.class);
                prefIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;

            case R.id.timeline_cus2:
                title = "My Timeline";
                Intent tIntent = new Intent(this, UserTimeLineAct.class);
                tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                break;

            case R.id.cus_overview2:
                title = "My Customers";
                fragment = new CustomerListFragment();
                break;
            case R.id.pOverview:
                title = "My Packs";
                fragment = new TPackListFragment();

                break;
            case R.id.birthdays_teller2:
                title = "Birthdays";
                fragment = new BirthdayListFragment();
                break;


            case R.id.cm_savings4:
                title = "My Savings";
                fragment = new SavingsListFragment();
                break;

            case R.id.teller_doc3:
                title = "My Documents";
                fragment = new MyDocumentsFragment();
                break;
            case R.id.teller_transactions3:
                title = "My Transactions";
                fragment = new TransactionFragment();
                break;
            case R.id.cm_loan_teller3:
                title = "My Cus. Loans";
                fragment = new CustomerLoanOverview();
                break;
            case R.id.nav_send_message:
                title = "Send a User Message";
                Intent intent3 = new Intent(this, SendUserMessageAct.class);
                startActivity(intent3);

                break;
            case R.id.nav_logout:
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, SignTabMainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameCont_Teller, fragment)
                    .commit();
            return true;
        }

        try {

            item.setChecked(true);
            //setTitle(title);
            drawerLayout.closeDrawer(GravityCompat.START);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return true;

    }*/

    public void showUpButton() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        /*toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/
    }



    /*@Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    private void setupDrawerListener() {
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
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

    private void setupHeader() {

        int savingsCount = 0;
        NavigationView navigationView = findViewById(R.id.tellerNav);
        View headerView = navigationView.getHeaderView(0);
        if (headerView !=null){
            txtTellerID = headerView.findViewById(R.id.teller_id);
            txtGrpSavings = headerView.findViewById(R.id.teller_grpSavings);
            if(userProfile !=null){
                int grpSavingsCount = userProfile.getGroupSavings().size();
                txtTellerID.setText(MessageFormat.format("My ID:{0}", userProfile.getuID()));
                txtGrpSavings.setText(MessageFormat.format("Group Savings:{0}", grpSavingsCount));
            }


        }

    }
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameCont_Teller, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TransactionFragment(), "My Trans");
        adapter.addFragment(new UserTimeLineOverview(), "Timeline");
        adapter.addFragment(new MyDocumentsFragment(), "Docs");
        adapter.addFragment(new MyMessageFragment(), "Mes");
        viewPager.setAdapter(adapter);
    }*/
    public void goToPrefs(View view) {
        Intent uIntent = new Intent(TellerDrawerAct.this, UserPrefActivity.class);
        startActivity(uIntent);
    }


    public void doLogOut(View view) {
        signOut();
    }

    public void GoToOurHelpSupport(View view) {
        Intent profile = new Intent(TellerDrawerAct.this, TellerMessages.class);
        startActivity(profile);
    }

    public void getMyStandingOrder(View view) {
    }

    public void getMyCustomer(View view) {
        Intent profile = new Intent(TellerDrawerAct.this, MyCusList.class);
        startActivity(profile);
    }

    public void getMyPackages(View view) {
        Intent profile = new Intent(TellerDrawerAct.this, MyProfilePackList.class);
        startActivity(profile);
    }

    /*@SuppressWarnings("deprecation")
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }*/
}