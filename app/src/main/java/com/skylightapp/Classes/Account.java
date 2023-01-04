package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;



import com.blongho.country_data.Currency;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.MarketClasses.MarketBizPackage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;


import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.Transaction.TRANSACTIONS_TABLE;
import static com.skylightapp.Classes.Transaction.TRANSACTION_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;

//@Entity(tableName = Account.ACCOUNTS_TABLE)
public class Account implements Serializable, Parcelable {
    private BigDecimal savingsAcctBalance;
    private BigDecimal itemAcctBalance;
    private String acctType;

    public Account(){
        super();

    }
    public static final String ACCOUNTS_TABLE = "accounts_table";
    public static final String ACCOUNT_BANK = "account_bank";
    public static final String ACCOUNT_NO = "account_id";
    public static final String ACCOUNT_NAME = "account_name";
    public static final String ACCOUNT_BALANCE = "account_balance";
    public static final String ACCOUNT_TYPE = "account_type";
    public static final String ACCOUNT_EXPECTED_SAVINGS = "acct_total_amount_exp";
    public static final String ACCOUNT_SAVED_AMOUNT = "acct_saved_amount";
    public static final String BANK_ACCT_NO = "bank_acct_no";
    public static final String BANK_ACCT_BALANCE = "bank_acct_Balance";

    public static final String ACCOUNT_TYPES_TABLE = "account_type_table";
    public static final String ACCOUNT_TYPE_INTEREST = "acct_type_interest";
    public static final String ACCOUNT_TYPE_NAME = "acct_type_name";
    public static final String ACCOUNT_CURRENCY = "acct_Currency";
    public static final String ACCOUNT_TYPE_ID = "acct_type_id";
    public static final String ACCOUNT_CUS_ID = "acct_cus__number";
    public static final String ACCOUNT_TYPE_NO_F = "acct_F_Key";
    public static final String ACCOUNT_PROF_ID = "acct_prof_number";
    public static final String ACCOUNT_TX_ID = "acct_TX_ID";
    public static final String ACCOUNT_MARKET_BUSINESS_ID = "acct_Biz_ID";
    public static final String ACCOUNT_MARKET_ID = "acct_Market_ID";
    public static final String ACCOUNT_DB_ID = "acct_DB_ID";
    public static final String ACCOUNT_IBAN = "acct_Iban";
    public static final String ACCOUNT_STATUS = "acct_Status";
    public static final String ACCOUNT_TYPE_NEW = "acct_Type_New";


    public static final String CREATE_ACCOUNT_TYPE_TABLE = "CREATE TABLE " + ACCOUNT_TYPES_TABLE + " (" + ACCOUNT_TYPE_ID + " INTEGER, " +
            ACCOUNT_TYPE_NO_F + " INTEGER, " + ACCOUNT_TYPE + " TEXT , " + ACCOUNT_TYPE_INTEREST + " REAL , " +
            "PRIMARY KEY(" + ACCOUNT_TYPE_ID + "), " + "FOREIGN KEY(" + ACCOUNT_TYPE_NO_F + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + "))";


