package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.location.Address;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Inventory.Stocks;

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

@Entity(tableName = Customer.CUSTOMER_TABLE)
public class Customer  implements Parcelable, Serializable {
    public static final String CUSTOMER_ID = "cus_id";
    public static final String CUSTOMER_SURNAME = "surname";
    public static final String CUSTOMER_FIRST_NAME = "first_name";
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
    public static final String CUSTOMER_TABLE = "customers";

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


    public static final String CREATE_CUSTOMERS_TABLE = "CREATE TABLE IF NOT EXISTS " + CUSTOMER_TABLE + " ( " + CUSTOMER_ID + " INTEGER  , " + CUS_CUS_LOCID + " INTEGER , " + CUSTOMER_PROF_ID + " INTEGER  , " +
            CUSTOMER_SURNAME + " TEXT, " + CUSTOMER_FIRST_NAME + " TEXT, " + CUSTOMER_PHONE_NUMBER + " TEXT, " + CUSTOMER_EMAIL_ADDRESS + " TEXT, " + CUSTOMER_NIN + " TEXT, " +
            CUSTOMER_DOB + " TEXT, " + CUSTOMER_GENDER + " TEXT, " + CUSTOMER_ADDRESS + " TEXT, " + CUSTOMER_USER_NAME + " TEXT, " + CUSTOMER_PASSWORD + " TEXT, " +
            CUSTOMER_OFFICE + " TEXT, " + CUSTOMER_DATE_JOINED + " TEXT, " + CUSTOMER_STATUS + " TEXT, "+ "FOREIGN KEY(" + CUS_CUS_LOCID + ") REFERENCES " + CUSTOMER_LOCATION_TABLE + "(" + CUSTOMER_LOC_ID + "),"+ "FOREIGN KEY(" + CUSTOMER_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"PRIMARY KEY(" + CUSTOMER_ID  + "))";


    public static final String CREATE_CUSTOMER_LOCATION_TABLE = "CREATE TABLE IF NOT EXISTS " + CUSTOMER_LOCATION_TABLE + " ( " + CUSTOMER_LOC_ID + " INTEGER  , " + CUS_LOC_CUS_ID + " INTEGER  , " +
            CUSTOMER_LONGITUDE + " TEXT, " + CUSTOMER_LATITUDE + " TEXT, " + CUSTOMER_LATLONG + " TEXT, " + CUSTOMER_WARD + " TEXT, " + CUSTOMER_CITY + " TEXT, "+ "FOREIGN KEY(" + CUS_LOC_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+"PRIMARY KEY(" + CUSTOMER_LOC_ID  + "))";

    private static final long serialVersionUID = 8924708152697574031L;
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @PrimaryKey(autoGenerate = true)
    private int cusID=1112;
    String firstName;
    String surname;
    String middleName;
    String gender;
    String office;
    private String cusStatus;
    String state;
    String nin;
    private String address;
    String profilePicture;
    String lga;
    ArrayList<Address> addresses;
    String dob;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private String dateJoined;
    String nextOfKin;
    private String userName;
    CustomerDailyReport savings;
    private Payment payment;
    private Transaction transaction;
    ArrayList<CustomerDailyReport> dailyReports;
    ArrayList<Transaction> transactions;
    ArrayList<TransactionGranting> transactionGrantingArrayList;
    ArrayList<Loan> loans;
    private int payoutNo;

    ArrayList<SkyLightPackage> skyLightPackages;
    ArrayList<SkylightPackageModel> skylightPackageModelArrayList;
    ArrayList<Customer> customersReferred;
    ArrayList<Payee> payees;
    private String role;
    ArrayList<Account> accounts;
    ArrayList<Message> messages;
    ArrayList<TimeLine> timeLines;
    ArrayList<PaymentCode> paymentCodes;
    ArrayList<StandingOrder> standingOrders;
    ArrayList<PaymentDoc> paymentDocs;
    private ArrayList<Stocks> stocks;
    private OfficeBranch officeBranch;
    SkyLightPackage skyLightPackage;
    Account account;
    private  String currency;
    StandingOrderAcct standingOrderAcct;
    StandingOrder standingOrder;
    boolean alreadyAdded;
    LatLng latLong;
    Profile profile;
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

    private int sponsorID;
    private String pin;

    public Customer(String refID, String phoneNumber) {

    }

    public Customer(int customerID, String customerName, String phone, String currentAddress, LatLng currentLocation) {
        this.firstName=customerName;
        this.phoneNumber=phone;
        this.address=currentAddress;
        this.latLong=currentLocation;
        this.cusID=customerID;
    }

