package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.skylightapp.Adapters.AddressesAdapter;
import com.skylightapp.Classes.AddressesModel;

import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.DeliveryAct.SELECT_ADDRESS;

public class MyAddressesAct extends AppCompatActivity {
    private RecyclerView myaddressesRecyclerView;
    private static AddressesAdapter addressesAdapter;
    private AppCompatButton deliverHereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        ((SimpleItemAnimator)myaddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
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