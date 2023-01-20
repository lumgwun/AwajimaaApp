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
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.skylightapp.Adapters.OfficeAdapter;
import com.skylightapp.Adapters.ProfileSimpleAdapter;
import com.skylightapp.Adapters.ProfileSpinnerAdapter;
import com.skylightapp.Adapters.TellerCashAdapter;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MarketBizDAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.Tellers.TellerCash;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<OfficeBranch> officeBranchArrayList;
    Gson gson;
    TellerCash tellerCash;
    AppCompatButton btnSelectBranch,btnSelectTeller;
    Spinner spnTeller,spnBranch;
    private String branchByName,tellerByName;
    LinearLayout layoutSpn,layoutBranch;
    private long bizID;
    private SQLiteDatabase sqLiteDatabase;
    private static final String PREF_NAME = "awajima";
    private OfficeAdapter officeAdapter;

    String machine,selectedOffice;
    private Awajima awajima;
    OfficeBranch officeBranch;
    Profile userProfile;
    Gson gson2,gson3;
    String json,json2,json3,userRole;
    private MarketBusiness marketBiz;
    private MarketBizDAO marketBizDAO;
    private TCashDAO tCashDAO;
    OfficeBranch selectedBranch;
    private SharedPreferences userPreferences;
    private int index=0;

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
        gson = new Gson();
        gson2 = new Gson();
        gson3= new Gson();
        awajima = new Awajima();
        officeBranch= new OfficeBranch();
        marketBizDAO=new MarketBizDAO(this);
        marketBiz= new MarketBusiness();
        userProfile= new Profile();
        selectedBranch= new OfficeBranch();
        tCashDAO= new TCashDAO(this);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerViewBranch = findViewById(R.id.mRecyclerViewBranch);
        mRecyclerViewTeller = findViewById(R.id.mRecyclerViewTeller);
        tellerCashArrayListBranches= new ArrayList<>();
        tellerCashArrayListTeller= new ArrayList<>();
        officeBranchArrayList = new ArrayList<OfficeBranch>();
        spnTeller = findViewById(R.id.spnTellerCash);
        spnBranch = findViewById(R.id.spnBranchTC);
        btnSelectBranch = findViewById(R.id.buttonBranchTC);
        btnSelectTeller = findViewById(R.id.buttonTellerCash);
        layoutSpn = findViewById(R.id.grp_Teller);
        layoutBranch = findViewById(R.id.layout5);
        tellerCashList = new ArrayList<>();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastAwajimaUsed", "");
        awajima = gson.fromJson(json, Awajima.class);
        userRole = userPreferences.getString("machine", "");

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBiz = gson2.fromJson(json2, MarketBusiness.class);
        if(marketBiz !=null){
            bizID=marketBiz.getBusinessID();
            officeBranchArrayList=marketBiz.getOfficeBranches();
            tellerCashList=marketBiz.getBizTellerCashList();
        }

        mAdapter = new TellerCashAdapter(TellerCashList.this,tellerCashList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(TellerCashList.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        SnapHelper snapHelper1 = new PagerSnapHelper();
        snapHelper1.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(TellerCashList.this);

        mProgressBar = findViewById(R.id.myDataLoaderProgressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        if(tCashDAO !=null){
            try {
                tellerCashStringTeller=tCashDAO.getAllTellerCashTellerNames();

            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }
        if(tCashDAO !=null){
            try {
                tellerCashStringBranch=tCashDAO.getAllTellerCashBranchNames();

            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }

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
                if(index==position){
                    return;
                }else {
                    tellerByName = (String) parent.getSelectedItem();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(tCashDAO !=null){
            try {
                tellerCashArrayListTeller=tCashDAO.getTellerCashByTellerName(tellerByName);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }



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

        officeAdapter = new OfficeAdapter(TellerCashList.this, officeBranchArrayList);
        spnBranch.setAdapter(officeAdapter);
        spnBranch.setSelection(0);

        spnBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(index==position){
                    return;
                }else {
                    selectedBranch = (OfficeBranch) parent.getSelectedItem();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(selectedBranch !=null){
            selectedOffice=selectedBranch.getOfficeBranchName();
        }

        branchByName=selectedOffice;
        if(tCashDAO !=null){
            try {
                tellerCashArrayListBranches=tCashDAO.getTellerCashForBranch(branchByName);

            } catch (SQLiteException e) {
                e.printStackTrace();
            }

        }


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
                mRecyclerViewBranch.setAdapter(tellerCashArrayAdapterB);
                tellerCashArrayAdapterB.setOnItemClickListener(TellerCashList.this);

            }
        });

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

    public void onItemClick(int position) {
        TellerCash tellerCash= tellerCashList.get(position);
        if(tellerCash !=null){
            try {
                String[] teacherData={tellerCash.getTellerCashTellerName(),tellerCash.getTellerCashItemName(), String.valueOf(tellerCash.getTellerCashAmount())};


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
        //openDetailActivity(teacherData);
    }

    @Override
    public void onShowItemClick(int position) {
        TellerCash tellerCash= tellerCashList.get(position);
        if(tellerCash !=null){
            try {
                String[] teacherData={tellerCash.getTellerCashTellerName(),tellerCash.getTellerCashItemName(), String.valueOf(tellerCash.getTellerCashAmount())};


            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onDeleteItemClick(int position) {
        TellerCash selectedItem= tellerCashList.get(position);
        TellerCash tellerCash= tellerCashList.get(position);
        if(tellerCash !=null){
            try {
                final String selectedKey = String.valueOf(selectedItem.getTellerCashID());

            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void getBranchTellerCash(View view) {
    }

    public void getTellerTC(View view) {
    }

}