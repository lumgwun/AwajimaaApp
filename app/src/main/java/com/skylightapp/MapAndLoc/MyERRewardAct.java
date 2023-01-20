package com.skylightapp.MapAndLoc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

public class MyERRewardAct extends AppCompatActivity {
    Customer customer;
    String uSurname, uFirstName,ManagerSurname,managerFirstName;
    Profile managerProfile;
    String currentDate;
    DBHelper applicationDb;
    Account account;
    int customerID;
    long acctID;
    int profileID;
    private Gson gson,gson1;
    private int AMOUNT;
    private String json,json1,machine;
    private SharedPreferences userPreferences;
    Profile userProfile;
    private static final String PREF_NAME = "awajima";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_erreward);
        setTitle("Task And Rewards");
        gson = new Gson();
        gson1 = new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userProfile= new Profile();
        customer= new Customer();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json, Customer.class);
        if(customer !=null){
            uSurname=customer.getCusSurname();
            account = customer.getCusAccount();
            if(account !=null){
                acctID = account.getAwajimaAcctNo();
            }
            customerID = customer.getCusUID();


        }
        machine=userPreferences.getString("Machine","");

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }


    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

}