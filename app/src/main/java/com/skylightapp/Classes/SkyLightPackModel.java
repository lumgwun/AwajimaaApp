package com.skylightapp.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class SkyLightPackModel extends SkyLightPackage implements Parcelable, Serializable {
    private String itemName;
    private String description;
    private double price;
    private String duration;
    private int itemImage;

    public SkyLightPackModel() {
        super();

    }

    public SkyLightPackModel(int itemImage, String itemName, double price, String description, String duration) {
        super();
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.itemImage = itemImage;
    }

    protected SkyLightPackModel(Parcel in) {
        super();
        itemName = in.readString();
        description = in.readString();
        price = in.readDouble();
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

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    public String getDuration(String s) {
        return duration;
    }
    public void setDuration(String duration)
    {
        this.duration = duration;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public String getDescription()
    {
        return description;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
    public double getPrice()
    {
        return price;
    }

    public void setItemImage(int itemImage)
    {
        this.itemImage = itemImage;
    }
    public int getItemImage()
    {
        return itemImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeString(description);
        parcel.writeDouble(price);
        parcel.writeString(duration);
    }

    public String getDuration() {
        return duration;
    }
}
