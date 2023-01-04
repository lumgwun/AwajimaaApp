package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.skylightapp.Admins.SOListAct;
import com.skylightapp.Bookings.BoatTripListAct;
import com.skylightapp.Bookings.BookingDrawerAct;
import com.skylightapp.Bookings.BookingHomeAct;
import com.skylightapp.Bookings.BookingsMainTab;
import com.skylightapp.Bookings.TaxiDriverRideAct;
import com.skylightapp.Bookings.TripBookingAct;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Customers.CusLoanTab;
import com.skylightapp.Customers.CusOrderTab;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Customers.SOTab;
import com.skylightapp.Customers.SavingsSOAct;
import com.skylightapp.MapAndLoc.EmergReportMapA;
import com.skylightapp.MapAndLoc.GoogleMapAct;
import com.skylightapp.MapAndLoc.MapPanoramaStVAct;
import com.skylightapp.MapAndLoc.ReportSceneMAct;
import com.skylightapp.MapAndLoc.UserReportEmergAct;
import com.skylightapp.Markets.BizDSelectPartnerAct;
import com.skylightapp.Markets.BizRegAct;
import com.skylightapp.Markets.ChatActCon;
import com.skylightapp.Markets.MarketChatTab;
import com.skylightapp.Markets.MarketCreatorAct;
import com.skylightapp.Markets.MarketHub;
import com.skylightapp.Markets.MarketTab;
import com.skylightapp.Markets.ProductsAct;
import com.skylightapp.Tellers.TellerHomeChoices;


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
                                    Intent loginRIntent = new Intent(ConfirmationActivity.this, UserPrefActivity.class);
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
        startActivity(new Intent(ConfirmationActivity.this, UserPrefActivity.class));
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        finish();
    }
}