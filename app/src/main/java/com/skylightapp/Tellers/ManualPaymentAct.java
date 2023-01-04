package com.skylightapp.Tellers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.BuildConfig;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.PrefManager;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.AppCash;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.TellerReport;
import com.skylightapp.Classes.Utils;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.PaymentDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.R;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static com.skylightapp.BuildConfig.TWILLO_NO;
import static com.skylightapp.BuildConfig.T_ACCT_SID;
import static com.skylightapp.BuildConfig.T_AUTH_TOKEN;


public class ManualPaymentAct extends AppCompatActivity {
    private static final String TAG = ManualPaymentAct.class.getSimpleName();
    public final static int KEY_EXTRA_REPORT_ID = 124;
    private String TWILLO_ACCOUNT_SID= T_ACCT_SID;
    private String TWILLO_AUTH_TOKEN= T_AUTH_TOKEN;
    private String TWILLO_PHONE_NO= TWILLO_NO;
    String from = TWILLO_NO;
    String password= BuildConfig.SKYLIGHT_EMAIL_PASSWORD;
    Date currentDate;

    private Profile tellerProfile;
    private CustomerManager customerManager;
    private Context context;
    private SharedPreferences userPreferences;
    private PrefManager prefManager;
    private Gson gson,gson1;
    private String json,json1;
    private  Bundle messageBundle;
    private  int tellerProfileID,customerID,accountID,standingOrderAcctID;
    private  String purpose;
    private Spinner spnCustomers;
    private  String selectedPurpose,sendee;
    private Customer customer;
    DatePicker picker;
    TextView txtAmtToWithdraw;
    private  Spinner spnNoOfClients,spnSavingDays,spnPackages;
    private AppCompatButton btnConfirmation;
    EditText edtAmounts;
    private double amountEntered;
    private  ArrayList<TellerReport> tellerReports;
    private int tellerID;
    private int noOfSavings;
    DBHelper applicationDb;
    int officeBranchID;
    int selectedNoIndex,selectedCustomerIndex,selectedPackageIndex;
    AppCompatButton btnGetPayment;
    private Account account;
    private StandingOrderAcct standingOrderAcct;
    private ArrayList<Customer> customersN;
    private ArrayAdapter<Customer> customerArrayAdapter;
    private ArrayList<MarketBizPackage> marketBizPackageArrayList;
    private ArrayAdapter<MarketBizPackage> skyLightPackageArrayAdapter;

