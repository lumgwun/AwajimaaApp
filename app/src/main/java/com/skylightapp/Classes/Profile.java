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
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;
import com.quickblox.core.model.QBEntity;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.Stocks;

import com.skylightapp.SuperAdmin.Skylight;
import com.skylightapp.Tellers.TellerCash;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Markets.Market.MARKET_ID;
import static com.skylightapp.Markets.Market.MARKET_TABLE;

//@Entity(tableName = "RoomProfileTable")
public class Profile implements Parcelable, Serializable, BaseColumns {
    //@Ignore
    public static final String PROFILE_SURNAME = "P_surname";
    //@Ignore
    public static final String PROFILE_FIRSTNAME = "P_first_name";
    //@Ignore
    public static final String PROFILE_EMAIL = "P_email";
    //@Ignore
    public static final String PROFILE_DOB = "P_dob";
    //@Ignore
    public static final String PROFILE_ADDRESS = "P_street";
    //@Ignore
    public static final String PROFILE_GENDER = "P_gender";
    //@Ignore
    public static final String PROFILE_PHONE = "p_phone";
    //@Ignore
    public static final String PROFILE_ROLE = "p_role";
    //@Ignore
    public static final String PROFILE_DATE_JOINED = "p_join_date";
    //@Ignore
    public static final String PROFILE_NEXT_OF_KIN = "p_next_of_kin";
    //@Ignore
    public static final String PROFILE_STATUS = "p_status";
    //@Ignore
    public static final String PROFILE_PASSWORD = "p_passCode";
    //@Ignore
    public static final String PROFILES_TABLE = "profiles_table";
    //@Ignore
    public static final String PROFILE_UNIT = "p_unit";
    //@Ignore
    public static final String PROFILE_WARD = "p_ward";
    //@Ignore
    public static final String PROFILE_TOWN = "p_town";
    //@Ignore
    public static final String PROFILE_COUNTRY = "p_country";
    //@Ignore
    public static final String PROFILE_SPONSOR_ID = "p_sponsor";
    //@Ignore

    public static final String PROFILE_ID = "profile_id";
    //@Ignore
    public static final String PICTURE_TABLE = "pictureTable";
    //@Ignore
    public static final String PICTURE_URI = "picture_uri";
    //@Ignore
    public static final String PROFILE_USERNAME = "p_username";
    //@Ignore
    public static final String PASSWORD = "p_password";
    //@Ignore
    public static final String PASSWORD_ID = "password_Id";

   // @Ignore
    public static final String PASSWORD_TABLE = "password_table";
    //@Ignore
    public static final String PROFILE_NIN = "profile_NIN";
    //@Ignore
    public static final String PROFILE_STATE = "p_state";
    //@Ignore
    public static final String PROFILE_OFFICE = "p_office";
    //@Ignore
    public static final String PROFILE_PIC_ID = "picture_id";
    //@Ignore
    public static final String PROFILE_CUS_ID_KEY = "customer_ID_Foreign_key";

    //@Ignore
    public static final String CUS_ID_PIX_KEY = "cus_ID_Pix_key";
    //@Ignore
    public static final String CUS_ID_PASS_KEY = "cus_ID_Pass_key";

    //@Ignore
    public static final String PROF_SPONSOR_ID = "prof_SponsorID";

    //@Ignore
    public static final String PROFID_FOREIGN_KEY_PIX = "profID_For_keyP";


    //@Ignore
    public static final String PROF_SPONSOR_KEY = "prof_Sponsor_Key";
    //@Ignore
    public static final String SPONSOR_TABLE = "sponsor_Table";
    //@Ignore
    public static final String SPONSOR_TABLE_ID = "sponsor_TableID";

    //@Ignore
    public static final String SPONSOR_TABLE_CUS_ID = "sponsor_TableCus_ID";

    //@Ignore
    public static final String SPONSOR_TABLE_PROF_ID = "sponsor_Table_Prof_ID";

    //@Ignore
    public static final String SPONSOR_TABLE_PHONE = "sponsor_Table_Phone";

    //@Ignore
    public static final String SPONSOR_TABLE_EMAIL = "sponsor_Table_Email";
    public static final String PICTURE_MARKET_ID = "pic_market_ID";




    //@Ignore
    public static final String PROF_ID_FOREIGN_KEY_PASSWORD = "prof_ID_FkeyPassW";
    public static final String PROF_BUSINESS_ID = "prof_BizID";
    public static final String PROF_MARKET_ID = "market_ID";


