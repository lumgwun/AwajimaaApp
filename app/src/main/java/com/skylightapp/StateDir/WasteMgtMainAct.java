package com.skylightapp.StateDir;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.MapAndLoc.UserInfoList;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WasteMgtMainAct extends AppCompatActivity {
    protected AddressReceiver addressReceiver;

    ListView listView;
    private static final String PREF_NAME = "awajima";
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1,SharedPrefSuperUser;


    ArrayList<ClusterUserList> clusterUserLists = new ArrayList<>();

    RouteAdapter routeAdapter;

    public static List<Address> addressLatLong = new ArrayList<>();

    static final ArrayList<String> addressList = new ArrayList<>();

    static final List<String> nameList = new ArrayList<>();

    static final List<Integer> demandList = new ArrayList<>();

    static final int vehicleCapacity = 40;

    Map<String, Collection<Integer>> mapClusterNodes = new HashMap<>();

    // store count
    static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_waste_mgt_main);
        listView = findViewById(R.id.list_view_route);

        addressReceiver = new AddressReceiver(null);

        queryDatabase();

        Log.e(GeocodeConstants.TAG_MAIN, "Point after query database!! ");
        Log.e(GeocodeConstants.TAG_MAIN, "Inside count!! " + count);
        if(count > 0){
            Log.e(GeocodeConstants.TAG_MAIN, "Inside count!! " + count);
            sendAddressForGeocode();
        }

    }

    protected void queryDatabase(){

//        FirebaseApp.initializeApp(this);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference =
//                firebaseDatabase.getReference("08-Mar-2018");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                User userValue = null;
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    userValue = userSnapshot.getValue(User.class);
//                    if (userValue.getReply().equals("Yes")) {
//
//                        addressList.add(userValue.getAddress());
//                        nameList.add(userValue.getName());
//                        count = count + 1;
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                    Log.w(GeocodeConstants.TAG_MAIN, "Failed to read value.", error.toException());
//            }
//        });

        // Adding dummy data manually to check if the app works properly
        addressList.add("Kvarnbolund 9, 752 63 Uppsala");
        addressList.add("Sernanders väg 4, 752 61 Uppsala");
        addressList.add("Uppsala");
        addressList.add("Lägerhyddsvägen 2, 752 37 Uppsala");
        addressList.add("Flogstavägen 5, 752 73 Uppsala");
        addressList.add("Kantorsgatan 44, 754 24 Uppsala");

        nameList.add("Une");
        nameList.add("Ene");
        nameList.add("Ima-Awaji");
        nameList.add("Kalibi");
        nameList.add("Princess");
        nameList.add("Patrick");

        // Assuming 1st address is the depot of the trucks
        demandList.add(20);
        demandList.add(30);
        demandList.add(20);
        demandList.add(10);
        demandList.add(5);

        count = 6;

        Log.e(GeocodeConstants.TAG_MAIN, "Count is: " + count);

    }


    private void sendAddressForGeocode(){

        try{

            // Now create an intent for the Geocode
            Intent intent = new Intent(getApplicationContext(), GeocodeService.class);
            intent.putExtra(GeocodeConstants.ADDRESS_RECEIVER,addressReceiver);
            intent.putStringArrayListExtra(GeocodeConstants.LOCATION_NAME, addressList);
            Log.v(GeocodeConstants.TAG_MAIN,"Starting the service");
            startService(intent);
        }
        finally {

        }

    }




    private void displayDatabaseInfo() {

//        displayView.setText("Add user information to produce optimized route to collect garbage");
    }

    private void deleteAllEntries(){

        listView.setVisibility(View.INVISIBLE);
        displayDatabaseInfo();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_waste, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_entries:
                deleteAllEntries();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class AddressReceiver extends ResultReceiver {

        public AddressReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult (final int resultCode, final Bundle resultData){

            // If it is a success
            if(resultCode == GeocodeConstants.SUCCESS){

                addressLatLong = resultData.getParcelableArrayList(GeocodeConstants.RES_ADDRESS);

                // Create clusters for segmentation
                assert addressLatLong != null;
                mapClusterNodes = Clustering.sweepAlgoclustering(addressLatLong);

                for(Map.Entry<String,Collection<Integer>> entry: mapClusterNodes.entrySet()){
                    List<Integer> list = new ArrayList<>(entry.getValue());
                    String cluster = entry.getKey();
                    ArrayList<UserInfoList> userInfoLists = new ArrayList<>();

                    for(int i = 0; i < list.size(); i++){
                        int pos = list.get(i)+1;
                        String name = nameList.get(pos);
                        String addList = addressList.get(pos);
                        double lat = addressLatLong.get(pos).getLatitude();
                        double longitude = addressLatLong.get(pos).getLongitude();
                        userInfoLists.add(new UserInfoList(name, addList, lat, longitude));
                    }
                    clusterUserLists.add(new ClusterUserList(cluster,userInfoLists));
                }

                routeAdapter = new RouteAdapter(getApplicationContext(), clusterUserLists);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        displayView.setVisibility(View.INVISIBLE);

                        if(routeAdapter != null){
                            listView.setAdapter(routeAdapter);
                        }

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                ClusterUserList clusterUserList = routeAdapter.getItem(i);
                                ArrayList<UserInfoList> userInfo;
                                assert clusterUserList != null;
                                String clusterName = clusterUserList.getCluster();
                                userInfo = clusterUserList.getUserInfoList();

                                Intent intent = new Intent(WasteMgtMainAct.this, SegmentedUsersAct.class);
                                intent.putExtra(GeocodeConstants.CLUSTER_NAME,clusterName);
                                intent.putExtra(GeocodeConstants.CLUSTER_USER_LIST,userInfo);
                                startActivity(intent);
                            }
                        });

                    }
                });
            }
            else{
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        displayView.append("No Data!");
                    }
                });
            }
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
        listView.setVisibility(View.INVISIBLE);
        queryDatabase();
        //userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }

}