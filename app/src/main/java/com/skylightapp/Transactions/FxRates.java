package com.skylightapp.Transactions;

public class FxRates {
    private int page;
    private int amount; //lost, won, initiated, accepted, declined .
    private String fromCurrency;//YYYY-MM-DD
    private String toCurrency;//YYYY-MM-DD
    private String authorization;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getAmount() {
        return amount;
    }


    public void setAmount(int amount) {
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
}
