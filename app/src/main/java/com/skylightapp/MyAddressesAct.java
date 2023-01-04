package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.skylightapp.Adapters.AddressesAdapter;
import com.skylightapp.Classes.AddressesModel;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.Markets.ProductDetailsAct;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.skylightapp.DeliveryAct.SELECT_ADDRESS;

public class MyAddressesAct extends AppCompatActivity{
    private RecyclerView myaddressesRecyclerView;
    private static AddressesAdapter addressesAdapter;
    private AppCompatButton deliverHereBtn;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1,gson2;
    private String json,json1,json2;
    private Profile userProfile;
    private MarketBusiness marketBusiness;
    private Customer customer;
    private static final String PREF_NAME = "awajima";
    String SharedPrefUserPassword,SharedPrefUserMachine,senderFullNames,phoneNo,SharedPrefUserName,adminName,sender;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_addresses);
        gson = new Gson();
        gson1 = new Gson();
        gson2 = new Gson();
        customer=new Customer();
        marketBusiness=new MarketBusiness();
        userProfile =new Profile();
        Toolbar toolbar = findViewById(R.id.toolbar_Address);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json1, Customer.class);

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBusiness = gson2.fromJson(json2, MarketBusiness.class);

        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        myaddressesRecyclerView = findViewById(R.id.addresses_recyclerview);
        deliverHereBtn = findViewById(R.id.deliver_here_btn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myaddressesRecyclerView.setLayoutManager(linearLayoutManager);
        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Unyeada mini Estate","Andoni","380001",true));
        addressesModelList.add(new AddressesModel("Ngo","Andoni","380001",false));
        addressesModelList.add(new AddressesModel("Ekede","Andoni","380001",false));
        addressesModelList.add(new AddressesModel("Okoroete","EO","380001",false));
        addressesModelList.add(new AddressesModel("Karu","Abuja","380001",false));
        addressesModelList.add(new AddressesModel("Iko","Eastern Obolo","380001",false));
        addressesModelList.add(new AddressesModel("Polokiri","Andoni","380001",false));
        addressesModelList.add(new AddressesModel("Asarameja","Andoni","380001",false));
        addressesModelList.add(new AddressesModel("Isiodum","Andoni","380001",false));

        int mode = getIntent().getIntExtra("MODE",-1);
        if(mode==SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else{
            deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdapter = new AddressesAdapter(addressesModelList,mode);
        myaddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator) Objects.requireNonNull(myaddressesRecyclerView.getItemAnimator())).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();

        deliverHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(MyAddressesAct.this, PayNowActivity.class);
                boolean addressSelected = true;
                startActivity(cartIntent);

            }
        });
    }

    public static  void refreshItem(int deSelect, int select){
        addressesAdapter.notifyItemChanged(deSelect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}