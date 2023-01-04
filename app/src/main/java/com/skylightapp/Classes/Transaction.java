
package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Currency;

import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.GroupAccount.GRP_ACCT_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;



//@Entity(tableName = Transaction.TRANSACTIONS_TABLE)
public class Transaction implements Parcelable, Serializable {
    public static final String TRANSACTIONS_TABLE = "transactions_Tables";
    public static final String TRANSACTION_ID = "transaction_id";

    //public static final String TIMESTAMP = "timestamp";
    public static final String TRANSACTION_SENDING_ACCT = "transaction_sending_acct";
    public static final String TRANSACTION_DEST_ACCT = "transaction_dest_acct";
    public static final String TRANSACTION_PAYEE = "transaction_payee";
    public static final String TRANSACTION_PAYER = "transaction_payer";
    public static final String TRANSACTION_STATUS = "transaction_status";
    public static final String TRANS_TYPE = "transfer_Type90";
    public static final String TRANSACTIONS_TYPE = "transaction_type";
    public static final String TRANSACTION_AMOUNT = "transaction_amount";
    public static final String TRANSACTION_DATE = "transaction_date";
    //public static final String TABLE_USER_TRANSACTIONS = "users_transactions";
    public static final String GRP_TRANX_TABLE = "grpTranX_Table";
    public static final String GRP_TRANX_ID = "grp_tX_id";
    public static final String GRP_TRANX_DB_ID = "grp_tX_DB_id";
    public static final String GRP_PAYMENT_METHOD = "grp_tX_payment_method";
    public static final String TRANSACTION_OFFICE_BRANCH = "transaction_office_branch";
    public static final String TRANSACTION_APPROVER = "transaction_approver";
    public static final String TRANSACTION_APPROVAL_DATE = "transaction_approval_Date";
    public static final String TRANSACTION_METHOD_OF_PAYMENT = "transaction_method_of_payment";
    public static final String TRANSACTION_PROF_ID = "transaction_Prof_ID";
    public static final String GRP_TRANX_PROF_ID = "grp_tX_Prof_ID";
    public static final String TRANSACTION_CUS_ID = "transaction_Cus_ID";
    public static final String TRANSACTION_ACCT_ID = "transaction_Acct_ID";
    public static final String TRANSACTION_BIZ_ID = "transaction_Biz_ID";
    public static final String TRANSACTION_MARKET_ID = "transaction_Market_ID";
    public static final String TRANSACTION_DB_ID = "transaction_DB_ID";

    public static final String GRP_TRANX_TYPE = "grp_tX_Type";
    public static final String GRP_TRANX_AMT = "grp_tX_AMT";
    public static final String GRP_TRANX_DATE = "grp_tX_Date";
    public static final String GRP_TRANX_SENDING_ACCT = "grp_tX_Sender_Acct";
    public static final String GRP_TRANX_RECEIVING_ACCT = "grp_tX_Receiving_Acct";
    public static final String GRP_TRANX_STATUS = "grp_tX_Status";
    public static final String GRP_TRANX_ACCT_ID = "grp_tX_Acct_id";


    public static final String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + TRANSACTIONS_TABLE + " (" + TRANSACTION_ID + " INTEGER, " + TRANSACTION_PROF_ID + " INTEGER , " +
            TRANSACTION_CUS_ID + " INTEGER , " + TRANSACTION_ACCT_ID + " INTEGER , " + TRANSACTION_DATE + " TEXT, " + TRANSACTION_SENDING_ACCT + " TEXT, " +
            TRANSACTION_DEST_ACCT + " TEXT, " + TRANSACTION_PAYEE + " TEXT, " + TRANSACTION_PAYER + " TEXT, " + TRANSACTION_AMOUNT + " REAL, " +
            TRANSACTIONS_TYPE + " TEXT, " + TRANSACTION_METHOD_OF_PAYMENT + " TEXT, " + TRANSACTION_OFFICE_BRANCH + " TEXT, "+ TRANSACTION_APPROVER + " TEXT, " +TRANSACTION_APPROVAL_DATE + " TEXT, "+  TRANSACTION_STATUS + " TEXT, " + TRANSACTION_BIZ_ID + " INTEGER, " + TRANSACTION_MARKET_ID + " INTEGER, " + TRANSACTION_DB_ID + " INTEGER, " + "PRIMARY KEY(" +TRANSACTION_DB_ID + "), " +"FOREIGN KEY(" + TRANSACTION_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + TRANSACTION_ACCT_ID  + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + ")," +"FOREIGN KEY(" + TRANSACTION_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";
    

