package com.skylightapp.Bookings;

import static com.skylightapp.Database.DBHelper.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TripDAO;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BoatTripListAct extends AppCompatActivity implements  TripAdapter.BoatTripListener{
    private static final String PREF_NAME = "awajima";
    public static final String KEY = "BoatTripListAct.KEY";
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private Profile userProfile;
    private int profileID;
    private Customer customer;
    private TripAdapter tripAdapter;
    private RecyclerView recyclerView,recyclerParam;
    private ArrayList<Trip> trips;
    private TripDAO tripDAO;
    private ArrayList<Trip> tripsParam;
    private TripDAO tripDAOParam;
    private TripAdapter tripAParam;
    private SwipeRefreshLayout swipeRefreshLayout,swipeRefreshParam;
    private Spinner spnFrom,spnTo;
    private DBHelper dbHelper;
    private AppCompatButton btnSearch;
    int index1=0;
    int index10=0;
    int index100=0;
    String SharedPrefUserPassword,tripType,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID,selectedTo,selectedFrom;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_boat_trip_list);
        userProfile=new Profile();
        gson1 = new Gson();
        gson = new Gson();
        customer=new Customer();
        trips = new ArrayList<>();
        tripsParam = new ArrayList<>();
        tripDAO = new TripDAO(this);
        tripDAOParam = new TripDAO(this);
        dbHelper= new DBHelper(this);
        recyclerView = findViewById(R.id.recycler_boat_trips);
        recyclerParam = findViewById(R.id.recycler_boat_param);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshB);
        swipeRefreshParam =  findViewById(R.id.swipeRefB232);
        tripType="Boat";
        spnFrom =  findViewById(R.id.spn_from);
        spnTo =  findViewById(R.id.spn_to);
        btnSearch =  findViewById(R.id.btn_SearchBT);



        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spnFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(index1==position){
                            return;
                        }else {
                            selectedFrom = spnFrom.getSelectedItem().toString();

                        }

                        //selectedOffice = (String) parent.getSelectedItem();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


                spnTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(index10==position){
                            return;
                        }else {
                            selectedTo = spnTo.getSelectedItem().toString();

                        }

                        //selectedOffice = (String) parent.getSelectedItem();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

            }
        });

        btnSearch.setOnClickListener(this::runSearchBT);



        try {

            if(sqLiteDatabase !=null){
                sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            }
            if(tripDAO !=null){
                try {
                    trips = tripDAO.getAllTripsByType(tripType);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        try {

            if(sqLiteDatabase !=null){
                sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            }
            if(tripDAO !=null){
                try {
                    trips = tripDAO.getTripsByTypeAndFromAndTo(tripType,selectedFrom,selectedTo);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }



        tripAdapter = new TripAdapter(this, trips);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(tripAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                RearrangeItems();
            }
        });

        tripAParam = new TripAdapter(this, trips);
        LinearLayoutManager linearLayoutMParam
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerParam.setLayoutManager(linearLayoutMParam);
        recyclerParam.setItemAnimator(new DefaultItemAnimator());
        recyclerParam.setAdapter(tripAParam);
        recyclerParam.setNestedScrollingEnabled(false);
        swipeRefreshParam.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshParam.setRefreshing(false);
                RearrangeItemsParam();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    public void RearrangeItemsParam() {
        Collections.shuffle(tripsParam, new Random(System.currentTimeMillis()));
        //Collections.shuffle(tripsParam, new Random(System.currentTimeMillis()));
        tripAParam = new TripAdapter(this, tripsParam);
        recyclerParam.setAdapter(tripAParam);
        notifyAll();
    }
    public void RearrangeItems() {
        Collections.shuffle(trips, new Random(System.currentTimeMillis()));
        //Collections.shuffle(trips, new Random(System.currentTimeMillis()));
        tripAdapter = new TripAdapter(this, trips);
        recyclerView.setAdapter(tripAdapter);
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

    @Override
    public void onItemClick(Trip item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Trip",item);
        Intent tripIntent = new Intent(BoatTripListAct.this, TripDetailAct.class);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        tripIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tripIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        tripIntent.putExtras(bundle);
        startActivity(tripIntent);

    }

    public void runSearchBT(View view) {
    }
}