package com.skylightapp.Transactions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.annotation.SuppressLint;
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
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;
import com.twilio.Twilio;

import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import static com.skylightapp.Transactions.OurConfig.TWILIO_ACCOUNT_SID;
import static com.skylightapp.Transactions.OurConfig.TWILIO_AUTH_TOKEN;
import static java.lang.String.valueOf;

public class MobileMoneyTransfer extends AppCompatActivity {
    public final static String MOBILE_MONEY_ID = "KEY_EXTRA_MOBILEMONEY";
    AppCompatButton submitMM;
    AppCompatEditText phoneNo,amount, surName,firstName,email;
    SharedPreferences userPreferences;
    Gson gson;
    String json,selectedBank;
    double accountBalance;
    Profile userProfile;
    private String mParam1;
    private String mParam2;
    protected DBHelper dbHelper;
    //long customerID;
    Random ran;
    long customerId;
    int profileID;
    String momoOperator, momoPhoneNO, borrowerNo,borrowingId;
    Account acctOfCustomer,acc;
    long borrowingID;
    Customer mySelectedCustomer,fromAllCustomer;
    String customerNames;
    int length;
    SecureRandom random;
    long transactionID;
    String refID;
    String narration;
    long messageID;
    public static final String ACCOUNT_SID = System.getenv(TWILIO_ACCOUNT_SID);
    public static final String AUTH_TOKEN = System.getenv(TWILIO_AUTH_TOKEN);
    String transferDate,transferMessage, momoTransferDate;
    double unAvailableAmount,newBalance,availableBalance;
    long accountNo;
    int customerID;
    double amountRequested;
    String userName,payee;
    Account account;
    double balance,transferFee;
    AppCompatTextView feedback;
    private ArrayAdapter<Account> accountAdapter;
    String  currency,selectedCountry,payeeNames,payeeSurName,payeeEmailAddress,payeeFirstName;
    LinearLayoutCompat gh_layout,ug_layout,ken_layout,rwnd_layout,zambia_layout;
    String selectedOperator,response,profileSurname, profileUserName,customerAddress,profileEmail, profilePhoneNumber,profileFirstName;
    AppCompatSpinner spnUgander,spnRwnd,spnKenya,spnGhana,spnZambia,spnAcct, spnCountry,spnNetWork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mobile_money_transfer);

        userPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this);
        profileSurname = userPreferences.getString("SURNAME_KEY", "");
        profileFirstName = userPreferences.getString("FIRST_NAME_KEY", "");
        profilePhoneNumber = userPreferences.getString("PHONE_NUMBER_KEY", "");
        profileEmail = userPreferences.getString("EMAIL_ADDRESS", "");
        customerAddress = userPreferences.getString("ADDRESS_KEY", "");
        profileUserName = userPreferences.getString("USER_NAME", "");
        String userType = userPreferences.getString("machine", "");
        int userRole = userPreferences.getInt("Role", Integer.parseInt("Role"));
        ug_layout = findViewById(R.id.ug_layout);
        ken_layout = findViewById(R.id.kenya_layout);
        gh_layout = findViewById(R.id.gh_layout);
        rwnd_layout = findViewById(R.id.rwn_layout);
        zambia_layout = findViewById(R.id.zambia_layout);

        spnUgander = findViewById(R.id.ug_selected_operator);
        spnKenya = findViewById(R.id.kenya_selected_operator);
        spnGhana = findViewById(R.id.gh_selected_operator);
        spnRwnd = findViewById(R.id.rwn_selected_operator);
        spnZambia = findViewById(R.id.zambia_selected_operator);
        spnCountry = findViewById(R.id.selected_country);
        phoneNo = findViewById(R.id.MM_no);
        feedback = findViewById(R.id.feedbackTxt);
        amount = findViewById(R.id.amountMM);
        surName = findViewById(R.id.payeeMM);
        firstName = findViewById(R.id.firstNameMM);
        email = findViewById(R.id.payeeEmail);

        submitMM = findViewById(R.id.send_mobile_MM);
        submitMM.setEnabled(false);
        random =new SecureRandom();
        transactionID = random.nextInt((int) (Math.random() * 920) + 170);
        refID = "OurAppT/"+ random.nextInt((int) (Math.random() * 900000) + 100000);
        narration ="Our App Transfer";
        messageID = random.nextInt((int) (Math.random() * 9) + 1);
        transferDate = valueOf(Calendar.getInstance().getTime());
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        transferMessage="You have requested for the transfer of "+ currency + amountRequested +"from your Our App Acct. @"+transferDate;

        gson = new Gson();
        acctOfCustomer=new Account();
        mySelectedCustomer=new Customer();
        json = userPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);
        profileID = userProfile.getPID();
        userName = userProfile.getProfileLastName();
        dbHelper = new DBHelper(this);

        balance=account.getAccountBalance();
        /*ArrayAdapter statusAdapter = ArrayAdapter.createFromResource(
                this, R.array.status_of_package, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //status.setAdapter(statusAdapter);*/

        spnCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedCountry = valueOf(parent.getSelectedItem());
                if(selectedCountry.equalsIgnoreCase("Ghana")){
                    currency="GHS";
                    gh_layout.setVisibility(View.VISIBLE);
                    selectedOperator = valueOf(spnGhana.getSelectedItem());
                }
                if(selectedCountry.equalsIgnoreCase("Kenya")){
                    currency="KES";
                    ken_layout.setVisibility(View.VISIBLE);
                    selectedOperator = valueOf(spnKenya.getSelectedItem());
                }
                if(selectedCountry.equalsIgnoreCase("Uganda")){
                    currency="UGX";
                    ug_layout.setVisibility(View.VISIBLE);
                    selectedOperator = valueOf(spnUgander.getSelectedItem());
                }
                if(selectedCountry.equalsIgnoreCase("Zambia")){
                    currency="ZMW";
                    zambia_layout.setVisibility(View.VISIBLE);
                    selectedOperator = valueOf(spnZambia.getSelectedItem());

                }
                if(selectedCountry.equalsIgnoreCase("Rwanda")){
                    currency="RWF";
                    rwnd_layout.setVisibility(View.VISIBLE);
                    selectedOperator = valueOf(spnRwnd.getSelectedItem());
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //spnCountry.setOnItemSelectedListener(this);


        submitMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmMMTransfer();
            }
        });
    }

    private void confirmMMTransfer() {
        boolean hasNum = false;
        boolean correctAcctNo = false;
        //double amountToTransfer = 0.00;
        // momoOperator = String.valueOf(spnNetWork.getSelectedItem());
        momoPhoneNO = Objects.requireNonNull(phoneNo.getText()).toString();


        try {
            payeeSurName = Objects.requireNonNull(surName.getText()).toString();
        } catch (Exception e) {
            Toast.makeText(this, "Please enter a valid Surname", Toast.LENGTH_SHORT).show();
            surName.requestFocus();
        }
        try {
            payeeEmailAddress = Objects.requireNonNull(email.getText()).toString();
        } catch (Exception e) {
            Toast.makeText(this, "Please enter a valid Surname", Toast.LENGTH_SHORT).show();
            email.requestFocus();
        }
        try {
            payeeFirstName = Objects.requireNonNull(firstName.getText()).toString();

        } catch (Exception e) {
            Toast.makeText(this, "Please enter a valid First Name", Toast.LENGTH_SHORT).show();
            firstName.requestFocus();
        }
        try {
            momoPhoneNO = phoneNo.getText().toString();
            if (momoPhoneNO.length() > 10)
                correctAcctNo = true;
        } catch (Exception e) {
            Toast.makeText(this, "Please,enter a valid Mobile No.", Toast.LENGTH_SHORT).show();
            phoneNo.requestFocus();
        }
        if (momoOperator ==null) {
            Toast.makeText(this, "Please select a valid Operator", Toast.LENGTH_SHORT).show();


        }

        try {
            amountRequested = Double.parseDouble(Objects.requireNonNull(amount.getText()).toString());
            hasNum = true;

        } catch (Exception e) {
            Toast.makeText(this, "Please enter an amount to Transfer", Toast.LENGTH_SHORT).show();
        }

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        momoTransferDate = mdformat.format(calendar.getTime());
        accountBalance = account.getAccountBalance();
        unAvailableAmount = 0.1 * accountBalance;
        transferFee = 0.02 * amountRequested;
        availableBalance = accountBalance - unAvailableAmount;
        newBalance = availableBalance - amountRequested-transferFee;
        accountNo = account.getAwajimaAcctNo();
        customerNames = account.getAccountName();
        payeeNames = payeeSurName+","+payeeFirstName;
        customerID = mySelectedCustomer.getCusUID();
        profileID = userProfile.getPID();

        if (hasNum) {
            if (spnAcct.getSelectedItemPosition() < 0) {
                Toast.makeText(this, "The account does not exist", Toast.LENGTH_SHORT).show();
            } else if (amountRequested > availableBalance) {
                Toast.makeText(this, "The maximum amount to transfer is:" + currency+ availableBalance, Toast.LENGTH_SHORT).show();

            } else if (amountRequested > userProfile.getProfileAccounts().get(spnAcct.getSelectedItemPosition()).getAccountBalance()) {

                Toast.makeText(this, "The account," + " " + acc.toString() + " with balance" + currency+ valueOf(accountBalance) + "does not have enough balance to transfer NGN" + amountRequested, Toast.LENGTH_LONG).show();
                startDepositDialog();
            } else {
                TranXDAO tranXDAO = new TranXDAO(this);
                DBHelper applicationDb = new DBHelper(this);
                ArrayList<Transaction> transactions = tranXDAO.getAllTransactionAdmin();
                boolean loanTaken = false;
                for (int iTransaction = 0; iTransaction < transactions.size(); iTransaction++) {
                    if (valueOf(amountRequested).equals(valueOf((transactions.get(iTransaction).getRecordAmount()))) && momoTransferDate.equals(transactions.get(iTransaction).getTranxDate())) {

                        Toast.makeText(this, "a very similar Transfer with same date already exist!", Toast.LENGTH_LONG).show();
                    }
                    if (valueOf(amountRequested).isEmpty()) {
                        Toast.makeText(this, "Amount can not be empty!", Toast.LENGTH_LONG).show();

                    } else {
                        String title = "Loan Alert";
                        String status = "Requested";

                        Location location = null;
                        String details = customerNames + "just applied to transfer"+ currency + amountRequested;
                        String details1 = "A transfer request of" +currency+ amountRequested + "was initiated on your Our Coop App account" + valueOf(accountNo) + "on" + momoTransferDate;
                        double balanceAfterBorrowing = accountBalance - amountRequested;
                        acctOfCustomer.setAccountBalance(balanceAfterBorrowing);
                        userProfile.addPBorrowingTranx(acctOfCustomer, amountRequested);
                        mySelectedCustomer.addCusTransactions(amountRequested);
                        TimeLineClassDAO timeLineClassDAO= new TimeLineClassDAO(this);
                        //mySelectedCustomer.addLoans(borrowingID, amountRequested, borrowingDate, status, "", 0.00);
                        mySelectedCustomer.addCusTimeLine(title, details1);
                        Loan loan = new Loan();
                        Transaction.TRANSACTION_TYPE type = Transaction.TRANSACTION_TYPE.TRANSFER;
                        timeLineClassDAO.insertTimeLine(title, details, momoTransferDate, location);
                        /*applicationDb.overwriteAccount(userProfile, acctOfCustomer);
                        applicationDb.savNewT(profileID, customerID, accountNo, type,amountRequested, newBalance,momoOperator, Long.parseLong(momoPhoneNO),
                                momoOperator, refID, momoPhoneNO,
                                "", momoTransferDate);*/

                        MobileMoney mobileMoney = new MobileMoney();
                        MobilemoneyPayload mobilemoneyPayload = new MobilemoneyPayload();
                        //mobilemoneyPayload.setPBFPubKey(PBFPubKey);
                        mobilemoneyPayload.setCurrency(currency);
                        mobilemoneyPayload.setAmount(valueOf(amountRequested));
                        mobilemoneyPayload.setPhonenumber(momoPhoneNO);
                        mobilemoneyPayload.setEmail(payeeEmailAddress);
                        mobilemoneyPayload.setFirstname(payeeFirstName);
                        mobilemoneyPayload.setLastname(payeeSurName);
                        mobilemoneyPayload.setNetwork(momoOperator);
                        mobilemoneyPayload.setCountry(selectedCountry);
                        mobilemoneyPayload.setPayment_type("mobilemoney");
                        mobilemoneyPayload.setPublic_key(OurConfig.OUR_PUBLIC_KEY);
                        /*TransferPayload transferPayload = new TransferPayload();
                        Transfers transfers = new Transfers();
                        transferPayload.setAccount_bank(momoOperator);
                        transferPayload.setBeneficiary_name(momoPhoneNO);
                        transferPayload.setAccount_number(borrowerNo);
                        transferPayload.setAmount(String.valueOf(amountRequested));
                        transferPayload.setNarration("Skylight Mobile Money Transfer");
                        transferPayload.setReference(borrowingId);
                        transferPayload.setCurrency("NGN");
                        transferPayload.setSeckey(SkylightConfig.SECRET_KEY);
                        transfers.doTransfer(transferPayload);
                        String response = transfers.doTransfer(transferPayload);*/

                        try {
                            response = mobileMoney.domobilemoney(mobilemoneyPayload);
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        }


                        Toast.makeText(this, "Transfer of " +currency + String.format(Locale.getDefault(), "%.2f", amountRequested) + " was successful.", Toast.LENGTH_SHORT).show();

                        //applicationDb.saveNewTransaction(profileID, customerID, accountNo, type, amountRequested, balanceAfterBorrowing, momoOperator, Long.parseLong(borrowerNo), momoPhoneNO, borrowingId, borrowingId, response, momoTransferDate);

                    }
                }
            }

            //int sendingAccIndex = spnReceivingAccount.getSelectedItemPosition();

        }
    }
    public void startDepositDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Our Coop. App Money Transfer interim option");
        builder.setItems(new CharSequence[]
                        {"Deposit money to your E-Wallet","Go back Home"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Toast.makeText(builder.getContext(), "Deposit option selected", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), OurDepositMoneyAct.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                                break;
                            case 1:
                                Toast.makeText(builder.getContext(), "Home movement option selected", Toast.LENGTH_SHORT).show();
                                break;

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
                        Intent intent = new Intent(getApplicationContext(), MobileMoneyTransfer.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });

        builder.create().show();


    }
}