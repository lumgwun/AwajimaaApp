package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SkyLightPackModel extends SkyLightPackage implements Parcelable, Serializable {
    private String pMItemName;
    private int pModeID;
    private String pMdesc;
    private String pMType;
    private String pMStartDate;
    private String pMEndDate;
    private double pMPrice;
    private double pGrandTotal;
    private int pMDuration;
    private int pMItemImage;
    private  SkyLightPackage skyLightPackage;

    public SkyLightPackModel() {
        super();

    }

    public SkyLightPackModel(int id, int pMItemImage, String pMItemName, double pMPrice, String pMdesc, int pMDuration) {
        super();
        this.pModeID = id;
        this.pMItemName = pMItemName;
        this.pMdesc = pMdesc;
        this.pMPrice = pMPrice;
        this.pMDuration = pMDuration;
        this.pMItemImage = pMItemImage;
    }
    public SkyLightPackModel(int pMItemImage, String pMItemName,String pMType, double pMPrice, String pMdesc, int pMDuration) {
        super();
        this.pMItemName = pMItemName;
        this.pMdesc = pMdesc;
        this.pMType = pMType;
        this.pMPrice = pMPrice;
        this.pMDuration = pMDuration;
        this.pMItemImage = pMItemImage;
    }

    protected SkyLightPackModel(Parcel in) {
        super();
        pMItemName = in.readString();
        pMdesc = in.readString();
        pMPrice = in.readDouble();
    }

    public static final Creator<SkyLightPackModel> CREATOR = new Creator<SkyLightPackModel>() {
        @Override
        public SkyLightPackModel createFromParcel(Parcel in) {
            return new SkyLightPackModel(in);
        }

        @Override
        public SkyLightPackModel[] newArray(int size) {
            return new SkyLightPackModel[size];
        }
    };

    public String getpMItemName() {
        return pMItemName;
    }
    public void setpMItemName(String pMItemName)
    {
        this.pMItemName = pMItemName;
    }
    public void setpMDuration(int pMDuration)
    {
        this.pMDuration = pMDuration;
    }
    public void setpMdesc(String pMdesc)
    {
        this.pMdesc = pMdesc;
    }
    public String getpMdesc()
    {
        return pMdesc;
    }

    public void setpMPrice(double pMPrice)
    {
        this.pMPrice = pMPrice;
    }
    public double getpMPrice()
    {
        return pMPrice;
    }

    public void setpMItemImage(int pMItemImage)
    {
        this.pMItemImage = pMItemImage;
    }
    public int getpMItemImage()
    {
        return pMItemImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pMItemName);
        parcel.writeString(pMdesc);
        parcel.writeDouble(pMPrice);
        parcel.writeInt(pMDuration);
    }

    public int getpMDuration() {
        return pMDuration;
    }


    public void setSkyLightPackage(SkyLightPackage skyLightPackage) {
        this.skyLightPackage = skyLightPackage;
    }

    public int getpModeID() {
        return pModeID;
    }

    public void setpModeID(int pModeID) {
        this.pModeID = pModeID;
    }

    public String getpMType() {
        return pMType;
    }

    public void setpMType(String pMType) {
        this.pMType = pMType;
    }

    public String getpMStartDate() {
        return pMStartDate;
    }

    public void setpMStartDate(String pMStartDate) {
        this.pMStartDate = pMStartDate;
    }

    public String getpMEndDate() {
        return pMEndDate;
    }

    public void setpMEndDate(String pMEndDate) {
        this.pMEndDate = pMEndDate;
    }

    public double getpGrandTotal() {
        return pGrandTotal;
    }

    public void setpGrandTotal(double pGrandTotal) {
        this.pGrandTotal = pGrandTotal;
    }

    public SkyLightPackage getSkyLightPackage() {
        return skyLightPackage;
    }
}
