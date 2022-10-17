package com.skylightapp.Classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.Stocks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;


public class UserSuperAdmin implements  Parcelable, Serializable {
    public static final String SUPER_ADMIN_ID = "super__Admin_id";
    public static final String SUPER_ADMIN_SURNAME = "super_admin_surname";
    public static final String SUPER_ADMIN_FIRST_NAME = "super_admin_first_name";
    public static final String SUPER_ADMIN_PHONE_NUMBER = "super_admin_phone_number";
    public static final String SUPER_ADMIN_EMAIL_ADDRESS = "super_admin_email_address";
    public static final String SUPER_ADMIN_DOB = "super_admin_date_of_birth";
    public static final String SUPER_ADMIN_GENDER = "super_admin_gender";
    public static final String SUPER_ADMIN_ADDRESS = "super_admin_address";
    public static final String SUPER_ADMIN_OFFICE = "super_admin_chosen_office";
    public static final String SUPER_ADMIN_USER_NAME = "super_admin_user_name";
    public static final String SUPER_ADMIN_PASSWORD = "super_admin_password";
    public static final String SUPER_ADMIN_NIN = "super_admin_NIN";
    public static final String SUPER_ADMIN_PROFILE_ID = "super_admin_Prof_ID";
    public static final String SUPER_ADMIN_TABLE = "super_admin_Table";
    public static final String SUPER_ADMIN_DB_ID = "super__Admin_DB_id";
    public static final String SUPER_ADMIN_MARKETBIZ_ID = "super__Admin_MarketBiz_id";


    public static final String CREATE_SUPER_ADMIN_TABLE = "CREATE TABLE IF NOT EXISTS " + SUPER_ADMIN_TABLE + " ( " + SUPER_ADMIN_ID + " INTEGER   , " + SUPER_ADMIN_SURNAME + " TEXT , " + SUPER_ADMIN_PROFILE_ID + " INTEGER  , " +
            SUPER_ADMIN_FIRST_NAME + " TEXT, " + SUPER_ADMIN_PHONE_NUMBER + " TEXT, " + SUPER_ADMIN_EMAIL_ADDRESS + " TEXT, " + SUPER_ADMIN_DOB + " TEXT, " + SUPER_ADMIN_GENDER + " TEXT, " +
            SUPER_ADMIN_ADDRESS + " TEXT, " + SUPER_ADMIN_OFFICE + " TEXT, " + SUPER_ADMIN_USER_NAME + " TEXT, " + SUPER_ADMIN_PASSWORD + " TEXT, " + SUPER_ADMIN_NIN + " TEXT, "+ SUPER_ADMIN_MARKETBIZ_ID + " INTEGER, "+ SUPER_ADMIN_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + "FOREIGN KEY(" + SUPER_ADMIN_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    private static final long serialVersionUID = 8924708152697574031L;
    private int superID;
    private String superRole;
    private String superUserName;
    private String sEmailAddress;
    private String sPhoneNumber;
    private String sDateJoined;
    private String sPin;
    private String sAuthenticationKey;
    String sFirstName;
    String sSurname;
    String sMiddleName;
    String sDesignation;
    String sGender;
    String sOffice;
    String sState;
    String sLga;
    String sDob;
    private String sPassword;
    String sOthers;
    DBHelper superDbHelper;
    private OfficeBranch sOfficeBranch;
    private ArrayList<CustomerDailyReport> sCustomDailyReports;
    private String supertranx;
    private transient boolean authenticated;
    String sProfilePicture;
    protected ArrayList<AdminUser> s_AdminUsers;
    protected UserSuperAdmin sSuperAdmin;
    protected ArrayList<SkyLightPackage> superSkyLightPackages;
    protected ArrayList<Customer> superCustomers;
    protected Customer superCustomer;
    protected ArrayList<CustomerManager> sCustomerManagers;
    //protected ArrayList<Address> address;
    private ArrayList<Transaction> sTransactions;
    private ArrayList<TimeLine> sTimeLineArrayList;
    private ArrayList<LatLng> sLatLngs;
    private ArrayList<Payee> sPayees;
    private ArrayList<TimeLine> sTimeLines;
    private ArrayList<Stocks> sStocks;
    private  LatLng superLastLocation;
    private static final String JSON_INVESTOR = "investor";
    private static final String JSON_LEARNER1 = "learner1";
    private static final String JSON_LEARNER2 = "learner2";
    private static final String JSON_ADMIN_USER = "admin";
    private static final String JSON_SUPER_ADMIN = "superAdmin";
    private static final String JSON_UID = "uid";
    private static final String JSON_REMIND = "remind";

