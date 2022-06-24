
package com.skylightapp.Classes;

import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static java.lang.String.valueOf;

@Entity(tableName = SkyLightPackage.PACKAGE_TABLE)
public class SkyLightPackage extends CustomerDailyReport implements Parcelable, Serializable {

    public static final String PACKAGE_TABLE = "package_table";
    public static final String PACKAGE_ID = "package_id";
    public static final String PACKAGE_NAME = "package_Name";
    public static final String PACKAGE_TYPE = "package_type";
    public static final String PACKAGE_START_DATE = "package_start_date";
    public static final String PACKAGE_END_DATE = "package_end_date";
    public static final String PACKAGE_VALUE = "package_value";
    public static final String PACKAGE_STATUS = "package_status";
    public static final String PACKAGE_DURATION = "package_duration";
    public static final String PACKAGE_TOTAL_VALUE = "package_total_value";
    public static final String PACKAGE_BALANCE = "package_balance";
    public static final String PACKAGE_CODE = "package_Code";
    public static final String PACKAGE_EXPECTED_VALUE = "package_expected_amount";
    public static final String PACKAGE_ITEM = "package_item";
    public static final String PACKAGE_REPORT_ID = "package_ReportID";
    public static final String PACKAGE_PROFILE_ID_FOREIGN = "profile_ID_Foreign";
    public static final String PACKAGE_CUSTOMER_ID_FOREIGN = "package_cus_ID_Foreign";


    public static final String CREATE_PACKAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + PACKAGE_TABLE + " (" + PACKAGE_ID + " INTEGER  , " + PACKAGE_PROFILE_ID_FOREIGN + " INTEGER NOT NULL, " + PACKAGE_CUSTOMER_ID_FOREIGN + " INTEGER NOT NULL, " +
            PACKAGE_REPORT_ID + " INTEGER , " + PACKAGE_NAME + " TEXT, " + PACKAGE_TYPE + " TEXT, " + PACKAGE_START_DATE + " TEXT, " + PACKAGE_DURATION + " TEXT, " + PACKAGE_END_DATE + " TEXT, " +
            PACKAGE_VALUE + " TEXT, " + PACKAGE_EXPECTED_VALUE + " TEXT, " + PACKAGE_BALANCE + " TEXT, " + PACKAGE_STATUS + " TEXT, " + PACKAGE_CODE + " TEXT, " + PACKAGE_ITEM + " TEXT, " +
            "PRIMARY KEY("  + PACKAGE_ID + "),"+ "FOREIGN KEY(" + PACKAGE_REPORT_ID + ") REFERENCES " + DAILY_REPORT_TABLE + "(" + REPORT_ID + "),"+ "FOREIGN KEY(" + PACKAGE_PROFILE_ID_FOREIGN + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "FOREIGN KEY(" + PACKAGE_CUSTOMER_ID_FOREIGN + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";
    private static int count;


    boolean complete =false;
    private String officeBranch;
    private int numberOfDays;
    @PrimaryKey(autoGenerate = true)
    private int packageId=1042;
    private int profileId;
    private int customerId;
    private String total;
    private String customerName;
    private String status;
    private String packageItem;
    private int num;
    int packageDuration;
    private String packageName;
    protected ArrayList<CustomerDailyReport> reports;
    protected CustomerDailyReport report;
    SkyLightPackage skyLightPackage;
    protected ArrayList<SkyLightPackage>skyLightPackages;
    private ArrayList<Loan> loans;
    private ArrayList<Account> accounts;
    private ArrayList<Promo> promos;
    protected Promo promo;
    protected String packageType;
    protected String c;
    int custId;
    int accNo;
    double totalAmount;
    double packageTotalAmount;
    Double rate_of_interest;
    double dailyAmount;
    double interest_amount_to_be_collected;
    double balance;
    double amount_collected;
    Long due;
    Long packageCode;
    String date;
    String bank;
    Profile userProfile ;
    Account account;
    private Item_Purchase item_purchase;
    private  Loan loan;
    protected ArrayList<Profile>profiles;
    protected ArrayList<Item_Purchase>item_purchases;
    private Long subscriptionDays ;
    private Long subscribedDays ;
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy - hh:mm a");

    public SkyLightPackage(long id, String packageId, String packageType, String packageValue, String packageStatus, String status) {

        super();
    }

