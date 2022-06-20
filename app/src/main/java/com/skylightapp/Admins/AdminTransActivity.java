package com.skylightapp.Admins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Window;

import com.google.android.material.tabs.TabLayout;
import com.skylightapp.Classes.CubeOutDepthTransformation;
import com.skylightapp.Classes.Loan;
import com.skylightapp.R;

public class AdminTransActivity extends AppCompatActivity implements LoanApplFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_trans);
        FragmentManager fm = getSupportFragmentManager();

        ViewStateAdapterTx sa = new ViewStateAdapterTx(fm, getLifecycle());
        final ViewPager2 pa = findViewById(R.id.viewpagerAdmin);
        pa.setAdapter(sa);
        pa.setPageTransformer(new CubeOutDepthTransformation());

        final TabLayout tabLayout = findViewById(R.id.tabsAdmin);
        tabLayout.addTab(tabLayout.newTab().setText("Loan"));
        tabLayout.addTab(tabLayout.newTab().setText("TrX"));
        tabLayout.addTab(tabLayout.newTab().setText("Docs"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pa.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pa.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }

    @Override
    public void onItemClick(Loan item) {

    }

    private static class ViewStateAdapterTx extends FragmentStateAdapter {

        public ViewStateAdapterTx(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // Hardcoded in this order, you'll want to use lists and make sure the titles match
            if (position == 0) {
                return new LoanApplFragment();
            }

            if (position == 1) {
                return new AdminTransListFrag();
            }
            if (position == 2) {
                return new AllPaymentDocFragment();
            }
            return new AllPaymentDocFragment();
        }

        @Override
        public int getItemCount() {
            // Hardcoded, use lists
            return 3;
        }


    }
}