package com.skylightapp.Transactions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ValidateCardCharge {
    public String doflwcardvalidate(ValidateCardPayload validatecardpayload){

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(validatecardpayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VerificationServices verificationservices = new VerificationServices();
        String response = null;
        try {
            response = verificationservices.docardvalidate(payload, validatecardpayload);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
