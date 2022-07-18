package com.skylightapp.Transactions;

public class Ebillpayload {

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
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return the SECKEY
     */
    public String getSECKEY() {
        return SECKEY;
    }

    /**
     * @param SECKEY the SECKEY to set
     */
    public void setSECKEY(String SECKEY) {
        this.SECKEY = SECKEY;
    }

    /**
     * @return the accountnumber
     */
    public String getAccountnumber() {
        return accountnumber;
    }

    /**
     * @param accountnumber the accountnumber to set
     */
    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    /**
     * @return the narration
     */
    public String getNarration() {
        return narration;
    }

    /**
     * @param narration the narration to set
     */
    public void setNarration(String narration) {
        this.narration = narration;
    }

    /**
     * @return the numberofunits
     */
    public String getNumberofunits() {
        return numberofunits;
    }

    /**
     * @param numberofunits the numberofunits to set
     */
    public void setNumberofunits(String numberofunits) {
        this.numberofunits = numberofunits;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
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

    /**
     * @return the txRef
     */
    public String getTxRef() {
        return txRef;
    }

    /**
     * @param txRef the txRef to set
     */
    public void setTxRef(String txRef) {
        this.txRef = txRef;
    }

    /**
     * @return the IP
     */
    public String getIP() {
        return IP;
    }

    /**
     * @param IP the IP to set
     */
    public void setIP(String IP) {
        this.IP = IP;
    }

//    	{
//      "SECKEY": "FLWSECK-xxxxxxxxxxxxxxxxxxxxx-X",
//      "accountnumber": "2918282737",
//      "narration": "test",
//      "numberofunits": 1,
//      "currency": "NGN",
//      "amount": 100,
//      "phonenumber": "09384747474",
//      "email": "jake@rad.com",
//      "txRef": "773838837373",
//      "IP": "127.9.0.7"
//		}

//    	{
//      "SECKEY": "FLWSECK-xxxxxxxxxxxxxxxxxxxxx-X",
//      "reference": "RVEBLS-6DB2D191EC44-134",
//      "currency": "NGN",
//      "amount": 3000
//		}


    private String SECKEY;
    private String accountnumber;
    private String narration;
    private String numberofunits;
    private String currency;
    private String country;
    private String amount;
    private String phonenumber;
    private String email;
    private String txRef;
    private String IP;
    private String reference;
    private String test;

}
