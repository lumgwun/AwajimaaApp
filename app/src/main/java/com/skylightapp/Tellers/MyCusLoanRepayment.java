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
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.FlutterWavePayments.FluPaywithBank;
import com.skylightapp.PayNowActivity;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.twilio.Twilio;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class MyCusLoanRepayment extends AppCompatActivity {
    SharedPreferences userPreferences;
    AppCompatEditText phone_number, email_address, firstName, surname1, userName, password, address_2;
    protected DatePickerDialog datePickerDialog;
    Random ran = new Random();
    SecureRandom random = new SecureRandom();

    Context context;
    String dateOfBirth;
    PreferenceManager preferenceManager;
    private Bundle bundle;
    String uPhoneNumber;
    int profileID;
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

    AppCompatSpinner spn_customers, office, gender;
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
    public static final String AUTH_TOKEN = System.getenv("AC5e05dc0a793a29dc1da2eabdebd6c28d");

    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
    String timeLineTime = mdformat.format(calendar.getTime());
    Profile userProfile;
    AppCompatButton nextBtn, sign_up;
    Location mCurrentLocation = null;
    String daysRemaining;
    int daysBTWN;
    Gson gson;
    String userType;
    String skylightFee="0.03";
    AppCompatSpinner spn_loan;
    AppCompatEditText amountToRepay;
    AppCompatButton btnPay;
    AppCompatTextView loanBalance;

    Long userID;
    int loanID;
    Long birthdayFID;
    Long acctFID;
    SimpleDateFormat dateFormatWithZone ;
    Date date ;
    String currentDate ;
    double loanAmount;
    ArrayAdapter<Loan> loanAdapter;
    private ArrayList<Customer> customerArrayList;
    private ArrayAdapter<Customer> customerArrayAdapterN;
    int loanIndex;
    Loan loan;
    double unpaidLoanBalance,residueAmt;
    SharedPreferences sharedpreferences;
    String json,customerName;
    Bundle loanBundle;
    private static final String PREF_NAME = "skylight";
    private SQLiteDatabase sqLiteDatabase;
    ActivityResultLauncher<Intent> myCusStartLoanRepaymentForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        dbHelper = new DBHelper(MyCusLoanRepayment.this);
                        unpaidLoanBalance=loan.getAccountBalance();
                        residueAmt=unpaidLoanBalance-loanAmount;
                        loan.setAccountBalance(residueAmt);
                        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String currentDate = dateFormatWithZone.format(date);

                        String timelineDetails = userSurname + "," + userFirstName + "Repaid Loan" + "for" + customerName;
                        String tittle = "Loan repayment Alert!";
                        String mYtimelineDetails = "You made loan payment of N" + loanAmount + " for"  + customerName+ "on" + timeLineTime;
                        String custimelineDetails = "loan repayment of N" + loanAmount + " was recorded at" + timeLineTime;
                        userProfile.addTimeLine(tittle,mYtimelineDetails);
                        customer.addCusTimeLine(tittle,custimelineDetails);
                        sendSMSMessage(customer);

                        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {

                            sqLiteDatabase = dbHelper.getWritableDatabase();
                            Toast.makeText(MyCusLoanRepayment.this, "Re-payment returned successful", Toast.LENGTH_SHORT).show();

                            dbHelper.updateLoan("paid",loanID,residueAmt);

                            dbHelper.insertTimeLine(tittle,timelineDetails,currentDate,null);

                        }


                        finish();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_cus_loan_rep);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        random= new SecureRandom();
        loanBundle=new Bundle();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        date = new Date();
        dateFormatWithZone= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormatWithZone.format(date);
        spn_customers = findViewById(R.id._customerMy);
        btnPay = findViewById(R.id.loanPayNowMy);
        loanBalance = findViewById(R.id.loanBalanceMy);
        spn_loan = findViewById(R.id.loanFromMyCus);
        amountToRepay= findViewById(R.id.amountToRepayMy);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //mAuth=FirebaseAuth.getInstance();
        Twilio.init("AC5e05dc0a793a29dc1da2eabdebd6c28d", "39410e8b813c131da386f3d7bb7f94f7");
        dbHelper= new DBHelper(this);
        if(userProfile !=null){
            profileID=userProfile.getPID();
            customerArrayList = dbHelper.getCustomersFromCurrentProfile(profileID);
            customerArrayAdapterN = new ArrayAdapter<Customer>(MyCusLoanRepayment.this, android.R.layout.simple_spinner_item, customerArrayList);
            customerArrayAdapterN.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_customers.setAdapter(customerArrayAdapterN);
            //spn_old_customers.setSelection(0);
            spn_customers.setSelection(customerArrayAdapterN.getPosition(customer));
            //selectedCustomerIndex = spn_old_customers.getSelectedItemPosition();

            spn_customers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    customer = (Customer) parent.getSelectedItem();
                    Toast.makeText(context, "Customer's ID: " + customer.getCusUID() + ",  Customer's Name : " + customer.getCusFirstName(), Toast.LENGTH_SHORT).show();

                    loanAdapter = new ArrayAdapter<Loan>(MyCusLoanRepayment.this, android.R.layout.simple_spinner_item, customer.getCusLoans());
                    loanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spn_loan.setAdapter(loanAdapter);
                    spn_loan.setSelection(0);

                    loanIndex = spn_loan.getSelectedItemPosition();
                    loan = (Loan) spn_loan.getItemAtPosition(loanIndex);
                    loanID=loan.getLoanId();
                    customerName=loan.getCustomer().getCusSurname()+","+loan.getCustomer().getCusFirstName();


                    spn_loan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            loan = (Loan) parent.getSelectedItem();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    if(loan !=null){
                        unpaidLoanBalance=loan.getAccountBalance();
                        loanID=loan.getLoanId();
                        loanBalance.setText("Loan Balance:N"+loan.getAccountBalance());
                        customerName=loan.getCustomer().getCusSurname()+","+loan.getCustomer().getCusFirstName();
                        Toast.makeText(context, "Customer's Amount: " + loan.getResidue()  , Toast.LENGTH_SHORT).show();

                    }

                    userSurname = userPreferences.getString("PROFILE_SURNAME", "userSurname");
                    userFirstName = userPreferences.getString("PROFILE_FIRSTNAME", "firstName1");
                    userPhoneNumber = userPreferences.getString("PROFILE_PHONE", "phoneNumber");
                    userEmail = userPreferences.getString("PROFILE_EMAIL", "email");
                    userAddress = userPreferences.getString("PROFILE_ADDRESS", "address1");
                    profileUserName = userPreferences.getString("PROFILE_USERNAME", "userName");
                    userType = userPreferences.getString("machine", "machine");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


        }


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPaymentActivity();

            }
        });
    }
    protected void sendSMSMessage(Customer customer) {
        String loanMessage = "Skylight appreciates your efforts in repaying  your loan";
        if(this.customer !=null){
            uPhoneNumber= this.customer.getCusPhoneNumber();

        }
        Twilio.init("AC5e05dc0a793a29dc1da2eabdebd6c28d", "39410e8b813c131da386f3d7bb7f94f7");
        Bundle smsBundle = new Bundle();
        smsBundle.putString("PROFILE_PHONE", userPhoneNumber);
        smsBundle.putString("USER_PHONE", userPhoneNumber);
        smsBundle.putString("smsMessage", loanMessage);
        smsBundle.putString("to", userPhoneNumber);
        Intent otpIntent = new Intent(MyCusLoanRepayment.this, SMSAct.class);
        otpIntent.putExtras(smsBundle);
        otpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


    }
    public void startPaymentActivity() {


        try {
            loanAmount = Double.parseDouble(Objects.requireNonNull(amountToRepay.getText()).toString().trim());
            if(loanAmount>unpaidLoanBalance){

                Toast.makeText(this, "The amount to pay is invalid", Toast.LENGTH_LONG).show();
                amountToRepay.requestFocus();

            }else {
                dbHelper = new DBHelper(this);
                loanIndex = spn_loan.getSelectedItemPosition();
                loan = (Loan) spn_loan.getItemAtPosition(loanIndex);
                loanID=loan.getLoanId();
                customerName=loan.getCustomer().getCusSurname()+","+loan.getCustomer().getCusFirstName();
                loanBundle.putParcelable("Loan", loan);
                loanBundle.putDouble("Total", loanAmount);
                loanBundle.putInt("LoanID", loanID);
                loanBundle.putParcelable("Profile", userProfile);
                loanBundle.putParcelable("Customer", loan.getCustomer());
                loanBundle.putString("Name", customerName);

                String[] Options = {"Pay with Bank", "Pay with card"};
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

                            }
                        });
                builder.setItems(Options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){
                            Intent intent3 = new Intent(MyCusLoanRepayment.this, FluPaywithBank.class);
                            intent3.putExtras(loanBundle);
                            //startActivity(intent3);
                            myCusStartLoanRepaymentForResult.launch(new Intent(intent3));

                        }else if(which == 1){
                            Intent i = new Intent(MyCusLoanRepayment.this, PayNowActivity.class);
                            i.putExtras(loanBundle);
                            //startActivity(intent3);
                            myCusStartLoanRepaymentForResult.launch(new Intent(i));

                        }else{
                            Toast.makeText(MyCusLoanRepayment.this, "Something went wrong " , Toast.LENGTH_LONG).show();
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
}