package com.skylightapp.SuperAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.StandingOrderAcct;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Classes.TransactionGranting;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;
import com.skylightapp.SMSAct;
import com.skylightapp.Tellers.MyCusLoanRepayment;
import com.skylightapp.Transactions.TransferPayload;
import com.skylightapp.Transactions.Transfers;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.skylightapp.Classes.Profile.PROFILE_PHONE;
import static com.skylightapp.Transactions.OurConfig.SKYLIGHT_SECRET_KEY;
import static java.lang.String.valueOf;

public class TranxApprovalAct extends AppCompatActivity {
    private  Bundle bundle;
    private TransactionGranting transactionGranting;
    private String transactionStatus;
    private double transactionAmount;
    private String transactionBank;
    private String transactionAcctName;
    private String transactionAcctNo;
    private String transactionCusName;
    private String transactionMethod;
    private String transactionPurpose;
    private String transactionAuthorizer;
    private long loanCode,dbLoanCode;
    private int customerID;
    private int tranxPayoutID;
    private Profile superProfile;
    SharedPreferences userPreferences;
    SecureRandom random =new SecureRandom();
    private Customer customer;
    String refID,otpPhoneNumber,response, tgType,accountBank,acctName, bankAcctNumber,superName,approvalTime,customerName,customerPhone,smsMessage,customerEmail,firstName,surName,officeBranch,tittle;
    int messageID,payoutID,acctNo,profileID,eWalletID,soAcctID,loanID,txID,loanProfileID;
    double amountRequested,e_WalletBalance, sOBalance,balance;
    private DBHelper dbHelper;
    private Profile userProfile, loanProfile;
    private Account account;
    private  Calendar calendar;
    private static final String PREF_NAME = "skylight";
    Gson gson;
    private Loan loan;
    String json,fromType,paymentType,selectedBank,todayDate,txApprover;
    private StandingOrderAcct standingOrderAcct;
    private Transaction transaction;
    private UserSuperAdmin superAdmin;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<Transaction>transactionArrayList;
    private Transaction.TRANSACTION_TYPE txType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tranx_approval);
        userProfile=new Profile();
        bundle=getIntent().getExtras();
        dbHelper=new DBHelper(this);
        calendar = Calendar.getInstance();
        gson = new Gson();
        superAdmin= new UserSuperAdmin();
        customer=new Customer();
        transaction= new Transaction(payoutID, loanProfileID, customerID, acctNo, todayDate, "Skylight", acctNo, acctName, customerName, amountRequested, paymentType, "PayStack Transfer", officeBranch, txApprover, todayDate, "Completed");
        superProfile=new Profile();
        loanProfile=new Profile();
        account=new Account();
        loan= new Loan();
        txType = Transaction.TRANSACTION_TYPE.PAYMENT;
        transactionArrayList=new ArrayList<>();
        standingOrderAcct=new StandingOrderAcct();
        transactionGranting=new TransactionGranting();
        userPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        json = userPreferences.getString("LastProfileUsed", "");
        superProfile = gson.fromJson(json, Profile.class);
        if(superProfile !=null){
            superAdmin=superProfile.getProfile_SuperAdmin();
        }
        if(superAdmin !=null){
            txApprover=superAdmin.getSFirstName();
        }


        @SuppressLint("SimpleDateFormat") SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        approvalTime = mdformat.format(calendar.getTime());
        payoutID = random.nextInt((int) (Math.random() * 120) + 1011);
        refID = "SkyLightT/"+ random.nextInt((int) (Math.random() * 1000) + 115);
        String narration ="Skylight requested Payment";
        if(bundle !=null){
            transactionGranting=bundle.getParcelable("TransactionGranting");
        }


        if(transactionGranting !=null){
            customer=transactionGranting.getTeCustomer();
            userProfile=transactionGranting.getTeProfile();
            amountRequested=transactionGranting.getTe_Amount();
            tranxPayoutID =transactionGranting.getTeId();
            accountBank =transactionGranting.getTe_Bank();
            acctName=transactionGranting.getTe_Bank_AcctName();
            bankAcctNumber =transactionGranting.getTe_Bank_AcctNo();
            fromType=transactionGranting.getTe_Customer_Name();
            selectedBank=transactionGranting.getTe_Bank();
            tgType=transactionGranting.getTe_Type();
            loan=transactionGranting.getTe_Loan();


        }
        if(userProfile !=null){
            profileID=userProfile.getPID();

        }
        if(tgType !=null) {
            if(tgType.equalsIgnoreCase("Loan")){
                paymentType="loan";

            }
        }
        if(loan !=null){
            fromType=loan.getAcctType();
            loanID=loan.getLoanId();
            standingOrderAcct=loan.getSOAcct();
            account=loan.getLoan_account();
            loanCode=loan.getLoanCode();
            loanProfile=loan.getLoan_profile();


        }
        if (sqLiteDatabase == null || !sqLiteDatabase.isOpen()) {

            sqLiteDatabase = dbHelper.getWritableDatabase();
            dbLoanCode=dbHelper.getLoanCode(loanID);

        }

        if(account !=null){
            acctNo=account.getSkyLightAcctNo();

        }

        messageID = random.nextInt((int) (Math.random() * 106) + 11);
        if(customer !=null){
            customerID =customer.getCusUID();
            customerPhone=customer.getCusPhoneNumber();
            customerEmail= customer.getCusEmailAddress();
            firstName=customer.getCusFirstName();
            surName=customer.getCusSurname();
            officeBranch=customer.getCusOfficeBranch();
            otpPhoneNumber=customer.getCusPhoneNumber();
            account=customer.getCusAccount();
            standingOrderAcct=customer.getCusStandingOrderAcct();

        }
        if(loanProfile !=null){
            loanProfileID=loanProfile.getPID();

        }
        customerName=surName+","+firstName;


        if(superProfile !=null){
            superName=superProfile.getProfileLastName()+","+superProfile.getProfileFirstName();
        }
        if (selectedBank != null ) {
            if(selectedBank.equalsIgnoreCase("ACCESS BANK PLC")){
                accountBank ="044";
            }
            if(selectedBank.equalsIgnoreCase("First Bank of Nigeria")){
                accountBank ="011";
            }
            if(selectedBank.equalsIgnoreCase("UBA PLC")){
                accountBank ="033";
            }
            if(selectedBank.equalsIgnoreCase("Heritage")){
                accountBank ="030";
            }
            if(selectedBank.equalsIgnoreCase("Keystone Bank")){
                accountBank ="082";
            }
            if(selectedBank.equalsIgnoreCase("Skye Bank")){
                accountBank ="076";
            }


            if(selectedBank.equalsIgnoreCase("Sterling Bank")){
                accountBank ="232";
            }

            if(selectedBank.equalsIgnoreCase("Union Bank")){
                accountBank ="032";
            }
            if(selectedBank.equalsIgnoreCase("GTBank Plc")){
                accountBank ="058";
            }
            if(selectedBank.equalsIgnoreCase("FCMB")){
                accountBank ="214";
            }
            if(selectedBank.equalsIgnoreCase("TrustBond")){
                accountBank ="523";
            }
            if(selectedBank.equalsIgnoreCase("SunTrust Bank")){
                accountBank ="100";
            }
            if(selectedBank.equalsIgnoreCase("Diamond Bank")){
                accountBank ="063";
            }
            if(selectedBank.equalsIgnoreCase("GT MOBILE MONEY")){
                accountBank ="315";
            }
            if(selectedBank.equalsIgnoreCase("FET")){
                accountBank ="314";
            }
            if(selectedBank.equalsIgnoreCase("Mkudi")){
                accountBank ="313";
            }
            if(selectedBank.equalsIgnoreCase("FSDH")){
                accountBank ="601";
            }
            if(selectedBank.equalsIgnoreCase("Coronation Merchant Bank")){
                accountBank ="559";
            }
            if(selectedBank.equalsIgnoreCase("Enterprise Bank")){
                accountBank ="084";
            }
            if(selectedBank.equalsIgnoreCase("Wema Bank")){
                accountBank ="035";
            }
            if(selectedBank.equalsIgnoreCase("Parralex")){
                accountBank ="526";
            }
            if(selectedBank.equalsIgnoreCase("Pagatech")){
                accountBank ="327";
            }
            if(selectedBank.equalsIgnoreCase("Stanbic IBTC Bank")){
                accountBank ="221";
            }
            if(selectedBank.equalsIgnoreCase("Fidelity Mobile")){
                accountBank ="318";
            }
            if(selectedBank.equalsIgnoreCase("EcoMobile")){
                accountBank ="307";
            }
            if(selectedBank.equalsIgnoreCase("Ecobank Plc")){
                accountBank ="050";
            }
            if(selectedBank.equalsIgnoreCase("JAIZ Bank")){
                accountBank ="301";
            }
            if(selectedBank.equalsIgnoreCase("Access Money")){
                accountBank ="323";
            }
            if(selectedBank.equalsIgnoreCase("Unity Bank")){
                accountBank ="215";
            }
            if(selectedBank.equalsIgnoreCase("CitiBank")){
                accountBank ="023";
            }
            if(selectedBank.equalsIgnoreCase("Fidelity Bank")){
                accountBank ="070";
            }
            if(selectedBank.equalsIgnoreCase("eTranzact")){
                accountBank ="306";
            }
            if(selectedBank.equalsIgnoreCase("Standard Chartered Bank")){
                accountBank ="068";
            }
            if(selectedBank.equalsIgnoreCase("Zenith Bank")){
                accountBank ="057";
            }
            if(selectedBank.equalsIgnoreCase("Jubilee Life Mortgage Bank")){
                accountBank ="402";
            }
            if(selectedBank.equalsIgnoreCase("Sterling Mobile")){
                accountBank ="326";
            }
            if(selectedBank.equalsIgnoreCase("FBNMobile")){
                accountBank ="309";
            }
            if(selectedBank.equalsIgnoreCase("Stanbic Mobile Money")){
                accountBank ="304";
            }
            if(selectedBank.equalsIgnoreCase("TCF MFB")){
                accountBank ="90115";
            }
            if(selectedBank.equalsIgnoreCase("ASO Savings and Loans")){
                accountBank ="401";
            }
            if(selectedBank.equalsIgnoreCase("Cellulant")){
                accountBank ="317";
            }
            if(selectedBank.equalsIgnoreCase("NIP Virtual Bank")){
                accountBank ="999";
            }
            if(selectedBank.equalsIgnoreCase("Paycom")){
                accountBank ="305";
            }
            if(selectedBank.equalsIgnoreCase("NPF MicroFinance Bank")){
                accountBank ="552";
            }
            if(selectedBank.equalsIgnoreCase("Imperial Homes Mortgage Bank")){
                accountBank ="415";
            }
            if(selectedBank.equalsIgnoreCase("Covenant Microfinance Bank")){
                accountBank ="551";
            }
            if(selectedBank.equalsIgnoreCase("SafeTrust Mortgage Bank")){
                accountBank ="403";
            }
            if(selectedBank.equalsIgnoreCase("ChamsMobile")){
                accountBank ="303";
            }
            if(selectedBank.equalsIgnoreCase("ZenithMobile")){
                accountBank ="322";
            }
            if(selectedBank.equalsIgnoreCase("PayAttitude Online")){
                accountBank ="329";
            }
            if(selectedBank.equalsIgnoreCase("Fortis Microfinance Bank")){
                accountBank ="501";
            }
            if(selectedBank.equalsIgnoreCase("VTNetworks")){
                accountBank ="320";
            }
            if(selectedBank.equalsIgnoreCase("TeasyMobile")){
                accountBank ="319";
            }
            if(selectedBank.equalsIgnoreCase("MoneyBox")){
                accountBank ="325";
            }
            if(selectedBank.equalsIgnoreCase("Hedonmark")){
                accountBank ="324";
            }
            if(selectedBank.equalsIgnoreCase("Eartholeum")){
                accountBank ="302";
            }
            if(selectedBank.equalsIgnoreCase("ReadyCash (Parkway)")){
                accountBank ="311";
            }
            if(selectedBank.equalsIgnoreCase("Omoluabi Mortgage Bank")){
                accountBank ="990";
            }
            if(selectedBank.equalsIgnoreCase("TagPay")){
                accountBank ="328";
            }
            if(selectedBank.equalsIgnoreCase("FortisMobile")){
                accountBank ="308";
            }
            if(selectedBank.equalsIgnoreCase("Page MFBank")){
                accountBank ="560";
            }
            if(selectedBank.equalsIgnoreCase("Pagatech")){
                accountBank ="327";
            }

        }

        if(loanCode==dbLoanCode){

            todayDate = mdformat.format(calendar.getTime());

            try {
                for (int i = 0; i < transactionArrayList.size(); i++) {
                    try {
                        if (transactionArrayList.get(i).getTranxDate().equalsIgnoreCase(todayDate) && transactionArrayList.get(i).getTransactionApprover().equalsIgnoreCase(txApprover) && transactionArrayList.get(i).getTranxCusID()==customerID) {
                            Toast.makeText(TranxApprovalAct.this, "A Similar Transaction  was already executed" , Toast.LENGTH_LONG).show();
                            return;

                        }
                        else {
                            Transfers transfers = new Transfers();
                            TransferPayload transferPayload = new TransferPayload();
                            transferPayload.setAccount_bank(accountBank);
                            transferPayload.setAccount_number(valueOf(acctNo));
                            transferPayload.setAmount(valueOf(amountRequested));
                            transferPayload.setSeckey(SKYLIGHT_SECRET_KEY);
                            transferPayload.setNarration(narration);
                            transferPayload.setCurrency("NGN");
                            transferPayload.setReference(refID);
                            transferPayload.setBeneficiary_name(customerName);
                            response = transfers.doTransfer(transferPayload);
                            smsMessage="Skylight has approved the payment request for"+""+"N"+amountRequested+""+"of"+""+customerName;
                            tittle="Payment request approval";
                            double newBalance =e_WalletBalance-amountRequested;
                            Location location= null;
                            Transaction.TRANSACTION_TYPE type= Transaction.TRANSACTION_TYPE.PAYMENT;
                            if(fromType.equalsIgnoreCase("EWallet")){
                                if(account !=null){
                                    e_WalletBalance=account.getAccountBalance();
                                    acctNo=account.getSkyLightAcctNo();


                                }
                                if(e_WalletBalance>0.00){
                                    newBalance=e_WalletBalance-amountRequested;
                                    dbHelper.updateAccBalance(eWalletID,newBalance);
                                    account.setAccountBalance(newBalance);

                                }

                            }
                            if(fromType.equalsIgnoreCase("StandingOrderAcct")){
                                if(standingOrderAcct !=null){
                                    sOBalance=standingOrderAcct.getSoAcctBalance();
                                    soAcctID=standingOrderAcct.getSoAcctNo();

                                }
                                if(sOBalance>0.00){
                                    newBalance=sOBalance-amountRequested;
                                    dbHelper.updateSOAcctBalance(soAcctID,newBalance);
                                    standingOrderAcct.setSoAcctBalance(newBalance);

                                }


                            }
                            transaction= new Transaction(payoutID,loanProfileID,customerID,acctNo,todayDate,"Skylight",acctNo,acctName,customerName,amountRequested,paymentType,"PayStack Transfer",officeBranch,txApprover,todayDate,response);
                            dbHelper.updateTranxGranting(tranxPayoutID,txApprover,"PayStack Transfer",response);
                            dbHelper.insertMessage(profileID,customerID,messageID,tittle+"/"+response,"Skylight",customerName,officeBranch,approvalTime);
                            dbHelper.insertTimeLine(tittle,smsMessage,approvalTime,location);
                            dbHelper.updateTranxGrantingStatus(tranxPayoutID,response);
                            sendSMSMessage(otpPhoneNumber,smsMessage);
                        }
                    } catch (NullPointerException e) {
                        System.out.println("Oops!");
                    }


                }
            } catch (NullPointerException e) {
                System.out.println("Oops!");
            }


            //dbHelper.insertTransaction_Granting(payoutID,profileID,customerID,customerName,amountRequested,approvalTime,accountbank,acctName, bankAcctNumber,"Payout","Flutterwave",superName,"Processed");
            //dbHelper.savePaymentTransactionP(profileID,customerID,type,response);


        }



    }
    protected void sendSMSMessage(String otpPhoneNumber, String smsMessage) {
        Bundle smsBundle= new Bundle();
        smsBundle.putString(PROFILE_PHONE, otpPhoneNumber);
        smsBundle.putString("USER_PHONE", otpPhoneNumber);
        smsBundle.putString("smsMessage", smsMessage);
        smsBundle.putString("from","Skylight");
        smsBundle.putString("to", otpPhoneNumber);
        Intent itemPurchaseIntent = new Intent(TranxApprovalAct.this, SMSAct.class);
        itemPurchaseIntent.putExtras(smsBundle);
        itemPurchaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(itemPurchaseIntent);

    }



}