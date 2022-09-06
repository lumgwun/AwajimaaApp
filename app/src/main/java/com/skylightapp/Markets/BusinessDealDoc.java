package com.skylightapp.Markets;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class BusinessDealDoc implements Serializable, Parcelable {
    private  int bizDocID;
    private  int bizDocProfID;
    private  int bizDocMarketID;
    private  int bizDocDealID;
    private String title;
    private  String desc;
    private Uri bizDocLink;
    private String bizDocStatus;
    private  int bizDocViewCount;

    public BusinessDealDoc () {
        super();

    }

    public BusinessDealDoc(int id, String title, String desc, Uri documentLink, String status) {
        this.bizDocID=id;
        this.title=title;
        this.desc=desc;
        this.bizDocLink=documentLink;
        this.bizDocStatus=status;
    }

    protected BusinessDealDoc(Parcel in) {
        bizDocID = in.readInt();
        bizDocProfID = in.readInt();
        bizDocMarketID = in.readInt();
        bizDocDealID = in.readInt();
        title = in.readString();
        desc = in.readString();
        bizDocLink = in.readParcelable(Uri.class.getClassLoader());
        bizDocStatus = in.readString();
        bizDocViewCount = in.readInt();
    }

    public static final Creator<BusinessDealDoc> CREATOR = new Creator<BusinessDealDoc>() {
        @Override
        public BusinessDealDoc createFromParcel(Parcel in) {
            return new BusinessDealDoc(in);
        }

        @Override
        public BusinessDealDoc[] newArray(int size) {
            return new BusinessDealDoc[size];
        }
    };

    public int getBizDocID() {
        return bizDocID;
    }

    public void setBizDocID(int bizDocID) {
        this.bizDocID = bizDocID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Uri getBizDocLink() {
        return bizDocLink;
    }

    public void setBizDocLink(Uri bizDocLink) {
        this.bizDocLink = bizDocLink;
    }

    public String getBizDocStatus() {
        return bizDocStatus;
    }

    public void setBizDocStatus(String bizDocStatus) {
        this.bizDocStatus = bizDocStatus;
    }

    public int getBizDocViewCount() {
        return bizDocViewCount;
    }

    public void setBizDocViewCount(int bizDocViewCount) {
        this.bizDocViewCount = bizDocViewCount;
    }

    public int getBizDocDealID() {
        return bizDocDealID;
    }

    public void setBizDocDealID(int bizDocDealID) {
        this.bizDocDealID = bizDocDealID;
    }

    public int getBizDocProfID() {
        return bizDocProfID;
    }

    public void setBizDocProfID(int bizDocProfID) {
        this.bizDocProfID = bizDocProfID;
    }

    public int getBizDocMarketID() {
        return bizDocMarketID;
    }

    public void setBizDocMarketID(int bizDocMarketID) {
        this.bizDocMarketID = bizDocMarketID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bizDocID);
        parcel.writeInt(bizDocProfID);
        parcel.writeInt(bizDocMarketID);
        parcel.writeInt(bizDocDealID);
        parcel.writeString(title);
        parcel.writeString(desc);
        parcel.writeParcelable(bizDocLink, i);
        parcel.writeString(bizDocStatus);
        parcel.writeInt(bizDocViewCount);
    }
}
