package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.GroupAcctAdapter;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class MyGrpSavings extends AppCompatActivity implements GroupAcctAdapter.ItemListener{
    private GroupAcctAdapter groupAcctAdapter;
    private ArrayList<GroupAccount> groupAccountArrayList ;
    DBHelper dbHelper;
    private SharedPreferences userPreferences;
    Profile userProfile;
    Gson gson;
    String json,tittle,purpose,firstName,surname,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    long profileID;
    Random ran ;
    AppCompatTextView txtNoGrpSavingsAcct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_grp_savings);
        dbHelper = new DBHelper(this);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        groupAccountArrayList = new ArrayList<>();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_GrpAcct);
        txtNoGrpSavingsAcct =  findViewById(R.id.noGrpSavingsAcct);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        userProfile=new Profile();
        random= new SecureRandom();
        json = userPreferences.getString("LastProfileUsed", "");
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        groupAccountArrayList = dbHelper.getGrpAcctsForCurrentProfile(profileID);
        groupAcctAdapter = new GroupAcctAdapter(groupAccountArrayList, R.layout.grp_acct_row, this);
        recyclerView.setAdapter(groupAcctAdapter);
        recyclerView.setLayoutManager(layoutManager);
        try {
            if(groupAccountArrayList.size()==0){
                txtNoGrpSavingsAcct.setVisibility(View.VISIBLE);
                txtNoGrpSavingsAcct.setText("Sorry no Group Accounts for this profile yet!");
                recyclerView.setVisibility(View.GONE);

            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        //recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

        recyclerView.addOnItemTouchListener(new MyTouchListener(this,
                recyclerView,
                new MyTouchListener.OnTouchActionListener() {
                    @Override
                    public void onLeftSwipe(View view, int position) {//code as per your need
                    }

                    @Override
                    public void onRightSwipe(View view, int position) {//code as per your need
                    }

                    @Override
                    public void onClick(View view, int position) {//code as per your need
                    }
                }));

        groupAcctAdapter = new GroupAcctAdapter(groupAccountArrayList, R.layout.grp_savings_row, this);
        recyclerView.setAdapter(groupAcctAdapter);

    }

    @Override
    public void onItemClick(GroupAccount groupAccount) {

    }
    public void goHome(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, LoginDirectorActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}