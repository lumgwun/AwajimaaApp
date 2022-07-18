package com.skylightapp.FlutterWavePayments;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.util.HashMap;

import javax.net.ssl.SSLContext;
import static org.apache.http.conn.ssl.SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;




public class ApiConnection {
    final private String url;


    public ApiConnection(String url) {
        this.url = url;
        this.enforceTlsV1point2();
    }
    private void enforceTlsV1point2() {
        try {
            SSLContext sslContext = SSLContexts.custom()
                    .build();
            SSLConnectionSocketFactory f = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1.2"},
                    null,
                    BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(f)
                    .build();
            Unirest.setHttpClient(httpClient);

        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
            Logger.getLogger(ApiConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public JSONObject connectAndQuery(ApiQuery query) {
        try {
            HttpResponse<JsonNode> queryForResponse = Unirest.post(url)
                    .header("Accept", "application/json")
                    .fields(query.getParams())
                    .asJson();



            try{
                return queryForResponse.getBody().getObject();
            }catch(Exception ex){}
        } catch (UnirestException e) {
        }
        return  null;
    }

    public JSONObject connectAndQuery(HashMap<String, Object> query) {
        try {
            HttpResponse<JsonNode> queryForResponse = Unirest.post(url)
                    .header("Accept", "application/json")

                    .fields(query)
                    .asJson();
            return queryForResponse.getBody().getObject();
        } catch (UnirestException e) {
        }
        return null;
    }

    public JsonNode connectAndQueryWithGet() {
        try {
            HttpResponse<JsonNode> queryForResponse = Unirest.get(url)
                    .header("content-type", "application/json")

                    .asJson();

            return queryForResponse.getBody();

        } catch (UnirestException e) {
            System.out.println("Cant query at this time!");
        }
        return null;
    }

}
