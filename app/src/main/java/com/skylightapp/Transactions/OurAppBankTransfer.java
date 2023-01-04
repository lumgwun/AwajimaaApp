package com.skylightapp.Transactions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import co.paystack.android.Transaction;
import co.paystack.android.model.Charge;

import static com.skylightapp.Transactions.OurConfig.INTERSWITCH_LS_SECRET_KEY;
import static com.skylightapp.Transactions.OurConfig.PAYSTACK_CREATE_TRANSFER_RECEPIENT;
import static com.skylightapp.Transactions.OurConfig.PAYSTACK_EXECUTE_TRANSFER;
import static com.skylightapp.Transactions.OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY;

public class OurAppBankTransfer extends AppCompatActivity {
    String response;
    Random random;
    AppCompatSpinner spnNigBank;
    int depositID;
    private ArrayAdapter<Account> accountAdapter;
    Profile userProfile;
    Customer customer;
    Context context;
    long customerId;
    String accountNo;
    int customerID;
    AppCompatButton depositBtn;
    AppCompatTextView verifyName,mTextError;
    Account account;
    int profileID;
    AppCompatEditText amountToDeposit, accountNumberEdt, accountName;

    ProgressDialog dialog;
    private Charge charge;
    private Transaction transaction;
     double transactionAmount = 0;
    private  double depositAmount,newAccountBalance;
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json,txRef,accountbank,selectedBank;
    AppCompatButton submitBankTransfer;
    DBHelper dbHelper;
    private StandingOrderAcct standingOrderAcct;
    Message message;
    String transferDate;
    private Loan loan;
    private  String bankAcctNumber;
    com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type;
    private  Bundle loanBundle;
    private  ProgressDialog progressDialog;
    private static final String PREF_NAME = "skylight";
    String transferType,bankTransferMessage, bank, bankAcctName,profileSurname, profileUserName,machine,profilePhone,profileEmailAddress,profileFirstName,textMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_our_app_bank_transfer);
        Twilio.init(OurConfig.TWILIO_ACCOUNT_SID, OurConfig.TWILIO_AUTH_TOKEN);
        spnNigBank = findViewById(R.id.selected_bank_account);
        submitBankTransfer = findViewById(R.id._deposit_account_submit);
        amountToDeposit = findViewById(R.id.bank_deposit_amount);
        accountNumberEdt = findViewById(R.id._deposit_account_no);
        accountName = findViewById(R.id._deposit_account_name);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        random= new Random();
        standingOrderAcct= new StandingOrderAcct();
        dbHelper=new DBHelper(this);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        customer= new Customer();
        account= new Account();
        loanBundle=getIntent().getExtras();
        if(loanBundle !=null){
            account=loanBundle.getParcelable("Account");
            customer=loanBundle.getParcelable("Customer");
            loan=loanBundle.getParcelable("Loan");
            userProfile=loanBundle.getParcelable("Profile");
            transferType=loanBundle.getString("Type");
            standingOrderAcct=loanBundle.getParcelable("StandingOrderAcct");



        }

        try {
            depositID = random.nextInt((int) (Math.random() * 109) + 11+77);
            txRef="OurCoopAppPayment"+random;
        } catch (NullPointerException e) {
            System.out.println("Oops!");
        }

        transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.DEPOSIT;

        if(userProfile !=null) {
            try {
                profileID = userProfile.getPID();
                customer = userProfile.getProfileCus();
                if(customer !=null){
                    customerID=customer.getCusUID();
                }
                account = userProfile.getProfileAccount();
                if(account !=null){
                    accountNo=account.getBankAcct_No();

                }
            } catch (NumberFormatException e) {
                System.out.println("Oops!");
            }
        }




        profileSurname = userPreferences.getString("PROFILE_SURNAME", "");
        profileFirstName = userPreferences.getString("PROFILE_FIRSTNAME", "");
        profilePhone = userPreferences.getString("PROFILE_PHONE", "");
        profileEmailAddress = userPreferences.getString("PROFILE_EMAIL", "");
        profileUserName = userPreferences.getString("PROFILE_USERNAME", "");
        machine=userPreferences.getString("PROFILE_ROLE","");
        submitBankTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBankDeposit();

            }
        });
        spnNigBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedBank = String.valueOf(parent.getSelectedItem());
                if(selectedBank.equalsIgnoreCase("ACCESS BANK PLC")){
                    accountbank="044";
                }
                if(selectedBank.equalsIgnoreCase("First Bank of Nigeria")){
                    accountbank="011";
                }
                if(selectedBank.equalsIgnoreCase("UBA PLC")){
                    accountbank="033";
                }
                if(selectedBank.equalsIgnoreCase("Heritage")){
                    accountbank="030";
                }
                if(selectedBank.equalsIgnoreCase("Keystone Bank")){
                    accountbank="082";
                }
                if(selectedBank.equalsIgnoreCase("Skye Bank")){
                    accountbank="076";
                }


                if(selectedBank.equalsIgnoreCase("Sterling Bank")){
                    accountbank="232";
                }

                if(selectedBank.equalsIgnoreCase("Union Bank")){
                    accountbank="032";
                }
                if(selectedBank.equalsIgnoreCase("GTBank Plc")){
                    accountbank="058";
                }
                if(selectedBank.equalsIgnoreCase("FCMB")){
                    accountbank="214";
                }
                if(selectedBank.equalsIgnoreCase("TrustBond")){
                    accountbank="523";
                }
                if(selectedBank.equalsIgnoreCase("SunTrust Bank")){
                    accountbank="100";
                }
                if(selectedBank.equalsIgnoreCase("Diamond Bank")){
                    accountbank="063";
                }
                if(selectedBank.equalsIgnoreCase("GT MOBILE MONEY")){
                    accountbank="315";
                }
                if(selectedBank.equalsIgnoreCase("FET")){
                    accountbank="314";
                }
                if(selectedBank.equalsIgnoreCase("Mkudi")){
                    accountbank="313";
                }
                if(selectedBank.equalsIgnoreCase("FSDH")){
                    accountbank="601";
                }
                if(selectedBank.equalsIgnoreCase("Coronation Merchant Bank")){
                    accountbank="559";
                }
                if(selectedBank.equalsIgnoreCase("Enterprise Bank")){
                    accountbank="084";
                }
                if(selectedBank.equalsIgnoreCase("Wema Bank")){
                    accountbank="035";
                }
                if(selectedBank.equalsIgnoreCase("Parralex")){
                    accountbank="526";
                }
                if(selectedBank.equalsIgnoreCase("Pagatech")){
                    accountbank="327";
                }
                if(selectedBank.equalsIgnoreCase("Stanbic IBTC Bank")){
                    accountbank="221";
                }
                if(selectedBank.equalsIgnoreCase("Fidelity Mobile")){
                    accountbank="318";
                }
                if(selectedBank.equalsIgnoreCase("EcoMobile")){
                    accountbank="307";
                }
                if(selectedBank.equalsIgnoreCase("Ecobank Plc")){
                    accountbank="050";
                }
                if(selectedBank.equalsIgnoreCase("JAIZ Bank")){
                    accountbank="301";
                }
                if(selectedBank.equalsIgnoreCase("Access Money")){
                    accountbank="323";
                }
                if(selectedBank.equalsIgnoreCase("Unity Bank")){
                    accountbank="215";
                }
                if(selectedBank.equalsIgnoreCase("CitiBank")){
                    accountbank="023";
                }
                if(selectedBank.equalsIgnoreCase("Fidelity Bank")){
                    accountbank="070";
                }
                if(selectedBank.equalsIgnoreCase("eTranzact")){
                    accountbank="306";
                }
                if(selectedBank.equalsIgnoreCase("Standard Chartered Bank")){
                    accountbank="068";
                }
                if(selectedBank.equalsIgnoreCase("Zenith Bank")){
                    accountbank="057";
                }
                if(selectedBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")){
                    accountbank="402";
                }
                if(selectedBank.equalsIgnoreCase("Sterling Mobile")){
                    accountbank="326";
                }
                if(selectedBank.equalsIgnoreCase("FBNMobile")){
                    accountbank="309";
                }
                if(selectedBank.equalsIgnoreCase("Stanbic Mobile Money")){
                    accountbank="304";
                }
                if(selectedBank.equalsIgnoreCase("TCF MFB")){
                    accountbank="90115";
                }
                if(selectedBank.equalsIgnoreCase("ASO Savings and Loans")){
                    accountbank="401";
                }
                if(selectedBank.equalsIgnoreCase("Cellulant")){
                    accountbank="317";
                }
                if(selectedBank.equalsIgnoreCase("NIP Virtual Bank")){
                    accountbank="999";
                }
                if(selectedBank.equalsIgnoreCase("Paycom")){
                    accountbank="305";
                }
                if(selectedBank.equalsIgnoreCase("NPF MicroFinance Bank")){
                    accountbank="552";
                }
                if(selectedBank.equalsIgnoreCase("Imperial Homes Mortgage Bank")){
                    accountbank="415";
                }
                if(selectedBank.equalsIgnoreCase("Covenant Microfinance Bank")){
                    accountbank="551";
                }
                if(selectedBank.equalsIgnoreCase("SafeTrust Mortgage Bank")){
                    accountbank="403";
                }
                if(selectedBank.equalsIgnoreCase("ChamsMobile")){
                    accountbank="303";
                }
                if(selectedBank.equalsIgnoreCase("ZenithMobile")){
                    accountbank="322";
                }
                if(selectedBank.equalsIgnoreCase("PayAttitude Online")){
                    accountbank="329";
                }
                if(selectedBank.equalsIgnoreCase("Fortis Microfinance Bank")){
                    accountbank="501";
                }
                if(selectedBank.equalsIgnoreCase("VTNetworks")){
                    accountbank="320";
                }
                if(selectedBank.equalsIgnoreCase("TeasyMobile")){
                    accountbank="319";
                }
                if(selectedBank.equalsIgnoreCase("MoneyBox")){
                    accountbank="325";
                }
                if(selectedBank.equalsIgnoreCase("Hedonmark")){
                    accountbank="324";
                }
                if(selectedBank.equalsIgnoreCase("Eartholeum")){
                    accountbank="302";
                }
                if(selectedBank.equalsIgnoreCase("ReadyCash (Parkway)")){
                    accountbank="311";
                }
                if(selectedBank.equalsIgnoreCase("Omoluabi Mortgage Bank")){
                    accountbank="990";
                }
                if(selectedBank.equalsIgnoreCase("TagPay")){
                    accountbank="328";
                }
                if(selectedBank.equalsIgnoreCase("FortisMobile")){
                    accountbank="308";
                }
                if(selectedBank.equalsIgnoreCase("Page MFBank")){
                    accountbank="560";
                }
                if(selectedBank.equalsIgnoreCase("Pagatech")){
                    accountbank="327";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //selectedBank = String.valueOf(spnNigBank.getSelectedItem());

    }
    private void startBankDeposit(){
        try {
            depositAmount = Double.parseDouble(Objects.requireNonNull(amountToDeposit.getText()).toString());
        } catch (Exception e) {
            Toast.makeText(this, "Please enter a valid Amount", Toast.LENGTH_SHORT).show();
            amountToDeposit.requestFocus();
        }
        try {
            bankAcctName = Objects.requireNonNull(accountName.getText()).toString();
        } catch (Exception e) {
            Toast.makeText(this, "Please enter a valid Account Name", Toast.LENGTH_SHORT).show();
            accountName.requestFocus();
        }
        try {
            bankAcctNumber = accountNumberEdt.getText().toString();

        } catch (Exception e) {
            Toast.makeText(this, "Please enter a valid First Name", Toast.LENGTH_SHORT).show();
            accountNumberEdt.requestFocus();
        }

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date newDate = calendar.getTime();
        transferDate = sdf.format(newDate);
        bankTransferMessage="You have requested for the transfer of NGN"+ depositAmount +"from your Awajima Acct. @"+transferDate;

        newAccountBalance=depositAmount+account.getAccountBalance();
        createTransferRecepient(bankAcctNumber,bankAcctName,accountbank,depositAmount);


    }

    private void createTransferRecepient(String bankAcctNumber, String bankAcctName, String accountbank, double transactionAmount) {

        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(OurAppBankTransfer.this);
                progressDialog.setMessage("Processing Loan");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                JSONObject jsonObject = new JSONObject();
                InputStream inputStream = null;
                String result = "";
                String json1 = "";
                HttpURLConnection urlConnection = null;

                try {
                    jsonObject.put("type", "nuban");
                    jsonObject.put("name", bankAcctName);
                    jsonObject.put("account_number", bankAcctNumber);
                    jsonObject.put("bank_code", accountbank);
                    jsonObject.put("currency", "NGN");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(PAYSTACK_CREATE_TRANSFER_RECEPIENT);
                    json1 = jsonObject.toString();
                    StringEntity stringEntity1 = new StringEntity(json1);
                    httpPost.setEntity(stringEntity1);
                    httpPost.setHeader("Authorization", INTERSWITCH_LS_SECRET_KEY);
                    httpPost.setHeader("Content-type", "application/json");
                    //httpPost.setHeader("Accept", "application/json");
                    HttpResponse httpResponse1 = httpclient.execute(httpPost);

                    inputStream = httpResponse1.getEntity().getContent();

                    InputStreamReader inputStreamReader1 = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader1);

                    StringBuilder stringBuilder1 = new StringBuilder();

                    String bufferedStrChunk = null;
                    //int data = inputStreamReader1.read();

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder1.append(bufferedStrChunk);
                    }
                    /*while (data != -1) {
                        result += (char) data;
                        data = inputStreamReader1.read();

                    }*/
                    result = stringBuilder1.toString();
                    //txtResult.setText("Response"+result);
                    return result;

                } catch (Exception e) {
                    Log.d("InputStream", e.getLocalizedMessage());
                }


                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                progressDialog.dismiss();
                try {
                    depositAmount = Double.parseDouble(Objects.requireNonNull(amountToDeposit.getText()).toString());
                } catch (Exception e) {
                }
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date newDate = calendar.getTime();
                String transferDate = sdf.format(newDate);
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String creditStatus = jsonObject.getString("data");
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                    JSONArray jsonArray2 = jsonObject.getJSONArray("details");

                    String recepientID = jsonObject.getString("id");

                    doTransferPostRequest(recepientID,depositAmount);



                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }
    }
    private void doTransferPostRequest(String recepientID,double transactionAmount) {

        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(OurAppBankTransfer.this);
                progressDialog.setMessage("Processing Payout");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject1 = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();
                InputStream inputStream = null;
                String result = "";
                String result1 = "";
                String result2 = "";
                String json1 = "";
                String json2 = "";
                HttpURLConnection urlConnection = null;

                try {
                    jsonObject.put("source", "balance");
                    jsonObject.put("amount", transactionAmount);
                    jsonObject.put("recipient", recepientID);
                    jsonObject.put("reason", "Loan payout");
                    jsonObject.put("currency", "NGN");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(PAYSTACK_EXECUTE_TRANSFER);
                    json1 = jsonObject.toString();
                    StringEntity stringEntity1 = new StringEntity(json1);
                    httpPost.setEntity(stringEntity1);
                    httpPost.setHeader("Authorization", AWAJIMA_PAYSTACK_SECRET_KEY);
                    httpPost.setHeader("Content-type", "application/json");
                    //httpPost.setHeader("Accept", "application/json");
                    HttpResponse httpResponse1 = httpclient.execute(httpPost);

                    inputStream = httpResponse1.getEntity().getContent();

                    InputStreamReader inputStreamReader1 = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader1);

                    StringBuilder stringBuilder1 = new StringBuilder();

                    String bufferedStrChunk = null;
                    //int data = inputStreamReader1.read();

                    while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                        stringBuilder1.append(bufferedStrChunk);
                    }
                    /*while (data != -1) {
                        result += (char) data;
                        data = inputStreamReader1.read();

                    }*/
                    result = stringBuilder1.toString();
                    //txtResult.setText("Response"+result);
                    return result;

                } catch (Exception e) {
                    Log.d("InputStream", e.getLocalizedMessage());
                }


                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                progressDialog.dismiss();


            }
        }
    }


}