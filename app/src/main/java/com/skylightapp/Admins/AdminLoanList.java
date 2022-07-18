package com.skylightapp.Admins;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.skylightapp.Adapters.LoanAdapter;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Customers.CusLoanRepaymentAct;
import com.skylightapp.Customers.CustomerLoanListAct;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AdminLoanList extends AppCompatActivity implements LoanAdapter.OnLoanInteractionListener{
    private RecyclerView recyclerView;
    TextView txtLoanMessage;
    private static final String TAG = AdminLoanList.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com";
    private LoanAdapter loanAdapter;
    private List<Loan> loans;
    private ArrayList<Loan> loanArrayList;
    DBHelper dbHelper;
    int loanCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_admin_loan_list);
        recyclerView = findViewById(R.id.recycler_loan_admin2);
        txtLoanMessage = findViewById(R.id.loanText3);
        loanAdapter = new LoanAdapter(this, loanArrayList);
        dbHelper = new DBHelper(this);
        loanArrayList = dbHelper.getAllLoansAdmin();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(loanAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        loanCount= dbHelper.getLoansCountAdmin();
        txtLoanMessage.setText(MessageFormat.format("Loan Count:{0}", loanCount));
    }

    @Override
    public void onItemClick(Loan item) {
        Bundle bundle=new Bundle();
        Intent intent = new Intent(this, LoanApprovalAct.class);
        bundle.putParcelable("Loan",item);
        startActivity(intent);

    }
}