package com.skylightapp.Transactions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class AccountServices {
    public String dolistsubscriptions() {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            String seckey = OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY;

            HttpPost post = new HttpPost((OurConfig.SUSCRIPTION_LIST_URL+seckey));
            post.setHeader("ContentType", "application/json");
            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (IOException ex) {
        }
        return null;
    }

    public String dofetchsubscription(Suscriptionfetch suscriptionfetch) throws IOException {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            String transaction_id = suscriptionfetch.getTransaction_id();
            String seckey = OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY;

            HttpPost post = new HttpPost((OurConfig.SUSCRIPTION_LIST_URL+seckey+transaction_id));
            post.setHeader("ContentType", "application/json");

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }

    public String docancelsubscription(Suscriptionfetch suscriptionfetch) throws IOException {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            String id = suscriptionfetch.getId();
            String fetch_by_tx = suscriptionfetch.getFetch_by_tx();

            HttpPost post = new HttpPost((OurConfig.SUSCRIPTION_URL+id+"/cancel"+fetch_by_tx));
            post.setHeader("ContentType", "application/json");

            JSONObject queryRequest = null;
            try {
                queryRequest = new JSONObject()
                        .put("seckey", OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringEntity input = null;
            if (queryRequest != null) {
                input = new StringEntity(queryRequest.toString());
            }
            if (input != null) {
                input.setContentType("application/json");
            }
            //System.out.println("input ===>" + input);
            post.setEntity(input);

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (UnsupportedEncodingException ex) {
        }
        return null;
    }

    public String doactivatesubscription(Suscriptionfetch suscriptionfetch) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            String id = suscriptionfetch.getId();
            String fetch_by_tx = suscriptionfetch.getFetch_by_tx();

            HttpPost post = new HttpPost((OurConfig.SUSCRIPTION_URL+id+"/activate"+fetch_by_tx));
            post.setHeader("ContentType", "application/json");

            JSONObject queryRequest = null;
            try {
                queryRequest = new JSONObject()
                        .put("seckey", OurConfig.AWAJIMA_PAYSTACK_SECRET_KEY);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            StringEntity input = new StringEntity(queryRequest != null ? queryRequest.toString() : null);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);


            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (IOException ex) {
        }
        return null;
    }
    public String dolistsubaccounts(SubAccountPayload subaccountpayload) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            String seckey = subaccountpayload.getSeckey();

            HttpGet httpget = new HttpGet(OurConfig.SUBACCOUNT_LIST_URL+"?seckey="+seckey);
            httpget.setHeader("ContentType", "application/json");
            HttpResponse response = client.execute(httpget);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (IOException ex) {
        }
        return null;
    }

    public String dogetsubaccounts(SubAccountPayload subaccountpayload) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            String id = subaccountpayload.getSeckey();
            String seckey = subaccountpayload.getSeckey();

            HttpGet httpget = new HttpGet((OurConfig.SUBACCOUNT_LIST_URL + "/get/" + id + "?seckey=" + seckey+ ""));
            httpget.setHeader("ContentType", "application/json");

            HttpResponse response = client.execute(httpget);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (UnsupportedEncodingException ex) {
        } catch (IOException ex) {
        }
        return null;
    }

    public String docreatesubaccounts(SubAccountPayload subaccountpayload) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post = new HttpPost((OurConfig.SUBACCOUNT_LIST_URL + "/create" ));
            post.setHeader("ContentType", "application/json");

            StringEntity input = null;
            try {
                input = new StringEntity(new JSONObject(String.valueOf(subaccountpayload)).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (input != null) {
                input.setContentType("application/json");
            }
            System.out.println("input ===>" + input);
            post.setEntity(input);

            HttpResponse response = client.execute(post);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (IOException ex) {
        }
        return null;
    }

    public String dodeletesubaccounts(SubAccountPayload subaccountpayload) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post = new HttpPost("https://api.ravepay.co/v2/gpx/subaccounts/delete");
            post.setHeader("ContentType", "application/json");

            StringEntity input = new StringEntity(new JSONObject((Map) subaccountpayload).toString());
            input.setContentType("application/json");
            System.out.println("input ===>" + input);
            post.setEntity(input);

            HttpResponse response = client.execute(post);

            //LOG.info("dodeletesubaccounts response code ::: " + response.getStatusLine().getStatusCode());
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (UnsupportedEncodingException ex) {
        } catch (IOException ex) {
        }
        return null;
    }
}
