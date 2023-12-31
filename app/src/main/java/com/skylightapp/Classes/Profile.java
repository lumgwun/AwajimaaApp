package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.location.Address;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;
import android.telephony.SmsMessage;



import com.google.android.gms.maps.model.LatLng;
import com.quickblox.core.model.QBEntity;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Bookings.Trip;
import com.skylightapp.Bookings.Driver;
import com.skylightapp.Bookings.TripBooking;
import com.skylightapp.Bookings.TripData;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.Stocks;

import com.skylightapp.MapAndLoc.EmergReportNext;
import com.skylightapp.MapAndLoc.EmergResponse;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.Bookings.TaxiDriver;
import com.skylightapp.MapAndLoc.Fence;
import com.skylightapp.MapAndLoc.FenceEvent;
import com.skylightapp.MarketClasses.BusinessOthers;
import com.skylightapp.MarketClasses.InsuranceCompany;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketAdmin;
import com.skylightapp.MarketClasses.MarketBizPackage;
import com.skylightapp.MarketClasses.MarketBusiness;
import com.skylightapp.SuperAdmin.Awajima;
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
import static com.skylightapp.Database.UserContentProvider.AUTHORITY;
import static com.skylightapp.Database.UserContentProvider.BASE_CONTENT_URI;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;

