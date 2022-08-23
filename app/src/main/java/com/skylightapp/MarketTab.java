package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;

import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class MarketTab extends TabActivity {
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    int profileUID2;
    int id;
    String userName;
    String password;
    Customer customer;
    Bundle getBundle;
    String name;
    com.github.clans.fab.FloatingActionButton fab;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,machine;
    int profileUID;
    Bundle bundle;
    private static final String PREF_NAME = "skylight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_market_tab);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        setTitle("Market Place");
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile=new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        bundle = new Bundle();
        if(userProfile !=null){
            machine=userProfile.getProfileMachine();
            profileUID=userProfile.getPID();

        }
        floatingActionButton = findViewById(R.id.fab_market);
        Intent intentProducts = new Intent().setClass(this, ProductsAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("General Shop")
                .setIndicator("", resources.getDrawable(R.drawable.ic_create_new))
                .setContent(intentProducts);

        Intent intentMarketHub = new Intent().setClass(this, MarketHub.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecHub = tabhost
                .newTabSpec("Market Hub")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentMarketHub);

        Intent intentLogistics = new Intent().setClass(this, LogisticParkAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogistics = tabhost
                .newTabSpec("Logistics Park")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentLogistics);

        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecHub);
        tabhost.addTab(tabSpecLogistics);

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
                Toast.makeText(MarketTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void recoverAccount(long profileUID,String machine) {
        bundle.putLong("ProfileID", profileUID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PROFILE_ID, profileUID);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}