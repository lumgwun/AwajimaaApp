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
    public static final String REPORT_NUMBER_OF_DAYS_SO_FAR = "report_no_of_days_so_far";
    public static final String REPORT_AMOUNT_COLLECTED_SO_FAR = "report_amount_so_far";

    public static final String DAILY_REPORT_TABLE = "report_Cdaily_reports";
    public static final String REPORT_PACKAGE = "report_Ppackage";
    public static final String REPORT_ID = "report_serial_number";
    public static final String REPORT_AMOUNT = "report_Aamount";
    public static final String REPORT_NUMBER_OF_DAYS = "report_Ddays";
    public static final String REPORT_DATE = "report_Ddate";
    public static final String REPORT_TOTAL = "report_Ttotal";
    public static final String REPORT_DAYS_REMAINING = "report_Ddays_remaining";
    public static final String REPORT_AMOUNT_REMAINING = "report_Aamount_remaining";
    public static final String REPORT_COUNT = "report_count";
    public static final String REPORT_OFFICE_BRANCH = "report_Office_Branch";
    public static final String REPORT_STATUS = "report_Sstatus";
    public static final String REPORT_CODE = "report_SsavingsCode";
    public static final String REPORT_PROF_ID_FK = "report_Pprofile_ID_Fk";
    public static final String REPORT_CUS_ID_FK = "report_Ccus_ID_FK";
    public static final String REPORT_PACK_ID_FK = "report_ID_FK";
    public static final String REPORT_ACCOUNT_NO_FK = "report_AcctNo_FK";
    //public static final String TABLE_CUSTOMER_RECORD = "customer_record";

    public static final String CREATE_DAILY_REPORT_TABLE = "CREATE TABLE " + DAILY_REPORT_TABLE + " (" + REPORT_PROF_ID_FK + " INTEGER , " + REPORT_CUS_ID_FK + " INTEGER , " +
            REPORT_ID + " INTEGER , " + REPORT_PACK_ID_FK + " INTEGER NOT NULL, " + REPORT_ACCOUNT_NO_FK + " INTEGER , " + REPORT_PACKAGE + " TEXT, " + REPORT_AMOUNT + " REAL, " + REPORT_OFFICE_BRANCH + " TEXT, " + REPORT_CODE + " TEXT, "+
            REPORT_NUMBER_OF_DAYS + " INTEGER , " + REPORT_TOTAL + " REAL,"+ REPORT_DAYS_REMAINING + " INTEGER, " + REPORT_AMOUNT_REMAINING + " TEXT, " + REPORT_DATE + " TEXT, " +
            REPORT_STATUS + " TEXT, " + REPORT_COUNT + " INTEGER, " + REPORT_AMOUNT_COLLECTED_SO_FAR + " REAL, " + REPORT_NUMBER_OF_DAYS_SO_FAR + " INTEGER, " +
            "PRIMARY KEY(" + REPORT_ID + "), "+ "FOREIGN KEY(" + REPORT_ACCOUNT_NO_FK + ") REFERENCES " + ACCOUNTS_TABLE + "(" + ACCOUNT_NO + "),"+ "FOREIGN KEY(" + REPORT_PACK_ID_FK + ") REFERENCES " + PACKAGE_TABLE + "(" + PACKAGE_ID + ")," + "FOREIGN KEY(" + REPORT_PROF_ID_FK + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+"FOREIGN KEY(" + REPORT_CUS_ID_FK + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";



    private Uri recordimage;
    private String recordDate;
    private int recordNumberOfDays;
    private String recordSerialNumber;
    private int recordRemainingDays =0;
    private int recordCustomerID;
    private double recordAmount;
    //private double total;
    private String recordCustomerName;
    private String recordStatus;
    private String recordOfficeBranch;
    //private String type;
    //private double grandTotal;
    boolean completed = true;
    private int recordCount;
    int recordProfileId;
    int recordPackageId;
    @PrimaryKey(autoGenerate = true)
    private int recordID =142;
    double recordAmountRemaining;
    private boolean recordRemind;
    private SkyLightPackage recordPackage;
    private Customer recordCustomer;
    private AdminUser recordAdminUser;
    private Account recordAccount;
    private Profile recordProfile;
    private long recordSavingsCode;
    DBHelper dbHelper;
    private Uri recordDocLink;
    private PaymentDoc recordPaymentDoc;
    private CustomerManager recordCustomerManager;
    private static final String JSON_NAME = "name";
    private static final String JSON_DATE = "recordDate";
    private static final String JSON_NUMBER_OF_DAYS = "days";
    private static final String JSON_REMIND = "remind";
    private static final String JSON_UID = "serialNumber";
    private static final String JSON_AMOUNT = "amount";
    private static final String JSON_TOTAL = "total";
    private static final String JSON_DAYS_REMAINING = "days_remaining";
    private static final String JSON_GRAND_TOTAL = "grandTotal";


    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());

    private static final int DAY_IN_MILLIS = 86400000;
    private String aPackage;

    public CustomerDailyReport(JSONObject json) throws JSONException {

        if (json.has(JSON_NAME)) {
            recordCustomerName = json.getString(JSON_NAME);
        }

        // Check whether user wants to be reminded for this birthday.
        recordRemind = !json.has(JSON_REMIND) || json.getBoolean(JSON_REMIND );
        // Default to true if not found, log message.

        // Date of birthday in millis.
        if (json.has(JSON_DATE)) {
            recordDate = valueOf(new Date());
            recordDate =json.getString(JSON_DATE);
        }
        // year
        if (json.has(JSON_REMIND)) {
            recordRemind = json.getBoolean(JSON_REMIND);

        }

        // UID
        if (json.has(JSON_UID)) {
            recordSerialNumber = valueOf(Integer.parseInt(json.getString(JSON_UID)));
        }

    }

    public CustomerDailyReport(int packageID, int reportID, double savingsAmount, int recordNumberOfDays, double totalAmountSum, int daysRemaining, double recordAmountRemaining, String reportDate, String recordStatus) {
        this.recordPackageId =packageID;
        this.recordID =reportID;
        //this.recordAmount =savingsAmount;
        this.recordNumberOfDays = recordNumberOfDays;
        this.recordAmount = totalAmountSum;
        this.recordDate =reportDate;
        this.recordAmountRemaining = recordAmountRemaining;
        this.recordStatus = recordStatus;
        this.recordRemainingDays =daysRemaining;
    }

    public CustomerDailyReport(String recordCount, int packageID, int reportID, double savingsAmount, int recordNumberOfDays, double totalAmountSum, int daysRemaining, double recordAmountRemaining, String reportDate, String recordStatus) {
        this.recordSerialNumber = recordCount;
        this.recordPackageId =packageID;
        this.recordID =reportID;
        this.recordNumberOfDays = recordNumberOfDays;
        this.recordAmount = totalAmountSum;
        this.recordDate =reportDate;
        this.recordAmountRemaining = recordAmountRemaining;
        this.recordStatus = recordStatus;
        this.recordRemainingDays =daysRemaining;
    }

    public CustomerDailyReport(int recordCount, int customerID, String recordCustomerName, int packageID, int reportID, double savingsAmount, int recordNumberOfDays, double totalAmountSum, int daysRemaining, double recordAmountRemaining, String reportDate, String recordStatus) {
        this.recordSerialNumber = valueOf(recordCount);
        this.recordCustomerID =customerID;
        this.recordPackageId =packageID;
        this.recordID =reportID;
        this.recordCustomerName = recordCustomerName;
        //this.recordAmount =savingsAmount;
        this.recordNumberOfDays = recordNumberOfDays;
        this.recordAmount = totalAmountSum;
        this.recordDate =reportDate;
        this.recordAmountRemaining = recordAmountRemaining;
        this.recordStatus = recordStatus;
        this.recordRemainingDays =daysRemaining;
    }


    public double getTotalSavingsForPackage(long packageID) {
        //String[] whereArgs = new String[]{String.valueOf(packageID)};
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT SUM(" + REPORT_AMOUNT + ") as Total FROM " + DAILY_REPORT_TABLE, new String[]{" WHERE PACKAGE_ID=?", valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Double.parseDouble(valueOf(cursor.getCount()));


            }
        }

        return 0;
    }

    public int getPackageCount(long packageID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT COUNT(*) as Count FROM DAILY_REPORT_TABLE WHERE PACKAGE_ID = ?",
                new String[]{valueOf(packageID)})) {

            if (cursor.moveToFirst()) {
                return Integer.parseInt(valueOf(cursor.getCount()));


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
                return Integer.parseInt(valueOf(cursor.getCount()));


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
                return Integer.parseInt(valueOf(cursor.getCount()));


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
                return Double.parseDouble(valueOf(cursor.getCount()));


            }
        }

        return -1;
    }

    public CustomerDailyReport(String s) {

    }

    public CustomerDailyReport(int i, String s, String savings, String ngn3000, String old, String confirmed) {


    }

    public void setSkylightPackage(SkyLightPackage skyLightPackage) {
        this.recordPackage =skyLightPackage;

    }

    public AdminUser getRecordAdminUser()
    {
        return recordAdminUser;
    }
    public void setSkylightPackage(AdminUser adminUser) {
        this.recordAdminUser =adminUser;

    }

    public SkyLightPackage getRecordPackage()
    {
        return recordPackage;
    }

    public CustomerDailyReport(Profile recordProfile, Customer recordCustomer, Account account, SkyLightPackage recordPackage) {
        this.recordProfile = recordProfile;
        this.recordCustomer = recordCustomer;
        this.recordAccount = account;
        this.recordPackage = recordPackage;

    }


    public CustomerDailyReport(int Package_ID, int reportID, int recordProfileId, int recordCustomerID, String date, double recordAmount, int numberOfDay, double total, double amount_collected_so_far, double recordAmountRemaining, int daysRemaining, String recordStatus) {
        this.recordSerialNumber = valueOf(reportID);
        //this.recordAmount = recordAmount;
        this.recordPackageId = Package_ID;
        this.recordNumberOfDays = numberOfDay;
        this.recordAmount = total;
        this.recordCustomerID = recordCustomerID;
        this.recordProfileId = recordProfileId;
        this.recordRemainingDays = daysRemaining;
        this.recordDate =date;
        this.recordStatus = recordStatus;
        this.recordAmountRemaining = recordAmountRemaining;
    }
    public CustomerDailyReport(int recordProfileId, int recordCustomerID, int id, double recordAmount, int numberOfDay, double total, int daysRemaining, double recordAmountRemaining, String date, String recordStatus) {
        this.recordSerialNumber = valueOf(id);
        //this.recordAmount = recordAmount;
        this.recordNumberOfDays = numberOfDay;
        this.recordAmount = total;
        this.recordCustomerID = recordCustomerID;
        this.recordProfileId = recordProfileId;
        this.recordRemainingDays = daysRemaining;
        this.recordDate =date;
        this.recordStatus = recordStatus;
        this.recordAmountRemaining = recordAmountRemaining;
    }



    public CustomerDailyReport() {

    }

    public CustomerDailyReport(int recordProfileId, int recordCustomerID, int recordPackageId, int reportId, double recordAmount, int recordNumberOfDays, double total, String reportDate, int recordRemainingDays, double recordAmountRemaining, String recordStatus) {
        this.recordSerialNumber = valueOf(recordPackageId);
        //this.recordAmount = recordAmount;
        this.recordPackageId = recordPackageId;
        this.recordNumberOfDays = recordNumberOfDays;
        this.recordAmount = total;
        this.recordCustomerID = recordCustomerID;
        this.recordProfileId = recordProfileId;
        this.recordRemainingDays = recordRemainingDays;
        this.recordDate =reportDate;
        this.recordStatus = recordStatus;
        this.recordAmountRemaining = recordAmountRemaining;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = Integer.parseInt(prime * result + recordSerialNumber);
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
        return recordSerialNumber.equals(other.recordSerialNumber);
    }

    public boolean getRecordRemind() {
        return recordRemind;
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public Object getRemindAlarmDrawable() {
        if (recordRemind) {
            return AppController.getInstance().getResources().getDrawable(R.drawable.ic_alert);
        } else {
            return AppController.getInstance().getResources().getDrawable(R.drawable.ic_alert);
        }
    }


    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_AMOUNT, this.getRecordAmount());
        json.put(JSON_NUMBER_OF_DAYS, this.getRecordNumberOfDays());
        json.put(JSON_REMIND, this.getRecordRemind());
        json.put(JSON_UID, this.getUID());
        json.put(JSON_DAYS_REMAINING, this.getRecordRemainingDays());
        return json;
    }

    public String getUID() {
        String uid = valueOf(this.recordSerialNumber);
        if (uid.isEmpty()) {
            uid = UUID.randomUUID().toString();
            setUID(uid);
        }
        return valueOf(recordSerialNumber);
    }

    public void setUID(String uID) {
        this.recordSerialNumber = uID;
    }

    public String getFormattedDaysRemainingString(int profileID, int skyLightPackageID, int reportID) {
        int i = getDaysBetween();
        Profile profile1=new Profile();
        CustomerDailyReport customerDailyReport = new CustomerDailyReport();
        int reportNumber = customerDailyReport.getColumnCount();
        SkyLightPackage skyLightPackage1;
        int packageId = recordPackage.getPackID();
        int profileId = recordProfile.getPID();
        int j = customerDailyReport.getCount();
        Context context = AppController.getInstance();
        //StringBuilder sb = new StringBuilder("column: ");

        for (int k = 0; i < recordCount; i++) {

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
        recordNumberOfDays = in.readInt();
        recordSerialNumber = in.readString();
        recordCustomerID = in.readInt();
        recordAmount = in.readDouble();
        recordStatus = in.readString();
        completed = in.readByte() != 0;
    }

    public CustomerDailyReport(int recordCount, int recordCustomerID, boolean b, double recordAmount, int recordNumberOfDays, String total, String recordStatus, int recordRemainingDays) {
        this.recordCount = this.getCount();
        this.recordCustomerID = recordCustomerID;
        this.completed =b;
        this.recordAmount = recordAmount;
        this.recordNumberOfDays = recordNumberOfDays;
        this.recordStatus = recordStatus;
        this.recordRemainingDays = recordRemainingDays;

    }

    public CustomerDailyReport(int id, double recordAmount, long recordNumberOfDays, double total, long daysRemaining, double recordAmountRemaining, String date, String recordStatus) {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recordNumberOfDays);
        dest.writeString(recordSerialNumber);
        dest.writeLong(recordCustomerID);
        dest.writeDouble(recordAmount);
        dest.writeString(recordStatus);
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




    public int getRecordRemainingDays()
    {
        return recordRemainingDays;
    }
    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }
    public int getRecordID()
    {
        return recordID;
    }

    public String getRecordSerialNumber()
    {
        return recordSerialNumber;
    }


    public String setReportCount(String serialNumber) {
        this.recordSerialNumber = serialNumber;
        return serialNumber;
    }


    public void setRecordDocLink(Uri recordDocLink) {
        this.recordDocLink = recordDocLink;
    }

    public Uri getRecordDocLink()
    {
        return recordDocLink;
    }


    public void setRecordRemainingDays(int recordRemainingDays)
    {
        this.recordRemainingDays = recordRemainingDays;
    }
    public double getRecordAmountRemaining()
    {
        return recordAmountRemaining;
    }

    public void setRecordAmountRemaining(double recordAmountRemaining)
    {
        this.recordAmountRemaining = recordAmountRemaining;
    }


    public String getRecordDate()
    {
        return recordDate;
    }

    public void setRecordDate(Date recordDate)
    {
        this.recordDate = valueOf(recordDate);
    }

    public Double getRecordAmount()
    {
        return recordAmount;
    }

    public void setRecordAmount(double recordAmount)
    {
        this.recordAmount = recordAmount;
    }

    public Profile getRecordProfile()
    {
        return recordProfile;
    }
    public void setRecordProfile(Profile recordProfile)
    {
        this.recordProfile = recordProfile;
    }

    public int getRecordNumberOfDays() {
        return recordNumberOfDays;
    }
    public void setRecordSavingsCode(long recordSavingsCode)
    {
        this.recordSavingsCode = recordSavingsCode;
    }

    public long getRecordSavingsCode()
    {
        return recordSavingsCode;
    }

    public void setRecordNumberOfDays(int recordNumberOfDays)
    {
        this.recordNumberOfDays = recordNumberOfDays;
    }
    public int getRecordPackageId()
    {
        return recordPackageId;
    }
    public void setRecordPackageId(int recordPackageId)
    {
        this.recordPackageId = recordPackageId;
    }

    public void setRecordSerialNumber(String recordSerialNumber)
    {
        this.recordSerialNumber = recordSerialNumber;
    }

    public CustomerDailyReport(String img, String text)
    {
        this.recordAmount = Double.parseDouble(text);
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
        //return Integer.parseInt(type);
        return i;
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


    public void setRecordDate(String createdDate) {

    }

    public Account getRecordAccount() {
        return recordAccount;
    }

    public void setRecordAccount(Account recordAccount) {
        this.recordAccount = recordAccount;
    }

    public CustomerManager getRecordCustomerManager() {
        return recordCustomerManager;
    }

    public void setRecordCustomerManager(CustomerManager recordCustomerManager) {
        this.recordCustomerManager = recordCustomerManager;
    }

    public Customer getRecordCustomer() {
        return recordCustomer;
    }

    public void setRecordCustomer(Customer recordCustomer) {
        this.recordCustomer = recordCustomer;
    }

    public String getRecordCustomerName() {
        return recordCustomerName;
    }

    public void setRecordCustomerName(String recordCustomerName) {
        this.recordCustomerName = recordCustomerName;
    }

    public String getRecordOfficeBranch() {
        return recordOfficeBranch;
    }

    public void setRecordOfficeBranch(String recordOfficeBranch) {
        this.recordOfficeBranch = recordOfficeBranch;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public PaymentDoc getRecordPaymentDoc() {
        return recordPaymentDoc;
    }

    public void setRecordPaymentDoc(PaymentDoc recordPaymentDoc) {
        this.recordPaymentDoc = recordPaymentDoc;
    }
}