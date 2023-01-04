package com.skylightapp.FlutterWavePayments;

import com.skylightapp.Transactions.OurConfig;

import org.json.JSONObject;

public class ExchangeRates {
    ApiConnection apiConnection;
    Endpoints end= new Endpoints();

    private String amount;
    private String origin_currency;
    private String destination_currency;


    /**
     * @return JSONObject
     */
    public JSONObject forex(){


        this.apiConnection = new ApiConnection(end.getForexEndPoint());
        ApiQuery api= new ApiQuery();

        //API PARAMETERS
        api.putParams("SECKEY", OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);
        api.putParams("origin_currency", this.getOrigin_currency());
        api.putParams("destination_currency", this.getDestination_currency());
        api.putParams("amount", this.getAmount());


        return this.apiConnection.connectAndQuery(api);

    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     * @return ExchangeRates
     */
    public ExchangeRates setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    /**
     * @return the origin_currency
     */
    public String getOrigin_currency() {
        return origin_currency;
    }

    /**
     * @param origin_currency the origin_currency to set
     * @return ExchangeRates
     */
    public ExchangeRates setOrigin_currency(String origin_currency) {
        this.origin_currency = origin_currency;
        return this;
    }

    /**
     * @return the destination_currency
     */
    public String getDestination_currency() {
        return destination_currency;
    }

    /**
     * @param destination_currency the destination_currency to set
     * @return ExchangeRates
     */
    public ExchangeRates setDestination_currency(String destination_currency) {
        this.destination_currency = destination_currency;
        return this;
    }
}
