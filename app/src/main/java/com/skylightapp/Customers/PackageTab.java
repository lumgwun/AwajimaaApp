package com.skylightapp.Customers;

import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirectorActivity;
import com.skylightapp.R;

import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class PackageTab extends TabActivity {
    FloatingActionButton floatingActionButton;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,machine;
    Profile userProfile;
    long profileUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_package_tab);
        userProfile=new Profile();
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_HomeTabPacks);
        if (userProfile != null) {
            profileUID = userProfile.getPID();



        }
        Resources resources = getResources();
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        tabHost.setup(getLocalActivityManager());

        Intent intentSignUp = new Intent().setClass(this, OldPackCusAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecOld = tabHost
                .newTabSpec("Old Packs")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignUp);




        Intent intentNew = new Intent().setClass(this, NewPackCusAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecNew = tabHost
                .newTabSpec("New Pack")
                .setIndicator("", resources.getDrawable(R.drawable.ic_add_business))
                .setContent(intentNew);



        Intent intentPack = new Intent().setClass(this, CustomerPackForPayment.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecPP = tabHost
                .newTabSpec("Unpaid Pack")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentPack);


        tabHost.addTab(tabSpecOld);
        tabHost.addTab(tabSpecNew);
        tabHost.addTab(tabSpecPP);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpHome();

            }
        });


    }
        public  void helpHome(){
            userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
            gson = new Gson();
            userProfile=new Profile();
            json = userPreferences.getString("LastProfileUsed", "");
            userProfile = gson.fromJson(json, Profile.class);
            Bundle bundle = new Bundle();
            if(userProfile !=null){
                machine=userProfile.getProfileMachine();
                profileUID=userProfile.getPID();

            }

            bundle.putLong("ProfileID", profileUID);
            bundle.putString(machine, machine);
            Intent intent = new Intent(this, LoginDirectorActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(PROFILE_ID, profileUID);
            intent.putExtras(bundle);
            startActivity(intent);



        }
}
