package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;


@Entity(tableName = Payee.PAYEES_TABLE)
public class Payee implements Serializable, Parcelable {
    public static final String PAYEES_TABLE = "payees_table";

    public static final String PAYEE_ID = "payee_id";
    public static final String PAYEE_NAME = "payee_name";


    public static final String CREATE_PAYEES_TABLE = "CREATE TABLE IF NOT EXISTS " + PAYEES_TABLE + " (" + PAYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT  , " + CUSTOMER_ID + " INTEGER , " + PROFILE_ID + " INTEGER , " +
            PAYEE_NAME + " TEXT, " + "FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "))";


    private String payeeID;
    private String payeeName;
    private String phoneNo;
    private String email;
    private String bankName;
    private long bankAcctNo;
    private String need;
    private String status;
    @PrimaryKey(autoGenerate = true)
    private int payeeDbId=1013;

    public Payee (String payeeID, String payeeName) {
        this.payeeID = payeeID;
        this.payeeName = payeeName;
    }

    public Payee (String payeeID, String payeeName, int dbId) {
        this(payeeID, payeeName);
        this.payeeDbId = dbId;
    }

    public Payee(int payeeID, String phoneNo, String email, String bankName, String name, long bankAcctNo, String need, String status) {
        this.payeeDbId = payeeID;
        this.phoneNo = phoneNo;
        this.email = email;
        this.bankName = bankName;
        this.bankAcctNo = bankAcctNo;
        this.need = need;
        this.status = status;
    }


    protected Payee(Parcel in) {
        payeeID = in.readString();
        payeeName = in.readString();
        phoneNo = in.readString();
        email = in.readString();
        bankName = in.readString();
        bankAcctNo = in.readLong();
        need = in.readString();
        status = in.readString();
        payeeDbId = in.readInt();
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
    public String getPayeeID() { return payeeID; }

    public void setPayeeDbId(int payeeDbId) {
        this.payeeDbId = payeeDbId;
    }

    @NonNull
    public String toString() { return (payeeName + " (" + payeeID + ")"); }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(payeeID);
        parcel.writeString(payeeName);
        parcel.writeString(phoneNo);
        parcel.writeString(email);
        parcel.writeString(bankName);
        parcel.writeLong(bankAcctNo);
        parcel.writeString(need);
        parcel.writeString(status);
        parcel.writeLong(payeeDbId);
    }
}
