package com.skylightapp.MapAndLoc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.R;

import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmergRFenceAct extends AppCompatActivity implements OnMapReadyCallback {
    private static final String CLASSTAG =
            " " + EmergRFenceAct.class.getSimpleName () + " ";

    private Location displayLocation;
    private String   displayName;
    GoogleMap gMap;
    private Bundle bundle;
    private ArrayList<Fence> fenceArrayList;

    private static Handler handler;
    private RecyclerView recycler;
    private CarouselLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_emerg_rfence);
        AppController app = (AppController) getApplication ();
        fenceArrayList= new ArrayList<>();
        bundle= new Bundle();
        SupportMapFragment fragment =
                (SupportMapFragment) getSupportFragmentManager ()
                        .findFragmentById (R.id.map);
        if (fragment != null) {
            fragment.getMapAsync (this);
        }
        if (handler == null) {
            handler = new Handler (getMainLooper ()) {
                @Override
                public void handleMessage (@NotNull Message msg)
                {
                    //Log.v (Constants.LOGTAG, CLASSTAG + "Handler receives Event");
                    refreshScreen ();
                }
            };
        } else
        {
            //Log.e ( Constants.LOGTAG, CLASSTAG + "Multiple DisplayLocationActivity instances");
            // This should not occur if the manifest has the
            // android:launchMode="singleTask" attribute
        }
        recycler = findViewById (R.id.recyler_Emerg);

        layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        recycler.setLayoutManager(layoutManager);
        FenceAd adapter = new FenceAd(app);
        recycler.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recycler.setAdapter(adapter);
        recycler.setClickable(true);

        recycler.setAdapter (adapter);
        recycler.invalidate ();
        recycler.addOnItemTouchListener(new MyTouchListener(this, recycler, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {
            }

            @Override
            public void onRightSwipe(View view, int position) {
            }

            @Override
            public void onClick(View view, int position) {
            }
        }));
    }
    @Override
    public void onPause () {
        super.onPause ();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        //Log.v (Constants.LOGTAG, CLASSTAG + "onPause called");
        AppController app = (AppController) getApplication ();
        if (app.getStatus () == AppController.Status.FENCES_FAILED)
        {
            app.setStatus (AppController.Status.DEFAULT);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {
        //Log.v (Constants.LOGTAG, CLASSTAG + "onPrepareOptionsMenu called");
        //menu.findItem (R.id.menu_show_events).setEnabled (getNumEvents () > 0);
        return true;
    }

    @Override
    public void onResume ()
    {
        super.onResume ();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
        //Log.v (Constants.LOGTAG, CLASSTAG + "onResume called");

        // Add fences if required
        AppController app = (AppController) getApplication ();
        if ( app.isGeofencingInitialised () &&
                (app.getStatus () == AppController.Status.DEFAULT) )
        {
            app.addFences (this);
        }

        refreshScreen ();
        FenceAd adapter = new FenceAd(app);
        recycler.setAdapter (adapter);
        recycler.invalidate ();
    }

    public void refreshScreen () {
        AppController app = (AppController) getApplication ();

        if (app.isHasBackgroundLocationPermission ()) {

            Locale locale = Locale.getDefault ();

        } else {

        }

        app.getLocationAvailability ()
                .addOnSuccessListener ( new OnSuccessListener<LocationAvailability>()
                {
                    @Override
                    public void onSuccess (LocationAvailability availability) {
                        //handleLocationAvailable (availability);
                    }
                } )
                .addOnFailureListener ( new OnFailureListener()
                {
                    @Override
                    public void onFailure (@NonNull Exception e)
                    {
                        //Log.w ( Constants.LOGTAG, CLASSTAG + "Location availability failure: " +e.getLocalizedMessage () );
                        //handleLocationNotAvailable ();
                    }
                } );

        // Force the menu to be updated
        invalidateOptionsMenu ();
    }
    private void initialiseGeofencing ()
    {
        AppController app = (AppController) getApplication ();
        app.initGeofencing (this);
    }
    private String locationToString (double d)
    { return Location.convert (d, Location.FORMAT_DEGREES); }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //Log.v ( Constants.LOGTAG, CLASSTAG + "onMapReady called for Location: " + displayLocation + " with Name: " + displayName );

        List<Fence> fences =
                ((AppController) getApplication ()).getFences ();
        new FenceMap (gMap)
                .showLocation (displayName, displayLocation)
                .showFences (fences);

        LatLng location =
                new LatLng ( displayLocation.getLatitude (),
                        displayLocation.getLongitude () );
        gMap.moveCamera (CameraUpdateFactory.newLatLngZoom (location, 15f));

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
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

}