    public static final String CREATE_GRP_TX_TABLE = "CREATE TABLE IF NOT EXISTS " + GRP_TRANX_TABLE + " (" + GRP_TRANX_ID + " INTEGER, " + GRP_TRANX_PROF_ID + " INTEGER , " +
            GRP_TRANX_ACCT_ID + " INTEGER NOT NULL, " + GRP_TRANX_TYPE + " TEXT, " + GRP_TRANX_AMT + " REAL, " + GRP_TRANX_DATE + " TEXT, " + GRP_PAYMENT_METHOD + " TEXT, " + GRP_TRANX_SENDING_ACCT + " REAL, " +
            GRP_TRANX_RECEIVING_ACCT + " INTEGER, " + GRP_TRANX_STATUS + " TEXT, "+ GRP_TRANX_DB_ID + " TEXT, " + "PRIMARY KEY(" +GRP_TRANX_DB_ID + "), " +"FOREIGN KEY(" + GRP_TRANX_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + GRP_TRANX_ACCT_ID + ") REFERENCES " + GRP_ACCT_TABLE + "(" + GRP_TRANX_ACCT_ID + "))";


    private String tranxPayer;
    private String tranxBankAcct;
    private String tranxBankAccName;
    private String tranxBankAcctNo;
    private CustomerDailyReport tranxCusDailyReport;
    private int tranxProfID;
    private int tranxRecordID;
    private String tranxExtraInfo;
    private Customer tranxCus;

    public Transaction(double totalBundleString) {
        this.tranxAmount = totalBundleString;

    }

    public int getTranxBizID() {
        return tranxBizID;
    }

    public void setTranxBizID(int tranxBizID) {
        this.tranxBizID = tranxBizID;
    }

    public int getTranxMarketID() {
        return tranxMarketID;
    }

    public void setTranxMarketID(int tranxMarketID) {
        this.tranxMarketID = tranxMarketID;
    }

    public Customer getTranxCus() {
        return tranxCus;
    }

    public void setTranxCus(Customer tranxCus) {
        this.tranxCus = tranxCus;
    }


    public enum TRANSACTION_TYPE {
        PAYMENT, TRANSFER, DEPOSIT,MANUAL_WITHDRAWAL, SAVINGS,LOAN,STANDING_ORDER,
        REFERRALS,BORROWING,GROUP_SAVINGS_DEPOSIT,GROUP_SAVINGS_WITHDRAWAL,WITHDRAWALTX,PROMO,ITEM_PURCHASE,
        RETURNS, INVESTMENT,APP_SUBSCRIPTION,INSURANCE,BIZ_DEAL,SESSION_PAYMENT,DONATION,BUSINESS_SUPPORT,BOAT_BOOKING,TRAIN_BOOKING,TAXI_BOOKING;
    }
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd - hh:mm a");

    //@PrimaryKey(autoGenerate = true)
    private int transactionID=10101;
    //private String timestamp;
    private int tranxSendingAcct;
    private String tranxBranchOffice;
    private String tranxApprover;
    private String tranxApprovalDate;
    private int tranxDestAcct;
    private String tranxPayee;
    private double tranxAmount;
    private TRANSACTION_TYPE tranXType;

    int tranxCusID;
    int tranxAcctId;
    String tranxDate;
    Currency tranxCurrency;
    int transferId;
    String tranxReceiptId;
    String tranxIdString;
    String tranxStatus;
    String tranxMethodOfPay;
    private int tranxBizID;
    private int tranxMarketID;


    public Transaction(int transactionID, Double amountOfGS, String names, String type, String tranxMethodOfPay, String tranxDate, String tranxStatus) {
        this.transactionID = transactionID;
        //timestamp = DATE_FORMAT.format(new Date());
        this.tranxPayee = names;
        this.tranxAmount = amountOfGS;
        this.tranXType = TRANSACTION_TYPE.valueOf(type);
        this.tranxMethodOfPay = tranxMethodOfPay;
        this.tranxStatus = tranxStatus;
    }

    public Transaction(int id, String type, double tranxAmount, String date, String methodOfPayment, int sendingAcct, int receivingAcct, String tranxStatus) {
        this.transactionID = id;
        this.tranxDate = date;
        this.tranxSendingAcct = sendingAcct;
        this.tranxDestAcct = receivingAcct;
        this.tranxAmount = tranxAmount;
        this.tranXType = TRANSACTION_TYPE.valueOf(type);
        this.tranxMethodOfPay = methodOfPayment;
        this.tranxStatus = tranxStatus;
    }

