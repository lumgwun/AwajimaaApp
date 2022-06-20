package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.skylightapp.Admins.AdminBankDeposit;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = AdminUser.ADMIN_TABLE)
public class AdminUser extends User implements Parcelable, Serializable {
    @Ignore
    public static final String ADMIN_ID = "admin_id";
    @Ignore
    public static final String ADMIN_SURNAME = "ad_surname";
    @Ignore
    public static final String ADMIN_FIRST_NAME = "ad_first_name";
    @Ignore
    public static final String ADMIN_PHONE_NUMBER = "ad_phone_number";
    @Ignore
    public static final String ADMIN_EMAIL_ADDRESS = "ad_email_address";
    @Ignore
    public static final String ADMIN_DOB = "ad_date_of_birth";
    @Ignore
    public static final String ADMIN_GENDER = "ad_gender";
    @Ignore
    public static final String ADMIN_ADDRESS = "ad_address";
    @Ignore
    public static final String ADMIN_OFFICE = "ad_chosen_office";
    @Ignore
    public static final String ADMIN_DATE_JOINED = "ad_date_joined";
    @Ignore
    public static final String ADMIN_USER_NAME = "ad_user_name";
    @Ignore
    public static final String ADMIN_PASSWORD = "ad_password";
    @Ignore
    public static final String ADMIN_NIN = "ad_nin";
    @Ignore
    public static final String ADMIN_STATUS = "ad_status";
    @Ignore
    public static final String ADMIN_PIX = "ad_picture";
    @Ignore
    public static final String ADMIN_TABLE = "admin_table";
    @Ignore

    public static final String CREATE_ADMIN_TABLE = "CREATE TABLE IF NOT EXISTS " + ADMIN_TABLE + " ( " + ADMIN_ID + " INTEGER   , " + PROFILE_ID + " INTEGER , " + ADMIN_SURNAME + " TEXT  , " +
            ADMIN_FIRST_NAME + " TEXT, " + ADMIN_PHONE_NUMBER + " TEXT, " + ADMIN_EMAIL_ADDRESS + " TEXT, " + ADMIN_DOB + " TEXT, " + ADMIN_GENDER + " TEXT, " +
            ADMIN_ADDRESS + " TEXT, " + ADMIN_OFFICE + " TEXT, " + ADMIN_DATE_JOINED + " TEXT, " + ADMIN_USER_NAME + " TEXT, " + ADMIN_PASSWORD + " TEXT, " +
            ADMIN_NIN + " TEXT,  " + ADMIN_PIX + " TEXT, " + ADMIN_STATUS + " TEXT, " + "FOREIGN KEY(" + PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"PRIMARY KEY(" + ADMIN_ID  + "))";

    private static final long serialVersionUID = 8924708152697574031L;
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    //this.dateJoined = DATE_FORMAT.format(new Date());
    @PrimaryKey(autoGenerate = true)
    private int adminID=1013;
    String adminFirstName;
    String adminSurname;
    String adminGender;
    String adminOffice;
    String adminState;
    String adminLga;
    String adminStatus;
    String adminPhoneNo;
    String adminEmailAddress;
    String adminNin;
    String adminPassword;
    String adminDob;
    String adminDateJoined;
    String adminAddress;
    @Ignore
    private OfficeBranch officeBranch;
    private String adminRole;
    String adminNextOfKin;
    private Uri adminPhoto;
    private String adminUserName;

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public int getAdminID() {
        return adminID;
    }
    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    public String getAdminFirstName() {
        return adminFirstName;
    }
    public void setAdminSurname(String adminSurname) {
        this.adminSurname = adminSurname;
    }
    public String getAdminSurname() {
        return adminSurname;
    }
    public String getAdminGender() {
        return adminGender;
    }
    public void setAdminGender(String gender) {
        this.adminGender = gender;

    }
    public void setAdminDob(String adminDob) {
        this.adminDob = adminDob;
    }

    public String getAdminDob() {
        return adminDob;
    }
    public void setAdminStatus(String adminStatus) {
        this.adminStatus = adminStatus;
    }

