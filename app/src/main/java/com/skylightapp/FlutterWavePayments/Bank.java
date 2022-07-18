package com.skylightapp.FlutterWavePayments;

import com.mashape.unirest.http.JsonNode;

public class Bank {
    private ApiConnection apiConnection;
    Endpoints ed= new Endpoints();

    public Bank(String bank, String s) {

    }

    public JsonNode getAllBanks(){
        this.apiConnection = new ApiConnection(ed.getBankEndPoint());

        return this.apiConnection.connectAndQueryWithGet();
    }
}
