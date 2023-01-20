package com.skylightapp.MarketClasses;

import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.blongho.country_data.Currency;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Birthday;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.OfficeBranch;
import com.skylightapp.Classes.Payee;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.ProjectSavingsGroup;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Classes.Worker;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.SuperAdmin.Awajima;
import com.skylightapp.Tellers.TellerCash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;


public class MarketBusiness implements Parcelable, Serializable {
    public static final String MARKET_BIZ_TABLE = "BizTable";
    public static final String MARKET_BIZ_ID = "biz_id";
    public static final String MARKET_BIZ_NAME = "BizName";
    public static final String MARKET_BIZ_REG_NO = "BizRegNo";
    public static final String MARKET_BIZ_PHONE_NO = "BizPhoneNo";
    public static final String MARKET_BIZ_EMAIL = "BizEmail";
    public static final String MARKET_BIZ_ADDRESS = "BizAddress";
    public static final String MARKET_BIZ_TYPE = "BizType";
    public static final String MARKET_BIZ_BRANDNAME = "BizBrand";
    public static final String MARKET_BIZ_STATE = "BizState";
    public static final String MARKET_BIZ_PIX = "BizPix";
    public static final String MARKET_BIZ_STATUS = "BizStatus";
    public static final String MARKET_BIZ_PROF_ID = "Biz_Prof_ID";
    public static final String MARKET_BIZ_COUNTRY = "Biz_Country";
    public static final String MARKET_BIZ_CONTINENT = "Biz_Continent";
    public static final String MARKET_BIZ_MARKET_ID = "Biz_MarketBiz_ID";
    public static final String MARKET_BIZ_CUSTOMER_ID = "Biz_Customer_ID";
    public static final String MARKET_BIZ_CAC_DATE = "Biz_CAC_Date";
    public static final String MARKET_BIZ_DESC = "Biz_Desc";
    public static final String MARKET_BIZ_JOINED_D = "Biz_Joining_D";


    public static final String CREATE_BIZ_TABLE = "CREATE TABLE IF NOT EXISTS " + MARKET_BIZ_TABLE + " (" + MARKET_BIZ_PROF_ID + " INTEGER , " +
            MARKET_BIZ_ID + " INTEGER , " + MARKET_BIZ_NAME + " TEXT , " + MARKET_BIZ_BRANDNAME + " TEXT , " + MARKET_BIZ_TYPE + " TEXT , " + MARKET_BIZ_REG_NO + " TEXT , " + MARKET_BIZ_EMAIL + " TEXT , " + MARKET_BIZ_PHONE_NO + " TEXT , " + MARKET_BIZ_ADDRESS + " TEXT , " + MARKET_BIZ_STATE + " TEXT , " + MARKET_BIZ_PIX + " BLOB , " + MARKET_BIZ_STATUS + " TEXT , "+ MARKET_BIZ_COUNTRY + " TEXT , "+ MARKET_BIZ_CONTINENT + " TEXT , "+ MARKET_BIZ_CUSTOMER_ID + " TEXT , " + MARKET_BIZ_CAC_DATE + " TEXT , " + MARKET_BIZ_DESC + " TEXT , "+ MARKET_BIZ_JOINED_D + " TEXT , "+  "PRIMARY KEY(" + MARKET_BIZ_ID + "), " +"FOREIGN KEY(" + MARKET_BIZ_MARKET_ID  + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + ")," +
            "FOREIGN KEY(" + MARKET_BIZ_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    public boolean isAwardOrganization;
    public boolean isRealEstate;
    public boolean isResponseOrganization;
    public boolean isLogisticesAgent;
    public boolean isWholeSaler;
    public long businessID;
    public long ID;
    public long mBProfileID;
    //public int customerID;
    public String businessName;

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
    public List logistics;
    public int numberOfNominations;
    public int numberOfProperties;
    String bizDescription;
    public int numberOfAgents;
    private ArrayList<Account> mBAccounts;
    private ArrayList<Address> addresses;
    private ArrayList<Payee> payees;
    private ArrayList<Customer> customers;
    private ArrayList<MarketBusiness> marketBusinesses;
    private ArrayList<CustomerDailyReport> dailyReports;
    private ArrayList<MarketBizPackage> marketBizPackages;

