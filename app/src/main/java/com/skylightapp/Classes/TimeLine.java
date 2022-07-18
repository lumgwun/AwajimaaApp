package com.skylightapp.Classes;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static com.skylightapp.Classes.Customer.CUSTOMER_ID;
import static com.skylightapp.Classes.Customer.CUSTOMER_TABLE;
import static com.skylightapp.Classes.Profile.PROFILES_TABLE;
import static com.skylightapp.Classes.Profile.PROFILE_ID;


@Entity(tableName = TimeLine.TIMELINE_TABLE)
public class TimeLine  implements Serializable ,Parcelable{
    public static final String TIMELINE_TABLE = "timelineTable";
    public static final String TIMELINE_ID = "_timeLine_id";
    public static final String TIMELINE_TITTLE = "timeline_tittle";
    public static final String TIMELINE_DETAILS = "timeline_details";
    public static final String TIMELINE_LOCATION = "timeline_location";
    public static final String TIMELINE_TIME = "timeline_time";
    public static final String TIMELINE_CUS_ID = "timeLine_CusID";
    public static final String TIMELINE_PROF_ID = "timeLine_ProfID";
    public static final String TIMELINE_AMOUNT = "timeline_Amount";
    public static final String TIMELINE_SENDING_ACCOUNT = "timeline_sending_account";
    public static final String TIMELINE_GETTING_ACCOUNT = "timeline_Getting_account";
    public static final String TIMELINE_WORKER_NAME = "timeline_worker_name";
    public static final String TIMELINE_CLIENT_NAME = "timeline_client_name";

    public static final String CREATE_TIMELINE_TABLE = "CREATE TABLE " + TIMELINE_TABLE + " (" + TIMELINE_PROF_ID + " INTEGER NOT NULL, " + TIMELINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            TIMELINE_CUS_ID + " INTEGER , " + TIMELINE_TITTLE + " TEXT , " + TIMELINE_DETAILS + " TEXT , " + TIMELINE_LOCATION + " TEXT , " +
            TIMELINE_WORKER_NAME + " TEXT, " + TIMELINE_CLIENT_NAME + " TEXT , " + TIMELINE_SENDING_ACCOUNT + " TEXT , " +
            TIMELINE_GETTING_ACCOUNT + " TEXT, " + TIMELINE_AMOUNT + " REAL , "+ TIMELINE_TIME + " TEXT , " +"FOREIGN KEY(" + TIMELINE_CUS_ID + ") REFERENCES " + CUSTOMER_TABLE + "(" + CUSTOMER_ID + ")," + "FOREIGN KEY(" + TIMELINE_PROF_ID + ") REFERENCES " + PROFILES_TABLE + "(" + PROFILE_ID + "))";

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

    protected TimeLine(Parcel in) {
        timelineID = in.readInt();
        timelineProfile = in.readParcelable(Profile.class.getClassLoader());
        timestamp = in.readString();
        timelineTittle = in.readString();
        timelineDetails = in.readString();
        timeline_WorkerName = in.readString();
        timeline_ClientName = in.readString();
        timeline_Amount = in.readDouble();
        timeline_destAcct = in.readString();
        timeline_PayingAcct = in.readString();
        timelineCustomer = in.readParcelable(Customer.class.getClassLoader());
        timeLineCusID = in.readInt();
        timeline_Location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<TimeLine> CREATOR = new Creator<TimeLine>() {
        @Override
        public TimeLine createFromParcel(Parcel in) {
            return new TimeLine(in);
        }

        @Override
        public TimeLine[] newArray(int size) {
            return new TimeLine[size];
        }
    };

    public int getTimeLineCusID() {
        return timeLineCusID;
    }

    public void setTimeLineCusID(int timeLineCusID) {
        this.timeLineCusID = timeLineCusID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(timelineID);
        parcel.writeParcelable(timelineProfile, i);
        parcel.writeString(timestamp);
        parcel.writeString(timelineTittle);
        parcel.writeString(timelineDetails);
        parcel.writeString(timeline_WorkerName);
        parcel.writeString(timeline_ClientName);
        parcel.writeDouble(timeline_Amount);
        parcel.writeString(timeline_destAcct);
        parcel.writeString(timeline_PayingAcct);
        parcel.writeParcelable(timelineCustomer, i);
        parcel.writeInt(timeLineCusID);
        parcel.writeParcelable(timeline_Location, i);
    }

    public int getTimeLineProfID() {
        return timeLineProfID;
    }

    public void setTimeLineProfID(int timeLineProfID) {
        this.timeLineProfID = timeLineProfID;
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
    private String timeline_WorkerName;
    private String timeline_ClientName;
    public double timeline_Amount;
    private String timeline_destAcct;
    private String timeline_PayingAcct;
    private Customer timelineCustomer;
    private int timeLineCusID;
    private int timeLineProfID;
    public Location timeline_Location;

    public TimeLine(int id, String timelineTittle, String timelineDetails) {
        super();
        this.timelineTittle = timelineTittle;
        this.timelineDetails = timelineDetails;
    }
    public TimeLine(String timelineTittle, String timelineDetails, Location timeline_Location) {
        super();
        this.timelineTittle = timelineTittle;
        this.timelineDetails = timelineDetails;
        this.timeline_Location = timeline_Location;
    }

    public Location getTimeline_Location() {
        return timeline_Location;
    }

    public void setTimeline_Location(Location timeline_Location) {
        this.timeline_Location = timeline_Location;
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

    public String getTimeline_WorkerName() {
        return timeline_WorkerName;
    }

    public void setTimeline_WorkerName(String timeline_WorkerName) {
        this.timeline_WorkerName = timeline_WorkerName;
    }
    public String getTimeline_ClientName() {
        return timeline_ClientName;
    }

    public void setTimeline_ClientName(String timeline_ClientName) {
        this.timeline_ClientName = timeline_ClientName;
    }

    public double getTimeline_Amount() {
        return timeline_Amount;
    }

    public void setTimeline_Amount(double timeline_Amount) {
        this.timeline_Amount = timeline_Amount;
    }

    public String getTimeline_destAcct() {
        return timeline_destAcct;
    }

    public void setTimeline_destAcct(String timeline_destAcct) {
        this.timeline_destAcct = timeline_destAcct;
    }
    public String getTimeline_PayingAcct() {
        return timeline_PayingAcct;
    }

    public void setTimeline_PayingAcct(String timeline_PayingAcct) {
        this.timeline_PayingAcct = timeline_PayingAcct;
    }
    public Customer getTimelineCustomer() {
        return timelineCustomer;
    }

    public void setTimelineCustomer(Customer timelineCustomer) {
        this.timelineCustomer = timelineCustomer;
    }

}
