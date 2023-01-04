package com.skylightapp.FlutterWavePayments;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Profile;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Interfaces.PaymentResultListener;
import com.skylightapp.LoginDirAct;
import com.skylightapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.Objects;

import static com.skylightapp.MarketClasses.MarketBizPackage.PACKAGE_TYPE;
import static com.skylightapp.FlutterWavePayments.OTPFragment.EXTRA_OTP;

public class FluPayWithCard extends AppCompatActivity implements PaymentResultListener {
    private TextInputLayout layoutCardNumber;
    private TextInputLayout layoutCardExpiryMonth,layoutCVV,layoutCardExpiryYear;
    private TextInputLayout mCardCVV;
    private EditText edtCardNo,edtCardMonth,edtCardYear,edtCardCVV;
    private  Button btnPayNow,btnHome;
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json;
    String ManagersBundleString;
    String iDBundleString;
    String PackageName;
    String amountBundleString;
    String dateBundleString;
    String imageBundleString;
    double totalBundleString;
    private Customer customer;
    private Account account;

    private Profile userProfile;
    MarketBizPackage marketBizPackage;
    DBHelper dbHelper;
    SecureRandom random;
    long transactionID;
    String refID ;
    String profileSurname;
    String profileFirstName;
    String userPix;
    String profilePhone;
    Transaction transaction;
    String profileEmail;
    String userName;
    String profileRole;
    long customerId8;
    String customerName,dateOfReport;
    String customerPhoneNo;
    String customerEmail,packageType,planCode,accessCode;
    double accountBalance;
    double amountSoFar;
    double packageTotal;
    long packageID,AccountID,profileID;
    double packageAmount;
    double amountRemaining,totalToday,accountBalanceNow;
    int savingsCount, numberOfDaysRemaining,selectedNumberOfDays;
    long newSavingsID ,reportID;
    TextView txtName,txtItemAmtInfo;
    private CustomerDailyReport customerDailyReport;
    String OTP;
    Intent intent;
    Bundle totalBundle,finalTotalBundle;
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            intent = result.getData();
                            intent = getIntent();
                            String OTP = intent.getStringExtra(EXTRA_OTP);
                            performCharge();

                        }

                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random =new SecureRandom();
        dbHelper= new DBHelper(this);
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        totalBundle= new Bundle();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        customer= new Customer();
        transactionID = random.nextInt((int) (Math.random() * 900) + 100);
        refID = "SkyLightT/"+ random.nextInt((int) (Math.random() * 900000) + 100000);
        setContentView(R.layout.act_flu_pay_with_card);
        layoutCardNumber = findViewById(R.id.layout_card_number3);
        edtCardNo = findViewById(R.id.edit_card_number3);
        layoutCardExpiryMonth = findViewById(R.id.layout_expiry_month3);
        layoutCardExpiryYear = findViewById(R.id.layout_card_year3);
        layoutCVV = findViewById(R.id.layout_cvv3);
        edtCardMonth = findViewById(R.id.edit_expiry_month3);
        mCardCVV = findViewById(R.id.til_card_cvv);
        edtCardYear = findViewById(R.id.edit_card_year3);
        edtCardCVV = findViewById(R.id.edit_cvv3);
        btnPayNow = findViewById(R.id.pay_now3);
        btnHome = findViewById(R.id.home_from_paymen3t);
        txtName =  findViewById(R.id.cus_info);
        txtItemAmtInfo = findViewById(R.id.item_info_Amt);
        totalBundle = getIntent().getExtras().getBundle("paymentBundle") ;
        totalBundle = getIntent().getExtras().getBundle("finalTotalBundle") ;

        if(totalBundle !=null){
            totalBundle = this.getIntent().getExtras();
            packageID = Long.parseLong(totalBundle.getString("Package Id"));
            AccountID = Long.parseLong(totalBundle.getString("Account ID"));
            dateOfReport = totalBundle.getString("Date");
            packageType = totalBundle.getString(PACKAGE_TYPE);
            account = totalBundle.getParcelable("Account");
            customer = totalBundle.getParcelable("Customer");
            marketBizPackage = totalBundle.getParcelable("Package");
            transaction = totalBundle.getParcelable("Transaction");
            customerDailyReport = totalBundle.getParcelable("Savings");
            reportID = Long.parseLong(totalBundle.getString("Report ID"));
            planCode = totalBundle.getString("Plan Code");
            accessCode = totalBundle.getString("Access Code");
            packageAmount = Double.parseDouble(totalBundle.getString("Amount"));
            selectedNumberOfDays = Integer.parseInt(totalBundle.getString("Number of Days"));
            totalToday = Double.parseDouble(totalBundle.getString("Total"));
            int packageDaysRem = totalBundle.getInt("Day Remaining");
            amountRemaining = Double.parseDouble(totalBundle.getString("Amount Remaining"));
            savingsCount = Integer.parseInt(totalBundle.getString("Package Count"));
            String grandTotalOfPackage = totalBundle.getString("Grand Total");
            amountSoFar = Double.parseDouble(totalBundle.getString("Amount So Far"));
            accountBalanceNow = Double.parseDouble(totalBundle.getString("Package Account Balance"));
            customerName = totalBundle.getString("Customer Name");
            customerId8 = Long.parseLong(totalBundle.getString("Customer ID"));
            customerEmail = totalBundle.getString("Customer Email");
            customerPhoneNo = totalBundle.getString("Customer Phone Number");
            txtName.setText(MessageFormat.format("{0}--{1}", packageID, reportID));
            txtItemAmtInfo.setText(MessageFormat.format("Total to Pay for Today:NGN{0}", totalToday));
        }

        Objects.requireNonNull(layoutCardExpiryMonth.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() == 2 && !s.toString().contains("/")) {
                    s.append("/");
                }
            }
        });
        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStartForResult.launch(new Intent(FluPayWithCard.this, VerificationActivity.class));

            }
        });
    }
    public void performCharge(){
        /*String cardNumber = layoutCardNumber.getEditText().getText().toString();
        String cardExpiry = layoutCardExpiryMonth.getEditText().getText().toString();
        String cvv = mCardCVV.getEditText().getText().toString();

        String[] cardExpiryArray = cardExpiry.split("/");
        int expiryMonth = Integer.parseInt(cardExpiryArray[0]);
        int expiryYear = Integer.parseInt(cardExpiryArray[1]);*/

        //clearErrors();
        String cardNumber = edtCardNo.getText().toString();
        String cardExpiryMonth = edtCardMonth.getText().toString();
        String cardExpiryYear = edtCardYear.getText().toString();
        String cardCVV = edtCardCVV.getText().toString();


        //PayStackCard payStackCard = new PayStackCard(cardNumber, cardExpiryMonth, cardExpiryYear, cardCVV);

        CardCharge ch = new CardCharge();

        ch.setCardno(cardNumber)
                .setCvv(cardCVV)
                .setAmount(String.valueOf(totalToday))
                .setExpiryyear(cardExpiryYear)
                .setExpirymonth(cardExpiryMonth)
                .setEmail(customerEmail)
                .setTxRef(refID)
                .setSuggested_auth("PIN")
                .setPin("3210");

        JSONObject charge= null;
        try {
            charge = ch.chargeMasterAndVerveCard();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(charge);
    }
    private void parseResponse(String refID) {
        String message = "Payment Successful - " + refID;
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        startActivity(new Intent(FluPayWithCard.this, LoginDirAct.class));
    }

    @Override
    public void onPaymentComplete(boolean success) {

    }

    @Override
    public void onPaymentError(int i, String errorMessage) {

    }
}