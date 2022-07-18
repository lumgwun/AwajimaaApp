package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.StandingOrderAdapterC;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;

public class StandingOrderList extends AppCompatActivity implements StandingOrderAdapterC.OrderAdapterClickListener {
    ListView mCustomerList;
    Context context;
    Customer customer;
    int customerID;
    LatLng customerLatLng;
    String cusPhoneNo,cusEmail;
    double cusLat,cusLng;
    RecyclerView recyclerView,recyclerViewDate;
    StandingOrderAdapterC orderAdapterC;
    StandingOrderAdapterC orderAdapterAll;
    ArrayAdapter<StandingOrder> orderArrayAdapter;
    ArrayAdapter<StandingOrder> standingOrderArrayAdapter;
    ArrayList<StandingOrder> standingOrders ;
    ArrayList<StandingOrder> standingOrdersAll;

    DBHelper dbHelper;
    String ManagerSurname,managerFirstName,managerPhoneNumber1,managerEmail, managerNIN,managerUserName;
    SharedPreferences sharedpreferences;
    Gson gson;
    String json,dateOfSO;
    AppCompatTextView txtSOCount;
    int soCount,soAllCount;
    DatePicker picker;
    protected DatePickerDialog datePickerDialog;
    private AppCompatButton btnSearchDB;
    private Gson gson1;
    private String json1;
    private  Profile userProfile;
    private  int profileID;
    private static final String PREF_NAME = "skylight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_standing_order_list);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnSearchDB = this.findViewById(R.id.buttonSearchSo);
        gson1 = new Gson();
        userProfile=new Profile();
        customer= new Customer();
        recyclerViewDate = findViewById(R.id.recyclerViewDateSO);
        recyclerView = findViewById(R.id.recycler_viewS);
        txtSOCount = findViewById(R.id.txtSOCount44);
        picker =findViewById(R.id._date_So);
        standingOrdersAll =new ArrayList<>();
        standingOrders =new ArrayList<>();

        txtSOCount =findViewById(R.id.txt_ReportCount);
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json1 = sharedpreferences.getString("LastProfileUsed", "");
        gson = new Gson();

        json = sharedpreferences.getString("LastCustomerUsed", "");
        customer = gson.fromJson(json, Customer.class);
        dbHelper= new DBHelper(this);
        userProfile = gson1.fromJson(json1, Profile.class);

        if(customer !=null){
            customerID=customer.getCusUID();
        }
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

        dateOfSO = picker.getDayOfMonth()+"-"+ (picker.getMonth() + 1)+"-"+picker.getYear();
        soAllCount =dbHelper.getSOCountCustomer(customerID);
        soCount = dbHelper.getCustomerSOCountForDate(customerID,dateOfSO);
        standingOrders=dbHelper.getAllStandingOrdersForCustomerDate(customerID,dateOfSO);
        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                recyclerViewDate.setVisibility(View.VISIBLE);
                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(StandingOrderList.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerViewDate.setLayoutManager(layoutManagerC);
                //recyclerViewDate.setHasFixedSize(true);
                orderAdapterC = new StandingOrderAdapterC(StandingOrderList.this, standingOrders);
                recyclerViewDate.setAdapter(orderAdapterC);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewDate.getContext(),
                        layoutManagerC.getOrientation());
                recyclerViewDate.addItemDecoration(dividerItemDecoration7);
                txtSOCount = findViewById(R.id.txtSOCount44);

                if(soCount >0){

                    txtSOCount.setText(MessageFormat.format("Standing Orders:{0}", soCount));

                }else if(soCount ==0){
                    txtSOCount.setText("Sorry! no any standing orders");

                }

            }
        });
        btnSearchDB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewDate.setVisibility(View.GONE);
                return false;
            }
        });
        if (customer !=null){
            customerID=customer.getCusUID();
            dbHelper= new DBHelper(this);
            standingOrdersAll =dbHelper.getSOFromCurrentCustomer(customerID);
            final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addOnScrollListener(new CenterScrollListener());
            layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            orderAdapterAll = new StandingOrderAdapterC(StandingOrderList.this, standingOrdersAll);
            //recyclerView.setHasFixedSize(true);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(orderAdapterAll);

        }


    }
    private void chooseDate() {
        dateOfSO = picker.getDayOfMonth()+"-"+ (picker.getMonth() + 1)+"-"+picker.getYear();

    }

    @Override
    public void standingOrderClicked(int position) {

    }
}