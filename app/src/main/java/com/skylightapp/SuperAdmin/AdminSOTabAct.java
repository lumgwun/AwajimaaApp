package com.skylightapp.SuperAdmin;

import androidx.preference.PreferenceManager;

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
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Admins.SOCompletedList;
import com.skylightapp.Admins.SODueDateListAct;
import com.skylightapp.Admins.SOListAct;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

public class AdminSOTabAct extends TabActivity {
    FloatingActionButton fab;
    Profile userProfile;
    long profileID;
    long id;
    String userName;
    String password,machine;
    Bundle getBundle;
    String name;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_sotab);

        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        fab = findViewById(R.id.fab_SOTab);
        Intent intentSignUp = new Intent().setClass(this, SOListAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSOList = tabhost
                .newTabSpec("Standing Orders")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentSignUp);

        Intent intentSignIn = new Intent().setClass(this, SOCompletedList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSOCompleted = tabhost
                .newTabSpec("Completed SO")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentSignIn);

        Intent intentSO = new Intent().setClass(this, SODueDateListAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSODueDate = tabhost
                .newTabSpec("Due SO")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentSO);


        tabhost.addTab(tabSpecSOList);
        tabhost.addTab(tabSpecSOCompleted);
        tabhost.addTab(tabSpecSODueDate);

        tabhost.setCurrentTab(0);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeBD();
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(AdminSOTabAct.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void homeBD() {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putString(machine, machine);
        bundle.putParcelable("User",userProfile);
        if(userProfile !=null){
            profileID=userProfile.getPID();

        }
        bundle.putLong("ProfileID", profileID);
        Intent intent = new Intent(this, SuperAdminOffice.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}