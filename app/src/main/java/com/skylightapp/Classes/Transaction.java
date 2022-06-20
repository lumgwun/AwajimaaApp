
package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRPA_ID;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;



@Entity(tableName = Transaction.TRANSACTIONS_TABLE)
public class Transaction extends CustomerDailyReport implements Parcelable, Serializable {
    public static final String TRANSACTIONS_TABLE = "transactions";
    public static final String TRANSACTION_ID = "transaction_id";

    public static final String TIMESTAMP = "timestamp";
    public static final String SENDING_ACCOUNT = "sending_account";
    public static final String DESTINATION_ACCOUNT = "destination_account";
    public static final String TRANSACTION_PAYEE = "payee";
    public static final String TRANSACTION_PAYER = "payer";
    public static final String TRANSACTION_STATUS = "transaction_status";
    public static final String TRANS_TYPE = "Type";
    public static final String TRANSACTIONS_TYPE = "transaction_type";
    public static final String TRANSACTION_AMOUNT = "transaction_amount";
    public static final String TRANSACTION_DATE = "transaction_date";
    public static final String TABLE_USER_TRANSACTIONS = "users_transactions";
    public static final String GRP_TRANX_TABLE = "grpTranX";
    public static final String GRP_TRANX_ID = "grp_tX_id";
    public static final String GRP_PAYMENT_METHOD = "grp_tX_payment_id";
    public static final String TRANSACTION_OFFICE_BRANCH = "transaction_office_branch";
    public static final String TRANSACTION_APPROVER = "transaction_approver";
    public static final String TRANSACTION_APPROVAL_DATE = "transaction_approval_Date";
    public static final String TRANSACTION_METHOD_OF_PAYMENT = "transaction_method_of_payment";


    public static final String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TRANSACTIONS_TABLE + " (" + TRANSACTION_ID + " INTEGER, " + PROFILE_ID + " INTEGER , " +
            CUSTOMER_ID + " INTEGER NOT NULL, " + ACCOUNT_NO + " INTEGER , " + TRANSACTION_DATE + " TEXT, " + SENDING_ACCOUNT + " TEXT, " +
            DESTINATION_ACCOUNT + " TEXT, " + TRANSACTION_PAYEE + " TEXT, " + TRANSACTION_PAYER + " TEXT, " + TRANSACTION_AMOUNT + " DOUBLE, " +
            TRANSACTIONS_TYPE + " TEXT, " + TRANSACTION_METHOD_OF_PAYMENT + " TEXT, " + TRANSACTION_OFFICE_BRANCH + " TEXT, "+ TRANSACTION_APPROVER + TRANSACTION_APPROVAL_DATE + " TEXT, "+  TRANSACTION_STATUS + " TEXT, " + "PRIMARY KEY(" +TRANSACTION_ID + "), " +"FOREIGN KEY(" + ACCOUNT_NO  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";



    public static final String CREATE_GRP_TX_TABLE = "CREATE TABLE IF NOT EXISTS " + GRP_TRANX_TABLE + " (" + GRP_TRANX_ID + " INTEGER, " + PROFILE_ID + " INTEGER , " +
            GRPA_ID + " INTEGER NOT NULL, " + TRANS_TYPE + " TEXT, " + TRANSACTION_AMOUNT + " DOUBLE, " + TRANSACTION_DATE + " TEXT, " + GRP_PAYMENT_METHOD + " TEXT, " + SENDING_ACCOUNT + " LONG, " +
            DESTINATION_ACCOUNT + " LONG, " + TRANSACTION_STATUS + " TEXT, " + "PRIMARY KEY(" +GRP_TRANX_ID + "), " +"FOREIGN KEY(" + PROFILE_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + GRPA_ID + ") REFERENCES " + GRP_ACCT_TABLE + "(" + GRPA_ID + "))";
    private String payer;
    private String bankAcct;
    private String bankAccName;
    private String bankAcctNo;


    public Transaction(int transactionID, Double amountOfGS, String names, String type, String methodOfPay, String date,String status) {
        this.transactionID = transactionID;
        timestamp = DATE_FORMAT.format(new Date());
        this.payee = names;
        this.amount = amountOfGS;
        this.transactionType = TRANSACTION_TYPE.valueOf(type);
        this.methodOfPay = methodOfPay;
        this.status = status;
    }

    public Transaction(int id, String type, double amount, String endDate, String methodOfPayment, int sendingAcct, int receivingAcct, String status) {
        this.transactionID = id;
        this.timestamp = endDate;
        this.sendingAccount = sendingAcct;
        this.destinationAccount = receivingAcct;
        this.amount = amount;
        this.transactionType = TRANSACTION_TYPE.valueOf(type);
        this.methodOfPay = methodOfPayment;
        this.status = status;
    }

