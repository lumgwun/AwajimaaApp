package com.skylightapp.Markets;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketStock;
import com.skylightapp.MarketClasses.MyStockAdapter;
import com.skylightapp.R;

import java.util.List;
import java.util.Locale;

public class TopStatsAct extends FragmentActivity implements ActionBar.TabListener, android.app.ActionBar.TabListener {

    @Override
    public void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private TopListFragment topListFragment;


        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {

            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    this.topListFragment = new TopBenefitsListFragment();
                    break;
                case 1:
                    this.topListFragment = new TopSalesListFragment();
                    break;
            }

            return this.topListFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_top_benefits_section).toUpperCase(l);
                case 1:
                    return getString(R.string.title_top_sales_section).toUpperCase(l);
            }
            return null;
        }
    }

    public static class TopBenefitsListFragment extends TopListFragment {
        public TopBenefitsListFragment(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            int length = Integer.parseInt(sharedPreferences.getString(StockSettingAct.PREF_TOP_LENGTH, "10"));
            setListData(db.getTopNBenefits(length));
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    public static class TopListFragment extends ListFragment {
        protected DBHelper db;
        protected MyStockAdapter productAdapter;

        public TopListFragment(){}

        @Override
        public void onAttach(Activity activity) {
            this.db = new DBHelper(activity);
            this.db.open();
            super.onAttach(activity);
        }
        @Override
        public void onDestroy() {
            this.db.close();
            super.onDestroy();
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
//			Intent intent = new Intent(this.getActivity(), ProductDetailsActivity.class);
//			((MLBApplication) this.getActivity().getApplication()).setCurrentProd((Product) l.getItemAtPosition(position));
//			startActivity(intent);

            // TODO Auto-generated method stub
            super.onListItemClick(l, v, position, id);
        }


        public void setListData(List<MarketStock> products) {
            this.productAdapter = new MyStockAdapter(getActivity(), android.R.layout.simple_list_item_1, products);
            setListAdapter(this.productAdapter);
        }
    }

    public static class TopSalesListFragment extends TopListFragment {
        public TopSalesListFragment(){}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            int length = Integer.parseInt(sharedPreferences.getString(StockSettingAct.PREF_TOP_LENGTH, "10"));
            setListData(db.getTopNSales(length));
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }


    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json,machine;
    int profileUID;
    Bundle bundle;
    private Profile userProfile;
    private static final String PREF_NAME = "skylight";
    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;


    private void navigateUpFromSameTask() {
        NavUtils.navigateUpFromSameTask(this);
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_top_stats);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile=new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");

        // Set up the action bar.
        final android.app.ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayHomeAsUpEnabled(true);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pagerMyStock);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {

            actionBar.addTab(actionBar.newTab()
                    .setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            navigateUpFromSameTask();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {}

    @Override
    public void onTabSelected(ActionBar.Tab tab,FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab,	FragmentTransaction fragmentTransaction) {}
}