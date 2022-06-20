package com.skylightapp.Transactions;

import org.json.JSONException;
import org.json.JSONObject;

public class BvnValidation {
    public String bvnvalidate(Bvnload bvnload) {
        VerificationServices verificationServices = new VerificationServices();

        String response = null;
        try {
            response = verificationServices.doBvnVerification(bvnload);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (response != null) {
                return new JSONObject(response).toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }
}
