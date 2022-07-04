package com.skylightapp;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;

public class GroupSavingsTab extends TabActivity {

    FloatingActionButton floatingActionButton;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json;
    Profile userProfile;
    long profileUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_group_savings_tab);
        FloatingActionButton floatingActionButton=findViewById(R.id.icon_tabGS);
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        Resources resources = getResources();
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.gs_toolbar);
        tabHost.setup(getLocalActivityManager());

        if (userProfile != null) {
            profileUID= userProfile.getPID();
        }

        Intent intentSignUp = new Intent().setClass(this, GroupSavingsAcctList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecList = tabHost
                .newTabSpec("Group Savings")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignUp);


        Intent intentSignIn = new Intent().setClass(this, MyGrpSavingsList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecGrp = tabHost
                .newTabSpec("My GS")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentSignIn);


        Intent intentMy = new Intent().setClass(this, MyNewGrpSavings.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecMy = tabHost
                .newTabSpec("New Grp.")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentMy);



        tabHost.addTab(tabSpecList);
        tabHost.addTab(tabSpecGrp);
        tabHost.addTab(tabSpecMy);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpHome();

            }
        });


    }
    public  void helpHome(){
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Profile", profileUID);
        startActivity(intent);


    }

}