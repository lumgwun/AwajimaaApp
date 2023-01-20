package com.skylightapp.Bookings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.skylightapp.Classes.PrefManager;
import com.skylightapp.R;

public class TripOpLaunchAct extends AppCompatActivity {
    private PrefManager prefManager;
    private LinearLayout viewOperator, viewConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_op_trip_launch);
        prefManager= new PrefManager(this);
        Class className;
        if (prefManager.getBoolean(TripConstants.IS_LOGGED_IN)) {
            if (prefManager.getBoolean(TripConstants.LOGGED_IN_CUSTOMER))
                className = BookingCusAct.class;
            else className = BookingDriverAct.class;
        } else className = TripOpLaunchAct.class;
        startActivity(new Intent(this, className));
        finish();
    }
    @SuppressLint("CutPasteId")
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        viewOperator = findViewById(R.id.image_operator);
        viewConsumer = findViewById(R.id.image_consumer);
        View btnContinue = findViewById(R.id.view_continue);

        viewConsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewConsumer.setSelected(!viewConsumer.isSelected());
                viewOperator.setSelected(false);
            }
        });


        viewOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewOperator.setSelected(!viewOperator.isSelected());
                viewConsumer.setSelected(false);
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        startMotion();
    }

    private void startMotion() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                MotionLayout motionLayout = findViewById(R.id.start_screen);
                motionLayout.transitionToEnd();
            }
        }, 1500);
    }

    private void validate() {
        try {
            PrefManager preference = new PrefManager(this);
            Intent intent;
            if (viewConsumer.isSelected()) {
                intent = new Intent(TripOpLaunchAct.this, BookingCusAct.class);
                preference.storeBoolean(TripConstants.LOGGED_IN_CUSTOMER, true);
            } else if (viewOperator.isSelected()) {
                intent = new Intent(TripOpLaunchAct.this, BookingDriverAct.class);
                preference.storeBoolean(TripConstants.LOGGED_IN_CUSTOMER, false);
            } else {
                Toast.makeText(this, "Select One to Continue", Toast.LENGTH_SHORT).show();
                return;
            }
            preference.storeBoolean(TripConstants.IS_LOGGED_IN, true);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}