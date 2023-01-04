package com.skylightapp.Transactions;

public class FxRates {
    private int page;
    private double amount; //lost, won, initiated, accepted, declined .
    private String fromCurrency;//YYYY-MM-DD
    private String toCurrency;//YYYY-MM-DD
    private String authorization;
    private double resultantAmount;
    private String currencyDate;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public double getAmount() {
        return amount;
    }


    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }


    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }



    public String getToCurrency() {
        return toCurrency;
    }


    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }



    public String getAuthorization() {
        return authorization;
    }


    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public double getResultantAmount() {
        return resultantAmount;
    }

    public void setResultantAmount(double resultantAmount) {
        this.resultantAmount = resultantAmount;
    }

    public String getCurrencyDate() {
        return currencyDate;
    }

    public void setCurrencyDate(String currencyDate) {
        this.currencyDate = currencyDate;
    }
}
