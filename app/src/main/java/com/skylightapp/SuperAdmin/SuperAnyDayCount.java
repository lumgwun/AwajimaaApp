package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class SuperAnyDayCount extends AppCompatActivity {
    TextView  txtTotalPaymentToday, countManualPaymentToday, txtNewCusToday;
    SharedPreferences sharedpreferences;
    protected DBHelper dbHelper;
    Gson gson;
    ArrayAdapter<CustomerManager> managerArrayAdapter;
    ArrayList<CustomerManager> customerManagerArrayList;
    ArrayAdapter<Customer> customerArrayAdapter;
    ArrayList<Customer> customerArrayList;
    ArrayAdapter<Profile> profileArrayAdapter;
    ArrayAdapter<Profile> profileArrayAdapter2;
    ArrayList<Profile> profileArrayList;
    ArrayList<Profile> profileArrayList2;
    String json, branchName, branchName1, todayDate, dateOf,tellerMachine, customerMachine, stringTeller, stringCustomer, branchName2, tellerIDString, tellerIDString1, tellerIDString2, customerIDString;
    double totalSavings2Today33;
    int totalSavingsToday, soCount, selectedTellerIndex, cusCountForOffice, selectedCusIndex, countToday, countPackageToday, customerCountToday, customersForTeller;
    private Profile userProfile;
    private Date date;
    private Profile teller, teller2;
    private int tellerID;
    private int tellerID1;
    private int customerID;
    private int tellerID2;
    private Customer customer;
    protected DatePickerDialog datePickerDialog;
    DatePicker picker;
    SimpleDateFormat sdf;
    private SQLiteDatabase sqLiteDatabase;

    private Spinner spnPaymentBranchT, spnCusDetails, spnTellersDetails;
    String finalTodayDate;
    private TextView txtTellerPaymentT, txtCustomersforBranch, txtCustomerPaymentToday, txtBranchPaymentToday, txtTellerTotalPayment, txtBranchTotalPayment, txtTellerNewCus;
    private AppCompatButton btnGetCusDetails, btnGetTellerDetails, btnGetBranchDetails;
    private static final String PREF_NAME = "skylight";
    private double paymentForTellerToday, paymentForCusToday, paymentTotalForTeller, paymentForBranchToday, paymentForBranchTotal;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_any_day_count);
        setTitle("Selected Day Details");
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        dbHelper = new DBHelper(this);
        userProfile=new Profile();
        profileArrayList = new ArrayList<>();
        customerArrayList = new ArrayList<>();
        customerManagerArrayList = new ArrayList<>();
        tellerMachine = "Teller";
        PaymentCode paymentCode = new PaymentCode();
        picker=(DatePicker)findViewById(R.id.count_date_Super);
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOf = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        //calendar.add(Calendar.DAY_OF_YEAR, 31);
        todayDate = sdf.format(calendar.getTime());
        try {
            date=sdf.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(dateOf ==null){
            dateOf=todayDate;

        }
        txtCustomerPaymentToday = findViewById(R.id.CustomerPaymentToday22);
        btnGetCusDetails = findViewById(R.id.buttonCusPayment);
        txtTellerTotalPayment = findViewById(R.id.TellerTotalPayment33);
        txtNewCusToday = findViewById(R.id.TellerNewCus33);
        btnGetTellerDetails = findViewById(R.id.buttonPaymentTellerT);
        txtBranchPaymentToday = findViewById(R.id.BranchPaymentToday33);
        txtBranchTotalPayment = findViewById(R.id.BranchTotalPayment33);
        txtCustomersforBranch = findViewById(R.id.CustomersforBranch222);
        spnPaymentBranchT = findViewById(R.id.spnBranchT);
        btnGetBranchDetails = findViewById(R.id.btnPBranchT);


        countManualPaymentToday = findViewById(R.id.countManualPayments);
        txtTotalPaymentToday = findViewById(R.id.TotalPayments);

        txtTellerPaymentT = findViewById(R.id.tellerPaymentT);
        ProfDAO profDAO = new ProfDAO(this);
        CusDAO cusDAO = new CusDAO(this);
        SODAO sodao = new SODAO(this);
        PaymentDAO paymentDAO = new PaymentDAO(this);

        txtTellerNewCus = findViewById(R.id.TellerNewCus);
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();

            sqLiteDatabase = dbHelper.getWritableDatabase();
            profileArrayList = profDAO.getTellersFromMachine(tellerMachine);
        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            customerArrayList = cusDAO.getAllCustomerSpinner();
        }

        spnTellersDetails = findViewById(R.id.spnTellerPayment);
        spnCusDetails = findViewById(R.id.spnCusPayment);



        try {
            customerArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customerArrayList);
            customerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnCusDetails.setAdapter(customerArrayAdapter);
            spnCusDetails.setSelection(0);
            selectedCusIndex = spnCusDetails.getSelectedItemPosition();
            customer = customerArrayList.get(selectedCusIndex);
        } catch (Exception e) {
            System.out.println("Oops!");
        }
        if (teller2 != null) {
            tellerID2 = teller2.getPID();
        }
        if (customer != null) {
            customerID = customer.getCusUID();
        }




        try {
            profileArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, profileArrayList);
            profileArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnTellersDetails.setAdapter(profileArrayAdapter);
            spnTellersDetails.setSelection(0);

            selectedTellerIndex = spnTellersDetails.getSelectedItemPosition();

        } catch (Exception e) {
            System.out.println("Oops!");
        }
        if(profileArrayList !=null){
            teller = profileArrayList.get(selectedTellerIndex);

        }
        if (teller != null) {
            tellerID = teller.getPID();
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


        btnGetCusDetails.setOnClickListener(this::getCusPaymentToday);
        btnGetCusDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    paymentForCusToday = paymentDAO.getTotalPaymentTodayForCustomer(customerID, dateOf);
                }


            }
        });

        btnGetTellerDetails.setOnClickListener(this::getTellerTotalPayment);

        finalTodayDate = dateOf;
        btnGetTellerDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    paymentForTellerToday = paymentDAO.getTotalPaymentTodayForTeller1(tellerID1, dateOf);
                }


                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    paymentTotalForTeller = paymentDAO.getTotalPaymentForTeller(tellerID);
                }


                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    customersForTeller = cusDAO.getNewCustomersCountForTodayTeller(tellerID2, dateOf);
                }



            }
        });

        btnGetBranchDetails.setOnClickListener(this::getTotalPaymentBranch);
        String finalTodayDate1 = dateOf;
        btnGetBranchDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cusCountForOffice = cusDAO.getNewCustomersCountForTodayOffice(branchName, dateOf);
                paymentForBranchTotal = paymentDAO.getTotalPaymentForBranch(branchName1);
                paymentForBranchToday = paymentDAO.getTotalPaymentTodayForBranch1(branchName2, dateOf);


            }
        });
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            soCount = sodao.getStandingOrderCountToday(dateOf);
        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalSavingsToday = dbHelper.getSavingsCountToday(dateOf);
        }


        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            totalSavings2Today33 = dbHelper.getTotalSavingsToday(dateOf);
        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            customerCountToday = cusDAO.getAllNewCusCountForToday(dateOf);

        }

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            countPackageToday = dbHelper.getNewPackageCountToday(dateOf);
        }
        TranXDAO tranXDAO = new TranXDAO(this);

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            dbHelper.openDataBase();
            sqLiteDatabase = dbHelper.getWritableDatabase();
            countToday = tranXDAO.getAllTxCountForToday(dateOf);
        }


        txtNewCusToday.setText(MessageFormat.format("New Customers:{0}", customerCountToday));
        txtTellerNewCus.setText(MessageFormat.format("Teller Cus:{0}", customersForTeller));
        txtTellerPaymentT.setText(MessageFormat.format("Teller's Payment for the day : N{0}", paymentForTellerToday));
        txtTellerTotalPayment.setText(MessageFormat.format("Teller's Total Payment: N{0}", paymentTotalForTeller));
        txtCustomerPaymentToday.setText(MessageFormat.format("Customer's Payment for the day : N{0}", paymentForCusToday));
        txtBranchPaymentToday.setText(MessageFormat.format("Branch's Payment for the day : N{0}", paymentForBranchToday));
        txtBranchTotalPayment.setText(MessageFormat.format("Branch's day Payment: N{0}", paymentForBranchTotal));
        txtCustomersforBranch.setText(MessageFormat.format("Branch Customers for the day:{0}", cusCountForOffice));


    }
    private void chooseDate() {
        dateOf = picker.getYear()+"-"+ (picker.getMonth() + 1)+"-"+picker.getDayOfMonth();

    }

    public void getCusForBranch(View view) {


    }

    public void getTellerNewCus(View view) {

    }

    public void getTotalPaymentBranch(View view) {



    }

    public void getTellerTotalPayment(View view) {


    }

    public void getBranchPaymentToday(View view) {


    }

    public void getCusPaymentToday(View view) {



    }

    public void tellerPaymentToday(View view) {


    }

}