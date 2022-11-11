package com.skylightapp.MapAndLoc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.skylightapp.R;

import java.util.Objects;

public class OurCleanUpAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_our_clean_up);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Clean Up Activity");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.lo_awaj);
    }
}