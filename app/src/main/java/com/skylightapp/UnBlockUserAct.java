package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.SuperAdmin.DuePackagesAct;

public class UnBlockUserAct extends AppCompatActivity {
    private Bundle bundle;
    private Customer customer;
    private DBHelper dbHelper;
    private String blockedUser,newUserType;
    private int cusInt, blockedProfileID;
    private Profile blockedProfile;
    private AppCompatSpinner spnUserType;
    private AppCompatButton btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_un_block_user);
        dbHelper=new DBHelper(this);
        btnUpdate = findViewById(R.id.buttonUpdateNewRole);
        spnUserType = findViewById(R.id.spn_new_role);
        blockedUser="BlockedUser";
        spnUserType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newUserType = (String) parent.getSelectedItem();
                Toast.makeText(UnBlockUserAct.this, "New User Status: "+ newUserType,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bundle=getIntent().getExtras();
        if(bundle !=null){
            customer=bundle.getParcelable("Customer");
            blockedProfile=bundle.getParcelable("Profile");
        }
        if(customer !=null){
            cusInt=customer.getCusUID();
        }
        if(blockedProfile !=null){
            blockedProfileID=blockedProfile.getPID();

        }
        btnUpdate.setOnClickListener(this::upDateUserRole);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.updateProfileStatus(newUserType,blockedProfileID);

            }
        });

    }

    public void upDateUserRole(View view) {
        dbHelper.updateProfileStatus(newUserType,blockedProfileID);
    }
}