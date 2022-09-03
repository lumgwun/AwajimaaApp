
package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.skylightapp.Markets.Market;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TABLE;

@Entity(tableName = Loan.LOAN_TABLE)
public class Loan implements Serializable, Parcelable {
    public static final String LOAN_ID = "loan_id";
    public static final String LOAN_TYPE = "loanType";
    public static final String LOAN_AMOUNT = "l_amount";
    public static final String LOAN_INTEREST = "l_interest";
    public static final String LOAN_FIXED_PAYMENT = "fixed_payment";
    public static final String LOAN_TOTAL_INTEREST = "l_total_interests";
    public static final String LOAN_DOWN_PAYMENT = "down_payment";
    public static final String LOAN_DISPOSABLE_COM = "disposable_commission";
    public static final String LOAN_MONTHLY_COM = "monthly_commission";
    public static final String LOAN_BALANCE = "residue_payment";
    public static final String LOAN_DATE = "l_date";
    public static final String LOAN_START_DATE = "loan_start_date";
    public static final String LOAN_END_DATE = "loan_end_date";
    public static final String LOAN_STATUS = "loan_status";
    public static final String LOAN_DURATION = "loan_duration";
    public static final String LOAN_ACCT_NAME = "loan_cus";
    public static final String LOAN_CODE = "loan_code";
    public static final String LOAN_TABLE = "loan_table";
    public static final String LOAN_CUS_ID = "loan_Cus_id";
    public static final String LOAN_PACK_ID = "loan_Package_id";
    public static final String LOAN_PROF_ID = "loan_Prof_id";
    public static final String LOAN_ACCT_NO = "loan_Acct_No";
    public static final String LOAN_BANK_ACCT_NO = "loan_BankAcct_No";

    public static final String CREATE_LOAN_TABLE = "CREATE TABLE IF NOT EXISTS " + LOAN_TABLE + " (" + LOAN_ID + " INTEGER , " +
            LOAN_CUS_ID + " INTEGER , " +LOAN_PROF_ID + " INTEGER  , " + LOAN_ACCT_NO + " INTEGER , " + LOAN_TYPE + " TEXT, " + LOAN_AMOUNT + " REAL, " + LOAN_DURATION + " TEXT, " +
            LOAN_INTEREST + " REAL, " + LOAN_FIXED_PAYMENT + " REAL, " + LOAN_TOTAL_INTEREST + " REAL, " + LOAN_DOWN_PAYMENT + " REAL, " + LOAN_DISPOSABLE_COM + " REAL, " +
            LOAN_MONTHLY_COM + " REAL, " + LOAN_BALANCE + " REAL, " + LOAN_DATE + " TEXT, " + LOAN_START_DATE + " TEXT, " + LOAN_END_DATE + " TEXT, " +
            LOAN_STATUS + " TEXT, " + LOAN_ACCT_NAME + " TEXT, " + LOAN_CODE + " REAL, "  + LOAN_PACK_ID + " INTEGER, "+ LOAN_BANK_ACCT_NO + " INTEGER, " + "FOREIGN KEY(" + LOAN_PACK_ID + ") REFERENCES " + PACKAGE_TABLE + "(" + PACKAGE_ID + ")," + "FOREIGN KEY(" + LOAN_ACCT_NO + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + "),"+ "FOREIGN KEY(" + LOAN_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "PRIMARY KEY(" + LOAN_ID  + "), " +
            "FOREIGN KEY(" + LOAN_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    private static final long serialVersionUID = 1L;

    public static final int PERCENT = 0;
    public static final int VALUE = 1;
    private boolean calculated;
    private String loan_date;
    private String loan_startDate;
    private String loan_endDate;
    private String loan_status;
    private String loanOfficeBranch;
    private  double loanBalance;
    private String loanBank;
    private String loanType;
    private int loanDuration;
    private String loanAcctName;
    private String loanBankAcctNo;
    private int loanAcctID;
    Profile loan_profile;
    Customer loan_customer;
    private Market loan_Market;
    private ArrayList<Market> loanMarkets;
    Payment loan_payment;
    Account loan_account;
    private TransactionGranting loan_granting;
    //private int loanType = 0;
    private BigDecimal amount = BigDecimal.ZERO;
    private BigDecimal interest = BigDecimal.ZERO;
    private BigDecimal fixedPayment = BigDecimal.ZERO;
    private Integer period = 0;
    private List<Payment> payments = new ArrayList<Payment>();
    @PrimaryKey(autoGenerate = true)
    private int loanId=115;

