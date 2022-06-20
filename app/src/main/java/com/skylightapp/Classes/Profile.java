package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.telephony.SmsMessage;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.RealEstate.Properties;
import com.skylightapp.SuperAdmin.Skylight;
import com.skylightapp.Tellers.TellerCash;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;

@Entity(tableName = "RoomProfileTable")
public class Profile extends User implements Parcelable, Serializable, BaseColumns {
    @Ignore
    public static final String PROFILE_SURNAME = "P_surname";
    @Ignore
    public static final String PROFILE_FIRSTNAME = "P_first_name";
    @Ignore
    public static final String PROFILE_EMAIL = "P_email";
    @Ignore
    public static final String PROFILE_DOB = "P_dob";
    @Ignore
    public static final String PROFILE_ADDRESS = "P_street";
    @Ignore
    public static final String PROFILE_GENDER = "P_gender";
    @Ignore
    public static final String PROFILE_PHONE = "p_phone";
    @Ignore
    public static final String PROFILE_ROLE = "p_role";
    @Ignore
    public static final String PROFILE_DATE_JOINED = "p_join_date";
    @Ignore
    public static final String PROFILE_NEXT_OF_KIN = "p_next_of_kin";
    @Ignore
    public static final String PROFILE_STATUS = "p_status";
    @Ignore
    public static final String PROFILE_PASSWORD = "p_passCode";
    @Ignore
    public static final String PROFILES_TABLE = "profiles_table";
    @Ignore
    public static final String PROFILE_UNIT = "p_unit";
    @Ignore
    public static final String PROFILE_WARD = "p_ward";
    @Ignore
    public static final String PROFILE_TOWN = "p_town";
    @Ignore
    public static final String PROFILE_COUNTRY = "p_country";
    @Ignore
    public static final String PROFILE_SPONSOR_ID = "p_sponsor";
    @Ignore

    public static final String PROFILE_ID = "profile_id";
    @Ignore
    public static final String PICTURE_TABLE = "pictureTable";
    @Ignore
    public static final String PICTURE_URI = "picture_uri";
    @Ignore
    public static final String PROFILE_USERNAME = "p_username";
    @Ignore
    public static final String PASSWORD = "p_password";
    @Ignore
    public static final String PASSWORD_TABLE = "password_table";
    @Ignore
    public static final String PROFILE_NIN = "profile_NIN";
    @Ignore
    public static final String PROFILE_STATE = "p_state";
    @Ignore
    public static final String PROFILE_OFFICE = "p_office";
    @Ignore
    public static final String PROFILE_PIC_ID = "picture_id";


    @Ignore
    private Profile profile;
    @Ignore
    private Payment payment;
    @Ignore
    private String identity, businessName,unit,ward,town;
    @Ignore

