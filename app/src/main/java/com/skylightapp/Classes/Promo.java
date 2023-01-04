package com.skylightapp.Classes;

import com.skylightapp.MarketClasses.MarketBizPackage;

import java.util.Date;

public class Promo {
    private int image_id;
    private String recordDate;
    private int numberOfDays;
    private int remainingDays =0;
    private long customerId;
    private double amount;
    private double total;
    private String CustomerName;
    private String status;
    protected String type;
    private double grandTotal;
    boolean completed = true;
    private int count;
    long promoId;
    double amountRemaining;
    private boolean remind;



    public int setCount(int count)
    {
        this.count = count;
        return count;
    }

    public int getRemainingDays()
    {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays)
    {
        this.remainingDays = remainingDays;
    }
    public double getAmountRemaining()
    {
        return amountRemaining;
    }

    public void setAmountRemaining(int amountRemaining)
    {
        this.amountRemaining = amountRemaining;
    }


    public int getImage_id()
    {
        return image_id;
    }

    public void setImage_id(int image_id)
    {
        this.image_id = image_id;
    }

    public String getRecordDate()
    {
        return recordDate;
    }
    public MarketBizPackage.SkylightPackage_Type getPackageType()
    {
        return MarketBizPackage.SkylightPackage_Type.valueOf(type);
    }
    public void setPackageType(int type)
    {
        this.type = String.valueOf(type);
    }

    public void setRecordDate(Date recordDate)
    {
        this.recordDate = String.valueOf(recordDate);
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }

    public boolean getCompleted()
    {
        return completed;
    }
    public void setCustomerName(String customerName)
    {
        this.CustomerName = customerName;
    }

    public String getCustomerName()
    {
        return CustomerName;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public long getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }


    public int getNumberOfDays()
    {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays)
    {
        this.numberOfDays = numberOfDays;
    }

    public double getGrandTotal()
    {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal)
    {
        this.grandTotal = grandTotal;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }



}
