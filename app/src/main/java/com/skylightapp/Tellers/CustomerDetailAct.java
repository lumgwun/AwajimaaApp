package com.skylightapp.Tellers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.skylightapp.Adapters.LoanAdapter;
import com.skylightapp.Adapters.SavingsAdapter;
import com.skylightapp.Adapters.SkyLightPackageAdapter;
import com.skylightapp.Adapters.StandingOrderAdapterC;
import com.skylightapp.Adapters.TransactionAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.PaymentAdapterTeller;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDetailAct extends AppCompatActivity {
    private Bundle customerBundle;
    private Customer customer;
    private int customerID;
    private List<Transaction> transactionList;
    private TransactionAdapter transactionAdapter;
    private ArrayList<Transaction> transactionArrayList;
    private ArrayList<Payment> paymentArrayList;
    private ArrayList<StandingOrder> standingOrderArrayList;
    private ArrayList<SkyLightPackage> skyLightPackageArrayList;
    private ArrayList<CustomerDailyReport> customerDailyReports;
    private ArrayList<Loan> loanArrayList;
    private SavingsAdapter savingsAdapter;
    private SkyLightPackageAdapter skyLightPackageAdapter;
    private PaymentAdapterTeller paymentAdapterTeller;
    private StandingOrderAdapterC standingOrderAdapterC;
    private LoanAdapter loanAdapter;
    int branchTranxCount;
    private DBHelper dbHelper;
    private Date today;
    String currentDate;
    private Profile userProfile;
    String SharedPrefUserPassword;
    long reportTime;
    int loanCount,savingsCount,soCount,transactionCount,packageCount;
    String officeBranch;

    String customerName;
    DatePicker picker;
    //double transactionTotal;
    protected DatePickerDialog datePickerDialog;
    TextView txtTransaction,txtSavings,txtLoan,txtPackages,txtCusName,txtSO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_detail);
        RecyclerView recyclerViewTX = findViewById(R.id.recycler_viewTX);
        RecyclerView recyclerViewPackages = findViewById(R.id.recycler_viewPacks);
        RecyclerView recyclerViewSavings = findViewById(R.id.recycler_viewSavings33);
        RecyclerView recyclerViewStandingOrder = findViewById(R.id.recycler_viewSO);
        RecyclerView recyclerViewLoan = findViewById(R.id.recycler_Loans);
        transactionArrayList = new ArrayList<Transaction>();
        standingOrderArrayList = new ArrayList<StandingOrder>();
        customerDailyReports = new ArrayList<CustomerDailyReport>();
        skyLightPackageArrayList = new ArrayList<SkyLightPackage>();
        txtCusName =findViewById(R.id.CustomerUserDetails);
        txtTransaction =findViewById(R.id.txTransactions);
        txtSO =findViewById(R.id.txSOCount);
        txtSavings =findViewById(R.id.txCountSavings);
        txtLoan =findViewById(R.id.txLoansCount);
        txtPackages =findViewById(R.id.txPackageCount);

        loanArrayList = new ArrayList<Loan>();

        dbHelper=new DBHelper(this);
        customerBundle=getIntent().getExtras();
        if(customerBundle !=null){
            customer=customerBundle.getParcelable("Customer");

        }
        if(customer !=null){
            customerID=customer.getCusUID();
            customerName=customer.getCusSurname()+","+customer.getCusFirstName();

        }
        try {
            loanArrayList=dbHelper.getAllLoansCustomer(customerID);
            transactionArrayList=dbHelper.getAllTransactionCustomer(customerID);
            standingOrderArrayList=dbHelper.getAllStandingOrdersForCustomer(customerID);
            customerDailyReports=dbHelper.getAllCustomerDailyReports(customerID);
            skyLightPackageArrayList=dbHelper.getAllPackagesCustomer(customerID);
            loanCount=dbHelper.getCustomerLoanCount(customerID);
            soCount=dbHelper.getCustomerSOCount(customerID);
            savingsCount=dbHelper.getCustomerTotalSavingsCount(customerID);
            transactionCount=dbHelper.getCustomerTotalTXCount(customerID);
            packageCount=dbHelper.getCustomerTotalPackageCount(customerID);

        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }




        if(customerName !=null){
            txtCusName.setText("Details for:"+ customerName);

        }else if(customerName ==null){
            txtCusName.setText("No Known Customer");

        }
        if(loanCount >0){
            txtLoan.setText("Loans:"+ loanCount);

        }else if(loanCount ==0){
            txtLoan.setText("Loans:N0");

        }
        if(savingsCount >0){
            txtSavings.setText("Savings:"+ savingsCount);

        }else if(savingsCount ==0){
            txtSavings.setText("Savings:0");

        }
        if(transactionCount >0){
            txtTransaction.setText("Transactions:"+ transactionCount);

        }else if(transactionCount ==0){
            txtTransaction.setText("Transactions:0");

        }
        if(packageCount >0){
            txtPackages.setText("Packages:"+ packageCount);

        }else if(packageCount ==0){
            txtPackages.setText("Packages:0");

        }
        if(soCount >0){
            txtPackages.setText("Standing Orders:"+ soCount);

        }else if(soCount ==0){
            txtPackages.setText("Standing Orders:0");

        }

        LinearLayoutManager layoutManagerL
                = new LinearLayoutManager(CustomerDetailAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewLoan.setLayoutManager(layoutManagerL);
        //recyclerViewLoan.setHasFixedSize(true);
        loanAdapter = new LoanAdapter(CustomerDetailAct.this,loanArrayList);
        recyclerViewLoan.setAdapter(loanAdapter);
        DividerItemDecoration dividerItemDecorationL = new DividerItemDecoration(recyclerViewLoan.getContext(),
                layoutManagerL.getOrientation());
        recyclerViewLoan.addItemDecoration(dividerItemDecorationL);

        LinearLayoutManager layoutManagerSO
                = new LinearLayoutManager(CustomerDetailAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewStandingOrder.setLayoutManager(layoutManagerSO);
        //recyclerViewStandingOrder.setHasFixedSize(true);
        standingOrderAdapterC = new StandingOrderAdapterC(CustomerDetailAct.this,standingOrderArrayList);
        recyclerViewStandingOrder.setAdapter(standingOrderAdapterC);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewStandingOrder.getContext(),
                layoutManagerSO.getOrientation());
        recyclerViewStandingOrder.addItemDecoration(dividerItemDecoration7);


        LinearLayoutManager layoutManagerC
                = new LinearLayoutManager(CustomerDetailAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSavings.setLayoutManager(layoutManagerC);
        //recyclerViewSavings.setHasFixedSize(true);
        savingsAdapter = new SavingsAdapter(CustomerDetailAct.this,customerDailyReports);
        recyclerViewSavings.setAdapter(savingsAdapter);
        DividerItemDecoration dividerItemDecorationSavings = new DividerItemDecoration(recyclerViewSavings.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewSavings.addItemDecoration(dividerItemDecorationSavings);




        LinearLayoutManager layoutManagerSaving
                = new LinearLayoutManager(CustomerDetailAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPackages.setLayoutManager(layoutManagerSaving);
        //recyclerViewPackages.setHasFixedSize(true);
        skyLightPackageAdapter = new SkyLightPackageAdapter(CustomerDetailAct.this,skyLightPackageArrayList);
        recyclerViewPackages.setAdapter(skyLightPackageAdapter);
        DividerItemDecoration dividerItemDecorationPack = new DividerItemDecoration(recyclerViewPackages.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewPackages.addItemDecoration(dividerItemDecorationPack);




        LinearLayoutManager layoutManagerT
                = new LinearLayoutManager(CustomerDetailAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTX.setLayoutManager(layoutManagerT);
        //recyclerViewTX.setHasFixedSize(true);
        transactionAdapter = new TransactionAdapter(CustomerDetailAct.this,transactionArrayList);
        recyclerViewTX.setAdapter(transactionAdapter);
        DividerItemDecoration dividerItemDecorationT = new DividerItemDecoration(recyclerViewTX.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewTX.addItemDecoration(dividerItemDecorationT);


    }
}