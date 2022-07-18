package com.skylightapp.Tellers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.LoginDirAct;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;
import com.skylightapp.Transactions.OurConfig;
import com.twilio.Twilio;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Objects;

import co.paystack.android.PaystackSdk;
import co.paystack.android.api.model.TransactionApiResponse;
import co.paystack.android.model.Card;

import static java.lang.String.valueOf;

public class PayForMyCusAct extends AppCompatActivity {
    SharedPreferences sp;
    Button paynow, homeBack,addCustomer;
    TextView p_id2, p_des2, c_total2, item_number2,aamount, item_date, totalAmount;

    String ManagersBundleString;
    String iDBundleString;
    String PackageName;
    String amountBundleString;
    String dateBundleString;
    String imageBundleString;
    double totalBundleString;

    private int selectedAllCustomerIndex;
    private int selectedCustomerIndex;
    private AppCompatSpinner spnAllCustomers, spnMyCustomers,spn_customer_packages,number_of_days_packages;
    private ArrayAdapter<Customer> allCustomerAdapter;
    private ArrayAdapter<Customer> customerAdapter;
    private ArrayList<Customer> customerArrayList;
    private ArrayList<SkyLightPackage> packageArrayList;
    private ArrayAdapter<SkyLightPackage> packagerAdapter;

    private SharedPreferences userPreferences;
    private Gson gson;
    private String json;
    EditText edtAccountName,edtAccountNumber;
    Button btnCancel,btnAddAccount,addNewCustomer,home_from_;
    TextView bankNameVerification;
    private  Customer customer;

    private Profile userProfile;
    DBHelper dbHelper;

    private boolean displayAddAccountDialogOnLaunch,isDisplayAddCustomerDialogOnLaunch ;
    private Dialog accountDialog,customerDialog;

    private GoogleMap mMap;
    private final int MY_LOCATION_REQUEST_CODE = 100;

    private Handler handler;
    private Marker m;
//    private GoogleApiClient googleApiClient;

    public final static int SENDING = 1;
    public final static int CONNECTING = 2;
    public final static int ERROR = 3;
    public final static int SENT = 4;
    public final static int SHUTDOWN = 5;

    private static final String TAG = PayForMyCusAct.class.getSimpleName();
    private static final String URL = "https://skylightciacs.com/wc-api/Tbz_WC_Paystack_Webhook/";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    Button btnFusedLocation;
    TextView tvLocation;
    LocationRequest mLocationRequest;
    Location mCurrentLocation;
    String mLastUpdateTime;
    private Location previousLocation;
    double newAccountBalance;
    SecureRandom random =new SecureRandom();
    long transactionID ;
    String refID ;
    String profileSurname;
    String profileFirstName;
    String userPix;
    String profilePhone;
    String profileEmail;
    String userName;
    String profileRole;
    int customerId8;
    String customerName;
    String customerPhoneNo;
    String customerEmail;
    double accountBalance;
    double amountSoFar;
    double packageTotal;
    long packageID,AccountID,profileID;
    double packageAmount;
    double amountRemaining,totalToday;
    int savingsCount, numberOfDaysRemaining;
    long newSavingsID ;
    LinearLayout payNow_l1,payNow_l2,payNow_l3;
    TextView total_info;
    int selectedNumberOfDays;
    double accountBalanceNow;
    ProgressDialog dialog;
    Card card1;
    TextView mTextError;
    TextView mTextBackendMessage,resultText;
    String planCode;
    long reportID;
    String dateOfReport;
    EditText noOfDays;
    String accessCode,title,packageType;
    Toolbar toolbar;
    Customer allCustomer;
    SkyLightPackage skyLightPackage;
    int myCustomerIndex;
    int allCustomerIndex;
    int packageIndex;
    Account account;
    private CustomerDailyReport customerDailyReport;
    String inProgress;
    private TextView mTextReference,txtPackSavingsID;
    private co.paystack.android.Transaction transaction;
    SkyLightPackModel package_list_model;
    Bundle paymentBundle;

