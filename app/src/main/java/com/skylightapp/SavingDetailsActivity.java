package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SavingDetailsActivity extends AppCompatActivity {
    private static final String PREF_NAME = "awajima";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_saving_details);
    }
}