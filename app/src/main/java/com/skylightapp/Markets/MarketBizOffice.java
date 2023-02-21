package com.skylightapp.Markets;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.Toast;

import com.blongho.country_data.Currency;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Admins.BranchReportTab;
import com.skylightapp.AwajimaSliderAct;
import com.skylightapp.BizSubQTOptionAct;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CusLoanRepaymentAct;
import com.skylightapp.Customers.CusLoanTab;
import com.skylightapp.Customers.CusOrderTab;
import com.skylightapp.Customers.CusPackForPayment;
import com.skylightapp.Customers.CusDocCodeSavingsAct;
import com.skylightapp.Customers.CustUtilTab;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Customers.NewManualPackCusAct;
import com.skylightapp.Customers.PackListTab;
import com.skylightapp.Customers.PackageTab;
import com.skylightapp.Customers.SOTab;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.LoginAct;
import com.skylightapp.MapAndLoc.AwajimaReportAct;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.MyGrpSavingsTab;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.PasswordRecovAct;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;
import com.skylightapp.SubRecordAct;
import com.skylightapp.UserPrefActivity;
import com.skylightapp.UserTimeLineAct;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class MarketBizOffice extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private ChipNavigationBar chipNavigationBar;
    private SharedPreferences userPreferences;
    SharedPreferences.Editor editor;
    Gson gson, gson1,gson2;
    String json, json1, json2,userName, userPassword, userMachine;
    Profile userProfile, customerProfile;
    private int profileID;
    private Customer customer;
    private DBHelper dbHelper;
    private static final String PREF_NAME = "awajima";
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    String SharedPrefProfileID;
    private MarketBusiness marketBusiness;
    private long bizID;
    private Toolbar toolbar;
    private Toolbar bizToolBar;
    private DrawerLayout drawer;
    private Account account;
    private ActionBarDrawerToggle toggle;
    private AppCompatImageView greetingsImg;
    private CircleImageView bizLogo;
    private AppCompatTextView txtName,txtBizID, txtUserName,txtWalletID,txtBizBalance,txtMBCus,txtSpnBalance,txtTodayNewCus,txtTodayMessages,txtTodayOrder;
    private Spinner spnAccts;
    private String bizName;
    private AppCompatButton btnRenewSub;
    private Set<String> bizTypes;
    CircleImageView imgProfilePic,userPix;
    Uri userPicture,bizImage;
    private CircularImageView bizLogoHeader;

    private ActivityResultLauncher<Intent> mStartBizTypeForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            // Handle the Intent
                            Toast.makeText(MarketBizOffice.this, "Activity returned ok", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(MarketBizOffice.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_office_drawer);
        checkInternetConnection();
        chipNavigationBar = findViewById(R.id.mb_nav_bar);
        greetingsImg = findViewById(R.id.mb_Greetings);
        bizLogo = findViewById(R.id.biz_logo);
        txtName = findViewById(R.id.n_bizBrand);
        txtBizID = findViewById(R.id.id_of_biz);
        txtWalletID = findViewById(R.id.wallet_biz_acct);
        txtBizBalance = findViewById(R.id.balance_BIZ_Balance);
        txtMBCus = findViewById(R.id.mbCus);
        spnAccts = findViewById(R.id.mb_Acct_Spn);
        txtSpnBalance = findViewById(R.id.spn_biz_balance);
        txtTodayNewCus = findViewById(R.id.cus_mbNew_Today);
        txtTodayMessages = findViewById(R.id.mb_support_m_today);
        txtTodayOrder = findViewById(R.id.today_orders_biz);
        bizToolBar = findViewById(R.id.biz_tBar);
        bizLogoHeader = findViewById(R.id.biz_Logo_Header);
        userPix = findViewById(R.id.biz_user_pix);

        txtUserName = findViewById(R.id.biz_username);
        btnRenewSub = findViewById(R.id.renew_subB);

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.biz_nav_view_r);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.biz_office_draw);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        getSupportActionBar().setTitle("Biz BackOffice");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_admin_panel);
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
        dbHelper = new DBHelper(this);
        userProfile = new Profile();
        customer = new Customer();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        bizTypes = userPreferences.getStringSet("BIZ_TYPE", new HashSet<String>());
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID= String.valueOf(userPreferences.getLong("PROFILE_ID", 0));
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        userName=userPreferences.getString("PROFILE_USERNAME", "");
        userPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        userMachine=userPreferences.getString("PROFILE_ROLE", "");
        profileID=userPreferences.getInt("PROFILE_ID", 0);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        setupHeader();
        if(bizTypes !=null){
            Log.v("!!!!!!!!!", "There is Biz Type");

        }else {
            Intent myIntent = new Intent(MarketBizOffice.this, BizTypeSelectAct.class);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mStartBizTypeForResult.launch(new Intent(myIntent));

        }
        if(marketBusiness !=null){
            bizName=marketBusiness.getBizBrandname();
        }
        txtUserName.setText("Brand Name :"+bizName);

        StringBuilder welcomeString = new StringBuilder();


        Calendar calendar = Calendar.getInstance();

        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 5 && timeOfDay < 12) {
            //welcomeString.append(getString(R.string.good_morning));
            greetingsImg.setImageResource(R.drawable.good_morn3);
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            welcomeString.append(getString(R.string.good_afternoon));
            greetingsImg.setImageResource(R.drawable.good_after1);
        } else {
            welcomeString.append(getString(R.string.good_evening));
            greetingsImg.setImageResource(R.drawable.good_even2);
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
                .append("Awajima Biz")
                .append("How are you? ")
                .append(getString(R.string.happy))
                .append(dow);

        btnRenewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MarketBizOffice.this, BizSubQTOptionAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);

            }
        });
        btnRenewSub.setOnClickListener(this::renewSub);


        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.cHome:
                                Intent myIntent = new Intent(MarketBizOffice.this, MarketBizOffice.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.cTimeLine:

                                Intent chat = new Intent(MarketBizOffice.this, MyTimelineAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.cGeneralShop:

                                Intent shop = new Intent(MarketBizOffice.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.cPackageT:

                                Intent pIntent = new Intent(MarketBizOffice.this, PackListTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.cSupport:
                                Intent helpIntent = new Intent(MarketBizOffice.this, CustomerHelpActTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(helpIntent);
                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
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
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
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
                Intent p1Intent = new Intent(this, UserTimeLineAct.class);
                p1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(p1Intent);
                return true;
            case R.id.my_subscriptions:
                Intent so1Intent = new Intent(this, PackageTab.class);
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

            case R.id.all_new:
                Intent wIntent = new Intent(this, NewManualPackCusAct.class);
                wIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(wIntent);
                return true;


            case R.id.privacy_policy_customer:
                Intent w1Intent = new Intent(this, PrivacyPolicy_Web.class);
                w1Intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(w1Intent);
                return true;
            case R.id.admin_pswd2:
                Intent impIntent = new Intent(this, PasswordRecovAct.class);
                impIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
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
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(loginIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showDrawerButton() {

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
    private void setupHeader() {
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
        dbHelper = new DBHelper(this);
        userProfile = new Profile();
        customer = new Customer();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        bizTypes = userPreferences.getStringSet("BIZ_TYPE", new HashSet<String>());
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID= String.valueOf(userPreferences.getLong("PROFILE_ID", 0));
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);
        userName=userPreferences.getString("PROFILE_USERNAME", "");
        userPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        userMachine=userPreferences.getString("PROFILE_ROLE", "");
        profileID=userPreferences.getInt("PROFILE_ID", 0);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);

        gson = new Gson();
        gson1 = new Gson();
        json = userPreferences.getString("LastCustomerUsed", "");
        customer = gson.fromJson(json, Customer.class);
        userPicture= Uri.parse(userPreferences.getString("PICTURE_URI", ""));


        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.biz_nav_view_r);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = findViewById(R.id.biz_office_draw);
        bizToolBar = findViewById(R.id.biz_tBar);
        bizLogoHeader = findViewById(R.id.biz_Logo_Header);
        userPix = findViewById(R.id.biz_user_pix);
        txtUserName = findViewById(R.id.biz_username);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        txtUserName.setText(userName);
        if(marketBusiness !=null){
            bizName=marketBusiness.getBizBrandname();
            bizImage=marketBusiness.getBizPicture();
            Objects.requireNonNull(getSupportActionBar()).setTitle(bizName+" Biz BackOffice");

        }else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Biz BackOffice");
        }
        getSupportActionBar().setElevation(-90);

        bizToolBar.setTitle(bizName);

        View headerView = navigationView.getHeaderView(0);
        btnRenewSub = headerView.findViewById(R.id.renew_subB);

        try {
            Glide.with(MarketBizOffice.this)
                    .asBitmap()
                    .load(bizLogo)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_alert)
                    //.listener(listener)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .centerCrop()
                    .into(bizLogoHeader);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        btnRenewSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MarketBizOffice.this, BizSubQTOptionAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);

            }
        });

        ProfDAO applicationDb = new ProfDAO(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        try {
            Glide.with(MarketBizOffice.this)
                    .asBitmap()
                    .load(userPicture)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .error(R.drawable.ic_alert)
                    //.listener(listener)
                    .skipMemoryCache(true)
                    .fitCenter()
                    .centerCrop()
                    .into(userPix);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        Currency currency=null;
        String acctCurrency=null;
        /*if(customer !=null){
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
                balance.setText(acctCurrency + accountBalance);
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


            if(accountBalance>0){
                balance.setText(MessageFormat.format(acctCurrency, accountBalance));

            }if (accountBalance <0) {
                balance.setText(MessageFormat.format(acctCurrency, accountBalance));

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
            if (savings == 0) {
                textSavings.setText("You dont have any savings yet");

            }

            //name = customerProfile.getFirstName() + " " + customerProfile.getSurname();
            textSavings.setText(MessageFormat.format("Saving:{0}", savings));
            textID.setText(MessageFormat.format("User Id:{0}", customerID));
            txtUserName.setText(MessageFormat.format("{0}{1}", cusUserName));


        }*/

    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.nav_biz_d:

                Intent intent = new Intent(MarketBizOffice.this, MarketBizOffice.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.biz_timeline:
                Intent profile = new Intent(MarketBizOffice.this, BizDealTimeLineAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                profile.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                profile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(profile);
                break;
            case R.id.nav_biz_cus:
                Intent active = new Intent(MarketBizOffice.this, CusDocCodeSavingsAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                active.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(active);
                break;
            case R.id.nav_suppliers_biz:
                Intent history = new Intent(MarketBizOffice.this, CusPackForPayment.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                history.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                history.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(history);
                break;

            case R.id.nav_inv_biz:
                Intent chat = new Intent(MarketBizOffice.this, MarketInvTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chat);
                break;

            case R.id.nav_regulators_biz:
                Intent supportInt = new Intent(MarketBizOffice.this, BizRegulOffice.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(supportInt);
                break;
            case R.id.nav_dep_cash:
                Intent intPref = new Intent(MarketBizOffice.this, UserPrefActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intPref.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intPref.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intPref);
                break;
            case R.id.nav_biz_Subscriptions:
                Intent intSO = new Intent(MarketBizOffice.this, SubRecordAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                intSO.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intSO.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intSO);
                break;
            case R.id.nav_Grp_Savings:
                Intent pIntent = new Intent(MarketBizOffice.this, MyGrpSavingsTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pIntent);
                break;

            case R.id.nav_biz_t:
                Intent lIntent = new Intent(MarketBizOffice.this, CusLoanRepaymentAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                lIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lIntent);
                break;
            case R.id.loan_nav_biz:
                Intent loanIntent = new Intent(MarketBizOffice.this, CusLoanTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loanIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loanIntent);
                break;
            case R.id.nav_grant_biz:
                Intent uIntent = new Intent(MarketBizOffice.this, CustUtilTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                uIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(uIntent);
                break;

            case R.id.nav_deals_biz:
                Intent dealIntent = new Intent(MarketBizOffice.this, BizDealTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                dealIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                dealIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dealIntent);
                break;


            case R.id.nav_workers:
                Intent workersIntent = new Intent(MarketBizOffice.this, MarketBizPOffice.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                workersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                workersIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(workersIntent);
                break;

            case R.id.nav_branches_B:
                Intent officeIntent = new Intent(MarketBizOffice.this, BranchReportTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                officeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                officeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(officeIntent);
                break;


            case R.id.nav_report_Emerg:
                Intent emegIntent = new Intent(MarketBizOffice.this, AwajimaReportAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                emegIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                emegIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(emegIntent);
                break;

            case R.id.nav_biz_Type_settings:
                Intent typeIntent = new Intent(MarketBizOffice.this, CustUtilTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                typeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                typeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(typeIntent);
                break;

            case R.id.nav_inventory_b:
                Intent inventoryInt = new Intent(MarketBizOffice.this, MarketInvTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                inventoryInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                inventoryInt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inventoryInt);
                break;
            case R.id.nav_market_biz:
                Intent marketIntent = new Intent(MarketBizOffice.this, MarketBizPOffice.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                marketIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                marketIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(marketIntent);
                break;

            case R.id.nav_support_biz:
                Intent supportIntent = new Intent(MarketBizOffice.this, CustomerHelpActTab.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                supportIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                supportIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(supportIntent);
                break;


            case R.id.nav_biz_logout:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(MarketBizOffice.this, SignTabMainActivity.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                finish();
                break;
        }
        return true;

    }

    private void setupDrawerListener() {
        drawer = findViewById(R.id.cus_drawer2);
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

    public void renewSub(View view) {
    }
}