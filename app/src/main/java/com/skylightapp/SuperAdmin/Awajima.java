package com.skylightapp.SuperAdmin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.quickblox.users.model.QBUser;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.GroupAccount;
import com.skylightapp.Classes.GroupSavings;
import com.skylightapp.Classes.Payee;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.Stocks;
import com.skylightapp.MapAndLoc.EmergReportNext;
import com.skylightapp.MapAndLoc.EmergencyReport;
import com.skylightapp.MapAndLoc.Fence;
import com.skylightapp.MapAndLoc.FenceEvent;
import com.skylightapp.MapAndLoc.State;
import com.skylightapp.MarketClasses.BizDealAccount;
import com.skylightapp.MarketClasses.BusinessDeal;
import com.skylightapp.MarketClasses.BusinessDealDoc;
import com.skylightapp.MarketClasses.BusinessDealLoan;
import com.skylightapp.MarketClasses.BusinessDealSub;
import com.skylightapp.MarketClasses.BusinessOthers;
import com.skylightapp.MarketClasses.Market;
import com.skylightapp.MarketClasses.MarketAdmin;
import com.skylightapp.MarketClasses.MarketAnnouncement;
import com.skylightapp.MarketClasses.MarketBizDonor;
import com.skylightapp.MarketClasses.MarketBizPartner;
import com.skylightapp.MarketClasses.MarketBizSub;
import com.skylightapp.MarketClasses.MarketBusiness;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Awajima implements Parcelable, Serializable {
    private Profile awajimaProf;
    private Account awajimaAccounts;
    private ArrayList<Payee> awajimaPayees;
    private ArrayList<TimeLine> awajimaTimeLines;
    private ArrayList<Customer> awajimaCustomers;
    private ArrayList<Stocks> skyStocks;
    private ArrayList<CustomerManager> skyTellers;
    private ArrayList<AdminUser> skyAdminUsers;
    private ArrayList<AdminBankDeposit> skyAdminBankDeposits;
    private ArrayList<StockTransfer> skyStockTransfers;
    private ArrayList<Transaction> skyTransactions;
    private ArrayList<LatLng> latLngs;
    private ArrayList<Payment> skyPayments;
    private ArrayList<PaymentCode> skyPaymentCodes;
    private ArrayList<PaymentDoc> SKYPaymentDocs;
    private ArrayList<MarketBusiness> AwajimaMarketBusinesses;
    private ArrayList<Market> awajimaMarkets;
    private ArrayList<EmergencyReport> awajimaEmergencies;
    private ArrayList<EmergReportNext> awajimaEmergNexts;
    private ArrayList<MarketAdmin> awajimaMarketAdmins;
    private ArrayList<MarketAnnouncement> awajimaAnnouncements;
    private ArrayList<MarketBizDonor> awajimaMarketDonors;
    private ArrayList<MarketBizPartner> awajimaMarketPartners;
    private ArrayList<BusinessDealSub> awajimaBizSubDeals;
    private ArrayList<MarketBizSub> awajimaSubs;
    private ArrayList<BusinessDeal> awajimaBizDeals;
    private ArrayList<BusinessDealDoc> awajimaBizDealDocs;
    private ArrayList<BusinessDealLoan> awajimaBizDealLoans;
    private ArrayList<BusinessOthers> awajimaOtherBizS;
    private ArrayList<BizDealAccount> awajimaBizAccts;
    private ArrayList<GroupAccount> awajimaGrpAccts;
    private ArrayList<GroupSavings> awajimaGrpSavings;
    private ArrayList<Profile> awajimaProfiles;
    private ArrayList<State> awajimaStates;
    private ArrayList<QBUser> awajimaQBUsers;
    private int awajimaID;
    private String awajimaOffice;
    private ArrayList<Fence> awajimaFenceArrayList;
    private ArrayList<FenceEvent> awajimaFenceEvents;
    private ArrayList<MarketBusiness> awajimaBizS;
    private ArrayList<Account> awajimaAccts;

    public Awajima() {
        super();

    }

    protected Awajima(Parcel in) {
        awajimaProf = in.readParcelable(Profile.class.getClassLoader());
        awajimaAccounts = in.readParcelable(Account.class.getClassLoader());
        awajimaPayees = in.createTypedArrayList(Payee.CREATOR);
        awajimaTimeLines = in.createTypedArrayList(TimeLine.CREATOR);
        awajimaCustomers = in.createTypedArrayList(Customer.CREATOR);
        skyStocks = in.createTypedArrayList(Stocks.CREATOR);
        skyTellers = in.createTypedArrayList(CustomerManager.CREATOR);
        skyAdminUsers = in.createTypedArrayList(AdminUser.CREATOR);
        skyAdminBankDeposits = in.createTypedArrayList(AdminBankDeposit.CREATOR);
        skyStockTransfers = in.createTypedArrayList(StockTransfer.CREATOR);
        skyTransactions = in.createTypedArrayList(Transaction.CREATOR);
        latLngs = in.createTypedArrayList(LatLng.CREATOR);
        skyPayments = in.createTypedArrayList(Payment.CREATOR);
        skyPaymentCodes = in.createTypedArrayList(PaymentCode.CREATOR);
        SKYPaymentDocs = in.createTypedArrayList(PaymentDoc.CREATOR);
        AwajimaMarketBusinesses = in.createTypedArrayList(MarketBusiness.CREATOR);
        awajimaMarkets = in.createTypedArrayList(Market.CREATOR);
        awajimaEmergencies = in.createTypedArrayList(EmergencyReport.CREATOR);
        awajimaEmergNexts = in.createTypedArrayList(EmergReportNext.CREATOR);
        awajimaAnnouncements = in.createTypedArrayList(MarketAnnouncement.CREATOR);
        awajimaBizSubDeals = in.createTypedArrayList(BusinessDealSub.CREATOR);
        awajimaBizDeals = in.createTypedArrayList(BusinessDeal.CREATOR);
        awajimaBizDealDocs = in.createTypedArrayList(BusinessDealDoc.CREATOR);
        awajimaBizDealLoans = in.createTypedArrayList(BusinessDealLoan.CREATOR);
        awajimaOtherBizS = in.createTypedArrayList(BusinessOthers.CREATOR);
        awajimaBizAccts = in.createTypedArrayList(BizDealAccount.CREATOR);
        awajimaGrpAccts = in.createTypedArrayList(GroupAccount.CREATOR);
        awajimaGrpSavings = in.createTypedArrayList(GroupSavings.CREATOR);
    }

    public static final Creator<Awajima> CREATOR = new Creator<Awajima>() {
        @Override
        public Awajima createFromParcel(Parcel in) {
            return new Awajima(in);
        }

        @Override
        public Awajima[] newArray(int size) {
            return new Awajima[size];
        }
    };

    public void setSkyCustomerManagers(ArrayList<CustomerManager> customerManagers) {
        this.skyTellers = customerManagers;
    }
    public ArrayList<CustomerManager> getTellers() { return skyTellers; }

    public void setSkyAdminUsers(ArrayList<AdminUser> skyAdminUsers) {
        this.skyAdminUsers = skyAdminUsers;
    }
    public ArrayList<AdminUser> getSkyAdminUsers() { return skyAdminUsers; }


    public void setSkyAdminBankDeposits(ArrayList<AdminBankDeposit> skyAdminBankDeposits) {
        this.skyAdminBankDeposits = skyAdminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminBankDeposit() { return skyAdminBankDeposits; }

    public void setStockTransfers(ArrayList<StockTransfer> stockTransferArrayList) {
        this.skyStockTransfers = stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStockTransfers() { return skyStockTransfers; }

    public void setPayments(ArrayList<Payment> paymentArrayList) {
        this.skyPayments = paymentArrayList;
    }
    public ArrayList<Payment> getPayments() { return skyPayments; }
    public void setPaymentCodes(ArrayList<PaymentCode> paymentCodeArrayList) {
        this.skyPaymentCodes = paymentCodeArrayList;
    }
    public ArrayList<PaymentCode> getPaymentCodes() { return skyPaymentCodes; }
    public void setPaymentDocuments(ArrayList<PaymentDoc> paymentDocArrayList) {
        this.SKYPaymentDocs = paymentDocArrayList;
    }
    public ArrayList<PaymentDoc> getSkyPaymentDocs() { return SKYPaymentDocs; }

    public ArrayList<Customer> getAwajimaCustomers() { return awajimaCustomers;
    }
    public void setSkyTransactions(ArrayList<Transaction> skyTransactions) {
        this.skyTransactions = skyTransactions;

    }
    public ArrayList<Payee> getAwajimaPayees() { return awajimaPayees; }



    public ArrayList<Transaction> getSkyTransactions() { return skyTransactions;
    }
    public ArrayList<LatLng> getProfileLocations() {
        return latLngs;
    }
    public void setAwajimaTimeLines(ArrayList<TimeLine> timeLineArrayList) {
        this.awajimaTimeLines = timeLineArrayList;
    }
    public ArrayList<TimeLine> getAwajimaTimeLines() { return awajimaTimeLines; }

    public Profile getAwajimaProf() {
        return awajimaProf;
    }

    public void setAwajimaProf(Profile awajimaProf) {
        this.awajimaProf = awajimaProf;
    }

    public Account getAwajimaAccounts() {
        return awajimaAccounts;
    }

    public void setAwajimaAccounts(Account awajimaAccounts) {
        this.awajimaAccounts = awajimaAccounts;
    }

    public void addPayment(String type, double totalToWithdraw, Date date, long paymentCode, String acctType, String office, String status) {
        ArrayList<Payment> paymentArrayList = null;
        if (paymentArrayList != null) {
            String paymentNo = "Payment:" + (paymentArrayList.size() + 1);
        }
        Payment payment = new Payment(type,totalToWithdraw, date,paymentCode,acctType,office,status);
        paymentArrayList.add(payment);

    }
    public void addNewCustomer(Customer customer) {
        awajimaCustomers = new ArrayList<>();
        awajimaCustomers.add(customer);

    }
    public void addNewAcct(Account account) {
        awajimaAccts = new ArrayList<>();
        awajimaAccts.add(account);

    }
    public void addProfile(Profile profile) {
        awajimaProfiles = new ArrayList<>();
        awajimaProfiles.add(profile);

    }
    public void addQBUsers(QBUser qbUser) {
        awajimaQBUsers = new ArrayList<>();
        awajimaQBUsers.add(qbUser);

    }

    public void addState(State state) {
        awajimaStates = new ArrayList<>();
        awajimaStates.add(state);

    }
    public void addEmergReport(int sReportID, int profileID1, long bizID, String reportDate, String oil_spillage_report, String localityString, String subUrb, String selectedLGA, String selectedOilCompany, String address, String strngLatLng, String moreInfo, String iamAvailable) {
        awajimaEmergencies = new ArrayList<>();
        EmergencyReport emergencyReport = new EmergencyReport(sReportID, profileID1, bizID, reportDate, oil_spillage_report, localityString, subUrb, selectedLGA, selectedOilCompany,  address, strngLatLng, moreInfo, iamAvailable);
        awajimaEmergencies.add(emergencyReport);

    }

    public void addStocksAll(int stocksID, String selectedStockPackage, String uStockType, String uStockModel, String uStockColor, String uStockSize, int uStockQuantity, String selectedOfficeBranch, double uStockPricePerUnit, String stockDate) {
        skyStocks = new ArrayList<>();
        int  stocksNo = skyStocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,selectedStockPackage, uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);
        skyStocks.add(stocks1);

    }
    public void addStocks(int stocksID, String stocksName, String uStockType,  int uStockQuantity, long stockCode,String selectedOfficeBranch, String stockDate,String status) {
        skyStocks = new ArrayList<>();
        int  stocksNo = skyStocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,stocksName,uStockType,uStockQuantity,stockCode,stockDate,status);
        skyStocks.add(stocks1);

    }
    public void addCustomer(int uID, String surname, String firstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String dateJoined, String userName, String password) {
        awajimaCustomers = new ArrayList<>();
        String CusNo = "C" + (awajimaCustomers.size() + 1);
        Customer customer = new Customer(uID,surname, firstName, customerPhoneNumber,customerEmailAddress,customerAddress,customerGender,customerOffice,customerState,profilePicture,dateJoined,userName,password);
        awajimaCustomers.add(customer);

    }
    public void addTimeLine(int id,String tittle,String timelineDetails) {
        awajimaTimeLines = new ArrayList<>();
        String history = "History" + (awajimaTimeLines.size() + 1);
        TimeLine timeLine = new TimeLine(id, tittle,timelineDetails);
        awajimaTimeLines.add(timeLine);
    }
    public void addTimeLine(String tittle,String timelineDetails) {
        awajimaTimeLines = new ArrayList<>();
        String history = "History" + (awajimaTimeLines.size() + 1);
        TimeLine timeLine = new TimeLine( tittle,timelineDetails);
        awajimaTimeLines.add(timeLine);
    }
    public void addTransaction(long transactionId, String surname, String firstName, String customerPhoneNumber, double amount,String accountNumber,String description,String date,String type) {
        skyTransactions = new ArrayList<>();
        String transactionCount = "C" + (skyTransactions.size() + 1);
        Transaction transaction1 = new Transaction(transactionId,surname, firstName, customerPhoneNumber,amount,accountNumber,description,date,type);
        skyTransactions.add(transaction1);
    }
    public void addPayee(String payeeName) {
        awajimaPayees = new ArrayList<>();
        int payeeID = awajimaPayees.size() + 1;
        Payee payee = new Payee(payeeID, payeeName);
        awajimaPayees.add(payee);
    }
    public void addMarket(Market market) {
        awajimaMarkets = new ArrayList<>();
        awajimaMarkets.add(market);

    }
    public void addFence(Fence fence) {
        awajimaFenceArrayList = new ArrayList<>();
        awajimaFenceArrayList.add(fence);

    }
    public void addFenceEvent(FenceEvent fenceEvent) {
        awajimaFenceEvents = new ArrayList<>();
        awajimaFenceEvents.add(fenceEvent);

    }



    public ArrayList<MarketBusiness> getAwajimaMarketBusinesses() {
        return AwajimaMarketBusinesses;
    }

    public void setAwajimaMarketBusinesses(ArrayList<MarketBusiness> awajimaMarketBusinesses) {
        AwajimaMarketBusinesses = awajimaMarketBusinesses;
    }

    public ArrayList<Market> getAwajimaMarkets() {
        return awajimaMarkets;
    }

    public void setAwajimaMarkets(ArrayList<Market> awajimaMarkets) {
        this.awajimaMarkets = awajimaMarkets;
    }


    public ArrayList<EmergencyReport> getAwajimaEmergencies() {
        return awajimaEmergencies;
    }

    public void setAwajimaEmergencies(ArrayList<EmergencyReport> awajimaEmergencies) {
        this.awajimaEmergencies = awajimaEmergencies;
    }

    public ArrayList<EmergReportNext> getAwajimaEmergNexts() {
        return awajimaEmergNexts;
    }

    public void setAwajimaEmergNexts(ArrayList<EmergReportNext> awajimaEmergNexts) {
        this.awajimaEmergNexts = awajimaEmergNexts;
    }

    public ArrayList<MarketAdmin> getAwajimaMarketAdmins() {
        return awajimaMarketAdmins;
    }

    public void setAwajimaMarketAdmins(ArrayList<MarketAdmin> awajimaMarketAdmins) {
        this.awajimaMarketAdmins = awajimaMarketAdmins;
    }

    public ArrayList<MarketAnnouncement> getAwajimaAnnouncements() {
        return awajimaAnnouncements;
    }

    public void setAwajimaAnnouncements(ArrayList<MarketAnnouncement> awajimaAnnouncements) {
        this.awajimaAnnouncements = awajimaAnnouncements;
    }

    public ArrayList<MarketBizDonor> getAwajimaMarketDonors() {
        return awajimaMarketDonors;
    }

    public void setAwajimaMarketDonors(ArrayList<MarketBizDonor> awajimaMarketDonors) {
        this.awajimaMarketDonors = awajimaMarketDonors;
    }

    public ArrayList<MarketBizPartner> getAwajimaMarketPartners() {
        return awajimaMarketPartners;
    }

    public void setAwajimaMarketPartners(ArrayList<MarketBizPartner> awajimaMarketPartners) {
        this.awajimaMarketPartners = awajimaMarketPartners;
    }

    public ArrayList<BusinessDeal> getAwajimaBizDeals() {
        return awajimaBizDeals;
    }

    public void setAwajimaBizDeals(ArrayList<BusinessDeal> awajimaBizDeals) {
        this.awajimaBizDeals = awajimaBizDeals;
    }

    public ArrayList<BusinessDealSub> getAwajimaBizSubDeals() {
        return awajimaBizSubDeals;
    }

    public void setAwajimaBizSubDeals(ArrayList<BusinessDealSub> awajimaBizSubDeals) {
        this.awajimaBizSubDeals = awajimaBizSubDeals;
    }

    public ArrayList<BusinessDealDoc> getAwajimaBizDealDocs() {
        return awajimaBizDealDocs;
    }

    public void setAwajimaBizDealDocs(ArrayList<BusinessDealDoc> awajimaBizDealDocs) {
        this.awajimaBizDealDocs = awajimaBizDealDocs;
    }

    public ArrayList<BusinessDealLoan> getAwajimaBizDealLoans() {
        return awajimaBizDealLoans;
    }

    public void setAwajimaBizDealLoans(ArrayList<BusinessDealLoan> awajimaBizDealLoans) {
        this.awajimaBizDealLoans = awajimaBizDealLoans;
    }

    public ArrayList<BusinessOthers> getAwajimaOtherBizS() {
        return awajimaOtherBizS;
    }

    public void setAwajimaOtherBizS(ArrayList<BusinessOthers> awajimaOtherBizS) {
        this.awajimaOtherBizS = awajimaOtherBizS;
    }

    public ArrayList<BizDealAccount> getAwajimaBizAccts() {
        return awajimaBizAccts;
    }

    public void setAwajimaBizAccts(ArrayList<BizDealAccount> awajimaBizAccts) {
        this.awajimaBizAccts = awajimaBizAccts;
    }

    public ArrayList<GroupAccount> getAwajimaGrpAccts() {
        return awajimaGrpAccts;
    }

    public void setAwajimaGrpAccts(ArrayList<GroupAccount> awajimaGrpAccts) {
        this.awajimaGrpAccts = awajimaGrpAccts;
    }

    public ArrayList<GroupSavings> getAwajimaGrpSavings() {
        return awajimaGrpSavings;
    }

    public void setAwajimaGrpSavings(ArrayList<GroupSavings> awajimaGrpSavings) {
        this.awajimaGrpSavings = awajimaGrpSavings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(awajimaProf, i);
        parcel.writeParcelable(awajimaAccounts, i);
        parcel.writeTypedList(awajimaPayees);
        parcel.writeTypedList(awajimaTimeLines);
        parcel.writeTypedList(awajimaCustomers);
        parcel.writeTypedList(skyStocks);
        parcel.writeTypedList(skyTellers);
        parcel.writeTypedList(skyAdminUsers);
        parcel.writeTypedList(skyAdminBankDeposits);
        parcel.writeTypedList(skyStockTransfers);
        parcel.writeTypedList(skyTransactions);
        parcel.writeTypedList(latLngs);
        parcel.writeTypedList(skyPayments);
        parcel.writeTypedList(skyPaymentCodes);
        parcel.writeTypedList(SKYPaymentDocs);
        parcel.writeTypedList(AwajimaMarketBusinesses);
        parcel.writeTypedList(awajimaMarkets);
        parcel.writeTypedList(awajimaEmergencies);
        parcel.writeTypedList(awajimaEmergNexts);
        parcel.writeTypedList(awajimaAnnouncements);
        parcel.writeTypedList(awajimaBizSubDeals);
        parcel.writeTypedList(awajimaBizDeals);
        parcel.writeTypedList(awajimaBizDealDocs);
        parcel.writeTypedList(awajimaBizDealLoans);
        parcel.writeTypedList(awajimaOtherBizS);
        parcel.writeTypedList(awajimaBizAccts);
        parcel.writeTypedList(awajimaGrpAccts);
        parcel.writeTypedList(awajimaGrpSavings);
    }

    public int getAwajimaID() {
        return awajimaID;
    }

    public void setAwajimaID(int awajimaID) {
        this.awajimaID = awajimaID;
    }

    public ArrayList<MarketBizSub> getAwajimaSubs() {
        return awajimaSubs;
    }

    public void setAwajimaSubs(ArrayList<MarketBizSub> awajimaSubs) {
        this.awajimaSubs = awajimaSubs;
    }

    public String getAwajimaOffice() {
        return awajimaOffice;
    }

    public void setAwajimaOffice(String awajimaOffice) {
        this.awajimaOffice = awajimaOffice;
    }

    public ArrayList<Profile> getAwajimaProfiles() {
        return awajimaProfiles;
    }

    public void setAwajimaProfiles(ArrayList<Profile> awajimaProfiles) {
        this.awajimaProfiles = awajimaProfiles;
    }

    public ArrayList<State> getAwajimaStates() {
        return awajimaStates;
    }

    public void setAwajimaStates(ArrayList<State> awajimaStates) {
        this.awajimaStates = awajimaStates;
    }

    public ArrayList<QBUser> getAwajimaQBUsers() {
        return awajimaQBUsers;
    }

    public void setAwajimaQBUsers(ArrayList<QBUser> awajimaQBUsers) {
        this.awajimaQBUsers = awajimaQBUsers;
    }

    public void addEmergReport(EmergencyReport emergencyReport) {
        awajimaEmergencies = new ArrayList<>();
        awajimaEmergencies.add(emergencyReport);

    }

    public ArrayList<Fence> getAwajimaFenceArrayList() {
        return awajimaFenceArrayList;
    }

    public void setAwajimaFenceArrayList(ArrayList<Fence> awajimaFenceArrayList) {
        this.awajimaFenceArrayList = awajimaFenceArrayList;
    }

    public ArrayList<FenceEvent> getAwajimaFenceEvents() {
        return awajimaFenceEvents;
    }

    public void setAwajimaFenceEvents(ArrayList<FenceEvent> awajimaFenceEvents) {
        this.awajimaFenceEvents = awajimaFenceEvents;
    }

    public void addBusiness(MarketBusiness marketBusiness) {
        awajimaBizS = new ArrayList<>();
        awajimaBizS.add(marketBusiness);

    }

    public ArrayList<Account> getAwajimaAccts() {
        return awajimaAccts;
    }

    public void setAwajimaAccts(ArrayList<Account> awajimaAccts) {
        this.awajimaAccts = awajimaAccts;
    }
}
