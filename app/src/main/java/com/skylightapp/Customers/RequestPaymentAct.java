package com.skylightapp.Customers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.AcctDAO;
import com.skylightapp.Database.AdminBalanceDAO;
import com.skylightapp.Database.AwardDAO;
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
import com.skylightapp.R;
import com.skylightapp.UserPrefActivity;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


import static com.skylightapp.Classes.Customer.CUSTOMER_OFFICE;
import static com.skylightapp.Transactions.OurConfig.TWILIO_ACCOUNT_SID;
import static com.skylightapp.Transactions.OurConfig.TWILIO_AUTH_TOKEN;
import static java.lang.String.valueOf;

public class RequestPaymentAct extends AppCompatActivity {
    public static final String CASHOUT_EXTRA_KEY = "RequestPaymentActivity.CASHOUT_EXTRA_KEY";
    SharedPreferences sharedPreferences;
    SecureRandom random =new SecureRandom();
    public Bundle getBundle = null;
    String acctBank, bankCode, bankName,officeChoice;
    long acctNo;
    double amount;
    private Customer customer;
    int tranxPayoutID = random.nextInt((int) (Math.random() * 220) + 170);
    String refID = "SkyLightT/"+ random.nextInt((int) (Math.random() * 12720) + 100000);
    String narration ="Skylight requested Payment";
    int messageID = random.nextInt((int) (Math.random() * 9) + 1);
    String requestDate;
    String response;
    String paymentMessage;
    double amountRequested;
    String customerPhone,firstName,surName;
    String customerEmail, tittle;
    long customerId,e_walletNo;
    double amount_Requested;
    int profileID;
    String sender = "Your Skylight Coop." ;
    public static final String ACCOUNT_SID = System.getenv(TWILIO_ACCOUNT_SID);
    public static final String AUTH_TOKEN = System.getenv(TWILIO_AUTH_TOKEN);
    Message message;
    Gson gson;
    int packageID;
    double grandTotal,acctBalance,amountWithdrawable;
    double amountContributedSoFar;
    double skylightFee=0.032;
    String json,jsonCustomer, bankPrefCat,accountBankCode,jsonBankAccount, acctBankNo,paymentResponse;
    Profile userProfile;
    Account skylightAccount;
    int customerID;
    String officeBranch,cusBankAcct,customerName;
    private Calendar calendar;
    private static final String PREF_NAME = "skylight";
    private SODAO sodao;
    private TranXDAO tranXDAO;
    private MessageDAO messageDAO;
    private LoanDAO loanDAO;
    private AcctDAO acctDAO;
    private CodeDAO codeDAO;
    private PaymDocDAO paymDocDAO;
    private CusDAO cusDAO;
    private PaymentCodeDAO paymentCodeDAO;
    private ProfDAO profileDao;
    private TCashDAO cashDAO;
    private TReportDAO tReportDAO;
    private PaymentDAO paymentDAO;
    private AdminBalanceDAO adminBalanceDAO;
    private TimeLineClassDAO timeLineClassDAO;
    private GrpProfileDAO grpProfileDAO;
    private StocksDAO stocksDAO;
    private WorkersDAO workersDAO;
    private StockTransferDAO stockTransferDAO;
    private OfficeBranchDAO officeBranchDAO;
    private BirthdayDAO birthdayDAO;
    private TransactionGrantingDAO grantingDAO;
    private AwardDAO awardDAO;

