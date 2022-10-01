package com.skylightapp.Customers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayInitializer;
import com.flutterwave.raveandroid.RavePayManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AppController;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.SkyLightPackModel;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.LoginDirAct;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
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

@SuppressWarnings("ResultOfMethodCallIgnored")
public class CustomerPayAct extends AppCompatActivity {
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json;
    private Customer customer;
    private Account account;

    private Profile userProfile;
    SkyLightPackage skyLightPackage,skyLightPackage1;
    DBHelper dbHelper;
    SecureRandom random;
    int transactionID;

    String refID ;
    String profileSurname,sMonth;
    String profileFirstName;
    String userPix;
    String profilePhone;
    Transaction transaction;
    String profileEmail;
    String userName;
    String profileRole;
    int customerID;
    String customerName,dateOfReport;
    String customerPhoneNo;
    String customerEmail,packageType,planCode,accessCode,inProgress;
    double accountBalance;
    double amountSoFar;
    double packageTotal;
    int packageID,AccountID,profileID;
    double packageAmount,newAccountBalance;
    double amountRemaining,totalToday,accountBalanceNow;
    int savingsCount, numberOfDaysRemaining,selectedNumberOfDays;
    int newSavingsID ,reportID;
    TextView txtName,txtItemAmtInfo,total_info;
    private Spinner spnBank;
    private CustomerDailyReport customerDailyReport;
    private EditText edtBankNameHolder,edtBankNumber;
    AppCompatButton payBank,btnCancel;
    private ArrayList<SkyLightPackage> packageArrayList;
    private ArrayAdapter<SkyLightPackage> packagerAdapter;
    private AppCompatSpinner spn_customer_packages,number_of_days_packages;
    int packageIndex;
    private Button btnPayWithAllFlutterWave,btnPayWithCardP;
    AppCompatButton btnSubmitCardP,btnCancelCardP;
    LinearLayoutCompat layoutCardP;
    EditText edtPCVV,edtPCardYear,edtPCardMonth,edtPCardNo;
    private  boolean isVisible;
    int noOfDays,daysSoFar,totalDays;
    com.skylightapp.Classes.Transaction Skylightransaction;
    int accountID;
    String cardNumber,cvv,sYear, selectedBank, bank_CODE,bankAcctNo,bankAcctName;
    private  Charge charge;
    ProgressDialog dialog;
    Card card1;
    public static final int PICTURE_REQUEST_CODE = 505;
    Location mCurrentLocation;
    private  String dob,day,month,year,passCode;
    com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type;

    AppController appController;
    private static boolean isPersistenceEnabled = false;
    private Uri mImageUri;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    private ProgressDialog progressDialog;
    private String picturePath;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String REQUIRED = "Required";
    private static final int RESULT_CAMERA_CODE=22;
    Bundle paymentBundle;
    LinearLayout package_Layout_l3;

