package com.skylightapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayInitializer;
import com.flutterwave.raveandroid.RavePayManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Customers.NewCustomerDrawer;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Transactions.OurConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.api.model.TransactionApiResponse;
import co.paystack.android.exceptions.ExpiredAccessCodeException;
import co.paystack.android.model.Card;
import co.paystack.android.model.Charge;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TYPE;
import static com.skylightapp.Transactions.OurConfig.SKYLIGHT_PUBLIC_KEY;
import static com.skylightapp.Transactions.OurConfig.SKYLIGHT_SECRET_KEY;
import static com.skylightapp.Transactions.OurConfig.TWILIO_ACCOUNT_SID;
import static com.skylightapp.Transactions.OurConfig.TWILIO_AUTH_TOKEN;
import static java.lang.String.valueOf;

public class PayNowActivity extends AppCompatActivity  {
    SharedPreferences sp;
    Button paynow, homeBack,addCustomer;
    TextView p_id2, p_des2, c_total2, item_number2,aamount, item_date, totalAmount;

    private Card card;
    private Charge charge;

    private EditText emailField,c_name1,c_name2,c_phone2,cEmail2,cAddress2;
    private EditText cardNumberField;
    private EditText expiryMonthField;
    private EditText expiryYearField;
    private EditText cvvField;

    private String email, cardNumber, cvv;
    private int expiryMonth, expiryYear;

    String ManagersBundleString;
    String iDBundleString;
    String PackageName;
    String amountBundleString;
    String dateBundleString;
    String imageBundleString;
    double totalBundleString;

    private int selectedAllCustomerIndex;
    private int selectedCustomerIndex;

    private SharedPreferences userPreferences;
    private Gson gson;
    private String json;
    EditText edtAccountName,edtNoOfDays;
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

