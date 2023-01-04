package com.skylightapp.FlutterWavePayments;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONObject;

public class FlutterWaveTransaction {
    ApiConnection apiConnection;

    Endpoints end= new Endpoints();
    private String  flwref, txRef;


    public JSONObject verifyTransactionRequery(){

        this.apiConnection = new ApiConnection(end.getVerifyEndPoint());
        ApiQuery api= new ApiQuery();
        api.putParams("txref", this.getTxRef());
        api.putParams("flw_ref", this.getFlwref());
        api.putParams("SECKEY", OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);

        return this.apiConnection.connectAndQuery(api);
    }


    public JSONObject verifyTransactionXrequery(){

        this.apiConnection = new ApiConnection(end.getVerifyXrequeryEndPoint());

        ApiQuery api= new ApiQuery();

        api.putParams("txref", this.getTxRef());
        api.putParams("txref", this.getTxRef());
        api.putParams("SECKEY", OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);
        api.putParams("last_attempt", 1);
        api.putParams("only_successful", 1);

        return this.apiConnection.connectAndQuery(api);
    }


    public String getFlwref() {
        return flwref;
    }


    public FlutterWaveTransaction setFlwref(String flwref) {
        this.flwref = flwref;
        return this;
    }


    public String getTxRef() {
        return txRef;
    }

    public FlutterWaveTransaction setTxRef(String txRef) {
        this.txRef = txRef;
        return this;
    }

}
