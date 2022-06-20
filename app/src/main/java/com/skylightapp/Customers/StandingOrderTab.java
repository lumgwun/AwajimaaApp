package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.skylightapp.Admins.AdminTransActivity;
import com.skylightapp.Admins.SkylightUsersActivity;
import com.skylightapp.R;
import com.skylightapp.SignTabMainActivity;

public class StandingOrderTab extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_standing_order_tab);
        Resources resources = getResources();
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(getLocalActivityManager());

        Intent intentsupport = new Intent().setClass(this, SavingsStandingOrder.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabUserSupport = tabhost
                .newTabSpec("Fixed Plan")
                .setIndicator("", resources.getDrawable(R.drawable.ic_add_business))
                .setContent(intentsupport);

        Intent intentTransactions= new Intent().setClass(this, NewSOPaymentPlan.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabTransaction = tabhost
                .newTabSpec("Your Plan")
                .setIndicator("", resources.getDrawable(R.drawable.withdrawal))
                .setContent(intentTransactions);


        // Blackberry tab
        Intent intentSignUpInTab = new Intent().setClass(this, StandingOrderList.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSignUpIn = tabhost
                .newTabSpec("SOs")
                .setIndicator("", resources.getDrawable(R.drawable.ic__category))
                .setContent(intentSignUpInTab);

        // add all tabs
        //tabhost.addTab(tabSpecAdminHome);
        tabhost.addTab(tabUserSupport);
        tabhost.addTab(tabTransaction);
        tabhost.addTab(tabSignUpIn);

        //set Windows tab as default (zero based)
        tabhost.setCurrentTab(2);

    }
}