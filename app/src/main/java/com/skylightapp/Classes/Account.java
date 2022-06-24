package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.skylightapp.Database.DBHelper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;

@Entity(tableName = Account.ACCOUNTS_TABLE)
public class Account implements Serializable, Parcelable {
    private BigDecimal savingsAcctBalance;
    private BigDecimal itemAcctBalance;

    public Account(){
        super();

    }
    public static final String ACCOUNTS_TABLE = "accounts";
    public static final String ACCOUNT_BANK = "account_bank";
    public static final String ACCOUNT_NO = "a_id";
    public static final String ACCOUNT_NAME = "account_name";
    public static final String ACCOUNT_BALANCE = "account_balance";
    public static final String ACCOUNT_TYPE = "account_type";
    public static final String ACCOUNT_EXPECTED_SAVINGS = "acct_total_amount_exp";
    public static final String ACCOUNT_SAVED_AMOUNT = "acct_saved_amount";
    public static final String BANK_ACCT_NO = "bank_acct_no";
    public static final String BANK_ACCT_BALANCE = "bank_acct_Balance";

    public static final String ACCOUNT_TYPES_TABLE = "account_type";
    public static final String ACCOUNT_TYPE_INTEREST = "interest";
    public static final String ACCOUNT_TYPE_NAME = "type_name";
    public static final String ACCOUNT_TYPE_BANK = "type_bank";
    public static final String ACCOUNT_TYPE_ID = "type_number";
    public static final String ACCOUNT_CUS_ID = "acct_cus__number";
    public static final String ACCOUNT_TYPE_NO_F = "acct_F_Key";
    public static final String ACCOUNT_PROF_ID = "acct_prof_number";
    public static final String ACCOUNT_TX_ID = "acct_TX_ID";


    public static final String CREATE_ACCOUNT_TYPE_TABLE = "CREATE TABLE " + ACCOUNT_TYPES_TABLE + " (" + ACCOUNT_TYPE_ID + " INTEGER, " +
            ACCOUNT_TYPE_NO_F + " INTEGER, " + ACCOUNT_TYPE + " TEXT , " + ACCOUNT_TYPE_INTEREST + " FLOAT , " +
            "PRIMARY KEY(" + ACCOUNT_TYPE_ID + "), " + "FOREIGN KEY(" + ACCOUNT_TYPE_NO_F + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + "))";


