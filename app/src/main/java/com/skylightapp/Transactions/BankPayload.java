package com.skylightapp.Transactions;

import java.util.List;

public class BankPayload {
    private String PBFPubKey;
    private String accountbank;
    private String accountnumber;
    private String currency;
    private String payment_type;
    private String country;
    private String amount;
    private String email;
    private String passcode;
    private String bvn;
    private String phonenumber;
    private String firstname;
    private String lastname;
    private String IP;
    private String txRef;
    private String device_fingerprint;
    private String redirect_url;
    private String SECKEY;
    private String test;
    private List<SplitaddPayload> subaccounts;
    public List<SplitaddPayload> getSubaccounts() {
        return subaccounts;
    }

    public void setSubaccounts(List<SplitaddPayload> subaccounts) {
        this.subaccounts = subaccounts;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getSECKEY() {
        return SECKEY;
    }


    public void setSECKEY(String SECKEY) {
        this.SECKEY = SECKEY;
    }

    public String getPBFPubKey() {
        return PBFPubKey;
    }


    public void setPBFPubKey(String PBFPubKey) {
        this.PBFPubKey = PBFPubKey;
    }


    public String getAccountbank() {
        return accountbank;
    }


    public void setAccountbank(String accountbank) {
        this.accountbank = accountbank;
    }


    public String getAccountnumber() {
        return accountnumber;
    }


    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }


    public String getCurrency() {
        return currency;
    }


    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getPayment_type() {
        return payment_type;
    }


    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getAmount() {
        return amount;
    }


    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPasscode() {
        return passcode;
    }


    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }


    public String getBvn() {
        return bvn;
    }


    public void setBvn(String bvn) {
        this.bvn = bvn;
    }


    public String getPhonenumber() {
        return phonenumber;
    }


    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getFirstname() {
        return firstname;
    }


    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public String getLastname() {
        return lastname;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getIP() {
        return IP;
    }


    public void setIP(String IP) {
        this.IP = IP;
    }


    public String getTxRef() {
        return txRef;
    }


    public void setTxRef(String txRef) {
        this.txRef = txRef;
    }


    public String getDevice_fingerprint() {
        return device_fingerprint;
    }


    public void setDevice_fingerprint(String device_fingerprint) {
        this.device_fingerprint = device_fingerprint;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }


}
