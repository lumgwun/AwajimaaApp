package com.skylightapp.Transactions;

public class QrcodePayload {
    private String PBFPubKey;
    private String amount;
    private String txRef;
    private String is_qr;
    private String payment_type;
    private String ip;
    private String device_fingerprint;
    private String email;
    private String country;
    private String phonenumber;
    private String firstname;
    private String lastname;
    private String submerchant_business_name;
    private String public_key;
    private String test;
    private Mpesameta meta;
    public String getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public void setTest(String test) {
        this.test = test;
    }

    /**
     * @return the public_key
     */
    public String getPublic_key() {
        return public_key;
    }

    /**
     * @param public_key the public_key to set
     */
    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the phonenumber
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * @param phonenumber the phonenumber to set
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the submerchant_business_name
     */
    public String getSubmerchant_business_name() {
        return submerchant_business_name;
    }

    /**
     * @param submerchant_business_name the submerchant_business_name to set
     */
    public void setSubmerchant_business_name(String submerchant_business_name) {
        this.submerchant_business_name = submerchant_business_name;
    }

    /**
     * @return the PBFPubKey
     */
    public String getPBFPubKey() {
        return PBFPubKey;
    }

    /**
     * @param PBFPubKey the PBFPubKey to set
     */
    public void setPBFPubKey(String PBFPubKey) {
        this.PBFPubKey = PBFPubKey;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the txRef
     */
    public String getTxRef() {
        return txRef;
    }


    public void setTxRef(String txRef) {
        this.txRef = txRef;
    }


    public String getIs_qr() {
        return is_qr;
    }


    public void setIs_qr(String is_qr) {
        this.is_qr = is_qr;
    }

    /**
     * @return the payment_type
     */
    public String getPayment_type() {
        return payment_type;
    }

    /**
     * @param payment_type the payment_type to set
     */
    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the device_fingerprint
     */
    public String getDevice_fingerprint() {
        return device_fingerprint;
    }

    /**
     * @param device_fingerprint the device_fingerprint to set
     */
    public void setDevice_fingerprint(String device_fingerprint) {
        this.device_fingerprint = device_fingerprint;
    }

    /**
     * @return the meta
     */
    public Mpesameta getMeta() {
        return meta;
    }

    /**
     * @param meta the meta to set
     */
    public void setMeta(Mpesameta meta) {
        this.meta = meta;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
//    {
//  "PBFPubKey": "FLWPUBK-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx-X",
//  "amount": 40,
//  "txRef": "m3s4m0c1526722407366",
//  "is_qr": "qr",
//  "payment_type": "pwc_qr",
//  "ip": "::1",
//  "device_fingerprint": "ada1d43c29279d9f743956edfb98d801",
//  "meta": [
//    {
//      "metaname": "flightid",
//      "metavalue": "93849-MK5000"
//    }
//  ],
//  "email": "user@ravepay.co"
//}


}
