package com.skylightapp.Markets;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Customers.SavingsSOAct;
import com.skylightapp.Customers.StandingOrderList;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirAct;
import com.skylightapp.LogisticDetailsAct;
import com.skylightapp.MapAndLoc.HotEmergAct;
import com.skylightapp.MapAndLoc.UserReportEmergAct;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class MarketTab extends TabActivity implements NavigationView.OnNavigationItemSelectedListener {
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    int profileUID2;
    int id;
    String userName;
    String password;
    Customer customer;
    Bundle getBundle;
    String name;
    FloatingActionButton fab;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,machine;
    int profileUID;
    Bundle bundle;
    PrefManager prefManager;
    private static final String PREF_NAME = "awajima";
    private ChipNavigationBar chipNavigationBar;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Account account;
    private ActionBarDrawerToggle toggle;
    private String surname,firstName,names,cusUserName;
    ImageButton hideLayouts;
    private int accountN, savings,skPackages;
    private double accountBalance;
    private AppCompatTextView txtBankAcctOwner, txtUserBankName;
    AppCompatButton txtBankNo;
    CircleImageView imgProfilePic;
    Bundle homeBundle;
    private  int SOCount,customerID,loanCount,txCount;
    String SharedPrefUserPassword,SharedPrefBankAcctNo,SharedPrefBankName,SharedPrefCusID,SharedPrefNirsal,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_tab_drawer);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        setTitle("Market Place");
        TabWidget tabs = findViewById(android.R.id.tabs);
        txtUserBankName = findViewById(R.id.bank_Acct_);
        txtBankNo = findViewById(R.id.cust_Nirsal_No);
        txtBankAcctOwner = findViewById(R.id.bank_owner_);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        dbHelper= new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        homeBundle=new Bundle();
        userProfile=new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        SharedPrefNirsal=userPreferences.getString("ACCOUNT_BANK", "");
        SharedPrefBankName=userPreferences.getString("ACCOUNT_NAME", "");
        SharedPrefBankAcctNo=userPreferences.getString("BANK_ACCT_NO", "");

        try {

            txtUserBankName.setText(SharedPrefNirsal);
            txtBankNo.setText(SharedPrefBankAcctNo);
            txtBankAcctOwner.setText(SharedPrefBankName);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        bundle = new Bundle();
        if(userProfile !=null){
            machine=userProfile.getProfileMachine();
            profileUID=userProfile.getPID();

        }
        floatingActionButton = findViewById(R.id.fab_market);
        fab = findViewById(R.id.fabGenHome);

        ArcNavigationView navigationView = (ArcNavigationView) findViewById(R.id.market_arc);
        navigationView.setNavigationItemSelectedListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(homeBundle !=null){
                    homeBundle.putParcelable("Profile",userProfile);
                }
                Intent hIntent = new Intent(MarketTab.this, NewCustomerDrawer.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                hIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                hIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                hIntent.putExtras(homeBundle);
                startActivity(hIntent);
            }
        });
        drawer = findViewById(R.id.market_drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_good));
        toggle.setHomeAsUpIndicator(R.drawable.home2);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        Intent intentMarkets = new Intent().setClass(this, MarketHub.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecMarketBiz = tabhost
                .newTabSpec("African Market")
                .setIndicator("", resources.getDrawable(R.drawable.ic_admin_panel))
                .setContent(intentMarkets);

        Intent intentProducts = new Intent().setClass(this, SavingsSOAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSavings = tabhost
                .newTabSpec("Savings")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentProducts);

        Intent intentMarketHub = new Intent().setClass(this, MarketCreatorAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabNewMarket = tabhost
                .newTabSpec("New Market")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentMarketHub);

        Intent intentLogistics = new Intent().setClass(this, LogisticDetailsAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogistics = tabhost
                .newTabSpec("Logistics Park")
                .setIndicator("", resources.getDrawable(R.drawable.ic_admin_panel))
                .setContent(intentLogistics);
        tabhost.addTab(tabSpecMarketBiz);
        tabhost.addTab(tabSpecSavings);
        tabhost.addTab(tabSpecLogistics);
        tabhost.addTab(tabNewMarket);


        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAccount(profileUID,machine,userProfile);
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(MarketTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });
        chipNavigationBar = findViewById(R.id.bottom_nav_Market);
        chipNavigationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.mHome:
                                Intent myIntent = new Intent(MarketTab.this, LoginDirAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.market_biz_deals:

                                Intent myDealIntent = new Intent(MarketTab.this, MyBizDealAct.class);
                                startActivity(myDealIntent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myDealIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myDealIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            case R.id.my_market_Book:

                                Intent myCashIntent = new Intent(MarketTab.this, MyCashAct.class);
                                startActivity(myCashIntent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myCashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myCashIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            case R.id.my_market_inventory:

                                Intent myStocksIntent = new Intent(MarketTab.this, MyMStocksListAct.class);
                                startActivity(myStocksIntent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myStocksIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myStocksIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            case R.id.market_prod:

                                Intent myProdIntent = new Intent(MarketTab.this, ProductsAct.class);
                                startActivity(myProdIntent);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myProdIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myProdIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            case R.id.my_marketFunding:

                                Intent fundingIntent = new Intent(MarketTab.this, MyFundingTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                fundingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                fundingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(fundingIntent);

                        }
                        /*getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container,
                                        fragment).commit();*/
                    }
                });


    }
    public void homeAccount(long profileUID, String machine, Profile userProfile) {
        bundle.putLong("ProfileID", profileUID);
        bundle.putString(machine, machine);
        bundle.putParcelable("Profile",userProfile);
        Intent intent = new Intent(this, UserReportEmergAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.putExtra(PROFILE_ID, profileUID);
        intent.putExtras(bundle);
        startActivity(intent);


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.market_dashboard:
                Intent intent = new Intent(MarketTab.this, MarketTab.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(intent);
                break;
            case R.id.emerg_marketM:
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                Intent emergIntent = new Intent(MarketTab.this, UserReportEmergAct.class);
                emergIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(emergIntent);
                break;
            case R.id.market_Hot:
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                Intent hotIntent = new Intent(MarketTab.this, HotEmergAct.class);
                hotIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(hotIntent);
                break;
            case R.id.market_insu:
                Intent active = new Intent(MarketTab.this, InsuranceAct.class);
                active.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(active);
                break;
            case R.id.market_levy:
                Intent history = new Intent(MarketTab.this, MarketLevyAct.class);
                history.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(history);
                break;

            case R.id.market_courses:
                Intent chat = new Intent(MarketTab.this, MarketLearningAct.class);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(chat);
                break;

            case R.id.market_investment:
                Intent invIntent = new Intent(MarketTab.this, MarketInvTab.class);
                invIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(invIntent);
                break;



            case R.id.market_funding:
                Intent supportInt = new Intent(MarketTab.this, MyFundingTab.class);
                supportInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(supportInt);
                break;
            case R.id.market_Logistics:
                Intent intLogistics = new Intent(MarketTab.this, LogisticsTab.class);
                intLogistics.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(intLogistics);
                break;

            case R.id.market_settings:
                Intent logisticsIntent = new Intent(MarketTab.this, StockSettingAct.class);
                logisticsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(logisticsIntent);
                break;

            case R.id.market_logout:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Intent loginIntent = new Intent(MarketTab.this, SignTabMainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(loginIntent);
                finish();
                break;
        }
        return true;

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

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

}