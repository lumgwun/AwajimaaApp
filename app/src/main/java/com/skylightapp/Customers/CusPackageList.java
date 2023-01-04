package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.PackageRecyclerAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class CusPackageList extends AppCompatActivity implements PackageRecyclerAdapter.OnItemsClickListener{
    private RecyclerView recyclerView;

    private ArrayList<MarketBizPackage> marketBizPackages;
    private PackageRecyclerAdapter mAdapter;
    private Customer customer;
    private int customerID;
    private Bundle cusBundle;

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
    TextView txtPackMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cus_package_list);
        dbHelper=new DBHelper(this);
        recyclerView = findViewById(R.id.recycler_Customer_Packs);
        txtPackMessage = findViewById(R.id.pText);
        gson = new Gson();
        marketBizPackages = new ArrayList<>();
        customer=new Customer();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = sharedPreferences.getString("USER_NAME", "");
        SharedPrefUserPassword = sharedPreferences.getString("USER_PASSWORD", "");
        SharedPrefCusID = sharedPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine = sharedPreferences.getString("machine", "");
        SharedPrefProfileID = sharedPreferences.getInt("PROFILE_ID", 0);
        SharedPrefRole = sharedPreferences.getString("USER_ROLE", "");
        SharedPrefJoinedDate = sharedPreferences.getString("USER_DATE_JOINED", "");
        SharedPrefOffice = sharedPreferences.getString("CHOSEN_OFFICE", "");
        customerId = sharedPreferences.getInt("CUSTOMER_ID", 0);
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        cusBundle=getIntent().getExtras();
        if(cusBundle !=null){
            customer=cusBundle.getParcelable("Customer");
            customerID=cusBundle.getInt("customerID");

        }else {
            if(userProfile !=null){
                customer=userProfile.getProfileCus();
                customerID=customer.getCusUID();

            }
        }
        marketBizPackages = dbHelper.getPackagesFromCustomer(customerID);

        if(marketBizPackages.size()==0){
            txtPackMessage.setText(MessageFormat.format("Packs:", "0"));
        }else {
            txtPackMessage.setText(MessageFormat.format("Packs:", marketBizPackages.size()));
        }
        mAdapter = new PackageRecyclerAdapter(this, marketBizPackages);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onItemClick(MarketBizPackage marketBizPackage) {
        Bundle bundle=new Bundle();
        Intent intent = new Intent(this, OldPackCusAct.class);
        bundle.putParcelable("MarketBizPackage", marketBizPackage);
        startActivity(intent);

    }
}