package com.skylightapp.FlutterWavePayments;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.skylightapp.FlutterWavePayments.Encryption.encryptData;

public class PreAuthorization {
    ApiConnection apiConnection;
    Endpoints end=new Endpoints();
    Encryption e=new Encryption();
    private String flwref;
    private String action;
    OurConfig key=new OurConfig();
    private String cardno,cvv,expirymonth,expiryyear,currency,country;
    private String  amount,email,phonenumber,firstname,lastname,IP;
    private String txRef,redirect_url,device_fingerprint,charge_type;
  /*
  NB: For Preauth payment please use the keys below:
| Public Key | FLWPUBK-8cd258c49f38e05292e5472b2b15906e-X |
| Secret Key | FLWSECK-c51891678d48c39eff3701ff686bdb69-X |
    Preauth payments is based on approval per merchant,
  and is not open to accounts except approval to use these payment method is given.
  */
    public JSONObject setJSON(){
        JSONObject json= new JSONObject();
        try{
            InetAddress inetAddress = InetAddress.getLocalHost();
            IP= inetAddress.getHostAddress();
            String public_key="FLWPUBK-8cd258c49f38e05292e5472b2b15906e-X ";
            json.put("PBFPubKey",public_key);
            json.put("cardno", this.getCardno());
            json.put("charge_type",this.getCharge_type());
            json.put("cvv", this.getCvv());
            json.put("expirymonth", this.getExpirymonth());
            json.put("expiryyear",this.getExpiryyear());
            json.put( "currency", this.getCurrency());
            json.put("country", this.getCountry());
            json.put("amount",this.getAmount());
            json.put("email", this.getEmail());
            json.put("phonenumber", this.getPhonenumber());
            json.put("firstname", this.getFirstname());
            json.put("lastname", this.getLastname());
            json.put("IP", IP);
            json.put("txRef", this.getTxRef());
            json.put("redirect_url", this.getRedirect_url());
            json.put("device_fingerprint", this.getDevice_fingerprint());
        }catch(UnknownHostException | JSONException ex){ex.getMessage();}
        return json;

    }

    public JSONObject preAuthorizeCard()throws JSONException{

        this.apiConnection = new ApiConnection(end.getChargeEndPoint());
        JSONObject json= setJSON();
        String public_key="FLWPUBK-8cd258c49f38e05292e5472b2b15906e-X ";
        //preauthorization requires special public key


        //else us the normal public key
        //  String public_key=key.getPublicKey();


        //preauthorization requires special public key

        String message= json.toString();
        String secret_key="FLWSECK-c51891678d48c39eff3701ff686bdb69-X";

        String encrypt_secret_key=Encryption.getKey(secret_key);
        String client= encryptData(message,encrypt_secret_key);
        String alg="3DES-24";

        ApiQuery api=new ApiQuery();

        api.putParams("PBFPubKey", public_key);
        api.putParams("client", client);
        api.putParams("alg", alg);
        System.out.println("Succesful");


        return this.apiConnection.connectAndQuery(api);
    }
    public JSONObject capture(){
        this.apiConnection = new ApiConnection(end.getCaptureEndPoint());
        ApiQuery api= new ApiQuery();

        String secret_key="FLWSECK-c51891678d48c39eff3701ff686bdb69-X";

        // String secret_key=key.getSecretKey();
        api.putParams("flwRef", this.getFlwref());
        api.putParams("SECKEY", secret_key);


        return this.apiConnection.connectAndQuery(api);
    }

    public JSONObject refundOrVoid(){
        this.apiConnection = new ApiConnection(end.getRefundOrVoidEndPoint());
        ApiQuery api= new ApiQuery();
        String secret_key="FLWSECK-c51891678d48c39eff3701ff686bdb69-X";
        // String secret_key=key.getSecretKey();
        api.putParams("ref", this.getFlwref());
        api.putParams("action", this.getAction());
        api.putParams("SECKEY", secret_key);

        return this.apiConnection.connectAndQuery(api);

    }


    public String getFlwref() {
        return flwref;
    }


    public PreAuthorization setFlwref(String flwref) {
        this.flwref = flwref;
        return this;
    }


    public String getAction() {
        return action;
    }


    public PreAuthorization setAction(String action) {
        this.action = action;
        return this;
    }


    public String getCardno() {
        return cardno;
    }


    public PreAuthorization setCardno(String cardno) {
        this.cardno = cardno;
        return this;
    }


    public String getCvv() {
        return cvv;
    }


    public PreAuthorization setCvv(String cvv) {
        this.cvv = cvv;
        return this;
    }


    public String getExpirymonth() {
        return expirymonth;
    }


    public PreAuthorization setExpirymonth(String expirymonth) {
        this.expirymonth = expirymonth;
        return this;
    }


    public String getExpiryyear() {
        return expiryyear;
    }


    public PreAuthorization setExpiryyear(String expiryyear) {
        this.expiryyear = expiryyear;
        return this;
    }


    public String getCurrency() {
        return currency;
    }


    public PreAuthorization setCurrency(String currency) {
        this.currency = currency;
        return this;
    }


    public String getCountry() {
        return country;
    }


    public PreAuthorization setCountry(String country) {
        this.country = country;
        return this;
    }


    public String getAmount() {
        return amount;
    }


    public PreAuthorization setAmount(String amount) {
        this.amount = amount;
        return this;
    }


    public String getEmail() {
        return email;
    }


    public PreAuthorization setEmail(String email) {
        this.email = email;
        return this;
    }


    public String getPhonenumber() {
        return phonenumber;
    }


    public PreAuthorization setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }


    public String getFirstname() {
        return firstname;
    }


    public PreAuthorization setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }


    public String getLastname() {
        return lastname;
    }


    public PreAuthorization setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }


    public String getIP() {
        return IP;
    }


    public PreAuthorization setIP(String IP) {
        this.IP = IP;
        return this;
    }


    public String getTxRef() {
        return txRef;
    }


    public PreAuthorization setTxRef(String txRef) {
        this.txRef = txRef;
        return this;
    }


    public String getRedirect_url() {
        return redirect_url;
    }


    public PreAuthorization setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
        return this;
    }


    public String getDevice_fingerprint() {
        return device_fingerprint;
    }


    public PreAuthorization setDevice_fingerprint(String device_fingerprint) {
        this.device_fingerprint = device_fingerprint;
        return this;
    }


    public String getCharge_type() {
        return charge_type;
    }


    public PreAuthorization setCharge_type(String charge_type) {
        this.charge_type = charge_type;
        return this;
    }
}