    //@Ignore
    public static final String CREATE_PIXTURE_TABLE = "CREATE TABLE " + PICTURE_TABLE + " (" + PROFILE_PIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFID_FOREIGN_KEY_PIX + " INTEGER, " + CUS_ID_PIX_KEY + " INTEGER , " +
            PICTURE_URI + " TEXT ,"+ PICTURE_MARKET_ID + " TEXT, "+"FOREIGN KEY(" + PICTURE_MARKET_ID  + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + "),"+"FOREIGN KEY(" + PROFID_FOREIGN_KEY_PIX  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + CUS_ID_PIX_KEY + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";
    //@Ignore
    public static final String CREATE_PASSWORD_TABLE = "CREATE TABLE IF NOT EXISTS " + PASSWORD_TABLE + " (" + PASSWORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROF_ID_FOREIGN_KEY_PASSWORD + " INTEGER, "+
            PASSWORD + " TEXT , " + CUS_ID_PASS_KEY + " INTEGER , " +"FOREIGN KEY(" + PROF_ID_FOREIGN_KEY_PASSWORD  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +
            "FOREIGN KEY(" + CUS_ID_PASS_KEY + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    public static final String CREATE_PROFILES_TABLE = "CREATE TABLE " + PROFILES_TABLE + " (" + PROFILE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFILE_SURNAME + " TEXT, " + PROFILE_FIRSTNAME + " TEXT, " + PROFILE_PHONE + " TEXT, " + PROFILE_EMAIL + " TEXT, " + PROFILE_DOB + " TEXT, " + PROFILE_GENDER + " TEXT, " +
            PROFILE_ADDRESS + " TEXT, " + PROFILE_NIN + " TEXT, " + PROFILE_UNIT + " TEXT, " + PROFILE_WARD + " TEXT, " + PROFILE_TOWN + " TEXT, " + PROFILE_STATE + " TEXT, " + PROFILE_COUNTRY + " TEXT, " + PROFILE_OFFICE + " TEXT, " + PROFILE_DATE_JOINED + " TEXT, " + PROFILE_ROLE + " TEXT, " + PROFILE_USERNAME + " TEXT, " + PROFILE_PASSWORD + " TEXT, " + PROFILE_STATUS + " TEXT, " + PROFILE_NEXT_OF_KIN + " TEXT,"+ PROFILE_SPONSOR_ID + " TEXT,"+ PROFILE_CUS_ID_KEY + " INTEGER,"+ PROF_BUSINESS_ID + " TEXT,"+ PROF_MARKET_ID + " TEXT," + "FOREIGN KEY(" + PROFILE_CUS_ID_KEY + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    //@Ignore
    public static final String CREATE_SPONSOR_TABLE = "CREATE TABLE " + SPONSOR_TABLE + " (" + SPONSOR_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SPONSOR_TABLE_CUS_ID + " INTEGER, " + SPONSOR_TABLE_PROF_ID + " INTEGER, " + SPONSOR_TABLE_PHONE + " TEXT, " + SPONSOR_TABLE_EMAIL + " TEXT, " + "FOREIGN KEY(" + SPONSOR_TABLE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";


    //@Ignore
    private Profile profile;
    @Ignore
    private Payment profile_Payment;
    @Ignore
    private String profile_Identity, businessName, profile_Unit, profile_Ward, profile_Town;
    @Ignore

    private OfficeBranch profile_OfficeBranch;

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
    private String profile_Nin;


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


    @ColumnInfo(name = PROFILE_CUS_ID_KEY)
    private int profCusID;

    @ColumnInfo(name = PROFID_FOREIGN_KEY_PIX)
    private int profPixID;


    @ColumnInfo(name = PROF_ID_FOREIGN_KEY_PASSWORD)
    private int profPassID;


    @ColumnInfo(name = PROFILE_STATUS)
    private String profileStatus;

