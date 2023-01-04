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
import com.skylightapp.Adapters.TranxAdminA;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Loan;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.PaymentAdapterTeller;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDetailAct extends AppCompatActivity {
    private Bundle customerBundle;
    private Customer customer;
    private int customerID;
    private List<Transaction> transactionList;
    private TranxAdminA tranxAdminA;
    private ArrayList<Transaction> transactionArrayList;
    private ArrayList<Payment> paymentArrayList;
    private ArrayList<StandingOrder> standingOrderArrayList;
    private ArrayList<MarketBizPackage> marketBizPackageArrayList;
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
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_detail);
        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        messageDAO= new MessageDAO(this);
        codeDAO= new CodeDAO(this);
        loanDAO= new LoanDAO(this);
        acctDAO= new AcctDAO(this);
        RecyclerView recyclerViewTX = findViewById(R.id.recycler_viewTX);
        RecyclerView recyclerViewPackages = findViewById(R.id.recycler_viewPacks);
        RecyclerView recyclerViewSavings = findViewById(R.id.recycler_viewSavings33);
        RecyclerView recyclerViewStandingOrder = findViewById(R.id.recycler_viewSO);
        RecyclerView recyclerViewLoan = findViewById(R.id.recycler_Loans);
        transactionArrayList = new ArrayList<Transaction>();
        standingOrderArrayList = new ArrayList<StandingOrder>();
        customerDailyReports = new ArrayList<CustomerDailyReport>();
        marketBizPackageArrayList = new ArrayList<MarketBizPackage>();
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
            loanArrayList=loanDAO.getAllLoansCustomer(customerID);
            transactionArrayList=tranXDAO.getAllTransactionCustomer(customerID);
            standingOrderArrayList=sodao.getAllStandingOrdersForCustomer(customerID);
            customerDailyReports=dbHelper.getAllCustomerDailyReports(customerID);
            marketBizPackageArrayList =dbHelper.getAllPackagesCustomer(customerID);
            loanCount=loanDAO.getCustomerLoanCount(customerID);
            soCount=sodao.getCustomerSOCount(customerID);
            savingsCount=dbHelper.getCustomerTotalSavingsCount(customerID);
            transactionCount=tranXDAO.getCustomerTotalTXCount(customerID);
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
        skyLightPackageAdapter = new SkyLightPackageAdapter(CustomerDetailAct.this, marketBizPackageArrayList);
        recyclerViewPackages.setAdapter(skyLightPackageAdapter);
        DividerItemDecoration dividerItemDecorationPack = new DividerItemDecoration(recyclerViewPackages.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewPackages.addItemDecoration(dividerItemDecorationPack);




        LinearLayoutManager layoutManagerT
                = new LinearLayoutManager(CustomerDetailAct.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTX.setLayoutManager(layoutManagerT);
        //recyclerViewTX.setHasFixedSize(true);
        tranxAdminA = new TranxAdminA(CustomerDetailAct.this,transactionArrayList);
        recyclerViewTX.setAdapter(tranxAdminA);
        DividerItemDecoration dividerItemDecorationT = new DividerItemDecoration(recyclerViewTX.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewTX.addItemDecoration(dividerItemDecorationT);


    }
}