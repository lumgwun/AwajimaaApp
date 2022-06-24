package com.skylightapp.Classes;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.skylightapp.Database.DBHelper;
import com.skylightapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


import static com.skylightapp.Classes.Account.ACCOUNTS_TABLE;
import static com.skylightapp.Classes.Account.ACCOUNT_NO;
import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;

import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TABLE;
import static java.lang.String.valueOf;

@SuppressWarnings("deprecation")
@Entity(tableName = CustomerDailyReport.DAILY_REPORT_TABLE)
public class CustomerDailyReport  implements Cursor, Parcelable, Serializable {
    public static final String REPORT_NUMBER_OF_DAYS_SO_FAR = "no_of_days_so_far";
    public static final String REPORT_AMOUNT_COLLECTED_SO_FAR = "amount_so_far";
    //public static final String RECORD_REPORT_ID = "r_id";


    public static final String DAILY_REPORT_TABLE = "daily_reports";
    public static final String REPORT_PACKAGE = "package";
    public static final String REPORT_ID = "serial_number";
    public static final String REPORT_AMOUNT = "amount";
    public static final String REPORT_NUMBER_OF_DAYS = "days";
    public static final String REPORT_DATE = "date";
    public static final String REPORT_TOTAL = "total";
    public static final String REPORT_DAYS_REMAINING = "days_remaining";
    public static final String REPORT_AMOUNT_REMAINING = "amount_remaining";
    public static final String REPORT_COUNT = "report_count";
    public static final String REPORT_OFFICE_BRANCH = "report_Office_Branch";

    public static final String REPORT_PACKAGE_ID = "report_package_Id";

    public static final String REPORT_STATUS = "status";
    public static final String REPORT_CODE = "savingsCode";
    public static final String REPORT_PROF_ID_FK = "profile_ID_Fk";
    public static final String REPORT_CUS_ID_FK = "cus_ID_FK";
    public static final String REPORT_PACK_ID_FK = "report_ID_FK";
    public static final String REPORT_ACCOUNT_NO_FK = "report_AcctNo_FK";
    //public static final String TABLE_CUSTOMER_RECORD = "customer_record";

    public static final String CREATE_DAILY_REPORT_TABLE = "CREATE TABLE " + DAILY_REPORT_TABLE + " (" + REPORT_PROF_ID_FK + " INTEGER , " + REPORT_CUS_ID_FK + " INTEGER , " +
            REPORT_ID + " INTEGER , " + REPORT_PACK_ID_FK + " INTEGER NOT NULL, " + REPORT_ACCOUNT_NO_FK + " INTEGER , " + REPORT_PACKAGE + " TEXT, " + REPORT_AMOUNT + " NUMERIC, " + REPORT_OFFICE_BRANCH + " TEXT, " + REPORT_CODE + " TEXT, "+
            REPORT_NUMBER_OF_DAYS + " INTEGER , " + REPORT_TOTAL + " NUMERIC,"+ REPORT_DAYS_REMAINING + " INTEGER, " + REPORT_AMOUNT_REMAINING + " TEXT, " + REPORT_DATE + " TEXT, " +
            REPORT_STATUS + " TEXT, " + REPORT_COUNT + " TEXT, " + REPORT_AMOUNT_COLLECTED_SO_FAR + " TEXT, " + REPORT_NUMBER_OF_DAYS_SO_FAR + " TEXT, " +
            "PRIMARY KEY(" + REPORT_ID + "), "+ "FOREIGN KEY(" + REPORT_ACCOUNT_NO_FK + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + "),"+ "FOREIGN KEY(" + REPORT_PACK_ID_FK + ") REFERENCES " + PACKAGE_TABLE + "(" + PACKAGE_ID + ")," + "FOREIGN KEY(" + REPORT_PROF_ID_FK + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"FOREIGN KEY(" + REPORT_CUS_ID_FK + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";



