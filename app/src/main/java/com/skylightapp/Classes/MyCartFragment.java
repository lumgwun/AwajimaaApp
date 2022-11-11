package com.skylightapp.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Adapters.CartAdapter;
import com.skylightapp.Customers.AddAddressAct;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {


    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemsRecyclerView;
    private AppCompatButton continueBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemsRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
        continueBtn=view.findViewById(R.id.cart_continue_btn);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(linearLayoutManager);
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0, R.drawable.hidden_camera, "Hidden Camera", 2, "NGN 70000/-", "1000/-", 1, 0, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.hp_32gb_2tb_ssd_, "High performance 32Gb RAM,2Tb HD Laptop", 0, "NGN 1200000/-", "49000/-", 1, 1, 0));
        cartItemModelList.add(new CartItemModel(1, "Price (2) items", "NGN1270000/-", "Free", "NGN1220000/-", "NGN 50000/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(getContext(), AddAddressAct.class);
                getContext().startActivity(deliveryIntent);
            }
        });
        return view;
    }
}