    public Transaction(int id, String type, String endDate, double tranxAmount, int duration, double total) {
        this.transactionID = id;
        this.tranxDate = endDate;
        //this.sendingAccount = String.valueOf(sendingAcct);
        //this.destinationAccount = String.valueOf(receivingAcct);
        this.tranxAmount = tranxAmount;
        this.tranXType = TRANSACTION_TYPE.valueOf(type);
        //this.methodOfPay = methodOfPayment;
        //this.total= total;

    }

    public Transaction(int transactionID, int profileID, int tranxCusID, int accountNo, String timestamp, int tranxSendingAcct, int tranxDestAcct, String tranxPayee, String tranxPayer, double tranxAmount, TRANSACTION_TYPE tranXType, String tranxMethodOfPay, String officeBranch, String tranxApprover, String tranxApprovalDate, String tranxStatus) {
        this.transactionID = transactionID;
        this.tranxProfID = profileID;
        this.tranxCusID = tranxCusID;
        this.tranxAcctId = accountNo;
        this.tranxDate = timestamp;
        this.tranxSendingAcct = tranxSendingAcct;
        this.tranxDestAcct = tranxDestAcct;
        this.tranxPayee = tranxPayee;
        this.tranxPayer = tranxPayer;
        this.tranxAmount = tranxAmount;
        this.tranXType = tranXType;
        this.tranxMethodOfPay = tranxMethodOfPay;
        this.tranxBranchOffice = officeBranch;
        this.tranxApprover = tranxApprover;
        this.tranxApprovalDate = tranxApprovalDate;
        this.tranxStatus = tranxStatus;
    }

    public Transaction(int transactionID, int accountNo, String timestamp, int tranxSendingAcct, int tranxDestAcct, String tranxPayee, String tranxPayer, double tranxAmount, TRANSACTION_TYPE tranXType, String tranxMethodOfPay, String officeBranch, String tranxApprover, String tranxApprovalDate, String tranxStatus) {
        this.transactionID = transactionID;
        this.tranxAcctId = accountNo;
        this.tranxDate = timestamp;
        this.tranxSendingAcct = tranxSendingAcct;
        this.tranxDestAcct = tranxDestAcct;
        this.tranxPayee = tranxPayee;
        this.tranxPayer = tranxPayer;
        this.tranxAmount = tranxAmount;
        this.tranXType = tranXType;
        this.tranxMethodOfPay = tranxMethodOfPay;
        this.tranxBranchOffice = officeBranch;
        this.tranxApprover = tranxApprover;
        this.tranxApprovalDate = tranxApprovalDate;
        this.tranxStatus = tranxStatus;
    }
    public Transaction(int accountNo, String timestamp, int tranxSendingAcct, int tranxDestAcct, String tranxPayee, String tranxPayer, double tranxAmount, TRANSACTION_TYPE tranXType, String tranxMethodOfPay, String officeBranch, String tranxApprover, String tranxApprovalDate, String tranxStatus) {
        this.tranxAcctId = accountNo;
        this.tranxDate = timestamp;
        this.tranxSendingAcct = tranxSendingAcct;
        this.tranxDestAcct = tranxDestAcct;
        this.tranxPayee = tranxPayee;
        this.tranxPayer = tranxPayer;
        this.tranxAmount = tranxAmount;
        this.tranXType = tranXType;
        this.tranxMethodOfPay = tranxMethodOfPay;
        this.tranxBranchOffice = officeBranch;
        this.tranxApprover = tranxApprover;
        this.tranxApprovalDate = tranxApprovalDate;
        this.tranxStatus = tranxStatus;
    }
    public Transaction(int transactionID, String timestamp, String tranxPayee, String tranxPayer, double tranxAmount, TRANSACTION_TYPE tranXType, String tranxMethodOfPay, String officeBranch, String tranxStatus) {
        this.transactionID = transactionID;
        this.tranxDate = timestamp;
        this.tranxPayee = tranxPayee;
        this.tranxPayer = tranxPayer;
        this.tranxAmount = tranxAmount;
        this.tranXType = tranXType;
        this.tranxMethodOfPay = tranxMethodOfPay;
        this.tranxBranchOffice = officeBranch;
        this.tranxStatus = tranxStatus;
    }
    public Transaction(int id, String type, String method, String tranxDate, int sendingAcct, int destination, double tranxAmount, String tranxPayee, String tranxPayer, String tranxStatus) {
        this.transactionID = id;
        this.tranxPayee = tranxPayee;
        this.tranxPayer = tranxPayer;
        this.tranxAmount = tranxAmount;
        this.tranxDate = tranxDate;
        this.tranxSendingAcct = sendingAcct;
        this.tranxDestAcct = destination;
        this.tranXType = TRANSACTION_TYPE.valueOf(type);
        this.tranxMethodOfPay = method;
        this.tranxStatus = tranxStatus;
    }
    public Transaction(int transactionID, String timestamp, String tranxPayee, double tranxAmount) {
        this.transactionID = transactionID;
        this.tranxDate = timestamp;
        this.tranxPayee = tranxPayee;
        this.tranxAmount = tranxAmount;

    }


