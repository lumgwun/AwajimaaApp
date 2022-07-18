package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_ID;
import static com.skylightapp.Classes.UserSuperAdmin.SUPER_ADMIN_TABLE;

@Entity(tableName = OfficeBranch.OFFICE_BRANCH_TABLE)
public class OfficeBranch implements Parcelable, Serializable{
    public static final String OFFICE_BRANCH_TABLE = "office_branch_table";
    public static final String OFFICE_BRANCH_ID = "office_space_id";
    public static final String OFFICE_BRANCH_NAME = "office_branch_name";
    public static final String OFFICE_BRANCH_DATE = "office_branch_date";
    public static final String OFFICE_BRANCH_ADDRESS = "office_branch_address";
    public static final String OFFICE_BRANCH_APPROVER = "office_branch_approver";
    public static final String OFFICE_BRANCH_STATUS = "office_branch_status";
    public static final String OFFICE_SUPERADMIN_ID = "officeS_superAdmin_id";

    public static final String CREATE_OFFICE_BRANCH = "CREATE TABLE IF NOT EXISTS " + OFFICE_BRANCH_TABLE + " (" + OFFICE_BRANCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + OFFICE_SUPERADMIN_ID + " INTEGER , " +
            OFFICE_BRANCH_NAME + " TEXT , " + OFFICE_BRANCH_DATE + " TEXT, " + OFFICE_BRANCH_ADDRESS + " TEXT, " + OFFICE_BRANCH_APPROVER + " TEXT, " + OFFICE_BRANCH_STATUS + " TEXT, " + "FOREIGN KEY(" + OFFICE_SUPERADMIN_ID + ") REFERENCES " + SUPER_ADMIN_TABLE + "(" + SUPER_ADMIN_ID + "))";
    private Account account;

    public OfficeBranch(Parcel in) {
        officeBranchID = in.readInt();
        officeBranchName = in.readString();
        officeBranchDate = in.readString();
        officeBranchAddress = in.readString();
        officeBranchApprover = in.readString();
        officeBranchStatus = in.readString();
    }

    public OfficeBranch() {
        super();
    }

    public static final Creator<OfficeBranch> CREATOR = new Creator<OfficeBranch>() {
        @Override
        public OfficeBranch createFromParcel(Parcel in) {
            return new OfficeBranch(in);
        }

        @Override
        public OfficeBranch[] newArray(int size) {
            return new OfficeBranch[size];
        }
    };

    public OfficeBranch(int officeID, String officeName, String officeStatus) {
        this.officeBranchID = officeID;
        this.officeBranchName = officeName;
        this.officeBranchStatus = officeStatus;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(officeBranchID);
        parcel.writeString(officeBranchName);
        parcel.writeString(officeBranchDate);
        parcel.writeString(officeBranchAddress);
        parcel.writeString(officeBranchApprover);
        parcel.writeString(officeBranchStatus);
        parcel.writeDouble(accountBalance);
        parcel.writeLong(accountNumber);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }


    public enum TRANSACTION_TYPE {
        BRANCH_OFFICE, AFFILIATE_CENTER;
    }

    @PrimaryKey(autoGenerate = true)
    private int officeBranchID=12;
    private String officeBranchName;
    private String officeBranchDate;
    private String officeBranchAddress;
    private String officeBranchApprover;
    private String officeBranchStatus;
    double accountBalance;
    private  Profile profile;
    private AdminUser adminUser;
    long accountNumber;

    public int getOfficeBranchID() { return officeBranchID; }
    public String getOfficeBranchName() { return officeBranchName; }
    public String getOfficeBranchDate() {
        return officeBranchDate;
    }
    public String getOfficeBranchAddress() {
        return officeBranchAddress;
    }
    public String getOfficeBranchApprover() { return officeBranchApprover; }
    public String getOfficeBranchStatus() {
        return officeBranchStatus;
    }


    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }



    public void setOfficeBranchID(int officeBranchID) {
        this.officeBranchID = officeBranchID;
    }
    public void setOfficeBranchName(String officeBranchName) {
        this.officeBranchName = officeBranchName;
    }
    public void setOfficeBranchDate(String officeBranchDate) {
        this.officeBranchDate = officeBranchDate;
    }
    public void setOfficeBranchAddress(String officeBranchAddress) {
        this.officeBranchAddress = officeBranchAddress;
    }
    public void setOfficeBranchApprover(String officeBranchApprover) {
        this.officeBranchApprover = officeBranchApprover;
    }
    public void setOfficeBranchStatus(String officeBranchAddressStatus) {
        this.officeBranchStatus = officeBranchAddressStatus;
    }
}
