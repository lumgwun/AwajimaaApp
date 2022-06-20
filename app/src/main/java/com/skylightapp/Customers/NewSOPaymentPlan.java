package com.skylightapp.Customers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;

import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Transactions.OurConfig.CREATE_CUSTOMER;
import static com.skylightapp.Transactions.OurConfig.PAYSTACK_PLAN_CREATE_URL;
import static com.skylightapp.Transactions.OurConfig.PUBLIC_KEY_PAYSTACK;
import static com.skylightapp.Transactions.OurConfig.SKYLIGHT_SECRET_KEY;

public class NewSOPaymentPlan extends AppCompatActivity {
    public static final String KEY = "CompleteMySavingPayment.KEY";
    AppCompatSpinner planSpn;
    String callUri ="https://skylightciacs.com/wc-api/Tbz_WC_Paystack_Webhook/";
    String selectedPlan,planCode,PLAN_CODE,phoneNo,PHONE_NO,reference,REF,authorization_code,AUTORIZATION,email,EMAIL,CALL_BACK_LINK,
            callback_url;
    Customer customer;
    String uSurname, uFirstName,ManagerSurname,managerFirstName;
    Profile managerProfile;
    String currentDate;
    DBHelper applicationDb;
    Account account;
    int customerID;
    long acctID;
    int profileID;
    //double amount;
    Transaction transaction;
    String paystackRef;
    StandingOrder standingOrder;
    SecureRandom random =new SecureRandom();
    int transactionID ;
    String refID,planName;
    String customerSurname,customerFirstName ;


