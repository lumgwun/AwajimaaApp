package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.location.Address;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;

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

@Entity(tableName = CustomerManager.CUSTOMER_TELLER_TABLE)
public class CustomerManager extends User implements Parcelable, Serializable , BaseColumns {
    @Ignore
    public static final String CUSTOMER_TELLER_ID = "ct_idT";
    @Ignore
    public static final String CUSTOMER_TELLER_SURNAME = "surnameT";
    @Ignore
    public static final String CUSTOMER_TELLER_FIRST_NAME = "first_nameT";
    @Ignore
    public static final String CUSTOMER_TELLER_PHONE_NUMBER = "phone_numberT";
    @Ignore
    public static final String CUSTOMER_TELLER_EMAIL_ADDRESS = "email_addressT";
    @Ignore
    public static final String CUSTOMER_TELLER_DOB = "date_of_birthT";
    @Ignore
    public static final String CUSTOMER_TELLER_GENDER = "genderT";
    @Ignore
    public static final String CUSTOMER_TELLER_ADDRESS = "addressT";
    @Ignore
    public static final String CUSTOMER_TELLER_OFFICE = "chosen_officeT";
    @Ignore
    public static final String CUSTOMER_TELLER_DATE_JOINED = "date_joinedT";
    @Ignore
    public static final String CUSTOMER_TELLER_USER_NAME = "user_nameT";
    @Ignore
    public static final String CUSTOMER_TELLER_PASSWORD = "passwordT";
    @Ignore
    public static final String CUSTOMER_TELLER_NIN = "ninT";
    @Ignore
    public static final String CUSTOMER_TELLER_PIX = "pixT";
    @Ignore
    public static final String CUSTOMER_TELLER_STATUS = "StatusT";
    @Ignore
    public static final String CUSTOMER_TELLER_PROF_ID = "teller_Prof_ID";

    @Ignore
    public static final String CUSTOMER_TELLER_TABLE = "tellers_table";
    @Ignore
    public static final String WORKER_TABLE = "workers_table";
    @Ignore
    public static final String WORKER = "worker";
    @Ignore
    public static final String WORKER_ID = "worker_id";
    @Ignore

    public static final String CREATE_WORKERS_TABLE = "CREATE TABLE IF NOT EXISTS " + WORKER_TABLE + " ( " + WORKER_ID + " INTEGER ,  " + WORKER + " TEXT  , " +
            PROFILE_ID + " INTEGER, " + "FOREIGN KEY(" + PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"PRIMARY KEY(" + WORKER_ID  + "))";

