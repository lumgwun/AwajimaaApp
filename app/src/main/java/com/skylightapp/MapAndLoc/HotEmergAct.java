package com.skylightapp.MapAndLoc;

import static com.skylightapp.MapAndLoc.PlaceData.COLUMN_PLACE_ID;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_ADDRESS;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_LATLNG;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_LATLNGBOUNDS;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_NAME;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_PHONE;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_PROF_ID;
import static com.skylightapp.MapAndLoc.PlaceData.PLACE_ENTRY_TIMESTAMP;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.Switch;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class HotEmergAct extends AppCompatActivity {
    private Woosmap woosmap;
    private DBHelper dbHelper;
    Gson gson, gson1,gson2;
    String json1, json2,json,  userMachine;
    Profile userProfile;
    private int profileID;
    String SharedPrefUserMachine;
    String SharedPrefUserName;
    String SharedPrefProfileID,dateOfToday;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    SharedPreferences.Editor editor;
    private int reportID;
    private MarketBusiness marketBusiness;
    private long bizID,reportIDF;
    private static final int PLACE_PICKER_REQUEST = 9871;
    private String placeID,stringLatLng;
    private CharSequence name;
    private CharSequence phoneNo;
    private Switch onOffSwitch;
    private LatLng placeLatLng;
    private LatLngBounds latLngBounds;
    private CharSequence address;
    private EmergencyReport emergencyReport;
    private EmergReportDAO emergReportDAO;
    private Intent intent;
    private boolean isDoHot=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_listing_filters);
        OtherPref.trackingEnable = !OtherPref.trackingEnable;

        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        userProfile= new Profile();
        emergencyReport= new EmergencyReport();
        marketBusiness= new MarketBusiness();
        emergReportDAO= new EmergReportDAO(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserMachine = userPreferences.getString("machine", "");
        SharedPrefProfileID = userPreferences.getString("PROFILE_ID", "");
        json1 = userPreferences.getString("LastProfileUsed", "");
        json = userPreferences.getString("LastTellerProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);
        reportID = ThreadLocalRandom.current().nextInt(1020, 103210);

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:SS", Locale.getDefault());

        dateOfToday =dateFormat.format(calendar.getTime());

        if(marketBusiness !=null){
            bizID=marketBusiness.getBusinessID();
        }

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            try {
                intent = builder.build(this);
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }

        }catch (GooglePlayServicesRepairableException e)
        {
            e.printStackTrace();
        }

        startActivityForResult(intent, PLACE_PICKER_REQUEST);


        Intent intent = new Intent(this, LocUpdatesBReceiver.class);
        intent.setAction(LocUpdatesBReceiver.ACTION_PROCESS_UPDATES);
        PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
         editor = userPreferences.edit();
        editor.putBoolean(getString(R.string.setting_enabled),true);
        editor.apply();
        String msg = "";
        if(OtherPref.trackingEnable) {
            msg = "Tracking Enable";
            editor.putBoolean( "trackingEnable",true);
            editor.apply();

        } else {
            msg = "Tracking Disable";
            editor.putBoolean( "trackingEnable",false);
            editor.apply();
        }
        try {

            woosmap.enableTracking(OtherPref.trackingEnable);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        OtherPref.searchAPIEnable = !OtherPref.searchAPIEnable;
        String msg1 = "";
        if(OtherPref.searchAPIEnable) {

            editor.putBoolean( "searchAPIEnable",true);
            editor.apply();
        }else {

            editor.putBoolean( "searchAPIEnable",false);
            editor.apply();
        }
        if(OtherPref.distanceAPIEnable) {

            editor.putBoolean( "distanceAPIEnable",true);
            editor.apply();

        }else {

            editor.putBoolean( "distanceAPIEnable",false);
            editor.apply();

        }


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:SS", Locale.getDefault());

        dateOfToday =dateFormat.format(calendar.getTime());
        emergReportDAO= new EmergReportDAO(this);
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);
            if (place == null) {
                //Log.i(TAG, "No place selected");
                return;
            }
            setResult(RESULT_OK);

            if(place !=null){
                placeID = place.getId();
                phoneNo=place.getPhoneNumber();
                placeLatLng=place.getLatLng();
                address=place.getAddress();
                latLngBounds=place.getViewport();
                name=place.getName();


            }


            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PLACE_ID, placeID);
            contentValues.put(PLACE_ENTRY_PROF_ID, profileID);
            contentValues.put(PLACE_ENTRY_NAME, String.valueOf(name));
            contentValues.put(PLACE_ENTRY_ADDRESS, String.valueOf(address));
            contentValues.put(PLACE_ENTRY_PHONE, String.valueOf(phoneNo));
            contentValues.put(PLACE_ENTRY_LATLNGBOUNDS, String.valueOf(latLngBounds));
            contentValues.put(PLACE_ENTRY_LATLNG, String.valueOf(placeLatLng));
            contentValues.put(PLACE_ENTRY_TIMESTAMP, dateOfToday);
            getContentResolver().insert(PlaceContract.PlaceEntry.CONTENT_URI, contentValues);

        }
        stringLatLng= String.valueOf(placeLatLng);
        emergencyReport = new EmergencyReport(reportID, profileID,bizID, dateOfToday, "Hot",stringLatLng,String.valueOf(name), "",String.valueOf(address),"");
        try {
            if(emergReportDAO !=null){
                isDoHot=true;
                reportIDF =emergReportDAO.insertHotEmergReport(reportID, profileID,bizID, dateOfToday, "Hot",stringLatLng,String.valueOf(name), "",String.valueOf(address),"");


            }

        } catch (SQLiteException e) {
            e.printStackTrace();
        }
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