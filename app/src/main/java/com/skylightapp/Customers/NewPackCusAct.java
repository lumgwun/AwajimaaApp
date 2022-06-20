package com.skylightapp.Customers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.skylightapp.SuperAdmin.AdminBalance;
import com.skylightapp.Tellers.TellerCash;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Transactions.OurConfig.TWILIO_ACCOUNT_SID;
import static com.skylightapp.Transactions.OurConfig.TWILIO_AUTH_TOKEN;

public class NewPackCusAct extends AppCompatActivity {
    public final static String DAILY_SAVINGS_CONT_ID = "KEY_EXTRA_PACKAGE_ID";
    private Button btn_add_New_Savings,  btn_payNow;

    AppCompatEditText edt_Amount;
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
    Account account1 ;

    String packageStatus;

    //int profileID;

    private ArrayAdapter<Account> accountAdapter;
    private ArrayAdapter<SkyLightPackage> skyLightPackageArrayAdapter;
    private ArrayList<Account> accountArrayList;
    private ArrayList<SkyLightPackage> skyLightPackageArrayList;
    private List<SkyLightPackage> skyLightPackageList;

    private SharedPreferences userPreferences;
    private Gson gson;
    private String json,phoneNo;
    LatLng cusLatLng;
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
    int profileID, packageID,oldPackageId,reportID;

    int packageId,newPackageCount,daysRemaining,packageDuration,numberOfMonths;
    String reportDate,packageNoOfDays,machine,selectedState,selectedGender,selectedStatus,selectedOffice;
    String surName, firstName, emailAddress, phoneNumber1, invDates;
    long virtualAccountID ,customerID1,profileID1;


    String stringNoOFMonths;
    Button btnAddNewCus;
    private Marker m;
    CustomerDailyReport customerDailyReport;
    Bundle paymentBundle;
    SQLiteDatabase sqLiteDatabase;
    int selectedCustomerIndex,selectedPackageIndex;
    Button btn_MyCustomers, btn_AllOurCustomers;
    private static boolean isPersistenceEnabled = false;
    String amtConverted;


    private LinearLayoutCompat layout_month,layout_total,layout_button,layout_days,layout_amount,layout_type_package,layout_old_package,layout_select_my_cust, layout_select_customer,layout_sub_status;

