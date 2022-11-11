package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.skylightapp.Adapters.CartAdapter;
import com.skylightapp.Classes.CartItemModel;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAct extends AppCompatActivity {
    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddNewAddressBtn;
    public static final int SELECT_ADDRESS = 0;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_delivery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");


        deliveryRecyclerView = findViewById(R.id.delivery_recyclerview);
        changeOrAddNewAddressBtn = findViewById(R.id.change_or_add_address_btn);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0, R.drawable.hp_16gb_1tb_ssd_608, "HP,16Gb,1 TB Laptop", 2, "NGN 1,1,000/-", "5000/-", 1, 0, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.hp_32gb_2tb_ssd_, "HP, 32Gb, 2Tb Laptop", 0, "NGN 1,9,900/-", "1000/-", 1, 1, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.hidden_camera, "Hidden watch Camera", 2, "NGN 21,100/-", "1000/-", 1, 2, 0));
        cartItemModelList.add(new CartItemModel(1, "Price (3) items", "NGN 3022000/-", "Free", "NGN 302300/-", "NGN 7000/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        changeOrAddNewAddressBtn.setVisibility(View.VISIBLE);
        changeOrAddNewAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyAddressesIntent = new Intent(DeliveryAct.this, MyAddressesAct.class);
                MyAddressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(MyAddressesIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);

    }
}