package com.skylightapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.GroupAcctAdapter;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GroupAccountDAO;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class GroupSavingsAcctList extends AppCompatActivity implements GroupAcctAdapter.ItemListener {
    private GroupAcctAdapter groupAcctAdapter;
    private ArrayList<GroupAccount> groupAccountArrayList;
    private ArrayList<GroupAccount> groupAccountAmtRange;
    private ArrayList<GroupAccount> groupAccountDuration;
    private GroupAcctAdapter groupAcctAmtAdapter;
    private GroupAcctAdapter groupAcctDurationAdapter;
    private SharedPreferences userPreferences;
    Profile userProfile;
    Gson gson;
    String json,tittle,purpose,firstName,surname,phoneNo,emailAddress,machine,bundleID,bundleMachine;
    SecureRandom random;
    DatePicker picker;
    Random ran ;
    DBHelper dbHelper;
    int profileID;
    AppCompatTextView txtNoSavings;
    private static final String PREF_NAME = "awajima";
    private  long minDuration, maxDuration,minAmount,maxAmount,maxElapseMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_grp_savings_acct);
        dbHelper = new DBHelper(this);
        groupAccountArrayList = new ArrayList<>();
        groupAccountAmtRange= new ArrayList<>();
        groupAccountDuration= new ArrayList<>();
        userProfile=new Profile();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_GrpSavingsAcct);
        RecyclerView recyclerD = (RecyclerView) findViewById(R.id.recycler_GrpSavingsAcct);
        txtNoSavings =  findViewById(R.id.noGrpSavingsAcct2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        GroupAccountDAO groupAccountDAO= new GroupAccountDAO(this);
        minDuration= userPreferences.getLong("minDuration", 0);
        maxDuration= userPreferences.getLong("maxDuration", 0);
        minAmount = userPreferences.getLong("minMatchAmount", 0);
        maxAmount = userPreferences.getLong("maxMatchAmount", 0);
        //maxElapseMonth = userPreferences.getLong("maxElapseMonth", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        if(minAmount >0){
            if(maxAmount>0){

                try {
                    groupAccountAmtRange=groupAccountDAO.getGrpAccountForAmtRange(minAmount,maxAmount);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            }
        }else {
            Intent myIntent = new Intent(GroupSavingsAcctList.this, GSavingsMatchAct.class);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);

        }
        if(minDuration >0){
            if(maxDuration>0){
                try {
                    groupAccountDuration=groupAccountDAO.getGrpAccountDurationRange(minDuration,maxDuration);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }



            }
        }else {
            Intent myIntent = new Intent(GroupSavingsAcctList.this, GSavingsMatchAct.class);
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);

        }
        groupAccountAmtRange.addAll(groupAccountDuration);

        final CarouselLayoutManager layoutD = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        groupAcctAmtAdapter = new GroupAcctAdapter(groupAccountAmtRange, R.layout.grp_acct_row, this);
        recyclerD.setAdapter(groupAcctAmtAdapter);
        recyclerD.setLayoutManager(layoutD);
        recyclerD.addOnScrollListener(new CenterScrollListener());
        layoutD.setPostLayoutListener(new CarouselZoomPostLayoutListener());


        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);

        try {
            groupAccountArrayList = groupAccountDAO.getGrpAcctsForCurrentProfile(profileID);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        groupAcctAdapter = new GroupAcctAdapter(groupAccountArrayList, R.layout.grp_acct_row, this);
        recyclerView.setAdapter(groupAcctAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        try {

            if(groupAccountArrayList.size()==0){
                txtNoSavings.setVisibility(View.VISIBLE);
                txtNoSavings.setText("Sorry no Group Savings for this profile yet!");
                recyclerView.setVisibility(View.GONE);

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

        groupAcctAdapter = new GroupAcctAdapter(groupAccountArrayList, R.layout.grp_savings_row, this);
        recyclerView.setAdapter(groupAcctAdapter);

    }


    @Override
    public void onItemClick(GroupAccount groupAccount) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        bundle.putParcelable("GroupAccount", groupAccount);
        doMoreDialog(groupAccount);

    }

    private void doMoreDialog(GroupAccount groupAccount) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        //userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        bundle.putParcelable("GroupAccount", groupAccount);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Grp. Acct Action");
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"Add more Savers", "Update Account","Delete Grp. Savings Account"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:

                                Toast.makeText(GroupSavingsAcctList.this, "Add new Group Saver, selected", Toast.LENGTH_SHORT).show();
                                Intent savingsIntent = new Intent(GroupSavingsAcctList.this, JoinThisGrpSavAct.class);
                                savingsIntent.putExtras(bundle);
                                savingsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                break;
                            case 1:
                                Toast.makeText(GroupSavingsAcctList.this, "packageType Package, selected", Toast.LENGTH_SHORT).show();
                                Intent itemPurchaseIntent = new Intent(GroupSavingsAcctList.this, UpdateGrpAcct.class);
                                itemPurchaseIntent.putExtras(bundle);
                                itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                break;

                            case 2:
                                Toast.makeText(GroupSavingsAcctList.this, "Delete Group Acct, selected", Toast.LENGTH_SHORT).show();
                                doConfirm(groupAccount);
                                break;
                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        builder.create().show();
    }
    public  void  doConfirm(GroupAccount groupAccount){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure of deleting this Group Acct?");
        GroupAccountDAO groupAccountDAO= new GroupAccountDAO(this);
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"Yes", "No"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if(groupAccount.getGrpAcctBalance()==0){
                                    try {
                                        groupAccountDAO.deleteGroupAcct(groupAccount.getGrpAcctNo());

                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }



                                }else {
                                    Toast.makeText(GroupSavingsAcctList.this, "Sorry!,you can not delete an Account with funds", Toast.LENGTH_SHORT).show();


                                }
                                break;
                            case 1:
                               finish();
                                break;

                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(groupAccount.getGrpAcctBalance()==0){
                            try {
                                groupAccountDAO.deleteGroupAcct(groupAccount.getGrpAcctNo());

                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                        }else {
                            Toast.makeText(GroupSavingsAcctList.this, "Sorry!,you can not delete an Account with funds", Toast.LENGTH_SHORT).show();


                        }

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

        builder.create().show();
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