    private static final String TAG = PayNowActivity.class.getSimpleName();
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
    int transactionID ;
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
    String customerPhoneNo,officeBranch;
    String customerEmail;
    double accountBalance;
    double amountSoFar;
    double packageTotal;
    int packageID,AccountID,profileID;
    double packageAmount;
    double amountRemaining,totalToday;
    int savingsCount, numberOfDaysRemaining;
    int newSavingsID,accountID ;
    TextView total_info;
    int selectedNumberOfDays;
    double accountBalanceNow;
    ProgressDialog dialog;
    Card card1;
    TextView mTextError;
    TextView mTextBackendMessage,resultText;
    String planCode;
    int reportID;
    String dateOfReport;
    String accessCode,title,packageType;
    Toolbar toolbar;
    Customer allCustomer;
    SkyLightPackage skyLightPackage;
    int packageIndex;
    Account account;
    private CustomerDailyReport customerDailyReport;
    String inProgress;
    private TextView mTextReference,txtPackSavingsID;
    private Transaction transaction;
    SkyLightPackModel package_list_model;
    Bundle totalBundle;
    private  int noOfDays3;
    String paymentType,itemName;
    int packageDaysRem;
    int transactionId;
    LinearLayoutCompat layoutPaystack;
    com.skylightapp.Classes.Transaction Skylightransaction;
    Button btnLayoutFlutter, btnLayoutPaystack;
    private static final String PREF_NAME = "skylight";
    RavePayInitializer ravePayInitializer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_pay_now);
        Skylightransaction= new com.skylightapp.Classes.Transaction();
        random = new SecureRandom();
        FirebaseApp.initializeApp(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        refID = "SkyLightT/"+ random.nextInt((int) (Math.random() * 100) + 1010);
        newSavingsID = random.nextInt((int) (Math.random() * 101) + 100);
        transactionID = random.nextInt((int) (Math.random() * 100) + 100);
        customer= new Customer();
        totalBundle= new Bundle();
        userProfile=new Profile();
        dbHelper= new DBHelper(this);
        account= new Account();
        gson = new Gson();
        total_info = findViewById(R.id.total_info);
        layoutPaystack = findViewById(R.id.layoutPayNow);
        btnLayoutFlutter = findViewById(R.id.flutterPay);
        btnLayoutPaystack = findViewById(R.id.payStack_Pay);
        customerDailyReport= new CustomerDailyReport();
        txtPackSavingsID = findViewById(R.id.pack_saving_ID);
        resultText = findViewById(R.id.result_toolbar);
        edtNoOfDays = findViewById(R.id.edit_No_Days);

        try {

            //totalBundle = getIntent().getExtras().getBundle("paymentBundle") ;
            totalBundle = this.getIntent().getExtras();


        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        if(totalBundle !=null){

            dateOfReport = totalBundle.getString("Date");
            packageType = totalBundle.getString(PACKAGE_TYPE);
            itemName = totalBundle.getString("ItemName");
            packageID = totalBundle.getInt("Package Id");
            txtPackSavingsID.setText(MessageFormat.format("Package ID:{0}", packageID));
            AccountID = totalBundle.getInt("Account ID");
            account = totalBundle.getParcelable("Account");
            customer = totalBundle.getParcelable("Customer");
            skyLightPackage = totalBundle.getParcelable("Package");
            skyLightPackage = totalBundle.getParcelable("SkyLightPackage");
            package_list_model = totalBundle.getParcelable("Package1");
            transaction = totalBundle.getParcelable("Transaction");
            customerDailyReport = totalBundle.getParcelable("Savings");
            reportID = totalBundle.getInt("Report ID");
            planCode = totalBundle.getString("Plan Code");
            accessCode = totalBundle.getString("Access Code");
            packageAmount = totalBundle.getDouble("Amount");
            selectedNumberOfDays = totalBundle.getInt("Number of Days");
            totalToday = totalBundle.getDouble("Total");

            resultText.setText("Package"+packageType+","+itemName+"/"+packageID);
            if(packageID==0){
                resultText.setText("No Selected Package");

            }
            if(selectedNumberOfDays==0){
                try {

                    edtNoOfDays.setActivated(true);
                    Objects.requireNonNull(edtNoOfDays).addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            noOfDays3 = Integer.parseInt(editable.toString());
                            totalToday=packageAmount*noOfDays3;
                            total_info.setText("Total to Pay for Today:NGN"+totalToday);
                        }
                    });

                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }
            if(customer !=null){
                customerId8=customer.getCusUID();
                customerName=customer.getCusSurname()+","+customer.getCusFirstName();
                customerEmail=customer.getCusEmail();
                customerPhoneNo=customer.getCusPhoneNumber();

            }
            if(skyLightPackage !=null){
                packageDaysRem=skyLightPackage.getRemainingDays();
                amountRemaining=skyLightPackage.getAmountRemaining();
                packageTotal=skyLightPackage.getGrandTotal();
                amountSoFar=skyLightPackage.getAmount_collected();

            }
            //packageDaysRem = totalBundle.getInt("Day Remaining");
            //amountRemaining = totalBundle.getDouble("Amount Remaining");
            savingsCount = totalBundle.getInt("Package Count");
            //packageTotal = totalBundle.getDouble("Grand Total");
            //amountSoFar = totalBundle.getDouble("Amount So Far");
            accountBalanceNow = totalBundle.getDouble("Package Account Balance");
            //customerName = totalBundle.getString("Customer Name");
            //customerId8 = totalBundle.getLong("Customer ID");
            //customerEmail = totalBundle.getString("Customer Email");
            //customerPhoneNo = totalBundle.getString("Customer Phone Number");

            if(totalToday==0){
                edtNoOfDays.setActivated(true);
                Objects.requireNonNull(edtNoOfDays).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        int noOfDays3 = Integer.parseInt(editable.toString());
                        totalToday=packageAmount*noOfDays3;
                        total_info.setText("Total to Pay for Today:NGN"+totalToday);
                    }
                });

            }
            else  {
                totalToday=packageAmount*selectedNumberOfDays;
                total_info.setText("Total to Pay for Today:NGN"+totalToday);
                edtNoOfDays.setActivated(false);

            }

        }else {
            resultText.setVisibility(View.VISIBLE);
            resultText.setText("Sorry !,You have not selected any Package for savings Payment");
            finish();


        }


        paynow = findViewById(R.id.pay_now);
        home_from_ =  findViewById(R.id.home_from_);
        homeBack = findViewById(R.id.home_from_payment);
        //packageArrayList = new ArrayList<SkyLightPackage>();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        try {
            if (userProfile != null) {
                profileID=userProfile.getPID();
                profileSurname=userProfile.getProfileLastName();
                profileFirstName=userProfile.getProfileFirstName();
                profilePhone=userProfile.getProfilePhoneNumber();
            }


        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        TransactionApiResponse apiResponse = new TransactionApiResponse();
        boolean asd = apiResponse.hasValidReferenceAndTrans();
        String pSeckey = OurConfig.SKYLIGHT_SECRET_KEY;

        PaystackSdk.initialize(getApplicationContext());
        PaystackSdk.setPublicKey(pSeckey);

        cardNumberField = (EditText) findViewById(R.id.edit_card_number);

        expiryMonthField = (EditText) findViewById(R.id.edit_expiry_month);
        total_info = findViewById(R.id.total_info);
        expiryYearField = (EditText) findViewById(R.id.edit_expiry_year);
        cvvField = findViewById(R.id.edit_cvv);
        Twilio.init("AC5e05dc0a793a29dc1da2eabdebd6c28d", "39410e8b813c131da386f3d7bb7f94f7");



        if(userProfile !=null){
            try {
                profileSurname =userProfile.getProfileLastName();
                profileID = userProfile.getPID();
                profileFirstName =userProfile.getProfileFirstName();
                //userPix=userProfile.getUProfilePicture();
                profilePhone =userProfile.getProfilePhoneNumber();
                profileEmail = userProfile.getProfileEmail();
                userName = userProfile.getProfileUserName();
                profileRole = userProfile.getProfileRole();

            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }

        }

        Objects.requireNonNull(expiryMonthField).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() == 2 && !s.toString().contains("/")) {
                    s.append("/");
                }
            }

        });
        paynow.setOnClickListener(this::revealPayStack);
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doPayStackPayment(totalBundle);

            }
        });
        btnLayoutFlutter.setOnClickListener(this::revealFlutterwave);
        btnLayoutFlutter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processFlutterPayment(profileEmail, amountSoFar, packageTotal,numberOfDaysRemaining, amountRemaining, accountBalance, profileID, profileSurname, profileFirstName, profilePhone, skyLightPackage, totalToday,selectedNumberOfDays, packageAmount, reportID, customerDailyReport, transaction, customer, account, packageID, packageType);
            }
        });



    }
    private void processFlutterPayment(String profileEmail, double amountSoFar, double packageTotal, int numberOfDaysRemaining, double amountRemaining, double accountBalance, long profileID, String profileSurname, String profileFirstName, String profilePhone, SkyLightPackage skyLightPackage, double totalToday, int selectedNumberOfDays, double packageAmount, long reportID, CustomerDailyReport customerDailyReport, Transaction transaction, Customer customer, Account account,  long packageID, String packageType) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date newDate = calendar.getTime();
        final Date[] readDate = {null};
        String theDate = sdf.format(newDate);

        try {
            new RavePayManager(this)
                    .setAmount(this.totalToday)
                    .setEmail(this.profileEmail)
                    .setCountry("NG")
                    .setCurrency("NGN")
                    .setfName(this.profileFirstName)
                    .setlName(this.profileSurname)
                    .setNarration("Payment for package"+ this.packageID)
                    .setPublicKey(SKYLIGHT_PUBLIC_KEY)
                    .setEncryptionKey(SKYLIGHT_SECRET_KEY)
                    .setTxRef(refID+"/"+System.currentTimeMillis())
                    .acceptAccountPayments(true)
                    .acceptCardPayments(true)
                    .acceptAchPayments(true)
                    .acceptBarterPayments(true)
                    //.acceptFrancMobileMoneyPayments(true)
                    .acceptGHMobileMoneyPayments(true)
                    .acceptRwfMobileMoneyPayments(true)
                    .acceptSaBankPayments(true)
                    .acceptUkPayments(true)
                    .acceptUssdPayments(true)
                    .acceptZmMobileMoneyPayments(true)
                    .allowSaveCardFeature(true)
                    .acceptUgMobileMoneyPayments(true)
                    .acceptBankTransferPayments(true)
                    .acceptMpesaPayments(true)
                    .onStagingEnv(false)
                    .withTheme(R.style.MyFlutterWaveTheme)
                    .shouldDisplayFee(true)
                    .showStagingLabel(false)
                    .initialize();
        } catch (Exception e) {
            Toast.makeText(PayNowActivity.this, "An error occurred while Processing Flutter Payment", Toast.LENGTH_LONG).show();


        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String paymentMessage="";
        if(totalBundle !=null) {

            dateOfReport = totalBundle.getString("Date");
            packageType = totalBundle.getString(PACKAGE_TYPE);
            packageID = totalBundle.getInt("Package Id");
            txtPackSavingsID.setText(MessageFormat.format("Package ID:{0}", packageID));
            AccountID = totalBundle.getInt("Account ID");
            account = totalBundle.getParcelable("Account");
            customer = totalBundle.getParcelable("Customer");
            skyLightPackage = totalBundle.getParcelable("Package");
            skyLightPackage = totalBundle.getParcelable("SkyLightPackage");
            package_list_model = totalBundle.getParcelable("Package1");
            transaction = totalBundle.getParcelable("Transaction");
            customerDailyReport = totalBundle.getParcelable("Savings");
            reportID = totalBundle.getInt("Report ID");
            planCode = totalBundle.getString("Plan Code");
            accessCode = totalBundle.getString("Access Code");
            packageAmount = totalBundle.getDouble("Amount");
            selectedNumberOfDays = totalBundle.getInt("Number of Days");
            totalToday = totalBundle.getDouble("Total");
            if (selectedNumberOfDays == 0) {
                try {

                    edtNoOfDays.setActivated(true);
                    Objects.requireNonNull(edtNoOfDays).addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            noOfDays3 = Integer.parseInt(editable.toString());
                            totalToday = packageAmount * noOfDays3;
                            total_info.setText("Total to Pay for Today:NGN" + totalToday);
                        }
                    });

                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }
            if (customer != null) {
                customerId8 = customer.getCusUID();
                customerName = customer.getCusSurname() + "," + customer.getCusFirstName();
                customerEmail = customer.getCusEmail();
                customerPhoneNo = customer.getCusPhoneNumber();

            }
            if(customer !=null){
                customerId8 = customer.getCusUID();
                customerName=customer.getCusSurname()+","+customer.getCusFirstName();
               officeBranch=customer.getCusOfficeBranch();

            }

            if(account !=null){
                accountID=account.getSkyLightAcctNo();
            }
            if (skyLightPackage != null) {
                packageDaysRem = skyLightPackage.getRemainingDays();
                amountRemaining = skyLightPackage.getAmountRemaining();
                packageTotal = skyLightPackage.getGrandTotal();
                amountSoFar = skyLightPackage.getAmount_collected();

            }
        }


        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_LONG).show();
                Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
                sendTransactionMessage();
                newSavingsID = random.nextInt((int) (Math.random() * 1201) + 10876);
                transactionID = random.nextInt((int) (Math.random() * 401) + 1780);

                refID = "SkyLightT/"+ random.nextInt((int) (Math.random() * 900000) + 100000);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                String currentDateAndTime = sdf.format(new Date());

                transactionId = transaction.getTransactionID();
                //long accountId = transactionAccount.getId();
                co.paystack.android.Transaction transaction2 = new co.paystack.android.Transaction();
                long paystackRefId = Long.parseLong(transaction2.getReference());
                String tittle = "New Online Payment Alert";
                String tittle1 = "Your Successful Online Payment Alert";
                String detail = "NGN" + totalToday + "was paid for" + customerName+""+"on"+currentDateAndTime;
                String managerName = userProfile.getProfileLastName() + userProfile.getProfileFirstName();
                String details = managerName + "paid" + "NGN" + totalToday + "for" + customerName;
                userProfile.addTransaction( transactionId, customerName, "", customerPhoneNo, totalToday, String.valueOf(AccountID),  PackageName,currentDateAndTime, "type");
                DBHelper applicationDb = new DBHelper (this);
                customer.addCusTransactions(totalToday);
                customer.addCusTimeLine(tittle1,details);
                parseResponse(transaction.getReceiptId());
                newAccountBalance = accountBalance+totalToday;
                userProfile.addTimeLine(tittle1,detail);
                customer.getCusAccount().setAccountBalance(newAccountBalance);
                Skylightransaction= new com.skylightapp.Classes.Transaction(transactionID, accountID, currentDateAndTime, 1001001, AccountID, "Skylight", customerName, totalToday, Transaction.TRANSACTION_TYPE.DEPOSIT, "",officeBranch, "", "", "");

                applicationDb.insertDailyReport(packageID,newSavingsID,profileID, customerId8,currentDateAndTime,packageAmount,selectedNumberOfDays,totalToday,amountSoFar+totalToday,amountRemaining,numberOfDaysRemaining,"paid");
                skyLightPackage.addSavings(profileID,customerId8,0,packageAmount,selectedNumberOfDays,totalToday,numberOfDaysRemaining,amountRemaining,currentDateAndTime,"paid");
                applicationDb.insertTimeLine(tittle,details,currentDateAndTime,mCurrentLocation);
                applicationDb.saveNewTransaction(profileID, customerId8,Skylightransaction, accountID, "Skylight", customerName, Transaction.TRANSACTION_TYPE.DEPOSIT,totalToday, transactionID, officeBranch, currentDateAndTime);


                Intent itemPurchaseIntent = new Intent(PayNowActivity.this, NewCustomerDrawer.class);
                itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(itemPurchaseIntent);
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void sendTransactionMessage() {
        Bundle smsBundle= new Bundle();

        String customerName=null;
        if (userProfile != null) {
            customer=userProfile.getProfileCus();
            if(customer !=null){
                customerId8=customer.getCusUID();
            }
            profileID = userProfile.getPID();
            profileSurname = userProfile.getProfileLastName();
            profileFirstName = userProfile.getProfileFirstName();
            profilePhone = userProfile.getProfilePhoneNumber();
            profileEmail=userProfile.getProfileEmail();


        }
        customerName=profileSurname+","+profileFirstName;
        String smsMessage=customerName+""+"Your online payment of"+"was successful";
        smsBundle.putString(PROFILE_PHONE,profilePhone);
        smsBundle.putString("USER_PHONE",profilePhone);
        smsBundle.putString("smsMessage",smsMessage);
        smsBundle.putString("from","Skylight");
        smsBundle.putString("to",profilePhone);
        Intent itemPurchaseIntent = new Intent(PayNowActivity.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }

    private void doPayStackPayment(Bundle finalTotalBundle) {
        try {
            layoutPaystack.setVisibility(View.VISIBLE);
            performCharge(true);
        } catch (Exception e) {
            Toast.makeText(PayNowActivity.this, "An error occurred while charging card", Toast.LENGTH_LONG).show();


        }

    }


    private Card loadCardFromForm() {

        try {
            cardNumber = cardNumberField.getText().toString().trim();
        } catch (Exception ignored) {
        }
        try {
            cvv = cvvField.getText().toString().trim();
        } catch (Exception ignored) {
        }


        card1 = (Card) new Card.Builder(cardNumber, 0, 0, "").build();
        card1.setCvc(cvv);

        String sMonth = expiryMonthField.getText().toString().trim();
        //expiryMonth = Integer.parseInt(expiryMonthField.getText().toString().trim());
        int month = 0;
        try {
            month = Integer.parseInt(sMonth);
        } catch (Exception ignored) {
        }

        card1.setExpiryMonth(month);

        //expiryYear = Integer.parseInt(expiryYearField.getText().toString().trim());


        String sYear = expiryYearField.getText().toString().trim();
        int year = 0;
        try {
            year = Integer.parseInt(sYear);
        } catch (Exception ignored) {
        }
        card.setExpiryYear(year);

        return card;
    }

    @Override
    public void onPause() {
        super.onPause();

        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }


    private void performCharge(boolean local) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if(totalBundle !=null) {

            dateOfReport = totalBundle.getString("Date");
            packageType = totalBundle.getString(PACKAGE_TYPE);
            packageID = totalBundle.getInt("Package Id");
            txtPackSavingsID.setText(MessageFormat.format("Package ID:{0}", packageID));
            AccountID = totalBundle.getInt("Account ID");
            account = totalBundle.getParcelable("Account");
            customer = totalBundle.getParcelable("Customer");
            skyLightPackage = totalBundle.getParcelable("Package");
            skyLightPackage = totalBundle.getParcelable("SkyLightPackage");
            package_list_model = totalBundle.getParcelable("Package1");
            transaction = totalBundle.getParcelable("Transaction");
            customerDailyReport = totalBundle.getParcelable("Savings");
            reportID = totalBundle.getInt("Report ID");
            planCode = totalBundle.getString("Plan Code");
            accessCode = totalBundle.getString("Access Code");
            packageAmount = totalBundle.getDouble("Amount");
            selectedNumberOfDays = totalBundle.getInt("Number of Days");
            totalToday = totalBundle.getDouble("Total");
            if(selectedNumberOfDays==0){
                try {
                    selectedNumberOfDays = Integer.parseInt(edtNoOfDays.getText().toString().trim());
                    totalToday=packageAmount*selectedNumberOfDays;

                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }

            totalToday=packageAmount*selectedNumberOfDays;
            if (customer != null) {
                customerId8 = customer.getCusUID();
                customerName = customer.getCusSurname() + "," + customer.getCusFirstName();
                customerEmail = customer.getCusEmail();
                customerPhoneNo = customer.getCusPhoneNumber();

            }
            if (skyLightPackage != null) {
                packageDaysRem = skyLightPackage.getRemainingDays();
                amountRemaining = skyLightPackage.getAmountRemaining();
                packageTotal = skyLightPackage.getGrandTotal();
                amountSoFar = skyLightPackage.getAmount_collected();

            }
        }


        charge = new Charge();
        charge.setCard(loadCardFromForm());

        dialog = new ProgressDialog(PayNowActivity.this);
        dialog.setMessage("Performing Skylight transaction... please wait");
        dialog.show();
        if (local) {

            charge.setEmail(customerEmail);
            charge.setAmount(Integer.parseInt(valueOf(totalToday)));
            charge.setCard(card1);
            charge.setReference(refID);
            charge.setPlan(planCode);
            charge.setAccessCode(accessCode);
            try {
                charge.putCustomField("Charged From", "Skylight  App");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            chargeCard(account,skyLightPackage,customerPhoneNo,customer,selectedNumberOfDays,totalToday);
        } else {
            finish();
        }

    }
    private void chargeCard(Account account,SkyLightPackage skyLightPackage,String customerPhoneNo,Customer customer,int selectedNumberOfDays,double totalToday) {
        co.paystack.android.Transaction transaction = null;
        PaystackSdk.chargeCard(PayNowActivity.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(co.paystack.android.Transaction transaction) {
                dismissDialog();
                saveTransaction(account,skyLightPackage,customerPhoneNo,customer,selectedNumberOfDays);
                account.setAccountBalance(account.getAccountBalance()+totalToday);
                String paymentReference = transaction.getReference();

                mTextError.setText(" ");
                Toast.makeText(PayNowActivity.this, paymentReference, Toast.LENGTH_LONG).show();
                updateTextViews();
                String welcomeMessage="Your payment of NGN"+totalToday+""+"was received today";
                Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
                Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(customerPhoneNo),
                        new com.twilio.type.PhoneNumber("234"+customerPhoneNo),
                        welcomeMessage)
                        .create();
                setResult(Activity.RESULT_OK);
                setResult(Activity.RESULT_OK, new Intent());
                //finish();
                Toast.makeText(PayNowActivity.this, "Transaction Successful! payment reference: "
                        + paymentReference, Toast.LENGTH_LONG).show();
            }

            @Override
            public void beforeValidate(co.paystack.android.Transaction transaction) {
                Toast.makeText(PayNowActivity.this, transaction.getReference(), Toast.LENGTH_LONG).show();
                updateTextViews();

            }

            @Override
            public void onError(Throwable error, co.paystack.android.Transaction transaction) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                if (error instanceof ExpiredAccessCodeException) {
                    PayNowActivity.this.performCharge(false);
                    //PayNowActivity.this.chargeCard();
                    return;
                }

                dismissDialog();

                if (transaction.getReference() != null) {
                    Toast.makeText(PayNowActivity.this, transaction.getReference() + " concluded with error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    mTextError.setText(String.format("%s  concluded with error: %s %s", transaction.getReference(), error.getClass().getSimpleName(), error.getMessage()));
                    //new verifyOnServer().execute(transaction.getReference());
                } else {
                    Toast.makeText(PayNowActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    mTextError.setText(String.format("Error: %s %s", error.getClass().getSimpleName(), error.getMessage()));
                }
                updateTextViews();

            }

        });
    }

    public void revealFlutterwave(View view) {
    }

    public void revealPayStack(View view) {
    }

    private class fetchAccessCodeFromServer extends AsyncTask<String, Void, String> {
        private String error;

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                charge.setAccessCode(result);
                //chargeCard();
                startActivity(new Intent(PayNowActivity.this, LoginDirectorActivity.class));
            } else {
                PayNowActivity.this.resultText.setText(String.format("There was a problem getting a new access code form the backend: %s", error));
                dismissDialog();
            }
        }

        @Override
        protected String doInBackground(String... ac_url) {
            try {
                java.net.URL url = new URL(ac_url[0]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                url.openStream()));

                String inputLine;
                inputLine = in.readLine();
                in.close();
                return inputLine;
            } catch (Exception e) {
                error = e.getClass().getSimpleName() + ": " + e.getMessage();
            }
            return null;
        }
    }
    private void updateTextViews() {
        if (transaction.getTransactionID()>0) {
            resultText.setVisibility(View.VISIBLE);
            resultText.setText(String.format("Reference: %s", transaction.getTransactionID()));
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

    private void saveTransaction(Account account,SkyLightPackage skyLightPackage,String customerPhoneNo,Customer customer,int selectedNumberOfDays ) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        String officeBranch=null;
        long accountNo=0;
        com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type=null;
        if(totalBundle !=null) {

            dateOfReport = totalBundle.getString("Date");
            packageType = totalBundle.getString(PACKAGE_TYPE);
            packageID = totalBundle.getInt("Package Id");
            txtPackSavingsID.setText(MessageFormat.format("Package ID:{0}", packageID));
            AccountID = totalBundle.getInt("Account ID");
            account = totalBundle.getParcelable("Account");
            customer = totalBundle.getParcelable("Customer");
            skyLightPackage = totalBundle.getParcelable("Package");
            package_list_model = totalBundle.getParcelable("Package1");
            transaction = totalBundle.getParcelable("Transaction");
            customerDailyReport = totalBundle.getParcelable("Savings");
            reportID = totalBundle.getInt("Report ID");
            planCode = totalBundle.getString("Plan Code");
            accessCode = totalBundle.getString("Access Code");
            packageAmount = totalBundle.getDouble("Amount");
            selectedNumberOfDays = totalBundle.getInt("Number of Days");
            totalToday = totalBundle.getDouble("Total");
        }
        try {
            //selectedNumberOfDays = Integer.parseInt(spnNo_of_days.getSelectedItem().toString());
            if(skyLightPackage !=null){
                packageID=skyLightPackage.getPackageId();
                accountBalanceNow=skyLightPackage.getAccount().getAccountBalance();
                savingsCount=skyLightPackage.getTotalPSavingsCount(packageID);
                numberOfDaysRemaining=skyLightPackage.getRemainingDays();
                amountSoFar =skyLightPackage.getAmount_collected();
                packageTotal=skyLightPackage.getGrandTotal();
                packageAmount=skyLightPackage.getDailyAmount();
                amountRemaining=skyLightPackage.getAmountRemaining();

            }



        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        transaction.setTransactionId(transactionID);

        transaction.setAmount(totalToday);
        //long profileID =userProfile.getuID();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateAndTime = sdf.format(new Date());
        if(customer !=null){
            customerId8 = customer.getCusUID();
            customerName=customer.getCusSurname()+","+customer.getCusFirstName();
            officeBranch=customer.getCusOfficeBranch();

        }

        if(account !=null){
            accountNo=account.getSkyLightAcctNo();
        }

        Transaction transaction1 = new Transaction();
        newSavingsID = random.nextInt((int) (Math.random() * 901) + 100);
        refID = "SkyLightT/"+ random.nextInt((int) (Math.random() * 900000) + 100000);

        //long transactionId = Long.parseLong(transaction1.getTransactionID());
        //long accountId = transactionAccount.getId();
        co.paystack.android.Transaction transaction2 = new co.paystack.android.Transaction();
        //long paystackRefId = Long.parseLong(transaction2.getReference());
        String tittle = "New Online Payment Alert";
        String tittle1 = "Your Successful Online Payment Alert";
        String detail = "NGN" + totalToday + "was paid for" + customerName+""+"on"+currentDateAndTime;
        String managerName = userProfile.getProfileLastName() + userProfile.getProfileFirstName();
        String details = managerName + "paid" + "NGN" + totalToday + "for" + customerName;
        userProfile.addTransaction( transactionId, customerName, "", customerPhoneNo, totalToday, String.valueOf(AccountID),  PackageName,currentDateAndTime, "type");
        DBHelper applicationDb = new DBHelper (this);
        if (customer != null) {
            customer.addCusTransactions(totalToday);
        }
        if (customer != null) {
            customer.addCusTimeLine(tittle1,details);
        }
        parseResponse(transaction.getReceiptId());
        newAccountBalance = accountBalance+totalToday;
        userProfile.addTimeLine(tittle1,detail);
        if (customer != null) {
            customer.getCusAccount().setAccountBalance(newAccountBalance);
        }
        Skylightransaction= new com.skylightapp.Classes.Transaction(transactionID, AccountID, currentDateAndTime, 1001001, AccountID, "Skylight", customerName, totalToday, transaction_type, "",officeBranch, "", "", "");

        applicationDb.insertDailyReport(packageID,newSavingsID,profileID, customerId8,currentDateAndTime,packageAmount,selectedNumberOfDays,totalToday,amountSoFar+totalToday,amountRemaining,numberOfDaysRemaining,"paid");
        if (skyLightPackage != null) {
            skyLightPackage.addSavings(profileID,customerId8,0,packageAmount,selectedNumberOfDays,totalToday,numberOfDaysRemaining,amountRemaining,currentDateAndTime,"paid");
        }
        applicationDb.insertTimeLine(tittle,details,currentDateAndTime,mCurrentLocation);
        applicationDb.saveNewTransaction(profileID, customerId8,Skylightransaction, AccountID, "Skylight", customerName,transaction_type,totalToday, transactionID, officeBranch, currentDateAndTime);


        startActivity(new Intent(PayNowActivity.this, LoginDirectorActivity.class));
    }
    private void parseResponse (String transactionReference){
        String message = "Skylight Payment Successful - " + transactionReference;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        startActivity(new Intent(PayNowActivity.this, LoginDirectorActivity.class));

    }

    private boolean validateForm() {
        boolean valid = true;

        cardNumber = cardNumberField.getText().toString().trim();
        expiryMonth = Integer.parseInt(expiryMonthField.getText().toString().trim());
        expiryYear = Integer.parseInt(expiryYearField.getText().toString().trim());
        cvv = cvvField.getText().toString().trim();
        try {
            selectedNumberOfDays = Integer.parseInt(edtNoOfDays.getText().toString().trim());
            if(selectedNumberOfDays<1){
                edtNoOfDays.setError("Required.");
                valid = false;
            } else {
                edtNoOfDays.setError(null);
            }
        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
        String cardNumber = cardNumberField.getText().toString();
        if (TextUtils.isEmpty(cardNumber)) {
            cardNumberField.setError("Required.");
            valid = false;
        } else {
            cardNumberField.setError(null);
        }


        String expiryMonth = expiryMonthField.getText().toString();
        if (TextUtils.isEmpty(expiryMonth)) {
            expiryMonthField.setError("Required.");
            valid = false;
        } else {
            expiryMonthField.setError(null);
        }

        String expiryYear = expiryYearField.getText().toString();
        if (TextUtils.isEmpty(expiryYear)) {
            expiryYearField.setError("Required.");
            valid = false;
        } else {
            expiryYearField.setError(null);
        }

        String cvv = cvvField.getText().toString();
        if (TextUtils.isEmpty(cvv)) {
            cvvField.setError("Required.");
            valid = false;
        } else {
            cvvField.setError(null);
        }

        return valid;
    }




}