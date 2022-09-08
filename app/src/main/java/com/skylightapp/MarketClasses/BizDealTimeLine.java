package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BizDealTimeLine implements Serializable, Parcelable {
    private int bDTimelineID;
    private int bDTimelineBDID;
    private String bDTimelineTittle;
    private String bDTimelineDetails;
    private String bDTimelineTime;
    private int bdTimelineProfID;
    private String bDTimelineStatus;

    public BizDealTimeLine () {
        super();

    }

    public BizDealTimeLine(int id, int bdTimelineProfID, String title, String details, String time, String status) {
        this.bDTimelineID=id;
        this.bDTimelineTittle=title;
        this.bdTimelineProfID=bdTimelineProfID;
        this.bDTimelineDetails=details;
        this.bDTimelineTime=time;
        this.bDTimelineStatus=status;
    }

    protected BizDealTimeLine(Parcel in) {
        bDTimelineID = in.readInt();
        bDTimelineBDID = in.readInt();
        bDTimelineTittle = in.readString();
        bDTimelineDetails = in.readString();
        bDTimelineTime = in.readString();
        bdTimelineProfID = in.readInt();
        bDTimelineStatus = in.readString();
    }

    public static final Creator<BizDealTimeLine> CREATOR = new Creator<BizDealTimeLine>() {
        @Override
        public BizDealTimeLine createFromParcel(Parcel in) {
            return new BizDealTimeLine(in);
        }

        @Override
        public BizDealTimeLine[] newArray(int size) {
            return new BizDealTimeLine[size];
        }
    };

    public int getbDTimelineBDID() {
        return bDTimelineBDID;
    }

    public void setbDTimelineBDID(int bDTimelineBDID) {
        this.bDTimelineBDID = bDTimelineBDID;
    }

    public int getbDTimelineID() {
        return bDTimelineID;
    }

    public void setbDTimelineID(int bDTimelineID) {
        this.bDTimelineID = bDTimelineID;
    }

    public String getbDTimelineTittle() {
        return bDTimelineTittle;
    }

    public void setbDTimelineTittle(String bDTimelineTittle) {
        this.bDTimelineTittle = bDTimelineTittle;
    }

    public String getbDTimelineDetails() {
        return bDTimelineDetails;
    }

    public void setbDTimelineDetails(String bDTimelineDetails) {
        this.bDTimelineDetails = bDTimelineDetails;
    }

    public String getbDTimelineTime() {
        return bDTimelineTime;
    }

    public void setbDTimelineTime(String bDTimelineTime) {
        this.bDTimelineTime = bDTimelineTime;
    }

    public int getBdTimelineProfID() {
        return bdTimelineProfID;
    }

    public void setBdTimelineProfID(int bdTimelineProfID) {
        this.bdTimelineProfID = bdTimelineProfID;
    }

    public String getbDTimelineStatus() {
        return bDTimelineStatus;
    }

    public void setbDTimelineStatus(String bDTimelineStatus) {
        this.bDTimelineStatus = bDTimelineStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bDTimelineID);
        parcel.writeInt(bDTimelineBDID);
        parcel.writeString(bDTimelineTittle);
        parcel.writeString(bDTimelineDetails);
        parcel.writeString(bDTimelineTime);
        parcel.writeInt(bdTimelineProfID);
        parcel.writeString(bDTimelineStatus);
    }
}
