package com.skylightapp.Transactions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Ebills {
    public String doebillscreate(Ebillpayload ebillpayload) {
        EbillServices ebillServices = new EbillServices();

        ebillpayload.setCountry("NG");

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(ebillpayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = null;
        try {
            response = ebillServices.doebillscreate(payload, ebillpayload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public String doebillsupdate(Ebillpayload ebillpayload) {
        EbillServices ebillServices = new EbillServices();

        ebillpayload.setCountry("NG");

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(ebillpayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = null;
        try {
            response = ebillServices.doebillsupdate(payload, ebillpayload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
