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
import android.content.SharedPreferences;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.TranxAdminA;
import com.skylightapp.Adapters.TranxSimpleAdapter;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.MarketClasses.MarketBizArrayAdapter;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.UpdateTranxAct;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ElelenwoTranx extends AppCompatActivity implements TranxAdminA.OnItemsClickListener{
    private TranxSimpleAdapter transactionAdapter;

    private ArrayList<Transaction> transactionArrayList;
    private ArrayList<Transaction> customTransactionArrayList;
    int branchTranxCount;
    private DBHelper dbHelper;
    String currentDate;
    Date customDayDate;
    String officeBranch;

    TextView txtTransactionCount4theDay,txtTransactionTotal4theDay;
    private AppCompatButton btnSearchDB;
    String dateOfTransaction;
    DatePicker picker;
    double transactionTotal;
    protected DatePickerDialog datePickerDialog;
    SQLiteDatabase sqLiteDatabase;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;
    private CusDAO cusDAO;
    private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private static final String PREF_NAME = "skylight";
    private MarketBusiness marketBiz;
    private OfficeBranch office;
    private long bizID;
    Gson gson3,gson2,gson;
    String json3,json2;
    private OfficeBranchDAO officeBranchDAO;
    private String userRole;
    private SharedPreferences userPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_elelenwo_tranx);
        setTitle("Transactions");
        gson = new Gson();
        gson2 = new Gson();
        gson3= new Gson();
        office= new OfficeBranch();
        dbHelper = new DBHelper(this);
        officeBranchDAO= new OfficeBranchDAO(this);
        marketBiz= new MarketBusiness();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        userRole = userPreferences.getString("machine", "");

        json2 = userPreferences.getString("LastMarketBusinessUsed", "");
        marketBiz = gson2.fromJson(json2, MarketBusiness.class);
        json3 = userPreferences.getString("LastOfficeBranchUsed", "");
        office = gson3.fromJson(json3, OfficeBranch.class);
        transactionArrayList = new ArrayList<Transaction>();
        customTransactionArrayList = new ArrayList<Transaction>();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTXElelenwo);
        RecyclerView recyclerViewTXToday = findViewById(R.id.recyclerViewElelenwoTXToday);
        picker=(DatePicker)findViewById(R.id.TX_date_Elelenwo);
        txtTransactionCount4theDay =findViewById(R.id.txCountElelenwo);
        txtTransactionTotal4theDay =findViewById(R.id.txAmountElelenwo);
        dbHelper=new DBHelper(this);
        cusDAO= new CusDAO(this);
        if(office !=null){
            officeBranch=office.getOfficeBranchName();
        }
        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfTransaction = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();
        SnapHelper snapHelper = new PagerSnapHelper();
        officeBranch=officeBranch;
        //Wimpey   ,   Elelenwo
        btnSearchDB = findViewById(R.id.btnSearchTXDBElelenwo);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        customDayDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormat.format(calendar.getTime());

        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getWritableDatabase();
            try {
                transactionArrayList = tranXDAO.getTransactionsForBranchAtDate(officeBranch,currentDate);
            } catch (SQLException e) {
                e.printStackTrace();
            }



        }

        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getWritableDatabase();
            try {
                branchTranxCount =tranXDAO.getTransactionCountForBranchAtDate(officeBranch,currentDate);
            } catch (SQLException e) {
                e.printStackTrace();
            }



        }

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
                transactionTotal=tranXDAO.getTotalTransactionForBranchAtDate(officeBranch,currentDate);
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

        if(dbHelper !=null){
            sqLiteDatabase = dbHelper.getWritableDatabase();
            try {
                transactionTotal=tranXDAO.getTotalTransactionForBranchAtDate(officeBranch,dateOfTransaction);
            } catch (SQLException e) {
                e.printStackTrace();
            }

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
        dateOfTransaction = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                recyclerViewTXToday.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);


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
        dateOfTransaction = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

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