
package com.skylightapp.Classes;

import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = Loan.LOAN_TABLE)
public class Loan extends Account implements Serializable, Parcelable {
    public static final String LOAN_ID = "loan_id";
    public static final String LOAN_TYPE = "loanType";
    public static final String LOAN_AMOUNT = "amount";
    public static final String LOAN_INTEREST = "interest";
    public static final String LOAN_FIXED_PAYMENT = "fixed_payment";
    public static final String LOAN_TOTAL_INTEREST = "total_interests";
    public static final String LOAN_DOWN_PAYMENT = "down_payment";
    public static final String LOAN_DISPOSABLE_COM = "disposable_commission";
    public static final String LOAN_MONTHLY_COM = "monthly_commission";
    public static final String LOAN_BALANCE = "residue_payment";
    public static final String LOAN_DATE = "date";
    public static final String LOAN_START_DATE = "start_date";
    public static final String LOAN_END_DATE = "end_date";
    public static final String LOAN_STATUS = "status";
    public static final String LOAN_DURATION = "loan_duration";
    public static final String LOAN_CUSTOMER = "loan_cus";
    public static final String LOAN_CODE = "loan_code";
    public static final String LOAN_TABLE = "loan_table";

    public static final String CREATE_LOAN_TABLE = "CREATE TABLE IF NOT EXISTS " + LOAN_TABLE + " (" + LOAN_ID + " INTEGER , " +
            CUSTOMER_ID + " INTEGER NOT NULL, " +PROFILE_ID + " INTEGER  , " + ACCOUNT_NO + " INTEGER NOT NULL, " + LOAN_TYPE + " TEXT, " + LOAN_AMOUNT + " DOUBLE, " + LOAN_DURATION + " TEXT, " +
            LOAN_INTEREST + " TEXT, " + LOAN_FIXED_PAYMENT + " TEXT, " + LOAN_TOTAL_INTEREST + " TEXT, " + LOAN_DOWN_PAYMENT + " TEXT, " + LOAN_DISPOSABLE_COM + " TEXT, " +
            LOAN_MONTHLY_COM + " TEXT, " + LOAN_BALANCE + " TEXT, " + LOAN_DATE + " TEXT, " + LOAN_START_DATE + " TEXT, " + LOAN_END_DATE + " TEXT, " +
            LOAN_STATUS + " TEXT, " + LOAN_CUSTOMER + " TEXT, " + LOAN_CODE + " LONG, " +"FOREIGN KEY(" + PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "PRIMARY KEY(" + LOAN_ID  + "), " +
            "FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    private static final long serialVersionUID = 1L;

    public static final int PERCENT = 0;
    public static final int VALUE = 1;
    private boolean calculated;
    private String date;
    private String startDate;
    private String endDate;
    private String status;
    private String loanOfficeBranch;
    Profile profile;
    Customer customer;
    Payment payment;
    Account account;
    private TransactionGranting granting;
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
        this.date = loanDate;
        this.startDate = loanStartDate;
        this.endDate = loanEndDate;
        this.status = loanStatus;
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
        this.date = loanDate;
        this.startDate = loanStartDate;
        this.endDate = loanEndDate;
        this.status = loanStatus;
    }
    public Loan(Profile profile1, Customer customer1, Payment payment1) {
        this.payment = payment1;
        this.profile = profile1;
        this.customer = customer1;
    }
    public Loan(Profile profile1, Customer customer1, Account account1, Payment payment1) {
        this.payment = payment1;
        this.profile = profile1;
        this.account = account1;
        this.customer = customer1;
    }

    public Loan() {

    }

    public Loan(int loanNumber,BigDecimal amount,String date,String status) {
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
    public Loan(int loanId,double amount, String loanStartDate, String status,String loanEndDate,double mInterest) {
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
    public Loan(double amount, String loanStartDate, String status,String loanEndDate,double interest) {
        this.loanId = loanId;
        this.amount1 = amount;
        this.mInterest = interest;
        this.endDate = loanEndDate;
        this.mInterest = interest;
        this.startDate = loanStartDate;
        this.status = status;


    }

    public Loan(int profileID, int customerID, int loanID, double loanAmount, String loanDate, String startDate, String endDate, String status) {
        this.loanId = loanID;
        this.amount1 = loanAmount;
        this.date = loanDate;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
    }


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

    public String getDate() {
        return date;
    }
    public String getStartDate() {
        return startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public void setLoanType(String date) {
        this.date = date;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public void setCustomer(Customer customer1) {
        this.customer = customer1;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setProfile(Profile profile1) {
        this.profile = profile1;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setAccount(Account account1) {
        this.account = account1;
    }

    public Account getAccount() {
        return account;
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

    public TransactionGranting getGranting() {
        return granting;
    }

    public void setGranting(TransactionGranting granting) {
        this.granting = granting;
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
}