    private int image_id;
    private String recordDate;
    private int numberOfDays;
    private String serialNumber;
    private int remainingDays =0;
    private int customerId;
    private double amount;
    private double total;
    private String CustomerName;
    private String status;
    private String officeBranch;
    protected String type;
    private double grandTotal;
    boolean completed = true;
    private int count;
    int  profileId;
    int  packageId;
    @PrimaryKey(autoGenerate = true)
    private int recordNo=142;
    double amountRemaining;
    private boolean remind;
    SkyLightPackage skyLightPackage;
    Customer customer;
    AdminUser adminUser;
    Account account;
    Profile profile;
    long savingsCode;
    DBHelper dbHelper;
    Uri savingsDoc;
    private CustomerManager customerManager;
    private static final String JSON_NAME = "name";
    private static final String JSON_DATE = "recordDate";
    private static final String JSON_NUMBER_OF_DAYS = "days";
    private static final String JSON_REMIND = "remind";
    private static final String JSON_UID = "serialNumber";
    private static final String JSON_SHOW_YEAR = "show_year";
    private static final String JSON_AMOUNT = "amount";
    private static final String JSON_TOTAL = "total";
    private static final String JSON_CUSTOMER_ID = "customer_id";
    private static final String JSON_STATUS = "status";
    private static final String JSON_DAYS_REMAINING = "days_remaining";
    private static final String JSON_COUNT = "count";
    private static final String JSON_GRAND_TOTAL = "grandTotal";


    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd.MM.yyyy", Locale.getDefault());

    private static final int DAY_IN_MILLIS = 86400000;
    private String aPackage;

    public CustomerDailyReport(JSONObject json) throws JSONException {

        if (json.has(JSON_NAME)) {
            CustomerName = json.getString(JSON_NAME);
        }

        // Check whether user wants to be reminded for this birthday.
        remind = !json.has(JSON_REMIND) || json.getBoolean(JSON_REMIND );
        // Default to true if not found, log message.

        // Date of birthday in millis.
        if (json.has(JSON_DATE)) {
            recordDate = String.valueOf(new Date());
            recordDate =json.getString(JSON_DATE);
        }
        // year
        if (json.has(JSON_REMIND)) {
            remind = json.getBoolean(JSON_REMIND);

        }

        // UID
        if (json.has(JSON_UID)) {
            serialNumber = String.valueOf(Integer.parseInt(json.getString(JSON_UID)));
        }

    }

    public CustomerDailyReport(int packageID, int reportID, double savingsAmount, int numberOfDays, double totalAmountSum, int daysRemaining, double amountRemaining, String reportDate, String status) {
        this.packageId =packageID;
        this.recordNo =reportID;
        this.amount =savingsAmount;
        this.numberOfDays =numberOfDays;
        this.total = totalAmountSum;
        this.recordDate =reportDate;
        this.amountRemaining =amountRemaining;
        this.status =status;
        this.remainingDays =daysRemaining;
    }

    public CustomerDailyReport(String count,int packageID, int reportID, double savingsAmount, int numberOfDays, double totalAmountSum, int daysRemaining, double amountRemaining, String reportDate, String status) {
        this.serialNumber = count;
        this.packageId =packageID;
        this.recordNo =reportID;
        this.amount =savingsAmount;
        this.numberOfDays =numberOfDays;
        this.total = totalAmountSum;
        this.recordDate =reportDate;
        this.amountRemaining =amountRemaining;
        this.status =status;
        this.remainingDays =daysRemaining;
    }

    public CustomerDailyReport(int count, int customerID, String customerName, int packageID, int reportID, double savingsAmount, int numberOfDays, double totalAmountSum, int daysRemaining, double amountRemaining, String reportDate, String status) {
        this.serialNumber = String.valueOf(count);
        this.customerId =customerID;
        this.packageId =packageID;
        this.recordNo =reportID;
        this.CustomerName =customerName;
        this.amount =savingsAmount;
        this.numberOfDays =numberOfDays;
        this.total = totalAmountSum;
        this.recordDate =reportDate;
        this.amountRemaining =amountRemaining;
        this.status =status;
        this.remainingDays =daysRemaining;
    }


