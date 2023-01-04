package com.skylightapp.StateDir;

import static com.skylightapp.Classes.Profile.PROFILE_ID;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CusMessageAct;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class StateDashboard extends TabActivity {
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    Gson gson1,gson2;
    String json1,json2;
    String json,machine,SharedPrefUserName,SharedPrefUserMachine,SharedPrefUserPassword;
    int profileUID;
    Bundle bundle;
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "StateDashboard.KEY";
    ChipNavigationBar chipNavigationBar;
    GridLayout maingrid;
    PrefManager prefManager;
    private int profileID,SharedPrefCusID,SharedPrefProfileID;
    CircleImageView profileImage;
    private Profile profile;
    private Customer customer;
    private Account account;
    private State state;
    private FloatingActionButton fabG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_state_dashboard);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        setTitle("State Office Board");
        TabWidget tabs = findViewById(android.R.id.tabs);
        chipNavigationBar = findViewById(R.id.state_nav_bar);
        fabG = findViewById(R.id.fab_state_);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        bundle = new Bundle();

        floatingActionButton = findViewById(R.id.fab_cus_State);
        fabG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StateDashboard.this, StateDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                startActivity(intent);

            }
        });
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        customer=new Customer();
        account=new Account();
        gson2 = new Gson();
        userProfile=new Profile();
        state= new State();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        json2 = userPreferences.getString("LastStateUsed", "");
        state = gson2.fromJson(json2, State.class);
        if(userProfile !=null){
            machine=userProfile.getProfileMachine();
            profileUID=userProfile.getPID();

        }
        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.state_Home:
                                Intent myIntent = new Intent(StateDashboard.this, StateDashboard.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.state_min:

                                Intent chat = new Intent(StateDashboard.this, StateMinAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.m_state_market:

                                Intent shop = new Intent(StateDashboard.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);

                            case R.id.cus_state_Prof:

                                Intent pIntent = new Intent(StateDashboard.this, NewCustomerDrawer.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);


                            case R.id.state_support:
                                Intent helpIntent = new Intent(StateDashboard.this, CusMessageAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                helpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(helpIntent);
                        }

                    }
                });
        Intent intentSignUp = new Intent().setClass(this, StateEnvResponseAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Responses")
                .setIndicator("", resources.getDrawable(R.drawable.ic_gps_fixed))
                .setContent(intentSignUp);

        Intent intentSignIn = new Intent().setClass(this, StateEnvReportsAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("Reports")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignIn);

        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);

        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoverAccount(profileUID,machine);
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(StateDashboard.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void recoverAccount(long profileUID,String machine) {
        bundle.putLong("ProfileID", profileUID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PROFILE_ID, profileUID);
        intent.putExtras(bundle);
        startActivity(intent);


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
    public void getStateWasteMgt(View view) {
        Intent intent=new Intent(StateDashboard.this, WasteCollectionAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }
    public void getWasteCollectTX(View view) {
        Intent intent=new Intent(StateDashboard.this, WastCollecTranxAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }
    public void getStateAnnouns(View view) {
        Intent intent=new Intent(StateDashboard.this, StateDashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        startActivity(intent);
    }
}