    public Transaction(int id, String type, String endDate, double amount, int duration, double total) {
        this.transactionID = id;
        this.timestamp = endDate;
        //this.sendingAccount = String.valueOf(sendingAcct);
        //this.destinationAccount = String.valueOf(receivingAcct);
        this.amount = amount;
        this.transactionType = TRANSACTION_TYPE.valueOf(type);
        //this.methodOfPay = methodOfPayment;
        //this.total= total;

    }

    public Transaction(int transactionID, int profileID, int customerID, int accountNo, String timestamp, int sendingAccount, int destinationAccount, String payee, String payer, double amount, TRANSACTION_TYPE transactionType, String methodOfPay,String officeBranch, String approver, String approvalDate, String status) {
        this.transactionID = transactionID;
        this.profileId = profileID;
        this.customerID = customerID;
        this.accountId = accountNo;
        this.timestamp = timestamp;
        this.sendingAccount = sendingAccount;
        this.destinationAccount = destinationAccount;
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
        this.transactionType = transactionType;
        this.methodOfPay = methodOfPay;
        this.branchOffice = officeBranch;
        this.approver = approver;
        this.approvalDate = approvalDate;
        this.status = status;
    }

    public Transaction(int transactionID, int accountNo, String timestamp, int sendingAccount, int destinationAccount, String payee, String payer, double amount, TRANSACTION_TYPE transactionType, String methodOfPay,String officeBranch, String approver, String approvalDate, String status) {
        this.transactionID = transactionID;
        this.accountId = accountNo;
        this.timestamp = timestamp;
        this.sendingAccount = sendingAccount;
        this.destinationAccount = destinationAccount;
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
        this.transactionType = transactionType;
        this.methodOfPay = methodOfPay;
        this.branchOffice = officeBranch;
        this.approver = approver;
        this.approvalDate = approvalDate;
        this.status = status;
    }
    public Transaction(int accountNo, String timestamp, int sendingAccount, int destinationAccount, String payee, String payer, double amount, TRANSACTION_TYPE transactionType, String methodOfPay,String officeBranch, String approver, String approvalDate, String status) {
        this.accountId = accountNo;
        this.timestamp = timestamp;
        this.sendingAccount = sendingAccount;
        this.destinationAccount = destinationAccount;
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
        this.transactionType = transactionType;
        this.methodOfPay = methodOfPay;
        this.branchOffice = officeBranch;
        this.approver = approver;
        this.approvalDate = approvalDate;
        this.status = status;
    }
    public Transaction(int transactionID,String timestamp, String payee, String payer, double amount, TRANSACTION_TYPE transactionType, String methodOfPay,String officeBranch, String status) {
        this.transactionID = transactionID;
        this.timestamp = timestamp;
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
        this.transactionType = transactionType;
        this.methodOfPay = methodOfPay;
        this.branchOffice = officeBranch;
        this.status = status;
    }
    public Transaction(int transactionID, String timestamp, String payee, double amount) {
        this.transactionID = transactionID;
        this.timestamp = timestamp;
        this.payee = payee;
        this.amount = amount;

    }

    public Transaction(double totalBundleString) {
        this.amount = totalBundleString;

    }

    public Transaction() {
        super();
    }



    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getBankAcct() {
        return bankAcct;
    }

    public void setBankAcct(String bankAcct) {
        this.bankAcct = bankAcct;
    }

    public String getBankAccName() {
        return bankAccName;
    }

    public void setBankAccName(String bankAccName) {
        this.bankAccName = bankAccName;
    }

    public String getBankAcctNo() {
        return bankAcctNo;
    }

    public void setBankAcctNo(String bankAcctNo) {
        this.bankAcctNo = bankAcctNo;
    }
           /*
            "FOREIGN KEY(" + ACCOUNT_NO + ") REFERENCES " + ACCOUNTS_TABLE + "("+ ACCOUNT_NO + ")," + */


    public enum TRANSACTION_TYPE {
        PAYMENT, TRANSFER, DEPOSIT,MANUAL_WITHDRAWAL, SAVINGS,LOAN,STANDING_ORDER,
        REFERRALS,BORROWING,GROUP_SAVINGS_DEPOSIT,GROUP_SAVINGS_WITHDRAWAL,WITHDRAWALTX,PROMO,ITEM_PURCHASE,
        RETURNS, IVESTMENT;
    }
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd - hh:mm a");

    @PrimaryKey(autoGenerate = true)
    private int transactionID=10101;
    private String timestamp;
    private int sendingAccount;
    private String branchOffice;
    private String approver;
    private String approvalDate;
    private int destinationAccount;
    private String payee;
    private double amount;
    private TRANSACTION_TYPE transactionType;

    int customerID;
    int accountId;
    String date;
    Currency currency;
    int transferId;
    String receiptId;
    String transactionId;
    String status;
    String methodOfPay;


    public void setTransactionStatus(String status) {
        this.status = status;
    }
    public String getTransactionStatus() {
        return status;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }
    public String getTxApprovalDate() {
        return approvalDate;
    }



    public String getTransactionApprover() {
        return approver;
    }

