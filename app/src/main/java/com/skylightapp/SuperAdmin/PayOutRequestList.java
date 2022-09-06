package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.skylightapp.Adapters.PaymentRequestAdap;
import com.skylightapp.Classes.TransactionGranting;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.EmergReportDAO;
import com.skylightapp.Database.TransactionGrantingDAO;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class PayOutRequestList extends AppCompatActivity implements PaymentRequestAdap.OnItemsClickListener{
    private ArrayList<TransactionGranting> transactionGrantings;
    private ArrayList<TransactionGranting> customtranxGrantings;
    private PaymentRequestAdap requestAdap;
    private DBHelper dbHelper;
    Date tomorrowDate;
    Date twoDaysDate;
    Date sevenDaysDate;
    Date customDayDate;
    String currentDate;

    TextView txtPayoutAmount, txtPayoutCounts,txPayoutMonthTotal;
    private AppCompatButton btnSearchDB;
    String dateOfToday,dateOfCustomDays,dateOfTransaction;
    DatePicker picker;
    double totalPayouts,totalPayoutsForTheMonth;
    private int payoutTranxCount;
    protected DatePickerDialog datePickerDialog;
    RecyclerView recyclerView, recyclerViewCustom;
    private SearchView searchView;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_out_request);
        transactionGrantings = new ArrayList<TransactionGranting>();
        customtranxGrantings = new ArrayList<TransactionGranting>();
        recyclerView = findViewById(R.id.recyclerViewAllPayouts);
        recyclerViewCustom = findViewById(R.id.recyclerViewCusPayout);
        picker=(DatePicker)findViewById(R.id.TX_date_Payout);
        txtPayoutAmount =findViewById(R.id.txPayoutAmount);
        txtPayoutCounts =findViewById(R.id.txPayoutCounts);
        txPayoutMonthTotal =findViewById(R.id.txPayoutMonthTotal);
        dbHelper=new DBHelper(this);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfTransaction = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

        SnapHelper snapHelper = new PagerSnapHelper();

        btnSearchDB = findViewById(R.id.btnSearchPayout);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        customDayDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormat.format(calendar.getTime());

        TransactionGrantingDAO grantingDAO = new TransactionGrantingDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            transactionGrantings = grantingDAO.getAllTransactionGranting();
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            customtranxGrantings =grantingDAO.getTranxRequestAtDate(currentDate);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            totalPayouts=grantingDAO.getTotalTranxRequestAtDate(currentDate);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            payoutTranxCount=grantingDAO.getTranxRequestCountAtDate(currentDate);
        }


        try {
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                sqLiteDatabase = dbHelper.getReadableDatabase();
                totalPayoutsForTheMonth=grantingDAO.getTotalTranxExtraForTheMonth1(currentDate);
            }



        }catch (ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManagerC
                = new LinearLayoutManager(PayOutRequestList.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCustom.setLayoutManager(layoutManagerC);
        requestAdap = new PaymentRequestAdap(PayOutRequestList.this,transactionGrantings);
        recyclerViewCustom.setAdapter(requestAdap);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewCustom.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewCustom.addItemDecoration(dividerItemDecoration7);
        btnSearchDB.setOnClickListener(this::getCustomPayouts);


        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                recyclerViewCustom.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);


                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(PayOutRequestList.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManagerC);
                requestAdap = new PaymentRequestAdap(PayOutRequestList.this,customtranxGrantings);
                recyclerView.setAdapter(requestAdap);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerView.getContext(),
                        layoutManagerC.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration7);
                snapHelper.attachToRecyclerView(recyclerView);



                if(payoutTranxCount >0){
                    txtPayoutAmount.setText(MessageFormat.format("Payouts:{0}", payoutTranxCount));

                }else if(payoutTranxCount ==0){
                    txtPayoutAmount.setText("Payouts:0");

                }
                if(totalPayoutsForTheMonth >0){
                    txPayoutMonthTotal.setText("Payout for the Month: N"+ totalPayoutsForTheMonth);

                }else if(totalPayoutsForTheMonth ==0){
                    txPayoutMonthTotal.setText("Payout for the Month :N0");

                }
                if(totalPayouts >0){
                    txtPayoutCounts.setText("Payout Total:"+ totalPayouts);

                }else if(totalPayouts ==0){
                    txtPayoutCounts.setText("Payout Total:N0");

                }

            }
        });
        btnSearchDB.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerViewCustom.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                return false;
            }
        });

    }
    private void chooseDate() {
        dateOfTransaction = picker.getYear()+"/"+ (picker.getMonth() + 1)+"/"+picker.getDayOfMonth();

    }

    public void getCustomPayouts(View view) {
    }

    @Override
    public void onItemClick(TransactionGranting transactionGranting) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("TransactionGranting", transactionGranting);
        Intent intent = new Intent(this, TranxApprovalAct.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.payout_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_searchPayouts)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                requestAdap.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                requestAdap.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_searchPayouts) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}