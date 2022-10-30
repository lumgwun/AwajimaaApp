package com.skylightapp.SuperAdmin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
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
import com.skylightapp.MarketClasses.MarketBusiness;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Awajima implements Parcelable, Serializable {
    private Profile skyProfile;
    private Account skyAccount;
    private ArrayList<Payee> skyPayees;
    private ArrayList<TimeLine> skyTimeLines;
    private ArrayList<Customer> skyCustomers;
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
    private ArrayList<Market> AwajimaMarketArrayList;
    private ArrayList<EmergencyReport> awajimaEmergencies;
    private ArrayList<EmergReportNext> awajimaEmergNexts;
    private ArrayList<MarketAdmin> awajimaMarketAdmins;
    private ArrayList<MarketAnnouncement> awajimaAnnouncements;
    private ArrayList<MarketBizDonor> awajimaMarketDonors;
    private ArrayList<MarketBizPartner> awajimaMarketPartners;
    private ArrayList<BusinessDealSub> awajimaBizSubDeals;
    private ArrayList<BusinessDeal> awajimaBizDeals;
    private ArrayList<BusinessDealDoc> awajimaBizDealDocs;
    private ArrayList<BusinessDealLoan> awajimaBizDealLoans;
    private ArrayList<BusinessOthers> awajimaOtherBizS;
    private ArrayList<BizDealAccount> awajimaBizAccts;
    private ArrayList<GroupAccount> awajimaGrpAccts;
    private ArrayList<GroupSavings> awajimaGrpSavings;

    public Awajima() {
        super();

    }

    protected Awajima(Parcel in) {
        skyProfile = in.readParcelable(Profile.class.getClassLoader());
        skyAccount = in.readParcelable(Account.class.getClassLoader());
        skyPayees = in.createTypedArrayList(Payee.CREATOR);
        skyTimeLines = in.createTypedArrayList(TimeLine.CREATOR);
        skyCustomers = in.createTypedArrayList(Customer.CREATOR);
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
        AwajimaMarketArrayList = in.createTypedArrayList(Market.CREATOR);
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

    public ArrayList<Customer> getSkyCustomers() { return skyCustomers;
    }
    public void setSkyTransactions(ArrayList<Transaction> skyTransactions) {
        this.skyTransactions = skyTransactions;

    }
    public ArrayList<Payee> getSkyPayees() { return skyPayees; }



    public ArrayList<Transaction> getSkyTransactions() { return skyTransactions;
    }
    public ArrayList<LatLng> getProfileLocations() {
        return latLngs;
    }
    public void setSkyTimeLines(ArrayList<TimeLine> timeLineArrayList) {
        this.skyTimeLines = timeLineArrayList;
    }
    public ArrayList<TimeLine> getSkyTimeLines() { return skyTimeLines; }

    public Profile getSkyProfile() {
        return skyProfile;
    }

    public void setSkyProfile(Profile skyProfile) {
        this.skyProfile = skyProfile;
    }

    public Account getSkyAccount() {
        return skyAccount;
    }

    public void setSkyAccount(Account skyAccount) {
        this.skyAccount = skyAccount;
    }

    public void addPayment(String type, double totalToWithdraw, Date date, long paymentCode, String acctType, String office, String status) {
        ArrayList<Payment> paymentArrayList = null;
        if (paymentArrayList != null) {
            String paymentNo = "Payment:" + (paymentArrayList.size() + 1);
        }
        Payment payment = new Payment(type,totalToWithdraw, date,paymentCode,acctType,office,status);
        paymentArrayList.add(payment);

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
        skyCustomers = new ArrayList<>();
        String CusNo = "C" + (skyCustomers.size() + 1);
        Customer customer = new Customer(uID,surname, firstName, customerPhoneNumber,customerEmailAddress,customerAddress,customerGender,customerOffice,customerState,profilePicture,dateJoined,userName,password);
        skyCustomers.add(customer);

    }
    public void addTimeLine(int id,String tittle,String timelineDetails) {
        skyTimeLines = new ArrayList<>();
        String history = "History" + (skyTimeLines.size() + 1);
        TimeLine timeLine = new TimeLine(id, tittle,timelineDetails);
        skyTimeLines.add(timeLine);
    }
    public void addTimeLine(String tittle,String timelineDetails) {
        skyTimeLines = new ArrayList<>();
        String history = "History" + (skyTimeLines.size() + 1);
        TimeLine timeLine = new TimeLine( tittle,timelineDetails);
        skyTimeLines.add(timeLine);
    }
    public void addTransaction(long transactionId, String surname, String firstName, String customerPhoneNumber, double amount,String accountNumber,String description,String date,String type) {
        skyTransactions = new ArrayList<>();
        String transactionCount = "C" + (skyTransactions.size() + 1);
        Transaction transaction1 = new Transaction(transactionId,surname, firstName, customerPhoneNumber,amount,accountNumber,description,date,type);
        skyTransactions.add(transaction1);
    }
    public void addPayee(String payeeName) {
        skyPayees = new ArrayList<>();
        int payeeID = skyPayees.size() + 1;
        Payee payee = new Payee(payeeID, payeeName);
        skyPayees.add(payee);
    }


    public ArrayList<MarketBusiness> getAwajimaMarketBusinesses() {
        return AwajimaMarketBusinesses;
    }

    public void setAwajimaMarketBusinesses(ArrayList<MarketBusiness> awajimaMarketBusinesses) {
        AwajimaMarketBusinesses = awajimaMarketBusinesses;
    }

    public ArrayList<Market> getAwajimaMarketArrayList() {
        return AwajimaMarketArrayList;
    }

    public void setAwajimaMarketArrayList(ArrayList<Market> awajimaMarketArrayList) {
        AwajimaMarketArrayList = awajimaMarketArrayList;
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
        parcel.writeParcelable(skyProfile, i);
        parcel.writeParcelable(skyAccount, i);
        parcel.writeTypedList(skyPayees);
        parcel.writeTypedList(skyTimeLines);
        parcel.writeTypedList(skyCustomers);
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
        parcel.writeTypedList(AwajimaMarketArrayList);
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
}