    public Transaction() {
        super();
    }




    public String getTranxPayer() {
        return tranxPayer;
    }

    public void setTranxPayer(String tranxPayer) {
        this.tranxPayer = tranxPayer;
    }

    public String getTranxBankAcct() {
        return tranxBankAcct;
    }

    public void setTranxBankAcct(String tranxBankAcct) {
        this.tranxBankAcct = tranxBankAcct;
    }

    public String getTranxBankAccName() {
        return tranxBankAccName;
    }

    public void setTranxBankAccName(String tranxBankAccName) {
        this.tranxBankAccName = tranxBankAccName;
    }

    public String getTranxBankAcctNo() {
        return tranxBankAcctNo;
    }

    public void setTranxBankAcctNo(String tranxBankAcctNo) {
        this.tranxBankAcctNo = tranxBankAcctNo;
    }

    public CustomerDailyReport getTranxCusDailyReport() {
        return tranxCusDailyReport;
    }

    public void setTranxCusDailyReport(CustomerDailyReport tranxCusDailyReport) {
        this.tranxCusDailyReport = tranxCusDailyReport;
    }

    public int getTranxProfID() {
        return tranxProfID;
    }

    public void setTranxProfID(int tranxProfID) {
        this.tranxProfID = tranxProfID;
    }

    public int getTranxRecordID() {
        return tranxRecordID;
    }

    public void setTranxRecordID(int tranxRecordID) {
        this.tranxRecordID = tranxRecordID;
    }

    public String getTranxExtraInfo() {
        return tranxExtraInfo;
    }

    public void setTranxExtraInfo(String tranxExtraInfo) {
        this.tranxExtraInfo = tranxExtraInfo;
    }
           /*
            "FOREIGN KEY(" + ACCOUNT_NO + ") REFERENCES " + ACCOUNTS_TABLE + "("+ ACCOUNT_NO + ")," + */




    public void setTransactionStatus(String status) {
        this.tranxStatus = status;
    }
    public String getTransactionStatus() {
        return tranxStatus;
    }

    public void setTranxApprovalDate(String tranxApprovalDate) {
        this.tranxApprovalDate = tranxApprovalDate;
    }
    public String getTxApprovalDate() {
        return tranxApprovalDate;
    }



    public String getTransactionApprover() {
        return tranxApprover;
    }

    public void setTransactionApprover(String approver) {
        this.tranxApprover = approver;
    }
    public String getTransactionOfficeBranch() {
        return tranxBranchOffice;
    }

    public void setTransactionOfficeBranch(String branchOffice) {
        this.tranxBranchOffice = branchOffice;
    }

    public String getTranxMethodOfPay() {
        return tranxMethodOfPay;
    }

    public void setTranxMethodOfPay(String tranxMethodOfPay) {
        this.tranxMethodOfPay = tranxMethodOfPay;
    }


    public Transaction (int transactionID, String tranxPayee, double tranxAmount) {
        this.transactionID = transactionID;
        //timestamp = DATE_FORMAT.format(new Date());
        this.tranxPayee = tranxPayee;
        this.tranxAmount = tranxAmount;
        tranXType = TRANSACTION_TYPE.PAYMENT;
    }

    public Transaction (int transactionID, String timestamp, String tranxPayee, double tranxAmount, int dbId) {
        this(transactionID, tranxPayee, tranxAmount);
        this.tranxDate = timestamp;
        this.tranxAcctId = dbId;
    }

    public Transaction(int transactionID, double tranxAmount) {
        this.transactionID = transactionID;
        this.tranxAmount = tranxAmount;
        tranXType = TRANSACTION_TYPE.DEPOSIT;
    }

    public Transaction(int transactionID, String timestamp, double tranxAmount, int dbId) {
        this(transactionID, tranxAmount);
        this.tranxDate = timestamp;
        this.tranxAcctId = dbId;
    }

