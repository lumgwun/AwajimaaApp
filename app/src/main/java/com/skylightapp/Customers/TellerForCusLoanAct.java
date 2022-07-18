package com.skylightapp.Customers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.skylightapp.Adapters.CusSpinnerAdapter;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.skylightapp.Transactions.OurConfig;
import com.skylightapp.Transactions.TransferPayload;
import com.skylightapp.Transactions.Transfers;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.FlutterWavePayments.OTPFragment.EXTRA_OTP;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class TellerForCusLoanAct extends AppCompatActivity {
    TextInputEditText amountEt;
    TextInputLayout amountTil;
    TextView amountTv;
    TextView accountNumberTv;
    TextView bankNameTv;
    TextView beneficiaryNameTv;
    TextView transferInstructionTv;
    TextView transferStatusTv;
    AppCompatTextView txtVerify,txtResult;
    AppCompatButton verifyPaymentButton;
    AppCompatButton payButton;
    ConstraintLayout initiateChargeLayout;
    ConstraintLayout transferDetailsLayout;
    private ProgressDialog progressDialog;
    private ProgressDialog pollingProgressDialog;
    private AppCompatSpinner spnLoanAcct;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText edtBorrowableAmount;
    private Spinner spnMyCustomer, spnFromAllCustomer, spn_Borrowing_Bank;
    private AppCompatEditText loan_amount, acct_No, acct_name;
    private AppCompatButton btnConfirmBorrowing;

    ArrayList<Account> accounts;
    ArrayList<Customer> customers;
    ArrayList<Customer> AllCustomers;
    ArrayList<Loan> loans;
    Customer customer;
    private CusSpinnerAdapter cusSpinnerAdapter;
    ArrayAdapter<Account> accountAdapter;
    CusSpinnerAdapter myCustomerAdapter;
    ArrayAdapter<Customer> allCustomerAdapter;

    SharedPreferences userPreferences;
    Gson gson;
    String json,selectedBank,smsMessage;
    double accountBalance;
    Profile userProfile;
    private String mParam1;
    private String mParam2;
    protected DBHelper dbHelper;
    //long customerID;
    Random ran;
    int customerId;
    int profileID;
    String bankName, borrower,borrowingId,bank_CODE;
    Account acctOfCustomer,account;
    int borrowingID;
    Customer mySelectedCustomer,fromAllCustomer;
    String customerNames,dob,passCode,verifiedName,bankAcctNo,codeSms,customerPhoneNo, loanAcctType;
    int length;
    int myBorrowingCustomerIndex,allBorrowingCustomerIndex,code;
    Intent intent;
    int OTP,index_no, grantingID,tXID, loanOTP;
    ProgressDialog dialog;
    int accountNo,borrowerNo;
    int customerID;
    SQLiteDatabase sqLiteDatabase;
    double unAvailableAmount,availableBalance;
    private  Bundle paymentBundle,loanBundle;
    private static final String PREF_NAME = "skylight";

    ActivityResultLauncher<Intent> bStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            intent = result.getData();
                            intent = getIntent();
                            OTP = Integer.parseInt(intent.getStringExtra(EXTRA_OTP));
                            confirmBorrowing();

                        }

                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_borrowing);
        paymentBundle=new Bundle();
        customers=new ArrayList<Customer>();
        dbHelper= new DBHelper(this);
        ran = new Random();
        gson = new Gson();
        userProfile= new Profile();
        acctOfCustomer=new Account();
        mySelectedCustomer=new Customer();
        loanBundle = new Bundle();
        final Date[] readDate = {null};
        borrowingID = ran.nextInt((int) (Math.random() * 190) + 11+19);
        tXID = ran.nextInt((int) (Math.random() * 139) + 11);
        loanOTP = ThreadLocalRandom.current().nextInt(120, 1631);
        grantingID = ThreadLocalRandom.current().nextInt(1025, 10410);
        borrowingId= "SKY_Loan/"+tXID;
        code=((1 + ran.nextInt(2)) * 105 + ran.nextInt(10020));
        codeSms="Your Skylight New Loan confirmation code is:"+code;
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        String userName = userProfile.getProfileLastName();
        spn_Borrowing_Bank = findViewById(R.id.bank_borrow);
        acct_No = findViewById(R.id.account_no1);
        edtBorrowableAmount = findViewById(R.id.amount6);
        acct_name = findViewById(R.id.receiver);
        txtVerify = findViewById(R.id.verifyTxt);
        txtResult = findViewById(R.id.resultTxt);
        spnFromAllCustomer = findViewById(R.id.spnBorrowingCustomerAll);
        spnMyCustomer = findViewById(R.id.selected_customer);
        btnConfirmBorrowing = findViewById(R.id.borrow_now);
        btnConfirmBorrowing.animate();
        btnConfirmBorrowing.setEnabled(false);
        paymentBundle = getIntent().getExtras();
        if(paymentBundle !=null){
            mySelectedCustomer=paymentBundle.getParcelable("Customer");
            account=paymentBundle.getParcelable("Account");
            customerId=account.getSkyLightAcctNo();
            spnFromAllCustomer.setVisibility(View.GONE);
            spnMyCustomer.setVisibility(View.GONE);

        }
        else {
            if(userProfile !=null) {
                profileID = userProfile.getPID();
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    sqLiteDatabase = dbHelper.getWritableDatabase();
                    customers = dbHelper.getCustomerFromCurrentProfile(profileID);

                }

                myCustomerAdapter = new CusSpinnerAdapter(this,  customers);
                //myCustomerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnMyCustomer.setAdapter(myCustomerAdapter);
                spnMyCustomer.setSelection(0);
                myBorrowingCustomerIndex = spnMyCustomer.getSelectedItemPosition();

                try {

                    mySelectedCustomer = customers.get(myBorrowingCustomerIndex);

                    try {
                        if (mySelectedCustomer != null) {
                            customerID = mySelectedCustomer.getCusUID();
                            account = mySelectedCustomer.getCusAccount();
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oops!");
                }
            }
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                dbHelper.openDataBase();
                sqLiteDatabase = dbHelper.getWritableDatabase();
                AllCustomers=dbHelper.getAllCustomers11();

            }

            allCustomerAdapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_spinner_item, AllCustomers);
            allCustomerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnFromAllCustomer.setAdapter(allCustomerAdapter);
            spnFromAllCustomer.setSelection(0);

            try {
                allBorrowingCustomerIndex = spnFromAllCustomer.getSelectedItemPosition();
                fromAllCustomer = customers.get(allBorrowingCustomerIndex);

                try {
                    if (fromAllCustomer != null) {
                        customerID=fromAllCustomer.getCusUID();
                        account=fromAllCustomer.getCusAccount();
                    }
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Oops!");
            }
            if(mySelectedCustomer==null){
                mySelectedCustomer=fromAllCustomer;
            }
            if(fromAllCustomer==null){
                fromAllCustomer=mySelectedCustomer;
            }


        }
        spnLoanAcct = findViewById(R.id.loanAcctType);
        spnLoanAcct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loanAcctType = (String) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        spn_Borrowing_Bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {

            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {

                selectedBank = (String) arg0.getItemAtPosition(position);
                //selectedPlan = String.valueOf(planSpn.getSelectedItem());
                if (selectedBank.equalsIgnoreCase("Access Bank Nigeria Plc")) {
                    bank_CODE.equals("044");

                }
                if (selectedBank.equalsIgnoreCase("First Bank of Nigeria")) {

                    bank_CODE.equals("011");

                }
                if (selectedBank.equalsIgnoreCase("UBA PLC")) {

                    bank_CODE.equals("033");

                }
                if (selectedBank.equalsIgnoreCase("Heritage")) {
                    bank_CODE.equals("030");

                }
                if (selectedBank.equalsIgnoreCase("Keystone Bank")) {
                    bank_CODE.equals("082");

                }
                if (selectedBank.equalsIgnoreCase("Skye Bank")) {
                    bank_CODE.equals("076");

                }
                if (selectedBank.equalsIgnoreCase("Sterling Bank")) {
                    bank_CODE.equals("232");

                }
                if (selectedBank.equalsIgnoreCase("Union Bank")) {

                    bank_CODE.equals("032");

                }
                if (selectedBank.equalsIgnoreCase("GTBank Plc")) {

                    bank_CODE.equals("058");

                }
                if (selectedBank.equalsIgnoreCase("FCMB")) {
                    bank_CODE.equals("214");

                }
                if (selectedBank.equalsIgnoreCase("TrustBond")) {
                    bank_CODE.equals("523");

                }
                if (selectedBank.equalsIgnoreCase("SunTrust Bank")) {

                    bank_CODE.equals("100");

                }
                if (selectedBank.equalsIgnoreCase("Diamond Bank")) {

                    bank_CODE.equals("063");

                }
                if (selectedBank.equalsIgnoreCase("GT MOBILE MONEY")) {
                    bank_CODE.equals("315");

                }
                if (selectedBank.equalsIgnoreCase("FET")) {

                    bank_CODE.equals("314");

                }
                if (selectedBank.equalsIgnoreCase("Mkudi")) {
                    bank_CODE.equals("313");

                }
                if (selectedBank.equalsIgnoreCase("FSDH")) {
                    bank_CODE.equals("601");

                }
                if (selectedBank.equalsIgnoreCase("Coronation Merchant Bank")) {
                    bank_CODE.equals("559");

                }
                if (selectedBank.equalsIgnoreCase("Enterprise Bank")) {
                    bank_CODE.equals("084");

                }
                if (selectedBank.equalsIgnoreCase("Wema Bank")) {
                    bank_CODE.equals("035");

                }
                if (selectedBank.equalsIgnoreCase("Parralex")) {
                    bank_CODE.equals("526");

                }
                if (selectedBank.equalsIgnoreCase("Pagatech")) {
                    bank_CODE.equals("327");

                }
                if (selectedBank.equalsIgnoreCase("Stanbic IBTC Bank")) {
                    bank_CODE.equals("039");

                }
                if (selectedBank.equalsIgnoreCase("Fidelity Mobile")) {
                    bank_CODE.equals("318");

                }
                if (selectedBank.equalsIgnoreCase("EcoMobile")) {

                    bank_CODE.equals("307");

                }
                if (selectedBank.equalsIgnoreCase("Ecobank Plc")) {

                    bank_CODE.equals("050");

                }
                if (selectedBank.equalsIgnoreCase("JAIZ Bank")) {

                    bank_CODE.equals("301");

                }
                if (selectedBank.equalsIgnoreCase("Access Money")) {

                    bank_CODE.equals("323");

                }
                if (selectedBank.equalsIgnoreCase("Unity Bank")) {
                    bank_CODE.equals("215");

                }
                if (selectedBank.equalsIgnoreCase("CitiBank")) {

                    bank_CODE.equals("023");

                }
                if (selectedBank.equalsIgnoreCase("Fidelity Bank")) {

                    bank_CODE.equals("070");

                }
                if (selectedBank.equalsIgnoreCase("eTranzact")) {
                    bank_CODE.equals("306");

                }
                if (selectedBank.equalsIgnoreCase("Standard Chartered Bank")) {

                    bank_CODE.equals("068");

                }
                if (selectedBank.equalsIgnoreCase("Zenith Bank")) {

                    bank_CODE.equals("057");
                    if (customer != null) {
                        dob = customer.getCusDob();
                        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");


                        try {
                            readDate[0] = df.parse(dob);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(readDate[0].getTime());
                        passCode = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.MONTH) + cal.get(Calendar.YEAR));

                    }
                }
                smsMessage=codeSms;
                if (selectedBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")) {

                    bank_CODE.equals("402");

                }
                if (selectedBank.equalsIgnoreCase("Sterling Mobile")) {
                    bank_CODE.equals("326");

                }
                if (selectedBank.equalsIgnoreCase("FBNMobile")) {

                    bank_CODE.equals("309");

                }
                if (selectedBank.equalsIgnoreCase("Stanbic Mobile Money")) {

                    bank_CODE.equals("304");

                }
                if (selectedBank.equalsIgnoreCase("Page MFBank")) {

                    bank_CODE.equals("560");

                }
                if (selectedBank.equalsIgnoreCase("FortisMobile")) {

                    bank_CODE.equals("308");

                }
                if (selectedBank.equalsIgnoreCase("TagPay")) {

                    bank_CODE.equals("328");

                }
                if (selectedBank.equalsIgnoreCase("Omoluabi Mortgage Bank")) {

                    bank_CODE.equals("990");

                }
                if (selectedBank.equalsIgnoreCase("ReadyCash (Parkway)")) {

                    bank_CODE.equals("311");

                }
                if (selectedBank.equalsIgnoreCase("Eartholeum")) {

                    bank_CODE.equals("302");

                }
                if (selectedBank.equalsIgnoreCase("Hedonmark")) {

                    bank_CODE.equals("324");

                }
                if (selectedBank.equalsIgnoreCase("MoneyBox")) {

                    bank_CODE.equals("325");

                }
                if (selectedBank.equalsIgnoreCase("TeasyMobile")) {

                    bank_CODE.equals("319");

                }
                if (selectedBank.equalsIgnoreCase("NIP Virtual Bank")) {

                    bank_CODE.equals("999");

                }
                if (selectedBank.equalsIgnoreCase("VTNetworks")) {

                    bank_CODE.equals("320");

                }
                if (selectedBank.equalsIgnoreCase("Fortis Microfinance Bank")) {

                    bank_CODE.equals("501");

                }
                if (selectedBank.equalsIgnoreCase("PayAttitude Online")) {

                    bank_CODE.equals("329");

                }
                if (selectedBank.equalsIgnoreCase("ChamsMobile")) {

                    bank_CODE.equals("303");

                }
                if (selectedBank.equalsIgnoreCase("SafeTrust Mortgage Bank")) {

                    bank_CODE.equals("403");

                }
                if (selectedBank.equalsIgnoreCase("Covenant Microfinance Bank")) {

                    bank_CODE.equals("551");

                }
                if (selectedBank.equalsIgnoreCase("Imperial Homes Mortgage Bank")) {

                    bank_CODE.equals("415");

                }
                if (selectedBank.equalsIgnoreCase("NPF MicroFinance Bank")) {

                    bank_CODE.equals("552");

                }
                if (selectedBank.equalsIgnoreCase("Parralex")) {

                    bank_CODE.equals("526");

                }
                if (selectedBank.equalsIgnoreCase("Enterprise Bank")) {

                    bank_CODE.equals("084");

                }
                if (selectedBank.equalsIgnoreCase("Paycom")) {

                    bank_CODE.equals("305");

                }
                if (selectedBank.equalsIgnoreCase("Cellulant")) {

                    bank_CODE.equals("317");

                }
                if (selectedBank.equalsIgnoreCase("Heritage")) {

                    bank_CODE.equals("030");

                }
                if (selectedBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")) {

                    bank_CODE.equals("402");

                }
                if (selectedBank.equalsIgnoreCase("Heritage")) {

                    bank_CODE.equals("030");

                }
                if (selectedBank.equalsIgnoreCase("FSDH")) {

                    bank_CODE.equals("601");

                }
                if (selectedBank.equalsIgnoreCase("FET")) {

                    bank_CODE.equals("314");

                }
                if (selectedBank.equalsIgnoreCase("TCF MFB")) {

                    bank_CODE.equals("90115");

                }
                if (selectedBank.equalsIgnoreCase("GTMobile")) {

                    bank_CODE.equals("315");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        acct_No.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                length = s.length();
                if(length==10){
                    MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                    myAsyncTasks.execute();
                    btnConfirmBorrowing.setEnabled(true);
                    txtVerify.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });




        btnConfirmBorrowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mySelectedCustomer==null){
                    mySelectedCustomer=fromAllCustomer;
                }
                if(fromAllCustomer==null){
                    fromAllCustomer=mySelectedCustomer;
                }
                sendSMSMessage(customerPhoneNo,smsMessage);


            }
        });



    }

    private void confirmBorrowing() {

        code=((1 + ran.nextInt(2)) * 10010 + ran.nextInt(10000));
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        String borrowingDate = mdformat.format(calendar.getTime());

        if(acctOfCustomer !=null){
            accountBalance = acctOfCustomer.getAccountBalance();
            unAvailableAmount = 0.033 * accountBalance;
            availableBalance = accountBalance-unAvailableAmount;
            accountNo = acctOfCustomer.getSkyLightAcctNo();
            customerNames = acctOfCustomer.getAccountName();

        }


        if(mySelectedCustomer !=null){
            customerID = mySelectedCustomer.getCusUID();

        }
        if(userProfile !=null){
            profileID = userProfile.getPID();

        }

        boolean hasNum = false;
        boolean correctAcctNo = false;
        double amountToBorrow = 0.00;
        final Date[] readDate = {null};
        bankName = String.valueOf(spn_Borrowing_Bank.getSelectedItem());
        spn_Borrowing_Bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {

            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {

                selectedBank = (String) arg0.getItemAtPosition(position);
                //selectedPlan = String.valueOf(planSpn.getSelectedItem());
                if (selectedBank.equalsIgnoreCase("Access Bank Nigeria Plc")) {
                    bank_CODE.equals("044");

                }
                if (selectedBank.equalsIgnoreCase("First Bank of Nigeria")) {

                    bank_CODE.equals("011");

                }
                if (selectedBank.equalsIgnoreCase("UBA PLC")) {

                    bank_CODE.equals("033");

                }
                if (selectedBank.equalsIgnoreCase("Heritage")) {
                    bank_CODE.equals("030");

                }
                if (selectedBank.equalsIgnoreCase("Keystone Bank")) {
                    bank_CODE.equals("082");

                }
                if (selectedBank.equalsIgnoreCase("Skye Bank")) {
                    bank_CODE.equals("076");

                }
                if (selectedBank.equalsIgnoreCase("Sterling Bank")) {
                    bank_CODE.equals("232");

                }
                if (selectedBank.equalsIgnoreCase("Union Bank")) {

                    bank_CODE.equals("032");

                }
                if (selectedBank.equalsIgnoreCase("GTBank Plc")) {

                    bank_CODE.equals("058");

                }
                if (selectedBank.equalsIgnoreCase("FCMB")) {
                    bank_CODE.equals("214");

                }
                if (selectedBank.equalsIgnoreCase("TrustBond")) {
                    bank_CODE.equals("523");

                }
                if (selectedBank.equalsIgnoreCase("SunTrust Bank")) {

                    bank_CODE.equals("100");

                }
                if (selectedBank.equalsIgnoreCase("Diamond Bank")) {

                    bank_CODE.equals("063");

                }
                if (selectedBank.equalsIgnoreCase("GT MOBILE MONEY")) {
                    bank_CODE.equals("315");

                }
                if (selectedBank.equalsIgnoreCase("FET")) {

                    bank_CODE.equals("314");

                }
                if (selectedBank.equalsIgnoreCase("Mkudi")) {
                    bank_CODE.equals("313");

                }
                if (selectedBank.equalsIgnoreCase("FSDH")) {
                    bank_CODE.equals("601");

                }
                if (selectedBank.equalsIgnoreCase("Coronation Merchant Bank")) {
                    bank_CODE.equals("559");

                }
                if (selectedBank.equalsIgnoreCase("Enterprise Bank")) {
                    bank_CODE.equals("084");

                }
                if (selectedBank.equalsIgnoreCase("Wema Bank")) {
                    bank_CODE.equals("035");

                }
                if (selectedBank.equalsIgnoreCase("Parralex")) {
                    bank_CODE.equals("526");

                }
                if (selectedBank.equalsIgnoreCase("Pagatech")) {
                    bank_CODE.equals("327");

                }
                if (selectedBank.equalsIgnoreCase("Stanbic IBTC Bank")) {
                    bank_CODE.equals("039");

                }
                if (selectedBank.equalsIgnoreCase("Fidelity Mobile")) {
                    bank_CODE.equals("318");

                }
                if (selectedBank.equalsIgnoreCase("EcoMobile")) {

                    bank_CODE.equals("307");

                }
                if (selectedBank.equalsIgnoreCase("Ecobank Plc")) {

                    bank_CODE.equals("050");

                }
                if (selectedBank.equalsIgnoreCase("JAIZ Bank")) {

                    bank_CODE.equals("301");

                }
                if (selectedBank.equalsIgnoreCase("Access Money")) {

                    bank_CODE.equals("323");

                }
                if (selectedBank.equalsIgnoreCase("Unity Bank")) {
                    bank_CODE.equals("215");

                }
                if (selectedBank.equalsIgnoreCase("CitiBank")) {

                    bank_CODE.equals("023");

                }
                if (selectedBank.equalsIgnoreCase("Fidelity Bank")) {

                    bank_CODE.equals("070");

                }
                if (selectedBank.equalsIgnoreCase("eTranzact")) {
                    bank_CODE.equals("306");

                }
                if (selectedBank.equalsIgnoreCase("Standard Chartered Bank")) {

                    bank_CODE.equals("068");

                }
                if (selectedBank.equalsIgnoreCase("Zenith Bank")) {

                    bank_CODE.equals("057");
                    if (customer != null) {
                        dob = customer.getCusDob();
                        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");


                        try {
                            readDate[0] = df.parse(dob);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(readDate[0].getTime());
                        passCode = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.MONTH+1) + cal.get(Calendar.YEAR));

                    }
                }
                if (selectedBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")) {

                    bank_CODE.equals("402");

                }
                if (selectedBank.equalsIgnoreCase("Sterling Mobile")) {
                    bank_CODE.equals("326");

                }
                if (selectedBank.equalsIgnoreCase("FBNMobile")) {

                    bank_CODE.equals("309");

                }
                if (selectedBank.equalsIgnoreCase("Stanbic Mobile Money")) {

                    bank_CODE.equals("304");

                }
                if (selectedBank.equalsIgnoreCase("Page MFBank")) {

                    bank_CODE.equals("560");

                }
                if (selectedBank.equalsIgnoreCase("FortisMobile")) {

                    bank_CODE.equals("308");

                }
                if (selectedBank.equalsIgnoreCase("TagPay")) {

                    bank_CODE.equals("328");

                }
                if (selectedBank.equalsIgnoreCase("Omoluabi Mortgage Bank")) {

                    bank_CODE.equals("990");

                }
                if (selectedBank.equalsIgnoreCase("ReadyCash (Parkway)")) {

                    bank_CODE.equals("311");

                }
                if (selectedBank.equalsIgnoreCase("Eartholeum")) {

                    bank_CODE.equals("302");

                }
                if (selectedBank.equalsIgnoreCase("Hedonmark")) {

                    bank_CODE.equals("324");

                }
                if (selectedBank.equalsIgnoreCase("MoneyBox")) {

                    bank_CODE.equals("325");

                }
                if (selectedBank.equalsIgnoreCase("TeasyMobile")) {

                    bank_CODE.equals("319");

                }
                if (selectedBank.equalsIgnoreCase("NIP Virtual Bank")) {

                    bank_CODE.equals("999");

                }
                if (selectedBank.equalsIgnoreCase("VTNetworks")) {

                    bank_CODE.equals("320");

                }
                if (selectedBank.equalsIgnoreCase("Fortis Microfinance Bank")) {

                    bank_CODE.equals("501");

                }
                if (selectedBank.equalsIgnoreCase("PayAttitude Online")) {

                    bank_CODE.equals("329");

                }
                if (selectedBank.equalsIgnoreCase("ChamsMobile")) {

                    bank_CODE.equals("303");

                }
                if (selectedBank.equalsIgnoreCase("SafeTrust Mortgage Bank")) {

                    bank_CODE.equals("403");

                }
                if (selectedBank.equalsIgnoreCase("Covenant Microfinance Bank")) {

                    bank_CODE.equals("551");

                }
                if (selectedBank.equalsIgnoreCase("Imperial Homes Mortgage Bank")) {

                    bank_CODE.equals("415");

                }
                if (selectedBank.equalsIgnoreCase("NPF MicroFinance Bank")) {

                    bank_CODE.equals("552");

                }
                if (selectedBank.equalsIgnoreCase("Parralex")) {

                    bank_CODE.equals("526");

                }
                if (selectedBank.equalsIgnoreCase("Enterprise Bank")) {

                    bank_CODE.equals("084");

                }
                if (selectedBank.equalsIgnoreCase("Paycom")) {

                    bank_CODE.equals("305");

                }
                if (selectedBank.equalsIgnoreCase("Cellulant")) {

                    bank_CODE.equals("317");

                }
                if (selectedBank.equalsIgnoreCase("Heritage")) {

                    bank_CODE.equals("030");

                }
                if (selectedBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")) {

                    bank_CODE.equals("402");

                }
                if (selectedBank.equalsIgnoreCase("Heritage")) {

                    bank_CODE.equals("030");

                }
                if (selectedBank.equalsIgnoreCase("FSDH")) {

                    bank_CODE.equals("601");

                }
                if (selectedBank.equalsIgnoreCase("FET")) {

                    bank_CODE.equals("314");

                }
                if (selectedBank.equalsIgnoreCase("TCF MFB")) {

                    bank_CODE.equals("90115");

                }
                if (selectedBank.equalsIgnoreCase("GTMobile")) {

                    bank_CODE.equals("315");

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

        borrower = Objects.requireNonNull(acct_name.getText()).toString();

        try {
            amountToBorrow = Double.parseDouble(edtBorrowableAmount.getText().toString());
            bankAcctNo = acct_No.getText().toString();
            hasNum = true;

        } catch (Exception e) {

            Toast.makeText(this, "Please enter an amount to Borrow", Toast.LENGTH_SHORT).show();
        }
        if (hasNum) {
            if (amountToBorrow < 0) {
                Toast.makeText(this, "The Amount to Borrow can not be N0", Toast.LENGTH_SHORT).show();
            } else if (amountToBorrow > availableBalance) {
                Toast.makeText(this, "The maximum amount to borrow is: NGN" + availableBalance, Toast.LENGTH_SHORT).show();


            } else {
                DBHelper applicationDb = new DBHelper(this);
                ArrayList<Loan> loans = applicationDb.getLoansFromCurrentCustomer(customerID);
                boolean loanTaken = false;
                for (int iLoan = 0; iLoan < loans.size(); iLoan++) {
                    if (String.valueOf(amountToBorrow).equals(String.valueOf((loans.get(iLoan).getAmount1()))) && borrowingDate.equals(loans.get(iLoan).getLoan_date())) {
                        loanTaken = true;
                        Toast.makeText(this, "a very similar Borrowing with same date already exist!", Toast.LENGTH_LONG).show();
                    }
                    if (String.valueOf(amountToBorrow).isEmpty()) {
                        loanTaken = true;
                        Toast.makeText(this, "Amount can not be empty!", Toast.LENGTH_LONG).show();

                    } else {
                        TransferPayload transferPayload= new TransferPayload();
                        Transfers transfers = new Transfers();

                        String title = "Loan Alert";
                        String status = "Being processed";
                        Location location=null;
                        String details = customerNames + "just applied to borrow NGN" + amountToBorrow;
                        String details1 = "A loan request of NGN" + amountToBorrow + "was initiated on your Skylight account" + String.valueOf(accountNo) + "on" + borrowingDate;
                        double balanceAfterBorrowing = accountBalance - amountToBorrow;
                        acctOfCustomer.setAccountBalance(balanceAfterBorrowing);
                        userProfile.addPBorrowingTranx(acctOfCustomer, amountToBorrow);
                        mySelectedCustomer.addLoans(borrowingID, amountToBorrow, borrowingDate, status, "", 0.00);
                        mySelectedCustomer.addCusTimeLine(title, details1);

                        Transaction.TRANSACTION_TYPE type = Transaction.TRANSACTION_TYPE.BORROWING;
                        applicationDb.insertTimeLine(title,details,borrowingDate,location);
                        loanOTP = ThreadLocalRandom.current().nextInt(120, 1631);
                        grantingID = ThreadLocalRandom.current().nextInt(1025, 10410);
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            applicationDb.insertNewLoan(profileID, customerID, borrowingID, amountToBorrow, borrowingDate,
                                    bankName,bankAcctNo,borrower,accountNo, loanAcctType, code,0.00,"pending");
                            //applicationDb.overwriteAccount(userProfile, acctOfCustomer);

                        }
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            applicationDb.insertTransaction_Granting(grantingID,profileID, customerID,borrower,amountToBorrow,borrowingDate,bankName, borrower,bankAcctNo, "Loan","","","Borrowing","pending");


                        }



                        Toast.makeText(this, "Borrowing of NGN" + String.format(Locale.getDefault(), "%.2f", amountToBorrow) + " was successfully submitted for approval", Toast.LENGTH_SHORT).show();


                    }
                }
            }

            //int sendingAccIndex = spnReceivingAccount.getSelectedItemPosition();

        }
    }
    protected void sendSMSMessage(String otpPhoneNumber, String smsMessage) {
        Bundle smsBundle= new Bundle();
        smsBundle.putString(PROFILE_PHONE, otpPhoneNumber);
        smsBundle.putString("USER_PHONE", otpPhoneNumber);
        smsBundle.putString("smsMessage", smsMessage);
        smsBundle.putString("from","Skylight");
        smsBundle.putString("to", otpPhoneNumber);
        Intent itemPurchaseIntent = new Intent(TellerForCusLoanAct.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }
    public class MyAsyncTasks extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result1 = new StringBuilder();
            json = "";
            JSONObject object = new JSONObject();
            spn_Borrowing_Bank = findViewById(R.id.bank_borrow);
            acct_No = findViewById(R.id.account_no1);
            final Date[] readDate = {null};
            edtBorrowableAmount = findViewById(R.id.amount6);
            //amountToBorrow = Double.parseDouble(edtBorrowableAmount.getText().toString());
            bankAcctNo=acct_No.getText().toString();
            spn_Borrowing_Bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onClick(View v) {

                }

                @Override
                public void onItemSelected(AdapterView<?> arg0, View view,
                                           int position, long row_id) {

                    selectedBank = (String) arg0.getItemAtPosition(position);
                    //selectedPlan = String.valueOf(planSpn.getSelectedItem());
                    if (selectedBank.equalsIgnoreCase("Access Bank Nigeria Plc")) {
                        bank_CODE.equals("044");

                    }
                    if (selectedBank.equalsIgnoreCase("First Bank of Nigeria")) {

                        bank_CODE.equals("011");

                    }
                    if (selectedBank.equalsIgnoreCase("UBA PLC")) {

                        bank_CODE.equals("033");

                    }
                    if (selectedBank.equalsIgnoreCase("Heritage")) {
                        bank_CODE.equals("030");

                    }
                    if (selectedBank.equalsIgnoreCase("Keystone Bank")) {
                        bank_CODE.equals("082");

                    }
                    if (selectedBank.equalsIgnoreCase("Skye Bank")) {
                        bank_CODE.equals("076");

                    }
                    if (selectedBank.equalsIgnoreCase("Sterling Bank")) {
                        bank_CODE.equals("232");

                    }
                    if (selectedBank.equalsIgnoreCase("Union Bank")) {

                        bank_CODE.equals("032");

                    }
                    if (selectedBank.equalsIgnoreCase("GTBank Plc")) {

                        bank_CODE.equals("058");

                    }
                    if (selectedBank.equalsIgnoreCase("FCMB")) {
                        bank_CODE.equals("214");

                    }
                    if (selectedBank.equalsIgnoreCase("TrustBond")) {
                        bank_CODE.equals("523");

                    }
                    if (selectedBank.equalsIgnoreCase("SunTrust Bank")) {

                        bank_CODE.equals("100");

                    }
                    if (selectedBank.equalsIgnoreCase("Diamond Bank")) {

                        bank_CODE.equals("063");

                    }
                    if (selectedBank.equalsIgnoreCase("GT MOBILE MONEY")) {
                        bank_CODE.equals("315");

                    }
                    if (selectedBank.equalsIgnoreCase("FET")) {

                        bank_CODE.equals("314");

                    }
                    if (selectedBank.equalsIgnoreCase("Mkudi")) {
                        bank_CODE.equals("313");

                    }
                    if (selectedBank.equalsIgnoreCase("FSDH")) {
                        bank_CODE.equals("601");

                    }
                    if (selectedBank.equalsIgnoreCase("Coronation Merchant Bank")) {
                        bank_CODE.equals("559");

                    }
                    if (selectedBank.equalsIgnoreCase("Enterprise Bank")) {
                        bank_CODE.equals("084");

                    }
                    if (selectedBank.equalsIgnoreCase("Wema Bank")) {
                        bank_CODE.equals("035");

                    }
                    if (selectedBank.equalsIgnoreCase("Parralex")) {
                        bank_CODE.equals("526");

                    }
                    if (selectedBank.equalsIgnoreCase("Pagatech")) {
                        bank_CODE.equals("327");

                    }
                    if (selectedBank.equalsIgnoreCase("Stanbic IBTC Bank")) {
                        bank_CODE.equals("039");

                    }
                    if (selectedBank.equalsIgnoreCase("Fidelity Mobile")) {
                        bank_CODE.equals("318");

                    }
                    if (selectedBank.equalsIgnoreCase("EcoMobile")) {

                        bank_CODE.equals("307");

                    }
                    if (selectedBank.equalsIgnoreCase("Ecobank Plc")) {

                        bank_CODE.equals("050");

                    }
                    if (selectedBank.equalsIgnoreCase("JAIZ Bank")) {

                        bank_CODE.equals("301");

                    }
                    if (selectedBank.equalsIgnoreCase("Access Money")) {

                        bank_CODE.equals("323");

                    }
                    if (selectedBank.equalsIgnoreCase("Unity Bank")) {
                        bank_CODE.equals("215");

                    }
                    if (selectedBank.equalsIgnoreCase("CitiBank")) {

                        bank_CODE.equals("023");

                    }
                    if (selectedBank.equalsIgnoreCase("Fidelity Bank")) {

                        bank_CODE.equals("070");

                    }
                    if (selectedBank.equalsIgnoreCase("eTranzact")) {
                        bank_CODE.equals("306");

                    }
                    if (selectedBank.equalsIgnoreCase("Standard Chartered Bank")) {

                        bank_CODE.equals("068");

                    }
                    if (selectedBank.equalsIgnoreCase("Zenith Bank")) {

                        bank_CODE.equals("057");
                        if (customer != null) {
                            dob = customer.getCusDob();
                            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy-MM-dd");


                            try {
                                readDate[0] = df.parse(dob);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(readDate[0].getTime());
                            passCode = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.MONTH) + cal.get(Calendar.YEAR));

                        }
                    }
                    if (selectedBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")) {

                        bank_CODE.equals("402");

                    }
                    if (selectedBank.equalsIgnoreCase("Sterling Mobile")) {
                        bank_CODE.equals("326");

                    }
                    if (selectedBank.equalsIgnoreCase("FBNMobile")) {

                        bank_CODE.equals("309");

                    }
                    if (selectedBank.equalsIgnoreCase("Stanbic Mobile Money")) {

                        bank_CODE.equals("304");

                    }
                    if (selectedBank.equalsIgnoreCase("Page MFBank")) {

                        bank_CODE.equals("560");

                    }
                    if (selectedBank.equalsIgnoreCase("FortisMobile")) {

                        bank_CODE.equals("308");

                    }
                    if (selectedBank.equalsIgnoreCase("TagPay")) {

                        bank_CODE.equals("328");

                    }
                    if (selectedBank.equalsIgnoreCase("Omoluabi Mortgage Bank")) {

                        bank_CODE.equals("990");

                    }
                    if (selectedBank.equalsIgnoreCase("ReadyCash (Parkway)")) {

                        bank_CODE.equals("311");

                    }
                    if (selectedBank.equalsIgnoreCase("Eartholeum")) {

                        bank_CODE.equals("302");

                    }
                    if (selectedBank.equalsIgnoreCase("Hedonmark")) {

                        bank_CODE.equals("324");

                    }
                    if (selectedBank.equalsIgnoreCase("MoneyBox")) {

                        bank_CODE.equals("325");

                    }
                    if (selectedBank.equalsIgnoreCase("TeasyMobile")) {

                        bank_CODE.equals("319");

                    }
                    if (selectedBank.equalsIgnoreCase("NIP Virtual Bank")) {

                        bank_CODE.equals("999");

                    }
                    if (selectedBank.equalsIgnoreCase("VTNetworks")) {

                        bank_CODE.equals("320");

                    }
                    if (selectedBank.equalsIgnoreCase("Fortis Microfinance Bank")) {

                        bank_CODE.equals("501");

                    }
                    if (selectedBank.equalsIgnoreCase("PayAttitude Online")) {

                        bank_CODE.equals("329");

                    }
                    if (selectedBank.equalsIgnoreCase("ChamsMobile")) {

                        bank_CODE.equals("303");

                    }
                    if (selectedBank.equalsIgnoreCase("SafeTrust Mortgage Bank")) {

                        bank_CODE.equals("403");

                    }
                    if (selectedBank.equalsIgnoreCase("Covenant Microfinance Bank")) {

                        bank_CODE.equals("551");

                    }
                    if (selectedBank.equalsIgnoreCase("Imperial Homes Mortgage Bank")) {

                        bank_CODE.equals("415");

                    }
                    if (selectedBank.equalsIgnoreCase("NPF MicroFinance Bank")) {

                        bank_CODE.equals("552");

                    }
                    if (selectedBank.equalsIgnoreCase("Parralex")) {

                        bank_CODE.equals("526");

                    }
                    if (selectedBank.equalsIgnoreCase("Enterprise Bank")) {

                        bank_CODE.equals("084");

                    }
                    if (selectedBank.equalsIgnoreCase("Paycom")) {

                        bank_CODE.equals("305");

                    }
                    if (selectedBank.equalsIgnoreCase("Cellulant")) {

                        bank_CODE.equals("317");

                    }
                    if (selectedBank.equalsIgnoreCase("Heritage")) {

                        bank_CODE.equals("030");

                    }
                    if (selectedBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")) {

                        bank_CODE.equals("402");

                    }
                    if (selectedBank.equalsIgnoreCase("Heritage")) {

                        bank_CODE.equals("030");

                    }
                    if (selectedBank.equalsIgnoreCase("FSDH")) {

                        bank_CODE.equals("601");

                    }
                    if (selectedBank.equalsIgnoreCase("FET")) {

                        bank_CODE.equals("314");

                    }
                    if (selectedBank.equalsIgnoreCase("TCF MFB")) {

                        bank_CODE.equals("90115");

                    }
                    if (selectedBank.equalsIgnoreCase("GTMobile")) {

                        bank_CODE.equals("315");

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }

            });

            try (CloseableHttpClient httpclient = HttpClientBuilder.create().build()) {
                HttpPost httpPost = new HttpPost("https://api.flutterwave.com/v3/accounts/resolve");

                //https://kyc-service.k8.isw.la/api/v1/verifications
                //"https://sandbox.interswitchng.com/api/v1/inquiry/bank-code/SORT_CODE/account/ACCOUNT_NUMBER"
                try {
                    //object.put("type", "BANK_ACCOUNT");
                    object.put("account_number", bankAcctNo);
                    object.put("account_bank", bank_CODE);

                    //object.put("bankCode", bank_CODE);
                    //object.put("country", "NGN");

                    //checkout.open(PayNowActivity.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                json = object.toString();

                StringEntity se = new StringEntity(json);
                httpPost.setEntity(se);
                /*MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\"account_number\":\bankAcctNo,\"account_bank\":\bank_CODE}");
                Request request = new Request.Builder()
                        .url("https://api.flutterwave.com/v3/accounts/resolve")

                        .post(body)

                        .addHeader("Accept", "application/json")

                        .addHeader("Authorization", OurConfig.SKYLIGHT_SECRET_KEY)

                        .addHeader("Content-Type", "application/json")

                        .build();*/

                httpPost.setHeader("Authorization", OurConfig.SKYLIGHT_SECRET_KEY);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpclient.execute(httpPost);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(httpResponse.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result1.append(line);
                }
                Log.d("do Resolve request", result1.toString());
                if (!String.valueOf(httpResponse.getStatusLine().getStatusCode()).startsWith("2") && !httpResponse.getEntity().getContentType().getValue().contains("json")) {
                    return null;
                }
                if (httpResponse.getStatusLine().getStatusCode() == 500) {
                    return "there is an error with the data";
                } else {
                    txtVerify.setText(result1.toString());
                    return result1.toString();

                }


            } catch (UnsupportedEncodingException ex) {
                Log.d("error", Arrays.toString(ex.getStackTrace()));
                Log.d("InputStream", ex.getLocalizedMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result1.toString();

        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TellerForCusLoanAct.this);
            progressDialog.setMessage("processing results");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(String result1) {
            progressDialog.dismiss();
            super.onPostExecute(result1);
            if (result1 != null) {
                try {

                    JSONObject jsonObject = new JSONObject(result1);
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(index_no);
                    String account_number = jsonObject1.getString("account_number");
                    String account_name = jsonObject1.getString("account_name");
                    txtResult.setVisibility(View.VISIBLE);
                    txtResult.setText(account_name);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                TellerForCusLoanAct.this.txtResult.setText(String.format("There was a problem getting a name from the account details"));
                dismissDialog();
            }

        }
    }
    private void dismissDialog() {
        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    @Override
    public void onPause() {
        super.onPause();

        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }
}