    private ArrayList<Loan> loans;
    private Transaction transaction;
    private int bizMarketID;
    private Loan loan;
    private Birthday birthday;
    private MarketBusiness marketBusiness;
    private GroupAccount groupAccount;
    private ArrayList<Transaction> transactions;
    private long dbId;
    private String status;
    private String dateOfJoin;
    public String businessBrandName;



    private ArrayList<PaymentCode> mBPaymentCodeArrayList;
    DBHelper dbHelper;
    private ArrayList<PaymentDoc> paymentDocArrayList;

    private ProjectSavingsGroup mBProjectSavingsGroup;
    private ArrayList<ProjectSavingsGroup> projectSavingsGroups;
    private ArrayList<GroupAccount> groupAccounts;
    private ArrayList<StandingOrder> mBStandingOrders;
    private Profile mBusOwner;
    private Profile mBusManager;
    private ArrayList<Market> mBusMarkets;
    private ArrayList<Location> mBusLocS;
    private ArrayList<MarketCustomer> mBusCusS;
    private ArrayList<MarketAdmin> mBusMarketAdminS;
    private ArrayList<BusinessDeal> mBusBizDeals;
    private ArrayList<MarketTranx> mBusMarketTranxS;
    private ArrayList<MarketCommodity> bizMarketCommodities;
    private ArrayList<MarketAnnouncement> bizMarketAnnouncements;
    private ArrayList<MarketBizSub> bizMarketBizSubs;
    private ArrayList<MarketInventory> bizMarketInvTs;
    private ArrayList<MarketStock> bizMarketStocks;
    private ArrayList<BizDealAccount> bizDealAccountArrayList;
    private ArrayList<Stocks> mBStockList;

    private ArrayList<Integer> marketBiz_acctIDs;
    private ArrayList<OfficeBranch> officeBranches;
    private Account marketBizAcct;
    private double marketBizRevenue;
    private Currency marketBizCurrency;
    private UserSuperAdmin mBSuperAdmin;
    private ArrayList<CustomerManager> bizTellers;
    private OfficeBranch MBBranchOffice;
    private ArrayList<Customer> MBCustomers;
    private ArrayList<MarketBusiness> mBMarketBusinesses;
    private Awajima marketBizAwajima;
    private Profile mBusProfile;
    private ArrayList<Profile> bizProfileList;
    private ArrayList<EmergencyReport> marketBizEmergL;
    private Market bizMarket;
    private Set<String> mBTypes;
    private ArrayList<BizDealPartner> bizDealPartners;
    private List<TellerCash> bizTellerCashList;
    private String bizDrivePix;
    private String bizCountry;
    private String bizContinent;
    private String mBDateOfCACReg;
    private int mBLogoInt;
    private ArrayList<TimeLine> mBTimeLineArrayList;
    private ArrayList<AdminUser> mBAdminUsers;
    private ArrayList<Worker> mBWorkers;


    public MarketBusiness(long businessID) {
        super();
        this.businessID=businessID;

    }

    public MarketBusiness(String s, String s1) {
        this.bizDrivePix = s;
        this.businessBrandName = s1;

    }
    public MarketBusiness(int logo, String s, String type) {
        this.businessName = s;
        this.mBLogoInt = logo;
        this.bizType = type;
    }
    public MarketBusiness(Uri logo, String s, String type) {
        this.businessName = s;
        this.bizPicture = logo;
        this.bizType = type;
    }


