package com.skylightapp.Accountant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Admins.AdminLoanList;
import com.skylightapp.Admins.CustomerListAct;
import com.skylightapp.Admins.SOCompletedList;
import com.skylightapp.Admins.SOListAct;
import com.skylightapp.Admins.SendSingleUserMAct;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CusOrderTab;
import com.skylightapp.Customers.CustUtilTab;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.CusLoanTab;
import com.skylightapp.Customers.CusPackForPayment;
import com.skylightapp.Customers.CusDocCodeSavingsAct;
import com.skylightapp.Customers.NewManualPackCusAct;
import com.skylightapp.Customers.PackageTab;
import com.skylightapp.Customers.SOTab;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.LoginAct;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.PasswordRecovAct;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.AwajimaSliderAct;
import com.skylightapp.SuperAdmin.AppCommission;
import com.skylightapp.SuperAdmin.AdminSOTabAct;
import com.skylightapp.SuperAdmin.SuperMPaymentListA;
import com.skylightapp.Tellers.TellerMessages;
import com.skylightapp.UserPrefActivity;
import com.skylightapp.UserTimeLineAct;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class AcctantBackOffice extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    public static final String KEY = "AcctantBackOffice.KEY";
    GridLayout maingrid;
    private SharedPreferences userPreferences;
    private Gson gson,gson1;
    private String json,json1;
    private LinearLayout linearLayout;

    private AppCompatImageView imgTime;

    private Profile userProfile;
    private int profileID;
    Button btnMore;
    float yValues[]={10,20,30,0,40,60};
    CardView cardViewPackges,cardViewGrpSavings,cardViewHistory, cardViewStandingOrders, cardViewOrders, cardViewSupport;

    AppCompatTextView extName, textAdminAcctNo, textAdminBalance,  textAmtOfSavings;

    private AppCompatTextView txtManualPaymentsToday;

    Intent data;
    FloatingActionButton floatingActionButton;
    Customer customer;
    Toolbar toolbar;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    String surname,firstName,names;
    long accountN,saving,skPackages;
    double accountBalance;
    CircleImageView imgProfilePic;
    private AppCommission appCommission;
    private DBHelper dbHelper;
    double manualPayment, totalSavingsToday;
    private Date today;
    String dateOfToday;
    private static final String PREF_NAME = "awajima";
    private ProfDAO profileDao;

    private PaymentDAO paymentDAO;
    private AdminBalanceDAO adminBalanceDAO;
    private DBHelper applicationDb;
    private SQLiteDatabase sqLiteDatabase;
    private MarketBusiness marketBusiness;
    String SharedPrefUserPassword;
    String SharedPrefCusID;
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    int SharedPrefProfileID;
    private Uri userPicture;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_acctant_office);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        marketBusiness= new MarketBusiness();
        appCommission = new AppCommission();
        dbHelper= new DBHelper(this);
        paymentDAO= new PaymentDAO(this);
        adminBalanceDAO= new AdminBalanceDAO(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));



        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        extName = findViewById(R.id.Acctant_name);
        toolbar = findViewById(R.id.toolbar_Acctant);
        textAdminAcctNo = findViewById(R.id.allAdminAcctID);
        textAdminBalance = findViewById(R.id.balance_AdminMoney);
        textAmtOfSavings = findViewById(R.id.savingsTodayAcctant);
        txtManualPaymentsToday = findViewById(R.id.ManualPaymentToday);
        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_view_Acctant);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.accountant_Drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Accountant BackOffice");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher_round);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        today = calendar.getTime();
        try {
            dateOfToday =dateFormat.format(today);
            today = dateFormat.parse(dateOfToday);


        } catch (ParseException ignored) {
        }

        imgProfilePic = findViewById(R.id.profile_image_Acctant);

        Glide.with(AcctantBackOffice.this)
                .asBitmap()
                .load(userPicture)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.drawable.ic_alert)
                //.listener(listener)
                .skipMemoryCache(true)
                .fitCenter()
                .centerCrop()
                .into(imgProfilePic);
        applicationDb = new DBHelper(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startCusPictureActivityForResult.launch(new Intent(AcctantBackOffice.this, CameraActivity.class));

            }
        });
        if (applicationDb != null) {
            sqLiteDatabase = applicationDb.getReadableDatabase();
            try {
                accountBalance = adminBalanceDAO.getAdminReceivedBalance();

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
        if (applicationDb != null) {
            sqLiteDatabase = applicationDb.getReadableDatabase();
            try {
                manualPayment=paymentDAO.getTotalPaymentToday1(dateOfToday);

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
        if (applicationDb != null) {
            sqLiteDatabase = applicationDb.getReadableDatabase();
            try {
                totalSavingsToday=dbHelper.getTotalSavingsToday(dateOfToday);

            } catch (SQLiteException sqLiteException) {
                sqLiteException.printStackTrace();
            }

        }

        if(totalSavingsToday>0){
            textAmtOfSavings.setText(MessageFormat.format("Savings today:{0}", totalSavingsToday));

        }else if(totalSavingsToday==0){
            textAmtOfSavings.setText("No savings today, yet");

        }
        if(appCommission !=null){
            accountN= appCommission.getAdminBalanceAcctID();

        }
        if(accountBalance>0){
            textAdminBalance.setText(MessageFormat.format("NGN{0}", accountBalance));

        }else if(accountBalance==0){
            textAdminBalance.setText("NGN" + "0.00");

        }

        if (accountBalance <0) {
            textAdminBalance.setText(MessageFormat.format("NGN-{0}", accountBalance));

        }

        textAdminAcctNo.setText(MessageFormat.format("Admin Acct No: {0}", accountN));
        if(manualPayment>0){
            txtManualPaymentsToday.setText(MessageFormat.format("Payments today: N{0}", manualPayment));
        }
        if (manualPayment == 0) {
            txtManualPaymentsToday.setText("There is no Manual Payment, today");

        }



        //getSupportActionBar().setLogo(R.drawable.ic_logo);


        //btnUtility=findViewById(R.id.btnUtilsCus);
        //btnUtility.setOnClickListener(this);
        /*btnUtility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCusUtils();


            }
        });*/
        //btnLoans=findViewById(R.id.btnLoans);
        //btnLoans.setOnClickListener(this);
        /*btnLoans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLoans();

            }
        });*/
        //btnSupport=findViewById(R.id.buttonOurPrivacyPolicy);
        //btnSupport.setOnClickListener(this);
        /*btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToOurHelpSupport();

            }
        });*/
        cardViewPackges=findViewById(R.id.cardCusPackages);
        cardViewHistory=findViewById(R.id.cardHistoryCus);
        cardViewGrpSavings=findViewById(R.id.cardGrpSavings);
        cardViewStandingOrders =findViewById(R.id.cardStandingOrdersCus);
        cardViewOrders =findViewById(R.id.cardOrdersCus);
        cardViewSupport =findViewById(R.id.cardCusHelp);
        linearLayout=findViewById(R.id.linearGrid);
        maingrid=(GridLayout) findViewById(R.id.ViewAccountant);
        imgTime = findViewById(R.id.AcctantGreetings);
        setSingleEvent(maingrid);
        StringBuilder welcomeString = new StringBuilder();


        calendar = Calendar.getInstance();

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
                .append("Awajima Accountant")
                .append("How are you? ")
                .append(getString(R.string.happy))
                .append(dow);


        setupHeader();


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.acctant_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home_customer:
                Intent mainIntent = new Intent(this, AcctantBackOffice.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                return true;
            case R.id.action_profile_customer:
                Intent pIntent = new Intent(this, UserPrefActivity.class);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pIntent);
                return true;

            case R.id.action_notification_customer:
                Intent p1Intent = new Intent(this, UserTimeLineAct.class);
                p1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(p1Intent);
                return true;
            case R.id.my_subscriptions:
                Intent so1Intent = new Intent(this, PackageTab.class);
                so1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(so1Intent);
                return true;

            case R.id.utility:
                Intent cIntent = new Intent(this, CustUtilTab.class);
                cIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cIntent);
                return true;

            case R.id.orderTab:
                Intent uIntent = new Intent(this, CusOrderTab.class);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(uIntent);
                return true;
            case R.id.loanTab:
                Intent loanIntent = new Intent(this, CusLoanTab.class);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loanIntent);
                return true;

            case R.id.action_help_customer:
                Intent payNowIntent = new Intent(this, CustomerHelpActTab.class);
                payNowIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(payNowIntent);
                return true;



            case R.id.so_menu:
                Intent grpIntent = new Intent(this, SOCompletedList.class);
                grpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(grpIntent);
                return true;


            case R.id.package_slider:
                Intent tIntent = new Intent(this, AwajimaSliderAct.class);
                tIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tIntent);
                return true;

            case R.id.all_new:
                Intent wIntent = new Intent(this, NewManualPackCusAct.class);
                wIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(wIntent);
                return true;


            case R.id.privacy_policy_customer:
                Intent w1Intent = new Intent(this, PrivacyPolicy_Web.class);
                w1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(w1Intent);
                return true;
            case R.id.admin_pswd2:
                Intent impIntent = new Intent(this, PasswordRecovAct.class);
                impIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(impIntent);
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
                startActivity(loginIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showDrawerButton() {
        toolbar = findViewById(R.id.toolbar_Acctant);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.syncState();
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
    private void setupDrawerListener() {
        drawer = findViewById(R.id.accountant_Drawer);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
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

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.nav_view_Acctant);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.accountant_Drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View headerView = navigationView.getHeaderView(0);
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        DBHelper applicationDb = new DBHelper(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
            Bitmap bitmap = profileDao.getProfilePicture(profileID);
            //CircleImageView imgProfilePic = headerView.findViewById(R.id.profile_image_Acctant);
            //imgProfilePic.setImageBitmap(bitmap);
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
                        tosend="TransAmadi";
                        Intent intent=new Intent(AcctantBackOffice.this, TransAmadiTranx.class);
                        intent.putExtra("Trans-Amadi Transactions",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==1)
                    {
                        tosend="Elelenwo Transactions";
                        Intent intent=new Intent(AcctantBackOffice.this, ElelenwoTranx.class);
                        intent.putExtra("Elelenwo Transactions",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==2)
                    {
                        tosend="Wimpey Transactions";
                        Intent intent=new Intent(AcctantBackOffice.this, WimpeyTranx.class);
                        intent.putExtra("Wimpey Transactions",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==3) {
                        tosend = "Standing Orders";
                        Intent intent=new Intent(AcctantBackOffice.this, AdminSOTabAct.class);
                        intent.putExtra("Standing Orders",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==4) {
                        tosend = "Manual Payments";
                        Intent intent=new Intent(AcctantBackOffice.this, SuperMPaymentListA.class);
                        intent.putExtra("Manual Payments",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==5) {
                        tosend = "Trans-Amadi Savings";
                        Intent intent=new Intent(AcctantBackOffice.this, TransAmadiSavings.class);
                        intent.putExtra("Trans-Amadi Savings",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==6) {
                        tosend = "Elelenwo Savings";
                        Intent intent=new Intent(AcctantBackOffice.this, ElelenwoSavings.class);
                        intent.putExtra("Elelenwo Savings",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==7) {
                        tosend = "My Sub. Savings";
                        Intent intent=new Intent(AcctantBackOffice.this, OfficeBranchSavings.class);
                        intent.putExtra("Wimpey Savings",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==8) {
                        tosend = "Loan List";
                        Intent intent=new Intent(AcctantBackOffice.this, AdminLoanList.class);
                        intent.putExtra("Loan List",tosend);
                        startActivity(intent);
                    }

                    else if(finalI==9) {
                        tosend = "TimeLine";
                        Intent intent=new Intent(AcctantBackOffice.this, UserTimeLineAct.class);
                        intent.putExtra("TimeLine",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==10) {
                        tosend = "Manual Payments";
                        Intent intent=new Intent(AcctantBackOffice.this, BranchMPayments.class);
                        intent.putExtra("Manual Payments",tosend);
                        startActivity(intent);
                    }
                    else if(finalI==11) {
                        tosend = "Customers";
                        Intent intent=new Intent(AcctantBackOffice.this, CustomerListAct.class);
                        intent.putExtra("Customers",tosend);
                        startActivity(intent);
                    }





                }
            });
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_dashboardAcctant:

                Intent intent = new Intent(AcctantBackOffice.this, AcctantBackOffice.class);
                startActivity(intent);
                break;
            case R.id.timeline_Acctant:
                Intent profile = new Intent(AcctantBackOffice.this, MyTimelineAct.class);
                startActivity(profile);
                break;
            case R.id.nav_Acctant_Savings:
                Intent active = new Intent(AcctantBackOffice.this, CusDocCodeSavingsAct.class);
                startActivity(active);
                break;
            case R.id.nav_Acctant_p:
                Intent history = new Intent(AcctantBackOffice.this, CusPackForPayment.class);
                startActivity(history);
                break;

            case R.id.nav_Acctant_SO:
                Intent chat = new Intent(AcctantBackOffice.this, SOListAct.class);
                startActivity(chat);
                break;

            case R.id.nav_AcctantTX:
                Intent supportInt = new Intent(AcctantBackOffice.this, CustomerHelpActTab.class);
                startActivity(supportInt);
                break;
            case R.id.nav_loanAcctant:
                Intent intPref = new Intent(AcctantBackOffice.this, CusLoanTab.class);
                startActivity(intPref);
                break;
            case R.id.nav_PaymentAcctant:
                Intent intSO = new Intent(AcctantBackOffice.this, SOTab.class);
                startActivity(intSO);
                break;
            case R.id.navsupportAcctant:
                Intent pIntent = new Intent(AcctantBackOffice.this, PackageTab.class);
                startActivity(pIntent);
                break;

            case R.id.nav_prefAcctant:
                Intent lIntent = new Intent(AcctantBackOffice.this, UserPrefActivity.class);
                startActivity(lIntent);
                break;


            case R.id.nav_logoutAcctant:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(AcctantBackOffice.this, SignTabMainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
                break;
        }
        return true;

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMyAcctantPref:
                Intent prefIntent = new Intent(this, UserPrefActivity.class);
                prefIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(prefIntent);
                break;
            case R.id.btnSendMessageAcctant:
                Intent sendIntent = new Intent(AcctantBackOffice.this, SendSingleUserMAct.class);
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(sendIntent);
                break;
            case R.id.buttonMyMessages:
                Intent supportIntent = new Intent(AcctantBackOffice.this, TellerMessages.class);
                supportIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(supportIntent);
                break;

        }

    }

    public void acctPref(View view) {
        Intent prefIntent = new Intent(this, UserPrefActivity.class);
        prefIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(prefIntent);
    }

    public void sendMessageAcct(View view) {
        Intent sendIntent = new Intent(AcctantBackOffice.this, SendSingleUserMAct.class);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(sendIntent);
    }

    public void getAcctSupportMessages(View view) {
        Intent supportIntent = new Intent(AcctantBackOffice.this, TellerMessages.class);
        supportIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(supportIntent);
    }
}