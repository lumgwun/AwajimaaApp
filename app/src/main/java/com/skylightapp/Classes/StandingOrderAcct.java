package com.skylightapp.Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = StandingOrderAcct.SO_ACCT_TABLE)
public class StandingOrderAcct extends  Account{
    public static final String SO_ACCT_TABLE = "SOAcctTable";
    public static final String SO_ACCOUNT_NO = "sO_Account_id";
    public static final String SO_ACCOUNT_NAME = "SO_AccountName";
    public static final String SO_ACCOUNT_BALANCE = "SO_AccountBalance";
    public static final String SO_FIXED_AMOUNT = "SO_Amount";
    public static final String SO_EXPECTED_SAVINGS = "TotalAmountToSave";
    public static final String SO_SAVED_AMOUNT = "SavedAmount";
    public static final String SO_ACCT_CUS_ID = "SO_Acct_cus_id";
    public static final String SO_ACCT_PROF_ID = "SO_Acct_Prof_id";

    public static final String CREATE_SO_ACCT_TABLE = "CREATE TABLE IF NOT EXISTS " + SO_ACCT_TABLE + " (" + SO_ACCOUNT_NO + " INTEGER, " + SO_ACCT_PROF_ID + " INTEGER , " +
            SO_ACCT_CUS_ID + " INTEGER , " +  SO_ACCOUNT_NAME +"TEXT,"+
             " TEXT, " + SO_ACCOUNT_BALANCE + " DOUBLE, " +"FOREIGN KEY(" + SO_ACCT_PROF_ID  + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "),"+ "FOREIGN KEY(" + SO_ACCT_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+
            "PRIMARY KEY(" + SO_ACCOUNT_NO +  "))";


    private ArrayList<Transaction> transactions;
    @PrimaryKey(autoGenerate = true)
    private int soAcctID=1108213;
    String soAcctName;
    Double soAcctBalance;
    Transaction transaction;
    private ArrayList<StandingOrder> standingOrders;
    private static Long nextAcNum = 88769912L;
    public StandingOrderAcct (int soAcctID, String soAcctName, Double soAcctBalance) {
        this.transactions = new ArrayList<>();
        this.soAcctID = soAcctID;
        this.soAcctName = soAcctName;
        this.soAcctBalance = soAcctBalance;
        nextAcNum++;
    }

    public StandingOrderAcct() {

    }

    public  void addStandingOrder(int soNo, double expectedAmount) {
        standingOrders= new ArrayList<>();
        soNo = standingOrders.size() + 1;
        StandingOrder business = new StandingOrder(soNo,expectedAmount);
        standingOrders.add(business);

    }


    public ArrayList<StandingOrder> getStandingOrders() { return standingOrders; }
    public void setStandingOrders(ArrayList<StandingOrder> standingOrders) {
        this.standingOrders = standingOrders;

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
}
