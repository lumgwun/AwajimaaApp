package com.skylightapp.FlutterWavePayments;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONObject;

public class Fees {
    private ApiConnection apiConnection;

    Endpoints end= new Endpoints();

    private String amount;
    private String currency;
    private String card6;

    public JSONObject getFees(){

        this.apiConnection = new ApiConnection(end.getFeesEndPoint());

        ApiQuery api= new ApiQuery();
        api.putParams("amount", this.getAmount());
        api.putParams("PBFPubKey",  OurConfig.SKYLIGHT_PUBLIC_KEY);
        api.putParams("currency", this.getCurrency());
        api.putParams("ptype",2);


        return this.apiConnection.connectAndQuery(api);
    }
     /*used only when the user has entered first 6digits of their payStackCard number,
     it also helps determine international fees on the transaction if the payStackCard being used is an international payStackCard
     */

    public JSONObject getFeesForCard6(){

        this.apiConnection = new ApiConnection(end.getFeesEndPoint());

        ApiQuery api= new ApiQuery();
        api.putParams("amount", this.getAmount());
        api.putParams("PBFPubKey", OurConfig.SKYLIGHT_PUBLIC_KEY);
        api.putParams("currency", this.getCurrency());
        api.putParams("ptype",2);
        api.putParams("card6", this.getCard6());

        return this.apiConnection.connectAndQuery(api);
    }


    public String getAmount() {
        return amount;
    }


    public Fees setAmount(String amount) {
        this.amount = amount;
        return this;
    }


    public String getCurrency() {
        return currency;
    }


    public Fees setCurrency(String currency) {
        this.currency = currency;
        return this;
    }


    public String getCard6() {
        return card6;
    }


    public Fees setCard6(String card6) {
        this.card6 = card6;
        return this;
    }
}
