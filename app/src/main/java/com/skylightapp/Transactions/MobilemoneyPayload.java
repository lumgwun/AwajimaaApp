package com.skylightapp.Transactions;

import java.util.List;

public class MobilemoneyPayload {
    private String PBFPubKey;
    private String currency;
    private String payment_type;
    private String country;
    private String amount;
    private String email;
    private String phonenumber;
    private String network;
    private String firstname;
    private String lastname;
    private String voucher;
    private String IP;
    private String txRef;
    private String orderRef;
    private int is_mobile_money_gh;
    private String redirect_url;
    private String device_fingerprint;
    private int is_mobile_money_ug;
    private String is_mpesa;
    private int is_mpesa_lipa;

    private String metaname;
    private String metavalue;
    private String test;
    private String secret_key;
    private String public_key;
    private String encryption_key;
    Mpesameta meta;
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


    public String getSecret_key() {
        return secret_key;
    }


    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }


    public String getPublic_key() {
        return public_key;
    }


    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }


    public String getEncryption_key() {
        return encryption_key;
    }


    public void setEncryption_key(String encryption_key) {
        this.encryption_key = encryption_key;
    }


    public String getMetavalue() {
        return metavalue;
    }


    public void setMetavalue(String metavalue) {
        this.metavalue = metavalue;
    }


    public String getMetaname() {
        return metaname;
    }


    public void setMetaname(String metaname) {
        this.metaname = metaname;
    }




    public String getPBFPubKey() {
        return PBFPubKey;
    }


    public void setPBFPubKey(String PBFPubKey) {
        this.PBFPubKey = PBFPubKey;
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


    public String getPhonenumber() {
        return phonenumber;
    }


    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    public String getNetwork() {
        return network;
    }


    public void setNetwork(String network) {
        this.network = network;
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


    public String getVoucher() {
        return voucher;
    }


    public void setVoucher(String voucher) {
        this.voucher = voucher;
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


    public String getOrderRef() {
        return orderRef;
    }

    public void setOrderRef(String orderRef) {
        this.orderRef = orderRef;
    }


    public int getIs_mobile_money_gh() {
        return is_mobile_money_gh;
    }


    public void setIs_mobile_money_gh(int is_mobile_money_gh) {
        this.is_mobile_money_gh = is_mobile_money_gh;
    }


    public String getRedirect_url() {
        return redirect_url;
    }


    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getDevice_fingerprint() {
        return device_fingerprint;
    }


    public void setDevice_fingerprint(String device_fingerprint) {
        this.device_fingerprint = device_fingerprint;
    }


    public int getIs_mobile_money_ug() {
        return is_mobile_money_ug;
    }

    public void setIs_mobile_money_ug(int is_mobile_money_ug) {
        this.is_mobile_money_ug = is_mobile_money_ug;
    }


    public String getIs_mpesa() {
        return is_mpesa;
    }


    public void setIs_mpesa(String is_mpesa) {
        this.is_mpesa = is_mpesa;
    }


    public int getIs_mpesa_lipa() {
        return is_mpesa_lipa;
    }


    public void setIs_mpesa_lipa(int is_mpesa_lipa) {
        this.is_mpesa_lipa = is_mpesa_lipa;
    }


    public Mpesameta getMeta() {
        return meta;
    }


    public void setMeta(Mpesameta meta) {
        this.meta = meta;
    }


}