    private String SAdminRole;

    protected UserSuperAdmin(Parcel in) {
        super();
        superID = in.readInt();
        superRole = in.readString();
        superUserName = in.readString();
        sEmailAddress = in.readString();
        sPhoneNumber = in.readString();
        sPin = in.readString();
        sAuthenticationKey = in.readString();
        sFirstName = in.readString();
        sSurname = in.readString();
        sMiddleName = in.readString();
        sDesignation = in.readString();
        sGender = in.readString();
        sOffice = in.readString();
        sState = in.readString();
        sLga = in.readString();
        sPassword = in.readString();
        sOthers = in.readString();

        supertranx = in.readString();
        sProfilePicture = in.readString();
        s_AdminUsers = in.readParcelable(AdminUser.class.getClassLoader());
        superID = in.readInt();
    }

    public static final Creator<UserSuperAdmin> CREATOR = new Creator<UserSuperAdmin>() {
        @Override
        public UserSuperAdmin createFromParcel(Parcel in) {
            return new UserSuperAdmin(in);
        }

        @Override
        public UserSuperAdmin[] newArray(int size) {
            return new UserSuperAdmin[size];
        }
    };

    public UserSuperAdmin(int superID, String sSurname, String sFirstName) {
        super();
        this.superID =superID;
        this.sSurname = sSurname;
        this.sFirstName = sFirstName;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(superID);
        parcel.writeString(superRole);
        parcel.writeString(superUserName);
        parcel.writeString(sEmailAddress);
        parcel.writeString(sPhoneNumber);
        parcel.writeString(sPin);
        parcel.writeString(sAuthenticationKey);
        parcel.writeString(sFirstName);
        parcel.writeString(sSurname);
        parcel.writeString(sMiddleName);
        parcel.writeString(sDesignation);
        parcel.writeString(sGender);
        parcel.writeString(sOffice);
        parcel.writeString(sState);
        parcel.writeString(sLga);
        parcel.writeString(sPassword);
        parcel.writeString(sOthers);

        parcel.writeString(supertranx);
        parcel.writeString(sProfilePicture);
        parcel.writeInt(superID);
    }

    public List<SkyLightPackage> getPackages() {
        return (List<SkyLightPackage>) superSkyLightPackages;
    }
    public Customer getSuperCustomer() {
        return superCustomer;
    }

    public ArrayList<CustomerManager> getCustomerManager(Profile userProfile) {
        return sCustomerManagers;
    }

    public void setSCustomDailyReport1(ArrayList<CustomerDailyReport> sCustomDailyReports) {
        this.sCustomDailyReports = sCustomDailyReports;

    }

    public ArrayList<LatLng> getSLatLngs() {
        return sLatLngs;
    }
    public void addSTransferTranx(Account sendingAcc, Account receivingAcc, double transferAmount) {

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

        //sendingAcc.getTransactions1().add(new Transaction("T" + (sendingAcc.getTransactions1().size() + 1) + "-T" + (sendingAccTransferCount+1), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
        //receivingAcc.getTransactions1().add(new Transaction("T" + (receivingAcc.getTransactions1().size() + 1) + "-T" + (receivingAccTransferCount+1), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
    }

    public void addSPayee(String payeeName) {
        sPayees = new ArrayList<>();
        int payeeID = sPayees.size() + 1;
        Payee payee = new Payee(payeeID, payeeName);
        sPayees.add(payee);
    }

