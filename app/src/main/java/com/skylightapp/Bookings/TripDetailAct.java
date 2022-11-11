package com.skylightapp.Bookings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.skylightapp.R;

import java.util.Calendar;

public class TripDetailAct extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    public static final double baseFare = 2.55;
    public static final double timeRate = 0.35;
    public static final double distanceRate = 1.75;
    private AppCompatTextView txtDate, txtFee, txtBaseFare, txtTime, txtDistance, txtEstimatedPayout, txtFrom, txtTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trip_detail);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        txtDate=(AppCompatTextView)findViewById(R.id.txtDate);
        txtFee=(AppCompatTextView)findViewById(R.id.txtFee);
        txtBaseFare=(AppCompatTextView)findViewById(R.id.txtBaseFare);
        txtTime=(AppCompatTextView)findViewById(R.id.txtTime);
        txtDistance=(AppCompatTextView)findViewById(R.id.txtDistance);
        txtEstimatedPayout=(AppCompatTextView)findViewById(R.id.txtEstimatedPayout);
        txtFrom=(AppCompatTextView)findViewById(R.id.txtFrom);
        txtTo=(AppCompatTextView)findViewById(R.id.txtTo);


    }

    @SuppressLint("PotentialBehaviorOverride")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap = googleMap;
        //mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.setOnMarkerClickListener(this);
        settingInformation();
    }

    @SuppressLint("DefaultLocale")
    private void settingInformation() {
        if(getIntent() != null) {
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("DefaultLocale") String date = String.format("%s, %d/%d", convertToDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH));
            txtDate.setText(date);
            txtFee.setText(String.format("$ %.2f", getIntent().getDoubleExtra("total", 0.0)));
            txtEstimatedPayout.setText(String.format("$ %.2f", getIntent().getDoubleExtra("total", 0.0)));
            txtBaseFare.setText(String.format("$ %.2f", baseFare));
            txtTime.setText(String.format("%s min", getIntent().getStringExtra("time")));
            txtDistance.setText(String.format("%s km", getIntent().getStringExtra("distance")));
            txtFrom.setText(getIntent().getStringExtra("start_address"));
            txtTo.setText(getIntent().getStringExtra("end_address"));

            //add icon_marker
            String[] location_end = getIntent().getStringExtra("location_end").split(",");
            LatLng dropOff = new LatLng(Double.parseDouble(location_end[0]), Double.parseDouble(location_end[1]));

            mMap.addMarker(new MarkerOptions().position(dropOff)
                    .title("Drop Off Here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_)));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dropOff, 12.0f));
        }
    }

    private String convertToDayOfWeek(int day) {
        switch(day){
            case Calendar.SUNDAY:
                return "SUNDAY";
            case Calendar.MONDAY:
                return "MONDAY";
            case Calendar.TUESDAY:
                return "TUESDAY";
            case Calendar.WEDNESDAY:
                return "WEDNESDAY";
            case Calendar.THURSDAY:
                return "THURSDAY";
            case Calendar.FRIDAY:
                return "FRIDAY";
            case Calendar.SATURDAY:
                return "SATURDAY";
            default:
                return "UNK";
        }
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }
}