    public static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE IF NOT EXISTS " + ACCOUNTS_TABLE + " (" + ACCOUNT_NO + " INTEGER , " + BANK_ACCT_NO + "TEXT," + ACCOUNT_PROF_ID + " INTEGER , " +
            ACCOUNT_CUS_ID + " INTEGER , " + ACCOUNT_TX_ID + " INTEGER , " +
            ACCOUNT_TYPE_NEW + " TEXT , " + ACCOUNT_BANK + " TEXT , " + ACCOUNT_NAME + " TEXT, " + ACCOUNT_BALANCE + " REAL, " +
            ACCOUNT_EXPECTED_SAVINGS + " TEXT, " + ACCOUNT_SAVED_AMOUNT + " REAL, " + BANK_ACCT_BALANCE + "REAL, "+ ACCOUNT_DB_ID + "INTEGER, "+ ACCOUNT_CURRENCY + " TEXT , "+ ACCOUNT_IBAN + " REAL, " + ACCOUNT_STATUS + " TEXT , "+ "FOREIGN KEY(" + ACCOUNT_MARKET_BUSINESS_ID + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "),"+ "FOREIGN KEY(" + ACCOUNT_MARKET_ID + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + "),"+"FOREIGN KEY(" + ACCOUNT_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + ACCOUNT_CUS_ID  + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," + "FOREIGN KEY(" + ACCOUNT_TX_ID + ") REFERENCES " + TRANSACTIONS_TABLE + "(" + TRANSACTION_ID + ")," +
            "PRIMARY KEY(" + ACCOUNT_DB_ID + "))";

    protected Account(Parcel in) {
        awajimaAcctNo = in.readInt();
        bankAcct_No = in.readString();
        grpAcctNo = in.readInt();
        accountBalance = in.readDouble();
        awajimaAcctName = in.readString();
        bankAccountName = in.readString();
        bankAccountBalance = in.readString();
        bankName = in.readString();
        sortCode = in.readString();
        country = in.readString();
        transaction = in.readParcelable(Transaction.class.getClassLoader());
        record = in.readParcelable(CustomerDailyReport.class.getClassLoader());
        loan = in.readParcelable(Loan.class.getClassLoader());
        marketBizPackage = in.readParcelable(MarketBizPackage.class.getClassLoader());
        totalAmountToSave = in.readDouble();
        interest = in.readDouble();
        image = in.readString();
        transactions = in.createTypedArrayList(Transaction.CREATOR);
        packages = in.createTypedArrayList(MarketBizPackage.CREATOR);
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

    public String getAwajimaAcctName() {
        return awajimaAcctName;
    }

    public void setAwajimaAcctName(String awajimaAcctName) {
        this.awajimaAcctName = awajimaAcctName;
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

    public int getAcctBizID() {
        return acctBizID;
    }

    public void setAcctBizID(int acctBizID) {
        this.acctBizID = acctBizID;
    }

    public int getAcctMarketID() {
        return acctMarketID;
    }

    public void setAcctMarketID(int acctMarketID) {
        this.acctMarketID = acctMarketID;
    }

    public int getAcctBizDealID() {
        return acctBizDealID;
    }

    public void setAcctBizDealID(int acctBizDealID) {
        this.acctBizDealID = acctBizDealID;
    }

    public String getAccountIban() {
        return accountIban;
    }

    public void setAccountIban(String accountIban) {
        this.accountIban = accountIban;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountCurrSymbol() {
        return accountCurrencyCode;
    }

    public void setAccountCurrencyCode(String accountCurrencyCode) {
        this.accountCurrencyCode = accountCurrencyCode;
    }

    public int getAcctCusID() {
        return acctCusID;
    }

    public void setAcctCusID(int acctCusID) {
        this.acctCusID = acctCusID;
    }

    public int getAcctProfID() {
        return acctProfID;
    }

    public void setAcctProfID(int acctProfID) {
        this.acctProfID = acctProfID;
    }

    public long getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(long acctNo) {
        this.acctNo = acctNo;
    }

    /*public enum ACCOUNT_TYPE {
        WALLET, STANDING_ORDER, GROUP_SAVINGS,BANK;
    }*/

    private static int nextAcNum = 88769912;
    //@PrimaryKey(autoGenerate = true)
    private int awajimaAcctNo ;

    private String bankAcct_No;
    int grpAcctNo;
    double accountBalance;
    DBHelper dbHelper;

    private String awajimaAcctName;
    private String bankAccountName;
    private String bankAccountBalance;

    String bankName;
    private Currency currency;
    String sortCode;
    String country;

    Transaction transaction;
    CustomerDailyReport record;
    Loan loan;
    AwajimaPackModel shopPackage;
    MarketBizPackage marketBizPackage;

    double totalAmountToSave;
    double interest;
    private int acctBizID;
    private int acctMarketID;
    private int acctBizDealID;
    private String image;
    private BigDecimal balance1 = null;
    private BigDecimal interestRate = BigDecimal.ZERO;


    AccountTypes type;
    private ArrayList<Transaction> transactions;
    private ArrayList<MarketBizPackage> packages;
    private ArrayList<AwajimaPackModel> shopPackages;
    private ArrayList<CustomerDailyReport> DailyPayments;
    private ArrayList<Promo> promos;
    private Promo promo;
    private int id_For_BigDecimal = -1;
    private int type_BigDecimal = -1;

    private ArrayList<Loan> loans;

    protected AccountTypesEnumMap enumMap;
    private String accountIban;
    private String accountStatus;
    private String accountCurrencyCode;
    private int acctCusID;
    private int acctProfID;
    private long acctNo;

    public Account(String accountName, double accountBalance) {

        super();
    }
    public Account(int acctNo, int awajimaAcctNo) {
        this.grpAcctNo = acctNo;
        this.awajimaAcctNo = awajimaAcctNo;

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
        this.awajimaAcctNo = nextAcNum;
        this.accountBalance = bal;
        nextAcNum++;
    }
    public Account(String name, int dbID, Currency currency, String country, String sortCode, int accountNo, double accountBalance) {
        this(name, accountNo, BigDecimal.valueOf(accountBalance), String.valueOf(currency),country,sortCode);
        this.awajimaAcctNo = dbID;
        this.awajimaAcctName = name;
        this.accountBalance = accountBalance;
        this.sortCode = sortCode;
        this.country = country;
        transactions = new ArrayList<>();

    }

    public Account(String name, int accountNo, BigDecimal accountBalance, String currency, String country, String sortCode) {

        super();
    }
    public Account(long acctID, String bank, String accountName, String bankAccountNo, double accountBalance, AccountTypes valueOf, String currency, String status) {
        super();
        this.acctNo = acctID;
        this.awajimaAcctName = accountName;
        this.accountBalance = accountBalance;
        this.sortCode = bankAccountNo;
        this.acctType = String.valueOf(valueOf);
        this.country = currency;
        this.country = status;
        this.country = bank;

    }

    public Account(String accountName, String bankAcct_No, double accountBalance, int awajimaAcctNo) {
        super();
        this.awajimaAcctName = accountName;
        this.bankAcct_No = bankAcct_No;
        this.accountBalance = accountBalance;
        this.awajimaAcctNo = awajimaAcctNo;
    }
    public Account(String accountBank, String accountNo,String authorization) {
        this.accountBalance = accountBalance;
        this.bankAcct_No = accountNo;
        this.bankName = accountBank;
        this.accountBalance = accountBalance;
    }

    public Account(String skylightMFb, String accountName, int accountNumber, double accountBalance, AccountTypes accountTypeStr) {
        super();
        this.accountBalance = accountBalance;
        this.awajimaAcctName = accountName;
        this.awajimaAcctNo = accountNumber;
        this.type = accountTypeStr;
        this.bankName = skylightMFb;

    }

    public Account(int awajimaAcctNo, String accountBank, String accountName, String accountNo, double accountBalance, AccountTypes accountType) {
        this.awajimaAcctNo = awajimaAcctNo;
        this.accountBalance = accountBalance;
        this.awajimaAcctName = accountName;
        this.bankAcct_No = accountNo;
        this.type = accountType;
        this.bankName = accountBank;
    }

    public Account(int awajimaAcctNo,int profileID, int cusID, String accountName, String currencyCode,long accountNo, double accountBalance, AccountTypes accountType,String status) {
        this.awajimaAcctNo = awajimaAcctNo;
        this.accountBalance = accountBalance;
        this.awajimaAcctName = accountName;
        this.acctNo = accountNo;
        this.type = accountType;
        this.accountCurrencyCode = currencyCode;
        this.accountStatus = status;
        this.acctCusID = cusID;
        this.acctProfID = profileID;
    }


    public Account(int virtualAccountID, String customerNames, double accountBalance, AccountTypes accountTypeStr) {
        this.awajimaAcctNo = virtualAccountID;
        this.accountBalance = accountBalance;
        this.awajimaAcctName = customerNames;
        this.type = accountTypeStr;
        this.accountBalance = accountBalance;
    }

    public Account(String customerBank, String customerNames, int accountNo, AccountTypes accountTypeStr) {
        this.awajimaAcctName = customerNames;
        this.type = accountTypeStr;
        this.awajimaAcctNo = accountNo;
        this.bankName = customerBank;
    }

    public Account(String reportID, int accountNumber) {
        this.awajimaAcctNo = accountNumber;

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


    public void setAwajimaAcctNo(int acctNo) {
        this.awajimaAcctNo = acctNo;
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




    public int getAwajimaAcctNo() {
        return awajimaAcctNo;
    }
    /*public void setId(int id) {
        this.acctID = id;
    }*/


    public String getAccName() {
        return awajimaAcctName;
    }
    public void setAcctName(String name) {
        this.awajimaAcctName = name;
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


    public MarketBizPackage getSkyLightPackage() {
        return marketBizPackage;
    }
    public void setSkyLightPackage(MarketBizPackage marketBizPackage) {
        this.marketBizPackage = marketBizPackage;
    }
    public Loan getLoan() {
        return loan;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public AwajimaPackModel getShopPackage() {
        return shopPackage;
    }
    public void setTransactions(AwajimaPackModel loan) {
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
    public ArrayList<MarketBizPackage> getpackages() {
        return packages;
    }
    public ArrayList<AwajimaPackModel> getShopPackages() {
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
    public void setPackages(ArrayList<MarketBizPackage> packagesFromCurrentProfile) {
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
            if (transactions.get(i).getTranXType() == Transaction.TRANSACTION_TYPE.PAYMENT)  {
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
            if (transactions.get(i).getTranXType() == Transaction.TRANSACTION_TYPE.DEPOSIT)  {
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
        parcel.writeInt(awajimaAcctNo);
        parcel.writeString(bankAcct_No);
        parcel.writeInt(grpAcctNo);
        parcel.writeDouble(accountBalance);
        parcel.writeString(awajimaAcctName);
        parcel.writeString(bankAccountName);
        parcel.writeString(bankAccountBalance);
        parcel.writeString(bankName);
        parcel.writeString(sortCode);
        parcel.writeString(country);
        parcel.writeParcelable(transaction, i);
        parcel.writeParcelable(record, i);
        parcel.writeParcelable(loan, i);
        parcel.writeParcelable(marketBizPackage, i);
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
        return awajimaAcctName;
    }

    public void addBorrowingTransaction(double borrowedAmount) {

    }

    public Object getType() {
        return type;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }
}
