package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.PackageRecyclerAdapter;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.Tellers.PackageAllCusAct;

import java.util.ArrayList;

public class ProfilePackDetails extends AppCompatActivity {
    private static final String TAG = CustomerPackageOverview.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/ProfilePackDetails";

    private RecyclerView recyclerView;

    private ArrayList<SkyLightPackage> skyLightPackages;
    private PackageRecyclerAdapter mAdapter;


    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    private FloatingActionButton fab;
    private ListView lstAccounts;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private Button btnAddMyPackages;
    private androidx.cardview.widget.CardView CardView;
    private Profile profile;
    long profileID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cust_pack_details);

        fab = findViewById(R.id.cus_home_pack);
        txtTitleMessage = findViewById(R.id.package_tittle);
        recyclerView = findViewById(R.id.recycler_view_prof);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        profile = gson.fromJson(json, Profile.class);

        skyLightPackages = new ArrayList<SkyLightPackage>();
        mAdapter = new PackageRecyclerAdapter(this, R.layout.package_list_row,skyLightPackages);
        dbHelper = new DBHelper(this);
        if(profile !=null){
            int profileID = profile.getPID();
            skyLightPackages = dbHelper.getPackagesFromCurrentProfile(profileID);

        }



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        setValues();


    }
    private void setValues() {

        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        String json = userPreferences.getString("LastProfileUsed", "");
        Profile profile = gson.fromJson(json, Profile.class);

        fab.setOnClickListener(view -> cMHome());



    }
    private void addSavings() {
        Intent usersIntent = new Intent(this,
                PackageAllCusAct.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this, "Taking your back to your Back Office", Toast.LENGTH_SHORT).show();

    }


    private void cMHome() {
        Intent usersIntent = new Intent(this,
                NewCustomerDrawer.class);
        usersIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Toast.makeText(this, "Taking your back to your Back Office", Toast.LENGTH_SHORT).show();


    }
}