package com.skylightapp.Transactions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Refund {
    public String dorefund(RefundPayload refundpayload) {
        AwajimaPaymentServices paymentservices = new AwajimaPaymentServices();


        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(refundpayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = null;
        try {
            response = paymentservices.dorefund(payload, refundpayload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