    public Customer(int customerID, String cusName, LatLng currentLocation) {
        this.firstName=cusName;
        this.latLong=currentLocation;
        this.cusID=customerID;

    }

    public Customer(int customerID, String surname, String firstName) {
        this.cusID=customerID;
        this.surname=surname;
        this.firstName=firstName;

    }
    public Customer(int count, int customerID, String customerName, String customerPhoneNo, String dateJoined, String customerStatus) {
        this.cusID=customerID;
        this.surname=customerName;
        this.firstName=customerName;
        this.phoneNumber=customerPhoneNo;
        this.dateJoined=dateJoined;
        //this.status=customerStatus;

    }
    public Customer(int customerID, String customerName, String customerPhoneNo, String emailAddress,String address, String office) {
        this.cusID=customerID;
        this.surname=customerName;
        this.emailAddress=emailAddress;
        this.phoneNumber=customerPhoneNo;
        this.address=address;
        this.office=office;

    }



    public void addShoppableItems(String shopItem) {
        String packageID = "P1:" + (skylightPackageModelArrayList.size() + 1);
        SkylightPackageModel skylightPackageModel = new SkylightPackageModel() {
            @Override
            public int getType() {
                return 0;
            }
        };
        skylightPackageModelArrayList.add(skylightPackageModel);
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
        firstName = in.readString();
        surname = in.readString();
        middleName = in.readString();
        gender = in.readString();
        office = in.readString();
        state = in.readString();
        lga = in.readString();

        password = in.readString();
        emailAddress = in.readString();
        phoneNumber = in.readString();
        dateJoined = in.readString();
        nextOfKin = in.readString();
        userName = in.readString();
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
    public Customer(String firstName,String customerEmailAddress,String customerPhoneNumber) {
        this.emailAddress = customerEmailAddress;
        this.firstName = firstName;
        this.nin = nin;
        this.phoneNumber = customerPhoneNumber;
    }

    public Customer(JSONObject jsonObject) {

    }

    public Customer(int uID, String surname, String firstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String dateJoined, String userName, String password) {
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = customerPhoneNumber;
        this.gender = gender;
        this.cusID = uID;
        this.nin = nin;
        this.gender = customerGender;
        this.office = office;
        this.emailAddress = customerEmailAddress;
        this.password = password;
        this.userName = userName;
        this.dob = dob;
        this.address = customerAddress;
        this.dateJoined = dateJoined;

    }
    public Customer(int uID, String surname, String firstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress,  String userName) {
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = customerPhoneNumber;
        this.cusID = uID;
        this.nin = nin;
        this.emailAddress = customerEmailAddress;
        this.userName = userName;
        this.address = customerAddress;

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
        this.address=address;

    }
    public StandingOrderAcct getCusStandingOrderAcct() { return standingOrderAcct; }
    public void setCusStandingOrderAcct(StandingOrderAcct standingOrderAcct) { this.standingOrderAcct = standingOrderAcct; }

    public void setCusPackage(SkyLightPackage skyLightPackage) {
        this.skyLightPackage=skyLightPackage;
    }
    public SkyLightPackage getCusSkyLightPackage() {
        return skyLightPackage;
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
    public void addCusMessages(int keyExtraMessageId, String selectedPurpose, String message, String sender, String time) {
        keyExtraMessageId = messages.size() + 1;
        supportMessage = new Message(keyExtraMessageId,selectedPurpose, message,sender,time);
        messages.add(supportMessage);

    }

    public void addCusNewSkylightPackage(int profileID, int customerID, int packageID, String packageType, double savingsAmount, int packageDuration, String startDate, double grandTotal, String endDate, String status) {
        ArrayList<SkyLightPackage> skyLightPackages = null;
        String packageNo = "Package:" + (skyLightPackages.size() + 1);
        skyLightPackage = new SkyLightPackage(profileID,customerID, packageID, SkyLightPackage.SkylightPackage_Type.valueOf(packageType),savingsAmount,packageDuration,startDate,grandTotal,endDate,status);
        skyLightPackages.add(skyLightPackage);
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
        return gender;
    }
    public void setCusGender(String gender) {
        this.gender = gender;
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

    public Customer(int uid, String surname, String firstName, String phoneNumber, String  emailAddress, String nin,String dob, String gender, String address, String userName, String password, String office, String dateJoined) {

        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.cusID = uid;
        this.gender = gender;
        this.office = office;
        this.emailAddress = emailAddress;
        this.password = password;
        this.userName = userName;
        this.dob = dob;
        this.nin = nin;
        this.address = address;
        this.dateJoined = dateJoined;

    }
    public Customer(int uid, String surname, String firstName, String phoneNumber,String nin,String gender, ArrayList<Address> address, String userName, String password, String office) {
        this.cusID = uid;
        this.dateJoined = DATE_FORMAT.format(new Date());
        this.surname = surname;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.nin = nin;
        this.addresses = address;
        this.userName = userName;
        this.password = password;
        this.office = office;
    }


    public Customer(int uid,String surname, String firstName, String customerPhoneNumber, String customerEmailAddress,String nin, ArrayList<Address> customerAddress, String customerGender, String customerOffice, String customerState,String userName,String password) {
        this.cusID = uid;
        this.dateJoined = DATE_FORMAT.format(new Date());
        this.surname = surname;
        this.firstName = firstName;
        this.phoneNumber = customerPhoneNumber;
        this.gender = customerGender;
        this.addresses = customerAddress;
        this.userName = userName;
        this.nin = nin;
        this.password = password;
        this.office = customerOffice;
    }

    public Customer(int uid, String surname, String firstName, String phoneNumber, String emailAddress, String nin, String gender, String address, String userName, String password, String office, String dateJoined) {
        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.nin = nin;
        this.cusID = uid;
        this.gender = gender;
        this.office = office;
        this.emailAddress = emailAddress;
        this.password = password;
        this.userName = userName;
        this.address = address;
        this.dateJoined = dateJoined;
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
    public void getCusSkyLightPackages(ArrayList<SkyLightPackage> skyLightPackages) {
        this.skyLightPackages = skyLightPackages;
    }

    public ArrayList<SkyLightPackage> getCusSkyLightPackages() {
        return skyLightPackages;
    }

    public void setCusFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCusFirstName() {
        return firstName;
    }
    public void setCusSurname(String surname) {
        this.surname = surname;
    }
    public String getCusSurname() {
        return surname;
    }

    public Account getCusAccount() {
        return account;
    }
    public void setCusAccount(Account account) {
        this.account = account;
    }
    public String getCusProfilePicture() {
        return profilePicture;
    }
    public void setCusProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCusMiddleName() {
        return middleName;
    }
    public void setCusMiddleName(String middleName) {
        this.middleName = middleName;
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
        return emailAddress;
    }
    public void setCusEmail(String email) {
        this.emailAddress = email;
    }
    public String getCusUserName() {
        return userName;
    }
    public void setCusUserName(String userName) {
        this.userName = userName;

    }
    public String getCustomerNin() {
        return nin;
    }
    public void setCustomerNin(String nin) {
        this.nin = nin;

    }
    public void setCusCurrency(String currency) {
        this.currency = currency;

    }
    public String getCusCurrency() {
        return currency;
    }
    public void setCusDob(String dob) {
        this.dob = dob;

    }
    public void setCusRole(String role) {
        this.role = role;
    }

    public void setCusOffice(String office) {
        this.office = office;
    }



    public String getCusDateJoined() {
        return dateJoined;
    }
    public void setCusDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;

    }
    public String getCusDob() {
        return String.valueOf(dob);
    }

    public String getCusLga() {
        return lga;
    }

    public void setCusNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public void setCusLga(String lga) {
        this.lga = lga;
    }
    public String getCusState() {
        return state;
    }
    public String getCusAddress() {
        return address;
    }
    public void setCusState(String state) {
        this.state = state;
    }
    public ArrayList<Address> getCusAddresses() {
        return addresses;
    }

    public void setCusAddresses(ArrayList<Address> address) {
        this.addresses = address;
    }

    public String getCusEmailAddress() {
        return emailAddress;
    }
    public void setCusEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;

    }

    public String getCusNextOfKin() {
        return nextOfKin;
    }


    public String getCusPhoneNumber() {
        return phoneNumber;
    }
    public void setCusPhoneNumber(String phoneNumber) {
        this.phoneNumber = String.valueOf(phoneNumber);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(cusID);
        parcel.writeString(firstName);
        parcel.writeString(surname);
        parcel.writeString(middleName);
        parcel.writeString(gender);
        parcel.writeString(office);
        parcel.writeString(state);
        parcel.writeString(lga);
        parcel.writeString(password);
        parcel.writeString(emailAddress);
        parcel.writeString(phoneNumber);
        parcel.writeString(dateJoined);
        parcel.writeString(nextOfKin);
        parcel.writeString(userName);
    }
    @NonNull
    public String toString() { return (firstName + " (" + cusID + ")"); }
}
