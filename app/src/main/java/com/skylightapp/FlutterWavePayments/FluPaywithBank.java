package com.skylightapp.FlutterWavePayments;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Interfaces.PaymentResultListener;
import com.skylightapp.R;
import com.skylightapp.Transactions.OurConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TYPE;
import static com.skylightapp.FlutterWavePayments.OTPFragment.EXTRA_OTP;
import static com.skylightapp.FlutterWavePayments.OTPFragment.IS_SAVED_CARD_CHARGE;
import static com.skylightapp.FlutterWavePayments.VerificationActivity.ACTIVITY_MOTIVE;
import static com.skylightapp.Transactions.OurConfig.NGN;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FluPaywithBank extends AppCompatActivity implements PaymentResultListener {
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json;
    private Customer customer;
    private Account account;

    private Profile userProfile;
    SkyLightPackage skyLightPackage;
    DBHelper dbHelper;
    SecureRandom random;
    long transactionID;
    String refID ;
    int index_no;
    String profileSurname;
    Transaction transaction;
    String profileEmail;

    long customerId8;
    String customerName,dateOfReport;
    String customerPhoneNo;
    String customerEmail,packageType,planCode,accessCode;
    double amountSoFar;
    long packageID,AccountID;
    double packageAmount;
    double amountRemaining,totalToday,accountBalanceNow;
    int savingsCount, selectedNumberOfDays,code;
    long reportID;
    TextView txtName,txtItemAmtInfo;
    private Spinner spnBank;
    private CustomerDailyReport customerDailyReport;
    private  EditText edtBankNameHolder,edtBankNumber;
    AppCompatButton payBank,btnCancel;
    private  String dob,day,month,year,passCode;
    String  selectedBank, bank_CODE,bankAcctNo,bankAcctName,codeSMS,verifiedName,uPhoneNumber;
    Intent intent;
    int OTP,length;
    private Bundle finalTotalBundle,totalBundle;
    ProgressDialog dialog;
    ProgressDialog progressDialog;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            intent = result.getData();
                            intent = getIntent();
                            OTP = Integer.parseInt(intent.getStringExtra(EXTRA_OTP));
                            sendPostRequest();

                        }

                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_flu_paywith_bank);
        random =new SecureRandom();
        intent = null;
        finalTotalBundle=new Bundle();
        spnBank =  findViewById(R.id.spn_bankAcct);
        txtName =  findViewById(R.id.cus_info);
        edtBankNameHolder =  findViewById(R.id.bankAcctName);
        edtBankNumber =  findViewById(R.id.bankAcctNo);
        payBank =  findViewById(R.id.pay_now33);
        btnCancel =  findViewById(R.id.home_Bank_paymen3t);
        txtItemAmtInfo = findViewById(R.id.item_info_Amt);
        dbHelper= new DBHelper(this);
        Twilio.init(OurConfig.TWILIO_ACCOUNT_SID, OurConfig.TWILIO_AUTH_TOKEN);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        customer= new Customer();
        String json = "";
        InputStream inputStream = null;
        String result = "";
        final Date[] readDate = {null};
        boolean requestResult=false;
        edtBankNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                length = s.length();
                if(length==10){
                    MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                    myAsyncTasks.execute();
                    payBank.setEnabled(true);
                    txtName.setVisibility(View.VISIBLE);


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

        if(customer !=null){
            dob=customer.getCusDob();
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy");


            try {
                readDate[0] = df.parse(dob);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(readDate[0].getTime());
            passCode= String.valueOf(cal.get(Calendar.DAY_OF_MONTH)+cal.get(Calendar.MONTH)+cal.get(Calendar.YEAR));

        }
        transactionID = random.nextInt((int) (Math.random() * 900) + 100);
        refID = "SkyLightT/"+ random.nextInt((int) (Math.random() * 900000) + 100000);
        code=((1 + random.nextInt(2)) * 10000 + random.nextInt(10000));
        codeSMS="Here is your Awajima payment verification code"+code;
        totalBundle = getIntent().getExtras().getBundle("paymentBundle") ;
        totalBundle = getIntent().getExtras().getBundle("finalTotalBundle") ;
        if(totalBundle !=null){
            totalBundle = this.getIntent().getExtras();
            packageID = Long.parseLong(totalBundle.getString("Package Id"));
            AccountID = Long.parseLong(totalBundle.getString("Account ID"));
            dateOfReport = totalBundle.getString("Date");
            packageType = totalBundle.getString(PACKAGE_TYPE);
            account = totalBundle.getParcelable("Account");
            customer = totalBundle.getParcelable("Customer");
            uPhoneNumber=customer.getCusPhoneNumber();
            skyLightPackage = totalBundle.getParcelable("Package");
            transaction = totalBundle.getParcelable("Transaction");
            customerDailyReport = totalBundle.getParcelable("Savings");
            reportID = Long.parseLong(totalBundle.getString("Report ID"));
            planCode = totalBundle.getString("Plan Code");
            accessCode = totalBundle.getString("Access Code");
            packageAmount = Double.parseDouble(totalBundle.getString("Amount"));
            selectedNumberOfDays = Integer.parseInt(totalBundle.getString("Number of Days"));
            totalToday = Double.parseDouble(totalBundle.getString("Total"));
            int packageDaysRem = totalBundle.getInt("Day Remaining");
            amountRemaining = Double.parseDouble(totalBundle.getString("Amount Remaining"));
            savingsCount = Integer.parseInt(totalBundle.getString("Package Count"));
            String grandTotalOfPackage = totalBundle.getString("Grand Total");
            amountSoFar = Double.parseDouble(totalBundle.getString("Amount So Far"));
            accountBalanceNow = Double.parseDouble(totalBundle.getString("Package Account Balance"));
            customerName = totalBundle.getString("Customer Name");
            customerId8 = Long.parseLong(totalBundle.getString("Customer ID"));
            customerEmail = totalBundle.getString("Customer Email");
            customerPhoneNo = totalBundle.getString("Customer Phone Number");
            txtName.setText(MessageFormat.format("{0}--{1}", packageID, reportID));
            txtItemAmtInfo.setText(MessageFormat.format("Total to Pay for Today:NGN{0}", totalToday));
        }
        spnBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd/MM/yyyy");


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

        Bundle userBundle = new Bundle();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        payBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle totalBundle = Objects.requireNonNull(getIntent().getExtras()).getBundle("paymentBundle") ;
                if(totalBundle !=null){
                    totalBundle = getIntent().getExtras();
                    packageID = Long.parseLong(totalBundle.getString("Package Id"));
                    AccountID = Long.parseLong(totalBundle.getString("Account ID"));
                    dateOfReport = totalBundle.getString("Date");
                    packageType = totalBundle.getString(PACKAGE_TYPE);
                    account = totalBundle.getParcelable("Account");
                    customer = totalBundle.getParcelable("Customer");
                    uPhoneNumber=customer.getCusPhoneNumber();
                    skyLightPackage = totalBundle.getParcelable("Package");
                    transaction = totalBundle.getParcelable("Transaction");
                    customerDailyReport = totalBundle.getParcelable("Savings");
                    reportID = Long.parseLong(totalBundle.getString("Report ID"));
                    planCode = totalBundle.getString("Plan Code");
                    accessCode = totalBundle.getString("Access Code");
                    packageAmount = Double.parseDouble(totalBundle.getString("Amount"));
                    selectedNumberOfDays = Integer.parseInt(totalBundle.getString("Number of Days"));
                    totalToday = Double.parseDouble(totalBundle.getString("Total"));
                    int packageDaysRem = totalBundle.getInt("Day Remaining");
                    amountRemaining = Double.parseDouble(totalBundle.getString("Amount Remaining"));
                    savingsCount = Integer.parseInt(totalBundle.getString("Package Count"));
                    String grandTotalOfPackage = totalBundle.getString("Grand Total");
                    amountSoFar = Double.parseDouble(totalBundle.getString("Amount So Far"));
                    accountBalanceNow = Double.parseDouble(totalBundle.getString("Package Account Balance"));
                    customerName = totalBundle.getString("Customer Name");
                    customerId8 = Long.parseLong(totalBundle.getString("Customer ID"));
                    customerEmail = totalBundle.getString("Customer Email");
                    customerPhoneNo = totalBundle.getString("Customer Phone Number");
                    txtName.setText(MessageFormat.format("{0}--{1}", packageID, reportID));
                    txtItemAmtInfo.setText(MessageFormat.format("Total to Pay for Today:NGN{0}", totalToday));
                }
                SharedPreferences.Editor editor = userPreferences.edit();
                editor.putInt(EXTRA_OTP, code);
                editor.apply();
                userBundle.putString(EXTRA_OTP, String.valueOf(code));
                userBundle.putString(ACTIVITY_MOTIVE, "otp");
                userBundle.putBoolean(IS_SAVED_CARD_CHARGE, true);

                Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
                Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(uPhoneNumber),
                        new com.twilio.type.PhoneNumber("234" + uPhoneNumber),
                        codeSMS)
                        .create();
                Intent intent3 = new Intent(FluPaywithBank.this, VerificationActivity.class);
                intent3.putExtras(userBundle);
                startActivity(intent3);
                mStartForResult.launch(new Intent(intent3));


            }
        });
    }
    private void sendPostRequest() {

        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                StringBuilder result = new StringBuilder();
                WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
                String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                json = "";
                JSONObject object = new JSONObject();
                try {
                    try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

                        HttpPost post = new HttpPost(("https://api.flutterwave.com/v3/charges?type=debit_ng_account"));

                        try {
                            object.put("account_bank", bank_CODE);
                            object.put("account_number", bankAcctNo);
                            object.put("amount", totalToday);
                            object.put("email", profileEmail);
                            object.put("tx_ref", refID);
                            object.put("currency", NGN);
                            object.put("fullname", profileSurname);
                            object.put("phone_number", uPhoneNumber);
                            object.put("client_ip", ipAddress);
                            object.put("device_fingerprint", "");
                            object.put("meta", "Savings ID:"+reportID);
                            object.put("passcode", passCode);
                            object.put("bvn", "");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        json = object.toString();

                        StringEntity input = new StringEntity(json);
                        input.setContentType("application/json");
                        post.setEntity(input);

                        post.setHeader("Authorization", OurConfig.SKYLIGHT_SECRET_KEY);
                        //httpPost.setHeader("Accept", "application/json");
                        post.setHeader("Content-type", "application/json");
                        HttpResponse httpResponse = client.execute(post);
                        BufferedReader rd = new BufferedReader(
                                new InputStreamReader(httpResponse.getEntity().getContent()));


                        String line;
                        while ((line = rd.readLine()) != null) {
                            result.append(line);
                        }
                        Log.d("do Resolve request", result.toString());
                        if (!String.valueOf(httpResponse.getStatusLine().getStatusCode()).startsWith("2") && !httpResponse.getEntity().getContentType().getValue().contains("json")) {
                            return null;
                        }
                        if (httpResponse.getStatusLine().getStatusCode() == 400) {
                            return "there is an error with the data";
                        } else {
                            return result.toString();
                        }

                    } catch (UnsupportedEncodingException ex) {
                        Log.d("error", Arrays.toString(ex.getStackTrace()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;


            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if(result.equals("working")){
                    Toast.makeText(getApplicationContext(), "HTTP POST is working...", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid POST req...", Toast.LENGTH_LONG).show();
                }


            }
        }

    }


    private void chargeBankAcct(){
        /*
        AccountResolvePayload ch= new AccountResolvePayload();
                        ch.setBankCode(bank_CODE)
                                .setAccountNumber(bankAcctNo)
                                .setAmount(String.valueOf(totalToday))
                                .setEmail(profileEmail)
                                .setTxRef(refID)
                                .setCountry("NG")
                                .setCurrency("NGN")
                                .setPasscode(passCode)
                                .setLastname(profileSurname)
                                .setPayment_type("account");


                        JSONObject result=ch.chargeAccount();

                        JSONObject poll=ch.chargeAccount(true);
                        System.out.println(result);
                        ch.setTransaction_reference(refID)
                                .setOtp(String.valueOf(code));
                        JSONObject val=ch.validateAccountCharge(true);
                        JSONObject validate=ch.validateAccountCharge();
                        String bankResult = validate.toString();
         */

    }

    @Override
    public void onPaymentComplete(boolean success) {

    }

    @Override
    public void onPaymentError(int i, String errorMessage) {

    }
    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result1 = new StringBuilder();
            edtBankNameHolder =  findViewById(R.id.bankAcctName);
            edtBankNumber =  findViewById(R.id.bankAcctNo);
            spnBank =  findViewById(R.id.spn_bankAcct);
            bankAcctNo=edtBankNumber.getText().toString().trim();
            json = "";
            JSONObject object = new JSONObject();

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

                httpPost.setHeader("Authorization", "IKIAF6C068791F465D2A2AA1A3FE88343B9951BAC9C3");
                //httpPost.setHeader("Accept", "application/json");
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
                    txtName.setText(result1.toString());
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
            progressDialog = new ProgressDialog(FluPaywithBank.this);
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
                    txtName.setVisibility(View.VISIBLE);
                    txtName.setText(account_name);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                FluPaywithBank.this.txtName.setText(String.format("There was a problem getting a name from the account details"));
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