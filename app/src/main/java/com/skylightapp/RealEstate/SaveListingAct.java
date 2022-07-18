package com.skylightapp.RealEstate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.icu.util.ValueIterator;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static android.content.ContentValues.TAG;

public class SaveListingAct extends AppCompatActivity {
    Bundle locationBundle;
    String location1;
    String longitude,latitude;
    Properties properties;
    String propertyTittle;
    ArrayList<Object> arraylist;
    //String[] arraylist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_save_listing);
        locationBundle=getIntent().getExtras();
        arraylist =new ArrayList<>();
        if(locationBundle !=null){
            location1=locationBundle.getParcelable("LatLng");
            longitude=locationBundle.getString("Longitude");
            latitude=locationBundle.getString("Latitude");
            properties=locationBundle.getParcelable("Properties");

        }
        if(properties !=null){
            propertyTittle=properties.getTittleOfProperty();
        }
        String[] array = { propertyTittle, latitude, longitude, location1 };
        arraylist = new ArrayList<>(Arrays.asList(array));
        new ArrayList<Object>(Collections.singletonList(arraylist));

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arraylist);
        editor.putString(TAG, json);
        editor.apply();
    }
}