package com.skylightapp.MarketClasses;

import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.blongho.country_data.Currency;
import com.skylightapp.Classes.Account;
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
import com.skylightapp.Classes.SavingsGroup;
import com.skylightapp.Classes.SkyLightPackage;
import com.skylightapp.Classes.StandingOrder;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Classes.UserSuperAdmin;
import com.skylightapp.Database.DBHelper;
import com.skylightapp.Markets.MarketTranx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    public static final String CREATE_BIZ_TABLE = "CREATE TABLE IF NOT EXISTS " + MARKET_BIZ_TABLE + " (" + MARKET_BIZ_PROF_ID + " INTEGER , " +
            MARKET_BIZ_ID + " INTEGER , " + MARKET_BIZ_NAME + " TEXT , " + MARKET_BIZ_BRANDNAME + " TEXT , " + MARKET_BIZ_TYPE + " TEXT , " + MARKET_BIZ_REG_NO + " TEXT , " + MARKET_BIZ_EMAIL + " TEXT , " + MARKET_BIZ_PHONE_NO + " TEXT , " + MARKET_BIZ_ADDRESS + " TEXT , " + MARKET_BIZ_STATE + " TEXT , " + MARKET_BIZ_PIX + " BLOB , " + MARKET_BIZ_STATUS + " TEXT , "+ MARKET_BIZ_COUNTRY + " TEXT , "+ MARKET_BIZ_CONTINENT + " TEXT , "+ MARKET_BIZ_CUSTOMER_ID + " TEXT , " +  "PRIMARY KEY(" + MARKET_BIZ_ID + "), " +"FOREIGN KEY(" + MARKET_BIZ_MARKET_ID  + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + ")," +
            "FOREIGN KEY(" + MARKET_BIZ_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    public boolean isAwardOrganization;
    public boolean isRealEstate;
    public boolean isResponseOrganization;
    public boolean isLogisticesAgent;
    public long businessID;
    public long ID;
    public long profileID;
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
    private ArrayList<TimeLine> marketBizTimeLines;
    private ArrayList<CustomerDailyReport> dailyReports;
    private ArrayList<SkyLightPackage> skyLightPackages;
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

    private ArrayList<TimeLine> mBTimeLineArrayList;
    private ArrayList<PaymentCode> mBPaymentCodeArrayList;
    DBHelper dbHelper;
    private ArrayList<PaymentDoc> paymentDocArrayList;

    private SavingsGroup mBSavingsGroup;
    private ArrayList<SavingsGroup> savingsGroups;
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
    private ArrayList<MarketCustomer> busCusS;
    private ArrayList<MarketCommodity> bizMarketCommodities;
    private ArrayList<MarketAnnouncement> bizMarketAnnouncements;
    private ArrayList<MarketBizSubScription> bizMarketBizSubs;
    private ArrayList<MarketInventory> bizMarketInvTs;
    private ArrayList<MarketStock> bizMarketStocks;
    private ArrayList<BizDealAccount> bizDealAccountArrayList;

    private ArrayList<Integer> marketBiz_acctIDs;
    private ArrayList<OfficeBranch> officeBranches;
    private Account marketBizAcct;
    private double marketBizRevenue;
    private Currency marketBizCurrency;
    private UserSuperAdmin mBSuperAdmin;
    private ArrayList<CustomerManager> bizTellers;


    public MarketBusiness(long businessID) {
        super();
        this.businessID=businessID;

    }

    public MarketBusiness(long biZID, long profileID, String name, String brandName, String typeBiz, String bizEmail, String bizAddress, String ph_no, String state, String regNo) {
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
        profileID = in.readLong();
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
        marketBizTimeLines = in.createTypedArrayList(TimeLine.CREATOR);
        dailyReports = in.createTypedArrayList(CustomerDailyReport.CREATOR);
        skyLightPackages = in.createTypedArrayList(SkyLightPackage.CREATOR);
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
        mBSavingsGroup = in.readParcelable(SavingsGroup.class.getClassLoader());
        savingsGroups = in.createTypedArrayList(SavingsGroup.CREATOR);
        groupAccounts = in.createTypedArrayList(GroupAccount.CREATOR);
        mBStandingOrders = in.createTypedArrayList(StandingOrder.CREATOR);
        mBusOwner = in.readParcelable(Profile.class.getClassLoader());
        mBusManager = in.readParcelable(Profile.class.getClassLoader());
        mBusMarkets = in.createTypedArrayList(Market.CREATOR);
        mBusLocS = in.createTypedArrayList(Location.CREATOR);
        mBusCusS = in.createTypedArrayList(MarketCustomer.CREATOR);
        mBusBizDeals = in.createTypedArrayList(BusinessDeal.CREATOR);
        mBusMarkets = in.createTypedArrayList(Market.CREATOR);
        busCusS = in.createTypedArrayList(MarketCustomer.CREATOR);
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
    public void addBizTeller(CustomerManager teller) {
        bizTellers = new ArrayList<>();
        bizTellers.add(teller);
    }


    public void addOfficeBranch(OfficeBranch officeBranch) {
        officeBranches = new ArrayList<>();
        officeBranches.add(officeBranch);
    }
    public void addMarketBizAcctID(int marketBizAcctID) {
        marketBiz_acctIDs = new ArrayList<>();
        marketBiz_acctIDs.add(marketBizAcctID);
    }
    public void addProfileMarketID(int profileMarketID) {
        ArrayList<Integer> profileMarketIDs = new ArrayList<>();
        profileMarketIDs.add(profileMarketID);
    }

    public ArrayList<MarketCustomer> getBusCusS() {
        return busCusS;
    }

    public void setBusCusS(ArrayList<MarketCustomer> busCusS) {
        this.busCusS = busCusS;
    }

    public ArrayList<MarketCommodity> getBizMarketCommodities() {
        return bizMarketCommodities;
    }

    public void setBizMarketCommodities(ArrayList<MarketCommodity> bizMarketCommodities) {
        this.bizMarketCommodities = bizMarketCommodities;
    }


    public ArrayList<MarketAnnouncement> getBizMarketAnnouncements() {
        return bizMarketAnnouncements;
    }

    public void setBizMarketAnnouncements(ArrayList<MarketAnnouncement> bizMarketAnnouncements) {
        this.bizMarketAnnouncements = bizMarketAnnouncements;
    }

    public ArrayList<MarketBizSubScription> getBizMarketBizSubs() {
        return bizMarketBizSubs;
    }

    public void setBizMarketBizSubs(ArrayList<MarketBizSubScription> bizMarketBizSubs) {
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



    public ArrayList<SavingsGroup> getProfile_SavingsGroups() { return savingsGroups; }
    public void setProfile_SavingsGroups(ArrayList<SavingsGroup> profile_SavingsGroups) {
        this.savingsGroups = profile_SavingsGroups;

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
        savingsGroups= new ArrayList<>();
        String GSNo = "A" + (savingsGroups.size() + 1);
        SavingsGroup savingsGroup = new SavingsGroup(gsID,groupName,adminName, purpose, amount,startDate,endDate,status);
        savingsGroups.add(savingsGroup);
    }

    public MarketBusiness(long id, String name, List nominations, boolean isAwardOrganization, int numberOfNominations) {

        super();
        this.businessName = name;
        this.ID = id;
        this.nominations = nominations;
        this.isAwardOrganization = isAwardOrganization;
        this.numberOfNominations = numberOfNominations;
    }

    public MarketBusiness(long businessID, long profileID, String businessName, String brandName, String bizEmail, String bizAddress, String bizPhoneNo, String bizType, String bizRegNo, String dateOfJoin, String status) {
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

    public MarketBusiness() {

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

    public SavingsGroup getmBSavingsGroup() {
        return mBSavingsGroup;
    }

    public void setmBSavingsGroup(SavingsGroup mBSavingsGroup) {
        this.mBSavingsGroup = mBSavingsGroup;
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
        parcel.writeLong(profileID);
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
        parcel.writeTypedList(marketBizTimeLines);
        parcel.writeTypedList(dailyReports);
        parcel.writeTypedList(skyLightPackages);
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
        parcel.writeParcelable(mBSavingsGroup, i);
        parcel.writeTypedList(savingsGroups);
        parcel.writeTypedList(groupAccounts);
        parcel.writeTypedList(mBStandingOrders);
        parcel.writeParcelable(mBusOwner, i);
        parcel.writeParcelable(mBusManager, i);
        parcel.writeTypedList(mBusMarkets);
        parcel.writeTypedList(mBusLocS);
        parcel.writeTypedList(mBusCusS);
        parcel.writeTypedList(mBusBizDeals);
        parcel.writeTypedList(busCusS);
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
}
