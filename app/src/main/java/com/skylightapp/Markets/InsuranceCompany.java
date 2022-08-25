package com.skylightapp.Markets;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class InsuranceCompany implements Serializable, Parcelable {
    private int insComID;
    private String insComName;
    private String insComAddress;
    private String insComEmailAddress;
    private String insComPhoneNO;
    private String insComRegYear;
    private String insComStatus;

    public InsuranceCompany () {
        super();

    }

    protected InsuranceCompany(Parcel in) {
        insComID = in.readInt();
        insComName = in.readString();
        insComAddress = in.readString();
        insComEmailAddress = in.readString();
        insComPhoneNO = in.readString();
        insComRegYear = in.readString();
        insComStatus = in.readString();
    }

    public static final Creator<InsuranceCompany> CREATOR = new Creator<InsuranceCompany>() {
        @Override
        public InsuranceCompany createFromParcel(Parcel in) {
            return new InsuranceCompany(in);
        }

        @Override
        public InsuranceCompany[] newArray(int size) {
            return new InsuranceCompany[size];
        }
    };

    public int getInsComID() {
        return insComID;
    }

    public void setInsComID(int insComID) {
        this.insComID = insComID;
    }

    public String getInsComName() {
        return insComName;
    }

    public void setInsComName(String insComName) {
        this.insComName = insComName;
    }

    public String getInsComAddress() {
        return insComAddress;
    }

    public void setInsComAddress(String insComAddress) {
        this.insComAddress = insComAddress;
    }

    public String getInsComRegYear() {
        return insComRegYear;
    }

    public void setInsComRegYear(String insComRegYear) {
        this.insComRegYear = insComRegYear;
    }

    public String getInsComStatus() {
        return insComStatus;
    }

    public void setInsComStatus(String insComStatus) {
        this.insComStatus = insComStatus;
    }

    public String getInsComEmailAddress() {
        return insComEmailAddress;
    }

    public void setInsComEmailAddress(String insComEmailAddress) {
        this.insComEmailAddress = insComEmailAddress;
    }

    public String getInsComPhoneNO() {
        return insComPhoneNO;
    }

    public void setInsComPhoneNO(String insComPhoneNO) {
        this.insComPhoneNO = insComPhoneNO;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(insComID);
        parcel.writeString(insComName);
        parcel.writeString(insComAddress);
        parcel.writeString(insComEmailAddress);
        parcel.writeString(insComPhoneNO);
        parcel.writeString(insComRegYear);
        parcel.writeString(insComStatus);
    }
}
