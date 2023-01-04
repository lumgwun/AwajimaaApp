package com.skylightapp.Admins;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Database.TimeLineClassDAO;
import com.skylightapp.Database.TranXDAO;
import com.skylightapp.R;
import com.skylightapp.Transactions.TransferPayload;
import com.skylightapp.Transactions.Transfers;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.skylightapp.Transactions.OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY;
import static com.skylightapp.Transactions.OurConfig.TWILIO_ACCOUNT_SID;
import static com.skylightapp.Transactions.OurConfig.TWILIO_AUTH_TOKEN;
import static java.lang.String.valueOf;

public class LoanApprovalAct extends AppCompatActivity {
    TextView txtId,txtAmt,txtBurea,txtDate,txtOurpose;
    private SwitchCompat swtchApproval;
    Bundle approvalBundle;
    Loan loan;
    double loanAmt;
    Customer loanCustomer;
    String customerPhone,bank,refID,paymentDate,surName,firstName,bankName, accountbank,acctBank,acctName;
    private  long acctNumber;
    int loanAcctID;
    private int mCount = 0;
    private  ProgressDialog progressDialog;
    private  double initialAcctBalance;
    private DBHelper dbHelper;
    private  Profile userProfile;
    int virtualAccountNumber ;
    int transactionID;
    int profileID;
    int messageID;
    int customerID;
    private  SecureRandom random;
    private TimeLineClassDAO timeLineClassDAO;
    private TranXDAO tranXDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_loan_approval);
        loanCustomer= new Customer();
        userProfile= new Profile();
        random = new SecureRandom();
        timeLineClassDAO= new TimeLineClassDAO(this);
        tranXDAO=new TranXDAO(this);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        paymentDate = mdformat.format(calendar.getTime());
        Twilio.init("ACb6e4c829a5792a4b744a3e6bd1cf2b4e", "0d5cbd54456dd0764786db0c37212578");
        approvalBundle= this.getIntent().getExtras();
        dbHelper= new DBHelper(this);
        loan = approvalBundle.getParcelable("Loan");
        txtId=findViewById(R.id.loanTextID);
        txtAmt=findViewById(R.id.loanTextAmt);
        txtBurea=findViewById(R.id.loanTextBurea);
        txtDate=findViewById(R.id.loanTextDate);
        txtOurpose=findViewById(R.id.loanTextPurpose);
        swtchApproval=findViewById(R.id.LoanSwitch);
        virtualAccountNumber = random.nextInt((int) (Math.random() * 900000) + 100000);
        transactionID = random.nextInt((int) (Math.random() * 900) + 1001);
        messageID = random.nextInt((int) (Math.random() * 900) + 1110);
        refID="SkylightLoan"+""+paymentDate+"/"+ transactionID;


        if(loan !=null) {
            loanAmt = loan.getAmount1();
            loanCustomer = loan.getLoan_customer();
            customerPhone = loan.getLoan_customer().getCusPhoneNumber();
            loanAcctID = loan.getLoan_account().getAwajimaAcctNo();
            acctBank = loan.getLoanBank();
            acctName = loan.getLoanAcctName();
            acctNumber = loan.getLoanId();
            userProfile = loan.getLoan_profile();

            initialAcctBalance = loan.getLoan_account().getAccountBalance();
            if(userProfile !=null){
                profileID = userProfile.getPID();

            }

            if(loanCustomer !=null){
                customerID = loanCustomer.getCusUID();
                firstName = loanCustomer.getCusFirstName();
                surName = loanCustomer.getCusSurname();

            }



            if (acctBank != null) {
                if (acctBank.equalsIgnoreCase("ACCESS BANK PLC")) {
                    accountbank = "044";
                }
                if (acctBank.equalsIgnoreCase("First Bank of Nigeria")) {
                    accountbank = "011";
                }
                if (acctBank.equalsIgnoreCase("UBA PLC")) {
                    accountbank = "033";
                }
                if (acctBank.equalsIgnoreCase("Heritage")) {
                    accountbank = "030";
                }
                if (acctBank.equalsIgnoreCase("Keystone Bank")) {
                    accountbank = "082";
                }
                if (acctBank.equalsIgnoreCase("Skye Bank")) {
                    accountbank = "076";
                }

                if (acctBank.equalsIgnoreCase("Sterling Bank")) {
                    accountbank = "232";
                }

                if (acctBank.equalsIgnoreCase("Union Bank")) {
                    accountbank = "032";
                }
                if (acctBank.equalsIgnoreCase("GTBank Plc")) {
                    accountbank = "058";
                }
                if (acctBank.equalsIgnoreCase("FCMB")) {
                    accountbank = "214";
                }
                if (acctBank.equalsIgnoreCase("TrustBond")) {
                    accountbank = "523";
                }
                if (acctBank.equalsIgnoreCase("SunTrust Bank")) {
                    accountbank = "100";
                }
                if (acctBank.equalsIgnoreCase("Diamond Bank")) {
                    accountbank = "063";
                }
                if (acctBank.equalsIgnoreCase("GT MOBILE MONEY")) {
                    accountbank = "315";
                }
                if (acctBank.equalsIgnoreCase("FET")) {
                    accountbank = "314";
                }
                if (acctBank.equalsIgnoreCase("Mkudi")) {
                    accountbank = "313";
                }
                if (acctBank.equalsIgnoreCase("FSDH")) {
                    accountbank = "601";
                }
                if (acctBank.equalsIgnoreCase("Coronation Merchant Bank")) {
                    accountbank = "559";
                }
                if (acctBank.equalsIgnoreCase("Enterprise Bank")) {
                    accountbank = "084";
                }
                if (acctBank.equalsIgnoreCase("Wema Bank")) {
                    accountbank = "035";
                }
                if (acctBank.equalsIgnoreCase("Parralex")) {
                    accountbank = "526";
                }
                if (acctBank.equalsIgnoreCase("Pagatech")) {
                    accountbank = "327";
                }
                if (acctBank.equalsIgnoreCase("Stanbic IBTC Bank")) {
                    accountbank = "221";
                }
                if (acctBank.equalsIgnoreCase("Fidelity Mobile")) {
                    accountbank = "318";
                }
                if (acctBank.equalsIgnoreCase("EcoMobile")) {
                    accountbank = "307";
                }
                if (acctBank.equalsIgnoreCase("Ecobank Plc")) {
                    accountbank = "050";
                }
                if (acctBank.equalsIgnoreCase("JAIZ Bank")) {
                    accountbank = "301";
                }
                if (acctBank.equalsIgnoreCase("Access Money")) {
                    accountbank = "323";
                }
                if (acctBank.equalsIgnoreCase("Unity Bank")) {
                    accountbank = "215";
                }
                if (acctBank.equalsIgnoreCase("CitiBank")) {
                    accountbank = "023";
                }
                if (acctBank.equalsIgnoreCase("Fidelity Bank")) {
                    accountbank = "070";
                }
                if (acctBank.equalsIgnoreCase("eTranzact")) {
                    accountbank = "306";
                }
                if (acctBank.equalsIgnoreCase("Standard Chartered Bank")) {
                    accountbank = "068";
                }
                if (acctBank.equalsIgnoreCase("Zenith Bank")) {
                    accountbank = "057";
                }
                if (acctBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")) {
                    accountbank = "402";
                }
                if (acctBank.equalsIgnoreCase("Sterling Mobile")) {
                    accountbank = "326";
                }
                if (acctBank.equalsIgnoreCase("FBNMobile")) {
                    accountbank = "309";
                }
                if (acctBank.equalsIgnoreCase("Stanbic Mobile Money")) {
                    accountbank = "304";
                }
                if (acctBank.equalsIgnoreCase("TCF MFB")) {
                    accountbank = "90115";
                }
                if (bankName.equalsIgnoreCase("ASO Savings and Loans")) {
                    accountbank = "401";
                }
                if (acctBank.equalsIgnoreCase("Cellulant")) {
                    accountbank = "317";
                }
                if (acctBank.equalsIgnoreCase("NIP Virtual Bank")) {
                    accountbank = "999";
                }
                if (acctBank.equalsIgnoreCase("Paycom")) {
                    accountbank = "305";
                }
                if (acctBank.equalsIgnoreCase("NPF MicroFinance Bank")) {
                    accountbank = "552";
                }
                if (acctBank.equalsIgnoreCase("Imperial Homes Mortgage Bank")) {
                    accountbank = "415";
                }
                if (acctBank.equalsIgnoreCase("Covenant Microfinance Bank")) {
                    accountbank = "551";
                }
                if (acctBank.equalsIgnoreCase("SafeTrust Mortgage Bank")) {
                    accountbank = "403";
                }
                if (acctBank.equalsIgnoreCase("ChamsMobile")) {
                    accountbank = "303";
                }
                if (acctBank.equalsIgnoreCase("ZenithMobile")) {
                    accountbank = "322";
                }
                if (acctBank.equalsIgnoreCase("PayAttitude Online")) {
                    accountbank = "329";
                }
                if (acctBank.equalsIgnoreCase("Fortis Microfinance Bank")) {
                    accountbank = "501";
                }
                if (acctBank.equalsIgnoreCase("VTNetworks")) {
                    accountbank = "320";
                }
                if (acctBank.equalsIgnoreCase("TeasyMobile")) {
                    accountbank = "319";
                }
                if (acctBank.equalsIgnoreCase("MoneyBox")) {
                    accountbank = "325";
                }
                if (acctBank.equalsIgnoreCase("Hedonmark")) {
                    accountbank = "324";
                }
                if (acctBank.equalsIgnoreCase("Eartholeum")) {
                    accountbank = "302";
                }
                if (acctBank.equalsIgnoreCase("ReadyCash (Parkway)")) {
                    accountbank = "311";
                }
                if (acctBank.equalsIgnoreCase("Omoluabi Mortgage Bank")) {
                    accountbank = "990";
                }
                if (acctBank.equalsIgnoreCase("TagPay")) {
                    accountbank = "328";
                }
                if (acctBank.equalsIgnoreCase("FortisMobile")) {
                    accountbank = "308";
                }
                if (acctBank.equalsIgnoreCase("Page MFBank")) {
                    accountbank = "560";
                }
                if (acctBank.equalsIgnoreCase("Pagatech")) {
                    accountbank = "327";
                }


            }


            swtchApproval.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    doDialog(loanAmt, customerPhone, loanAcctID, bank, acctName, acctNumber, initialAcctBalance, accountbank, loanCustomer,customerID,messageID,transactionID,surName,firstName,profileID,acctBank);

                }
            });
        }
    }

    private void doDialog(double loanAmt, String customerPhone, int loanAcctID, String bank, String acctName, long acctNumber, double initialAcctBalance, String accountbank, Customer loanCustomer, int customerID, int messageID, int transactionID, String surName, String firstName, int profileID, String acctBank) {
        swtchApproval=findViewById(R.id.LoanSwitch);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure that you want to approve this LOAN?");
        builder.setIcon(R.drawable.ic_icon2);
        builder.setItems(new CharSequence[]
                        {"Yes", "No"},
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                mCount++;
                                swtchApproval.setEnabled(false);
                                swtchApproval.setText("Approval given");
                                approvalMessage(loanAmt, customerPhone);
                                doSendMoney(loanAmt, customerPhone, loanAcctID, bank, acctName, acctNumber, initialAcctBalance, accountbank, loanCustomer,customerID,messageID,transactionID,surName,firstName,profileID,acctBank);
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                swtchApproval.setEnabled(true);
                                break;
                        }

                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mCount++;
                        swtchApproval.setEnabled(false);
                        swtchApproval.setText("Approval given");
                        approvalMessage(loanAmt, customerPhone);
                        //doSendMoney(loanAmt, customerPhone, loanAcctID, bank, acctName, acctNumber, initialAcctBalance, accountbank, loanCustomer,customerID,messageID,transactionID,surName,firstName,profileID,acctBank);

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        swtchApproval.setEnabled(true);
                    }
                });

        builder.create().show();

    }

    private void doSendMoney(double loanAmt, String customerPhone, int loanAcctID, String bank, String acctName, long acctNumber, double initialAcctBalance, String accountbank, Customer loanCustomer, int customerID, int messageID, int transactionID, String surName, String firstName, int profileID, String acctBank) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        paymentDate = mdformat.format(calendar.getTime());
        String narration="Awajima Loan disbursement!";
        String paymentMessage="Your Loan of NGN"+loanAmt+""+"has been approved by the Awajima Admin,and you will be credited now";


        @SuppressLint("StaticFieldLeak")
        class SendPostReqAsyncTask extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(LoanApprovalAct.this);
                progressDialog.setMessage("Processing Loan Disbursement");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                try {

                    Transfers transfers = new Transfers();
                    TransferPayload transferPayload = new TransferPayload();
                    transferPayload.setAccount_bank(accountbank);
                    transferPayload.setAccount_number(valueOf(acctNumber));
                    transferPayload.setAmount(valueOf(loanAmt));
                    transferPayload.setSeckey(AWAJIMA_PAYSTACK_SECRET_KEY);
                    transferPayload.setNarration(narration);
                    transferPayload.setCurrency("NGN");
                    transferPayload.setReference(refID);
                    Location location= null;
                    transferPayload.setBeneficiary_name(acctName);
                   String response = transfers.doTransfer(transferPayload);
                    String tittle="Payment Request Alert!";
                    timeLineClassDAO= new TimeLineClassDAO(LoanApprovalAct.this);
                    tranXDAO=new TranXDAO(LoanApprovalAct.this);
                    double newBalance =initialAcctBalance-loanAmt;
                    Transaction.TRANSACTION_TYPE type= Transaction.TRANSACTION_TYPE.BORROWING;
                    //dbHelper.insertMessage(profileID,customerID,messageID,paymentMessage,"Admin",paymentDate);
                    timeLineClassDAO.insertTimeLine(tittle,paymentMessage,paymentDate,location);
                    loanCustomer.addCusTransactions(loanAmt);
                    String acctDetails=acctBank+","+acctName+","+acctNumber;
                    loanCustomer.getCusAccount().setAccountBalance(newBalance);
                    userProfile.addPTransaction(transactionID,surName,firstName,customerPhone,loanAmt, String.valueOf(loanAcctID),acctDetails,paymentDate,"Loan Disbursement");
                    tranXDAO.savePaymentTransactionP(profileID,customerID,type,response);
                    approvalMessage(loanAmt,customerPhone);

                } catch (Exception e) {
                    Log.d("InputStream", e.getLocalizedMessage());
                }


                return null;
            }

            @Override
            protected void onPostExecute(String response) {
                super.onPostExecute(response);
                progressDialog.dismiss();

            }
        }
    }


    private  void approvalMessage(double loanAmt,String customerPhone){
        String paymentMessage="Your Loan of NGN"+loanAmt+""+"has been approved by the Awajima Admin,and you will be credited now";
        Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(customerPhone),
                new com.twilio.type.PhoneNumber("234"+customerPhone),
                paymentMessage)
                .create();
    }
}