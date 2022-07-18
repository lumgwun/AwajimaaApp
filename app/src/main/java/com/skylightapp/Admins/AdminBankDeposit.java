package com.skylightapp.Admins;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.skylightapp.Classes.Profile;
import com.skylightapp.Classes.Transaction;

import java.io.Serializable;
import java.util.Currency;
import java.util.Date;

import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;

@Entity(tableName = AdminBankDeposit.DEPOSIT_TABLE)
public class AdminBankDeposit implements Serializable, Parcelable {
    public static final String DEPOSIT_TABLE = "Deposit_Table";
    public static final String DEPOSIT_ID = "D_id";
    public static final String DEPOSIT_AMOUNT = "D_Amount";
    public static final String DEPOSIT_DATE = "D_Date";
    public static final String DEPOSIT_ACCOUNT = "DAccount";
    public static final String DEPOSIT_BANK = "D_Bank";
    public static final String DEPOSIT_ACCOUNT_NAME = "D_Account_Name";
    public static final String DEPOSITOR = "admin_Depositor";
    public static final String DEPOSIT_TRANSACTION_STATUS = "D_status";

    public static final String DEPOSIT_TRANS_TYPE = "D_Type";
    public static final String DEPOSIT_OFFICE_BRANCH = "transaction_Office_Branch";
    public static final String DEPOSIT_APPROVER = "transaction_Approver";
    public static final String DEPOSIT_CONFIRMATION_DATE = "DConfirmation_Date";
    public static final String DEPOSIT_DOC = "deposit_doc";
    public static final String DEPOSIT_PROFILE_ID = "deposit_Profile_Doc";


    public static final String CREATE_ADMIN_DEPOSIT_TABLE = "CREATE TABLE IF NOT EXISTS " + DEPOSIT_TABLE + " (" + DEPOSIT_ID + " LONG, " + DEPOSIT_PROFILE_ID + " INTEGER , " + DEPOSIT_AMOUNT + " FLOAT , " +
            DEPOSIT_OFFICE_BRANCH + " TEXT, " + DEPOSIT_TRANS_TYPE + " TEXT , " + DEPOSIT_BANK + " TEXT, " + DEPOSIT_ACCOUNT_NAME + " TEXT, " +
            DEPOSIT_ACCOUNT + " TEXT, " + DEPOSIT_DATE + " TEXT, " + DEPOSITOR + " TEXT, " + DEPOSIT_APPROVER + " TEXT, " +
            DEPOSIT_CONFIRMATION_DATE + " TEXT, " + DEPOSIT_TRANSACTION_STATUS + " TEXT, " +  DEPOSIT_DOC + " TEXT, " +"PRIMARY KEY(" +DEPOSIT_ID + "), " +"FOREIGN KEY(" + DEPOSIT_PROFILE_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";




    @PrimaryKey(autoGenerate = true)
    int depositID=1182;

    int profileID;
    String depositDate;
    String depositAcctNo;
     String depositBank;
     double depositAmount;
    String depositAcctName;
    String depositor;
    String depositStatus;

    String depositType;
    String depositBranchOffice;
    String depositApprover;
    String depositApprovalDate;

    public AdminBankDeposit(int depositID, int profileID, double depositAmt, String officeBranch, String type, String bank, String acctName, String acctNo, String date, String depositor, String depositApprover, String confirmationDate, String status) {
        this.profileID = profileID;
        this.depositID = depositID;
        this.depositAmount = depositAmt;
        this.depositBranchOffice = officeBranch;
        this.depositType = type;
        this.depositBank = bank;
        this.depositAcctName = acctName;
        this.depositAcctNo = acctNo;
        this.depositDate = date;
        this.depositor = depositor;
        this.depositApprovalDate = confirmationDate;
        this.depositApprover = depositApprover;
        this.depositStatus = status;
    }

    public AdminBankDeposit(double depositAmt, String officeBranch, String type, String bank, String acctName, String acctNo, String date, String depositor, String status) {
        this.depositAmount = depositAmt;
        this.depositBranchOffice = officeBranch;
        this.depositType = type;
        this.depositBank = bank;
        this.depositAcctName = acctName;
        this.depositAcctNo = acctNo;
        this.depositDate = date;
        this.depositor = depositor;
        this.depositStatus = status;
    }

    protected AdminBankDeposit(Parcel in) {
        depositID = in.readInt();
        profileID = in.readInt();
        depositDate = in.readString();
        depositAcctNo = in.readString();
        depositBank = in.readString();
        depositAmount = in.readDouble();
        depositAcctName = in.readString();
        depositor = in.readString();
        depositStatus = in.readString();
        depositType = in.readString();
        depositBranchOffice = in.readString();
        depositApprover = in.readString();
        depositApprovalDate = in.readString();
    }
    public AdminBankDeposit(int depositID, int senderProfileID, String adminName, String dateOfReport, String officeBranch, String selectedBank, double amountEntered) {
        this.depositID = depositID;
        this.profileID = senderProfileID;
        this.depositor = adminName;
        this.depositDate = dateOfReport;
        this.depositBranchOffice = officeBranch;
        this.depositBank = selectedBank;
        this.depositAmount = amountEntered;
    }
    public AdminBankDeposit(int keyExtraReportId, String officeBranch, String selectedBank, double amountEntered, Date reportDate) {
        this.depositor = depositor;
        this.depositAmount = amountEntered;
        this.depositBranchOffice = officeBranch;
        this.depositDate = String.valueOf(reportDate);
        this.depositBank = selectedBank;
    }

    public static final Creator<AdminBankDeposit> CREATOR = new Creator<AdminBankDeposit>() {
        @Override
        public AdminBankDeposit createFromParcel(Parcel in) {
            return new AdminBankDeposit(in);
        }

        @Override
        public AdminBankDeposit[] newArray(int size) {
            return new AdminBankDeposit[size];
        }
    };

    public AdminBankDeposit() {
        super();
    }




    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getDepositOfficeBranch() {
        return depositBranchOffice;
    }

    public void setDepositOfficeBranch(String depositBranchOffice) {
        this.depositBranchOffice = depositBranchOffice;
    }
    public String getDepositApprover() {
        return depositApprover;
    }

    public void setDepositApprover(String depositApprover) {
        this.depositApprover = depositApprover;
    }


    public String getDepositApprovalDate() {
        return depositApprovalDate;
    }

    public void setDepositApprovalDate(String depositApprovalDate) {
        this.depositApprovalDate = depositApprovalDate;
    }


    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }
    public String getDepositor() {
        return depositor;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }
    public String getDepositBank() {
        return depositBank;
    }

    public String getDepositAcctNo() {
        return depositAcctNo;
    }

    public void setDepositAcctNo(String depositAcctNo) {
        this.depositAcctNo = depositAcctNo;
    }

    public String getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(String depositDate) {
        this.depositDate = depositDate;
    }

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public int getDepositID() {
        return depositID;
    }

    public void setDepositID(int depositID) {
        this.depositID = depositID;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getDepositAcctName() {
        return depositAcctName;
    }

    public void setDepositAcctName(String depositAcctName) {
        this.depositAcctName = depositAcctName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(depositID);
        parcel.writeLong(profileID);
        parcel.writeString(depositDate);
        parcel.writeString(depositAcctNo);
        parcel.writeString(depositBank);
        parcel.writeDouble(depositAmount);
        parcel.writeString(depositAcctName);
        parcel.writeString(depositor);
        parcel.writeString(depositStatus);
        parcel.writeString(depositType);
        parcel.writeString(depositBranchOffice);
        parcel.writeString(depositApprover);
        parcel.writeString(depositApprovalDate);
    }
}