    ContentLoadingProgressBar progressBar;
    boolean isStaging=false;
    private static final String PREF_NAME = "skylight";
    SkyLightPackModel skylightPackageModel,skylightPackageModel1;
    private String noOfDaysString;
    RavePayInitializer ravePayInitializer;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_pay);
        dbHelper= new DBHelper(this);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        FirebaseApp.initializeApp(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        customer= new Customer();
        random =new SecureRandom();
        isVisible= false;
        charge=new Charge();
        skylightPackageModel= new SkyLightPackModel();
        paymentBundle= new Bundle();
        skyLightPackage= new SkyLightPackage();
        final Date[] readDate = {null};
        transaction_type=null;
        progressBar = findViewById(R.id.progressBar);

        txtName =  findViewById(R.id.cus_infoPay);
        txtItemAmtInfo = findViewById(R.id._info_Amt);
        spn_customer_packages = findViewById(R.id.spn_packages);
        number_of_days_packages = findViewById(R.id.number_of_days_p);
        total_info = findViewById(R.id.total_infoA);
        btnPayWithAllFlutterWave =  findViewById(R.id.all_Flutter_Wave);
        btnPayWithCardP =  findViewById(R.id.pay_CardP);
        package_Layout_l3 =  findViewById(R.id.package_Layout_l3);
        layoutCardP =  findViewById(R.id.Layoutcard_Paystack);
        edtPCVV = findViewById(R.id.Pedit_cvv3);
        edtPCardYear = findViewById(R.id.Pedit_card_year33);
        edtPCardNo = findViewById(R.id.Pedit_card_number33);
        edtPCardMonth = findViewById(R.id.Pedit_expiry_month33);
        btnSubmitCardP =  findViewById(R.id.submitPayWithCardP);
        btnCancelCardP =  findViewById(R.id.CancelPayWithCardP);


        btnPayWithAllFlutterWave.setOnClickListener(this::payWithFlutter);
        btnPayWithCardP.setOnClickListener(this::payWithPayStackCard);
        btnSubmitCardP.setOnClickListener(this::SubmitPayWithCardP);
        btnCancelCardP.setOnClickListener(this::CancelPayWithCardP);


        paymentBundle=getIntent().getExtras();


        if(paymentBundle !=null){
            package_Layout_l3.setVisibility(View.GONE);
            skylightPackageModel=paymentBundle.getParcelable("SkyLightPackage_List_Model");
            skyLightPackage=paymentBundle.getParcelable("SkyLightPackage");
            dateOfReport = paymentBundle.getString("Date");
            packageType = paymentBundle.getString(PACKAGE_TYPE);
            packageID = paymentBundle.getInt("Package Id");
            AccountID = paymentBundle.getInt("Account ID");
            account = paymentBundle.getParcelable("Account");
            customer = paymentBundle.getParcelable("Customer");
            skyLightPackage1 = paymentBundle.getParcelable("Package");
            skylightPackageModel1 = paymentBundle.getParcelable("Package1");
            transaction = paymentBundle.getParcelable("Transaction");
            customerDailyReport = paymentBundle.getParcelable("Savings");
            reportID = paymentBundle.getInt("Report ID");
            planCode = paymentBundle.getString("Plan Code");
            accessCode = paymentBundle.getString("Access Code");
            packageAmount = paymentBundle.getDouble("Amount");
            selectedNumberOfDays = paymentBundle.getInt("Number of Days");
            totalToday = paymentBundle.getDouble("Total");

            if(skyLightPackage ==null){
                skyLightPackage=skyLightPackage1;

            }
            if(skylightPackageModel ==null){
                skylightPackageModel=skylightPackageModel1;
            }
            if(customer !=null){
                customerID=customer.getCusUID();
            }


        }else {
            package_Layout_l3.setVisibility(View.VISIBLE);

            try {
                if (userProfile != null) {
                    customer=userProfile.getProfileCus();
                    if(customer !=null){
                        customerID=customer.getCusUID();
                    }
                    profileID = userProfile.getPID();
                    profileSurname = userProfile.getProfileLastName();
                    profileFirstName = userProfile.getProfileFirstName();
                    profilePhone = userProfile.getProfilePhoneNumber();
                    profileEmail=userProfile.getProfileEmail();
                    packageArrayList = dbHelper.getProfileIncompletePack(customerID, inProgress);
                    packagerAdapter = new ArrayAdapter<SkyLightPackage>(this, android.R.layout.simple_spinner_item, packageArrayList);
                    packagerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_customer_packages.setAdapter(packagerAdapter);
                    //spn_customer_packages.setSelection(0);
                    spn_customer_packages.setSelection(packagerAdapter.getPosition(skyLightPackage));
                    packageIndex = spn_customer_packages.getSelectedItemPosition();
                    try {

                        skyLightPackage = (SkyLightPackage) spn_customer_packages.getItemAtPosition(packageIndex);
                        packageID=skyLightPackage.getPackID();
                        account=skyLightPackage.getPackageAccount();
                        if(account !=null){
                            accountBalance=account.getAccountBalance();
                            accountID=account.getAwajimaAcctNo();

                        }

                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Oops!");
                    }


                }

            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }

        }

        try {
            if(skyLightPackage !=null){
                packageID=skyLightPackage.getPackID();
                account=skyLightPackage.getPackageAccount();
                //packageID=skyLightPackage.getPackageId();
                savingsCount=skyLightPackage.getPSavingsCount(packageID);
                numberOfDaysRemaining=skyLightPackage.getPackageDaysRem();
                amountSoFar =skyLightPackage.getPackageAmount_collected();
                packageTotal=skyLightPackage.getPackageTotalAmount();
                packageAmount=skyLightPackage.getPackageDailyAmount();
                amountRemaining=skyLightPackage.getPackageAmtRem();
                if(account !=null){
                    accountBalanceNow=account.getAccountBalance();
                    accountBalance=account.getAccountBalance();
                    accountID=account.getAwajimaAcctNo();

                }
            }



        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }


        transactionID = random.nextInt((int) (Math.random() * 1033) + 109872);
        refID = "SkyLightT/"+ random.nextInt((int) (Math.random() * 100) + 1110);
        number_of_days_packages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                noOfDaysString = number_of_days_packages.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        try {
            noOfDays = Integer.parseInt(noOfDaysString);

        } catch (NumberFormatException e) {
            //no


        }




        btnPayWithCardP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutCardP.setVisibility(View.VISIBLE);

            }
        });
        btnPayWithAllFlutterWave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutCardP.setVisibility(View.GONE);
                processFlutterPayment(profileEmail,amountSoFar,packageTotal,numberOfDaysRemaining,amountRemaining,accountBalance,profileID,profileSurname,profileFirstName,profilePhone,skyLightPackage,totalToday,selectedNumberOfDays,packageAmount,reportID,customerDailyReport,transaction,customer,account,AccountID,packageID,packageType);

            }
        });


    }

    private void processFlutterPayment(String profileEmail, double amountSoFar, double packageTotal, int numberOfDaysRemaining, double amountRemaining, double accountBalance, long profileID, String profileSurname, String profileFirstName, String profilePhone, SkyLightPackage skyLightPackage, double totalToday, int selectedNumberOfDays, double packageAmount, long reportID, CustomerDailyReport customerDailyReport, Transaction transaction, Customer customer, Account account, long accountID, long packageID, String packageType) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date newDate = calendar.getTime();
        final Date[] readDate = {null};
        String theDate = sdf.format(newDate);


        try {
            if (ravePayInitializer.isStaging() && ravePayInitializer.getShowStagingLabel()) {
                findViewById(R.id.stagingModeBannerLay).setVisibility(View.VISIBLE);
            }
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
        } catch (RuntimeException e) {
            Toast.makeText(CustomerPayAct.this, "An error occurred while charging card", Toast.LENGTH_LONG).show();


        }





    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String paymentMessage="";


        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_LONG).show();
                Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
                sendTransactionMessage();
                saveTransaction( );
                Intent itemPurchaseIntent = new Intent(CustomerPayAct.this, NewCustomerDrawer.class);
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
                customerID=customer.getCusUID();
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
        Intent itemPurchaseIntent = new Intent(CustomerPayAct.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }

    public void cancelBankPay(View view) {
        finishAndRemoveTask();

    }

    public void SubmitPayWithCardP(View view) {
        String pSeckey = SKYLIGHT_SECRET_KEY;
        customer=new Customer();

        //Objects.requireNonNull(getSupportActionBar()).setTitle("Pay Now Activity");
        PaystackSdk.initialize(getApplicationContext());
        PaystackSdk.setPublicKey(pSeckey);
        spn_customer_packages = findViewById(R.id.spn_packages);
        number_of_days_packages = findViewById(R.id.number_of_days_p);
        number_of_days_packages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                noOfDays = Integer.parseInt(number_of_days_packages.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if(customer !=null){
            customerID=customer.getCusUID();
        }
        try {
            if (userProfile != null) {
                profileID = userProfile.getPID();
                profileSurname = userProfile.getProfileLastName();
                profileFirstName = userProfile.getProfileFirstName();
                profilePhone = userProfile.getProfilePhoneNumber();
                customerEmail=userProfile.getProfileEmail();
                customerPhoneNo=userProfile.getProfilePhoneNumber();
                packageArrayList = dbHelper.getProfileIncompletePack(customerID, inProgress);
                packagerAdapter = new ArrayAdapter<SkyLightPackage>(this, android.R.layout.simple_spinner_item, packageArrayList);
                packagerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_customer_packages.setAdapter(packagerAdapter);
                spn_customer_packages.setSelection(0);
                packageIndex = spn_customer_packages.getSelectedItemPosition();
                try {

                    skyLightPackage = (SkyLightPackage) spn_customer_packages.getItemAtPosition(packageIndex);
                    packageID=skyLightPackage.getPackID();
                    account=skyLightPackage.getPackageAccount();
                    packageAmount=skyLightPackage.getPackageDailyAmount();
                    accountBalance=account.getAccountBalance();
                    accountID=account.getAwajimaAcctNo();
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oops!");
                }
                totalToday=packageAmount*noOfDays;


            }

        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }
        try {
            performCharge(true);
        } catch (Exception e) {
            Toast.makeText(CustomerPayAct.this, "An error occurred while charging card", Toast.LENGTH_LONG).show();


        }


    }
    private Card loadCardFromForm() {
        edtPCVV = findViewById(R.id.Pedit_cvv3);
        edtPCardYear = findViewById(R.id.Pedit_card_year33);
        edtPCardNo = findViewById(R.id.Pedit_card_number33);
        edtPCardMonth = findViewById(R.id.Pedit_expiry_month33);

        try {
            cardNumber = edtPCardNo.getText().toString().trim();
        } catch (Exception ignored) {
        }
        try {
            cvv = edtPCVV.getText().toString().trim();
        } catch (Exception ignored) {
        }


        card1 = (Card) new Card.Builder(cardNumber, 0, 0, "").build();
        card1.setCvc(cvv);

        sMonth = edtPCardMonth.getText().toString().trim();
        //expiryMonth = Integer.parseInt(expiryMonthField.getText().toString().trim());
        int month = 0;
        try {
            month = Integer.parseInt(sMonth);
        } catch (Exception ignored) {
        }

        card1.setExpiryMonth(month);

        //expiryYear = Integer.parseInt(expiryYearField.getText().toString().trim());


        sYear = edtPCardYear.getText().toString().trim();
        int year = 0;
        try {
            year = Integer.parseInt(sYear);
        } catch (Exception ignored) {
        }
        card1.setExpiryYear(year);

        return card1;
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
        String currentDateAndTime = sdf.format(new Date());
        charge = new Charge();
        charge.setCard(loadCardFromForm());

        dialog = new ProgressDialog(CustomerPayAct.this);
        dialog.setMessage("Performing Skylight transaction... please wait");
        dialog.show();
        if (local) {

            charge.setEmail(customerEmail);
            charge.setAmount(Integer.parseInt(valueOf(totalToday)));
            charge.setCard(card1);
            charge.setReference(refID);
            try {
                charge.putCustomField("Charged From", "Skylight App");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            chargeCard();
        } else {
            // Perform transaction/initialize on our server to get an access code
            // documentation: https://developers.paystack.co/reference#initialize-a-transaction
            //new fetchAccessCodeFromServer().execute(backend_url + "/new-access-code");
        }

    }
    private void chargeCard() {
        co.paystack.android.Transaction transaction = null;
        PaystackSdk.chargeCard(CustomerPayAct.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(co.paystack.android.Transaction transaction) {
                dismissDialog();
                saveTransaction();
                account.setAccountBalance(account.getAccountBalance()+totalToday);
                //CustomerPayAct.this.transaction = transaction;
                String paymentReference = transaction.getReference();

                Toast.makeText(CustomerPayAct.this, paymentReference, Toast.LENGTH_LONG).show();
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
                /*int customerAccIndex = spnAllCustomers.getSelectedItemPosition();
                int customerIndex = spnMyCustomers.getSelectedItemPosition();

                Account transactionAccount = (Account) spnAllCustomers.getItemAtPosition(customerAccIndex);
                Customer transactionCustomer = (Customer) spnMyCustomers.getItemAtPosition(customerIndex);
                long customerID = transactionCustomer.getuID();
                long profileID=userProfile.getuID();
                double accountBalance = transactionAccount.getAccountBalance();
                final String c_email = transactionCustomer.getEmail();
                final String c_phoneNumber = transactionCustomer.getPhoneNumber();
                final String f_name = transactionCustomer.getFirstName();
                final String s_name = transactionCustomer.getSurname();
                String gender = transactionCustomer.getGender();
                String office = transactionCustomer.getOffice();*/
                //String accountNumber = edtAccountNumber.getText().toString();
                Toast.makeText(CustomerPayAct.this, "Transaction Successful! payment reference: "
                        + paymentReference, Toast.LENGTH_LONG).show();

                //customer.addTransactions(Double.parseDouble(String.valueOf(totalToday)));


                //applicationDb.insertCustomer11(packageID, customerId8, "", customerName, customerPhoneNo, customerEmail, "", "", "", "", "", currentDateAndTime, "", "", Uri.parse(""), 0);

                //startActivity(new Intent(PayNowActivity.this, LoginDirectorActivity.class));

                JSONObject object = new JSONObject();
                try {
                    // to put name
                    object.put("name", "Skylight payment");

                    // put description
                    //object.put("description", "Test payment");

                    // to set theme color
                    //object.put("theme.color", "");

                    // put the currency
                    object.put("currency", "NGN");

                    // put amount
                    //object.put("amount", amount);

                    // put mobile number
                    //object.put("prefill.contact", "9284064503");

                    // put email
                    //object.put("prefill.email", "chaitanyamunje@gmail.com");

                    // open razorpay to checkout activity
                    //checkout.open(PayNowActivity.this, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeValidate(co.paystack.android.Transaction transaction) {
                //CustomerPayAct.this.transaction = transaction;
                Toast.makeText(CustomerPayAct.this, transaction.getReference(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(Throwable error, co.paystack.android.Transaction transaction) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                //CustomerPayAct.this.transaction = transaction;
                if (error instanceof ExpiredAccessCodeException) {
                    CustomerPayAct.this.performCharge(false);
                    CustomerPayAct.this.chargeCard();
                    return;
                }

                dismissDialog();

                if (transaction.getReference() != null) {
                    Toast.makeText(CustomerPayAct.this, transaction.getReference() + " concluded with error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    //new verifyOnServer().execute(transaction.getReference());
                } else {
                    Toast.makeText(CustomerPayAct.this, error.getMessage(), Toast.LENGTH_LONG).show();

                }

            }

        });
    }
    private void saveTransaction( ) {
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        Transaction transaction = new Transaction();

        int tId = transaction.getTransactionID();
        Skylightransaction= new com.skylightapp.Classes.Transaction();

        transaction.setTransactionId(tId);
        String officeBranch=null;
        FirebaseApp.initializeApp(this);


        transaction.setRecordAmount(totalToday);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        if (userProfile != null) {
            officeBranch=userProfile.getProfileOffice();
        }

        Customer customer = new Customer();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateAndTime = sdf.format(new Date());
        long customerId = customer.getCusUID();
        int transactionId=0;
        Transaction transaction1 = new Transaction();
        if(transaction1 !=null){
            transactionId = transaction1.getTransactionID();

        }

        //long accountId = transactionAccount.getId();
        co.paystack.android.Transaction transaction2 = new co.paystack.android.Transaction();
        long paystackRefId = Long.parseLong(transaction2.getReference());
        String tittle = "New Online Payment Alert";
        String tittle1 = "Your Successful Online Payment Alert";
        String detail = "NGN" + totalToday + "was paid for" + customerName+""+"on"+currentDateAndTime;
        String managerName = userProfile.getProfileLastName() + userProfile.getProfileFirstName();
        String details = managerName + "paid" + "NGN" + totalToday + "for" + customerName;
        userProfile.addPTransaction( transactionId, customerName, "", customerPhoneNo, totalToday, String.valueOf(AccountID),  "",currentDateAndTime, "type");
        DBHelper applicationDb = new DBHelper (this);
        Skylightransaction= new com.skylightapp.Classes.Transaction(transactionID, AccountID, currentDateAndTime, 1001001, AccountID, "Skylight", customerName, totalToday, transaction_type, "Online",officeBranch, "", "", "");

        customer.addCusTransactions(totalToday);
        customer.addCusTimeLine(tittle1,details);
        parseResponse(transaction.getTranxReceiptId());
        newAccountBalance = accountBalance+totalToday;
        customer.getCusAccount().setAccountBalance(newAccountBalance);
        Skylightransaction.setRecordAmount(totalToday);
        Skylightransaction.setTranxAcctId(AccountID);
        Skylightransaction.setTranxCusID(customerID);
        Skylightransaction.setTranxCurrency(Currency.getInstance("NGN"));
        Skylightransaction.setTranxMethodOfPay("Online");
        Skylightransaction.setTranxDate(currentDateAndTime);
        TimeLineClassDAO timeLineDao = new TimeLineClassDAO(this);
        TranXDAO tranXDAO = new TranXDAO(this);
        Skylightransaction.setTranxPayer(customerName);


        timeLineDao.insertTimeLine(tittle,details,currentDateAndTime,mCurrentLocation);
        tranXDAO.saveNewTransaction(profileID, customerID,Skylightransaction, AccountID, "Skylight", customerName,transaction_type,totalToday, reportID, officeBranch, currentDateAndTime);

    }
    private void parseResponse (String transactionReference){
        String message = "Skylight Payment Successful - " + transactionReference;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        startActivity(new Intent(CustomerPayAct.this, LoginDirAct.class));

    }


    private void dismissDialog() {
        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void payWithPayStackCard(View view) {
    }

    public void payWithFlutter(View view) {
    }


    private class fetchAccessCodeFromServer extends AsyncTask<String, Void, String> {
        private String error;

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                charge.setAccessCode(result);
                chargeCard();
            } else {
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

    public void CancelPayWithCardP(View view) {
        finishAndRemoveTask();

    }
}