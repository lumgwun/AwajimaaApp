package com.skylightapp.Customers;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;


import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.PrivacyPolicy_Web;
import com.skylightapp.R;
import com.skylightapp.UserPrefActivity;

import static com.skylightapp.Classes.Profile.PROFILE_ID;


public class CustomerHelpActTab extends TabActivity {
    FragmentManager fragmentManager;

    FloatingActionButton fHome;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson,gson1;
    String json,json1;
    Profile userProfile;
    long profileUID11;
    Customer customer;

    private static final String PREF_NAME = "awajima";
    String SharedPrefUserPassword;
    String SharedPrefCusID;
    String SharedPrefUserName;
    String SharedPrefUserMachine;
    int SharedPrefProfileID;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_help);
        userProfile= new Profile();
        gson = new Gson();
        gson1 = new Gson();
        customer= new Customer();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        SharedPrefUserName=userPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=userPreferences.getString("USER_PASSWORD", "");
        SharedPrefCusID=userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);

        if (userProfile != null) {
            profileUID11 = userProfile.getPID();
        }

         fHome = findViewById(R.id.fab_HelpTab);

        TabHost tabhost = findViewById(android.R.id.tabhost);
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();


        Intent newIntent = new Intent().setClass(this, UserPrefActivity.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecNewLoan = tabhost
                .newTabSpec("Preferences")
                .setIndicator("", resources.getDrawable(R.drawable.settings_fg))
                .setContent(newIntent);

        Intent loanListIntent = new Intent().setClass(this, CusMessageAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLoanList = tabhost
                .newTabSpec("Messages")
                .setIndicator("", resources.getDrawable(R.drawable.ic_return_chat))
                .setContent(loanListIntent);

        Intent repaymentIntent = new Intent().setClass(this, PrivacyPolicy_Web.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecRepayment = tabhost
                .newTabSpec("Our Policy")
                .setIndicator("", resources.getDrawable(R.drawable.ic_icon2))
                .setContent(repaymentIntent);

        tabhost.addTab(tabSpecNewLoan);
        tabhost.addTab(tabSpecLoanList);
        tabhost.addTab(tabSpecRepayment);


        fHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpHome();

            }
        });


    }

    public void helpHome() {
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("User", (Parcelable) userProfile);
        intent.putExtra("Profile", (Parcelable) userProfile);
        intent.putExtra("Customer", (Parcelable) customer);
        intent.putExtra("Machine", SharedPrefUserMachine);
        intent.putExtra(PROFILE_ID, SharedPrefProfileID);
        intent.putExtra("ProfileID", SharedPrefProfileID);

        startActivity(intent);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }
    public void onResume(){
        super.onResume();
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);
    }


}