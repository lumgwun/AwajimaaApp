package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;

public class VerifiAct extends AppCompatActivity {
    private Bundle bundle;
    private String chooser;
    private SharedPreferences userPreferences;
    private static final String PREF_NAME = "awajima";
    private Gson gson;
    private String json, selectedPlan,link;
    private Profile userProfile;
    int profileID;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_verifi);
        bundle= new Bundle();
        doChooser();
        gson = new Gson();
        userProfile= new Profile();
        //floatingActionButton = findViewById(R.id.fab_membership);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
    }

    private void doChooser() {
        bundle=getIntent().getExtras();
        if(bundle !=null){
            chooser=bundle.getString("chooser");
        }

    }
}