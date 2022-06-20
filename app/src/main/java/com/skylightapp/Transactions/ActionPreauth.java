package com.skylightapp.Transactions;

import org.json.JSONException;
import org.json.JSONObject;

public class ActionPreauth {
    public String docapture(CapturePayload capturepayload){

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(capturepayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        VerificationServices verificationservices = new VerificationServices();
        String response = verificationservices.doaction(payload);

        return response;
    }
}