    public SkyLightPackage(Comparable<String> s, CustomerDailyReport dailyReport) {

        super();
    }
    public SkyLightPackage(int packageId, String packageName, String packageType, double packageValue, double expectedValue, int packageDuration, String startDate, String endDate, long code, String packageStatus) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packageType = packageType;
        this.dailyAmount = packageValue;
        this.packageTotalAmount = expectedValue;
        this.packageDuration = packageDuration;
        this.dateStarted = startDate;
        this.dateEnded = endDate;
        this.packageCode = code;
        this.status = packageStatus;

    }


    public SkyLightPackage(int r1, String package_a, int i, String s, String s1) {

        super();
    }

    public SkyLightPackage(String custID, String lendInfoID, long l, Long totalAmount, Long number_of_days,String startDate, Double rate_of_interest, Long dailyAmount, Long interest_amount_to_be_collected, Long totalAmount1, long l1, String unpaid, long l2, String s) {
        super();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.packageId = Math.toIntExact(l);
        }
        this.type = type;
        this.dateStarted = startDate;
        this.dateEnded = s;
        this.totalAmount = totalAmount;
        this.status = status;
        this.date = date;
        this.custId = custId;
        this.accNo = accNo;
        this.amount_collected = amount_collected;
        this.rate_of_interest = rate_of_interest;
        this.interest_amount_to_be_collected = interest_amount_to_be_collected;
        this.balance = balance;
        this.due = due;
    }
    public SkyLightPackage(int packageId, String packageType, double packageValue,double expectedAmount,String startDate, int duration,String endDate, double amountContributed,String packageStatus) {
        super();
        this.packageId = packageId;
        this.packageType=packageType;
        this.dailyAmount = packageValue;
        this.dateStarted = startDate;
        this.dateEnded = endDate;
        this.packageTotalAmount = expectedAmount;
        this.dateEnded = endDate;
        this.numberOfDays = duration;
        this.amount_collected = amountContributed;
        this.status = packageStatus;
    }



    public SkyLightPackage(int packageId, String packageType, String packageValue, String packageStatus) {
        super();
        this.packageId = packageId;
        this.packageType=packageType;
    }

    public SkyLightPackage(String custID, String lendInfoID, long l, Long totalAmount, Long number_of_days, double rate_of_interest, long dailyAmount, long interest_amount_to_be_collected, Long totalAmount1, long l1, String unpaid, long l2, String s) {
        super();
        this.type = type;
        this.dateEnded = s;
        this.totalAmount = totalAmount;
        this.status = unpaid;
        this.date = date;
        this.custId = custId;
        this.accNo = accNo;
        this.amount_collected = amount_collected;
        this.rate_of_interest = rate_of_interest;
        this.interest_amount_to_be_collected = interest_amount_to_be_collected;
        this.balance = balance;
        this.due = due;
    }

    /*public SkyLightPackage(int profileID, int customerID, int packageID, String finalItemType, String packageType, double savingsAmount, int packageDuration, String reportDate, double grandTotal, String officeBranch, String packageEndDate, String fresh) {

        super();
    }
    public SkyLightPackage(int packageID, int profileID, int customerID, String finalItemType, String packageType, double savingsAmount, int packageDuration, String reportDate, double grandTotal, String officeBranch, String packageEndDate, String fresh) {

    }*/
    public SkyLightPackage(int packageId, String packageType, double balance) {
        this.packageId = packageId;
        this.packageType = packageType;
        this.balance = balance;
    }




    public SkyLightPackage(int profileID, int customerID, int packageID, String packageType, String finalItemType, double savingsAmount, int packageDuration, String reportDate, double grandTotal, String dateEnded, String fresh) {
        this.profileId = profileID;
        this.customerId = customerID;
        this.packageId = packageID;
        this.packageType = packageType;
        this.packageItem = finalItemType;
        this.dailyAmount = savingsAmount;
        this.packageDuration = packageDuration;
        this.date = reportDate;
        this.packageTotalAmount = grandTotal;
        this.dateEnded = dateEnded;
        this.status = fresh;

    }
    public SkyLightPackage(int packageID, String name, String type, double amount, String startDate, int duration, double total, double balance, String endDate, String status) {
        this.dateStarted = startDate;
        this.packageName = name;
        this.packageId = packageID;
        this.packageType = type;
        this.dailyAmount = amount;
        this.packageDuration = duration;
        this.balance = balance;
        this.packageTotalAmount = total;
        this.dateEnded = endDate;
        this.status = status;
    }
    public SkyLightPackage(int profileID, int customerID, int packageID, String packageName, String packageType, double savingsAmount, int packageDuration, String reportDate, double grandTotal, String officeBranch, String packageEndDate, String fresh) {
        this.dateStarted = reportDate;
        this.profileId = profileID;
        this.customerId = customerID;
        this.packageName = packageName;
        this.packageId = packageID;
        this.packageType = packageType;
        this.dailyAmount = savingsAmount;
        this.packageDuration = packageDuration;
        this.officeBranch = officeBranch;
        this.packageTotalAmount = grandTotal;
        this.dateEnded = packageEndDate;
        this.status = fresh;
    }



    public SkyLightPackage(String savings) {

    }

    public SkyLightPackage() {

    }


    public static void setCount(int count) {
        SkyLightPackage.count = count;
    }

    public  int getCount() {
        return count;
    }


    @Override
    public void setNotificationUris(@NonNull ContentResolver cr, @NonNull List<Uri> uris) {

    }

    public String setDateEnded(String string) {
        return null;
    }

    public void setOfficeBranch(String officeBranch) {
        this.officeBranch = officeBranch;
    }

    public String getOfficeBranch() {
        return officeBranch;
    }

    public String getPackageItem() {
        return packageItem;
    }

    public void setPackageItem(String packageItem) {
        this.packageItem = packageItem;
    }

    public void addProfileManager(Profile userProfile) {
        String accNo = "A" + (profiles.size() + 1);
        Profile profile = new Profile(userProfile);
        profiles.add(profile);

    }


    public enum SkylightPackage_Type {
        SAVINGS , ITEM_PURCHASE, PROMO,BORROWING
    }

    public int getSavingsCount(long packageID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM PACKAGE_TABLE WHERE PACKAGE_ID = ?",
                new String[]{valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Integer.parseInt(valueOf(cursor.getCount()));


            }
        }
        return 0;
    }

        public void addReportCount(long packageID,int savingsCount) {
            CustomerDailyReport customerDailyReport = new CustomerDailyReport("Reports" + (reports.size() + 1) + "Package" +  packageId);

    }
    /*public void addSavings1(long  profileId, long  customerId, long id, long packageId, double amount, double numberOfDay, double total, int daysRemaining, double amountRemaining, String date, String status) {
        String savingsCount = "C" + (reports.size() + 1);
        for (int reportsCount = 0; reportsCount < reports.size(); reportsCount++ ) {
            if (reports.get(reportsCount).getPackageId() == packageId)  {
                ;
                //if (reports.get(i).getPackageType() == SkylightPackage_Type.SAVINGS)  {
                // reportsCount =1;
                //}
                if (reportsCount !=31)  {
                    complete =true;

                }
            }
        }
        CustomerDailyReport customerDailyReport = new CustomerDailyReport(profileId,customerId, id, amount,numberOfDay,total,daysRemaining,amountRemaining,date,status);
        reports.add(customerDailyReport);
    }*/
    private int image_id;
    private String dateStarted;
    private String dateEnded;
    boolean completed;

    {
        Boolean.parseBoolean("false");
        completed = false;
    }


    public SkyLightPackage(int profileID, int customerID, int packageID, SkylightPackage_Type type,double packageAmount, int packageDuration,String startDate, double expectedTotalAmount, String dateEnded,  String status) {
        super();
        this.profileId = profileID;
        this.packageId = packageID;
        this.packageType = valueOf(type);
        this.dateStarted = startDate;
        this.dateEnded = dateEnded;
        this.dailyAmount = totalAmount;
        this.status = status;
        this.dateStarted = startDate;
        this.custId = customerID;

    }

    public SkyLightPackage(int id, String type, double amount,String startDate,int duration,double total,double balance,String endDate,String status) {
        super();
        this.packageId = id;
        this.packageType=type;
        this.dailyAmount=amount;
        this.dateStarted=startDate;
        this.packageDuration=duration;
        this.packageTotalAmount=total;
        this.balance=balance;
        this.dateEnded=endDate;
        this.status=status;
    }
    public void addAccount(int virtualAccountID, String customerBank, String customerNames, long accountNo, double accountBalance, AccountTypes accountTypes) {
        String accNo = "A" + (accounts.size() + 1);
        Account account = new Account(virtualAccountID,customerBank,customerNames, valueOf(accountNo), accountBalance,accountTypes);
        accounts.add(account);
    }

    public void addSavings(int  profileId,int  customerId,int id, double amount, int numberOfDay, double total, int daysRemaining, double amountRemaining,String date, String status) {
        String savingCount = "C" + (reports.size() + 1);
        CustomerDailyReport customerSavings = new CustomerDailyReport();
        reports.add(customerSavings);
    }
    public void addSavings(Profile userProfile, Customer customer, SkyLightPackage skyLightPackage) {
        String savingCount = "C" + (reports.size() + 1);
        CustomerDailyReport customerSavings = new CustomerDailyReport();
        reports.add(customerSavings);
    }
   /* public void addProfileManagers() {
        String profileCounts = "P" + (profiles.size() + 1);
        SkyLightPackage skyLightPackage = new SkyLightPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, "fresh");
        skyLightPackage.addProfileManagers();
    }
    public void addProfileManager(Profile userProfile) {
        SkyLightPackage skyLightPackage = new SkyLightPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, "fresh");
        skyLightPackage.addProfileManager(userProfile);
    }*/


    @RequiresApi(api = Build.VERSION_CODES.N)
    public SkyLightPackage(int id, int custId, int accNo, String type, double packageAmount, String startDate, String date, double amount_collected, Double rate_of_interest, double interest_amount_to_be_collected, double totalAmount, double balance, String endDate, Long due, String status) {
        super();
        this.packageId = Math.toIntExact(id);
        this.type = type;
        this.dateStarted = startDate;
        this.dateEnded = endDate;
        this.dailyAmount = packageAmount;
        this.status = status;
        this.date = date;
        this.custId = custId;
        this.accNo = accNo;
        this.amount_collected = amount_collected;
        this.rate_of_interest = rate_of_interest;
        this.interest_amount_to_be_collected = interest_amount_to_be_collected;
        this.balance = balance;
        this.due = due;

    }



    public SkyLightPackage(Parcel in) {
        super();
        image_id = in.readInt();
        completed = in.readByte() != 0;
        numberOfDays = in.readInt();
        packageId = in.readInt();
        total = in.readString();
        customerName = in.readString();
        status = in.readString();
    }

    public static final Creator<SkyLightPackage> CREATOR = new Creator<SkyLightPackage>() {
        @Override
        public SkyLightPackage createFromParcel(Parcel in) {
            return new SkyLightPackage(in);
        }

        @Override
        public SkyLightPackage[] newArray(int size) {
            return new SkyLightPackage[size];
        }
    };
    public String getBank() {
        return bank;
    }

    public void setLoan(Loan loan1) {
        this.loan = loan1;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public Account getAccount() {
        return account;
    }
    public void setPromo(Promo promo1) {
        this.promo = promo1;
    }
    public Promo getPromo() {
        return promo;
    }
    public void setItem_purchase(Item_Purchase item_purchase) {
        this.item_purchase = item_purchase;
    }
    public Item_Purchase getItem_purchase() {
        return item_purchase;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public Loan getLoan() {
        return loan;
    }
    public void addSavings(int  profileId, int  customerId, int id, double amount, double numberOfDay, double total, int daysRemaining, double amountRemaining, String date, String status) {
        String savingsCount = "C" + (reports.size() + 1);
        CustomerDailyReport customerDailyReport = new CustomerDailyReport(profileId,customerId, id, amount,numberOfDay,total,daysRemaining,amountRemaining,date,status);
        reports.add(customerDailyReport);
    }
    public void addLoans(BigDecimal amount, String loanStartDate, String status) {
        int loanNumber = Integer.parseInt("Loan" + (loans.size() + 1));
        Loan loan = new Loan(loanNumber, amount,loanStartDate,status);
        loans.add(loan);
    }
    public void addLoans(int loanId,double amount, String loanStartDate, String status,String loanEndDate,double interest1) {
        int loanNumber = Integer.parseInt(("Loan" + (loans.size() + 1)));
        Loan loan = new Loan(loanNumber, amount,loanStartDate,status,loanEndDate,interest1);
        loans.add(loan);
    }
    public int getAccNo() {
        return accNo;
    }

    public void setAccNo(int accNo) {
        this.accNo = accNo;
    }
    public long getPackageCode() {
        return packageCode;
    }
    public void setPackageCode(long packageCode) {
        this.packageCode = packageCode;
    }

    public int getProfileId() {
        return profileId;
    }
    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }



    public Long getSubscribedDays() {
        return subscribedDays;
    }

    public void setSubscribedDays(Long subscribedDays) {
        this.subscribedDays = subscribedDays;
    }
    public Long getSubscriptionDays() {
        return subscriptionDays;
    }

    public void setSubscriptionDays(Long subscriptionDays) {
        this.subscriptionDays = subscriptionDays;
    }

    public void setAccount1(Account account1) {
        this.account = account1;
    }
    public ArrayList<CustomerDailyReport> getSavings() {
        return reports;
    }
    public ArrayList<Promo> getPromos() {
        return promos;
    }
    public ArrayList<Item_Purchase> getItem_purchases() {
        return item_purchases;
    }


    public void setCustomerDailyReport(CustomerDailyReport report) {
        this.report = report;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getPackageTotalAmount() {
        return packageTotalAmount;
    }

    public void setPackageTotalAmount(double packageTotalAmount) {
        this.packageTotalAmount = packageTotalAmount;
    }


    public double getAmount_collected() {
        return amount_collected;
    }

    public void setAmount_collected(double amount_collected) {
        this.amount_collected = amount_collected;
    }

    public String getStatus() {
        return status;

    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDue() {
        return due;
    }

    public void setDue(Long due) {
        this.due = due;
    }


    public double getInterest_amount_to_be_collected() {
        return interest_amount_to_be_collected;
    }

    public void setInterest_amount_to_be_collected(Long interest_amount_to_be_collected) {
        this.interest_amount_to_be_collected = interest_amount_to_be_collected;
    }

    public void setLendInfoID(int packageId) {
        this.packageId = packageId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getNumber_of_days() {
        return numberOfDays;
    }

    public void setNumber_of_days(int number_of_days) {
        this.numberOfDays = number_of_days;
    }

    public Double getRate_of_interest() {
        return rate_of_interest;
    }

    public void setRate_of_interest(Double rate_of_interest) {
        this.rate_of_interest = rate_of_interest;
    }

    public int getPackageDuration() {
        return packageDuration;
    }

    public void setPackageDuration(int packageDuration) {
        this.packageDuration = packageDuration;
    }

    public double getDailyAmount() {
        return dailyAmount;
    }

    public void setDailyAmount(Long dailyAmount) {
        this.dailyAmount = dailyAmount;
    }

    public String getDateEnded()
    {
        return dateEnded;
    }

    public void setDateEnded(Date dateEnded)
    {
        this.dateEnded = valueOf(dateEnded);
    }

    public SkyLightPackage(String packageName, int num) {
        super();
        this.packageName = packageName;
        this.num = num;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public int getNum() {
        return num;
    }

    public String getPackageStatus()
    {
        return status;
    }

    public void setPackageStatus(String status)
    {
        this.status = status;
    }


    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    public String getDateStarted()
    {
        return dateStarted;
    }

    public void setDateStarted(String dateStarted)
    {
        this.dateStarted = valueOf(dateStarted);
    }

    public int getNumberOfDays()
    {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays)
    {
        this.numberOfDays = numberOfDays;
    }
    public int getPackID()
    {
        return packageId;
    }

    public void setPackageId(int packageId)
    {
        this.packageId = packageId;
    }
    public int getImage_id()
    {
        return image_id;
    }

    public void setImage_id(int image_id)
    {
        this.image_id = image_id;
    }

    public double getTotal()
    {
        return totalAmount;
    }

    public void setTotal(double totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    SkyLightPackage(String img, String text)
    {
        super();
        image_id = Integer.parseInt(img);
        this.total = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(image_id);
        parcel.writeByte((byte) (completed ? 1 : 0));
        parcel.writeInt(numberOfDays);
        parcel.writeLong(packageId);
        parcel.writeString(total);
        parcel.writeString(customerName);
        parcel.writeString(status);
    }
}
