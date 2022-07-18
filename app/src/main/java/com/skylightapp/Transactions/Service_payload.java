package com.skylightapp.Transactions;

public class Service_payload {
    private String Country;
    private String CustomerId;
    private String Reference;
    private int Amount;
    private String RecurringType;
    private boolean IsAirtime;
    private String BillerName;
    public int getAmount() {
        return Amount;
    }


    public void setAmount(int Amount) {
        this.Amount = Amount;
    }


    public String getRecurringType() {
        return RecurringType;
    }


    public void setRecurringType(String RecurringType) {
        this.RecurringType = RecurringType;
    }


    public boolean isIsAirtime() {
        return IsAirtime;
    }


    public void setIsAirtime(boolean IsAirtime) {
        this.IsAirtime = IsAirtime;
    }


    public String getCountry() {
        return Country;
    }


    public void setCountry(String Country) {
        this.Country = Country;
    }


    public String getCustomerId() {
        return CustomerId;
    }


    public void setCustomerId(String CustomerId) {
        this.CustomerId = CustomerId;
    }


    public String getReference() {
        return Reference;
    }


    public void setReference(String Reference) {
        this.Reference = Reference;
    }


    public String getBillerName() {
        return BillerName;
    }


    public void setBillerName(String BillerName) {
        this.BillerName = BillerName;
    }


}
