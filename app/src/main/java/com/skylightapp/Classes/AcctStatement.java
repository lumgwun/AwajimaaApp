package com.skylightapp.Classes;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = AcctStatement.ACCT_STATEMENT_TABLE)
public class AcctStatement implements Serializable, Parcelable {

    public static final String ACCT_STATEMENT_TABLE = "Acct_Statement_Table";
    public static final String ACCT_STATEMENT_ID = "acct_statement_ID";
    public static final String ACCT_STATEMENT_TOTAL_SAVING = "Total_savings";
    public static final String ACCT_STATEMENT_TOTAL_WITHDRAWALS = "Total_withdrawals";
    public static final String ACCT_STATEMENT_TOTAL_SO = "Total_SO";
    public static final String ACCT_STATEMENT_TOTAL_GRP_SAVINGS = "Total_Grp_Savings";
    public static final String ACCT_STATEMENT_SONO = "SO_No";
    public static final String ACCT_STATEMENT_GRPNO = "Grp_Acct_No";
    public static final String ACCT_STATEMENT_EWALLET_NO = "Ewallet_No";
    public static final String ACCT_STATEMENT_DATE = "Statement_Date";
    public static final String ACCT_STATEMENT_TOTAL_CASH_DEPOSIT = "Total_Cash_deposit";
    public static final String ACCT_STATEMENT_TOTAL_BANK_DEPOSIT = "Total_Bank_deposit";
    public static final String ACCT_STATEMENT_TOTAL_LOANS = "Loans";
    public static final String ACCT_STATEMENT_NoOFSAVINGS = "No_Of_Savings";
    public static final String ACCT_STATEMENT_NoOFSO = "No_Of_SO";
    public static final String ACCT_STATEMENT_BALANCE = "Statement_Balance";
    public static final String ACCT_STATEMENT_NAME = "Statement_Cus_Name";

    /*public static final String CREATE_ACCOUNTS_STATEMENT_TABLE = "CREATE TABLE IF NOT EXISTS " + ACCT_STATEMENT_TABLE + " (" + ACCT_STATEMENT_ID + " LONG, " + CUSTOMER_ID + "LONG,"+ ACCT_STATEMENT_NAME + " DOUBLE , " +  PROFILE_ID + " LONG , " + ACCT_STATEMENT_DATE + " DATE , " + ACCT_STATEMENT_SONO + " LONG , " + ACCT_STATEMENT_EWALLET_NO + " TEXT , " +
            ACCT_STATEMENT_GRPNO + " LONG , " + ACCT_STATEMENT_TOTAL_SAVING + " DOUBLE , " + ACCT_STATEMENT_TOTAL_SO + " DOUBLE, " + ACCT_STATEMENT_TOTAL_GRP_SAVINGS + " DOUBLE, " +
            ACCT_STATEMENT_TOTAL_CASH_DEPOSIT + " DOUBLE, " + ACCT_STATEMENT_TOTAL_BANK_DEPOSIT + " DOUBLE, "+ ACCT_STATEMENT_TOTAL_LOANS + " DOUBLE , "+ ACCT_STATEMENT_NoOFSAVINGS + " INTEGER , "+ ACCT_STATEMENT_TOTAL_WITHDRAWALS + " DOUBLE , "+ ACCT_STATEMENT_NoOFSO + " DOUBLE , " + ACCT_STATEMENT_BALANCE + " DOUBLE , " + "FOREIGN KEY(" + CUSTOMER_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + "),"+
            "PRIMARY KEY(" + ACCT_STATEMENT_ID +  "))";*/


    @PrimaryKey(autoGenerate = true)
    private int statementId=152;
    private int walletAcct_No;
    private int soNo;
    private long grpSavingsAcctNo;
    private Uri logo;
    private String nameOfCustomer;
    private double totalSavings;
    private double totalSOs;
    private double totalBankDeposits;
    private double totalCashDeposits;
    private double grpSavingsAmt;
    private double grpSavingsBalance;
    private double totalLoans;
    private double totalWithdrawals;
    private double balance;
    private String date;
    private int noOfSavings;
    private int noOfSo;
    private int noOfGrpSavings;


    protected AcctStatement(Parcel in) {
        statementId = in.readInt();
        walletAcct_No = in.readInt();
        soNo = in.readInt();
        grpSavingsAcctNo = in.readLong();
        logo = in.readParcelable(Uri.class.getClassLoader());
        nameOfCustomer = in.readString();
        totalSavings = in.readDouble();
        totalSOs = in.readDouble();
        totalLoans = in.readDouble();
        totalWithdrawals = in.readDouble();
        balance = in.readDouble();
        date = in.readString();
        noOfSavings = in.readInt();
        noOfSo = in.readInt();
        noOfGrpSavings = in.readInt();
    }

