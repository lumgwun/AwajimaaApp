package com.skylightapp.Classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skylightapp.Adapters.MyOrderAdapter;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment extends Fragment {


    public MyOrdersFragment() {
    }

    private RecyclerView myOrdersRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        myOrdersRecyclerView=view.findViewById(R.id.my_orders_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrdersRecyclerView.setLayoutManager(linearLayoutManager);
        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.water_dispenser,2,"Water Dispenser","Delivered on Mon,15th JAN 2022"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.home_theater_1200,1,"Home Theater","Delivered on Mon,15th FEB 2022"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.hot_plate_double_burner,0,"Hot plate","Cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.keke_bus,4,"Keke Bus","Delivered on Mon,15th MAR 2022"));
        MyOrderAdapter myOrderAdapter=new MyOrderAdapter(myOrderItemModelList);
        myOrdersRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();
        return view;
    }
}