    AppCompatTextView totalForTheDay;
    public static final String ACCOUNT_SID = System.getenv("AC5e05dc0a793a29dc1da2eabdebd6c28d");
    public static final String AUTH_TOKEN = System.getenv("39410e8b813c131da386f3d7bb7f94f7");
    private static final int SPINNER_OPTION_FIRST = 0;
    private static final int SPINNER_OPTION_SECOND = 1;
    AppCompatSpinner spnNoOfMonths,spnNoOfDays;
    TextView totalDurationDays,txtTotalForToday;
    int intNoOfMonths;
    int durationInDays;
    int noOfDaysInt;
    double amountDouble;
    Bundle packageTypeBundle;
    TextWatcher textWatcher;
    long skylightCode,tellerCashCode;
    String noOfDaysString,officeBranch;

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    com.skylightapp.Classes.Transaction Skylightransaction;
    AdminBalance adminBalance;
    String transactionID,tellerSurName,selectedItem,tellerOffice,tellerFirstName,tellerName;
    TellerCash tellerCash;
    AppCompatSpinner spnSavingsPlan, spnFoodAndItem, spnInvestment,spnTypeOfPackage,spnPromo;
    LinearLayoutCompat layoutSavings,layoutDuration;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String PREF_NAME = "skylight";
    com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type;
    String investStringEndDate,selectedPromoPack,packageEndDate,invMaturityDate,newPackageType,selectedFoodStuff,selectedItemType,finalItemType,selectedInvestmentType;
    LinearLayoutCompat layoutInvestment, layoutFoodItemPurchase,layoutPackageType,layoutPromo;
    ActivityResultLauncher<Intent> savingsStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(NewPackCusAct.this, "Payment returned successful", Toast.LENGTH_SHORT).show();
                            doProcessing(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, skyLightPackage, dbHelper, Skylightransaction, customerDailyReport, totalAmountSum, transaction_type);
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(NewPackCusAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
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
    ActivityResultLauncher<Intent> mStandingOrder = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent data = result.getData();
                            /*if (data != null) {
                                pictureUri = data.getData();
                                Picasso.get().load(pictureUri).into(photoPrOP);
                            }*/
                            Toast.makeText(NewPackCusAct.this, "successful ", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(NewPackCusAct.this, "cancelled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_new_pack_cus);
        random = new SecureRandom();
        packageTypeBundle= new Bundle();
        userProfile=new Profile();
        layoutPackageType=  findViewById(R.id.type_Layout);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        skyLightPackage= new SkyLightPackage();
        tellerCash= new TellerCash();
        edt_Amount =  findViewById(R.id.NewAmt);
        totalDurationDays=  findViewById(R.id.totalDuration);
        spnNoOfMonths =  findViewById(R.id.numberOfMonths);
        spnNoOfDays =  findViewById(R.id.numberOfDDays);
        txtTotalForToday =  findViewById(R.id.totalForToday);
        btn_add_New_Savings = findViewById(R.id.add_savingsNew);
        btn_payNow = findViewById(R.id.pay_now_New);
        edt_Amount.addTextChangedListener(textWatcher);
        spnTypeOfPackage=  findViewById(R.id.cus_pack_Type);
        layoutDuration=  findViewById(R.id.month_durationX);


        layoutSavings =  findViewById(R.id._savings_Layout);
        spnSavingsPlan =  findViewById(R.id._savings_plans);

        layoutFoodItemPurchase =  findViewById(R.id._food_Stuff_Layout);
        spnFoodAndItem =  findViewById(R.id._foodStuff_Item);

        layoutInvestment=  findViewById(R.id.cus_inv_Layout);
        spnInvestment=  findViewById(R.id.cus_inv_Type);

        layoutPromo=  findViewById(R.id._promo_Stuff_Layout);
        spnPromo=  findViewById(R.id._promoStuff);
        customer= new Customer();
        PaymentCode paymentCode=new PaymentCode();
        AdminBalance adminBalance= new AdminBalance();
        skylightCode = random.nextInt((int) (Math.random() * 10180) + 12341);
        tellerCashCode = random.nextInt((int) (Math.random() * 20089) + 12041);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        if(userProfile !=null){
            officeBranch=userProfile.getProfileOffice();
            customer=userProfile.getTimelineCustomer();
        }

        packageTypeBundle = getIntent().getExtras() ;
        if(packageTypeBundle !=null){
            packageType=packageTypeBundle.getString("PackageType");
            //txtTypeOfPackage.setText(MessageFormat.format("Package Type{0}", packageType));

        }else{
            layoutPackageType.setVisibility(View.VISIBLE);

            spnTypeOfPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    packageType = spnTypeOfPackage.getSelectedItem().toString();
                    packageType = (String) parent.getSelectedItem();
                    Toast.makeText(NewPackCusAct.this, "Selected Type: "+ packageType,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            packageType = spnTypeOfPackage.getSelectedItem().toString();
        }
        if(customer !=null){
            cusLatLng=customer.getCusLocation();
        }
        packageType = spnTypeOfPackage.getSelectedItem().toString();

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar calendar2 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar calendar3 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.add(Calendar.DAY_OF_YEAR, 31);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar2.add(Calendar.DAY_OF_YEAR, 372);

        calendar3.add(Calendar.DAY_OF_YEAR, 403);

        Date maturityDate = calendar3.getTime();
        //String maturityString = sdf.format(maturityDate);

        Date investmentEndDate = calendar2.getTime();
        investStringEndDate = sdf.format(investmentEndDate);
        invMaturityDate = sdf.format(maturityDate);

        invDates=investStringEndDate+"//"+invMaturityDate;

        Date newDate = calendar.getTime();
        String savingsEndDate = sdf.format(newDate);

        mAuth = FirebaseAuth.getInstance();

        try {
            profileID = userPreferences.getInt("PROFILE_ID", 0);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        tellerOffice = userPreferences.getString("CHOSEN_OFFICE", "");
        machine=userPreferences.getString(machine,"");
        checkInternetConnection();
        //userProfile= new Profile();
        customerDailyReport= new CustomerDailyReport();
        dbHelper = new DBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        Twilio.init("AC5e05dc0a793a29dc1da2eabdebd6c28d", "39410e8b813c131da386f3d7bb7f94f7");

        spnNoOfDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                packageNoOfDays = parent.getItemAtPosition(position).toString();
                try {
                    noOfDaysInt = Integer.parseInt(packageNoOfDays);

                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        spnNoOfMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                stringNoOFMonths = adapterView.getItemAtPosition(i).toString();
                //stringNoOFMonths = spnNoOfMonths.getSelectedItem().toString();
                //numberOfMonths=Integer.parseInt(stringNoOFMonths.trim());
                try {
                    intNoOfMonths = Integer.parseInt(stringNoOFMonths);
                    durationInDays=intNoOfMonths*31;
                    totalDurationDays.setText(MessageFormat.format("Duration in days:{0}", durationInDays));

                    numberOfMonths=Integer.parseInt(stringNoOFMonths.trim());
                    noOfDaysInt=numberOfMonths*31;
                    totalDurationDays.setText(MessageFormat.format("Durations in days{0}", noOfDaysInt));

                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!TextUtils.isEmpty(edt_Amount.getText().toString().trim())

                ) {

                    //noOfDaysInt
                    int answer = Integer.parseInt(edt_Amount.getText().toString().trim()) *
                            Integer.parseInt(noOfDaysString.trim());

                    Log.e("RESULT", String.valueOf(answer));
                    txtTotalForToday.setText(MessageFormat.format("Total N{0}", String.valueOf(answer)));
                }else {
                    txtTotalForToday.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };


        skyLightPackageArrayList = new ArrayList<SkyLightPackage>();


        if(packageType.equalsIgnoreCase("Item Purchase")){
            layoutSavings.setVisibility(View.GONE);
            layoutFoodItemPurchase.setVisibility(View.VISIBLE);
            layoutInvestment.setVisibility(View.GONE);
            layoutPromo.setVisibility(View.GONE);
            packageDuration=durationInDays;

        }
        if(packageType.equalsIgnoreCase("Promo")){
            layoutPromo.setVisibility(View.VISIBLE);
            layoutSavings.setVisibility(View.GONE);
            layoutInvestment.setVisibility(View.GONE);
            layoutFoodItemPurchase.setVisibility(View.GONE);
            packageDuration=durationInDays;


        }
        if(packageType.equalsIgnoreCase("Investment")){
            layoutInvestment.setVisibility(View.VISIBLE);
            layoutSavings.setVisibility(View.GONE);
            layoutPromo.setVisibility(View.GONE);
            layoutFoodItemPurchase.setVisibility(View.GONE);
            layoutDuration.setVisibility(View.GONE);
            packageDuration=372;
            totalDurationDays.setText(MessageFormat.format("372 saving days,End Date:{0}",investmentEndDate +"/"+""+invMaturityDate));

        }
        if(packageType.equalsIgnoreCase("Savings")){
            layoutSavings.setVisibility(View.VISIBLE);
            layoutFoodItemPurchase.setVisibility(View.GONE);
            layoutPromo.setVisibility(View.GONE);
            layoutInvestment.setVisibility(View.GONE);
            packageDuration=31;
            layoutDuration.setVisibility(View.GONE);
            packageEndDate=null;
        }

        spnSavingsPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemType = spnSavingsPlan.getSelectedItem().toString();
                selectedItemType = (String) parent.getSelectedItem();
                finalItemType=selectedItemType;
                Toast.makeText(NewPackCusAct.this, "Item Selected: "+ selectedItemType,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        spnFoodAndItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFoodStuff = spnFoodAndItem.getSelectedItem().toString();
                selectedFoodStuff = (String) parent.getSelectedItem();
                finalItemType=selectedFoodStuff;
                Toast.makeText(NewPackCusAct.this, "Selected Food Stuff: "+ selectedFoodStuff,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnInvestment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedInvestmentType = spnInvestment.getSelectedItem().toString();
                selectedInvestmentType = (String) parent.getSelectedItem();
                finalItemType=selectedInvestmentType;
                Toast.makeText(NewPackCusAct.this, "Selected Investment: "+ selectedInvestmentType,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnPromo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPromoPack = spnPromo.getSelectedItem().toString();
                selectedPromoPack = (String) parent.getSelectedItem();
                finalItemType=selectedPromoPack;
                Toast.makeText(NewPackCusAct.this, "Selected Promo: "+ selectedPromoPack,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        totalAmountSum=noOfDaysInt* amountDouble;
        txtTotalForToday.setText("Total for today NGN:"+totalAmountSum);
        btn_add_New_Savings.setOnClickListener(this::addNewSavings);
        btn_add_New_Savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savingsAmount = Double.parseDouble(edt_Amount.getText().toString().trim());
                packageID = random.nextInt((int) (Math.random() * 1240) + 14060);
                reportID = random.nextInt((int) (Math.random() * 23) + 1561);



                packageTypeBundle = getIntent().getExtras();
                if(packageTypeBundle !=null){
                    packageType=packageTypeBundle.getString("PackageType");

                }
                if(packageType.equalsIgnoreCase("Savings")){
                    transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.SAVINGS;

                }
                if(packageType.equalsIgnoreCase("Item Purchase")){
                    transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.ITEM_PURCHASE;

                }
                if(packageType.equalsIgnoreCase("Promo")){
                    transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.PROMO;

                }
                if(packageType.equalsIgnoreCase("Investment")){
                    transaction_type= Transaction.TRANSACTION_TYPE.IVESTMENT;

                }
                Skylightransaction= new com.skylightapp.Classes.Transaction();
                if (userProfile != null) {
                    officeBranch=userProfile.getProfileOffice();

                }



                try {
                    totalAmountSum = savingsAmount*numberOfDays;

                    totalForTheDay.setText("NGN"+totalAmountSum);
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }


                try {
                    grandTotal = packageDuration*savingsAmount;
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

                try {
                    totalAmountSum = savingsAmount*numberOfDays;
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }
                try {
                    if (amountDouble<=0.00) {
                        Toast.makeText(NewPackCusAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();


                    }
                    if (stringNoOFMonths.isEmpty()) {
                        Toast.makeText(NewPackCusAct.this, "The Number for month, must be selected", Toast.LENGTH_LONG).show();


                    }
                    if (noOfDaysString.isEmpty()) {
                        Toast.makeText(NewPackCusAct.this, "Number of days must be selected", Toast.LENGTH_LONG).show();


                    }else {
                        skyLightPackage = new SkyLightPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, "fresh");
                        customerDailyReport = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, reportDate, "new");

                        doProcessing(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate,skyLightPackage,dbHelper,Skylightransaction,customerDailyReport,totalAmountSum,transaction_type);


                    }
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }


            }
        });
        btn_payNow.setOnClickListener(this::payDialoqNew);
        btn_payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savingsAmount = Double.parseDouble(edt_Amount.getText().toString().trim());
                packageID = random.nextInt((int) (Math.random() * 1240) + 14060);
                reportID = random.nextInt((int) (Math.random() * 23) + 1561);


                com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type=null;
                packageTypeBundle = getIntent().getExtras();
                if(packageTypeBundle !=null){
                    packageType=packageTypeBundle.getString("PackageType");

                }
                if(packageType.equalsIgnoreCase("Savings")){
                    transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.SAVINGS;

                }
                if(packageType.equalsIgnoreCase("Item Purchase")){
                    transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.ITEM_PURCHASE;

                }
                if(packageType.equalsIgnoreCase("Promo")){
                    transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.PROMO;

                }
                if(packageType.equalsIgnoreCase("Investment")){
                    transaction_type= Transaction.TRANSACTION_TYPE.IVESTMENT;

                }
                Skylightransaction= new com.skylightapp.Classes.Transaction();
                if (userProfile != null) {
                    officeBranch=userProfile.getProfileOffice();

                }



                try {
                    totalAmountSum = savingsAmount*numberOfDays;

                    totalForTheDay.setText("NGN"+totalAmountSum);
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }


                try {
                    grandTotal = packageDuration*savingsAmount;
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

                try {
                    totalAmountSum = savingsAmount*numberOfDays;
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }
                try {
                    if (amountDouble<=0.00) {
                        Toast.makeText(NewPackCusAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();


                    }
                    if (stringNoOFMonths.isEmpty()) {
                        Toast.makeText(NewPackCusAct.this, "The Number for month, must be selected", Toast.LENGTH_LONG).show();


                    }
                    if (noOfDaysString.isEmpty()) {
                        Toast.makeText(NewPackCusAct.this, "Number of days must be selected", Toast.LENGTH_LONG).show();


                    }else {
                        customerDailyReport = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, reportDate, "new");

                        skyLightPackage = new SkyLightPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, "fresh");
                        try {
                            paymentBundle = new Bundle();
                            paymentBundle.putString("Total", String.valueOf(totalAmountSum));
                            Intent amountIntent = new Intent(NewPackCusAct.this, PayNowActivity.class);
                            amountIntent.putExtras(paymentBundle);
                            Toast.makeText(NewPackCusAct.this, "Your are paying NGN"+totalAmountSum, Toast.LENGTH_SHORT).show();
                            savingsStartForResult.launch(new Intent(amountIntent));

                        } catch (NumberFormatException e) {
                            System.out.println("Oops!");
                        }


                    }
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }
        });




    }




    public void goHome(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putLong(PROFILE_ID, profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }



    public void payDialoqNew(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Payment Method");
        builder.setItems(new CharSequence[]
                        {"Manual", "Standing Order Plan Method"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Toast.makeText(NewPackCusAct.this, "Manual Payment option, selected ", Toast.LENGTH_SHORT).show();
                                //doProcessing(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, skyLightPackage, dbHelper, Skylightransaction, customerDailyReport, totalAmountSum);
                                payNowNew();
                                break;
                            case 1:
                                /*Intent amountIntent = new Intent(NewPackCustomerAct.this, SavingsStandingOrder.class);
                                //amountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                Toast.makeText(NewPackCustomerAct.this, "Standing Order Choice, made", Toast.LENGTH_SHORT).show();
                                //paymentBundle = new Bundle();
                                //paymentBundle.putString("Total", String.valueOf(totalAmountSum));*/


                                Intent standingOrderIntent = new Intent(NewPackCusAct.this, StandingOrderTab.class);
                                standingOrderIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mStandingOrder.launch(standingOrderIntent);
                                //amountIntent.putExtras(paymentBundle);
                                break;
                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.create().show();

    }
    public void payNowNew() {
        checkInternetConnection();
        try {
            paymentBundle = new Bundle();
            paymentBundle.putString("Total", String.valueOf(totalAmountSum));
            Intent amountIntent = new Intent(NewPackCusAct.this, PayNowActivity.class);
            amountIntent.putExtras(paymentBundle);
            Toast.makeText(NewPackCusAct.this, "Your are paying NGN"+totalAmountSum, Toast.LENGTH_SHORT).show();
            savingsStartForResult.launch(new Intent(amountIntent));

        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
    }

    @SuppressLint("SetTextI18n")
    protected  void doProcessing(int profileID, int customerID, int packageID, String finalItemType, String packageType, double savingsAmount, int packageDuration, String reportDate, double grandTotal, String officeBranch, String packageEndDate, SkyLightPackage skyLightPackage, DBHelper dbHelper, Transaction skylightransaction, CustomerDailyReport customerDailyReport, double totalAmountSum, Transaction.TRANSACTION_TYPE transaction_type){
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);        gson = new Gson();
        random= new SecureRandom();
        account1=customer.getCusAccount();
        if(account1 !=null){
            packageAccountBalance=account1.getAccountBalance()+ totalAmountSum;

        }
        sqLiteDatabase = dbHelper.getWritableDatabase();

        amountContributedSoFar = totalAmountSum;
        grandTotal = packageDuration * savingsAmount;
        newAmountRemaining= grandTotal -amountContributedSoFar;
        newDaysRemaining= packageDuration -numberOfDays;

        String status3 = "Subscription in progress";
        String status1 = "Savings Unconfirmed";
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        reportDate = mdformat.format(calendar.getTime());
        String timelineTittle3 = "New Savings Package alert";
        String timelineDetails3 = "NGN" + grandTotal + " package was initiated for" + surName + "," + firstName;


        String timelineDetails5 = "NGN" + newTotal + "was saved for" + surName + "," + firstName + "on" + reportDate;

        packageType = (spn_type_of_package.getSelectedItem().toString());
        String customerName = surName + "," + firstName + "with Phone No:" + phoneNumber1;

        customerNames = customer.getCusSurname() + "," + customer.getCusFirstName();
        emailAddress = customer.getCusEmailAddress();
        String managerName = userProfile.getProfileLastName() + "," + userProfile.getProfileFirstName();
        skylightCode = random.nextInt((int) (Math.random() * 20089) + 22341);
        String transactionID="Skylight"+skylightCode;

        String tittle = "New Package Alert" + "from" + managerName;
        String details = managerName + "added a new package of NGN" + newTotal + "for" + customerName + "on" + reportDate;

        SkyLightPackage skyLightPackage1 = new SkyLightPackage(profileID, customerID, packageID, packageType, finalItemType, savingsAmount, packageDuration, reportDate, grandTotal, "", "Just started");
        skyLightPackage1.addSavings(profileID, customerID, reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, reportDate, status1);
        customerDailyReport = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, reportDate, status1);
        if(customer !=null){
            customer.addCusNewSavings(packageID,reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, reportDate, status1);
            customer.addCusNewSkylightPackage(profileID, customerID, packageID, packageType, savingsAmount, packageDuration, reportDate, grandTotal, "","just stated");
            customer.getCusAccount().setAccountBalance(packageAccountBalance);
            customer.addCusTimeLine(timelineTittle3, timelineDetails3);
            phoneNumber1 = customer.getCusPhoneNumber();
        }

        //skyLightPackage1.addProfileManager(userProfile);
        skyLightPackage1.setAmount_collected(newAmountContributedSoFar);
        skyLightPackage1.addReportCount(packageID, 1);
        if(userProfile !=null){
            userProfile.addNewSkylightPackage(profileID, customerID, packageID, packageType, savingsAmount, this.packageDuration, reportDate, grandTotal, "","just stated");
        }
        //Skylightransaction= new com.skylightapp.Classes.Transaction(transactionID, accountNo, this.reportDate, "Skylight", String.valueOf(account1), "Skylight", customerNames, this.totalAmountSum, transaction_type, "",officeBranch, "", "", "");

        sendSMSMessage(customerNames,amountContributedSoFar,skylightCode);

        String paymentMessage = "Congratulations" + customerNames + "as you start a new Package, may you achieve big big things with us";

        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
            //dbHelper = new DBHelper(this);
            sqLiteDatabase = dbHelper.getWritableDatabase();
            dbHelper.insertTimeLine(tittle, details, reportDate, mCurrentLocation);
            dbHelper.insertTimeLine(timelineTittle3, details, reportDate, mCurrentLocation);
            dbHelper.saveNewTransaction(profileID, customerID,Skylightransaction, acctID, "Skylight", customerNames,transaction_type, totalAmountSum, reportID, officeBranch, reportDate);
            dbHelper.insertNewPackage(userProfile, customer,  skyLightPackage1);
            dbHelper.saveNewAdminBalance(acctID, profileID, customerID, packageID, savingsAmount, reportDate,"Unconfirmed");
            dbHelper.insertDailyReport(packageID,reportID, profileID, customerID, reportDate, savingsAmount,numberOfDays,newTotal,newAmountContributedSoFar,newAmountRemaining,newDaysRemaining,"First Report");



        }


        sendSMS22(phoneNumber1, paymentMessage);

        Toast.makeText(NewPackCusAct.this, "Your have successfuly added a savings of NGN"+ this.totalAmountSum, Toast.LENGTH_SHORT).show();




    }
    protected void sendSMS22(String uPhoneNumber, String paymentMessage) {
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(uPhoneNumber),
                new com.twilio.type.PhoneNumber("+15703251701"),
                paymentMessage)
                .create();
    }



    protected void sendSMSMessage(String customerNames, double amountContributedSoFar, long skylightCode) {
        Bundle smsBundle= new Bundle();
        String smsMessage="The Code for" + ""+ customerNames +""+ "for package"+ ""+packageID +","+"N"+amountContributedSoFar +""+"is"+""+ skylightCode;
        smsBundle.putString(PROFILE_PHONE, "uPhoneNumber");
        smsBundle.putString("USER_PHONE", "uPhoneNumber");
        smsBundle.putString("smsMessage",smsMessage);
        smsBundle.putString("from","+15703251701");
        smsBundle.putString("to", "uPhoneNumber");
        Intent itemPurchaseIntent = new Intent(NewPackCusAct.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

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



    public void addNewSavings(View view) {
    }

}