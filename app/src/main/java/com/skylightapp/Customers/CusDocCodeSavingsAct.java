package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.WindowManager;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.skylightapp.Classes.CubeOutDepthTransformation;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.LoginDirAct;
import com.skylightapp.R;
import com.twilio.Twilio;

public class CusDocCodeSavingsAct extends AppCompatActivity implements MySavingsCodesFrag.OnFragmentInteractionListener,MySavingsFragment.OnFragmentInteractionListener {
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson;
    private String json;

    private Profile userProfile;
    private long profileID;
    CustomerTabAdapter adapter;
    FloatingActionButton fab;
    private static final String PREF_NAME = "awajima";
    Gson gson1;
    String  json1, localityString,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_cus_pack);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile= new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new CustomerTabAdapter(fragmentManager, getLifecycle());
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        //fab = findViewById(R.id.fabPackCus);
        ViewPager2 viewPager2 = findViewById(R.id.PagerP);
        TabLayout tabLayout = findViewById(R.id.tabsP);
        tabLayout.addTab(tabLayout.newTab().setText("Docs"));
        tabLayout.addTab(tabLayout.newTab().setText("Code"));
        tabLayout.setTabTextColors(Color.RED,Color.BLUE);
        tabLayout.addTab(tabLayout.newTab().setText("Savs"));
        viewPager2.setAdapter(adapter);
        tabLayout.getTabCount();
        viewPager2.setPageTransformer(new CubeOutDepthTransformation());
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(" " + (position + 1))
        ).attach();

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setIcon(R.drawable.selected_indicator)
        ).attach();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        /*fab.setOnClickListener(view ->

                        Snackbar.make(view, "Dashboard called", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show());
        takeHome();*/


    }

    public void takeHome() {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        gson = new Gson();
        userProfile= new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Profile", (Parcelable) userProfile);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showUpButton() {

    }


}