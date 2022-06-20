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
    private Profile profile;
    private Account account;
    private ArrayList<Payee> payees;
    private ArrayList<TimeLine> timeLines;
    private ArrayList<Customer> customers;
    private ArrayList<Stocks> stocks;

    private ArrayList<CustomerManager> customerManagers;
    private ArrayList<AdminUser> adminUsers;
    private ArrayList<AdminBankDeposit> adminBankDeposits;
    private ArrayList<StockTransfer> stockTransferArrayList;

    private ArrayList<Transaction> transactions;
    private ArrayList<TimeLine> timeLineArrayList;
    private ArrayList<LatLng> latLngs;
    private ArrayList<Payment> paymentArrayList;
    private ArrayList<PaymentCode> paymentCodeArrayList;
    private ArrayList<PaymentDoc> paymentDocArrayList;

    public void setTellers(ArrayList<CustomerManager> customerManagers) {
        this.customerManagers = customerManagers;
    }
    public ArrayList<CustomerManager> getTellers() { return customerManagers; }

    public void setAdminUsers(ArrayList<AdminUser> adminUsers) {
        this.adminUsers = adminUsers;
    }
    public ArrayList<AdminUser> getAdminUsers() { return adminUsers; }


    public void setAdminBankDeposits(ArrayList<AdminBankDeposit> adminBankDeposits) {
        this.adminBankDeposits = adminBankDeposits;
    }
    public ArrayList<AdminBankDeposit> getAdminBankDeposit() { return adminBankDeposits; }

    public void setStockTransfers(ArrayList<StockTransfer> stockTransferArrayList) {
        this.stockTransferArrayList = stockTransferArrayList;
    }
    public ArrayList<StockTransfer> getStockTransfers() { return stockTransferArrayList; }



    public void setPayments(ArrayList<Payment> paymentArrayList) {
        this.paymentArrayList = paymentArrayList;
    }
    public ArrayList<Payment> getPayments() { return paymentArrayList; }
    public void setPaymentCodes(ArrayList<PaymentCode> paymentCodeArrayList) {
        this.paymentCodeArrayList = paymentCodeArrayList;
    }
    public ArrayList<PaymentCode> getPaymentCodes() { return paymentCodeArrayList; }
    public void setPaymentDocuments(ArrayList<PaymentDoc> paymentDocArrayList) {
        this.paymentDocArrayList = paymentDocArrayList;
    }
    public ArrayList<PaymentDoc> getPaymentDocuments() { return paymentDocArrayList; }

    public ArrayList<Customer> getCustomers() { return customers;
    }
    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;

    }
    public ArrayList<Payee> getPayees() { return payees; }



    public ArrayList<Transaction> getTransactions() { return transactions;
    }
    public ArrayList<LatLng> getProfileLocations() {
        return latLngs;
    }
    public void setTimeLines(ArrayList<TimeLine> timeLineArrayList) {
        this.timeLineArrayList = timeLineArrayList;
    }
    public ArrayList<TimeLine> getTimeLines() { return timeLineArrayList; }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
        stocks= new ArrayList<>();
        int  stocksNo = stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,selectedStockPackage, uStockType,uStockModel,uStockColor,uStockSize,uStockQuantity,selectedOfficeBranch,uStockPricePerUnit,stockDate);
        stocks.add(stocks1);

    }
    public void addStocks(int stocksID, String stocksName, String uStockType,  int uStockQuantity, long stockCode,String selectedOfficeBranch, String stockDate,String status) {
        stocks= new ArrayList<>();
        int  stocksNo = stocks.size() + 1;
        Stocks stocks1 = new Stocks(stocksID,stocksName,uStockType,uStockQuantity,stockCode,stockDate,status);
        stocks.add(stocks1);

    }
    public void addCustomer(int uID, String surname, String firstName, String customerPhoneNumber, String customerEmailAddress, String customerAddress, String customerGender, String customerOffice, String customerState, Uri profilePicture, String dateJoined, String userName, String password) {
        customers= new ArrayList<>();
        String CusNo = "C" + (customers.size() + 1);
        Customer customer = new Customer(uID,surname, firstName, customerPhoneNumber,customerEmailAddress,customerAddress,customerGender,customerOffice,customerState,profilePicture,dateJoined,userName,password);
        customers.add(customer);

    }
    public void addTimeLine(String tittle,String timelineDetails) {
        timeLines= new ArrayList<>();
        String history = "History" + (timeLines.size() + 1);
        TimeLine timeLine = new TimeLine(tittle,timelineDetails);
        timeLines.add(timeLine);
    }
    public void addTransaction(long transactionId, String surname, String firstName, String customerPhoneNumber, double amount,String accountNumber,String description,String date,String type) {
        transactions= new ArrayList<>();
        String transactionCount = "C" + (transactions.size() + 1);
        Transaction transaction1 = new Transaction(transactionId,surname, firstName, customerPhoneNumber,amount,accountNumber,description,date,type);
        transactions.add(transaction1);
    }
    public void addPayee(String payeeName) {
        payees= new ArrayList<>();
        String payeeID = "P" + (payees.size() + 1);
        Payee payee = new Payee(payeeID, payeeName);
        payees.add(payee);
    }


}
