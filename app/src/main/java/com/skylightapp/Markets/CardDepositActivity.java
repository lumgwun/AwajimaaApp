package com.skylightapp.Markets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.LoginDirAct;
import com.skylightapp.R;
import com.skylightapp.Transactions.Card;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import org.json.JSONException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.Transaction;
import co.paystack.android.exceptions.ExpiredAccessCodeException;
import co.paystack.android.model.Charge;

import static com.twilio.example.ValidationExample.ACCOUNT_SID;
import static com.twilio.example.ValidationExample.AUTH_TOKEN;


public class CardDepositActivity extends AppCompatActivity {
    public final static String CARD_DEPOSIT_ID = "KEY_EXTRA_CARD_DEPOSIT";
    SecureRandom random;
    AppCompatSpinner spnAcct;
    long depositID;
    Customer customer;
    private ArrayList<Account> accountArrayList;
    private ArrayAdapter<Account> accountAdapter;
    Profile userProfile;
    Context context;
    long customerId;
    long accountNo;
    long customerID;
    double amountContributedSoFar,accountBalance;
    double packageGrantTotal,amountRemaining,moneySaved;
    long profileID, packageIdOld;
    AppCompatEditText amountToDeposit,cardNumber, cardCVV, cardMonthDate,cardYearDate;
    AppCompatButton depositBtn;
    AppCompatTextView mTextBackendMessage,mTextError;
    Account account;

