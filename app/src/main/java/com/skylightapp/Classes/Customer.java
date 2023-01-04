package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.location.Address;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.MarketClasses.MarketBusiness;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;

//@Entity(tableName = Customer.CUSTOMER_TABLE)
public class Customer  implements Parcelable, Serializable {
    public static final String CUSTOMER_ID = "cus_id";
    public static final String CUSTOMER_SURNAME = "cus_surname";
    public static final String CUSTOMER_FIRST_NAME = "cus_first_name";
    public static final String CUSTOMER_PHONE_NUMBER = "customer_phone_number";
    public static final String CUSTOMER_EMAIL_ADDRESS = "customer_email_address";
    public static final String CUSTOMER_DOB = "customer_date_of_birth";
    public static final String CUSTOMER_GENDER = "customer_gender";
    public static final String CUSTOMER_ADDRESS = "customer_address";
    public static final String CUSTOMER_OFFICE = "customer_chosen_office";
    public static final String CUSTOMER_DATE_JOINED = "customer_date_joined";
    public static final String CUSTOMER_USER_NAME = "customer_user_name";
    public static final String CUSTOMER_PASSWORD = "customer_password";
    public static final String CUSTOMER_NIN = "customer_NIN";
    public static final String CUSTOMER_STATUS = "customer_Status";
    public static final String CUSTOMER_TABLE = "customers_table";

    public static final String CUSTOMER_LOCATION_TABLE = "customer_LocTable";
    public static final String CUSTOMER_LOC_ID = "cusLoc_id";
    public static final String CUSTOMER_PROF_ID = "cus_Prof_id";
    public static final String CUSTOMER_LONGITUDE = "customer_Longitude";
    public static final String CUSTOMER_LATITUDE = "customer_Latitude";
    public static final String CUSTOMER_LATLONG = "customer_LatLong";
    public static final String CUSTOMER_CITY = "customer_City";
    public static final String CUSTOMER_WARD = "customer_Ward";
    public static final String CUS_LOC_CUS_ID = "cusLoc_CusId";
    public static final String CUS_CUS_LOCID = "cus_Cus_LocId";
    public static final String CUS_BIZ_ID1 = "customer_Biz_ID22";
    public static final String CUSTOMER_MARKET_ID = "customer_Market_ID";
    public static final String CUSTOMER_COUNTRY = "customer_Country";
    public static final String CUS_DB_ID = "customer_DB_ID";
    public static final String CUSTOMER_ROLE = "customer_Role";
    public static final String CUSTOMER_BIZ_STATUS = "customer_BIZ_Status";


    public static final String CREATE_CUSTOMERS_TABLE = "CREATE TABLE IF NOT EXISTS " + CUSTOMER_TABLE + " ( " + CUSTOMER_ID + " INTEGER  , " + CUS_CUS_LOCID + " INTEGER , " + CUSTOMER_PROF_ID + " INTEGER  , " +
            CUSTOMER_SURNAME + " TEXT, " + CUSTOMER_FIRST_NAME + " TEXT, " + CUSTOMER_PHONE_NUMBER + " TEXT, " + CUSTOMER_EMAIL_ADDRESS + " TEXT, " + CUSTOMER_NIN + " TEXT, " +
            CUSTOMER_DOB + " TEXT, " + CUSTOMER_GENDER + " TEXT, " + CUSTOMER_ADDRESS + " TEXT, " + CUSTOMER_USER_NAME + " TEXT, " + CUSTOMER_PASSWORD + " TEXT, " +
            CUSTOMER_OFFICE + " TEXT, " + CUSTOMER_DATE_JOINED + " TEXT, " + CUSTOMER_STATUS + " TEXT, "+ CUSTOMER_COUNTRY + " TEXT, "+ CUS_BIZ_ID1 + " TEXT, "+ CUSTOMER_MARKET_ID + " TEXT, "+ CUS_DB_ID + " TEXT, "+ CUSTOMER_ROLE + " TEXT, "+ CUSTOMER_BIZ_STATUS + " TEXT, "+ "FOREIGN KEY(" + CUS_BIZ_ID1 + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "),"+ "FOREIGN KEY(" + CUSTOMER_MARKET_ID + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + "),"+ "FOREIGN KEY(" + CUS_CUS_LOCID + ") REFERENCES " + CUSTOMER_LOCATION_TABLE + "(" + CUSTOMER_LOC_ID + "),"+ "FOREIGN KEY(" + CUSTOMER_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"PRIMARY KEY(" + CUS_DB_ID  + "))";


