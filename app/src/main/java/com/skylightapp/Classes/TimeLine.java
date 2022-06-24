package com.skylightapp.Classes;

import android.location.Location;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;


@Entity(tableName = TimeLine.TIMELINE_TABLE)
public class TimeLine extends Profile implements Serializable ,Parcelable{
    public static final String TIMELINE_TABLE = "timelineTable";
    public static final String TIMELINE_ID = "_timeLine_id";
    public static final String TIMELINE_TITTLE = "tittle";
    public static final String TIMELINE_DETAILS = "details";
    public static final String TIMELINE_LOCATION = "location";
    public static final String TIMELINE_TIME = "time";
    public static final String TIMELINE_CUS_ID = "timeLine_CusID";
    public static final String TIMELINE_PROF_ID = "timeLine_ProfID";
    public static final String TIMELINE_AMOUNT = "amount";
    public static final String TIMELINE_SENDING_ACCOUNT = "sending_account";
    public static final String TIMELINE_GETTING_ACCOUNT = "Getting_account";
    public static final String TIMELINE_WORKER_NAME = "worker_name";
    public static final String TIMELINE_CLIENT_NAME = "client_name";

    public static final String CREATE_TIMELINE_TABLE = "CREATE TABLE " + TIMELINE_TABLE + " (" + TIMELINE_PROF_ID + " INTEGER NOT NULL, " + TIMELINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            TIMELINE_CUS_ID + " INTEGER , " + TIMELINE_TITTLE + " TEXT , " + TIMELINE_DETAILS + " TEXT , " + TIMELINE_LOCATION + " TEXT , " +
            TIMELINE_WORKER_NAME + " TEXT, " + TIMELINE_CLIENT_NAME + " TEXT , " + TIMELINE_SENDING_ACCOUNT + " TEXT , " +
            TIMELINE_GETTING_ACCOUNT + " TEXT, " + TIMELINE_AMOUNT + " FLOAT , " +"FOREIGN KEY(" + TIMELINE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," + "FOREIGN KEY(" + TIMELINE_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

    public TimeLine(int timelineID, String timelineTittle, String timelineDetails, String timestamp) {
        super();
        this.timelineTittle = timelineTittle;
        this.timelineDetails = timelineDetails;
        this.timelineID = timelineID;
        this.timestamp = timestamp;

    }

    public TimeLine(String tittle, String timelineDetails) {
        this.timelineTittle = tittle;
        this.timelineDetails = timelineDetails;

    }

    public int getTimeLineCusID() {
        return timeLineCusID;
    }

    public void setTimeLineCusID(int timeLineCusID) {
        this.timeLineCusID = timeLineCusID;
    }



    public enum TIMELINE_TYPE {
        ADMIN, CUSTOMER, TELLER,
        OTHERS,
        SAVINGS,TRANSACTION;
    }
    @PrimaryKey(autoGenerate = true)
    private int timelineID=140;
    private Profile timelineProfile;
    public String timestamp;
    public String timelineTittle;
    public String timelineDetails;
    private String workerName;
    private String clientName;
    public double amount;
    private String destinationAccount;
    private String payingAccount;
    private Customer timelineCustomer;
    private int timeLineCusID;
    public Location location;

    public TimeLine(int id, String timelineTittle, String timelineDetails) {
        super();
        this.timelineTittle = timelineTittle;
        this.timelineDetails = timelineDetails;
    }
    public TimeLine(String timelineTittle, String timelineDetails, Location location) {
        super();
        this.timelineTittle = timelineTittle;
        this.timelineDetails = timelineDetails;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    public String getTimelineDetails() {
        return timelineDetails;
    }

    public void setTimelineDetails(String timelineDetails) {
        this.timelineDetails = timelineDetails;
    }
    public int getTimelineID() {
        return timelineID;
    }

    public void setTimelineID(int timelineID) {
        this.timelineID = timelineID;
    }

    public Profile getTimelineProfile() {
        return timelineProfile;
    }

    public void setTimelineProfile(Profile timelineProfile) {
        this.timelineProfile = timelineProfile;
    }

    public String getTimelineTittle() {
        return timelineTittle;
    }

    public void setTimelineTittle(String timelineTittle) {
        this.timelineTittle = timelineTittle;
    }
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }
    public String getPayingAccount() {
        return payingAccount;
    }

    public void setPayingAccount(String payingAccount) {
        this.payingAccount = payingAccount;
    }
    public Customer getProfileCus() {
        return timelineCustomer;
    }

    public void setProfileCus(Customer timelineCustomer) {
        this.timelineCustomer = timelineCustomer;
    }

}