    private ArrayList<Customer> customers;


    private BigDecimal totalInterests = BigDecimal.ZERO;
    private BigDecimal minimalPayment = BigDecimal.ZERO;
    private BigDecimal maximalPayment = BigDecimal.ZERO;

    private BigDecimal downPayment;
    private BigDecimal disposableCommission;
    private BigDecimal monthlyCommission;
    private BigDecimal residue;

    private int downPaymentType;
    private int disposableCommissionType;
    private int monthlyCommissionType;
    private int residueType;
    private long loanCode;
    private double mInterest;
    private  double amount1;
    private  double total1;

    private BigDecimal downPaymentPayment;
    private BigDecimal disposableCommissionPayment;
    private BigDecimal monthlyCommissionPayment;
    private BigDecimal residuePayment;

    private BigDecimal commissionsTotal = BigDecimal.ZERO;

    private BigDecimal effectiveInterestRate = BigDecimal.ZERO;
    private ArrayList<Profile> profiles;
    private String acctType;
    private StandingOrderAcct SOAcct;
    public Loan() {
        super();

    }


    public Loan(int profileID, int loanId, int loanType, BigDecimal amount, BigDecimal interest, BigDecimal fixedPayment, BigDecimal totalInterests, BigDecimal downPayment, BigDecimal disposableCommission, BigDecimal monthlyCommission, BigDecimal residuePayment, String loanDate, String loanStartDate, String loanEndDate, String loanStatus) {
        this.payments = payments;
        this.loanId = loanId;
        //this.loanType = loanType;
        this.amount = amount;
        this.interest = interest;
        this.fixedPayment = fixedPayment;
        this.totalInterests = totalInterests;
        this.downPayment = downPayment;
        this.disposableCommission = disposableCommission;
        this.monthlyCommission = monthlyCommission;
        this.residuePayment = residuePayment;
        this.loan_date = loanDate;
        this.loan_startDate = loanStartDate;
        this.loan_endDate = loanEndDate;
        this.loan_status = loanStatus;
    }
    public Loan(long profileID, long customerID, int loanId, int loanType, BigDecimal amount, BigDecimal interest, BigDecimal fixedPayment, BigDecimal totalInterests, BigDecimal downPayment, BigDecimal disposableCommission, BigDecimal monthlyCommission, BigDecimal residuePayment, String loanDate, String loanStartDate, String loanEndDate, String loanStatus) {
        this.payments = payments;
        this.loanId = loanId;
        //this.customer.getId() = customerID;
        this.amount = amount;
        this.interest = interest;
        this.fixedPayment = fixedPayment;
        this.totalInterests = totalInterests;
        this.downPayment = downPayment;
        this.disposableCommission = disposableCommission;
        this.monthlyCommission = monthlyCommission;
        this.residuePayment = residuePayment;
        this.loan_date = loanDate;
        this.loan_startDate = loanStartDate;
        this.loan_endDate = loanEndDate;
        this.loan_status = loanStatus;
    }
    public Loan(Profile profile1, Customer customer1, Payment payment1) {
        this.loan_payment = payment1;
        this.loan_profile = profile1;
        this.loan_customer = customer1;
    }
    public Loan(Profile profile1, Customer customer1, Account account1, Payment payment1) {
        this.loan_payment = payment1;
        this.loan_profile = profile1;
        this.loan_account = account1;
        this.loan_customer = customer1;
    }


    public void addPBusiness(Market loan_Market) {
        loanMarkets = new ArrayList<>();
        Market market = new Market(loan_Market);
        loanMarkets.add(market);
    }

