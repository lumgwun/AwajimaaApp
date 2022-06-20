package com.skylightapp.Customers;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

public class CusLoanTab extends TabActivity {
    FloatingActionButton floatingActionButton;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    String json;
    Profile userProfile;
    long profileUID11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_loan_tab);
        FloatingActionButton floatingActionButton = findViewById(R.id.fab_Loan2Tab);
        userPreferences = this.getSharedPreferences("LastProfileUsed", MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();


        Intent newIntent = new Intent().setClass(this, NewLoanOptions.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecNewLoan = tabhost
                .newTabSpec("New Loan")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(newIntent);

        Intent loanListIntent = new Intent().setClass(this, CustomerLoanListAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLoanList = tabhost
                .newTabSpec("Loan List")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(loanListIntent);

        Intent repaymentIntent = new Intent().setClass(this, CusLoanRepaymentAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecRepayment = tabhost
                .newTabSpec("Repayment")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(repaymentIntent);

        tabhost.addTab(tabSpecNewLoan);
        tabhost.addTab(tabSpecLoanList);
        tabhost.addTab(tabSpecRepayment);


        if (userProfile != null) {
            profileUID11 = userProfile.getPID();



        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpHome();

            }
        });

    }
    public  void helpHome(){
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", profileUID11);
        startActivity(intent);




    }
}