package com.skylightapp.MarketClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.quickblox.chat.model.QBChatDialog;

import java.io.Serializable;
import java.util.ArrayList;

public class BDealChatExtra extends QBChatDialog implements Serializable, Parcelable {
    private int bdChatExtraID;
    private String bdChatCEChatID;
    private int bdChatCEHostID;
    private int bdChatCEPartnerID;
    private String bdChatEType;
    private long bdChatEFromBID;
    private int bdChatCEDealID;
    private long bdChatEToBID;
    private String bdChatEDateCreated;
    private String bdChatTittle;
    private double bdChatEAmount;
    private String bdChatECurrency;
    private String bdChatEStatus;
    private BusinessDeal bdChatEBusinessDeal;
    public BDealChatExtra () {
        super();

    }
    private ArrayList<BizDealMileStone> bdChatEMileStone;

    protected BDealChatExtra(Parcel in) {
        bdChatExtraID = in.readInt();
        bdChatCEChatID = in.readString();
        bdChatCEHostID = in.readInt();
        bdChatCEPartnerID = in.readInt();
        bdChatEType = in.readString();
        bdChatEFromBID = in.readLong();
        bdChatCEDealID = in.readInt();
        bdChatEToBID = in.readLong();
        bdChatEDateCreated = in.readString();
        bdChatTittle = in.readString();
        bdChatEAmount = in.readDouble();
        bdChatECurrency = in.readString();
        bdChatEStatus = in.readString();
        bdChatEBusinessDeal = in.readParcelable(BusinessDeal.class.getClassLoader());
        bdChatEMileStone = in.createTypedArrayList(BizDealMileStone.CREATOR);
    }

    public static final Creator<BDealChatExtra> CREATOR = new Creator<BDealChatExtra>() {
        @Override
        public BDealChatExtra createFromParcel(Parcel in) {
            return new BDealChatExtra(in);
        }

        @Override
        public BDealChatExtra[] newArray(int size) {
            return new BDealChatExtra[size];
        }
    };

    public BDealChatExtra(int chatID, int dealID, String title, int hostChatID, int partnerChatID, String chatQBID, String chatType, long toBizID, long fromBizID, String dateCreated, double chatAmt, String status, String currency) {
        this.bdChatExtraID = chatID;
        this.bdChatCEDealID = dealID;
        this.bdChatTittle = title;
        this.bdChatCEHostID = hostChatID;
        this.bdChatCEPartnerID = partnerChatID;
        this.bdChatCEChatID = chatQBID;
        this.bdChatEType = chatType;
        this.bdChatEToBID = toBizID;
        this.bdChatEFromBID = fromBizID;
        this.bdChatEDateCreated = dateCreated;
        this.bdChatEAmount = chatAmt;
        this.bdChatEStatus = status;
        this.bdChatECurrency = currency;
    }

    public String getBdChatEStatus() {
        return bdChatEStatus;
    }

    public void setBdChatEStatus(String bdChatEStatus) {
        this.bdChatEStatus = bdChatEStatus;
    }

    public String getBdChatECurrency() {
        return bdChatECurrency;
    }

    public void setBdChatECurrency(String bdChatECurrency) {
        this.bdChatECurrency = bdChatECurrency;
    }

    public double getBdChatEAmount() {
        return bdChatEAmount;
    }

    public void setBdChatEAmount(double bdChatEAmount) {
        this.bdChatEAmount = bdChatEAmount;
    }

    public String getBdChatTittle() {
        return bdChatTittle;
    }

    public void setBdChatTittle(String bdChatTittle) {
        this.bdChatTittle = bdChatTittle;
    }

    public String getBdChatCEChatID() {
        return bdChatCEChatID;
    }

    public void setBdChatCEChatID(String bdChatCEChatID) {
        this.bdChatCEChatID = bdChatCEChatID;
    }

    public int getBdChatExtraID() {
        return bdChatExtraID;
    }

    public void setBdChatExtraID(int bdChatExtraID) {
        this.bdChatExtraID = bdChatExtraID;
    }

    public String getBdChatEDateCreated() {
        return bdChatEDateCreated;
    }

    public void setBdChatEDateCreated(String bdChatEDateCreated) {
        this.bdChatEDateCreated = bdChatEDateCreated;
    }

    public int getBdChatCEPartnerID() {
        return bdChatCEPartnerID;
    }

    public void setBdChatCEPartnerID(int bdChatCEPartnerID) {
        this.bdChatCEPartnerID = bdChatCEPartnerID;
    }

    public int getBdChatCEHostID() {
        return bdChatCEHostID;
    }

    public void setBdChatCEHostID(int bdChatCEHostID) {
        this.bdChatCEHostID = bdChatCEHostID;
    }

    public String getBdChatEType() {
        return bdChatEType;
    }

    public void setBdChatEType(String bdChatEType) {
        this.bdChatEType = bdChatEType;
    }

    public long getBdChatEFromBID() {
        return bdChatEFromBID;
    }

    public void setBdChatEFromBID(long bdChatEFromBID) {
        this.bdChatEFromBID = bdChatEFromBID;
    }

    public long getBdChatEToBID() {
        return bdChatEToBID;
    }

    public void setBdChatEToBID(long bdChatEToBID) {
        this.bdChatEToBID = bdChatEToBID;
    }

    public int getBdChatCEDealID() {
        return bdChatCEDealID;
    }

    public void setBdChatCEDealID(int bdChatCEDealID) {
        this.bdChatCEDealID = bdChatCEDealID;
    }

    public BusinessDeal getBdChatEBusinessDeal() {
        return bdChatEBusinessDeal;
    }

    public void setBdChatEBusinessDeal(BusinessDeal bdChatEBusinessDeal) {
        this.bdChatEBusinessDeal = bdChatEBusinessDeal;
    }

    public ArrayList<BizDealMileStone> getBdChatEMileStone() {
        return bdChatEMileStone;
    }

    public void setBdChatEMileStone(ArrayList<BizDealMileStone> bdChatEMileStone) {
        this.bdChatEMileStone = bdChatEMileStone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bdChatExtraID);
        parcel.writeString(bdChatCEChatID);
        parcel.writeInt(bdChatCEHostID);
        parcel.writeInt(bdChatCEPartnerID);
        parcel.writeString(bdChatEType);
        parcel.writeLong(bdChatEFromBID);
        parcel.writeInt(bdChatCEDealID);
        parcel.writeLong(bdChatEToBID);
        parcel.writeString(bdChatEDateCreated);
        parcel.writeString(bdChatTittle);
        parcel.writeDouble(bdChatEAmount);
        parcel.writeString(bdChatECurrency);
        parcel.writeString(bdChatEStatus);
        parcel.writeParcelable(bdChatEBusinessDeal, i);
        parcel.writeTypedList(bdChatEMileStone);
    }
}
