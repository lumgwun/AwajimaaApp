package com.skylightapp.MapAndLoc;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TabHost;

import com.blongho.country_data.Country;
import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.CurrAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.ClimateCRepAct;
import com.skylightapp.ClimateCRespAct;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.NewCustomerDrawer;

import com.skylightapp.MarketClasses.MarketBizSub;
import com.skylightapp.Markets.MarketTab;

import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClimateCOffice extends TabActivity {
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "ClimateCOffice.KEY";
    ChipNavigationBar chipNavigationBar;
    GridLayout maingrid;
    Button btnUtility, btnOurPrivacyPolicy,btnInvestment;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    private int profileID;
    CircleImageView profileImage;
    private Profile profile;
    private Intent data;
    private FloatingActionButton fActionButton;
    private Customer customer;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private Account account;
    private Currency currency;
    private String currencySymbol;
    private List<Country> countries;
    private List<Currency> currencies;
    private World world;
    private CurrAdapter currencyAdapter;
    private ArrayList<Account> accountArrayList;
    private ArrayList<MarketBizSub> marketBizSubs;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;
    CardView cardViewPackges,cardViewGrpSavings,cardViewHistory, cardViewStandingOrders, cardViewOrders, cardViewSupport;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_climate_c_office);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        chipNavigationBar = findViewById(R.id.chip_climate);
        fab = findViewById(R.id.fab_cc);
        fActionButton = findViewById(R.id.ic_cc_fab);

        gson = new Gson();
        World.init(this);
        countries=new ArrayList<>();
        gson1 = new Gson();
        countries=World.getAllCountries();
        currencies= new ArrayList<>();
        //currency= new Currency();
        userProfile=new Profile();
        customer=new Customer();
        account=new Account();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        fActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        Resources resources = getResources();
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(getLocalActivityManager());

        Intent intentsupport = new Intent().setClass(this, ClimateCRepAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabUserSupport = tabhost
                .newTabSpec("Climate Change Reports")
                .setIndicator("", resources.getDrawable(R.drawable.user3))
                .setContent(intentsupport);

        Intent intentTransactions= new Intent().setClass(this, ClimateCRespAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabTransaction = tabhost
                .newTabSpec("Climate Change Response")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentTransactions);

        tabhost.addTab(tabUserSupport);
        tabhost.addTab(tabTransaction);

        tabhost.setCurrentTab(2);
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.home_climateC:
                                Intent myIntent = new Intent(ClimateCOffice.this, ClimateCOffice.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.oilSpillage_cc:

                                Intent chat = new Intent(ClimateCOffice.this, ClimateCRespAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.climate_M:

                                Intent shop = new Intent(ClimateCOffice.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.cus_climate:

                                Intent pIntent = new Intent(ClimateCOffice.this, NewCustomerDrawer.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.support_climate:
                                Intent helpIntent = new Intent(ClimateCOffice.this, CustomerHelpActTab.class);
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
}