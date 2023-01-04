package com.skylightapp.Transactions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardPayment {
    public String doflwcardpayment(CardLoad cardload) throws UnknownHostException {
        AwajimaPaymentServices paymentservices = new AwajimaPaymentServices();
        InetAddress localhost = InetAddress.getLocalHost();
        cardload.setIP((localhost).toString());

        if(cardload.getTxRef() == null){
            Date date = new Date();
            cardload.setTxRef("MC" + date);
        }
       SkyLightMetaModel skyLightMetaModel = new SkyLightMetaModel();
        skyLightMetaModel.setMetaname(cardload.getMetaname());
        skyLightMetaModel.setMetavalue(cardload.getMetavalue());

        CardPayload cardpayload = new CardPayload();
        cardpayload.setAmount(cardload.getAmount());
        cardpayload.setCardno(cardload.getCardno());
        cardpayload.setCountry(cardload.getCountry());
        cardpayload.setCurrency(cardload.getCurrency());
        cardpayload.setCvv(cardload.getCvv());
        cardpayload.setDevice_fingerprint(cardload.getDevice_fingerprint());
        cardpayload.setEmail(cardload.getEmail());
        cardpayload.setExpirymonth(cardload.getExpirymonth());
        cardpayload.setExpiryyear(cardload.getExpiryyear());
        cardpayload.setFirstname(cardload.getFirstname());
        cardpayload.setIP(cardload.getIP());
        cardpayload.setLastname(cardload.getLastname());
        cardpayload.setPBFPubKey(cardload.getPublic_key());
        cardpayload.setPhonenumber(cardload.getPhonenumber());
        cardpayload.setRedirect_url(cardload.getRedirect_url());
        cardpayload.setTxRef(cardload.getTxRef());
        cardpayload.setMeta(skyLightMetaModel);
        cardpayload.setPin(cardload.getPin());
        cardpayload.setSuggested_auth(cardload.getSuggested_auth());
        cardpayload.setBillingaddress(cardload.getBillingaddress());
        cardpayload.setBillingcity(cardload.getBillingcity());
        cardpayload.setBillingcountry(cardload.getBillingcountry());
        cardpayload.setBillingstate(cardload.getBillingstate());
        cardpayload.setBillingzip(cardload.getBillingzip());
        cardpayload.setEncryption_key(cardload.getEncryption_key());
        cardpayload.setTest(cardload.getTest());

        if (cardload.getSubaccounts() != null) {
            JSONArray array = new JSONArray(cardload.getSubaccounts());

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
            cardpayload.setSubaccounts(list);
        }

        if ("preauth".equals(cardload.getCharge_type())){

            cardpayload.setCharge_type("preauth");
        }

        TripleDES tripledes = new TripleDES();

        String payload = null;
        try {
            payload = new JSONObject(String.valueOf(cardpayload)).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String Encryteddata = tripledes.encryptData(payload, cardpayload.getEncryption_key());

        String response = paymentservices.doflwcardpayment(Encryteddata, cardpayload);

        JSONObject myObject = null;
        try {
            myObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject Object = null;
        if (myObject != null) {
            Object = myObject.optJSONObject("data");
        }
        if (myObject != null && !"V-COMP".equals(myObject.optString("message"))) {
            if (Object != null) {
                if (myObject.optString("status").equals("success") && Object.optString("suggested_auth").equals("PIN")) {
                    SuggestedLoad suggestedload = new SuggestedLoad();
                    suggestedload.setSuggested_auth("PIN");
                    suggestedload.setRequest("please enter pin");
                    try {
                        response = new JSONObject(String.valueOf(suggestedload)).toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (myObject.optString("status").equals("success") && Object.optString("suggested_auth").equals("NOAUTH_INTERNATIONAL")) {
                    SuggestedLoad suggestedload = new SuggestedLoad();
                    suggestedload.setSuggested_auth("NOAUTH_INTERNATIONAL");
                    suggestedload.setRequest("please enter billingzip, billingcity, billingaddress ,billingstate ,billingcountry");
                    try {
                        response = new JSONObject(String.valueOf(suggestedload)).toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (myObject.optString("status").equals("success") && Object.optString("authurl") != "N/A") {
                    SuggestedLoad suggestedload = new SuggestedLoad();
                    suggestedload.setSuggested_auth(Object.optString("authurl"));
                    try {
                        response = new JSONObject(String.valueOf(suggestedload)).toString();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }
}
