package com.skylightapp;

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
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.appbar.AppBarLayout;

import com.google.gson.Gson;
import com.skylightapp.Adapters.CusSpinnerAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AllCustOldPackAct extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener, AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{
    private AppBarLayout appBarLayout;
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    int profileUID2;
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

    EditText edt_NoOfDays;
    AppCompatSpinner spn_old_customers;
    private Calendar mCalendar;
    protected DBHelper dbHelper;

    Customer selectedCustomer;
    Account account1 ;

    private ViewPager viewPager;

    private LinearLayout dotsLayout;


    private ArrayAdapter<Account> accountAdapter;
    private ArrayAdapter<Customer> customerArrayAdapterN;
    private ArrayAdapter<SkyLightPackage> skyLightPackageAllAdapter;
    private ArrayList<Customer> customerArrayList;
    private ArrayList<Account> accountArrayList;
    private ArrayList<CustomerDailyReport> customerDailyReports;
    private List<Customer> customerList;
    private ArrayList<SkyLightPackage> skyLightPackageAll;
    private ArrayList<Customer> customers;
    private List<SkyLightPackage> skyLightPackageList;
    private CusSpinnerAdapter cusSpinnerAdapter;

    private SharedPreferences userPreferences;
    private String phoneNo;

    DatePickerDialog datePickerDialog;
    private static final String PREF_NAME = "skylight";
    Calendar dateCalendar;
    private Customer lastCustomerProfileUsed;
    private final int MY_LOCATION_REQUEST_CODE = 100;

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
    //private static boolean isPersistenceEnabled = false;

    int packageId,newPackageCount,daysRemaining,packageDuration,numberOfMonths;
    String reportDate,packageTypeS,machine,selectedState,selectedGender,selectedStatus,selectedOffice;
    String surName, firstName, emailAddress, phoneNumber1, address;
    int virtualAccountID ,packageID,customerID1,profileID1,reportID;
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

    private LinearLayoutCompat layout_month,layout_total,layout_button, layout_Amount,layout_amount,layout_type_package,layout_old_package,layout_select_my_cust, layout_select_customer,layout_sub_status;

    AppCompatTextView totalForTheDay;
    ScrollView layoutScrollPackage;
    FloatingActionButton fabHome;
    Transaction transaction;
    Account account;
    private String packageName;
    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");

    ActivityResultLauncher<Intent> flutterPayActivityResultLauncher = registerForActivityResult(
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

    ActivityResultLauncher<Intent> savingsStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(AllCustOldPackAct.this, "Payment returned successful", Toast.LENGTH_SHORT).show();
                            dbHelper.updatePackageRecord(profileID, customerID, oldPackageId, reportID, "paid");
                            Intent intent = new Intent(AllCustOldPackAct.this, LoginDirAct.class);
                            startActivity(intent);
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(AllCustOldPackAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
                /*@Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        finish();
                    }
                }*/
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_cust_old_pack);
        setTitle("Old Package");
        customer= new Customer();
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        random= new SecureRandom();
        selectedPackage= new SkyLightPackage();
        account= new Account();
        customers=new ArrayList<Customer>();
        json = sharedpreferences.getString("LastProfileUsed", "");
        Profile userProfile = gson.fromJson(json, Profile.class);
        checkInternetConnection();
        //userProfile= new Profile();
        customerDailyReport= new CustomerDailyReport();
        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        spn_old_customers = findViewById(R.id.old_customerAll);
        spn_select_packageOngoing = findViewById(R.id.packageFromAllCus);
        edt_NoOfDays=  findViewById(R.id.edtNoOfDaysAllCus);
        totalForTheDay =  findViewById(R.id.total_amountAllCus);
        btn_add_New_Savings = findViewById(R.id.add_savingsAllCus);
        btn_payNow = findViewById(R.id.pay_now_savingsAllCus);
        fabHome = findViewById(R.id.fabALLCus);

        try {
            customers = dbHelper.getAllCustomerSpinner();
            cusSpinnerAdapter = new CusSpinnerAdapter(AllCustOldPackAct.this,  customers);
            //customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_old_customers.setAdapter(customerArrayAdapterN);
            //spn_old_customers.setSelection(0);
            spn_old_customers.setOnItemSelectedListener(this);

            spn_old_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    //selectedCustomer = spn_old_customers.getSelectedItem().toString();


                    try {
                        selectedCustomer = (Customer) adapterView.getItemAtPosition(position);
                        Account account=customer.getCusAccount();
                        try {
                            if (selectedCustomer != null) {
                                customerID=selectedCustomer.getCusUID();
                                getSkylightPackages(customerID);
                            }
                        } catch (NullPointerException e) {
                            System.out.println("Oops!");
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Oops!");
                    }


                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (SQLiteException e1) {
            e1.printStackTrace();
        }
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
    }

    public void getSkylightPackages(int customerID){
        skyLightPackageAll=dbHelper.getCustomerIncompletePack(customerID,"inProgress");
        skyLightPackageAllAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, skyLightPackageAll);
        skyLightPackageAllAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_select_packageOngoing.setAdapter(skyLightPackageAllAdapter);
        spn_select_packageOngoing.setSelection(0);
        selectedPackageIndex = spn_select_packageOngoing.getSelectedItemPosition();
        try {
            selectedPackage = skyLightPackageAll.get(selectedPackageIndex);
            packageName=selectedPackage.getPackageName();

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }



    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemSelected(): position: " + position + " id: " + id);
        try {
            selectedCustomerIndex = spn_old_customers.getSelectedItemPosition();
            customer = customers.get(selectedCustomerIndex);
            Account account=customer.getCusAccount();
            try {
                if (customer != null) {
                    customerID=customer.getCusUID();
                    getSkylightPackages(customerID);
                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Required
    }
    public void payDialoq4(View view) {
    }
    public void goHome() {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        //String accountBank = userPreferences.getString("user","bankName");
        //String accountNumber = userPreferences.getString("Account Number","accountNumber");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void addSavings() {
        try {
            if (String.valueOf(numberOfDays).isEmpty()) {
                Toast.makeText(AllCustOldPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();

            }
            if (String.valueOf(savingsAmount).isEmpty()) {
                Toast.makeText(AllCustOldPackAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();


            } else {
                doProcessing();

            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
    }



    public void payDialoq(View view){

        doProcessing();
        paymentBundle = new Bundle();
        account=customer.getCusAccount();
        paymentBundle.putString("Total", String.valueOf(totalAmountSum));
        paymentBundle.putParcelable("Customer", customer);
        paymentBundle.putParcelable("Account", account);
        paymentBundle.putParcelable("Package", selectedPackage);
        paymentBundle.putParcelable("SkyLightPackage", selectedPackage);
        paymentBundle.putString("PackageName", packageName);
        Intent amountIntent = new Intent(AllCustOldPackAct.this, PayNowActivity.class);
        amountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        amountIntent.putExtras(paymentBundle);
        flutterPayActivityResultLauncher.launch(amountIntent.putExtras(paymentBundle));

    }


    public void payNow() {
        checkInternetConnection();
        try {
            if (String.valueOf(numberOfDays).isEmpty()) {
                Toast.makeText(AllCustOldPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();

            }else {
                paymentBundle = new Bundle();
                paymentBundle.putString("Total", String.valueOf(totalAmountSum));
                paymentBundle.putString("PackageName", packageName);
                Intent amountIntent = new Intent(AllCustOldPackAct.this, PayNowActivity.class);
                amountIntent.putExtras(paymentBundle);
                Toast.makeText(AllCustOldPackAct.this, "Your are paying NGN"+totalAmountSum, Toast.LENGTH_SHORT).show();
                savingsStartForResult.launch(new Intent(amountIntent));

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
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = mdformat.format(calendar.getTime());
        String timelineTittle1 = "New Savings alert";
        String timelineDetails1 = "NGN" + newTotal + "was saved for" + lastNameOfCustomer + "," + firstNameOfCustomer;


        String timelineDetails2 = "NGN" + newTotal + "was saved for" + lastNameOfCustomer + "," + firstNameOfCustomer + "on" + reportDate;
        if(selectedPackage !=null){
            try {
                savingsAmount=selectedPackage.getPackageDailyAmount();
                totalAmountSum = savingsAmount * numberOfDays;
                packageType= String.valueOf(selectedPackage.getPackageType());
                if(customer !=null){
                    customerDailyReports=customer.getCusDailyReports();

                }
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }
            account1 = selectedPackage.getPackageAccount();
            if (account1 != null) {
                packageAccountBalance = account1.getAccountBalance() + totalAmountSum;
                acctID = account1.getSkyLightAcctNo();

            }
            if(customer !=null){
                customerDailyReports=customer.getCusDailyReports();

            }
            try {
                for (int i = 0; i < customerDailyReports.size(); i++) {
                    if (String.valueOf(savingsAmount).equals(String.valueOf(customerDailyReports.get(i).getRecordAmount())) &&
                            String.valueOf(numberOfDays).equals(String.valueOf(customerDailyReports.get(i).getRecordNumberOfDays())) &&
                            String.valueOf(reportDate).equals(String.valueOf(customerDailyReports.get(i).getRecordDate()))) {
                        Toast.makeText(AllCustOldPackAct.this, "a very similar Report already exist!", Toast.LENGTH_LONG).show();

                    }
                    if (String.valueOf(savingsAmount).isEmpty() || String.valueOf(numberOfDays).isEmpty()) {
                        Toast.makeText(AllCustOldPackAct.this, "Some record fields are empty,please fill them", Toast.LENGTH_SHORT).show();


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
                            account1.setAccountBalance(newBalance);
                            selectedPackage.setPackageAmount_collected(newAmountContributedSoFar);

                            try {

                                dbHelper.insertTimeLine(timelineTittle1, timelineDetails1, reportDate, mCurrentLocation);
                                dbHelper.insertDailyReport(packageID, reportID, profileID, customerID, reportDate, savingsAmount, numberOfDays, newTotal, newAmountContributedSoFar, newAmountRemaining, newDaysRemaining, status);

                            } catch (SQLiteException e) {
                                System.out.println("Oops!");
                            }

                        }
                        if (String.valueOf(newAmountContributedSoFar).equals(String.valueOf(grandTotal))) {
                            Toast.makeText(AllCustOldPackAct.this, "This package is complete,start new savings", Toast.LENGTH_SHORT).show();


                            try {

                                dbHelper.overwriteCustomerPackage(selectedPackage, selectedCustomer, customerDailyReport, account1);
                                dbHelper.updatePackage(customerID, oldPackageId,  0, "Completed");
                                dbHelper.getPackageReminder(String.valueOf(oldPackageId));
                                sendNotification();



                            } catch (SQLiteException e) {
                                System.out.println("Oops!");
                            }
                            String paymentMessage = "Skylight! your package :" + oldPackageId + "" + "is Complete";
                            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                            Message message = Message.creator(
                                    new com.twilio.type.PhoneNumber(phoneNo),
                                    new com.twilio.type.PhoneNumber("234" + phoneNo),
                                    paymentMessage).create();

                        }

                    }


                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }


        }

    }
    private void sendNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Skylight Message")
                        .setContentText("Start a New Package");

        Intent notificationIntent = new Intent(this, LoginDirAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
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
        switch (menuItem.getItemId()) {
            case android.R.id.home:
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


}