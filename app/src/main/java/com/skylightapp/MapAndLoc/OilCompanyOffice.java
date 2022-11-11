package com.skylightapp.MapAndLoc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;

import com.blongho.country_data.Country;
import com.blongho.country_data.Currency;
import com.blongho.country_data.World;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Adapters.CurrAdapter;
import com.skylightapp.BizSubQTOptionAct;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Customers.PackListTab;
import com.skylightapp.HQLogisticsOffice;
import com.skylightapp.MarketClasses.MarketBizSubScription;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.MyTimelineAct;
import com.skylightapp.R;
import com.skylightapp.SubScriptionTab;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OilCompanyOffice extends AppCompatActivity {
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "HQLogisticsOffice.KEY";
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
    private Currency currency;
    private String currencySymbol;
    private List<Country> countries;
    private List<Currency> currencies;
    private World world;
    private CurrAdapter currencyAdapter;
    private Customer customer;
    private Account account;
    private ArrayList<Account> accountArrayList;
    private ArrayList<MarketBizSubScription> marketBizSubScriptions;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_oil_company_office);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        chipNavigationBar = findViewById(R.id.oilCom_nav_bar);
        fab = findViewById(R.id.fab_oC);
        gson = new Gson();
        gson1 = new Gson();
        customer= new Customer();
        userProfile= new Profile();
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

        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.oil_Home:
                                Intent myIntent = new Intent(OilCompanyOffice.this, OilCompanyOffice.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.OIL_Reports:

                                Intent chat = new Intent(OilCompanyOffice.this, OurSpReportAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.oil_done:

                                Intent shop = new Intent(OilCompanyOffice.this, TreatedReportAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.oil_sub:

                                Intent pIntent = new Intent(OilCompanyOffice.this, SubScriptionTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.oil_messages:
                                Intent helpIntent = new Intent(OilCompanyOffice.this, CustomerHelpActTab.class);
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
}