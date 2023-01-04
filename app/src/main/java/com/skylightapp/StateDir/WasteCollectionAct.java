package com.skylightapp.StateDir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridLayout;

import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class WasteCollectionAct extends AppCompatActivity {
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    com.github.clans.fab.FloatingActionButton fab;
    SharedPreferences userPreferences;
    DBHelper dbHelper;
    Gson gson;
    Gson gson1,gson2;
    String json1,json2;
    String json,machine,SharedPrefUserName,SharedPrefUserMachine,SharedPrefUserPassword;
    int profileUID;
    Bundle bundle;
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "WasteCollectionAct.KEY";
    ChipNavigationBar chipNavigationBar;
    GridLayout maingrid;
    PrefManager prefManager;
    private int profileID,SharedPrefCusID,SharedPrefProfileID;
    CircleImageView profileImage;
    private Profile profile;
    private Customer customer;
    private Account account;
    private State state;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_waste_collection);
        setTitle("Waste Collection Board");
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        customer=new Customer();
        account=new Account();
        gson2 = new Gson();
        userProfile=new Profile();
        state= new State();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        json2 = userPreferences.getString("LastStateUsed", "");
        state = gson2.fromJson(json2, State.class);
        if(userProfile !=null){
            machine=userProfile.getProfileMachine();
            profileUID=userProfile.getPID();

        }
    }
}