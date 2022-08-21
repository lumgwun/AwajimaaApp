package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.skylightapp.Accountant.AcctantBackOffice;
import com.skylightapp.Admins.AdminDrawerActivity;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.SuperAdmin.SuperAdminOffice;
import com.skylightapp.Tellers.TellerHomeChoices;

public class TestAct extends AppCompatActivity {
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json,phoneNo;
    private Customer selectedCustomer;
    private Profile userProfile;
    private StandingOrderAcct standingOrderAcct;
    double accountBalance1;
    long accountNo,acctID;
    long customerID;
    double amountContributedSoFar,newAmountContributedSoFar;
    double packageGrantTotal,amountRemaining,moneySaved;
    long profileID, oldPackageId;
    private AppCompatButton btn_Customer,btnAdmin,btnShop,btnTeller,btnSuperAdmin,btnSign,btnAccountant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        gson = new Gson();
        userProfile= new Profile();
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        standingOrderAcct= new StandingOrderAcct();
        btn_Customer = findViewById(R.id.getCustomer);
        btnSign = findViewById(R.id.GetSign);
        btnAdmin = findViewById(R.id.getAdmin);
        btnShop = findViewById(R.id.getEShopping);
        btnAccountant = findViewById(R.id.getAccountant);
        btnSign.setOnClickListener(this::goSignTab);
        btnShop.setOnClickListener(this::goECommerce);
        btnAdmin.setOnClickListener(this::goAdmin);
        btnTeller = findViewById(R.id.GetTeller);
        btnSuperAdmin = findViewById(R.id.getSuperAdmin);
        btnSuperAdmin.setOnClickListener(this::goSuperAdmin);
        btnTeller.setOnClickListener(this::goTeller);
        btnAccountant.setOnClickListener(this::goAccountant);

        selectedCustomer= new Customer();
        btn_Customer.setOnClickListener(this::goCustomer);
    }
    public void goAdmin(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, AdminDrawerActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void goCustomer(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void goTeller(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, TellerHomeChoices.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void goAccountant(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, AcctantBackOffice.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void goShop(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, ProductsAct.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    public void goSignTab(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, SignTabMainActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void goSuperAdmin(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, SuperAdminOffice.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void goECommerce(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, ProductsAct.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}