package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.gson.Gson;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.PaymentAdapterTeller;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.AdminBalance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ManualPaymentList extends AppCompatActivity implements PaymentAdapterTeller.OnItemsClickListener{
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    Gson gson;
    String json;
    private Profile userProfile;
    private RecyclerView recyclerView;

    private ArrayList<Payment> paymentArrayList;
    private PaymentAdapterTeller mAdapter;
    private int profileID;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_manual_payment_list);
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        userProfile= new Profile();
        PaymentCode paymentCode=new PaymentCode();
        AdminBalance adminBalance= new AdminBalance();
        bundle=getIntent().getExtras();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        Date newDate = calendar.getTime();
        String todayDate = sdf.format(newDate);
        dbHelper = new DBHelper(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
        }

        com.github.clans.fab.FloatingActionButton home = findViewById(R.id.notification_teller);
        recyclerView = findViewById(R.id.recycler_TellerPayment);
        paymentArrayList = new ArrayList<Payment>();

        mAdapter = new PaymentAdapterTeller(this, R.layout.payment_list_row, paymentArrayList);

        dbHelper = new DBHelper(this);

        paymentArrayList = dbHelper.getALLPaymentsTeller(profileID);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onItemClick(Payment payment) {
        bundle=getIntent().getExtras();
        bundle.putParcelable("Payment",payment);
        Intent listIntentManual = new Intent(ManualPaymentList.this, ManualPCodeUpdate.class);
        listIntentManual.putExtras(bundle);
        startActivity(listIntentManual);

    }
}