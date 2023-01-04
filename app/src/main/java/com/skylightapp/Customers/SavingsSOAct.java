package com.skylightapp.Customers;

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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.MessageDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.SODAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.PayNowActivity;
import com.skylightapp.QuickTellerPlanAct;
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

import static com.skylightapp.Classes.AppConstants.AWAJIMA_SO_WEBHOOK;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_100K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_100K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_100_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_100k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_10K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_10K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_10M_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_10M_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_10k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_1M_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_1M_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_1k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_200K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_200_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_200_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_200k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_20K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_20K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_20k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_2k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_300K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_300_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_300_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_300k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_30K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_30K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_30k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_400K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_400K_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_400_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_400_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_40K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_40K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_40k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_500K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_500K_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_500_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_500_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_50K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_50K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_50K_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_5k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_600K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_600K_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_600_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_60K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_60K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_60k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_700K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_700K_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_70K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_70K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_70k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_800K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_800_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_800k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_80K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_80K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_80k_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_900K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_900K_W31;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_900_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_90K_M12;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_90K_M6;
import static com.skylightapp.Classes.SavingsLinkUtil.SAVINGS_90k_W31;
import static com.skylightapp.Database.DBHelper.DATABASE_NAME;
import static com.skylightapp.Transactions.OurConfig.PUBLIC_KEY_PAYSTACK;
import static com.skylightapp.Transactions.OurConfig.SKYLIGHT_PAYSTACK_PLAN_UPDATE;

@SuppressWarnings({"ResultOfMethodCallIgnored", "EqualsBetweenInconvertibleTypes"})
public class SavingsSOAct extends AppCompatActivity implements View.OnClickListener{
    public static final String KEY = "SavingsSOAct.KEY";
    AppCompatSpinner freqSpn;
    String callUri =AWAJIMA_SO_WEBHOOK;
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
    double expectedAmount,totalForToday;
    double amountDiff;
    double newBalance;
    String dateInString,frequency,currency;
    private Gson gson,gson1;
    private int AMOUNT;
    private String json,json1;
    private SharedPreferences userPreferences;
    Profile userProfile;
    String txMessage="Savings Awajima,Congratulations! you have successfully started a standing order savings.";
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Transaction transaction1;
    Spinner spnPlans;
    private  ProgressDialog progressDialog;
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;

    private ProfDAO profileDao;

    private TimeLineClassDAO timeLineClassDAO;

    private AppCompatTextView txtResult;
    int soAcctNo;
    double sOAmount,amountCarriedForward,sONowAmount,amtDiff;
    int noOfDays, months,daysRemaining,soAccountNumber;
    String customerName, customerPhoneNo,customerEmail, endDate;
    StandingOrderAcct standingOrderAcct;
    String Enddate,officeBranch,weblink;
    private static final String PREF_NAME = "awajima";
    private WebView mywebView;
    private MaterialCardView cardView;
    private Bundle bundle;
    private DBHelper dbHelper;
    private AppCompatButton proceed_Button;
    com.skylightapp.Classes.Transaction Skylightransaction;
    private Animation translater, translER;
    private Date date;
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
                                applicationDb = new DBHelper(SavingsSOAct.this);
                                String managerFullNames = ManagerSurname + "," + managerFirstName;

                                String timelineSkylight = uSurname + "," + uFirstName + "'s new Package:" + selectedPlan + "plan was initiated by" + managerFullNames + "@" + currentDate;
                                String tittleT = "Standing order package Alert!";
                                String namesT = uSurname + uFirstName;

