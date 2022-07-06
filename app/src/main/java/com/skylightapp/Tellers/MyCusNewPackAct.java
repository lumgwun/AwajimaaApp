package com.skylightapp.Tellers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.NewLocAct;
import com.skylightapp.SMSAct;
import com.skylightapp.SignUpAct;
import com.skylightapp.SuperAdmin.AdminBalance;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AccountTypes;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

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

public class MyCusNewPackAct extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
    private AppBarLayout appBarLayout;
    com.melnykov.fab.FloatingActionButton floatingActionButton;
    Profile userProfile;
    long profileUID2,tellerCashCode;
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
    String packageEndDate;

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

    DatePickerDialog datePickerDialog;
    Calendar dateCalendar;
    private Customer lastCustomerProfileUsed;
    private final int MY_LOCATION_REQUEST_CODE = 100;

    public final static int SENDING = 1;
    public final static int CONNECTING = 2;
    public final static int ERROR = 3;
    public final static int SENT = 4;
    public final static int SHUTDOWN = 5;

    private static final String TAG = "MyCusNewPackAct";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Button btnFusedLocation, btn_backTo_dashboard;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String customerPhone;
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
    String packageOffice;
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
    String interestRate, firstNameOfCustomer,amtText,profileSurname,packageStartDate, profilePhone,lastNameOfCustomer,profileFirstName;
    String customerNames;
    Context context;
    int customerId;
    long skylightCode;
    int customerID,accountNo,acctID;
    double amountContributedSoFar,newAmountContributedSoFar;
    double packageGrantTotal,amountRemaining,moneySaved;
    int profileID, oldPackageId;
    private static boolean isPersistenceEnabled = false;

    int packageId,newPackageCount,daysRemaining,packageDuration,numberOfMonths;
    String reportDate,packageTypeS,machine,selectedState,selectedGender,selectedStatus,selectedOffice;
    String surName, firstName, emailAddress, phoneNumber1, address,officeBranch;
    long virtualAccountID;
    int packageID;
    int customerID1;
    int profileID1;
    int reportID;
    RadioGroup grpRB,grp2;
    int durationInDays;
    int x;
    int y;
    double totalAmountCalc;


    String mDate;
    private Marker m;
    CustomerDailyReport customerDailyReport;
    Bundle paymentBundle;

    AppCompatTextView txtTotalForTheDay,txtDurationInDays,txtTypeOfPackage;
    String managerName;
    int totalPackageDays;
    Bundle packageTypeBundle1;
    TextWatcher textWatcher;
    TextWatcher textWatcherDuration;
    PaymentCode paymentCode;
    Transaction transaction;
    AdminBalance adminBalance;
    String transactionID,tellerSurName,selectedItem,tellerOffice,tellerFirstName,tellerName;
    TellerCash tellerCash;
    AppCompatSpinner spnSavingsPlan, spnFoodAndItem, spnInvestment,spnTypeOfPackage,spnPromo;
    LinearLayoutCompat layoutSavings;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    com.skylightapp.Classes.Transaction Skylightransaction;
    AppCompatButton btnAddNewCus;
    LatLng cusLatLng;
    double tellerAmount;
    private static final String PREF_NAME = "skylight";
    String investStringEndDate,selectedPromoPack,invMaturityDate,invDates,newPackageType,selectedFoodStuff,selectedItemType,finalItemType,selectedInvestmentType;
    LinearLayoutCompat layoutInvestment, layoutFoodItemPurchase,layoutPackageType,layoutPromo;
    //public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    //public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");

    ActivityResultLauncher<Intent> startGetCustomerForResult = registerForActivityResult(new
            ActivityResultContracts.StartActivityForResult(), new
            ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result!=null&&result.getResultCode()==RESULT_OK){
                        if(result.getData()!=null ){
                            Intent data = result.getData();
                            customer =data.getParcelableExtra("Customer");
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> locActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent amountIntent = new Intent(MyCusNewPackAct.this, TellerHomeChoices.class);
                        amountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                            Toast.makeText(MyCusNewPackAct.this, "Payment returned successful", Toast.LENGTH_SHORT).show();
                            doProcessing(tellerAmount, packageType, finalItemType, packageDuration, packageEndDate, totalAmountCalc, skylightCode, paymentCode, adminBalance, tellerCash, profileID, tellerName, tellerCashCode, tellerOffice, customer, adminBalance);
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(MyCusNewPackAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.act_my_cus_new_pack);
        checkInternetConnection();

        packageTypeBundle1 = new Bundle();
        layoutPackageType=  findViewById(R.id.type_Layout);
        spnTypeOfPackage=  findViewById(R.id.pack_Type);
        txtTypeOfPackage=  findViewById(R.id.TypeP);
        btnAddNewCus=  findViewById(R.id.my_add_new_cus);

        dbHelper= new DBHelper(this);

        packageTypeBundle1 = getIntent().getExtras() ;
        if(packageTypeBundle1 !=null){
            packageType=packageTypeBundle1.getString("PackageType");
            txtTypeOfPackage.setText(MessageFormat.format("Package Type{0}", packageType));

        }else{
            layoutPackageType.setVisibility(View.VISIBLE);

            spnTypeOfPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    packageType = spnTypeOfPackage.getSelectedItem().toString();
                    packageType = (String) parent.getSelectedItem();
                    Toast.makeText(MyCusNewPackAct.this, "Selected Type: "+ packageType,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            packageType = spnTypeOfPackage.getSelectedItem().toString();
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

        customer= new Customer();
        mAuth = FirebaseAuth.getInstance();
        //mauthListener();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        tellerSurName = userPreferences.getString("USER_SURNAME", "");
        tellerFirstName = userPreferences.getString("USER_FIRSTNAME", "");
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        try {
            profileID = Integer.parseInt((userPreferences.getString("PROFILE_ID", "")));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        tellerOffice = userPreferences.getString("CHOSEN_OFFICE", "");
        random= new SecureRandom();

        customers=new ArrayList<Customer>();
        Skylightransaction= new com.skylightapp.Classes.Transaction();

        customersN=new ArrayList<Customer>();
        tellerCash= new TellerCash();
        PaymentCode paymentCode=new PaymentCode();
        AdminBalance adminBalance= new AdminBalance();
        skylightCode = random.nextInt((int) (Math.random() * 10180) + 12341);
        tellerCashCode = random.nextInt((int) (Math.random() * 20089) + 12041);

        spn_old_customers = findViewById(R.id.old_customerMyNew);
        edtDurationInMonths =  findViewById(R.id.monthsMyCus);
        edt_Amount =  findViewById(R.id.amountMyCus);
        edt_NoOfDays=  findViewById(R.id.edtNoOfDaysMyCus);

        layoutSavings =  findViewById(R.id.savings_Layout);
        spnSavingsPlan =  findViewById(R.id.savings_plans);

        layoutFoodItemPurchase =  findViewById(R.id.food_Stuff_Layout);
        spnFoodAndItem =  findViewById(R.id.foodStuff_Item);

        layoutInvestment=  findViewById(R.id.inv_Layout);
        spnInvestment=  findViewById(R.id.inv_Type);

        layoutPromo=  findViewById(R.id.promo_Stuff_Layout);
        spnPromo=  findViewById(R.id.promoStuff);


        txtTypeOfPackage=  findViewById(R.id.TypeP);

        txtTotalForTheDay =  findViewById(R.id.total_amountMyNew);
        txtDurationInDays =  findViewById(R.id.totalDurationInDays);
        btn_add_New_Savings = findViewById(R.id.add_savingsMyNew);
        btn_payNow = findViewById(R.id.pay_now_savingsMyNew);
        btn_payNow.setOnClickListener(this::payDialoqMyNew);
        tellerName=tellerSurName+","+ tellerFirstName;



        edtDurationInMonths.addTextChangedListener(textWatcherDuration);
        edt_Amount.addTextChangedListener(textWatcher);
        edt_NoOfDays.addTextChangedListener(textWatcher);
        textWatcherDuration = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!TextUtils.isEmpty(edtDurationInMonths.getText().toString().trim())

                ) {

                    durationInDays = Integer.parseInt(edtDurationInMonths.getText().toString().trim()) * 31;

                    Log.e("Duration in days:", String.valueOf(durationInDays));
                    txtDurationInDays.setText(MessageFormat.format("Duration in days:{0}", String.valueOf(durationInDays)+""+"savings days"));
                }else {
                    txtDurationInDays.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!TextUtils.isEmpty(edt_Amount.getText().toString().trim())
                        || !TextUtils.isEmpty(edt_NoOfDays.getText().toString().trim())

                ) {


                    totalAmountCalc = Double.parseDouble(edt_Amount.getText().toString().trim()) *
                            Integer.parseInt(edt_NoOfDays.getText().toString().trim());

                    Log.e("Total", String.valueOf(totalAmountCalc));
                    txtTotalForTheDay.setText(String.format("Total N%s", totalAmountCalc));
                }else {
                    txtTotalForTheDay.setText("No Total Yet");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };




        if(packageType.equalsIgnoreCase("Item Purchase")){
            layoutSavings.setVisibility(View.GONE);
            layoutFoodItemPurchase.setVisibility(View.VISIBLE);
            layoutInvestment.setVisibility(View.GONE);
            layoutPromo.setVisibility(View.GONE);
            packageDuration=durationInDays;
            tellerAmount=1000;

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
            txtDurationInDays.setVisibility(View.GONE);
            packageDuration=372;
            txtTotalForTheDay.setText(String.format("Total N%s", totalAmountCalc));
            txtDurationInDays.setText(MessageFormat.format("372 saving days,End Date:{0}",investmentEndDate +"/"+""+invMaturityDate));


        }
        if(packageType.equalsIgnoreCase("Savings")){
            layoutSavings.setVisibility(View.VISIBLE);
            layoutFoodItemPurchase.setVisibility(View.GONE);
            layoutPromo.setVisibility(View.GONE);
            layoutInvestment.setVisibility(View.GONE);
            packageDuration=31;
            tellerAmount=0.00;
            edtDurationInMonths.setVisibility(View.GONE);
            packageEndDate=null;
            txtDurationInDays.setText(MessageFormat.format("Life Cycle:31 days, Temporary End Date:{0}", savingsEndDate));
        }

        spnSavingsPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemType = spnSavingsPlan.getSelectedItem().toString();
                selectedItemType = (String) parent.getSelectedItem();
                finalItemType=selectedItemType;
                Toast.makeText(MyCusNewPackAct.this, "Item Selected: "+ selectedItemType,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MyCusNewPackAct.this, "Selected Food Stuff: "+ selectedFoodStuff,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MyCusNewPackAct.this, "Selected Investment: "+ selectedInvestmentType,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MyCusNewPackAct.this, "Selected Promo: "+ selectedPromoPack,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        btn_payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //doPayNow();

            }
        });
        btnAddNewCus.setOnClickListener(this::addNewMyCus);
        btnAddNewCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerIntent = new Intent(MyCusNewPackAct.this, SignUpAct.class);
                //customerIntent.putExtra("Customer", (Parcelable) customer);
                Toast.makeText(MyCusNewPackAct.this, "Taking you to Customer Sign up area", Toast.LENGTH_SHORT).show();
                startGetCustomerForResult.launch(customerIntent);

            }
        });




        //userProfile= new Profile();
        customerDailyReport= new CustomerDailyReport();
        skyLightPackage = new SkyLightPackage();
        dbHelper = new DBHelper(this);
        //Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");


        if(userProfile !=null){
            ManagerSurname = userProfile.getProfileLastName();
            managerFirstName = userProfile.getProfileFirstName();
            managerPhoneNumber1 = userProfile.getProfilePhoneNumber();
            managerEmail = userProfile.getProfileEmail();
            managerNIN = userProfile.getProfileIdentity();
            managerName=ManagerSurname+""+ managerFirstName;
            managerUserName = userProfile.getProfileUserName();
            machine = userProfile.getProfileMachine();
            profileUID2= userProfile.getPID();

        }
        if (userProfile != null) {
            customersN = userProfile.getProfileCustomers();
            customerArrayAdapterN = new ArrayAdapter<>(MyCusNewPackAct.this, android.R.layout.simple_spinner_item, customersN);
            customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_old_customers.setAdapter(customerArrayAdapterN);
            spn_old_customers.setSelection(0);
            spn_old_customers.setSelection(customerArrayAdapterN.getPosition(customer));
            //selectedCustomerIndex = spn_old_customers.getSelectedItemPosition();

            spn_old_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    customer = (Customer) parent.getSelectedItem();
                    Toast.makeText(MyCusNewPackAct.this, "Customer's ID: "+customer.getCusUID()+",  Customer's Name : "+customer.getCusFirstName(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            btn_add_New_Savings.setOnClickListener(this::addNewPackage);

            btn_add_New_Savings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());
                    } catch (NumberFormatException e) {
                        System.out.println("Oops!");
                    }
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
                        totalAmountSum = savingsAmount*numberOfDays;

                    } catch (NumberFormatException e) {
                        System.out.println("Oops!");
                    }
                    skyLightPackage = new SkyLightPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, "fresh");



                    try {
                        if (String.valueOf(numberOfDays).isEmpty()) {
                            Toast.makeText(MyCusNewPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();


                        }
                        if (String.valueOf(savingsAmount).isEmpty()) {
                            Toast.makeText(MyCusNewPackAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();


                        }
                        if (packageType.isEmpty()) {
                            Toast.makeText(MyCusNewPackAct.this, "Package Type can not be left empty", Toast.LENGTH_LONG).show();


                        }
                        if (finalItemType.isEmpty()) {
                            Toast.makeText(MyCusNewPackAct.this, "Type, can not be left empty", Toast.LENGTH_LONG).show();


                        } else {
                            doProcessing(tellerAmount, packageType, finalItemType, packageDuration, packageEndDate, totalAmountCalc, skylightCode, paymentCode, adminBalance, tellerCash, profileID, tellerName, tellerCashCode, tellerOffice, customer, adminBalance);


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }
    /*private void mauthListener() {
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
    }*/

    private void doPayNow() {

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Payment Method");
        builder.setItems(new CharSequence[]
                        {"Card", "Bank"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Toast.makeText(MyCusNewPackAct.this, "Card Payment option, selected ", Toast.LENGTH_SHORT).show();
                                payWithCard();
                                break;
                            case 1:
                                Toast.makeText(MyCusNewPackAct.this, "Pay with Bank Choice, made", Toast.LENGTH_SHORT).show();
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

        builder.create().show();*/

    }


    public void payDialoqMyNew(View view) {

    }
    protected  void doProcessing(double tellerAmount, String packageType, String finalItemType, int packageDuration, String packageEndDate, double totalAmountCalc, long skylightCode, PaymentCode paymentCode, AdminBalance adminBalance, TellerCash tellerCash, int profileID, String tellerName, long tellerCashCode, String tellerOffice, Customer customer, AdminBalance balance){
        Bundle locBundle = new Bundle();
        Bundle packageTypeBundle1 = new Bundle();
        dbHelper.openDataBase();
        com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type=null;
        Skylightransaction= new com.skylightapp.Classes.Transaction();

        if(packageType.equalsIgnoreCase("Savings")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }
        if(packageType.equalsIgnoreCase("Item Purchase")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }
        if(packageType.equalsIgnoreCase("Promo")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }
        if(packageType.equalsIgnoreCase("Investment")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }

        spnSavingsPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = (String) parent.getSelectedItem();
                Toast.makeText(MyCusNewPackAct.this, "Selected Item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        reportDate = mdformat.format(calendar.getTime());
        try {
            numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        packageID = random.nextInt((int) (Math.random() * 1470) + 13601);
        reportID = random.nextInt((int) (Math.random() * 1029) + 11);
        long reportCode = random.nextInt((int) (Math.random() * 10089) + 10340);
        /*calendar.add(Calendar.DAY_OF_YEAR, this.packageDuration);
        Date newDate = calendar.getTime();
        String endDate = sdf.format(newDate);
        this.packageEndDate =endDate;
        String status = "InProgress";*/



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
            totalAmountSum = savingsAmount*numberOfDays;

        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }

        try {
            grandTotal = this.packageDuration *savingsAmount;
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        if(customer !=null){
            account1= customer.getCusAccount();
            packageOffice= customer.getCusOfficeBranch();

        }

        transactionID="Skylight"+packageID;
        double adminCommission=adminBalance.getAdminReceivedBalance();
        //double skylightCommission=100;
        double initialDeposit=totalAmountSum-savingsAmount;
        double acctBalance=account1.getAccountBalance();
        double adminNewBalance= adminCommission+savingsAmount;

        amountContributedSoFar = totalAmountSum;
        newAmountRemaining=grandTotal-amountContributedSoFar;
        newDaysRemaining= packageDuration -numberOfDays;

        if(account1 !=null){
            packageAccountBalance=acctBalance+initialDeposit;

        }

        try {
            if (String.valueOf(numberOfDays).isEmpty()) {
                Toast.makeText(MyCusNewPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();


            }
            if (String.valueOf(savingsAmount).isEmpty()) {
                Toast.makeText(MyCusNewPackAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();


            }if (packageType.isEmpty()) {
                Toast.makeText(MyCusNewPackAct.this, "Package Type can not be left empty", Toast.LENGTH_LONG).show();


            }if (finalItemType.isEmpty()) {
                Toast.makeText(MyCusNewPackAct.this, "Name of Order, can not be left empty", Toast.LENGTH_LONG).show();


            }else {
                tellerCash=new TellerCash(reportID,profileID,packageID,finalItemType,tellerAmount,tellerName,officeBranch,packageStartDate,tellerCashCode,"UnConfirmed");
                tellerCash.setTellerCashCode(tellerCashCode);
                dbHelper.insertTellerCash(reportID,profileID,packageID,finalItemType,tellerAmount,tellerName,officeBranch,packageStartDate,tellerCashCode,"UnConfirmed");

                String status3 = "inProgress";
                String status1 = "Unconfirmed";
                reportDate = mdformat.format(calendar.getTime());
                String timelineTittle3 = "New Package alert";

                String timelineDetails3 = "NGN" + grandTotal + " package was initiated for" + surName + "," + firstName;

                String timelineDetails5 = "A new package of NGN" + grandTotal + "was started" + "on" + reportDate;

                String customerName = surName + "," + firstName ;
                //Skylightransaction= new com.skylightapp.Classes.Transaction(packageID, accountNo, reportDate, "Skylight", String.valueOf(account1), "Skylight", customerNames, initialDeposit, transaction_type, "",officeBranch, "", "", "");

                if(customer !=null){
                    cusLatLng=customer.getCusLocation();
                    customerNames = customer.getCusSurname() + "," + customer.getCusFirstName();
                    emailAddress = customer.getCusEmailAddress();
                    managerName = userProfile.getProfileLastName() + "," + userProfile.getProfileFirstName();
                    customerPhone= customer.getCusPhoneNumber();


                    phoneNumber1 = customer.getCusPhoneNumber();
                    customer.addCusTimeLine(timelineTittle3, timelineDetails5);
                    customer.getCusAccount().setAccountBalance(initialDeposit);
                    customer.addCusNewSavings(packageID,reportID,  savingsAmount, numberOfDays, initialDeposit, daysRemaining, amountRemaining, reportDate, status1);
                    customer.addCusNewSkylightPackage(this.profileID, customerID,packageID, this.packageType, savingsAmount, this.packageDuration, reportDate, grandTotal, this.packageEndDate,"fresh");

                }

                String tittle = "New Package Alert" + "from" + managerName;
                String details = managerName + "added a new package of NGN" + newTotal + "for" + customerName + "on" + reportDate;
                SkyLightPackage skyLightPackage1 = new SkyLightPackage(this.profileID, customerID, packageID, this.packageType,finalItemType, savingsAmount, this.packageDuration, reportDate, grandTotal, this.packageEndDate, "fresh");
                customerDailyReport = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, initialDeposit, daysRemaining, amountRemaining, reportDate, status3);
                adminBalance.setAdminReceivedBalance(adminNewBalance);
                paymentCode=new PaymentCode(customerID,reportID,skylightCode,reportDate);
                if(skyLightPackage1 !=null){
                    skyLightPackage1.addProfileManager(userProfile);
                    skyLightPackage1.setPackageAmount_collected(newAmountContributedSoFar);
                    skyLightPackage1.addReportCount(packageID, numberOfDays);
                    skyLightPackage1.setDocCustomer(customer);
                    skyLightPackage1.setPackageCode(skylightCode);
                    skyLightPackage1.setPackageOfficeBranch(userProfile.getProfileOffice());
                    skyLightPackage1.addSavings(profileID, customerID, reportID, savingsAmount, numberOfDays, initialDeposit, daysRemaining, amountRemaining, reportDate, "first");


                }
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
                                    locBundle.putString("PackageType", packageType);
                                    locBundle.putLong("CustomerID", customerID);
                                    locBundle.putLong("ProfileID", profileID);
                                    locBundle.putString("CustomerName", customerNames);
                                    locBundle.putString("CustomerPhone", customerPhone);
                                    Intent locationIntent = new Intent(MyCusNewPackAct.this, NewLocAct.class);
                                    locationIntent.putExtras(locBundle);
                                    locActivityResultLauncher.launch(new Intent(locationIntent));

                                }
                            }
                        });

                    }
                }).start();


                try {

                    dbHelper.insertTimeLine(tittle, details, reportDate, mCurrentLocation);
                    dbHelper.saveNewTransaction(profileID, customerID,Skylightransaction, acctID, "Skylight", customerName,transaction_type,initialDeposit, reportID, officeBranch, reportDate);
                    dbHelper.insertNewPackage(profileID, customerID,packageID, skylightCode, finalItemType,this.packageType, this.packageDuration,savingsAmount,  reportDate, grandTotal, this.packageEndDate, "fresh");
                    dbHelper.insertNewDailyReport(profileID,customerID,packageID,reportID,reportCode,numberOfDays,newTotal,reportDate,newDaysRemaining,newAmountRemaining,"First");
                    dbHelper.saveNewAdminBalance(acctID,profileID,customerID,packageID,savingsAmount,reportDate,"Unconfirmed");
                    dbHelper.insertSavingsCode(paymentCode);
                    //dbHelper.insertDailyReport(packageID,reportID, this.profileID, customerID,reportDate,savingsAmount,numberOfDays,newTotal,newAmountContributedSoFar,newAmountRemaining,newDaysRemaining,"first");

                } catch (SQLiteException e) {
                    System.out.println("Oops!");
                }
                sendSMSMessage(customerNames,customerPhone,amountContributedSoFar,skylightCode,totalAmountSum,skylightCode);


                if(userProfile !=null){
                    userProfile.addTellerCash(reportID,packageID,finalItemType,tellerAmount,tellerName,officeBranch,packageStartDate);
                    userProfile.addSavings(profileID, customerID, reportID, savingsAmount, numberOfDays, initialDeposit, daysRemaining, amountRemaining, reportDate, "First");
                    userProfile.addNewSkylightPackage(profileID, customerID,packageID, this.packageType, savingsAmount, this.packageDuration, reportDate, grandTotal, this.packageEndDate,"fresh");


                }

                if(packageType.equalsIgnoreCase("Item Purchase")){
                    newPackageType="item_purchase";


                }
                if(packageType.equalsIgnoreCase("Promo")){
                    newPackageType="promo";



                }
                if(packageType.equalsIgnoreCase("Investment")){
                    newPackageType="investment";


                }
                if(packageType.equalsIgnoreCase("Savings")){
                    newPackageType="savings";

                }



            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }


    }
    protected void sendSMSMessage(String customerNames, String customerPhone, double amountContributedSoFar, long skylightCode, double totalAmountSum, long code) {
        Bundle smsBundle= new Bundle();
        String smsMessage="Congratulations" + ""+ customerNames +""+ "as you start a new Package, your savings code for savings N"+ totalAmountSum +""+"is"+""+ skylightCode;
        smsBundle.putString(PROFILE_PHONE, customerPhone);
        smsBundle.putString("USER_PHONE", customerPhone);
        smsBundle.putString("smsMessage",smsMessage);
        smsBundle.putString("from","+15703251701");
        smsBundle.putString("to", customerPhone);
        Intent itemPurchaseIntent = new Intent(MyCusNewPackAct.this, SMSAct.class);
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


    public void addNewPackage(View view) {
    }

    public void addNewMyCus(View view) {
    }

    public void payWithBank() {
        /*customer= new Customer();
        Bundle locBundle = new Bundle();
        customersN=new ArrayList<Customer>();
        Bundle packageTypeBundle1 = new Bundle();
        com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type=null;
        Skylightransaction= new com.skylightapp.Classes.Transaction();
        packageTypeBundle1 = getIntent().getExtras().getBundle("optionBundle") ;
        if(packageTypeBundle1 !=null){
            packageType=packageTypeBundle1.getString("PackageType");

        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if(packageType.equalsIgnoreCase("Savings")){
            //transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.SAVINGS;
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }
        if(packageType.equalsIgnoreCase("Item Purchase")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }
        if(packageType.equalsIgnoreCase("Promo")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);
            //transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.PROMO;

        }
        if(packageType.equalsIgnoreCase("Investment")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);
            //transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.IVESTMENT;

        }
        if(packageType.equalsIgnoreCase("Investment")){
            packageDuration=403;
            edtDurationInMonths.setVisibility(View.GONE);
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }
        if(packageType.equalsIgnoreCase("Savings")){
            packageDuration=31;

        }if(packageType.equalsIgnoreCase("Investment")){
            packageDuration=403;
            layoutSavings.setVisibility(View.VISIBLE);

        }
        if(packageType.equalsIgnoreCase("Savings")){
            packageDuration=31;

        }if(packageType.equalsIgnoreCase("Item Purchase")){
            layoutSavings.setVisibility(View.VISIBLE);

        }if(packageType.equalsIgnoreCase("Investment")){
            packageDuration=403;
            layoutSavings.setVisibility(View.VISIBLE);
            edtDurationInMonths.setVisibility(View.GONE);

        }else {
            try {
                numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());
                packageDuration = numberOfMonths*31;
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }

        }
        spnSavingsPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = (String) parent.getSelectedItem();
                Toast.makeText(MyCusNewPackAct.this, "Selected Item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        packageDuration = numberOfMonths*31;
        calendar.add(Calendar.DAY_OF_YEAR, packageDuration);
        Date newDate = calendar.getTime();
        String endDate = sdf.format(newDate);
        packageEndDate=endDate;


        sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if (userProfile != null) {
            customersN = userProfile.getCustomers();
            customerArrayAdapterN = new ArrayAdapter<>(MyCusNewPackAct.this, android.R.layout.simple_spinner_item, customersN);
            customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_old_customers.setAdapter(customerArrayAdapterN);
            //spn_old_customers.setSelection(0);
            spn_old_customers.setSelection(customerArrayAdapterN.getPosition(customer));
            //selectedCustomerIndex = spn_old_customers.getSelectedItemPosition();

            spn_old_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    customer = (Customer) parent.getSelectedItem();
                    Toast.makeText(MyCusNewPackAct.this, "Customer's ID: " + customer.getuID() + ",  Customer's Name : " + customer.getFirstName(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        if(customer !=null){
            packageOffice=customer.getOffice();
        }
        random= new SecureRandom();
        String status = "Subscription in progress";
        savingsAmount= Double.parseDouble(edt_Amount.getText().toString().trim());
        numberOfDays=Integer.parseInt(edt_NoOfDays.getText().toString().trim());
        numberOfMonths= Integer.parseInt(edtDurationInMonths.getText().toString().trim());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        packageStartDate = mdformat.format(calendar.getTime());
        try {
            totalAmountSum = savingsAmount*numberOfDays;

        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        try {
            numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());

        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        try {
            packageID = random.nextInt((int) (Math.random() * 1020) + 1361);

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        try {
            if (String.valueOf(numberOfDays).isEmpty()) {
                Toast.makeText(MyCusNewPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();


            }
            if (String.valueOf(savingsAmount).isEmpty()) {
                Toast.makeText(MyCusNewPackAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();


            }else {




                try {
                    reportID = random.nextInt((int) (Math.random() * 99) + 11);
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                tellerCash=new TellerCash(reportID,profileID,packageID,selectedItem,savingsAmount,tellerName,officeBranch,packageStartDate,tellerCashCode,"UnConfirmed");
                tellerCash.setTellerCashCode(tellerCashCode);
                dbHelper.insertTellerCash(reportID,profileID,packageID,selectedItem,savingsAmount,tellerName,officeBranch,packageStartDate,tellerCashCode,"UnConfirmed");



                packageDuration = numberOfMonths*31;
                grandTotal=packageDuration*savingsAmount;
                daysRemaining=packageDuration-numberOfDays;
                amountRemaining=grandTotal-totalAmountSum;
                skyLightPackage = new SkyLightPackage(this.profileID, customerID, packageID, this.packageType,finalItemType, savingsAmount, this.packageDuration, reportDate, grandTotal, this.packageEndDate, "fresh");

                customerDailyReport = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, packageStartDate, "first");
                paymentBundle = new Bundle();
                skyLightPackage.setOfficeBranch(packageOffice);
                customerDailyReport.setSavingsCode(String.valueOf(skylightCode));
                SkyLightPackage.setCount(numberOfDays);
                paymentBundle.putString("Total", String.valueOf(totalAmountSum));
                paymentBundle.putString("PackageID", String.valueOf(packageID));
                paymentBundle.putString("SavingsID", String.valueOf(reportID));
                paymentBundle.putString("PackageType", packageType);
                paymentBundle.putString("CustomerID", String.valueOf(customerID));
                paymentBundle.putString("CustomerName", "");
                paymentBundle.putString("CustomerPhone", "");

                paymentBundle.putString("officeBranch", "");
                paymentBundle.putParcelable("Customer", customer);
                paymentBundle.putParcelable("Package", skyLightPackage);
                paymentBundle.putParcelable("Savings", customerDailyReport);
                Intent amountIntent = new Intent(MyCusNewPackAct.this, FluPaywithBank.class);
                amountIntent.putExtras(paymentBundle);
                Toast.makeText(MyCusNewPackAct.this, "Your are paying NGN"+totalAmountSum, Toast.LENGTH_SHORT).show();
                savingsStartForResult.launch(new Intent(amountIntent));

            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }*/



    }
    public void payWithCard() {
        customer= new Customer();
        /*sharedpreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        customersN=new ArrayList<Customer>();
        Skylightransaction= new com.skylightapp.Classes.Transaction();
        com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type=null;
        json = sharedpreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        Bundle locBundle = new Bundle();
        Bundle packageTypeBundle1 = new Bundle();
        packageTypeBundle1 = getIntent().getExtras().getBundle("optionBundle") ;
        if(packageTypeBundle1 !=null){
            packageType=packageTypeBundle1.getString("PackageType");

        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        packageTypeBundle1 = getIntent().getExtras().getBundle("optionBundle") ;
        if(packageTypeBundle1 !=null){
            packageType=packageTypeBundle1.getString("PackageType");

        }
        if(packageType.equalsIgnoreCase("Savings")){
            //transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.SAVINGS;
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }
        if(packageType.equalsIgnoreCase("Item Purchase")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }
        if(packageType.equalsIgnoreCase("Promo")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);
            //transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.PROMO;

        }
        if(packageType.equalsIgnoreCase("Investment")){
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);
            //transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.IVESTMENT;

        }
        if(packageType.equalsIgnoreCase("Investment")){
            packageDuration=403;
            edtDurationInMonths.setVisibility(View.GONE);
            transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.valueOf(packageType);

        }
        if(packageType.equalsIgnoreCase("Savings")){
            packageDuration=31;

        }if(packageType.equalsIgnoreCase("Investment")){
            packageDuration=403;
            layoutSavings.setVisibility(View.VISIBLE);

        }else {
            try {
                numberOfMonths = Integer.parseInt(edtDurationInMonths.getText().toString());
                packageDuration = numberOfMonths*31;
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }

        }
        spnSavingsPlan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItem = (String) parent.getSelectedItem();
                Toast.makeText(MyCusNewPackAct.this, "Selected Item: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        packageDuration = numberOfMonths*31;
        calendar.add(Calendar.DAY_OF_YEAR, packageDuration);
        Date newDate = calendar.getTime();
        String endDate = sdf.format(newDate);
        packageEndDate=endDate;

        if (userProfile != null) {
            customersN = userProfile.getCustomers();
            customerArrayAdapterN = new ArrayAdapter<>(MyCusNewPackAct.this, android.R.layout.simple_spinner_item, customersN);
            customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_old_customers.setAdapter(customerArrayAdapterN);
            //spn_old_customers.setSelection(0);
            spn_old_customers.setSelection(customerArrayAdapterN.getPosition(customer));
            //selectedCustomerIndex = spn_old_customers.getSelectedItemPosition();

            spn_old_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    customer = (Customer) parent.getSelectedItem();
                    Toast.makeText(MyCusNewPackAct.this, "Customer's ID: " + customer.getuID() + ",  Customer's Name : " + customer.getFirstName(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
        if(customer !=null){
            packageOffice=customer.getOffice();
        }
        random= new SecureRandom();
        String status = "Fresh";
        savingsAmount= Double.parseDouble(edt_Amount.getText().toString().trim());
        numberOfDays=Integer.parseInt(edt_NoOfDays.getText().toString().trim());
        numberOfMonths= Integer.parseInt(edtDurationInMonths.getText().toString().trim());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        packageStartDate = mdformat.format(calendar.getTime());
        try {
            totalAmountSum = savingsAmount*numberOfDays;

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
                Toast.makeText(MyCusNewPackAct.this, "Number of days can not be left empty", Toast.LENGTH_LONG).show();


            }
            if (String.valueOf(savingsAmount).isEmpty()) {
                Toast.makeText(MyCusNewPackAct.this, "Package Amount can not be left empty", Toast.LENGTH_LONG).show();


            }else {
                tellerCash=new TellerCash(reportID,profileID,packageID,selectedItem,savingsAmount,tellerName,officeBranch,packageStartDate,tellerCashCode,"UnConfirmed");
                tellerCash.setTellerCashCode(tellerCashCode);
                dbHelper.insertTellerCash(reportID,profileID,packageID,selectedItem,savingsAmount,tellerName,officeBranch,packageStartDate,tellerCashCode,"UnConfirmed");





                try {
                    reportID = random.nextInt((int) (Math.random() * 99) + 11);
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                packageDuration = numberOfMonths*31;
                grandTotal=packageDuration*savingsAmount;
                daysRemaining=packageDuration-numberOfDays;
                amountRemaining=grandTotal-totalAmountSum;
                skyLightPackage = new SkyLightPackage(this.profileID, customerID, packageID, this.packageType,finalItemType, savingsAmount, this.packageDuration, reportDate, grandTotal, this.packageEndDate, "fresh");
                customerDailyReport = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, packageStartDate, "first");
                customerDailyReport.setSavingsCode(String.valueOf(skylightCode));
                skyLightPackage.setOfficeBranch(packageOffice);
                paymentBundle = new Bundle();
                paymentBundle.putString("Total", String.valueOf(totalAmountSum));
                paymentBundle.putString("PackageID", String.valueOf(packageID));
                paymentBundle.putString("SavingsID", String.valueOf(reportID));
                paymentBundle.putString("PackageType", packageType);
                paymentBundle.putString("CustomerID", String.valueOf(customerID));
                paymentBundle.putString("CustomerName", "");
                paymentBundle.putString("CustomerPhone", "");
                paymentBundle.putParcelable("Customer", customer);
                paymentBundle.putParcelable("Package", skyLightPackage);
                paymentBundle.putParcelable("Savings", customerDailyReport);
                Intent amountIntent = new Intent(MyCusNewPackAct.this, PayNowActivity.class);
                amountIntent.putExtras(paymentBundle);
                Toast.makeText(MyCusNewPackAct.this, "Your are paying NGN"+totalAmountSum, Toast.LENGTH_SHORT).show();
                savingsStartForResult.launch(new Intent(amountIntent));

            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }*/



    }


}