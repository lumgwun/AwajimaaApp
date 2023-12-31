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
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.skylightapp.Adapters.TranxAdminA;
import com.skylightapp.Adapters.TranxSimpleAdapter;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.UpdateTranxAct;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TransAmadiTranx extends AppCompatActivity implements TranxAdminA.OnItemsClickListener{
    private List<Transaction> transactionList;
    private TranxSimpleAdapter transactionAdapter;

    private ArrayList<Transaction> transactionArrayList;
    private ArrayList<Transaction> customTransactionArrayList;
    int branchTranxCount;
    private DBHelper dbHelper;
    //private Date today;
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
    private  SimpleDateFormat dateFormat;
    Calendar calendar;
    private TranXDAO tranXDAO;
    private SQLiteDatabase sqLiteDatabase;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trans_amadi_tranx);
        transactionArrayList = new ArrayList<Transaction>();
        customTransactionArrayList = new ArrayList<Transaction>();
        calendar=Calendar.getInstance();
        dbHelper=new DBHelper(this);
        tranXDAO= new TranXDAO(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTXTR);
        RecyclerView recyclerViewTXToday = findViewById(R.id.recyclerViewTXToday);
        picker=(DatePicker)findViewById(R.id.TX_date_);
        txtTransactionCount4theDay =findViewById(R.id.txCount);
        txtTransactionTotal4theDay =findViewById(R.id.txAmount);
        dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy");

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
        btnSearchDB = findViewById(R.id.buttonSearchTXDB);
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        customDayDate = calendar.getTime();

        currentDate = dateFormat.format(customDayDate);


        LinearLayoutManager layoutManagerC
                = new LinearLayoutManager(TransAmadiTranx.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTXToday.setLayoutManager(layoutManagerC);
        //recyclerViewTXToday.setHasFixedSize(true);
        try {
            transactionArrayList = tranXDAO.getTransactionsForBranchAtDate(officeBranch,currentDate);
            branchTranxCount =tranXDAO.getTransactionCountForBranchAtDate(officeBranch,currentDate);
            transactionTotal=tranXDAO.getTotalTransactionForBranchAtDate(officeBranch,currentDate);

        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        transactionAdapter = new TranxSimpleAdapter(TransAmadiTranx.this,transactionArrayList);
        recyclerViewTXToday.setAdapter(transactionAdapter);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewTXToday.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewTXToday.addItemDecoration(dividerItemDecoration7);


        if(transactionTotal >0){
            txtTransactionTotal4theDay.setText(MessageFormat.format("TranX Total:{0}", transactionTotal));

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
                if(dbHelper !=null){
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    try {
                        transactionArrayList = tranXDAO.getTransactionsForBranchAtDate(officeBranch,dateOfTransaction);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                }

                if(dbHelper !=null){
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    try {
                        transactionTotal=tranXDAO.getTotalTransactionForBranchAtDate(officeBranch,dateOfTransaction);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                }


                if(dbHelper !=null){
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    try {
                        branchTranxCount =tranXDAO.getTransactionCountForBranchAtDate(officeBranch,dateOfTransaction);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }



                }

                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(TransAmadiTranx.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManagerC);
                //recyclerView.setHasFixedSize(true);
                transactionAdapter = new TranxSimpleAdapter(TransAmadiTranx.this,customTransactionArrayList);
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
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