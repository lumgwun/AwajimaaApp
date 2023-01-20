package com.skylightapp.Customers;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;

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
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
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
import com.skylightapp.FlutterWavePayments.FluPaywithBank;
import com.skylightapp.GooglePayAct;
import com.skylightapp.PayNowActivity;
import com.skylightapp.QuickTellerPayAct;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
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
    AppCompatEditText phone_number;
    protected DatePickerDialog datePickerDialog;
    Random ran = new Random();
    SecureRandom random = new SecureRandom();
    Context context;
    private Bundle bundle;
    String uPhoneNumber;
    int profileID;
    String userSurname;
    String userFirstName, uPassword;
    String userPhoneNumber;
    String userEmail;
    String userAddress;
    String profileUserName;

    Customer customer;

    DBHelper dbHelper;

    public static final String ACCOUNT_SID = System.getenv("ACb6e4c829a5792a4b744a3e6bd1cf2b4e");
    public static final String AUTH_TOKEN = System.getenv("0d5cbd54456dd0764786db0c37212578");

    Calendar calendar = Calendar.getInstance();
    Profile userProfile;
    Gson gson;
    String userType;
    String skylightFee="0.03";
    AppCompatSpinner spn_loan;
    AppCompatEditText amountToRepay;
    AppCompatButton btnPay;
    AppCompatTextView loanBalance;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    //private CodeDAO codeDAO;
    //private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    //private PaymentDAO paymentDAO;
    //private AdminBalanceDAO adminBalanceDAO;
    private TimeLineClassDAO timeLineClassDAO;
    //private WorkersDAO workersDAO;
    //private OfficeBranchDAO officeBranchDAO;

    Long userID;
    int loanID;
    Long birthdayFID;
    Long acctFID;
    SimpleDateFormat dateFormatWithZone ;
    Date date ;
    String currentDate ;
    double loanAmount;
    ArrayAdapter<Loan> loanAdapter;
    int loanIndex,initIndex;
    Loan loan;
    double unpaidLoanBalance,residueAmt;
    SharedPreferences sharedpreferences;
    String json,customerName,cusPhoneNo,loanCurrency,emailAddress;
    private static final String PREF_NAME = "awajima";
    Bundle loanBundle, bundle1;
    private Customer loanCustomer;
    ActivityResultLauncher<Intent> mStartLoanRepaymentForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        Toast.makeText(CusLoanRepaymentAct.this, "Re-payment returned successful", Toast.LENGTH_SHORT).show();
                        if(loanDAO !=null){
                            try {

                                unpaidLoanBalance=loan.getLoanBalance();
                                residueAmt=unpaidLoanBalance-loanAmount;
                                loan.setLoanBalance(residueAmt);

                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                        }



                        if(loanDAO !=null){
                            try {

                                loanDAO.updateLoan("paid",loanID,residueAmt);

                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                        }


                        dateFormatWithZone= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        currentDate = dateFormatWithZone.format(date);
                        sendSMSMessageD();

                        String timelineDetails = userSurname + "," + userFirstName + "Repaid Loan" + "for" + customerName;
                        String tittle = "Loan repayment Alert!";
                        String mYtimelineDetails = "You made loan payment of N" + loanAmount + " for"  + customerName+ "on" + currentDate;
                        String custimelineDetails = "loan repayment of N" + loanAmount + " was recorded at" + currentDate;
                        if(loanDAO !=null){
                            try {

                                timeLineClassDAO.insertTimeLine(tittle,timelineDetails,currentDate,null);

                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                        }
                        if(userProfile !=null){
                            userProfile.addPTimeLine(tittle,mYtimelineDetails);

                        }
                        if(customer !=null){
                            customer.addCusTimeLine(tittle,custimelineDetails);

                        }

                        finish();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loan_repayment);
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        loanCustomer= new Customer();
        profileDao= new ProfDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);
        tranXDAO= new TranXDAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);
        random= new SecureRandom();
        loanBundle=new Bundle();
        userProfile=new Profile();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        date = new Date();
        dateFormatWithZone= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        currentDate = dateFormatWithZone.format(date);
        btnPay = findViewById(R.id.loanPayNow);
        loanBalance = findViewById(R.id.loanBalance);
        spn_loan = findViewById(R.id.spn_loan);
        amountToRepay= findViewById(R.id.amountToRepay);
        bundle =getIntent().getExtras();
        if(bundle1 !=null){
            loan= bundle.getParcelable("Loan");
            spn_loan.setVisibility(View.GONE);
        }
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        dbHelper= new DBHelper(this);
        if(userProfile !=null) {
            loanAdapter = new ArrayAdapter<Loan>(this, android.R.layout.simple_spinner_item, userProfile.getProfileLoans());
            loanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_loan.setAdapter(loanAdapter);
            spn_loan.setSelection(0);

            loanIndex = spn_loan.getSelectedItemPosition();

            spn_loan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(initIndex==position){
                        return;
                    }else {
                        //loan = (Loan) parent.getSelectedItem();
                        loan = (Loan) spn_loan.getItemAtPosition(loanIndex);

                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            if(loan !=null){
                loanID=loan.getLoanId();
                unpaidLoanBalance=loan.getLoanBalance();
                loanCustomer=loan.getLoan_customer();
                loanCurrency =loan.getloanCurrency();
                if(loanCurrency.isEmpty()){
                    loanCurrency="NGN";
                }
                loanBalance.setText("Loan Balance"+loanCurrency+loan.getLoanBalance());
                unpaidLoanBalance=loan.getLoanBalance();


            }
            if(loanCustomer !=null){
                customerName=loanCustomer.getCusSurname()+","+loanCustomer.getCusFirstName();
                cusPhoneNo=loanCustomer.getCusPhoneNumber();
                emailAddress=loanCustomer.getCusEmailAddress();
            }

            userSurname = userPreferences.getString("PROFILE_SURNAME", "userSurname");
            userFirstName = userPreferences.getString("PROFILE_FIRSTNAME", "firstName1");
            userPhoneNumber = userPreferences.getString("PROFILE_PHONE", "phoneNumber");
            userEmail = userPreferences.getString("PROFILE_EMAIL", "email");
            userAddress = userPreferences.getString("PROFILE_ADDRESS", "address1");
            profileUserName = userPreferences.getString("PROFILE_USERNAME", "userName");
            userType = userPreferences.getString("PROFILE_ROLE", "machine");

        }
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPaymentActivity(loanID,unpaidLoanBalance,loanCustomer,customerName,cusPhoneNo,loan);

            }
        });
    }
    protected void sendSMSMessageD() {
        String welcomeMessage = "Awajima appreciates your efforts in repaying  your loan";
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String machine = userPreferences.getString("machine","");
        Bundle bundle = new Bundle();
        bundle.putInt("ProfileID", profileID);
        bundle.putString(machine, machine);
        bundle.putString(PROFILE_PHONE, this.cusPhoneNo);
        bundle.putString("smsMessage", welcomeMessage);
        bundle.putString("emailAddress", this.emailAddress);
        Intent intent = new Intent(this, SMSAct.class);
        intent.putExtras(bundle);
        overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
    public void startPaymentActivity(int loanID,double unpaidLoanBalance,Customer loanCustomer,String customerName,String cusPhoneNo,Loan loan) {


        try {
            try {

                loanAmount = Double.parseDouble(Objects.requireNonNull(amountToRepay.getText()).toString().trim());

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if(loanAmount>unpaidLoanBalance){

                Toast.makeText(this, "The amount to pay is invalid", Toast.LENGTH_LONG).show();
                amountToRepay.requestFocus();

            }else {
                loanBundle.putParcelable("Loan", loan);
                loanBundle.putDouble("Total", loanAmount);
                loanBundle.putDouble("LoanID", loanID);
                loanBundle.putParcelable("Profile", userProfile);
                loanBundle.putParcelable("Customer", loanCustomer);
                loanBundle.putString("Name", customerName);
                loanBundle.putString("PaymentFor", "Loan Repayment");

                String[] Options = {"Pay with QuickTeller", "Pay with Google Pay"};
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
                            Intent intent3 = new Intent(CusLoanRepaymentAct.this, QuickTellerPayAct.class);
                            intent3.putExtras(loanBundle);
                            //startActivity(intent3);
                            mStartLoanRepaymentForResult.launch(new Intent(intent3));

                        }else if(which == 1){
                            Intent i = new Intent(CusLoanRepaymentAct.this, GooglePayAct.class);
                            i.putExtras(loanBundle);
                            //startActivity(intent3);
                            mStartLoanRepaymentForResult.launch(new Intent(i));

                        }else{
                            Toast.makeText(CusLoanRepaymentAct.this, "Something went wrong " , Toast.LENGTH_LONG).show();
                        }
                    }
                });

                AlertDialog dialog = builder.create();


            }
        } catch (Exception e) {
            System.out.println("Oops!");
            amountToRepay.requestFocus();
        }

        BroadcastUtils.sendExplicitBroadcast(this, new Intent(), "Awajima Loan Repayment action");

    }
}