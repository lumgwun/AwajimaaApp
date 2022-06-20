package com.skylightapp.Accountant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.skylightapp.Adapters.TransactionAdapter;
import com.skylightapp.Adapters.TranxSimpleAdapter;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.UpdateTranxAct;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ElelenwoTranx extends AppCompatActivity implements TransactionAdapter.OnItemsClickListener{
    private TranxSimpleAdapter transactionAdapter;

    private ArrayList<Transaction> transactionArrayList;
    private ArrayList<Transaction> customTransactionArrayList;
    int branchTranxCount;
    private DBHelper dbHelper;
    String currentDate;
    Date tomorrowDate;
    Date twoDaysDate;
    Date sevenDaysDate;
    Date customDayDate;
    private Profile userProfile;
    String SharedPrefUserPassword;
    long reportTime;
    int noOfDay;
    String officeBranch;

    TextView txtTransactionCount4theDay,txtTransactionTotal4theDay;
    private AppCompatButton btnSearchDB;
    String dateOfToday,dateOfCustomDays,dateOfTransaction;
    DatePicker picker;
    double transactionTotal;
    protected DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_elelenwo_tranx);
        transactionArrayList = new ArrayList<Transaction>();
        customTransactionArrayList = new ArrayList<Transaction>();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTXElelenwo);
        RecyclerView recyclerViewTXToday = findViewById(R.id.recyclerViewElelenwoTXToday);
        picker=(DatePicker)findViewById(R.id.TX_date_Elelenwo);
        txtTransactionCount4theDay =findViewById(R.id.txCountElelenwo);
        txtTransactionTotal4theDay =findViewById(R.id.txAmountElelenwo);
        dbHelper=new DBHelper(this);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfTransaction = picker.getDayOfMonth()+"-"+ (picker.getMonth() + 1)+"-"+picker.getYear();
        SnapHelper snapHelper = new PagerSnapHelper();
        officeBranch="Trans-Amadi";
        //Wimpey   ,   Elelenwo
        btnSearchDB = findViewById(R.id.btnSearchTXDBElelenwo);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        customDayDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormat.format(calendar.getTime());

        try {
            transactionArrayList = dbHelper.getTransactionsForBranchAtDate(officeBranch,currentDate);
            branchTranxCount =dbHelper.getTransactionCountForBranchAtDate(officeBranch,currentDate);
            transactionTotal=dbHelper.getTotalTransactionForBranchAtDate(officeBranch,currentDate);

        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManagerC
                = new LinearLayoutManager(ElelenwoTranx.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTXToday.setLayoutManager(layoutManagerC);
        //recyclerViewTXToday.setHasFixedSize(true);
        transactionAdapter = new TranxSimpleAdapter(ElelenwoTranx.this,transactionArrayList);
        recyclerViewTXToday.setAdapter(transactionAdapter);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewTXToday.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewTXToday.addItemDecoration(dividerItemDecoration7);

        if(transactionTotal >0){
            txtTransactionTotal4theDay.setText("TranX Total:"+ transactionTotal);

        }else if(transactionTotal ==0){
            txtTransactionTotal4theDay.setText("TranX Total:N0");

        }
        if(branchTranxCount >0){
            txtTransactionCount4theDay.setText("Transactions:"+ branchTranxCount);

        }else if(branchTranxCount ==0){
            txtTransactionCount4theDay.setText("Transactions:0");

        }
        dateOfTransaction = picker.getDayOfMonth()+"-"+ (picker.getMonth() + 1)+"-"+picker.getYear();

        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                recyclerViewTXToday.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                try {
                    transactionArrayList = dbHelper.getTransactionsForBranchAtDate(officeBranch,dateOfTransaction);
                    transactionTotal=dbHelper.getTotalTransactionForBranchAtDate(officeBranch,dateOfTransaction);
                    branchTranxCount =dbHelper.getTransactionCountForBranchAtDate(officeBranch,dateOfTransaction);
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }


                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(ElelenwoTranx.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManagerC);
                //recyclerView.setHasFixedSize(true);
                transactionAdapter = new TranxSimpleAdapter(ElelenwoTranx.this,customTransactionArrayList);
                recyclerView.setAdapter(transactionAdapter);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerView.getContext(),
                        layoutManagerC.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration7);
                snapHelper.attachToRecyclerView(recyclerView);



                if(branchTranxCount >0){
                    txtTransactionCount4theDay.setText(MessageFormat.format("Transactions:{0}", branchTranxCount));

                }else if(branchTranxCount ==0){
                    txtTransactionCount4theDay.setText("Transactions:0");

                }
                if(transactionTotal >0){
                    txtTransactionTotal4theDay.setText("TranX Total:"+ transactionTotal);

                }else if(transactionTotal ==0){
                    txtTransactionTotal4theDay.setText("TranX Total:N0");

                }

            }
        });
        btnSearchDB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerViewTXToday.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                return false;
            }
        });


    }
    private void chooseDate() {
        dateOfTransaction = picker.getDayOfMonth()+"-"+ (picker.getMonth() + 1)+"-"+picker.getYear();

    }

    @Override
    public void onItemClick(Transaction transaction) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("Transaction", transaction);
        Intent intent = new Intent(this, UpdateTranxAct.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}