    @Ignore
    public static final String CREATE_CUSTOMERS_TELLER_TABLE = "CREATE TABLE IF NOT EXISTS " + CUSTOMER_TELLER_TABLE + " ( " + CUSTOMER_TELLER_ID + " INTEGER   ,  " + CUSTOMER_TELLER_PROF_ID + " INTEGER  , " +
            CUSTOMER_TELLER_SURNAME + " TEXT, " + CUSTOMER_TELLER_FIRST_NAME + " TEXT, " + CUSTOMER_TELLER_PHONE_NUMBER + " TEXT, " + CUSTOMER_TELLER_EMAIL_ADDRESS + " TEXT, " + CUSTOMER_TELLER_USER_NAME + " TEXT, " +
            CUSTOMER_TELLER_DOB + " TEXT, " + CUSTOMER_TELLER_GENDER + " TEXT, " + CUSTOMER_TELLER_ADDRESS + " TEXT, " + CUSTOMER_TELLER_OFFICE + " TEXT, " + CUSTOMER_TELLER_DATE_JOINED + " TEXT, " +
            CUSTOMER_TELLER_PASSWORD + " TEXT, " + CUSTOMER_TELLER_NIN + " TEXT," + CUSTOMER_TELLER_PIX + " TEXT," + CUSTOMER_TELLER_STATUS + " TEXT, " + "FOREIGN KEY(" + CUSTOMER_TELLER_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"PRIMARY KEY(" + CUSTOMER_TELLER_ID  + "))";
    @Ignore
    private static final long serialVersionUID = 8924708152697574031L;
    @SuppressLint("SimpleDateFormat")
    @Ignore
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CUSTOMER_TELLER_ID)
    private int tID=1012;
    @ColumnInfo(name = CUSTOMER_TELLER_FIRST_NAME)
    String tFirstName;
    @ColumnInfo(name = CUSTOMER_TELLER_SURNAME)
    String tSurname;
    @ColumnInfo(name = CUSTOMER_TELLER_STATUS)
    String tStatus;
    @Ignore
    String tMiddleName;
    @ColumnInfo(name = CUSTOMER_TELLER_GENDER)
    String tGender;
    @ColumnInfo(name = CUSTOMER_TELLER_OFFICE)
    String tOffice;
    @Ignore
    String tState;
    @Ignore
    String tLga;
    @ColumnInfo(name = CUSTOMER_TELLER_ADDRESS)
    String tAddress;
    @ColumnInfo(name = CUSTOMER_TELLER_DOB)
    String tDob;
    @Ignore
    private  LatLng lastLocation;
    @ColumnInfo(name = CUSTOMER_TELLER_PASSWORD)
    private String tPassword;
    @ColumnInfo(name = CUSTOMER_TELLER_EMAIL_ADDRESS)
    private String tEmailAddress;
    @ColumnInfo(name = CUSTOMER_TELLER_PHONE_NUMBER)
    private String tPhoneNumber;
    @ColumnInfo(name = CUSTOMER_TELLER_DATE_JOINED)
    private String tDateJoined;
    @Ignore
    String tNextOfKin;
    @ColumnInfo(name = CUSTOMER_TELLER_USER_NAME)
    private String tUserName;

    @TypeConverters
    @ColumnInfo(name = CUSTOMER_TELLER_PIX)
    private Uri tPhoto;

    public void setTOffice(String tOffice) {
        this.tOffice = tOffice;
    }

    public String getTOffice() {
        return tOffice;
    }

    public void setTStatus(String tStatus) {
        this.tStatus = tStatus;
    }

    public String getTStatus() {
        return tStatus;
    }

    public void setTFirstName(String tFirstName) {
        this.tFirstName = tFirstName;
    }

    public String getTFirstName() {
        return tFirstName;
    }
    public void setTSurname(String tSurname) {
        this.tSurname = tSurname;
    }
    public String getTSurname() {
        return tSurname;
    }
    public void setTPhoto(Uri tPhoto) {
        this.tPhoto = tPhoto;
    }
    public Uri getTPhoto() {
        return tPhoto;
    }

    public String getTMiddleName() {
        return tMiddleName;
    }
    public void setTMiddleName(String tMiddleName) {
        this.tMiddleName = tMiddleName;
    }

    public int getTID() {
        return tID;
    }
    public void setTID(int tID) {
        this.tID = tID;

    }
    public String getTEmailAddress() {
        return tEmailAddress;
    }
    public void setTEmailAddress(String tEmailAddress) {
        this.tEmailAddress = tEmailAddress;
    }
    public String getTUserName() {
        return tUserName;
    }
    public void setTUserName(String tUserName) {
        this.tUserName = tUserName;

    }

    public String getTDateJoined() {
        return tDateJoined;
    }
    public void setTDateJoined(String tDateJoined) {
        this.tDateJoined = tDateJoined;

    }

    public String getTDob() {
        return tDob;
    }

    public String getTLga() {
        return tLga;
    }

    public void setTNextOfKin(String tNextOfKin) {
        this.tNextOfKin = tNextOfKin;
    }

    public void setTLga(String tLga) {
        this.tLga = tLga;
    }
    public String getTState() {
        return tState;
    }
    public void setTState(String tState) {
        this.tState = tState;
    }


    public void setTAddress(String tAddress) {
        this.tAddress = tAddress;
    }

    public String getTNextOfKin() {
        return tNextOfKin;
    }


    public String getTPhoneNumber() {
        return tPhoneNumber;
    }
    public void setTPhoneNumber(String tPhoneNumber) {
        this.tPhoneNumber = tPhoneNumber;

    }







    @Ignore
    private Profile tellerProfile;
    @Ignore
    ArrayList<CustomerDailyReport> dailyReports;
    @Ignore
    ArrayList<Transaction> transactions;
    @Ignore
    ArrayList<Address> addresses;
    @Ignore
    ArrayList<TellerReport> tellerReportArrayList;
    @Ignore
    private ArrayList<TimeLine> timeLines;
    @Ignore
    ArrayList<Customer> customerArrayList;
    @Ignore
    boolean alreadyAdded;

    @Ignore
    public static final List<Customer.CustomerItem> ITEMS = new ArrayList<Customer.CustomerItem>();
    @Ignore

    public static final Map<String, Customer.CustomerItem> ITEM_MAP = new HashMap<String, Customer.CustomerItem>();
    @Ignore

    private static final int COUNT = 25;


    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createCustomerItem(i));
        }
    }

    protected CustomerManager(Parcel in) {
        super(in);
        tID = in.readInt();
        tFirstName = in.readString();
        tSurname = in.readString();
        tMiddleName = in.readString();
        tGender = in.readString();
        tOffice = in.readString();
        tState = in.readString();
        tLga = in.readString();
        tAddress = in.readString();
        tPassword = in.readString();
        tEmailAddress = in.readString();
        tPhoneNumber = in.readString();
        tDateJoined = in.readString();
        tNextOfKin = in.readString();
        tUserName = in.readString();
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

    public CustomerManager() {
        super();

    }

    public CustomerManager(JSONObject jsonObject) {
        super();
    }
    public CustomerManager(int wID, String tSurname, String tFirstName, String customerPhoneNumber, String dateOfBirth, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String tDateJoined, String tUserName, String password) {
        super();
        this.tSurname = tSurname;
        this.tFirstName = tFirstName;
        this.tOffice = customerOffice;
        this.tPhoneNumber = customerPhoneNumber;
        this.tState = customerState;
        this.tPhoto = profilePicture;
        this.tDateJoined = tDateJoined;
        this.tDob = dateOfBirth;
        this.tPassword = password;
        this.tUserName = tUserName;
        this.tGender = tGender;
        this.tAddress = customerAddress;
        this.tEmailAddress = customerEmailAddress;
        this.tPhoto = tPhoto;
        this.tID = wID;
    }
    public CustomerManager(int wID, String tSurname) {
        super();
        this.tSurname = tSurname;
        this.tID = wID;
    }
    public CustomerManager(int wID, String tSurname, String tFirstName, String tGender, String tOffice) {
        super();
        this.tSurname = tSurname;
        this.tFirstName = tFirstName;
        this.tOffice = tOffice;
        this.tGender = tGender;
        this.tID = wID;
    }

    public CustomerManager(int uid, String tSurname, String tFirstName, String tPhoneNumber, String tEmailAddress, String tDob, String tGender, String tAddress, String tUserName, String password, String tOffice, String tDateJoined) throws ConnectionFailedException {
        super();
        this.tFirstName = tFirstName;
        this.tSurname = tSurname;
        this.tPhoneNumber = tPhoneNumber;
        this.tGender = tGender;
        this.tID = uid;
        this.tGender = tGender;
        this.tOffice = tOffice;
        this.tEmailAddress = tEmailAddress;
        this.tPassword = password;
        this.tUserName = tUserName;
        this.tAddress = tAddress;
        this.tDateJoined = tDateJoined;
        //this.setURoleId(this.enumMap.getRoleId("TELLER"));
        //List<Integer> accountIds = DBHelper.getAccountIds(this.getId());
        // add each account to the users account
        //for (Integer accountId : accountIds) {
         //   this.addAccount(dbHelper.getAccountDetails(accountId));
       // }
    }

    /*public CustomerManager(int uid, String surname, String firstName, String phoneNumber, String emailAddress, String dob, String gender, String address, String userName, String password, String office, String dateJoined) {

        this.firstName = firstName;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.uID = uid;
        this.dob = dob;
        this.gender = gender;
        this.office = office;
        this.emailAddress = emailAddress;
        this.password = password;
        this.userName = userName;
        this.dob = dob;
        this.address = address;
        this.dateJoined = dateJoined;

    }*/
    public CustomerManager(int uid, String tSurname, String tFirstName, String tPhoneNumber, String tGender, String tAddress, String tUserName, String password, String tOffice) {
        super();
        this.tID = uid;
        this.tDateJoined = DATE_FORMAT.format(new Date());
        this.tSurname = tSurname;
        this.tFirstName = tFirstName;
        this.tPhoneNumber = tPhoneNumber;
        this.tGender = tGender;
        this.tAddress = tAddress;
        this.tUserName = tUserName;
        this.tPassword = password;
        this.tOffice = tOffice;
    }


    public CustomerManager(int uid, String tSurname, String tFirstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, String tUserName, String password) {
        super();
        this.tID = uid;
        this.tDateJoined = DATE_FORMAT.format(new Date());
        this.tSurname = tSurname;
        this.tFirstName = tFirstName;
        this.tPhoneNumber = customerPhoneNumber;
        this.tGender = customerGender;
        this.tAddress = customerAddress;
        this.tUserName = tUserName;
        this.tPassword = password;
        this.tOffice = customerOffice;
    }

    public CustomerManager(long tellerID, String tSurname, String tFirstName, String tPhoneNumber, String tEmailAddress, String nin, String tDob, String tGender, String tAddress, String tUserName, String password, String tOffice, String tDateJoined, Uri pix, String tStatus)
            throws ConnectionFailedException {
        super();
        this.tFirstName = tFirstName;
        this.tSurname = tSurname;
        this.tPhoneNumber = tPhoneNumber;
        this.tGender = tGender;
        this.tID = Math.toIntExact(tellerID);
        this.tGender = tGender;
        this.tOffice = tOffice;
        this.tEmailAddress = tEmailAddress;
        this.tPassword = password;
        this.tUserName = tUserName;
        this.tAddress = tAddress;
        this.tStatus = tStatus;
        this.tDateJoined = tDateJoined;
        if (tAddress != null) {
            this.setTAddress(tAddress);
        }
        this.tPhoto = pix;
    }
    public CustomerManager(int uid, String tSurname, String tFirstName, String tPhoneNumber, String tEmailAddress, String tDob, String tGender, String tAddress, String tUserName, String password, String tOffice, String tDateJoined, boolean authenticated)
            throws ConnectionFailedException {
        super();
        this.tFirstName = tFirstName;
        this.tSurname = tSurname;
        this.tPhoneNumber = tPhoneNumber;
        this.tGender = tGender;
        this.tID = uid;
        this.tGender = tGender;
        this.tOffice = tOffice;
        this.tEmailAddress = tEmailAddress;
        this.tPassword = password;
        this.tUserName = tUserName;
        this.tAddress = tAddress;
        this.tDateJoined = tDateJoined;
        if (tAddress != null) {
            this.setTAddress(tAddress);
        }
        //this.setURoleId(this.enumMap.getRoleId("TELLER"));
        //this.authenticated = authenticated;
    }

    public CustomerManager(int tellerID, String tSurname, String tFirstName) {
        super();
        this.tFirstName = tFirstName;
        this.tSurname = tSurname;
        this.tID = tellerID;

    }

    @Ignore
    public void setAddress1(String address) {
        this.tAddress =address;

    }
    @Ignore
    public LatLng getLastKnownLocation() {
        return lastLocation;
    }
    @Ignore
    public LatLng setLastKnownLocation(LatLng lastLocation) {
        this.lastLocation = lastLocation;

        return lastLocation;
    }
    @Ignore
    public void addTimeLine(String tittle,String timelineDetails) {
        timeLines= new ArrayList<>();
        String history = "History" + (timeLines.size() + 1);
        TimeLine timeLine = new TimeLine(tittle,timelineDetails);
        timeLines.add(timeLine);
    }

    @Ignore
    public void addTimeLine(int timeLineID,String tittle,String timelineDetails) {
        timeLines= new ArrayList<>();
        String history = "History" + (timeLines.size() + 1);
        TimeLine timeLine = new TimeLine(timeLineID, tittle,timelineDetails);
        timeLines.add(timeLine);
    }
    @Ignore


    private static void addItem(Customer.CustomerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
    @Ignore

    private static Customer.CustomerItem createCustomerItem(int position) {
        return new Customer.CustomerItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }
    @Ignore

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }
    @Ignore

    public boolean isAlreadyAdded() {
        return false;
    }
    @Ignore

    public void setAlreadyAdded(boolean alreadyAdded) {

    }
    @Ignore
    public boolean getAlreadyAdded() {
        return alreadyAdded;
    }

    public String getTPassword() {
        return tPassword;
    }

    public void setTPassword(String tPassword) {
        this.tPassword = tPassword;
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

    public void addReport(int count,int customerID,String customerName,int packageID, int reportID, double savingsAmount, int numberOfDays, double totalAmountSum, int daysRemaining, double amountRemaining, String reportDate, String status) {
        count = dailyReports.size() + 1;
        CustomerDailyReport dailyReport = new CustomerDailyReport( count,customerID,customerName,packageID, reportID, savingsAmount, numberOfDays, totalAmountSum, daysRemaining, amountRemaining,  reportDate, status);
        dailyReports.add(dailyReport);
    }
    @Ignore

    public void addCustomer(int count,int customerID,String customerName,String customerPhoneNo, String dateJoined, String customerStatus) {
        count = customerArrayList.size() + 1;
        Customer customer = new Customer( count,customerID,customerName,customerPhoneNo, dateJoined, customerStatus);
        customerArrayList.add(customer);
    }
    @Ignore

    public void setReportFromDB(ArrayList<CustomerDailyReport> dailyReports) {
        this.dailyReports = dailyReports;
    }
    @Ignore
    public List<Address> gettAddress() {
        return addresses;
    }
    @Ignore
    public Profile getTellerProfile() {
        return tellerProfile;
    }
    @Ignore
    public void setTellerProfile(Profile tellerProfile) {
        this.tellerProfile = tellerProfile;

    }

}
