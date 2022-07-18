package com.skylightapp.Transactions;

public class Chargebacks {
    private int page;
    private String status; //lost, won, initiated, accepted, declined .
    private String from;//YYYY-MM-DD
    private String to;//YYYY-MM-DD
    private String currency;//NGN
    private String id;
    private String reference;
    private String authorization;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getFrom() {
        return from;
    }


    public void setFrom(String from) {
        this.from = from;
    }


    public String getId() {
        return id;
    }


    public void setId(String accountbank) {
        this.id = id;
    }


    public String getTo() {
        return to;
    }


    public void setTo(String to) {
        this.to = to;
    }


    public String getCurrency() {
        return currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getReference() {
        return reference;
    }


    public void setReference(String reference) {
        this.reference = reference;
    }


    public String getAuthorization() {
        return authorization;
    }


    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }



}
