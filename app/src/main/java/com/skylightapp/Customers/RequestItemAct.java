package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class RequestItemAct extends AppCompatActivity {
    SharedPreferences userPreferences;
    private Gson gson;
    private String json,packageName;
    private Profile userProfile;
    Bundle packBundle,bundle;
    private Customer customer;
    private int selectedPackageIndex;
    SkyLightPackage selectedPackage;
    private ArrayList<SkyLightPackage> skyLightPackageAll;
    private ArrayAdapter<SkyLightPackage> skyLightPackageAllAdapter;
    AppCompatSpinner spn_select_package;
    SecureRandom random;
    Random ran ;
    private DBHelper dbHelper;
    int customerID;
    int profileID;
    int messageID;
    long packageID;
    AppCompatTextView txtCusName;
    AppCompatButton btnRequest;
    String customerName, message,dateOfRequest, customerBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_request_item);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        dbHelper= new DBHelper(this);
        customer = new Customer();
        userProfile=new Profile();
        packBundle=new Bundle();
        bundle=new Bundle();
        random= new SecureRandom();
        txtCusName = findViewById(R.id.cusForRequest);
        spn_select_package = findViewById(R.id.packageForRequestCus);
        btnRequest = findViewById(R.id.submitRequest);
        userProfile = gson.fromJson(json, Profile.class);
        packBundle = getIntent().getExtras();
        if(userProfile !=null){
            customer=userProfile.getTimelineCustomer();
            profileID=userProfile.getPID();

        }
        if(customer !=null){
            customerID=customer.getCusUID();
            customerName=customer.getCusSurname()+","+customer.getCusFirstName();
            customerBranch=customer.getCusOfficeBranch();
        }
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        dateOfRequest = mdformat.format(calendar.getTime());
        txtCusName.setText(MessageFormat.format("Customer:{0}", customerName));
        skyLightPackageAll=dbHelper.getCustomerCompletePack(customerID,"completed");
        skyLightPackageAllAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, skyLightPackageAll);
        skyLightPackageAllAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_select_package.setAdapter(skyLightPackageAllAdapter);
        spn_select_package.setSelection(0);
        selectedPackageIndex = spn_select_package.getSelectedItemPosition();
        try {
            selectedPackage = skyLightPackageAll.get(selectedPackageIndex);
            packageName=selectedPackage.getPackageName();

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(selectedPackage !=null){
            packageID=selectedPackage.getPackageID();
        }
        message="I am requesting for my "+packageName+"/"+packageID;
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertMessage(profileID,customerID,messageID,"Package Request",message,customerName,customerBranch,dateOfRequest);
                Toast.makeText(RequestItemAct.this, "Request has been sent", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void cusHome(View view) {
        Intent amountIntent = new Intent(RequestItemAct.this, NewCustomerDrawer.class);
        amountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(amountIntent);
    }

    public void addNew(View view) {
        Intent amountIntent = new Intent(RequestItemAct.this, NewPackCusAct.class);
        amountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(amountIntent);
    }
}