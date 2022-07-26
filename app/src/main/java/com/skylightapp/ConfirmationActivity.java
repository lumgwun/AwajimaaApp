package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.skylightapp.Accountant.BranchMPayments;
import com.skylightapp.Admins.AdminDrawerActivity;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Customers.CusLoanTab;
import com.skylightapp.Customers.CustomerPayAct;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Customers.SendCustomerMessage;
import com.skylightapp.SuperAdmin.ADepositList;
import com.skylightapp.SuperAdmin.ChartAct;
import com.skylightapp.SuperAdmin.OfficeCreatorAct;
import com.skylightapp.SuperAdmin.PayOutRequestList;
import com.skylightapp.SuperAdmin.SuperAdminCountAct;
import com.skylightapp.SuperAdmin.SuperAdminOffice;
import com.skylightapp.SuperAdmin.SuperAnyDayCount;
import com.skylightapp.SuperAdmin.SuperStockTrAct;
import com.skylightapp.SuperAdmin.SuperUserCreator;
import com.skylightapp.SuperAdmin.TellerWorkAct;
import com.skylightapp.SuperAdmin.TranxApprovalAct;
import com.skylightapp.Tellers.MyCusLoanRepayment;
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
                                Intent loginRIntent = new Intent(ConfirmationActivity.this, AdminDrawerActivity.class);
                            loginRIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(loginRIntent);
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
        startActivity(new Intent(ConfirmationActivity.this, AdminDrawerActivity.class));
        finish();
    }
}