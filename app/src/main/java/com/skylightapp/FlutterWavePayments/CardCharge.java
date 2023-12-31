package com.skylightapp.FlutterWavePayments;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONException;
import org.json.JSONObject;

import static com.skylightapp.FlutterWavePayments.Encryption.encryptData;
import static com.skylightapp.FlutterWavePayments.Encryption.getKey;

public class CardCharge {
    JSONObject api=new JSONObject();
    Endpoints ed=new Endpoints();
    ApiConnection apiConnection;

    Encryption e=new Encryption();
    private String cardno,cvv,expirymonth,expiryyear,currency,country,pin,suggested_auth,
            amount,email,phonenumber,firstname,lastname,txRef,redirect_url,device_fingerprint,IP,
            charge_type;

    private String transactionreference,otp, authUrl;


    public JSONObject setJSON() {
        JSONObject json=new JSONObject();
        try{

            json.put("cardno", this.getCardno());
            json.put("cvv", this.getCvv());
            json.put("currency", this.getCurrency());
            json.put("country", this.getCountry());
            json.put("amount", this.getAmount());
            json.put("expiryyear", this.getExpiryyear());
            json.put("expirymonth", this.getExpirymonth());
            json.put("email", this.getEmail());
            json.put("IP", this.getIP());
            json.put("txRef", this.getTxRef());
            json.put("device_fingerprint", this.getDevice_fingerprint());
            // json.put("pin", this.getPin());
            //json.put("suggested_auth", this.getSuggested_auth());
            json.put("firstname", this.getFirstname());
            json.put("lastname", this.getLastname());
            json.put("redirect_url", this.getRedirect_url());
            json.put("charge_type", this.getCharge_type());
        }catch( JSONException ex){ex.getMessage();}
        return json;
    }


    public JSONObject chargeMasterAndVerveCard() throws JSONException{
        JSONObject json= setJSON();

        json.put("PBFPubKey", OurConfig.SKYLIGHT_PUBLIC_KEY);
        json.put("pin",this.getPin() );
        json.put("suggested_auth",this.getSuggested_auth() );


        String message= json.toString();

        String encrypt_secret_key=getKey(OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);
        String client= null;
        if (encrypt_secret_key != null) {
            client = encryptData(message,encrypt_secret_key);
        }

        Charge ch=new Charge();

        return ch.charge(client);

    }
    public JSONObject chargeMasterAndVerveCard(boolean polling) throws JSONException {
        JSONObject json= setJSON();

        json.put("PBFPubKey",OurConfig.SKYLIGHT_PUBLIC_KEY);
        json.put("pin",this.getPin() );
        json.put("suggested_auth",this.getSuggested_auth() );
        Polling p=new Polling();

        return p.handleTimeoutCharge(json);

    }
    public JSONObject chargeVisaAndIntl() throws JSONException{
        JSONObject json= setJSON();
        json.put("PBFPubKey",OurConfig.SKYLIGHT_PUBLIC_KEY);
        json.put("redirect_url", this.getRedirect_url() );

        String message= json.toString();

        String encrypt_secret_key=getKey(OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);
        String client= null;
        if (encrypt_secret_key != null) {
            client = encryptData(message,encrypt_secret_key);
        }

        Charge ch=new Charge();

        return ch.charge(client);

    }

    public JSONObject chargeVisaAndIntl(boolean polling) throws JSONException{
        JSONObject json= setJSON();
        json.put("PBFPubKey",OurConfig.SKYLIGHT_PUBLIC_KEY);
        json.put("redirect_url", this.getRedirect_url() );
        Polling p=new Polling();

        return p.handleTimeoutCharge(json);

    }


    /*
    if AuthMode::"PIN"
    @params transaction reference(flwRef),OTP
      * @return JSONObject
    */

    public JSONObject validateCardCharge(){
        Charge vch= new Charge();

        return vch.validateCardCharge(this.getTransactionreference(), this.getOtp());
    }
    //if timeout
    public JSONObject validateCardCharge(boolean polling){

        Polling p=new Polling();

        return p.validateCardChargeTimeout(this.getTransactionreference(), this.getOtp());
    }


