package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

public class BizDetailsAct extends AppCompatActivity {
    private FloatingActionButton fabHome,fabAcct,fabTranx;
    int profileID,position;
    private static final String PREF_NAME = "awajima";
    Gson gson, gson1,gson3;
    String json, json1,json3;
    private Profile userProfile;
    private long bizNo;
    SharedPreferences userPreferences;
    private Bundle bundle;
    private MarketBusiness marketBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biz_details);
        marketBusiness= new MarketBusiness();
        fabHome = findViewById(R.id.fab_bizH);
        fabAcct = findViewById(R.id.fab_acctB);
        fabTranx = findViewById(R.id.fab_Biz_T);
        gson1 = new Gson();
        gson = new Gson();
        bundle= new Bundle();
        userProfile = new Profile();
        bundle=getIntent().getExtras();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(bundle !=null){
            marketBusiness=bundle.getParcelable("MarketBusiness");
            position=bundle.getInt("position");

        }
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fabAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        fabTranx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}