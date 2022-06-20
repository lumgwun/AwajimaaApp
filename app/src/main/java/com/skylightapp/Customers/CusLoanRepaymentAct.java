package com.skylightapp.Customers;

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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class CusLoanRepaymentAct extends AppCompatActivity {
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
    int loanIndex;
    Loan loan;
    double unpaidLoanBalance,residueAmt;
    SharedPreferences sharedpreferences;
    String json,customerName;
    Bundle loanBundle, bundle1;
    ActivityResultLauncher<Intent> mStartLoanRepaymentForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Toast.makeText(CusLoanRepaymentAct.this, "Re-payment returned successful", Toast.LENGTH_SHORT).show();
                        unpaidLoanBalance=loan.getAccountBalance();
                        residueAmt=unpaidLoanBalance-loanAmount;
                        loan.setAccountBalance(residueAmt);
                        dbHelper.updateLoan("paid",loanID,residueAmt);
                        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String currentDate = dateFormatWithZone.format(date);

                        String timelineDetails = userSurname + "," + userFirstName + "Repaid Loan" + "for" + customerName;
                        String tittle = "Loan repayment Alert!";
                        String mYtimelineDetails = "You made loan payment of N" + loanAmount + " for"  + customerName+ "on" + timeLineTime;
                        String custimelineDetails = "loan repayment of N" + loanAmount + " was recorded at" + timeLineTime;
                        dbHelper.insertTimeLine(tittle,timelineDetails,currentDate,null);
                        userProfile.addTimeLine(tittle,mYtimelineDetails);
                        customer.addCusTimeLine(tittle,custimelineDetails);
                        finish();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loan_repayment);
        /*getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();*/
        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        random= new SecureRandom();
        loanBundle=new Bundle();
        userProfile=new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        date = new Date();
        dateFormatWithZone= new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        currentDate = dateFormatWithZone.format(date);
        btnPay = findViewById(R.id.loanPayNow);
        loanBalance = findViewById(R.id.loanBalance);
        spn_loan = findViewById(R.id.spn_loan);
        amountToRepay= findViewById(R.id.amountToRepay);
        bundle =getIntent().getExtras();
        if(bundle1 !=null){
            loan= bundle.getParcelable("Loan");
            loanID=loan.getLoanId();
            loanBalance.setText(MessageFormat.format("Loan Balance:N{0}", loan.getAccountBalance()));
            unpaidLoanBalance=loan.getAccountBalance();
            spn_loan.setVisibility(View.GONE);
        }
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //mAuth=FirebaseAuth.getInstance();
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        dbHelper= new DBHelper(this);
        if(userProfile !=null) {
            loanAdapter = new ArrayAdapter<Loan>(this, android.R.layout.simple_spinner_item, userProfile.getProfileLoans());
            loanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_loan.setAdapter(loanAdapter);
            spn_loan.setSelection(0);

            loanIndex = spn_loan.getSelectedItemPosition();
            loan = (Loan) spn_loan.getItemAtPosition(loanIndex);
            loanID=loan.getLoanId();
            loanBalance.setText(MessageFormat.format("Loan Balance:N{0}", loan.getAccountBalance()));
            customerName=loan.getCustomer().getCusSurname()+","+loan.getCustomer().getCusFirstName();


            spn_loan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    loan = (Loan) parent.getSelectedItem();
                    loanID=loan.getLoanId();
                    customerName=loan.getCustomer().getCusSurname()+","+loan.getCustomer().getCusFirstName();
                    Toast.makeText(context, "Customer's Amount: " + loan.getResidue()  , Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            unpaidLoanBalance=loan.getAccountBalance();
            userSurname = userPreferences.getString(SURNAME_KEY, "userSurname");
            userFirstName = userPreferences.getString(FIRST_NAME_KEY, "firstName1");
            userPhoneNumber = userPreferences.getString(PHONE_NUMBER_KEY, "phoneNumber");
            userEmail = userPreferences.getString(EMAIL_ADDRESS, "email");
            userAddress = userPreferences.getString(ADDRESS_KEY, "address1");
            profileUserName = userPreferences.getString(USER_NAME, "userName");
            userType = userPreferences.getString("machine", "machine");
            int userRole = userPreferences.getInt("Role", Integer.parseInt("Role"));
        }
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPaymentActivity();

            }
        });
    }
    protected void sendSMSMessage() {
        String welcomeMessage = "Skylight appreciates your efforts in repaying  your loan";
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
    public void startPaymentActivity() {


        try {
            loanAmount = Double.parseDouble(Objects.requireNonNull(amountToRepay.getText()).toString().trim());
            if(loanAmount>unpaidLoanBalance){

                Toast.makeText(this, "The amount to pay is invalid", Toast.LENGTH_LONG).show();
                amountToRepay.requestFocus();

            }else {
                DBHelper applicationDb = new DBHelper(this);
                loanIndex = spn_loan.getSelectedItemPosition();
                loan = (Loan) spn_loan.getItemAtPosition(loanIndex);
                loanID=loan.getLoanId();
                customerName=loan.getCustomer().getCusSurname()+","+loan.getCustomer().getCusFirstName();
                loanBundle.putParcelable("Loan", loan);
                loanBundle.putDouble("Total", loanAmount);
                loanBundle.putDouble("LoanID", loanID);
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
                            Intent intent3 = new Intent(CusLoanRepaymentAct.this, FluPaywithBank.class);
                            intent3.putExtras(loanBundle);
                            //startActivity(intent3);
                            mStartLoanRepaymentForResult.launch(new Intent(intent3));

                        }else if(which == 1){
                            Intent i = new Intent(CusLoanRepaymentAct.this, PayNowActivity.class);
                            i.putExtras(loanBundle);
                            //startActivity(intent3);
                            mStartLoanRepaymentForResult.launch(new Intent(i));

                        }else{
                            Toast.makeText(getApplicationContext(), "Something went wrong " , Toast.LENGTH_LONG).show();
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