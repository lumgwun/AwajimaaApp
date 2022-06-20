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
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_NUMBER;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;

@Entity(tableName = Account.ACCOUNTS_TABLE)
public class Account implements Serializable, Parcelable {
    public static final String ACCOUNTS_TABLE = "accounts";
    public static final String ACCOUNT_BANK = "account_bank";
    public static final String ACCOUNT_NO = "a_id";
    public static final String ACCOUNT_NAME = "account_name";
    public static final String ACCOUNT_BALANCE = "account_balance";
    public static final String ACCOUNT_TYPE = "account_type";
    public static final String ACCOUNT_EXPECTED_SAVINGS = "total_amount_exp";
    public static final String ACCOUNT_SAVED_AMOUNT = "saved_amount";
    public static final String BANK_ACCT_NO = "acct_id";


    public static final String ACCOUNT_TYPES_TABLE = "account_type";
    public static final String ACCOUNT_TYPE_INTEREST = "interest";
    public static final String ACCOUNT_TYPE_NAME = "type_name";
    public static final String ACCOUNT_TYPE_BANK = "type_bank";
    public static final String ACCOUNT_TYPE_NUMBER = "type_number";






    public static final String CREATE_ACCOUNT_TYPE_TABLE = "CREATE TABLE " + ACCOUNT_TYPES_TABLE + " (" + ACCOUNT_TYPE_NUMBER + " INTEGER, " +
            ACCOUNT_NO + " INTEGER, " + ACCOUNT_TYPE + " TEXT , " + ACCOUNT_TYPE_INTEREST + " DOUBLE , " +
            "PRIMARY KEY(" + ACCOUNT_TYPE_NUMBER + "), " + "FOREIGN KEY(" + ACCOUNT_NO + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + "))";


