package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.Gson;
import com.mig35.carousellayoutmanager.CarouselLayoutManager;
import com.mig35.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.mig35.carousellayoutmanager.CenterScrollListener;
import com.skylightapp.Adapters.TellerCountAdat;
import com.skylightapp.Classes.ChartData;
import com.skylightapp.Classes.MyTouchListener;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TellerCountData;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TellerCountAct extends AppCompatActivity {
    private DBHelper dbHelper;
    private static final String PREF_NAME = "skylight";
    private Bundle tellerBundle;
    private int customerID;
    private  int adminID,tellerReportID;
    private Profile tellerProfile;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private ArrayList<TellerCountData> tellerCountDataCus;
    private ArrayList<TellerCountData> tellerCountDataSavings;
    private ArrayList<TellerCountData> tellerCountDataItems;
    private ArrayList<TellerCountData> tellerCountDataPromos;
    private ArrayList<TellerCountData> tellerCountDataInvestment;
    private ArrayList<TellerCountData> tellerCountDataPayments;
    private ArrayList<TellerCountData> tellerCountTReports;
    private TellerCountAdat tellerCountAdatReports,tellerCountAdatCus,tellerCountAdatPayment,tellerCountAdatSavings,tellerCountAdatItems,tellerCountAdatPromo,tellerCountAdatInv;
    private String json,json1,dateOfStatement, todayDate,selectedDay;
    private int mYear = 2021;
    private int mMonth = 5;
    private int mDay = 30;
    Calendar calendar;
    private int tellerProfileID;
    private Profile userProfile;
    Description descriptionSavings,descriptionCus;

    private String monthYearStr,monthYearStrFinal;
    static final int DATE_DIALOG_ID = 2;
    AppCompatTextView dateText,txtCusC,textTReport,txtSavings,textItems,texInv,txtPromo,txtPayment;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    AppCompatButton btnSelect;
    private ArrayList<BarEntry> valueSetCus;
    private ArrayList<BarEntry> valueSetSavings;
    private BarChart cusChart,savingCharts;
    BarDataSet packageBarDataSet;
    ArrayList<ChartData> investmentArrayList;
    ArrayList<ChartData> promoArrayList11;
    ArrayList<ChartData> itemPurchaseArrayList;
    ArrayList<ChartData> savingsArrayList;
    ArrayList<ChartData> packageArrayList;
    ArrayList<ChartData> customersArrayList;
    ArrayList<ChartData> payoutArrayList;
    ArrayList<ChartData> transactionArrayList;
    private ArrayList<BarEntry> valueSetItems;
    private ArrayList<BarEntry> valueSetInvestment;
    private ArrayList<BarEntry> valueSetPromo;
    private ArrayList<BarEntry> valueSetTransaction;
    private ArrayList<BarEntry> valueSetPayment;
    private ArrayList<BarEntry> valueSetCustomers;
    private ArrayList<BarEntry> valueSetPackage;
    SQLiteDatabase sqLiteDatabase;
    private BarChart payoutChart,customersCharts,transXBarChart,savingsBarChart,paymentChart,packageChart,invBarChart,promoBarChart,itemsBarchart;
    private ChartData chartDataPackage,chartDataSavings,chartDataPromo,chartDataItems,chartDataTransaction,chartDataInvestment,chartDataCustomers,chartDataPayment;
    SupportSQLiteDatabase supportSQLiteDatabase;
    RecyclerView recyclerViewCusC,recyclerPayment,recyclerInv,recyclerItems,recyclerPromo,recyclerReports,recyclerSavings;
    String SharedPrefUserPassword,stringDate,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_teller_cus_count);
        dbHelper=new DBHelper(this);
        tellerBundle=new Bundle();
        tellerProfile=new Profile();
        descriptionSavings = new Description();
        descriptionCus= new Description();
        valueSetCus = new ArrayList<BarEntry>();
        valueSetSavings = new ArrayList<BarEntry>();
        chartDataPackage= new ChartData();
        chartDataSavings= new ChartData();
        chartDataPromo= new ChartData();
        chartDataItems= new ChartData();
        chartDataTransaction= new ChartData();
        chartDataInvestment= new ChartData();
        chartDataPayment= new ChartData();
        chartDataCustomers= new ChartData();
        valueSetItems = new ArrayList<BarEntry>();
        valueSetCustomers = new ArrayList<BarEntry>();
        valueSetInvestment = new ArrayList<BarEntry>();
        valueSetPackage = new ArrayList<BarEntry>();
        valueSetPayment = new ArrayList<BarEntry>();
        valueSetTransaction = new ArrayList<BarEntry>();
        valueSetPromo = new ArrayList<BarEntry>();
        valueSetSavings = new ArrayList<BarEntry>();
        cusChart = (BarChart) findViewById(R.id.cBarChart);
        savingCharts = (BarChart) findViewById(R.id.sBarChart);
        calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        recyclerViewCusC = (RecyclerView) findViewById(R.id.recyclerTCusCount);
        recyclerPayment = findViewById(R.id.recyclerPaymentTeller);
        recyclerSavings = findViewById(R.id.recyclerSavTeller);
        recyclerReports = findViewById(R.id.recyclerTeReports);
        recyclerPromo = findViewById(R.id.recyclerTPromo);
        recyclerInv = findViewById(R.id.recyclerTInv);
        recyclerItems = findViewById(R.id.recyclerItem);
        dateText = findViewById(R.id.dateSelect);

        dateText = findViewById(R.id.dateSelect);
        txtCusC = findViewById(R.id.cBarCus);
        txtSavings = findViewById(R.id.sBarCSavings);
        txtPromo = findViewById(R.id.cBarPromo);
        textTReport = findViewById(R.id.txtTelReports);
        textItems = findViewById(R.id.sBarItems);
        txtPayment = findViewById(R.id.txtTellsPay);
        texInv = findViewById(R.id.txtCountInv);


        btnSelect = findViewById(R.id.buttonDSelect);
        btnSelect.setOnClickListener(this::getMAndY);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        tellerCountDataPayments= new ArrayList<>();
        tellerCountDataInvestment= new ArrayList<>();
        tellerCountDataPromos= new ArrayList<>();
        tellerCountDataItems= new ArrayList<>();
        tellerCountDataSavings= new ArrayList<>();
        tellerCountDataCus= new ArrayList<>();
        tellerCountTReports= new ArrayList<>();

        gson = new Gson();
        gson1 = new Gson();
        tellerProfile=new Profile();
        tellerBundle=getIntent().getExtras();
        if(tellerBundle !=null){
            tellerProfile=tellerBundle.getParcelable("Profile");
        }
        if(tellerProfile !=null){
            tellerProfileID=tellerProfile.getPID();
        }

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            //dbHelper = new DBHelper(this);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            tellerCountDataCus=dbHelper.getTellerMonthCuSs(tellerProfileID);
            tellerCountDataInvestment=dbHelper.getTellerMonthInvs(tellerProfileID);
            tellerCountDataItems=dbHelper.getTellerMonthItems(tellerProfileID);
            tellerCountDataPayments=dbHelper.getTellerMonthPayment(tellerProfileID);
            tellerCountDataSavings=dbHelper.getTellerMonthSavings(tellerProfileID);
            tellerCountDataPromos=dbHelper.getTellerMonthPromo(tellerProfileID);
            tellerCountTReports=dbHelper.getTellerMonthTReport(tellerProfileID);


        }

        descriptionCus.setText("Teller Customer Chart");
        descriptionSavings.setText("Teller Savings Chart");


        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        todayDate = mdformat.format(calendar.getTime());
        String[] items2 = todayDate.split("-");
        String year1 = items2[0];
        String month1 = items2[1];
        monthYearStr = year1 + "-" + month1;

        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getString("PROFILE_ID", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        dateText = findViewById(R.id.date_text_);
        dateText.setOnClickListener(this::datePicker);

        dateText.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);

            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDate();
            }

        };
        stringDate = mDay+"-"+ mMonth+"-"+mYear;

        monthYearStrFinal = mYear + "-" + mMonth;

        if(stringDate.isEmpty()){
            stringDate=todayDate;
        }
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        populateBarChartCustomer(customersArrayList,cusChart);
        populateBarChartSavings(savingsArrayList,savingCharts);


        LinearLayoutManager layoutManagerInv
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerInv.setLayoutManager(layoutManagerInv);
        tellerCountAdatInv = new TellerCountAdat(TellerCountAct.this, tellerCountDataInvestment);
        recyclerInv.setAdapter(tellerCountAdatInv);
        recyclerInv.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationTToday = new DividerItemDecoration(recyclerInv.getContext(),
                layoutManagerInv.getOrientation());
        recyclerInv.addItemDecoration(dividerItemDecorationTToday);

        try {

            if(tellerCountDataInvestment.size()==0){
                texInv.setText("Sorry no Investment yet!");

            }
            if(tellerCountDataInvestment.size()>0){
                texInv.setText("Investments Count: tellerCountDataInvestment");


            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        LinearLayoutManager layoutManagerItems
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerItems.setLayoutManager(layoutManagerItems);
        tellerCountAdatItems = new TellerCountAdat(TellerCountAct.this, tellerCountDataItems);
        recyclerItems.setAdapter(tellerCountAdatItems);
        recyclerItems.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationItems = new DividerItemDecoration(recyclerItems.getContext(),
                layoutManagerItems.getOrientation());
        recyclerItems.addItemDecoration(dividerItemDecorationItems);

        try {

            if(tellerCountDataItems.size()==0){
                txtSavings.setText("Sorry no Items yet!");

            }
            if(tellerCountDataItems.size()>0){
                txtSavings.setText("Items Count: tellerCountDataItems");


            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        LinearLayoutManager layoutManagerSavings
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerSavings.setLayoutManager(layoutManagerSavings);
        tellerCountAdatSavings = new TellerCountAdat(TellerCountAct.this, tellerCountDataSavings);
        recyclerSavings.setAdapter(tellerCountAdatSavings);
        recyclerSavings.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationSavings = new DividerItemDecoration(recyclerSavings.getContext(),
                layoutManagerSavings.getOrientation());
        recyclerSavings.addItemDecoration(dividerItemDecorationSavings);

        try {

            if(tellerCountDataSavings.size()==0){
                txtSavings.setText("Sorry no Savings yet!");

            }
            if(tellerCountDataSavings.size()>0){
                txtSavings.setText("Savings Count: tellerCountDataSavings");


            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        LinearLayoutManager layoutManagerPayment
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerPayment.setLayoutManager(layoutManagerPayment);
        tellerCountAdatPayment = new TellerCountAdat(TellerCountAct.this, tellerCountDataPayments);
        recyclerPayment.setAdapter(tellerCountAdatPayment);
        recyclerPayment.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationPayment = new DividerItemDecoration(recyclerPayment.getContext(),
                layoutManagerPayment.getOrientation());
        recyclerPayment.addItemDecoration(dividerItemDecorationPayment);
        try {

            if(tellerCountDataPayments.size()==0){
                txtPayment.setText("Sorry no Payments yet!");

            }
            if(tellerCountDataPayments.size()>0){
                txtPayment.setText("Payment Count: tellerCountDataPayments");


            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }




        LinearLayoutManager layoutManagerReport
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerReports.setLayoutManager(layoutManagerReport);
        tellerCountAdatReports = new TellerCountAdat(TellerCountAct.this, tellerCountTReports);
        recyclerReports.setAdapter(tellerCountAdatReports);
        recyclerReports.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationReport = new DividerItemDecoration(recyclerReports.getContext(),
                layoutManagerSavings.getOrientation());
        recyclerReports.addItemDecoration(dividerItemDecorationReport);

        try {

            if(tellerCountTReports.size()==0){
                textTReport.setText("Sorry no Report yet!");

            }
            if(tellerCountTReports.size()>0){
                textTReport.setText("Reports Count: tellerCountTReports");


            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }





        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        tellerCountAdatCus = new TellerCountAdat(this, tellerCountDataCus);
        recyclerViewCusC.setAdapter(tellerCountAdatCus);
        recyclerViewCusC.setLayoutManager(layoutManager);
        recyclerViewCusC.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration9 = new DividerItemDecoration(recyclerViewCusC.getContext(),
                layoutManager.getOrientation());
        recyclerViewCusC.addItemDecoration(dividerItemDecoration9);
        recyclerViewCusC.addOnScrollListener(new CenterScrollListener());
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        try {

            if(tellerCountDataCus.size()==0){
                txtCusC.setText("Sorry no Customers yet!");

            }
            if(tellerCountDataCus.size()>0){
                txtCusC.setText("Customer Count: tellerCountDataCus");


            }
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        recyclerViewCusC.addOnItemTouchListener(new MyTouchListener(this,
                recyclerViewCusC,
                new MyTouchListener.OnTouchActionListener() {
                    @Override
                    public void onLeftSwipe(View view, int position) {//code as per your need
                    }

                    @Override
                    public void onRightSwipe(View view, int position) {//code as per your need
                    }

                    @Override
                    public void onClick(View view, int position) {//code as per your need
                    }
                }));


    }
    private void populateBarChartCustomer(ArrayList<ChartData> customersArrayList,  BarChart cusChart) {

        BarData data = new BarData((IBarDataSet) getCusXAxisValues(customersArrayList), (IBarDataSet) getCusYAxisData(customersArrayList));
        cusChart.setData(data);
        cusChart.setDescription(descriptionCus);
        cusChart.animateXY(150, 150);
        cusChart.invalidate();

    }
    private ArrayList<BarEntry> getCusYAxisData(ArrayList<ChartData> customersArrayList) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        double packageTotal=0.00;

        for (int i = 0; i < customersArrayList.size(); i++) {
            ChartData chartDataX = customersArrayList.get(i);
            yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

        }
        for (int i = 0; i < customersArrayList.size(); i++) {
            ChartData chartDataX = customersArrayList.get(i);
            Double a12 = chartDataX.getValue();
            yVals.add(a12);

        }

        return yVal333;
    }
    private ArrayList<String> getCusXAxisValues(ArrayList<ChartData> customersArrayList) {
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < customersArrayList.size(); i++) {
            chartDataCustomers = customersArrayList.get(i);
            String monthYear = chartDataCustomers.getData();
            xVals.add(monthYear);

        }

        return xVals;
    }

    private void populateBarChartSavings(ArrayList<ChartData> savingsArrayList, BarChart savingCharts) {

        BarData data = new BarData((IBarDataSet) getSavingsXAxisValues(savingsArrayList), (IBarDataSet) getSavingsYAxisData(savingsArrayList));
        savingCharts.setData(data);
        savingCharts.setDescription(descriptionSavings);
        savingCharts.animateXY(150, 150);
        savingCharts.invalidate();

    }
    private ArrayList<BarEntry> getSavingsYAxisData(ArrayList<ChartData> savingsArrayList) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        double packageTotal=0.00;

        for (int i = 0; i < savingsArrayList.size(); i++) {
            ChartData chartDataX = savingsArrayList.get(i);
            yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

        }
        for (int i = 0; i < savingsArrayList.size(); i++) {
            ChartData chartDataX = savingsArrayList.get(i);
            Double a12 = chartDataX.getValue();
            yVals.add(a12);

        }

        return yVal333;
    }
    private ArrayList<String> getSavingsXAxisValues(ArrayList<ChartData> savingsArrayList) {
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < savingsArrayList.size(); i++) {
            chartDataSavings = savingsArrayList.get(i);
            String monthYear = chartDataSavings.getData();
            xVals.add(monthYear);

        }

        return xVals;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:

                DatePickerDialog datePickerDialog = this.customDatePicker();
                return datePickerDialog;
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    protected void updateDate() {
        int localMonth = (mMonth + 1);
        String monthString = localMonth < 10 ? "0" + localMonth : Integer
                .toString(localMonth);
        String localYear = Integer.toString(mYear).substring(2);
        dateText.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(monthString).append("-").append(localYear).append(" "));
        showDialog(DATE_DIALOG_ID);
    }

    private DatePickerDialog customDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListener,
                mYear, mMonth, mDay);
        try {
            Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField
                            .get(dpd);
                    Field datePickerFields[] = datePickerDialogField.getType()
                            .getDeclaredFields();
                    for (Field datePickerField : datePickerFields) {
                        if ("mDayPicker".equals(datePickerField.getName())
                                || "mDaySpinner".equals(datePickerField
                                .getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dpd;
    }
    public void datePicker(View view) {
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH+1);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog( TellerCountAct.this, R.style.DatePickerDialogStyle,mDateSetListener,mYear,mMonth,mDay);
        dialog.show();
        stringDate = mYear+"-"+ mMonth+"-"+mDay;
    }

    public void getMAndY(View view) {
    }
}