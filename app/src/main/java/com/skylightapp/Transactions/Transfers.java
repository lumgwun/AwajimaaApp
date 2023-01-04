package com.skylightapp.Transactions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class Transfers {
    public String doTransfer(TransferPayload transferPayload) {
        TransferServices transferServices = new TransferServices();

        if (transferPayload.getReference() == null) {
            Date date = new Date();
            transferPayload.setReference("Awajima" + date);
        }


        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(transferPayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = null;
        try {
            response = transferServices.dotransfer(payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String doBulkTransfer(BulkPayload bulkPayload) {
        TransferServices transferServices = new TransferServices();

        bulkPayload.setSeckey(OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(bulkPayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String response = transferServices.dobulktransfer(payload);
        return response;
    }

}
