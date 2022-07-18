package com.skylightapp.FlutterWavePayments;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONObject;

import static com.skylightapp.FlutterWavePayments.Encryption.encryptData;

public class Polling {
    ApiConnection apiConnection;
    final private Endpoints ed=new Endpoints();
    Encryption e=new Encryption();
    OurConfig key=new OurConfig();



    public JSONObject handleTimeoutCharge(JSONObject json){
        this.apiConnection = new ApiConnection(ed.getChargeTimeoutEndpoint());
        JSONObject tcharge= null;

        String message= json.toString();

        String encrypt_secret_key=Encryption.getKey( OurConfig.SKYLIGHT_SECRET_KEY);
        String client= encryptData(message,encrypt_secret_key);

        String alg="3DES-24";

        ApiQuery api=new ApiQuery();

        api.putParams("PBFPubKey", OurConfig.SKYLIGHT_PUBLIC_KEY);

        api.putParams("client", client);

        api.putParams("alg", alg);

        tcharge=  this.apiConnection.connectAndQuery(api);

        return tcharge;

    }

    public JSONObject validateCardChargeTimeout(String transaction_reference,String otp){

        this.apiConnection = new ApiConnection(ed.getValidateCardChargeTimeoutEndpoint());
        ApiQuery api=new ApiQuery();

        api.putParams("PBFPubKey",OurConfig.SKYLIGHT_PUBLIC_KEY);
        api.putParams("transaction_reference", transaction_reference);

        api.putParams("otp", otp);

        return this.apiConnection.connectAndQuery(api);
    }

    public JSONObject validateAccountChargeTimeout(String transaction_reference,String otp){

        this.apiConnection = new ApiConnection(ed.getValidateAccountChargeTimeoutEndpoint());
        ApiQuery api=new ApiQuery();

        api.putParams("PBFPubKey",OurConfig.SKYLIGHT_PUBLIC_KEY);
        api.putParams("transaction_reference", transaction_reference);

        api.putParams("otp", otp);

        return this.apiConnection.connectAndQuery(api);
    }

}