    public static final String CREATE_CUSTOMER_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " + CUSTOMER_LOCATION_TABLE + " ( " + CUSTOMER_LOC_ID + " INTEGER  , " + CUS_LOC_CUS_ID + " INTEGER  , " +
            CUSTOMER_LONGITUDE + " TEXT, " + CUSTOMER_LATITUDE + " TEXT, " + CUSTOMER_LATLONG + " TEXT, " + CUSTOMER_WARD + " TEXT, " + CUSTOMER_CITY + " TEXT, "+ "FOREIGN KEY(" + CUS_LOC_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+"PRIMARY KEY(" + CUSTOMER_LOC_ID  + "))";

    private static final long serialVersionUID = 8924708152697574031L;
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @PrimaryKey(autoGenerate = true)
    private int cusID=1112;
    String cusFirstName;
    String cusSurname;
    String cusMiddleName;
    String cusGender;
    String cusOffice;
    private String cusStatus;
    String cusState;
    String cusNin;
    private String cusAddress;
    String cusProfPicture;
    String cusLga;
    ArrayList<Address> addresses;
    String cusDob;
    private String cusPassword;
    private String cusEmailAddress;
    private String cusPhoneNumber;
    private String cusDateJoined;
    String cusNextOfKin;
    private String cusUserName;
    private String cusCountry;
    private int cusMarketID;
    private long cusBizID;
    private ArrayList<Integer> cusMarketIDs;
    private ArrayList<Integer> cusBizIDs;
    CustomerDailyReport savings;
    private Payment payment;
    private Transaction transaction;
    ArrayList<CustomerDailyReport> dailyReports;
    ArrayList<Transaction> transactions;
    ArrayList<TransactionGranting> transactionGrantingArrayList;
    ArrayList<Loan> loans;
    private int payoutNo;
    private String customerRole;
    private String cusBizStatus;
    private ArrayList<String> cusRoleList;

    ArrayList<MarketBizPackage> marketBizPackages;
    ArrayList<AwajimaPackModel> awajimaPackModelArrayList;
    ArrayList<Customer> customersReferred;
    private ArrayList<Payee> payees;
    private String role;
    ArrayList<Account> accounts;
    ArrayList<Message> messages;
    ArrayList<TimeLine> timeLines;
    ArrayList<PaymentCode> paymentCodes;
    ArrayList<StandingOrder> standingOrders;
    ArrayList<PaymentDoc> paymentDocs;
    private ArrayList<Stocks> stocks;
    private OfficeBranch officeBranch;
    MarketBizPackage marketBizPackage;
    Account account;
    private  String currency;
    StandingOrderAcct standingOrderAcct;
    StandingOrder standingOrder;
    boolean alreadyAdded;
    LatLng latLong;
    Profile profile;
    private int sponsorID;
    private String pin;
    private String cusBank;
    private double cusBankBalance;
    private String cusBankAcctNo;
    private Message supportMessage;
    ArrayList<Payment> paymentArrayList;
    public static final List<CustomerItem> ITEMS = new ArrayList<CustomerItem>();

    public static final Map<String, CustomerItem> ITEM_MAP = new HashMap<String, CustomerItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createCustomerItem(i));
        }
    }

    private ArrayList<MarketBusiness> cusMarketBusinesses;

    public Customer(String refID, String cusPhoneNumber) {

    }

    public Customer(int customerID, String customerName, String phone, String currentAddress, LatLng currentLocation) {
        this.cusFirstName =customerName;
        this.cusPhoneNumber =phone;
        this.cusAddress =currentAddress;
        this.latLong=currentLocation;
        this.cusID=customerID;
    }

    public Customer(int customerID, String cusName, LatLng currentLocation) {
        this.cusFirstName =cusName;
        this.latLong=currentLocation;
        this.cusID=customerID;

    }

    public Customer(int customerID, String cusSurname, String cusFirstName) {
        this.cusID=customerID;
        this.cusSurname = cusSurname;
        this.cusFirstName = cusFirstName;

    }
    public Customer(int count, int customerID, String customerName, String customerPhoneNo, String cusDateJoined, String customerStatus) {
        this.cusID=customerID;
        this.cusSurname =customerName;
        this.cusFirstName =customerName;
        this.cusPhoneNumber =customerPhoneNo;
        this.cusDateJoined = cusDateJoined;
        //this.status=customerStatus;

    }
    public Customer(int customerID, String customerName, String customerPhoneNo, String cusEmailAddress, String cusAddress, String cusOffice) {
        this.cusID=customerID;
        this.cusSurname =customerName;
        this.cusEmailAddress = cusEmailAddress;
        this.cusPhoneNumber =customerPhoneNo;
        this.cusAddress = cusAddress;
        this.cusOffice = cusOffice;

    }

    public Customer(int customerID1, long bizID, String uSurname, String uFirstName, String uPhoneNumber, String uEmail, String nIN, String dateOfBirth, String selectedGender, String uAddress, String userName, String cusPassword, String selectedOffice, String joinedDate) {
        this.cusID=customerID1;
        this.cusBizID =bizID;
        this.cusSurname = uSurname;
        this.cusFirstName = uFirstName;
        this.cusPhoneNumber =uPhoneNumber;
        this.cusEmailAddress = uEmail;
        this.cusNin = nIN;
        this.cusDob = dateOfBirth;
        this.cusGender = selectedGender;
        this.cusAddress = uAddress;
        this.cusUserName = userName;
        this.cusPassword = cusPassword;
        this.cusOffice = selectedOffice;
        this.cusDateJoined = joinedDate;

    }


    public void addShoppableItems(String shopItem) {
        String packageID = "P1:" + (awajimaPackModelArrayList.size() + 1);
        AwajimaPackModel awajimaPackModel = new AwajimaPackModel() {
            @Override
            public int getType() {
                return 0;
            }
        };
        awajimaPackModelArrayList.add(awajimaPackModel);
    }
    public void addReferredCustomers(String phoneNumber) {
        String refID = "R" + (customersReferred.size() + 1);
        Customer customer1 = new Customer(refID, phoneNumber);
        customersReferred.add(customer1);
    }
    public void addLoans(int loanId,double amount, String loanStartDate, String status,String loanEndDate,double interest1) {
        int loanNumber = Integer.parseInt(("Loan" + (loans.size() + 1)));
        Loan loan = new Loan(loanNumber, amount,loanStartDate,status,loanEndDate,interest1);
        loans.add(loan);
    }
    public void addPaymentCode(int PROFILE_ID,String CODE_OWNER_PHONE, long CODE_PIN,String CODE_DATE,String CODE_STATUS,String CODE_MANAGER) {
        String codeNumber = "Code:" + (paymentCodes.size() + 1);
        PaymentCode paymentCode = new PaymentCode(PROFILE_ID, CODE_OWNER_PHONE,CODE_PIN,CODE_DATE,CODE_STATUS,CODE_MANAGER);
        paymentCodes.add(paymentCode);
    }
    public void addPaymentDocument(int id, String title, int customerId, int reportId, Uri documentLink,String status) {
        String docNumber = "PD:" + (paymentDocs.size() + 1);
        PaymentDoc paymentDoc = new PaymentDoc(id, title, customerId,  reportId, documentLink, status);
        paymentDocs.add(paymentDoc);
    }

    public void addCusMessages(String phoneNumber, String timestamp) {
        int messageID = messages.size() + 1;
        Message message = new Message(messageID, phoneNumber,timestamp);
        messages.add(message);
    }

    public void addCusAccount(int accountNumber) {
        int reportID = accounts.size() + 1;
        Account account = new Account(reportID, accountNumber);
        accounts.add(account);
    }

    protected Customer(Parcel in) {
        cusID = in.readInt();
        cusFirstName = in.readString();
        cusSurname = in.readString();
        cusMiddleName = in.readString();
        cusGender = in.readString();
        cusOffice = in.readString();
        cusState = in.readString();
        cusLga = in.readString();

        cusPassword = in.readString();
        cusEmailAddress = in.readString();
        cusPhoneNumber = in.readString();
        cusDateJoined = in.readString();
        cusNextOfKin = in.readString();
        cusUserName = in.readString();
        dailyReports = in.createTypedArrayList(CustomerDailyReport.CREATOR);
        transactions = in.createTypedArrayList(Transaction.CREATOR);
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    public Customer() {

    }
    public Customer(String cusFirstName, String customerEmailAddress, String customerPhoneNumber) {
        this.cusEmailAddress = customerEmailAddress;
        this.cusFirstName = cusFirstName;
        this.cusNin = cusNin;
        this.cusPhoneNumber = customerPhoneNumber;
    }

    public Customer(JSONObject jsonObject) {

    }

    public Customer(int uID, String cusSurname, String cusFirstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri cusProfPicture, String cusDateJoined, String cusUserName, String cusPassword) {
        this.cusFirstName = cusFirstName;
        this.cusSurname = cusSurname;
        this.cusPhoneNumber = customerPhoneNumber;
        this.cusGender = cusGender;
        this.cusID = uID;
        this.cusNin = cusNin;
        this.cusGender = customerGender;
        this.cusOffice = cusOffice;
        this.cusEmailAddress = customerEmailAddress;
        this.cusPassword = cusPassword;
        this.cusUserName = cusUserName;
        this.cusDob = cusDob;
        this.cusAddress = customerAddress;
        this.cusDateJoined = cusDateJoined;

    }
    public Customer(int uID, String cusSurname, String cusFirstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String cusUserName) {
        this.cusFirstName = cusFirstName;
        this.cusSurname = cusSurname;
        this.cusPhoneNumber = customerPhoneNumber;
        this.cusID = uID;
        this.cusNin = cusNin;
        this.cusEmailAddress = customerEmailAddress;
        this.cusUserName = cusUserName;
        this.cusAddress = customerAddress;

    }


    private static void addItem(CustomerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static CustomerItem createCustomerItem(int position) {
        return new CustomerItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }


    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public boolean isAlreadyAdded() {
        return false;
    }

    public void setAlreadyAdded(boolean alreadyAdded) {

    }
    public boolean getAlreadyAdded() {
        return alreadyAdded;
    }

    public void setCusAddress(String address) {
        this.cusAddress =address;

    }
    public StandingOrderAcct getCusStandingOrderAcct() { return standingOrderAcct; }
    public void setCusStandingOrderAcct(StandingOrderAcct standingOrderAcct) { this.standingOrderAcct = standingOrderAcct; }

    public void setCusPackage(MarketBizPackage marketBizPackage) {
        this.marketBizPackage = marketBizPackage;
    }
    public MarketBizPackage getCusSkyLightPackage() {
        return marketBizPackage;
    }
    public void setCusStandingOrder(StandingOrder standingOrder) {
        this.standingOrder=standingOrder;
    }
    public StandingOrder getCusStandingOrder() {
        return standingOrder;
    }

    public void addCusStocks(int stocksID, String stocksName, String uStockType, int uStockQuantity, long stockCode, String selectedOfficeBranch, String stockDate, String status) {
        stocks= new ArrayList<>();
        int  stocksNo = stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,stocksName,uStockType,uStockQuantity,stockCode,selectedOfficeBranch,stockDate,status);
        stocks.add(stocks1);

    }

    public void addCusNewSavings(int packageID, int reportID, double savingsAmount, int numberOfDays, double totalAmountSum, int daysRemaining, double amountRemaining, String reportDate, String status1) {
        ArrayList<CustomerDailyReport> dailyReports = null;
        if (dailyReports != null) {
            String savingsNo = "SavingsNo:" + (dailyReports.size() + 1);
        }
        CustomerDailyReport customerDailyReport1 = new CustomerDailyReport(packageID,reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining, reportDate, status1);
        dailyReports.add(customerDailyReport1);

    }
    public void addCusStandingOrder(int uID, int so_Acct_No, double soDailyAmount, String so_start_date, double expectedAmount, double receivedAmount, double amountDiff, String soStatus, String so_end_date) {
        ArrayList<StandingOrder> standingOrders = null;
        String sONo = "SO:" + (standingOrders.size() + 1);
        StandingOrder standingOrder = new StandingOrder(uID,so_Acct_No,soDailyAmount, so_start_date, expectedAmount,receivedAmount,amountDiff,soStatus,so_end_date);
        standingOrders.add(standingOrder);
    }
    public void addCusStandingOrder(int profileID, int customerID, int soAcctNo, double amountCarriedForward, String currentDate, double expectedAmount, double sONowAmount, double amtDiff, double amtDiff1, int totalDays, int daysRemaining, String so_end_date, String inProgress) {
        ArrayList<StandingOrder> standingOrders = null;
        String sONo = "SO:" + (standingOrders.size() + 1);
        StandingOrder standingOrder = new StandingOrder(profileID,customerID,soAcctNo,amountCarriedForward, currentDate, expectedAmount,sONowAmount,amtDiff,amtDiff1,totalDays,daysRemaining,so_end_date,inProgress);
        standingOrders.add(standingOrder);
    }

    public void addReport(String report) {
        ArrayList<CustomerDailyReport> dailyReports = null;
        String reportID = "R" + (dailyReports.size() + 1);
        CustomerDailyReport dailyReport = new CustomerDailyReport();
        dailyReports.add(dailyReport);
    }
    public void addCusStandingOrder1(int uID, String names, double balance) {
        ArrayList<StandingOrder> standingOrders = null;
        String sONo = "SO:" + (standingOrders.size() + 1);
        StandingOrder standingOrder = new StandingOrder(uID,names,balance);
        standingOrders.add(standingOrder);
    }

    public void addCusAccount(int virtualAccountID, String customerBank, String customerNames, String accountNo, double accountBalance, AccountTypes accountTypeStr) {
        ArrayList<Account> accounts = null;
        String accNo = "A" + (accounts.size() + 1);
        Account account = new Account(virtualAccountID,customerBank,customerNames, accountNo, accountBalance,accountTypeStr);
        accounts.add(account);
    }
    public void addCusAccount1(int virtualAccountID, String customerNames, double accountBalance, AccountTypes accountTypeStr) {
        ArrayList<Account> accounts = null;
        String accNo = "A" + (accounts.size() + 1);
        Account account = new Account(virtualAccountID,customerNames, accountBalance,accountTypeStr);
        accounts.add(account);
    }
    public void addCusBankAccount(String customerBank, String customerNames, int accountNo, AccountTypes accountTypeStr) {
        ArrayList<Account> accounts = null;
        String bankAcctNo = "B" + (accounts.size() + 1);
        Account account = new Account(customerBank,customerNames, accountNo,accountTypeStr);
        accounts.add(account);
    }
    public void addCusAccountManager(int uID, String surname, String firstName, String gender, String office) {
        ArrayList<CustomerManager> customerManagers = null;
        String noOfManagers = "M" + (customerManagers.size() + 1);
        CustomerManager customerManager = null;
        customerManager = new CustomerManager(uID,surname, firstName, gender,office);
        customerManagers.add(customerManager);

    }

    public void addCusTransactions(double totalBundleString) {
        ArrayList<Transaction> transactionArrayList = null;
        String transactionNo = "Transaction:" + (transactionArrayList.size() + 1);
        transaction = new Transaction(totalBundleString);
        transactionArrayList.add(transaction);

    }
    public void addCusTransactions(Transaction transaction) {
        transactions= new ArrayList<>();
        transactions.add(transaction);

    }
    public void addCusStandingOrder(StandingOrder standingOrder) {
        standingOrders= new ArrayList<>();
        standingOrders.add(standingOrder);

    }
    public void addCusMessages(int keyExtraMessageId, String selectedPurpose, String message, String sender, String time) {
        keyExtraMessageId = messages.size() + 1;
        supportMessage = new Message(keyExtraMessageId,selectedPurpose, message,sender,time);
        messages.add(supportMessage);

    }

    public void addCusNewSkylightPackage(int profileID, int customerID, int packageID, String packageType, double savingsAmount, int packageDuration, String startDate, double grandTotal, String endDate, String status) {
        ArrayList<MarketBizPackage> marketBizPackages = null;
        String packageNo = "Package:" + (marketBizPackages.size() + 1);
        marketBizPackage = new MarketBizPackage(profileID,customerID, packageID, MarketBizPackage.SkylightPackage_Type.valueOf(packageType),savingsAmount,packageDuration,startDate,grandTotal,endDate,status);
        marketBizPackages.add(marketBizPackage);
    }

    public LatLng getCusLocation() {
        return latLong;
    }
    public void setCustomerLocation(LatLng latLong) {
        this.latLong = latLong;
    }


    public Profile getCusProfile() {
        return profile;
    }
    public void setCusProfile(Profile profile) {
        this.profile = profile;
    }

    public void addCusPayment(String type, double totalToWithdraw, Date date, long paymentCode, String acctType, String office, String status) {
        ArrayList<Payment> paymentArrayList = null;
        if (paymentArrayList != null) {
            String paymentNo = "Payment:" + (paymentArrayList.size() + 1);
        }
        payment = new Payment(type,totalToWithdraw, date,paymentCode,acctType,office,status);
        paymentArrayList.add(payment);

    }

    public String getCusOfficeBranch() {
        return String.valueOf(officeBranch);
    }

    public void setCusOfficeBranch(OfficeBranch officeBranch) {
        this.officeBranch = officeBranch;
    }

    public void setCusSponsorID(int sponsorID) {
        this.sponsorID = sponsorID;
    }

    public long getCusSponsorID() {
        return sponsorID;
    }
    public void setCusUID(int uID) {
        if (uID > 0) this.cusID = uID;
    }
    public String getCusRole() {
        return role;
    }
    public String getCusGender() {
        return cusGender;
    }
    public void setCusGender(String gender) {
        this.cusGender = gender;
    }

    public void setCusLoans(ArrayList<Loan> loans) {
        this.loans = loans;

    }
    public ArrayList<Loan> getCusLoans() { return loans; }

    public String getCusPassword() {
        return pin;
    }
    public int getCusUID() {
        return cusID;
    }

    public void setCusPin(String pin) {
        this.pin = pin;
    }

    public String getCusStatus() {
        return cusStatus;
    }

    public void setCusStatus(String cusStatus) {
        this.cusStatus = cusStatus;
    }

    public String getCusBank() {
        return cusBank;
    }

    public void setCusBank(String cusBank) {
        this.cusBank = cusBank;
    }

    public double getCusBankBalance() {
        return cusBankBalance;
    }

    public void setCusBankBalance(double cusBankBalance) {
        this.cusBankBalance = cusBankBalance;
    }

    public String getCusBankAcctNo() {
        return cusBankAcctNo;
    }

    public void setCusBankAcctNo(String cusBankAcctNo) {
        this.cusBankAcctNo = cusBankAcctNo;
    }

    public String getCusCountry() {
        return cusCountry;
    }

    public void setCusCountry(String cusCountry) {
        this.cusCountry = cusCountry;
    }

    public ArrayList<Payee> getPayees() {
        return payees;
    }

    public void setPayees(ArrayList<Payee> payees) {
        this.payees = payees;
    }

    public int getCusMarketID() {
        return cusMarketID;
    }

    public void setCusMarketID(int cusMarketID) {
        this.cusMarketID = cusMarketID;
    }

    public ArrayList<Integer> getCusMarketIDs() {
        return cusMarketIDs;
    }

    public void setCusMarketIDs(ArrayList<Integer> cusMarketIDs) {
        this.cusMarketIDs = cusMarketIDs;
    }

    public ArrayList<Integer> getCusBizIDs() {
        return cusBizIDs;
    }

    public void setCusBizIDs(ArrayList<Integer> cusBizIDs) {
        this.cusBizIDs = cusBizIDs;
    }

    public long getCusBizID() {
        return cusBizID;
    }

    public void setCusBizID(long cusBizID) {
        this.cusBizID = cusBizID;
    }

    public ArrayList<MarketBusiness> getCusMarketBusinesses() {
        return cusMarketBusinesses;
    }

    public void setCusMarketBusinesses(ArrayList<MarketBusiness> cusMarketBusinesses) {
        this.cusMarketBusinesses = cusMarketBusinesses;
    }

    public String getCusBizStatus() {
        return cusBizStatus;
    }

    public void setCusBizStatus(String cusBizStatus) {
        this.cusBizStatus = cusBizStatus;
    }

    public ArrayList<String> getCusRoleList() {
        return cusRoleList;
    }

    public void setCusRoleList(ArrayList<String> cusRoleList) {
        this.cusRoleList = cusRoleList;
    }

    public String getCustomerRole() {
        return customerRole;
    }

    public void setCustomerRole(String customerRole) {
        this.customerRole = customerRole;
    }




    public static class CustomerItem {
        public final String id;
        public final String content;
        public final String details;

        public CustomerItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @NonNull
        @Override
        public String toString() {
            return content;
        }
    }

    public Customer(int uid, String cusSurname, String cusFirstName, String cusPhoneNumber, String cusEmailAddress, String cusNin, String cusDob, String cusGender, String cusAddress, String cusUserName, String cusPassword, String cusOffice, String cusDateJoined) {

        this.cusFirstName = cusFirstName;
        this.cusSurname = cusSurname;
        this.cusPhoneNumber = cusPhoneNumber;
        this.cusGender = cusGender;
        this.cusID = uid;
        this.cusGender = cusGender;
        this.cusOffice = cusOffice;
        this.cusEmailAddress = cusEmailAddress;
        this.cusPassword = cusPassword;
        this.cusUserName = cusUserName;
        this.cusDob = cusDob;
        this.cusNin = cusNin;
        this.cusAddress = cusAddress;
        this.cusDateJoined = cusDateJoined;

    }
    public Customer(int uid, String cusSurname, String cusFirstName, String cusPhoneNumber, String cusNin, String cusGender, ArrayList<Address> cusAddress, String cusUserName, String cusPassword, String cusOffice) {
        this.cusID = uid;
        this.cusDateJoined = DATE_FORMAT.format(new Date());
        this.cusSurname = cusSurname;
        this.cusFirstName = cusFirstName;
        this.cusPhoneNumber = cusPhoneNumber;
        this.cusGender = cusGender;
        this.cusNin = cusNin;
        this.addresses = cusAddress;
        this.cusUserName = cusUserName;
        this.cusPassword = cusPassword;
        this.cusOffice = cusOffice;
    }


    public Customer(int uid, String cusSurname, String cusFirstName, String customerPhoneNumber, String customerEmailAddress, String cusNin, ArrayList<Address> customerAddress, String customerGender, String customerOffice, String customerState, String cusUserName, String cusPassword) {
        this.cusID = uid;
        this.cusDateJoined = DATE_FORMAT.format(new Date());
        this.cusSurname = cusSurname;
        this.cusFirstName = cusFirstName;
        this.cusPhoneNumber = customerPhoneNumber;
        this.cusGender = customerGender;
        this.addresses = customerAddress;
        this.cusUserName = cusUserName;
        this.cusNin = cusNin;
        this.cusPassword = cusPassword;
        this.cusOffice = customerOffice;
    }

    public Customer(int uid, String cusSurname, String cusFirstName, String cusPhoneNumber, String cusEmailAddress, String cusNin, String cusGender, String cusAddress, String cusUserName, String cusPassword, String cusOffice, String cusDateJoined) {
        this.cusFirstName = cusFirstName;
        this.cusSurname = cusSurname;
        this.cusPhoneNumber = cusPhoneNumber;
        this.cusGender = cusGender;
        this.cusNin = cusNin;
        this.cusID = uid;
        this.cusGender = cusGender;
        this.cusOffice = cusOffice;
        this.cusEmailAddress = cusEmailAddress;
        this.cusPassword = cusPassword;
        this.cusUserName = cusUserName;
        this.cusAddress = cusAddress;
        this.cusDateJoined = cusDateJoined;
    }


    public void addCusTimeLine(String tittle, String timelineDetails) {
        String history = "History" + (timeLines.size() + 1);
        TimeLine timeLine = new TimeLine(tittle,timelineDetails);
        timeLines.add(timeLine);
    }
    public void addCusStandingOrder(int soAcctNo, double expectedAmount, double sONowAmount, double amountCarriedForward, String currentDate, int months, int daysRemaining, String endDate) {
        soAcctNo = standingOrders.size() + 1;
        StandingOrder timeLine = new StandingOrder(soAcctNo,expectedAmount,sONowAmount,amountCarriedForward,currentDate,months,daysRemaining,endDate);
        standingOrders.add(timeLine);
    }
    public void addCusTranxGranding(int tranxPayoutID, int cusID,String cusName, double amountRequested, String acctBank, String bankName, String acctBankNo, String s, String requestDate) {
        payoutNo = transactionGrantingArrayList.size() + 1;
        TransactionGranting transactionGranting = new TransactionGranting(tranxPayoutID,cusID,cusName,amountRequested,acctBank,bankName,acctBankNo,s,requestDate);
        transactionGrantingArrayList.add(transactionGranting);
    }

    public void setCusReportFromDB(ArrayList<CustomerDailyReport> dailyReports) {
        this.dailyReports = dailyReports;
    }
    public void setCustomersFromDB(ArrayList<Customer> customerFromCurrentProfile) {
        this.customersReferred = customerFromCurrentProfile;

    }
    public void setCusMessages(ArrayList<Message> messageArrayList) {
        this.messages = messageArrayList;
    }
    public ArrayList<Message> getCusMessages() {
        return messages;
    }
    public void setCusStandingOrders(ArrayList<StandingOrder> standingOrderArrayList) {
        this.standingOrders = standingOrderArrayList;
    }
    public ArrayList<StandingOrder> getCusStandingOrders() {
        return standingOrders;
    }
    public void setCusDailyReports(ArrayList<CustomerDailyReport> customerDailyReportArrayList) {
        this.dailyReports = customerDailyReportArrayList;
    }


    public ArrayList<PaymentDoc> getCusPaymentDocuments() {
        return paymentDocs;
    }
    public void setPaymentDocuments(ArrayList<PaymentDoc> paymentDocs) {
        this.paymentDocs = paymentDocs;
    }
    public ArrayList<CustomerDailyReport> getCusDailyReports() {
        return dailyReports;
    }
    public void setTransactions(ArrayList<Transaction> transactionArrayList) {
        this.transactions = transactionArrayList;
    }
    public ArrayList<Transaction> getCusTransactions() {
        return transactions;
    }
    public void getCusSkyLightPackages(ArrayList<MarketBizPackage> marketBizPackages) {
        this.marketBizPackages = marketBizPackages;
    }

    public ArrayList<MarketBizPackage> getCusSkyLightPackages() {
        return marketBizPackages;
    }

    public void setCusFirstName(String firstName) {
        this.cusFirstName = firstName;
    }

    public String getCusFirstName() {
        return cusFirstName;
    }
    public void setCusSurname(String surname) {
        this.cusSurname = surname;
    }
    public String getCusSurname() {
        return cusSurname;
    }

    public Account getCusAccount() {
        return account;
    }
    public void setCusAccount(Account account) {
        this.account = account;
    }
    public String getCusProfilePicture() {
        return cusProfPicture;
    }
    public void setCusProfilePicture(String profilePicture) {
        this.cusProfPicture = profilePicture;
    }

    public String getCusMiddleName() {
        return cusMiddleName;
    }
    public void setCusMiddleName(String middleName) {
        this.cusMiddleName = middleName;
    }

    /*public long getId() {
        return Math.toIntExact(uID);
    }
    public void setId(long uID) {
        if (uID > 0) {
            this.uID = (int) uID;
        }

    }*/
    /*public int getCusUID() {
        String uid = String.valueOf(this.uID);
        if (uid == null ||uid.isEmpty()) {
            uid = UUID.randomUUID().toString();
            setUID(uid);
        }
        return uID;
    }*/

    /*public void setUID(int uID) {
        this.uID = uID;
    }*/
    public String getCusEmail() {
        return cusEmailAddress;
    }
    public void setCusEmail(String email) {
        this.cusEmailAddress = email;
    }
    public String getCusUserName() {
        return cusUserName;
    }
    public void setCusUserName(String userName) {
        this.cusUserName = userName;

    }
    public String getCustomerNin() {
        return cusNin;
    }
    public void setCustomerNin(String nin) {
        this.cusNin = nin;

    }
    public void setCusCurrency(String currency) {
        this.currency = currency;

    }
    public String getCusCurrency() {
        return currency;
    }
    public void setCusDob(String dob) {
        this.cusDob = dob;

    }
    public void setCusRole(String role) {
        this.role = role;
    }

    public void setCusOffice(String office) {
        this.cusOffice = office;
    }



    public String getCusDateJoined() {
        return cusDateJoined;
    }
    public void setCusDateJoined(String dateJoined) {
        this.cusDateJoined = dateJoined;

    }
    public String getCusDob() {
        return String.valueOf(cusDob);
    }

    public String getCusLga() {
        return cusLga;
    }

    public void setCusNextOfKin(String nextOfKin) {
        this.cusNextOfKin = nextOfKin;
    }

    public void setCusLga(String lga) {
        this.cusLga = lga;
    }
    public String getCusState() {
        return cusState;
    }
    public String getCusAddress() {
        return cusAddress;
    }
    public void setCusState(String state) {
        this.cusState = state;
    }
    public ArrayList<Address> getCusAddresses() {
        return addresses;
    }

    public void setCusAddresses(ArrayList<Address> address) {
        this.addresses = address;
    }

    public String getCusEmailAddress() {
        return cusEmailAddress;
    }
    public void setCusEmailAddress(String emailAddress) {
        this.cusEmailAddress = emailAddress;

    }

    public String getCusNextOfKin() {
        return cusNextOfKin;
    }


    public String getCusPhoneNumber() {
        return cusPhoneNumber;
    }
    public void setCusPhoneNumber(String phoneNumber) {
        this.cusPhoneNumber = String.valueOf(phoneNumber);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(cusID);
        parcel.writeString(cusFirstName);
        parcel.writeString(cusSurname);
        parcel.writeString(cusMiddleName);
        parcel.writeString(cusGender);
        parcel.writeString(cusOffice);
        parcel.writeString(cusState);
        parcel.writeString(cusLga);
        parcel.writeString(cusPassword);
        parcel.writeString(cusEmailAddress);
        parcel.writeString(cusPhoneNumber);
        parcel.writeString(cusDateJoined);
        parcel.writeString(cusNextOfKin);
        parcel.writeString(cusUserName);
    }
    @NonNull
    public String toString() { return (cusFirstName + " (" + cusID + ")"); }
}