    public static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE IF NOT EXISTS " + ACCOUNTS_TABLE + " (" + ACCOUNT_NO + " INTEGER , " + BANK_ACCT_NO + "LONG," + PROFILE_ID + " INTEGER , " +
            CUSTOMER_ID + " INTEGER , " + REPORT_NUMBER + " INTEGER , " + PACKAGE_ID + " INTEGER , " + TRANSACTION_ID + " INTEGER , " +
            ACCOUNT_TYPE + " TEXT , " + ACCOUNT_BANK + " TEXT , " + ACCOUNT_NAME + " TEXT, " + ACCOUNT_BALANCE + " DOUBLE, " +
            ACCOUNT_EXPECTED_SAVINGS + " TEXT, " + ACCOUNT_SAVED_AMOUNT + " DOUBLE, " + "FOREIGN KEY(" + TRANSACTION_ID + ") REFERENCES " + TRANSACTIONS_TABLE + "(" + TRANSACTION_ID + ")," +
            "PRIMARY KEY(" + ACCOUNT_NO + "))";

    public enum ACCOUNT_TYPE {
        WALLET, STANDING_ORDER, GROUP_SAVINGS;
    }


    private static int nextAcNum = 88769912;
    private String username, password;

    DBHelper dbHelper;

    String name;
    String bank;
    String sortCode;
    String country;
    Transaction transaction;
    CustomerDailyReport record;
    Loan loan;
    SkylightPackageModel shopPackage;
    SkyLightPackage skyLightPackage;
    double accountBalance;
    double totalAmountToSave;
    double interest;
    private String image;
    private BigDecimal balance1 = null;
    private BigDecimal interestRate = BigDecimal.ZERO;

    @PrimaryKey(autoGenerate = true)
    private int acctID=100321;
    private String number;
    private int productId;
    private long bankAcct_No;
    private Currency currency;
    int grpAcctNo;
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
    public Account(int acctNo, int acctID) {
        this.grpAcctNo = acctNo;
        this.acctID = acctID;

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
    public Account (String uName, String pass, double bal,String bank) {
        this.transactions = new ArrayList<>();
        this.username = uName;
        this.password = pass;
        this.number = String.valueOf(nextAcNum);
        this.accountBalance = bal;

        //increment account number, so next one will be updated
        nextAcNum++;
    }
    public Account(String name, int dbID, Currency currency, String country, String sortCode, int accountNo, double accountBalance) {
        this(name, accountNo, BigDecimal.valueOf(accountBalance), String.valueOf(currency),country,sortCode);
        this.acctID = dbID;
        this.name = name;
        this.accountBalance = accountBalance;
        this.sortCode = sortCode;
        this.country = country;
        transactions = new ArrayList<>();

    }

    public Account(String name, int accountNo, BigDecimal accountBalance, String currency, String country, String sortCode) {

        super();
    }

    public Account(String accountName, String accNo, double accountBalance, int acctID) {
        this.name = accountName;
        this.number = accNo;
        this.accountBalance = accountBalance;
        this.acctID = acctID;
    }
    public Account(String accountBank, String accountNo,String authorization) {
        this.accountBalance = accountBalance;
        this.number = accountNo;
        this.bank = accountBank;
        this.accountBalance = accountBalance;
    }

    public Account(String skylightMFb, String accountName, long accountNumber, double accountBalance, AccountTypes accountTypeStr) {
        this.accountBalance = accountBalance;
        this.name = accountName;
        this.number = String.valueOf(accountNumber);
        this.type = accountTypeStr;
        this.bank = skylightMFb;
        this.accountBalance = accountBalance;
    }

    public Account(int acctID, String accountBank, String accountName, String accountNo, double accountBalance, AccountTypes accountType) {
        this.acctID = acctID;
        this.accountBalance = accountBalance;
        this.name = accountName;
        this.number = accountNo;
        this.type = accountType;
        this.bank = accountBank;
        this.accountBalance = accountBalance;
    }

    public Account(int virtualAccountID, String customerBank, String customerNames, long accountNo, double accountBalance, AccountTypes accountTypeStr) {
        this.acctID = virtualAccountID;
        this.accountBalance = accountBalance;
        this.name = customerNames;
        this.number = String.valueOf(accountNo);
        this.type = accountTypeStr;
        this.bank = customerBank;
        this.accountBalance = accountBalance;
    }

    public Account(int virtualAccountID, String customerNames, double accountBalance, AccountTypes accountTypeStr) {
        this.acctID = virtualAccountID;
        this.accountBalance = accountBalance;
        this.name = customerNames;
        this.type = accountTypeStr;
        this.accountBalance = accountBalance;
    }

    public Account(String customerBank, String customerNames, int accountNo, AccountTypes accountTypeStr) {
        this.name = customerNames;
        this.type = accountTypeStr;
        this.acctID = accountNo;
        this.bank = customerBank;
    }

    public Account(String reportID, int accountNumber) {
        this.acctID = accountNumber;

    }



    public void setBalance(BigDecimal balance1) {
        if (this.balance1 == null) {
            this.balance1 = balance1;
        } else if (balance1 != null) {
            this.balance1 = balance1.setScale(2, BigDecimal.ROUND_HALF_UP);
            // set the balance of the account in the database
            dbHelper.updateAccountBalance(balance1.setScale(2, BigDecimal.ROUND_HALF_UP),
                    this.id_For_BigDecimal);
        }
    }
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


    public void setAcctID(int id_For_BigDecimal) {
        if (id_For_BigDecimal > 0) {
            this.id_For_BigDecimal = id_For_BigDecimal;
        }
    }

    public void setBankAcctName(String name) {
        if (name != null && name.length() > 0) {
            // check if the name is being updated
            if (!this.name.equals("")) {
                // update the name in the database
                dbHelper.updateProfileUserName(name, this.id_For_BigDecimal);
            }
            this.name = name;
        }
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

    protected Account(Parcel in) {
        name = in.readString();
        bank = in.readString();
        sortCode = in.readString();
        country = in.readString();
        accountBalance = in.readDouble();
        image = in.readString();
        accountBalance = in.readDouble();
        acctID = in.readInt();
        number = in.readString();
        productId = in.readInt();
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

    public Account() {

    }

    public Account(String accountBank, String accountName, String accountNumber, double accountBalance) {
        this.name = accountName;
        this.number = accountNumber;
        this.bank = accountBank;
        this.accountBalance = accountBalance;

    }
    public Account(int acctID, String bankName, String accountName, String bankNumber) {
        this.name = accountName;
        this.acctID = acctID;
        this.number = bankNumber;
        this.bank = bankName;

    }

    public void addTransaction(Transaction t) {
        this.transactions.add(t);
    }

    //getters and setters
    public String getUserName() {
        return username;
    }


    public void setTotalAmountToSave(double totalAmountToSave) {
        this.totalAmountToSave = totalAmountToSave;
    }

    public double getTotalAmountToSave() {
        return this.totalAmountToSave;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }


    public double setBalance(double accountBalance) {
        this.accountBalance = accountBalance;
        return accountBalance;
    }
    public long getBankAcct_No() {
        return this.bankAcct_No;
    }

    public void setBankAcctNo(long bankAcct_No) {
        this.bankAcct_No = bankAcct_No;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public int getAcctID() {
        return acctID;
    }
    /*public void setId(int id) {
        this.acctID = id;
    }*/

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getBank() {
        return bank;
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
    /*public void setTransactions(Transaction transaction) {
        this.transaction = transaction;
    }
    public Transaction getTransaction() {
        return transaction;
    }*/

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

    public String toString() {
        return (name + ""+this.number+" (NGN" + String.format(Locale.getDefault(), "%.2f",this.accountBalance) + ")");
    }

    public String toTransactionString() { return (name + " (" + number + ")"); }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }


    //@Override
    public int compareTo(Object o) {
        return 0;
    }

    public String getAccountName() {
        return name;
    }

    public void addBorrowingTransaction(double borrowedAmount) {

    }

    public Object getType() {
        return type;
    }

}
