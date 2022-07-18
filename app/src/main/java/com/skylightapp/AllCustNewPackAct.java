package com.skylightapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

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
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.gson.Gson;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.SuperAdmin.AdminBalance;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Tellers.TellerCash;
import com.skylightapp.Tellers.TellerHomeChoices;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;

public class AllCustNewPackAct extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener, AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener{
    private AppBarLayout appBarLayout;
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    int profileUID2;
    long skylightCode,tellerCashCode;
    Customer customer;
    TellerCash tellerCash;
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
    AppCompatSpinner spnNumberOfDays;
    AppCompatSpinner spn_old_customers;
    private Calendar mCalendar;
    protected DBHelper dbHelper;

    Customer selectedCustomer;
    Account account1 ;


    private LinearLayout dotsLayout;


    private ArrayAdapter<Account> accountAdapter;
    private ArrayAdapter<Customer> customerArrayAdapter;
    private ArrayAdapter<Customer> customerArrayAdapterN;
    private ArrayAdapter<SkyLightPackage> skyLightPackageArrayAdapter;
    private ArrayAdapter<SkyLightPackage> skyLightPackageAllAdapter;
    private ArrayList<Customer> customerArrayList;
    private ArrayList<Account> accountArrayList;
    private ArrayList<CustomerDailyReport> customerDailyReports;
    private List<Customer> customerList;
    private ArrayList<SkyLightPackage> skyLightPackageArrayList;
    private ArrayList<SkyLightPackage> skyLightPackageAll;
    private ArrayList<Customer> customers;
    private ArrayList<Customer> customersN;
    private List<SkyLightPackage> skyLightPackageList;

    private SharedPreferences userPreferences;
    private String phoneNo;
    //private Spinner accountSpinner, genderSpinner, stateSpinner, number_of_day_spinner, purposeSpinner, months;

    DatePickerDialog datePickerDialog;
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
    TextView c;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String packageStatus;
    private Location previousLocation;
    private GoogleMap mMap;

    SecureRandom random;
    DatePicker picker;
    Random ran ;
    DatePicker date_picker_dob;

    String packageType,dateOfSavings;
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
    double totalAmountCalc;
    double savingsAmount;
    double newGrandTotal;
    double newAmountRemaining;
    private int newDaysRemaining,numberOfDays;
    AccountTypes accountTypeStr;
    String interestRate, firstNameOfCustomer,profileSurname,packageStartDate, profilePhone,lastNameOfCustomer,profileFirstName;
    String customerNames;
    Context context;
    int accountNo,acctID;
    int customerID;
    double amountContributedSoFar,newAmountContributedSoFar;
    double packageGrantTotal,amountRemaining,moneySaved;
    int profileID, oldPackageId;
    private static boolean isPersistenceEnabled = false;

    int packageId,newPackageCount,daysRemaining,packageDuration,numberOfMonths;
    String reportDate,packageTypeS,machine,selectedState,selectedGender,selectedStatus,selectedOffice;
    String surName, firstName, emailAddress, phoneNumber1, address;
    int virtualAccountID ,packageID,customerID1,profileID1,reportID;
    RadioGroup grpRB,grp2;
    int x;
    int y;


    String mDate,packageEndDate;
    Button btnAddNewCus;
    private Marker m;
    CustomerDailyReport customerDailyReport;
    Bundle paymentBundle;
    private static final String PREF_NAME = "skylight";
    SQLiteDatabase sqLiteDatabase;
    int selectedCustomerIndex,selectedPackageIndex;
    Button btn_MyCustomers, btn_AllOurCustomers,useDateToday;
    AppCompatRadioButton checkboxOld, checkboxNew, checkboxCustom,checkboxSavings;
    String managerName,itemPurchase,customerPhone,officeBranch;
    boolean myCustomerButtonClicked;
    boolean allCustomerButtonClicked;
    DatePicker savings_date_;
    AppCompatButton btn_pay_Savings;
    PaymentCode paymentCode;
    private LatLng cusLatLng;
    AdminBalance adminBalance;

    private LinearLayoutCompat layout_month,layout_total,layout_button, layout_Amount,layout_amount,layout_type_package,layout_old_package,layout_select_my_cust, layout_select_customer,layout_sub_status;

    TextView totalForTheDay;
    ScrollView layoutScrollPackage;
    Transaction transaction;
    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");
    boolean isOnTextChanged = true;
    CardView cardViewSelect;

