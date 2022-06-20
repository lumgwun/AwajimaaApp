package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.LoanAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


public class CustomerLoanListAct extends AppCompatActivity implements LoanAdapter.OnLoanInteractionListener {
    DBHelper dbHelper;
    SharedPreferences userPreferences;
    Gson gson;
    String json;
    Profile userProfile;
    String SharedPrefUserName;
    SharedPreferences sharedPreferences;
    String SharedPrefUserPassword;
    int SharedPrefCusID;
    String SharedPrefUserMachine;
    String SharedPrefState;
    String SharedPrefOffice;
    String SharedPrefAddress;
    String SharedPrefJoinedDate;
    String SharedPrefGender;
    String name;
    String SharedPrefRole;
    String SharedPrefDOB;
    String SharedPrefPhone;
    String SharedPrefEmail;
    int SharedPrefProfileID;
    String SharedPrefSurName;
    String SharedPrefFirstName;
    String SharedPrefAcctNo;
    int customerId;
    String SharedPrefBankNo;
    String SharedPrefAcctBalance;
    String SharedPrefAcctName;
    String SharedPrefType;
    String SharedPrefBank;
    private LoanAdapter.OnLoanInteractionListener listener;
    private LoanAdapter loanAdapter;
    private List<Loan> loans;
    private ArrayList<Loan> loanArrayList;
    private  Customer customer;
    private int customerID;
    private RecyclerView recyclerView;
    TextView txtLoanMessage;
    private Bundle cusBundle;
    private static final String PREF_NAME = "skylight";

    private static final String TAG = CustomerLoanListAct.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cust_loan_list);
        dbHelper= new DBHelper(this);
        recyclerView = findViewById(R.id.recycler_loan_Customer);
        txtLoanMessage = findViewById(R.id.loanText);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPrefUserName = sharedPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword = sharedPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefCusID = sharedPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefUserMachine = sharedPreferences.getString("machine", "");
        SharedPrefProfileID = sharedPreferences.getInt("PROFILE_ID", 0);
        SharedPrefRole = sharedPreferences.getString("PROFILE_ROLE", "");
        SharedPrefJoinedDate = sharedPreferences.getString("USER_DATE_JOINED", "");
        SharedPrefOffice = sharedPreferences.getString("PROFILE_OFFICE", "");

        customerId = sharedPreferences.getInt("CUSTOMER_ID", 0);
        SharedPrefAcctNo = sharedPreferences.getString("ACCOUNT_NO", "");
        SharedPrefBankNo = sharedPreferences.getString("BANK_ACCT_NO", "");
        SharedPrefAcctBalance = sharedPreferences.getString("ACCOUNT_BALANCE", "");
        SharedPrefAcctName = sharedPreferences.getString("ACCOUNT_NAME", "");
        SharedPrefType = sharedPreferences.getString("ACCOUNT_TYPE", "");
        SharedPrefBank = sharedPreferences.getString("ACCOUNT_BANK", "");
        gson = new Gson();
        customer=new Customer();
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        cusBundle=getIntent().getExtras();
        if(cusBundle !=null){
            customer=cusBundle.getParcelable("Customer");
            customerID=cusBundle.getInt("customerID");

        }else {
            if(userProfile !=null){
                customer=userProfile.getTimelineCustomer();


            }
        }
        if(customer !=null){
            customerID=customer.getCusUID();
            loanArrayList = dbHelper.getLoansFromCurrentCustomer(customerID);

        }

        txtLoanMessage.setText(MessageFormat.format("Loans:", loanArrayList.size()));
        if(loanArrayList.size()==0){
            txtLoanMessage.setText(MessageFormat.format("Loans:", "0"));
        }

        loanArrayList = new ArrayList<Loan>();
        txtLoanMessage.setText(MessageFormat.format("Loans:", "0"));
        loanAdapter = new LoanAdapter(this, loanArrayList);
        dbHelper = new DBHelper(this);

        loanArrayList = dbHelper.getLoansFromCurrentCustomer(customerID);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(loanAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);

    }

    @Override
    public void onItemClick(Loan item) {
        Bundle bundle=new Bundle();
        Intent intent = new Intent(this, CusLoanRepaymentAct.class);
        bundle.putParcelable("Loan",item);
        startActivity(intent);

    }


}