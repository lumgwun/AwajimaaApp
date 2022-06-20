package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.SmsMessage;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.RealEstate.Properties;
import com.skylightapp.SuperAdmin.Skylight;
import com.skylightapp.Tellers.TellerCash;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = User.USER_TABLE)
public class User implements Parcelable, Serializable {
    @Ignore
    private static final long serialVersionUID = 8924708152697574031L;
    @Ignore

    public static final String USER_TABLE = "user_table";
    @Ignore
    //public static final String USER_COLUMN_ID = "_id1";
    public static final String USER_SURNAME = "u_surname";
    @Ignore
    public static final String USER_FIRSTNAME = "u_first_name";
    @Ignore
    public static final String USER_EMAIL = "u_email";
    @Ignore
    public static final String USER_DOB = "u_dob";
    @Ignore
    public static final String USER_ADDRESS = "u_street";
    @Ignore
    public static final String USER_GENDER = "u_gender";
    @Ignore
    public static final String USER_PHONE = "u_phone";
    @Ignore
    public static final String USER_ROLE = "u_role";
    @Ignore
    public static final String USER_DATE_JOINED = "u_join_date";
    @Ignore
    public static final String USER_NEXT_OF_KIN = "u_next_of_kin";
    @Ignore
    public static final String USER_STATUS = "u_status";
    @Ignore
    public static final String USER_PASSWORD = "u_passCode";

    //public static final String PROFILES_TABLE = "profiles_table";
    @Ignore
    public static final String USER_UNIT = "u_unit";
    @Ignore
    public static final String USER_WARD = "u_ward";
    @Ignore
    public static final String USER_TOWN = "u_town";
    @Ignore
    public static final String USER_COUNTRY = "u_country";
    @Ignore
    public static final String USER_SPONSOR_ID = "u_sponsor";
    @Ignore

    public static final String USER_ID = "user_id";
    @Ignore
    //public static final String PICTURE_TABLE = "picture";
    //public static final String PICTURE_URI = "picture_uri";
    public static final String USERNAME = "username";
    @Ignore
    //public static final String PASSWORD = "password";
    //public static final String PASSWORD_TABLE = "password_table";
    public static final String USER_NIN = "u_NIN";
    @Ignore
    public static final String USER_STATE = "u_state";
    @Ignore
    public static final String USER_OFFICE = "u_office";
    @Ignore



    public static final String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + " (" + USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_SURNAME + " TEXT, " + USER_FIRSTNAME + " TEXT, " +  USER_PHONE + " TEXT, " + USER_EMAIL + " TEXT, " +USER_DOB + " TEXT, " + USER_GENDER + " TEXT, " +
            USER_ADDRESS + " TEXT, " + USER_NIN + " TEXT, " + USER_UNIT + " TEXT, " +USER_WARD + " TEXT, " +USER_TOWN + " TEXT, " +USER_STATE + " TEXT, " + USER_COUNTRY + " TEXT, " +  USER_OFFICE + " TEXT, " + USER_DATE_JOINED + " TEXT, " + USER_ROLE + " TEXT, " + USERNAME + " TEXT, " + USER_PASSWORD + " TEXT, " + USER_STATUS + " TEXT, " + USER_NEXT_OF_KIN + " TEXT,"+ USER_SPONSOR_ID + " TEXT)";

