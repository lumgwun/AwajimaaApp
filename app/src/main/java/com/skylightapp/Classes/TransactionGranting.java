package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;

public class TransactionGranting implements Parcelable, Serializable {

    private int teId;
    private String te_name;
    private String te_Customer_Name;
    private String te_Bank;
    private int teCustomerID;
    private int teProfileID;
    private int teTransactionID;
    private String te_paymentMethod;
    private String te_Bank_AcctNo;
    private String te_Bank_AcctName;
    private String te_purpose;
    private String te_Authorizer;
    private String teBranchOffice;
    private String te_status;
    private String te_Date;
    private String te_Type;
    private double te_Amount;
    private Customer teCustomer;
    private Profile teProfile;

    public static final String TANSACTION_EXTRA_ID = "te_id";
    public static final String TANSACTION_EXTRA_C_NAME = "te_name";
    public static final String TANSACTION_EXTRA_BANK = "te_bank";
    public static final String TANSACTION_EXTRA_ACCTNO = "te_acctNo";
    public static final String TANSACTION_EXTRA_ACCTNAME = "te_acctName";
    public static final String TANSACTION_EXTRA_STATUS = "te_status";
    public static final String TANSACTION_EXTRA_AMOUNT = "te_Amount";
    public static final String TANSACTION_EXTRA_PURPOSE = "te_Purpose";
    public static final String TANSACTION_EXTRA_PAYMENT_METHOD = "te_payment_method";
    public static final String TANSACTION_EXTRA_AUTHORIZER = "te_authorizer";
    public static final String TANSACTION_EXTRA_TABLE = "te_table";
    public static final String TANSACTION_EXTRA_DATE = "te_date";
    public static final String TANSACTION_EXTRA_OFFICE = "te_office";
    public static final String TANSACTION_EXTRA_TYPE = "te_Type";
    public static final String TANSACTION_EXTRA_CUSTOMER = "te_cus";
    public static final String TANSACTION_EXTRA_CUSTOMER_ID = "te_cus_ID";
    public static final String TANSACTION_EXTRA_PROFILE_ID = "te_Prof_id";
    public static final String TANSACTION_EXTRA_T_ID = "te_Tranx_id";


