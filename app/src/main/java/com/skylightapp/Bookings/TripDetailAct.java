package com.skylightapp.Bookings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.Calendar;

public class TripDetailAct extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    public static double baseFare = 2.55;
    public static final double timeRate = 0.35;
    public static final double distanceRate = 1.75;
    private FloatingActionButton fabShare;
    private Bundle bundle;
    private String tripType;
    private String currency;
    private String date;
    private String type;
    private String endTime,from;
    private int duration;
    private String startTime;
    private String status;
    private String destination;
    private String vehicleType;
    LatLng tripStartLatLng,tripEndLatLng;
    private Trip trip;
    private boolean success;
    private double totalAmt;
    private PolylineOptions polylineOptions;
    private double latitude1,latitude2,lng1,lng2;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private int seatRem,seatBooked,totalSeats;
    private AppCompatButton btnBookTrip,btnStartTrip,btnSendTripMes;
    private AppCompatTextView txtSeatsRem;
    private Driver driver;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    private int profileID;
    String SharedPrefUserPassword;

    String SharedPrefUserMachine;
    String SharedPrefUserName;
    int SharedPrefProfileID;
    String selectedTo;
    String selectedFrom;
    private int driverProfID;
    private String tripCurrency;
    private AppCompatImageButton btnCloseSheet;
    private AppCompatTextView txtSheetRem,txtAmtPerSeat;
    private AppCompatEditText edtNoToBook;
    private AppCompatButton btnNewBooking;
    private BottomSheetBehavior sheetBehavior;
    private LinearLayoutCompat layoutSheet;

    private AppCompatTextView txtDate, txtFee, txtBaseFare, txtTime, txtDistance, txtEstimatedPayout,txtRtipType,txtConveyer, txtFrom, txtTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trip_detail);
        bundle=new Bundle();
        userProfile=new Profile();
        gson1 = new Gson();
        gson = new Gson();
        driver= new Driver();
        trip= new Trip();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        bundle=getIntent().getExtras();
        if(bundle !=null){
            tripType=bundle.getString("tripType");
            trip=bundle.getParcelable("Trip");

        }
        if(trip !=null){
            if(tripType==null){
                tripType=trip.getTripType();

            }


        }
        txtDate=findViewById(R.id.txtDate);
        txtFee=findViewById(R.id.txtFee);
        txtBaseFare=(AppCompatTextView)findViewById(R.id.txtBaseFare);
        txtTime=(AppCompatTextView)findViewById(R.id.txtTime);
        txtDistance=(AppCompatTextView)findViewById(R.id.txtDistance);
        txtEstimatedPayout=(AppCompatTextView)findViewById(R.id.txtEstimatedPayout);
        txtFrom=(AppCompatTextView)findViewById(R.id.txtFrom);
        txtTo=(AppCompatTextView)findViewById(R.id.txtTo);
        txtRtipType=(AppCompatTextView)findViewById(R.id.txt_Trip_type);
        txtConveyer=(AppCompatTextView)findViewById(R.id.txt_Conveyer);
        btnBookTrip=findViewById(R.id.book_trip);
        btnStartTrip=findViewById(R.id.start_Trip);
        btnSendTripMes=findViewById(R.id.message_trip);

        txtSeatsRem=(AppCompatTextView)findViewById(R.id.txt_SeatR);
        btnCloseSheet=findViewById(R.id.close_sheet);
        txtSheetRem=findViewById(R.id.seats_rem);

        txtAmtPerSeat=findViewById(R.id.amt_per_sit);
        edtNoToBook=findViewById(R.id.edt_no_book);
        btnNewBooking=findViewById(R.id.btn_sumit_booking);
        layoutSheet=findViewById(R.id.sheet_book_L);


        btnCloseSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });
        btnNewBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        fabShare=findViewById(R.id.fabShareTripD);
        btnStartTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnStartTrip.setOnClickListener(this::startTrip);
        btnSendTripMes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSendTripMes.setOnClickListener(this::sendTripMessage);
        btnBookTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutSheet.setVisibility(View.VISIBLE);
                sheetBehavior = BottomSheetBehavior.from(layoutSheet);

            }
        });
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        btnBookTrip.setOnClickListener(this::bookTrip);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }


    @SuppressLint("PotentialBehaviorOverride")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        bundle=getIntent().getExtras();
        if(bundle !=null){
            tripType=bundle.getString("tripType");
            trip=bundle.getParcelable("Trip");

        }
        if(tripType !=null){
            if(tripType.equalsIgnoreCase("Train Trip")){

                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = mMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    this, R.raw.rail_map));

                    if (!success) {
                        Log.e("MapsActivityRaw", "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("MapsActivityRaw", "Can't find style.", e);
                }
                //mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.uber_style_map));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(4.8360, 7.0138)));


            }
            if(tripType.equalsIgnoreCase("Boat Trip")){

                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = mMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    this, R.raw.river_map));

                    if (!success) {
                        Log.e("MapsActivityRaw", "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("MapsActivityRaw", "Can't find style.", e);
                }
                //mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.uber_style_map));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-34, 151)));


            }
            if(tripType.equalsIgnoreCase("Taxi Trip")){

                try {
                    // Customise the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = mMap.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    this, R.raw.taxi_map));

                    if (!success) {
                        Log.e("MapsActivityRaw", "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e("MapsActivityRaw", "Can't find style.", e);
                }

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(4.824167, 7.033611)));


            }

        }
        //mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
        mMap.setOnMarkerClickListener(this);
        settingInformation();
    }

    @SuppressLint("DefaultLocale")
    private void settingInformation() {
        bundle= new Bundle();
        gson= new Gson();
        userProfile= new Profile();
        btnBookTrip=findViewById(R.id.book_trip);
        btnStartTrip=findViewById(R.id.start_Trip);
        btnSendTripMes=findViewById(R.id.message_trip);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        bundle=getIntent().getExtras();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        driver= new Driver();
        if(bundle !=null){
            tripType=bundle.getString("tripType");
            trip=bundle.getParcelable("Trip");
            totalAmt=bundle.getDouble("total");

        }
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        txtSeatsRem=(AppCompatTextView)findViewById(R.id.txt_SeatR);

        if(trip !=null){
            currency=trip.getBtTripCurrency();
            date=trip.getBtDate();
            type=trip.getTripType();
            endTime=trip.getBtEndTime();
            destination=trip.getBtFinalDestination();
            startTime=trip.getBtStartTime();
            status=trip.getBtStatus();
            vehicleType=trip.getBtTypeOfBoat();
            duration=trip.getBoatTripDuration();
            baseFare=trip.getBtAmountForAdult();
            from=trip.getBtLoadingPointLatLngStrng();
            tripStartLatLng=trip.getBtTakeOffLatLng();
            tripEndLatLng=trip.getBtFinalDestLatLng();
            totalSeats=trip.getaTripNoOfSits();
            seatRem=trip.getBtNoOfSitRem();
            driver=trip.getTripDriver();

        }
        if(driver !=null){
            driverProfID=driver.getDriverProfID();

        }
        if(driverProfID==profileID){
            btnBookTrip.setVisibility(View.GONE);
            btnStartTrip.setVisibility(View.VISIBLE);
            btnSendTripMes.setVisibility(View.VISIBLE);

        }else {
            btnBookTrip.setVisibility(View.VISIBLE);
            btnStartTrip.setVisibility(View.GONE);
            btnSendTripMes.setVisibility(View.GONE);

        }
        if(tripStartLatLng !=null){
            lng2=tripStartLatLng.longitude;
            latitude1=tripStartLatLng.latitude;
        }
        if(tripEndLatLng !=null){
            lng2=tripEndLatLng.longitude;
            latitude2=tripEndLatLng.latitude;
        }
        if(getIntent() != null) {
            Calendar calendar = Calendar.getInstance();
            if(date !=null){
                date = String.format("%s, %d/%d", convertToDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)), calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH));
            }

            txtSeatsRem.setText(MessageFormat.format("Seat Remaing:{0}", seatRem));
            txtDate.setText(MessageFormat.format("Date{0}", date));
            txtFee.setText(MessageFormat.format("Base Fare{0}{1}", currency, baseFare));
            txtEstimatedPayout.setText(MessageFormat.format("Total Amount:{0}{1}", currency, totalAmt));
            txtBaseFare.setText(MessageFormat.format("{0}", baseFare));
            txtTime.setText(MessageFormat.format("From{0}to{1}", startTime, endTime));
            txtDistance.setText(MessageFormat.format("distance:{0}", Utils.taxiDistance(tripStartLatLng, tripEndLatLng, "M")));
            txtFrom.setText(MessageFormat.format("From{0}", from));
            txtTo.setText(MessageFormat.format("End Point:{0}", destination));

            mMap.addMarker(new MarkerOptions().position(tripStartLatLng)
                    .title("Drop Off Here")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_)));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(tripEndLatLng, 17.0f));
            polylineOptions = new PolylineOptions()
                    .add(new LatLng(latitude1, lng1))
            .add(new LatLng(latitude2, lng2));
            polylineOptions.width(10);
            polylineOptions.color(Color.RED);
            polylineOptions.geodesic(true);

            Polyline polyline = mMap.addPolyline(polylineOptions);

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
        //mapView.onPause();
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

    public void startTrip(View view) {
    }

    public void bookTrip(View view) {
    }

    public void sendTripMessage(View view) {
    }
}