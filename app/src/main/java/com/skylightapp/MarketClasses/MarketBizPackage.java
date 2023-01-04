
package com.skylightapp.MarketClasses;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.skylightapp.Classes.Account;
import com.skylightapp.Classes.Customer;
import com.skylightapp.Classes.CustomerDailyReport;
import com.skylightapp.Classes.Item_Purchase;
import com.skylightapp.Classes.Loan;
import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Promo;
import com.skylightapp.Database.DBHelper;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.DAILY_REPORT_TABLE;
import static com.skylightapp.Classes.CustomerDailyReport.REPORT_ID;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_ID;
import static com.skylightapp.MarketClasses.Market.MARKET_TABLE;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_ID;
import static com.skylightapp.MarketClasses.MarketBusiness.MARKET_BIZ_TABLE;
import static java.lang.String.valueOf;

@Entity(tableName = MarketBizPackage.PACKAGE_TABLE)
public class MarketBizPackage implements Parcelable, Serializable {

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
    public static final boolean PACKAGE_IS_COMPLETE = false;
    public static final String PACKAGE_DB_ID = "package_DB_id";
    public static final String PACKAGE_BIZ_ID = "package_Biz_id";
    public static final String PACKAGE_MARKET_ID = "package_Market_ID";
    public static final String PACKAGE_IMAGE = "package_ImageA";
    public static final String PACKAGE_COLLECTION_STATUS = "package_Collection_Status";


    public static final String CREATE_PACKAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + PACKAGE_TABLE + " (" + PACKAGE_ID + " INTEGER  , " + PACKAGE_PROFILE_ID_FOREIGN + " INTEGER NOT NULL, " + PACKAGE_CUSTOMER_ID_FOREIGN + " INTEGER NOT NULL, " +
            PACKAGE_REPORT_ID + " INTEGER , " + PACKAGE_NAME + " TEXT, " + PACKAGE_TYPE + " TEXT, " + PACKAGE_START_DATE + " TEXT, " + PACKAGE_DURATION + " TEXT, " + PACKAGE_END_DATE + " TEXT, " +
            PACKAGE_VALUE + " REAL, " + PACKAGE_EXPECTED_VALUE + " REAL, " + PACKAGE_BALANCE + " REAL, " + PACKAGE_STATUS + " TEXT, " + PACKAGE_CODE + " REAL, " + PACKAGE_ITEM + " TEXT, "+ PACKAGE_IS_COMPLETE + " TEXT, " + PACKAGE_DB_ID + " INTEGER, "+ PACKAGE_COLLECTION_STATUS + " TEXT, " + PACKAGE_BIZ_ID + " INTEGER, "+ PACKAGE_MARKET_ID + " INTEGER, "+ PACKAGE_IMAGE + " BLOB, "+
            "PRIMARY KEY("  + PACKAGE_DB_ID + "),"+ "FOREIGN KEY(" + PACKAGE_BIZ_ID + ") REFERENCES " + MARKET_BIZ_TABLE + "(" + MARKET_BIZ_ID + "),"+ "FOREIGN KEY(" + PACKAGE_MARKET_ID + ") REFERENCES " + MARKET_TABLE + "(" + MARKET_ID + "),"+ "FOREIGN KEY(" + PACKAGE_REPORT_ID + ") REFERENCES " + DAILY_REPORT_TABLE + "(" + REPORT_ID + "),"+ "FOREIGN KEY(" + PACKAGE_PROFILE_ID_FOREIGN + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "FOREIGN KEY(" + PACKAGE_CUSTOMER_ID_FOREIGN + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";
    private static int count;


    private boolean packageIscomplete =false;
    private String packageOfficeBranch;
    private DBHelper dbHelper;
    //private int numberOfDays;
    @PrimaryKey(autoGenerate = true)
    private int packageId;
    private int packageProfileId;
    private int packageCustomerId;
    private long packageBizID;
    private int packageMarketID;
    //private String total;
    private String packageCustomerName;
    private String packageStatus;
    private String packageItem;
    private int num;
    int packageDuration;
    private String packageName;
    protected ArrayList<CustomerDailyReport> packageSavings;
    protected CustomerDailyReport packageReport;
    MarketBizPackage marketBizPackage;
    protected ArrayList<MarketBizPackage> marketBizPackages;
    protected ArrayList<Profile>packageProfiles;