    public double getTotalSavingsForPackage(long packageID) {
        //String[] whereArgs = new String[]{String.valueOf(packageID)};
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT SUM(" + REPORT_AMOUNT + ") as Total FROM " + DAILY_REPORT_TABLE, new String[]{" WHERE PACKAGE_ID=?",String.valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Double.parseDouble(String.valueOf(cursor.getCount()));


            }
        }

        return 0;
    }

    public int getPackageCount(long packageID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) as Count FROM DAILY_REPORT_TABLE WHERE PACKAGE_ID = ?",
                new String[]{valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Integer.parseInt(String.valueOf(cursor.getCount()));


            }
        }

        return 0;
    }

    public int getCount(long packageID) {
        return getPackageCount(packageID);
    }
    //return -1;

    public int getTotalPSavingsCount(long packageID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM DAILY_REPORT_TABLE WHERE PACKAGE_ID = ?",
                new String[]{valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Integer.parseInt(String.valueOf(cursor.getCount()));


            }
        }

        /*try (Cursor cursor = db.rawQuery("SELECT SUM(" + REPORT_AMOUNT + ") as Total FROM " + DAILY_REPORT_TABLE, new String[]{" WHERE PACKAGE_ID=?",String.valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Integer.parseInt(String.valueOf(cursor.getCount()));


            }
        }*/

        return -1;
    }
    public int getSkyNoOfPacksForCus(long customerID) {
        Customer customer1 = new Customer();
        customerID=customer1.getCusUID();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM PACKAGE_TABLE WHERE customerID = ?",
                new String[]{valueOf(customerID)})) {

            if (cursor.moveToFirst()) {
                return Integer.parseInt(String.valueOf(cursor.getCount()));


            }
        }

        /*try (Cursor cursor = db.rawQuery("SELECT SUM(" + REPORT_AMOUNT + ") as Total FROM " + DAILY_REPORT_TABLE, new String[]{" WHERE PACKAGE_ID=?",String.valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Integer.parseInt(String.valueOf(cursor.getCount()));


            }
        }*/

        return -1;
    }
    public double getTotalSavingsForCustomer(long customerID) {
        Customer customer = new Customer();
        customerID=customer.getCusUID();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try (Cursor cursor = db.rawQuery("SELECT SUM(" + REPORT_AMOUNT + ") as Total FROM DAILY_REPORT_TABLE WHERE CUSTOMER_ID = ?",
                new String[]{valueOf(customerID)})) {

            if (cursor.moveToFirst()) {
                return Double.parseDouble(String.valueOf(cursor.getCount()));


            }
        }

        return -1;
    }

    public CustomerDailyReport(String s) {

    }

    public CustomerDailyReport(int i, String s, String savings, String ngn3000, String old, String confirmed) {


    }

    public void setSkylightPackage(SkyLightPackage skyLightPackage) {
        this.skyLightPackage=skyLightPackage;

    }

    public AdminUser getAdminUser()
    {
        return adminUser;
    }
    public void setSkylightPackage(AdminUser adminUser) {
        this.adminUser=adminUser;

    }

    public SkyLightPackage getSkyLightPackage()
    {
        return skyLightPackage;
    }

    public CustomerDailyReport(Profile profile, Customer customer, Account account, SkyLightPackage skyLightPackage) {
        this.profile = profile;
        this.customer = customer;
        this.account = account;
        this.skyLightPackage = skyLightPackage;

    }


    public CustomerDailyReport(int Package_ID,int reportID,int  profileId, int  customerId,String date, double amount, int numberOfDay, double total, double amount_collected_so_far, double amountRemaining, int daysRemaining, String status) {
        this.serialNumber = String.valueOf(reportID);
        this.amount = amount;
        this.packageId = Package_ID;
        this.numberOfDays = Integer.parseInt(String.valueOf(numberOfDay));
        this.total = total;
        this.customerId = customerId;
        this.profileId = profileId;
        this.remainingDays = Integer.parseInt(String.valueOf(daysRemaining));
        this.recordDate =date;
        this.status =status;
        this.amountRemaining =amountRemaining;
    }
    public CustomerDailyReport(int  profileId, int  customerId, int id, double amount, double numberOfDay, double total, int daysRemaining, double amountRemaining, String date, String status) {
        this.serialNumber = String.valueOf(id);
        this.amount = amount;
        this.numberOfDays = Integer.parseInt(String.valueOf(numberOfDay));
        this.total = total;
        this.customerId = customerId;
        this.profileId = profileId;
        this.remainingDays = Integer.parseInt(String.valueOf(daysRemaining));
        this.recordDate =date;
        this.status =status;
        this.amountRemaining =amountRemaining;
    }



    public CustomerDailyReport() {

    }

    public CustomerDailyReport(int profileId, int customerId, int packageId, int reportId,double amount, int numberOfDays, double total,String reportDate,int remainingDays, double amountRemaining, String status) {
        this.serialNumber = String.valueOf(packageId);
        this.amount = amount;
        this.packageId = packageId;
        this.numberOfDays = Integer.parseInt(String.valueOf(numberOfDays));
        this.total = total;
        this.customerId = customerId;
        this.profileId = profileId;
        this.remainingDays = Integer.parseInt(String.valueOf(remainingDays));
        this.recordDate =reportDate;
        this.status =status;
        this.amountRemaining =amountRemaining;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = Integer.parseInt(prime * result + serialNumber);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomerDailyReport other = (CustomerDailyReport) obj;
        return serialNumber.equals(other.serialNumber);
    }

    public boolean getRemind() {
        return remind;
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public Object getRemindAlarmDrawable() {
        if (remind) {
            return AppController.getInstance().getResources().getDrawable(R.drawable.ic_alert);
        } else {
            return AppController.getInstance().getResources().getDrawable(R.drawable.ic_alert);
        }
    }


    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, this.getCustomerName());
        json.put(JSON_AMOUNT, this.getAmount());
        json.put(JSON_NUMBER_OF_DAYS, this.getNumberOfDays());
        json.put(JSON_REMIND, this.getRemind());
        json.put(JSON_UID, this.getUID());
        json.put(JSON_TOTAL, this.getTotal());
        json.put(JSON_GRAND_TOTAL, this.getGrandTotal());
        json.put(JSON_DAYS_REMAINING, this.getRemainingDays());
        return json;
    }

    public String getUID() {
        String uid = String.valueOf(this.serialNumber);
        if (uid.isEmpty()) {
            uid = UUID.randomUUID().toString();
            setUID(uid);
        }
        return String.valueOf(serialNumber);
    }

    public void setUID(String uID) {
        this.serialNumber = uID;
    }

    public String getFormattedDaysRemainingString(int profileID, int skyLightPackageID, int reportID) {
        int i = getDaysBetween();
        Profile profile1=new Profile();
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        int reportNumber = customerDailyReport.getColumnCount();
        SkyLightPackage skyLightPackage1;
        long packageId = skyLightPackage.getPackageId();
        long profileId = profile.getPID();
        int j = customerDailyReport.getCount();
        Context context = AppController.getInstance();
        //StringBuilder sb = new StringBuilder("column: ");

        for (int k = 0; i < count; i++) {

            if (i == 0 && i == j) {
                return (context.getString(R.string.days_31) + "!");
            } else if (i == 1) {
                return (context.getString(R.string.days_30) + "!");
            } else if (i == 2) {
                return (context.getString(R.string.days_29)+ "!");

            } else if (i == 3) {
                return (context.getString(R.string.days_28)+ "!");
            } else if (i == 4) {
                return (context.getString(R.string.days_27)+ "!");
            } else if (i == 5) {
                return (context.getString(R.string.days_26)+ "!");
            } else if (i == 6) {
                return (context.getString(R.string.days_25)+ "!");
            } else if (i == 7) {
                return (context.getString(R.string.days_24)+ "!");
            } else if (i == 8) {
                return ( context.getString(R.string.days_23)+ "!");
            } else if (i == 9) {
                return (context.getString(R.string.days_22)+ "!");
            } else if (i == 10) {
                return (context.getString(R.string.days_21)+ "!");
            } else if (i == 11) {
                return (context.getString(R.string.days_20)+ "!");
            } else if (i == 12) {
                return (context.getString(R.string.days_19)+ "!");
            } else if (i == 13) {
                return (context.getString(R.string.days_18)+ "!");
            } else if (i == 14) {
                return (context.getString(R.string.days_17)+ "!");
            } else if (i == 15) {
                return (context.getString(R.string.days_16)+ "!");
            } else if (i == 16) {
                return (context.getString(R.string.days_15)+ "!");
            } else if (i == 17) {
                return (context.getString(R.string.days_14)+ "!");
            } else if (i == 18) {
                return (context.getString(R.string.days_13)+ "!");
            } else if (i == 19) {
                return (context.getString(R.string.days_12)+ "!");
            } else if (i == 20) {
                return (context.getString(R.string.days_11)+ "!");
            } else if (i == 21) {
                return (context.getString(R.string.days_10)+ "!");
            } else if (i == 22) {
                return (context.getString(R.string.days_9)+ "!");
            } else if (i == 23) {
                return (context.getString(R.string.days_8)+ "!");
            } else if (i == 24) {
                return (context.getString(R.string.days_7)+ "!");
            } else if (i == 25) {
                return (context.getString(R.string.days_6)+ "!");
            } else if (i == 26) {
                return (context.getString(R.string.days_5)+ "!");
            } else if (i == 27) {
                return (context.getString(R.string.days_4)+ "!");
            } else if (i == 28) {
                return (context.getString(R.string.days_3)+ "!");
            } else if (i == 29) {
                return (context.getString(R.string.days_2)+ "!");
            } else if (i == 30) {
                return (context.getString(R.string.days_1)+ "!");
            } else if (i == 31) {
                return (context.getString(R.string.days_0)+ "!");
            }
            return null;
        }


        return null;
    }

    public int getDaysBetween() {

        return 0;
    }

    public static long getDayCount(String start, String end) {

        long dayCount = 0;
        try {
            Date dateStart = simpleDateFormat.parse(start);
            Date dateEnd = simpleDateFormat.parse(end);
            assert dateEnd != null;
            assert dateStart != null;
            dayCount = Math.round((dateEnd.getTime() - dateStart.getTime())
                    / (double) DAY_IN_MILLIS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dayCount;
    }




    protected CustomerDailyReport(Parcel in) {
        image_id = in.readInt();
        numberOfDays = in.readInt();
        serialNumber = in.readString();
        customerId = in.readInt();
        amount = in.readDouble();
        total = in.readDouble();
        status = in.readString();
        grandTotal = in.readDouble();
        completed = in.readByte() != 0;
    }

    public CustomerDailyReport(int count, int customerId, boolean b, double amount, int numberOfDays, String total, String status, int remainingDays) {
        this.count = this.getCount();
        this.customerId =customerId;
        this.completed =b;
        this.amount =amount;
        this.numberOfDays =numberOfDays;
        this.total = Double.parseDouble(total);
        this.status =status;
        this.remainingDays =remainingDays;

    }

    public CustomerDailyReport(long id, double amount, long numberOfDays, double total, long daysRemaining, double amountRemaining, String date, String status) {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image_id);
        dest.writeInt(numberOfDays);
        dest.writeString(serialNumber);
        dest.writeLong(customerId);
        dest.writeDouble(amount);
        dest.writeDouble(total);
        dest.writeString(status);
        dest.writeDouble(grandTotal);
        dest.writeByte((byte) (completed ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomerDailyReport> CREATOR = new Creator<CustomerDailyReport>() {
        @Override
        public CustomerDailyReport createFromParcel(Parcel in) {
            return new CustomerDailyReport(in);
        }

        @Override
        public CustomerDailyReport[] newArray(int size) {
            return new CustomerDailyReport[size];
        }
    };




    public int getRemainingDays()
    {
        return remainingDays;
    }
    public void setRecordNo(int recordNo) {
        this.recordNo = recordNo;
    }
    public int getRecordNo()
    {
        return recordNo;
    }
    public void setSerialNo(String serialNumber) {

        this.serialNumber = serialNumber;
    }
    public void setOfficeBranch(String officeBranch) {

        this.officeBranch = officeBranch;
    }
    public String getOfficeBranch()
    {
        return officeBranch;
    }
    public String getSerialNumber()
    {
        return serialNumber;
    }


    public String setReportCount(String serialNumber) {
        this.serialNumber = serialNumber;
        return serialNumber;
    }


    public void setSavingsDoc(Uri savingsDoc) {
        this.savingsDoc = savingsDoc;
    }

    public Uri getSavingsDoc()
    {
        return savingsDoc;
    }

    public CustomerManager getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }


    public void setRemainingDays(int remainingDays)
    {
        this.remainingDays = remainingDays;
    }
    public double getAmountRemaining()
    {
        return amountRemaining;
    }

    public void setAmountRemaining(double amountRemaining)
    {
        this.amountRemaining = amountRemaining;
    }


    public int getImage_id()
    {
        return this.image_id;
    }

    public void setImage_id(int image_id)
    {
        this.image_id = image_id;
    }

    public String getRecordDate()
    {
        return recordDate;
    }
    public SkyLightPackage.SkylightPackage_Type getPackageType()
    {
        return SkyLightPackage.SkylightPackage_Type.valueOf(type);
    }
    public void setPackageType(int type)
    {
        this.type = String.valueOf(type);
    }

    public void setRecordDate(Date recordDate)
    {
        this.recordDate = String.valueOf(recordDate);
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }

    public boolean getCompleted()
    {
        return completed;
    }
    public void setCustomerName(String customerName)
    {
        this.CustomerName = customerName;
    }

    public Customer getCustomer()
    {
        return customer;
    }
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    public Profile getProfile()
    {
        return profile;
    }
    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }

    public String getCustomerName()
    {
        return CustomerName;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }
    public int getPackageID()
    {
        return packageId;
    }


    public int getNumberOfDays() {
        return numberOfDays;
    }
    public void setSavingsCode(long savingsCode)
    {
        this.savingsCode = savingsCode;
    }

    public long getSavingsCode()
    {
        return savingsCode;
    }

    public void setNumberOfDays(int numberOfDays)
    {
        this.numberOfDays = numberOfDays;
    }
    public int getPackageId()
    {
        return packageId;
    }
    public void setPackageId(int packageId)
    {
        this.packageId = packageId;
    }

    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }
    public double getGrandTotal()
    {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal)
    {
        this.grandTotal = grandTotal;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public CustomerDailyReport(String img, String text)
    {
        image_id = Integer.parseInt(img);
        this.total = Double.parseDouble(text);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public boolean move(int i) {
        return false;
    }

    @Override
    public boolean moveToPosition(int i) {
        return false;
    }

    @Override
    public boolean moveToFirst() {
        return false;
    }

    @Override
    public boolean moveToLast() {
        return false;
    }

    @Override
    public boolean moveToNext() {
        return false;
    }

    @Override
    public boolean moveToPrevious() {
        return false;
    }

    @Override
    public boolean isFirst() {
        return false;
    }

    @Override
    public boolean isLast() {
        return false;
    }

    @Override
    public boolean isBeforeFirst() {
        return false;
    }

    @Override
    public boolean isAfterLast() {
        return false;
    }

    @Override
    public int getColumnIndex(String s) {
        return 0;
    }

    @Override
    public int getColumnIndexOrThrow(String s) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public String getColumnName(int i) {
        return null;
    }

    @Override
    public String[] getColumnNames() {
        return new String[0];
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public byte[] getBlob(int i) {
        return new byte[0];
    }

    @Override
    public String getString(int i) {
        return null;
    }

    @Override
    public void copyStringToBuffer(int i, CharArrayBuffer charArrayBuffer) {

    }

    @Override
    public short getShort(int i) {
        return 0;
    }

    @Override
    public int getInt(int i) {
        return 0;
    }

    @Override
    public long getLong(int i) {
        return 0;
    }

    @Override
    public float getFloat(int i) {
        return 0;
    }

    @Override
    public double getDouble(int i) {
        return 0;
    }

    @Override
    public int getType(int i) {
        return Integer.parseInt(type);
    }

    public int getType() {
        return 0;
    }

    @Override
    public boolean isNull(int i) {
        return false;
    }

    @Override
    public void deactivate() {

    }

    @Override
    public boolean requery() {
        return false;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void registerContentObserver(ContentObserver contentObserver) {

    }

    @Override
    public void unregisterContentObserver(ContentObserver contentObserver) {

    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void setNotificationUri(ContentResolver contentResolver, Uri uri) {

    }

    @Override
    public Uri getNotificationUri() {
        return null;
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return false;
    }

    @Override
    public void setExtras(Bundle bundle) {

    }

    @Override
    public Bundle getExtras() {
        return null;
    }

    @Override
    public Bundle respond(Bundle bundle) {
        return null;
    }

    public SkyLightPackage.SkylightPackage_Type getSkylightPackage() {
        return SkyLightPackage.SkylightPackage_Type.valueOf(type);
    }

    public void setSkylightPackage(SkyLightPackage.SkylightPackage_Type aPackage) {
        this.type = String.valueOf(aPackage);
    }


    public void setRecordDate(String createdDate) {

    }
}