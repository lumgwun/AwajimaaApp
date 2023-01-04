package com.skylightapp.StateDir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skylightapp.MapAndLoc.UserInfoList;
import com.skylightapp.MapAndLoc.UserInfoListAdapter;
import com.skylightapp.R;

import java.util.ArrayList;

public class SegmentedUsersAct extends AppCompatActivity {
    AppCompatTextView displayView;
    ListView listView;

    ArrayList<UserInfoList> userInfoLists;
    UserInfoListAdapter userInfoListAdapter;
    String clusterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_segmented_users);
        displayView = findViewById(R.id.text_view_main);
        listView = findViewById(R.id.list_view_user);

        getClusterUserInfoList();
        setTitle(clusterName);
        // Setup FAB to open Route map
        FloatingActionButton mapFab = findViewById(R.id.map);
        mapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SegmentedUsersAct.this, RouteMapAct.class);
                intent.putExtra(GeocodeConstants.CLUSTER_NAME1,clusterName);
                startActivity(intent);
            }
        });
    }

    @Override
    protected  void onStart(){
        super.onStart();
        getClusterUserInfoList();
    }

    private void getClusterUserInfoList(){
        userInfoLists = (ArrayList<UserInfoList>) getIntent().getSerializableExtra(GeocodeConstants.CLUSTER_USER_LIST);
        clusterName = getIntent().getStringExtra(GeocodeConstants.CLUSTER_NAME);
        userInfoListAdapter = new UserInfoListAdapter(getApplicationContext(), userInfoLists);
        listView.setAdapter(userInfoListAdapter);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Navigate back to parent activity (MainActivity)
//                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}