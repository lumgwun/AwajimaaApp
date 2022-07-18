package com.skylightapp.FlutterWavePayments;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONObject;

public class Charge {
    ApiConnection apiConnection;
    Endpoints ed= new Endpoints();

    public JSONObject charge(String client){
        this.apiConnection = new ApiConnection(ed.getChargeEndPoint());

        String alg="3DES-24";


        ApiQuery api=new ApiQuery();

        api.putParams("PBFPubKey", OurConfig.SKYLIGHT_PUBLIC_KEY);

        api.putParams("client", client);

        api.putParams("alg", alg);


        return this.apiConnection.connectAndQuery(api);
    }


    public JSONObject validateAccountCharge(String transaction_reference, String otp){

        this.apiConnection = new ApiConnection(ed.getValidateAccountChargeEndPoint());

        ApiQuery api=new ApiQuery();

        api.putParams("PBFPubKey",OurConfig.SKYLIGHT_PUBLIC_KEY);
        api.putParams("transactionreference", transaction_reference);

        api.putParams("otp", otp);

        return this.apiConnection.connectAndQuery(api);
    }


    public JSONObject validateCardCharge(String transaction_reference, String otp){

        this.apiConnection = new ApiConnection(ed.getValidateCardChargeEndPoint());

        ApiQuery api=new ApiQuery();

        api.putParams("PBFPubKey",OurConfig.SKYLIGHT_PUBLIC_KEY);
        api.putParams("transaction_reference", transaction_reference);

        api.putParams("otp", otp);

        return this.apiConnection.connectAndQuery(api);
    }

}
