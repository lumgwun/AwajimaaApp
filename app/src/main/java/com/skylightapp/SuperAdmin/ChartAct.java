package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.skylightapp.Classes.ChartData;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.Database.TransactionGrantingDAO;
import com.skylightapp.R;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ChartAct extends AppCompatActivity {
    private Bundle bundle;
    private TellerReport tellerReport;
    private DBHelper dbHelper;
    private int customerID;
    private  int adminID,tellerReportID;
    private Profile userProfile;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1,dateOfStatement, selectedDay,stringType;
    private Customer customer,customer1;
    private static final String PREF_NAME = "skylight";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Description descriptionTx,descriptionCus,descriptionPacks,descriptionPayment,descriptionPromo,descriptionItems,descriptionInv,descriptionSavings;
    ArrayList<BarEntry> yVals ;
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
    private ArrayList<BarEntry> valueSetSavings;

    protected DatePickerDialog datePickerDialog;
    DatePicker picker;
    SimpleDateFormat sdf;
    static final int DATE_DIALOG_ID = 1;
    private int mYear = 2013;
    private int mMonth = 5;
    private int mDay = 30;
    private BarData data;
    private AppCompatTextView txtPickADate;
    private AppCompatButton btnDate;
    DatePickerDialog.OnDateSetListener mDateSetListner;
    ArrayList<BarDataSet> barDataSet = null;
    SQLiteDatabase sqLiteDatabase;
    BarDataSet packageBarDataSet;
    private BarChart payoutChart,customersCharts,transXBarChart,savingsBarChart,paymentChart,packageChart,invBarChart,promoBarChart,itemsBarchart;
    private  ChartData chartDataPackage,chartDataSavings,chartDataPromo,chartDataItems,chartDataTransaction,chartDataInvestment,chartDataCustomers,chartDataPayment;
    String SharedPrefUserPassword, stringNoOfSavings, todayYearMonth,dateOfChart,todayDate,officeBranch,dateOfReport, cmFirstName,cmLastName,cmName,SharedPrefUserMachine,phoneNo,SharedPrefUserName,SharedPrefProfileID,adminName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_chart);
        setTitle("Awajima Multi-Chart Analysis");
        checkInternetConnection();
        bundle= new Bundle();
        stringType=null;
        descriptionTx= new Description();
        descriptionCus= new Description();
        descriptionSavings= new Description();
        descriptionPacks= new Description();
        descriptionPromo= new Description();
        descriptionItems= new Description();
        descriptionInv= new Description();
        descriptionPayment= new Description();

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
        txtPickADate = findViewById(R.id.date_chart_);
        btnDate = findViewById(R.id.buttonRefreshDB);
        btnDate.setOnClickListener(this::refreshDate);
        saveNewPackage();
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshDBase();

            }
        });
        txtPickADate.setOnClickListener(this::getMonthAndYear);
        txtPickADate.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        mDateSetListner = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDate();
            }
        };
        dateOfChart=mYear+"-"+mMonth;

        final Calendar c = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = calendar.getTime();
        todayDate = sdf.format(newDate);
        todayYearMonth=mYear+"-"+mMonth+1;

        if(dateOfChart.isEmpty()){
            dateOfChart=todayYearMonth;
        }


        promoArrayList11 = new ArrayList<ChartData>();
        itemPurchaseArrayList = new ArrayList<ChartData>();
        savingsArrayList = new ArrayList<ChartData>();
        packageArrayList = new ArrayList<ChartData>();
        customersArrayList = new ArrayList<ChartData>();
        payoutArrayList = new ArrayList<ChartData>();
        transactionArrayList = new ArrayList<ChartData>();
        investmentArrayList = new ArrayList<ChartData>();


        dbHelper=new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        descriptionTx = new Description();

        gson1 = new Gson();
        userProfile=new Profile();
        customer = new Customer();
        CusDAO cusDAO = new CusDAO(this);
        TranXDAO tranXDAO = new TranXDAO(this);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            investmentArrayList =dbHelper.getChartInvestmentAll("Investment",dateOfChart);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            promoArrayList11 =dbHelper.getChartPromoValue("Promo",dateOfChart);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            itemPurchaseArrayList=dbHelper.getChartItemsValue("ItemsPurchase",dateOfChart);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            savingsArrayList =dbHelper.getChartSavingsAll("Savings",dateOfChart);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            packageArrayList =dbHelper.getPackageChart(dateOfChart);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            customersArrayList=cusDAO.getChartCustomers(dateOfChart);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            transactionArrayList=tranXDAO.getTranxChartAll(dateOfChart);
        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getReadableDatabase();
            payoutArrayList=dbHelper.getChartPaymentAll(dateOfChart);
        }

        payoutChart = findViewById(R.id.chartPayout);
        customersCharts = findViewById(R.id.cusCharts);
        transXBarChart = findViewById(R.id.transactionChart);
        paymentChart = findViewById(R.id.paymentCharts);
        packageChart = (BarChart) findViewById(R.id.packsBarChart);
        invBarChart = (BarChart) findViewById(R.id.ourBarChartInv);
        promoBarChart = findViewById(R.id.ourPieChartPromo);
        itemsBarchart = (BarChart) findViewById(R.id.itemsBarChart);
        savingsBarChart = findViewById(R.id.savingsBarChart);
        descriptionTx.setText("Awajima Tranx Chart");
        descriptionCus.setText("Awajima Customer Chart");
        descriptionSavings.setText("Awajima Savings Chart");
        descriptionPacks.setText("Awajima Packages Chart");
        descriptionPromo.setText("Awajima Promo Chart");
        descriptionItems.setText("Awajima Items Chart");
        descriptionInv.setText("Awajima Investment Chart");
        descriptionPayment.setText("Awajima Payout Chart");


        populateBarCartInv(investmentArrayList,chartDataInvestment,valueSetInvestment,invBarChart);
        populateBarCartPromo(promoArrayList11,chartDataPromo,valueSetPromo,promoBarChart);
        populateBarCartItem(itemPurchaseArrayList,chartDataItems,valueSetItems,itemsBarchart);
        populateBarCartSavings(savingsArrayList,chartDataSavings,valueSetSavings,savingsBarChart);
        populateBarCartPackage(packageArrayList,chartDataPackage,valueSetPackage,payoutChart);
        populateBarCartCustomer(customersArrayList,chartDataCustomers,valueSetCustomers);
        populateBarCartTransaction(transactionArrayList,chartDataTransaction,valueSetTransaction);
        populateBarCartPayment(payoutArrayList,chartDataPayment,valueSetPayment,payoutChart);


    }





    private void populateBarCartPackage(ArrayList<ChartData> packageArrayList, ChartData chartDataPackage, ArrayList<BarEntry> valueSetPackage, BarChart packageChart) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        double packageTotal=0.00;
        float start = 1f;

        for (int i = 0; i < packageArrayList.size(); i++) {
            ChartData chartDataX = packageArrayList.get(i);
            //float val = (float) (Math.random() * (60 + 1));
            if (Math.random() * 100 < 25) {
                yVal333.add(new BarEntry(i, (float) chartDataX.getValue(), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                yVal333.add(new BarEntry(i, (float) chartDataX.getValue()));
                //yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

            }


        }
        /*BarData data=packageChart.getBarData();
        IBarDataSet set = data.getDataSetByIndex(high.getDataSetIndex());*/
        BarData data=null;
        try {

            data = new BarData((IBarDataSet) getPackageXAxisValues(packageArrayList), (IBarDataSet) getPackageYAxisData(packageArrayList));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }


        BarDataSet barDataSet;
        if (packageChart.getData() != null &&
                packageChart.getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) packageChart.getData().getDataSetByIndex(0);
            barDataSet.setValues(yVal333);
            packageChart.setDescription(descriptionPacks);
            //packageChart.animateXY(150, 150);
            packageChart.animateXY(2000, 2000);
            packageChart.animateX(2000);
            packageChart.animateY(2000);
            packageChart.invalidate();
            packageChart.getData().notifyDataChanged();
            packageChart.notifyDataSetChanged();
            packageChart.getData().setHighlightEnabled(!packageChart.getData().isHighlightEnabled());
            packageChart.setPinchZoom(true);
            packageChart.setAutoScaleMinMaxEnabled(!packageChart.isAutoScaleMinMaxEnabled());
            packageChart.setData(data);


        } else {
            barDataSet = new BarDataSet(yVal333, "June 2022");

            barDataSet.setDrawIcons(true);

            dataSets.add(barDataSet);


            data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            packageChart.setData(data);
        }


    }
    private ArrayList<IBarDataSet> getPackageYAxisData(ArrayList<ChartData> packageArrayList) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        double packageTotal=0.00;
        float start = 1f;

        for (int i = 0; i < packageArrayList.size(); i++) {
            ChartData chartDataX = packageArrayList.get(i);
            //float val = (float) (Math.random() * (60 + 1));
            if (Math.random() * 100 < 25) {
                yVal333.add(new BarEntry(i, (float) chartDataX.getValue(), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                yVal333.add(new BarEntry(i, (float) chartDataX.getValue()));
                //yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

            }


        }
        BarDataSet barDataSet1 = new BarDataSet(yVal333, "Year and Month");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);


        return dataSets;
    }
    private ArrayList<IBarDataSet> getPackageXAxisValues(ArrayList<ChartData> packageArrayList) {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> xVal = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < packageArrayList.size(); i++) {
            chartDataPackage = packageArrayList.get(i);
            String monthYear = chartDataPackage.getData();
            xVals.add(monthYear);

        }

        for (int i = 0; i < packageArrayList.size(); i++) {
            ChartData chartDataX = packageArrayList.get(i);
            if (Math.random() * 100 < 25) {
                xVal.add(new BarEntry(i, Float.parseFloat(chartDataPackage.getData()), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                xVal.add(new BarEntry(Float.parseFloat(chartDataPackage.getData()), i));


            }


        }

        BarDataSet barDataSet1 = new BarDataSet(xVal, "Total");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }
    /*private ArrayList<String> getPackageXAxisValues(ArrayList<ChartData> packageArrayList) {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < packageArrayList.size(); i++) {
            chartDataPackage = packageArrayList.get(i);
            String monthYear = chartDataPackage.getData();
            xVals.add(monthYear);

        }

        return xVals;
    }*/

    private void populateBarCartTransaction(ArrayList<ChartData> transactionArrayList, ChartData chartDataTransaction, ArrayList<BarEntry> valueSetTransaction) {
        try {

            data = new BarData((IBarDataSet) getTranxXAxisValues(transactionArrayList), (IBarDataSet) getTranxYAxisValues(transactionArrayList));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        transXBarChart.setData(data);
        transXBarChart.setDescription(descriptionTx);
        transXBarChart.animateXY(150, 150);
        transXBarChart.invalidate();

    }
    private ArrayList<IBarDataSet> getTranxXAxisValues(ArrayList<ChartData> transactionArrayList) {
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<BarEntry> xVal = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();


        for (int i = 0; i < transactionArrayList.size(); i++) {
            chartDataTransaction = transactionArrayList.get(i);
            String monthYear = chartDataTransaction.getData();
            xVals.add(monthYear);

        }

        for (int i = 0; i < transactionArrayList.size(); i++) {
            chartDataTransaction = transactionArrayList.get(i);
            if (Math.random() * 100 < 25) {
                xVal.add(new BarEntry(i, Float.parseFloat(chartDataTransaction.getData()), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                xVal.add(new BarEntry(Float.parseFloat(chartDataTransaction.getData()), i));


            }


        }
        BarDataSet barDataSet1 = new BarDataSet(xVal, "Total");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<BarEntry> getTranxYAxisValues(ArrayList<ChartData> transactionArrayList) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        double packageTotal=0.00;
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < transactionArrayList.size(); i++) {
            ChartData chartDataX = transactionArrayList.get(i);
            yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

        }
        for (int i = 0; i < transactionArrayList.size(); i++) {
            ChartData chartDataX = transactionArrayList.get(i);
            Double a12 = chartDataX.getValue();
            yVals.add(a12);

        }

        return yVal333;
    }

    private void populateBarCartPayment(ArrayList<ChartData> payoutArrayList, ChartData chartDataPayment, ArrayList<BarEntry> valueSetPayment, BarChart payoutChart) {

        try {

            data = new BarData((IBarDataSet) getPaymentXAxisValues(payoutArrayList), (IBarDataSet) getPaymentYAxisValues(payoutArrayList));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        payoutChart.setData(data);
        payoutChart.setDescription(descriptionPayment);
        payoutChart.animateXY(150, 150);
        payoutChart.invalidate();

    }
    private ArrayList<IBarDataSet> getPaymentXAxisValues(ArrayList<ChartData> payoutArrayList) {

        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> xVal = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();


        for (int i = 0; i < payoutArrayList.size(); i++) {
            chartDataPayment = payoutArrayList.get(i);
            if (Math.random() * 100 < 25) {
                xVal.add(new BarEntry(i, Float.parseFloat(chartDataPayment.getData()), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                xVal.add(new BarEntry(Float.parseFloat(chartDataPayment.getData()), i));


            }


        }
        BarDataSet barDataSet1 = new BarDataSet(xVal, "Total");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<BarEntry> getPaymentYAxisValues(ArrayList<ChartData> payoutArrayList) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        double packageTotal=0.00;
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < payoutArrayList.size(); i++) {
            ChartData chartDataX = payoutArrayList.get(i);
            yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

        }
        for (int i = 0; i < payoutArrayList.size(); i++) {
            ChartData chartDataX = payoutArrayList.get(i);
            Double a12 = chartDataX.getValue();
            yVals.add(a12);

        }

        return yVal333;
    }


    private void populateBarCartCustomer(ArrayList<ChartData> customersArrayList, ChartData chartDataCustomers, ArrayList<BarEntry> valueSetCustomers) {

        try {

            data = new BarData((IBarDataSet) getCusXAxisValues(customersArrayList), (IBarDataSet) getCusYAxisValues(customersArrayList));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }


        customersCharts.setData(data);
        customersCharts.setDescription(descriptionCus);
        customersCharts.animateXY(150, 150);
        customersCharts.invalidate();

    }
    private ArrayList<IBarDataSet> getCusXAxisValues(ArrayList<ChartData> customersArrayList) {

        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> xVal = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();


        for (int i = 0; i < customersArrayList.size(); i++) {
            chartDataCustomers = customersArrayList.get(i);
            if (Math.random() * 100 < 25) {
                xVal.add(new BarEntry(i, Float.parseFloat(chartDataCustomers.getData()), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                xVal.add(new BarEntry(Float.parseFloat(chartDataCustomers.getData()), i));


            }


        }
        BarDataSet barDataSet1 = new BarDataSet(xVal, "Total");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<BarEntry> getCusYAxisValues(ArrayList<ChartData> customersArrayList) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        double packageTotal=0.00;
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < customersArrayList.size(); i++) {
            ChartData chartDataX = customersArrayList.get(i);
            yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(customersArrayList.get(i).getValue())), i));

        }
        for (int i = 0; i < customersArrayList.size(); i++) {
            ChartData chartDataX = customersArrayList.get(i);
            Double a12 = chartDataX.getValue();
            yVals.add(a12);

        }

        return yVal333;
    }





    private void populateBarCartItem(ArrayList<ChartData> itemPurchaseArrayList, ChartData chartDataItems, ArrayList<BarEntry> valueSetItems, BarChart itemsBarchart) {
        try {

            data = new BarData((IBarDataSet) getItemsXAxisValues(itemPurchaseArrayList), (IBarDataSet) getItemsYAxisValues(itemPurchaseArrayList));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        itemsBarchart.setData(data);
        itemsBarchart.setDescription(descriptionItems);
        itemsBarchart.animateXY(150, 150);
        itemsBarchart.invalidate();

    }

    private ArrayList<IBarDataSet> getItemsXAxisValues(ArrayList<ChartData> itemPurchaseArrayList) {

        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> xVal = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();


        for (int i = 0; i < itemPurchaseArrayList.size(); i++) {
            chartDataItems = itemPurchaseArrayList.get(i);
            if (Math.random() * 100 < 25) {
                xVal.add(new BarEntry(i, Float.parseFloat(chartDataItems.getData()), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                xVal.add(new BarEntry(Float.parseFloat(chartDataItems.getData()), i));


            }


        }
        BarDataSet barDataSet1 = new BarDataSet(xVal, "Total");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }


    private ArrayList<BarEntry> getItemsYAxisValues(ArrayList<ChartData> itemPurchaseArrayList) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        double packageTotal=0.00;

        for (int i = 0; i < itemPurchaseArrayList.size(); i++) {
            ChartData chartDataX = itemPurchaseArrayList.get(i);
            yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

        }
        for (int i = 0; i < itemPurchaseArrayList.size(); i++) {
            ChartData chartDataX = itemPurchaseArrayList.get(i);
            Double a12 = chartDataX.getValue();
            yVals.add(a12);

        }

        return yVal333;
    }


    private void populateBarCartSavings(ArrayList<ChartData> savingsArrayList, ChartData chartDataSavings, ArrayList<BarEntry> valueSetSavings, BarChart savingsBarChart) {

        try {

            data = new BarData((IBarDataSet) getSavingsXAxisValues(savingsArrayList), (IBarDataSet) getSavingsYAxisValues(savingsArrayList));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        savingsBarChart.setData(data);
        savingsBarChart.setDescription(descriptionSavings);
        savingsBarChart.animateXY(150, 150);
        savingsBarChart.invalidate();

    }
    private ArrayList<IBarDataSet> getSavingsXAxisValues(ArrayList<ChartData> savingsArrayList) {

        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> xVal = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();


        for (int i = 0; i < savingsArrayList.size(); i++) {
            chartDataSavings = savingsArrayList.get(i);
            if (Math.random() * 100 < 25) {
                xVal.add(new BarEntry(i, Float.parseFloat(chartDataSavings.getData()), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                xVal.add(new BarEntry(Float.parseFloat(chartDataSavings.getData()), i));


            }


        }
        BarDataSet barDataSet1 = new BarDataSet(xVal, "Total");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }


    private ArrayList<BarEntry> getSavingsYAxisValues(ArrayList<ChartData> savingsArrayList) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
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


    private void populateBarCartPromo(ArrayList<ChartData> promoArrayList11, ChartData chartDataPromo, ArrayList<BarEntry> valueSetPromo, BarChart promoBarChart) {


        try {

            data = new BarData((IBarDataSet) getPromoXAxisValues(promoArrayList11), (IBarDataSet) getPromoYAxisValues(promoArrayList11));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        promoBarChart.setData(data);
        promoBarChart.setDescription(descriptionPromo);
        promoBarChart.animateXY(150, 150);
        promoBarChart.invalidate();

    }
    private ArrayList<IBarDataSet> getPromoXAxisValues(ArrayList<ChartData> promoArrayList11) {

        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> xVal = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();


        for (int i = 0; i < promoArrayList11.size(); i++) {
            chartDataPromo = promoArrayList11.get(i);
            if (Math.random() * 100 < 25) {
                xVal.add(new BarEntry(i, Float.parseFloat(chartDataPromo.getData()), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                xVal.add(new BarEntry(Float.parseFloat(chartDataPromo.getData()), i));


            }


        }
        BarDataSet barDataSet1 = new BarDataSet(xVal, "Total");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<BarEntry> getPromoYAxisValues(ArrayList<ChartData> promoArrayList11) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        double packageTotal=0.00;

        for (int i = 0; i < promoArrayList11.size(); i++) {
            ChartData chartDataX = promoArrayList11.get(i);
            yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

        }
        for (int i = 0; i < promoArrayList11.size(); i++) {
            ChartData chartDataX = promoArrayList11.get(i);
            Double a12 = chartDataX.getValue();
            yVals.add(a12);

        }

        return yVal333;
    }


    private void populateBarCartInv(ArrayList<ChartData> investmentArrayList, ChartData chartDataInvestment, ArrayList<BarEntry> valueSetInvestment, BarChart invBarChart) {

        try {

            data = new BarData((IBarDataSet) getInvXAxisValues(investmentArrayList), (IBarDataSet) getInvYAxisValues(investmentArrayList));

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        invBarChart.setData(data);
        invBarChart.setDescription(descriptionInv);
        invBarChart.animateXY(150, 150);
        invBarChart.invalidate();

    }
    private ArrayList<IBarDataSet> getInvXAxisValues(ArrayList<ChartData> investmentArrayList) {

        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> xVal = new ArrayList<BarEntry>();
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();


        for (int i = 0; i < investmentArrayList.size(); i++) {
            chartDataInvestment = investmentArrayList.get(i);
            if (Math.random() * 100 < 25) {
                xVal.add(new BarEntry(i, Float.parseFloat(chartDataInvestment.getData()), getResources().getDrawable(R.mipmap.star_round)));
            } else {
                xVal.add(new BarEntry(Float.parseFloat(chartDataInvestment.getData()), i));


            }


        }
        ArrayList<Double> yVals = new ArrayList<Double>();
        double packageTotal=0.00;

        for (int i = 0; i < investmentArrayList.size(); i++) {
            ChartData chartDataX = investmentArrayList.get(i);
            yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

        }
        for (int i = 0; i < investmentArrayList.size(); i++) {
            ChartData chartDataX = investmentArrayList.get(i);
            Double a12 = chartDataX.getValue();
            yVals.add(a12);

        }

        BarDataSet barDataSet1 = new BarDataSet(xVal, "Total");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets.add(barDataSet1);

        //BarData d = new BarData(dataSets);

        return dataSets;
    }

    private ArrayList<BarEntry> getInvYAxisValues(ArrayList<ChartData> investmentArrayList) {
        ArrayList<Double> yVals = new ArrayList<Double>();
        ArrayList<BarEntry> yVal333 = new ArrayList<BarEntry>();
        double packageTotal=0.00;

        for (int i = 0; i < investmentArrayList.size(); i++) {
            ChartData chartDataX = investmentArrayList.get(i);
            yVal333.add(new BarEntry(Float.parseFloat(String.valueOf(chartDataX.getValue())), i));

        }
        for (int i = 0; i < investmentArrayList.size(); i++) {
            ChartData chartDataX = investmentArrayList.get(i);
            Double a12 = chartDataX.getValue();
            yVals.add(a12);

        }

        return yVal333;
    }


    private void saveNewPackage() {
        dbHelper= new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {

            dbHelper.insertNewPackage(1000,1234,9234,4567,"Test Package","Savings",31,5000,"2022-17-06",5000,"","Confirmed");

        }

    }

    private void refreshDBase() {

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
        String localYear = Integer.toString(mYear);
        //String localYear = Integer.toString(mYear).substring(2);
        txtPickADate.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(monthString).append("-").append(localYear).append(" "));
        dateOfChart=localYear+"-"+monthString;
        showDialog(DATE_DIALOG_ID);
    }

    private DatePickerDialog customDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListner,
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
        }
        return dpd;
    }



    public void getMonthAndYear(View view) {
        showDialog(DATE_DIALOG_ID);
    }

    public void refreshDate(View view) {
    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean checkInternetConnection() {
        boolean hasInternetConnection = hasInternetConnection();
        if (!hasInternetConnection) {
            showWarningDialog("Internet connection failed");
        }

        return hasInternetConnection;
    }

    public void showWarningDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.button_ok, null);
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        packageChart = (BarChart) findViewById(R.id.packsBarChart);

        switch (item.getItemId()) {
            /*case R.id.actionToggleValues: {
                if( packageChart !=null){
                    for (IDataSet set : packageChart.getData().getDataSets())
                        set.setDrawValues(!set.isDrawValuesEnabled());

                    packageChart.invalidate();
                    break;

                }
                break;

            }
            case R.id.actionToggleIcons: {
                if( packageChart !=null){
                    for (IDataSet set : packageChart.getData().getDataSets())
                        set.setDrawIcons(!set.isDrawIconsEnabled());

                    packageChart.invalidate();
                    break;

                }

            }
            case R.id.actionToggleBarBorders: {
                if( packageChart !=null){
                    for (IBarDataSet set : packageChart.getData().getDataSets())
                        ((BarDataSet) set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                    packageChart.invalidate();
                    break;

                }

            }
            case R.id.actionToggleHighlight: {
                if( packageChart !=null){
                    if (packageChart.getData() != null) {
                        packageChart.getData().setHighlightEnabled(!packageChart.getData().isHighlightEnabled());
                        packageChart.invalidate();
                    }
                    break;

                }

            }*/
            case R.id.actionTogglePinch: {
                if( packageChart !=null){
                    if (packageChart.isPinchZoomEnabled())
                        packageChart.setPinchZoom(false);
                    else
                        packageChart.setPinchZoom(true);

                    packageChart.invalidate();
                    break;

                }

            }
            case R.id.actionToggleAutoScaleMinMax: {
                if( packageChart !=null){
                    packageChart.setAutoScaleMinMaxEnabled(!packageChart.isAutoScaleMinMaxEnabled());
                    packageChart.notifyDataSetChanged();
                    break;

                }

            }

            case R.id.animateX: {
                packageChart.animateX(2000);
                break;
            }
            case R.id.animateY: {
                packageChart.animateY(2000);
                break;
            }
            case R.id.animateXY: {
                packageChart.animateXY(2000, 2000);
                break;
            }

        }
        return true;
    }


}