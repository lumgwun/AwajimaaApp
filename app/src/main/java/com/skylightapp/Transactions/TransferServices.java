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

public class TransferServices {

    public String dotransfer(String params) throws IOException {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post = new HttpPost((OurConfig.TRANSFER_URL+"create"));

            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("do transfer response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do transfer request", result.toString());
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

    public String dobulktransfer(String params) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post = new HttpPost((OurConfig.TRANSFER_URL +"create_bulk"));

            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("dobulktransfer response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do bulk transfer request", result.toString());
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            if (response.getStatusLine().getStatusCode() == 500) {
                return "there is an error with the data";
            } else {
                return result.toString();
            }

        } catch (IOException ex) {
            Log.d("error", Arrays.toString(ex.getStackTrace()));
        }
        return null;
    }
}