    ProgressDialog dialog;
    private Charge charge;
    private Transaction transaction;
    private  double depositAmount,newAccountBalance;
    private SharedPreferences userPreferences;
    private Gson gson;
    private String json;
    String  profileSurname, profileUserName,machine,profilePhone,profileEmailAddress,profileFirstName,textMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_card_deposit);
        PaystackSdk.initialize(this);

        if (AUTH_TOKEN != null) {
            if (ACCOUNT_SID != null) {
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            }
        }
        userPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        profileID=userProfile.getPID();
        account= new Account();

        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        profileSurname = userPreferences.getString("SURNAME_KEY", "");
        profileFirstName = userPreferences.getString("FIRST_NAME_KEY", "");
        profilePhone = userPreferences.getString("PHONE_NUMBER_KEY", "");
        profileEmailAddress = userPreferences.getString("EMAIL_ADDRESS", "");
        profileUserName = userPreferences.getString("USER_NAME", "");
        int userRole = userPreferences.getInt("Role", Integer.parseInt("Role"));
        machine=userPreferences.getString("machine","");

        amountToDeposit = findViewById(R.id.amountToDeposit4);
        cardNumber = findViewById(R.id.card_number4);
        cardCVV = findViewById(R.id._card_cvv);
        cardMonthDate = findViewById(R.id.card_expiry);
        cardYearDate = findViewById(R.id.card_expiry_year);
        depositBtn = findViewById(R.id.btn_make_deposit);
        spnAcct = findViewById(R.id.selected_deposit_account4);
        mTextError = findViewById(R.id.errorDeposit3);
        accountAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userProfile.getProfileAccounts());
        accountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAcct.setAdapter(accountAdapter);
        spnAcct.setSelection(0);
        account = (Account) spnAcct.getSelectedItem();
        accountNo= account.getAwajimaAcctNo();
        accountBalance=account.getAccountBalance();
        depositID = random.nextInt((int) (Math.random() * 99) + 11);
        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    startAFreshCharge(true);
                } catch (Exception e) {
                    CardDepositActivity.this.mTextError.setText(String.format("An error occurred while charging card: %s %s", e.getClass().getSimpleName(), e.getMessage()));

                }
            }
        });

    }
    private void startAFreshCharge(boolean local) {
        try {
            depositAmount = Double.parseDouble(Objects.requireNonNull(amountToDeposit.getText()).toString().trim());
        } catch (Exception ignored) {
        }
        charge = new Charge();
        charge.setCard(loadCardFromForm());

        dialog = new ProgressDialog(CardDepositActivity.this);
        dialog.setMessage("Performing deposit... please wait");
        dialog.show();

        if (local) {
            charge.setAmount(Integer.parseInt(String.valueOf(depositAmount)));
            charge.setEmail(profileEmailAddress);
            charge.setReference("SkDeposit" +depositID+ Calendar.getInstance().getTimeInMillis());
            try {
                charge.putCustomField("Charged From", "Awajima Mobile App");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            chargeCard();
        } else {

        }
    }

    private Card loadCardFromForm() {
        Card card;

        String cardNum = Objects.requireNonNull(cardNumber.getText()).toString().trim();
        card = (Card) new Card.Builder(cardNum, 0, 0, "").build();
        String cvc = Objects.requireNonNull(cardCVV.getText()).toString().trim();
        card.setCvc(cvc);

        String sMonth = Objects.requireNonNull(cardMonthDate.getText()).toString().trim();
        int month = 0;
        try {
            month = Integer.parseInt(sMonth);
        } catch (Exception ignored) {
        }

        card.setExpiryMonth(month);

        String sYear = Objects.requireNonNull(cardYearDate.getText()).toString().trim();
        int year = 0;
        try {
            year = Integer.parseInt(sYear);
        } catch (Exception ignored) {
        }
        card.setExpiryYear(year);

        return card;
    }

    @Override
    public void onPause() {
        super.onPause();

        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    private void chargeCard() {
        transaction = null;
        String depositText="Your Deposit of N"+depositAmount+"into your Awajima acct"+accountNo+"was successful";
        String depositTextFailed="Your Deposit of N"+depositAmount+"into your Awajima acct"+accountNo+"failed";
        PaystackSdk.chargeCard(CardDepositActivity.this, charge, new Paystack.TransactionCallback() {
            @Override
            public void onSuccess(Transaction transaction) {
                dismissDialog();
                newAccountBalance=accountBalance+depositAmount;
                account.setAccountBalance(newAccountBalance);
                CardDepositActivity.this.transaction = transaction;
                mTextError.setText(" ");
                Toast.makeText(CardDepositActivity.this, transaction.getReference(), Toast.LENGTH_LONG).show();
                updateTextViews();
                Intent intent = new Intent(CardDepositActivity.this, LoginDirAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                if (ACCOUNT_SID != null) {
                    if (AUTH_TOKEN != null) {
                        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                    }
                }
                Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(profilePhone),
                        new com.twilio.type.PhoneNumber("234" + profilePhone),
                        depositText).create();
            }

            @Override
            public void beforeValidate(Transaction transaction) {
                CardDepositActivity.this.transaction = transaction;
                Toast.makeText(CardDepositActivity.this, transaction.getReference(), Toast.LENGTH_LONG).show();
                updateTextViews();
            }

            @Override
            public void onError(Throwable error, Transaction transaction) {
                CardDepositActivity.this.transaction = transaction;
                Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(profilePhone),
                        new com.twilio.type.PhoneNumber("234" + profilePhone),
                        depositTextFailed).create();
                if (error instanceof ExpiredAccessCodeException) {
                    CardDepositActivity.this.startAFreshCharge(false);
                    CardDepositActivity.this.chargeCard();
                    return;
                }

                dismissDialog();

                if (transaction.getReference() != null) {
                    Toast.makeText(CardDepositActivity.this, transaction.getReference() + " concluded with error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    mTextError.setText(String.format("%s  concluded with error: %s %s", transaction.getReference(), error.getClass().getSimpleName(), error.getMessage()));
                } else {
                    Toast.makeText(CardDepositActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
                updateTextViews();
            }

        });
    }

    private void dismissDialog() {
        if ((dialog != null) && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    private void updateTextViews() {
        if (transaction.getReference() != null) {
            mTextError.setText(String.format("Reference: %s", transaction.getReference()));
        } else {
            mTextError.setText("No Deposits");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_main_menu, menu);
        return true;
    }

    private boolean isEmpty(String s) {
        return s == null || s.length() < 1;
    }

}