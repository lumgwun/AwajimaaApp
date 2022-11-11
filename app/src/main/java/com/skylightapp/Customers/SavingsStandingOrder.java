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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.AwardDAO;
import com.skylightapp.Database.BirthdayDAO;
import com.skylightapp.Database.CodeDAO;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.GrpProfileDAO;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.OfficeBranchDAO;
import com.skylightapp.Database.PaymDocDAO;
import com.skylightapp.Database.PaymentCodeDAO;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.StockTransferDAO;
import com.skylightapp.Database.StocksDAO;
import com.skylightapp.Database.TCashDAO;
import com.skylightapp.Database.TReportDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.Database.TransactionGrantingDAO;
import com.skylightapp.Database.WorkersDAO;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.skylightapp.Transactions.OurConfig;

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
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Transactions.OurConfig.PUBLIC_KEY_PAYSTACK;
import static com.skylightapp.Transactions.OurConfig.SKYLIGHT_PAYSTACK_PLAN_UPDATE;

@SuppressWarnings({"ResultOfMethodCallIgnored", "EqualsBetweenInconvertibleTypes"})
public class SavingsStandingOrder extends AppCompatActivity {
    public static final String KEY = "SavingsStandingOrder.KEY";
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
    double amount;
    Transaction transaction;
    String paystackRef;
    StandingOrder standingOrder;
    StandingOrder standingOrderNew;
    SecureRandom random =new SecureRandom();
    int transactionID ;
    String refID,customerSurname,customerFirstName ;

    int soNo ;
    String cusNames,machine;
    double soBalance=0.00;
    double expectedAmount,totalForToday;
    double amountDiff;
    double newBalance;
    String dateInString,frequency,currency;
    private Gson gson;
    private int AMOUNT;
    private String json;
    private SharedPreferences userPreferences;
    Profile userProfile;
    String txMessage="Savings Awajima,Congratulations! you have successfully started a standing order savings.";
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Transaction transaction1;
    Spinner spnFrequency, spnCurrency;
    private  ProgressDialog progressDialog;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;
    private CusDAO cusDAO;
    private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private PaymentDAO paymentDAO;
    private AdminBalanceDAO adminBalanceDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private GrpProfileDAO grpProfileDAO;
    private StocksDAO stocksDAO;
    private WorkersDAO workersDAO;
    private StockTransferDAO stockTransferDAO;
    private OfficeBranchDAO officeBranchDAO;
    private BirthdayDAO birthdayDAO;
    private TransactionGrantingDAO grantingDAO;
    private AwardDAO awardDAO;

