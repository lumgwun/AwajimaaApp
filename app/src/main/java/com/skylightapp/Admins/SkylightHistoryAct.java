package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.CubeOutDepthTransformation;
import com.skylightapp.Classes.HistoryViewPagerAdapter;
import com.skylightapp.LoginDirAct;
import com.skylightapp.R;

public class SkylightHistoryAct extends AppCompatActivity {
    HistoryViewPagerAdapter viewPagerAdapter;
    FloatingActionButton floatingActionButton1,floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_skylight_history);
        FragmentManager fm = getSupportFragmentManager();
        final ViewPager2 viewPager2 = findViewById(R.id.viewpager3);
        final TabLayout tabLayout = findViewById(R.id.tabs);
        floatingActionButton1 = findViewById(R.id.fab_admin_history1);
        floatingActionButton2 = findViewById(R.id.fab_admin_history2);

        viewPagerAdapter = new HistoryViewPagerAdapter(fm, getLifecycle());
        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setPageTransformer(new CubeOutDepthTransformation());
        //viewPager.addItemDecoration(RecyclerView.ItemDecoration k);
        //tabLayout.setupWithViewPager(viewPager2);
        tabLayout.addTab(tabLayout.newTab().setText("Tim"));
        tabLayout.addTab(tabLayout.newTab().setText("Pac"));
        tabLayout.addTab(tabLayout.newTab().setText("Sav"));
        tabLayout.addTab(tabLayout.newTab().setText("Mes"));

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
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                packageStuff();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeAdminHistory();
            }
        });


    }
    public void homeAdminHistory() {

        Intent intent = new Intent(this, LoginDirAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(this, getString(R.string.teller_dashboard_taking), Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }

    public void packageStuff() {

        Intent intent = new Intent(this, AdminPackageActivity.class);
        Toast.makeText(this, "Taking you to the Customer area", Toast.LENGTH_LONG).show();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.putExtra("User", profileID);
        startActivity(intent);

    }
}