    public static final Creator<AcctStatement> CREATOR = new Creator<AcctStatement>() {
        @Override
        public AcctStatement createFromParcel(Parcel in) {
            return new AcctStatement(in);
        }

        @Override
        public AcctStatement[] newArray(int size) {
            return new AcctStatement[size];
        }
    };

    public void setAcctStatementId(int statementId) {
        this.statementId = statementId;
    }
    public long getStatementId() {
        return this.statementId;
    }

    public void setAcctStatementEwalletNo(int walletAcct_No) {
        this.walletAcct_No = walletAcct_No;
    }
    public long getWalletAcct_No() {
        return this.walletAcct_No;
    }


    public void setAcctStatementSono(int soNo) {
        this.soNo = soNo;
    }

    public long getSoNo() {
        return this.soNo;
    }
    public void setGrpSavingsAcctNo(long grpSavingsAcctNo) {
        this.grpSavingsAcctNo = grpSavingsAcctNo;
    }

    public long getGrpSavingsAcctNo() {
        return this.grpSavingsAcctNo;
    }
    public void setLogo(Uri logo) {
        this.logo = logo;
    }

    public Uri getLogo() {
        return this.logo;
    }
    public void setNameOfCustomer(String nameOfCustomer) {
        this.nameOfCustomer = nameOfCustomer;
    }

    public String getNameOfCustomer() {
        return this.nameOfCustomer;
    }
    public void setTotalSavings(double totalSavings) {
        this.totalSavings = totalSavings;
    }

    public double getTotalSavings() {
        return this.totalSavings;
    }
    public void setTotalSOs(double totalSOs) {
        this.totalSOs = totalSOs;
    }
    public double getGrpSavingsBalance() {
        return this.grpSavingsBalance;
    }
    public void setGrpSavingsBalance(double grpSavingsBalance) {
        this.grpSavingsBalance = grpSavingsBalance;
    }
    public double getGrpSavingsAmt() {
        return this.grpSavingsAmt;
    }
    public void setGrpSavingsAmt(double grpSavingsAmt) {
        this.grpSavingsAmt = grpSavingsAmt;
    }
    public double getTotalCashDeposits() {
        return this.totalCashDeposits;
    }
    public void setTotalCashDeposits(double totalCashDeposits) {
        this.totalCashDeposits = totalCashDeposits;
    }
    public double getTotalBankDeposits() {
        return this.totalBankDeposits;
    }
    public void setTotalBankDeposits(double totalBankDeposits) {
        this.totalBankDeposits = totalBankDeposits;
    }



    public double getTotalSOs() {
        return this.totalSOs;
    }
    public void setTotalLoans(double totalLoans) {
        this.totalLoans = totalLoans;
    }

    public double getTotalLoans() {
        return this.totalLoans;
    }
    public void setTotalWithdrawals(double totalWithdrawals) {
        this.totalWithdrawals = totalWithdrawals;
    }

    public double getTotalWithdrawals() {
        return this.totalWithdrawals;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }
    public void setNoOfSavings(int noOfSavings) {
        this.noOfSavings = noOfSavings;
    }

    public int getNoOfSavings() {
        return this.noOfSavings;
    }
    public void setNoOfSo(int noOfSo) {
        this.noOfSo = noOfSo;
    }

    public int getNoOfSo() {
        return this.noOfSo;
    }
    public void setNoOfGrpSavings(int noOfGrpSavings) {
        this.noOfGrpSavings = noOfGrpSavings;
    }

    public int getNoOfGrpSavings() {
        return this.noOfGrpSavings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(statementId);
        parcel.writeLong(walletAcct_No);
        parcel.writeLong(soNo);
        parcel.writeLong(grpSavingsAcctNo);
        parcel.writeParcelable(logo, i);
        parcel.writeString(nameOfCustomer);
        parcel.writeDouble(totalSavings);
        parcel.writeDouble(totalSOs);
        parcel.writeDouble(totalLoans);
        parcel.writeDouble(totalWithdrawals);
        parcel.writeDouble(balance);
        parcel.writeString(date);
        parcel.writeInt(noOfSavings);
        parcel.writeInt(noOfSo);
        parcel.writeInt(noOfGrpSavings);
    }
}
