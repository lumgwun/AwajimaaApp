package com.skylightapp.Classes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;



public class HistoryViewPagerAdapter extends FragmentStateAdapter {



    public HistoryViewPagerAdapter(@NonNull FragmentManager supportFragmentManager,@NonNull Lifecycle lifecycle) {
        super(supportFragmentManager,lifecycle);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            /*case 0:
                return new AppTimeLineOverview();

            case 1:
                return new AdminPackageListFragment();

            case 2:
                return new SavingsListFragment();
            case 3:
                return new AdminSupportMessagesFragment();

            default:
                return null;*/
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}

