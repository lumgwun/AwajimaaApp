package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Database.BizSubscriptionDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketBizSub;
import com.skylightapp.R;

public class SubReceivedAct extends AppCompatActivity {
    private Bundle bundle,receiverBundle;
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    private Gson gson;
    private String json;
    private Profile profile;
    private DBHelper dbHelper;
    private MarketBizSub marketBizSub;
    private BizSubscriptionDAO subscriptionDAO;
    private int subID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sub_received);
        dbHelper = new DBHelper(this);
        bundle= new Bundle();
        receiverBundle= new Bundle();
        marketBizSub= new MarketBizSub();
        gson = new Gson();
        profile= new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        profile = gson.fromJson(json, Profile.class);
        doSubUpdate(bundle);
    }

    private void doSubUpdate(Bundle bundle) {
        subscriptionDAO= new BizSubscriptionDAO(this);
        receiverBundle=bundle;
        receiverBundle=getIntent().getExtras();
        if(receiverBundle !=null){
            marketBizSub=receiverBundle.getParcelable("MarketBizSub");
        }
        if(marketBizSub !=null){
            subID=marketBizSub.getMbiz_Sub_ID();
        }

    }

    public void processSubReceived(View view) {
    }

    public void subRDatePicker(View view) {
    }
}