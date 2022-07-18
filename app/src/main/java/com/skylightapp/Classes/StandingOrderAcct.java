package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = StandingOrderAcct.SO_ACCT_TABLE)
public class StandingOrderAcct implements Serializable, Parcelable {
    public static final String SO_ACCT_TABLE = "sOAcctTable";
    public static final String SO_ACCOUNT_NO = "sO_Account_id";
    public static final String SO_ACCOUNT_NAME = "sO_AccountName";
    public static final String SO_ACCOUNT_BALANCE = "sO_AccountBalance";
    public static final String SO_ACCT_CUS_ID = "sO_Acct_cus_id";
    public static final String SO_ACCT_PROF_ID = "sO_Acct_Prof_id";

    public static final String CREATE_SO_ACCT_TABLE = "CREATE TABLE IF NOT EXISTS " + SO_ACCT_TABLE + " (" + SO_ACCOUNT_NO + " INTEGER, " + SO_ACCT_PROF_ID + " INTEGER , " +
            SO_ACCT_CUS_ID + " INTEGER , " +  SO_ACCOUNT_NAME +"TEXT,"+
             " TEXT, " + SO_ACCOUNT_BALANCE + " REAL, " +"FOREIGN KEY(" + SO_ACCT_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "FOREIGN KEY(" + SO_ACCT_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+
            "PRIMARY KEY(" + SO_ACCOUNT_NO +  "))";


    private ArrayList<Transaction> soTranXs;
    @PrimaryKey(autoGenerate = true)
    private int soAcctID=1108213;
    String soAcctName;
    private int soAcctProfID;
    private int soAcctCusID;
    Double soAcctBalance;
    Transaction so_tranx;
    private ArrayList<StandingOrder> soa_standingOrders;
    private static Long nextAcNum = 88769912L;
    public StandingOrderAcct (int soAcctID, String soAcctName, Double soAcctBalance) {
        this.soTranXs = new ArrayList<>();
        this.soAcctID = soAcctID;
        this.soAcctName = soAcctName;
        this.soAcctBalance = soAcctBalance;
        nextAcNum++;
    }

    public StandingOrderAcct() {

    }

    protected StandingOrderAcct(Parcel in) {
        soTranXs = in.createTypedArrayList(Transaction.CREATOR);
        soAcctID = in.readInt();
        soAcctName = in.readString();
        if (in.readByte() == 0) {
            soAcctBalance = null;
        } else {
            soAcctBalance = in.readDouble();
        }
        so_tranx = in.readParcelable(Transaction.class.getClassLoader());
        soa_standingOrders = in.createTypedArrayList(StandingOrder.CREATOR);
    }

    public static final Creator<StandingOrderAcct> CREATOR = new Creator<StandingOrderAcct>() {
        @Override
        public StandingOrderAcct createFromParcel(Parcel in) {
            return new StandingOrderAcct(in);
        }

        @Override
        public StandingOrderAcct[] newArray(int size) {
            return new StandingOrderAcct[size];
        }
    };

    public  void addSOAStandingOrder(int soNo, double expectedAmount) {
        soa_standingOrders = new ArrayList<>();
        soNo = soa_standingOrders.size() + 1;
        StandingOrder business = new StandingOrder(soNo,expectedAmount);
        soa_standingOrders.add(business);

    }


    public ArrayList<StandingOrder> getSoa_standingOrders() { return soa_standingOrders; }
    public void setSoa_standingOrders(ArrayList<StandingOrder> soa_standingOrders) {
        this.soa_standingOrders = soa_standingOrders;

    }


    public double setSoAcctBalance(double soAcctBalance) {
        this.soAcctBalance = soAcctBalance;
        return soAcctBalance;
    }
    public Double getSoAcctBalance() {
        return this.soAcctBalance;
    }

    public int getSoAcctNo() {
        return this.soAcctID;
    }

    public void setSoAccountNo(int soAcctID) {
        this.soAcctID = soAcctID;
    }

    public String getSoAcctName(String soAcctName) {
        this.soAcctName = soAcctName;
        return soAcctName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(soTranXs);
        parcel.writeInt(soAcctID);
        parcel.writeString(soAcctName);
        if (soAcctBalance == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(soAcctBalance);
        }
        parcel.writeParcelable(so_tranx, i);
        parcel.writeTypedList(soa_standingOrders);
    }

    public int getSoAcctProfID() {
        return soAcctProfID;
    }

    public void setSoAcctProfID(int soAcctProfID) {
        this.soAcctProfID = soAcctProfID;
    }

    public int getSoAcctCusID() {
        return soAcctCusID;
    }

    public void setSoAcctCusID(int soAcctCusID) {
        this.soAcctCusID = soAcctCusID;
    }
}