    public String getAdminStatus() {
        return adminStatus;
    }
    public String getAdminState() {
        return adminState;
    }
    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }
    public String getAdminLga() {
        return adminLga;
    }

    public void setAdminLga(String adminLga) {
        this.adminLga = adminLga;
    }
    public String getAdminRole() {
        return adminRole;
    }
    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;

    }
    public void setAdminNextOfKin(String adminNextOfKin) {
        this.adminNextOfKin = adminNextOfKin;
    }

    public String getAdminNextOfKin() {
        return adminNextOfKin;
    }

    public String getAdminUserName() {
        return adminUserName;
    }
    public void setAdminUserName(String uUserName) {
        this.adminUserName = uUserName;

    }
    public String getAdminAddress() {
        return adminAddress;
    }
    public void setAdminAddress(String address) {
        this.adminAddress = address;

    }
    public String getAdminDateJoined() {
        return adminDateJoined;
    }
    public void setAdminDateJoined(String dateJoined) {
        this.adminDateJoined = dateJoined;

    }
    public String getAdminPassword() {
        return adminPassword;
    }
    public void setAdminPassword(String password) {
        this.adminPassword = password;

    }
    public String getAdminNin() {
        return adminNin;
    }
    public void setAdminNin(String adminNin) {
        this.adminNin = adminNin;

    }
    public String getAdminEmailAddress() {
        return adminEmailAddress;
    }
    public void setAdminEmailAddress(String emailAddress) {
        this.adminEmailAddress = emailAddress;

    }
    public String getAdminPhoneNo() {
        return adminPhoneNo;
    }
    public void setAdminPhoneNo(String adminPhoneNo) {
        this.adminPhoneNo = adminPhoneNo;

    }
    public String getAdminOffice() {
        return adminOffice;
    }
    public void setAdminOffice(String adminOffice) {
        this.adminOffice = adminOffice;

    }
    public void setAdminPhoto(Uri adminPhoto) {
        this.adminPhoto = adminPhoto;
    }
    public Uri getAdminPhoto() {
        return adminPhoto;
    }
    @Ignore
    ArrayList<TellerReport> tellerReportArrayList;
    @Ignore
    ArrayList<Transaction> transactions;
    @Ignore
    ArrayList<AdminBankDeposit> adminBankDeposits;
    @Ignore
    ArrayList<Payment> payments;
    @Ignore
    boolean alreadyAdded;
    @Ignore
    private ArrayList<TimeLine> timeLines;
    @Ignore
    private transient boolean authenticated;

    public AdminUser() {

        super();
    }


    public AdminUser(JSONObject jsonObject) {

        super();
    }


    public AdminUser(int userId, int profileID, String adminSurname, String adminFirstName, String adminGender) {
        super();
        this.adminSurname = adminSurname;
        this.adminFirstName = adminFirstName;
        this.adminGender = adminGender;
        this.adminID = userId;
        tellerReportArrayList = new ArrayList<>();
        payments = new ArrayList<>();
        transactions = new ArrayList<>();

    }


    public AdminUser(int uID, String adminSurname, String adminFirstName, String customerPhoneNumber, String dateOfBirth, String customerEmailAddress, String customerAddress, String aGender, String customerOffice, String customerState, Uri profilePicture, String adminDateJoined, String adminUserName, String adminPassword) {
        super();
        this.adminSurname = adminSurname;
        this.adminFirstName = adminFirstName;
        this.adminPhoneNo = customerPhoneNumber;
        this.adminGender = aGender;
        this.adminUserName = adminUserName;

        this.adminID = Math.toIntExact(uID);
    }
    public AdminUser(long uID, String adminSurname) {
        super();
        this.adminSurname = adminSurname;
        this.adminID = Math.toIntExact(uID);
    }

    public AdminUser(int adminID, String adminSurname, String adminFirstName, String phoneNumber, String adminEmailAddress, String adminNin, String adminDob, String adminGender, String adminAddress, String adminUserName, String adminPassword, String adminOffice, String adminDateJoined, Uri pix, String adminStatus) {
        super();
        this.adminID = Math.toIntExact(adminID);
        this.adminSurname = adminSurname;
        this.adminFirstName = adminFirstName;
        this.adminPhoneNo = phoneNumber;
        this.adminDob = adminDob;
        this.adminGender = adminGender;
        this.adminUserName = adminUserName;
        this.adminOffice = adminOffice;
        this.adminEmailAddress = adminEmailAddress;
        this.adminNin = adminNin;
        this.adminAddress = adminAddress;
        this.adminPassword = adminPassword;
        this.adminDateJoined = adminDateJoined;
        this.adminPhoto = pix;
        this.adminStatus = adminStatus;
    }

    public AdminUser(int adminID, String adminSurname, String adminFirstName) {
        super();
        this.adminFirstName = adminFirstName;
        this.adminSurname = adminSurname;
        this.adminID = Math.toIntExact(adminID);
        this.adminID = adminID;
    }




    /*private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }*/
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




    @Ignore
    public AdminUser(int uid, String adminSurname, String adminFirstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, String adminUserName, String adminPassword) {
        super();
        this.adminID = uid;
        this.adminSurname = adminSurname;
        this.adminFirstName = adminFirstName;
        this.adminGender = customerGender;
        this.adminUserName = adminUserName;
        this.adminOffice = customerOffice;
    }
    @Ignore
    public void addReport(int dbaseID,int reportID,String date,String status) {
        ArrayList<TellerReport> tellerReports = null;
        reportID = tellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(reportID, dbaseID,date,status);
        tellerReportArrayList.add(dailyReport);
    }
    @Ignore
    public void addPayment(int dbaseID,int reportID,Date date,String status) {
        ArrayList<Payment> payments = null;
        reportID = payments.size() + 1;
        Payment payment = new Payment(reportID, dbaseID,date,status);
        payments.add(payment);
    }
    @Ignore

    public void setTransactions(ArrayList<Transaction> transactionArrayList) {
        this.transactions = transactionArrayList;
    }


    @Ignore

    public void addTransaction(int transactionID) {
        ArrayList<Transaction> transactions = null;
        transactionID = transactions.size() + 1;
        Transaction transaction = new Transaction();
        transactions.add(transaction);
    }
    @Ignore
    public void addTellerReport(int dbaseID, int reportID, String date, double balance, String status) {
        ArrayList<TellerReport> tellerReports = null;
        reportID = tellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(reportID, dbaseID,date,balance,status);
        tellerReportArrayList.add(dailyReport);
    }
    @Ignore
    public void addTellerReport(int keyExtraReportId, int tellerReportID, String officeBranch, double amountEntered, double amountExpected, int noOfSavings, String reportDate, String status) {
        ArrayList<TellerReport> tellerReports = null;
        keyExtraReportId = tellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(keyExtraReportId, officeBranch,amountEntered,noOfSavings,reportDate);
        tellerReportArrayList.add(dailyReport);
    }
    @Ignore

    public ArrayList<Transaction> getTransactions() { return transactions; }
    @Ignore
    public void addTimeLine(String tittle,String timelineDetails) {
        timeLines= new ArrayList<>();
        String history = "History" + (timeLines.size() + 1);
        TimeLine timeLine = new TimeLine(tittle,timelineDetails);
        timeLines.add(timeLine);
    }
    @Ignore

    public void addTellerReport(int tellerReportID, String officeBranch, double amountEntered, double amountExpected, int noOfSavings, String reportDate, String status) {
        ArrayList<TellerReport> tellerReports = null;
        int keyExtraReportId = tellerReportArrayList.size() + 1;
        TellerReport dailyReport = new TellerReport(keyExtraReportId, officeBranch,amountEntered,noOfSavings,reportDate);
        tellerReportArrayList.add(dailyReport);
    }
    @Ignore

    public void addDepositReport(int keyExtraReportId, String adminName,String officeBranch, String selectedBank, double amountEntered, Date reportDate) {
        ArrayList<AdminBankDeposit> adminBankDeposits = null;
        keyExtraReportId = adminBankDeposits.size() + 1;
        AdminBankDeposit adminBankDeposit = new AdminBankDeposit(keyExtraReportId, officeBranch,selectedBank,amountEntered,reportDate);
        adminBankDeposits.add(adminBankDeposit);
    }
    @Ignore

    public OfficeBranch getOfficeBranch() {
        return officeBranch;
    }
    @Ignore
    public void setOfficeBranch(OfficeBranch officeBranch) {
        this.officeBranch = officeBranch;
    }
}
