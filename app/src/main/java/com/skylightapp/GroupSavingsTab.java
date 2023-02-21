package com.skylightapp;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MapAndLoc.AwajimaReportAct;
import com.skylightapp.Markets.MarketTab;

public class GroupSavingsTab extends TabActivity {

    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json;
    Profile userProfile;
    long profileUID;
    private static final String PREF_NAME = "awajima";
    private ChipNavigationBar chipNavigationBar;
    private com.google.android.material.floatingactionbutton.FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_group_savings_tab);
        chipNavigationBar = findViewById(R.id.gsavings_nav_bar);
        floatingActionButton=findViewById(R.id.gs_fab_);
        userProfile= new Profile();
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpHome();

            }
        });


        Resources resources = getResources();
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
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


        Intent intentMy = new Intent().setClass(this, GrpSavingsOptionAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecMy = tabHost
                .newTabSpec("Fresh Savings")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentMy);


        tabHost.addTab(tabSpecList);
        tabHost.addTab(tabSpecGrp);
        tabHost.addTab(tabSpecMy);

        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        //Fragment fragment = null;
                        switch (i){
                            case R.id.gsavings_Em:
                                Intent myIntent = new Intent(GroupSavingsTab.this, AwajimaReportAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(myIntent);

                            case R.id.gSavings_Tranx:
                                Intent pIntent = new Intent(GroupSavingsTab.this, GrpProfileTraxs.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                pIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(pIntent);

                            case R.id.gSavings_TimeL:

                                Intent chat = new Intent(GroupSavingsTab.this, MyTimelineAct.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                chat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(chat);


                            case R.id.gs_marketT:

                                Intent shop = new Intent(GroupSavingsTab.this, MarketTab.class);
                                overridePendingTransition(R.anim.slide_in_right,
                                        R.anim.slide_out_left);
                                shop.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                shop.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(shop);




                            case R.id.gSavings_Support:
                                Intent helpIntent = new Intent(GroupSavingsTab.this, CustomerHelpActTab.class);
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
    public  void helpHome(){
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Profile", profileUID);
        startActivity(intent);


    }

}