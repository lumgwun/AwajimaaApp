package com.skylightapp.RealEstate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Classes.DrawGoogleMap;
import com.skylightapp.Classes.ErrorHandler;
import com.skylightapp.Classes.GoogleMapData;
import com.skylightapp.Classes.MenuObject;
import com.skylightapp.Classes.MenuParams;
import com.skylightapp.R;
import com.skylightapp.VoiceRecogAct;

import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

public class PropLocUpdate extends AppCompatActivity implements MenuItem.OnMenuItemClickListener, OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private DialogFragment mMenuDialogFragment;
    private DrawGoogleMap myCustomGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_prop_loc_update);

        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();

        myCustomGoogleMap = new DrawGoogleMap();
        boolean loadCurrentAddress = true;
        Bundle extras = getIntent().getExtras();
        EditText addressEditText = (EditText) findViewById(R.id.address);
        if (extras != null) {
            String eraseAddress = extras.getString("SaveListingCommand");
            if (eraseAddress != null && eraseAddress.equals("clear")) {
                GoogleMapData.eraseCurrentListing();
                loadCurrentAddress = false;
            }

            String voiceAddress = extras.getString("GoogleVoiceCommand");
            if (voiceAddress != null) {
                addressEditText.setText(voiceAddress);
                try {
                    updateCurrentListing();
                    loadCurrentAddress = false;
                } catch (InvalidParameterException e) {
                    ErrorHandler.displayException(PropLocUpdate.this, e);
                }
            }
        }

        if (loadCurrentAddress && GoogleMapData.getCurrentListing() != null) {
            addressEditText.setText(MessageFormat.format("Location:{0}", GoogleMapData.getCurrentListing().getCusLocation()));
        }

        // draw map
        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFrag != null) {
            mapFrag.getMapAsync(PropLocUpdate.this);  // This calls OnMapReady(..). (Asynchronously)
        }

        final EditText editText = (EditText)findViewById(R.id.address);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    try {
                        updateCurrentListing();
                    } catch (InvalidParameterException e) {
                        ErrorHandler.displayException(PropLocUpdate.this, e);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        myCustomGoogleMap.drawMap(getApplicationContext(), map, GoogleMapData.getCameraLoc(), GoogleMapData.getCameraZoom());
    }

    // update marker for current real estate listing
    private void updateCurrentListing() {
        final EditText editText = (EditText) findViewById(R.id.address);

        List<Address> list;
        Geocoder gc = new Geocoder(getApplicationContext());
        String stringLocation = editText.getText().toString().trim();

        if (!stringLocation.equals("")) {
            try {
                list = gc.getFromLocationName(stringLocation, 50);

                if (list.size() > 0) {
                    Address googleAddress = list.get(0);

                    double lat = googleAddress.getLatitude();
                    double lng = googleAddress.getLongitude();

                    LatLng listingLatLng = new LatLng(lat, lng);

                    GoogleMapData.setCameraLoc(listingLatLng);

                    GoogleMapData.setCurrentListing(stringLocation, listingLatLng);

                    //refresh map
                    SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    if (mapFrag != null) {
                        mapFrag.getMapAsync(PropLocUpdate.this);
                    }
                } else {
                    throw new InvalidParameterException(getResources().getString(R.string.error));
                }
            } catch (java.io.IOException e) {
                throw new InvalidParameterException("IO Error: " + e.toString());
            }
        } else {
            throw new InvalidParameterException(getResources().getString(R.string.error));
        }
    }

    private void saveMapData() {
        GoogleMapData.setCameraLoc(myCustomGoogleMap.getCameraLocation());
        GoogleMapData.setCameraZoom(myCustomGoogleMap.getCameraZoom());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loadCurrentListing(View v) {
        try {
            updateCurrentListing();
        } catch (InvalidParameterException e) {
            // display any errors that occurred when updating current listing
            ErrorHandler.displayException(PropLocUpdate.this, e);
        }
    }

    public void openVoiceSearch(View v) {

        Intent intent = new Intent(this, VoiceRecogAct.class);
        startActivity(intent);
    }

    //@Override
    public void onMenuItemLongClick(View clickedView, int position) {
        parseMenuClick(position);
    }

    //@Override
    public void onMenuItemClick(View clickedView, int position) {
        parseMenuClick(position);
    }

    private void parseMenuClick(int position) {
        if (position > 0) {
            if (position > 1) {
                try {
                    startActivity(MenuParams.getMenuActivityIntent(this, getResources().getString(R.string.map_menu), position));
                } catch (InvalidParameterException e) {
                    ErrorHandler.displayException(this, e);
                }
            } else {
                saveListing();
            }
        }
    }

    private void saveListing() {
        try {
            updateCurrentListing();

            if (!(GoogleMapData.isCurrentLocInSavedListings(GoogleMapData.getCurrentListing().getCusLocation()))) {

                Intent intent = new Intent(PropLocUpdate.this, SaveListingAct.class);

                if (!GoogleMapData.isCurrentLocInSavedListings(GoogleMapData.getCurrentListing().getCusLocation())) {
                    saveMapData();

                    intent.putExtra("Location", GoogleMapData.getCurrentListing().getCusLocation());
                    intent.putExtra("Longitude", Double.toString(GoogleMapData.getCurrentListing().getCusLocation().longitude));
                    intent.putExtra("Latitude", Double.toString(GoogleMapData.getCurrentListing().getCusLocation().latitude));
                    /*intent.putExtra("Description", GoogleMapData.getCurrentListing().getDescription());
                    intent.putExtra("Longitude", Double.toString(GoogleMapData.getCurrentListing().getLocation().longitude));
                    intent.putExtra("Latitude", Double.toString(GoogleMapData.getCurrentListing().getLocation().latitude));
                    intent.putExtra("Tittle", GoogleMapData.getCurrentListing().getTittleOfProperty());
                    intent.putExtra("Property Type", GoogleMapData.getCurrentListing().getPropertyType());
                    intent.putExtra("Price Duration", GoogleMapData.getCurrentListing().getPriceDuration());
                    intent.putExtra("Capacity", GoogleMapData.getCurrentListing().getPropertyCapacity());
                    intent.putExtra("Status", GoogleMapData.getCurrentListing().getStatus());
                    intent.putExtra("Property Link", Uri.parse(String.valueOf(GoogleMapData.getCurrentListing().getPropertylink())));
                    intent.putExtra("Property Date", String.valueOf(GoogleMapData.getCurrentListing().getPropertyDate()));*/

                /*} else {
                    intent.putExtra("Town", GoogleMapData.getCurrentListing().getTown());
                    intent.putExtra("Description", GoogleMapData.getCurrentListing().getDescription());
                    intent.putExtra("Town", GoogleMapData.getCurrentListing().getTown());
                    intent.putExtra("Longitude", Double.toString(GoogleMapData.getCurrentListing().getLocation().longitude));
                    intent.putExtra("Latitude", Double.toString(GoogleMapData.getCurrentListing().getLocation().latitude));
                    intent.putExtra("Tittle", GoogleMapData.getCurrentListing().getTittleOfProperty());
                    intent.putExtra("Property Type", GoogleMapData.getCurrentListing().getPropertyType());
                    intent.putExtra("Price Duration", GoogleMapData.getCurrentListing().getPriceDuration());
                    intent.putExtra("Capacity", GoogleMapData.getCurrentListing().getPropertyCapacity());
                    intent.putExtra("Status", GoogleMapData.getCurrentListing().getStatus());
                    intent.putExtra("Property Link", Uri.parse(String.valueOf(GoogleMapData.getCurrentListing().getPropertylink())));
                    intent.putExtra("Property Date", String.valueOf(GoogleMapData.getCurrentListing().getPropertyDate()));
                    if (GoogleMapData.getCurrentListing().getPrice() > 0) {
                        intent.putExtra("Price", Double.toString(GoogleMapData.getCurrentListing().getPrice()));
                    }
                    intent.putExtra("Longitude", Double.toString(GoogleMapData.getCurrentListing().getLocation().longitude));
                    intent.putExtra("Latitude", Double.toString(GoogleMapData.getCurrentListing().getLocation().latitude));*/
                }
                startActivity(intent);
            } else {
                ErrorHandler.displayError(PropLocUpdate.this, R.string.error);
            }
        } catch (InvalidParameterException e) {
            // display any errors that occurred when updating current listing
            ErrorHandler.displayException(PropLocUpdate.this, e);
        }
    }

    @Override

    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.prop_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                mMenuDialogFragment.show(fragmentManager, "ContextMenuDialogFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ConMenuDialFrag.newInstance(menuParams);
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        try {
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        } catch (java.lang.NullPointerException e) {
            // do nothing
        }
        mToolBarTextView.setText(R.string.title_activity_google_map);
    }

    private List<MenuObject> getMenuObjects() {
        return MenuParams.getMenuObjects(PropLocUpdate.this, getResources().getString(R.string.prop_map_menu));

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }

}