    int soNo ;
    String cusNames,machine;
    double soBalance=0.00;
    double expectedAmount,totalForToday;
    double amountDiff;
    double newBalance;
    String dateInString;
    private Gson gson;
    private String json;
    private SharedPreferences userPreferences;
    Profile userProfile;
    String txMessage="Savings Skylight,Congratulations! you have successfully started a standing order savings.";
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Bundle soBundle;
    int soAcctNo;
    double sOAmount,amountCarriedForward,sONowAmount,amtDiff,AMOUNT;
    AppCompatEditText edtAmount, edtsoDaysToPayToday, edtMonths;
    int noOfDays, frequencyIndex,currencyIndex,months,soDaysToPayToday,daysRemaining;
    String customerName, customerPhoneNo,customerEmail, endDate,amount,currency,frequency;
    AppCompatButton btnCreateAPlan;
    Spinner spnPlanFreq,spnPlanCurrency;
    private  ProgressDialog progressDialog;
    private AppCompatTextView txtResult;
    StandingOrderAcct standingOrderAcct;
    private static final String PREF_NAME = "skylight";
    com.skylightapp.Classes.Transaction Skylightransaction;
    ActivityResultLauncher<Intent> standingOrderStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    switch (result.getResultCode()) {
                        case Activity.RESULT_OK :
                            if (result.getData() != null) {
                                Intent data = result.getData();
                                Bundle bundle = data.getBundleExtra("paymentBundle");
                                standingOrderAcct = bundle.getParcelable("StandingOrderAcct");
                                applicationDb = bundle.getParcelable("DBHelper");
                                standingOrder = bundle.getParcelable("StandingOrder");
                                totalForToday = bundle.getDouble("Total");
                                expectedAmount = bundle.getDouble("GrandTotal");
                                profileID = bundle.getInt(PROFILE_ID);
                                daysRemaining = bundle.getInt("DaysRemaining");
                                amountCarriedForward = bundle.getDouble("AmountRemaining");
                                endDate = bundle.getString("EndDate");
                                selectedPlan = bundle.getString("PlanName");
                                frequency = bundle.getString("Frequency");
                                currency = bundle.getString("Currency");
                                standingOrderAcct= new StandingOrderAcct(soNo,customerName,sONowAmount);
                                customer.setCusStandingOrderAcct(standingOrderAcct);
                                //StandingOrderAcct.addStandingOrder(soNo,expectedAmount);
                                customer.addCusStandingOrder(soAcctNo,expectedAmount,sONowAmount,amountCarriedForward, currentDate, months,daysRemaining,endDate);
                                applicationDb = new DBHelper(NewSOPaymentPlan.this);
                                String managerFullNames = ManagerSurname + "," + managerFirstName;
                                String officeBranch=null;

                                String timelineSkylight = uSurname + "," + uFirstName + "'s new Package:" + selectedPlan + "plan was initiated by" + managerFullNames + "@" + currentDate;
                                String tittleT = "Standing order package Alert!";
                                String namesT = uSurname +"," +uFirstName;

                                String timelineUser = selectedPlan + "was initiated for you by" + managerFullNames + "@" + currentDate;
                                String timelineUserProfile = selectedPlan + "was initiated by you for" + namesT + "@" + currentDate;
                                String tittle = "Standing order package Alert";
                                com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type=null;
                                if(customer !=null){
                                    customer.addCusTransactions(sONowAmount);
                                    customer.setCusStandingOrder(standingOrder);
                                    customer.addCusTimeLine(tittleT,timelineUser);
                                    customer.setCusCurrency(currency);
                                    customer.addCusStandingOrder(transactionID,soNo,sONowAmount,currentDate,expectedAmount, AMOUNT,amountDiff,"just started", endDate);


                                }
                                if(userProfile !=null){
                                    userProfile.addTimeLine(tittle,timelineUserProfile);
                                    officeBranch=userProfile.getProfileOffice();
                                    userProfile.addTransaction(transactionID,uSurname,uFirstName,phoneNo,sONowAmount,PHONE_NO,"Standing order payment",currentDate,"Standing Order");

                                }
                                refID= "SkyLight/SO"+ random.nextInt((int) (Math.random() * 101) + 191);

                                if(customer!=null){
                                    transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.STANDING_ORDER;
                                    refID= "SkyLight/SO"+ random.nextInt((int) (Math.random() * 101) + 191);

                                    //Skylightransaction= new com.skylightapp.Classes.Transaction(refID, soAcctNo, currentDate, "Skylight", String.valueOf(soAcctNo), "Skylight", namesT, sONowAmount, transaction_type, "",officeBranch, "", "", "");

                                    standingOrderAcct=customer.getCusStandingOrderAcct();
                                    if(standingOrderAcct !=null){
                                        soAcctNo=standingOrderAcct.getSoAcctNo();

                                        try {
                                            applicationDb.insertStandingOrderAcct(profileID,customerID,soAcctNo,customerName,sONowAmount);
                                            applicationDb.saveNewTransaction(profileID, customerID,Skylightransaction, soAcctNo, "Skylight", namesT,transaction_type,sONowAmount, transactionID, officeBranch, currentDate);
                                            applicationDb.insertTimeLine(tittle, timelineSkylight, currentDate, null);
                                        } catch (SQLiteException e) {
                                            System.out.println("Oops!");
                                        }
                                    }

                                }
                                try {
                                    applicationDb.insertStandingOrder(profileID,customerID,soNo,soAcctNo,amountCarriedForward, currentDate,expectedAmount,sONowAmount,amtDiff, months,daysRemaining,endDate,"inProgress");


                                } catch (SQLiteException e) {
                                    System.out.println("Oops!");
                                }

                                //DBHelper.insertCustomerLocation(customerID1,cusLatLng);
                                Toast.makeText(NewSOPaymentPlan.this, "Standing order first payment successful", Toast.LENGTH_SHORT).show();

                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(NewSOPaymentPlan.this, "Activity canceled", Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + result.getResultCode());
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
        setContentView(R.layout.act_new_sopayment_plan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gson= new Gson();
        random= new SecureRandom();
        /*getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();*/
        planName=null;
        Skylightransaction= new com.skylightapp.Classes.Transaction();
        refID= "SkyLight/SO"+ random.nextInt((int) (Math.random() * 101) + 191);
        soAcctNo= random.nextInt((int) (Math.random() * 139211) + 1910276);
        soNo = random.nextInt((int) (Math.random() * 111) + 1561);
        transactionID = soNo+random.nextInt((int) (Math.random() * 91) + 19);
        userProfile= new Profile();
        standingOrderAcct= new StandingOrderAcct();
        standingOrder=new StandingOrder(soNo, expectedAmount);

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        applicationDb = new DBHelper(this);
        edtsoDaysToPayToday = findViewById(R.id.soDaysToPay);
        edtAmount = findViewById(R.id.soAmt);
        txtResult = findViewById(R.id.txtResult);
        edtMonths = findViewById(R.id.soNoOfMonths);
        btnCreateAPlan = findViewById(R.id.createSO);
        Intent intent= getIntent();
        soBundle=intent.getExtras();
        spnPlanFreq = findViewById(R.id.planFrequency);
        spnPlanCurrency = findViewById(R.id.planCurrency);
        spnPlanFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //frequency = spnPlanFreq.getSelectedItem().toString();
                frequency = (String) adapterView.getItemAtPosition(i);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnPlanCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                currency = (String) adapterView.getItemAtPosition(i);
                //currency = spnPlanCurrency.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        PaystackSdk.initialize(getApplicationContext());
        PaystackSdk.setPublicKey(PUBLIC_KEY_PAYSTACK);
        Transaction transaction1 = new Transaction();
        paystackRef = transaction1.getReference();
        Date date = new Date();
        standingOrderAcct = new StandingOrderAcct();
        customer= new Customer();
        if(userProfile !=null){
            ManagerSurname=userProfile.getProfileLastName();
            managerFirstName=userProfile.getProfileFirstName();
            uFirstName= customer.getCusFirstName();

            profileID=userProfile.getPID();
        }

        if(soBundle !=null){
            userProfile=soBundle.getParcelable("Profile");
            customer=soBundle.getParcelable("Customer");
            amountCarriedForward=soBundle.getDouble("PackageAmount");
            edtAmount.setText("Standing Amount is NGN:"+amountCarriedForward);
            edtAmount.setActivated(false);
            customerName=soBundle.getString("Names");
            months =soBundle.getInt("daysRemaining");
            customerPhoneNo=soBundle.getString("PhoneNo");
            customerID=soBundle.getInt("customerID");
            customerEmail=soBundle.getString("EmailAddress");
            expectedAmount=soBundle.getDouble("expectedAmount");

        }else {
            if(userProfile !=null){
                ManagerSurname=userProfile.getProfileLastName();
                managerFirstName=userProfile.getProfileFirstName();
                uFirstName= customer.getCusFirstName();
                profileID=userProfile.getPID();
                customer=userProfile.getTimelineCustomer();
                customerSurname=customer.getCusSurname();
                customerFirstName=customer.getCusFirstName();
                customerName=customer.getCusSurname()+""+customer.getCusFirstName();
                customerPhoneNo=customer.getCusPhoneNumber();
                customerID=customer.getCusUID();
                customerEmail=customer.getCusEmailAddress();
                standingOrderAcct = customer.getCusStandingOrderAcct();
            }

        }
        btnCreateAPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaystackSdk.initialize(getApplicationContext());
                PaystackSdk.setPublicKey(PUBLIC_KEY_PAYSTACK);
                SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = dateFormatWithZone.format(date);
                userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                json = userPreferences.getString("LastProfileUsed", "");
                userProfile = gson.fromJson(json, Profile.class);
                spnPlanFreq = findViewById(R.id.planFrequency);
                spnPlanCurrency = findViewById(R.id.planCurrency);


                String Enddate = null;
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, noOfDays);
                Date resultdate = new Date(c.getTimeInMillis());
                Enddate = sdf.format(resultdate);

                /*try {
                    if (Enddate != null) {
                        c.setTime(sdf.parse(Enddate));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                spnPlanFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        frequency = (String) adapterView.getItemAtPosition(i);
                        //frequency = spnPlanFreq.getSelectedItem().toString();

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                frequency = spnPlanFreq.getSelectedItem().toString();

                spnPlanCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //currency = spnPlanCurrency.getSelectedItem().toString();
                        currency = (String) adapterView.getItemAtPosition(i);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                currency = spnPlanCurrency.getSelectedItem().toString();
                try {
                    sOAmount=Double.parseDouble(edtAmount.getText().toString().trim());

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    soDaysToPayToday=Integer.parseInt(edtsoDaysToPayToday.getText().toString().trim());

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    months =Integer.parseInt(edtMonths.getText().toString().trim());
                    noOfDays=months*31;


                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if (sOAmount<=0)  {
                    Toast.makeText(NewSOPaymentPlan.this, "Please enter the New Standing Order Amount..", Toast.LENGTH_SHORT).show();
                }
                if (soDaysToPayToday<=0) {
                    Toast.makeText(NewSOPaymentPlan.this, "Please enter the no. of days to pay for today..", Toast.LENGTH_SHORT).show();
                }
                if (months<=0)  {
                    Toast.makeText(NewSOPaymentPlan.this, "Please enter the number of months for your savings..", Toast.LENGTH_SHORT).show();
                }
                else {

                    c.add(Calendar.DATE, noOfDays);
                    resultdate = new Date(c.getTimeInMillis());
                    endDate = sdf.format(resultdate);

                    expectedAmount=sOAmount * months*31;
                    sONowAmount=sOAmount*soDaysToPayToday;
                    //amountCarriedForward=sOAmount;
                    amtDiff=expectedAmount-sONowAmount;
                    daysRemaining= (months*31) -soDaysToPayToday;
                    if(userProfile !=null){
                        ManagerSurname=userProfile.getProfileLastName();
                        managerFirstName=userProfile.getProfileFirstName();
                        uFirstName= customer.getCusFirstName();
                        profileID=userProfile.getPID();
                        customer=userProfile.getTimelineCustomer();
                        if(customer !=null){
                            customerName=customer.getCusSurname()+""+customer.getCusFirstName();
                            customerPhoneNo=customer.getCusPhoneNumber();
                            customerID=customer.getCusUID();
                            customerSurname=customer.getCusSurname();
                            customerFirstName=customer.getCusFirstName();
                            customerEmail=customer.getCusEmailAddress();
                            standingOrderAcct = customer.getCusStandingOrderAcct();
                            customerName=customer.getCusSurname()+""+customer.getCusFirstName();
                            customerPhoneNo=customer.getCusPhoneNumber();
                            customerID=customer.getCusUID();
                            customerEmail=customer.getCusEmailAddress();
                            //account = customer.getStandingOrderAcct();
                        }
                        customer=userProfile.getTimelineCustomer();

                    }

                    //acctID = account.getAcctID();
                    planName="N"+sOAmount+""+frequency+""+"plan";
                    String plan_code= "SkylightSO"+sOAmount+currentDate;
                    String description="My Skylight Standing Order"+currency+sOAmount;
                    standingOrder= new StandingOrder(soNo,customerName,expectedAmount);

                    sendPostRequest(reference, plan_code, authorization_code, sOAmount, email,callback_url,phoneNo,expectedAmount,sONowAmount,amtDiff,daysRemaining,customerName,frequency,currency,description,endDate,standingOrder,profileID,planName,customerSurname,customerFirstName);

                }

            }
        });



    }
    private void sendPostRequest(String reference, String plan_code,String authorization_code,double sOAmount,String email,String callback_url,String phoneNo,double expectedAmount,double sONowAmount,double amtDiff,int daysRemaining,String customerName,String frequency,String currency,String description,String endDate,StandingOrder standingOrder,long profileID,String planName,String customerSurname,String customerFirstName) {

        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(NewSOPaymentPlan.this);
                progressDialog.setMessage("Creating Plan");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject1 = new JSONObject();
                InputStream inputStream = null;
                String json1 = "";
                String result = "";
                HttpURLConnection urlConnection = null;
                double finalAmount=100*sOAmount;
                try {
                    jsonObject1.put("email",email);
                    jsonObject1.put("first_name",customerFirstName);
                    jsonObject1.put("last_name",customerSurname);
                    jsonObject1.put("phone",phoneNo);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    jsonObject.put("name",planName);
                    jsonObject.put("interval",frequency);
                    jsonObject.put("amount",finalAmount);
                    jsonObject.put("integration","");
                    jsonObject.put("domain","");
                    jsonObject.put("plan_code",plan_code);
                    jsonObject.put("description","");
                    jsonObject.put("send_invoices","true");
                    jsonObject.put("send_sms","true");
                    jsonObject.put("currency",currency);
                    jsonObject.put("invoice_limit",1000);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(CREATE_CUSTOMER);
                    json1 = jsonObject1.toString();
                    StringEntity se = new StringEntity(json1);
                    httpPost.setEntity(se);
                    httpPost.setHeader("Authorization", SKYLIGHT_SECRET_KEY);
                    httpPost.setHeader("Content-type", "application/json");
                    //httpPost.setHeader("Accept", "application/json");
                    HttpResponse httpResponse1 = httpclient.execute(httpPost);

                    inputStream = httpResponse1.getEntity().getContent();

                    InputStreamReader inputStreamReader1 = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader1);

                    StringBuilder stringBuilder = new StringBuilder();

                    String bufferedStrChunk = null;
                    int data = inputStreamReader1.read();

                    while((bufferedStrChunk = bufferedReader.readLine()) != null){
                        stringBuilder.append(bufferedStrChunk);
                    }
                    /*while (data != -1) {
                        result += (char) data;
                        data = inputStreamReader1.read();

                    }*/
                    result=stringBuilder.toString();
                    //txtResult.setText("Response"+result);
                    return result;

                } catch (Exception e) {
                    Log.d("InputStream", e.getLocalizedMessage());
                }



                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(PAYSTACK_PLAN_CREATE_URL);
                    String json = "";
                    json = jsonObject.toString();
                    StringEntity se = new StringEntity(json);
                    httpPost.setEntity(se);
                    httpPost.setHeader("Authorization", SKYLIGHT_SECRET_KEY);
                    httpPost.setHeader("Content-type", "application/json");
                    httpPost.setHeader("Accept", "application/json");
                    HttpResponse httpResponse = httpclient.execute(httpPost);

                    inputStream = httpResponse.getEntity().getContent();

                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    StringBuilder stringBuilder = new StringBuilder();

                    String bufferedStrChunk = null;
                    int data = inputStreamReader.read();

                    while((bufferedStrChunk = bufferedReader.readLine()) != null){
                        stringBuilder.append(bufferedStrChunk);
                    }
                    while (data != -1) {
                        result += (char) data;
                        data = inputStreamReader.read();

                    }
                    result=stringBuilder.toString();
                    txtResult.setText("Response"+result);
                    if(result !=null){

                        doPayStackCardPayment(sONowAmount,standingOrderAcct,applicationDb,standingOrder,endDate,expectedAmount,amtDiff,daysRemaining,profileID,planName,frequency,currency);

                    }



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

    private void doPayStackCardPayment(double sONowAmount,StandingOrderAcct standingOrderAcct, DBHelper applicationDb,StandingOrder standingOrder,String endDate,double expectedAmount,Double amtDiff,int daysRemaining,long profileID,String planName,String frequency,String currency) {
        Bundle paymentBundle=new Bundle();
        Intent intent8 = new Intent(this, PayNowActivity.class);
        paymentBundle.putDouble("Total", sONowAmount);
        paymentBundle.putString("EndDate", endDate);
        paymentBundle.putLong(PROFILE_ID, profileID);
        paymentBundle.putDouble("AmountRemaining", amtDiff);
        paymentBundle.putDouble("DaysRemaining", daysRemaining);
        paymentBundle.putDouble("GrandTotal", expectedAmount);
        paymentBundle.putString("PlanName", planName);
        paymentBundle.putString("Currency", currency);
        paymentBundle.putString("Frequency", frequency);
        paymentBundle.putParcelable("StandingOrderAcct", standingOrderAcct);
        paymentBundle.putParcelable("DBHelper", (Parcelable) applicationDb);
        paymentBundle.putParcelable("StandingOrder", standingOrder);
        intent8.putExtras(paymentBundle);
        startActivity(intent8);
        standingOrderStartForResult.launch(new Intent(intent8));
    }
}