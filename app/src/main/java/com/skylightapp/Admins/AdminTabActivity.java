package com.skylightapp.Admins;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.skylightapp.R;

public class AdminTabActivity extends TabActivity {
    private static final String PREF_NAME = "awajima";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_tab);
        Resources resources = getResources();
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup(getLocalActivityManager());

        Intent intentsupport = new Intent().setClass(this, AwajimaUsersAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabUserSupport = tabhost
                .newTabSpec("Users")
                .setIndicator("", resources.getDrawable(R.drawable.user3))
                .setContent(intentsupport);

        Intent intentTransactions= new Intent().setClass(this, AdminTransActivity.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabTransaction = tabhost
                .newTabSpec("Tx")
                .setIndicator("", resources.getDrawable(R.drawable.withdrawal))
                .setContent(intentTransactions);

        tabhost.addTab(tabUserSupport);
        tabhost.addTab(tabTransaction);

        tabhost.setCurrentTab(2);
    }
}