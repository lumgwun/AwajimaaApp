package com.skylightapp.MapAndLoc;

import static com.skylightapp.Classes.ImageUtil.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class UserLocatorAct extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;
    private static final String PREF_NAME = "awajima";
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
    private Gson gson, gson1;
    private String json, json1;
    private Profile userProfile;
    private int emergencyReportID;
    LatLng mostRecentLatLng;
    int PERMISSION_ALL = 1;
    private EmergencyReport emergencyReport;
    private boolean locationPermissionGranted;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest mLocationRequest;
    private CancellationTokenSource cancellationTokenSource;

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
        emergencyReport = new EmergencyReport();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            if (!hasPermissions(UserLocatorAct.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(UserLocatorAct.this, PERMISSIONS, PERMISSION_ALL);
            }
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile = new Profile();
        bundle = new Bundle();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        cancellationTokenSource = new CancellationTokenSource();

        bundle = getIntent().getExtras();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000);

        if (bundle != null) {
            emergencyReport = bundle.getParcelable("EmergencyReport");
            oldLatitude = bundle.getDouble("Lat");
            oldLongitude = bundle.getDouble("Lng");
            position = (LatLng) bundle.getParcelable("Location");

        }
        if (position == null) {
            position = new LatLng(oldLatitude, oldLongitude);

        }
        if (emergencyReport != null) {
            emergencyReportID = emergencyReport.getEmergReportID();
        }


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

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationClient.getCurrentLocation(2, cancellationTokenSource.getToken())
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location2) {
                        if (location2 != null) {
                            latitude = location2.getLatitude();
                            longitude = location2.getLongitude();
                            setResult(Activity.RESULT_OK, new Intent());
                            //setResult(Activity.RESULT_OK, new Intent().putExtras(userLocBundle));


                        } else {
                            latitude = 4.52871;
                            longitude = 7.44507;
                            Log.d(TAG, "Current location is null. Using defaults.");
                        }

                    }

                });
        latitude2=latitude;
        longitude2=longitude;
        mMap=googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            emergencyReport=bundle.getParcelable("EmergencyReport");
            oldLatitude=bundle.getDouble("Lat");
            oldLongitude=bundle.getDouble("Lng");
            position= (LatLng) bundle.getParcelable("Location");

        }
        if(mostRecentLatLng ==null){
            mostRecentLatLng = new LatLng(latitude2, longitude2);

        }

        MarkerOptions options = new MarkerOptions();

        options.position(mostRecentLatLng);

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
        mMap.addMarker(options);
        mMap.addMarker(options.position(new LatLng(latitude2,
                longitude2)));

        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(mostRecentLatLng);

        CameraUpdate updateZoom = CameraUpdateFactory.zoomTo(6);

        mMap.moveCamera(updatePosition);

        mMap.animateCamera(updateZoom);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NotNull LatLng latLng) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UserLocatorAct.this);
                builder.setTitle("Choose Reporter Location Action");
                builder.setIcon(R.drawable.marker_);
                builder.setItems(new CharSequence[]
                                {"View Address", "Get map Direction to the Reporter","Navigate to the Repoter's Address"},
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

                                        Toast.makeText(UserLocatorAct.this, " Navigate to the Repoerter, selected", Toast.LENGTH_SHORT).show();
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
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1000);
        latitude2 = location.getLatitude();
        longitude2 = location.getLongitude();
        mostRecentLatLng = new LatLng(latitude2, longitude2);
        MarkerOptions options = new MarkerOptions()
                .position(mostRecentLatLng)
                .title("Me!!");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mostRecentLatLng));
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mostRecentLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(19));


    }

    @Override
    public void onConnectionFailed(@NonNull @NotNull ConnectionResult connectionResult) {

    }
    public void createNotification(String title, String body) {
        final int NOTIFY_ID = 1002;

        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setLightColor( Color.GREEN);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);

            intent = new Intent(this, UserLocatorAct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            int flags = PendingIntent.FLAG_UPDATE_CURRENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE;
            }
            pendingIntent = PendingIntent.getActivity(this, 0, intent, flags);

            builder.setContentTitle(title)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentText(body)
                    .setDefaults( Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(this);


            intent = new Intent(this, UserLocatorAct.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(title)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                    .setContentText(body)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}