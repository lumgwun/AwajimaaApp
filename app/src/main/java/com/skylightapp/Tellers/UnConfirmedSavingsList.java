package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.SavingsAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;


public class UnConfirmedSavingsList extends AppCompatActivity implements SavingsAdapter.OnItemsClickListener{
    private Bundle bundle;
    private long paymentID;
    private int customerID;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    private Profile userProfile;
    private int profileID;
    AppCompatButton btnCustomerID;
    AppCompatEditText edtCustomerID;
    private Gson gson,gson1;
    private String json,json1;
    long code;
    private FloatingActionButton fab;
    private ListView payNow;
    private TextView txtTitleMessage;
    private TextView txtDetailMessage;
    private SharedPreferences userPreferences;
    private Button home, settings;

    private RecyclerView recyclerView,recyclerView2;

    private ArrayList<CustomerDailyReport> customerDailyReports;
    private SavingsAdapter mAdapter;
    private Customer customer;
    String todayDate;
    LinearLayout linearLayout;
    private static final String PREF_NAME = "skylight";
    String SharedPrefUserPassword,noOfDays, status,stringNoOfSavings,office, customerPhoneNo,officeBranch,dateOfReport,nameOfCustomer, cmFirstName,cmLastName,cmName,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_un_con_savings);
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        userProfile= new Profile();
        bundle= new Bundle();
        dbHelper= new DBHelper(this);
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        linearLayout = findViewById(R.id.layoutUn);
        recyclerView = findViewById(R.id.recycler_TellerAllUnconfirmed);
        recyclerView2 = findViewById(R.id.recycler_TellerUnConfirmed);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            customer=bundle.getParcelable("Customer");
        }
        if(customer !=null){
            customerID =customer.getCusUID();
        }else{
            linearLayout.setVisibility(View.VISIBLE);
            recyclerView2.setVisibility(View.GONE);
            customerDailyReports = new ArrayList<CustomerDailyReport>();

            mAdapter = new SavingsAdapter(this, R.layout.savings_list_row, customerDailyReports);

            dbHelper = new DBHelper(this);

            customerDailyReports = dbHelper.getIncompleteSavingsForTeller(profileID,"unPaid");

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            bundle= new Bundle();
            dbHelper= new DBHelper(this);
            bundle=getIntent().getExtras();
        }
        SharedPrefUserName=sharedpreferences.getString("USER_NAME", "");
        SharedPrefUserPassword=sharedpreferences.getString("USER_PASSWORD", "");
        SharedPrefUserMachine=sharedpreferences.getString("machine", "");
        SharedPrefProfileID=sharedpreferences.getString("PROFILE_ID", "");
        dbHelper = new DBHelper(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }
        btnCustomerID = findViewById(R.id.CustomerIDButton);
        edtCustomerID = findViewById(R.id.customerID);
        btnCustomerID.setOnClickListener(this::getIDReports);

    }

    public void getIDReports(View view) {
        linearLayout = findViewById(R.id.layoutUn);
        recyclerView = findViewById(R.id.recycler_TellerAllUnconfirmed);
        recyclerView.setVisibility(View.GONE);
        customerDailyReports = new ArrayList<CustomerDailyReport>();

        mAdapter = new SavingsAdapter(this, R.layout.savings_list_row, customerDailyReports);

        dbHelper = new DBHelper(this);
        bundle= new Bundle();
        dbHelper= new DBHelper(this);
        bundle=getIntent().getExtras();
        if(bundle !=null){
            customer=bundle.getParcelable("Customer");
        }
        if(customer !=null){
            customerID =customer.getCusUID();
        }else {
            try {
                customerID = Integer.parseInt((Objects.requireNonNull(edtCustomerID.getText()).toString()));
            } catch (Exception e) {
                System.out.println("Oops!");
                edtCustomerID.requestFocus();
            }

        }
        customerDailyReports = dbHelper.getCustomerIncompleteSavings(customerID,"unconfirmed");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(linearLayoutManager);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());
        recyclerView2.setAdapter(mAdapter);
        recyclerView2.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView2);

    }

    @Override
    public void onItemClick(CustomerDailyReport customerDailyReport) {
        Bundle userBundle=new Bundle();
        userBundle.putParcelable("CustomerDailyReport",customerDailyReport);
        Intent itemPurchaseIntent = new Intent(UnConfirmedSavingsList.this, SavingsCodeUpdateAct.class);
        itemPurchaseIntent.putExtras(userBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    }
}