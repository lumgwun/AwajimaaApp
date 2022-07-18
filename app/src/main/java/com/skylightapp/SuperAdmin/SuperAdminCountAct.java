package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Adapters.AccountAdapter2;
import com.skylightapp.Adapters.CustomerAdapter;
import com.skylightapp.Adapters.MySavingsCodeAdapter;
import com.skylightapp.Adapters.PaymentAdapterSuper;
import com.skylightapp.Adapters.ProfileSimpleAdapter;
import com.skylightapp.Adapters.SkyLightPackageAdapter;
import com.skylightapp.Adapters.StandingOrderAdapter;
import com.skylightapp.Adapters.SuperSavingsAdapter;
import com.skylightapp.Adapters.TellerCashAdapter;
import com.skylightapp.Adapters.TellerReportAdapterSuper;
import com.skylightapp.Adapters.TransactionAdapter;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.CusSimpleAdapter;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentCodeAdapter;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.ProfileAdapter;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.R;
import com.skylightapp.Tellers.TellerCash;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SuperAdminCountAct extends AppCompatActivity implements AdapterView.OnItemSelectedListener,SkyLightPackageAdapter.OnItemsClickListener,SuperSavingsAdapter.OnItemsClickListener, MySavingsCodeAdapter.OnItemsClickListener,TransactionAdapter.OnItemsClickListener,StandingOrderAdapter.OnItemsClickListener,AccountAdapter2.OnItemsClickListener{
    TextView txtTotalForTheDay,txtDurationInDays,txtTotalPaymentToday, countManualPaymentToday,txtNewCusToday,totalSavings2Today,txttotalSavingCs,totalSoCountToday,txtNewPackageCountToday, txtNewTXToday,txtTotalSavingsToday,txtAllProfileCount;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    Gson gson;
    CusSimpleAdapter customerArrayAdapter;
    ArrayList<Customer> customerArrayList;
    ArrayAdapter<Profile> profileArrayAdapter;
    ArrayAdapter<Customer> cusArrayAdapter;
    ArrayList<Profile> profileArrayList;
    ArrayList<Profile> profileArrayList2;
    String json,branchName,branchName1,tellerMachine,stringDate,customerMachine,stringTeller,stringCustomer,branchName2,tellerIDString,tellerIDString1,tellerIDString2,customerIDString;
    double totalSavings2Today33;
    int totalSavingsToday,soCount,selectedTellerIndex, cusCountForOffice,selectedCusIndex, countToday,countPackageToday,customerCountToday,customersForTeller;
    private Profile userProfile;
    private Date date;
    private  Profile teller,teller2;
    private int tellerID;
    private long tellerID1;
    private int customerID;
    private long tellerID2;
    private Customer customer;
    LinearLayout cusLayout;
    protected DatePickerDialog datePickerDialog;
    private ProfileSimpleAdapter profileSimpleAdapter;
    private ProfileAdapter profileAdapter;
    private StandingOrderAdapter standingOrderAdapter;
    private TransactionAdapter transactionAdapter;
    private PaymentAdapterSuper paymentAdapter;
    private CustomerAdapter customerAdapter;

    private AccountAdapter2 accountAdapter;
    private TellerReportAdapterSuper tellerReportAdapter;
    //private MySavingsCodeAdapter codeAdapter;
    private PaymentCodeAdapter paymentCodeAdapter;
    private TellerCashAdapter tellerCashAdapter;
    private SuperSavingsAdapter savingsAdapter;
    private SkyLightPackageAdapter packageAdapter;
    private TellerReportAdapterSuper tellerReportAdapterSuper;
    private ArrayList<CustomerDailyReport> customerDailyReports3;
    private ArrayList<CustomerDailyReport> customerDailyReportsAll;
    private ArrayList<Profile> allUsers;
    private ArrayList<Profile> usersToday;
    private CustomerDailyReport customerDailyReport;
    private ArrayList<SkyLightPackage> skyLightPackages;
    private ArrayList<SkyLightPackage> skyLightPackagesToday;
    private SkyLightPackage skyLightPackage;
    private ArrayList<Account> accounts;
    private Account account;
    private ArrayList<Transaction> transactionAll;
    private ArrayList<Transaction> transactionsToday;
    private Transaction transaction;
    private ArrayList<StandingOrder> standingOrders;
    private StandingOrder standingOrder;
    private ArrayList<PaymentCode> paymentCodeArrayList;
    private PaymentCode paymentCode;
    private ArrayList<TellerCash> tellerCashArrayList;
    private ArrayList<Payment> paymentArrayListAll;
    private ArrayList<Payment> paymentArrayListToday;
    private ArrayList<Payment> paymentArrayList;
    private ArrayList<TellerCash> tellerTellerCashAll;
    private ArrayList<TellerCash> tellerTellerCashToday;
    private ArrayList<Payment> manualPaymentTellerAll;
    private ArrayList<Payment> manualPaymentTellerToday;
    private ArrayList<SuperCash> superCashTellerAll;
    private ArrayList<SuperCash> superCashTellerToday;
    private ArrayList<StockTransfer> stocksTellerTxAll;
    private ArrayList<StockTransfer> stocksTellerTxToday;
    private ArrayList<SkyLightPackage> skyLightPackagesTellerToday;
    private ArrayList<SkyLightPackage> skyLightPackagesTellerAll;
    private ArrayList<CustomerDailyReport> savingsAll;
    private ArrayList<CustomerDailyReport> savingsToday;
    private ArrayList<CustomerDailyReport> customerDailyReportsTellerAll;
    private ArrayList<CustomerDailyReport> customerDailyReportsTellerToday;
    private ArrayList<Customer> customersTellerAll;
    private ArrayList<Customer> customersTellerToday;
    private ArrayList<TellerReport> tellerReportsTellerAll;
    private ArrayList<TellerReport> tellerReportsTellerToday;
    private ArrayList<CustomerDailyReport> savingsCusAll;
    private ArrayList<CustomerDailyReport> savingsCusUnconfirmed;
    private ArrayList<Payment> manualPaymentCusToday;
    private ArrayList<Payment> manualPaymentCusAll;
    private ArrayList<Transaction> transactionsCusToday;
    private ArrayList<Transaction> transactionsCusAll;
    private ArrayList<PaymentCode> paymentCodeCusAll;
    private ArrayList<PaymentCode> paymentCodeDate;
    private ArrayList<PaymentCode> paymentCodeForCus;
    private ArrayList<PaymentCode> paymentCodeForCusToday;
    private ArrayList<PaymentCode> paymentCodeForTeller;
    private ArrayList<PaymentDoc> paymentDocCusAll;
    private ArrayList<PaymentCode> paymentDocCusToday;
    private Payment payment;
    private TellerCash tellerCash;
    private ArrayList<SuperCash> superCashArrayList;
    private ArrayList<SuperCash> superCashBranchAll;
    private ArrayList<SuperCash> superCashBranchToday;
    private ArrayList<Payment> manualPaymentBranchAll;
    private ArrayList<Payment> manualPaymentBranchToday;
    private ArrayList<Payment> manualPaymentToday;
    private ArrayList<Payment> manualPaymentTeller;
    private ArrayList<TellerReport> tellerReportsBranchAll;
    private ArrayList<TellerReport> tellerReportsAll;
    private ArrayList<TellerReport> tellerReportsTeller;
    private ArrayList<TellerReport> tellerReportsBranchToday;
    private ArrayList<Customer> customersBranchAll;
    private ArrayList<Customer> customersNewToday;
    private ArrayList<Customer> customersBranchToday;
    private ArrayList<AdminBankDeposit> adminBankDepositsBranchToday;
    private ArrayList<AdminBankDeposit> adminBankDepositsBranchAll;
    private SuperCash superCash;
    private ArrayList<AdminBankDeposit> adminBankDeposits;

    private AdminBankDeposit adminBankDeposit;
    private Spinner spnPaymentBranchT, spnCusDetails, spnTellersDetails;
    private AppCompatTextView dateText;
    private  String todayDate,tellerNames;
    private  TextView txtCustomersforBranch,txtTellerMonthTellerCash,txtCustomerPaymentToday,txtBranchPaymentToday,txtTellerTotalPayment,txtBranchTotalPayment,txtTellerNewCus;
    private AppCompatButton btnGetCusDetails, btnGetTellerDetails, btnGetBranchDetails;
    private double paymentForTellerToday,paymentForCusToday,totalTellerCashForTheMonth,paymentTotalForTeller,paymentForBranchToday,paymentForBranchTotal;
    private int day, month, year;
    private  int totalTellerNewCusForTheMonth;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String PREF_NAME = "skylight";
    static final int DATE_DIALOG_ID = 1;
    private int mYear = 2021;
    private int mMonth = 5;
    private int mDay = 30;
    private Calendar calendar1;
    SQLiteDatabase sqLiteDatabase;
    private String monthYearStr,monthYearStrFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_admin_count);
        setTitle("Selected Day More Details");
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        gson = new Gson();
        customer= new Customer();
        userProfile= new Profile();
        profileArrayList= new ArrayList<>();
        tellerCashArrayList= new ArrayList<>();
        paymentArrayList= new ArrayList<>();
        superCashArrayList= new ArrayList<>();
        tellerCashArrayList= new ArrayList<>();
        adminBankDeposits= new ArrayList<>();
        customerDailyReportsAll= new ArrayList<>();
        customerArrayList= new ArrayList<>();
        standingOrders=new ArrayList<StandingOrder>();
        paymentCodeArrayList=new ArrayList<PaymentCode>();
        transactionAll =new ArrayList<Transaction>();
        savingsToday =new ArrayList<>();
        savingsAll =new ArrayList<>();
        paymentCodeForTeller =new ArrayList<>();
        tellerReportsTeller =new ArrayList<>();
        manualPaymentTeller =new ArrayList<>();
        manualPaymentToday =new ArrayList<>();
        paymentCodeForCus =new ArrayList<>();
        paymentCodeForCusToday =new ArrayList<>();


        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        transactionsToday =new ArrayList<Transaction>();

        skyLightPackages=new ArrayList<SkyLightPackage>();
        skyLightPackagesToday=new ArrayList<>();

        customerDailyReports3=new ArrayList<CustomerDailyReport>();
        accounts=new ArrayList<Account>();
        allUsers=new ArrayList<>();
        usersToday=new ArrayList<>();
        paymentArrayListAll=new ArrayList<>();
        paymentArrayList=new ArrayList<>();
        tellerTellerCashAll=new ArrayList<>();
        tellerTellerCashToday=new ArrayList<>();
        manualPaymentTellerAll=new ArrayList<>();
        manualPaymentTellerToday=new ArrayList<>();
        superCashTellerAll=new ArrayList<>();
        superCashTellerToday=new ArrayList<>();
        stocksTellerTxAll=new ArrayList<>();
        stocksTellerTxToday=new ArrayList<>();
        customersTellerAll=new ArrayList<>();
        customersTellerToday=new ArrayList<>();
        customerDailyReportsTellerToday=new ArrayList<>();
        skyLightPackagesTellerToday=new ArrayList<>();
        skyLightPackagesTellerAll=new ArrayList<>();
        customerDailyReportsTellerAll=new ArrayList<>();
        tellerReportsTellerAll=new ArrayList<>();
        tellerReportsTellerToday=new ArrayList<>();
        savingsCusAll=new ArrayList<>();
        paymentCodeCusAll=new ArrayList<>();
        paymentCodeDate =new ArrayList<>();
        savingsCusUnconfirmed=new ArrayList<>();
        paymentDocCusAll=new ArrayList<>();
        paymentDocCusToday=new ArrayList<>();
        manualPaymentCusToday=new ArrayList<>();
        manualPaymentCusAll=new ArrayList<>();
        transactionsCusToday=new ArrayList<>();
        transactionsCusAll=new ArrayList<>();
        transactionAll=new ArrayList<>();
        transactionsToday=new ArrayList<>();



        superCashArrayList=new ArrayList<>();
        adminBankDepositsBranchAll=new ArrayList<>();
        adminBankDepositsBranchToday=new ArrayList<>();
        customersBranchToday=new ArrayList<>();
        customersBranchAll=new ArrayList<>();
        tellerReportsBranchToday=new ArrayList<>();
        tellerReportsBranchAll=new ArrayList<>();
        tellerReportsBranchToday=new ArrayList<>();
        superCashBranchAll=new ArrayList<>();
        superCashBranchToday=new ArrayList<>();
        manualPaymentBranchAll=new ArrayList<>();
        manualPaymentBranchToday=new ArrayList<>();
        tellerReportsAll=new ArrayList<>();
        customersNewToday=new ArrayList<>();
        tellerCashArrayList=new ArrayList<>();


        customerMachine="Customer";
        tellerMachine="Teller";

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        dbHelper = new DBHelper(this);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        todayDate = mdformat.format(calendar.getTime());
        String[] items2 = todayDate.split("-");
        String year1 = items2[0];
        String month1 = items2[1];
        monthYearStr = year1 + "-" + month1;
        try {
            date=mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cusLayout = findViewById(R.id.layout2);
        txtTellerTotalPayment = findViewById(R.id.TellerTotalPayment);
        //txtTellerPaymentT = findViewById(R.id.tellerPaymentT);
        txtTellerNewCus = findViewById(R.id.TellerNewCus);
        txtTellerMonthTellerCash = findViewById(R.id.tellerMonthTellerCash);
        txtBranchPaymentToday = findViewById(R.id.BranchPaymentToday);
        txtBranchTotalPayment = findViewById(R.id.BranchTotalPayment);
        txtCustomersforBranch = findViewById(R.id.CustomersforBranch);
        txtCustomerPaymentToday = findViewById(R.id.CustomerPaymentToday);
        totalSoCountToday =  findViewById(R.id.soCounts);
        txtTotalPaymentToday =  findViewById(R.id.TotalPayments);
        countManualPaymentToday =  findViewById(R.id.countManualPayments);
        txtNewTXToday = findViewById(R.id.txtNewTXToday);
        txtNewPackageCountToday =  findViewById(R.id.txtNewPackageCountToday);
        txtNewCusToday =  findViewById(R.id.txtNewCusToday);
        txtAllProfileCount=  findViewById(R.id.txtAllProfileCount);
        txttotalSavingCs =  findViewById(R.id.totalSavingCs);
        txtTotalSavingsToday =  findViewById(R.id.txtTotalSavingsToday);
        btnGetTellerDetails =  findViewById(R.id.buttonPaymentTellerTotal);
        btnGetTellerDetails.setOnClickListener(this::getTellerTotalPayment);
        btnGetCusDetails =  findViewById(R.id.buttonCustomerPayment);
        btnGetCusDetails.setOnClickListener(this::getCusPaymentToday);
        btnGetBranchDetails =  findViewById(R.id.buttonPaymentBranchT);
        dateText = findViewById(R.id.date_text_);
        dateText.setOnClickListener(this::datePicker);

        dateText.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View arg0) {
                showDialog(DATE_DIALOG_ID);

            }
        });
        calendar1 = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);

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

        stringDate = mYear+"-"+ mMonth+"-"+mDay;

        String[] items222 = stringDate.split("-");
        String year133 = items2[0];
        String month144 = items2[1];

        monthYearStrFinal = mYear + "-" + mMonth;




        if(stringDate.isEmpty()){
            stringDate=todayDate;
            monthYearStrFinal=monthYearStr;
        }



        btnGetBranchDetails.setOnClickListener(this::getBranchPaymentToday);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            profileArrayList=dbHelper.getTellersFromMachine(tellerMachine);

        }


        //customerArrayList=dbHelper.getCustomerFromMachine();
        spnPaymentBranchT =  findViewById(R.id.spnPaymentBranchT);
        spnTellersDetails =  findViewById(R.id.spnTellerPayment);
        spnCusDetails =  findViewById(R.id.spnCusPayment);
        spnTellersDetails.setOnItemSelectedListener(this);

        try {

            spnTellersDetails.setAdapter(new ProfileSimpleAdapter(SuperAdminCountAct.this, R.layout.profile_row,
                    profileArrayList));
            profileArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnTellersDetails.setAdapter(profileSimpleAdapter);
            spnTellersDetails.setSelection(1);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        try {

            selectedTellerIndex = spnTellersDetails.getSelectedItemPosition();


        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        try {

            if(profileArrayList.size()>0){
                teller = profileArrayList.get(selectedTellerIndex);

            }


        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Oops! No Teller");
        }



        if(teller !=null){
            tellerID=teller.getPID();
            customerArrayList=teller.getProfileCustomers();
            tellerNames=teller.getProfileLastName()+","+teller.getProfileFirstName();
        }


        try {
            spnCusDetails.setAdapter(new CusSimpleAdapter(SuperAdminCountAct.this, R.layout.customer_row3,
                    customerArrayList));
            cusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCusDetails.setAdapter(customerArrayAdapter);
            spnCusDetails.setSelection(0);
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        try {


            selectedCusIndex = spnCusDetails.getSelectedItemPosition();
            if(customerArrayList !=null){
                customer = customerArrayList.get(selectedCusIndex);

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }

        if(customer !=null){
            customerID=customer.getCusUID();
        }




        spnPaymentBranchT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //branchName1 = spnPaymentBranchT.getSelectedItem().toString();
                branchName1 = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            standingOrders = dbHelper.getAllStandingOrders11();

        }




        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            skyLightPackages = dbHelper.getAllPackagesAdmin();

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            paymentCodeArrayList = dbHelper.getAllSavingsCodes();

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            customerDailyReports3 = dbHelper.getAllReportsAdmin();

        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            allUsers = dbHelper.getAllProfileUsers();

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            paymentArrayListAll=dbHelper.getALLPaymentsSuper();

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            tellerReportsAll=dbHelper.getTellerReportsAll();

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            paymentForTellerToday=dbHelper.getTotalPaymentTodayForTeller1(tellerID,stringDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            paymentTotalForTeller=dbHelper.getTotalPaymentForTeller(tellerID);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            customersForTeller=dbHelper.getNewCustomersCountForTodayTeller(tellerID,todayDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            paymentForBranchTotal=dbHelper.getTotalPaymentForBranch(branchName1);

        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            paymentForBranchToday=dbHelper.getTotalPaymentTodayForBranch1(branchName2,stringDate);

        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            soCount=dbHelper.getStandingOrderCountToday(todayDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            totalSavingsToday=dbHelper.getSavingsCountToday(todayDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            totalSavings2Today33=dbHelper.getTotalSavingsToday(todayDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            customerCountToday=dbHelper.getAllNewCusCountForToday(todayDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            countPackageToday=dbHelper.getNewPackageCountToday(todayDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            countToday =dbHelper.getAllTxCountForToday(todayDate);

        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            customersNewToday=dbHelper.getCustomersToday(todayDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            savingsAll=dbHelper.getAllReportsAdmin();

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            paymentCodeDate =dbHelper.getSavingsCodeForDate(stringDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            customersTellerAll=dbHelper.getCustomersFromCurrentProfile(tellerID);

        }


        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            customersTellerToday=dbHelper.getCustomersFromProfileWithDate(tellerID,stringDate);

        }

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            skyLightPackagesTellerAll=dbHelper.getAllPackagesProfile(tellerID);

        }

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            skyLightPackagesTellerToday=dbHelper.getPackagesForTellerProfileWithDate(tellerID,stringDate);

        }

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            paymentCodeForTeller=dbHelper.getCodesFromCurrentTeller(tellerNames);

        }

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            tellerCashArrayList=dbHelper.getTellerCashForTeller(tellerID);

        }

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            tellerReportsTeller=dbHelper.getTellerReportForTeller(tellerID);

        }

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            manualPaymentTeller=dbHelper.getALLPaymentsTeller(tellerID);

        }

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            skyLightPackagesToday= dbHelper.getPackagesSubscribedToday(todayDate);

        }


        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            savingsToday=dbHelper.getCustomerDailyReportToday(todayDate);

        }

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            transactionsToday=dbHelper.getTransactionsToday(todayDate);

        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            manualPaymentToday=dbHelper.getALLPaymentsSuperToday(todayDate);

        }

        sqLiteDatabase = dbHelper.getWritableDatabase();
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            paymentCodeForCus=dbHelper.getCodesFromCurrentCustomer(customerID);

        }

        //transactionAll=dbHelper.getAllTransactionAdmin();



        RecyclerView recyclerPackages = findViewById(R.id.recyclerAllPackages444);
        RecyclerView recyclerNewPackageToday = findViewById(R.id.recyclerNewPackageToday);

        RecyclerView recyclerSavingsToday = findViewById(R.id.recyclerTotalSavingsToday);
        RecyclerView recyclerSavingsAll = findViewById(R.id.recyclerSavingsAll);
        RecyclerView recyclerAllUsers = findViewById(R.id.recyclerAllUsers);
        RecyclerView recyclerNewUsersToday = findViewById(R.id.recyclerNewUsersToday);

        RecyclerView recyclerTranxToday = findViewById(R.id.recyclerTranxToday);
        RecyclerView recyclerAllTellerReports = findViewById(R.id.recyclerAllTellerReports);
        RecyclerView recyclerAllTranx = findViewById(R.id.recyclerAllTranx);
        RecyclerView recyclerManualPaymentToday = findViewById(R.id.recyclerManualPaymentToday);
        RecyclerView recyclerAllManualPayment = findViewById(R.id.recyclerAllPayments);
        RecyclerView recyclerStandingOrders = findViewById(R.id.recyclerSOAll);

        RecyclerView recyclerTellerCode = findViewById(R.id.rV_TellerCode);
        RecyclerView recyclerTellerCash = findViewById(R.id.rV_TellerCash);
        RecyclerView recyclerTellerPacksAll = findViewById(R.id.rv_Teller_Packages);
        RecyclerView recyclerTellerManualPaymentToday = findViewById(R.id.rV_Teller_Payments_Today);
        RecyclerView recyclerTellerAllManualPayment = findViewById(R.id.rV_Teller_Payments);
        RecyclerView recyclerTellerSuperCashToday = findViewById(R.id.rv_Teller_SuperCashToday);
        RecyclerView recyclerAllTellerSuperCash = findViewById(R.id.rv_Teller_SuperCash);
        RecyclerView recyclerTellerTellerReport = findViewById(R.id.rV_Teller_Teller_Report);

        RecyclerView recyclerTellerStocksToday = findViewById(R.id.rv_Teller_StocksToday);
        RecyclerView recyclerTellerStocksAll = findViewById(R.id.rv_Teller_Stocks);
        RecyclerView recyclerTellerAllCus = findViewById(R.id.rv_TellerCus);
        RecyclerView recyclerTellerCusToday = findViewById(R.id.rv_TellerCusToday);
        RecyclerView recyclerTellerReportToday = findViewById(R.id.rV_Teller_Report_Today);
        RecyclerView recyclerTellerAllReports = findViewById(R.id.rV_Teller_All_Report);
        RecyclerView recyclerTellerPacksToday = findViewById(R.id.rv_Teller_Pack_Today);
        //RecyclerView recyclerTellerPacksAll = findViewById(R.id.rv_Teller_Packages);

        RecyclerView recyclerCusSavingsToday = findViewById(R.id.recyclerCusSavingsToday);
        RecyclerView recyclerCusAllSavings = findViewById(R.id.recyclerAllCusSavings);
        RecyclerView recyclerCusTXToday = findViewById(R.id.recyclerCusTranxToday);
        RecyclerView recyclerCusAllTx = findViewById(R.id.recyclerCusAllTranx);
        RecyclerView recyclerCusAllPayments = findViewById(R.id.recyclerCusPayment);
        RecyclerView recyclerCusUnconfirmedSavings = findViewById(R.id.recyclerCusUnCon);
        RecyclerView recyclerCusAllCodes = findViewById(R.id.payment_recyclerCusCode);


        RecyclerView recyclerCusAllDocs = findViewById(R.id.recyclerCusDoc);

        RecyclerView recyclerBranchAllDeposits = findViewById(R.id.recyclerBranchDeposits);
        RecyclerView recyclerBranchAllStocks = findViewById(R.id.recyclerBranchStocks);
        RecyclerView recyclerBranchSuperCash = findViewById(R.id.recyclerBranchSuperCash);
        RecyclerView recyclerBranchCus = findViewById(R.id.recyclerBranchCus);
        RecyclerView recyclerBranchPayments = findViewById(R.id.recyclerBranchPayments);

        RecyclerView recyclerCusCodes = findViewById(R.id.recyclerCusPaymentCodes);
        RecyclerView rcyclerTransactions = findViewById(R.id.recyclerViewTx);
        RecyclerView recyclerAccounts = findViewById(R.id.recyclerViewAcct);
        RecyclerView recyclerStandingOrder = findViewById(R.id.recyclerViewSO);

        try {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerStandingOrders.setLayoutManager(layoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerStandingOrders.getContext(),
                    layoutManager.getOrientation());
            recyclerStandingOrders.addItemDecoration(dividerItemDecoration);
            recyclerStandingOrders.setItemAnimator(new DefaultItemAnimator());
            standingOrderAdapter = new StandingOrderAdapter(SuperAdminCountAct.this,standingOrders);
            recyclerStandingOrders.setAdapter(standingOrderAdapter);
            //standingOrderAdapter.setWhenClickListener(this);
        } catch (RuntimeException e) {
            System.out.println("Oops!");
        }


        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerCusCodes.setLayoutManager(layoutManager2);
        paymentCodeAdapter = new PaymentCodeAdapter(SuperAdminCountAct.this,paymentCodeForCus);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerCusCodes.getContext(),
                layoutManager2.getOrientation());
        recyclerCusCodes.addItemDecoration(dividerItemDecoration2);
        recyclerCusCodes.setItemAnimator(new DefaultItemAnimator());
        recyclerCusCodes.setAdapter(paymentCodeAdapter);



        LinearLayoutManager layoutManagerTToday
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerTranxToday.setLayoutManager(layoutManagerTToday);
        transactionAdapter = new TransactionAdapter(SuperAdminCountAct.this, transactionsToday);
        recyclerTranxToday.setAdapter(transactionAdapter);
        recyclerTranxToday.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationTToday = new DividerItemDecoration(recyclerTranxToday.getContext(),
                layoutManagerTToday.getOrientation());
        recyclerAllManualPayment.addItemDecoration(dividerItemDecorationTToday);



        LinearLayoutManager layoutManagerCus
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerNewUsersToday.setLayoutManager(layoutManagerCus);
        DividerItemDecoration dividerItemDecorationCus = new DividerItemDecoration(recyclerNewUsersToday.getContext(),
                layoutManagerCus.getOrientation());
        recyclerNewUsersToday.addItemDecoration(dividerItemDecorationCus);
        recyclerNewUsersToday.setItemAnimator(new DefaultItemAnimator());
        customerAdapter = new CustomerAdapter(SuperAdminCountAct.this,customersNewToday);
        //recyclerNewUsersToday.setHasFixedSize(true);
        recyclerNewUsersToday.setAdapter(customerAdapter);





        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerPackages.setLayoutManager(layoutManager1);
        packageAdapter = new SkyLightPackageAdapter(SuperAdminCountAct.this,skyLightPackages);
        //recyclerPackages.setHasFixedSize(true);
        recyclerPackages.setAdapter(packageAdapter);
        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(recyclerPackages.getContext(),
                layoutManager1.getOrientation());
        recyclerPackages.addItemDecoration(dividerItemDecoration1);
        recyclerPackages.setItemAnimator(new DefaultItemAnimator());




        LinearLayoutManager layoutManagerP
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        paymentAdapter = new PaymentAdapterSuper(SuperAdminCountAct.this,manualPaymentToday);
        recyclerManualPaymentToday.setLayoutManager(layoutManagerP);
        recyclerManualPaymentToday.setAdapter(paymentAdapter);
        recyclerManualPaymentToday.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration8 = new DividerItemDecoration(recyclerManualPaymentToday.getContext(),
                layoutManagerP.getOrientation());
        recyclerAllManualPayment.addItemDecoration(dividerItemDecoration8);




        LinearLayoutManager layoutManager3
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerAllTranx.setLayoutManager(layoutManager3);
        transactionAdapter = new TransactionAdapter(SuperAdminCountAct.this, transactionAll);
        recyclerAllTranx.setAdapter(transactionAdapter);
        recyclerAllTranx.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationT = new DividerItemDecoration(recyclerAllTranx.getContext(),
                layoutManager3.getOrientation());
        recyclerAllManualPayment.addItemDecoration(dividerItemDecorationT);



        LinearLayoutManager layoutManager6
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        savingsAdapter = new SuperSavingsAdapter(SuperAdminCountAct.this,customerDailyReports3);
        recyclerSavingsAll.setLayoutManager(layoutManager6);
        recyclerSavingsAll.setAdapter(savingsAdapter);
        recyclerSavingsAll.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationL = new DividerItemDecoration(recyclerSavingsAll.getContext(),
                layoutManager6.getOrientation());
        recyclerSavingsAll.addItemDecoration(dividerItemDecorationL);
        //recyclerSavingsAll.setHasFixedSize(true);


        LinearLayoutManager layoutManager7
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        profileAdapter = new ProfileAdapter(SuperAdminCountAct.this,allUsers);
        recyclerAllUsers.setLayoutManager(layoutManager7);
        recyclerAllUsers.setAdapter(profileAdapter);
        recyclerAllUsers.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration7 = new DividerItemDecoration(recyclerAllUsers.getContext(),
                layoutManager7.getOrientation());
        recyclerAllUsers.addItemDecoration(dividerItemDecoration7);
        //recyclerAllUsers.setHasFixedSize(true);



        LinearLayoutManager layoutManager8
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        paymentAdapter = new PaymentAdapterSuper(SuperAdminCountAct.this,paymentArrayListAll);
        recyclerAllManualPayment.setLayoutManager(layoutManager8);
        recyclerAllManualPayment.setAdapter(paymentAdapter);
        recyclerAllManualPayment.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecorationPay = new DividerItemDecoration(recyclerAllManualPayment.getContext(),
                layoutManager8.getOrientation());
        recyclerAllManualPayment.addItemDecoration(dividerItemDecorationPay);




        LinearLayoutManager layoutManager9
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tellerReportAdapter = new TellerReportAdapterSuper(SuperAdminCountAct.this,tellerReportsAll);
        recyclerAllTellerReports.setLayoutManager(layoutManager9);
        recyclerAllTellerReports.setAdapter(tellerReportAdapter);
        recyclerAllTellerReports.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration9 = new DividerItemDecoration(recyclerAllTellerReports.getContext(),
                layoutManager9.getOrientation());
        recyclerAllTellerReports.addItemDecoration(dividerItemDecoration9);
        //recyclerAllTellerReports.setHasFixedSize(true);




        btnGetCusDetails =  findViewById(R.id.buttonCustomerPayment);
        btnGetCusDetails.setOnClickListener(this::getCusPaymentToday);
        btnGetCusDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cusLayout.setVisibility(View.VISIBLE);
                recyclerCusSavingsToday.setVisibility(View.VISIBLE);
                recyclerCusAllSavings.setVisibility(View.VISIBLE);
                recyclerCusAllDocs.setVisibility(View.VISIBLE);
                recyclerCusAllCodes.setVisibility(View.VISIBLE);
                recyclerCusTXToday.setVisibility(View.VISIBLE);
                recyclerCusAllPayments.setVisibility(View.VISIBLE);
                recyclerCusUnconfirmedSavings.setVisibility(View.VISIBLE);
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    paymentForCusToday=dbHelper.getTotalPaymentTodayForCustomer(customerID,todayDate);

                }

                recyclerBranchAllDeposits.setVisibility(View.GONE);
                recyclerBranchAllStocks.setVisibility(View.GONE);
                recyclerBranchSuperCash.setVisibility(View.GONE);
                recyclerBranchCus.setVisibility(View.GONE);
                recyclerBranchPayments.setVisibility(View.GONE);
                recyclerTellerManualPaymentToday.setVisibility(View.GONE);
                recyclerTellerPacksToday.setVisibility(View.GONE);
                recyclerTellerPacksAll.setVisibility(View.GONE);
                recyclerTellerAllReports.setVisibility(View.GONE);
                recyclerTellerReportToday.setVisibility(View.GONE);
                recyclerTellerAllCus.setVisibility(View.GONE);
                recyclerTellerCusToday.setVisibility(View.GONE);
                recyclerTellerStocksAll.setVisibility(View.GONE);
                recyclerTellerStocksToday.setVisibility(View.GONE);
                recyclerAllTellerSuperCash.setVisibility(View.GONE);
                recyclerTellerAllManualPayment.setVisibility(View.GONE);
                recyclerTellerSuperCashToday.setVisibility(View.GONE);

            }
        });

        btnGetTellerDetails =  findViewById(R.id.buttonPaymentTellerTotal);
        btnGetTellerDetails.setOnClickListener(this::getTellerTotalPayment);
        btnGetTellerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cusLayout.setVisibility(View.GONE);
                recyclerTellerManualPaymentToday.setVisibility(View.VISIBLE);
                recyclerTellerPacksToday.setVisibility(View.VISIBLE);
                recyclerTellerPacksAll.setVisibility(View.VISIBLE);
                recyclerTellerAllReports.setVisibility(View.VISIBLE);
                recyclerTellerReportToday.setVisibility(View.VISIBLE);
                recyclerTellerAllCus.setVisibility(View.VISIBLE);
                recyclerTellerCusToday.setVisibility(View.VISIBLE);
                recyclerTellerStocksAll.setVisibility(View.VISIBLE);
                recyclerTellerStocksToday.setVisibility(View.VISIBLE);
                recyclerAllTellerSuperCash.setVisibility(View.VISIBLE);
                recyclerTellerAllManualPayment.setVisibility(View.VISIBLE);
                recyclerTellerCode.setVisibility(View.VISIBLE);
                recyclerTellerSuperCashToday.setVisibility(View.VISIBLE);
                recyclerTellerCash.setVisibility(View.VISIBLE);
                recyclerTellerTellerReport.setVisibility(View.VISIBLE);


                recyclerBranchAllDeposits.setVisibility(View.GONE);
                recyclerBranchAllStocks.setVisibility(View.GONE);
                recyclerBranchSuperCash.setVisibility(View.GONE);
                recyclerBranchCus.setVisibility(View.GONE);
                recyclerBranchPayments.setVisibility(View.GONE);
                recyclerCusSavingsToday.setVisibility(View.GONE);
                recyclerCusAllSavings.setVisibility(View.GONE);
                recyclerCusAllDocs.setVisibility(View.GONE);
                recyclerCusAllCodes.setVisibility(View.GONE);
                recyclerCusTXToday.setVisibility(View.GONE);
                recyclerCusAllPayments.setVisibility(View.GONE);
                recyclerCusUnconfirmedSavings.setVisibility(View.GONE);
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    totalTellerCashForTheMonth = dbHelper.getTellerTotalTellerCashForTheMonth(tellerNames, monthYearStrFinal);


                }
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    totalTellerNewCusForTheMonth = dbHelper.getTellerMonthCusCountNew(tellerID,monthYearStrFinal);


                }


                if (totalTellerNewCusForTheMonth > 0) {
                    txtTellerNewCus.setText("Number of New Customers for the Month" + totalTellerNewCusForTheMonth);


                } else if (totalTellerNewCusForTheMonth == 0) {
                    txtTellerNewCus.setText("No new Customers for this Month, for the Teller");

                }

                if (totalTellerCashForTheMonth > 0) {
                    txtTellerMonthTellerCash.setText("Teller Cash for the Month N" + totalTellerNewCusForTheMonth);


                } else if (totalTellerCashForTheMonth == 0) {
                    txtTellerMonthTellerCash.setText("No Teller Cash for this Month, for the Teller");

                }

                LinearLayoutManager layoutManagerMP
                        = new LinearLayoutManager(SuperAdminCountAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerTellerAllManualPayment.setLayoutManager(layoutManagerMP);
                DividerItemDecoration dividerItemDecorationMP = new DividerItemDecoration(recyclerTellerAllManualPayment.getContext(),
                        layoutManagerCus.getOrientation());
                recyclerTellerAllManualPayment.addItemDecoration(dividerItemDecorationMP);
                recyclerTellerAllManualPayment.setItemAnimator(new DefaultItemAnimator());
                paymentAdapter = new PaymentAdapterSuper(SuperAdminCountAct.this, manualPaymentTeller);
                //recyclerTellerAllManualPayment.setHasFixedSize(true);
                recyclerTellerAllManualPayment.setAdapter(paymentAdapter);

                LinearLayoutManager layoutManagerTR
                        = new LinearLayoutManager(SuperAdminCountAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerTellerTellerReport.setLayoutManager(layoutManagerTR);
                DividerItemDecoration dividerItemDecorationTR = new DividerItemDecoration(recyclerTellerTellerReport.getContext(),
                        layoutManagerCus.getOrientation());
                recyclerTellerTellerReport.addItemDecoration(dividerItemDecorationTR);
                recyclerTellerTellerReport.setItemAnimator(new DefaultItemAnimator());
                tellerReportAdapterSuper = new TellerReportAdapterSuper(SuperAdminCountAct.this, tellerReportsTeller);
                //recyclerTellerTellerReport.setHasFixedSize(true);
                recyclerTellerTellerReport.setAdapter(tellerReportAdapterSuper);

                LinearLayoutManager layoutManagertc
                        = new LinearLayoutManager(SuperAdminCountAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerTellerCash.setLayoutManager(layoutManagertc);
                DividerItemDecoration dividerItemDecorationTC = new DividerItemDecoration(recyclerTellerCash.getContext(),
                        layoutManagerCus.getOrientation());
                recyclerTellerCash.addItemDecoration(dividerItemDecorationTC);
                recyclerTellerCash.setItemAnimator(new DefaultItemAnimator());
                tellerCashAdapter = new TellerCashAdapter(SuperAdminCountAct.this, tellerCashArrayList);
                recyclerTellerCash.setAdapter(tellerCashAdapter);

                LinearLayoutManager layoutManagerCode
                        = new LinearLayoutManager(SuperAdminCountAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerTellerCode.setLayoutManager(layoutManagerCode);
                DividerItemDecoration dividerItemDecorationCode = new DividerItemDecoration(recyclerTellerCode.getContext(),
                        layoutManagerCode.getOrientation());
                recyclerTellerCode.addItemDecoration(dividerItemDecorationCode);
                recyclerTellerCode.setItemAnimator(new DefaultItemAnimator());
                paymentCodeAdapter = new PaymentCodeAdapter(SuperAdminCountAct.this, paymentCodeForTeller);
                recyclerTellerCode.setAdapter(paymentCodeAdapter);


                LinearLayoutManager layoutManagerCus
                        = new LinearLayoutManager(SuperAdminCountAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerTellerCusToday.setLayoutManager(layoutManagerCus);
                DividerItemDecoration dividerItemDecorationCus = new DividerItemDecoration(recyclerTellerCusToday.getContext(),
                        layoutManagerCus.getOrientation());
                recyclerTellerCusToday.addItemDecoration(dividerItemDecorationCus);
                recyclerTellerCusToday.setItemAnimator(new DefaultItemAnimator());
                customerAdapter = new CustomerAdapter(SuperAdminCountAct.this, customersTellerToday);
                //recyclerTellerCusToday.setHasFixedSize(true);
                recyclerTellerCusToday.setAdapter(customerAdapter);


                LinearLayoutManager layoutManagerCusAll
                        = new LinearLayoutManager(SuperAdminCountAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerTellerAllCus.setLayoutManager(layoutManagerCusAll);
                DividerItemDecoration dividerItemDecorationCusAll = new DividerItemDecoration(recyclerTellerAllCus.getContext(),
                        layoutManagerCus.getOrientation());
                recyclerTellerAllCus.addItemDecoration(dividerItemDecorationCusAll);
                recyclerTellerAllCus.setItemAnimator(new DefaultItemAnimator());
                customerAdapter = new CustomerAdapter(SuperAdminCountAct.this, customersTellerAll);
                //recyclerTellerAllCus.setHasFixedSize(true);
                recyclerTellerAllCus.setAdapter(customerAdapter);


                LinearLayoutManager layoutManager1
                        = new LinearLayoutManager(SuperAdminCountAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerTellerPacksToday.setLayoutManager(layoutManager1);
                packageAdapter = new SkyLightPackageAdapter(SuperAdminCountAct.this, skyLightPackagesTellerToday);
                //recyclerTellerPacksToday.setHasFixedSize(true);
                recyclerTellerPacksToday.setAdapter(packageAdapter);
                DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(recyclerTellerPacksToday.getContext(),
                        layoutManager1.getOrientation());
                recyclerTellerPacksToday.addItemDecoration(dividerItemDecoration1);
                recyclerTellerPacksToday.setItemAnimator(new DefaultItemAnimator());


                LinearLayoutManager layoutManagerAll
                        = new LinearLayoutManager(SuperAdminCountAct.this, LinearLayoutManager.HORIZONTAL, false);
                recyclerTellerPacksAll.setLayoutManager(layoutManagerAll);
                packageAdapter = new SkyLightPackageAdapter(SuperAdminCountAct.this, skyLightPackagesTellerAll);
                //recyclerTellerPacksAll.setHasFixedSize(true);
                recyclerTellerPacksAll.setAdapter(packageAdapter);
                DividerItemDecoration dividerItemDecorationAll = new DividerItemDecoration(recyclerTellerPacksAll.getContext(),
                        layoutManagerAll.getOrientation());
                recyclerTellerPacksAll.addItemDecoration(dividerItemDecorationAll);
                recyclerTellerPacksAll.setItemAnimator(new DefaultItemAnimator());

            }
        });





        btnGetBranchDetails =  findViewById(R.id.buttonPaymentBranchT);
        btnGetBranchDetails.setOnClickListener(this::getTotalPaymentBranch);
        btnGetBranchDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cusCountForOffice= dbHelper.getNewCustomersCountForTodayOffice(branchName,todayDate);
                recyclerBranchAllDeposits.setVisibility(View.VISIBLE);
                recyclerBranchAllStocks.setVisibility(View.VISIBLE);
                recyclerBranchSuperCash.setVisibility(View.VISIBLE);
                recyclerBranchCus.setVisibility(View.VISIBLE);
                recyclerBranchPayments.setVisibility(View.VISIBLE);
                recyclerTellerManualPaymentToday.setVisibility(View.GONE);
                recyclerTellerPacksToday.setVisibility(View.GONE);
                recyclerTellerPacksAll.setVisibility(View.GONE);
                recyclerTellerAllReports.setVisibility(View.GONE);
                recyclerTellerReportToday.setVisibility(View.GONE);
                recyclerTellerAllCus.setVisibility(View.GONE);
                recyclerTellerCusToday.setVisibility(View.GONE);
                recyclerTellerStocksAll.setVisibility(View.GONE);
                recyclerTellerStocksToday.setVisibility(View.GONE);
                recyclerAllTellerSuperCash.setVisibility(View.GONE);
                recyclerTellerAllManualPayment.setVisibility(View.GONE);
                recyclerTellerSuperCashToday.setVisibility(View.GONE);

                recyclerCusSavingsToday.setVisibility(View.GONE);
                recyclerCusAllSavings.setVisibility(View.GONE);
                recyclerCusAllDocs.setVisibility(View.GONE);
                recyclerCusAllCodes.setVisibility(View.GONE);
                recyclerCusTXToday.setVisibility(View.GONE);
                recyclerCusAllPayments.setVisibility(View.GONE);
                recyclerCusUnconfirmedSavings.setVisibility(View.GONE);




            }
        });


        txttotalSavingCs.setText(MessageFormat.format("Total Savings N{0}", totalSavings2Today33));
        txtNewCusToday.setText(MessageFormat.format("New Customers:{0}", customerCountToday));
        txtNewPackageCountToday.setText(MessageFormat.format("New Packs:{0}", countPackageToday));
        txtNewTXToday.setText(MessageFormat.format("Transactions today N{0}", countToday));
        txtTellerNewCus.setText(MessageFormat.format("Teller Cus:{0}", customersForTeller));

        //txtTellerPaymentT.setText(MessageFormat.format("Teller's Payment ,today : N{0}", paymentForTellerToday));
        txtTellerTotalPayment.setText(MessageFormat.format("Teller's Total Payment: N{0}", paymentTotalForTeller));
        txtCustomerPaymentToday.setText(MessageFormat.format("Customer's Payment ,today : N{0}", paymentForCusToday));
        txtTotalSavingsToday.setText(MessageFormat.format("Savings Count{0}", totalSavingsToday));
        txtBranchPaymentToday.setText(MessageFormat.format("Branch's Payment ,today : N{0}", paymentForBranchToday));
        txtBranchTotalPayment.setText(MessageFormat.format("Branch's Total Payment: N{0}", paymentForBranchTotal));
        txtCustomersforBranch.setText(MessageFormat.format("Branch Customers ,Today:{0}", cusCountForOffice));

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
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH+1);
        day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog( SuperAdminCountAct.this, R.style.DatePickerDialogStyle,mDateSetListener,year,month,day);
        dialog.show();
        stringDate = year+"-"+ month+"-"+day;
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //Toast.makeText(this, profileArrayList[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }

    public void getCusForBranch(View view) {


    }

    public void getTellerNewCus(View view) {

    }

    public void getTotalPaymentBranch(View view) {


    }

    public void getTellerTotalPayment(View view) {
        dbHelper = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());

        //tellerID


    }

    public void getBranchPaymentToday(View view) {
        dbHelper = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();


        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());
        try {
            date=mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }



    }

    public void getCusPaymentToday(View view) {
        dbHelper = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());
        //customerID
        try {
            date=mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    public void tellerPaymentToday(View view) {
        dbHelper = new DBHelper(this);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = mdformat.format(calendar.getTime());
        try {
            date=mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onItemClick(Account account) {

    }

    @Override
    public void onItemClick(PaymentCode paymentCode) {

    }

    @Override
    public void onItemClick(SkyLightPackage lightPackage) {

    }

    @Override
    public void onItemClick(StandingOrder standingOrder) {

    }

    @Override
    public void onItemClick(CustomerDailyReport customerDailyReport) {

    }

    @Override
    public void onItemClick(Transaction transaction) {

    }
}