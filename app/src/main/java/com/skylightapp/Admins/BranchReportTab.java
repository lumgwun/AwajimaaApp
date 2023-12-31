package com.skylightapp.Admins;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirAct;
import com.skylightapp.R;

import static com.skylightapp.Classes.Profile.PROFILE_ID;

public class BranchReportTab extends TabActivity {
    private AppBarLayout appBarLayout;
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    long profileUID2;
    long id;
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
    String currentDate;
    Gson gson1,gson2;
    String json1,json2,officeBranch;
    private AdminUser adminUser;
    private static final String PREF_NAME = "awajima";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_branch_report_tab);
        setTitle("Branch Reports");
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        userProfile = new Profile();
        adminUser = new AdminUser();
        json2 = userPreferences.getString("LastAdminUserUsed", "");
        json = userPreferences.getString("LastProfileUsed", "");
        adminUser = gson2.fromJson(json, AdminUser.class);
        userProfile = gson.fromJson(json, Profile.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        officeBranch=userPreferences.getString("PROFILE_OFFICE","");
        bundle = new Bundle();
        if(userProfile !=null){
            machine=userProfile.getProfileMachine();
            profileUID=userProfile.getPID();

        }
        floatingActionButton = findViewById(R.id.fab_adminHome);
        Intent intentSignUp = new Intent().setClass(this, TellerReportBranchToday.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Report For Today")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(intentSignUp);

        Intent intentSignIn = new Intent().setClass(this, TellerReportBranchAll.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("All Branch Report")
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
                Toast.makeText(BranchReportTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void recoverAccount(int profileUID,String machine) {
        bundle.putLong("ProfileID", profileUID);
        bundle.putString(machine, machine);
        bundle.putString("machine", machine);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(PROFILE_ID, profileUID);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}