package com.skylightapp.FlutterWavePayments;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONException;
import org.json.JSONObject;

import static com.skylightapp.FlutterWavePayments.Encryption.encryptData;

public class AccountResolvePayload {
    private String PBFPubKey;
    private String country;
    private String currency;
    private String bankCode;
    private String accountNumber;
    private String name;
    private String SECKEY;
    Encryption e=new Encryption();
    private String transaction_reference;//to be called
    private String otp;//to be called

    private String amount,email,phonenumber,firstname,lastname,IP,
            txRef,passcode,device_fingerprint,payment_type;

    public String getSECKEY() {
        return SECKEY;
    }


    public AccountResolvePayload setSECKEY(String SECKEY) {
        this.SECKEY = SECKEY;
        return  this;
    }
    public String getPayment_type() {
        return payment_type;
    }


    public AccountResolvePayload setPayment_type(String payment_type) {
        this.payment_type = payment_type;
        return  this;
    }


    public String getPBFPubKey() {
        return PBFPubKey;
    }


    public AccountResolvePayload setPBFPubKey(String PBFPubKey) {
        this.PBFPubKey = PBFPubKey;
        return this;
    }


    public String getBankCode() {
        return bankCode;
    }


    public AccountResolvePayload setBankCode(String bankCode) {
        this.bankCode = bankCode;
        return  this;
    }





    public String getCurrency() {
        return currency;
    }


    public AccountResolvePayload setCurrency(String currency) {
        this.currency = currency;
        return  this;
    }


    public String getName() {
        return name;
    }


    public AccountResolvePayload setName(String name) {
        this.name = name;
        return this;
    }


    public String getCountry() {
        return country;
    }


    public AccountResolvePayload setCountry(String country) {
        this.country = country;
        return this;
    }
    public String getAmount() {
        return amount;
    }

    public AccountResolvePayload setAmount(String amount) {
        this.amount = amount;
        return this;
    }
    public String getEmail() {
        return email;
    }


    public AccountResolvePayload setEmail(String email) {
        this.email = email;
        return this;
    }
    public String getAccountNumber() {
        return accountNumber;
    }


    public AccountResolvePayload setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }


    public String getPhonenumber() {
        return phonenumber;
    }


    public AccountResolvePayload setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }


    public String getFirstname() {
        return firstname;
    }


    public AccountResolvePayload setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }


    public String getLastname() {
        return lastname;
    }


    public AccountResolvePayload setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }


    public String getIP() {
        return IP;
    }


    public AccountResolvePayload setIP(String IP) {
        this.IP = IP;
        return this;
    }


    public String getTxRef() {
        return txRef;
    }


    public AccountResolvePayload setTxRef(String txRef) {
        this.txRef = txRef;
        return this;
    }


    public String getPasscode() {
        return passcode;
    }


    public AccountResolvePayload setPasscode(String passcode) {
        this.passcode = passcode;
        return this;
    }


    public String getDevice_fingerprint() {
        return device_fingerprint;
    }


    public AccountResolvePayload setDevice_fingerprint(String device_fingerprint) {
        this.device_fingerprint = device_fingerprint;
        return this;
    }


    public String getTransaction_reference() {
        return transaction_reference;
    }


    public AccountResolvePayload setTransaction_reference(String transaction_reference) {
        this.transaction_reference = transaction_reference;
        return this;
    }


    public String getOtp() {
        return otp;
    }


    public AccountResolvePayload setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public JSONObject setJSON() {
        JSONObject json=new JSONObject();
        try{
            json.put("PBFPubKey", OurConfig.SKYLIGHT_PUBLIC_KEY);
            json.put("accountnumber",this.getAccountNumber());
            json.put("accountbank",this.getBankCode());
            json.put("currency", this.getCurrency());
            json.put("country", this.getCountry());
            json.put("amount", this.getAmount());
            json.put("firstname", this.getFirstname());
            json.put("lastname", this.getLastname());
            json.put("passcode", this.getPasscode());
            json.put("email", this.getEmail());
            json.put("IP", this.getIP());
            json.put("payment_type", "account");
            json.put("txRef", this.getTxRef());
            json.put("device_fingerprint", this.getDevice_fingerprint());

        }catch( JSONException ex){ex.getMessage();}
        return json;
    }


    public JSONObject chargeAccount() {

        JSONObject json=setJSON();


        String message= json.toString();

        String encrypt_secret_key=Encryption.getKey(OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);
        String client= encryptData(message,encrypt_secret_key);

        Charge ch=new Charge();

        return ch.charge(client);

    }
    public JSONObject chargeAccount(boolean polling) {

        JSONObject json=setJSON();

        Polling p=new Polling();

        return p.handleTimeoutCharge(json);

    }


    public JSONObject validateAccountCharge(){
        Charge vcharge= new Charge();
        return vcharge.validateAccountCharge(this.getTransaction_reference(), this.getOtp());
    }


    public JSONObject validateAccountCharge(boolean polling){
        Polling p=new Polling();
        return p.validateAccountChargeTimeout(this.getTransaction_reference(),this.getOtp());
    }



}
