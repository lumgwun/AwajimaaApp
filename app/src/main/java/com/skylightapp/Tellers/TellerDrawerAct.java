package com.skylightapp.Tellers;

import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.skylightapp.Admins.BirthdayTab;
import com.skylightapp.AllCusPackTab;
import com.skylightapp.AllNewAct;
import com.skylightapp.AwajimaSliderAct;
import com.skylightapp.CameraActivity;
import com.skylightapp.CheckMailActivity;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.TellerForCusLoanAct;
import com.skylightapp.Customers.CusDocCodeSavingsAct;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.Inventory.StocksTransferAct;
import com.skylightapp.MyInvAct;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.MapAndLoc.ESiteGeoFenAct;
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

    private Toolbar toolbar;


    private Gson gson,gson1;
    private String json,json1;


    private Profile userProfile;
    FragmentManager fragmentManager;
    private Fragment fragment = null;
    private AppCompatImageView imgTime;
    CircleImageView profilePix;
    int soS,noOfSavings,noOfCustomers,noOfPackages;
    private AppCompatTextView txtWelcomeName, txtTellerBalance,standingOrders, txtTellerID,txtGrpSavings,packages,customers,txtSavings;

    int profileId;
    CardView cardViewMyCusPackges,cardViewGrpSavings, cardViewAllCusPacks, cardViewTellerWeb, cardViewOrders, cardViewSupport;


    DBHelper applicationDb;
    Account acct;
    double acctBalance;
    GridLayout maingrid;
    Button btnUtility, btnOurPrivacyPolicy;

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
    AppCompatImageButton profileImage;
    FrameLayout frameLayout;
    FloatingActionButton fab;
    private Button btnUtils,btnSupport, btnLogOut;
    private double tellerTodayBalance,tellerTotalCash,skylightTotalCashForToday;
    private TellerCash tellerCash;
    private Payment payment;
    private static boolean isPersistenceEnabled = false;
    private Date dateToday;
    private String tellerName;
    private Account tellerAccount;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;

    CusDAO cusDAO;
    PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;

    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private PaymentDAO paymentDAO;
    private TCashDAO tCashDAO;
    private static final String PREF_NAME = "awajima";
    private SQLiteDatabase sqLiteDatabase;
    private PrefManager prefManager;
    private double totalSavingsForTellerToday,totalPaymentForTellerToday;
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
        cusDAO= new CusDAO(this);
        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        sodao= new SODAO(this);
        paymentDAO= new PaymentDAO(this);
        tCashDAO= new TCashDAO(this);

        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
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
        acct = gson1.fromJson(json1, Account.class);
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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
        mauthListener();

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

        try {

            if(sqLiteDatabase !=null){
                sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            }
            if(dbHelper !=null){
                try {
                    totalSavingsForTellerToday=dbHelper.getTotalSavingsTodayForTeller(profileId,dateToday);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        try {

            if(sqLiteDatabase !=null){
                sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            }
            if(paymentDAO !=null){
                try {
                    totalPaymentForTellerToday=paymentDAO.getTotalPaymentTodayForTeller1(profileId,todayDate);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }




        try {

            if(sqLiteDatabase !=null){
                sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            }
            if(tCashDAO !=null){
                try {

                    tellerTotalCash=tCashDAO.getTellerCashForTellerToday(tellerName,todayDate);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }



        try {

            if(sqLiteDatabase !=null){
                sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            }
            if(dbHelper !=null){
                try {
                    skylightTotalCashForToday=dbHelper.getSkylightCashTotalForProfileAndDate(profileId,todayDate);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }






        //tellerTotalCash=dbHelper.getTellerCashForTellerTheMonth(tellerName,todayDate);



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
            txtTellerSkylightCashToday.setText("Cash from Awajima:N"+skylightTotalCashForToday);


        }else {
            txtTellerSkylightCashToday.setText("Cash from Awajima:N"+0);


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
            profilePix.setImageBitmap(profileDao.getProfilePicture(SharedPrefProfileID));
            String names = userProfile.getProfileFirstName();
            txtWelcomeName.setText("Welcome"+""+names);
            try {


                soS = userProfile.getProfile_StandingOrders().size();
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
            int soS = userProfile.getProfile_StandingOrders().size();
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


    private void mauthListener() {

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
                        tosend = "Awajima Packages";
                        Intent intent=new Intent(TellerDrawerAct.this, AwajimaSliderAct.class);
                        intent.putExtra("Awajima Packages",tosend);
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
                        Intent intent=new Intent(TellerDrawerAct.this, ESiteGeoFenAct.class);
                        intent.putExtra("Emergency",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==17) {
                        tosend = "Customer More";
                        Intent intent=new Intent(TellerDrawerAct.this, CusDocCodeSavingsAct.class);
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






    private void showImagePickerOptions() {
        Intent intent = new Intent(TellerDrawerAct.this, CameraActivity.class);
        startTellerPictureActivityForResult.launch(intent);

    }


    private void signOut() {
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        prefManager= new PrefManager();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        prefManager.logoutUser();
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

            Intent mainIntent = new Intent(this, AwajimaSliderAct.class);
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
            Intent loginIntent = new Intent(getApplicationContext(), LoginAct.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();

        }

        return super.onOptionsItemSelected(item);
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

}