    public void setSPayees(ArrayList<Payee> payees) {
        this.sPayees = payees;
    }
    public ArrayList<Transaction> getSTransactions() { return sTransactions;
    }
    public void addSTimeLine(int id, String tittle, String timelineDetails) {
        sTimeLines = new ArrayList<>();
        String history = "History" + (sTimeLines.size() + 1);
        TimeLine timeLine = new TimeLine(id, tittle,timelineDetails);
        sTimeLines.add(timeLine);
    }
    public void addSTimeLine(String tittle, String timelineDetails) {
        sTimeLines = new ArrayList<>();
        String history = "History" + (sTimeLines.size() + 1);
        TimeLine timeLine = new TimeLine(tittle,timelineDetails);
        sTimeLines.add(timeLine);
    }

    public void addStocks(int stocksID, String selectedStockPackage, String uStockType, String uStockModel, String uStockColor, String uStockSize, int uStockQuantity, String selectedOfficeBranch, double uStockPricePerUnit, String stockDate) {
        sStocks = new ArrayList<>();
        //stocksID = stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID, selectedStockPackage,uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);
        sStocks.add(stocks1);
    }




    public UserSuperAdmin() {
        super();
    }
    public UserSuperAdmin(String surname1, String firstName1, String middleName1, String phoneNumber1, String email1, String sDob, String gender1, String office1, String designation1, String password1) {
        super();
        this.sFirstName = firstName1;
        this.sSurname = surname1;
        this.sPhoneNumber = phoneNumber1;
        this.sMiddleName = middleName1;
        this.sDesignation = designation1;
        this.sGender = gender1;
        this.sOffice = office1;
        this.sEmailAddress = email1;
        this.sPin = password1;
        this.sDob = sDob;

    }
    public UserSuperAdmin(String sSurname) {
        super();
        this.sSurname = sSurname;
    }


    public UserSuperAdmin(String surname1, String firstName1, String middleName1, String phoneNumber1, String email1, String gender1, String office1, String designation1, String password1) {
        super();
        this.sFirstName = firstName1;
        this.sSurname = surname1;
        this.sPhoneNumber = phoneNumber1;
        this.sMiddleName = middleName1;
        this.sDesignation = designation1;
        this.sGender = gender1;
        this.sOffice = office1;
        this.sEmailAddress = email1;
        this.sPin = password1;

    }
    public ArrayList<AdminUser> getS_AdminUsers() {
        return (ArrayList<AdminUser>) s_AdminUsers;
    }
    public ArrayList<Customer> getSuperCustomers() {
        return (ArrayList<Customer>) superCustomers;
    }

    public boolean superAuthenticate(String password, String phoneNumber) {

        return false;
    }



    /*public void setUUserName(String userName) throws Exception  {
        if (userName != null && userName.length() > 0) {
            if (!this.superUserName.equals("")) {

                superDbHelper.updateProfileUserName(userName, this.superID);
            }
            this.superUserName = userName;
        }
    }*/

    public void setSPin(String pin) {
        this.sPin = pin;

    }


    public ArrayList<SkyLightPackage> getSuperSkyLightPackages() {
        return superSkyLightPackages;
    }
    public void setSuperSkyLightPackages(ArrayList<SkyLightPackage> skyLightPackages) {
        this.superSkyLightPackages = skyLightPackages;
    }

    public void setSCustomDailyReports(ArrayList<CustomerDailyReport> report) {
        this.sCustomDailyReports = report;
    }
    public String getSupertranx() {
        return supertranx;
    }
    public void setSupertranx(String supertranx) {
        this.supertranx = supertranx;
    }
    public Uri getSProfilePicture() {
        return Uri.parse(sProfilePicture);
    }
    public void setSProfilePicture(String profilePicture) {
        this.sProfilePicture = profilePicture;
    }

