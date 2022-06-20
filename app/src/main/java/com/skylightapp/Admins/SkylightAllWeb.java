package com.skylightapp.Admins;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.OfficialEmailAct;
import com.skylightapp.R;
import com.skylightapp.Tellers.StoreManagerWeb;
import com.skylightapp.Tellers.WPAdminActivity;

public class SkylightAllWeb extends TabActivity {
    FragmentManager fragmentManager;
    public static final String USER_ID_EXTRA_KEY = "SkylightAllWeb.USER_ID_EXTRA_KEY";
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson;
    private String json;

    private Profile userProfile;
    private String profileID;
    Intent data;
    FloatingActionButton floatingActionButton,floatingActionButton2;
    private ActionBar toolbar;
    private ImageView imgTime;
    private TextView txtWelcome;
    private TextView txtMessage;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_skylight_all_web);
        floatingActionButton = findViewById(R.id.fab_admin_Home_w);
        floatingActionButton2 = findViewById(R.id.fab_admin_packs_w);
        Resources resources = getResources();
        TabWidget tabs = findViewById(android.R.id.tabs);
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup();
        Toolbar toolbar = (Toolbar) findViewById(R.id.web_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_admin_web);
        Intent webEmail = new Intent().setClass(this, OfficialEmailAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec email = tabhost
                .newTabSpec("Email")
                .setIndicator("", resources.getDrawable(R.drawable.web_mail))
                .setContent(webEmail);

        Intent intentWebHosting = new Intent().setClass(this, WebHostingAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecHosting = tabhost
                .newTabSpec("Hosting")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentWebHosting);

        Intent intentWebShop = new Intent().setClass(this, StoreManagerWeb.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabWebStore = tabhost
                .newTabSpec("Store")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentWebShop);

        Intent intentWebAdmin = new Intent().setClass(this, WPAdminActivity.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabAdmin = tabhost
                .newTabSpec("Web Admin")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentWebAdmin);

        Intent intentTwillo = new Intent().setClass(this, TwilloConsole.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabTwillo = tabhost
                .newTabSpec("SMS Console")
                .setIndicator("", resources.getDrawable(R.drawable.web_hosting))
                .setContent(intentTwillo);



        tabhost.addTab(email);
        tabhost.addTab(tabSpecHosting);
        tabhost.addTab(tabWebStore);
        tabhost.addTab(tabAdmin);
        tabhost.addTab(tabTwillo);

        tabhost.setCurrentTab(0);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminHomeHistory();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminSkyPackage();
            }
        });


    }


    public void adminHomeHistory() {

        Intent intent = new Intent(this, AdminHomeChoices.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, getString(R.string.teller_dashboard_taking), Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

    public void adminSkyPackage() {

        Intent intent = new Intent(this, SkylightHistoryAct.class);
        Toast.makeText(this, "Taking you to the Customer area", Toast.LENGTH_LONG).show();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileID);
        startActivity(intent);

    }
}