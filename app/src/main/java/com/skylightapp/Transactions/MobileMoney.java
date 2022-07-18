package com.skylightapp.Transactions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MobileMoney {
    public String domobilemoney(MobilemoneyPayload mobilemoneyPayload) throws UnknownHostException {
        OurFluterwavePaymentServices paymentservices = new OurFluterwavePaymentServices();
        InetAddress localhost = InetAddress.getLocalHost();
        mobilemoneyPayload.setIP((localhost).toString());

        Date date = new Date();

        if (mobilemoneyPayload.getTxRef() == null) {
            mobilemoneyPayload.setTxRef("MC" + date);
        }

        if (mobilemoneyPayload.getOrderRef() == null) {
            mobilemoneyPayload.setOrderRef("MC" + date);
        }

        if (mobilemoneyPayload.getSubaccounts() != null) {

            JSONArray array = new JSONArray(mobilemoneyPayload.getSubaccounts());

            List<SplitaddPayload> list = new ArrayList<>();

            if (array != null) {

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

            }
            mobilemoneyPayload.setSubaccounts(list);
        }

        Mpesameta mpesameta = new Mpesameta();
        mpesameta.setMetaname(mobilemoneyPayload.getMetaname());
        mpesameta.setMetavalue(mobilemoneyPayload.getMetavalue());

        if (null != mobilemoneyPayload.getPayment_type()) {
            switch (mobilemoneyPayload.getPayment_type()) {
                case "mobilemoneygh":
                    mobilemoneyPayload.setIs_mobile_money_gh(1);
                    break;
                case "mpesa":
                    mobilemoneyPayload.setIs_mpesa("1");
                    mobilemoneyPayload.setIs_mpesa_lipa(1);
                    break;
                case "mobilemoneyuganda":
                    mobilemoneyPayload.setIs_mobile_money_ug(1);
                    break;
                case "mobilemoneyzambia":
                    mobilemoneyPayload.setIs_mobile_money_ug(1);
                    break;
                default:
                    break;
            }
        }

        TripleDES tripledes = new TripleDES();

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(mobilemoneyPayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String Encryteddata = tripledes.encryptData(payload, mobilemoneyPayload.getEncryption_key());

        String response = null;
        try {
            response = paymentservices.doflwmobilemoney(Encryteddata, mobilemoneyPayload);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }

}
