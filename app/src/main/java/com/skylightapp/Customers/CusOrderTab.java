package com.skylightapp.Customers;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.Tellers.UserTxAct;

public class CusOrderTab extends TabActivity {
    FragmentManager fragmentManager;

    FloatingActionButton floatingActionButton;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json;
    Profile userProfile;
    long profileUID11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_order_tab);
        userProfile= new Profile();
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_HomeTab3);
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Resources resources = getResources();
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.order_toolbar);
        tabHost.setup(getLocalActivityManager());
        Intent privacyPolicyIntent = new Intent(this, CusPackForPayment.class);
        TabHost.TabSpec cPFP = tabHost.newTabSpec("My Packs");
        cPFP.setIndicator("Packs", getResources().getDrawable(R.drawable.ic_icon2));
        cPFP.setContent(privacyPolicyIntent);
        Intent txIntent = new Intent(this, UserTxAct.class);
        TabHost.TabSpec txUser = tabHost.newTabSpec("Txs");
        txUser.setIndicator("Tx", getResources().getDrawable(R.drawable.ic_icon2));
        txUser.setContent(txIntent);
        tabHost.addTab(cPFP);
        tabHost.addTab(txUser);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpHome();

            }
        });


        if (userProfile != null) {
            profileUID11 = userProfile.getPID();

        }
    }
        public  void helpHome(){
            Intent intent = new Intent(this, NewCustomerDrawer.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("PROFILE_ID", profileUID11);
            startActivity(intent);



        }




}