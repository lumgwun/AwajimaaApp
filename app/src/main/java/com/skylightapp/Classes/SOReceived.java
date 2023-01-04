package com.skylightapp.Classes;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;
import static com.skylightapp.Classes.StandingOrder.SO_ID;
import static com.skylightapp.Classes.StandingOrder.STANDING_ORDER_TABLE;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCOUNT_NO;
import static com.skylightapp.Classes.StandingOrderAcct.SO_ACCT_TABLE;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SOReceived implements Serializable, Parcelable {
    private int sorID;
    private int sorSOID;
    private int sorSOAcctNo;
    private int sorProfileID;
    private  int sorCusID;
    private String sorTranxRef;
    private String sorTranxDate;
    private String sorStatus;
    private double sorAmount;
    private int sorManagerID;
    private String sorManagerName;
    private int sorOfficeID;
    private String sorComment;
    private String sorCusName;
    private String sorPlatformType;
    private double sorAmountRem;

    public static final String SO_RECEIVED_TABLE = "sO_Received_Table";
    public static final String SOR_ID = "sOR_id";
    public static final String SOR_AMOUNT = "SoR_Amount";
    public static final String SOR_PROF_ID = "soR_Prof_ID";
    public static final String SOR_CUS_ID = "soR_Cus_Id";
    public static final String SOR_SOACCT_NO = "soR_SO_Acct_NO";
    public static final String SOR_SO_ID = "soR_SO_ID";
    public static final String SOR_OFFICE_ID = "soR_Office_ID";
    public static final String SOR_STATUS = "SoR_status";
    public static final String SOR_DBID = "SO_DBID";
    public static final String SOR_DATE = "SOR_Date";
    public static final String SOR_ManagerID = "SO_ManagerID";
    public static final String SOR_MANAGER_NAME = "SOR_Manager_Name";
    public static final String SOR_TX_REF = "SOR_TX_Ref";
    public static final String SOR_COMMENT = "SOR_Comment";
    public static final String SOR_CUS_NAME = "SOR_Cus_Name";
    public static final String SOR_PLATFORM = "SOR_Platform";
    public static final String SOR_AMT_REM = "SOR_Amt_Rem";

    public static final String CREATE_SO_RECEIVED_TABLE = "CREATE TABLE IF NOT EXISTS " + SO_RECEIVED_TABLE + " (" + SOR_DBID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SOR_ID + " INTEGER, " +
            SOR_SO_ID + " INTEGER , " + SOR_ManagerID + " REAL , " + SOR_PROF_ID + " INTEGER , " + SOR_CUS_ID + " INTEGER , " + SOR_SOACCT_NO + " REAL , " +
            SOR_AMOUNT + " REAL , " + SOR_TX_REF + " TEXT , " + SOR_DATE + " INTEGER , " + SOR_MANAGER_NAME + " TEXT, " + SOR_OFFICE_ID + " TEXT, "+ SOR_STATUS + " TEXT, " + SOR_COMMENT + " TEXT, "+ SOR_CUS_NAME + " TEXT , "+ SOR_PLATFORM + " TEXT , "+ SOR_AMT_REM + " TEXT , "  + "FOREIGN KEY(" + SOR_SO_ID + ") REFERENCES " + STANDING_ORDER_TABLE + "(" + SO_ID + ")," +"FOREIGN KEY(" + SOR_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "FOREIGN KEY(" + SOR_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";


    public SOReceived(Parcel in) {
        sorSOID = in.readInt();
        sorSOAcctNo = in.readInt();
        sorProfileID = in.readInt();
        sorCusID = in.readInt();
        sorTranxRef = in.readString();
        sorTranxDate = in.readString();
        sorStatus = in.readString();
        sorAmount = in.readDouble();
        sorManagerID = in.readInt();
        sorManagerName = in.readString();
        sorOfficeID = in.readInt();
    }

    public static final Creator<SOReceived> CREATOR = new Creator<SOReceived>() {
        @Override
        public SOReceived createFromParcel(Parcel in) {
            return new SOReceived(in);
        }

        @Override
        public SOReceived[] newArray(int size) {
            return new SOReceived[size];
        }
    };
    public SOReceived() {
        super();

    }

    public SOReceived(int soRID, int profID, int customerID,String sorCusName, int soRSoID, int soRManagerID, int sorSoAcctNo, int sorOfficeID, double sorAmount, String sor_txRef, String date, String managerName, String soPlatform,String soComment, String soStatus) {
        this.sorID = soRID;
        this.sorProfileID = profID;
        this.sorCusID = customerID;
        this.sorSOID = soRSoID;
        this.sorManagerID = soRManagerID;
        this.sorTranxDate = date;
        this.sorSOAcctNo = sorSoAcctNo;
        this.sorOfficeID = sorOfficeID;
        this.sorAmount = sorAmount;
        this.sorTranxRef = sor_txRef;
        this.sorTranxDate = date;
        this.sorStatus = soStatus;
        this.sorManagerName = managerName;
        this.sorCusName = sorCusName;
        this.sorComment = soComment;
        this.sorPlatformType = soPlatform;

    }

    public int getSorSOID() {
        return sorSOID;
    }

    public void setSorSOID(int sorSOID) {
        this.sorSOID = sorSOID;
    }

    public int getSorProfileID() {
        return sorProfileID;
    }

    public void setSorProfileID(int sorProfileID) {
        this.sorProfileID = sorProfileID;
    }

    public int getSorCusID() {
        return sorCusID;
    }

    public void setSorCusID(int sorCusID) {
        this.sorCusID = sorCusID;
    }

    public String getSorTranxRef() {
        return sorTranxRef;
    }

    public void setSorTranxRef(String sorTranxRef) {
        this.sorTranxRef = sorTranxRef;
    }

    public String getSorTranxDate() {
        return sorTranxDate;
    }

    public void setSorTranxDate(String sorTranxDate) {
        this.sorTranxDate = sorTranxDate;
    }

    public String getSorStatus() {
        return sorStatus;
    }

    public void setSorStatus(String sorStatus) {
        this.sorStatus = sorStatus;
    }

    public double getSorAmount() {
        return sorAmount;
    }

    public void setSorAmount(double sorAmount) {
        this.sorAmount = sorAmount;
    }

    public int getSorManagerID() {
        return sorManagerID;
    }

    public void setSorManagerID(int sorManagerID) {
        this.sorManagerID = sorManagerID;
    }

    public String getSorManagerName() {
        return sorManagerName;
    }

    public void setSorManagerName(String sorManagerName) {
        this.sorManagerName = sorManagerName;
    }

    public int getSorOfficeID() {
        return sorOfficeID;
    }

    public void setSorOfficeID(int sorOfficeID) {
        this.sorOfficeID = sorOfficeID;
    }

    public int getSorSOAcctNo() {
        return sorSOAcctNo;
    }

    public void setSorSOAcctNo(int sorSOAcctNo) {
        this.sorSOAcctNo = sorSOAcctNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(sorSOID);
        parcel.writeInt(sorSOAcctNo);
        parcel.writeInt(sorProfileID);
        parcel.writeInt(sorCusID);
        parcel.writeString(sorTranxRef);
        parcel.writeString(sorTranxDate);
        parcel.writeString(sorStatus);
        parcel.writeDouble(sorAmount);
        parcel.writeInt(sorManagerID);
        parcel.writeString(sorManagerName);
        parcel.writeInt(sorOfficeID);
        parcel.writeString(sorComment);

    }

    public String getSorComment() {
        return sorComment;
    }

    public void setSorComment(String sorComment) {
        this.sorComment = sorComment;
    }

    public int getSorID() {
        return sorID;
    }

    public void setSorID(int sorID) {
        this.sorID = sorID;
    }

    public String getSorCusName() {
        return sorCusName;
    }

    public void setSorCusName(String sorCusName) {
        this.sorCusName = sorCusName;
    }

    public String getSorPlatformType() {
        return sorPlatformType;
    }

    public void setSorPlatformType(String sorPlatformType) {
        this.sorPlatformType = sorPlatformType;
    }

    public double getSorAmountRem() {
        return sorAmountRem;
    }

    public void setSorAmountRem(double sorAmountRem) {
        this.sorAmountRem = sorAmountRem;
    }
}
