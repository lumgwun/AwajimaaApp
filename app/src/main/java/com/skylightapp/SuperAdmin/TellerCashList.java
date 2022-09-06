package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.skylightapp.Adapters.ProfileSimpleAdapter;
import com.skylightapp.Adapters.ProfileSpinnerAdapter;
import com.skylightapp.Adapters.StandingOrderAdapter;
import com.skylightapp.Adapters.TellerCashAdapter;
import com.skylightapp.Admins.AdminPackageActivity;
import com.skylightapp.Classes.CusSimpleAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.R;
import com.skylightapp.Tellers.MyTellerCashAdapter;
import com.skylightapp.Tellers.TellerCash;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TellerCashList extends AppCompatActivity implements TellerCashAdapter.OnItemClickListener{
    private RecyclerView mRecyclerView,mRecyclerViewBranch,mRecyclerViewTeller;
    private TellerCashAdapter mAdapter;
    private ProgressBar mProgressBar;
    private List<TellerCash> tellerCashList;
    TellerCashAdapter tellerCashArrayAdapterT;
    TellerCashAdapter tellerCashArrayAdapterB;
    TellerCashAdapter tellerCashArrayAdapterA;
    ArrayList<TellerCash> tellerCashArrayListTeller;
    ArrayList<TellerCash> tellerCashArrayListBranches;
    ArrayList<TellerCash> tellerCashArrayListAll;
    ArrayList<String> tellerCashStringTeller;
    ArrayList<String> tellerCashStringBranch;
    ProfileSimpleAdapter customerArrayAdapterBranch;
    ProfileSimpleAdapter arrayAdapterTeller;
    ProfileSimpleAdapter arrayAdapterBranch;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    Gson gson;
    TellerCash tellerCash;
    AppCompatButton btnSelectBranch,btnSelectTeller;
    Spinner spnTeller,spnBranch;
    private String branchByName,tellerByName;
    LinearLayout layoutSpn,layoutBranch;
    private void openDetailActivity(String[] data){
        /*Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("TELLER_CASH_TELLER_NAME",data[0]);
        intent.putExtra("TELLER_CASH_ITEM_NAME",data[1]);
        intent.putExtra("TELLER_CASH_AMOUNT",data[2]);
        startActivity(intent);*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_cash_list);
        dbHelper = new DBHelper(this);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerViewBranch = findViewById(R.id.mRecyclerViewBranch);
        mRecyclerViewTeller = findViewById(R.id.mRecyclerViewTeller);
        tellerCashArrayListBranches= new ArrayList<>();
        tellerCashArrayListTeller= new ArrayList<>();
        spnTeller = findViewById(R.id.spnTellerCash);
        spnBranch = findViewById(R.id.spnBranchTC);
        btnSelectBranch = findViewById(R.id.buttonBranchTC);
        btnSelectTeller = findViewById(R.id.buttonTellerCash);
        layoutSpn = findViewById(R.id.grp_Teller);
        layoutBranch = findViewById(R.id.layout5);
        tellerCashList = new ArrayList<>();


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(TellerCashList.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TellerCashAdapter(TellerCashList.this,tellerCashList);
        //mRecyclerView.setHasFixedSize(true);
        SnapHelper snapHelper1 = new PagerSnapHelper();
        snapHelper1.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setHasFixedSize(true);
        mAdapter.setOnItemClickListener(TellerCashList.this);

        mProgressBar = findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        TCashDAO tCashDAO= new TCashDAO(this);

        tellerCashStringTeller=tCashDAO.getAllTellerCashTellerNames();
        tellerCashStringBranch=tCashDAO.getAllTellerCashBranchNames();


        try {
            spnTeller.setAdapter(new ProfileSpinnerAdapter(TellerCashList.this, android.R.layout.simple_spinner_dropdown_item,
                    tellerCashStringTeller));
            arrayAdapterTeller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnTeller.setAdapter(arrayAdapterTeller);
            spnTeller.setSelection(0);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        spnTeller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //branchName1 = spnPaymentBranchT.getSelectedItem().toString();
                tellerByName = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        tellerCashArrayListTeller=tCashDAO.getTellerCashByTellerName(tellerByName);


        btnSelectTeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutSpn.setVisibility(View.VISIBLE);

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(TellerCashList.this, LinearLayoutManager.HORIZONTAL, false);
                mRecyclerViewTeller.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewTeller.getContext(),
                        layoutManager.getOrientation());
                mRecyclerViewTeller.addItemDecoration(dividerItemDecoration);
                mRecyclerViewTeller.setItemAnimator(new DefaultItemAnimator());
                tellerCashArrayAdapterT = new TellerCashAdapter(TellerCashList.this,tellerCashArrayListTeller);
                //mRecyclerViewTeller.setHasFixedSize(true);
                mRecyclerViewTeller.setAdapter(tellerCashArrayAdapterT);
                tellerCashArrayAdapterT.setOnItemClickListener(TellerCashList.this);

            }
        });


        try {
            spnBranch.setAdapter(new ProfileSpinnerAdapter(TellerCashList.this, android.R.layout.simple_spinner_dropdown_item,
                    tellerCashStringBranch));
            arrayAdapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnBranch.setAdapter(arrayAdapterBranch);
            spnBranch.setSelection(0);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        spnBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //branchName1 = spnPaymentBranchT.getSelectedItem().toString();
                branchByName = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        tellerCashArrayListBranches=tCashDAO.getTellerCashForBranch(branchByName);

        btnSelectBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutBranch.setVisibility(View.VISIBLE);

                LinearLayoutManager layoutManager
                        = new LinearLayoutManager(TellerCashList.this, LinearLayoutManager.HORIZONTAL, false);
                mRecyclerViewBranch.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewBranch.getContext(),
                        layoutManager.getOrientation());
                mRecyclerViewBranch.addItemDecoration(dividerItemDecoration);
                mRecyclerViewBranch.setItemAnimator(new DefaultItemAnimator());
                tellerCashArrayAdapterB = new TellerCashAdapter(TellerCashList.this,tellerCashArrayListBranches);
                //mRecyclerViewBranch.setHasFixedSize(true);
                //SnapHelper snapHelper1 = new PagerSnapHelper();
                //snapHelper1.attachToRecyclerView(mRecyclerViewBranch);
                mRecyclerViewBranch.setAdapter(tellerCashArrayAdapterB);
                tellerCashArrayAdapterB.setOnItemClickListener(TellerCashList.this);

            }
        });

    }
    public void onItemClick(int position) {
        TellerCash tellerCash= tellerCashList.get(position);
        String[] teacherData={tellerCash.getTellerCashTellerName(),tellerCash.getTellerCashItemName(), String.valueOf(tellerCash.getTellerCashAmount())};
        //openDetailActivity(teacherData);
    }

    @Override
    public void onShowItemClick(int position) {
        TellerCash tellerCash= tellerCashList.get(position);
        String[] teacherData={tellerCash.getTellerCashTellerName(),tellerCash.getTellerCashItemName(), String.valueOf(tellerCash.getTellerCashAmount())};
        //openDetailActivity(teacherData);
    }

    @Override
    public void onDeleteItemClick(int position) {
        TellerCash selectedItem= tellerCashList.get(position);
        final String selectedKey = String.valueOf(selectedItem.getTellerCashID());

    }
    protected void onDestroy() {
        super.onDestroy();
    }


    public void getBranchTellerCash(View view) {
    }

    public void getTellerTC(View view) {
    }
}