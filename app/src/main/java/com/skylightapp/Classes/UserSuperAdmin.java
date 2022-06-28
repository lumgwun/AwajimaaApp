package com.skylightapp.Classes;

import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.Stocks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;


public class UserSuperAdmin extends User implements  Parcelable, Serializable {
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


    public static final String CREATE_SUPER_ADMIN_TABLE = "CREATE TABLE IF NOT EXISTS " + SUPER_ADMIN_TABLE + " ( " + SUPER_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT  , " + SUPER_ADMIN_SURNAME + " TEXT , " + SUPER_ADMIN_PROFILE_ID + " INTEGER  , " +
            SUPER_ADMIN_FIRST_NAME + " TEXT, " + SUPER_ADMIN_PHONE_NUMBER + " TEXT, " + SUPER_ADMIN_EMAIL_ADDRESS + " TEXT, " + SUPER_ADMIN_DOB + " TEXT, " + SUPER_ADMIN_GENDER + " TEXT, " +
            SUPER_ADMIN_ADDRESS + " TEXT, " + SUPER_ADMIN_OFFICE + " TEXT, " + SUPER_ADMIN_USER_NAME + " TEXT, " + SUPER_ADMIN_PASSWORD + " TEXT, " + SUPER_ADMIN_NIN + " TEXT, " + "FOREIGN KEY(" + SUPER_ADMIN_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    private static final long serialVersionUID = 8924708152697574031L;
    private int superID;
    private int superRoleId;
    private String superRole;
    private String superUserName;
    private String sEmailAddress;
    private String sPhoneNumber;
    private Date sDateJoined;
    private String sPin;
    private String authenticationKey;
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
    private ArrayList<CustomerDailyReport> reports;
    private String transaction;
    private transient boolean authenticated;
    String sProfilePicture;
    protected ArrayList<AdminUser> adminUser;
    protected UserSuperAdmin superAdmin;
    protected ArrayList<SkyLightPackage> skyLightPackage;
    protected ArrayList<Customer> customers;
    protected Customer customer;
    protected UserVendor vendor;
    protected ArrayList<CustomerManager> customerManagers;
    protected ArrayList<Address> address;
    private ArrayList<Transaction> transactions;
    private ArrayList<TimeLine> timeLineArrayList;
    private ArrayList<LatLng> latLngs;
    private ArrayList<Payee> payees;
    private ArrayList<TimeLine> timeLines;
    private ArrayList<Stocks> stocks;

    private  LatLng lastLocation;

    private boolean remind;
    protected User_Type type;
    String nextOfKin;
    private String createdDate;
    private String country;
    //private Referral_Link referralLink;


    private static final String JSON_INVESTOR = "investor";
    private static final String JSON_LEARNER1 = "learner1";
    private static final String JSON_LEARNER2 = "learner2";
    private static final String JSON_ADMIN_USER = "admin";
    private static final String JSON_SUPER_ADMIN = "superAdmin";
    private static final String JSON_UID = "uid";
    private static final String JSON_REMIND = "remind";

    private List<Account> accounts = new ArrayList<Account>();
    //protected RolesEnumMap enumMap = new RolesEnumMap();
    long id;
    private String SAdminRole;

    protected UserSuperAdmin(Parcel in) {
        super();
        superID = in.readInt();
        superRole = in.readString();
        superUserName = in.readString();
        sEmailAddress = in.readString();
        sPhoneNumber = in.readString();
        sPin = in.readString();
        authenticationKey = in.readString();
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

        transaction = in.readString();
        sProfilePicture = in.readString();
        adminUser = in.readParcelable(AdminUser.class.getClassLoader());
        remind = in.readByte() != 0;
        nextOfKin = in.readString();
        createdDate = in.readString();
        country = in.readString();
        accounts = in.createTypedArrayList(Account.CREATOR);
        id = in.readLong();
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
        parcel.writeString(authenticationKey);
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

        parcel.writeString(transaction);
        parcel.writeString(sProfilePicture);

        parcel.writeByte((byte) (remind ? 1 : 0));
        parcel.writeString(nextOfKin);
        parcel.writeString(createdDate);
        parcel.writeString(country);
        parcel.writeTypedList(accounts);
        parcel.writeLong(id);
    }

    public List<SkyLightPackage> getPackages() {
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

    }

    public void setCustomDailyReport1(String string) {

    }
    public ArrayList<LatLng> getProfileLocations() {
        return latLngs;
    }
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

