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

import com.skylightapp.Adapters.SavingsAdapter;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.UpdateSavingsAct;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TransAmadiSavings extends AppCompatActivity implements SavingsAdapter.OnItemsClickListener{
    private List<CustomerDailyReport> transactionList;
    private SavingsAdapter savingsAdapter;

    private ArrayList<CustomerDailyReport> customerDailyReports;
    private ArrayList<CustomerDailyReport> customerDailyReportArrayList;
    int branchSavingsCount;
    private DBHelper dbHelper;
    private Date today;
    Date currentDate;
    Date tomorrowDate;
    Date twoDaysDate;
    Date sevenDaysDate;
    Date customDayDate;
    private Profile userProfile;
    String SharedPrefUserPassword;
    long reportTime;
    int noOfDay;
    String officeBranch;

    TextView txtSavingsCount4theDay, txtSavingsTotal4theDay;
    private AppCompatButton btnSearchDB;
    String dateOfToday,dateOfCustomDays, dateOfSavings;
    DatePicker picker;
    double savingsTotal;
    protected DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_trans_amadi_packs);
        setTitle("Trans-Amadi Collections");
        customerDailyReports = new ArrayList<CustomerDailyReport>();
        customerDailyReportArrayList = new ArrayList<CustomerDailyReport>();
        RecyclerView recyclerView = findViewById(R.id.recyclerViewSavingsTR);
        RecyclerView recyclerViewTXToday = findViewById(R.id.recyclerViewTRSavingsToday);
        picker=(DatePicker)findViewById(R.id.Savings_date_TR);
        txtSavingsCount4theDay =findViewById(R.id.SavingsCountTR);
        txtSavingsTotal4theDay =findViewById(R.id.SavingsAmountTR);
        dbHelper=new DBHelper(this);

        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });

        dateOfSavings = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();
        SnapHelper snapHelper = new PagerSnapHelper();
        officeBranch="Elelenwo";
        //   ,   Elelenwo .Trans-Amadi
        btnSearchDB = findViewById(R.id.btnSearchSavingsDBTR);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        customDayDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());


        try {
            dateOfToday = dateFormat.format(calendar.getTime());
            today = dateFormat.parse(dateOfToday);

        } catch (ParseException ignored) {
        }
        customerDailyReports = dbHelper.getSavingsForBranchAtDate(officeBranch,dateOfToday);
        branchSavingsCount =dbHelper.getSavingsCountForBranchAtDate(officeBranch,dateOfToday);
        savingsTotal =dbHelper.getTotalSavingsForBranchAtDate(officeBranch,dateOfToday);
        LinearLayoutManager layoutManagerC
                = new LinearLayoutManager(TransAmadiSavings.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTXToday.setLayoutManager(layoutManagerC);
        savingsAdapter = new SavingsAdapter(TransAmadiSavings.this, customerDailyReports);
        recyclerViewTXToday.setAdapter(savingsAdapter);
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerViewTXToday.getContext(),
                layoutManagerC.getOrientation());
        recyclerViewTXToday.addItemDecoration(dividerItemDecoration7);

        if(savingsTotal >0){
            txtSavingsTotal4theDay.setText("Savings Total:"+ savingsTotal);

        }else if(savingsTotal ==0){
            txtSavingsTotal4theDay.setText("Savings Total:N0");

        }
        if(branchSavingsCount >0){
            txtSavingsCount4theDay.setText("Savings:"+ branchSavingsCount);

        }else if(branchSavingsCount ==0){
            txtSavingsCount4theDay.setText("Savings:0");

        }
        dateOfSavings = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

        btnSearchDB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                recyclerViewTXToday.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                customerDailyReports = dbHelper.getSavingsForBranchAtDate(officeBranch, dateOfSavings);
                branchSavingsCount =dbHelper.getSavingsCountForBranchAtDate(officeBranch, dateOfSavings);
                savingsTotal =dbHelper.getTotalSavingsForBranchAtDate(officeBranch, dateOfSavings);
                LinearLayoutManager layoutManagerC
                        = new LinearLayoutManager(TransAmadiSavings.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManagerC);

                savingsAdapter = new SavingsAdapter(TransAmadiSavings.this, customerDailyReportArrayList);
                recyclerView.setAdapter(savingsAdapter);
                DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerView.getContext(),
                        layoutManagerC.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration7);
                snapHelper.attachToRecyclerView(recyclerView);

                if(branchSavingsCount >0){
                    txtSavingsCount4theDay.setText(MessageFormat.format("Savings:{0}", branchSavingsCount));

                }else if(branchSavingsCount ==0){
                    txtSavingsCount4theDay.setText("Savings:0");

                }
                if(savingsTotal >0){
                    txtSavingsTotal4theDay.setText("Savings Total:"+ savingsTotal);

                }else if(savingsTotal ==0){
                    txtSavingsTotal4theDay.setText("Savings Total:N0");

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
        dateOfSavings = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

    }

    @Override
    public void onItemClick(CustomerDailyReport customerDailyReport) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("CustomerDailyReport", customerDailyReport);
        Intent intent = new Intent(this, UpdateSavingsAct.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}