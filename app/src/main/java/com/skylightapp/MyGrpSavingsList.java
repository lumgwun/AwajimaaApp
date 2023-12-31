package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.GroupSavingsAdapter;
import com.skylightapp.Classes.GroupSavings;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GroupSavingsDAO;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Random;

public class MyGrpSavingsList extends AppCompatActivity implements GroupSavingsAdapter.ItemListener {
    private GroupSavingsAdapter groupSavingsAdapter;
    private ArrayList<GroupSavings>groupSavingsArrayList;
    private SharedPreferences userPreferences;
    Profile userProfile;
    Gson gson;
    String json,tittle,purpose,firstName,surname,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    Random ran ;
    GroupSavingsDAO groupSavingsDAO;
    private DBHelper dbHelper;
    int profileID;
    AppCompatTextView txtNoSavings;
    private static final String PREF_NAME = "awajima";
    private SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_grp_savings_list);
        groupSavingsDAO = new GroupSavingsDAO(this);
        dbHelper= new DBHelper(this);
        groupSavingsArrayList = new ArrayList<>();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_GrpS);
        txtNoSavings =  findViewById(R.id.noGrpSavings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("My Group Savings");
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile=new Profile();
        random= new SecureRandom();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        final LinearLayoutManager layoutManager = new LinearLayoutManager( this);
        if(userProfile !=null){
            profileID= userProfile.getPID();
        }
        try {

            if(dbHelper !=null){
                try {

                    if (groupSavingsDAO != null) {
                        groupSavingsArrayList = groupSavingsDAO.getGrpSavingsForCurrentProfile(profileID);
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }



        groupSavingsAdapter = new GroupSavingsAdapter(groupSavingsArrayList, R.layout.grp_savings_row, this);
        recyclerView.setAdapter(groupSavingsAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        //layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        try {

            if(groupSavingsArrayList.size()==0){
                txtNoSavings.setVisibility(View.VISIBLE);
                txtNoSavings.setText("Sorry no Group Savings for this profile yet!");
                recyclerView.setVisibility(View.GONE);

            }else {
                txtNoSavings.setText(MessageFormat.format("No of Grp Savings:{0}", groupSavingsArrayList.size()));

            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


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

        groupSavingsAdapter = new GroupSavingsAdapter(groupSavingsArrayList, R.layout.grp_savings_row, this);
        recyclerView.setAdapter(groupSavingsAdapter);

    }



    @Override
    public void onItemClick(GroupSavings groupSavings) {

    }
    public void goHome(View view) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}