    public static final String CREATE_TANSACTION_EXTRA_TABLE = "CREATE TABLE " + TANSACTION_EXTRA_TABLE + " (" + TANSACTION_EXTRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+ TANSACTION_EXTRA_PROFILE_ID + " INTEGER NOT NULL, " + TANSACTION_EXTRA_CUSTOMER_ID + " INTEGER , " + TANSACTION_EXTRA_C_NAME + " TEXT , " +
            TANSACTION_EXTRA_BANK + " TEXT , " + TANSACTION_EXTRA_ACCTNAME + " TEXT , "+ TANSACTION_EXTRA_ACCTNO + " TEXT , " + TANSACTION_EXTRA_AMOUNT + " REAL , "+ TANSACTION_EXTRA_PURPOSE + " TEXT , " + TANSACTION_EXTRA_PAYMENT_METHOD + " TEXT , " + TANSACTION_EXTRA_DATE + " TEXT , " + TANSACTION_EXTRA_AUTHORIZER + " TEXT , "+ TANSACTION_EXTRA_STATUS + " TEXT , "+ TANSACTION_EXTRA_T_ID + " INTEGER , "+ TANSACTION_EXTRA_OFFICE + " TEXT , "+ TANSACTION_EXTRA_CUSTOMER + " TEXT , " + TANSACTION_EXTRA_TYPE + " TEXT , "+  "FOREIGN KEY(" + TANSACTION_EXTRA_T_ID + ") REFERENCES " + TRANSACTIONS_TABLE + "(" + TRANSACTION_ID + "),"+  "FOREIGN KEY(" + TANSACTION_EXTRA_CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," +"FOREIGN KEY(" + TANSACTION_EXTRA_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";
    private String cusAcctType;
    private Loan teLoan;
    private Loan te_Loan;

    public TransactionGranting(int loanNumber, int teProfileID, int teCustomerID, String customerName, String selectedBank, String acctName, long accountNo, double amountDouble1, String te_purpose, String loanDate, String te_method, String te_authorizer, String inProgress) {
        this.teId = loanNumber;
        this.teProfileID = teProfileID;
        this.teCustomerID = teCustomerID;
        this.te_name = customerName;
        this.te_Bank = selectedBank;
        this.te_Bank_AcctName = acctName;
        this.te_Bank_AcctNo = String.valueOf(accountNo);
        this.te_Amount = amountDouble1;
        this.te_purpose = te_purpose;
        this.te_Date = loanDate;
        this.te_paymentMethod = te_method;
        this.te_Authorizer = te_authorizer;
        this.te_status = inProgress;

    }

    public TransactionGranting(int teID, int teProfileID, int teCustomerID, String teCusName, String te_bank, String teBank_acctName, String te_bank_acctNo, double te_amount, String te_purpose, String date, String te_method, String te_authorizer, String status) {
        this.teId = teID;
        this.teProfileID = teProfileID;
        this.teCustomerID = teCustomerID;
        this.te_name = teCusName;
        this.te_Bank = te_bank;
        this.te_Bank_AcctName = teBank_acctName;
        this.te_Bank_AcctNo = te_bank_acctNo;
        this.te_Amount = te_amount;
        this.te_purpose = te_purpose;
        this.te_Date = date;
        this.te_paymentMethod = te_method;
        this.te_Authorizer = te_authorizer;
        this.te_status = status;
    }

    protected TransactionGranting(Parcel in) {
        teId = in.readInt();
        te_name = in.readString();
        te_Customer_Name = in.readString();
        te_Bank = in.readString();
        teCustomerID = in.readInt();
        teProfileID = in.readInt();
        teTransactionID = in.readInt();
        te_paymentMethod = in.readString();
        te_Bank_AcctNo = in.readString();
        te_Bank_AcctName = in.readString();
        te_purpose = in.readString();
        te_Authorizer = in.readString();
        te_status = in.readString();
        te_Date = in.readString();
        te_Amount = in.readDouble();
        teCustomer = in.readParcelable(Customer.class.getClassLoader());
        teProfile = in.readParcelable(Profile.class.getClassLoader());
    }

    public TransactionGranting(int tranxPayoutID, int teProfileID, String customerName, double amountRequested, String acctBank, String bankName, String acctBankNo, String teCusName, String requestDate) {
        this.teId = tranxPayoutID;
        this.teProfileID = teProfileID;
        this.te_name = teCusName;
        this.te_Bank = acctBank;
        this.te_Bank_AcctName = bankName;
        this.te_Bank_AcctNo = acctBankNo;
        this.te_Amount = amountRequested;
        this.te_Date = requestDate;
    }

    public TransactionGranting() {
        super();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(teId);
        dest.writeString(te_name);
        dest.writeString(te_Customer_Name);
        dest.writeString(te_Bank);
        dest.writeLong(teCustomerID);
        dest.writeLong(teProfileID);
        dest.writeLong(teTransactionID);
        dest.writeString(te_paymentMethod);
        dest.writeString(te_Bank_AcctNo);
        dest.writeString(te_Bank_AcctName);
        dest.writeString(te_purpose);
        dest.writeString(te_Authorizer);
        dest.writeString(te_status);
        dest.writeString(te_Date);
        dest.writeDouble(te_Amount);
        dest.writeParcelable(teCustomer, flags);
        dest.writeParcelable(teProfile, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TransactionGranting> CREATOR = new Creator<TransactionGranting>() {
        @Override
        public TransactionGranting createFromParcel(Parcel in) {
            return new TransactionGranting(in);
        }

        @Override
        public TransactionGranting[] newArray(int size) {
            return new TransactionGranting[size];
        }
    };

    public int getTeId() {
        return teId;
    }

    public void setTeId(int teId) {
        this.teId = teId;
    }

    public String getTe_name() {
        return te_name;
    }

    public void setTe_name(String te_name) {
        this.te_name = te_name;
    }

    public String getTe_Customer_Name() {
        return te_Customer_Name;
    }

    public void setTe_Customer_Name(String te_Customer_Name) {
        this.te_Customer_Name = te_Customer_Name;
    }

    public String getTe_Bank() {
        return te_Bank;
    }

    public void setTe_Bank(String te_Bank) {
        this.te_Bank = te_Bank;
    }

    public long getTeCustomerID() {
        return teCustomerID;
    }

    public void setTeCustomerID(int teCustomerID) {
        this.teCustomerID = teCustomerID;
    }

    public long getTeProfileID() {
        return teProfileID;
    }

    public void setTeProfileID(int teProfileID) {
        this.teProfileID = teProfileID;
    }

    public String getTe_paymentMethod() {
        return te_paymentMethod;
    }

    public void setTe_paymentMethod(String te_paymentMethod) {
        this.te_paymentMethod = te_paymentMethod;
    }

    public String getTe_Bank_AcctNo() {
        return te_Bank_AcctNo;
    }

    public void setTe_Bank_AcctNo(String te_Bank_AcctNo) {
        this.te_Bank_AcctNo = te_Bank_AcctNo;
    }

    public String getTe_Bank_AcctName() {
        return te_Bank_AcctName;
    }

    public void setTe_Bank_AcctName(String te_Bank_AcctName) {
        this.te_Bank_AcctName = te_Bank_AcctName;
    }

    public String getTe_purpose() {
        return te_purpose;
    }

    public void setTe_purpose(String te_purpose) {
        this.te_purpose = te_purpose;
    }

    public String getTe_Authorizer() {
        return te_Authorizer;
    }

    public void setTe_Authorizer(String te_Authorizer) {
        this.te_Authorizer = te_Authorizer;
    }

    public String getTe_status() {
        return te_status;
    }

    public void setTe_status(String te_status) {
        this.te_status = te_status;
    }

    public String getTe_Date() {
        return te_Date;
    }

    public void setTe_Date(String te_Date) {
        this.te_Date = te_Date;
    }

    public double getTe_Amount() {
        return te_Amount;
    }

    public void setTe_Amount(double te_Amount) {
        this.te_Amount = te_Amount;
    }

    public long getTeTransactionID() {
        return teTransactionID;
    }

    public void setTeTransactionID(int teTransactionID) {
        this.teTransactionID = teTransactionID;
    }

    public Customer getTeCustomer() {
        return teCustomer;
    }

    public void setTeCustomer(Customer teCustomer) {
        this.teCustomer = teCustomer;
    }

    public Profile getTeProfile() {
        return teProfile;
    }

    public void setTeProfile(Profile teProfile) {
        this.teProfile = teProfile;
    }

    public String getTeBranchOffice() {
        return teBranchOffice;
    }

    public void setTeBranchOffice(String teBranchOffice) {
        this.teBranchOffice = teBranchOffice;
    }

    public String getTe_Type() {
        return te_Type;
    }

    public void setTe_Type(String te_Type) {
        this.te_Type = te_Type;
    }

    public void setTe_Loan(Loan te_loan) {
        this.te_Loan = te_loan;
    }

    public Loan getTe_Loan() {
        return te_Loan;
    }
}
