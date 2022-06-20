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
    private long customerID;
    private long profileID;
    private long transactionID;
    private String te_paymentMethod;
    private String te_Bank_AcctNo;
    private String te_Bank_AcctName;
    private String te_purpose;
    private String te_Authorizer;
    private String branchOffice;
    private String te_status;
    private String te_Date;
    private String te_Type;
    private double te_Amount;
    private Customer customer;
    private Profile profile;

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

    public static final String CREATE_TANSACTION_EXTRA_TABLE = "CREATE TABLE " + TANSACTION_EXTRA_TABLE + " (" + TANSACTION_EXTRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"+ PROFILE_ID + " INTEGER NOT NULL, " + CUSTOMER_ID + " INTEGER , " + TANSACTION_EXTRA_C_NAME + " TEXT , " +
            TANSACTION_EXTRA_BANK + " TEXT , " + TANSACTION_EXTRA_ACCTNAME + " TEXT , "+ TANSACTION_EXTRA_ACCTNO + " TEXT , " + TANSACTION_EXTRA_AMOUNT + " TEXT , "+ TANSACTION_EXTRA_PURPOSE + " TEXT , " + TANSACTION_EXTRA_PAYMENT_METHOD + " TEXT , " + TANSACTION_EXTRA_DATE + " TEXT , " + TANSACTION_EXTRA_AUTHORIZER + " TEXT , "+ TANSACTION_EXTRA_STATUS + " TEXT , "+ TRANSACTION_ID + " TEXT , "+ TANSACTION_EXTRA_OFFICE + " TEXT , "+ TANSACTION_EXTRA_CUSTOMER + " TEXT , " + TANSACTION_EXTRA_TYPE + " TEXT , "+  "FOREIGN KEY(" + TRANSACTION_ID + ") REFERENCES " + TRANSACTIONS_TABLE + "(" + TRANSACTION_ID + "),"+  "FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," +"FOREIGN KEY(" + PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";
    private String cusAcctType;
    private Loan loan;

    public TransactionGranting(int loanNumber, int profileID, int customerID, String customerName, String selectedBank, String acctName, long accountNo, double amountDouble1, String te_purpose, String loanDate, String te_method, String te_authorizer, String inProgress) {
        this.teId = loanNumber;
        this.profileID = profileID;
        this.customerID = customerID;
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

    public TransactionGranting(int teID, int profileID, int customerID, String teCusName, String te_bank, String teBank_acctName, String te_bank_acctNo, double te_amount, String te_purpose, String date, String te_method, String te_authorizer, String status) {
        this.teId = teID;
        this.profileID = profileID;
        this.customerID = customerID;
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
        customerID = in.readLong();
        profileID = in.readLong();
        transactionID = in.readLong();
        te_paymentMethod = in.readString();
        te_Bank_AcctNo = in.readString();
        te_Bank_AcctName = in.readString();
        te_purpose = in.readString();
        te_Authorizer = in.readString();
        te_status = in.readString();
        te_Date = in.readString();
        te_Amount = in.readDouble();
        customer = in.readParcelable(Customer.class.getClassLoader());
        profile = in.readParcelable(Profile.class.getClassLoader());
    }

    public TransactionGranting(int tranxPayoutID, int profileID, String customerName, double amountRequested, String acctBank, String bankName, String acctBankNo, String teCusName, String requestDate) {
        this.teId = tranxPayoutID;
        this.profileID = profileID;
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
        dest.writeLong(customerID);
        dest.writeLong(profileID);
        dest.writeLong(transactionID);
        dest.writeString(te_paymentMethod);
        dest.writeString(te_Bank_AcctNo);
        dest.writeString(te_Bank_AcctName);
        dest.writeString(te_purpose);
        dest.writeString(te_Authorizer);
        dest.writeString(te_status);
        dest.writeString(te_Date);
        dest.writeDouble(te_Amount);
        dest.writeParcelable(customer, flags);
        dest.writeParcelable(profile, flags);
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

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
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

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getBranchOffice() {
        return branchOffice;
    }

    public void setBranchOffice(String branchOffice) {
        this.branchOffice = branchOffice;
    }

    public String getCusAcctType() {
        return cusAcctType;
    }

    public void setCusAcctType(String cusAcctType) {
        this.cusAcctType = cusAcctType;
    }

    public String getTe_Type() {
        return te_Type;
    }

    public void setTe_Type(String te_Type) {
        this.te_Type = te_Type;
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