    public void setTransactionApprover(String approver) {
        this.approver = approver;
    }
    public String getTransactionOfficeBranch() {
        return branchOffice;
    }

    public void setTransactionOfficeBranch(String branchOffice) {
        this.branchOffice = branchOffice;
    }

    public String getMethodOfPay() {
        return methodOfPay;
    }

    public void setMethodOfPay(String methodOfPay) {
        this.methodOfPay = methodOfPay;
    }


    public Transaction (int transactionID, String payee, double amount) {
        this.transactionID = transactionID;
        timestamp = DATE_FORMAT.format(new Date());
        this.payee = payee;
        this.amount = amount;
        transactionType = TRANSACTION_TYPE.PAYMENT;
    }

    public Transaction (int transactionID, String timestamp, String payee, double amount, int dbId) {
        this(transactionID, payee, amount);
        this.timestamp = timestamp;
        this.accountId = dbId;
    }

    public Transaction(int transactionID, double amount) {
        this.transactionID = transactionID;
        timestamp = DATE_FORMAT.format(new Date());
        this.amount = amount;
        transactionType = TRANSACTION_TYPE.DEPOSIT;
    }

    public Transaction(int transactionID, String timestamp, double amount, int dbId) {
        this(transactionID, amount);
        this.timestamp = timestamp;
        this.accountId = dbId;
    }

    public Transaction(int transactionID, int sendingAccount, int destinationAccount, double amount) {
        this.transactionID = transactionID;
        this.timestamp = DATE_FORMAT.format(new Date());
        this.sendingAccount = sendingAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        transactionType = TRANSACTION_TYPE.TRANSFER;
    }
    public Transaction(int transactionID, String timestamp,int sendingAccount, int destinationAccount, double amount) {
        this.transactionID = transactionID;
        this.timestamp = DATE_FORMAT.format(new Date());
        this.sendingAccount = sendingAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        transactionType = TRANSACTION_TYPE.TRANSFER;
    }

    public Transaction(int transactionID, String timestamp, int sendingAccount, int destinationAccount, double amount, int dbId) {
        this(transactionID, sendingAccount, destinationAccount, amount);
        this.timestamp = timestamp;
        this.accountId = dbId;
    }

    public int getTransactionID() { return transactionID; }
    public String getTimestamp() { return timestamp; }
    public int getSendingAccount() {
        return sendingAccount;
    }
    public int getDestinationAccount() {
        return destinationAccount;
    }
    public String getPayee() { return payee; }

    public void setDbId(int dbId) { this.accountId = dbId; }

    public static final Creator<Transaction> CREATOR = new
            Creator<Transaction>() {
                @Override
                public Transaction createFromParcel(Parcel source) {
                    return new Transaction(source);
                }

                @Override
                public Transaction[] newArray(int size) {
                    return new Transaction[size];
                }
            };


    protected Transaction(Parcel in) {
        this.transactionId = in.readString();
        this.customerID = in.readInt();
        this.accountId = in.readInt();
        this.amount = in.readDouble();
        this.date = in.readString();
        this.currency = in.readParcelable(Currency.class.getClassLoader());
        int tmpTransactionType = in.readInt();
        this.transactionType = tmpTransactionType == -1 ? null :
                TRANSACTION_TYPE.values()[tmpTransactionType];
        this.transferId = in.readInt();
        this.receiptId = in.readString();
    }

    public Transaction(int payoutID, int loanProfileID, int customerID, int acctNo, String todayDate, String skylight, int no, String acctName, String customerName, double amountRequested, String paymentType, String payStack_transfer, String officeBranch, String txApprover, String date, String completed) {
    }
    public Transaction(long transactionId, String surname, String firstName, String customerPhoneNumber, double amount, String accountNumber, String description,String date, String type) {

    }

    public int getTransactionId() {
        return transactionID;
    }

    public void setTransactionId(int transactionId) {
        this.transactionID = transactionId;
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public TRANSACTION_TYPE getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TRANSACTION_TYPE transactionType) {
        this.transactionType = transactionType;
    }

    public long getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    /*public TransferDetail getTransferDetail() {
        return transferDetail;
    }

    public void setTransferDetail(
            TransferDetail transferDetail) {
        this.transferDetail = transferDetail;
    }*/


    @Override
    public int describeContents() {
        return 0;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.transactionId);
        dest.writeLong(this.customerID);
        dest.writeLong(this.accountId);
        dest.writeDouble(this.amount);
        dest.writeString(this.date);
        //dest.writeParcelable(this.currency, flags);
        dest.writeInt(this.transactionType == null ? -1 : this.transactionType.ordinal());
        dest.writeLong(this.transferId);
        //dest.writeParcelable((Parcelable) this.transferDetail, flags);
        dest.writeString(this.receiptId);
    }

    @NonNull
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", transferId=" + transferId +
                // ", transferDetail=" + transferDetail +
                ", receiptId=" + receiptId +
                '}';
    }
}