    protected Promo packagePromo;
    private String packageType;
    private int packageAccNo;
    double packageTotalAmount;
    double packageDailyAmount;
    double packageBalance;
    double packageAmount_collected;
    Long packageCode;
    private Profile packageProfile ;
    private Account packageAccount;
    private Item_Purchase packageItem_purchase;
    private Loan packageLoan;
    private int packageDaysSub;
    private Uri packageImage;
    private String packageDateStarted;
    private String packageDateEnded;
    private double packageAmtRem;
    private int packageDaysRem;
    private Customer packageCustomer;
    boolean packageCompleted;
    private String packageTellerName;
    private String packageCollectionStatus;

    {
        Boolean.parseBoolean("false");
        packageCompleted = false;
    }
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public MarketBizPackage(long id, String packageId, String packageType, String packageValue, String packageStatus, String status) {

        super();
    }

    public MarketBizPackage(Comparable<String> s, CustomerDailyReport dailyReport) {

        super();
    }
    public MarketBizPackage(int packageId, String packageName, String packageType, double packageValue, double expectedValue, int packageDuration, String startDate, String endDate, long code, String packageStatus) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.packageType = packageType;
        this.packageDailyAmount = packageValue;
        this.packageTotalAmount = expectedValue;
        this.packageDuration = packageDuration;
        this.packageDateStarted = startDate;
        this.packageDateEnded = endDate;
        this.packageCode = code;
        this.packageStatus = packageStatus;

    }


    public MarketBizPackage(int r1, String package_a, int i, String s, String s1) {

        super();
    }

    public MarketBizPackage(String custID, String lendInfoID, long l, Long totalAmount, Long number_of_days, String startDate, Double rate_of_interest, Long packageDailyAmount, Long interest_amount_to_be_collected, Long totalAmount1, long l1, String unpaid, long l2, String s) {
        super();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.packageId = Math.toIntExact(l);
        }
        this.packageDateStarted = startDate;
        this.packageDateEnded = s;
        this.packageStatus = packageStatus;

    }
    public MarketBizPackage(int packageId, String packageType, double packageValue, double expectedAmount, String startDate, int duration, String endDate, double amountContributed, String packageStatus) {
        super();
        this.packageId = packageId;
        this.packageType=packageType;
        this.packageDailyAmount = packageValue;
        this.packageDateStarted = startDate;
        this.packageDateEnded = endDate;
        this.packageTotalAmount = expectedAmount;
        this.packageDateEnded = endDate;
        this.packageDuration = duration;
        this.packageAmount_collected = amountContributed;
        this.packageStatus = packageStatus;
    }



    public MarketBizPackage(int packageId, String packageType, String packageValue, String packageStatus) {
        super();
        this.packageId = packageId;
        this.packageType=packageType;
    }

    public MarketBizPackage(String custID, String lendInfoID, long l, Long totalAmount, Long number_of_days, double rate_of_interest, long packageDailyAmount, long interest_amount_to_be_collected, Long totalAmount1, long l1, String unpaid, long l2, String s) {
        super();
        this.packageDateEnded = s;
        this.packageStatus = unpaid;

    }

    /*public MarketBizPackage(int profileID, int customerID, int packageID, String finalItemType, String packageType, double savingsAmount, int packageDuration, String reportDate, double grandTotal, String officeBranch, String packageEndDate, String fresh) {

        super();
    }
    public MarketBizPackage(int packageID, int profileID, int customerID, String finalItemType, String packageType, double savingsAmount, int packageDuration, String reportDate, double grandTotal, String officeBranch, String packageEndDate, String fresh) {

    }*/
    public MarketBizPackage(int packageId, String packageType, double packageBalance) {
        this.packageId = packageId;
        this.packageType = packageType;
        this.packageBalance = packageBalance;
    }




    public MarketBizPackage(int profileID, int customerID, int packageID, String packageType, String finalItemType, double savingsAmount, int packageDuration, String reportDate, double grandTotal, String packageDateEnded, String fresh) {
        this.packageProfileId = profileID;
        this.packageCustomerId = customerID;
        this.packageId = packageID;
        this.packageType = packageType;
        this.packageItem = finalItemType;
        this.packageDailyAmount = savingsAmount;
        this.packageDuration = packageDuration;
        this.packageTotalAmount = grandTotal;
        this.packageDateEnded = packageDateEnded;
        this.packageStatus = fresh;

    }
    public MarketBizPackage(int packageID, String name, String type, double amount, String startDate, int duration, double total, double packageBalance, String endDate, String packageStatus) {
        this.packageDateStarted = startDate;
        this.packageName = name;
        this.packageId = packageID;
        this.packageType = type;
        this.packageDailyAmount = amount;
        this.packageDuration = duration;
        this.packageBalance = packageBalance;
        this.packageTotalAmount = total;
        this.packageDateEnded = endDate;
        this.packageStatus = packageStatus;
    }
    public MarketBizPackage(int profileID, int customerID, int packageID, String packageName, String packageType, double savingsAmount, int packageDuration, String reportDate, double grandTotal, String packageOfficeBranch, String packageEndDate, String fresh) {
        this.packageDateStarted = reportDate;
        this.packageProfileId = profileID;
        this.packageCustomerId = customerID;
        this.packageName = packageName;
        this.packageId = packageID;
        this.packageType = packageType;
        this.packageDailyAmount = savingsAmount;
        this.packageDuration = packageDuration;
        this.packageOfficeBranch = packageOfficeBranch;
        this.packageTotalAmount = grandTotal;
        this.packageDateEnded = packageEndDate;
        this.packageStatus = fresh;
    }



    public MarketBizPackage(String savings) {

    }

    public MarketBizPackage() {

    }

    public MarketBizPackage(int packageID, int cusID, String tittle, double amount, double grandTotal, String type, int duration) {

        this.packageId = packageID;
        this.packageCustomerId = cusID;
        this.packageType = type;
        this.packageDailyAmount = amount;
        this.packageDuration = duration;
        this.packageTotalAmount = grandTotal;
        this.packageName = tittle;
    }

    public MarketBizPackage(int packageId, String packageType, Uri packageImage) {
        this.packageId = packageId;
        this.packageType = packageType;
        this.packageImage = packageImage;
    }


    public static void setCount(int count) {
        MarketBizPackage.count = count;
    }

    public  int getCount() {
        return count;
    }


    public String setDateEnded(String string) {
        return null;
    }

    public void setPackageOfficeBranch(String packageOfficeBranch) {
        this.packageOfficeBranch = packageOfficeBranch;
    }

    public String getPackageOfficeBranch() {
        return packageOfficeBranch;
    }

    public String getPackageItem() {
        return packageItem;
    }

    public void setPackageItem(String packageItem) {
        this.packageItem = packageItem;
    }

    public void addPProfileManager(Profile userProfile) {
        String accNo = "A" + (packageProfiles.size() + 1);
        Profile profile = new Profile(userProfile);
        packageProfiles.add(profile);

    }

    public Profile getPackageProfile() {
        return packageProfile;
    }

    public void setPackageProfile(Profile packageProfile) {
        this.packageProfile = packageProfile;
    }

    public Account getPackageAccount() {
        return packageAccount;
    }

    public void setPackageAccount(Account packageAccount) {
        this.packageAccount = packageAccount;
    }

    public Customer getPackageCustomer() {
        return packageCustomer;
    }

    public void setPackageCustomer(Customer packageCustomer) {
        this.packageCustomer = packageCustomer;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public boolean isPackageIscomplete() {
        return packageIscomplete;
    }

    public void setPackageIscomplete(boolean packageIscomplete) {
        this.packageIscomplete = packageIscomplete;
    }

    public String getPackageTellerName() {
        return packageTellerName;
    }

    public void setPackageTellerName(String packageTellerName) {
        this.packageTellerName = packageTellerName;
    }

    public int getPackageDaysRem() {
        return packageDaysRem;
    }

    public void setPackageDaysRem(int packageDaysRem) {
        this.packageDaysRem = packageDaysRem;
    }

    public double getPackageAmtRem() {
        return packageAmtRem;
    }

    public void setPackageAmtRem(double packageAmtRem) {
        this.packageAmtRem = packageAmtRem;
    }

    public String getPackageCollectionStatus() {
        return packageCollectionStatus;
    }

    public void setPackageCollectionStatus(String packageCollectionStatus) {
        this.packageCollectionStatus = packageCollectionStatus;
    }

    public long getPackageBizID() {
        return packageBizID;
    }

    public void setPackageBizID(long packageBizID) {
        this.packageBizID = packageBizID;
    }

    public int getPackageMarketID() {
        return packageMarketID;
    }

    public void setPackageMarketID(int packageMarketID) {
        this.packageMarketID = packageMarketID;
    }


    public enum SkylightPackage_Type {
        SAVINGS , ITEM_PURCHASE, PROMO,BORROWING
    }

    public int getPSavingsCount(int packageID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM PACKAGE_TABLE WHERE PACKAGE_ID = ?",
                new String[]{valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Integer.parseInt(valueOf(cursor.getCount()));


            }
        }
        return 0;
    }

        public void addPReportCount(long packageID, int savingsCount) {
            CustomerDailyReport customerDailyReport = new CustomerDailyReport("Reports" + (packageSavings.size() + 1) + "Package" +  packageId);

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



    public MarketBizPackage(int profileID, int customerID, int packageID, SkylightPackage_Type type, double packageAmount, int packageDuration, String startDate, double expectedTotalAmount, String packageDateEnded, String packageStatus) {
        super();
        this.packageProfileId = profileID;
        this.packageId = packageID;
        this.packageType = valueOf(type);
        this.packageDateStarted = startDate;
        this.packageDateEnded = packageDateEnded;
        this.packageDailyAmount = packageAmount;
        this.packageStatus = packageStatus;
        this.packageDateStarted = startDate;
        this.packageCustomerId = customerID;

    }

    public MarketBizPackage(int id, String type, double amount, String startDate, int duration, double total, double packageBalance, String endDate, String packageStatus) {
        super();
        this.packageId = id;
        this.packageType=type;
        this.packageDailyAmount =amount;
        this.packageDateStarted =startDate;
        this.packageDuration=duration;
        this.packageTotalAmount=total;
        this.packageBalance = packageBalance;
        this.packageDateEnded =endDate;
        this.packageStatus = packageStatus;
    }

    /*public void addSavings(int  profileId,int  customerId,int id, double amount, int numberOfDay, double total, int daysRemaining, double amountRemaining,String date, String status) {
        String savingCount = "C" + (packageSavings.size() + 1);
        CustomerDailyReport customerSavings = new CustomerDailyReport();
        packageSavings.add(customerSavings);
    }*/
    public void addPSavings(Profile userProfile, Customer customer, MarketBizPackage marketBizPackage) {
        String savingCount = "C" + (packageSavings.size() + 1);
        CustomerDailyReport customerSavings = new CustomerDailyReport();
        packageSavings.add(customerSavings);
    }
   /* public void addProfileManagers() {
        String profileCounts = "P" + (profiles.size() + 1);
        MarketBizPackage marketBizPackage = new MarketBizPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, "fresh");
        marketBizPackage.addProfileManagers();
    }
    public void addProfileManager(Profile userProfile) {
        MarketBizPackage marketBizPackage = new MarketBizPackage(profileID, customerID, packageID, finalItemType, packageType, savingsAmount, packageDuration, reportDate, grandTotal, officeBranch, packageEndDate, "fresh");
        marketBizPackage.addProfileManager(userProfile);
    }*/


    @RequiresApi(api = Build.VERSION_CODES.N)
    public MarketBizPackage(int id, int custId, int packageAccNo, String type, double packageAmount, String startDate, String date, double packageAmount_collected, Double rate_of_interest, double interest_amount_to_be_collected, double totalAmount, double packageBalance, String endDate, Long due, String packageStatus) {
        super();
        this.packageId = Math.toIntExact(id);
        this.packageType = type;
        this.packageDateStarted = startDate;
        this.packageDateEnded = endDate;
        this.packageDailyAmount = packageAmount;
        this.packageStatus = packageStatus;
        this.packageCustomerId = custId;
        this.packageAccNo = packageAccNo;
        this.packageAmount_collected = packageAmount_collected;
        this.packageBalance = packageBalance;

    }



    public MarketBizPackage(Parcel in) {
        super();
        //packageImage = in.readInt();
        packageCompleted = in.readByte() != 0;
        packageDuration = in.readInt();
        packageId = in.readInt();
        packageTotalAmount = in.readDouble();
        packageCustomerName = in.readString();
        packageStatus = in.readString();
    }

    public static final Creator<MarketBizPackage> CREATOR = new Creator<MarketBizPackage>() {
        @Override
        public MarketBizPackage createFromParcel(Parcel in) {
            return new MarketBizPackage(in);
        }

        @Override
        public MarketBizPackage[] newArray(int size) {
            return new MarketBizPackage[size];
        }
    };

    public void setPackageLoan(Loan loan1) {
        this.packageLoan = loan1;
    }

    public void setPackagePromo(Promo promo1) {
        this.packagePromo = promo1;
    }
    public Promo getPackagePromo() {
        return packagePromo;
    }
    public void setPackageItem_purchase(Item_Purchase packageItem_purchase) {
        this.packageItem_purchase = packageItem_purchase;
    }
    public Item_Purchase getPackageItem_purchase() {
        return packageItem_purchase;
    }
    public Loan getPackageLoan() {
        return packageLoan;
    }
    public void addPSavings(int  profileId, int  customerId, int id, double amount, int numberOfDay, double total, int daysRemaining, double amountRemaining, String date, String status) {
        String savingsCount = "C" + (packageSavings.size() + 1);
        CustomerDailyReport customerDailyReport = new CustomerDailyReport(profileId,customerId, id, amount,numberOfDay,total,daysRemaining,amountRemaining,date,status);
        packageSavings.add(customerDailyReport);
    }

    public int getPackageAccNo() {
        return packageAccNo;
    }

    public void setPackageAccNo(int packageAccNo) {
        this.packageAccNo = packageAccNo;
    }
    public long getPackageCode() {
        return packageCode;
    }
    public void setPackageCode(long packageCode) {
        this.packageCode = packageCode;
    }

    public int getPackageProfileId() {
        return packageProfileId;
    }
    public void setPackageProfileId(int packageProfileId) {
        this.packageProfileId = packageProfileId;
    }

    public int getPackageCustomerId() {
        return packageCustomerId;
    }
    public void setPackageCustomerId(int packageCustomerId) {
        this.packageCustomerId = packageCustomerId;
    }



    public int getPackageDaysSub() {
        return packageDaysSub;
    }

    public void setPackageDaysSub(int packageDaysSub) {
        this.packageDaysSub = packageDaysSub;
    }


    public ArrayList<CustomerDailyReport> getPackageSavings() {
        return packageSavings;
    }

    public void setPackageReport(CustomerDailyReport report) {
        this.packageReport = report;
    }

    public double getPackageBalance() {
        return packageBalance;
    }

    public void setPackageBalance(double packageBalance) {
        this.packageBalance = packageBalance;
    }
    public double getPackageTotalAmount() {
        return packageTotalAmount;
    }

    public void setPackageTotalAmount(double packageTotalAmount) {
        this.packageTotalAmount = packageTotalAmount;
    }


    public double getPackageAmount_collected() {
        return packageAmount_collected;
    }

    public void setPackageAmount_collected(double packageAmount_collected) {
        this.packageAmount_collected = packageAmount_collected;
    }


    public int getPackageDuration() {
        return packageDuration;
    }

    public void setPackageDuration(int packageDuration) {
        this.packageDuration = packageDuration;
    }

    public double getPackageDailyAmount() {
        return packageDailyAmount;
    }

    public void setPackageDailyAmount(double packageDailyAmount) {
        this.packageDailyAmount = packageDailyAmount;
    }

    public String getPackageDateEnded()
    {
        return packageDateEnded;
    }

    public void setPackageDateEnded(Date packageDateEnded)
    {
        this.packageDateEnded = valueOf(packageDateEnded);
    }

    public MarketBizPackage(String packageName, int num) {
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

    public int getPackNo() {
        return num;
    }

    public String getPackageStatus()
    {
        return packageStatus;
    }

    public void setPackageStatus(String status)
    {
        this.packageStatus = status;
    }


    public String getPackageCustomerName()
    {
        return packageCustomerName;
    }

    public void setPackageCustomerName(String packageCustomerName)
    {
        this.packageCustomerName = packageCustomerName;
    }
    public String getPackageDateStarted()
    {
        return packageDateStarted;
    }

    public void setPackageDateStarted(String packageDateStarted)
    {
        this.packageDateStarted = valueOf(packageDateStarted);
    }


    public int getPackID()
    {
        return packageId;
    }

    public void setRecordPackageId(int recordPackageId)
    {
        this.packageId = recordPackageId;
    }
    public Uri getPackageImage()
    {
        return packageImage;
    }

    public void setPackageImage(Uri packageImage)
    {
        this.packageImage = packageImage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        //parcel.writeInt(packageImage);
        parcel.writeByte((byte) (packageCompleted ? 1 : 0));
        parcel.writeInt(packageDuration);
        parcel.writeLong(packageId);
        parcel.writeDouble(packageTotalAmount);
        parcel.writeString(packageCustomerName);
        parcel.writeString(packageStatus);
    }
}