    public MarketBusiness(long biZID, long mBProfileID, String name, String brandName, String typeBiz, String bizEmail, String bizAddress, String ph_no, String state, String regNo) {
        super();
        this.businessID = biZID;
        this.mBProfileID = mBProfileID;
        this.businessName = name;
        this.businessBrandName = brandName;
        this.bizEmail = bizEmail;
        this.bizAddress = bizAddress;
        this.bizEmail = bizEmail;
        this.bizPhoneNo = ph_no;
        this.bizType = typeBiz;
        this.bizRegNo = bizRegNo;
    }

    public MarketBusiness(long bizID, String name, String brandName, String type, String regNo, String email, String phoneNo, String address, String state, String status, Uri logo) {
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


    protected MarketBusiness(Parcel in) {
        isAwardOrganization = in.readByte() != 0;
        isRealEstate = in.readByte() != 0;
        isResponseOrganization = in.readByte() != 0;
        isLogisticesAgent = in.readByte() != 0;
        businessID = in.readLong();
        ID = in.readLong();
        mBProfileID = in.readLong();
        businessName = in.readString();
        businessBrandName = in.readString();
        bizEmail = in.readString();
        bizState = in.readString();
        bizStatus = in.readString();
        bizPicture = in.readParcelable(Uri.class.getClassLoader());
        bizAddress = in.readString();
        bizPhoneNo = in.readString();
        bizType = in.readString();
        bizRegNo = in.readString();
        profile = in.readParcelable(Profile.class.getClassLoader());
        numberOfNominations = in.readInt();
        numberOfProperties = in.readInt();
        bizDescription = in.readString();
        numberOfAgents = in.readInt();
        mBAccounts = in.createTypedArrayList(Account.CREATOR);
        addresses = in.createTypedArrayList(Address.CREATOR);
        payees = in.createTypedArrayList(Payee.CREATOR);
        customers = in.createTypedArrayList(Customer.CREATOR);
        marketBusinesses = in.createTypedArrayList(MarketBusiness.CREATOR);
        dailyReports = in.createTypedArrayList(CustomerDailyReport.CREATOR);
        marketBizPackages = in.createTypedArrayList(MarketBizPackage.CREATOR);
        loans = in.createTypedArrayList(Loan.CREATOR);
        transaction = in.readParcelable(Transaction.class.getClassLoader());
        loan = in.readParcelable(Loan.class.getClassLoader());
        birthday = in.readParcelable(Birthday.class.getClassLoader());
        marketBusiness = in.readParcelable(MarketBusiness.class.getClassLoader());
        groupAccount = in.readParcelable(GroupAccount.class.getClassLoader());
        transactions = in.createTypedArrayList(Transaction.CREATOR);
        dbId = in.readLong();
        status = in.readString();
        dateOfJoin = in.readString();
        mBTimeLineArrayList = in.createTypedArrayList(TimeLine.CREATOR);
        mBPaymentCodeArrayList = in.createTypedArrayList(PaymentCode.CREATOR);
        paymentDocArrayList = in.createTypedArrayList(PaymentDoc.CREATOR);
        mBProjectSavingsGroup = in.readParcelable(ProjectSavingsGroup.class.getClassLoader());
        projectSavingsGroups = in.createTypedArrayList(ProjectSavingsGroup.CREATOR);
        groupAccounts = in.createTypedArrayList(GroupAccount.CREATOR);
        mBStandingOrders = in.createTypedArrayList(StandingOrder.CREATOR);
        mBusOwner = in.readParcelable(Profile.class.getClassLoader());
        mBusManager = in.readParcelable(Profile.class.getClassLoader());
        mBusMarkets = in.createTypedArrayList(Market.CREATOR);
        mBusLocS = in.createTypedArrayList(Location.CREATOR);
        mBusCusS = in.createTypedArrayList(MarketCustomer.CREATOR);
        mBusBizDeals = in.createTypedArrayList(BusinessDeal.CREATOR);
        mBusMarkets = in.createTypedArrayList(Market.CREATOR);
        mBusBizDeals = in.createTypedArrayList(BusinessDeal.CREATOR);
        bizMarketAnnouncements = in.createTypedArrayList(MarketAnnouncement.CREATOR);
        bizDealAccountArrayList = in.createTypedArrayList(BizDealAccount.CREATOR);
    }

    public static final Creator<MarketBusiness> CREATOR = new Creator<MarketBusiness>() {
        @Override
        public MarketBusiness createFromParcel(Parcel in) {
            return new MarketBusiness(in);
        }

        @Override
        public MarketBusiness[] newArray(int size) {
            return new MarketBusiness[size];
        }
    };




    public void addBizDealAcct(BizDealAccount account) {
        bizDealAccountArrayList = new ArrayList<>();
        bizDealAccountArrayList.add(account);
    }
    public void addTimeLine(TimeLine timeLine) {
        mBTimeLineArrayList = new ArrayList<>();
        mBTimeLineArrayList.add(timeLine);
    }

    public void addWorker(Worker worker) {
        mBWorkers = new ArrayList<>();
        mBWorkers.add(worker);

    }
    public void addAdminUser(AdminUser adminUser) {
        mBAdminUsers = new ArrayList<>();
        mBAdminUsers.add(adminUser);

    }
    public void addSubscription(MarketBizSub subScription) {
        bizMarketBizSubs= new ArrayList<>();
        bizMarketBizSubs.add(subScription);

    }
    public void addBizTeller(CustomerManager teller) {
        bizTellers = new ArrayList<>();
        bizTellers.add(teller);
    }


    public void addOfficeBranch(OfficeBranch officeBranch) {
        officeBranches = new ArrayList<>();
        officeBranches.add(officeBranch);
    }
    public void addBizDealPartner(BizDealPartner bizDealPartner) {
        bizDealPartners = new ArrayList<>();
        bizDealPartners.add(bizDealPartner);
    }
    public void addMarketBizAcctID(int marketBizAcctID) {
        marketBiz_acctIDs = new ArrayList<>();
        marketBiz_acctIDs.add(marketBizAcctID);
    }
    public void addProfileMarketID(int profileMarketID) {
        ArrayList<Integer> profileMarketIDs = new ArrayList<>();
        profileMarketIDs.add(profileMarketID);
    }
    public void addMarketPackage(MarketBizPackage marketBizPackage) {
        marketBiz_acctIDs = new ArrayList<>();
        marketBizPackages.add(marketBizPackage);
    }

    public void addTellerCash(TellerCash tellerCash) {
        bizTellerCashList = new ArrayList<>();
        bizTellerCashList.add(tellerCash);
    }
    public List<TellerCash> getBizTellerCashList() {
        return bizTellerCashList;
    }

    public void setBizTellerCashList(List<TellerCash> bizTellerCashList) {
        this.bizTellerCashList = bizTellerCashList;
    }



    public ArrayList<MarketCommodity> getBizMarketCommodities() {
        return bizMarketCommodities;
    }

    public void setBizMarketCommodities(ArrayList<MarketCommodity> bizMarketCommodities) {
        this.bizMarketCommodities = bizMarketCommodities;
    }
    public ArrayList<MarketBizPackage> getMarketBizPackages() {
        return marketBizPackages;
    }

    public void setMarketBizPackages(ArrayList<MarketBizPackage> marketBizPackages1) {
        this.marketBizPackages = marketBizPackages1;
    }


    public ArrayList<MarketAnnouncement> getBizMarketAnnouncements() {
        return bizMarketAnnouncements;
    }

    public void setBizMarketAnnouncements(ArrayList<MarketAnnouncement> bizMarketAnnouncements) {
        this.bizMarketAnnouncements = bizMarketAnnouncements;
    }

    public ArrayList<MarketBizSub> getBizMarketBizSubs() {
        return bizMarketBizSubs;
    }

    public void setBizMarketBizSubs(ArrayList<MarketBizSub> bizMarketBizSubs) {
        this.bizMarketBizSubs = bizMarketBizSubs;
    }

    public ArrayList<MarketInventory> getBizMarketInvTs() {
        return bizMarketInvTs;
    }

    public void setBizMarketInvTs(ArrayList<MarketInventory> bizMarketInvTs) {
        this.bizMarketInvTs = bizMarketInvTs;
    }

    public ArrayList<MarketStock> getBizMarketStocks() {
        return bizMarketStocks;
    }

    public void setBizMarketStocks(ArrayList<MarketStock> bizMarketStocks) {
        this.bizMarketStocks = bizMarketStocks;
    }



    public ArrayList<ProjectSavingsGroup> getProfile_SavingsGroups() { return projectSavingsGroups; }
    public void setProfile_SavingsGroups(ArrayList<ProjectSavingsGroup> profile_Project_SavingsGroups) {
        this.projectSavingsGroups = profile_Project_SavingsGroups;

    }

    public ArrayList<GroupAccount> getProfile_GroupAccounts() { return groupAccounts; }
    public void setProfile_GroupAccounts(ArrayList<GroupAccount> profile_GroupAccounts) {
        this.groupAccounts = profile_GroupAccounts;

    }
    public void addPPaymentDocument(int id, String title, int customerId, int reportId, Uri documentLink, String status) {
        paymentDocArrayList = new ArrayList<>();
        String docNo = "Doc" + (paymentDocArrayList.size() + 1);
        PaymentDoc paymentDoc = new PaymentDoc(id,title, customerId, reportId,documentLink,status);
        paymentDocArrayList.add(paymentDoc);
    }

    public void addPSavingsGrp(int gsID, String groupName, String adminName, String purpose, double amount, Date startDate, Date endDate, String status) {
        projectSavingsGroups = new ArrayList<>();
        String GSNo = "A" + (projectSavingsGroups.size() + 1);
        ProjectSavingsGroup projectSavingsGroup = new ProjectSavingsGroup(gsID,groupName,adminName, purpose, amount,startDate,endDate,status);
        projectSavingsGroups.add(projectSavingsGroup);
    }

    public MarketBusiness(long id, String name, List nominations, boolean isAwardOrganization, int numberOfNominations) {

        super();
        this.businessName = name;
        this.ID = id;
        this.nominations = nominations;
        this.isAwardOrganization = isAwardOrganization;
        this.numberOfNominations = numberOfNominations;
    }

    public MarketBusiness(long businessID, long mBProfileID, String businessName, String brandName, String bizEmail, String bizAddress, String bizPhoneNo, String bizType, String bizRegNo, String dateOfJoin, String status) {
        super();
        this.businessID = businessID;
        this.mBProfileID = mBProfileID;
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

    public MarketBusiness() {

        super();
    }

    public Profile getTimelineProfile() { return profile; }

    public void setTimelineProfile(Profile timelineProfile) { this.profile = timelineProfile; }
    public long getBusinessID() { return businessID; }
    public void setBizID(long businessID) { this.businessID = businessID; }

    public long getmBProfileID() { return mBProfileID; }
    public void setmBProfileID(long mBProfileID) { this.mBProfileID = mBProfileID; }


    public String getBizStatus() { return bizStatus; }
    public void setBizStatus(String bizStatus) { this.bizStatus = bizStatus; }

    public String getDateOfJoin() { return dateOfJoin; }
    public void setDateOfJoin(String dateOfJoin) { this.dateOfJoin = dateOfJoin; }

    public String getBizDescription() { return bizDescription; }

    public Uri getBizPicture() { return bizPicture; }
    public void setBizPicture(Uri bizPicture) { this.bizPicture = bizPicture; }

    public String getBizBrandname() { return businessBrandName; }
    public void setBizBrandname(String businessBrandName) { this.businessBrandName = businessBrandName; }

    public String getBizEmail() { return bizEmail; }
    public void setBizEmail(String bizEmail) { this.bizEmail = bizEmail; }

    public String getBizState() { return bizState; }
    public void setBizState(String bizState) { this.bizState = bizState; }


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

    public void setBusinessName(String businessName) { this.businessName = businessName; }
    public String getBusinessName() {
        return businessName;
    }

    public void setBizPhoneNo(String bizPhoneNo) { this.bizPhoneNo = bizPhoneNo; }
    public String getBizPhoneNo() { return bizPhoneNo; }

    public String getDescription() {
        return bizDescription;
    }
    public void setBizDescription(String bizDescription) { this.bizDescription = bizDescription; }

    public Profile getmBusOwner() {
        return mBusOwner;
    }

    public void setmBusOwner(Profile mBusOwner) {
        this.mBusOwner = mBusOwner;
    }

    public Profile getmBusManager() {
        return mBusManager;
    }

    public void setmBusManager(Profile mBusManager) {
        this.mBusManager = mBusManager;
    }

    public ArrayList<Market> getmBusMarkets() {
        return mBusMarkets;
    }

    public void setmBusMarkets(ArrayList<Market> mBusMarkets) {
        this.mBusMarkets = mBusMarkets;
    }

    public ArrayList<Location> getmBusLocS() {
        return mBusLocS;
    }

    public void setmBusLocS(ArrayList<Location> mBusLocS) {
        this.mBusLocS = mBusLocS;
    }

    public ArrayList<MarketCustomer> getmBusCusS() {
        return mBusCusS;
    }

    public void setmBusCusS(ArrayList<MarketCustomer> mBusCusS) {
        this.mBusCusS = mBusCusS;
    }

    public ArrayList<MarketAdmin> getmBusMarketAdminS() {
        return mBusMarketAdminS;
    }

    public void setmBusMarketAdminS(ArrayList<MarketAdmin> mBusMarketAdminS) {
        this.mBusMarketAdminS = mBusMarketAdminS;
    }

    public ArrayList<BusinessDeal> getmBusBizDeals() {
        return mBusBizDeals;
    }

    public void setmBusBizDeals(ArrayList<BusinessDeal> mBusBizDeals) {
        this.mBusBizDeals = mBusBizDeals;
    }

    public ArrayList<MarketTranx> getmBusMarketTranxS() {
        return mBusMarketTranxS;
    }

    public void setmBusMarketTranxS(ArrayList<MarketTranx> mBusMarketTranxS) {
        this.mBusMarketTranxS = mBusMarketTranxS;
    }

    public ArrayList<StandingOrder> getmBStandingOrders() {
        return mBStandingOrders;
    }

    public void setmBStandingOrders(ArrayList<StandingOrder> mBStandingOrders) {
        this.mBStandingOrders = mBStandingOrders;
    }

    public ProjectSavingsGroup getmBSavingsGroup() {
        return mBProjectSavingsGroup;
    }

    public void setmBSavingsGroup(ProjectSavingsGroup mBProjectSavingsGroup) {
        this.mBProjectSavingsGroup = mBProjectSavingsGroup;
    }

    public ArrayList<TimeLine> getmBTimeLineArrayList() {
        return mBTimeLineArrayList;
    }

    public void setmBTimeLineArrayList(ArrayList<TimeLine> mBTimeLineArrayList) {
        this.mBTimeLineArrayList = mBTimeLineArrayList;
    }

    public ArrayList<PaymentCode> getmBPaymentCodeArrayList() {
        return mBPaymentCodeArrayList;
    }

    public void setmBPaymentCodeArrayList(ArrayList<PaymentCode> mBPaymentCodeArrayList) {
        this.mBPaymentCodeArrayList = mBPaymentCodeArrayList;
    }

    public ArrayList<BizDealAccount> getBizDealAccountArrayList() {
        return bizDealAccountArrayList;
    }

    public void setBizDealAccountArrayList(ArrayList<BizDealAccount> bizDealAccountArrayList) {
        this.bizDealAccountArrayList = bizDealAccountArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isAwardOrganization ? 1 : 0));
        parcel.writeByte((byte) (isRealEstate ? 1 : 0));
        parcel.writeByte((byte) (isResponseOrganization ? 1 : 0));
        parcel.writeByte((byte) (isLogisticesAgent ? 1 : 0));
        parcel.writeLong(businessID);
        parcel.writeLong(ID);
        parcel.writeLong(mBProfileID);
        parcel.writeString(businessName);
        parcel.writeString(businessBrandName);
        parcel.writeString(bizEmail);
        parcel.writeString(bizState);
        parcel.writeString(bizStatus);
        parcel.writeParcelable(bizPicture, i);
        parcel.writeString(bizAddress);
        parcel.writeString(bizPhoneNo);
        parcel.writeString(bizType);
        parcel.writeString(bizRegNo);
        parcel.writeParcelable(profile, i);
        parcel.writeInt(numberOfNominations);
        parcel.writeInt(numberOfProperties);
        parcel.writeString(bizDescription);
        parcel.writeInt(numberOfAgents);
        parcel.writeTypedList(mBAccounts);
        parcel.writeTypedList(addresses);
        parcel.writeTypedList(payees);
        parcel.writeTypedList(customers);
        parcel.writeTypedList(marketBusinesses);
        parcel.writeTypedList(dailyReports);
        parcel.writeTypedList(marketBizPackages);
        parcel.writeTypedList(loans);
        parcel.writeParcelable(transaction, i);
        parcel.writeParcelable(loan, i);
        parcel.writeParcelable(birthday, i);
        parcel.writeParcelable(marketBusiness, i);
        parcel.writeParcelable(groupAccount, i);
        parcel.writeTypedList(transactions);
        parcel.writeLong(dbId);
        parcel.writeString(status);
        parcel.writeString(dateOfJoin);
        parcel.writeTypedList(mBTimeLineArrayList);
        parcel.writeTypedList(mBPaymentCodeArrayList);
        parcel.writeTypedList(paymentDocArrayList);
        parcel.writeParcelable(mBProjectSavingsGroup, i);
        parcel.writeTypedList(projectSavingsGroups);
        parcel.writeTypedList(groupAccounts);
        parcel.writeTypedList(mBStandingOrders);
        parcel.writeParcelable(mBusOwner, i);
        parcel.writeParcelable(mBusManager, i);
        parcel.writeTypedList(mBusMarkets);
        parcel.writeTypedList(mBusLocS);
        parcel.writeTypedList(mBusCusS);
        parcel.writeTypedList(mBusBizDeals);
        parcel.writeTypedList(bizMarketAnnouncements);
        parcel.writeTypedList(bizDealAccountArrayList);
    }
    public ArrayList<OfficeBranch> getOfficeBranches() {
        return officeBranches;
    }

