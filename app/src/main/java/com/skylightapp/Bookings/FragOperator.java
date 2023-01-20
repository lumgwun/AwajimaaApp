package com.skylightapp.Bookings;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Utils;
import com.skylightapp.CustomApplication;
import com.skylightapp.Database.TripDAO;
import com.skylightapp.Interfaces.Consts;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.Awajima;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;

public class FragOperator extends Fragment implements TripListener, View.OnClickListener, OnSuccessListener<Location> {

    private Activity context;

    private LocationManager manager;

    private View viewRoot, viewEmpty;

    private Dialog dialogBuilder;
    CustomApplication application;

    private static final int REQUEST_CODE_PERMISSIONS = 101;

    private PrefManager mPreference;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private TripTAd mAdapter,stateTodayAdapter,stateAllAdapter,allAdapter,allTodayAdapter;
    private Gson gson, gson1,gson2,gson3,gson4;
    private String json, json1,json2,json3,json4;
    private Profile userProfile;
    private Customer customer;
    String SharedPrefUserPassword;
    String SharedPrefCusID;
    String SharedPrefUserMachine,surName,firstName;
    String SharedPrefUserName,selectedState;
    int SharedPrefProfileID;
    long bizID;
    private TaxiDriver driver;
    private ArrayList<Trip> currentTrips;
    private ArrayList<Trip> allTrips;
    private ArrayList<Trip> todayTrips;
    private ArrayList<Trip> todayStateTrips;
    private ArrayList<Trip> allStateTrips;
    
