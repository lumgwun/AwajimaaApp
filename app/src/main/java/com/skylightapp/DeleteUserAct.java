package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.skylightapp.Admins.AdminCusActionAct;
import com.skylightapp.Admins.AdminCusActionView;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.SuperAdmin.SuperAdminOffice;

public class DeleteUserAct extends AppCompatActivity {
    private Bundle bundle;
    private Customer customer;
    private DBHelper dbHelper;
    private String blockedUser;
    private int cusInt,cusProfID;
    private Profile customerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_delete_user);
        bundle=getIntent().getExtras();
        customer= new Customer();
        customerProfile= new Profile();
        if(bundle !=null){
            customer=bundle.getParcelable("Customer");
            customerProfile=bundle.getParcelable("Profile");
        }
        if(customer !=null){
            cusInt=customer.getCusUID();
        }
        if(customerProfile !=null){
            cusProfID=customerProfile.getPID();

            AlertDialog.Builder builder = new AlertDialog.Builder(DeleteUserAct.this);
            builder.setTitle("Confirm Action on User");
            builder.setItems(new CharSequence[]
                            {},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dbHelper.deleteProfile(cusProfID);


                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent userIntent = new Intent(DeleteUserAct.this, SuperAdminOffice.class);
                            userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(userIntent);
                            finish();

                        }
                    });

            builder.create().show();
        }

    }
}