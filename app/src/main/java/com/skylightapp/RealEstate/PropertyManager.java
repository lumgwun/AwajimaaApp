package com.skylightapp.RealEstate;

import android.location.Address;
import android.net.Uri;
import android.os.Parcelable;
import android.telephony.SmsMessage;

import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Business;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Payee;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.SavingsGroup;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Database.DBHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class PropertyManager extends Profile implements Parcelable, Serializable {
    private String firstName;
    private String lastName;
    private String state;
    private String username;
    private String password;
    private String email;
    private String dob1;
    private String gender;
    protected String address;
    private String office;
    private String phoneNumber;
    private String dateJoined;
    private String machine;
    private String identity, businessName;
    protected Uri profilePicture;
    String nin;
    private int role;
    AdminUser adminUser2;
    private ArrayList<Account> accounts;
    private ArrayList<Address> addresses;
    private ArrayList<Payee> payees;
    private ArrayList<Customer> customers;
    private ArrayList<Business> businesses;
    private ArrayList<Properties> properties;
    private ArrayList<TimeLine> timeLines;
    private ArrayList<CustomerDailyReport> dailyReports;
    private ArrayList<SkyLightPackage> skyLightPackages;
    private ArrayList<Loan> loans;
    private Transaction transaction;
    private Loan loan;
    private Birthday birthday;
    private Business business;
    private GroupAccount groupAccount;

    private SavingsGroup savingsGroup;
    private ArrayList<SavingsGroup> savingsGroups;
    private ArrayList<GroupAccount> groupAccounts;
    private ArrayList<StandingOrder> standingOrders;
    private StandingOrder standingOrder;
    private ArrayList<PropertyManager> propertyManagers;

    private CustomerDailyReport customerDailyReport;
    private SkyLightPackage skyLightPackage;
    private Payee payee;
    private ArrayList<SmsMessage.MessageClass> messageClasses;
    SmsMessage.MessageClass messageClass;
    private Customer customer;
    //long customerID=customer.getuID();
    Account account;
    private ArrayList<Transaction> transactions;
    private long dbId;
    private String status;
    private ArrayList<TimeLine> timeLineArrayList;
    private ArrayList<PaymentCode> paymentCodeArrayList;
    DBHelper dbHelper;
    private ArrayList<PaymentDoc> paymentDocArrayList;
    String accountBalance;
    public PropertyManager(long profileID, String firstName, String lastName, String phoneNumber, String email, String gender, String state, String username, String identity, String company) {
        this();
        this.dbId = profileID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dbId = dbId;
        this.username = username;
        this.email = email;
        this.businessName = company;
        this.gender = gender;
        this.identity = identity;
        this.phoneNumber = phoneNumber;
        this.state = state;
    }

    public PropertyManager() {

        super();
    }
    public GroupAccount getGroupAccount() { return groupAccount; }
    public void setGroupAccount(GroupAccount groupAccount) { this.groupAccount = groupAccount; }
    public SavingsGroup getSavingsGroup() { return savingsGroup; }
    public void setSavingsGroup(SavingsGroup savingsGroup) { this.savingsGroup = savingsGroup; }

    public String getProfileEmail() { return email; }
    public void setProfileEmail(String email) { this.email = email; }

    public String getUFirstName() {
        return firstName;
    }
    public String getProfileLastName() {
        return lastName;
    }
    public String getProfileState() {
        return state;
    }
    public String getProfileUsername() {
        return username;
    }
    public String getProfilePassword() {
        return password;
    }

    public String getProfileIdentity() {
        return identity;
    }

    public void setProfileIdentity(String identity) {
        this.identity = identity;

    }
    public String getProfileStatus() { return status; }
    public void setProfileStatus(String status) { this.status = status; }

    public void setProfileDob(String dob1) { this.dob1 = dob1; }
    public void setUGender(String gender) { this.gender = gender; }
    public String getProfileOffice() {
        return office;
    }
    public void setProfileMachine(String machine) { this.machine = machine; }
    public String getProfileMachine() {
        return machine;
    }

    public String getProfilePhoneNumber() { return phoneNumber; }
    public void setProfileOffice(String office) { this.office = office; }
    public void setProfilePhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setProfileAddress(String address) { this.address = address; }
    public void setProfilePicture(Uri profilePicture)
    { this.profilePicture = profilePicture; }
    public void setRole(int role) { this.role = role;
    }

}