    public static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE IF NOT EXISTS " + ACCOUNTS_TABLE + " (" + ACCOUNT_NO + " INTEGER , " + BANK_ACCT_NO + "TEXT," + ACCOUNT_PROF_ID + " INTEGER , " +
            ACCOUNT_CUS_ID + " INTEGER , " + ACCOUNT_TX_ID + " INTEGER , " +
            ACCOUNT_TYPE + " TEXT , " + ACCOUNT_BANK + " TEXT , " + ACCOUNT_NAME + " TEXT, " + ACCOUNT_BALANCE + " FLOAT, " +
            ACCOUNT_EXPECTED_SAVINGS + " TEXT, " + ACCOUNT_SAVED_AMOUNT + " FLOAT, " + BANK_ACCT_BALANCE + "FLOAT, "+"FOREIGN KEY(" + ACCOUNT_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + ACCOUNT_CUS_ID  + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," + "FOREIGN KEY(" + ACCOUNT_TX_ID + ") REFERENCES " + TRANSACTIONS_TABLE + "(" + TRANSACTION_ID + ")," +
            "PRIMARY KEY(" + ACCOUNT_NO + "))";

    protected Account(Parcel in) {
        skyLightAcctNo = in.readInt();
        bankAcct_No = in.readString();
        grpAcctNo = in.readInt();
        accountBalance = in.readDouble();
        skylightAccountName = in.readString();
        bankAccountName = in.readString();
        bankAccountBalance = in.readString();
        bankName = in.readString();
        sortCode = in.readString();
        country = in.readString();
        transaction = in.readParcelable(Transaction.class.getClassLoader());
        record = in.readParcelable(CustomerDailyReport.class.getClassLoader());
        loan = in.readParcelable(Loan.class.getClassLoader());
        skyLightPackage = in.readParcelable(SkyLightPackage.class.getClassLoader());
        totalAmountToSave = in.readDouble();
        interest = in.readDouble();
        image = in.readString();
        transactions = in.createTypedArrayList(Transaction.CREATOR);
        packages = in.createTypedArrayList(SkyLightPackage.CREATOR);
        DailyPayments = in.createTypedArrayList(CustomerDailyReport.CREATOR);
        id_For_BigDecimal = in.readInt();
        type_BigDecimal = in.readInt();
        //loans = in.createTypedArrayList(Loan.CREATOR);
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getSkylightAccountName() {
        return skylightAccountName;
    }

    public void setSkylightAccountName(String skylightAccountName) {
        this.skylightAccountName = skylightAccountName;
    }

    public String getBankAccountBalance() {
        return bankAccountBalance;
    }

    public void setBankAccountBalance(String bankAccountBalance) {
        this.bankAccountBalance = bankAccountBalance;
    }

    protected void setPromoAcctName(String name) {

    }

    public void setSavingsAcctBalance(BigDecimal savingsAcctBalance) {
        this.savingsAcctBalance = savingsAcctBalance;
    }

    public BigDecimal getSavingsAcctBalance() {
        return savingsAcctBalance;
    }

    public void setItemAcctBalance(BigDecimal itemAcctBalance) {
        this.itemAcctBalance = itemAcctBalance;
    }

    public BigDecimal getItemAcctBalance() {
        return itemAcctBalance;
    }

    public enum ACCOUNT_TYPE {
        WALLET, STANDING_ORDER, GROUP_SAVINGS;
    }

    private static int nextAcNum = 88769912;
    @PrimaryKey(autoGenerate = true)
    private int skyLightAcctNo =100321;

    private String bankAcct_No;
    int grpAcctNo;
    double accountBalance;
    DBHelper dbHelper;

    private String skylightAccountName;
    private String bankAccountName;
    private String bankAccountBalance;

    String bankName;
    private Currency currency;
    String sortCode;
    String country;
    Transaction transaction;
    CustomerDailyReport record;
    Loan loan;
    SkylightPackageModel shopPackage;
    SkyLightPackage skyLightPackage;

    double totalAmountToSave;
    double interest;
    private String image;
    private BigDecimal balance1 = null;
    private BigDecimal interestRate = BigDecimal.ZERO;


    AccountTypes type;
    private ArrayList<Transaction> transactions;
    private ArrayList<SkyLightPackage> packages;
    private ArrayList<SkylightPackageModel> shopPackages;
    private ArrayList<CustomerDailyReport> DailyPayments;
    private ArrayList<Promo> promos;
    private Promo promo;
    private int id_For_BigDecimal = -1;
    private int type_BigDecimal = -1;

    private ArrayList<Loan> loans;

    protected AccountTypesEnumMap enumMap;

    public Account(String accountName, double accountBalance) {

        super();
    }
    public Account(int acctNo, int skyLightAcctNo) {
        this.grpAcctNo = acctNo;
        this.skyLightAcctNo = skyLightAcctNo;

    }
    /*public Account(String uName, String pass) {
        this.transactions = new ArrayList<>();
        this.username = uName;
        this.password = pass;
        this.number = String.valueOf(nextAcNum);
        this.balance = 0;
        nextAcNum++;
        transactions = new ArrayList<>();

    }*/
    public Account (String uName, String pass, double bal,String bankName) {
        this.transactions = new ArrayList<>();
        this.skyLightAcctNo = nextAcNum;
        this.accountBalance = bal;
        nextAcNum++;
    }
    public Account(String name, int dbID, Currency currency, String country, String sortCode, int accountNo, double accountBalance) {
        this(name, accountNo, BigDecimal.valueOf(accountBalance), String.valueOf(currency),country,sortCode);
        this.skyLightAcctNo = dbID;
        this.skylightAccountName = name;
        this.accountBalance = accountBalance;
        this.sortCode = sortCode;
        this.country = country;
        transactions = new ArrayList<>();

    }

    public Account(String name, int accountNo, BigDecimal accountBalance, String currency, String country, String sortCode) {

        super();
    }

    public Account(String accountName, String bankAcct_No, double accountBalance, int skyLightAcctNo) {
        this.skylightAccountName = accountName;
        this.bankAcct_No = bankAcct_No;
        this.accountBalance = accountBalance;
        this.skyLightAcctNo = skyLightAcctNo;
    }
    public Account(String accountBank, String accountNo,String authorization) {
        this.accountBalance = accountBalance;
        this.bankAcct_No = accountNo;
        this.bankName = accountBank;
        this.accountBalance = accountBalance;
    }

    public Account(String skylightMFb, String accountName, int accountNumber, double accountBalance, AccountTypes accountTypeStr) {
        this.accountBalance = accountBalance;
        this.skylightAccountName = accountName;
        this.skyLightAcctNo = accountNumber;
        this.type = accountTypeStr;
        this.bankName = skylightMFb;

    }

    public Account(int skyLightAcctNo, String accountBank, String accountName, String accountNo, double accountBalance, AccountTypes accountType) {
        this.skyLightAcctNo = skyLightAcctNo;
        this.accountBalance = accountBalance;
        this.skylightAccountName = accountName;
        this.bankAcct_No = accountNo;
        this.type = accountType;
        this.bankName = accountBank;
    }


    public Account(int virtualAccountID, String customerNames, double accountBalance, AccountTypes accountTypeStr) {
        this.skyLightAcctNo = virtualAccountID;
        this.accountBalance = accountBalance;
        this.skylightAccountName = customerNames;
        this.type = accountTypeStr;
        this.accountBalance = accountBalance;
    }

    public Account(String customerBank, String customerNames, int accountNo, AccountTypes accountTypeStr) {
        this.skylightAccountName = customerNames;
        this.type = accountTypeStr;
        this.skyLightAcctNo = accountNo;
        this.bankName = customerBank;
    }

    public Account(String reportID, int accountNumber) {
        this.skyLightAcctNo = accountNumber;

    }



    /*public void setBalance(BigDecimal balance1) {
        if (this.balance1 == null) {
            this.balance1 = balance1;
        } else if (balance1 != null) {
            this.balance1 = balance1.setScale(2, BigDecimal.ROUND_HALF_UP);
            // set the balance of the account in the database
            dbHelper.updateAccountBalance(balance1.setScale(2, BigDecimal.ROUND_HALF_UP),
                    this.id_For_BigDecimal);
        }
    }*/
    public Promo getPromo() {
        return promo;
    }
    public void setPromo(Promo promo1) {
        this.promo = promo1;
    }

    public ArrayList<Promo> getPromos() {
        return promos;
    }
    public void setPromos(ArrayList<Promo> promoArrayList) {
        this.promos = promoArrayList;
    }
    public void findAndSetInterestRate() {
        //this.interestRate = dbHelper.getI(this.getType_BigDecimal());
    }
    public void addInterest() {
        this.findAndSetInterestRate();
        //BigDecimal toAdd = this.getBalance().multiply(interestRate);
        //BigDecimal newBalance = this.getBalance().add(toAdd);
        //this.setBalance(newBalance);
    }
    public void setInterest(double interest) {
        this.interest = interest;
    }

    public int getId_For_BigDecimal() {
        return this.id_For_BigDecimal;
    }


    public void setSkyLightAcctNo(int acctNo) {
        this.skyLightAcctNo = acctNo;
    }

    public void setBankAcctName(String bankName) {
        this.bankAccountName = bankName;

    }
    public BigDecimal getBalanceInBigDecimal() {
        return this.balance1;
    }
    public int getType_BigDecimal() {
        return this.type_BigDecimal;
    }

    public void setType_BigDecimal(int accountType) {
        this.type_BigDecimal = accountType;
    }



    public double getInterest() {
        return this.interest;
    }



    public Account(String accountBank, String accountName, String accountNumber, double accountBalance) {
        this.bankAccountName = accountName;
        this.bankAcct_No = accountNumber;
        this.bankName = accountBank;
        this.accountBalance = accountBalance;

    }


    public void setTotalAmountToSave(double totalAmountToSave) {
        this.totalAmountToSave = totalAmountToSave;
    }



    public double setBalance(double accountBalance) {
        this.accountBalance = accountBalance;
        return accountBalance;
    }
    public String getBankAcct_No() {
        return this.bankAcct_No;
    }

    public void setBankAcctNo(String bankAcct_No) {
        this.bankAcct_No = bankAcct_No;
    }




    public int getSkyLightAcctNo() {
        return skyLightAcctNo;
    }
    /*public void setId(int id) {
        this.acctID = id;
    }*/


    public String getAccName() {
        return skylightAccountName;
    }
    public void setAcctName(String name) {
        this.skylightAccountName = name;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        currency.getCurrencyCode();
        currency.getDisplayName();
        return currency;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankName() {
        return bankName;
    }

    public String getSortCode() {
        return sortCode;
    }
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }


    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }


    public SkyLightPackage getSkyLightPackage() {
        return skyLightPackage;
    }
    public void setSkyLightPackage(SkyLightPackage skyLightPackage) {
        this.skyLightPackage = skyLightPackage;
    }
    public Loan getLoan() {
        return loan;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public SkylightPackageModel getShopPackage() {
        return shopPackage;
    }
    public void setTransactions(SkylightPackageModel loan) {
        this.shopPackage = shopPackage;
    }
    public double getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public ArrayList<Transaction> getTransactions1() {
        return transactions;
    }
    public ArrayList<SkyLightPackage> getpackages() {
        return packages;
    }
    public ArrayList<SkylightPackageModel> getShopPackages() {
        return shopPackages;
    }
    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public ArrayList<CustomerDailyReport> getDailyPayments() {
        return DailyPayments;
    }
    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
    public void setPackages(ArrayList<SkyLightPackage> packagesFromCurrentProfile) {
        this.packages = packagesFromCurrentProfile;


    }
    public void setDailySavings(ArrayList<CustomerDailyReport> savingsFromCurrentProfile) {
        this.DailyPayments = savingsFromCurrentProfile;


    }
    public void setLoans(ArrayList<Loan> loansFromCurrentProfile) {
        this.loans = loansFromCurrentProfile;


    }


    public void addPaymentTransaction (String payee, double amount) {
        accountBalance -= amount;

        int paymentCount = 0;

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT)  {
                paymentCount++;
            }
        }

        //Transaction payment = new Transaction("T" + (transactions.size() + 1) + "-P" + (paymentCount+1), payee, amount);
        //transactions.add(payment);
    }