    private MarketBusiness marketBusiness;
    private TripDAO tripDAO;
    boolean refresh=false;
    RecyclerView recyclerCurrent, recyclerStateToday,recyclerAll;
    AppCompatButton btnAll,btnStateToday,btnStateAll;
    Spinner spnState;
    LinearLayoutCompat layoutState,layoutAllTrips;
    int stateIndex,awajimaID;
    private Awajima awajima;
    private Calendar calendar;
    String todayDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_operator, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        application= new CustomApplication();
        OurTeliver.setTripListener(this);
        userProfile = new Profile();
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
        gson3 = new Gson();
        gson4 = new Gson();
        customer = new Customer();
        driver= new TaxiDriver();
        awajima= new Awajima();
        tripDAO= new TripDAO(getContext());
        marketBusiness= new MarketBusiness();
        currentTrips = new ArrayList<Trip>();
        allTrips = new ArrayList<Trip>();
        todayTrips = new ArrayList<Trip>();
        todayStateTrips = new ArrayList<Trip>();
        allStateTrips = new ArrayList<Trip>();
        mPreference = new PrefManager(context);
        userPreferences = requireActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
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
        json3 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json3, MarketBusiness.class);
        json4 = userPreferences.getString("LastAwajimaUsed", "");
        awajima = gson4.fromJson(json4, Awajima.class);
        viewRoot = view.findViewById(R.id.view_root);
        manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        ExtendedFloatingActionButton txtTripStatus = view.findViewById(R.id.add_op_trip);
        recyclerCurrent = view.findViewById(R.id.op_current_trips);
        recyclerStateToday = view.findViewById(R.id.recyler_our_TripsT);

        btnAll = view.findViewById(R.id.btn_TripsAll);
        btnStateToday = view.findViewById(R.id.btn_Trips_SToday);
        btnStateAll = view.findViewById(R.id.btn_StateTrip_All);
        btnAll.setOnClickListener(this);
        btnStateAll.setOnClickListener(this);
        btnAll.setOnClickListener(this);
        spnState = view.findViewById(R.id.spn_op_state);
        layoutState = view.findViewById(R.id.layout_op_state);
        layoutAllTrips = view.findViewById(R.id.layout_All_our);
        recyclerAll = view.findViewById(R.id.recyler_our_allTrip);
        calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        todayDate = mdformat.format(calendar.getTime());
        spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(stateIndex==position){
                    return;
                }else{
                    selectedState = spnState.getSelectedItem().toString();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        viewEmpty = view.findViewById(R.id.view_empty_Op);
        if(marketBusiness !=null){
            bizID=marketBusiness.getBusinessID();
        }
        if(awajima !=null){
            awajimaID=awajima.getAwajimaID();
        }
        if(tripDAO !=null){
            try {
                currentTrips.addAll(tripDAO.getCurrentTripsForBiz(bizID));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
        recyclerCurrent.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new TripTAd(context);
        mAdapter.setData(currentTrips, this);
        recyclerCurrent.setAdapter(mAdapter);


        if (currentTrips.isEmpty())
            viewEmpty.setVisibility(View.VISIBLE);

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS &&
                apiAvailability.isUserResolvableError(resultCode)) {
            Dialog dialog = apiAvailability.getErrorDialog(context, resultCode,
                    900);
            if (dialog != null) {
                dialog.setCancelable(false);
            }
            if (dialog != null) {
                dialog.show();
            }
        }

        txtTripStatus.setOnClickListener(view1 -> {
            if (Utils.checkLPermission(context))
                Utils.enableGPS(context, this);
        });
    }

    public void validateTrip() {
        try {
            dialogBuilder = new Dialog(context);
            dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(
                    ContextCompat.getColor(context, android.R.color.transparent)));
            dialogBuilder.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialogBuilder.setContentView(R.layout.alert_add_trip);
            final AppCompatEditText edtId = dialogBuilder.findViewById(R.id.edt_id);
            final AppCompatEditText edtTitle = dialogBuilder.findViewById(R.id.edt_title);
            final AppCompatEditText edtMsg = dialogBuilder.findViewById(R.id.edt_msg);
            final AppCompatEditText edtUserId = dialogBuilder.findViewById(R.id.edt_user_id);
            final AppCompatTextView btnOk = dialogBuilder.findViewById(R.id.btn_ok);
            final AppCompatImageView imageArrow = dialogBuilder.findViewById(R.id.image_arrow);
            final View expandView = dialogBuilder.findViewById(R.id.expandable_view);
            final View viewNotifyUser = dialogBuilder.findViewById(R.id.view_notify_users);

            viewNotifyUser.setOnClickListener(v -> {
                if (expandView.getVisibility() == View.GONE) {
                    expandView.setVisibility(View.VISIBLE);
                    imageArrow.setRotation(180);
                } else {
                    expandView.setVisibility(View.GONE);
                    imageArrow.setRotation(360);
                }
            });
            btnOk.setOnClickListener(view -> startTrip(edtTitle.getText().toString(), edtMsg.getText().toString(),
                    edtUserId.getText().toString().trim(), edtId.getText().toString().trim()));
            dialogBuilder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTrip(String title, String msg, String userId, String trackingId) {
        try {
            if (trackingId.isEmpty())
                Utils.showSnack(viewRoot, getString(R.string.enter_valid_id));
            else if (!Utils.isNetConnected(context))
                Utils.showSnack(viewRoot, getString(R.string.no_internet_connection));
            else {
                dialogBuilder.dismiss();
                TripBuilder builder = new TripBuilder(trackingId);
                if (!userId.isEmpty()) {
                    PushData pushData = new PushData(userId.split(","));
                    pushData.setPayload(msg);
                    pushData.setMessage(title);
                    builder.withUserPushObject(pushData);
                }
                OurTeliver.startTrip(builder.build());
                Utils.showSnack(viewRoot, getString(R.string.txt_wait_start_trip));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTripStarted(Trip tripDetails) {
        Log.d("Driver:", "Trip started::" + tripDetails);
        changeStatus(tripDetails.getTripId(), true);

    }

    @Override
    public void onLocationUpdate(Location location) {

    }

    @Override
    public void onTripEnded(String trackingId) {
        Log.d("Driver:", "Trip Ended::" + trackingId);
        changeStatus(null, false);
    }


    private void changeStatus(String id, boolean status) {
        mPreference.storeBoolean(Consts.IS_TRIP_ACTIVE, status);
        //mPreference.storeString(Consts.TRACKING_ID, id);
        currentTrips.clear();

        if(marketBusiness !=null){
            bizID=marketBusiness.getBusinessID();
        }
        if(refresh=true){
            if(tripDAO !=null){
                try {
                    currentTrips.addAll(tripDAO.getCurrentTripsForBiz(bizID));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }

        }

        mAdapter.notifyDataSetChanged();
        viewEmpty.setVisibility(currentTrips.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onTripError(String reason) {
        Log.d("Driver:", "Trip error: Reason: " + reason);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.stop:
                try {
                    OurTeliver.stopTrip(v.getTag().toString());
                    Utils.showSnack(viewRoot, getString(R.string.txt_wait_stop_trip));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_Trips_SToday:
                calendar = Calendar.getInstance();
                spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(stateIndex==position){
                            return;
                        }else{
                            selectedState = spnState.getSelectedItem().toString();

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                todayDate = mdformat.format(calendar.getTime());
                if(marketBusiness !=null){
                    bizID=marketBusiness.getBusinessID();
                }
                layoutState.setVisibility(View.VISIBLE);
                layoutAllTrips.setVisibility(View.GONE);
                recyclerStateToday = v.findViewById(R.id.recyler_our_TripsT);
                doStateTodayProcessing(recyclerStateToday,bizID,todayDate,selectedState);

                //layoutState = view.findViewById(R.id.layout_op_state);
                //layoutAllTrips = view.findViewById(R.id.layout_All_our);
                //recyclerAll = view.findViewById(R.id.recyler_our_allTrip);

                break;

            case R.id.btn_TripsAll:
                if(marketBusiness !=null){
                    bizID=marketBusiness.getBusinessID();
                }
                recyclerAll = v.findViewById(R.id.recyler_our_allTrip);
                layoutAllTrips.setVisibility(View.VISIBLE);
                layoutState.setVisibility(View.GONE);
                doAllTripProcessing(recyclerAll,bizID);

                break;
            case R.id.btn_TodayTrip:
                calendar = Calendar.getInstance();
                 mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                todayDate = mdformat.format(calendar.getTime());
                if(marketBusiness !=null){
                    bizID=marketBusiness.getBusinessID();
                }
                layoutAllTrips.setVisibility(View.VISIBLE);
                recyclerAll = v.findViewById(R.id.recyler_our_allTrip);
                layoutState.setVisibility(View.GONE);
                doTodayTrips(recyclerAll,bizID,todayDate);
                break;

            case R.id.btn_StateTrip_All:
                spnState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(stateIndex==position){
                            return;
                        }else{
                            selectedState = spnState.getSelectedItem().toString();

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                if(marketBusiness !=null){
                    bizID=marketBusiness.getBusinessID();
                }
                layoutState.setVisibility(View.VISIBLE);
                layoutAllTrips.setVisibility(View.GONE);
                recyclerStateToday = v.findViewById(R.id.recyler_our_TripsT);
                doAllStateTrips(recyclerStateToday,bizID,selectedState);
                break;
            default:
                break;
        }
    }

    private void doAllStateTrips(RecyclerView recyclerStateToday, long bizID, String selectedState) {


        if(tripDAO !=null){
            try {
                allStateTrips.addAll(tripDAO.getAllStateTripsForBiz(bizID, selectedState));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
        Collections.shuffle(allStateTrips, new Random(System.currentTimeMillis()));
        LinearLayoutManager linearLayoutMParam
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recyclerStateToday.setLayoutManager(linearLayoutMParam);
        stateAllAdapter = new TripTAd(context);
        stateAllAdapter.setData(allStateTrips, this);
        recyclerStateToday.setAdapter(stateAllAdapter);

    }

    private void doTodayTrips(RecyclerView recyclerAll, long bizID, String todayDate) {
        if(tripDAO !=null){
            try {
                todayTrips.addAll(tripDAO.getTodayTripsForBiz(bizID, todayDate));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
        Collections.shuffle(todayTrips, new Random(System.currentTimeMillis()));
        recyclerAll.setLayoutManager(new LinearLayoutManager(context));
        allTodayAdapter = new TripTAd(context);
        allTodayAdapter.setData(todayTrips, this);
        recyclerAll.setAdapter(allTodayAdapter);

    }

    private void doAllTripProcessing(RecyclerView recyclerAll, long bizID) {
        if(tripDAO !=null){
            try {
                allTrips.addAll(tripDAO.getAllTripsForBiz(bizID));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
        Collections.shuffle(allTrips, new Random(System.currentTimeMillis()));

        recyclerAll.setLayoutManager(new LinearLayoutManager(context));
        allAdapter = new TripTAd(context);
        allAdapter.setData(allTrips, this);
        this.recyclerAll.setAdapter(allAdapter);
    }

    private void doStateTodayProcessing(RecyclerView recyclerStateToday, long bizID, String todayDate, String selectedState) {
        if(tripDAO !=null){
            try {
                todayStateTrips.addAll(tripDAO.getTodayTripsForBizState(bizID, todayDate, selectedState));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
        Collections.shuffle(todayStateTrips, new Random(System.currentTimeMillis()));
        recyclerStateToday.setLayoutManager(new LinearLayoutManager(context));
        stateTodayAdapter = new TripTAd(context);
        stateTodayAdapter.setData(todayStateTrips, this);
        recyclerStateToday.setAdapter(stateTodayAdapter);

    }

    public void onReqPermission(int[] grantResults) {
        if (Utils.isPermissionOk(grantResults))
            Utils.enableGPS(context,this);
        else
            Toast.makeText(context, "permission is required for Awajima App to function..", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSuccess(Location location) {
        if (location != null)
            validateTrip();
    }
}
