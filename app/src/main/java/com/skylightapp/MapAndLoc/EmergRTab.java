package com.skylightapp.MapAndLoc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.skylightapp.Bookings.BoatBookingTab;
import com.skylightapp.Bookings.BoatTripListAct;
import com.skylightapp.Bookings.MyBoatAct;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.Awajima;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmergRTab extends TabActivity {
    Location location;
    StreetViewPanorama streetViewPanorama;
    private SharedPreferences userPreferences;
    ArrayList<EmergencyReport> emergencyReports;
    Gson gson,gson1;
    String json,machine,json1,state;
    int profileUID;
    Profile userProfile;
    Bundle bundle;
    private AppCompatSpinner spnTab;
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    private static final String PREF_NAME = "awajima";
    private Awajima awajima;
    private EmergReportDAO emergReportDAO;
    private EmergReportArrayA reportArrayA;
    private EmergencyReport emergencyReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_emerg_r_tab);
        checkInternetConnection();
        emergencyReport= new EmergencyReport();
        TabHost tabhost = findViewById(android.R.id.tabhost);
        setTitle("Emergency Report House");
        awajima= new Awajima();
        emergReportDAO= new EmergReportDAO(this);
        TabWidget tabs = findViewById(android.R.id.tabs);
        tabhost.setup(getLocalActivityManager());
        Resources resources = getResources();
        spnTab = findViewById(R.id.er_spn_tab);
        gson = new Gson();
        gson1= new Gson();
        emergencyReports= new ArrayList<>();
        userProfile=new Profile();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json1 = userPreferences.getString("LastAwajimaUsed", "");
        awajima = gson1.fromJson(json1, Awajima.class);
        json = userPreferences.getString("LastProfileUsed", "");
        state = userPreferences.getString("PROFILE_STATE", "");
        userProfile = gson.fromJson(json, Profile.class);
        if(awajima !=null){
            if(emergReportDAO !=null){
                emergencyReports=emergReportDAO.getAllEmergencyReports();

            }

        }else {
            if(emergReportDAO !=null){
                if(state !=null){
                    emergencyReports=emergReportDAO.getEmergencyReportForState(state);

                }else {
                    emergencyReports=emergReportDAO.getEmergencyReportForCountry("Nigeria");
                }

            }
        }
        bundle = new Bundle();
        if(userProfile !=null){
            machine=userProfile.getProfileMachine();
            profileUID=userProfile.getPID();
        }


        floatingActionButton = findViewById(R.id.er_fab_tab);
        Intent intentFence = new Intent().setClass(this, EmergRFenceAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecSignUp = tabhost
                .newTabSpec("Location Sites")
                .setIndicator("", resources.getDrawable(R.drawable.boat33))
                .setContent(intentFence);

        Intent intentNew = new Intent().setClass(this, UserReportEmergAct.class);
        @SuppressLint("UseCompatLoadingForDrawables") TabHost.TabSpec tabSpecLogin = tabhost
                .newTabSpec("New Report")
                .setIndicator("", resources.getDrawable(R.drawable.boat33))
                .setContent(intentNew);

        tabhost.addTab(tabSpecSignUp);
        tabhost.addTab(tabSpecLogin);

        tabhost.setCurrentTab(0);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recoverAccount(profileUID,machine);
            }
        });
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Toast.makeText(EmergRTab.this, tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate (R.menu.show_emerg, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        boolean result;
        if(item.getItemId()== android.R.id.home){
            finish();
            result = true;
            //return true;

        }
        if(item.getItemId()== R.id.my_state_Pref){
            finish();
            result = true;

        }
        if (item.getItemId () == R.id.my_select_emerg) {
            handleSpinners ();
            result = true;
        } else {
            result = super.onOptionsItemSelected (item);
        }
        return result;
    }

    private void handleSpinners() {
        emergReportDAO= new EmergReportDAO(this);
        emergencyReport= new EmergencyReport();
        spnTab = findViewById(R.id.er_spn_tab);
        spnTab.setVisibility(View.VISIBLE);
        if(emergReportDAO !=null){
            if(state !=null){
                emergencyReports=emergReportDAO.getEmergencyReportForState(state);

            }else {
                emergencyReports=emergReportDAO.getEmergencyReportForCountry("Nigeria");
            }

        }
        reportArrayA = new EmergReportArrayA(EmergRTab.this, R.layout.emerg_array, emergencyReports);
        spnTab.setAdapter(reportArrayA);
        spnTab.setSelection(0);

        spnTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                emergencyReport = (EmergencyReport) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("Internet connection failed");
        }

        return hasInternetConnection;
    }
    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
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
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }


}