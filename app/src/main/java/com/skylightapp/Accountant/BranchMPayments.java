package com.skylightapp.Accountant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;
import com.skylightapp.Adapters.PaymentAdapterSuper;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class BranchMPayments extends AppCompatActivity implements  PaymentAdapterSuper.OnItemsClickListener {
    private FloatingActionButton fab;
    private ListView payNow;
    private TextView TxtPaymentCounts;
    private TextView txtDetailMessage;
    private Gson gson;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private Button home, settings;

    private RecyclerView recyclerView,recyclerViewAll;

    private ArrayList<Payment> paymentArrayList;
    private ArrayList<Payment> paymentArrayList2;
    private PaymentAdapterSuper mAdapter;
    private PaymentAdapterSuper mAdapter2;

    DBHelper dbHelper;
    String json,dateOfPayment,officeBranch,selectedOffice;
    long profileID;
    private AppCompatButton btnSearch;
    protected DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;
    Calendar calendar;
    DatePicker picker;
    int branchPaymentCount;
    Spinner spnPaymentOffice;
    private SearchView searchView;



    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_branch_m_payments);
        setTitle("Branch Manual Payment");
        calendar=Calendar.getInstance();
        com.github.clans.fab.FloatingActionButton home = findViewById(R.id.AllPayments);
        recyclerView = findViewById(R.id.recycler_AcctantPayment);
        recyclerViewAll = findViewById(R.id.recycler_AcctantPaymentAll);
        btnSearch = findViewById(R.id.btnSearchDB);
        TxtPaymentCounts = findViewById(R.id.TextPayments);
        spnPaymentOffice = findViewById(R.id.SpnPayment);
        picker=(DatePicker)findViewById(R.id.ManualPaymentBranch);
        paymentArrayList = new ArrayList<Payment>();
        paymentArrayList2 = new ArrayList<Payment>();
        dbHelper=new DBHelper(this);
        spnPaymentOffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOffice = spnPaymentOffice.getSelectedItem().toString();
                selectedOffice = (String) parent.getSelectedItem();
                Toast.makeText(BranchMPayments.this, "Office Branch: "+ selectedOffice,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateString = mdformat.format(calendar.getTime());

        //currentDate = dateFormat.format(calendar.getTime());
        //officeBranch="Trans-Amadi";
        //Wimpey   ,   Elelenwo
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        if(branchPaymentCount >0){
            TxtPaymentCounts.setText("Manual Payments:"+ branchPaymentCount);

        }else if(branchPaymentCount ==0){
            TxtPaymentCounts.setText("Manual Payments:0");

        }
        dateOfPayment = picker.getYear() +"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();


        dbHelper = new DBHelper(this);

        try {
            paymentArrayList = dbHelper.getALLPaymentsBranchToday(selectedOffice,dateString);
            paymentArrayList2 = dbHelper.getALLPaymentsSuperToday(dateString);
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        mAdapter = new PaymentAdapterSuper(this, R.layout.super_payment_row, paymentArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        mAdapter2 = new PaymentAdapterSuper(this, R.layout.super_payment_row, paymentArrayList2);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerViewAll.setLayoutManager(linearLayoutManager2);
        recyclerViewAll.setItemAnimator(new DefaultItemAnimator());
        recyclerViewAll.setAdapter(mAdapter2);
        recyclerViewAll.setNestedScrollingEnabled(false);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewAll.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                try {
                    paymentArrayList = dbHelper.getALLPaymentsBranchToday(selectedOffice,dateOfPayment);
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
                mAdapter = new PaymentAdapterSuper(BranchMPayments.this, R.layout.super_payment_row, paymentArrayList);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(BranchMPayments.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);
                recyclerView.setNestedScrollingEnabled(false);
                //SnapHelper snapHelper = new PagerSnapHelper();
                //snapHelper.attachToRecyclerView(recyclerView);

            }
        });
        btnSearch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerViewAll.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }
        });

    }
    private void chooseDate() {
        dateOfPayment = picker.getYear() +"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.branch_m_payment, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searchMPayments)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter2.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter2.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_searchMPayments) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onItemClick(Payment payment) {

    }
}