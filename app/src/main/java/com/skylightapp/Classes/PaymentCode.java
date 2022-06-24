package com.skylightapp.Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.PaymentCode.CODE_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_ID;
import static com.skylightapp.Classes.SkyLightPackage.PACKAGE_TABLE;


@Entity(tableName = CODE_TABLE)
public class PaymentCode extends  CustomerDailyReport implements Serializable {
    private String ownerPhone;
    private long code;
    private String codeDate;
    private String codeStatus;
    private String codeManager;
    private int customer_ID;
    @PrimaryKey(autoGenerate = true)
    private int code_ID=101401;
    private int savingsID;

    public static final String CODE_OWNER = "code_owner";
    public static final String CODE_TABLE = "code_table";
    public static final String CODE_OWNER_PHONE = "code_owner_phone";
    public static final String CODE_ID = "payment_code_id";
    public static final String CODE_PIN = "paymentCode";
    public static final String CODE_DATE = "code_date";
    public static final String CODE_STATUS = "code_status";
    public static final String CODE_MANAGER = "code_manager";
    public static final String CODE_CUS_ID = "code_Cus_ID";
    public static final String CODE_PROFILE_ID = "code_Prof_ID";
    public static final String CODE_REPORT_NO = "code_Report_No";

    public static final String CREATE_CODE_TABLE = "CREATE TABLE IF NOT EXISTS " + CODE_TABLE + " (" + CODE_CUS_ID + " INTEGER NOT NULL, " + CODE_REPORT_NO + " INTEGER , " + CODE_OWNER_PHONE + " TEXT , " +
            CODE_PIN + " LONG , " + CODE_DATE + " TEXT , " + CODE_STATUS + " TEXT , " + CODE_MANAGER + " TEXT , " + CODE_ID + " INTEGER PRIMARY KEY , "+ CODE_PROFILE_ID + " INTEGER , "+ "FOREIGN KEY(" + CODE_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," + "FOREIGN KEY(" + CODE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+  "FOREIGN KEY(" + CODE_REPORT_NO + ") REFERENCES " + DAILY_REPORT_TABLE + "(" + REPORT_ID + "))";

    private CustomerDailyReport customerDailyReport;

    public PaymentCode () {

    }

    public PaymentCode(int customer_ID,String CODE_OWNER_PHONE, long CODE_PIN,String CODE_DATE,String CODE_STATUS,String CODE_MANAGER) {
        this.customer_ID = customer_ID;
        this.ownerPhone = CODE_OWNER_PHONE;
        this.code = CODE_PIN;
        this.codeDate = CODE_DATE;
        this.codeManager = CODE_MANAGER;
        this.codeStatus = CODE_STATUS;

    }
    public PaymentCode(int customer_ID,int savingsID, long CODE_PIN,String CODE_DATE,String CODE_STATUS) {
        this.customer_ID = customer_ID;
        this.savingsID = savingsID;
        this.code = CODE_PIN;
        this.codeDate = CODE_DATE;
        this.codeStatus = CODE_STATUS;

    }
    public PaymentCode(int customer_ID,int savingsID, long CODE_PIN,String CODE_DATE) {
        this.customer_ID = customer_ID;
        this.savingsID = savingsID;
        this.code = CODE_PIN;
        this.codeDate = CODE_DATE;

    }
    public int getSavingsID() {
        return savingsID;
    }
    public void setSavingsID(int savingsID) {
        this.savingsID = savingsID;
    }


    public int getCustomer_ID() {
        return customer_ID;
    }
    public void setCustomer_ID(int customer_ID) {
        this.customer_ID = customer_ID;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }
    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }


    public long getCode() {
        return code;
    }
    public void setCode(long code) {
        this.code = code;
    }

    public int getCodeID() {
        return code_ID;
    }
    public void setCode(int codeID) {
        this.code_ID = codeID;
    }


    public String getCodeDate() {
        return codeDate;
    }
    public void setCodeDate(String codeDate) {
        this.codeDate = codeDate;
    }


    public String getCodeStatus() {
        return codeStatus;
    }
    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }


    public String getCodeManager() { return codeManager; }
    public void setCodeManager(String codeManager) {
        this.codeManager = codeManager;
    }

    public CustomerDailyReport getCustomerDailyReport() {
        return customerDailyReport;
    }

    public void setCustomerDailyReport(CustomerDailyReport customerDailyReport) {
        this.customerDailyReport = customerDailyReport;
    }
}
