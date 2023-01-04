package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;


//@Entity(tableName = Payee.PAYEES_TABLE)
public class Payee implements Serializable, Parcelable {
    public static final String PAYEES_TABLE = "payees_table";
    public static final String PAYEE_ID = "payee_id";
    public static final String PAYEE_NAME = "payee_name";
    public static final String PAYEE_CUS_ID = "payee_Cus_ID";
    public static final String PAYEE_PROF_ID = "payee_Prof_ID";
    public static final String PAYEE_AMOUNT = "payee_Amount";
    public static final String PAYEE_DB_ID = "payee_DB_ID";


    public static final String CREATE_PAYEES_TABLE = "CREATE TABLE IF NOT EXISTS " + PAYEES_TABLE + " (" + PAYEE_ID + " INTEGER   , " + PAYEE_CUS_ID + " INTEGER , " + PAYEE_PROF_ID + " INTEGER , " +
            PAYEE_NAME + " TEXT, " + PAYEE_AMOUNT + " REAL , "+ PAYEE_DB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+ "FOREIGN KEY(" + PAYEE_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + ")," + "FOREIGN KEY(" + PAYEE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";


    private String payeeName;
    private String payeePhoneNo;
    private String payeeEmail;
    private String payyBankName;
    private String payeeBankAcctNo;
    private String payeeNeed;
    private int payeeCusID;
    private int payeeProfileID;
    private String payeeStatus;
    //@PrimaryKey(autoGenerate = true)
    private int payeeID=1013;

    public Payee () {
        super();

    }

    public Payee (int payeeID, String payeeName) {
        this.payeeID = payeeID;
        this.payeeName = payeeName;
    }

    public Payee (int payeeID, String payeeName, int dbId) {
        this.payeeID = payeeID;
        this.payeeName = payeeName;
        this.payeeProfileID = dbId;
    }

    public Payee(int payeeID, String payeePhoneNo, String payeeEmail, String payyBankName, String name, String payeeBankAcctNo, String payeeNeed, String payeeStatus) {
        this.payeeID = payeeID;
        this.payeePhoneNo = payeePhoneNo;
        this.payeeEmail = payeeEmail;
        this.payyBankName = payyBankName;
        this.payeeBankAcctNo = payeeBankAcctNo;
        this.payeeNeed = payeeNeed;
        this.payeeStatus = payeeStatus;
    }
    public Payee(int payeeID, int profID, String payeeName) {
        this.payeeID = payeeID;
        this.payeeProfileID = profID;
        this.payeeName = payeeName;

    }


    protected Payee(Parcel in) {
        payeeID = in.readInt();
        payeeName = in.readString();
        payeePhoneNo = in.readString();
        payeeEmail = in.readString();
        payyBankName = in.readString();
        payeeBankAcctNo = in.readString();
        payeeNeed = in.readString();
        payeeStatus = in.readString();
        payeeID = in.readInt();
    }

    public static final Creator<Payee> CREATOR = new Creator<Payee>() {
        @Override
        public Payee createFromParcel(Parcel in) {
            return new Payee(in);
        }

        @Override
        public Payee[] newArray(int size) {
            return new Payee[size];
        }
    };



    public String getPayeeName() {
        return payeeName;
    }
    public int getPayeeID() { return payeeID; }

    public void setPayeeDbId(int payeeDbId) {
        this.payeeID = payeeDbId;
    }

    @NonNull
    public String toString() { return (payeeName + " (" + payeeID + ")"); }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(payeeID);
        parcel.writeString(payeeName);
        parcel.writeString(payeePhoneNo);
        parcel.writeString(payeeEmail);
        parcel.writeString(payyBankName);
        parcel.writeString(payeeBankAcctNo);
        parcel.writeString(payeeNeed);
        parcel.writeString(payeeStatus);
        parcel.writeLong(payeeID);
    }

    public int getPayeeCusID() {
        return payeeCusID;
    }

    public void setPayeeCusID(int payeeCusID) {
        this.payeeCusID = payeeCusID;
    }

    public int getPayeeProfileID() {
        return payeeProfileID;
    }

    public void setPayeeProfileID(int payeeProfileID) {
        this.payeeProfileID = payeeProfileID;
    }
}
