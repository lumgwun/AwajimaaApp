package com.skylightapp.MapAndLoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.skylightapp.R;

import java.util.Objects;

public class TreatedReportAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_treated_report);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Treated Spillage Reports");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.lo_awaj);
    }
}