    private AppCompatTextView txtResult;
    int soAcctNo;
    double sOAmount,amountCarriedForward,sONowAmount,amtDiff;
    int noOfDays, months,daysRemaining;
    String customerName, customerPhoneNo,customerEmail, endDate;
    StandingOrderAcct standingOrderAcct;
    String Enddate,officeBranch;
    private static final String PREF_NAME = "awajima";
    AppCompatEditText edtNoOfMonths;
    private MaterialCardView cardView;
    com.skylightapp.Classes.Transaction Skylightransaction;
    com.skylightapp.Classes.Transaction.TRANSACTION_TYPE transaction_type;
    ActivityResultLauncher<Intent> standingOrderUpdateStartForResult = registerForActivityResult(
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

                                customer.addCusStandingOrder(soAcctNo,expectedAmount,sONowAmount,amountCarriedForward, currentDate, months,daysRemaining,endDate);
                                applicationDb = new DBHelper(SavingsStandingOrder.this);
                                String managerFullNames = ManagerSurname + "," + managerFirstName;

                                String timelineSkylight = uSurname + "," + uFirstName + "'s new Package:" + selectedPlan + "plan was initiated by" + managerFullNames + "@" + currentDate;
                                String tittleT = "Standing order package Alert!";
                                String namesT = uSurname + uFirstName;

                                String timelineUser = selectedPlan + "was initiated for you by" + managerFullNames + "@" + currentDate;
                                String timelineUserProfile = selectedPlan + "was initiated by you for" + namesT + "@" + currentDate;
                                String tittle = "Standing order package Alert";
                                transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.STANDING_ORDER;
                                refID= "Awajima/SO"+ random.nextInt((int) (Math.random() * 101) + 191);
                                if(userProfile !=null){
                                    officeBranch=userProfile.getProfOfficeName();
                                    userProfile.addPTransaction(transactionID,uSurname,uFirstName,phoneNo,sONowAmount,PHONE_NO,"Standing order payment",currentDate,"Standing Order");

                                }
                                Skylightransaction= new com.skylightapp.Classes.Transaction(transactionID, soAcctNo, currentDate, 100001, soAcctNo, "Awajima", namesT, sONowAmount, transaction_type, "",officeBranch, "", "", "");

                                if(customer !=null){

                                    customer.addCusTransactions(sONowAmount);
                                    customer.setCusStandingOrder(standingOrder);
                                    customer.addCusTimeLine(tittleT,timelineUser);
                                    customer.setCusCurrency(currency);
                                    customer.addCusStandingOrder(transactionID,soNo,sONowAmount,currentDate,expectedAmount, AMOUNT,amountDiff,"just started", endDate);


                                }
                                if(userProfile !=null){
                                    officeBranch=userProfile.getProfOfficeName();
                                    userProfile.addPTimeLine(tittle,timelineUserProfile);
                                    userProfile.addPTransaction(transactionID,uSurname,uFirstName,phoneNo,sONowAmount,PHONE_NO,"Standing order payment",currentDate,"Standing Order");

                                }
                                if(customer!=null){
                                    tranXDAO= new TranXDAO(getApplicationContext());
                                    timeLineClassDAO= new TimeLineClassDAO(getApplicationContext());
                                    standingOrderAcct=customer.getCusStandingOrderAcct();
                                    sodao= new SODAO(SavingsStandingOrder.this);
                                    if(standingOrderAcct !=null){
                                        soAcctNo=standingOrderAcct.getSoAcctNo();

                                        try {
                                            sodao.insertStandingOrderAcct(profileID,customerID,soAcctNo,customerName,sONowAmount);
                                            tranXDAO.saveNewTransaction(profileID, customerID,Skylightransaction, soAcctNo, "Awajima", namesT,transaction_type,sONowAmount, transactionID, officeBranch, currentDate);
                                            timeLineClassDAO.insertTimeLine(tittle, timelineSkylight, currentDate, null);
                                        } catch (SQLiteException e) {
                                            System.out.println("Oops!");
                                        }
                                    }

                                }
                                try {
                                    sodao= new SODAO(SavingsStandingOrder.this);
                                    sodao.insertStandingOrder(profileID,customerID,soNo,soAcctNo,amountCarriedForward, currentDate,expectedAmount,sONowAmount,amtDiff, months,daysRemaining,endDate,"inProgress");


                                } catch (SQLiteException e) {
                                    System.out.println("Oops!");
                                }

                                //DBHelper.insertCustomerLocation(customerID1,cusLatLng);
                                Toast.makeText(SavingsStandingOrder.this, "Standing order first payment successful", Toast.LENGTH_SHORT).show();

                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(SavingsStandingOrder.this, "Activity canceled", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.act_savings_so_app);
        setTitle("Savings Standing Orders");
        planSpn = findViewById(R.id.planSpinner);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userProfile= new Profile();

        spnFrequency = findViewById(R.id.SOFrequency2);
        spnCurrency = findViewById(R.id.SOCurrency);
        txtResult = findViewById(R.id.txtResult4);
        edtNoOfMonths = findViewById(R.id.soNoOfMonths4);
        cardView = findViewById(R.id.cd_material);

        Date date = new Date();
        workersDAO= new WorkersDAO(this);
        awardDAO= new AwardDAO(this);
        grantingDAO= new TransactionGrantingDAO(this);
        stocksDAO= new StocksDAO(this);
        cusDAO= new CusDAO(this);
        birthdayDAO= new BirthdayDAO(this);
        officeBranchDAO= new OfficeBranchDAO(this);
        stockTransferDAO= new StockTransferDAO(this);

        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        paymentDAO= new PaymentDAO(this);
        adminBalanceDAO= new AdminBalanceDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);
        grpProfileDAO= new GrpProfileDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
        transaction_type=null;
        Skylightransaction= new com.skylightapp.Classes.Transaction();

        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormatWithZone.format(date);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        refID= "Awajima/"+currentDate+ random.nextInt((int) (Math.random() * 9091) + 19);
        transactionID = random.nextInt((int) (Math.random() * 91) + 19);
        soNo = random.nextInt((int) (Math.random() * 75310) + 13570);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gson = new Gson();
        spnFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //frequency = spnFrequency.getSelectedItem().toString();
                frequency = (String) adapterView.getItemAtPosition(i);


            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //currency = spnCurrency.getSelectedItem().toString();
                currency = (String) adapterView.getItemAtPosition(i);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        applicationDb = new DBHelper(this);
        customer= new Customer();
        account=new Account();
        if(customer !=null){
            EMAIL= customer.getCusEmail();
            PHONE_NO= customer.getCusPhoneNumber();
            uSurname=customer.getCusSurname();
            account = customer.getCusAccount();
            if(account !=null){
                acctID = account.getAwajimaAcctNo();
            }
            customerID = customer.getCusUID();

            cusNames=uSurname+","+uFirstName;
            standingOrder= new StandingOrder(soNo,cusNames,soBalance);

        }
        if(userProfile !=null){
            ManagerSurname=userProfile.getProfileLastName();
            managerFirstName=userProfile.getProfileFirstName();
            uFirstName= customer.getCusFirstName();

            profileID=userProfile.getPID();
        }
        
