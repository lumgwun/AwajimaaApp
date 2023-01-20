package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.skylightapp.Admins.AdminPackageAct;
import com.skylightapp.Classes.PrefManager;


public class ConfirmationActivity extends AppCompatActivity {
    private AppCompatButton xConfirmBtn;

    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_confirmation);
        xConfirmBtn = (AppCompatButton) findViewById(R.id.confirmB3);
        CheckBox xCheckBox = (CheckBox) findViewById(R.id.checkBox2);
        xConfirmBtn.setEnabled(false);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            //finish();
        }

        xCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {

                    xConfirmBtn.setEnabled(true);

                    xConfirmBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            prefManager.setFirstTimeLaunch(false);
                                    @SuppressLint({"NewApi", "LocalSuppress"}) Intent loginRIntent = new Intent(ConfirmationActivity.this, SignUpAct.class);
                            loginRIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(loginRIntent);
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_left);
                        }
                    });

                } else if(!compoundButton.isChecked()) {

                    xConfirmBtn.setEnabled(false);
                }
            }
        });
    }
    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startActivity(new Intent(ConfirmationActivity.this, SignUpAct.class));
        }
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }
}