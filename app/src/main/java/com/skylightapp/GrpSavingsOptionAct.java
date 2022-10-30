package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.NewCustomerDrawer;

public class GrpSavingsOptionAct extends AppCompatActivity {
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    Profile userProfile;
    Gson gson;
    private String json;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_grp_savings_option);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        bundle= new Bundle();
        userProfile=new Profile();
        AlertDialog.Builder builder= new AlertDialog.Builder(GrpSavingsOptionAct.this);
        builder.setTitle("Group Savings Choice");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        builder.setIcon(R.drawable.marker_);
        //builder.setIcon(R.drawable.ic_);
        bundle.putParcelable("Profile",userProfile);
        builder.setItems(new CharSequence[]{"Start a new Savings Group", "Join an Existing Group"},
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                switch (which) {
                    case 0:

                        //Toast.makeText(UserLocatorAct.this, "You have selected, to Start a New Group", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(GrpSavingsOptionAct.this, MyNewGrpSavings.class);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myIntent.putExtras(bundle);
                        startActivity(myIntent);
                        break;
                    case 1:

                        Intent myInt = new Intent(GrpSavingsOptionAct.this, GroupSavingsAcctList.class);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        myInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        myInt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myInt.putExtras(bundle);
                        startActivity(myInt);

                        break;


                }

            }
                }).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();

    }
}