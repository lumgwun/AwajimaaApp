package com.skylightapp.MapAndLoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.skylightapp.R;

import java.util.Objects;

public class OurJIVsAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_our_jivs);
        Objects.requireNonNull(getSupportActionBar()).setTitle("JIV Activity");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.lo_awaj);
    }
}