    /*public void validateCardChargeVB(){

        if (Desktop.isDesktopSupported()) {
            try{
                Desktop.getDesktop().browse(new URI(this.getAuthUrl()));
            }catch(URISyntaxException | IOException ex){}
        }
    }*/


    /**
     * @return the cardno
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * @param cardno the cardno to set
     *  @return CardCharge
     */
    public CardCharge setCardno(String cardno) {
        this.cardno = cardno;

        return this;
    }

    /**
     * @return the cvv
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * @param cvv the cvv to set
     *  @return CardCharge
     */
    public CardCharge setCvv(String cvv) {
        this.cvv = cvv;

        return this;
    }

    /**
     * @return the expirymonth
     */
    public String getExpirymonth() {
        return expirymonth;
    }

    /**
     * @param expirymonth the expirymonth to set
     *  @return CardCharge
     */
    public CardCharge setExpirymonth(String expirymonth) {
        this.expirymonth = expirymonth;

        return this;
    }

    /**
     * @return the expiryyear
     */
    public String getExpiryyear() {
        return expiryyear;
    }

    /**
     * @param expiryyear the expiryyear to set
     *  @return CardCharge
     */
    public CardCharge setExpiryyear(String expiryyear) {
        this.expiryyear = expiryyear;

        return this;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }


    public CardCharge setCurrency(String currency) {
        this.currency = currency;

        return this;
    }


    public String getCountry() {
        return country;
    }


    public CardCharge setCountry(String country) {
        this.country = country;

        return this;
    }


    public String getPin() {
        return pin;
    }

    /**
     * @param pin the pin to set
     *  @return CardCharge
     */
    public CardCharge setPin(String pin) {
        this.pin = pin;

        return this;
    }

    /**
     * @return the suggested_auth
     */
    public String getSuggested_auth() {
        return suggested_auth;
    }

    /**
     * @param suggested_auth the suggested_auth to set
     *  @return CardCharge
     */
    public CardCharge setSuggested_auth(String suggested_auth) {
        this.suggested_auth = suggested_auth;

        return this;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     *  @return CardCharge
     */
    public CardCharge setAmount(String amount) {
        this.amount = amount;

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
     *  @return CardCharge
     */
    public CardCharge setEmail(String email) {
        this.email = email;

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
     *  @return CardCharge
     */
    public CardCharge setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;

        return this;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     *  @return CardCharge
     */
    public CardCharge setFirstname(String firstname) {
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
     *  @return CardCharge
     */
    public CardCharge setLastname(String lastname) {
        this.lastname = lastname;

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
     *  @return CardCharge
     */
    public CardCharge setIP(String IP) {
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
     *  @return CardCharge
     */
    public CardCharge setTxRef(String txRef) {
        this.txRef = txRef;

        return this;
    }


    public String getRedirect_url() {
        return redirect_url;
    }


    public CardCharge setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;

        return this;
    }


    public String getDevice_fingerprint() {
        return device_fingerprint;
    }


    public CardCharge setDevice_fingerprint(String device_fingerprint) {
        this.device_fingerprint = device_fingerprint;

        return this;
    }


    public String getCharge_type() {
        return charge_type;
    }


    public CardCharge setCharge_type(String charge_type) {
        this.charge_type = charge_type;

        return this;
    }


    public String getTransactionreference() {
        return transactionreference;
    }


    public CardCharge setTransactionreference(String transaction_reference) {
        this.transactionreference= transaction_reference;

        return this;
    }


    public String getOtp() {
        return otp;
    }


    public CardCharge setOtp(String otp) {
        this.otp = otp;

        return this;
    }


    public String getAuthUrl() {
        return authUrl;
    }


    public CardCharge setAuthUrl(String authUrl) {
        this.authUrl = authUrl;

        return this;
    }
}
