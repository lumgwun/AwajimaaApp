package com.skylightapp.Transactions;

import android.util.Log;

import com.skylightapp.FlutterWavePayments.AccountResolvePayload;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class VerificationServices {
    public String doBvnVerification(Bvnload bvnload) throws IOException, Exception {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            String seckey = OurConfig.SKYLIGHT_SECRET_KEY;
            String bvn = bvnload.getBvn();
            HttpGet httpGet = new HttpGet(OurConfig.BVN_LIVE + "/" + bvn + "?seckey=" + seckey + "");


            httpGet.setHeader("Content-Type", "application/json");

            HttpResponse response = client.execute(httpGet);
            Log.d("do BvnVerification response code :::", String.valueOf(response.getStatusLine().getStatusCode()));

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do BvnVerification response message", result.toString());
            if (!String.valueOf(response.getStatusLine().getStatusCode()).startsWith("2") && !response.getEntity().getContentType().getValue().contains("json")) {
                return null;
            }
            return result.toString();

        } catch (UnsupportedEncodingException ex) {
            Log.d("error", Arrays.toString(ex.getStackTrace()));

        }
        return null;
    }

    public String dotransverify(String params, TransverifyPayload transverifyPayload) throws IOException {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post  = new HttpPost((OurConfig.TRANSACTION_VERIFICATION_URL_LIVE));


            Log.d("do transverify response :::", params);
            //System.out.println("params ===>" + params);

            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("dotransverify response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do transverify request", result.toString());
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
    public String bankAcctVerify(String params, AccountResolvePayload accountResolvePayload) throws IOException {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post  = new HttpPost((OurConfig.RESOLVE_ACCOUNT));

            Log.d("Do Resolve acct response :::", params);
            //System.out.println("params ===>" + params);

            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("Do Resolve acct response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do Resolve request", result.toString());
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


    public String docardvalidate(String params, ValidateCardPayload validatecardpayload) throws IOException {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post = new HttpPost((OurConfig.VALIDATE_CHARGE_URL));
            Log.d("docardvalidate response :::", params);
            //System.out.println("params ===>" + params);

            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("docardvalidate response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do cardvalidate request", result.toString());
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

    public String docapture(String params) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post = new HttpPost((OurConfig.CAPTURE_URL));
            Log.d("do capture response :::", params);
            //System.out.println("params ===>" + params);

            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("docapture response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do capture request", result.toString());
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
    public String doOTP(String params) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post = new HttpPost((OurConfig.CREATE_OTP));
            Log.d("OTPresponse :::", params);
            //System.out.println("params ===>" + params);

            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("OTP response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("Create OTP request", result.toString());
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

    public String doaction(String params) {
        StringBuilder result = new StringBuilder();
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

            HttpPost post = new HttpPost((OurConfig.VOID_URL));
            Log.d("docapture response :::", params);
            //System.out.println("params ===>" + params);

            StringEntity input = new StringEntity(params);
            input.setContentType("application/json");
            //System.out.println("input ===>" + input);
            post.setEntity(input);
            HttpResponse response = client.execute(post);
            Log.d("docapture response code :::", String.valueOf(response.getStatusLine().getStatusCode()));
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            Log.d("do capture request", result.toString());
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