    private  Transaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_request_payment);
        DBHelper dbHelper = new DBHelper(this);
        userProfile= new Profile();
        transaction= new Transaction();
        customer= new Customer();
        getBundle=new Bundle();
        bankName=null;
        workersDAO= new WorkersDAO(this);
        awardDAO= new AwardDAO(this);
        grantingDAO= new TransactionGrantingDAO(this);
        stocksDAO= new StocksDAO(this);
        cusDAO= new CusDAO(this);
        birthdayDAO= new BirthdayDAO(this);
        officeBranchDAO= new OfficeBranchDAO(this);
        stockTransferDAO= new StockTransferDAO(this);

        paymentCodeDAO= new PaymentCodeDAO(this);
        profileDao= new ProfDAO(this);
        cashDAO= new TCashDAO(this);
        paymDocDAO= new PaymDocDAO(this);
        tReportDAO= new TReportDAO(this);
        paymentDAO= new PaymentDAO(this);
        adminBalanceDAO= new AdminBalanceDAO(this);
        timeLineClassDAO= new TimeLineClassDAO(this);
        grpProfileDAO= new GrpProfileDAO(this);

        sodao= new SODAO(this);
        tranXDAO= new TranXDAO(this);
        sodao= new SODAO(this);
        messageDAO= new MessageDAO(this);
        loanDAO= new LoanDAO(this);

        codeDAO= new CodeDAO(this);
        acctDAO= new AcctDAO(this);
        acctBankNo=null;
        acctBank=null;
        accountBankCode=null;
        skylightAccount=new Account();
        calendar = Calendar.getInstance();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        gson = new Gson();
        json = sharedPreferences.getString("LastProfileUsed", "");
        userProfile = gson.fromJson(json, Profile.class);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(userProfile !=null){
            customer=userProfile.getProfileCus();
        }
        if(customer !=null){
            customerID =customer.getCusUID();
            customerPhone=customer.getCusPhoneNumber();
            customerEmail= customer.getCusEmailAddress();
            firstName=customer.getCusFirstName();
            surName=customer.getCusSurname();
            officeBranch=customer.getCusOfficeBranch();
            skylightAccount=customer.getCusAccount();

        }

        if(userProfile !=null){
            profileID =userProfile.getPID();

        }
        if(skylightAccount !=null){
            acctBalance=skylightAccount.getAccountBalance();
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        requestDate = mdformat.format(calendar.getTime());

        //Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        paymentMessage="You have requested for the withdrawal of NGN"+ amountRequested +"from your Skylight Acct. @"+requestDate;

        if(acctBank !=null){
            acctBank=sharedPreferences.getString("Bank","");


        }
        if(acctBank !=null){
            acctBank =sharedPreferences.getString("Bank","");
            if(acctBank.equalsIgnoreCase("ACCESS BANK PLC")){
                accountBankCode ="044";
            }
            if(acctBank.equalsIgnoreCase("First Bank of Nigeria")){
                accountBankCode ="011";
            }
            if(acctBank.equalsIgnoreCase("UBA PLC")){
                accountBankCode ="033";
            }
            if(acctBank.equalsIgnoreCase("Heritage")){
                accountBankCode ="030";
            }
            if(acctBank.equalsIgnoreCase("Keystone Bank")){
                accountBankCode ="082";
            }
            if(acctBank.equalsIgnoreCase("Skye Bank")){
                accountBankCode ="076";
            }

            if(acctBank.equalsIgnoreCase("Sterling Bank")){
                accountBankCode ="232";
            }

            if(acctBank.equalsIgnoreCase("Union Bank")){
                accountBankCode ="032";
            }
            if(acctBank.equalsIgnoreCase("GTBank Plc")){
                accountBankCode ="058";
            }
            if(acctBank.equalsIgnoreCase("FCMB")){
                accountBankCode ="214";
            }
            if(acctBank.equalsIgnoreCase("TrustBond")){
                accountBankCode ="523";
            }
            if(acctBank.equalsIgnoreCase("SunTrust Bank")){
                accountBankCode ="100";
            }
            if(acctBank.equalsIgnoreCase("Diamond Bank")){
                accountBankCode ="063";
            }
            if(acctBank.equalsIgnoreCase("GT MOBILE MONEY")){
                accountBankCode ="315";
            }
            if(acctBank.equalsIgnoreCase("FET")){
                accountBankCode ="314";
            }
            if(acctBank.equalsIgnoreCase("Mkudi")){
                accountBankCode ="313";
            }
            if(acctBank.equalsIgnoreCase("FSDH")){
                accountBankCode ="601";
            }
            if(acctBank.equalsIgnoreCase("Coronation Merchant Bank")){
                accountBankCode ="559";
            }
            if(acctBank.equalsIgnoreCase("Enterprise Bank")){
                accountBankCode ="084";
            }
            if(acctBank.equalsIgnoreCase("Wema Bank")){
                accountBankCode ="035";
            }
            if(acctBank.equalsIgnoreCase("Parralex")){
                accountBankCode ="526";
            }
            if(acctBank.equalsIgnoreCase("Pagatech")){
                accountBankCode ="327";
            }
            if(acctBank.equalsIgnoreCase("Stanbic IBTC Bank")){
                accountBankCode ="221";
            }
            if(acctBank.equalsIgnoreCase("Fidelity Mobile")){
                accountBankCode ="318";
            }
            if(acctBank.equalsIgnoreCase("EcoMobile")){
                accountBankCode ="307";
            }
            if(acctBank.equalsIgnoreCase("Ecobank Plc")){
                accountBankCode ="050";
            }
            if(acctBank.equalsIgnoreCase("JAIZ Bank")){
                accountBankCode ="301";
            }
            if(acctBank.equalsIgnoreCase("Access Money")){
                accountBankCode ="323";
            }
            if(acctBank.equalsIgnoreCase("Unity Bank")){
                accountBankCode ="215";
            }
            if(acctBank.equalsIgnoreCase("CitiBank")){
                accountBankCode ="023";
            }
            if(acctBank.equalsIgnoreCase("Fidelity Bank")){
                accountBankCode ="070";
            }
            if(acctBank.equalsIgnoreCase("eTranzact")){
                accountBankCode ="306";
            }
            if(acctBank.equalsIgnoreCase("Standard Chartered Bank")){
                accountBankCode ="068";
            }
            if(acctBank.equalsIgnoreCase("Zenith Bank")){
                accountBankCode ="057";
            }
            if(acctBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")){
                accountBankCode ="402";
            }
            if(acctBank.equalsIgnoreCase("Sterling Mobile")){
                accountBankCode ="326";
            }
            if(acctBank.equalsIgnoreCase("FBNMobile")){
                accountBankCode ="309";
            }
            if(acctBank.equalsIgnoreCase("Stanbic Mobile Money")){
                accountBankCode ="304";
            }
            if(acctBank.equalsIgnoreCase("TCF MFB")){
                accountBankCode ="90115";
            }
            if(bankName.equalsIgnoreCase("ASO Savings and Loans")){
                accountBankCode ="401";
            }
            if(acctBank.equalsIgnoreCase("Cellulant")){
                accountBankCode ="317";
            }
            if(acctBank.equalsIgnoreCase("NIP Virtual Bank")){
                accountBankCode ="999";
            }
            if(acctBank.equalsIgnoreCase("Paycom")){
                accountBankCode ="305";
            }
            if(acctBank.equalsIgnoreCase("NPF MicroFinance Bank")){
                accountBankCode ="552";
            }
            if(acctBank.equalsIgnoreCase("Imperial Homes Mortgage Bank")){
                accountBankCode ="415";
            }
            if(acctBank.equalsIgnoreCase("Covenant Microfinance Bank")){
                accountBankCode ="551";
            }
            if(acctBank.equalsIgnoreCase("SafeTrust Mortgage Bank")){
                accountBankCode ="403";
            }
            if(acctBank.equalsIgnoreCase("ChamsMobile")){
                accountBankCode ="303";
            }
            if(acctBank.equalsIgnoreCase("ZenithMobile")){
                accountBankCode ="322";
            }
            if(acctBank.equalsIgnoreCase("PayAttitude Online")){
                accountBankCode ="329";
            }
            if(acctBank.equalsIgnoreCase("Fortis Microfinance Bank")){
                accountBankCode ="501";
            }
            if(acctBank.equalsIgnoreCase("VTNetworks")){
                accountBankCode ="320";
            }
            if(acctBank.equalsIgnoreCase("TeasyMobile")){
                accountBankCode ="319";
            }
            if(acctBank.equalsIgnoreCase("MoneyBox")){
                accountBankCode ="325";
            }
            if(acctBank.equalsIgnoreCase("Hedonmark")){
                accountBankCode ="324";
            }
            if(acctBank.equalsIgnoreCase("Eartholeum")){
                accountBankCode ="302";
            }
            if(acctBank.equalsIgnoreCase("ReadyCash (Parkway)")){
                accountBankCode ="311";
            }
            if(acctBank.equalsIgnoreCase("Omoluabi Mortgage Bank")){
                accountBankCode ="990";
            }
            if(acctBank.equalsIgnoreCase("TagPay")){
                accountBankCode ="328";
            }
            if(acctBank.equalsIgnoreCase("FortisMobile")){
                accountBankCode ="308";
            }
            if(acctBank.equalsIgnoreCase("Page MFBank")){
                accountBankCode ="560";
            }
            if(acctBank.equalsIgnoreCase("Pagatech")){
                accountBankCode ="327";
            }

        }
        if(acctBankNo !=null){
            acctBankNo=sharedPreferences.getString("Bank_Acct_Number","");


        }
        if(!(amountRequested ==0)){
            amountRequested= Double.parseDouble(sharedPreferences.getString("Amount_Requested",""));


        }

        officeChoice=sharedPreferences.getString(CUSTOMER_OFFICE,"");
        try {
            if(acctBank ==null){
                Intent paymentsIntent = new Intent(this,
                        UserPrefActivity.class);
                startActivityForResult(paymentsIntent,2);

            }
            if(bankName ==null){
                Intent paymentsIntent1sIntent = new Intent(this,
                        UserPrefActivity.class);
                startActivityForResult(paymentsIntent1sIntent,2);

            }
            if(acctNo==0){
                Intent paymentsIntent2Intent = new Intent(this,
                        UserPrefActivity.class);
                startActivityForResult(paymentsIntent2Intent,2);

            }
            if(amountRequested==0){
                Intent paymentsIntent2Intent = new Intent(this,
                        UserPrefActivity.class);
                startActivityForResult(paymentsIntent2Intent,2);

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }


        String acctDetails=acctBank+","+ bankName +"," + valueOf(acctNo);

        /*Bundle bundle = new Bundle();
        bundle.putString("ProfileID", "ProfileID");
        bundle.putString("Surname", "Surname");
        bundle.putString("FirstName", "FirstName");
        Intent intent = new Intent(getApplicationContext(), SendUserMessage.class);
        intent.putExtras(bundle);
        startActivity(intent);*/
        //Intent intent = getIntent();
        //getBundle = this.getIntent().getExtras();

        getBundle = getIntent().getExtras();

        if(getBundle !=null){
            customerName = getBundle.getString("CUSTOMER_USER_NAME");
            customerPhone = getBundle.getString("CUSTOMER_PHONE_NUMBER");
            customerEmail = getBundle.getString("CUSTOMER_EMAIL_ADDRESS");
            amountRequested = getBundle.getDouble("TRANSACTION_AMOUNT");
            //e_WalletBalance = getBundle.getDouble(ACCOUNT_BALANCE);

            packageID = getBundle.getInt("PACKAGE_ID");
            grandTotal = getBundle.getDouble("PACKAGE_TOTAL_VALUE");
            amountContributedSoFar = getBundle.getDouble("REPORT_AMOUNT_COLLECTED_SO_FAR");
            customerId = getBundle.getInt("CUSTOMER_ID");
            e_walletNo = getBundle.getLong("ACCOUNT_NO");
            profileID = getBundle.getInt("PROFILE_ID");

        }


        tittle="Payment Request Alert!";

        Location location= null;


        if(!(packageID ==0)){
            //amountWithdrawable=amountContributedSoFar -(skylightFee*amountContributedSoFar);
            //double qualifyingAmount=0.5*grandTotal;
            if(amountRequested>acctBalance){
                Toast.makeText(this, "You have exceeded the amount to withdraw", Toast.LENGTH_SHORT).show();
                amountRequested=0.00;
            }else{
                //transaction.setCustomer(customer);
                //transaction.setAmount(amountRequested);
                //transaction.setProfile(userProfile);
                //transaction.setMethodOfPay("");

                //Transaction.TRANSACTION_TYPE type= Transaction.TRANSACTION_TYPE.PAYMENT;
                messageDAO= new MessageDAO(this);
                grantingDAO= new TransactionGrantingDAO(this);
                timeLineClassDAO= new TimeLineClassDAO(this);
                userProfile.addPTranxGranding(tranxPayoutID,customerID,customerName,amountRequested,acctBank,bankName, acctBankNo,requestDate,"Payout");
                customer.addCusTranxGranding(tranxPayoutID,customerID,customerName,amountRequested,acctBank,bankName, acctBankNo,customerName,requestDate);
                messageDAO.insertMessage(profileID,customerID,messageID,paymentMessage,sender,"Admin",officeBranch,requestDate);
                timeLineClassDAO.insertTimeLine(tittle,paymentMessage,requestDate,location);
                grantingDAO.insertTransaction_Granting(tranxPayoutID,profileID,customerID,customerName,amountRequested,requestDate,acctBank,bankName,acctBankNo,"Payout","","","Payout","pending");
                //customer.addCusTransactions(amountRequested);
                //userProfile.addTransaction(transactionID,surName,firstName,customerPhone,amountRequested, String.valueOf(e_walletNo),acctDetails,requestDate,"Requested Payment");
                //dbHelper.savePaymentTransactionP(profileID,customerID,type,"");
                sendSMS();
                Toast.makeText(this, "Payout Request Successful", Toast.LENGTH_SHORT).show();

            }


        }





    }
    public void sendSMS() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        message = Message.creator(
                new com.twilio.type.PhoneNumber(customerPhone),
                new com.twilio.type.PhoneNumber("234"+paymentMessage),
                paymentMessage)
                .create();

        System.out.println(message.getSid());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2 && data != null) {
            try {
                bankPrefCat = data.getStringExtra("Bank_pref");
                acctBank = data.getStringExtra("Bank");
                bankName = data.getStringExtra("Bank_Acct_Name");
                amountRequested = data.getDoubleExtra("Amount_Requested", 0.00);
                acctBankNo = data.getStringExtra("Bank_Acct_Number");


            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }


            acctNo = Long.parseLong(acctBankNo);
            sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            gson = new Gson();
            json = sharedPreferences.getString("LastProfileUsed", "");
            userProfile = gson.fromJson(json, Profile.class);
            sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Bank", acctBank);
            editor.putString("Bank_Acct_Name", bankName);
            editor.putString("Bank_Acct_Number", acctBankNo);
            editor.putString("Amount_Requested", String.valueOf(amountRequested)).apply();

            if(acctBank !=null){
                acctBank =sharedPreferences.getString("Bank","");
                if(acctBank.equalsIgnoreCase("ACCESS BANK PLC")){
                    accountBankCode ="044";
                }
                if(acctBank.equalsIgnoreCase("First Bank of Nigeria")){
                    accountBankCode ="011";
                }
                if(acctBank.equalsIgnoreCase("UBA PLC")){
                    accountBankCode ="033";
                }
                if(acctBank.equalsIgnoreCase("Heritage")){
                    accountBankCode ="030";
                }
                if(acctBank.equalsIgnoreCase("Keystone Bank")){
                    accountBankCode ="082";
                }
                if(acctBank.equalsIgnoreCase("Skye Bank")){
                    accountBankCode ="076";
                }

                if(acctBank.equalsIgnoreCase("Sterling Bank")){
                    accountBankCode ="232";
                }

                if(acctBank.equalsIgnoreCase("Union Bank")){
                    accountBankCode ="032";
                }
                if(acctBank.equalsIgnoreCase("GTBank Plc")){
                    accountBankCode ="058";
                }
                if(acctBank.equalsIgnoreCase("FCMB")){
                    accountBankCode ="214";
                }
                if(acctBank.equalsIgnoreCase("TrustBond")){
                    accountBankCode ="523";
                }
                if(acctBank.equalsIgnoreCase("SunTrust Bank")){
                    accountBankCode ="100";
                }
                if(acctBank.equalsIgnoreCase("Diamond Bank")){
                    accountBankCode ="063";
                }
                if(acctBank.equalsIgnoreCase("GT MOBILE MONEY")){
                    accountBankCode ="315";
                }
                if(acctBank.equalsIgnoreCase("FET")){
                    accountBankCode ="314";
                }
                if(acctBank.equalsIgnoreCase("Mkudi")){
                    accountBankCode ="313";
                }
                if(acctBank.equalsIgnoreCase("FSDH")){
                    accountBankCode ="601";
                }
                if(acctBank.equalsIgnoreCase("Coronation Merchant Bank")){
                    accountBankCode ="559";
                }
                if(acctBank.equalsIgnoreCase("Enterprise Bank")){
                    accountBankCode ="084";
                }
                if(acctBank.equalsIgnoreCase("Wema Bank")){
                    accountBankCode ="035";
                }
                if(acctBank.equalsIgnoreCase("Parralex")){
                    accountBankCode ="526";
                }
                if(acctBank.equalsIgnoreCase("Pagatech")){
                    accountBankCode ="327";
                }
                if(acctBank.equalsIgnoreCase("Stanbic IBTC Bank")){
                    accountBankCode ="221";
                }
                if(acctBank.equalsIgnoreCase("Fidelity Mobile")){
                    accountBankCode ="318";
                }
                if(acctBank.equalsIgnoreCase("EcoMobile")){
                    accountBankCode ="307";
                }
                if(acctBank.equalsIgnoreCase("Ecobank Plc")){
                    accountBankCode ="050";
                }
                if(acctBank.equalsIgnoreCase("JAIZ Bank")){
                    accountBankCode ="301";
                }
                if(acctBank.equalsIgnoreCase("Access Money")){
                    accountBankCode ="323";
                }
                if(acctBank.equalsIgnoreCase("Unity Bank")){
                    accountBankCode ="215";
                }
                if(acctBank.equalsIgnoreCase("CitiBank")){
                    accountBankCode ="023";
                }
                if(acctBank.equalsIgnoreCase("Fidelity Bank")){
                    accountBankCode ="070";
                }
                if(acctBank.equalsIgnoreCase("eTranzact")){
                    accountBankCode ="306";
                }
                if(acctBank.equalsIgnoreCase("Standard Chartered Bank")){
                    accountBankCode ="068";
                }
                if(acctBank.equalsIgnoreCase("Zenith Bank")){
                    accountBankCode ="057";
                }
                if(acctBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")){
                    accountBankCode ="402";
                }
                if(acctBank.equalsIgnoreCase("Sterling Mobile")){
                    accountBankCode ="326";
                }
                if(acctBank.equalsIgnoreCase("FBNMobile")){
                    accountBankCode ="309";
                }
                if(acctBank.equalsIgnoreCase("Stanbic Mobile Money")){
                    accountBankCode ="304";
                }
                if(acctBank.equalsIgnoreCase("TCF MFB")){
                    accountBankCode ="90115";
                }
                if(bankName.equalsIgnoreCase("ASO Savings and Loans")){
                    accountBankCode ="401";
                }
                if(acctBank.equalsIgnoreCase("Cellulant")){
                    accountBankCode ="317";
                }
                if(acctBank.equalsIgnoreCase("NIP Virtual Bank")){
                    accountBankCode ="999";
                }
                if(acctBank.equalsIgnoreCase("Paycom")){
                    accountBankCode ="305";
                }
                if(acctBank.equalsIgnoreCase("NPF MicroFinance Bank")){
                    accountBankCode ="552";
                }
                if(acctBank.equalsIgnoreCase("Imperial Homes Mortgage Bank")){
                    accountBankCode ="415";
                }
                if(acctBank.equalsIgnoreCase("Covenant Microfinance Bank")){
                    accountBankCode ="551";
                }
                if(acctBank.equalsIgnoreCase("SafeTrust Mortgage Bank")){
                    accountBankCode ="403";
                }
                if(acctBank.equalsIgnoreCase("ChamsMobile")){
                    accountBankCode ="303";
                }
                if(acctBank.equalsIgnoreCase("ZenithMobile")){
                    accountBankCode ="322";
                }
                if(acctBank.equalsIgnoreCase("PayAttitude Online")){
                    accountBankCode ="329";
                }
                if(acctBank.equalsIgnoreCase("Fortis Microfinance Bank")){
                    accountBankCode ="501";
                }
                if(acctBank.equalsIgnoreCase("VTNetworks")){
                    accountBankCode ="320";
                }
                if(acctBank.equalsIgnoreCase("TeasyMobile")){
                    accountBankCode ="319";
                }
                if(acctBank.equalsIgnoreCase("MoneyBox")){
                    accountBankCode ="325";
                }
                if(acctBank.equalsIgnoreCase("Hedonmark")){
                    accountBankCode ="324";
                }
                if(acctBank.equalsIgnoreCase("Eartholeum")){
                    accountBankCode ="302";
                }
                if(acctBank.equalsIgnoreCase("ReadyCash (Parkway)")){
                    accountBankCode ="311";
                }
                if(acctBank.equalsIgnoreCase("Omoluabi Mortgage Bank")){
                    accountBankCode ="990";
                }
                if(acctBank.equalsIgnoreCase("TagPay")){
                    accountBankCode ="328";
                }
                if(acctBank.equalsIgnoreCase("FortisMobile")){
                    accountBankCode ="308";
                }
                if(acctBank.equalsIgnoreCase("Page MFBank")){
                    accountBankCode ="560";
                }
                if(acctBank.equalsIgnoreCase("Pagatech")){
                    accountBankCode ="327";
                }


            }
            if (resultCode == 2) {
                Toast.makeText(this, "SUCCESS " + bankPrefCat, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "CANCELLED " + bankPrefCat, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}