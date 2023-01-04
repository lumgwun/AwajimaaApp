package com.skylightapp.StateDir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridLayout;

import com.google.gson.Gson;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class WastCollecTranxAct extends AppCompatActivity {
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "WastCollecTranxAct.KEY";
    ChipNavigationBar chipNavigationBar;
    GridLayout maingrid;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    private int profileID;
    CircleImageView profileImage;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_wast_collec_tranx);
    }
}