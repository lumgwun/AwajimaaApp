package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.SuperAdmin.SuperAdminOffice;

public class BlockedUserAct extends AppCompatActivity {
    private Bundle bundle;
    private Customer customer;
    private DBHelper dbHelper;
    private String blockedUser;
    private int cusInt;
    private CusDAO cusDAO;
    private ProfDAO profileDao;
    //private Spinner spnPurpose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_blocked_user);
        dbHelper=new DBHelper(this);
        blockedUser="BlockedUser";
        cusDAO= new CusDAO(this);
        profileDao= new ProfDAO(this);

        bundle=getIntent().getExtras();
        if(bundle !=null){
            customer=bundle.getParcelable("Customer");
        }
        if(customer !=null){
            cusInt=customer.getCusUID();
            AlertDialog.Builder builder = new AlertDialog.Builder(BlockedUserAct.this);
            builder.setTitle("Confirm Action on Customer");
            builder.setItems(new CharSequence[]
                            {},
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            profileDao.blockCustomer(cusInt,blockedUser);


                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            Intent userIntent = new Intent(BlockedUserAct.this, SuperAdminOffice.class);
                            userIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(userIntent);
                            finish();

                        }
                    });

            builder.create().show();
        }


    }
}