    private OfficeBranch officeBranch;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PROFILE_ID)
    private int pID =10152;

    @ColumnInfo(name = PROFILE_SURNAME)
    private String profileLastName;

    @ColumnInfo(name = PROFILE_FIRSTNAME)
    private String profileFirstName;


    @ColumnInfo(name = PROFILE_PHONE)
    private String profilePhoneNumber;

    @ColumnInfo(name = PROFILE_EMAIL)
    private String profileEmail;

    @ColumnInfo(name = PROFILE_DOB)
    private String profileDob;

    @ColumnInfo(name = PROFILE_GENDER)
    private String profileGender;

    @ColumnInfo(name = PROFILE_ADDRESS)
    protected String profileAddress;

    //@ColumnInfo(name = PROFILE_NIN)
    @Ignore
    private String nin;


    @ColumnInfo(name = PROFILE_OFFICE)
    private String profileOffice;

    @ColumnInfo(name = PROFILE_DATE_JOINED)
    private String profileDateJoined;


    @ColumnInfo(name = PROFILE_STATE)
    private String profileState;


    @ColumnInfo(name = PROFILE_ROLE)
    private String profileRole;



    @ColumnInfo(name = PROFILE_NEXT_OF_KIN)
    private String nextOfKin;


    @ColumnInfo(name = PROFILE_USERNAME)
    private String profileUserName;




    @ColumnInfo(name = PASSWORD)
    private String profilePassword;


    @ColumnInfo(name = PROFILE_SPONSOR_ID)
    private int profileSponsorID;


    @ColumnInfo(name = CUSTOMER_ID)
    private int profCusID;

    @ColumnInfo(name = PROFILE_STATUS)
    private String profileStatus;



    public void setProfilePassword(String profilePassword)   {
        this.profilePassword = profilePassword;
    }

    public String getProfilePassword() { return profilePassword; }
    public String getProfileFirstName() { return profileFirstName; }


    /*@ColumnInfo(name = PROFILE_DOB)
    private String dob1;*/






    @Ignore

    private String pin;

    @Ignore
    protected User.User_Type type;


    @Ignore
    private String machine;



    @TypeConverters
    @ColumnInfo(name = PICTURE_URI)
    private Uri profilePicture;








    public void setProfileUserName(String profileUserName)   {
        this.profileUserName = profileUserName;
    }
    public String getProfileUserName() {
        return profileUserName;
    }




    public int getProfCusID() {
        return profCusID;
    }
    public void setProfCusID(int profCusID) {
        this.profCusID = profCusID;
    }
    public int getPID() {
        return pID;
    }
    public void setPID(int pID) {
        this.pID = pID;
    }
    public String getProfileRole() {
        return profileRole;
    }
    public String getProfileDateJoined() {
        return profileDateJoined;
    }
    public void setProfileDateJoined(String profileDateJoined) {
        this.profileDateJoined = profileDateJoined;

    }

    public String getProfileStatus() { return profileStatus; }
    public void setProfileStatus(String profileStatus) { this.profileStatus = profileStatus; }

    public void setProfileDob(String profileDob) { this.profileDob = profileDob; }
    public void setProfileGender(String profileGender) { this.profileGender = profileGender; }
    public String getProfileOffice() {
        return profileOffice;
    }
    @Ignore
    public void setProfileMachine(String machine) { this.machine = machine;
    }
    @Ignore
    public String getProfileMachine() {
        return machine;
    }

    public String getProfilePhoneNumber() { return profilePhoneNumber; }
    public void setProfileOffice(String profileOffice) { this.profileOffice = profileOffice; }
    public void setProfilePhoneNumber(String profilePhoneNumber) { this.profilePhoneNumber = profilePhoneNumber; }

    public void setProfileAddress(String profileAddress) { this.profileAddress = profileAddress; }
    public void setProfilePicture(Uri profilePicture)
    { this.profilePicture = profilePicture; }

    public void setProfileRole(String profileRole) { this.profileRole = profileRole;
    }

    public int getProfileSponsorID() {
        return profileSponsorID;
    }

    public void setProfileSponsorID(int sponsorID) {
        this.profileSponsorID = sponsorID;
    }

    @Ignore
    public ArrayList<Address> getProfileAddresses() { return addresses; }
    @Ignore
    AdminUser adminUser2;
    @Ignore
    public static final String CREATE_PIXTURE_TABLE = "CREATE TABLE " + PICTURE_TABLE + " (" + PROFILE_PIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFILE_ID + " TEXT, " + CUSTOMER_ID + " INTEGER , " +
            PICTURE_URI + " TEXT ,"+"FOREIGN KEY(" + PROFILE_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";
    @Ignore
    public static final String CREATE_PASSWORD_TABLE = "CREATE TABLE IF NOT EXISTS " + PASSWORD_TABLE + " (" + PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PASSWORD + " TEXT , " + CUSTOMER_ID + " INTEGER , " +
            "FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";
    @Ignore
    public static final String CREATE_PROFILES_TABLE = "CREATE TABLE " + PROFILES_TABLE + " (" + PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFILE_SURNAME + " TEXT, " + PROFILE_FIRSTNAME + " TEXT, " + PROFILE_PHONE + " TEXT, " + PROFILE_EMAIL + " TEXT, " + PROFILE_DOB + " TEXT, " + PROFILE_GENDER + " TEXT, " +
            PROFILE_ADDRESS + " TEXT, " + PROFILE_NIN + " TEXT, " + PROFILE_UNIT + " TEXT, " + PROFILE_WARD + " TEXT, " + PROFILE_TOWN + " TEXT, " + PROFILE_STATE + " TEXT, " + PROFILE_COUNTRY + " TEXT, " + PROFILE_OFFICE + " TEXT, " + PROFILE_DATE_JOINED + " TEXT, " + PROFILE_ROLE + " TEXT, " + PROFILE_USERNAME + " TEXT, " + PROFILE_PASSWORD + " TEXT, " + PROFILE_STATUS + " TEXT, " + PROFILE_NEXT_OF_KIN + " TEXT,"+ PROFILE_SPONSOR_ID + " TEXT,"+ CUSTOMER_ID + " TEXT," + "FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";


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
    private ArrayList<TellerCash> tellerCashes;
    @Ignore
    private ArrayList<StandingOrder> standingOrders;
    @Ignore
    private ArrayList<SavingsGroup> savingsGroups;
    @Ignore
    private ArrayList<GroupAccount> groupAccounts;
    @Ignore
    private ArrayList<Transaction> transactions;
    @Ignore
    private ArrayList<TimeLine> timeLineArrayList;
    @Ignore
    private ArrayList<LatLng> latLngs;
    @Ignore
    ArrayList<TellerReport> tellerReportArrayList;
    @Ignore
    ArrayList<TransactionGranting> transactionGrantingArrayList;
    @Ignore
    private ArrayList<SmsMessage.MessageClass> messageClasses;
    @Ignore
    private ArrayList<GroupSavings> groupSavings;

    @Ignore
    private Transaction transaction;
    @Ignore
    private Loan loan;
    @Ignore
    private Birthday birthday;
    @Ignore
    private Business business;
    @Ignore
    private GroupAccount groupAccount;
    @Ignore
    private String authenticationKey;
    @Ignore
    Context context;
    @Ignore
    protected transient boolean authenticated = false;
    @Ignore
    protected RolesEnumMap enumMap = new RolesEnumMap(context);
    @Ignore
    private Skylight skylight;
    @Ignore
    private SavingsGroup savingsGroup;
    @Ignore
    StandingOrderAcct standingOrderAcct;
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
    private UserSuperAdmin superAdmin;
    @Ignore
    SmsMessage.MessageClass messageClass;
    @Ignore
    private Customer customer;
    @Ignore
    Account account;
    @Ignore
    private  LatLng lastLocation;
    @Ignore
    DBHelper dbHelper;

    @Ignore
    String accountBalance;
    @Ignore
    private int payoutNo;
    @Ignore
    int profileNo;




    public Profile(int profile_id, String surName, String profileFirstName, String phone, String profileEmail, String profileDob, String profileGender, String profileAddress, String s, String profileState, String officeBranch, String joinedDate, String profileRole, String userName, String profilePassword, String profileStatus) {
        super();
        this.pID = profile_id;
        this.profileLastName = surName;
        this.profilePhoneNumber = phone;
        this.profileEmail = profileEmail;
        this.identity = identity;
        this.profileUserName = userName;
    }

    public Profile(int profileID1, String uSurname, String uFirstName, String uPhoneNumber, String uEmail, String dateOfBirth, String selectedGender, String uAddress, String nIN, String selectedState, String selectedOffice, String dateOfJoin, String userProfileType, String username, String profilePassword, String profileStatus, String nextOfKin) {
        super();
        this.pID = profileID1;
        this.profileLastName = uSurname;
        this.profileFirstName = uFirstName;
        this.profilePhoneNumber = uPhoneNumber;
        this.profileEmail = uEmail;
        this.profileDob = dateOfBirth;
        this.profileGender = selectedGender;
        this.profileAddress = uAddress;
        this.identity = nIN;
        this.profileState = selectedState;
        this.profileOffice = selectedOffice;
        this.profileDateJoined = dateOfJoin;
        this.profileRole = userProfileType;
        this.profileUserName = username;
        this.profilePassword = profilePassword;
        this.profileStatus = profileStatus;
        this.nextOfKin = nextOfKin;

    }

    public Profile(int pID, String surname, String profileFirstName, String profileDateJoined, String profileStatus, Uri pix) {
        super();
        this.pID = pID;
        this.profileLastName = surname;
        this.profileFirstName = profileFirstName;
        this.profilePicture = pix;
        this.profileDateJoined = profileDateJoined;
        this.profileStatus = profileStatus;

    }

    public Profile(String surname, String profileFirstName) {
        super();
        this.profileLastName = surname;
        this.profileFirstName = profileFirstName;

    }

    public Profile(int profileID1, String selectedOffice, String branch) {
        super();
        this.profileLastName = selectedOffice;
        this.pID = profileID1;
        this.profileRole = branch;
    }

    @Ignore
    protected Profile(Parcel in) {
        super();
        pID = in.readInt();
        profCusID = in.readInt();
        profileFirstName = in.readString();
        profileLastName = in.readString();
        profileState = in.readString();
        profileUserName = in.readString();
        profilePassword = in.readString();
        profileEmail = in.readString();
        profileDob = in.readString();
        profileSponsorID = in.readInt();
        profileGender = in.readString();
        profileAddress = in.readString();
        profileOffice = in.readString();
        profilePhoneNumber = in.readString();
        profileDateJoined = in.readString();
        machine = in.readString();
        payment = in.readParcelable(Payment.class.getClassLoader());
        identity = in.readString();
        businessName = in.readString();
        unit = in.readString();
        ward = in.readString();
        town = in.readString();
        profilePicture = in.readParcelable(Uri.class.getClassLoader());
        officeBranch = in.readParcelable(OfficeBranch.class.getClassLoader());
        nin = in.readString();
        profileRole = in.readString();
        adminUser2 = in.readParcelable(AdminUser.class.getClassLoader());
        accounts = in.createTypedArrayList(Account.CREATOR);
        stocks = in.createTypedArrayList(Stocks.CREATOR);
        addresses = in.createTypedArrayList(Address.CREATOR);
        payees = in.createTypedArrayList(Payee.CREATOR);
        //timeLines = in.createTypedArrayList(TimeLine.CREATOR);
        customers = in.createTypedArrayList(Customer.CREATOR);
        //businesses = in.createTypedArrayList(Business.CREATOR);
        properties = in.createTypedArrayList(Properties.CREATOR);
        dailyReports = in.createTypedArrayList(CustomerDailyReport.CREATOR);
        skyLightPackages = in.createTypedArrayList(SkyLightPackage.CREATOR);
        //loans = in.createTypedArrayList(Loan.CREATOR);
        paymentArrayList = in.createTypedArrayList(Payment.CREATOR);
        //paymentCodeArrayList = in.createTypedArrayList(PaymentCode.CREATOR);
        //paymentDocumentArrayList = in.createTypedArrayList(PaymentDocument.CREATOR);
        //customerManagers = in.createTypedArrayList(CustomerManager.CREATOR);
        //adminUsers = in.createTypedArrayList(AdminUser.CREATOR);
        adminBankDeposits = in.createTypedArrayList(AdminBankDeposit.CREATOR);
        stockTransferArrayList = in.createTypedArrayList(StockTransfer.CREATOR);
        transaction = in.readParcelable(Transaction.class.getClassLoader());
        loan = in.readParcelable(Loan.class.getClassLoader());
        birthday = in.readParcelable(Birthday.class.getClassLoader());
        business = in.readParcelable(Business.class.getClassLoader());
        groupAccount = in.readParcelable(GroupAccount.class.getClassLoader());
        savingsGroup = in.readParcelable(SavingsGroup.class.getClassLoader());
        savingsGroups = in.createTypedArrayList(SavingsGroup.CREATOR);
        groupAccounts = in.createTypedArrayList(GroupAccount.CREATOR);
        standingOrderAcct = in.readParcelable(StandingOrderAcct.class.getClassLoader());
        tellerCashes = in.createTypedArrayList(TellerCash.CREATOR);
        //standingOrders = in.createTypedArrayList(StandingOrder.CREATOR);
        standingOrder = in.readParcelable(StandingOrder.class.getClassLoader());
        customerDailyReport = in.readParcelable(CustomerDailyReport.class.getClassLoader());
        skyLightPackage = in.readParcelable(SkyLightPackage.class.getClassLoader());
        payee = in.readParcelable(Payee.class.getClassLoader());
        adminUser = in.readParcelable(AdminUser.class.getClassLoader());
        customerManager = in.readParcelable(CustomerManager.class.getClassLoader());
        superAdmin = in.readParcelable(UserSuperAdmin.class.getClassLoader());
        customer = in.readParcelable(Customer.class.getClassLoader());
        account = in.readParcelable(Account.class.getClassLoader());
        transactions = in.createTypedArrayList(Transaction.CREATOR);
        //timeLineArrayList = in.createTypedArrayList(TimeLine.CREATOR);
        latLngs = in.createTypedArrayList(LatLng.CREATOR);
        lastLocation = in.readParcelable(LatLng.class.getClassLoader());
        pID = in.readInt();
        profileStatus = in.readString();
        groupSavings = in.createTypedArrayList(GroupSavings.CREATOR);
        accountBalance = in.readString();
        profileNo = in.readInt();
        tellerReportArrayList = in.createTypedArrayList(TellerReport.CREATOR);
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public Profile() {

        super();
    }

    public Profile(int pID, String name, String phoneNo, String profileEmail, String identity, String userType) {
        this.pID = pID;
        this.profileLastName = name;
        this.profilePhoneNumber = phoneNo;
        this.profileEmail = profileEmail;
        this.nin = identity;


    }

    public Profile(Profile userProfile) {
        this.profile = userProfile;
    }

    /*public Uri getProfileImage() {

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "SELECT * FROM PICTURE_TABLE";
            Cursor cursor = db.rawQuery(sql, new String[] {});

            if(cursor.moveToFirst()){
                profileID = Long.parseLong(cursor.getString(0));
                customerId = cursor.getLong(1);

            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();
            if(cursor.getCount() == 0){
                return null;
            } else {
                return this;
            }

        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }


        return null;
    }

    public int getSponsorID() {
        return sponsorID;
    }
    public String getGender() {
        return gender;
    }
    public String getOffice() {
        return office;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getProfileFirstName() {
        return profileFirstName;
    }
    public String getRole() {
        return role;
    }
    public int getCustomerId() {
        return customerId;
    }
    public String getLastName() {
        return profileLastName;
    }
    public String getState() {
        return profileState;
    }
    public String getPassword() {
        return profilePassword;
    }
    public String getStatus (){
        return status;
    }public String getDob() {
        return dob;
    }
    public String getDateJoined() {
        return dateJoined;
    }
    public int getProfileID() {
        return profileID;
    }*/


    @Ignore
    public void setProfilePayments(ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
    }
    @Ignore
    public ArrayList<Payment> getProfilePayments() { return paymentArrayList;
    }
    @Ignore
    public void setProfilePaymentCodes(ArrayList<PaymentCode> paymentCodeArrayList) {
        this.paymentCodeArrayList = paymentCodeArrayList;
    }
    @Ignore
    public ArrayList<PaymentCode> getProfilePaymentCodes() { return paymentCodeArrayList;
    }
    @Ignore
    public void setUserPaymentDocuments(ArrayList<PaymentDoc> paymentDocArrayList) {
        this.paymentDocArrayList = paymentDocArrayList;
    }
    @Ignore
    public ArrayList<PaymentDoc> getProfilePaymentDocuments() { return paymentDocArrayList; }

    public Profile(int profileNo, int pID, String profileStatus) {
        super();
        this.profileNo = profileNo;
        this.pID = pID;
        this.profileStatus = profileStatus;

    }
    /*public void setProfileUserName(String userName) throws Exception  {
        if (userName != null && userName.length() > 0) {
            if (!this.userName.equals("")) {
                dbHelper.updateProfileUserName(userName, this.uID);
            }
            this.userName = userName;
        }
    }*/




    public String getProfileLastName() {
        return profileLastName;
    }

    public void setProfileFirstName(String profileFirstName) {
        this.profileFirstName = profileFirstName;
    }

    public void setProfileLastName(String profileLastName) {
        this.profileLastName = profileLastName;
    }
    @Ignore
    public String getProfileIdentity() {
        return identity;
    }
    @Ignore
    public void setProfileIdentity(String identity) {
        this.identity = identity;

    }
    public String getProfileEmail() { return profileEmail; }
    public String getProfileAddress() { return profileAddress; }

    public void setProfileEmail(String profileEmail) { this.profileEmail = profileEmail; }
    public String getProfileState() { return profileState; }
    public void setProfileState(String profileState) { this.profileState = profileState; }

    public String getNextOfKin() {
        return nextOfKin;
    }

    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
    }
    @Ignore
    public String getProfileUnit() {
        return unit;
    }
    @Ignore
    public void setProfileUnit(String unit) { this.unit = unit;
    }
    @Ignore
    public String getProfileWard() {
        return ward;
    }
    @Ignore
    public void setProfileWard(String ward) { this.ward = ward;
    }
    @Ignore
    public String getProfileTown() {
        return town;
    }
    @Ignore
    public void setProfileTown(String town) { this.town = town; }








    @Ignore
    public StandingOrderAcct getProfileSOAcct() { return standingOrderAcct; }
    @Ignore
    public void setProfileSOAcct(StandingOrderAcct standingOrderAcct) { this.standingOrderAcct = standingOrderAcct; }
    @SuppressLint("SimpleDateFormat")
    @Ignore
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Ignore
    public Skylight getProfileSkylight() { return skylight;
    }
    @Ignore
    public void setProfileSkylight(Skylight skylight) { this.skylight = skylight;
    }


    @Ignore
    public String getAuthenticationKey() {
        return authenticationKey;
    }
    @Ignore
    public void setAuthenticationKey(String authenticationKey) {
        this.authenticationKey = authenticationKey;
    }

    @Ignore


    public LatLng getProfileLastKnownLocation() {
        return lastLocation;
    }
    @Ignore
    public LatLng setProfileLastKnownLocation(LatLng lastLocation) {
        this.lastLocation = lastLocation;

        return lastLocation;
    }

    @Ignore
    public boolean getAuthenticated() {
        return false;
    }
    public Birthday getProfileBirthday() { return birthday; }
    public void setProfileBirthday(Birthday birthday) { this.birthday = birthday; }
    @Ignore
    public GroupAccount getGroupAccount(long grpAcctID) { return groupAccount; }
    public void setGroupAccount(GroupAccount groupAccount) { this.groupAccount = groupAccount; }
    @Ignore
    public SavingsGroup getSavingsGroup() { return savingsGroup; }
    @Ignore
    public void setSavingsGroup(SavingsGroup savingsGroup) { this.savingsGroup = savingsGroup; }

    @Ignore
    public boolean profileAuthenticate(String password, String phoneNumber) {

        return false;
    }



    @Ignore
    public String getProfileBusinessName() {
        if(business !=null){
            businessName=business.getProfileBusinessName();
        }
        return businessName;
    }
    @Ignore
    public void setProfileBusinessName(String businessName) { this.profileEmail = businessName; }


    public Profile (int userID, String profileFirstName, String profileLastName, String phone, String profileEmail, String username, String profilePassword, String machine, Uri profilePicture) {
        super();
        this.profileFirstName = profileFirstName;
        this.profileLastName = profileLastName;
        this.pID = userID;
        this.profileUserName = username;
        this.profilePhoneNumber = phone;
        this.profileEmail = profileEmail;
        this.profilePassword = profilePassword;
        this.machine = machine;
        this.profilePicture = profilePicture;


    }

    public Profile (String profileFirstName, String profileLastName, String profileState, String username, String profilePassword, int dbId) {
        super();
        this.profileFirstName = profileFirstName;
        this.profileLastName = profileLastName;
        this.pID = dbId;
        this.profileUserName = username;
        this.profileEmail = profileEmail;
    }
    public Profile (int pID, String profileFirstName, String profileLastName, String profilePhoneNumber, String profileEmail, String profileGender, String profileState, String username, String identity, String company) {
        super();
        this.pID = pID;
        this.profileFirstName = profileFirstName;
        this.profileLastName = profileLastName;
        this.profileUserName = username;
        this.profileEmail = profileEmail;
        this.businessName = company;
        this.profileGender = profileGender;
        this.identity = identity;
        this.profilePhoneNumber = profilePhoneNumber;
        this.profileState = profileState;
    }

    public Profile(String userSurname, String firstName1, String profilePhoneNumber, String profileEmail, String dob1, String selectedGender, String address1, String selectedState, String selectedOffice, String userName, String profilePassword, String s, String s1, String profilePicture) {
        super();
        this.profileFirstName = firstName1;
        this.profileLastName = userSurname;
        this.profilePhoneNumber = profilePhoneNumber;
        this.profileEmail = profileEmail;
        this.profileDob = dob1;
        this.profileGender = selectedGender;
        this.profileAddress = address1;
        this.profileState = selectedState;
        this.profileOffice = selectedOffice;
        this.profileRole = s;
        this.profileUserName = userName;
        this.profilePassword = profilePassword;
        this.profileStatus = s1;
        this.profilePicture = Uri.parse(profilePicture);


    }

    public Profile(String toString, String toString1, String toString2, String toString3, String toString4, String toString5, String toString6,String toString7) {
        super();
        this.profileFirstName = toString;
        this.profileLastName = toString1;
        this.profilePhoneNumber = toString2;
        this.profileEmail = toString3;
        this.profileAddress = toString4;
        this.profileUserName = toString5;
        this.profilePassword = toString6;
        this.profileDateJoined = toString7;

    }

    public Profile(String uid) {

        super();
    }
    public Profile(String profileFirstName, String profileLastName, String profilePhoneNumber, String profileEmail, String profileDob, String profileGender, String profileAddress, String username, String profileOffice, String joinedDate, int id) {
        super();
        this.profileFirstName = profileFirstName;
        this.profileLastName = profileLastName;
        this.profilePhoneNumber = profilePhoneNumber;
        this.profileEmail = profileEmail;
        this.profileAddress = profileDob;
        this.profileGender = profileGender;
        this.profileAddress = profileAddress;
        this.profileUserName = username;
        this.profileDateJoined = joinedDate;
        this.profileOffice = profileOffice;
        this.pID = id;
    }

    public Profile(int customerID1, String surName, String profileFirstName, String phoneNumber1, String emailAddress, String profileAddress, String genderType, String spinnerOffice, String profileState, Uri parse, String profileDob, String userName, String profilePassword, String profileStatus, String profileRole) {
        super();
        this.profileFirstName = profileFirstName;
        this.profileLastName = surName;
        this.profilePhoneNumber = phoneNumber1;
        this.profileEmail = emailAddress;
        this.profileDob = profileDob;
        this.profileGender = genderType;
        this.profileAddress = profileAddress;
        this.profileState = profileState;
        this.profileOffice = spinnerOffice;
        this.profileUserName = userName;
        this.profilePassword = profilePassword;
        this.profileRole = profileRole;
        this.profileStatus = profileStatus;
        this.profilePicture = Uri.parse(String.valueOf(parse));
    }




    @Ignore
    public StandingOrder getProfileStandingOrder() { return standingOrder; }
    @Ignore
    public void setProfileStandingOrder(StandingOrder standingOrder) {
        this.standingOrder = standingOrder;

    }
    @Ignore
    public Business getProfileBusiness() { return business; }
    @Ignore
    public void setProfileBusiness(Business business) {
        this.business = business;

    }
    @Ignore

    public ArrayList<SavingsGroup> getSavingsGroups() { return savingsGroups; }
    @Ignore
    public void setSavingsGroups(ArrayList<SavingsGroup> savingsGroups) {
        this.savingsGroups = savingsGroups;

    }
    @Ignore
    public ArrayList<GroupSavings> getGroupSavings() { return groupSavings;
    }
    @Ignore
    public void setGroupSavings(ArrayList<GroupSavings> groupSavings) {
        this.savingsGroups = savingsGroups;

    }
    @Ignore
    public ArrayList<GroupAccount> getGroupAccounts() { return groupAccounts; }
    @Ignore
    public void setGroupAccounts(ArrayList<GroupAccount> groupAccounts) {
        this.groupAccounts = groupAccounts;

    }
    @Ignore
    public ArrayList<StandingOrder> getStandingOrders() { return standingOrders; }
    @Ignore
    public void setStandingOrders(ArrayList<StandingOrder> standingOrders) {
        this.standingOrders = standingOrders;

    }
    @Ignore


    public ArrayList<Account> getProfileAccounts() { return accounts;
    }
    @Ignore
    public ArrayList<Customer> getProfileCustomers() { return customers;
    }
    @Ignore
    public ArrayList<Business> getBusinesses() { return businesses; }
    @Ignore
    public void setBusinesses(ArrayList<Business> businesses) { this.businesses = businesses; }
    @Ignore

    public ArrayList<SkyLightPackage> getProfileSkylightPackages() { return skyLightPackages; }
    @Ignore
    public ArrayList<CustomerDailyReport> getProfileDailyReports() { return dailyReports; }

    @Ignore
    public void setProfileLoans(ArrayList<Loan> loans) {
        this.loans = loans;

    }
    @Ignore
    public ArrayList<Loan> getProfileLoans() { return loans; }
    @Ignore
    public Loan getProfileLoan() { return loan; }
    @Ignore
    public void setProfileLoan(Loan loan) { this.loan = loan; }

   @Ignore
    public OfficeBranch getProfileOfficeBranch() {
        return officeBranch;
    }
    @Ignore
    public void setProfileOfficeBranch(OfficeBranch officeBranch) {
        this.officeBranch = officeBranch;
    }






    public Payee getProfilePayee() { return payee; }
    @Ignore
    public void setProfilePayee(Payee payee) { this.payee = payee; }
    @Ignore

    public SmsMessage.MessageClass getMessageClass() { return messageClass; }
    @Ignore
    public void setMessageClass(SmsMessage.MessageClass messageClass) { this.messageClass = messageClass; }
    @Ignore
    public SkyLightPackage getProfileSkyLightPackage() { return skyLightPackage; }
    @Ignore
    public void setProfileSkyLightPackage(SkyLightPackage skyLightPackage) { this.skyLightPackage = skyLightPackage; }
    @Ignore
    public Account getProfileAccount() { return account;
    }
    @Ignore
    public void setProfileAccount(Account account) { this.account = account;
    }
    @Ignore
    public CustomerDailyReport getProfileCustomerDailyReport() { return customerDailyReport;
    }
    @Ignore
    public void setCustomerDailyReport(CustomerDailyReport customerDailyReport) { this.customerDailyReport = customerDailyReport; }


    @Ignore
    public AdminUser getProfileAdminUser() { return adminUser;
    }
   @Ignore
    public void setPin(String pin) {
        this.pin = pin;

    }

    public String getProfileGender() {
        return profileGender;
    }
    public String getProfileDob() {
        return profileDob;
    }
    @Ignore
    public User.User_Type getProfileType() {
        return type;
    }

    public Uri getProfilePicture() {
        return profilePicture;
    }
    @Ignore
    public String getProfilePin() {
        return pin;
    }
    @Ignore

    public void setAdminUser(AdminUser adminUser) { this.adminUser = adminUser;
    }
    @Ignore

    public CustomerManager getCustomerManager() { return customerManager;
    }
    @Ignore
    public void setCustomerManager(CustomerManager customerManager) { this.customerManager = customerManager; }
    @Ignore
    public UserSuperAdmin getSuperAdmin() { return superAdmin;
    }
    @Ignore
    public void setSuperAdmin(UserSuperAdmin superAdmin) { this.superAdmin = superAdmin; }

    @Ignore
    public void setTransactionsFromDB(ArrayList<Transaction> transactions) {
        this.transactions = transactions;

    }
    @Ignore
    public void setTimeLines(ArrayList<TimeLine> timeLineArrayList) {
        this.timeLineArrayList = timeLineArrayList;
    }
    @Ignore
    public ArrayList<TimeLine> getTimeLines() { return timeLineArrayList;
    }
    @Ignore
    public Customer getTimelineCustomer() { return customer;
    }
    @Ignore
    public void setTimelineCustomer(Customer timelineCustomer) { this.customer = timelineCustomer; }

    @Ignore
    public void setSkyLightPackagesFromDB(ArrayList<SkyLightPackage> skyLightPackages) {
        this.skyLightPackages = skyLightPackages;
    }
    @Ignore
    public ArrayList<Payee> getPayees() { return payees; }

    @Ignore

    public ArrayList<Transaction> getTransactions() { return transactions;
    }
    @Ignore
    public ArrayList<LatLng> getProfileLocations() {
        return latLngs;
    }
    /*public long getDbId() {
        return dbId;
    }
    public void setDbId(long profileID) { this.profileID = profileID; }*/
    /*public void addAccount(Account account) throws ConnectionFailedException {
        if (account != null && !this.accounts.contains(account)) {
            this.accounts.add(account);
            // add the user account in the database if it is not already there
            if (!DBHelper.getAccountIds(this.getuID()).contains(account.getId())) {
                //DatabaseInsertHelper.insertAccount(this.getuID(), account.getId());
            }
        }
    }*/
    @Ignore
    public final boolean authenticateProfile(String password) throws ConnectionFailedException {
        if (DBHelper.getPassword(this.pID) != null) {
            this.authenticated = PasswordHelpers.comparePassword(Objects.requireNonNull(DBHelper.getPassword(this.pID)),
                    password);
        }
        return this.authenticated;
    }
    @Ignore

    public String toString() {
        return "ID: " + String.valueOf(this.pID) + "\nlastName: " + this.profileFirstName + "\nphoneNumber: "
                + String.valueOf(this.profileEmail) + "\ndob: " + this.profileAddress + "\njoinedDate: " + this.profileGender;
    }
    @Ignore

    public void addPaymentDocument(int id, String title, int customerId, int reportId, Uri documentLink,String status) {
        paymentDocArrayList = new ArrayList<>();
        String docNo = "Doc" + (paymentDocArrayList.size() + 1);
        PaymentDoc paymentDoc = new PaymentDoc(id,title, customerId, reportId,documentLink,status);
        paymentDocArrayList.add(paymentDoc);
    }
    @Ignore

    public void addAddress( Locale address) {
        addresses= new ArrayList<>();
        String addressNo = "No:" + (addresses.size() + 1);
        Address address1 = new Address(address);
        addresses.add(address1);
    }
    @Ignore


    public void addBusiness(long businessID,long profileID, String businessName, String bizEmail, String bizAddress, String bizPhoneNo, String bizType, String bizRegNo,String dateOfJoin,String status) {
        businesses= new ArrayList<>();
        String bizNo = "Biz:" + (businesses.size() + 1);
        Business business = new Business(businessID,profileID, businessName, bizEmail, bizAddress, bizPhoneNo,  bizType,  bizRegNo,dateOfJoin,status);
        businesses.add(business);
    }
    @Ignore
    public void addStandingOrderAcct(int soNo,double expectedAmount) {
        standingOrders= new ArrayList<>();
        soNo = standingOrders.size() + 1;
        StandingOrder business = new StandingOrder(soNo,expectedAmount);
        standingOrders.add(business);

    }
    @Ignore

    public void addCode(int PROFILE_ID,String CODE_OWNER_PHONE, long CODE_PIN,String CODE_DATE,String CODE_STATUS,String CODE_MANAGER) {
        paymentCodeArrayList= new ArrayList<>();
        String codeNo = "Code" + (paymentCodeArrayList.size() + 1);
        PaymentCode paymentCode = new PaymentCode(PROFILE_ID,CODE_OWNER_PHONE,CODE_PIN, CODE_DATE, CODE_STATUS,CODE_MANAGER);
        paymentCodeArrayList.add(paymentCode);
    }
    @Ignore
    public void addAccount(String accountBank, String accountName, long accountNumber, double accountBalance, AccountTypes accountTypes) {
        accounts= new ArrayList<>();
        String accNo = "A" + (accounts.size() + 1);
        Account account = new Account(accountBank,accountName, accountNumber, accountBalance,accountTypes);
        accounts.add(account);

    }
    @Ignore
    public void addAccount(int virtualAccountID, String customerBank, String customerNames, long accountNo, double accountBalance, AccountTypes accountTypes) {
        accounts= new ArrayList<>();
        String accNo = "A" + (accounts.size() + 1);
        Account account = new Account(virtualAccountID,customerBank,customerNames, String.valueOf(accountNo), accountBalance,accountTypes);
        accounts.add(account);
    }
    @Ignore
    public void addSavingsGrp(int gsID, String groupName, String adminName, String purpose, double amount, Date startDate, Date endDate, String status) {
        savingsGroups= new ArrayList<>();
        String GSNo = "A" + (savingsGroups.size() + 1);
        SavingsGroup savingsGroup = new SavingsGroup(gsID,groupName,adminName, purpose, amount,startDate,endDate,status);
        savingsGroups.add(savingsGroup);
    }
    @Ignore
    public void addInvAcct(int comAcctID, double v, String zero_interest, String currentDate) {

    }
    @Ignore
    public void addProperty(long propertyID, long profileId, String tittleOfProperty, String description, String propertyType, String town, String lga, Double price, String priceDuration, String propertyCapacity, String typeOfLetting,Date propertyDate,Uri propertylink,String status) {
        properties= new ArrayList<>();
        String propertyNo = "property" + (properties.size() + 1);
        Properties property = new Properties(propertyID,  profileId, tittleOfProperty, description,  propertyType,town, lga, price, priceDuration, propertyCapacity, typeOfLetting,propertyDate,propertylink,status);
        properties.add(property);
    }
    @Ignore

    public void addTellerReport(int dbaseID, int reportID, String date, double balance, String status) {
        ArrayList<TellerReport> tellerReports = null;
        reportID = tellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(reportID, dbaseID,date,balance,status);
        tellerReportArrayList.add(dailyReport);
    }
    @Ignore
    public void addTellerReport(int keyExtraReportId, String officeBranch, double amountEntered, int noOfCustomers, String reportDate) {
        ArrayList<TellerReport> tellerReports = null;
        keyExtraReportId = tellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(keyExtraReportId, officeBranch,amountEntered,noOfCustomers,reportDate);
        tellerReportArrayList.add(dailyReport);
    }
    @Ignore
    public void addPayment(String type, double totalToWithdraw, Date date, long paymentCode, String acctType, String office, String status) {
        ArrayList<Payment> paymentArrayList = null;
        if (paymentArrayList != null) {
            String paymentNo = "Payment:" + (paymentArrayList.size() + 1);
        }
        payment = new Payment(type,totalToWithdraw, date,paymentCode,acctType,office,status);
        paymentArrayList.add(payment);

    }
    @Ignore

    public void addReport(int count,int customerID,String customerName,int packageID, int reportID, double savingsAmount, int numberOfDays, double totalAmountSum, int daysRemaining, double amountRemaining, String reportDate, String status) {
        count = dailyReports.size() + 1;
        CustomerDailyReport dailyReport = new CustomerDailyReport( count,customerID,customerName,packageID, reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining,  reportDate, status);
        dailyReports.add(dailyReport);
    }
    @Ignore
    public void addGroupSavings(int GSNo, String groupName, double amount, Date savingsDate, String status) {
        savingsGroups= new ArrayList<>();
        GSNo = groupSavings.size() + 1;
        GroupSavings groupSavings1 = new GroupSavings(GSNo,groupName,amount, savingsDate, status);
        groupSavings.add(groupSavings1);
    }
    @Ignore
    public void addCustomer(int uID, String surname, String firstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String dateJoined, String userName, String password) {
        customers= new ArrayList<>();
        String CusNo = "C" + (customers.size() + 1);
        Customer customer = new Customer(uID,surname, firstName, customerPhoneNumber,customerEmailAddress,customerAddress,customerGender,customerOffice,customerState,profilePicture,dateJoined,userName,password);
        customers.add(customer);

    }
    @Ignore
    public void addCustomerManager(int uID, String surname, String firstName, String customerPhoneNumber,String dateOfBirth, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String dateJoined, String userName, String password) {
        customerManagers= new ArrayList<>();
        String CMNo = "C" + (customerManagers.size() + 1);
        CustomerManager customer = null;
        customer = new CustomerManager(uID,surname, firstName, customerPhoneNumber, dateOfBirth,customerEmailAddress,customerAddress,customerGender,customerOffice,customerState,profilePicture,dateJoined,userName,password);
        customerManagers.add(customer);


    }
    @Ignore
    public void addCustomerManager1(int uID, String surname, String firstName,  String managerGender, String managerOffice) {
        customerManagers= new ArrayList<>();
        String CMNo = "C" + (customerManagers.size() + 1);
        CustomerManager customerManager = null;
        customerManager = new CustomerManager(uID,surname, firstName, managerGender,managerOffice);
        customerManagers.add(customerManager);


    }
    @Ignore
    public void addSavings(int  profileId, int  customerId, int id, double amount, double numberOfDay, double total, int daysRemaining, double amountRemaining, String date, String status) {
        dailyReports= new ArrayList<>();
        String savingsCount = "C" + (dailyReports.size() + 1);
        CustomerDailyReport customerDailyReport = new CustomerDailyReport(profileId,customerId, id, amount,numberOfDay,total,daysRemaining,amountRemaining,date,status);
        dailyReports.add(customerDailyReport);
    }
    @Ignore
    public void addSavings(int profileId, int customerId, int newRecordID, double packageAmount, int numberOfDaysConverted, double totalForTheDay, int newNumberOfDaysRem, double newAmountRemaining, String reportDate, String status) {
        dailyReports= new ArrayList<>();
        String savingsCount = "C" + (dailyReports.size() + 1);
        CustomerDailyReport customerDailyReport = new CustomerDailyReport(profileId,customerId, newRecordID, packageAmount,numberOfDaysConverted,totalForTheDay,newNumberOfDaysRem,newAmountRemaining,reportDate,status);
        dailyReports.add(customerDailyReport);
    }
    @Ignore
    public void addLoans(int loanId,double amount, String loanStartDate, String status,String loanEndDate,double interest1) {
        loans= new ArrayList<>();
        int loanNumber = Integer.parseInt(("Loan" + (loans.size() + 1)));
        Loan loan = new Loan(loanNumber, amount,loanStartDate,status,loanEndDate,interest1);
        loans.add(loan);
    }
    @Ignore
    public void addTimeLine(String tittle,String timelineDetails) {
        timeLines= new ArrayList<>();
        String history = "History" + (timeLines.size() + 1);
        TimeLine timeLine = new TimeLine(tittle,timelineDetails);
        timeLines.add(timeLine);
    }
    @Ignore
    public void setAccountsFromDB(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
    @Ignore
    public void addTransaction(long transactionId, String surname, String firstName, String customerPhoneNumber, double amount,String accountNumber,String description,String date,String type) {
        transactions= new ArrayList<>();
        String transactionCount = "C" + (transactions.size() + 1);
        Transaction transaction1 = new Transaction(transactionId,surname, firstName, customerPhoneNumber,amount,accountNumber,description,date,type);
        transactions.add(transaction1);
    }
    @Ignore

    public void addTransferTransaction(Account sendingAcc, Account receivingAcc, double transferAmount) {

        sendingAcc.setAccountBalance(sendingAcc.getAccountBalance() - transferAmount);
        receivingAcc.setAccountBalance(receivingAcc.getAccountBalance() + transferAmount);

        int sendingAccTransferCount = 0;
        int receivingAccTransferCount = 0;
        for (int i = 0; i < sendingAcc.getTransactions1().size(); i ++) {
            if (sendingAcc.getTransactions1().get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                sendingAccTransferCount++;
            }
        }
        for (int i = 0; i < receivingAcc.getTransactions1().size(); i++) {
            if (receivingAcc.getTransactions1().get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                receivingAccTransferCount++;
            }
        }

        //sendingAcc.getTransactions1().add(new Transaction(Integer.parseInt("T" + (sendingAcc.getTransactions1().size() + 1) + "-T" + (sendingAccTransferCount+1)), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
        //receivingAcc.getTransactions1().add(new Transaction(Integer.parseInt("T" + (receivingAcc.getTransactions1().size() + 1) + "-T" + (receivingAccTransferCount+1)), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
    }
    @Ignore

    public void addPayee(String payeeName) {
        payees= new ArrayList<>();
        String payeeID = "P" + (payees.size() + 1);
        Payee payee = new Payee(payeeID, payeeName);
        payees.add(payee);
    }
    @Ignore

    public void setPayeesFromDB(ArrayList<Payee> payees) {
        this.payees = payees;
    }
    @Ignore
    public void setCustomersFromDB(ArrayList<Customer> customers) {
        this.customers = customers;
    }
    @Ignore

    public void addBorrowingTransaction(Account receivingAccount, double amountToBorrow) {
        receivingAccount.setAccountBalance(receivingAccount.getAccountBalance() - amountToBorrow);

        int receivingAccBorrowingCount = 0;
        for (int i = 0; i < receivingAccount.getTransactions1().size(); i ++) {
            if (receivingAccount.getTransactions1().get(i).getTransactionType() == Transaction.TRANSACTION_TYPE.BORROWING) {
                receivingAccBorrowingCount++;
            }
        }


        //receivingAccount.getTransactions1().add(new Transaction("T" + (receivingAccount.getTransactions1().size() + 1) + "-T" + (receivingAccBorrowingCount+1), receivingAccount.toTransactionString(), receivingAccount.toTransactionString(), amountToBorrow));

    }
    @Ignore


    public int leaveMessage(String message, int userId) {
        return 0;
    }
    @Ignore

    public void addNewSkylightPackage(int profileID, int customerID, int packageID, String packageType, double savingsAmount, int packageDuration, String startDate, double grandTotal, String endDate, String just_stated) {
        skyLightPackages= new ArrayList<>();
        String packageNo = "Package:" + (skyLightPackages.size() + 1);
        skyLightPackage = new SkyLightPackage(profileID,customerID, packageID, SkyLightPackage.SkylightPackage_Type.valueOf(packageType),savingsAmount,packageDuration,startDate,grandTotal,endDate, profileStatus);
        skyLightPackages.add(skyLightPackage);
    }
    @Ignore


    public void addStocksAll(int stocksID, String selectedStockPackage, String uStockType, String uStockModel, String uStockColor, String uStockSize, int uStockQuantity, String selectedOfficeBranch, double uStockPricePerUnit, String stockDate) {
        stocks= new ArrayList<>();
        int  stocksNo = stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,selectedStockPackage, uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);
        stocks.add(stocks1);

    }
    @Ignore
    public void addStocks(int stocksID, String stocksName, String uStockType,  int uStockQuantity, long stockCode,String selectedOfficeBranch, String stockDate,String status) {
        stocks= new ArrayList<>();
        int  stocksNo = stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,stocksName,uStockType,uStockQuantity,stockCode,stockDate,status);
        stocks.add(stocks1);

    }

    @Ignore
    public void addTellerCash(int reportID,int packageID, String finalItemType, double tellerAmount, String tellerName, String officeBranch, String packageStartDate) {
        tellerCashes= new ArrayList<>();
        int  stocksNo = tellerCashes.size() + 1;
        TellerCash tellerCash = new TellerCash(reportID,packageID,finalItemType,tellerAmount,tellerName,officeBranch,packageStartDate);
        tellerCashes.add(tellerCash);

    }
    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }
    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(pID);
        parcel.writeLong(profCusID);
        parcel.writeString(profileFirstName);
        parcel.writeString(profileLastName);
        parcel.writeString(profileState);
        parcel.writeString(profileUserName);
        parcel.writeString(profilePassword);
        parcel.writeString(profileEmail);
        parcel.writeString(profileDob);
        parcel.writeLong(profileSponsorID);
        parcel.writeString(profileGender);
        parcel.writeString(profileAddress);
        parcel.writeString(profileOffice);
        parcel.writeString(profilePhoneNumber);
        parcel.writeString(profileDateJoined);
        parcel.writeString(machine);
        parcel.writeParcelable(payment, i);
        parcel.writeString(identity);
        parcel.writeString(businessName);
        parcel.writeString(unit);
        parcel.writeString(ward);
        parcel.writeString(town);
        parcel.writeParcelable(profilePicture, i);
        parcel.writeParcelable(officeBranch, i);
        parcel.writeString(nin);
        parcel.writeString(profileRole);
        parcel.writeParcelable(adminUser2, i);
        parcel.writeTypedList(accounts);
        parcel.writeTypedList(stocks);
        parcel.writeTypedList(addresses);
        parcel.writeTypedList(payees);
        parcel.writeTypedList(timeLines);
        parcel.writeTypedList(customers);
        parcel.writeTypedList(businesses);
        parcel.writeTypedList(properties);
        parcel.writeTypedList(dailyReports);
        parcel.writeTypedList(skyLightPackages);
        parcel.writeTypedList(loans);
        parcel.writeTypedList(paymentArrayList);
        parcel.writeTypedList(paymentCodeArrayList);
        parcel.writeTypedList(paymentDocArrayList);
        parcel.writeTypedList(customerManagers);
        parcel.writeTypedList(adminUsers);
        parcel.writeTypedList(adminBankDeposits);
        parcel.writeTypedList(stockTransferArrayList);
        parcel.writeParcelable(transaction, i);
        parcel.writeParcelable(loan, i);
        parcel.writeParcelable(birthday, i);
        parcel.writeParcelable(business, i);
        parcel.writeParcelable(groupAccount, i);
        parcel.writeParcelable(savingsGroup, i);
        parcel.writeTypedList(savingsGroups);
        parcel.writeTypedList(groupAccounts);
        parcel.writeParcelable(standingOrderAcct, i);
        parcel.writeTypedList(tellerCashes);
        parcel.writeTypedList(standingOrders);
        parcel.writeParcelable(standingOrder, i);
        parcel.writeParcelable(customerDailyReport, i);
        parcel.writeParcelable(skyLightPackage, i);
        parcel.writeParcelable(payee, i);
        parcel.writeParcelable(adminUser, i);
        parcel.writeParcelable(customerManager, i);
        parcel.writeParcelable(superAdmin, i);
        parcel.writeParcelable(customer, i);
        parcel.writeParcelable(account, i);
        parcel.writeTypedList(transactions);
        parcel.writeTypedList(timeLineArrayList);
        parcel.writeTypedList(latLngs);
        parcel.writeParcelable(lastLocation, i);
        parcel.writeLong(pID);
        parcel.writeString(profileStatus);
        parcel.writeTypedList(groupSavings);
        parcel.writeString(accountBalance);
        parcel.writeInt(profileNo);
        parcel.writeTypedList(tellerReportArrayList);
    }

    @Ignore
    public void addTranxGranding(int tranxPayoutID,int customerID,String customerName, double amountRequested, String acctBank, String bankName, String acctBankNo, String s, String requestDate) {
        payoutNo = transactionGrantingArrayList.size() + 1;
        TransactionGranting transactionGranting = new TransactionGranting(tranxPayoutID,customerID,customerName,amountRequested,acctBank,bankName,acctBankNo,s,requestDate);
        transactionGrantingArrayList.add(transactionGranting);
    }
    @Ignore

    public Profile getTimelineProfile() {
        return profile;
    }
    @Ignore

    public void setTimelineProfile(Profile timelineProfile) {
        this.profile = timelineProfile;
    }
}