    private int profileBusinessID;
    private int profileMarketID;
    private QBUser profQbUser;
    private QBEntity profQbEntity;


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
    private String profile_Machine;



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
    public void setProfileMachine(String machine) { this.profile_Machine = machine;
    }
    @Ignore
    public String getProfileMachine() {
        return profile_Machine;
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
    public ArrayList<Address> getProfileAddresses() { return profile_Addresses; }
    @Ignore
    AdminUser adminUser2;


    @Ignore
    private ArrayList<Account> profile_Accounts;
    @Ignore
    private ArrayList<Stocks> profile_Stocks;
    @Ignore
    private ArrayList<Address> profile_Addresses;
    @Ignore
    private ArrayList<Payee> profile_Payees;
    @Ignore
    private ArrayList<TimeLine> profile_TimeLines;
    @Ignore
    private ArrayList<Customer> profile_Customers;
    @Ignore
    private ArrayList<OtherBusiness> profile_Other_Businesses;
    @Ignore
    private ArrayList<CustomerDailyReport> profile_DailyReports;
    @Ignore
    private ArrayList<SkyLightPackage> profile_SkyLightPackages;
    @Ignore
    private ArrayList<Loan> profile_Loans;
    @Ignore
    private ArrayList<Payment> profile_PaymentArrayList;
    @Ignore
    private ArrayList<PaymentCode> profile_PaymentCodeArrayList;
    @Ignore
    private ArrayList<PaymentDoc> profile_PaymentDocArrayList;
    @Ignore

    private ArrayList<CustomerManager> profile_CustomerManagers;
    @Ignore
    private ArrayList<AdminUser> adminUsers;
    @Ignore
    private ArrayList<AdminBankDeposit> profile_AdminBankDeposits;
    @Ignore
    private ArrayList<StockTransfer> profile_StockTransferArrayList;
    @Ignore
    private ArrayList<TellerCash> profile_TellerCashes;
    @Ignore
    private ArrayList<StandingOrder> profile_StandingOrders;
    @Ignore
    private ArrayList<SavingsGroup> profile_SavingsGroups;
    @Ignore
    private ArrayList<GroupAccount> profile_GroupAccounts;
    @Ignore
    private ArrayList<Transaction> profile_Transactions;
    @Ignore
    private ArrayList<TimeLine> profile_TimeLineArrayList;
    @Ignore
    private ArrayList<LatLng> profile_LatLngs;
    @Ignore
    ArrayList<TellerReport> ptellerReportArrayList;
    @Ignore
    ArrayList<TransactionGranting> profile_TranxGrantings;
    @Ignore
    private ArrayList<SmsMessage.MessageClass> messageClasses;
    @Ignore
    private ArrayList<GroupSavings> profile_GroupSavings;

    @Ignore
    private Transaction profile_Tranx;
    @Ignore
    private Loan profile_Loan;
    @Ignore
    private Birthday profile_Birthday;
    @Ignore
    private OtherBusiness profile_Biz;
    @Ignore
    private GroupAccount profile_GroupAcct;
    @Ignore
    private String profile_AuthenticationKey;
    @Ignore
    Context context;
    @Ignore
    protected transient boolean profile_Authenticated = false;
    @Ignore
    protected RolesEnumMap enumMap = new RolesEnumMap(context);
    @Ignore
    private Skylight profile_Skylight;
    @Ignore
    private SavingsGroup profile_SavingsGroup;
    @Ignore
    StandingOrderAcct profile_SOAcct;
    @Ignore
    private StandingOrder profile_StandingOrder;
    @Ignore
    private CustomerDailyReport profile_CusDailyReport;
    @Ignore
    private SkyLightPackage profile_SkyLightPackage;
    @Ignore
    private Payee profile_Payee;
    @Ignore
    private AdminUser profile_AdminUser;
    @Ignore
    private CustomerManager profile_CustomerManager;
    @Ignore
    private UserSuperAdmin profile_SuperAdmin;
    @Ignore
    SmsMessage.MessageClass messageClass;
    @Ignore
    private Customer profile_Customer;
    @Ignore
    Account profile_Account;
    @Ignore
    private  LatLng profile_LastLocation;
    @Ignore
    DBHelper dbHelper;

    @Ignore
    String profile_AccountBalance;
    @Ignore
    private int payoutNo;
    @Ignore
    int profileNo;




    public Profile(int profile_id, String surName, String profileFirstName, String phone, String profileEmail, String profileDob, String profileGender, String profileAddress, String s, String profileState, String profile_OfficeBranch, String joinedDate, String profileRole, String userName, String profilePassword, String profileStatus) {
        super();
        this.pID = profile_id;
        this.profileLastName = surName;
        this.profilePhoneNumber = phone;
        this.profileEmail = profileEmail;
        this.profile_Identity = profile_Identity;
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
        this.profile_Identity = nIN;
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
        profile_Machine = in.readString();
        profile_Payment = in.readParcelable(Payment.class.getClassLoader());
        profile_Identity = in.readString();
        businessName = in.readString();
        profile_Unit = in.readString();
        profile_Ward = in.readString();
        profile_Town = in.readString();
        profilePicture = in.readParcelable(Uri.class.getClassLoader());
        profile_OfficeBranch = in.readParcelable(OfficeBranch.class.getClassLoader());
        profile_Nin = in.readString();
        profileRole = in.readString();
        profile_Accounts = in.createTypedArrayList(Account.CREATOR);
        profile_Stocks = in.createTypedArrayList(Stocks.CREATOR);
        profile_Addresses = in.createTypedArrayList(Address.CREATOR);
        profile_Payees = in.createTypedArrayList(Payee.CREATOR);
        //timeLines = in.createTypedArrayList(TimeLine.CREATOR);
        profile_Customers = in.createTypedArrayList(Customer.CREATOR);
        //businesses = in.createTypedArrayList(Business.CREATOR);
        profile_DailyReports = in.createTypedArrayList(CustomerDailyReport.CREATOR);
        profile_SkyLightPackages = in.createTypedArrayList(SkyLightPackage.CREATOR);
        //loans = in.createTypedArrayList(Loan.CREATOR);
        profile_PaymentArrayList = in.createTypedArrayList(Payment.CREATOR);
        //paymentCodeArrayList = in.createTypedArrayList(PaymentCode.CREATOR);
        //paymentDocumentArrayList = in.createTypedArrayList(PaymentDocument.CREATOR);
        //customerManagers = in.createTypedArrayList(CustomerManager.CREATOR);
        //adminUsers = in.createTypedArrayList(AdminUser.CREATOR);
        profile_AdminBankDeposits = in.createTypedArrayList(AdminBankDeposit.CREATOR);
        profile_StockTransferArrayList = in.createTypedArrayList(StockTransfer.CREATOR);
        profile_Tranx = in.readParcelable(Transaction.class.getClassLoader());
        profile_Loan = in.readParcelable(Loan.class.getClassLoader());
        profile_Birthday = in.readParcelable(Birthday.class.getClassLoader());
        profile_Biz = in.readParcelable(OtherBusiness.class.getClassLoader());
        profile_GroupAcct = in.readParcelable(GroupAccount.class.getClassLoader());
        profile_SavingsGroup = in.readParcelable(SavingsGroup.class.getClassLoader());
        profile_SavingsGroups = in.createTypedArrayList(SavingsGroup.CREATOR);
        profile_GroupAccounts = in.createTypedArrayList(GroupAccount.CREATOR);
        profile_SOAcct = in.readParcelable(StandingOrderAcct.class.getClassLoader());
        profile_TellerCashes = in.createTypedArrayList(TellerCash.CREATOR);
        //standingOrders = in.createTypedArrayList(StandingOrder.CREATOR);
        profile_StandingOrder = in.readParcelable(StandingOrder.class.getClassLoader());
        profile_CusDailyReport = in.readParcelable(CustomerDailyReport.class.getClassLoader());
        profile_SkyLightPackage = in.readParcelable(SkyLightPackage.class.getClassLoader());
        profile_Payee = in.readParcelable(Payee.class.getClassLoader());
        profile_AdminUser = in.readParcelable(AdminUser.class.getClassLoader());
        profile_CustomerManager = in.readParcelable(CustomerManager.class.getClassLoader());
        profile_SuperAdmin = in.readParcelable(UserSuperAdmin.class.getClassLoader());
        profile_Customer = in.readParcelable(Customer.class.getClassLoader());
        profile_Account = in.readParcelable(Account.class.getClassLoader());
        profile_Transactions = in.createTypedArrayList(Transaction.CREATOR);
        //timeLineArrayList = in.createTypedArrayList(TimeLine.CREATOR);
        profile_LatLngs = in.createTypedArrayList(LatLng.CREATOR);
        profile_LastLocation = in.readParcelable(LatLng.class.getClassLoader());
        pID = in.readInt();
        profileStatus = in.readString();
        profile_GroupSavings = in.createTypedArrayList(GroupSavings.CREATOR);
        profile_AccountBalance = in.readString();
        profileNo = in.readInt();
        ptellerReportArrayList = in.createTypedArrayList(TellerReport.CREATOR);
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

    public Profile(int pID, String name, String phoneNo, String profileEmail, String profile_Identity, String userType) {
        this.pID = pID;
        this.profileLastName = name;
        this.profilePhoneNumber = phoneNo;
        this.profileEmail = profileEmail;
        this.profile_Nin = profile_Identity;


    }
    public Profile(int id, String firstName, String lastName, String state, String username) {
        this.pID = id;
        this.profileFirstName = firstName;
        this.profileLastName = lastName;
        this.profileState = state;
        this.profileUserName = username;

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
        this.profile_PaymentArrayList = paymentArrayList;
    }
    @Ignore
    public ArrayList<Payment> getProfilePayments() { return profile_PaymentArrayList;
    }
    @Ignore
    public void setProfilePaymentCodes(ArrayList<PaymentCode> paymentCodeArrayList) {
        this.profile_PaymentCodeArrayList = paymentCodeArrayList;
    }
    @Ignore
    public ArrayList<PaymentCode> getProfilePaymentCodes() { return profile_PaymentCodeArrayList;
    }
    @Ignore
    public void setUserPaymentDocuments(ArrayList<PaymentDoc> paymentDocArrayList) {
        this.profile_PaymentDocArrayList = paymentDocArrayList;
    }
    @Ignore
    public ArrayList<PaymentDoc> getProfilePaymentDocuments() { return profile_PaymentDocArrayList; }

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
        return profile_Identity;
    }
    @Ignore
    public void setProfileIdentity(String identity) {
        this.profile_Identity = identity;

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
        return profile_Unit;
    }
    @Ignore
    public void setProfileUnit(String unit) { this.profile_Unit = unit;
    }
    @Ignore
    public String getProfileWard() {
        return profile_Ward;
    }
    @Ignore
    public void setProfileWard(String ward) { this.profile_Ward = ward;
    }
    @Ignore
    public String getProfileTown() {
        return profile_Town;
    }
    @Ignore
    public void setProfileTown(String town) { this.profile_Town = town; }








    @Ignore
    public StandingOrderAcct getProfileSOAcct() { return profile_SOAcct; }
    @Ignore
    public void setProfileSOAcct(StandingOrderAcct standingOrderAcct) { this.profile_SOAcct = standingOrderAcct; }
    @SuppressLint("SimpleDateFormat")
    @Ignore
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    @Ignore
    public Skylight getProfileSkylight() { return profile_Skylight;
    }
    @Ignore
    public void setProfileSkylight(Skylight skylight) { this.profile_Skylight = skylight;
    }


    @Ignore
    public String getProfile_AuthenticationKey() {
        return profile_AuthenticationKey;
    }
    @Ignore
    public void setProfile_AuthenticationKey(String profile_AuthenticationKey) {
        this.profile_AuthenticationKey = profile_AuthenticationKey;
    }

    @Ignore


    public LatLng getProfileLastKnownLocation() {
        return profile_LastLocation;
    }
    @Ignore
    public LatLng setProfileLastKnownLocation(LatLng lastLocation) {
        this.profile_LastLocation = lastLocation;

        return lastLocation;
    }

    @Ignore
    public boolean getProfile_Authenticated() {
        return false;
    }
    public Birthday getProfileBirthday() { return profile_Birthday; }
    public void setProfileBirthday(Birthday birthday) { this.profile_Birthday = birthday; }
    @Ignore
    public GroupAccount getGroupAccount(long grpAcctID) { return profile_GroupAcct; }
    public void setProfile_GroupAcct(GroupAccount profile_GroupAcct) { this.profile_GroupAcct = profile_GroupAcct; }
    @Ignore
    public SavingsGroup getProfile_SavingsGroup() { return profile_SavingsGroup; }
    @Ignore
    public void setProfile_SavingsGroup(SavingsGroup profile_SavingsGroup) { this.profile_SavingsGroup = profile_SavingsGroup; }

    @Ignore
    public boolean profileAuthenticate(String password, String phoneNumber) {

        return false;
    }



    @Ignore
    public String getProfileBusinessName() {
        if(profile_Biz !=null){
            businessName= profile_Biz.getProfileBusinessName();
        }
        return businessName;
    }
    @Ignore
    public void setProfileBusinessName(String businessName) { this.profileEmail = businessName; }


    public Profile (int userID, String profileFirstName, String profileLastName, String phone, String profileEmail, String username, String profilePassword, String profile_Machine, Uri profilePicture) {
        super();
        this.profileFirstName = profileFirstName;
        this.profileLastName = profileLastName;
        this.pID = userID;
        this.profileUserName = username;
        this.profilePhoneNumber = phone;
        this.profileEmail = profileEmail;
        this.profilePassword = profilePassword;
        this.profile_Machine = profile_Machine;
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
    public Profile (int pID, String profileFirstName, String profileLastName, String profilePhoneNumber, String profileEmail, String profileGender, String profileState, String username, String profile_Identity, String company) {
        super();
        this.pID = pID;
        this.profileFirstName = profileFirstName;
        this.profileLastName = profileLastName;
        this.profileUserName = username;
        this.profileEmail = profileEmail;
        this.businessName = company;
        this.profileGender = profileGender;
        this.profile_Identity = profile_Identity;
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
    public StandingOrder getProfileStandingOrder() { return profile_StandingOrder; }
    @Ignore
    public void setProfileStandingOrder(StandingOrder standingOrder) {
        this.profile_StandingOrder = standingOrder;

    }
    @Ignore
    public OtherBusiness getProfileBusiness() { return profile_Biz; }
    @Ignore
    public void setProfileBusiness(OtherBusiness otherBusiness) {
        this.profile_Biz = otherBusiness;

    }
    @Ignore

    public ArrayList<SavingsGroup> getProfile_SavingsGroups() { return profile_SavingsGroups; }
    @Ignore
    public void setProfile_SavingsGroups(ArrayList<SavingsGroup> profile_SavingsGroups) {
        this.profile_SavingsGroups = profile_SavingsGroups;

    }
    @Ignore
    public ArrayList<GroupSavings> getProfile_GroupSavings() { return profile_GroupSavings;
    }
    @Ignore
    public void setProfile_GroupSavings(ArrayList<GroupSavings> profile_GroupSavings) {
        this.profile_SavingsGroups = profile_SavingsGroups;

    }
    @Ignore
    public ArrayList<GroupAccount> getProfile_GroupAccounts() { return profile_GroupAccounts; }
    @Ignore
    public void setProfile_GroupAccounts(ArrayList<GroupAccount> profile_GroupAccounts) {
        this.profile_GroupAccounts = profile_GroupAccounts;

    }
    @Ignore
    public ArrayList<StandingOrder> getProfile_StandingOrders() { return profile_StandingOrders; }
    @Ignore
    public void setProfile_StandingOrders(ArrayList<StandingOrder> profile_StandingOrders) {
        this.profile_StandingOrders = profile_StandingOrders;

    }
    @Ignore


    public ArrayList<Account> getProfileAccounts() { return profile_Accounts;
    }
    @Ignore
    public ArrayList<Customer> getProfileCustomers() { return profile_Customers;
    }
    @Ignore
    public ArrayList<OtherBusiness> getProfile_Businesses() { return profile_Other_Businesses; }
    @Ignore
    public void setProfile_Businesses(ArrayList<OtherBusiness> profile_Other_Businesses) { this.profile_Other_Businesses = profile_Other_Businesses; }
    @Ignore

    public ArrayList<SkyLightPackage> getProfileSkylightPackages() { return profile_SkyLightPackages; }
    @Ignore
    public ArrayList<CustomerDailyReport> getProfileDailyReports() { return profile_DailyReports; }

    @Ignore
    public void setProfileLoans(ArrayList<Loan> loans) {
        this.profile_Loans = loans;

    }
    @Ignore
    public ArrayList<Loan> getProfileLoans() { return profile_Loans; }
    @Ignore
    public Loan getProfileLoan() { return profile_Loan; }
    @Ignore
    public void setProfileLoan(Loan loan) { this.profile_Loan = loan; }

   @Ignore
    public OfficeBranch getProfileOfficeBranch() {
        return profile_OfficeBranch;
    }
    @Ignore
    public void setProfileOfficeBranch(OfficeBranch officeBranch) {
        this.profile_OfficeBranch = officeBranch;
    }






    public Payee getProfilePayee() { return profile_Payee; }
    @Ignore
    public void setProfilePayee(Payee payee) { this.profile_Payee = payee; }
    @Ignore

    public SmsMessage.MessageClass getMessageClass() { return messageClass; }
    @Ignore
    public void setMessageClass(SmsMessage.MessageClass messageClass) { this.messageClass = messageClass; }
    @Ignore
    public SkyLightPackage getProfileSkyLightPackage() { return profile_SkyLightPackage; }
    @Ignore
    public void setProfileSkyLightPackage(SkyLightPackage skyLightPackage) { this.profile_SkyLightPackage = skyLightPackage; }
    @Ignore
    public Account getProfileAccount() { return profile_Account;
    }
    @Ignore
    public void setProfileAccount(Account account) { this.profile_Account = account;
    }
    @Ignore
    public CustomerDailyReport getProfileCustomerDailyReport() { return profile_CusDailyReport;
    }
    @Ignore
    public void setProfile_CusDailyReport(CustomerDailyReport profile_CusDailyReport) { this.profile_CusDailyReport = profile_CusDailyReport; }


    @Ignore
    public AdminUser getProfileAdminUser() { return profile_AdminUser;
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

    public void setProfile_AdminUser(AdminUser profile_AdminUser) { this.profile_AdminUser = profile_AdminUser;
    }
    @Ignore

    public CustomerManager getProfile_CustomerManager() { return profile_CustomerManager;
    }
    @Ignore
    public void setProfile_CustomerManager(CustomerManager profile_CustomerManager) { this.profile_CustomerManager = profile_CustomerManager; }
    @Ignore
    public UserSuperAdmin getProfile_SuperAdmin() { return profile_SuperAdmin;
    }
    @Ignore
    public void setProfile_SuperAdmin(UserSuperAdmin profile_SuperAdmin) { this.profile_SuperAdmin = profile_SuperAdmin; }

    @Ignore
    public void setTransactionsFromDB(ArrayList<Transaction> transactions) {
        this.profile_Transactions = transactions;

    }
    @Ignore
    public void setProfile_TimeLines(ArrayList<TimeLine> timeLineArrayList) {
        this.profile_TimeLineArrayList = timeLineArrayList;
    }
    @Ignore
    public ArrayList<TimeLine> getProfile_TimeLines() { return profile_TimeLineArrayList;
    }
    @Ignore
    public Customer getProfileCus() { return profile_Customer;
    }
    @Ignore
    public void setProfileCus(Customer timelineCustomer) { this.profile_Customer = timelineCustomer; }

    @Ignore
    public void setSkyLightPackagesFromDB(ArrayList<SkyLightPackage> skyLightPackages) {
        this.profile_SkyLightPackages = skyLightPackages;
    }
    @Ignore
    public ArrayList<Payee> getProfile_Payees() { return profile_Payees; }

    @Ignore

    public ArrayList<Transaction> getProfile_Transactions() { return profile_Transactions;
    }
    @Ignore
    public ArrayList<LatLng> getProfileLocations() {
        return profile_LatLngs;
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
    }
    @Ignore
    public final boolean authenticateProfile(String password) throws ConnectionFailedException {
        if (DBHelper.getPassword(this.pID) != null) {
            this.authenticated = PasswordHelpers.comparePassword(Objects.requireNonNull(DBHelper.getPassword(this.pID)),
                    password);
        }
        return this.authenticated;
    }*/
    @Ignore

    public String toString() {
        return "ID: " + String.valueOf(this.pID) + "\nlastName: " + this.profileFirstName + "\nphoneNumber: "
                + String.valueOf(this.profileEmail) + "\ndob: " + this.profileAddress + "\njoinedDate: " + this.profileGender;
    }
    @Ignore

    public void addPPaymentDocument(int id, String title, int customerId, int reportId, Uri documentLink, String status) {
        profile_PaymentDocArrayList = new ArrayList<>();
        String docNo = "Doc" + (profile_PaymentDocArrayList.size() + 1);
        PaymentDoc paymentDoc = new PaymentDoc(id,title, customerId, reportId,documentLink,status);
        profile_PaymentDocArrayList.add(paymentDoc);
    }
    @Ignore

    public void addPAddress(Locale address) {
        profile_Addresses = new ArrayList<>();
        String addressNo = "No:" + (profile_Addresses.size() + 1);
        Address address1 = new Address(address);
        profile_Addresses.add(address1);
    }
    @Ignore


    public void addPBusiness(long businessID, long profileID, String businessName, String bizEmail, String bizAddress, String bizPhoneNo, String bizType, String bizRegNo, String dateOfJoin, String status) {
        profile_Other_Businesses = new ArrayList<>();
        String bizNo = "Biz:" + (profile_Other_Businesses.size() + 1);
        OtherBusiness otherBusiness = new OtherBusiness(businessID,profileID, businessName, bizEmail, bizAddress, bizPhoneNo,  bizType,  bizRegNo,dateOfJoin,status);
        profile_Other_Businesses.add(otherBusiness);
    }
    @Ignore
    public void addPSOAcct(int soNo, double expectedAmount) {
        profile_StandingOrders = new ArrayList<>();
        soNo = profile_StandingOrders.size() + 1;
        StandingOrder business = new StandingOrder(soNo,expectedAmount);
        profile_StandingOrders.add(business);

    }
    @Ignore

    public void addPCode(int PROFILE_ID, String CODE_OWNER_PHONE, long CODE_PIN, String CODE_DATE, String CODE_STATUS, String CODE_MANAGER) {
        profile_PaymentCodeArrayList = new ArrayList<>();
        String codeNo = "Code" + (profile_PaymentCodeArrayList.size() + 1);
        PaymentCode paymentCode = new PaymentCode(PROFILE_ID,CODE_OWNER_PHONE,CODE_PIN, CODE_DATE, CODE_STATUS,CODE_MANAGER);
        profile_PaymentCodeArrayList.add(paymentCode);
    }
    @Ignore
    public void addPAccount(String accountBank, String accountName, int accountNumber, double accountBalance, AccountTypes accountTypes) {
        profile_Accounts = new ArrayList<>();
        String accNo = "A" + (profile_Accounts.size() + 1);
        Account account = new Account(accountBank,accountName, accountNumber, accountBalance,accountTypes);
        profile_Accounts.add(account);

    }
    @Ignore
    public void addPAccount(int virtualAccountID, String customerBank, String customerNames, long accountNo, double accountBalance, AccountTypes accountTypes) {
        profile_Accounts = new ArrayList<>();
        String accNo = "A" + (profile_Accounts.size() + 1);
        Account account = new Account(virtualAccountID,customerBank,customerNames, String.valueOf(accountNo), accountBalance,accountTypes);
        profile_Accounts.add(account);
    }
    @Ignore
    public void addPSavingsGrp(int gsID, String groupName, String adminName, String purpose, double amount, Date startDate, Date endDate, String status) {
        profile_SavingsGroups = new ArrayList<>();
        String GSNo = "A" + (profile_SavingsGroups.size() + 1);
        SavingsGroup savingsGroup = new SavingsGroup(gsID,groupName,adminName, purpose, amount,startDate,endDate,status);
        profile_SavingsGroups.add(savingsGroup);
    }
    /*@Ignore
    public void addInvAcct(int comAcctID, double v, String zero_interest, String currentDate) {

    }*/

    @Ignore

    public void addPTellerReport(int dbaseID, int reportID, String date, double balance, String status) {
        ArrayList<TellerReport> tellerReports = null;
        reportID = ptellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(reportID, dbaseID,date,balance,status);
        ptellerReportArrayList.add(dailyReport);
    }
    @Ignore
    public void addPTellerReport(int keyExtraReportId, String officeBranch, double amountEntered, int noOfCustomers, String reportDate) {
        ArrayList<TellerReport> tellerReports = null;
        keyExtraReportId = ptellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(keyExtraReportId, officeBranch,amountEntered,noOfCustomers,reportDate);
        ptellerReportArrayList.add(dailyReport);
    }
    @Ignore
    public void addPPayment(String type, double totalToWithdraw, Date date, long paymentCode, String acctType, String office, String status) {
        ArrayList<Payment> paymentArrayList = null;
        if (paymentArrayList != null) {
            String paymentNo = "Payment:" + (paymentArrayList.size() + 1);
        }
        profile_Payment = new Payment(type,totalToWithdraw, date,paymentCode,acctType,office,status);
        paymentArrayList.add(profile_Payment);

    }
    @Ignore

    public void addPReport(int count, int customerID, String customerName, int packageID, int reportID, double savingsAmount, int numberOfDays, double totalAmountSum, int daysRemaining, double amountRemaining, String reportDate, String status) {
        count = profile_DailyReports.size() + 1;
        CustomerDailyReport dailyReport = new CustomerDailyReport( count,customerID,customerName,packageID, reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining,  reportDate, status);
        profile_DailyReports.add(dailyReport);
    }
    @Ignore
    public void addPGroupSavings(int GSNo, String groupName, double amount, Date savingsDate, String status) {
        profile_SavingsGroups = new ArrayList<>();
        GSNo = profile_GroupSavings.size() + 1;
        GroupSavings groupSavings1 = new GroupSavings(GSNo,groupName,amount, savingsDate, status);
        profile_GroupSavings.add(groupSavings1);
    }
    @Ignore
    public void addPCustomer(int uID, String surname, String firstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String dateJoined, String userName, String password) {
        profile_Customers = new ArrayList<>();
        String CusNo = "C" + (profile_Customers.size() + 1);
        Customer customer = new Customer(uID,surname, firstName, customerPhoneNumber,customerEmailAddress,customerAddress,customerGender,customerOffice,customerState,profilePicture,dateJoined,userName,password);
        profile_Customers.add(customer);

    }
    @Ignore
    public void addPCustomerManager(int uID, String surname, String firstName, String customerPhoneNumber, String dateOfBirth, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String dateJoined, String userName, String password) {
        profile_CustomerManagers = new ArrayList<>();
        String CMNo = "C" + (profile_CustomerManagers.size() + 1);
        CustomerManager customer = null;
        customer = new CustomerManager(uID,surname, firstName, customerPhoneNumber, dateOfBirth,customerEmailAddress,customerAddress,customerGender,customerOffice,customerState,profilePicture,dateJoined,userName,password);
        profile_CustomerManagers.add(customer);


    }
    @Ignore
    public void addPCustomerManager1(int uID, String surname, String firstName, String managerGender, String managerOffice) {
        profile_CustomerManagers = new ArrayList<>();
        String CMNo = "C" + (profile_CustomerManagers.size() + 1);
        CustomerManager customerManager = null;
        customerManager = new CustomerManager(uID,surname, firstName, managerGender,managerOffice);
        profile_CustomerManagers.add(customerManager);


    }

    @Ignore
    public void addPSavings(int profileId, int customerId, int newRecordID, double packageAmount, int numberOfDaysConverted, double totalForTheDay, int newNumberOfDaysRem, double newAmountRemaining, String reportDate, String status) {
        profile_DailyReports = new ArrayList<>();
        String savingsCount = "C" + (profile_DailyReports.size() + 1);
        CustomerDailyReport customerDailyReport = new CustomerDailyReport(profileId,customerId, newRecordID, packageAmount,numberOfDaysConverted,totalForTheDay,newNumberOfDaysRem,newAmountRemaining,reportDate,status);
        profile_DailyReports.add(customerDailyReport);
    }
    @Ignore
    public void addPLoans(int loanId, double amount, String loanStartDate, String status, String loanEndDate, double interest1) {
        profile_Loans = new ArrayList<>();
        int loanNumber = Integer.parseInt(("Loan" + (profile_Loans.size() + 1)));
        Loan loan = new Loan(loanNumber, amount,loanStartDate,status,loanEndDate,interest1);
        profile_Loans.add(loan);
    }
    @Ignore
    public void addPTimeLine(String tittle, String timelineDetails) {
        profile_TimeLines = new ArrayList<>();
        String history = "History" + (profile_TimeLines.size() + 1);
        TimeLine timeLine = new TimeLine(tittle,timelineDetails);
        profile_TimeLines.add(timeLine);
    }
    @Ignore
    public void setProfile_Accounts(ArrayList<Account> accounts) {
        this.profile_Accounts = accounts;
    }
    @Ignore
    public void addPTransaction(long transactionId, String surname, String firstName, String customerPhoneNumber, double amount, String accountNumber, String description, String date, String type) {
        profile_Transactions = new ArrayList<>();
        String transactionCount = "C" + (profile_Transactions.size() + 1);
        Transaction transaction1 = new Transaction(transactionId,surname, firstName, customerPhoneNumber,amount,accountNumber,description,date,type);
        profile_Transactions.add(transaction1);
    }
    @Ignore

    public void addPTransferTranX(Account sendingAcc, Account receivingAcc, double transferAmount) {

        sendingAcc.setAccountBalance(sendingAcc.getAccountBalance() - transferAmount);
        receivingAcc.setAccountBalance(receivingAcc.getAccountBalance() + transferAmount);

        int sendingAccTransferCount = 0;
        int receivingAccTransferCount = 0;
        for (int i = 0; i < sendingAcc.getTransactions1().size(); i ++) {
            if (sendingAcc.getTransactions1().get(i).getTranXType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                sendingAccTransferCount++;
            }
        }
        for (int i = 0; i < receivingAcc.getTransactions1().size(); i++) {
            if (receivingAcc.getTransactions1().get(i).getTranXType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
                receivingAccTransferCount++;
            }
        }

        //sendingAcc.getTransactions1().add(new Transaction(Integer.parseInt("T" + (sendingAcc.getTransactions1().size() + 1) + "-T" + (sendingAccTransferCount+1)), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
        //receivingAcc.getTransactions1().add(new Transaction(Integer.parseInt("T" + (receivingAcc.getTransactions1().size() + 1) + "-T" + (receivingAccTransferCount+1)), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
    }
    @Ignore

    public void addPPayee(String payeeName) {
        profile_Payees = new ArrayList<>();
        int payeeID = profile_Payees.size() + 1;
        Payee payee = new Payee(payeeID, payeeName);
        profile_Payees.add(payee);
    }
    @Ignore

    public void setProfile_Payees(ArrayList<Payee> payees) {
        this.profile_Payees = payees;
    }
    @Ignore
    public void setProfile_Customers(ArrayList<Customer> customers) {
        this.profile_Customers = customers;
    }
    @Ignore

    public void addPBorrowingTranx(Account receivingAccount, double amountToBorrow) {
        receivingAccount.setAccountBalance(receivingAccount.getAccountBalance() - amountToBorrow);

        int receivingAccBorrowingCount = 0;
        for (int i = 0; i < receivingAccount.getTransactions1().size(); i ++) {
            if (receivingAccount.getTransactions1().get(i).getTranXType() == Transaction.TRANSACTION_TYPE.BORROWING) {
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

    public void addPNewSkylightPackage(int profileID, int customerID, int packageID, String packageType, double savingsAmount, int packageDuration, String startDate, double grandTotal, String endDate, String just_stated) {
        profile_SkyLightPackages = new ArrayList<>();
        String packageNo = "Package:" + (profile_SkyLightPackages.size() + 1);
        profile_SkyLightPackage = new SkyLightPackage(profileID,customerID, packageID, SkyLightPackage.SkylightPackage_Type.valueOf(packageType),savingsAmount,packageDuration,startDate,grandTotal,endDate, profileStatus);
        profile_SkyLightPackages.add(profile_SkyLightPackage);
    }
    @Ignore


    public void addPStocksAll(int stocksID, String selectedStockPackage, String uStockType, String uStockModel, String uStockColor, String uStockSize, int uStockQuantity, String selectedOfficeBranch, double uStockPricePerUnit, String stockDate) {
        profile_Stocks = new ArrayList<>();
        int  stocksNo = profile_Stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,selectedStockPackage, uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);
        profile_Stocks.add(stocks1);

    }
    @Ignore
    public void addPStocks(int stocksID, String stocksName, String uStockType, int uStockQuantity, long stockCode, String selectedOfficeBranch, String stockDate, String status) {
        profile_Stocks = new ArrayList<>();
        int  stocksNo = profile_Stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,stocksName,uStockType,uStockQuantity,stockCode,stockDate,status);
        profile_Stocks.add(stocks1);

    }

    @Ignore
    public void addPTellerCash(int reportID, int packageID, String finalItemType, double tellerAmount, String tellerName, String officeBranch, String packageStartDate) {
        profile_TellerCashes = new ArrayList<>();
        int  stocksNo = profile_TellerCashes.size() + 1;
        TellerCash tellerCash = new TellerCash(reportID,packageID,finalItemType,tellerAmount,tellerName,officeBranch,packageStartDate);
        profile_TellerCashes.add(tellerCash);

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
        parcel.writeString(profile_Machine);
        parcel.writeParcelable(profile_Payment, i);
        parcel.writeString(profile_Identity);
        parcel.writeString(businessName);
        parcel.writeString(profile_Unit);
        parcel.writeString(profile_Ward);
        parcel.writeString(profile_Town);
        parcel.writeParcelable(profilePicture, i);
        parcel.writeParcelable(profile_OfficeBranch, i);
        parcel.writeString(profile_Nin);
        parcel.writeString(profileRole);
        parcel.writeParcelable(adminUser2, i);
        parcel.writeTypedList(profile_Accounts);
        parcel.writeTypedList(profile_Stocks);
        parcel.writeTypedList(profile_Addresses);
        parcel.writeTypedList(profile_Payees);
        parcel.writeTypedList(profile_TimeLines);
        parcel.writeTypedList(profile_Customers);
        parcel.writeTypedList(profile_Other_Businesses);
        parcel.writeTypedList(profile_DailyReports);
        parcel.writeTypedList(profile_SkyLightPackages);
        parcel.writeTypedList(profile_Loans);
        parcel.writeTypedList(profile_PaymentArrayList);
        parcel.writeTypedList(profile_PaymentCodeArrayList);
        parcel.writeTypedList(profile_PaymentDocArrayList);
        parcel.writeTypedList(profile_CustomerManagers);
        parcel.writeTypedList(adminUsers);
        parcel.writeTypedList(profile_AdminBankDeposits);
        parcel.writeTypedList(profile_StockTransferArrayList);
        parcel.writeParcelable(profile_Tranx, i);
        parcel.writeParcelable(profile_Loan, i);
        parcel.writeParcelable(profile_Birthday, i);
        parcel.writeParcelable(profile_Biz, i);
        parcel.writeParcelable(profile_GroupAcct, i);
        parcel.writeParcelable(profile_SavingsGroup, i);
        parcel.writeTypedList(profile_SavingsGroups);
        parcel.writeTypedList(profile_GroupAccounts);
        parcel.writeParcelable(profile_SOAcct, i);
        parcel.writeTypedList(profile_TellerCashes);
        parcel.writeTypedList(profile_StandingOrders);
        parcel.writeParcelable(profile_StandingOrder, i);
        parcel.writeParcelable(profile_CusDailyReport, i);
        parcel.writeParcelable(profile_SkyLightPackage, i);
        parcel.writeParcelable(profile_Payee, i);
        parcel.writeParcelable(profile_AdminUser, i);
        parcel.writeParcelable(profile_CustomerManager, i);
        parcel.writeParcelable(profile_SuperAdmin, i);
        parcel.writeParcelable(profile_Customer, i);
        parcel.writeParcelable(profile_Account, i);
        parcel.writeTypedList(profile_Transactions);
        parcel.writeTypedList(profile_TimeLineArrayList);
        parcel.writeTypedList(profile_LatLngs);
        parcel.writeParcelable(profile_LastLocation, i);
        parcel.writeLong(pID);
        parcel.writeString(profileStatus);
        parcel.writeTypedList(profile_GroupSavings);
        parcel.writeString(profile_AccountBalance);
        parcel.writeInt(profileNo);
        parcel.writeTypedList(ptellerReportArrayList);
    }

    @Ignore
    public void addPTranxGranding(int tranxPayoutID, int customerID, String customerName, double amountRequested, String acctBank, String bankName, String acctBankNo, String s, String requestDate) {
        payoutNo = profile_TranxGrantings.size() + 1;
        TransactionGranting transactionGranting = new TransactionGranting(tranxPayoutID,customerID,customerName,amountRequested,acctBank,bankName,acctBankNo,s,requestDate);
        profile_TranxGrantings.add(transactionGranting);
    }
    @Ignore

    public int getProfPassID() {
        return profPassID;
    }

    public void setProfPassID(int profPassID) {
        this.profPassID = profPassID;
    }

    public int getProfPixID() {
        return profPixID;
    }

    public void setProfPixID(int profPixID) {
        this.profPixID = profPixID;
    }

    public int getProfileBusinessID() {
        return profileBusinessID;
    }

    public void setProfileBusinessID(int profileBusinessID) {
        this.profileBusinessID = profileBusinessID;
    }

    public int getProfileMarketID() {
        return profileMarketID;
    }

    public void setProfileMarketID(int profileMarketID) {
        this.profileMarketID = profileMarketID;
    }

    public QBUser getProfQbUser() {
        return profQbUser;
    }

    public void setProfQbUser(QBUser profQbUser) {
        this.profQbUser = profQbUser;
    }

    public QBEntity getProfQbEntity() {
        return profQbEntity;
    }

    public void setProfQbEntity(QBEntity profQbEntity) {
        this.profQbEntity = profQbEntity;
    }
}