                                String timelineUser = selectedPlan + "was initiated for you by" + managerFullNames + "@" + currentDate;
                                String timelineUserProfile = selectedPlan + "was initiated by you for" + namesT + "@" + currentDate;
                                String tittle = "Standing order package Alert";
                                transaction_type= com.skylightapp.Classes.Transaction.TRANSACTION_TYPE.STANDING_ORDER;
                                refID= "Awajima/SO"+ random.nextInt((int) (Math.random() * 101) + 191);
                                /*if(userProfile !=null){
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
                                    timeLineClassDAO= new TimeLineClassDAO(SavingsSOAct.this);
                                    standingOrderAcct=customer.getCusStandingOrderAcct();
                                    sodao= new SODAO(SavingsSOAct.this);
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
                                    sodao= new SODAO(SavingsSOAct.this);
                                    sodao.insertStandingOrder(profileID,customerID,soNo,soAcctNo,amountCarriedForward, currentDate,expectedAmount,sONowAmount,amtDiff, months,daysRemaining,endDate,"inProgress");


                                } catch (SQLiteException e) {
                                    System.out.println("Oops!");
                                }*/

                                //DBHelper.insertCustomerLocation(customerID1,cusLatLng);
                                Toast.makeText(SavingsSOAct.this, "Standing order first payment successful", Toast.LENGTH_SHORT).show();

                            }
                            break;
                        case Activity.RESULT_CANCELED:
                            Toast.makeText(SavingsSOAct.this, "Activity canceled", Toast.LENGTH_SHORT).show();
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
    private SQLiteDatabase sqLiteDatabase;
    int currentItem = 0;
    int currentPlan = 0;
    private LinearLayoutCompat planLayout;

    private AdapterView.OnItemSelectedListener freq_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(currentItem == position){
                return; //do nothing
            }
            else {
                frequency = freqSpn.getSelectedItem().toString();

            }
            //currentItem = position;

            if(frequency !=null){
                planLayout.setVisibility(View.VISIBLE);
                dogetPlan(frequency);

            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };
    private AdapterView.OnItemSelectedListener plan_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(currentPlan == position){
                return; //do nothing
            }
            else {
                selectedPlan = spnPlans.getSelectedItem().toString();

            }
            //currentItem = position;

            if(selectedPlan !=null){
                doFinalProcessing(selectedPlan);

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_savings_so);
        setTitle("Automated Savings");
        bundle= new Bundle();
        PaystackSdk.initialize(SavingsSOAct.this);
        PaystackSdk.setPublicKey(PUBLIC_KEY_PAYSTACK);
        standingOrderAcct= new StandingOrderAcct();
        freqSpn = findViewById(R.id.freq_Spinner);
        spnPlans = findViewById(R.id.all_plans);
        translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
         translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        proceed_Button=findViewById(R.id.proceed_Button);
        spnPlans.setOnItemSelectedListener(plan_listener);
        freqSpn.setOnItemSelectedListener(freq_listener);

        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        userProfile= new Profile();
        customer= new Customer();

        date = new Date();
        profileDao= new ProfDAO(this);

        timeLineClassDAO= new TimeLineClassDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        proceed_Button.setOnClickListener(this);

        transaction_type=null;
        Skylightransaction= new com.skylightapp.Classes.Transaction();

        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormatWithZone.format(date);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        refID= "Awajima/"+currentDate+ random.nextInt((int) (Math.random() * 9091) + 19);
        soAccountNumber = random.nextInt((int) (Math.random() * 1044) + 100125);
        transactionID = random.nextInt((int) (Math.random() * 91) + 19);
        soNo = random.nextInt((int) (Math.random() * 75310) + 13570);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gson = new Gson();
        gson1 = new Gson();
        doSpinnerInt();


        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        json1 = userPreferences.getString("LastCustomerUsed", "");
        customer = gson1.fromJson(json, Customer.class);
        applicationDb = new DBHelper(this);
        tranXDAO= new TranXDAO(SavingsSOAct.this);
        timeLineClassDAO= new TimeLineClassDAO(SavingsSOAct.this);
        sodao= new SODAO(SavingsSOAct.this);

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
            //standingOrder= new StandingOrder(soNo,cusNames,soBalance);

        }
        if(userProfile !=null){
            ManagerSurname=userProfile.getProfileLastName();
            managerFirstName=userProfile.getProfileFirstName();
            uFirstName= customer.getCusFirstName();

            profileID=userProfile.getPID();
        }

        machine=userPreferences.getString("Machine","");


        transaction1 = new Transaction();

        PaystackSdk.initialize(SavingsSOAct.this);
        PaystackSdk.setPublicKey(PUBLIC_KEY_PAYSTACK);
        Bundle planBundle = new Bundle();


        dbHelper= new DBHelper(this);

    }

    private void doSpinnerInt() {
        freqSpn = findViewById(R.id.freq_Spinner);
        spnPlans = findViewById(R.id.all_plans);
        txtResult = findViewById(R.id.txtResult4);
        cardView = findViewById(R.id.cd_material);
        planLayout = findViewById(R.id.all_plans_Layout);

        freqSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //frequency = freqSpn.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });



    }
    private void dogetPlan(String frequency) {
        if(frequency !=null){
            if(frequency.equalsIgnoreCase("Weekly")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(SavingsSOAct.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.savings_weekly_plans));

                myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnPlans.setAdapter(myAdaptor);

            }
            if(frequency.equalsIgnoreCase("Monthly")){
                ArrayAdapter<String> myAdaptor = new ArrayAdapter<String>(SavingsSOAct.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.savings_monthly_plans));

                myAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spnPlans.setAdapter(myAdaptor);

            }

        }

    }

    public  void doFinalProcessing(String selectedPlan){
        if(selectedPlan !=null){
            if (selectedPlan.equalsIgnoreCase("SAVINGS N100 for 31 Weeks")) {
                weblink=SAVINGS_100_W31;
                AMOUNT=100;
                expectedAmount=31*100;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN100);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N200 for 31 Weeks")) {
                AMOUNT=200;
                weblink=SAVINGS_200_W31;
                expectedAmount=31*200;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN200);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N300 for 31 Weeks")) {
                AMOUNT=300;
                weblink=SAVINGS_300_W31;
                expectedAmount=31*300;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN300);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N400 for 31 Weeks")) {
                AMOUNT=400;
                weblink=SAVINGS_400_W31;
                expectedAmount=31*400;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN400);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N500 for 31 Weeks")) {
                AMOUNT=500;
                weblink=SAVINGS_500_W31;
                expectedAmount=31*500;
                noOfDays=31;

                PLAN_CODE= String.valueOf(R.string.NGN500);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N1,000 for 31 Weeks")) {
                AMOUNT=1000;
                weblink=SAVINGS_1k_W31;
                expectedAmount=31*1000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN600);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N2,000 for 31 Weeks")) {
                AMOUNT=2000;
                weblink=SAVINGS_2k_W31;
                expectedAmount=31*2000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN700);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N5,000 for 31 Weeks")) {
                AMOUNT=5000;
                weblink=SAVINGS_5k_W31;
                expectedAmount=31*5000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN800);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N10,000 for 31 Weeks")) {
                AMOUNT=10000;
                weblink=SAVINGS_10k_W31;
                expectedAmount=31*10000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN900);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N20,000 for 31 Weeks")) {
                AMOUNT=20000;
                weblink=SAVINGS_20k_W31;
                expectedAmount=31*20000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN1000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N30,000 for 31 Weeks")) {
                AMOUNT=30000;
                weblink=SAVINGS_30k_W31;
                expectedAmount=31*30000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN2000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N40,000 for 31 Weeks")) {
                AMOUNT=40000;
                weblink=SAVINGS_40k_W31;
                expectedAmount=31*40000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN3000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N50,000 for 31 Weeks")) {
                AMOUNT=50000;
                weblink=SAVINGS_50K_W31;
                expectedAmount=31*50000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN4000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N60,000 for 31 Weeks")) {
                AMOUNT=60000;
                weblink=SAVINGS_60k_W31;
                expectedAmount=31*60000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN5000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N70,000 for 31 Weeks")) {
                AMOUNT=70000;
                weblink=SAVINGS_70k_W31;
                expectedAmount=31*70000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN6000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N80,000 for 31 Weeks")) {
                AMOUNT=80000;
                weblink=SAVINGS_80k_W31;
                expectedAmount=31*80000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN7000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N90,000 for 31 Weeks")) {
                AMOUNT=90000;
                weblink=SAVINGS_90k_W31;
                expectedAmount=31*90000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN8000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS 100,000 for 31 Weeks")) {
                AMOUNT=100000;
                weblink=SAVINGS_100k_W31;
                expectedAmount=31*100000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN9000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N200,000 for 31 Weeks")) {
                AMOUNT=200000;
                weblink=SAVINGS_200k_W31;
                noOfDays=31;
                expectedAmount=31*200000;
                PLAN_CODE= String.valueOf(R.string.NGN10000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N300,000 for 31 Weeks")) {
                AMOUNT=300000;
                weblink=SAVINGS_300k_W31;
                expectedAmount=31*300000;
                PLAN_CODE= String.valueOf(R.string.NGN20000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N400,000 for 31 Weeks")) {
                AMOUNT=400000;
                weblink=SAVINGS_400K_W31;
                expectedAmount=31*400000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN30000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N500,000 for 31 Weeks")) {
                AMOUNT=500000;
                weblink=SAVINGS_500K_W31;
                expectedAmount=31*500000;
                noOfDays=31;
                PLAN_CODE.equals(R.string.NGN40000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N600,000 for 31 Weeks")) {
                AMOUNT=600000;
                weblink=SAVINGS_600K_W31;
                expectedAmount=31*600000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN50000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N700,000 for 31 Weeks")) {
                AMOUNT=700000;
                weblink=SAVINGS_700K_W31;
                expectedAmount=31*700000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN60000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N800,000 for 31 Weeks")) {
                AMOUNT=800000;
                weblink=SAVINGS_800k_W31;
                expectedAmount=31*800000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN70000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N900,000 for 31 Weeks")) {
                AMOUNT=900000;
                weblink=SAVINGS_900K_W31;
                expectedAmount=31*900000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN80000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N1,000,000 for 31 Weeks")) {
                AMOUNT=1000000;
                weblink=SAVINGS_100_W31;
                expectedAmount=31*1000000;
                noOfDays=31;
                PLAN_CODE= String.valueOf(R.string.NGN90000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N10,000,000 for 12 months")) {
                AMOUNT=10000000;
                weblink=SAVINGS_10M_M12;
                expectedAmount=12*10000000;
                noOfDays=12;
                PLAN_CODE= String.valueOf(R.string.NGN100000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N1,000,000 for 12 months")) {
                AMOUNT=1000000;
                weblink=SAVINGS_1M_M12;
                expectedAmount=12*1000000;
                noOfDays=12;
                PLAN_CODE= String.valueOf(R.string.NGN200000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N900,000 for 12 months")) {
                AMOUNT=900000;
                weblink=SAVINGS_900K_M12;
                noOfDays=12;
                expectedAmount=12*900000;
                PLAN_CODE= String.valueOf(R.string.NGN300000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N800,000 for 12 months")) {
                AMOUNT=800000;
                weblink=SAVINGS_800K_M12;
                noOfDays=12;
                expectedAmount=12*800000;
                PLAN_CODE= String.valueOf(R.string.NGN400000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N700,000 for 12 months")) {
                AMOUNT=700000;
                weblink=SAVINGS_700K_M12;
                noOfDays=12;
                expectedAmount=12*700000;
                PLAN_CODE= String.valueOf(R.string.NGN500000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N600,000 for 12 months")) {
                AMOUNT=600000;
                weblink=SAVINGS_600K_M12;
                noOfDays=12;
                expectedAmount=12*600000;
                PLAN_CODE= String.valueOf(R.string.NGN600000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N500,000 for 12 months")) {
                AMOUNT=500000;
                weblink=SAVINGS_500K_M12;
                noOfDays=12;
                expectedAmount=12*500000;
                PLAN_CODE= String.valueOf(R.string.NGN700000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N400,000 for 12 months")) {
                AMOUNT=400000;
                weblink=SAVINGS_400K_M12;
                noOfDays=12;
                expectedAmount=12*400000;
                PLAN_CODE= String.valueOf(R.string.NGN800000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N300,000 for 12 months")) {
                AMOUNT=300000;
                weblink=SAVINGS_300K_M12;
                noOfDays=12;
                expectedAmount=12*300000;
                PLAN_CODE= String.valueOf(R.string.NGN900000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N200,000 for 12 months")) {
                AMOUNT=200000;
                weblink=SAVINGS_200K_M12;
                noOfDays=12;
                expectedAmount=12*200000;
                PLAN_CODE= String.valueOf(R.string.NGN1000000);

            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N100,000 for 12 months")) {
                AMOUNT=100000;
                weblink=SAVINGS_100K_M12;
                noOfDays=12;
                expectedAmount=12*100000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }

            if (selectedPlan.equalsIgnoreCase("SAVINGS N90,000 for 12 months")) {
                AMOUNT=90000;
                weblink=SAVINGS_90K_M12;
                noOfDays=12;
                expectedAmount=12*90000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }

            if (selectedPlan.equalsIgnoreCase("SAVINGS N80,000 for 12 months")) {
                AMOUNT=80000;
                weblink=SAVINGS_80K_M12;
                noOfDays=12;
                expectedAmount=12*80000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }

            if (selectedPlan.equalsIgnoreCase("SAVINGS N70,000 for 12 months")) {
                AMOUNT=70000;
                weblink=SAVINGS_70K_M12;
                noOfDays=12;
                expectedAmount=12*70000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N60,000 for 12 months")) {
                AMOUNT=60000;
                weblink=SAVINGS_60K_M12;
                noOfDays=12;
                expectedAmount=12*60000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N50,000 for 12 months")) {
                AMOUNT=50000;
                weblink=SAVINGS_50K_M12;
                noOfDays=12;
                expectedAmount=12*50000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N40,000 for 12 months")) {
                AMOUNT=40000;
                weblink=SAVINGS_40K_M12;
                noOfDays=12;
                expectedAmount=12*40000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N30,000 for 12 months")) {
                AMOUNT=30000;
                weblink=SAVINGS_30K_M12;
                noOfDays=12;
                expectedAmount=12*30000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N20,000 for 12 months")) {
                AMOUNT=20000;
                weblink=SAVINGS_20K_M12;
                noOfDays=12;
                expectedAmount=12*20000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N10,000 for 12 months")) {
                AMOUNT=10000;
                weblink=SAVINGS_10K_M12;
                noOfDays=12;
                expectedAmount=12*10000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N10,000,000 for 6 months")) {
                AMOUNT=10000000;
                weblink=SAVINGS_10M_M6;
                noOfDays=6;
                expectedAmount=6*10000000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }

            if (selectedPlan.equalsIgnoreCase("SAVINGS N1,000,000 for 6 months")) {
                AMOUNT=1000000;
                weblink=SAVINGS_1M_M6;
                noOfDays=6;
                expectedAmount=6*1000000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N900,000 for 6 months")) {
                AMOUNT=900000;
                weblink=SAVINGS_900_M6;
                noOfDays=6;
                expectedAmount=6*900000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N800,000 for 6 months")) {
                AMOUNT=800000;
                weblink=SAVINGS_800_M6;
                noOfDays=6;
                expectedAmount=6*800000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N700,000 for 6 months")) {
                AMOUNT=700000;
                //weblink=SAVINGS_700_M6;
                noOfDays=6;
                expectedAmount=6*700000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N600,000 for 6 months")) {
                AMOUNT=600000;
                weblink=SAVINGS_600_M6;
                noOfDays=6;
                expectedAmount=6*600000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N500,000 for 6 months")) {
                AMOUNT=500000;
                weblink=SAVINGS_500_M6;
                noOfDays=6;
                expectedAmount=6*500000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N400,000 for 6 months")) {
                AMOUNT=400000;
                weblink=SAVINGS_400_M6;
                noOfDays=6;
                expectedAmount=6*400000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N300,000 for 6 months")) {
                AMOUNT=300000;
                weblink=SAVINGS_300_M6;
                noOfDays=6;
                expectedAmount=6*300000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N200,000 for 6 months")) {
                AMOUNT=200000;
                weblink=SAVINGS_200_M6;
                noOfDays=6;
                expectedAmount=6*200000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N100,000 for 6 months")) {
                AMOUNT=100000;
                weblink=SAVINGS_100K_M6;
                noOfDays=6;
                expectedAmount=6*100000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N90,000 for 6 months")) {
                AMOUNT=90000;
                weblink=SAVINGS_90K_M6;
                noOfDays=6;
                expectedAmount=6*90000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N80,000 for 6 months")) {
                AMOUNT=80000;
                weblink=SAVINGS_80K_M6;
                noOfDays=6;
                expectedAmount=6*80000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N70,000 for 6 months")) {
                AMOUNT=70000;
                weblink=SAVINGS_70K_M6;
                noOfDays=6;
                expectedAmount=6*70000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N60,000 for 6 months")) {
                AMOUNT=60000;
                weblink=SAVINGS_60K_M6;
                noOfDays=6;
                expectedAmount=6*60000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N50,000 for 6 months")) {
                AMOUNT=50000;
                weblink=SAVINGS_50K_M6;
                noOfDays=6;
                expectedAmount=6*50000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N40,000 for 6 months")) {
                AMOUNT=40000;
                weblink=SAVINGS_40K_M6;
                noOfDays=6;
                expectedAmount=6*40000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N30,000 for 6 months")) {
                AMOUNT=30000;
                weblink=SAVINGS_30K_M6;
                noOfDays=6;
                expectedAmount=6*30000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N20,000 for 6 months")) {
                AMOUNT=20000;
                weblink=SAVINGS_20K_M6;
                noOfDays=6;
                expectedAmount=6*20000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            if (selectedPlan.equalsIgnoreCase("SAVINGS N10,000 for 6 months")) {
                AMOUNT=10000;
                weblink=SAVINGS_10K_M6;
                noOfDays=6;
                expectedAmount=6*10000;
                PLAN_CODE= String.valueOf(R.string.NGN10000000);


            }
            /*if(selectedPlan !=null){
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

                }*/


        }
        //sendPostRequest(reference, PLAN_CODE, authorization_code, sOAmount, email,callback_url,phoneNo,expectedAmount,sONowAmount,amtDiff,daysRemaining,customerName,frequency,currency,description,endDate,standingOrder,profileID,planName,customerSurname,customerFirstName);




    }


    @Override
    public void onClick(View view) {
        selectedPlan = spnPlans.getSelectedItem().toString();
        frequency = freqSpn.getSelectedItem().toString();
        translater = AnimationUtils.loadAnimation(this, R.anim.bounce);
        translER = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormatWithZone.format(date);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        refID= "Awajima/"+currentDate+ random.nextInt((int) (Math.random() * 9091) + 19);
        soAccountNumber = random.nextInt((int) (Math.random() * 1044) + 100125);
        transactionID = random.nextInt((int) (Math.random() * 91) + 19);
        soNo = random.nextInt((int) (Math.random() * 75310) + 13570);
        int id = view.getId();
        view.startAnimation(translater);
        if (id == R.id.proceed_Button) {
            sOAmount=AMOUNT;
            String soDate = dateFormatWithZone.format(date);
            userProfile= new Profile();
            userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            //userPreferences = PreferenceManager.getDefaultSharedPreferences(SavingsSOAct.this);
            json = userPreferences.getString("LastProfileUsed", "");
            userProfile = gson.fromJson(json, Profile.class);
            //processQuickTeller(weblink);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, noOfDays);
            Date resultdate = new Date(c.getTimeInMillis());
            Enddate = sdf.format(resultdate);
            String planName="N"+sOAmount+""+frequency+""+"plan";
            //String plan_code= "SkylightSO"+sOAmount+currentDate;
            String description="My Awajima Standing Order"+""+"NGN"+sOAmount;

            String managerFullNames = ManagerSurname + "," + managerFirstName;

            String timelineAwajima = uSurname + "," + uFirstName + "'s new Package:" + selectedPlan + "plan was initiated by" + managerFullNames + "@" + currentDate;
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
            if(customer !=null){
                customerPhoneNo=customer.getCusPhoneNumber();
                customerID=customer.getCusUID();
                customerEmail=customer.getCusEmailAddress();
                standingOrderAcct = customer.getCusStandingOrderAcct();
                customerSurname=customer.getCusSurname();
                customerFirstName=customer.getCusFirstName();
                customerName=customer.getCusSurname()+""+customer.getCusFirstName();

            }
            Skylightransaction= new com.skylightapp.Classes.Transaction(transactionID, soAcctNo, soDate, 100001, soAcctNo, "Awajima", namesT, sONowAmount, transaction_type, "QuickTeller",officeBranch, "", "", "Pending");

            if(customer !=null){

                //customer.addCusTransactions(sONowAmount);
                //customer.setCusStandingOrder(standingOrder);
                customer.addCusTimeLine(tittleT,timelineUser);
                //customer.setCusCurrency(currency);
                //customer.addCusStandingOrder(transactionID,soNo,sONowAmount,currentDate,expectedAmount, AMOUNT,amountDiff,"just started", endDate);


            }
            if(userProfile !=null){
                officeBranch=userProfile.getProfOfficeName();
                userProfile.addPTimeLine(tittle,timelineUserProfile);
                userProfile.addPTransaction(transactionID,uSurname,uFirstName,phoneNo,sONowAmount,PHONE_NO,"Standing order payment",soDate,"Standing Order");

            }
            if(standingOrderAcct !=null){
                soAccountNumber=standingOrderAcct.getSoAcctNo();

            }

            if(standingOrderAcct !=null){
                soAcctNo=standingOrderAcct.getSoAcctNo();

            }
            if(dbHelper !=null){
                try {

                    if(sqLiteDatabase !=null){
                        sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                    }
                    if(tranXDAO !=null){
                        try {
                            tranXDAO.saveNewTransaction(profileID, customerID,Skylightransaction, soAcctNo, "Awajima", namesT,transaction_type,sONowAmount, transactionID, officeBranch, soDate);
                        } catch (NullPointerException e) {
                            System.out.println("Oops!");
                        }
                    }
                    if(timeLineClassDAO !=null){
                        try {
                            timeLineClassDAO.insertTimeLine(tittle, timelineAwajima, soDate, null);

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }

            }


            Location mCurrentLocation=null;
            if(userProfile !=null){
                ManagerSurname=userProfile.getProfileLastName();
                managerFirstName=userProfile.getProfileFirstName();
                uFirstName= customer.getCusFirstName();
                profileID=userProfile.getPID();
                customer=userProfile.getProfileCus();


                standingOrder= new StandingOrder(soNo,profileID,customerID,customerName,sOAmount,selectedPlan,frequency,soDate);

                if(standingOrder !=null){
                    standingOrder.setSoAcctNo(soAccountNumber);
                    standingOrder.setStandingOrderAcct(standingOrderAcct);
                    standingOrder.setCustomerID(customerID);
                    standingOrder.setProfileID(profileID);
                    standingOrder.setSo_frequency(frequency);
                    standingOrder.setSo_Req_Platform("QuickTeller");
                    standingOrder.setSo_plan(selectedPlan);
                    standingOrder.setSo_TotalDays(noOfDays);
                    standingOrder.setSoExpectedAmount(expectedAmount);
                    standingOrder.setSo_Customer(customer);
                    standingOrder.setSoStatus("pending");
                    standingOrder.setSo_Names(customerName);
                    standingOrder.setSo_request_date(soDate);
                }
                if(customer !=null){
                    customer.addCusStandingOrder(standingOrder);
                }

                if(dbHelper !=null){
                    try {

                        if(sqLiteDatabase !=null){
                            sqLiteDatabase = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                        }
                    } catch (SQLiteException e) {
                        e.printStackTrace();
                    }
                    if(sodao !=null){
                        try {
                            sodao.insertStandingOrder(standingOrder,customerID);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }


                    }


                }
                planName=this.selectedPlan;

                bundle.putParcelable("Customer",customer);
                bundle.putParcelable("Profile",userProfile);
                bundle.putString("weblink",this.weblink);
                bundle.putDouble("Amount",this.AMOUNT);
                bundle.putString("weblink",this.weblink);
                bundle.putString("Date",soDate);
                bundle.putInt("noOfDays",this.noOfDays);
                bundle.putDouble("expectedAmount",this.expectedAmount);
                bundle.putParcelable("StandingOrder",standingOrder);
                bundle.putString("EndDate", endDate);
                bundle.putLong(PROFILE_ID, profileID);
                bundle.putDouble("AmountRemaining", amtDiff);
                bundle.putDouble("DaysRemaining", daysRemaining);
                bundle.putDouble("GrandTotal", expectedAmount);
                bundle.putString("PlanName", planName);
                bundle.putDouble("Total", sONowAmount);
                bundle.putString("Currency", currency);
                bundle.putString("Frequency", frequency);
                bundle.putParcelable("StandingOrderAcct", standingOrderAcct);
                bundle.putParcelable("DBHelper", (Parcelable) applicationDb);
                Intent itemPurchaseIntent = new Intent(SavingsSOAct.this, QuickTellerPlanAct.class);
                itemPurchaseIntent.putExtras(bundle);
                itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                standingOrderUpdateStartForResult.launch(new Intent(itemPurchaseIntent));
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);

            }
        }
        //processQuickTeller(weblink);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
    public  void sendTwilloMessage(){

        Bundle smsBundle = new Bundle();
        String smsMessage = txMessage;
        smsBundle.putString(PROFILE_PHONE, PHONE_NO);
        smsBundle.putString("USER_PHONE", PHONE_NO);
        smsBundle.putString("smsMessage", smsMessage);
        smsBundle.putString("from", "");
        smsBundle.putString("to", PHONE_NO);
        Intent itemPurchaseIntent = new Intent(SavingsSOAct.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


    }
    private void sendPostRequest(String reference, String PLAN_CODE,String authorization_code,double sOAmount,String email,String callback_url,String phoneNo,double expectedAmount,double sONowAmount,double amtDiff,int daysRemaining,String customerName,String frequency,String currency,String description,String endDate,StandingOrder standingOrder,long profileID,String planName,String customerSurname,String customerFirstName) {

        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(SavingsSOAct.this);
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
                    httpPost.setHeader("Authorization", OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);
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

                        //doPayStackCardPayment(sONowAmount,standingOrderAcct,applicationDb,standingOrder,endDate,expectedAmount,amtDiff,daysRemaining,profileID,planName,frequency,currency);

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

    private void doPayStackCardPayment33(double sONowAmount, StandingOrderAcct standingOrderAcct, DBHelper applicationDb, StandingOrder standingOrder, String endDate, double expectedAmount, Double amtDiff, int daysRemaining, long profileID, String planName, String frequency, String currency) {
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
    @Override
    public void onPause() {
        super.onPause();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }

    @Override
    public void onStop() {
        super.onStop();
        //overridePendingTransition(R.anim.move_left_in, R.anim.move_right_out);
    }
    @Override
    protected void onStart() {
        super.onStart();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        overridePendingTransition(R.anim.base_slide_left_out, R.anim.bounce);

    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        @SuppressLint("MissingPermission") NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
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

}