    private ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),

            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK:
                            Intent intent = result.getData();
                            startActivity(new Intent(PayForMyCusAct.this, LoginDirAct.class));

                            /*Transaction transaction = new Transaction();
                            String tId = transaction.getTransactionID();

                            transaction.setTransactionId(tId);

                            transaction.setAmount(totalToday);
                            Customer customer = new Customer();
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                            String currentDateAndTime = sdf.format(new Date());
                            long customerId = customer.getuID();
                            Location location=null;
                            Transaction transaction1 = new Transaction();
                            long transactionId = Long.parseLong(transaction1.getTransactionID());
                            co.paystack.android.Transaction transaction2 = new co.paystack.android.Transaction();
                            long paystackRefId = Long.parseLong(transaction2.getReference());
                            String tittle = "New Online Payment Alert";
                            String tittle1 = "Your Successful Online Payment Alert";
                            String detail = "NGN" + totalToday + "was paid for" + customerName+""+"on"+currentDateAndTime;
                            String managerName = userProfile.getLastName() + userProfile.getFirstName();
                            String details = managerName + "paid" + "NGN" + totalToday + "for" + customerName;
                            userProfile.addTransaction( transactionId, customerName, "", customerPhoneNo, totalToday, String.valueOf(AccountID),  PackageName,currentDateAndTime, "type");
                            DBHelper applicationDb = new DBHelper (PayForMyCusAct.this);
                            customer.addTransactions(totalToday);
                            customer.addTimeLine(tittle1,details);
                            userProfile.addTimeLine(tittle1,detail);
                            parseResponse(transaction.getReceiptId());
                            newAccountBalance = accountBalance+totalToday;
                            customer.getAccount().setAccountBalance(newAccountBalance);
                            applicationDb.insertTimeLine(tittle,details,currentDateAndTime,mCurrentLocation);
                            applicationDb.saveNewTransaction( profileID, customerId8,AccountID, totalToday, transactionId,paystackRefId,currentDateAndTime);*/


                            Toast.makeText(PayForMyCusAct.this, "Activity returned ok", Toast.LENGTH_SHORT).show();
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(PayForMyCusAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_for_customer);
        random=new SecureRandom();
        refID = "SkyLightT/"+ random.nextInt((int) (Math.random() * 900000) + 100000);
        newSavingsID = random.nextInt((int) (Math.random() * 901) + 100);
        transactionID = random.nextInt((int) (Math.random() * 900) + 100);
        customer= new Customer();
        paymentBundle = new Bundle();
        resultText = findViewById(R.id.result_t);
        total_info = findViewById(R.id.total_info3);
        customerDailyReport= new CustomerDailyReport();
        dbHelper= new DBHelper(this);
        account= new Account();
        txtPackSavingsID = findViewById(R.id.pack_saving_ID3);
        spnMyCustomers = findViewById(R.id.cm_cus_Mselect);
        spn_customer_packages = findViewById(R.id.spn_cust_packages);
        noOfDays = findViewById(R.id.numberOfDay);
        home_from_ =  findViewById(R.id.home_from_3);
        homeBack = findViewById(R.id.home_from_payment3);
        customerArrayList = new ArrayList<Customer>();
        packageArrayList = new ArrayList<SkyLightPackage>();
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        TransactionApiResponse apiResponse = new TransactionApiResponse();
        boolean asd = apiResponse.hasValidReferenceAndTrans();
        String pSeckey = OurConfig.SKYLIGHT_SECRET_KEY;
        PaystackSdk.initialize(getApplicationContext());
        PaystackSdk.setPublicKey(pSeckey);
        total_info = findViewById(R.id.total_info);
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        Objects.requireNonNull(noOfDays).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String text = editable.toString();
                int noOfDays3 = Integer.parseInt(editable.toString());
                double totalForToday=noOfDays3*packageAmount;
                total_info.setText("Total to Pay for Today:NGN"+totalForToday);
            }
        });

        //cvvField = findViewById(R.id.edit_cvv3);
        paynow = findViewById(R.id.pay_now3);

        try {
            if (userProfile != null) {
                profileID=userProfile.getPID();
                profileSurname=userProfile.getProfileLastName();
                profileFirstName=userProfile.getProfileFirstName();
                profilePhone=userProfile.getProfilePhoneNumber();

                customerArrayList= userProfile.getProfileCustomers();

                customerAdapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_spinner_item, userProfile.getProfileCustomers());
                customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnMyCustomers.setAdapter(customerAdapter);
                spnMyCustomers.setSelection(0);
                myCustomerIndex = spnMyCustomers.getSelectedItemPosition();
                customer = (Customer) spnMyCustomers.getItemAtPosition(myCustomerIndex);
                doGetPackage(myCustomerIndex);



            }

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentBundle.putParcelable("Customer", customer);
                paymentBundle.putParcelable("Profile", userProfile);
                paymentBundle.putParcelable("Package", skyLightPackage);
                paymentBundle.putParcelable("SkyLightPackage", skyLightPackage);

                choosePaymentMethod(userProfile,customer,paymentBundle,skyLightPackage);
            }
        });



    }
    private void doGetPackage(int myCustomerIndex) {
        try {

            customer = (Customer) spnMyCustomers.getItemAtPosition(myCustomerIndex);
            if(customer !=null){
                customerId8=customer.getCusUID();
                packageArrayList = dbHelper.getCustomerIncompletePack(customerId8,inProgress);
                packagerAdapter = new ArrayAdapter<SkyLightPackage>(this, android.R.layout.simple_spinner_item, packageArrayList);
                packagerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_customer_packages.setAdapter(packagerAdapter);
                spn_customer_packages.setSelection(0);
                packageIndex = spn_customer_packages.getSelectedItemPosition();
                try {

                    skyLightPackage = (SkyLightPackage) spn_customer_packages.getItemAtPosition(packageIndex);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oops!");
                }

            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Oops!");
        }
    }

    private void choosePaymentMethod(Profile userProfile,Customer customer,Bundle paymentBundle,SkyLightPackage skyLightPackage) {
        Toast.makeText(PayForMyCusAct.this, "packageType Package, selected", Toast.LENGTH_SHORT).show();
        Intent savingsIntent = new Intent(PayForMyCusAct.this, PayNowActivity.class);
        savingsIntent.putExtras(paymentBundle);
    }


    @Override
    public void onPause() {
        super.onPause();

        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    private void updateTextViews() {
        if (transaction.getReference() != null) {
            resultText.setVisibility(View.VISIBLE);
            resultText.setText(String.format("Reference: %s", transaction.getReference()));
        } else {
            resultText.setVisibility(View.VISIBLE);
            resultText.setText("No transaction");
        }
    }

    private void dismissDialog() {
        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void saveTransaction( ) {

        //selectedAllCustomerIndex = spnAllCustomers.getSelectedItemPosition();

        int customerAccIndex = spnAllCustomers.getSelectedItemPosition();
        //int customerIndex = spnMyCustomers.getSelectedItemPosition();

        //Customer transactionCustomer = (Customer) spnMyCustomers.getItemAtPosition(customerIndex);
        //double accountBalance = transactionAccount.getAccountBalance();
        //String accountName = transactionAccount.getAccountName();

        //transactionAccount.addPaymentTransaction(accountName, totalBundleString);

        //CustomerTransactionFragment transactionsFragment = new CustomerTransactionFragment();
        //Bundle bundle = new Bundle();
        //.putInt("SelectedAccount", customerAccIndex);
        //transactionsFragment.setArguments(bundle);


    }
    private void parseResponse (String transactionReference){
        String message = "Skylight Payment Successful - " + transactionReference;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        startActivity(new Intent(PayForMyCusAct.this, LoginDirAct.class));

    }

}