    public void setOfficeBranches(ArrayList<OfficeBranch> officeBranches) {
        this.officeBranches = officeBranches;
    }

    public int getBizMarketID() {
        return bizMarketID;
    }

    public void setBizMarketID(int bizMarketID) {
        this.bizMarketID = bizMarketID;
    }

    public Account getMarketBizAcct() {

        return marketBizAcct;
    }

    public void setMarketBizAcct(Account marketBizAcct) {
        this.marketBizAcct = marketBizAcct;
    }

    public double getMarketBizRevenue() {
        return marketBizRevenue;
    }

    public void setMarketBizRevenue(double marketBizRevenue) {
        this.marketBizRevenue = marketBizRevenue;
    }

    public Currency getMarketBizCurrency() {
        return marketBizCurrency;
    }

    public void setMarketBizCurrency(Currency marketBizCurrency) {
        this.marketBizCurrency = marketBizCurrency;
    }

    public UserSuperAdmin getmBSuperAdmin() {
        return mBSuperAdmin;
    }

    public void setmBSuperAdmin(UserSuperAdmin mBSuperAdmin) {
        this.mBSuperAdmin = mBSuperAdmin;
    }

    public ArrayList<CustomerManager> getBizTellers() {
        return bizTellers;
    }

    public void setBizTellers(ArrayList<CustomerManager> bizTellers) {
        this.bizTellers = bizTellers;
    }

