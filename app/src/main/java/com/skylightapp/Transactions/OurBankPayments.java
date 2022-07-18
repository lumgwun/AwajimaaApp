package com.skylightapp.Transactions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OurBankPayments {
    public String doflwbankpayment(BankPayload bankpayload) throws UnknownHostException {
        SkylightPaymentServices paymentservices = new SkylightPaymentServices();
        InetAddress localhost = InetAddress.getLocalHost();
        bankpayload.setIP((localhost).toString());

        Date date = new Date();

        if (bankpayload.getTxRef() == null) {
            bankpayload.setTxRef("MC" + date);
        }

        if (bankpayload.getSubaccounts() != null) {

            JSONArray array = new JSONArray(bankpayload.getSubaccounts());

            List<SplitaddPayload> list = new ArrayList<>();

            for (int i = 0; i < array.length(); i++) {
                JSONObject recordJSON = null;
                try {
                    recordJSON = array.getJSONObject(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SplitaddPayload splitaddPayload = new SplitaddPayload();
                if (recordJSON != null) {
                    splitaddPayload.setId(recordJSON.optString("id"));
                }
                if (recordJSON != null) {
                    splitaddPayload.setTransaction_split_ratio(recordJSON.optString("transaction_split_ratio"));
                }

                list.add(splitaddPayload);
            }

            bankpayload.setSubaccounts(list);
        }

        TripleDES tripledes = new TripleDES();
        String encrytedsecretkey = tripledes.getKey(bankpayload.getSECKEY());

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(bankpayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String Encryteddata = tripledes.encryptData(payload, encrytedsecretkey);

        String response = paymentservices.doflwbankpayment(Encryteddata, bankpayload);
        return response;

    }
}
