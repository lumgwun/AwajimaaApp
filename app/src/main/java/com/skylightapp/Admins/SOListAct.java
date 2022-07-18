package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.StandingOrderAdapter;
import com.skylightapp.Adapters.StandingOrderAdapterC;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Customers.StandingOrderList;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;

public class SOListAct extends AppCompatActivity implements StandingOrderAdapter.OnItemsClickListener{
    ListView mCustomerList;
    Context context;
    Customer customer;
    long customerID;
    LatLng customerLatLng;
    String cusPhoneNo,cusEmail;
    double cusLat,cusLng;
    RecyclerView recyclerView;
    StandingOrderAdapter orderAdapterC;
    ArrayAdapter<StandingOrder> orderArrayAdapter;
    ArrayList<StandingOrder> standingOrders =new ArrayList<>();
    DBHelper dbHelper;
    String ManagerSurname,managerFirstName,managerPhoneNumber1,managerEmail, managerNIN,managerUserName;
    SharedPreferences sharedpreferences;
    Gson gson;
    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_solist);
        recyclerView = findViewById(R.id.recycler_viewSOList);
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        dbHelper= new DBHelper(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dbHelper= new DBHelper(this);
        standingOrders =dbHelper.getAllStandingOrders11();
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        orderAdapterC = new StandingOrderAdapter(SOListAct.this, standingOrders);
        //recyclerView.setHasFixedSize(true);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(orderAdapterC);
    }

    @Override
    public void onItemClick(StandingOrder standingOrder) {

    }
}