    public Loan(int loanNumber, BigDecimal amount, String loan_date, String loan_status) {
        this.payments = payments;
        this.loanId = loanNumber;
        //this.loanType = loanType;
        this.amount = amount;
        this.interest = interest;
        this.fixedPayment = fixedPayment;
        this.totalInterests = totalInterests;
        this.downPayment = downPayment;
        this.disposableCommission = disposableCommission;
        this.monthlyCommission = monthlyCommission;
        this.residuePayment = residuePayment;


    }
    public Loan(int loanId, double amount, String loanStartDate, String loan_status, String loanEndDate, double mInterest) {
        this.payments = payments;
        this.loanId = loanId;
        //this.loanType = loanType;
        this.amount1 = amount;
        this.interest = interest;
        this.fixedPayment = fixedPayment;
        this.totalInterests = totalInterests;
        this.downPayment = downPayment;
        this.disposableCommission = disposableCommission;
        this.monthlyCommission = monthlyCommission;
        this.residuePayment = residuePayment;


    }
    public Loan(double amount, String loanStartDate, String loan_status, String loanEndDate, double interest) {
        this.loanId = loanId;
        this.amount1 = amount;
        this.mInterest = interest;
        this.loan_endDate = loanEndDate;
        this.mInterest = interest;
        this.loan_startDate = loanStartDate;
        this.loan_status = loan_status;


    }

    public Loan(int profileID, int customerID, int loanID, double loanAmount, String loanDate, String loan_startDate, String loan_endDate, String loan_status) {
        this.loanId = loanID;
        this.amount1 = loanAmount;
        this.loan_date = loanDate;
        this.loan_endDate = loan_endDate;
        this.loan_startDate = loan_startDate;
        this.loan_status = loan_status;
    }


    protected Loan(Parcel in) {
        calculated = in.readByte() != 0;
        loan_date = in.readString();
        loan_startDate = in.readString();
        loan_endDate = in.readString();
        loan_status = in.readString();
        loanOfficeBranch = in.readString();
        loan_profile = in.readParcelable(Profile.class.getClassLoader());
        loan_customer = in.readParcelable(Customer.class.getClassLoader());
        loan_payment = in.readParcelable(Payment.class.getClassLoader());
        loan_account = in.readParcelable(Account.class.getClassLoader());
        loan_granting = in.readParcelable(TransactionGranting.class.getClassLoader());
        if (in.readByte() == 0) {
            period = null;
        } else {
            period = in.readInt();
        }
        payments = in.createTypedArrayList(Payment.CREATOR);
        loanId = in.readInt();
        customers = in.createTypedArrayList(Customer.CREATOR);
        downPaymentType = in.readInt();
        disposableCommissionType = in.readInt();
        monthlyCommissionType = in.readInt();
        residueType = in.readInt();
        loanCode = in.readLong();
        mInterest = in.readDouble();
        amount1 = in.readDouble();
        total1 = in.readDouble();
        profiles = in.createTypedArrayList(Profile.CREATOR);
        acctType = in.readString();
        SOAcct = in.readParcelable(StandingOrderAcct.class.getClassLoader());
    }

    public static final Creator<Loan> CREATOR = new Creator<Loan>() {
        @Override
        public Loan createFromParcel(Parcel in) {
            return new Loan(in);
        }

        @Override
        public Loan[] newArray(int size) {
            return new Loan[size];
        }
    };

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
        totalInterests = BigDecimal.ZERO;
        minimalPayment = BigDecimal.ZERO;
        maximalPayment = BigDecimal.ZERO;
        commissionsTotal = BigDecimal.ZERO;

        if (disposableCommissionPayment != null) {
            commissionsTotal = commissionsTotal.add(disposableCommissionPayment);
        }