    private ArrayList<Payment> paymentArrayList;
    private ArrayAdapter<Payment> paymentArrayAdapter;
    private Bundle bundle;
    LinearLayoutCompat spnLayout;
    private MarketBizPackage marketBizPackage;
    private Payment payment;
    private  double packageBalance,packageAmount,tellerBalance,totalToWithdraw,noOfDaysDouble,newAmountContributedSoFar,newSOBalance,amountContributedSoFar,accountBalance,soAccountBalance;
    Date date;
    Random ran;
    SecureRandom random;
    private int paymentID;
    private long paymentCode;
    private double newBalance;
    Payment.PAYMENT_TYPE payment_type;
    AppCash appCash;
    private static final String PREF_NAME = "skylight";
    String SharedPrefUserPassword;
    String noOfDays;
    String status;
    String stringNoOfSavings;
    String office;
    String customerPhoneNo;
    String officeBranch;
    String dateOfReport;
    String nameOfCustomer;
    String cmFirstName;
    String cmLastName;
    String cmName;
    String SharedPrefUserMachine;
    String phoneNo;
    String SharedPrefUserName;
    int SharedPrefProfileID;
    String adminName;
    private  int noOfDaysInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_manual_payment);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        gson1 = new Gson();
        random = new SecureRandom();
        tellerProfile =new Profile();
        account= new Account();
        appCash = new AppCash();
        payment= new Payment();
        marketBizPackage = new MarketBizPackage();
        customerManager=new CustomerManager();
        standingOrderAcct= new StandingOrderAcct();
        currentDate= new Date();
        bundle=new Bundle();
        tellerReports=new ArrayList<>();
        applicationDb = new DBHelper(this);
        SharedPrefUserName=userPreferences.getString("PROFILE_USERNAME", "");
        SharedPrefUserPassword=userPreferences.getString("PROFILE_PASSWORD", "");
        SharedPrefUserMachine=userPreferences.getString("machine", "");
        SharedPrefProfileID=userPreferences.getInt("PROFILE_ID", 0);
        json = userPreferences.getString("LastProfileUsed", "");
        tellerProfile = gson.fromJson(json, Profile.class);
        json1 = userPreferences.getString("LastTellerProfileUsed", "");
        customerManager = gson1.fromJson(json, CustomerManager.class);
        spnCustomers = findViewById(R.id.spinnerCus);
        spnLayout = findViewById(R.id.layoutSelectCustomer);
        spnSavingDays = findViewById(R.id.number_day1245);
        spnPackages = findViewById(R.id.spinnerCusPackageSelected);
        txtAmtToWithdraw = findViewById(R.id.withdrawalAmt);
        spnSavingDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                noOfDays = (String) parent.getSelectedItem();
                if(noOfDays !=null){
                    try {
                        noOfDaysInt= Integer.parseInt((noOfDays));

                    } catch (NumberFormatException e) {
                        System.out.println("Oops!");
                    }

                }

                Toast.makeText(ManualPaymentAct.this, "Number of days: "  + noOfDays, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bundle=getIntent().getExtras();
        if(bundle !=null){
            customer=bundle.getParcelable("Customer");
            spnLayout.setVisibility(View.GONE);
        }
        else {
            if (tellerProfile != null) {
                CusDAO cusDAO= new CusDAO(this);
                customersN = cusDAO.getAllCustomers11();
                customerArrayAdapter = new ArrayAdapter<>(ManualPaymentAct.this, android.R.layout.simple_spinner_item, customersN);
                customerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCustomers.setAdapter(customerArrayAdapter);
                spnCustomers.setSelection(0);
                selectedCustomerIndex = spnCustomers.getSelectedItemPosition();
                try {
                    customer = customersN.get(selectedCustomerIndex);
                    try {
                        if (customer != null) {
                            account=customer.getCusAccount();
                            standingOrderAcct=customer.getCusStandingOrderAcct();

                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Oops!");
                }


            }
        }
        if (customer != null) {
            account=customer.getCusAccount();
            standingOrderAcct=customer.getCusStandingOrderAcct();

        }


        if(account !=null){
            tellerBalance=account.getAccountBalance();

        }

        if (customer != null) {
            marketBizPackageArrayList = customer.getCusSkyLightPackages();
            skyLightPackageArrayAdapter = new ArrayAdapter<>(ManualPaymentAct.this, android.R.layout.simple_spinner_item, marketBizPackageArrayList);
            skyLightPackageArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnPackages.setAdapter(skyLightPackageArrayAdapter);
            spnPackages.setSelection(0);
            if(spnPackages !=null){
                selectedPackageIndex = spnPackages.getSelectedItemPosition();


            }
            if(marketBizPackageArrayList.size()>0){
                marketBizPackage = marketBizPackageArrayList.get(selectedPackageIndex);


            }
            if (marketBizPackage != null) {
                packageAmount= marketBizPackage.getPackageDailyAmount();
                packageBalance= marketBizPackage.getPackageBalance();
            }


        }
        totalToWithdraw=packageAmount*noOfDaysDouble;
        txtAmtToWithdraw.setText("Total to Withdraw: N"+totalToWithdraw);
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        if(customerManager !=null){
            cmFirstName =customerManager.getTFirstName();
            cmLastName =customerManager.getTSurname();
            cmName=cmLastName+""+cmFirstName;
            tellerID=customerManager.getTID();
        }


        btnGetPayment = findViewById(R.id.confirmWithwalSub);
        btnGetPayment.setOnClickListener(this::withdrawForCus);
        btnGetPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentID = random.nextInt((int) (Math.random() * 1059) + 30110);
                paymentCode = random.nextInt((int) (Math.random() * 2045) + 10600);
                doManual(noOfDaysInt,customer,account,standingOrderAcct,tellerProfile,packageAmount,packageBalance,totalToWithdraw,noOfDaysDouble, marketBizPackage,payment,paymentCode,paymentID);

            }
        });
    }

    private void doManual(int noOfDays, Customer customer, Account account, StandingOrderAcct standingOrderAcct, Profile tellerProfile, double packageAmount, double packageBalance, double totalToWithdraw, double noOfDaysDouble, MarketBizPackage marketBizPackage, Payment payment, long paymentCode, int paymentID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        accountBalance=0.00;

        if(tellerProfile !=null){
            tellerProfileID=tellerProfile.getPID();
            office=tellerProfile.getProfOfficeName();
            cmName=tellerProfile.getProfileLastName()+""+tellerProfile.getProfileFirstName();

        }
        if (customer != null) {
            account=customer.getCusAccount();
            standingOrderAcct=customer.getCusStandingOrderAcct();
            customerID=customer.getCusUID();
            nameOfCustomer=customer.getCusSurname()+""+customer.getCusFirstName();
            customerPhoneNo=customer.getCusPhoneNumber();

        }
        if(account !=null){
            accountID=account.getAwajimaAcctNo();
            accountBalance=account.getAccountBalance();
            newBalance=account.getAccountBalance()-totalToWithdraw;
        }
        try {
            if(standingOrderAcct !=null){
                soAccountBalance=standingOrderAcct.getSoAcctBalance();

                standingOrderAcctID=standingOrderAcct.getSoAcctNo();
                newSOBalance=standingOrderAcct.getSoAcctBalance()-totalToWithdraw;

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        paymentArrayList=new ArrayList<Payment>();
        //String status = "Manual withdrawal in progress";
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = mdformat.format(calendar.getTime());
        PaymentDAO paymentDAO= new PaymentDAO(this);

        try {
            date = mdformat.parse(todayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        paymentArrayList=paymentDAO.getALLPaymentsTellerToday(tellerProfileID,todayDate);
        status="";
        if(marketBizPackage !=null){
            amountContributedSoFar= marketBizPackage.getPackageAmount_collected();

        }

        newAmountContributedSoFar=amountContributedSoFar-totalToWithdraw;
        String type= "CASH_WITHDRAWAL";

        String timelineTittle1 = "Manual withdrawal alert";
        String timelineDetails1 = "NGN" + totalToWithdraw + "was withdrawn for" + nameOfCustomer+"by"+cmName;
        String timelineDetails2 = "NGN" + totalToWithdraw +""+ "was withdrawn for" + nameOfCustomer +"by"+""+cmName + " on" + Utils.setLastSeenTime(todayDate);

        builder.setTitle("Choose Account Type to withdraw from");
        builder.setIcon(R.drawable.transaction);
        String finalStatus = status;
        Account finalAccount = account;
        builder.setItems(new CharSequence[]
                        {"Ewallet Savings", "Standing Order Savings"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                Toast.makeText(ManualPaymentAct.this, "Ewallet Acct selected", Toast.LENGTH_SHORT).show();
                                String acctType="WALLET";

                                payment_type= Payment.PAYMENT_TYPE.CASH_WITHDRAWAL;
                                try {
                                    for (int i = 0; i < paymentArrayList.size(); i++) {
                                        if (String.valueOf(totalToWithdraw).equals(String.valueOf(paymentArrayList.get(i).getPaymentAmtToWithdraw())) &&
                                                String.valueOf(todayDate).equals(String.valueOf(paymentArrayList.get(i).getPaymentDate()))) {
                                            Toast.makeText(ManualPaymentAct.this, "a very similar Manual withdrawal already exist!", Toast.LENGTH_LONG).show();

                                        }
                                        if (String.valueOf(noOfDays).isEmpty()) {
                                            Toast.makeText(ManualPaymentAct.this, "The Number of Days can not be Empty", Toast.LENGTH_SHORT).show();

                                        }
                                        if (totalToWithdraw>accountBalance) {
                                            Toast.makeText(ManualPaymentAct.this, "Insufficient funds", Toast.LENGTH_SHORT).show();

                                        }
                                        else {
                                            if (customer != null) {
                                                customer.addCusPayment(type, totalToWithdraw, date, paymentCode, acctType, office, finalStatus);
                                                customer.addCusTimeLine(timelineTittle1, timelineDetails2);
                                                customer.getCusAccount().setAccountBalance(newBalance);

                                            }
                                            if (tellerProfile != null) {
                                                tellerProfile.addPTimeLine(timelineTittle1, timelineDetails2);
                                                tellerProfile.addPPayment(type, totalToWithdraw, date, paymentCode, acctType, office, finalStatus);
                                            }
                                            PaymentDAO paymentDAO= new PaymentDAO(ManualPaymentAct.this);
                                            TimeLineClassDAO timeLineClassDAO= new TimeLineClassDAO(ManualPaymentAct.this);

                                            ManualPaymentAct.this.payment = new Payment(tellerProfileID,customerID,type, totalToWithdraw, date,"",paymentCode,acctType,office,"");
                                            marketBizPackage.setPackageBalance(newBalance);
                                            finalAccount.setAccountBalance(newBalance);
                                            marketBizPackage.setPackageAmount_collected(newAmountContributedSoFar);

                                            try {

                                                timeLineClassDAO.insertTimeLine(timelineTittle1, timelineDetails1, Utils.setLastSeenTime(todayDate), null);
                                                paymentDAO.insertPayment(paymentID,tellerProfileID,customerID,office,todayDate,payment_type, totalToWithdraw,paymentCode,accountID,acctType,"","","");

                                            } catch (SQLiteException e) {
                                                System.out.println("Oops!");
                                            }
                                            startNotification();

                                            String paymentMessage = "Awajima! your manual withdrawal :" + "was successful";
                                            Twilio.init(TWILLO_ACCOUNT_SID, TWILLO_AUTH_TOKEN);
                                            Message message = Message.creator(
                                                    new com.twilio.type.PhoneNumber(customerPhoneNo),
                                                    new com.twilio.type.PhoneNumber("234" + customerPhoneNo),
                                                    paymentMessage).create();

                                        }


                                    }

                                } catch (NullPointerException e) {
                                    System.out.println("Oops!");
                                }

                            case 1:
                                acctType="STANDING_ORDER";

                                //Intent soIntent = new Intent(ManualPaymentAct.this, BorrowFromSOAcct.class);
                                Toast.makeText(ManualPaymentAct.this, "Standing Order Acct would used", Toast.LENGTH_SHORT).show();
                                try {
                                    for (int i = 0; i < paymentArrayList.size(); i++) {
                                        if (String.valueOf(totalToWithdraw).equals(String.valueOf(paymentArrayList.get(i).getPaymentAmtToWithdraw())) &&
                                                String.valueOf(todayDate).equals(String.valueOf(paymentArrayList.get(i).getPaymentDate()))) {
                                            Toast.makeText(ManualPaymentAct.this, "a very similar Manual withdrawal already exist!", Toast.LENGTH_LONG).show();

                                        }
                                        if (String.valueOf(noOfDays).isEmpty()) {
                                            Toast.makeText(ManualPaymentAct.this, "The Number of Days can not be Empty", Toast.LENGTH_SHORT).show();

                                        }
                                        if (totalToWithdraw>soAccountBalance) {
                                            Toast.makeText(ManualPaymentAct.this, "Insufficient funds", Toast.LENGTH_SHORT).show();

                                        }
                                        else {
                                            if (customer != null) {
                                                customer.addCusPayment(type, totalToWithdraw, date, paymentCode, acctType, office, finalStatus);
                                                customer.addCusTimeLine(timelineTittle1, timelineDetails2);
                                                customer.getCusStandingOrderAcct().setSoAcctBalance(newSOBalance);

                                            }
                                            if (tellerProfile != null) {
                                                tellerProfile.addPTimeLine(timelineTittle1, timelineDetails2);
                                                tellerProfile.addPPayment(type, totalToWithdraw, date, paymentCode, acctType, office, finalStatus);
                                            }
                                            PaymentDAO paymentDAO= new PaymentDAO(ManualPaymentAct.this);
                                            TimeLineClassDAO timeLineClassDAO= new TimeLineClassDAO(ManualPaymentAct.this);

                                            ManualPaymentAct.this.payment = new Payment(tellerProfileID,customerID,type, totalToWithdraw, date,"",paymentCode,acctType,office,"");
                                            marketBizPackage.setPackageBalance(newBalance);
                                            marketBizPackage.setPackageAmount_collected(newAmountContributedSoFar);

                                            try {

                                                timeLineClassDAO.insertTimeLine(timelineTittle1, timelineDetails1, Utils.setLastSeenTime(todayDate), null);
                                                paymentDAO.insertPayment(paymentID,tellerProfileID,customerID,office,todayDate,payment_type, totalToWithdraw,paymentCode,accountID,acctType,"","","");

                                            } catch (SQLiteException e) {
                                                System.out.println("Oops!");
                                            }
                                            startNotification();

                                            String paymentMessage = "Awajima! your manual withdrawal :" + "was successful";
                                            Twilio.init(TWILLO_ACCOUNT_SID, TWILLO_AUTH_TOKEN);
                                            Message message = Message.creator(
                                                    new com.twilio.type.PhoneNumber(customerPhoneNo),
                                                    new com.twilio.type.PhoneNumber("234" + customerPhoneNo),
                                                    paymentMessage).create();

                                        }


                                    }

                                } catch (NullPointerException e) {
                                    System.out.println("Oops!");
                                }


                        }
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        builder.create().show();



    }

    private void startNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Customer withdrawal Alert")
                        .setContentText("A Teller just withdrawn money for a Customer");

        Intent notificationIntent = new Intent(this, TellerHomeChoices.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(TellerHomeChoices.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void withdrawForCus(View view) {
    }
    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
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