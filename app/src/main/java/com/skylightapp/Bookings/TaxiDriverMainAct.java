package com.skylightapp.Bookings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.LocationServices;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Customers.CustomerHelpActTab;
import com.skylightapp.R;
import com.teliver.sdk.core.Teliver;
import com.teliver.sdk.models.UserBuilder;

public class TaxiDriverMainAct extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, FragmentManager.OnBackStackChangedListener {
    AppCompatButton login;
    private GoogleApiClient mGoogleApiClient;
    double latitude, longitude;
    private SharedPreferences userPreferences;
    private Gson gson, gson1,gson2;
    private String json, json1,json2;
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
    private AppCompatTextView txtDriver,txtGreetings;
    private Toolbar toolbar;
    private TaxiDriver driver;

    private FragmentManager fragmentManager;

    private View rootView;

    private Snackbar snackbar;
    private String driverOnlineID;
    private int driverID;

    private FragDriver fragmentDriver;

    private TaxiTrip taxiTrip;
    //private TaxiTripDAO taxiTripDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_taxi_driver_main);
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
        customer = new Customer();
        bundle= new Bundle();
        driver= new TaxiDriver();
        bundle= new Bundle();
        txtDriver = findViewById(R.id.driverName);
        txtGreetings = findViewById(R.id.driverGreetings);
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
        json2 = userPreferences.getString("LastTaxiDriverUsed", "");
        driver = gson2.fromJson(json2, TaxiDriver.class);

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
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_arrow_back_black_24dp);
        gson2 = new Gson();
        driver= new TaxiDriver();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = userPreferences.getString("PROFILE_USERNAME", "");
        setSupportActionBar(toolbar);
        json2 = userPreferences.getString("LastTaxiDriverUsed", "");
        driver = gson2.fromJson(json2, TaxiDriver.class);
        //Utils.setUpToolBar(this, toolbar, getSupportActionBar(), getString(R.string.app_name));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        rootView = findViewById(R.id.view_root);
        if(driver !=null){
            driverID=driver.driverID;
        }
        driverOnlineID=SharedPrefUserName+driverID;
        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        changeFragment(0);
        Teliver.identifyUser(new UserBuilder(driverOnlineID)
                .setUserType(UserBuilder.USER_TYPE.OPERATOR).build());
    }

    private void changeFragment(int caseValue) {
        if (caseValue == 0) {
            if (fragmentDriver == null)
                fragmentDriver = new FragDriver();
            switchView(fragmentDriver, getString(R.string.app_name));
        }
    }

    private void switchView(final Fragment fragment, final String title) {
        try {
            toolbar.setTitle(title);
            FragmentTransaction mFragmentTransaction = fragmentManager.beginTransaction();
            Fragment mTempFragment = fragmentManager.findFragmentById(R.id.driver_container);
            if (!fragment.equals(mTempFragment)) {
                String className = fragment.getClass().getName();
                boolean isAdded = fragmentManager.popBackStackImmediate(className, 0);
                if (!isAdded) {
                    mFragmentTransaction.addToBackStack(className);
                    mFragmentTransaction.add(R.id.driver_container, fragment, title);
                }
            }
            mFragmentTransaction.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackStackChanged() {
        Fragment fragment = fragmentManager.findFragmentById(R.id.driver_container);
        if (fragment == null)
            return;
        String tag = fragment.getTag();
        toolbar.setTitle(tag);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1)
            fragmentManager.popBackStackImmediate();
        else if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
            finish();
        } else {
            snackbar = Snackbar.make(rootView, R.string.txt_press_back, 3000);
            snackbar.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 115)
            fragmentDriver.onReqPermission(grantResults);
    }

    @Override
    public void onDestroy() {
        mGoogleApiClient.disconnect();
        super.onDestroy();
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
                /*URLpetition petition = new URLpetition("login driver");

                StringBuilder sb = new StringBuilder();
                sb.append(getResources().getString(R.string.ip));
                sb.append("drivers/login?id=");
                sb.append(SharedPrefUserName);
                sb.append("&latitude=");
                sb.append(latitude);
                sb.append("&longitude=");
                sb.append(longitude);

                petition.execute(sb.toString());*/
                break;
        }
    }
    public static double[] getNeighbourhoodArea(
            final double lat, final double lng, final int distInMtrs) {

        double[] area = new double[4];

        final double latRadian = Math.toRadians(lat);

        final double degLatKm = 110.574235;
        final double degLngKm = 110.572833 * Math.cos(latRadian);
        final double deltaLat = distInMtrs / 1000.0 / degLatKm;
        final double deltaLong = distInMtrs / 1000.0 / degLngKm;

        final double minLat = lat - deltaLat;
        final double minLng = lng - deltaLong;
        final double maxLat = lat + deltaLat;
        final double maxLng = lng + deltaLong;

        area[0] = minLat;
        area[1] = minLng;
        area[2] = maxLat;
        area[3] = maxLng;

        return area;
    }


    /*private PointOfInterest collectPOIs(double lat, double lng) {

        if (mDb == null) return Const.NULL_POI;

        Cursor cursorStat = mDb.getPoisInArea(lat, lng, Const.SIDE_LENGTH_GEO_OFFSET);

        double area[] = Logic.getProtectionArea(lat, lng, Const.SIDE_LENGTH_GEO_OFFSET);

        ArrayList<PointOfInterest> poiArray = new ArrayList<PointOfInterest>();

        PointOfInterest poi = Const.NULL_POI;

        if (cursorStat.moveToFirst()) {
            for (int i = 0; i < cursorStat.getCount(); i++) {
                double potLat = cursorStat.getFloat(Const.COL_LA);
                double potLng = cursorStat.getFloat(Const.COL_LO);

                if ((potLat < area[Const.MAX_LAT] && potLat > area[Const.MIN_LAT])
                        && (potLng < area[Const.MAX_LNG] && potLng > area[Const.MIN_LNG])) {

                    poi = Logic.getPoiByCursor(getApplicationContext(), cursorStat);
                    poiArray.add(poi);

                }
                cursorStat.moveToNext();
            } // End "Cursor"
        }
        cursorStat.close();

        // more than once, fire the nearest
        if (poiArray.size() > 1) return closest(poiArray, lat, lng);
        else return poi; // one or null
    }*/



    public void loginToRides(View view) {
    }


    private class URLpetition extends AsyncTask<String, Void, String> {
        String action;

        public URLpetition(String action) {
            this.action = action;
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            Log.d("url = ", params[0]);
            HttpGet get = new HttpGet(params[0]);
            String retorno = "";
            StringBuilder stringBuilder = new StringBuilder();
            try {
                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();
                //InputStream stream = new InputStream(entity.getContent(),"UTF-8");
                InputStream stream = entity.getContent();
                BufferedReader r = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                String line;
                while ((line = r.readLine()) != null) {
                    stringBuilder.append(line);
                }

                if (action.equals("login driver")) {
                    return stringBuilder.toString();
                }
            } catch (IOException e) {
                Log.d("Error: ", e.getMessage());
            }
            Log.d("Return text = ", retorno);
            return retorno;
        }

        @Override
        protected void onPostExecute(String result) {
            if (action.equals("login driver")) {
                if (result.equals("id not valid")) {
                    showMSG("ID not valid");
                } else {
                    //showMSG(result);
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String name, lastname, vehicle;
                        name = jsonObject.getString("name");
                        lastname = jsonObject.getString("last_name");
                        vehicle = jsonObject.getString("vehicle");
                        saveSession(name, lastname, vehicle);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        @Override
        protected void onPreExecute() {
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
        customer = new Customer();
        bundle= new Bundle();
        driver= new TaxiDriver();
        txtDriver = findViewById(R.id.driverName);
        txtGreetings = findViewById(R.id.driverGreetings);
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
        json2 = userPreferences.getString("LastTaxiDriverUsed", "");
        driver = gson2.fromJson(json2, TaxiDriver.class);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        latitude = mLastLocation.getLatitude();
        longitude = mLastLocation.getLongitude();

        login = findViewById(R.id.loginForRide);

        login.setOnClickListener(this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void showMSG(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void saveSession(String name, String lastname, String vehicle) {
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        customer = new Customer();
        txtDriver = findViewById(R.id.driverName);
        txtGreetings = findViewById(R.id.driverGreetings);

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
            txtGreetings.setText(R.string.good_morning);
        } else if (timeOfDay >= 12 && timeOfDay < 17) {
            //welcomeString.append(getString(R.string.good_afternoon));
            txtGreetings.setText(R.string.good_afternoon);
        } else {
            //welcomeString.append(getString(R.string.good_evening));
            txtGreetings.setText(R.string.good_evening);
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

        editor.putString("name", name);
        editor.putString("driver_id",SharedPrefUserName);
        editor.putString("lastname", lastname);
        editor.putString("vehicle", vehicle);
        editor.apply();

        //showMSG("Welcome "+name + " " + lastname);
        bundle.putParcelable("Profile",userProfile);
        bundle.putString("name", name);
        bundle.putString("driver_id",SharedPrefUserName);
        bundle.putString("lastname", lastname);
        bundle.putString("vehicle", vehicle);
        Intent rideIntent = new Intent(this, TaxiDriverRideAct.class);
        rideIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        rideIntent.putExtras(bundle);
        startActivity(rideIntent);
        this.finish();
    }

}