        int i = 0;
        for (Payment payment : payments) {
            totalInterests = totalInterests.add(payment.getInterest());


            if (payment.getCommission() != null) {
                commissionsTotal = commissionsTotal.add(payment.getCommission());
            }

            if (++i != payments.size() || getResidue().compareTo(BigDecimal.ZERO) == 0) {
                if (minimalPayment.equals(BigDecimal.ZERO)) {
                    minimalPayment = payment.getAmount();
                } else {
                    minimalPayment = minimalPayment.min(payment.getAmount());
                }
                maximalPayment = maximalPayment.max(payment.getAmount());
            }
        }
    }

    public BigDecimal getTotalAmount() {
        BigDecimal total = amount.add(totalInterests);

        if (getCommissionsTotal() != null && getCommissionsTotal().compareTo(BigDecimal.ZERO) != 0) {
            total = total.add(getCommissionsTotal());
        }
        return total;
    }

    public BigDecimal getTotalInterests() {
        return totalInterests;
    }
    public int getLoanId() {
        return loanId;
    }
    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getMaxMonthlyPayment() {
        return maximalPayment;
    }


    public BigDecimal getMinMonthlyPayment() {
        return minimalPayment;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public double getTotal1() {
        return total1;
    }

    public void setTotal1(double total1) {
        this.total1 = total1;
    }
    public double getmInterest() {
        return mInterest;
    }

    public void setmInterest(double mInterest) {
        this.mInterest = mInterest;
    }
    public double getAmount1() {
        return amount1;
    }

    public void setAmount1(double amount) {
        this.amount1 = amount;
    }


    public BigDecimal getInterest2() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public List<Payment> getPayments() {
        return new ArrayList<Payment>(payments);
    }

    public BigDecimal getFixedPayment() {
        return fixedPayment;
    }

    public void setFixedPayment(BigDecimal fixedPayment) {
        this.fixedPayment = fixedPayment;
    }

    public String getLoan_date() {
        return loan_date;
    }
    public String getLoan_startDate() {
        return loan_startDate;
    }
    public String getLoan_endDate() {
        return loan_endDate;
    }
    public String getLoan_status() {
        return loan_status;
    }
    public void setLoan_status(String loan_status) {
        this.loan_status = loan_status;
    }

    public void setLoan_date(String date) {
        this.loan_date = date;
    }
    public void setLoan_startDate(String loan_startDate) {
        this.loan_startDate = loan_startDate;
    }
    public void setLoan_endDate(String loan_endDate) {
        this.loan_endDate = loan_endDate;
    }

    public BigDecimal getMonthlyCommission() {
        return monthlyCommission;
    }

    public void setMonthlyCommission(BigDecimal monthlyCommission) {
        this.monthlyCommission = monthlyCommission;
    }

    public BigDecimal getDisposableCommission() {
        return disposableCommission;
    }

    public void setDisposableCommission(BigDecimal disposableCommission) {
        this.disposableCommission = disposableCommission;
    }

    public void setLoan_customer(Customer customer1) {
        this.loan_customer = customer1;
    }

    public Customer getLoan_customer() {
        return loan_customer;
    }

    public void setLoan_profile(Profile profile1) {
        this.loan_profile = profile1;
    }

    public Profile getLoan_profile() {
        return loan_profile;
    }

    public void setLoan_account(Account account1) {
        this.loan_account = account1;
    }

    public Account getLoan_account() {
        return loan_account;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public int getDownPaymentType() {
        return downPaymentType;
    }

    public void setDownPaymentType(int downPaymentType) {
        this.downPaymentType = downPaymentType;
    }

    public int getDisposableCommissionType() {
        return disposableCommissionType;
    }

    public void setDisposableCommissionType(int disposableCommissionType) {
        this.disposableCommissionType = disposableCommissionType;
    }

    public void addUserProfile(String firstName, String lastName, String phoneNumber, String email, String dob, String gender, String address, String username, String office, String joinedDate, int id) {
        String profileNo = "P" + (profiles.size() + 1);
        Profile profile = new Profile(firstName,lastName, phoneNumber, email,dob,gender,address,username,office,joinedDate,id);
        profiles.add(profile);
    }
    public void addCustomer(int uid, String surname, String firstName, String phoneNumber, String  emailAddress,String nin, String dob, String gender, String address, String userName, String password, String office, String dateJoined) {
        String profileNo = "P" + (customers.size() + 1);
        Customer customer1 = new Customer(uid,surname,firstName,phoneNumber,emailAddress, nin,dob, gender,address,userName,password,office,dateJoined);
        customers.add(customer1);
    }

    public int getMonthlyCommissionType() {
        return monthlyCommissionType;
    }

    public void setMonthlyCommissionType(int monthlyCommissionType) {
        this.monthlyCommissionType = monthlyCommissionType;
    }

    public BigDecimal getDownPaymentPayment() {
        return downPaymentPayment;
    }

    public void setDownPaymentPayment(BigDecimal downPaymentPayment) {
        this.downPaymentPayment = downPaymentPayment;
    }

    public BigDecimal getMonthlyCommissionPayment() {
        return monthlyCommissionPayment;
    }

    public void setMonthlyCommissionPayment(BigDecimal monthlyCommissionPayment) {
        this.monthlyCommissionPayment = monthlyCommissionPayment;
    }

    public BigDecimal getDisposableCommissionPayment() {
        return disposableCommissionPayment;
    }

    public void setDisposableCommissionPayment(BigDecimal disposableCommissionPayment) {
        this.disposableCommissionPayment = disposableCommissionPayment;
    }

    public BigDecimal getCommissionsTotal() {
        return commissionsTotal;
    }

    public BigDecimal getEffectiveInterestRate() {
        return effectiveInterestRate;
    }

    public void setEffectiveInterestRate(BigDecimal effectiveInterestRate) {
        this.effectiveInterestRate = effectiveInterestRate;
    }

    public BigDecimal getResiduePayment() {
        return residuePayment;
    }

    public void setResiduePayment(BigDecimal residuePayment) {
        this.residuePayment = residuePayment;
    }

    public int getResidueType() {
        return residueType;
    }

    public void setResidueType(int residueType) {
        this.residueType = residueType;
    }

    public BigDecimal getResidue() {
        return residue;
    }

    public void setResidue(BigDecimal residue) {
        this.residue = residue;
    }

    public boolean hasDownPayment() {
        return getDownPaymentPayment() != null && getDownPaymentPayment().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean hasDisposableCommission() {
        return getDisposableCommissionPayment() != null && getDisposableCommissionPayment().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean hasAnyCommission() {
        return getCommissionsTotal().compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isCalculated() {
        return calculated;
    }

    public void setCalculated(boolean calculated) {
        this.calculated = calculated;
    }

    public TransactionGranting getLoan_granting() {
        return loan_granting;
    }

    public void setLoan_granting(TransactionGranting loan_granting) {
        this.loan_granting = loan_granting;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public StandingOrderAcct getSOAcct() {
        return SOAcct;
    }

    public void setSOAcct(StandingOrderAcct soAcct) {
        this.SOAcct = soAcct;
    }

    public long getLoanCode() {
        return loanCode;
    }

    public void setLoanCode(long loanCode) {
        this.loanCode = loanCode;
    }

    public String getLoanOfficeBranch() {
        return loanOfficeBranch;
    }

    public void setLoanOfficeBranch(String loanOfficeBranch) {
        this.loanOfficeBranch = loanOfficeBranch;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (calculated ? 1 : 0));
        parcel.writeString(loan_date);
        parcel.writeString(loan_startDate);
        parcel.writeString(loan_endDate);
        parcel.writeString(loan_status);
        parcel.writeString(loanOfficeBranch);
        parcel.writeParcelable(loan_profile, i);
        parcel.writeParcelable(loan_customer, i);
        parcel.writeParcelable(loan_payment, i);
        parcel.writeParcelable(loan_account, i);
        parcel.writeParcelable(loan_granting, i);
        if (period == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(period);
        }
        parcel.writeTypedList(payments);
        parcel.writeInt(loanId);
        parcel.writeTypedList(customers);
        parcel.writeInt(downPaymentType);
        parcel.writeInt(disposableCommissionType);
        parcel.writeInt(monthlyCommissionType);
        parcel.writeInt(residueType);
        parcel.writeLong(loanCode);
        parcel.writeDouble(mInterest);
        parcel.writeDouble(amount1);
        parcel.writeDouble(total1);
        parcel.writeTypedList(profiles);
        parcel.writeString(acctType);
        parcel.writeParcelable(SOAcct, i);
    }

    public double getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(double loanBalance) {
        this.loanBalance = loanBalance;
    }

    public String getLoanBank() {
        return loanBank;
    }

    public void setLoanBank(String loanBank) {
        this.loanBank = loanBank;
    }

    public String getLoanAcctName() {
        return loanAcctName;
    }

    public void setLoanAcctName(String loanAcctName) {
        this.loanAcctName = loanAcctName;
    }

    public String getLoanBankAcctNo() {
        return loanBankAcctNo;
    }

    public void setLoanBankAcctNo(String loanBankAcctNo) {
        this.loanBankAcctNo = loanBankAcctNo;
    }

    public int getLoanAcctID() {
        return loanAcctID;
    }

    public void setLoanAcctID(int loanAcctID) {
        this.loanAcctID = loanAcctID;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public Market getLoan_Market() {
        return loan_Market;
    }

    public void setLoan_Market(Market loan_Market) {
        this.loan_Market = loan_Market;
    }

    public int getLoanDuration() {
        return loanDuration;
    }

    public void setLoanDuration(int loanDuration) {
        this.loanDuration = loanDuration;
    }
}