    private FirebaseAuth mAuth;
    TextWatcher textWatcherDuration;
    FirebaseAuth.AuthStateListener mAuthListner;
    private FirebaseAuth.AuthStateListener mAuthListener;
    int durationInDays;
    TextView txtDurationInDays;
    private Bundle extras;
    com.skylightapp.Classes.Transaction Skylightransaction;
    String investStringEndDate,selectedPromoPack,newPackageType,tellerName,selectedFoodStuff,selectedItemType,finalItemType,selectedInvestmentType;
    LinearLayoutCompat layoutInvestment, layoutSavings,layoutFoodItemPurchase,layoutPackageType,layoutPromo;
    AppCompatSpinner spnSavingsPlan, spnFoodAndItem, spnInvestment,spnTypeOfPackage,spnPromo;

    ActivityResultLauncher<Intent> mStartSignUpForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        finish();
                    }
                }
            });
    ActivityResultLauncher<Intent> locActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent amountIntent = new Intent(AllCustNewPackAct.this, TellerHomeChoices.class);
                        amountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                    }
                }
            });

    ActivityResultLauncher<Intent> mStartPaymentUpForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Toast.makeText(AllCustNewPackAct.this, "Payment returned successful", Toast.LENGTH_SHORT).show();
                        dbHelper.updatePackageRecord(profileID, customerID, oldPackageId, reportID, "Paid");
                        finish();
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_cust_new_pack);
        setTitle("New Package onBoarding");
        checkInternetConnection();
        dbHelper = new DBHelper(this);
        customer= new Customer();
        sharedpreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        random= new SecureRandom();
        PaymentCode paymentCode=new PaymentCode();
        selectedPackage= new SkyLightPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, "fresh");
        adminBalance= new AdminBalance();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Skylightransaction= new com.skylightapp.Classes.Transaction();
        extras = getIntent().getExtras();

        customerDailyReport= new CustomerDailyReport();
        tellerCash= new TellerCash();
        skyLightPackage = new SkyLightPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, "fresh");
        sqLiteDatabase = dbHelper.getWritableDatabase();
        skylightCode = random.nextInt((int) (Math.random() * 100089) + 10341);
        btnAddNewCus = findViewById(R.id.add_new_cus);
        btn_pay_Savings = findViewById(R.id.paySavings);
        spn_old_customers = findViewById(R.id.old_customer4);
        edtDurationInMonths =  findViewById(R.id.monthsNo);
        txtDurationInDays =  findViewById(R.id.noInDays);

        edt_Amount =  findViewById(R.id.eachDayAmt);
        edt_NoOfDays=  findViewById(R.id.daysNo);
        totalForTheDay =  findViewById(R.id.daysTotal);
        savings_date_=(DatePicker)findViewById(R.id.savings_date_);
        btn_add_New_Savings = findViewById(R.id.submitSavings);
        customersN = dbHelper.getAllCustomers11();
        try {
            customerArrayAdapterN = new ArrayAdapter<>(AllCustNewPackAct.this, android.R.layout.simple_spinner_item, customersN);
            customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_old_customers.setAdapter(customerArrayAdapterN);
            spn_old_customers.setSelection(customerArrayAdapterN.getPosition(customer));
        }catch (NullPointerException e) {
            //ff
        }


        spnTypeOfPackage=  findViewById(R.id.all_pack_Type);

        layoutSavings =  findViewById(R.id.all_savings_Layout);
        spnSavingsPlan =  findViewById(R.id.all_savings_plans);

        layoutFoodItemPurchase =  findViewById(R.id.all_food_Stuff_Layout);
        spnFoodAndItem =  findViewById(R.id.all_foodStuff_Item);

        layoutInvestment=  findViewById(R.id.all_inv_Layout);
        spnInvestment=  findViewById(R.id.all_inv_Type);

        layoutPromo=  findViewById(R.id.all_promo_Stuff_Layout);
        spnPromo=  findViewById(R.id.all_promoStuff);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar calendar2 = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        Calendar calendarToday = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        calendar.add(Calendar.DAY_OF_YEAR, 31);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendar2.add(Calendar.DAY_OF_YEAR, 372);
        Date investmentEndDate = calendar.getTime();
        investStringEndDate = sdf.format(investmentEndDate);

        Date newDate = calendar.getTime();
        String savingsEndDate = sdf.format(newDate);

        Date todayInDate = calendarToday.getTime();
        String today = sdf.format(todayInDate);
        textWatcherDuration = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!TextUtils.isEmpty(edtDurationInMonths.getText().toString().trim())) {
                    txtDurationInDays.setVisibility(View.VISIBLE);

                    durationInDays = Integer.parseInt(edtDurationInMonths.getText().toString().trim()) * 31;

                    Log.e("Duration in days:", String.valueOf(durationInDays));
                    txtDurationInDays.setText(MessageFormat.format("Duration in days:{0}", String.valueOf(durationInDays)));
                }else {
                    txtDurationInDays.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };


        if(extras !=null){
            packageType=extras.getString("PackageType");
        }else{
            layoutPackageType.setVisibility(View.VISIBLE);

            spnTypeOfPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    packageType = spnTypeOfPackage.getSelectedItem().toString();
                    packageType = (String) parent.getSelectedItem();
                    Toast.makeText(AllCustNewPackAct.this, "Selected Package Type: "+ packageType,Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            packageType = spnTypeOfPackage.getSelectedItem().toString();
        }
        packageType = spnTypeOfPackage.getSelectedItem().toString();

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
            edtDurationInMonths.setVisibility(View.GONE);
            packageDuration=372;
            //txtTotalForTheDay.setText(String.format("Total N%s", totalAmountCalc));


        }
        if(packageType.equalsIgnoreCase("Savings")){
            layoutSavings.setVisibility(View.VISIBLE);
            layoutFoodItemPurchase.setVisibility(View.GONE);
            layoutPromo.setVisibility(View.GONE);
            layoutInvestment.setVisibility(View.GONE);
            packageDuration=31;
            edtDurationInMonths.setVisibility(View.GONE);
            packageEndDate=savingsEndDate;
            txtDurationInDays.setText(MessageFormat.format("Life Cycle:31 days, Temporary End Date:{0}", savingsEndDate));
        }

        spnSavingsPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemType = spnSavingsPlan.getSelectedItem().toString();
                selectedItemType = (String) parent.getSelectedItem();
                finalItemType=selectedItemType;
                Toast.makeText(AllCustNewPackAct.this, "Item Selected: "+ selectedItemType,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AllCustNewPackAct.this, "Selected Food Stuff: "+ selectedFoodStuff,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AllCustNewPackAct.this, "Selected Investment: "+ selectedInvestmentType,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AllCustNewPackAct.this, "Selected Promo: "+ selectedPromoPack,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //selectedCustomerIndex = spn_old_customers.getSelectedItemPosition();

        spn_old_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                customer = (Customer) parent.getSelectedItem();
                Toast.makeText(AllCustNewPackAct.this, "Customer's ID: " + customer.getCusUID() + ",  Customer's Name : " + customer.getCusFirstName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnAddNewCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartSignUpForResult.launch(new Intent(AllCustNewPackAct.this, SignUpAct.class));

            }
        });
        if(customer !=null){
            officeBranch=customer.getCusOfficeBranch();
        }


        savings_date_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
        dateOfSavings = savings_date_.getDayOfMonth()+"/"+ (savings_date_.getMonth() + 1)+"/"+savings_date_.getYear();


        edt_Amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                isOnTextChanged = true;
            }
            @Override
            public void afterTextChanged(Editable editable) {
                totalAmountCalc = 0;
                if (isOnTextChanged) {
                    isOnTextChanged = false;
                    try {
                        totalAmountCalc = 0;
                        savingsAmount = Double.parseDouble(Objects.requireNonNull(edt_Amount.getText()).toString());
                        numberOfDays = Integer.parseInt(Objects.requireNonNull(edt_NoOfDays.getText()).toString());
                        totalAmountCalc = savingsAmount * numberOfDays;

                        totalForTheDay.setText(MessageFormat.format("Total for Today: NGN {0}", String.valueOf(totalAmountCalc)));
                    }catch (NumberFormatException e)
                    {
                        totalForTheDay.setText(MessageFormat.format("Total for Today: NGN {0}", String.valueOf(totalAmountCalc)));
                    }
                }
            }
        });
        if(dateOfSavings.isEmpty()){
            dateOfSavings=today;
        }
        edt_NoOfDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                isOnTextChanged = true;
            }
            @Override
            public void afterTextChanged(Editable editable) {
                totalAmountCalc = 0;
                if (isOnTextChanged) {
                    isOnTextChanged = false;
                    try {
                        savingsAmount = Double.parseDouble(Objects.requireNonNull(edt_Amount.getText()).toString());
                        numberOfDays = Integer.parseInt(Objects.requireNonNull(edt_NoOfDays.getText()).toString());
                        totalAmountCalc = savingsAmount * numberOfDays;

                        totalForTheDay.setText(MessageFormat.format("Total for Today: NGN {0}", String.valueOf(totalAmountCalc)));
                    }catch (NumberFormatException e)
                    {
                        totalForTheDay.setText(MessageFormat.format("Total for Today: NGN {0}", String.valueOf(totalAmountCalc)));
                    }
                }
            }
        });


        /*if (!isPersistenceEnabled) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            isPersistenceEnabled = true;
        }*/
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
        mAuth = FirebaseAuth.getInstance();
        mauthListener();

        tellerName=ManagerSurname+","+managerFirstName;
        tellerCash= new TellerCash();
        AdminBalance adminBalance= new AdminBalance();
        skylightCode = random.nextInt((int) (Math.random() * 10180) + 12341);
        tellerCashCode = random.nextInt((int) (Math.random() * 20089) + 12041);

        btn_add_New_Savings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doProcessing(packageType,finalItemType,packageDuration,packageEndDate,dateOfSavings,totalAmountCalc,skylightCode,paymentCode,adminBalance,tellerCash,profileID,tellerName,tellerCashCode,officeBranch,customer,adminBalance);

            }
        });
    }

    protected  void doProcessing(String packageType, String finalItemType, int packageDuration, String packageEndDate, String dateOfSavings, double totalAmountCalc, long skylightCode, PaymentCode paymentCode, AdminBalance adminBalance, TellerCash tellerCash, int profileID, String tellerName, long tellerCashCode, String officeBranch, Customer customer, AdminBalance adminBalance1){
        Bundle locBundle = new Bundle();
        long reportCode = random.nextInt((int) (Math.random() * 10089) + 10340);

        Bundle packageTypeBundle1 = new Bundle();
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if (userProfile != null) {
            customersN = userProfile.getProfileCustomers();
            officeBranch=userProfile.getProfileOffice();
        }


        com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type=null;
        packageTypeBundle1 = getIntent().getExtras().getBundle("optionBundle") ;
        if(packageTypeBundle1 !=null){
            packageType=packageTypeBundle1.getString("PackageType");

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

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(packageType.equalsIgnoreCase("Savings")){
            packageDuration=31;

        }else {
            try {
                numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());
                packageDuration = numberOfMonths*31;
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }

        }
        packageDuration = numberOfMonths*31;
        calendar.add(Calendar.DAY_OF_YEAR, packageDuration);
        Date newDate = calendar.getTime();
        String endDate = sdf.format(newDate);
        packageEndDate=endDate;
        random= new SecureRandom();
        String transactionID=null;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = mdformat.format(calendar.getTime());
        if(this.dateOfSavings.isEmpty()){
            this.dateOfSavings =reportDate;
        }

        if(selectedPackage ==null){


            try {
                numberOfDays = Integer.parseInt(Objects.requireNonNull(edt_NoOfDays.getText()).toString());
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }
            try {
                savingsAmount = Double.parseDouble(Objects.requireNonNull(edt_Amount.getText()).toString());
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }
            try {
                totalAmountCalc = savingsAmount*numberOfDays;

            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }

            try {
                numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }

            try {
                packageID = random.nextInt((int) (Math.random() * 10000) + 10001);

            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }

            try {
                reportID = random.nextInt((int) (Math.random() * 1029) + 11);
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }


            try {
                packageDuration = numberOfMonths*31;
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }
            try {
                grandTotal = packageDuration*savingsAmount;
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }

            account1=customer.getCusAccount();
            if(account1 !=null){
                packageAccountBalance=account1.getAccountBalance()+ totalAmountCalc;
                acctID=account1.getSkyLightAcctNo();

            }
            transactionID="SkyLight/"+packageID;

            amountContributedSoFar = totalAmountCalc;
            grandTotal = packageDuration*savingsAmount;
            newAmountRemaining=grandTotal-amountContributedSoFar;
            newDaysRemaining=packageDuration-numberOfDays;

            try {
                if (String.valueOf(numberOfDays).isEmpty()) {
                    Toast.makeText(AllCustNewPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();


                }
                if (String.valueOf(savingsAmount).isEmpty()) {
                    Toast.makeText(AllCustNewPackAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();
                }
                else {
                    String status3 = "Subscription in progress";
                    String status1 = "Savings Unconfirmed";
                    reportDate = mdformat.format(calendar.getTime());
                    String timelineTittle3 = "New Package alert";
                    String timelineDetails3 = "NGN" + grandTotal + " package was initiated for" + surName + "," + firstName;
                    double adminCommission=adminBalance.getAdminReceivedBalance();
                    double skylightCommission=100;
                    double initialDeposit= totalAmountCalc -savingsAmount;
                    //double acctBalance=account1.getAccountBalance();
                    double adminNewBalance= adminCommission+savingsAmount;


                    String timelineDetails5 = "A new package of NGN" + grandTotal + "was started" + "on" + reportDate;

                    String customerName = surName + "," + firstName + "with Phone No:" + phoneNumber1;
                    if(customer !=null){
                        customerNames = customer.getCusSurname() + "," + customer.getCusFirstName();
                        emailAddress = customer.getCusEmailAddress();
                        managerName = userProfile.getProfileLastName() + "," + userProfile.getProfileFirstName();
                        officeBranch=customer.getCusOfficeBranch();
                        cusLatLng=customer.getCusLocation();
                        customerPhone=customer.getCusPhoneNumber();

                    }
                    tellerCash=new TellerCash(reportID,profileID,packageID,finalItemType,savingsAmount,tellerName,officeBranch,packageStartDate,tellerCashCode,"UnConfirmed");
                    tellerCash.setTellerCashCode(tellerCashCode);
                    dbHelper.insertTellerCash(reportID,profileID,packageID,finalItemType,savingsAmount,tellerName,officeBranch,packageStartDate,tellerCashCode,"UnConfirmed");


                    String tittle = "New Package Alert" + "from" + managerName;
                    String details = managerName + "added a new package of NGN" + newTotal + "for" + customerName + "on" + reportDate;
                    SkyLightPackage skyLightPackage1 = new SkyLightPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal,officeBranch, packageEndDate, "fresh");
                    customerDailyReport = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, initialDeposit, daysRemaining, amountRemaining, reportDate, "first");
                    paymentCode=new PaymentCode(customerID,reportID,skylightCode,reportDate);


                    String finalPackageType = packageType;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(cusLatLng ==null){
                                        locBundle.putLong("PackageID", packageID);
                                        locBundle.putString("PackageType", finalPackageType);
                                        locBundle.putLong("CustomerID", customerID);
                                        locBundle.putLong("ProfileID", profileID);
                                        locBundle.putString("CustomerName", customerNames);
                                        locBundle.putString("CustomerPhone", customerPhone);
                                        Intent locationIntent = new Intent(AllCustNewPackAct.this, NewLocAct.class);
                                        locationIntent.putExtras(locBundle);
                                        locActivityResultLauncher.launch(new Intent(locationIntent));

                                    }
                                }
                            });

                        }
                    }).start();


                    //Skylightransaction= new com.skylightapp.Classes.Transaction(transactionID, accountNo, reportDate, 1001001, acctID, "Skylight", customerNames, initialDeposit, transaction_type, "",officeBranch, "", "", "");


                    try {

                        //long profileID, long customerId,Transaction transaction, long accountId,String payee,String payer,Transaction.TRANSACTION_TYPE type, double amount, long transactionId,  String officeBranch,String date

                        //dbHelper.insertTimeLine(tittle, details, reportDate, mCurrentLocation);
                        dbHelper.insertTimeLine(timelineTittle3, details, reportDate, mCurrentLocation);
                        //dbHelper.saveNewTransaction(profileID, customerID,Skylightransaction, acctID, "Skylight", customerNames,transaction_type, initialDeposit, reportID, officeBranch, reportDate);
                        dbHelper.insertNewPackage(this.profileID, customerID,packageID, skylightCode, finalItemType,this.packageType, this.packageDuration,savingsAmount,  reportDate, grandTotal, this.packageEndDate, "fresh");
                        //dbHelper.insertNewPackage(userProfile, customer, skyLightPackage1);
                        dbHelper.insertSavingsCode(paymentCode);
                        adminBalance.setAdminReceivedBalance(adminNewBalance);
                        dbHelper.insertNewDailyReport(profileID,customerID,packageID,reportID,reportCode,numberOfDays,initialDeposit,reportDate,newDaysRemaining,newAmountRemaining,"First");

                        //dbHelper.insertDailyReport(packageID,reportID,profileID, customerID,reportDate,savingsAmount,numberOfDays,newTotal,newAmountContributedSoFar,newAmountRemaining,newDaysRemaining,"First");

                    } catch (SQLiteException e) {
                        System.out.println("Oops!");
                    }

                    if(userProfile !=null){
                        userProfile.addPNewSkylightPackage(profileID, customerID,packageID,  packageType, savingsAmount, packageDuration, reportDate, grandTotal, packageEndDate,"fresh");


                    }
                    if(customer !=null){
                        phoneNumber1 = customer.getCusPhoneNumber();
                        customer.addCusTimeLine(timelineTittle3, timelineDetails5);
                        customer.getCusAccount().setAccountBalance(initialDeposit);
                        customer.addCusNewSavings(packageID,reportID,  savingsAmount, numberOfDays, initialDeposit, daysRemaining, amountRemaining, reportDate, status1);
                        customer.addCusNewSkylightPackage(profileID, customerID,packageID,  packageType, savingsAmount, packageDuration, reportDate, grandTotal, packageEndDate,"fresh");

                    }
                    if(skyLightPackage1 !=null){
                        skyLightPackage1.addPProfileManager(userProfile);
                        skyLightPackage1.setPackageAmount_collected(newAmountContributedSoFar);
                        skyLightPackage1.addPReportCount(packageID, numberOfDays);
                        skyLightPackage1.setPackageCustomer(customer);
                        skyLightPackage1.setPackageOfficeBranch(officeBranch);
                        skyLightPackage1.setPackageCode(skylightCode);
                        skyLightPackage1.addPSavings(profileID, customerID, reportID, savingsAmount, numberOfDays, initialDeposit, daysRemaining, amountRemaining, reportDate, "first");


                    }
                    sendSMSMessage(customerNames,phoneNumber1,amountContributedSoFar,skylightCode);

                    locBundle.putLong("PackageID", packageID);
                    locBundle.putString("PackageType", this.packageType);
                    locBundle.putLong("CustomerID", customerID);
                    locBundle.putLong("ProfileID", this.profileID);
                    locBundle.putString("CustomerName", customerNames);
                    locBundle.putString("CustomerPhone", phoneNumber1);
                    Intent locationIntent = new Intent(AllCustNewPackAct.this, UserLocTrackingAct.class);
                    locationIntent.putExtras(locBundle);
                    locationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(AllCustNewPackAct.this, "Taking you to Customer Location savings", Toast.LENGTH_SHORT).show();
                    locActivityResultLauncher.launch(new Intent(locationIntent));


                }
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }




        }


    }
    private void mauthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };
    }
    private void chooseDate() {
        dateOfSavings = savings_date_.getDayOfMonth()+"/"+ (savings_date_.getMonth() + 1)+"/"+savings_date_.getYear();
        if(dateOfSavings.isEmpty()){
            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
            dateOfSavings = mdformat.format(calendar.getTime());

        }

    }
    public void payNow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Payment Method");
        builder.setItems(new CharSequence[]
                        {"Card", "Bank"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Toast.makeText(AllCustNewPackAct.this, "Card Payment option, selected ", Toast.LENGTH_SHORT).show();
                                payWithCard();
                                break;
                            case 1:
                                Toast.makeText(AllCustNewPackAct.this, "Pay with Bank Choice, made", Toast.LENGTH_SHORT).show();
                                payWithBank();
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
    public void PayWithAll(View view) {
        payNow();
    }
    public void payWithCard() {
        /*customer= new Customer();
        Bundle locBundle = new Bundle();
        skylightCode = random.nextInt((int) (Math.random() * 90089) + 22341);
        Bundle packageTypeBundle1 = new Bundle();
        packageTypeBundle1 = getIntent().getExtras().getBundle("optionBundle") ;
        if(packageTypeBundle1 !=null){
            packageType=packageTypeBundle1.getString("PackageType");

        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(packageType.equalsIgnoreCase("Savings")){
            packageDuration=31;

        }else {
            try {
                numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());
                packageDuration = numberOfMonths*31;
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }

        }
        packageDuration = numberOfMonths*31;
        calendar.add(Calendar.DAY_OF_YEAR, packageDuration);
        Date newDate = calendar.getTime();
        String endDate = sdf.format(newDate);
        packageEndDate=endDate;
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        dbHelper = new DBHelper(this);
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if (userProfile != null) {
            customersN = userProfile.getCustomers();
            customerArrayAdapterN = new ArrayAdapter<>(AllCustNewPackAct.this, android.R.layout.simple_spinner_item, customersN);
            customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_old_customers.setAdapter(customerArrayAdapterN);
            //spn_old_customers.setSelection(0);
            spn_old_customers.setSelection(customerArrayAdapterN.getPosition(customer));
            //selectedCustomerIndex = spn_old_customers.getSelectedItemPosition();

            spn_old_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    customer = (Customer) parent.getSelectedItem();
                    Toast.makeText(AllCustNewPackAct.this, "Customer's ID: " + customer.getuID() + ",  Customer's Name : " + customer.getFirstName(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        random= new SecureRandom();
        String status = "Subscription in progress";
        savingsAmount= Double.parseDouble(edt_Amount.getText().toString().trim());
        numberOfDays=Integer.parseInt(edt_NoOfDays.getText().toString().trim());
        numberOfMonths= Integer.parseInt(edtDurationInMonths.getText().toString().trim());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        packageStartDate = mdformat.format(calendar.getTime());
        try {
            totalAmountCalc = savingsAmount*numberOfDays;

        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        try {
            numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());

        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        try {
            packageID = random.nextInt((int) (Math.random() * 9020) + 1361);

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        try {
            if (String.valueOf(numberOfDays).isEmpty()) {
                Toast.makeText(AllCustNewPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();


            }
            if (String.valueOf(savingsAmount).isEmpty()) {
                Toast.makeText(AllCustNewPackAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();


            }else {




                try {
                    reportID = random.nextInt((int) (Math.random() * 99) + 11);
                    skylightCode = random.nextInt((int) (Math.random() * 90089) + 22341);
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                packageDuration = numberOfMonths*31;
                grandTotal=packageDuration*savingsAmount;
                daysRemaining=packageDuration-numberOfDays;
                amountRemaining=grandTotal- totalAmountCalc;

                skyLightPackage = new SkyLightPackage(profileID, customerID, packageID, packageType, savingsAmount, packageDuration, packageStartDate, grandTotal, packageEndDate, "fresh");
                customerDailyReport = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, totalAmountCalc, daysRemaining, amountRemaining, packageStartDate, "first");
                paymentBundle = new Bundle();
                paymentBundle.putString("Total", String.valueOf(totalAmountCalc));
                paymentBundle.putString("PackageID", String.valueOf(packageID));
                paymentBundle.putString("SavingsID", String.valueOf(reportID));
                paymentBundle.putString("PackageType", packageType);
                paymentBundle.putString("CustomerID", String.valueOf(customerID));
                paymentBundle.putString("CustomerName", "");
                paymentBundle.putString("CustomerPhone", "");
                paymentBundle.putParcelable("Customer", customer);
                paymentBundle.putParcelable("Package", skyLightPackage);
                paymentBundle.putParcelable("Savings", customerDailyReport);
                Intent amountIntent = new Intent(AllCustNewPackAct.this, PayNowActivity.class);
                amountIntent.putExtras(paymentBundle);
                Toast.makeText(AllCustNewPackAct.this, "Your are paying NGN"+ totalAmountCalc, Toast.LENGTH_SHORT).show();
                mStartPaymentUpForResult.launch(new Intent(amountIntent));

            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }*/



    }
    public void payWithBank() {
        /*customer= new Customer();
        skylightCode = random.nextInt((int) (Math.random() * 90089) + 22341);
        Bundle locBundle = new Bundle();
        Bundle packageTypeBundle1 = new Bundle();

        com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type=null;
        packageTypeBundle1 = getIntent().getExtras().getBundle("optionBundle") ;
        if(packageTypeBundle1 !=null){
            packageType=packageTypeBundle1.getString("PackageType");

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
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(packageType.equalsIgnoreCase("Savings")){
            packageDuration=31;

        }else {
            try {
                numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());
                packageDuration = numberOfMonths*31;
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }

        }
        packageDuration = numberOfMonths*31;
        calendar.add(Calendar.DAY_OF_YEAR, packageDuration);
        Date newDate = calendar.getTime();
        String endDate = sdf.format(newDate);
        packageEndDate=endDate;
        String officeBranch=null;
        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if (userProfile != null) {
            customersN = userProfile.getCustomers();
            officeBranch=userProfile.getOffice();
            customerArrayAdapterN = new ArrayAdapter<>(AllCustNewPackAct.this, android.R.layout.simple_spinner_item, customersN);
            customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_old_customers.setAdapter(customerArrayAdapterN);
            //spn_old_customers.setSelection(0);
            spn_old_customers.setSelection(customerArrayAdapterN.getPosition(customer));
            //selectedCustomerIndex = spn_old_customers.getSelectedItemPosition();

            spn_old_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    customer = (Customer) parent.getSelectedItem();
                    Toast.makeText(AllCustNewPackAct.this, "Customer's ID: " + customer.getuID() + ",  Customer's Name : " + customer.getFirstName(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        random= new SecureRandom();
        String status = "Subscription in progress";
        savingsAmount= Double.parseDouble(edt_Amount.getText().toString().trim());
        numberOfDays=Integer.parseInt(edt_NoOfDays.getText().toString().trim());
        numberOfMonths= Integer.parseInt(edtDurationInMonths.getText().toString().trim());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        packageStartDate = mdformat.format(calendar.getTime());
        try {
            totalAmountCalc = savingsAmount*numberOfDays;

        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        try {
            numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());

        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        try {
            packageID = random.nextInt((int) (Math.random() * 9020) + 1361);

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        try {
            if (String.valueOf(numberOfDays).isEmpty()) {
                Toast.makeText(AllCustNewPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();


            }
            if (String.valueOf(savingsAmount).isEmpty()) {
                Toast.makeText(AllCustNewPackAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();


            }else {




                try {
                    reportID = random.nextInt((int) (Math.random() * 99) + 11);
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                packageDuration = numberOfMonths*31;
                grandTotal=packageDuration*savingsAmount;
                daysRemaining=packageDuration-numberOfDays;
                amountRemaining=grandTotal- totalAmountCalc;

                skyLightPackage = new SkyLightPackage(profileID, customerID, packageID, packageType, savingsAmount, packageDuration, packageStartDate, grandTotal, packageEndDate, "fresh");
                customerDailyReport = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, totalAmountCalc, daysRemaining, amountRemaining, packageStartDate, "first");
                paymentCode=new PaymentCode(customerID,reportID,skylightCode,reportDate);
                paymentBundle = new Bundle();
                paymentBundle.putString("Total", String.valueOf(totalAmountCalc));
                paymentBundle.putString("PackageID", String.valueOf(packageID));
                paymentBundle.putString("SavingsID", String.valueOf(reportID));
                paymentBundle.putString("PackageType", packageType);
                paymentBundle.putString("CustomerID", String.valueOf(customerID));
                paymentBundle.putString("CustomerName", "");
                paymentBundle.putString("CustomerPhone", "");
                paymentBundle.putParcelable("Customer", customer);
                paymentBundle.putParcelable("Package", skyLightPackage);
                paymentBundle.putParcelable("Savings", customerDailyReport);
                Intent amountIntent = new Intent(AllCustNewPackAct.this, FluPaywithBank.class);
                amountIntent.putExtras(paymentBundle);
                Toast.makeText(AllCustNewPackAct.this, "Your are paying NGN"+ totalAmountCalc, Toast.LENGTH_SHORT).show();
                mStartPaymentUpForResult.launch(new Intent(amountIntent));

            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }*/



    }

    protected void sendSMSMessage(String customerNames, String phoneNumber1, double amountContributedSoFar, long skylightCode) {
        Bundle smsBundle= new Bundle();
        String smsMessage="Congratulations" + this.customerNames + "as you start a new Package, your savings code for savings N"+ this.amountContributedSoFar +""+"is"+""+ this.skylightCode;
        smsBundle.putString(PROFILE_PHONE,phoneNumber1);
        smsBundle.putString("USER_PHONE",phoneNumber1);
        smsBundle.putString("smsMessage",smsMessage);
        smsBundle.putString("from","Skylight");
        smsBundle.putString("to",phoneNumber1);
        Intent itemPurchaseIntent = new Intent(AllCustNewPackAct.this, SMSAct.class);
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