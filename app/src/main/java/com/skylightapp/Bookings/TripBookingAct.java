package com.skylightapp.Bookings;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.BoatTripDAO;
import com.skylightapp.Database.BoatTripRouteDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.GooglePayAct;
import com.skylightapp.NinIDAct;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class TripBookingAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener,BoatTripAdapter.BoatTripListener {

    private Spinner spnNoOfBoats, spnTripType, spnBoatTypes,spnTripRoutes,spnState,spnAdults,spnChildren,spnTripPoints;
    private ArrayAdapter<String> personAdapter;
    ArrayList<String> tourType = new ArrayList<String>();
    private ArrayList<String> scheduleList = new ArrayList<String>();
    private ArrayAdapter<String> typeAdapter;
    private double totalTarrif;
    private TextView tvTotalTarrif;
    private TextView txtTourDate;
    private int day;
    private int month;
    private int year;
    Calendar cal = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateset;
    private DatePickerDialog tourDatePiker;
    private Bundle bundle;
    private ArrayList<BoatTrip> boatTripArrayList;
    private ArrayList<BoatTripRoute> boatTripRoutes;
    private BoatTrip boatTrip;
    private String selectedTripType,selectedState,selectedNoOfBoats,adults,children,selectedBoatType;
    private int selectedRouteIndex,boatTripID,profID,noOfBoatInt;
    private  BoatTripRoute boatTripRoute;
    private BoatTripRouteAdap boatTripRouteAdap;
    private BoatTripRouteDAO boatTripRouteDAO;
    private RecyclerView recyclerViewTrips;
    private AppCompatButton btnJoinTrip,btnCharterTrip,btnDoCustomBooking;
    private LinearLayoutCompat layoutJoinTrip,layoutCustomTrip;
    private static final String PREF_NAME = "awajima";
    Gson gson, gson1;
    String json, tripTakeOffPoints,tripDestination,userNIN,takeOffState,dateOfTripRequest, selectedSitStrng,stopPointName;
    Profile userProfile;
    SharedPreferences userPreferences;
    private BoatTripDAO boatTripDAO;
    private SQLiteDatabase sqLiteDatabase;
    private DBHelper dbHelper;
    private ImageButton imageButton;
    private AppCompatTextView txtTripTK,txtFinalDest,txtUnBookedSits,txtTripAmount,txtTotalAmt;
    private Spinner spnSitSel;
    private AppCompatButton btnGoToPayment;
    private LinearLayoutCompat layoutCompatSheet;
    private BottomSheetBehavior behavior;
    private int selectedSitInt,selectedTripIndex;
    private BoatTripAdapter boatTripAdapter;
    private int unbookedSitsNo,selectedPointIndex;
    private double amount;
    private long totalAmount;
    private TripStopPoint tripStopPoint;
    private ArrayList<TripStopPoint> tripStopPoints;
    private TripStopPointAdap tripStopPointAdap;
    SharedPreferences.Editor editor;
    private ActivityResultLauncher<Intent> startNinActForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            userProfile = new Profile();
                            gson = new Gson();
                            userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                            json = userPreferences.getString("LastProfileUsed", "");
                            userProfile = gson.fromJson(json, Profile.class);
                            userNIN=userPreferences.getString("PROFILE_NIN", "");
                            Toast.makeText(TripBookingAct.this, "Activity returned ok", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(TripBookingAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trip_booking);
        setTitle("Trip Booking");
        bundle= new Bundle();
        tripStopPoint = new TripStopPoint();
        boatTrip= new BoatTrip();
        dbHelper= new DBHelper(this);
        bundle = getIntent().getExtras();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        boatTripRoute= new BoatTripRoute();
        boatTripRoutes= new ArrayList<>();
        tripStopPoints = new ArrayList<>();
        boatTripRouteDAO= new BoatTripRouteDAO(this);

        boatTripDAO= new BoatTripDAO(this);
        boatTripArrayList= new ArrayList<>();
        userProfile = new Profile();
        gson = new Gson();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        layoutJoinTrip =  findViewById(R.id.layout_JoinT);
        layoutCustomTrip =  findViewById(R.id.layout_CustomTrip);
        recyclerViewTrips =  findViewById(R.id.recyler_Trip);
        btnJoinTrip =  findViewById(R.id.btn_JoinTrip);
        btnCharterTrip =  findViewById(R.id.btn_CharterTrip);
        btnDoCustomBooking =  findViewById(R.id.btnBooking);
        imageButton =  findViewById(R.id.close_trip_sheet);
        txtTripTK =  findViewById(R.id.takeOff_PointT);
        txtFinalDest =  findViewById(R.id.dest_PointT);
        txtUnBookedSits =  findViewById(R.id.sits_left);
        txtTripAmount =  findViewById(R.id.trip_amountPerSit);
        spnSitSel =  findViewById(R.id.spnTripNoP);
        txtTotalAmt =  findViewById(R.id.trip_TotalP);

        btnGoToPayment =  findViewById(R.id.btnPayment_Trip);
        layoutCompatSheet =  findViewById(R.id.trip_bottomSheet);


        if(userProfile !=null){
            profID=userProfile.getPID();
        }
        behavior = BottomSheetBehavior.from(layoutCompatSheet);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    behavior.setPeekHeight(0);
                }
            }
        });

        spnTripType = (Spinner) findViewById(R.id.spnTripReturnType);
        spnTripRoutes = (Spinner) findViewById(R.id.spnTripRoute);
        spnState = (Spinner) findViewById(R.id.spnTripState);
        spnAdults = (Spinner) findViewById(R.id.spnAdults);
        spnChildren = (Spinner) findViewById(R.id.spnChildren);


        //spnTourSchedule = (Spinner) findViewById(R.id.spnTourSchedule);
        spnBoatTypes = (Spinner) findViewById(R.id.spnBoatType);
        spnNoOfBoats = (Spinner) findViewById(R.id.spnNoOfBoats);
        tvTotalTarrif = (TextView) findViewById(R.id.tvTotalTarrif);
        txtTourDate = (TextView) findViewById(R.id.txtTourDate);

        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = spnState.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnSitSel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSitStrng = spnSitSel.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(selectedSitStrng !=null){
            selectedSitInt= Integer.parseInt(selectedSitStrng);

        }


        if(selectedState !=null){
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                sqLiteDatabase = dbHelper.getReadableDatabase();
                boatTripRoutes=boatTripRouteDAO.getBoatTripRouteForState(selectedState);
            }


        }
        if(selectedState !=null){
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                sqLiteDatabase = dbHelper.getReadableDatabase();
                boatTripArrayList=boatTripDAO.getBoatTripForState(selectedState);
            }


        }


        btnJoinTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutJoinTrip.setVisibility(View.VISIBLE);
                layoutCustomTrip.setVisibility(View.GONE);


            }
        });
        btnCharterTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutJoinTrip.setVisibility(View.GONE);
                layoutCustomTrip.setVisibility(View.VISIBLE);

            }
        });
        btnJoinTrip.setOnClickListener(this::JoinATripBooking);
        btnCharterTrip.setOnClickListener(this::CharterTrip);

        boatTripRouteAdap = new BoatTripRouteAdap(TripBookingAct.this,R.layout.boat_route_item, boatTripRoutes);
        spnTripRoutes.setAdapter(boatTripRouteAdap);
        spnTripRoutes.setSelection(0);
        selectedRouteIndex = spnTripRoutes.getSelectedItemPosition();

        try {
            boatTripRoute = boatTripRoutes.get(selectedRouteIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }




        typeAdapter = new ArrayAdapter<String>(this,
                R.layout.tour_spinner_item, R.id.tvSpnItem, tourType);
        spnTripType.setAdapter(typeAdapter);


        spnTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTripType = spnTripType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnNoOfBoats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedNoOfBoats = spnNoOfBoats.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnAdults.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adults = spnState.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnChildren.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                children = spnState.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnBoatTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBoatType = spnBoatTypes.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        boatTripAdapter = new BoatTripAdapter(TripBookingAct.this,R.layout.boat_trip_item, boatTripArrayList);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewTrips.setLayoutManager(layoutManager);
        recyclerViewTrips.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTrips.setAdapter(boatTripAdapter);
        recyclerViewTrips.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewTrips);
        behavior = BottomSheetBehavior.from(layoutCompatSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });


        spnNoOfBoats.setAdapter(personAdapter);
        spnTripType.setOnItemSelectedListener(this);

        spnNoOfBoats.setOnItemSelectedListener(this);
        findViewById(R.id.btnBooking).setOnClickListener(this);
        txtTourDate.setOnClickListener(this);

        dateset = new DatePickerDialog.OnDateSetListener() {
            private String userDate;

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                userDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                txtTourDate.setText(userDate);
                dateOfTripRequest = year + "-" + (monthOfYear + 1)  + "-" + dayOfMonth;
            }
        };
        if(boatTripRoute !=null){
            tripTakeOffPoints=boatTripRoute.getBtRouteFrom();
            tripDestination=boatTripRoute.getBtRouteTo();
            takeOffState=boatTripRoute.getBtRouteState();
            //takeOffThrough=boatTripRoute.getBtRouteThrough();

        }
        noOfBoatInt= Integer.parseInt(selectedNoOfBoats);
        boatTripID = ThreadLocalRandom.current().nextInt(107, 1731);
        btnDoCustomBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boatTrip= new BoatTrip(boatTripID, profID, noOfBoatInt,selectedBoatType, tripTakeOffPoints, tripDestination, takeOffState, dateOfTripRequest,selectedTripType, "");

                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    boatTripDAO.insertBoatTrip(boatTripID, profID, noOfBoatInt,selectedBoatType, tripTakeOffPoints, tripDestination, takeOffState, dateOfTripRequest,selectedTripType);

                }

            }
        });
        btnDoCustomBooking.setOnClickListener(this::bookCustomTrip);
    }
    @Override
    public void onItemClick(BoatTrip item) {
        userProfile = new Profile();
        gson = new Gson();
        tripStopPoints = new ArrayList<>();

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        userNIN=userPreferences.getString("PROFILE_NIN", "");
        txtTripTK =  findViewById(R.id.takeOff_PointT);
        txtFinalDest =  findViewById(R.id.dest_PointT);
        txtUnBookedSits =  findViewById(R.id.sits_left);
        txtTripAmount =  findViewById(R.id.trip_amountPerSit);
        spnSitSel =  findViewById(R.id.spnTripNoP);
        spnTripPoints =  findViewById(R.id.spn_Destination_Point);
        txtTotalAmt =  findViewById(R.id.trip_TotalP);
        btnGoToPayment =  findViewById(R.id.btnPayment_Trip);
        if(userNIN.isEmpty()){
            Intent myIntent = new Intent(TripBookingAct.this, NinIDAct.class);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startNinActForResult.launch(myIntent);
            //startActivity(myIntent);


        }

        spnSitSel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSitStrng = spnSitSel.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if(selectedSitStrng !=null){
            selectedSitInt= Integer.parseInt(selectedSitStrng);

        }



        btnGoToPayment =  findViewById(R.id.btnPayment_Trip);
        if(item !=null){
            tripTakeOffPoints=item.getBtTakeOffPoint();
            tripDestination=item.getBtFinalDestination();
            unbookedSitsNo=item.getBtNoOfSitRem();
            amount=item.getBtAmountForAdult();
            tripStopPoints = item.getBtStopPoints();


        }
        tripStopPointAdap = new TripStopPointAdap(TripBookingAct.this,R.layout.trip_stop_point_item, tripStopPoints);
        spnTripPoints.setAdapter(tripStopPointAdap);
        spnTripPoints.setSelection(0);
        selectedPointIndex = spnTripPoints.getSelectedItemPosition();

        try {
            tripStopPoint = tripStopPoints.get(selectedPointIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
        if(tripStopPoint !=null){
            stopPointName= tripStopPoint.getbTSPName();

        }

        txtTripTK.setText(tripTakeOffPoints);
        txtFinalDest.setText(tripDestination);
        txtUnBookedSits.setText(unbookedSitsNo);
        txtTripAmount.setText("NGN"+amount);
        totalAmount= (long) (amount * selectedSitInt);
        txtTotalAmt.setText("NGN"+totalAmount);
        Bundle paymentBundle= new Bundle();
        paymentBundle.putParcelable("BoatTrip",item);
        paymentBundle.putInt("SitCount",selectedSitInt);
        paymentBundle.putLong("Total",totalAmount);
        paymentBundle.putString("PaymentFor","Boat Booking");
        paymentBundle.putString("stopPointName",stopPointName);
        paymentBundle.putString("PROFILE_NIN",userNIN);
        paymentBundle.putParcelable("Profile",userProfile);
        btnGoToPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(TripBookingAct.this, GooglePayAct.class);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);

            }
        });
        btnGoToPayment.setOnClickListener(this::DoPayment);




    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txtTourDate) {
            tourDatePiker = new DatePickerDialog(this, dateset, year, month,
                    day);
            tourDatePiker.getDatePicker().setMinDate(
                    System.currentTimeMillis() - 1000);
            tourDatePiker.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view == spnBoatTypes) {
            //personList.clear();
            boatTrip= new BoatTrip();
            spnNoOfBoats.setSelection(0);
            if (spnBoatTypes.getSelectedItemPosition() == 0) {

            }
            personAdapter.notifyDataSetChanged();
        }
        if (view == spnTripType) {
            scheduleList.clear();
            //timeList.clear();
            /*if (spnTripType.getSelectedItem().toString()
                    .equals(getString(R.string.text_full_day_tour))) {
                scheduleList.add(getString(R.string.text_morning));
                timeList.add(getString(R.string.text_departure) + ":"
                        + fullDayBundle.getString(BookingConstant.Params.FULL_DAY_DTIME)
                        + " " + getString(R.string.text_return) + ":"
                        + fullDayBundle.getString(BookingConstant.Params.FULL_DAY_RTIME));
                if (spnBoatTypes.getSelectedItemPosition() == 1) {
                    personList.clear();
                    for (int i = 0; i < fullDayPriceList.size(); i++) {
                        boatTrip = fullDayPriceList.get(i);
                        personList.add("Max. " + boatTrip.getpTourPerson()
                                + " Person");
                    }
                    spnNoOfBoats.setSelection(0);
                }
            } else {
                scheduleList.add(getString(R.string.text_morning));
                scheduleList.add(getString(R.string.text_afternoon));
                timeList.add(getString(R.string.text_departure)
                        + ":"
                        + halfDayBundle
                        .getString(BookingConstant.Params.HALF_DAY_MORNING_DTIME)
                        + " "
                        + getString(R.string.text_return)
                        + ":"
                        + halfDayBundle
                        .getString(BookingConstant.Params.HALF_DAY_MORNING_RTIME));
                timeList.add(getString(R.string.text_departure)
                        + ":"
                        + halfDayBundle
                        .getString(BookingConstant.Params.HALF_DAY_AFTER_DTIME)
                        + " "
                        + getString(R.string.text_return)
                        + ":"
                        + halfDayBundle
                        .getString(BookingConstant.Params.HALF_DAY_AFTER_RTIME));

            }
            scheduleAdapter.notifyDataSetChanged();
            personAdapter.notifyDataSetChanged();*/
        }
        /*String selectedType = spnTripType.getSelectedItem().toString();
        // Common Tour Calculation
        if (spnBoatTypes.getSelectedItemPosition() == 0) {
            int totalPersons = spnNoOfBoats.getSelectedItemPosition() + 1;
            if (selectedType.equals(getString(R.string.text_full_day_tour))) {
                totalTarrif = fullDayCommonPrice * totalPersons;
            } else {
                totalTarrif = halfDayCommonPrice * totalPersons;
            }
        }

        tvTotalTarrif.setText("NGN" + totalTarrif);*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void JoinATripBooking(View view) {
        layoutCustomTrip.setVisibility(View.GONE);
        layoutJoinTrip.setVisibility(View.VISIBLE);
    }

    public void CharterTrip(View view) {
        layoutJoinTrip.setVisibility(View.GONE);
        layoutCustomTrip.setVisibility(View.VISIBLE);
    }

    public void bookCustomTrip(View view) {
    }

    public void DoPayment(View view) {
    }


}