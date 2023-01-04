package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.skylightapp.MarketClasses.MarketBusiness;

public class LogisticDetailsAct extends AppCompatActivity {
    private Bundle bundle;
    private MarketBusiness marketBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_logistic_park);
        bundle= new Bundle();
        marketBusiness= new MarketBusiness();
        bundle=getIntent().getExtras();
        if(bundle !=null){
            marketBusiness=bundle.getParcelable("MarketBusiness");

        }
    }
}