        //profileSurname=userProfile.getSurname();
        //profileFirstName=userProfile.getFirstName();
        //profilePhone=userProfile.getPhoneNumber();
        machine=userPreferences.getString("Machine","");


        transaction1 = new Transaction();

        PaystackSdk.initialize(getApplicationContext());
        PaystackSdk.setPublicKey(PUBLIC_KEY_PAYSTACK);
        Bundle planBundle = new Bundle();


        AppCompatButton proceed_Button=findViewById(R.id.proceed_Button);

        ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(SavingsStandingOrder.this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.plans));

        myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        planSpn.setAdapter(myAdaptor);

        planSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {

            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {

                selectedPlan = (String) arg0.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), selectedPlan, Toast.LENGTH_SHORT).show();
                //selectedPlan = String.valueOf(planSpn.getSelectedItem());

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });
        if(selectedPlan !=null){
            if (selectedPlan.equalsIgnoreCase("NGN100")) {
                AMOUNT=100;
                PLAN_CODE= String.valueOf(R.string.NGN100);

            }
            if (selectedPlan.equalsIgnoreCase("NGN200")) {
                AMOUNT=200;
                PLAN_CODE= String.valueOf(R.string.NGN200);

            }
            if (selectedPlan.equalsIgnoreCase("NGN300")) {
                AMOUNT=300;
                PLAN_CODE= String.valueOf(R.string.NGN300);

            }
            if (selectedPlan.equalsIgnoreCase("NGN400")) {
                AMOUNT=400;
                PLAN_CODE= String.valueOf(R.string.NGN400);

            }
            if (selectedPlan.equalsIgnoreCase("NGN500")) {
                AMOUNT=500;
                PLAN_CODE= String.valueOf(R.string.NGN500);

            }
            if (selectedPlan.equalsIgnoreCase("NGN600")) {
                AMOUNT=600;
                PLAN_CODE= String.valueOf(R.string.NGN600);

            }
            if (selectedPlan.equalsIgnoreCase("NGN700")) {
                AMOUNT=700;
                PLAN_CODE= String.valueOf(R.string.NGN700);

            }
            if (selectedPlan.equalsIgnoreCase("NGN800")) {
                AMOUNT=800;
                PLAN_CODE= String.valueOf(R.string.NGN800);

            }
            if (selectedPlan.equalsIgnoreCase("NGN900")) {
                AMOUNT=900;
                PLAN_CODE= String.valueOf(R.string.NGN900);

            }
            if (selectedPlan.equalsIgnoreCase("NGN1000")) {
                AMOUNT=1000;
                PLAN_CODE= String.valueOf(R.string.NGN1000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN2000")) {
                AMOUNT=2000;
                PLAN_CODE= String.valueOf(R.string.NGN2000);


            }
            if (selectedPlan.equalsIgnoreCase("NGN3000")) {
                AMOUNT=3000;
                PLAN_CODE= String.valueOf(R.string.NGN3000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN4000")) {
                AMOUNT=4000;
                PLAN_CODE= String.valueOf(R.string.NGN4000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN5000")) {
                AMOUNT=5000;
                PLAN_CODE= String.valueOf(R.string.NGN5000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN6000")) {
                AMOUNT=6000;
                PLAN_CODE= String.valueOf(R.string.NGN6000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN7000")) {
                AMOUNT=7000;
                PLAN_CODE= String.valueOf(R.string.NGN7000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN8000")) {
                AMOUNT=8000;
                PLAN_CODE= String.valueOf(R.string.NGN8000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN9000")) {
                AMOUNT=9000;
                PLAN_CODE= String.valueOf(R.string.NGN9000);


            }
            if (selectedPlan.equalsIgnoreCase("NGN10000")) {
                AMOUNT=10000;
                PLAN_CODE= String.valueOf(R.string.NGN10000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN20000")) {
                AMOUNT=20000;
                PLAN_CODE= String.valueOf(R.string.NGN20000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN30000")) {
                AMOUNT=30000;
                PLAN_CODE= String.valueOf(R.string.NGN30000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN40000")) {
                AMOUNT=40000;
                PLAN_CODE.equals(R.string.NGN40000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN50000")) {
                AMOUNT=50000;
                PLAN_CODE= String.valueOf(R.string.NGN50000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN60000")) {
                AMOUNT=60000;
                PLAN_CODE= String.valueOf(R.string.NGN60000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN70000")) {
                AMOUNT=70000;
                PLAN_CODE= String.valueOf(R.string.NGN70000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN80000")) {
                AMOUNT=80000;
                PLAN_CODE= String.valueOf(R.string.NGN80000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN90000")) {
                AMOUNT=90000;
                PLAN_CODE= String.valueOf(R.string.NGN90000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN100000")) {
                AMOUNT=100000;
                PLAN_CODE= String.valueOf(R.string.NGN100000);


            }
            if (selectedPlan.equalsIgnoreCase("NGN200000")) {
                AMOUNT=200000;
                PLAN_CODE= String.valueOf(R.string.NGN200000);


            }
            if (selectedPlan.equalsIgnoreCase("NGN300000")) {
                AMOUNT=300000;
                PLAN_CODE= String.valueOf(R.string.NGN300000);


            }
            if (selectedPlan.equalsIgnoreCase("NGN400000")) {
                AMOUNT=400000;
                PLAN_CODE= String.valueOf(R.string.NGN400000);


            }
            if (selectedPlan.equalsIgnoreCase("NGN500000")) {
                AMOUNT=500000;
                PLAN_CODE= String.valueOf(R.string.NGN500000);


            }
            if (selectedPlan.equalsIgnoreCase("NGN600000")) {
                AMOUNT=600000;
                PLAN_CODE= String.valueOf(R.string.NGN600000);


            }
            if (selectedPlan.equalsIgnoreCase("NGN700000")) {
                AMOUNT=700000;
                PLAN_CODE= String.valueOf(R.string.NGN700000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN800000")) {
                AMOUNT=800000;
                PLAN_CODE= String.valueOf(R.string.NGN800000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN900000")) {
                AMOUNT=900000;
                PLAN_CODE= String.valueOf(R.string.NGN900000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN1000000")) {
                AMOUNT=1000000;
                PLAN_CODE= String.valueOf(R.string.NGN1000000);

            }
            if (selectedPlan.equalsIgnoreCase("NGN10000000")) {
                AMOUNT=10000000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }

        }

        proceed_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaystackSdk.initialize(getApplicationContext());
                PaystackSdk.setPublicKey(PUBLIC_KEY_PAYSTACK);
                SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = dateFormatWithZone.format(date);
                userProfile= new Profile();
                userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                //userPreferences = PreferenceManager.getDefaultSharedPreferences(SavingsStandingOrder.this);
                json = userPreferences.getString("LastProfileUsed", "");
                userProfile = gson.fromJson(json, Profile.class);

                spnCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //currency = spnCurrency.getSelectedItem().toString();
                        currency = (String) adapterView.getItemAtPosition(i);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                planSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    public void onClick(View v) {

                    }

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View view,
                                               int position, long row_id) {

                        selectedPlan = (String) arg0.getItemAtPosition(position);
                        Toast.makeText(SavingsStandingOrder.this, selectedPlan, Toast.LENGTH_SHORT).show();
                        //selectedPlan = String.valueOf(planSpn.getSelectedItem());


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }

                });
                if(selectedPlan !=null){
                    if (selectedPlan.equalsIgnoreCase("NGN100")) {
                        AMOUNT=100;
                        PLAN_CODE= String.valueOf(R.string.NGN100);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN200")) {
                        AMOUNT=200;
                        PLAN_CODE= String.valueOf(R.string.NGN200);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN300")) {
                        AMOUNT=300;
                        PLAN_CODE= String.valueOf(R.string.NGN300);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN400")) {
                        AMOUNT=400;
                        PLAN_CODE= String.valueOf(R.string.NGN400);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN500")) {
                        AMOUNT=500;
                        PLAN_CODE= String.valueOf(R.string.NGN500);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN600")) {
                        AMOUNT=600;
                        PLAN_CODE= String.valueOf(R.string.NGN600);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN700")) {
                        AMOUNT=700;
                        PLAN_CODE= String.valueOf(R.string.NGN700);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN800")) {
                        AMOUNT=800;
                        PLAN_CODE= String.valueOf(R.string.NGN800);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN900")) {
                        AMOUNT=900;
                        PLAN_CODE= String.valueOf(R.string.NGN900);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN1000")) {
                        AMOUNT=1000;
                        PLAN_CODE= String.valueOf(R.string.NGN1000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN2000")) {
                        AMOUNT=2000;
                        PLAN_CODE= String.valueOf(R.string.NGN2000);


                    }
                    if (selectedPlan.equalsIgnoreCase("NGN3000")) {
                        AMOUNT=3000;
                        PLAN_CODE= String.valueOf(R.string.NGN3000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN4000")) {
                        AMOUNT=4000;
                        PLAN_CODE= String.valueOf(R.string.NGN4000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN5000")) {
                        AMOUNT=5000;
                        PLAN_CODE= String.valueOf(R.string.NGN5000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN6000")) {
                        AMOUNT=6000;
                        PLAN_CODE= String.valueOf(R.string.NGN6000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN7000")) {
                        AMOUNT=7000;
                        PLAN_CODE= String.valueOf(R.string.NGN7000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN8000")) {
                        AMOUNT=8000;
                        PLAN_CODE= String.valueOf(R.string.NGN8000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN9000")) {
                        AMOUNT=9000;
                        PLAN_CODE= String.valueOf(R.string.NGN9000);


                    }
                    if (selectedPlan.equalsIgnoreCase("NGN10000")) {
                        AMOUNT=10000;
                        PLAN_CODE= String.valueOf(R.string.NGN10000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN20000")) {
                        AMOUNT=20000;
                        PLAN_CODE= String.valueOf(R.string.NGN20000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN30000")) {
                        AMOUNT=30000;
                        PLAN_CODE= String.valueOf(R.string.NGN30000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN40000")) {
                        AMOUNT=40000;
                        PLAN_CODE.equals(R.string.NGN40000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN50000")) {
                        AMOUNT=50000;
                        PLAN_CODE= String.valueOf(R.string.NGN50000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN60000")) {
                        AMOUNT=60000;
                        PLAN_CODE= String.valueOf(R.string.NGN60000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN70000")) {
                        AMOUNT=70000;
                        PLAN_CODE= String.valueOf(R.string.NGN70000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN80000")) {
                        AMOUNT=80000;
                        PLAN_CODE= String.valueOf(R.string.NGN80000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN90000")) {
                        AMOUNT=90000;
                        PLAN_CODE= String.valueOf(R.string.NGN90000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN100000")) {
                        AMOUNT=100000;
                        PLAN_CODE= String.valueOf(R.string.NGN100000);


                    }
                    if (selectedPlan.equalsIgnoreCase("NGN200000")) {
                        AMOUNT=200000;
                        PLAN_CODE= String.valueOf(R.string.NGN200000);


                    }
                    if (selectedPlan.equalsIgnoreCase("NGN300000")) {
                        AMOUNT=300000;
                        PLAN_CODE= String.valueOf(R.string.NGN300000);


                    }
                    if (selectedPlan.equalsIgnoreCase("NGN400000")) {
                        AMOUNT=400000;
                        PLAN_CODE= String.valueOf(R.string.NGN400000);


                    }
                    if (selectedPlan.equalsIgnoreCase("NGN500000")) {
                        AMOUNT=500000;
                        PLAN_CODE= String.valueOf(R.string.NGN500000);


                    }
                    if (selectedPlan.equalsIgnoreCase("NGN600000")) {
                        AMOUNT=600000;
                        PLAN_CODE= String.valueOf(R.string.NGN600000);


                    }
                    if (selectedPlan.equalsIgnoreCase("NGN700000")) {
                        AMOUNT=700000;
                        PLAN_CODE= String.valueOf(R.string.NGN700000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN800000")) {
                        AMOUNT=800000;
                        PLAN_CODE= String.valueOf(R.string.NGN800000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN900000")) {
                        AMOUNT=900000;
                        PLAN_CODE= String.valueOf(R.string.NGN900000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN1000000")) {
                        AMOUNT=1000000;
                        PLAN_CODE= String.valueOf(R.string.NGN1000000);

                    }
                    if (selectedPlan.equalsIgnoreCase("NGN10000000")) {
                        AMOUNT=10000000;
                        PLAN_CODE= String.valueOf(R.string.NGN10000000);


                    }

                }
                spnFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //frequency = spnFrequency.getSelectedItem().toString();
                        frequency = (String) adapterView.getItemAtPosition(i);
                        if(frequency.equalsIgnoreCase("daily")){
                            noOfDays=months*31;
                            expectedAmount=AMOUNT*noOfDays;

                        }else {
                            noOfDays=months*5;
                            expectedAmount=AMOUNT*noOfDays;

                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                amountDiff=expectedAmount-amount;
                newBalance=amount;
                try {
                    months =Integer.parseInt(edtNoOfMonths.getText().toString().trim());


                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, noOfDays);
                Date resultdate = new Date(c.getTimeInMillis());
                Enddate = sdf.format(resultdate);
                String planName="N"+sOAmount+""+frequency+""+"plan";
                //String plan_code= "SkylightSO"+sOAmount+currentDate;
                String description="My Awajima Standing Order"+currency+sOAmount;
                standingOrder= new StandingOrder(soNo,customerName,expectedAmount);
                //standingOrderNew = new StandingOrder(soNo,cusNames,newBalance);

                Location mCurrentLocation=null;
                if(userProfile !=null){
                    ManagerSurname=userProfile.getProfileLastName();
                    managerFirstName=userProfile.getProfileFirstName();
                    uFirstName= customer.getCusFirstName();
                    profileID=userProfile.getPID();
                    customer=userProfile.getProfileCus();
                    if(customer !=null){
                        customerName=customer.getCusSurname()+""+customer.getCusFirstName();
                        customerPhoneNo=customer.getCusPhoneNumber();
                        customerID=customer.getCusUID();
                        customerEmail=customer.getCusEmailAddress();
                        standingOrderAcct = customer.getCusStandingOrderAcct();
                        customerSurname=customer.getCusSurname();
                        customerFirstName=customer.getCusFirstName();
                        customerName=customer.getCusSurname()+""+customer.getCusFirstName();
                        customerPhoneNo=customer.getCusPhoneNumber();
                        customerID=customer.getCusUID();
                        customerEmail=customer.getCusEmailAddress();
                        //account = customer.getStandingOrderAcct();
                    }
                    customer=userProfile.getProfileCus();

                }
                applicationDb = new DBHelper(SavingsStandingOrder.this);
                sendPostRequest(reference, PLAN_CODE, authorization_code, sOAmount, email,callback_url,phoneNo,expectedAmount,sONowAmount,amtDiff,daysRemaining,customerName,frequency,currency,description,endDate,standingOrder,profileID,planName,customerSurname,customerFirstName);



            }

        });

    }
    public  void sendTwilloMessage(){

        Bundle smsBundle = new Bundle();
        String smsMessage = txMessage;
        smsBundle.putString(PROFILE_PHONE, PHONE_NO);
        smsBundle.putString("USER_PHONE", PHONE_NO);
        smsBundle.putString("smsMessage", smsMessage);
        smsBundle.putString("from", "");
        smsBundle.putString("to", PHONE_NO);
        Intent itemPurchaseIntent = new Intent(SavingsStandingOrder.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


    }
    private void sendPostRequest(String reference, String PLAN_CODE,String authorization_code,double sOAmount,String email,String callback_url,String phoneNo,double expectedAmount,double sONowAmount,double amtDiff,int daysRemaining,String customerName,String frequency,String currency,String description,String endDate,StandingOrder standingOrder,long profileID,String planName,String customerSurname,String customerFirstName) {

        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(SavingsStandingOrder.this);
                progressDialog.setMessage("Creating Plan");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject1 = new JSONObject();
                InputStream inputStream = null;
                String result = "";
                String json1 = "";
                HttpURLConnection urlConnection = null;
                String updateLink=SKYLIGHT_PAYSTACK_PLAN_UPDATE+PLAN_CODE;
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
                    jsonObject.put("interval",frequency);
                    jsonObject.put("amount",finalAmount);
                    jsonObject.put("currency",currency);
                    jsonObject.put("send_invoices","true");
                    jsonObject.put("send_sms","true");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(updateLink);
                    json1 = jsonObject1.toString();
                    StringEntity se = new StringEntity(json1);
                    httpPost.setEntity(se);
                    httpPost.setHeader("Authorization", OurConfig.CREATE_CUSTOMER);
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
                    HttpPost httpPost = new HttpPost(updateLink);
                    String json = "";
                    json = jsonObject.toString();
                    StringEntity se = new StringEntity(json);
                    httpPost.setEntity(se);
                    httpPost.setHeader("Authorization", OurConfig.SKYLIGHT_SECRET_KEY);
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

    private void doPayStackCardPayment(double sONowAmount, StandingOrderAcct standingOrderAcct, DBHelper applicationDb, StandingOrder standingOrder, String endDate, double expectedAmount, Double amtDiff, int daysRemaining, long profileID, String planName, String frequency, String currency) {
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
        standingOrderUpdateStartForResult.launch(new Intent(intent8));
    }

}
