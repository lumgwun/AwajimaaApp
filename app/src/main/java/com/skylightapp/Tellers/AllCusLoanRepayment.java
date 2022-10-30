package com.skylightapp.Tellers;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.klinker.android.send_message.BroadcastUtils;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.CusDAO;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.LoanDAO;
import com.skylightapp.Database.ProfDAO;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.FlutterWavePayments.FluPaywithBank;
import com.skylightapp.LoginDirAct;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class AllCusLoanRepayment extends AppCompatActivity {
    SharedPreferences userPreferences;
    AppCompatEditText phone_number, email_address, firstName, surname1, userName, password, address_2;
    protected DatePickerDialog datePickerDialog;
    Random ran = new Random();
    SecureRandom random = new SecureRandom();
    //int virtualAccountNumber = ran.nextInt((int) (Math.random() * 900000) + 100000);
    //long customerID1 = random.nextInt((int) (Math.random() * 900) + 1001);
    //long profileID1 = random.nextInt((int) (Math.random() * 900) + 100);
    Context context;
    String dateOfBirth;
    PreferenceManager preferenceManager;
    private Bundle bundle;
    String uPhoneNumber;
    long profileID;
    //private FirebaseAuth mAuth;
    // private ProgressBar loadingPB;
    String userSurname;
    String userFirstName, uPassword;
    String userPhoneNumber;
    String userEmail;
    String userAddress;
    String profileUserName;
    String mLastUpdateTime, selectedGender, selectedOffice, selectedState;

    AppCompatButton dob1;
    AppCompatRadioButton customerManager;
    Customer customer;
    String uFirstName, uEmail, uSurname, uAddress, uUserName;
    //Random ran = new Random();
    //SecureRandom random =new SecureRandom();
    int virtualAccountNumber = ran.nextInt((int) (Math.random() * 900000) + 100000);
    long customerID1 = random.nextInt((int) (Math.random() * 900) + 1001);
    long profileID1 = random.nextInt((int) (Math.random() * 900) + 100);

    AppCompatSpinner state, office, gender;
    DBHelper dbHelper;
    public final static String MODE_KEY = "key_mode";
    public final static String DATE_KEY = "key_date";
    public final static String MONTH_KEY = "key_month";
    public final static String SHOW_YEAR_KEY = "key_show_year";
    public final static String YEAR_KEY = "key_year";
    public final static String UID_KEY = "key_uid";
    public final static String SURNAME_KEY = "key_surname";
    public final static String PHONE_NUMBER_KEY = "key_phone_number";
    public final static String DATE_OF_BIRTH_KEY = "key_dob";
    public final static String ADDRESS_KEY = "key_Address";
    public final static String GENDER_KEY = "key_gender";
    public final static String ROLE_KEY = "key_role";
    public final static String STATE_KEY = "key_gender";
    public final static String FIRST_NAME_KEY = "first_name";
    public static final String USER_KEY = "clientKey";
    public static final String CHOSEN_OFFICE = "clientKey";
    public static final String USER_NAME = "clientKey";
    public static final String EMAIL_ADDRESS = "clientKey";
    public static final String STATUS_KEY = "statusKey";
    public final static String KEY_EXTRA_PROFILE_ID = "KEY_EXTRA_SIGN_UP_ID";

    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");

    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
    String timeLineTime = mdformat.format(calendar.getTime());
    Profile userProfile;
    AppCompatButton nextBtn, sign_up;
    double remainingAmount;
    Location mCurrentLocation = null;
    String daysRemaining,loanStatus;
    int daysBTWN;
    Gson gson;
    String userType;
    String skylightFee="0.03";
    AppCompatSpinner spn_loan;
    AppCompatEditText amountToRepay;
    AppCompatButton btnPay;
    AppCompatTextView loanBalance;
    private SQLiteDatabase sqLiteDatabase;

    Long userID;
    int loanID;
    Long birthdayFID;
    Long acctFID;
    SimpleDateFormat dateFormatWithZone ;
    Date date ;
    String currentDate ;
    double loanAmount;
    ArrayAdapter<Loan> loanAdapter;
    int loanIndex,selectedCustomerIndex;
    Loan loan;
    double unpaidLoanBalance,residueAmt;
    SharedPreferences sharedpreferences;
    String json,customerName;
    Bundle loanBundle;
    private ArrayAdapter<Account> accountAdapter;
    private ArrayAdapter<Customer> customerArrayAdapter;
    private ArrayAdapter<Customer> customerArrayAdapterN;
    private ArrayAdapter<Loan> skyLightPackageArrayAdapter;
    private ArrayList<Customer> customerArrayList;
    private List<Customer> customerList;
    private ArrayList<Loan> loanArrayList;
    AppCompatSpinner spn_customers;
    private LoanDAO loanDAO;
    private TimeLineClassDAO timeLineClassDAO;
    ActivityResultLauncher<Intent> allCusStartLoanRepaymentForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        startTellerNotification();
                        Intent intent = result.getData();
                        Toast.makeText(AllCusLoanRepayment.this, "Re-payment returned successful", Toast.LENGTH_SHORT).show();
                        unpaidLoanBalance=loan.getLoanBalance();
                        residueAmt=unpaidLoanBalance-loanAmount;
                        loan.setLoanBalance(residueAmt);
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            loanDAO.updateLoan("Complete",loanID,residueAmt);
                        }

                        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String currentDate = dateFormatWithZone.format(date);

                        String timelineDetails = userSurname + "," + userFirstName + "Repaid Loan" + "for" + customerName;
                        String tittle = "Loan repayment Alert!";
                        String mYtimelineDetails = "You made loan payment of N" + loanAmount + " for"  + customerName+ "on" + timeLineTime;
                        String custimelineDetails = "loan repayment of N" + loanAmount + " was recorded at" + timeLineTime;
                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {
                            dbHelper.openDataBase();
                            timeLineClassDAO.insertTimeLine(tittle,timelineDetails,currentDate,null);
                        }


                        userProfile.addPTimeLine(tittle,mYtimelineDetails);
                        customer.addCusTimeLine(tittle,custimelineDetails);
                        finish();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_all_cus_loan_repayment);
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        loanBundle=new Bundle();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        userEmail = userPreferences.getString(EMAIL_ADDRESS, "email");
        userAddress = userPreferences.getString(ADDRESS_KEY, "address1");
        profileUserName = userPreferences.getString(USER_NAME, "userName");
        userType = userPreferences.getString("machine", "");
        date = new Date();
        loan= new Loan();
        customer=new Customer();
        timeLineClassDAO= new TimeLineClassDAO(this);
        loanDAO = new LoanDAO(this);
        dateFormatWithZone= new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        currentDate = dateFormatWithZone.format(date);
        btnPay = findViewById(R.id.loanPayNowAll);
        loanBalance = findViewById(R.id.loanBalanceAll);
        spn_loan = findViewById(R.id.loanFromAllCus);
        amountToRepay= findViewById(R.id.amountToRepayAll);
        spn_customers = findViewById(R.id._customerAll);
        CusDAO cusDAO= new CusDAO(this);
        //Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        dbHelper= new DBHelper(this);
        customerArrayList = cusDAO.getAllCustomers11();

        //spn_old_customers.setSelection(0);
        try {
            customerArrayAdapterN = new ArrayAdapter<Customer>(AllCusLoanRepayment.this, android.R.layout.simple_spinner_item, customerArrayList);
            customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_customers.setAdapter(customerArrayAdapterN);
            spn_customers.setSelection(customerArrayAdapterN.getPosition(customer));
            selectedCustomerIndex = spn_customers.getSelectedItemPosition();

        } catch (RuntimeException e) {
            System.out.println("Oops!");
        }

        selectedCustomerIndex = spn_customers.getSelectedItemPosition();
        try {
            customer = (Customer) spn_customers.getItemAtPosition(loanIndex);
        } catch (NullPointerException e) {
        }

        if(customer !=null){

            spn_loan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    loanAdapter = new ArrayAdapter<Loan>(AllCusLoanRepayment.this, android.R.layout.simple_spinner_item, customer.getCusLoans());
                    loanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_loan.setAdapter(loanAdapter);
                    spn_loan.setSelection(0);

                    loanIndex = spn_loan.getSelectedItemPosition();
                    loan = (Loan) spn_loan.getItemAtPosition(loanIndex);
                    if(loan !=null){
                        loanID=loan.getLoanId();
                        customerName=loan.getLoan_customer().getCusSurname()+","+loan.getLoan_customer().getCusFirstName();
                        unpaidLoanBalance=loan.getLoanBalance();

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


        }


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPaymentActivity(userType,customer,loan);

            }
        });
    }
    private void startTellerNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("New Loan Repayment Alert")
                        .setContentText("A Customer just repaid his/her Loan");

        Intent notificationIntent = new Intent(this, LoginDirAct.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(LoginDirAct.class);
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
    protected void sendSMSMessage() {
        spn_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                customer = (Customer) parent.getSelectedItem();
                Toast.makeText(AllCusLoanRepayment.this, "Customer's ID: " + customer.getCusUID() + ",  Customer's Name : " + customer.getCusFirstName(), Toast.LENGTH_SHORT).show();

                customerName=loan.getLoan_customer().getCusSurname()+","+loan.getLoan_customer().getCusFirstName();

                customerName=loan.getLoan_customer().getCusSurname()+","+loan.getLoan_customer().getCusFirstName();
                userPhoneNumber=customer.getCusPhoneNumber();
                String welcomeMessage = "Awajima appreciates your efforts in repaying  your loan";
                phone_number = findViewById(R.id.phone_number);
                uPhoneNumber = Objects.requireNonNull(phone_number.getText()).toString();
                Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
                //Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
                Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(userPhoneNumber),
                        new com.twilio.type.PhoneNumber("234" + userPhoneNumber),
                        welcomeMessage)
                        .create();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }
    public void startPaymentActivity(String userType, Customer customer, Loan loan) {
        unpaidLoanBalance=loan.getLoanBalance();
        //unpaidLoanBalance=loan.getResidue();
        DBHelper applicationDb = new DBHelper(this);

        try {
            loanAmount = Double.parseDouble(Objects.requireNonNull(amountToRepay.getText()).toString().trim());
            if(loanAmount>unpaidLoanBalance){

                Toast.makeText(this, "The amount to pay is invalid", Toast.LENGTH_LONG).show();
                amountToRepay.requestFocus();

            }else {
                SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String currentDate = dateFormatWithZone.format(date);

                String timelineDetails = userSurname + "," + userFirstName + "Repaid Loan" + "for" + customerName;
                String tittle = "Loan repayment Alert!";
                String mYtimelineDetails = "You made loan payment of N" + loanAmount + " for"  + customerName+ "on" + timeLineTime;
                String custimelineDetails = "loan repayment of N" + loanAmount + " was recorded at" + timeLineTime;
                remainingAmount=unpaidLoanBalance-loanAmount;
                if(remainingAmount==0){
                    loanStatus="complete";

                }else {
                    loanStatus="incomplete";

                }
                if(loan !=null){
                    loanID= this.loan.getLoanId();

                }

                customerName= this.loan.getLoan_customer().getCusSurname()+","+ this.loan.getLoan_customer().getCusFirstName();
                loanBundle.putParcelable("Loan", this.loan);
                loanBundle.putDouble("Total", loanAmount);
                loanBundle.putDouble("LoanID", loanID);
                loanBundle.putParcelable("Profile", userProfile);
                loanBundle.putParcelable("Customer", this.loan.getLoan_customer());
                loanBundle.putString("Name", customerName);

                String[] Options = {"Submit as a Teller", "Pay with Bank","Pay with card"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Select mode of repayment")

                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                finish();

                            }
                        });
                loanDAO= new LoanDAO(this);
                timeLineClassDAO= new TimeLineClassDAO(this);
                builder.setItems(Options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            loanDAO.updateLoan(loanStatus,loanID,remainingAmount);
                            timeLineClassDAO.insertTimeLine(tittle,timelineDetails,currentDate,null);
                            userProfile.addPTimeLine(tittle,mYtimelineDetails);
                            customer.addCusTimeLine(tittle,custimelineDetails);

                        }
                        if(which == 1){
                            Intent intent3 = new Intent(AllCusLoanRepayment.this, FluPaywithBank.class);
                            intent3.putExtras(loanBundle);
                            //startActivity(intent3);
                            allCusStartLoanRepaymentForResult.launch(new Intent(intent3));

                        }else if(which == 2){
                            Intent i = new Intent(AllCusLoanRepayment.this, PayNowActivity.class);
                            i.putExtras(loanBundle);
                            //startActivity(intent3);
                            allCusStartLoanRepaymentForResult.launch(new Intent(i));

                        }else{
                            Toast.makeText(AllCusLoanRepayment.this, "Something went wrong " , Toast.LENGTH_LONG).show();
                        }
                    }
                });

                AlertDialog dialog = builder.create();


            }
        } catch (Exception e) {
            System.out.println("Oops!");
            amountToRepay.requestFocus();
        }

        BroadcastUtils.sendExplicitBroadcast(this, new Intent(), "skylight Loan Repayment action");
    }

    private void submitAsATeller() {

    }
}