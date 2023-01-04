package com.skylightapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Adapters.SavingsAdapter;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Database.DBHelper;

import java.text.MessageFormat;
import java.util.ArrayList;

public class PackageDetailsActivity extends AppCompatActivity implements SavingsAdapter.OnItemsClickListener {
    Bundle bundle;
    DBHelper dbHelper;
    String packageName,startDate,newStartDate,endDate,manager,surName,userNames,status,firstName,type,email,address,customerOfficeBranch,phoneNumber;
    int packageId;
    double paymentAmount,totalAmount,savedAmount,remAmount;
    int duration;
    LatLng location;
    double aDouble;
    MarketBizPackage skylightPackage;
    AppCompatTextView txtName,txtType,txtPackID,txtTittle,txtPackageAmount,txtStartDate,txtDuration,txtGrandTotal;
    private RecyclerView recyclerView;
    private ArrayList<CustomerDailyReport> customerDailyReports;
    private SavingsAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_package_details);
        recyclerView = findViewById(R.id.recycler_savings_);
        dbHelper = new DBHelper(this);
        txtName = findViewById(R.id.packageCusName);
        txtTittle = findViewById(R.id.packageTitle3);
        txtPackID = findViewById(R.id.Id2Pack);
        txtPackageAmount = findViewById(R.id.AmtPackage);
        txtType = findViewById(R.id.TypePackage);
        txtDuration = findViewById(R.id.DurationPackage);
        txtStartDate = findViewById(R.id.StartDatePackage);
        txtGrandTotal = findViewById(R.id.grandTotalPackage);
        bundle=getIntent().getExtras();
        if(bundle !=null) {
            duration = bundle.getInt("duration");
            skylightPackage = bundle.getParcelable("Package");
            remAmount = bundle.getDouble("remAmount");
            savedAmount = bundle.getDouble("savedAmount");
            totalAmount = bundle.getDouble("totalAmount");
            paymentAmount = bundle.getDouble("paymentAmount");
            txtPackageAmount.setText(MessageFormat.format("Amt:N{0}", paymentAmount));
            packageId = bundle.getInt("packageId");
            txtPackID.setText(MessageFormat.format("Pack ID:{0}", packageId));
            txtType.setText(MessageFormat.format("Type:{0}", skylightPackage.getPackageType()));
            txtDuration.setText(MessageFormat.format("Duration:{0}", duration));
            txtStartDate.setText(MessageFormat.format("Start Date:{0}", skylightPackage.getPackageDateStarted()));
            txtGrandTotal.setText(MessageFormat.format("Grand Total:N{0}", skylightPackage.getPackageTotalAmount()));
            txtName.setText(MessageFormat.format("Customer:{0}", skylightPackage.getPackageCustomerName()));
            customerDailyReports = dbHelper.getPackageSavings2(packageId);
            mAdapter = new SavingsAdapter(PackageDetailsActivity.this, customerDailyReports);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
            //DocumentDataPrepare();
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mLayoutManager.getBaseline());
            recyclerView.addItemDecoration(dividerItemDecoration);
            packageName=bundle.getString(packageName);
            startDate=bundle.getString(startDate);
            endDate=bundle.getString(endDate);
            manager=bundle.getString(manager);
            surName=bundle.getString(surName);
            userNames=bundle.getString(userNames);
            status=bundle.getString(status);
            firstName=bundle.getString(firstName);
            type=bundle.getString(type);
            email=bundle.getString(email);
            phoneNumber=bundle.getString(phoneNumber);
            customerOfficeBranch=bundle.getString(customerOfficeBranch);
            address=bundle.getString(address);
        }else {
            txtTittle.setText("No savings yet, for no Package ");
        }

    }


    @Override
    public void onItemClick(CustomerDailyReport customerDailyReport) {

    }
}