package com.skylightapp.SuperAdmin;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.skylightapp.Admins.AdminBankDeposit;
import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.AdminUser;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerManager;
import com.skylightapp.Classes.Payee;
import com.skylightapp.Classes.Payment;
import com.skylightapp.Classes.PaymentCode;
import com.skylightapp.Classes.PaymentDoc;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.TimeLine;
import com.skylightapp.Classes.Transaction;
import com.skylightapp.Inventory.StockTransfer;
import com.skylightapp.Inventory.Stocks;

import java.util.ArrayList;
import java.util.Date;

public class Skylight {
    private Profile skyProfile;
    private Account skyAccount;
    private ArrayList<Payee> skyPayees;
    private ArrayList<TimeLine> skyTimeLines;
    private ArrayList<Customer> skyCustomers;
    private ArrayList<Stocks> skyStocks;

    private ArrayList<CustomerManager> skyCustomerManagers;
    private ArrayList<AdminUser> skyAdminUsers;
    private ArrayList<AdminBankDeposit> skyAdminBankDeposits;
    private ArrayList<StockTransfer> skyStockTransfers;

    private ArrayList<Transaction> skyTransactions;
    private ArrayList<LatLng> latLngs;
    private ArrayList<Payment> skyPayments;
    private ArrayList<PaymentCode> skyPaymentCodes;
    private ArrayList<PaymentDoc> SKYPaymentDocs;

    public void setSkyCustomerManagers(ArrayList<CustomerManager> customerManagers) {
        this.skyCustomerManagers = customerManagers;
    }
    public ArrayList<CustomerManager> getTellers() { return skyCustomerManagers; }

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


}
