package com.skylightapp.Bookings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.skylightapp.Classes.Utils;
import com.skylightapp.R;

import java.util.Objects;

import io.teliver.sdk.core.Teliver;
import io.teliver.sdk.models.UserBuilder;

public class BookingDriverAct extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener{
    private Toolbar toolbar;

    private FragmentManager fragmentManager;

    private View rootView;

    private Snackbar snackbar;

    private FragDriver fragmentDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_booking_driver);
    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.l_awajima);
        setSupportActionBar(toolbar);
        Utils.setUpToolBar(this, toolbar, getSupportActionBar(), getString(R.string.app_name));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        rootView = findViewById(R.id.view_root);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        changeFragment(0);
        /*Teliver.identifyUser(new UserBuilder("driver")
                .setUserType(UserBuilder.USER_TYPE.OPERATOR).build());*/

        OurTeliver.identifyUser(new com.skylightapp.Classes.UserBuilder("driver")
                .setUser_Type(com.skylightapp.Classes.UserBuilder.USER_TYPE.DRIVER).registerPush()
                .build());
    }

    private void changeFragment(int caseValue) {
        if (caseValue == 0) {
            if (fragmentDriver == null)
                fragmentDriver = new FragDriver();
            switchView(fragmentDriver, getString(R.string.app_name));
        }
    }

    private void switchView(final Fragment fragment, final String title) {
        try {
            toolbar.setTitle(title);
            FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
            Fragment mTempFragment = fragmentManager.findFragmentById(R.id.view_container);
            if (!fragment.equals(mTempFragment)) {
                String className = fragment.getClass().getName();
                boolean isAdded = fragmentManager.popBackStackImmediate(className, 0);
                if (!isAdded) {
                    mFragmentTransaction.addToBackStack(className);
                    mFragmentTransaction.add(R.id.view_container, fragment, title);
                }
            }
            mFragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackStackChanged() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.view_container);
        if (fragment == null)
            return;
        String tag = fragment.getTag();
        toolbar.setTitle(tag);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1)
            fragmentManager.popBackStackImmediate();
        else if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
            finish();
        } else {
            snackbar = Snackbar.make(rootView, R.string.txt_press_back, 3000);
            snackbar.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 115)
            fragmentDriver.onReqPermission(grantResults);
    }
}