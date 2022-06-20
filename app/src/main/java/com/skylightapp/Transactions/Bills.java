package com.skylightapp.Transactions;

import com.skylightapp.Classes.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Bills {
    public String dobillpayment(BillModel billmodel) {
        BillPaymentServices billpaymentservices = new BillPaymentServices();

        Service_payload service_payload = new Service_payload();

        Utils referenceutil = new Utils();

        String ref = referenceutil.generateRef(10);

        service_payload.setAmount(billmodel.getAmount());

        service_payload.setBillerName(OurConfig.AIRTIME_BILLERNAME);
        service_payload.setCustomerId(billmodel.getCustomerId());
        service_payload.setRecurringType(billmodel.getRecurringType());
        service_payload.setIsAirtime(billmodel.isIsAirtime());
        service_payload.setAmount(billmodel.getAmount());
        service_payload.setReference(billmodel.getReference());
        service_payload.setCountry(billmodel.getCountry());

        BillLoad billload = new BillLoad();
        billload.setSecret_key(billmodel.getSecret_key());
        billload.setService(OurConfig.BUY_SERVICE);
        billload.setService_channel(OurConfig.SERVICE_CHANNEL);
        billload.setService_method(OurConfig.SERVICE_METHOD_POST);
        billload.setService_version(OurConfig.SERVICE_VERSION);
        billload.setService_payload(service_payload);

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(billload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String response = null;
        try {
            response = billpaymentservices.dobillpaymentflw(payload, billmodel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
