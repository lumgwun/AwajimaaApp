package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

//@Entity(tableName = Payment.PAYMENTS_TABLE)
public class Payment implements Parcelable,Serializable {

    public static final String PAYMENTS_TABLE = "payment_Table";
    public static final String PAYMENT_ID = "payment_id";
    public static final String PAYMENT_DATE = "payment_date";
    public static final String PAYMENTTYPE = "payment_type";
    public static final String PAYMENT_OFFICE = "payment_office";
    public static final String PAYMENT_AMOUNT = "payment_amount";
    public static final String PAYMENT_APPROVAL_DATE = "payment_approval_date";
    public static final String PAYMENT_APPROVER = "payment_approver";
    public static final String PAYMENT_CODE = "payment_code";
    public static final String PAYMENT_ACCOUNT = "payment_account";
    public static final String PAYMENT_ACCOUNT_TYPE = "payment_account_type";
    public static final String PAYMENT_STATUS = "payment_status";
    public static final String PAYMENT_PROF_ID = "payment_Prof_ID";
    public static final String PAYMENT_ADMIN_ID = "payment_Admin_ID";
    public static final String PAYMENT_CUS_ID = "payment_Cus_ID";
    public static final String PAYMENT_DB_ID = "payment_DB_ID";


    public static final String CREATE_PAYMENT_TABLE = "CREATE TABLE IF NOT EXISTS " + PAYMENTS_TABLE + " (" + PAYMENT_ID + " INTEGER , " + PAYMENT_PROF_ID + " INTEGER , " +
            PAYMENT_CUS_ID + " INTEGER , " + PAYMENT_ADMIN_ID + " INTEGER, " + PAYMENTTYPE + " TEXT, " + PAYMENT_AMOUNT + " REAL, " + PAYMENT_DATE + " TEXT, " + PAYMENT_APPROVAL_DATE + " TEXT, " +
            PAYMENT_APPROVER + " TEXT, " + PAYMENT_CODE + " REAL, " + PAYMENT_ACCOUNT + " INTEGER, " +
            PAYMENT_ACCOUNT_TYPE + " TEXT, " + PAYMENT_OFFICE + " TEXT, " + PAYMENT_STATUS + " TEXT, "+ PAYMENT_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +"FOREIGN KEY(" + PAYMENT_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + PAYMENT_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";



    protected Payment(Parcel in) {
        if (in.readByte() == 0) {
            nr = null;
        } else {
            nr = in.readInt();
        }
        paymentAmtToWithdraw = in.readDouble();
        paymentProfileID = in.readInt();
        paymentCusID = in.readInt();
        paymentID = in.readInt();
        paymentDate = in.readString();
        paymentApprovalDate = in.readString();
        paymentApprover = in.readString();
        paymentCode = in.readLong();
        paymentWithdrawalBal = in.readDouble();
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    public Payment() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (nr == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(nr);
        }
        parcel.writeDouble(paymentAmtToWithdraw);
        parcel.writeLong(paymentProfileID);
        parcel.writeLong(paymentCusID);
        parcel.writeLong(paymentID);
        parcel.writeString(paymentDate);
        parcel.writeString(paymentApprovalDate);
        parcel.writeString(paymentApprover);
        parcel.writeLong(paymentCode);
        parcel.writeDouble(paymentWithdrawalBal);
    }

    public enum PAYMENT_TYPE {
        CASH_WITHDRAWAL, BANK, SAVINGS,
        REFERRALS,GROUP_SAVINGS_WITHDRAWAL;
    }
    private static final long serialVersionUID = 1L;
    private Integer nr = 0;
    private  double paymentAmtToWithdraw;
    private  int paymentProfileID, paymentCusID;
    //@PrimaryKey(autoGenerate = true)
    private int paymentID=1015;
    private  String paymentDate;
    private  String paymentApprovalDate;
    private  PAYMENT_TYPE paymentType;
    private  String paymentApprover;
    private  long paymentCode;
    private  long payment_Account;
    private  String payment_Account_Type;
    private  String paymentStatus;
    private  String paymentOffice;
    private  double paymentWithdrawalBal;
    private BigDecimal balance = BigDecimal.ZERO;
    private BigDecimal principal = BigDecimal.ZERO;
    private BigDecimal interest = BigDecimal.ZERO;
    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal commission = BigDecimal.ZERO;

