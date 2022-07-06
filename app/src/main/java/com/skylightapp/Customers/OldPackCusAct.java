package com.skylightapp.Customers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

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
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirAct;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class OldPackCusAct extends AppCompatActivity {
    public final static String DAILY_SAVINGS_CONT_ID = "KEY_EXTRA_PACKAGE_ID";
    private Button btn_add_New_Savings,  btn_payNow;

    EditText edt_Amount,edtDurationInMonths,edt_NoOfDays;
    private ArrayList<CustomerDailyReport> customerDailyReports;

    //EditText ;
    AppCompatSpinner spnNumberOfDays;
    Spinner spm_status_of_package;
    AppCompatSpinner spn_all_old_customers,spn_type_of_package;
    private int mYear, packageCount,mMonth, mHour, mMinute, mDay;
    private long mRepeatTime;
    private Calendar mCalendar;
    protected DBHelper dbHelper;
    Profile userProfile;
    Customer customer ;
    Customer selectedCustomer;
    Account account1 ;

    String packageStatus;
    private ViewPager viewPager;

    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnPrevious, btnNext;

    //int profileID;

    private ArrayAdapter<Account> accountAdapter;
    private ArrayAdapter<SkyLightPackage> skyLightPackageArrayAdapter;
    private ArrayList<Account> accountArrayList;
    private ArrayList<SkyLightPackage> skyLightPackageArrayList;
    private List<SkyLightPackage> skyLightPackageList;

    private SharedPreferences userPreferences;
    private Gson gson;
    private String json,phoneNo;
    //private Spinner accountSpinner, genderSpinner, stateSpinner, number_of_day_spinner, purposeSpinner, months;

    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;
    private Customer lastCustomerProfileUsed;
    private final int MY_LOCATION_REQUEST_CODE = 100;
//    private GoogleApiClient googleApiClient;

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
    String mLastUpdateTime;
    private Location previousLocation;
    private GoogleMap mMap;

    SecureRandom random;
    Random ran ;
    DatePicker date_picker_dob;

    String packageType;
    //SkyLightPackage.SkylightPackage_Type packageType;
    AppCompatSpinner state_spn, spn_select_packageOngoing, spn_my_cus;
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

    int packageId,newPackageCount,daysRemaining,packageDuration,numberOfMonths;
    String reportDate,packageTypeS,machine,selectedState,selectedGender,selectedStatus,selectedOffice;
    String surName, firstName, emailAddress, phoneNumber1, address;
    int virtualAccountID ,packageID,customerID1,profileID1,reportID;


    String mDate,packageNoOfDays;
    Button btnAddNewCus;
    private Marker m;
    CustomerDailyReport customerDailyReport;
    Bundle paymentBundle;
    SQLiteDatabase sqLiteDatabase;
    int selectedCustomerIndex,selectedPackageIndex;
    Button btn_MyCustomers, btn_AllOurCustomers;
    private static boolean isPersistenceEnabled = false;
    private  Account account;
    private static final String PREF_NAME = "skylight";


    private LinearLayoutCompat layout_month,layout_total,layout_button,layout_days,layout_amount,layout_type_package,layout_old_package,layout_select_my_cust, layout_select_customer,layout_sub_status;

    AppCompatTextView textTotalForTheDay, txtAmountPerDay;
    AppCompatSpinner spnNoOfDays,spnOldPackage;
    int noOfDays;
    ActivityResultLauncher<Intent> doMySavingsStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(OldPackCusAct.this, "Payment returned successful", Toast.LENGTH_SHORT).show();
                            doProcessing();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(OldPackCusAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.act_old_pack_cus);
        setTitle("My Old Pack Continuation");
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        paymentBundle= new Bundle();
        textTotalForTheDay =  findViewById(R.id.tittleTotal);
        spnOldPackage =  findViewById(R.id.packageOldMy);
        spnNoOfDays =  findViewById(R.id.SpnNoOfDaysQ);
        txtAmountPerDay =  findViewById(R.id.amountPerDay);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        dbHelper = new DBHelper(this);
        customerDailyReport= new CustomerDailyReport();
        skyLightPackage = new SkyLightPackage();
        String incompleteStatus="inProgress";
        paymentBundle=getIntent().getExtras();
        if(paymentBundle !=null){
            selectedPackage=paymentBundle.getParcelable("Package");
            selectedPackage=paymentBundle.getParcelable("SkyLightPackage");
            skyLightPackage=paymentBundle.getParcelable("Account");
            skyLightPackage=paymentBundle.getParcelable("Customer");
            savingsAmount=paymentBundle.getDouble("Amount");
            spnOldPackage.setVisibility(View.GONE);
            txtAmountPerDay.setVisibility(View.GONE);

        }else{
            if(userProfile !=null) {
                profileID = userProfile.getPID();

                try {
                    skyLightPackageArrayList = dbHelper.getProfileIncompletePack(profileID, incompleteStatus);

                    skyLightPackageArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, skyLightPackageArrayList);
                    skyLightPackageArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnOldPackage.setAdapter(skyLightPackageArrayAdapter);
                    spnOldPackage.setSelection(0);

                    //selectedPackageIndex = spn_select_packageOngoing.getSelectedItemPosition();
                    spnOldPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            try {
                                selectedPackage = (SkyLightPackage) parent.getItemAtPosition(position);
                                savingsAmount = selectedPackage.getPackageDailyAmount();
                                txtAmountPerDay.setText(MessageFormat.format("Amount per day:{0}", savingsAmount));
                            } catch (NullPointerException e) {
                                System.out.println("Oops!");
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // can leave this empty
                        }
                    });

                    spnNoOfDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            packageNoOfDays = parent.getItemAtPosition(position).toString();
                            try {
                                noOfDays = Integer.parseInt(packageNoOfDays);

                            } catch (NumberFormatException e) {
                                System.out.println("Oops!");
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }

                    });

                } catch (SQLiteException e) {
                    System.out.println("Oops!");
                }
            }
            totalAmountSum=noOfDays*savingsAmount;
            textTotalForTheDay.setText("NGN" + totalAmountSum);

        }
        checkInternetConnection();

        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");

        machine=userPreferences.getString(machine,"");




    }
    public void goHome(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putString(machine, machine);
        if(userProfile !=null){
            profileID=userProfile.getPID();

        }
        bundle.putLong("ProfileID", profileID);
        Intent intent = new Intent(this, LoginDirAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtras(bundle);
        startActivity(intent);


    }

    public void addSavingOld(View view) {
    }

    public void payNowOld(View view) {
        checkInternetConnection();
        try {
            if (String.valueOf(packageNoOfDays).isEmpty()) {
                Toast.makeText(OldPackCusAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();


            }else {
                paymentBundle = new Bundle();
                paymentBundle.putString("Total", String.valueOf(totalAmountSum));
                Intent amountIntent = new Intent(OldPackCusAct.this, PayNowActivity.class);
                amountIntent.putExtras(paymentBundle);
                Toast.makeText(OldPackCusAct.this, "Your are paying NGN"+totalAmountSum, Toast.LENGTH_SHORT).show();
                doMySavingsStartForResult.launch(new Intent(amountIntent));

            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }

    }

    @SuppressLint("SetTextI18n")
    protected  void doProcessing() {
        spnOldPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                try {
                    selectedPackage = (SkyLightPackage) parent.getItemAtPosition(position);
                    savingsAmount=selectedPackage.getPackageDailyAmount();
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                // can leave this empty
            }
        });
        spnNoOfDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                packageNoOfDays = parent.getItemAtPosition(position).toString();
                try {
                    noOfDays = Integer.parseInt(packageNoOfDays);

                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        totalAmountSum=noOfDays*savingsAmount;

        if (selectedPackage != null){
            try {
                try {
                    oldPackageId=selectedPackage.getRecordPackageId();
                    packageDuration = selectedPackage.getPackageDuration();
                    grandTotal = selectedPackage.getPackageTotalAmount();
                    packageCount = customerDailyReport.getPackageCount(oldPackageId);
                    savingsAmount = selectedPackage.getPackageDailyAmount();
                    daysRemaining = selectedPackage.getRecordRemainingDays();
                    amountContributedSoFar = selectedPackage.getPackageAmount_collected();
                    newAmountContributedSoFar = amountContributedSoFar + totalAmountSum;
                    newAmountRemaining = grandTotal - newAmountContributedSoFar;
                    newDaysRemaining = packageDuration - daysRemaining - numberOfDays;
                    newBalance = account1.getAccountBalance() + totalAmountSum;

                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

                try {
                    totalAmountSum = savingsAmount * numberOfDays;
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }
                account = selectedPackage.getAccount();
                if (account != null) {
                    newBalance = account.getAccountBalance() + totalAmountSum;
                    acctID = account1.getSkyLightAcctNo();


                }
                customerDailyReport = new CustomerDailyReport(reportID, savingsAmount, numberOfDays, newTotal, newDaysRemaining, newAmountRemaining, reportDate, "in progress");

                String status = "inProgress";
                Calendar calendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                String reportDate = mdformat.format(calendar.getTime());
                String timelineTittle1 = "New Savings alert";
                String timelineDetails1 = "NGN" + newTotal + "was saved for" + lastNameOfCustomer + "," + firstNameOfCustomer;


                String timelineDetails2 = "NGN" + newTotal + "was saved for" + lastNameOfCustomer + "," + firstNameOfCustomer + "on" + reportDate;

                try {
                    for (int i = 0; i < customerDailyReports.size(); i++) {
                        if (String.valueOf(savingsAmount).equals(String.valueOf(customerDailyReports.get(i).getRecordAmount())) &&
                                String.valueOf(numberOfDays).equals(String.valueOf(customerDailyReports.get(i).getRecordNumberOfDays())) &&
                                reportDate.equals(customerDailyReports.get(i).getRecordDate())) {
                            Toast.makeText(OldPackCusAct.this, "a very similar Report already exist!", Toast.LENGTH_LONG).show();

                        }
                        if ( String.valueOf(numberOfDays).isEmpty()) {
                            Toast.makeText(OldPackCusAct.this, "Number of days can not be empty", Toast.LENGTH_SHORT).show();


                        } else {
                            if (newAmountContributedSoFar < newGrandTotal) {
                                btn_backTo_dashboard.setVisibility(View.VISIBLE);
                                skyLightPackage.addSavings(profileID, customerID, reportID, savingsAmount, numberOfDays, newTotal, newDaysRemaining, newAmountRemaining, reportDate, status);
                                customer.addCusNewSavings(packageID, reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, reportDate, "In progress");
                                customer.addCusTimeLine(timelineTittle1, timelineDetails2);
                                userProfile.addTimeLine(timelineTittle1, timelineDetails2);
                                userProfile.addSavings(profileID, customerId, reportID, savingsAmount, numberOfDays, newTotal, newDaysRemaining, newAmountRemaining, reportDate, "In progress");

                                customer.getCusAccount().setAccountBalance(newBalance);
                                skyLightPackage.setPackageBalance(newAmountContributedSoFar);
                                skyLightPackage.addProfileManager(userProfile);
                                account1.setAccountBalance(newBalance);
                                skyLightPackage.setPackageAmount_collected(newAmountContributedSoFar);
                                skyLightPackage.setDocStatus("inComplete");

                                /*try {

                                    databaseSavings = FirebaseDatabase.getInstance().getReference("SavingsPackageInfo").child("Savings");
                                    databaseSavings.child("profileID").setValue(profileID);
                                    databaseSavings.child("customerID").setValue(customerID);
                                    databaseSavings.child("packageID").setValue(packageID);
                                    databaseSavings.child("reportID").setValue(reportID);
                                    databaseSavings.child("packageType").setValue(packageType);
                                    databaseSavings.child("savingsAmount").setValue(savingsAmount);
                                    databaseSavings.child("numberOfDays").setValue(numberOfDays);
                                    databaseSavings.child("newTotal").setValue(newTotal);
                                    databaseSavings.child("newDaysRemaining").setValue(newDaysRemaining);
                                    databaseSavings.child("newAmountRemaining").setValue(newAmountRemaining);
                                    databaseSavings.child("packageDuration").setValue(packageDuration);
                                    databaseSavings.child("reportDate").setValue(reportDate);
                                    databaseSavings.child("grandTotal").setValue(grandTotal);
                                    databaseSavings.child("DateEnded").setValue("");
                                    databaseSavings.child("Status").setValue("New Package");


                                } catch (DatabaseException e) {
                                    System.out.println("Oops!");
                                }*/


                                try {

                                    dbHelper.insertTimeLine(timelineTittle1, timelineDetails1, reportDate, mCurrentLocation);
                                    dbHelper.insertDailyReport(packageID, reportID, profileID, customerID, reportDate, savingsAmount, numberOfDays, newTotal, newAmountContributedSoFar, newAmountRemaining, newDaysRemaining, status);

                                } catch (SQLiteException e) {
                                    System.out.println("Oops!");
                                }

                                    /*Bundle paymentBundle = new Bundle();
                                    paymentBundle.putLong("Package Id", oldPackageId);
                                    paymentBundle.putString("Account ID", profileSurname);
                                    paymentBundle.putString("Date", reportDate);
                                    paymentBundle.putString("Report ID", String.valueOf(reportID));
                                    paymentBundle.putString("Plan Code", "");
                                    paymentBundle.putString("Access Code", "");
                                    paymentBundle.putString("Amount", String.valueOf(savingsAmount));
                                    paymentBundle.putString("Number of Days", String.valueOf(numberOfDays));
                                    paymentBundle.putString("Total", String.valueOf(newTotal));
                                    paymentBundle.putString("Day Remaining", String.valueOf(newDaysRemaining));
                                    paymentBundle.putString("Amount Remaining", String.valueOf(newAmountRemaining));
                                    paymentBundle.putString("Package Count", "");
                                    paymentBundle.putString("Grand Total", String.valueOf(grandTotal));
                                    paymentBundle.putString("Amount So Far", String.valueOf(moneySaved));
                                    paymentBundle.putString("Customer Name", lastNameOfCustomer + "," + firstNameOfCustomer);
                                    paymentBundle.putString("Customer ID", String.valueOf(customerID));
                                    paymentBundle.putString("Customer Email", emailAddress);
                                    paymentBundle.putString("Customer Phone Number", phoneNo);
                                    //Intent intent = new Intent(this, PayNowActivity.class);
                                    //intent.putExtras(paymentBundle);*/
                            }
                            if (String.valueOf(newAmountContributedSoFar).equals(String.valueOf(grandTotal))) {
                                Toast.makeText(OldPackCusAct.this, "This package is complete,start new savings", Toast.LENGTH_SHORT).show();


                                try {

                                    dbHelper.overwriteCustomerPackage(skyLightPackage, selectedCustomer, customerDailyReport, account1);
                                    dbHelper.updatePackage(customerID, oldPackageId, 0, "Completed");
                                    dbHelper.getPackageReminder(String.valueOf(oldPackageId));

                                    String paymentMessage = "Skylight! your package :" + oldPackageId + "" + "is Complete";
                                    Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
                                    Message message = Message.creator(
                                            new com.twilio.type.PhoneNumber(phoneNo),
                                            new com.twilio.type.PhoneNumber("234" + phoneNo),
                                            paymentMessage).create();
                                } catch (SQLiteException e) {
                                    System.out.println("Oops!");
                                }

                            }

                        }


                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
}