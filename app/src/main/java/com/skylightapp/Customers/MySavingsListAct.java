package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.skylightapp.Adapters.SavingsAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirectorActivity;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class MySavingsListAct extends AppCompatActivity {
    private RecyclerView recyclerView;

    private ArrayList<CustomerDailyReport> customerDailyReports;
    private SavingsAdapter mAdapter;
    private Customer customer;
    private int customerID;
    private Bundle cusBundle;
    private SearchView searchView;

    private static final String PREF_NAME = "skylight";
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    Profile userProfile;
    String SharedPrefUserName;
    SharedPreferences sharedPreferences;
    String SharedPrefUserPassword;
    int SharedPrefCusID;
    String SharedPrefUserMachine;
    String SharedPrefState;
    String SharedPrefOffice;
    String SharedPrefAddress;
    String SharedPrefJoinedDate;
    String SharedPrefGender;
    String name;
    String SharedPrefRole;
    String SharedPrefDOB;
    String SharedPrefPhone;
    String SharedPrefEmail;
    int SharedPrefProfileID;
    String SharedPrefSurName;
    String SharedPrefFirstName;
    String SharedPrefAcctNo;
    int customerId;

    private DBHelper dbHelper;
    TextView txtSavingsMessage;
    private FloatingActionButton hFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_savings_list);
        dbHelper=new DBHelper(this);
        cusBundle= new Bundle();
        recyclerView = findViewById(R.id.recycler_view_MySavings);
        txtSavingsMessage = findViewById(R.id.myCusS);
        hFab = findViewById(R.id.savings_fab2);

        gson = new Gson();
        customer=new Customer();
        customerDailyReports = new ArrayList<>();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = sharedPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword = sharedPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID = sharedPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine = sharedPreferences.getString("machine", "");
        SharedPrefProfileID = sharedPreferences.getInt("PROFILE_ID", 0);
        SharedPrefRole = sharedPreferences.getString("PROFILE_ROLE", "");
        SharedPrefJoinedDate = sharedPreferences.getString("PROFILE_DATE_JOINED", "");
        SharedPrefOffice = sharedPreferences.getString("CHOSEN_OFFICE", "");
        customerId = sharedPreferences.getInt("CUSTOMER_ID", 0);
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        hFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle= new Bundle();
                bundle.putParcelable("Profile",userProfile);
                bundle.putInt("PROFILE_ID",SharedPrefProfileID);
                bundle.putInt("CUSTOMER_ID",SharedPrefCusID);
                bundle.putString("USER_NAME",SharedPrefUserName);
                bundle.putString("USER_PASSWORD",SharedPrefUserPassword);
                bundle.putString("PROFILE_USERNAME",SharedPrefUserName);
                bundle.putString("PROFILE_PASSWORD",SharedPrefUserPassword);
                Intent loginRIntent = new Intent(MySavingsListAct.this, LoginDirectorActivity.class);
                loginRIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginRIntent);

            }
        });
        cusBundle=getIntent().getExtras();
        if(cusBundle !=null){
            customer=cusBundle.getParcelable("Customer");
            customerID=cusBundle.getInt("customerID");

        }else {
            if(userProfile !=null){
                customer=userProfile.getTimelineCustomer();
                customerID=customer.getCusUID();
            }
        }
        customerDailyReports = dbHelper.getCustomerDailyReportForCustomer(customerID);

        if(customerDailyReports.size()==0){
            txtSavingsMessage.setText(MessageFormat.format("Savings:", "0"));
        }else {
            txtSavingsMessage.setText(MessageFormat.format("Savings:", customerDailyReports.size()));
        }
        mAdapter = new SavingsAdapter(this, customerDailyReports);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_savings_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searchSavings)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_searchSavings) {
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
}