    public Payment(int paymentProfileID, int paymentCusID, String typeOfPayment, double amountPaid, Date date, String paymentApprover, long code, String acctType, String office, String paymentStatus) {
        this.paymentProfileID = paymentProfileID;
        this.paymentCusID = paymentCusID;
        this.paymentType = PAYMENT_TYPE.valueOf(typeOfPayment);
        this.paymentAmtToWithdraw = amountPaid;
        this.paymentDate = String.valueOf(date);
        this.paymentApprover = paymentApprover;
        this.paymentCode = code;
        this.payment_Account_Type = acctType;
        this.paymentOffice = office;
        this.paymentStatus = paymentStatus;
    }
    public Payment(String typeOfPayment, double amountPaid, Date date,long code, String acctType, String office, String paymentStatus) {
        this.paymentType = PAYMENT_TYPE.valueOf(typeOfPayment);
        this.paymentAmtToWithdraw = amountPaid;
        this.paymentDate = String.valueOf(date);
        this.paymentCode = code;
        this.payment_Account_Type = acctType;
        this.paymentOffice = office;
        this.paymentStatus = paymentStatus;
    }
    public Payment(int paymentCusID, String typeOfPayment, double amountPaid, Date date, String paymentStatus) {
        this.paymentCusID = paymentCusID;
        this.paymentType = PAYMENT_TYPE.valueOf(typeOfPayment);
        this.paymentAmtToWithdraw = amountPaid;
        this.paymentDate = String.valueOf(date);
        this.paymentStatus = paymentStatus;

    }

    public Payment(Integer nr, BigDecimal balance, BigDecimal principal, BigDecimal interest, BigDecimal amount) {
        this.nr = nr;
        this.balance = balance;
        this.principal = principal;
        this.interest = interest;
        this.amount = amount;
    }



    public Payment(int reportID, int dbaseID, Date date, String paymentStatus) {
        this.nr = reportID;

    }


    public void setPaymentType(PAYMENT_TYPE paymentType) {
        this.paymentType = paymentType;
    }

    public PAYMENT_TYPE getPaymentType() {
        return paymentType;
    }
    public String getPaymentOffice() {
        return paymentOffice;
    }

    public void setPaymentOffice(String paymentOffice) {
        this.paymentOffice = paymentOffice;
    }
    public long getPaymentAccount() {
        return payment_Account;
    }

    public void setPaymentAccount(long payment_Account) {
        this.payment_Account = payment_Account;
    }

    public String getPaymentAccountType() {
        return payment_Account_Type;
    }

    public void setPaymentAccountType(String payment_Account_Type) {
        this.payment_Account_Type = payment_Account_Type;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String status) {
        this.paymentStatus = status;
    }
    public String getPaymentApprovalDate() {
        return paymentApprovalDate;
    }

    public void setPaymentApprovalDate(String paymentApprovalDate) {
        this.paymentApprovalDate = paymentApprovalDate;
    }
    public String getPaymentApprover() {
        return paymentApprover;
    }
    public void setPaymentApprover(String paymentApprover) {
        this.paymentApprover = paymentApprover;
    }
    public double getPaymentAmtToWithdraw() {
        return paymentAmtToWithdraw;
    }

    public void setPaymentAmtToWithdraw(double paymentAmtToWithdraw) {
        this.paymentAmtToWithdraw = paymentAmtToWithdraw;
    }

    public int getPaymentProfileID() {
        return paymentProfileID;
    }

    public void setPaymentProfileID(int paymentProfileID) {
        this.paymentProfileID = paymentProfileID;
    }
    public long getPaymentCusID() {
        return paymentCusID;
    }

    public void setPaymentCusID(int paymentCusID) {
        this.paymentCusID = paymentCusID;
    }
    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    public long getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(long paymentCode) {
        this.paymentCode = paymentCode;
    }

    public double getPaymentWithdrawalBal() {
        return paymentWithdrawalBal;
    }

    public void setPaymentWithdrawalBal(double paymentWithdrawalBal) {
        this.paymentWithdrawalBal = paymentWithdrawalBal;
    }


    public Integer getNr() {
        return nr;
    }

    public void setNr(Integer nr) {
        this.nr = nr;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }
}
