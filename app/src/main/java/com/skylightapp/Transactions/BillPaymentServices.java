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



public class BillPaymentServices {
    public String dobillpaymentflw(String params, BillModel billmodel) throws IOException {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post  =  new HttpPost((OurConfig.LIVE_URL));

            //Timber.d(params);

            StringEntity input = new StringEntity(params.toString());
            input.setContentType("application/json");
            post.setEntity(input);
            HttpResponse response = client.execute(post);

            //Timber.tag("do billpayment  response code :::").d(String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            //Timber.tag("dobillpaymentflw response message").d(result.toString());
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            return result.toString();

        } catch (UnsupportedEncodingException ex) {
            //Timber.tag("error").d(Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }
}
