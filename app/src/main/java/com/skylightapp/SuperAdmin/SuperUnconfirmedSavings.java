package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.ProfileSimpleAdapter;
import com.skylightapp.Adapters.SavingsAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.Tellers.SavingsCodeUpdateAct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SuperUnconfirmedSavings extends AppCompatActivity implements SavingsAdapter.OnItemsClickListener{
    private Bundle bundle;
    private long paymentID;
    private int tellerID;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    private Profile userProfile;
    private Profile teller;
    private long profileID;
    AppCompatButton btnTellerSearch;
    private Gson gson,gson1;
    private String json,json1;
    long code;
    private FloatingActionButton fab;
    private ListView payNow;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private SharedPreferences userPreferences;
    private Button home, settings;

    private RecyclerView recyclerView,recyclerView3;

    private ArrayList<CustomerDailyReport> customerDailyReports;
    private SavingsAdapter mAdapter;
    //private Customer customer;
    String todayDate;
    LinearLayout linearLayout;
    private Spinner spnSelectTeller;
    int selectedTellerIndex;
    ArrayList<Customer> customerArrayList;
    ArrayAdapter<Profile> profileArrayAdapter;
    ArrayList<Profile> profileArrayList;
    private ProfileSimpleAdapter profileSimpleAdapter;
    private static final String PREF_NAME = "skylight";
    private MarketBusiness marketBiz;
    private OfficeBranch office;
    private long bizID;
    String json3,json2;
    Gson gson3,gson2;
    private OfficeBranchDAO officeBranchDAO;
    private SQLiteDatabase sqLiteDatabase;
    private String officeName;

    String SharedPrefUserPassword,noOfDays, tellerMachine,status,stringNoOfSavings, customerPhoneNo,officeBranch,dateOfReport,nameOfCustomer, cmFirstName,cmLastName,cmName,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_unconfirmed_);
        bundle= new Bundle();
        gson = new Gson();
        gson2 = new Gson();
        office= new OfficeBranch();
        gson3= new Gson();
        dbHelper= new DBHelper(this);
        officeBranchDAO= new OfficeBranchDAO(this);
        marketBiz= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBiz = gson2.fromJson(json2, MarketBusiness.class);
        json3 = userPreferences.getString("LastOfficeBranchUsed", "");
        office = gson3.fromJson(json3, OfficeBranch.class);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        linearLayout = findViewById(R.id.layoutSuperUn);
        recyclerView = findViewById(R.id.recycler_AllUnConfirmed);
        recyclerView3 = findViewById(R.id.recycler_Teller8);
        Date newDate = calendar.getTime();
        if(office !=null){
            officeBranch=office.getOfficeBranchName();
        }
        if(marketBiz !=null){
            bizID=marketBiz.getBusinessID();
        }

        tellerMachine=officeBranch;
        String todayDate = sdf.format(newDate);
        dbHelper= new DBHelper(this);
        bundle=getIntent().getExtras();
        profileArrayList= new ArrayList<>();
        btnTellerSearch = findViewById(R.id.TellerIDButton);
        spnSelectTeller = findViewById(R.id.spnTeller);
        ProfDAO profDAO1 = new ProfDAO(this);


        profileArrayList=profDAO1.getTellersFromMachineAndBiz(tellerMachine,bizID);
        btnTellerSearch.setOnClickListener(this::getIDReports);
        if(bundle !=null){
            userProfile=bundle.getParcelable("Profile");
        }

        try {

            spnSelectTeller.setAdapter(new ProfileSimpleAdapter(SuperUnconfirmedSavings.this, R.layout.profile_row,
                    profileArrayList));
            profileArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnSelectTeller.setAdapter(profileSimpleAdapter);
            spnSelectTeller.setSelection(1);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        try {

            selectedTellerIndex = spnSelectTeller.getSelectedItemPosition();

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        try {

            if(profileArrayList.size()>0){
                teller = profileArrayList.get(selectedTellerIndex);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }


        if(teller !=null){
            tellerID=teller.getPID();
        }
        if(userProfile !=null){
            profileID =userProfile.getPID();
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView3.setVisibility(View.GONE);
            recyclerView = findViewById(R.id.recycler_AllUnConfirmed);
            customerDailyReports = new ArrayList<CustomerDailyReport>();

            mAdapter = new SavingsAdapter(this, R.layout.savings_list_row, customerDailyReports);

            dbHelper = new DBHelper(this);

            customerDailyReports = dbHelper.getAllIncompleteSavings("unconfirmed");

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);

        }
        btnTellerSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter = new SavingsAdapter(SuperUnconfirmedSavings.this, R.layout.savings_list_row, customerDailyReports);
                bundle= new Bundle();
                dbHelper= new DBHelper(SuperUnconfirmedSavings.this);
                bundle=getIntent().getExtras();
                if(bundle !=null){
                    userProfile=bundle.getParcelable("Profile");
                }
                if(userProfile !=null){
                    tellerID =userProfile.getPID();
                }else {
                    if(teller !=null){
                        tellerID=teller.getPID();
                    }

                }
                customerDailyReports = dbHelper.getIncompleteSavingsForTeller(tellerID,"unPaid");

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SuperUnconfirmedSavings.this);
                recyclerView3.setLayoutManager(linearLayoutManager);
                recyclerView3.setItemAnimator(new DefaultItemAnimator());
                recyclerView3.setAdapter(mAdapter);
                recyclerView3.setNestedScrollingEnabled(false);
                SnapHelper snapHelper66 = new PagerSnapHelper();
                snapHelper66.attachToRecyclerView(recyclerView3);

            }
        });


    }

    public void getIDReports(View view) {
        linearLayout = findViewById(R.id.layoutSuperUn);
        linearLayout.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recycler_AllUnConfirmed);
        recyclerView.setVisibility(View.GONE);
        recyclerView3 = findViewById(R.id.recycler_Teller8);
        customerDailyReports = new ArrayList<CustomerDailyReport>();



    }
    @Override
    public void onItemClick(CustomerDailyReport customerDailyReport) {
        Bundle userBundle=new Bundle();
        userBundle.putParcelable("CustomerDailyReport",customerDailyReport);
        Intent itemPurchaseIntent = new Intent(SuperUnconfirmedSavings.this, SavingsCodeUpdateAct.class);
        itemPurchaseIntent.putExtras(userBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    }

    public void getTellerSelected(View view) {
    }
}