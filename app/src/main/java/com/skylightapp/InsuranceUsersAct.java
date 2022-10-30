package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.blongho.country_data.World;
import com.google.gson.Gson;
import com.skylightapp.Adapters.CustomerAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.InsuranceCompany;
import com.skylightapp.MarketClasses.MarketBusiness;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class InsuranceUsersAct extends AppCompatActivity implements CustomerAdapter.CustomerListener{
    private CustomerAdapter customerAdapter;
    private CustomerAdapter customerAdapterAll;
    private ArrayList<Customer> customerArrayList;
    private ArrayList<Customer> customerAlList;
    private Customer customer;
    private SharedPreferences userPreferences;
    PrefManager prefManager;
    private Gson gson,gson1,gson2,gson3;
    private String json,json1,json2,json3;
    private Profile userProfile;
    private int profileID,count;
    CircleImageView profileImage;
    private Profile profile;
    private Intent data;
    private InsuranceCompany insuranceCompany;
    private static final String PREF_NAME = "awajima";
    private RecyclerView recyclerCus,recyclerCusAll;
    private AppCompatTextView txtCount,txtAllCounts;
    String SharedPrefUserPassword,SharedPrefUserMachine,SharedPrefUserName,bizType;
    private int SharedPrefProfileID,SharedPrefCusID,allCusCounts;
    private MarketBusiness marketBusiness;
    private InsuranceCompany marketBizInsCom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_insur_users);
        recyclerCus = findViewById(R.id.recyclerView_Ins);
        recyclerCusAll = findViewById(R.id.recycler_All_BZ);
        txtAllCounts = findViewById(R.id.counts_Biz_all);

        txtCount = findViewById(R.id.counts_ins);
        gson = new Gson();
        customerArrayList= new ArrayList<>();
        customerAlList= new ArrayList<>();
        gson1 = new Gson();
        gson2 = new Gson();
        gson3 = new Gson();

        userProfile=new Profile();
        customer=new Customer();
        marketBusiness= new MarketBusiness();
        insuranceCompany=new InsuranceCompany();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID=userPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefUserMachine = userPreferences.getString("PROFILE_ROLE", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);
        json2 = userPreferences.getString("LastInsuranceCompanyUsed", "");
        insuranceCompany = gson2.fromJson(json2, InsuranceCompany.class);

        json3 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson3.fromJson(json3, MarketBusiness.class);
        if(marketBusiness !=null){
            bizType=marketBusiness.getBizType();
            customerAlList=marketBusiness.getMBCustomers();

        }
        if(bizType !=null){
            if(bizType.equalsIgnoreCase("Insurance Company")){

            }

        }

        LinearLayoutManager layoutMAll
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerCus.setLayoutManager(layoutMAll);
        recyclerCus.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationL = new DividerItemDecoration(recyclerCus.getContext(),
                layoutMAll.getOrientation());
        recyclerCus.addItemDecoration(dividerItemDecorationL);
        customerAdapterAll = new CustomerAdapter(InsuranceUsersAct.this,customerAlList);
        recyclerCus.setAdapter(customerAdapterAll);

        allCusCounts=customerAdapterAll.getItemCount();
        if(allCusCounts>0){
            txtAllCounts.setText("All Customers:"+allCusCounts);
        }else {
            txtAllCounts.setText("No Customers yet");

        }


        if(insuranceCompany !=null){
            customerArrayList=insuranceCompany.getInsCustomers();

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerCus.setLayoutManager(layoutManager);
            recyclerCus.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration dividerItemDeco = new DividerItemDecoration(recyclerCus.getContext(),
                    layoutManager.getOrientation());
            recyclerCus.addItemDecoration(dividerItemDeco);
            customerAdapter = new CustomerAdapter(InsuranceUsersAct.this,customerArrayList);
            //recyclerAccounts.setHasFixedSize(true);
            recyclerCus.setAdapter(customerAdapter);

            if(customerArrayList !=null){
                count=customerArrayList.size();
                txtCount.setText(count+""+"Customers");
            }else {
                txtCount.setText("No"+""+"Customers yet");

            }
        }else {
        }
    }

    @Override
    public void onItemClick(Customer customer) {

    }
}