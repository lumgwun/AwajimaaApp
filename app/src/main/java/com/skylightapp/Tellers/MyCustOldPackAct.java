package com.skylightapp.Tellers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;
import com.skylightapp.SMSAct;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;

public class MyCustOldPackAct extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    private AppBarLayout appBarLayout;
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    long profileUID2;
    long id;
    String userName;
    String password;
    Customer customer;
    Bundle getBundle;
    String name;
    com.github.clans.fab.FloatingActionButton fab;
    String ManagerSurname,managerFirstName,managerPhoneNumber1,managerEmail, managerNIN,managerUserName;
    SharedPreferences sharedpreferences;
    Gson gson;
    String json;

    public final static String DAILY_SAVINGS_CONT_ID = "KEY_EXTRA_PACKAGE_ID";
    private Button btn_add_New_Savings,  btn_payNow;
    private static final int SPINNER_OPTION_FIRST = 0;
    private static final int SPINNER_OPTION_SECOND = 1;
    private static final int SPINNER_OPTION_THIRD = 2;

    EditText edt_Amount,edtDurationInMonths,edt_NoOfDays;
    //EditText ;
    AppCompatSpinner spnNumberOfDays;
    AppCompatSpinner spn_old_customers;
    private Calendar mCalendar;
    protected DBHelper dbHelper;

    Customer selectedCustomer;
    Account account ;


    private LinearLayout dotsLayout;

    private ArrayAdapter<Customer> customerArrayAdapterN;
    private ArrayAdapter<SkyLightPackage> skyLightPackageArrayAdapter;
    private ArrayList<CustomerDailyReport> customerDailyReports;
    private ArrayList<SkyLightPackage> skyLightPackageArrayList;
    private ArrayList<Customer> customersN;
    private List<SkyLightPackage> skyLightPackageList;

    private SharedPreferences userPreferences;
    private String phoneNo;
    //private Spinner accountSpinner, genderSpinner, stateSpinner, number_of_day_spinner, purposeSpinner, months;

    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;
    private Customer lastCustomerProfileUsed;
    private final int MY_LOCATION_REQUEST_CODE = 100;
    private Handler handler;

    public final static int SENDING = 1;
    public final static int CONNECTING = 2;
    public final static int ERROR = 3;
    public final static int SENT = 4;
    public final static int SHUTDOWN = 5;

    private static final String TAG = "CustomerSavingsPackageAddFragment";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Button btnFusedLocation, btn_backTo_dashboard;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String packageStatus;
    private Location previousLocation;
    private GoogleMap mMap;

    SecureRandom random;
    Random ran ;
    DatePicker date_picker_dob;

    String packageType;
    //SkyLightPackage.SkylightPackage_Type packageType;
    AppCompatSpinner spn_select_packageOngoing;
    SkyLightPackage selectedPackage;
    SkyLightPackage skyLightPackage;
    String customerBank;
    double accountBalance1;
    double newAmount;
    double newBalance;
    double newTotal;
    double packageAccountBalance;
    double grandTotal;
    double totalAmountSum;
    double savingsAmount;
    double newGrandTotal;
    double newAmountRemaining;
    private int newDaysRemaining,numberOfDays;
    AccountTypes accountTypeStr;
    String interestRate, firstNameOfCustomer,profileSurname,packageStartDate, profilePhone,lastNameOfCustomer,profileFirstName;
    String customerNames;
    Context context;
    int customerId;
    int accountNo,acctID;
    int customerID;
    double amountContributedSoFar,newAmountContributedSoFar;
    double packageGrantTotal,amountRemaining,moneySaved;
    int profileID, oldPackageId;
    private static boolean isPersistenceEnabled = false;

    int packageId,newPackageCount,daysRemaining,packageDuration,numberOfMonths;
    String reportDate,packageTypeS,machine,selectedState,selectedGender,selectedStatus,selectedOffice;
    String surName, firstName, emailAddress, phoneNumber1, address;
    long virtualAccountID;
    int packageID;
    long customerID1;
    long profileID1;
    int reportID;
    RadioGroup grpRB,grp2;
    int x;
    int y;


    String mDate;
    Button btnAddNewCus;
    private Marker m;
    CustomerDailyReport customerDailyReport;
    Bundle paymentBundle;
    SQLiteDatabase sqLiteDatabase;
    int selectedCustomerIndex,selectedPackageIndex;
    Button btn_MyCustomers, btn_AllOurCustomers;
    AppCompatRadioButton checkboxOld, checkboxNew, checkboxCustom,checkboxSavings;
    String savings,itemPurchase,promo,customPlan;
    boolean myCustomerButtonClicked;
    boolean allCustomerButtonClicked;
    TextView txtPackAmount, txtAmtSoFar,txtAmountRem,txtTotalPackAmt;

    private LinearLayoutCompat layout_month,layout_total,layout_button, layout_Amount,layout_amount,layout_type_package,layout_old_package,layout_select_my_cust, layout_select_customer,layout_sub_status;

    AppCompatTextView totalForTheDay;
    private static final String PREF_NAME = "skylight";
    ScrollView layoutScrollPackage;
    Transaction transaction;
    public static final String ACCOUNT_SID = System.getenv("AC5e05dc0a793a29dc1da2eabdebd6c28d");
    public static final String AUTH_TOKEN = System.getenv("39410e8b813c131da386f3d7bb7f94f7");
    ActivityResultLauncher<Intent> payActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Here, no request code
                        //Intent data = result.getData();
                        finish();
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_cust_old_pack);
        customer= new Customer();
        account= new Account();
        random= new SecureRandom();
        userProfile=new Profile();
        selectedPackage= new SkyLightPackage();
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        checkInternetConnection();
        customerDailyReport= new CustomerDailyReport();
        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        spn_old_customers = findViewById(R.id.old_cusMy);
        spn_select_packageOngoing = findViewById(R.id.packOldMy);
        txtPackAmount = findViewById(R.id.old_packAmount);
        txtAmtSoFar = findViewById(R.id.old_packAmountSoFar);
        txtTotalPackAmt = findViewById(R.id.old_packTotal);
        txtAmountRem = findViewById(R.id.old_packAmountRem);
        edt_NoOfDays=  findViewById(R.id.edtNoOfDaysMyOld);
        totalForTheDay =  findViewById(R.id.total_amtOld);
        btn_add_New_Savings = findViewById(R.id.add_savingsOld);
        btn_payNow = findViewById(R.id.pay_now_savingsOld);
        btn_payNow.setOnClickListener(this::payDialoq4);

        if(userProfile !=null){
            ManagerSurname = userProfile.getProfileLastName();
            managerFirstName = userProfile.getProfileFirstName();
            managerPhoneNumber1 = userProfile.getProfilePhoneNumber();
            managerEmail = userProfile.getProfileEmail();
            managerNIN = userProfile.getProfileIdentity();
            managerUserName = userProfile.getProfileUserName();
            String userRole = userProfile.getProfileMachine();
            profileUID2= userProfile.getPID();

        }
        if (userProfile != null) {
            customersN = userProfile.getProfileCustomers();
            customerArrayAdapterN = new ArrayAdapter<>(MyCustOldPackAct.this, android.R.layout.simple_spinner_item, customersN);
            customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_old_customers.setAdapter(customerArrayAdapterN);
            spn_old_customers.setSelection(0);
            spn_old_customers.setSelection(customerArrayAdapterN.getPosition(customer));
            selectedCustomerIndex = spn_old_customers.getSelectedItemPosition();
            try {
                customer = customersN.get(selectedCustomerIndex);
                account=customer.getCusAccount();
                acctID=account.getSkyLightAcctNo();
                accountBalance1=account.getAccountBalance();
                getSkylightPackages(customer,account,acctID,accountBalance1);
                try {
                    if (customer != null) {
                        customerID=customer.getCusUID();
                        getSkylightPackages(customer,account,acctID,accountBalance1);

                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }


        }
    }


    public void getSkylightPackages(Customer customer,Account account,long acctID,double accountBalance1){
        skyLightPackageArrayList=selectedCustomer.getCusSkyLightPackages();
        skyLightPackageArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, skyLightPackageArrayList);
        skyLightPackageArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_select_packageOngoing.setAdapter(skyLightPackageArrayAdapter);
        spn_select_packageOngoing.setSelection(0);
        spn_select_packageOngoing.setSelection(skyLightPackageArrayAdapter.getPosition(selectedPackage));
        selectedPackageIndex = spn_select_packageOngoing.getSelectedItemPosition();
        try {
            selectedPackage = skyLightPackageArrayList.get(selectedPackageIndex);

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }



    }

    public void addSavings() {
        try {
            if(selectedPackage !=null){
                packageID=selectedPackage.getPackID();
            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
    }



    public void payDialoq4(View view){
        checkInternetConnection();
        try {
            if (String.valueOf(numberOfDays).isEmpty()) {
                Toast.makeText(MyCustOldPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();


            }else {
                doProcessing();
                //payNow();
                paymentBundle = new Bundle();
                account=customer.getCusAccount();
                paymentBundle.putString("Total", String.valueOf(totalAmountSum));
                paymentBundle.putParcelable("Customer", customer);
                paymentBundle.putParcelable("Account", account);
                paymentBundle.putParcelable("Package", selectedPackage);
                Intent amountIntent = new Intent(MyCustOldPackAct.this, PayNowActivity.class);
                amountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                amountIntent.putExtras(paymentBundle);
                payActivityResultLauncher.launch(amountIntent.putExtras(paymentBundle));

            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }



    }

    protected  void doProcessing(){

        try {
            numberOfDays = Integer.parseInt(edt_NoOfDays.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }


        String status = "Subscription in progress";
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String reportDate = mdformat.format(calendar.getTime());
        String timelineTittle1 = "New Savings alert";
        String timelineDetails1 = "NGN" + newTotal + "was saved for" + lastNameOfCustomer + "," + firstNameOfCustomer;


        String timelineDetails2 = "NGN" + newTotal + "was saved for" + lastNameOfCustomer + "," + firstNameOfCustomer + "on" + reportDate;
        if(selectedPackage !=null){
            try {
                savingsAmount=selectedPackage.getPackageDailyAmount();
                totalAmountSum = savingsAmount * numberOfDays;
                if(customer !=null){
                    customerDailyReports=customer.getCusDailyReports();

                }
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }
            account = selectedPackage.getPackageAccount();
            if (account != null) {
                packageAccountBalance = account.getAccountBalance() + totalAmountSum;
                acctID = account.getSkyLightAcctNo();

            }
            if(customer !=null){
                customerDailyReports=customer.getCusDailyReports();

            }
            try {
                for (int i = 0; i < customerDailyReports.size(); i++) {
                    if (String.valueOf(savingsAmount).equals(String.valueOf(customerDailyReports.get(i).getRecordAmount())) &&
                            String.valueOf(numberOfDays).equals(String.valueOf(customerDailyReports.get(i).getRecordNumberOfDays())) &&
                            String.valueOf(reportDate).equals(String.valueOf(customerDailyReports.get(i).getRecordDate()))) {
                        Toast.makeText(MyCustOldPackAct.this, "a very similar Report already exist!", Toast.LENGTH_LONG).show();

                    }
                    if (String.valueOf(savingsAmount).isEmpty() || String.valueOf(numberOfDays).isEmpty()) {
                        Toast.makeText(MyCustOldPackAct.this, "Some record fields are empty,please fill them", Toast.LENGTH_SHORT).show();


                    } else {
                        if (newAmountContributedSoFar < newGrandTotal) {
                            btn_backTo_dashboard.setVisibility(View.VISIBLE);
                            selectedPackage.addPSavings(profileID, customerID, reportID, savingsAmount, numberOfDays, newTotal, newDaysRemaining, newAmountRemaining, reportDate, status);
                            if(customer !=null){
                                customer.addCusNewSavings(packageID,reportID,  savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, reportDate, "In progress");
                                customer.addCusTimeLine(timelineTittle1, timelineDetails2);
                                customer.getCusAccount().setAccountBalance(newBalance);

                            }
                            if(userProfile !=null){
                                userProfile.addPTimeLine(timelineTittle1, timelineDetails2);
                                userProfile.addPSavings(profileID,customerId, reportID, savingsAmount,numberOfDays,newTotal,newDaysRemaining,newAmountRemaining,reportDate,"In progress");



                            }


                            customerDailyReport = new CustomerDailyReport(reportID, savingsAmount, numberOfDays, newTotal, newDaysRemaining, newAmountRemaining, reportDate, "in progress");
                            selectedPackage.setPackageBalance(newBalance);
                            selectedPackage.addPProfileManager(userProfile);
                            account.setAccountBalance(newBalance);
                            selectedPackage.setPackageAmount_collected(newAmountContributedSoFar);

                            try {

                                dbHelper.insertTimeLine(timelineTittle1, timelineDetails1, reportDate, mCurrentLocation);
                                dbHelper.insertDailyReport(packageID, reportID, profileID, customerID, reportDate, savingsAmount, numberOfDays, newTotal, newAmountContributedSoFar, newAmountRemaining, newDaysRemaining, status);

                            } catch (SQLiteException e) {
                                System.out.println("Oops!");
                            }

                        }
                        if (String.valueOf(newAmountContributedSoFar).equals(String.valueOf(grandTotal))) {
                            Toast.makeText(MyCustOldPackAct.this, "This package is complete,start new savings", Toast.LENGTH_SHORT).show();


                            try {

                                dbHelper.overwriteCustomerPackage(selectedPackage, selectedCustomer, customerDailyReport, account);
                                dbHelper.updatePackage(customerID, oldPackageId, 0, "Completed");
                                dbHelper.getPackageReminder(String.valueOf(oldPackageId));



                            } catch (SQLiteException e) {
                                System.out.println("Oops!");
                            }
                            String paymentMessage = "Skylight! your package :" + oldPackageId + "" + "is Complete";
                            sendSMSMessage(paymentMessage,phoneNo);

                        }

                    }


                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }


        }

    }
    protected void sendSMSMessage(String paymentMessage, String phoneNo) {
        Bundle smsBundle = new Bundle();
        smsBundle.putString(PROFILE_PHONE, phoneNo);
        smsBundle.putString("USER_PHONE", phoneNo);
        smsBundle.putString("smsMessage", paymentMessage);
        smsBundle.putString("from", "");
        smsBundle.putString("to", phoneNo);
        Intent itemPurchaseIntent = new Intent(MyCustOldPackAct.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //System.out.println(message2.getSid());;

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
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));

    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}