package com.skylightapp.Bookings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Calendar;

public class BoatDriverMainAct extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    AppCompatButton login;
    private GoogleApiClient mGoogleApiClient;
    double latitude, longitude;
    private SharedPreferences userPreferences;
    private Gson gson, gson1;
    private String json, json1;
    private Profile userProfile;
    private Customer customer;
    String SharedPrefUserPassword;
    String SharedPrefCusID;
    String SharedPrefUserMachine,surName,firstName;
    String SharedPrefUserName;
    int SharedPrefProfileID;
    private Calendar calendar;
    private static final String PREF_NAME = "awajima";
    private Bundle bundle;
    private AppCompatTextView txtDriver;
    private AppCompatImageView imgDGreetings;
    private RecyclerView recyclerViewTrip;
    private Trip trip;
    private TripBooking tripBooking;
    private ArrayList<TripBooking> tripBookings;
    private ProgressDialog progressDialog;
    ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_boat_driver_main);
        checkInternetConnection();
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        customer = new Customer();
        bundle= new Bundle();
        trip = new Trip();
        tripBooking= new TripBooking();
        tripBookings= new ArrayList<>();
        txtDriver = findViewById(R.id.boat_DriverName);
        recyclerViewTrip = findViewById(R.id.recyTrip);
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
        bundle=getIntent().getExtras();
        if(bundle !=null){
            trip = bundle.getParcelable("Trip");


        }
        if(trip !=null){
            tripBookings= trip.getBtBookings();
        }

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            mGoogleApiClient.connect();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_driver_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
            case R.id.loginForRide:
                userProfile = new Profile();
                gson1 = new Gson();
                gson = new Gson();
                customer = new Customer();
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
                break;
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        customer = new Customer();
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude();
        }
        if (mLastLocation != null) {
            longitude = mLastLocation.getLongitude();
        }

        login = findViewById(R.id.loginForRide);

        login.setOnClickListener(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void saveSession(String name, String lastname, String vehicle) {
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        customer = new Customer();
        txtDriver = findViewById(R.id.boat_DriverName);
        imgDGreetings = findViewById(R.id.bGreetings);

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
        surName = userPreferences.getString("PROFILE_SURNAME", "");
        firstName = userPreferences.getString("PROFILE_FIRSTNAME", "");
        name= surName+","+ firstName;
        bundle= new Bundle();

        StringBuilder welcomeString = new StringBuilder();

        int timeOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 5 && timeOfDay < 12) {
            //welcomeString.append(getString(R.string.good_morning));
            imgDGreetings.setImageResource(R.drawable.good_morn3);
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            welcomeString.append(getString(R.string.good_afternoon));
            imgDGreetings.setImageResource(R.drawable.good_after1);
        } else {
            welcomeString.append(getString(R.string.good_evening));
            imgDGreetings.setImageResource(R.drawable.good_even2);
        }
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String[] days = getResources().getStringArray(R.array.days);
        String dow = "";

        switch (day) {
            case Calendar.SUNDAY:
                dow = days[0];
                break;
            case Calendar.MONDAY:
                dow = days[1];
                break;
            case Calendar.TUESDAY:
                dow = days[2];
                break;
            case Calendar.WEDNESDAY:
                dow = days[3];
                break;
            case Calendar.THURSDAY:
                dow = days[4];
                break;
            case Calendar.FRIDAY:
                dow = days[5];
                break;
            case Calendar.SATURDAY:
                dow = days[6];
                break;
            default:
                break;
        }


        welcomeString.append(name + "")
                .append("How are you today? ")
                .append(getString(R.string.happy))
                .append(dow);
        txtDriver.setText(welcomeString);

        SharedPreferences.Editor editor = userPreferences.edit();

        editor.putString("DRIVER_NAME", name);
        editor.putString("DRIVER_ID",SharedPrefUserName);
        editor.putString("PROFILE_SURNAME", lastname);
        editor.putString("DRIVER_VEHICLE", vehicle);
        editor.apply();

        //showMSG("Welcome "+name + " " + lastname);
        bundle.putParcelable("Profile",userProfile);
        bundle.putString("DRIVER_NAME", name);
        bundle.putString("DRIVER_ID",SharedPrefUserName);
        bundle.putString("PROFILE_SURNAME", lastname);
        bundle.putString("DRIVER_VEHICLE", vehicle);
        Intent rideIntent = new Intent(this, TaxiDriverRideAct.class);
        rideIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        rideIntent.putExtras(bundle);
        startActivity(rideIntent);
        this.finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);//you can cancel it by pressing back button
        progressDialog.setMessage("signing up wait ...");
        progressBar.show();//displays the progress bar
    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
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
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
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
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }
    public void onResume(){
        super.onResume();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);
    }
}