    public OfficeBranch getMBBranchOffice() {
        return MBBranchOffice;
    }

    public void setMBBranchOffice(OfficeBranch mbBranchOffice) {
        this.MBBranchOffice = mbBranchOffice;
    }

    public ArrayList<Customer> getMBCustomers() {
        return MBCustomers;
    }

    public void setMBCustomers(ArrayList<Customer> mbCustomers) {
        this.MBCustomers = mbCustomers;
    }

    public ArrayList<Stocks> getmBStockList() {
        return mBStockList;
    }

    public void setmBStockList(ArrayList<Stocks> mBStockList) {
        this.mBStockList = mBStockList;
    }



    public Awajima getMarketBizAwajima() {
        return marketBizAwajima;
    }

    public void setMarketBizAwajima(Awajima marketBizAwajima) {
        this.marketBizAwajima = marketBizAwajima;
    }

    public ArrayList<MarketBusiness> getmBMarketBusinesses() {
        return mBMarketBusinesses;
    }

    public void setmBMarketBusinesses(ArrayList<MarketBusiness> mBMarketBusinesses) {
        this.mBMarketBusinesses = mBMarketBusinesses;
    }

    public Profile getmBusProfile() {
        return mBusProfile;
    }

    public void setmBusProfile(Profile mBusProfile) {
        this.mBusProfile = mBusProfile;
    }