    public Transaction(int transactionID, int tranxSendingAcct, int tranxDestAcct, double tranxAmount) {
        this.transactionID = transactionID;
        this.tranxSendingAcct = tranxSendingAcct;
        this.tranxDestAcct = tranxDestAcct;
        this.tranxAmount = tranxAmount;
        tranXType = TRANSACTION_TYPE.TRANSFER;
    }
    public Transaction(int transactionID, String timestamp, int tranxSendingAcct, int tranxDestAcct, double tranxAmount) {
        this.transactionID = transactionID;
        //this.timestamp = DATE_FORMAT.format(new Date());
        this.tranxSendingAcct = tranxSendingAcct;
        this.tranxDestAcct = tranxDestAcct;
        this.tranxAmount = tranxAmount;
        tranXType = TRANSACTION_TYPE.TRANSFER;
    }

    public Transaction(int transactionID, String timestamp, int tranxSendingAcct, int tranxDestAcct, double tranxAmount, int dbId) {
        this(transactionID, tranxSendingAcct, tranxDestAcct, tranxAmount);
        this.tranxDate = timestamp;
        this.tranxAcctId = dbId;
    }

    public int getTransactionID() { return transactionID; }
    public int getTranxSendingAcct() {
        return tranxSendingAcct;
    }
    public int getTranxDestAcct() {
        return tranxDestAcct;
    }
    public String getTranxPayee() { return tranxPayee; }

    public void setDbId(int dbId) { this.tranxAcctId = dbId; }

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
        this.tranxIdString = in.readString();
        this.tranxCusID = in.readInt();
        this.tranxAcctId = in.readInt();
        this.tranxAmount = in.readDouble();
        this.tranxDate = in.readString();
        this.tranxCurrency = in.readParcelable(Currency.class.getClassLoader());
        int tmpTransactionType = in.readInt();
        this.tranXType = tmpTransactionType == -1 ? null :
                TRANSACTION_TYPE.values()[tmpTransactionType];
        this.transferId = in.readInt();
        this.tranxReceiptId = in.readString();
    }

    public Transaction(int payoutID, int loanProfileID, int tranxCusID, int acctNo, String todayDate, String skylight, int no, String acctName, String customerName, double amountRequested, String paymentType, String payStack_transfer, String officeBranch, String txApprover, String tranxDate, String completed) {
    }
    public Transaction(long tranxIdString, String surname, String firstName, String customerPhoneNumber, double tranxAmount, String accountNumber, String description, String tranxDate, String type) {

    }

    public int getTranxIdString() {
        return transactionID;
    }

    public void setTranxIdString(int tranxIdString) {
        this.transactionID = tranxIdString;
    }

    public long getTranxCusID() {
        return tranxCusID;
    }

    public void setTranxCusID(int tranxCusID) {
        this.tranxCusID = tranxCusID;
    }

    public long getTranxAcctId() {
        return tranxAcctId;
    }

    public void setTranxAcctId(int tranxAcctId) {
        this.tranxAcctId = tranxAcctId;
    }

    public Double getTranxAmount() {
        return tranxAmount;
    }

    public void setTranxAmount(double recordAmount) {
        this.tranxAmount = recordAmount;
    }

    public String getTranxDate() {
        return tranxDate;
    }

    public void setTranxDate(String tranxDate) {
        this.tranxDate = tranxDate;
    }

    public Currency getTranxCurrency() {
        return tranxCurrency;
    }

    public void setTranxCurrency(Currency tranxCurrency) {
        this.tranxCurrency = tranxCurrency;
    }

    public TRANSACTION_TYPE getTranXType() {
        return tranXType;
    }

    public void setTranXType(TRANSACTION_TYPE tranXType) {
        this.tranXType = tranXType;
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

    public String getTranxReceiptId() {
        return tranxReceiptId;
    }

    public void setTranxReceiptId(String tranxReceiptId) {
        this.tranxReceiptId = tranxReceiptId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tranxIdString);
        dest.writeLong(this.tranxCusID);
        dest.writeLong(this.tranxAcctId);
        dest.writeDouble(this.tranxAmount);
        dest.writeString(this.tranxDate);
        //dest.writeParcelable(this.currency, flags);
        dest.writeInt(this.tranXType == null ? -1 : this.tranXType.ordinal());
        dest.writeLong(this.transferId);
        //dest.writeParcelable((Parcelable) this.transferDetail, flags);
        dest.writeString(this.tranxReceiptId);
    }

    @NonNull
    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + tranxIdString + '\'' +
                ", transferId=" + transferId +
                // ", transferDetail=" + transferDetail +
                ", receiptId=" + tranxReceiptId +
                '}';
    }
}