    public String getSGender() {
        return sGender;
    }
    public void setSGender(String gender) {
        this.sGender = gender;
    }
    public String getsAuthenticationKey() {
        return sAuthenticationKey;
    }

    public void setSAuthenticationKey(String authenticationKey) {
        this.sAuthenticationKey = authenticationKey;
    }
    public void setSFirstName(String firstName) {
        this.sFirstName = firstName;
    }

    public String getSFirstName() {
        return sFirstName;
    }
    public void setSSurname(String surname) {
        this.sSurname = surname;
    }
    public String getSSurname() {
        return sSurname;
    }

    public String getsMiddleName() {
        return sMiddleName;
    }
    public void setsMiddleName(String sMiddleName) {
        this.sMiddleName = sMiddleName;
    }


    public String getsDesignation() {
        return sDesignation;
    }
    public void setsDesignation(String sDesignation) {
        this.sDesignation = sDesignation;
    }

    public String getsOffice() {
        return sOffice;
    }
    public void setsOffice(String sOffice) {
        this.sOffice = sOffice;
    }
    public String getSEmailAddress() {
        return sEmailAddress;
    }
    public void setSEmailAddress(String email) {
        this.sEmailAddress = email;
    }



    public int getSuperID() {
        return superID;
    }
    public void setSuperID(int superID) {
        if (superID > 0) this.superID = superID;
    }
    public void setSuperRole(String superRole) {
        this.superRole = superRole;
    }


    public String getSuperUserName() {
        return superUserName;
    }
    public void setSuperUserName(String uUserName) {
        this.superUserName = uUserName;

    }
    public String getSDateJoined() {
        return String.valueOf(sDateJoined);
    }
    public void setSDateJoined(String dateUserJoined) {
        this.sDateJoined = dateUserJoined;

    }
    public String getsPin() {
        return String.valueOf(Integer.parseInt(sPin));
    }
    public String getsDob() {
        return String.valueOf(sDob);
    }
    public void setsDob(String sDob) {
        this.sDob = sDob;;

    }
    public String getsLga() {
        return sLga;
    }

    public void setsLga(String sLga) {
        this.sLga = sLga;
    }
    public String getsState() {
        return sState;
    }
    public void setsState(String sState) {
        this.sState = sState;
    }


    public String getsEmailAddress() {
        return sEmailAddress;
    }
    public void setsEmailAddress(String sEmailAddress) {
        this.sEmailAddress = sEmailAddress;

    }

    public String getsPhoneNumber() {
        return sPhoneNumber;
    }
    public void setsPhoneNumber(String sPhoneNumber) {
        this.sPhoneNumber = sPhoneNumber;

    }

    public boolean getSAuthenticated() {
        return false;
    }

    public OfficeBranch getsOfficeBranch() {
        return sOfficeBranch;
    }

    public void setsOfficeBranch(OfficeBranch sOfficeBranch) {
        this.sOfficeBranch = sOfficeBranch;
    }

    public String getSAdminRole() {
        return SAdminRole;
    }

    public void setSAdminRole(String sAdminRole) {
        this.SAdminRole = sAdminRole;
    }

    public ArrayList<Transaction> getsTransactions() {
        return sTransactions;
    }

    public void setsTransactions(ArrayList<Transaction> sTransactions) {
        this.sTransactions = sTransactions;
    }

    public ArrayList<TimeLine> getsTimeLineArrayList() {
        return sTimeLineArrayList;
    }

    public void setsTimeLineArrayList(ArrayList<TimeLine> sTimeLineArrayList) {
        this.sTimeLineArrayList = sTimeLineArrayList;
    }

    public ArrayList<LatLng> getsLatLngs() {
        return sLatLngs;
    }

    public void setsLatLngs(ArrayList<LatLng> sLatLngs) {
        this.sLatLngs = sLatLngs;
    }

    public LatLng getSuperLastLocation() {
        return superLastLocation;
    }

    public void setSuperLastLocation(LatLng superLastLocation) {
        this.superLastLocation = superLastLocation;
    }
}
