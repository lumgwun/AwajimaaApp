package com.skylightapp.Transactions;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class EbillServices {
    public String doebillscreate(String params, Ebillpayload ebillpayload) throws IOException {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post  = new HttpPost((OurConfig.EBILLS_URL+"generateorder/"));

            Log.d("do qrpayment response :::", params);
            //System.out.println("params ===>" + params);


            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("do ebills create response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do ebills create request", result.toString());
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (UnsupportedEncodingException ex) {
            Log.d("error", Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }


    public String doebillsupdate(String params, Ebillpayload ebillpayload) throws IOException {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post  = new HttpPost((OurConfig.EBILLS_LIVE_URL+"update/"));

            Log.d("do qrpayment response :::", params);
            //System.out.println("params ===>" + params);


            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("do ebillsupdate response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do ebillsupdate request", result.toString());
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (UnsupportedEncodingException ex) {
            Log.d("error", Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }
}
