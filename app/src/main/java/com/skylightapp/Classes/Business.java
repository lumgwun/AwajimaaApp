package com.skylightapp.Classes;

import android.location.Address;
import android.net.Uri;

import com.skylightapp.Database.DBHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Business extends Profile {
    public static final String BIZ_TABLE = "BizTable";
    public static final String BIZ_ID = "id";
    public static final String BIZ_NAME = "BizName";
    public static final String BIZ_REG_NO = "BizRegNo";
    public static final String BIZ_PHONE_NO = "BizPhoneNo";
    public static final String BIZ_EMAIL = "BizEmail";
    public static final String BIZ_ADDRESS = "BizAddress";
    public static final String BIZ_TYPE = "BizType";
    public static final String BIZ_BRANDNAME = "BizBrand";
    public static final String BIZ_STATE = "BizState";
    public static final String BIZ_PIX = "BizPix";
    public static final String BIZ_STATUS = "BizStatus";

    public static final String CREATE_BIZ_TABLE = "CREATE TABLE IF NOT EXISTS " + BIZ_TABLE + " (" + PROFILE_ID + " INTEGER , " +
            BIZ_ID + " INTEGER , " + BIZ_NAME + " TEXT , " +BIZ_BRANDNAME + " TEXT , " + BIZ_TYPE + " TEXT , " + BIZ_REG_NO + " TEXT , " + BIZ_EMAIL + " TEXT , " + BIZ_PHONE_NO + " TEXT , " + BIZ_ADDRESS + " TEXT , " + BIZ_STATE + " TEXT , " + BIZ_PIX + " BLOB , " + BIZ_STATUS + " TEXT , " +  "PRIMARY KEY(" + BIZ_ID  + "), " +
            "FOREIGN KEY(" + PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";



    public boolean isAwardOrganization;
    public boolean isRealEstate;
    public boolean isResponseOrganization;
    public boolean isLogisticesAgent;
    public long businessID;
    public long ID;
    public long profileID;
    public String businessName;
    public String businessBrandName;
    public String bizEmail;
    public String bizState;
    public String bizStatus;
    public Uri bizPicture;
    protected String bizAddress;
    public String bizPhoneNo;
    public String bizType;
    public String bizRegNo;
    public Profile profile;
    public List nominations;
    public List realEstates;
    public List logistics;
    public int numberOfNominations;
    public int numberOfProperties;
    String bizDescription;
    public int numberOfAgents;
    private ArrayList<Account> accounts;
    private ArrayList<Address> addresses;
    private ArrayList<Payee> payees;
    private ArrayList<Customer> customers;
    private ArrayList<Business> businesses;
    private ArrayList<TimeLine> timeLines;
    private ArrayList<CustomerDailyReport> dailyReports;
    private ArrayList<SkyLightPackage> skyLightPackages;
    private ArrayList<Loan> loans;
    private Transaction transaction;
    private Loan loan;
    private Birthday birthday;
    private Business business;
    private GroupAccount groupAccount;
    private ArrayList<Transaction> transactions;
    private long dbId;
    private String status;
    private String dateOfJoin;

    private ArrayList<TimeLine> timeLineArrayList;
    private ArrayList<PaymentCode> paymentCodeArrayList;
    DBHelper dbHelper;
    private ArrayList<PaymentDoc> paymentDocArrayList;

    private SavingsGroup savingsGroup;
    private ArrayList<SavingsGroup> savingsGroups;
    private ArrayList<GroupAccount> groupAccounts;
    private ArrayList<StandingOrder> standingOrders;
    private StandingOrder standingOrder;

    public Business(long businessID) {
        super();
        this.businessID=businessID;

    }

    public Business(long biZID, long profileID, String name, String brandName, String typeBiz, String bizEmail, String bizAddress, String ph_no, String state, String regNo) {
        super();
        this.businessID = biZID;
        this.profileID = profileID;
        this.businessName = name;
        this.businessBrandName = brandName;
        this.bizEmail = bizEmail;
        this.bizAddress = bizAddress;
        this.bizEmail = bizEmail;
        this.bizPhoneNo = ph_no;
        this.bizType = typeBiz;
        this.bizRegNo = bizRegNo;
    }

    public Business(long bizID, String name, String brandName, String type, String regNo, String email, String phoneNo, String address, String state, String status, Uri logo) {
        super();
        this.businessID = bizID;
        this.businessBrandName = brandName;
        this.businessName = name;
        this.bizEmail = email;
        this.bizAddress = address;
        this.bizEmail = bizEmail;
        this.bizPhoneNo = phoneNo;
        this.bizType = type;
        this.bizRegNo = regNo;
        this.bizState = state;
        this.status = status;
        this.bizPicture = logo;

    }


    public ArrayList<SavingsGroup> getSavingsGroups() { return savingsGroups; }
    public void setSavingsGroups(ArrayList<SavingsGroup> savingsGroups) {
        this.savingsGroups = savingsGroups;

    }

    public ArrayList<GroupAccount> getGroupAccounts() { return groupAccounts; }
    public void setGroupAccounts(ArrayList<GroupAccount> groupAccounts) {
        this.groupAccounts = groupAccounts;

    }
    public void addPaymentDocument(int id, String title, int customerId, int reportId, Uri documentLink,String status) {
        paymentDocArrayList = new ArrayList<>();
        String docNo = "Doc" + (paymentDocArrayList.size() + 1);
        PaymentDoc paymentDoc = new PaymentDoc(id,title, customerId, reportId,documentLink,status);
        paymentDocArrayList.add(paymentDoc);
    }

    public void addSavingsGrp(int gsID, String groupName, String adminName, String purpose, double amount, Date startDate, Date endDate, String status) {
        savingsGroups= new ArrayList<>();
        String GSNo = "A" + (savingsGroups.size() + 1);
        SavingsGroup savingsGroup = new SavingsGroup(gsID,groupName,adminName, purpose, amount,startDate,endDate,status);
        savingsGroups.add(savingsGroup);
    }

    public Business(long id, String name, List nominations, boolean isAwardOrganization, int numberOfNominations) {

        super();
        this.businessName = name;
        this.ID = id;
        this.nominations = nominations;
        this.isAwardOrganization = isAwardOrganization;
        this.numberOfNominations = numberOfNominations;
    }

    public Business (long businessID,long profileID, String businessName, String brandName,String bizEmail, String bizAddress, String bizPhoneNo, String bizType, String bizRegNo,String dateOfJoin,String status) {
        super();
        this.businessID = businessID;
        this.profileID = profileID;
        this.businessName = businessName;
        this.bizEmail = bizEmail;
        this.bizAddress = bizAddress;
        this.bizEmail = bizEmail;
        this.bizPhoneNo = bizPhoneNo;
        this.bizType = bizType;
        this.bizRegNo = bizRegNo;
        this.dateOfJoin = dateOfJoin;
        this.status = status;
        this.businessBrandName = brandName;



    }

    public Business() {

        super();
    }

    public Profile getTimelineProfile() { return profile; }

    public void setTimelineProfile(Profile timelineProfile) { this.profile = timelineProfile; }
    public long getBusinessID() { return businessID; }
    public void setBizID(long businessID) { this.businessID = businessID; }

    public long getProfileID() { return profileID; }
    public void setProfileID(long profileID) { this.profileID = profileID; }


    public String getBizStatus() { return bizStatus; }
    public void setBizStatus(String bizStatus) { this.bizStatus = bizStatus; }

    public String getDateOfJoin() { return dateOfJoin; }
    public void setDateOfJoin(String dateOfJoin) { this.dateOfJoin = dateOfJoin; }

    public String getBizDescription() { return bizDescription; }

    public String getBizName() { return businessName; }





    public Uri getBizPicture() { return bizPicture; }
    public void setBizPicture(Uri bizPicture) { this.bizPicture = bizPicture; }

    public String getBizBrandname() { return businessBrandName; }
    public void setBizBrandname(String businessBrandName) { this.businessBrandName = businessBrandName; }

    public String getBizEmail() { return bizEmail; }
    public void setBizEmail(String bizEmail) { this.bizEmail = bizEmail; }

    public String getBizState() { return bizState; }
    public void setBizState(String bizState) { this.bizState = bizState; }



    public String getBusinessBrandName() { return businessBrandName; }
    public void setBusinessBrandName(String businessBrandName) { this.businessBrandName = businessBrandName; }

    public boolean getIsAwardOrganization() { return isAwardOrganization; }
    public void setIsAwardOrganization(boolean bizEmail) { this.isAwardOrganization = isAwardOrganization; }


    public String getBizAddress() { return bizAddress; }
    public void setBizAddress(String bizAddress) { this.bizAddress = bizAddress; }

    public void setBizRegNo(String bizRegNo) { this.bizRegNo = bizRegNo; }
    public String getBizRegNo() { return bizRegNo; }

    public String getBizType() {
        return bizType;
    }
    public void setBizType(String bizType) { this.bizType = bizType; }
    public void setProfilePhoneNumber(String bizPhoneNo) { this.bizPhoneNo = bizPhoneNo; }

    public void setProfileBusinessName(String businessName) { this.businessName = businessName; }
    public String getProfileBusinessName() {
        return businessName;
    }

    public void setBizPhoneNo(String bizPhoneNo) { this.bizPhoneNo = bizPhoneNo; }
    public String getBizPhoneNo() { return bizPhoneNo; }

    public String getDescription() {
        return bizDescription;
    }
    public void setBizDescription(String bizDescription) { this.bizDescription = bizDescription; }

}
