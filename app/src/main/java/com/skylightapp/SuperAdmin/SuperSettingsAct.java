package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.skylightapp.R;

public class SuperSettingsAct extends AppCompatActivity {
    ToggleButton tb1, tb2;
    SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuilder result = new StringBuilder();
                result.append("ToggleButton1 : ").append(tb1.getText());
                result.append("\nToggleButton2 : ").append(tb2.getText());

                Snackbar snackbar= Snackbar.make(view, result.toString(), Snackbar.LENGTH_LONG)
                        .setAction("SWITCH ENABLE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                switchCompat.setChecked(true);
                            }
                        });

                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        });

        tb1= (ToggleButton)findViewById(R.id.tb1);
        tb2=(ToggleButton)findViewById(R.id.tb2);
        switchCompat=(SwitchCompat)findViewById(R.id.switchButton);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Snackbar.make(buttonView, "Switch state checked "+isChecked, Snackbar.LENGTH_LONG)
                        .setAction("ACTION",null).show();
            }
        });


    }
}