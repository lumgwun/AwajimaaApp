package com.skylightapp.Transactions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class TransValidation {
    public String bvnvalidate(TransverifyPayload transverifyPayload) {
        VerificationServices verificationServices = new VerificationServices();

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(transverifyPayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = null;
        try {
            response = verificationServices.dotransverify(payload, transverifyPayload);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return new JSONObject(response).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return payload;
    }
}