//@Entity(tableName = "RoomProfileTable")
//@Entity
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class Profile implements Parcelable, Serializable, BaseColumns {
    public static final String PROFILE_SURNAME = "P_surname";
    public static final String PROFILE_FIRSTNAME = "P_first_name";
    public static final String PROFILE_EMAIL = "P_email";
    public static final String PROFILE_DOB = "P_dob";
    public static final String PROFILE_ADDRESS = "P_street";
    public static final String PROFILE_GENDER = "P_gender";
    public static final String PROFILE_PHONE = "p_phone";
    public static final String PROFILE_ROLE = "p_role";
    public static final String PROFILE_DATE_JOINED = "p_join_date";
    public static final String PROFILE_NEXT_OF_KIN = "p_next_of_kin";
    public static final String PROFILE_STATUS = "p_status";
    public static final String PROFILE_PASSWORD = "p_passCode";
    public static final String PROFILES_TABLE = "profiles_table";
    public static final String PROFILE_UNIT = "p_unit";
    public static final String PROFILE_WARD = "p_ward";
    public static final String PROFILE_TOWN = "p_town";
    public static final String PROFILE_COUNTRY = "p_country";
    public static final String PROFILE_SPONSOR_ID = "p_sponsor";
    public static final String PROFILE_ID = "profile_id";
    public static final String PICTURE_TABLE = "pictureTable";
    public static final String PICTURE_URI = "picture_uri";
    public static final String PROFILE_USERNAME = "p_username";
    public static final String PASSWORD = "p_password";
    public static final String PASSWORD_ID = "password_Id";
    public static final String PASSWORD_TABLE = "password_table";
    public static final String PROFILE_NIN = "profile_NIN";
    public static final String PROFILE_STATE = "p_state";
    public static final String PROFILE_OFFICE = "p_office";
    public static final String PROFILE_PIC_ID = "picture_id";
    public static final String PROFILE_CUS_ID_KEY = "customer_ID_Foreign_key";
    public static final String CUS_ID_PIX_KEY = "cus_ID_Pix_key";
    public static final String CUS_ID_PASS_KEY = "cus_ID_Pass_key";
    public static final String PROF_SPONSOR_ID = "prof_SponsorID";
    public static final String PROFID_FOREIGN_KEY_PIX = "profID_For_keyP";
    public static final String PROF_SPONSOR_KEY = "prof_Sponsor_Key";
    public static final String PROF_REF_LINK = "prof_Ref_Link";
    public static final String SPONSOR_TABLE = "sponsor_Table";
    public static final String SPONSOR_TABLE_ID = "sponsor_TableID";
    public static final String SPONSOR_TABLE_CUS_ID = "sponsor_TableCus_ID";
    public static final String SPONSOR_TABLE_PROF_ID = "sponsor_Table_Prof_ID";
    public static final String SPONSOR_TABLE_PHONE = "sponsor_Table_Phone";
    public static final String SPONSOR_TABLE_EMAIL = "sponsor_Table_Email";
    public static final String PICTURE_MARKET_ID = "pic_market_ID";
    public static final String SPONSOR_REFERER = "s_ReferrerZ";
    public static final String SPONSOR_REFERRER_CODE = "sponsor_Ref_Code";
    public static final String SPONSOR_REFERRER_USER = "sponsor_Ref_User";
    public static final String SPONSOR_REF_COUNT = "sponsor_Ref_Count";
    public static final String SPONSOR_REF_REWARD_COUNT21 = "sponsor_Ref_Reward_Count";
    public static final String PROF_ID_FOREIGN_KEY_PASSWORD = "prof_ID_FkeyPassW";
    public static final String PROF_BUSINESS_ID = "prof_BizID";
    public static final String PROF_MARKET_ID = "profmarket_ID8234";
    public static final String PROF_ADMIN_TYPE = "prof_Admin_Type";
    public static final String PROF_ROLE_TYPE = "prof_Role_Type";
    public static final String PROF_DB_ID = "prof_DB_ID";
    public static final String USER_TYPE_TABLE = "user_type_tableP";
    public static final String USER_TYPE_ID = "user_type_ID";
    public static final String USER_TYPE_PROF_ID = "user_type_Prof_ID";
    public static final String USER_TYPE_CUS_ID = "user_type_Cus_ID";
    public static final String USER_TYPE_STRING = "user_type_SS";
    public static final String USER_TYPE_BIZ_ID = "user_type_Biz_ID";
    public static final String USER_TYPE_MARKET_ID = "user_type_MID";
    public static final String USER_TYPE_STATUS = "user_type_Status";

    public static final String NIN_TABLE = "nIN_Table";
    public static final String NIN_ID = "nIN_id";
    public static final String NIN_PIX = "nIN_Pix";
    public static final String NIN_NUMBER = "nIN_Number";
    public static final String NIN_TYPE = "nIN_TYPE";
    public static final String NIN_EXPIRYD = "nIN_Expiry_Date";
    public static final String NIN_PROF_ID = "nIN_ProfID";
    public static final String NIN_CUS_ID = "nIN_Cus_ID";
    public static final String NIN_STATUS = "nIN_STATUS";
    public static final String NIN_APPROVER = "nIN_Approver";
    public static final String NIN_APPROVING_OFFICE = "nIN_Approving_O";
    public static final String NIN_QBID = "nIN_QB_ID";


    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PROFILES_TABLE);

    public static final String CONTENT_LIST_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PROFILES_TABLE;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PROFILES_TABLE;

    public static final String CREATE_NIN_TABLE = "CREATE TABLE IF NOT EXISTS " + NIN_TABLE + " (" + NIN_ID + " INTEGER, " + NIN_PROF_ID + " INTEGER , " +
            NIN_NUMBER + " TEXT , " + NIN_PIX + " BLOB , " + NIN_TYPE + " TEXT, " + NIN_CUS_ID + " INTEGER, " +
            NIN_EXPIRYD + " TEXT, " + NIN_APPROVER + " INTEGER, " + NIN_APPROVING_OFFICE + " REAL, " + NIN_APPROVING_OFFICE + " TEXT, " +
            NIN_QBID  + " INTEGER, "+ NIN_STATUS + " TEXT, "  +"PRIMARY KEY(" +NIN_ID + "), " +"FOREIGN KEY(" + NIN_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + NIN_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    public static final String CREATE_PIXTURE_TABLE = "CREATE TABLE " + PICTURE_TABLE + " (" + PROFILE_PIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFID_FOREIGN_KEY_PIX + " INTEGER, " + CUS_ID_PIX_KEY + " INTEGER , " +
            PICTURE_URI + " BLOB ,"+ PICTURE_MARKET_ID + " TEXT, "+"FOREIGN KEY(" + PICTURE_MARKET_ID  + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + "),"+"FOREIGN KEY(" + PROFID_FOREIGN_KEY_PIX  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +"FOREIGN KEY(" + CUS_ID_PIX_KEY + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";
    //@Ignore
    public static final String CREATE_PASSWORD_TABLE = "CREATE TABLE IF NOT EXISTS " + PASSWORD_TABLE + " (" + PASSWORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROF_ID_FOREIGN_KEY_PASSWORD + " INTEGER, "+
            PASSWORD + " TEXT , " + CUS_ID_PASS_KEY + " INTEGER , " +"FOREIGN KEY(" + PROF_ID_FOREIGN_KEY_PASSWORD  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," +
            "FOREIGN KEY(" + CUS_ID_PASS_KEY + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    public static final String CREATE_PROFILES_TABLE = "CREATE TABLE " + PROFILES_TABLE + " (" + PROFILE_ID + " INTEGER , " + PROFILE_SURNAME + " TEXT, " + PROFILE_FIRSTNAME + " TEXT, " + PROFILE_PHONE + " TEXT, " + PROFILE_EMAIL + " TEXT, " + PROFILE_DOB + " TEXT, " + PROFILE_GENDER + " TEXT, " +
            PROFILE_ADDRESS + " TEXT, " + PROFILE_NIN + " TEXT, " + PROFILE_UNIT + " TEXT, " + PROFILE_WARD + " TEXT, " + PROFILE_TOWN + " TEXT, " + PROFILE_STATE + " TEXT, " + PROFILE_COUNTRY + " TEXT, " + PROFILE_OFFICE + " TEXT, " + PROFILE_DATE_JOINED + " TEXT, " + PROFILE_ROLE + " TEXT, " + PROFILE_USERNAME + " TEXT, " + PROFILE_PASSWORD + " TEXT, " + PROFILE_STATUS + " TEXT, " + PROFILE_NEXT_OF_KIN + " TEXT,"+ PROFILE_SPONSOR_ID + " TEXT,"+ PROFILE_CUS_ID_KEY + " INTEGER,"+ PROF_BUSINESS_ID + " TEXT,"+ PROF_MARKET_ID + " TEXT,"+ PROF_REF_LINK + " REAL," + PROF_ADMIN_TYPE + " TEXT, " + PROF_ROLE_TYPE + " TEXT, "+ PROF_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ "FOREIGN KEY(" + PROF_BUSINESS_ID + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "),"+ "FOREIGN KEY(" + PROF_MARKET_ID + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + "),"+ "FOREIGN KEY(" + PROFILE_CUS_ID_KEY + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";

    public static final String CREATE_SPONSOR_TABLE = "CREATE TABLE " + SPONSOR_TABLE + " (" + SPONSOR_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SPONSOR_TABLE_CUS_ID + " INTEGER, " + SPONSOR_TABLE_PROF_ID + " INTEGER, " + SPONSOR_TABLE_PHONE + " TEXT, " + SPONSOR_TABLE_EMAIL + " TEXT, "+ SPONSOR_REFERER + " TEXT, "+ SPONSOR_REFERRER_CODE + " TEXT, "+ SPONSOR_REFERRER_USER + " TEXT, "+ SPONSOR_REF_COUNT + " INTEGER, "+  SPONSOR_REF_REWARD_COUNT21 + " INTEGER, " + "FOREIGN KEY(" + SPONSOR_TABLE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";
    public static final String CREATE_USER_TYPE_TABLE = "CREATE TABLE " + USER_TYPE_TABLE + " (" + USER_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USER_TYPE_PROF_ID + " INTEGER, " + USER_TYPE_CUS_ID + " INTEGER, " + USER_TYPE_STRING + " TEXT, " + USER_TYPE_BIZ_ID + " INTEGER, "+ USER_TYPE_MARKET_ID + " INTEGER, "+ USER_TYPE_STATUS + " TEXT, "+ "FOREIGN KEY(" + USER_TYPE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+ "FOREIGN KEY(" + USER_TYPE_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "FOREIGN KEY(" + USER_TYPE_BIZ_ID + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "),"+ "FOREIGN KEY(" + USER_TYPE_MARKET_ID + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + "))";

    private Profile profile;
    private Payment profile_Payment;
    private String profile_Identity, businessName, profile_Unit, profile_Ward, profile_Town;
    //@PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = PROFILE_ID)
    private int pID ;
    //@ColumnInfo(name = PROFILE_SURNAME)
    private String profileLastName;
    //@ColumnInfo(name = PROFILE_FIRSTNAME)
    private String profileFirstName;
    //@ColumnInfo(name = PROFILE_PHONE)
    private String profilePhoneNumber;
    //@ColumnInfo(name = PROFILE_EMAIL)
    private String profileEmail;
    //@ColumnInfo(name = PROFILE_DOB)
    private String profileDob;
    //@ColumnInfo(name = PROFILE_GENDER)
    private String profileGender;
    //@ColumnInfo(name = PROFILE_ADDRESS)
    protected String profileAddress;
    //@ColumnInfo(name = PROFILE_NIN)
    //@Ignore
    private String profile_Nin;
    private OfficeBranch profile_OfficeBranch;
    //@ColumnInfo(name = PROFILE_OFFICE)
    private String profOfficeName;
    //@ColumnInfo(name = PROFILE_DATE_JOINED)
    private String profileDateJoined;
    //@ColumnInfo(name = PROFILE_STATE)
    private String profileState;
    //@ColumnInfo(name = PROFILE_ROLE)
    private String profileRole;
    //@ColumnInfo(name = PROFILE_NEXT_OF_KIN)
    private String nextOfKin;
    //@ColumnInfo(name = PROFILE_USERNAME)
    private String profileUserName;
    //@ColumnInfo(name = PASSWORD)
    private String profilePassword;
    //@ColumnInfo(name = PROFILE_SPONSOR_ID)
    private int profileSponsorID;
    //@ColumnInfo(name = PROFILE_CUS_ID_KEY)
    private int profCusID;
    //@ColumnInfo(name = PROFID_FOREIGN_KEY_PIX)
    private int profPixID;
    //@ColumnInfo(name = PROF_ID_FOREIGN_KEY_PASSWORD)
    private int profPassID;
    //@ColumnInfo(name = PROFILE_STATUS)
    private String profileStatus;
    private String pin;
    protected User.User_Type type;
    private String profile_Machine;
    //@TypeConverters
    //@ColumnInfo(name = PICTURE_URI)
    private Uri profilePicture;

    private Transaction profile_Tranx;

    private Loan profile_Loan;

    private Birthday profile_Birthday;

    private MarketBusiness profile_Biz;

    private GroupAccount profile_GroupAcct;

    private String profile_AuthenticationKey;

    Context context;

    protected transient boolean profile_Authenticated = false;

    //protected RolesEnumMap enumMap = new RolesEnumMap(context);

    private Awajima profile_Awajima;

    private ProjectSavingsGroup profile_Project_SavingsGroup;

    StandingOrderAcct profile_SOAcct;

    private StandingOrder profile_StandingOrder;

    private CustomerDailyReport profile_CusDailyReport;

    private MarketBizPackage profile_MarketBizPackage;

    private Payee profile_Payee;

    private AdminUser profile_AdminUser;
    private CustomerManager profile_CustomerManager;

    private UserSuperAdmin profile_SuperAdmin;

    SmsMessage.MessageClass messageClass;

    private Customer profile_Customer;

    Account profile_Account;

    private  LatLng profile_LastLocation;

    DBHelper dbHelper;
    String profile_AccountBalance;

    private int payoutNo;

    int profileNo;

    private int profileBusinessID;
    private int profileMarketID;
    private int profOfficeID;
    private QBUser profQbUser;
    private QBEntity profQbEntity;
    private AdminUser.ADMIN_TYPE profileAdmin_type;
    private String profileAdminType;
    private String profileRoleType;
    private TaxiDriver profileTaxiDriver;
    private Driver profDriver;
    private ArrayList<Integer> profile_acctIDs;
    private ArrayList<Integer> profMarketIDs;
    private ArrayList<Long> profBusinessIDs;

    private ArrayList<BusinessOthers> profBizOthers;
    private ArrayList<MarketBusiness> profMarketBizs;
    private ArrayList<MarketBusiness> marketBusinessArrayList;
    private ArrayList<Market> profMarketArrayList;
    private ArrayList<MarketAdmin> profMarketAdmins;
    private ArrayList<InsuranceCompany> insuranceCompanies;
    private ArrayList<EmergencyReport> profileEmergReports;
    private ArrayList<EmergReportNext> profileEmergRNextS;
    private ArrayList<EmergResponse> profileEmergResponses;
    private ArrayList<String> profUserTypes;
    private ArrayList<Trip> profileTrips;
    private ArrayList<TripBooking> profileTripBookings;
    private ArrayList<Account> profile_Accounts;

    private ArrayList<Stocks> profile_Stocks;

    private ArrayList<Address> profile_Addresses;

    private ArrayList<Payee> profile_Payees;

    private ArrayList<TimeLine> profile_TimeLines;

    private ArrayList<Customer> profile_Customers;

    private ArrayList<CustomerDailyReport> profile_DailyReports;

    private ArrayList<MarketBizPackage> profile_MarketBizPackages;

    private ArrayList<Loan> profile_Loans;

    private ArrayList<Payment> profile_PaymentArrayList;

    private ArrayList<PaymentCode> profile_PaymentCodeArrayList;

    private ArrayList<PaymentDoc> profile_PaymentDocArrayList;


    private ArrayList<CustomerManager> profile_CustomerManagers;
    private ArrayList<AdminUser> adminUsers;
    private ArrayList<AdminBankDeposit> profile_AdminBankDeposits;
    //@Ignore
    private ArrayList<StockTransfer> profile_StockTransferArrayList;
    //@Ignore
    private ArrayList<TellerCash> profile_TellerCashes;
    //@Ignore
    private ArrayList<StandingOrder> profile_StandingOrders;
    //@Ignore
    private ArrayList<ProjectSavingsGroup> profile_Project_SavingsGroups;
    //@Ignore
    private ArrayList<GroupAccount> profile_GroupAccounts;
    //@Ignore
    private ArrayList<Transaction> profile_Transactions;
    //@Ignore
    private ArrayList<TimeLine> profile_TimeLineArrayList;
    //@Ignore
    private ArrayList<LatLng> profile_LatLngs;
    //@Ignore
    ArrayList<TellerReport> ptellerReportArrayList;
    //@Ignore
    ArrayList<TransactionGranting> profile_TranxGrantings;
    //@Ignore
    private ArrayList<Message> messageArrayList;
    //@Ignore
    private ArrayList<GroupSavings> prof_GrpSavings;
    private ArrayList<TripBooking> profTripBookings;
    private int profNIN_ID;
    private String profNin_Number;
    private String profNinType;
    private String profNinEXPD;
    private Uri profNinPix;
    private String profNinApprover;
    private String profNin_A_Office;
    private String profNin_Status;
    private ArrayList<Fence> profFenceArrayList;
    private ArrayList<FenceEvent> profFenceEvents;
    private TripData.Builder builder;


    public void setProfilePassword(String profilePassword)   {
        this.profilePassword = profilePassword;
    }

    public String getProfilePassword() { return profilePassword; }
    public String getProfileFirstName() { return profileFirstName;
    }


    /*@ColumnInfo(name = PROFILE_DOB)
    private String dob1;*/



    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Profile))
            return false;
        Profile profile = (Profile) o;
        return Objects.equals(this.pID, profile.pID) && Objects.equals(this.profileFirstName, profile.profileFirstName)
                && Objects.equals(this.profileRole, profile.profileRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.pID, this.profileFirstName, this.profileRole);
    }
    public TripData.Builder registerPush() {
        return builder;
    }




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
    public String getProfOfficeName() {
        return profOfficeName;
    }
    //@Ignore
    public void setProfileMachine(String machine) { this.profile_Machine = machine;
    }
    //@Ignore
    public String getProfileMachine() {
        return profile_Machine;
    }

    public String getProfilePhoneNumber() { return profilePhoneNumber; }
    public void setProfOfficeName(String profOfficeName) { this.profOfficeName = profOfficeName; }
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

    //@Ignore
    public ArrayList<Address> getProfileAddresses() { return profile_Addresses; }
    //@Ignore








    //@Ignore
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
        profOfficeName = in.readString();
        profilePhoneNumber = in.readString();
        profileDateJoined = in.readString();
        profile_Machine = in.readString();
        profile_Payment = in.readParcelable(Payment.class.getClassLoader());
        profile_PaymentArrayList = in.createTypedArrayList(Payment.CREATOR);


        profile_Transactions = in.createTypedArrayList(Transaction.CREATOR);
        profile_Tranx = in.readParcelable(Transaction.class.getClassLoader());


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
        profile_MarketBizPackages = in.createTypedArrayList(MarketBizPackage.CREATOR);
        //loans = in.createTypedArrayList(Loan.CREATOR);

        profile_PaymentCodeArrayList = in.createTypedArrayList(PaymentCode.CREATOR);
        profile_PaymentDocArrayList = in.createTypedArrayList(PaymentDoc.CREATOR);
        adminUsers = in.createTypedArrayList(AdminUser.CREATOR);
        profile_AdminBankDeposits = in.createTypedArrayList(AdminBankDeposit.CREATOR);
        profile_StockTransferArrayList = in.createTypedArrayList(StockTransfer.CREATOR);

        profile_Loan = in.readParcelable(Loan.class.getClassLoader());
        profile_Birthday = in.readParcelable(Birthday.class.getClassLoader());
        profile_Biz = in.readParcelable(MarketBusiness.class.getClassLoader());
        profile_GroupAcct = in.readParcelable(GroupAccount.class.getClassLoader());
        profile_Project_SavingsGroup = in.readParcelable(ProjectSavingsGroup.class.getClassLoader());
        profile_Project_SavingsGroups = in.createTypedArrayList(ProjectSavingsGroup.CREATOR);
        profile_GroupAccounts = in.createTypedArrayList(GroupAccount.CREATOR);
        profile_SOAcct = in.readParcelable(StandingOrderAcct.class.getClassLoader());
        profile_TellerCashes = in.createTypedArrayList(TellerCash.CREATOR);
        //standingOrders = in.createTypedArrayList(StandingOrder.CREATOR);
        profile_StandingOrder = in.readParcelable(StandingOrder.class.getClassLoader());
        profile_CusDailyReport = in.readParcelable(CustomerDailyReport.class.getClassLoader());
        profile_MarketBizPackage = in.readParcelable(MarketBizPackage.class.getClassLoader());
        profile_Payee = in.readParcelable(Payee.class.getClassLoader());
        profile_AdminUser = in.readParcelable(AdminUser.class.getClassLoader());
        profile_CustomerManager = in.readParcelable(CustomerManager.class.getClassLoader());
        profile_SuperAdmin = in.readParcelable(UserSuperAdmin.class.getClassLoader());
        profile_Customer = in.readParcelable(Customer.class.getClassLoader());
        profile_Account = in.readParcelable(Account.class.getClassLoader());



        //timeLineArrayList = in.createTypedArrayList(TimeLine.CREATOR);
        profile_LatLngs = in.createTypedArrayList(LatLng.CREATOR);
        profile_LastLocation = in.readParcelable(LatLng.class.getClassLoader());
        pID = in.readInt();
        profileStatus = in.readString();
        prof_GrpSavings = in.createTypedArrayList(GroupSavings.CREATOR);
        profile_AccountBalance = in.readString();
        profileNo = in.readInt();
        ptellerReportArrayList = in.createTypedArrayList(TellerReport.CREATOR);
    }


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
        parcel.writeString(profOfficeName);
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
        parcel.writeTypedList(profile_Accounts);
        parcel.writeTypedList(profile_Stocks);
        parcel.writeTypedList(profile_Addresses);
        parcel.writeTypedList(profile_Payees);
        parcel.writeTypedList(profile_TimeLines);
        parcel.writeTypedList(profile_Customers);
        parcel.writeTypedList(marketBusinessArrayList);
        parcel.writeTypedList(profile_DailyReports);
        parcel.writeTypedList(profile_MarketBizPackages);
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
        parcel.writeParcelable(profile_Project_SavingsGroup, i);
        parcel.writeTypedList(profile_Project_SavingsGroups);
        parcel.writeTypedList(profile_GroupAccounts);
        parcel.writeParcelable(profile_SOAcct, i);
        parcel.writeTypedList(profile_TellerCashes);
        parcel.writeTypedList(profile_StandingOrders);
        parcel.writeParcelable(profile_StandingOrder, i);
        parcel.writeParcelable(profile_CusDailyReport, i);
        parcel.writeParcelable(profile_MarketBizPackage, i);
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
        parcel.writeTypedList(prof_GrpSavings);
        parcel.writeString(profile_AccountBalance);
        parcel.writeInt(profileNo);
        parcel.writeTypedList(ptellerReportArrayList);
    }



    public Profile(int profile_id, String surName, String profileFirstName, String phone, String profileEmail, String profileDob, String profileGender, String profileAddress, String s, String profileState, String profile_OfficeBranch, String joinedDate, String profileRole, String userName, String profilePassword, String profileStatus) {
        super();
        this.pID = profile_id;
        this.profileLastName = surName;
        this.profilePhoneNumber = phone;
        this.profileEmail = profileEmail;
        this.profile_Identity = profile_Identity;
        this.profileUserName = userName;
    }
    public Profile(String uSurname, String uFirstName, String uPhoneNumber, String uEmail, String dateOfBirth, String selectedGender, String uAddress, String selectedState, String selectedOffice, String dateJoined, String uUserName, String selectedUserType, String status, Uri image, AdminUser.ADMIN_TYPE profileAdmin_type) {
        this.profileFirstName = uFirstName;
        this.profileLastName = uSurname;
        this.profilePhoneNumber = uPhoneNumber;
        this.profileEmail = uEmail;
        this.profileDob = dateOfBirth;
        this.profileGender = selectedGender;
        this.profileAddress = uAddress;
        this.profileState = selectedState;
        this.profOfficeName = selectedOffice;
        this.profileDateJoined = dateJoined;
        this.profileUserName = uUserName;
        this.profileRole = selectedUserType;
        this.profileStatus = status;
        this.profilePicture = image;
        this.profileAdmin_type = profileAdmin_type;



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
        this.profOfficeName = selectedOffice;
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


    //@Ignore
    public void setProfilePayments(ArrayList<Payment> paymentArrayList) {
        this.profile_PaymentArrayList = paymentArrayList;
    }
    //@Ignore
    public ArrayList<Payment> getProfilePayments() { return profile_PaymentArrayList;
    }
    public void setProfilePaymentCodes(ArrayList<PaymentCode> paymentCodeArrayList) {
        this.profile_PaymentCodeArrayList = paymentCodeArrayList;
    }
    //@Ignore
    public ArrayList<PaymentCode> getProfilePaymentCodes()
    { return profile_PaymentCodeArrayList;
    }
    //@Ignore
    public void setUserPaymentDocuments(ArrayList<PaymentDoc> paymentDocArrayList) {
        this.profile_PaymentDocArrayList = paymentDocArrayList;
    }

    public ArrayList<PaymentDoc> getProfilePaymentDocuments() { return profile_PaymentDocArrayList; }

    //@Ignore


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

    public String getProfileIdentity() {
        return profile_Identity;
    }

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

    public String getProfileUnit() {
        return profile_Unit;
    }

    public void setProfileUnit(String unit) { this.profile_Unit = unit;
    }

    public String getProfileWard() {
        return profile_Ward;
    }

    public void setProfileWard(String ward) { this.profile_Ward = ward;
    }

    public String getProfileTown() {
        return profile_Town;
    }

    public void setProfileTown(String town) { this.profile_Town = town; }

    public StandingOrderAcct getProfileSOAcct() { return profile_SOAcct; }

    public void setProfileSOAcct(StandingOrderAcct standingOrderAcct) { this.profile_SOAcct = standingOrderAcct; }
    @SuppressLint("SimpleDateFormat")

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Awajima getProfileAwajima() { return profile_Awajima;
    }

    public void setProfileSkylight(Awajima awajima) { this.profile_Awajima = awajima;
    }



    public String getProfile_AuthenticationKey() {
        return profile_AuthenticationKey;
    }

    public void setProfile_AuthenticationKey(String profile_AuthenticationKey) {
        this.profile_AuthenticationKey = profile_AuthenticationKey;
    }




    public LatLng getProfileLastKnownLocation() {
        return profile_LastLocation;
    }

    public LatLng setProfileLastKnownLocation(LatLng lastLocation) {
        this.profile_LastLocation = lastLocation;

        return lastLocation;
    }

    public boolean getProfile_Authenticated() {
        return false;
    }
    public Birthday getProfileBirthday() { return profile_Birthday; }
    public void setProfileBirthday(Birthday birthday) { this.profile_Birthday = birthday; }

    public GroupAccount getGroupAccount(int grpAcctID) { return profile_GroupAcct; }
    public void setProfile_GroupAcct(GroupAccount profile_GroupAcct) { this.profile_GroupAcct = profile_GroupAcct; }

    public ProjectSavingsGroup getProject_SavingsGroup() { return profile_Project_SavingsGroup; }

    public void setProject_SavingsGroup(ProjectSavingsGroup profile_Project_SavingsGroup) { this.profile_Project_SavingsGroup = profile_Project_SavingsGroup; }

    public boolean profileAuthenticate(String password, String phoneNumber) {

        return false;
    }

    public String getProfileBusinessName() {
        if(profile_Biz !=null){
            businessName= profile_Biz.getBusinessName();
        }
        return businessName;
    }




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
        this.profOfficeName = selectedOffice;
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
    public Profile(String profileFirstName, String profileLastName, String profilePhoneNumber, String profileEmail, String profileDob, String profileGender, String profileAddress, String username, String profOfficeName, String joinedDate, int id) {
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
        this.profOfficeName = profOfficeName;
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
        this.profOfficeName = spinnerOffice;
        this.profileUserName = userName;
        this.profilePassword = profilePassword;
        this.profileRole = profileRole;
        this.profileStatus = profileStatus;
        this.profilePicture = Uri.parse(String.valueOf(parse));
    }

    public StandingOrder getProfileStandingOrder() { return profile_StandingOrder; }

    public void setProfileStandingOrder(StandingOrder standingOrder) {
        this.profile_StandingOrder = standingOrder;

    }

    public MarketBusiness getProfileBusiness() { return profile_Biz; }

    public void setProfileBusiness(MarketBusiness marketBusiness) {
        this.profile_Biz = marketBusiness;

    }
    //@Ignore

   /* public ArrayList<ProjectSavingsGroup> getProfile_SavingsGroups() { return profile_Project_SavingsGroups; }
    @Ignore
    public void setProfile_SavingsGroups(ArrayList<ProjectSavingsGroup> profile_Project_SavingsGroups) {
        this.profile_Project_SavingsGroups = profile_Project_SavingsGroups;

    }*/

    public ArrayList<GroupSavings> getProf_GrpSavings() { return prof_GrpSavings;
    }

    public void setProf_GrpSavings(ArrayList<GroupSavings> prof_GrpSavings) {
        this.prof_GrpSavings = prof_GrpSavings;

    }
    public ArrayList<CustomerManager> getProfile_CustomerManagers() { return profile_CustomerManagers;
    }

    public void setProfile_CustomerManagers(ArrayList<CustomerManager> profile_CustomerManagers) {
        this.profile_CustomerManagers = profile_CustomerManagers;

    }

    //@Ignore
    public ArrayList<GroupAccount> getProfile_GroupAccounts() { return profile_GroupAccounts; }
    //@Ignore
    public void setProfile_GroupAccounts(ArrayList<GroupAccount> profile_GroupAccounts) {
        this.profile_GroupAccounts = profile_GroupAccounts;

    }

    public ArrayList<StandingOrder> getProfile_StandingOrders() { return profile_StandingOrders; }
    public void setProfile_StandingOrders(ArrayList<StandingOrder> profile_StandingOrders) {
        this.profile_StandingOrders = profile_StandingOrders;

    }


    public ArrayList<Account> getProfileAccounts() { return profile_Accounts;
    }

    public ArrayList<Customer> getProfileCustomers() { return profile_Customers;
    }

    public ArrayList<MarketBusiness> getProfile_Businesses() { return marketBusinessArrayList; }

    public void setProfile_Businesses(ArrayList<MarketBusiness> profile_Businesses) { this.marketBusinessArrayList = profile_Businesses; }

    public ArrayList<MarketBizPackage> getProfileSkylightPackages() { return profile_MarketBizPackages; }

    public ArrayList<CustomerDailyReport> getProfileDailyReports() { return profile_DailyReports; }


    public void setProfileLoans(ArrayList<Loan> loans) {
        this.profile_Loans = loans;

    }

    public ArrayList<Loan> getProfileLoans() { return profile_Loans; }

    public Loan getProfileLoan() { return profile_Loan; }

    public void setProfileLoan(Loan loan) { this.profile_Loan = loan; }

    public OfficeBranch getProfileOfficeBranch() {
        return profile_OfficeBranch;
    }

    public void setProfileOfficeBranch(OfficeBranch officeBranch) {
        this.profile_OfficeBranch = officeBranch;
    }


    public Payee getProfilePayee() { return profile_Payee; }

    public void setProfilePayee(Payee payee) { this.profile_Payee = payee; }


    public SmsMessage.MessageClass getMessageClass() { return messageClass; }

    public void setMessageClass(SmsMessage.MessageClass messageClass) { this.messageClass = messageClass; }

    public MarketBizPackage getProfileSkyLightPackage() { return profile_MarketBizPackage; }

    public void setProfileSkyLightPackage(MarketBizPackage marketBizPackage) { this.profile_MarketBizPackage = marketBizPackage; }

    public Account getProfileAccount() { return profile_Account;
    }

    public void setProfileAccount(Account account) { this.profile_Account = account;
    }

    public CustomerDailyReport getProfileCustomerDailyReport() { return profile_CusDailyReport;
    }

    public void setProfile_CusDailyReport(CustomerDailyReport profile_CusDailyReport) { this.profile_CusDailyReport = profile_CusDailyReport; }

    public AdminUser getProfileAdminUser() { return profile_AdminUser;
    }

    public void setPin(String pin) {
        this.pin = pin;

    }

    public String getProfileGender() {
        return profileGender;
    }
    public String getProfileDob() {
        return profileDob;
    }

    public User.User_Type getProfileType() {
        return type;
    }

    public Uri getProfilePicture() {
        return profilePicture;
    }

    public String getProfilePin() {
        return pin;
    }


    public void setProfile_AdminUser(AdminUser profile_AdminUser) { this.profile_AdminUser = profile_AdminUser;
    }


    public CustomerManager getProfile_CustomerManager() { return profile_CustomerManager;
    }

    public void setProfile_CustomerManager(CustomerManager profile_CustomerManager) { this.profile_CustomerManager = profile_CustomerManager; }

    public UserSuperAdmin getProfile_SuperAdmin() { return profile_SuperAdmin;
    }

    public void setProfile_SuperAdmin(UserSuperAdmin profile_SuperAdmin) { this.profile_SuperAdmin = profile_SuperAdmin; }


    public void setTransactionsFromDB(ArrayList<Transaction> transactions) {
        this.profile_Transactions = transactions;

    }

    public void setProfile_TimeLines(ArrayList<TimeLine> timeLineArrayList) {
        this.profile_TimeLineArrayList = timeLineArrayList;
    }

    public ArrayList<TimeLine> getProfile_TimeLines() { return profile_TimeLineArrayList;
    }

    public Customer getProfileCus() { return profile_Customer;
    }

    public void setProfileCus(Customer timelineCustomer) { this.profile_Customer = timelineCustomer; }

    public void setSkyLightPackagesFromDB(ArrayList<MarketBizPackage> marketBizPackages) {
        this.profile_MarketBizPackages = marketBizPackages;
    }

    public ArrayList<Payee> getProfile_Payees() { return profile_Payees; }



    public ArrayList<Transaction> getProfile_Transactions() { return profile_Transactions;
    }

    public ArrayList<LatLng> getProfileLocations() {
        return profile_LatLngs;
    }
    public void setProfile_Payees(ArrayList<Payee> payees) {
        this.profile_Payees = payees;
    }
    //@Ignore
    public void setProfile_Customers(ArrayList<Customer> customers) {
        this.profile_Customers = customers;
    }

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

    public ArrayList<Integer> getProfile_acctIDs() {
        return profile_acctIDs;
    }

    public void setProfile_acctIDs(ArrayList<Integer> profile_acctIDs) {
        this.profile_acctIDs = profile_acctIDs;
    }


    public int getProfOfficeID() {
        return profOfficeID;
    }

    public void setProfOfficeID(int profOfficeID) {
        this.profOfficeID = profOfficeID;
    }


    public ArrayList<InsuranceCompany> getInsuranceCompanies() {
        return insuranceCompanies;
    }

    public void setInsuranceCompanies(ArrayList<InsuranceCompany> insuranceCompanies) {
        this.insuranceCompanies = insuranceCompanies;
    }


    public String getProfileAdminType() {
        return profileAdminType;
    }

    public void setProfileAdminType(String profileAdminType) {
        this.profileAdminType = profileAdminType;
    }

    public String getProfileRoleType() {
        return profileRoleType;
    }

    public void setProfileRoleType(String profileRoleType) {
        this.profileRoleType = profileRoleType;
    }

    public ArrayList<EmergencyReport> getProfileEmergReports() {
        return profileEmergReports;
    }

    public void setProfileEmergReports(ArrayList<EmergencyReport> profileEmergReports) {
        this.profileEmergReports = profileEmergReports;
    }

    public ArrayList<EmergReportNext> getProfileEmergRNextS() {
        return profileEmergRNextS;
    }

    public void setProfileEmergRNextS(ArrayList<EmergReportNext> profileEmergRNextS) {
        this.profileEmergRNextS = profileEmergRNextS;
    }

    public ArrayList<EmergResponse> getProfileEmergResponses() {
        return profileEmergResponses;
    }

    public void setProfileEmergResponses(ArrayList<EmergResponse> profileEmergResponses) {
        this.profileEmergResponses = profileEmergResponses;
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

    public String toString() {
        return "ID: " + String.valueOf(this.pID) + "\nlastName: " + this.profileFirstName + "\nphoneNumber: "
                + String.valueOf(this.profileEmail) + "\ndob: " + this.profileAddress + "\njoinedDate: " + this.profileGender;
    }
    public void addMarketAdmin(MarketAdmin marketAdmin) {
        profMarketAdmins = new ArrayList<>();
        profMarketAdmins.add(marketAdmin);
    }

    public void addInsuranceCompany(InsuranceCompany insuranceCompany) {
        insuranceCompanies = new ArrayList<>();
        insuranceCompanies.add(insuranceCompany);
    }
    public void addMessage(Message message) {
        messageArrayList = new ArrayList<>();
        messageArrayList.add(message);
    }



    public void addMarketID(int marketID) {
        profMarketIDs = new ArrayList<>();
        profMarketIDs.add(marketID);
    }
    public void addUserType(String userType) {
        profUserTypes = new ArrayList<>();
        profUserTypes.add(userType);
    }

    /*public void addBusinessOthers(BusinessOthers businessOthers) {
        profBizOthers = new ArrayList<>();
        profBizOthers.add(businessOthers);
    }*/
    public void addMarketBusiness(MarketBusiness marketBusiness) {
        profMarketBizs = new ArrayList<>();
        profMarketBizs.add(marketBusiness);
    }
    public void addMarket(Market market) {
        profMarketArrayList = new ArrayList<>();
        profMarketArrayList.add(market);
    }
    public void addBusinessID(long businessID) {
        profBusinessIDs = new ArrayList<>();
        profBusinessIDs.add(businessID);
    }


    public void addProfFence(Fence fence) {
        profFenceArrayList = new ArrayList<>();
        profFenceArrayList.add(fence);
    }
    public void addProfileAcctID(int profileAcctID) {
        profile_acctIDs = new ArrayList<>();
        profile_acctIDs.add(profileAcctID);
    }
    public void addProfFenceEvents(FenceEvent fenceEvent) {
        profFenceEvents = new ArrayList<>();
        profFenceEvents.add(fenceEvent);
    }
    public void addProfileMarketID(int profileMarketID) {
        ArrayList<Integer> profileMarketIDs = new ArrayList<>();
        profileMarketIDs.add(profileMarketID);
    }
    public void addPCode(int PROFILE_ID, String CODE_OWNER_PHONE, long CODE_PIN, String CODE_DATE, String CODE_STATUS, String CODE_MANAGER) {
        profile_PaymentCodeArrayList = new ArrayList<>();
        String codeNo = "Code" + (profile_PaymentCodeArrayList.size() + 1);
        PaymentCode paymentCode = new PaymentCode(PROFILE_ID,CODE_OWNER_PHONE,CODE_PIN, CODE_DATE, CODE_STATUS,CODE_MANAGER);
        profile_PaymentCodeArrayList.add(paymentCode);
    }
    public void addPPaymentDocument(int id, String title, int customerId, int reportId, Uri documentLink, String status) {
        profile_PaymentDocArrayList = new ArrayList<>();
        String docNo = "Doc" + (profile_PaymentDocArrayList.size() + 1);
        PaymentDoc paymentDoc = new PaymentDoc(id,title, customerId, reportId,documentLink,status);
        profile_PaymentDocArrayList.add(paymentDoc);
    }

    //@Ignore

    public void addPAddress(Locale address) {
        profile_Addresses = new ArrayList<>();
        String addressNo = "No:" + (profile_Addresses.size() + 1);
        Address address1 = new Address(address);
        profile_Addresses.add(address1);
    }
    //@Ignore


    public void addMarketBusiness(long businessID, long profileID, String businessName, String bizEmail, String bizAddress, String bizPhoneNo, String bizType, String bizRegNo, String dateOfJoin, String status) {
        marketBusinessArrayList = new ArrayList<>();
        String bizNo = "Biz:" + (marketBusinessArrayList.size() + 1);
        MarketBusiness marketBusiness = new MarketBusiness(businessID,profileID, businessName, bizEmail, bizAddress, bizPhoneNo,  bizType,  bizRegNo,dateOfJoin,status);
        marketBusinessArrayList.add(marketBusiness);
    }
    //@Ignore
    public void addPSOAcct(int soNo, double expectedAmount) {
        profile_StandingOrders = new ArrayList<>();
        soNo = profile_StandingOrders.size() + 1;
        StandingOrder business = new StandingOrder(soNo,expectedAmount);
        profile_StandingOrders.add(business);

    }
    //@Ignore



    //@Ignore
    public void addPAccount(String accountBank, String accountName, int accountNumber, double accountBalance, AccountTypes accountTypes) {
        profile_Accounts = new ArrayList<>();
        String accNo = "A" + (profile_Accounts.size() + 1);
        Account account = new Account(accountBank,accountName, accountNumber, accountBalance,accountTypes);
        profile_Accounts.add(account);

    }
    //@Ignore
    public void addPSavingsGrpAcct(GroupAccount groupAccount) {
        profile_GroupAccounts= new ArrayList<>();
        profile_GroupAccounts.add(groupAccount);
    }
    public void addPGrpSavings(GroupSavings groupSavings) {
        prof_GrpSavings= new ArrayList<>();
        prof_GrpSavings.add(groupSavings);
    }

    public void addPAccount(Account account) {
        profile_Accounts = new ArrayList<>();
        profile_Accounts.add(account);
    }
    public void addPAccount(int virtualAccountID, String customerBank, String customerNames, long accountNo, double accountBalance, AccountTypes accountTypes) {
        profile_Accounts = new ArrayList<>();
        String accNo = "A" + (profile_Accounts.size() + 1);
        Account account = new Account(virtualAccountID,customerBank,customerNames, String.valueOf(accountNo), accountBalance,accountTypes);
        profile_Accounts.add(account);
    }
    /*@Ignore
    public void addInvAcct(int comAcctID, double v, String zero_interest, String currentDate) {

    }*/

    //@Ignore

    public void addPTellerReport(int dbaseID, int reportID, String date, double balance, String status) {
        ArrayList<TellerReport> tellerReports = null;
        reportID = ptellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(reportID, dbaseID,date,balance,status);
        ptellerReportArrayList.add(dailyReport);
    }

    public void addPTellerReport(int keyExtraReportId, String bizName, String officeBranch, double amountEntered, int noOfCustomers, String dateOfReport) {
        ArrayList<TellerReport> tellerReports = null;
        keyExtraReportId = ptellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(keyExtraReportId, bizName,officeBranch,amountEntered,noOfCustomers,dateOfReport);
        ptellerReportArrayList.add(dailyReport);
    }
    //@Ignore
    public void addPPayment(String type, double totalToWithdraw, Date date, long paymentCode, String acctType, String office, String status) {
        ArrayList<Payment> paymentArrayList = null;
        if (paymentArrayList != null) {
            String paymentNo = "Payment:" + (paymentArrayList.size() + 1);
        }
        profile_Payment = new Payment(type,totalToWithdraw, date,paymentCode,acctType,office,status);
        paymentArrayList.add(profile_Payment);

    }
    //@Ignore

    public void addPReport(int count, int customerID, String customerName, int packageID, int reportID, double savingsAmount, int numberOfDays, double totalAmountSum, int daysRemaining, double amountRemaining, String reportDate, String status) {
        count = profile_DailyReports.size() + 1;
        CustomerDailyReport dailyReport = new CustomerDailyReport( count,customerID,customerName,packageID, reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining,  reportDate, status);
        profile_DailyReports.add(dailyReport);
    }


    //@Ignore
    public void addPCustomer(int uID, String surname, String firstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String dateJoined, String userName, String password) {
        profile_Customers = new ArrayList<>();
        String CusNo = "C" + (profile_Customers.size() + 1);
        Customer customer = new Customer(uID,surname, firstName, customerPhoneNumber,customerEmailAddress,customerAddress,customerGender,customerOffice,customerState,profilePicture,dateJoined,userName,password);
        profile_Customers.add(customer);

    }
    //@Ignore
    public void addPCustomerManager(int uID, String surname, String firstName, String customerPhoneNumber, String dateOfBirth, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String dateJoined, String userName, String password) {
        profile_CustomerManagers = new ArrayList<>();
        String CMNo = "C" + (profile_CustomerManagers.size() + 1);
        CustomerManager customer = null;
        customer = new CustomerManager(uID,surname, firstName, customerPhoneNumber, dateOfBirth,customerEmailAddress,customerAddress,customerGender,customerOffice,customerState,profilePicture,dateJoined,userName,password);
        profile_CustomerManagers.add(customer);


    }
    //@Ignore
    public void addPCustomerManager1(int uID, String surname, String firstName, String managerGender, String managerOffice) {
        profile_CustomerManagers = new ArrayList<>();
        String CMNo = "C" + (profile_CustomerManagers.size() + 1);
        CustomerManager customerManager = null;
        customerManager = new CustomerManager(uID,surname, firstName, managerGender,managerOffice);
        profile_CustomerManagers.add(customerManager);


    }

    //@Ignore
    public void addPSavings(int profileId, int customerId, int newRecordID, double packageAmount, int numberOfDaysConverted, double totalForTheDay, int newNumberOfDaysRem, double newAmountRemaining, String reportDate, String status) {
        profile_DailyReports = new ArrayList<>();
        String savingsCount = "C" + (profile_DailyReports.size() + 1);
        CustomerDailyReport customerDailyReport = new CustomerDailyReport(profileId,customerId, newRecordID, packageAmount,numberOfDaysConverted,totalForTheDay,newNumberOfDaysRem,newAmountRemaining,reportDate,status);
        profile_DailyReports.add(customerDailyReport);
    }
    //@Ignore
    public void addPLoans(int loanId, double amount, String loanStartDate, String status, String loanEndDate, double interest1) {
        profile_Loans = new ArrayList<>();
        int loanNumber = Integer.parseInt(("Loan" + (profile_Loans.size() + 1)));
        Loan loan = new Loan(loanNumber, amount,loanStartDate,status,loanEndDate,interest1);
        profile_Loans.add(loan);
    }
    //@Ignore
    public void addPTimeLine(String tittle, String timelineDetails) {
        profile_TimeLines = new ArrayList<>();
        String history = "History" + (profile_TimeLines.size() + 1);
        TimeLine timeLine = new TimeLine(tittle,timelineDetails);
        profile_TimeLines.add(timeLine);
    }
    public void addPTimeLine(TimeLine timeLine) {
        profile_TimeLines = new ArrayList<>();
        profile_TimeLines.add(timeLine);
    }
    public void addPTellerReport(TellerReport tellerReport) {
        ptellerReportArrayList= new ArrayList<>();
        ptellerReportArrayList.add(tellerReport);



    }

    //@Ignore
    public void setProfile_Accounts(ArrayList<Account> accounts) {
        this.profile_Accounts = accounts;
    }
    //@Ignore
    public void addPTransaction(long transactionId, String surname, String firstName, String customerPhoneNumber, double amount, String accountNumber, String description, String date, String type) {
        profile_Transactions = new ArrayList<>();
        String transactionCount = "C" + (profile_Transactions.size() + 1);
        Transaction transaction1 = new Transaction(transactionId,surname, firstName, customerPhoneNumber,amount,accountNumber,description,date,type);
        profile_Transactions.add(transaction1);
    }
    //@Ignore

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
    //@Ignore

    public void addPPayee(String payeeName) {
        profile_Payees = new ArrayList<>();
        int payeeID = profile_Payees.size() + 1;
        Payee payee = new Payee(payeeID, payeeName);
        profile_Payees.add(payee);
    }
    //@Ignore


    //@Ignore

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
    //@Ignore


    public int leaveMessage(String message, int userId) {
        return 0;
    }
    //@Ignore


    public void addPNewBizPackage(int profileID, int customerID, int packageID, String packageType, double savingsAmount, int packageDuration, String startDate, double grandTotal, String endDate, String just_stated) {
        profile_MarketBizPackages = new ArrayList<>();
        String packageNo = "Package:" + (profile_MarketBizPackages.size() + 1);
        profile_MarketBizPackage = new MarketBizPackage(profileID,customerID, packageID, MarketBizPackage.SkylightPackage_Type.valueOf(packageType),savingsAmount,packageDuration,startDate,grandTotal,endDate, profileStatus);
        profile_MarketBizPackages.add(profile_MarketBizPackage);
    }
    //@Ignore


    public void addPStocksAll(int stocksID, String selectedStockPackage, String uStockType, String uStockModel, String uStockColor, String uStockSize, int uStockQuantity, String selectedOfficeBranch, double uStockPricePerUnit, String stockDate) {
        profile_Stocks = new ArrayList<>();
        int  stocksNo = profile_Stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,selectedStockPackage, uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);
        profile_Stocks.add(stocks1);

    }
    //@Ignore
    public void addPStocks(int stocksID, String stocksName, String uStockType, int uStockQuantity, long stockCode, String selectedOfficeBranch, String stockDate, String status) {
        profile_Stocks = new ArrayList<>();
        int  stocksNo = profile_Stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,stocksName,uStockType,uStockQuantity,stockCode,stockDate,status);
        profile_Stocks.add(stocks1);

    }


    //@Ignore
    public void addPTellerCash(int reportID, int packageID, String finalItemType, double tellerAmount, String tellerName, String officeBranch, String packageStartDate) {
        profile_TellerCashes = new ArrayList<>();
        int  stocksNo = profile_TellerCashes.size() + 1;
        TellerCash tellerCash = new TellerCash(reportID,packageID,finalItemType,tellerAmount,tellerName,officeBranch,packageStartDate);
        profile_TellerCashes.add(tellerCash);

    }
    public void addPTranxGranding(int tranxPayoutID, int customerID, String customerName, double amountRequested, String acctBank, String bankName, String acctBankNo, String s, String requestDate) {
        payoutNo = profile_TranxGrantings.size() + 1;
        TransactionGranting transactionGranting = new TransactionGranting(tranxPayoutID,customerID,customerName,amountRequested,acctBank,bankName,acctBankNo,s,requestDate);
        profile_TranxGrantings.add(transactionGranting);
    }
    public void addProfEmergResponse(EmergResponse emergResponse) {
        profileEmergResponses = new ArrayList<>();
        profileEmergResponses.add(emergResponse);
    }
    public void addEmergReportNext(EmergReportNext emergReportNext) {
        profileEmergRNextS = new ArrayList<>();
        profileEmergRNextS.add(emergReportNext);
    }
    public void addNewEmergReport(EmergencyReport emergencyReport) {
        profileEmergReports = new ArrayList<>();
        profileEmergReports.add(emergencyReport);
    }
    public void addBoatTrip(Trip trip) {
        profileTrips = new ArrayList<>();
        profileTrips.add(trip);

    }
    public void addTripBooking(TripBooking tripBooking) {
        profileTripBookings = new ArrayList<>();
        profileTripBookings.add(tripBooking);

    }



    //@Ignore
    @Override
    public int describeContents() {
        return 0;
    }


    public ArrayList<String> getProfUserTypes() {
        return profUserTypes;
    }

    public void setProfUserTypes(ArrayList<String> profUserTypes) {
        this.profUserTypes = profUserTypes;
    }


    public ArrayList<Trip> getProfileTrips() {
        return profileTrips;
    }

    public void setProfileBoatTrips(ArrayList<Trip> profileTrips) {
        this.profileTrips = profileTrips;
    }


    public TaxiDriver getProfileTaxiDriver() {
        return profileTaxiDriver;
    }

    public void setProfileTaxiDriver(TaxiDriver profileTaxiDriver) {
        this.profileTaxiDriver = profileTaxiDriver;
    }

    public Driver getProfDriver() {
        return profDriver;
    }

    public void setProfDriver(Driver profDriver) {
        this.profDriver = profDriver;
    }

    public ArrayList<TripBooking> getProfTripBookings() {
        return profTripBookings;
    }

    public void setProfTripBookings(ArrayList<TripBooking> profTripBookings) {
        this.profTripBookings = profTripBookings;
    }

    public int getProfNIN_ID() {
        return profNIN_ID;
    }

    public void setProfNIN_ID(int profNIN_ID) {
        this.profNIN_ID = profNIN_ID;
    }

    public String getProfNin_Number() {
        return profNin_Number;
    }

    public void setProfNin_Number(String profNin_Number) {
        this.profNin_Number = profNin_Number;
    }

    public String getProfNinType() {
        return profNinType;
    }

    public void setProfNinType(String profNinType) {
        this.profNinType = profNinType;
    }

    public String getProfNinEXPD() {
        return profNinEXPD;
    }

    public void setProfNinEXPD(String profNinEXPD) {
        this.profNinEXPD = profNinEXPD;
    }

    public Uri getProfNinPix() {
        return profNinPix;
    }

    public void setProfNinPix(Uri profNinPix) {
        this.profNinPix = profNinPix;
    }

    public String getProfNinApprover() {
        return profNinApprover;
    }

    public void setProfNinApprover(String profNinApprover) {
        this.profNinApprover = profNinApprover;
    }

    public String getProfNin_A_Office() {
        return profNin_A_Office;
    }

    public void setProfNin_A_Office(String profNin_A_Office) {
        this.profNin_A_Office = profNin_A_Office;
    }

    public String getProfNin_Status() {
        return profNin_Status;
    }

    public void setProfNin_Status(String profNin_Status) {
        this.profNin_Status = profNin_Status;
    }

    public ArrayList<Message> getMessageArrayList() {
        return messageArrayList;
    }

    public void setMessageArrayList(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    public ArrayList<Fence> getProfFenceArrayList() {
        return profFenceArrayList;
    }

    public void setProfFenceArrayList(ArrayList<Fence> profFenceArrayList) {
        this.profFenceArrayList = profFenceArrayList;
    }

    public ArrayList<FenceEvent> getProfFenceEvents() {
        return profFenceEvents;
    }

    public void setProfFenceEvents(ArrayList<FenceEvent> profFenceEvents) {
        this.profFenceEvents = profFenceEvents;
    }
    public int getProfileBusinessID() {
        return profileBusinessID;
    }

    public void setProfileBusinessID(int profileBusinessID) {
        this.profileBusinessID = profileBusinessID;
    }


}
