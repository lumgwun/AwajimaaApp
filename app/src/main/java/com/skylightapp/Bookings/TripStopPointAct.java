package com.skylightapp.Bookings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;

import java.util.ArrayList;

public class TripStopPointAct extends AppCompatActivity {
    private TripStopPoint tripStopPoint;
    private TripStopPointAdap arrayAdapter;
    SQLiteDatabase sqLiteDatabase;
    Gson gson, gson1,gson3,gson6;
    String json, json1, json3,json6nIN,name;
    private static final String PREF_NAME = "awajima";
    String selectedState;
    LatLng userLocation;
    LatLng cusLatLng;
    double latitude = 0;
    double longitude = 0;
    Geocoder geocoder;
    AppCompatSpinner spnState,spnTripPoint,spnUseLoc;
    ArrayList<TripStopPoint> tripStopPoints;
    private Profile userProfile;
    SharedPreferences userPreferences;
    private AppCompatButton btnNewTripPoint;
    private AppCompatEditText edtPlaceName;
    private LinearLayoutCompat layoutNewPlace;
    private FloatingActionButton newTpFab;
    private MarketBusiness marketBusiness;
    private long bizID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trip_stop_point);
        gson = new Gson();
        gson1 = new Gson();
        gson3= new Gson();
        userProfile= new Profile();
        marketBusiness= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastProfileUsed", "");
        marketBusiness = gson1.fromJson(json1, MarketBusiness.class);
        spnState = findViewById(R.id.tp_state);
        spnTripPoint = findViewById(R.id.tp_for);
        spnUseLoc = findViewById(R.id.spn_use_this);
        btnNewTripPoint = findViewById(R.id.reg_new_tp);
        edtPlaceName = findViewById(R.id.name_trip_point);
        layoutNewPlace = findViewById(R.id.new_TP_Layout);
        newTpFab = findViewById(R.id.ic_trip_p_fab);
        if(marketBusiness !=null){
            bizID=marketBusiness.getBusinessID();
        }


        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = spnState.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        newTpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutNewPlace.setVisibility(View.VISIBLE);
            }
        });

        arrayAdapter = new TripStopPointAdap(TripStopPointAct.this, R.layout.trip_stop_point_item, tripStopPoints);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnState.setAdapter(arrayAdapter);

        spnTripPoint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tripStopPoint = (TripStopPoint) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnNewTripPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void newTripPoint(View view) {
    }
}