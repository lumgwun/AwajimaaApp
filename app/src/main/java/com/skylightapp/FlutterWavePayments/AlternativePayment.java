package com.skylightapp.FlutterWavePayments;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONException;
import org.json.JSONObject;

import static com.skylightapp.FlutterWavePayments.Encryption.encryptData;

public class AlternativePayment {

    ApiConnection apiConnection;

    Encryption e=new Encryption();


    private String accountnumber,accountbank,currency,country,
            amount,firstname,lastname,
            pin,email,IP,txRef,phonenumber,orderRef,network,
            flwRef;


    // charge customers using nigerian USSD for GTB and Zenith Bank,Ghana mobile money and Kenya Mpesa

    public JSONObject chargeNigerianUssd () {
        //getting charge endpoint
        JSONObject json=new JSONObject();
        try{
            json.put("accountnumber", this.getAccountnumber());
            json.put("accountbank", this.getAccountbank());
            json.put("currency", this.getCurrency());
            json.put("country", this.getCountry());
            json.put("amount", this.getAmount());
            json.put("firstname", this.getFirstname());
            json.put("lastname", this.getLastname());
            json.put("pin", this.getPin());
            json.put("email", this.getEmail());
            json.put("IP", this.getIP());
            json.put("txRef", this.getTxRef());
            json.put("payment_type", "ussd");

        }catch(JSONException ex){ex.getMessage();}
        String message= json.toString();

        String encrypt_secret_key=Encryption.getKey(OurConfig.SKYLIGHT_SECRET_KEY);
        String client= encryptData(message,encrypt_secret_key);

        Charge ch=new Charge();

        return ch.charge(client);

    }


    public JSONObject chargeGhanaMobileMoney () {
        JSONObject json=new JSONObject();
        try{
            json.put("orderRef",this.getOrderRef());
            json.put("network", this.getNetwork());
            json.put("currency", this.getCurrency());
            json.put("country", this.getCountry());
            json.put("amount", this.getAmount());
            json.put("firstname", this.getFirstname());
            json.put("lastname", this.getLastname());
            json.put("pin", this.getPin());
            json.put("email", this.getEmail());
            json.put("IP", this.getIP());
            json.put("txRef", this.getTxRef());
            json.put("payment_type", "mobilemoneygh");
            json.put("is_mobile_money_gh", "1");
            json.put("phonenumber",this.getPhonenumber());
        }catch(JSONException ex){ex.getMessage();}
        String message= json.toString();

        String encrypt_secret_key=Encryption.getKey(OurConfig.SKYLIGHT_SECRET_KEY);
        String client= encryptData(message,encrypt_secret_key);

        Charge ch=new Charge();

        return ch.charge(client);

    }


    public JSONObject chargeKenyaMpesa () {
        //getting charge endpoint
        JSONObject json=new JSONObject();
        try{
            json.put("currency", this.getCurrency());
            json.put("country", this.getCountry());
            json.put("amount", this.getAmount());
            json.put("firstname", this.getFirstname());
            json.put("lastname", this.getLastname());
            json.put("pin", this.getPin());
            json.put("email", this.getEmail());
            json.put("IP", this.getIP());
            json.put("txRef", this.getTxRef());
            json.put("orderRef", this.getOrderRef());
            json.put("phonenumber", this.getPhonenumber());
            json.put("payment_type", "mpesa");
            json.put("is_mpesa", "1");
        }catch(JSONException ex){ex.getMessage();}
        String message= json.toString();

        String encrypt_secret_key=Encryption.getKey(OurConfig.SKYLIGHT_SECRET_KEY);
        String client= encryptData(message,encrypt_secret_key);

        Charge ch=new Charge();

        return ch.charge(client);

    }

    public String getAccountnumber() {
        return accountnumber;
    }


    public AlternativePayment setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
        return this;
    }


    public String getAccountbank() {
        return accountbank;
    }


    public AlternativePayment setAccountbank(String accountbank) {
        this.accountbank = accountbank;
        return this;
    }


    public String getCurrency() {
        return currency;
    }


    public AlternativePayment setCurrency(String currency) {
        this.currency = currency;
        return this;
    }


    public String getCountry() {
        return country;
    }


    public AlternativePayment setCountry(String country) {
        this.country = country;
        return this;
    }


    public String getAmount() {
        return amount;
    }


    public AlternativePayment setAmount(String amount) {
        this.amount = amount;
        return this;
    }


    public String getFirstname() {
        return firstname;
    }


    public AlternativePayment setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     *  @return AlternativePayment
     */
    public AlternativePayment setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    /**
     * @return the pin
     */
    public String getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     *  @return AlternativePayment
     */
    public AlternativePayment setPin(String pin) {
        this.pin = pin;
        return this;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     *  @return AlternativePayment
     */
    public AlternativePayment setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * @return the IP
     */
    public String getIP() {
        return IP;
    }

    /**
     * @param IP the IP to set
     *  @return AlternativePayment
     */
    public AlternativePayment setIP(String IP) {
        this.IP = IP;
        return this;
    }

    /**
     * @return the txRef
     */
    public String getTxRef() {
        return txRef;
    }

    /**
     * @param txRef the txRef to set
     *  @return AlternativePayment
     */
    public AlternativePayment setTxRef(String txRef) {
        this.txRef = txRef;
        return this;
    }

    /**
     * @return the phonenumber
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * @param phonenumber the phonenumber to set
     *  @return AlternativePayment
     */
    public AlternativePayment setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }

    /**
     * @return the orderRef
     */
    public String getOrderRef() {
        return orderRef;
    }

    /**
     * @param orderRef the orderRef to set
     *  @return AlternativePayment
     */
    public AlternativePayment setOrderRef(String orderRef) {
        this.orderRef = orderRef;
        return this;
    }

    /**
     * @return the network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * @param network the network to set
     *  @return AlternativePayment
     */
    public AlternativePayment setNetwork(String network) {
        this.network = network;
        return this;
    }

    /**
     * @return the flwRef
     */
    public String getFlwRef() {
        return flwRef;
    }


    public AlternativePayment setFlwRef(String flwRef) {
        this.flwRef = flwRef;
        return this;
    }
}
