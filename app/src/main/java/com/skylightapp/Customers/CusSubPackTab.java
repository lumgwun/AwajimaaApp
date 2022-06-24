package com.skylightapp.Customers;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirectorActivity;
import com.skylightapp.R;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PASSWORD;

public class CusSubPackTab extends TabActivity {
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson,gson1;
    String json,json1;
    Profile userProfile;
    long profileUID11;
    Customer customer;
    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserName,SharedPrefUserMachine,
            SharedPrefProfileID;
    FloatingActionButton fHome;
    Bundle packBundle;
    long customerID;
    Bundle paymentBundle;
    private static final String PREF_NAME = "skylight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_sub_pack);
        userProfile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        packBundle=new Bundle();
        json = userPreferences.getString("LastProfileUsed", "");
        SharedPrefUserName=userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=userPreferences.getString(PROFILE_PASSWORD, "");
        SharedPrefCusID=userPreferences.getString(CUSTOMER_ID, "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getString(PROFILE_ID, "");
        userProfile = gson.fromJson(json, Profile.class);
        packBundle = getIntent().getExtras();
        if(packBundle !=null){
            customer=packBundle.getParcelable("Customer");

        }
        else {
            if(userProfile !=null){
                customer=userProfile.getProfileCus();
                profileUID11 = userProfile.getPID();

            }

            if (customer !=null){
                customerID = customer.getCusUID();

            }

        }

        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        fHome = findViewById(R.id.fab_SubTab);


        Intent pack2Intent = new Intent().setClass(this, CustomerPackForPayment.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpec2 = tabhost
                .newTabSpec("Pack For Payment")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(pack2Intent);

        Intent pack3Intent = new Intent().setClass(this, CusPackOverviewAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecOverview = tabhost
                .newTabSpec("Pack Overview")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(pack3Intent);

        tabhost.addTab(tabSpec2);
        tabhost.addTab(tabSpecOverview);


        fHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpHome();

            }
        });


    }

    public void helpHome() {
        Intent intent = new Intent(this, LoginDirectorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", (Parcelable) userProfile);
        intent.putExtra("Profile", (Parcelable) userProfile);
        intent.putExtra("Customer", (Parcelable) customer);
        intent.putExtra("Machine", SharedPrefUserMachine);
        intent.putExtra(PROFILE_ID, SharedPrefProfileID);
        intent.putExtra("ProfileID", SharedPrefProfileID);

        startActivity(intent);

    }


}