        //sendingAcc.getTransactions1().add(new Transaction("T" + (sendingAcc.getTransactions1().size() + 1) + "-T" + (sendingAccTransferCount+1), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
        //receivingAcc.getTransactions1().add(new Transaction("T" + (receivingAcc.getTransactions1().size() + 1) + "-T" + (receivingAccTransferCount+1), sendingAcc.toTransactionString(), receivingAcc.toTransactionString(), transferAmount));
    }

    public void addPayee(String payeeName) {
        payees= new ArrayList<>();
        int payeeID = payees.size() + 1;
        Payee payee = new Payee(payeeID, payeeName);
        payees.add(payee);
    }

    public void setPayeesFromDB(ArrayList<Payee> payees) {
        this.payees = payees;
    }
    public ArrayList<Transaction> getTransactions() { return transactions;
    }
    public void addTimeLine(int id, String tittle,String timelineDetails) {
        timeLines= new ArrayList<>();
        String history = "History" + (timeLines.size() + 1);
        TimeLine timeLine = new TimeLine(id, tittle,timelineDetails);
        timeLines.add(timeLine);
    }
    public void addTimeLine(String tittle,String timelineDetails) {
        timeLines= new ArrayList<>();
        String history = "History" + (timeLines.size() + 1);
        TimeLine timeLine = new TimeLine(tittle,timelineDetails);
        timeLines.add(timeLine);
    }

    public void addStocks(int stocksID, String selectedStockPackage, String uStockType, String uStockModel, String uStockColor, String uStockSize, int uStockQuantity, String selectedOfficeBranch, double uStockPricePerUnit, String stockDate) {
        stocks= new ArrayList<>();
        //stocksID = stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID, selectedStockPackage,uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);
        stocks.add(stocks1);
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
        this.id = id;
        this.sDesignation = designation1;
        this.sGender = gender1;
        this.sOffice = office1;
        this.sEmailAddress = email1;
        this.sPin = password1;
        this.sState = sState;
        this.sLga = sLga;
        this.sDob = Utils.stringToDate(sDob);
        this.country = country;
        this.createdDate = createdDate;
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
        this.id = id;
        this.sDesignation = designation1;
        this.sGender = gender1;
        this.sOffice = office1;
        this.sEmailAddress = email1;
        this.sPin = password1;
        this.sState = sState;
        this.sLga = sLga;
        this.country = country;
        this.createdDate = createdDate;
    }
    public ArrayList<AdminUser> getAdminUsers() {
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


    public boolean authenticate(String password, String phoneNumber) {

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int getUId() {
        return Math.toIntExact(id);
    }



    public void setUUserName(String userName) throws Exception  {
        if (userName != null && userName.length() > 0) {
            if (!this.superUserName.equals("")) {

                superDbHelper.updateProfileUserName(userName, this.superID);
            }
            this.superUserName = userName;
        }
    }
    public void setId(long uID) {
        if (id > 0) {
            this.superID = (int) uID;
        }

    }
    public long getURoleId() {
        return superRoleId;
    }
    public void setURoleId(int roleId) {
        if (roleId > 0) {
            this.superRoleId = roleId;
        }
    }

    public void setUPin(String pin) {
        this.sPin = pin;

    }
    public void setCustomerManagers(ArrayList<CustomerManager> customerManagers) {
        this.customerManagers = customerManagers;

    }
    public void setAddresses(ArrayList<Address> addresses) {
        this.address = addresses;

    }

    public ArrayList<SkyLightPackage> getSkyLightPackages() {
        return skyLightPackage;
    }
    public void setPackages(ArrayList<SkyLightPackage> skyLightPackages) {
        this.skyLightPackage = skyLightPackages;
    }

    public void setCustomDailyReport(ArrayList<CustomerDailyReport> report) {
        this.reports = report;
    }
    public String getTransaction() {
        return transaction;
    }
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
    public Uri getUProfilePicture() {
        return Uri.parse(sProfilePicture);
    }
    public void setUProfilePicture(String profilePicture) {
        this.sProfilePicture = profilePicture;
    }

    public String getUGender() {
        return sGender;
    }
    public void setUGender(String gender) {
        this.sGender = gender;
    }
    public String getAuthenticationKey() {
        return authenticationKey;
    }

    public void setAuthenticationKey(String authenticationKey) {
        this.authenticationKey = authenticationKey;
    }
    public void setUFirstName(String firstName) {
        this.sFirstName = firstName;
    }

    public String getUFirstName() {
        return sFirstName;
    }
    public void setUSurname(String surname) {
        this.sSurname = surname;
    }
    public String getUSurname() {
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
    public String getEmail() {
        return sEmailAddress;
    }
    public void setEmail(String email) {
        this.sEmailAddress = email;
    }



    public int getUserID() {
        return superID;
    }
    public void setUserID(int userID) {
        if (userID > 0) this.superID = userID;
    }
    public void setSuperRole(String superRole) {
        this.superRole = superRole;
    }


    public String getuUserName() {
        return superUserName;
    }
    public void setuUserName(String uUserName) {
        this.superUserName = uUserName;

    }
    public String getDateUserJoined() {
        return String.valueOf(sDateJoined);
    }
    public void setDateUserJoined(Date dateUserJoined) {
        this.sDateJoined = dateUserJoined;

    }
    public String getsPin() {
        return String.valueOf(Integer.parseInt(sPin));
    }
    public String getsDob() {
        return String.valueOf(sDob);
    }
    public void setsDob(String sDob) {
        this.sDob = Utils.stringToDate(String.valueOf(sDob));;

    }
    public String getsLga() {
        return sLga;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public  String getCountry() {
        return country;
    }
    public void setNextOfKin(String nextOfKin) {
        this.nextOfKin = nextOfKin;
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

    public String getNextOfKin() {
        return nextOfKin;
    }


    public String getsPhoneNumber() {
        return sPhoneNumber;
    }
    public void setsPhoneNumber(String sPhoneNumber) {
        this.sPhoneNumber = sPhoneNumber;

    }




    public boolean getAuthenticated() {
        return false;
    }

    public User_Type getType() {
        return type;
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
}
