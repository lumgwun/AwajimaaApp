package com.skylightapp.Admins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.AllNewAct;
import com.skylightapp.CameraActivity;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.SOTab;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
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
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.GroupSavingsTab;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Markets.BizCusDepositAct;
import com.skylightapp.PasswordRecovAct;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.SignUpAct;
import com.skylightapp.AwajimaSliderAct;
import com.skylightapp.MapAndLoc.StateEmergList;
import com.skylightapp.SuperAdmin.Awajima;
import com.skylightapp.Tellers.AllCusLoanRepayment;
import com.skylightapp.Tellers.LoanRepaymentTab;
import com.skylightapp.Tellers.MyCusList;
import com.skylightapp.Tellers.TellerWebTab;
import com.skylightapp.TransactionFragment;
import com.skylightapp.UserPrefActivity;
import com.skylightapp.UserTimeLineAct;

import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawerLayout;
    //private NavigationView navView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    public static final String INTENT_IMAGE_PICKER_OPTION = "image_picker_option";

    private SharedPreferences userPreferences;
    private Gson gson,gson2,gson3;
    private String json,json2,json3;

    private Profile userProfile;

    private Dialog depositDialog;
    private Spinner spnAccounts;
    private ArrayAdapter<Account> accountAdapter;
    private EditText edtDepositAmount;
    private Button btnCancel;
    private Button btnDeposit;
    private Button btnBorrow;
    private Button btnAddPackage;
    int userID;
    int profileID;
    Customer customer;
    String names;
    Profile profile;
    Bundle bundle;
    private Fragment fragment = null;
    NavigationView navigationView;
    ViewPager viewPager;
    AppCompatImageView greetingImage;
    public static final int STORAGE_PERMISSION_CODE = 107;
    public static final int REQUEST_IMAGE = 100;
    long profileId;
    int customerID;
    DBHelper applicationDb;
    AppCompatTextView adminName,txtAdminUserName, txtNoOfPaymentsToday,txtGrpSavings,txtProfileID, txtTotalPayments, txtNoOfReports, txtgrandBalance,txtAdminUserName2;
    Account acct;
    double acctBalance;
    private String selectedNavMenu;
    Button btnSO,btnImportantDate,btnSignUp;

    CircleImageView profilePix;
    String SharedPrefUserName;
    SharedPreferences sharedPreferences;
    double paymentTotal,tellerReportBalance;
    int paymentCount,tellerReportCount;
    Date newDate1;
    private  Bitmap bitmap;
    private SQLiteDatabase sqLiteDatabase;
    String SharedPrefUserPassword,officeBranch,SharedPrefUserMachine,SharedPrefState,SharedPrefOffice,
            SharedPrefAddress,SharedPrefJoinedDate,SharedPrefGender,
            SharedPrefRole,SharedPrefDOB,SharedPrefPhone,SharedPrefEmail,SharedPrefProfileID,
            SharedPrefSurName,SharedPrefFirstName,SharedPrefAcctNo,customerId,SharedPrefBankNo,SharedPrefAcctBalance,SharedPrefAcctName,SharedPrefType,SharedPrefBank
            ;
    GridLayout gridLayout;
    private DBHelper dbHelper;
    private static final String PREF_NAME = "awajima";
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;
    private CusDAO cusDAO;
    private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private PaymentDAO paymentDAO;
    private AdminBalanceDAO adminBalanceDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private MarketBusiness marketBusiness;
    private int officeBranchID,awajimaID;
    private long bizID;
    private Awajima awajima;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_drawer);
        drawerLayout = findViewById(R.id.admin_drawer3);
        dbHelper= new DBHelper(this);
        userProfile=new Profile();
        newDate1= new Date();
        awajima= new Awajima();
        marketBusiness= new MarketBusiness();
        cusDAO= new CusDAO(this);
        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        paymentDAO= new PaymentDAO(this);
        adminBalanceDAO= new AdminBalanceDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=sharedPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=sharedPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=sharedPreferences.getString("Machine", "");
        SharedPrefProfileID=sharedPreferences.getString("PROFILE_ID", "");
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
        SharedPrefAcctBalance=sharedPreferences.getString("ACCOUNT_BALANCE", "");
        SharedPrefUserMachine=sharedPreferences.getString("Machine", "");
        officeBranchID=sharedPreferences.getInt("OFFICE_BRANCH_ID", 0);
        officeBranch=sharedPreferences.getString("OFFICE_BRANCH_NAME", "");
        gson = new Gson();
        gson2 = new Gson();
        gson3 = new Gson();
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        json = sharedPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);

        json = sharedPreferences.getString("LastMarketBusinessUsed", "");
        awajima = gson3.fromJson(json3, Awajima.class);

        if(awajima !=null){
            awajimaID=awajima.getAwajimaID();
        }


        ArcNavigationView navigationView =  findViewById(R.id.nav_view_admin3);
        navigationView.setNavigationItemSelectedListener(this);
        gridLayout=(GridLayout)findViewById(R.id.ViewAdmin3);
        setSingleEvent(gridLayout);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        try {
            newDate1=sdf.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(marketBusiness !=null){
            bizID=marketBusiness.getBusinessID();
        }
        adminName=findViewById(R.id.nameAdmin);
        txtAdminUserName = findViewById(R.id.userNAdmin);
        txtAdminUserName2 = findViewById(R.id.admin_username90);
        txtProfileID = findViewById(R.id.profile_ID);
        txtGrpSavings = findViewById(R.id.GrpSavingsAdmin56);
        txtNoOfPaymentsToday = findViewById(R.id.paymentNO);
        txtTotalPayments = findViewById(R.id.adminTotalPayment);
        if(paymentDAO !=null){
            try {

                paymentTotal=paymentDAO.getTotalPaymentTodayForBranch1(SharedPrefOffice,todayDate);
                paymentCount=paymentDAO.getPaymentCountTodayForBranch(SharedPrefOffice,todayDate);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        txtNoOfPaymentsToday.setText(MessageFormat.format("Branch Payments:{0}", paymentCount));
        txtTotalPayments.setText(MessageFormat.format("Payments: N{0}", paymentTotal));

        txtNoOfReports = findViewById(R.id.reportsAdminToday);
        try {
            if(tReportDAO !=null){
                tellerReportCount=tReportDAO.getTellerReportCountTodayForBranch(SharedPrefOffice,todayDate);
                tellerReportBalance=tReportDAO.getTotalTellerReportAmountSubmittedTodayForBranch(SharedPrefOffice,newDate1);


            }



        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        txtNoOfReports.setText(MessageFormat.format("Reports:{0}", tellerReportCount));
        txtgrandBalance = findViewById(R.id.grandBalanceAdmin);
        txtgrandBalance.setText(MessageFormat.format("Grand Balance: N{0}", tellerReportBalance));

        btnSO=findViewById(R.id.standingOrder3);
        btnImportantDate=findViewById(R.id.ImportantDate);
        btnSignUp=findViewById(R.id.signUpUserA);
        btnImportantDate.setOnClickListener(this::importantDate);
        btnSignUp.setOnClickListener(this::signUpNewUser);
        btnSO.setOnClickListener(this::standingOrders);

        toolbar = findViewById(R.id.toolbar_admin2);
        //setSupportActionBar(toolbar);
        FragmentContainerView frameLayout = findViewById(R.id.AContent_admin);

         toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        //drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //toggle.setHomeAsUpIndicator(R.drawable.home2);
        //getSupportActionBar().setElevation(0);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Admin Branch BackOffice");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dashboard_black_24dp);

        navigationView.setNavigationItemSelectedListener(this);
        setupDrawerListener();
        setupHeader(SharedPrefProfileID,SharedPrefUserName,userProfile);
        txtGrpSavings = findViewById(R.id.GrpSavingsAdmin56);

        //acctBalance=acct.getAccountBalance();
        if(userProfile !=null){
            profileID =userProfile.getPID();
            String name =userProfile.getProfileFirstName();
            names =userProfile.getProfileFirstName()+","+userProfile.getProfileLastName();
            adminName.setText(MessageFormat.format("Admin{0}", name));
            userID=userProfile.getPID();
            String username = userProfile.getProfileUserName();
            txtAdminUserName2.setText(MessageFormat.format("Admin UserName:{0}", username));
            txtProfileID.setText(MessageFormat.format("Profile ID:{0}", userProfile.getPID()));
            txtGrpSavings.setText(MessageFormat.format("Grp Savings:{0}", userProfile.getProf_GrpSavings().size()));

        }

        profilePix =findViewById(R.id.profilePAdmin);

        if(userProfile !=null){
            userID=userProfile.getPID();
            String username = userProfile.getProfileUserName();
            txtAdminUserName2.setText(MessageFormat.format("Admin UserName:{0}", username));
            txtProfileID.setText(MessageFormat.format("Profile ID:{0}", userProfile.getPID()));

        }

        DBHelper applicationDb = new DBHelper(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {

            sqLiteDatabase = dbHelper.getWritableDatabase();
            try {
                bitmap= profileDao.getProfilePicture(userID);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }

        try {
            profilePix.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }



        profilePix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPicturePicking();

            }
        });
        greetingImage=findViewById(R.id.greetingsA);
        calendar = Calendar.getInstance();

        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 5 && timeOfDay < 12) {
            //welcomeString.append(getString(R.string.good_morning));
            greetingImage.setImageResource(R.drawable.good_morn3);
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            //welcomeString.append(getString(R.string.good_afternoon));
            greetingImage.setImageResource(R.drawable.good_after1);
        } else {
            //welcomeString.append(getString(R.string.good_evening));
            greetingImage.setImageResource(R.drawable.good_even2);
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

        welcomeString.append("welcome").append("Admin"+"").append(names)
                .append("How are You today? ")
                .append("");

        welcomeString.append(dow)
                .append(".");
    }
    private void setSingleEvent(GridLayout gridLayout) {
        for(int i = 0; i<gridLayout.getChildCount();i++){
            CardView cardView=(CardView)gridLayout.getChildAt(i);
            final int finalI= i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(finalI) {
                        case 0:
                            Intent myIntent = new Intent(AdminDrawerActivity.this, AdminPackageAct.class);
                            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myIntent);
                            break;
                        case 1:
                            Intent myIntent1 = new Intent(AdminDrawerActivity.this, UserTimeLineAct.class);
                            myIntent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myIntent1);
                            break;
                        case 2:
                            Intent myIntent2 = new Intent(AdminDrawerActivity.this, BranchSuppAct.class);
                            myIntent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myIntent2);
                            break;
                        case 3:
                            Intent myIntent3 = new Intent(AdminDrawerActivity.this, BirthdayTab.class);
                            myIntent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myIntent3);
                            break;
                        case 4:
                            Intent myIntent4 = new Intent(AdminDrawerActivity.this, LoanRepaymentTab.class);
                            myIntent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myIntent4);
                            break;
                        case 5:
                            Intent myIntent5 = new Intent(AdminDrawerActivity.this, SendSingleUserMAct.class);
                            myIntent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myIntent5);
                            break;
                        case 6:
                            Intent myWeb = new Intent(AdminDrawerActivity.this, TellerWebTab.class);
                            myWeb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myWeb);
                            break;
                        case 7:
                            Intent myIntentTeller = new Intent(AdminDrawerActivity.this, BranchReportTab.class);
                            myIntentTeller.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myIntentTeller);
                            break;

                        case 8:
                            Intent superIntentT = new Intent(AdminDrawerActivity.this, AdminCountAct.class);
                            superIntentT.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(superIntentT);
                            break;

                        case 9:
                            Intent lastIntent = new Intent(AdminDrawerActivity.this, UserPrefActivity.class);
                            lastIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(lastIntent);
                            break;

                        case 10:
                            Intent stockIntent = new Intent(AdminDrawerActivity.this, AdminStocksTab.class);
                            stockIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(stockIntent);
                            break;

                        case 11:
                            Intent emergIntent = new Intent(AdminDrawerActivity.this, StateEmergList.class);
                            emergIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(emergIntent);
                            break;

                        case 12:
                            Intent depositIntent = new Intent(AdminDrawerActivity.this, BizCusDepositAct.class);
                            depositIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(depositIntent);
                            break;
                        default:
                    }
                    Toast.makeText(AdminDrawerActivity.this,"Selected "+ finalI,
                            Toast.LENGTH_SHORT).show();

                }
            });

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
                inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
            }
        });
    }

    private void setupHeader(String SharedPrefProfileID,String SharedPrefUserName,Profile userProfile) {
        ArcNavigationView navigationView = findViewById(R.id.nav_view_admin3);
        View headerView = navigationView.getHeaderView(0);
        //txtAdminUserName2 = findViewById(R.id.admin_username90);
        /*txtAdminUserName2 = headerView.findViewById(R.id.admin_username90);
        txtProfileID = headerView.findViewById(R.id.profile_ID);
        txtGrpSavings = headerView.findViewById(R.id.GrpSavingsAdmin56);
        */

    }
    public void startPicturePicking() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("PROFILE_ID", profileID);
        intent.putExtra(INTENT_IMAGE_PICKER_OPTION, INTENT_IMAGE_PICKER_OPTION);
        startActivityForResult(intent,REQUEST_IMAGE);

    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (requestCode==REQUEST_IMAGE && resultCode==RESULT_OK) {
            data = getIntent();
            if(userProfile !=null){
                profileID =userProfile.getPID();
                customer=userProfile.getProfileCus();

            }
            if(customer !=null){
                customerID=customer.getCusUID();

            }
            Uri pictureUri = data.getData();
            applicationDb =new DBHelper(this);
            profileDao.insertProfilePicture(profileID,customerID,pictureUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    public void showDrawerButton() {

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
    }

    public void showUpButton() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
    public void removeUpButton() {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
    }

    /// new codes
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_activity_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home10:
                Intent mainIntent = new Intent(this, AdminDrawerActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                return true;
            case R.id.AdminTimeline:
                Intent timelineIntent = new Intent(this, UserTimeLineAct.class);
                timelineIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(timelineIntent);
                return true;

            case R.id.branchReport:
                Intent bIntent = new Intent(this, BranchReportTab.class);
                bIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(bIntent);
                return true;

            case R.id.history20:
                Intent pIntent = new Intent(this, ProfilePackHistoryAct.class);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(pIntent);
                return true;

            case R.id.package_slider:
                Intent tIntent = new Intent(this, AwajimaSliderAct.class);
                tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(tIntent);
                return true;

            case R.id.cus_list1:
                Intent cIntent = new Intent(this, MyCusList.class);
                cIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(cIntent);
                return true;

            case R.id.nav_so:
                Intent standingOIntent = new Intent(this, SOTab.class);
                standingOIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(standingOIntent);
                return true;
            case R.id.transactions_money:
                Intent transactionIntent = new Intent(this, AdminTransActivity.class);
                transactionIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(transactionIntent);
                return true;


            case R.id.admin_money:
                Intent loanIntent = new Intent(this, AllCusLoanRepayment.class);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loanIntent);
                return true;

            case R.id.nav_now:
                Intent payNowIntent = new Intent(this, PayNowActivity.class);
                payNowIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(payNowIntent);
                return true;

            case R.id.all_new:
                Intent wIntent = new Intent(this, AllNewAct.class);
                wIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(wIntent);
                return true;

            case R.id.nav_birthday_date:
                Intent bdIntent = new Intent(this, BirthdayTab.class);
                bdIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(bdIntent);
                return true;


            case R.id.web_allApp:
                Intent w1Intent = new Intent(this, TellerWebTab.class);
                w1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(w1Intent);
                return true;

            case R.id.admin_support:
                Intent sIntent = new Intent(this, BranchSuppAct.class);
                sIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(sIntent);
                return true;
            case R.id.admin_pswd2:
                Intent impIntent = new Intent(this, PasswordRecovAct.class);
                impIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(impIntent);
                return true;

            case R.id.myAdminPref:
                Intent prefIntent = new Intent(this, UserPrefActivity.class);
                prefIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(prefIntent);
                return true;

            case R.id.nav_logout_11:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(this, SignTabMainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void importantDate(View view) {
        Intent pIntent = new Intent(this, BirthdayTab.class);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pIntent);
    }

    public void signUpNewUser(View view) {
        Intent pIntent = new Intent(this, SignUpAct.class);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pIntent);
    }

    public void standingOrders(View view) {
        Intent pIntent = new Intent(this, SODueDateListAct.class);
        pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(pIntent);
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        Class fragmentClass = null;
        String title = item.getTitle().toString();
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.nav_app_home_admin2:
                Intent mainIntent = new Intent(this, AdminDrawerActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                return true;

            case R.id.all_web:
                Intent newIntent = new Intent(this, TellerWebTab.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(newIntent);
                return true;
            case R.id.transactions_admin2:
                fragmentClass= TransactionFragment.class;
                return true;
            case R.id.nav_birthDay:
                Intent impIntent = new Intent(this, BirthdayTab.class);
                impIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(impIntent);
                return true;

            case R.id.nav_send_message2:
                Intent mIntent = new Intent(this, SendSingleUserMAct.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                return true;

            case R.id.grp_saving_nav:
                Intent billsIntent = new Intent(this, GroupSavingsTab.class);
                billsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(billsIntent);
                return true;
            case R.id.admin_timeLines:
                Intent timelineIntent = new Intent(this, UserTimeLineAct.class);
                timelineIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(timelineIntent);

                return true;
            case R.id.admin_support3:
                Intent supportIntent = new Intent(this, BranchSuppAct.class);
                supportIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(supportIntent);
                return true;

            case R.id.log_admin3:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
               Intent loginIntent = new Intent(this, SignTabMainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                return super.onOptionsItemSelected(item);
            default:
        }
        try {
            Fragment fragment = (Fragment) Objects.requireNonNull(fragmentClass).newInstance();
            fragmentManager.beginTransaction().replace(R.id.AContent_admin, fragment).commit();

            item.setChecked(true);
            setTitle(title);
            drawerLayout.closeDrawers();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}