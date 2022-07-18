package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.GrpTranxAdapter;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class GrpProfileTraxs extends AppCompatActivity implements  GrpTranxAdapter.OnItemsClickListener{
    private GrpTranxAdapter grpTranxAdapter;
    private ArrayList<Transaction> transactionArrayList;

    DBHelper dbHelper;
    private SharedPreferences userPreferences;
    Profile userProfile;
    Gson gson;
    String json,tittle,purpose,firstName,surname,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    long profileID, grpAcctID;
    Random ran ;
    AppCompatTextView txtNoGrpSavingsUsers;
    private GroupAccount groupAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_grp_profile_traxs);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        userProfile=new Profile();
        random= new SecureRandom();
        json = userPreferences.getString("LastProfileUsed", "");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle bundle = getIntent().getExtras() ;
        dbHelper = new DBHelper(this);
        if(bundle !=null){
            groupAccount = bundle.getParcelable("GroupAccount");
            grpAcctID=groupAccount.getGrpAcctNo();
            transactionArrayList = dbHelper.getAllGrpAcctTranxs(grpAcctID);
            grpTranxAdapter = new GrpTranxAdapter(GrpProfileTraxs.this, transactionArrayList);
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_GrpSTx);
            txtNoGrpSavingsUsers =  findViewById(R.id.noGrpSavingsTx);
            final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
            recyclerView.setLayoutManager(layoutManager);
            //recyclerView.setHasFixedSize(true);
            recyclerView.addOnScrollListener(new CenterScrollListener());
            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

            recyclerView.setAdapter(grpTranxAdapter);

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

            try {
                if(transactionArrayList.size()==0){
                    txtNoGrpSavingsUsers.setVisibility(View.VISIBLE);
                    txtNoGrpSavingsUsers.setText("Sorry no Group Transactions for this User, yet!");
                    recyclerView.setVisibility(View.GONE);

                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }

        }




    }

    public void goHome(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }


    @Override
    public void onItemClick(Transaction transaction) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        bundle.putParcelable("Transaction", transaction);
        Intent intent = new Intent(this, GrpProfileTraxs.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}