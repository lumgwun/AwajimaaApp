package com.skylightapp.Transactions;


import com.skylightapp.Classes.Customer;

public class OTP {
    private int length;
    private int status;
    private String sender;
    private boolean send=true;
    private Customer customer;
    private String medium;// sms, email and whatsapp
    private int expiry;
    private int otp;
    private String reference;
    private String authorization;

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSender() {
        return sender;
    }


    public void setSender(String sender) {
        this.sender = sender;
    }


    public int getExpiry() {
        return expiry;
    }


    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }


    public boolean getSend() {
        return send;
    }


    public void setSend(boolean send) {
        this.send = send;
    }


    public String getMedium() {
        return medium;
    }


    public void setMedium(String medium) {
        this.medium = medium;
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