    protected User(Parcel in) {
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    /*@ColumnInfo(name = USERNAME)
    private String userName;
    @ColumnInfo(name = USER_EMAIL)
    private String email;
    @ColumnInfo(name = USER_ADDRESS)
    protected String address;
    @ColumnInfo(name = USER_DATE_JOINED)
    private String dateJoined;
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = USER_ID)
    private int userID=10182;
    @ColumnInfo(name = USER_ROLE)
    private String role;
    @ColumnInfo(name = USERNAME)
    private String uUserName;
    @ColumnInfo(name = USER_EMAIL)
    private String emailAddress;
    @ColumnInfo(name = USER_PHONE)
    private String phoneNumber;
    @ColumnInfo(name = USER_DATE_JOINED)
    private Date dateUserJoined;
    @ColumnInfo(name = USER_PASSWORD)
    private String pin;
    @Ignore
    private String authenticationKey;
    @ColumnInfo(name = USER_FIRSTNAME)
    String firstName;
    @ColumnInfo(name = USER_SURNAME)
    String surname;
    @Ignore
    String middleName;
    @ColumnInfo(name = USER_ROLE)
    String designation;
    @ColumnInfo(name = USER_GENDER)
    String gender;
    @ColumnInfo(name = USER_OFFICE)
    String office;
    @ColumnInfo(name = USER_STATE)
    String state;
    @Ignore
    String lga;
    @ColumnInfo(name = USER_DOB)
    String dob;
    @ColumnInfo(name = USER_PASSWORD)
    private String password;
    @Ignore
    String others;
    @Ignore
    //private ArrayList<CustomerDailyReport> reports;
    private String transaction;
    @Ignore
    String profilePicture;
    //DBHelper dbHelper;
    @Ignore
    Context context;
    @Ignore
    protected transient boolean authenticated = false;
    @Ignore
    protected RolesEnumMap enumMap = new RolesEnumMap(context);
    //protected ArrayList<AdminUser> adminUser;
    @Ignore
    protected UserSuperAdmin superAdmin;
    //protected ArrayList<SkyLightPackage> skyLightPackage;
    //protected ArrayList<Customer> customers;
    //ArrayList<Account> accounts;
    @Ignore
    Account account;
    @Ignore
    Customer customer;
    @Ignore
    protected UserVendor vendor;
    //protected ArrayList<CustomerManager> customerManagers;
    //protected ArrayList<Address> address;
    @Ignore
    private boolean remind;
    @Ignore
    protected User_Type type;
    @ColumnInfo(name = USER_NEXT_OF_KIN)
    String nextOfKin;
    @Ignore
    private String createdDate;
    @ColumnInfo(name = USER_COUNTRY)
    private String country;





    @Ignore
    private long uID,roleId;

    @Ignore

    private String machine;
    @Ignore
    private Payment payment;
    @Ignore
    private String  businessName,unit,ward,town;
    @Ignore
    String nin;
    @Ignore
    AdminUser adminUser2;
    @Ignore
    private ArrayList<Account> accounts;
    @Ignore
    private ArrayList<Stocks> stocks;
    @Ignore
    private ArrayList<Address> addresses;
    @Ignore
    private ArrayList<Payee> payees;
    @Ignore
    private ArrayList<TimeLine> timeLines;
    @Ignore
    private ArrayList<Customer> customers;
    @Ignore
    private ArrayList<Business> businesses;
    @Ignore
    private ArrayList<Properties> properties;
    @Ignore

    private ArrayList<CustomerDailyReport> dailyReports;
    @Ignore
    private ArrayList<SkyLightPackage> skyLightPackages;
    @Ignore
    private ArrayList<Loan> loans;
    @Ignore
    private ArrayList<Payment> paymentArrayList;
    @Ignore
    private ArrayList<PaymentCode> paymentCodeArrayList;
    @Ignore
    private ArrayList<PaymentDoc> paymentDocArrayList;
    @Ignore

    private ArrayList<CustomerManager> customerManagers;
    @Ignore
    private ArrayList<AdminUser> adminUsers;
    @Ignore
    private ArrayList<AdminBankDeposit> adminBankDeposits;
    @Ignore
    private ArrayList<StockTransfer> stockTransferArrayList;
    @Ignore


    private Loan loan;
    @Ignore

    private Birthday birthday;
    @Ignore
    private Business business;
    @Ignore
    private GroupAccount groupAccount;
    @Ignore
    private Skylight skylight;
    @Ignore
    private SavingsGroup savingsGroup;
    @Ignore
    private ArrayList<SavingsGroup> savingsGroups;
    @Ignore
    private ArrayList<GroupAccount> groupAccounts;
    @Ignore
    StandingOrderAcct standingOrderAcct;
    @Ignore

    private ArrayList<TellerCash> tellerCashes;
    @Ignore
    private ArrayList<StandingOrder> standingOrders;
    @Ignore
    private StandingOrder standingOrder;
    @Ignore
    private CustomerDailyReport customerDailyReport;
    @Ignore
    private SkyLightPackage skyLightPackage;
    @Ignore
    private Payee payee;
    @Ignore
    private AdminUser adminUser;
    @Ignore
    private CustomerManager customerManager;
    @Ignore
    private ArrayList<SmsMessage.MessageClass> messageClasses;
    @Ignore
    SmsMessage.MessageClass messageClass;
    @Ignore
    //long customerID=customer.getuID();
    private ArrayList<Transaction> transactions;
    @Ignore
    private ArrayList<TimeLine> timeLineArrayList;
    @Ignore
    private ArrayList<LatLng> latLngs;
    @Ignore
    private  LatLng lastLocation;
    @Ignore
    private long dbId;
    @Ignore
    private String status;
    @Ignore

    private ArrayList<GroupSavings> groupSavings;
    String accountBalance;
    @Ignore

    int profileNo;
    @Ignore
    ArrayList<TellerReport> tellerReportArrayList;
    @Ignore

    public User(JSONObject jsonObject) {

    }
    @Ignore

    public User(int profileID2, String ezekiel, String s, String s1, String s2, String s3, String male, String ilabuchi, String s4, String rivers, String elelenwo, String s5, String superAdmin, String lumgwun, String s6, String confirmed, String s7) {

    }

    public User() {

    }
    @Ignore

    public void setUserPayments(ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
    }
    @Ignore
    public ArrayList<Payment> getUserPayments() { return paymentArrayList; }
    public void setUserPaymentCodes(ArrayList<PaymentCode> paymentCodeArrayList) {
        this.paymentCodeArrayList = paymentCodeArrayList;
    }
    @Ignore
    public ArrayList<PaymentCode> getUserPaymentCodes() { return paymentCodeArrayList; }
    public void setUserPaymentDocuments(ArrayList<PaymentDoc> paymentDocArrayList) {
        this.paymentDocArrayList = paymentDocArrayList;
    }
    @Ignore
    public ArrayList<PaymentDoc> getUserPaymentDocuments() { return paymentDocArrayList;
    }
    @Ignore

    public User(int profileNo, int userID, String status) {
        this.profileNo = profileNo;
        this.userID = userID;
        this.status = status;

    }
    @Ignore

    public StandingOrderAcct getUStandingOrderAcct() { return standingOrderAcct;
    }
    @Ignore
    public void setUStandingOrderAcct(StandingOrderAcct standingOrderAcct) { this.standingOrderAcct = standingOrderAcct; }
    @Ignore
    @SuppressLint("SimpleDateFormat")
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public Skylight getUSkylight() { return skylight;
    }
    @Ignore
    public void setUSkylight(Skylight skylight) { this.skylight = skylight;
    }
    @Ignore
    public void setUDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;

    }
    public void setDateJoined(String dateJoined) {
        this.dateJoined = dateJoined;

    }
    @Ignore

    public Birthday getUBirthday() { return birthday;
    }
    @Ignore
    public void setUBirthday(Birthday birthday) { this.birthday = birthday;
    }
    @Ignore

    public String getUDob() {
        return String.valueOf(dob);
    }
    public String getDob() {
        return String.valueOf(dob);
    }
    @Ignore
    public void setUDob(String dob) {
        this.dob = Utils.stringToDate(String.valueOf(dob));;

    }
    @Ignore
    public String getUnit() {
        return unit;
    }
    @Ignore
    public void setUnit(String unit) { this.unit = unit;
    }
    @Ignore
    public String getUWard() {
        return ward;
    }
    @Ignore
    public void setUWard(String ward) { this.ward = ward;
    }
    @Ignore
    public String getUTown() {
        return town;
    }
    @Ignore
    public void setUTown(String town) { this.town = town;
    }
    @Ignore

    public String getUTransaction() {
        return transaction;
    }
    @Ignore
    public void setUTransaction(String transaction) {
        this.transaction = transaction;
    }
    @Ignore

    public void setUProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    @Ignore

    public String getUGender() {
        return gender;
    }
    @Ignore
    public void setUGender(String gender) {
        this.gender = gender;
    }
    @Ignore
    public String getUAuthenticationKey() {
        return authenticationKey;
    }
    @Ignore

    public void setUAuthenticationKey(String authenticationKey) {
        this.authenticationKey = authenticationKey;
    }
    @Ignore
    public void setUFirstName(String firstName) {
        this.firstName = firstName;
    }
    @Ignore

    public String getUFirstName() {
        return firstName;
    }
    public void setUSurname(String surname) {
        this.surname = surname;
    }
    public String getUSurname() {
        return surname;
    }

    public String getUMiddleName() {
        return middleName;
    }
    public void setUMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getUDesignation() {
        return designation;
    }
    public void setUDesignation(String designation) {
        this.designation = designation;
    }

    public String getUOffice() {
        return office;
    }
    public void setUOffice(String office) {
        this.office = office;
    }


    public int getUUserID() {
        return userID;
    }
    public void setUUserID(int userID) {
        if (userID > 0) this.userID = userID;
    }
    public String getURole() {
        return role;
    }
    public void setURole(String role) {
        this.role = role;
    }*/


    /*public void setUUserName(String userName) throws Exception  {
        this.userName = userName;
        /*if (userName != null && userName.length() > 0) {
            if (!this.userName.equals("")) {
                dbHelper.updateProfileUserName(userName, this.uID);
            }

        }
    }

    public String getUUserName() {
        return userName;
    }
    public String getUDateUserJoined() {
        return String.valueOf(dateUserJoined);
    }
    public void setUDateUserJoined(Date dateUserJoined) {
        this.dateUserJoined = dateUserJoined;

    }
    public String getUPin() {
        return pin;
    }

    public String getULga() {
        return lga;
    }
    public void setUCountry(String country) {
        this.country = country;
    }
    public  String getUCountry() {
        return country;
    }
    public void setUNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }

    public void setULga(String lga) {
        this.lga = lga;
    }
    public String getUState() {
        return state;
    }
    public void setUState(String state) {
        this.state = state;
    }


    public String getUEmailAddress() {
        return emailAddress;
    }
    public void setUEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;

    }

    public String getUNextOfKin() {
        return nextOfKin;
    }


    public String getUPhoneNumber() {
        return phoneNumber;
    }
    public void setUPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

    }

    public boolean getUAuthenticated() {
        return false;
    }

    public User_Type getUType() {
        return type;
    }


    public String getUBusinessName() {
        if(business !=null){
            businessName=business.getProfileBusinessName();
        }
        return businessName;
    }
    public void setUBusinessName(String businessName) { this.email = businessName; }

    private static final String JSON_INVESTOR = "investor";
    private static final String JSON_LEARNER1 = "learner1";
    private static final String JSON_LEARNER2 = "learner2";
    private static final String JSON_ADMIN_USER = "admin";
    private static final String JSON_SUPER_ADMIN = "superAdmin";
    private static final String JSON_UID = "uid";
    private static final String JSON_REMIND = "remind";

    //private ArrayList<Account> accounts = new ArrayList<Account>();
    long id;
    @Ignore
    protected User(Parcel in) {
        userID = in.readInt();
        role = in.readString();
        uUserName = in.readString();
        emailAddress = in.readString();
        phoneNumber = in.readString();
        pin = in.readString();
        authenticationKey = in.readString();
        firstName = in.readString();
        surname = in.readString();
        middleName = in.readString();
        designation = in.readString();
        gender = in.readString();
        office = in.readString();
        state = in.readString();
        lga = in.readString();
        password = in.readString();
        others = in.readString();

        transaction = in.readString();
        profilePicture = in.readString();
        //adminUser = in.readParcelable(AdminUser.class.getClassLoader());
        remind = in.readByte() != 0;
        nextOfKin = in.readString();
        createdDate = in.readString();
        country = in.readString();
        //accounts = in.createTypedArrayList(Account.CREATOR);
        id = in.readLong();
    }
    @Ignore
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    @Ignore

    public User(Long profileID2, String ezekiel, String s, String s1, String s2, String s3, String male, String ilabuchi, String s4, String rivers, String elelenwo, String s5, String superAdmin, String lumgwun, String s6, String confirmed, String s7) {


    }
    @Ignore

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    @Ignore
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(userID);
        parcel.writeString(role);
        parcel.writeString(uUserName);
        parcel.writeString(emailAddress);
        parcel.writeString(phoneNumber);
        parcel.writeString(pin);
        parcel.writeString(authenticationKey);
        parcel.writeString(firstName);
        parcel.writeString(surname);
        parcel.writeString(middleName);
        parcel.writeString(designation);
        parcel.writeString(gender);
        parcel.writeString(office);
        parcel.writeString(state);
        parcel.writeString(lga);
        parcel.writeString(password);
        parcel.writeString(others);

        parcel.writeString(transaction);
        parcel.writeString(profilePicture);

        parcel.writeByte((byte) (remind ? 1 : 0));
        parcel.writeString(nextOfKin);
        parcel.writeString(createdDate);
        parcel.writeString(country);
        //parcel.writeTypedList(accounts);
        parcel.writeLong(id);
    }

    /*public List<SkyLightPackage> getPackages() {
        return (List<SkyLightPackage>) skyLightPackage;
    }
    public Customer getCustomer() {
        return customer;
    }

    public ArrayList<CustomerManager> getCustomerManager(Profile userProfile) {
        return customerManagers;
    }

    public void setCustomDailyReport1(ArrayList<CustomerDailyReport> string) {
        this.reports = string;

    }*/




    @SuppressWarnings("ConstantConditions")

    public enum User_Type implements CharSequence {
        ADMIN, SUPER_ADMIN, INVESTOR,  MERCHANT, CUSTOMER,  OFFLINE_USER, ONLINE_USER, FRAUDULENT_USER;
        protected int userTypeCode;

        public int getUserTypeCode() {
            return userTypeCode;
        }

        @Override
        public int length() {
            return 0;
        }

        @Override
        public char charAt(int i) {
            return 0;
        }

        @NonNull
        @Override
        public CharSequence subSequence(int i, int i1) {
            return null;
        }
    }
    /*@Ignore

    public User(long id, ArrayList<AdminUser> adminUser,UserSuperAdmin superAdmin, boolean remind, UserVendor vendor, ArrayList<CustomerManager> customerManagers, ArrayList<Customer> customer) {
        //this.adminUser = adminUser;
        //this.customers = (ArrayList<Customer>) customers;
        this.remind = remind;
        this.id = id;
        this.vendor = vendor;
        //this.customerManagers = (ArrayList<CustomerManager>) customerManagers;
        this.superAdmin = superAdmin;
        if (!authenticated) {
            this.authenticated = true;
        }
        this.state = state;
        this.lga = lga;
        this.country = country;
        this.createdDate = createdDate;
        this.id = Integer.parseInt(UUID.randomUUID().toString());
    }
    @Ignore

    public User(String surname1, String firstName1, String middleName1, String phoneNumber1, String email1, String dob,String gender1, String office1, String designation1, String password1) {
        this.firstName = firstName1;
        this.surname = surname1;
        this.phoneNumber = phoneNumber1;
        this.middleName = middleName1;
        this.id = id;
        this.designation = designation1;
        this.gender = gender1;
        this.office = office1;
        this.emailAddress = email1;
        this.pin = password1;
        this.dob = dob;

    }

    public User(String surname1, String firstName1, String middleName1, String phoneNumber1, String email1, String gender1, String office1, String designation1, String password1) {
        this.firstName = firstName1;
        this.surname = surname1;
        this.phoneNumber = phoneNumber1;
        this.middleName = middleName1;
        this.id = id;
        this.designation = designation1;
        this.gender = gender1;
        this.office = office1;
        this.emailAddress = email1;
        this.pin = password1;
        this.state = state;
        this.lga = lga;
        this.country = country;
        this.createdDate = createdDate;
    }
    /*public ArrayList<AdminUser> getAdminUsers() {
        return (ArrayList<AdminUser>)adminUser;
    }
    public ArrayList<Customer> getCustomers() {
        return (ArrayList<Customer>)customers;
    }

    public ArrayList<CustomerDailyReport> getDailyReport() {
        return reports;
    }
    public List<Address> getAddress() {
        return address;
    }

    @Ignore
    public boolean authenticate(String password, String phoneNumber) {

        return false;
    }




    /*public void setName(String name) throws Exception  {
        if (name != null && name.length() > 0) {
            if (!this.uUserName.equals("")) {
                dbHelper.updateProfileUserName(name, this.userID);
            }
            this.uUserName = name;
        }
    }
    public void setUId(long uID) {
        if (userID > 0) {
            this.userID = (int) uID;
        }

    }
    public long getURoleId() {
        return roleId;
    }
    public void setURoleId(long roleId) {
        if (roleId > 0) {
            this.roleId = roleId;
        }
    }

    public void setUPin(String pin) {
        this.pin = pin;

    }
    /*public void setCustomerManagers(ArrayList<CustomerManager> customerManagers) {
        this.customerManagers = customerManagers;

    }
    public void setAddresses(ArrayList<Address> addresses) {
        this.address = addresses;

    }

    public ArrayList<SkyLightPackage> getSkyLightPackages() {
        return skyLightPackage;
    }
    public void setSkyLightPackage(ArrayList<SkyLightPackage> skyLightPackages) {
        this.skyLightPackage = skyLightPackages;
    }*/
    /*public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public List<Loan> getLoans() {
        return loans;
    }
    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }
    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }*/
    /*public void addAccount(Account account) throws ConnectionFailedException {
        if (account != null && !this.accounts.contains(account)) {
            this.accounts.add(account);
            // add the user account in the database if it is not already there
            if (!Objects.requireNonNull(DBHelper.getAccountIds(this.getuID())).contains(account.getId())) {
                DBHelper.insertUserAccount(this.getuID(), account.getId());
            }
        }
    }
    public final boolean authenticate(String password) throws ConnectionFailedException {
        this.authenticated = PasswordHelpers.comparePassword(Objects.requireNonNull(DBHelper.getPassword(this.id)),
                password);
        // Return whether the password given matches the password of the User
        return this.authenticated;
    }

    public void setCustomDailyReport(ArrayList<CustomerDailyReport> report) {
        this.reports = report;
    }*/

}
