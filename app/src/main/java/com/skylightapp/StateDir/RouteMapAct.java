package com.skylightapp.StateDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.skylightapp.Classes.Profile;
import com.skylightapp.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class RouteMapAct extends AppCompatActivity {
    protected boolean flag;

    AppCompatTextView textView;

    List<Address> addresses = new ArrayList<>();

    List<Address> optimalAddress = new ArrayList<>();

    Map<String,List<Address>> mapClusterAddress = new HashMap<>();

    List<Address> testAddress = new ArrayList<>();

    String clusterName;
    private Gson gson,gson1;
    private String json,json1,SharedPrefSuperUser;


    MapView mapView = null;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    private Profile userProfile;
    private int profileID;
    private Uri pictureLink;

    String machineUser, office,state,role,surname,firstName,dbRole,userName,joinedDate,password, email,phoneNO, dob,gender,address;

    String SharedPrefUserPassword,SharedPrefCusID,SharedPrefUserMachine,SharedPrefUserName,SharedPrefProfileID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_route_map);
        Context ctx = getApplicationContext();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        userProfile=new Profile();
        Configuration.getInstance().load(ctx, getSharedPreferences(PREF_NAME, MODE_PRIVATE));
        textView = findViewById(R.id.text_view_map);

        clusterName = getIntent().getStringExtra(GeocodeConstants.CLUSTER_NAME1);
        addresses = WasteMgtMainAct.addressLatLong;

        mapClusterAddress = Clustering.sweepAlgosegmentedAddress();
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        profileID = userPreferences.getInt("PROFILE_ID", 0);
        userName = userPreferences.getString("PROFILE_USERNAME", "");
        office = userPreferences.getString("PROFILE_OFFICE", "");
        state = userPreferences.getString("PROFILE_STATE", "");
        role = userPreferences.getString("PROFILE_ROLE", "");
        password = userPreferences.getString("PROFILE_PASSWORD", "");
        joinedDate = userPreferences.getString("PROFILE_DATE_JOINED", "");
        surname = userPreferences.getString("PROFILE_SURNAME", "");
        email = userPreferences.getString("PROFILE_EMAIL", "");
        phoneNO = userPreferences.getString("PROFILE_PHONE", "");
        firstName = userPreferences.getString("PROFILE_FIRSTNAME", "");
        dob = userPreferences.getString("PROFILE_DOB", "");
        gender = userPreferences.getString("PROFILE_GENDER", "");
        //address = userPreferences.getString("PROFILE_ADDRESS", "");
        pictureLink = Uri.parse(userPreferences.getString("PICTURE_URI", ""));
        json1 = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson1.fromJson(json1, Profile.class);

        uppsalaMapView();

        flag = settingMarkerinMap();

        // Add scale bar
        scaleBar();

        for(Map.Entry<String,List<Address>> entry: mapClusterAddress.entrySet()){
            if(entry.getKey().equals(clusterName)){
                testAddress.add(addresses.get(0));
                testAddress.addAll(entry.getValue());
            }
        }


        // Find the optimal route
        optimalAddress = RouteGeneration.OptimalRoute(testAddress);

        ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();
        GeoPoint startPoint;
        GeoPoint endPoint;

        if(flag){
            for (int i = 0; i < optimalAddress.size(); i++){

                if((i + 1) == optimalAddress.size()){
                    break;
                }
                else{
                    startPoint = new GeoPoint(optimalAddress.get(i).getLatitude(),optimalAddress.get(i).getLongitude());
                    endPoint = new GeoPoint(optimalAddress.get(i+1).getLatitude(),optimalAddress.get(i+1).getLongitude());

                    waypoints.add(startPoint);
                    waypoints.add(endPoint);

                    new RoadMap().execute(waypoints);
                }
            }
        }
        else{
            textView.setText("No route available");
        }
        Log.v("Main activity","roadmanager test 1");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate back to parent activity (MainActivity)
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void uppsalaMapView(){

        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(13);
        GeoPoint startPoint = new GeoPoint(59.8586, 17.6389);
        mapController.setCenter(startPoint);


    }

    private boolean settingMarkerinMap(){

        ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();

        // Check if there is at least 2 entry data in the database to make the route
        if(WasteMgtMainAct.count > 1){

//            for(int i = 0; i < address.size() ; i++){
//                overlayItems.add(new OverlayItem("","Testing Location",
//                        new GeoPoint(address.get(i).getLatitude(), address.get(i).getLongitude())));
//            }

            overlayItems.add(new OverlayItem("","Testing Location",
                    new GeoPoint(addresses.get(0).getLatitude(), addresses.get(0).getLongitude())));

            for(Map.Entry<String,List<Address>> entry: mapClusterAddress.entrySet()){
                if(entry.getKey().equals(clusterName)){
                    for(int i = 0; i < entry.getValue().size(); i++){
                        overlayItems.add(new OverlayItem("","Testing Location",
                                new GeoPoint(entry.getValue().get(i).getLatitude(), entry.getValue().get(i).getLongitude())));
                    }
                }
            }

            ItemizedIconOverlay<OverlayItem> anotherItemizedIconOverlay
                    = new ItemizedIconOverlay<OverlayItem>(this, overlayItems, null);
            mapView.getOverlays().add(anotherItemizedIconOverlay);
            return true;
        }
        else{
            return false;
        }
    }

    private void scaleBar(){
        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);
    }



    @SuppressLint("StaticFieldLeak")
    private class RoadMap extends AsyncTask<ArrayList<GeoPoint>, Integer, org.osmdroid.views.overlay.Polyline> {

        @Override
        protected org.osmdroid.views.overlay.Polyline doInBackground(ArrayList<GeoPoint>... waypoints) {

            RoadManager roadManager = new OSRMRoadManager(getApplicationContext());

            Road road = roadManager.getRoad(waypoints[0]);
            double mDuration = road.mDuration;

            Log.e(GeocodeConstants.TAG_ROUTE,"Duration : " + mDuration);
            Log.e(GeocodeConstants.TAG_ROUTE,"Length : " + road.mLength);

            org.osmdroid.views.overlay.Polyline roadOverlay = RoadManager.buildRoadOverlay(road);

            return  roadOverlay;
        }

        @Override
        protected void onPostExecute(org.osmdroid.views.overlay.Polyline result){
            mapView.getOverlays().add(result);
            mapView.invalidate();
        }

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
        mapView.onPause();
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
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        mapView.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

}