    public void addDepositTransaction(double amount) {
        accountBalance += amount;

        int depositsCount = 0;

        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.DEPOSIT)  {
                depositsCount++;
            }
        }

        //Transaction deposit = new Transaction("T" + (transactions.size() + 1) + "-D" + (depositsCount+1), amount);
       // transactions.add(deposit);
    }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(skyLightAcctNo);
        parcel.writeString(bankAcct_No);
        parcel.writeInt(grpAcctNo);
        parcel.writeDouble(accountBalance);
        parcel.writeString(skylightAccountName);
        parcel.writeString(bankAccountName);
        parcel.writeString(bankAccountBalance);
        parcel.writeString(bankName);
        parcel.writeString(sortCode);
        parcel.writeString(country);
        parcel.writeParcelable(transaction, i);
        parcel.writeParcelable(record, i);
        parcel.writeParcelable(loan, i);
        parcel.writeParcelable(skyLightPackage, i);
        parcel.writeDouble(totalAmountToSave);
        parcel.writeDouble(interest);
        parcel.writeString(image);
        parcel.writeTypedList(transactions);
        parcel.writeTypedList(packages);
        parcel.writeTypedList(DailyPayments);
        parcel.writeInt(id_For_BigDecimal);
        parcel.writeInt(type_BigDecimal);
        parcel.writeTypedList(loans);
    }


    //@Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getAccountName() {
        return skylightAccountName;
    }

    public void addBorrowingTransaction(double borrowedAmount) {

    }

    public Object getType() {
        return type;
    }

}
