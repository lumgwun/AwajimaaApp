package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.StandingOrderAdapter;
import com.skylightapp.Adapters.StandingOrderAdapterC;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class SOCompletedList extends AppCompatActivity implements StandingOrderAdapter.OnItemsClickListener {
    ListView mCustomerList;
    Context context;
    Customer customer;
    long customerID;
    LatLng customerLatLng;
    String cusPhoneNo,cusEmail;
    double cusLat,cusLng;
    RecyclerView recyclerView;
    StandingOrderAdapterC orderAdapterC;
    ArrayAdapter<StandingOrder> orderArrayAdapter;
    ArrayList<StandingOrder> standingOrders;
    ArrayList<StandingOrder> standingOrderArrayList;
    DBHelper dbHelper;
    String ManagerSurname,managerFirstName,managerPhoneNumber1,managerEmail, managerNIN,managerUserName;
    SharedPreferences sharedpreferences;
    Gson gson;
    String json,completedStatus;
    int completedSOCount;
    private TextView txtSOCompletedCount;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_socompleted_list);
        recyclerView = findViewById(R.id.recycler_viewSOCompleted);
        txtSOCompletedCount = findViewById(R.id.loanTxt);


        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dbHelper= new DBHelper(this);
        standingOrders =new ArrayList<>();
        standingOrderArrayList =new ArrayList<>();
        standingOrders =dbHelper.getAllStandingOrdersWithStatus("Completed");
        completedSOCount =dbHelper.getAllStandingOrdersWithStatusCount("Completed");
        if(completedSOCount >0){
            txtSOCompletedCount.setText(MessageFormat.format("Completed Standing Orders:{0}", completedSOCount));

        }else if(completedSOCount ==0){
            txtSOCompletedCount.setText("Completed Standing Orders:0");

        }

        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        orderAdapterC = new StandingOrderAdapterC(SOCompletedList.this, standingOrders);
        //recyclerView.setHasFixedSize(true);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(orderAdapterC);
    }

    @Override
    public void onItemClick(StandingOrder standingOrder) {

    }
}