    public ArrayList<Profile> getBizProfileList() {
        return bizProfileList;
    }

    public void setBizProfileList(ArrayList<Profile> bizProfileList) {
        this.bizProfileList = bizProfileList;
    }

    public ArrayList<EmergencyReport> getMarketBizEmergL() {
        return marketBizEmergL;
    }

    public void setMarketBizEmergL(ArrayList<EmergencyReport> marketBizEmergL) {
        this.marketBizEmergL = marketBizEmergL;
    }

    public Market getBizMarket() {
        return bizMarket;
    }

    public void setBizMarket(Market bizMarket) {
        this.bizMarket = bizMarket;
    }

    public Set<String> getmBTypes() {
        return mBTypes;
    }

    public void setmBTypes(Set<String> mBTypes) {
        this.mBTypes = mBTypes;
    }


    public ArrayList<BizDealPartner> getBizDealPartners() {
        return bizDealPartners;
    }

    public void setBizDealPartners(ArrayList<BizDealPartner> bizDealPartners) {
        this.bizDealPartners = bizDealPartners;
    }


    public String getBizDrivePix() {
        return bizDrivePix;
    }

    public void setBizDrivePix(String bizDrivePix) {
        this.bizDrivePix = bizDrivePix;
    }

    public void setBizCountry(String bizCountry) {
        this.bizCountry = bizCountry;
    }

    public String getBizCountry() {
        return bizCountry;
    }

    public void setBizContinent(String bizContinent) {
        this.bizContinent = bizContinent;
    }

    public String getBizContinent() {
        return bizContinent;
    }


    public void setmBDateOfCACReg(String mBDateOfCACReg) {
        this.mBDateOfCACReg = mBDateOfCACReg;
    }

    public String getmBDateOfCACReg() {
        return mBDateOfCACReg;
    }

    public int getmBLogoInt() {
        return mBLogoInt;
    }

    public void setmBLogoInt(int mBLogoInt) {
        this.mBLogoInt = mBLogoInt;
    }


    public ArrayList<AdminUser> getmBAdminUsers() {
        return mBAdminUsers;
    }

    public void setmBAdminUsers(ArrayList<AdminUser> mBAdminUsers) {
        this.mBAdminUsers = mBAdminUsers;
    }

    public ArrayList<Worker> getmBWorkers() {
        return mBWorkers;
    }

    public void setmBWorkers(ArrayList<Worker> mBWorkers) {
        this.mBWorkers = mBWorkers;
    }
}
