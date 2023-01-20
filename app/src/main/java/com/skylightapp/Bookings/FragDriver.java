package com.skylightapp.Bookings;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
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
import com.skylightapp.R;


import java.util.ArrayList;


public class FragDriver extends Fragment implements TripListener, View.OnClickListener, OnSuccessListener<Location> {

    private Activity context;

    private LocationManager manager;

    private View viewRoot, viewEmpty;

    private Dialog dialogBuilder;
    CustomApplication application;

    private static final int REQUEST_CODE_PERMISSIONS = 101;

    private PrefManager mPreference;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private TripTAd mAdapter;
    private Gson gson, gson1,gson2;
    private String json, json1,json2;
    private Profile userProfile,driverProfile;
    private Customer customer;
    String SharedPrefUserPassword;
    String SharedPrefCusID;
    String SharedPrefUserMachine,surName,firstName;
    String SharedPrefUserName;
    int SharedPrefProfileID, driverID, profileID;
    private TaxiDriver driver;
    private ArrayList<Trip> currentTrips;
    private boolean itIsDriver;
    private TripDAO tripDAO;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_driver, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        application= new CustomApplication();
        OurTeliver.setTripListener(this);
        userProfile = new Profile();
        tripDAO= new TripDAO(getContext());
        gson1 = new Gson();
        gson = new Gson();
        gson2 = new Gson();
        customer = new Customer();
        driver= new TaxiDriver();
        driverProfile=new Profile();
        currentTrips = new ArrayList<Trip>();
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
        json2 = userPreferences.getString("LastTaxiDriverUsed", "");
        driver = gson2.fromJson(json2, TaxiDriver.class);
        viewRoot = view.findViewById(R.id.view_root);
        manager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        ExtendedFloatingActionButton txtTripStatus = view.findViewById(R.id.add_trip);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_driver_trips);
        viewEmpty = view.findViewById(R.id.view_empty);
        if(userProfile!=null){
            profileID=userProfile.getPID();
        }
        if(driver !=null){
            driverProfile=driver.getDriverProfile();
            driverID=driver.getDriverID();
        }
        if(userProfile==driverProfile){
            itIsDriver=true;

        }
        if(itIsDriver){

            if(tripDAO !=null){
                try {
                    currentTrips.addAll(tripDAO.getCurrentTripForDriver(driverID));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new TripTAd(context);
        mAdapter.setData(currentTrips, this);
        recyclerView.setAdapter(mAdapter);
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
        mPreference.storeString(Consts.TRACKING_ID, id);
        currentTrips.clear();
        if(userProfile!=null){
            profileID=userProfile.getPID();
        }
        if(driver !=null){
            driverProfile=driver.getDriverProfile();
            driverID=driver.getDriverID();
        }
        if(userProfile==driverProfile){
            itIsDriver=true;

        }
        if(itIsDriver){

            if(tripDAO !=null){
                try {
                    currentTrips.addAll(tripDAO.getCurrentTripForDriver(driverID));
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
            default:
                break;
        }
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
