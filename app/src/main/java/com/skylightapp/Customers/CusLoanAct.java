package com.skylightapp.Customers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.TransactionGranting;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.SuperAdmin.AdminBalance;
import com.twilio.Twilio;

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
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;

import static com.skylightapp.Transactions.OurConfig.INTERSWITCH_LS_SECRET_KEY;
import static com.skylightapp.Transactions.OurConfig.PAYSTACK_CREATE_TRANSFER_RECEPIENT;
import static com.skylightapp.Transactions.OurConfig.PAYSTACK_EXECUTE_TRANSFER;
import static com.skylightapp.Transactions.OurConfig.SKYLIGHT_SECRET_KEY;

public class CusLoanAct extends AppCompatActivity {
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json,phoneNo;
    private Customer selectedCustomer;
    private Profile userProfile;
    private StandingOrderAcct standingOrderAcct;
    double accountBalance1;
    String loanDate,customerName;
    int accountNo;
    int acctID;
    int customerID;
    int profileID;
    String bankAccountNo;

    int loanCode;
    String acctName;
    Button btnAddNewCus;
    private Marker m;
    double amountDouble;
    TextWatcher textWatcher;
    String noOfDaysString,txRef,accountbank,selectedBank;
    private DBHelper dbHelper;
    private AppCompatEditText edt_Amount;
    private AppCompatButton btn_getLoan;
    private  double standingOrderBalance,walletBalance,balance,fees,amountDouble1;
    AppCompatSpinner spnNigBank;
    AppCompatEditText edtAcctName,edtAcctNO;
    private  ProgressDialog progressDialog;
    private  Loan loan;
    private  Random random;
    private  Customer customer;
    private  int loanNumber,loanAcctNo;
    private Bundle loanBundle;
    private String loanType, loanOffice;
    private Account account;
    private AdminBalance adminBalance;
    private TransactionGranting granting;
    private static final String PREF_NAME = "skylight";
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_borrow_from_soacct);
        loanBundle= new Bundle();
        account= new Account();
        dbHelper = new DBHelper(this);
        gson = new Gson();
        userProfile= new Profile();
        random= new Random();
        adminBalance= new AdminBalance();
        granting= new TransactionGranting(loanNumber, profileID, customerID, customerName, selectedBank, acctName, accountNo, amountDouble1, "", loanDate, "", "", "inProgress");
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        edt_Amount =  findViewById(R.id.loanAmountSO);
        btn_getLoan = findViewById(R.id.ApplyForLoan);
        spnNigBank = findViewById(R.id.selected_bank_account35);
        edtAcctNO = findViewById(R.id._deposit_account_no34);
        edtAcctName = findViewById(R.id._deposit_account_name33);
        edt_Amount.addTextChangedListener(textWatcher);

        loanBundle=getIntent().getExtras();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        standingOrderAcct= new StandingOrderAcct();
        dbHelper = new DBHelper(this);
        btn_getLoan.setOnClickListener(this::addNewLoan);
        selectedCustomer= new Customer();
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        if(loanBundle !=null){
            loanType=loanBundle.getString("loanType");
        }

        if(userProfile !=null){
            selectedCustomer=userProfile.getProfileCus();
            standingOrderAcct=selectedCustomer.getCusStandingOrderAcct();
            standingOrderBalance=standingOrderAcct.getSoAcctBalance();
            account=userProfile.getProfileAccount();
            standingOrderAcct=userProfile.getProfileSOAcct();
            profileID=userProfile.getPID();


        }
        if(standingOrderAcct !=null){
            standingOrderBalance=standingOrderAcct.getSoAcctBalance();
            balance=standingOrderBalance;
        }
        if(selectedCustomer !=null){
            customerID=selectedCustomer.getCusUID();
            loanOffice=selectedCustomer.getCusOfficeBranch();
            customerName=selectedCustomer.getCusSurname()+","+customer.getCusFirstName();
        }
        if(account !=null){
            accountBalance1=account.getAccountBalance();

        }
        if(loanType.equals("EWallet")){
            balance=accountBalance1;
            if(account !=null){
                loanAcctNo=account.getSkyLightAcctNo();

            }

        }else {
            if(loanType.equals("StandingOrderAcct")){
                balance=standingOrderBalance;
                if(standingOrderAcct !=null){
                    loanAcctNo=standingOrderAcct.getSoAcctNo();

                }

            }
        }


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
        btn_getLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    bankAccountNo = edtAcctNO.getText().toString();

                } catch (NumberFormatException e) {
                    Toast.makeText(CusLoanAct.this, "Please enter a valid Account Number", Toast.LENGTH_SHORT).show();
                    edtAcctNO.requestFocus();
                }
                try {
                    acctName = edtAcctName.getText().toString();

                } catch (Exception e) {
                    Toast.makeText(CusLoanAct.this, "Please enter a valid Account Name", Toast.LENGTH_SHORT).show();
                    edtAcctName.requestFocus();
                }
                try {
                    amountDouble = Double.parseDouble(edt_Amount.getText().toString());

                } catch (NumberFormatException e) {
                    Toast.makeText(CusLoanAct.this, "Please enter a valid Amount", Toast.LENGTH_SHORT).show();
                    edt_Amount.requestFocus();
                }
                fees=balance*0.31;

                try {
                    if (amountDouble<=1000) {
                        Toast.makeText(CusLoanAct.this, "Loan Amount can not be less than N1,000", Toast.LENGTH_LONG).show();


                    }
                    if (amountDouble>balance) {
                        Toast.makeText(CusLoanAct.this, "Loan Amount can not exceed N"+balance, Toast.LENGTH_LONG).show();


                    }

                    else {
                        customer=selectedCustomer;
                        //ArrayList<TransactionGranting> grantingArrayList = dbHelper.getLoansFromCurrentCustomer(customerID);
                        amountDouble1=amountDouble-fees;

                        String timelineTittle="Subscriber Loan Request Alert";
                        String timelineDetails="Loan of N"+amountDouble+""+"requested for:"+customerID;
                        String timelineTittle2="Your Loan Request Alert";
                        Location location=null;
                        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        //calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
                        Date newDate = calendar.getTime();
                        loanDate = sdf.format(newDate);
                        try {
                            loanCode = random.nextInt((int) (Math.random() * 13) + 11+270);
                            txRef="SkylightAppLoan"+random;
                        } catch (NullPointerException e) {
                            System.out.println("Oops!");
                        }
                        String userTimelineText="You requested for a Loan Facility of N"+amountDouble+"@"+""+loanDate;
                        loanNumber = random.nextInt((int) (Math.random() * 12) + 111+77);
                        loan=new Loan(loanNumber,amountDouble,loanDate,"inProgress","",0.05);
                        loan.setAmount1(amountDouble1);
                        customer.addCusTimeLine(timelineTittle2,userTimelineText);
                        userProfile.addPTimeLine(timelineTittle2,userTimelineText);
                        if(loanType.equals("EWallet")){
                            loan.setAcctType(loanType);
                            loan.setLoan_account(account);
                            standingOrderAcct=null;

                        }else {
                            if(loanType.equals("StandingOrderAcct")){
                                balance=standingOrderBalance;
                                loan.setAcctType(loanType);
                                account=null;
                                loan.setSOAcct(standingOrderAcct);

                            }
                        }


                        loan.setLoanAcctID(loanAcctNo);
                        loan.setLoanAcctName(acctName);
                        loan.setLoanBankAcctNo(bankAccountNo);
                        loan.setLoanBank(selectedBank);
                        loan.setInterest(BigDecimal.valueOf(0.05));
                        loan.setLoanCode(loanCode);
                        loan.setLoan_profile(userProfile);
                        loan.setLoanOfficeBranch(loanOffice);
                        granting= new TransactionGranting(loanNumber,profileID,customerID,customerName,selectedBank,acctName,bankAccountNo,amountDouble1,"",loanDate,"","","inProgress");

                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            dbHelper.insertTimeLine(timelineTittle,timelineDetails,loanDate,location);


                        }
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            dbHelper.insertTransaction_Granting(loanNumber,profileID,customerID,customerName,amountDouble1,loanDate,selectedBank,acctName,bankAccountNo,"Loan","","","Loan","inProgress");



                        }
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            dbHelper.insertNewLoan(profileID,customerID,loanNumber,0.00,amountDouble1,loanDate, accountNo,loanType,loanCode,"inProgress");



                        }

                        granting.setTe_Type("Loan");
                        granting.setTe_Loan(loan);
                        loan.setLoan_granting(granting);
                        customer.addLoans(loanNumber,amountDouble,loanDate,"inProgress","",0.00);
                        userProfile.addPLoans(loanNumber,amountDouble,loanDate,"inProgress","",0.00);


                    }
                } catch (NumberFormatException e) {
                    System.out.println("Oops!");
                }

            }
        });


        /*textWatcher = new TextWatcher() {
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
                    txtTotalForToday.setText(String.valueOf(answer));
                }else {
                    txtTotalForToday.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };*/


    }
    public void addNewLoan(View view) {

        edt_Amount =  findViewById(R.id.loanAmountSO);
        btn_getLoan = findViewById(R.id.ApplyForLoan);
        spnNigBank = findViewById(R.id.selected_bank_account35);
        edtAcctNO = findViewById(R.id._deposit_account_no34);
        edtAcctName = findViewById(R.id._deposit_account_name33);
        edt_Amount.addTextChangedListener(textWatcher);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        userProfile= new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        dbHelper = new DBHelper(this);
        gson = new Gson();
        random= new Random();
        standingOrderAcct= new StandingOrderAcct();

        try {
            accountNo = Integer.parseInt(edtAcctNO.getText().toString());

        } catch (NumberFormatException e) {
            Toast.makeText(CusLoanAct.this, "Please enter a valid Account Number", Toast.LENGTH_SHORT).show();
            edtAcctNO.requestFocus();
        }
        try {
            acctName = edtAcctName.getText().toString();

        } catch (Exception e) {
            Toast.makeText(CusLoanAct.this, "Please enter a valid Account Name", Toast.LENGTH_SHORT).show();
            edtAcctName.requestFocus();
        }
        try {
            amountDouble = Double.parseDouble(edt_Amount.getText().toString());

        } catch (NumberFormatException e) {
            Toast.makeText(CusLoanAct.this, "Please enter a valid Amount", Toast.LENGTH_SHORT).show();
            edt_Amount.requestFocus();
        }
        fees=balance*0.31;

        try {
            if (amountDouble<=1000) {
                Toast.makeText(CusLoanAct.this, "Loan Amount can not be less than N1,000", Toast.LENGTH_LONG).show();


            }
            if (amountDouble>balance) {
                Toast.makeText(CusLoanAct.this, "Loan Amount can not exceed N"+balance, Toast.LENGTH_LONG).show();


            }

            else {
                customer=selectedCustomer;
                //ArrayList<TransactionGranting> grantingArrayList = dbHelper.getLoansFromCurrentCustomer(customerID);
                amountDouble1=amountDouble-fees;

                String timelineTittle="Subscriber Loan Request Alert";
                String timelineDetails="Loan of N"+amountDouble+""+"requested for:"+customerID;
                String timelineTittle2="Your Loan Request Alert";
                Location location=null;
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
                Date newDate = calendar.getTime();
                loanDate = sdf.format(newDate);
                try {
                    loanCode = random.nextInt((int) (Math.random() * 13) + 11+270);
                    txRef="SkylightAppLoan"+random;
                } catch (NullPointerException e) {
                    System.out.println("Oops!");
                }
                String userTimelineText="You requested for a Loan Facility of N"+amountDouble+"@"+""+loanDate;
                loanNumber = random.nextInt((int) (Math.random() * 12) + 111+77);
                loan=new Loan(loanNumber,amountDouble,loanDate,"inProgress","",0.05);
                loan.setAmount1(amountDouble1);
                customer.addCusTimeLine(timelineTittle2,userTimelineText);
                userProfile.addPTimeLine(timelineTittle2,userTimelineText);
                if(loanType.equals("EWallet")){
                    loan.setAcctType(loanType);
                    loan.setLoan_account(account);

                }else {
                    if(loanType.equals("StandingOrderAcct")){
                        balance=standingOrderBalance;
                        loan.setAcctType(loanType);
                        loan.setSOAcct(standingOrderAcct);

                    }
                }

                loan.setLoanAcctID(loanAcctNo);
                loan.setLoanAcctName(acctName);
                loan.setLoanBankAcctNo(bankAccountNo);
                loan.setLoanBank(selectedBank);
                loan.setInterest(BigDecimal.valueOf(0.05));
                loan.setLoanCode(loanCode);
                granting= new TransactionGranting(loanNumber,profileID,customerID,customerName,selectedBank,acctName,bankAccountNo,amountDouble1,"",loanDate,"","","inProgress");

                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    dbHelper.insertTimeLine(timelineTittle,timelineDetails,loanDate,location);

                }

                granting.setTe_Type("Loan");
                loan.setLoan_granting(granting);
                customer.addLoans(loanNumber,amountDouble,loanDate,"inProgress","",0.00);
                userProfile.addPLoans(loanNumber,amountDouble,loanDate,"inProgress","",0.00);
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    dbHelper.insertTransaction_Granting(loanNumber,profileID,customerID,customerName,amountDouble1,loanDate,selectedBank,acctName,bankAccountNo,"Loan","","","Loan","inProgress");



                }
                if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                    dbHelper.openDataBase();
                    dbHelper.insertNewLoan(profileID,customerID,loanNumber,0.00,amountDouble1,loanDate, accountNo,loanType,loanCode,"inProgress");



                }

            }

        } catch (NumberFormatException e) {
            System.out.println("Oops!");
        }
    }
    private void createTransferRecepient(long accountNo,String acctName,String accountbank,double amountDouble) {

        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(CusLoanAct.this);
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
                    jsonObject.put("name", acctName);
                    jsonObject.put("account_number", accountNo);
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
                    amountDouble1 = Double.parseDouble(Objects.requireNonNull(edt_Amount.getText()).toString());
                } catch (NumberFormatException e) {
                }
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date newDate = calendar.getTime();
                String transferDate = sdf.format(newDate);
                try {

                    JSONObject jsonObject = new JSONObject(result);
                    String creditStatus = jsonObject.getString("data");
                    JSONArray jsonArray1 = jsonObject.getJSONArray("data");
                    JSONArray jsonArray2 = jsonObject.getJSONArray("details");

                    String recepientID = jsonObject.getString("id");

                    doTransferPostRequest(recepientID,amountDouble1,accountNo,accountbank,acctName);



                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }
    }
    private void doTransferPostRequest(String recepientID,double amountDouble1,long accountNo,String accountbank,String acctName) {

        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(CusLoanAct.this);
                progressDialog.setMessage("Processing Payout");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                JSONObject jsonObject = new JSONObject();
                userPreferences = PreferenceManager.getDefaultSharedPreferences(CusLoanAct.this);
                gson = new Gson();
                userProfile= new Profile();
                json = userPreferences.getString("LastProfileUsed", "");
                userProfile = gson.fromJson(json, Profile.class);
                dbHelper = new DBHelper(CusLoanAct.this);
                gson = new Gson();
                random= new Random();
                standingOrderAcct= new StandingOrderAcct();
                InputStream inputStream = null;
                String result = "";
                String result1 = "";
                String result2 = "";
                String json1 = "";
                String json2 = "";
                HttpURLConnection urlConnection = null;

                try {

                    jsonObject.put("source", "balance");
                    jsonObject.put("amount", amountDouble1);
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
                    httpPost.setHeader("Authorization", SKYLIGHT_SECRET_KEY);
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
                    if(userProfile !=null){
                        selectedCustomer=userProfile.getProfileCus();
                        standingOrderAcct=selectedCustomer.getCusStandingOrderAcct();
                        standingOrderBalance=standingOrderAcct.getSoAcctBalance();
                        accountBalance1=standingOrderBalance-amountDouble1-fees;
                        customer=userProfile.getProfileCus();


                    }
                    standingOrderAcct.setSoAcctBalance(accountBalance1);


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

    public void goHome(View view) {
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putLong("ProfileID", profileID);
        bundle.putString(machine, machine);
        //bundle.putString("machine", "machine");
        //bundle.putString("FirstName", profileFirstName);
        Intent intent = new Intent(this, NewCustomerDrawer.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}