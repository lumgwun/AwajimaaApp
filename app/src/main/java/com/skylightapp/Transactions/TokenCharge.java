package com.skylightapp.Transactions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TokenCharge {
    public String dotokenizedcharge(TokenChargePayload tokenchargepayload) throws UnknownHostException {


        InetAddress localhost = InetAddress.getLocalHost();
        tokenchargepayload.setIP((localhost).toString());

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(tokenchargepayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SkylightPaymentServices paymentservices = new SkylightPaymentServices();

        String response = paymentservices.dotokencharge(payload, tokenchargepayload);
        return response;
    }


    public String doupdatetoken(TokenChargePayload tokenchargepayload){

        SkylightPaymentServices paymentservices = new SkylightPaymentServices();
        String response = null;
        try {
            response = paymentservices.dotokenupdate(tokenchargepayload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
