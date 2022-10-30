package com.skylightapp.Customers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.skylightapp.TransactionFragment;
import com.skylightapp.UserTimeLineOverview;

public class CustomerTabAdapter extends FragmentStateAdapter {

    public CustomerTabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new UserTimeLineOverview();
        }
        if (position == 1) {
            return new MyDocumentsFragment();
        }
        if (position == 2) {
            return new CustomerPackageOverview();
        }

        if (position == 3) {
            return new MySavingsCodesFrag();
        }

        if (position == 4) {
            return new MySavingsFragment();
        }

        return new UserTimeLineOverview();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
