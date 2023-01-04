package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.gson.Gson;
import com.skylightapp.Bookings.TaxiDriver;
import com.skylightapp.Bookings.TripTAd;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.CustomApplication;
import com.skylightapp.Interfaces.Consts;
import com.skylightapp.R;
import com.teliver.sdk.core.Teliver;
import com.teliver.sdk.core.TripListener;
import com.teliver.sdk.models.Trip;

import java.util.ArrayList;
import java.util.List;

public class AllLiveTrips extends AppCompatActivity implements TripListener,View.OnClickListener {
    private LocationManager manager;

    private View  viewEmpty;
    private PrefManager mPreference;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private TripTAd mAdapter;
    private Gson gson, gson1,gson2;
    private String json, json1,json2;
    private Profile userProfile;
    private Customer customer;
    String SharedPrefUserPassword;
    String SharedPrefCusID;
    String SharedPrefUserMachine,surName,firstName;
    String SharedPrefUserName;
    int SharedPrefProfileID;
    private Awajima awajima;
    private List<Trip> currentTrips;
    CustomApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_live_trips);
        application= new CustomApplication();
        Teliver.setTripListener(this);
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        customer = new Customer();
        awajima= new Awajima();
        currentTrips = new ArrayList<>();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword = userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID = userPreferences.getString("CUSTOMER_ID", "");
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID = userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        json2 = userPreferences.getString("LastAwajimaUsed", "");
        awajima = gson2.fromJson(json2, Awajima.class);
        RecyclerView recyclerView = findViewById(R.id.live_recycler);
        //viewEmpty = findViewById(R.id.view_empty);
        currentTrips.addAll(Teliver.getCurrentTrips());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TripTAd(this);
        mAdapter.setData(currentTrips, this);
        recyclerView.setAdapter(mAdapter);
        /*if (currentTrips.isEmpty())
            viewEmpty.setVisibility(View.VISIBLE);*/
    }

    @Override
    public void onTripStarted(Trip tripDetails) {
        Log.d("Driver:", "Trip started::" + tripDetails);
        changeStatus(tripDetails.getTrackingId(), true);

    }

    @Override
    public void onLocationUpdate(Location location) {

    }

    @Override
    public void onTripEnded(String trackingID) {
        Log.d("Driver:", "Trip Ended::" + trackingID);
        changeStatus(null, false);

    }
    @SuppressLint("NotifyDataSetChanged")
    private void changeStatus(String id, boolean status) {
        currentTrips.clear();
        currentTrips.addAll(Teliver.getCurrentTrips());
        mAdapter.notifyDataSetChanged();
        viewEmpty.setVisibility(currentTrips.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onTripError(String reason) {

    }

    @Override
    public void onClick(View view) {

    }
}