package com.skylightapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class UserLocatorAct extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;
    double latitude = 0;
    double longitude = 0;
    double latitude2 = 0;
    double longitude2 = 0;
    LatLng position;
    LatLng latLng;
    Bundle bundle;
    double oldLatitude, oldLongitude;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    Location location;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    //protected String latitude,longitude;
    protected boolean gps_enabled, network_enabled;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,

    };
    private ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            Toast.makeText(UserLocatorAct.this, "Activity returned ok", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(UserLocatorAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pack_loc);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }else {
            if (!hasPermissions(UserLocatorAct.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(UserLocatorAct.this, PERMISSIONS, PERMISSION_ALL);
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) this);
        bundle=getIntent().getExtras();
        oldLatitude = bundle.getDouble(String.valueOf(latitude),0);
        oldLongitude = bundle.getDouble(String.valueOf(longitude),0);
        position= (LatLng) bundle.getParcelable("Location");
        //latitude = getIntent().getDoubleExtra("lat", 0);
        //longitude = getIntent().getDoubleExtra("long", 0);
        // Getting Reference to SupportMapFragment of activity_map.xml
        SupportMapFragment fm = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (fm != null) {
            fm.getMapAsync(UserLocatorAct.this);
        }

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        bundle=getIntent().getExtras();
        position= (LatLng) bundle.getParcelable("Location");
        if(position !=null){
            oldLatitude=position.latitude;
            oldLongitude=position.longitude;

        }
        latitude2 = location.getLatitude();

        longitude2 = location.getLongitude();

        position = new LatLng(oldLatitude, oldLongitude);

        MarkerOptions options = new MarkerOptions();

        options.position(position);

        options.title("User Location");

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(oldLatitude,
                    oldLongitude, 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getAddressLine(1);
            String countryName = addresses.get(0).getAddressLine(2);
            options.snippet(cityName + "," + stateName);

        } catch (Exception ex) {
            ex.getMessage();
            options.snippet("Latitude:" + oldLatitude + ",Longitude:" + oldLongitude);

        }
        googleMap.addMarker(options);
        googleMap.addMarker(options.position(new LatLng(latitude2,
                longitude2)));

        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(position);

        CameraUpdate updateZoom = CameraUpdateFactory.zoomTo(6);

        googleMap.moveCamera(updatePosition);

        googleMap.animateCamera(updateZoom);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(@NotNull LatLng latLng) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserLocatorAct.this);
                builder.setTitle("Choose Customer Location Action");
                builder.setIcon(R.drawable.marker_);
                builder.setItems(new CharSequence[]
                                {"View Address", "Get map Direction to the Customer","Navigate to the Customer's Address"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        String url = "http://maps.google.com/maps?daddr=" + position.latitude + "," + position.longitude;
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                        startActivity(intent);
                                        break;
                                    case 1:

                                        Toast.makeText(UserLocatorAct.this, "You have selected, get Direction", Toast.LENGTH_SHORT).show();
                                        String url1 = "https://www.google.com/maps/dir/?api=1&destination=" + position.latitude + "," + position.longitude + "&travelmode=driving";
                                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                                        startActivity(intent1);

                                        break;


                                    case 2:

                                        Toast.makeText(UserLocatorAct.this, " Navigate to the Customer, selected", Toast.LENGTH_SHORT).show();
                                        Uri gmmIntentUri = Uri.parse("google.navigation:q=position.latitude,position.longitude");
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps");
                                        startActivity(mapIntent);

                                        break;

                                }
                            }
                        })
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                builder.create().show();


            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public void onConnected(@Nullable @org.jetbrains.annotations.Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }



    @Override
    public void onLocationChanged(@NonNull @NotNull Location location) {


    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }
}